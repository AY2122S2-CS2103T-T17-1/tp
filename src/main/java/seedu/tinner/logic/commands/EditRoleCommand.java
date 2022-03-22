package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STIPEND;
import static seedu.tinner.model.company.RoleManager.PREDICATE_SHOW_ALL_ROLES;

import java.util.List;
import java.util.Optional;

import javafx.collections.ObservableList;
import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.commons.util.CollectionUtil;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.Description;
import seedu.tinner.model.role.Role;
import seedu.tinner.model.role.RoleName;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;


/**
 * Edits the details of an existing role in an existing company.
 */
public class EditRoleCommand extends Command {
    public static final String COMMAND_WORD = "editRole";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the Role"
            + " identified "
            + "by the index number of the company used in the displayed company list.\n"
            + "Followed by the index number used by the role in the specified company.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: COMPANY_INDEX ROLE_INDEX (both must be positive integers) "
            + "[" + PREFIX_NAME + "ROLENAME] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_STIPEND + "STIPEND]...\n"
            + "Example: " + COMMAND_WORD + " 1 1 "
            + PREFIX_STATUS + "PENDING "
            + PREFIX_STIPEND + "3000";

    public static final String MESSAGE_EDIT_ROLE_SUCCESS = "Edited Role: %1$s %1$s";
    public static final String MESSAGE_DUPLICATE_ROLE = "This role already exists in the "
            + "company.";

    private final Index companyIndex;
    private final Index roleIndex;

    private final EditRoleDescriptor editRoleDescriptor;

    /**
     * @param companyIndex       of the company in the filtered company list to edit
     * @param roleIndex          of the role in the company in the filtered company list to edit
     * @param editRoleDescriptor details to edit the company with
     */
    public EditRoleCommand(Index companyIndex, Index roleIndex, EditRoleDescriptor editRoleDescriptor) {
        requireAllNonNull(companyIndex, roleIndex, editRoleDescriptor);

        this.companyIndex = companyIndex;
        this.roleIndex = roleIndex;
        this.editRoleDescriptor = editRoleDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        ObservableList<Company> lastShownCompanyList = model.getFilteredCompanyList();

        if (companyIndex.getZeroBased() >= lastShownCompanyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        List<Role> lastShownRoleList = model.getFilteredRoleList(companyIndex);

        if (roleIndex.getZeroBased() >= lastShownRoleList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROLE_DISPLAYED_INDEX);
        }

        Role roleToEdit = lastShownRoleList.get(roleIndex.getZeroBased());
        Role editedRole = createEditedRole(roleToEdit, editRoleDescriptor);

        if (!roleToEdit.isSameRole(editedRole) && model.hasRole(companyIndex, editedRole)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROLE);
        }

        model.setRole(companyIndex, roleToEdit, editedRole);
        model.updateFilteredRoleList(companyIndex, PREDICATE_SHOW_ALL_ROLES);

        return new CommandResult(String.format(MESSAGE_EDIT_ROLE_SUCCESS, editedRole));
    }

    /**
     * Creates and returns a {@code Role} with the details of {@code roleToEdit}
     * edited with {@code editRoleDescriptor}.
     */
    private static Role createEditedRole(Role roleToEdit,
                                               EditRoleCommand.EditRoleDescriptor editRoleDescriptor) {
        assert roleToEdit != null;

        RoleName updatedName = editRoleDescriptor.getName().orElse(roleToEdit.getName());
        Status updatedStatus = editRoleDescriptor.getStatus().orElse(roleToEdit.getStatus());
        Deadline updatedDeadline = editRoleDescriptor.getDeadline().orElse(roleToEdit.getDeadline());
        Description updatedDescription =
                editRoleDescriptor.getDescription().orElse(roleToEdit.getDescription());
        Stipend updatedStipend = editRoleDescriptor.getStipend().orElse(roleToEdit.getStipend());

        return new Role(updatedName, updatedStatus, updatedDeadline, updatedDescription, updatedStipend);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditRoleCommand // instanceof handles nulls
                && companyIndex.equals(((EditRoleCommand) other).companyIndex)) // state check
                && roleIndex.equals(((EditRoleCommand) other).roleIndex)
                && editRoleDescriptor.equals(((EditRoleCommand) other).editRoleDescriptor);
    }

    /**
     * Stores the details to edit the Role with. Each non-empty field value will replace the
     * corresponding field value of the Role.
     */
    public static class EditRoleDescriptor {
        private RoleName name;
        private Status status;
        private Deadline deadline;
        private Description description;
        private Stipend stipend;

        public EditRoleDescriptor() {
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, status, deadline, description, stipend);
        }

        public void setName(RoleName name) {
            this.name = name;
        }

        public Optional<RoleName> getName() {
            return Optional.ofNullable(name);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
        }

        public void setDescription(Description description) {
            this.description = description;
        }

        public Optional<Description> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setStipend(Stipend stipend) {
            this.stipend = stipend;
        }

        public Optional<Stipend> getStipend() {
            return Optional.ofNullable(stipend);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRoleCommand.EditRoleDescriptor)) {
                return false;
            }

            // state check
            EditRoleCommand.EditRoleDescriptor e = (EditRoleCommand.EditRoleDescriptor) other;

            return getName().equals(e.getName())
                    && getStatus().equals(e.getStatus())
                    && getDeadline().equals(e.getDeadline())
                    && getDescription().equals(e.getDescription())
                    && getStipend().equals(e.getStipend());
        }
    }
}
