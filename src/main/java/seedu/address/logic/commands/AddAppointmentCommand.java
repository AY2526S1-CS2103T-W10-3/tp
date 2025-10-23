package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_DATETIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINTMENT_TITLE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Adds an appointment to the patient specified in Doctorbase.
 */
public class AddAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "add-appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to the address book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_APPOINTMENT_TITLE + "TITLE "
            + PREFIX_APPOINTMENT_DATETIME + "DATE_TIME (dd-MM-yyyy, HHmm)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_APPOINTMENT_TITLE + "Dental Checkup "
            + PREFIX_APPOINTMENT_DATETIME + "10-10-2010, 0900\n";

    public static final String MESSAGE_SUCCESS = "New appointment for %1$s: %2$s";
    public static final String MESSAGE_PATIENT_NOT_FOUND = "No patient with this NRIC exists in the address book.";
    public static final String MESSAGE_APPOINTMENT_TIME_CLASH =
            "The appointment has a clashing timing with another appointment.";

    private final Appointment toAdd;
    private final Index index;

    /**
     * Creates an AddAppointmentCommand to add the specified {@code Appointment} to the patient with the given NRIC.
     *
     */
    public AddAppointmentCommand(Index index, Appointment appointment) {
        requireAllNonNull(index, appointment);
        this.index = index;
        this.toAdd = appointment;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownList = model.getFilteredPatientList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }
        Patient targetPatient = lastShownList.get(index.getZeroBased());

        // Check for any duplicate timings
        ArrayList<Appointment> allAppointments = new ArrayList<>();
        for (int i = 0; i < lastShownList.size(); i++) {
            allAppointments.addAll(lastShownList.get(i).getAppointments());
        }
        if (allAppointments.stream().anyMatch(x -> x.clashTime(toAdd))) {
            throw new CommandException(MESSAGE_APPOINTMENT_TIME_CLASH);
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
                && index.equals(otherAddAppointmentCommand.index);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("toAdd", toAdd)
                .toString();
    }
}
