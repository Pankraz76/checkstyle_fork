# Unit 2.1 - Recording listener

| Field | Value |
|---|---|
| Phase | 2 - Safety net |
| Kind | tests only |
| Depends on | nothing |
| Issue | to be written |
| Estimate | 2.5 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Tests can record all listener events of a run and compare two runs.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T2.1.1 (1.5 h) Test listener that records every event as a comparable value. Proof: own unit
  test.
- [ ] T2.1.2 (1 h) Helper: run a configuration over files, return the recorded events. Proof: own
  unit test.

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
