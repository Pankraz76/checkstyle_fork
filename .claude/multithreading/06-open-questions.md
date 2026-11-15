# Open questions

Everything that needs a person. A question is answered by writing the answer and its source
below it and moving it to "Answered"; decisions that follow go into `04-decisions.md`.

## For the maintainers

Asked in `issues/proposal.md`. They map to the open decisions in `04-decisions.md`.

| Id | Question | Decision |
|---|---|---|
| Q1 | Property plus command line option for the number of threads? | D1 |
| Q2 | Reuse or remove the `ThreadModeSettings` plumbing? | D1 |
| Q3 | FileSetChecks without a marker stay single instances on the calling thread? | D2 |
| Q4 | Hand-over of `SuppressWarningsHolder` entries as an internal mechanism? | D3 |
| Q5 | A FileSetCheck that reports from `finishProcessing` must be marked global? | D5 |
| Q6 | Are file-stateful checks with a reset in `beginTree` as welcome as stateless ones? | none |

Q6 matters beyond this feature: the review on pull 22133 asked for a stateless check because
of future multi-threading. With one instance for each worker that reason no longer holds.

## For the fork owner

| Id | Question | Why it matters |
|---|---|---|
| O1 | Post the proposal on issue 4409 as it is, or shorten it first? | starts phase 0 |
| O2 | File the two defect drafts now? | units 1.1 and 1.2 wait for `approved` |
| O3 | Is the procedure of decision A6 (notes separate from code) how you work? | every unit |
| O4 | Stay with `.claude/` or move this folder to another name? | skill discovery needs it |
| O5 | Should fork `master` be synchronised with upstream before unit 0.1? | task T0.1.1 |
| O6 | Where should code commits go when a session may push to one branch only? | see below |

O6: the session of 2026-11-15 could push only to the notes branch. The test of unit 1.6 is
therefore a separate `minor:` commit on that branch, next to the `doc:` commits. For upstream
it has to be cherry-picked onto a branch cut from upstream `master`. If sessions should
create such branches themselves, they need the permission to push to them.

## Not verified

| Id | What | Where it is planned |
|---|---|---|
| V1 | Behaviour on large external projects | unit 5.1 |
| V2 | More than four cores | unit 5.2 |
| V3 | Memory use with several module trees | unit 5.2 |
| V4 | Third-party checks and filters | decision D2, not planned |
| V6 | Both defect reproductions against the latest release | task T0.1.3 |
| V7 | Whether upstream's spell check accepts this folder | not planned, fork only |

## Answered

| Id | What | Answer | Source |
|---|---|---|---|
| V5 | ANTLR contract for clearing the shared DFA cache | performance only, not accuracy | F7 |

The source for V5 is `01-analysis.md`, section "F7 in detail", read from the ANTLR 4.13.2
sources on 2026-11-15.
