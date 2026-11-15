# Unit 4.1 - Ordered window (no Checkstyle types)

| Field | Value |
|---|---|
| Phase | 4 - Parallel engine |
| Kind | new code |
| Depends on | phase 0 gate |
| Issue | to be written |
| Estimate | 6 hours in 4 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

A small utility runs tasks on workers and returns results in submission order.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T4.1.1 (1.5 h) Results come back in submission order. Proof: S09.
- [ ] T4.1.2 (1.5 h) The bound of decision D4 is respected. Proof: S10.
- [ ] T4.1.3 (1.5 h) First failure in order is rethrown, later work is cancelled. Proof: S11.
- [ ] T4.1.4 (1.5 h) Interrupt and shutdown leave no live thread. Proof: S12.

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
