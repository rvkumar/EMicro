package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class HolidayForm extends ActionForm{
	
	private String holidayName;
	private String holidayDate;
	private String dayName;
	private String holidayType;
		
	private String message;
	private String modifyDate;
	
	private String location;
	private LinkedList locationId;
	private LinkedList locationName;
	
	private String loc;
	private String holidayYear;
	private String linkName;
	
	private String locationAddress;
	private ArrayList years=new ArrayList();
	
	private String header;
	
	
	
	
	public String getLocationAddress() {
		return locationAddress;
	}
	public void setLocationAddress(String locationAddress) {
		this.locationAddress = locationAddress;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getHolidayYear() {
		return holidayYear;
	}
	public void setHolidayYear(String holidayYear) {
		this.holidayYear = holidayYear;
	}
	public ArrayList getYears() {
		return years;
	}
	public void setYears(ArrayList years) {
		this.years = years;
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
	
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	
	
}
