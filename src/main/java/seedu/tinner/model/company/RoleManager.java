package seedu.tinner.model.company;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.tinner.model.role.Role;

/**
 * Represents the list of all roles tagged to a Company found in the address book.
 */
public class RoleManager {

    /**
     * {@code Predicate} that always evaluate to true
     */
    public static final Predicate<Role> PREDICATE_SHOW_ALL_ROLES = unused -> true;

    /**
     * {@code Predicate} that always evaluate to false
     */
    public static final Predicate<Role> PREDICATE_SHOW_NO_ROLES = unused -> false;

    private final RoleList roleList;
    private final FilteredList<Role> filteredRoles;

    /**
     * Initializes a RoleList with the given roleList.
     */
    public RoleManager(ReadOnlyRoleList roleList) {
        requireAllNonNull(roleList);

        this.roleList = new RoleList(roleList);
        this.filteredRoles = new FilteredList<>(this.roleList.getRoles());
    }

    public RoleManager() {
        this(new RoleList());
    }

    //=========== RoleList ================================================================================

    public void setRoleList(ReadOnlyRoleList roleList) {
        this.roleList.resetData(roleList);
    }

    public ReadOnlyRoleList getRoleList() {
        return roleList;
    }

    /**
     * Returns true if a role with the same identity as {@code role} exists in the role list.
     */
    public boolean hasRole(Role role) {
        requireNonNull(role);
        return roleList.hasRole(role);
    }

    /**
     * Deletes the given role.
     * The role must exist in the address book.
     */
    public void deleteRole(Role target) {
        roleList.removeRole(target);
    }

    /**
     * Adds the given role.
     * {@code role} must not already exist in the address book.
     */
    public void addRole(Role role) {
        roleList.addRole(role);
        updateFilteredRoleList(PREDICATE_SHOW_ALL_ROLES);
    }

    /**
     * Replaces the given role {@code role} with {@code editedROle}.
     * {@code target} must exist in the role list.
     * The role identity of {@code editedRole} must not be the same as another existing role in the
     * role list.
     */
    public void setRole(Role target, Role editedRole) {
        requireAllNonNull(target, editedRole);

        roleList.setRole(target, editedRole);
    }

    //=========== Filtered Role List Accessors ============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Role}.
     */
    public ObservableList<Role> getFilteredRoleList() {
        return filteredRoles;
    }

    /**
     * Updates the filter of the filtered role list to filter by the given {@code predicate}.
     *
     * @param predicate Predicate to be tested against.
     * @throws NullPointerException if {@code predicate} is null.
     */
    public void updateFilteredRoleList(Predicate<Role> predicate) {
        requireNonNull(predicate);
        filteredRoles.setPredicate(PREDICATE_SHOW_ALL_ROLES);
        filteredRoles.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof RoleManager)) {
            return false;
        }

        // state check
        RoleManager other = (RoleManager) obj;
        return roleList.equals(other.roleList)
                && filteredRoles.equals(other.filteredRoles);
    }
}
