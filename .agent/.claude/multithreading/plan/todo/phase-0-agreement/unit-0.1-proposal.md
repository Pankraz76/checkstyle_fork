# Unit 0.1 - Proposal on upstream issue 4409

| Field | Value |
|---|---|
| Phase | 0 - Agreement |
| Kind | coordination |
| Depends on | nothing |
| Issue | upstream 4409, text in `.agent/.claude/multithreading/issues/proposal.md` |
| Estimate | 6 hours in 6 tasks, plus the unit gate |
| Claimed by | nobody |
| Branch | none |
| Pull request | none |

## Goal

The maintainers agree to the invariants and answer decisions D1 to D6 of the design.

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [ ] T0.1.1 (0.5 h) Synchronise fork `master` with upstream, rebase this branch. Proof: `git log`.
- [ ] T0.1.2 (1.5 h) Re-apply `spike/spike.patch`, re-run corpus A of `01-analysis.md`. Proof: same
  hashes.
- [ ] T0.1.3 (1 h) Re-run the F1 and F2 reproductions with the latest release jar. Proof: CLI
  output.
- [ ] T0.1.4 (0.5 h) Owner reviews `issues/proposal.md` and posts it on issue 4409. Proof: link.
- [ ] T0.1.5 (1 h) Write the maintainers' answers to D1 to D6 into `03-design.md`. Proof: diff.
- [ ] T0.1.6 (1.5 h) Re-cut phases 1 to 6 where an answer changes the design. Proof: diff of this
  file.

## Unit gate

- [ ] Skills and documents are updated and "Learned" below is filled in
- [ ] This file is moved to `done/` and `STATUS.md` is updated

## Learned

Nothing yet.
