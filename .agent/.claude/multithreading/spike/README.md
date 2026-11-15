# Spike

Throwaway prototype that produced the numbers in `../01-analysis.md`. It is evidence, not a
starting point for the implementation: it has no tests, ignores the code style and takes
shortcuts (system properties, a public helper on `SuppressWarningsHolder`).

## Files

| File | Content |
|---|---|
| `spike.patch` | Changes to `Checker` and `SuppressWarningsHolder`, against commit `9d82118` |
| `all-modules-config.py` | Writes a configuration with all 247 modules in their defaults |

## Switches of the patched jar

| System property | Meaning |
|---|---|
| `checkstyle.spike.threads` | Number of workers; 1 (default) takes the unchanged path |
| `checkstyle.spike.mode` | `replica` (default) or `naive` (one instance for all threads) |
| `checkstyle.spike.snapshot` | `false` disables the hand-over of suppression entries |

## Reproduce

Never commit the patched sources. Build the jar, then restore the tree.

```bash
export LANG=C.UTF-8 LC_ALL=C.UTF-8
./mvnw -e --no-transfer-progress clean package -DskipTests -Passembly,no-validations
cp target/checkstyle-*-all.jar /tmp/baseline.jar

git apply .agent/.claude/multithreading/spike/spike.patch
./mvnw -e --no-transfer-progress clean package -DskipTests -Passembly,no-validations
cp target/checkstyle-*-all.jar /tmp/spike.jar
git checkout -- src

python3 .agent/.claude/multithreading/spike/all-modules-config.py . /tmp/all.xml
FILES="src/main/java src/test/java src/it/java"

java -jar /tmp/baseline.jar -c /tmp/all.xml -f xml -o /tmp/st.xml $FILES
java -Dcheckstyle.spike.threads=4 -jar /tmp/spike.jar \
    -c /tmp/all.xml -f xml -o /tmp/mt.xml $FILES
cmp /tmp/st.xml /tmp/mt.xml && echo identical
```

The build regenerates files under `src/site/xdoc` and the module metadata; `git checkout`
restores them. Both jars must be built from the same sources apart from the patch, because
`Checker.java` is itself one of the checked files.

## What the patch does

- `Checker.processFiles` branches to `spikeProcessFiles` when more than one thread is asked for.
- The calling thread creates one module tree per worker from the child configurations it kept
  in `setupChild`. Global-stateful and unannotated FileSetChecks are not copied.
- Each worker takes one tree, reads the file and runs the tree. Its result carries the
  `FileText`, the violations, a copy of the `SuppressWarningsHolder` entries and the index of a
  module that failed.
- The calling thread drains results in input order through a window of `4 * threads` files and
  runs global FileSetChecks, filters, listeners and cache handling as before.
- At the end every worker calls `finishProcessing` and `destroy` on its own tree.

## Known shortcuts

- The window is bounded by file count, so one very slow file stalls all workers.
- Cache entries of cancelled files are removed instead of being restored to their old value.
- Stack traces in `general.exception` violations contain executor frames.
- No handling of interruption, no reuse of the pool across `process` calls.
