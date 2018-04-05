package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.microlabs.utilities.IdValuePair;

public class UserRightsForm extends ActionForm{
	
	private String userName;
	private String message;
	private String fullName;
	private String groupName;
	private String moduleName;
	private ArrayList<IdValuePair> modules;
	private ArrayList<IdValuePair> links;
	private ArrayList<IdValuePair> locations;
	
	private ArrayList<String> allGroupIds;
	private ArrayList<IdValuePair> subLinks;
	private ArrayList<String> selectedSubLinks;
	private ArrayList<String> selectedModules;
	private ArrayList<String> selectedLocations;
	private String subModuleName;
	private ArrayList<IdValuePair> subModules;
	private ArrayList<IdValuePair> subSubLinks;
	private ArrayList<String> selectedSubSubModules;
	private String searchText;
	
	private ArrayList ar_id=new ArrayList();
    private ArrayList ar_name=new ArrayList();
    private String grouplistid;
    
    private  String moduleId;
	private ArrayList moduleIdList=new ArrayList();
	private ArrayList moduleLabelList=new ArrayList();
	
	private String pernr;
	private String loc;
	private String empname;
	private String dpt;
	private String desg;
	private String atten_loc;
	private String created_by;
	private String created_date;
	private String modified_by;	
	private String modified_date;
	
	
	
	
	
	
	public String getPernr() {
		return pernr;
	}
	public void setPernr(String pernr) {
		this.pernr = pernr;
	}
	public String getLoc() {
		return loc;
	}
	public void setLoc(String loc) {
		this.loc = loc;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getDpt() {
		return dpt;
	}
	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
	}

	public String getAtten_loc() {
		return atten_loc;
	}
	public void setAtten_loc(String atten_loc) {
		this.atten_loc = atten_loc;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getCreated_date() {
		return created_date;
	}
	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	public String getModified_by() {
		return modified_by;
	}
	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}
	public String getModified_date() {
		return modified_date;
	}
	public void setModified_date(String modified_date) {
		this.modified_date = modified_date;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public ArrayList getModuleIdList() {
		return moduleIdList;
	}
	public void setModuleIdList(ArrayList moduleIdList) {
		this.moduleIdList = moduleIdList;
	}
	public ArrayList getModuleLabelList() {
		return moduleLabelList;
	}
	public void setModuleLabelList(ArrayList moduleLabelList) {
		this.moduleLabelList = moduleLabelList;
	}
	public ArrayList<String> getSelectedLocations() {
		return selectedLocations;
	}
	public void setSelectedLocations(ArrayList<String> selectedLocations) {
		this.selectedLocations = selectedLocations;
	}
	public ArrayList<IdValuePair> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<IdValuePair> locations) {
		this.locations = locations;
	}
	public ArrayList getAr_id() {
		return ar_id;
	}
	public void setAr_id(ArrayList ar_id) {
		this.ar_id = ar_id;
	}
	public ArrayList getAr_name() {
		return ar_name;
	}
	public void setAr_name(ArrayList ar_name) {
		this.ar_name = ar_name;
	}
	public String getGrouplistid() {
		return grouplistid;
	}
	public void setGrouplistid(String grouplistid) {
		this.grouplistid = grouplistid;
	}
	public ArrayList<String> getSelectedSubSubModules() {
		return selectedSubSubModules;
	}
	public void setSelectedSubSubModules(ArrayList<String> selectedSubSubModules) {
		this.selectedSubSubModules = selectedSubSubModules;
	}
	public ArrayList<IdValuePair> getSubSubLinks() {
		return subSubLinks;
	}
	public void setSubSubLinks(ArrayList<IdValuePair> subSubLinks) {
		this.subSubLinks = subSubLinks;
	}
	public String getSubModuleName() {
		return subModuleName;
	}
	public void setSubModuleName(String subModuleName) {
		this.subModuleName = subModuleName;
	}
	public ArrayList<IdValuePair> getSubModules() {
		return subModules;
	}
	public void setSubModules(ArrayList<IdValuePair> subModules) {
		this.subModules = subModules;
	}
	public ArrayList<String> getSelectedModules() {
		return selectedModules;
	}
	public void setSelectedModules(ArrayList<String> selectedModules) {
		this.selectedModules = selectedModules;
	}
	public ArrayList<String> getSelectedSubLinks() {
		return selectedSubLinks;
	}
	public void setSelectedSubLinks(ArrayList<String> selectedSubLinks) {
		this.selectedSubLinks = selectedSubLinks;
	}
	public ArrayList<IdValuePair> getSubLinks() {
		return subLinks;
	}
	public void setSubLinks(ArrayList<IdValuePair> subLinks) {
		this.subLinks = subLinks;
	}
	public ArrayList<String> getAllGroupIds() {
		return allGroupIds;
	}
	public void setAllGroupIds(ArrayList<String> allGroupIds) {
		this.allGroupIds = allGroupIds;
	}
	public ArrayList<IdValuePair> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<IdValuePair> links) {
		this.links = links;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public ArrayList<IdValuePair> getModules() {
		return modules;
	}
	public void setModules(ArrayList<IdValuePair> modules) {
		this.modules = modules;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSearchText(){
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	
}
