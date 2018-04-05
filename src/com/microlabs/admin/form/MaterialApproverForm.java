package com.microlabs.admin.form;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;

public class MaterialApproverForm extends ActionForm{

	public String message;
	public String locationId;
	public String materialType;
	public String materialGroupId;
	public String customerGroupId;
	public String reqGroupId;
	public String approver1;
	public String approver2;
	public String approver3;
	public String approver4;
	public String approver5;
	public String approver6;
	public int priority1;
	public int priority2;
	public int priority3;
	public int priority4;
	public int priority5;
	public int priority6;
	public String role1;
	public String role2;
	public String role3;
	public String role4;
	public String role5;
	public String role6;
	
	public String parllelAppr11;
	public String parllelAppr12;
	public String parllelAppr21;
	public String parllelAppr22;
	public String parllelAppr31;
	public String parllelAppr32;
	public String parllelAppr41;
	public String parllelAppr42;
	public String parllelAppr51;
	public String parllelAppr52;
	public String parllelAppr61;
	public String parllelAppr62;
	
	public String reqFiled;
	public String employeeNumber;
	public String department;
	public String designation;
    public String empName;
	public int totalApprovers;
	
	
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private LinkedList materTypeIDList;
	private LinkedList materialTypeIdValueList;
	private LinkedList materGroupIDList;
	private LinkedList materialGroupIdValueList;
	private ArrayList departmentList= new ArrayList();
	
	private int total;
	private int next;
	private int prev;

	private int startRecord;
	private int endRecord;
	private String subCategoryId;
	
	private ArrayList categorylist;
	private ArrayList categortShortlist;
	private  ArrayList subcatList;
	
	
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
	public String getCustomerGroupId() {
		return customerGroupId;
	}
	public void setCustomerGroupId(String customerGroupId) {
		this.customerGroupId = customerGroupId;
	}
	public String getReqGroupId() {
		return reqGroupId;
	}
	public void setReqGroupId(String reqGroupId) {
		this.reqGroupId = reqGroupId;
	}
	public int getTotalApprovers() {
		return totalApprovers;
	}
	public void setTotalApprovers(int totalApprovers) {
		this.totalApprovers = totalApprovers;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getReqFiled() {
		return reqFiled;
	}
	public void setReqFiled(String reqFiled) {
		this.reqFiled = reqFiled;
	}
	public ArrayList getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(ArrayList departmentList) {
		this.departmentList = departmentList;
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
	public String getParllelAppr51() {
		return parllelAppr51;
	}
	public void setParllelAppr51(String parllelAppr51) {
		this.parllelAppr51 = parllelAppr51;
	}
	public String getParllelAppr52() {
		return parllelAppr52;
	}
	public void setParllelAppr52(String parllelAppr52) {
		this.parllelAppr52 = parllelAppr52;
	}
	public String getParllelAppr61() {
		return parllelAppr61;
	}
	public void setParllelAppr61(String parllelAppr61) {
		this.parllelAppr61 = parllelAppr61;
	}
	public String getParllelAppr62() {
		return parllelAppr62;
	}
	public void setParllelAppr62(String parllelAppr62) {
		this.parllelAppr62 = parllelAppr62;
	}
	public String getRole1() {
		return role1;
	}
	public void setRole1(String role1) {
		this.role1 = role1;
	}
	public String getRole2() {
		return role2;
	}
	public void setRole2(String role2) {
		this.role2 = role2;
	}
	public String getRole3() {
		return role3;
	}
	public void setRole3(String role3) {
		this.role3 = role3;
	}
	public String getRole4() {
		return role4;
	}
	public void setRole4(String role4) {
		this.role4 = role4;
	}
	public String getRole5() {
		return role5;
	}
	public void setRole5(String role5) {
		this.role5 = role5;
	}
	public String getRole6() {
		return role6;
	}
	public void setRole6(String role6) {
		this.role6 = role6;
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
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getMaterialGroupId() {
		return materialGroupId;
	}
	public void setMaterialGroupId(String materialGroupId) {
		this.materialGroupId = materialGroupId;
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
	
	public LinkedList getMaterTypeIDList() {
		return materTypeIDList;
	}
	public void setMaterTypeIDList(LinkedList materTypeIDList) {
		this.materTypeIDList = materTypeIDList;
	}
	public LinkedList getMaterialTypeIdValueList() {
		return materialTypeIdValueList;
	}
	public void setMaterialTypeIdValueList(LinkedList materialTypeIdValueList) {
		this.materialTypeIdValueList = materialTypeIdValueList;
	}
	public LinkedList getMaterGroupIDList() {
		return materGroupIDList;
	}
	public void setMaterGroupIDList(LinkedList materGroupIDList) {
		this.materGroupIDList = materGroupIDList;
	}
	public LinkedList getMaterialGroupIdValueList() {
		return materialGroupIdValueList;
	}
	public void setMaterialGroupIdValueList(LinkedList materialGroupIdValueList) {
		this.materialGroupIdValueList = materialGroupIdValueList;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getSubCategoryId() {
		return subCategoryId;
	}
	public void setSubCategoryId(String subCategoryId) {
		this.subCategoryId = subCategoryId;
	}
	public ArrayList getCategorylist() {
		return categorylist;
	}
	public void setCategorylist(ArrayList categorylist) {
		this.categorylist = categorylist;
	}
	public ArrayList getCategortShortlist() {
		return categortShortlist;
	}
	public void setCategortShortlist(ArrayList categortShortlist) {
		this.categortShortlist = categortShortlist;
	}
	public ArrayList getSubcatList() {
		return subcatList;
	}
	public void setSubcatList(ArrayList subcatList) {
		this.subcatList = subcatList;
	}
	
	
	
}
