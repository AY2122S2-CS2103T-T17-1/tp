---
layout: page
title: User Guide
---

Tinner (Anagram of Intern) is a desktop app for managing internship applications, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, Tinner allows you to easily sort through and retrieve relevant information faster than traditional GUI apps.

## Table of Contents

* [Quick start](#quick-start)
* [Features](#features)
* [Viewing help: `help`](#c-help)
* [Viewing all companies and roles: `list`](#c-list)
* [Viewing all favourited companies: `listFavourite`](#c-listfavourite)
* [Adding a company: `addCompany`](#c-add-c)
* [Adding a role: `addRole`](#c-add-c-r)
* [Deleting a company: `deleteCompany`](#c-delete-c)
* [Deleting a role: `deleteRole`](#c-delete-c-r)
* [Editing a company: `editCompany`](#c-edit-c)
* [Editing a role: `editRole`](#c-edit-r)
* [Finding a specific company or role: `find`](#c-find-c-r)
* [Favouriting a specific company: `favourite`](#c-favourite-c)  
* [Unfavouriting a specific company: `unfavourite`](#c-unfavourite-c)
* [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start <a id="quick-start"></a>

1. Ensure you have Java 11 or above installed in your computer.
2. Download the latest tinner.jar.
3. Copy the file to the folder you want to use as the home folder for your Tinner.
4. Double-click the file to start the app. The GUI like below should appear in a few seconds. Note how the app contains some sample data.

![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing help and pressing Enter will open the help window.

   Some example commands you can try:
   * **`list`** : Lists all companies and internship roles.
   * **`addCompany`**`n/Google p/98765432 e/hr_google@gmail.com a/70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371` :
    Adds a company named `Google` to Tinner.
   * **`addRole`**`1 n/Software Engineering Intern s/applying b/31-03-2022 23:59 d/Write mobile applications $/5000` :
    Adds a software engineering intern role to the company at index 1.
   * **`deleteCompany`**`1` : Deletes the 1st company shown in the current list.
   * **`deleteRole`**`1 1` : Deletes the 1st role of the 1st company shown in the current list.
   * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features <a id="features"></a>

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/COMPANY`, `COMPANY` is a parameter which can be used as `add n/Google`.

* Items in square brackets are optional.<br>
  e.g `n/COMPANY [p/PHONE_NUMBER]` can be used as `n/Google P/65427981` or as `n/Google`.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/COMPANY [p/PHONE_NUMBER]`, `[p/PHONE_NUMBER] n/COMPANY` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaining how to access the User Guide.

![help message](images/helpMessage.png)

Format: `help`

### Listing all companies : `list` <a id="c-list"></a>

Shows a list of all companies and internship roles in Tinner.

Format: `list`

### Listing all favourited companies : `listFavourite` <a id="c-listfavourite"></a>

Shows a list of all favourited companies and internship roles within these companies in Tinner.

Format: `listFavourite`

### Adding a company: `addCompany` <a id="c-add-c"></a>

Adds a company that does not exist in the list.

Format: `addCompany n/COMPANY_NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`

Examples:

* `addCompany n/Google p/98765432 e/hr_google@gmail.com a/70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371 `
* `addCompany n/Meta p/91234567 e/hr_meta@meta.com a/9 Straits View, Marina One, Singapore 018937`

### Adding an internship role to an existing company: `addRole` <a id="c-add-c-r"></a>

Adds an internship role to a company that already exists in the list.

Format: `addRole COMPANY_INDEX n/ROLE_NAME [(TYPE)] s/STATUS b/DEADLINE [d/DESCRIPTION] [$/STIPEND]`
* Add internship role at the specified `COMPANY_INDEX`.
* The `COMPANY_INDEX` must be a positive integer like 1, 2, 3, ...
* The `ROLE_NAME` should only contain alphanumeric characters, spaces and an optional pair of round brackets.
* The `STATUS` accepted are as follows: applying, pending, interview and assessments, offered, rejected, complete.
* The `DEADLINE` should be in format dd-MM-yyyy HH:mm
* The `DESCRIPTION` and `STIPEND` fields are optional during the initial role creation
    * The `DESCRIPTION` can contain alphanumeric characters, spaces and special characters.
    * The `STIPEND` must be a positive integer going up to 10 digits long.

Examples:

* `addRole 1 n/Data Analyst s/applying b/31-03-2022 23:59 d/Analyse data $/4800 `
* `addRole 3 n/Software Engineer (Front end) s/applying b/30-04-2022 01:20 d/web deveploment with react js $/2400 `

### Deleting a Company : `deleteCompany` <a id="c-delete-c"></a>

Deletes the specified company from the list.

Format: `deleteCompany COMPANY_INDEX`

* Deletes the company at the specified `COMPANY_INDEX`.
* The index refers to the index number shown in the displayed company list.
* The index must be a positive integer like 1, 2, 3, …

Examples:

* `list` followed by `deleteCompany 2` deletes the 2<sup>nd</sup> company in the displayed company list.

### Deleting an internship role from company : `deleteRole` <a id="c-delete-c-r"></a>

Deletes the specified role in the specified company.

Format: `deleteRole COMPANY_INDEX ROLE_INDEX`

* Deletes the role at the specified `ROLE_INDEX` of the company at the specified `COMPANY_INDEX`. The indexes refer to the index numbers shown in the displayed company list.
* The indexes must be a positive integer like 1, 2, 3, …

Examples:

* `list` followed by, `deleteRole 1 1` deletes the 1<sup>st</sup> role from the 1<sup>st</sup>
  company in Tinner.

### Edits an existing company in the company list : `editCompany` <a id="c-edit-c"></a>
Format: `editCompany COMPANY_INDEX [n/COMPANY_NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]`

* Edits the company at the specified INDEX. The index refers to the index number shown in the displayed company list. The index must be a positive integer 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:

* editCompany 1 p/91234567 e/johndoe@example.com

### Edits an existing role from company : `editRole` <a id="c-edit-r"></a>
Format: `editRole COMPANY_INDEX ROLE_INDEX [n/ROLE_NAME [(TYPE)]] [s/STATUS] [b/DEADLINE] [d/DESCRIPTION] [$/STIPEND]`

* Edits the role at the specified `ROLE_INDEX` of the company at the specified `COMPANY_INDEX`. The indexes refers to the index number shown in the displayed company list. The indexes must be a positive integer 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:

* `editRole 1 1 [s/offered] [$/3000]`
  
### Finding companies or internship roles from companies by name: `find` <a id="c-find-c-r"></a>

Find companies or/and roles depending on the format given.
If only company keywords are entered, companies whose names match any of the given keywords will be displayed with all their roles.
If only role keywords are entered, roles across all companies whose role names match any of the given keywords will be displayed.
If both keywords are entered, only companies with names that match any of the company keywords while containing roles whose names match any of the role keywords are displayed.

Format: `find c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS] r/ROLE_KEYWORD [MORE_ROLE_KEYWORDS]`

* The search is case-insensitive. e.g. `meta` will match `Meta`.
* The order of the keywords does not matter. e.g. `software engineer` will match `engineer software`.
* Only the company name, and the role name are searched.
* Only full words would be matched e.g. `mobile` will not match `mobiles`
* Companies and roles matching at least one keyword will be returned e.g. `software engineer` will match `mobile engineer` and `software developer`
* At least one role keyword or one company keyword must be provided in the user input. 
* The prefixes `c/` and `r/` can be omitted or included if no corresponding keywords are meant to be entered  

Examples:

* `find c/meta amazon r/engineer` 

### Favouriting a specific company: `favourite` <a id="c-favourite-c"></a>

Favourite a specific company from the list of companies

Format: `favourite COMPANY_INDEX`

* Favourites the company at the specified `COMPANY_INDEX`.
* The index refer to the index number shown in the displayed company list.
* The indexes must be a positive integer like 1, 2, 3, …

Examples:

* `list` followed by, `favourite 1` favourites the 1<sup>st</sup>
  company in Tinner.

### Unfavouriting a specific company: `unfavourite` <a id="c-unfavourite-c"></a>

Unfavourite a specific company from the list of companies

Format: `unfavourite COMPANY_INDEX`

* Unfavourites the company at the specified `COMPANY_INDEX`.
* The index refer to the index number shown in the displayed company list.
* The indexes must be a positive integer like 1, 2, 3, …

Examples:

* `list` followed by, `unfavourite 1` unfavourites the 1<sup>st</sup>
  company in Tinner.
  
### Exiting the program : `exit` <a id="c-exit"></a>

Exits the program.

Format: `exit`

### Saving the data

Tinner data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## Command summary <a id="command-summary"></a>

Action | Format, Examples
--------|------------------
**List companies** | `list`
**List favourited companies** | `listFavourite`
**Add company** | `addCompany n/COMPANY_NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` <br><br> e.g.,`addCompany n/Google p/98765432 e/hr_google@gmail.com a/70 Pasir Panjang Rd, #03-71 Mapletree Business City II, Singapore 117371`
**Add role** | `addRole COMPANY_INDEX n/ROLE_NAME [(TYPE)] s/STATUS b/DEADLINE [d/DESCRIPTION] [$/STIPEND]` <br><br> e.g.,` addRole 1 n/Data Analyst s/applying b/31-03-2022 23:59 d/Analyse marketing data $/5000`
**Delete company** | `deleteCompany COMPANY_INDEX `<br><br> e.g.,`deleteCompany 3 `
**Delete role** | `deleteRole COMPANY_INDEX ROLE_INDEX` <br><br> e.g.,`deleteRole 3 1 `
**Edit company** | `editCompany COMPANY_INDEX [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS]` <br><br> e.g.,`editCompany 1 n/Google p/98765432 e/hr_google@gmail.com`
**Edit role** | `editRole COMPANY_INDEX ROLE_INDEX [n/ROLE_NAME [(TYPE)]] [s/STATUS] [b/DEADLINE] [d/DESCRIPTION] [$/STIPEND]` <br><br> e.g.,` editRole 1 1 s/pending b/31-03-2022 23:59 $/5000`
**Find company or role** | `find c/COMPANY_KEYWORD [MORE_COMPANY_KEYWORDS] r/ROLE_KEYWORD [MORE_ROLE_KEYWORDS]` <br><br> e.g., `find c/google r/mobile software`
**Favourite company** | `favourite COMPANY_INDEX`
**Unfavourite company** | `unfavourite COMPANY_INDEX`
**Help** | `help`
**Exit Tinner** | `exit`
