package seedu.address.ui;

import java.time.LocalDateTime;
import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";
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

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private Label appointments;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName + ", [" + person.getNric().value + "]"
                + ", [" + person.getDob().value + "]");
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        appointments.setText(
                person.getAppointments().stream()
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
