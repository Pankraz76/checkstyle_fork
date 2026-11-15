# Glossary

## Terms of this topic

| Term | Meaning |
|---|---|
| Coordinator | The thread that calls `Checker.process`. Owns everything observable |
| Worker | A thread that reads files and runs one module tree |
| Module tree | One set of FileSetCheck instances, with `TreeWalker` and its children |
| Confined | Used by exactly one thread for its whole life |
| Global module | A FileSetCheck that keeps data across files; one instance, coordinator only |
| Hand-over | Passing the `SuppressWarningsHolder` entries of a file to the coordinator |
| In-order window | The bounded set of files that are submitted but not yet reported |
| Differential test | Run with one thread and with several, compare all listener events |
| Negative control | A run that must fail the differential test, to prove the test works |
| Corpus A | `src/main/java`, `src/test/java`, `src/it/java` of Checkstyle, 1,275 files |
| Corpus B | Test input folders and `src/xdocs-examples`, 7,236 files of all types |
| Spike | Throwaway prototype; never the basis of production code |

## Terms of Checkstyle

| Term | Meaning |
|---|---|
| `Checker` | Root module; processes the list of files |
| FileSetCheck | Module that gets every file; child of `Checker` |
| `TreeWalker` | The FileSetCheck that parses Java and runs AST checks |
| Check | Child of `TreeWalker`, subclass of `AbstractCheck` |
| Filter | Child of `Checker` that can drop a violation before it is reported |
| TreeWalker filter | Child of `TreeWalker` that can drop a violation; sees the AST |
| Audit listener | Receives the events of a run: logger, counter, report writer |
| `@StatelessCheck` | Marker: no field changes while files are processed |
| `@FileStatefulCheck` | Marker: fields hold data of the current file |
| `@GlobalStatefulCheck` | Marker: fields hold data across files |
| `haltOnException` | `Checker` property; off turns an exception into a violation |
| Cache file | `Checker` property `cacheFile`; skips unchanged files without violations |

## Terms of the workflow

| Term | Meaning |
|---|---|
| Feature | The whole topic with one measurable goal |
| Phase | A milestone with a gate |
| Unit | One issue, one pull request, one code commit |
| Task | One atomic step of at most two hours that ends green |
| Gate | Checklist that must be ticked before a unit or phase counts as done |
| Claim | Entry in `STATUS.md` that reserves a unit for one branch |
| Scenario | Behaviour in Given, When, Then form with an id (S01 ...) |
