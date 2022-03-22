package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.tinner.logic.parser.CliSyntax.PREFIX_STIPEND;

import java.util.List;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.role.Role;

/**
 * Adds a Role to the company.
 */
public class AddRoleCommand extends Command {

    public static final String COMMAND_WORD = "addRole";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a role to the company listed. "
            + "Parameters: "
            + "companyIndex "
            + PREFIX_NAME + "ROLE_NAME (TYPE) "
            + PREFIX_STATUS + "STATUS "
            + PREFIX_DEADLINE + "DEADLINE "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + PREFIX_STIPEND + "STIPEND\n"
            + "Example: " + COMMAND_WORD + " " + "1 "
            + PREFIX_NAME + "Software Engineer "
            + PREFIX_STATUS + "pending "
            + PREFIX_DEADLINE + "31-01-2022 23:59 "
            + PREFIX_DESCRIPTION + "Frontend "
            + PREFIX_STIPEND + "1000 ";

    public static final String MESSAGE_SUCCESS = "New role added: %1$s";
    public static final String MESSAGE_DUPLICATE_ROLE = "This role already exists in this company's role list";

    private final Index companyIndex;
    private final Role toAddRole;

    /**
     * Creates an AddRoleCommand to add the specified {@code Company}
     */
    public AddRoleCommand(Index companyIndex, Role role) {
        requireAllNonNull(companyIndex, role);
        this.companyIndex = companyIndex;
        this.toAddRole = role;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> companyList = model.getFilteredCompanyList();

        if (companyIndex.getZeroBased() >= companyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        if (model.hasRole(companyIndex, toAddRole)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROLE);
        }

        model.addRole(companyIndex, toAddRole);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddRole));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddRoleCommand // instanceof handles nulls
                && toAddRole.equals(((AddRoleCommand) other).toAddRole));
    }
}
