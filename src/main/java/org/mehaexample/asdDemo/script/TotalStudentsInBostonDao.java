package org.mehaexample.asdDemo.script;


import java.sql.SQLException;

public class TotalStudentsInBostonDao {
  // Singleton pattern
  private static TotalStudentsInBostonDao instance = null;

  /**
   * Default Constructor.
   */
  public TotalStudentsInBostonDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Total Students In Boston Dao.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TotalStudentsInBostonDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TotalStudentsInBostonDao();
    }
    return instance;
  }

  /**
   * Get total students in Boston from the private database.
   * This is a script that extract the information for total students
   * in Boston from the private database.
   *
   * @return Total Students In Boston
   * @throws SQLException when connection to database has something wrong.
   */
  public int getTotalStudentsInBostonFromPrivateDatabase() throws SQLException {
    String getTotalStudentsFromPrivateDatabase =
            "SELECT COUNT(*) AS TOTAL_BOSTON_STUDENTS " +
                    "FROM Students " +
                    "WHERE Campus = \"BOSTON\";";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsFromPrivateDatabase,
            "TOTAL_BOSTON_STUDENTS");
  }

  /**
   * Update total students in Boston in the public database.
   * This is a script that update the information for total students
   * in Boston in the public database.
   *
   * @param totalStudents Total Students in Boston (extracted from the
   *                      private database); not null.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTotalStudentsInBostonInPublicDatabase(int totalStudents) throws SQLException {
    String updateTotalStudentsInBostonInPublic =
            "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalStudentsInBoston\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalStudentsInBostonInPublic, totalStudents);
  }

  /**
   * Get total students in Boston from the public database.
   * This is a script that gets the information for total students
   * in Boston from the public database.
   *
   * @return Total Students In Boston (From public database)
   * @throws SQLException when connection to the DB has something wrong.
   */
  public int getTotalStudentsInBostonFromPublicDatabase() throws SQLException {
    String getTotalStudentsInBostonFromPublicDatabase =
            "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalStudentsInBoston\";";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getSingleValueQuery(
            getTotalStudentsInBostonFromPublicDatabase);
  }
}
