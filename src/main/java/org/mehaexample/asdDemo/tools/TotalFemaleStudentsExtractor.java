package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

import org.mehaexample.asdDemo.scripts.TotalFemaleStudentsDao;

public class TotalFemaleStudentsExtractor implements PrivateToPublicExtractor<Integer> {
  private TotalFemaleStudentsDao totalFemaleStudentsDao;

  public TotalFemaleStudentsExtractor() {
    totalFemaleStudentsDao = new TotalFemaleStudentsDao();
  }

  @Override
  public Integer extractFromPrivateAndLoadToPublic() throws SQLException {
    int totalStudents = totalFemaleStudentsDao.getTotalFemaleStudentsFromPrivateDatabase();
    totalFemaleStudentsDao.updateTotalFemaleStudentsInPublicDatabase(totalStudents);
    return totalFemaleStudentsDao.getTotalFemaleStudentsFromPublicDatabase();
  }
}
