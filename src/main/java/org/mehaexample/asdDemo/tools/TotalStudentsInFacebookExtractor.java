package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInFacebookDao;

public class TotalStudentsInFacebookExtractor implements PrivateToPublicExtractor<Integer> {
    private TotalStudentsInFacebookDao totalStudentsInFacebookDao;

    public TotalStudentsInFacebookExtractor() {
        totalStudentsInFacebookDao = new TotalStudentsInFacebookDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalStudentsInFacebookDao.getTotalStudentsFromPrivateDB();
        totalStudentsInFacebookDao.updateTotalStudentsInFacebook(totalStudents);
        return totalStudentsInFacebookDao.getTotalStudentsInFacebook();
    }
}
