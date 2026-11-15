# Upstream history

What `checkstyle/checkstyle` already tried and decided. Read on 2026-11-15 from the public issue
and pull request pages. Quotes are verbatim. Two timelines (issues 4869 and 22175) had collapsed
entries that were not read.

## Timeline

| When | What | Reference |
|---|---|---|
| 2014 | First request; closed when the contributor gave up | issue 138 |
| 2015 | Maven runs Checkstyle concurrently; Javadoc checks get basic safety | issue 2237 |
| 2017 | GSoC project: investigation, hidden CLI options `-C` and `-W` | issues 4354, 4370 |
| 2017 | Markers `@StatelessCheck`, `@FileStatefulCheck`, `@GlobalStatefulCheck` | 4883, 4870 |
| 2017 | `ThreadLocal` contexts in the abstract check classes | 4908, 4917, 4925 |
| 2017 | Draft pull requests: clone service, MT Checker, MT TreeWalker | 4882, 4890, 4958 |
| 2018 | MT TreeWalker rejected and closed | pull 4958 |
| 2019 | Clone service and MT Checker closed: no agreement on the design | pulls 4882, 4890 |
| 2019 | "Checkstyle is single thread tool now", topic postponed | issues 4957, 7253 |
| 2020 | CLI options `-C` and `-W` removed in 8.32 | issue 8181 |
| 2020 | `TranslationCheck` output order reported and fixed in 8.38 | issue 8963, pull 8967 |
| 2026 | Every file-stateful check must reset state in `beginTree` | issue 22175 |
| 2026 | Review on this fork owner's pull request asks for stateless checks | pull 22133 |

## Issues that are still open

| Issue | Title | Labels |
|---|---|---|
| 4409 | Add MultiThread Checker implementation | approved |
| 4957 | Add MultiThread TreeWalker implementation | hard |
| 4869 | Implement a class to clone checks for MT mode | approved |
| 5140 | GlobalStatefulCheck annotation discussion | approved |
| 4473 | Split checks in three groups for MT implementation (discussion) | none |
| 4577 | Add multi thread mode to ANT task | approved |
| 7253 | Make Checkstyle multithread save? | none |
| 3540 | Support multi-file validation FileSet Modules | approved, hard |
| 3539 | Checker Cache and TranslationCheck conflict | approved, hard |
| 4831 | SuppressWarningsFilter should be a child of TreeWalker | approved, breaking |

Issue 4409 is the natural home for a new proposal. Opening a competing issue would duplicate it.

## Positions of the maintainers

### romani (Roman Ivanov)

- Priorities, issue 138: "stability for our project is in more priority then performance."
- Priorities, issue 5140: "Compatibility is more in demand then speed." and "Demand for all
  Checks to be thread safe - will over complicate thirdparty checks and make validation
  unstable."
- Instances, issue 4409: "each thread will have it is own set of modules instances. So all
  modules will be completely tread local."
- Instances, issue 4869: "Cloning is not required, I try to avoid this word." and "if Stateless
  or FileStateful then create by config (as usually)." and "If Globalstateful then reuse
  instance that already exists." and "So "create or reuse" but not a clone."
- API, issue 4869: "changes in API is also suspicious , we should avoid this".
- Global checks, issue 5140: "`GlobalStatefulCheck` annotation should be allowed ONLY on
  FileSetChecks" and checks that are not thread safe "should be executed as it was before".
- TreeWalker, pull 4958: "I do not support such MT model also. We definitely need to focus on
  threading Checker first." The objection was "enormous futures emission".
- Why it stopped, pull 4890: "we cannot find agreement on design of MT mode, I am closing this
  PR, we can come back to it ones we agree and have time to continue."
- Later idea, issue 7253: multi-threading for parsing only, validation and filters stay on one
  thread. "Problem of MT is rooting too deep to checkstyle architecture and goals."
- 2026, pull 22133: "we need stateless, as eventually we will do multithread processing, so it
  will be away more performant."

### rnveach (Richard Veach)

- Where to parallelise, issue 7253: "IMO, the easiest way for MT support is to make Checker MT.
  Then everything else falls in."
- No second pass, issue 5140: "A split of checks into 2 different runs will cause unnecessary
  IOs as we will have to re-read the files."
- Instances, issue 4869: "Multi-file modules must be a single instance throughout all threads"
  and "Non-multi-file modules must be different instances in each thread."
- TreeWalker, pull 4958: "Each check expects visit token to be called in order of the tree."
- Motivation, issue 5140: "Speed is the whole reason people want multi-threading mode."

### Others

- oburn, issue 138: error reporting must stay consistent, hosts may not allow threads, and "any
  solution should be backwardly compatible with the current Check API."
- pbludov, pull 4958: asked for a measurement of the single-threaded version against the new
  code with exactly one thread. Nobody posted one.
- soon (GSoC student), issue 4473: single thread about 40 s, per-thread instances about 32 s on
  his corpus; with a `ThreadLocal` holder 1 min 37 s against 2 min 02 s on OpenJDK 7 sources.
- soon, issue 4409: `Violation.compareTo` is not consistent with `equals`, so two checks that
  report the same position can swap order when they run on different threads.

## What the disagreement was about

| Question | romani | rnveach |
|---|---|---|
| How instances are made | create from configuration or reuse, never "clone" | clone service |
| Global-stateful modules | separate part, as before, no thread demands | one shared instance |
| Second pass over files | acceptable, cheap | not acceptable, doubles reading |
| Where to start | Checker | Checker |
| Inside one file | no | no |

Both agreed on per-thread instances for everything that is not global, on starting with
`Checker`, and on leaving `TreeWalker` alone. The pull requests died on the word "clone", on new
marker interfaces in the API, and on how global modules run.

## How the proposed design answers each point

| Concern | Answer in `03-design.md` |
|---|---|
| Stability and compatibility first | one thread is the default and is the old code path |
| No thread-safety demands on checks | every module is confined to one thread |
| Create or reuse, no clone | workers get new instances from `Configuration` |
| Global modules as before | they stay single instances on the calling thread |
| No second pass | the worker hands its `FileText` to the calling thread |
| Consistent error reporting | listeners run on the calling thread in input order |
| No API change | no new interface, no new annotation, no changed signature |
| Order of equal positions | one module tree per file, same order as today |
| Hosts that forbid threads | opt-in |
| Measurement with one thread | task in phase 5, and one thread is the old path |

## About "we need stateless" (pull 22133)

The request assumes that one check instance is shared by all threads; only then does a
stateless check matter for correctness. Under the rule the maintainers wrote down in 2018
(stateless or file-stateful: create by configuration for each thread) both kinds are equally
safe, and the prototype confirms it for all 75 file-stateful checks. Stateless checks keep one
real advantage: nothing to reset between files, so no `beginTree` bug of the kind tracked in
issue 22175. This is worth raising with the maintainers, with the prototype as evidence,
because it removes a constraint from many check contributions.
