package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedPatient.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Phone;

public class JsonAdaptedPatientTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_GENDER = "n";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_NRIC = "A1234567Z";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_DOB = "31-31-2002";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_TITLE = " ";
    private static final String INVALID_DATE = "";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_GENDER = BENSON.getGender().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_DOB = BENSON.getDob().toString();
    private static final String VALID_NRIC = BENSON.getNric().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());
    private static final List<JsonAdaptedAppointment> VALID_APPOINTMENTS =
            BENSON.getAppointments().stream()
            .map(JsonAdaptedAppointment::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validPatientDetails_returnsPatient() throws Exception {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(BENSON);
        assertEquals(BENSON, patient.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(INVALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE,
                VALID_EMAIL, VALID_DOB, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(null, VALID_NRIC, VALID_GENDER, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DOB, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidGender_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, INVALID_GENDER, VALID_PHONE,
                VALID_EMAIL, VALID_ADDRESS, VALID_DOB, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = Gender.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullGender_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, null, VALID_PHONE, VALID_EMAIL,
                VALID_ADDRESS, VALID_DOB, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER, INVALID_PHONE,
                VALID_EMAIL, VALID_DOB, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullPhone_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER, null, VALID_EMAIL,
                VALID_DOB, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE,
                INVALID_EMAIL, VALID_DOB, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullEmail_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE, null,
                VALID_DOB, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, INVALID_NRIC, VALID_GENDER, VALID_PHONE, VALID_EMAIL, VALID_DOB,
                        VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = Nric.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullNric_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, null, VALID_GENDER, VALID_PHONE, VALID_EMAIL,
                VALID_DOB, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Nric.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidDob_throwsIllegalValueException() {
        JsonAdaptedPatient patient =
                new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE, VALID_EMAIL, INVALID_DOB,
                        VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = DateOfBirth.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullDob_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE,
                VALID_EMAIL, null, VALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateOfBirth.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(
            VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE, VALID_EMAIL,
            VALID_DOB, INVALID_ADDRESS, VALID_TAGS, VALID_APPOINTMENTS
        );
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_nullAddress_throwsIllegalValueException() {
        JsonAdaptedPatient patient = new JsonAdaptedPatient(VALID_NAME, VALID_NRIC, VALID_GENDER,
                VALID_PHONE, VALID_EMAIL, VALID_DOB, null, VALID_TAGS, VALID_APPOINTMENTS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, patient::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedPatient patient = new JsonAdaptedPatient(
            VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE, VALID_EMAIL,
            VALID_DOB, VALID_ADDRESS, invalidTags, VALID_APPOINTMENTS
        );
        assertThrows(IllegalValueException.class, patient::toModelType);
    }

    @Test
    public void toModelType_invalidAppointments_throwsIllegalValueException() {
        List<JsonAdaptedAppointment> invalidAppointments = new ArrayList<>(VALID_APPOINTMENTS);
        invalidAppointments.add(new JsonAdaptedAppointment(INVALID_TITLE, INVALID_DATE));
        JsonAdaptedPatient patient = new JsonAdaptedPatient(
            VALID_NAME, VALID_NRIC, VALID_GENDER, VALID_PHONE, VALID_EMAIL,
            VALID_DOB, VALID_ADDRESS, VALID_TAGS, invalidAppointments
        );
        assertThrows(IllegalValueException.class, patient::toModelType);
    }

}
