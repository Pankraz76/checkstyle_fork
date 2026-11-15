# Unit 1.2 - F2: order of `TranslationCheck` reports

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | draft `issues/translation-check-order.md` of the topic, not filed |
| Estimate | 3 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

`TranslationCheck` reports in an order defined by its input (finding F2).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T1.2.1 (0.5 h) Read what pull 8967 changed (issue 8963), then file
  `issues/translation-check-order.md` with that reference, wait for `approved`. Proof: link.
- [ ] T1.2.2 (1.5 h) Failing test: 20 bundles, reports must follow input order. Proof: S03 red.
- [ ] T1.2.3 (1 h) Keep insertion order for bundles and files. Proof: S03 green.

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
