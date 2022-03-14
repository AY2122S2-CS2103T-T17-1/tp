package seedu.tinner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.CommandTestUtil.DESC_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.DESC_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_NAME_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_PHONE_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tinner.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.company.Company;
import seedu.tinner.testutil.CompanyBuilder;
import seedu.tinner.testutil.EditCompanyDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCompanyCommandTest {

    private Model model = new ModelManager(getTypicalCompanyList(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Company editedCompany = new CompanyBuilder().build();
        EditCompanyCommand.EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(editedCompany).build();
        EditCompanyCommand editCommand = new EditCompanyCommand(INDEX_FIRST_COMPANY, descriptor);

        String expectedMessage = String.format(EditCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedCompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastCompany = Index.fromOneBased(model.getFilteredCompanyList().size());
        Company lastCompany = model.getFilteredCompanyList().get(indexLastCompany.getZeroBased());

        CompanyBuilder companyInList = new CompanyBuilder(lastCompany);
        Company editedCompany = companyInList.withName(VALID_NAME_WHATSAPP).withPhone(VALID_PHONE_WHATSAPP).build();

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP)
                .withPhone(VALID_PHONE_WHATSAPP).build();
        EditCompanyCommand editCommand = new EditCompanyCommand(indexLastCompany, descriptor);

        String expectedMessage = String.format(EditCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setCompany(lastCompany, editedCompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCompanyCommand editCommand = new EditCompanyCommand(
                INDEX_FIRST_COMPANY, new EditCompanyCommand.EditCompanyDescriptor());
        Company editedCompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());

        String expectedMessage = String.format(EditCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        Company companyInFilteredList = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        Company editedCompany = new CompanyBuilder(companyInFilteredList).withName(VALID_NAME_WHATSAPP).build();
        EditCompanyCommand editCommand = new EditCompanyCommand(INDEX_FIRST_COMPANY,
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP).build());

        String expectedMessage = String.format(EditCompanyCommand.MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany);

        Model expectedModel = new ModelManager(new CompanyList(model.getCompanyList()), new UserPrefs());
        expectedModel.setCompany(model.getFilteredCompanyList().get(0), editedCompany);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCompanyUnfilteredList_failure() {
        Company firstCompany = model.getFilteredCompanyList().get(INDEX_FIRST_COMPANY.getZeroBased());
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(firstCompany).build();
        EditCompanyCommand editCommand = new EditCompanyCommand(INDEX_SECOND_COMPANY, descriptor);

        assertCommandFailure(editCommand, model, EditCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_duplicateCompanyFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);

        // edit company in filtered list into a duplicate in address book
        Company companyInList = model.getCompanyList().getCompanyList().get(INDEX_SECOND_COMPANY.getZeroBased());
        EditCompanyCommand editCommand = new EditCompanyCommand(INDEX_FIRST_COMPANY,
                new EditCompanyDescriptorBuilder(companyInList).build());

        assertCommandFailure(editCommand, model, EditCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
    }

    @Test
    public void execute_invalidCompanyIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredCompanyList().size() + 1);
        EditCompanyCommand.EditCompanyDescriptor descriptor =
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP).build();
        EditCompanyCommand editCommand = new EditCompanyCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidCompanyIndexFilteredList_failure() {
        showCompanyAtIndex(model, INDEX_FIRST_COMPANY);
        Index outOfBoundIndex = INDEX_SECOND_COMPANY;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getCompanyList().getCompanyList().size());

        EditCompanyCommand editCompanyCommand = new EditCompanyCommand(outOfBoundIndex,
                new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP).build());

        assertCommandFailure(editCompanyCommand, model, Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCompanyCommand standardCommand = new EditCompanyCommand(INDEX_FIRST_COMPANY, DESC_INSTAGRAM);

        // same values -> returns true
        EditCompanyCommand.EditCompanyDescriptor copyDescriptor = new EditCompanyDescriptor(DESC_INSTAGRAM);
        EditCompanyCommand commandWithSameValues = new EditCompanyCommand(INDEX_FIRST_COMPANY, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCompanyCommand(INDEX_SECOND_COMPANY, DESC_INSTAGRAM)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCompanyCommand(INDEX_FIRST_COMPANY, DESC_WHATSAPP)));
    }

}
