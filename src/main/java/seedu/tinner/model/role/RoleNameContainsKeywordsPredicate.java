package seedu.tinner.model.role;

import java.util.List;
import java.util.function.Predicate;

import seedu.tinner.commons.util.StringUtil;

/**
 * Holds the keywords {@code roleNameKeywords} in order to look for matches with
 * stored {@code Role}.
 */
public class RoleNameContainsKeywordsPredicate implements Predicate<Role> {

    private final List<String> roleNameKeywords;

    /**
     * Constructor for {@code CompanyNamecontainsKeywordsPredicate} that stores
     * keywords {@code roleNameKeywords}.
     *
     * @param roleNameKeywords List of strings representing role name keywords entered by user
     */
    public RoleNameContainsKeywordsPredicate(List<String> roleNameKeywords) {
        assert(!roleNameKeywords.contains(""));
        this.roleNameKeywords = roleNameKeywords;
    }

    public List<String> getRoleNameKeywords() {
        return roleNameKeywords;
    }

    @Override
    public boolean test(Role role) {
        return roleNameKeywords.isEmpty() || roleNameKeywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(role.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                // instanceof handles nulls
                || (other instanceof RoleNameContainsKeywordsPredicate
                // state check
                && getRoleNameKeywords().equals(((RoleNameContainsKeywordsPredicate) other).getRoleNameKeywords()));
    }
}
