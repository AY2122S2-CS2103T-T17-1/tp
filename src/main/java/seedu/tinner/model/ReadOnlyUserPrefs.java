package seedu.tinner.model;

import java.nio.file.Path;

import seedu.tinner.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getCompanyListFilePath();

}
