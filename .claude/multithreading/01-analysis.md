# Feasibility analysis

Audit of `master` at commit `9d82118` (version `14.4.0-SNAPSHOT`, 2026-10-24). Everything below
was read in the source or measured; items that were not verified are marked.

## Verdict

Processing files in parallel is feasible without changing the check API and without making
any module thread-safe, on one condition: module instances are confined to a thread, and
everything that is observable (filters, listeners, cache, exit code) stays on the calling
thread in the original file order. A prototype of exactly this produced byte-identical reports.

Sharing module instances between threads is not feasible. It fails within one second.

## How a run works today

| Step | Code | Thread-relevant state |
|---|---|---|
| Start | `Checker.process` | fires `auditStarted`, calls `beginProcessing` on all FileSetChecks |
| Per file | `Checker.processFiles` | cache lookup, before-execution filters, cache `put` |
| Per file | `Checker.processFile` | reads `FileText`, calls every `FileSetCheck.process` |
| Per file | `TreeWalker.processFiltered` | parse, walk, TreeWalker filters; field `violations` |
| Per file | `Checker.fireErrors` | Checker filters, then listeners, cache `remove` |
| End | `Checker.process` | `finishProcessing`, `destroy`, `auditFinished` |

Everything runs on the calling thread. `ThreadModeSettings`, `DefaultConfiguration` and
`ConfigurationLoader` still carry thread numbers from 2017, but `Main` passes the constant 1
and `ThreadModeSettings.resolveName` throws "not implemented" for the modules `Checker` and
`TreeWalker` as soon as more than one checker thread is requested.

## Module inventory

| Kind | Count | Source of truth |
|---|---|---|
| Classes annotated `@StatelessCheck` | 149 | `grep -rl "^@StatelessCheck" src/main/java` |
| Classes annotated `@FileStatefulCheck` | 75 | same with `@FileStatefulCheck` |
| Classes annotated `@GlobalStatefulCheck` | 3 | same with `@GlobalStatefulCheck` |
| TreeWalker checks in metadata | 227 | `src/main/resources/.../meta/checks` |
| FileSet checks in metadata | 15 | same, parent `Checker` |
| TreeWalker filters | 4 | `meta/filters`, parent `TreeWalker` |
| Checker filters and file filters | 7 | `meta/filters`, `meta/filefilters` |
| Audit listeners | 6 | `implements AuditListener` |

The three global-stateful classes are `TranslationCheck`, `JavadocPackageCheck` and
`JavadocLeadingAsteriskAlignCheck`. The last one is a TreeWalker Javadoc check whose fields
hold per-file data, so the annotation is wrong (finding F3).

## State that matters for threads

| Location | State | Consequence |
|---|---|---|
| `AbstractCheck`, `AbstractFileSetCheck` | per-instance `ThreadLocal` context | already isolated |
| `AbstractJavadocCheck` | static `ThreadLocal` tree cache, instance `ThreadLocal` | isolated |
| `SuppressWarningsHolder` | static `ThreadLocal` list `ENTRIES` | read by a Checker filter |
| `TreeWalker` | fields `violations`, check sets | one instance per thread needed |
| 75 file-stateful checks | plain fields, reset in `beginTree` | one instance per thread |
| `TranslationCheck` | `HashSet` of files, reports at the end | one instance, one thread |
| `JavadocPackageCheck` | `HashSet` of directories | one instance, order matters |
| Checker filters | per-file caches in two text filters | one thread |
| TreeWalker filters | `WeakReferenceHolder` per-file cache | live inside a TreeWalker |
| `XMLLogger`, `SarifLogger`, counters | `HashMap`, `ArrayList`, plain `int` | one thread |
| `PropertyCacheFile` | `Properties`, static shared `byte[]` buffer | one thread |
| `LocalizedMessage` | static `Locale`, set during setup | written before workers start |
| `XpathFileGeneratorAstFilter` | static `HashMap`, written while filtering | finding F6 |
| `JavaLanguageParser` (grammar) | `static int fileCounter`, clears shared DFA | finding F7 |
| `JavaAstVisitor` | `parallelStream()` on the common pool | finding F8 |
| `PackageObjectFactory` | lazily loaded third-party name map | create modules on one thread |

The release notes of 8.x list thread-safety changes for `SeverityLevelCounter` and `XMLLogger`
(issues 4927 and 4932). They are not in the code any more: the counter is a plain `int` and
`XMLLogger` uses a `HashMap`. That is fine as long as listeners are only called from one
thread.

## Prototype

The prototype is in `spike/spike.patch` (throwaway, not production code). It keeps the calling
thread as coordinator and adds worker threads:

- The coordinator creates one extra module tree per worker from the same `Configuration`
  (FileSetChecks that are stateless or file-stateful, including `TreeWalker` with all its checks
  and filters). Global-stateful and unannotated FileSetChecks stay single instances.
- A worker reads the file and runs its own module tree. It returns the `FileText`, the sorted
  violations and a copy of the `SuppressWarningsHolder` entries.
- The coordinator takes results strictly in input order and does the rest as today: file
  started, global FileSetChecks (with the `FileText` from the worker, no second read), Checker
  filters, listeners, cache, file finished. A window of `4 * threads` files bounds memory.

### Equivalence results

Machine: 4 cores (Intel Xeon 2.1 GHz), 15 GB, JDK 21.0.12. Reports are XML (`-f xml`), compared
with `cmp`. Corpus A is `src/main/java`, `src/test/java`, `src/it/java` (1,275 files). Corpus B
is the test input folders and `src/xdocs-examples` (7,236 files of all types, many of them not
compilable).

| Configuration | Corpus | Violations | Result |
|---|---|---|---|
| `sun_checks.xml` | A | 41,802 | identical for 2 and 4 threads |
| `google_checks.xml` | A | 97,869 | identical for 4 threads |
| `config/checkstyle-checks.xml` | A | 86 | identical for 2, 3 and 4 threads |
| all 247 modules, defaults | A | 87,856 | identical, 3 runs, also to unpatched jar |
| all 247 modules, defaults | B | 582,564 | 4 files differ, all explained below |
| cache file enabled | A | 0 | same cache entries, warm run audits 0 files |
| `haltOnException`, broken file | 9 files | 2 | same output, exit code and exception chain |

Differences on corpus B and their causes:

- Two `.txt` files: `SuppressWarningsFilter` applied entries that were left over from an
  earlier Java file. This is defect F1 and happens with one thread as well; which entries are
  left over simply depends on the file processed before.
- Two `.java` files in one folder: a check threw an exception and `haltOnException` was off.
  The first prototype skipped global checks for such a file. After honouring the module order
  (modules before the failing one still run) the difference was gone.
- After removing `SuppressWarningsFilter` and with that fix, the only remaining difference was
  the position of one `TranslationCheck` block. This is defect F2.
- The stack trace inside a `general.exception` violation contains thread frames and therefore
  differs in text. This is the only accepted difference.

### Controls

| Control | Result |
|---|---|
| Module instances shared by 4 threads | `NoSuchElementException` in `TreeWalker` after 1.2 s |
| No hand-over of `SuppressWarningsHolder` entries | 97,883 instead of 97,869 violations |

### Speed

Not a benchmark: one machine, one corpus, wall time of whole JVM runs.

| Configuration, corpus A | 1 thread | 2 threads | 3 threads | 4 threads |
|---|---|---|---|---|
| project config, default JVM | 28.6 to 30.1 s | 18.0 to 19.1 s | not run | 17.2 to 18.9 s |
| project config, C1 and serial GC | 54.1 s | 30.3 s | 24.5 s | 22.6 s |
| `google_checks.xml`, default JVM | 19.6 s | not run | not run | 15.3 s |
| `sun_checks.xml`, default JVM | 10.3 s | 7.7 s | not run | 8.1 s |

- With the default JVM a single-threaded run already uses two cores (61 s CPU in 30 s) because
  of JIT compilation, garbage collection and the `parallelStream()` in `JavaAstVisitor`. On four
  cores that caps the gain at about 1.6 times.
- With that noise removed the same work scales 1.8, 2.2 and 2.4 times for 2, 3 and 4 threads.
  The serial part is roughly 12 percent (start-up, configuration, coordinator).
- Runs with very many violations gain least: filters and loggers are serial by design.
- Corpus B gained almost nothing (148.9 s against 137.1 s). One input file,
  `InputXpathMapperStringConcat.java` (284 KB), needs about 2 minutes to parse on its own. While
  it runs, the window is full and three workers idle. See decision D4 in `04-decisions.md`.
- Upstream measured in 2017 that parsing takes 42 to 68 percent of the time and walking 21 to
  24 percent (issue 4354). Both are inside the parallel part of this design.

## Findings

| Id | Finding | Type | Proof |
|---|---|---|---|
| F1 | `SuppressWarningsHolder` entries outlive their file | defect today | CLI below |
| F2 | `TranslationCheck` order depends on identity hashes | defect today | CLI below |
| F3 | `JavadocLeadingAsteriskAlignCheck` is marked global-stateful | wrong marker | source |
| F4 | File-stateful checks without `beginTree` reset | hygiene | upstream issue 22175 |
| F5 | `PropertyCacheFile` shares a static buffer; `destroy` skipped on failure | leak | own PRs |
| F6 | `XpathFileGeneratorAstFilter` fills a static map while filtering | race in MT | source |
| F7 | `fileCounter` is a plain static `int`; `clearDFA` on a shared cache | benign | below |
| F8 | `parallelStream()` inside the parser | oversubscription | source |
| F9 | Dead thread-mode plumbing (`ThreadModeSettings` and friends) | clean-up | source |

F5 is covered by the open pull requests 22180, 22181 and 22182 of this fork's owner.

### F7 in detail (verified in the ANTLR 4.13.2 sources, unit 1.6)

- `ATNSimulator.clearDFA` is documented as: "Since the DFA cache may be shared by multiple ATN
  simulators, this method may affect the performance (but not accuracy) of other parsers
  which are being used concurrently."
- `ParserATNSimulator.clearDFA` replaces each element of the shared array with a new `DFA`,
  without a lock. `adaptivePredict` reads the element once into a local variable and uses that
  object for the whole prediction, so a parser works with the old `DFA` or with the new one,
  never with a mixture.
- A `DFA` can be published this way: its fields are `final` (`states`, `decision`,
  `atnStartState`, `precedenceDfa`) or `volatile` (`s0`).
- Updates of a shared `DFA` are locked as the class comment "THREAD SAFETY" describes:
  `addDFAState` locks `dfa.states`, `addDFAEdge` locks the source state, and
  `getCachedContext` locks the shared context cache.
- What remains is Checkstyle's own `static int fileCounter` in `JavaLanguageParser.g4`. Two
  threads can lose an increment or both clear the cache. That costs time, not correctness.
- The lexers and the Javadoc parser share their caches in the same way and never clear them.

### F1 reproduction

```text
$ cat A.java
@SuppressWarnings("all")
class A {
    int a;
    int b;
    int c;
}
$ java -jar checkstyle-all.jar -c cfg.xml b.txt A.java
Starting audit...
[ERROR] b.txt:2:1: Line contains a tab character. [FileTabCharacter]
Audit done.
$ java -jar checkstyle-all.jar -c cfg.xml A.java b.txt
Starting audit...
Audit done.
```

`cfg.xml` contains `SuppressWarningsFilter`, `FileTabCharacter` and a `TreeWalker` with
`SuppressWarningsHolder`; `b.txt` has a tab on line 2. The violation disappears when the Java
file is processed first, because the filter still sees the suppression of `A.java`.

### F2 reproduction

```text
$ java -jar checkstyle-all.jar -c tr.xml src/main/resources | head -3
Starting audit...
[ERROR] .../checks/regexp:1: Properties file 'messages_sq.properties' is missing.
[ERROR] .../checks/design:1: Properties file 'messages_sq.properties' is missing.
$ java -XX:+UnlockExperimentalVMOptions -XX:hashCode=2 -jar checkstyle-all.jar \
    -c tr.xml src/main/resources | head -3
Starting audit...
[ERROR] .../checks/metrics:1: Properties file 'messages_sq.properties' is missing.
[ERROR] .../checks/modules:1: Properties file 'messages_sq.properties' is missing.
```

`tr.xml` is `Translation` with `requiredTranslations=sq`. The 19 violations are the same, their
order follows a `HashSet` of objects without `equals` and `hashCode`.

## Limits of this analysis

- Both reproductions used a jar built from `master`, not the latest release. Re-run them with
  the latest release before filing the defects.
- Equivalence was shown on Checkstyle's own sources and test inputs. Large external projects
  (the `checkstyle-tester` set) come in phase 5.
- Third-party checks and filters were not tested. The design treats unannotated FileSetChecks
  as single instances on the calling thread for that reason.
- No test with more than four cores, no memory measurement, no JMH benchmark.
