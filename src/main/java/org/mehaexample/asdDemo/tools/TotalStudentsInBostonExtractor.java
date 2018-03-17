package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInBostonDao;

public class TotalStudentsInBostonExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalStudentsInBostonDao totalStudentsInBostonDao;

  public TotalStudentsInBostonExtractor() {
    totalStudentsInBostonDao = new TotalStudentsInBostonDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalStudentsInBostonDao.getTotalStudentsInBostonFromPrivateDatabase();
    totalStudentsInBostonDao.updateTotalStudentsInBostonInPublicDatabase(totalStudents);
    return totalStudentsInBostonDao.getTotalStudentsInBostonFromPublicDatabase();
  }
}
