package com.microlabs.admin.form;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class UserForm extends ActionForm{
	private String message;
	private String employeeNumber;
	private String userName;
	private String password;
	private String fullName;
	private String designation;
	private String groupID;
	private String countryName;
	private String state;
	private String plant;
	private String department;
	private String employeeType;
	
	private String userId;
	
	
	private ArrayList groupIdList=new ArrayList();
	private ArrayList groupValueList=new ArrayList();
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmployeeType() {
		return employeeType;
	}
	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public ArrayList getGroupIdList() {
		return groupIdList;
	}
	public void setGroupIdList(ArrayList groupIdList) {
		this.groupIdList = groupIdList;
	}
	public ArrayList getGroupValueList() {
		return groupValueList;
	}
	public void setGroupValueList(ArrayList groupValueList) {
		this.groupValueList = groupValueList;
	}
	
	
	

}
