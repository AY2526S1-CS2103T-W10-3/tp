package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    public void isValidGender() {
        // null gender
        assertThrows(NullPointerException.class, () -> Gender.isValidGender(null));

        // blank gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only

        // invalid gender
        assertFalse(Gender.isValidGender("k")); // Invalid lowercase short form of Gender
        assertFalse(Gender.isValidGender("K")); // Invalid uppercase short form of Gender
        assertFalse(Gender.isValidGender("female Male")); // Invalid full word gender
        assertFalse(Gender.isValidGender("femal")); // Misspelled full word female
        assertFalse(Gender.isValidGender("FEMAL")); // Misspelled uppercase full word female
        assertFalse(Gender.isValidGender("ma")); // Misspelled full word male

        // valid gender
        assertTrue(Gender.isValidGender("m")); // Lowercase short form of Male
        assertTrue(Gender.isValidGender("M")); // Uppercase short form of Male
        assertTrue(Gender.isValidGender("f")); // Lowercase short form of Female
        assertTrue(Gender.isValidGender("F")); // Uppercase short form of Female
        assertTrue(Gender.isValidGender("o")); // Lowercase short form of Other
        assertTrue(Gender.isValidGender("O")); // Uppercase short form of Other

        assertTrue(Gender.isValidGender("male")); // Lowercase full word Male
        assertTrue(Gender.isValidGender("MALE")); // Uppercase full word Male
        assertTrue(Gender.isValidGender("Male")); // Capitalized full word Male
        assertTrue(Gender.isValidGender("mAlE")); // Mixed case full word Male

        assertTrue(Gender.isValidGender("female")); // Lowercase full word Female
        assertTrue(Gender.isValidGender("FEMALE")); // Uppercase full word Female
        assertTrue(Gender.isValidGender("Female")); // Capitalized full word Female
        assertTrue(Gender.isValidGender("fEmAlE")); // Mixed case full word Female

        assertTrue(Gender.isValidGender("other")); // Lowercase full word Other
        assertTrue(Gender.isValidGender("OTHER")); // Uppercase full word Other
        assertTrue(Gender.isValidGender("Other")); // Capitalized full word Other
        assertTrue(Gender.isValidGender("oThEr")); // Mixed case full word Other
    }

    @Test
    public void equals() {
        Gender gender = new Gender("m");

        // same values -> returns true
        assertTrue(gender.equals(new Gender("m")));

        // same object -> returns true
        assertTrue(gender.equals(gender));

        // null -> returns false
        assertFalse(gender.equals(null));

        // different types -> returns false
        assertFalse(gender.equals(5.0f));

        // different values -> returns false
        assertFalse(gender.equals(new Gender("f")));

        // different input for same gender -> returns true
        assertTrue(gender.equals(new Gender("M")));

        // different input for same gender -> returns true
        assertTrue(gender.equals(new Gender("Male")));

        // different input for same gender -> returns true
        assertTrue(gender.equals(new Gender("male")));

        // different input for same gender -> returns true
        assertTrue(gender.equals(new Gender("MAlE")));
    }
}
