package seedu.tinner.model.company;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.tinner.model.role.Role;
import seedu.tinner.model.role.UniqueRoleList;

/**
 * Wraps all data at the company level
 * Duplicates are not allowed (by .isSameRole comparison)
 */
public class RoleList implements ReadOnlyRoleList {

    private final UniqueRoleList roles;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        roles = new UniqueRoleList();
    }

    public RoleList() {
    }

    /**
     * Creates an RoleList using the Roles in the {@code toBeCopied}
     */
    public RoleList(ReadOnlyRoleList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the role list with {@code roles}.
     * {@code roles} must not contain duplicate roles.
     */
    public void setRoles(List<Role> roles) {
        this.roles.setRoles(roles);
    }

    /**
     * Resets the existing data of this {@code RoleList} with {@code newData}.
     */
    public void resetData(ReadOnlyRoleList newData) {
        requireNonNull(newData);

        setRoles(newData.getRoles());
    }

    //// company-level operations

    /**
     * Returns true if a role with the same identity as {@code role} exists in the role list.
     */
    public boolean hasRole(Role role) {
        requireNonNull(role);
        return roles.contains(role);
    }

    /**
     * Adds a role to the role list.
     * The role must not already exist in the role list.
     */
    public void addRole(Role r) {
        roles.add(r);
    }

    /**
     * Replaces the given role {@code target} in the list with {@code editedRole}.
     * {@code target} must exist in the role list.
     * The role identity of {@code editedRole} must not be the same as another existing role
     * in the address book.
     */
    public void setRole(Role target, Role editedRole) {
        requireNonNull(editedRole);

        roles.setRole(target, editedRole);
    }

    /**
     * Removes {@code role} from this {@code RoleList}.
     * {@code key} must exist in the role list.
     */
    public void removeRole(Role key) {
        roles.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return roles.asUnmodifiableObservableList().size() + " roles";
        // TODO: refine later
    }

    @Override
    public ObservableList<Role> getRoles() {
        return roles.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RoleList // instanceof handles nulls
                && roles.equals(((RoleList) other).roles));
    }

    @Override
    public int hashCode() {
        return roles.hashCode();
    }
}
