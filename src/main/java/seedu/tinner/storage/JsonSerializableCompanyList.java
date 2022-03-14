package seedu.tinner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.tinner.commons.exceptions.IllegalValueException;
import seedu.tinner.model.CompanyList;
import seedu.tinner.model.ReadOnlyCompanyList;
import seedu.tinner.model.company.Company;

/**
 * An Immutable CompanyList that is serializable to JSON format.
 */
@JsonRootName(value = "companylist")
class JsonSerializableCompanyList {

    public static final String MESSAGE_DUPLICATE_COMPANY = "Company list contains duplicate company(s).";

    private final List<JsonAdaptedCompany> companies = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableCompanyList} with the given companies.
     */
    @JsonCreator
    public JsonSerializableCompanyList(@JsonProperty("companies") List<JsonAdaptedCompany> companies) {
        this.companies.addAll(companies);
    }

    /**
     * Converts a given {@code ReadOnlyCompanyList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableCompanyList}.
     */
    public JsonSerializableCompanyList(ReadOnlyCompanyList source) {
        companies.addAll(source.getCompanyList().stream().map(JsonAdaptedCompany::new).collect(Collectors.toList()));
    }

    /**
     * Converts this company list into the model's {@code CompanyList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public CompanyList toModelType() throws IllegalValueException {
        CompanyList companyList = new CompanyList();
        for (JsonAdaptedCompany jsonAdaptedCompany : companies) {
            Company company = jsonAdaptedCompany.toModelType();
            if (companyList.hasCompany(company)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_COMPANY);
            }
            companyList.addCompany(company);
        }
        return companyList;
    }

}
