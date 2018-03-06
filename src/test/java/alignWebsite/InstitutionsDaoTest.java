package alignWebsite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.InstitutionsDao;
import org.mehaexample.asdDemo.model.Institutions;

import java.util.List;

import static org.junit.Assert.*;

public class InstitutionsDaoTest {
  private static InstitutionsDao institutionsDao;

  @BeforeClass
  public static void init() {
    institutionsDao = new InstitutionsDao();
  }

  @Test
  public void getAllInstitutionsTest() {
    List<Institutions> listOfInstitutions = institutionsDao.getAllInstitutions();
    for (Institutions institution : listOfInstitutions) {
      switch (institution.getInstitutionName()) {
        case "University of Washington":
        case "Stanford University":
        case "University of California - Berkeley":
        case "University of California - Los Angeles":
        case "Massachusetts Institute of Technology":
        case "Harvard University":
          break;
        default:
          fail();
          break;
      }
    }
  }

  @Test
  public void getInstitutionByInstitutionIdTest() {
    assertTrue(institutionsDao.getInstitutionById(1)
            .getInstitutionName().equals("University of Washington"));
    assertTrue(institutionsDao.getInstitutionById(6)
            .getInstitutionName().equals("Harvard University"));
    assertTrue(institutionsDao.getInstitutionById(-100) == null);
  }

  @Test
  public void getInstitutionByInstitutionNameTest() {
    assertTrue(institutionsDao.getInstitutionByName("Stanford University")
            .getInstitutionName().equals("Stanford University"));
    assertTrue(institutionsDao.getInstitutionByName("University of California - Berkeley")
            .getInstitutionName().equals("University of California - Berkeley"));
    assertTrue(institutionsDao.getInstitutionByName("NonExistentInstitution") == null);
  }

  @Test
  public void createAndDeleteInstitutionTest() {
    Institutions institution = new Institutions();
    institution.setInstitutionName("Seattle University");
    institutionsDao.createInstitution(institution);
    institution = institutionsDao.getInstitutionByName("Seattle University");
    assertTrue(institution.getInstitutionName().equals("Seattle University"));

    assertTrue(institutionsDao.deleteInstitutionById(institution.getInstitutionId()));
    assertFalse(institutionsDao.deleteInstitutionById(-20));
  }

  @Test
  public void updateInstitutionTest() {
    Institutions institution = institutionsDao.getInstitutionByName("University of Washington");
    institution.setInstitutionName("University of Seattle");
    institutionsDao.updateInstitution(institution);
    assertTrue(institutionsDao.getInstitutionByName("University of Seattle")
            .getInstitutionName().equals("University of Seattle"));

    institution.setInstitutionName("University of Washington");
    institutionsDao.updateInstitution(institution);
    assertTrue(institutionsDao.getInstitutionByName("University of Washington")
            .getInstitutionName().equals("University of Washington"));
    assertTrue(institutionsDao.getInstitutionByName("University of Seattle") == null);

    institution.setInstitutionId(-100);
    assertFalse(institutionsDao.updateInstitution(institution));
    assertTrue(institutionsDao.getInstitutionById(-100) == null);
  }
}