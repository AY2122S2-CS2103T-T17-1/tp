package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javafx.util.Pair;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.company.Address;
import seedu.address.model.company.CompanyName;
import seedu.address.model.company.Email;
import seedu.address.model.company.Phone;
import seedu.address.model.role.Deadline;
import seedu.address.model.role.Description;
import seedu.address.model.role.RoleName;
import seedu.address.model.role.Status;
import seedu.address.model.role.Stipend;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_DOUBLE_INDEX = "Double indexes are missing.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing
     * whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses two {@code oneBasedIndex} into an array of {@code Index} of length two and returns it.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if one of the specified indexes is invalid (not non-zero unsigned
     *                        integer)
     *                        or one or both indexes are missing.
     */
    public static Index[] parseDoubleIndex(String oneBasedIndexes) throws ParseException {
        String[] splitTrimmedIndexes = oneBasedIndexes.trim().split("\\s+");
        if (!(splitTrimmedIndexes.length == 2)) {
            throw new ParseException(MESSAGE_INVALID_DOUBLE_INDEX);
        }
        Index firstIndex = parseIndex(splitTrimmedIndexes[0]);
        Index secondIndex = parseIndex(splitTrimmedIndexes[1]);

        return new Index[]{firstIndex, secondIndex};
    }

    /**
     * Parses two {@code oneBasedIndex} into an array of {@code Index} of length two and returns it.
     *
     * @param content String representation of user input.
     * @return Pair containing company index and role information.
     * @throws ParseException if either index or role information is absent or index is invalide.
     */
    public static Pair<Index, String> parseIndexWithContent(String content) throws ParseException {
        String[] splitTrimmedContent = content.trim().split("\\s+", 2);
        if (!(splitTrimmedContent.length == 2)) {
            throw new ParseException(MESSAGE_INVALID_DOUBLE_INDEX);
        }
        Index index = parseIndex(splitTrimmedContent[0]);
        String info = " " + splitTrimmedContent[1];
        return new Pair<>(index, info);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static CompanyName parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!CompanyName.isValidName(trimmedName)) {
            throw new ParseException(CompanyName.MESSAGE_CONSTRAINTS);
        }
        return new CompanyName(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid and {@code phone} is not empty
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String roleName} into a {@code RoleName}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code roleName} is invalid.
     */
    public static RoleName parseRoleName(String roleName) throws ParseException {
        requireNonNull(roleName);
        String trimmedRoleName = roleName.trim();
        if (!RoleName.isValidName(trimmedRoleName)) {
            throw new ParseException(RoleName.MESSAGE_CONSTRAINTS);
        }
        return new RoleName(trimmedRoleName);
    }

    /**
     * Parses a {@code String status} into a {@code Status}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        requireNonNull(status);
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses a {@code String deadline} into a {@code Deadline}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code deadline} is invalid.
     */
    public static Deadline parseDeadline(String deadline) throws ParseException {
        requireNonNull(deadline);
        String trimmedDeadline = deadline.trim();
        if (!Deadline.isValidDeadline(trimmedDeadline) || !Deadline.isDeadlineAfter(trimmedDeadline)) {
            throw new ParseException(Deadline.MESSAGE_CONSTRAINTS);
        }
        return new Deadline(trimmedDeadline);
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses a {@code String stipend} into a {@code Stipend}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code stipend} is invalid.
     */
    public static Stipend parseStipend(String stipend) throws ParseException {
        requireNonNull(stipend);
        String trimmedStipend = stipend.trim();
        if (!Stipend.isValidStipend(trimmedStipend)) {
            throw new ParseException(Stipend.MESSAGE_CONSTRAINTS);
        }
        return new Stipend(trimmedStipend);
    }
}
