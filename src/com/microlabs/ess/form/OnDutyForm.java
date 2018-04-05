package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class OnDutyForm extends ActionForm{
	
	
	
	private String onDutyType;
	private String requestType;
	private int requestNumber;
	private String duration;
	private String contentDescription;
	private String startDate;
	private String startDurationType;
	private String endDate;
	private String submitDate;
	private String endDurationType;
	private String noOfDays;
	private String message;
	private String message2;
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
	private String department;
	private String designation;
	private String approverNumber;
	private String approverDesg;
	private String employeeName;
	private String fileFullPath;
	private String videoFullPath;
	
	private String linkName;

	private int totalRecords1;
	private int startRecord1;
	private int endRecord1;
	
	private int onDTotal;
	private int vpTotal;
	private int acTotal;
	private int cvTotal;
	private int tourTotal;
    private String remark;
	private String approveDate;
	private String locationId;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private String startTime;
	private String endTime;
	private String holidyMessage;
	
	
	private LinkedList ondutyvalue=new LinkedList();
	private LinkedList ondutyreason=new LinkedList();
	
	private String frststatus;
	private String secstatus;
	
	private String month;

	private String year;

	
	
	
	private String applyAfterDate;
	
private String doj;
	
	private ArrayList emplIdList = new ArrayList();
	private ArrayList empLabelList = new ArrayList();
	private ArrayList yearList = new ArrayList();
    private String empno;
    private String empyear;
    
    
    
	

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
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

	public String getApplyAfterDate() {
		return applyAfterDate;
	}

	public void setApplyAfterDate(String applyAfterDate) {
		this.applyAfterDate = applyAfterDate;
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

	public String getHolidyMessage() {
		return holidyMessage;
	}

	public void setHolidyMessage(String holidyMessage) {
		this.holidyMessage = holidyMessage;
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

	public int getTotalRecords1() {
		return totalRecords1;
	}

	public void setTotalRecords1(int totalRecords1) {
		this.totalRecords1 = totalRecords1;
	}

	public int getStartRecord1() {
		return startRecord1;
	}

	public void setStartRecord1(int startRecord1) {
		this.startRecord1 = startRecord1;
	}

	public int getEndRecord1() {
		return endRecord1;
	}

	public void setEndRecord1(int endRecord1) {
		this.endRecord1 = endRecord1;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getOnDutyType() {
		return onDutyType;
	}

	public void setOnDutyType(String onDutyType) {
		this.onDutyType = onDutyType;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	
	public int getCvTotal() {
		return cvTotal;
	}
	public void setCvTotal(int cvTotal) {
		this.cvTotal = cvTotal;
	}
	
	public int getAcTotal() {
		return acTotal;
	}

	public void setAcTotal(int acTotal) {
		this.acTotal = acTotal;
	}
	
	public int getTourTotal() {
		return tourTotal;
	}

	public void setTourTotal(int tourTotal) {
		this.tourTotal = tourTotal;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getApproverDesg() {
		return approverDesg;
	}

	public void setApproverDesg(String approverDesg) {
		this.approverDesg = approverDesg;
	}

}
