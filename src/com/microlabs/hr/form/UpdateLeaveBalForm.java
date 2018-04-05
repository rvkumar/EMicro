package com.microlabs.hr.form;

import org.apache.struts.action.ActionForm;

public class UpdateLeaveBalForm extends ActionForm {

	
	private String empNumber;
	private String empName;
	private String empdep;
	private String empdesg;
	
	private float openingBalence;
	private float avalableBalence;
	private float closingBalence;
	private float awaitingBalence;
	private int year;
	
	private String message;
	private String message2;
	
	
	private String startDate;
	private String startDurationType;
	private String endDate;
	private String submitDate;
	private String endDurationType;
	private String noOfDays;
	
	private double totalLeaveDays;
	
	private String reason;
	
	private String doj;
	private String dol;
	
	
	
	
	public String getDol() {
		return dol;
	}

	public void setDol(String dol) {
		this.dol = dol;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getEmpdep() {
		return empdep;
	}

	public void setEmpdep(String empdep) {
		this.empdep = empdep;
	}

	public String getEmpdesg() {
		return empdesg;
	}

	public void setEmpdesg(String empdesg) {
		this.empdesg = empdesg;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public double getTotalLeaveDays() {
		return totalLeaveDays;
	}

	public void setTotalLeaveDays(double totalLeaveDays) {
		this.totalLeaveDays = totalLeaveDays;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartDurationType() {
		return startDurationType;
	}

	public void setStartDurationType(String startDurationType) {
		this.startDurationType = startDurationType;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String getEndDurationType() {
		return endDurationType;
	}

	public void setEndDurationType(String endDurationType) {
		this.endDurationType = endDurationType;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public float getOpeningBalence() {
		return openingBalence;
	}

	public void setOpeningBalence(float openingBalence) {
		this.openingBalence = openingBalence;
	}

	public float getAvalableBalence() {
		return avalableBalence;
	}

	public void setAvalableBalence(float avalableBalence) {
		this.avalableBalence = avalableBalence;
	}

	public float getClosingBalence() {
		return closingBalence;
	}

	public void setClosingBalence(float closingBalence) {
		this.closingBalence = closingBalence;
	}

	public float getAwaitingBalence() {
		return awaitingBalence;
	}

	public void setAwaitingBalence(float awaitingBalence) {
		this.awaitingBalence = awaitingBalence;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getEmpNumber() {
		return empNumber;
	}

	public void setEmpNumber(String empNumber) {
		this.empNumber = empNumber;
	}
	
	
}
