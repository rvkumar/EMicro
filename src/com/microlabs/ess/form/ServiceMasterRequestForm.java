package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ServiceMasterRequestForm extends ActionForm {
	
	private String requestDate;
	private String materialtype;
	private String material_des;
	private String materialGroup;
	private String message;
	private String materialPlant;
	private String locationID;
	
	private String serviceRequestDate;
	
	//private String serviceDescription;
	//private String serviceGroup;
	private String servicePlant;
	private String serviceSatus;
	private String serviceRequestedBy;
	private String serviceApproveDate;
	
	private String customerRequestDate;
	private String customerName;
	private String customerLocation;
	private String c_country;
	private String c_customerGroup;
	private String c_status;
	
	private int r_no;
	private int[] requiredRequestNumber;
	private String[] getReqno;
	
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
	private String valuationClass;
	private FormFile serviceAttachment;
	private String approveStatus;
	private String requestedBy;
	private String approveDate;
	private String typeDetails;
	//private String requestedBy;
	//private String approveDate;
	private String[] documentCheck;
	private String filepath;
	
	private String u_id;
	private String u_label;
	
	private String p_id;
	private String p_all;
	
	private String pl_id;
	private String pl_all;
	
	private String service_id;
	private String service_label;
	private String sfile;
	
	private LinkedList<Object> uomId;
	private LinkedList uomLabel;
	
	private LinkedList pid;
	private LinkedList plabel;
	
	private LinkedList vid;
	private LinkedList vlabel;
	
	private LinkedList pcode; 
	
	
	private LinkedList sgroupLabel;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private LinkedList unitOfMeasIdList;
	private LinkedList unitOfMeasIdValues;
	
	private LinkedList serviceGroupID;
	private LinkedList serviceGroupValues;
	
	private int total;
	private int next;
	private int prev;
	private int startRecord;
	private int endRecord;
	private String appStatusMessage;
	
	private int totalCodeSearch;
	private int codeStartRecord;
	private int codeEndRecord;
	
	private String lastApprover;
	private String pendingApprover;
	private String approversList;
	private String codeCreationDate;
	private String sacCode;
	
	
	
	
public String getSacCode() {
		return sacCode;
	}

	public void setSacCode(String sacCode) {
		this.sacCode = sacCode;
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

private String approveType;
private String sapCodeNo;
private String sapCodeExistsNo;
private String sapCodeExists;
private String sapCreationDate;
private String sapCreatedBy;
private LinkedList puchaseGroupIdList;
private LinkedList puchaseGroupIdValues;
private LinkedList valuationClassID;
private LinkedList valuationClassName;
private String message1;
private String message2;

private String serviceDescription1;
private String serviceGroup1;
private String requestno;


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

	public String getLocationID() {
	return locationID;
}

public void setLocationID(String locationID) {
	this.locationID = locationID;
}

	public String getMessage1() {
	return message1;
}

public void setMessage1(String message1) {
	this.message1 = message1;
}

	public LinkedList getServiceGroupID() {
	return serviceGroupID;
}

public void setServiceGroupID(LinkedList serviceGroupID) {
	this.serviceGroupID = serviceGroupID;
}

public LinkedList getServiceGroupValues() {
	return serviceGroupValues;
}

public void setServiceGroupValues(LinkedList serviceGroupValues) {
	this.serviceGroupValues = serviceGroupValues;
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

	public String getTypeDetails() {
	return typeDetails;
}

public void setTypeDetails(String typeDetails) {
	this.typeDetails = typeDetails;
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

	public String getApproveType() {
	return approveType;
}

public void setApproveType(String approveType) {
	this.approveType = approveType;
}

	public FormFile getServiceAttachment() {
		return serviceAttachment;
	}

	public void setServiceAttachment(FormFile serviceAttachment) {
		this.serviceAttachment = serviceAttachment;
	}

	public String getValuationClass() {
		return valuationClass;
	}

	public void setValuationClass(String valuationClass) {
		this.valuationClass = valuationClass;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
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

	public String getApp_amount() {
		return app_amount;
	}

	public void setApp_amount(String app_amount) {
		this.app_amount = app_amount;
	}

	public String getE_m_name() {
		return e_m_name;
	}

	public void setE_m_name(String e_m_name) {
		this.e_m_name = e_m_name;
	}

	public String getPurchaseGroup() {
		return purchaseGroup;
	}

	public void setPurchaseGroup(String purchaseGroup) {
		this.purchaseGroup = purchaseGroup;
	}

	public String getUom() {
		return uom;
	}

	public void setUom(String uom) {
		this.uom = uom;
	}

	public String getDetailedServiceDescription() {
		return detailedServiceDescription;
	}

	public void setDetailedServiceDescription(String detailedServiceDescription) {
		this.detailedServiceDescription = detailedServiceDescription;
	}

	
	
	public String getPlantCode() {
		return plantCode;
	}

	public void setPlantCode(String plantCode) {
		this.plantCode = plantCode;
	}

	public String getC_status() {
		return c_status;
	}

	public void setC_status(String c_status) {
		this.c_status = c_status;
	}

	public String getC_customerGroup() {
		return c_customerGroup;
	}

	public void setC_customerGroup(String group) {
		c_customerGroup = group;
	}

	public String getC_country() {
		return c_country;
	}

	public void setC_country(String c_country) {
		this.c_country = c_country;
	}

	public String getCustomerLocation() {
		return customerLocation;
	}

	public void setCustomerLocation(String customerLocation) {
		this.customerLocation = customerLocation;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerRequestDate() {
		return customerRequestDate;
	}

	public void setCustomerRequestDate(String customerRequestDate) {
		this.customerRequestDate = customerRequestDate;
	}

	public String getServiceApproveDate() {
		return serviceApproveDate;
	}

	public void setServiceApproveDate(String serviceApproveDate) {
		this.serviceApproveDate = serviceApproveDate;
	}

	public String getServiceRequestedBy() {
		return serviceRequestedBy;
	}

	public void setServiceRequestedBy(String serviceRequestedBy) {
		this.serviceRequestedBy = serviceRequestedBy;
	}

	public String getServiceSatus() {
		return serviceSatus;
	}

	public void setServiceSatus(String serviceSatus) {
		this.serviceSatus = serviceSatus;
	}

	public String getServiceGroup() {
		return serviceGroup;
	}

	public void setServiceGroup(String serviceGroup) {
		this.serviceGroup = serviceGroup;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getServiceRequestDate() {
		return serviceRequestDate;
	}

	public void setServiceRequestDate(String serviceRequestDate) {
		this.serviceRequestDate = serviceRequestDate;
	}

	public String getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
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

	public String getMaterial_des() {
		return material_des;
	}

	public void setMaterial_des(String material_des) {
		this.material_des = material_des;
	}

	public String getMaterialtype() {
		return materialtype;
	}

	public void setMaterialtype(String materialtype) {
		this.materialtype = materialtype;
	}

	

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getServiceCatagory() {
		return serviceCatagory;
	}

	public void setServiceCatagory(String serviceCatagory) {
		this.serviceCatagory = serviceCatagory;
	}

	public String getMaterialPlant() {
		return materialPlant;
	}

	public void setMaterialPlant(String materialPlant) {
		this.materialPlant = materialPlant;
	}

	public String getServicePlant() {
		return servicePlant;
	}

	public void setServicePlant(String servicePlant) {
		this.servicePlant = servicePlant;
	}

	public String getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		this.storageLocation = storageLocation;
	}

	

	public int getR_no() {
		return r_no;
	}

	public void setR_no(int r_no) {
		this.r_no = r_no;
	}

	public String getSfile() {
		return sfile;
	}

	public void setSfile(String sfile) {
		this.sfile = sfile;
	}

	public String[] getDocumentCheck() {
		return documentCheck;
	}

	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}

	public int[] getRequiredRequestNumber() {
		return requiredRequestNumber;
	}

	public void setRequiredRequestNumber(int[] requiredRequestNumber) {
		this.requiredRequestNumber = requiredRequestNumber;
	}

	public String getApproveStatus() {
		return approveStatus;
	}

	public void setApproveStatus(String approveStatus) {
		this.approveStatus = approveStatus;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
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

	public LinkedList getUomId() {
		return uomId;
	}



	public LinkedList getUomLabel() {
		return uomLabel;
	}

	public void setUomLabel(LinkedList uomLabel) {
		this.uomLabel = uomLabel;
	}

	public LinkedList getPid() {
		return pid;
	}

	public void setPid(LinkedList pid) {
		this.pid = pid;
	}

	public LinkedList getPlabel() {
		return plabel;
	}

	public void setPlabel(LinkedList plabel) {
		this.plabel = plabel;
	}

	public LinkedList getVid() {
		return vid;
	}

	public void setVid(LinkedList vid) {
		this.vid = vid;
	}

	public LinkedList getVlabel() {
		return vlabel;
	}

	public void setVlabel(LinkedList vlabel) {
		this.vlabel = vlabel;
	}

	public String getU_id() {
		return u_id;
	}

	public void setU_id(String u_id) {
		this.u_id = u_id;
	}

	public String getU_label() {
		return u_label;
	}

	public void setU_label(String u_label) {
		this.u_label = u_label;
	}

	public void setUomId(LinkedList<Object> uomId) {
		this.uomId = uomId;
	}

	public String getP_id() {
		return p_id;
	}

	public void setP_id(String p_id) {
		this.p_id = p_id;
	}

	public String getP_all() {
		return p_all;
	}

	public void setP_all(String p_all) {
		this.p_all = p_all;
	}

	public LinkedList getPcode() {
		return pcode;
	}

	public void setPcode(LinkedList pcode) {
		this.pcode = pcode;
	}


	public String getPl_all() {
		return pl_all;
	}

	public void setPl_all(String pl_all) {
		this.pl_all = pl_all;
	}

	

	public String getPl_id() {
		return pl_id;
	}

	public void setPl_id(String pl_id) {
		this.pl_id = pl_id;
	}

	

	public LinkedList getSgroupLabel() {
		return sgroupLabel;
	}

	public void setSgroupLabel(LinkedList sgroupLabel) {
		this.sgroupLabel = sgroupLabel;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getService_label() {
		return service_label;
	}

	public void setService_label(String service_label) {
		this.service_label = service_label;
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

	public String getServiceDescription1() {
		return serviceDescription1;
	}

	public void setServiceDescription1(String serviceDescription1) {
		this.serviceDescription1 = serviceDescription1;
	}

	public String getServiceGroup1() {
		return serviceGroup1;
	}

	public void setServiceGroup1(String serviceGroup1) {
		this.serviceGroup1 = serviceGroup1;
	}

	public String getRequestno() {
		return requestno;
	}

	public void setRequestno(String requestno) {
		this.requestno = requestno;
	}

	
	
	
	
	
}
