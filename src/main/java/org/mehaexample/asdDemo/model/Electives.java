package org.mehaexample.asdDemo.model;

import org.mehaexample.asdDemo.enums.Term;

public class Electives {
  private int electiveId;

  private String neuId;
  private String courseId;
  private Term courseTerm;
  private int courseYear;

  public Electives(String neuId, String courseId, Term courseTerm, int courseYear) {
    this.neuId = neuId;
    this.courseId = courseId;
    this.courseTerm = courseTerm;
    this.courseYear = courseYear;
  }

  public Electives() {
    super();
  }

  public int getElectiveId() {
    return electiveId;
  }

  public void setElectiveId(int electiveId) {
    this.electiveId = electiveId;
  }

  public String getNeuId() {
    return neuId;
  }

  public void setNeuId(String neuId) {
    this.neuId = neuId;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public Term getCourseTerm() {
    return courseTerm;
  }

  public void setCourseTerm(Term courseTerm) {
    this.courseTerm = courseTerm;
  }

  public int getCourseYear() {
    return courseYear;
  }

  public void setCourseYear(int courseYear) {
    this.courseYear = courseYear;
  }

}