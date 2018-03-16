package org.mehaexample.asdDemo.model.alignadmin;

import java.sql.Timestamp;

public class AdminLogins {
  private String email;
  private String adminPassword;
  private String registrationKey;
  private Timestamp keyExpiration;

  public AdminLogins(String email, String adminPassword, String registrationKey, Timestamp keyExpiration) {
    this.email = email;
    this.adminPassword = adminPassword;
    this.registrationKey = registrationKey;
    this.keyExpiration = keyExpiration;
  }

  public AdminLogins() { }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAdminPassword() {
    return adminPassword;
  }

  public void setAdminPassword(String adminPassword) {
    this.adminPassword = adminPassword;
  }

  public String getRegistrationKey() {
    return registrationKey;
  }

  public void setRegistrationKey(String registrationKey) {
    this.registrationKey = registrationKey;
  }

  public Timestamp getKeyExpiration() {
    return keyExpiration;
  }

  public void setKeyExpiration(Timestamp keyExpiration) {
    this.keyExpiration = keyExpiration;
  }
}