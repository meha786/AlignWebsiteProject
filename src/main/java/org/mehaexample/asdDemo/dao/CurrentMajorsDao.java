package org.mehaexample.asdDemo.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.CurrentMajors;

import java.util.List;

public class CurrentMajorsDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentsDao;
  private MajorsDao majorsDao;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public CurrentMajorsDao() {
    studentsDao = new StudentsDao();
    majorsDao = new MajorsDao();
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Find a Current Major by the Current Major Id.
   * This method searches the current major from the private database.
   *
   * @param currentMajorId current major Id in private database.
   * @return Current major if found, null if not found.
   */
  public CurrentMajors getCurrentMajorById(int currentMajorId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM CurrentMajors WHERE currentMajorId = :currentMajorId");
    query.setParameter("currentMajorId", currentMajorId);
    List<CurrentMajors> listOfCurrentMajor = query.list();
    if (listOfCurrentMajor.isEmpty()) {
      return null;
    }
    CurrentMajors currentMajors = listOfCurrentMajor.get(0);
    populateForeignKey(currentMajors);
    session.close();
    return currentMajors;
  }

  /**
   * Find current major records of a student in private DB.
   *
   * @param neuId the neu Id of a student; not null.
   * @return List of Prior Educations if neu Id found, null if Neu Id not found.
   */
  public List<CurrentMajors> getCurrentMajorsByNeuId(String neuId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery(
            "FROM CurrentMajors WHERE student.neuId = :neuId");
    query.setParameter("neuId", neuId);
    List<CurrentMajors> listOfCurrentMajor = query.list();
    if (listOfCurrentMajor.isEmpty()) {
      return null;
    }
    for (CurrentMajors currentMajor : listOfCurrentMajor) {
      populateForeignKey(currentMajor);
    }
    session.close();
    return listOfCurrentMajor;
  }

  /**
   * Create a current in the private database.
   * This function requires the Student and major
   * object inside the prior education object to be not null.
   *
   * @param currentMajor the curent major object to be created; not null.
   */
  public void createCurrentMajor(CurrentMajors currentMajor) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      System.out.println("saving current major"
              + " in Current Major table");
      session.save(currentMajor);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
    } finally {
      session.close();
    }
  }

  /**
   * Delete a current major in the private database.
   *
   * @param currentMajorId the currentMajor Id to be deleted.
   * @return true if current major is deleted, false otherwise.
   */
  public boolean deleteCurrentMajorById(int currentMajorId) {
    CurrentMajors currentMajor = getCurrentMajorById(currentMajorId);
    if (currentMajor != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting current major with id = " + currentMajor.getCurrentMajorId());
        session.delete(currentMajor);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
        return false;
      } finally {
        session.close();
      }
      return true;
    }
    return false;
  }

  /**
   * Update a current major in the private DB.
   *
   * @param currentMajor current major object; not null.
   * @return true if the current major is updated, false otherwise.
   */
  public boolean updateCurrentMajor(CurrentMajors currentMajor) {
    if (getCurrentMajorById(currentMajor.getCurrentMajorId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating current major in Current Majors table...");
        session.saveOrUpdate(currentMajor);
        tx.commit();
      } catch (HibernateException e) {
        System.out.println("HibernateException: " + e);
        if (tx != null) tx.rollback();
        return false;
      } finally {
        session.close();
      }
      return true;
    }
    return false;
  }

  private void populateForeignKey(CurrentMajors currentMajors) {
    currentMajors.setStudent(studentsDao.getStudentRecord(currentMajors.getStudent().getNeuId()));
    currentMajors.setMajor(majorsDao.getMajorById(currentMajors.getMajor().getMajorId()));
  }
}
