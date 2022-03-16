package seedu.tinner.model.company;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Company's favourite status in the address book.
 * Guarantees: immutable
 */
public class FavouriteStatus {

    public static final String MESSAGE_CONSTRAINTS =
            "Favourite should only contain numbers, and it should be at least 3 digits long";
    public final Boolean isFavourited;

    /**
     * Constructs a {@code FavouriteStatus}.
     *
     * @param isFavourited A Boolean value
     */
    public FavouriteStatus(Boolean isFavourited) {
        requireNonNull(isFavourited);
        this.isFavourited = isFavourited;
    }

    @Override
    public String toString() {
        return isFavourited.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteStatus // instanceof handles nulls
                && (isFavourited == ((FavouriteStatus) other).isFavourited)); // state check
    }

    @Override
    public int hashCode() {
        return isFavourited.hashCode();
    }

}
