package seedu.address.model.company;

import javafx.collections.ObservableList;
import seedu.address.model.role.Role;

/**
 * Unmodifiable view of a role list
 */
public interface ReadOnlyRoleList {

    /**
     * Returns an unmodifiable view of the roles list.
     * This list will not contain any duplicate roles.
     */
    ObservableList<Role> getRoles();

}
