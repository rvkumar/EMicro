package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class SAPReportForm extends ActionForm{

	private String message;
	private String message1;
	private String requestNumber;
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private String fromDate;
	private String toDate;
	private String locationId;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private String materialCodeLists;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private int midRecord;
	private int rejRecord;
	private String searchType;
	
	private ArrayList accountGroupList=new ArrayList();
	private ArrayList accountGroupLabelList=new ArrayList();
	
	private String fromDate1;
	private String toDate1;
	
	private String req_Status;
	
	private ArrayList techidList=new ArrayList();
	private ArrayList technameList=new ArrayList();
	
	private String approver;
	
	
	
	public int getMidRecord() {
		return midRecord;
	}
	public int getRejRecord() {
		return rejRecord;
	}
	public void setMidRecord(int midRecord) {
		this.midRecord = midRecord;
	}
	public void setrejRecord(int rejRecord) {
		this.rejRecord = rejRecord;
	}
	public ArrayList getAccountGroupList() {
		return accountGroupList;
	}
	public void setAccountGroupList(ArrayList accountGroupList) {
		this.accountGroupList = accountGroupList;
	}
	public ArrayList getAccountGroupLabelList() {
		return accountGroupLabelList;
	}
	public void setAccountGroupLabelList(ArrayList accountGroupLabelList) {
		this.accountGroupLabelList = accountGroupLabelList;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
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
	public String getMaterialCodeLists() {
		return materialCodeLists;
	}
	public void setMaterialCodeLists(String materialCodeLists) {
		this.materialCodeLists = materialCodeLists;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage1() {
		return message1;
	}
	public void setMessage1(String message1) {
		this.message1 = message1;
	}
	
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public LinkedList getMaterTypeIDList() {
		return materTypeIDList;
	}
	public void setMaterTypeIDList(LinkedList materTypeIDList) {
		this.materTypeIDList = materTypeIDList;
	}
	public LinkedList getMaterialTypeIdValueList() {
		return materialTypeIdValueList;
	}
	public void setMaterialTypeIdValueList(LinkedList materialTypeIdValueList) {
		this.materialTypeIdValueList = materialTypeIdValueList;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
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
	public String getToDate1() {
		return toDate1;
	}
	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}
	public String getFromDate1() {
		return fromDate1;
	}
	public void setFromDate1(String fromDate1) {
		this.fromDate1 = fromDate1;
	}
	public String getReq_Status() {
		return req_Status;
	}
	public void setReq_Status(String req_Status) {
		this.req_Status = req_Status;
	}
	public ArrayList getTechidList() {
		return techidList;
	}
	public void setTechidList(ArrayList techidList) {
		this.techidList = techidList;
	}
	public ArrayList getTechnameList() {
		return technameList;
	}
	public void setTechnameList(ArrayList technameList) {
		this.technameList = technameList;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	
	
}
