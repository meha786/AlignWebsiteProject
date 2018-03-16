package org.mehaexample.asdDemo.model.alignprivate;

import java.sql.Timestamp;

public class StudentLogins {
  private String email;
  private String studentPassword;
  private String registrationKey;
  private Timestamp keyExpiration;

  public StudentLogins(String email, String studentPassword, String registrationKey, Timestamp keyExpiration) {
    this.email = email;
    this.studentPassword = studentPassword;
    this.registrationKey = registrationKey;
    this.keyExpiration = keyExpiration;
  }

  public StudentLogins() { }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStudentPassword() {
    return studentPassword;
  }

  public void setStudentPassword(String studentPassword) {
    this.studentPassword = studentPassword;
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