package seedu.tinner.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.FavouriteCompanyCommand.createFavouritedCompany;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STIPEND;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;
import static seedu.tinner.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyNameContainsKeywordsPredicate;
import seedu.tinner.testutil.EditCompanyDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_DEADLINE_SOFTWARE_ENGINEER = "01-01-2023 00:00";
    public static final String VALID_NAME_SOFTWARE_ENGINEER = "Software engineer";
    public static final String VALID_STIPEND_SOFTWARE_ENGINEER = "8888";
    public static final String VALID_STATUS_SOFTWARE_ENGINEER = "offered";
    public static final String VALID_DESCRIPTION_SOFTWARE_ENGINEER = "Develop web applications.";

    public static final String VALID_NAME_INSTAGRAM = "Instagram";
    public static final String VALID_NAME_WHATSAPP = "Whatsapp";
    public static final String VALID_PHONE_INSTAGRAM = "1111111";
    public static final String VALID_PHONE_WHATSAPP = "22222222";
    public static final String VALID_EMAIL_INSTAGRAM = "insta@example.com";
    public static final String VALID_EMAIL_WHATSAPP = "WA@example.com";
    public static final String VALID_ADDRESS_INSTAGRAM = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_WHATSAPP = "Block 123, Bobby Street 3";

    public static final String NAME_DESC_INSTAGRAM = " " + PREFIX_NAME + VALID_NAME_INSTAGRAM;
    public static final String NAME_DESC_WHATSAPP = " " + PREFIX_NAME + VALID_NAME_WHATSAPP;
    public static final String PHONE_DESC_INSTAGRAM = " " + PREFIX_PHONE + VALID_PHONE_INSTAGRAM;
    public static final String PHONE_DESC_WHATSAPP = " " + PREFIX_PHONE + VALID_PHONE_WHATSAPP;
    public static final String EMAIL_DESC_INSTAGRAM = " " + PREFIX_EMAIL + VALID_EMAIL_INSTAGRAM;
    public static final String EMAIL_DESC_WHATSAPP = " " + PREFIX_EMAIL + VALID_EMAIL_WHATSAPP;
    public static final String ADDRESS_DESC_INSTAGRAM = " " + PREFIX_ADDRESS + VALID_ADDRESS_INSTAGRAM;
    public static final String ADDRESS_DESC_WHATSAPP = " " + PREFIX_ADDRESS + VALID_ADDRESS_WHATSAPP;

    public static final String INDEX_DESC_SOFTWARE_ENGINEER = " 1";
    public static final String NAME_DESC_SOFTWARE_ENGINEER = " " + PREFIX_NAME + VALID_NAME_SOFTWARE_ENGINEER;
    public static final String STATUS_DESC_SOFTWARE_ENGINEER = " " + PREFIX_STATUS + VALID_STATUS_SOFTWARE_ENGINEER;
    public static final String DEADLINE_DESC_SOFTWARE_ENGINEER =
            " " + PREFIX_DEADLINE + VALID_DEADLINE_SOFTWARE_ENGINEER;
    public static final String DESCRIPTION_DESC_SOFTWARE_ENGINEER =
            " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_SOFTWARE_ENGINEER;
    public static final String STIPEND_DESC_SOFTWARE_ENGINEER =
            " " + PREFIX_STIPEND + VALID_STIPEND_SOFTWARE_ENGINEER;

    public static final String INVALID_INDEX_DESC = " ";
    public static final String INVALID_ROLE_NAME_DESC = " " + PREFIX_NAME + "Softw@re Engineer";
    public static final String INVALID_STATUS_DESC = " " + PREFIX_STATUS + "dont know";
    public static final String INVALID_DEADLINE_DESC =
            " " + PREFIX_DEADLINE + "01-01-1999 00:00";
    public static final String INVALID_DESCRIPTION_DESC =
            " " + PREFIX_DESCRIPTION + "Fronte^d";
    public static final String INVALID_STIPEND_DESC = " " + PREFIX_STIPEND + "one thousand";

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&";
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a";
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo";
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS;


    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCompanyCommand.EditCompanyDescriptor DESC_INSTAGRAM;
    public static final EditCompanyCommand.EditCompanyDescriptor DESC_WHATSAPP;

    static {
        DESC_INSTAGRAM = new EditCompanyDescriptorBuilder().withName(VALID_NAME_INSTAGRAM)
                .withPhone(VALID_PHONE_INSTAGRAM).withEmail(VALID_EMAIL_INSTAGRAM).withAddress(VALID_ADDRESS_INSTAGRAM)
                .build();
        DESC_WHATSAPP = new EditCompanyDescriptorBuilder().withName(VALID_NAME_WHATSAPP)
                .withPhone(VALID_PHONE_WHATSAPP).withEmail(VALID_EMAIL_WHATSAPP).withAddress(VALID_ADDRESS_WHATSAPP)
                .build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)} that takes a string
     * {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered company list and selected company in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel,
                                            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can only do so by copying its
        // components.
        CompanyList expectedCompanyList = new CompanyList(actualModel.getCompanyList());
        List<Company> expectedFilteredList = new ArrayList<>(actualModel.getFilteredCompanyList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedCompanyList, actualModel.getCompanyList());
        assertEquals(expectedFilteredList, actualModel.getFilteredCompanyList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the company at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showCompanyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCompanyList().size());

        Company company = model.getFilteredCompanyList().get(targetIndex.getZeroBased());
        final String[] splitName = company.getName().fullName.split("\\s+");
        model.updateFilteredCompanyList(new CompanyNameContainsKeywordsPredicate(new ArrayList<>(),
                Arrays.asList(splitName)), PREDICATE_SHOW_ALL_ROLES);

        assertEquals(1, model.getFilteredCompanyList().size());
    }

    /**
     * Updates {@code model}'s list to favourite the company at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void favouriteCompanyAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCompanyList().size());
        Company companyToFavourite = model.getFilteredCompanyList().get(targetIndex.getZeroBased());
        Company favouritedCompany = createFavouritedCompany(companyToFavourite);
        assertTrue(favouritedCompany.getFavouriteStatus().value);
        model.setCompany(companyToFavourite, favouritedCompany);
    }

}
