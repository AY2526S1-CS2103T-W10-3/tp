package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewMode;

/**
 * Lists upcoming appointments of all patients in the address book, sorted by their DateTimes.
 */
public class ListAppointmentUpcomingCommand extends Command {

    public static final String COMMAND_WORD = "list-appt-upcoming";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists upcoming appointments of all patients in DoctorBase.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all upcoming appointments";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        model.setSelectedPatient(null);
        model.setViewMode(ViewMode.UPCOMING_APPOINTMENT_LIST);
        return new CommandResult(String.format(MESSAGE_SUCCESS), false, true, false);
    }
}
