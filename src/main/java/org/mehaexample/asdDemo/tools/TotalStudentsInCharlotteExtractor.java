package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInCharlotteDao;

public class TotalStudentsInCharlotteExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalStudentsInCharlotteDao totalStudentsInCharlotteDao;

  public TotalStudentsInCharlotteExtractor() {
    totalStudentsInCharlotteDao = new TotalStudentsInCharlotteDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPrivateDatabase();
    totalStudentsInCharlotteDao.updateTotalStudentsInCharlotteInPublicDatabase(totalStudents);
    return totalStudentsInCharlotteDao.getTotalStudentsInCharlotteFromPublicDatabase();
  }
}
