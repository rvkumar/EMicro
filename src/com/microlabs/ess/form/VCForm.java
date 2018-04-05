package com.microlabs.ess.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class VCForm extends ActionForm{

	private String empId;
	private String locationId;
	private String floor;
	private String roomName;
	private String purpose;
	
	private String status;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private ArrayList floorList;
	private String searchText;
	private String message;
	private String message2;
	private String requestername;
	private String requesterdepartment;
	private String requesterdesignation;
	private String extno;
	private String ipPhoneno;
	private String hostname;
	private String IPNumber;
	private String location;
	private String locNo;
	private String empno;
	private String empEmailID;
	
	private String fromDate;
	private String fromTime;
	private String toDate;
	private String toTime;
	private String vcFrom;
	private String vcTo;
	
	private String reqNo;
	private String message1;
	private String submitDate;
	private String empName;
	private String approvalStatus;
	private String lastApprover;
	private String pendingApprover;
	private String dept;
	private String desg;
	
	private String requestType;
	private String approvedDate;
	
	private String employeeno;
	private String usage;
	
	
	private ArrayList yearList=new ArrayList();
	private String year;
	private String month;
	private String reqstatus;
	private String duration;
	
	private String displaymonth;
	
	
	/////disha
	
	private String divisionid;
	private String State;
	private String headquater;
	private String[] stateArray;
	private String[] hqArray;
	private String[] deptArray;
	private ArrayList stateList=new ArrayList();
	private ArrayList stateLabelList=new ArrayList();

	private ArrayList hqList=new ArrayList();
	private ArrayList hqLabelList=new ArrayList();

	private ArrayList divList=new ArrayList();
	private ArrayList divLabelList=new ArrayList();
	private String documentCheck;

	private String reqDate;
	private FormFile documentFile;
	private String category;
	private String desc;
	private String filename;
	private String path;
	private String id;
	private String spath;
	private String sfilename;
	private String fromDate1;
	private String toDate1;
	private String frequency;
	private String completed;
	private String selectedRequestNo;
	private String title;
	private String subdate;



	
	private String[] desgArray;
	private ArrayList desgList=new ArrayList();
	private ArrayList desgLabelList=new ArrayList();

	private ArrayList deptList=new ArrayList();
	private ArrayList deptLabelList=new ArrayList();


	private String[] desginationArray;
	private ArrayList desginationList=new ArrayList();
	private ArrayList desginationLabelList=new ArrayList();
	private String group_type;
	
	
	
	
	
	
	
	public String getDivisionid() {
		return divisionid;
	}
	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getHeadquater() {
		return headquater;
	}
	public void setHeadquater(String headquater) {
		this.headquater = headquater;
	}
	public String[] getStateArray() {
		return stateArray;
	}
	public void setStateArray(String[] stateArray) {
		this.stateArray = stateArray;
	}
	public String[] getHqArray() {
		return hqArray;
	}
	public void setHqArray(String[] hqArray) {
		this.hqArray = hqArray;
	}
	public String[] getDeptArray() {
		return deptArray;
	}
	public void setDeptArray(String[] deptArray) {
		this.deptArray = deptArray;
	}
	public ArrayList getStateList() {
		return stateList;
	}
	public void setStateList(ArrayList stateList) {
		this.stateList = stateList;
	}
	public ArrayList getStateLabelList() {
		return stateLabelList;
	}
	public void setStateLabelList(ArrayList stateLabelList) {
		this.stateLabelList = stateLabelList;
	}
	public ArrayList getHqList() {
		return hqList;
	}
	public void setHqList(ArrayList hqList) {
		this.hqList = hqList;
	}
	public ArrayList getHqLabelList() {
		return hqLabelList;
	}
	public void setHqLabelList(ArrayList hqLabelList) {
		this.hqLabelList = hqLabelList;
	}
	public ArrayList getDivList() {
		return divList;
	}
	public void setDivList(ArrayList divList) {
		this.divList = divList;
	}
	public ArrayList getDivLabelList() {
		return divLabelList;
	}
	public void setDivLabelList(ArrayList divLabelList) {
		this.divLabelList = divLabelList;
	}
	public String getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String documentCheck) {
		this.documentCheck = documentCheck;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public FormFile getDocumentFile() {
		return documentFile;
	}
	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSpath() {
		return spath;
	}
	public void setSpath(String spath) {
		this.spath = spath;
	}
	public String getSfilename() {
		return sfilename;
	}
	public void setSfilename(String sfilename) {
		this.sfilename = sfilename;
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
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getCompleted() {
		return completed;
	}
	public void setCompleted(String completed) {
		this.completed = completed;
	}
	public String getSelectedRequestNo() {
		return selectedRequestNo;
	}
	public void setSelectedRequestNo(String selectedRequestNo) {
		this.selectedRequestNo = selectedRequestNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubdate() {
		return subdate;
	}
	public void setSubdate(String subdate) {
		this.subdate = subdate;
	}
	public String[] getDesgArray() {
		return desgArray;
	}
	public void setDesgArray(String[] desgArray) {
		this.desgArray = desgArray;
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
	public String[] getDesginationArray() {
		return desginationArray;
	}
	public void setDesginationArray(String[] desginationArray) {
		this.desginationArray = desginationArray;
	}
	public ArrayList getDesginationList() {
		return desginationList;
	}
	public void setDesginationList(ArrayList desginationList) {
		this.desginationList = desginationList;
	}
	public ArrayList getDesginationLabelList() {
		return desginationLabelList;
	}
	public void setDesginationLabelList(ArrayList desginationLabelList) {
		this.desginationLabelList = desginationLabelList;
	}
	public String getGroup_type() {
		return group_type;
	}
	public void setGroup_type(String group_type) {
		this.group_type = group_type;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDisplaymonth() {
		return displaymonth;
	}
	public void setDisplaymonth(String displaymonth) {
		this.displaymonth = displaymonth;
	}
	public ArrayList getYearList() {
		return yearList;
	}
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getReqstatus() {
		return reqstatus;
	}
	public void setReqstatus(String reqstatus) {
		this.reqstatus = reqstatus;
	}
	public String getEmployeeno() {
		return employeeno;
	}
	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getVcFrom() {
		return vcFrom;
	}
	public void setVcFrom(String vcFrom) {
		this.vcFrom = vcFrom;
	}
	public String getVcTo() {
		return vcTo;
	}
	public void setVcTo(String vcTo) {
		this.vcTo = vcTo;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
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
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getMessage1() {
		return message1;
	}
	public void setMessage1(String message1) {
		this.message1 = message1;
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
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getLocNo() {
		return locNo;
	}
	public void setLocNo(String locNo) {
		this.locNo = locNo;
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
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public ArrayList getFloorList() {
		return floorList;
	}
	public void setFloorList(ArrayList floorList) {
		this.floorList = floorList;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
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
	
	
	
}
