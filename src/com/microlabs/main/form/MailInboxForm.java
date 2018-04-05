package com.microlabs.main.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import java.util.ArrayList;
import java.util.LinkedList;

public class MailInboxForm extends ActionForm{
	
	private String subject;
	private String description;
	private String dateTime;
	private String fromInbox;
	private String ccAddress;
	private String bccAddress;
	private String toAddress;
	private String message;
	private String attach;
	private String exactAttName;
	private String mailMessage;
	private FormFile documentFile;
	private FormFile documentFiles[];
	private String attachNames;
	private String mailId;
	private String uSER_NAME;
	public String getuSER_NAME() {
		return uSER_NAME;
	}

	public void setuSER_NAME(String uSER_NAME) {
		this.uSER_NAME = uSER_NAME;
	}
	private LinkedList fromAddressList;
	private ArrayList fileList=new ArrayList();
	private ArrayList SubjectList=new ArrayList();
	private String attLists;
	private ArrayList dateList=new ArrayList();
	
	private int totalMailCount;
	private int startMailCount;
	private int endMailCount;
	private int compId;
	private String read;
	private String searchAddress;
	
	private String[] chMail;
	
	private String uploadFilePath;

	private String fileList1;
	
	private String message2;
	
	private String fileSize;
	
	private String heading;
	private String searchText;
	private String method;
	private String password;
	private String toAddresses;
	private String empname;
	private String sendermail;
	
	
	
	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getSendermail() {
		return sendermail;
	}

	public void setSendermail(String sendermail) {
		this.sendermail = sendermail;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getFileList1() {
		return fileList1;
	}

	public void setFileList1(String fileList1) {
		this.fileList1 = fileList1;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getUploadFilePath() {
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath) {
		this.uploadFilePath = uploadFilePath;
	}

	public String[] getChRequestNumber() {
		return chMail;
	}

	public void setChRequestNumber(String[] chRequestNumber) {
		this.chMail = chRequestNumber;
	}
	
	public String getFromInbox() {
		return fromInbox;
	}
	public void setFromInbox(String fromInbox) {
		this.fromInbox = fromInbox;
	}
	
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	
	
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public String getccAddress() {
		return ccAddress;
	}
	public void setccAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}
	
	
	public String getbccAddress() {
		return bccAddress;
	}
	public void setbccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}
	
	
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getAttachNames() {
		return attachNames;
	}
	public void setAttachNames(String attachNames) {
		this.attachNames = attachNames;
	}
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	

	public FormFile getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}

	
	public String getMailMessage() {
		return mailMessage;
	}
	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}
	
	
	public LinkedList getFromAddressList() {
		return fromAddressList;
	}
	public void setFromAddressList(LinkedList fromAddressList) {
		this.fromAddressList = fromAddressList;
	}
	
	
	public ArrayList getFileList() {
		return fileList;
	}
	public void setFileList(ArrayList fileList) {
		this.fileList =fileList;
	}
	
	
	public ArrayList getSubjectList() {
		return SubjectList;
	}
	public void setSubjectList(ArrayList SubjectList) {
		this.SubjectList =SubjectList;
	}
	
	
	public ArrayList getDateList() {
		return dateList;
	}
	public void setDateList(ArrayList dateList) {
		this.dateList =dateList;
	}
	
	
	public String getAttLists() {
		return attLists;
	}
	public void setAttLists(String attLists) {
		this.attLists=attLists;
	}
	
	
	public int getStartMailCount() {
		return startMailCount;
	}
	public void setStartMailCount(int startMailCount) {
		this.startMailCount = startMailCount;
	}
	
	
	public int getEndMailCount() {
		return endMailCount;
	}
	public void setEndMailCount(int endMailCount) {
		this.endMailCount = endMailCount;
	}
	
	
	public int getTotalMailCount() {
		return totalMailCount;
	}
	public void setTotalMailCount(int totalMailCount) {
		this.totalMailCount = totalMailCount;
	}
	
	
	public String getMailId(){
		return mailId;
	}
	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
	
	
	public String getExactAttName(){
		return exactAttName;
	}
	public void setExactAttName(String exactAttName) {
		this.exactAttName = exactAttName;
	}
	
	
	public String getRead(){
		return read;
	}
	public void setRead(String read) {
		this.read = read;
	}

	public int getCompId() {
		return compId;
	}
	public void setCompId(int compId) {
		this.compId = compId;
	}
	
	public FormFile[] getDocumentFiles() {
		return documentFiles;
	}
	public void setDocumentFiles(FormFile[] documentFiles) {
		this.documentFiles = documentFiles;
	}
	
	public String getHeading(){
		return heading;
	}
	public void setHeading(String heading) {
		this.heading = heading;
	}
	
	public String getSearchText(){
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
	public String getSearchAddress() {
		return searchAddress;
	}
	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}
	
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getToAddresses() {
		return toAddresses;
	}
	public void setToAddresses(String toAddresses) {
		this.toAddresses = toAddresses;
	}
}
