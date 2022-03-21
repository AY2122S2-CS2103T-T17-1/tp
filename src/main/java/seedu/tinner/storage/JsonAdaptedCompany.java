package seedu.tinner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.tinner.commons.exceptions.IllegalValueException;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.FavouriteStatus;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.company.RoleList;

/**
 * Jackson-friendly version of {@link Company}.
 */
class JsonAdaptedCompany {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Company's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String favouriteStatus;
    private final List<JsonAdaptedRole> roles = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedCompany} with the given company details.
     */
    @JsonCreator
    public JsonAdaptedCompany(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                              @JsonProperty("email") String email, @JsonProperty("address") String address,
                              @JsonProperty("favouriteStatus") String favouriteStatus,
                              @JsonProperty("roles") List<JsonAdaptedRole> roles) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.favouriteStatus = favouriteStatus;
        if (roles != null) {
            this.roles.addAll(roles);
        }
    }

    /**
     * Converts a given {@code Company} into this class for Jackson use.
     */
    public JsonAdaptedCompany(Company source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        favouriteStatus = source.getFavouriteStatus().value.toString();
        roles.addAll(source.getRoleManager().getRoleList()
                .getRoles().stream()
                .map(JsonAdaptedRole::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted company object into the model's {@code Company} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted company.
     */
    public Company toModelType() throws IllegalValueException {
        final RoleList companyRoles = new RoleList();
        for (JsonAdaptedRole role : roles) {
            companyRoles.addRole(role.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CompanyName.class.getSimpleName()));
        }
        if (!CompanyName.isValidName(name)) {
            throw new IllegalValueException(CompanyName.MESSAGE_CONSTRAINTS);
        }
        final CompanyName modelName = new CompanyName(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (favouriteStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    FavouriteStatus.class.getSimpleName()));
        }
        if (!FavouriteStatus.isValidFavouriteStatus(favouriteStatus)) {
            throw new IllegalValueException(FavouriteStatus.MESSAGE_CONSTRAINTS);
        }
        final FavouriteStatus modelFavouriteStatus = new FavouriteStatus(Boolean.parseBoolean(favouriteStatus));

        final RoleList modelRoles = new RoleList(companyRoles);

        return new Company(modelName, modelPhone, modelEmail, modelAddress, modelRoles,
                modelFavouriteStatus);
    }

}
