package alignWebsite.alignpublic;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.alignpublic.StudentsPublicDao;
import org.mehaexample.asdDemo.dao.alignpublic.WorkExperiencesPublicDao;
import org.mehaexample.asdDemo.model.alignpublic.StudentsPublic;
import org.mehaexample.asdDemo.model.alignpublic.WorkExperiencesPublic;

import static org.junit.Assert.*;

public class WorkExperiencesPublicDaoTest {
  private static WorkExperiencesPublicDao workExperiencesPublicDao;
  private static StudentsPublicDao studentsPublicDao;
  private static WorkExperiencesPublic workExperience;

  @BeforeClass
  public static void init() {
    workExperiencesPublicDao = new WorkExperiencesPublicDao();
    studentsPublicDao = new StudentsPublicDao();
    StudentsPublic studentsPublic = new StudentsPublic(5, "Josh", null, 2016);
    studentsPublicDao.createStudent(studentsPublic);
    workExperience = new WorkExperiencesPublic(5, "Google");
    workExperience = workExperiencesPublicDao.createWorkExperience(workExperience);
  }

  @AfterClass
  public static void deleteDatabasePlaceholder() {
    workExperiencesPublicDao.deleteWorkExperienceById(workExperience.getWorkExperienceId());
    studentsPublicDao.deleteStudentByPublicId(5);
  }

  @Test
  public void findWorkExperienceByIdTest() {
    assertTrue(workExperiencesPublicDao.findWorkExperienceById(
            workExperience.getWorkExperienceId()).getCoop().equals("Google"));
  }
}