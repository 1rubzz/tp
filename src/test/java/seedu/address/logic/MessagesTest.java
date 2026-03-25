package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class MessagesTest {

    @Test
    public void constructor_instantiation_success() {
        Messages messages = new Messages();
        assertEquals(Messages.class, messages.getClass());
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_noPrefixes_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, Messages::getErrorMessageForDuplicatePrefixes);
    }
}
