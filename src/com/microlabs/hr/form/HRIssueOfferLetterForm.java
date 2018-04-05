package com.microlabs.hr.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class HRIssueOfferLetterForm extends ActionForm{

	private int empno;
	private String userloginname;
	private String password;
	private  ArrayList<String> groupIds;
	private String recuritmentID;
	private String firstName;
	private String middleName;
	private String lastName;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String state;
	private String country;
	private String plant;
	private String empType;
	private String recruitmentId;
	
	private String pincode;
	private String gender;
	private String dateOfBirth;
	private String maritalStatus;
	private String cellNo;
	private String telephoneNo;
	private String emailID;
	private String salaryOffered;
	private String jobTitle;
	private String department;
	private String primaryLocation;
    private String fileList;
	private FormFile documentFile;
	
	private String[] documentCheck;
	private String[] select;
	private String otherLocation;
	private String empStatus;
	private String totalEmp;
	private String shiftType;
	private String jobDescription;
	
	private String qualifications;
	private String experience;
	private String industryType;
	private String saleryOffered;

	
	
	
	
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
	public String getRecruitmentId() {
		return recruitmentId;
	}
	public void setRecruitmentId(String recruitmentId) {
		this.recruitmentId = recruitmentId;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public int getEmpno() {
		return empno;
	}
	public void setEmpno(int empno) {
		this.empno = empno;
	}
	public String getUserloginname() {
		return userloginname;
	}
	public void setUserloginname(String userloginname) {
		this.userloginname = userloginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String[] getSelect() {
		return select;
	}
	public void setSelect(String[] select) {
		this.select = select;
	}
	public String[] getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}
	public ArrayList<String> getGroupIds() {
		return groupIds;
	}
	public void setGroupIds(ArrayList<String> groupIds) {
		this.groupIds = groupIds;
	}
	public String getRecuritmentID() {
		return recuritmentID;
	}
	public void setRecuritmentID(String recuritmentID) {
		this.recuritmentID = recuritmentID;
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
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getCellNo() {
		return cellNo;
	}
	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}
	public String getTelephoneNo() {
		return telephoneNo;
	}
	public void setTelephoneNo(String telephoneNo) {
		this.telephoneNo = telephoneNo;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getSalaryOffered() {
		return salaryOffered;
	}
	public void setSalaryOffered(String salaryOffered) {
		this.salaryOffered = salaryOffered;
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
	public FormFile getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}
	public String getFileList() {
		return fileList;
	}
	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	
	

	

	
	
	
	
}
