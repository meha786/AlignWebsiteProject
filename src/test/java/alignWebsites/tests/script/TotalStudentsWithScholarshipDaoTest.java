package alignWebsites.tests.script;

import org.mehaexample.asdDemo.script.TotalStudentsWithScholarshipDao;
import org.junit.Before;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.StudentsDao;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.model.Students;

import junit.framework.Assert;

public class TotalStudentsWithScholarshipDaoTest {


    public static final int TOTAL_STUDENTS_WITH_SCHOLARSHIP_TEST = 36;

    TotalStudentsWithScholarshipDao totalStudentsWithScholarishipDao;
    StudentsDao studentsDao;

    @Before
    public void init() throws Exception {
        totalStudentsWithScholarishipDao = TotalStudentsWithScholarshipDao.getInstance();
        studentsDao = new StudentsDao();
    }

    @Test
    public void getTotalStudentsWithScholarshipFromPrivateDatabase() throws Exception {
        int totalStudentWithScholarship = totalStudentsWithScholarishipDao.getTotalStudentsWithScholarshipFromPrivateDatabase();
        Students newStudent = new Students("0000000","tomcat@gmail.com", "Tom", "",
                "Cat", Gender.M, "F1", "1111111111",
                "401 Terry Ave", "WA", "Seattle", "98109",
                EnrollmentStatus.FULL_TIME, Campus.SEATTLE, DegreeCandidacy.MASTERS,null);

        newStudent.setScholarship(true);
        studentsDao.addStudent(newStudent);
        int totalStudentWithScholarship_incre = totalStudentsWithScholarishipDao.getTotalStudentsWithScholarshipFromPrivateDatabase();
        studentsDao.deleteStudent("0000000");

        Assert.assertTrue(totalStudentWithScholarship == totalStudentWithScholarship_incre - 1);
    }

    @Test
    public void updateTotalStudentsWithScholarshipInPublicDatabase() throws Exception {
        int totalStudentWithScholarship = totalStudentsWithScholarishipDao.getTotalStudentsWithScholarshipFromPublicDatabase();

        totalStudentsWithScholarishipDao.updateTotalStudentsWithScholarshipInPublicDatabase(
                TOTAL_STUDENTS_WITH_SCHOLARSHIP_TEST);
        Assert.assertTrue(totalStudentsWithScholarishipDao.getTotalStudentsWithScholarshipFromPublicDatabase()
                == TOTAL_STUDENTS_WITH_SCHOLARSHIP_TEST);

        totalStudentsWithScholarishipDao.updateTotalStudentsWithScholarshipInPublicDatabase(
                totalStudentWithScholarship);
    }

    @Test
    public void getTotalStudentsWithScholarshipFromPublicDatabase() throws Exception {
        int totalStudentWithScholarship = totalStudentsWithScholarishipDao.getTotalStudentsWithScholarshipFromPublicDatabase();

        totalStudentsWithScholarishipDao.updateTotalStudentsWithScholarshipInPublicDatabase(
                TOTAL_STUDENTS_WITH_SCHOLARSHIP_TEST);
        Assert.assertTrue(totalStudentsWithScholarishipDao.getTotalStudentsWithScholarshipFromPublicDatabase()
                == TOTAL_STUDENTS_WITH_SCHOLARSHIP_TEST);

        totalStudentsWithScholarishipDao.updateTotalStudentsWithScholarshipInPublicDatabase(
                totalStudentWithScholarship);
    }
}