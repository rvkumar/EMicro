package com.microlabs.newsandmedia.form;

import org.apache.struts.action.ActionForm;

public class NewsAndMediaForm extends ActionForm{
	private String contentDescription;
	private String fileFullPath;
	private String videoFullPath;
	private String iconName;
	
	private String linkPath;
	private String method;
	private String module;
	private String linkName;
	private String imageName;
	private String divStatus;
	private String linkTitle;
	private String linkId;
	private String contentYear;
	
	private String message;
	
	private String loginUserID;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	
	
	
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

	public String getLoginUserID() {
		return loginUserID;
	}

	public void setLoginUserID(String loginUserID) {
		this.loginUserID = loginUserID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getContentYear() {
		return contentYear;
	}

	public void setContentYear(String contentYear) {
		this.contentYear = contentYear;
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

	public String getLinkTitle() {
		return linkTitle;
	}

	public void setLinkTitle(String linkTitle) {
		this.linkTitle = linkTitle;
	}

	public String getDivStatus() {
		return divStatus;
	}

	public void setDivStatus(String divStatus) {
		this.divStatus = divStatus;
	}

	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
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

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
}
