package com.microlabs.newsandmedia.form;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;


public class FckEditorForm extends ActionForm {

	private String linkName;
	private String subLinkName;
	private String message;
	private String subLinkId;
	private String linkPath;
	private String methodName;
	private String priority;
	private String contentDescription;
	private String underSubLinks;
	private String module;
	private FormFile fileNames;
	private FormFile videoFileNames;
	private String fileList;
	private String[] select;
	private String videoFilesList;
	private String[] select1;
	private ArrayList linkIdList = new ArrayList();
	private ArrayList linkValueList = new ArrayList();
	
	private String contentDescription1;
	private String linkId;
	private String mainLinkName;
	
	private ArrayList subLinkIdList = new ArrayList();
	private ArrayList subLinkValueList = new ArrayList();

	
	private ArrayList filenamelist = new ArrayList();
	
	private ArrayList videoList = new ArrayList();
	
	private String[] modifyPriority;
	private String[] reqIDs;

	
	
	public String[] getReqIDs() {
		return reqIDs;
	}

	public void setReqIDs(String[] reqIDs) {
		this.reqIDs = reqIDs;
	}

	public String[] getModifyPriority() {
		return modifyPriority;
	}

	public void setModifyPriority(String[] modifyPriority) {
		this.modifyPriority = modifyPriority;
	}

	public String getContentDescription1() {
		return contentDescription1;
	}

	public void setContentDescription1(String contentDescription1) {
		this.contentDescription1 = contentDescription1;
	}

	public String getLinkId() {
		return linkId;
	}

	public void setLinkId(String linkId) {
		this.linkId = linkId;
	}

	public String getMainLinkName() {
		return mainLinkName;
	}

	public void setMainLinkName(String mainLinkName) {
		this.mainLinkName = mainLinkName;
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

	public String[] getSelect() {
		return select;
	}

	public void setSelect(String[] select) {
		this.select = select;
	}

	public String getFileList() {
		return fileList;
	}

	public void setFileList(String fileList) {
		this.fileList = fileList;
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

	public FormFile getFileNames() {
		return fileNames;
	}

	public void setFileNames(FormFile fileNames) {
		this.fileNames = fileNames;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getUnderSubLinks() {
		return underSubLinks;
	}

	public void setUnderSubLinks(String underSubLinks) {
		this.underSubLinks = underSubLinks;
	}

	public String getContentDescription() {
		return contentDescription;
	}

	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}

	public String getLinkPath() {
		return linkPath;
	}

	public void setLinkPath(String linkPath) {
		this.linkPath = linkPath;
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

	public ArrayList getSubLinkIdList() {
		return subLinkIdList;
	}

	public void setSubLinkIdList(ArrayList subLinkIdList) {
		this.subLinkIdList = subLinkIdList;
	}

	public ArrayList getSubLinkValueList() {
		return subLinkValueList;
	}

	public void setSubLinkValueList(ArrayList subLinkValueList) {
		this.subLinkValueList = subLinkValueList;
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

	public ArrayList getFilenamelist() {
		return filenamelist;
	}

	public void setFilenamelist(ArrayList filenamelist) {
		this.filenamelist = filenamelist;
	}
	
}
