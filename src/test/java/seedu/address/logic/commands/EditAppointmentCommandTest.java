package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.EditAppointmentDescriptorBuilder;

public class EditAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        model.setSelectedPatient(BENSON);

        // change both title and dateTime
        Appointment editedAppointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                editedAppointment
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        model.setSelectedPatient(BENSON);

        // only change dateTime
        Appointment editedAppointment = new AppointmentBuilder()
                .withDateTime("20-02-2002, 1000").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                editedAppointment
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        model.setSelectedPatient(BENSON);

        // no change
        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                editedAppointment
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_selectedPatientNull_throwsCommandException() {
        // Set selectedPatient to null to indicate not viewing Appointments
        model.setSelectedPatient(null);

        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        assertCommandFailure(editAppointmentCommand, model, EditAppointmentCommand.MESSAGE_NOT_VIEWING_APPOINTMENT);
    }

    @Test
    public void equalsTest() {
        // same object
        Appointment appointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(appointment).build();
        EditAppointmentCommand command = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        assertTrue(command.equals(command));

        // wrong type
        String str = "wrong type";
        assertFalse(command.equals(str));

        // wrong index
        EditAppointmentCommand cmd1 = new EditAppointmentCommand(INDEX_SECOND_APPOINTMENT, descriptor);
        assertFalse(command.equals(cmd1));

        // wrong appointment
        Appointment ppt = new AppointmentBuilder().withTitle("fake disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor desc1 = new EditAppointmentDescriptorBuilder(ppt).build();
        EditAppointmentCommand cmd3 = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, desc1);
        assertFalse(command.equals(cmd3));

        // equal attributes
        Appointment appt = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor desc = new EditAppointmentDescriptorBuilder(appt).build();
        EditAppointmentCommand cmd4 = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, desc);
        assertTrue(command.equals(cmd4));
    }

    @Test
    public void invalidIndexTest_fail() {
        model.setSelectedPatient(BENSON);
        Index invalid = Index.fromZeroBased(model.getSelectedPatient().getAppointments().size());
        Appointment editedAppointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("07-10-2025, 1530").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(invalid, descriptor);

        assertCommandFailure(editAppointmentCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void duplicateTimingTest_fail() {
        model.setSelectedPatient(BENSON);
        // change both title and dateTime
        Appointment editedAppointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("07-10-2025, 1530").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        assertCommandFailure(editAppointmentCommand, model, EditAppointmentCommand.MESSAGE_APPOINTMENT_TIME_CLASH);
    }

    @Test
    public void duplicateTimingSameAppointmentTest_success() {
        model.setSelectedPatient(BENSON);

        // only change dateTime
        Appointment editedAppointment = new AppointmentBuilder()
                .withTitle("test title").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedMessage = String.format(
                EditAppointmentCommand.MESSAGE_SUCCESS,
                editedAppointment
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPatient(model.getFilteredPatientList().get(1), newPatient);

        assertCommandSuccess(editAppointmentCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void toStringTest() {
        model.setSelectedPatient(BENSON);

        Appointment editedAppointment = new AppointmentBuilder().withTitle("new disease")
                .withDateTime("20-02-2002, 0900").build();
        EditAppointmentDescriptor descriptor = new EditAppointmentDescriptorBuilder(editedAppointment).build();
        EditAppointmentCommand editAppointmentCommand = new EditAppointmentCommand(INDEX_FIRST_APPOINTMENT, descriptor);
        Patient newPatient = new Patient(BENSON.getName(),
                BENSON.getNric(),
                BENSON.getGender(),
                BENSON.getPhone(),
                BENSON.getEmail(),
                BENSON.getDob(),
                BENSON.getAddress(),
                BENSON.getTags(),
                BENSON.getAppointments());

        String expectedString = new ToStringBuilder(editAppointmentCommand)
                .add("index", INDEX_FIRST_APPOINTMENT)
                .add("editPatientDescriptor", descriptor)
                .toString();
        assertEquals(expectedString, editAppointmentCommand.toString());
    }

}
