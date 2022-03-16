package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tinner.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;

import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.commons.util.CollectionUtil;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.FavouriteStatus;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.company.ReadOnlyRoleList;

/**
 * Edits the details of an existing company in the address book.
 */
public class EditCompanyCommand extends Command {

    public static final String COMMAND_WORD = "editCompany";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the company"
            + " identified "
            + "by the index number used in the displayed company list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_COMPANY_SUCCESS = "Edited Company: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_COMPANY = "This company already exists in the "
            + "company list.";

    private final Index index;
    private final EditCompanyDescriptor editCompanyDescriptor;

    /**
     * @param index                 of the company in the filtered company list to edit
     * @param editCompanyDescriptor details to edit the company with
     */
    public EditCompanyCommand(Index index, EditCompanyDescriptor editCompanyDescriptor) {
        requireNonNull(index);
        requireNonNull(editCompanyDescriptor);

        this.index = index;
        this.editCompanyDescriptor = new EditCompanyDescriptor(editCompanyDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Company> lastShownList = model.getFilteredCompanyList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToEdit = lastShownList.get(index.getZeroBased());
        Company editedCompany = createEditedCompany(companyToEdit, editCompanyDescriptor);

        if (!companyToEdit.isSameCompany(editedCompany) && model.hasCompany(editedCompany)) {
            throw new CommandException(MESSAGE_DUPLICATE_COMPANY);
        }

        model.setCompany(companyToEdit, editedCompany);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES, PREDICATE_SHOW_ALL_ROLES);
        return new CommandResult(String.format(MESSAGE_EDIT_COMPANY_SUCCESS, editedCompany));
    }

    /**
     * Creates and returns a {@code Company} with the details of {@code companyToEdit}
     * edited with {@code editCompanyDescriptor}.
     */
    private static Company createEditedCompany(Company companyToEdit,
                                               EditCompanyDescriptor editCompanyDescriptor) {
        assert companyToEdit != null;

        CompanyName updatedName = editCompanyDescriptor.getName().orElse(companyToEdit.getName());
        Phone updatedPhone = editCompanyDescriptor.getPhone().orElse(companyToEdit.getPhone());
        Email updatedEmail = editCompanyDescriptor.getEmail().orElse(companyToEdit.getEmail());
        Address updatedAddress =
                editCompanyDescriptor.getAddress().orElse(companyToEdit.getAddress());
        ReadOnlyRoleList roles = companyToEdit.getRoleManager().getRoleList();
        FavouriteStatus favouriteStatus = companyToEdit.getFavouriteStatus();

        return new Company(updatedName, updatedPhone, updatedEmail, updatedAddress, roles, favouriteStatus);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCompanyCommand)) {
            return false;
        }

        // state check
        EditCompanyCommand e = (EditCompanyCommand) other;
        return index.equals(e.index)
                && editCompanyDescriptor.equals(e.editCompanyDescriptor);
    }

    /**
     * Stores the details to edit the company with. Each non-empty field value will replace the
     * corresponding field value of the company.
     */
    public static class EditCompanyDescriptor {
        private CompanyName name;
        private Phone phone;
        private Email email;
        private Address address;

        public EditCompanyDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditCompanyDescriptor(EditCompanyDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address);
        }

        public void setName(CompanyName name) {
            this.name = name;
        }

        public Optional<CompanyName> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCompanyDescriptor)) {
                return false;
            }

            // state check
            EditCompanyDescriptor e = (EditCompanyDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress());
        }
    }
}
