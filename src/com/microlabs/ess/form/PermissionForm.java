package com.microlabs.ess.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class PermissionForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int opbal;
	private int closbal;
	private int awtbal;
	private int availbal;
	private String designation;
	private String doj;
	private String locationId;
	
	private String date;
	private String startTime;
	
	private String Reason;
	private int requestNumber;
	
	private String employeeName;
	private String employeeNo;
	private String desg;
	private String department;
	
	private String approver;
	private String approverStatus;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String message;
	private String message2;
	private String endTime;
	private String approved_date;
	private String rejected_date;
	private String approveDate;
	private String comments;
	private String type;

	private String locname;
	
	
	   public String getLocname() {
		return locname;
	}

	public void setLocname(String locname) {
		this.locname = locname;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public int getOpbal() {
		return opbal;
	}

	public void setOpbal(int opbal) {
		this.opbal = opbal;
	}

	public int getClosbal() {
		return closbal;
	}

	public void setClosbal(int closbal) {
		this.closbal = closbal;
	}

	public int getAwtbal() {
		return awtbal;
	}

	public void setAwtbal(int awtbal) {
		this.awtbal = awtbal;
	}

	public int getAvailbal() {
		return availbal;
	}

	public void setAvailbal(int availbal) {
		this.availbal = availbal;
	}

	private String remark;
	   
	   private String reqdate;
	   
	   private String swipetype;
	   
	   private String frommonth;
		private ArrayList yearList=new ArrayList();
		
		
		private String year;
		
		
		private String shift;
	   
		
		private String attendanceStatus;
		
		
		
		
	
	public String getFrommonth() {
			return frommonth;
		}

		public void setFrommonth(String frommonth) {
			this.frommonth = frommonth;
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

		public String getShift() {
			return shift;
		}

		public void setShift(String shift) {
			this.shift = shift;
		}

		public String getAttendanceStatus() {
			return attendanceStatus;
		}

		public void setAttendanceStatus(String attendanceStatus) {
			this.attendanceStatus = attendanceStatus;
		}

	public String getSwipetype() {
		return swipetype;
	}

	public void setSwipetype(String swipetype) {
		this.swipetype = swipetype;
	}

	public String getReqdate() {
		return reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getReason() {
		return Reason;
	}

	public void setReason(String reason) {
		this.Reason = reason;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getDesg() {
		return desg;
	}

	public void setDesg(String desg) {
		this.desg = desg;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getApproved_date() {
		return approved_date;
	}

	public void setApproved_date(String approved_date) {
		this.approved_date = approved_date;
	}

	public String getRejected_date() {
		return rejected_date;
	}

	public void setRejected_date(String rejected_date) {
		this.rejected_date = rejected_date;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	
	

}
