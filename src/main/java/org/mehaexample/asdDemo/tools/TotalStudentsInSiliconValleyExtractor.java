package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalStudentsInSiliconValleyDao;

public class TotalStudentsInSiliconValleyExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalStudentsInSiliconValleyDao totalStudentsInSiliconValleyDao;

  public TotalStudentsInSiliconValleyExtractor() {
    totalStudentsInSiliconValleyDao = new TotalStudentsInSiliconValleyDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPrivateDatabase();
    totalStudentsInSiliconValleyDao.updateTotalStudentsInSiliconValleyInPublicDatabase(totalStudents);
    return totalStudentsInSiliconValleyDao.getTotalStudentsInSiliconValleyFromPublicDatabase();
  }
}
