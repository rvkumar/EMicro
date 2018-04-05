package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ExternalIncomeForm extends ActionForm{
	private int id;
	private int requestNo;
	private String employeeNo;
	private String fiscalYear;
	private String fiscalYearDesc;
	private String employeeName;
	private String message;
	
	private String employer;
	private String saleryID;
	private int salID;
	private String salSTXT;
	private String salLTXT;
	private String saleryDescription;
	private String saleryType;
	private String startDate;
	private String endDate;
	private String totalAmount;
	private String remarks;
	public String[] documentCheck;
	public double totalIncome;
	public double totalDeduction;
	private String department;
	private String designation;
	private String submitDate;
	private String approvalStatus;  
	private String lastApprover;
	private String pendingApprover;
	
	
	
	 private ArrayList yearList=new ArrayList();
	 

		private String staffCategory;
		private String doj;
		private String location;

		
		private FormFile documentFile;
		
		
		
		
		
		
	 
	public FormFile getDocumentFile() {
			return documentFile;
		}
		public void setDocumentFile(FormFile documentFile) {
			this.documentFile = documentFile;
		}
	public String getStaffCategory() {
			return staffCategory;
		}
		public void setStaffCategory(String staffCategory) {
			this.staffCategory = staffCategory;
		}
		public String getDoj() {
			return doj;
		}
		public void setDoj(String doj) {
			this.doj = doj;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
	public ArrayList getYearList() {
		return yearList;
	}
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}
	public String getLastApprover() {
		return lastApprover;
	}
	public void setLastApprover(String lastApprover) {
		this.lastApprover = lastApprover;
	}
	public String getPendingApprover() {
		return pendingApprover;
	}
	public void setPendingApprover(String pendingApprover) {
		this.pendingApprover = pendingApprover;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public int getSalID() {
		return salID;
	}
	public void setSalID(int salID) {
		this.salID = salID;
	}
	public String getSalSTXT() {
		return salSTXT;
	}
	public void setSalSTXT(String salSTXT) {
		this.salSTXT = salSTXT;
	}
	public String getSalLTXT() {
		return salLTXT;
	}
	public void setSalLTXT(String salLTXT) {
		this.salLTXT = salLTXT;
	}
	public double getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}
	public double getTotalDeduction() {
		return totalDeduction;
	}
	public void setTotalDeduction(double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}
	public String getFiscalYearDesc() {
		return fiscalYearDesc;
	}
	public void setFiscalYearDesc(String fiscalYearDesc) {
		this.fiscalYearDesc = fiscalYearDesc;
	}
	public String[] getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}
	public String getEmployer() {
		return employer;
	}
	public void setEmployer(String employer) {
		this.employer = employer;
	}
	public String getSaleryID() {
		return saleryID;
	}
	public void setSaleryID(String saleryID) {
		this.saleryID = saleryID;
	}
	public String getSaleryDescription() {
		return saleryDescription;
	}
	public void setSaleryDescription(String saleryDescription) {
		this.saleryDescription = saleryDescription;
	}
	public String getSaleryType() {
		return saleryType;
	}
	public void setSaleryType(String saleryType) {
		this.saleryType = saleryType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	
	
}
