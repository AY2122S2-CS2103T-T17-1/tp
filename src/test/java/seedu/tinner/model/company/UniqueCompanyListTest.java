package seedu.tinner.model.company;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tinner.logic.commands.CommandTestUtil.VALID_ADDRESS_WHATSAPP;
import static seedu.tinner.testutil.Assert.assertThrows;
import static seedu.tinner.testutil.TypicalCompanies.META;
import static seedu.tinner.testutil.TypicalCompanies.WHATSAPP;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tinner.model.company.exceptions.CompanyNotFoundException;
import seedu.tinner.model.company.exceptions.DuplicateCompanyException;
import seedu.tinner.testutil.CompanyBuilder;

public class UniqueCompanyListTest {

    private final UniqueCompanyList uniqueCompanyList = new UniqueCompanyList();

    @Test
    public void contains_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.contains(null));
    }

    @Test
    public void contains_companyNotInList_returnsFalse() {
        assertFalse(uniqueCompanyList.contains(META));
    }

    @Test
    public void contains_companyInList_returnsTrue() {
        uniqueCompanyList.add(META);
        assertTrue(uniqueCompanyList.contains(META));
    }

    @Test
    public void contains_companyWithSameIdentityFieldsInList_returnsTrue() {
        uniqueCompanyList.add(META);
        Company editedAlice = new CompanyBuilder(META).withAddress(VALID_ADDRESS_WHATSAPP).build();
        assertTrue(uniqueCompanyList.contains(editedAlice));
    }

    @Test
    public void add_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.add(null));
    }

    @Test
    public void add_duplicateCompany_throwsDuplicateCompanyException() {
        uniqueCompanyList.add(META);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.add(META));
    }

    @Test
    public void setCompany_nullTargetCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompany(null, META));
    }

    @Test
    public void setCompany_nullEditedCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompany(META, null));
    }

    @Test
    public void setCompany_targetCompanyNotInList_throwsCompanyNotFoundException() {
        assertThrows(CompanyNotFoundException.class, () -> uniqueCompanyList.setCompany(META, META));
    }

    @Test
    public void setCompany_editedCompanyIsSameCompany_success() {
        uniqueCompanyList.add(META);
        uniqueCompanyList.setCompany(META, META);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(META);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasSameIdentity_success() {
        uniqueCompanyList.add(META);
        Company editedAlice = new CompanyBuilder(META).withAddress(VALID_ADDRESS_WHATSAPP).build();
        uniqueCompanyList.setCompany(META, editedAlice);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(editedAlice);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasDifferentIdentity_success() {
        uniqueCompanyList.add(META);
        uniqueCompanyList.setCompany(META, WHATSAPP);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(WHATSAPP);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompany_editedCompanyHasNonUniqueIdentity_throwsDuplicateCompanyException() {
        uniqueCompanyList.add(META);
        uniqueCompanyList.add(WHATSAPP);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.setCompany(META, WHATSAPP));
    }

    @Test
    public void remove_nullCompany_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.remove(null));
    }

    @Test
    public void remove_companyDoesNotExist_throwsCompanyNotFoundException() {
        assertThrows(CompanyNotFoundException.class, () -> uniqueCompanyList.remove(META));
    }

    @Test
    public void remove_existingCompany_removesCompany() {
        uniqueCompanyList.add(META);
        uniqueCompanyList.remove(META);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_nullUniqueCompanyList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompanies((UniqueCompanyList) null));
    }

    @Test
    public void setCompanies_uniqueCompanyList_replacesOwnListWithProvidedUniqueCompanyList() {
        uniqueCompanyList.add(META);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(WHATSAPP);
        uniqueCompanyList.setCompanies(expectedUniqueCompanyList);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueCompanyList.setCompanies((List<Company>) null));
    }

    @Test
    public void setCompanies_list_replacesOwnListWithProvidedList() {
        uniqueCompanyList.add(META);
        List<Company> companyList = Collections.singletonList(WHATSAPP);
        uniqueCompanyList.setCompanies(companyList);
        UniqueCompanyList expectedUniqueCompanyList = new UniqueCompanyList();
        expectedUniqueCompanyList.add(WHATSAPP);
        assertEquals(expectedUniqueCompanyList, uniqueCompanyList);
    }

    @Test
    public void setCompanies_listWithDuplicateCompanies_throwsDuplicateCompanyException() {
        List<Company> listWithDuplicateCompanies = Arrays.asList(META, META);
        assertThrows(DuplicateCompanyException.class, () -> uniqueCompanyList.setCompanies(listWithDuplicateCompanies));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueCompanyList.asUnmodifiableObservableList().remove(0));
    }
}
