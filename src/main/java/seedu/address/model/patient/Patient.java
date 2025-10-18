package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.medicalhistory.MedicalHistory;

/**
 * Represents a Patient in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    // Identity fields
    private final Name name;
    private final Nric nric;
    private final Gender gender;
    private final Phone phone;
    private final Email email;
    private final DateOfBirth dateOfBirth;

    // Data fields
    private final Address address;
    private final Set<MedicalHistory> medicalHistories = new HashSet<>();
    private final ArrayList<Appointment> appointments;

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Nric nric, Gender gender, Phone phone, Email email, DateOfBirth dateOfBirth,
                  Address address, Set<MedicalHistory> medicalHistories, ArrayList<Appointment> appointments) {
        requireAllNonNull(name, nric, gender, phone, email, dateOfBirth, address, medicalHistories, appointments);
        this.name = name;
        this.nric = nric;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.medicalHistories.addAll(medicalHistories);
        this.appointments = appointments;
    }

    public Nric getNric() {
        return nric;
    }

    public DateOfBirth getDob() {
        return dateOfBirth;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<MedicalHistory> getTags() {
        return Collections.unmodifiableSet(medicalHistories);
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    /**
     * Returns true if both patients have the same name.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getNric().equals(getNric());
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return name.equals(otherPatient.name)
                && nric.equals(otherPatient.nric)
                && gender.equals(otherPatient.gender)
                && phone.equals(otherPatient.phone)
                && email.equals(otherPatient.email)
                && dateOfBirth.equals(otherPatient.dateOfBirth)
                && address.equals(otherPatient.address)
                && medicalHistories.equals(otherPatient.medicalHistories);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, nric, gender, phone, email, dateOfBirth, address, medicalHistories);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("nric", nric)
                .add("gender", gender)
                .add("phone", phone)
                .add("email", email)
                .add("dob", dateOfBirth)
                .add("address", address)
                .add("medicalhistory", medicalHistories)
                .toString();
    }

}
