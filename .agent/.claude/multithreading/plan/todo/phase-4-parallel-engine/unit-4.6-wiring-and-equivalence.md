# Unit 4.6 - Wiring and equivalence

| Field | Value |
|---|---|
| Phase | 4 - Parallel engine |
| Kind | new code |
| Depends on | units 4.4, 4.5, 2.1 and the phase 1 gate |
| Issue | to be written |
| Estimate | 9.5 hours in 7 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

The engine is wired into `Checker` behind a test-only switch and proven equivalent.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T4.6.1 (1 h) Package-private switch; one thread takes the old path. Proof: S25.
- [ ] T4.6.2 (2 h) Differential test: all modules on the test inputs, 2 and 4 threads. Proof: S26.
- [ ] T4.6.3 (1 h) Differential test: `sun_checks`, `google_checks`, project config. Proof: S26.
- [ ] T4.6.4 (1 h) Edge sizes: no file, one file, fewer files than threads. Proof: S27.
- [ ] T4.6.5 (1 h) Same `Checker` used for a second `process` call. Proof: S28.
- [ ] T4.6.6 (1.5 h) Stress: 50 repetitions with random thread counts. Proof: S29.
- [ ] T4.6.7 (2 h) `pitest` profile for the new classes, no surviving mutation. Proof: pitest
  report.

## Unit gate

- [ ] Targeted tests and the meta tests this unit touches are green
- [ ] Self-check is green (`antrun:run@ant-phase-verify`, command in `AGENTS.md`)
- [ ] `clean verify` is green (command in `AGENTS.md`)
- [ ] PIT for the touched classes shows no new surviving mutation
- [ ] Regenerated files (`src/site/xdoc`, module metadata) are in the commit
- [ ] One code commit with a message in the `AGENTS.md` format; no notes in it
- [ ] Draft pull request is open and its link is in the table above
- [ ] Skills and documents are updated and "Learned" below is filled in
- [ ] This file is moved to `done/` and `STATUS.md` is updated

## Learned

Nothing yet.
