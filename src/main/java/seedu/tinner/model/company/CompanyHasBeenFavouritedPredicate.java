package seedu.tinner.model.company;

import java.util.function.Predicate;

/**
 * Tests that a {@code Company} has been favourited by the user.
 */
public class CompanyHasBeenFavouritedPredicate implements Predicate<Company> {

    public CompanyHasBeenFavouritedPredicate() {

    }

    @Override
    public boolean test(Company company) {
        return company.getFavouriteStatus().value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                // instanceof handles nulls
                || other instanceof CompanyHasBeenFavouritedPredicate;
    }

}
