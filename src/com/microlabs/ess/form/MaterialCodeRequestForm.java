package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class MaterialCodeRequestForm extends ActionForm{

	
	private String materialCodeLists;
	private String materialName;
	private String serviceDescription;
	private String requestNumber;
	private String requestDate;
	private String approveStatus;
	private String locationId;
	private String materialShortName;
	private String approveType;
	private String message;
	private String message2;
	private String sendMessage;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;

	private int startRecord1;
	private int endRecord2;
	private String reportUrl;
	
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	
	private String materialCodeLists1;
	private String materailType;
	private String materialDesc;
	private String locationCode;
	private String employeeName;
	private String lastApprover;
	private String pendingApprover;
	private String materialGroup;
	private String reqMatType;
	private String[] getReqno;
	private String sapCodeNo;
	private String itemCodeExist;
	private String materialGroupName;
	private String uom;
	private String packSize;
	private String sapCreationDate;
	private String selectedFilter;
	private String itemExist;
	private String shortName;
	private String longName;
	private String codeNo;
	private String MaterialTypeDesc;
	private String materialGrup;
	private String materialType;
	private String createdOn;
	private String requestedOn;
	private String approximatePrice;
	
	private int totalSearchRecords;
	private int startSearchRecord;
	private int endSearchRecord;
	
	private String requesterName;
	
	private String approversList;
	private String codeCreationDate;
	private String appStatusMessage;
	
	private String prevApprover;
	private String prevApproverDate;
	
	
	
	
	public String getPrevApprover() {
		return prevApprover;
	}

	public void setPrevApprover(String prevApprover) {
		this.prevApprover = prevApprover;
	}

	public String getPrevApproverDate() {
		return prevApproverDate;
	}

	public void setPrevApproverDate(String prevApproverDate) {
		this.prevApproverDate = prevApproverDate;
	}

	public String getAppStatusMessage() {
		return appStatusMessage;
	}

	public void setAppStatusMessage(String appStatusMessage) {
		this.appStatusMessage = appStatusMessage;
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

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}

	public int getTotalSearchRecords() {
		return totalSearchRecords;
	}

	public void setTotalSearchRecords(int totalSearchRecords) {
		this.totalSearchRecords = totalSearchRecords;
	}

	public int getStartSearchRecord() {
		return startSearchRecord;
	}

	public void setStartSearchRecord(int startSearchRecord) {
		this.startSearchRecord = startSearchRecord;
	}

	public int getEndSearchRecord() {
		return endSearchRecord;
	}

	public void setEndSearchRecord(int endSearchRecord) {
		this.endSearchRecord = endSearchRecord;
	}

	public String getApproximatePrice() {
		return approximatePrice;
	}

	public void setApproximatePrice(String approximatePrice) {
		this.approximatePrice = approximatePrice;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getRequestedOn() {
		return requestedOn;
	}

	public void setRequestedOn(String requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	

	public String getMaterialTypeDesc() {
		return MaterialTypeDesc;
	}

	public void setMaterialTypeDesc(String materialTypeDesc) {
		MaterialTypeDesc = materialTypeDesc;
	}

	public String getMaterialGrup() {
		return materialGrup;
	}

	public void setMaterialGrup(String materialGrup) {
		this.materialGrup = materialGrup;
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

	public String getItemExist() {
		return itemExist;
	}

	public void setItemExist(String itemExist) {
		this.itemExist = itemExist;
	}

	public String getSelectedFilter() {
		return selectedFilter;
	}

	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}

	public String getSapCreationDate() {
		return sapCreationDate;
	}

	public void setSapCreationDate(String sapCreationDate) {
		this.sapCreationDate = sapCreationDate;
	}

	public String getPackSize() {
		return packSize;
	}

	public void setPackSize(String packSize) {
		this.packSize = packSize;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getMaterialGroupName() {
		return materialGroupName;
	}

	public void setMaterialGroupName(String materialGroupName) {
		this.materialGroupName = materialGroupName;
	}

	public String getSapCodeNo() {
		return sapCodeNo;
	}

	public void setSapCodeNo(String sapCodeNo) {
		this.sapCodeNo = sapCodeNo;
	}

	public String getItemCodeExist() {
		return itemCodeExist;
	}

	public void setItemCodeExist(String itemCodeExist) {
		this.itemCodeExist = itemCodeExist;
	}

	public String getMaterialDesc() {
		return materialDesc;
	}

	public void setMaterialDesc(String materialDesc) {
		this.materialDesc = materialDesc;
	}

	public String[] getGetReqno() {
		return getReqno;
	}

	public void setGetReqno(String[] getReqno) {
		this.getReqno = getReqno;
	}

	public String getReqMatType() {
		return reqMatType;
	}

	public void setReqMatType(String reqMatType) {
		this.reqMatType = reqMatType;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
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

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getMaterailType() {
		return materailType;
	}

	public void setMaterailType(String materailType) {
		this.materailType = materailType;
	}

	public String getMaterialCodeLists1() {
		return materialCodeLists1;
	}

	public void setMaterialCodeLists1(String materialCodeLists1) {
		this.materialCodeLists1 = materialCodeLists1;
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

	public String getReportUrl() {
		return reportUrl;
	}

	public void setReportUrl(String reportUrl) {
		this.reportUrl = reportUrl;
	}

	public int getStartRecord1() {
		return startRecord1;
	}

	public void setStartRecord1(int startRecord1) {
		this.startRecord1 = startRecord1;
	}

	public int getEndRecord2() {
		return endRecord2;
	}

	public void setEndRecord2(int endRecord2) {
		this.endRecord2 = endRecord2;
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

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public String getMType() {
		return mType;
	}

	public void setMType(String type) {
		mType = type;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getmType() {
		return mType;
	}

	public void setmType(String mType) {
		this.mType = mType;
	}

	private String fromDate;
	private String toDate;
	private String mcode;
	private String mType;
	
	public String getMcode() {
		return mcode;
	}

	public void setMcode(String mcode) {
		this.mcode = mcode;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private String requiredRequestNumber;
	private String[] chRequestNumber;
	public String[] getChRequestNumber() {
		return chRequestNumber;
	}

	public void setChRequestNumber(String[] chRequestNumber) {
		this.chRequestNumber = chRequestNumber;
	}

	private String url;
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	
	
	

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequiredRequestNumber() {
		return requiredRequestNumber;
	}

	public void setRequiredRequestNumber(String requiredRequestNumber) {
		this.requiredRequestNumber = requiredRequestNumber;
	}

	public String getApproveType() {
		return approveType;
	}

	public void setApproveType(String approveType) {
		this.approveType = approveType;
	}

	public String getMaterialShortName() {
		return materialShortName;
	}

	public void setMaterialShortName(String materialShortName) {
		this.materialShortName = materialShortName;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
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

	public String getMaterialCodeLists() {
		return materialCodeLists;
	}

	public void setMaterialCodeLists(String materialCodeLists) {
		this.materialCodeLists = materialCodeLists;
	}
	
	
	
	
}
