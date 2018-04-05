package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class PackageMaterialMasterForm extends ActionForm{

	private int requestNo;
	private String requestDate;
	private String locationId;
	private String storageLocationId;
	private String materialTypeId;
	private String materialShortName;
	private String materialLongName;
	private String materialGroupId;
	private String pharmacopName;
	private String pharmacopGrade;
	private String genericName;
	private String synonym;
	private String pharmacopSpecification;
	private String isDMFMaterial;
	private String dmfGradeId;
	private String materialGrade;
	private String cosGradeNo;
	private String additionalTest;
	private String selectedType;
	private String existingItemCode;
	private String countryId;
	private String customerName;
	private String toBeUsedInProducts;
	private String isVendorSpecificMaterial;
	private String mfgrName;
	private String siteOfManufacture;
	private String shelfLife;
	private String dutyElement;
	private String tempCondition;
	private String storageCondition;
	private String retestDays;
	private String retestType;
	private String valuationClass;
	private String approximateValue;
	private String unitOfMeasId;
	private String puchaseGroupId;
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCreationDate;
	private String sapCreatedBy;
	private String requestedBy;
	private String message;
	private String message2;
	private String packageMaterialGroup;
	private String artworkNo;
	
	private String isArtworkRevision;
	private String  existingSAPItemCode;
	
	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	
	private String searchData;
	private String typeOfMaterial;
	FormFile fileNames;
	private String fileList;
	private String materialCodeLists;
	private String materialCodeLists1;
	private String approveType;
	
	private LinkedList counID;
	private LinkedList countryName;
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private LinkedList storageID;
	private LinkedList storageIDName;
	
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;

	private LinkedList unitOfMeasIdList;
	private LinkedList unitOfMeasIdValues;
	
	private LinkedList pharmacopGradeIDList;
	private LinkedList pharmacopGradeValueList;
	
	private LinkedList valuationClassID;
	private LinkedList valuationClassName;
	
	private LinkedList puchaseGroupIdList;
	private LinkedList puchaseGroupIdValues;
	
	private LinkedList dmfGradeIDList;
	private LinkedList dmfGradeIDValueList;
	
	private LinkedList packageGroupID;
	private LinkedList packageGroupIDValue;
	private LinkedList tempIDList;
	private LinkedList temValueList;
	private LinkedList storageIDList;
	private LinkedList storageLocList;
	
	private String typeDetails;
	
	private String filepath;
	private String uploadFileStatus;
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
	
	
	public String getAppStatusMessage() {
		return appStatusMessage;
	}
	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
	}
	public LinkedList getTempIDList() {
		return tempIDList;
	}
	public void setTempIDList(LinkedList tempIDList) {
		this.tempIDList = tempIDList;
	}
	public LinkedList getTemValueList() {
		return temValueList;
	}
	public void setTemValueList(LinkedList temValueList) {
		this.temValueList = temValueList;
	}
	public LinkedList getStorageIDList() {
		return storageIDList;
	}
	public void setStorageIDList(LinkedList storageIDList) {
		this.storageIDList = storageIDList;
	}
	public LinkedList getStorageLocList() {
		return storageLocList;
	}
	public void setStorageLocList(LinkedList storageLocList) {
		this.storageLocList = storageLocList;
	}
	public String getUploadFileStatus() {
		return uploadFileStatus;
	}
	public void setUploadFileStatus(String uploadFileStatus) {
		this.uploadFileStatus = uploadFileStatus;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
	public String getSelectedType() {
		return selectedType;
	}
	public void setSelectedType(String selectedType) {
		this.selectedType = selectedType;
	}
	public String getExistingItemCode() {
		return existingItemCode;
	}
	public void setExistingItemCode(String existingItemCode) {
		this.existingItemCode = existingItemCode;
	}
	public String getTempCondition() {
		return tempCondition;
	}
	public void setTempCondition(String tempCondition) {
		this.tempCondition = tempCondition;
	}
	public String getStorageCondition() {
		return storageCondition;
	}
	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}
	public String getRetestType() {
		return retestType;
	}
	public void setRetestType(String retestType) {
		this.retestType = retestType;
	}
	public String getTypeOfMaterial() {
		return typeOfMaterial;
	}
	public void setTypeOfMaterial(String typeOfMaterial) {
		this.typeOfMaterial = typeOfMaterial;
	}
	public LinkedList getPackageGroupID() {
		return packageGroupID;
	}
	public void setPackageGroupID(LinkedList packageGroupID) {
		this.packageGroupID = packageGroupID;
	}
	public LinkedList getPackageGroupIDValue() {
		return packageGroupIDValue;
	}
	public void setPackageGroupIDValue(LinkedList packageGroupIDValue) {
		this.packageGroupIDValue = packageGroupIDValue;
	}
	public LinkedList getDmfGradeIDList() {
		return dmfGradeIDList;
	}
	public void setDmfGradeIDList(LinkedList dmfGradeIDList) {
		this.dmfGradeIDList = dmfGradeIDList;
	}
	public LinkedList getDmfGradeIDValueList() {
		return dmfGradeIDValueList;
	}
	public void setDmfGradeIDValueList(LinkedList dmfGradeIDValueList) {
		this.dmfGradeIDValueList = dmfGradeIDValueList;
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
	public LinkedList getPharmacopGradeIDList() {
		return pharmacopGradeIDList;
	}
	public void setPharmacopGradeIDList(LinkedList pharmacopGradeIDList) {
		this.pharmacopGradeIDList = pharmacopGradeIDList;
	}
	public LinkedList getPharmacopGradeValueList() {
		return pharmacopGradeValueList;
	}
	public void setPharmacopGradeValueList(LinkedList pharmacopGradeValueList) {
		this.pharmacopGradeValueList = pharmacopGradeValueList;
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
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
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
	public String getSearchData() {
		return searchData;
	}
	public void setSearchData(String searchData) {
		this.searchData = searchData;
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
	public String getPackageMaterialGroup() {
		return packageMaterialGroup;
	}
	public void setPackageMaterialGroup(String packageMaterialGroup) {
		this.packageMaterialGroup = packageMaterialGroup;
	}
	public String getArtworkNo() {
		return artworkNo;
	}
	public void setArtworkNo(String artworkNo) {
		this.artworkNo = artworkNo;
	}
	public String getIsArtworkRevision() {
		return isArtworkRevision;
	}
	public void setIsArtworkRevision(String isArtworkRevision) {
		this.isArtworkRevision = isArtworkRevision;
	}
	public String getExistingSAPItemCode() {
		return existingSAPItemCode;
	}
	public void setExistingSAPItemCode(String existingSAPItemCode) {
		this.existingSAPItemCode = existingSAPItemCode;
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
	public String getStorageLocationId() {
		return storageLocationId;
	}
	public void setStorageLocationId(String storageLocationId) {
		this.storageLocationId = storageLocationId;
	}
	public String getMaterialTypeId() {
		return materialTypeId;
	}
	public void setMaterialTypeId(String materialTypeId) {
		this.materialTypeId = materialTypeId;
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
	public String getPharmacopName() {
		return pharmacopName;
	}
	public void setPharmacopName(String pharmacopName) {
		this.pharmacopName = pharmacopName;
	}
	public String getPharmacopGrade() {
		return pharmacopGrade;
	}
	public void setPharmacopGrade(String pharmacopGrade) {
		this.pharmacopGrade = pharmacopGrade;
	}
	public String getGenericName() {
		return genericName;
	}
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	public String getSynonym() {
		return synonym;
	}
	public void setSynonym(String synonym) {
		this.synonym = synonym;
	}
	public String getPharmacopSpecification() {
		return pharmacopSpecification;
	}
	public void setPharmacopSpecification(String pharmacopSpecification) {
		this.pharmacopSpecification = pharmacopSpecification;
	}
	public String getIsDMFMaterial() {
		return isDMFMaterial;
	}
	public void setIsDMFMaterial(String isDMFMaterial) {
		this.isDMFMaterial = isDMFMaterial;
	}
	public String getDmfGradeId() {
		return dmfGradeId;
	}
	public void setDmfGradeId(String dmfGradeId) {
		this.dmfGradeId = dmfGradeId;
	}
	public String getMaterialGrade() {
		return materialGrade;
	}
	public void setMaterialGrade(String materialGrade) {
		this.materialGrade = materialGrade;
	}
	public String getCosGradeNo() {
		return cosGradeNo;
	}
	public void setCosGradeNo(String cosGradeNo) {
		this.cosGradeNo = cosGradeNo;
	}
	public String getAdditionalTest() {
		return additionalTest;
	}
	public void setAdditionalTest(String additionalTest) {
		this.additionalTest = additionalTest;
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
	public String getToBeUsedInProducts() {
		return toBeUsedInProducts;
	}
	public void setToBeUsedInProducts(String toBeUsedInProducts) {
		this.toBeUsedInProducts = toBeUsedInProducts;
	}
	public String getIsVendorSpecificMaterial() {
		return isVendorSpecificMaterial;
	}
	public void setIsVendorSpecificMaterial(String isVendorSpecificMaterial) {
		this.isVendorSpecificMaterial = isVendorSpecificMaterial;
	}
	public String getMfgrName() {
		return mfgrName;
	}
	public void setMfgrName(String mfgrName) {
		this.mfgrName = mfgrName;
	}
	public String getSiteOfManufacture() {
		return siteOfManufacture;
	}
	public void setSiteOfManufacture(String siteOfManufacture) {
		this.siteOfManufacture = siteOfManufacture;
	}
	public String getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(String shelfLife) {
		this.shelfLife = shelfLife;
	}
	public String getDutyElement() {
		return dutyElement;
	}
	public void setDutyElement(String dutyElement) {
		this.dutyElement = dutyElement;
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
	public String getApproximateValue() {
		return approximateValue;
	}
	public void setApproximateValue(String approximateValue) {
		this.approximateValue = approximateValue;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	
	
	
}
