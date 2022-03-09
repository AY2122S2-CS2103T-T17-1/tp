package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DEADLINE_DESC_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.INDEX_DESC_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STIPEND_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.STIPEND_DESC_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SOFTWARE_ENGINEER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_SOFTWARE_ENGINEER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalRoles.SOFTWARE_ENGINEER;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddRoleCommand;
import seedu.address.model.role.Deadline;
import seedu.address.model.role.Description;
import seedu.address.model.role.Role;
import seedu.address.model.role.RoleName;
import seedu.address.model.role.Status;
import seedu.address.model.role.Stipend;
import seedu.address.testutil.RoleBuilder;

public class AddRoleCommandParserTest {
    private AddRoleCommandParser parser = new AddRoleCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Role expectedRole = new RoleBuilder(SOFTWARE_ENGINEER).build();
        assertParseSuccess(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER
                + DESCRIPTION_DESC_SOFTWARE_ENGINEER + STIPEND_DESC_SOFTWARE_ENGINEER,
                new AddRoleCommand(INDEX_FIRST_COMPANY, expectedRole));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no stipend
        Role expectedRole = new RoleBuilder(SOFTWARE_ENGINEER).withoutStipend().build();
        assertParseSuccess(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                        + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER
                        + DESCRIPTION_DESC_SOFTWARE_ENGINEER,
                new AddRoleCommand(INDEX_FIRST_COMPANY, expectedRole));

        // no description
        expectedRole = new RoleBuilder(SOFTWARE_ENGINEER).withoutDescription().build();
        assertParseSuccess(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                        + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER
                        + STIPEND_DESC_SOFTWARE_ENGINEER,
                new AddRoleCommand(INDEX_FIRST_COMPANY, expectedRole));

        // no description and stipend
        expectedRole = new RoleBuilder(SOFTWARE_ENGINEER).withoutDescription().withoutStipend().build();
        assertParseSuccess(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                        + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER,
                new AddRoleCommand(INDEX_FIRST_COMPANY, expectedRole));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoleCommand.MESSAGE_USAGE);

        // missing index
        assertParseFailure(parser, NAME_DESC_SOFTWARE_ENGINEER + STATUS_DESC_SOFTWARE_ENGINEER
                + DEADLINE_DESC_SOFTWARE_ENGINEER, expectedMessage);

        // missing name field
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + STATUS_DESC_SOFTWARE_ENGINEER
                        + DEADLINE_DESC_SOFTWARE_ENGINEER, expectedMessage);

        // missing status field
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + DEADLINE_DESC_SOFTWARE_ENGINEER, expectedMessage);

        // missing deadline field
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_SOFTWARE_ENGINEER + VALID_STATUS_SOFTWARE_ENGINEER
                        + VALID_DESCRIPTION_SOFTWARE_ENGINEER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        //// invalid index
        //assertParseFailure(parser, INVALID_INDEX_DESC + NAME_DESC_SOFTWARE_ENGINEER
        //        + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
        //        + STIPEND_DESC_SOFTWARE_ENGINEER,
        //        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoleCommand.MESSAGE_USAGE));

        // invalid name
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + INVALID_RNAME_DESC
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + STIPEND_DESC_SOFTWARE_ENGINEER, RoleName.MESSAGE_CONSTRAINTS);

        // invalid status
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + INVALID_STATUS_DESC + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + STIPEND_DESC_SOFTWARE_ENGINEER, Status.MESSAGE_CONSTRAINTS);

        // invalid deadline
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER + INVALID_DEADLINE_DESC + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + STIPEND_DESC_SOFTWARE_ENGINEER, Deadline.MESSAGE_CONSTRAINTS);

//        // invalid description
//        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
//                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + INVALID_DESCRIPTION_DESC
//                + STIPEND_DESC_SOFTWARE_ENGINEER, Description.MESSAGE_CONSTRAINTS);

        // invalid stipend
        assertParseFailure(parser, INDEX_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + INVALID_STIPEND_DESC, Stipend.MESSAGE_CONSTRAINTS);
    }
}
