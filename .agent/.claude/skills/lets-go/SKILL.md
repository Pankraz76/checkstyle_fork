---
name: lets-go
description: >-
  Continue a planned topic from where it stands. Use when the user says "let's go", "weiter",
  "continue", "mach weiter", "next task", "where are we" or asks for the status of a topic
  that has a STATUS.md under .agent/.claude (for example .agent/.claude/multithreading).
  Finds the current position in the plan folders, claims the next unit and executes its next
  open task.
---

# Let's go

One command to resume a long-running topic. The plan is the file system; this skill reads it,
does the next small step and leaves it in a correct state.

## Find the position

- Topics are the folders under `.agent/.claude/` that contain a `STATUS.md`. If there is more than
  one and the user named none, ask which.
- Read `STATUS.md`, then list `plan/todo` and `plan/done`. If they disagree with `STATUS.md`,
  the folders are right: correct `STATUS.md` first and say so.
- Read `05-findings.md` and `06-open-questions.md` of the topic. They hold the pitfalls that
  already cost time and the answers that are still missing.
- If the current branch has a claim, that unit is the position. Otherwise the position is the
  lowest unit in `plan/todo` whose "Depends on" entries are all in `plan/done`, that has no
  claim of another branch, and that does not wait for a person.
- A unit waits for a person when its next open task needs the owner or a maintainer (posting
  upstream, an `approved` label, an answer). Say what is needed, then take the next unit.
- If nothing can start, say exactly what blocks and stop.

## Report before working

Tell the user in a few lines: the topic, what is finished, the unit and task that come next,
what that task will prove, and what is blocked and by whom. Then start; do not wait for a
confirmation unless the task changes something outside this repository.

## Work

- Claim the unit: add a row to "Claims" in `STATUS.md` and fill "Claimed by" and "Branch" in
  the unit file.
- Load the topic skill (for example `multithreading`) and `large-change-workflow`, and follow
  them. Everything is behaviour-first and test-first.
- Do exactly one task: the first one without a tick. Tick it only when its proof exists, and
  write the proof next to it if it is a link or a number.
- A task that grows beyond two hours is split in the unit file before work continues.
- If the unit file and the design do not answer a question, stop and ask. Record the question
  in `06-open-questions.md`. Do not guess.
- Continue with the next task of the same unit until the unit is finished, the session ends or
  a stop condition of the workflow skill applies.

## Finish a unit

- Tick the unit gate items one by one, each with real output.
- Fill in "Learned" and carry each entry to where it belongs: `05-findings.md`,
  `04-decisions.md`, `06-open-questions.md`, `07-glossary.md` or a skill.
- Move the unit file to the same phase folder under `plan/done` with `git mv`.
- In `STATUS.md`: tick the unit, update the count of its phase, update "Now", remove the claim
  and add a line to "Log".
- When the last unit of a phase is done, tick the phase gate in the phase `README.md`, move
  that file to `plan/done` as well and tick the phase in `STATUS.md`.

## Leave a clean state

Whenever a session ends, also in the middle of a unit:

- Ticks match reality: nothing is ticked that is not proven, nothing proven is left open.
- The unit file says in one line under "Learned" where the work stopped and what comes next.
- The claim stays if the unit is unfinished, so the next session continues it.
- Notes are committed and pushed. Code that is not green is not pushed.
