package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalExperiencedStudentsDao;

public class TotalExperiencedStudentsExtractor implements PrivateToPublicExtractor<Integer>  {
    private TotalExperiencedStudentsDao totalExperiencedStudentsDao;

    public TotalExperiencedStudentsExtractor() {
        totalExperiencedStudentsDao = new TotalExperiencedStudentsDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalExperiencedStudentsDao.getTotalStudentsFromPrivateDB();
        totalExperiencedStudentsDao.updateTotalExperiencedStudents(totalStudents);
        return totalExperiencedStudentsDao.getTotalExperiencedStudents();
    }
}
