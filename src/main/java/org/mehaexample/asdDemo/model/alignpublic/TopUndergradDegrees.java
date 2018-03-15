package org.mehaexample.asdDemo.model.alignpublic;

public class TopUndergradDegrees {
  private String undergradDegree;
  private long totalStudents;

  public TopUndergradDegrees(String undergradDegree, long totalStudents) {
    this.undergradDegree = undergradDegree;
    this.totalStudents = totalStudents;
  }

  public TopUndergradDegrees() { }

  public String getUndergradDegree() {
    return undergradDegree;
  }

  public void setUndergradDegree(String undergradDegree) {
    this.undergradDegree = undergradDegree;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
