//package org.mehaexample.asdDemo.tools;
//
//import java.sql.SQLException;
//import java.util.List;
//
//import org.mehaexample.asdDemo.enums.Campus;
//import org.mehaexample.asdDemo.model.alignadmin.GenderRatio;
//import org.mehaexample.asdDemo.scripts.GenderRatioDao;
//
//public class GenderRatioExtractor {
//  private GenderRatioDao genderRatioDao;
//
//  public GenderRatioExtractor() {
//    genderRatioDao = new GenderRatioDao();
//  }
//
//  public void extractFromPrivateAndLoadToPublic() throws SQLException {
//    List<GenderRatio> seattleGenderRatio =
//            genderRatioDao.getYearlyGenderRatioFromPrivateDatabase(Campus.SEATTLE);
//    List<GenderRatio> bostonGenderRatio =
//            genderRatioDao.getYearlyGenderRatioFromPrivateDatabase(Campus.BOSTON);
//    List<GenderRatio> charlotteGenderRatio =
//            genderRatioDao.getYearlyGenderRatioFromPrivateDatabase(Campus.CHARLOTTE);
//    List<GenderRatio> siliconValleyGenderRatio =
//            genderRatioDao.getYearlyGenderRatioFromPrivateDatabase(Campus.SILICON_VALLEY);
//    genderRatioDao.insertYearlyGenderRatioToPublicDatabase(Campus.SEATTLE, seattleGenderRatio);
//    genderRatioDao.insertYearlyGenderRatioToPublicDatabase(Campus.BOSTON, bostonGenderRatio);
//    genderRatioDao.insertYearlyGenderRatioToPublicDatabase(Campus.CHARLOTTE, charlotteGenderRatio);
//    genderRatioDao.insertYearlyGenderRatioToPublicDatabase(Campus.SILICON_VALLEY, siliconValleyGenderRatio);
//  }
//}
