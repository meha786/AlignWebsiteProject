package org.mehaexample.asdDemo.dao.alignadmin;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.Constants;
import org.mehaexample.asdDemo.model.alignadmin.Administrators;

public class AdministratorsDao {
  private static final String ADMIN_EXIST_ERROR = "Admin already exists.";

  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor.
   */
  public AdministratorsDao() {
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration()
              .configure("/hibernate_Admin.cfg.xml").buildSessionFactory();
    } catch (ExceptionInInitializerError ex) {
      throw new ExceptionInInitializerError(ex);

    }
  }

  /**
   * This is the function to add an Administrator into database.
   *
   * @param administrators administrator object.
   * @return true if insert successfully. Otherwise throws exception.
   */
  public Administrators addAdministrator(Administrators administrators) {
    if (administrators == null) {
      return null;
    }

    Transaction tx = null;

    if (ifAdminNuidExists(administrators.getAdministratorNeuId())) {
      throw new HibernateException(ADMIN_EXIST_ERROR);
    } else {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.save(administrators);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
      } finally {
        session.close();
      }
    }
    return administrators;
  }

  /**
   * Get all the Administrators records from database.
   *
   * @return A list of Administrators
   */
  public List<Administrators> getAllAdminstrators() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM Administrators");
    List<Administrators> list = query.list();
    session.close();

    return list;
  }

  /**
   * Search a single Administrator record using adminNeuId.
   *
   * @param adminNeuId administrator Neu Id
   * @return an Administrators object
   */
  public Administrators getAdministratorRecord(String adminNeuId) {
    session = factory.openSession();

    org.hibernate.query.Query query = session.createQuery("FROM Administrators"
            + " WHERE administratorNeuId = :administratorNeuId ");
    query.setParameter("administratorNeuId", adminNeuId);
    List list = query.list();
    session.close();

    if (list.isEmpty()) {
      return null;
    }
    return (Administrators) list.get(0);
  }

  /**
   * Update a Administrator record.
   *
   * @param administrator which contains the latest information.
   * @return true if update successfully. Otherwise, return false.
   */
  public boolean updateAdministratorRecord(Administrators administrator) {
    Transaction tx = null;
    boolean updated = false;

    String administratorNeuId = administrator.getAdministratorNeuId();

    if (ifAdminNuidExists(administratorNeuId)) {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.saveOrUpdate(administrator);
        tx.commit();
        updated = true;
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Student id does not exist.");
    }

    return updated;
  }


  /**
   * Delete an Administrator record from database.
   *
   * @param administratorNeuId administrator Neu Id.
   * @return true if delete successfully. Otherwise, false.
   */
  public boolean deleteAdministrator(String administratorNeuId) {
    boolean deleted = false;
    if (administratorNeuId == null || administratorNeuId.isEmpty()) {
      return false;
    }

    Administrators admin = getAdministratorRecord(administratorNeuId);
    Transaction tx = null;
    if (admin != null) {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.delete(admin);
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
   * Check if a specific Administrator existed in database based on adminNeuId.
   *
   * @param adminNeuId administrator Neu Id.
   * @return true if existed, false if not.
   */
  public boolean ifAdminNuidExists(String adminNeuId) {
    boolean find = false;
    try {
      session = factory.openSession();

      org.hibernate.query.Query query = session.createQuery("FROM Administrators "
              + "WHERE administratorNeuId = :administratorNeuId");
      query.setParameter("administratorNeuId", adminNeuId);
      List list = query.list();

      session.close();
      if (!list.isEmpty()) {
        find = true;
      }
    } catch (HibernateException e) {
      throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
    }

    return find;
  }
}