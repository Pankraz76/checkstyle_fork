# Phase 2 - Safety net

Tests exist that prove a file's result does not depend on other files. Test code only.

## Units

| Unit | Title | Depends on | Tasks | Hours |
|---|---|---|---|---|
| 2.1 | Recording listener | nothing | 2 | 2.5 |
| 2.2 | File independence of all modules | 2.1 | 5 | 8 |
| 2.3 | Static state guard | nothing | 2 | 3 |

A unit is open while its file is in this folder and finished when the file is in the
folder of the same name under `done/`.

## Phase gate

- [ ] All tests of this phase run in `verify`
- [ ] They are green on upstream `master`
- [ ] Every module they exposed has its own unit in phase 1
- [ ] This file is moved to `done/` and `STATUS.md` is updated
