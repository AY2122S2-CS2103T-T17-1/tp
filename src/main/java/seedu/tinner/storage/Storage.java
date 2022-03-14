package seedu.tinner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tinner.commons.exceptions.DataConversionException;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.ReadOnlyUserPrefs;
import seedu.tinner.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends CompanyListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCompanyListFilePath();

    @Override
    Optional<ReadOnlyCompanyList> readCompanyList() throws DataConversionException, IOException;

    @Override
    void saveCompanyList(ReadOnlyCompanyList addressBook) throws IOException;

}
