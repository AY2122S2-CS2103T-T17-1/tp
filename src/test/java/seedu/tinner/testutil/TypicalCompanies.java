package seedu.tinner.testutil;

import static seedu.tinner.logic.commands.CommandTestUtil.VALID_ADDRESS_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_ADDRESS_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_EMAIL_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_EMAIL_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_NAME_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_NAME_WHATSAPP;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_PHONE_INSTAGRAM;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_PHONE_WHATSAPP;
import static seedu.tinner.testutil.TypicalRoles.ML_ENGINEER;
import static seedu.tinner.testutil.TypicalRoles.MOBILE_ENGINEER;
import static seedu.tinner.testutil.TypicalRoles.NETWORK_ENGINEER;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.tinner.model.CompanyList;
import seedu.tinner.model.company.Company;

/**
 * A utility class containing a list of {@code Company} objects to be used in tests.
 */
public class TypicalCompanies {

    public static final Company META = new CompanyBuilder().withName("Meta")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("meta@example.com")
            .withPhone("94351253")
            .withRoles(ML_ENGINEER, MOBILE_ENGINEER)
            .build();
    public static final Company AMAZON = new CompanyBuilder().withName("Amazon")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("amazon@example.com").withPhone("98765432")
            .withRoles(MOBILE_ENGINEER)
            .build();
    public static final Company ZOOM = new CompanyBuilder().withName("Zoom").withPhone("95352563")
            .withEmail("zooom@example.com").withAddress("wall street")
            .withRoles(NETWORK_ENGINEER)
            .build();
    public static final Company TIKTOK = new CompanyBuilder().withName("Tiktok").withPhone("87652533")
            .withEmail("ticky@example.com").withAddress("10th street")
            .withRoles(MOBILE_ENGINEER, NETWORK_ENGINEER)
            .build();
    public static final Company APPLE = new CompanyBuilder().withName("Apple").withPhone("9482224")
            .withEmail("pear@example.com").withAddress("micheegan ave")
            .withRoles(ML_ENGINEER)
            .build();
    public static final Company GOVTECH = new CompanyBuilder().withName("Gov tech").withPhone("9482427")
            .withEmail("techygov@example.com").withAddress("little tokyo")
            .withRoles(NETWORK_ENGINEER)
            .build();
    public static final Company SLACK = new CompanyBuilder().withName("Slack").withPhone("9482442")
            .withEmail("slacker@example.com").withAddress("4th street")
            .withRoles(NETWORK_ENGINEER)
            .build();

    // Manually added
    public static final Company ASANA = new CompanyBuilder().withName("Asana").withPhone("8482424")
            .withEmail("asana@example.com").withAddress("little india")
            .withRoles(MOBILE_ENGINEER)
            .build();
    public static final Company GOOGLE = new CompanyBuilder().withName("Google").withPhone("8482131")
            .withEmail("ggooggle@example.com").withAddress("chicago ave")
            .withRoles(ML_ENGINEER, MOBILE_ENGINEER, NETWORK_ENGINEER)
            .build();

    // Manually added - Company's details found in {@code CommandTestUtil}
    public static final Company INSTAGRAM = new CompanyBuilder().withName(VALID_NAME_INSTAGRAM)
            .withPhone(VALID_PHONE_INSTAGRAM)
            .withEmail(VALID_EMAIL_INSTAGRAM)
            .withAddress(VALID_ADDRESS_INSTAGRAM)
             .withRoles(NETWORK_ENGINEER)
            .build();
    public static final Company WHATSAPP = new CompanyBuilder()
            .withName(VALID_NAME_WHATSAPP)
            .withPhone(VALID_PHONE_WHATSAPP)
            .withEmail(VALID_EMAIL_WHATSAPP)
            .withAddress(VALID_ADDRESS_WHATSAPP)
             .withRoles(ML_ENGINEER)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalCompanies() {} // prevents instantiation

    /**
     * Returns an {@code CompanyList} with all the typical companies.
     */
    public static CompanyList getTypicalCompanyList() {
        CompanyList ab = new CompanyList();
        for (Company company : getTypicalCompanies()) {
            ab.addCompany(company);
        }
        return ab;
    }

    public static List<Company> getTypicalCompanies() {
        return new ArrayList<>(Arrays.asList(META, AMAZON, ZOOM, TIKTOK, APPLE, GOVTECH, SLACK));
    }
}
