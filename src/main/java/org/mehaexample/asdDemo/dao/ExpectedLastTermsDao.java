package org.mehaexample.asdDemo.dao;

import java.util.List;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.ExpectedLastTerms;

public class ExpectedLastTermsDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentsDao;
  private TermsDao termsDao;

  public ExpectedLastTermsDao() {
    studentsDao = new StudentsDao();
    termsDao = new TermsDao();
    try {
      // it will check the hibernate.cfg.xml file and load it
      // next it goes to all table files in the hibernate file and loads them
      factory = new Configuration().configure().buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Failed to create sessionFactory object." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }

  public List<ExpectedLastTerms> getAllExpectedLastTerms() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from ExpectedLastTerms ");
    List<ExpectedLastTerms> list = query.list();
    session.close();
    for (ExpectedLastTerms lastTerm : list) {
      populateForeignKey(lastTerm);
    }
    return list;
  }

  public ExpectedLastTerms getExpectedLastTermById(int id) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from ExpectedLastTerms where ExpectedLastTermId = :id");
    query.setParameter("id", id);
    List<ExpectedLastTerms> list = query.list();
    session.close();
    if (list.isEmpty()) {
      return null;
    }
    ExpectedLastTerms expectedLastTerm = list.get(0);
    populateForeignKey(expectedLastTerm);
    return expectedLastTerm;
  }

  public ExpectedLastTerms updateExpectedLastTermRecord(ExpectedLastTerms expectedLastTerm) {
    if (expectedLastTerm == null) {
      return null;
    }

    Transaction tx = null;
    StudentsDao studentDaoHibernate = new StudentsDao();
    session = factory.openSession();

    if (studentDaoHibernate.ifNuidExists(expectedLastTerm.getStudent().getNeuId())) {
      try {
        tx = session.beginTransaction();
        session.saveOrUpdate(expectedLastTerm);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
      } finally {
        session.close();
      }
    } else {
      System.out.println("The student with a given nuid doesn't exists");
    }

    return expectedLastTerm;
  }

  public ExpectedLastTerms addExpectedLastTermRecord(ExpectedLastTerms expectedLastTerm) {
    if (expectedLastTerm == null) {
      return null;
    }

    session = factory.openSession();

    Transaction tx = null;
    StudentsDao studentDaoHibernate = new StudentsDao();

    if (studentDaoHibernate.ifNuidExists(expectedLastTerm.getStudent().getNeuId())) {
      try {
        tx = session.beginTransaction();
        session.save(expectedLastTerm);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
      } finally {
        session.close();
      }
    } else {
      System.out.println("The student with a given nuid doesn't exists");
    }

    return expectedLastTerm;
  }

  public boolean deleteExpectedLastTermRecord(int id) {
    Transaction tx = null;
    session = factory.openSession();

    try {
      tx = session.beginTransaction();
      ExpectedLastTerms expectedLastTerm = session.get(ExpectedLastTerms.class, id);
      System.out.println("Deleting student for id = " + id);
      session.delete(expectedLastTerm);
      tx.commit();
    } catch (HibernateException e) {
      System.out.println("exceptionnnnnn");
      if (tx != null) tx.rollback();
      e.printStackTrace();
    } finally {
      session.close();
    }

    return true;
  }

  private void populateForeignKey(ExpectedLastTerms expectedLastTerms) {
    expectedLastTerms.setStudent(studentsDao.getStudentRecord(expectedLastTerms.getStudent().getNeuId()));
    expectedLastTerms.setTerm(termsDao.getTermById(expectedLastTerms.getTerm().getTermId()));
  }
}
