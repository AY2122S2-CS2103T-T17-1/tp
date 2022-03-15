package seedu.tinner.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.stream.Stream;

import javafx.util.Pair;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.commons.util.StringUtil;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.role.Deadline;
import seedu.tinner.model.role.Description;
import seedu.tinner.model.role.RoleName;
import seedu.tinner.model.role.Status;
import seedu.tinner.model.role.Stipend;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_DOUBLE_INDEX = "Double indexes are missing.";
    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_CONTENT = "Index[es] and/or content are missing.";

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
        if (splitTrimmedIndexes.length != 2) {
            throw new ParseException(MESSAGE_INVALID_DOUBLE_INDEX);
        }
        Index firstIndex = parseIndex(splitTrimmedIndexes[0]);
        Index secondIndex = parseIndex(splitTrimmedIndexes[1]);

        return new Index[]{firstIndex, secondIndex};
    }

    /**
     * Parses  {@code oneBasedIndex} and content into an {@code Index} and string.
     *
     * @param content String representation of user input.
     * @return Pair containing company index and role information.
     * @throws ParseException if either index or role information is absent or index is invalide.
     */
    public static Pair<Index, String> parseIndexWithContent(String content) throws ParseException {
        String[] splitTrimmedContent = content.trim().split("\\s+", 2);
        if (splitTrimmedContent.length != 2) {
            throw new ParseException(MESSAGE_INVALID_CONTENT);
        }
        Index index = parseIndex(splitTrimmedContent[0]);
        String info = " " + splitTrimmedContent[1];
        return new Pair<>(index, info);
    }

    /**
     * Parses two {@code oneBasedIndex} and content into an array of {@code Index} and string.
     *
     * @param content String representation of user input.
     * @return Pair containing company index, role index and role information.
     * @throws ParseException if either index or role information is absent or index is invalide.
     */
    public static Pair<Index[], String> parseDoubleIndexWithContent(String content) throws ParseException {
        String[] splitTrimmedContent = content.trim().split("\\s+", 3);
        if (splitTrimmedContent.length != 3) {
            throw new ParseException(MESSAGE_INVALID_CONTENT);
        }
        Index[] indexes = parseDoubleIndex(splitTrimmedContent[0] + " "
                + splitTrimmedContent[1]);
        String info = " " + splitTrimmedContent[2];
        return new Pair<>(indexes, info);
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

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if prefix is found and the corresponding prefix contains empty values in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean hasPrefixWithoutValue(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return (arePrefixesPresent(argumentMultimap, prefix)
                && argumentMultimap.getOptionalValue(prefix).get().isEmpty());
    }
}
