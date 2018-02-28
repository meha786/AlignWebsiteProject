package org.mehaexample.asdDemo.dao;


import java.sql.SQLException;

public class TotalStudentsWithScholarshipDao {
    // Singleton pattern
    private static TotalStudentsWithScholarshipDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalStudentsWithScholarshipDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students With Scholarship Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalStudentsWithScholarshipDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalStudentsWithScholarshipDao();
        }
        return instance;
    }

    /**
     * Get total students with scholarship from the private database.
     * This is a script that extract the information for total students
     * with scholarship from the private database.
     *
     * @return Total Students With Scholarship
     * @throws SQLException when connection to database has something wrong.
     */
    public int getTotalStudentsWithScholarshipFromPrivateDatabase() throws SQLException {
        String getTotalStudentsFromPrivateDatabase =
                "SELECT COUNT(*) AS TOTAL_SCHOLARSHIP_STUDENTS " +
                        "FROM Students " +
                        "WHERE Scholarship = true;";
        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                getTotalStudentsFromPrivateDatabase,
                "TOTAL_SCHOLARSHIP_STUDENTS");
    }

    /**
     * Update total students with scholarship in the public database.
     * This is a script that update the information for total students
     * with scholarship in the public database.
     *
     * @param totalStudents Total Students with Scholarship (extracted from the
     *                      private database); not null.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public void updateTotalStudentsWithScholarshipInPublicDatabase(int totalStudents) throws SQLException {
        String updateTotalStudentsWithScholarshipInPublic =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalStudentsWithScholarship\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalStudentsWithScholarshipInPublic, totalStudents);
    }

    /**
     * Get total students with scholarship from the public database.
     * This is a script that gets the information for total students
     * with scholarship from the public database.
     *
     * @return Total Students With Scholarship (From public database)
     * @throws SQLException when connection to the DB has something wrong.
     */
    public int getTotalStudentsWithScholarshipFromPublicDatabase() throws SQLException {
        String getTotalStudentsWithScholarishipFromPublicDatabase =
                "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalStudentsWithScholarship\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(
                getTotalStudentsWithScholarishipFromPublicDatabase);
    }
}
