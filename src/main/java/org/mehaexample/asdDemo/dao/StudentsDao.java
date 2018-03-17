package org.mehaexample.asdDemo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.model.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class StudentsDao {
	private static SessionFactory factory;
	private static Session session;

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
	 * @param student student to be inserted
	 * @return inserted student if successful. Otherwise null.
	 */
	public Students addStudent(Students student) {
		Transaction tx = null;

		if (ifNuidExists(student.getNeuId())) {
			System.out.println("student already exists!");
		} else {
			System.out.println("saving student in addStudentRecord");
			try {
				session = factory.openSession();
				tx = session.beginTransaction();
				session.save(student);
				tx.commit();
			} catch (HibernateException e) {
				System.out.println("HibernateException: " + e);
				if (tx != null) tx.rollback();
				student = null;
			} finally {
				session.close();
			}
		}

		return student;
	}

	public List<Students> getAdminFilteredStudents(Map<String, List<String>> filters) {
		StringBuilder hql = new StringBuilder("SELECT s " +
				"FROM Students s LEFT OUTER JOIN WorkExperiences we " +
				"ON s.neuId = we.neuId");
		Set<String> filterKeys = filters.keySet();
		if (!filters.isEmpty()) {
			hql.append(" WHERE ");
		}
		boolean firstWhereArgument = true;
		for (String filter : filterKeys) {
			if (!firstWhereArgument) {
				hql.append("AND ");
			}
			hql.append("(");
			boolean first = true;
			List<String> filterElements = filters.get(filter);
			for (int i = 0; i < filterElements.size(); i++) {
				if (!first) {
					hql.append(" OR ");
				}
				if (first) {
					first = false;
				}
				if (filter.equals("companyName")) {
					hql.append("we.").append(filter).append(" = :").append(filter).append(i);
				} else {
					hql.append("s.").append(filter).append(" = :").append(filter).append(i);
				}
			}
			hql.append(") ");
			if (firstWhereArgument) {
				firstWhereArgument = false;
			}
		}

		session = factory.openSession();
		org.hibernate.query.Query query = session.createQuery(hql.toString());

		for (String filter : filterKeys) {
			List<String> filterElements = filters.get(filter);
			for (int i = 0; i < filterElements.size(); i++) {
				query.setParameter(filter + i, filterElements.get(i));
			}
		}
		List<Students> list = query.list();
		session.close();
		return deleteDuplicate(list);
	}

	/**
	 * Search for students by multiple properties. Each property have one or multiple values.
	 *
	 * @param filters The key of filter map is the property, like firstName.
	 *                The value of map is a list of detail values.
	 * @return a list of students filtered by specified map.
	 */
	public List<Students> getStudentFilteredStudents(Map<String, List<String>> filters) {
		StringBuilder hql = new StringBuilder("SELECT s FROM Students s ");

		if (filters.containsKey("companyName")) {
			hql.append("INNER JOIN WorkExperiences we ON s.neuId = we.neuId ");
		}

		if (filters.containsKey("courseName")) {
			hql.append("INNER JOIN Electives el ON s.neuId = el.neuId ")
			.append("INNER JOIN Courses co ON el.courseId = co.courseId ");
		}

		Set<String> filterKeys = filters.keySet();
		if (!filters.isEmpty()) {
			hql.append("WHERE ");
		}

		boolean firstWhereArgument = true;
		for (String filter : filterKeys) {
			if (!firstWhereArgument) {
				hql.append("AND ");
			}

			if (filter.equals("companyName")) {
				hql.append("we.").append(filter).append(" IN ").append("(").append(":")
				.append(filter).append(") ");
			} else if (filter.equals("courseName")) {
				hql.append("co.").append(filter).append(" IN ").append("(").append(":")
				.append(filter).append(") ");
			} else if (filter.equals("startTerm")){
				String startTerm =  "CONCAT(s.entryTerm, s.entryYear) ";
				hql.append(startTerm).append(" IN ").append("(").append(":")
				.append(filter).append(") ");
			}  else if (filter.equals("endTerm")) {
				String endTerm = "CONCAT(s.expectedLastTerm, s.expectedLastYear) ";
				hql.append(endTerm).append(" IN ").append("(").append(":")
				.append(filter).append(") ");
			} else {
				hql.append("s.").append(filter).append(" IN ").append("(").append(":")
				.append(filter).append(") ");
			}

			if (firstWhereArgument) {
				firstWhereArgument = false;
			}
		}

		session = factory.openSession();
		org.hibernate.query.Query query = session.createQuery(hql.toString());

		for (String filter : filterKeys) {
			List<String> filterElements = filters.get(filter);
			if (filter.equals("campus")) {
				List<Campus> campuses = new ArrayList<>();
				for (String campus : filterElements) {
					campuses.add(Campus.valueOf(campus));
				}
				query.setParameterList(filter, campuses);
			} else if (filter.equals("gender")) {
				List<Gender> genders = new ArrayList<>();
				for (String gender : filterElements) {
					genders.add(Gender.valueOf(gender));
				}
				query.setParameterList(filter, genders);
			} else {
				query.setParameterList(filter, filterElements);
			}
		}

		List<Students> list = query.list();
		session.close();
		return deleteDuplicate(list);
	}

	private List<Students> deleteDuplicate(List<Students> students) {
		Map<String, Students> outputMap = new HashMap<>();
		for (Students student: students) {
			outputMap.put(student.getNeuId(),student);
		}
		return (List<Students>) new ArrayList<>(outputMap.values());
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
		if (list.size() == 1) {
			return (Students) list.get(0);
		} else {
			System.out.println("The list should contain only one student with a given nuid");
			return null;
		}
	}

	/**
	 * Update a student record with most recent details.
	 *
	 * @param student which contains the new student details.
	 * @return true if successful. Otherwise, false.
	 */
	public boolean updateStudentRecord(Students student) {
		Transaction tx = null;
		String neuId = student.getNeuId();
		boolean updated = false;

		if (ifNuidExists(neuId)) {
			try {
				Session session = factory.openSession();
				tx = session.beginTransaction();
				session.saveOrUpdate(student);
				tx.commit();
				updated = true;
			} catch (HibernateException e) {
				if (tx != null) tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		return updated;
	}


	/**
	 * Delete a student record from database.
	 *
	 * @param neuId Student Neu Id
	 * @return true if delete succesfully. Otherwise, false.
	 */
	public boolean deleteStudent(String neuId) {
		boolean deleted = false;

		if (neuId == null || neuId.isEmpty()) {
			return false;
		}

		Students student = getStudentRecord(neuId);
		if (student != null) {
			session = factory.openSession();
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.delete(student);
				tx.commit();
				deleted = true;
			} catch (HibernateException e) {
				if (tx != null) tx.rollback();
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		return deleted;
	}

	/**
	 * Get a list of students who have the same first name.
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
	public List<Students> getAllStudents() {
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
	public boolean ifNuidExists(String neuId) {
		boolean find = false;

		try {
			System.out.println("Checking if an entered neuId exists or not.......");
			session = factory.openSession();
			org.hibernate.query.Query query = session.createQuery("FROM Students WHERE NeuId = :studentNeuId");
			query.setParameter("studentNeuId", neuId);
			List list = query.list();
			if (list.size() == 1) {
				find = true;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return find;
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
