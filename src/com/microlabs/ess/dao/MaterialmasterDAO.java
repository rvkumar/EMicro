package com.microlabs.ess.dao;

import java.sql.ResultSet;
import java.util.LinkedList;

import com.microlabs.admin.form.MaterialApproverForm;

public class MaterialmasterDAO {

public LinkedList customerApprsList(String customerType){
		
		LinkedList apprList=new LinkedList();
		EssDao ad=new EssDao();
		
		String getApprList="select m.Location,m.Material_Type,m.Priority,m.Approver_Id,emp.EMP_FULLNAME,m.Role from Material_Approvers m,emp_official_info emp "
		+ "where m.Location='' and Material_Type='Customer Master' and m.Material_Group='"+customerType+"' and emp.PERNR=m.Approver_Id order by m.Location,m.Material_Type,m.Priority";
		ResultSet rs=ad.selectQuery(getApprList);
		try{
		while(rs.next()){
			MaterialApproverForm m=new MaterialApproverForm();
			m.setPriority1(rs.getInt("Priority"));
			m.setLocationId(rs.getString("Location"));
			m.setMaterialType(rs.getString("Material_Type"));
			m.setEmployeeNumber(rs.getString("Approver_Id"));
			m.setEmpName(rs.getString("EMP_FULLNAME"));
			m.setRole1(rs.getString("Role"));
			apprList.add(m);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return apprList;
	}
public LinkedList vendorApprsList(String matGroup){
		
		LinkedList apprList=new LinkedList();
		EssDao ad=new EssDao();
		
		String getApprList="select m.Location,m.Material_Type,m.Priority,m.Approver_Id,emp.EMP_FULLNAME,m.Role from Material_Approvers m,emp_official_info emp "
		+ "where m.Location='' and Material_Type='Vendor Master' and m.Material_Group='"+matGroup+"' and emp.PERNR=m.Approver_Id order by m.Location,m.Material_Type,m.Priority";
		ResultSet rs=ad.selectQuery(getApprList);
		try{
		while(rs.next()){
			MaterialApproverForm m=new MaterialApproverForm();
			m.setPriority1(rs.getInt("Priority"));
			m.setLocationId(rs.getString("Location"));
			m.setMaterialType(rs.getString("Material_Type"));
			m.setEmployeeNumber(rs.getString("Approver_Id"));
			m.setEmpName(rs.getString("EMP_FULLNAME"));
			m.setRole1(rs.getString("Role"));
			apprList.add(m);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return apprList;
	}
public LinkedList serviceApprsList(String locID){
		
		LinkedList apprList=new LinkedList();
		EssDao ad=new EssDao();
		String getApprList="select l.LOCID,m.Material_Group,m.Location,m.Material_Type,m.Priority,m.Approver_Id,emp.EMP_FULLNAME,m.Role from "
		+ "Material_Approvers m,Location l,emp_official_info emp where m.Location=l.LOCATION_CODE and emp.PERNR=m.Approver_Id  and l.LOCID='"+locID+"' "
		+ " and m.Material_Type='Service Master' and m.Material_Group=''  order by m.Location,m.Material_Type,m.Priority";
		ResultSet rs=ad.selectQuery(getApprList);
		try{
		while(rs.next()){
			MaterialApproverForm m=new MaterialApproverForm();
			m.setPriority1(rs.getInt("Priority"));
			m.setLocationId(rs.getString("Location"));
			m.setMaterialType(rs.getString("Material_Type"));
			m.setEmployeeNumber(rs.getString("Approver_Id"));
			m.setEmpName(rs.getString("EMP_FULLNAME"));
			m.setRole1(rs.getString("Role"));
			apprList.add(m);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return apprList;
	}
	public LinkedList approversList(String locID,String matType,String matGroup){
		
		LinkedList apprList=new LinkedList();
		EssDao ad=new EssDao();
		String getApprList="select l.LOCID,m.Material_Group,m.Location,m.Material_Type,m.Priority,m.Approver_Id,emp.EMP_FULLNAME,m.Role from "
		+ "Material_Approvers m,Location l,emp_official_info emp,MATERIAL_TYPE mat where m.Location=l.LOCATION_CODE and emp.PERNR=m.Approver_Id and m.Material_Type=mat.MATERIAL_GROUP_ID and l.LOCID='"+locID+"' "
		+ " and mat.id='"+matType+"' and m.Material_Group='"+matGroup+"'  order by m.Location,m.Material_Type,m.Priority";
		ResultSet rs=ad.selectQuery(getApprList);
		try{
		while(rs.next()){
			MaterialApproverForm m=new MaterialApproverForm();
			m.setPriority1(rs.getInt("Priority"));
			m.setLocationId(rs.getString("Location"));
			m.setMaterialType(rs.getString("Material_Type"));
			m.setEmployeeNumber(rs.getString("Approver_Id"));
			m.setEmpName(rs.getString("EMP_FULLNAME"));
			m.setRole1(rs.getString("Role"));
			apprList.add(m);
		}
		if(apprList.size()==0){
			getApprList="select l.LOCID,m.Material_Group,m.Location,m.Material_Type,m.Priority,m.Approver_Id,emp.EMP_FULLNAME,m.Role from "
			+ "Material_Approvers m,Location l,emp_official_info emp,MATERIAL_TYPE mat where m.Location=l.LOCATION_CODE and emp.PERNR=m.Approver_Id and m.Material_Type=mat.MATERIAL_GROUP_ID and l.LOCID='"+locID+"' "
			+ " and mat.id='"+matType+"' and m.Material_Group=''  order by m.Location,m.Material_Type,m.Priority";
			 rs=ad.selectQuery(getApprList);
			while(rs.next()){
				MaterialApproverForm m=new MaterialApproverForm();
				m.setPriority1(rs.getInt("Priority"));
				m.setLocationId(rs.getString("Location"));
				m.setMaterialType(rs.getString("Material_Type"));
				m.setEmployeeNumber(rs.getString("Approver_Id"));
				m.setEmpName(rs.getString("EMP_FULLNAME"));
				m.setRole1(rs.getString("Role"));
				apprList.add(m);
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return apprList;
	}
}
