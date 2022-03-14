package seedu.tinner.model.role;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.AppUtil.checkArgument;

/**
 * Represents a Role's stipend in Tinner.
 * Guarantees: immutable; is valid as declared in {@link #isValidStipend(String)}
 */
public class Stipend {

    public static final String MESSAGE_CONSTRAINTS =
            "Stipends should only contain numbers, and it should be at most 10 digits long";
    public static final String VALIDATION_REGEX = "\\d{1,10}";
    public final String value;

    /**
     * Constructs a {@code Stipend}.
     *
     * @param stipend A valid stipend.
     */
    public Stipend(String stipend) {
        requireNonNull(stipend);
        checkArgument(isValidStipend(stipend), MESSAGE_CONSTRAINTS);
        value = stipend;
    }

    /**
     * Returns true if a given string is a valid stipend or is empty.
     */
    public static boolean isValidStipend(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Stipend // instanceof handles nulls
                && value.equals(((Stipend) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
