package org.mehaexample.asdDemo.model.alignadmin;

public class TopEmployer {
  private String companyName;
  private long totalStudents;

  public TopEmployer(String companyName, long totalStudents) {
    this.companyName = companyName;
    this.totalStudents = totalStudents;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
