# Phase 4 - Parallel engine

The engine exists as package-private code and is proven equivalent. Users cannot reach it.

## Units

| Unit | Title | Depends on | Tasks | Hours |
|---|---|---|---|---|
| 4.1 | Ordered window (no Checkstyle types) | phase 0 gate | 5 | 7 |
| 4.2 | Module trees for workers | 3.3 | 3 | 4.5 |
| 4.3 | Work done by a worker | 4.2 | 4 | 5.5 |
| 4.4 | Work done by the coordinator | 4.1 and 4.3 | 4 | 5.5 |
| 4.5 | Suppression hand-over (decision D3) | 4.4 and 1.1 | 2 | 2.5 |
| 4.6 | Wiring and equivalence | 4.4, 4.5, 2.1 and the phase 1 gate | 7 | 9.5 |

A unit is open while its file is in this folder and finished when the file is in the
folder of the same name under `done/`.

## Phase gate

- [ ] Scenarios S09 to S29 are green in `verify`
- [ ] The engine cannot be enabled from the CLI, the Ant task or a configuration file
- [ ] One thread still takes the old code path (scenario S25)
- [ ] This file is moved to `done/` and `STATUS.md` is updated
