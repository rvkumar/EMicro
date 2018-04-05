package com.microlabs.ess.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class MyIncomeTaxForm extends ActionForm{
	
	private String message;
	private String message2;
	private String message3;
	private String comments;
	private String requestNo;
	private String requestDate;
	private String requestType;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String[] selectedRequestNo;
	private LinkedList filterType;
	private String selectedFilter;
	private String fileNameList;
	private String reqRequstType;
	
	private int totalAppCount;
	private int startAppCount;
	private int endAppCount;
	private String[] ltaAmtApproved;
	private int[] ltaID;
	private String approverName;
	private String apprDept;
	private String apprDesg;
	private String apprStatus;
	private String apprDate;
	private int priority;
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
	public String getMessage3() {
		return message3;
	}
	public void setMessage3(String message3) {
		this.message3 = message3;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	public String[] getSelectedRequestNo() {
		return selectedRequestNo;
	}
	public void setSelectedRequestNo(String[] selectedRequestNo) {
		this.selectedRequestNo = selectedRequestNo;
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
	public String getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(String fileNameList) {
		this.fileNameList = fileNameList;
	}
	public String getReqRequstType() {
		return reqRequstType;
	}
	public void setReqRequstType(String reqRequstType) {
		this.reqRequstType = reqRequstType;
	}
	public int getTotalAppCount() {
		return totalAppCount;
	}
	public void setTotalAppCount(int totalAppCount) {
		this.totalAppCount = totalAppCount;
	}
	public int getStartAppCount() {
		return startAppCount;
	}
	public void setStartAppCount(int startAppCount) {
		this.startAppCount = startAppCount;
	}
	public int getEndAppCount() {
		return endAppCount;
	}
	public void setEndAppCount(int endAppCount) {
		this.endAppCount = endAppCount;
	}
	public String[] getLtaAmtApproved() {
		return ltaAmtApproved;
	}
	public void setLtaAmtApproved(String[] ltaAmtApproved) {
		this.ltaAmtApproved = ltaAmtApproved;
	}
	public int[] getLtaID() {
		return ltaID;
	}
	public void setLtaID(int[] ltaID) {
		this.ltaID = ltaID;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApprDept() {
		return apprDept;
	}
	public void setApprDept(String apprDept) {
		this.apprDept = apprDept;
	}
	public String getApprDesg() {
		return apprDesg;
	}
	public void setApprDesg(String apprDesg) {
		this.apprDesg = apprDesg;
	}
	public String getApprStatus() {
		return apprStatus;
	}
	public void setApprStatus(String apprStatus) {
		this.apprStatus = apprStatus;
	}
	public String getApprDate() {
		return apprDate;
	}
	public void setApprDate(String apprDate) {
		this.apprDate = apprDate;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	

}
