package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.microlabs.utilities.IdValuePair;

public class UserGroupForm extends ActionForm{
	
	private String message;
	private String groupName;
	private ArrayList<String> groups; 
	
	private String contentDescription;
	private String fileFullPath;
	private String videoFullPath;
	private ArrayList<IdValuePair> modules;
	
	
	private ArrayList<IdValuePair> mainModules;
	
	private ArrayList<IdValuePair> links;
	private ArrayList<IdValuePair> locations;
	
	private String[] selectedLinksArr;
	private String moduleName;
	private ArrayList<String> allGroupIds;
	private String userName;
	
	private ArrayList<IdValuePair<Integer,String>> centersList;
	private String center;
	private ArrayList<IdValuePair<String,String>> user_name_id;
	
	private ArrayList<IdValuePair> subLinks;
	private String subModuleName;
	
	private ArrayList<IdValuePair> subModules;
	private String statusMessage;
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	public ArrayList<IdValuePair> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<IdValuePair> locations) {
		this.locations = locations;
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
	public String getContentDescription() {
		return contentDescription;
	}
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
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
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public ArrayList<IdValuePair> getSubModules() {
		return subModules;
	}
	public void setSubModules(ArrayList<IdValuePair> subModules) {
		this.subModules = subModules;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	public ArrayList<IdValuePair> getMainModules() {
		return mainModules;
	}
	public void setMainModules(ArrayList<IdValuePair> mainModules) {
		this.mainModules = mainModules;
	}
	public ArrayList<IdValuePair> getSubLinks() {
		return subLinks;
	}
	public void setSubLinks(ArrayList<IdValuePair> subLinks) {
		this.subLinks = subLinks;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public ArrayList<String> getGroups() {
		return groups;
	}
	public void setGroups(ArrayList<String> groups) {
		this.groups = groups;
	}
	public ArrayList<IdValuePair> getModules() {
		return modules;
	}
	public void setModules(ArrayList<IdValuePair> modules) {
		this.modules = modules;
	}
	public ArrayList<IdValuePair> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<IdValuePair> links) {
		this.links = links;
	}
	public String[] getSelectedLinksArr() {
		return selectedLinksArr;
	}
	public void setSelectedLinksArr(String[] selectedLinksArr) {
		this.selectedLinksArr = selectedLinksArr;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public ArrayList<String> getAllGroupIds() {
		return allGroupIds;
	}
	public void setAllGroupIds(ArrayList<String> allGroupIds) {
		this.allGroupIds = allGroupIds;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public ArrayList<IdValuePair<Integer, String>> getCentersList() {
		return centersList;
	}
	public void setCentersList(ArrayList<IdValuePair<Integer, String>> centersList) {
		this.centersList = centersList;
	}
	public String getCenter() {
		return center;
	}
	public void setCenter(String center) {
		this.center = center;
	}
	public ArrayList<IdValuePair<String, String>> getUser_name_id() {
		return user_name_id;
	}
	public void setUser_name_id(ArrayList<IdValuePair<String, String>> user_name_id) {
		this.user_name_id = user_name_id;
	}
	
	
	
	
	
	
	
	
	
	
}
