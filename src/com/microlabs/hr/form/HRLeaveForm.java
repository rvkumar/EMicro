package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class HRLeaveForm extends ActionForm{

//pending request properties
	
private String reqRequstType;
private String selectedFilter;


//leave properties	
	
private String requiredType;
private String empNumber;
private String empName;
private String leaveType;
private String requestType;
private int requestNumber;
private String contentDescription;
private String startDate;
private String startDurationType;
private String endDate;
private String submitDate;
private String endDurationType;
private String noOfDays;
private String message;
private String message3;
private String documentName;
private String fileName;
private String id;
private FormFile documentFile;
public String[] documentCheck;
private String generatedBy;
private String status;
public String[] leaveCheck;
public String[] leavelist;
private String reason;
private String fileNames;
private String holidayType;

private String approver;
private String employeeNumber;
private String designation;
private String approverNumber;
private String employeeName;
private String fileFullPath;
private String videoFullPath;


private String linkName;

private int totalRecords;
private int startRecord;
private int endRecord;

private int next;
private int prev;
private ArrayList myList;
private String filePath;
LinkedList leaveTypeID=new LinkedList();
LinkedList leaveTypeName=new LinkedList();

private float openingBalence;
private float avalableBalence;
private float closingBalence;
private float awaitingBalence;
private int year;



private String emailID;
private String approverManager;
private String reportingMgr;
String approverID="";
String approverName="";
String reportingMgrID="";
String reportingMgrName="";
String appDesig="";
String reportMgrDesg="";

private double totalLeaveDays;
private String message2;
private String reasonType;
private boolean deligate;
private String appType;
private String appType1;

private String comments;
private String approveStatus;
private String approvedDate;
private double cl;
private String  sl;
private String pl; 
private String department;


private float casleavadv;
private float preleavadv;
private float preleavmin;

private float caslclosingBalence;
private float sickclosingBalence;
private float privclosingBalence;
private String doj;

private double sicklvcloseBal;
private double casuallvcloseBal;
private float clMaxDays;
private float slMaxDays;

private int clmindur;
private int slmindur;
private int lossmindur;
private int plmindur;
//onduty properties

private String locationId;
private ArrayList locationIdList=new ArrayList();
private ArrayList locationLabelList=new ArrayList();
private String onDutyType;
private String duration;

private String startTime;
private String endTime;
private String holidyMessage;
private int onDTotal;
private int vpTotal;
private int acTotal;
private int cvTotal;
private int tourTotal;
private String empStatus;

//permission

private String date;

private LinkedList ondutyvalue=new LinkedList();
private LinkedList ondutyreason=new LinkedList();

private	LinkedList leaveReason=new LinkedList();
private LinkedList leaveDetReason=new LinkedList();


private ArrayList yearList = new ArrayList();



private String applyAfterDate;

private String type;

private String swipetype;




public String getType() {
	return type;
}
public void setType(String type) {
	this.type = type;
}
public String getSwipetype() {
	return swipetype;
}
public void setSwipetype(String swipetype) {
	this.swipetype = swipetype;
}
public String getApplyAfterDate() {
	return applyAfterDate;
}
public void setApplyAfterDate(String applyAfterDate) {
	this.applyAfterDate = applyAfterDate;
}
public LinkedList getLeaveReason() {
	return leaveReason;
}
public void setLeaveReason(LinkedList leaveReason) {
	this.leaveReason = leaveReason;
}
public LinkedList getLeaveDetReason() {
	return leaveDetReason;
}
public void setLeaveDetReason(LinkedList leaveDetReason) {
	this.leaveDetReason = leaveDetReason;
}
public ArrayList getYearList() {
	return yearList;
}
public void setYearList(ArrayList yearList) {
	this.yearList = yearList;
}
public LinkedList getOndutyvalue() {
	return ondutyvalue;
}
public void setOndutyvalue(LinkedList ondutyvalue) {
	this.ondutyvalue = ondutyvalue;
}
public LinkedList getOndutyreason() {
	return ondutyreason;
}
public void setOndutyreason(LinkedList ondutyreason) {
	this.ondutyreason = ondutyreason;
}
public int getClmindur() {
	return clmindur;
}
public void setClmindur(int clmindur) {
	this.clmindur = clmindur;
}
public int getSlmindur() {
	return slmindur;
}
public void setSlmindur(int slmindur) {
	this.slmindur = slmindur;
}
public int getLossmindur() {
	return lossmindur;
}
public void setLossmindur(int lossmindur) {
	this.lossmindur = lossmindur;
}
public int getPlmindur() {
	return plmindur;
}
public void setPlmindur(int plmindur) {
	this.plmindur = plmindur;
}
public float getSlMaxDays() {
	return slMaxDays;
}
public void setSlMaxDays(float slMaxDays) {
	this.slMaxDays = slMaxDays;
}
public String getMessage3() {
	return message3;
}
public void setMessage3(String message3) {
	this.message3 = message3;
}
public String getEmpName() {
	return empName;
}
public void setEmpName(String empName) {
	this.empName = empName;
}
public String getReqRequstType() {
	return reqRequstType;
}
public void setReqRequstType(String reqRequstType) {
	this.reqRequstType = reqRequstType;
}
public String getSelectedFilter() {
	return selectedFilter;
}
public void setSelectedFilter(String selectedFilter) {
	this.selectedFilter = selectedFilter;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getEmpStatus() {
	return empStatus;
}
public void setEmpStatus(String empStatus) {
	this.empStatus = empStatus;
}
public int getOnDTotal() {
	return onDTotal;
}
public void setOnDTotal(int onDTotal) {
	this.onDTotal = onDTotal;
}
public int getVpTotal() {
	return vpTotal;
}
public void setVpTotal(int vpTotal) {
	this.vpTotal = vpTotal;
}
public int getAcTotal() {
	return acTotal;
}
public void setAcTotal(int acTotal) {
	this.acTotal = acTotal;
}
public int getCvTotal() {
	return cvTotal;
}
public void setCvTotal(int cvTotal) {
	this.cvTotal = cvTotal;
}
public int getTourTotal() {
	return tourTotal;
}
public void setTourTotal(int tourTotal) {
	this.tourTotal = tourTotal;
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
public String getHolidyMessage() {
	return holidyMessage;
}
public void setHolidyMessage(String holidyMessage) {
	this.holidyMessage = holidyMessage;
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
public String getAppType1() {
	return appType1;
}
public void setAppType1(String appType1) {
	this.appType1 = appType1;
}
public String getRequiredType() {
	return requiredType;
}
public void setRequiredType(String requiredType) {
	this.requiredType = requiredType;
}
public String getEmpNumber() {
	return empNumber;
}
public void setEmpNumber(String empNumber) {
	this.empNumber = empNumber;
}
public String getLeaveType() {
	return leaveType;
}
public void setLeaveType(String leaveType) {
	this.leaveType = leaveType;
}
public int getRequestNumber() {
	return requestNumber;
}
public void setRequestNumber(int requestNumber) {
	this.requestNumber = requestNumber;
}
public String getContentDescription() {
	return contentDescription;
}
public void setContentDescription(String contentDescription) {
	this.contentDescription = contentDescription;
}
public String getStartDate() {
	return startDate;
}
public void setStartDate(String startDate) {
	this.startDate = startDate;
}
public String getStartDurationType() {
	return startDurationType;
}
public void setStartDurationType(String startDurationType) {
	this.startDurationType = startDurationType;
}
public String getEndDate() {
	return endDate;
}
public void setEndDate(String endDate) {
	this.endDate = endDate;
}
public String getSubmitDate() {
	return submitDate;
}
public void setSubmitDate(String submitDate) {
	this.submitDate = submitDate;
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
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getDocumentName() {
	return documentName;
}
public void setDocumentName(String documentName) {
	this.documentName = documentName;
}
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
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
public String getGeneratedBy() {
	return generatedBy;
}
public void setGeneratedBy(String generatedBy) {
	this.generatedBy = generatedBy;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String[] getLeaveCheck() {
	return leaveCheck;
}
public void setLeaveCheck(String[] leaveCheck) {
	this.leaveCheck = leaveCheck;
}
public String[] getLeavelist() {
	return leavelist;
}
public void setLeavelist(String[] leavelist) {
	this.leavelist = leavelist;
}
public String getReason() {
	return reason;
}
public void setReason(String reason) {
	this.reason = reason;
}
public String getFileNames() {
	return fileNames;
}
public void setFileNames(String fileNames) {
	this.fileNames = fileNames;
}
public String getHolidayType() {
	return holidayType;
}
public void setHolidayType(String holidayType) {
	this.holidayType = holidayType;
}
public String getApprover() {
	return approver;
}
public void setApprover(String approver) {
	this.approver = approver;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getApproverNumber() {
	return approverNumber;
}
public void setApproverNumber(String approverNumber) {
	this.approverNumber = approverNumber;
}
public String getEmployeeName() {
	return employeeName;
}
public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
}
public String getFileFullPath() {
	return fileFullPath;
}
public void setFileFullPath(String fileFullPath) {
	this.fileFullPath = fileFullPath;
}
public String getVideoFullPath() {
	return videoFullPath;
}
public void setVideoFullPath(String videoFullPath) {
	this.videoFullPath = videoFullPath;
}
public String getLinkName() {
	return linkName;
}
public void setLinkName(String linkName) {
	this.linkName = linkName;
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
public ArrayList getMyList() {
	return myList;
}
public void setMyList(ArrayList myList) {
	this.myList = myList;
}
public String getFilePath() {
	return filePath;
}
public void setFilePath(String filePath) {
	this.filePath = filePath;
}
public LinkedList getLeaveTypeID() {
	return leaveTypeID;
}
public void setLeaveTypeID(LinkedList leaveTypeID) {
	this.leaveTypeID = leaveTypeID;
}
public LinkedList getLeaveTypeName() {
	return leaveTypeName;
}
public void setLeaveTypeName(LinkedList leaveTypeName) {
	this.leaveTypeName = leaveTypeName;
}

public float getOpeningBalence() {
	return openingBalence;
}
public void setOpeningBalence(float openingBalence) {
	this.openingBalence = openingBalence;
}
public float getAvalableBalence() {
	return avalableBalence;
}
public void setAvalableBalence(float avalableBalence) {
	this.avalableBalence = avalableBalence;
}
public float getClosingBalence() {
	return closingBalence;
}
public void setClosingBalence(float closingBalence) {
	this.closingBalence = closingBalence;
}
public float getAwaitingBalence() {
	return awaitingBalence;
}
public void setAwaitingBalence(float awaitingBalence) {
	this.awaitingBalence = awaitingBalence;
}
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
public String getEmailID() {
	return emailID;
}
public void setEmailID(String emailID) {
	this.emailID = emailID;
}
public String getApproverManager() {
	return approverManager;
}
public void setApproverManager(String approverManager) {
	this.approverManager = approverManager;
}
public String getReportingMgr() {
	return reportingMgr;
}
public void setReportingMgr(String reportingMgr) {
	this.reportingMgr = reportingMgr;
}
public String getApproverID() {
	return approverID;
}
public void setApproverID(String approverID) {
	this.approverID = approverID;
}
public String getApproverName() {
	return approverName;
}
public void setApproverName(String approverName) {
	this.approverName = approverName;
}
public String getReportingMgrID() {
	return reportingMgrID;
}
public void setReportingMgrID(String reportingMgrID) {
	this.reportingMgrID = reportingMgrID;
}
public String getReportingMgrName() {
	return reportingMgrName;
}
public void setReportingMgrName(String reportingMgrName) {
	this.reportingMgrName = reportingMgrName;
}
public String getAppDesig() {
	return appDesig;
}
public void setAppDesig(String appDesig) {
	this.appDesig = appDesig;
}
public String getReportMgrDesg() {
	return reportMgrDesg;
}
public void setReportMgrDesg(String reportMgrDesg) {
	this.reportMgrDesg = reportMgrDesg;
}
public double getTotalLeaveDays() {
	return totalLeaveDays;
}
public void setTotalLeaveDays(double totalLeaveDays) {
	this.totalLeaveDays = totalLeaveDays;
}
public String getMessage2() {
	return message2;
}
public void setMessage2(String message2) {
	this.message2 = message2;
}
public String getReasonType() {
	return reasonType;
}
public void setReasonType(String reasonType) {
	this.reasonType = reasonType;
}
public boolean isDeligate() {
	return deligate;
}
public void setDeligate(boolean deligate) {
	this.deligate = deligate;
}
public String getAppType() {
	return appType;
}
public void setAppType(String appType) {
	this.appType = appType;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getApproveStatus() {
	return approveStatus;
}
public void setApproveStatus(String approveStatus) {
	this.approveStatus = approveStatus;
}
public String getApprovedDate() {
	return approvedDate;
}
public void setApprovedDate(String approvedDate) {
	this.approvedDate = approvedDate;
}
public double getCl() {
	return cl;
}
public void setCl(double cl) {
	this.cl = cl;
}
public String getSl() {
	return sl;
}
public void setSl(String sl) {
	this.sl = sl;
}
public String getPl() {
	return pl;
}
public void setPl(String pl) {
	this.pl = pl;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public float getCasleavadv() {
	return casleavadv;
}
public void setCasleavadv(float casleavadv) {
	this.casleavadv = casleavadv;
}
public float getPreleavadv() {
	return preleavadv;
}
public void setPreleavadv(float preleavadv) {
	this.preleavadv = preleavadv;
}
public float getPreleavmin() {
	return preleavmin;
}
public void setPreleavmin(float preleavmin) {
	this.preleavmin = preleavmin;
}
public float getCaslclosingBalence() {
	return caslclosingBalence;
}
public void setCaslclosingBalence(float caslclosingBalence) {
	this.caslclosingBalence = caslclosingBalence;
}
public float getSickclosingBalence() {
	return sickclosingBalence;
}
public void setSickclosingBalence(float sickclosingBalence) {
	this.sickclosingBalence = sickclosingBalence;
}
public float getPrivclosingBalence() {
	return privclosingBalence;
}
public void setPrivclosingBalence(float privclosingBalence) {
	this.privclosingBalence = privclosingBalence;
}
public String getDoj() {
	return doj;
}
public void setDoj(String doj) {
	this.doj = doj;
}
public double getSicklvcloseBal() {
	return sicklvcloseBal;
}
public void setSicklvcloseBal(double sicklvcloseBal) {
	this.sicklvcloseBal = sicklvcloseBal;
}
public double getCasuallvcloseBal() {
	return casuallvcloseBal;
}
public void setCasuallvcloseBal(double casuallvcloseBal) {
	this.casuallvcloseBal = casuallvcloseBal;
}
public float getClMaxDays() {
	return clMaxDays;
}
public void setClMaxDays(float clMaxDays) {
	this.clMaxDays = clMaxDays;
}
public String getRequestType() {
	return requestType;
}
public void setRequestType(String requestType) {
	this.requestType = requestType;
}
public String getEmployeeNumber() {
	return employeeNumber;
}
public void setEmployeeNumber(String employeeNumber) {
	this.employeeNumber = employeeNumber;
}


}
