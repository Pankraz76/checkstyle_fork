# Unit 6.2 - Command line

| Field | Value |
|---|---|
| Phase | 6 - Exposure |
| Kind | new code |
| Depends on | unit 6.1 |
| Issue | to be written |
| Estimate | 4.5 hours in 3 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Users can set the number of threads on the command line.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T6.2.1 (2 h) Option in `Main`, overrides the property. Proof: `MainTest`.
- [ ] T6.2.2 (1.5 h) Suppression generation modes force one thread (finding F6). Proof: S31.
- [ ] T6.2.3 (1 h) Command line page. Proof: `XdocsPagesTest`.

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
