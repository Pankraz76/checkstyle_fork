# .agent/.claude

Working notes, skills and plans for AI-assisted work on this fork. Nothing in this folder is
part of the Checkstyle product and nothing here is meant to be sent upstream. The rules for
building, testing and committing stay in `AGENTS.md`; this folder only adds topic knowledge
and the way large topics are executed.

The repository root has a symbolic link `.claude` that points to this folder, because Claude
Code loads skills and `CLAUDE.md` only from there (decision A8 of the multi-threading topic).

## Layout

| Path | Purpose |
|---|---|
| `CLAUDE.md` | Loaded in every session: where the knowledge is and how to continue |
| `skills/lets-go/SKILL.md` | "Let's go": find the position in a plan and continue |
| `skills/large-change-workflow/SKILL.md` | How a large topic is analysed, cut and executed |
| `skills/multithreading/SKILL.md` | Hard rules for the multi-threading topic |
| `multithreading/` | The topic: knowledge base, plan, status |

## Layout of a topic

| Path | Purpose |
|---|---|
| `README.md` | Verdict and reading order |
| `STATUS.md` | Where the work stands: what can start, claims, phases, units, log |
| `01-analysis.md` | Facts about the code and measured evidence |
| `02-upstream-history.md` | Upstream issues, pull requests and maintainer positions |
| `03-design.md` | Target design, invariants, risks |
| `04-decisions.md` | Decision log: taken, open, rejected |
| `05-findings.md` | Reusable learnings on how to work |
| `06-open-questions.md` | Everything that needs a person, and what is not verified |
| `07-glossary.md` | Terms |
| `08-scenarios.md` | Behaviour scenarios (Given, When, Then) with ids |
| `09-prompts.md` | Hardened prompts for new sessions |
| `plan/todo/`, `plan/done/` | One file for each unit, tasks and gates with boxes |
| `issues/` | Issue texts that are ready to be filed |
| `spike/` | Throwaway prototype that produced the evidence |

## Rules for this folder

- Every statement about the code names a file, and every measured number names the command
  that produced it. Mark anything that was not verified as "not verified".
- Keep documents short enough to be read before a task starts: no file above roughly 400
  lines. Put raw evidence in `01-analysis.md` or `spike/`, not in the plan.
- The plan is the file system. Tick boxes, move unit files to `done/` and update `STATUS.md`
  in the same step as the work.
- Skills and documents are maintained by whoever works with them: wrong or outdated content is
  corrected and new findings are added in the same unit, see "Keep this knowledge current" in
  the workflow skill.
- Markdown here must pass `mdl -g .` with `config/markdownlint.rb` (line length 100).
