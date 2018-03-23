package org.mehaexample.asdDemo.model.alignpublic;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class StudentsPublic {
  private int publicId;
  private String firstName;
  private Blob photo;
  private int graduationYear;
  private List<WorkExperiencesPublic> workExperiences = new ArrayList<>(0);
  private List<UndergraduatesPublic> undergraduates = new ArrayList<>(0);

  public StudentsPublic(int publicId, String firstName, Blob photo, int graduationYear) {
    this.publicId = publicId;
    this.firstName = firstName;
    this.photo = photo;
    this.graduationYear = graduationYear;
  }

  public StudentsPublic() { }

  public int getPublicId() {
    return publicId;
  }

  public void setPublicId(int publicId) {
    this.publicId = publicId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Blob getPhoto() {
    return photo;
  }

  public void setPhoto(Blob photo) {
    this.photo = photo;
  }

  public int getGraduationYear() {
    return graduationYear;
  }

  public void setGraduationYear(int graduationYear) {
    this.graduationYear = graduationYear;
  }

  public List<WorkExperiencesPublic> getWorkExperiences() {
    return workExperiences;
  }

  public void setWorkExperiences(List<WorkExperiencesPublic> workExperiencePublics) {
    this.workExperiences = workExperiencePublics;
  }

  public List<UndergraduatesPublic> getUndergraduates() {
    return undergraduates;
  }

  public void setUndergraduates(List<UndergraduatesPublic> undergraduates) {
    this.undergraduates = undergraduates;
  }
}
