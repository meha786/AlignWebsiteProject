//package org.alignprivate.asd.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//public class TotalStudentsInSiliconValleyExtractorTest {
//  private static final int TEST_VALUE_FROM_DB = 3;
//  private TotalStudentsInSiliconValleyExtractor totalStudentsInSiliconValleyExtractor;
//
//  @Before
//  public void init() {
//    totalStudentsInSiliconValleyExtractor = new TotalStudentsInSiliconValleyExtractor();
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(totalStudentsInSiliconValleyExtractor.extractFromPrivateAndLoadToPublic() == TEST_VALUE_FROM_DB);
//  }
//}