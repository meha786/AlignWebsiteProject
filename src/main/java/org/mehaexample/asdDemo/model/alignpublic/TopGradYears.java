package org.mehaexample.asdDemo.model.alignpublic;

public class TopGradYears {
  private int graduationYear;
  private long totalStudents;

  public TopGradYears(int graduationYear, long totalStudents) {
    this.graduationYear = graduationYear;
    this.totalStudents = totalStudents;
  }

  public int getGraduationYear() {
    return graduationYear;
  }

  public void setGraduationYear(int graduationYear) {
    this.graduationYear = graduationYear;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
