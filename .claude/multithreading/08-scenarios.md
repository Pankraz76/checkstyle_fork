# Behaviour scenarios

Each scenario is written before its task starts and becomes a test with a Given, When, Then
structure in the existing JUnit and Truth style. "Same as one thread" always means: the
recorded listener events are equal, in the same order (invariant I1).

## Phase 1 - Existing defects

```gherkin
Scenario: S01 Suppression of a Java file does not reach the next text file
  Given SuppressWarningsFilter, SuppressWarningsHolder and FileTabCharacter
  And A.java whose class is annotated with @SuppressWarnings("all") from line 1 to 6
  And b.txt with a tab character on line 2
  When A.java and then b.txt are processed
  Then the tab violation of b.txt is reported

Scenario: S02 Suppression does not reach a Java file whose parsing is skipped
  Given the modules of S01 and skipFileOnJavaParseException set to true
  And Broken.java that cannot be parsed and has a tab character on line 2
  When A.java and then Broken.java are processed
  Then the tab violation of Broken.java is reported

Scenario: S03 TranslationCheck reports bundles in the order the files were given
  Given 20 bundles that each miss the required translation
  When the files are processed in a fixed order
  Then the reported paths follow that order

Scenario: S04 JavadocLeadingAsteriskAlign forgets the previous file
  Given one check instance
  When a file with a misaligned Javadoc and then a clean file are processed
  Then the clean file has no violation and no field refers to the first file

Scenario: S05 Concurrent parsing gives the same trees
  Given the 51 grammar regression inputs and their AST dumps from sequential parsing
  When 4 threads each parse all inputs and replace the shared DFA cache after every file
  Then every AST dump equals its sequential dump
```

## Phase 2 - Safety net

```gherkin
Scenario: S06 Results do not depend on the files processed before
  Given every module in its default configuration, global modules excluded
  And the compilable test inputs in a fixed order
  When they are processed in that order and in the reverse order
  Then each file has the same violations in both runs

Scenario: S07 A reused instance behaves like a fresh one
  Given the configuration and inputs of S06
  When one Checker processes all files and a fresh Checker processes each file alone
  Then each file has the same violations in both runs

Scenario: S08 Modules that must stay on the calling thread are recognised
  Given every FileSetCheck of this repository
  When it is classified
  Then TranslationCheck and JavadocPackageCheck stay on the calling thread
  And a FileSetCheck without a marker stays on the calling thread
  And every other FileSetCheck, including TreeWalker, may run on a worker
```

## Phase 4 - Ordered window

```gherkin
Scenario: S09 Results are delivered in submission order
  Given tasks 1 to 100 where even tasks finish later than odd tasks
  When all are submitted and drained
  Then the results arrive as 1, 2, 3 up to 100

Scenario: S10 The window bound is respected
  Given a bound of 8 pending results and one task that blocks
  When 100 tasks are submitted
  Then never more than 8 results are pending
  And all 100 results arrive after the blocking task is released

Scenario: S11 The first failure in order wins
  Given task 5 and task 3 both fail and task 5 fails first in time
  When the results are drained
  Then results 1 and 2 are delivered, the failure of task 3 is thrown
  And tasks that were not started are cancelled

Scenario: S12 No thread is left behind
  Given a running window
  When the calling thread is interrupted or a failure is thrown
  Then all worker threads have ended when the call returns
```

## Phase 4 - Module trees and workers

```gherkin
Scenario: S13 Worker trees are created by the calling thread
  Given a configuration with TreeWalker, LineLength and Translation
  When trees for 3 workers are created
  Then 3 new TreeWalker and 3 new LineLength instances exist, created on the calling thread
  And there is still exactly one Translation instance

Scenario: S14 A module instance is used by one thread only
  Given a recording check inside TreeWalker
  When 200 files are processed on 4 threads
  Then every check instance has seen exactly one thread

Scenario: S15 Lifecycle calls happen on the owning thread
  Given a FileSetCheck that records the thread of each lifecycle call
  When a run with 3 workers ends
  Then process, finishProcessing and destroy of an instance were called on one thread
  And no two finishProcessing calls overlapped

Scenario: S16 A worker returns text and sorted violations
  Given a file with two violations of two modules
  When a worker processes it
  Then the result holds the file text and both violations in sorted order

Scenario: S17 An unreadable file
  Given a file that cannot be read
  When it is processed on 2 threads
  Then the events are the same as with one thread

Scenario: S18 A module throws and haltOnException is off
  Given modules G1 (global), W (worker, throws) and G2 (global) in this order
  When a file is processed on 2 threads
  Then G1 has seen the file, G2 has not
  And the events are the same as with one thread, apart from the stack trace text

Scenario: S19 A module throws and haltOnException is on
  Given 9 files and a check that throws on the fourth
  When they are processed on 4 threads
  Then files 1 to 3 are reported, file 4 gets fileStarted and fileFinished
  And the same CheckstyleException with the same cause chain is thrown
  And files 5 to 9 produce no event
```

## Phase 4 - Coordinator

```gherkin
Scenario: S20 Global modules see every file once, in input order
  Given JavadocPackage and 3 folders without package-info.java
  When the files are processed on 4 threads
  Then the violation is reported on the first file of each folder, as with one thread
  And no file was read twice

Scenario: S21 Modules after a failing module do not run
  Given the modules of S18
  When W throws
  Then the coordinator runs G1 only

Scenario: S22 The cache file is the same
  Given a cache file and files with and without violations
  When they are processed on 4 threads, also in a run that aborts on file 4 of 9
  Then the cache file has the same entries as after the same run with one thread

Scenario: S23 An Error is wrapped as today
  Given a check that throws an Error on the second file
  When the files are processed on 2 threads
  Then an Error with the same message and cause is thrown and the cache entry is removed

Scenario: S24 SuppressWarnings works across threads
  Given SuppressWarningsFilter, SuppressWarningsHolder and LineLength
  And 50 Java files with @SuppressWarnings("linelength") on some classes
  When they are processed on 4 threads
  Then the events are the same as with one thread
```

## Phase 4 - Wiring

```gherkin
Scenario: S25 One thread is the old path
  Given the switch set to 1
  When files are processed
  Then no worker thread is created and no worker module tree exists

Scenario: S26 Equivalence on the full input set
  Given a configuration (all modules, sun_checks, google_checks or the project config)
  When the test inputs are processed with 1, 2 and 4 threads
  Then the events of all three runs are equal

Scenario: S27 Edge sizes
  Given 4 threads
  When no file, one file or three files are processed
  Then the events are the same as with one thread

Scenario: S28 Second run on the same Checker
  Given a Checker that has finished a run on 4 threads
  When process is called again with other files
  Then the events are the same as two runs with one thread

Scenario: S29 Repeated runs are stable
  Given the configuration and inputs of S26
  When the run is repeated 50 times with 2 to 8 threads
  Then all 50 event lists are equal
```

## Phase 6 - Exposure

```gherkin
Scenario: S30 The property is validated
  Given the Checker property for the number of threads
  When it is set to 0, to -1 or to text
  Then configuration fails with a localized message that names the property

Scenario: S31 Suppression generation stays on one thread
  Given a command line that generates suppressions and asks for 4 threads
  When it runs
  Then one thread is used and the generated file equals the one from a normal run
```
