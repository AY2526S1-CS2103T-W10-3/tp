package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.DeleteAppointmentCommand.MESSAGE_APPOINTMENT_NOT_FOUND;
import static seedu.address.logic.commands.DeleteAppointmentCommand.MESSAGE_PATIENT_NOT_FOUND;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteAppointmentCommand}.
 */
public class DeleteAppointmentCommandTest {

    @Test
    public void execute_validNricValidIndex_success() throws Exception {
        Patient patientWithAppointment = createPatientWithAppointment();

        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                patientWithAppointment.getNric().toString(),
                INDEX_FIRST_PATIENT);

        Appointment appointmentToDelete = patientWithAppointment.getAppointments()
                .get(INDEX_FIRST_PATIENT.getZeroBased());
        String expectedMessage = String.format(DeleteAppointmentCommand.MESSAGE_DELETE_APPOINTMENT_SUCCESS,
                Messages.format(patientWithAppointment), appointmentToDelete);

        ModelStubWithPatient modelStub = new ModelStubWithPatient(patientWithAppointment);
        CommandResult commandResult = deleteAppointmentCommand.execute(modelStub);

        Patient updatedPatient = modelStub.patient;
        assertEquals(expectedMessage, commandResult.getFeedbackToUser());
        assertTrue(updatedPatient.getAppointments().isEmpty());
    }

    @Test
    public void execute_validNricInvalidIndex_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(),
                INDEX_SECOND_PATIENT);

        ModelStubWithPatient modelStub = new ModelStubWithPatient(BENSON);
        assertCommandFailure(deleteAppointmentCommand, modelStub,
                String.format(MESSAGE_APPOINTMENT_NOT_FOUND, INDEX_SECOND_PATIENT.getOneBased()));
    }

    @Test
    public void execute_invalidNric_throwsCommandException() {
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(
                ALICE.getNric().toString(),
                INDEX_FIRST_PATIENT);

        ModelStubWithPatient modelStub = new ModelStubWithPatient(BENSON);
        assertCommandFailure(deleteAppointmentCommand, modelStub, MESSAGE_PATIENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        DeleteAppointmentCommand deleteAppointmentFirstCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_PATIENT);
        DeleteAppointmentCommand deleteAppointmentSecondCommand = new DeleteAppointmentCommand(
                BENSON.getNric().toString(), INDEX_SECOND_PATIENT);
        DeleteAppointmentCommand deleteAppointmentThirdCommand = new DeleteAppointmentCommand(
                ALICE.getNric().toString(), INDEX_FIRST_PATIENT);

        // same object -> returns true
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommand));

        // same values -> returns true
        DeleteAppointmentCommand deleteAppointmentFirstCommandCopy = new DeleteAppointmentCommand(
                BENSON.getNric().toString(), INDEX_FIRST_PATIENT);
        assertTrue(deleteAppointmentFirstCommand.equals(deleteAppointmentFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(null));

        // different index -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentSecondCommand));

        // different nric -> returns false
        assertFalse(deleteAppointmentFirstCommand.equals(deleteAppointmentThirdCommand));
    }

    @Test
    public void toStringMethod() {
        String targetNric = BENSON.getNric().toString();
        Index targetIndex = Index.fromOneBased(1);
        DeleteAppointmentCommand deleteAppointmentCommand = new DeleteAppointmentCommand(targetNric, targetIndex);
        String expected = DeleteAppointmentCommand.class.getCanonicalName() + "{targetNric=" + targetNric + ", "
                + "targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteAppointmentCommand.toString());
    }

    private Patient createPatientWithAppointment() {
        return new PatientBuilder().withName("Benson Meier")
                .withNric("S9599208J")
                .withGender("Male")
                .withAddress("311, Clementi Ave 2, #02-25")
                .withEmail("johnd@example.com")
                .withDob("08-10-2001")
                .withPhone("98765432")
                .withTags("owesMoney", "friends")
                .withAppointments(new ArrayList<>(List.of(
                        new Appointment(
                                new Title("Dentist Appointment"),
                                LocalDateTime.of(2025, 10, 7, 14, 30)
                        )
                ))).build();
    }

    /**
     * A default model stub that have all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient findPatientByNric(String targetNric) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Patient getSelectedPatient() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSelectedPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub with a single existing patient.
     */
    private class ModelStubWithPatient extends ModelStub {
        private final AddressBook addressBook;
        private final Patient patient;

        private final FilteredList<Patient> filteredPatients;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
            this.addressBook = new AddressBook();
            addressBook.addPatient(patient);
            this.filteredPatients = new FilteredList<>(addressBook.getPatientList());
        }

        @Override
        public Patient findPatientByNric(String targetNric) {
            if (patient.getNric().toString().equalsIgnoreCase(targetNric)) {
                return patient;
            }
            return null;
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            return filteredPatients;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }


    }
}
