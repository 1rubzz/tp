package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Shows all departments in the right panel.
 */
public class ShowCommand extends Command {

    public static final String COMMAND_WORD = "show";
    public static final String MESSAGE_DEPARTMENT_LIST_SUCCESS = "Showing all departments";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all departments in the right panel.\n"
            + "Format: " + COMMAND_WORD + " d|dept|department\n"
            + "Examples: " + COMMAND_WORD + " d OR " + COMMAND_WORD + " department";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(
                MESSAGE_DEPARTMENT_LIST_SUCCESS,
                false,
                false,
                null,
                RightPanelView.DEPARTMENT_LIST);
    }
}


