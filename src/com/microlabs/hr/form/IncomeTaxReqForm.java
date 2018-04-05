package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class IncomeTaxReqForm extends ActionForm{
	
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
	private String chooseKeyword;
	
	private ArrayList yearList=new ArrayList();
	
	private String fiscalYear;
	
	
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private String[] locArray;
	
	
	private String[] repgrpArray;
	private ArrayList repgrpList=new ArrayList();
	private ArrayList repgrpLabelList=new ArrayList();
	
	
	
	
	public ArrayList getLocationIdList() {
		return locationIdList;
	}
	public void setLocationIdList(ArrayList locationIdList) {
		this.locationIdList = locationIdList;
	}
	public ArrayList getLocationLabelList() {
		return locationLabelList;
	}
	public void setLocationLabelList(ArrayList locationLabelList) {
		this.locationLabelList = locationLabelList;
	}
	public String[] getLocArray() {
		return locArray;
	}
	public void setLocArray(String[] locArray) {
		this.locArray = locArray;
	}
	public String[] getRepgrpArray() {
		return repgrpArray;
	}
	public void setRepgrpArray(String[] repgrpArray) {
		this.repgrpArray = repgrpArray;
	}
	public ArrayList getRepgrpList() {
		return repgrpList;
	}
	public void setRepgrpList(ArrayList repgrpList) {
		this.repgrpList = repgrpList;
	}
	public ArrayList getRepgrpLabelList() {
		return repgrpLabelList;
	}
	public void setRepgrpLabelList(ArrayList repgrpLabelList) {
		this.repgrpLabelList = repgrpLabelList;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public ArrayList getYearList() {
		return yearList;
	}
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}
	public String getChooseKeyword() {
		return chooseKeyword;
	}
	public void setChooseKeyword(String chooseKeyword) {
		this.chooseKeyword = chooseKeyword;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public int[] getLtaID() {
		return ltaID;
	}
	public void setLtaID(int[] ltaID) {
		this.ltaID = ltaID;
	}
	public String[] getLtaAmtApproved() {
		return ltaAmtApproved;
	}
	public void setLtaAmtApproved(String[] ltaAmtApproved) {
		this.ltaAmtApproved = ltaAmtApproved;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
	

}
