package org.mehaexample.asdDemo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.Institutions;

import java.util.List;

public class InstitutionsDao {
  private static SessionFactory factory;
  private static Session session;

  /**
   * Default constructor.
   * it will check the Hibernate.cfg.xml file and load it
   * next it goes to all table files in the hibernate file and loads them.
   */
  public InstitutionsDao() {
    try {
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  /**
   * Get list of all institutions from the private database.
   *
   * @return list of All Institutions from private database.
   */
  public List<Institutions> getAllInstitutions() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Institutions ");
    List<Institutions> listOfInstitutions = query.list();
    session.close();
    return listOfInstitutions;
  }

  /**
   * Get an institution from the private database from their
   * institution Id.
   *
   * @param institutionId Institution Id that wants to be found.
   * @return institution if found, null if not found.
   */
  public Institutions getInstitutionById(int institutionId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Institutions WHERE institutionId = :institutionId");
    query.setParameter("institutionId", institutionId);
    List listOfInstitutions = query.list();
    if (listOfInstitutions.isEmpty()) {
      return null;
    }
    session.close();
    return (Institutions) listOfInstitutions.get(0);
  }

  /**
   * Get an institution from the private database from their name.
   *
   * @param institutionName institution Name that wants to be found.
   * @return institution if found, null if not found.
   */
  public Institutions getInstitutionByName(String institutionName) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Institutions WHERE institutionName = :institutionName");
    query.setParameter("institutionName", institutionName);
    List listOfInstitutions = query.list();
    if (listOfInstitutions.isEmpty()) {
      return null;
    }
    session.close();
    return (Institutions) listOfInstitutions.get(0);
  }

  /**
   * Create a new institution in the private database.
   *
   * @param institution the institution (model) that wants to be
   *                inserted into the private database.
   */
  public void createInstitution(Institutions institution) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      System.out.println("saving " + institution.getInstitutionName() + " in Institutions table");
      session.save(institution);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("HibernateException: " + e);
      if (tx != null) tx.rollback();
    } finally {
      session.close();
    }
  }

  /**
   * Delete an institution from the private database based
   * on the institution Id.
   *
   * @param institutionId institution Id that wants to be deleted.
   * @return true if institution is deleted,
   * false if institution is not found
   */
  public boolean deleteInstitutionById(int institutionId) {
    Institutions institution = getInstitutionById(institutionId);
    if (institution != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("Deleting institution with name = " + institution.getInstitutionName());
        session.delete(institution);
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
   * Update an institution based on the provided institution model.
   *
   * @param institution full institution model (including the institutionId).
   * @return true if institution is successfully updated, false if
   * the specific institution is not found.
   */
  public boolean updateInstitution(Institutions institution) {
    if (getInstitutionById(institution.getInstitutionId()) != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        System.out.println("updating institution in Institutions table...");
        session.saveOrUpdate(institution);
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
