package alignWebsite;

import java.util.List;


import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.CoursesDao;
import org.mehaexample.asdDemo.dao.ElectivesDao;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.dao.TermsDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.enums.Term;
import org.mehaexample.asdDemo.enums.TermType;
import org.mehaexample.asdDemo.model.Courses;
import org.mehaexample.asdDemo.model.Electives;
import org.mehaexample.asdDemo.model.Students;
import org.mehaexample.asdDemo.model.Terms;

public class ElectiveDaoTest {
  private static ElectivesDao electivesDao;
  private static StudentsDao studentsDao;
  private static CoursesDao coursesDao;
  private static TermsDao termsDao;

  @BeforeClass
  public static void init() {
    electivesDao = new ElectivesDao();
    studentsDao = new StudentsDao();
    coursesDao = new CoursesDao();
    termsDao = new TermsDao();
  }

  @Test
  public void addNullElectivesTest() {
    Electives Electives = electivesDao.addElective(null);
    Assert.assertNull(Electives);
  }

  @Test
  public void getAllElectives() {
    List<Electives> electivesList = electivesDao.getAllElectives();

    System.out.println(electivesList.size());
  }

  @Test
  public void addElectivesTest() {
    int tempId = 1221;

    Students newStudent = new Students(tempId + "", "tomcat78@gmail.com", "Tom3", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null);

    studentsDao.addStudent(newStudent);

    Courses newCourse = new Courses(tempId + "", "course2", "course description 2");
    Courses courses = coursesDao.createCourse(newCourse);

    Terms newTerm = new Terms(Term.FALL, tempId, TermType.QUARTER);
    Terms term = termsDao.addTerm(newTerm);

    Electives elective = new Electives();
    elective.setStudent(newStudent);
    elective.setCourse(newCourse);
    elective.setTerms(newTerm);
    elective.setRetake(false);
    elective.setGpa((float) 3.2);
    elective.setPlagiarism(false);

    Electives electivesNew = electivesDao.addElective(elective);

    electivesDao.deleteElectiveRecord(electivesNew.getElectiveId());
    coursesDao.deleteCourseById(tempId + "");
    termsDao.deleteTerm(term.getTermId());
    studentsDao.deleteStudent(tempId + "");
  }

  @Test
  public void deleteElectivesTest() {
    int tempId = 289;

    List<Electives> experiencesOld = electivesDao.getAllElectives();
    int oldSize = experiencesOld.size();

    Students newStudent = new Students(tempId + "", "tomcat2e1kk3@gmail.com", "Tom3", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null);

    studentsDao.addStudent(newStudent);

    Courses newCourse = new Courses(tempId + "", "course2", "course description 2");
    Courses courses = coursesDao.createCourse(newCourse);

    Terms newTerm = new Terms(Term.FALL, tempId, TermType.QUARTER);
    Terms term = termsDao.addTerm(newTerm);

    Electives elective = new Electives();
    elective.setStudent(newStudent);
    elective.setCourse(newCourse);
    elective.setTerms(newTerm);
    elective.setRetake(false);
    elective.setGpa((float) 3.2);
    elective.setPlagiarism(false);

    Electives electivesNew = electivesDao.addElective(elective);
    electivesDao.deleteElectiveRecord(electivesNew.getElectiveId());

    List<Electives> electivessNew = electivesDao.getAllElectives();
    int newSize = electivessNew.size();
    Assert.assertEquals(oldSize, newSize);

    coursesDao.deleteCourseById(tempId + "");
    termsDao.deleteTerm(term.getTermId());
    studentsDao.deleteStudent(tempId + "");
  }

  //
  @Test
  public void updateElectivesTest() {
    int tempId = 9187;

    Students newStudent = new Students(tempId + "", "tommcautty@gmail.com", "Tom3", "",
            "Cat", Gender.M, "F1", "1111111111",
            "401 Terry Ave", "WA", "Seattle", "98109",
            EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS, null);

    studentsDao.addStudent(newStudent);

    Courses newCourse = new Courses(tempId + "", "course2", "course description 2");
    Courses courses = coursesDao.createCourse(newCourse);

    Terms newTerm = new Terms(Term.FALL, tempId, TermType.QUARTER);
    Terms term = termsDao.addTerm(newTerm);

    Electives elective = new Electives();
    elective.setStudent(newStudent);
    elective.setCourse(newCourse);
    elective.setTerms(newTerm);
    elective.setRetake(false);
    elective.setGpa((float) 3.2);
    elective.setPlagiarism(false);

    Electives electivesNew = electivesDao.addElective(elective);

    electivesNew.setGpa((float) 4.2332);
    electivesDao.updateElectives(electivesNew);
    Assert.assertEquals(electivesNew.getGpa(), ((float) 4.2332), 0.1);

    electivesDao.deleteElectiveRecord(electivesNew.getElectiveId());
    coursesDao.deleteCourseById(tempId + "");
    termsDao.deleteTerm(term.getTermId());
    studentsDao.deleteStudent(tempId + "");
  }


}
