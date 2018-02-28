package org.mehaexample.asdDemo.dao;


import java.sql.SQLException;

public class TotalStudentsInSeattleDao {
    // Singleton pattern
    private static TotalStudentsInSeattleDao instance = null;

    /**
     * Default Constructor.
     */
    public TotalStudentsInSeattleDao() { }

    /**
     * Singleton Pattern.
     *
     * @return Total Students In Seattle Dao.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public static TotalStudentsInSeattleDao getInstance() throws SQLException {
        if (instance == null) {
            instance = new TotalStudentsInSeattleDao();
        }
        return instance;
    }

    /**
     * Get total students in Seattle from the private database.
     * This is a script that extract the information for total students
     * in Seattle from the private database.
     *
     * @return Total Students In Seattle
     * @throws SQLException when connection to database has something wrong.
     */
    public int getTotalStudentsInSeattleFromPrivateDatabase() throws SQLException {
        String getTotalStudentsFromPrivateDatabase =
                "SELECT COUNT(*) AS TOTAL_SEATTLE_STUDENTS " +
                        "FROM Students " +
                        "WHERE Campus = \"SEATTLE\";";
        PrivateDatabaseEtlQuery privateDatabaseEtlQuery = new PrivateDatabaseEtlQuery();
        return privateDatabaseEtlQuery.getSingleValueQuery(
                getTotalStudentsFromPrivateDatabase,
                "TOTAL_SEATTLE_STUDENTS");
    }

    /**
     * Update total students in Seattle in the public database.
     * This is a script that update the information for total students
     * in Seattle in the public database.
     *
     * @param totalStudents Total Students in Seattle (extracted from the
     *                      private database); not null.
     * @throws SQLException when connection to the DB has something wrong.
     */
    public void updateTotalStudentsInSeattleInPublicDatabase(int totalStudents) throws SQLException {
        String updateTotalStudentsInSeattleInPublic =
                "UPDATE SingleValueAggregatedData SET DataValue = ? WHERE DataKey = \"TotalStudentsInSeattle\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        publicPublicDatabaseEtlQuery.updateSingleValueQuery(updateTotalStudentsInSeattleInPublic, totalStudents);
    }

    /**
     * Get total students in Seattle from the public database.
     * This is a script that gets the information for total students
     * in Seattle from the public database.
     *
     * @return Total Students In Seattle (From public database)
     * @throws SQLException when connection to the DB has something wrong.
     */
    public int getTotalStudentsInSeattleFromPublicDatabase() throws SQLException {
        String getTotalStudentsInSeattleFromPublicDatabase =
                "SELECT DataValue FROM SingleValueAggregatedData WHERE DataKey = \"TotalStudentsInSeattle\";";
        PublicDatabaseEtlQuery publicPublicDatabaseEtlQuery = new PublicDatabaseEtlQuery();
        return publicPublicDatabaseEtlQuery.getSingleValueQuery(
                getTotalStudentsInSeattleFromPublicDatabase);
    }
}

