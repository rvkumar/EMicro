package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class PromotionalForm extends ActionForm{
	
	private int requestNo;
	private String requestDate;
	
	private String locationId;
	private String materialTypeId;
	private String storageLocationId;
	private String materialShortName;
	private String materialLongName;
	private String materialGroupId;
	private String unitOfMeasId;
	private String puchaseGroupId;
	private String approximateValue;
	private String valuationClass;
	private String detailedJustification;
	private String purposeID;
	private String isSASFormAvailable;
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCreationDate;
	private String requestedBy;
	private String sapCreatedBy;
	private String message;
	private String isAsset;

	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	private String approveType;
	private LinkedList divisonID;
	private LinkedList divisonName;
	
	private LinkedList counID;
	private LinkedList countryName;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private String materialCodeLists;
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	private LinkedList unitOfMeasIdList;
	private LinkedList unitOfMeasIdValues;
	private LinkedList storageID;
	private LinkedList storageIDName;
	private String message2;
	private LinkedList puchaseGroupIdList;
	private LinkedList puchaseGroupIdValues;
	
	private String materialCodeLists1;
	private String typeDetails;
	
	private LinkedList valuationClassID;
	private LinkedList valuationClassName;
	private String detailedSpecification;
	private String divisionId;
	private String appStatusMessage;
	
	
private String reqEmail;
	

private String brandID;
private String brandIDtxt;

private LinkedList brandIDList;
private LinkedList brandNameList;
private String hsnCode;
	
	
	
	public String getHsnCode() {
	return hsnCode;
}
public void setHsnCode(String hsnCode) {
	this.hsnCode = hsnCode;
}
	public String getBrandID() {
	return brandID;
}
public void setBrandID(String brandID) {
	this.brandID = brandID;
}
public String getBrandIDtxt() {
	return brandIDtxt;
}
public void setBrandIDtxt(String brandIDtxt) {
	this.brandIDtxt = brandIDtxt;
}
public LinkedList getBrandIDList() {
	return brandIDList;
}
public void setBrandIDList(LinkedList brandIDList) {
	this.brandIDList = brandIDList;
}
public LinkedList getBrandNameList() {
	return brandNameList;
}
public void setBrandNameList(LinkedList brandNameList) {
	this.brandNameList = brandNameList;
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

	public LinkedList getDivisonID() {
		return divisonID;
	}

	public void setDivisonID(LinkedList divisonID) {
		this.divisonID = divisonID;
	}

	public LinkedList getDivisonName() {
		return divisonName;
	}

	public void setDivisonName(LinkedList divisonName) {
		this.divisonName = divisonName;
	}

	public String getDivisionId() {
		return divisionId;
	}

	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}

	public String getIsAsset() {
		return isAsset;
	}

	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}

	public String getDetailedSpecification() {
		return detailedSpecification;
	}

	public void setDetailedSpecification(String detailedSpecification) {
		this.detailedSpecification = detailedSpecification;
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

	public LinkedList getPuchaseGroupIdList() {
		return puchaseGroupIdList;
	}

	public void setPuchaseGroupIdList(LinkedList puchaseGroupIdList) {
		this.puchaseGroupIdList = puchaseGroupIdList;
	}

	public LinkedList getPuchaseGroupIdValues() {
		return puchaseGroupIdValues;
	}

	public void setPuchaseGroupIdValues(LinkedList puchaseGroupIdValues) {
		this.puchaseGroupIdValues = puchaseGroupIdValues;
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

	public String getSapCreatedBy() {
		return sapCreatedBy;
	}

	public void setSapCreatedBy(String sapCreatedBy) {
		this.sapCreatedBy = sapCreatedBy;
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

	public String getPuchaseGroupId() {
		return puchaseGroupId;
	}

	public void setPuchaseGroupId(String puchaseGroupId) {
		this.puchaseGroupId = puchaseGroupId;
	}

	public String getApproximateValue() {
		return approximateValue;
	}

	public void setApproximateValue(String approximateValue) {
		this.approximateValue = approximateValue;
	}

	public String getValuationClass() {
		return valuationClass;
	}

	public void setValuationClass(String valuationClass) {
		this.valuationClass = valuationClass;
	}

	public String getDetailedJustification() {
		return detailedJustification;
	}

	public void setDetailedJustification(String detailedJustification) {
		this.detailedJustification = detailedJustification;
	}

	public String getPurposeID() {
		return purposeID;
	}

	public void setPurposeID(String purposeID) {
		this.purposeID = purposeID;
	}

	public String getIsSASFormAvailable() {
		return isSASFormAvailable;
	}

	public void setIsSASFormAvailable(String isSASFormAvailable) {
		this.isSASFormAvailable = isSASFormAvailable;
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

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
