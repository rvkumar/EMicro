package com.microlabs.main.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class MainForm extends ActionForm{
	
	private String loginType;
	private String userName;
	private String password;
	
		
	private String contentDescription;
	
	
	
	private String reqDay;
	private String dayDetails;
	
	private String linkName;
	private String subLinkName;
	
	private String subLinkId;
	private String message;
	private String message2;
	
	private ArrayList linkIdList=new ArrayList();
	private ArrayList linkValueList=new ArrayList();
	
	//pending request
	private String req_id;
	private String request_name;
	private String req_type;
	private String req_date;
	private String last_approver;
	
	
	public String getReq_id() {
		return req_id;
	}
	public void setReq_id(String req_id) {
		this.req_id = req_id;
	}
	public String getRequest_name() {
		return request_name;
	}
	public void setRequest_name(String request_name) {
		this.request_name = request_name;
	}
	public String getReq_type() {
		return req_type;
	}
	public void setReq_type(String req_type) {
		this.req_type = req_type;
	}
	public String getReq_date() {
		return req_date;
	}
	public void setReq_date(String req_date) {
		this.req_date = req_date;
	}
	public String getLast_approver() {
		return last_approver;
	}
	public void setLast_approver(String last_approver) {
		this.last_approver = last_approver;
	}
	//announcement
	private int id;
	private String headLines;
	private String annoucementDate;
	private String announcementTime;
	private FormFile fileNames;
	private String announcementLinkName;
	private String linkDescription;
	private String gifFile;
	
	private String empOldPwd;
	private String oldPassword; 
	private String newPassword;
	private String conformPassword;
	private String statusMessage;
	private String favoritQues;
	private String favAns;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String requiredType;
	private String empName;
	private String empPhoto;
	private String gender;
	private String employeeNo;
	private String thoughtMsg;
	private String managementVideo;
	private int pwdExpDays;
	private String announcementMonth;
	private String announcementDay;
	
	
	private String alertmessage;
	private String alertheader;
	
	private String staffCat;
	
	
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public String getAlertmessage() {
		return alertmessage;
	}
	public void setAlertmessage(String alertmessage) {
		this.alertmessage = alertmessage;
	}
	public String getAlertheader() {
		return alertheader;
	}
	public void setAlertheader(String alertheader) {
		this.alertheader = alertheader;
	}
	public String getAnnouncementMonth() {
		return announcementMonth;
	}
	public void setAnnouncementMonth(String announcementMonth) {
		this.announcementMonth = announcementMonth;
	}
	public String getAnnouncementDay() {
		return announcementDay;
	}
	public void setAnnouncementDay(String announcementDay) {
		this.announcementDay = announcementDay;
	}
	public int getPwdExpDays() {
		return pwdExpDays;
	}
	public void setPwdExpDays(int pwdExpDays) {
		this.pwdExpDays = pwdExpDays;
	}
	public String getManagementVideo() {
		return managementVideo;
	}
	public void setManagementVideo(String managementVideo) {
		this.managementVideo = managementVideo;
	}
	public String getThoughtMsg() {
		return thoughtMsg;
	}
	public void setThoughtMsg(String thoughtMsg) {
		this.thoughtMsg = thoughtMsg;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpPhoto() {
		return empPhoto;
	}
	public void setEmpPhoto(String empPhoto) {
		this.empPhoto = empPhoto;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public String getRequiredType() {
		return requiredType;
	}
	public void setRequiredType(String requiredType) {
		this.requiredType = requiredType;
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
	public String getFavoritQues() {
		return favoritQues;
	}
	public void setFavoritQues(String favoritQues) {
		this.favoritQues = favoritQues;
	}
	public String getFavAns() {
		return favAns;
	}
	public void setFavAns(String favAns) {
		this.favAns = favAns;
	}
	public String getGifFile() {
		return gifFile;
	}
	public void setGifFile(String gifFile) {
		this.gifFile = gifFile;
	}
	public String getSubLinkId() {
		return subLinkId;
	}
	public void setSubLinkId(String subLinkId) {
		this.subLinkId = subLinkId;
	}
	public ArrayList getLinkIdList() {
		return linkIdList;
	}
	public void setLinkIdList(ArrayList linkIdList) {
		this.linkIdList = linkIdList;
	}
	public ArrayList getLinkValueList() {
		return linkValueList;
	}
	public void setLinkValueList(ArrayList linkValueList) {
		this.linkValueList = linkValueList;
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
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getHeadLines() {
		return headLines;
	}
	public void setHeadLines(String headLines) {
		this.headLines = headLines;
	}
	public String getAnnoucementDate() {
		return annoucementDate;
	}
	public void setAnnoucementDate(String annoucementDate) {
		this.annoucementDate = annoucementDate;
	}
	public String getAnnouncementTime() {
		return announcementTime;
	}
	public void setAnnouncementTime(String announcementTime) {
		this.announcementTime = announcementTime;
	}
	public FormFile getFileNames() {
		return fileNames;
	}
	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
	}
	public String getAnnouncementLinkName() {
		return announcementLinkName;
	}
	public void setAnnouncementLinkName(String announcementLinkName) {
		this.announcementLinkName = announcementLinkName;
	}
	public String getLinkDescription() {
		return linkDescription;
	}
	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
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
	public String getContentDescription() {
		return contentDescription;
	}
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReqDay() {
		return reqDay;
	}
	public void setReqDay(String reqDay) {
		this.reqDay = reqDay;
	}
	public String getDayDetails() {
		return dayDetails;
	}
	public void setDayDetails(String dayDetails) {
		this.dayDetails = dayDetails;
	}
	public String getEmpOldPwd() {
		return empOldPwd;
	}
	public void setEmpOldPwd(String empOldPwd) {
		this.empOldPwd = empOldPwd;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConformPassword() {
		return conformPassword;
	}
	public void setConformPassword(String conformPassword) {
		this.conformPassword = conformPassword;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getStaffCat() {
		return staffCat;
	}
	public void setStaffCat(String staffCat) {
		this.staffCat = staffCat;
	}
	
}
