package org.mehaexample.asdDemo.model.alignprivate;

public class StudentBasicInfo {
  private String firstName;
  private String lastName;
  private String neuId;

  public StudentBasicInfo(String firstName, String lastName, String neuId) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.neuId = neuId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getNeuId() {
    return neuId;
  }

  public void setNeuId(String neuId) {
    this.neuId = neuId;
  }
}
