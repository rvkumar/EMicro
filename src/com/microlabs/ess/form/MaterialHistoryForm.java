package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class MaterialHistoryForm  extends ActionForm{
	private int requestNo;
	private String requestDate;
	private String modifiedDate;
	private String locationId;
	private String matType;
	private String materialTypeId;
	
	///roh
	
		private String dmfGradeId;
		private String materialGrade;
		private String cosGradeNo;
		private String additionalTest;
		private String toBeUsedInProducts;
		private String isVendorSpecificMaterial;
		private String mfgrName;
		private String siteOfManufacture;
		private String tempCondition;
		private String storageCondition;
		private String shelfLife;
		private String dutyElement;
		private String retestDays;
		private String pharmacopName;
		private String pharmacopGrade;
		private String genericName;
		private String synonym;
		private String pharmacopSpecification;
		private String isDMFMaterial;
		private String shelfLifeType;
		private String retestType;	
		private String reqMaterialGroup;
		
		
		private String dmfGradeId_C;
		private String materialGrade_C;
		private String cosGradeNo_C;
		private String additionalTest_C;
		private String toBeUsedInProducts_C;
		private String isVendorSpecificMaterial_C;
		private String mfgrName_C;
		private String siteOfManufacture_C;
		private String tempCondition_C;
		private String storageCondition_C;
		private String shelfLife_C;
		private String dutyElement_C;
		private String retestDays_C;
		private String pharmacopName_C;
		private String pharmacopGrade_C;
		private String genericName_C;
		private String synonym_C;
		private String pharmacopSpecification_C;
		private String isDMFMaterial_C;
		private String shelfLifeType_C;
		private String retestType_C;
		private String reqMaterialGroup_C;
		
		
		
		//VERP
		private String packageMaterialGroup;
		private String typeOfMaterial;	
		private String artworkNo;	
		private String isArtworkRevision;
		private String  existingSAPItemCode;
		
		private String packageMaterialGroup_C;
		private String typeOfMaterial_C;	
		private String artworkNo_C;	
		private String isArtworkRevision_C;
		private String  existingSAPItemCode_C;
		
		//HALB
		private String targetWeight;
		private String standardBatchSize;
		private String batchCode;
		private String weightUOM;
		
		
		//HALB
		private String targetWeight_C;
		private String standardBatchSize_C;
		private String batchCode_C;
		private String weightUOM_C;
		
		//FERT HAWA
		private String manufacturedAt;
		private String shelfType;
		private String saleableOrSample;
		private String domesticOrExports;
		private String salesPackId;
		private String packTypeId;
		private String therapeuticSegmentID;
		private String brandID;
		private String srengthId;
		private String prodInspMemo;
		private String grossWeight;
		private String netWeight;
		private String dimension;
		private String salesUnitOfMeaseurement;
		private String taxClassification;
		private String materialPricing;

		
		private String manufacturedAt_C;
		private String shelfType_C;
		private String saleableOrSample_C;
		private String domesticOrExports_C;
		private String salesPackId_C;
		private String packTypeId_C;
		private String therapeuticSegmentID_C;
		private String brandID_C;
		private String srengthId_C;
		private String prodInspMemo_C;
		private String grossWeight_C;
		private String netWeight_C;
		private String dimension_C;
		private String salesUnitOfMeaseurement_C;
		private String taxClassification_C;
		private String materialPricing_C;
		
		
	
	//General Material
	private String storageLocationId;
	private String materialShortName;
	private String materialLongName;
	private String materialGroupId;
	private String puchaseGroupId;
	private String unitOfMeasId;
	private String approximateValue;
	private String valuationClass;
	private String materialUsedIn;
	private String isEquipment;
	private String isSpare;
	private String isNew;
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
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCreationDate;
	private String sapCreatedBy;
	private String requestedBy;
	private String materialCodeLists;
	
	private String materialCodeLists1;
	private String typeDetails;
	
	private String message;
	private String message2;
	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	private String approveType;
	private String equipmentMake;
	private String componentMake;
	private String oemPartNo;
	private String isAsset;
	private String appStatusMessage;
	private String role;
	
	private String materialGroupId_C;
	private String storageLocationId_C;
	private String materialShortName_C;
	private String materialLongName_C;
	private String puchaseGroupId_C;
	private String unitOfMeasId_C;
	private String approximateValue_C;
	private String valuationClass_C;
	private String materialUsedIn_C;
	private String isEquipment_C;
	private String isSpare_C;
	private String isNew_C;
	private String isItNewFurniture_C;
	private String isItFacility_C;
	private String isSpareNewEquipment_C;
	private String equipmentName_C;
	private String prNumber_C;
	private String poNumber_C;
	private String utilizingDept_C;
	private String detailedJustification_C;
	private String detailedSpecification_C;
	private String purposeID_C;
	private String sapCodeNo_C;
	private String sapCodeExists_C;
	private String sapCreationDate_C;
	private String sapCreatedBy_C;
	private String requestedBy_C;
	private String materialCodeLists_C;
	private String materialCodeLists1_C;
	private String typeDetails_C;
	private String equipmentMake_C;
	private String componentMake_C;
	private String oemPartNo_C;
	private String isAsset_C;
	private String role_C;
	private String modifiedBy;
	
	//zpsr
	private String isNewEquipment;
	private String searchText;
	private String reqFieldName;
	private String eqName;
	private String moc;
	private String rating;
	private String range;
	private String dimensions;
	private String equipIntendedFor;
	private String packSize;
	
	private String isNewEquipment_C;
	private String searchText_C;
	private String reqFieldName_C;
	private String eqName_C;
	private String moc_C;
	private String rating_C;
	private String range_C;
	private String dimensions_C;
	private String equipIntendedFor_C;
	private String packSize_C;
	//zppc
	
	private String divisionId;
	private String isSASFormAvailable;
	private String divisionId_C;
	private String isSASFormAvailable_C;
	//service master
	private String plantCode;
	private String storageLocation;
	private String serviceDescription;
	private String detailedServiceDescription;
	private String uom;
	private String purchaseGroup;
	private String serviceCatagory;
	private String serviceGroup;
	private String e_m_name;
	private String app_amount;
	private String whereUsed;
	private String purpose;
	private String justification;
	
	private String plantCode_C;
	private String storageLocation_C;
	private String serviceDescription_C;
	private String detailedServiceDescription_C;
	private String uom_C;
	private String purchaseGroup_C;
	private String serviceCatagory_C;
	private String serviceGroup_C;
	private String e_m_name_C;
	private String app_amount_C;
	private String whereUsed_C;
	private String purpose_C;
	private String justification_C;
	
	//customer master
	private String accGroupId;
	private String viewType;
	private String pincode;
	private String landlineNo;
	private String customerGroup;
	private String priceGroup;
	private String priceList;
	private String taxType;
	private String listNumber;
	private String tinNumber;
	private String cstNumber;
	private String panNumber;
	private String serviceTaxNo;
	private String isRegdExciseVender;
	private String dlno1;
	private String dlno2;
	private String paymentTermID;
	private String createdDate;
	private String createdBy;
	private String custmerType;
	private String tdsStatus;
	private String sales;
	private String accounts;
	private String domestic;
	private String exports;
	private String customerType;
	private String cutomerCode;
	private String searchRequired;
	private String customerName;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String mobileNo;
	private String faxNo;
	private String emailId;
	private String eccNo;
	private String exciseRegNo;
	private String linkName;
	private String exciseRange;
	private String exciseDivision;
	private String currencyId;
	private String accountClerkId;
	private String tdsCode;
	private String countryId;
	private String state;
	
	private String accGroupId_C;
	private String viewType_C;
	private String pincode_C;
	private String landlineNo_C;
	private String customerGroup_C;
	private String priceGroup_C;
	private String priceList_C;
	private String taxType_C;
	private String listNumber_C;
	private String tinNumber_C;
	private String cstNumber_C;
	private String panNumber_C;
	private String serviceTaxNo_C;
	private String isRegdExciseVender_C;
	private String dlno1_C;
	private String dlno2_C;
	private String paymentTermID_C;
	private String createdDate_C;
	private String createdBy_C;
	private String custmerType_C;
	private String tdsStatus_C;
	private String sales_C;
	private String accounts_C;
	private String domestic_C;
	private String exports_C;
	private String customerType_C;
	private String cutomerCode_C;
	private String searchRequired_C;
	private String customerName_C;
	private String address1_C;
	private String address2_C;
	private String address3_C;
	private String address4_C;
	private String city_C;
	private String mobileNo_C;
	private String faxNo_C;
	private String emailId_C;
	private String eccNo_C;
	private String exciseRegNo_C;
	private String linkName_C;
	private String exciseRange_C;
	private String exciseDivision_C;
	private String currencyId_C;
	private String accountClerkId_C;
	private String tdsCode_C;
	private String countryId_C;
	private String state_C;
//vendor
	
	private String accountGroupId;
	private String purchaseView;
	private String title;
	private String name;
	private String pinCode;
	private String country;
	private String landLineNo;
	private String reConcillationActId;
	private String elgTds;
	private String lstNo;
	private String tinNo;
	private String cstNo;
	private String panNo;
	private String serviceTaxRegNo;
	private String regExciseVendor;
	private String paymentTermId;
	private String isApprovedVendor;
	private FormFile vendorAttachments;
	private String contentDescription;
	private String fileFullPath;
	private String videoFullPath;
	private String commissionerate;
	private String typeOfVendor;
	private String accountView;
	
	
	private String accountView_C;
	private String accountGroupId_C;
	private String purchaseView_C;
	private String title_C;
	private String name_C;
	private String pinCode_C;
	private String country_C;
	private String landLineNo_C;
	private String reConcillationActId_C;
	private String elgTds_C;
	private String lstNo_C;
	private String tinNo_C;
	private String cstNo_C;
	private String panNo_C;
	private String serviceTaxRegNo_C;
	private String regExciseVendor_C;
	private String paymentTermId_C;
	private String isApprovedVendor_C;
	private FormFile vendorAttachments_C;
	private String contentDescription_C;
	private String fileFullPath_C;
	private String videoFullPath_C;
	private String commissionerate_C;
	private String typeOfVendor_C;
	
	
	
	
//	
	
	
	
	public String getCountryId() {
		return countryId;
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
	public String getReqMaterialGroup() {
		return reqMaterialGroup;
	}
	public void setReqMaterialGroup(String reqMaterialGroup) {
		this.reqMaterialGroup = reqMaterialGroup;
	}
	public String getDmfGradeId_C() {
		return dmfGradeId_C;
	}
	public void setDmfGradeId_C(String dmfGradeId_C) {
		this.dmfGradeId_C = dmfGradeId_C;
	}
	public String getMaterialGrade_C() {
		return materialGrade_C;
	}
	public void setMaterialGrade_C(String materialGrade_C) {
		this.materialGrade_C = materialGrade_C;
	}
	public String getCosGradeNo_C() {
		return cosGradeNo_C;
	}
	public void setCosGradeNo_C(String cosGradeNo_C) {
		this.cosGradeNo_C = cosGradeNo_C;
	}
	public String getAdditionalTest_C() {
		return additionalTest_C;
	}
	public void setAdditionalTest_C(String additionalTest_C) {
		this.additionalTest_C = additionalTest_C;
	}
	public String getToBeUsedInProducts_C() {
		return toBeUsedInProducts_C;
	}
	public void setToBeUsedInProducts_C(String toBeUsedInProducts_C) {
		this.toBeUsedInProducts_C = toBeUsedInProducts_C;
	}
	public String getIsVendorSpecificMaterial_C() {
		return isVendorSpecificMaterial_C;
	}
	public void setIsVendorSpecificMaterial_C(String isVendorSpecificMaterial_C) {
		this.isVendorSpecificMaterial_C = isVendorSpecificMaterial_C;
	}
	public String getMfgrName_C() {
		return mfgrName_C;
	}
	public void setMfgrName_C(String mfgrName_C) {
		this.mfgrName_C = mfgrName_C;
	}
	public String getSiteOfManufacture_C() {
		return siteOfManufacture_C;
	}
	public void setSiteOfManufacture_C(String siteOfManufacture_C) {
		this.siteOfManufacture_C = siteOfManufacture_C;
	}
	public String getTempCondition_C() {
		return tempCondition_C;
	}
	public void setTempCondition_C(String tempCondition_C) {
		this.tempCondition_C = tempCondition_C;
	}
	public String getStorageCondition_C() {
		return storageCondition_C;
	}
	public void setStorageCondition_C(String storageCondition_C) {
		this.storageCondition_C = storageCondition_C;
	}
	public String getShelfLife_C() {
		return shelfLife_C;
	}
	public void setShelfLife_C(String shelfLife_C) {
		this.shelfLife_C = shelfLife_C;
	}
	public String getDutyElement_C() {
		return dutyElement_C;
	}
	public void setDutyElement_C(String dutyElement_C) {
		this.dutyElement_C = dutyElement_C;
	}
	public String getRetestDays_C() {
		return retestDays_C;
	}
	public void setRetestDays_C(String retestDays_C) {
		this.retestDays_C = retestDays_C;
	}
	public String getPharmacopName_C() {
		return pharmacopName_C;
	}
	public void setPharmacopName_C(String pharmacopName_C) {
		this.pharmacopName_C = pharmacopName_C;
	}
	public String getPharmacopGrade_C() {
		return pharmacopGrade_C;
	}
	public void setPharmacopGrade_C(String pharmacopGrade_C) {
		this.pharmacopGrade_C = pharmacopGrade_C;
	}
	public String getGenericName_C() {
		return genericName_C;
	}
	public void setGenericName_C(String genericName_C) {
		this.genericName_C = genericName_C;
	}
	public String getSynonym_C() {
		return synonym_C;
	}
	public void setSynonym_C(String synonym_C) {
		this.synonym_C = synonym_C;
	}
	public String getPharmacopSpecification_C() {
		return pharmacopSpecification_C;
	}
	public void setPharmacopSpecification_C(String pharmacopSpecification_C) {
		this.pharmacopSpecification_C = pharmacopSpecification_C;
	}
	public String getIsDMFMaterial_C() {
		return isDMFMaterial_C;
	}
	public void setIsDMFMaterial_C(String isDMFMaterial_C) {
		this.isDMFMaterial_C = isDMFMaterial_C;
	}
	public String getShelfLifeType_C() {
		return shelfLifeType_C;
	}
	public void setShelfLifeType_C(String shelfLifeType_C) {
		this.shelfLifeType_C = shelfLifeType_C;
	}
	public String getRetestType_C() {
		return retestType_C;
	}
	public void setRetestType_C(String retestType_C) {
		this.retestType_C = retestType_C;
	}
	public String getReqMaterialGroup_C() {
		return reqMaterialGroup_C;
	}
	public void setReqMaterialGroup_C(String reqMaterialGroup_C) {
		this.reqMaterialGroup_C = reqMaterialGroup_C;
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
	public String getPackageMaterialGroup_C() {
		return packageMaterialGroup_C;
	}
	public void setPackageMaterialGroup_C(String packageMaterialGroup_C) {
		this.packageMaterialGroup_C = packageMaterialGroup_C;
	}
	public String getTypeOfMaterial_C() {
		return typeOfMaterial_C;
	}
	public void setTypeOfMaterial_C(String typeOfMaterial_C) {
		this.typeOfMaterial_C = typeOfMaterial_C;
	}
	public String getArtworkNo_C() {
		return artworkNo_C;
	}
	public void setArtworkNo_C(String artworkNo_C) {
		this.artworkNo_C = artworkNo_C;
	}
	public String getIsArtworkRevision_C() {
		return isArtworkRevision_C;
	}
	public void setIsArtworkRevision_C(String isArtworkRevision_C) {
		this.isArtworkRevision_C = isArtworkRevision_C;
	}
	public String getExistingSAPItemCode_C() {
		return existingSAPItemCode_C;
	}
	public void setExistingSAPItemCode_C(String existingSAPItemCode_C) {
		this.existingSAPItemCode_C = existingSAPItemCode_C;
	}
	public String getTargetWeight() {
		return targetWeight;
	}
	public void setTargetWeight(String targetWeight) {
		this.targetWeight = targetWeight;
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
	public String getWeightUOM() {
		return weightUOM;
	}
	public void setWeightUOM(String weightUOM) {
		this.weightUOM = weightUOM;
	}
	public String getTargetWeight_C() {
		return targetWeight_C;
	}
	public void setTargetWeight_C(String targetWeight_C) {
		this.targetWeight_C = targetWeight_C;
	}
	public String getStandardBatchSize_C() {
		return standardBatchSize_C;
	}
	public void setStandardBatchSize_C(String standardBatchSize_C) {
		this.standardBatchSize_C = standardBatchSize_C;
	}
	public String getBatchCode_C() {
		return batchCode_C;
	}
	public void setBatchCode_C(String batchCode_C) {
		this.batchCode_C = batchCode_C;
	}
	public String getWeightUOM_C() {
		return weightUOM_C;
	}
	public void setWeightUOM_C(String weightUOM_C) {
		this.weightUOM_C = weightUOM_C;
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
	public String getManufacturedAt_C() {
		return manufacturedAt_C;
	}
	public void setManufacturedAt_C(String manufacturedAt_C) {
		this.manufacturedAt_C = manufacturedAt_C;
	}
	public String getShelfType_C() {
		return shelfType_C;
	}
	public void setShelfType_C(String shelfType_C) {
		this.shelfType_C = shelfType_C;
	}
	public String getSaleableOrSample_C() {
		return saleableOrSample_C;
	}
	public void setSaleableOrSample_C(String saleableOrSample_C) {
		this.saleableOrSample_C = saleableOrSample_C;
	}
	public String getDomesticOrExports_C() {
		return domesticOrExports_C;
	}
	public void setDomesticOrExports_C(String domesticOrExports_C) {
		this.domesticOrExports_C = domesticOrExports_C;
	}
	public String getSalesPackId_C() {
		return salesPackId_C;
	}
	public void setSalesPackId_C(String salesPackId_C) {
		this.salesPackId_C = salesPackId_C;
	}
	public String getPackTypeId_C() {
		return packTypeId_C;
	}
	public void setPackTypeId_C(String packTypeId_C) {
		this.packTypeId_C = packTypeId_C;
	}
	public String getTherapeuticSegmentID_C() {
		return therapeuticSegmentID_C;
	}
	public void setTherapeuticSegmentID_C(String therapeuticSegmentID_C) {
		this.therapeuticSegmentID_C = therapeuticSegmentID_C;
	}
	public String getBrandID_C() {
		return brandID_C;
	}
	public void setBrandID_C(String brandID_C) {
		this.brandID_C = brandID_C;
	}
	public String getSrengthId_C() {
		return srengthId_C;
	}
	public void setSrengthId_C(String srengthId_C) {
		this.srengthId_C = srengthId_C;
	}
	public String getProdInspMemo_C() {
		return prodInspMemo_C;
	}
	public void setProdInspMemo_C(String prodInspMemo_C) {
		this.prodInspMemo_C = prodInspMemo_C;
	}
	public String getGrossWeight_C() {
		return grossWeight_C;
	}
	public void setGrossWeight_C(String grossWeight_C) {
		this.grossWeight_C = grossWeight_C;
	}
	public String getNetWeight_C() {
		return netWeight_C;
	}
	public void setNetWeight_C(String netWeight_C) {
		this.netWeight_C = netWeight_C;
	}
	public String getDimension_C() {
		return dimension_C;
	}
	public void setDimension_C(String dimension_C) {
		this.dimension_C = dimension_C;
	}
	public String getSalesUnitOfMeaseurement_C() {
		return salesUnitOfMeaseurement_C;
	}
	public void setSalesUnitOfMeaseurement_C(String salesUnitOfMeaseurement_C) {
		this.salesUnitOfMeaseurement_C = salesUnitOfMeaseurement_C;
	}
	public String getTaxClassification_C() {
		return taxClassification_C;
	}
	public void setTaxClassification_C(String taxClassification_C) {
		this.taxClassification_C = taxClassification_C;
	}
	public String getMaterialPricing_C() {
		return materialPricing_C;
	}
	public void setMaterialPricing_C(String materialPricing_C) {
		this.materialPricing_C = materialPricing_C;
	}
	public String getAccountView() {
		return accountView;
	}
	public void setAccountView(String accountView) {
		this.accountView = accountView;
	}
	public String getAccountView_C() {
		return accountView_C;
	}
	public void setAccountView_C(String accountView_C) {
		this.accountView_C = accountView_C;
	}
	public String getAccountGroupId() {
		return accountGroupId;
	}
	public void setAccountGroupId(String accountGroupId) {
		this.accountGroupId = accountGroupId;
	}
	public String getPurchaseView() {
		return purchaseView;
	}
	public void setPurchaseView(String purchaseView) {
		this.purchaseView = purchaseView;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLandLineNo() {
		return landLineNo;
	}
	public void setLandLineNo(String landLineNo) {
		this.landLineNo = landLineNo;
	}
	public String getReConcillationActId() {
		return reConcillationActId;
	}
	public void setReConcillationActId(String reConcillationActId) {
		this.reConcillationActId = reConcillationActId;
	}
	public String getElgTds() {
		return elgTds;
	}
	public void setElgTds(String elgTds) {
		this.elgTds = elgTds;
	}
	public String getLstNo() {
		return lstNo;
	}
	public void setLstNo(String lstNo) {
		this.lstNo = lstNo;
	}
	public String getTinNo() {
		return tinNo;
	}
	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}
	public String getCstNo() {
		return cstNo;
	}
	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getServiceTaxRegNo() {
		return serviceTaxRegNo;
	}
	public void setServiceTaxRegNo(String serviceTaxRegNo) {
		this.serviceTaxRegNo = serviceTaxRegNo;
	}
	public String getRegExciseVendor() {
		return regExciseVendor;
	}
	public void setRegExciseVendor(String regExciseVendor) {
		this.regExciseVendor = regExciseVendor;
	}
	public String getPaymentTermId() {
		return paymentTermId;
	}
	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}
	public String getIsApprovedVendor() {
		return isApprovedVendor;
	}
	public void setIsApprovedVendor(String isApprovedVendor) {
		this.isApprovedVendor = isApprovedVendor;
	}
	public FormFile getVendorAttachments() {
		return vendorAttachments;
	}
	public void setVendorAttachments(FormFile vendorAttachments) {
		this.vendorAttachments = vendorAttachments;
	}
	public String getContentDescription() {
		return contentDescription;
	}
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
	public String getFileFullPath() {
		return fileFullPath;
	}
	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}
	public String getVideoFullPath() {
		return videoFullPath;
	}
	public void setVideoFullPath(String videoFullPath) {
		this.videoFullPath = videoFullPath;
	}
	public String getCommissionerate() {
		return commissionerate;
	}
	public void setCommissionerate(String commissionerate) {
		this.commissionerate = commissionerate;
	}
	public String getTypeOfVendor() {
		return typeOfVendor;
	}
	public void setTypeOfVendor(String typeOfVendor) {
		this.typeOfVendor = typeOfVendor;
	}
	public String getAccountGroupId_C() {
		return accountGroupId_C;
	}
	public void setAccountGroupId_C(String accountGroupId_C) {
		this.accountGroupId_C = accountGroupId_C;
	}
	public String getPurchaseView_C() {
		return purchaseView_C;
	}
	public void setPurchaseView_C(String purchaseView_C) {
		this.purchaseView_C = purchaseView_C;
	}
	public String getTitle_C() {
		return title_C;
	}
	public void setTitle_C(String title_C) {
		this.title_C = title_C;
	}
	public String getName_C() {
		return name_C;
	}
	public void setName_C(String name_C) {
		this.name_C = name_C;
	}
	public String getPinCode_C() {
		return pinCode_C;
	}
	public void setPinCode_C(String pinCode_C) {
		this.pinCode_C = pinCode_C;
	}
	public String getCountry_C() {
		return country_C;
	}
	public void setCountry_C(String country_C) {
		this.country_C = country_C;
	}
	public String getLandLineNo_C() {
		return landLineNo_C;
	}
	public void setLandLineNo_C(String landLineNo_C) {
		this.landLineNo_C = landLineNo_C;
	}
	public String getReConcillationActId_C() {
		return reConcillationActId_C;
	}
	public void setReConcillationActId_C(String reConcillationActId_C) {
		this.reConcillationActId_C = reConcillationActId_C;
	}
	public String getElgTds_C() {
		return elgTds_C;
	}
	public void setElgTds_C(String elgTds_C) {
		this.elgTds_C = elgTds_C;
	}
	public String getLstNo_C() {
		return lstNo_C;
	}
	public void setLstNo_C(String lstNo_C) {
		this.lstNo_C = lstNo_C;
	}
	public String getTinNo_C() {
		return tinNo_C;
	}
	public void setTinNo_C(String tinNo_C) {
		this.tinNo_C = tinNo_C;
	}
	public String getCstNo_C() {
		return cstNo_C;
	}
	public void setCstNo_C(String cstNo_C) {
		this.cstNo_C = cstNo_C;
	}
	public String getPanNo_C() {
		return panNo_C;
	}
	public void setPanNo_C(String panNo_C) {
		this.panNo_C = panNo_C;
	}
	public String getServiceTaxRegNo_C() {
		return serviceTaxRegNo_C;
	}
	public void setServiceTaxRegNo_C(String serviceTaxRegNo_C) {
		this.serviceTaxRegNo_C = serviceTaxRegNo_C;
	}
	public String getRegExciseVendor_C() {
		return regExciseVendor_C;
	}
	public void setRegExciseVendor_C(String regExciseVendor_C) {
		this.regExciseVendor_C = regExciseVendor_C;
	}
	public String getPaymentTermId_C() {
		return paymentTermId_C;
	}
	public void setPaymentTermId_C(String paymentTermId_C) {
		this.paymentTermId_C = paymentTermId_C;
	}
	public String getIsApprovedVendor_C() {
		return isApprovedVendor_C;
	}
	public void setIsApprovedVendor_C(String isApprovedVendor_C) {
		this.isApprovedVendor_C = isApprovedVendor_C;
	}
	public FormFile getVendorAttachments_C() {
		return vendorAttachments_C;
	}
	public void setVendorAttachments_C(FormFile vendorAttachments_C) {
		this.vendorAttachments_C = vendorAttachments_C;
	}
	public String getContentDescription_C() {
		return contentDescription_C;
	}
	public void setContentDescription_C(String contentDescription_C) {
		this.contentDescription_C = contentDescription_C;
	}
	public String getFileFullPath_C() {
		return fileFullPath_C;
	}
	public void setFileFullPath_C(String fileFullPath_C) {
		this.fileFullPath_C = fileFullPath_C;
	}
	public String getVideoFullPath_C() {
		return videoFullPath_C;
	}
	public void setVideoFullPath_C(String videoFullPath_C) {
		this.videoFullPath_C = videoFullPath_C;
	}
	public String getCommissionerate_C() {
		return commissionerate_C;
	}
	public void setCommissionerate_C(String commissionerate_C) {
		this.commissionerate_C = commissionerate_C;
	}
	public String getTypeOfVendor_C() {
		return typeOfVendor_C;
	}
	public void setTypeOfVendor_C(String typeOfVendor_C) {
		this.typeOfVendor_C = typeOfVendor_C;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAccGroupId_C() {
		return accGroupId_C;
	}
	public void setAccGroupId_C(String accGroupId_C) {
		this.accGroupId_C = accGroupId_C;
	}
	public String getViewType_C() {
		return viewType_C;
	}
	public void setViewType_C(String viewType_C) {
		this.viewType_C = viewType_C;
	}
	public String getPincode_C() {
		return pincode_C;
	}
	public void setPincode_C(String pincode_C) {
		this.pincode_C = pincode_C;
	}
	public String getLandlineNo_C() {
		return landlineNo_C;
	}
	public void setLandlineNo_C(String landlineNo_C) {
		this.landlineNo_C = landlineNo_C;
	}
	public String getCustomerGroup_C() {
		return customerGroup_C;
	}
	public void setCustomerGroup_C(String customerGroup_C) {
		this.customerGroup_C = customerGroup_C;
	}
	public String getPriceGroup_C() {
		return priceGroup_C;
	}
	public void setPriceGroup_C(String priceGroup_C) {
		this.priceGroup_C = priceGroup_C;
	}
	public String getPriceList_C() {
		return priceList_C;
	}
	public void setPriceList_C(String priceList_C) {
		this.priceList_C = priceList_C;
	}
	public String getTaxType_C() {
		return taxType_C;
	}
	public void setTaxType_C(String taxType_C) {
		this.taxType_C = taxType_C;
	}
	public String getListNumber_C() {
		return listNumber_C;
	}
	public void setListNumber_C(String listNumber_C) {
		this.listNumber_C = listNumber_C;
	}
	public String getTinNumber_C() {
		return tinNumber_C;
	}
	public void setTinNumber_C(String tinNumber_C) {
		this.tinNumber_C = tinNumber_C;
	}
	public String getCstNumber_C() {
		return cstNumber_C;
	}
	public void setCstNumber_C(String cstNumber_C) {
		this.cstNumber_C = cstNumber_C;
	}
	public String getPanNumber_C() {
		return panNumber_C;
	}
	public void setPanNumber_C(String panNumber_C) {
		this.panNumber_C = panNumber_C;
	}
	public String getServiceTaxNo_C() {
		return serviceTaxNo_C;
	}
	public void setServiceTaxNo_C(String serviceTaxNo_C) {
		this.serviceTaxNo_C = serviceTaxNo_C;
	}
	public String getIsRegdExciseVender_C() {
		return isRegdExciseVender_C;
	}
	public void setIsRegdExciseVender_C(String isRegdExciseVender_C) {
		this.isRegdExciseVender_C = isRegdExciseVender_C;
	}
	public String getDlno1_C() {
		return dlno1_C;
	}
	public void setDlno1_C(String dlno1_C) {
		this.dlno1_C = dlno1_C;
	}
	public String getDlno2_C() {
		return dlno2_C;
	}
	public void setDlno2_C(String dlno2_C) {
		this.dlno2_C = dlno2_C;
	}
	public String getPaymentTermID_C() {
		return paymentTermID_C;
	}
	public void setPaymentTermID_C(String paymentTermID_C) {
		this.paymentTermID_C = paymentTermID_C;
	}
	public String getCreatedDate_C() {
		return createdDate_C;
	}
	public void setCreatedDate_C(String createdDate_C) {
		this.createdDate_C = createdDate_C;
	}
	public String getCreatedBy_C() {
		return createdBy_C;
	}
	public void setCreatedBy_C(String createdBy_C) {
		this.createdBy_C = createdBy_C;
	}
	public String getCustmerType_C() {
		return custmerType_C;
	}
	public void setCustmerType_C(String custmerType_C) {
		this.custmerType_C = custmerType_C;
	}
	public String getTdsStatus_C() {
		return tdsStatus_C;
	}
	public void setTdsStatus_C(String tdsStatus_C) {
		this.tdsStatus_C = tdsStatus_C;
	}
	public String getSales_C() {
		return sales_C;
	}
	public void setSales_C(String sales_C) {
		this.sales_C = sales_C;
	}
	public String getAccounts_C() {
		return accounts_C;
	}
	public void setAccounts_C(String accounts_C) {
		this.accounts_C = accounts_C;
	}
	public String getDomestic_C() {
		return domestic_C;
	}
	public void setDomestic_C(String domestic_C) {
		this.domestic_C = domestic_C;
	}
	public String getExports_C() {
		return exports_C;
	}
	public void setExports_C(String exports_C) {
		this.exports_C = exports_C;
	}
	public String getCustomerType_C() {
		return customerType_C;
	}
	public void setCustomerType_C(String customerType_C) {
		this.customerType_C = customerType_C;
	}
	public String getCutomerCode_C() {
		return cutomerCode_C;
	}
	public void setCutomerCode_C(String cutomerCode_C) {
		this.cutomerCode_C = cutomerCode_C;
	}
	public String getSearchRequired_C() {
		return searchRequired_C;
	}
	public void setSearchRequired_C(String searchRequired_C) {
		this.searchRequired_C = searchRequired_C;
	}
	public String getCustomerName_C() {
		return customerName_C;
	}
	public void setCustomerName_C(String customerName_C) {
		this.customerName_C = customerName_C;
	}
	public String getAddress1_C() {
		return address1_C;
	}
	public void setAddress1_C(String address1_C) {
		this.address1_C = address1_C;
	}
	public String getAddress2_C() {
		return address2_C;
	}
	public void setAddress2_C(String address2_C) {
		this.address2_C = address2_C;
	}
	public String getAddress3_C() {
		return address3_C;
	}
	public void setAddress3_C(String address3_C) {
		this.address3_C = address3_C;
	}
	public String getAddress4_C() {
		return address4_C;
	}
	public void setAddress4_C(String address4_C) {
		this.address4_C = address4_C;
	}
	public String getCity_C() {
		return city_C;
	}
	public void setCity_C(String city_C) {
		this.city_C = city_C;
	}
	public String getMobileNo_C() {
		return mobileNo_C;
	}
	public void setMobileNo_C(String mobileNo_C) {
		this.mobileNo_C = mobileNo_C;
	}
	public String getFaxNo_C() {
		return faxNo_C;
	}
	public void setFaxNo_C(String faxNo_C) {
		this.faxNo_C = faxNo_C;
	}
	public String getEmailId_C() {
		return emailId_C;
	}
	public void setEmailId_C(String emailId_C) {
		this.emailId_C = emailId_C;
	}
	public String getEccNo_C() {
		return eccNo_C;
	}
	public void setEccNo_C(String eccNo_C) {
		this.eccNo_C = eccNo_C;
	}
	public String getExciseRegNo_C() {
		return exciseRegNo_C;
	}
	public void setExciseRegNo_C(String exciseRegNo_C) {
		this.exciseRegNo_C = exciseRegNo_C;
	}
	public String getLinkName_C() {
		return linkName_C;
	}
	public void setLinkName_C(String linkName_C) {
		this.linkName_C = linkName_C;
	}
	public String getExciseRange_C() {
		return exciseRange_C;
	}
	public void setExciseRange_C(String exciseRange_C) {
		this.exciseRange_C = exciseRange_C;
	}
	public String getExciseDivision_C() {
		return exciseDivision_C;
	}
	public void setExciseDivision_C(String exciseDivision_C) {
		this.exciseDivision_C = exciseDivision_C;
	}
	public String getCurrencyId_C() {
		return currencyId_C;
	}
	public void setCurrencyId_C(String currencyId_C) {
		this.currencyId_C = currencyId_C;
	}
	public String getAccountClerkId_C() {
		return accountClerkId_C;
	}
	public void setAccountClerkId_C(String accountClerkId_C) {
		this.accountClerkId_C = accountClerkId_C;
	}
	public String getTdsCode_C() {
		return tdsCode_C;
	}
	public void setTdsCode_C(String tdsCode_C) {
		this.tdsCode_C = tdsCode_C;
	}
	public String getCountryId_C() {
		return countryId_C;
	}
	public void setCountryId_C(String countryId_C) {
		this.countryId_C = countryId_C;
	}
	public String getState_C() {
		return state_C;
	}
	public void setState_C(String state_C) {
		this.state_C = state_C;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getTdsCode() {
		return tdsCode;
	}
	public void setTdsCode(String tdsCode) {
		this.tdsCode = tdsCode;
	}
	public String getAccountClerkId() {
		return accountClerkId;
	}
	public void setAccountClerkId(String accountClerkId) {
		this.accountClerkId = accountClerkId;
	}
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getEccNo() {
		return eccNo;
	}
	public void setEccNo(String eccNo) {
		this.eccNo = eccNo;
	}
	public String getExciseRegNo() {
		return exciseRegNo;
	}
	public void setExciseRegNo(String exciseRegNo) {
		this.exciseRegNo = exciseRegNo;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getExciseRange() {
		return exciseRange;
	}
	public void setExciseRange(String exciseRange) {
		this.exciseRange = exciseRange;
	}
	public String getExciseDivision() {
		return exciseDivision;
	}
	public void setExciseDivision(String exciseDivision) {
		this.exciseDivision = exciseDivision;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAccGroupId() {
		return accGroupId;
	}
	public void setAccGroupId(String accGroupId) {
		this.accGroupId = accGroupId;
	}
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getLandlineNo() {
		return landlineNo;
	}
	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}
	public String getCustomerGroup() {
		return customerGroup;
	}
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	public String getPriceGroup() {
		return priceGroup;
	}
	public void setPriceGroup(String priceGroup) {
		this.priceGroup = priceGroup;
	}
	public String getPriceList() {
		return priceList;
	}
	public void setPriceList(String priceList) {
		this.priceList = priceList;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getListNumber() {
		return listNumber;
	}
	public void setListNumber(String listNumber) {
		this.listNumber = listNumber;
	}
	public String getTinNumber() {
		return tinNumber;
	}
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}
	public String getCstNumber() {
		return cstNumber;
	}
	public void setCstNumber(String cstNumber) {
		this.cstNumber = cstNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getServiceTaxNo() {
		return serviceTaxNo;
	}
	public void setServiceTaxNo(String serviceTaxNo) {
		this.serviceTaxNo = serviceTaxNo;
	}
	public String getIsRegdExciseVender() {
		return isRegdExciseVender;
	}
	public void setIsRegdExciseVender(String isRegdExciseVender) {
		this.isRegdExciseVender = isRegdExciseVender;
	}
	public String getDlno1() {
		return dlno1;
	}
	public void setDlno1(String dlno1) {
		this.dlno1 = dlno1;
	}
	public String getDlno2() {
		return dlno2;
	}
	public void setDlno2(String dlno2) {
		this.dlno2 = dlno2;
	}
	public String getPaymentTermID() {
		return paymentTermID;
	}
	public void setPaymentTermID(String paymentTermID) {
		this.paymentTermID = paymentTermID;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCustmerType() {
		return custmerType;
	}
	public void setCustmerType(String custmerType) {
		this.custmerType = custmerType;
	}
	public String getTdsStatus() {
		return tdsStatus;
	}
	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	public String getAccounts() {
		return accounts;
	}
	public void setAccounts(String accounts) {
		this.accounts = accounts;
	}
	public String getDomestic() {
		return domestic;
	}
	public void setDomestic(String domestic) {
		this.domestic = domestic;
	}
	public String getExports() {
		return exports;
	}
	public void setExports(String exports) {
		this.exports = exports;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCutomerCode() {
		return cutomerCode;
	}
	public void setCutomerCode(String cutomerCode) {
		this.cutomerCode = cutomerCode;
	}
	public String getSearchRequired() {
		return searchRequired;
	}
	public void setSearchRequired(String searchRequired) {
		this.searchRequired = searchRequired;
	}
	public String getPlantCode_C() {
		return plantCode_C;
	}
	public void setPlantCode_C(String plantCode_C) {
		this.plantCode_C = plantCode_C;
	}
	public String getStorageLocation_C() {
		return storageLocation_C;
	}
	public void setStorageLocation_C(String storageLocation_C) {
		this.storageLocation_C = storageLocation_C;
	}
	public String getServiceDescription_C() {
		return serviceDescription_C;
	}
	public void setServiceDescription_C(String serviceDescription_C) {
		this.serviceDescription_C = serviceDescription_C;
	}
	public String getDetailedServiceDescription_C() {
		return detailedServiceDescription_C;
	}
	public void setDetailedServiceDescription_C(String detailedServiceDescription_C) {
		this.detailedServiceDescription_C = detailedServiceDescription_C;
	}
	public String getUom_C() {
		return uom_C;
	}
	public void setUom_C(String uom_C) {
		this.uom_C = uom_C;
	}
	public String getPurchaseGroup_C() {
		return purchaseGroup_C;
	}
	public void setPurchaseGroup_C(String purchaseGroup_C) {
		this.purchaseGroup_C = purchaseGroup_C;
	}
	public String getServiceCatagory_C() {
		return serviceCatagory_C;
	}
	public void setServiceCatagory_C(String serviceCatagory_C) {
		this.serviceCatagory_C = serviceCatagory_C;
	}
	public String getServiceGroup_C() {
		return serviceGroup_C;
	}
	public void setServiceGroup_C(String serviceGroup_C) {
		this.serviceGroup_C = serviceGroup_C;
	}
	public String getE_m_name_C() {
		return e_m_name_C;
	}
	public void setE_m_name_C(String e_m_name_C) {
		this.e_m_name_C = e_m_name_C;
	}
	public String getApp_amount_C() {
		return app_amount_C;
	}
	public void setApp_amount_C(String app_amount_C) {
		this.app_amount_C = app_amount_C;
	}
	public String getWhereUsed_C() {
		return whereUsed_C;
	}
	public void setWhereUsed_C(String whereUsed_C) {
		this.whereUsed_C = whereUsed_C;
	}
	public String getPurpose_C() {
		return purpose_C;
	}
	public void setPurpose_C(String purpose_C) {
		this.purpose_C = purpose_C;
	}
	public String getJustification_C() {
		return justification_C;
	}
	public void setJustification_C(String justification_C) {
		this.justification_C = justification_C;
	}
	public String getPlantCode() {
		return plantCode;
	}
	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}
	public String getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
	public String getDetailedServiceDescription() {
		return detailedServiceDescription;
	}
	public void setDetailedServiceDescription(String detailedServiceDescription) {
		this.detailedServiceDescription = detailedServiceDescription;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getPurchaseGroup() {
		return purchaseGroup;
	}
	public void setPurchaseGroup(String purchaseGroup) {
		this.purchaseGroup = purchaseGroup;
	}
	public String getServiceCatagory() {
		return serviceCatagory;
	}
	public void setServiceCatagory(String serviceCatagory) {
		this.serviceCatagory = serviceCatagory;
	}
	public String getServiceGroup() {
		return serviceGroup;
	}
	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}
	public String getE_m_name() {
		return e_m_name;
	}
	public void setE_m_name(String e_m_name) {
		this.e_m_name = e_m_name;
	}
	public String getApp_amount() {
		return app_amount;
	}
	public void setApp_amount(String app_amount) {
		this.app_amount = app_amount;
	}
	public String getWhereUsed() {
		return whereUsed;
	}
	public void setWhereUsed(String whereUsed) {
		this.whereUsed = whereUsed;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getJustification() {
		return justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
	public String getDivisionId_C() {
		return divisionId_C;
	}
	public void setDivisionId_C(String divisionId_C) {
		this.divisionId_C = divisionId_C;
	}
	public String getIsSASFormAvailable_C() {
		return isSASFormAvailable_C;
	}
	public void setIsSASFormAvailable_C(String isSASFormAvailable_C) {
		this.isSASFormAvailable_C = isSASFormAvailable_C;
	}
	public String getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(String divisionId) {
		this.divisionId = divisionId;
	}
	public String getIsSASFormAvailable() {
		return isSASFormAvailable;
	}
	public void setIsSASFormAvailable(String isSASFormAvailable) {
		this.isSASFormAvailable = isSASFormAvailable;
	}
	public String getIsNewEquipment() {
		return isNewEquipment;
	}
	public void setIsNewEquipment(String isNewEquipment) {
		this.isNewEquipment = isNewEquipment;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getReqFieldName() {
		return reqFieldName;
	}
	public void setReqFieldName(String reqFieldName) {
		this.reqFieldName = reqFieldName;
	}
	public String getEqName() {
		return eqName;
	}
	public void setEqName(String eqName) {
		this.eqName = eqName;
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
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getEquipIntendedFor() {
		return equipIntendedFor;
	}
	public void setEquipIntendedFor(String equipIntendedFor) {
		this.equipIntendedFor = equipIntendedFor;
	}
	public String getPackSize() {
		return packSize;
	}
	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}
	public String getIsNewEquipment_C() {
		return isNewEquipment_C;
	}
	public void setIsNewEquipment_C(String isNewEquipment_C) {
		this.isNewEquipment_C = isNewEquipment_C;
	}
	public String getSearchText_C() {
		return searchText_C;
	}
	public void setSearchText_C(String searchText_C) {
		this.searchText_C = searchText_C;
	}
	public String getReqFieldName_C() {
		return reqFieldName_C;
	}
	public void setReqFieldName_C(String reqFieldName_C) {
		this.reqFieldName_C = reqFieldName_C;
	}
	public String getEqName_C() {
		return eqName_C;
	}
	public void setEqName_C(String eqName_C) {
		this.eqName_C = eqName_C;
	}
	public String getMoc_C() {
		return moc_C;
	}
	public void setMoc_C(String moc_C) {
		this.moc_C = moc_C;
	}
	public String getRating_C() {
		return rating_C;
	}
	public void setRating_C(String rating_C) {
		this.rating_C = rating_C;
	}
	public String getRange_C() {
		return range_C;
	}
	public void setRange_C(String range_C) {
		this.range_C = range_C;
	}
	public String getDimensions_C() {
		return dimensions_C;
	}
	public void setDimensions_C(String dimensions_C) {
		this.dimensions_C = dimensions_C;
	}
	public String getEquipIntendedFor_C() {
		return equipIntendedFor_C;
	}
	public void setEquipIntendedFor_C(String equipIntendedFor_C) {
		this.equipIntendedFor_C = equipIntendedFor_C;
	}
	public String getPackSize_C() {
		return packSize_C;
	}
	public void setPackSize_C(String packSize_C) {
		this.packSize_C = packSize_C;
	}
	public String getMatType() {
		return matType;
	}
	public void setMatType(String matType) {
		this.matType = matType;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getMaterialGroupId_C() {
		return materialGroupId_C;
	}
	public void setMaterialGroupId_C(String materialGroupId_C) {
		this.materialGroupId_C = materialGroupId_C;
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
	public String getPuchaseGroupId() {
		return puchaseGroupId;
	}
	public void setPuchaseGroupId(String puchaseGroupId) {
		this.puchaseGroupId = puchaseGroupId;
	}
	public String getUnitOfMeasId() {
		return unitOfMeasId;
	}
	public void setUnitOfMeasId(String unitOfMeasId) {
		this.unitOfMeasId = unitOfMeasId;
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
	public String getIsNew() {
		return isNew;
	}
	public void setIsNew(String isNew) {
		this.isNew = isNew;
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
	public String getMaterialCodeLists() {
		return materialCodeLists;
	}
	public void setMaterialCodeLists(String materialCodeLists) {
		this.materialCodeLists = materialCodeLists;
	}
	public String getMaterialCodeLists1() {
		return materialCodeLists1;
	}
	public void setMaterialCodeLists1(String materialCodeLists1) {
		this.materialCodeLists1 = materialCodeLists1;
	}
	public String getTypeDetails() {
		return typeDetails;
	}
	public void setTypeDetails(String typeDetails) {
		this.typeDetails = typeDetails;
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
	public String getApproveType() {
		return approveType;
	}
	public void setApproveType(String approveType) {
		this.approveType = approveType;
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
	public String getIsAsset() {
		return isAsset;
	}
	public void setIsAsset(String isAsset) {
		this.isAsset = isAsset;
	}
	public String getAppStatusMessage() {
		return appStatusMessage;
	}
	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getStorageLocationId_C() {
		return storageLocationId_C;
	}
	public void setStorageLocationId_C(String storageLocationId_C) {
		this.storageLocationId_C = storageLocationId_C;
	}
	public String getMaterialShortName_C() {
		return materialShortName_C;
	}
	public void setMaterialShortName_C(String materialShortName_C) {
		this.materialShortName_C = materialShortName_C;
	}
	public String getMaterialLongName_C() {
		return materialLongName_C;
	}
	public void setMaterialLongName_C(String materialLongName_C) {
		this.materialLongName_C = materialLongName_C;
	}
	public String getPuchaseGroupId_C() {
		return puchaseGroupId_C;
	}
	public void setPuchaseGroupId_C(String puchaseGroupId_C) {
		this.puchaseGroupId_C = puchaseGroupId_C;
	}
	public String getUnitOfMeasId_C() {
		return unitOfMeasId_C;
	}
	public void setUnitOfMeasId_C(String unitOfMeasId_C) {
		this.unitOfMeasId_C = unitOfMeasId_C;
	}
	public String getApproximateValue_C() {
		return approximateValue_C;
	}
	public void setApproximateValue_C(String approximateValue_C) {
		this.approximateValue_C = approximateValue_C;
	}
	public String getValuationClass_C() {
		return valuationClass_C;
	}
	public void setValuationClass_C(String valuationClass_C) {
		this.valuationClass_C = valuationClass_C;
	}
	public String getMaterialUsedIn_C() {
		return materialUsedIn_C;
	}
	public void setMaterialUsedIn_C(String materialUsedIn_C) {
		this.materialUsedIn_C = materialUsedIn_C;
	}
	public String getIsEquipment_C() {
		return isEquipment_C;
	}
	public void setIsEquipment_C(String isEquipment_C) {
		this.isEquipment_C = isEquipment_C;
	}
	public String getIsSpare_C() {
		return isSpare_C;
	}
	public void setIsSpare_C(String isSpare_C) {
		this.isSpare_C = isSpare_C;
	}
	public String getIsNew_C() {
		return isNew_C;
	}
	public void setIsNew_C(String isNew_C) {
		this.isNew_C = isNew_C;
	}
	public String getIsItNewFurniture_C() {
		return isItNewFurniture_C;
	}
	public void setIsItNewFurniture_C(String isItNewFurniture_C) {
		this.isItNewFurniture_C = isItNewFurniture_C;
	}
	public String getIsItFacility_C() {
		return isItFacility_C;
	}
	public void setIsItFacility_C(String isItFacility_C) {
		this.isItFacility_C = isItFacility_C;
	}
	public String getIsSpareNewEquipment_C() {
		return isSpareNewEquipment_C;
	}
	public void setIsSpareNewEquipment_C(String isSpareNewEquipment_C) {
		this.isSpareNewEquipment_C = isSpareNewEquipment_C;
	}
	public String getEquipmentName_C() {
		return equipmentName_C;
	}
	public void setEquipmentName_C(String equipmentName_C) {
		this.equipmentName_C = equipmentName_C;
	}
	public String getPrNumber_C() {
		return prNumber_C;
	}
	public void setPrNumber_C(String prNumber_C) {
		this.prNumber_C = prNumber_C;
	}
	public String getPoNumber_C() {
		return poNumber_C;
	}
	public void setPoNumber_C(String poNumber_C) {
		this.poNumber_C = poNumber_C;
	}
	public String getUtilizingDept_C() {
		return utilizingDept_C;
	}
	public void setUtilizingDept_C(String utilizingDept_C) {
		this.utilizingDept_C = utilizingDept_C;
	}
	public String getDetailedJustification_C() {
		return detailedJustification_C;
	}
	public void setDetailedJustification_C(String detailedJustification_C) {
		this.detailedJustification_C = detailedJustification_C;
	}
	public String getDetailedSpecification_C() {
		return detailedSpecification_C;
	}
	public void setDetailedSpecification_C(String detailedSpecification_C) {
		this.detailedSpecification_C = detailedSpecification_C;
	}
	public String getPurposeID_C() {
		return purposeID_C;
	}
	public void setPurposeID_C(String purposeID_C) {
		this.purposeID_C = purposeID_C;
	}
	public String getSapCodeNo_C() {
		return sapCodeNo_C;
	}
	public void setSapCodeNo_C(String sapCodeNo_C) {
		this.sapCodeNo_C = sapCodeNo_C;
	}
	public String getSapCodeExists_C() {
		return sapCodeExists_C;
	}
	public void setSapCodeExists_C(String sapCodeExists_C) {
		this.sapCodeExists_C = sapCodeExists_C;
	}
	public String getSapCreationDate_C() {
		return sapCreationDate_C;
	}
	public void setSapCreationDate_C(String sapCreationDate_C) {
		this.sapCreationDate_C = sapCreationDate_C;
	}
	public String getSapCreatedBy_C() {
		return sapCreatedBy_C;
	}
	public void setSapCreatedBy_C(String sapCreatedBy_C) {
		this.sapCreatedBy_C = sapCreatedBy_C;
	}
	public String getRequestedBy_C() {
		return requestedBy_C;
	}
	public void setRequestedBy_C(String requestedBy_C) {
		this.requestedBy_C = requestedBy_C;
	}
	public String getMaterialCodeLists_C() {
		return materialCodeLists_C;
	}
	public void setMaterialCodeLists_C(String materialCodeLists_C) {
		this.materialCodeLists_C = materialCodeLists_C;
	}
	public String getMaterialCodeLists1_C() {
		return materialCodeLists1_C;
	}
	public void setMaterialCodeLists1_C(String materialCodeLists1_C) {
		this.materialCodeLists1_C = materialCodeLists1_C;
	}
	public String getTypeDetails_C() {
		return typeDetails_C;
	}
	public void setTypeDetails_C(String typeDetails_C) {
		this.typeDetails_C = typeDetails_C;
	}
	public String getEquipmentMake_C() {
		return equipmentMake_C;
	}
	public void setEquipmentMake_C(String equipmentMake_C) {
		this.equipmentMake_C = equipmentMake_C;
	}
	public String getComponentMake_C() {
		return componentMake_C;
	}
	public void setComponentMake_C(String componentMake_C) {
		this.componentMake_C = componentMake_C;
	}
	public String getOemPartNo_C() {
		return oemPartNo_C;
	}
	public void setOemPartNo_C(String oemPartNo_C) {
		this.oemPartNo_C = oemPartNo_C;
	}
	public String getIsAsset_C() {
		return isAsset_C;
	}
	public void setIsAsset_C(String isAsset_C) {
		this.isAsset_C = isAsset_C;
	}
	public String getRole_C() {
		return role_C;
	}
	public void setRole_C(String role_C) {
		this.role_C = role_C;
	}
	
	
	

}
