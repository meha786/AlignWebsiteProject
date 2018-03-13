package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;
import java.util.List;

import org.mehaexample.asdDemo.scripts.TopTenBachelorsDegreeDao;

public class TopTenBachelorsDegreeExtractor implements PrivateToPublicExtractor<List<String>> {
  private TopTenBachelorsDegreeDao topTenBachelorsDegreeDao;

  public TopTenBachelorsDegreeExtractor() {
    topTenBachelorsDegreeDao = new TopTenBachelorsDegreeDao();
  }

  @Override
  public List<String> extractFromPrivateAndLoadToPublic() throws SQLException {
    List<String> listOfBachelorsDegree = topTenBachelorsDegreeDao.getTopTenBachelorsDegreeFromPrivateDatabase();
    topTenBachelorsDegreeDao.updateTopTenBachelorsDegreeInPublicDatabase(listOfBachelorsDegree);
    return topTenBachelorsDegreeDao.getTopTenBachelorsDegreeFromPublicDatabase();
  }
}
