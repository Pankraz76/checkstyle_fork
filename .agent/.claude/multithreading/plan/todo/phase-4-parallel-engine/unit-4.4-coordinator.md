# Unit 4.4 - Work done by the coordinator

| Field | Value |
|---|---|
| Phase | 4 - Parallel engine |
| Kind | new code |
| Depends on | units 4.1 and 4.3 |
| Issue | to be written |
| Estimate | 5.5 hours in 4 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

The calling thread reports results in input order with global modules and cache.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T4.4.1 (1.5 h) Global modules get the worker's `FileText`, in input order. Proof: S20.
- [ ] T4.4.2 (1 h) After a failure only modules before the failing one run (D6). Proof: S21.
- [ ] T4.4.3 (2 h) Cache: same entries as today, also when the run aborts. Proof: S22.
- [ ] T4.4.4 (1 h) `Error` path: same wrapping and cache clean-up as today. Proof: S23.

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
