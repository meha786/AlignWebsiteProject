package alignWebsite;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mehaexample.asdDemo.dao.CoursesDao;
import org.mehaexample.asdDemo.model.Courses;

public class CoursesDaoTest {

	private static CoursesDao coursesDao;

	@BeforeClass
	public static void init() {
		coursesDao = new CoursesDao();
	}

	@Test
	public void addNullcourseTest() {
		Courses Courses = coursesDao.createCourse(null);
		Assert.assertNull(Courses);
	}

	@Test
	public void addCourseTest() {
		Courses newcourse = new Courses("222", "course1", "course description 1");
		Courses courses = coursesDao.createCourse(newcourse);
		Assert.assertTrue(courses.toString().equals(newcourse.toString()));
		coursesDao.deleteCourseById("222");
	}

	@Test
	public void deleteCourseTest() {
		List<Courses> Courses = coursesDao.getAllCourses();
		int oldSize = Courses.size();		
		Courses newcourse = new Courses("111", "course name", "desc");		

		coursesDao.createCourse(newcourse);
		coursesDao.deleteCourseById("111");
		int newSize = Courses.size();	
		Assert.assertEquals(oldSize, newSize); 
	}

	@Test
	public void getAllCoursesTest() {
		List<Courses> courses = coursesDao.getAllCourses();

		System.out.println(courses.size());
		Courses newcourse = new Courses("111", "course2", "course desc");	
		coursesDao.createCourse(newcourse);
		List<Courses> newCourses = coursesDao.getAllCourses();
		Assert.assertTrue(newCourses.size() == courses.size() + 1);
		coursesDao.deleteCourseById("111");
	}

	@Test
	public void deleteNullcourseTest() {
		boolean result = coursesDao.deleteCourseById(null);
		Assert.assertFalse(result);
		
		result = coursesDao.deleteCourseById("");
		Assert.assertFalse(result);
	}
}