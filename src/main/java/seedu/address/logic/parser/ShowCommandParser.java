package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Locale;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@link ShowCommand} object.
 */
public class ShowCommandParser implements Parser<ShowCommand> {

    @Override
    public ShowCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim().toLowerCase(Locale.ROOT);

        if (trimmedArgs.equals("d") || trimmedArgs.equals("dept") || trimmedArgs.equals("department")) {
            return new ShowCommand();
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }
}


