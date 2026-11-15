# Multi-threading in Checkstyle

Can `Checker` process files on several threads without breaking anything? This folder holds
the answer, the evidence, the design and the plan.

## Verdict

- Feasible, with results identical to a single-threaded run, if module instances are confined
  to one thread and everything observable stays on the calling thread in file order.
- Proven by a prototype: byte-identical XML reports for 1,275 files with all 247 modules, for
  `sun_checks`, `google_checks` and the project's own configuration, with cache and with
  `haltOnException`.
- Not feasible by sharing module instances between threads. That fails within a second.
- Expected gain on 4 cores: about 1.6 times in wall time with a default JVM, 2.4 times for the
  work itself. Runs with very many violations gain less.
- Upstream stopped in 2019 for lack of an agreed design, not for technical reasons. The design
  here follows the rules the maintainers wrote down then.
- Three existing defects make results depend on file order or JVM internals even with one
  thread. They are fixed first.

## Reading order

| Document | Read it when |
|---|---|
| `STATUS.md` | you want to know where the work stands |
| `01-analysis.md` | you need facts about the code or the measurements |
| `02-upstream-history.md` | you talk to maintainers or question a design rule |
| `03-design.md` | you implement or review anything |
| `04-decisions.md` | you wonder why something is done this way, or want to change it |
| `05-findings.md` | you start a session: pitfalls that already cost time |
| `06-open-questions.md` | you need an answer from a person, or want to know what is unproven |
| `07-glossary.md` | a term is unclear |
| `08-scenarios.md` | you write the test of a task |
| `09-prompts.md` | you start a new session for a unit or a phase gate |
| `plan/` | you pick the next task |
| `issues/` | something has to be filed upstream |
| `spike/` | you want to reproduce the evidence |

## Continue

Say "let's go". The skill `lets-go` reads `STATUS.md` and the folders under `plan/`, claims the
next unit and does its next open task. Phases 1 and 2 can start at once; phases 3 to 6 wait
for the agreement of phase 0.

## Not verified yet

See `06-open-questions.md`, section "Not verified". The most important ones: large external
projects, more than four cores, memory, and third-party modules.
