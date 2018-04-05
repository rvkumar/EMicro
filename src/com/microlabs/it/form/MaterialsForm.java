package com.microlabs.it.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class MaterialsForm extends ActionForm{

	private String masterType;
	
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private String status;
	private String message;
	private String message2;
	private String message3;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String reqRequstType;
	private String selectedFilter;
	private String materialCodeLists;
	private String[] selectedRequestNo;
	private String[] itemCode;
	private String[] itemExist;
	private String[] itemExistNO;
	
	private String hsnCode;
	private String testName;
	private String reqReqNo;
	private String reqItemNo;
	private String reqItemExist;
	private int autoNo;
	
	
	//ZCON default propertys
	
	private String SALES_VIEW;
	private String MBRSH;
	private String MTPOS_MARA;
	private String BRGEW;
	private String GEWEI;
	private String NTGEW;
	private String FERTH;
	private String XCHPF;
	private String TRAGR;

	private String EKWSL;
	private String TEMPB;
	private String RAUBE;
	private String IPRKZ;
	private String MHDHB;
	private String RDMHD;
	private String SKTOF;
	private String VERSG;
	private String KONDM;
	private String KTGRM;
	private String MTPOS;
	private String MVGR1;
	private String MVGR2;
	private String MVGR3;
	private String MVGR4;
	private String MVGR5;
	private String MTVFP;
	private String LADGR;
	private String WEBAZ;
	private String DISMM;
	private String DISLS;
	private String	BESKZ;
	private String	KZECH;
	private String	RGEKZ;
	private String	PLIFZ;
	private String	EISBE;
	private String	PERKZ;
	private String	SBDKZ;
	private String	LOSGR;
	private String	INSMK;
	private String	PEINH;
	private String	STPRS;
	private String	MTART;
	private String	materialDesc;
	private String	MEINS;
	private String	MATKL;
	private String	EXTWG;
	private String	SPART;
	private String	LGORT;
	private String	VKORG;
	private String	VTWEG;
	private String	VRKME;
	private String	DWERK;
	private String	WERKS;
	private String	PRCTR;
	private String	EKGRP;
	private String	LGPRO;
	private String	LGFSB;
	private String	BKLAS;
	private String	S_TDLINE;
	private String	P_TDLINE;
	private String	ZDESC;
	private String	BEGRU;
	private String	QMATA;
	
//roh
	
	private LinkedList storageID;
	private LinkedList storageIDName;
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	private LinkedList unitOfMeasIdList;
	private LinkedList unitOfMeasIdValues;
	private LinkedList pharmacopGradeIDList;
	private LinkedList pharmacopGradeValueList;
	private LinkedList valuationClassID;
	private LinkedList valuationClassName;
	private LinkedList dmfGradeIDList;
	private LinkedList dmfGradeIDValueList;
	private LinkedList counID;
	private LinkedList countryName;
	private LinkedList puchaseGroupIdList;
	private LinkedList puchaseGroupIdValues;
	private LinkedList sapCodeNoIdList;
	private LinkedList sapCodeNoIdValues;
	private LinkedList tempIDList;
	private LinkedList temValueList;
	private LinkedList storageIDList;
	private LinkedList storageLocList;
	private LinkedList deptId;
	private LinkedList deptName;
	
	private String requestNo;
	private String requestDate;
	private String requestType;
	private String reqMaterialGroup;
	private String requestedBy;
	private String header;
	
	private String jobTitle;
	private String department;
	private String primaryLocation;
	
	private String remark;
	
	private String jobDescription;
	private String empStatus;
	private String totalEmp;
	private String qualifications;
	private String experience;
	private String saleryOffered;
	private String reqStatus;
	private String appMessage;
	
	private int totalAppCount;
	private int startAppCount;
	private int endAppCount;
	private LinkedList filterType;
	private String fileNameList;
	
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCodeExistsNo;
	private String sapCreationDate;
	private String sapCreatedBy;
	

			
	private String heading;
	private String searchText;
	private String method;
	

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
	private String shelfLifeType;
	private String retestType;
	
	private String dmfGradeId;
	private String materialGrade;
	private String cosGradeNo;
	private String additionalTest;
	private String countryId;
	private String customerName;
	private String toBeUsedInProducts;
	private String isVendorSpecificMaterial;
	private String mfgrName;
	private String siteOfManufacture;
	private String tempCondition;
	private String storageCondition;
	private String shelfLife;
	private String dutyElement;
	private String retestDays;
	private String valuationClass;
	private String approximateValue;
	private String unitOfMeasId;
	private String puchaseGroupId;
	
	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	
	private String comments;
	private String userRole;
	
	//Finished Product
	private String manufacturedAt;
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
	private String srengthId;
	private String prodInspMemo;
	private String grossWeight;
	private String netWeight;
	private String weightUOM;
	private String dimension;
	private String salesUnitOfMeaseurement;
	private String typeDetails;
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
	private String taxClassification;
	private String materialPricing;
	
	
	//VERP
	private String packageMaterialGroup;
	private String typeOfMaterial;
	
	private String artworkNo;
	
	private String isArtworkRevision;
	private String  existingSAPItemCode;
	private LinkedList packageGroupID;
	private LinkedList packageGroupIDValue;
	
	
	//semifinished
	
	private String packSize;
	private String targetWeight;
	
	//promotional
	private String isSASFormAvailable;
	private String isAsset;
	
	
	//ZPSR
	
	private String moc;
	private String rating;
	private String range;
	private String materialUsedIn;
	private String isEquipment;
	private String isSpare;
	private String isNewEquipment;
	private String isItNewFurniture;
	private String isItFacility;
	private String isSpareNewEquipment;
	
	
	
	private String equipmentName;
	private String prNumber;
	private String poNumber;
	private String utilizingDept;
	private String detailedJustification;
	private String detailedSpecification;
	private String purposeID;

	private String equipmentMake;
	private String componentMake;
	private String oemPartNo;
	
	
	private String materialGrup;
	
	private String fromDate;
	private String toDate;
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private String username;
	
	private String password;
	
	
	private String equipIntendedFor;
	
	
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
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
	public LinkedList getSapCodeNoIdList() {
		return sapCodeNoIdList;
	}
	public void setSapCodeNoIdList(LinkedList sapCodeNoIdList) {
		this.sapCodeNoIdList = sapCodeNoIdList;
	}
	public LinkedList getSapCodeNoIdValues() {
		return sapCodeNoIdValues;
	}
	public void setSapCodeNoIdValues(LinkedList sapCodeNoIdValues) {
		this.sapCodeNoIdValues = sapCodeNoIdValues;
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
	public LinkedList getDeptId() {
		return deptId;
	}
	public void setDeptId(LinkedList deptId) {
		this.deptId = deptId;
	}
	public LinkedList getDeptName() {
		return deptName;
	}
	public void setDeptName(LinkedList deptName) {
		this.deptName = deptName;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getReqMaterialGroup() {
		return reqMaterialGroup;
	}
	public void setReqMaterialGroup(String reqMaterialGroup) {
		this.reqMaterialGroup = reqMaterialGroup;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPrimaryLocation() {
		return primaryLocation;
	}
	public void setPrimaryLocation(String primaryLocation) {
		this.primaryLocation = primaryLocation;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public String getTotalEmp() {
		return totalEmp;
	}
	public void setTotalEmp(String totalEmp) {
		this.totalEmp = totalEmp;
	}
	public String getQualifications() {
		return qualifications;
	}
	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getSaleryOffered() {
		return saleryOffered;
	}
	public void setSaleryOffered(String saleryOffered) {
		this.saleryOffered = saleryOffered;
	}
	public String getReqStatus() {
		return reqStatus;
	}
	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}
	public String getAppMessage() {
		return appMessage;
	}
	public void setAppMessage(String appMessage) {
		this.appMessage = appMessage;
	}
	public int getTotalAppCount() {
		return totalAppCount;
	}
	public void setTotalAppCount(int totalAppCount) {
		this.totalAppCount = totalAppCount;
	}
	public int getStartAppCount() {
		return startAppCount;
	}
	public void setStartAppCount(int startAppCount) {
		this.startAppCount = startAppCount;
	}
	public int getEndAppCount() {
		return endAppCount;
	}
	public void setEndAppCount(int endAppCount) {
		this.endAppCount = endAppCount;
	}
	public LinkedList getFilterType() {
		return filterType;
	}
	public void setFilterType(LinkedList filterType) {
		this.filterType = filterType;
	}
	public String getFileNameList() {
		return fileNameList;
	}
	public void setFileNameList(String fileNameList) {
		this.fileNameList = fileNameList;
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
	public String getSapCodeExistsNo() {
		return sapCodeExistsNo;
	}
	public void setSapCodeExistsNo(String sapCodeExistsNo) {
		this.sapCodeExistsNo = sapCodeExistsNo;
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
	public String getHeading() {
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
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
	public String getShelfLifeType() {
		return shelfLifeType;
	}
	public void setShelfLifeType(String shelfLifeType) {
		this.shelfLifeType = shelfLifeType;
	}
	public String getRetestType() {
		return retestType;
	}
	public void setRetestType(String retestType) {
		this.retestType = retestType;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getManufacturedAt() {
		return manufacturedAt;
	}
	public void setManufacturedAt(String manufacturedAt) {
		this.manufacturedAt = manufacturedAt;
	}
	public String getShelfType() {
		return shelfType;
	}
	public void setShelfType(String shelfType) {
		this.shelfType = shelfType;
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
	public String getSalesUnitOfMeaseurement() {
		return salesUnitOfMeaseurement;
	}
	public void setSalesUnitOfMeaseurement(String salesUnitOfMeaseurement) {
		this.salesUnitOfMeaseurement = salesUnitOfMeaseurement;
	}
	public String getTypeDetails() {
		return typeDetails;
	}
	public void setTypeDetails(String typeDetails) {
		this.typeDetails = typeDetails;
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
	public String getPackageMaterialGroup() {
		return packageMaterialGroup;
	}
	public void setPackageMaterialGroup(String packageMaterialGroup) {
		this.packageMaterialGroup = packageMaterialGroup;
	}
	public String getTypeOfMaterial() {
		return typeOfMaterial;
	}
	public void setTypeOfMaterial(String typeOfMaterial) {
		this.typeOfMaterial = typeOfMaterial;
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
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public String getTargetWeight() {
		return targetWeight;
	}
	public void setTargetWeight(String targetWeight) {
		this.targetWeight = targetWeight;
	}
	public String getIsSASFormAvailable() {
		return isSASFormAvailable;
	}
	public void setIsSASFormAvailable(String isSASFormAvailable) {
		this.isSASFormAvailable = isSASFormAvailable;
	}
	public String getIsAsset() {
		return isAsset;
	}
	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}
	public String getMoc() {
		return moc;
	}
	public void setMoc(String moc) {
		this.moc = moc;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getMaterialUsedIn() {
		return materialUsedIn;
	}
	public void setMaterialUsedIn(String materialUsedIn) {
		this.materialUsedIn = materialUsedIn;
	}
	public String getIsEquipment() {
		return isEquipment;
	}
	public void setIsEquipment(String isEquipment) {
		this.isEquipment = isEquipment;
	}
	public String getIsSpare() {
		return isSpare;
	}
	public void setIsSpare(String isSpare) {
		this.isSpare = isSpare;
	}
	public String getIsNewEquipment() {
		return isNewEquipment;
	}
	public void setIsNewEquipment(String isNewEquipment) {
		this.isNewEquipment = isNewEquipment;
	}
	public String getIsItNewFurniture() {
		return isItNewFurniture;
	}
	public void setIsItNewFurniture(String isItNewFurniture) {
		this.isItNewFurniture = isItNewFurniture;
	}
	public String getIsItFacility() {
		return isItFacility;
	}
	public void setIsItFacility(String isItFacility) {
		this.isItFacility = isItFacility;
	}
	public String getIsSpareNewEquipment() {
		return isSpareNewEquipment;
	}
	public void setIsSpareNewEquipment(String isSpareNewEquipment) {
		this.isSpareNewEquipment = isSpareNewEquipment;
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getUtilizingDept() {
		return utilizingDept;
	}
	public void setUtilizingDept(String utilizingDept) {
		this.utilizingDept = utilizingDept;
	}
	public String getDetailedJustification() {
		return detailedJustification;
	}
	public void setDetailedJustification(String detailedJustification) {
		this.detailedJustification = detailedJustification;
	}
	public String getDetailedSpecification() {
		return detailedSpecification;
	}
	public void setDetailedSpecification(String detailedSpecification) {
		this.detailedSpecification = detailedSpecification;
	}
	public String getPurposeID() {
		return purposeID;
	}
	public void setPurposeID(String purposeID) {
		this.purposeID = purposeID;
	}
	public String getEquipmentMake() {
		return equipmentMake;
	}
	public void setEquipmentMake(String equipmentMake) {
		this.equipmentMake = equipmentMake;
	}
	public String getComponentMake() {
		return componentMake;
	}
	public void setComponentMake(String componentMake) {
		this.componentMake = componentMake;
	}
	public String getOemPartNo() {
		return oemPartNo;
	}
	public void setOemPartNo(String oemPartNo) {
		this.oemPartNo = oemPartNo;
	}
	public int getAutoNo() {
		return autoNo;
	}
	public void setAutoNo(int autoNo) {
		this.autoNo = autoNo;
	}
	public String getMTART() {
		return MTART;
	}
	public void setMTART(String mtart) {
		MTART = mtart;
	}
	public String getMaterialDesc() {
		return materialDesc;
	}
	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}
	public String getMEINS() {
		return MEINS;
	}
	public void setMEINS(String meins) {
		MEINS = meins;
	}
	public String getMATKL() {
		return MATKL;
	}
	public void setMATKL(String matkl) {
		MATKL = matkl;
	}
	public String getEXTWG() {
		return EXTWG;
	}
	public void setEXTWG(String extwg) {
		EXTWG = extwg;
	}
	public String getSPART() {
		return SPART;
	}
	public void setSPART(String spart) {
		SPART = spart;
	}
	public String getLGORT() {
		return LGORT;
	}
	public void setLGORT(String lgort) {
		LGORT = lgort;
	}
	public String getVKORG() {
		return VKORG;
	}
	public void setVKORG(String vkorg) {
		VKORG = vkorg;
	}
	public String getVTWEG() {
		return VTWEG;
	}
	public void setVTWEG(String vtweg) {
		VTWEG = vtweg;
	}
	public String getVRKME() {
		return VRKME;
	}
	public void setVRKME(String vrkme) {
		VRKME = vrkme;
	}
	public String getDWERK() {
		return DWERK;
	}
	public void setDWERK(String dwerk) {
		DWERK = dwerk;
	}
	public String getWERKS() {
		return WERKS;
	}
	public void setWERKS(String werks) {
		WERKS = werks;
	}
	public String getPRCTR() {
		return PRCTR;
	}
	public void setPRCTR(String prctr) {
		PRCTR = prctr;
	}
	public String getEKGRP() {
		return EKGRP;
	}
	public void setEKGRP(String ekgrp) {
		EKGRP = ekgrp;
	}
	public String getLGPRO() {
		return LGPRO;
	}
	public void setLGPRO(String lgpro) {
		LGPRO = lgpro;
	}
	public String getLGFSB() {
		return LGFSB;
	}
	public void setLGFSB(String lgfsb) {
		LGFSB = lgfsb;
	}
	public String getBKLAS() {
		return BKLAS;
	}
	public void setBKLAS(String bklas) {
		BKLAS = bklas;
	}
	public String getS_TDLINE() {
		return S_TDLINE;
	}
	public void setS_TDLINE(String s_tdline) {
		S_TDLINE = s_tdline;
	}
	public String getP_TDLINE() {
		return P_TDLINE;
	}
	public void setP_TDLINE(String p_tdline) {
		P_TDLINE = p_tdline;
	}
	public String getZDESC() {
		return ZDESC;
	}
	public void setZDESC(String zdesc) {
		ZDESC = zdesc;
	}
	public String getBEGRU() {
		return BEGRU;
	}
	public void setBEGRU(String begru) {
		BEGRU = begru;
	}
	public String getQMATA() {
		return QMATA;
	}
	public void setQMATA(String qmata) {
		QMATA = qmata;
	}
	public String getSALES_VIEW() {
		return SALES_VIEW;
	}
	public void setSALES_VIEW(String sales_view) {
		SALES_VIEW = sales_view;
	}
	public String getMBRSH() {
		return MBRSH;
	}
	public void setMBRSH(String mbrsh) {
		MBRSH = mbrsh;
	}
	public String getMTPOS_MARA() {
		return MTPOS_MARA;
	}
	public void setMTPOS_MARA(String mtpos_mara) {
		MTPOS_MARA = mtpos_mara;
	}
	public String getBRGEW() {
		return BRGEW;
	}
	public void setBRGEW(String brgew) {
		BRGEW = brgew;
	}
	public String getGEWEI() {
		return GEWEI;
	}
	public void setGEWEI(String gewei) {
		GEWEI = gewei;
	}
	public String getNTGEW() {
		return NTGEW;
	}
	public void setNTGEW(String ntgew) {
		NTGEW = ntgew;
	}
	public String getFERTH() {
		return FERTH;
	}
	public void setFERTH(String ferth) {
		FERTH = ferth;
	}
	public String getXCHPF() {
		return XCHPF;
	}
	public void setXCHPF(String xchpf) {
		XCHPF = xchpf;
	}
	public String getTRAGR() {
		return TRAGR;
	}
	public void setTRAGR(String tragr) {
		TRAGR = tragr;
	}
	public String getEKWSL() {
		return EKWSL;
	}
	public void setEKWSL(String ekwsl) {
		EKWSL = ekwsl;
	}
	public String getTEMPB() {
		return TEMPB;
	}
	public void setTEMPB(String tempb) {
		TEMPB = tempb;
	}
	public String getRAUBE() {
		return RAUBE;
	}
	public void setRAUBE(String raube) {
		RAUBE = raube;
	}
	public String getIPRKZ() {
		return IPRKZ;
	}
	public void setIPRKZ(String iprkz) {
		IPRKZ = iprkz;
	}
	public String getMHDHB() {
		return MHDHB;
	}
	public void setMHDHB(String mhdhb) {
		MHDHB = mhdhb;
	}
	public String getRDMHD() {
		return RDMHD;
	}
	public void setRDMHD(String rdmhd) {
		RDMHD = rdmhd;
	}
	public String getSKTOF() {
		return SKTOF;
	}
	public void setSKTOF(String sktof) {
		SKTOF = sktof;
	}
	public String getVERSG() {
		return VERSG;
	}
	public void setVERSG(String versg) {
		VERSG = versg;
	}
	public String getKONDM() {
		return KONDM;
	}
	public void setKONDM(String kondm) {
		KONDM = kondm;
	}
	public String getKTGRM() {
		return KTGRM;
	}
	public void setKTGRM(String ktgrm) {
		KTGRM = ktgrm;
	}
	public String getMTPOS() {
		return MTPOS;
	}
	public void setMTPOS(String mtpos) {
		MTPOS = mtpos;
	}
	public String getMVGR1() {
		return MVGR1;
	}
	public void setMVGR1(String mvgr1) {
		MVGR1 = mvgr1;
	}
	public String getMVGR2() {
		return MVGR2;
	}
	public void setMVGR2(String mvgr2) {
		MVGR2 = mvgr2;
	}
	public String getMVGR3() {
		return MVGR3;
	}
	public void setMVGR3(String mvgr3) {
		MVGR3 = mvgr3;
	}
	public String getMVGR4() {
		return MVGR4;
	}
	public void setMVGR4(String mvgr4) {
		MVGR4 = mvgr4;
	}
	public String getMVGR5() {
		return MVGR5;
	}
	public void setMVGR5(String mvgr5) {
		MVGR5 = mvgr5;
	}
	public String getMTVFP() {
		return MTVFP;
	}
	public void setMTVFP(String mtvfp) {
		MTVFP = mtvfp;
	}
	public String getLADGR() {
		return LADGR;
	}
	public void setLADGR(String ladgr) {
		LADGR = ladgr;
	}
	public String getWEBAZ() {
		return WEBAZ;
	}
	public void setWEBAZ(String webaz) {
		WEBAZ = webaz;
	}
	public String getDISMM() {
		return DISMM;
	}
	public void setDISMM(String dismm) {
		DISMM = dismm;
	}
	public String getDISLS() {
		return DISLS;
	}
	public void setDISLS(String disls) {
		DISLS = disls;
	}
	public String getBESKZ() {
		return BESKZ;
	}
	public void setBESKZ(String beskz) {
		BESKZ = beskz;
	}
	public String getKZECH() {
		return KZECH;
	}
	public void setKZECH(String kzech) {
		KZECH = kzech;
	}
	public String getRGEKZ() {
		return RGEKZ;
	}
	public void setRGEKZ(String rgekz) {
		RGEKZ = rgekz;
	}
	public String getPLIFZ() {
		return PLIFZ;
	}
	public void setPLIFZ(String plifz) {
		PLIFZ = plifz;
	}
	public String getEISBE() {
		return EISBE;
	}
	public void setEISBE(String eisbe) {
		EISBE = eisbe;
	}
	public String getPERKZ() {
		return PERKZ;
	}
	public void setPERKZ(String perkz) {
		PERKZ = perkz;
	}
	public String getSBDKZ() {
		return SBDKZ;
	}
	public void setSBDKZ(String sbdkz) {
		SBDKZ = sbdkz;
	}
	public String getLOSGR() {
		return LOSGR;
	}
	public void setLOSGR(String losgr) {
		LOSGR = losgr;
	}
	public String getINSMK() {
		return INSMK;
	}
	public void setINSMK(String insmk) {
		INSMK = insmk;
	}
	public String getPEINH() {
		return PEINH;
	}
	public void setPEINH(String peinh) {
		PEINH = peinh;
	}
	public String getSTPRS() {
		return STPRS;
	}
	public void setSTPRS(String stprs) {
		STPRS = stprs;
	}
	public String[] getItemExistNO() {
		return itemExistNO;
	}
	public void setItemExistNO(String[] itemExistNO) {
		this.itemExistNO = itemExistNO;
	}
	public String getReqReqNo() {
		return reqReqNo;
	}
	public void setReqReqNo(String reqReqNo) {
		this.reqReqNo = reqReqNo;
	}
	public String getReqItemNo() {
		return reqItemNo;
	}
	public void setReqItemNo(String reqItemNo) {
		this.reqItemNo = reqItemNo;
	}
	public String getReqItemExist() {
		return reqItemExist;
	}
	public void setReqItemExist(String reqItemExist) {
		this.reqItemExist = reqItemExist;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public String[] getItemCode() {
		return itemCode;
	}
	public void setItemCode(String[] itemCode) {
		this.itemCode = itemCode;
	}
	public String[] getItemExist() {
		return itemExist;
	}
	public void setItemExist(String[] itemExist) {
		this.itemExist = itemExist;
	}
	public String[] getSelectedRequestNo() {
		return selectedRequestNo;
	}
	public void setSelectedRequestNo(String[] selectedRequestNo) {
		this.selectedRequestNo = selectedRequestNo;
	}
	public String getMessage3() {
		return message3;
	}
	public void setMessage3(String message3) {
		this.message3 = message3;
	}
	public String getMaterialCodeLists() {
		return materialCodeLists;
	}
	public void setMaterialCodeLists(String materialCodeLists) {
		this.materialCodeLists = materialCodeLists;
	}
	public String getReqRequstType() {
		return reqRequstType;
	}
	public void setReqRequstType(String reqRequstType) {
		this.reqRequstType = reqRequstType;
	}
	public String getSelectedFilter() {
		return selectedFilter;
	}
	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMasterType() {
		return masterType;
	}
	public void setMasterType(String masterType) {
		this.masterType = masterType;
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
	public String getMaterialGrup() {
		return materialGrup;
	}
	public void setMaterialGrup(String materialGrup) {
		this.materialGrup = materialGrup;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEquipIntendedFor() {
		return equipIntendedFor;
	}
	public void setEquipIntendedFor(String equipIntendedFor) {
		this.equipIntendedFor = equipIntendedFor;
	}
	
	
}
