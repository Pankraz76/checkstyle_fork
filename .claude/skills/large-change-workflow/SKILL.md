---
name: large-change-workflow
description: >-
  How to run any large, multi-week change in this repository (new subsystem, cross-cutting
  refactoring, performance work). Use it before planning or executing work that does not fit
  into one pull request. It defines the Feature, Phase, Unit, Task structure, the size limits
  and the gates.
---

# Large change workflow

A large topic is never implemented in one go. It is analysed first, cut into very small pieces,
and every piece is merged on its own. This file describes the generic procedure. Topic
knowledge lives next to it, for example in `.claude/multithreading/`.

## Vocabulary

| Level | Meaning | Size limit |
|---|---|---|
| Feature | The whole topic, with one measurable goal | none |
| Phase | A milestone with one gate; later phases depend on it | 3 to 8 units |
| Unit | One upstream issue, one pull request, one commit | 1 to 4 tasks |
| Task | One atomic step that ends in a green build | 1 to 2 hours |

A task that cannot be finished in two hours is not a task yet. Split it before starting.

## Step A - Analyse before planning

- Restate the real goal, the constraints and what must not break.
- Read the code paths involved. Record facts with file names, not impressions.
- Read the upstream history (issues, closed pull requests, maintainer comments). A design that
  was rejected before must not be proposed again without new evidence.
- State every assumption and try to falsify it. Prefer a throwaway spike with a differential
  test (old behaviour against new behaviour on real input) over reasoning.
- Write down what was verified, what was refuted and what is still not verified.

## Step B - Decide the invariants

- Write the properties that must hold after every unit (for example "output is identical").
- Write how each invariant is tested automatically. An invariant without a test is a wish.
- List the decisions that belong to maintainers and get them agreed before writing code.

## Step C - Cut into phases, units and tasks

- Order phases so that each one is valuable and safe on its own: fixes for existing defects
  first, then the safety net (tests), then refactoring without behaviour change, then the new
  behaviour behind an opt-in switch, then verification at scale, then exposure and docs.
- A unit changes one thing. Refactoring and behaviour change never share a unit.
- Every task names: goal, files, the failing test written first, the command that proves it is
  done, and the tasks it depends on.
- No task may leave the build red. No task may need a later task to make sense.

## Step C2 - Put the plan into the file system

The plan is not one document. It is a folder that shows the state at a glance and that any
session can continue from.

- `<topic>/README.md`: verdict and reading order.
- `<topic>/01-analysis.md` to `09-prompts.md`: the knowledge base, numbered in reading order:
  analysis, upstream history, design, decisions, findings, open questions, glossary,
  scenarios, prompts. `.claude/README.md` lists what each one holds.
- `<topic>/STATUS.md`: read first, updated last. Holds what can start, the claims, a box for
  each phase and each unit, and a log.
- `<topic>/plan/todo/phase-N-name/unit-N.M-name.md`: one file for each unit with its goal,
  dependencies, a box for each task, a box for each gate item and a "Learned" section.
- `<topic>/plan/todo/phase-N-name/README.md`: outcome and gate of the phase.
- `<topic>/plan/done/...`: the same files after they are finished, moved with `git mv`.
- A task box is ticked only when the proof named next to it exists.
- Before work starts a unit is claimed in `STATUS.md` (unit, branch, date). Two sessions never
  work on one unit. Independent units may run in parallel on separate branches.
- The skill `lets-go` implements "find the position and continue" on this structure.

## Notes and code never share a commit

This folder exists only on the fork. Upstream pull requests must contain exactly one commit
with code and nothing from `.claude/`.

- Code of a unit is one commit. Ticks, status and learned notes are separate `doc:` commits.
- The branch for an upstream pull request is cut from upstream `master` and receives only the
  code commit (cherry-pick). Notes commits go to the fork's `master` through a fork pull
  request.
- This procedure is a proposal of the first analysis session. If the owner works differently,
  write the real procedure here.

## Step D - Execute one task at a time

Everything is done behaviour-first and test-first. There is no task type that is exempt
except pure documentation, and then the existing checks are run instead.

- Work on one task at a time, in the order of the unit file, and tick it there when done.
- BDD: write the scenario (Given, When, Then) before any code. It names the behaviour, not the
  implementation, and goes into the topic's scenario file with an id.
- TDD, RED: turn the scenario into one test in the existing JUnit and Truth style. Run it and
  show the failing output. It must fail for the right reason: a missing behaviour, not a
  compile error or a broken setup.
- TDD, GREEN: write only the code this test needs. Run the test, then the affected suite.
- TDD, REFACTOR: clean up on green only, and re-run after each step.
- A defect is reproduced by a failing test before it is fixed.
- Never report a test as passed without having run it. Quote the real output.
- Use the narrow commands from `AGENTS.md` while iterating; start with `clean`.
- After the last task of a unit run the unit gate below.

## Unit gate (all must pass before the pull request)

- The targeted tests of the unit and the meta tests it touches (`AllChecksTest`,
  `XdocsPagesTest`, `ArchUnitTest`, `ImmutabilityTest`, ...).
- Self-check: `./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify`.
- `./mvnw -e --no-transfer-progress clean verify`.
- PIT for the touched classes: no new surviving mutation.
- Regenerated files (`src/site/xdoc`, module metadata) are part of the commit.
- One commit, message format from `AGENTS.md`, issue labelled `approved` for defects and
  behaviour changes.

## Phase gate

- Every unit file of the phase is under `plan/done` and its pull request is merged.
- The invariants of Step B are re-checked on real projects, not only on unit tests.
- The gate boxes in the phase `README.md` are ticked, the file is moved to `plan/done`,
  `STATUS.md` is updated, and the next phase is re-cut with what was learned. Tasks of later
  phases are estimates until then.

## Keep this knowledge current

These skills and the topic documents are working material. Whoever executes a task also
maintains them, in the same unit, without being asked.

- Something in a skill or document turned out wrong or outdated: correct it, and say in the
  report what was wrong and how it was noticed.
- A step in this workflow did not work as described, or a better way was found: change the
  workflow here, with one sentence on why.
- New facts go where they belong, with the source: a fact about the code or a measurement
  into the analysis, a decision into the decision log, a pitfall that cost time into the
  findings, a question for a person into the open questions, a new term into the glossary.
- An earlier decision is never contradicted silently. Disagreement is written into the open
  questions, and the decision is followed until it is changed in the decision log.
- A plan task was too large, missing or in the wrong order: fix the plan before continuing.
- Something was learned that applies to every topic, not only this one: put it into this
  file, not into the topic folder. Build and commit rules belong in `AGENTS.md`.
- Never rewrite history to look better: an estimate that was wrong stays visible in the
  report, and a refuted assumption is recorded as refuted.
- End every report with a short "Learned" list. An empty list is allowed, a missing one is not.

## Stop conditions

- A task grows beyond two hours: stop, split, update the plan.
- An invariant test fails and the cause is not understood within one task: stop and write the
  finding into the topic analysis before changing more code.
- A maintainer decision is missing: do not guess, ask in the issue.
- The unit file or the design does not answer a question: stop and ask, and record the
  question in the open questions. Say "unclear" rather than inventing behaviour.
