package org.mehaexample.asdDemo.model.alignpublic;

public class TopUndergradSchools {
  private String undergradSchool;
  private long totalStudents;

  public TopUndergradSchools(String undergradSchool, long totalStudents) {
    this.undergradSchool = undergradSchool;
    this.totalStudents = totalStudents;
  }

  public TopUndergradSchools() { }

  public String getUndergradSchool() {
    return undergradSchool;
  }

  public void setUndergradSchool(String undergradSchool) {
    this.undergradSchool = undergradSchool;
  }

  public long getTotalStudents() {
    return totalStudents;
  }

  public void setTotalStudents(long totalStudents) {
    this.totalStudents = totalStudents;
  }
}
