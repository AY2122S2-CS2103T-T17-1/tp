package seedu.tinner.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalCompanies.ASANA;
import static seedu.tinner.testutil.TypicalCompanies.GOOGLE;
import static seedu.tinner.testutil.TypicalCompanies.META;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tinner.commons.exceptions.DataConversionException;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.ReadOnlyCompanyList;

public class JsonCompanyListStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonCompanyListStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readCompanyList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readCompanyList(null));
    }

    private java.util.Optional<ReadOnlyCompanyList> readCompanyList(String filePath) throws Exception {
        return new JsonCompanyListStorage(Paths.get(filePath)).readCompanyList(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readCompanyList("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readCompanyList("notJsonFormatCompanyList.json"));
    }

    @Test
    public void readCompanyList_invalidCompanyCompanyList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCompanyList("invalidCompanyList.json"));
    }

    @Test
    public void readCompanyList_invalidAndValidCompanyList_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readCompanyList("invalidAndValidCompanyList.json"));
    }

    @Test
    public void readAndSaveCompanyList_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempCompanyList.json");
        CompanyList original = getTypicalCompanyList();
        JsonCompanyListStorage jsonCompanyListStorage = new JsonCompanyListStorage(filePath);

        // Save in new file and read back
        jsonCompanyListStorage.saveCompanyList(original, filePath);
        ReadOnlyCompanyList readBack = jsonCompanyListStorage.readCompanyList(filePath).get();
        assertEquals(original, new CompanyList(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addCompany(ASANA);
        original.removeCompany(META);
        jsonCompanyListStorage.saveCompanyList(original, filePath);
        readBack = jsonCompanyListStorage.readCompanyList(filePath).get();
        assertEquals(original, new CompanyList(readBack));

        // Save and read without specifying file path
        original.addCompany(GOOGLE);
        jsonCompanyListStorage.saveCompanyList(original); // file path not specified
        readBack = jsonCompanyListStorage.readCompanyList().get(); // file path not specified
        assertEquals(original, new CompanyList(readBack));

    }

    @Test
    public void saveCompanyList_nullCompanyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCompanyList(null, "SomeFile.json"));
    }

    /**
     * Saves {@code companyList} at the specified {@code filePath}.
     */
    private void saveCompanyList(ReadOnlyCompanyList companyList, String filePath) {
        try {
            new JsonCompanyListStorage(Paths.get(filePath))
                    .saveCompanyList(companyList, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveCompanyList_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveCompanyList(new CompanyList(), null));
    }
}
