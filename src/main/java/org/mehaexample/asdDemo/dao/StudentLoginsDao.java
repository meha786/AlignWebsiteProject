package org.mehaexample.asdDemo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.StudentLogins;

import java.util.InputMismatchException;
import java.util.List;

public class StudentLoginsDao {
  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor.
   */
  public StudentLoginsDao() {
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public StudentLogins findStudentLoginsByEmail(String email) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("FROM StudentLogins WHERE email = :email ");
    query.setParameter("email", email);
    List list = query.list();
    session.close();
    if (list.isEmpty()) {
      return null;
    }
    return (StudentLogins) list.get(0);
  }

  public StudentLogins createStudentLogin(StudentLogins studentLogin) {
    Transaction tx = null;
    if (findStudentLoginsByEmail(studentLogin.getEmail()) == null) {
      System.out.println("Student Login already exists!");
    } else {
      System.out.println("Saving student login...");
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.save(studentLogin);
        tx.commit();
      } catch (HibernateException e) {
        System.out.println("HibernateException: " + e);
        if (tx != null) tx.rollback();
        throw new HibernateException("Connecting went wrong, cannot save student login.");
      } finally {
        session.close();
      }
    }
    return studentLogin;
  }

  public boolean updateStudentLogin(StudentLogins studentLogin) {
    Transaction tx = null;
    if (findStudentLoginsByEmail(studentLogin.getEmail()) != null) {
      try {
        Session session = factory.openSession();
        tx = session.beginTransaction();
        session.saveOrUpdate(studentLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connecting went wrong, cannot save student login.");
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Student Login with email: " + studentLogin.getEmail() +
              " not found.");
    }
    return true;
  }

  public boolean deleteStudentLogin(String email) {
    if (email == null || email.trim().isEmpty()) {
      throw new InputMismatchException("Email argument cannot be null or empty.");
    }

    StudentLogins studentLogin = findStudentLoginsByEmail(email);
    if (studentLogin != null) {
      session = factory.openSession();
      Transaction tx = null;
      try {
        tx = session.beginTransaction();
        session.delete(studentLogin);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException("Connecting went wrong, cannot save student login.");
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Student Login with email: " + email +
              " not found.");
    }
    return true;
  }
}