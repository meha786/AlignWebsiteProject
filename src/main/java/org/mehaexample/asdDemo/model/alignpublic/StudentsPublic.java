package org.mehaexample.asdDemo.model.alignpublic;

import java.util.ArrayList;
import java.util.List;

public class StudentsPublic {
  private int publicId;
  private int graduationYear;
  private boolean visibleToPublic;
  private List<WorkExperiencesPublic> workExperiences = new ArrayList<>(0);
  private List<UndergraduatesPublic> undergraduates = new ArrayList<>(0);

  public StudentsPublic(int publicId, int graduationYear, boolean visibleToPublic) {
    this.publicId = publicId;
    this.graduationYear = graduationYear;
    this.visibleToPublic = visibleToPublic;
  }

  public StudentsPublic() { }

  public int getPublicId() {
    return publicId;
  }

  public void setPublicId(int publicId) {
    this.publicId = publicId;
  }

  public int getGraduationYear() {
    return graduationYear;
  }

  public void setGraduationYear(int graduationYear) {
    this.graduationYear = graduationYear;
  }

  public boolean isVisibleToPublic() {
    return visibleToPublic;
  }

  public void setVisibleToPublic(boolean visibleToPublic) {
    this.visibleToPublic = visibleToPublic;
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
