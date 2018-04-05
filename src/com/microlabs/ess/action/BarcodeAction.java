package com.microlabs.ess.action;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.BarcodeForm;
import com.microlabs.ess.form.LeaveForm;
import com.microlabs.utilities.UserInfo;

public class BarcodeAction extends DispatchAction{
	
	public ActionForward barcode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		BarcodeForm help=(BarcodeForm)form;
		EssDao ad=new EssDao();
		
		ArrayList barcodelist=new ArrayList();	
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode_temp.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode_temp.* " +
			"from barcode_temp left outer join emp_official_info on emp_official_info.pernr = barcode_temp.accepted_by"
			+ " left outer join material_type on material_type.material_group_id=barcode_temp.MTART "
			+ " where accepted_by = '"+user.getEmployeeNo()+"') as sub ";
		ResultSet rs=ad.selectQuery(sql);
			try {
				while (rs.next()) {
					BarcodeForm help1 =new BarcodeForm();
					help1.setEmpName(rs.getString("emp_fullname"));
					help1.setmBLNR(rs.getString("MBLNR"));
					help1.setmJAHR(rs.getString("MJAHR"));
					help1.setlOEKZ(rs.getString("LOEKZ"));
					help1.setbLDAT(rs.getString("BLDAT1"));
					help1.setbUDAT(rs.getString("BUDAT"));
					help1.setwERKS(rs.getString("WERKS"));
					help1.setmTART(rs.getString("m_desc"));
					help1.seteKGRP(rs.getString("EKGRP"));
					help1.setxBLNR(rs.getString("XBLNR"));
					help1.seteBELN(rs.getString("EBELN"));
					help1.setlIFNR(rs.getString("LIFNR"));
					help1.setxSTBW(rs.getString("XSTBW"));
					help1.setbWART(rs.getString("BWART"));
					help1.setbSART(rs.getString("BSART"));
					help1.setdDMBTR(rs.getString("DMBTR"));
					help1.setbBKTXT(rs.getString("BKTXT"));
					help1.setsENT_DATE(rs.getString("SENT_DATE"));
					help1.setsSENT_BY(rs.getString("SENT_BY"));
					help1.setdCKET_NO(rs.getString("DOCKET_NO"));
					help1.setcNAME(rs.getString("COURIER_NAME"));
					help1.setpD_DT(rs.getString("PRECD_DT"));
					help1.setName1(rs.getString("NAME1"));
					help1.setoORT01(rs.getString("ORT01"));
					help1.setAccdate(rs.getString("accdate"));
					barcodelist.add(help1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			help.setmBLNR("");
			request.setAttribute("barcodelist", barcodelist);
		
		return mapping.findForward("barcode");
	}
	
	
	public ActionForward barcodeSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		BarcodeForm help=(BarcodeForm)form;
		EssDao ad=new EssDao();
		
		String s ="select material_type.m_desc ,  convert(nvarchar(10),PRECD_DT,103) as PRECD_DT1, convert(nvarchar(10),sENT_DATE,103) as sENT_DATE1 ,convert(nvarchar(10),BUDAT,103) as BUDAT1  , convert(nvarchar(10),BLDAT,103) as BLDAT1  ,barcode.* from "
				+ " barcode left outer join material_type on material_type.material_group_id=barcode.MTART  where  MBLNR='"+help.getmBLNR()+"' ";
		ResultSet rs = ad.selectQuery(s);
		try {
			if(rs.next())
			{
				help.setmBLNR(rs.getString("MBLNR"));
				help.setmJAHR(rs.getString("MJAHR"));
				help.setlOEKZ(rs.getString("LOEKZ"));
				if(!rs.getString("BLDAT1").contains("0000"))
				help.setbLDAT(rs.getString("BLDAT1"));
				if(!rs.getString("BUDAT1").contains("0000"))
				help.setbUDAT(rs.getString("BUDAT1"));
				help.setwERKS(rs.getString("WERKS"));
				help.setmTART(rs.getString("m_desc"));
				help.seteKGRP(rs.getString("EKGRP"));
				help.setxBLNR(rs.getString("XBLNR"));
				help.seteBELN(rs.getString("EBELN"));
				help.setlIFNR(rs.getString("LIFNR"));
				help.setxSTBW(rs.getString("XSTBW"));
				help.setbWART(rs.getString("BWART"));
				help.setbSART(rs.getString("BSART"));
				help.setdDMBTR(rs.getString("DMBTR"));
				help.setbBKTXT(rs.getString("BKTXT"));
				if(!rs.getString("SENT_DATE1").contains("0000"))
				help.setsENT_DATE(rs.getString("SENT_DATE1"));
				help.setsSENT_BY(rs.getString("SENT_BY"));
				help.setdCKET_NO(rs.getString("DOCKET_NO"));
				help.setcNAME(rs.getString("COURIER_NAME"));
				if(!rs.getString("PRECD_DT1").contains("0000"))
				help.setpD_DT(rs.getString("PRECD_DT1"));
				help.setName1(rs.getString("NAME1"));
				help.setoORT01(rs.getString("ORT01"));
				
				help.setMessage("");
				request.setAttribute("bar", "bar");

			}
			
			else
			{
				help.setMessage("Invalid Barcode");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return mapping.findForward("barcode");
	}
	
	
	
	public ActionForward barcodeInsert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		BarcodeForm help=(BarcodeForm)form;
		EssDao ad=new EssDao();
		
		String update ="insert into  barcode_temp (MBLNR,MJAHR,LOEKZ,BLDAT,BUDAT,WERKS,MTART,EKGRP,XBLNR,EBELN,LIFNR,XSTBW,BWART,BSART,DMBTR,BKTXT,SENT_DATE,SENT_BY,DOCKET_NO,COURIER_NAME,PRECD_DT,NAME1,ORT01,sap_sent_flag,sap_message,sap_sent_date,accepted_date,accepted_by,status_flag) "
				+ "  select MBLNR,MJAHR,LOEKZ,BLDAT,BUDAT,WERKS,MTART,EKGRP,XBLNR,EBELN,LIFNR,XSTBW,BWART,BSART,DMBTR,BKTXT,SENT_DATE,SENT_BY,DOCKET_NO,COURIER_NAME,PRECD_DT,NAME1,ORT01,sap_sent_flag,"
				+ " sap_message,sap_sent_date,accepted_date,'"+user.getEmployeeNo()+"', 1 from  barcode "
				+ " where  MBLNR='"+help.getmBLNR()+"' and deleted=0 and MBLNR not in (select MBLNR from barcode_temp where accepted_by='"+user.getEmployeeNo()+"'  ) ";
   		int i = ad.SqlExecuteUpdate(update);
   		
   		if(i==0)
   		{
   			help.setMessage("Barcode already exist/Invalid Barcode..Try Again");
   			BARCODE_BAPI.main(null); 
   		}
   		
   		else
   		{
   			
   			help.setMessage("");
   		}
   		
   		
   		ArrayList barcodelist=new ArrayList();	
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode_temp.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode_temp.* " +
			"from barcode_temp left outer join emp_official_info on emp_official_info.pernr = barcode_temp.accepted_by"
			+ " left outer join material_type on material_type.material_group_id=barcode_temp.MTART "
			+ " where accepted_by = '"+user.getEmployeeNo()+"') as sub ";
		ResultSet rs=ad.selectQuery(sql);
			try {
				while (rs.next()) {
					BarcodeForm help1 =new BarcodeForm();
					help1.setEmpName(rs.getString("emp_fullname"));
					help1.setmBLNR(rs.getString("MBLNR"));
					help1.setmJAHR(rs.getString("MJAHR"));
					help1.setlOEKZ(rs.getString("LOEKZ"));
					help1.setbLDAT(rs.getString("BLDAT1"));
					help1.setbUDAT(rs.getString("BUDAT"));
					help1.setwERKS(rs.getString("WERKS"));
					help1.setmTART(rs.getString("m_desc"));
					help1.seteKGRP(rs.getString("EKGRP"));
					help1.setxBLNR(rs.getString("XBLNR"));
					help1.seteBELN(rs.getString("EBELN"));
					help1.setlIFNR(rs.getString("LIFNR"));
					help1.setxSTBW(rs.getString("XSTBW"));
					help1.setbWART(rs.getString("BWART"));
					help1.setbSART(rs.getString("BSART"));
					help1.setdDMBTR(rs.getString("DMBTR"));
					help1.setbBKTXT(rs.getString("BKTXT"));
					help1.setsENT_DATE(rs.getString("SENT_DATE"));
					help1.setsSENT_BY(rs.getString("SENT_BY"));
					help1.setdCKET_NO(rs.getString("DOCKET_NO"));
					help1.setcNAME(rs.getString("COURIER_NAME"));
					help1.setpD_DT(rs.getString("PRECD_DT"));
					help1.setName1(rs.getString("NAME1"));
					help1.setoORT01(rs.getString("ORT01"));
					help1.setAccdate(rs.getString("accdate"));
					barcodelist.add(help1);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			help.setmBLNR("");
			request.setAttribute("barcodelist", barcodelist);
   		
   		

		
		return mapping.findForward("barcode");
	}
	
	
	
	public ActionForward barcodeUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		BarcodeForm help=(BarcodeForm)form;
		EssDao ad=new EssDao();
		
		String update ="Update barcode set accepted_date=getdate() ,accepted_by='"+user.getEmployeeNo()+"'  "
				+ " where  MBLNR in (select MBLNR from barcode_temp where accepted_by ='"+user.getEmployeeNo()+"')  and deleted=0 ";
   		int i = ad.SqlExecuteUpdate(update);
   		
   		if(i>0)
   		{
   			String update1 ="Insert into barcode_history(MBLNR,accepted_date,accepted_by)   "
   					+ " select MBLNR,getdate(),'"+user.getEmployeeNo()+"' from barcode_temp where accepted_by ='"+user.getEmployeeNo()+"'  ";
   			int k = ad.SqlExecuteUpdate(update1);
   			
   			
   			String update2 ="delete from barcode_temp where accepted_by ='"+user.getEmployeeNo()+"'  ";
   			int j = ad.SqlExecuteUpdate(update2);
   			
   			
   		}
   		
   		help.setmBLNR("");
   		help.setmJAHR("");
   		help.setlOEKZ("");
   		help.setbLDAT("");
   		help.setbUDAT("");
   		help.setwERKS("");
   		help.setmTART("");
   		help.seteKGRP("");
   		help.setxBLNR("");
   		help.seteBELN("");
   		help.setlIFNR("");
   		help.setxSTBW("");
   		help.setbWART("");
   		help.setbSART("");
   		help.setdDMBTR("");
   		help.setbBKTXT("");
   		help.setsENT_DATE("");
   		help.setsSENT_BY("");
   		help.setdCKET_NO("");
   		help.setcNAME("");
   		help.setpD_DT("");
   		help.setName1("");
   		help.setoORT01("");
   		help.setMessage("Succesfully submitted");

		
		return mapping.findForward("barcode");
	}
	
	
	
	
	public ActionForward postalreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		BarcodeForm help1=(BarcodeForm)form;
		EssDao ad=new EssDao();

		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  try{
		  
		    String startDate="";
		    String endDate="";
		    String query="";
		    
		    if(help1.getDatefrom()==null)
            {
		    	help1.setDatefrom("");
		    	
            }
		    
		    if(help1.getType()==null)
            {
		    	help1.setType("");
		    	
            }
		    
		    if(help1.getEmployeeno()==null)
		    {
		    	help1.setEmployeeno("");
		    }
		    
		    
            if(help1.getDatefrom().contains("/"))
            {	
		    String b[]=help1.getDatefrom().split("/");
			startDate=b[2]+"-"+b[1]+"-"+b[0];
		  
			String c[]=help1.getDateto().split("/");
			endDate=c[2]+"-"+c[1]+"-"+c[0];
            }
			
		    if(help1.getType().equalsIgnoreCase("CD"))
		    {
		    	query=" and bldat between '"+startDate+"' and '"+endDate+"'  ";
		    }
		    else if(help1.getType().equalsIgnoreCase("RD"))
		    {
		    	query=" and convert(date,accepted_date) between '"+startDate+"' and '"+endDate+"' ";
		    }
		    
		    else
		    {
		    	query=" and convert(date,accepted_date) between '' and '' ";
		    }
		    
		    
		    if(help1.getLocation()==null)
		    {
		    	help1.setLocation("");
		    }
		    if(!help1.getLocation().equalsIgnoreCase(""))
		    {	
		    	query=query+" and barcode.WERKS = '"+help1.getLocation()+"' ";
		    }
		    
		  
		    if(help1.getPur()==null)
		    {
		    	help1.setPur("");
		    }
		    if(!help1.getPur().equalsIgnoreCase(""))
		    {	
		    	query=query+" and EKGRP = '"+help1.getPur()+"' ";
		    }
		    
		    if(!help1.getEmployeeno().equalsIgnoreCase(""))
		    {
		    	query=query+" and accepted_by = '"+help1.getEmployeeno()+"' ";
		    }
		    
		    
		    
		    String getTotalRecords="select count(*) from barcode where mblnr!='' "+query+" ";
			  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
			  while(rsTotalRecods.next())
			  {
				  totalRecords=rsTotalRecods.getInt(1);
			  }
			  if(totalRecords>=10)
			  {
				  help1.setTotalRecords1(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  help1.setStartRecord1(1);
			  help1.setEndRecord1(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  help1.setTotalRecords1(totalRecords);
				  help1.setStartRecord1(1);
				  help1.setEndRecord1(totalRecords); 
			  }	
		  
		  ArrayList barcodelist=new ArrayList();	
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode.* " +
			"from barcode left outer join emp_official_info on emp_official_info.pernr = barcode.accepted_by "
			+ " left outer join material_type on material_type.material_group_id=barcode.MTART "
			+ " where mblnr!='' "+query+" ) as sub Where  sub.RowNum between 1 and 10";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				BarcodeForm help =new BarcodeForm();
				help.setEmpName(rs.getString("emp_fullname"));
				help.setmBLNR(rs.getString("MBLNR"));
				help.setmJAHR(rs.getString("MJAHR"));
				help.setlOEKZ(rs.getString("LOEKZ"));
				help.setbLDAT(rs.getString("BLDAT1"));
				help.setbUDAT(rs.getString("BUDAT"));
				help.setwERKS(rs.getString("WERKS"));
				help.setmTART(rs.getString("m_desc"));
				help.seteKGRP(rs.getString("EKGRP"));
				help.setxBLNR(rs.getString("XBLNR"));
				help.seteBELN(rs.getString("EBELN"));
				help.setlIFNR(rs.getString("LIFNR"));
				help.setxSTBW(rs.getString("XSTBW"));
				help.setbWART(rs.getString("BWART"));
				help.setbSART(rs.getString("BSART"));
				help.setdDMBTR(rs.getString("DMBTR"));
				help.setbBKTXT(rs.getString("BKTXT"));
				help.setsENT_DATE(rs.getString("SENT_DATE"));
				help.setsSENT_BY(rs.getString("SENT_BY"));
				help.setdCKET_NO(rs.getString("DOCKET_NO"));
				help.setcNAME(rs.getString("COURIER_NAME"));
				help.setpD_DT(rs.getString("PRECD_DT"));
				help.setName1(rs.getString("NAME1"));
				help.setoORT01(rs.getString("ORT01"));
				help.setAccdate(rs.getString("accdate"));
				
				barcodelist.add(help);
			}
			request.setAttribute("barcodelist", barcodelist);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(barcodelist.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				help1.setMessage("No records are found");
			}
			System.out.println(help1.getTotalRecords1());
			System.out.println(help1.getStartRecord1());
			System.out.println(help1.getEndRecord1());
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		  
		           ResultSet rs11 = ad.selectQuery("select LOCID," +
					"LOCNAME,location_code from location");
					ArrayList locationList=new ArrayList();
					ArrayList locationLabelList=new ArrayList();
					try {
						while(rs11.next()) {
							locationList.add(rs11.getString("location_code"));
							locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					help1.setLocationIdList(locationList);
					help1.setLocationLabelList(locationLabelList);
	

					rs11 = ad.selectQuery("select purchase_group_id from PURCHASE_GROUP");
					ArrayList purchaselList=new ArrayList();
					try {
						while(rs11.next()) {
							purchaselList.add(rs11.getString("purchase_group_id"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					help1.setPurchaselList(purchaselList);		
	
		
		return mapping.findForward("barcodeList");
	}
	
	
	public ActionForward exportpostalreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String exportdate=ft.format(dNow);
		
		response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_Postal_received_Report.xls");
		
		BarcodeForm help1=(BarcodeForm)form;
		EssDao ad=new EssDao();

		String module=request.getParameter("id"); 
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			request.setAttribute("MenuIcon", module);
			return mapping.findForward("displayiFrameSession");
		}
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  try{
		  
		    String startDate="";
		    String endDate="";
		    String query="";
		    
		    if(help1.getDatefrom()==null)
            {
		    	help1.setDatefrom("");
		    	
            }
		    
		    if(help1.getType()==null)
            {
		    	help1.setType("");
		    	
            }
		    
		    if(help1.getEmployeeno()==null)
		    {
		    	help1.setEmployeeno("");
		    }
		    
		    
		   
		    
            if(help1.getDatefrom().contains("/"))
            {	
		    String b[]=help1.getDatefrom().split("/");
			startDate=b[2]+"-"+b[1]+"-"+b[0];
		  
			String c[]=help1.getDateto().split("/");
			endDate=c[2]+"-"+c[1]+"-"+c[0];
            }
			
		    if(help1.getType().equalsIgnoreCase("CD"))
		    {
		    	query=" and bldat between '"+startDate+"' and '"+endDate+"'  ";
		    }
		    else if(help1.getType().equalsIgnoreCase("RD"))
		    {
		    	query=" and convert(date,accepted_date) between '"+startDate+"' and '"+endDate+"' ";
		    }
		    
		    else
		    {
		    	query=" and accepted_date between '' and '' ";
		    }
		    
		    
		    if(help1.getLocation()==null)
		    {
		    	help1.setLocation("");
		    }
		    if(!help1.getLocation().equalsIgnoreCase(""))
		    {	
		    	query=query+" and barcode.WERKS = '"+help1.getLocation()+"' ";
		    }
		    
		  
		    if(help1.getPur()==null)
		    {
		    	help1.setPur("");
		    }
		    if(!help1.getPur().equalsIgnoreCase(""))
		    {	
		    	query=query+" and EKGRP = '"+help1.getPur()+"' ";
		    }
		    
		    
		    if(!help1.getEmployeeno().equalsIgnoreCase(""))
		    {
		    	query=query+" and accepted_by = '"+help1.getEmployeeno()+"' ";
		    }
		    
		    
		    
		   
		  
		  ArrayList barcodelist=new ArrayList();	
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode.* " +
			"from barcode left outer join emp_official_info on emp_official_info.pernr = barcode.accepted_by "
			+ " left outer join material_type on material_type.material_group_id=barcode.MTART "
			+ " where mblnr!='' "+query+" ) as sub ";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				BarcodeForm help =new BarcodeForm();
				help.setEmpName(rs.getString("emp_fullname"));
				help.setmBLNR(rs.getString("MBLNR"));
				help.setmJAHR(rs.getString("MJAHR"));
				help.setlOEKZ(rs.getString("LOEKZ"));
				help.setbLDAT(rs.getString("BLDAT1"));
				help.setbUDAT(rs.getString("BUDAT"));
				help.setwERKS(rs.getString("WERKS"));
				help.setmTART(rs.getString("m_desc"));
				help.seteKGRP(rs.getString("EKGRP"));
				help.setxBLNR(rs.getString("XBLNR"));
				help.seteBELN(rs.getString("EBELN"));
				help.setlIFNR(rs.getString("LIFNR"));
				help.setxSTBW(rs.getString("XSTBW"));
				help.setbWART(rs.getString("BWART"));
				help.setbSART(rs.getString("BSART"));
				help.setdDMBTR(rs.getString("DMBTR"));
				help.setbBKTXT(rs.getString("BKTXT"));
				help.setsENT_DATE(rs.getString("SENT_DATE"));
				help.setsSENT_BY(rs.getString("SENT_BY"));
				help.setdCKET_NO(rs.getString("DOCKET_NO"));
				help.setcNAME(rs.getString("COURIER_NAME"));
				help.setpD_DT(rs.getString("PRECD_DT"));
				help.setName1(rs.getString("NAME1"));
				help.setoORT01(rs.getString("ORT01"));
				help.setAccdate(rs.getString("accdate"));
				
				barcodelist.add(help);
			}
			request.setAttribute("barcodelist", barcodelist);
			request.setAttribute("disablePreviousButton","disablePreviousButton");
			if(barcodelist.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				help1.setMessage("No records are found");
			}
			System.out.println(help1.getTotalRecords1());
			System.out.println(help1.getStartRecord1());
			System.out.println(help1.getEndRecord1());
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		  
	
		
		return mapping.findForward("exportbarcodeList");
	}

	
	
	public ActionForward previouspostalreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		
		EssDao ad = new EssDao();
		
		BarcodeForm help1 =(BarcodeForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		int userID=user.getId();
		ArrayList a1=new ArrayList();
	try{
		int totalRecords=help1.getTotalRecords1();//21
		int endRecord=help1.getStartRecord1()-1;//20
		int startRecord=help1.getStartRecord1()-10;//11
		
		

	    String startDate="";
	    String endDate="";
	    String query="";
	    
	    if(help1.getDatefrom()==null)
        {
	    	help1.setDatefrom("");
	    	
        }
	    
	    if(help1.getType()==null)
        {
	    	help1.setType("");
	    	
        }
	    
	    if(help1.getEmployeeno()==null)
	    {
	    	help1.setEmployeeno("");
	    }
	    
	    
        if(help1.getDatefrom().contains("/"))
        {	
	    String b[]=help1.getDatefrom().split("/");
		startDate=b[2]+"-"+b[1]+"-"+b[0];
	  
		String c[]=help1.getDateto().split("/");
		endDate=c[2]+"-"+c[1]+"-"+c[0];
        }
		
	    if(help1.getType().equalsIgnoreCase("CD"))
	    {
	    	query=" and bldat between '"+startDate+"' and '"+endDate+"'  ";
	    }
	    else if(help1.getType().equalsIgnoreCase("RD"))
	    {
	    	query=" and convert(date,accepted_date) between '"+startDate+"' and '"+endDate+"' ";
	    }
	    
	    else
	    {
	    	query=" and accepted_date between '' and '' ";
	    }
	    
	    if(help1.getLocation()==null)
	    {
	    	help1.setLocation("");
	    }
	    if(!help1.getLocation().equalsIgnoreCase(""))
	    {	
	    	query=query+" and barcode.WERKS = '"+help1.getLocation()+"' ";
	    }
	    
	  
	    if(help1.getPur()==null)
	    {
	    	help1.setPur("");
	    }
	    if(!help1.getPur().equalsIgnoreCase(""))
	    {	
	    	query=query+" and EKGRP = '"+help1.getPur()+"' ";
	    }
	    
	    if(!help1.getEmployeeno().equalsIgnoreCase(""))
	    {
	    	query=query+" and accepted_by = '"+help1.getEmployeeno()+"' ";
	    }
	    
	    
		  ArrayList barcodelist=new ArrayList();
		  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode.ID ) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode.* " 
				  +"from barcode left outer join emp_official_info on emp_official_info.pernr = barcode.accepted_by "
				  + " left outer join material_type on material_type.material_group_id=barcode.MTART "
		+ " where mblnr!='' "+query+") as sub ";
		ResultSet rs=ad.selectQuery(sql);
			while (rs.next()) {
				BarcodeForm help =new BarcodeForm();
				help.setEmpName(rs.getString("emp_fullname"));
				help.setmBLNR(rs.getString("MBLNR"));
				help.setmJAHR(rs.getString("MJAHR"));
				help.setlOEKZ(rs.getString("LOEKZ"));
				help.setbLDAT(rs.getString("BLDAT1"));
				help.setbUDAT(rs.getString("BUDAT"));
				help.setwERKS(rs.getString("WERKS"));
				help.setmTART(rs.getString("m_desc"));
				help.seteKGRP(rs.getString("EKGRP"));
				help.setxBLNR(rs.getString("XBLNR"));
				help.seteBELN(rs.getString("EBELN"));
				help.setlIFNR(rs.getString("LIFNR"));
				help.setxSTBW(rs.getString("XSTBW"));
				help.setbWART(rs.getString("BWART"));
				help.setbSART(rs.getString("BSART"));
				help.setdDMBTR(rs.getString("DMBTR"));
				help.setbBKTXT(rs.getString("BKTXT"));
				help.setsENT_DATE(rs.getString("SENT_DATE"));
				help.setsSENT_BY(rs.getString("SENT_BY"));
				help.setdCKET_NO(rs.getString("DOCKET_NO"));
				help.setcNAME(rs.getString("COURIER_NAME"));
				help.setpD_DT(rs.getString("PRECD_DT"));
				help.setName1(rs.getString("NAME1"));
				help.setoORT01(rs.getString("ORT01"));
				help.setAccdate(rs.getString("accdate"));
				barcodelist.add(help);
			}
			request.setAttribute("barcodelist", barcodelist);
			
	}catch (Exception e) {
		e.printStackTrace();
		}
	
	
	           ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setLocationIdList(locationList);
				help1.setLocationLabelList(locationLabelList);
				
				
				rs11 = ad.selectQuery("select purchase_group_id from PURCHASE_GROUP");
				ArrayList purchaselList=new ArrayList();
				try {
					while(rs11.next()) {
						purchaselList.add(rs11.getString("purchase_group_id"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setPurchaselList(purchaselList);		

		return mapping.findForward("barcodeList");
	}
	public ActionForward nextpostalreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		BarcodeForm help1 =(BarcodeForm)form;
		HttpSession session=request.getSession();
		
		EssDao ad = new EssDao();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		try{
			int totalRecords=help1.getTotalRecords1();//21
			int startRecord=help1.getStartRecord1();//11
			int endRecord=help1.getEndRecord1();
			ArrayList barcodelist=new ArrayList();
			
			

		    String startDate="";
		    String endDate="";
		    String query="";
		    
		    if(help1.getDatefrom()==null)
            {
		    	help1.setDatefrom("");
		    	
            }
		    
		    if(help1.getType()==null)
            {
		    	help1.setType("");
		    	
            }
		    
		    if(help1.getEmployeeno()==null)
		    {
		    	help1.setEmployeeno("");
		    }
		    
            if(help1.getDatefrom().contains("/"))
            {	
		    String b[]=help1.getDatefrom().split("/");
			startDate=b[2]+"-"+b[1]+"-"+b[0];
		  
			String c[]=help1.getDateto().split("/");
			endDate=c[2]+"-"+c[1]+"-"+c[0];
            }
			
		    if(help1.getType().equalsIgnoreCase("CD"))
		    {
		    	query=" and bldat between '"+startDate+"' and '"+endDate+"'  ";
		    }
		    else if(help1.getType().equalsIgnoreCase("RD"))
		    {
		    	query=" and convert(date,accepted_date) between '"+startDate+"' and '"+endDate+"' ";
		    }
		    
		    else
		    {
		    	query=" and accepted_date between '' and '' ";
		    }
		    
		    
		    if(help1.getLocation()==null)
		    {
		    	help1.setLocation("");
		    }
		    if(!help1.getLocation().equalsIgnoreCase(""))
		    {	
		    	query=query+" and barcode.WERKS = '"+help1.getLocation()+"' ";
		    }
		    
		  
		    if(help1.getPur()==null)
		    {
		    	help1.setPur("");
		    }
		    if(!help1.getPur().equalsIgnoreCase(""))
		    {	
		    	query=query+" and EKGRP = '"+help1.getPur()+"' ";
		    }
		    
		 
		    
		    if(!help1.getEmployeeno().equalsIgnoreCase(""))
		    {
		    	query=query+" and accepted_by = '"+help1.getEmployeeno()+"' ";
		    }
		    
		    
		  
			
			if(totalRecords>endRecord)
			{
				if(totalRecords==endRecord)
				{
					startRecord=startRecord;
					endRecord=totalRecords;
				}
				if(totalRecords>endRecord)
				{
				startRecord=endRecord+1;
				endRecord=endRecord+10;
				}
				String holidayType="";
				String LeaveType="";
				LeaveForm leaveForm1=null;
				  
				  
				  String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode.* " +
						  "from barcode left outer join emp_official_info on emp_official_info.pernr = barcode.accepted_by "
						  + " left outer join material_type on material_type.material_group_id=barcode.MTART "
					+ "where mblnr!='' "+query+") as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						BarcodeForm help =new BarcodeForm();
						help.setEmpName(rs.getString("emp_fullname"));
						help.setmBLNR(rs.getString("MBLNR"));
						help.setmJAHR(rs.getString("MJAHR"));
						help.setlOEKZ(rs.getString("LOEKZ"));
						help.setbLDAT(rs.getString("BLDAT1"));
						help.setbUDAT(rs.getString("BUDAT"));
						help.setwERKS(rs.getString("WERKS"));
						help.setmTART(rs.getString("m_desc"));
						help.seteKGRP(rs.getString("EKGRP"));
						help.setxBLNR(rs.getString("XBLNR"));
						help.seteBELN(rs.getString("EBELN"));
						help.setlIFNR(rs.getString("LIFNR"));
						help.setxSTBW(rs.getString("XSTBW"));
						help.setbWART(rs.getString("BWART"));
						help.setbSART(rs.getString("BSART"));
						help.setdDMBTR(rs.getString("DMBTR"));
						help.setbBKTXT(rs.getString("BKTXT"));
						help.setsENT_DATE(rs.getString("SENT_DATE"));
						help.setsSENT_BY(rs.getString("SENT_BY"));
						help.setdCKET_NO(rs.getString("DOCKET_NO"));
						help.setcNAME(rs.getString("COURIER_NAME"));
						help.setpD_DT(rs.getString("PRECD_DT"));
						help.setName1(rs.getString("NAME1"));
						help.setoORT01(rs.getString("ORT01"));
						help.setAccdate(rs.getString("accdate"));
						barcodelist.add(help);
					}
					request.setAttribute("barcodelist", barcodelist);
				
				
			}
			
			System.out.println("list length="+barcodelist.size());
			
			 if(barcodelist.size()!=0)
				{
				 help1.setTotalRecords1(totalRecords);
				 help1.setStartRecord1(startRecord);
				 help1.setEndRecord1(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					help1.setTotalRecords1(totalRecords);
					help1.setStartRecord1(start);
					help1.setEndRecord1(end);
					
				}
			 if(barcodelist.size()<10)
			 {
				 help1.setTotalRecords1(totalRecords);
				 help1.setStartRecord1(startRecord);
				 help1.setEndRecord1(startRecord+barcodelist.size()-1);
					request.setAttribute("nextButton", "");
					request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton"); 
				 
			 }
			 
			 if(endRecord==totalRecords)
			 {
				 request.setAttribute("nextButton", "");
					request.setAttribute("disableNextButton", "disableNextButton");
			 }
			 request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
		e.printStackTrace();
		}
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setLocationIdList(locationList);
				help1.setLocationLabelList(locationLabelList);
				
				
				
				rs11 = ad.selectQuery("select purchase_group_id from PURCHASE_GROUP");
				ArrayList purchaselList=new ArrayList();
				try {
					while(rs11.next()) {
						purchaselList.add(rs11.getString("purchase_group_id"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setPurchaselList(purchaselList);		

		
		
		return mapping.findForward("barcodeList");
	}
	
	public ActionForward firstpostalreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		BarcodeForm help1 =(BarcodeForm)form;
		EssDao ad = new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		int userID=user.getId();
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=help1.getTotalRecords1();//21
			int startRecord=help1.getStartRecord1();//11
			int endRecord=help1.getEndRecord1();	
			
			

		    String startDate="";
		    String endDate="";
		    String query="";
		    
		    if(help1.getDatefrom()==null)
            {
		    	help1.setDatefrom("");
		    	
            }
		    
		    if(help1.getType()==null)
            {
		    	help1.setType("");
		    	
            }
		    
		    if(help1.getEmployeeno()==null)
		    {
		    	help1.setEmployeeno("");
		    }
		    
		    
            if(help1.getDatefrom().contains("/"))
            {	
		    String b[]=help1.getDatefrom().split("/");
			startDate=b[2]+"-"+b[1]+"-"+b[0];
		  
			String c[]=help1.getDateto().split("/");
			endDate=c[2]+"-"+c[1]+"-"+c[0];
            }
			
		    if(help1.getType().equalsIgnoreCase("CD"))
		    {
		    	query=" and bldat between '"+startDate+"' and '"+endDate+"'  ";
		    }
		    else if(help1.getType().equalsIgnoreCase("RD"))
		    {
		    	query=" and convert(date,accepted_date) between '"+startDate+"' and '"+endDate+"' ";
		    }
		    
		    else
		    {
		    	query=" and accepted_date between '' and '' ";
		    }
		    
		    
		    if(help1.getLocation()==null)
		    {
		    	help1.setLocation("");
		    }
		    if(!help1.getLocation().equalsIgnoreCase(""))
		    {	
		    	query=query+" and barcode.WERKS = '"+help1.getLocation()+"' ";
		    }
		    
		  
		    if(help1.getPur()==null)
		    {
		    	help1.setPur("");
		    }
		    if(!help1.getPur().equalsIgnoreCase(""))
		    {	
		    	query=query+" and EKGRP = '"+help1.getPur()+"' ";
		    }
		    
		   
		    if(!help1.getEmployeeno().equalsIgnoreCase(""))
		    {
		    	query=query+" and accepted_by = '"+help1.getEmployeeno()+"' ";
		    }
		    
		  
			
			
			if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  help1.setTotalRecords1(totalRecords);
			  help1.setStartRecord1(startRecord);
			  help1.setEndRecord1(10);
			  }
			  else{
				  startRecord=1;
				  help1.setTotalRecords1(totalRecords);
				  help1.setStartRecord1(startRecord);
				  help1.setEndRecord1(totalRecords);  
			  }
			 String holidayType="";
				String LeaveType="";
				 ArrayList barcodelist=new ArrayList();
				 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode.* " +
						 "from barcode left outer join emp_official_info on emp_official_info.pernr = barcode.accepted_by "
						 + " left outer join material_type on material_type.material_group_id=barcode.MTART "
					+ "where mblnr!=''"+query+" ) as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) {
						BarcodeForm help =new BarcodeForm();
						help.setEmpName(rs.getString("emp_fullname"));
						help.setmBLNR(rs.getString("MBLNR"));
						help.setmJAHR(rs.getString("MJAHR"));
						help.setlOEKZ(rs.getString("LOEKZ"));
						help.setbLDAT(rs.getString("BLDAT1"));
						help.setbUDAT(rs.getString("BUDAT"));
						help.setwERKS(rs.getString("WERKS"));
						help.setmTART(rs.getString("m_desc"));
						help.seteKGRP(rs.getString("EKGRP"));
						help.setxBLNR(rs.getString("XBLNR"));
						help.seteBELN(rs.getString("EBELN"));
						help.setlIFNR(rs.getString("LIFNR"));
						help.setxSTBW(rs.getString("XSTBW"));
						help.setbWART(rs.getString("BWART"));
						help.setbSART(rs.getString("BSART"));
						help.setdDMBTR(rs.getString("DMBTR"));
						help.setbBKTXT(rs.getString("BKTXT"));
						help.setsENT_DATE(rs.getString("SENT_DATE"));
						help.setsSENT_BY(rs.getString("SENT_BY"));
						help.setdCKET_NO(rs.getString("DOCKET_NO"));
						help.setcNAME(rs.getString("COURIER_NAME"));
						help.setpD_DT(rs.getString("PRECD_DT"));
						help.setName1(rs.getString("NAME1"));
						help.setoORT01(rs.getString("ORT01"));
						help.setAccdate(rs.getString("accdate"));
						barcodelist.add(help);
					}
					request.setAttribute("barcodelist", barcodelist);
				 if(totalRecords>10)
					{
						request.setAttribute("nextButton", "nextButton");
					}
				
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
					
					request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setLocationIdList(locationList);
				help1.setLocationLabelList(locationLabelList);
				
				
				rs11 = ad.selectQuery("select purchase_group_id from PURCHASE_GROUP");
				ArrayList purchaselList=new ArrayList();
				try {
					while(rs11.next()) {
						purchaselList.add(rs11.getString("purchase_group_id"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setPurchaselList(purchaselList);		

		
		return mapping.findForward("barcodeList");
	}
	
	public ActionForward lastpostalreport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		BarcodeForm help1 =(BarcodeForm)form;		
		HttpSession session=request.getSession();
		EssDao ad = new EssDao();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		ArrayList a1=new ArrayList();
		try{
			int totalRecords=help1.getTotalRecords1();//21
			int startRecord=help1.getStartRecord1();//11
			int endRecord=help1.getEndRecord1();	
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 help1.setTotalRecords1(totalRecords);
			 help1.setStartRecord1(startRecord);
			 help1.setEndRecord1(totalRecords);
			 

			    String startDate="";
			    String endDate="";
			    String query="";
			    
			    if(help1.getDatefrom()==null)
	            {
			    	help1.setDatefrom("");
			    	
	            }
			    
			    if(help1.getType()==null)
	            {
			    	help1.setType("");
			    	
	            }
			    
			    if(help1.getEmployeeno()==null)
			    {
			    	help1.setEmployeeno("");
			    }
			    
	            if(help1.getDatefrom().contains("/"))
	            {	
			    String b[]=help1.getDatefrom().split("/");
				startDate=b[2]+"-"+b[1]+"-"+b[0];
			  
				String c[]=help1.getDateto().split("/");
				endDate=c[2]+"-"+c[1]+"-"+c[0];
	            }
				
			    if(help1.getType().equalsIgnoreCase("CD"))
			    {
			    	query=" and bldat between '"+startDate+"' and '"+endDate+"'  ";
			    }
			    else if(help1.getType().equalsIgnoreCase("RD"))
			    {
			    	query=" and convert(date,accepted_date) between '"+startDate+"' and '"+endDate+"' ";
			    }
			    
			    else
			    {
			    	query=" and accepted_date between '' and '' ";
			    }
			    
			    
			    if(help1.getLocation()==null)
			    {
			    	help1.setLocation("");
			    }
			    if(!help1.getLocation().equalsIgnoreCase(""))
			    {	
			    	query=query+" and barcode.WERKS = '"+help1.getLocation()+"' ";
			    }
			    
			  
			    if(help1.getPur()==null)
			    {
			    	help1.setPur("");
			    }
			    if(!help1.getPur().equalsIgnoreCase(""))
			    {	
			    	query=query+" and EKGRP = '"+help1.getPur()+"' ";
			    }
			    
			    
			   
			    
			    
			    if(!help1.getEmployeeno().equalsIgnoreCase(""))
			    {
			    	query=query+" and accepted_by = '"+help1.getEmployeeno()+"' ";
			    }
			    
			    
			  
			 	
				 ArrayList barcodelist=new ArrayList();
				 String sql="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY barcode.ID desc) AS RowNum,convert(nvarchar(10),BLDAT,103) as BLDAT1 , convert(nvarchar(10),BUDAT,103) as BUDAT1,material_type.m_desc,emp_fullname,convert(nvarchar(10),accepted_date,103)+' '+ CONVERT(varchar(5),accepted_date,108) as accdate,barcode.* " +
						 "from barcode left outer join emp_official_info on emp_official_info.pernr = barcode.accepted_by "
						 + " left outer join material_type on material_type.material_group_id=barcode.MTART "
					+ "where mblnr!='' "+query+") as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rs=ad.selectQuery(sql);
					while (rs.next()) 
					{
						BarcodeForm help =new BarcodeForm();
						
						help.setEmpName(rs.getString("emp_fullname"));
						help.setmBLNR(rs.getString("MBLNR"));
						help.setmJAHR(rs.getString("MJAHR"));
						help.setlOEKZ(rs.getString("LOEKZ"));
						help.setbLDAT(rs.getString("BLDAT1"));
						help.setbUDAT(rs.getString("BUDAT"));
						help.setwERKS(rs.getString("WERKS"));
						help.setmTART(rs.getString("m_desc"));
						help.seteKGRP(rs.getString("EKGRP"));
						help.setxBLNR(rs.getString("XBLNR"));
						help.seteBELN(rs.getString("EBELN"));
						help.setlIFNR(rs.getString("LIFNR"));
						help.setxSTBW(rs.getString("XSTBW"));
						help.setbWART(rs.getString("BWART"));
						help.setbSART(rs.getString("BSART"));
						help.setdDMBTR(rs.getString("DMBTR"));
						help.setbBKTXT(rs.getString("BKTXT"));
						help.setsENT_DATE(rs.getString("SENT_DATE"));
						help.setsSENT_BY(rs.getString("SENT_BY"));
						help.setdCKET_NO(rs.getString("DOCKET_NO"));
						help.setcNAME(rs.getString("COURIER_NAME"));
						help.setpD_DT(rs.getString("PRECD_DT"));
						help.setName1(rs.getString("NAME1"));
						help.setoORT01(rs.getString("ORT01"));
						help.setAccdate(rs.getString("accdate"));
						barcodelist.add(help);
					}
					request.setAttribute("barcodelist", barcodelist);
				 request.setAttribute("disableNextButton", "disableNextButton");
					request.setAttribute("previousButton", "previousButton");
					if(a1.size()<10)
					{
						
						request.setAttribute("previousButton", "");
						request.setAttribute("disablePreviousButton", "disablePreviousButton");
					}
					request.setAttribute("displayRecordNo", "displayRecordNo");
					
					
					ResultSet rs11 = ad.selectQuery("select LOCID," +
							"LOCNAME,location_code from location");
							ArrayList locationList=new ArrayList();
							ArrayList locationLabelList=new ArrayList();
							while(rs11.next()) {
								locationList.add(rs11.getString("location_code"));
								locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
							}
							help1.setLocationIdList(locationList);
							help1.setLocationLabelList(locationLabelList);
			 
	}catch (Exception e) {
		e.printStackTrace();
		}
		
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				try {
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				help1.setLocationIdList(locationList);
				help1.setLocationLabelList(locationLabelList);
				
				
						
				rs11 = ad.selectQuery("select purchase_group_id from PURCHASE_GROUP");
								ArrayList purchaselList=new ArrayList();
								try {
									while(rs11.next()) {
										purchaselList.add(rs11.getString("purchase_group_id"));
									}
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								help1.setPurchaselList(purchaselList);		
				
				
				
				
		return mapping.findForward("barcodeList");
	}
		
}
