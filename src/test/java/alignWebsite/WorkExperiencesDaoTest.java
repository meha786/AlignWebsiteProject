package alignWebsite;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.CompaniesDao;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.dao.WorkExperiencesDao;
import org.mehaexample.asdDemo.model.Companies;
import org.mehaexample.asdDemo.model.Students;
import org.mehaexample.asdDemo.model.WorkExperiences;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

public class WorkExperiencesDaoTest {
  private static WorkExperiencesDao workExperiencesDao;
  private static StudentsDao studentsDao;
  private static CompaniesDao companiesDao;

  @BeforeClass
  public static void init() {
    workExperiencesDao = new WorkExperiencesDao();
    studentsDao = new StudentsDao();
    companiesDao = new CompaniesDao();
  }

//  @Test
//  public void getWorkExperienceIdTest() {
//    WorkExperiences workExperience1 = workExperiencesDao.getWorkExperienceById(1);
//    assertTrue(workExperience1.getStudent().getNeuId().equals("001234567"));
//    assertTrue(workExperience1.getStudent().getEmail().equals("kwan.d@husky.neu.edu"));
//    assertTrue(workExperience1.getCompany().getCompanyName().equals("Amazon"));
//    WorkExperiences notFoundWorkExperience = workExperiencesDao.getWorkExperienceById(-10);
//    assertTrue(notFoundWorkExperience == null);
//  }

//  @Test
//  public void getWorkExperiencesByNeuIdTest() {
//    List<WorkExperiences> listOfWorkExperiences = workExperiencesDao.getWorkExperiencesByNeuId("001234567");
//    assertTrue(listOfWorkExperiences.get(0).getCompany().getCompanyName().equals("Amazon"));
//    assertTrue(listOfWorkExperiences.get(1).getCompany().getCompanyName().equals("Google"));
//
//    assertTrue(workExperiencesDao.getWorkExperiencesByNeuId("00000000") == null);
//  }

  @Test
  public void createUpdateDeleteWorkExperience() throws ParseException {
    WorkExperiences newWorkExperience = new WorkExperiences();

    Students student = studentsDao.getStudentRecord("211234548");
    Companies company = companiesDao.getCompanyByName("Facebook");

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    newWorkExperience.setStartDate(dateFormat.parse("2017-06-01"));
    newWorkExperience.setEndDate(dateFormat.parse("2017-12-01"));
    newWorkExperience.setCurrentJob(false);
    newWorkExperience.setTitle("Title");
    newWorkExperience.setDescription("Description");
    newWorkExperience.setStudent(student);
    newWorkExperience.setCompany(company);

    // create new work experience
    workExperiencesDao.createWorkExperience(newWorkExperience);
    WorkExperiences foundWorkExperience = workExperiencesDao.getWorkExperiencesByNeuId("211234548").get(0);
    assertTrue(foundWorkExperience.getCompany().getCompanyName().equals("Facebook"));

    // update found work experience
    foundWorkExperience.setDescription("Description2");
    workExperiencesDao.updateWorkExperience(foundWorkExperience);
    assertTrue(workExperiencesDao.getWorkExperiencesByNeuId("211234548").get(0).getDescription().equals("Description2"));
    newWorkExperience.setWorkExperienceId(-100);
    assertFalse(workExperiencesDao.updateWorkExperience(newWorkExperience));

    // delete the work experience
    workExperiencesDao.deleteWorkExperienceById(foundWorkExperience.getWorkExperienceId());
    assertTrue(workExperiencesDao.getWorkExperienceById(foundWorkExperience.getWorkExperienceId()) == null);
    assertFalse(workExperiencesDao.deleteWorkExperienceById(-100));
  }
}