package org.mehaexample.asdDemo.model.alignadmin;

public class ParamsObject {

	private String campus; 
	private String year;
	private String firstname;
	private String lastname;
	private String email;
	private String degreeyear;
	private String enrollmentstatus;
	private String company;
	private String beginindex;
	private String endindex;

	/**
	 * 
	 */
	public ParamsObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param campus
	 * @param year
	 * @param firstname
	 * @param lastname
	 * @param email
	 * @param degreeyear
	 * @param enrollmentstatus
	 * @param company
	 */
	public ParamsObject(String campus, String year, String firstname,
			String lastname, String email, String degreeyear,
			String enrollmentstatus, String company) {
		super();
		this.campus = campus;
		this.year = year;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.degreeyear = degreeyear;
		this.enrollmentstatus = enrollmentstatus;
		this.company = company;
	}
	/**
	 * @return the campus
	 */
	public String getCampus() {
		return campus;
	}
	/**
	 * @param campus the campus to set
	 */
	public void setCampus(String campus) {
		this.campus = campus;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the degreeyear
	 */
	public String getDegreeyear() {
		return degreeyear;
	}
	/**
	 * @param degreeyear the degreeyear to set
	 */
	public void setDegreeyear(String degreeyear) {
		this.degreeyear = degreeyear;
	}
	/**
	 * @return the enrollmentstatus
	 */
	public String getEnrollmentstatus() {
		return enrollmentstatus;
	}
	/**
	 * @param enrollmentstatus the enrollmentstatus to set
	 */
	public void setEnrollmentstatus(String enrollmentstatus) {
		this.enrollmentstatus = enrollmentstatus;
	}
	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * @return the beginindex
	 */
	public String getBeginindex() {
		return beginindex;
	}
	/**
	 * @param beginindex the beginindex to set
	 */
	public void setBeginindex(String beginindex) {
		this.beginindex = beginindex;
	}
	/**
	 * @return the endindex
	 */
	public String getEndindex() {
		return endindex;
	}
	/**
	 * @param endindex the endindex to set
	 */
	public void setEndindex(String endindex) {
		this.endindex = endindex;
	}
}
