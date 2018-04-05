package com.microlabs.hr.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class HRCompanyDetailsForm extends ActionForm{
	
	private String contentDescription;
	private String contentDescriptionAdmin;
	private String underSubLinks;
	private String linkName;
	private String subLinkName;
	private String methodName;
	private String priority;
	private String linkPath;
	private String subLinkId;
	private String message;
	private FormFile fileNames;
	private FormFile videoFileNames;
	private FormFile iconNames;
	
	private String fileList;
	private String[] select;
	private String videoFilesList;
	private String[] select1;
	private String iconList;
	private String[] select2;
	
	private ArrayList filenamelist = new ArrayList();
	private ArrayList videoList = new ArrayList();
	private ArrayList linkIdList=new ArrayList();
	private ArrayList linkValueList=new ArrayList();
	private String linkId;
	
	private String fileFullPath;
	private String videoFullPath;
	
	
	
	
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
	public String getContentDescriptionAdmin() {
		return contentDescriptionAdmin;
	}
	public void setContentDescriptionAdmin(String contentDescriptionAdmin) {
		this.contentDescriptionAdmin = contentDescriptionAdmin;
	}
	public String getUnderSubLinks() {
		return underSubLinks;
	}
	public void setUnderSubLinks(String underSubLinks) {
		this.underSubLinks = underSubLinks;
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
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getLinkPath() {
		return linkPath;
	}
	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
	}
	public String getSubLinkId() {
		return subLinkId;
	}
	public void setSubLinkId(String subLinkId) {
		this.subLinkId = subLinkId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public FormFile getFileNames() {
		return fileNames;
	}
	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
	}
	public FormFile getVideoFileNames() {
		return videoFileNames;
	}
	public void setVideoFileNames(FormFile videoFileNames) {
		this.videoFileNames = videoFileNames;
	}
	public FormFile getIconNames() {
		return iconNames;
	}
	public void setIconNames(FormFile iconNames) {
		this.iconNames = iconNames;
	}
	public String getFileList() {
		return fileList;
	}
	public void setFileList(String fileList) {
		this.fileList = fileList;
	}
	public String[] getSelect() {
		return select;
	}
	public void setSelect(String[] select) {
		this.select = select;
	}
	public String getVideoFilesList() {
		return videoFilesList;
	}
	public void setVideoFilesList(String videoFilesList) {
		this.videoFilesList = videoFilesList;
	}
	public String[] getSelect1() {
		return select1;
	}
	public void setSelect1(String[] select1) {
		this.select1 = select1;
	}
	public String getIconList() {
		return iconList;
	}
	public void setIconList(String iconList) {
		this.iconList = iconList;
	}
	public String[] getSelect2() {
		return select2;
	}
	public void setSelect2(String[] select2) {
		this.select2 = select2;
	}
	public ArrayList getFilenamelist() {
		return filenamelist;
	}
	public void setFilenamelist(ArrayList filenamelist) {
		this.filenamelist = filenamelist;
	}
	public ArrayList getVideoList() {
		return videoList;
	}
	public void setVideoList(ArrayList videoList) {
		this.videoList = videoList;
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
	public String getLinkId() {
		return linkId;
	}
	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}
	
	
	

}
