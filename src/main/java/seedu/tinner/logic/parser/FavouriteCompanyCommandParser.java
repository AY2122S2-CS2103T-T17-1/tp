package seedu.tinner.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.FavouriteCompanyCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;

/**
 *  Parses input arguments and creates a new FavouriteCompanyCommand object
 */
public class FavouriteCompanyCommandParser implements Parser<FavouriteCompanyCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FavouriteCompanyCommand
     * and returns an FavouriteCompanyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FavouriteCompanyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Index companyIndex;

        try {
            companyIndex = ParserUtil.parseIndex(args);
            return new FavouriteCompanyCommand(companyIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FavouriteCompanyCommand.MESSAGE_USAGE), pe);
        }
    }
}
