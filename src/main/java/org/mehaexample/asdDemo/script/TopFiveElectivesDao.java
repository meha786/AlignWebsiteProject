package org.mehaexample.asdDemo.script;

import org.mehaexample.asdDemo.Constants;

import java.sql.SQLException;
import java.util.List;

public class TopFiveElectivesDao {

  // Singleton pattern
  private static TopFiveElectivesDao instance = null;

  /**
   * Default Constructor.
   */
  public TopFiveElectivesDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Top Five Electives Dao instance.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TopFiveElectivesDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TopFiveElectivesDao();
    }
    return instance;
  }

  /**
   * Get the top five electives from the private database.
   * This is a script that extract the information for top five
   * electives from the private database.
   *
   * @return Top five electives.
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopFiveElectivesFromPrivateDatabase() throws SQLException {
    String getTopFiveElectivesFromPrivateDatabase =
            "SELECT Courses.CourseName AS COURSE, COUNT(*) AS TOTAL " +
                    "FROM Electives INNER JOIN Courses ON Electives.CourseId = Courses.CourseId " +
                    "GROUP BY COURSE " +
                    "ORDER BY TOTAL DESC " +
                    "LIMIT 5;";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getMultipleValueQuery(
            getTopFiveElectivesFromPrivateDatabase, "COURSE");
  }

  /**
   * Update top five electives the public database.
   * This is a script that update the information for top five
   * electives in the public database.
   *
   * @param topFiveElectives List of Top Five Electives (extracted from the
   *                              private database); not null, size less than or equal to 5.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTopFiveElectivesInPublicDatabase(List<String> topFiveElectives) throws SQLException {
    String updateTopFiveElectivesInPublic =
            "UPDATE TopFiveElectives SET CourseName = ? WHERE TopFiveElectivesId = ?;";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateMultipleValueQuery(
            updateTopFiveElectivesInPublic, topFiveElectives, Constants.TOP_FIVE);
  }

  /**
   * Get the top five electives from the public database.
   * This is a script that gets the information for top five
   * electives from the public database.
   *
   * @return Top Five Electives (From public database).
   * @throws SQLException when connection to the DB has something wrong.
   */
  public List<String> getTopFiveElectivesFromPublicDatabase() throws SQLException {
    String getTopFiveElectivesFromPublicDatabase =
            "SELECT CourseName FROM TopFiveElectives;";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getMultipleValueQuery(
            getTopFiveElectivesFromPublicDatabase, "CourseName");
  }
}
