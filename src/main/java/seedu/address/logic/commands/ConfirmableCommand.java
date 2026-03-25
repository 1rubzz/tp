package seedu.address.logic.commands;

/**
 * Represents a command that requires explicit user confirmation before execution.
 */
public interface ConfirmableCommand {

    /**
     * Returns the prompt shown to the user when confirmation is required.
     */
    String getConfirmationPrompt();

    /**
     * Returns a short action description used in cancellation feedback.
     */
    String getActionDescription();
}
