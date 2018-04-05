package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class AnnouncementForm extends ActionForm {

	private int id;
	private String headLinesType;
	private String annoucementDate;
	private String announcementTime;

	private FormFile fileNames;
	private String content;
	private String subject;
	private FormFile videoFileNames;
	private String videoFilesList;
	private ArrayList videoList = new ArrayList();

	private String saveType;

	private ArrayList locationIdList = new ArrayList();
	private ArrayList locationLabelList = new ArrayList();

	private ArrayList deptIdList = new ArrayList();
	private ArrayList deptLabelList = new ArrayList();

	private String department[];

	private String locationId[];

	private String cat[];

	private String pernr[];

	private String message;
	private String message2;
	
	private int startRecord;
	private int endRecord;
	private int totalRecords;

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

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String[] getDepartment() {
		return department;
	}

	public void setDepartment(String[] department) {
		this.department = department;
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

	public String[] getPernr() {
		return pernr;
	}

	public void setPernr(String[] pernr) {
		this.pernr = pernr;
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

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getVideoFilesList() {
		return videoFilesList;
	}

	public void setVideoFilesList(String videoFilesList) {
		this.videoFilesList = videoFilesList;
	}

	public ArrayList getVideoList() {
		return videoList;
	}

	public void setVideoList(ArrayList videoList) {
		this.videoList = videoList;
	}

	public FormFile getVideoFileNames() {
		return videoFileNames;
	}

	public void setVideoFileNames(FormFile videoFileNames) {
		this.videoFileNames = videoFileNames;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getHeadLinesType() {
		return headLinesType;
	}

	public void setHeadLinesType(String headLinesType) {
		this.headLinesType = headLinesType;
	}

	public FormFile getFileNames() {
		return fileNames;
	}

	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
