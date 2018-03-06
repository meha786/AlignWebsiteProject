package org.mehaexample.asdDemo.dao;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.AdministratorNotes;

import java.util.List;

public class AdministratorNotesDao {
    private static SessionFactory factory;
    private static Session session;

    private StudentsDao studentsDao;
    private AdministratorsDao adminDao;

    /**
     * Default Constructor
     */
    public AdministratorNotesDao() {
        studentsDao = new StudentsDao();
        adminDao = new AdministratorsDao();

        try {
            // it will check the hibernate.cfg.xml file and load it
            // next it goes to all table files in the hibernate file and loads them
            factory = new Configuration().configure().buildSessionFactory();
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
            org.hibernate.query.Query query = session.createQuery("FROM AdministratorNotes WHERE student.neuId = :neuId ");
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
                    "WHERE admin.administratorNeuId = :administratorNeuId ");
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
     * @return Newly inserted Administrator Note
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
        String neuId = note.getStudent().getNeuId();
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM AdministratorNotes " +
                    "WHERE student.neuId = :neuId ");
            query.setParameter("neuId", neuId);
            query.executeUpdate();
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return false;
    }

    /**
     * Check if an Administrator exists for a specific neu id.
     * @param neuId Student Neu Id
     * @return true if exists. Return false if not.
     */
    public boolean ifNuidExists(String neuId) {
        try{
            session = factory.openSession();
            org.hibernate.query.Query query = session.createQuery("FROM AdministratorNotes WHERE student.neuId = :neuId");
            query.setParameter("neuId", neuId);
            List list = query.list();
            if(list.size() != 0){
                return true;
            }
        }catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return false;
    }
}
