package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.DeleteRoleCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteRoleCommand object
 */
public class DeleteRoleCommandParser implements Parser<DeleteRoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteRoleCommand
     * and returns a DeleteRoleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteRoleCommand parse(String args) throws ParseException {
        try {
            Index[] indexes = ParserUtil.parseDoubleIndex(args);
            return new DeleteRoleCommand(indexes[0], indexes[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteRoleCommand.MESSAGE_USAGE), pe);
        }
    }
}
