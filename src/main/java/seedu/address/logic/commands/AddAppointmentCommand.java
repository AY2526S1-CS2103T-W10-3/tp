package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TITLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;

import java.util.ArrayList;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the patient specified in the address book.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the address book. "
            + "Parameters: "
            + PREFIX_NRIC + "NRIC "
            + PREFIX_APPOINTMENT_TITLE + "TITLE "
            + PREFIX_APPOINTMENT_DATETIME + "DATE_TIME (dd-MM-yyyy, HHmm)\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NRIC + "S1234567A "
            + PREFIX_APPOINTMENT_TITLE + "Dental Checkup "
            + PREFIX_APPOINTMENT_DATETIME + "10-10-2010, 0900\n";

    public static final String MESSAGE_SUCCESS = "New appointment for %1$s: %2$s";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with this NRIC exists in the address book.";

    private final Appointment toAdd;
    private final String targetNric;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment} to the patient with the given NRIC.
     *
     */
    public AddAppointmentCommand(String targetNric, Appointment appointment) {
        requireAllNonNull(targetNric, appointment);
        requireNonNull(appointment);
        this.targetNric = targetNric;
        this.toAdd = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Patient targetPatient = model.findPatientByNric(targetNric);
        if (targetPatient == null) {
            throw new CommandException(MESSAGE_PATIENT_NOT_FOUND);
        }

        ArrayList<Appointment> updatedAppointments = new ArrayList<>(
                targetPatient.getAppointments()
        );
        updatedAppointments.add(toAdd);

        Patient newPatient = new Patient(
                targetPatient.getName(),
                targetPatient.getNric(),
                targetPatient.getGender(),
                targetPatient.getPhone(),
                targetPatient.getEmail(),
                targetPatient.getDob(),
                targetPatient.getAddress(),
                targetPatient.getTags(),
                updatedAppointments
        );

        model.setPatient(targetPatient, newPatient);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                Messages.format(newPatient), toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppointmentCommand)) {
            return false;
        }

        AddAppointmentCommand otherAddAppointmentCommand = (AddAppointmentCommand) other;
        return toAdd.equals(otherAddAppointmentCommand.toAdd)
                && targetNric.equals(otherAddAppointmentCommand.targetNric);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetNric", targetNric)
                .add("toAdd", toAdd)
                .toString();
    }
}
