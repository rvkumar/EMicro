package com.microlabs.hr.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class HRForm extends ActionForm{
	
	private String category;
	private String status;	
	private String mode;
	private String priority;
	private String name;
	private String requesterId;
	private String assignedTo;	
	private String assetDetails;
	private String contactNo;
	private String department;	
	private String createdDate;
	private String jobTitle;

	
	private String plant;
	private String locationDetails;
	private String intercomNo;	
	private String IPAdress;
	private String subCategory;
	private String subject;
	private String dueBy;
	private String contentDescription;
	private String fileFullPath;
	private String videoFullPath;
	private String completedDate;
	private ArrayList<?> requestList;
	private String linkName;
	private String subLinkName;
	private String linkPath;

	private String iconName;
	private String linkTitle;
	private String linkId;
	private String imageName;
	
	//payslip
	private ArrayList locationIdList = new ArrayList();
	private ArrayList locationLabelList = new ArrayList();

	private ArrayList deptIdList = new ArrayList();
	private ArrayList deptLabelList = new ArrayList();
	
	private String selectedRequestNo[];
	
	
	private String departments[];

	private String locationId[];

	private String cat[];

	private String pernr;
	
	private String message;
	private String message2;
	
	private String id;
	private String reqNo;
	private String assetType;
	private String assetDesc;
	private String serialNo;
	private String insurence_renuewal;
	private String received_dt;
	private String pERNR;
	private String model;
	private String make;
	private String vehicle_no;
	private String insurance_no;
	private String insurance_Compny;
	private String insurance_Issue_date;
	private String insurance_Exp_date;
	private String prev_user;
	private String service_provider;
	private String data_card_no;
	private String default_pwd;
	private String service_plan;
	private String delete_flag;
	private String created_by;
	private String created_Date;
	private String modified_by;
	private String modified_date;

	
	private String documentCheck;
	
	private String empname;
	private String empdep;
	private String empdesg;
	private String emploc;
	private String empext;
	
	private String prev_user_name;
	private String prev_user_dep;
	private String prev_user_des;
	
	
	
	
	
	
	public String getPrev_user_name() {
		return prev_user_name;
	}
	public void setPrev_user_name(String prev_user_name) {
		this.prev_user_name = prev_user_name;
	}
	public String getPrev_user_dep() {
		return prev_user_dep;
	}
	public void setPrev_user_dep(String prev_user_dep) {
		this.prev_user_dep = prev_user_dep;
	}
	public String getPrev_user_des() {
		return prev_user_des;
	}
	public void setPrev_user_des(String prev_user_des) {
		this.prev_user_des = prev_user_des;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpdep() {
		return empdep;
	}
	public void setEmpdep(String empdep) {
		this.empdep = empdep;
	}
	public String getEmpdesg() {
		return empdesg;
	}
	public void setEmpdesg(String empdesg) {
		this.empdesg = empdesg;
	}
	public String getEmploc() {
		return emploc;
	}
	public void setEmploc(String emploc) {
		this.emploc = emploc;
	}
	public String getEmpext() {
		return empext;
	}
	public void setEmpext(String empext) {
		this.empext = empext;
	}
	public String getDocumentCheck() {
		return documentCheck;
	}
	public void setDocumentCheck(String documentCheck) {
		this.documentCheck = documentCheck;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getReqNo() {
		return reqNo;
	}
	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}
	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	public String getAssetDesc() {
		return assetDesc;
	}
	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getInsurence_renuewal() {
		return insurence_renuewal;
	}
	public void setInsurence_renuewal(String insurence_renuewal) {
		this.insurence_renuewal = insurence_renuewal;
	}
	public String getReceived_dt() {
		return received_dt;
	}
	public void setReceived_dt(String received_dt) {
		this.received_dt = received_dt;
	}
	public String getpERNR() {
		return pERNR;
	}
	public void setpERNR(String pERNR) {
		this.pERNR = pERNR;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getVehicle_no() {
		return vehicle_no;
	}
	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}
	public String getInsurance_no() {
		return insurance_no;
	}
	public void setInsurance_no(String insurance_no) {
		this.insurance_no = insurance_no;
	}
	public String getInsurance_Compny() {
		return insurance_Compny;
	}
	public void setInsurance_Compny(String insurance_Compny) {
		this.insurance_Compny = insurance_Compny;
	}
	public String getInsurance_Issue_date() {
		return insurance_Issue_date;
	}
	public void setInsurance_Issue_date(String insurance_Issue_date) {
		this.insurance_Issue_date = insurance_Issue_date;
	}
	public String getInsurance_Exp_date() {
		return insurance_Exp_date;
	}
	public void setInsurance_Exp_date(String insurance_Exp_date) {
		this.insurance_Exp_date = insurance_Exp_date;
	}
	public String getPrev_user() {
		return prev_user;
	}
	public void setPrev_user(String prev_user) {
		this.prev_user = prev_user;
	}
	public String getService_provider() {
		return service_provider;
	}
	public void setService_provider(String service_provider) {
		this.service_provider = service_provider;
	}
	public String getData_card_no() {
		return data_card_no;
	}
	public void setData_card_no(String data_card_no) {
		this.data_card_no = data_card_no;
	}
	public String getDefault_pwd() {
		return default_pwd;
	}
	public void setDefault_pwd(String default_pwd) {
		this.default_pwd = default_pwd;
	}
	public String getService_plan() {
		return service_plan;
	}
	public void setService_plan(String service_plan) {
		this.service_plan = service_plan;
	}
	public String getDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(String delete_flag) {
		this.delete_flag = delete_flag;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getCreated_Date() {
		return created_Date;
	}
	public void setCreated_Date(String created_Date) {
		this.created_Date = created_Date;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
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
	public String[] getSelectedRequestNo() {
		return selectedRequestNo;
	}
	public void setSelectedRequestNo(String[] selectedRequestNo) {
		this.selectedRequestNo = selectedRequestNo;
	}
	public String[] getDepartments() {
		return departments;
	}
	public void setDepartments(String[] departments) {
		this.departments = departments;
	}
	public String[] getLocationId() {
		return locationId;
	}
	public void setLocationId(String[] locationId) {
		this.locationId = locationId;
	}
	public String[] getCat() {
		return cat;
	}
	public void setCat(String[] cat) {
		this.cat = cat;
	}
	public String getPernr() {
		return pernr;
	}
	public void setPernr(String pernr) {
		this.pernr = pernr;
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
	public ArrayList getDeptIdList() {
		return deptIdList;
	}
	public void setDeptIdList(ArrayList deptIdList) {
		this.deptIdList = deptIdList;
	}
	public ArrayList getDeptLabelList() {
		return deptLabelList;
	}
	public void setDeptLabelList(ArrayList deptLabelList) {
		this.deptLabelList = deptLabelList;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRequesterId() {
		return requesterId;
	}
	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getAssetDetails() {
		return assetDetails;
	}
	public void setAssetDetails(String assetDetails) {
		this.assetDetails = assetDetails;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getLocationDetails() {
		return locationDetails;
	}
	public void setLocationDetails(String locationDetails) {
		this.locationDetails = locationDetails;
	}
	public String getIntercomNo() {
		return intercomNo;
	}
	public void setIntercomNo(String intercomNo) {
		this.intercomNo = intercomNo;
	}
	public String getIPAdress() {
		return IPAdress;
	}
	public void setIPAdress(String adress) {
		IPAdress = adress;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDueBy() {
		return dueBy;
	}
	public void setDueBy(String dueBy) {
		this.dueBy = dueBy;
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
	public String getCompletedDate() {
		return completedDate;
	}
	public void setCompletedDate(String completedDate) {
		this.completedDate = completedDate;
	}
	public ArrayList<?> getRequestList() {
		return requestList;
	}
	public void setRequestList(ArrayList<?> requestList) {
		this.requestList = requestList;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public String getSubLinkName() {
		return subLinkName;
	}
	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}
	public String getLinkPath() {
		return linkPath;
	}
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}
	public String getLinkTitle() {
		return linkTitle;
	}
	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
	
	
	
	
	
	
	
	
}
