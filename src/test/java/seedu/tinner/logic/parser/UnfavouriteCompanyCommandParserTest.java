package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.UnfavouriteCompanyCommand;

public class UnfavouriteCompanyCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavouriteCompanyCommand.MESSAGE_USAGE);

    private UnfavouriteCompanyCommandParser parser = new UnfavouriteCompanyCommandParser();

    @Test
    public void parse_missingIndex_failure() {
        // no index specified
        assertParseFailure(parser, INVALID_INDEX_DESC, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_validIndex_success() {
        Index targetIndex = INDEX_SECOND_COMPANY;
        String userInput = String.valueOf(targetIndex.getOneBased());

        UnfavouriteCompanyCommand expectedCommand = new UnfavouriteCompanyCommand(targetIndex);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
