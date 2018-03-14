package org.mehaexample.asdDemo.dao.alignadmin;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.alignadmin.AdministratorNotes;

import java.util.List;

public class AdministratorNotesDao {
    private static SessionFactory factory;
    private static Session session;

    /**
     * Default Constructor
     */
    public AdministratorNotesDao() {
        try {
            // it will check the hibernate.cfg.xml file and load it
            // next it goes to all table files in the hibernate file and loads them
            factory = new Configuration()
                    .configure("/hibernate_Admin.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
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
            query.setParameter("neuId", neuId);
            List<AdministratorNotes> list = query.list();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
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
            List<AdministratorNotes> list = query.list();
            return list;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
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
            e.printStackTrace();
            if (tx!=null) tx.rollback();
            note = null;
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
        String neuId = note.getNeuId();
        boolean deleted = false;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM AdministratorNotes " +
                    "WHERE neuId = :neuId ");
            query.setParameter("neuId", neuId);
            query.executeUpdate();
            tx.commit();
            deleted = true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return deleted;
    }

    /**
     * Check if an Administrator exists for a specific neu id.
     * @param neuId Student Neu Id
     * @return true if exists. Return false if not.
     */
    public boolean ifNuidExists(String neuId) {
        boolean find = false;
        try{
            session = factory.openSession();
            org.hibernate.query.Query query = session.createQuery("FROM AdministratorNotes WHERE neuId = :neuId");
            query.setParameter("neuId", neuId);
            List list = query.list();
            if(list.size() != 0){
                find = true;
            }
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return find;
    }
}