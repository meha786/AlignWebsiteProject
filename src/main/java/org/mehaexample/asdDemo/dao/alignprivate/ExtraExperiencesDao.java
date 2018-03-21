package org.mehaexample.asdDemo.dao.alignprivate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.Constants;
import org.mehaexample.asdDemo.model.alignprivate.ExtraExperiences;

import java.util.List;

public class ExtraExperiencesDao {
    private static SessionFactory factory;
    private static Session session;

    /**
     * Default constructor.
     * it will check the Hibernate.cfg.xml file and load it
     * next it goes to all table files in the hibernate file and loads them.
     */
    public ExtraExperiencesDao() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Find an Extra Experience by the Experience Id.
     * This method searches the extra experience from the private database.
     *
     * @param extraExperienceId extra experience Id in private database.
     * @return Extra Experience if found.
     */
    public ExtraExperiences getExtraExperienceById(int extraExperienceId) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery(
                "FROM ExtraExperiences WHERE extraExperienceId = :extraExperienceId");
        query.setParameter("extraExperienceId", extraExperienceId);
        List<ExtraExperiences> listOfExtraExperience = query.list();
        if (listOfExtraExperience.size() == 0)
            return null;
        ExtraExperiences extraExperiences = listOfExtraExperience.get(0);
        session.close();
        return extraExperiences;
    }

    /**
     * Find extra experience records of a student in private DB.
     *
     * @param neuId the neu Id of a student; not null.
     * @return List of Extra Experiences.
     */
    public List<ExtraExperiences> getExtraExperiencesByNeuId(String neuId) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery(
                "FROM ExtraExperiences WHERE neuId = :neuId");
        query.setParameter("neuId", neuId);
        List<ExtraExperiences> listOfExtraExperience = query.list();
        session.close();
        return listOfExtraExperience;
    }

    /**
     * Create a extra experience in the private database.
     * This function requires the StudentsPublic object and the Companies
     * object inside the extra experience object to be not null.
     *
     * @param extraExperience the extra experience object to be created; not null.
     * @return newly created ExtraExperience if success. Otherwise, return null;
     */
    public ExtraExperiences createExtraExperience(ExtraExperiences extraExperience) {
        session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            System.out.println("saving extra experience"
                    + " in ExtraExperiences table");
            session.save(extraExperience);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
        } finally {
            session.close();
        }

        return extraExperience;
    }

    /**
     * Delete a extra experience in the private database.
     *
     * @param extraExperienceId the extra experience Id to be deleted.
     * @return true if extra experience is deleted, false otherwise.
     */
    public boolean deleteExtraExperienceById(int extraExperienceId) {
        boolean deleted = false;
        ExtraExperiences extraExperiences = getExtraExperienceById(extraExperienceId);
        if (extraExperiences != null) {
            session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("Deleting extra experience with id = " + extraExperiences.getExtraExperienceId());
                session.delete(extraExperiences);
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

    public boolean deleteExtraExperienceByNeuId(String neuId) {
        Transaction tx = null;
        boolean deleted = false;

        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM ExtraExperiences " +
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

    /**
     * Update an extra experience in the private DB.
     *
     * @param extraExperience extra experience object; not null.
     * @return true if the extra experience is updated, false otherwise.
     */
    public boolean updateExtraExperience(ExtraExperiences extraExperience) {
        boolean updated = false;

        if (getExtraExperienceById(extraExperience.getExtraExperienceId()) != null) {
            session = factory.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                System.out.println("updating extra experience in ExtraExperiences table...");
                session.saveOrUpdate(extraExperience);
                tx.commit();
                updated = true;
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                throw new HibernateException(Constants.DATABASE_CONNECTION_ERROR);
            } finally {
                session.close();
            }
        }

        return updated;
    }
}
