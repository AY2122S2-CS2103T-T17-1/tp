package seedu.tinner.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STIPEND;

import javafx.util.Pair;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.EditRoleCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRoleCommand object
 */
public class EditRoleCommandParser implements Parser<EditRoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRoleCommand
     * and returns an EditRoleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRoleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        Pair<Index[], String> parsedInput;

        try {
            parsedInput = ParserUtil.parseDoubleIndexWithContent(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoleCommand.MESSAGE_USAGE), pe);
        }

        Index[] indexes = parsedInput.getKey();
        Index companyIndex = indexes[0];
        Index roleIndex = indexes[1];
        String content = parsedInput.getValue();

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(content, PREFIX_NAME, PREFIX_STATUS,
                        PREFIX_DEADLINE, PREFIX_DESCRIPTION, PREFIX_STIPEND);

        EditRoleCommand.EditRoleDescriptor editRoleDescriptor = new EditRoleCommand.EditRoleDescriptor();
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editRoleDescriptor.setName(ParserUtil.parseRoleName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            editRoleDescriptor.setStatus(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
        }
        if (argMultimap.getValue(PREFIX_DEADLINE).isPresent()) {
            editRoleDescriptor.setDeadline(ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editRoleDescriptor.setDescription(ParserUtil.parseDescription(
                    argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_STIPEND).isPresent()) {
            editRoleDescriptor.setStipend(ParserUtil.parseStipend(argMultimap.getValue(PREFIX_STIPEND).get()));
        }

        if (!editRoleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRoleCommand.MESSAGE_USAGE));
        }

        return new EditRoleCommand(companyIndex, roleIndex, editRoleDescriptor);
    }

}
