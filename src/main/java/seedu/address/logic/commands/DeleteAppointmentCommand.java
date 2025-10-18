package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the patient specified in the address book.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment from the address book. "
            + "Parameters: "
            + PREFIX_NRIC + " NRIC "
            + PREFIX_INDEX + " INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_NRIC + " S1234567A " + PREFIX_INDEX + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Appointment for %1$s: %2$s deleted";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with this NRIC exists in the address book.";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "No apppointment for this NRIC at index %1$s "
            + "exists in the address book";

    private final String targetNric;
    private final Index targetIndex;

    /**
     * Creates a DeleteAppointmentCommand to delete the specified {@code Appointment}
     * from the patient with the given NRIC.
     *
     */
    public DeleteAppointmentCommand(String targetNric, Index targetIndex) {
        requireAllNonNull(targetNric, targetIndex);
        this.targetNric = targetNric;
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient targetPatient = model.findPatientByNric(targetNric);
        if (targetPatient == null) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        ArrayList<Appointment> targetPatientAppointments = targetPatient.getAppointments();
        if (targetIndex.getZeroBased() >= targetPatientAppointments.size()) {
            throw new CommandException(String.format(MESSAGE_APPOINTMENT_NOT_FOUND, targetIndex.getOneBased()));
        }

        Appointment deletedAppointment = targetPatientAppointments.remove(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(targetPatient), deletedAppointment), false, true, false);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteAppointmentCommand)) {
            return false;
        }

        DeleteAppointmentCommand otherDeleteAppointmentCommand = (DeleteAppointmentCommand) other;
        return targetIndex.equals(otherDeleteAppointmentCommand.targetIndex)
                && targetNric.equals(otherDeleteAppointmentCommand.targetNric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
