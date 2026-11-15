# Phase 6 - Exposure

Users can switch the feature on, and the contract for check authors is documented.

## Units

| Unit | Title | Depends on | Tasks | Hours |
|---|---|---|---|---|
| 6.1 | `Checker` property (decision D1) | phase 5 gate | 2 | 4 |
| 6.2 | Command line | 6.1 | 3 | 4.5 |
| 6.3 | Ant task (upstream issue 4577) | 6.1 | 2 | 2.5 |
| 6.4 | Contract for check authors | 6.1 | 2 | 3 |
| 6.5 | Dead plumbing (finding F9) | 6.1 | 2 | 2.5 |

A unit is open while its file is in this folder and finished when the file is in the
folder of the same name under `done/`.

## Phase gate

- [ ] Property, command line option and Ant attribute are documented
- [ ] `XdocsPagesTest` and the other meta tests are green
- [ ] The marker Javadoc describes what is implemented
- [ ] This file is moved to `done/` and `STATUS.md` is updated
