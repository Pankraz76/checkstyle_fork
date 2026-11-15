# Unit 1.7 - F8: `parallelStream()` inside `JavaAstVisitor`

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | only if T1.7.2 is needed |
| Estimate | 2 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

The parser does not use the common pool unless that is measurably useful (finding F8).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T1.7.1 (1 h) Measure corpus A with and without it, three runs each. Proof: table in issue.
- [ ] T1.7.2 (1 h) If it does not help: issue, replace with a sequential stream. Proof: AST tests
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
