package org.mehaexample.asdDemo.scripts;

import java.sql.SQLException;

public class TotalMaleStudentsDao {
  // Singleton pattern
  private static TotalMaleStudentsDao instance = null;

  /**
   * Default Constructor.
   */
  public TotalMaleStudentsDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Total Male StudentsPublic.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalMaleStudentsDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalMaleStudentsDao();
    }

    return instance;
  }

  /**
   * Get total male students from the private database.
   * This is a script that extract the information for total male students
   * from the private database.
   *
   * @return Total Male StudentsPublic.
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalMaleStudentsFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_MALE_STUDENTS " +
                    "FROM StudentsPublic " +
                    "WHERE Gender = \"M\";";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsFromPrivateDatabase,
            "TOTAL_MALE_STUDENTS");
  }

  /**
   * Update total male students in the public database.
   * This is a script that update the information for total male students
   * in the public database.
   *
   * @param totalStudents Total Male StudentsPublic (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalMaleStudentsInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalMaleStudentsInPublic =
            "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalMaleStudents\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalMaleStudentsInPublic, totalStudents);
  }

  /**
   * Get total Male students from the public database.
   * This is a script that gets the information for total Male students
   * from the public database.
   *
   * @return Total Male StudentsPublic (From public database)
   * @throws SQLException when connection to the DB has something wrong.
   */
  public int getTotalMaleStudentsFromPublicDatabase() throws SQLException {
    String getTotalMaleStudentsFromPublicDatabase =
            "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalMaleStudents\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getSingleValueQuery(
            getTotalMaleStudentsFromPublicDatabase);
  }
}
