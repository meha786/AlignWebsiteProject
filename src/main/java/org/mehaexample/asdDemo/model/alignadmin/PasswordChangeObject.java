package org.mehaexample.asdDemo.model.alignadmin;

public class PasswordChangeObject {
	 private String oldPassword; 
	 private String newPassword;
	 
	public PasswordChangeObject(String email, String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	public PasswordChangeObject() {
		super();
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	} 	 
}
