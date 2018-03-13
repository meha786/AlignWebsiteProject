package org.mehaexample.asdDemo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.AdminLogins;

import java.util.InputMismatchException;
import java.util.List;

public class AdminLoginsDao {
  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor.
   */
  public AdminLoginsDao() {
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public AdminLogins findAdminLoginsByEmail(String email) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM AdminLogins WHERE email = :email ");
    query.setParameter("email", email);
    List list = query.list();
    session.close();
    if (list.isEmpty()) {
      return null;
    }
    return (AdminLogins) list.get(0);
  }

  public AdminLogins createAdminLogin(AdminLogins adminLogin) {
    Transaction tx = null;
    if (findAdminLoginsByEmail(adminLogin.getEmail()) == null) {
      System.out.println("Admin Login already exists!");
    } else {
      System.out.println("Saving admin login...");
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.save(adminLogin);
        tx.commit();
      } catch (HibernateException e) {
        System.out.println("HibernateException: " + e);
        if (tx != null) tx.rollback();
        throw new HibernateException("Connecting went wrong, cannot save admin login.");
      } finally {
        session.close();
      }
    }
    return adminLogin;
  }

  public boolean updateAdminLogin(AdminLogins adminLogin) {
    Transaction tx = null;
    boolean updated;
    if (findAdminLoginsByEmail(adminLogin.getEmail()) != null) {
      try {
        Session session = factory.openSession();
        tx = session.beginTransaction();
        session.saveOrUpdate(adminLogin);
        tx.commit();
        updated = true;
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connecting went wrong, cannot save admin login.");
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Admin Login with email: " + adminLogin.getEmail() +
              " not found.");
    }
    return updated;
  }

  public boolean deleteAdminLogin(String email) {
    boolean deleted = false;

    if (email == null || email.trim().isEmpty()) {
      throw new InputMismatchException("Email argument cannot be null or empty.");
    }

    AdminLogins adminLogin = findAdminLoginsByEmail(email);
    if (adminLogin != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(adminLogin);
        tx.commit();
        deleted = true;
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connecting went wrong, cannot save admin login.");
      } finally {
        session.close();
      }
    }
    return deleted;
  }
}