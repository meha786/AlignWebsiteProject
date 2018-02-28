package org.mehaexample.asdDemo.dao;

import java.sql.SQLException;

public class TotalStudentsInAmazonDao {
    // Singleton pattern
    private static TotalStudentsInAmazonDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalStudentsInAmazonDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students Who Worked In Amazon Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalStudentsInAmazonDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalStudentsInAmazonDao();
        }

        return instance;
    }

    /**
     * Get total number of students who worked in Amazon
     * from private database.
     *
     * @return Total number of students who worked in Amazon
     * @throws SQLException when connection to the DB has something wrong
     */
    public int getTotalStudentsFromPrivateDB() throws SQLException {
        String query = "SELECT COUNT(*) AS TOTAL_AMAZON_STUDENT FROM ( " +
                "SELECT S.NeuId, COUNT(*) " +
                "FROM AlignPrivate.Students AS S " +
                "INNER JOIN AlignPrivate.WorkExperiences AS E ON S.NeuId = E.NeuId " +
                "INNER JOIN AlignPrivate.Companies AS C ON E.CompanyId = C.CompanyId " +
                "WHERE C.CompanyName = \"Amazon\" " +
                "GROUP BY S.NeuId) AS Student_Amazon;";

        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                query,
                "TOTAL_AMAZON_STUDENT");
    }

    /**
     * Update the number of students who worked in Amazon in public database.
     *
     * @param totalStudentsInAmazon total students worked in Amazon
     * @throws SQLException when connection to the DB has something wrong
     */
    public void updateTotalStudentsInAmazon(int totalStudentsInAmazon) throws SQLException {
        String updateStudentsInAmazon =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE " +
                        "DataKey = \"TotalStudentsWhoWorkInAmazon\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateStudentsInAmazon, totalStudentsInAmazon);
    }

    /**
     * Get the number of students who worked in Amazon from public database.
     *
     * @return the number of students who worked in Amazon.
     * @throws SQLException when connection to the DB has something wrong
     */
    public int getTotalStudentsInAmazon() throws SQLException {
        String getStudentsInAmazon =
                "SELECT DataValue FROM SingleValueAggregatedData " +
                        "WHERE DataKey = \"TotalStudentsWhoWorkInAmazon\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(getStudentsInAmazon);
    }
}
