package org.mehaexample.asdDemo.model;

public class CurrentMajors {
  private int currentMajorId;
  private Students student;
  private Majors major;

  public CurrentMajors() { }

  public int getCurrentMajorId() {
    return currentMajorId;
  }

  public void setCurrentMajorId(int currentMajorId) {
    this.currentMajorId = currentMajorId;
  }

  public Students getStudent() {
    return student;
  }

  public void setStudent(Students student) {
    this.student = student;
  }

  public Majors getMajor() {
    return major;
  }

  public void setMajor(Majors major) {
    this.major = major;
  }
}
