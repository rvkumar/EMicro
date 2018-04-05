package com.microlabs.admin.form;

import org.apache.struts.action.ActionForm;

public class ActivateUserForm extends ActionForm{
	
	private String userId;
	private String userName;
	private String message;
	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
