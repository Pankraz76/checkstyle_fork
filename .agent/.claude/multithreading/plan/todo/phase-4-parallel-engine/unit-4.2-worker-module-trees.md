# Unit 4.2 - Module trees for workers

| Field | Value |
|---|---|
| Phase | 4 - Parallel engine |
| Kind | new code |
| Depends on | unit 3.3 |
| Issue | to be written |
| Estimate | 4.5 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Each worker owns one module tree for its whole life.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T4.2.1 (1.5 h) Create n trees on the calling thread from the kept configurations. Proof: S13.
- [ ] T4.2.2 (1 h) Each worker takes one tree and never another. Proof: S14.
- [ ] T4.2.3 (2 h) `finishProcessing` and `destroy` run on the owning worker, one at a time. Proof:
  S15.

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
