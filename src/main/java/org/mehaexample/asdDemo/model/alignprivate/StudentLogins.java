package org.mehaexample.asdDemo.model.alignprivate;

public class StudentLogins {
  private String email;
  private String studentPassword;

  public StudentLogins(String email, String studentPassword) {
    this.email = email;
    this.studentPassword = studentPassword;
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
}