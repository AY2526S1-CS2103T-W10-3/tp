package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

public class AppointmentTest {

    @Test
    public void constructor_validInput_success() {
        Title validTitle = new Title("Dentist Appointment");
        LocalDateTime validDate = LocalDateTime.of(2025, 10, 7, 14, 30);

        Appointment appointment = new Appointment(validTitle, validDate);

        // check field assignments
        assertEquals(validTitle, appointment.getTitle());
        assertEquals(validDate, appointment.getDateTime());

        // check string format (dd-MM-yyyy, HHmm)
        String expectedToString = "Dentist Appointment (07-10-2025, 1430)";
        assertEquals(expectedToString, appointment.toString());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        Title title = new Title("Dentist Appointment");
        LocalDateTime date = LocalDateTime.of(2025, 10, 7, 14, 30);

        assertThrows(NullPointerException.class, () -> new Appointment(title, null)); // null date
        assertThrows(NullPointerException.class, () -> new Appointment(null, date)); // null title
    }

    @Test
    public void equals() {
        Title title = new Title("Dentist Appointment");
        LocalDateTime date = LocalDateTime.of(2025, 10, 7, 14, 30);

        Appointment a = new Appointment(title, date);
        Appointment sameValues = new Appointment(
            new Title("Dentist Appointment"),
            LocalDateTime.of(2025, 10, 7, 14, 30)
        );
        Appointment differentTitle = new Appointment(new Title("Doctor Visit"), date);
        Appointment differentDate = new Appointment(title, LocalDateTime.of(2025, 10, 8, 9, 0));

        // same values -> true
        assertTrue(a.equals(sameValues));

        // same object -> true
        assertTrue(a.equals(a));

        // null -> false
        assertFalse(a.equals(null));

        // different title -> false
        assertFalse(a.equals(differentTitle));

        // different date -> false
        assertFalse(a.equals(differentDate));
    }

    @Test
    public void clashTimeTest() {
        Title title = new Title("Dentist Appointment");
        LocalDateTime date = LocalDateTime.of(2025, 10, 7, 14, 30);

        Appointment a = new Appointment(title, date);
        Appointment sameValues = new Appointment(
                new Title("Dentist Appointment"),
                LocalDateTime.of(2025, 10, 7, 14, 30)
        );
        Appointment differentTitle = new Appointment(new Title("Doctor Visit"), date);
        Appointment differentTime = new Appointment(title,
                LocalDateTime.of(2025, 10, 17, 14, 30));

        assertTrue(a.clashTime(sameValues));
        assertTrue(a.clashTime(differentTitle));
        assertFalse(a.clashTime(differentTime));
    }

    @Test
    public void toStringMethod() {
        Title title = new Title("Dentist Appointment");
        LocalDateTime date = LocalDateTime.of(2025, 10, 7, 14, 30);

        Appointment a = new Appointment(title, date);

        // Appointment.toString() -> "%s (%s)" where date is "dd-MM-yyyy, HHmm"
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy, HHmm");
        String expected = title + " (" + date.format(fmt) + ")";
        assertEquals(expected, a.toString());
    }
}
