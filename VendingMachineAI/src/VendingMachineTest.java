import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VendingMachineTest {

    private VendingMachine machine;
    private VendingMachineItem chips;

    @BeforeEach
    void setUp() {
        machine = new VendingMachine();
        chips = new VendingMachineItem("Chips", 1.50);
    }

    @Test
    void constructor_initializesEmptyMachineWithZeroBalance() {
        assertEquals(0.0, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
        assertNull(machine.getItem("B"));
        assertNull(machine.getItem("C"));
        assertNull(machine.getItem("D"));
    }

    @Test
    void addItem_emptySlot_storesItem() {
        machine.addItem(chips, "A");

        assertSame(chips, machine.getItem("A"));
    }

    @Test
    void addItem_occupiedSlot_throwsException() {
        machine.addItem(chips, "A");
        VendingMachineItem drink = new VendingMachineItem("Drink", 2.00);

        assertThrows(
            VendingMachineException.class,
            () -> machine.addItem(drink, "A")
        );

        assertSame(chips, machine.getItem("A"));
    }

    @Test
    void addItem_invalidCode_throwsException() {
        assertThrows(
            VendingMachineException.class,
            () -> machine.addItem(chips, "E")
        );
    }

    @Test
    void removeItem_occupiedSlot_returnsAndRemovesItem() {
        machine.addItem(chips, "B");

        VendingMachineItem removed = machine.removeItem("B");

        assertSame(chips, removed);
        assertNull(machine.getItem("B"));
    }

    @Test
    void removeItem_emptySlot_throwsException() {
        assertThrows(
            VendingMachineException.class,
            () -> machine.removeItem("C")
        );
    }

    @Test
    void insertMoney_positiveAmount_increasesBalance() {
        machine.insertMoney(2.50);

        assertEquals(2.50, machine.getBalance(), 0.001);
    }

    @Test
    void insertMoney_zeroAmount_isAllowed() {
        machine.insertMoney(0.0);

        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @Test
    void insertMoney_negativeAmount_throwsExceptionAndPreservesBalance() {
        assertThrows(
            VendingMachineException.class,
            () -> machine.insertMoney(-0.01)
        );

        assertEquals(0.0, machine.getBalance(), 0.001);
    }

    @Test
    void makePurchase_exactBalance_completesPurchase() {
        machine.addItem(chips, "A");
        machine.insertMoney(1.50);

        boolean purchased = machine.makePurchase("A");

        assertTrue(purchased);
        assertEquals(0.0, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void makePurchase_excessBalance_completesPurchaseAndLeavesChange() {
        machine.addItem(chips, "A");
        machine.insertMoney(2.00);

        boolean purchased = machine.makePurchase("A");

        assertTrue(purchased);
        assertEquals(0.50, machine.getBalance(), 0.001);
        assertNull(machine.getItem("A"));
    }

    @Test
    void makePurchase_insufficientBalance_returnsFalse() {
        machine.addItem(chips, "A");
        machine.insertMoney(1.00);

        boolean purchased = machine.makePurchase("A");

        assertFalse(purchased);
        assertEquals(1.00, machine.getBalance(), 0.001);
        assertSame(chips, machine.getItem("A"));
    }

    @Test
    void makePurchase_emptySlot_returnsFalse() {
        machine.insertMoney(2.00);

        boolean purchased = machine.makePurchase("D");

        assertFalse(purchased);
        assertEquals(2.00, machine.getBalance(), 0.001);
        assertNull(machine.getItem("D"));
    }

    @Test
    void returnChange_returnsBalanceAndResetsMachineToZero() {
        machine.insertMoney(3.25);

        double change = machine.returnChange();

        assertEquals(3.25, change, 0.001);
        assertEquals(0.0, machine.getBalance(), 0.001);
    }
}