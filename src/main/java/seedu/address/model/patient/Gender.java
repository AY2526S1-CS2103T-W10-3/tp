package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's Gender in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidGender(String)}
 */
public class Gender {

    public static final String VALIDATION_REGEX = "(?i)\\s*(male|female|other|m|f|o)\\s*";

    public static final String MESSAGE_CONSTRAINTS =
            "Gender should be one of: Male (m), Female (f), or Other (o).";

    public final GenderType gender;

    /**
     * Enum representing valid gender types.
     */
    public enum GenderType {
        MALE("Male", "m"),
        FEMALE("Female", "f"),
        OTHER("Other", "o");

        private final String full;
        private final String shortForm;

        GenderType(String full, String shortForm) {
            this.full = full;
            this.shortForm = shortForm;
        }

        /**
         * Returns the corresponding {@code GenderType} for the given string representation.
         * This method performs a case-insensitive comparison and accepts both the short form
         * (e.g., "m", "f", "o") and the full form (e.g., "male", "female", "other") of a gender.
         * Leading and trailing whitespace are ignored.
         *
         * @param value the input string representing a gender; may be a short or full form
         * @return the matching {@code GenderType} constant
         * @throws IllegalArgumentException if {@code value} is {@code null} or does not correspond
         *         to any defined gender type
         */
        public static GenderType fromString(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Gender cannot be null");
            }
            String v = value.trim().toLowerCase();
            for (GenderType g : GenderType.values()) {
                if (g.full.toLowerCase().equals(v) || g.shortForm.equals(v)) {
                    return g;
                }
            }
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }

        @Override
        public String toString() {
            return full;
        }
    }

    /**
     * Constructs a {@code Gender}.
     *
     * @param gender A valid gender.
     */
    public Gender(String gender) {
        requireNonNull(gender);
        checkArgument(isValidGender(gender), MESSAGE_CONSTRAINTS);
        this.gender = GenderType.fromString(gender);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidGender(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return this.gender.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Gender)) {
            return false;
        }

        Gender otherGender = (Gender) other;
        return this.gender.equals(otherGender.gender);
    }

    @Override
    public int hashCode() {
        return this.gender.hashCode();
    }
}
