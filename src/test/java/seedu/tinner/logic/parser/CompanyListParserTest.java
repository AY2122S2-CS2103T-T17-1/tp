package seedu.tinner.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tinner.logic.commands.AddCompanyCommand;
import seedu.tinner.logic.commands.ClearCommand;
import seedu.tinner.logic.commands.DeleteCompanyCommand;
import seedu.tinner.logic.commands.EditCompanyCommand;
import seedu.tinner.logic.commands.EditCompanyCommand.EditCompanyDescriptor;
import seedu.tinner.logic.commands.ExitCommand;
import seedu.tinner.logic.commands.FindCommand;
import seedu.tinner.logic.commands.HelpCommand;
import seedu.tinner.logic.commands.ListCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyNameContainsKeywordsPredicate;
import seedu.tinner.model.role.RoleNameContainsKeywordsPredicate;
import seedu.tinner.testutil.CompanyBuilder;
import seedu.tinner.testutil.CompanyUtil;
import seedu.tinner.testutil.EditCompanyDescriptorBuilder;

public class CompanyListParserTest {

    private final CompanyListParser parser = new CompanyListParser();

    @Test
    public void parseCommand_add() throws Exception {
        Company company = new CompanyBuilder().build();
        AddCompanyCommand command = (AddCompanyCommand) parser.parseCommand(CompanyUtil.getAddCompanyCommand(company));
        assertEquals(new AddCompanyCommand(company), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCompanyCommand command = (DeleteCompanyCommand) parser.parseCommand(
                DeleteCompanyCommand.COMMAND_WORD + " " + INDEX_FIRST_COMPANY
                        .getOneBased());
        assertEquals(new DeleteCompanyCommand(INDEX_FIRST_COMPANY
        ), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Company company = new CompanyBuilder().build();
        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder(company).build();
        EditCompanyCommand command = (EditCompanyCommand) parser.parseCommand(
                EditCompanyCommand.COMMAND_WORD + " " + INDEX_FIRST_COMPANY
                .getOneBased() + " " + CompanyUtil.getEditCompanyDescriptorDetails(descriptor));
        assertEquals(new EditCompanyCommand(INDEX_FIRST_COMPANY, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(FindCommand.COMMAND_WORD + " c/"
                + String.join(" ", keywords) + " r/" + String.join(" ", keywords));
        assertEquals(new FindCommand(new CompanyNameContainsKeywordsPredicate(keywords, keywords),
                new RoleNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                HelpCommand.MESSAGE_USAGE), () -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand(
                "unknownCommand"));
    }
}
