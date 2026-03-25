package seedu.address.logic.commands;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

import static java.util.Objects.requireNonNull;

public class ImportCommand extends Command implements ConfirmableCommand{

    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_SUCCESS = "Imported employee list from local csv file.";
    public static final String ACTION_SUMMARY = "Import local list.";
    public static final String IMPACT_SUMMARY =
        "Local employee list will be imported, overwriting existing import list.";
    public static final String ACTION_DESCRIPTION = "import local list";

    @Override
    public String getConfirmationPrompt() {
        return ConfirmationPromptFormatter.format(ACTION_SUMMARY, IMPACT_SUMMARY);
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
