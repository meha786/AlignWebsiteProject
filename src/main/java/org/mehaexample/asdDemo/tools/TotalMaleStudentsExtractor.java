package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalMaleStudentsDao;

public class TotalMaleStudentsExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalMaleStudentsDao totalMaleStudentsDao;

  public TotalMaleStudentsExtractor() {
    totalMaleStudentsDao = new TotalMaleStudentsDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalMaleStudentsDao.getTotalMaleStudentsFromPrivateDatabase();
    totalMaleStudentsDao.updateTotalMaleStudentsInPublicDatabase(totalStudents);
    return totalMaleStudentsDao.getTotalMaleStudentsFromPublicDatabase();
  }
}
