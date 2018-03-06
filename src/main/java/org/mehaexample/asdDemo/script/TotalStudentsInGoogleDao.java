package org.mehaexample.asdDemo.script;

import java.sql.SQLException;

public class TotalStudentsInGoogleDao {
    // Singleton pattern
    private static TotalStudentsInGoogleDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalStudentsInGoogleDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students Who Worked In Google Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalStudentsInGoogleDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalStudentsInGoogleDao();
        }

        return instance;
    }

    /**
     * Get total number of students who worked in Google
     * from private database.
     *
     * @return Total number of students who worked in Google
     * @throws SQLException when connection to the DB has something wrong
     */
    public int getTotalStudentsFromPrivateDB() throws SQLException {
        String query = "SELECT COUNT(*) AS TOTAL_GOOGLE_STUDENT FROM ( " +
                "SELECT S.NeuId, COUNT(*) " +
                "FROM AlignPrivate.Students AS S " +
                "INNER JOIN AlignPrivate.WorkExperiences AS E ON S.NeuId = E.NeuId " +
                "INNER JOIN AlignPrivate.Companies AS C ON E.CompanyId = C.CompanyId " +
                "WHERE C.CompanyName = \"Google\" " +
                "GROUP BY S.NeuId) AS Student_Google;";

        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                query,
                "TOTAL_GOOGLE_STUDENT");
    }

    /**
     * Update the number of students who worked in Google in public database.
     *
     * @param totalStudentsInGoogle total students worked in Google
     * @throws SQLException when connection to the DB has something wrong
     */
    public void updateTotalStudentsInGoogle(int totalStudentsInGoogle) throws SQLException {
        String updateStudentsInGoogle =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE " +
                        "DataKey = \"TotalStudentsWhoWorkInGoogle\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateStudentsInGoogle, totalStudentsInGoogle);
    }

    /**
     * Get the number of students who worked in Google from public database.
     *
     * @return the number of students who worked in Google.
     * @throws SQLException when connection to the DB has something wrong
     */
    public int getTotalStudentsInGoogle() throws SQLException {
        String getStudentsInGoogle =
                "SELECT DataValue FROM SingleValueAggregatedData " +
                        "WHERE DataKey = \"TotalStudentsWhoWorkInGoogle\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(getStudentsInGoogle);
    }
}

