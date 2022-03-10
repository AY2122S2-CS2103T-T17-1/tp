package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE_NAME;

import java.util.Arrays;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.company.CompanyNameContainsKeywordsPredicate;
import seedu.address.model.role.RoleNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_COMPANY_NAME, PREFIX_ROLE_NAME);

        if ((!arePrefixesPresent(argMultimap, PREFIX_COMPANY_NAME)
                && !arePrefixesPresent(argMultimap, PREFIX_ROLE_NAME)) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }

        String companyName = argMultimap.getOptionalValue(PREFIX_COMPANY_NAME).get();
        String roleName = argMultimap.getOptionalValue(PREFIX_ROLE_NAME).get();

        String trimmedCompanyName = companyName.trim();
        String trimmedRoleName = roleName.trim();

        if (trimmedCompanyName.isEmpty() && trimmedRoleName.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] roleNameKeywords = trimmedRoleName.split("\\s+");
        String[] companyNameKeywords = trimmedCompanyName.split("\\s+");

        if (trimmedRoleName.isEmpty()) {
            roleNameKeywords = new String[0];
        }
        if (trimmedCompanyName.isEmpty()) {
            companyNameKeywords = new String[0];
        }

        return new FindCommand(new CompanyNameContainsKeywordsPredicate(Arrays.asList(roleNameKeywords),
                Arrays.asList(companyNameKeywords)),
                new RoleNameContainsKeywordsPredicate(Arrays.asList(roleNameKeywords)));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
