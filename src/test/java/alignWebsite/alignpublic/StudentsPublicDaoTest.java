package alignWebsite.alignpublic;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.UndergraduatesPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopGradYears;
import org.mehaexample.asdDemo.model.alignpublic.UndergraduatesPublic;
import org.mehaexample.asdDemo.model.alignpublic.WorkExperiencesPublic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class StudentsPublicDaoTest {
  private static UndergraduatesPublicDao undergraduatesPublicDao;
  private static WorkExperiencesPublicDao workExperiencesPublicDao;
  private static StudentsPublicDao studentsPublicDao;
  private static WorkExperiencesPublic workExperience;
  private static UndergraduatesPublic undergraduatesPublic;

  @BeforeClass
  public static void init() {
    undergraduatesPublicDao = new UndergraduatesPublicDao(true);
    workExperiencesPublicDao = new WorkExperiencesPublicDao(true);
    studentsPublicDao = new StudentsPublicDao(true);

//    undergraduatesPublicDao = new UndergraduatesPublicDao();
//    workExperiencesPublicDao = new WorkExperiencesPublicDao();
//    studentsPublicDao = new StudentsPublicDao();

    StudentsPublic studentsPublic = new StudentsPublic(5, 2016, true);
    studentsPublicDao.createStudent(studentsPublic);
    StudentsPublic studentsPublic2 = new StudentsPublic(6, 2016, false);
    studentsPublicDao.createStudent(studentsPublic2);
    StudentsPublic studentsPublic3 = new StudentsPublic(7, 2017, true);
    studentsPublicDao.createStudent(studentsPublic3);
    workExperience = new WorkExperiencesPublic(5, "Google");
    workExperience = workExperiencesPublicDao.createWorkExperience(workExperience);
    undergraduatesPublic = new UndergraduatesPublic(
            5, "Farming", "UCLA");
    undergraduatesPublicDao.createUndergraduate(undergraduatesPublic);
  }

  @AfterClass
  public static void deleteDatabasePlaceholder() {
    undergraduatesPublicDao.deleteUndergraduateById(undergraduatesPublic.getUndergraduateId());
    workExperiencesPublicDao.deleteWorkExperienceById(workExperience.getWorkExperienceId());
    studentsPublicDao.deleteStudentByPublicId(5);
    studentsPublicDao.deleteStudentByPublicId(6);
    studentsPublicDao.deleteStudentByPublicId(7);
  }

  // need VPN for this
//  @Test
//  public void deploymentDatabaseConnectionTest() {
//    new StudentsPublicDao();
//  }

  @Test(expected = HibernateException.class)
  public void createDuplicateStudentTest() {
    StudentsPublic studentsPublic = new StudentsPublic(5, 2016, true);
    studentsPublicDao.createStudent(studentsPublic);
  }

  @Test(expected = HibernateException.class)
  public void deleteNonExistentStudentTest() {
    studentsPublicDao.deleteStudentByPublicId(-200);
  }

  @Test(expected = HibernateException.class)
  public void updateNonExistentStudentTest() {
    StudentsPublic sp = new StudentsPublic();
    sp.setPublicId(-200);
    studentsPublicDao.updateStudent(sp);
  }

  @Test
  public void findStudentByPublicIdAndUpdateTest() {
    StudentsPublic studentsPublic = studentsPublicDao.findStudentByPublicId(5);
    assertTrue(studentsPublic.getGraduationYear() == 2016);
    studentsPublic.setGraduationYear(2017);
    studentsPublicDao.updateStudent(studentsPublic);
    studentsPublic = studentsPublicDao.findStudentByPublicId(5);
    assertTrue(studentsPublic.getGraduationYear() == 2017);
    studentsPublic.setGraduationYear(2016);
    studentsPublicDao.updateStudent(studentsPublic);
    studentsPublic = studentsPublicDao.findStudentByPublicId(5);
    assertTrue(studentsPublic.getGraduationYear() == 2016);
    assertTrue(studentsPublic.getUndergraduates().get(0).getUndergradSchool().equals("UCLA"));
    assertTrue(studentsPublic.getWorkExperiences().get(0).getPublicId() == 5);
    assertTrue(studentsPublic.getWorkExperiences().get(0).getCoop().equals("Google"));
  }

  @Test
  public void getTopGraduationYearsTest() {
    List<TopGradYears> listOfTopGradYears = studentsPublicDao.getTopGraduationYears(3);
    assertTrue(listOfTopGradYears.size() == 2);
    assertTrue(listOfTopGradYears.get(0).getGraduationYear() == 2016);
    assertTrue(listOfTopGradYears.get(0).getTotalStudents() == 2);
    assertTrue(listOfTopGradYears.get(1).getGraduationYear() == 2017);
    assertTrue(listOfTopGradYears.get(1).getTotalStudents() == 1);
  }

  @Test
  public void getListOfAllGraduationYearsTest() {
    List<Integer> listOfAllGraduationYears = studentsPublicDao.getListOfAllGraduationYears();
    assertTrue(listOfAllGraduationYears.size() == 2);
    assertTrue(listOfAllGraduationYears.get(0) == 2016);
    assertTrue(listOfAllGraduationYears.get(1) == 2017);
  }

  @Test
  public void getListOfAllStudentsTest() {
    List<StudentsPublic> listOfAllStudents = studentsPublicDao.getListOfAllStudents();
    assertTrue(listOfAllStudents.size() == 2);
    assertTrue(listOfAllStudents.get(0).getGraduationYear() == 2017);
    assertTrue(listOfAllStudents.get(1).getGraduationYear() == 2016);
    assertTrue(listOfAllStudents.get(1).getWorkExperiences().get(0).getCoop().equals("Google"));
  }

  @Test
  public void getPublicFilteredStudentsTest() {
    Map<String, List<String>> filter = new HashMap<>();
    List<String> coop = new ArrayList<>();
    List<String> graduationYear = new ArrayList<>();
    List<String> undergradDegree = new ArrayList<>();
    coop.add("Google");
    graduationYear.add("2016");
    undergradDegree.add("Farming");
    undergradDegree.add("English");
    filter.put("coop", coop);
    filter.put("graduationYear", graduationYear);
    filter.put("undergradDegree", undergradDegree);
    List<StudentsPublic> listOfFilteredStudents = studentsPublicDao.getPublicFilteredStudents(filter, 1, 1);
    assertTrue(listOfFilteredStudents.size() == 1);
    assertTrue(listOfFilteredStudents.get(0).getGraduationYear() == 2016);

    assertTrue(studentsPublicDao.getPublicFilteredStudentsCount(new HashMap<String, List<String>>()) == 2);

    listOfFilteredStudents = studentsPublicDao.getPublicFilteredStudents(new HashMap<String, List<String>>(), 1, 3);
    assertTrue(listOfFilteredStudents.size() == 2);

    listOfFilteredStudents = studentsPublicDao.getPublicFilteredStudents(new HashMap<String, List<String>>(), 2, 3);
    assertTrue(listOfFilteredStudents.size() == 1);

    Map<String, List<String>> filter2 = new HashMap<>();
    List<String> graduationYear2 = new ArrayList<>();
    graduationYear2.add("2016");
    filter2.put("graduationYear", graduationYear2);
    List<StudentsPublic> listOfFilteredStudents2 = studentsPublicDao.getPublicFilteredStudents(filter2, 1, 2);
    assertTrue(listOfFilteredStudents2.size() == 1);
  }
}