package org.mehaexample.asdDemo.scripts;

import java.sql.SQLException;
import java.util.List;

import org.mehaexample.asdDemo.Constants;

public class TopFiveEmployersDao {

  // Singleton pattern
  private static TopFiveEmployersDao instance = null;

  /**
   * Default Constructor.
   */
  public TopFiveEmployersDao() { }

  /**
   * Singleton Pattern.
   *
   * @return Top Five Employers Dao instance.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public static TopFiveEmployersDao getInstance() throws SQLException {
    if (instance == null) {
      instance = new TopFiveEmployersDao();
    }
    return instance;
  }

  /**
   * Get the top five employers from the private database.
   * This is a script that extract the information for top five
   * employers from the private database.
   *
   * @return Top five employers.
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopFiveEmployersFromPrivateDatabase() throws SQLException {
    String getTopFiveEmployersFromPrivateDatabase =
            "SELECT Companies.CompanyName AS EMPLOYER, COUNT(*) AS TOTAL " +
                    "FROM WorkExperiences INNER JOIN Companies ON WorkExperiences.CompanyId = Companies.CompanyId " +
                    "GROUP BY EMPLOYER " +
                    "ORDER BY TOTAL DESC " +
                    "LIMIT 5;";
    PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
    return privateDatabaseEtlQuery.getMultipleValueQuery(
            getTopFiveEmployersFromPrivateDatabase, "EMPLOYER");
  }

  /**
   * Update top five employers the public database.
   * This is a script that update the information for top five
   * employers in the public database.
   *
   * @param topFiveEmployers List of Top Five Employers (extracted from the
   *                              private database); not null, size less than or equal to 5.
   * @throws SQLException when connection to the DB has something wrong.
   */
  public void updateTopFiveEmployersInPublicDatabase(List<String> topFiveEmployers) throws SQLException {
    String updateTopFiveEmployersInPublic =
            "UPDATE TopFiveEmployers SET Employer = ? WHERE TopFiveEmployersId = ?;";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    publicPublicDatabaseEtlQuery.updateMultipleValueQuery(
            updateTopFiveEmployersInPublic, topFiveEmployers, Constants.TOP_FIVE);
  }

  /**
   * Get the top five employers from the private database.
   * This is a script that gets the information for top five
   * employers from the private database.
   *
   * @return Top five employers (from public database).
   * @throws SQLException when connection to database has something wrong.
   */
  public List<String> getTopFiveEmployersFromPublicDatabase() throws SQLException {
    String getTopFiveEmployersFromPublicDatabase =
            "SELECT Employer FROM TopFiveEmployers;";
    PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
    return publicPublicDatabaseEtlQuery.getMultipleValueQuery(
            getTopFiveEmployersFromPublicDatabase, "Employer");
  }
}
