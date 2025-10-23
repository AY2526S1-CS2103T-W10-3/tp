package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.EditAppointmentCommand.EditAppointmentDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Title;

public class EditAppointmentDescriptorTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy, HHmm");

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void isAnyFieldsEditedTitleTest() {
        // no title
        EditAppointmentDescriptor appt = new EditAppointmentDescriptor();
        assertFalse(appt.isAnyFieldEdited());

        // with title
        appt.setTitle(new Title("test title"));
        assertTrue(appt.isAnyFieldEdited());

    }

    @Test
    public void isAnyFieldsEditedDateTimeTest() {
        // no dateTime
        EditAppointmentDescriptor appt = new EditAppointmentDescriptor();
        assertFalse(appt.isAnyFieldEdited());

        // with dateTime
        appt.setDateTime(LocalDateTime.parse("02-02-2002, 0900", FORMATTER));
        assertTrue(appt.isAnyFieldEdited());

    }

    @Test
    public void isAnyFieldsEditedBothTest() {
        // with both
        EditAppointmentDescriptor appt = new EditAppointmentDescriptor();
        appt.setDateTime(LocalDateTime.parse("02-02-2002, 0900", FORMATTER));
        appt.setTitle(new Title("test title"));
        assertTrue(appt.isAnyFieldEdited());
    }

    @Test
    public void equalsTest() {
        // same object
        EditAppointmentDescriptor appt = new EditAppointmentDescriptor();
        appt.setDateTime(LocalDateTime.parse("02-02-2002, 0900", FORMATTER));
        appt.setTitle(new Title("test title"));
        assertTrue(appt.equals(appt));

        // wrong type
        String s = "wrong type";
        assertFalse(appt.equals(s));

        // wrong title
        EditAppointmentDescriptor appt2 = new EditAppointmentDescriptor();
        appt2.setTitle(new Title("wrong title"));
        appt2.setDateTime(LocalDateTime.parse("02-02-2002, 0900", FORMATTER));
        assertFalse(appt.equals(appt2));

        // wrong dateTime
        EditAppointmentDescriptor appt3 = new EditAppointmentDescriptor();
        appt.setDateTime(LocalDateTime.parse("02-02-2002, 1000", FORMATTER));
        appt.setTitle(new Title("test title"));
        assertFalse(appt.equals(appt3));
    }

    @Test
    public void toStringTest() {
        EditAppointmentDescriptor appt = new EditAppointmentDescriptor();
        appt.setDateTime(LocalDateTime.parse("02-02-2002, 0900", FORMATTER));
        appt.setTitle(new Title("test title"));

        String expectedString = new ToStringBuilder(appt)
                .add("title", appt.getTitle().orElse(new Title("a")))
                .add("date", appt.getDateTime().orElse(LocalDateTime.of(2025, 10, 1, 10, 0)))
                .toString();

        assertEquals(expectedString, appt.toString());
    }
}
