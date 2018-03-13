package org.mehaexample.asdDemo.scripts;

import java.sql.SQLException;

public class TotalStudentsInMicrosoftDao {
    // Singleton pattern
    private static TotalStudentsInMicrosoftDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalStudentsInMicrosoftDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students Who Worked In Microsoft Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalStudentsInMicrosoftDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalStudentsInMicrosoftDao();
        }

        return instance;
    }

    /**
     * Get total number of students who worked in Microsoft
     * from private database.
     *
     * @return Total number of students who worked in Microsoft
     * @throws SQLException when connection to the DB has something wrong
     */
    public int getTotalStudentsFromPrivateDB() throws SQLException {
        String query = "SELECT COUNT(*) AS TOTAL_MICROSOFT_STUDENT FROM ( " +
                "SELECT S.NeuId, COUNT(*) " +
                "FROM AlignPrivate.Students AS S " +
                "INNER JOIN AlignPrivate.WorkExperiences AS E ON S.NeuId = E.NeuId " +
                "INNER JOIN AlignPrivate.Companies AS C ON E.CompanyId = C.CompanyId " +
                "WHERE C.CompanyName = \"Microsoft\" " +
                "GROUP BY S.NeuId) AS Student_Microsoft;";

        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                query,
                "TOTAL_MICROSOFT_STUDENT");
    }

    /**
     * Update the number of students who worked in Microsoft in public database.
     *
     * @param totalStudentsInMicrosoft total students worked in Mircrosoft
     * @throws SQLException when connection to the DB has something wrong
     */
    public void updateTotalStudentsInMicrosoft(int totalStudentsInMicrosoft) throws SQLException {
        String updateStudentsInMicrosoft =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE " +
                        "DataKey = \"TotalStudentsWhoWorkInMicrosoft\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateStudentsInMicrosoft, totalStudentsInMicrosoft);
    }

    /**
     * Get the number of students who worked in Microsoft from public database.
     *
     * @return the number of students who worked in Microsoft.
     * @throws SQLException when connection to the DB has something wrong
     */
    public int getTotalStudentsInMicrosoft() throws SQLException {
        String getStudentsInMicrosoft =
                "SELECT DataValue FROM SingleValueAggregatedData " +
                        "WHERE DataKey = \"TotalStudentsWhoWorkInMicrosoft\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(getStudentsInMicrosoft);
    }
}

