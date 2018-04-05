package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;

public class BirthdayEmpForm extends ActionForm{

  private String emplyeeName;
  private String dept;
  private String location;
  private String extention;
  private String empPhoto;
  private String message;
  private String emailID;
  private String ipphone;
  
  private String currentDate;
  
  
  
	public String getCurrentDate() {
	return currentDate;
}
public void setCurrentDate(String currentDate) {
	this.currentDate = currentDate;
}
	public String getIpphone() {
	return ipphone;
}
public void setIpphone(String ipphone) {
	this.ipphone = ipphone;
}
	public String getEmailID() {
	return emailID;
}
public void setEmailID(String emailID) {
	this.emailID = emailID;
}

	public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
	public String getEmpPhoto() {
	return empPhoto;
}
public void setEmpPhoto(String empPhoto) {
	this.empPhoto = empPhoto;
}
	public String getEmplyeeName() {
		return emplyeeName;
	}
	public void setEmplyeeName(String emplyeeName) {
		this.emplyeeName = emplyeeName;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getExtention() {
		return extention;
	}
	public void setExtention(String extention) {
		this.extention = extention;
	}
  
  
}
