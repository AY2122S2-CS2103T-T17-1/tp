package seedu.tinner.testutil;

import seedu.tinner.model.CompanyList;
import seedu.tinner.model.company.Company;

/**
 * A utility class to help with building CompanyList objects.
 * Example usage: <br>
 *     {@code CompanyList ab = new CompanyListBuilder().withCompany("John", "Doe").build();}
 */
public class CompanyListBuilder {

    private CompanyList companyList;

    public CompanyListBuilder() {
        companyList = new CompanyList();
    }

    public CompanyListBuilder(CompanyList companyList) {
        this.companyList = companyList;
    }

    /**
     * Adds a new {@code Company} to the {@code CompanyList} that we are building.
     */
    public CompanyListBuilder withCompany(Company company) {
        companyList.addCompany(company);
        return this;
    }

    public CompanyList build() {
        return companyList;
    }
}
