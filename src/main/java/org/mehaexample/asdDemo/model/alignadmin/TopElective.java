package org.mehaexample.asdDemo.model.alignadmin;

public class TopElective {
  private String courseName;
  private long totalStudents;

  public TopElective(String courseName, long totalStudents) {
    this.courseName = courseName;
    this.totalStudents = totalStudents;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
