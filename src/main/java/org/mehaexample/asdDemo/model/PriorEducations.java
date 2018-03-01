package org.mehaexample.asdDemo.model;


import java.util.Date;

import org.mehaexample.asdDemo.enums.DegreeCandidacy;

public class PriorEducations {
  private int priorEducationId;
  private Date graduationDate;
  private float gpa;
  private DegreeCandidacy degreeCandidacy;
  private Students student;
  private Institutions institution;
  private Majors major;

  public PriorEducations() { 
	  super();
  }

  public int getPriorEducationId() {
    return priorEducationId;
  }

  public void setPriorEducationId(int priorEducationId) {
    this.priorEducationId = priorEducationId;
  }

  public Date getGraduationDate() {
    return graduationDate;
  }

  public void setGraduationDate(Date graduationDate) {
    this.graduationDate = graduationDate;
  }

  public float getGpa() {
    return gpa;
  }

  public void setGpa(float gpa) {
    this.gpa = gpa;
  }

  public DegreeCandidacy getDegreeCandidacy() {
    return degreeCandidacy;
  }

  public void setDegreeCandidacy(DegreeCandidacy degreeCandidacy) {
    this.degreeCandidacy = degreeCandidacy;
  }

  public Students getStudent() {
    return student;
  }

  public void setStudent(Students student) {
    this.student = student;
  }

  public Institutions getInstitution() {
    return institution;
  }

  public void setInstitution(Institutions institution) {
    this.institution = institution;
  }

  public Majors getMajor() {
    return major;
  }

  public void setMajor(Majors major) {
    this.major = major;
  }
}
