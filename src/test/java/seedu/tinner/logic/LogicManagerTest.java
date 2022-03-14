package seedu.tinner.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX;
import static seedu.tinner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.tinner.logic.commands.CommandTestUtil.ADDRESS_DESC_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.EMAIL_DESC_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.NAME_DESC_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.PHONE_DESC_INSTAGRAM;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalCompanies.INSTAGRAM;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.tinner.logic.commands.AddCompanyCommand;
import seedu.tinner.logic.commands.CommandResult;
import seedu.tinner.logic.commands.ListCommand;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.company.Company;
import seedu.tinner.storage.JsonCompanyListStorage;
import seedu.tinner.storage.JsonUserPrefsStorage;
import seedu.tinner.storage.StorageManager;
import seedu.tinner.testutil.CompanyBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonCompanyListStorage addressBookStorage =
                new JsonCompanyListStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "deleteCompany 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonCompanyListIoExceptionThrowingStub
        JsonCompanyListStorage addressBookStorage =
                new JsonCompanyListIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addCompanyCommand = AddCompanyCommand.COMMAND_WORD + NAME_DESC_INSTAGRAM
                + PHONE_DESC_INSTAGRAM + EMAIL_DESC_INSTAGRAM + ADDRESS_DESC_INSTAGRAM;
        Company expectedCompany = new CompanyBuilder(INSTAGRAM).withRoles().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addCompany(expectedCompany);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCompanyCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredCompanyList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredCompanyList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonCompanyListIoExceptionThrowingStub extends JsonCompanyListStorage {
        private JsonCompanyListIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveCompanyList(ReadOnlyCompanyList companyList, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
