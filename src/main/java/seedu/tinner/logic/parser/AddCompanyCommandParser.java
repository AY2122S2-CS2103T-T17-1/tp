package seedu.tinner.logic.parser;

import static seedu.tinner.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tinner.commons.core.Messages.MESSAGE_NO_VALUE_AFTER_PREFIX;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_PHONE;

import seedu.tinner.logic.commands.AddCompanyCommand;
import seedu.tinner.logic.parser.exceptions.ParseException;
import seedu.tinner.model.company.Address;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.CompanyName;
import seedu.tinner.model.company.Email;
import seedu.tinner.model.company.FavouriteStatus;
import seedu.tinner.model.company.Phone;
import seedu.tinner.model.company.RoleList;


/**
 * Parses input arguments and creates a new AddCompanyCommand object
 */
public class AddCompanyCommandParser implements Parser<AddCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCompanyCommand
     * and returns an AddCompanyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCompanyCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                        PREFIX_ADDRESS);

        if (!ParserUtil.arePrefixesPresent(argMultimap, PREFIX_NAME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCompanyCommand.MESSAGE_USAGE));
        }

        if (ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_NAME)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_PHONE)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_EMAIL)
                || ParserUtil.hasPrefixWithoutValue(argMultimap, PREFIX_ADDRESS)) {
            throw new ParseException(String.format(MESSAGE_NO_VALUE_AFTER_PREFIX,
                    AddCompanyCommand.MESSAGE_USAGE));
        }

        CompanyName name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getOptionalValue(PREFIX_PHONE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getOptionalValue(PREFIX_EMAIL).get());
        Address address = ParserUtil.parseAddress(argMultimap.getOptionalValue(PREFIX_ADDRESS).get());

        RoleList roles = new RoleList();

        Company company = new Company(name, phone, email, address, roles, new FavouriteStatus(false));

        return new AddCompanyCommand(company);
    }

}
