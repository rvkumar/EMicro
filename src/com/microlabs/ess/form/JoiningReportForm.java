package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class JoiningReportForm extends ActionForm{
	

	

	private String userType;
	private String userName;
	private String password;
	private String message1;
	//Personal Information
	private FormFile empPhoto;
	private String photoImage;
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String initials;
	private String nickName;
	private String gender;
	private String maritalStatus;
	private String dateofBirth;
	private String birthplace;
	private String countryofBirth;
	private String caste;
	private String religiousDenomination;
	private String nationality;
	private String telephoneNumber;
	private String mobileNumber;
	private String faxNumber;
	private String emailAddress;
	private String website;
	private String bloodGroup;
	private String permanentAccountNumber;
	private String passportNumber;
	private String placeofIssueofPassport;
	private String dateofexpiryofPassport;
	private String personalIdentificationMarks;
	private String dateofissueofPassport;
	private String physicallyChallenged;
	private String physicallyChallengeddetails;
	private String emergencyContactPersonName;
	private String emergencyContactAddressLine1;
	private String emergCntAdd11;
	private String emergCntAdd111;
	
	
	private String emergencyContactAddressLine2;
	private String emergCntAdd22;
	private String emergCntAdd222;
	
	private String emergencyTelephoneNumber;
	private String emergencyMobileNumber;
	private String noOfChildrens;
	
	
	private String emergencyContactPersonName1;
	private String emergencyContactAddressLine3;
	private String emergCntAdd33;
	private String emergCntAdd333;
	
	private String emergencyContactAddressLine4;
	private String emergCntAdd44;
	private String emergCntAdd444;
	
	private String emergencyTelephoneNumber1;
	private String emergencyMobileNumber1;
	private String emergCity1;
	private String emergCity2;
	private String emergState1;
	private String emergState2;
	//Address
	private String careofcontactname;
	private String houseNumber;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String postalCode;
	private String city;
	private String state;
	private String country;
	private String ownAccomodation;
	private String addressType;
	private String companyHousing;
	private String addStartDate;
	private String addEndDate;
	
	//Address display
	private String[]  idAddress;
	private String[] careofcontactname1;
	private String[] houseNumber1;
	private String[] addressLine11;
	private String[] addressLine21;
	private String[] addressLine31;
	private String[] postalCode1;
	private String[] city1;
	private String[] state1;
	private String[] country1;
	private String[] ownAccomodation1;
	private String[] addressType1;
	private String[] companyHousing1;
	private String[] addStartDate1;
	private String[] addEndDate1;
	
	
	
	
	
	
	//Family Details
	private String familyDependentTypeID;
	private String ftitle;
	private String ffirstName;
	private String fmiddleName;
	private String flastName;
	private String finitials;
	private String fgender;
	private String fdateofBirth;
	private String fbirthplace;
	private String ftelephoneNumber;
	private String fmobileNumber;
	private String femailAddress;
	private String fbloodGroup;
	private String fdependent;
	private String femployeeofCompany;
	private String femployeeNumber;
	
	// family display
	private String[] familyDependentTypeID1;
	private String[] ftitle1;
	private String[] ffirstName1;
	private String[] fmiddleName1;
	private String[] flastName1;
	private String[] finitials1;
	private String[] fgender1;
	private String[] fdateofBirth1;
	private String[] fbirthplace1;
	private String[] ftelephoneNumber1;
	private String[] fmobileNumber1;
	private String[] femailAddress1;
	private String[] fbloodGroup1;
	private String[] fdependent1;
	private String[] femployeeofCompany1;
	private String[] femployeeNumber1;
	
	
	//Educational Details
	private String educationalLevel;
	private String qualification;
	private String specialization;
	private String universityName;
	private String univerysityLocation;
	private String edstate;
	private String edcountry;
	private String durationofCourse;
	private String times;
	private String fromDate;
	private String toDate;
	private String fullTimePartTime;
	private String percentage;
	private String yearofpassing;
	
	private FormFile empEdu;
	private	String   empEduDoc;
	
	//education display
	
	private String[] educationalLevel1;
	private String[] qualification1;
	private String[] specialization1;
	private String[] universityName1;
	private String[] univerysityLocation1;
	private String[] edstate1;
	private String[] edcountry1;
	private String[] durationofCourse1;
	private String[] times1;
	private String[] fromDate1;
	private String[] toDate1;
	private String[] fullTimePartTime1;
	private String[] percentage1;
	private String saveStatus;
	//Experience
	
	private String  nameOfEmployer;
	private String  industry;
	private String  exCity;
	private String  excountry;
	private String  positionHeld;
	private String  jobRole;
	private String  startDate;
	private String  endDate;
	private String microExp;
	private String microNo;
	
	
   //Experience display
	
	private String[]  nameOfEmployer1;
	private String[]  industry1;
	private String[]  exCity1;
	private String[]  excountry1;
	private String[]  positionHeld1;
	private String[]  jobRole1;
	private String[]  startDate1;
	private String[]  endDate1;
	
	//Language
	
	
	private String  language;
	private String  motherTongue;	
	private String  langSpeaking;
	private String  langRead;
	private String  langWrite;
	private String  langstartDate;
	private String  langendDate;
	
//Language display
	
	
	private String[]  language1;
	private String[]  motherTongue1;	
	private String[]  langSpeaking1;
	private String[]  langRead1;
	private String[]  langWrite1;
	private String[]  langstartDate1;
	private String[]  langendDate1;
	
	//common fields
	
	private String  employeeNo;
	private String  creationdate;
	private String  timeofEntry;
	private String  userName1;
	private String  changedOn;
	private String lastChangedBy;
	private String[] selectAddress;
	private String[] selectFamily;
	private String[] selectEducation;
	private String[] selectExperience;
	private String[] selectLanguage;
	private String method;
	private String  id;
	private String[]  id1;
	
	private String location;
	private String location_name;
	private String paygrp;
	private String category;
	private String doj;
	private String doc;
	private String grade;
	
	private String desg;
	
	private String esino;
	private String pfno;
	


	private String created_date;
	
	private String panno;
	private String uanno;
	private String adharno;
	
	
	private String salaryCurrency;
	private String paymentMethod;
	private String accountType;
	private String accountNumber;

	private String bankName;
	private String branchName;
	private String ifsCCode;
	private String micrCode;
	
	
	private ArrayList currencyList=new ArrayList();
	private ArrayList currencyLabelList=new ArrayList();
	
	private ArrayList paymodeList=new ArrayList();
	private ArrayList paymodeLabelList=new ArrayList();
	
	private ArrayList bankIDList=new ArrayList();
	private ArrayList bankLabelList=new ArrayList();
	
	
	private ArrayList desgnIDList=new ArrayList();
	private ArrayList desgnTXTList=new ArrayList();
	
	private ArrayList departIDList=new ArrayList();
	private ArrayList departTXTList=new ArrayList();
	//departments//

	
	
	private String fnominee;
	
	private String fileFullPath;
	
	
	private String newid;
	
	private String searchEmployee;
	
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	
	
	private String dateofjoin;
	
	private String department;
	
	private String designation;
	

	
	
	
	

	public String getDateofjoin() {
		return dateofjoin;
	}

	public void setDateofjoin(String dateofjoin) {
		this.dateofjoin = dateofjoin;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public ArrayList getDesgnIDList() {
		return desgnIDList;
	}

	public void setDesgnIDList(ArrayList desgnIDList) {
		this.desgnIDList = desgnIDList;
	}

	public ArrayList getDesgnTXTList() {
		return desgnTXTList;
	}

	public void setDesgnTXTList(ArrayList desgnTXTList) {
		this.desgnTXTList = desgnTXTList;
	}

	public ArrayList getDepartIDList() {
		return departIDList;
	}

	public void setDepartIDList(ArrayList departIDList) {
		this.departIDList = departIDList;
	}

	public ArrayList getDepartTXTList() {
		return departTXTList;
	}

	public void setDepartTXTList(ArrayList departTXTList) {
		this.departTXTList = departTXTList;
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

	public String getSearchEmployee() {
		return searchEmployee;
	}

	public void setSearchEmployee(String searchEmployee) {
		this.searchEmployee = searchEmployee;
	}

	public String getNewid() {
		return newid;
	}

	public void setNewid(String newid) {
		this.newid = newid;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public String getFnominee() {
		return fnominee;
	}

	public void setFnominee(String fnominee) {
		this.fnominee = fnominee;
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

	public ArrayList getPaymodeList() {
		return paymodeList;
	}

	public void setPaymodeList(ArrayList paymodeList) {
		this.paymodeList = paymodeList;
	}

	public ArrayList getPaymodeLabelList() {
		return paymodeLabelList;
	}

	public void setPaymodeLabelList(ArrayList paymodeLabelList) {
		this.paymodeLabelList = paymodeLabelList;
	}

	public ArrayList getBankIDList() {
		return bankIDList;
	}

	public void setBankIDList(ArrayList bankIDList) {
		this.bankIDList = bankIDList;
	}

	public ArrayList getBankLabelList() {
		return bankLabelList;
	}

	public void setBankLabelList(ArrayList bankLabelList) {
		this.bankLabelList = bankLabelList;
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

	public String getPanno() {
		return panno;
	}

	public void setPanno(String panno) {
		this.panno = panno;
	}

	public String getUanno() {
		return uanno;
	}

	public void setUanno(String uanno) {
		this.uanno = uanno;
	}

	public String getAdharno() {
		return adharno;
	}

	public void setAdharno(String adharno) {
		this.adharno = adharno;
	}

	

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}



	
	public String getPfno() {
		return pfno;
	}

	public void setPfno(String pfno) {
		this.pfno = pfno;
	}

	public String getEsino() {
		return esino;
	}

	public void setEsino(String esino) {
		this.esino = esino;
	}

	public String getDesg() {
		return desg;
	}

	public void setDesg(String desg) {
		this.desg = desg;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getPaygrp() {
		return paygrp;
	}

	public void setPaygrp(String paygrp) {
		this.paygrp = paygrp;
	}

	public String getLocation_name() {
		return location_name;
	}

	public void setLocation_name(String location_name) {
		this.location_name = location_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getYearofpassing() {
		return yearofpassing;
	}

	public void setYearofpassing(String yearofpassing) {
		this.yearofpassing = yearofpassing;
	}

	public String getEmergCntAdd11() {
		return emergCntAdd11;
	}

	public void setEmergCntAdd11(String emergCntAdd11) {
		this.emergCntAdd11 = emergCntAdd11;
	}

	public String getEmergCntAdd111() {
		return emergCntAdd111;
	}

	public void setEmergCntAdd111(String emergCntAdd111) {
		this.emergCntAdd111 = emergCntAdd111;
	}

	public String getEmergCntAdd22() {
		return emergCntAdd22;
	}

	public void setEmergCntAdd22(String emergCntAdd22) {
		this.emergCntAdd22 = emergCntAdd22;
	}

	public String getEmergCntAdd222() {
		return emergCntAdd222;
	}

	public void setEmergCntAdd222(String emergCntAdd222) {
		this.emergCntAdd222 = emergCntAdd222;
	}

	public String getEmergCntAdd33() {
		return emergCntAdd33;
	}

	public void setEmergCntAdd33(String emergCntAdd33) {
		this.emergCntAdd33 = emergCntAdd33;
	}

	public String getEmergCntAdd333() {
		return emergCntAdd333;
	}

	public void setEmergCntAdd333(String emergCntAdd333) {
		this.emergCntAdd333 = emergCntAdd333;
	}

	public String getEmergCntAdd44() {
		return emergCntAdd44;
	}

	public void setEmergCntAdd44(String emergCntAdd44) {
		this.emergCntAdd44 = emergCntAdd44;
	}

	public String getEmergCntAdd444() {
		return emergCntAdd444;
	}

	public void setEmergCntAdd444(String emergCntAdd444) {
		this.emergCntAdd444 = emergCntAdd444;
	}

	public String getMicroExp() {
		return microExp;
	}

	public void setMicroExp(String microExp) {
		this.microExp = microExp;
	}

	public String getMicroNo() {
		return microNo;
	}

	public void setMicroNo(String microNo) {
		this.microNo = microNo;
	}

	public String getEmergCity1() {
		return emergCity1;
	}

	public void setEmergCity1(String emergCity1) {
		this.emergCity1 = emergCity1;
	}

	public String getEmergCity2() {
		return emergCity2;
	}

	public void setEmergCity2(String emergCity2) {
		this.emergCity2 = emergCity2;
	}

	public String getEmergState1() {
		return emergState1;
	}

	public void setEmergState1(String emergState1) {
		this.emergState1 = emergState1;
	}

	public String getEmergState2() {
		return emergState2;
	}

	public void setEmergState2(String emergState2) {
		this.emergState2 = emergState2;
	}

	private ArrayList countryList=new ArrayList();
	private ArrayList countryLabelList=new ArrayList();
	
	private ArrayList stateList=new ArrayList();
	private ArrayList stateLabelList=new ArrayList();
	
	private ArrayList relationIDList=new ArrayList();
	private ArrayList relationValueList=new ArrayList();
	
	private ArrayList eduIDList=new ArrayList();
	private ArrayList eduValueList=new ArrayList();
	
	private ArrayList qulificationID=new ArrayList();
	private ArrayList qulificationValueList=new ArrayList();
	
	private ArrayList industyID=new ArrayList();
	private ArrayList industyValueList=new ArrayList();
	
	private ArrayList languageID=new ArrayList();
	private ArrayList languageValueList=new ArrayList();
	
	private ArrayList nationalityList=new ArrayList();
	private ArrayList nationalityLabelList=new ArrayList();
	
	private ArrayList religionList=new ArrayList();
	private ArrayList religionLabelList=new ArrayList();
	
	
	private String reqAddressID;
	
	private String reqFamilyID;
	private String reqEduID;
	private String reqExpID;
	private String reqLangID;
	
	private String addressStatus;
	private String educationStatus;
	
	private String message;
	
	//
	private  String reqNo;
	private  String dept;
	private  String empname;
	private  String reqdate;
	private  String reqStatus;
	private  String empId;
	
	
	
	
	
	public ArrayList getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(ArrayList nationalityList) {
		this.nationalityList = nationalityList;
	}

	public ArrayList getNationalityLabelList() {
		return nationalityLabelList;
	}

	public void setNationalityLabelList(ArrayList nationalityLabelList) {
		this.nationalityLabelList = nationalityLabelList;
	}

	public ArrayList getReligionList() {
		return religionList;
	}

	public void setReligionList(ArrayList religionList) {
		this.religionList = religionList;
	}

	public ArrayList getReligionLabelList() {
		return religionLabelList;
	}

	public void setReligionLabelList(ArrayList religionLabelList) {
		this.religionLabelList = religionLabelList;
	}

	public String getReqNo() {
		return reqNo;
	}

	public void setReqNo(String reqNo) {
		this.reqNo = reqNo;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getReqdate() {
		return reqdate;
	}

	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getSaveStatus() {
		return saveStatus;
	}

	public void setSaveStatus(String saveStatus) {
		this.saveStatus = saveStatus;
	}

	public String getEmergencyContactPersonName1() {
		return emergencyContactPersonName1;
	}

	public void setEmergencyContactPersonName1(String emergencyContactPersonName1) {
		this.emergencyContactPersonName1 = emergencyContactPersonName1;
	}

	public String getEmergencyContactAddressLine3() {
		return emergencyContactAddressLine3;
	}

	public void setEmergencyContactAddressLine3(String emergencyContactAddressLine3) {
		this.emergencyContactAddressLine3 = emergencyContactAddressLine3;
	}

	public String getEmergencyContactAddressLine4() {
		return emergencyContactAddressLine4;
	}

	public void setEmergencyContactAddressLine4(String emergencyContactAddressLine4) {
		this.emergencyContactAddressLine4 = emergencyContactAddressLine4;
	}

	public String getEmergencyTelephoneNumber1() {
		return emergencyTelephoneNumber1;
	}

	public void setEmergencyTelephoneNumber1(String emergencyTelephoneNumber1) {
		this.emergencyTelephoneNumber1 = emergencyTelephoneNumber1;
	}

	public String getEmergencyMobileNumber1() {
		return emergencyMobileNumber1;
	}

	public void setEmergencyMobileNumber1(String emergencyMobileNumber1) {
		this.emergencyMobileNumber1 = emergencyMobileNumber1;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getEducationStatus() {
		return educationStatus;
	}

	public void setEducationStatus(String educationStatus) {
		this.educationStatus = educationStatus;
	}

	public String getPhotoImage() {
		return photoImage;
	}

	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FormFile getEmpPhoto() {
		return empPhoto;
	}

	public void setEmpPhoto(FormFile empPhoto) {
		this.empPhoto = empPhoto;
	}

	public String getAddressStatus() {
		return addressStatus;
	}

	public void setAddressStatus(String addressStatus) {
		this.addressStatus = addressStatus;
	}

	public String getReqAddressID() {
		return reqAddressID;
	}

	public void setReqAddressID(String reqAddressID) {
		this.reqAddressID = reqAddressID;
	}

	public String getReqFamilyID() {
		return reqFamilyID;
	}

	public void setReqFamilyID(String reqFamilyID) {
		this.reqFamilyID = reqFamilyID;
	}

	public String getReqEduID() {
		return reqEduID;
	}

	public void setReqEduID(String reqEduID) {
		this.reqEduID = reqEduID;
	}

	public String getReqExpID() {
		return reqExpID;
	}

	public void setReqExpID(String reqExpID) {
		this.reqExpID = reqExpID;
	}

	public String getReqLangID() {
		return reqLangID;
	}

	public void setReqLangID(String reqLangID) {
		this.reqLangID = reqLangID;
	}

	public ArrayList getLanguageID() {
		return languageID;
	}

	public void setLanguageID(ArrayList languageID) {
		this.languageID = languageID;
	}

	public ArrayList getLanguageValueList() {
		return languageValueList;
	}

	public void setLanguageValueList(ArrayList languageValueList) {
		this.languageValueList = languageValueList;
	}

	public ArrayList getIndustyID() {
		return industyID;
	}

	public void setIndustyID(ArrayList industyID) {
		this.industyID = industyID;
	}

	public ArrayList getIndustyValueList() {
		return industyValueList;
	}

	public void setIndustyValueList(ArrayList industyValueList) {
		this.industyValueList = industyValueList;
	}

	public ArrayList getQulificationID() {
		return qulificationID;
	}

	public void setQulificationID(ArrayList qulificationID) {
		this.qulificationID = qulificationID;
	}

	public ArrayList getQulificationValueList() {
		return qulificationValueList;
	}

	public void setQulificationValueList(ArrayList qulificationValueList) {
		this.qulificationValueList = qulificationValueList;
	}

	public ArrayList getEduIDList() {
		return eduIDList;
	}

	public void setEduIDList(ArrayList eduIDList) {
		this.eduIDList = eduIDList;
	}

	public ArrayList getEduValueList() {
		return eduValueList;
	}

	public void setEduValueList(ArrayList eduValueList) {
		this.eduValueList = eduValueList;
	}

	public ArrayList getRelationIDList() {
		return relationIDList;
	}

	public void setRelationIDList(ArrayList relationIDList) {
		this.relationIDList = relationIDList;
	}

	public ArrayList getRelationValueList() {
		return relationValueList;
	}

	public void setRelationValueList(ArrayList relationValueList) {
		this.relationValueList = relationValueList;
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

	public String[] getId1() {
		return id1;
	}

	public void setId1(String[] id1) {
		this.id1 = id1;
	}

	public String[] getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String[] idAddress) {
		this.idAddress = idAddress;
	}

	public String[] getCareofcontactname1() {
		return careofcontactname1;
	}

	public void setCareofcontactname1(String[] careofcontactname1) {
		this.careofcontactname1 = careofcontactname1;
	}

	public String[] getHouseNumber1() {
		return houseNumber1;
	}

	public void setHouseNumber1(String[] houseNumber1) {
		this.houseNumber1 = houseNumber1;
	}

	public String[] getAddressLine11() {
		return addressLine11;
	}

	public void setAddressLine11(String[] addressLine11) {
		this.addressLine11 = addressLine11;
	}

	public String[] getAddressLine21() {
		return addressLine21;
	}

	public void setAddressLine21(String[] addressLine21) {
		this.addressLine21 = addressLine21;
	}

	public String[] getAddressLine31() {
		return addressLine31;
	}

	public void setAddressLine31(String[] addressLine31) {
		this.addressLine31 = addressLine31;
	}

	public String[] getPostalCode1() {
		return postalCode1;
	}

	public void setPostalCode1(String[] postalCode1) {
		this.postalCode1 = postalCode1;
	}

	public String[] getCity1() {
		return city1;
	}

	public void setCity1(String[] city1) {
		this.city1 = city1;
	}

	public String[] getState1() {
		return state1;
	}

	public void setState1(String[] state1) {
		this.state1 = state1;
	}

	public String[] getCountry1() {
		return country1;
	}

	public void setCountry1(String[] country1) {
		this.country1 = country1;
	}

	public String[] getOwnAccomodation1() {
		return ownAccomodation1;
	}

	public void setOwnAccomodation1(String[] ownAccomodation1) {
		this.ownAccomodation1 = ownAccomodation1;
	}

	public String[] getAddressType1() {
		return addressType1;
	}

	public void setAddressType1(String[] addressType1) {
		this.addressType1 = addressType1;
	}

	public String[] getCompanyHousing1() {
		return companyHousing1;
	}

	public void setCompanyHousing1(String[] companyHousing1) {
		this.companyHousing1 = companyHousing1;
	}

	public String[] getAddStartDate1() {
		return addStartDate1;
	}

	public void setAddStartDate1(String[] addStartDate1) {
		this.addStartDate1 = addStartDate1;
	}

	public String[] getAddEndDate1() {
		return addEndDate1;
	}

	public void setAddEndDate1(String[] addEndDate1) {
		this.addEndDate1 = addEndDate1;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	

	public String[] getSelectAddress() {
		return selectAddress;
	}

	public void setSelectAddress(String[] selectAddress) {
		this.selectAddress = selectAddress;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getCreationdate() {
		return creationdate;
	}

	public void setCreationdate(String creationdate) {
		this.creationdate = creationdate;
	}

	public String getTimeofEntry() {
		return timeofEntry;
	}

	public void setTimeofEntry(String timeofEntry) {
		this.timeofEntry = timeofEntry;
	}

	public String getUserName1() {
		return userName1;
	}

	public void setUserName1(String userName1) {
		this.userName1 = userName1;
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

	
	public String getNoOfChildrens() {
		return noOfChildrens;
	}

	public void setNoOfChildrens(String noOfChildrens) {
		this.noOfChildrens = noOfChildrens;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCompanyHousing() {
		return companyHousing;
	}

	public void setCompanyHousing(String companyHousing) {
		this.companyHousing = companyHousing;
	}

	public String getAddStartDate() {
		return addStartDate;
	}

	public void setAddStartDate(String addStartDate) {
		this.addStartDate = addStartDate;
	}

	public String getAddEndDate() {
		return addEndDate;
	}

	public void setAddEndDate(String addEndDate) {
		this.addEndDate = addEndDate;
	}

	public String getLangSpeaking() {
		return langSpeaking;
	}

	public void setLangSpeaking(String langSpeaking) {
		this.langSpeaking = langSpeaking;
	}

	public String getLangRead() {
		return langRead;
	}

	public void setLangRead(String langRead) {
		this.langRead = langRead;
	}

	public String getLangWrite() {
		return langWrite;
	}

	public void setLangWrite(String langWrite) {
		this.langWrite = langWrite;
	}

	public String getLangendDate() {
		return langendDate;
	}

	public void setLangendDate(String langendDate) {
		this.langendDate = langendDate;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}


	public String getLangstartDate() {
		return langstartDate;
	}

	public void setLangstartDate(String langstartDate) {
		this.langstartDate = langstartDate;
	}

	public String getNameOfEmployer() {
		return nameOfEmployer;
	}

	public void setNameOfEmployer(String nameOfEmployer) {
		this.nameOfEmployer = nameOfEmployer;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getExCity() {
		return exCity;
	}

	public void setExCity(String exCity) {
		this.exCity = exCity;
	}

	

	

	
	public String getExcountry() {
		return excountry;
	}

	public void setExcountry(String excountry) {
		this.excountry = excountry;
	}

	public String getPositionHeld() {
		return positionHeld;
	}

	public void setPositionHeld(String positionHeld) {
		this.positionHeld = positionHeld;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public String getFamilyDependentTypeID() {
		return familyDependentTypeID;
	}

	public void setFamilyDependentTypeID(String familyDependentTypeID) {
		this.familyDependentTypeID = familyDependentTypeID;
	}

	

	
	public String getFtitle() {
		return ftitle;
	}

	public void setFtitle(String ftitle) {
		this.ftitle = ftitle;
	}

	public String getFfirstName() {
		return ffirstName;
	}

	public void setFfirstName(String ffirstName) {
		this.ffirstName = ffirstName;
	}

	public String getFmiddleName() {
		return fmiddleName;
	}

	public void setFmiddleName(String fmiddleName) {
		this.fmiddleName = fmiddleName;
	}

	public String getFlastName() {
		return flastName;
	}

	public void setFlastName(String flastName) {
		this.flastName = flastName;
	}

	public String getFinitials() {
		return finitials;
	}

	public void setFinitials(String finitials) {
		this.finitials = finitials;
	}

	public String getFgender() {
		return fgender;
	}

	public void setFgender(String fgender) {
		this.fgender = fgender;
	}

	public String getFdateofBirth() {
		return fdateofBirth;
	}

	public void setFdateofBirth(String fdateofBirth) {
		this.fdateofBirth = fdateofBirth;
	}

	public String getFbirthplace() {
		return fbirthplace;
	}

	public void setFbirthplace(String fbirthplace) {
		this.fbirthplace = fbirthplace;
	}

	public String getFtelephoneNumber() {
		return ftelephoneNumber;
	}

	public void setFtelephoneNumber(String ftelephoneNumber) {
		this.ftelephoneNumber = ftelephoneNumber;
	}

	public String getFmobileNumber() {
		return fmobileNumber;
	}

	public void setFmobileNumber(String fmobileNumber) {
		this.fmobileNumber = fmobileNumber;
	}

	public String getFemailAddress() {
		return femailAddress;
	}

	public void setFemailAddress(String femailAddress) {
		this.femailAddress = femailAddress;
	}

	public String getFbloodGroup() {
		return fbloodGroup;
	}

	public void setFbloodGroup(String fbloodGroup) {
		this.fbloodGroup = fbloodGroup;
	}

	public String getFdependent() {
		return fdependent;
	}

	public void setFdependent(String fdependent) {
		this.fdependent = fdependent;
	}

	public String getFemployeeofCompany() {
		return femployeeofCompany;
	}

	public void setFemployeeofCompany(String femployeeofCompany) {
		this.femployeeofCompany = femployeeofCompany;
	}

	public String getFemployeeNumber() {
		return femployeeNumber;
	}

	public void setFemployeeNumber(String femployeeNumber) {
		this.femployeeNumber = femployeeNumber;
	}

	public String getCareofcontactname() {
		return careofcontactname;
	}

	public void setCareofcontactname(String careofcontactname) {
		this.careofcontactname = careofcontactname;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOwnAccomodation() {
		return ownAccomodation;
	}

	public void setOwnAccomodation(String ownAccomodation) {
		this.ownAccomodation = ownAccomodation;
	}

	public String getPhysicallyChallenged() {
		return physicallyChallenged;
	}

	public void setPhysicallyChallenged(String physicallyChallenged) {
		this.physicallyChallenged = physicallyChallenged;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getCountryofBirth() {
		return countryofBirth;
	}

	public void setCountryofBirth(String countryofBirth) {
		this.countryofBirth = countryofBirth;
	}

	

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getReligiousDenomination() {
		return religiousDenomination;
	}

	public void setReligiousDenomination(String religiousDenomination) {
		this.religiousDenomination = religiousDenomination;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPermanentAccountNumber() {
		return permanentAccountNumber;
	}

	public void setPermanentAccountNumber(String permanentAccountNumber) {
		this.permanentAccountNumber = permanentAccountNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getPlaceofIssueofPassport() {
		return placeofIssueofPassport;
	}

	public void setPlaceofIssueofPassport(String placeofIssueofPassport) {
		this.placeofIssueofPassport = placeofIssueofPassport;
	}

	public String getDateofexpiryofPassport() {
		return dateofexpiryofPassport;
	}

	public void setDateofexpiryofPassport(String dateofexpiryofPassport) {
		this.dateofexpiryofPassport = dateofexpiryofPassport;
	}

	public String getPersonalIdentificationMarks() {
		return personalIdentificationMarks;
	}

	public void setPersonalIdentificationMarks(String personalIdentificationMarks) {
		this.personalIdentificationMarks = personalIdentificationMarks;
	}

	public String getDateofissueofPassport() {
		return dateofissueofPassport;
	}

	public void setDateofissueofPassport(String dateofissueofPassport) {
		this.dateofissueofPassport = dateofissueofPassport;
	}

	

	public String getEmergencyContactPersonName() {
		return emergencyContactPersonName;
	}

	public void setEmergencyContactPersonName(String emergencyContactPersonName) {
		this.emergencyContactPersonName = emergencyContactPersonName;
	}

	public String getEmergencyContactAddressLine1() {
		return emergencyContactAddressLine1;
	}

	public void setEmergencyContactAddressLine1(String emergencyContactAddressLine1) {
		this.emergencyContactAddressLine1 = emergencyContactAddressLine1;
	}

	public String getEmergencyContactAddressLine2() {
		return emergencyContactAddressLine2;
	}

	public void setEmergencyContactAddressLine2(String emergencyContactAddressLine2) {
		this.emergencyContactAddressLine2 = emergencyContactAddressLine2;
	}

	public String getEmergencyTelephoneNumber() {
		return emergencyTelephoneNumber;
	}

	public void setEmergencyTelephoneNumber(String emergencyTelephoneNumber) {
		this.emergencyTelephoneNumber = emergencyTelephoneNumber;
	}

	public String getEmergencyMobileNumber() {
		return emergencyMobileNumber;
	}

	public void setEmergencyMobileNumber(String emergencyMobileNumber) {
		this.emergencyMobileNumber = emergencyMobileNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPhysicallyChallengeddetails() {
		return physicallyChallengeddetails;
	}

	public void setPhysicallyChallengeddetails(String physicallyChallengeddetails) {
		this.physicallyChallengeddetails = physicallyChallengeddetails;
	}

	

	public String getEducationalLevel() {
		return educationalLevel;
	}

	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getUniversityName() {
		return universityName;
	}

	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}

	public String getUniverysityLocation() {
		return univerysityLocation;
	}

	public void setUniverysityLocation(String univerysityLocation) {
		this.univerysityLocation = univerysityLocation;
	}

	public String getEdstate() {
		return edstate;
	}

	public void setEdstate(String edstate) {
		this.edstate = edstate;
	}

	public String getEdcountry() {
		return edcountry;
	}

	public void setEdcountry(String edcountry) {
		this.edcountry = edcountry;
	}

	public String getDurationofCourse() {
		return durationofCourse;
	}

	public void setDurationofCourse(String durationofCourse) {
		this.durationofCourse = durationofCourse;
	}

	

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
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

	public String getFullTimePartTime() {
		return fullTimePartTime;
	}

	public void setFullTimePartTime(String fullTimePartTime) {
		this.fullTimePartTime = fullTimePartTime;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String[] getSelectFamily() {
		return selectFamily;
	}

	public void setSelectFamily(String[] selectFamily) {
		this.selectFamily = selectFamily;
	}

	public String[] getSelectEducation() {
		return selectEducation;
	}

	public void setSelectEducation(String[] selectEducation) {
		this.selectEducation = selectEducation;
	}

	public String[] getSelectExperience() {
		return selectExperience;
	}

	public void setSelectExperience(String[] selectExperience) {
		this.selectExperience = selectExperience;
	}

	public String[] getSelectLanguage() {
		return selectLanguage;
	}

	public void setSelectLanguage(String[] selectLanguage) {
		this.selectLanguage = selectLanguage;
	}

	public String[] getFamilyDependentTypeID1() {
		return familyDependentTypeID1;
	}

	public void setFamilyDependentTypeID1(String[] familyDependentTypeID1) {
		this.familyDependentTypeID1 = familyDependentTypeID1;
	}

	public String[] getFtitle1() {
		return ftitle1;
	}

	public void setFtitle1(String[] ftitle1) {
		this.ftitle1 = ftitle1;
	}

	public String[] getFfirstName1() {
		return ffirstName1;
	}

	public void setFfirstName1(String[] ffirstName1) {
		this.ffirstName1 = ffirstName1;
	}

	public String[] getFmiddleName1() {
		return fmiddleName1;
	}

	public void setFmiddleName1(String[] fmiddleName1) {
		this.fmiddleName1 = fmiddleName1;
	}

	public String[] getFlastName1() {
		return flastName1;
	}

	public void setFlastName1(String[] flastName1) {
		this.flastName1 = flastName1;
	}

	public String[] getFinitials1() {
		return finitials1;
	}

	public void setFinitials1(String[] finitials1) {
		this.finitials1 = finitials1;
	}

	public String[] getFgender1() {
		return fgender1;
	}

	public void setFgender1(String[] fgender1) {
		this.fgender1 = fgender1;
	}

	public String[] getFdateofBirth1() {
		return fdateofBirth1;
	}

	public void setFdateofBirth1(String[] fdateofBirth1) {
		this.fdateofBirth1 = fdateofBirth1;
	}

	public String[] getFbirthplace1() {
		return fbirthplace1;
	}

	public void setFbirthplace1(String[] fbirthplace1) {
		this.fbirthplace1 = fbirthplace1;
	}

	public String[] getFtelephoneNumber1() {
		return ftelephoneNumber1;
	}

	public void setFtelephoneNumber1(String[] ftelephoneNumber1) {
		this.ftelephoneNumber1 = ftelephoneNumber1;
	}

	public String[] getFmobileNumber1() {
		return fmobileNumber1;
	}

	public void setFmobileNumber1(String[] fmobileNumber1) {
		this.fmobileNumber1 = fmobileNumber1;
	}

	public String[] getFemailAddress1() {
		return femailAddress1;
	}

	public void setFemailAddress1(String[] femailAddress1) {
		this.femailAddress1 = femailAddress1;
	}

	public String[] getFbloodGroup1() {
		return fbloodGroup1;
	}

	public void setFbloodGroup1(String[] fbloodGroup1) {
		this.fbloodGroup1 = fbloodGroup1;
	}

	public String[] getFdependent1() {
		return fdependent1;
	}

	public void setFdependent1(String[] fdependent1) {
		this.fdependent1 = fdependent1;
	}

	public String[] getFemployeeofCompany1() {
		return femployeeofCompany1;
	}

	public void setFemployeeofCompany1(String[] femployeeofCompany1) {
		this.femployeeofCompany1 = femployeeofCompany1;
	}

	public String[] getFemployeeNumber1() {
		return femployeeNumber1;
	}

	public void setFemployeeNumber1(String[] femployeeNumber1) {
		this.femployeeNumber1 = femployeeNumber1;
	}

	public String[] getEducationalLevel1() {
		return educationalLevel1;
	}

	public void setEducationalLevel1(String[] educationalLevel1) {
		this.educationalLevel1 = educationalLevel1;
	}

	public String[] getQualification1() {
		return qualification1;
	}

	public void setQualification1(String[] qualification1) {
		this.qualification1 = qualification1;
	}

	public String[] getSpecialization1() {
		return specialization1;
	}

	public void setSpecialization1(String[] specialization1) {
		this.specialization1 = specialization1;
	}

	public String[] getUniversityName1() {
		return universityName1;
	}

	public void setUniversityName1(String[] universityName1) {
		this.universityName1 = universityName1;
	}

	public String[] getUniverysityLocation1() {
		return univerysityLocation1;
	}

	public void setUniverysityLocation1(String[] univerysityLocation1) {
		this.univerysityLocation1 = univerysityLocation1;
	}

	public String[] getEdstate1() {
		return edstate1;
	}

	public void setEdstate1(String[] edstate1) {
		this.edstate1 = edstate1;
	}

	public String[] getEdcountry1() {
		return edcountry1;
	}

	public void setEdcountry1(String[] edcountry1) {
		this.edcountry1 = edcountry1;
	}

	public String[] getDurationofCourse1() {
		return durationofCourse1;
	}

	public void setDurationofCourse1(String[] durationofCourse1) {
		this.durationofCourse1 = durationofCourse1;
	}

	public String[] getTimes1() {
		return times1;
	}

	public void setTimes1(String[] times1) {
		this.times1 = times1;
	}

	public String[] getFromDate1() {
		return fromDate1;
	}

	public void setFromDate1(String[] fromDate1) {
		this.fromDate1 = fromDate1;
	}

	public String[] getToDate1() {
		return toDate1;
	}

	public void setToDate1(String[] toDate1) {
		this.toDate1 = toDate1;
	}

	public String[] getFullTimePartTime1() {
		return fullTimePartTime1;
	}

	public void setFullTimePartTime1(String[] fullTimePartTime1) {
		this.fullTimePartTime1 = fullTimePartTime1;
	}

	public String[] getPercentage1() {
		return percentage1;
	}

	public void setPercentage1(String[] percentage1) {
		this.percentage1 = percentage1;
	}

	public String[] getNameOfEmployer1() {
		return nameOfEmployer1;
	}

	public void setNameOfEmployer1(String[] nameOfEmployer1) {
		this.nameOfEmployer1 = nameOfEmployer1;
	}

	public String[] getIndustry1() {
		return industry1;
	}

	public void setIndustry1(String[] industry1) {
		this.industry1 = industry1;
	}

	public String[] getExCity1() {
		return exCity1;
	}

	public void setExCity1(String[] exCity1) {
		this.exCity1 = exCity1;
	}

	public String[] getExcountry1() {
		return excountry1;
	}

	public void setExcountry1(String[] excountry1) {
		this.excountry1 = excountry1;
	}

	public String[] getPositionHeld1() {
		return positionHeld1;
	}

	public void setPositionHeld1(String[] positionHeld1) {
		this.positionHeld1 = positionHeld1;
	}

	public String[] getJobRole1() {
		return jobRole1;
	}

	public void setJobRole1(String[] jobRole1) {
		this.jobRole1 = jobRole1;
	}

	public String[] getStartDate1() {
		return startDate1;
	}

	public void setStartDate1(String[] startDate1) {
		this.startDate1 = startDate1;
	}

	public String[] getEndDate1() {
		return endDate1;
	}

	public void setEndDate1(String[] endDate1) {
		this.endDate1 = endDate1;
	}

	public String[] getLanguage1() {
		return language1;
	}

	public void setLanguage1(String[] language1) {
		this.language1 = language1;
	}

	public String[] getMotherTongue1() {
		return motherTongue1;
	}

	public void setMotherTongue1(String[] motherTongue1) {
		this.motherTongue1 = motherTongue1;
	}

	public String[] getLangSpeaking1() {
		return langSpeaking1;
	}

	public void setLangSpeaking1(String[] langSpeaking1) {
		this.langSpeaking1 = langSpeaking1;
	}

	public String[] getLangRead1() {
		return langRead1;
	}

	public void setLangRead1(String[] langRead1) {
		this.langRead1 = langRead1;
	}

	public String[] getLangWrite1() {
		return langWrite1;
	}

	public void setLangWrite1(String[] langWrite1) {
		this.langWrite1 = langWrite1;
	}

	public String[] getLangstartDate1() {
		return langstartDate1;
	}

	public void setLangstartDate1(String[] langstartDate1) {
		this.langstartDate1 = langstartDate1;
	}

	public String[] getLangendDate1() {
		return langendDate1;
	}

	public void setLangendDate1(String[] langendDate1) {
		this.langendDate1 = langendDate1;
	}

	public FormFile getEmpEdu() {
		return empEdu;
	}

	public void setEmpEdu(FormFile empEdu) {
		this.empEdu = empEdu;
	}

	public String getEmpEduDoc() {
		return empEduDoc;
	}

	public void setEmpEduDoc(String empEduDoc) {
		this.empEduDoc = empEduDoc;
	}

	
	
}
