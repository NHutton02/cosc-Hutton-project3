# Vending Machine Bugs

## Bug 1 - Constructor Array Index Error

### Observed Failure
Creating a new `VendingMachine` caused an `ArrayIndexOutOfBoundsException`.

### Test That Exposed It
`testConstructor_initialState_emptyAndZeroBalance()`

### Source-Code Fault
The constructor loop used `i <= NUM_SLOTS`, causing the loop to attempt to access index 4 in an array of length 4.

### Diagnosis
The JUnit failure reported `Index 4 out of bounds for length 4` at the constructor in `VendingMachine.java`. Since the four valid array indexes are 0 through 3, the loop was executing one iteration too many.

### Correction
Changed the loop condition from:

`i <= NUM_SLOTS`

to:

`i < NUM_SLOTS`

## Bug 2 - insertMoney Rejects Valid Amounts Below One Dollar

### Observed Failure
The parameterized `insertMoney()` test showed that the vending machine
rejected valid amounts of 0.00, 0.01, and 0.99 by throwing a
`VendingMachineException`.

### Test That Exposed It
`testInsertMoney_boundaryValues()`

### Source-Code Fault
The validation condition in `insertMoney()` incorrectly treated
non-negative amounts below 1.00 as invalid.

### Diagnosis
The parameterized boundary test showed that negative values were
correctly rejected and values of 1.00 or greater were accepted, but
0.00, 0.01, and 0.99 were incorrectly rejected. Debugging the method
showed that the validation condition was using the wrong boundary.

### Correction
Changed the validation condition so that only amounts less than 0
cause a `VendingMachineException`.

## Test Sensitivity Experiment

### Injected Fault
The validation condition in `VendingMachine.insertMoney()` was temporarily changed from:

`amount < 0`

to:

`amount <= 0`

The modified line was marked with:

`// INJECTED FAULT FOR TEST VALIDATION`

### Test That Failed
`testInsertMoney_boundaryValues()`

The parameterized test case using `0.00` failed.

### JUnit Failure
The test produced a `VendingMachineException` stating that the amount must be greater than or equal to zero.

### Why the Test Detected the Fault
The test includes values immediately below, at, and above the valid money boundary. The value `0.00` is expected to be valid. Changing the condition to `<= 0` incorrectly rejects zero, so the boundary test detects the injected fault.
