package seedu.tinner.model.role;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new RoleName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new RoleName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> RoleName.isValidName(null));

        // invalid name
        assertFalse(RoleName.isValidName("")); // empty string
        assertFalse(RoleName.isValidName(" ")); // spaces only
        assertFalse(RoleName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(RoleName.isValidName("technician*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(RoleName.isValidName("software engineer")); // alphabets only
        assertTrue(RoleName.isValidName("12345")); // numbers only
        assertTrue(RoleName.isValidName("data analyst 2")); // alphanumeric characters
        assertTrue(RoleName.isValidName("Frontend Developer")); // with capital letters
        assertTrue(RoleName.isValidName("Database Management and Server Integration")); // long names
    }
}
