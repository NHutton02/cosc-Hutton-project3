import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VendingMachineItemTest {

    @Test
    void testConstructor_validValues_storesNameAndPrice() {
        // Arrange / Act
        VendingMachineItem item =
                new VendingMachineItem("Chips", 1.50);

        // Assert
        assertEquals("Chips", item.getName());
        assertEquals(1.50, item.getPrice(), 0.001);
    }

    @Test
    void testConstructor_zeroPrice_storesZeroPrice() {
        // Arrange / Act
        VendingMachineItem item =
                new VendingMachineItem("Free Sample", 0.00);

        // Assert
        assertEquals("Free Sample", item.getName());
        assertEquals(0.00, item.getPrice(), 0.001);
    }

    @Test
    void testConstructor_negativePrice_throwsException() {
        // Act + Assert
        assertThrows(
            VendingMachineException.class,
            () -> new VendingMachineItem("Chips", -0.01)
        );
    }

    @Test
    void testGetName_returnsStoredName() {
        // Arrange
        VendingMachineItem item =
                new VendingMachineItem("Candy", 1.25);

        // Act
        String name = item.getName();

        // Assert
        assertEquals("Candy", name);
    }

    @Test
    void testGetPrice_returnsStoredPrice() {
        // Arrange
        VendingMachineItem item =
                new VendingMachineItem("Soda", 2.00);

        // Act
        double price = item.getPrice();

        // Assert
        assertEquals(2.00, price, 0.001);
    }
}
