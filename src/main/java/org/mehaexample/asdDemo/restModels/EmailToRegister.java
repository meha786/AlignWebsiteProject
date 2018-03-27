package org.mehaexample.asdDemo.restModels;

public class EmailToRegister {
	private String email;

	public EmailToRegister(String email) {
		super();
		this.email = email;
	}

	public EmailToRegister() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;  
	}
}
