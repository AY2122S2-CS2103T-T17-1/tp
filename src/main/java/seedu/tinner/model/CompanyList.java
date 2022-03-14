package seedu.tinner.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.tinner.model.company.Company;
import seedu.tinner.model.company.UniqueCompanyList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameCompany comparison)
 */
public class CompanyList implements ReadOnlyCompanyList {

    private final UniqueCompanyList companies;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */

    {
        companies = new UniqueCompanyList();
    }

    public CompanyList() {
    }

    /**
     * Creates an CompanyList using the Companies in the {@code toBeCopied}
     */
    public CompanyList(ReadOnlyCompanyList toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the company list with {@code companies}.
     * {@code companies} must not contain duplicate companies.
     */
    public void setCompanies(List<Company> companies) {
        this.companies.setCompanies(companies);
    }

    /**
     * Resets the existing data of this {@code CompanyList} with {@code newData}.
     */
    public void resetData(ReadOnlyCompanyList newData) {
        requireNonNull(newData);

        setCompanies(newData.getCompanyList());
    }

    //// company-level operations

    /**
     * Returns true if a company with the same identity as {@code company} exists in the address book.
     */
    public boolean hasCompany(Company company) {
        requireNonNull(company);
        return companies.contains(company);
    }

    /**
     * Adds a company to the address book.
     * The company must not already exist in the address book.
     */
    public void addCompany(Company p) {
        companies.add(p);
    }

    /**
     * Replaces the given company {@code target} in the list with {@code editedCompany}.
     * {@code target} must exist in the address book.
     * The company identity of {@code editedCompany} must not be the same as another existing company
     * in the address book.
     */
    public void setCompany(Company target, Company editedCompany) {
        requireNonNull(editedCompany);

        companies.setCompany(target, editedCompany);
    }

    /**
     * Removes {@code key} from this {@code CompanyList}.
     * {@code key} must exist in the address book.
     */
    public void removeCompany(Company key) {
        companies.remove(key);
    }

    //// util methods

    @Override
    public int size() {
        return getCompanyList().size();
    }

    @Override
    public String toString() {
        return size() + " companies";
        // TODO: refine later
    }

    @Override
    public ObservableList<Company> getCompanyList() {
        return companies.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CompanyList // instanceof handles nulls
                && companies.equals(((CompanyList) other).companies));
    }

    @Override
    public int hashCode() {
        return companies.hashCode();
    }
}
