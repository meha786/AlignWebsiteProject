package alignWebsite;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.PriorEducationsDao;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.model.PriorEducations;
import org.mehaexample.asdDemo.model.Students;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class PriorEducationsDaoTest {
  private static PriorEducationsDao priorEducationsDao;
  private static StudentsDao studentsDao;

  @BeforeClass
  public static void init() {
    priorEducationsDao = new PriorEducationsDao();
    studentsDao = new StudentsDao();
  }

  @Before
  public void addDatabasePlaceholder() throws ParseException {
    Students student = new Students("001234567","tomcat@gmail.com", "Tom", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            Term.FALL, 2014, Term.SPRING, 2016,
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS,null, true);
    Students student2 = new Students("111234567","jerrymouse@gmail.com", "Jerry", "",
            "Mouse", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Boston", "98109",
            Term.FALL, 2014, Term.SPRING, 2016,
            EnrollmentStatus.FULL_TIME, Campus.BOSTON, DegreeCandidacy.MASTERS,null, true);
    studentsDao.addStudent(student);
    studentsDao.addStudent(student2);

    PriorEducations newPriorEducation = new PriorEducations();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newPriorEducation.setGraduationDate(dateFormat.parse("2015-01-01"));
    newPriorEducation.setGpa(3.50f);
    newPriorEducation.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
    newPriorEducation.setNeuId(student.getNeuId());
    newPriorEducation.setMajorName("Computer Science");
    newPriorEducation.setInstitutionName("University of Washington");

    priorEducationsDao.createPriorEducation(newPriorEducation);
  }

  @After
  public void deleteDatabasePlaceholder() {
    priorEducationsDao.deletePriorEducationById(
            priorEducationsDao.getPriorEducationsByNeuId("001234567").get(0).getPriorEducationId());
    studentsDao.deleteStudent("001234567");
    studentsDao.deleteStudent("111234567");

  }

  @Test
  public void getTopTenBachelors() throws ParseException {
    List<String> temp = priorEducationsDao.getTopTenBachelors(null);
    assertTrue(temp.size() == 1);
    assertTrue(temp.get(0).equals("Computer Science"));

    // add new prior education
    PriorEducations newPriorEducation = new PriorEducations();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newPriorEducation.setGraduationDate(dateFormat.parse("2015-01-01"));
    newPriorEducation.setGpa(3.50f);
    newPriorEducation.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
    newPriorEducation.setNeuId("111234567");
    newPriorEducation.setMajorName("farming");
    newPriorEducation.setInstitutionName("Harvard University");
    priorEducationsDao.createPriorEducation(newPriorEducation);

    // test using the newly created prior education
    temp = priorEducationsDao.getTopTenBachelors(null);
    assertTrue(temp.size() == 2);
    assertTrue(temp.get(0).equals("Computer Science"));
    assertTrue(temp.get(1).equals("farming"));

    temp = priorEducationsDao.getTopTenBachelors(Campus.BOSTON);
    assertTrue(temp.size() == 1);
    assertTrue(temp.get(0).equals("farming"));

    temp = priorEducationsDao.getTopTenBachelors(Campus.SEATTLE);
    assertTrue(temp.size() == 1);
    assertTrue(temp.get(0).equals("Computer Science"));

    temp = priorEducationsDao.getTopTenBachelors(Campus.CHARLOTTE);
    assertTrue(temp.isEmpty());

    // delete new prior education
    priorEducationsDao.deletePriorEducationById(
            priorEducationsDao.getPriorEducationsByNeuId("111234567").get(0).getPriorEducationId());
  }

  @Test
  public void getPriorEducationByIdTest() {
    int tempId = priorEducationsDao.getPriorEducationsByNeuId("001234567").get(0).getPriorEducationId();
    PriorEducations tempPriorEducation = priorEducationsDao.getPriorEducationById(tempId);
    assertTrue(tempPriorEducation.getNeuId().equals("001234567"));
    assertTrue(tempPriorEducation.getInstitutionName().equals("University of Washington"));
    assertTrue(tempPriorEducation.getGpa() == 3.50f);
    assertTrue(priorEducationsDao.getPriorEducationById(-20) == null);
  }

  @Test
  public void getPriorEducationsByNeuIdTest() {
    List<PriorEducations> listOfPriorEducation = priorEducationsDao.getPriorEducationsByNeuId("001234567");
    assertTrue(listOfPriorEducation.get(0).getInstitutionName().equals("University of Washington"));
    assertTrue(listOfPriorEducation.get(0).getMajorName().equals("Computer Science"));
    assertTrue(priorEducationsDao.getPriorEducationsByNeuId("000000000") == null);
  }

  @Test
  public void createUpdateDeletePriorEducation() throws ParseException {
    PriorEducations newPriorEducation = new PriorEducations();

    Students student = studentsDao.getStudentRecord("111234567");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newPriorEducation.setGraduationDate(dateFormat.parse("2015-01-01"));
    newPriorEducation.setGpa(4.00f);
    newPriorEducation.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
    newPriorEducation.setNeuId(student.getNeuId());
    newPriorEducation.setMajorName("Accounting");
    newPriorEducation.setInstitutionName("Stanford University");

    // create new work experience
    priorEducationsDao.createPriorEducation(newPriorEducation);
    PriorEducations foundPriorEducation = priorEducationsDao.getPriorEducationsByNeuId("111234567").get(0);
    System.out.println(foundPriorEducation.getGpa());
    assertTrue(foundPriorEducation.getGpa() == 4.00f);
    assertTrue(foundPriorEducation.getInstitutionName().equals("Stanford University"));

    // update found work experience
    foundPriorEducation.setGpa(3.99f);
    priorEducationsDao.updatePriorEducation(foundPriorEducation);
    assertTrue(priorEducationsDao.getPriorEducationsByNeuId("111234567").get(0).getGpa() == 3.99f);
    newPriorEducation.setPriorEducationId(-100);
    assertFalse(priorEducationsDao.updatePriorEducation(newPriorEducation));

    // delete the work experience
    priorEducationsDao.deletePriorEducationById(foundPriorEducation.getPriorEducationId());
    assertTrue(priorEducationsDao.getPriorEducationById(foundPriorEducation.getPriorEducationId()) == null);
    assertFalse(priorEducationsDao.deletePriorEducationById(-100));
  }
}