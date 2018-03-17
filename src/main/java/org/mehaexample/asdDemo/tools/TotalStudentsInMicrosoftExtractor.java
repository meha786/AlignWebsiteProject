package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInMicrosoftDao;

public class TotalStudentsInMicrosoftExtractor implements PrivateToPublicExtractor<Integer> {
    private TotalStudentsInMicrosoftDao totalStudentsInMicrosoftDao;

    public TotalStudentsInMicrosoftExtractor() {
        totalStudentsInMicrosoftDao = new TotalStudentsInMicrosoftDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalStudentsInMicrosoftDao.getTotalStudentsFromPrivateDB();
        totalStudentsInMicrosoftDao.updateTotalStudentsInMicrosoft(totalStudents);
        return totalStudentsInMicrosoftDao.getTotalStudentsInMicrosoft();
    }
}
