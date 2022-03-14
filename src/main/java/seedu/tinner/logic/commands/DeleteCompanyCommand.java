package seedu.tinner.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.tinner.commons.core.Messages;
import seedu.tinner.commons.core.index.Index;
import seedu.tinner.logic.commands.exceptions.CommandException;
import seedu.tinner.model.Model;
import seedu.tinner.model.company.Company;

/**
 * Deletes a company identified using it's displayed index from the address book.
 */
public class DeleteCompanyCommand extends Command {

    public static final String COMMAND_WORD = "deleteCompany";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the company identified by the index number used in the displayed company list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_COMPANY_SUCCESS = "Deleted Company: %1$s";

    private final Index targetIndex;

    public DeleteCompanyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Company> lastShownList = model.getFilteredCompanyList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_COMPANY_DISPLAYED_INDEX);
        }

        Company companyToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteCompany(companyToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_COMPANY_SUCCESS, companyToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCompanyCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCompanyCommand) other).targetIndex)); // state check
    }
}
