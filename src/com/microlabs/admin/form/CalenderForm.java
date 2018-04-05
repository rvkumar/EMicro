package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class CalenderForm extends ActionForm {

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
	
	private String emp_name;
	private String emp_id;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private String locationId;
	
	
	private String weekSaturday;
	private String weekOff;
	private String publicHoliday;
	private String curentDate;
	
	
	
	
	private String holidayType;
	private String message;
	private String message2;
	
	private String frequency;
	private String day;
	private String month;
	
	
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public String getiMonth() {
		return iMonth;
	}
	public void setiMonth(String iMonth) {
		this.iMonth = iMonth;
	}
	public String getiYear() {
		return iYear;
	}
	public void setiYear(String iYear) {
		this.iYear = iYear;
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
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
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
	public String getWeekSaturday() {
		return weekSaturday;
	}
	public void setWeekSaturday(String weekSaturday) {
		this.weekSaturday = weekSaturday;
	}
	public String getWeekOff() {
		return weekOff;
	}
	public void setWeekOff(String weekOff) {
		this.weekOff = weekOff;
	}
	public String getPublicHoliday() {
		return publicHoliday;
	}
	public void setPublicHoliday(String publicHoliday) {
		this.publicHoliday = publicHoliday;
	}
	public String getCurentDate() {
		return curentDate;
	}
	public void setCurentDate(String curentDate) {
		this.curentDate = curentDate;
	}
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
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
