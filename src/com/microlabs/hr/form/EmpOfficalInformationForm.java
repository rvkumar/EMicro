package com.microlabs.hr.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class EmpOfficalInformationForm extends ActionForm{
	
	
	private String id;
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
	
	private String awaitingBal1;
	private String awaitingBal2;
	private String awaitingBal3;
	private String awaitingBal4;
	
	private String availedBal1;
	private String availedBal2;
	private String availedBal3;
	private String availedBal4;
	
	
	private String appr;
	private String dob;

///Country///
	private LinkedList countryID;
	private LinkedList country;
	
	//State//
	private LinkedList stateID;
	private LinkedList stateName;

	
	//desgnations//
	private LinkedList 	desgnIDList;
	private LinkedList 	desgnTXTList;
	
	
	//departments//

	private LinkedList 	departIDList;
	private LinkedList 	departTXTList;
	
	//Plant//
	private ArrayList plantNameList=new ArrayList();
	private ArrayList plantIDList=new ArrayList();
	
	
	private ArrayList departmentList= new ArrayList();
	
	//paygroup//
	private ArrayList paygroupList=new ArrayList();
	private ArrayList paystextList=new ArrayList();
	
	//EMP CATOGERY//
	
	
	private ArrayList empCatogeryList=new ArrayList();
	private ArrayList empCatogerytextList=new ArrayList();
	
	//Grade//
	
	
	
	private ArrayList gradetxtList = new ArrayList();
	private ArrayList  gradeidList = new ArrayList();
	
	//Bank//
	private ArrayList bankIDList = new ArrayList();
	private ArrayList banknameList = new ArrayList();
	
	//Currency//
	private ArrayList currencyIDList = new ArrayList();
	private ArrayList currencyNameList = new ArrayList();
	
	//Company//
	private ArrayList companyIDList = new ArrayList();
	private ArrayList companyNameList = new ArrayList(); 
	
	//Pay Method//
	private ArrayList payIDList = new ArrayList();
	private ArrayList payNameList = new ArrayList();
	
	//Location//
	private ArrayList locIDList = new ArrayList();
	private ArrayList locNameList = new ArrayList();
	private String year;

	
	LinkedList leaveTypeID=new LinkedList();
	LinkedList leaveTypeName=new LinkedList();
	private String searchText;
	private String searchAddress;
	
	private String saveType;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	
	private String fullName;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private String locationId;
	
	private String emailID;
	private String uanno;
	private String title;
	private String usergrp;
	
	
	
	private String shift_Code;
	private String rule_Code;
	private String swipe_Count;
	private String work_id;
	
	
	private String repgrp;
	private String sdptid;
	
	
	
	private String doj;
	private String conId;
	private String conSal;
	
	
	
	
	
	
	public String getConSal() {
		return conSal;
	}
	public void setConSal(String conSal) {
		this.conSal = conSal;
	}
	public String getConId() {
		return conId;
	}
	public void setConId(String conId) {
		this.conId = conId;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getRepgrp() {
		return repgrp;
	}
	public void setRepgrp(String repgrp) {
		this.repgrp = repgrp;
	}
	public String getSdptid() {
		return sdptid;
	}
	public void setSdptid(String sdptid) {
		this.sdptid = sdptid;
	}
	
	
	public String getShift_Code() {
		return shift_Code;
	}
	public void setShift_Code(String shift_Code) {
		this.shift_Code = shift_Code;
	}
	public String getRule_Code() {
		return rule_Code;
	}
	public void setRule_Code(String rule_Code) {
		this.rule_Code = rule_Code;
	}
	public String getSwipe_Count() {
		return swipe_Count;
	}
	public void setSwipe_Count(String swipe_Count) {
		this.swipe_Count = swipe_Count;
	}
	public String getWork_id() {
		return work_id;
	}
	public void setWork_id(String work_id) {
		this.work_id = work_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUsergrp() {
		return usergrp;
	}
	public void setUsergrp(String usergrp) {
		this.usergrp = usergrp;
	}
	public String getUanno() {
		return uanno;
	}
	public void setUanno(String uanno) {
		this.uanno = uanno;
	}
	public String getAwaitingBal1() {
		return awaitingBal1;
	}
	public void setAwaitingBal1(String awaitingBal1) {
		this.awaitingBal1 = awaitingBal1;
	}
	public String getAwaitingBal2() {
		return awaitingBal2;
	}
	public void setAwaitingBal2(String awaitingBal2) {
		this.awaitingBal2 = awaitingBal2;
	}
	public String getAwaitingBal3() {
		return awaitingBal3;
	}
	public void setAwaitingBal3(String awaitingBal3) {
		this.awaitingBal3 = awaitingBal3;
	}
	public String getAwaitingBal4() {
		return awaitingBal4;
	}
	public void setAwaitingBal4(String awaitingBal4) {
		this.awaitingBal4 = awaitingBal4;
	}
	public String getAvailedBal1() {
		return availedBal1;
	}
	public void setAvailedBal1(String availedBal1) {
		this.availedBal1 = availedBal1;
	}
	public String getAvailedBal2() {
		return availedBal2;
	}
	public void setAvailedBal2(String availedBal2) {
		this.availedBal2 = availedBal2;
	}
	public String getAvailedBal3() {
		return availedBal3;
	}
	public void setAvailedBal3(String availedBal3) {
		this.availedBal3 = availedBal3;
	}
	public String getAvailedBal4() {
		return availedBal4;
	}
	public void setAvailedBal4(String availedBal4) {
		this.availedBal4 = availedBal4;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAppr() {
		return appr;
	}
	public void setAppr(String appr) {
		this.appr = appr;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
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
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	public String getReqEmpNo() {
		return reqEmpNo;
	}
	public void setReqEmpNo(String reqEmpNo) {
		this.reqEmpNo = reqEmpNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getSaveType() {
		return saveType;
	}
	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}
	public String getSearchAddress() {
		return searchAddress;
	}
	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public LinkedList getLeaveTypeID() {
		return leaveTypeID;
	}
	public void setLeaveTypeID(LinkedList leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}
	public LinkedList getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(LinkedList leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
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
	public String getIpPhoneNo() {
		return ipPhoneNo;
	}
	public void setIpPhoneNo(String ipPhoneNo) {
		this.ipPhoneNo = ipPhoneNo;
	}
	public String getStates() {
		return states;
	}
	public void setStates(String states) {
		this.states = states;
	}
	public ArrayList getLocIDList() {
		return locIDList;
	}
	public void setLocIDList(ArrayList locIDList) {
		this.locIDList = locIDList;
	}
	public ArrayList getPayIDList() {
		return payIDList;
	}
	public void setPayIDList(ArrayList payIDList) {
		this.payIDList = payIDList;
	}
	public ArrayList getPayNameList() {
		return payNameList;
	}
	public void setPayNameList(ArrayList payNameList) {
		this.payNameList = payNameList;
	}
	public ArrayList getCurrencyIDList() {
		return currencyIDList;
	}
	public void setCurrencyIDList(ArrayList currencyIDList) {
		this.currencyIDList = currencyIDList;
	}
	public ArrayList getCurrencyNameList() {
		return currencyNameList;
	}
	public void setCurrencyNameList(ArrayList currencyNameList) {
		this.currencyNameList = currencyNameList;
	}
	public ArrayList getBankIDList() {
		return bankIDList;
	}
	public void setBankIDList(ArrayList bankIDList) {
		this.bankIDList = bankIDList;
	}
	public ArrayList getBanknameList() {
		return banknameList;
	}
	public void setBanknameList(ArrayList banknameList) {
		this.banknameList = banknameList;
	}
	public ArrayList getGradetxtList() {
		return gradetxtList;
	}
	public void setGradetxtList(ArrayList gradetxtList) {
		this.gradetxtList = gradetxtList;
	}
	public ArrayList getGradeidList() {
		return gradeidList;
	}
	public void setGradeidList(ArrayList gradeidList) {
		this.gradeidList = gradeidList;
	}
	public ArrayList getPaygroupList() {
		return paygroupList;
	}
	public void setPaygroupList(ArrayList paygroupList) {
		this.paygroupList = paygroupList;
	}
	public ArrayList getPaystextList() {
		return paystextList;
	}
	public void setPaystextList(ArrayList paystextList) {
		this.paystextList = paystextList;
	}

	
	
	
	public ArrayList getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
	}


	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	public String getCountryofWorking() {
		return countryofWorking;
	}
	public void setCountryofWorking(String countryofWorking) {
		this.countryofWorking = countryofWorking;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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

	public LinkedList getCountry() {
		return country;
	}
	public void setCountry(LinkedList country) {
		this.country = country;
	}
	public String getCounID() {
		return counID;
	}
	public void setCounID(String counID) {
		this.counID = counID;
	}
	public LinkedList getStateID() {
		return stateID;
	}
	public void setStateID(LinkedList stateID) {
		this.stateID = stateID;
	}
	public LinkedList getStateName() {
		return stateName;
	}
	public void setStateName(LinkedList stateName) {
		this.stateName = stateName;
	}
	public LinkedList getCountryID() {
		return countryID;
	}
	public void setCountryID(LinkedList countryID) {
		this.countryID = countryID;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ArrayList getEmpCatogeryList() {
		return empCatogeryList;
	}
	public void setEmpCatogeryList(ArrayList empCatogeryList) {
		this.empCatogeryList = empCatogeryList;
	}
	public ArrayList getEmpCatogerytextList() {
		return empCatogerytextList;
	}
	public void setEmpCatogerytextList(ArrayList empCatogerytextList) {
		this.empCatogerytextList = empCatogerytextList;
	}
	public LinkedList getDepartIDList() {
		return departIDList;
	}
	public void setDepartIDList(LinkedList departIDList) {
		this.departIDList = departIDList;
	}
	public LinkedList getDepartTXTList() {
		return departTXTList;
	}
	public void setDepartTXTList(LinkedList departTXTList) {
		this.departTXTList = departTXTList;
	}
	public LinkedList getDesgnIDList() {
		return desgnIDList;
	}
	public void setDesgnIDList(LinkedList desgnIDList) {
		this.desgnIDList = desgnIDList;
	}
	public LinkedList getDesgnTXTList() {
		return desgnTXTList;
	}
	public void setDesgnTXTList(LinkedList desgnTXTList) {
		this.desgnTXTList = desgnTXTList;
	}
	public ArrayList getCompanyIDList() {
		return companyIDList;
	}
	public void setCompanyIDList(ArrayList companyIDList) {
		this.companyIDList = companyIDList;
	}
	public ArrayList getCompanyNameList() {
		return companyNameList;
	}
	public void setCompanyNameList(ArrayList companyNameList) {
		this.companyNameList = companyNameList;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
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
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
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
	public ArrayList getPlantNameList() {
		return plantNameList;
	}
	public void setPlantNameList(ArrayList plantNameList) {
		this.plantNameList = plantNameList;
	}
	public ArrayList getPlantIDList() {
		return plantIDList;
	}
	public void setPlantIDList(ArrayList plantIDList) {
		this.plantIDList = plantIDList;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArrayList getLocNameList() {
		return locNameList;
	}
	public void setLocNameList(ArrayList locNameList) {
		this.locNameList = locNameList;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	

	
  
	 
  
	


	

}
