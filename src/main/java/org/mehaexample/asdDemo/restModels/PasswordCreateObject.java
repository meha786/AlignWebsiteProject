package org.mehaexample.asdDemo.restModels;

public class PasswordCreateObject {
	private String email;
	private String password;
	private String registrationKey;
	
	public PasswordCreateObject(String email, String password, String registrationKey) {
		super();
		this.email = email;
		this.password = password;
		this.registrationKey = registrationKey;
	}

	public PasswordCreateObject(){
		super();	
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRegistrationKey() {
		return registrationKey;
	}

	public void setRegistrationKey(String registrationKey) {
		this.registrationKey = registrationKey;
	}
}
