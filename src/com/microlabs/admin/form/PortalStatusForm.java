package com.microlabs.admin.form;

import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class PortalStatusForm extends ActionForm{
	
	private String pernr;
	private String name;
	private String dept;
	private String desg;
	private String perstatus;
	private String addstatus;
	private String famstatus;
	private String edustatus;
	private String expstatus;
	private String lanstatus;
	private String status;
	
	private LinkedList locidlist;
	private LinkedList locnamelist;
	
	private String locid;
	private String cat;
	
	
	private String explocid;
	private String expcat;
	
	
	
	

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPerstatus() {
		return perstatus;
	}
	public void setPerstatus(String perstatus) {
		this.perstatus = perstatus;
	}
	public String getAddstatus() {
		return addstatus;
	}
	public void setAddstatus(String addstatus) {
		this.addstatus = addstatus;
	}
	public String getFamstatus() {
		return famstatus;
	}
	public void setFamstatus(String famstatus) {
		this.famstatus = famstatus;
	}
	public String getEdustatus() {
		return edustatus;
	}
	public void setEdustatus(String edustatus) {
		this.edustatus = edustatus;
	}
	public String getExpstatus() {
		return expstatus;
	}
	public void setExpstatus(String expstatus) {
		this.expstatus = expstatus;
	}
	public String getLanstatus() {
		return lanstatus;
	}
	public void setLanstatus(String lanstatus) {
		this.lanstatus = lanstatus;
	}
	public String getExplocid() {
		return explocid;
	}
	public void setExplocid(String explocid) {
		this.explocid = explocid;
	}
	public String getExpcat() {
		return expcat;
	}
	public void setExpcat(String expcat) {
		this.expcat = expcat;
	}
	public LinkedList getLocidlist() {
		return locidlist;
	}
	public void setLocidlist(LinkedList locidlist) {
		this.locidlist = locidlist;
	}
	public LinkedList getLocnamelist() {
		return locnamelist;
	}
	public void setLocnamelist(LinkedList locnamelist) {
		this.locnamelist = locnamelist;
	}
	public String getLocid() {
		return locid;
	}
	public void setLocid(String locid) {
		this.locid = locid;
	}
	public String getCat() {
		return cat;
	}
	public void setCat(String cat) {
		this.cat = cat;
	}
	public String getPernr() {
		return pernr;
	}
	public void setPernr(String pernr) {
		this.pernr = pernr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getDesg() {
		return desg;
	}
	public void setDesg(String desg) {
		this.desg = desg;
	}
	

	
	
}
