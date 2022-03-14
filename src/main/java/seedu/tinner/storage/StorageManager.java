package seedu.tinner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tinner.commons.core.LogsCenter;
import seedu.tinner.commons.exceptions.DataConversionException;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.ReadOnlyUserPrefs;
import seedu.tinner.model.UserPrefs;

/**
 * Manages storage of CompanyList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private CompanyListStorage companyListStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code CompanyListStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(CompanyListStorage companyListStorage, UserPrefsStorage userPrefsStorage) {
        this.companyListStorage = companyListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ CompanyList methods ==============================

    @Override
    public Path getCompanyListFilePath() {
        return companyListStorage.getCompanyListFilePath();
    }

    @Override
    public Optional<ReadOnlyCompanyList> readCompanyList() throws DataConversionException, IOException {
        return readCompanyList(companyListStorage.getCompanyListFilePath());
    }

    @Override
    public Optional<ReadOnlyCompanyList> readCompanyList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return companyListStorage.readCompanyList(filePath);
    }

    @Override
    public void saveCompanyList(ReadOnlyCompanyList companyList) throws IOException {
        saveCompanyList(companyList, companyListStorage.getCompanyListFilePath());
    }

    @Override
    public void saveCompanyList(ReadOnlyCompanyList companyList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        companyListStorage.saveCompanyList(companyList, filePath);
    }

}
