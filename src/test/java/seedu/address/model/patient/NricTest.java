package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NricTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructor_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "";
        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void isValidNric() {
        // null name
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // invalid name
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("A1234567Z")); // Not valid starting character

        // valid name
        assertTrue(Nric.isValidNric("S3225623F")); // Start with S
        assertTrue(Nric.isValidNric("T5437220A")); // Start with T
        assertTrue(Nric.isValidNric("F1718035P")); // Start with F
        assertTrue(Nric.isValidNric("G4319841L")); // Start with G
        assertTrue(Nric.isValidNric("M5033954P")); // Start with M
    }

    @Test
    public void equals() {
        Nric nric = new Nric("S3225623F");

        // same values -> returns true
        assertTrue(nric.equals(new Nric("S3225623F")));

        // same object -> returns true
        assertTrue(nric.equals(nric));

        // null -> returns false
        assertFalse(nric.equals(null));

        // different types -> returns false
        assertFalse(nric.equals(5.0f));

        // different values -> returns false
        assertFalse(nric.equals(new Name("T5437220A")));
    }
}
