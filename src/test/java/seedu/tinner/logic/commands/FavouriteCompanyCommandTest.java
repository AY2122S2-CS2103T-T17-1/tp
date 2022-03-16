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
 * {@code FavouriteCompanyCommand}.
 */
public class FavouriteCompanyCommandTest {

    private Model model = new ModelManager(getTypicalCompanyList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToFavourite = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        FavouriteCompanyCommand favouriteCompanyCommand = new FavouriteCompanyCommand(INDEX_FIRST_COMPANY);

        Company favouritedCompany = new CompanyBuilder(companyToFavourite).setToFavourite().build();

        String expectedMessage = String.format(FavouriteCompanyCommand.MESSAGE_FAVOURITE_COMPANY_SUCCESS,
                favouritedCompany);

        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.setCompany(companyToFavourite, favouritedCompany);

        assertCommandSuccess(favouriteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        FavouriteCompanyCommand favouriteCompanyCommand = new FavouriteCompanyCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyToFavourite = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        FavouriteCompanyCommand favouriteCompanyCommand = new FavouriteCompanyCommand(INDEX_FIRST_COMPANY);

        Company favouritedCompany = new CompanyBuilder(companyToFavourite).setToFavourite().build();

        String expectedMessage = String.format(FavouriteCompanyCommand.MESSAGE_FAVOURITE_COMPANY_SUCCESS,
                favouritedCompany);

        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.setCompany(companyToFavourite, favouritedCompany);

        assertCommandSuccess(favouriteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyList().getCompanyList().size());

        FavouriteCompanyCommand favouriteCompanyCommand = new FavouriteCompanyCommand(outOfBoundIndex);

        assertCommandFailure(favouriteCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_companyAlreadyFavourited_throwsCommandException() {
        favouriteCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        FavouriteCompanyCommand favouriteCompanyCommand = new FavouriteCompanyCommand(INDEX_FIRST_COMPANY);

        assertCommandFailure(favouriteCompanyCommand, model,
                FavouriteCompanyCommand.MESSAGE_ALREADY_FAVOURITED_COMPANY);
    }

    @Test
    public void equals() {
        FavouriteCompanyCommand favouriteFirstCommand = new FavouriteCompanyCommand(INDEX_FIRST_COMPANY);
        FavouriteCompanyCommand favouriteSecondCommand = new FavouriteCompanyCommand(INDEX_SECOND_COMPANY);

        // same object -> returns true
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommand));

        // same values -> returns true
        FavouriteCompanyCommand favouriteFirstCommandCopy = new FavouriteCompanyCommand(INDEX_FIRST_COMPANY);
        assertTrue(favouriteFirstCommand.equals(favouriteFirstCommandCopy));

        // different types -> returns false
        assertFalse(favouriteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(favouriteFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(favouriteFirstCommand.equals(favouriteSecondCommand));
    }
}
