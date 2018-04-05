package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;

public class SAPOndutyForm  extends ActionForm{
	
	private String employeeNo;
	private String payGroup;
	private String year;		
	private String ondutytype;	
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String submitDate;
	private String reason;	

	private String appstatus;
	private int reqno;
	
	
	
		
	public int getReqno() {
		return reqno;
	}
	public void setReqno(int reqno) {
		this.reqno = reqno;
	}
	public String getAppstatus() {
		return appstatus;
	}
	public void setAppstatus(String appstatus) {
		this.appstatus = appstatus;
	}
	private String sapstatus;
	private String sapApprovedDate;
	private String message;
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getPayGroup() {
		return payGroup;
	}
	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getOndutytype() {
		return ondutytype;
	}
	public void setOndutytype(String ondutytype) {
		this.ondutytype = ondutytype;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSapstatus() {
		return sapstatus;
	}
	public void setSapstatus(String sapstatus) {
		this.sapstatus = sapstatus;
	}
	public String getSapApprovedDate() {
		return sapApprovedDate;
	}
	public void setSapApprovedDate(String sapApprovedDate) {
		this.sapApprovedDate = sapApprovedDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	

}
