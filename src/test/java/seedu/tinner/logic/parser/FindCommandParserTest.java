package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tinner.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tinner.logic.commands.FindCommand;
import seedu.tinner.model.company.CompanyNameContainsKeywordsPredicate;
import seedu.tinner.model.role.RoleNameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "find c/ r/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "find c/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "find r/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new CompanyNameContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"),
                        Arrays.asList("Square", "Enix")),
                        new RoleNameContainsKeywordsPredicate(Arrays.asList("Software", "Engineer")));
        assertParseSuccess(parser, " c/Square Enix r/Software Engineer", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n c/Square Enix \n \t r/Software Engineer \t", expectedFindCommand);
    }
}
