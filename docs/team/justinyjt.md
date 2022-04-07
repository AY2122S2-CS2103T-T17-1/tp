---
layout: page
title: Justin Yip's Project Portfolio Page
---

### Project: Tinner

Tinner (Anagram of Intern) is a desktop app for managing internship applications, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Tinner allows you to easily sort through and retrieve the relevant information faster than traditional GUI apps.

Given below are my contributions to the project.

* **Code contributed**:
    * [RepoSense link](https://nus-cs2103-ay2122s2.github.io/tp-dashboard/?search=justinyjt&brekdown=true)

* **New Feature**: Added Storage for the `Role` class and its fields. (Pull request [\64](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/64))
  * What it does: Allows the application to store and load user created roles within their associated companies automatically.
  * Justification: This feature is necessary to keep track of user given data on their internships and allow proper storing and loading of files.
  * Highlights: This change required major changes to components in the `Storage` package with the addition of new classes like `JsonAdaptedRole`.

* **New Feature**: Added the `Reminder` feature logic for the application. (Pull request [\#167](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/167))
  * What it does: Creates a list of reminders based on upcoming reminder dates of the roles in Tinner's company list.
  * Justification: This feature allows users to be reminded of significant dates related to their internship and is one of the main value propositions of the application.
  * Highlights: This feature required major changes to multiple components of the code such as the `MainApp` and `Model`.

* **New Feature**: Added the `setWindow` command to set the reminder window. (Pull request [\#175](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/175))
  * What it does: Allows the user to customize the reminder window to any number of days between 1 and 30.
  * Justification: This feature gives the user more choice when it comes to getting reminders on their internship applications.
  * Highlight: This feature is unique as updates the `UserPrefs` file within the `Storage`

* **Project management**:
    * Recorded a video demo of the application in v1.2
    * Collected photos from the team and created the AboutUs page

* **Enhancements to existing features**:
    * Refactored class and variable names in Address Book 3 to suit the context of Tinner (Pull request [\#51](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/51))
    * Refactored the edit command to editCompany and phased out the tag command (Pull request: [\121](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/121))

* **Documentation**:
  * User Guide:
      * Added documentation for the `setWindow` feature (Pull request [\#183](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/183))
      * Added documentation for Demo Use Cases (Pull request [\#194](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/194))
  * Developer Guide:
    * Added documentation for the `Reminder` class (Pull request [\#269](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/269))
    * Updated documentation for the `Model` component to include the `Reminder` class (Pull request [\#269](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/269))
    * Updated documentation for the `Storage` component to match Tinner (Pull request [\#147](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/147))
    * Added the skeleton format for the Architecture section (Pull request [\#269](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/269))

* **Community**:
    * PRs reviewed (with non-trivial review comments:) (Pull requests: [\#54](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/54), [\#92](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/92), [\#200](https://github.com/AY2122S2-CS2103T-T17-1/tp/pull/200))
    * Reported bugs and suggestions for other teams in the class (examples: [1](https://github.com/justinyjt/ped/issues/7), [2](https://github.com/justinyjt/ped/issues/6), [3](https://github.com/justinyjt/ped/issues/2)
