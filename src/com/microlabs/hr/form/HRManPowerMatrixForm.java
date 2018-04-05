package com.microlabs.hr.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class HRManPowerMatrixForm extends ActionForm{

	private String recuritmentID;
	private String jobTitle;
	private String department;
	private String totalEmp;
	
	private String country;
	private String state;
	private String city;
	private String plant;

	private String employeeNumber;
	private String designation;
	
	private String message;
	
	private String monthlySalary;
	
	private String departmentInvestment;
	
	private String startDate;
	private String endDate;
	
	private String location;
	private LinkedList locationId;
	private LinkedList locationName;
	
	private String header;
	private String reqEmp;
	private String userDepart;
	private LinkedList departmentName;
	private String recLink;
	
	
	public String getRecuritmentID() {
		return recuritmentID;
	}
	public void setRecuritmentID(String recuritmentID) {
		this.recuritmentID = recuritmentID;
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
	
	public String getTotalEmp() {
		return totalEmp;
	}
	public void setTotalEmp(String totalEmp) {
		this.totalEmp = totalEmp;
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
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getDepartmentInvestment()
	{
		return departmentInvestment;
	}
	public void setDepartmentInvestment(String dInvest){
		this.departmentInvestment = dInvest;
	}
	
	public String getMonthlySalary() {
		return monthlySalary;
	}
	public void setMonthlySalary(String mSalary) {
		this.monthlySalary = mSalary;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String sDate) {
		this.startDate = sDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String eDate) {
		this.endDate = eDate;
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
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	public String getReqEmp() {
		return reqEmp;
	}
	public void setReqEmp(String reqEmp) {
		this.reqEmp = reqEmp;
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
	
	public String getRecLink() {
		return recLink;
	}
	public void setRecLink(String link) {
		this.recLink = link;
	}
	
}
