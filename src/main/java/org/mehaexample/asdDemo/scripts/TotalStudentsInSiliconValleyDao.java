package org.mehaexample.asdDemo.scripts;

import java.sql.SQLException;

public class TotalStudentsInSiliconValleyDao {

  // Singleton pattern
  private static TotalStudentsInSiliconValleyDao instance = null;

  /**
   * Default Constructor.
   */
  public TotalStudentsInSiliconValleyDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Total Students In SiliconValley Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalStudentsInSiliconValleyDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalStudentsInSiliconValleyDao();
    }
    return instance;
  }

  /**
   * Get total students in Silicon Valley from the private database.
   * This is a script that extract the information for total students
   * in Silicon Valley from the private database.
   *
   * @return Total Students In Silicon Valley
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalStudentsInSiliconValleyFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_SILICON_VALLEY_STUDENTS " +
                    "FROM Students " +
                    "WHERE Campus = \"SILICON_VALLEY\";";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsFromPrivateDatabase,
            "TOTAL_SILICON_VALLEY_STUDENTS");
  }

  /**
   * Update total students in Silicon Valley in the public database.
   * This is a script that update the information for total students
   * in Silicon Valley in the public database.
   *
   * @param totalStudents Total Students in Silicon Valley (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalStudentsInSiliconValleyInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalStudentsInSiliconValleyInPublic =
            "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalStudentsInSiliconValley\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalStudentsInSiliconValleyInPublic, totalStudents);
  }

  /**
   * Get total students in Silicon Valley from the public database.
   * This is a script that gets the information for total students
   * in Silicon Valley from the public database.
   *
   * @return Total Students In Silicon Valley (From public database).
   * @throws SQLException when connection to the DB has something wrong.
   */
  public int getTotalStudentsInSiliconValleyFromPublicDatabase() throws SQLException {
    String getTotalStudentsInSiliconValleyFromPublicDatabase =
            "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalStudentsInSiliconValley\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsInSiliconValleyFromPublicDatabase);
  }
}
