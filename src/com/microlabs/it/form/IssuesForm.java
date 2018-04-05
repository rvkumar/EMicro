package com.microlabs.it.form;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class IssuesForm extends ActionForm{
	private String id;
	private FormFile documentFile;
	public String[] documentCheck;
	private String issrequestype;
	private String category;
	private String subject;
	private String fileName;
	private String fileFullPath;
	private String message2;
	private int requestNo;
	private String requestNumber;
	private String message;
	private String message1;
	private String requestStatus;
	private String requestername;
	private String requesterdepartment;
	private String requesterdesignation;
	private String location;
	private String locNo;
	private int priority;
	private String requestType;
	private String employeeno;
	private String mobno;
	private String extno;
	private String ipPhoneno;
	private String hostname;
	private String IPNumber;
	private String empno;
	private String empEmailID;
	private  ArrayList subcatList;
	private String usage;
	private String reqPriority;
	private String itEngStatus;
	private String remarks;
	private String reason;
	private String locationId;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private String issAssignedTechn;
	private String technicianID;
	private String technicianName;
	
	private String maincategory;
	private String mainrequestStatus;
	
	private String onbename;
	private String onbedepartment;
	private String onbedesignation;
	private String oncomments;
	
	
	private int requestId;
	private String requestName;
	private ArrayList requestlist=new ArrayList();
	private ArrayList requestidlist=new ArrayList();
	
	
	private String roleId;
	private ArrayList rolelist=new ArrayList();
	private ArrayList roleLabelList=new ArrayList();
	
	

	public ArrayList getRoleLabelList() {
		return roleLabelList;
	}
	public void setRoleLabelList(ArrayList roleLabelList) {
		this.roleLabelList = roleLabelList;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public ArrayList getRolelist() {
		return rolelist;
	}
	public void setRolelist(ArrayList rolelist) {
		this.rolelist = rolelist;
	}
	public String getOnbename() {
		return onbename;
	}
	public void setOnbename(String onbename) {
		this.onbename = onbename;
	}
	public String getOnbedepartment() {
		return onbedepartment;
	}
	public void setOnbedepartment(String onbedepartment) {
		this.onbedepartment = onbedepartment;
	}
	public String getOnbedesignation() {
		return onbedesignation;
	}
	public void setOnbedesignation(String onbedesignation) {
		this.onbedesignation = onbedesignation;
	}
	public String getOncomments() {
		return oncomments;
	}
	public void setOncomments(String oncomments) {
		this.oncomments = oncomments;
	}
	public String getMaincategory() {
		return maincategory;
	}
	public void setMaincategory(String maincategory) {
		this.maincategory = maincategory;
	}
	public String getMainrequestStatus() {
		return mainrequestStatus;
	}
	public void setMainrequestStatus(String mainrequestStatus) {
		this.mainrequestStatus = mainrequestStatus;
	}
	public ArrayList getDepartmentIdList() {
		return departmentIdList;
	}
	public void setDepartmentIdList(ArrayList departmentIdList) {
		this.departmentIdList = departmentIdList;
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
	public String getFromlocationId() {
		return fromlocationId;
	}
	public void setFromlocationId(String fromlocationId) {
		this.fromlocationId = fromlocationId;
	}
	public String getTolocationId() {
		return tolocationId;
	}
	public void setTolocationId(String tolocationId) {
		this.tolocationId = tolocationId;
	}
	public String getFromdepartment() {
		return fromdepartment;
	}
	public void setFromdepartment(String fromdepartment) {
		this.fromdepartment = fromdepartment;
	}
	public String getTodepartment() {
		return todepartment;
	}
	public void setTodepartment(String todepartment) {
		this.todepartment = todepartment;
	}
	private ArrayList departmentIdList=new ArrayList();
	
	private ArrayList techidList=new ArrayList();
	private ArrayList technameList= new ArrayList();
	
	private String fromlocationId;
	private String tolocationId;
	
	private String  fromdepartment;
	private String  todepartment;
	
	
	private String selectedSubCategory;
	private String selectedTechnician;
	
	private String employeename;
	
	private String[] selectedRequestNo;
	private String reqDate;
	private String subcategory;
	private String mode;
	private String comments;
	private String forwardEmpId;
	private String reqFiled;
	private ArrayList departmentList= new ArrayList();
	private String approvedDate;
	private String issuePriority;
	private String empType;
	
	private String chooseType;
	private String chooseKeyword;
	private String selectedIssueStatus;
	//navigation
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private int readStatus;
	private int transNo;
	List issueSolutDocs=new LinkedList();
	private List techIDs;
	private List techNames;
	private String assignTo;
	private boolean reqNoStatus;
	private int esclanatTo;
	private String slaStatus;
	private  ArrayList techList;
	
	private String employeeCode;
	private String department;
	private String designation;
	
	private String reqEmail;
	private String reftype;
	private String reftypes;
	
	private String day;
	private String hr;
	private String refYear;
	private String compDate;
	
	private ArrayList categorylist;
	private ArrayList categortShortlist;
	
	private int escalatedLevel;
	
	
	
	
	private String fromDate;
	private String toDate;
	
	private String oldid;
	private String newid;
	
	
	
	
	
	public String getOldid() {
		return oldid;
	}
	public void setOldid(String oldid) {
		this.oldid = oldid;
	}
	public String getNewid() {
		return newid;
	}
	public void setNewid(String newid) {
		this.newid = newid;
	}
	public ArrayList getRequestidlist() {
		return requestidlist;
	}
	public void setRequestidlist(ArrayList requestidlist) {
		this.requestidlist = requestidlist;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}
	public ArrayList getRequestlist() {
		return requestlist;
	}
	public void setRequestlist(ArrayList requestlist) {
		this.requestlist = requestlist;
	}
	public String getReftypes() {
		return reftypes;
	}
	public void setReftypes(String reftypes) {
		this.reftypes = reftypes;
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
	public int getEscalatedLevel() {
		return escalatedLevel;
	}
	public void setEscalatedLevel(int escalatedLevel) {
		this.escalatedLevel = escalatedLevel;
	}
	public String getCompDate() {
		return compDate;
	}
	public void setCompDate(String compDate) {
		this.compDate = compDate;
	}
	public ArrayList getTechList() {
		return techList;
	}
	public void setTechList(ArrayList techList) {
		this.techList = techList;
	}
	
	
	
	
	
	
	
	
	public String getSlaStatus() {
		return slaStatus;
	}
	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}
	public int getEsclanatTo() {
		return esclanatTo;
	}
	public void setEsclanatTo(int esclanatTo) {
		this.esclanatTo = esclanatTo;
	}
	public boolean isReqNoStatus() {
		return reqNoStatus;
	}
	public void setReqNoStatus(boolean reqNoStatus) {
		this.reqNoStatus = reqNoStatus;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public int getReadStatus() {
		return readStatus;
	}
	public void setReadStatus(int readStatus) {
		this.readStatus = readStatus;
	}
	
	public List getTechIDs() {
		return techIDs;
	}
	public void setTechIDs(List techIDs) {
		this.techIDs = techIDs;
	}
	public List getTechNames() {
		return techNames;
	}
	public void setTechNames(List techNames) {
		this.techNames = techNames;
	}
	public List getIssueSolutDocs() {
		return issueSolutDocs;
	}
	public void setIssueSolutDocs(List issueSolutDocs) {
		this.issueSolutDocs = issueSolutDocs;
	}
	public int getTransNo() {
		return transNo;
	}
	public void setTransNo(int transNo) {
		this.transNo = transNo;
	}
	public String getSelectedIssueStatus() {
		return selectedIssueStatus;
	}
	public void setSelectedIssueStatus(String selectedIssueStatus) {
		this.selectedIssueStatus = selectedIssueStatus;
	}
	public String getChooseType() {
		return chooseType;
	}
	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}
	public String getChooseKeyword() {
		return chooseKeyword;
	}
	public void setChooseKeyword(String chooseKeyword) {
		this.chooseKeyword = chooseKeyword;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String getIssuePriority() {
		return issuePriority;
	}
	public void setIssuePriority(String issuePriority) {
		this.issuePriority = issuePriority;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public ArrayList getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
	}
	public String getReqFiled() {
		return reqFiled;
	}
	public void setReqFiled(String reqFiled) {
		this.reqFiled = reqFiled;
	}
	public String getForwardEmpId() {
		return forwardEmpId;
	}
	public void setForwardEmpId(String forwardEmpId) {
		this.forwardEmpId = forwardEmpId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getEmployeename() {
		return employeename;
	}
	public void setEmployeename(String employeename) {
		this.employeename = employeename;
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
	public String getSelectedSubCategory() {
		return selectedSubCategory;
	}
	public void setSelectedSubCategory(String selectedSubCategory) {
		this.selectedSubCategory = selectedSubCategory;
	}
	public String getSelectedTechnician() {
		return selectedTechnician;
	}
	public void setSelectedTechnician(String selectedTechnician) {
		this.selectedTechnician = selectedTechnician;
	}
	public String getTechnicianID() {
		return technicianID;
	}
	public void setTechnicianID(String technicianID) {
		this.technicianID = technicianID;
	}
	public String getTechnicianName() {
		return technicianName;
	}
	public void setTechnicianName(String technicianName) {
		this.technicianName = technicianName;
	}
	public String getLocNo() {
		return locNo;
	}
	public void setLocNo(String locNo) {
		this.locNo = locNo;
	}
	public String getIssAssignedTechn() {
		return issAssignedTechn;
	}
	public void setIssAssignedTechn(String issAssignedTechn) {
		this.issAssignedTechn = issAssignedTechn;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public ArrayList getSubcatList() {
		return subcatList;
	}
	public void setSubcatList(ArrayList subcatList) {
		this.subcatList = subcatList;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getEmpno() {
		return empno;
	}
	public void setEmpno(String empno) {
		this.empno = empno;
	}
	public String getEmpEmailID() {
		return empEmailID;
	}
	public void setEmpEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getIPNumber() {
		return IPNumber;
	}
	public void setIPNumber(String iPNumber) {
		IPNumber = iPNumber;
	}
	public String getMobno() {
		return mobno;
	}
	public void setMobno(String mobno) {
		this.mobno = mobno;
	}
	public String getExtno() {
		return extno;
	}
	public void setExtno(String extno) {
		this.extno = extno;
	}
	public String getIpPhoneno() {
		return ipPhoneno;
	}
	public void setIpPhoneno(String ipPhoneno) {
		this.ipPhoneno = ipPhoneno;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getEmployeeno() {
		return employeeno ;
	}
	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}
	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public String getRequestername() {
		return requestername;
	}
	public void setRequestername(String requestername) {
		this.requestername = requestername;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public FormFile getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}
	public String[] getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}
	public String getIssrequestype() {
		return issrequestype;
	}
	public void setIssrequestype(String issrequestype) {
		this.issrequestype = issrequestype;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileFullPath() {
		return fileFullPath;
	}
	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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
	public String getReqEmail() {
		return reqEmail;
	}
	public void setReqEmail(String reqEmail) {
		this.reqEmail = reqEmail;
	}
	public String getReftype() {
		return reftype;
	}
	public void setReftype(String reftype) {
		this.reftype = reftype;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHr() {
		return hr;
	}
	public void setHr(String hr) {
		this.hr = hr;
	}
	public String getRefYear() {
		return refYear;
	}
	public void setRefYear(String refYear) {
		this.refYear = refYear;
	}
	public ArrayList getCategorylist() {
		return categorylist;
	}
	public void setCategorylist(ArrayList categorylist) {
		this.categorylist = categorylist;
	}
	public ArrayList getCategortShortlist() {
		return categortShortlist;
	}
	public void setCategortShortlist(ArrayList categortShortlist) {
		this.categortShortlist = categortShortlist;
	}

	
	
	
	
}
