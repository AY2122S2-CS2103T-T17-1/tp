package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;

import seedu.tinner.model.Model;
import seedu.tinner.model.company.CompanyHasBeenFavouritedPredicate;

/**
 * Lists all favourited companies in the address book to the user.
 */
public class ListFavouriteCommand extends Command {

    public static final String COMMAND_WORD = "listFavourite";

    public static final String MESSAGE_SUCCESS = "Listed all favourited companies";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        CompanyHasBeenFavouritedPredicate companyPredicate = new CompanyHasBeenFavouritedPredicate();
        model.updateFilteredCompanyList(companyPredicate, PREDICATE_SHOW_ALL_ROLES);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
