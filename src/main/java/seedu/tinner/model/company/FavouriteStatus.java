package seedu.tinner.model.company;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Company's favourite status in the address book.
 * Guarantees: immutable
 */
public class FavouriteStatus {

    public static final String MESSAGE_CONSTRAINTS = "Favourite status should only contain boolean values.";

    public final Boolean value;

    /**
     * Constructs a {@code FavouriteStatus}.
     *
     * @param isFavourited A Boolean value
     */
    public FavouriteStatus(Boolean isFavourited) {
        requireNonNull(isFavourited);
        this.value = isFavourited;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    /**
     * Returns true if a given string is a valid favourite status.
     */
    public static boolean isValidFavouriteStatus(String test) {
        return test.equals("true") || test.equals("false");
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteStatus // instanceof handles nulls
                && (value == ((FavouriteStatus) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
