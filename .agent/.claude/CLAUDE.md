# Working notes of this fork

Build, test and commit rules are in `AGENTS.md`. This file only points to the knowledge that
earlier sessions left behind.

- Long-running topics live in `.agent/.claude/<topic>/`. Each has a `STATUS.md` that says
  where the work stands. Current topic: `.agent/.claude/multithreading/`.
- To continue a topic, use the skill `lets-go` ("let's go", "weiter").
- At the start of a session on a topic read its `README.md`, `STATUS.md`, `05-findings.md` and
  `06-open-questions.md`.
- At the end of a session record what is reusable there, tick what was proven and leave
  `STATUS.md` correct.
- Nothing under `.agent/.claude/` is sent upstream.
