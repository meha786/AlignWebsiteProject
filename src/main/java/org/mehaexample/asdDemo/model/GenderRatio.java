package org.mehaexample.asdDemo.model;

public class GenderRatio {
  private int entryYear;
  private int male;
  private int female;

  public GenderRatio(int entryYear, int male, int female) {
    this.entryYear = entryYear;
    this.male = male;
    this.female = female;
  }

  public GenderRatio() { }

  public int getEntryYear() {
    return entryYear;
  }

  public void setEntryYear(int entryYear) {
    this.entryYear = entryYear;
  }

  public int getMale() {
    return male;
  }

  public void setMale(int male) {
    this.male = male;
  }

  public int getFemale() {
    return female;
  }

  public void setFemale(int female) {
    this.female = female;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    GenderRatio that = (GenderRatio) o;

    if (entryYear != that.entryYear) return false;
    if (male != that.male) return false;
    return female == that.female;
  }

  @Override
  public int hashCode() {
    int result = entryYear;
    result = 31 * result + male;
    result = 31 * result + female;
    return result;
  }
}