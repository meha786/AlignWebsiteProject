package org.mehaexample.asdDemo.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
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
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);

        }
    }

    /**
     * This is the function to add a student into database.
     *
     * @param student Student record to be inserted
     * @return true if insert successfully. Otherwise throws exception.
     */
    public Students addStudent(Students student) {
        Transaction tx = null;

        if(ifNuidExists(student.getNeuId())){
            System.out.println("student already exists!");
        }else{
            System.out.println("saving student in addStudentRecord");
            try {
                session = factory.openSession();
                tx = session.beginTransaction();
                session.save(student);
                tx.commit();
            } catch (HibernateException e) {
                System.out.println("HibernateException: " + e);
                if (tx!=null) tx.rollback();
            } finally {
                    session.close();
            }
        }
        return student;
    }

    /**
     * Search a single student record using neu id.
     *
     * @param neuId Student Neu Id
     * @return a student object
     */
    public Students getStudentRecord(String neuId) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNuid ");
        query.setParameter("studentNuid", neuId);
        List list = query.list();
        session.close();
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
     * @param student which contains the new information.
     * @return Updated student object if successful. Otherwise, null.
     */
    public Students updateStudentRecord(Students student) {
        Transaction tx = null;
        String neuId = student.getNeuId();
        if(ifNuidExists(neuId)){
            try{
                Session session = factory.openSession();
                tx = session.beginTransaction();
                String address = student.getAddress();
                String email = student.getEmail();
                String phone = student.getPhoneNum();

                String hql = "UPDATE Students set Address = :address, "  +
                        "Email = :email, " +
                        "Phone = :phone " +
                        "WHERE NeuId = :neuId";
                org.hibernate.query.Query query = session.createQuery(hql);
                query.setParameter("address", address);
                query.setParameter("email", email);
                query.setParameter("phone", phone);
                query.setParameter("neuId", neuId);
                int result = query.executeUpdate();
                System.out.println("Rows affected: " + result);
                tx.commit();
                return student;
            }catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }else{
            System.out.println("student id doesn't exists..");
        }

        return null;
    }

    /**
     * Delete a student record from database.
     *
     * @param neuId Student Neu Id
     * @return true if delete succesfully. Otherwise, false.
     */
    public boolean deleteStudent(String neuId){
        Transaction tx = null;
        try {
            session = factory.openSession();
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
     * @param firstName Student first name
     * @return A list of students
     */
    public List<Students> searchStudentRecord(String firstName) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE FirstName = :studentfirstName ");
        query.setParameter("studentfirstName", firstName);
        session.close();
        List<Students> list = query.list();
        return list;
    }

    /**
     * Get all the students records from database.
     *
     * @return A list of students
     */
    public List<Students> getAllStudents(){
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery("FROM Students");
        List<Students> list = query.list();
        session.close();
        return list;
    }

    /**
     * Check if a specific student existed in database based on neu id.
     *
     * @param neuId Student Neu Id
     * @return true if existed, false if not.
     */
    public boolean ifNuidExists(String neuId){
        try{
            System.out.println("Checking if an entered neuId exists or not.......");
            session = factory.openSession();
            org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNeuId");
            query.setParameter("studentNeuId", neuId);
            List list = query.list();
            if(list.size() == 1){
                return true;
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
           session.close();
        }

        return false;
    }

    /**
     * Get the total number of male students in database.
     *
     * @return the number of male students.
     */
    public int countMaleStudents() {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Gender = 'M'");
        List<Students> list = query.list();
        session.close();
        return list.size();
    }

    /**
     * Get the total number of female students in database.
     *
     * @return the number of female students.
     */
    public int countFemaleStudents() {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE Gender = 'F'");
        List<Students> list = query.list();
        session.close();
        return list.size();
    }

    /**
     * Get a list of similar students.
     *
     * @param degree The degree candidacy
     * @return a list of students with the same degree.
     */
    public List<Students> searchSimilarStudents(DegreeCandidacy degree) {
        session = factory.openSession();
        org.hibernate.query.Query query = session.createQuery("FROM Students WHERE degreeCandidacy = :degree");
        query.setParameter("degree", degree.name());
        List<Students> list = query.list();
        session.close();
        return list;
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
     * Get a list of similar students.
     *
     * @param degree,enrollment status, first name, email id
     * @return a list of students with the filters.
     */
    public List<Students> adminFiterStudents
    (DegreeCandidacy degreeEnum, EnrollmentStatus enrollmentStatus, String name,String email) {
        String queryStr = "FROM Students WHERE";
        
        if(degreeEnum.toString() != "PLACEHOLDER"){
        	queryStr = queryStr + " degreeCandidacy = :degree";
        	if(name != "PLACEHOLDER" || email != "PLACEHOLDER" ||
        			enrollmentStatus.toString() != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(enrollmentStatus.toString() != "PLACEHOLDER"){
        	queryStr = queryStr + " enrollmentStatus = :enrollmentStatus";
        	if(name != "PLACEHOLDER" || email != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(name != "PLACEHOLDER"){
        	queryStr = queryStr + " firstName like :name";
        	name = name+'%';
        	if(email != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(email != "PLACEHOLDER"){
        	queryStr = queryStr + " email like :email";
        	email = email+'%';
        }
        
    	org.hibernate.query.Query query = session.createQuery(queryStr);
    	
    	if(degreeEnum.toString() != "PLACEHOLDER"){
    		query.setParameter("degree", degreeEnum.name());
    	}
    	if(enrollmentStatus.toString() != "PLACEHOLDER"){
    		query.setParameter("enrollmentStatus", enrollmentStatus);
        }
        if(name != "PLACEHOLDER"){
        	query.setParameter("name", name);
    	}
        if(email != "PLACEHOLDER"){
        	query.setParameter("email", email);
    	}
    	
        List<Students> list = query.list();
        return list;
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
     * Get a list of similar students.
     *
     * @param degree
     * @return a list of students with the filters.
     */
    public List<Students> searchSimilarStudents
    (DegreeCandidacy degreeEnum,Campus campusEnum,EnrollmentStatus enrollmentStatus,Gender gender,String scholarship,String visa) {
        String queryStr = "FROM Students WHERE";
        
        if(degreeEnum.toString() != "PLACEHOLDER"){
        	queryStr = queryStr + " degreeCandidacy = :degree";
        	if(scholarship != "PLACEHOLDER" || visa != "PLACEHOLDER" || 
        	campusEnum.toString() != "PLACEHOLDER" || enrollmentStatus.toString() != "PLACEHOLDER" || gender.toString() != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(campusEnum.toString() != "PLACEHOLDER"){
        	queryStr = queryStr + " campus = :campuspl";
        	if(scholarship != "PLACEHOLDER" || visa != "PLACEHOLDER"
        			|| enrollmentStatus.toString() != "PLACEHOLDER" || gender.toString() != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(enrollmentStatus.toString() != "PLACEHOLDER"){
        	queryStr = queryStr + " enrollmentStatus = :enrollmentStatus";
        	if(scholarship != "PLACEHOLDER" || visa != "PLACEHOLDER"
        			|| gender.toString() != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(gender.toString() != "PLACEHOLDER"){
        	queryStr = queryStr + " gender = :gender";
        	if(scholarship != "PLACEHOLDER" || visa != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(scholarship != "PLACEHOLDER"){
        	queryStr = queryStr + " scholarship = :scholarship";
        	if(visa != "PLACEHOLDER"){
        		queryStr = queryStr + " and";
        	}
        }
        if(visa != "PLACEHOLDER"){
        	queryStr = queryStr + " visa = :visa";
        }
        
    	org.hibernate.query.Query query = session.createQuery(queryStr);
    	
    	if(degreeEnum.toString() != "PLACEHOLDER"){
    		query.setParameter("degree", degreeEnum.name());
    	}
    	if(campusEnum.toString() != "PLACEHOLDER"){
    		query.setParameter("campuspl", campusEnum);
    	}
    	if(enrollmentStatus.toString() != "PLACEHOLDER"){
    		query.setParameter("enrollmentStatus", enrollmentStatus);
        }
        if(gender.toString() != "PLACEHOLDER"){
        	query.setParameter("gender", gender);
        }
        if(scholarship != "PLACEHOLDER"){
        	query.setParameter("scholarship", Boolean.parseBoolean(scholarship));
    	}
        if(visa != "PLACEHOLDER"){
        	query.setParameter("visa", visa);
    	}
    	
        List<Students> list = query.list();
        return list;
    }

	public boolean ifEmailExists(String email) {
		return false;
	}
}
