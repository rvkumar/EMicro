package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class IncomeTaxForm extends ActionForm{

	private String totalAmount;
	private String employeeNo;
	
	private String fiscalYear;


	private String employeeName;
	private FormFile documentFile;
	private int requestNumber;
	private String message;
	private String message2;
	
	private String fileName;
	private String id;
	private String fileFullPath;
	public String[] documentCheck;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String submitDate;
	private String approvalStatus;
	
	private String[] investmetnCode;
	private String[] investmentDesc;
	private String[] receiptNo;
	private String[] amount;
	private String[] date;
	private String[] status;
	private String[] remarks;
	
	private String invCode;
	private String invDes;
	private String reciptNum;
	private String amt;
	private String invDate;
	private String invStatus;
	private String invRemarks;
    private LinkedList invCodeList;
	
    private String department;
	private String designation;
	private String fiscalYearDesc;
	private String lastApprover;
	private String pendingApprover;
	
	private String staffCategory;
	
	
	private String invSection;
	private String invLimit;
	
	
	private String totaldeduction;
	
	private String amtApproved;
	
	
	
	
	
	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getAmtApproved() {
		return amtApproved;
	}

	public void setAmtApproved(String amtApproved) {
		this.amtApproved = amtApproved;
	}

	public String getInvSection() {
		return invSection;
	}

	public void setInvSection(String invSection) {
		this.invSection = invSection;
	}

	public String getTotaldeduction() {
		return totaldeduction;
	}

	public void setTotaldeduction(String totaldeduction) {
		this.totaldeduction = totaldeduction;
	}

	public String getInvLimit() {
		return invLimit;
	}

	public void setInvLimit(String invLimit) {
		this.invLimit = invLimit;
	}



	public String getStaffCategory() {
		return staffCategory;
	}

	public void setStaffCategory(String staffCategory) {
		this.staffCategory = staffCategory;
	}

	private ArrayList yearList=new ArrayList();
	

	private String doj;
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

	private String location;
	
	

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

	public String getFiscalYearDesc() {
		return fiscalYearDesc;
	}

	public void setFiscalYearDesc(String fiscalYearDesc) {
		this.fiscalYearDesc = fiscalYearDesc;
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

	public LinkedList getInvCodeList() {
		return invCodeList;
	}

	public void setInvCodeList(LinkedList invCodeList) {
		this.invCodeList = invCodeList;
	}

	public String[] getInvestmetnCode() {
		return investmetnCode;
	}

	public void setInvestmetnCode(String[] investmetnCode) {
		this.investmetnCode = investmetnCode;
	}

	public String[] getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String[] receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String[] getAmount() {
		return amount;
	}

	public void setAmount(String[] amount) {
		this.amount = amount;
	}

	public String[] getDate() {
		return date;
	}

	public void setDate(String[] date) {
		this.date = date;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public String[] getRemarks() {
		return remarks;
	}

	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}

	public String getInvCode() {
		return invCode;
	}

	public void setInvCode(String invCode) {
		this.invCode = invCode;
	}

	public String getReciptNum() {
		return reciptNum;
	}

	public void setReciptNum(String reciptNum) {
		this.reciptNum = reciptNum;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getInvDate() {
		return invDate;
	}

	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}

	public String getInvStatus() {
		return invStatus;
	}

	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	public String getInvRemarks() {
		return invRemarks;
	}

	public void setInvRemarks(String invRemarks) {
		this.invRemarks = invRemarks;
	}

	public String getInvDes() {
		return invDes;
	}

	public void setInvDes(String invDes) {
		this.invDes = invDes;
	}

	public String[] getInvestmentDesc() {
		return investmentDesc;
	}

	public void setInvestmentDesc(String[] investmentDesc) {
		this.investmentDesc = investmentDesc;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
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

	public FormFile getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String[] getDocumentCheck() {
		return documentCheck;
	}

	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
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

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}


	
}
