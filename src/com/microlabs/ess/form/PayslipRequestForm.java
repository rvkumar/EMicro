package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class PayslipRequestForm extends ActionForm{
	
private int req_id;
private int req_by;
private int month;
private int year;
private String req_type;
private String status;
private String req_date;

private String monthname;
private String empname;


private String subject;

private String description;

private String apprvddate;

private String pendappr;

private String lastappr;


private ArrayList yearList=new ArrayList();


private String message;



private String empdep;
private String empdesg;
private String emploc;
private String empext;



private String hrID;
private String hrName;


private int totalRecords;
private int startRecord;
private int endRecord;


private String selectedFilter;

private String comments;




private String chooseKeyword;


private String datefrom;

private String dateto;

private String category;


private LinkedList categoryList=new LinkedList();





public LinkedList getCategoryList() {
	return categoryList;
}
public void setCategoryList(LinkedList categoryList) {
	this.categoryList = categoryList;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getDatefrom() {
	return datefrom;
}
public void setDatefrom(String datefrom) {
	this.datefrom = datefrom;
}
public String getDateto() {
	return dateto;
}
public void setDateto(String dateto) {
	this.dateto = dateto;
}
public String getChooseKeyword() {
	return chooseKeyword;
}
public void setChooseKeyword(String chooseKeyword) {
	this.chooseKeyword = chooseKeyword;
}
public String getComments() {
	return comments;
}
public void setComments(String comments) {
	this.comments = comments;
}
public String getSelectedFilter() {
	return selectedFilter;
}
public void setSelectedFilter(String selectedFilter) {
	this.selectedFilter = selectedFilter;
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
public String getHrID() {
	return hrID;
}
public void setHrID(String hrID) {
	this.hrID = hrID;
}
public String getHrName() {
	return hrName;
}
public void setHrName(String hrName) {
	this.hrName = hrName;
}
public String getEmpdep() {
	return empdep;
}
public void setEmpdep(String empdep) {
	this.empdep = empdep;
}
public String getEmpdesg() {
	return empdesg;
}
public void setEmpdesg(String empdesg) {
	this.empdesg = empdesg;
}
public String getEmploc() {
	return emploc;
}
public void setEmploc(String emploc) {
	this.emploc = emploc;
}
public String getEmpext() {
	return empext;
}
public void setEmpext(String empext) {
	this.empext = empext;
}
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public String getDescription() {
	return description;
}
public void setDescription(String description) {
	this.description = description;
}
public String getApprvddate() {
	return apprvddate;
}
public void setApprvddate(String apprvddate) {
	this.apprvddate = apprvddate;
}
public String getPendappr() {
	return pendappr;
}
public void setPendappr(String pendappr) {
	this.pendappr = pendappr;
}
public String getLastappr() {
	return lastappr;
}
public void setLastappr(String lastappr) {
	this.lastappr = lastappr;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public ArrayList getYearList() {
	return yearList;
}
public void setYearList(ArrayList yearList) {
	this.yearList = yearList;
}
public String getMonthname() {
	return monthname;
}
public void setMonthname(String monthname) {
	this.monthname = monthname;
}
public String getEmpname() {
	return empname;
}
public void setEmpname(String empname) {
	this.empname = empname;
}
public int getReq_id() {
	return req_id;
}
public void setReq_id(int req_id) {
	this.req_id = req_id;
}
public int getReq_by() {
	return req_by;
}
public void setReq_by(int req_by) {
	this.req_by = req_by;
}
public int getMonth() {
	return month;
}
public void setMonth(int month) {
	this.month = month;
}
public int getYear() {
	return year;
}
public void setYear(int year) {
	this.year = year;
}
public String getReq_type() {
	return req_type;
}
public void setReq_type(String req_type) {
	this.req_type = req_type;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getReq_date() {
	return req_date;
}
public void setReq_date(String req_date) {
	this.req_date = req_date;
}
	
	
	
}
