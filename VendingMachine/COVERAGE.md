# VendingMachine Coverage Analysis

## Initial Coverage

| Source File | Statement/Line Coverage | Branch Coverage |
|---|---:|---:|
| VendingMachine.java | 100% (43/43) | 100% (20/20) |
| VendingMachineItem.java | 100% (8/8) | 100% (2/2) |

## Coverage-Guided Test Improvement

The Project 2 test suite already achieved 100% statement/line coverage
and 100% branch coverage for both VendingMachine.java and
VendingMachineItem.java. Therefore, no additional tests were required
solely to improve coverage.

## Final Coverage

| Source File | Statement/Line Coverage | Branch Coverage |
|---|---:|---:|
| VendingMachine.java | 100% (43/43) | 100% (20/20) |
| VendingMachineItem.java | 100% (8/8) | 100% (2/2) |

## Condition-Coverage Analysis

The compound Boolean decision selected for analysis is in the
`makePurchase()` method:

```java
if ((item != null) && (this.balance >= item.getPrice()))