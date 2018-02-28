package org.mehaexample.asdDemo.model;

import java.util.Date;

public class WorkExperiences {
  private int workExperienceId;
  private Date startDate;
  private Date endDate;
  private boolean currentJob;
  private String title;
  private String description;
  private Students student;
  private Companies company;

  public WorkExperiences() { }

  public int getWorkExperienceId() {
    return workExperienceId;
  }

  public void setWorkExperienceId(int workExperienceId) {
    this.workExperienceId = workExperienceId;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public boolean isCurrentJob() {
    return currentJob;
  }

  public void setCurrentJob(boolean currentJob) {
    this.currentJob = currentJob;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Students getStudent() {
    return student;
  }

  public void setStudent(Students student) {
    this.student = student;
  }

  public Companies getCompany() {
    return company;
  }

  public void setCompany(Companies company) {
    this.company = company;
  }
}
