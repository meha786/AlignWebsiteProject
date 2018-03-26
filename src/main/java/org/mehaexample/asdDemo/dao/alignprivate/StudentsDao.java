package org.mehaexample.asdDemo.dao.alignprivate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.mehaexample.asdDemo.enums.Campus;
import org.mehaexample.asdDemo.enums.DegreeCandidacy;
import org.mehaexample.asdDemo.enums.EnrollmentStatus;
import org.mehaexample.asdDemo.enums.Gender;
import org.mehaexample.asdDemo.model.alignprivate.Students;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class StudentsDao {
  private SessionFactory factory;
  private Session session;

  /**
   * Default Constructor.
   */
  public StudentsDao() {
    // it will check the hibernate.cfg.xml file and load it
    // next it goes to all table files in the hibernate file and loads them
    this.factory = StudentSessionFactory.getFactory();
  }

  public StudentsDao(boolean test) {
    if (test) {
      this.factory = StudentTestSessionFactory.getFactory();
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
      throw new HibernateException("student already exists.");
    }

    try {
      session = factory.openSession();
      tx = session.beginTransaction();
      session.save(student);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return student;
  }

  public int getTotalStudentsInACampus(Campus campus) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "SELECT COUNT(*) FROM Students WHERE campus = :campus");
      query.setParameter("campus", campus);
      return ((Long) query.list().get(0)).intValue();
    } finally {
      session.close();
    }
  }

  public int getTotalStudents() {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "SELECT COUNT(*) FROM Students");
      return ((Long) query.list().get(0)).intValue();
    } finally {
      session.close();
    }
  }

  public List<Students> getAdminFilteredStudents(Map<String, List<String>> filters, int begin, int end) {
    StringBuilder hql = new StringBuilder("SELECT Distinct(s) " +
            "FROM Students s " +
            "LEFT OUTER JOIN WorkExperiences we ON s.neuId = we.neuId " +
            "LEFT OUTER JOIN PriorEducations pe ON s.neuId = pe.neuId ");
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
        if (filter.equalsIgnoreCase("companyName")) {
          hql.append("we.").append(filter).append(" = :").append(filter).append(i);
        } else if (filter.equalsIgnoreCase("majorName") || filter.equalsIgnoreCase("institutionName")) {
          hql.append("pe.").append(filter).append(" = :").append(filter).append(i);
        } else {
          hql.append("s.").append(filter).append(" = :").append(filter).append(i);
        }
      }
      hql.append(") ");
      if (firstWhereArgument) {
        firstWhereArgument = false;
      }
    }

    hql.append(" ORDER BY s.expectedLastYear DESC ");
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(hql.toString());
      query.setFirstResult(begin - 1);
      query.setMaxResults(end - begin + 1);

      for (String filter : filterKeys) {
        List<String> filterElements = filters.get(filter);
        for (int i = 0; i < filterElements.size(); i++) {
          if (filter.equals("expectedLastYear")) {
            query.setParameter(filter + i, Integer.parseInt(filterElements.get(i)));
          } else {
            if (filter.trim().equalsIgnoreCase("CAMPUS")) {
              query.setParameter(filter + i, Campus.valueOf(filterElements.get(i).trim().toUpperCase()));
            } else if (filter.trim().equalsIgnoreCase("GENDER")) {
              query.setParameter(filter + i, Gender.valueOf(filterElements.get(i).trim().toUpperCase()));
            } else {
              query.setParameter(filter + i, filterElements.get(i));
            }
          }
        }
      }

      return (List<Students>) query.list();
    } finally {
      session.close();
    }
  }

  public int getTotalDropOutStudents() {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(
              "SELECT COUNT(*) FROM Students WHERE enrollmentStatus = :enrollmentStatus");
      query.setParameter("enrollmentStatus", EnrollmentStatus.DROPPED_OUT);
      return ((Long) query.list().get(0)).intValue();
    } finally {
      session.close();
    }
  }

  /**
   * Search for students by multiple properties. Each property have one or multiple values.
   *
   * @param filters The key of filter map is the property, like firstName.
   *                The value of map is a list of detail values.
   * @return a list of students filtered by specified map.
   */
  public List<Students> getStudentFilteredStudents(Map<String, List<String>> filters, int begin, int end) {
    StringBuilder hql = new StringBuilder("SELECT Distinct(s) FROM Students s ");

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
      } else if (filter.equals("startTerm")) {
        String startTerm = "CONCAT(s.entryTerm, s.entryYear) ";
        hql.append(startTerm).append(" IN ").append("(").append(":")
                .append(filter).append(") ");
      } else if (filter.equals("endTerm")) {
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

    hql.append(" ORDER BY s.expectedLastYear DESC ");

    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery(hql.toString());
      query.setFirstResult(begin - 1);
      query.setMaxResults(end - begin + 1);

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

      return (List<Students>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Search a single student record using neu id.
   *
   * @param neuId Student Neu Id
   * @return a student object
   */
  public Students getStudentRecord(String neuId) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM Students WHERE neuId = :studentNuid ");
      query.setParameter("studentNuid", neuId);
      List list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return (Students) list.get(0);
    } finally {
      session.close();
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

    if (ifNuidExists(neuId)) {
      try {
        session = factory.openSession();
        tx = session.beginTransaction();
        session.saveOrUpdate(student);
        tx.commit();
      } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        throw new HibernateException(e);
      } finally {
        session.close();
      }
    } else {
      throw new HibernateException("Student cannot be found.");
    }

    return true;
  }


  /**
   * Delete a student record from database.
   *
   * @param neuId Student Neu Id
   * @return true if delete succesfully. Otherwise, false.
   */
  public boolean deleteStudent(String neuId) {
    if (neuId == null || neuId.isEmpty()) {
      throw new IllegalArgumentException("Neu ID argument cannot be null or empty.");
    }

    Students student = getStudentRecord(neuId);
    if (student == null) {
      throw new HibernateException("Student cannot be found.");
    }
    session = factory.openSession();
    Transaction tx = null;
    try {
      tx = session.beginTransaction();
      session.delete(student);
      tx.commit();
    } catch (HibernateException e) {
      if (tx != null) tx.rollback();
      throw new HibernateException(e);
    } finally {
      session.close();
    }

    return true;
  }

  /**
   * Get a list of students who have the same first name.
   *
   * @param firstName Student first name
   * @return A list of students
   */
  public List<Students> searchStudentRecord(String firstName) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM Students WHERE firstName = :studentfirstName ");
      query.setParameter("studentfirstName", firstName);
      return (List<Students>) query.list();
    } finally {
      session.close();
    }
  }

  /**
   * Get all the students records from database.
   *
   * @return A list of students
   */
  public List<Students> getAllStudents() {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM Students");
      return (List<Students>) query.list();
    } finally {
      session.close();
    }
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
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM Students WHERE neuId = :studentNeuId");
      query.setParameter("studentNeuId", neuId);
      List list = query.list();
      if (!list.isEmpty()) {
        find = true;
      }
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
    org.hibernate.query.Query query = session.createQuery("FROM Students WHERE gender = 'M'");
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
    org.hibernate.query.Query query = session.createQuery("FROM Students WHERE gender = 'F'");
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
    org.hibernate.query.Query query = session.createQuery("FROM Students WHERE degree = :degree");
    query.setParameter("degree", degree);
    List<Students> list = query.list();
    session.close();
    return list;
  }

  /**
   * Get a single student record using emailId.
   *
   * @param email
   * @return a student object
   */
  public Students getStudentRecordByEmailId(String email) {
    try {
      session = factory.openSession();
      org.hibernate.query.Query query = session.createQuery("FROM Students WHERE email = :email ");
      query.setParameter("email", email);
      List list = query.list();
      if (list.isEmpty()) {
        return null;
      }
      return (Students) list.get(0);
    } finally {
      session.close();
    }
  }

}
