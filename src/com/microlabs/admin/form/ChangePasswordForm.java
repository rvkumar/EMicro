package com.microlabs.admin.form;

import org.apache.struts.action.ActionForm;

public class ChangePasswordForm extends ActionForm{
	
	
	private String message;
	private String oldPassword1;
	private String newPassword;
	private String confirmNewPassword;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getOldPassword1() {
		return oldPassword1;
	}
	public void setOldPassword1(String oldPassword1) {
		this.oldPassword1 = oldPassword1;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmNewPassword() {
		return confirmNewPassword;
	}
	public void setConfirmNewPassword(String confirmNewPassword) {
		this.confirmNewPassword = confirmNewPassword;
	}
	
	
}
