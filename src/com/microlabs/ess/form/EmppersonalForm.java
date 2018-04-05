package com.microlabs.ess.form;

import org.apache.struts.action.ActionForm;

public class EmppersonalForm  extends ActionForm{

//Personal Information
	private String id;
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
	private String emergencyContactAddressLine2;
	private String emergencyTelephoneNumber;
	private String emergencyMobileNumber;
	private String noOfChildrens;
	/////////////////////////
	private String dateOfBirth_mod;
	private String dateofissueofPassport_mod;
	private String dateofexpiryofPassport_mod;
	
	
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
	
	
	//Experience
	
	private String  nameOfEmployer;
	private String  industry;
	private String  exCity;
	private String  excountry;
	private String  positionHeld;
	private String  jobRole;
	private String  startDate;
	private String  endDate;
	
	//Language
	
	private String  language;
	private String  motherTongue;
	private String  languageFluency;
	private String  langstartDate;
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
	public String getPhysicallyChallenged() {
		return physicallyChallenged;
	}
	public void setPhysicallyChallenged(String physicallyChallenged) {
		this.physicallyChallenged = physicallyChallenged;
	}
	public String getPhysicallyChallengeddetails() {
		return physicallyChallengeddetails;
	}
	public void setPhysicallyChallengeddetails(String physicallyChallengeddetails) {
		this.physicallyChallengeddetails = physicallyChallengeddetails;
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
	public String getLanguageFluency() {
		return languageFluency;
	}
	public void setLanguageFluency(String languageFluency) {
		this.languageFluency = languageFluency;
	}
	public String getLangstartDate() {
		return langstartDate;
	}
	public void setLangstartDate(String langstartDate) {
		this.langstartDate = langstartDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNoOfChildrens() {
		return noOfChildrens;
	}
	public void setNoOfChildrens(String noOfChildrens) {
		this.noOfChildrens = noOfChildrens;
	}
	
	public String getDateOfBirth_mod() {
		return dateOfBirth_mod;
	}
	public void setDateOfBirth_mod(String dateOfBirth_mod) {
		this.dateOfBirth_mod = dateOfBirth_mod;
	}
	public String getDateofissueofPassport_mod() {
		return dateofissueofPassport_mod;
	}
	public void setDateofissueofPassport_mod(String dateofissueofPassport_mod) {
		this.dateofissueofPassport_mod = dateofissueofPassport_mod;
	}
	public String getDateofexpiryofPassport_mod() {
		return dateofexpiryofPassport_mod;
	}
	public void setDateofexpiryofPassport_mod(String dateofexpiryofPassport_mod) {
		this.dateofexpiryofPassport_mod = dateofexpiryofPassport_mod;
	}
	
	
	
}
