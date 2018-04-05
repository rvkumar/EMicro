package com.microlabs.admin.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class HolidayForm extends ActionForm{

	private String holidayName;
	private String holidayDate;
	private String dayName;

	private String date;
	private String message;
	private String modifyDate;
	
	private String location;
	private LinkedList locationId;
	private LinkedList locationName;
	
	private String loc;
	
	private String requiredLocation;
	private String requiredString;
	private int year;
	
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getRequiredString() {
		return requiredString;
	}
	public void setRequiredString(String requiredString) {
		this.requiredString = requiredString;
	}
	public String getRequiredLocation() {
		return requiredLocation;
	}
	public void setRequiredLocation(String requiredLocation) {
		this.requiredLocation = requiredLocation;
	}
	public String getDayName() {
		return dayName;
	}
	public void setDayName(String dayName) {
		this.dayName = dayName;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getHolidayName() {
		return holidayName;
	}
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	public String getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LinkedList getLocationId() {
		return locationId;
	}
	public void setLocationId(LinkedList locationId) {
		this.locationId = locationId;
	}
	public LinkedList getLocationName() {
		return locationName;
	}
	public void setLocationName(LinkedList locationName) {
		this.locationName = locationName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
	
}
