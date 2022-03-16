package seedu.tinner.model.role;

import static seedu.tinner.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.tinner.model.company.FavouriteStatus;

public class FavouriteStatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FavouriteStatus(null));
    }
}
