package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_NOT_VIEWING_APPOINTMENT;

import java.util.ArrayList;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ViewMode;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Deletes an appointment to the patient specified in Doctorbase.
 */
public class DeleteAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "delete-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an appointment from DoctorBase.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_APPOINTMENT_SUCCESS = "Appointment for %1$s: %2$s deleted";
    public static final String MESSAGE_APPOINTMENT_NOT_FOUND = "No apppointment at index %1$s "
            + "exists in DoctorBase";

    private final Index targetIndex;

    /**
     * Creates a DeleteAppointmentCommand to delete the specified {@code Appointment}
     * from the patient with the given NRIC.
     */
    public DeleteAppointmentCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Patient targetPatient = model.getSelectedPatient();

        if (model.getViewMode() != ViewMode.PATIENT_APPOINTMENT_LIST) {
            throw new CommandException(MESSAGE_NOT_VIEWING_APPOINTMENT);
        }

        ArrayList<Appointment> targetPatientAppointments = targetPatient.getAppointments();
        if (targetIndex.getZeroBased() >= targetPatientAppointments.size()) {
            throw new CommandException(String.format(MESSAGE_APPOINTMENT_NOT_FOUND, targetIndex.getOneBased()));
        }

        Appointment deletedAppointment = targetPatientAppointments.remove(targetIndex.getZeroBased());

        // Updates the model with the updated appointment list
        model.setPatient(targetPatient, targetPatient);
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
        return targetIndex.equals(otherDeleteAppointmentCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
