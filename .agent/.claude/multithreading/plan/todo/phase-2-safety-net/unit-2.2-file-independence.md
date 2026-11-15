# Unit 2.2 - File independence of all modules

| Field | Value |
|---|---|
| Phase | 2 - Safety net |
| Kind | tests only |
| Depends on | unit 2.1 |
| Issue | to be written |
| Estimate | 8 hours in 5 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

A test proves that the result for a file does not depend on the files before it.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T2.2.1 (1.5 h) Helper that builds the all-modules default configuration from metadata. Proof:
  own test.
- [ ] T2.2.2 (1 h) Helper that lists compilable test inputs, stable order. Proof: own test.
- [ ] T2.2.3 (2 h) Test: files in order and in reverse order give equal violations per file. Proof:
  S06.
- [ ] T2.2.4 (1.5 h) Test: one `Checker` for all files equals a fresh `Checker` per file. Proof:
  S07.
- [ ] T2.2.5 (2 h) Triage each failure into a new unit of phase 1; exclude global modules. Proof:
  list.

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
