package seedu.tinner.model.role;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Role's deadline in the role list.
 * Guarantees: immutable; is valid as declared in {@link #isValidDeadline(String)}
 */
public class Deadline {

    public static final String MESSAGE_CONSTRAINTS =
            "Deadlines should not have passed and must be in the following format: dd-MM-yyyy HH:mm";

    public static final DateTimeFormatter VALIDATION_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public static final DateTimeFormatter STRING_REPRESENTATION_FORMATTER =
            DateTimeFormatter.ofPattern("E, MMM dd yyyy, HH:mm a");

    public final LocalDateTime value;

    /**
     * Constructs a {@code Deadline}.
     *
     * @param deadline A valid deadline.
     */
    public Deadline(String deadline) {
        requireNonNull(deadline);
        checkArgument(isValidDeadline(deadline), MESSAGE_CONSTRAINTS);
        value = LocalDateTime.parse(deadline, VALIDATION_FORMATTER);
    }

    /**
     * Returns true if a given string is a valid deadline.
     */
    public static boolean isValidDeadline(String test) {
        try {
            VALIDATION_FORMATTER.parse(test);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns true if a given string, when parsed into {@code LocalDateTime} is after the current time.
     *
     * @param test User input to be tested.
     * @return true if user input is after current time.
     */
    public static boolean isDeadlineAfter(String test) {
        return LocalDateTime.parse(test, VALIDATION_FORMATTER).isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return value.format(STRING_REPRESENTATION_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Deadline // instanceof handles nulls
                && value.equals(((Deadline) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
