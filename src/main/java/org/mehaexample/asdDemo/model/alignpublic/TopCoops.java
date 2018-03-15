package org.mehaexample.asdDemo.model.alignpublic;

public class TopCoops {
  private String coop;
  private long totalStudents;

  public TopCoops(String coop, long totalStudents) {
    this.coop = coop;
    this.totalStudents = totalStudents;
  }

  public TopCoops() { }

  public String getCoop() {
    return coop;
  }

  public void setCoop(String coop) {
    this.coop = coop;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
