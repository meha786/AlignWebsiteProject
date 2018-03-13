//package org.alignprivate.asd.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//public class TotalStudentsInBostonExtractorTest {
//  private static final int VALUE_FROM_DB = 1;
//  private TotalStudentsInBostonExtractor totalStudentsInBostonExtractor;
//
//  @Before
//  public void init() {
//    totalStudentsInBostonExtractor = new TotalStudentsInBostonExtractor();
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(totalStudentsInBostonExtractor.extractFromPrivateAndLoadToPublic() == VALUE_FROM_DB);
//  }
//}