package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAppointmentCommand.
 */
public class ListAppointmentCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ListAppointmentCommand(null));
    }

    @Test
    public void execute_patientExists_success() throws Exception {
        ListAppointmentCommand command = new ListAppointmentCommand(BENSON.getNric().toString());
        Patient patient = model.findPatientByNric(BENSON.getNric().toString());

        // Benson has 1 appointment
        String expectedMessage = String.format(ListAppointmentCommand.MESSAGE_SUCCESS, patient.getName(),
                patient.getNric());
        Model expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

        command = new ListAppointmentCommand(ALICE.getNric().toString());
        patient = model.findPatientByNric(ALICE.getNric().toString());

        // Alice has no appointment
        expectedMessage = String.format(ListAppointmentCommand.MESSAGE_SUCCESS, patient.getName(),
                patient.getNric());
        expectedModel = new ModelManager(model.getAddressBook(), model.getUserPrefs());

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_patientNotFound_throwsCommandException() {
        ListAppointmentCommand command = new ListAppointmentCommand("S1234567Z");

        assertCommandFailure(command, model, ListAppointmentCommand.MESSAGE_PATIENT_NOT_FOUND);
    }

    @Test
    public void equals() {
        ListAppointmentCommand cmd1 = new ListAppointmentCommand("S1234567A");
        ListAppointmentCommand cmd2 = new ListAppointmentCommand("S1234567A");
        ListAppointmentCommand cmd3 = new ListAppointmentCommand("S1234567B");

        // same object -> true
        assertTrue(cmd1.equals(cmd1));

        // same values -> true
        assertTrue(cmd1.equals(cmd2));

        // null -> false
        assertFalse(cmd1.equals(null));

        // different type -> false
        assertFalse(cmd1.equals(1));

        // different NRIC -> false
        assertFalse(cmd1.equals(cmd3));
    }

    @Test
    public void toStringMethod() {
        ListAppointmentCommand command = new ListAppointmentCommand("S1234567A");
        String expected = ListAppointmentCommand.class.getCanonicalName()
                + "{targetNric=S1234567A}";
        assertEquals(expected, command.toString());
    }
}
