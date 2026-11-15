# Unit 5.1 - Differences on real projects

| Field | Value |
|---|---|
| Phase | 5 - Verification at scale |
| Kind | scripts and CI |
| Depends on | unit 4.6 |
| Issue | to be written |
| Estimate | 5.5 hours in 4 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

Runs with one and several threads give identical reports on real projects.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T5.1.1 (2 h) Script in `.ci`: run a project with 1 and n threads, compare reports. Proof:
  shellcheck.
- [ ] T5.1.2 (1 h) Run it on Guava. Proof: zero difference.
- [ ] T5.1.3 (1 h) Run it on one more project of the `checkstyle-tester` list. Proof: zero
  difference.
- [ ] T5.1.4 (1.5 h) CI job that runs the script on pull requests that touch `Checker`. Proof: job
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
