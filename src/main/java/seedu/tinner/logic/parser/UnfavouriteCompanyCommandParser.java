package seedu.tinner.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.UnfavouriteCompanyCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;

/**
 *  Parses input arguments and creates a new UnfavouriteCompanyCommand object
 */
public class UnfavouriteCompanyCommandParser implements Parser<UnfavouriteCompanyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UnfavouriteCompanyCommand
     * and returns an UnfavouriteCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UnfavouriteCompanyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index companyIndex;

        try {
            companyIndex = ParserUtil.parseIndex(args);
            return new UnfavouriteCompanyCommand(companyIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnfavouriteCompanyCommand.MESSAGE_USAGE), pe);
        }
    }
}
