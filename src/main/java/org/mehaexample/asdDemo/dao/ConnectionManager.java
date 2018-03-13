package org.mehaexample.asdDemo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
  private String username;
  private String password;
  private String host;
  private int port;
  private String database;
  private Connection connection;

  public ConnectionManager(String username,
                           String password,
                           String host,
                           int port,
                           String database) {
    this.username = username;
    this.password = password;
    this.host = host;
    this.port = port;
    this.database = database;
  }

  /**
   * Get the connection to the public database.
   *
   * @return public database connection.
   * @throws SQLException when there is something wrong
   *                      with the connection setup.
   */
  public Connection connect() throws SQLException {
    try {
      Properties connectionProperties = new Properties();
      connectionProperties.setProperty("user", username);
      connectionProperties.setProperty("password", password);

      // Configure the SSL connection
      connectionProperties.setProperty("useSSL", "true");
      System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
      System.setProperty("javax.net.ssl.trustStoreType", "jks");
      System.setProperty("javax.net.ssl.keyStore", "/Users/valeryfardeli/.ssl/clientcertificate.p12");
      System.setProperty("javax.net.ssl.trustStore", "/Users/valeryfardeli/.ssl/mariadbserver.keystore");
      System.setProperty("javax.net.ssl.keyStorePassword", "AlienGUIse-93");

      String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
      connection = DriverManager.getConnection(url, connectionProperties);
    } catch (SQLException exception) {
      exception.printStackTrace();
      throw exception;
    }
    return connection;
  }

  /**
   * Get the current connection to a database instance.
   *
   * @return current connection.
   */
  public Connection getConnection() {
    return connection;
  }

  /**
   * Close the current connection to the database instance.
   *
   * @throws SQLException when there is something wrong when
   *                      closing the connection.
   */
  public void closeConnection() throws SQLException {
    if (this.connection != null) {
      connection.close();
    }
    connection = null;
  }
}