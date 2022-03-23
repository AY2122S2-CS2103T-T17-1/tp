package seedu.tinner.model.company;

import static seedu.tinner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Company in the list of companies
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Company {

    // Identity fields
    private final CompanyName companyName;
    private final Phone phone;
    private final Email email;
    private final FavouriteStatus favouriteStatus;

    // Data fields
    private final Address address;
    private RoleManager roleManager = new RoleManager();

    /**
     * Name is compulsory but Phone, Email and Address are optional
     */
    public Company(CompanyName companyName, Phone phone, Email email, Address address, ReadOnlyRoleList roles,
                   FavouriteStatus favouriteStatus) {
        requireAllNonNull(companyName, phone, email, address, roles, favouriteStatus);
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.roleManager.setRoleList(roles);
        this.favouriteStatus = favouriteStatus;
    }

    public CompanyName getName() {
        return companyName;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public FavouriteStatus getFavouriteStatus() {
        return favouriteStatus;
    }

    public RoleManager getRoleManager() {
        return roleManager;
    }

    /**
     * Returns true if both companies have the same name.
     * This defines a weaker notion of equality between two companies.
     */
    public boolean isSameCompany(Company otherCompany) {
        if (otherCompany == this) {
            return true;
        }

        return otherCompany != null
                && otherCompany.getName().equals(getName());
    }

    /**
     * Returns true if both companies have the same identity and data fields.
     * This defines a stronger notion of equality between two companies.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Company)) {
            return false;
        }

        Company otherCompany = (Company) other;
        return otherCompany.getName().equals(getName())
                && otherCompany.getPhone().equals(getPhone())
                && otherCompany.getEmail().equals(getEmail())
                && otherCompany.getAddress().equals(getAddress())
                && otherCompany.getFavouriteStatus().equals(getFavouriteStatus());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(companyName, phone, email, address, favouriteStatus);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress())
                .append("; Favourite: ")
                .append(getFavouriteStatus());

        return builder.toString();
    }

}
