import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VendingMachineExceptionTest {

    @Test
    void defaultConstructor_createsExceptionWithoutMessage() {
        VendingMachineException exception =
            new VendingMachineException();

        assertNotNull(exception);
        assertNull(exception.getMessage());
    }

    @Test
    void messageConstructor_storesProvidedMessage() {
        VendingMachineException exception =
            new VendingMachineException("Test message");

        assertEquals("Test message", exception.getMessage());
    }
}
