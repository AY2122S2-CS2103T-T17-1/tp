package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tinner.logic.commands.AddCompanyCommand;
import seedu.tinner.logic.commands.AddRoleCommand;
import seedu.tinner.logic.commands.ClearCommand;
import seedu.tinner.logic.commands.Command;
import seedu.tinner.logic.commands.DeleteCompanyCommand;
import seedu.tinner.logic.commands.DeleteRoleCommand;
import seedu.tinner.logic.commands.EditCompanyCommand;
import seedu.tinner.logic.commands.EditRoleCommand;
import seedu.tinner.logic.commands.ExitCommand;
import seedu.tinner.logic.commands.FavouriteCompanyCommand;
import seedu.tinner.logic.commands.FindCommand;
import seedu.tinner.logic.commands.HelpCommand;
import seedu.tinner.logic.commands.ListCommand;
import seedu.tinner.logic.commands.ListFavouriteCommand;
import seedu.tinner.logic.commands.UnfavouriteCompanyCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class CompanyListParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCompanyCommand.COMMAND_WORD:
            return new AddCompanyCommandParser().parse(arguments);

        case EditCompanyCommand.COMMAND_WORD:
            return new EditCompanyCommandParser().parse(arguments);

        case DeleteCompanyCommand.COMMAND_WORD:
            return new DeleteCompanyCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ListFavouriteCommand.COMMAND_WORD:
            return new ListFavouriteCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddRoleCommand.COMMAND_WORD:
            return new AddRoleCommandParser().parse(arguments);

        case DeleteRoleCommand.COMMAND_WORD:
            return new DeleteRoleCommandParser().parse(arguments);

        case EditRoleCommand.COMMAND_WORD:
            return new EditRoleCommandParser().parse(arguments);

        case FavouriteCompanyCommand.COMMAND_WORD:
            return new FavouriteCompanyCommandParser().parse(arguments);

        case UnfavouriteCompanyCommand.COMMAND_WORD:
            return new UnfavouriteCompanyCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
