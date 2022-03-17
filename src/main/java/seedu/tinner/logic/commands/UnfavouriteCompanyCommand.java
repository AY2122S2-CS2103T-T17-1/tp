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
 * Unfavourites a company identified using it's displayed index from the address book.
 */
public class UnfavouriteCompanyCommand extends Command {

    public static final String COMMAND_WORD = "unfavourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFAVOURITE_COMPANY_SUCCESS = "Unfavourited Company: %1$s";

    public static final String MESSAGE_ALREADY_UNFAVOURITED_COMPANY = "This company is already unfavourited.";

    private final Index targetIndex;

    public UnfavouriteCompanyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToUnfavourite = lastShownList.get(targetIndex.getZeroBased());

        if (!companyToUnfavourite.getFavouriteStatus().value) {
            throw new CommandException(MESSAGE_ALREADY_UNFAVOURITED_COMPANY);
        }

        Company unfavouritedCompany = createUnfavouritedCompany(companyToUnfavourite);

        model.setCompany(companyToUnfavourite, unfavouritedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES, PREDICATE_SHOW_ALL_ROLES);

        return new CommandResult(String.format(MESSAGE_UNFAVOURITE_COMPANY_SUCCESS, unfavouritedCompany));
    }

    /**
     * Creates and returns a {@code Company} with the details of {@code companyToUnfavourite}
     * other than its {@code FavouriteStatus} which is updated with the {@code isFavourited} field set to
     * false
     */
    public static Company createUnfavouritedCompany(Company companyToUnfavourite) {
        assert companyToUnfavourite != null;

        CompanyName companyName = companyToUnfavourite.getName();
        Phone phone = companyToUnfavourite.getPhone();
        Email email = companyToUnfavourite.getEmail();
        Address address = companyToUnfavourite.getAddress();
        ReadOnlyRoleList roles = companyToUnfavourite.getRoleManager().getRoleList();
        FavouriteStatus favouriteStatus = new FavouriteStatus(false);

        return new Company(companyName, phone, email, address, roles, favouriteStatus);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCompanyCommand // instanceof handles nulls
                && targetIndex.equals(((UnfavouriteCompanyCommand) other).targetIndex)); // state check
    }
}
