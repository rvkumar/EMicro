package com.microlabs.ess.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ReportRequestForm extends ActionForm {
private String m_rquestDate;
private String m_materialType;
private String m_materialDescription;
private String m_materialGroup;
private String m_plant;
private String m_status;
private String m_requestedBy;
private String m_approveDate;



private String s_requestDate;
private String s_serviceCatagory;
private String s_serviceDescription;
private String s_serviceGroup;
private String s_plant;
private String s_status;
private String s_requestedBy;
private String s_approveDate;

private String c_approveDate;
private String c_name;
private String c_location;
private String c_country;
private String c_customerGroup;
private String c_status;
private String c_requestedBy;
private String c_requestDate;


private String v_requestDate;
private String v_name;
private String v_location;
private String v_country;
private String v_vendorType;
private String v_status;
private String v_requestedBy;
private String v_approveDate;

private String masterType;
private String message;
private int row;

private int total;
private int next;
private int prev;

private int startRecord;
private int endRecord;
private String query;
private int range;

private String[] cl;

private ArrayList s_group;
private ArrayList s_groupLabel;

private ArrayList plant_name;
private ArrayList plant_label;




//-----------------------Getter & Setters--------------------------------//


public ArrayList getPlant_name() {
	return plant_name;
}
public void setPlant_name(ArrayList plant_name) {
	this.plant_name = plant_name;
}
public ArrayList getPlant_label() {
	return plant_label;
}
public void setPlant_label(ArrayList plant_label) {
	this.plant_label = plant_label;
}
public ArrayList getS_groupLabel() {
	return s_groupLabel;
}
public void setS_groupLabel(ArrayList label) {
	s_groupLabel = label;
}

public String[] getCl() {
	return cl;
}
public void setCl(String[] cl) {
	this.cl = cl;
}
public int getRange() {
	return range;
}
public void setRange(int range) {
	this.range = range;
}
public String getQuery() {
	return query;
}
public void setQuery(String query) {
	this.query = query;
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
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
public int getNext() {
	return next;
}
public void setNext(int next) {
	this.next = next;
}
public int getPrev() {
	return prev;
}
public void setPrev(int prev) {
	this.prev = prev;
}
public int getRow() {
	return row;
}
public void setRow(int row) {
	this.row = row;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getMasterType() {
	return masterType;
}
public void setMasterType(String masterType) {
	this.masterType = masterType;
}
public String getV_approveDate() {
	return v_approveDate;
}
public void setV_approveDate(String date) {
	v_approveDate = date;
}
public String getV_requestedBy() {
	return v_requestedBy;
}
public void setV_requestedBy(String by) {
	v_requestedBy = by;
}
public String getV_status() {
	return v_status;
}
public void setV_status(String v_status) {
	this.v_status = v_status;
}
public String getV_vendorType() {
	return v_vendorType;
}
public void setV_vendorType(String type) {
	v_vendorType = type;
}
public String getV_country() {
	return v_country;
}
public void setV_country(String v_country) {
	this.v_country = v_country;
}
public String getV_location() {
	return v_location;
}
public void setV_location(String v_location) {
	this.v_location = v_location;
}
public String getV_name() {
	return v_name;
}
public void setV_name(String v_name) {
	this.v_name = v_name;
}
public String getV_requestDate() {
	return v_requestDate;
}
public void setV_requestDate(String date) {
	v_requestDate = date;
}
public String getC_requestedBy() {
	return c_requestedBy;
}
public void setC_requestedBy(String by) {
	c_requestedBy = by;
}
public String getC_status() {
	return c_status;
}
public void setC_status(String c_status) {
	this.c_status = c_status;
}
public String getC_customerGroup() {
	return c_customerGroup;
}
public void setC_customerGroup(String group) {
	c_customerGroup = group;
}
public String getC_country() {
	return c_country;
}
public void setC_country(String c_country) {
	this.c_country = c_country;
}
public String getC_location() {
	return c_location;
}
public void setC_location(String c_location) {
	this.c_location = c_location;
}
public String getC_name() {
	return c_name;
}
public void setC_name(String c_name) {
	this.c_name = c_name;
}
public String getC_approveDate() {
	return c_approveDate;
}
public void setC_approveDate(String date) {
	c_approveDate = date;
}
public String getS_approveDate() {
	return s_approveDate;
}
public void setS_approveDate(String date) {
	s_approveDate = date;
}
public String getS_requestedBy() {
	return s_requestedBy;
}
public void setS_requestedBy(String by) {
	s_requestedBy = by;
}
public String getS_status() {
	return s_status;
}
public void setS_status(String s_status) {
	this.s_status = s_status;
}
public String getS_plant() {
	return s_plant;
}
public void setS_plant(String s_plant) {
	this.s_plant = s_plant;
}
public String getS_serviceCatagory() {
	return s_serviceCatagory;
}
public void setS_serviceCatagory(String catagory) {
	s_serviceCatagory = catagory;
}
public String getS_requestDate() {
	return s_requestDate;
}
public void setS_requestDate(String date) {
	s_requestDate = date;
}
public String getM_approveDate() {
	return m_approveDate;
}
public void setM_approveDate(String date) {
	m_approveDate = date;
}
public String getM_requestedBy() {
	return m_requestedBy;
}
public void setM_requestedBy(String by) {
	m_requestedBy = by;
}
public String getM_plant() {
	return m_plant;
}
public void setM_plant(String m_plant) {
	this.m_plant = m_plant;
}
public String getM_rquestDate() {
	return m_rquestDate;
}
public void setM_rquestDate(String date) {
	m_rquestDate = date;
}
public String getM_materialType() {
	return m_materialType;
}
public void setM_materialType(String type) {
	m_materialType = type;
}
public String getM_materialDescription() {
	return m_materialDescription;
}
public void setM_materialDescription(String description) {
	m_materialDescription = description;
}
public String getM_materialGroup() {
	return m_materialGroup;
}
public void setM_materialGroup(String group) {
	m_materialGroup = group;
}
public String getM_status() {
	return m_status;
}
public void setM_status(String m_status) {
	this.m_status = m_status;
}
public String getS_serviceDescription() {
	return s_serviceDescription;
}
public void setS_serviceDescription(String description) {
	s_serviceDescription = description;
}
public String getS_serviceGroup() {
	return s_serviceGroup;
}
public void setS_serviceGroup(String group) {
	s_serviceGroup = group;
}
public String getC_requestDate() {
	return c_requestDate;
}
public void setC_requestDate(String date) {
	c_requestDate = date;
}
public ArrayList getS_group() {
	return s_group;
}
public void setS_group(ArrayList s_group) {
	this.s_group = s_group;
}


}
