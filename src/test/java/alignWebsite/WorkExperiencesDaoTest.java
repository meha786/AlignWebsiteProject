package alignWebsite;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.dao.WorkExperiencesDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.model.Students;
import org.mehaexample.asdDemo.model.WorkExperiences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class WorkExperiencesDaoTest {
  private static WorkExperiencesDao workExperiencesDao;
  private static StudentsDao studentsDao;

  @BeforeClass
  public static void init() {
    workExperiencesDao = new WorkExperiencesDao();
    studentsDao = new StudentsDao();
  }

  @Before
  public void addDatabasePlaceholder() throws ParseException {
    Students student = new Students("001234567","tomcatdd@gmail.com", "Tom", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            Term.FALL, 2014, Term.SPRING, 2016,
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS,null, true);
    Students student2 = new Students("111234567","jerrymodduse@gmail.com", "Jerry", "",
            "Mouse", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            Term.FALL, 2014, Term.SPRING, 2016,
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS,null, true);
    studentsDao.addStudent(student);
    studentsDao.addStudent(student2);

    WorkExperiences newWorkExperience = new WorkExperiences();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newWorkExperience.setStartDate(dateFormat.parse("2017-06-01"));
    newWorkExperience.setEndDate(dateFormat.parse("2017-12-01"));
    newWorkExperience.setCurrentJob(false);
    newWorkExperience.setTitle("Title");
    newWorkExperience.setDescription("Description");
    newWorkExperience.setNeuId(student.getNeuId());
    newWorkExperience.setCompanyName("Amazon");
    workExperiencesDao.createWorkExperience(newWorkExperience);
  }

  @After
  public void deleteDatabasePlaceholder() {
    workExperiencesDao.deleteWorkExperienceByNeuId("001234567");
    studentsDao.deleteStudent("001234567");
    studentsDao.deleteStudent("111234567");
  }

  @Test
  public void getTotalStudentsWorkingInACompanyTest() {
    int temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            Campus.SEATTLE, 2017, "amazon");
    assertTrue(temp == 1);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            Campus.BOSTON, 2017, "Amazon");
    assertTrue(temp == 0);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            Campus.SEATTLE, 2015, "Amazon");
    assertTrue(temp == 0);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            Campus.SEATTLE, 2015, "xyz");
    assertTrue(temp == 0);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            null, null, "Amazon");
    assertTrue(temp == 1);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            Campus.SEATTLE, null, "Amazon");
    assertTrue(temp == 1);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            null, 2017, "Amazon");
    assertTrue(temp == 1);
    temp = workExperiencesDao.getTotalStudentsWorkingInACompany(
            null, 2016, "Amazon");
    assertTrue(temp == 0);
  }

//  @Test
//  public void getTotalStudentsWithWorkExpTest() {
//    int temp = workExperiencesDao.getTotalStudentsWithWorkExp(null, null);
//    assertTrue(temp == 1);
//    temp = workExperiencesDao.getTotalStudentsWithWorkExp(Campus.SEATTLE, null);
//    assertTrue(temp == 1);
//    temp = workExperiencesDao.getTotalStudentsWithWorkExp(Campus.BOSTON, null);
//    assertTrue(temp == 0);
//    temp = workExperiencesDao.getTotalStudentsWithWorkExp(null, 2017);
//    assertTrue(temp == 1);
//    temp = workExperiencesDao.getTotalStudentsWithWorkExp(Campus.SEATTLE, 2016);
//    assertTrue(temp == 0);
//    temp = workExperiencesDao.getTotalStudentsWithWorkExp(null, 1994);
//    assertTrue(temp == 0);
//  }

//  @Test
//  public void getTopTenEmployersTest() throws ParseException {
//    List<String> temp = workExperiencesDao.getTopTenEmployers(null, null);
//    assertTrue(temp.size() == 1);
//
//    WorkExperiences newWorkExperience = new WorkExperiences();
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//    newWorkExperience.setStartDate(dateFormat.parse("2018-06-01"));
//    newWorkExperience.setEndDate(dateFormat.parse("2018-12-01"));
//    newWorkExperience.setCurrentJob(false);
//    newWorkExperience.setTitle("Title");
//    newWorkExperience.setDescription("Description");
//    newWorkExperience.setNeuId("001234567");
//    newWorkExperience.setCompanyName("Amazon");
//    workExperiencesDao.createWorkExperience(newWorkExperience);
//
//    WorkExperiences newWorkExperience2 = new WorkExperiences();
//    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
//    newWorkExperience2.setStartDate(dateFormat2.parse("2018-06-01"));
//    newWorkExperience2.setEndDate(dateFormat2.parse("2018-12-01"));
//    newWorkExperience2.setCurrentJob(false);
//    newWorkExperience2.setTitle("Title");
//    newWorkExperience2.setDescription("Description");
//    newWorkExperience2.setNeuId("111234567");
//    newWorkExperience2.setCompanyName("Aaa");
//    workExperiencesDao.createWorkExperience(newWorkExperience2);
//
//    temp = workExperiencesDao.getTopTenEmployers(null, null);
//    assertTrue(temp.size() == 2);
//    assertTrue(temp.get(0).equals("Amazon"));
//    assertTrue(temp.get(1).equals("Aaa"));
//
//    temp = workExperiencesDao.getTopTenEmployers(Campus.BOSTON, 1994);
//    assertTrue(temp.size() == 0);
//    temp = workExperiencesDao.getTopTenEmployers(Campus.BOSTON, null);
//    assertTrue(temp.size() == 0);
//  }

  @Test
  public void getWorkExperienceIdTest() {
    int tempId = workExperiencesDao.getWorkExperiencesByNeuId("001234567").get(0).getWorkExperienceId();
    WorkExperiences workExperience1 = workExperiencesDao.getWorkExperienceById(tempId);
    assertTrue(workExperience1.getNeuId().equals("001234567"));
    assertTrue(workExperience1.getCompanyName().equals("Amazon"));
    WorkExperiences notFoundWorkExperience = workExperiencesDao.getWorkExperienceById(-10);
    assertTrue(notFoundWorkExperience == null);
  }

  @Test
  public void getWorkExperiencesByNeuIdTest() {
    List<WorkExperiences> listOfWorkExperiences = workExperiencesDao.getWorkExperiencesByNeuId("001234567");
    assertTrue(listOfWorkExperiences.get(0).getCompanyName().equals("Amazon"));

    assertTrue(workExperiencesDao.getWorkExperiencesByNeuId("00000000").size() == 0);
  }

  @Test
  public void createUpdateDeleteWorkExperience() throws ParseException {
    WorkExperiences newWorkExperience = new WorkExperiences();

    Students student = studentsDao.getStudentRecord("111234567");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newWorkExperience.setStartDate(dateFormat.parse("2017-06-01"));
    newWorkExperience.setEndDate(dateFormat.parse("2017-12-01"));
    newWorkExperience.setCurrentJob(false);
    newWorkExperience.setTitle("Title");
    newWorkExperience.setDescription("Description");
    newWorkExperience.setNeuId(student.getNeuId());
    newWorkExperience.setCompanyName("Facebook");

    // create new work experience
    workExperiencesDao.createWorkExperience(newWorkExperience);
    WorkExperiences foundWorkExperience = workExperiencesDao.getWorkExperiencesByNeuId("111234567").get(0);
    assertTrue(foundWorkExperience.getCompanyName().equals("Facebook"));

    // update found work experience
    foundWorkExperience.setDescription("Description2");
    workExperiencesDao.updateWorkExperience(foundWorkExperience);
    assertTrue(workExperiencesDao.getWorkExperiencesByNeuId("111234567").get(0).getDescription().equals("Description2"));
    newWorkExperience.setWorkExperienceId(-100);
    assertFalse(workExperiencesDao.updateWorkExperience(newWorkExperience));

    // delete the work experience
    workExperiencesDao.deleteWorkExperienceById(foundWorkExperience.getWorkExperienceId());
    assertTrue(workExperiencesDao.getWorkExperienceById(foundWorkExperience.getWorkExperienceId()) == null);
    assertFalse(workExperiencesDao.deleteWorkExperienceById(-100));
  }
}