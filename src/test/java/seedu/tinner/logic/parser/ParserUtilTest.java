package seedu.tinner.logic.parser;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tinner.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_ROLE;

import org.junit.jupiter.api.Test;

import javafx.util.Pair;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.Phone;

public class ParserUtilTest {

    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_INDEX, () -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_COMPANY, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_COMPANY, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseDoubleIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDoubleIndex("10"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDoubleIndex("10 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDoubleIndex("1 1 1"));
    }

    @Test
    public void parseDoubleIndex_validInput_success() throws Exception {
        // No whitespaces
        Index[] indexesInput = ParserUtil.parseDoubleIndex("1 1");
        assertEquals(INDEX_FIRST_COMPANY, indexesInput[0]);
        assertEquals(INDEX_FIRST_ROLE, indexesInput[1]);

        // Leading and trailing whitespaces
        Index[] indexesWhiteSpaceInput = ParserUtil.parseDoubleIndex("  1     1  ");
        assertEquals(INDEX_FIRST_COMPANY, indexesWhiteSpaceInput[0]);
        assertEquals(INDEX_FIRST_ROLE, indexesWhiteSpaceInput[1]);
    }

    @Test
    public void parseIndexWithContent_invalidInput_throwsParseException() {
        // No content
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexWithContent("1"));

        // No separation
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexWithContent("2test"));

        // Negative integer for index
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexWithContent("-5 test"));

        // Index not integer
        assertThrows(ParseException.class, () -> ParserUtil.parseIndexWithContent("a test"));
    }

    @Test
    public void parseIndexWithContent_validInput_success() throws Exception {
        // No whitespaces
        Pair<Index, String> parsedInput = ParserUtil.parseIndexWithContent("1 test");
        Index index = parsedInput.getKey();
        String content = parsedInput.getValue();
        assertEquals(INDEX_FIRST_COMPANY, index);
        assertEquals(content, " test");

        // Leading and trailing whitespaces
        Pair<Index, String> whiteSpaceInput = ParserUtil.parseIndexWithContent("  1      test");
        index = parsedInput.getKey();
        content = parsedInput.getValue();
        assertEquals(INDEX_FIRST_COMPANY, index);
        assertEquals(content, " test");
    }

    @Test
    public void parseDoubleIndexWithContent_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDoubleIndexWithContent("1 0"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDoubleIndex("10 a"));
        assertThrows(ParseException.class, () -> ParserUtil.parseDoubleIndex("1 1 1"));
    }

    @Test
    public void parseDoubleIndexWithContent_validInput_success() throws Exception {
        // No whitespaces
        Pair<Index[], String> parsedInput = ParserUtil.parseDoubleIndexWithContent("1 1 test");
        Index[] indexes = parsedInput.getKey();
        String info = parsedInput.getValue();
        assertEquals(INDEX_FIRST_COMPANY, indexes[0]);
        assertEquals(INDEX_FIRST_ROLE, indexes[1]);
        assertEquals(info, " test");

        // Leading and trailing whitespaces
        Pair<Index[], String> whiteSpaceInput = ParserUtil.parseDoubleIndexWithContent("  1     1   test");
        indexes = parsedInput.getKey();
        info = parsedInput.getValue();
        assertEquals(INDEX_FIRST_COMPANY, indexes[0]);
        assertEquals(INDEX_FIRST_ROLE, indexes[1]);
        assertEquals(info, " test");
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        CompanyName expectedName = new CompanyName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        CompanyName expectedName = new CompanyName(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    /**
     * @Test public void parseAddress_invalidValue_throwsParseException() {
     * assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
     * }
     */

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }
}
