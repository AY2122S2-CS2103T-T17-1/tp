package seedu.tinner.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.tinner.commons.core.GuiSettings;
import seedu.tinner.logic.commands.CommandResult;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.company.Company;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the CompanyList.
     *
     * @see seedu.tinner.model.Model#getCompanyList()
     */
    ReadOnlyCompanyList getCompanyList();

    /** Returns an unmodifiable view of the filtered list of companies */
    ObservableList<Company> getFilteredCompanyList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getCompanyListFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
