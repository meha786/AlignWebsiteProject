package org.mehaexample.asdDemo.dao.alignadmin;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.Constants;
import org.mehaexample.asdDemo.dao.alignprivate.StudentsDao;
import org.mehaexample.asdDemo.model.alignadmin.ElectivesAdmin;

public class ElectivesAdminDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentDao;

  /**
   * Default Constructor.
   */
  public ElectivesAdminDao() {
    studentDao = new StudentsDao();
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration()
              .configure("/hibernate_Admin.cfg.xml").buildSessionFactory();
    } catch (ExceptionInInitializerError ex) {
      throw new ExceptionInInitializerError(ex);

    }
  }

  public List<ElectivesAdmin> getElectivesByNeuId(String neuId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from ElectivesAdmin where neuId = :neuId");
    query.setParameter("neuId", neuId);
    List<ElectivesAdmin> list = query.list();
    session.close();
    return list;
  }

  public ElectivesAdmin getElectiveById(int electiveId) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from ElectivesAdmin where electiveId = :electiveId");
    query.setParameter("electiveId", electiveId);
    List<ElectivesAdmin> list = query.list();
    session.close();
    if (list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  /**
   * This is the function to add an Elective for a given student into database.
   *
   * @param elective elective to be added; not null.
   * @return true if insert successfully. Otherwise throws exception.
   */
  public ElectivesAdmin addElective(ElectivesAdmin elective) {
    if (elective == null) {
      return null;
    }

    Transaction tx = null;
    session = factory.openSession();

    if (studentDao.ifNuidExists(elective.getNeuId())) {
      try {
        tx = session.beginTransaction();
        session.save(elective);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("The student with a given nuid doesn't exists");
    }
    return elective;
  }

  public boolean updateElectives(ElectivesAdmin elective) {
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.saveOrUpdate(elective);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
    } finally {
      session.close();
    }
    return true;
  }

  public boolean deleteElectiveRecord(int id) {
    Transaction tx = null;

    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      ElectivesAdmin electives = session.get(ElectivesAdmin.class, id);
      session.delete(electives);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
    } finally {
      session.close();
    }

    return true;
  }

  public boolean deleteElectivesByNeuId(String neuId) {
    Transaction tx = null;
    boolean deleted = false;

    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("DELETE FROM ElectivesAdmin " +
              "WHERE neuId = :neuId ");
      query.setParameter("neuId", neuId);
      query.executeUpdate();
      tx.commit();
      deleted = true;
    } catch (HibernateException e) {
      if (tx!=null) tx.rollback();
      throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
    } finally {
      session.close();
    }

    return deleted;
  }
}