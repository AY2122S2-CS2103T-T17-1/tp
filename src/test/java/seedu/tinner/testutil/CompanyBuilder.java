package seedu.tinner.testutil;

import java.util.Arrays;

import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.FavouriteStatus;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.company.ReadOnlyRoleList;
import seedu.tinner.model.company.RoleList;
import seedu.tinner.model.role.Role;

/**
 * A utility class to help with building Company objects.
 */
public class CompanyBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private CompanyName name;
    private Phone phone;
    private Email email;
    private Address address;
    private FavouriteStatus favouriteStatus;
    private ReadOnlyRoleList roles;

    /**
     * Creates a {@code CompanyBuilder} with the default details.
     */
    public CompanyBuilder() {
        name = new CompanyName(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        favouriteStatus = new FavouriteStatus(false);
        roles = new RoleList();
    }

    /**
     * Initializes the CompanyBuilder with the data of {@code companyToCopy}.
     */
    public CompanyBuilder(Company companyToCopy) {
        name = companyToCopy.getName();
        phone = companyToCopy.getPhone();
        email = companyToCopy.getEmail();
        address = companyToCopy.getAddress();
        roles = companyToCopy.getRoleManager().getRoleList();
        favouriteStatus = companyToCopy.getFavouriteStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Company} that we are building.
     */
    public CompanyBuilder withName(String name) {
        this.name = new CompanyName(name);
        return this;
    }

    /**
     * Parses the {@code roles} into a {@code ArrayList<Role>} and set it to the {@code Company} that we are building.
     */
    public CompanyBuilder withRoles(Role... roles) {
        RoleList roleList = new RoleList();
        roleList.setRoles(Arrays.asList(roles));
        this.roles = roleList;
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Company} that we are building.
     */
    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Company} that we are building to an empty {@code Address}.
     */
    public CompanyBuilder withoutAddress() {
        this.address = new Address("");
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Company} that we are building.
     */
    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Company} that we are building to an empty {@code Phone}.
     */
    public CompanyBuilder withoutPhone() {
        this.phone = new Phone("");
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Company} that we are building.
     */
    public CompanyBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Company} that we are building to an empty {@code Email}.
     */
    public CompanyBuilder withoutEmail() {
        this.email = new Email("");
        return this;
    }

    /**
     * Sets the {@code isFavourited} field of {@code FavouriteStatus} in the {@code Company}
     * that we are building to true.
     */
    public CompanyBuilder setToFavourite() {
        this.favouriteStatus = new FavouriteStatus(true);
        return this;
    }

    /**
     * Sets the {@code isFavourited} field of {@code FavouriteStatus} in the {@code Company}
     * that we are building to false.
     */
    public CompanyBuilder setToUnfavourite() {
        this.favouriteStatus = new FavouriteStatus(false);
        return this;
    }

    public Company build() {
        return new Company(name, phone, email, address, roles, favouriteStatus);
    }

}
