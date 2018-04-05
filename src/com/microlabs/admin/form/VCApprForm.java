package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class VCApprForm extends ActionForm{

	private String locationId;
	
	private String floor;
	private String roomName;
	private String status;
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private ArrayList floorList;
	
	public String approver1;
	public String approver2;
	public String approver3;
	private String searchText;
	private String emp;
	private String employeeNo;
	public int priority1;
	public int priority2;
	public int priority3;
	private String message;
	private String message2;
	
	private int totalRecords;
	
	private String locationname;
	
	
	private String addflr;
	private String addroom;
	private String locationId1;
	private String floor1;
	
	
	
	
	
	
	
	public String getFloor1() {
		return floor1;
	}
	public void setFloor1(String floor1) {
		this.floor1 = floor1;
	}
	public String getLocationId1() {
		return locationId1;
	}
	public void setLocationId1(String locationId1) {
		this.locationId1 = locationId1;
	}
	public String getAddflr() {
		return addflr;
	}
	public void setAddflr(String addflr) {
		this.addflr = addflr;
	}
	public String getAddroom() {
		return addroom;
	}
	public void setAddroom(String addroom) {
		this.addroom = addroom;
	}
	public String getLocationname() {
		return locationname;
	}
	public void setLocationname(String locationname) {
		this.locationname = locationname;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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
	public int getPriority1() {
		return priority1;
	}
	public void setPriority1(int priority1) {
		this.priority1 = priority1;
	}
	public int getPriority2() {
		return priority2;
	}
	public void setPriority2(int priority2) {
		this.priority2 = priority2;
	}
	public int getPriority3() {
		return priority3;
	}
	public void setPriority3(int priority3) {
		this.priority3 = priority3;
	}
	public String getEmp() {
		return emp;
	}
	public void setEmp(String emp) {
		this.emp = emp;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public ArrayList getFloorList() {
		return floorList;
	}
	public void setFloorList(ArrayList floorList) {
		this.floorList = floorList;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getApprover1() {
		return approver1;
	}
	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}
	public String getApprover2() {
		return approver2;
	}
	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}
	public String getApprover3() {
		return approver3;
	}
	public void setApprover3(String approver3) {
		this.approver3 = approver3;
	}
	
	
}
