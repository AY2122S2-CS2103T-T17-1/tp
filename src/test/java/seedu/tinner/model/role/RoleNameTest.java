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
        assertFalse(RoleName.isValidName("(")); // left bracket only
        assertFalse(RoleName.isValidName(")")); // right bracket only
        assertFalse(RoleName.isValidName("technician*")); // contains non-alphanumeric characters
        assertFalse(RoleName.isValidName("engineer ( backend")); // alphanumeric and left bracket only
        assertFalse(RoleName.isValidName("engineer (()")); // alphanumeric and multiple left brackets
        assertFalse(RoleName.isValidName("engineer ())")); // alphanumeric and multiple right brackets
        assertFalse(RoleName.isValidName("engineer ) front end (")); // alphanumeric and wrong order of brackets
        assertFalse(RoleName.isValidName("teacher (test.)")); // alphanumeric, brackets and dot
        assertFalse(RoleName.isValidName("staff (.)  ( admin")); //alphanumeric, wrong order brackets and dot
        assertFalse(RoleName.isValidName("(Backend)")); // brackets containing alphanumeric only

        // valid name
        assertTrue(RoleName.isValidName("Database Management and Server Integration")); // long names
        assertTrue(RoleName.isValidName("12345")); // numbers only
        assertTrue(RoleName.isValidName("data analyst 2")); // alphanumeric characters
        assertTrue(RoleName.isValidName("Frontend Developer")); // with capital letters
        assertTrue(RoleName.isValidName("SWE ()")); // alphanumeric and brackets
        assertTrue(RoleName.isValidName("SWE (Backend)")); // alphanumeric and brackets
    }
}
