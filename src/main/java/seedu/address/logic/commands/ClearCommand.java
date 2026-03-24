package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command implements ConfirmableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Employee list has been cleared!";
    public static final String MESSAGE_CONFIRMATION_PROMPT = "Are you sure you want to clear all employees? [y/n]";
    public static final String ACTION_DESCRIPTION = "clear all employees";

    @Override
    public String getConfirmationPrompt() {
        return MESSAGE_CONFIRMATION_PROMPT;
    }

    @Override
    public String getActionDescription() {
        return ACTION_DESCRIPTION;
    }


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
