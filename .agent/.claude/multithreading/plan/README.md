# Plan

Feature: opt-in multi-threaded file processing in `Checker` with results identical to a
single-threaded run (`../03-design.md`, invariants I1 to I6).

## Structure

```text
plan/
  README.md                      this file
  todo/
    phase-N-name/
      README.md                  outcome, units and gate of the phase
      unit-N.M-name.md           one unit: goal, tasks, gate, learned
  done/
    phase-N-name/                finished units and finished phase files
```

- A unit is one issue, one pull request and one code commit. A task is one atomic step of at
  most two hours that ends with a green build.
- The state of the work is the file system: a unit is open while its file is under `todo/` and
  finished when it is under `done/`. Inside a unit file every task and every gate item has a
  box that is ticked when its proof exists.
- `../STATUS.md` is the summary that is read first. The folders win if they disagree.
- Scenario ids (S01 ...) refer to `../08-scenarios.md`.

## How to continue

Say "let's go". The skill `lets-go` finds the position and continues. By hand it is:

- Read `../STATUS.md`, then take the lowest unit in `todo/` whose dependencies are in `done/`
  and that nobody has claimed.
- Claim it in `../STATUS.md` and in the unit file.
- Do the first task without a tick, test-first, and tick it when its proof exists.
- When all tasks and the gate are ticked, move the file to `done/` with `git mv`, update
  `../STATUS.md` and release the claim.

## Phases

| Phase | Outcome | Production code | Units | Tasks | Hours |
|---|---|---|---|---|---|
| A | Verdict, design and plan | none | 1 | 6 | done |
| 0 | Design agreed with maintainers | none | 1 | 6 | 6 |
| 1 | Existing order and state defects fixed | small fixes | 7 | 22 | 24 |
| 2 | Safety net exists | tests only | 3 | 9 | 13.5 |
| 3 | `Checker` prepared | refactoring only | 3 | 8 | 10 |
| 4 | Parallel engine, not reachable by users | new code | 6 | 25 | 34.5 |
| 5 | Verified on real projects, numbers published | scripts, CI | 3 | 9 | 13 |
| 6 | Users can switch it on | property, CLI, Ant, docs | 5 | 11 | 16.5 |

Add about 1.5 hours of unit gate for each of the 28 open units, and review time that cannot
be planned. Tasks of phases 3 to 6 are estimates; they are re-cut at each phase gate.

## Order and parallel work

- Phases 1 and 2 are useful without the feature and may start before phase 0 is closed.
- Phases 3 to 6 need the phase 0 gate: no engine code before the design is agreed.
- Units of one phase that do not depend on each other may be worked on in parallel, each in
  its own session and on its own branch, each with its own claim.
- Two sessions never work on the same unit. A session that finds a claim of another branch
  picks a different unit or stops.

## Before the first unit

This fork's `master` is at `9d82118` (2026-10-24) and behind upstream. Synchronise it first;
upstream has since added an ArchUnit rule for `beginTree` (issue 22175). Unit 0.1 starts with
that.

## Outside this repository

- `maven-checkstyle-plugin` and the Gradle plugin pick the property up through the
  configuration file. Dedicated parameters are their decision.
- IDE plugins usually check one file at a time and gain nothing.
