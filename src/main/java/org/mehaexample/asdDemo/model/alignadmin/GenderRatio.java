package org.mehaexample.asdDemo.model.alignadmin;

public class GenderRatio {
  private int entryYear;
  private long male;
  private long female;

  public GenderRatio(int entryYear, long male, long female) {
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

  public long getMale() {
    return male;
  }

  public void setMale(long male) {
    this.male = male;
  }

  public long getFemale() {
    return female;
  }

  public void setFemale(long female) {
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
    result = 31 * result + (int) (male ^ (male >>> 32));
    result = 31 * result + (int) (female ^ (female >>> 32));
    return result;
  }
}