package seedu.address.model.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Title;
import seedu.address.model.medicalhistory.MedicalHistory;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.DateOfBirth;
import seedu.address.model.patient.Email;
import seedu.address.model.patient.Gender;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(
                new Name("Alex Yeoh"),
                new Nric("S2743251D"),
                new Gender("m"),
                new Phone("87438807"),
                new Email("alexyeoh@example.com"),
                new DateOfBirth("12-12-2001"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("overweight"),
                new ArrayList<>(List.of(
                    new Appointment(
                        new Title("Dentist Appointment"),
                        LocalDateTime.of(2025, 10, 7, 14, 30)
                    ),
                    new Appointment(
                            new Title("Follow-up, Eczema"),
                            LocalDateTime.of(2025, 11, 7, 15, 45)
                    ),
                    new Appointment(
                            new Title("Routine Check-up"),
                            LocalDateTime.of(2025, 12, 7, 10, 30)
                    )
                ))
            ),
            new Patient(
                new Name("Bernice Yu"),
                new Nric("S7101271I"),
                new Gender("f"),
                new Phone("99272758"),
                new Email("berniceyu@example.com"),
                new DateOfBirth("30-06-1999"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("low sugar", "high blood pressure"),
                new ArrayList<>(List.of(
                    new Appointment(
                        new Title("Flu Jab"),
                        LocalDateTime.of(2025, 11, 7, 16, 30)
                    )
                ))
            ),
            new Patient(
                new Name("Charlotte Oliveiro"),
                new Nric("T3726790I"),
                new Gender("f"),
                new Phone("93210283"),
                new Email("charlotte@example.com"),
                new DateOfBirth("01-01-2000"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("irregular heart rate"),
                new ArrayList<>(List.of(
                    new Appointment(
                        new Title("Room 2B"),
                        LocalDateTime.of(2024, 12, 7, 16, 30)
                    )
                ))
            ),
            new Patient(
                new Name("David Li"),
                new Nric("F0512458K"),
                new Gender("m"),
                new Phone("91031282"),
                new Email("lidavid@example.com"),
                new DateOfBirth("10-12-2015"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet("covid"),
                new ArrayList<>(List.of(
                    new Appointment(
                        new Title("Follow-Up, Eczema"),
                        LocalDateTime.of(2024, 10, 8, 16, 30)
                    )
                ))
            ),
            new Patient(
                new Name("Irfan Ibrahim"),
                new Nric("G4160131R"),
                new Gender("m"),
                new Phone("92492021"),
                new Email("irfan@example.com"),
                new DateOfBirth("02-08-2003"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("hepatitis B"),
                new ArrayList<>(List.of(
                    new Appointment(
                        new Title("Covid-19"),
                        LocalDateTime.of(2024, 10, 9, 16, 30)
                    )
                ))
            ),
            new Patient(
                new Name("Roy Balakrishnan"),
                new Nric("M1902102L"),
                new Gender("m"),
                new Phone("92624417"),
                new Email("royb@example.com"),
                new DateOfBirth("18-02-2001"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("Eczema"),
                new ArrayList<>(List.of(
                    new Appointment(
                        new Title("Covid-19"),
                        LocalDateTime.of(2024, 10, 10, 16, 30)
                    )
                ))
            )
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Patient samplePatient : getSamplePatients()) {
            sampleAb.addPatient(samplePatient);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<MedicalHistory> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(MedicalHistory::new)
                .collect(Collectors.toSet());
    }

}
