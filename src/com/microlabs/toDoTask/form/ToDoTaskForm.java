package com.microlabs.toDoTask.form;

import org.apache.struts.action.ActionForm;

public class ToDoTaskForm extends ActionForm{
	
	private int sno;
	private String contactPerson;
	private String iMonth;
	private String iYear;
	private String subject;
	private String description;
	private String sheduleTime;
	private String remarks;
	private String status;
	private String taskdate;
	private String statusMessage;
	private String holiday_name;
	private String day_name;
	private String hol_name;
	
	private String emp_name;
	private String emp_id;
	private String from_date;
	private String from_time;
	private String to_date;
	private String to_time;
	private String requiredRemiderDate;
	private double totalDays;
	
	
	
	public double getTotalDays() {
		return totalDays;
	}

	public void setTotalDays(double totalDays) {
		this.totalDays = totalDays;
	}

	public String getRequiredRemiderDate() {
		return requiredRemiderDate;
	}

	public void setRequiredRemiderDate(String requiredRemiderDate) {
		this.requiredRemiderDate = requiredRemiderDate;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getFrom_time() {
		return from_time;
	}

	public void setFrom_time(String from_time) {
		this.from_time = from_time;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getTo_time() {
		return to_time;
	}

	public void setTo_time(String to_time) {
		this.to_time = to_time;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getHoliday_name() {
		return holiday_name;
	}

	public void setHoliday_name(String holiday_name) {
		this.holiday_name = holiday_name;
	}

	public String getDay_name() {
		return day_name;
	}

	public void setDay_name(String day_name) {
		this.day_name = day_name;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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

	public String getSheduleTime() {
		return sheduleTime;
	}

	public void setSheduleTime(String sheduleTime) {
		this.sheduleTime = sheduleTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTaskdate() {
		return taskdate;
	}

	public void setTaskdate(String taskdate) {
		this.taskdate = taskdate;
	}

	public String getIMonth() {
		return iMonth;
	}

	public void setIMonth(String month) {
		iMonth = month;
	}

	public String getIYear() {
		return iYear;
	}

	public void setIYear(String year) {
		iYear = year;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getHol_name() {
		return hol_name;
	}

	public void setHol_name(String hol_name) {
		this.hol_name = hol_name;
	}

}
