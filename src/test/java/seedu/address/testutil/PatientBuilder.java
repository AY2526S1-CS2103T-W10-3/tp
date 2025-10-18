package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Patient objects.
 */
public class PatientBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_NRIC = "T7060267E";
    public static final String DEFAULT_GENDER = "Female";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_DOB = "12-12-2002";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private Name name;
    private Nric nric;
    private Gender gender;
    private Phone phone;
    private Email email;
    private DateOfBirth dateOfBirth;
    private Address address;
    private Set<MedicalHistory> medicalHistories;
    private ArrayList<Appointment> appointments;

    /**
     * Creates a {@code PatientBuilder} with the default details.
     */
    public PatientBuilder() {
        name = new Name(DEFAULT_NAME);
        nric = new Nric(DEFAULT_NRIC);
        gender = new Gender(DEFAULT_GENDER);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        dateOfBirth = new DateOfBirth(DEFAULT_DOB);
        address = new Address(DEFAULT_ADDRESS);
        medicalHistories = new HashSet<>();
        appointments = new ArrayList<>();
    }

    /**
     * Initializes the PatientBuilder with the data of {@code patientToCopy}.
     */
    public PatientBuilder(Patient patientToCopy) {
        name = patientToCopy.getName();
        nric = patientToCopy.getNric();
        gender = patientToCopy.getGender();
        phone = patientToCopy.getPhone();
        email = patientToCopy.getEmail();
        dateOfBirth = patientToCopy.getDob();
        address = patientToCopy.getAddress();
        medicalHistories = new HashSet<>(patientToCopy.getTags());
        appointments = new ArrayList<>(patientToCopy.getAppointments());
    }

    /**
     * Sets the {@code Name} of the {@code Patient} that we are building.
     */
    public PatientBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code Patient} that we are building.
     */
    public PatientBuilder withNric(String nric) {
        this.nric = new Nric(nric);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code Patient} that we are building.
     */
    public PatientBuilder withGender(String gender) {
        this.gender = new Gender(gender);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Patient} that we are building.
     */
    public PatientBuilder withTags(String ... tags) {
        this.medicalHistories = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Patient} that we are building.
     */
    public PatientBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Patient} that we are building.
     */
    public PatientBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Dob} of the {@code Patient} that we are building.
     */
    public PatientBuilder withDob(String dob) {
        this.dateOfBirth = new DateOfBirth(dob);
        return this;
    }

    /**
     * Sets the {@code Appointment} of the {@code Patient} that we are building.
     */
    public PatientBuilder withAppointments(ArrayList<Appointment> appointments) {
        if (appointments == null) {
            this.appointments = new ArrayList<>();
            return this;
        }
        this.appointments = appointments;
        return this;
    }

    public Patient build() {
        return new Patient(name, nric, gender, phone, email, dateOfBirth, address, medicalHistories, appointments);
    }

}
