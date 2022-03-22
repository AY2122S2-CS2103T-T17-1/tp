package seedu.tinner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_ROLE;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_ROLE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.role.Role;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteRoleCommand}.
 */
public class DeleteRoleCommandTest {
    private Model model = new ModelManager(getTypicalCompanyList(), new UserPrefs());

    @Test
    public void execute_validIndexList_success() {
        List<Role> lastShownRoleList = model.getFilteredRoleList(INDEX_FIRST_COMPANY);
        Role roleToDelete = lastShownRoleList.get(INDEX_FIRST_ROLE.getZeroBased());
        DeleteRoleCommand deleteRoleCommand = new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE);

        String expectedMessage = String.format(DeleteRoleCommand.MESSAGE_DELETE_ROLE_SUCCESS, roleToDelete);
        ModelManager expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        assertCommandSuccess(deleteRoleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexList_throwsCommandException() {
        // test invalid company index
        Index outOfCompanyBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        DeleteRoleCommand deleteRoleCommand1 = new DeleteRoleCommand(outOfCompanyBoundIndex, INDEX_FIRST_ROLE);
        assertCommandFailure(deleteRoleCommand1, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);

        // test invalid role index
        Index outOfRoleBoundIndex = Index.fromOneBased(model.getFilteredRoleList(INDEX_FIRST_COMPANY).size() + 1);
        DeleteRoleCommand deleteRoleCommand2 = new DeleteRoleCommand(INDEX_FIRST_COMPANY, outOfRoleBoundIndex);
        assertCommandFailure(deleteRoleCommand2, model, Messages.MESSAGE_INVALID_ROLE_DISPLAYED_INDEX);

        // test both invalid company and role indexes
        DeleteRoleCommand deleteRoleCommand3 = new DeleteRoleCommand(outOfCompanyBoundIndex, outOfRoleBoundIndex);
        assertCommandFailure(deleteRoleCommand3, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteRoleCommand deleteRoleCommand1 = new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE);
        DeleteRoleCommand deleteRoleCommand2 = new DeleteRoleCommand(INDEX_SECOND_COMPANY, INDEX_SECOND_ROLE);
        DeleteRoleCommand deleteRoleCommand3 = new DeleteRoleCommand(INDEX_SECOND_COMPANY, INDEX_FIRST_ROLE);

        // same object -> returns true
        assertTrue(deleteRoleCommand1.equals(deleteRoleCommand1));

        // same values -> returns true
        DeleteRoleCommand deleteRoleCommand1Copy = new DeleteRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE);
        assertTrue(deleteRoleCommand1.equals(deleteRoleCommand1Copy));

        // different types -> returns false
        assertFalse(deleteRoleCommand1.equals(1));

        // null -> returns false
        assertFalse(deleteRoleCommand1.equals(null));

        // different role -> returns false
        assertFalse(deleteRoleCommand1.equals(deleteRoleCommand2));

        // different company -> returns false
        assertFalse(deleteRoleCommand1.equals(deleteRoleCommand3));
    }
}
