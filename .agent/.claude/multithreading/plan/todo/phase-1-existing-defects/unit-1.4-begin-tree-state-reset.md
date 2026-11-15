# Unit 1.4 - F4: state reset in file-stateful checks (upstream issue 22175)

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | upstream 22175 |
| Estimate | 3.5 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Every file-stateful check resets its per-file state in `beginTree` (finding F4).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T1.4.1 (0.5 h) List checks still suppressed in the `beginTree` ArchUnit rule. Proof: list in
  issue.
- [ ] T1.4.2 (1.5 h) For one unclaimed check: failing `testClearState`, then `beginTree`. Proof:
  test green.
- [ ] T1.4.3 (1.5 h) Repeat T1.4.2 as its own unit for each further unclaimed check. Proof: test
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
