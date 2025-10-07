package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Appointment's title in the address book.
 */
public class Title {

    public static final String MESSAGE_CONSTRAINTS =
            "Appointment title should start with an alphanumeric character, "
                    + "and contain only alphanumeric characters, spaces, commas, apostrophes, "
                    + "parentheses, or hyphens.";

    /*
     * The first character must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "\\p{Alnum}[\\p{Alnum} ,'()\\-]*";

    public final String title;

    /**
     * Constructs a {@code Title}.
     *
     * @param title A valid title.
     */
    public Title(String title) {
        requireNonNull(title);
        checkArgument(isValidTitle(title), MESSAGE_CONSTRAINTS);
        this.title = title;
    }

    /**
     * Returns true if a given string is a valid title.
     */
    public static boolean isValidTitle(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Title otherTitle)) {
            return false;
        }

        return title.equals(otherTitle.title);
    }

    @Override
    public int hashCode() {
        return title.hashCode();
    }
}
