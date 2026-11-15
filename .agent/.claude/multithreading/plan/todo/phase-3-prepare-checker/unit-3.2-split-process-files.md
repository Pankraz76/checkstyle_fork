# Unit 3.2 - Split `processFiles` into select, execute, report

| Field | Value |
|---|---|
| Phase | 3 - Prepare Checker |
| Kind | refactoring |
| Depends on | unit 3.1 |
| Issue | to be written |
| Estimate | 4 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

`Checker.processFiles` consists of three separate steps: select, execute, report.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T3.2.1 (1.5 h) Extract "should this file be processed" (cache, file filters). Proof: suite
  green.
- [ ] T3.2.2 (1.5 h) Extract "report one file" (started, errors, finished). Proof: suite green.
- [ ] T3.2.3 (1 h) Extract the wrapping of `Exception` and `Error` with the file name. Proof: suite
  green.

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
