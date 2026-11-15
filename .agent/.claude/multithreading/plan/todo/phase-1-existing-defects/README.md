# Phase 1 - Existing defects

Defects that make results depend on file order or JVM internals are fixed. Each unit is independent
of the others and useful without the feature.

## Units

| Unit | Title | Depends on | Tasks | Hours |
|---|---|---|---|---|
| 1.1 | F1: suppression entries outlive their file | nothing | 5 | 5 |
| 1.2 | F2: order of `TranslationCheck` reports | nothing | 3 | 3 |
| 1.3 | marker of `JavadocLeadingAsteriskAlignCheck` | nothing | 3 | 3 |
| 1.4 | state reset in file-stateful checks (upstream issue 22175) | nothing | 3 | 3.5 |
| 1.5 | finish the open pull requests 22180, 22181, 22182 | nothing | 3 | 3 |
| 1.6 | F7: parser caches under concurrent parsing | nothing | 3 | 4.5 |
| 1.7 | `parallelStream()` inside `JavaAstVisitor` | nothing | 2 | 2 |

A unit is open while its file is in this folder and finished when the file is in the
folder of the same name under `done/`.

## Phase gate

- [ ] Units 1.1, 1.2 and 1.3 are merged upstream
- [ ] Unit 1.4: no unclaimed check is left in upstream issue 22175
- [ ] Unit 1.6: the result is recorded in `01-analysis.md`
- [ ] Units 1.5 and 1.7 are at least in review
- [ ] This file is moved to `done/` and `STATUS.md` is updated
