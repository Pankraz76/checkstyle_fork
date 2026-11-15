---
name: multithreading
description: >-
  Rules and entry point for any work on multi-threaded file processing in Checkstyle (Checker,
  TreeWalker, StatelessCheck, FileStatefulCheck, GlobalStatefulCheck, ThreadModeSettings,
  thread safety of checks, filters, listeners or the cache). Use it before touching these
  areas or answering questions about them.
---

# Multi-threading in Checkstyle

The topic folder is `.agent/.claude/multithreading/`. Read its `README.md` first (verdict and
reading order), then `STATUS.md` (where the work stands). Then follow the skill
`large-change-workflow` for execution.

## Hard rules

- Work only on a unit that `STATUS.md` and the folder `plan/todo` of the topic allow
  (dependencies done, no claim of another branch). Do not skip phases.
- The single-threaded path must stay byte-for-byte what it is today. One thread means the old
  code path, not the new one with a pool of size one.
- The equivalence contract in `03-design.md` (invariants I1 to I6) is checked by a differential
  test in every unit from phase 2 on. A difference is a bug in the unit, never an accepted
  deviation, unless `03-design.md` lists it.
- Do not make checks, filters or listeners thread-safe to solve a problem. Confine them to one
  thread instead. Adding `synchronized`, `volatile`, `ThreadLocal` or concurrent collections
  to a module needs a written reason in the pull request.
- Do not clone modules. Create another instance from its `Configuration` or reuse the one
  instance (the maintainers' "create or reuse" rule).
- Do not parallelise `visitToken` calls or anything inside one file. That design was rejected
  upstream.
- No public API change without an approved issue.

## What is already known

- It is feasible. A prototype produced byte-identical XML reports on 1,275 files with all 247
  modules enabled, see `01-analysis.md`.
- Naive sharing of module instances between threads fails within a second.
- Three existing defects make results depend on file order or JVM internals even with one
  thread. They are fixed first (phase 1).
- The maintainers stopped in 2019 because they did not agree on a design, not because it is
  impossible, see `02-upstream-history.md`.

## How the work is done

Behaviour-first and test-first, always: scenario in `08-scenarios.md`, failing test, minimal
change, refactoring on green. From phase 4 on, every unit also runs the differential scenarios
(one thread against several) before it is done.

## Keep this folder current

- If the code no longer matches `01-analysis.md` (the audit was made at commit `9d82118`),
  re-verify the affected statement, correct the document in the same unit and say so in the
  pull request.
- A new fact about the code or a measurement goes into `01-analysis.md`, a maintainer
  statement into `02-upstream-history.md`, a decision into `04-decisions.md`, in the same unit
  and with its source.
- A rule in this skill that proved wrong or incomplete is corrected here, with the reason.
- `STATUS.md` and the boxes in the unit files under `plan/` always show the real state.

## Before and after a session

- At the start read `README.md`, `STATUS.md`, `05-findings.md` and `06-open-questions.md` of
  the topic folder. They hold what earlier sessions learned and what is still unanswered.
- At the end record what is reusable: a technical learning in `05-findings.md`, a decision in
  `04-decisions.md`, a question for a person in `06-open-questions.md`, a new term in
  `07-glossary.md`.
- Never contradict an earlier decision silently. If you disagree, write the disagreement into
  `06-open-questions.md` and keep following the decision until it is changed.
