package org.mehaexample.asdDemo.scripts;

import java.sql.SQLException;

public class TotalFemaleStudentsDao {
  // Singleton pattern
  private static TotalFemaleStudentsDao instance = null;

  /**
   * Default Constructor.
   */
  public TotalFemaleStudentsDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Total Female Students.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalFemaleStudentsDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalFemaleStudentsDao();
    }

    return instance;
  }

  /**
   * Get total female students from the private database.
   * This is a script that extract the information for total female students
   * from the private database.
   *
   * @return Total Female Students.
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalFemaleStudentsFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_FEMALE_STUDENTS " +
                    "FROM Students " +
                    "WHERE Gender = \"F\";";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsFromPrivateDatabase,
            "TOTAL_FEMALE_STUDENTS");
  }

  /**
   * Update total female students in the public database.
   * This is a script that update the information for total female students
   * in the public database.
   *
   * @param totalStudents Total Female Students (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalFemaleStudentsInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalFemaleStudentsInPublic =
            "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalFemaleStudents\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalFemaleStudentsInPublic, totalStudents);
  }

  /**
   * Get total Female students from the public database.
   * This is a script that gets the information for total Female students
   * from the public database.
   *
   * @return Total Female Students (From public database)
   * @throws SQLException when connection to the DB has something wrong.
   */
  public int getTotalFemaleStudentsFromPublicDatabase() throws SQLException {
    String getTotalFemaleStudentsFromPublicDatabase =
            "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalFemaleStudents\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getSingleValueQuery(
            getTotalFemaleStudentsFromPublicDatabase);
  }
}
