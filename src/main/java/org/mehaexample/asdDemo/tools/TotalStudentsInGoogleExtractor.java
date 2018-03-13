package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInGoogleDao;

public class TotalStudentsInGoogleExtractor implements PrivateToPublicExtractor<Integer> {
    private TotalStudentsInGoogleDao totalStudentsInGoogleDao;

    public TotalStudentsInGoogleExtractor() {
        totalStudentsInGoogleDao = new TotalStudentsInGoogleDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalStudentsInGoogleDao.getTotalStudentsFromPrivateDB();
        totalStudentsInGoogleDao.updateTotalStudentsInGoogle(totalStudents);
        return totalStudentsInGoogleDao.getTotalStudentsInGoogle();
    }
}
