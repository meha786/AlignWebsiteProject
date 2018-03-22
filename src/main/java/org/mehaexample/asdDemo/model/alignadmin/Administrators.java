package org.mehaexample.asdDemo.model.alignadmin;

public class Administrators {
	private String administratorNeuId;
	private String email;
	private String firstName;
	private String middleName;
	private String lastName;

	public Administrators(String administratorNeuId,String email, String firstName, String middleName,
			String lastName){
		this.administratorNeuId = administratorNeuId;
		this.email = email;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public Administrators() { }

	public String getAdministratorNeuId() {
		return administratorNeuId;
	}

	public void setAdministratorNeuId(String administratorNeuId) {
		this.administratorNeuId = administratorNeuId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Administrators{" +
				"administratorNeuId='" + administratorNeuId + '\'' +
				", email='" + email + '\'' +
				", firstName='" + firstName + '\'' +
				", middleName='" + middleName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}