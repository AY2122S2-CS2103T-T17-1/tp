package seedu.tinner.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tinner.commons.core.GuiSettings;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonCompanyListStorage companyListStorage = new JsonCompanyListStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(companyListStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void companyListReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonCompanyListStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonCompanyListStorageTest} class.
         */
        CompanyList original = getTypicalCompanyList();
        storageManager.saveCompanyList(original);
        ReadOnlyCompanyList retrieved = storageManager.readCompanyList().get();
        assertEquals(original, new CompanyList(retrieved));
    }

    @Test
    public void getCompanyListFilePath() {
        assertNotNull(storageManager.getCompanyListFilePath());
    }

}
