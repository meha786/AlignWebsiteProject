package alignWebsite;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.MajorsDao;
import org.mehaexample.asdDemo.model.Majors;

public class MajorsDaoTest {
  private static MajorsDao majorsDao;

  @BeforeClass
  public static void init() {
    majorsDao = new MajorsDao();
  }

  @Test
  public void getAllMajorsTest() {
    List<Majors> listOfMajors = majorsDao.getAllMajors();
    for (Majors major : listOfMajors) {
      switch (major.getMajor()) {
        case "Computer Science":
        case "Accounting":
        case "English":
          break;
        default:
          fail();
          break;
      }
    }
  }

  @Test
  public void getInstitutionByMajorIdTest() {
    assertTrue(majorsDao.getMajorById(1)
            .getMajor().equals("Computer Science"));
    assertTrue(majorsDao.getMajorById(3)
            .getMajor().equals("English"));
    assertTrue(majorsDao.getMajorById(-100) == null);
  }

  @Test
  public void getInstitutionByMajorNameTest() {
    assertTrue(majorsDao.getMajorByName("Accounting")
            .getMajor().equals("Accounting"));
    assertTrue(majorsDao.getMajorByName("NonExistentMajor") == null);
  }

  @Test
  public void createAndDeleteMajorTest() {
    Majors major = new Majors();
    major.setMajor("Electrical Engineering");
    majorsDao.createMajor(major);
    major = majorsDao.getMajorByName("Electrical Engineering");
    assertTrue(major.getMajor().equals("Electrical Engineering"));

    assertTrue(majorsDao.deleteMajorById(major.getMajorId()));
    assertTrue(majorsDao.getMajorByName("Electrical Engineering") == null);
    assertFalse(majorsDao.deleteMajorById(-20));
  }

  @Test
  public void updateMajorTest() {
    Majors major = majorsDao.getMajorByName("Computer Science");
    major.setMajor("Computer Engineering");
    majorsDao.updateMajor(major);
    assertTrue(majorsDao.getMajorByName("Computer Engineering")
            .getMajor().equals("Computer Engineering"));

    major.setMajor("Computer Science");
    majorsDao.updateMajor(major);
    assertTrue(majorsDao.getMajorByName("Computer Science")
            .getMajor().equals("Computer Science"));
    assertTrue(majorsDao.getMajorById(1)
            .getMajor().equals("Computer Science"));
    assertTrue(majorsDao.getMajorByName("Computer Engineering") == null);

    major.setMajorId(-100);
    assertFalse(majorsDao.updateMajor(major));
    assertTrue(majorsDao.getMajorById(-100) == null);
  }
}