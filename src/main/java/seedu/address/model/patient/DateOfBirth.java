package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a Patient's DOB in the address book
 * Guarantees: immutable; is valid as declared in {@link #isValidDateOfBirth(String)}
 */
public class DateOfBirth {

    public static final String MESSAGE_CONSTRAINTS =
            "DOB must be in DD-MM-YYYY format.";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    /*
     * Regex for DOB
     */
    // public static final String VALIDATION_REGEX = "^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$";

    public final String value;
    public final LocalDate date;

    /**
     * Constructs a {@code Dob}.
     *
     * @param dob A valid dob
     */
    public DateOfBirth(String dob) {
        requireNonNull(dob);
        checkArgument(isValidDateOfBirth(dob), MESSAGE_CONSTRAINTS);
        value = dob;
        date = LocalDate.parse(dob, FORMATTER);
    }

    /**
     * Test to check validity of given dob
     *
     * @param test given dob to test
     * @return true if a given string is a valid dob
     */
    public static boolean isValidDateOfBirth(String test) {
        try {
            LocalDate.parse(test, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }

    /**
     * Method to obtain age of patient
     *
     * @return Age of patient as a String
     */
    public String getAge() {
        Period period = Period.between(this.date, LocalDate.now());
        return String.valueOf(period.getYears());
    }


    @Override
    public String toString() {
        return date.format(FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DateOfBirth)) {
            return false;
        }

        DateOfBirth otherDateOfBirth = (DateOfBirth) other;
        return value.equals(otherDateOfBirth.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
