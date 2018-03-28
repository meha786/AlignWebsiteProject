package alignWebsite.alignpublic;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradDegrees;
import org.mehaexample.asdDemo.model.alignpublic.TopUndergradSchools;
import org.mehaexample.asdDemo.model.alignpublic.UndergraduatesPublic;

import java.util.List;

import static org.junit.Assert.*;

public class UndergraduatesPublicDaoTest {
  private static UndergraduatesPublicDao undergraduatesPublicDao;
  private static StudentsPublicDao studentsPublicDao;
  private static UndergraduatesPublic undergraduate;
  private static UndergraduatesPublic undergraduate2;
  private static UndergraduatesPublic undergraduate3;

  @BeforeClass
  public static void init() {
    undergraduatesPublicDao = new UndergraduatesPublicDao(true);
    studentsPublicDao = new StudentsPublicDao(true);

//    undergraduatesPublicDao = new UndergraduatesPublicDao();
//    studentsPublicDao = new StudentsPublicDao();

    StudentsPublic studentsPublic = new StudentsPublic(
            5, 2016, true);
    studentsPublicDao.createStudent(studentsPublic);
    StudentsPublic studentsPublic2 = new StudentsPublic(
            6, 2016, true);
    studentsPublicDao.createStudent(studentsPublic2);
    undergraduate = new UndergraduatesPublic(
            5, "Chemistry", "George Washington University");
    undergraduatesPublicDao.createUndergraduate(undergraduate);
    undergraduate2 = new UndergraduatesPublic(
            6, "English", "George Washington University");
    undergraduatesPublicDao.createUndergraduate(undergraduate2);
    undergraduate3 = new UndergraduatesPublic(
            6, "English", "Harvard University");
    undergraduatesPublicDao.createUndergraduate(undergraduate3);
  }

  @AfterClass
  public static void remove() {
    undergraduatesPublicDao.deleteUndergraduateById(undergraduate.getUndergraduateId());
    undergraduatesPublicDao.deleteUndergraduateById(undergraduate2.getUndergraduateId());
    undergraduatesPublicDao.deleteUndergraduateById(undergraduate3.getUndergraduateId());
    studentsPublicDao.deleteStudentByPublicId(6);
    studentsPublicDao.deleteStudentByPublicId(5);
  }

  @Test(expected = HibernateException.class)
  public void deleteNonExistentUndergraduateTest() {
    undergraduatesPublicDao.deleteUndergraduateById(-200);
  }

  @Test
  public void findUndergraduateByIdTest() {
    assertTrue(undergraduatesPublicDao.findUndergraduateById(undergraduate.getUndergraduateId())
            .getUndergradDegree().equals("Chemistry"));
    assertTrue(undergraduatesPublicDao.findUndergraduateById(undergraduate.getUndergraduateId())
            .getUndergradSchool().equals("George Washington University"));
    assertTrue(undergraduatesPublicDao.findUndergraduateById(-200) == null);
  }

  @Test
  public void getTopUndergradSchoolsTest() {
    List<TopUndergradSchools> listOfTopSchools = undergraduatesPublicDao.getTopUndergradSchools(3);
    assertTrue(listOfTopSchools.size() == 2);
    assertTrue(listOfTopSchools.get(0).getUndergradSchool().equals("George Washington University"));
    assertTrue(listOfTopSchools.get(0).getTotalStudents() == 2);
    assertTrue(listOfTopSchools.get(1).getUndergradSchool().equals("Harvard University"));
    assertTrue(listOfTopSchools.get(1).getTotalStudents() == 1);
  }

  @Test
  public void getTopUndergradDegreesTest() {
    List<TopUndergradDegrees> listOfTopDegrees = undergraduatesPublicDao.getTopUndergradDegrees(3);
    assertTrue(listOfTopDegrees.size() == 2);
    assertTrue(listOfTopDegrees.get(0).getUndergradDegree().equals("English"));
    assertTrue(listOfTopDegrees.get(0).getTotalStudents() == 2);
    assertTrue(listOfTopDegrees.get(1).getUndergradDegree().equals("Chemistry"));
    assertTrue(listOfTopDegrees.get(1).getTotalStudents() == 1);
  }

  @Test
  public void getListOfAllSchoolsTest() {
    List<String> listOfAllSchools = undergraduatesPublicDao.getListOfAllSchools();
    assertTrue(listOfAllSchools.size() == 2);
    assertTrue(listOfAllSchools.get(0).equals("George Washington University"));
    assertTrue(listOfAllSchools.get(1).equals("Harvard University"));
  }

  @Test
  public void getListOfAllUndergraduateDegreesTest() {
    List<String> listOfAllUndergraduateDegrees = undergraduatesPublicDao.getListOfAllUndergraduateDegrees();
    assertTrue(listOfAllUndergraduateDegrees.size() == 2);
    assertTrue(listOfAllUndergraduateDegrees.get(0).equals("English"));
    assertTrue(listOfAllUndergraduateDegrees.get(1).equals("Chemistry"));
  }
}
