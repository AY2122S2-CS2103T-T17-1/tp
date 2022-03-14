package seedu.tinner.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalCompanies.AMAZON;
import static seedu.tinner.testutil.TypicalCompanies.META;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.core.GuiSettings;
import seedu.tinner.model.company.CompanyNameContainsKeywordsPredicate;
import seedu.tinner.model.role.RoleNameContainsKeywordsPredicate;
import seedu.tinner.testutil.CompanyListBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new CompanyList(), new CompanyList(modelManager.getCompanyList()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setCompanyListFilePath(Paths.get("address/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setCompanyListFilePath(Paths.get("new/address/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setCompanyListFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setCompanyListFilePath(null));
    }

    @Test
    public void setCompanyListFilePath_validPath_setsCompanyListFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setCompanyListFilePath(path);
        assertEquals(path, modelManager.getCompanyListFilePath());
    }

    @Test
    public void hasCompany_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasCompany(null));
    }

    @Test
    public void hasCompany_companyNotInCompanyList_returnsFalse() {
        assertFalse(modelManager.hasCompany(META));
    }

    @Test
    public void hasCompany_companyInCompanyList_returnsTrue() {
        modelManager.addCompany(META);
        assertTrue(modelManager.hasCompany(META));
    }

    @Test
    public void getFilteredCompanyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                modelManager.getFilteredCompanyList().remove(0));
    }

    @Test
    public void equals() {
        CompanyList companyList =
                new CompanyListBuilder().withCompany(META).withCompany(AMAZON).build();
        CompanyList differentCompanyList = new CompanyList();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(companyList, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(companyList, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different companyList -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentCompanyList, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = META.getName().fullName.split("\\s+");
        modelManager.updateFilteredCompanyList(new CompanyNameContainsKeywordsPredicate(Arrays.asList(keywords),
                        Arrays.asList(keywords)), new RoleNameContainsKeywordsPredicate(Arrays.asList(" ")));
        assertFalse(modelManager.equals(new ModelManager(companyList, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES, PREDICATE_SHOW_ALL_ROLES);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setCompanyListFilePath(Paths.get("differentFilePath"));
        assertFalse(modelManager.equals(new ModelManager(companyList, differentUserPrefs)));
    }
}
