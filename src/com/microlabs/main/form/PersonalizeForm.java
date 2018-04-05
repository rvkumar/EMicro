package com.microlabs.main.form;

import org.apache.struts.action.ActionForm;

public class PersonalizeForm extends ActionForm{
	private String empOldPwd;
	private String oldPassword; 
	private String newPassword;
	private String conformPassword;
	private String message;
	private String message2;
	private String password;
	private String favoritQues;
	private String favAns;
	
	
	
	
	public String getFavoritQues() {
		return favoritQues;
	}
	public void setFavoritQues(String favoritQues) {
		this.favoritQues = favoritQues;
	}
	public String getFavAns() {
		return favAns;
	}
	public void setFavAns(String favAns) {
		this.favAns = favAns;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmpOldPwd() {
		return empOldPwd;
	}
	public void setEmpOldPwd(String empOldPwd) {
		this.empOldPwd = empOldPwd;
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
	public String getConformPassword() {
		return conformPassword;
	}
	public void setConformPassword(String conformPassword) {
		this.conformPassword = conformPassword;
	}
	
	
	
}
