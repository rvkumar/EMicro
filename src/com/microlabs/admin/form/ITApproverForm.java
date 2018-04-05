package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class ITApproverForm  extends ActionForm{
	
	private String searchKeyword;
	
	private String message;
	private String message2;
	private String essType;
	private String employeeNo;
	private String sequentialType;
	private String parallelType;
	
	public int priority1;
	public int priority2;
	public int priority3;
	public int priority4;
	public int priority5;
	public int priority6;
	public int priority7;
	public int priority8;
	
	public String approver1;
	public String approver2;
	public String approver3;
	public String approver4;
	public String approver5;
	public String approver6;
	public String approver7;
	public String approver8;
	
	
	public String parllelAppr11;
	public String parllelAppr12;
	public String parllelAppr21;
	public String parllelAppr22;
	public String parllelAppr31;
	public String parllelAppr32;
	public String parllelAppr41;
	public String parllelAppr42;
	
	
	private String searchText;
	private String emp;
	private String searchAddress;
	private String reqFieldName;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	private String empname;
	private String department;
	private String message1;

	private int adtotalAppr;
	private int lotuscreationotalAppr;
	
	private String adApprovers;
	private String lotusApprs;
	private String requestType;
	
	private String location;
	private String locationId;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	

	private ArrayList subcatList=new ArrayList();
	
	private String category;
	
	
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ArrayList getSubcatList() {
		return subcatList;
	}

	public void setSubcatList(ArrayList subcatList) {
		this.subcatList = subcatList;
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

	public int getAdtotalAppr() {
		return adtotalAppr;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public void setAdtotalAppr(int adtotalAppr) {
		this.adtotalAppr = adtotalAppr;
	}

	public String getAdApprovers() {
		return adApprovers;
	}

	public void setAdApprovers(String adApprovers) {
		this.adApprovers = adApprovers;
	}

	public String getLotusApprs() {
		return lotusApprs;
	}

	public void setLotusApprs(String lotusApprs) {
		this.lotusApprs = lotusApprs;
	}

	public int getPriority5() {
		return priority5;
	}

	public void setPriority5(int priority5) {
		this.priority5 = priority5;
	}

	public int getPriority6() {
		return priority6;
	}

	public void setPriority6(int priority6) {
		this.priority6 = priority6;
	}

	public int getPriority7() {
		return priority7;
	}

	public void setPriority7(int priority7) {
		this.priority7 = priority7;
	}

	public int getPriority8() {
		return priority8;
	}

	public void setPriority8(int priority8) {
		this.priority8 = priority8;
	}

	public String getApprover5() {
		return approver5;
	}

	public void setApprover5(String approver5) {
		this.approver5 = approver5;
	}

	public String getApprover6() {
		return approver6;
	}

	public void setApprover6(String approver6) {
		this.approver6 = approver6;
	}

	public String getApprover7() {
		return approver7;
	}

	public void setApprover7(String approver7) {
		this.approver7 = approver7;
	}

	public String getApprover8() {
		return approver8;
	}

	public void setApprover8(String approver8) {
		this.approver8 = approver8;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public String getEssType() {
		return essType;
	}

	public void setEssType(String essType) {
		this.essType = essType;
	}

	public String getEmployeeNo() {
		return employeeNo;
	}

	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}

	public String getSequentialType() {
		return sequentialType;
	}

	public void setSequentialType(String sequentialType) {
		this.sequentialType = sequentialType;
	}

	public String getParallelType() {
		return parallelType;
	}

	public void setParallelType(String parallelType) {
		this.parallelType = parallelType;
	}

	public int getPriority1() {
		return priority1;
	}

	public void setPriority1(int priority1) {
		this.priority1 = priority1;
	}

	public int getPriority2() {
		return priority2;
	}

	public void setPriority2(int priority2) {
		this.priority2 = priority2;
	}

	public int getPriority3() {
		return priority3;
	}

	public void setPriority3(int priority3) {
		this.priority3 = priority3;
	}

	public int getPriority4() {
		return priority4;
	}

	public void setPriority4(int priority4) {
		this.priority4 = priority4;
	}

	public String getApprover1() {
		return approver1;
	}

	public void setApprover1(String approver1) {
		this.approver1 = approver1;
	}

	public String getApprover2() {
		return approver2;
	}

	public void setApprover2(String approver2) {
		this.approver2 = approver2;
	}

	public String getApprover3() {
		return approver3;
	}

	public void setApprover3(String approver3) {
		this.approver3 = approver3;
	}

	public String getApprover4() {
		return approver4;
	}

	public void setApprover4(String approver4) {
		this.approver4 = approver4;
	}

	public String getParllelAppr11() {
		return parllelAppr11;
	}

	public void setParllelAppr11(String parllelAppr11) {
		this.parllelAppr11 = parllelAppr11;
	}

	public String getParllelAppr12() {
		return parllelAppr12;
	}

	public void setParllelAppr12(String parllelAppr12) {
		this.parllelAppr12 = parllelAppr12;
	}

	public String getParllelAppr21() {
		return parllelAppr21;
	}

	public void setParllelAppr21(String parllelAppr21) {
		this.parllelAppr21 = parllelAppr21;
	}

	public String getParllelAppr22() {
		return parllelAppr22;
	}

	public void setParllelAppr22(String parllelAppr22) {
		this.parllelAppr22 = parllelAppr22;
	}

	public String getParllelAppr31() {
		return parllelAppr31;
	}

	public void setParllelAppr31(String parllelAppr31) {
		this.parllelAppr31 = parllelAppr31;
	}

	public String getParllelAppr32() {
		return parllelAppr32;
	}

	public void setParllelAppr32(String parllelAppr32) {
		this.parllelAppr32 = parllelAppr32;
	}

	public String getParllelAppr41() {
		return parllelAppr41;
	}

	public void setParllelAppr41(String parllelAppr41) {
		this.parllelAppr41 = parllelAppr41;
	}

	public String getParllelAppr42() {
		return parllelAppr42;
	}

	public void setParllelAppr42(String parllelAppr42) {
		this.parllelAppr42 = parllelAppr42;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getEmp() {
		return emp;
	}

	public void setEmp(String emp) {
		this.emp = emp;
	}

	public String getSearchAddress() {
		return searchAddress;
	}

	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}

	public String getReqFieldName() {
		return reqFieldName;
	}

	public void setReqFieldName(String reqFieldName) {
		this.reqFieldName = reqFieldName;
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

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getMessage1() {
		return message1;
	}

	public void setMessage1(String message1) {
		this.message1 = message1;
	}

	public String getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public int getLotuscreationotalAppr() {
		return lotuscreationotalAppr;
	}

	public void setLotuscreationotalAppr(int lotuscreationotalAppr) {
		this.lotuscreationotalAppr = lotuscreationotalAppr;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
