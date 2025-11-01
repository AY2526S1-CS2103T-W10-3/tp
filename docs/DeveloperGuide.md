---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* Based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org).

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PatientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Patient` and `Appointment` objects residing in the 
`Model`.

`MainWindow` also handles switching between views:
* `PatientListPanel` is shown when the user is browsing patients
* `AppointmentListPanel` is shown when the user requests to view appointments

Additionally, the `HelpWindow` is displayed when the user issues the `help` command or presses **F1**. 


The `StatusBarFooter` at the bottom of the UI updates according to what the user is currently viewing
(either overall patient data or a specific patient’s appointment list).

### Logic component

**API** : [`Logic.java`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `DoctorBaseParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to delete a patient).  
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `DoctorBaseParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `DoctorBaseParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the DoctorBase data i.e., all `Patient` objects (which are contained in a `UniquePatientList` object).
* stores the currently 'selected' `Patient` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Patient>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `MedicalHistory` list in `DoctorBase`, which `Patient` references. This allows `DoctorBase` to only require one `MedicalHistory` object per unique medical history, instead of each `Patient` needing their own `MedicalHistory` objects.  

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/AY2526S1-CS2103T-W10-3/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both DoctorBase data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `DoctorBaseStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Adding appointment feature

The following activity diagram summarizes what happens when the `AddAppointmentParser` attempts to parse the user's input:  
![AddAppointmentParserActivityDiagram](images/AddAppointmentParserActivityDiagram.png)

The following activity diagram summarizes what happens when a user executes the `AddAppointmentCommand`:
![AddAppointmentActivityDiagram](images/AddAppointmentActivityDiagram.png)

The following sequence diagram shows how an add-appt command interacts within the Logic component.
Using `add-appt 1 at/DentalCheckup ad/10-10-2010, 0900` as an example.
![AddAppointmentSequenceDiagram](images/AddAppointmentSequenceDiagram.png)

### Deleting appointment feature
The following activity diagram summarizes what happens when the `DeleteAppointmentParser` attempts to parse the user's input:  
![DeleteAppointmentParserActivityDiagram](images/DeleteAppointmentParserActivityDiagram.png)

The following activity diagram summarizes what happens when a user executes the `DeleteAppointmentCommand`:
![DeleteAppointmentActivityDiagram](images/DeleteAppointmentActivityDiagram.png)

The following sequence diagram shows how a delete-appt command interacts within the Logic component.
Using `delete-appt 1` as an example. 
![DeleteAppointmentSequenceDiagram](images/DeleteAppointmentSequenceDiagram.png)

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**: 
- Solo doctor who manages administrative tasks on their own.
- prefer desktop apps over other types 
- can type fast 
- prefers typing to mouse interactions 
- is reasonably comfortable using CLI apps

**Value proposition**: 
- Simplifies administrative workload
- Can manage patient records and appointments faster than a typical mouse/GUI driven app
- Reduces forgotten or missed appointments


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​     | I want to …​                        | So that I can…​                                                         |
|----------|-------------|-------------------------------------|-------------------------------------------------------------------------|
| `* * *`  | new user    | see usage instructions              | refer to instructions when I forget how to use the app                  |
| `* * *`  | doctor      | add patient details                 | keep track of my patients                                               |
| `* * *`  | doctor      | delete patient details              | remove information on patients I no longer need                         |
| `* * *`  | doctor      | find a patient by name              | locate details of patients without having to go through the entire list |
| `* * *`  | doctor      | list patient details                | view all of my patients' information                                    |
| `* * *`  | doctor      | see my patients' appointments       | prepare for upcoming appointments                                       |
| `* * *`  | doctor      | add a patient's appointment         | register a new appointment                                              |
| `* * *`  | doctor      | delete a patient's appointment      | handle cancellation of appointments                                     |
| `* * *`  | doctor      | edit patients' contacts             | edit patients' information                                              |
| `* * *`  | doctor      | see all stored appointments         | easily see upcoming appointments                                        |
| `* * *`  | doctor      | edit upcoming appointments          | reschedule appointments if needed                                       |
| `* *`    | doctor      | search appointments by patient name | quickly locate a specific consultation                                  |
| `* *`    | doctor      | add detailed notes for each patient | remember diagnoses, observations and treatments                         |
| `*`      | doctor      | attach documents/links              | easily reference patient documents                                      |
| `*`      | busy doctor | autocomplete commands               | use the program more easily without technological know-how              |
| `*`      | busy doctor | use alias commands                  | quickly add patient and appointment information via shortcuts           |



### Use cases

(For all use cases below, the **System** is the `DoctorBase` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add patient**

**MSS**

1. User chooses to add patient with relevant details 
2. DoctorBase adds patient into patient list  
   Use case ends.

**Extensions**

* 1a. DoctorBase detects an invalid parameter in the entered details
  * 1a1. DoctorBase rejects command and does not add patient into patient list  
    Use case ends.

* 1b. DoctorBase detects a duplicate patient entry
  * 1b1. DoctorBase rejects command and does not add patient into patient list  
    Use case ends.

<br>

**Use case: UC2 - List current patients**

**MSS**

1. User chooses to view a list of current patients 
2. DoctorBase shows list of patients to user  
   Use case ends.


**Extensions**

* 2a. DoctorBase detects no entries in list  
  Use case ends.

<br>

**Use case: UC3 - Delete patient**

**MSS**

1. User chooses to delete a patient from DoctorBase 
2. DoctorBase deletes the patient entry from storage  
   Use case ends.


**Extensions**

* 2a. DoctorBase does not find an entry of the selected patient
  * 2a1. DoctorBase rejects command and does nothing  
  Use case ends.

<br>

**Use case: UC4 - Add appointment**

**MSS**

1. User chooses to add an appointment to DoctorBase 
2. DoctorBase adds the appointment into appointment storage  
   Use case ends.



**Extensions**

* 1a. DoctorBase detects that the given INDEX does not correspond to any patient in the current list
  * 1a1. DoctorBase rejects command and does nothing  
  Use case ends.


* 1b. DoctorBase detects duplicate appointment 
	* 1b1. DoctorBase rejects command and does nothing  
           Use case ends.


* 1c. DoctorBase detects invalid parameter in appointment details
	* 1c1. DoctorBase rejects command and does nothing  
	Use case ends.

<br>


**Use case: UC5 - List patient's appointments**

**MSS**

1. User chooses to view a list of existing appointments for a specific patient
2. DoctorBase shows the list of existing appointments to User  
   Use case ends.

**Extensions**

* 1a. DoctorBase detects that the given INDEX does not correspond to any patient in the current list
    * 1a1. DoctorBase rejects command and does nothing  
      Use case ends.
  
* 2a. DoctorBase detects no entries in list  
  Use case ends.

<br>

**Use case: UC6 - List all upcoming appointments**

**MSS**

1. User chooses to view the list of all upcoming appointments
2. DoctorBase shows the list of existing appointments to User  
   Use case ends.

**Extensions**

* 2a. DoctorBase detects no entries in list  
  Use case ends.

<br>

**Use case: UC7 - Delete appointment**

**MSS**

1. User chooses to delete an appointment entry from the list of existing appointments 
2. DoctorBase deletes the appointment entry from the list of existing appointments  
   Use case ends.


**Extensions**

* 2a. DoctorBase does not find the indicated appointment entry
  * 2a1. DoctorBase rejects command and does nothing  
  Use case ends.

<br>

**Use case: UC8 - Find patient by name**

**MSS**

1. User chooses to find patients by name in DoctorBase  
2. DoctorBase lists all patients whose names contain any of the given keywords (case-insensitive, full-word match)  
   Use case ends.

**Extensions**

* 1a. No keyword is entered  
  * 1a1. DoctorBase rejects the command and does nothing  
  Use case ends.

* 2a. No patient names match the given keywords  
  * 2a1. DoctorBase displays an empty list.
  Use case ends.

<br>

**Use case: UC9 - Edit patient**

**MSS**

1. User chooses to edit a patient’s details.  
2. DoctorBase updates the patient’s record with the new details.  
   Use case ends.

**Extensions**

* 2a. DoctorBase detects an invalid patient index  
  * 2a1. DoctorBase rejects command and does nothing  
  Use case ends.

* 2b. User provides no fields to edit  
  * 2b1. DoctorBase rejects command and does nothing  
  Use case ends.

* 2c. DoctorBase detects a duplicate patient after editing  
  * 2c1. DoctorBase rejects command and does nothing  
  Use case ends.

<br>

**Use case: UC10 - Edit appointment**

**MSS**

1. User chooses to edit a patient’s appointment.  
2. DoctorBase updates the appointment with the new details.  
   Use case ends.

**Extensions**

* 1a. User is not currently viewing a patient’s appointment list  
  * 1a1. DoctorBase rejects command and does nothing  
  Use case ends.

* 2a. DoctorBase detects an invalid appointment index  
  * 2a1. DoctorBase rejects command and does nothing  
  Use case ends.

* 2b. User provides no fields to edit  
  * 2b1. DoctorBase rejects command and does nothing  
  Use case ends.

* 2c. DoctorBase detects a clashing appointment time  
  * 2c1. DoctorBase rejects command and does nothing  
  Use case ends.


### Non-Functional Requirements

* The system should respond within 2 seconds (performance)
* The system should only be used by 1 user (scalability)
* The system should function on Windows 11, Linux, and MacOS (usability)
* The system should not store more than 500 patients 
* The system should not store more than 5000 appointments



### Glossary

* **Solo doctor**: A doctor that takes care of patients under them by themselves with no other assistance
* **Patient**: A person that receives care and has appointments with the solo doctor
* **Appointment**: A scheduled medical meeting between the patient and the solo doctor
* **JSON (JavaScript Object Notation)**: The file format the application uses to store patient and appointment details

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample patients. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Finding a patient

1. Finding a patient while all patients are being shown.

   1. Prerequisites: None.

   1. Test case: `find alex`  
      Expected: Shows patients that include `alex` in their `NAME`.

### Deleting a patient

1. Deleting a patient while all patients are being shown.

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`  
      Expected: First patient is deleted from the list. Details of the deleted patient shown in the status message.

   1. Test case: `delete 0`  
      Expected: No patient is deleted. Error details shown in the status message.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)  
      Expected: Similar to previous.

### Adding a patient

1. Adding a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command.

   1. Test case: `add n/John Doe i/S8052802G g/m p/98765432 e/johnd@example.com d/12-12-2002 a/John street, block 123, #01-01`  
      Expected: New patient added. Details of the added patient shown in the status message.

   1. Test case (missing `GENDER`): `add n/John Doe i/S8052802G p/98765432 e/johnd@example.com d/12-12-2002 a/John street, block 123, #01-01`  
      Expected: No patient added. Error details shown in status message.
      
   1. Test case (invalid `NRIC`): `add n/John Doe i/A8052802G g/m p/98765432 e/johnd@example.com d/12-12-2002 a/John street, block 123, #01-01`  
      Excepted: No patient added. Error details shown in status message.

### Editing a patient

1. Editing a patient while all patients are being shown.

   1. Prerequisites: List all patients using the `list` command. At least 1 patient in the list.

   1. Test case: `edit 1 p/91234567 e/johndoe@example.com`  
      Expected: First patient edited. Details of edited patient shown in the status message

   1. Test case: `edit 1`  
      Expected: No patient edited. Error details shown in status message.

   1. Test case (duplicate `NRIC`): `edit 1 i/S7101271I`  
      Expected: No patient added. Error details shown in status message.

### Listing all patients

1. Listing all patients from appointment list

   1. Prerequisite: List all appointments of the 1st patient using the `list-appt 1` command. At least 1 patient in list

   1. Test case: `list`  
      Expected: Listed all patients

   1. Test case: `list aewawsd`
      Expected: Listed all patients

### Adding an appointment

1. Adding an appointment to the first patient's appointment list.

   1. Prerequisite: List all patients using the `list` command. At least 1 patient in list. There is no existing appointment with the same appointment timing.

   1. Test case: `add-appt 2 at/flu jab ad/27-02-2025, 0900`  
      Expected: New appointment added. Details of the edited patient shown in the status message.

   1. Test case: `add-appt 2 at/flu jab ad/30-02-2025, 0900`  
      Expected: No appointment added. Error details shown in status message.

### Editing an appointment

1. Editing an appointment of the first patient's appointment list.

   1. Prerequisite: List all appointments of first patient using the `list-appt 1` command. At least 1 patient in list. There is no existing appointment with the same appointment timing. At least 1 appointment in appointment list.

   1. Test case: `edit-appt 1 at/dental ad/02-02-2002, 0900`  
      Expected: First appointment of first patient edited. Details of edited patient shown in the status message

   1. Test case: `edit-appt 1`  
      Expected: No appointment edited. Error details shown in status message.

### Deleting an appointment

1. Deleting an appointment of the first patient's appointment list.

   1. Prerequisite: List all appointments of first patient using the `list-appt 1` command. At least 1 patient in list. At least 1 appointment in appointment list

   1. Test case: `delete-appt 1`  
      Expected: First appointment of first patient delete. Details of edited patient shown in the status message.

   1. Test case: `delete-appt 0`  
      Expected: No appointment deleted. Error details shown in status message.

### Listing all appointments

1. Listing all appointments of first patient.

   1. Prerequisite: List all patients using the `list` command. At least 1 patient in list.

   1. Test case: `list-appt 1`  
      Expected: First patient's appointment list shown. Listed all appointments for first patient.

   1. Test case: `list-appt 0`  
      Expected: No appointment list shown. Error details shown in status message.

### Listing all upcoming appointments

1. Listing all upcoming appointments

   1. Prerequisite: None.

   1. Test case: `list-appt-upcoming`  
      Expected: All upcoming appointments shown. Listed all upcoming appointments.

   1. Test case: `list-appt-upcoming 123123123`  
      Expected: All upcoming appointments shown. Listed all upcoming appointments.

### Saving data

1. Dealing with missing data files.

   1. From the initial sample data, run any valid command to initiate a save to the data file.

   2. Close the application.
   
   3. Delete the data file and re-launch the application.  
      Expected: DoctorBase will start with the sample data again.

2. Dealing with corrupted data files.
   
   1. From the initial sample data, run any valid command to initiate a save to the data file.
   
   2. Close the application.
   
   3. Modify `data/doctorBase.json` to corrupt it.  
   E.g. Modify `patients` to `patient`  
   Expected: DoctorBase will start with no data. The data file will only be updated when a save is initiated.

## **Appendix: Planned Enhancements**

### Team Size: 5

1. **Support for special characters in Patient Names.**  
   Currently, special characters (e.g. `/`, `ö`, `'`, etc.) are not accepted as valid inputs for Patient names.  
   The enhancement will expand input validation to support UTF-8 character sets, allowing more realistic patient names.

2. **Support for Patients without NRIC.**  
   Current implementation requires all patients to have a valid Singapore NRIC.  
   The enhancement will relax this constraint and accept alternative identifiers such as Passport numbers or Foreign 
   ICs.

3. **Improve error message handling when in wrong view.**  
   When in the wrong view, error messages indicating that the user should be in another view is only displayed if 
   the commands are typed with correct parameters.  
   The enhancement will display the Wrong View error message as long 
   as the command word is inputted and in the wrong view.

4. **Support Appointment Commands when in Upcoming Appointments view.**  
   Users cannot delete or edit appointments directly from the `list-appt-upcoming` view.  
   The enhancement will track the patient-appointment mapping internally, allowing: 
   ```
   list-appt-upcoming
   delete-appt 1
   edit-appt 2 ad/02-02-2026, 1500
   ```

5. **More flexible `find` Command using Regex/Partial.**  
   `find` currently matches only full words.  
   The enhancement will allow partial matches and regular expressions, such as:
    * `find han` matches `Hannah Ho`, `Hans Tan` and `Johan Chan`
    * `find ^han` matches only `Hannah Ho` and `Hans Tan`

6. **Find feature for appointments.**  
   In `list-appt` and `list-appt-upcoming` view, users cannot search for appointments by any of the field.  
   The enhancement would allow users to find specific appointments by name or by date.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**

Given below are how we expanded upon AB3 to fit our users (solo doctors)

### Fields of Patient
The Patient class (refactored from Person) had basic field of a typical person, however, it did not have some of the fields we would expect to be stored in a patient's info  
such as:
* Date of birth
* Gender
* Medical history

Hence, the missing `DateOfBirth` and `Gender` classes has to be made from scratch, with reference from the existing AB3 fields (Name, Email, etc.)  

While this was manageable, it required us to have an understanding of how the fields functioned and interacted with the rest of the system.  

Whereas for `MedicalHistory`, we were able to refactor the existing `Tag` field in order for it to more properly fit our desired project scope.  

### Appointment model
Appointments are things that every patient would have, and would also be something that would happen at a specified date and time, to achieve an event, such as an injection.  
However, having these inner fields would mean that the appointments would need to be implemented as a new model, with the date and time of the appointment, and the event happening at the appointment, as its fields.  

The Appointment model had to be built from scratch, including the fields:
* Title
* DateTime

The Appointment class also had to be integrated as a field of Patient, as to fit the project scope.

### Appointment commands
With a new model, there would be new commands tied to it as well  
Hence, `add-appt`, `edit-appt`, `delete-appt` and `list-appt` were implemented.  

To implement these command, we were able to reference the existing `add`, `edit`, `delete` and `list` commands.  
However, there were also some caveats such as the selection of the patient to be operated on, requiring changes in core underlying classes such as `LogicManager` and `ModelManager` to implement a `selectedPatient` field and methods such as `getSelectedPatient()`.  

### View model handling
With the new commands with realistic restrictions such as `edit-appt` requiring the UI to be viewing a patient's appointment list, checks based on what the UI was viewing had to be implemented.  

Hence, a `ViewMode` enum class had to built and implemented into `ModelManager` in order to track what the user is currently viewing.  
With this, we were able to properly restrict the usage of certain commands to their required `ViewMode`.  
