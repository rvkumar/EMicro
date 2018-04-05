package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class HRRecruitmentForm extends ActionForm{

	private String recuritmentID;
	private  ArrayList<String> groupIds;
	private String jobTitle;
	private String department;
	private String primaryLocation;
	private String otherLocation;
	private String empStatus;
	private String empStatusType;
	private String totalEmp;
	private String shiftType;
	private String jobDescription;
	
	private String qualifications;
	private String experience;
	private String industryType;
	private String saleryOffered;
	private String approvalStatus;
	private String noOfPersonsApproved;
	private String approvedpersons;
	private String country;
	private String state;
	private String city;
	private String plant;
	private String requesterName;
	private String requestDate;
	private String resons;
	
	
	private String approver1;


	private String employeeName;
	private String approverNumber;
	private String employeeNumber;
	private String designation;
	
	
	private String message;
	private String userDepart;
	private LinkedList departmentName;
	private String location;
	private LinkedList locationId;
	private LinkedList locationName;
	private String moreUrl;
	private String applyUrl;
	
	
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getApproverNumber() {
		return approverNumber;
	}
	public void setApproverNumber(String approverNumber) {
		this.approverNumber = approverNumber;
	}
	public String getApprover1() {
		return approver1;
	}
	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	
	public String getResons() {
		return resons;
	}
	public void setResons(String resons) {
		this.resons = resons;
	}
	public String getRequesterName() {
		return requesterName;
	}
	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getNoOfPersonsApproved() {
		return noOfPersonsApproved;
	}
	public void setNoOfPersonsApproved(String noOfPersonsApproved) {
		this.noOfPersonsApproved = noOfPersonsApproved;
	}
	public String getApprovedpersons() {
		return approvedpersons;
	}
	public void setApprovedpersons(String approvedpersons) {
		this.approvedpersons = approvedpersons;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getOtherLocation() {
		return otherLocation;
	}
	public void setOtherLocation(String otherLocation) {
		this.otherLocation = otherLocation;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public String getTotalEmp() {
		return totalEmp;
	}
	public void setTotalEmp(String totalEmp) {
		this.totalEmp = totalEmp;
	}
	public String getShiftType() {
		return shiftType;
	}
	public void setShiftType(String shiftType) {
		this.shiftType = shiftType;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getQualifications() {
		return qualifications;
	}
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getSaleryOffered() {
		return saleryOffered;
	}
	public void setSaleryOffered(String saleryOffered) {
		this.saleryOffered = saleryOffered;
	}
	public String getRecuritmentID() {
		return recuritmentID;
	}
	public void setRecuritmentID(String recuritmentID) {
		this.recuritmentID = recuritmentID;
	}
	public ArrayList<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(ArrayList<String> groupIds) {
		this.groupIds = groupIds;
	}
	
	public String getEmpStatusType() {
		return empStatusType;
	}
	public void setEmpStatusType(String empStatusType) {
		this.empStatusType = empStatusType;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public LinkedList getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(LinkedList departName) {
		this.departmentName = departName;
	}
	
	public String getUserDepart() {
		return userDepart;
	}
	public void setUserDepart(String udepart) {
		this.userDepart = udepart;
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
	public void setLocationId(LinkedList locId) {
		this.locationId = locId;
	}
	
	public LinkedList getLocationName() {
		return locationName;
	}
	public void setLocationName(LinkedList locName) {
		this.locationName = locName;
	}
	
	public String getMoreUrl() {
		return moreUrl;
	}
	public void setMoreUrl(String moreUrl) {
		this.moreUrl = moreUrl;
	}
	
	public String getApplyUrl() {
		return applyUrl;
	}
	public void setApplyUrl(String applyUrl) {
		this.applyUrl = applyUrl;
	}
	
}
