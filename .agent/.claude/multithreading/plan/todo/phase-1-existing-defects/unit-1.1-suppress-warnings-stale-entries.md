# Unit 1.1 - F1: suppression entries outlive their file

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | draft `issues/suppress-warnings-stale-entries.md` of the topic, not filed |
| Estimate | 5 hours in 5 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Suppression entries collected in one file never apply to another file (finding F1).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T1.1.1 (0.5 h) File `issues/suppress-warnings-stale-entries.md`, wait for `approved`. Proof:
  link.
- [ ] T1.1.2 (1 h) Failing test: Java file, then text file with a violation. Proof: S01 red.
- [ ] T1.1.3 (0.5 h) Agree in the issue where entries are dropped (holder or filter). Proof:
  comment.
- [ ] T1.1.4 (1.5 h) Minimal fix. Proof: S01 green.
- [ ] T1.1.5 (1.5 h) Failing test, then fix: Java file that fails to parse and is skipped. Proof:
  S02 green.

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
