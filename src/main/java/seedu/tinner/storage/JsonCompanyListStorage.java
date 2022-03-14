package seedu.tinner.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tinner.commons.core.LogsCenter;
import seedu.tinner.commons.exceptions.DataConversionException;
import seedu.tinner.commons.exceptions.IllegalValueException;
import seedu.tinner.commons.util.FileUtil;
import seedu.tinner.commons.util.JsonUtil;
import seedu.tinner.model.ReadOnlyCompanyList;

/**
 * A class to access CompanyList data stored as a json file on the hard disk.
 */
public class JsonCompanyListStorage implements CompanyListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonCompanyListStorage.class);

    private Path filePath;

    public JsonCompanyListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getCompanyListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyCompanyList> readCompanyList() throws DataConversionException {
        return readCompanyList(filePath);
    }

    /**
     * Similar to {@link #readCompanyList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyCompanyList> readCompanyList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableCompanyList> jsonCompanyList = JsonUtil.readJsonFile(
                filePath, JsonSerializableCompanyList.class);
        if (!jsonCompanyList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonCompanyList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveCompanyList(ReadOnlyCompanyList companyList) throws IOException {
        saveCompanyList(companyList, filePath);
    }

    /**
     * Similar to {@link #saveCompanyList(ReadOnlyCompanyList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveCompanyList(ReadOnlyCompanyList companyList, Path filePath) throws IOException {
        requireNonNull(companyList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableCompanyList(companyList), filePath);
    }

}
