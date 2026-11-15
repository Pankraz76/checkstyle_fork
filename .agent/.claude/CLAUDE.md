# Working notes of this fork

Read `AGENTS.md` in the repository root before you build, test or commit anything. It holds
the build commands, the commit message rules and the rules for changing checks. Since this
file exists, `AGENTS.md` is not loaded on its own any more, so reading it is a required first
step, not an option.

This file only points to the knowledge that earlier sessions left behind.

- Long-running topics live in `.agent/.claude/<topic>/`. Each has a `STATUS.md` that says
  where the work stands. Current topic: `.agent/.claude/multithreading/`.
- To continue a topic, use the skill `lets-go` ("let's go", "weiter").
- At the start of a session on a topic read its `README.md`, `STATUS.md`, `05-findings.md` and
  `06-open-questions.md`.
- At the end of a session record what is reusable there, tick what was proven and leave
  `STATUS.md` correct.
- Nothing under `.agent/.claude/` is sent upstream.
