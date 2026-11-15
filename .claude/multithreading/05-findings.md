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
- Upstream runs a spell check over all files (`.ci/test-spelling-unknown-words.sh`). It was not
  run for this folder, which is not meant to go upstream (2026-11-15).

## Upstream research

- The GitHub search API reaches upstream issues: `gh api -X GET search/issues` with
  `repo:checkstyle/checkstyle is:issue <words>`. Comments are not included; read the issue
  pages for them (2026-11-15).
- Long issue timelines are collapsed on the page. Two timelines (issues 4869 and 22175) were
  only partly read (2026-11-15).
