package org.mehaexample.asdDemo.model.alignprivate;

import java.util.List;

public class StudentCoopList {
  private String neuId;
  private String firstName;
  private String lastName;
  private List<String> companies;

  public StudentCoopList(String neuId, String firstName, String lastName) {
    this.neuId = neuId;
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getNeuId() {
    return neuId;
  }

  public void setNeuId(String neuId) {
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

  public List<String> getCompanies() {
    return companies;
  }

  public void setCompanies(List<String> companies) {
    this.companies = companies;
  }
}
