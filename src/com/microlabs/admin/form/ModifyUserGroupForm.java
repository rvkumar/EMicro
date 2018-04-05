package com.microlabs.admin.form;
import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.microlabs.utilities.IdValuePair;

public class ModifyUserGroupForm extends ActionForm{
	
	
	private String searchRequired;
	
	private String groupname;
	private String egroupname;
	private String groupid;
	private String status;
	private String firstName;
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String groupcount;
	private String department;
	private String searchQuery;
	private String empcode;
	private String designation;
	private String reqempcode;
	private String[] cp_new2;
	
	
	private String locationId;
	private String group;
	
	private ArrayList groupIdList=new ArrayList();
	private ArrayList groupLabelList=new ArrayList();
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private ArrayList departmentList= new ArrayList();
	
	private ArrayList<String> selectedSubLinks;
	
	
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	
	public ArrayList getGroupIdList() {
		return groupIdList;
	}
	public void setGroupIdList(ArrayList groupIdList) {
		this.groupIdList = groupIdList;
	}
	public ArrayList getGroupLabelList() {
		return groupLabelList;
	}
	public void setGroupLabelList(ArrayList groupLabelList) {
		this.groupLabelList = groupLabelList;
	}

	private String message;
	private String groupName;
	private ArrayList<String> groups; 
	
	private ArrayList<IdValuePair> modules;
	private ArrayList<IdValuePair> links;
	
	private String[] selectedLinksArr;
	private String moduleName;
	private ArrayList<String> allGroupIds;
	private String userName;
	private String subModuleName;
	
	private ArrayList<IdValuePair<Integer,String>> centersList;
	private String center;
	private ArrayList<IdValuePair<String,String>> user_name_id;
	private ArrayList<IdValuePair> subModules;
	private ArrayList<IdValuePair> subLinks;
	public String getSearchRequired() {
		return searchRequired;
	}
	public void setSearchRequired(String searchRequired) {
		this.searchRequired = searchRequired;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
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
	public ArrayList<IdValuePair> getSubModules() {
		return subModules;
	}
	public void setSubModules(ArrayList<IdValuePair> subModules) {
		this.subModules = subModules;
	}
	public ArrayList<IdValuePair> getSubLinks() {
		return subLinks;
	}
	public void setSubLinks(ArrayList<IdValuePair> subLinks) {
		this.subLinks = subLinks;
	}
	public String getGroupcount() {
		return groupcount;
	}
	public void setGroupcount(String groupcount) {
		this.groupcount = groupcount;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmpcode() {
		return empcode;
	}
	public void setEmpcode(String empcode) {
		this.empcode = empcode;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
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
	public ArrayList getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
	}
	public String getSearchQuery() {
		return searchQuery;
	}
	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}
	public String getReqempcode() {
		return reqempcode;
	}
	public void setReqempcode(String reqempcode) {
		this.reqempcode = reqempcode;
	}
	public String[] getCp_new2() {
		return cp_new2;
	}
	public void setCp_new2(String[] cp_new2) {
		this.cp_new2 = cp_new2;
	}
	public ArrayList<String> getSelectedSubLinks() {
		return selectedSubLinks;
	}
	public void setSelectedSubLinks(ArrayList<String> selectedSubLinks) {
		this.selectedSubLinks = selectedSubLinks;
	}
	public String getEgroupname() {
		return egroupname;
	}
	public void setEgroupname(String egroupname) {
		this.egroupname = egroupname;
	}
	
	
	
	

}
