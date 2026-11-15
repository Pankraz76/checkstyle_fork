# Proposal for upstream issue 4409

Post this as a comment on `checkstyle/checkstyle` issue 4409 ("Add MultiThread Checker
implementation", open, approved) instead of opening a duplicate. Everything from the next
heading on is the comment.

## Multi-threaded `Checker`: design proposal with a verified prototype

I went through the earlier attempts and built a throwaway prototype to check whether a design
exists that satisfies the requirements stated there. It does, and I would like to agree on it
before writing any production code. The earlier attempts are the issues #138, #4354, #4370,
this one, #4473, #4869, #4957, #5140 and #7253 and the pulls #4882, #4890 and #4958.

### Requirements taken from the earlier discussions

- Compatibility and stability before speed; no demand that checks become thread-safe.
- Modules are created from configuration or reused, never cloned; no new API for checks.
- Global-stateful modules run as they do today.
- No second pass that reads all files again.
- Start with `Checker`; nothing is parallelised inside one file.
- Error reporting stays consistent.

### Design

The thread that calls `Checker.process` stays the coordinator. It adds worker threads.

- For each worker, `Checker` creates one more instance of every FileSetCheck that is marked
  `@StatelessCheck` or `@FileStatefulCheck`, through the normal path (`ModuleFactory`,
  `contextualize`, `configure`, `init`). That includes `TreeWalker` with all its checks and
  filters. An instance is only ever used by its worker.
- FileSetChecks marked `@GlobalStatefulCheck`, FileSetChecks without a marker, all Checker
  filters, all listeners and the cache stay single instances that only the coordinator touches.
- A worker reads the file and runs its modules. It returns the `FileText` and the violations.
- The coordinator takes the results strictly in input order and does what `processFiles` does
  today: `fireFileStarted`, the global FileSetChecks (with the `FileText` from the worker),
  filters, listeners, cache, `fireFileFinished`.
- With one thread configured, today's code path runs unchanged. That is the default.

No module needs to be thread-safe, no signature changes, and reports keep their order.

### Evidence from the prototype

Checkstyle 14.4.0-SNAPSHOT (`9d82118`), JDK 21, 4 cores. XML reports compared byte for byte.

| Configuration | Files | Violations | 1 thread against 2 to 4 threads |
|---|---|---|---|
| `sun_checks.xml` | 1,275 | 41,802 | identical |
| `google_checks.xml` | 1,275 | 97,869 | identical |
| `config/checkstyle-checks.xml` | 1,275 | 86 | identical |
| all 247 modules with defaults | 1,275 | 87,856 | identical, 3 repetitions |
| cache file enabled | 1,275 | 0 | same cache entries |
| `haltOnException`, broken file | 9 | 2 | same output, exit code, exception chain |

Files are Checkstyle's `src/main/java`, `src/test/java` and `src/it/java`. On 7,236 test input
files with all modules, 4 files differed; every difference is one of the existing defects
listed below or the stack trace text inside a `general.exception` violation.

Controls: sharing module instances between 4 threads throws `NoSuchElementException` within
about a second. Leaving out the hand-over for `SuppressWarningsHolder` loses 14 suppressions.

Wall time for `config/checkstyle-checks.xml` on those files, whole JVM run: 28.6 to 30.1 s with
one thread, 17.2 to 18.9 s with four. A single-threaded run already uses two cores because of
JIT and GC, so four cores cap the gain. With C1 only and serial GC: 54.1 s, 30.3 s, 24.5 s and
22.6 s for 1, 2, 3 and 4 threads. This is one machine and not a benchmark; a proper report
(including the old path against the new code with one worker, as asked in #4958) is part of
the plan.

### Existing defects found on the way

These exist with one thread today and I will file them separately:

- `SuppressWarningsFilter` applies `@SuppressWarnings` entries of the previous Java file to a
  following non-Java file, so the result depends on file order.
- `TranslationCheck` reports bundles in the iteration order of a `HashSet` of objects without
  `equals` and `hashCode`; the order changes with the JVM's identity hash setting.
- `JavadocLeadingAsteriskAlignCheck` is marked `@GlobalStatefulCheck` but is a TreeWalker
  check with per-file state.

### Questions

- Is a `Checker` property (default 1) plus a CLI option acceptable, now that `-C` and `-W`
  are gone (#8181)? Should the `ThreadModeSettings` plumbing be reused or removed?
- FileSetChecks without a marker stay on the calling thread as single instances. Agreed?
- `SuppressWarningsHolder` passes data to `SuppressWarningsFilter` through a static
  `ThreadLocal`. The prototype hands the entries over with the per-file result. Is that
  acceptable as an internal mechanism, or do you prefer another way (#4831)?
- A FileSetCheck that reports from `finishProcessing` is a multi-file check and must be marked
  global. Is it fine to state that in the marker Javadoc?
- The marker Javadoc says stateless checks are shared between threads. With one instance per
  worker this is no longer needed for correctness. Do you still want new checks to be
  stateless, or is file-stateful with a reset in `beginTree` equally welcome?

### Plan

Small pull requests in this order: fix the defects above; add tests that prove a file's
result does not depend on the files processed before; refactor `Checker.processFiles` without
behaviour change; add the engine without any way to enable it; verify on the regression
projects and publish numbers; only then add the property, the CLI option and the docs.

I will not start beyond the defect fixes and the tests until the design is agreed here.
