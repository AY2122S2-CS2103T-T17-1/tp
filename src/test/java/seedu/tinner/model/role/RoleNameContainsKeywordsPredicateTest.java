package seedu.tinner.model.role;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tinner.testutil.RoleBuilder;

public class RoleNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> predicateKeywordList = Collections.singletonList("first");

        RoleNameContainsKeywordsPredicate predicate =
                new RoleNameContainsKeywordsPredicate(predicateKeywordList);

        // same object -> returns true
        assertTrue(predicate.equals(predicate));

        // same values -> returns true
        RoleNameContainsKeywordsPredicate predicateCopy =
                new RoleNameContainsKeywordsPredicate(predicateKeywordList);
        assertTrue(predicate.equals(predicateCopy));

        // different types -> returns false
        assertFalse(predicate.equals(1));

        // null -> returns false
        assertFalse(predicate.equals(null));

        // different role keywords -> returns false
        List<String> altPredicateKeywordList = Arrays.asList("first", "second");
        assertFalse(predicate.equals(altPredicateKeywordList));
    }

    @Test
    public void test_roleContainsKeywords_returnsTrue() {
        // Zero keywords
        RoleNameContainsKeywordsPredicate predicate =
                new RoleNameContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new RoleBuilder().withName("Software developer").build()));

        // One role keyword
        predicate = new RoleNameContainsKeywordsPredicate(Collections.singletonList("Software"));
        assertTrue(predicate.test(new RoleBuilder().withName("Software developer").build()));

        // Multiple keywords
        predicate = new RoleNameContainsKeywordsPredicate(Arrays.asList("Software", "developer"));
        assertTrue(predicate.test(new RoleBuilder().withName("Software developer").build()));

        // Only one matching keyword
        predicate = new RoleNameContainsKeywordsPredicate(Arrays.asList("Software", "Engineer"));
        assertTrue(predicate.test(new RoleBuilder().withName("Software developer").build()));

        // Mixed-case keywords
        predicate = new RoleNameContainsKeywordsPredicate(Arrays.asList("sOftWAre", "dEVelOpeR"));
        assertTrue(predicate.test(new RoleBuilder().withName("Software developer").build()));
    }

    @Test
    public void test_roleDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        RoleNameContainsKeywordsPredicate predicate = new RoleNameContainsKeywordsPredicate(Arrays.asList("Artifical"));
        assertFalse(predicate.test(new RoleBuilder().withName("Software developer").build()));

        // Keyword not matching exactly
        predicate = new RoleNameContainsKeywordsPredicate(Arrays.asList("Soffware"));
        assertFalse(predicate.test(new RoleBuilder().withName("Software developer").build()));
    }

    @Test
    public void test_rolePredicateIsEmpty_throwsAssertionError() {
        //empty string in keywords
        assertThrows(AssertionError.class, () -> new RoleNameContainsKeywordsPredicate(Arrays.asList("")));
    }
}
