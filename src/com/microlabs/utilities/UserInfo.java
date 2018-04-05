package com.microlabs.utilities;

import java.util.ArrayList;

public class UserInfo {
	  private int id;
	  private String firstName;
	  private String middleName;
	  private String lastName;
	  private String userName;
	  private String fullName;
	  private String shortName;
	  private String S_ID;
	  private String groupName;
	  private String person;
	  private String userType;
	  private int groupId=-1;
	  private String mail_id;
	  private ArrayList<String> centerIds;
	  private ArrayList<String> includeLinks;
	  private ArrayList<String> excludeLinks;
	  private String includeSubLinks;
	  private String includeSubSubLinks;
	  private String includeLinks1;
	  
	  private String departmentId;
	  private String countryId;
	  private String stateId;
	  private String designationId;
	  private String plantId;
	  
	  private String userstatus;
	  private String lastLoginDate;
	  private String passwordExpiryDate;
	  private String password;
	  private String employeeNo;
	  private String loginCount;
	  private int pwdExpDays;
	  private String availableLocations;
	  private String  locname;
	  private String hod;
	  private String empPhoto;
	  private String email;
	  
	  private String qmsplantId;
	  
	  private String sdptid;
	  private String repgrp;
	  
	  
	  private String staffcat;
	  
	  
	  
	  
	  
	  
		public String getStaffcat() {
			return staffcat;
		}
		public void setStaffcat(String staffcat) {
			this.staffcat = staffcat;
		}
	  
	  
	  
	public String getSdptid() {
		return sdptid;
	}
	public void setSdptid(String sdptid) {
		this.sdptid = sdptid;
	}
	public String getRepgrp() {
		return repgrp;
	}
	public void setRepgrp(String repgrp) {
		this.repgrp = repgrp;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public String getQmsplantId() {
		return qmsplantId;
	}
	public void setQmsplantId(String qmsplantId) {
		this.qmsplantId = qmsplantId;
	}
	public String getLocname() {
		return locname;
	}
	public void setLocname(String locname) {
		this.locname = locname;
	}
	public String getAvailableLocations() {
		return availableLocations;
	}
	public void setAvailableLocations(String availableLocations) {
		this.availableLocations = availableLocations;
	}
	public int getPwdExpDays() {
		return pwdExpDays;
	}
	public void setPwdExpDays(int pwdExpDays) {
		this.pwdExpDays = pwdExpDays;
	}
	public String getLoginCount() {
		return loginCount;
	}
	public void setLoginCount(String loginCount) {
		this.loginCount = loginCount;
	}
	public String empcode() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getPasswordExpiryDate() {
		return passwordExpiryDate;
	}
	public void setPasswordExpiryDate(String passwordExpiryDate) {
		this.passwordExpiryDate = passwordExpiryDate;
	}
	public String getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public String getUserstatus() {
		return userstatus;
	}
	public void setUserstatus(String userstatus) {
		this.userstatus = userstatus;
	}
	public String getPlantId() {
		return plantId;
	}
	public void setPlantId(String plantId) {
		this.plantId = plantId;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getStateId() {
		return stateId;
	}
	public void setStateId(String stateId) {
		this.stateId = stateId;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getIncludeLinks1() {
		return includeLinks1;
	}
	public void setIncludeLinks1(String includeLinks1) {
		this.includeLinks1 = includeLinks1;
	}
	public String getIncludeSubLinks() {
		return includeSubLinks;
	}
	public void setIncludeSubLinks(String includeSubLinks) {
		this.includeSubLinks = includeSubLinks;
	}
	public String getIncludeSubSubLinks() {
		return includeSubSubLinks;
	}
	public void setIncludeSubSubLinks(String includeSubSubLinks) {
		this.includeSubSubLinks = includeSubSubLinks;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getS_ID() {
		return S_ID;
	}
	public void setS_ID(String s_ID) {
		S_ID = s_ID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getMail_id() {
		return mail_id;
	}
	public void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}
	public ArrayList<String> getCenterIds() {
		return centerIds;
	}
	public void setCenterIds(ArrayList<String> centerIds) {
		this.centerIds = centerIds;
	}
	public ArrayList<String> getIncludeLinks() {
		return includeLinks;
	}
	public void setIncludeLinks(ArrayList<String> includeLinks) {
		this.includeLinks = includeLinks;
	}
	public ArrayList<String> getExcludeLinks() {
		return excludeLinks;
	}
	public void setExcludeLinks(ArrayList<String> excludeLinks) {
		this.excludeLinks = excludeLinks;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getHod() {
		return hod;
	}
	public void setHod(String hod) {
		this.hod = hod;
	}
	public String getEmpPhoto() {
		return empPhoto;
	}
	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	  
}
