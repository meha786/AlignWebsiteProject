package org.mehaexample.asdDemo.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.model.Privacies;

import java.util.List;

public class PrivaciesDao {
    private static SessionFactory factory;
    private static Session session;

    /**
     * Default Constructor
     */
    public PrivaciesDao() {
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
     * Insert a new Privacy record to Privacy table.
     *
     * @param privacy Privacy record to be inserted
     * @return privacy which is newly added to database. Return null if failed.
     */
    public Privacies addPrivacyRecord(Privacies privacy) {
        Transaction tx = null;

        if(ifNuidExists(privacy.getNeuId())){
            System.out.println("privacy already exists!");
        }else{
            try {
                session = factory.openSession();
                tx = session.beginTransaction();
                session.save(privacy);
                tx.commit();
            } catch (HibernateException e) {
                e.printStackTrace();
                if (tx!=null) tx.rollback();
            } finally {
                    session.close();
            }
        }
        return privacy;
    }

    /**
     * Search for a privacy record by neu id.
     *
     * @param neuId Student Neu Id
     * @return a privacy if it exists, otherwise return null.
     */
    public Privacies getPrivacyRecordByNeuId(String neuId) {
        try {
            session = factory.openSession();
            org.hibernate.query.Query query = session.createQuery("FROM Privacies WHERE NeuId = :neuId ");
            query.setParameter("neuId", neuId);
            List list = query.list();
            if(list.size()==1){
                return (Privacies) list.get(0);
            }else{
                System.out.println("The list should contain only one Privacy with a given neu id");
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    /**
     * Search for a privacy record by privacy id.
     *
     * @param privacyId Privacy Id
     * @return a privacy if it exists, otherwise return null.
     */
    public Privacies getPrivacyRecordByPrivacyId(int privacyId) {
        try {
            session = factory.openSession();
            org.hibernate.query.Query query = session.createQuery("FROM Privacies WHERE PrivacyId = :privacyId ");
            query.setParameter("privacyId", privacyId);
            List list = query.list();
            if(list.size()==1){
                return (Privacies) list.get(0);
            }else{
                System.out.println("The list should contain only one Privacy with a given privacyId");
                return null;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }

        return null;
    }

    /**
     * Update a privacy record.
     *
     * @param privacy Privacy that contains new information. Its privacyId indicates the record
     *                to be updated in database.
     * @return the updated privacy. If privacy doesn't exist, return null;
     */
    public Privacies updatePrivacyRecord(Privacies privacy) {
        Transaction tx = null;
        String neuId = privacy.getNeuId();
        if(ifNuidExists(neuId)){
            try{
                Session session = factory.openSession();
                tx = session.beginTransaction();
                boolean neuIdShown = privacy.isNeuIdShown();
                boolean phoneShown = privacy.isPhoneShown();
                boolean addressShown = privacy.isAddressShown();
                boolean stateShown = privacy.isStateShown();
                boolean zipShown = privacy.isZipShown();
                boolean emailShown = privacy.isZipShown();
                boolean enrollmentStatusShown = privacy.isEnrollmentStatusShown();
                boolean priorEducationShown = privacy.isPriorEducationShown();
                boolean experienceShown = privacy.isExperienceShown();
                boolean workExperienceShown = privacy.isWorkExperienceShown();
                boolean entryTermShown = privacy.isEntryTermShown();
                boolean expectedLastTermShown = privacy.isExpectedLastTermShown();
                boolean scholarshipShown = privacy.isScholarshipShown();

                String hql = "UPDATE Privacies set NeuIdShown = :neuIdShown, "  +
                        "PhoneShown = :phoneShown, " +
                        "AddressShown = :addressShown, " +
                        "StateShown = :stateShown, " +
                        "ZipShown = :zipShown, " +
                        "EmailShown = :emailShown, " +
                        "EnrollmentStatusShown = :enrollmentStatusShown, " +
                        "PriorEducationShown = :priorEducationShown, " +
                        "ExperienceShown = :experienceShown, " +
                        "WorkExperienceShown = :workExperienceShown, " +
                        "EntryTermShown = :entryTermShown, " +
                        "ExpectedLastTermShown = :expectedLastTermShown, " +
                        "ScholarshipShown = :scholarshipShown " +
                        "WHERE NeuId = :neuId";

                org.hibernate.query.Query query = session.createQuery(hql);
                query.setParameter("neuIdShown", neuIdShown);
                query.setParameter("phoneShown", phoneShown);
                query.setParameter("addressShown", addressShown);
                query.setParameter("stateShown", stateShown);
                query.setParameter("zipShown", zipShown);
                query.setParameter("emailShown", emailShown);
                query.setParameter("enrollmentStatusShown", enrollmentStatusShown);
                query.setParameter("priorEducationShown", priorEducationShown);
                query.setParameter("experienceShown", experienceShown);
                query.setParameter("workExperienceShown", workExperienceShown);
                query.setParameter("entryTermShown", entryTermShown);
                query.setParameter("expectedLastTermShown", expectedLastTermShown);
                query.setParameter("scholarshipShown", scholarshipShown);
                query.setParameter("neuId", neuId);

                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);
                tx.commit();
                return privacy;
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }else{
            System.out.println("privacy for this neu id doesn't exists..");
        }

        return null;
    }

    /**
     * Delete a privacy record from database.
     *
     * @param privacy Privacy record to be deleted. Its privacyId indicates the record to be deleted in database.
     * @return true if delete successfully. Return false if failed.
     */
    public boolean deletePrivacyRecord(Privacies privacy) {
        Transaction tx = null;
        String neuId = privacy.getNeuId();
        try {
            session = factory.openSession();
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM Privacies WHERE NeuId = :neuId ");
            query.setParameter("neuId", neuId);
            System.out.println("Deleting privacy for nuid = " + neuId);
            query.executeUpdate();
            tx.commit();
            if(ifNuidExists(neuId)){
                return false;
            }else{
                return true;
            }
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return false;
    }

    /**
     * Check if a privacy exists for a specific neu id.
     * @param neuId Student Neu Id
     * @return true if exists. Return false if not.
     */
    public boolean ifNuidExists(String neuId) {
        try{
            System.out.println("Checking if a privacy exists or not.......");
            session = factory.openSession();
            org.hibernate.query.Query query = session.createQuery("FROM Privacies WHERE NeuId = :neuId");
            query.setParameter("neuId", neuId);
            List list = query.list();
            if(list.size() == 1){
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