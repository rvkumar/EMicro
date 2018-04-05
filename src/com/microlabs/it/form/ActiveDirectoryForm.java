package com.microlabs.it.form;

import org.apache.struts.action.ActionForm;

public class ActiveDirectoryForm extends ActionForm{
	
	private String employeeNo;
	private String reqADId;
	private String firstName;
	private String lastName;
	private String designation;
	private String department;
	
	private String location;
	private String contactDetails;
	private String assetDetails;
	private String hostName;
	private String IPNumber;
	private String adLoginName;
	
	private String requiredFolderAccess;
	private String changeReqForm;


	
	public String getReqADId() {
		return reqADId;
	}

	public void setReqADId(String reqADId) {
		this.reqADId = reqADId;
	}

	public String getChangeReqForm() {
		return changeReqForm;
	}

	public void setChangeReqForm(String changeReqForm) {
		this.changeReqForm = changeReqForm;
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public String getAssetDetails() {
		return assetDetails;
	}

	public void setAssetDetails(String assetDetails) {
		this.assetDetails = assetDetails;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIPNumber() {
		return IPNumber;
	}

	public void setIPNumber(String number) {
		IPNumber = number;
	}

	public String getAdLoginName() {
		return adLoginName;
	}

	public void setAdLoginName(String adLoginName) {
		this.adLoginName = adLoginName;
	}

	public String getRequiredFolderAccess() {
		return requiredFolderAccess;
	}

	public void setRequiredFolderAccess(String requiredFolderAccess) {
		this.requiredFolderAccess = requiredFolderAccess;
	}
	

}
