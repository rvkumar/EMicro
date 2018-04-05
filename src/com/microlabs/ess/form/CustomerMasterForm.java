package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class CustomerMasterForm extends ActionForm{
	
	
	private int requestNumber;
	private String requestDate;
	private String accGroupId;
	private String viewType;
	private String customerName;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String pincode;
	private String countryId;
	private String state;
	private String landlineNo;
	private String mobileNo;
	private String faxNo;
	private String emailId;
	private String customerGroup;
	private String priceGroup;
	private String priceList;
	private String taxType;
	private String currencyId;
	private String tdsCode;
	private String listNumber;
	
	private String tinNumber;
	private String cstNumber;
	private String panNumber;
	private String serviceTaxNo;
	private String isRegdExciseVender;
	private String exciseRegNo;
	private String exciseRange;
	private String exciseDivision;
	private String dlno1;
	private String dlno2;
	private String paymentTermID;
	private String accountClerkId;
	private String createdDate;
	private String createdBy;
	private String modifiedDate;
	private String message;
	private String custmerType;
	private String eccNo;
	private String tdsStatus;
	private String requestNo;
	private String requestsearchDate;
	private String locationSearch;
	private String sales;
	private String accounts;
	private String domestic;
	private String exports;
	private String customerType;
	private String approveType;
	private String searchRequired;
	private String gstinNo;
	
	private String[] requiredRequestNumber;
	private String[] getReqno;
	
	private ArrayList accountClerkList=new ArrayList();
	private ArrayList accountClerkLabelList=new ArrayList();
	private ArrayList accountGroupList=new ArrayList();
	private ArrayList accountGroupLabelList=new ArrayList();
	

	
	private LinkedList cusGroupID;
	private LinkedList cusGroupList;
	
	private LinkedList priceGroupID;
	private LinkedList piceGroupList;
	
	private LinkedList priceListID;
	private LinkedList piceListValue;
	
	private LinkedList taxTypeID;
	private LinkedList taxTypeValue;
	
	private ArrayList paymentTermList=new ArrayList();
	private ArrayList paymentTermLabelList=new ArrayList();
	
	
	private LinkedList counID;
	private LinkedList countryName;
	
	private LinkedList stateId;
	private LinkedList states;
	
	private LinkedList currenIds;
	private LinkedList currencys;
	
	private LinkedList tdsIds;
	private LinkedList tdsCodes;
	
	
	FormFile fileNames;
	
	
	
	private String selecteRequestNo;
	private String fileList;
	private String filepath;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String typeDetails;
	
	private LinkedList uploadedFilesList;
	private String sapCodeNo;
	private String sapCodeExists;
	private String sapCodeExistsNo;
	private String sapCreationDate;
	private String sapCreatedBy;
	private String requestedBy;
	private String department;
	private String locationId;
	private String cutomerCode;
	private String message1;
	private String message2;
	
	
	private String customerName1;
	private String city1;
	private String countryId1;
	private String lastApprover;
	private String pendingApprover;
	private String approversList;
	private String codeCreationDate;
	private String appStatusMessage;
	
	
	private int totalCodeSearch;
	private int codeStartRecord;
	private int codeEndRecord;
	
	
	
	
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
	public String getCutomerCode() {
		return cutomerCode;
	}
	public void setCutomerCode(String cutomerCode) {
		this.cutomerCode = cutomerCode;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getSearchRequired() {
		return searchRequired;
	}
	public void setSearchRequired(String searchRequired) {
		this.searchRequired = searchRequired;
	}
	public LinkedList getPriceGroupID() {
		return priceGroupID;
	}
	public void setPriceGroupID(LinkedList priceGroupID) {
		this.priceGroupID = priceGroupID;
	}
	public LinkedList getPiceGroupList() {
		return piceGroupList;
	}
	public void setPiceGroupList(LinkedList piceGroupList) {
		this.piceGroupList = piceGroupList;
	}
	public LinkedList getPriceListID() {
		return priceListID;
	}
	public void setPriceListID(LinkedList priceListID) {
		this.priceListID = priceListID;
	}
	public LinkedList getPiceListValue() {
		return piceListValue;
	}
	public void setPiceListValue(LinkedList piceListValue) {
		this.piceListValue = piceListValue;
	}
	public LinkedList getTaxTypeID() {
		return taxTypeID;
	}
	public void setTaxTypeID(LinkedList taxTypeID) {
		this.taxTypeID = taxTypeID;
	}
	public LinkedList getTaxTypeValue() {
		return taxTypeValue;
	}
	public void setTaxTypeValue(LinkedList taxTypeValue) {
		this.taxTypeValue = taxTypeValue;
	}
	public LinkedList getCusGroupID() {
		return cusGroupID;
	}
	public void setCusGroupID(LinkedList cusGroupID) {
		this.cusGroupID = cusGroupID;
	}
	public LinkedList getCusGroupList() {
		return cusGroupList;
	}
	public void setCusGroupList(LinkedList cusGroupList) {
		this.cusGroupList = cusGroupList;
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
	public LinkedList getUploadedFilesList() {
		return uploadedFilesList;
	}
	public void setUploadedFilesList(LinkedList uploadedFilesList) {
		this.uploadedFilesList = uploadedFilesList;
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
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
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
	public String[] getRequiredRequestNumber() {
		return requiredRequestNumber;
	}
	public void setRequiredRequestNumber(String[] requiredRequestNumber) {
		this.requiredRequestNumber = requiredRequestNumber;
	}
	public String getSelecteRequestNo() {
		return selecteRequestNo;
	}
	public void setSelecteRequestNo(String selecteRequestNo) {
		this.selecteRequestNo = selecteRequestNo;
	}
	public int getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
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
	public String getTdsStatus() {
		return tdsStatus;
	}
	public void setTdsStatus(String tdsStatus) {
		this.tdsStatus = tdsStatus;
	}
	public LinkedList getTdsIds() {
		return tdsIds;
	}
	public void setTdsIds(LinkedList tdsIds) {
		this.tdsIds = tdsIds;
	}
	public LinkedList getTdsCodes() {
		return tdsCodes;
	}
	public void setTdsCodes(LinkedList tdsCodes) {
		this.tdsCodes = tdsCodes;
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
	public FormFile getFileNames() {
		return fileNames;
	}
	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
	}
	public LinkedList getCurrenIds() {
		return currenIds;
	}
	public void setCurrenIds(LinkedList currenIds) {
		this.currenIds = currenIds;
	}
	public LinkedList getCurrencys() {
		return currencys;
	}
	public void setCurrencys(LinkedList currencys) {
		this.currencys = currencys;
	}
	public LinkedList getStateId() {
		return stateId;
	}
	public void setStateId(LinkedList stateId) {
		this.stateId = stateId;
	}
	public LinkedList getStates() {
		return states;
	}
	public void setStates(LinkedList states) {
		this.states = states;
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
	
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
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
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLandlineNo() {
		return landlineNo;
	}
	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
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
	public String getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId;
	}
	public String getTdsCode() {
		return tdsCode;
	}
	public void setTdsCode(String tdsCode) {
		this.tdsCode = tdsCode;
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
	public String getAccountClerkId() {
		return accountClerkId;
	}
	public void setAccountClerkId(String accountClerkId) {
		this.accountClerkId = accountClerkId;
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
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getCustmerType() {
		return custmerType;
	}
	public void setCustmerType(String custmerType) {
		this.custmerType = custmerType;
	}
	public String getEccNo() {
		return eccNo;
	}
	public void setEccNo(String eccNo) {
		this.eccNo = eccNo;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
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
	public String[] getGetReqno() {
		return getReqno;
	}
	public void setGetReqno(String[] getReqno) {
		this.getReqno = getReqno;
	}
	public String getCustomerName1() {
		return customerName1;
	}
	public void setCustomerName1(String customerName1) {
		this.customerName1 = customerName1;
	}
	public String getCity1() {
		return city1;
	}
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	public String getCountryId1() {
		return countryId1;
	}
	public void setCountryId1(String countryId1) {
		this.countryId1 = countryId1;
	}

	
}
