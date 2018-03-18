package org.mehaexample.asdDemo.model.alignprivate;

import java.util.Date;

import org.mehaexample.asdDemo.enums.DegreeCandidacy;

public class PriorEducations {
  private int priorEducationId;
  private String institutionName;
  private String majorName;
  private Date graduationDate;
  private float gpa;
  private DegreeCandidacy degreeCandidacy;
  private String neuId;

  public PriorEducations(String institutionName, String majorName, Date graduationDate,
                         float gpa, DegreeCandidacy degreeCandidacy, String neuId) {
    this.institutionName = institutionName;
    this.majorName = majorName;
    this.graduationDate = graduationDate;
    this.gpa = gpa;
    this.degreeCandidacy = degreeCandidacy;
    this.neuId = neuId;
  }

  public PriorEducations() { }

  public int getPriorEducationId() {
    return priorEducationId;
  }

  public void setPriorEducationId(int priorEducationId) {
    this.priorEducationId = priorEducationId;
  }

  public String getInstitutionName() {
    return institutionName;
  }

  public void setInstitutionName(String institutionName) {
    this.institutionName = institutionName;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
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

  public String getNeuId() {
    return neuId;
  }

  public void setNeuId(String neuId) {
    this.neuId = neuId;
  }
}