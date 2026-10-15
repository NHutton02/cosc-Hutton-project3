import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class VendingMachineTest {
    VendingMachine machine;

    @BeforeEach 
        void setUp(){
            machine = new VendingMachine();
        }

    @Test 
    void testConstructor_initialState_emptyAndZeroBalance(){

        

        //assert
        assertEquals(0.0, machine.getBalance(), 0.001);

        assertNull(machine.getItem("A"));
        assertNull(machine.getItem("B"));
        assertNull(machine.getItem("C"));
        assertNull(machine.getItem("D"));

    }

@Test
void testAddItem_validEmptySlot_addsItem() {
    // Arrange
    VendingMachineItem item =
            new VendingMachineItem("Chips", 1.50);

    // Act
    machine.addItem(item, "A");

    // Assert
    assertSame(item, machine.getItem("A"));
}

@Test
void testAddItem_occupiedSlot_throwsException() {
    // Arrange
    VendingMachineItem firstItem =
            new VendingMachineItem("Chips", 1.50);

    VendingMachineItem secondItem =
            new VendingMachineItem("Candy", 1.00);

    machine.addItem(firstItem, "A");

    // Act + Assert
    assertThrows(
        VendingMachineException.class,
        () -> machine.addItem(secondItem, "A")
    );
}

@Test
void testAddItem_invalidCode_throwsException() {
    // Arrange
    VendingMachineItem item =
            new VendingMachineItem("Chips", 1.50);

    // Act + Assert
    assertThrows(
        VendingMachineException.class,
        () -> machine.addItem(item, "E")
    );
}
    

    @Test
    void testGetBalance_initialBalance_isZero() {
        // Act
        double balance = machine.getBalance();
    
        // Assert
        assertEquals(0.0, balance, 0.001);
    }
    
    @Test
    void testGetBalance_afterInsert_returnsCurrentBalance() {
        // Arrange
        machine.insertMoney(2.50);
    
        // Act
        double balance = machine.getBalance();
    
        // Assert
        assertEquals(2.50, balance, 0.001);
    }

    @Test
    void testGetItem_occupiedSlot_returnsItem() {
        //Arrange
        VendingMachineItem item =
            new VendingMachineItem("Chips", 1.50);
    machine.addItem(item, "A");


        //Act
VendingMachineItem result = machine.getItem("A");
        //Assert
assertSame(item, result);

    }

    @Test
void testGetItem_emptySlot_returnsNull() {
    // Act
    VendingMachineItem result = machine.getItem("A");

    // Assert
    assertNull(result);
}

@Test
void testGetItem_invalidCode_throwsException() {
    // Act + Assert
    assertThrows(
        VendingMachineException.class,
        () -> machine.getItem("E")
    );
}

@ParameterizedTest
@CsvSource({
    "-1.00, false",
    "-0.01, false",
    "0.00, true",
    "0.01, true",
    "0.99, true",
    "1.00, true",
    "2.50, true"
})
void testInsertMoney_boundaryValues(double amount, boolean shouldBeValid) {

    if (shouldBeValid) {
        // Act
        machine.insertMoney(amount);

        // Assert
        assertEquals(amount, machine.getBalance(), 0.001);

    } else {
        // Act + Assert
        assertThrows(
            VendingMachineException.class,
            () -> machine.insertMoney(amount)
        );

        assertEquals(0.0, machine.getBalance(), 0.001);
    }
}


    
@Test
void testMakePurchase_exactBalance_completesPurchase() {
    // Arrange
    VendingMachineItem item = new VendingMachineItem("Chips", 1.50);
    machine.addItem(item, "A");
    machine.insertMoney(1.50);

    // Act
    boolean result = machine.makePurchase("A");

    // Assert
    assertTrue(result);
    assertEquals(0.0, machine.getBalance(), 0.001);
    assertNull(machine.getItem("A"));
}

@Test
void testMakePurchase_excessBalance_completesPurchaseAndLeavesBalance() {
    // Arrange
    VendingMachineItem item = new VendingMachineItem("Chips", 1.50);
    machine.addItem(item, "A");
    machine.insertMoney(2.00);

    // Act
    boolean result = machine.makePurchase("A");

    // Assert
    assertTrue(result);
    assertEquals(0.50, machine.getBalance(), 0.001);
    assertNull(machine.getItem("A"));
}

@Test
void testMakePurchase_insufficientBalance_returnsFalse() {
    // Arrange
    VendingMachineItem item = new VendingMachineItem("Chips", 1.50);
    machine.addItem(item, "A");
    machine.insertMoney(1.00);

    // Act
    boolean result = machine.makePurchase("A");

    // Assert
    assertFalse(result);
    assertEquals(1.00, machine.getBalance(), 0.001);
    assertSame(item, machine.getItem("A"));
}

@Test
void testMakePurchase_emptySlot_returnsFalse() {
    // Arrange
    machine.insertMoney(2.00);

    // Act
    boolean result = machine.makePurchase("A");

    // Assert
    assertFalse(result);
    assertEquals(2.00, machine.getBalance(), 0.001);
    assertNull(machine.getItem("A"));
}

@Test
void testMakePurchase_invalidCode_throwsException() {
    // Act + Assert
    assertThrows(
        VendingMachineException.class,
        () -> machine.makePurchase("E")
    );
}

    @Test
void testRemoveItem_occupiedSlot_removesAndReturnsItem() {
    // Arrange
    VendingMachineItem item =
            new VendingMachineItem("Chips", 1.50);
    machine.addItem(item, "A");

    // Act
    VendingMachineItem removedItem = machine.removeItem("A");

    // Assert
    assertSame(item, removedItem);
    assertNull(machine.getItem("A"));
}

@Test
void testRemoveItem_emptySlot_throwsException() {
    // Act + Assert
    assertThrows(
        VendingMachineException.class,
        () -> machine.removeItem("A")
    );
}

@Test
void testRemoveItem_invalidCode_throwsException() {
    // Act + Assert
    assertThrows(
        VendingMachineException.class,
        () -> machine.removeItem("E")
    );
}

   @Test
void testReturnChange_positiveBalance_returnsBalanceAndResetsToZero() {
    // Arrange
    machine.insertMoney(2.50);

    // Act
    double change = machine.returnChange();

    // Assert
    assertEquals(2.50, change, 0.001);
    assertEquals(0.0, machine.getBalance(), 0.001);
}

@Test
void testReturnChange_zeroBalance_returnsZero() {
    // Act
    double change = machine.returnChange();

    // Assert
    assertEquals(0.0, change, 0.001);
    assertEquals(0.0, machine.getBalance(), 0.001);
}
}
