package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;

public class EmployeeOfficialInfoForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   private int id;
	private String message1;
private String photoImage;
private String reqBuilding;
private String reqFloor;
private String reqBlockNo;
private String reqTelNo;
private String reqExtNo;
private String reqIPPhone;

private String companyName;	
private String employeeNumber;
private String firstName;
private String middleName;
private String lastName;
private String photo;
private String reqEmpNo;
private String employeeName;
private String gender;

private String plant;
private String place;
private String payGroup;
private String employeeCategory;  
//// 
private String reportingManger;
private String approvalManger;
private String headOfDepartment;
private String gradeID;
private String designation;	
private String department;

private String emailid;
private String telNo;
private String extnNo;
private String ipPhoneNo;
private String countryofWorking;
private String counID;
private String state;
private String location;
private String headQuarters;
private String dateofJoining;
private String dateofConformation;
private String dateofLeaving;

private String room;
private String floor;
private String building;
private String addressTypeID;


private String eligibleforESIDeduction;

private String esiNumber;	
private String eligibleforPFDeduction;
private String pfNumber;
private String eligibleforPTDeduction;	
private String eligibleforITDeduction;
private String panNo;
private String bonus;
private String leaves;

private String salaryCurrency;
private String paymentMethod;
private String accountType;
private String accountNumber;
private String bankId;
private String bankName;
private String branchName;
private String ifsCCode;
private String micrCode;

private String createdDate;
private String timeofEntry;
private String userName;
private String changedOn;
private String lastChangedBy;
private String deletedOn;
private String deletedBy;

private String message;

private String searchEmployee;
private String loginName;

private String contactNo;
private String states;

private String leaveType1;
private String leaveType2;
private String leaveType3;
private String leaveType4;

private String openingBalence1;
private String openingBalence2;
private String openingBalence3;
private String openingBalence4;

private String closingBalence1;
private String closingBalence2;
private String closingBalence3;
private String closingBalence4;

private String ipPhone;

//Assets
private int reqNo;
private String empNo;
private String assetType;
private String assetDesc;
private String serialNo;
private String recivedDt;
private String insurRenewalDt;
public String[] documentCheck;
public String boardLine;
public String reqboardLine;
private String uanno;




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



private String empname;
private String empdep;
private String empdesg;
private String emploc;
private String empext;

private String prev_user_name;
private String prev_user_dep;
private String prev_user_des;



private int asset_ID;
private String category;
private String asset_No;
private String host_Name;
private String bar_Code;
private String manufacturer;
private String part_No;
private String serial_No;
private String processor;
private String rAM;
private String hDD;
private String config;
private String iP_Address;
private String monitor_Type;
private String size;
private String assetState;





public int getAsset_ID() {
	return asset_ID;
}
public void setAsset_ID(int asset_ID) {
	this.asset_ID = asset_ID;
}
public String getCategory() {
	return category;
}
public void setCategory(String category) {
	this.category = category;
}
public String getAsset_No() {
	return asset_No;
}
public void setAsset_No(String asset_No) {
	this.asset_No = asset_No;
}
public String getHost_Name() {
	return host_Name;
}
public void setHost_Name(String host_Name) {
	this.host_Name = host_Name;
}
public String getBar_Code() {
	return bar_Code;
}
public void setBar_Code(String bar_Code) {
	this.bar_Code = bar_Code;
}
public String getManufacturer() {
	return manufacturer;
}
public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
}
public String getPart_No() {
	return part_No;
}
public void setPart_No(String part_No) {
	this.part_No = part_No;
}
public String getSerial_No() {
	return serial_No;
}
public void setSerial_No(String serial_No) {
	this.serial_No = serial_No;
}
public String getProcessor() {
	return processor;
}
public void setProcessor(String processor) {
	this.processor = processor;
}
public String getrAM() {
	return rAM;
}
public void setrAM(String rAM) {
	this.rAM = rAM;
}
public String gethDD() {
	return hDD;
}
public void sethDD(String hDD) {
	this.hDD = hDD;
}
public String getConfig() {
	return config;
}
public void setConfig(String config) {
	this.config = config;
}
public String getiP_Address() {
	return iP_Address;
}
public void setiP_Address(String iP_Address) {
	this.iP_Address = iP_Address;
}
public String getMonitor_Type() {
	return monitor_Type;
}
public void setMonitor_Type(String monitor_Type) {
	this.monitor_Type = monitor_Type;
}
public String getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}
public String getAssetState() {
	return assetState;
}
public void setAssetState(String assetState) {
	this.assetState = assetState;
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
public String getUanno() {
	return uanno;
}
public void setUanno(String uanno) {
	this.uanno = uanno;
}
public String getReqboardLine() {
	return reqboardLine;
}
public void setReqboardLine(String reqboardLine) {
	this.reqboardLine = reqboardLine;
}
public String getBoardLine() {
	return boardLine;
}
public void setBoardLine(String boardLine) {
	this.boardLine = boardLine;
}
public String getPlace() {
	return place;
}
public void setPlace(String place) {
	this.place = place;
}
public String[] getDocumentCheck() {
	return documentCheck;
}
public void setDocumentCheck(String[] documentCheck) {
	this.documentCheck = documentCheck;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getReqNo() {
	return reqNo;
}
public void setReqNo(int reqNo) {
	this.reqNo = reqNo;
}
public String getEmpNo() {
	return empNo;
}
public void setEmpNo(String empNo) {
	this.empNo = empNo;
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
public String getRecivedDt() {
	return recivedDt;
}
public void setRecivedDt(String recivedDt) {
	this.recivedDt = recivedDt;
}
public String getInsurRenewalDt() {
	return insurRenewalDt;
}
public void setInsurRenewalDt(String insurRenewalDt) {
	this.insurRenewalDt = insurRenewalDt;
}
public static long getSerialversionuid() {
	return serialVersionUID;
}
public String getIpPhone() {
	return ipPhone;
}
public void setIpPhone(String ipPhone) {
	this.ipPhone = ipPhone;
}
public String getMessage1() {
	return message1;
}
public void setMessage1(String message1) {
	this.message1 = message1;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public String getEmployeeNumber() {
	return employeeNumber;
}
public void setEmployeeNumber(String employeeNumber) {
	this.employeeNumber = employeeNumber;
}
public String getFirstName() {
	return firstName;
}
public void setFirstName(String firstName) {
	this.firstName = firstName;
}
public String getMiddleName() {
	return middleName;
}
public void setMiddleName(String middleName) {
	this.middleName = middleName;
}
public String getLastName() {
	return lastName;
}
public void setLastName(String lastName) {
	this.lastName = lastName;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getReqEmpNo() {
	return reqEmpNo;
}
public void setReqEmpNo(String reqEmpNo) {
	this.reqEmpNo = reqEmpNo;
}
public String getEmployeeName() {
	return employeeName;
}
public void setEmployeeName(String employeeName) {
	this.employeeName = employeeName;
}
public String getGender() {
	return gender;
}
public void setGender(String gender) {
	this.gender = gender;
}
public String getPlant() {
	return plant;
}
public void setPlant(String plant) {
	this.plant = plant;
}
public String getPayGroup() {
	return payGroup;
}
public void setPayGroup(String payGroup) {
	this.payGroup = payGroup;
}
public String getEmployeeCategory() {
	return employeeCategory;
}
public void setEmployeeCategory(String employeeCategory) {
	this.employeeCategory = employeeCategory;
}
public String getReportingManger() {
	return reportingManger;
}
public void setReportingManger(String reportingManger) {
	this.reportingManger = reportingManger;
}
public String getApprovalManger() {
	return approvalManger;
}
public void setApprovalManger(String approvalManger) {
	this.approvalManger = approvalManger;
}
public String getHeadOfDepartment() {
	return headOfDepartment;
}
public void setHeadOfDepartment(String headOfDepartment) {
	this.headOfDepartment = headOfDepartment;
}
public String getGradeID() {
	return gradeID;
}
public void setGradeID(String gradeID) {
	this.gradeID = gradeID;
}
public String getDesignation() {
	return designation;
}
public void setDesignation(String designation) {
	this.designation = designation;
}
public String getDepartment() {
	return department;
}
public void setDepartment(String department) {
	this.department = department;
}
public String getEmailid() {
	return emailid;
}
public void setEmailid(String emailid) {
	this.emailid = emailid;
}
public String getTelNo() {
	return telNo;
}
public void setTelNo(String telNo) {
	this.telNo = telNo;
}
public String getExtnNo() {
	return extnNo;
}
public void setExtnNo(String extnNo) {
	this.extnNo = extnNo;
}
public String getIpPhoneNo() {
	return ipPhoneNo;
}
public void setIpPhoneNo(String ipPhoneNo) {
	this.ipPhoneNo = ipPhoneNo;
}
public String getCountryofWorking() {
	return countryofWorking;
}
public void setCountryofWorking(String countryofWorking) {
	this.countryofWorking = countryofWorking;
}
public String getCounID() {
	return counID;
}
public void setCounID(String counID) {
	this.counID = counID;
}
public String getState() {
	return state;
}
public void setState(String state) {
	this.state = state;
}
public String getLocation() {
	return location;
}
public void setLocation(String location) {
	this.location = location;
}
public String getHeadQuarters() {
	return headQuarters;
}
public void setHeadQuarters(String headQuarters) {
	this.headQuarters = headQuarters;
}
public String getDateofJoining() {
	return dateofJoining;
}
public void setDateofJoining(String dateofJoining) {
	this.dateofJoining = dateofJoining;
}
public String getDateofConformation() {
	return dateofConformation;
}
public void setDateofConformation(String dateofConformation) {
	this.dateofConformation = dateofConformation;
}
public String getDateofLeaving() {
	return dateofLeaving;
}
public void setDateofLeaving(String dateofLeaving) {
	this.dateofLeaving = dateofLeaving;
}
public String getRoom() {
	return room;
}
public void setRoom(String room) {
	this.room = room;
}
public String getFloor() {
	return floor;
}
public void setFloor(String floor) {
	this.floor = floor;
}
public String getBuilding() {
	return building;
}
public void setBuilding(String building) {
	this.building = building;
}
public String getAddressTypeID() {
	return addressTypeID;
}
public void setAddressTypeID(String addressTypeID) {
	this.addressTypeID = addressTypeID;
}
public String getEligibleforESIDeduction() {
	return eligibleforESIDeduction;
}
public void setEligibleforESIDeduction(String eligibleforESIDeduction) {
	this.eligibleforESIDeduction = eligibleforESIDeduction;
}
public String getEsiNumber() {
	return esiNumber;
}
public void setEsiNumber(String esiNumber) {
	this.esiNumber = esiNumber;
}
public String getEligibleforPFDeduction() {
	return eligibleforPFDeduction;
}
public void setEligibleforPFDeduction(String eligibleforPFDeduction) {
	this.eligibleforPFDeduction = eligibleforPFDeduction;
}
public String getPfNumber() {
	return pfNumber;
}
public void setPfNumber(String pfNumber) {
	this.pfNumber = pfNumber;
}
public String getEligibleforPTDeduction() {
	return eligibleforPTDeduction;
}
public void setEligibleforPTDeduction(String eligibleforPTDeduction) {
	this.eligibleforPTDeduction = eligibleforPTDeduction;
}
public String getEligibleforITDeduction() {
	return eligibleforITDeduction;
}
public void setEligibleforITDeduction(String eligibleforITDeduction) {
	this.eligibleforITDeduction = eligibleforITDeduction;
}
public String getPanNo() {
	return panNo;
}
public void setPanNo(String panNo) {
	this.panNo = panNo;
}
public String getBonus() {
	return bonus;
}
public void setBonus(String bonus) {
	this.bonus = bonus;
}
public String getLeaves() {
	return leaves;
}
public void setLeaves(String leaves) {
	this.leaves = leaves;
}
public String getSalaryCurrency() {
	return salaryCurrency;
}
public void setSalaryCurrency(String salaryCurrency) {
	this.salaryCurrency = salaryCurrency;
}
public String getPaymentMethod() {
	return paymentMethod;
}
public void setPaymentMethod(String paymentMethod) {
	this.paymentMethod = paymentMethod;
}
public String getAccountType() {
	return accountType;
}
public void setAccountType(String accountType) {
	this.accountType = accountType;
}
public String getAccountNumber() {
	return accountNumber;
}
public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
}
public String getBankId() {
	return bankId;
}
public void setBankId(String bankId) {
	this.bankId = bankId;
}
public String getBankName() {
	return bankName;
}
public void setBankName(String bankName) {
	this.bankName = bankName;
}
public String getBranchName() {
	return branchName;
}
public void setBranchName(String branchName) {
	this.branchName = branchName;
}
public String getIfsCCode() {
	return ifsCCode;
}
public void setIfsCCode(String ifsCCode) {
	this.ifsCCode = ifsCCode;
}
public String getMicrCode() {
	return micrCode;
}
public void setMicrCode(String micrCode) {
	this.micrCode = micrCode;
}
public String getCreatedDate() {
	return createdDate;
}
public void setCreatedDate(String createdDate) {
	this.createdDate = createdDate;
}
public String getTimeofEntry() {
	return timeofEntry;
}
public void setTimeofEntry(String timeofEntry) {
	this.timeofEntry = timeofEntry;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getChangedOn() {
	return changedOn;
}
public void setChangedOn(String changedOn) {
	this.changedOn = changedOn;
}
public String getLastChangedBy() {
	return lastChangedBy;
}
public void setLastChangedBy(String lastChangedBy) {
	this.lastChangedBy = lastChangedBy;
}
public String getDeletedOn() {
	return deletedOn;
}
public void setDeletedOn(String deletedOn) {
	this.deletedOn = deletedOn;
}
public String getDeletedBy() {
	return deletedBy;
}
public void setDeletedBy(String deletedBy) {
	this.deletedBy = deletedBy;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getSearchEmployee() {
	return searchEmployee;
}
public void setSearchEmployee(String searchEmployee) {
	this.searchEmployee = searchEmployee;
}
public String getLoginName() {
	return loginName;
}
public void setLoginName(String loginName) {
	this.loginName = loginName;
}
public String getContactNo() {
	return contactNo;
}
public void setContactNo(String contactNo) {
	this.contactNo = contactNo;
}
public String getStates() {
	return states;
}
public void setStates(String states) {
	this.states = states;
}
public String getLeaveType1() {
	return leaveType1;
}
public void setLeaveType1(String leaveType1) {
	this.leaveType1 = leaveType1;
}
public String getLeaveType2() {
	return leaveType2;
}
public void setLeaveType2(String leaveType2) {
	this.leaveType2 = leaveType2;
}
public String getLeaveType3() {
	return leaveType3;
}
public void setLeaveType3(String leaveType3) {
	this.leaveType3 = leaveType3;
}
public String getLeaveType4() {
	return leaveType4;
}
public void setLeaveType4(String leaveType4) {
	this.leaveType4 = leaveType4;
}
public String getOpeningBalence1() {
	return openingBalence1;
}
public void setOpeningBalence1(String openingBalence1) {
	this.openingBalence1 = openingBalence1;
}
public String getOpeningBalence2() {
	return openingBalence2;
}
public void setOpeningBalence2(String openingBalence2) {
	this.openingBalence2 = openingBalence2;
}
public String getOpeningBalence3() {
	return openingBalence3;
}
public void setOpeningBalence3(String openingBalence3) {
	this.openingBalence3 = openingBalence3;
}
public String getOpeningBalence4() {
	return openingBalence4;
}
public void setOpeningBalence4(String openingBalence4) {
	this.openingBalence4 = openingBalence4;
}
public String getClosingBalence1() {
	return closingBalence1;
}
public void setClosingBalence1(String closingBalence1) {
	this.closingBalence1 = closingBalence1;
}
public String getClosingBalence2() {
	return closingBalence2;
}
public void setClosingBalence2(String closingBalence2) {
	this.closingBalence2 = closingBalence2;
}
public String getClosingBalence3() {
	return closingBalence3;
}
public void setClosingBalence3(String closingBalence3) {
	this.closingBalence3 = closingBalence3;
}
public String getClosingBalence4() {
	return closingBalence4;
}
public void setClosingBalence4(String closingBalence4) {
	this.closingBalence4 = closingBalence4;
}
public String getReqFloor() {
	return reqFloor;
}
public void setReqFloor(String reqFloor) {
	this.reqFloor = reqFloor;
}
public String getReqBlockNo() {
	return reqBlockNo;
}
public void setReqBlockNo(String reqBlockNo) {
	this.reqBlockNo = reqBlockNo;
}
public String getReqTelNo() {
	return reqTelNo;
}
public void setReqTelNo(String reqTelNo) {
	this.reqTelNo = reqTelNo;
}
public String getReqExtNo() {
	return reqExtNo;
}
public void setReqExtNo(String reqExtNo) {
	this.reqExtNo = reqExtNo;
}
public String getReqIPPhone() {
	return reqIPPhone;
}
public void setReqIPPhone(String reqIPPhone) {
	this.reqIPPhone = reqIPPhone;
}
public String getReqBuilding() {
	return reqBuilding;
}
public void setReqBuilding(String reqBuilding) {
	this.reqBuilding = reqBuilding;
}
public String getPhotoImage() {
	return photoImage;
}
public void setPhotoImage(String photoImage) {
	this.photoImage = photoImage;
}



}
