package seedu.address.model.company;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.CompanyBuilder;

public class CompanyNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        CompanyNameContainsKeywordsPredicate firstPredicate =
                new CompanyNameContainsKeywordsPredicate(firstPredicateKeywordList);
        CompanyNameContainsKeywordsPredicate secondPredicate =
                new CompanyNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        CompanyNameContainsKeywordsPredicate firstPredicateCopy =
                new CompanyNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different company -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        CompanyNameContainsKeywordsPredicate predicate =
                new CompanyNameContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new CompanyBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new CompanyBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new CompanyBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new CompanyBuilder().withName("Alice Bob").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        CompanyNameContainsKeywordsPredicate predicate =
                new CompanyNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new CompanyBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new CompanyBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address, but does not match name
        predicate = new CompanyNameContainsKeywordsPredicate(Arrays.asList("12345", "alice@email"
                + ".com", "Main", "Street"));
        assertFalse(predicate.test(new CompanyBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));
    }
}