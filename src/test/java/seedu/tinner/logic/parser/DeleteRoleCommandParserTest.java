package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_ROLE;

import org.junit.jupiter.api.Test;

import seedu.tinner.logic.commands.DeleteRoleCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCompanyCommand code. For example, inputs "1 1" and "1 abc" take the
 * same path through the DeleteRoleCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteRoleCommandParserTest {

    private DeleteRoleCommandParser parser = new DeleteRoleCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 1", new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "1 a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRoleCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRoleCommand.MESSAGE_USAGE));
    }
}
