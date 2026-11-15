# Unit 3.3 - Module creation that can be repeated

| Field | Value |
|---|---|
| Phase | 3 - Prepare Checker |
| Kind | refactoring |
| Depends on | unit 3.2 |
| Issue | to be written |
| Estimate | 4 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

`Checker` can create a further instance of a FileSetCheck and knows where it may run.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T3.3.1 (1 h) `Checker` keeps the `Configuration` of each FileSetCheck child. Proof: new test.
- [ ] T3.3.2 (1.5 h) One private method creates a FileSetCheck from a `Configuration`. Proof: suite
  green.
- [ ] T3.3.3 (1.5 h) Predicate "stays on the calling thread" (global marker or no marker). Proof:
  S08.

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
