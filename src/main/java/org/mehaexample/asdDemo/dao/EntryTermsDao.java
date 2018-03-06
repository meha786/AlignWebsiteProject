package org.mehaexample.asdDemo.dao;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.EntryTerms;

public class EntryTermsDao {
  private static SessionFactory factory;
  private static Session session;

  private StudentsDao studentsDao;
  private TermsDao termsDao;

  public EntryTermsDao() {
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

  public List<EntryTerms> getAllEntryTerms() {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from EntryTerms");
    List<EntryTerms> list = query.list();
    for (EntryTerms entryTerm : list) {
      populateForeignKey(entryTerm);
    }
    session.close();
    return list;
  }

  public EntryTerms getEntryTermById(int id) {
    session = factory.openSession();
    org.hibernate.query.Query query = session.createQuery("from EntryTerms where experienceId = :id");
    query.setParameter("id", id);
    List<EntryTerms> list = query.list();
    if (list.isEmpty()) {
      return null;
    }
    EntryTerms entryTerm = list.get(0);
    populateForeignKey(entryTerm);
    session.close();
    return entryTerm;
  }

  public EntryTerms updateEntryTerm(EntryTerms entryTerms) {
    if (entryTerms == null) {
      return null;
    }

    Transaction tx = null;
    StudentsDao studentDaoHibernate = new StudentsDao();
    session = factory.openSession();

    if (studentDaoHibernate.ifNuidExists(entryTerms.getStudent().getNeuId())) {
      try {
        tx = session.beginTransaction();
        session.saveOrUpdate(entryTerms);
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

    return entryTerms;
  }

  public EntryTerms addEntryTerm(EntryTerms entryTerm) {
    if (entryTerm == null) {
      return null;
    }

    Transaction tx = null;
    StudentsDao studentDaoHibernate = new StudentsDao();
    session = factory.openSession();

    if (studentDaoHibernate.ifNuidExists(entryTerm.getStudent().getNeuId())) {
      try {
        tx = session.beginTransaction();
        session.save(entryTerm);
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

    return entryTerm;
  }

  public boolean deleteEntryTerms(int id) {
    Transaction tx = null;

    try {
      session = factory.openSession();

      tx = session.beginTransaction();
      EntryTerms experience = session.get(EntryTerms.class, id);
      System.out.println("Deleting student for id = " + id);
      session.delete(experience);
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

  private void populateForeignKey(EntryTerms entryTerms) {
    entryTerms.setStudent(studentsDao.getStudentRecord(entryTerms.getStudent().getNeuId()));
    entryTerms.setTerms(termsDao.getTermById(entryTerms.getTerms().getTermId()));
  }
}