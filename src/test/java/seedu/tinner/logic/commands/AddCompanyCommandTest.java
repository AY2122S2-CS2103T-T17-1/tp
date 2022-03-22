package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tinner.commons.core.GuiSettings;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.Model;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.ReadOnlyUserPrefs;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.role.Role;
import seedu.tinner.testutil.CompanyBuilder;

public class AddCompanyCommandTest {

    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCompanyCommand(null));
    }

    @Test
    public void execute_companyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCompanyAdded modelStub = new ModelStubAcceptingCompanyAdded();
        Company validCompany = new CompanyBuilder().build();
        CompanyList companyListWithValidCompany = new CompanyList();
        companyListWithValidCompany.addCompany(validCompany);

        CommandResult commandResult = new AddCompanyCommand(validCompany).execute(modelStub);

        assertEquals(String.format(AddCompanyCommand.MESSAGE_SUCCESS, validCompany), commandResult.getFeedbackToUser());
        assertEquals(companyListWithValidCompany, modelStub.companyList);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company validCompany = new CompanyBuilder().build();
        AddCompanyCommand addCompanyCommand = new AddCompanyCommand(validCompany);
        ModelStub modelStub = new ModelStubWithCompany(validCompany);

        assertThrows(CommandException.class, AddCompanyCommand.MESSAGE_DUPLICATE_COMPANY, () ->
                addCompanyCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Company alice = new CompanyBuilder().withName("Alice").build();
        Company bob = new CompanyBuilder().withName("Bob").build();
        AddCompanyCommand addAliceCommand = new AddCompanyCommand(alice);
        AddCompanyCommand addBobCommand = new AddCompanyCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCompanyCommand addAliceCommandCopy = new AddCompanyCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different company -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCompanyListFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompanyListFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCompany(Company company) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompanyList(ReadOnlyCompanyList newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCompanyList getCompanyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCompany(Company company) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCompany(Company target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCompany(Company target, Company editedCompany) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Company> getFilteredCompanyList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCompanyList(Predicate<Company> companyPredicate, Predicate<Role> rolePredicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRole(Index companyIndex, Role role) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRole(Index companyIndex, Role role) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Role> getFilteredRoleList(Index companyIndex) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRole(Index companyIndex, Role role) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRole(Index companyIndex, Role target, Role editedRole) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRoleList(Index companyIndex, Predicate<Role> predicate) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single company.
     */
    private class ModelStubWithCompany extends ModelStub {

        private final CompanyList companyList;
        private final FilteredList<Company> filteredCompanies;

        ModelStubWithCompany(Company company) {
            requireNonNull(company);
            CompanyList companyList = new CompanyList();
            companyList.setCompanies(List.of(company));
            this.companyList = companyList;
            filteredCompanies = new FilteredList<>(this.companyList.getCompanyList());
        }

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companyList.hasCompany(company);
        }
    }

    /**
     * A Model stub that always accept the company being added.
     */
    private class ModelStubAcceptingCompanyAdded extends ModelStub {

        private final CompanyList companyList = new CompanyList();

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companyList.hasCompany(company);
        }

        @Override
        public void addCompany(Company company) {
            requireNonNull(company);
            companyList.addCompany(company);
        }

        @Override
        public void updateFilteredCompanyList(Predicate<Company> companyPredicate, Predicate<Role> rolePredicate) {
        }

        @Override
        public ReadOnlyCompanyList getCompanyList() {
            return new CompanyList();
        }
    }

}
