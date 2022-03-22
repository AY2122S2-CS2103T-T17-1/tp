package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tinner.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.role.Role;

public class DeleteRoleCommand extends Command {

    public static final String COMMAND_WORD = "deleteRole";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the role identified by the index number used in the displayed company list \n"
            + "followed by the index number used by the role in the specified company.\n"
            + "Parameters: COMPANY_INDEX ROLE_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 1";

    public static final String MESSAGE_DELETE_ROLE_SUCCESS = "Deleted Role: %1$s %1$s";

    private final Index companyIndex;
    private final Index roleIndex;

    /**
     * @param companyIndex of the company in the company list
     * @param roleIndex    of the role in the specified company
     */
    public DeleteRoleCommand(Index companyIndex, Index roleIndex) {
        requireAllNonNull(companyIndex, roleIndex);
        this.companyIndex = companyIndex;
        this.roleIndex = roleIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownCompanyList = model.getFilteredCompanyList();

        if (companyIndex.getZeroBased() >= lastShownCompanyList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        List<Role> lastShownRoleList = model.getFilteredRoleList(companyIndex);

        if (roleIndex.getZeroBased() >= lastShownRoleList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROLE_DISPLAYED_INDEX);
        }

        Role roleToDelete = lastShownRoleList.get(roleIndex.getZeroBased());
        model.deleteRole(companyIndex, roleToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_ROLE_SUCCESS, roleToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteRoleCommand // instanceof handles nulls
                && companyIndex.equals(((DeleteRoleCommand) other).companyIndex)) // state check
                && roleIndex.equals(((DeleteRoleCommand) other).roleIndex);
    }
}
