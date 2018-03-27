package org.mehaexample.asdDemo.model.alignadmin;

import java.sql.Timestamp;

public class AdminLogins {
  private String email;
  private String adminPassword;
  private String registrationKey;
  private Timestamp loginTime;
  private Timestamp keyExpiration;
  private boolean confirmed;

  public AdminLogins(String email, String adminPassword, String registrationKey, Timestamp loginTime,
                     Timestamp keyExpiration, boolean confirmed) {
    this.email = email;
    this.adminPassword = adminPassword;
    this.registrationKey = registrationKey;
    this.loginTime = loginTime;
    this.keyExpiration = keyExpiration;
    this.confirmed = confirmed;
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

  public Timestamp getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Timestamp loginTime) {
    this.loginTime = loginTime;
  }

  public Timestamp getKeyExpiration() {
    return keyExpiration;
  }

  public void setKeyExpiration(Timestamp keyExpiration) {
    this.keyExpiration = keyExpiration;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }
}