---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------
## **Design**

### Architecture

**`Main`** has two classes called [`Main`](https://github.com/AY2021S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/momentum/Main.java) and [`MainApp`](https://github.com/AY2021S1-CS2103T-T10-1/tp/blob/master/src/main/java/seedu/momentum/MainApp.java). It is responsible for,

* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its _API_ in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1` when in project view.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI Component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/AY2122S2-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/tinner/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `CompanyListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `CommandBox` and `ResultDisplay` appear at the top of the application for the user
to interact with the application.

The `CompanyListPanel` appears at the center of the application displaying key information of `Company`
from `CompanyCard`.

The `RoleListPanel` appears at the bottom of `CompanyCard` displaying key information of `Role` from `RoleCard`.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the `MainWindow` is specified in `MainWindow.fxml`

The `UI` component,
* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the `UI` can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Company` and `Role` objects residing in the `Model`.

### Logic Component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** : 
[`Logic.java`](https://github.com/AY2122S2-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/tinner/logic/Logic.java)


### Model Component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : 
[`Model.java`](https://github.com/AY2122S2-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/tinner/model/Model.java)



### Storage Component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : 
[`Storage.java`](https://github.com/AY2122S2-CS2103T-T17-1/tp/blob/master/src/main/java/seedu/tinner/storage/Storage.java)

The `Storage` component,

* can save `UserPref` objects in json format and read it back.
* can save `JsonAdaptedCompany` objects in `JsonSerializableCompanyList` in json format and read it back.
* can save `JsonAdaptedRole` objects in `JsonAdaptedCompany` in json format and read it back
* inherits from both `CompanyListStorage` and `UserPrefStorage`,meaning that it can be 
  treated as either one (if the functionality of only one is required)
* depends on classes like `Company` and `Role` in the `Model` component (as it is the `Storage` component's
  job to save/retrieve objects that belong to the `Model`)

The `JsonAdaptedCompany` also contains a list of roles in `List<JsonAdaptedRole>` format, as show in the class diagram above.

### Common Classes

---
## **Implementation**

### Edit role feature
The `editRole` command for the `Role` item allows the user to update any fields by specifying
the company index, role index and prefixes of the fields to be updated.

#### Implementation
The `editRole` command is primarily implemented by `EditRoleCommandParser` a class that extends `Parser`, and `EditRoleCommand` is a class that extends `Command`. The `EditRoleCommand` has an inner class `EditRoleDescriptor` that holds the changes to the `Role`.

* Upon a valid user's input using the `editRole` command, the `EditRoleCommandParser#parse()`
  creates an `EditRoleDescriptor` with the edited changes to the `Role` fields.
* The `EditRoleCommandParser#parse()` then use the `EditRoleDescriptor` object to instantiate the `EditRoleCommand`.
* Then invoking the `EditRoleCommand#execute()` method will update the `Role` with the new changes.


![UML diagram of the EditRole feature](images/EditRoleDiagram.png)

The following sequence diagram shows how the `editRole` command operation works with the user input `editRole 1 1 d/react js`:

![Sequence diagram of the EditRole feature](images/EditRoleSequenceDiagram.png)

1. The user will first enter the input `editRole 1 1 d/react js`, the `CompayListParser#parseCommand()` method will parse the information `1 1 d/react js` to `EditRoleCommandParser` using the method `parse()` based on the keyword `editRole`.
2. The `EditRoleCommandParser#parse()` method will create an `EditRoleDescriptor` object with all the non-empty fields with the fields' prefixes that are specified by the user such as `s/`,  `b/`, `d/`, etc. In this example, `EditRoleDescriptor#setDescription()` will be used as the description `d/` is a non-empty field with `react js`.
3. Then the `EditRoleCommandParser#parse()` method will create an `EditRoleCommand` object with the company index `1`, role index `1` and the `EditRoleDescriptor` object.
4. The `EditRoleCommand` object will be returned to the `LogicManager` and will then invoke the `EditRoleCommand#execute()` method to implement the changes.
5. The  `EditRoleCommand#execute()` will check the validity of both the indexes, and invoke the `Model#setRole` method.
6. The  `Model#setRole()` with the company index will set the role to be edited with a new role containing the changes. Then the `Model#updateFilteredRoleList()` filters the list of roles such that the application only displays the edited `Role` to the User.
7. Upon successful operation, a new `CommandResult` object is returned to the `LogicManager`.


## Requirements

### Product scope

**Target user profile**:

Students who...

* want to keep track of tech internships
* are disorganised and tend to miss deadlines
* prefer desktop apps over other types
* can type fast
* prefer typing to mouse interactions
* are reasonably comfortable using CLI apps

**Value proposition**: 

* Track (View all your internship applications at a glance)
* Remind (Be reminded of your upcoming assessments)
* Review (Comment on each stage of the application process for future self-improvement or review the company’s hiring process)

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​        | I want to …​                                                               | So that I can…​                                          |
|----------|----------------|----------------------------------------------------------------------------|----------------------------------------------------------|
| `* * *`  | user           | add a new company                                                          | add new internship roles to said company                 |
| `* * *`  | user           | add a new internship role                                                  | keep track of said internship application process        |
| `* * *`  | user           | delete a company                                                           | remove companies that I am no longer interested in       |
| `* * *`  | user           | delete an internship role                                                  | remove internship roles that are outdated or complete    |
| `* * *`  | user           | see a summary of my internship applications at a glance                    | have a general overview of the status of my applications |
| `* *`    | user           | search keywords like company name, internship roles, etc.                  | locate my internship application quickly                 |
| `* *`    | user           | sort company names in alphabetical order                                   | locate a company that I may have forgotten about         |
| `* *`    | user           | sort applications in chronological order of deadlines                      | keep track of the timeline of applications               |
| `* *`    | user           | modify each item                                                           | keep Tinner and all its contents up to date              |
| `* *`    | user           | be reminded of upcoming deadlines                                          | be on time with my applications                          |
| `* *`    | organised user | tag applications and events                                                | keep items compartmentalised and thus easier to access   |
| `* *`    | long term user | archive/hide irrelevant events                                             | not get distracted by what is not important              |
| `* *`    | new user       | learn how to use the application via a guide                               | use the application periodically with ease               |
| `*`      | user           | export calendar dates of important events into a .ics or .pdf file         | keep track of my events on an external platform          |
| `*`      | user           | export a list of interview reviews of different companies into a .csv file | share my experience with juniors and peers               |
| `*`      | user           | mark certain entries as my favourites                                      | view those that I am more interested in at a glance      |

*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `Tinner` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a company**

Guarantees: a company will be successfully created

**MSS**

1. User requests to add a specific company and its information in the list
2. Tinner adds a company to the list
3. Tinner displays the list of companies

   Use case ends

**Extensions**

 1a. The input does not adhere to the command format <br/>
  1a1. Tinner shows an invalid input format error message <br/>
     Use case resumes at step 1

 1b. The specific company is already stored <br/>
  1b1. Tinner shows a duplicate company error message <br/>
     Use case resumes at step 1

**Use case: UC02 - Add an internship role**

Precondition: the company to which the internship role will belong has already been created

Guarantees: an internship role will be successfully created

**MSS**

1. User requests to view a list of companies
2. User requests to add an internship role and provides the relevant details
3. Tinner adds the internship role to the list of roles of the specific company

   Use case ends

**Extensions**

 1a. The input does not adhere to the command format <br/>
  1a1. Tinner shows an invalid input format error message <br/>
     Use case resumes at step 1

**Use case: UC03 - Delete a company**

Precondition: there exists at least one company in the list of companies

Guarantees: a company is successfully removed from the list of companies

**MSS**

1. User requests to view a list of companies
2. Tinner shows a list of companies and the associated internship roles
3. User requests to delete a specific company in the list
4. Tinner deletes the company

   Use case ends

**Extensions**

 3a. The input does not adhere to the command format <br/>
  3a1. Tinner shows an invalid input format error message <br/>
     Use case resumes at step 2

 3b. The input company index is invalid <br/>
  3b1. Tinner shows a company index out of bounds error message <br/>
     Use case resumes at step 2

**Use case: UC04 - Delete an internship role**

Precondition: there exists at least one internship role associated with a company in the list of companies

Guarantees: a specified internship role is successfully removed from the associated company

**MSS**

1. User requests to view a list of companies
2. Tinner shows a list of companies and the associated internship roles
3. User requests to delete a specific internship role of a company in the list
4. Tinner deletes the internship role

   Use case ends

**Extensions**

 3a. The input does not adhere to the command format <br/>
  3a1. Tinner shows an invalid input format error message <br/>
     Use case resumes at step 2

 3b. The input company index is invalid <br/>
  3b1. Tinner shows a company index out of bounds error message <br/>
     Use case resumes at step 2

 3c. The input internship role index is invalid <br/>
  3c1. Tinner shows an internship role index out of bounds error message <br/>
     Use case resumes at step 2

**Use case: UC05 - List all companies**

Precondition: there exist at least one company stored in Tinner

Guarantees: every company stored in Tinner will be shown

**MSS**

1. User requests to view a list of companies
2. Tinner displays all companies in its storage

   Use case ends

*{More to be added}*

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed
2. Should be able to hold up to 1000 items (companies and internship roles) without a noticeable sluggishness in performance for typical usage
3. Should require no installation
4. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse
5. Should be responsive and have a latency of less than 3 seconds

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, macOS
* **CLI**: Command Line Interface
* **MSS**: Main Success Scenario
