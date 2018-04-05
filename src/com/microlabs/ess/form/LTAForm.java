package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class LTAForm extends ActionForm{
	private int id;
	private int requestNo;
	private String employeeNo;
	private String fiscalYear;
	private String fiscalYearDesc;
	private String employeeName;
	private String message;
	
	
	private String travelStartDate;
	private String travelEndDate;
	private String ltaAmtApplFor;
	private String ltaAmtAprvdFor;
	private String leaveDetails;
	public String[] documentCheck;
	private String leaveType;
	private String startDate;
	private String startDurationType;
	private String endDate;
	private String submitDate;
	private String endDurationType;
	private String noOfDays;
	private double totalLeaveDays;
	LinkedList leaveTypeID=new LinkedList();
	LinkedList leaveTypeName=new LinkedList();
	private float preleavmin;
	private float casleavadv;
	private float preleavadv;
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
	private int year;
	private String department;
	private String designation;
	private String approvalStatus;  
	private String lastApprover;
	private String pendingApprover;
	
	 private ArrayList yearList=new ArrayList();
	 
	 
		private String location;
		private String staffCategory;
		
		
		private FormFile documentFile;
		
		
		public double totalIncome;
		
		private String billno;
		private String billdesc;
		private String billdate;
		private String billamount;
		private String remarks;
		
		private String billamountapproved;
		
		
		
		
	
	public String getBillamountapproved() {
			return billamountapproved;
		}
		public void setBillamountapproved(String billamountapproved) {
			this.billamountapproved = billamountapproved;
		}
	public String getBillno() {
			return billno;
		}
		public void setBillno(String billno) {
			this.billno = billno;
		}
		public String getBilldesc() {
			return billdesc;
		}
		public void setBilldesc(String billdesc) {
			this.billdesc = billdesc;
		}
		public String getBilldate() {
			return billdate;
		}
		public void setBilldate(String billdate) {
			this.billdate = billdate;
		}
		public String getBillamount() {
			return billamount;
		}
		public void setBillamount(String billamount) {
			this.billamount = billamount;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	public double getTotalIncome() {
			return totalIncome;
		}
		public void setTotalIncome(double totalIncome) {
			this.totalIncome = totalIncome;
		}
	public FormFile getDocumentFile() {
			return documentFile;
		}
		public void setDocumentFile(FormFile documentFile) {
			this.documentFile = documentFile;
		}
	public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public String getStaffCategory() {
			return staffCategory;
		}
		public void setStaffCategory(String staffCategory) {
			this.staffCategory = staffCategory;
		}
	public ArrayList getYearList() {
		return yearList;
	}
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
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
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String[] getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
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
	public float getSlMaxDays() {
		return slMaxDays;
	}
	public void setSlMaxDays(float slMaxDays) {
		this.slMaxDays = slMaxDays;
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
	public float getPreleavmin() {
		return preleavmin;
	}
	public void setPreleavmin(float preleavmin) {
		this.preleavmin = preleavmin;
	}
	public double getTotalLeaveDays() {
		return totalLeaveDays;
	}
	public void setTotalLeaveDays(double totalLeaveDays) {
		this.totalLeaveDays = totalLeaveDays;
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
	public String getLeaveDetails() {
		return leaveDetails;
	}
	public void setLeaveDetails(String leaveDetails) {
		this.leaveDetails = leaveDetails;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
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
	public String getTravelStartDate() {
		return travelStartDate;
	}
	public void setTravelStartDate(String travelStartDate) {
		this.travelStartDate = travelStartDate;
	}
	public String getTravelEndDate() {
		return travelEndDate;
	}
	public void setTravelEndDate(String travelEndDate) {
		this.travelEndDate = travelEndDate;
	}
	public String getLtaAmtApplFor() {
		return ltaAmtApplFor;
	}
	public void setLtaAmtApplFor(String ltaAmtApplFor) {
		this.ltaAmtApplFor = ltaAmtApplFor;
	}
	public String getLtaAmtAprvdFor() {
		return ltaAmtAprvdFor;
	}
	public void setLtaAmtAprvdFor(String ltaAmtAprvdFor) {
		this.ltaAmtAprvdFor = ltaAmtAprvdFor;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(String fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public String getFiscalYearDesc() {
		return fiscalYearDesc;
	}
	public void setFiscalYearDesc(String fiscalYearDesc) {
		this.fiscalYearDesc = fiscalYearDesc;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	
	
}
