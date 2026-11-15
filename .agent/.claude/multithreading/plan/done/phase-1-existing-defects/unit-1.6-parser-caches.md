# Unit 1.6 - F7: parser caches under concurrent parsing

| Field | Value |
|---|---|
| Phase | 1 - Existing defects |
| Kind | defect fix |
| Depends on | nothing |
| Issue | none; the change is a test only (`minor:`) |
| Estimate | 4.5 hours in 3 tasks, plus the unit gate |
| Claimed by | released |
| Branch | `claude/cool-brahmagupta-xtbfb2` |
| Pull request | fork pull request 7 (together with the notes) |

## Goal

It is proven that concurrent parsing gives the same trees (finding F7).

## Tasks

Tick a task only when its proof exists. One task at a time, in this order.

- [x] T1.6.1 (1 h) Read ANTLR 4.13.2 sources on `clearDFA` and DFA sharing; record it. Proof:
  `01-analysis.md`, section "F7 in detail".
- [x] T1.6.2 (2 h) Test: parse the grammar inputs on 4 threads, AST dumps equal the sequential
  ones. Proof: S05 as `JavaParserTest#testConcurrentParsingProducesSameTrees`; 60 of 60
  repetitions green; fails when the expected trees are deliberately wrong. After the fix for
  PMD and Qodana (try-with-resources, PMD exception): 28 tests of the class green, 30 of 30
  repetitions green, `pmd:check` green.
- [x] T1.6.3 (1.5 h) Only if T1.6.2 fails or the contract asks for it: guard the clearing.
  Not needed, see decision A7 in `04-decisions.md`. Proof: S05 green without a guard.

## Unit gate

- [x] Targeted tests and the meta tests this unit touches are green. `JavaParserTest`: 28
  tests, 0 failures; the new test 30 of 30 repetitions.
- [x] Self-check is green (`antrun:run@ant-phase-verify`, command in `AGENTS.md`). Part of
  the verify run below.
- [x] `clean verify` is green (command in `AGENTS.md`). BUILD SUCCESS in 10:56 min with 7,080
  unit tests and 1,409 integration tests, 0 failures. One test was excluded because the
  container runs as root: `MainTest#testExistingTargetFileButWithoutReadAccess`.
- [x] PIT for the touched classes shows no new surviving mutation. Not applicable: no
  production class was changed.
- [x] Regenerated files (`src/site/xdoc`, module metadata) are in the commit. None changed.
- [x] One code commit with a message in the `AGENTS.md` format; no notes in it. Two `minor:`
  commits on the notes branch (`85dc574` test, `532d816` fix for PMD and Qodana); squash them
  into one when they are cherry-picked for upstream.
- [x] Draft pull request is open and its link is in the table above. Fork pull request 7; the
  checks that run on the fork are green on it.
- [x] Skills and documents are updated and "Learned" below is filled in
- [x] This file is moved to `done/` and `STATUS.md` is updated

## Learned

- The ANTLR contract holds: replacing the shared DFA cache changes speed, not trees. Recorded
  in `01-analysis.md`, "F7 in detail", and as decision A7.
- A single green run of a concurrency test proves little; the failure appeared in repetition
  8 of 30. Recorded in `05-findings.md`.
- The first full build failed for a reason unrelated to the change (root user), the second
  because of the flag used to work around that. Recorded in `05-findings.md`.
- PMD and Qodana reject executors that are not in try-with-resources, and PMD forbids
  threads unless a class is listed. This affects phase 4; task T4.1.0 was added.
- Not done here: the test is not on a branch cut from upstream `master`. That needs a branch
  this session may not push to (open question O6).
