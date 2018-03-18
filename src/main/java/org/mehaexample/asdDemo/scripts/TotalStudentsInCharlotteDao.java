package org.mehaexample.asdDemo.scripts;

import java.sql.SQLException;

public class TotalStudentsInCharlotteDao {

  // Singleton pattern
  private static TotalStudentsInCharlotteDao instance = null;

  /**
   * Default Constructor.
   */
  public TotalStudentsInCharlotteDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Total StudentsPublic In Charlotte Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalStudentsInCharlotteDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalStudentsInCharlotteDao();
    }
    return instance;
  }

  /**
   * Get total students in Charlotte from the private database.
   * This is a script that extract the information for total students
   * in Charlotte from the private database.
   *
   * @return Total StudentsPublic In Charlotte
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalStudentsInCharlotteFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_CHARLOTTE_STUDENTS " +
                    "FROM StudentsPublic " +
                    "WHERE Campus = \"CHARLOTTE\";";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsFromPrivateDatabase,
            "TOTAL_CHARLOTTE_STUDENTS");
  }

  /**
   * Update total students in Charlotte in the public database.
   * This is a script that update the information for total students
   * in Charlotte in the public database.
   *
   * @param totalStudents Total StudentsPublic in Charlotte (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalStudentsInCharlotteInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalStudentsInCharlotteInPublic =
            "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalStudentsInCharlotte\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalStudentsInCharlotteInPublic, totalStudents);
  }

  /**
   * Get total students in Charlotte from the public database.
   * This is a script that gets the information for total students
   * in Charlotte from the public database.
   *
   * @return Total StudentsPublic In Charlotte (From public database)
   * @throws SQLException when connection to the DB has something wrong.
   */
  public int getTotalStudentsInCharlotteFromPublicDatabase() throws SQLException {
    String getTotalStudentsInCharlotteFromPublicDatabase =
            "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalStudentsInCharlotte\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsInCharlotteFromPublicDatabase);
  }
}
