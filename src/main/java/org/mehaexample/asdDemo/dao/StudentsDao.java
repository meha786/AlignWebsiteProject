package org.mehaexample.asdDemo.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.model.Privacies;
import org.mehaexample.asdDemo.model.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class StudentsDao {
    private static SessionFactory factory;
    private static Session session;
    private static PrivaciesDao privaciesDao;

    /**
     * Default Constructor.
     */
    public StudentsDao() {
        try {
            // it will check the hibernate.cfg.xml file and load it
            // next it goes to all table files in the hibernate file and loads them
            factory = new Configuration().configure().buildSessionFactory();
            session = factory.openSession();
            privaciesDao = new PrivaciesDao();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);

        }
    }

    /**
     * This is the function to add a student into database.
     *
     * @param student
     * @return true if insert successfully. Otherwise throws exception.
     */
    public Students addStudent(Students student) {
        Transaction tx = null;

        if(ifNuidExists(student.getNeuId())){
            System.out.println("student already exists!");
        }else{
            System.out.println("saving student in addStudentRecord");
            try {
                tx = session.beginTransaction();
                session.save(student);
                tx.commit();
            } catch (HibernateException e) {
                System.out.println("HibernateException: " + e);
                if (tx!=null) tx.rollback();
            } finally {
//                    session.close();
            }
        }
        return student;
    }

    /**
     * Get a single student record using neuId
     *
     * @param neuId
     * @return a student object
     */
    public Students getStudentRecord(String neuId) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNuid ");
        query.setParameter("studentNuid", neuId);
        List list = query.list();
        if(list.size()==1){
            return (Students) list.get(0);
        }else{
            System.out.println("The list should contain only one student with a given nuid");
            return null;
        }
    }
    
    /**
     * Get a single student record using neuId that respects privacy rules
     *
     * @param neuId
     * @return a student object
     * http://localhost:8080/alignWebsite/webapi/studentresource/privacies/id/1234567
     */
    public Students getStudentRecordPrivately(String neuId) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNuid ");
        query.setParameter("studentNuid", neuId);
        List list = query.list();
        if(list.size()==1){
        	Students studentsWithAllDetails = (Students) list.get(0);
        	Privacies privacies = privaciesDao.getPrivacyRecordByNeuId(studentsWithAllDetails.getNeuId());
        	System.out.println("phone: "+privacies.isPhoneShown());
        	Students student = new Students();
        	if(privacies.isAddressShown()) {
        		student.setAddress(studentsWithAllDetails.getAddress());
        	}
        	
        	if(privacies.isEmailShown()) {
        		student.setEmail(studentsWithAllDetails.getEmail());
        	}
        	
        	if(privacies.isEnrollmentStatusShown()) {
        		student.setEnrollmentStatus(studentsWithAllDetails.getEnrollmentStatus());
        	}
        	
        	if(privacies.isNeuIdShown()) {
        		student.setNeuId(studentsWithAllDetails.getNeuId());
        	}
        	
        	if(privacies.isPhoneShown()) {
        		student.setPhoneNum(studentsWithAllDetails.getPhoneNum());
        	}
        	
        	if(privacies.isScholarshipShown()) {
        		student.setScholarship(studentsWithAllDetails.isScholarship());
        	}
        	
        	if(privacies.isStateShown()) {
        		student.setState(studentsWithAllDetails.getState());
        	}
        	
        	if(privacies.isZipShown()) {
        		student.setZip(studentsWithAllDetails.getZip());
        	}
        	
            return (Students) student;
        }else{
            System.out.println("The list should contain only one student with a given nuid");
            return null;
        }
    }

    /**
     * Get a single student record using emailId
     * 
     * @param emailId
     * @return a student object
     */
    public Students getStudentRecordByEmailId(String emailId) {
    	 org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Email = :studentEmailId ");
         query.setParameter("studentEmailId", emailId);
         List list = query.list();
         if(list.size()==1){
             return (Students) list.get(0);
         }else{
             System.out.println("The list should contain only one student with a given nuid");
             return null;
         }		 
	}
    
    /**
     * Update a student record.
     *
     * @param student which contains the latest information.
     * @return Updated student object if successful. Otherwise, null.
     */
    public Students updateStudentRecord(Students student) {
        Transaction tx = null;
        String neuId = student.getNeuId();
        if(ifNuidExists(neuId)){
            try{
                Session session = factory.openSession();
                tx = session.beginTransaction();
                session.saveOrUpdate(student);
                tx.commit();
                return student;
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            }
        }else{
            System.out.println("student id doesn't exists..");
        }

        return null;
    }
    
    /**
     * Delete a student record from database.
     *
     * @param neuId
     * @return true if delete succesfully. Otherwise, false.
     */
    public boolean deleteStudent(String neuId){
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            org.hibernate.query.Query query = session.createQuery("DELETE FROM Students WHERE NeuId = :studentNuid ");
            query.setParameter("studentNuid", neuId);
            System.out.println("Deleting student for nuid = " + neuId);
            query.executeUpdate();
            tx.commit();
            if(ifNuidExists(neuId)){
                return false;
            }else{
                return true;
            }
        } catch (HibernateException e) {
            System.out.println("exceptionnnnnn");
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return true;
    }

    /**
     *  Get a list of students who have the same first name.
     *
     * @param firstName
     * @return A list of students
     */
    public List<Students> searchStudentRecordByFirstName(String firstName) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE FirstName = :studentfirstName ");
        query.setParameter("studentfirstName", firstName);
        List<Students> list = query.list();
        return list;
    }
    
    /**
     *  Get a list of students who have the same last name.
     *
     * @param lastName
     * @return A list of students
     */
    public List<Students> searchStudentRecordByLastName(String lastName) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE LastName = :lastName ");
        query.setParameter("lastName", lastName);
        List<Students> list = query.list();
        return list;
    }
    
    /**
     *  Get a list of students who have the same first name.
     *
     * @param firstName
     * @return A list of students
     */
    public List<Students> searchStudentRecordByFirstNameAndLastName(String firstName, String lastName) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE FirstName = :studentfirstName and LastName = :studentlastName");
        query.setParameter("studentfirstName", firstName);
        query.setParameter("studentlastName", lastName);
        
        List<Students> list = query.list();
        return list;
    }

    /**
     * Get all the students records from database.
     *
     * @return A list of students
     */
    public List<Students> getAllStudents(){
    	System.out.println("reached dao method");
        org.hibernate.query.Query query = session.createQuery("FROM Students");
        System.out.println("reached dao method 2");
        
        List<Students> list = query.list();
        
        System.out.println(list.get(0).getEmail());
        return list;
    }

    /**
     * Check if a specific student existed in database based on neu id.
     *
     * @param neuId
     * @return true if existed, false if not.
     */
    public boolean ifNuidExists(String neuId){
        try{
            System.out.println("Checking if an entered neuId exists or not.......");
            org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNeuId");
            query.setParameter("studentNeuId", neuId);
            List list = query.list();
            if(list.size() == 1){
                return true;
            }
        }catch (HibernateException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Get the total number of male students in database.
     *
     * @return the number of male students.
     */
    public int countMaleStudents() {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Gender = 'M'");
        List<Students> list = query.list();
        return list.size();
    }

    /**
     * Get the total number of female students in database.
     *
     * @return the number of female students.
     */
    public int countFemaleStudents() {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Gender = 'F'");
        List<Students> list = query.list();
        return list.size();
    }

    /**
     * Get a list of similar students.
     *
     * @param degree
     * @return a list of students with the same degree.
     */
    public List<Students> searchSimilarStudents(DegreeCandidacy degree) {
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE degreeCandidacy = :degree");
        query.setParameter("degree", degree.name());
        List<Students> list = query.list();
        return list;
    }
	
}

