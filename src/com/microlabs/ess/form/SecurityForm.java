package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;

public class SecurityForm extends ActionForm{

	private String requestNo;
	private String userId;
	private String requsterID;
	private String reqType;
	private String ondutyType;
	private String userName;
	private String fromDate;
	private String toDate;
	private String fromTime;
	private String toTime;
	private String inTime;
	private String outTime;
	private String message;
	private String message1;
	private String approverId;
	private String approverName;
	private String approveStatus;
	private String reason;
	private String location;
	private String department;
	private String designation;
	private String empPhoto;
	private String securityDate;
	
	private String selectedFilter; 
	
	private String requestNo1;
	private String userId1;
	private String requsterID1;
	private String reqType1;
	private String ondutyType1;
	private String userName1;
	private String fromDate1;
	private String toDate1;
	private String fromTime1;
	private String toTime1;
	private String inTime1;
	private String outTime1;
	private String message12;
	private String message11;
	private String approverId1;
	private String approverName1;
	private String approveStatus1;
	private String reason1;
	private String location1;
	private String department1;
	private String designation1;
	private String empPhoto1;
	private String securityDate1;
	
	
	private int totalRecords1;
	private int totalRecords;
	private int next;
	private int total;
	private int prev;
	private int startRecord;
	private int endRecord;
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public int getTotalRecords1() {
		return totalRecords1;
	}
	public void setTotalRecords1(int totalRecords1) {
		this.totalRecords1 = totalRecords1;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getRequsterID() {
		return requsterID;
	}
	public void setRequsterID(String requsterID) {
		this.requsterID = requsterID;
	}
	public String getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getOndutyType() {
		return ondutyType;
	}
	public void setOndutyType(String ondutyType) {
		this.ondutyType = ondutyType;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReqType() {
		return reqType;
	}
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getFromTime() {
		return fromTime;
	}
	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}
	public String getToTime() {
		return toTime;
	}
	public void setToTime(String toTime) {
		this.toTime = toTime;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
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
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getEmpPhoto() {
		return empPhoto;
	}
	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getSecurityDate() {
		return securityDate;
	}
	public void setSecurityDate(String securityDate) {
		this.securityDate = securityDate;
	}
	public String getRequestNo1() {
		return requestNo1;
	}
	public void setRequestNo1(String requestNo1) {
		this.requestNo1 = requestNo1;
	}
	public String getUserId1() {
		return userId1;
	}
	public void setUserId1(String userId1) {
		this.userId1 = userId1;
	}
	public String getRequsterID1() {
		return requsterID1;
	}
	public void setRequsterID1(String requsterID1) {
		this.requsterID1 = requsterID1;
	}
	public String getReqType1() {
		return reqType1;
	}
	public void setReqType1(String reqType1) {
		this.reqType1 = reqType1;
	}
	public String getOndutyType1() {
		return ondutyType1;
	}
	public void setOndutyType1(String ondutyType1) {
		this.ondutyType1 = ondutyType1;
	}
	public String getUserName1() {
		return userName1;
	}
	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}
	public String getFromDate1() {
		return fromDate1;
	}
	public void setFromDate1(String fromDate1) {
		this.fromDate1 = fromDate1;
	}
	public String getToDate1() {
		return toDate1;
	}
	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}
	public String getFromTime1() {
		return fromTime1;
	}
	public void setFromTime1(String fromTime1) {
		this.fromTime1 = fromTime1;
	}
	public String getToTime1() {
		return toTime1;
	}
	public void setToTime1(String toTime1) {
		this.toTime1 = toTime1;
	}
	public String getInTime1() {
		return inTime1;
	}
	public void setInTime1(String inTime1) {
		this.inTime1 = inTime1;
	}
	public String getOutTime1() {
		return outTime1;
	}
	public void setOutTime1(String outTime1) {
		this.outTime1 = outTime1;
	}
	public String getMessage12() {
		return message12;
	}
	public void setMessage12(String message12) {
		this.message12 = message12;
	}
	public String getMessage11() {
		return message11;
	}
	public void setMessage11(String message11) {
		this.message11 = message11;
	}
	public String getApproverId1() {
		return approverId1;
	}
	public void setApproverId1(String approverId1) {
		this.approverId1 = approverId1;
	}
	public String getApproverName1() {
		return approverName1;
	}
	public void setApproverName1(String approverName1) {
		this.approverName1 = approverName1;
	}
	public String getApproveStatus1() {
		return approveStatus1;
	}
	public void setApproveStatus1(String approveStatus1) {
		this.approveStatus1 = approveStatus1;
	}
	public String getReason1() {
		return reason1;
	}
	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}
	public String getLocation1() {
		return location1;
	}
	public void setLocation1(String location1) {
		this.location1 = location1;
	}
	public String getDepartment1() {
		return department1;
	}
	public void setDepartment1(String department1) {
		this.department1 = department1;
	}
	public String getDesignation1() {
		return designation1;
	}
	public void setDesignation1(String designation1) {
		this.designation1 = designation1;
	}
	public String getEmpPhoto1() {
		return empPhoto1;
	}
	public void setEmpPhoto1(String empPhoto1) {
		this.empPhoto1 = empPhoto1;
	}
	public String getSecurityDate1() {
		return securityDate1;
	}
	public void setSecurityDate1(String securityDate1) {
		this.securityDate1 = securityDate1;
	}
	public String getSelectedFilter() {
		return selectedFilter;
	}
	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	
	
	
}
