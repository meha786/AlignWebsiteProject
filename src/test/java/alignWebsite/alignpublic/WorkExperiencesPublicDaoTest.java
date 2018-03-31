package alignWebsite.alignpublic;

import org.hibernate.HibernateException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.TopCoops;
import org.mehaexample.asdDemo.model.alignpublic.WorkExperiencesPublic;

import java.util.List;

import static org.junit.Assert.*;

public class WorkExperiencesPublicDaoTest {
  private static WorkExperiencesPublicDao workExperiencesPublicDao;
  private static StudentsPublicDao studentsPublicDao;
  private static WorkExperiencesPublic workExperience;
  private static WorkExperiencesPublic workExperience2;
  private static WorkExperiencesPublic workExperience3;

  @BeforeClass
  public static void init() {
    workExperiencesPublicDao = new WorkExperiencesPublicDao(true);
    studentsPublicDao = new StudentsPublicDao(true);

//    workExperiencesPublicDao = new WorkExperiencesPublicDao();
//    studentsPublicDao = new StudentsPublicDao();

    StudentsPublic studentsPublic = new StudentsPublic(5, 2016, true);
    studentsPublicDao.createStudent(studentsPublic);
    StudentsPublic studentsPublic2 = new StudentsPublic(6, 2016, true);
    studentsPublicDao.createStudent(studentsPublic2);
    workExperience = new WorkExperiencesPublic(5, "Google");
    workExperience = workExperiencesPublicDao.createWorkExperience(workExperience);
    workExperience2 = new WorkExperiencesPublic(6, "Google");
    workExperience2 = workExperiencesPublicDao.createWorkExperience(workExperience2);
    workExperience3 = new WorkExperiencesPublic(5, "Microsoft");
    workExperience3 = workExperiencesPublicDao.createWorkExperience(workExperience3);
  }

  @AfterClass
  public static void deleteDatabasePlaceholder() {
    workExperiencesPublicDao.deleteWorkExperienceById(workExperience.getWorkExperienceId());
    workExperiencesPublicDao.deleteWorkExperienceById(workExperience2.getWorkExperienceId());
    workExperiencesPublicDao.deleteWorkExperienceById(workExperience3.getWorkExperienceId());
    studentsPublicDao.deleteStudentByPublicId(6);
    studentsPublicDao.deleteStudentByPublicId(5);
  }

  @Test(expected = HibernateException.class)
  public void deleteNonExistentWorkExperienceTest() {
    assertTrue(workExperiencesPublicDao.deleteWorkExperienceById(-200));
  }

  @Test
  public void findWorkExperienceByIdTest() {
    assertTrue(workExperiencesPublicDao.findWorkExperienceById(
            workExperience.getWorkExperienceId()).getCoop().equals("Google"));
    assertTrue(workExperiencesPublicDao.findWorkExperienceById(-200) == null);
  }

  @Test
  public void getTopCoopsTest() {
    List<TopCoops> listOfTopCoops = workExperiencesPublicDao.getTopCoops(3);
    assertTrue(listOfTopCoops.size() == 2);
    assertTrue(listOfTopCoops.get(0).getCoop().equals("Google"));
    assertTrue(listOfTopCoops.get(0).getTotalStudents() == 2);
    assertTrue(listOfTopCoops.get(1).getCoop().equals("Microsoft"));
    assertTrue(listOfTopCoops.get(1).getTotalStudents() == 1);
  }

  @Test
  public void getListOfAllCoopCompanies() {
    List<String> listOfAllCoopCompanies = workExperiencesPublicDao.getListOfAllCoopCompanies();
    assertTrue(listOfAllCoopCompanies.size() == 2);
    assertTrue(listOfAllCoopCompanies.get(0).equals("Google"));
    assertTrue(listOfAllCoopCompanies.get(1).equals("Microsoft"));
  }
}