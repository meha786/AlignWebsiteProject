package org.mehaexample.asdDemo.model.alignadmin;

public class StudentBachelorInstitution {
  private String institutionName;
  private long totalStudents;

  public StudentBachelorInstitution(String institutionName, long totalStudents) {
    this.institutionName = institutionName;
    this.totalStudents = totalStudents;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
