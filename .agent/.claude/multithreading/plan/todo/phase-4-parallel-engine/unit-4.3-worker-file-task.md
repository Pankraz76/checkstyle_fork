# Unit 4.3 - Work done by a worker

| Field | Value |
|---|---|
| Phase | 4 - Parallel engine |
| Kind | new code |
| Depends on | unit 4.2 |
| Issue | to be written |
| Estimate | 5.5 hours in 4 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

A worker processes one file exactly as `processFile` does today.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T4.3.1 (1.5 h) Read the file, run the tree, return text and violations. Proof: S16.
- [ ] T4.3.2 (1 h) Unreadable file gives the same violation as today. Proof: S17.
- [ ] T4.3.3 (1.5 h) `haltOnException` off: exception violation plus index of the module. Proof:
  S18.
- [ ] T4.3.4 (1.5 h) `haltOnException` on: same exception chain as today. Proof: S19.

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
