package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignadmin.AdminLogins;

import java.util.List;

public class AdminLoginsDao {
  private static final String ADMIN_EXIST_ERROR = "Admin Login already exist.";

  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor.
   */
  public AdminLoginsDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    this.factory = AdminSessionFactory.getFactory();
  }

  public AdminLoginsDao(boolean test) {
    if (test) {
      this.factory = AdminTestSessionFactory.getFactory();
    }
  }

  public AdminLogins findAdminLoginsByEmail(String email) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM AdminLogins WHERE email = :email");
      query.setParameter("email", email);
      List list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return (AdminLogins) list.get(0);
    } finally {
      session.close();
    }
  }

  public AdminLogins createAdminLogin(AdminLogins adminLogin) {
    Transaction tx = null;
    if (findAdminLoginsByEmail(adminLogin.getEmail()) != null) {
      throw new HibernateException(ADMIN_EXIST_ERROR);
    } else {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.save(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    }
    return adminLogin;
  }

  public boolean updateAdminLogin(AdminLogins adminLogin) {
    Transaction tx = null;
    if (findAdminLoginsByEmail(adminLogin.getEmail()) != null) {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.saveOrUpdate(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Admin Login with email: " + adminLogin.getEmail() +
              " not found.");
    }
    return true;
  }

  public boolean deleteAdminLogin(String email) {
    AdminLogins adminLogin = findAdminLoginsByEmail(email);
    if (adminLogin != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Admin Login does not exist.");
    }
    return true;
  }
}