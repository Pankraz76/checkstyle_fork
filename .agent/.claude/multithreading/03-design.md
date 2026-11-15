# Target design

Proposal, not yet agreed with the maintainers. Phase 0 of the plan exists to get it agreed.

## Goal and non-goals

Goal: `Checker` processes files on several threads, opt-in, with results that cannot be told
apart from a single-threaded run.

Not part of this feature:

- Parallel work inside one file (rejected upstream, pull 4958).
- Multi-file validation for TreeWalker checks (issue 3540).
- Changes in the Maven, Gradle or IDE plugins.
- Making any check, filter or listener thread-safe.

## Invariants

| Id | Invariant | Automatic test |
|---|---|---|
| I1 | Same listener events in the same order as with one thread | differential test |
| I2 | Same exit code, same exception type, message and cause chain | differential test |
| I3 | Same cache file content after the run | differential test |
| I4 | One thread configured means today's code path, unchanged | existing test suite |
| I5 | No module instance is ever used by two threads | ArchUnit rule plus stress test |
| I6 | No public API signature changes; no new annotation or interface | review of the diff |

One accepted difference: the stack trace text inside a `general.exception` violation and the
"... n more" lines on standard error contain thread frames.

## Roles

```text
caller of Checker.process  =  coordinator thread
   |  in input order: cache lookup, before-execution filters, cache put
   |  submit file ------------------------------> worker thread (one of n)
   |                                                 read FileText
   |                                                 run own module tree:
   |                                                   stateless / file-stateful FileSetChecks,
   |                                                   TreeWalker with its checks and filters
   |  <----- FileText, violations, holder entries -- done
   |  in input order, exactly as today:
   |     fireFileStarted
   |     global and unannotated FileSetChecks (given the worker's FileText)
   |     Checker filters -> listeners, cache remove on violations
   |     fireFileFinished
   v  after the last file: finishProcessing, destroy, auditFinished
```

## Where each kind of module runs

| Module kind | Instances | Thread |
|---|---|---|
| `@StatelessCheck` or `@FileStatefulCheck` FileSetCheck, including `TreeWalker` | n + 1 | worker |
| Checks and filters inside a `TreeWalker` | one set per TreeWalker | worker |
| `@GlobalStatefulCheck` FileSetCheck | 1 | coordinator |
| FileSetCheck without any marker (third party) | 1 | coordinator |
| Filter, BeforeExecutionFileFilter | 1 | coordinator |
| AuditListener, cache, error counter | 1 | coordinator |

The "+ 1" is the instance created during configuration as today. It validates the
configuration at start-up and serves the single-threaded path. Worker instances are created by
the coordinator, before the first worker starts, through the normal path: `ModuleFactory`,
`contextualize`, `configure`, `init`. A check inside `TreeWalker` never needs a marker to be
safe, because its `TreeWalker` is confined to one worker.

## Why results are identical

- A file is processed by one complete module tree on one thread, so everything inside a file
  happens in today's order, including the order of violations at equal positions.
- Results for a file do not depend on other files, provided modules reset their per-file state.
  That property is what phase 1 repairs and what the phase 2 tests guard.
- Everything that depends on file order (global modules, filters with caches, listeners, the
  cache file, the first exception) runs on one thread in input order.

## Decisions

Every decision, taken or open, is in `04-decisions.md` with context and consequences. This
design depends on the open decisions D1 to D6 there. Questions that need a person are in
`06-open-questions.md`.

## Risks

| Risk | Mitigation |
|---|---|
| A module keeps state across files without resetting it | phase 1 fixes, phase 2 order test |
| Third-party check and filter talk through a `ThreadLocal` | documented limit; D3 pattern |
| More memory: n module trees and pending file texts | D4, measured in phase 5 |
| ANTLR caches under concurrent parsing | task in phase 1 verifies the ANTLR contract |
| Maintainers do not agree again | phase 0 comes before any production code |
| Gain is small on few cores or many violations | numbers are published as they are |
