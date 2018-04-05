package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class AlertForm extends ActionForm{



	private ArrayList locationIdList = new ArrayList();
	private ArrayList locationLabelList = new ArrayList();



	private ArrayList deptIdList = new ArrayList();
	private ArrayList deptLabelList = new ArrayList();
	
	
	private ArrayList Empnolist  = new ArrayList();
	
	
	private String cat[];
	
	

	private String department[];
	private String locationId[];
	
	private String fromDate;
	private String fromTime;
	private String toDate;
	private String toTime;
	
	private String fromDate1;
	private String fromTime1;
	private String toDate1;
	private String toTime1;
	
	private String saveType;
	
	private String subject;
	
	
	
	private int id;
	
	private String content;
	
	private String reqNo;

	private String headLines;
	
	private String headLines1;
	
	private String location;
	
	private String category;
	
	private String dep;
	
	private String subject1;
	
	private int totalRecords;
	
	private int startRecord;
	
	private int endRecord;
	
	
	
	

	
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
	public ArrayList getDeptIdList() {
		return deptIdList;
	}
	public void setDeptIdList(ArrayList deptIdList) {
		this.deptIdList = deptIdList;
	}
	public ArrayList getDeptLabelList() {
		return deptLabelList;
	}
	public void setDeptLabelList(ArrayList deptLabelList) {
		this.deptLabelList = deptLabelList;
	}
	public String[] getDepartment() {
		return department;
	}
	public void setDepartment(String department[]) {
		this.department = department;
	}
	public String[] getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId[]) {
		this.locationId = locationId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String[] getCat() {
		return cat;
	}
	public void setCat(String cat[]) {
		this.cat = cat;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHeadLines() {
		return headLines;
	}
	public void setHeadLines(String headLines) {
		this.headLines = headLines;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDep() {
		return dep;
	}
	public void setDep(String dep) {
		this.dep = dep;
	}
	public String getFromDate1() {
		return fromDate1;
	}
	public void setFromDate1(String fromDate1) {
		this.fromDate1 = fromDate1;
	}
	public String getFromTime1() {
		return fromTime1;
	}
	public void setFromTime1(String fromTime1) {
		this.fromTime1 = fromTime1;
	}
	public String getToDate1() {
		return toDate1;
	}
	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}
	public String getToTime1() {
		return toTime1;
	}
	public void setToTime1(String toTime1) {
		this.toTime1 = toTime1;
	}
	public String getHeadLines1() {
		return headLines1;
	}
	public void setHeadLines1(String headLines1) {
		this.headLines1 = headLines1;
	}
	public String getSubject1() {
		return subject1;
	}
	public void setSubject1(String subject1) {
		this.subject1 = subject1;
	}
	public ArrayList getEmpnolist() {
		return Empnolist;
	}
	public void setEmpnolist(ArrayList empnolist) {
		Empnolist = empnolist;
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

	

}
