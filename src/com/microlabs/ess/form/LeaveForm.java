package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class LeaveForm extends ActionForm{
	private String typeOfTravel;
	private String fromPlace;
	private String toPlace;
	private String departOn;
	private String departTime;
	private String returnOn;
	public String getTypeOfTravel() {
		return typeOfTravel;
	}

	public void setTypeOfTravel(String typeOfTravel) {
		this.typeOfTravel = typeOfTravel;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public String getDepartOn() {
		return departOn;
	}

	public void setDepartOn(String departOn) {
		this.departOn = departOn;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getReturnOn() {
		return returnOn;
	}

	public void setReturnOn(String returnOn) {
		this.returnOn = returnOn;
	}

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
	private	LinkedList leaveTypeID=new LinkedList();
	private LinkedList leaveTypeName=new LinkedList();
	
	
	private	LinkedList leaveReason=new LinkedList();
	private LinkedList leaveDetReason=new LinkedList();
	
	private String reasonID;
	private String reasonName;
	
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
	private String appType="";
	private String comments;
	private String approveStatus;
	private String approvedDate;
    private double cl;
    private String  sl;
    private String pl; 
    private String comp; 
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
	
	private float caslopenBalence;
	private float sickopenBalence;
	private float privopenBalence;
	
	
	private ArrayList emplIdList = new ArrayList();
	private ArrayList empLabelList = new ArrayList();
	private ArrayList yearList = new ArrayList();
    private String empno;
    private String empyear;
    
	// comp_off

	private String nofhrs;
	private String shift;
	public String reqFiled;
	
	public String searchText;
	

	public String comFromtime;
	public String comTotime;
	
	public String comAvailDate;
	public String comAvailDays;
	public String compBalance;
	
	private ArrayList departmentList = new ArrayList();
	private ArrayList locationIdList = new ArrayList();
	private ArrayList locationLabelList = new ArrayList();

	public String locationId;
	public String empName;

	
	private String frststatus;
	private String secstatus;
	
	private String month;

private String applyAfterDate;
	
	
	
private String papprover;
private String lapprover;



public String getPapprover() {
	return papprover;
}

public void setPapprover(String papprover) {
	this.papprover = papprover;
}

public String getLapprover() {
	return lapprover;
}

public void setLapprover(String lapprover) {
	this.lapprover = lapprover;
}

	
	public String getApplyAfterDate() {
		return applyAfterDate;
	}

	public void setApplyAfterDate(String applyAfterDate) {
		this.applyAfterDate = applyAfterDate;
	}
	
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getFrststatus() {
		return frststatus;
	}

	public void setFrststatus(String frststatus) {
		this.frststatus = frststatus;
	}

	public String getSecstatus() {
		return secstatus;
	}

	public void setSecstatus(String secstatus) {
		this.secstatus = secstatus;
	}

	public ArrayList getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
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

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getComAvailDate() {
		return comAvailDate;
	}

	public void setComAvailDate(String comAvailDate) {
		this.comAvailDate = comAvailDate;
	}

	public String getComAvailDays() {
		return comAvailDays;
	}

	public void setComAvailDays(String comAvailDays) {
		this.comAvailDays = comAvailDays;
	}

	public String getCompBalance() {
		return compBalance;
	}

	public void setCompBalance(String compBalance) {
		this.compBalance = compBalance;
	}

	public String getComFromtime() {
		return comFromtime;
	}

	public void setComFromtime(String comFromtime) {
		this.comFromtime = comFromtime;
	}

	public String getComTotime() {
		return comTotime;
	}

	public void setComTotime(String comTotime) {
		this.comTotime = comTotime;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getNofhrs() {
		return nofhrs;
	}

	public void setNofhrs(String nofhrs) {
		this.nofhrs = nofhrs;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getReqFiled() {
		return reqFiled;
	}

	public void setReqFiled(String reqFiled) {
		this.reqFiled = reqFiled;
	}

	public ArrayList getEmplIdList() {
		return emplIdList;
	}

	public void setEmplIdList(ArrayList emplIdList) {
		this.emplIdList = emplIdList;
	}

	public ArrayList getEmpLabelList() {
		return empLabelList;
	}

	public void setEmpLabelList(ArrayList empLabelList) {
		this.empLabelList = empLabelList;
	}

	public ArrayList getYearList() {
		return yearList;
	}

	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	public String getEmpyear() {
		return empyear;
	}

	public void setEmpyear(String empyear) {
		this.empyear = empyear;
	}

	public String getReasonID() {
		return reasonID;
	}

	public void setReasonID(String reasonID) {
		this.reasonID = reasonID;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
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

	public String getComp() {
		return comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public float getCaslopenBalence() {
		return caslopenBalence;
	}

	public void setCaslopenBalence(float caslopenBalence) {
		this.caslopenBalence = caslopenBalence;
	}

	public float getSickopenBalence() {
		return sickopenBalence;
	}

	public void setSickopenBalence(float sickopenBalence) {
		this.sickopenBalence = sickopenBalence;
	}

	public float getPrivopenBalence() {
		return privopenBalence;
	}

	public void setPrivopenBalence(float privopenBalence) {
		this.privopenBalence = privopenBalence;
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

	public float getClMaxDays() {
		return clMaxDays;
	}

	public void setClMaxDays(float clMaxDays) {
		this.clMaxDays = clMaxDays;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
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

	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public float getAwaitingBalence() {
		return awaitingBalence;
	}

	public void setAwaitingBalence(float awaitingBalence) {
		this.awaitingBalence = awaitingBalence;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getReasonType() {
		return reasonType;
	}

	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public double getTotalLeaveDays() {
		return totalLeaveDays;
	}

	public void setTotalLeaveDays(double totalLeaveDays) {
		this.totalLeaveDays = totalLeaveDays;
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

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public float getOpeningBalence() {
		return openingBalence;
	}

	public void setOpeningBalence(float openingBalence) {
		this.openingBalence = openingBalence;
	}

	public float getClosingBalence() {
		return closingBalence;
	}

	public void setClosingBalence(float closingBalence) {
		this.closingBalence = closingBalence;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public float getAvalableBalence() {
		return avalableBalence;
	}

	public void setAvalableBalence(float avalableBalence) {
		this.avalableBalence = avalableBalence;
	}

	public LinkedList getLeaveTypeName() {
		return leaveTypeName;
	}

	public void setLeaveTypeName(LinkedList leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}

	public LinkedList getLeaveTypeID() {
		return leaveTypeID;
	}

	public void setLeaveTypeID(LinkedList leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}



	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public ArrayList getMyList() {
		return myList;
	}

	public void setMyList(ArrayList myList) {
		this.myList = myList;
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

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
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

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getApproverNumber() {
		return approverNumber;
	}

	public void setApproverNumber(String approverNumber) {
		this.approverNumber = approverNumber;
	}

	public String getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}

	public String[] getLeavelist() {
		return leavelist;
	}

	public void setLeavelist(String[] leavelist) {
		this.leavelist = leavelist;
	}

	public String[] getLeaveCheck() {
		return leaveCheck;
	}

	public void setLeaveCheck(String[] leaveCheck) {
		this.leaveCheck = leaveCheck;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FormFile getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
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

	public String getEndDurationType() {
		return endDurationType;
	}

	public void setEndDurationType(String endDurationType) {
		this.endDurationType = endDurationType;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public boolean getDeligate() {
		return deligate;
	}

	public void setDeligate(boolean deligate) {
		this.deligate = deligate;
	}
	
	public String getAppType() {
		return appType;
	}

	public void setApptype(String appType) {
		this.appType = appType;
	}

	public float getPreleavmin() {
		return preleavmin;
	}

	public void setPreleavmin(float preleavmin) {
		this.preleavmin = preleavmin;
	}
	
	
}
