package com.microlabs.admin.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class EmpCreationForm  extends ActionForm{

	private String location;
	private LinkedList locationId;
	private LinkedList locationName;
	private String department;
	private LinkedList deptId;
	private LinkedList deptName;
	private String designation;
	private LinkedList desgnId;
	private LinkedList desgName;
	private String employeeNo;
	private String reqEmpNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String contactNo;
	
	private String emailID;
	private String loginName;
	private String password;
	private String message;
	private String activation;
	private String empStatus;
	private String employeeGroup;
	
	private LinkedList groupList;
	private String searchEmployee;
	private String title;
	private String fullName;
	private String favoriteQuest;
	private String answer;

	private int totalRecords;
	private int startRecord;
	private int endRecord;
	
	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}

	public int getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}

	public String getReqEmpNo() {
		return reqEmpNo;
	}

	public void setReqEmpNo(String reqEmpNo) {
		this.reqEmpNo = reqEmpNo;
	}

	public String getFavoriteQuest() {
		return favoriteQuest;
	}

	public void setFavoriteQuest(String favoriteQuest) {
		this.favoriteQuest = favoriteQuest;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getSearchEmployee() {
		return searchEmployee;
	}

	public void setSearchEmployee(String searchEmployee) {
		this.searchEmployee = searchEmployee;
	}

	public String getActivation() {
		return activation;
	}

	public void setActivation(String activation) {
		this.activation = activation;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public String getEmployeeGroup() {
		return employeeGroup;
	}

	public void setEmployeeGroup(String employeeGroup) {
		this.employeeGroup = employeeGroup;
	}

	public LinkedList getGroupList() {
		return groupList;
	}

	public void setGroupList(LinkedList groupList) {
		this.groupList = groupList;
	}

	public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LinkedList getLocationId() {
		return locationId;
	}

	public void setLocationId(LinkedList locationId) {
		this.locationId = locationId;
	}

	public LinkedList getLocationName() {
		return locationName;
	}

	public void setLocationName(LinkedList locationName) {
		this.locationName = locationName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public LinkedList getDeptId() {
		return deptId;
	}

	public void setDeptId(LinkedList deptId) {
		this.deptId = deptId;
	}

	public LinkedList getDeptName() {
		return deptName;
	}

	public void setDeptName(LinkedList deptName) {
		this.deptName = deptName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public LinkedList getDesgnId() {
		return desgnId;
	}

	public void setDesgnId(LinkedList desgnId) {
		this.desgnId = desgnId;
	}

	public LinkedList getDesgName() {
		return desgName;
	}

	public void setDesgName(LinkedList desgName) {
		this.desgName = desgName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
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

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	
	
	
	
	
	
}
