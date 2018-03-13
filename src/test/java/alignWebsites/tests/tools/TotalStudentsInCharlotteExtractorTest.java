//package org.alignprivate.asd.tools;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.SQLException;
//
//public class TotalStudentsInCharlotteExtractorTest {
//  private static final int TEST_VALUE_FROM_DB = 2;
//  private TotalStudentsInCharlotteExtractor totalStudentsInCharlotteExtractor;
//
//  @Before
//  public void init() {
//    totalStudentsInCharlotteExtractor = new TotalStudentsInCharlotteExtractor();
//  }
//
//  @Test
//  public void extractFromPrivateAndLoadToPublicTest() throws SQLException {
//    Assert.assertTrue(totalStudentsInCharlotteExtractor.extractFromPrivateAndLoadToPublic() == TEST_VALUE_FROM_DB);
//  }
//}