# Phase 3 - Prepare Checker

`Checker` is restructured so that the engine can be added. No behaviour change; no existing test is
touched.

## Units

| Unit | Title | Depends on | Tasks | Hours |
|---|---|---|---|---|
| 3.1 | Per-file result value | phase 0 gate, 2.2 | 2 | 2 |
| 3.2 | Split `processFiles` into select, execute, report | 3.1 | 3 | 4 |
| 3.3 | Module creation that can be repeated | 3.2 | 3 | 4 |

A unit is open while its file is in this folder and finished when the file is in the
folder of the same name under `done/`.

## Phase gate

- [ ] The complete suite is green without a changed test
- [ ] PIT for `Checker` shows no new surviving mutation
- [ ] The regression report against `master` shows no difference
- [ ] This file is moved to `done/` and `STATUS.md` is updated
