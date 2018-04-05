package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class MaterialCodeExtensionForm extends ActionForm{

	private int requestNo;
	private String requestDate;
	private String plantType;
	private String storageLocationType;
	private String plant1;
	private String materialCode1;
	private String extendToPlant1;
	private String storageLocationId1;
	private String extendToStorageLocation1;

	private String plant2;
	private String materialCode2;
	private String fromStorageLocation;
	private String toStorageLocation;
	private String actionType;
	
	private String massage1;
	private String massage2;
	private String approveType;
	private String[] requiredRequestNumber;
	private String[] getReqno;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String appStatusMessage;
	
	
	
	
	
	public String getAppStatusMessage() {
		return appStatusMessage;
	}
	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
	}
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private ArrayList locationIdList1=new ArrayList();
	private ArrayList locationLabelList1=new ArrayList();
	private LinkedList storageID;
	private LinkedList storageIDName;
	
	private String message;
	private String description;
	

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}
	public String getMassage1() {
		return massage1;
	}
	public void setMassage1(String massage1) {
		this.massage1 = massage1;
	}
	public String getMassage2() {
		return massage2;
	}
	public void setMassage2(String massage2) {
		this.massage2 = massage2;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public LinkedList getStorageID() {
		return storageID;
	}
	public void setStorageID(LinkedList storageID) {
		this.storageID = storageID;
	}
	public LinkedList getStorageIDName() {
		return storageIDName;
	}
	public void setStorageIDName(LinkedList storageIDName) {
		this.storageIDName = storageIDName;
	}
	public String getMaterialCode1() {
		return materialCode1;
	}
	public void setMaterialCode1(String materialCode1) {
		this.materialCode1 = materialCode1;
	}
	public String getExtendToPlant1() {
		return extendToPlant1;
	}
	public void setExtendToPlant1(String extendToPlant1) {
		this.extendToPlant1 = extendToPlant1;
	}
	public String getStorageLocationId1() {
		return storageLocationId1;
	}
	public void setStorageLocationId1(String storageLocationId1) {
		this.storageLocationId1 = storageLocationId1;
	}
	public String getExtendToStorageLocation1() {
		return extendToStorageLocation1;
	}
	public void setExtendToStorageLocation1(String extendToStorageLocation1) {
		this.extendToStorageLocation1 = extendToStorageLocation1;
	}
	public String getPlant1() {
		return plant1;
	}
	public void setPlant1(String plant1) {
		this.plant1 = plant1;
	}
	public String getPlantType() {
		return plantType;
	}
	public void setPlantType(String plantType) {
		this.plantType = plantType;
	}
	public String getStorageLocationType() {
		return storageLocationType;
	}
	public void setStorageLocationType(String storageLocationType) {
		this.storageLocationType = storageLocationType;
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
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getPlant2() {
		return plant2;
	}
	public void setPlant2(String plant2) {
		this.plant2 = plant2;
	}
	public String getMaterialCode2() {
		return materialCode2;
	}
	public void setMaterialCode2(String materialCode2) {
		this.materialCode2 = materialCode2;
	}
	public String getFromStorageLocation() {
		return fromStorageLocation;
	}
	public void setFromStorageLocation(String fromStorageLocation) {
		this.fromStorageLocation = fromStorageLocation;
	}
	public String getToStorageLocation() {
		return toStorageLocation;
	}
	public void setToStorageLocation(String toStorageLocation) {
		this.toStorageLocation = toStorageLocation;
	}
	public String[] getRequiredRequestNumber() {
		return requiredRequestNumber;
	}
	public void setRequiredRequestNumber(String[] requiredRequestNumber) {
		this.requiredRequestNumber = requiredRequestNumber;
	}
	public String[] getGetReqno() {
		return getReqno;
	}
	public void setGetReqno(String[] getReqno) {
		this.getReqno = getReqno;
	}
	public ArrayList getLocationIdList1() {
		return locationIdList1;
	}
	public void setLocationIdList1(ArrayList locationIdList1) {
		this.locationIdList1 = locationIdList1;
	}
	public ArrayList getLocationLabelList1() {
		return locationLabelList1;
	}
	public void setLocationLabelList1(ArrayList locationLabelList1) {
		this.locationLabelList1 = locationLabelList1;
	}
	
	
	
	
}
