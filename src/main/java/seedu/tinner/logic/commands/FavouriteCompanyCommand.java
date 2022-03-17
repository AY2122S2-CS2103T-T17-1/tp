package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;

import java.util.List;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.FavouriteStatus;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.company.ReadOnlyRoleList;


/**
 * Favourites a company identified using it's displayed index from the address book.
 */
public class FavouriteCompanyCommand extends Command {

    public static final String COMMAND_WORD = "favourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_COMPANY_SUCCESS = "Favourited Company: %1$s";

    public static final String MESSAGE_ALREADY_FAVOURITED_COMPANY = "This company has already been favourited.";

    private final Index targetIndex;

    public FavouriteCompanyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToFavourite = lastShownList.get(targetIndex.getZeroBased());

        if (companyToFavourite.getFavouriteStatus().value) {
            throw new CommandException(MESSAGE_ALREADY_FAVOURITED_COMPANY);
        }

        Company favouritedCompany = createFavouritedCompany(companyToFavourite);

        model.setCompany(companyToFavourite, favouritedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES, PREDICATE_SHOW_ALL_ROLES);

        return new CommandResult(String.format(MESSAGE_FAVOURITE_COMPANY_SUCCESS, favouritedCompany));
    }

    /**
     * Creates and returns a {@code Company} with the details of {@code companyToFavourite}
     * other than its {@code FavouriteStatus} which is updated with the {@code isFavourited} field set to
     * true
     */
    public static Company createFavouritedCompany(Company companyToFavourite) {
        assert companyToFavourite != null;

        CompanyName companyName = companyToFavourite.getName();
        Phone phone = companyToFavourite.getPhone();
        Email email = companyToFavourite.getEmail();
        Address address = companyToFavourite.getAddress();
        ReadOnlyRoleList roles = companyToFavourite.getRoleManager().getRoleList();
        FavouriteStatus favouriteStatus = new FavouriteStatus(true);

        return new Company(companyName, phone, email, address, roles, favouriteStatus);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCompanyCommand // instanceof handles nulls
                && targetIndex.equals(((FavouriteCompanyCommand) other).targetIndex)); // state check
    }
}
