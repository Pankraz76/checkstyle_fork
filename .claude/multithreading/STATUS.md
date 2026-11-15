# Status

The one place that says where the work stands. Read it first, update it last. If it
disagrees with the folders `plan/todo` and `plan/done`, the folders are right and this file
is corrected.

## Now

| Field | Value |
|---|---|
| Last finished | A.1 feasibility analysis and spike, 2026-11-15 |
| Can start at once | 0.1 (owner posts the proposal), 1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 2.1, 2.3 |
| Waiting for | phase 0 gate before any unit of phases 3 to 6 |
| Audit commit | `9d82118` |

## Claims

A unit is claimed before work starts and released when its file is in `done/`. Never work on
a unit that another branch has claimed.

| Unit | Branch | Since |
|---|---|---|
| none | | |

## Phases

- [x] A Analysis and spike (1 of 1 units)
- [ ] 0 Agreement (0 of 1 units)
- [ ] 1 Existing defects (0 of 7 units)
- [ ] 2 Safety net (0 of 3 units)
- [ ] 3 Prepare Checker (0 of 3 units)
- [ ] 4 Parallel engine (0 of 6 units)
- [ ] 5 Verification at scale (0 of 3 units)
- [ ] 6 Exposure (0 of 5 units)

## Units

- [x] A.1 Feasibility analysis and spike
- [ ] 0.1 Proposal on upstream issue 4409
- [ ] 1.1 F1: suppression entries outlive their file
- [ ] 1.2 F2: order of `TranslationCheck` reports
- [ ] 1.3 F3: marker of `JavadocLeadingAsteriskAlignCheck`
- [ ] 1.4 F4: state reset in file-stateful checks (upstream issue 22175)
- [ ] 1.5 F5: finish the open pull requests 22180, 22181, 22182
- [ ] 1.6 F7: parser caches under concurrent parsing
- [ ] 1.7 F8: `parallelStream()` inside `JavaAstVisitor`
- [ ] 2.1 Recording listener
- [ ] 2.2 File independence of all modules
- [ ] 2.3 Static state guard
- [ ] 3.1 Per-file result value
- [ ] 3.2 Split `processFiles` into select, execute, report
- [ ] 3.3 Module creation that can be repeated
- [ ] 4.1 Ordered window (no Checkstyle types)
- [ ] 4.2 Module trees for workers
- [ ] 4.3 Work done by a worker
- [ ] 4.4 Work done by the coordinator
- [ ] 4.5 Suppression hand-over (decision D3)
- [ ] 4.6 Wiring and equivalence
- [ ] 5.1 Differences on real projects
- [ ] 5.2 Performance report
- [ ] 5.3 Self-check
- [ ] 6.1 `Checker` property (decision D1)
- [ ] 6.2 Command line
- [ ] 6.3 Ant task (upstream issue 4577)
- [ ] 6.4 Contract for check authors
- [ ] 6.5 Dead plumbing (finding F9)

## Log

Newest first. One line for each finished unit or decision.

| Date | Event |
|---|---|
| 2026-11-15 | Unit A.1 done: verdict feasible, prototype identical on 1,275 files |
