package seedu.tinner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tinner.logic.commands.CommandTestUtil.favouriteCompanyAtIndex;
import static seedu.tinner.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.company.Company;
import seedu.tinner.testutil.CompanyBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code UnfavouriteCompanyCommand}.
 */
public class UnfavouriteCompanyCommandTest {

    private Model model = new ModelManager(getTypicalCompanyList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        favouriteCompanyAtIndex(model, INDEX_FIRST_COMPANY);
        Company companyToUnfavourite = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        UnfavouriteCompanyCommand unfavouriteCompanyCommand = new UnfavouriteCompanyCommand(INDEX_FIRST_COMPANY);

        Company unfavouritedCompany = new CompanyBuilder(companyToUnfavourite).setToUnfavourite().build();

        String expectedMessage = String.format(UnfavouriteCompanyCommand.MESSAGE_UNFAVOURITE_COMPANY_SUCCESS,
                unfavouritedCompany);

        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.setCompany(companyToUnfavourite, unfavouritedCompany);

        assertCommandSuccess(unfavouriteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        UnfavouriteCompanyCommand unfavouriteCompanyCommand = new UnfavouriteCompanyCommand(outOfBoundIndex);

        assertCommandFailure(unfavouriteCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        favouriteCompanyAtIndex(model, INDEX_FIRST_COMPANY);
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyToUnfavourite = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        UnfavouriteCompanyCommand unfavouriteCompanyCommand = new UnfavouriteCompanyCommand(INDEX_FIRST_COMPANY);

        Company unfavouritedCompany = new CompanyBuilder(companyToUnfavourite).setToUnfavourite().build();

        String expectedMessage = String.format(UnfavouriteCompanyCommand.MESSAGE_UNFAVOURITE_COMPANY_SUCCESS,
                unfavouritedCompany);

        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.setCompany(companyToUnfavourite, unfavouritedCompany);

        assertCommandSuccess(unfavouriteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyList().getCompanyList().size());

        UnfavouriteCompanyCommand favouriteCompanyCommand = new UnfavouriteCompanyCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_companyAlreadyUnfavourited_throwsCommandException() {
        UnfavouriteCompanyCommand unfavouriteCompanyCommand = new UnfavouriteCompanyCommand(INDEX_FIRST_COMPANY);

        assertCommandFailure(unfavouriteCompanyCommand, model,
                UnfavouriteCompanyCommand.MESSAGE_ALREADY_UNFAVOURITED_COMPANY);
    }

    @Test
    public void equals() {
        UnfavouriteCompanyCommand unfavouriteFirstCommand = new UnfavouriteCompanyCommand(INDEX_FIRST_COMPANY);
        UnfavouriteCompanyCommand unfavouriteSecondCommand = new UnfavouriteCompanyCommand(INDEX_SECOND_COMPANY);

        // same object -> returns true
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommand));

        // same values -> returns true
        UnfavouriteCompanyCommand unfavouriteFirstCommandCopy = new UnfavouriteCompanyCommand(INDEX_FIRST_COMPANY);
        assertTrue(unfavouriteFirstCommand.equals(unfavouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(unfavouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(unfavouriteFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(unfavouriteFirstCommand.equals(unfavouriteSecondCommand));
    }
}
