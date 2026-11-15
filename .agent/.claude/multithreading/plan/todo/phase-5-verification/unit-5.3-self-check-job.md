# Unit 5.3 - Self-check

| Field | Value |
|---|---|
| Phase | 5 - Verification at scale |
| Kind | scripts and CI |
| Depends on | unit 5.1 |
| Issue | to be written |
| Estimate | 1.5 hours in 1 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

CI checks Checkstyle with several threads and still with one.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T5.3.1 (1.5 h) Second self-check job with several threads; the existing job stays. Proof: job
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
