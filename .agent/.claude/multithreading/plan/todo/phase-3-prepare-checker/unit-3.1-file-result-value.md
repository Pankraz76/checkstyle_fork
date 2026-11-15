# Unit 3.1 - Per-file result value

| Field | Value |
|---|---|
| Phase | 3 - Prepare Checker |
| Kind | refactoring |
| Depends on | phase 0 gate, unit 2.2 |
| Issue | to be written |
| Estimate | 2 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

`Checker.processFile` returns one value that holds everything known about a file.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T3.1.1 (1 h) `processFile` returns a private result value instead of a bare set. Proof: suite
  green.
- [ ] T3.1.2 (1 h) PIT for `Checker`: no new surviving mutation. Proof: pitest report.

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
