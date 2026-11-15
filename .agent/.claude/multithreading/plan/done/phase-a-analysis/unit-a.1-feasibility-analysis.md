# Unit A.1 - Feasibility analysis and spike

| Field | Value |
|---|---|
| Phase | A - Analysis |
| Kind | analysis, no production code |
| Depends on | nothing |
| Issue | none |
| Estimate | not estimated |
| Claimed by | released |
| Branch | `claude/cool-brahmagupta-xtbfb2` |
| Pull request | draft on the fork |

## Goal

Decide with evidence whether files can be processed on several threads without changing
results, and prepare everything needed to execute that.

## Tasks

- [x] A.1.1 Audit of `Checker`, `TreeWalker`, base classes, markers, filters, listeners, cache
  and parser. Proof: `01-analysis.md`.
- [x] A.1.2 Upstream history with maintainer statements. Proof: `02-upstream-history.md`.
- [x] A.1.3 Throwaway prototype and differential test on four configurations, with cache and
  with a failing file. Proof: `spike/`, tables in `01-analysis.md`.
- [x] A.1.4 Negative control with shared instances. Proof: `01-analysis.md`, section "Controls".
- [x] A.1.5 Every difference explained. Proof: `01-analysis.md`, section "Equivalence results".
- [x] A.1.6 Design, scenarios, plan, issue drafts, prompts and skills written. Proof: this folder.

## Unit gate

- [x] Working tree contains no prototype code
- [x] Markdown passes `mdl -g .`
- [x] Self-check for non-main files is green
- [x] Skills and documents are updated and "Learned" below is filled in
- [x] This file is in `done/` and `STATUS.md` is updated

## Learned

- A differential test needs a negative control, otherwise a green result proves nothing.
- The checked sources must not change between compared runs; `Checker.java` is in the corpus.
- All four differing files led to a real defect or a real gap in the prototype.
- Whole-JVM wall time hides the scaling of the work; JIT and GC already use a second core.
- A bounded in-order window lets one slow file stall all workers.
- The non-main self-check and `mdl` apply to this folder; a `git diff` patch needed its long
  header lines removed.
