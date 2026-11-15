# Unit 6.4 - Contract for check authors

| Field | Value |
|---|---|
| Phase | 6 - Exposure |
| Kind | docs |
| Depends on | unit 6.1 |
| Issue | to be written |
| Estimate | 3 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Marker Javadoc and "Writing checks" describe what is implemented.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T6.4.1 (1.5 h) Rewrite the Javadoc of the three markers to what is implemented. Proof: review.
- [ ] T6.4.2 (1.5 h) Section in "Writing checks": state and markers. Proof: `XdocsPagesTest`.

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
