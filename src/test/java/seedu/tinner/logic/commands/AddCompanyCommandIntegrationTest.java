package seedu.tinner.logic.commands;

import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tinner.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tinner.testutil.TypicalCompanies.getTypicalCompanyList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.tinner.model.Model;
import seedu.tinner.model.ModelManager;
import seedu.tinner.model.UserPrefs;
import seedu.tinner.model.company.Company;
import seedu.tinner.testutil.CompanyBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCompanyCommand}.
 */
public class AddCompanyCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCompanyList(), new UserPrefs());
    }

    @Test
    public void execute_newCompany_success() {
        Company validCompany = new CompanyBuilder().build();

        Model expectedModel = new ModelManager(model.getCompanyList(), new UserPrefs());
        expectedModel.addCompany(validCompany);

        assertCommandSuccess(new AddCompanyCommand(validCompany), model,
                String.format(AddCompanyCommand.MESSAGE_SUCCESS, validCompany), expectedModel);
    }

    @Test
    public void execute_duplicateCompany_throwsCommandException() {
        Company companyInList = model.getCompanyList().getCompanyList().get(0);
        assertCommandFailure(new AddCompanyCommand(companyInList), model, AddCompanyCommand.MESSAGE_DUPLICATE_COMPANY);
    }

}
