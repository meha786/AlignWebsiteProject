package org.mehaexample.asdDemo.tools;

import java.sql.SQLException;

public interface PrivateToPublicExtractor<T> {
  /**
   * Extract the related information from private database
   * and store it in the public database. Value can be in terms
   * of Integer or List of Strings.
   *
   * @return the value stored in the public database for
   *         the specific query; not null.
   * @throws SQLException when the connection to the DB
   *                      has something wrong.
   */
  T extractFromPrivateAndLoadToPublic() throws SQLException;
}
