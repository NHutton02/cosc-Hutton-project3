# Vending Machine Test Plan

## Test Plan

| Method / Behavior | Valid Case(s) | Exception / Invalid Case(s) | Boundary Case(s) | Oracle / Expected Result | Related JUnit Test(s) |
|---|---|---|---|---|---|
| VendingMachine Constructor | Create a new vending machine | N/A | Newly created/empty machine | According to the constructor postcondition, the balance should be 0 and all four slots should be empty | testConstructor_initialState_emptyAndZeroBalance |
| addItem() | Add an item to a valid empty slot such as A | Invalid slot code; attempt to add an item to an occupied slot | Empty slot changes to occupied | Javadocs state the item should be stored in the specified slot; occupied or invalid slots should throw VendingMachineException | testAddItem_validEmptySlot_addsItem; testAddItem_occupiedSlot_throwsException; testAddItem_invalidCode_throwsException |
| getItem() | Retrieve an item from a valid occupied slot | Invalid slot code | Valid empty slot returns null | Javadocs state that the item occupying the requested slot is returned and an invalid code throws VendingMachineException | testGetItem_occupiedSlot_returnsItem; testGetItem_emptySlot_returnsNull; testGetItem_invalidCode_throwsException |
| removeItem() | Remove an item from a valid occupied slot | Invalid slot code; attempt to remove from an empty slot | Occupied slot becomes empty | Javadocs state the item should be returned and removed; empty or invalid slots should throw VendingMachineException | testRemoveItem_occupiedSlot_removesAndReturnsItem; testRemoveItem_emptySlot_throwsException; testRemoveItem_invalidCode_throwsException |
| insertMoney() | Insert a positive amount | Amount less than 0 | Test values below, at, and above 0 | The documented precondition is amount >= 0, so nonnegative amounts should be accepted and negative amounts should throw VendingMachineException | testInsertMoney_validAmount_increasesBalance; parameterized test for negative, zero, and positive amounts |
| getBalance() | Check the balance after inserting money | N/A | Initial balance of 0 | Javadocs state that getBalance() returns the current balance without changing it | testGetBalance_initialBalance_isZero; testGetBalance_afterInsert_returnsCurrentBalance |
| makePurchase() | Purchase an available item with sufficient balance | Empty slot; insufficient balance; invalid slot code | Insufficient balance, exact balance, and greater-than-price balance | Javadocs state the method returns true when enough money is available, false for insufficient money or an empty slot, removes the purchased item, and subtracts its price from the balance | testMakePurchase_exactBalance_completesPurchase; testMakePurchase_excessBalance_completesPurchaseAndLeavesBalance; testMakePurchase_insufficientBalance_returnsFalse; testMakePurchase_emptySlot_returnsFalse; testMakePurchase_invalidCode_throwsException |
| returnChange() | Return a positive existing balance | N/A | Balance of exactly 0 | Javadocs state that the current balance is returned and the machine balance is reset to 0 | testReturnChange_positiveBalance_returnsAmountAndResetsBalance; testReturnChange_zeroBalance_returnsZero |
| VendingMachineItem Constructor | Create an item with a valid nonnegative price | Negative price | Price exactly 0 | Javadocs state that the supplied name and price are stored and a price below 0 throws VendingMachineException | testItemConstructor_validPrice_storesValues; testItemConstructor_zeroPrice_isAllowed; testItemConstructor_negativePrice_throwsException |
| VendingMachineItem.getName() | Retrieve an item's stored name | N/A | N/A | Javadocs state that the actual stored item name is returned | testGetName_returnsStoredName |
| VendingMachineItem.getPrice() | Retrieve an item's stored price | N/A | Price of 0 | Javadocs state that the actual stored item price is returned | testGetPrice_returnsStoredPrice |
| VendingMachineException() | Create a default exception | N/A | N/A | Default RuntimeException behavior should create an exception object without a supplied message | testException_defaultConstructor_createsException |
| VendingMachineException(String) | Create an exception with a message | N/A | Empty-string message if desired | The constructor passes the supplied reason to RuntimeException, so getMessage() should return the supplied message | testException_messageConstructor_preservesMessage |

## Parameterized Test Strategy

The VendingMachine test suite will include at least one meaningful JUnit 5 parameterized test using `@ParameterizedTest` with either `@ValueSource` or `@CsvSource`.

A good target is `insertMoney()` because its documented boundary is **0**:

- negative values should be rejected;
- `0` should be accepted according to the documented precondition `amount >= 0`;
- positive values should be accepted and added to the balance.

The parameterized test will use at least six meaningful values around this boundary so that it demonstrates deliberate boundary or equivalence-class testing rather than simply repeating arbitrary inputs.

Possible values to consider:

- `-1.00`
- `-0.01`
- `0.00`
- `0.01`
- `0.50`
- `1.00`

The exact parameterized test structure will be finalized when the JUnit tests are implemented.

## Test Oracle

Expected results will be determined from the Javadocs, documented preconditions and postconditions, and manual calculations where necessary. The current behavior of the source code will not be used as the oracle because the provided project may contain faults.

The parameterized test `testInsertMoney_boundaryValues()` uses seven input values:
-1.00, -0.01, 0.00, 0.01, 0.99, 1.00, and 2.50.

The values were selected to test the invalid equivalence class below zero, the exact boundary at zero, values immediately above zero, values below one dollar, and ordinary valid positive amounts.
