package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;

public class DelegationForm extends ActionForm{
	

	private String searchText;
	private String message;
	
	private String employeeNumber;
	private boolean delegate;
	private String fromDate;
	private String toDate;

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

	public boolean getDelegate() {
		return delegate;
	}

	public void setDelegate(boolean delegate) {
		this.delegate = delegate;
	}
	
	public String getSearchText(){
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getFromDate(){
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate(){
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}