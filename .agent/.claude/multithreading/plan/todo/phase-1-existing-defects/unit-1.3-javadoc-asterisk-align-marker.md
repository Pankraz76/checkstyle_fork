# Unit 1.3 - F3: marker of `JavadocLeadingAsteriskAlignCheck`

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | to be written |
| Estimate | 3 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

`JavadocLeadingAsteriskAlignCheck` carries the file-stateful marker (finding F3).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T1.3.1 (0.5 h) Issue: the check is not multi-file, marker should be file-stateful. Proof:
  link.
- [ ] T1.3.2 (1.5 h) Test that per-file fields are reset between two files. Proof: S04.
- [ ] T1.3.3 (1 h) Change the marker, run `ImmutabilityTest` and `ArchUnitTest`. Proof: tests green.

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
