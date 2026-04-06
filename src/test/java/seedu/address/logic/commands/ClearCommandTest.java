package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    public void execute_clearThenUndo_restoresTypicalAddressBook() throws Exception {
        // 1. Setup the initial state with "Typical" employees
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        // 2. Execute the ClearCommand
        // We expect the model to now be empty and have a commit in its history
        ClearCommand clearCommand = new ClearCommand();
        expectedModel.setAddressBook(new AddressBook());
        expectedModel.commitAddressBook();

        // Use the framework's built-in assertion for the 'Clear' phase
        assertCommandSuccess(clearCommand, model, ClearCommand.MESSAGE_SUCCESS, expectedModel);

        // 3. Verify Undo availability
        assertTrue(model.canUndoAddressBook());

        // 4. Execute Undo and verify the state returns to TypicalAddressBook
        // Note: We use UndoCommand.MESSAGE_SUCCESS, not ClearCommand.MESSAGE_SUCCESS
        assertCommandSuccess(new UndoCommand(), model, UndoCommand.MESSAGE_SUCCESS,
                            new ModelManager(getTypicalAddressBook(), new UserPrefs()));
    }

    @Test
    public void getConfirmationPrompt_returnsExpectedPrompt() {
        ClearCommand clearCommand = new ClearCommand();
        assertEquals(
            ConfirmationPromptFormatter.format(
                ClearCommand.ACTION_SUMMARY,
                ClearCommand.IMPACT_SUMMARY),
            clearCommand.getConfirmationPrompt());
    }

    @Test
    public void getActionDescription_returnsExpectedDescription() {
        ClearCommand clearCommand = new ClearCommand();
        assertEquals(ClearCommand.ACTION_DESCRIPTION, clearCommand.getActionDescription());
    }

}
