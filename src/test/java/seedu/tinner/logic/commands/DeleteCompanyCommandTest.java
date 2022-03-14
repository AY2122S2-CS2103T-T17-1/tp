package seedu.tinner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
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

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCompanyCommand}.
 */
public class DeleteCompanyCommandTest {

    private Model model = new ModelManager(getTypicalCompanyList(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS, companyToDelete);

        ModelManager expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);

        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(outOfBoundIndex);

        assertCommandFailure(deleteCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyToDelete = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);

        String expectedMessage = String.format(DeleteCompanyCommand.MESSAGE_DELETE_COMPANY_SUCCESS, companyToDelete);

        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.deleteCompany(companyToDelete);
        showNoCompany(expectedModel);

        assertCommandSuccess(deleteCompanyCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyList().getCompanyList().size());

        DeleteCompanyCommand deleteCompanyCommand = new DeleteCompanyCommand(outOfBoundIndex);

        assertCommandFailure(deleteCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCompanyCommand deleteFirstCommand = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);
        DeleteCompanyCommand deleteSecondCommand = new DeleteCompanyCommand(INDEX_SECOND_COMPANY);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        DeleteCompanyCommand deleteFirstCommandCopy = new DeleteCompanyCommand(INDEX_FIRST_COMPANY);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different company -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoCompany(Model model) {
        model.updateFilteredCompanyList(p -> false, p -> false);

        assertTrue(model.getFilteredCompanyList().isEmpty());
    }
}
