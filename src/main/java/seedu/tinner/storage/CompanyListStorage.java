package seedu.tinner.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tinner.commons.exceptions.DataConversionException;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.ReadOnlyCompanyList;

/**
 * Represents a storage for {@link CompanyList}.
 */
public interface CompanyListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCompanyListFilePath();

    /**
     * Returns CompanyList data as a {@link ReadOnlyCompanyList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyCompanyList> readCompanyList() throws DataConversionException, IOException;

    /**
     * @see #getCompanyListFilePath()
     */
    Optional<ReadOnlyCompanyList> readCompanyList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyCompanyList} to the storage.
     * @param companyList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCompanyList(ReadOnlyCompanyList companyList) throws IOException;

    /**
     * @see #saveCompanyList(ReadOnlyCompanyList)
     */
    void saveCompanyList(ReadOnlyCompanyList companyList, Path filePath) throws IOException;

}
