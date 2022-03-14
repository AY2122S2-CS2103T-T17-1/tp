package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_COMPANY_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_ROLE_NAME;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.CompanyNameContainsKeywordsPredicate;
import seedu.tinner.model.role.RoleNameContainsKeywordsPredicate;

/**
 * Finds and lists all companies or roles whose name contains any of the argument keywords.
 * Search type of search conducted depends on the format of user input.
 * Keyword matching is case-insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all companies whose names "
            + "contain any of "
            + "the specified keywords (case-insensitive) and roles contains any of the specified keywords"
            + "(case-insensitive) displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_COMPANY_NAME
            + "COMPANY_NAME "
            + PREFIX_ROLE_NAME
            + "ROLE_NAME"
            + "\n"
            + "Example: " + COMMAND_WORD + " c/amazon r/engineer";

    private final CompanyNameContainsKeywordsPredicate companyPredicate;
    private final RoleNameContainsKeywordsPredicate rolePredicate;

    /**
     * @param companyPredicate Predicate provided to filter through companies
     * @param rolePredicate    Predicate provided to filter through roles
     */
    public FindCommand(CompanyNameContainsKeywordsPredicate companyPredicate,
                       RoleNameContainsKeywordsPredicate rolePredicate) {
        this.companyPredicate = companyPredicate;
        this.rolePredicate = rolePredicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredCompanyList(companyPredicate, rolePredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_COMPANIES_LISTED_OVERVIEW, model.getFilteredCompanyList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && companyPredicate.equals(((FindCommand) other).companyPredicate)
                && rolePredicate.equals(((FindCommand) other).rolePredicate)); // state check
    }
}
