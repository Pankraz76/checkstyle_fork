# Unit 1.6 - F7: parser caches under concurrent parsing

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | only if T1.6.3 is needed |
| Estimate | 4.5 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

It is proven that concurrent parsing gives the same trees (finding F7).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T1.6.1 (1 h) Read ANTLR 4.13.2 sources on `clearDFA` and DFA sharing; record it. Proof: note.
- [ ] T1.6.2 (2 h) Test: parse 200 inputs on 4 threads, AST dumps equal the sequential ones. Proof:
  S05.
- [ ] T1.6.3 (1.5 h) Only if T1.6.2 fails or the contract asks for it: guard the clearing. Proof:
  S05 green.

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
