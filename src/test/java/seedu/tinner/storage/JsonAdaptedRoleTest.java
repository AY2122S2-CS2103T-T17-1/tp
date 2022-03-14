package seedu.tinner.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tinner.model.role.Deadline.VALIDATION_FORMATTER;
import static seedu.tinner.storage.JsonAdaptedRole.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalRoles.NETWORK_ENGINEER;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.exceptions.IllegalValueException;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.Description;
import seedu.tinner.model.role.RoleName;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;

public class JsonAdaptedRoleTest {

    private static final String INVALID_ROLENAME = "@pple";
    private static final String INVALID_STATUS = "waiting";
    private static final String INVALID_DEADLINE = "2020-02-22";
    private static final String INVALID_DESCRIPTION = " a lot of work";
    private static final String INVALID_STIPEND = "one thousand";
    public static final JsonAdaptedRole INVALID_ROLE = new JsonAdaptedRole(INVALID_ROLENAME, INVALID_STATUS,
            INVALID_DEADLINE, INVALID_DESCRIPTION, INVALID_STIPEND);

    private static final String VALID_NAME = NETWORK_ENGINEER.getName().toString();
    private static final String VALID_STATUS = NETWORK_ENGINEER.getStatus().toString();
    private static final String VALID_DEADLINE = NETWORK_ENGINEER.getDeadline().value.format(VALIDATION_FORMATTER);
    private static final String VALID_DESCRIPTION = NETWORK_ENGINEER.getDescription().toString();
    private static final String VALID_STIPEND = NETWORK_ENGINEER.getStipend().toString();

    @Test
    public void toModelType_validRoleDetails_returnsRole() throws Exception {
        JsonAdaptedRole role = new JsonAdaptedRole(NETWORK_ENGINEER);
        assertEquals(NETWORK_ENGINEER, role.toModelType());
    }

    @Test
    public void toModelType_invalidRoleName_throwsIllegalValueException() {
        JsonAdaptedRole role =
                new JsonAdaptedRole(INVALID_ROLENAME, VALID_STATUS, VALID_DEADLINE, VALID_DESCRIPTION, VALID_STIPEND);
        String expectedMessage = RoleName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_nullRoleName_throwsIllegalValueException() {
        JsonAdaptedRole role = new JsonAdaptedRole(null, VALID_STATUS, VALID_DEADLINE, VALID_DESCRIPTION,
                VALID_STIPEND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, RoleName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedRole role =
                new JsonAdaptedRole(VALID_NAME, INVALID_STATUS, VALID_DEADLINE, VALID_DESCRIPTION, VALID_STIPEND);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedRole role = new JsonAdaptedRole(VALID_NAME, null, VALID_DEADLINE, VALID_DESCRIPTION,
                VALID_STIPEND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_invalidDeadline_throwsIllegalValueException() {
        JsonAdaptedRole role =
                new JsonAdaptedRole(VALID_NAME, VALID_STATUS, INVALID_DEADLINE, VALID_DESCRIPTION, VALID_STIPEND);
        String expectedMessage = Deadline.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_nullDeadline_throwsIllegalValueException() {
        JsonAdaptedRole role = new JsonAdaptedRole(VALID_NAME, VALID_STATUS, null, VALID_DESCRIPTION,
                VALID_STIPEND);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Deadline.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_invalidDescription_throwsIllegalValueException() {
        JsonAdaptedRole role =
                new JsonAdaptedRole(VALID_NAME, VALID_STATUS, VALID_DEADLINE, INVALID_DESCRIPTION, VALID_STIPEND);
        String expectedMessage = Description.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }

    @Test
    public void toModelType_invalidStipend_throwsIllegalValueException() {
        JsonAdaptedRole role =
                new JsonAdaptedRole(VALID_NAME, VALID_STATUS, VALID_DEADLINE, VALID_DESCRIPTION, INVALID_STIPEND);
        String expectedMessage = Stipend.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, role::toModelType);
    }
}

