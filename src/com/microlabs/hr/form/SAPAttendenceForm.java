package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class SAPAttendenceForm extends ActionForm {

	private String employeeNo;

	private String month;
	private String payGroup;
	private LinkedList payGroupID;
	private LinkedList payGroupShort_Name;

	private String date;
	private String day;
	private String iNTIME;
	
	
	private String iNSTATUS;
	private String oUTTIME;
	private String oUTSTATUS;
	private String message;
	private String message1;

	private ArrayList ar_id = new ArrayList();
	private ArrayList ar_name = new ArrayList();

	private String remarks;
	
	private ArrayList emplIdList = new ArrayList();
	private ArrayList empLabelList = new ArrayList();
	
    private String empno;
  
	private String shift;
	
	private String allpunch;
	
	private String shiftStart;
    
	private String shiftEnd;
	
	private String iNSTATUSstar;
	private String oUTSTATUSstar;
	private String iNTIMEhash;
	private String oUTTIMEhash;
	
    
	
	
    public String getAllpunch() {
		return allpunch;
	}

	public void setAllpunch(String allpunch) {
		this.allpunch = allpunch;
	}

	public String getShiftStart() {
		return shiftStart;
	}

	public void setShiftStart(String shiftStart) {
		this.shiftStart = shiftStart;
	}

	public String getShiftEnd() {
		return shiftEnd;
	}

	public void setShiftEnd(String shiftEnd) {
		this.shiftEnd = shiftEnd;
	}

	public String getiNSTATUSstar() {
		return iNSTATUSstar;
	}

	public void setiNSTATUSstar(String iNSTATUSstar) {
		this.iNSTATUSstar = iNSTATUSstar;
	}

	public String getoUTSTATUSstar() {
		return oUTSTATUSstar;
	}

	public void setoUTSTATUSstar(String oUTSTATUSstar) {
		this.oUTSTATUSstar = oUTSTATUSstar;
	}

	public String getiNTIMEhash() {
		return iNTIMEhash;
	}

	public void setiNTIMEhash(String iNTIMEhash) {
		this.iNTIMEhash = iNTIMEhash;
	}

	public String getoUTTIMEhash() {
		return oUTTIMEhash;
	}

	public void setoUTTIMEhash(String oUTTIMEhash) {
		this.oUTTIMEhash = oUTTIMEhash;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
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

	

	

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empno) {
		this.empno = empno;
	}

	

	

    
    public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ArrayList getAr_id() {
		return ar_id;
	}

	public void setAr_id(ArrayList ar_id) {
		this.ar_id = ar_id;
	}

	public ArrayList getAr_name() {
		return ar_name;
	}

	public void setAr_name(ArrayList ar_name) {
		this.ar_name = ar_name;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getiNTIME() {
		return iNTIME;
	}

	public void setiNTIME(String iNTIME) {
		this.iNTIME = iNTIME;
	}

	public String getiNSTATUS() {
		return iNSTATUS;
	}

	public void setiNSTATUS(String iNSTATUS) {
		this.iNSTATUS = iNSTATUS;
	}

	public String getoUTTIME() {
		return oUTTIME;
	}

	public void setoUTTIME(String oUTTIME) {
		this.oUTTIME = oUTTIME;
	}

	public String getoUTSTATUS() {
		return oUTSTATUS;
	}

	public void setoUTSTATUS(String oUTSTATUS) {
		this.oUTSTATUS = oUTSTATUS;
	}

	public LinkedList getPayGroupID() {
		return payGroupID;
	}

	public void setPayGroupID(LinkedList payGroupID) {
		this.payGroupID = payGroupID;
	}

	public LinkedList getPayGroupShort_Name() {
		return payGroupShort_Name;
	}

	public void setPayGroupShort_Name(LinkedList payGroupShort_Name) {
		this.payGroupShort_Name = payGroupShort_Name;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getPayGroup() {
		return payGroup;
	}

	public void setPayGroup(String payGroup) {
		this.payGroup = payGroup;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

}
