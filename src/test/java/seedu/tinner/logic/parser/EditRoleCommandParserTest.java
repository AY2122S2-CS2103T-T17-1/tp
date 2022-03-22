package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.logic.commands.CommandTestUtil.DEADLINE_DESC_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.DESCRIPTION_DESC_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.INVALID_DEADLINE_DESC;
import static seedu.tinner.logic.commands.CommandTestUtil.INVALID_ROLE_NAME_DESC;
import static seedu.tinner.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.tinner.logic.commands.CommandTestUtil.INVALID_STIPEND_DESC;
import static seedu.tinner.logic.commands.CommandTestUtil.NAME_DESC_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.STATUS_DESC_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.STIPEND_DESC_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_DEADLINE_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_NAME_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_STATUS_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_STIPEND_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_ROLE;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.EditRoleCommand;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.RoleName;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;
import seedu.tinner.testutil.EditRoleDescriptorBuilder;

public class EditRoleCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoleCommand.MESSAGE_USAGE);

    private EditRoleCommandParser parser = new EditRoleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // missing Company/Role index specified
        assertParseFailure(parser, "1" + NAME_DESC_SOFTWARE_ENGINEER, MESSAGE_INVALID_FORMAT);

        // no field and info specified
        assertParseFailure(parser, "1 1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative Company index
        assertParseFailure(parser, "-5 1" + NAME_DESC_SOFTWARE_ENGINEER, MESSAGE_INVALID_FORMAT);

        // negative Role index
        assertParseFailure(parser, "5 -1" + NAME_DESC_SOFTWARE_ENGINEER, MESSAGE_INVALID_FORMAT);

        // zero company index
        assertParseFailure(parser, "0 1" + NAME_DESC_SOFTWARE_ENGINEER, MESSAGE_INVALID_FORMAT);

        // zero role index
        assertParseFailure(parser, "1 0" + NAME_DESC_SOFTWARE_ENGINEER, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 1 i/string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, "1 1" + INVALID_ROLE_NAME_DESC, RoleName.MESSAGE_CONSTRAINTS);
        // invalid status
        assertParseFailure(parser, "1 1" + INVALID_STATUS_DESC, Status.MESSAGE_CONSTRAINTS);
        // invalid deadline
        assertParseFailure(parser, "1 1" + INVALID_DEADLINE_DESC, Deadline.MESSAGE_CONSTRAINTS);
        // invalid stipend
        assertParseFailure(parser, "1 1" + INVALID_STIPEND_DESC, Stipend.MESSAGE_CONSTRAINTS);

        // invalid status followed by valid deadline
        assertParseFailure(parser, "1 1" + INVALID_STATUS_DESC + DEADLINE_DESC_SOFTWARE_ENGINEER,
                Status.MESSAGE_CONSTRAINTS);

        // valid status followed by invalid status. The test case for invalid status followed by
        // valid status
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1 1" + STATUS_DESC_SOFTWARE_ENGINEER + INVALID_STATUS_DESC,
                Status.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                "1 1" + INVALID_STATUS_DESC + INVALID_STIPEND_DESC + VALID_DEADLINE_SOFTWARE_ENGINEER,
                Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index companyIndex = INDEX_SECOND_COMPANY;
        Index roleIndex = INDEX_FIRST_ROLE;
        String userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased() + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + STIPEND_DESC_SOFTWARE_ENGINEER;

        EditRoleCommand.EditRoleDescriptor descriptor =
                new EditRoleDescriptorBuilder().withName(VALID_NAME_SOFTWARE_ENGINEER)
                        .withStatus(VALID_STATUS_SOFTWARE_ENGINEER).withDeadline(VALID_DEADLINE_SOFTWARE_ENGINEER)
                        .withDescription(VALID_DESCRIPTION_SOFTWARE_ENGINEER)
                        .withStipend(VALID_STIPEND_SOFTWARE_ENGINEER).build();
        EditRoleCommand expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index companyIndex = INDEX_SECOND_COMPANY;
        Index roleIndex = INDEX_FIRST_ROLE;

        String userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER;


        EditRoleCommand.EditRoleDescriptor descriptor =
                new EditRoleDescriptorBuilder().withDeadline(VALID_DEADLINE_SOFTWARE_ENGINEER)
                        .withDescription(VALID_DESCRIPTION_SOFTWARE_ENGINEER).build();
        EditRoleCommand expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        Index companyIndex = INDEX_SECOND_COMPANY;
        Index roleIndex = INDEX_FIRST_ROLE;

        // name
        String userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + NAME_DESC_SOFTWARE_ENGINEER;
        EditRoleCommand.EditRoleDescriptor descriptor =
                new EditRoleDescriptorBuilder().withName(VALID_NAME_SOFTWARE_ENGINEER).build();
        EditRoleCommand expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // status
        userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + STATUS_DESC_SOFTWARE_ENGINEER;
        descriptor = new EditRoleDescriptorBuilder()
                        .withStatus(VALID_STATUS_SOFTWARE_ENGINEER).build();
        expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // deadline
        userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + DEADLINE_DESC_SOFTWARE_ENGINEER;
        descriptor = new EditRoleDescriptorBuilder().withDeadline(VALID_DEADLINE_SOFTWARE_ENGINEER)
                .build();
        expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + DESCRIPTION_DESC_SOFTWARE_ENGINEER;
        descriptor = new EditRoleDescriptorBuilder().withDescription(VALID_DESCRIPTION_SOFTWARE_ENGINEER)
                .build();
        expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // stipend
        userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + STIPEND_DESC_SOFTWARE_ENGINEER;
        descriptor = new EditRoleDescriptorBuilder().withStipend(VALID_STIPEND_SOFTWARE_ENGINEER).build();
        expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index companyIndex = INDEX_SECOND_COMPANY;
        Index roleIndex = INDEX_FIRST_ROLE;

        String userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased() + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + STIPEND_DESC_SOFTWARE_ENGINEER + NAME_DESC_SOFTWARE_ENGINEER
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + DESCRIPTION_DESC_SOFTWARE_ENGINEER
                + STIPEND_DESC_SOFTWARE_ENGINEER;

        EditRoleCommand.EditRoleDescriptor descriptor =
                new EditRoleDescriptorBuilder().withName(VALID_NAME_SOFTWARE_ENGINEER)
                        .withStatus(VALID_STATUS_SOFTWARE_ENGINEER).withDeadline(VALID_DEADLINE_SOFTWARE_ENGINEER)
                        .withDescription(VALID_DESCRIPTION_SOFTWARE_ENGINEER)
                        .withStipend(VALID_STIPEND_SOFTWARE_ENGINEER).build();
        EditRoleCommand expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index companyIndex = INDEX_SECOND_COMPANY;
        Index roleIndex = INDEX_FIRST_ROLE;

        String userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + INVALID_STATUS_DESC
                + STATUS_DESC_SOFTWARE_ENGINEER;
        EditRoleCommand.EditRoleDescriptor descriptor =
                new EditRoleDescriptorBuilder()
                        .withStatus(VALID_STATUS_SOFTWARE_ENGINEER).build();
        EditRoleCommand expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = companyIndex.getOneBased() + " " + roleIndex.getOneBased()
                + STATUS_DESC_SOFTWARE_ENGINEER + DEADLINE_DESC_SOFTWARE_ENGINEER + INVALID_STIPEND_DESC
                + STIPEND_DESC_SOFTWARE_ENGINEER;
        descriptor =
                new EditRoleDescriptorBuilder().withStatus(VALID_STATUS_SOFTWARE_ENGINEER)
                        .withDeadline(VALID_DEADLINE_SOFTWARE_ENGINEER)
                        .withStipend(VALID_STIPEND_SOFTWARE_ENGINEER).build();
        expectedCommand = new EditRoleCommand(companyIndex, roleIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
