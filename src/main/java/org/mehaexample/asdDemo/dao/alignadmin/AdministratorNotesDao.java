package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignadmin.AdministratorNotes;

import java.util.List;

public class AdministratorNotesDao {
  private static final String NEU_ID = "neuId";

  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor
   */
  public AdministratorNotesDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    factory = new Configuration()
            .configure("/hibernate_Admin.cfg.xml").buildSessionFactory();
  }

  /**
   * Search for a list of Administrator Note by neu Id.
   *
   * @param neuId Student Neu Id
   * @return A list of Administrator Notes
   */
  public List<AdministratorNotes> getAdministratorNoteRecordByNeuId(String neuId) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM AdministratorNotes WHERE neuId = :neuId ");
      query.setParameter(NEU_ID, neuId);
      return (List<AdministratorNotes>) query.list();
    } finally {
      session.close();
    }
  }

  public AdministratorNotes getAdministratorNoteById(int administratorNoteId) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "FROM AdministratorNotes WHERE administratorNoteId = :administratorNoteId ");
      query.setParameter("administratorNoteId", administratorNoteId);
      List<AdministratorNotes> list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return list.get(0);
    } finally {
      session.close();
    }
  }

  /**
   * Search for a list of Administrator Note by administrator Neu Id.
   *
   * @param administratorNeuId Administrator Neu Id
   * @return A list of Administrator Notes
   */
  public List<AdministratorNotes> getAdministratorNoteRecordByAdminNeuId(String administratorNeuId) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM AdministratorNotes " +
              "WHERE administratorNeuId = :administratorNeuId ");
      query.setParameter("administratorNeuId", administratorNeuId);
      return (List<AdministratorNotes>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Insert Administrator Note record into database.
   *
   * @param note Administrator Note to be inserted
   * @return Newly inserted Administrator Note if success. Otherwise, null.
   */
  public AdministratorNotes addAdministratorNoteRecord(AdministratorNotes note) {
    Transaction tx = null;
    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      session.save(note);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }
    return note;
  }

  /**
   * Delete existing Administrator Record.
   *
   * @param note Administrator Note whose administratorNoteId indicates the note to be deleted from database.
   * @return true if delete successfully. Return false if failed.
   */
  public boolean deleteAdministratorNoteRecord(AdministratorNotes note) {
    Transaction tx = null;
    int administratorNoteId = note.getAdministratorNoteId();
    if (getAdministratorNoteById(note.getAdministratorNoteId()) == null) {
      throw new HibernateException("Administrator Note Id does not exist");
    }
    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      org.hibernate.query.Query query = session.createQuery("DELETE FROM AdministratorNotes " +
              "WHERE administratorNoteId = :administratorNoteId ");
      query.setParameter("administratorNoteId", administratorNoteId);
      query.executeUpdate();
      tx.commit();
      return true;
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }
  }

  /**
   * Check if an Administrator exists for a specific neu id.
   *
   * @param neuId Student Neu Id
   * @return true if exists. Return false if not.
   */
  public boolean ifNuidExists(String neuId) {
    boolean find = false;
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM AdministratorNotes WHERE neuId = :neuId");
      query.setParameter(NEU_ID, neuId);
      List list = query.list();
      if (!list.isEmpty()) {
        find = true;
      }
    } finally {
      session.close();
    }

    return find;
  }
}