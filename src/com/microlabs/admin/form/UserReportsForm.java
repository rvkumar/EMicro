package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.microlabs.utilities.IdValuePair;

public class UserReportsForm extends ActionForm{
	
	private String staffId;
	private String userName;
	private String fName;
	private String disp;
	private String ip;
	private String date;
	private ArrayList<UserReportsForm> staffList;
	private int editId;
	private String serUserName;
	private ArrayList<UserReportsForm> userList;
	private String moduleName;
	private String linkName;
	private int hits;
	private ArrayList<UserReportsForm> userLogReportList;
	private String serDD;
	private String serMM;
	private String serYY;
	private String serFrmHH="00";
	private String serFrmMM="01";
	private String serToHH="23";
	private String serToMM="59";
	private ArrayList<UserReportsForm> userLogCheckList;
	private String serFrmDD;
	private String serToDD;
	private String serFrmMT;
	private String serToMT;
	private String serFrmYY;
	private String serToYY;
	private String url;
	private ArrayList<String> allModulesList;
	private ArrayList<UserReportsForm> logReportList;	
	private IdValuePair<String, String> toFromDatePair;
	
	
	
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFName() {
		return fName;
	}
	public void setFName(String name) {
		fName = name;
	}
	public String getDisp() {
		return disp;
	}
	public void setDisp(String disp) {
		this.disp = disp;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<UserReportsForm> getStaffList() {
		return staffList;
	}
	public void setStaffList(ArrayList<UserReportsForm> staffList) {
		this.staffList = staffList;
	}
	public int getEditId() {
		return editId;
	}
	public void setEditId(int editId) {
		this.editId = editId;
	}
	public String getSerUserName() {
		return serUserName;
	}
	public void setSerUserName(String serUserName) {
		this.serUserName = serUserName;
	}
	public ArrayList<UserReportsForm> getUserList() {
		return userList;
	}
	public void setUserList(ArrayList<UserReportsForm> userList) {
		this.userList = userList;
	}
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public ArrayList<UserReportsForm> getUserLogReportList() {
		return userLogReportList;
	}
	public void setUserLogReportList(ArrayList<UserReportsForm> userLogReportList) {
		this.userLogReportList = userLogReportList;
	}
	public String getSerDD() {
		return serDD;
	}
	public void setSerDD(String serDD) {
		this.serDD = serDD;
	}
	public String getSerMM() {
		return serMM;
	}
	public void setSerMM(String serMM) {
		this.serMM = serMM;
	}
	public String getSerYY() {
		return serYY;
	}
	public void setSerYY(String serYY) {
		this.serYY = serYY;
	}
	public String getSerFrmHH() {
		return serFrmHH;
	}
	public void setSerFrmHH(String serFrmHH) {
		this.serFrmHH = serFrmHH;
	}
	public String getSerFrmMM() {
		return serFrmMM;
	}
	public void setSerFrmMM(String serFrmMM) {
		this.serFrmMM = serFrmMM;
	}
	public String getSerToHH() {
		return serToHH;
	}
	public void setSerToHH(String serToHH) {
		this.serToHH = serToHH;
	}
	public String getSerToMM() {
		return serToMM;
	}
	public void setSerToMM(String serToMM) {
		this.serToMM = serToMM;
	}
	public ArrayList<UserReportsForm> getUserLogCheckList() {
		return userLogCheckList;
	}
	public void setUserLogCheckList(ArrayList<UserReportsForm> userLogCheckList) {
		this.userLogCheckList = userLogCheckList;
	}
	public String getSerFrmDD() {
		return serFrmDD;
	}
	public void setSerFrmDD(String serFrmDD) {
		this.serFrmDD = serFrmDD;
	}
	public String getSerToDD() {
		return serToDD;
	}
	public void setSerToDD(String serToDD) {
		this.serToDD = serToDD;
	}
	public String getSerFrmMT() {
		return serFrmMT;
	}
	public void setSerFrmMT(String serFrmMT) {
		this.serFrmMT = serFrmMT;
	}
	public String getSerToMT() {
		return serToMT;
	}
	public void setSerToMT(String serToMT) {
		this.serToMT = serToMT;
	}
	public String getSerFrmYY() {
		return serFrmYY;
	}
	public void setSerFrmYY(String serFrmYY) {
		this.serFrmYY = serFrmYY;
	}
	public String getSerToYY() {
		return serToYY;
	}
	public void setSerToYY(String serToYY) {
		this.serToYY = serToYY;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<String> getAllModulesList() {
		return allModulesList;
	}
	public void setAllModulesList(ArrayList<String> allModulesList) {
		this.allModulesList = allModulesList;
	}
	public ArrayList<UserReportsForm> getLogReportList() {
		return logReportList;
	}
	public void setLogReportList(ArrayList<UserReportsForm> logReportList) {
		this.logReportList = logReportList;
	}
	public IdValuePair<String, String> getToFromDatePair() {
		return toFromDatePair;
	}
	public void setToFromDatePair(IdValuePair<String, String> toFromDatePair) {
		this.toFromDatePair = toFromDatePair;
	}
	
	
	

}
