package seedu.address.logic.commands;

/**
 * Utility class to build consistent confirmation prompts for destructive commands.
 */
public final class ConfirmationPromptFormatter {

    private static final String PROMPT_TEMPLATE = "Please confirm this action.%n"
            + "Action: %s%n"
            + "Impact: %s%n"
            + "Enter 'y' to proceed or 'n' to cancel.";

    private ConfirmationPromptFormatter() {}

    /**
     * Returns a readable, multi-line confirmation prompt.
     *
     * @param actionSummary Short description of the action that will be executed.
     * @param impactSummary Description of what will happen if the action proceeds.
     */
    public static String format(String actionSummary, String impactSummary) {
        return String.format(PROMPT_TEMPLATE, actionSummary, impactSummary);
    }
}
