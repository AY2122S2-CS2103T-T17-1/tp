package seedu.tinner.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tinner.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.tinner.commons.exceptions.IllegalValueException;
import seedu.tinner.commons.util.JsonUtil;
import seedu.tinner.model.CompanyList;
import seedu.tinner.testutil.TypicalCompanies;

public class JsonSerializableCompanyListTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableCompanyListTest");
    private static final Path TYPICAL_COMPANIES_FILE = TEST_DATA_FOLDER.resolve("typicalCompaniesList.json");
    private static final Path INVALID_COMPANY_FILE = TEST_DATA_FOLDER.resolve("invalidCompanyList.json");
    private static final Path DUPLICATE_COMPANY_FILE = TEST_DATA_FOLDER.resolve("duplicateCompanyList.json");

    @Test
    public void toModelType_typicalCompaniesFile_success() throws Exception {
        JsonSerializableCompanyList dataFromFile = JsonUtil.readJsonFile(TYPICAL_COMPANIES_FILE,
                JsonSerializableCompanyList.class).get();
        CompanyList companyListFromFile = dataFromFile.toModelType();
        CompanyList typicalCompaniesCompanyList = TypicalCompanies.getTypicalCompanyList();
        assertEquals(companyListFromFile, typicalCompaniesCompanyList);
    }

    @Test
    public void toModelType_invalidCompanyFile_throwsIllegalValueException() throws Exception {
        JsonSerializableCompanyList dataFromFile = JsonUtil.readJsonFile(INVALID_COMPANY_FILE,
                JsonSerializableCompanyList.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateCompanies_throwsIllegalValueException() throws Exception {
        JsonSerializableCompanyList dataFromFile = JsonUtil.readJsonFile(DUPLICATE_COMPANY_FILE,
                JsonSerializableCompanyList.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableCompanyList.MESSAGE_DUPLICATE_COMPANY,
                dataFromFile::toModelType);
    }

}
