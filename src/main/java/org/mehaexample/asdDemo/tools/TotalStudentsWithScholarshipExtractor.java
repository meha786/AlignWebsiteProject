package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsWithScholarshipDao;

public class TotalStudentsWithScholarshipExtractor implements PrivateToPublicExtractor<Integer> {
    private TotalStudentsWithScholarshipDao totalStudentsWithScholarshipDao;

    public TotalStudentsWithScholarshipExtractor() {
        totalStudentsWithScholarshipDao = new TotalStudentsWithScholarshipDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalStudentsWithScholarshipDao.getTotalStudentsWithScholarshipFromPrivateDatabase();
        totalStudentsWithScholarshipDao.updateTotalStudentsWithScholarshipInPublicDatabase(totalStudents);
        return totalStudentsWithScholarshipDao.getTotalStudentsWithScholarshipFromPublicDatabase();
    }
}
