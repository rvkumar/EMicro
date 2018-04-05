package com.microlabs.myrequest.form;

import java.util.ArrayList;
import java.util.LinkedList;
import org.apache.struts.action.ActionForm;

public class MyRequestForm extends ActionForm {
	
	private String requestName;
	private String roleId;
	private String oldId;
	private String newId;
	private int itReqNo;
	private String reqPriority;
	private String itEngStatus;
	private String itReqDate;
	
	private String onDutyType;
	private String duration;
	private String endDurationType;
	private String noOfDays;
	private String status;
	private String permissiondate;
	
	private String department;
	private String designation;
	private String approverDesg;
	
	private String requestNumber;
	private String locationName;
	private String requestType;
	private String reqMaterialType;
	private String createDate;
	private String approvalStatus;
	private String url;
	private String lastApprover;
	private String pendingApprover;
	private String approver;
	private String reqRequstType;
	private String reqStatus;
	private ArrayList <MyRequestForm>listOfrecords;
	private String matshortname;
	private int total;
	private int next;
	private int prev;
	

	private int startRecord;
	private int endRecord;
	private String message;
	private LinkedList filterType;
	private String selectedFilter;
	private String heading;
	private String searchText;
	private String requestNo;
     
	//leave properties
	private String startDate;
	private String startDateDuration;
	private String endDate;
	private String endDateDuration;
	
	private String fromStorage;
	private String toStorage;
	
private String plantType;
private String vendorType;
private String description;
private String sapNo;
private String startTime;
private String endTime;
private String employeeName;
private String employeeNumber;
private String requestername;
private String requesterdepartment;
private String requesterdesignation;
private String extno;
private String ipPhoneno;
private String serviceDescription;
private String location;
private String IPNumber;
private String requestStatus;
private String priority;
private String mode;
private String category;
private String category1;
private String subcategory;
private String subcategory1;

private String techniciansID;
private String reason;
private String subject;
private String employeeCode;
private String comments;
private String date;
private String locNo; 
private String empEmailID;

private String reqdate;
private String type;


private ArrayList deptList=new ArrayList();
private ArrayList deptLabelList=new ArrayList();



private ArrayList desgList=new ArrayList();
private ArrayList desgLabelList=new ArrayList();






public String getRoleId() {
	return roleId;
}
public void setRoleId(String roleId) {
	this.roleId = roleId;
}
public String getOldId() {
	return oldId;
}
public void setOldId(String oldId) {
	this.oldId = oldId;
}
public String getNewId() {
	return newId;
}
public void setNewId(String newId) {
	this.newId = newId;
}
public String getRequestName() {
	return requestName;
}
public void setRequestName(String requestName) {
	this.requestName = requestName;
}
public ArrayList getDeptList() {
	return deptList;
}
public void setDeptList(ArrayList deptList) {
	this.deptList = deptList;
}
public ArrayList getDeptLabelList() {
	return deptLabelList;
}
public void setDeptLabelList(ArrayList deptLabelList) {
	this.deptLabelList = deptLabelList;
}
public ArrayList getDesgList() {
	return desgList;
}
public void setDesgList(ArrayList desgList) {
	this.desgList = desgList;
}
public ArrayList getDesgLabelList() {
	return desgLabelList;
}
public void setDesgLabelList(ArrayList desgLabelList) {
	this.desgLabelList = desgLabelList;
}
public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getReqdate() {
	return reqdate;
}
public void setReqdate(String reqdate) {
	this.reqdate = reqdate;
}
public String getRequesterdepartment() {
	return requesterdepartment;
}
public void setRequesterdepartment(String requesterdepartment) {
	this.requesterdepartment = requesterdepartment;
}
public String getRequesterdesignation() {
	return requesterdesignation;
}
public void setRequesterdesignation(String requesterdesignation) {
	this.requesterdesignation = requesterdesignation;
}

public String getIpPhoneno() {
	return ipPhoneno;
}
public void setIpPhoneno(String ipPhoneno) {
	this.ipPhoneno = ipPhoneno;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getIPNumber() {
	return IPNumber;
}
public void setIPNumber(String iPNumber) {
	IPNumber = iPNumber;
}
public String getRequestStatus() {
	return requestStatus;
}
public void setRequestStatus(String requestStatus) {
	this.requestStatus = requestStatus;
}
public String getPriority() {
	return priority;
}
public void setPriority(String priority) {
	this.priority = priority;
}
public String getMode() {
	return mode;
}
public void setMode(String mode) {
	this.mode = mode;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getCategory1() {
	return category1;
}
public void setCategory1(String category1) {
	this.category1 = category1;
}
public String getSubcategory() {
	return subcategory;
}
public void setSubcategory(String subcategory) {
	this.subcategory = subcategory;
}

public String getTechniciansID() {
	return techniciansID;
}
public void setTechniciansID(String techniciansID) {
	this.techniciansID = techniciansID;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getEmployeeCode() {
	return employeeCode;
}
public void setEmployeeCode(String employeeCode) {
	this.employeeCode = employeeCode;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
	private String name;
	private String vendorName;
	private String empno;
	
	public String getReqPriority() {
		return reqPriority;
	}
	public void setReqPriority(String reqPriority) {
		this.reqPriority = reqPriority;
	}
	public String getItEngStatus() {
		return itEngStatus;
	}
	public void setItEngStatus(String itEngStatus) {
		this.itEngStatus = itEngStatus;
	}
	public String getMatshortname() {
		return matshortname;
	}
	public void setMatshortname(String matshortname) {
		this.matshortname = matshortname;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSapNo() {
		return sapNo;
	}
	public void setSapNo(String sapNo) {
		this.sapNo = sapNo;
	}
	public String getReqMaterialType() {
		return reqMaterialType;
	}
	public void setReqMaterialType(String reqMaterialType) {
		this.reqMaterialType = reqMaterialType;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getVendorType() {
		return vendorType;
	}
	public void setVendorType(String vendorType) {
		this.vendorType = vendorType;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getServiceDescription() {
	return serviceDescription;
}
public void setServiceDescription(String serviceDescription) {
	this.serviceDescription = serviceDescription;
}
	public String getFromStorage() {
	return fromStorage;
}
public void setFromStorage(String fromStorage) {
	this.fromStorage = fromStorage;
}
public String getToStorage() {
	return toStorage;
}
public void setToStorage(String toStorage) {
	this.toStorage = toStorage;
}
public String getPlantType() {
	return plantType;
}
public void setPlantType(String plantType) {
	this.plantType = plantType;
}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartDateDuration() {
		return startDateDuration;
	}
	public void setStartDateDuration(String startDateDuration) {
		this.startDateDuration = startDateDuration;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEndDateDuration() {
		return endDateDuration;
	}
	public void setEndDateDuration(String endDateDuration) {
		this.endDateDuration = endDateDuration;
	}
	public String getRequestNumber() {
	return requestNumber;
}
public void setRequestNumber(String requestNumber) {
	this.requestNumber = requestNumber;
}
	public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
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
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
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
	public String getReqRequstType() {
		return reqRequstType;
	}
	public void setReqRequstType(String reqRequstType) {
		this.reqRequstType = reqRequstType;
	}
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public ArrayList<MyRequestForm> getListOfrecords() {
		return listOfrecords;
	}
	public void setListOfrecords(ArrayList<MyRequestForm> listOfrecords) {
		this.listOfrecords = listOfrecords;
	}
	public LinkedList getFilterType() {
		return filterType;
	}
	public void setFilterType(LinkedList fType) {
		this.filterType = fType;
	}
	public String getSelectedFilter() {
		return selectedFilter;
	}
	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	
	public String getHeading(){
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	public String getSearchText(){
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String getRequestNo(){
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getOnDutyType() {
		return onDutyType;
	}
	public void setOnDutyType(String onDutyType) {
		this.onDutyType = onDutyType;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getEndDurationType() {
		return endDurationType;
	}
	public void setEndDurationType(String endDurationType) {
		this.endDurationType = endDurationType;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPermissiondate() {
		return permissiondate;
	}
	public void setPermissiondate(String permissiondate) {
		this.permissiondate = permissiondate;
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
	public String getApproverDesg() {
		return approverDesg;
	}
	public void setApproverDesg(String approverDesg) {
		this.approverDesg = approverDesg;
	}
	public int getItReqNo() {
		return itReqNo;
	}
	public void setItReqNo(int itReqNo) {
		this.itReqNo = itReqNo;
	}
	public String getItReqDate() {
		return itReqDate;
	}
	public void setItReqDate(String itReqDate) {
		this.itReqDate = itReqDate;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getRequestername() {
		return requestername;
	}
	public void setRequestername(String requestername) {
		this.requestername = requestername;
	}
	public String getSubcategory1() {
		return subcategory1;
	}
	public void setSubcategory1(String subcategory1) {
		this.subcategory1 = subcategory1;
	}
	public String getExtno() {
		return extno;
	}
	public void setExtno(String extno) {
		this.extno = extno;
	}
	public String getLocNo() {
		return locNo;
	}
	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}
	public String getEmpEmailID() {
		return empEmailID;
	}
	public void setEmpEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
	}
	
}
