package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * An UI component that displays information of a {@code Patient}.
 */
public class PatientCard extends UiPart<Region> {

    private static final String FXML = "PatientListCard.fxml";
    private static final Integer MAX_APPOINTMENTS_TO_DISPLAY = 3;
    private static final String NO_APPOINTMENTS_TO_DISPLAY = "No upcoming appointments";
    private static final String FOUND_APPOINTMENTS_TO_DISPLAY = "Upcoming Appointments:";
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Patient patient;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label gender;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label dob;
    @FXML
    private FlowPane tags;
    @FXML
    private Label appointments;

    /**
     * Creates a {@code PatientCode} with the given {@code Patient} and index to display.
     */
    public PatientCard(Patient patient, int displayedIndex) {
        super(FXML);
        this.patient = patient;
        id.setText(displayedIndex + ". ");
        name.setText(patient.getName().fullName + ", " + patient.getDob().getAge()
                + " [" + patient.getNric().value + "]");
        gender.setText(patient.getGender().gender.toString());
        phone.setText(patient.getPhone().value);
        address.setText(patient.getAddress().value);
        email.setText(patient.getEmail().value);
        dob.setText("DOB : " + patient.getDob().value);
        patient.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        appointments.setText(
                patient.getAppointments().stream()
                        .filter(a -> a.getDateTime().isAfter(LocalDateTime.now()))
                        .sorted(Comparator.comparing(Appointment::getDateTime))
                        .limit(MAX_APPOINTMENTS_TO_DISPLAY)
                        .map(Appointment::toString)
                        .reduce((a, b) -> a + "\n" + b)
                        .map(text -> FOUND_APPOINTMENTS_TO_DISPLAY + "\n" + text)
                        .orElse(NO_APPOINTMENTS_TO_DISPLAY)
        );
    }
}
