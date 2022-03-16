package seedu.tinner.logic.commands;

import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tinner.logic.commands.CommandTestUtil.favouriteCompanyAtIndex;
import static seedu.tinner.logic.commands.CommandTestUtil.showCompanyAtIndex;
import static seedu.tinner.model.Model.PREDICATE_SHOW_NO_COMPANIES;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_NO_ROLES;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListFavouriteCommand.
 */
public class ListFavouriteCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCompanyList(), new UserPrefs());
        expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
    }

    @Test
    public void execute_noCompanyFavourited_showsEmptyList() {
        expectedModel.updateFilteredCompanyList(PREDICATE_SHOW_NO_COMPANIES, PREDICATE_SHOW_NO_ROLES);
        assertCommandSuccess(new ListFavouriteCommand(), model, ListFavouriteCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_oneRoleFavourited_showsFavouritedRole() {
        favouriteCompanyAtIndex(expectedModel, INDEX_SECOND_COMPANY);
        showCompanyAtIndex(expectedModel, INDEX_SECOND_COMPANY);
        favouriteCompanyAtIndex(model, INDEX_SECOND_COMPANY);
        assertCommandSuccess(new ListFavouriteCommand(), model, ListFavouriteCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
