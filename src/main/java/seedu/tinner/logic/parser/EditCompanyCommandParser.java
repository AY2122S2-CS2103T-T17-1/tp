package seedu.tinner.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.EditCompanyCommand;
import seedu.tinner.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.tinner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCompanyCommandParser implements Parser<EditCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCompanyCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCompanyCommand.MESSAGE_USAGE), pe);
        }

        EditCompanyDescriptor editCompanyDescriptor = new EditCompanyDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editCompanyDescriptor.setName(ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            editCompanyDescriptor.setPhone(ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get()));
        }
        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            editCompanyDescriptor.setEmail(ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get()));
        }
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editCompanyDescriptor.setAddress(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }

        if (!editCompanyDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCompanyCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCompanyCommand(index, editCompanyDescriptor);
    }
}
