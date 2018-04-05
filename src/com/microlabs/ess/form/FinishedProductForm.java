package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class FinishedProductForm extends ActionForm{
	
	private int requestNo;
	private String requestDate;
	private String locationId;
	private String materialTypeId;
	private String manufacturedAt;
	private String matType;
	
	private String storageLocationId;
	private String materialShortName;
	private String materialLongName;
	private String materialGroupId;
	private String valuationClass;
	private String countryId;
	private String customerName;
	private String shelfLife;
	private String shelfType;
	private String standardBatchSize;
	private String batchCode;
	private String saleableOrSample;
	private String domesticOrExports;
	private String salesPackId;
	private String packTypeId;
	private String therapeuticSegmentID;
	private String divisionId;
	private String brandID;
	private String brandIDtxt;
	private String srengthId;
	private String prodInspMemo;

	private String grossWeight;
	
	private String netWeight;
	private String weightUOM;
	private String dimension;
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCreationDate;
	private String sapCreatedBy;
	private String requestedBy;
	private String unitOfMeasId;
	private String puchaseGroupId;
	private String message;
	private String genericName;
	private String message2;
	private String materialCodeLists1;
	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	private LinkedList counID;
	private LinkedList countryName;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();

	
	
	FormFile fileNames;
	private String fileList;
	private String materialCodeLists;
	private String approveType;

	private String salesUnitOfMeaseurement;
	private String typeDetails;
	
	
	
	
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	private LinkedList unitOfMeasIdList;
	private LinkedList unitOfMeasIdValues;
	private LinkedList storageID;
	private LinkedList storageIDName;
	private LinkedList packSizeID;
	private LinkedList packSizeName;
	
	private LinkedList packTypeID;
	private LinkedList packTypeName;
	private LinkedList salesUOMID;
	private LinkedList salesUOMName;
	private LinkedList divisonID;
	private LinkedList divisonName;
	private LinkedList therapeuticID;
	private LinkedList therapeuticName;
	private LinkedList brandIDList;
	private LinkedList brandNameList;
	private LinkedList strengthIDList;
	private LinkedList strengthNameList;
	private LinkedList genericIDList;
	private LinkedList genericNameList;
	
	private LinkedList weightUOMID;
	private LinkedList weightUOMName;
	private LinkedList puchaseGroupIdList;
	private LinkedList puchaseGroupIdValues;
	
	private LinkedList valuationClassID;
	private LinkedList valuationClassName;
	
	private String taxClassification;
	private String materialPricing;
	private String appStatusMessage;
	
    
	
	
private String reqEmail;
	
	
private String storage;

private String hsnCode;




public String getHsnCode() {
	return hsnCode;
}
public void setHsnCode(String hsnCode) {
	this.hsnCode = hsnCode;
}
public String getStorage() {
		return storage;
	}
	public void setStorage(String storage) {
		this.storage = storage;
	}
	
	public String getReqEmail() {
		return reqEmail;
	}
	public void setReqEmail(String reqEmail) {
		this.reqEmail = reqEmail;
	}
	
	
	
	public String getBrandIDtxt() {
		return brandIDtxt;
	}
	public void setBrandIDtxt(String brandIDtxt) {
		this.brandIDtxt = brandIDtxt;
	}
	public String getAppStatusMessage() {
		return appStatusMessage;
	}
	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getTaxClassification() {
		return taxClassification;
	}
	public void setTaxClassification(String taxClassification) {
		this.taxClassification = taxClassification;
	}
	public String getMaterialPricing() {
		return materialPricing;
	}
	public void setMaterialPricing(String materialPricing) {
		this.materialPricing = materialPricing;
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
	public LinkedList getPackTypeID() {
		return packTypeID;
	}
	public void setPackTypeID(LinkedList packTypeID) {
		this.packTypeID = packTypeID;
	}
	public LinkedList getPackTypeName() {
		return packTypeName;
	}
	public void setPackTypeName(LinkedList packTypeName) {
		this.packTypeName = packTypeName;
	}
	public LinkedList getSalesUOMID() {
		return salesUOMID;
	}
	public void setSalesUOMID(LinkedList salesUOMID) {
		this.salesUOMID = salesUOMID;
	}
	public LinkedList getSalesUOMName() {
		return salesUOMName;
	}
	public void setSalesUOMName(LinkedList salesUOMName) {
		this.salesUOMName = salesUOMName;
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
	public LinkedList getTherapeuticID() {
		return therapeuticID;
	}
	public void setTherapeuticID(LinkedList therapeuticID) {
		this.therapeuticID = therapeuticID;
	}
	public LinkedList getTherapeuticName() {
		return therapeuticName;
	}
	public void setTherapeuticName(LinkedList therapeuticName) {
		this.therapeuticName = therapeuticName;
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
	public LinkedList getStrengthIDList() {
		return strengthIDList;
	}
	public void setStrengthIDList(LinkedList strengthIDList) {
		this.strengthIDList = strengthIDList;
	}
	public LinkedList getStrengthNameList() {
		return strengthNameList;
	}
	public void setStrengthNameList(LinkedList strengthNameList) {
		this.strengthNameList = strengthNameList;
	}
	public LinkedList getGenericIDList() {
		return genericIDList;
	}
	public void setGenericIDList(LinkedList genericIDList) {
		this.genericIDList = genericIDList;
	}
	public LinkedList getGenericNameList() {
		return genericNameList;
	}
	public void setGenericNameList(LinkedList genericNameList) {
		this.genericNameList = genericNameList;
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
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public String getGenericName() {
		return genericName;
	}
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
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
	public String getSalesUnitOfMeaseurement() {
		return salesUnitOfMeaseurement;
	}
	public void setSalesUnitOfMeaseurement(String salesUnitOfMeaseurement) {
		this.salesUnitOfMeaseurement = salesUnitOfMeaseurement;
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
	

	public String getManufacturedAt() {
		return manufacturedAt;
	}
	public void setManufacturedAt(String manufacturedAt) {
		this.manufacturedAt = manufacturedAt;
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
	public String getValuationClass() {
		return valuationClass;
	}
	public void setValuationClass(String valuationClass) {
		this.valuationClass = valuationClass;
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
	public String getSaleableOrSample() {
		return saleableOrSample;
	}
	public void setSaleableOrSample(String saleableOrSample) {
		this.saleableOrSample = saleableOrSample;
	}
	public String getDomesticOrExports() {
		return domesticOrExports;
	}
	public void setDomesticOrExports(String domesticOrExports) {
		this.domesticOrExports = domesticOrExports;
	}
	public String getSalesPackId() {
		return salesPackId;
	}
	public void setSalesPackId(String salesPackId) {
		this.salesPackId = salesPackId;
	}
	public String getPackTypeId() {
		return packTypeId;
	}
	public void setPackTypeId(String packTypeId) {
		this.packTypeId = packTypeId;
	}
	public String getTherapeuticSegmentID() {
		return therapeuticSegmentID;
	}
	public void setTherapeuticSegmentID(String therapeuticSegmentID) {
		this.therapeuticSegmentID = therapeuticSegmentID;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public String getBrandID() {
		return brandID;
	}
	public void setBrandID(String brandID) {
		this.brandID = brandID;
	}
	public String getSrengthId() {
		return srengthId;
	}
	public void setSrengthId(String srengthId) {
		this.srengthId = srengthId;
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
	public String getDimension() {
		return dimension;
	}
	public void setDimension(String dimension) {
		this.dimension = dimension;
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
	public FormFile getFileNames() {
		return fileNames;
	}
	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
	}
	public String getFileList() {
		return fileList;
	}
	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
