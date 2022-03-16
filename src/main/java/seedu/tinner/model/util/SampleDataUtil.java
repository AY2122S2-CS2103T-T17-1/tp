package seedu.tinner.model.util;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.tinner.model.CompanyList;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.FavouriteStatus;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.company.RoleList;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.Description;
import seedu.tinner.model.role.Role;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;

/**
 * Contains utility methods for populating {@code CompanyList} with sample data.
 */
public class SampleDataUtil {

    private static final Role SAMPLE_ROLE_1 = new Role(
            new seedu.tinner.model.role.RoleName("Backend engineer"),
            new Status("pending"), new Deadline("27-02-2022 18:00"),
            new Description("Backend engineer"), new Stipend("1000"));
    private static final Role SAMPLE_ROLE_2 = new Role(
            new seedu.tinner.model.role.RoleName("Frontend engineer"),
            new Status("pending"), new Deadline("28-02-2022 22:00"),
            new Description("Frontend engineer"), new Stipend("1000"));

    private static final RoleList SAMPLE_ROLES_1 = new RoleList();
    private static final RoleList SAMPLE_ROLES_2 = new RoleList();
    private static final RoleList SAMPLE_ROLES_3 = new RoleList();

    public static Company[] getSampleCompanies() {
        SAMPLE_ROLES_1.setRoles(Arrays.asList(SAMPLE_ROLE_1));
        SAMPLE_ROLES_2.setRoles(Arrays.asList(SAMPLE_ROLE_2));
        SAMPLE_ROLES_3.setRoles(Arrays.asList(SAMPLE_ROLE_1, SAMPLE_ROLE_2));

        return new Company[]{
                new Company(new CompanyName("Meta"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"), SAMPLE_ROLES_1,
                        new FavouriteStatus(false)),
                new Company(new CompanyName("Amazon"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), SAMPLE_ROLES_1,
                        new FavouriteStatus(false)),
                new Company(new CompanyName("Netflix"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), SAMPLE_ROLES_2,
                        new FavouriteStatus(false)),
                new Company(new CompanyName("Google"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), SAMPLE_ROLES_2,
                        new FavouriteStatus(false)),
                new Company(new CompanyName("Apple"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"), SAMPLE_ROLES_3,
                        new FavouriteStatus(false)),
                new Company(new CompanyName("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"), SAMPLE_ROLES_3,
                        new FavouriteStatus(false))
        };
    }

    public static ReadOnlyCompanyList getSampleCompanyList() {
        CompanyList sampleAb = new CompanyList();
        for (Company sampleCompany : getSampleCompanies()) {
            sampleAb.addCompany(sampleCompany);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static List<Role> getRoleList(Role... roles) {
        return Arrays.stream(roles)
                .collect(Collectors.toList());
    }

}
