package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class HRReportForm extends ActionForm{
	
	
	private String requestType;
	private LinkedList filterType;
	private String selectedFilter;
	private String reqRequstType;
	private String hrFromDate;
	private String hrToDate;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String message;
	private String message3;
	private String appMessage;
	private String date;
	private String department;
	private String employeeNo;
	
	
	
	private String requiredType;
	private ArrayList departmentList= new ArrayList();
private ArrayList departmentLabelList= new ArrayList();
	
	
	
	
	public ArrayList getDepartmentLabelList() {
		return departmentLabelList;
	}
	public void setDepartmentLabelList(ArrayList departmentLabelList) {
		this.departmentLabelList = departmentLabelList;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public ArrayList getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
	}
	public String getAppMessage() {
		return appMessage;
	}
	public void setAppMessage(String appMessage) {
		this.appMessage = appMessage;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage3() {
		return message3;
	}
	public void setMessage3(String message3) {
		this.message3 = message3;
	}
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
	public String getHrFromDate() {
		return hrFromDate;
	}
	public void setHrFromDate(String hrFromDate) {
		this.hrFromDate = hrFromDate;
	}
	public String getHrToDate() {
		return hrToDate;
	}
	public void setHrToDate(String hrToDate) {
		this.hrToDate = hrToDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getReqRequstType() {
		return reqRequstType;
	}
	public void setReqRequstType(String reqRequstType) {
		this.reqRequstType = reqRequstType;
	}
	public LinkedList getFilterType() {
		return filterType;
	}
	public void setFilterType(LinkedList filterType) {
		this.filterType = filterType;
	}
	public String getSelectedFilter() {
		return selectedFilter;
	}
	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	public String getRequiredType() {
		return requiredType;
	}
	public void setRequiredType(String requiredType) {
		this.requiredType = requiredType;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

}
