# Decisions

Decision log. One entry for each decision with context, decision and consequences. An entry is
never deleted or rewritten: a changed decision gets a new entry that names the one it
replaces. Status is "accepted" (taken by the fork owner or in the analysis), "proposed"
(recommended, needs the maintainers) or "rejected".

## Taken

### A1 - Module instances are confined to a thread, never shared

- Status: accepted, 2026-11-15.
- Context: sharing instances between threads fails within a second (`01-analysis.md`,
  section "Controls"), and the maintainers refuse to demand thread-safe checks.
- Decision: every stateless or file-stateful FileSetCheck, including `TreeWalker` with its
  children, exists once for each worker. Nothing is made thread-safe.
- Consequences: memory grows with the number of workers; the stateless and file-stateful
  markers make no difference for correctness.

### A2 - Everything observable stays on the calling thread, in input order

- Status: accepted, 2026-11-15.
- Context: listeners, filters with caches, the cache file and global modules depend on file
  order, and none of them is thread-safe.
- Decision: the calling thread is the coordinator. It takes results in input order and runs
  global modules, Checker filters, listeners and cache handling exactly as today.
- Consequences: reports are identical; the serial part limits the gain when there are very
  many violations.

### A3 - One thread means today's code path

- Status: accepted, 2026-11-15.
- Context: stability comes before speed, and a reviewer asked for the cost of the new code
  with one thread.
- Decision: the default is one thread, and one thread never enters the new engine.
- Consequences: two code paths until the engine has proven itself; the differential test
  compares them permanently.

### A4 - Defects first, then tests, then refactoring, then the engine

- Status: accepted, 2026-11-15.
- Context: three existing defects make results depend on file order or JVM internals, which
  would make any equivalence test flaky.
- Decision: the order of phases 1 to 6 in `plan/README.md`.
- Consequences: the first merged pull requests are useful even if the feature is never built.

### A5 - No engine code before the maintainers agree

- Status: accepted, 2026-11-15.
- Context: the attempts of 2017 to 2019 produced code first and were closed for lack of an
  agreed design.
- Decision: phases 3 to 6 wait for the gate of phase 0.
- Consequences: progress depends on answers from upstream; phases 1 and 2 fill the time.

### A6 - Notes and code never share a commit

- Status: accepted as a proposal of the analysis session, to be confirmed by the fork owner.
- Context: `.claude/` exists only on the fork; upstream wants one commit for each pull request.
- Decision: code of a unit is one commit; ticks, status and notes are separate `doc:` commits
  that stay on the fork.
- Consequences: the branch for an upstream pull request is cut from upstream `master` and
  gets only the code commit.

## Open, for the maintainers

### D1 - How the number of threads is configured

- Status: proposed.
- Context: the hidden options `-C` and `-W` were removed in 8.32; `ThreadModeSettings` is dead
  plumbing (finding F9).
- Options: a `Checker` property, a command line option, or both.
- Proposal: a `Checker` property that defaults to 1, plus a command line option that overrides
  it. A property works for every integration without plugin releases.

### D2 - Modules without a marker

- Status: proposed.
- Context: third-party FileSetChecks may have no marker.
- Proposal: they run on the coordinator as single instances, exactly as today. Slower, but it
  cannot break them, and it matches "executed as it was before".

### D3 - `SuppressWarningsFilter` and `SuppressWarningsHolder`

- Status: proposed.
- Context: the holder (a check, on the worker) passes data to the filter (on the coordinator)
  through a static `ThreadLocal`.
- Options: hand the entries over with the result of the file; run Checker filters on the
  worker with one filter instance for each worker; make the filter a TreeWalker filter
  (issue 4831, but then it cannot suppress violations of FileSetChecks such as `LineLength`).
- Proposal: hand-over through the result, as an internal mechanism, after defect F1 is fixed.
  The prototype did this.

### D4 - Size of the in-order window

- Status: proposed.
- Context: a bounded window keeps memory flat but lets one slow file stall all workers
  (measured: three of four workers idle for two minutes).
- Options: bound by file count, bound by bytes of pending `FileText`, or both.
- Proposal: bound by bytes with a generous default, because results are small and it is the
  file text that costs memory.

### D5 - `finishProcessing` of worker instances

- Status: proposed.
- Context: only `TranslationCheck` overrides `finishProcessing` in this repository, and it is
  global.
- Proposal: for worker instances the method is called on the owning worker, one worker at a
  time, before `destroy`. A FileSetCheck that reports from `finishProcessing` is a multi-file
  check and must be marked global. This needs a sentence in the marker Javadoc.

### D6 - Failure in the middle of a file

- Status: proposed.
- Context: with `haltOnException` off, a module that throws ends the processing of that file,
  and modules that come earlier in the configuration have already run.
- Proposal: the coordinator runs only the global modules that precede the failing module. The
  prototype proved this is required.

## Rejected

| Alternative | Why not |
|---|---|
| Share one instance of each module between threads | fails at once; needs thread-safe checks |
| Clone configured instances | rejected by romani; needs new API |
| Parallel `visitToken` | rejected upstream; order of calls is part of the contract |
| Parse in parallel, validate on one thread | walking and Javadoc parsing stay serial |
| One `Checker` for each thread, started by the caller | breaks global modules, cache, reports |
| Second pass over all files for global modules | rejected by rnveach; doubles file reading |
