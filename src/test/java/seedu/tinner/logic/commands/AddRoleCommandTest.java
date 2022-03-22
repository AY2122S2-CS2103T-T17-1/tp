package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
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

public class AddRoleCommandTest {

    @Test
    public void constructor_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCompanyCommand(null));
    }

    @Test
    public void execute_companyAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCompanyAdded modelStub = new ModelStubAcceptingCompanyAdded();
        Company validCompany = new CompanyBuilder().build();

        CommandResult commandResult = new AddCompanyCommand(validCompany).execute(modelStub);

        assertEquals(String.format(AddCompanyCommand.MESSAGE_SUCCESS, validCompany),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validCompany), modelStub.companiesAdded);
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
        Company amazon = new CompanyBuilder().withName("Amazon").build();
        Company netflix = new CompanyBuilder().withName("Netflix").build();
        AddCompanyCommand addAmazonCommand = new AddCompanyCommand(amazon);
        AddCompanyCommand addNetflixCommand = new AddCompanyCommand(netflix);

        // same object -> returns true
        assertTrue(addAmazonCommand.equals(addAmazonCommand));

        // same values -> returns true
        AddCompanyCommand addAmazonCommandCopy = new AddCompanyCommand(amazon);
        assertTrue(addAmazonCommand.equals(addAmazonCommandCopy));

        // different types -> returns false
        assertFalse(addAmazonCommand.equals(1));

        // null -> returns false
        assertFalse(addAmazonCommand.equals(null));

        // different company -> returns false
        assertFalse(addAmazonCommand.equals(addNetflixCommand));
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
        private final Company company;

        ModelStubWithCompany(Company company) {
            requireNonNull(company);
            this.company = company;
        }

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return this.company.isSameCompany(company);
        }
    }

    /**
     * A Model stub that always accept the company being added.
     */
    private class ModelStubAcceptingCompanyAdded extends ModelStub {
        final ArrayList<Company> companiesAdded = new ArrayList<>();

        @Override
        public boolean hasCompany(Company company) {
            requireNonNull(company);
            return companiesAdded.stream().anyMatch(company::isSameCompany);
        }

        @Override
        public void addCompany(Company company) {
            requireNonNull(company);
            companiesAdded.add(company);
        }

        @Override
        public ReadOnlyCompanyList getCompanyList() {
            return new CompanyList();
        }
    }

}
