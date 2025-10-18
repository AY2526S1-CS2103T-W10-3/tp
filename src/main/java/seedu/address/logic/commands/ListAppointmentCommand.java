package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.patient.Patient;

/**
 * List appointments of the specified patient in the address book.
 */
public class ListAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "list-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List appointments of a specified patient."
            + "Parameters: "
            + PREFIX_NRIC + "NRIC\n"
            + "Example: " + COMMAND_WORD + " i/S1234567A";

    public static final String MESSAGE_SUCCESS = "Listed all appointments for %1$s [%2$s]";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with this NRIC exists in the address book.";

    private final String targetNric;

    /**
     * Creates an ListAppointmentCommand to list appointments to the specified to the patient with the given NRIC.
     */
    public ListAppointmentCommand(String targetNric) {
        requireNonNull(targetNric);
        this.targetNric = targetNric;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient targetPatient = model.findPatientByNric(targetNric);
        if (targetPatient == null) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        model.setSelectedPatient(targetPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS, targetPatient.getName(),
                targetPatient.getNric()), false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListAppointmentCommand)) {
            return false;
        }

        ListAppointmentCommand otherListAppointmentCommand = (ListAppointmentCommand) other;
        return targetNric.equals(otherListAppointmentCommand.targetNric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .toString();
    }
}
