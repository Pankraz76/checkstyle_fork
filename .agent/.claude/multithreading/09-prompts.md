# Prompts

Copy a block as a whole. Each one is complete on its own.

## Prompt A - Feasibility analysis (used on 2026-11-15)

```text
ROLE
You are a senior Java engineer and Checkstyle contributor. You are sceptical by default: a
claim counts only if you read it in the code or measured it.

CONTEXT
Repository: this Checkstyle fork (Java 21, Maven wrapper, rules in AGENTS.md). Checkstyle is
single-threaded. Upstream tried multi-threading in 2017 to 2019 and stopped. A maintainer
recently asked for stateless checks "as eventually we will do multithread processing".

OBJECTIVE
Decide, with evidence, whether Checkstyle can process files on several threads without
changing results, and produce everything needed to execute that as a long-running topic.

CONSTRAINTS
- Do not change production code on the branch. A prototype is a throwaway spike: apply it in
  the working tree, measure, save it as a patch, restore the tree.
- Nothing may be sent upstream. Write issue texts as drafts.
- Correctness before speed. "Identical results" means identical listener events in identical
  order, identical exit code and identical cache file.
- Every statement names its source (file, command, issue number). Mark anything you could not
  verify as "not verified". Never present an assumption as a fact.
- Follow AGENTS.md for builds, commit message format and the single-commit rule.

APPROACH
1. Audit: read Checker, TreeWalker, the abstract check classes, the three state markers,
   ThreadModeSettings, Main, filters, listeners, the cache and the parser entry point. List
   every piece of mutable state that outlives one file or is static.
2. History: find every upstream issue and pull request about multi-threading and state
   markers. Extract what maintainers decided, in their words, and why the work stopped.
3. Hypothesis: derive the design that satisfies the maintainers' stated rules.
4. Falsify: build the spike and run a differential test (single-threaded report against
   multi-threaded report, byte for byte) on at least three configurations, on a configuration
   with every module enabled, with the cache, and with a failing file. Add a negative control
   (shared instances) so the test is known to detect breakage. Explain every difference.
5. Measure wall time for 1, 2 and 4 threads and say what limits the gain.
6. Plan: phases with gates, units of one issue and one pull request, tasks of at most two
   hours that each end green and start with a failing test.

DELIVERABLES
- .agent/.claude/ in the repository root: a workflow skill for large changes, a topic skill, and
  under .agent/.claude/multithreading: analysis, upstream history, design, plan, scenarios, issue
  drafts, prompts and the spike with instructions to reproduce it.
- One commit on the working branch and a draft pull request.

SUCCESS CRITERIA
- The verdict is unambiguous and backed by reproducible commands.
- Every difference found by the differential test is explained or fixed.
- The plan contains no task above two hours and no task that leaves the build red.
- The working tree contains no spike code, and the Markdown passes the repository lint.
```

## Prompt B - Execute one unit of the plan

Replace `<UNIT>` with a unit id from `plan/`, for example `1.2`. Without a unit id, simply say
"let's go": the skill `lets-go` picks the unit.

```text
ROLE
You are a senior Java engineer and Checkstyle contributor working test-first.

CONTEXT
Read first, in this order: AGENTS.md, .agent/.claude/skills/large-change-workflow/SKILL.md,
.agent/.claude/skills/multithreading/SKILL.md, then in .agent/.claude/multithreading:
README.md, STATUS.md, 05-findings.md and 06-open-questions.md, then the file of unit <UNIT>
under plan/todo and the parts of 03-design.md, 04-decisions.md and 08-scenarios.md it refers
to.

OBJECTIVE
Deliver unit <UNIT> of the plan as one code commit and one draft pull request.

CONSTRAINTS
- Only unit <UNIT>. Claim it first. If a task turns out larger than two hours, stop, split it
  in the unit file and tell me before continuing.
- Check STATUS.md: if something this unit depends on is not done, or another branch has
  claimed it, stop and say so.
- If the unit needs an upstream issue that does not exist or is not labelled approved,
  prepare the issue text and stop. Do not file anything upstream yourself.
- For each task: write the scenario test first, run it and show the failing output, write the
  minimal change, run it green, refactor on green, then tick the task in the unit file.
- The single-threaded path must not change behaviour. Do not make modules thread-safe; confine
  them. No public API change.
- If the code no longer matches 01-analysis.md, re-verify, correct the document and report it.
- Maintain the knowledge as you go: correct skills and documents that proved wrong, add new
  facts to 01-analysis.md, decisions to 04-decisions.md, pitfalls to 05-findings.md and
  questions for a person to 06-open-questions.md, each with its source, and fix the unit file
  when a task was too large, missing or misplaced. Do this in the same unit, without being
  asked. Never contradict an earlier decision silently.

VERIFICATION (unit gate, show real output for each)
- Targeted tests of the unit and the meta tests it touches.
- ./mvnw -e --no-transfer-progress clean compile antrun:run@ant-phase-verify
- ./mvnw -e --no-transfer-progress clean verify
- PIT for the touched classes: no new surviving mutation.
- From phase 4 on: the differential scenarios S24 to S29 for 1, 2 and 4 threads.

OUTPUT
- One commit with a message in the AGENTS.md format, pushed to the working branch, and a draft
  pull request that follows .github/PULL_REQUEST_TEMPLATE.md.
- All boxes of the unit ticked, the unit file moved to plan/done, STATUS.md updated. Notes are
  a separate commit; they are never part of the code commit.
- A report: table of scenario, test, result; build results; files changed; assumptions; risks;
  and a "Learned" list with what was added or corrected in the skills and documents.
```

## Prompt C - Phase gate review

Replace `<PHASE>` with a phase number.

```text
ROLE
You are a reviewer who did not write the code.

CONTEXT
Read .agent/.claude/multithreading/03-design.md, 04-decisions.md, STATUS.md, 08-scenarios.md and the
phase folder under plan.

OBJECTIVE
Decide whether phase <PHASE> has passed its gate.

APPROACH
- List the units of the phase and verify each is in plan/done and merged (git log on master).
- Re-run the invariant tests named in 03-design.md on the current master and show the output.
- For phases 4 and later: run the differential comparison on Checkstyle's own sources with all
  modules for 1, 2 and 4 threads, three times, and compare the reports byte for byte.
- Look for anything that weakens an invariant: a skipped test, a new suppression, a module
  made thread-safe without a written reason, a public API change.

OUTPUT
- "Passed" or "Not passed" with the evidence.
- Corrections to 01-analysis.md and 03-design.md where reality differs.
- The gate boxes in the phase README ticked or left open accordingly.
- The unit files of the next phase re-cut into tasks of at most two hours.
```
