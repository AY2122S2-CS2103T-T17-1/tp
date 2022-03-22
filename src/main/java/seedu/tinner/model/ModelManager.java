package seedu.tinner.model;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tinner.commons.core.GuiSettings;
import seedu.tinner.commons.core.LogsCenter;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.RoleManager;
import seedu.tinner.model.role.Role;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final CompanyList companyList;
    private final UserPrefs userPrefs;
    private final FilteredList<Company> filteredCompanies;

    /**
     * Initializes a ModelManager with the given companyList and userPrefs.
     */
    public ModelManager(ReadOnlyCompanyList companyList, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(companyList, userPrefs);

        logger.fine("Initializing with address book: " + companyList + " and user prefs " + userPrefs);

        this.companyList = new CompanyList(companyList);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredCompanies = new FilteredList<>(this.companyList.getCompanyList());
    }

    public ModelManager() {
        this(new CompanyList(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getCompanyListFilePath() {
        return userPrefs.getCompanyListFilePath();
    }

    @Override
    public void setCompanyListFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setCompanyListFilePath(addressBookFilePath);
    }

    //=========== CompanyList ================================================================================

    @Override
    public void setCompanyList(ReadOnlyCompanyList companyList) {
        this.companyList.resetData(companyList);
    }

    @Override
    public ReadOnlyCompanyList getCompanyList() {
        return companyList;
    }

    @Override
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return companyList.hasCompany(company);
    }

    @Override
    public void deleteCompany(Company target) {
        companyList.removeCompany(target);
    }

    @Override
    public void addCompany(Company company) {
        companyList.addCompany(company);
        updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES, PREDICATE_SHOW_ALL_ROLES);
    }

    @Override
    public void setCompany(Company target, Company editedCompany) {
        requireAllNonNull(target, editedCompany);
        companyList.setCompany(target, editedCompany);
    }

    //=========== Filtered Company List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Company} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Company> getFilteredCompanyList() {
        return filteredCompanies;
    }

    @Override
    public void updateFilteredCompanyList(Predicate<Company> companyPredicate, Predicate<Role> rolePredicate) {
        requireAllNonNull(companyPredicate, rolePredicate);
        filteredCompanies.setPredicate(companyPredicate);
        filteredCompanies.forEach(company -> company.getRoleManager().updateFilteredRoleList(rolePredicate));
        assert(filteredCompanies.stream().allMatch(companyPredicate));
        assert(filteredCompanies.stream().allMatch(company -> company.getRoleManager().getFilteredRoleList()
                .stream().allMatch(rolePredicate)));
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return companyList.equals(other.companyList)
                && userPrefs.equals(other.userPrefs)
                && filteredCompanies.equals(other.filteredCompanies);
    }

    //=========== RoleManager ================================================================================
    @Override
    public void addRole (Index companyIndex, Role role) {
        assert(companyIndex.getZeroBased() < filteredCompanies.size());
        requireNonNull(role);

        Company company = this.filteredCompanies.get(companyIndex.getZeroBased());
        RoleManager roleManager = company.getRoleManager();
        roleManager.addRole(role);
    }

    @Override
    public boolean hasRole (Index companyIndex, Role role) {
        assert(companyIndex.getZeroBased() < filteredCompanies.size());
        requireNonNull(role);

        Company company = this.filteredCompanies.get(companyIndex.getZeroBased());
        RoleManager roleManager = company.getRoleManager();
        return roleManager.hasRole(role);
    }

    @Override
    public void deleteRole(Index companyIndex, Role roleToDelete) {
        assert(companyIndex.getZeroBased() < filteredCompanies.size());
        requireNonNull(roleToDelete);

        Company company = this.filteredCompanies.get(companyIndex.getZeroBased());
        RoleManager roleManager = company.getRoleManager();
        roleManager.deleteRole(roleToDelete);
    }

    @Override
    public void setRole(Index companyIndex, Role target, Role editedRole) {
        assert(companyIndex.getZeroBased() < filteredCompanies.size());
        requireAllNonNull(target, editedRole);

        Company company = this.filteredCompanies.get(companyIndex.getZeroBased());
        RoleManager roleManager = company.getRoleManager();
        roleManager.setRole(target, editedRole);
    }

    @Override
    public void updateFilteredRoleList(Index companyIndex, Predicate<Role> predicate) {
        assert(companyIndex.getZeroBased() < filteredCompanies.size());

        Company company = this.filteredCompanies.get(companyIndex.getZeroBased());
        RoleManager roleManager = company.getRoleManager();
        roleManager.updateFilteredRoleList(predicate);
    }

    //=========== Filtered Company List Accessors =============================================================
    @Override
    public ObservableList<Role> getFilteredRoleList(Index companyIndex) {
        assert(companyIndex.getZeroBased() < filteredCompanies.size());

        Company company = this.filteredCompanies.get(companyIndex.getZeroBased());
        RoleManager roleManager = company.getRoleManager();
        return roleManager.getFilteredRoleList();
    }











}
