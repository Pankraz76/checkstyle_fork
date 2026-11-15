# Unit 2.3 - Static state guard

| Field | Value |
|---|---|
| Phase | 2 - Safety net |
| Kind | tests only |
| Depends on | nothing |
| Issue | to be written |
| Estimate | 3 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

A rule stops new mutable static state from entering modules.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T2.3.1 (1 h) Read `ImmutabilityTest` and `ArchUnitTest`, note what they already forbid. Proof:
  note.
- [ ] T2.3.2 (2 h) ArchUnit rule: no mutable static field in modules, known cases frozen. Proof:
  rule green.

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
