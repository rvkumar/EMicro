package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class SemiFinishedForm extends ActionForm{

	private int requestNo;
	private String requestDate;
	private String locationId;
	private String materialTypeId;
	private String storageLocationId;
	private String materialShortName;
	private String materialLongName;
	private String materialGroupId;
	private String unitOfMeasId;
	private String countryId;
	private String customerName;
	private String shelfLife;
	private String shelfType;
	private String standardBatchSize;
	private String batchCode;
	private String targetWeight;
	private String prodInspMemo;
	private String grossWeight;
	private String netWeight;
	private String weightUOM;
	private String retestDays;
	private String valuationClass;
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCreationDate;
	private String sapCreatedBy;
	private String requestedBy;
	private String message;
	private String approveType;
	private LinkedList counID;
	private LinkedList countryName;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private LinkedList packSizeID;
	private LinkedList packSizeName;
	
	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	private String message2;
	
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	private LinkedList unitOfMeasIdList;
	private LinkedList unitOfMeasIdValues;
	private LinkedList storageID;
	private LinkedList storageIDName;
	
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	
	private LinkedList valuationClassID;
	private LinkedList valuationClassName;
	private LinkedList weightUOMID;
	private LinkedList weightUOMName;
	
	private String materialCodeLists;
	private String materialCodeLists1;
	private String typeDetails;
	private String packSize;
	private String retestType;
	private String appStatusMessage;
	
	
private String reqEmail;
private String hsn_Code;
	
	
	
	
	public String getHsn_Code() {
	return hsn_Code;
}
public void setHsn_Code(String hsn_Code) {
	this.hsn_Code = hsn_Code;
}
	public String getReqEmail() {
		return reqEmail;
	}
	public void setReqEmail(String reqEmail) {
		this.reqEmail = reqEmail;
	}
	
	
	public String getAppStatusMessage() {
		return appStatusMessage;
	}
	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
	}
	public LinkedList getPackSizeID() {
		return packSizeID;
	}
	public void setPackSizeID(LinkedList packSizeID) {
		this.packSizeID = packSizeID;
	}
	public LinkedList getPackSizeName() {
		return packSizeName;
	}
	public void setPackSizeName(LinkedList packSizeName) {
		this.packSizeName = packSizeName;
	}
	public String getRetestType() {
		return retestType;
	}
	public void setRetestType(String retestType) {
		this.retestType = retestType;
	}
	public LinkedList getWeightUOMID() {
		return weightUOMID;
	}
	public void setWeightUOMID(LinkedList weightUOMID) {
		this.weightUOMID = weightUOMID;
	}
	public LinkedList getWeightUOMName() {
		return weightUOMName;
	}
	public void setWeightUOMName(LinkedList weightUOMName) {
		this.weightUOMName = weightUOMName;
	}
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public LinkedList getValuationClassID() {
		return valuationClassID;
	}
	public void setValuationClassID(LinkedList valuationClassID) {
		this.valuationClassID = valuationClassID;
	}
	public LinkedList getValuationClassName() {
		return valuationClassName;
	}
	public void setValuationClassName(LinkedList valuationClassName) {
		this.valuationClassName = valuationClassName;
	}
	public String getTypeDetails() {
		return typeDetails;
	}
	public void setTypeDetails(String typeDetails) {
		this.typeDetails = typeDetails;
	}
	public String getMaterialCodeLists1() {
		return materialCodeLists1;
	}
	public void setMaterialCodeLists1(String materialCodeLists1) {
		this.materialCodeLists1 = materialCodeLists1;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
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
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}
	public String getMaterialCodeLists() {
		return materialCodeLists;
	}
	public void setMaterialCodeLists(String materialCodeLists) {
		this.materialCodeLists = materialCodeLists;
	}
	public String getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getRequestsearchDate() {
		return requestsearchDate;
	}
	public void setRequestsearchDate(String requestsearchDate) {
		this.requestsearchDate = requestsearchDate;
	}
	public String getLocationSearch() {
		return locationSearch;
	}
	public void setLocationSearch(String locationSearch) {
		this.locationSearch = locationSearch;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public LinkedList getCounID() {
		return counID;
	}
	public void setCounID(LinkedList counID) {
		this.counID = counID;
	}
	public LinkedList getCountryName() {
		return countryName;
	}
	public void setCountryName(LinkedList countryName) {
		this.countryName = countryName;
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
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getMaterialTypeId() {
		return materialTypeId;
	}
	public void setMaterialTypeId(String materialTypeId) {
		this.materialTypeId = materialTypeId;
	}
	public String getStorageLocationId() {
		return storageLocationId;
	}
	public void setStorageLocationId(String storageLocationId) {
		this.storageLocationId = storageLocationId;
	}
	public String getMaterialShortName() {
		return materialShortName;
	}
	public void setMaterialShortName(String materialShortName) {
		this.materialShortName = materialShortName;
	}
	public String getMaterialLongName() {
		return materialLongName;
	}
	public void setMaterialLongName(String materialLongName) {
		this.materialLongName = materialLongName;
	}
	public String getMaterialGroupId() {
		return materialGroupId;
	}
	public void setMaterialGroupId(String materialGroupId) {
		this.materialGroupId = materialGroupId;
	}
	public String getUnitOfMeasId() {
		return unitOfMeasId;
	}
	public void setUnitOfMeasId(String unitOfMeasId) {
		this.unitOfMeasId = unitOfMeasId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}
	public String getStandardBatchSize() {
		return standardBatchSize;
	}
	public void setStandardBatchSize(String standardBatchSize) {
		this.standardBatchSize = standardBatchSize;
	}
	public String getBatchCode() {
		return batchCode;
	}
	public void setBatchCode(String batchCode) {
		this.batchCode = batchCode;
	}
	public String getTargetWeight() {
		return targetWeight;
	}
	public void setTargetWeight(String targetWeight) {
		this.targetWeight = targetWeight;
	}
	public String getProdInspMemo() {
		return prodInspMemo;
	}
	public void setProdInspMemo(String prodInspMemo) {
		this.prodInspMemo = prodInspMemo;
	}
	public String getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}
	public String getNetWeight() {
		return netWeight;
	}
	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}
	public String getWeightUOM() {
		return weightUOM;
	}
	public void setWeightUOM(String weightUOM) {
		this.weightUOM = weightUOM;
	}
	public String getRetestDays() {
		return retestDays;
	}
	public void setRetestDays(String retestDays) {
		this.retestDays = retestDays;
	}
	public String getValuationClass() {
		return valuationClass;
	}
	public void setValuationClass(String valuationClass) {
		this.valuationClass = valuationClass;
	}
	public String getSapCodeNo() {
		return sapCodeNo;
	}
	public void setSapCodeNo(String sapCodeNo) {
		this.sapCodeNo = sapCodeNo;
	}
	public String getSapCodeExists() {
		return sapCodeExists;
	}
	public void setSapCodeExists(String sapCodeExists) {
		this.sapCodeExists = sapCodeExists;
	}
	public String getSapCreationDate() {
		return sapCreationDate;
	}
	public void setSapCreationDate(String sapCreationDate) {
		this.sapCreationDate = sapCreationDate;
	}
	public String getSapCreatedBy() {
		return sapCreatedBy;
	}
	public void setSapCreatedBy(String sapCreatedBy) {
		this.sapCreatedBy = sapCreatedBy;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
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
	public LinkedList getUnitOfMeasIdList() {
		return unitOfMeasIdList;
	}
	public void setUnitOfMeasIdList(LinkedList unitOfMeasIdList) {
		this.unitOfMeasIdList = unitOfMeasIdList;
	}
	public LinkedList getUnitOfMeasIdValues() {
		return unitOfMeasIdValues;
	}
	public void setUnitOfMeasIdValues(LinkedList unitOfMeasIdValues) {
		this.unitOfMeasIdValues = unitOfMeasIdValues;
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
	
	
	
	
	
	
}