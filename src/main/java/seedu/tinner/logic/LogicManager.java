package seedu.tinner.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.tinner.commons.core.GuiSettings;
import seedu.tinner.commons.core.LogsCenter;
import seedu.tinner.logic.commands.Command;
import seedu.tinner.logic.commands.CommandResult;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.logic.parser.CompanyListParser;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.Model;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.company.Company;
import seedu.tinner.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final CompanyListParser companyListParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        companyListParser = new CompanyListParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = companyListParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveCompanyList(model.getCompanyList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlyCompanyList getCompanyList() {
        return model.getCompanyList();
    }

    @Override
    public ObservableList<Company> getFilteredCompanyList() {
        return model.getFilteredCompanyList();
    }

    @Override
    public Path getCompanyListFilePath() {
        return model.getCompanyListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
