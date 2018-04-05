package com.microlabs.admin.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class AuthenticationForm extends ActionForm{

	private String employeeNumber ;
	private String[] approverID;
	private String[] designation;
	private String[] emailID;
	
	private String requestType;
	private String apprID;
	private String empDesignation;
	private String empEmailID;
	private String message;
	private String addOrView;
	LinkedList requestNames;
	private int approverPriority;
	private String searchAddress;
	private String searchText;
	private int startRequestCount;
	private int endRequestCount;
	private int totalRequestCount;
	private String location;
	private LinkedList locationId;
	private LinkedList locationName;
	private String selectedRequest;
	private String materialTypeId;
	private String materialGroupId;
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	private String codeCreator;
	private String design;	
	private String department;
	//desgnations//
	private LinkedList 	desgnIDList;
	private LinkedList 	desgnTXTList;
	
	
	//departments//

	private LinkedList 	departIDList;
	private LinkedList 	departTXTList;
	
	public LinkedList getRequestNames() {
		return requestNames;
	}

	public void setRequestNames(LinkedList requestNames) {
		this.requestNames = requestNames;
	}

	public String getEmpDesignation() {
		return empDesignation;
	}

	public void setEmpDesignation(String empDesignation) {
		this.empDesignation = empDesignation;
	}

	public String getEmpEmailID() {
		return empEmailID;
	}

	public void setEmpEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
	}

	public String[] getDesignation() {
		return designation;
	}

	public void setDesignation(String[] designation) {
		this.designation = designation;
	}

	public String[] getEmailID() {
		return emailID;
	}

	public void setEmailID(String[] emailID) {
		this.emailID = emailID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getApprID() {
		return apprID;
	}

	public void setApprID(String apprID) {
		this.apprID = apprID;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String[] getApproverID() {
		return approverID;
	}

	public void setApproverID(String[] approverID) {
		this.approverID = approverID;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	
	public String getAddOrView() {
		return addOrView;
	}

	public void setAddOrView(String addOrView) {
		this.addOrView = addOrView;
	}
	
	public int getApproverPriority() {
		return approverPriority;
	}

	public void setApproverPriority(int approverPriority) {
		this.approverPriority = approverPriority;
	}
	
	public String getSearchAddress() {
		return searchAddress;
	}
	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}
	
	public String getSearchText(){
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public int getStartRequestCount() {
		return startRequestCount;
	}
	public void setStartRequestCount(int startRequestCount) {
		this.startRequestCount = startRequestCount;
	}
	
	
	public int getEndRequestCount() {
		return endRequestCount;
	}
	public void setEndRequestCount(int endRequestCount) {
		this.endRequestCount = endRequestCount;
	}
	
	
	public int getTotalRequestCount() {
		return totalRequestCount;
	}
	public void setTotalRequestCount(int totalRequestCount) {
		this.totalRequestCount = totalRequestCount;
	}
	
	public LinkedList getLocationId() {
		return locationId;
	}
	public void setLocationId(LinkedList locId) {
		this.locationId = locId;
	}
	
	public LinkedList getLocationName() {
		return locationName;
	}
	public void setLocationName(LinkedList locName) {
		this.locationName = locName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getSelectedRequest() {
		return selectedRequest;
	}

	public void setSelectedRequest(String selectedRequest) {
		this.selectedRequest = selectedRequest;
	}
	
	public String getMaterialTypeId() {
		return materialTypeId;
	}
	public void setMaterialTypeId(String materialTypeId) {
		this.materialTypeId = materialTypeId;
	}
	public String getMaterialGroupId() {
		return materialGroupId;
	}
	public void setMaterialGroupId(String materialGroupId) {
		this.materialGroupId = materialGroupId;
	}
	public LinkedList getMaterTypeIDList() {
		return materTypeIDList;
	}
	public void setMaterTypeIDList(LinkedList materTypeIDList) {
		this.materTypeIDList = materTypeIDList;
	}
	public LinkedList getMaterialTypeIdValueList() {
		return materialTypeIdValueList;
	}
	public void setMaterialTypeIdValueList(LinkedList materialTypeIdValueList) {
		this.materialTypeIdValueList = materialTypeIdValueList;
	}
	
	public LinkedList getMaterGroupIDList() {
		return materGroupIDList;
	}
	public void setMaterGroupIDList(LinkedList materGroupIDList) {
		this.materGroupIDList = materGroupIDList;
	}
	public LinkedList getMaterialGroupIdValueList() {
		return materialGroupIdValueList;
	}
	public void setMaterialGroupIdValueList(LinkedList materialGroupIdValueList) {
		this.materialGroupIdValueList = materialGroupIdValueList;
	}
	
	public String getCodeCreator() {
		return codeCreator;
	}
	public void setCodeCreator(String codeCreator) {
		this.codeCreator = codeCreator;
	}
	
	public LinkedList getDepartIDList() {
		return departIDList;
	}
	public void setDepartIDList(LinkedList departIDList) {
		this.departIDList = departIDList;
	}
	public LinkedList getDepartTXTList() {
		return departTXTList;
	}
	public void setDepartTXTList(LinkedList departTXTList) {
		this.departTXTList = departTXTList;
	}
	public LinkedList getDesgnIDList() {
		return desgnIDList;
	}
	public void setDesgnIDList(LinkedList desgnIDList) {
		this.desgnIDList = desgnIDList;
	}
	public LinkedList getDesgnTXTList() {
		return desgnTXTList;
	}
	public void setDesgnTXTList(LinkedList desgnTXTList) {
		this.desgnTXTList = desgnTXTList;
	}
	public String getDesign() {
		return design;
	}
	public void setDesignation(String design) {
		this.design = design;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
}
