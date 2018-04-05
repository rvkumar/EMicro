package com.microlabs.ess.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class SAPAttendenceForm extends ActionForm{

	private String employeeNo;
	private String payGroup;
	private String month;
	private LinkedList payGroupID;
	private LinkedList payGroupShort_Name;
	
	
	public LinkedList getPayGroupID() {
		return payGroupID;
	}
	public void setPayGroupID(LinkedList payGroupID) {
		this.payGroupID = payGroupID;
	}
	public LinkedList getPayGroupShort_Name() {
		return payGroupShort_Name;
	}
	public void setPayGroupShort_Name(LinkedList payGroupShort_Name) {
		this.payGroupShort_Name = payGroupShort_Name;
	}
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
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	
}
