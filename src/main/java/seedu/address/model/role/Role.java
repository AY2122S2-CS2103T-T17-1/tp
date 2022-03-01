package seedu.address.model.role;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Role in Tinner.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Role {

    private final RoleName roleName;
    private final Status status;
    private final Deadline deadline;

    // "optional" in the sense that they can be empty strings
    private final Description description;
    private final Stipend stipend;

    /**
     * Every field must be present and not null.
     */
    public Role(RoleName roleName, Status status, Deadline deadline, Description description, Stipend stipend) {
        requireAllNonNull(roleName, status, deadline, description, stipend);
        this.roleName = roleName;
        this.status = status;
        this.deadline = deadline;
        this.description = description;
        this.stipend = stipend;
    }

    public RoleName getName() {
        return roleName;
    }

    public Status getStatus() {
        return status;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public Description getDescription() {
        return description;
    }

    public Stipend getStipend() {
        return stipend;
    }

    /**
     * Returns true if both roles have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return otherRole.getName().equals(getName())
                && otherRole.getStatus().equals(getStatus())
                && otherRole.getDeadline().equals(getDeadline())
                && otherRole.getDescription().equals(getDescription())
                && otherRole.getStipend().equals(getStipend());
    }

}
