package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.MaterialApproverForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.utilities.UserInfo;

public class MaterialApproverAction extends DispatchAction{
	public ActionForward searchMaterials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		masterData(mapping, form, request, response);
		EssDao ad=new EssDao();
		String location=masterForm.getLocationId();
		String materialType=masterForm.getMaterialType();
		String matGroup=masterForm.getMaterialGroupId();
		String matSubcat = masterForm.getSubCategoryId();
		LinkedList materialTypeList=new LinkedList();
		String getMaterialSearch="select Location,Material_Type,Material_Group,Material_Sub_Category ,count(*) as total from Material_Approvers where " +
		"(Location like '%"+location+"%' and Material_Type like '%"+materialType+"%' and  Material_Group like '%"+matGroup+"%' "
				+ " and Material_Sub_Category like '%"+matSubcat+"%') group by Location,Material_Type,Material_Group,Material_Sub_Category";
        System.out.println(getMaterialSearch);
		
		
		ResultSet rs=ad.selectQuery(getMaterialSearch);
        try{
        	ResultSet rsMatType=ad.selectQuery(getMaterialSearch);
    		while(rsMatType.next()){
    			MaterialApproverForm approverForm=new MaterialApproverForm();
    			approverForm.setLocationId(rsMatType.getString("Location"));
    			approverForm.setMaterialType(rsMatType.getString("Material_Type"));
    			String matGroup1=rsMatType.getString("Material_Group");
    			approverForm.setMaterialGroupId(rsMatType.getString("Material_Group"));
    			approverForm.setSubCategoryId(rsMatType.getString("Material_Sub_Category"));
    			
    			if(matGroup1.equals("V")){
    				matGroup1="Validation";
    				approverForm.setMaterialGroupId(matGroup1);
    			}
    			approverForm.setReqGroupId(rsMatType.getString("Material_Group"));
    			approverForm.setTotalApprovers(rsMatType.getInt("total"));
    			materialTypeList.add(approverForm);
    		}
    		request.setAttribute("materialTypeList", materialTypeList);
    		if(materialTypeList.size()==0)
    			request.setAttribute("noRecords", "noRecords");
        }catch (Exception e) {
			e.printStackTrace();
		}
        
        ArrayList category=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		masterForm.setCategorylist(category);
		
		masterForm.setCategortShortlist(categoryShortName);
		
		
		

			
			ArrayList subCategoryList = new ArrayList();
			
			String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
			ResultSet rs3 = ad.selectQuery(cat1);
			
			try {
				while (rs3.next())

				{
					subCategoryList.add(rs3.getString("c_sub_cat_name"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			
			masterForm.setSubcatList(subCategoryList);
		
		//---------

        
		return mapping.findForward("displayApprovers");
	}
	
	public ActionForward deleteMatType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		EssDao ad=new EssDao();
		try{
			
			String location=request.getParameter("Location");
			String matType=request.getParameter("matType");
			String matGroup=request.getParameter("matGroup");
			   
			   String deleteRecord="delete from Material_Approvers where Location='"+location+"' and Material_Type='"+matType+"' and Material_Group='"+matGroup+"'";
			   int j=0;
			   j=ad.SqlExecuteUpdate(deleteRecord);
		 	if(j>0)
		 	{
		 		masterForm.setMessage("Data deleted successfully");
		 	}else{
		 		masterForm.setMessage("Error...Data not deleted successfully");
		 	}
		 		
		}catch (Exception e) {
			e.printStackTrace();
		}
		displayApprovers(mapping, form, request, response);
		return mapping.findForward("displayApprovers");
	}
	
	public ActionForward modifyMatType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		EssDao ad=new EssDao();
		try{
	   String location=masterForm.getLocationId();
	   String matType=masterForm.getMaterialType();
	   String matGroup=masterForm.getMaterialGroupId();
	   String matSubcat = masterForm.getSubCategoryId();
	   
	   String approver1=masterForm.getApprover1();
		 String approver2=masterForm.getApprover2();
		 String approver3=masterForm.getApprover3();
		 String approver4=masterForm.getApprover4();
		 String approver5=masterForm.getApprover5();
		 String approver6=masterForm.getApprover6();
		 String parllApp11=masterForm.getParllelAppr11();
		  String parllApp12=masterForm.getParllelAppr12();
		  String parllApp21=masterForm.getParllelAppr21();
		  String parllApp22=masterForm.getParllelAppr22();
		  String parllApp31=masterForm.getParllelAppr31();
		  String parllApp32=masterForm.getParllelAppr32();
		  
		  String parllApp41=masterForm.getParllelAppr41();
		  String parllApp42=masterForm.getParllelAppr42();
		  String parllApp51=masterForm.getParllelAppr51();
		  String parllApp52=masterForm.getParllelAppr52();
		  String parllApp61=masterForm.getParllelAppr61();
		  String parllApp62=masterForm.getParllelAppr62();
		  
		  int empexist=0;
		  
		  if((!approver1.equalsIgnoreCase(""))){
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver1+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "Approver 1 is not exist");
				  masterForm.setMessage("Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
			  
		  }
		  if((!parllApp11.equalsIgnoreCase(""))){
			  empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp11+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 1 Parllel Approver 1 is not exist");
				  masterForm.setMessage("In Priority 1 Parllel Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
			}
			if((!parllApp12.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp12+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "Parllel Approver 2 is not exist");
					  masterForm.setMessage("In Priority 1 Parllel Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("modifyButton", "modifyButton");
						return mapping.findForward("newMasterType");
						
				  }  
			}
		if((!approver2.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver2+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", " Approver 2 is not exist");
				  masterForm.setMessage(" Approver 2 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }			  
		}
		if((!parllApp21.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp21+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 2 Parllel Approver 1 is not exist");
				  masterForm.setMessage("In Priority 2 Parllel Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
		}
		if((!parllApp22.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp22+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 2 Parllel Approver 2 is not exist");
				  masterForm.setMessage("In Priority 2 Parllel Approver 2 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
		}
		if((!approver3.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver3+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", " Approver 3 is not exist");
				  masterForm.setMessage(" Approver 3 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }	 
		}
		if((!parllApp31.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp31+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 3 Parllel Approver 1 is not exist");
				  masterForm.setMessage("In Priority 3 Parllel Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }	  
		}
		if((!parllApp32.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp32+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 3 Parllel Approver 2 is not exist");
				  masterForm.setMessage("In Priority 3 Parllel Approver 2 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  } 
		}
		if((!approver4.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver4+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", " Approver 4 is not exist");
				  masterForm.setMessage("Approver 4 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
			  }	  
		}
		if((!parllApp41.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp41+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 4 Parllel Approver 1 is not exist");
				  masterForm.setMessage("In Priority 4 Parllel Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }  
		}
		if((!parllApp42.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 4 Parllel Approver 2 is not exist");
				  masterForm.setMessage("In Priority 4 Parllel Approver 2 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
		}
		if((!approver5.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver5+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", " Approver 5 is not exist");
				  masterForm.setMessage("Approver 5 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
			  } 
		}
		if((!parllApp51.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp51+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 5 Parllel Approver 1 is not exist");
				  masterForm.setMessage("In Priority 5 Parllel Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }  
		}
		if((!parllApp52.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 5 Parllel Approver 2 is not exist");
				  masterForm.setMessage("In Priority 5 Parllel Approver 2 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
		}
		if((!approver6.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver6+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", " Approver 6 is not exist");
				  masterForm.setMessage("Approver 6 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
			  }
		}
		if((!parllApp61.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 6 Parllel Approver 1 is not exist");
				  masterForm.setMessage("In Priority 6 Parllel Approver 1 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
					
			  }
		}
		if((!parllApp62.equalsIgnoreCase(""))){
			empexist=0;
			  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
			  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
			  while(rsEmpCheck.next())
			  {
				  empexist=rsEmpCheck.getInt(1);
			  }
			  if(empexist==0)
			  {
				  request.setAttribute("invalidEmp", "In Priority 6 Parllel Approver 2 is not exist");
				  masterForm.setMessage("In Priority 6 Parllel Approver 2 is not exist");
				  masterData(mapping, form, request, response);
				  request.setAttribute("modifyButton", "modifyButton");
					return mapping.findForward("newMasterType");
			  }
		}
	   
		
		String materialType=masterForm.getMaterialType();
		if(materialType.equalsIgnoreCase("Customer Master")){
			matGroup=masterForm.getCustomerGroupId();
			masterForm.setMaterialGroupId(matGroup);
		}
		if(materialType.equalsIgnoreCase("Vendor Master")){
			 matGroup=masterForm.getCustomerGroupId();
			masterForm.setMaterialGroupId(matGroup);
		}
		//FG,HAWA
		if(materialType.equals("FG")||materialType.equals("HAWA")){
			String group=masterForm.getCustomerGroupId();
			masterForm.setMaterialGroupId(group);
			matGroup=group;
		}
	   
	   String deleteRecord="delete from Material_Approvers where Location='"+location+"' and Material_Type='"+matType+"' and Material_Group='"+matGroup+"'";
	   int j=0;
	   j=ad.SqlExecuteUpdate(deleteRecord);
	   if(j>0)
	   {
		   try{
				
				
				int i=0;
		  if(!(approver1.equalsIgnoreCase("")))
		  {
			  String approverID=approver1;
			  
			  
	        String insertApp1="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) "+
	        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.priority1+"','"+masterForm.getParllelAppr11()+"','"+masterForm.getParllelAppr12()+"','"+masterForm.getRole1()+"','"+masterForm.getSubCategoryId()+"')";
	        i=ad.SqlExecuteUpdate(insertApp1);
		  }
		  if(!(approver2.equalsIgnoreCase("")))
		  {
			  String approverID=approver2;
			 
			
	        String insertApp2="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
	        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.priority2+"','"+masterForm.getParllelAppr21()+"','"+masterForm.getParllelAppr22()+"','"+masterForm.getRole2()+"','"+masterForm.getSubCategoryId()+"')";
	        i=ad.SqlExecuteUpdate(insertApp2);
		  }
		  if(!(approver3.equalsIgnoreCase("")))
		  {
			  String approverID=approver3;
			  
			
	        String insertApp3="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
	        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.priority3+"','"+masterForm.getParllelAppr31()+"','"+masterForm.getParllelAppr32()+"','"+masterForm.getRole3()+"','"+masterForm.getSubCategoryId()+"')";
	        i=ad.SqlExecuteUpdate(insertApp3);
		  }
		  if(!(approver4.equalsIgnoreCase("")))
		  {
			  String approverID=approver4;
			  
			 
	        String insertApp4="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
	        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.priority4+"','"+masterForm.getParllelAppr41()+"','"+masterForm.getParllelAppr42()+"','"+masterForm.getRole4()+"','"+masterForm.getSubCategoryId()+"')";
	        i=ad.SqlExecuteUpdate(insertApp4);
		  }
		  if(!(approver5.equalsIgnoreCase("")))
		  {
			  String approverID=approver5;
			 
			 
	        String insertApp5="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
	        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.priority5+"','"+masterForm.getParllelAppr51()+"','"+masterForm.getParllelAppr52()+"','"+masterForm.getRole5()+"','"+masterForm.getSubCategoryId()+"')";
	        i=ad.SqlExecuteUpdate(insertApp5);
		  }
		  if(!(approver6.equalsIgnoreCase("")))
		  {
			  
			  String approverID=approver6;
			 
	        String insertApp6="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Approver_Name,Material_Sub_Category) " +
	        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.priority6+"','"+masterForm.getParllelAppr61()+"','"+masterForm.getParllelAppr62()+"','"+masterForm.getRole6()+"','"+masterForm.getSubCategoryId()+"')";
	        i=ad.SqlExecuteUpdate(insertApp6);
		  }
		  masterData(mapping, form, request, response);
			if(i>0)
			{
				masterForm.setMessage("Data Modified Successfully");
				
			}else{
				masterForm.setMessage("Error..Data Not Modified");
			}
		  
	   }catch (Exception e) {
		e.printStackTrace();
	}
	   }
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ArrayList category=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		masterForm.setCategorylist(category);
		
		masterForm.setCategortShortlist(categoryShortName);
		
		
		

			
			ArrayList subCategoryList = new ArrayList();
			
			String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
			ResultSet rs3 = ad.selectQuery(cat1);
			
			try {
				while (rs3.next())

				{
					subCategoryList.add(rs3.getString("c_sub_cat_name"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			
			masterForm.setSubcatList(subCategoryList);
		

		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("newMasterType");
	}
	
	
	public ActionForward editMatrialType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		EssDao ad=new EssDao();
		try{
		String location=request.getParameter("Location");
		String matType=request.getParameter("matType");
		String matGroup=request.getParameter("matGroup");	
		String matSubcat = request.getParameter("matSubcat");
		masterData(mapping, form, request, response);
		String getMaterialType="select m.Location,m.Material_Type,m.Material_Group,m.Role,m.Approver_Id,emp.EMP_FULLNAME,m.Priority,m.Parllel_Approver_1," +
		"m.Parllel_Approver_2 from Material_Approvers as m,emp_official_info as emp where m.Location='"+location+"' and m.Approver_Id=emp.PERNR  and  " +
				"m.Material_Type='"+matType+"' and m.Material_Group='"+matGroup+"' and Material_Sub_Category like '%"+matSubcat+"%'";
		
		if(matType.equalsIgnoreCase("Customer Master") || matType.equalsIgnoreCase("Vendor Master")||matType.equals("FG")||matType.equals("HAWA"))
		{
			masterForm.setCustomerGroupId(matGroup);	
		}
		ResultSet rs=ad.selectQuery(getMaterialType);
		while(rs.next()){
			masterForm.setLocationId(rs.getString("Location"));
			masterForm.setMaterialGroupId(rs.getString("Material_Group"));
			masterForm.setMaterialType(rs.getString("Material_Type"));
			String role1=rs.getString("Priority");
			if(role1.equalsIgnoreCase("1")){
			masterForm.setRole1(rs.getString("Role"));
			masterForm.setApprover1(rs.getString("Approver_Id"));
			masterForm.setParllelAppr11(rs.getString("Parllel_Approver_1"));
			masterForm.setParllelAppr12(rs.getString("Parllel_Approver_2"));
			}
			String role2=rs.getString("Priority");
			if(role2.equalsIgnoreCase("2")){
			masterForm.setRole2(rs.getString("Role"));
			masterForm.setApprover2(rs.getString("Approver_Id"));
			masterForm.setParllelAppr21(rs.getString("Parllel_Approver_1"));
			masterForm.setParllelAppr22(rs.getString("Parllel_Approver_2"));
			}
			String role3=rs.getString("Priority");
			if(role3.equalsIgnoreCase("3")){
			masterForm.setRole3(rs.getString("Role"));
			masterForm.setApprover3(rs.getString("Approver_Id"));
			masterForm.setParllelAppr31(rs.getString("Parllel_Approver_1"));
			masterForm.setParllelAppr32(rs.getString("Parllel_Approver_2"));
			}
			String role4=rs.getString("Priority");
			if(role4.equalsIgnoreCase("4")){
			masterForm.setRole4(rs.getString("Role"));
			masterForm.setApprover4(rs.getString("Approver_Id"));
			masterForm.setParllelAppr41(rs.getString("Parllel_Approver_1"));
			masterForm.setParllelAppr42(rs.getString("Parllel_Approver_2"));
			}
			String role5=rs.getString("Priority");
			if(role5.equalsIgnoreCase("5")){
			masterForm.setRole5(rs.getString("Role"));
			masterForm.setApprover5(rs.getString("Approver_Id"));
			masterForm.setParllelAppr51(rs.getString("Parllel_Approver_1"));
			masterForm.setParllelAppr52(rs.getString("Parllel_Approver_2"));
			}
			String role6=rs.getString("Priority");
			if(role6.equalsIgnoreCase("6")){
			masterForm.setRole6(rs.getString("Role"));
			masterForm.setApprover6(rs.getString("Approver_Id"));
			masterForm.setParllelAppr61(rs.getString("Parllel_Approver_1"));
			masterForm.setParllelAppr62(rs.getString("Parllel_Approver_2"));
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		 ArrayList category=new ArrayList();
			ArrayList categoryShortName=new ArrayList();
			String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
			ResultSet rs2 = ad.selectQuery(cat);
			
			try {
				while (rs2.next())

				{
					category.add(rs2.getString("c_cat_name"));
					categoryShortName.add(rs2.getString("c_cat_sh_name"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}
			
			masterForm.setCategorylist(category);
			
			masterForm.setCategortShortlist(categoryShortName);
			
			
			

				
				ArrayList subCategoryList = new ArrayList();
				
				String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
				ResultSet rs3 = ad.selectQuery(cat1);
				
				try {
					while (rs3.next())

					{
						subCategoryList.add(rs3.getString("c_sub_cat_name"));
						
					}
				} catch (SQLException e1) {

					e1.printStackTrace();
				}

				
				masterForm.setSubcatList(subCategoryList);
			
		request.setAttribute("modifyButton", "modifyButton");
		return mapping.findForward("newMasterType");
	}
	public ActionForward prev(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
	
		int prev=masterForm.getPrev();
		int start=prev-10;
		//start--;
		//prev--;
		if(start<0)
			start=0;
		masterForm.setStartRecord(start+1);
		masterForm.setEndRecord(prev);
		int i=0;
		int row=0;
		//sform.setEndRecord(end);
		LinkedList alldata=new LinkedList();
		try{
				 alldata=getMaterialApproverList();
			 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 ArrayList NewList=new ArrayList();
			
			
			Iterator it=alldata.iterator();
			while(i<prev){
				if(it.hasNext()&&i==start){
					NewList.add(it.next());
					start++;
				}
				else
				it.next();
				i++;
			}
			if((prev-10)<=0){
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("nextButton", "ok");
		          }
		    
			else{
				request.setAttribute("previousButton", "ok");
			request.setAttribute("nextButton", "ok");
			}
			masterForm.setPrev(prev-10);
			masterForm.setNext(prev);
			request.setAttribute("displayRecordNo","ok");
			request.setAttribute("materialTypeList", NewList);
			masterData(mapping, form, request, response);
			
			
			EssDao ad=new EssDao();



			ArrayList category=new ArrayList();
					ArrayList categoryShortName=new ArrayList();
					String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
					ResultSet rs2 = ad.selectQuery(cat);

					try {
						while (rs2.next())

						{
							category.add(rs2.getString("c_cat_name"));
							categoryShortName.add(rs2.getString("c_cat_sh_name"));

						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}

					masterForm.setCategorylist(category);

					masterForm.setCategortShortlist(categoryShortName);





						ArrayList subCategoryList = new ArrayList();

						String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
						ResultSet rs3 = ad.selectQuery(cat1);

						try {
							while (rs3.next())

							{
								subCategoryList.add(rs3.getString("c_sub_cat_name"));

							}
						} catch (SQLException e1) {

							e1.printStackTrace();
						}


						masterForm.setSubcatList(subCategoryList);
			
			
		return mapping.findForward("displayApprovers");
	}
	
	public ActionForward next(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
	
		int nextval=masterForm.getNext();
		int i=0;
		i=nextval;
		masterForm.setStartRecord(i+1);
		masterForm.setPrev(nextval);
		int end=nextval+10;
		int start=0;
		int row=0;
		LinkedList alldata=new LinkedList();
			try{
					 alldata=getMaterialApproverList();
				 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			 ArrayList NewList=new ArrayList();
				Iterator it=alldata.iterator();
				try{
				while(start<end){
					if(it.hasNext()&&start==nextval){
						NewList.add(it.next());
						nextval++;
						
					}
					else
					it.next();
					start++;
				}
				
				}
				catch(Exception e){
					e.printStackTrace();
				}
				System.out.println(nextval);
				
				masterForm.setEndRecord(nextval);
				if(it.hasNext()){
					request.setAttribute("nextButton", "yes");
				request.setAttribute("previousButton", "ok");
				}
				else{
					request.setAttribute("disableNextButton","yes");
				request.setAttribute("previousButton", "ok");
				}
				request.setAttribute("displayRecordNo","ok");	
				
				masterForm.setNext(nextval);
				//myReqForm.setRow(row);
				request.setAttribute("materialTypeList", NewList);
				masterData(mapping, form, request, response);
				EssDao ad=new EssDao();



				ArrayList category=new ArrayList();
						ArrayList categoryShortName=new ArrayList();
						String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
						ResultSet rs2 = ad.selectQuery(cat);

						try {
							while (rs2.next())

							{
								category.add(rs2.getString("c_cat_name"));
								categoryShortName.add(rs2.getString("c_cat_sh_name"));

							}
						} catch (SQLException e1) {

							e1.printStackTrace();
						}

						masterForm.setCategorylist(category);

						masterForm.setCategortShortlist(categoryShortName);





							ArrayList subCategoryList = new ArrayList();

							String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
							ResultSet rs3 = ad.selectQuery(cat1);

							try {
								while (rs3.next())

								{
									subCategoryList.add(rs3.getString("c_sub_cat_name"));

								}
							} catch (SQLException e1) {

								e1.printStackTrace();
							}


							masterForm.setSubcatList(subCategoryList);
				
		return mapping.findForward("displayApprovers");
	}
	
	public ActionForward displayApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
	
		EssDao ad=new EssDao();
		LinkedList materialTypeList=new LinkedList();
		masterData(mapping, form, request, response);
		materialTypeList=getMaterialApproverList();
		ArrayList newList=new ArrayList();
		Iterator it=materialTypeList.iterator();
		int i=0;
		while(i<10){
			if(it.hasNext()){
				newList.add(it.next());
			i++;
			}
			else
				break;
		}
		masterForm.setStartRecord(1);
		masterForm.setEndRecord(i);
		masterForm.setNext(i);
	if(i>=10){
		
		request.setAttribute("displayRecordNo","ok");
		if(it.hasNext()){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("displayRecordNo","ok");
		}
		}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		
		}
	request.setAttribute("materialTypeList", newList);
		if(newList.size()==0)
		{
			masterForm.setMessage("No Records Found.");
			request.setAttribute("noRecords", "noRecords");
		}
		
		
		//-----------------
		ArrayList category=new ArrayList();
		ArrayList categoryShortName=new ArrayList();
		String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
		ResultSet rs2 = ad.selectQuery(cat);
		
		try {
			while (rs2.next())

			{
				category.add(rs2.getString("c_cat_name"));
				categoryShortName.add(rs2.getString("c_cat_sh_name"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		masterForm.setCategorylist(category);
		
		masterForm.setCategortShortlist(categoryShortName);
		
		
		

			
			ArrayList subCategoryList = new ArrayList();
			
			String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
			ResultSet rs3 = ad.selectQuery(cat1);
			
			try {
				while (rs3.next())

				{
					subCategoryList.add(rs3.getString("c_sub_cat_name"));
					
				}
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

			
			masterForm.setSubcatList(subCategoryList);
		
		//---------
		return mapping.findForward("displayApprovers");
	}
	
	public LinkedList getMaterialApproverList(){
		LinkedList materialTypeList=new LinkedList();
		EssDao ad=new EssDao();
		try{
			String getMaterialTypes="SELECT m.Location,m.Material_Type,m.Material_Group, mg.STXT, count(*) as total FROM Material_Approvers as m," +
			"MATERIAL_GROUP as mg where mg.MATERIAL_GROUP_ID=m.Material_Group GROUP BY m.Location,m.Material_Type, m.Material_Group,mg.STXT HAVING count(*) > 0";
		
		ResultSet rsMatType=ad.selectQuery(getMaterialTypes);
		while(rsMatType.next()){
			MaterialApproverForm approverForm=new MaterialApproverForm();
			approverForm.setLocationId(rsMatType.getString("Location"));
			approverForm.setMaterialType(rsMatType.getString("Material_Type"));
			approverForm.setMaterialGroupId(rsMatType.getString("STXT"));
			approverForm.setReqGroupId(rsMatType.getString("Material_Group"));
			approverForm.setTotalApprovers(rsMatType.getInt("total"));
			materialTypeList.add(approverForm);
		}
		
		String getMaterialTypes1="SELECT Location,Material_Type, Material_Group, count(*) as total FROM Material_Approvers where   Material_Group='' " +
		"GROUP BY Location, Material_Group,Material_Type HAVING count(*) > 0";
		 rsMatType=ad.selectQuery(getMaterialTypes1);
		while(rsMatType.next()){
			MaterialApproverForm approverForm=new MaterialApproverForm();
			approverForm.setLocationId(rsMatType.getString("Location"));
			approverForm.setMaterialType(rsMatType.getString("Material_Type"));
			approverForm.setMaterialGroupId("");
			approverForm.setTotalApprovers(rsMatType.getInt("total"));
			materialTypeList.add(approverForm);
		}
		String getCustomerList="SELECT Material_Type, Material_Group, count(*) as total FROM Material_Approvers where (MATERIAL_TYPE='Customer Master' " +
				"or MATERIAL_TYPE='Vendor Master')  GROUP BY  Material_Group,Material_Type HAVING count(*) > 0";
		 rsMatType=ad.selectQuery(getCustomerList);
		while(rsMatType.next()){
			MaterialApproverForm approverForm=new MaterialApproverForm();
			approverForm.setLocationId("");
			approverForm.setMaterialType(rsMatType.getString("Material_Type"));
			approverForm.setMaterialGroupId(rsMatType.getString("Material_Group"));
			approverForm.setReqGroupId(rsMatType.getString("Material_Group"));
			approverForm.setTotalApprovers(rsMatType.getInt("total"));
			materialTypeList.add(approverForm);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return  materialTypeList;
	}
	
	public ActionForward saveApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		LoginDao ad=new LoginDao();
		try{
		
			 String approver1=masterForm.getApprover1();
			 String approver2=masterForm.getApprover2();
			 String approver3=masterForm.getApprover3();
			 String approver4=masterForm.getApprover4();
			 String approver5=masterForm.getApprover5();
			 String approver6=masterForm.getApprover6();
	 		 String parllApp11=masterForm.getParllelAppr11();
			  String parllApp12=masterForm.getParllelAppr12();
			  String parllApp21=masterForm.getParllelAppr21();
			  String parllApp22=masterForm.getParllelAppr22();
			  String parllApp31=masterForm.getParllelAppr31();
			  String parllApp32=masterForm.getParllelAppr32();
			  
			  String parllApp41=masterForm.getParllelAppr41();
			  String parllApp42=masterForm.getParllelAppr42();
			  String parllApp51=masterForm.getParllelAppr51();
			  String parllApp52=masterForm.getParllelAppr52();
			  String parllApp61=masterForm.getParllelAppr61();
			  String parllApp62=masterForm.getParllelAppr62();
			  
			  int empexist=0;
			  
			  if((!approver1.equalsIgnoreCase(""))){
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver1+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "Approver 1 is not exist");
					  masterForm.setMessage("Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
				  
			  }
			  if((!parllApp11.equalsIgnoreCase(""))){
				  empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp11+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 1 Parllel Approver 1 is not exist");
					  masterForm.setMessage("In Priority 1 Parllel Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
				}
				if((!parllApp12.equalsIgnoreCase(""))){
					empexist=0;
					  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp12+"'";
					  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
					  while(rsEmpCheck.next())
					  {
						  empexist=rsEmpCheck.getInt(1);
					  }
					  if(empexist==0)
					  {
						  request.setAttribute("invalidEmp", "Parllel Approver 2 is not exist");
						  masterForm.setMessage("In Priority 1 Parllel Approver 2 is not exist");
						  masterData(mapping, form, request, response);
						  request.setAttribute("saveButton", "saveButton");
							return mapping.findForward("newMasterType");
							
					  }  
				}
			if((!approver2.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver2+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", " Approver 2 is not exist");
					  masterForm.setMessage(" Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }			  
			}
			if((!parllApp21.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp21+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 2 Parllel Approver 1 is not exist");
					  masterForm.setMessage("In Priority 2 Parllel Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
			}
			if((!parllApp22.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp22+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 2 Parllel Approver 2 is not exist");
					  masterForm.setMessage("In Priority 2 Parllel Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
			}
			if((!approver3.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver3+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", " Approver 3 is not exist");
					  masterForm.setMessage(" Approver 3 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }	 
			}
			if((!parllApp31.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp31+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 3 Parllel Approver 1 is not exist");
					  masterForm.setMessage("In Priority 3 Parllel Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }	  
			}
			if((!parllApp32.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp32+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 3 Parllel Approver 2 is not exist");
					  masterForm.setMessage("In Priority 3 Parllel Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  } 
			}
			if((!approver4.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver4+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", " Approver 4 is not exist");
					  masterForm.setMessage("Approver 4 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
				  }	  
			}
			if((!parllApp41.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp41+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 4 Parllel Approver 1 is not exist");
					  masterForm.setMessage("In Priority 4 Parllel Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }  
			}
			if((!parllApp42.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 4 Parllel Approver 2 is not exist");
					  masterForm.setMessage("In Priority 4 Parllel Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
			}
			if((!approver5.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver5+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", " Approver 5 is not exist");
					  masterForm.setMessage("Approver 5 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
				  } 
			}
			if((!parllApp51.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp51+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 5 Parllel Approver 1 is not exist");
					  masterForm.setMessage("In Priority 5 Parllel Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }  
			}
			if((!parllApp52.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 5 Parllel Approver 2 is not exist");
					  masterForm.setMessage("In Priority 5 Parllel Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
			}
			if((!approver6.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+approver6+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", " Approver 6 is not exist");
					  masterForm.setMessage("Approver 6 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
				  }
			}
			if((!parllApp61.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 6 Parllel Approver 1 is not exist");
					  masterForm.setMessage("In Priority 6 Parllel Approver 1 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
						
				  }
			}
			if((!parllApp62.equalsIgnoreCase(""))){
				empexist=0;
				  String empCheck="select COUNT(*) from emp_official_info where PERNR='"+parllApp42+"'";
				  ResultSet rsEmpCheck=ad.selectQuery(empCheck);
				  while(rsEmpCheck.next())
				  {
					  empexist=rsEmpCheck.getInt(1);
				  }
				  if(empexist==0)
				  {
					  request.setAttribute("invalidEmp", "In Priority 6 Parllel Approver 2 is not exist");
					  masterForm.setMessage("In Priority 6 Parllel Approver 2 is not exist");
					  masterData(mapping, form, request, response);
					  request.setAttribute("saveButton", "saveButton");
						return mapping.findForward("newMasterType");
				  }
			}
			
			
			  
			  
			int i=0;
			
	 int checkCout=0;		
	 
	String materialType=masterForm.getMaterialType();
	if(materialType.equalsIgnoreCase("Customer Master")){
		String group=masterForm.getCustomerGroupId();
		masterForm.setMaterialGroupId(group);
	}
	if(materialType.equalsIgnoreCase("Vendor Master")){
		String group=masterForm.getCustomerGroupId();
		masterForm.setMaterialGroupId(group);
	}
	//FG,HAWA
	if(materialType.equals("FG")||materialType.equals("HAWA")){
		String group=masterForm.getCustomerGroupId();
		masterForm.setMaterialGroupId(group);
	}
	 
	 String checkRecord="select count(*) from Material_Approvers where Location='"+masterForm.getLocationId()+"' and  Material_Type='"+masterForm.getMaterialType()+"' and Material_Group='"+masterForm.getMaterialGroupId()+"' ";
	 ResultSet rsCheck=ad.selectQuery(checkRecord);
	 while(rsCheck.next())
	 {
		 checkCout=rsCheck.getInt(1);
	 }
	 if(checkCout==0)
	 {		
	  if(!(approver1.equalsIgnoreCase("")))
	  {
		  String approverID=approver1;
        String insertApp1="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.getPriority1()+"','"+parllApp11+"','"+parllApp12+"','"+masterForm.getRole1()+"','"+masterForm.getSubCategoryId()+"')";
        i=ad.SqlExecuteUpdate(insertApp1);
	  }
	  if(!(approver2.equalsIgnoreCase("")))
	  {
		  String approverID=approver2;
		 
		  
		 
        String insertApp2="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.getPriority2()+"','"+parllApp21+"','"+parllApp22+"','"+masterForm.getRole2()+"','"+masterForm.getSubCategoryId()+"')";
        i=ad.SqlExecuteUpdate(insertApp2);
	  }
	  if(!(approver3.equalsIgnoreCase("")))
	  {
		  String approverID=approver3;
		 
		  
		 
        String insertApp3="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.getPriority3()+"','"+parllApp31+"','"+parllApp32+"','"+masterForm.getRole3()+"','"+masterForm.getSubCategoryId()+"')";
        i=ad.SqlExecuteUpdate(insertApp3);
	  }
	  if(!(approver4.equalsIgnoreCase("")))
	  {
		  String approverID=approver4;
		  
		  
		
        String insertApp4="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.getPriority4()+"','"+parllApp41+"','"+parllApp42+"','"+masterForm.getRole4()+"','"+masterForm.getSubCategoryId()+"')";
        i=ad.SqlExecuteUpdate(insertApp4);
	  }
	  if(!(approver5.equalsIgnoreCase("")))
	  {
		  String approverID=approver5;
		 
		 
		
        String insertApp5="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Material_Sub_Category) " +
        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.getPriority5()+"','"+parllApp51+"','"+parllApp52+"','"+masterForm.getRole5()+"','"+masterForm.getSubCategoryId()+"')";
        i=ad.SqlExecuteUpdate(insertApp5);
	  }
	  if(!(approver6.equalsIgnoreCase("")))
	  {
		  
		  String approverID=approver6;
		  
		 
		 
        String insertApp6="insert into Material_Approvers(Location,Material_Type,Material_Group,Approver_Id,Priority,Parllel_Approver_1,Parllel_Approver_2,Role,Approver_Name,Material_Sub_Category) " +
        " values('"+masterForm.getLocationId()+"','"+masterForm.getMaterialType()+"','"+masterForm.getMaterialGroupId()+"','"+approverID+"','"+masterForm.getPriority6()+"','"+parllApp61+"','"+parllApp62+"','"+masterForm.getRole6()+"','"+masterForm.getSubCategoryId()+"')";
        i=ad.SqlExecuteUpdate(insertApp6);
	  }		
		
	
		if(i>0)
		{
			masterForm.setMessage("Data Saved Successfully");
			createType(mapping, form, request, response);
		}else{
			masterForm.setMessage("Error..Data Not Saved");
		}
	 }else{
		 masterForm.setMessage("Approver alredy exist.data not saved");
	 }
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterData(mapping, form, request, response);
		return mapping.findForward("newMasterType");
	}
		

	
	public ActionForward searchContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		LoginDao ad=new LoginDao();
		String reqFiled=masterForm.getReqFiled();
		masterForm.setReqFiled(reqFiled);
		try{
		
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			 HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("location_code")+" - "+rs11.getString("LOCNAME"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department ");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			masterForm.setDepartmentList(deparmentList);
		
			String locationId=masterForm.getLocationId();
			String department=masterForm.getDepartment();
			String firstName=masterForm.getEmpName();
			ArrayList contactList=new ArrayList();
			EmpOfficalInformationForm emp=null;
			
			
			String getContactList="Select  u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code,desg.DSGSTXT " +
					" from emp_official_info as u,Location as loc,department as dept,designation as desg where u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DPTID  ";
		if(!locationId.equalsIgnoreCase(""))
		{
			getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
		}
		if(!department.equalsIgnoreCase(""))
		{
			getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
		}
		if(!firstName.equalsIgnoreCase(""))
		{
			getContactList=getContactList+"and  (u.EMP_FULLNAME like'%"+firstName+"%' or u.PERNR like'%"+firstName+"%')";
		}
		
		
		getContactList=getContactList;
		
			ResultSet rs=ad.selectQuery(getContactList);
			while(rs.next())
			{
				emp=new EmpOfficalInformationForm();
				emp.setEmployeeNumber(rs.getString("PERNR"));
				emp.setFirstName(rs.getString("EMP_FULLNAME"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocationId(rs.getString("location_code"));
				contactList.add(emp);
			}			
		
			request.setAttribute("userDetails", contactList);
			
			if(contactList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				
				masterForm.setMessage("No Contacts");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayUsers");
	}
	
	public ActionForward displayListUsers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		
		String reqFiled=request.getParameter("reqFiled");
		masterForm.setReqFiled(reqFiled);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		UserDao ad=new UserDao();
		String plantID=user.getPlantId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("location_code")+" - "+rs11.getString("LOCNAME"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			masterForm.setDepartmentList(deparmentList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayUsers");
	}
	
	public ActionForward createType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		EssDao ad=new EssDao();
		masterForm.setLocationId("");
		masterForm.setMaterialType("");
		masterForm.setMaterialGroupId("");
		try{
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		while(rs11.next()) {
			locationList.add(rs11.getString("LOCATION_CODE"));
			locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		LinkedList materTypeIDList=new LinkedList();
		LinkedList materialTypeIdValueList=new LinkedList();
		String getMaterials="select * from MATERIAL_TYPE";
		ResultSet rsMaterial=ad.selectQuery(getMaterials);
		while(rsMaterial.next())
		{
			materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
			materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
		}
		masterForm.setMaterTypeIDList(materTypeIDList);
		masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
		
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		while(rsMaterialGroup.next())
		{
			materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
		}
		masterForm.setMaterGroupIDList(materGroupIDList);
		masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("saveButton", "saveButton");
		
		
		//-----------------
				ArrayList category=new ArrayList();
				ArrayList categoryShortName=new ArrayList();
				String cat = "select distinct c_cat_name , c_cat_sh_name from CAT_SUBCAT_MASTER where c_prefix = 'SAP'";
				ResultSet rs2 = ad.selectQuery(cat);
				
				try {
					while (rs2.next())

					{
						category.add(rs2.getString("c_cat_name"));
						categoryShortName.add(rs2.getString("c_cat_sh_name"));
						
					}
				} catch (SQLException e1) {

					e1.printStackTrace();
				}
				
				masterForm.setCategorylist(category);
				
				masterForm.setCategortShortlist(categoryShortName);
				
				
				

					
					ArrayList subCategoryList = new ArrayList();
					
					String cat1 = "select distinct  c_sub_cat_name from CAT_SUBCAT_MASTER where  c_prefix ='SAP'";
					ResultSet rs3 = ad.selectQuery(cat1);
					
					try {
						while (rs3.next())

						{
							subCategoryList.add(rs3.getString("c_sub_cat_name"));
							
						}
					} catch (SQLException e1) {

						e1.printStackTrace();
					}

					
					masterForm.setSubcatList(subCategoryList);
				
				//---------

		return mapping.findForward("newMasterType");
	}
	
	public ActionForward masterData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialApproverForm masterForm=(MaterialApproverForm)form;
		EssDao ad=new EssDao();
		try{
			ResultSet rs11 = ad.selectQuery("select * from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
			
			LinkedList materGroupIDList=new LinkedList();
			LinkedList materialGroupIdValueList=new LinkedList();
			
			String getMaterialGroup="select * from MATERIAL_GROUP";
			ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
			}
			masterForm.setMaterGroupIDList(materGroupIDList);
			masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
			}catch (Exception e) {
				e.printStackTrace();
			}
			return mapping.findForward("displayApprovers");
	}
	
}
