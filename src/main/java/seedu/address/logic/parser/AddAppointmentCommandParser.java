package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.patient.Nric;

/**
 * Parses input arguments and creates a new AddAppointmentCommand object
 */
public class AddAppointmentCommandParser implements Parser<AddAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddAppointmentCommand
     * and returns an AddAppointmentCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NRIC,
                        PREFIX_APPOINTMENT_TITLE, PREFIX_APPOINTMENT_DATETIME);

        if (!arePrefixesPresent(argMultimap, PREFIX_NRIC,
                PREFIX_APPOINTMENT_TITLE, PREFIX_APPOINTMENT_DATETIME)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddAppointmentCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NRIC,
                PREFIX_APPOINTMENT_TITLE, PREFIX_APPOINTMENT_DATETIME);

        Nric nric = ParserUtil.parseNric(argMultimap.getValue(PREFIX_NRIC).get());
        Title title = ParserUtil.parseTitle(argMultimap.getValue(PREFIX_APPOINTMENT_TITLE).get());
        LocalDateTime dateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_APPOINTMENT_DATETIME).get());

        Appointment appointment = new Appointment(title, dateTime);

        return new AddAppointmentCommand(nric.toString(), appointment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
