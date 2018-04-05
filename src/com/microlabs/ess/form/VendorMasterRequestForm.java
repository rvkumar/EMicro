package com.microlabs.ess.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class VendorMasterRequestForm extends ActionForm{
	
	
	private int requestNo;
	private String accountGroupId;
	private String requestDate;
	private String purchaseView;
	private String title;
	private String name;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String pinCode;
	private String country;
	private String state;
	private String landLineNo;
	private String mobileNo;
	private String faxNo;
	private String emailId;
	private String currencyId;
	private String reConcillationActId;
	private String elgTds;
	private String tdsCode;
	private String lstNo;
	private String tinNo;
	private String cstNo;
	private String panNo;
	private String serviceTaxRegNo;
	private String regExciseVendor;
	private String eccNo;
	private String exciseRegNo;
	private String linkName;
	private String exciseRange;
	private String exciseDivision;
	private String paymentTermId;
	private String accountClerkId;
	private String isApprovedVendor;
	private FormFile vendorAttachments;
	private String contentDescription;
	private String fileFullPath;
	private String videoFullPath;

	private String commissionerate;
	private String typeOfVendor;

	private String fileName;
	private String fileId;
	private String filepath;
	private String message;
	private String message1;
	private String[] documentCheck;
	private String approveType;
	private String[] requiredRequestNumber;
	private String[] getReqno;
	
	
	private String accountView;
	
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCreationDate;
	private String sapCreatedBy;
	private String requestedBy;
	private String department;
	private String locationId;
	private String searchRequired;
	private String sapCodeExistsNo;
	
	private ArrayList accountGroupList=new ArrayList();
	private ArrayList accountGroupLabelList=new ArrayList();
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private ArrayList countryList=new ArrayList();
	private ArrayList countryLabelList=new ArrayList();
	
	private ArrayList stateList=new ArrayList();
	private ArrayList stateLabelList=new ArrayList();
	
	private ArrayList currencyList=new ArrayList();
	private ArrayList currencyLabelList=new ArrayList();
	
	private ArrayList reConcillationList=new ArrayList();
	private ArrayList reConcillationLabelList=new ArrayList();
	
	private ArrayList paymentTermList=new ArrayList();
	private ArrayList paymentTermLabelList=new ArrayList();
	
	private ArrayList accountClerkList=new ArrayList();
	private ArrayList accountClerkLabelList=new ArrayList();
	
	private ArrayList tdsIdList=new ArrayList();
	private ArrayList tdsLabelList=new ArrayList();
	
	
	private String requestNumber;
	private String requestsearchDate;
	private String locationSearch;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String typeDetails;
	private String message2;
	
	
	private String name1;
	private String city1;
	private String country1;
	private String appStatusMessage;
	
	private int totalCodeSearch;
	private int codeStartRecord;
	private int codeEndRecord;
	private String lastApprover;
	private String pendingApprover;
	private String approversList;
	private String codeCreationDate;
	
	private String gstinNo;
	
	
	
	
	
	
	public String getGstinNo() {
		return gstinNo;
	}
	public void setGstinNo(String gstinNo) {
		this.gstinNo = gstinNo;
	}
	public String getApproversList() {
		return approversList;
	}
	public void setApproversList(String approversList) {
		this.approversList = approversList;
	}
	public String getCodeCreationDate() {
		return codeCreationDate;
	}
	public void setCodeCreationDate(String codeCreationDate) {
		this.codeCreationDate = codeCreationDate;
	}
	public String getLastApprover() {
		return lastApprover;
	}
	public void setLastApprover(String lastApprover) {
		this.lastApprover = lastApprover;
	}
	public String getPendingApprover() {
		return pendingApprover;
	}
	public void setPendingApprover(String pendingApprover) {
		this.pendingApprover = pendingApprover;
	}
	public int getTotalCodeSearch() {
		return totalCodeSearch;
	}
	public void setTotalCodeSearch(int totalCodeSearch) {
		this.totalCodeSearch = totalCodeSearch;
	}
	public int getCodeStartRecord() {
		return codeStartRecord;
	}
	public void setCodeStartRecord(int codeStartRecord) {
		this.codeStartRecord = codeStartRecord;
	}
	public int getCodeEndRecord() {
		return codeEndRecord;
	}
	public void setCodeEndRecord(int codeEndRecord) {
		this.codeEndRecord = codeEndRecord;
	}
	public String getAppStatusMessage() {
		return appStatusMessage;
	}
	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public String getSapCodeExistsNo() {
		return sapCodeExistsNo;
	}
	public void setSapCodeExistsNo(String sapCodeExistsNo) {
		this.sapCodeExistsNo = sapCodeExistsNo;
	}
	public String getMessage1() {
		return message1;
	}
	public void setMessage1(String message1) {
		this.message1 = message1;
	}
	public String getSearchRequired() {
		return searchRequired;
	}
	public void setSearchRequired(String searchRequired) {
		this.searchRequired = searchRequired;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public String getTypeDetails() {
		return typeDetails;
	}
	public void setTypeDetails(String typeDetails) {
		this.typeDetails = typeDetails;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String[] getRequiredRequestNumber() {
		return requiredRequestNumber;
	}
	public void setRequiredRequestNumber(String[] requiredRequestNumber) {
		this.requiredRequestNumber = requiredRequestNumber;
	}
	public String getCommissionerate() {
		return commissionerate;
	}
	public void setCommissionerate(String commissionerate) {
		this.commissionerate = commissionerate;
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
	public ArrayList getTdsIdList() {
		return tdsIdList;
	}
	public void setTdsIdList(ArrayList tdsIdList) {
		this.tdsIdList = tdsIdList;
	}
	public ArrayList getTdsLabelList() {
		return tdsLabelList;
	}
	public void setTdsLabelList(ArrayList tdsLabelList) {
		this.tdsLabelList = tdsLabelList;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
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
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getAccountView() {
		return accountView;
	}
	public void setAccountView(String accountView) {
		this.accountView = accountView;
	}
	public ArrayList getAccountGroupList() {
		return accountGroupList;
	}
	public void setAccountGroupList(ArrayList accountGroupList) {
		this.accountGroupList = accountGroupList;
	}
	public ArrayList getAccountGroupLabelList() {
		return accountGroupLabelList;
	}
	public void setAccountGroupLabelList(ArrayList accountGroupLabelList) {
		this.accountGroupLabelList = accountGroupLabelList;
	}
	public ArrayList getCountryList() {
		return countryList;
	}
	public void setCountryList(ArrayList countryList) {
		this.countryList = countryList;
	}
	public ArrayList getCountryLabelList() {
		return countryLabelList;
	}
	public void setCountryLabelList(ArrayList countryLabelList) {
		this.countryLabelList = countryLabelList;
	}
	public ArrayList getStateList() {
		return stateList;
	}
	public void setStateList(ArrayList stateList) {
		this.stateList = stateList;
	}
	public ArrayList getStateLabelList() {
		return stateLabelList;
	}
	public void setStateLabelList(ArrayList stateLabelList) {
		this.stateLabelList = stateLabelList;
	}
	public ArrayList getCurrencyList() {
		return currencyList;
	}
	public void setCurrencyList(ArrayList currencyList) {
		this.currencyList = currencyList;
	}
	public ArrayList getCurrencyLabelList() {
		return currencyLabelList;
	}
	public void setCurrencyLabelList(ArrayList currencyLabelList) {
		this.currencyLabelList = currencyLabelList;
	}
	public ArrayList getReConcillationList() {
		return reConcillationList;
	}
	public void setReConcillationList(ArrayList reConcillationList) {
		this.reConcillationList = reConcillationList;
	}
	public ArrayList getReConcillationLabelList() {
		return reConcillationLabelList;
	}
	public void setReConcillationLabelList(ArrayList reConcillationLabelList) {
		this.reConcillationLabelList = reConcillationLabelList;
	}
	public ArrayList getPaymentTermList() {
		return paymentTermList;
	}
	public void setPaymentTermList(ArrayList paymentTermList) {
		this.paymentTermList = paymentTermList;
	}
	public ArrayList getPaymentTermLabelList() {
		return paymentTermLabelList;
	}
	public void setPaymentTermLabelList(ArrayList paymentTermLabelList) {
		this.paymentTermLabelList = paymentTermLabelList;
	}
	public ArrayList getAccountClerkList() {
		return accountClerkList;
	}
	public void setAccountClerkList(ArrayList accountClerkList) {
		this.accountClerkList = accountClerkList;
	}
	public ArrayList getAccountClerkLabelList() {
		return accountClerkLabelList;
	}
	public void setAccountClerkLabelList(ArrayList accountClerkLabelList) {
		this.accountClerkLabelList = accountClerkLabelList;
	}
	public String[] getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public FormFile getVendorAttachments() {
		return vendorAttachments;
	}
	public void setVendorAttachments(FormFile vendorAttachments) {
		this.vendorAttachments = vendorAttachments;
	}
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
	}
	public String getAccountGroupId() {
		return accountGroupId;
	}
	public void setAccountGroupId(String accountGroupId) {
		this.accountGroupId = accountGroupId;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getPurchaseView() {
		return purchaseView;
	}
	public void setPurchaseView(String purchaseView) {
		this.purchaseView = purchaseView;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLandLineNo() {
		return landLineNo;
	}
	public void setLandLineNo(String landLineNo) {
		this.landLineNo = landLineNo;
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
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
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
	public String getTdsCode() {
		return tdsCode;
	}
	public void setTdsCode(String tdsCode) {
		this.tdsCode = tdsCode;
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
	public String getPaymentTermId() {
		return paymentTermId;
	}
	public void setPaymentTermId(String paymentTermId) {
		this.paymentTermId = paymentTermId;
	}
	public String getAccountClerkId() {
		return accountClerkId;
	}
	public void setAccountClerkId(String accountClerkId) {
		this.accountClerkId = accountClerkId;
	}
	public String getIsApprovedVendor() {
		return isApprovedVendor;
	}
	public void setIsApprovedVendor(String isApprovedVendor) {
		this.isApprovedVendor = isApprovedVendor;
	}
	public String getTypeOfVendor() {
		return typeOfVendor;
	}
	public void setTypeOfVendor(String typeOfVendor) {
		this.typeOfVendor = typeOfVendor;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String[] getGetReqno() {
		return getReqno;
	}
	public void setGetReqno(String[] getReqno) {
		this.getReqno = getReqno;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getCity1() {
		return city1;
	}
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	public String getCountry1() {
		return country1;
	}
	public void setCountry1(String country1) {
		this.country1 = country1;
	}

	
	
	
}