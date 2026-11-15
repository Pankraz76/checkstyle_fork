# Findings

Reusable technical learnings for later sessions. Append, with the date; correct an entry that
turned out wrong instead of deleting it. Facts about the product code belong in
`01-analysis.md`; this file is about how to work.

## Building and running

- A build that reaches `process-classes` regenerates `src/site/xdoc` and the module metadata.
  After a throwaway build restore them with `git checkout -- src` (2026-11-15).
- `./mvnw -o` fails for `antrun:run@ant-phase-verify` until the plugin was downloaded once.
  Run the self-check online the first time (2026-11-15).
- The CLI jar for experiments is `target/checkstyle-*-all.jar` from
  `clean package -DskipTests -Passembly,no-validations`, about 100 seconds (2026-11-15).
- This environment sets `JAVA_TOOL_OPTIONS`, which prints a line on standard error for every
  JVM start. Clear it (`JAVA_TOOL_OPTIONS=`) when output is compared (2026-11-15).
- Use a UTF-8 locale for every run over the test input folders; some file names are not ASCII
  (2026-11-15).
- In a container that runs as root, `clean verify` fails in
  `MainTest#testExistingTargetFileButWithoutReadAccess`: root can read a file after
  `setReadable(false)`. Surefire then stops the build before the integration tests and the
  static analysis (2026-11-15).
- Do not exclude that test with `-Dtest='!MainTest#...'`. `-Dtest` replaces the includes of
  surefire, so surefire also runs the integration tests of `src/it`, which fail there with a
  missing "Header mismatch" violation because only failsafe sets the header file properties
  (151 failures in one run). Use an exclude file instead: a text file with the line
  `MainTest#testExistingTargetFileButWithoutReadAccess` and
  `-Dsurefire.excludesFile=<file>` (2026-11-15).
- A failure that appears after changing how a build is started is first suspected to come
  from that change. Run one failing test the documented way before blaming the code
  (2026-11-15).
- The notification of a background command reports the exit code of the wrapper. Read the
  real result from the log (`BUILD SUCCESS` or `BUILD FAILURE`) (2026-11-15).

## Measuring

- Compare reports with `-f xml -o file` and `cmp`. XML keeps order, so the comparison covers
  the order of files and violations (2026-11-15).
- A differential test needs a negative control. Without the run with shared instances it
  would not have been known that the comparison can detect breakage (2026-11-15).
- The checked sources must not change between compared runs. `Checker.java` is part of the
  corpus, so a rebuilt prototype changed the baseline report (2026-11-15).
- A difference is not noise until it is explained. Each of the four differing files led to a
  real defect or a real gap in the prototype (2026-11-15).
- Whole-JVM wall time hides the scaling of the work: a single-threaded run already uses two
  cores for JIT and garbage collection. Measure additionally with
  `-XX:TieredStopAtLevel=1 -XX:+UseSerialGC` and say which number is which (2026-11-15).
- `src/test/resources/.../xpathmapper/InputXpathMapperStringConcat.java` needs about two
  minutes to parse. Leave it out of timing runs or it dominates them (2026-11-15).
- The test input folders contain files that make checks throw. Use `haltOnException=false`
  and `skipFileOnJavaParseException=true` for runs over them (2026-11-15).
- `-XX:+UnlockExperimentalVMOptions -XX:hashCode=2` changes identity hash codes and exposes
  code that depends on `HashSet` order of objects without `equals` (2026-11-15).

## Repository rules that apply to this folder

- Markdown is checked by `mdl -g .` with `config/markdownlint.rb`: 100 characters, also in
  tables and code blocks. A line must not start with `#` followed by digits; it is read as a
  header (2026-11-15).
- `mdl` is installed with `gem install mdl`; the binary may not be on the path, look in the
  gem directory (2026-11-15).
- All files outside `src/main` are checked by `config/checkstyle-non-main-files-checks.xml`:
  100 characters and a newline at the end. A `git diff` patch needs its `diff --git` lines and
  the text after `@@ ... @@` removed; `git apply` still accepts it (2026-11-15).
- The fork's check "Check PR Description" failed on every pull request because
  `.ci/pr-description.sh` asked the upstream repository for the pull request number of the
  fork. Fixed on 2026-11-15 by reading the pull request from `$GITHUB_REPOSITORY`; issues are
  still looked up upstream. The fix is a fork commit and has not been proposed upstream.
- Shell scripts are linted with `shellcheck` and `.shellcheckrc`; `pip install shellcheck-py`
  provides the binary (2026-11-15).
- Upstream runs a spell check over all files (`.ci/test-spelling-unknown-words.sh`). It was not
  run for this folder, which is not meant to go upstream (2026-11-15).

## Writing concurrency tests

- Stress a new concurrency test before trusting it: change `@Test` to `@RepeatedTest(60)`
  locally, run it once, change it back. A single green run of unit 1.6 hid a failure that
  showed up in repetition 8 of 30 (2026-11-15).
- An assertion inside a helper must not depend on production code that is racy by design.
  The helper of unit 1.6 first relied on the parser's file counter to replace the cache;
  calling `clearDFA()` directly made it deterministic (2026-11-15).
- Read which assertion failed before concluding anything. The stress failure was the helper's
  own check, not the comparison of trees (2026-11-15).
- The test suite has no other test with an executor. Shut it down in `finally` and get every
  `Future`, so that a failure on a worker reaches JUnit (2026-11-15).
- New methods of a test class go before its inner types, or the self-check reports
  `InnerTypeLast` (2026-11-15).

## Reading library sources

- Sources of a dependency: `./mvnw -q dependency:get
  -Dartifact=org.antlr:antlr4-runtime:4.13.2:jar:sources`, then unzip the jar from
  `~/.m2/repository` (2026-11-15).

## Upstream research

- GitHub keeps single line breaks in issue and pull request comments. The drafts under
  `issues/` are wrapped at 100 characters for the lint, so join the lines of each paragraph
  before posting, or the comment shows ragged lines (2026-11-15).

- The GitHub search API reaches upstream issues: `gh api -X GET search/issues` with
  `repo:checkstyle/checkstyle is:issue <words>`. Comments are not included; read the issue
  pages for them (2026-11-15).
- Search upstream for an existing issue before drafting a new one, also among closed issues.
  The draft for the `TranslationCheck` order was written before issue 8963 was found
  (2026-11-15).
- Long issue timelines are collapsed on the page. Two timelines (issues 4869 and 22175) were
  only partly read (2026-11-15).
