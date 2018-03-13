package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInSeattleDao;

public class TotalStudentsInSeattleExtractor implements PrivateToPublicExtractor<Integer> {
    private TotalStudentsInSeattleDao totalStudentsInSeattleDao;

    public TotalStudentsInSeattleExtractor() {
        totalStudentsInSeattleDao = new TotalStudentsInSeattleDao();
    }

    @Override
    public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
        int totalStudents = totalStudentsInSeattleDao.getTotalStudentsInSeattleFromPrivateDatabase();
        totalStudentsInSeattleDao.updateTotalStudentsInSeattleInPublicDatabase(totalStudents);
        return totalStudentsInSeattleDao.getTotalStudentsInSeattleFromPublicDatabase();
    }
}
