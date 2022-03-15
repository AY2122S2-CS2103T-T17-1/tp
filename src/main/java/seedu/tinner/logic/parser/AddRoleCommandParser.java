package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.commons.core.Messages.MESSAGE_NO_VALUE_AFTER_PREFIX;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STIPEND;
import static seedu.tinner.logic.parser.ParserUtil.arePrefixesPresent;

import javafx.util.Pair;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.AddRoleCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.Description;
import seedu.tinner.model.role.Role;
import seedu.tinner.model.role.RoleName;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;

/**
 * Parses input arguments and creates a new AddRoleCommand object
 */
public class AddRoleCommandParser implements Parser<AddRoleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddRoleCommand
     * and returns an AddRoleCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddRoleCommand parse(String args) throws ParseException {
        Pair<Index, String> parsedInput;
        try {
            parsedInput = ParserUtil.parseIndexWithContent(args);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoleCommand.MESSAGE_USAGE), pe);
        }

        Index index = parsedInput.getKey();
        String content = parsedInput.getValue();
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(content, PREFIX_NAME, PREFIX_STATUS,
                        PREFIX_DEADLINE, PREFIX_DESCRIPTION, PREFIX_STIPEND);
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_STATUS, PREFIX_DEADLINE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRoleCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_NAME)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_STATUS)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_DEADLINE)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_DESCRIPTION)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_STIPEND)) {
            throw new ParseException(String.format(MESSAGE_NO_VALUE_AFTER_PREFIX,
                    AddRoleCommand.MESSAGE_USAGE));
        }

        RoleName name = ParserUtil.parseRoleName(argMultimap.getValue(PREFIX_NAME).get());
        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        Deadline deadline = ParserUtil.parseDeadline(argMultimap.getValue(PREFIX_DEADLINE).get());
        Description description = ParserUtil.parseDescription(argMultimap.getOptionalValue(PREFIX_DESCRIPTION).get());
        Stipend stipend = ParserUtil.parseStipend(argMultimap.getOptionalValue(PREFIX_STIPEND).get());
        Role role = new Role(name, status, deadline, description, stipend);
        return new AddRoleCommand(index, role);
    }

}
