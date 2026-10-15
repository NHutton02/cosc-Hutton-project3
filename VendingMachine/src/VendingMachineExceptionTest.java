import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VendingMachineExceptionTest {

    @Test
    void testDefaultConstructor_createsException() {
        // Act
        VendingMachineException exception =
                new VendingMachineException();

        // Assert
        assertNotNull(exception);
    }

    @Test
    void testMessageConstructor_storesMessage() {
        // Arrange
        String message = "Test exception message";

        // Act
        VendingMachineException exception =
                new VendingMachineException(message);

        // Assert
        assertEquals(message, exception.getMessage());
    }
}
