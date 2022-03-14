package seedu.tinner.testutil;

import static seedu.tinner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.tinner.logic.commands.AddCompanyCommand;
import seedu.tinner.logic.commands.EditCompanyCommand;
import seedu.tinner.model.company.Company;

/**
 * A utility class for Company.
 */
public class CompanyUtil {

    /**
     * Returns an add command string for adding the {@code company}.
     */
    public static String getAddCompanyCommand(Company company) {
        return AddCompanyCommand.COMMAND_WORD + " " + getCompanyDetails(company);
    }

    /**
     * Returns the part of command string for the given {@code company}'s details.
     */
    public static String getCompanyDetails(Company company) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + company.getName().fullName + " ");
        sb.append(PREFIX_PHONE + company.getPhone().value + " ");
        sb.append(PREFIX_EMAIL + company.getEmail().value + " ");
        sb.append(PREFIX_ADDRESS + company.getAddress().value + " ");
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditCompanyDescriptor}'s details.
     */
    public static String getEditCompanyDescriptorDetails(EditCompanyCommand.EditCompanyDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getAddress().ifPresent(address -> sb.append(PREFIX_ADDRESS).append(address.value).append(" "));
        return sb.toString();
    }
}
