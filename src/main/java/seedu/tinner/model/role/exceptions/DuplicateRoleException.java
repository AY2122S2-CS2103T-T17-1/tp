package seedu.tinner.model.role.exceptions;

/**
 * Signals that the operation will result in duplicate Roles (Roles are considered duplicates if they have the
 * same identity).
 */
public class DuplicateRoleException extends RuntimeException {
    public DuplicateRoleException() {
        super("Operation would result in duplicate roles");
    }
}
