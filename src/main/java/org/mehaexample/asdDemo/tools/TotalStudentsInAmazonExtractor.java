package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInAmazonDao;

public class TotalStudentsInAmazonExtractor implements PrivateToPublicExtractor<Integer> {
    private TotalStudentsInAmazonDao totalStudentsInAmazonDao;

    public TotalStudentsInAmazonExtractor() {
        totalStudentsInAmazonDao = new TotalStudentsInAmazonDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalStudentsInAmazonDao.getTotalStudentsFromPrivateDB();
        totalStudentsInAmazonDao.updateTotalStudentsInAmazon(totalStudents);
        return totalStudentsInAmazonDao.getTotalStudentsInAmazon();
    }
}
