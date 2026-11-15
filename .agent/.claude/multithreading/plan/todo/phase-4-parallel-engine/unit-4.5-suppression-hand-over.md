# Unit 4.5 - Suppression hand-over (decision D3)

| Field | Value |
|---|---|
| Phase | 4 - Parallel engine |
| Kind | new code |
| Depends on | units 4.4 and 1.1 |
| Issue | to be written |
| Estimate | 2.5 hours in 2 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

`SuppressWarningsFilter` works when the holder ran on a worker (decision D3).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T4.5.1 (1 h) Failing differential test with `@SuppressWarnings`. Proof: S24 red.
- [ ] T4.5.2 (1.5 h) Hand the holder entries over with the result. Proof: S24 green.

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
