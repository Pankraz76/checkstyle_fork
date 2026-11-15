# Unit 6.5 - Dead plumbing (finding F9)

| Field | Value |
|---|---|
| Phase | 6 - Exposure |
| Kind | refactoring |
| Depends on | unit 6.1 |
| Issue | to be written |
| Estimate | 2.5 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

The unused thread-mode plumbing of 2017 is reused or deprecated (finding F9).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T6.5.1 (0.5 h) Issue: reuse or deprecate `ThreadModeSettings` and its accessors. Proof:
  decision.
- [ ] T6.5.2 (2 h) Implement the decision. Proof: suite green.

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
