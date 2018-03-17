package org.mehaexample.asdDemo.model;

public class AdminLogins {
  private String email;
  private String adminPassword;

  public AdminLogins(String email, String adminPassword) {
    this.email = email;
    this.adminPassword = adminPassword;
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
}