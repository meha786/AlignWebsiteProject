package org.mehaexample.asdDemo.dao.alignprivate;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.Constants;
import org.mehaexample.asdDemo.model.alignprivate.Courses;

public class CoursesDao {

  private static SessionFactory factory;
  private static Session session;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public CoursesDao() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Get list of all companies from the private database.
   *
   * @return list of All companies from private database.
   */
  public List<Courses> getAllCourses() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Courses");
    List<Courses> listOfCourses = query.list();

    session.close();
    return listOfCourses;
  }

  /**
   * Get a course from the private database from their
   * course Id.
   *
   * @param courseId Id that wants to be found.
   * @return Course corresponding to the course Id, null otherwise.
   */
  public Courses getCourseById(String courseId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Courses WHERE courseId = :courseId");
    query.setParameter("courseId", courseId);
    List listOfCourses = query.list();
    session.close();
    if (listOfCourses.isEmpty()) {
      return null;
    }
    return (Courses) listOfCourses.get(0);
  }

  /**
   * Get a company from the private database from their name.
   *
   * @param courseName company Name that wants to be found.
   * @return company if found, null if not found.
   */
  public List<Courses> getCoursesByName(String courseName) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Courses WHERE courseName = :courseName");
    query.setParameter("courseName", courseName);
    List<Courses> listOfCourses = query.list();
    session.close();

    return listOfCourses;
  }

  /**
   * Create a new course in private database
   *
   * @param course course object
   * @return Course if found, null if an error happen.
   */
  public Courses createCourse(Courses course) {
    if (course == null) {
      return null;
    }

    if (getCourseById(course.getCourseId()) != null) {
      return course;
    }

    session = factory.openSession();
    Transaction tx = null;
    System.out.println("saving " + course.getCourseName() + " in Courses table");
    try {
      tx = session.beginTransaction();
      session.save(course);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
      throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
    } finally {
      session.close();
    }

    return course;
  }

  /**
   * Delete a course from the private database based
   * on the company Id.
   *
   * @param courseId course Id.
   * @return true if course is deleted, false otherwise.
   */
  public boolean deleteCourseById(String courseId) {
    boolean deleted = false;

    if (courseId == null || courseId.isEmpty()) {
      return deleted;
    }

    Courses course = getCourseById(courseId);
    if (course != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting course with name = " + course.getCourseName());
        session.delete(course);
        tx.commit();
        deleted = true;
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
      } finally {
        session.close();
      }
    }

    return deleted;
  }

  /**
   * Update a course based on the provided company model.
   *
   * @param course course object.
   * @return true if course is updated, false otherwise.
   */
  public boolean updateCourse(Courses course) {
    boolean updated = false;

    if (getCourseById(course.getCourseId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      System.out.println("updating company in Companies table...");
      try {
        tx = session.beginTransaction();
        session.saveOrUpdate(course);
        tx.commit();
        updated = true;
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
      } finally {
        session.close();
      }
    }

    return updated;
  }

}