package seedu.tinner.model.role;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DescriptionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Description(null));
    }

    @Test
    public void constructor_invalidDescription_throwsIllegalArgumentException() {
        String invalidDescription = " ";
        assertThrows(IllegalArgumentException.class, () -> new Description(invalidDescription));
    }

    @Test
    public void isValidDescription() {
        // null description
        assertThrows(NullPointerException.class, () -> Description.isValidDescription(null));

        // invalid descriptions
        assertFalse(Description.isValidDescription((" "))); // spaces only

        // valid descriptions
        assertTrue(Description.isValidDescription((""))); // empty string
        assertTrue(Description.isValidDescription(("Work-from-home conditions.")));
        assertTrue(Description.isValidDescription(("-"))); // one character
        assertTrue(Description.isValidDescription(("Based in the States; consider work visa")));
    }
}
