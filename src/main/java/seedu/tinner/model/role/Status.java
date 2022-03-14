package seedu.tinner.model.role;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.AppUtil.checkArgument;

import java.util.Arrays;

/**
 * Represents a Role's application status in the role list.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS =
            "Status should only be one from the following: \"applying\", \"pending\", "
                    + "\"interview and assessments\", \"offered\", \"rejected\", \"complete\"";
    public static final String[] VALIDATION_ARRAY =
            new String[]{"applying", "pending", "offered", "interview and assessments", "rejected", "complete"};
    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = status;
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return Arrays.asList(VALIDATION_ARRAY).contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Status // instanceof handles nulls
                && value.equals(((Status) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
