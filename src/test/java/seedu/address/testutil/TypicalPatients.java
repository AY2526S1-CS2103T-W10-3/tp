package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DOB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_DIABETES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_ECZEMA;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withNric("S3216508G")
            .withGender("Female")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withDob("12-12-2003")
            .withPhone("94351253")
            .withTags("eczema").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withNric("S9599208J")
            .withGender("Male")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withDob("08-10-2001")
            .withPhone("98765432")
            .withTags("diabetes", "asthma")
            .withAppointments(new ArrayList<>(List.of(
                new Appointment(
                        new Title("Dentist Appointment"),
                        LocalDateTime.of(2025, 10, 7, 14, 30)
                )
            ))).build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz")
            .withNric("T1257515C")
            .withGender("M")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withDob("15-02-1999")
            .withAddress("wall street").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier")
            .withNric("T4509297B")
            .withGender("M")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withDob("18-12-2002")
            .withAddress("10th street")
            .withTags("high blood pressure", "covid-19").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer")
            .withNric("F2827236R")
            .withGender("F")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withDob("01-01-2007")
            .withAddress("michegan ave").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz")
            .withNric("G5568363N")
            .withGender("F")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withDob("20-07-2005")
            .withAddress("little tokyo").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best")
            .withNric("M1393796J")
            .withGender("M")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withDob("20-03-2012")
            .withAddress("4th street").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withNric("S2427427F")
            .withGender("M").withPhone("8482424").withEmail("stefan@example.com").withDob("18-12-2002")
            .withAddress("little india").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withNric("S8306085I")
            .withGender("F").withPhone("8482131").withEmail("hans@example.com").withDob("02-08-1988")
            .withAddress("chicago ave").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY =
            new PatientBuilder().withName(VALID_NAME_AMY).withNric(VALID_NRIC_AMY)
            .withGender(VALID_GENDER_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
            .withDob(VALID_DOB_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_DIABETES).build();
    public static final Patient BOB =
            new PatientBuilder().withName(VALID_NAME_BOB).withNric(VALID_NRIC_BOB)
            .withGender(VALID_GENDER_BOB).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
            .withDob(VALID_DOB_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_ECZEMA, VALID_TAG_DIABETES)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical patients.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Patient patient : getTypicalPatients()) {
            ab.addPatient(patient);
        }
        return ab;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
