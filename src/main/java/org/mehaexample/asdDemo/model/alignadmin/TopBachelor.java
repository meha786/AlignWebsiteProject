package org.mehaexample.asdDemo.model.alignadmin;

public class TopBachelor {
  private String degree;
  private long totalStudents;

  public TopBachelor(String degree, long totalStudents) {
    this.degree = degree;
    this.totalStudents = totalStudents;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
