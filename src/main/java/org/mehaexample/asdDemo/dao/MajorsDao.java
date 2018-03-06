package org.mehaexample.asdDemo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.Majors;

import java.util.List;

public class MajorsDao {
  private static SessionFactory factory;
  private static Session session;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public MajorsDao() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Get list of all majors from the private database.
   *
   * @return list of All Majors from private database.
   */
  public List<Majors> getAllMajors() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Majors ");
    List<Majors> listOfMajors = query.list();
    session.close();
    return listOfMajors;
  }

  /**
   * Get a major from the private database from their
   * major Id.
   *
   * @param majorId Major Id that wants to be found.
   * @return major if found, null if not found.
   */
  public Majors getMajorById(int majorId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Majors WHERE majorId = :majorId");
    query.setParameter("majorId", majorId);
    List listOfMajors = query.list();
    if (listOfMajors.isEmpty()) {
      return null;
    }
    session.close();
    return (Majors) listOfMajors.get(0);
  }

  /**
   * Get a major from the private database from their name.
   *
   * @param major major Name that wants to be found.
   * @return major if found, null if not found.
   */
  public Majors getMajorByName(String major) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Majors WHERE major = :major");
    query.setParameter("major", major);
    List listOfMajors = query.list();
    if (listOfMajors.isEmpty()) {
      return null;
    }
    session.close();
    return (Majors) listOfMajors.get(0);
  }

  /**
   * Create a new major in the private database.
   *
   * @param major the major (model) that wants to be
   *                inserted into the private database.
   */
  public void createMajor(Majors major) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      System.out.println("saving " + major.getMajor() + " in Majors table");
      session.save(major);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
    } finally {
      session.close();
    }
  }

  /**
   * Delete a major from the private database based
   * on the major Id.
   *
   * @param majorId major Id that wants to be deleted.
   * @return true if major is deleted,
   * false if major is not found
   */
  public boolean deleteMajorById(int majorId) {
    Majors major = getMajorById(majorId);
    if (major != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting major with name = " + major.getMajor());
        session.delete(major);
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
   * Update a major based on the provided major model.
   *
   * @param major full major model (including the majorId).
   * @return true if major is successfully updated, false if
   * the specific major is not found.
   */
  public boolean updateMajor(Majors major) {
    if (getMajorById(major.getMajorId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating major in Majors table...");
        session.saveOrUpdate(major);
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
}
