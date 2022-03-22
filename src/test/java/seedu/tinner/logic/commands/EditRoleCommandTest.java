package seedu.tinner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_NAME_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_NAME_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_STATUS_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_STIPEND_SOFTWARE_ENGINEER;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_ROLE;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_ROLE;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.role.Role;
import seedu.tinner.testutil.EditRoleDescriptorBuilder;
import seedu.tinner.testutil.RoleBuilder;

public class EditRoleCommandTest {
    private Model model = new ModelManager(getTypicalCompanyList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        Role editedRole = new RoleBuilder().build();
        EditRoleCommand.EditRoleDescriptor descriptor = new EditRoleDescriptorBuilder(editedRole).build();
        EditRoleCommand editRoleCommand = new EditRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE,
                descriptor);
        String expectedMessage = String.format(EditRoleCommand.MESSAGE_EDIT_ROLE_SUCCESS, editedRole);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        List<Role> expectedRoleList = expectedModel.getFilteredRoleList(INDEX_FIRST_COMPANY);
        Role expectedRole = expectedRoleList.get(INDEX_FIRST_ROLE.getZeroBased());
        expectedModel.setRole(INDEX_FIRST_COMPANY, expectedRole, editedRole);

        assertCommandSuccess(editRoleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        Index indexLastCompany = Index.fromOneBased(model.getFilteredCompanyList().size());
        Index indexLastRole = Index.fromOneBased(model.getFilteredRoleList(indexLastCompany).size());
        Role lastRole = model.getFilteredRoleList(indexLastCompany).get(indexLastRole.getZeroBased());

        RoleBuilder roleInList = new RoleBuilder(lastRole);
        Role editedRole = roleInList.withName(VALID_NAME_WHATSAPP)
                .withStatus(VALID_STATUS_SOFTWARE_ENGINEER).withStipend(VALID_STIPEND_SOFTWARE_ENGINEER)
                .build();

        EditRoleCommand.EditRoleDescriptor descriptor = new EditRoleDescriptorBuilder().withName(VALID_NAME_WHATSAPP)
                .withStatus(VALID_STATUS_SOFTWARE_ENGINEER).withStipend(VALID_STIPEND_SOFTWARE_ENGINEER).build();

        EditRoleCommand editRoleCommand = new EditRoleCommand(indexLastCompany, indexLastRole, descriptor);

        String expectedMessage = String.format(EditRoleCommand.MESSAGE_EDIT_ROLE_SUCCESS, editedRole);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setRole(indexLastCompany, lastRole, editedRole);

        assertCommandSuccess(editRoleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        EditRoleCommand editRoleCommand = new EditRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE,
                new EditRoleCommand.EditRoleDescriptor());

        Role editedRole = model.getFilteredRoleList(INDEX_FIRST_COMPANY).get(INDEX_FIRST_ROLE.getZeroBased());

        String expectedMessage = String.format(EditRoleCommand.MESSAGE_EDIT_ROLE_SUCCESS, editedRole);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());

        assertCommandSuccess(editRoleCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidRoleIndex_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredRoleList(INDEX_FIRST_COMPANY).size() + 1);

        EditRoleCommand.EditRoleDescriptor descriptor =
                new EditRoleDescriptorBuilder().withName(VALID_NAME_SOFTWARE_ENGINEER).build();
        EditRoleCommand editRoleCommand = new EditRoleCommand(INDEX_FIRST_COMPANY, outOfBoundIndex, descriptor);

        assertCommandFailure(editRoleCommand, model, Messages.MESSAGE_INVALID_ROLE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Role firstRole = model.getFilteredRoleList(INDEX_FIRST_COMPANY).get(INDEX_FIRST_ROLE.getZeroBased());
        EditRoleCommand.EditRoleDescriptor firstDescriptor = new EditRoleDescriptorBuilder(firstRole).build();

        final EditRoleCommand standardCommand = new EditRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE,
                firstDescriptor);

        // same values -> returns true
        EditRoleCommand.EditRoleDescriptor copyDescriptor = new EditRoleDescriptorBuilder(firstRole).build();
        EditRoleCommand commandWithSameValues = new EditRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE,
                copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditRoleCommand(INDEX_FIRST_COMPANY, INDEX_SECOND_ROLE,
                firstDescriptor)));

        // different descriptor -> returns false
        Role diffRole = new RoleBuilder().build();
        EditRoleCommand.EditRoleDescriptor diffDescriptor = new EditRoleDescriptorBuilder(diffRole).build();
        assertFalse(standardCommand.equals(new EditRoleCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_ROLE, diffDescriptor)));
    }
}
