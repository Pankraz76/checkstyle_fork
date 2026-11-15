# Unit 6.3 - Ant task (upstream issue 4577)

| Field | Value |
|---|---|
| Phase | 6 - Exposure |
| Kind | new code |
| Depends on | unit 6.1 |
| Issue | upstream 4577 |
| Estimate | 2.5 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Users can set the number of threads in the Ant task.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T6.3.1 (1.5 h) Attribute in `CheckstyleAntTask`. Proof: `CheckstyleAntTaskTest`.
- [ ] T6.3.2 (1 h) Ant task page. Proof: `XdocsPagesTest`.

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
