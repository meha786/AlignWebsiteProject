package alignWebsite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.InstitutionsDao;
import org.mehaexample.asdDemo.dao.MajorsDao;
import org.mehaexample.asdDemo.dao.PriorEducationsDao;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.model.Institutions;
import org.mehaexample.asdDemo.model.Majors;
import org.mehaexample.asdDemo.model.PriorEducations;
import org.mehaexample.asdDemo.model.Students;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class PriorEducationsDaoTest {
  private static PriorEducationsDao priorEducationsDao;
  private static StudentsDao studentsDao;
  private static MajorsDao majorsDao;
  private static InstitutionsDao institutionsDao;

  @BeforeClass
  public static void init() {
    priorEducationsDao = new PriorEducationsDao();
    studentsDao = new StudentsDao();
    majorsDao = new MajorsDao();
    institutionsDao = new InstitutionsDao();
  }

  @Test
  public void getPriorEducationByIdTest() {
    PriorEducations tempPriorEducation = priorEducationsDao.getPriorEducationById(1);
    assertTrue(tempPriorEducation.getStudent().getNeuId().equals("001234567"));
    assertTrue(tempPriorEducation.getInstitution().getInstitutionName().equals("University of Washington"));
    assertTrue(tempPriorEducation.getMajor().getMajorId() == 1);
    assertTrue(tempPriorEducation.getGpa() == 3.50f);
    assertTrue(priorEducationsDao.getPriorEducationById(-20) == null);
  }

  @Test
  public void getPriorEducationsByNeuIdTest() {
    List<PriorEducations> listOfPriorEducation = priorEducationsDao.getPriorEducationsByNeuId("111234545");
    assertTrue(listOfPriorEducation.get(0).getInstitution().getInstitutionName().equals("University of California - Los Angeles"));
    assertTrue(listOfPriorEducation.get(0).getMajor().getMajor().equals("Computer Science"));
    assertTrue(priorEducationsDao.getPriorEducationsByNeuId("000000000") == null);
  }

  @Test
  public void createUpdateDeletePriorEducation() throws ParseException {
    PriorEducations newPriorEducation = new PriorEducations();

    Students student = studentsDao.getStudentRecord("001234567");
    Majors major = majorsDao.getMajorByName("Computer Science");
    Institutions institution = institutionsDao.getInstitutionByName("Stanford University");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newPriorEducation.setGraduationDate(dateFormat.parse("2015-01-01"));
    newPriorEducation.setGpa(4.00f);
    newPriorEducation.setDegreeCandidacy(DegreeCandidacy.BACHELORS);
    newPriorEducation.setStudent(student);
    newPriorEducation.setMajor(major);
    newPriorEducation.setInstitution(institution);

    // create new work experience
    priorEducationsDao.createPriorEducation(newPriorEducation);
    PriorEducations foundPriorEducation = priorEducationsDao.getPriorEducationsByNeuId("001234567").get(1);
    assertTrue(foundPriorEducation.getGpa() == 4.00f);
    assertTrue(foundPriorEducation.getInstitution().getInstitutionName().equals("Stanford University"));

    // update found work experience
    foundPriorEducation.setGpa(3.99f);
    priorEducationsDao.updatePriorEducation(foundPriorEducation);
    assertTrue(priorEducationsDao.getPriorEducationsByNeuId("001234567").get(1).getGpa() == 3.99f);
    newPriorEducation.setPriorEducationId(-100);
    assertFalse(priorEducationsDao.updatePriorEducation(newPriorEducation));

    // delete the work experience
    priorEducationsDao.deletePriorEducationById(foundPriorEducation.getPriorEducationId());
    assertTrue(priorEducationsDao.getPriorEducationById(foundPriorEducation.getPriorEducationId()) == null);
    assertFalse(priorEducationsDao.deletePriorEducationById(-100));
  }
}