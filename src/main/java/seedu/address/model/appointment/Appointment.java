package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;


/**
 * Represents an Appointment in the address book.
 */
public class Appointment {
    public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy, HHmm");
    public static final String DATETIME_MESSAGE_CONSTRAINTS =
            "Appointment date invalid! Expected Format: dd-MM-yyyy, HHmm";

    private final Title title;
    private final LocalDateTime dateTime;

    /**
     * Constructs a {@code Appointment}.
     * @param title An appointment title
     * @param dateTime An appointment datetime
     */
    public Appointment(Title title, LocalDateTime dateTime) {
        requireAllNonNull(title, dateTime);
        this.title = title;
        this.dateTime = dateTime;
    }

    /**
     * Returns true if a given string is a valid Appointment DateTime.
     */
    public static boolean isValidDateTimeString(String test) {
        try {
            DATETIME_FORMAT.withResolverStyle(ResolverStyle.STRICT).parse(test);
            return true;
        } catch (NullPointerException | DateTimeParseException e) {
            return false;
        }
    }

    public Title getTitle() {
        return title;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeAsString() {
        return dateTime.format(DATETIME_FORMAT);
    }

    /**
     * Returns true if both Appointments have the same fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Appointment otherAppointment)) {
            return false;
        }

        return title.equals(otherAppointment.title) && dateTime.equals(otherAppointment.dateTime);
    }

    /**
     * Returns true if both appointments have the same dateTime
     */
    public boolean clashTime(Appointment other) {
        return dateTime.equals(other.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, dateTime);
    }

    public String toString() {
        return String.format("%s (%s)", title, getDateTimeAsString());
    }
}
