package com.microlabs.ess.action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.ReportRequestForm;
import com.microlabs.utilities.UserInfo;

public class ReportRequestAction extends DispatchAction{
	public ActionForward displayList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
	  return mapping.findForward("display");
						
	}
	
	public ActionForward showSearchBar(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ReportRequestForm rform=(ReportRequestForm)form;
		String type=rform.getMasterType();
		
		
		if(type.equalsIgnoreCase("material")){
			request.setAttribute("material", "ok");
			
		}
		else if(type.equalsIgnoreCase("service")){
			
			EssDao ad=new EssDao();
			String query="select * from material_group";
			ArrayList s_group=new ArrayList();
			ArrayList s_Label=new ArrayList();
			ResultSet rs=ad.selectQuery(query);
			try{
			while(rs.next()){
				
				s_group.add(rs.getString("material_group_id"));
				s_Label.add(rs.getString("material_group_id")+"-"+rs.getString("material_group_name"));
				
			}
			rform.setS_group(s_group);
			rform.setS_groupLabel(s_Label);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			String plant_query="select LOCNAME,location_code from location";
			ArrayList p_name=new ArrayList();
			ArrayList p_label=new ArrayList();
			ResultSet plant_rs=ad.selectQuery(plant_query);
			try{
				while(plant_rs.next()){
					p_name.add(plant_rs.getString("location_code"));
					p_label.add(plant_rs.getString("location_code")+"-"+plant_rs.getString("LOCNAME"));
				}
				rform.setPlant_name(p_name);
				rform.setPlant_label(p_label);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			
			
			
			request.setAttribute("service", "ok");
		}
		else if(type.equalsIgnoreCase("customer"))
			request.setAttribute("customer", "ok");
		else if(type.equalsIgnoreCase("vendor"))
			request.setAttribute("vendor", "ok");
		
		 return mapping.findForward("display");
	}
	public ActionForward genrateReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		    ReportRequestForm rform=(ReportRequestForm)form;
		    String mtype=rform.getM_materialType();
		    String rdate=rform.getM_rquestDate();
		    
		    if(!rdate.equalsIgnoreCase("")){
				  String b[]=rdate.split("/");
				  rdate=b[2]+"-"+b[1]+"-"+b[0];
				}
		    
		    
		    String flag=request.getParameter("flag");
		    HttpSession session=request.getSession();
		String status=rform.getM_status();
		String requestedby=rform.getM_requestedBy();
		ArrayList all=new ArrayList();
		ArrayList mlist=new ArrayList();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userid=user.getId();
		
		String query;
		int row=0;
		query="select m.request_date,m.approve_type,m.type,m.approve_date,m.requested_by,l.location_code,g.material_group_name from material_code_request as m,location as l,material_group as g  where " +
					"m.location_id=l.LOCID and m.material_group_id=g.material_group_id and m.created_by='"+userid+"'";
		
		if(!mtype.equalsIgnoreCase(""))
			query=query+"and m.type='"+mtype+"'";
		
		if(!rdate.equalsIgnoreCase(""))
			query=query+"and m.request_date='"+rdate+"'";
		if(!status.equalsIgnoreCase(""))
			query=query+"and m.Approve_Type='"+status+"'";
		if(!requestedby.equalsIgnoreCase(""))
			query=query+"and m.requested_by='"+requestedby+"'";
		
		 System.out.println(query);
		 try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setM_rquestDate(reqDate);
				 rf.setM_status(rs.getString("Approve_Type"));
				 rf.setM_materialType(rs.getString("type"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a1[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				 rf.setM_approveDate(appDate);
				 }
				 rf.setM_plant(rs.getString("location_code"));
				 rf.setM_materialGroup(rs.getString("material_group_name"));
				 rf.setM_requestedBy(rs.getString("requested_by"));
				 row++;
				 all.add(rf);
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 rform.setQuery(query);	
		 
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=all.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			rform.setStartRecord(1);
			rform.setEndRecord(i);
			rform.setNext(i);
			if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("displayRecordNo","ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			
			}
			
			//request.setAttribute("serviceMasterList", serviceMasterList);
		 rform.setRow(row);
		 rform.setTotal(row--);
		 request.setAttribute("reportList", serviceMasterList);
		 session.setAttribute("masterList", serviceMasterList);
		
	             
		 return mapping.findForward("displayReport");
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
		int nextval=sform.getNext();
		
		int i=0;
		i=nextval;
		sform.setStartRecord(i+1);
	
		sform.setPrev(nextval);
		int end=nextval+10;
		int start=0;
		int row=0;
	    HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList<ReportRequestForm> alldata=new ArrayList<ReportRequestForm>();
		String query=sform.getQuery();
		System.out.println("cccccccccccccccccc"+query);
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setM_rquestDate(reqDate);
				 rf.setM_status(rs.getString("Approve_Type"));
				 rf.setM_materialType(rs.getString("type"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a1[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				 
				 rf.setM_approveDate(appDate);
				 }
				 rf.setM_plant(rs.getString("location_code"));
				 rf.setM_materialGroup(rs.getString("material_group_name"));
				 row++;
				 alldata.add(rf);
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		//HttpSession session=request.getSession();
		
		ArrayList serviceMasterList=new ArrayList();
		Iterator it=alldata.iterator();
		try{
		while(start<end){
			if(it.hasNext()&&start==nextval){
				serviceMasterList.add(it.next());
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
		sform.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	
		
		sform.setNext(nextval);
		 sform.setRow(row);
		request.setAttribute("reportList", serviceMasterList);
		 session.setAttribute("masterList", serviceMasterList);
		return mapping.findForward("displayReport");
	}
	//-----------------------------------------------------------------------------------------------------------------------------//
	//------------------------------------------Previous Ten Records-----------------------------------------------------------------//
	public ActionForward prevRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
		HttpSession session=request.getSession();
		int prev=sform.getPrev();
		int start=prev-10;
		System.out.println("ppppppppppppppppppppppppppppppppppp"+start);
		//start--;
		//prev--;
		if(start<0)
			start=0;
		sform.setStartRecord(start+1);
		sform.setEndRecord(prev);
		int i=0;
		int row=0;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setM_rquestDate(reqDate);
				 rf.setM_status(rs.getString("Approve_Type"));
				 rf.setM_materialType(rs.getString("type"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a1[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				    rf.setM_approveDate(appDate);
				 }
				 rf.setM_plant(rs.getString("location_code"));
				 rf.setM_materialGroup(rs.getString("material_group_name"));
				 row++;
				 alldata.add(rf);
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		
		ArrayList serviceMasterList=new ArrayList();
		
		
		Iterator it=alldata.iterator();
		while(i<prev){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());
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
		sform.setPrev(prev-10);
		sform.setNext(prev);
		 sform.setRow(row);
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("reportList", serviceMasterList);
		 session.setAttribute("masterList", serviceMasterList);
		return mapping.findForward("displayReport");
	}
	//----------------------------------------------------------------------------------------------------//
	//---------------------------------Last record-------------------------------------------------------//
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		
		
		 ReportRequestForm sform=(ReportRequestForm)form;
		HttpSession session=request.getSession();
		int prev=sform.getPrev();
		int start=0;
		
		prev--;
		
		int i=0;
		int row=0;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setM_rquestDate(reqDate);
				 rf.setM_status(rs.getString("Approve_Type"));
				 rf.setM_materialType(rs.getString("type"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				 rf.setM_approveDate(appDate);
				 }
				 rf.setM_plant(rs.getString("location_code"));
				 rf.setM_materialGroup(rs.getString("material_group_name"));
				 row++;
				 alldata.add(rf);
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		ArrayList serviceMasterList=new ArrayList();
		
		
		ListIterator it=alldata.listIterator();
		int l=0;
		int j=0;
		while(it.hasNext())
		{
			l++;
			it.next();
		}
		j=l;
		while(j>0)
		{
			it.previous();
			j--;
		}
		i=l-10;
		sform.setStartRecord(i+1);
		System.out.println("------------------------------"+i+1);
		sform.setPrev(i);
		while(i<l){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());
				
				i++;
			}
			else if(it.hasNext())
			it.next();
			else
				break;
			start++;
		}
		
			request.setAttribute("previousButton", "ok");
			request.setAttribute("disableNextButton","yes");
		//request.setAttribute("nextButton", "ok");
		
		
		sform.setEndRecord(l);
		 sform.setRow(row);
		//sform.setNext(prev);
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("reportList", serviceMasterList);
		
		 session.setAttribute("masterList", serviceMasterList);
		
		
		
		
		return mapping.findForward("displayReport");
		
	}
	
	//-----------------------------------------------------------------------------------------------------------//
	//-----------------------------First ten Records---------------------------------------------------//
	
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
		HttpSession session=request.getSession();
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		int row=0;
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setM_rquestDate(reqDate);
				 rf.setM_status(rs.getString("Approve_Type"));
				 rf.setM_materialType(rs.getString("type"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a1[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				 rf.setM_approveDate(appDate);
				 }
				 rf.setM_plant(rs.getString("location_code"));
				 rf.setM_materialGroup(rs.getString("material_group_name"));
				 alldata.add(rf);
				 row++;
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		ArrayList serviceMasterList=new ArrayList();
		
		Iterator it=alldata.iterator();
		
		int i=0;
		while(i<10){
			if(it.hasNext()){
			serviceMasterList.add(it.next());
			i++;
			}
			else
				break;
		}
		sform.setStartRecord(1);
		sform.setEndRecord(i);
		 sform.setRow(row);
		sform.setNext(i);
		if(it.hasNext()){
		request.setAttribute("nextButton", "yes");
		request.setAttribute("disablePreviousButton", "ok");
		}
		else{
		request.setAttribute("disablePreviousButton", "ok");
		request.setAttribute("disableNextButton","yes");
		}
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("reportList", serviceMasterList);
		
		//request.setAttribute("serviceMasterList", serviceDetails);
		 session.setAttribute("masterList", serviceMasterList);
		return mapping.findForward("displayReport");
		  
		//return mapping.findForward("displayList");
		
	}
	//------------------------------genrate table on range-------------------------------------------------------------------------//
	public ActionForward selectRows(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setM_rquestDate(reqDate);
				 rf.setM_status(rs.getString("Approve_Type"));
				 rf.setM_materialType(rs.getString("type"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a1[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				 rf.setM_approveDate(appDate);
				 }
				 else
					 appDate="";
				 rf.setM_plant(rs.getString("location_code"));
				 rf.setM_materialGroup(rs.getString("material_group_name"));
				 
				 alldata.add(rf);
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 int range=sform.getRange();
		 ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=alldata.iterator();
			
			int i=0;
			while(i<range){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			request.setAttribute("reportList", serviceMasterList);
		
		
		return mapping.findForward("displayReport");
	}
	//-------------------------------------------------------SERVICE REPORT--------------------------------------------------------------------//
	public ActionForward genrateServiceReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 HttpSession session=request.getSession();
		 ReportRequestForm rform=(ReportRequestForm)form;
		String servicecatagory=rform.getS_serviceCatagory();
		String servicegroup=rform.getS_serviceGroup();
		String plant=rform.getS_plant();
		String status=rform.getS_status();
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userid=user.getId();
	
		
		String query="select * from service_master where created_by="+userid+"";
		
		if(!servicecatagory.equalsIgnoreCase(""))
            query=query+" and service_catagory='"+servicecatagory+"'";
		if(!servicegroup.equalsIgnoreCase(""))
			query=query+" and service_group='"+servicegroup+"'";
		if(!plant.equalsIgnoreCase(""))
			query=query+" and plant_code='"+plant+"'";
		String appDate="";
		ArrayList s_report=new ArrayList();
		ResultSet rs=ad.selectQuery(query);
		System.out.println(query);
		int row=0;
		try{
		while(rs.next()){
			ReportRequestForm rf= new ReportRequestForm();
			
			String reqDate=rs.getString("request_date");
			if(reqDate!=null)
			{
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
		}else{
			reqDate="";
		}
			rf.setS_requestDate(reqDate);
			rf.setS_plant(rs.getString("plant_code"));
			rf.setS_requestedBy(rs.getString("requested_by"));
			rf.setS_serviceCatagory(rs.getString("service_catagory"));
			rf.setS_serviceDescription(rs.getString("service_description"));
			rf.setS_serviceGroup(rs.getString("service_group"));
			rf.setS_status(rs.getString("app_satus"));
			rf.setS_requestedBy(rs.getString("requested_by"));
			appDate=rs.getString("approve_date");
			
			if(appDate!=null){
			String a1[]=appDate.split(" ");
			appDate=a1[0];
			String b1[]=appDate.split("-");
			appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
			rf.setS_approveDate(appDate);
			}
			else{
				appDate="";
			}
			//rf.setS_approveDate(appDate);
			s_report.add(rf);
			row++;
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 rform.setQuery(query);	
		 
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=s_report.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			rform.setStartRecord(1);
			rform.setEndRecord(i);
			rform.setNext(i);
			if(i>9){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("displayRecordNo","ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			
			//request.setAttribute("serviceMasterList", serviceMasterList);
		 rform.setRow(row);
		
		request.setAttribute("serviceList", serviceMasterList);
		session.setAttribute("slist", serviceMasterList);
		
		
		return mapping.findForward("serviceReport");
	
	}
	//======================================DYNAMIC COL SELECTION FOR SERVIVE REPORT===================================================================
	
	//==========================================================NEXT SERVIVE RECORDS=========================================================//
	public ActionForward nextServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		 HttpSession session=request.getSession();
		 ReportRequestForm sform=(ReportRequestForm)form;
			int nextval=sform.getNext();
			sform.setStartRecord(nextval);
			sform.setPrev(nextval);
			int end=nextval+10;
			int start=1;
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			System.out.println("cccccccccccccccccc"+query);
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						
					 rf.setS_requestDate(reqDate);
						rf.setS_plant(rs.getString("plant_code"));
						rf.setS_requestedBy(rs.getString("requested_by"));
						rf.setS_serviceCatagory(rs.getString("service_catagory"));
						rf.setS_serviceDescription(rs.getString("service_description"));
						rf.setS_serviceGroup(rs.getString("service_group"));
						rf.setS_status(rs.getString("app_satus"));
						rf.setS_requestedBy(rs.getString("requested_by"));
						String appDate=rs.getString("");
						if(appDate!=null){
						String a1[]=appDate.split(" ");
						appDate=a1[0];
						String b1[]=appDate.split("-");
						appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						rf.setS_approveDate(appDate);
						}
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			//HttpSession session=request.getSession();
			
			ArrayList serviceMasterList=new ArrayList();
			Iterator it=alldata.iterator();
			try{
			while(start<end){
				if(it.hasNext()&&start==nextval){
					serviceMasterList.add(it.next());
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
			sform.setEndRecord(nextval);
			if(it.hasNext()){
				request.setAttribute("nextButton", "yes");
			request.setAttribute("previousButton", "ok");
			}
			else{
				request.setAttribute("disableNextButton","yes");
			request.setAttribute("previousButton", "ok");
			}
			request.setAttribute("displayRecordNo","ok");	
			
			sform.setNext(nextval);
			request.setAttribute("serviceList", serviceMasterList);
			session.setAttribute("slist", serviceMasterList);
		return mapping.findForward("serviceReport");
		
	}
	//========================================================PREVIOUS SERVICE RECORDS=============================================
	public ActionForward prevServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
			HttpSession session=request.getSession();
			int prev=sform.getPrev();
			int start=prev-10;
			System.out.println("ppppppppppppppppppppppppppppppppppp"+start);
			//start--;
			//prev--;
			if(start<0)
				start=0;
			sform.setStartRecord(start+1);
			sform.setEndRecord(prev);
			int i=0;
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setS_requestDate(reqDate);
						rf.setS_plant(rs.getString("plant_code"));
						rf.setS_requestedBy(rs.getString("requested_by"));
						rf.setS_serviceCatagory(rs.getString("service_catagory"));
						rf.setS_serviceDescription(rs.getString("service_description"));
						rf.setS_serviceGroup(rs.getString("service_group"));
						rf.setS_status(rs.getString("app_satus"));
						rf.setS_requestedBy(rs.getString("requested_by"));
						String appDate=rs.getString("approve_date");
						if(appDate!=null){
						String a1[]=appDate.split(" ");
						appDate=a1[0];
						String b1[]=appDate.split("-");
						appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						rf.setS_approveDate(appDate);
						}
					 
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			
			Iterator it=alldata.iterator();
			while(i<prev){
				if(it.hasNext()&&i==start){
					serviceMasterList.add(it.next());
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
			sform.setPrev(prev-10);
			sform.setNext(prev);
			request.setAttribute("displayRecordNo","ok");
			request.setAttribute("serviceList", serviceMasterList);
			session.setAttribute("slist", serviceMasterList);
		
		return mapping.findForward("serviceReport");
		
	}
	//=====================================================================LAST 10 RECORDS==============================================
	public ActionForward lastServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		 ReportRequestForm sform=(ReportRequestForm)form;
		HttpSession session=request.getSession();
		int prev=sform.getPrev();
		int start=0;
		
		prev--;
		
		int i=0;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				    rf.setS_requestDate(reqDate);
					rf.setS_plant(rs.getString("plant_code"));
					rf.setS_requestedBy(rs.getString("requested_by"));
					rf.setS_serviceCatagory(rs.getString("service_catagory"));
					rf.setS_serviceDescription(rs.getString("service_description"));
					rf.setS_serviceGroup(rs.getString("service_group"));
					rf.setS_status(rs.getString("app_satus"));
					rf.setS_requestedBy(rs.getString("requested_by"));
					 String appDate=rs.getString("approve_date");
					 if(appDate!=null){
						String a1[]=appDate.split(" ");
						appDate=a1[0];
						String b1[]=appDate.split("-");
						appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					rf.setS_approveDate(appDate);
					 }
				 alldata.add(rf);
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		
		ArrayList serviceMasterList=new ArrayList();
		
		
		ListIterator it=alldata.listIterator();
		int l=0;
		int j=0;
		while(it.hasNext())
		{
			l++;
			it.next();
		}
		j=l;
		while(j>0)
		{
			it.previous();
			j--;
		}
		i=l-10;
		if(l<10)
			i=0;
		sform.setStartRecord(i+1);
		sform.setPrev(i);
		while(i<l){
			if(it.hasNext()&&i==start){
				serviceMasterList.add(it.next());
				
				i++;
			}
			else if(it.hasNext())
			it.next();
			else
				break;
			start++;
		}
		
			request.setAttribute("previousButton", "ok");
			request.setAttribute("disableNextButton","yes");
		//request.setAttribute("nextButton", "ok");
		
		
		sform.setEndRecord(l);
	
		//sform.setNext(prev);
		request.setAttribute("displayRecordNo","ok");
		request.setAttribute("serviceList", serviceMasterList);
		session.setAttribute("slist", serviceMasterList);
		
		
		
		return mapping.findForward("serviceReport");
		
	}
	//===================================================================FIRST 10 SERVICE RECORDS====================================================
	public ActionForward firstServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
			HttpSession session=request.getSession();
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setS_requestDate(reqDate);
						rf.setS_plant(rs.getString("plant_code"));
						rf.setS_requestedBy(rs.getString("requested_by"));
						rf.setS_serviceCatagory(rs.getString("service_catagory"));
						rf.setS_serviceDescription(rs.getString("service_description"));
						rf.setS_serviceGroup(rs.getString("service_group"));
						rf.setS_status(rs.getString("app_satus"));
						rf.setS_requestedBy(rs.getString("requeted_by"));
						 String appDate=rs.getString("approve_date");
						 if(appDate!=null){
							String a1[]=appDate.split(" ");
							appDate=a1[0];
							String b1[]=appDate.split("-");
							appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						rf.setS_approveDate(appDate);
						 }
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=alldata.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			sform.setStartRecord(1);
			sform.setEndRecord(i++);
			sform.setNext(i);
			if(i>9){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			request.setAttribute("displayRecordNo","ok");
			request.setAttribute("serviceList", serviceMasterList);
			
			//request.setAttribute("serviceMasterList", serviceDetails);
			session.setAttribute("slist", serviceMasterList);
			
		
		
		
		return mapping.findForward("serviceReport");
	}
	//===================================CUST GENRATE=======================================================================
	public ActionForward genrateCustomerReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm rform=(ReportRequestForm)form;
		 HttpSession session=request.getSession();
		String customergroup=rform.getC_customerGroup();
		String appstatus=rform.getC_status();
		String requestedby=rform.getC_requestedBy();
		String approvedate=rform.getC_approveDate();
	    
	    if(!approvedate.equalsIgnoreCase("")){
			  String b[]=approvedate.split("/");
			  approvedate=b[2]+"-"+b[1]+"-"+b[0];
			}
		
	    UserInfo user=(UserInfo)session.getAttribute("user");
		int userid=user.getId();
		EssDao ad=new EssDao();
		int row=0;
		String query="select c.approve_status,c.CREATED_BY,c.customer_group,c.request_date,c.name,c.approve_date,t.country_name,c.CITY from customer_master_m as c," +
				"country as t where c.country_id=t.LAND1  and c.created_by='"+userid+"' and c.approve_status='Pending' ";
		
		
		if(!customergroup.equalsIgnoreCase(""))
			query=query+"and c.customer_group='"+customergroup+"'";
		if(!appstatus.equalsIgnoreCase(""))
			query=query+"and c.Approve_Status='"+appstatus+"'";
		if(!requestedby.equalsIgnoreCase(""))
			query=query+"and c.NAME='"+requestedby+"'";
		if(!approvedate.equalsIgnoreCase(""))
			query=query+"and c.approve_date='"+approvedate+"'";
		
		
		
		ArrayList alldata=new ArrayList();
		ResultSet rs=ad.selectQuery(query);
		try{
		while(rs.next()){
			 ReportRequestForm rf=new ReportRequestForm();
			 rf.setC_country(rs.getString("LANDX"));
			 rf.setC_customerGroup(rs.getString("customer_group"));
			 String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
			 rf.setC_requestDate(reqDate);
			 rf.setC_status(rs.getString("approve_status"));
			 rf.setC_name(rs.getString("name"));
			 rf.setC_location(rs.getString("CITY"));
			 rf.setC_requestedBy(rs.getString("created_by"));
			 String appDate=rs.getString("approve_date");
			 if(appDate!=null){
				String a1[]=appDate.split(" ");
				appDate=a1[0];
				String b1[]=appDate.split("-");
				appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
			 rf.setC_approveDate(appDate);
			 }
			 alldata.add(rf);
			 row++;
			 
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 rform.setQuery(query);	
		 
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=alldata.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			rform.setStartRecord(1);
			rform.setEndRecord(i);
			rform.setNext(i);
			if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("displayRecordNo","ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			
			//request.setAttribute("serviceMasterList", serviceMasterList);
		 rform.setRow(row);
		session.setAttribute("clist", serviceMasterList);
		request.setAttribute("customerList", serviceMasterList);
		
		
		
		return mapping.findForward("customerReport");
	}
	//=========================================================NEXT CUST===========================================
	public ActionForward nextCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ReportRequestForm sform=(ReportRequestForm)form;
		 HttpSession session=request.getSession();
		int nextval=sform.getNext();
		nextval=nextval+1;
		sform.setStartRecord(nextval);
		sform.setPrev(nextval);
		int end=nextval+10;
		int start=1;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		System.out.println("cccccccccccccccccc"+query);
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 rf.setC_country(rs.getString("country_name"));
				 rf.setC_customerGroup(rs.getString("customer_group"));
				 rf.setC_location(rs.getString("CITY"));
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setC_requestDate(reqDate);
				 rf.setC_status(rs.getString("approve_status"));
				 rf.setC_name(rs.getString("name"));
				 rf.setC_requestedBy(rs.getString("created_by"));
				 alldata.add(rf);
					
				
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		//HttpSession session=request.getSession();
		
		ArrayList serviceMasterList=new ArrayList();
		Iterator it=alldata.iterator();
		try{
		while(start<end){
			if(it.hasNext()&&start==nextval){
				serviceMasterList.add(it.next());
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
		nextval=nextval-1;
		sform.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	
		
		sform.setNext(nextval);
		session.setAttribute("clist", serviceMasterList);
		request.setAttribute("customerList", serviceMasterList);
		
		
		return mapping.findForward("customerReport");
		
	}
	//========================================PREV CUST===========================================================================
	public ActionForward prevCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
			HttpSession session=request.getSession();
			int prev=sform.getPrev();
			prev=prev-1;
			int start=prev-9;
			System.out.println("ppppppppppppppppppppppppppppppppppp"+start);
			//start--;
			//prev--;
			if(start<0)
				start=1;
			
			sform.setStartRecord(start);
			sform.setEndRecord(prev);
			prev=sform.getPrev();
			prev=prev-1;
			 start=prev-9;
			if(start<0)
				start=0;
			int i=0;
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 rf.setC_country(rs.getString("country_name"));
					 rf.setC_customerGroup(rs.getString("customer_group"));
					 rf.setC_location(rs.getString("CITY"));
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setC_requestDate(reqDate);
					 rf.setC_status(rs.getString("approve_status"));
					 rf.setC_name(rs.getString("name"));
					 rf.setC_requestedBy(rs.getString("created_by"));
					
					alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			
			Iterator it=alldata.iterator();
			while(i<prev){
				if(it.hasNext()&&i==start){
					serviceMasterList.add(it.next());
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
			sform.setPrev(prev-9);
			sform.setNext(prev);
			request.setAttribute("displayRecordNo","ok");
			request.setAttribute("customerList", serviceMasterList);
			session.setAttribute("clist", serviceMasterList);
		
		
		
		
		
		return mapping.findForward("customerReport");
	}
	//========================================FRST CUST=================================================================
	public ActionForward firstCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
			HttpSession session=request.getSession();
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 rf.setC_country(rs.getString("country_name"));
					 rf.setC_customerGroup(rs.getString("customer_group"));
					 rf.setC_location(rs.getString("CITY"));
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setC_requestDate(reqDate);
					 rf.setC_status(rs.getString("approve_status"));
					 rf.setC_name(rs.getString("name"));
					 rf.setC_requestedBy(rs.getString("created_by"));
					 
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=alldata.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			sform.setStartRecord(1);
			sform.setEndRecord(i);
			sform.setNext(i);
			if(i>9){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			request.setAttribute("displayRecordNo","ok");
			request.setAttribute("customerList", serviceMasterList);
			session.setAttribute("clist", serviceMasterList);
			//request.setAttribute("serviceMasterList", serviceDetails);
		
		
		return mapping.findForward("customerReport");
	}
	//====================================LAST CUST===============================================================
	public ActionForward lastCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 ReportRequestForm sform=(ReportRequestForm)form;
			
			int prev=sform.getPrev();
			int start=0;
			
			prev--;
			
			int i=0;
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 rf.setC_country(rs.getString("country_name"));
					 rf.setC_customerGroup(rs.getString("customer_group"));
					 rf.setC_location(rs.getString("CITY"));
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setC_requestDate(reqDate);
					 rf.setC_status(rs.getString("approve_status"));
					 rf.setC_name(rs.getString("name"));
					 rf.setC_requestedBy(rs.getString("created_by"));
					 
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			ArrayList serviceMasterList=new ArrayList();
			
			
			ListIterator it=alldata.listIterator();
			int l=0;
			int j=0;
			while(it.hasNext())
			{
				l++;
				it.next();
			}
			j=l;
			while(j>0)
			{
				it.previous();
				j--;
			}
			i=l-10;
			sform.setStartRecord(i);
			sform.setPrev(i);
			while(i<l){
				if(it.hasNext()&&i==start){
					serviceMasterList.add(it.next());
					
					i++;
				}
				else if(it.hasNext())
				it.next();
				else
					break;
				start++;
			}
			
				request.setAttribute("previousButton", "ok");
				request.setAttribute("disableNextButton","yes");
			//request.setAttribute("nextButton", "ok");
			
			
			sform.setEndRecord(l);
		
			//sform.setNext(prev);
			request.setAttribute("displayRecordNo","ok");
			request.setAttribute("customerList", serviceMasterList);
			session.setAttribute("clist", serviceMasterList);
		
		
		
		return mapping.findForward("customerReport");
	}
	public ActionForward genrateVendorReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
		 HttpSession session=request.getSession();
		String vendortype=sform.getV_vendorType();
		String status=sform.getV_status();
		String requestedby=sform.getV_requestedBy();
		String approvedate=sform.getV_approveDate();
		 if(!approvedate.equalsIgnoreCase("")){
			  String b[]=approvedate.split("/");
			  approvedate=b[2]+"-"+b[1]+"-"+b[0];
			}
		
		int row=0;
		EssDao ad=new EssDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userid=user.getId();
		String query="select v.request_date,v.name,l.LOCNAME,c.country_name,v.type,v.approve_status,v.approve_date from vendor_master_m as v,location as l" +
		",country as c where l.LOCID=v.LOCATION_ID  and v.COUNTRY_ID=c.LAND1 and v.created_by='"+userid+"'";
		if(!vendortype.equalsIgnoreCase(""))
			query=query+"and v.type='"+vendortype+"'";
		if(!status.equalsIgnoreCase(""))
			query=query+"and v.approve_status='"+status+"'";
		if(!requestedby.equalsIgnoreCase(""))
			query=query+"and v.approve_status='"+requestedby+"'";
		if(!approvedate.equalsIgnoreCase(""))
			query=query+"and v.approve_date='"+approvedate+"'";
		
		
		ArrayList alldata=new ArrayList();
		ResultSet rs=ad.selectQuery(query);
		try{
		while(rs.next()){
			 ReportRequestForm rf=new ReportRequestForm();
			 String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
			 rf.setV_requestDate(reqDate);
			 rf.setV_name(rs.getString("name"));
			 rf.setV_location(rs.getString("LOCNAME"));
			 rf.setV_country(rs.getString("LANDX"));
			 rf.setV_vendorType(rs.getString("type"));
			 rf.setV_status(rs.getString("approve_status"));
			 String appDate=rs.getString("approve_date");
			 
			 if(appDate!=null){
				String a1[]=appDate.split(" ");
				appDate=a1[0];
				String b1[]=appDate.split("-");
				appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
			 rf.setV_approveDate(appDate);
			 }
			 else
				 rf.setV_approveDate("");
			 alldata.add(rf);
			 row++;
			 
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 sform.setQuery(query);	
		 
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=alldata.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			sform.setStartRecord(1);
			sform.setEndRecord(i);
			sform.setNext(i);
			if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("displayRecordNo","ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			
			//request.setAttribute("serviceMasterList", serviceMasterList);
		 sform.setRow(row);
		session.setAttribute("vlist", serviceMasterList);
		request.setAttribute("vendorList", serviceMasterList);
		
		
		
		
		
		
		
		return mapping.findForward("vendorReport");
		
	}
	public ActionForward nextVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ReportRequestForm sform=(ReportRequestForm)form;
		 HttpSession session=request.getSession();
		int nextval=sform.getNext();
		int row=0;
		int i=0;
		i=nextval;
		sform.setStartRecord(i+1);
		
		sform.setPrev(nextval);
		int end=nextval+10;
		int start=1;
		EssDao ad=new EssDao();
		//sform.setEndRecord(end);
		ArrayList alldata=new ArrayList();
		String query=sform.getQuery();
		System.out.println("cccccccccccccccccc"+query);
		try{
			 ResultSet rs=ad.selectQuery(query);
			 while(rs.next()){
				 ReportRequestForm rf=new ReportRequestForm();
				 String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
				 rf.setV_requestDate(reqDate);
				 rf.setV_name(rs.getString("name"));
				 rf.setV_location(rs.getString("LOCNAME"));
				 rf.setV_country(rs.getString("country_name"));
				 rf.setV_vendorType(rs.getString("type"));
				 rf.setV_status(rs.getString("approve_status"));
				 String appDate=rs.getString("approve_date");
				 if(appDate!=null){
					String a1[]=appDate.split(" ");
					appDate=a1[0];
					String b1[]=appDate.split("-");
					appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				 rf.setV_approveDate(appDate);
				 }
				 else
					 rf.setV_approveDate("");
				 alldata.add(rf);
				 row++;
				 
			
				 
				
				
			 }
			 
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		//HttpSession session=request.getSession();
		
		ArrayList serviceMasterList=new ArrayList();
		Iterator it=alldata.iterator();
		try{
		while(start<end){
			if(it.hasNext()&&start==nextval){
				serviceMasterList.add(it.next());
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
		sform.setEndRecord(nextval);
		if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
		request.setAttribute("previousButton", "ok");
		}
		else{
			request.setAttribute("disableNextButton","yes");
		request.setAttribute("previousButton", "ok");
		}
		request.setAttribute("displayRecordNo","ok");	
		 sform.setRow(row);
		sform.setNext(nextval);
		session.setAttribute("vlist", serviceMasterList);
		request.setAttribute("vendorList", serviceMasterList);
		
		
		return mapping.findForward("vendorReport");
	}
	public ActionForward prevVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		 ReportRequestForm sform=(ReportRequestForm)form;
		 int row=0;
			HttpSession session=request.getSession();
			int prev=sform.getPrev();
			int start=prev-10;
			System.out.println("ppppppppppppppppppppppppppppppppppp"+start);
			//start--;
			//prev--;
			if(start<0)
				start=0;
			sform.setStartRecord(start+1);
			sform.setEndRecord(prev);
			int i=0;
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setV_requestDate(reqDate);
					 rf.setV_name(rs.getString("name"));
					 rf.setV_location(rs.getString("LOCNAME"));
					 rf.setV_country(rs.getString("country_name"));
					 rf.setV_vendorType(rs.getString("type"));
					 rf.setV_status(rs.getString("approve_status"));
					 String appDate=rs.getString("approve_date");
					 if(appDate!=null){
						String a1[]=appDate.split(" ");
						appDate=a1[0];
						String b1[]=appDate.split("-");
						appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					 rf.setV_approveDate(appDate);
					 }
					 else
						 rf.setV_approveDate("");
					 alldata.add(rf);
					 row++;
					 
				
					 
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			
			ArrayList serviceMasterList=new ArrayList();
			
			
			Iterator it=alldata.iterator();
			while(i<prev){
				if(it.hasNext()&&i==start){
					serviceMasterList.add(it.next());
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
			sform.setPrev(prev-10);
			sform.setNext(prev);
			 sform.setRow(row);
			request.setAttribute("displayRecordNo","ok");
			session.setAttribute("vlist", serviceMasterList);
			request.setAttribute("vendorList", serviceMasterList);
		
		
		
		
		return mapping.findForward("vendorReport");
	}
	public ActionForward firstVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
			HttpSession session=request.getSession();
			EssDao ad=new EssDao();
			int row=0;
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setV_requestDate(reqDate);
					 rf.setV_name(rs.getString("name"));
					 rf.setV_location(rs.getString("LOCNAME"));
					 rf.setV_country(rs.getString("country_name"));
					 rf.setV_vendorType(rs.getString("type"));
					 rf.setV_status(rs.getString("approve_status"));
					 String appDate=rs.getString("approve_date");
					 if(appDate!=null){
						String a1[]=appDate.split(" ");
						appDate=a1[0];
						String b1[]=appDate.split("-");
						appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					 rf.setV_approveDate(appDate);
					 }
					 else
						 rf.setV_approveDate("");
					
					 row++;
					 
				
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			ArrayList serviceMasterList=new ArrayList();
			
			Iterator it=alldata.iterator();
			
			int i=0;
			while(i<10){
				if(it.hasNext()){
				serviceMasterList.add(it.next());
				i++;
				}
				else
					break;
			}
			sform.setStartRecord(1);
			sform.setEndRecord(i);
			sform.setNext(i);
			if(it.hasNext()){
			request.setAttribute("nextButton", "yes");
			request.setAttribute("disablePreviousButton", "ok");
			}
			else{
			request.setAttribute("disablePreviousButton", "ok");
			request.setAttribute("disableNextButton","yes");
			}
			request.setAttribute("displayRecordNo","ok");
			session.setAttribute("vlist", serviceMasterList);
			request.setAttribute("vendorList", serviceMasterList);
			 sform.setRow(row);
		
		return mapping.findForward("vendorReport");
		
	}
	public ActionForward lastVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 ReportRequestForm sform=(ReportRequestForm)form;
		 HttpSession session=request.getSession();
			int prev=sform.getPrev();
			int start=0;
			int row=0;
			prev--;
			
			int i=0;
			EssDao ad=new EssDao();
			//sform.setEndRecord(end);
			ArrayList alldata=new ArrayList();
			String query=sform.getQuery();
			try{
				 ResultSet rs=ad.selectQuery(query);
				 while(rs.next()){
					 ReportRequestForm rf=new ReportRequestForm();
					 String reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
					 rf.setV_requestDate(reqDate);
					 rf.setV_name(rs.getString("name"));
					 rf.setV_location(rs.getString("LOCNAME"));
					 rf.setV_country(rs.getString("country_name"));
					 rf.setV_vendorType(rs.getString("type"));
					 rf.setV_status(rs.getString("approve_status"));
					 String appDate=rs.getString("approve_date");
					 if(appDate!=null){
						String a1[]=appDate.split(" ");
						appDate=a1[0];
						String b1[]=appDate.split("-");
						appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
					 rf.setV_approveDate(appDate);
					 }
					 else
						 rf.setV_approveDate("");
					
					 row++;
					 
				
					 alldata.add(rf);
				 }
				 
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
			
			ArrayList serviceMasterList=new ArrayList();
			
			
			ListIterator it=alldata.listIterator();
			int l=0;
			int j=0;
			while(it.hasNext())
			{
				l++;
				it.next();
			}
			j=l;
			while(j>0)
			{
				it.previous();
				j--;
			}
			i=l-10;
			sform.setStartRecord(i);
			sform.setPrev(i);
			while(i<l){
				if(it.hasNext()&&i==start){
					serviceMasterList.add(it.next());
					
					i++;
				}
				else if(it.hasNext())
				it.next();
				else
					break;
				start++;
			}
			
				request.setAttribute("previousButton", "ok");
				request.setAttribute("disableNextButton","yes");
			//request.setAttribute("nextButton", "ok");
			
			
			sform.setEndRecord(l);
		
			//sform.setNext(prev);
			request.setAttribute("displayRecordNo","ok");
			session.setAttribute("vlist", serviceMasterList);
			request.setAttribute("vendorList", serviceMasterList);
			 sform.setRow(row);
		
		
		return mapping.findForward("vendorReport");
	}
	public ActionForward genrateExcelMasterRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toExcel=new ArrayList();
		 toExcel=(ArrayList)session.getAttribute("masterList");
		  HSSFWorkbook l_workBook_out = new HSSFWorkbook(); 
		  HSSFSheet sheet = l_workBook_out.createSheet("Report");   
		  
		  //--------column heading--------------------------//
		  // rf.setM_rquestDate(rs.getString("request_date"));
			// rf.setM_status(rs.getString("Approve_Type"));
			// rf.setM_materialType(rs.getString("type"));
			// rf.setM_approveDate(rs.getString("approve_date"));
			// rf.setM_plant(rs.getString("location_code"));
			// rf.setM_materialGroup(rs.getString("material_group_name"));
		  
		  int rowNum=0;
		HSSFRow headerRow = sheet.createRow((short) rowNum); 
		  headerRow.createCell((short) 0).setCellValue(new HSSFRichTextString("REQUEST DATE")); 
		  headerRow.createCell((short) 1).setCellValue(new HSSFRichTextString("APPROVE STATUS")); 
		  headerRow.createCell((short) 2).setCellValue(new HSSFRichTextString("MATERIAL TYPE")); 
		  headerRow.createCell((short) 3).setCellValue(new HSSFRichTextString("APPROVE DATE")); 
		  headerRow.createCell((short) 4).setCellValue(new HSSFRichTextString("PLANT")); 
		  headerRow.createCell((short) 5).setCellValue(new HSSFRichTextString("MATERIAL GROUP")); 
		  
		  Iterator itr=toExcel.iterator();
		  
		  while(itr.hasNext()){
			  ReportRequestForm rr=(ReportRequestForm)itr.next();
			  
			  HSSFRow row1 = sheet.createRow((short) ++rowNum); 

			  HSSFCell nameCell = row1.createCell((short) 0); 
			  nameCell.setCellValue(new HSSFRichTextString(rr.getM_rquestDate())); 

			  HSSFCell addressCell = row1.createCell((short) 1); 
			  addressCell.setCellValue(new HSSFRichTextString(rr.getM_status())); 

			  HSSFCell cityCell = row1.createCell((short) 2); 
			  cityCell.setCellValue(new HSSFRichTextString(rr.getM_materialType())); 
			  
			  HSSFCell appdate = row1.createCell((short) 3); 
			  appdate.setCellValue(new HSSFRichTextString(rr.getM_approveDate())); 
			  
			  HSSFCell plant = row1.createCell((short) 4); 
			  plant.setCellValue(new HSSFRichTextString(rr.getM_plant())); 
			  
			  HSSFCell materialGroup = row1.createCell((short) 5); 
			  materialGroup.setCellValue(new HSSFRichTextString(rr.getM_materialGroup())); 


			  
		  }
		  //------------genrate outpuut----------------------//
		  
		  
		   File l_str_file_out = new File("C:/results.xls"); //Give the location suitable to your requirement   
		    FileOutputStream fileOut;   
		   try {   
		     fileOut = new FileOutputStream(l_str_file_out);   
		     l_workBook_out.write(fileOut);   
		     fileOut.close();   
		    } 
		   	catch (Exception e) {   
		    e.printStackTrace();
		    }
		   	
		   	//---------------------Out put excel file-----------------//
		   	
		    response.setHeader( "Pragma", "public" );  

               response.setContentType( "application/vnd.ms-excel" );
                response.setHeader("Content-disposition", "attachment;filename="+l_str_file_out+"" );
               // ServletOutputStream stream = null;

               // BufferedInputStream buf =null;
                //stream = response.getOutputStream();
                int length=0; 
                //FileInputStream input = new FileInputStream(word_rep);
                try{
                ServletOutputStream sos = response.getOutputStream();   
                byte[] bbuf = new byte[4096];   

                DataInputStream in = new DataInputStream(new FileInputStream(l_str_file_out)); 
                
                while ((in != null) && ((length = in.read(bbuf)) != -1))   
                {   
                    sos.write(bbuf,0,length);   
                } 
                
                
                
                in.close();   
                sos.flush();   
                response.flushBuffer();   
                sos.close(); 
                }
                catch(Exception e){
                	e.printStackTrace();
                }

		
		
		return null;
	}
	public ActionForward genratePdfMasterRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toPdf=new ArrayList();
		 toPdf=(ArrayList)session.getAttribute("masterList");
		 Document document = new Document();
		 File pdffile=new File("c:/pdf_genrated.pdf");
		// rf.setM_rquestDate(rs.getString("request_date"));
			// rf.setM_status(rs.getString("Approve_Type"));
			// rf.setM_materialType(rs.getString("type"));
			// rf.setM_approveDate(rs.getString("approve_date"));
			// rf.setM_plant(rs.getString("location_code"));
			// rf.setM_materialGroup(rs.getString("material_group_name"));
         try{
        	 
        
		 PdfWriter.getInstance(document,
                 new FileOutputStream(pdffile));
         document.open();

         //
         // Creates a table with four column. The first rows
         // will have cell 1 to cell 4.
         //
         PdfPTable table = new PdfPTable(6);
         PdfPCell cell1 = new PdfPCell(new Phrase("REQUEST DATE"));
         PdfPCell cell2 = new PdfPCell(new Phrase("APPROVE TYPE"));
         PdfPCell cell3 = new PdfPCell(new Phrase("TYPE"));
         PdfPCell cell4 = new PdfPCell(new Phrase("APPROVE DATE"));
         PdfPCell cell5 = new PdfPCell(new Phrase("PLANT CODE"));
         PdfPCell cell6 = new PdfPCell(new Phrase("MATERIAL GROUP"));
         
         cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell1.setBorderWidth(1f);
         cell1.setBorderColor(BaseColor.WHITE);
         
         cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell2.setBorderWidth(1f);
         cell2.setBorderColor(BaseColor.WHITE);
         
         cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell3.setBorderWidth(1f);
         cell3.setBorderColor(BaseColor.WHITE);

         cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell4.setBorderWidth(1f);
         cell4.setBorderColor(BaseColor.WHITE);
         
         cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell5.setBorderWidth(1f);
         cell5.setBorderColor(BaseColor.WHITE);

         cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
         cell6.setBorderWidth(1f);
         cell6.setBorderColor(BaseColor.WHITE);


         table.addCell(cell1);
         table.addCell(cell2);
         table.addCell(cell3);
         table.addCell(cell4);
         table.addCell(cell5);
         table.addCell(cell6);
         table.completeRow();
      
         
         Iterator itr=toPdf.iterator();
         
         while(itr.hasNext()){
			  ReportRequestForm rr=(ReportRequestForm)itr.next();
			   table.addCell(new PdfPCell(new Phrase(rr.getM_rquestDate())));
		         table.addCell(new PdfPCell(new Phrase(rr.getM_status())));
		         table.addCell(new PdfPCell(new Phrase(rr.getM_materialType())));
		         table.addCell(new PdfPCell(new Phrase(rr.getM_approveDate())));
		         table.addCell(new PdfPCell(new Phrase(rr.getM_plant())));
		         table.addCell(new PdfPCell(new Phrase(rr.getM_materialGroup())));
		         table.completeRow();
         }
         //
         // Creates another row that only have to columns.
         // The cell 5 and cell 6 width will span two columns
         // in width.
         //
        
         table.completeRow();

         //
         // Adds table to the document
         //
         document.add(table);
         
         
         
        
         }catch (Exception e) {
         e.printStackTrace();
     } finally {
         document.close();
     }
     response.setHeader( "Pragma", "public" );  

     response.setContentType( "application/pdf" );
      response.setHeader("Content-disposition", "attachment;filename="+pdffile+"" );
     // ServletOutputStream stream = null;

     // BufferedInputStream buf =null;
      //stream = response.getOutputStream();
      int length=0; 
      //FileInputStream input = new FileInputStream(word_rep);
      try{
      ServletOutputStream sos = response.getOutputStream();   
      byte[] bbuf = new byte[4096];   

      DataInputStream in = new DataInputStream(new FileInputStream(pdffile)); 
      
      while ((in != null) && ((length = in.read(bbuf)) != -1))   
      {   
          sos.write(bbuf,0,length);   
      } 
      
      
      
      in.close();   
      sos.flush();   
      response.flushBuffer();   
      sos.close(); 
      
 }
      catch(Exception e){
    	  e.printStackTrace();
      }
 

		
		return null;
	}
	public ActionForward genrateCsvMasterRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		 List tocsv=new ArrayList();
		 tocsv=(ArrayList)session.getAttribute("masterList");
		// rf.setM_rquestDate(rs.getString("request_date"));
			// rf.setM_status(rs.getString("Approve_Type"));
			// rf.setM_materialType(rs.getString("type"));
			// rf.setM_approveDate(rs.getString("approve_date"));
			// rf.setM_plant(rs.getString("location_code"));
			// rf.setM_materialGroup(rs.getString("material_group_name"));
		
		
		File csvfile=new File("c:/csv_output.csv");
		try
		{
		    FileWriter writer = new FileWriter(csvfile);
	 
		    writer.append("REQUEST DATE");
		    writer.append(',');
		    writer.append("APPROVE STATUS");
		    writer.append(',');
		    writer.append("MATERIAL TYPE");
		    writer.append(',');
		    writer.append("APPROVE DATE");
		    writer.append(',');
		    writer.append("PLANT CODE");
		    writer.append(',');
		    writer.append("MATERIAL GROUP");
		    writer.append(',');
		    
		    writer.append('\n');
		    
		    Iterator itr=tocsv.iterator();
		    
		    while(itr.hasNext()){
				  ReportRequestForm rr=(ReportRequestForm)itr.next();
				  writer.append(rr.getM_rquestDate());
				    writer.append(',');
				    writer.append(rr.getM_status());
				    writer.append(',');
				    writer.append(rr.getM_materialType());
				    writer.append(',');
				    writer.append(rr.getM_approveDate());
				    writer.append(',');
				    writer.append(rr.getM_plant());
				    writer.append(',');
				    writer.append(rr.getM_materialGroup());
				    writer.append(',');
				    
				    writer.append('\n');
				  
		    }
	 
		    //generate whatever data you want
	 
		    writer.flush();
		    writer.close();
		}
		catch(IOException e)
		{
		     e.printStackTrace();
		} 
		response.setHeader( "Pragma", "public" );  

	     response.setContentType( "application/vnd.ms-excel" );
	      response.setHeader("Content-disposition", "attachment;filename="+csvfile+"" );
	     // ServletOutputStream stream = null;

	     // BufferedInputStream buf =null;
	      //stream = response.getOutputStream();
	      int length=0; 
	      //FileInputStream input = new FileInputStream(word_rep);
	      try{
	      ServletOutputStream sos = response.getOutputStream();   
	      byte[] bbuf = new byte[4096];   

	      DataInputStream in = new DataInputStream(new FileInputStream(csvfile)); 
	      
	      while ((in != null) && ((length = in.read(bbuf)) != -1))   
	      {   
	          sos.write(bbuf,0,length);   
	      } 
	      
	      
	      
	      in.close();   
	      sos.flush();   
	      response.flushBuffer();   
	      sos.close(); 
	      }
	      catch(Exception e){
	    	  e.printStackTrace();
	      }

		
		
		return null;
	      }
	public ActionForward genrateExcelServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session=request.getSession();
		 List toExcel=new ArrayList();
		 toExcel=(ArrayList)session.getAttribute("slist");
		  HSSFWorkbook l_workBook_out = new HSSFWorkbook(); 
		  HSSFSheet sheet = l_workBook_out.createSheet("Report");   
		  
		  //--------column heading--------------------------//
		  //rf.setS_requestDate(rs.getString("request_date"));
			//rf.setS_plant(rs.getString("plant_code"));
			//rf.setS_requestedBy(rs.getString("requested_by"));
			//rf.setS_serviceCatagory(rs.getString("service_catagory"));
			//rf.setS_serviceDescription(rs.getString("service_description"));
			//rf.setS_serviceGroup(rs.getString("service_group"));
			//rf.setS_status(rs.getString("app_satus"));
			//rf.setS_approveDate(rs.getString("approve_date"));
		  
		  int rowNum=0;
		HSSFRow headerRow = sheet.createRow((short) rowNum); 
		  headerRow.createCell((short) 0).setCellValue(new HSSFRichTextString("REQUEST DATE")); 
		  headerRow.createCell((short) 1).setCellValue(new HSSFRichTextString("PLANT CODE")); 
		  headerRow.createCell((short) 2).setCellValue(new HSSFRichTextString("REQUESTED BY")); 
		  headerRow.createCell((short) 3).setCellValue(new HSSFRichTextString("SERVICE CATAGORY")); 
		  headerRow.createCell((short) 4).setCellValue(new HSSFRichTextString("SERVICE DESCRIPTION")); 
		  headerRow.createCell((short) 5).setCellValue(new HSSFRichTextString("APPROVAL STATUS")); 
		  headerRow.createCell((short) 6).setCellValue(new HSSFRichTextString("APPROVE DATE")); 
		  
		  Iterator itr=toExcel.iterator();
		  
		  while(itr.hasNext()){
			  ReportRequestForm rr=(ReportRequestForm)itr.next();
			  
			  HSSFRow row1 = sheet.createRow((short) ++rowNum); 

			  HSSFCell nameCell = row1.createCell((short) 0); 
			  nameCell.setCellValue(new HSSFRichTextString(rr.getS_requestDate())); 

			  HSSFCell addressCell = row1.createCell((short) 1); 
			  addressCell.setCellValue(new HSSFRichTextString(rr.getS_plant())); 

			  HSSFCell cityCell = row1.createCell((short) 2); 
			  cityCell.setCellValue(new HSSFRichTextString(rr.getS_requestedBy())); 
			  
			  HSSFCell appdate = row1.createCell((short) 3); 
			  appdate.setCellValue(new HSSFRichTextString(rr.getS_serviceCatagory())); 
			  
			  HSSFCell plant = row1.createCell((short) 4); 
			  plant.setCellValue(new HSSFRichTextString(rr.getS_serviceDescription())); 
			  
			  HSSFCell status = row1.createCell((short) 5); 
			  status.setCellValue(new HSSFRichTextString(rr.getS_status())); 
			  
			  HSSFCell app_date = row1.createCell((short) 6); 
			  app_date.setCellValue(new HSSFRichTextString(rr.getS_approveDate()));


			  
		  }
		  //------------genrate outpuut----------------------//
		  
		  
		   File l_str_file_out = new File("C:/results.xls"); //Give the location suitable to your requirement   
		    FileOutputStream fileOut;   
		   try {   
		     fileOut = new FileOutputStream(l_str_file_out);   
		     l_workBook_out.write(fileOut);   
		     fileOut.close();   
		    } 
		   	catch (Exception e) {   
		    e.printStackTrace();
		    }
		   	
		   	//---------------------Out put excel file-----------------//
		   	
		    response.setHeader( "Pragma", "public" );  

              response.setContentType( "application/vnd.ms-excel" );
               response.setHeader("Content-disposition", "attachment;filename="+l_str_file_out+"" );
              // ServletOutputStream stream = null;

              // BufferedInputStream buf =null;
               //stream = response.getOutputStream();
               int length=0; 
               //FileInputStream input = new FileInputStream(word_rep);
               try{
               ServletOutputStream sos = response.getOutputStream();   
               byte[] bbuf = new byte[4096];   

               DataInputStream in = new DataInputStream(new FileInputStream(l_str_file_out)); 
               
               while ((in != null) && ((length = in.read(bbuf)) != -1))   
               {   
                   sos.write(bbuf,0,length);   
               } 
               
               
               
               in.close();   
               sos.flush();   
               response.flushBuffer();   
               sos.close(); 
               }
               catch(Exception e){
               	e.printStackTrace();
               }

		
		
		
		return null;
	}
	public ActionForward genratePdfServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toPdf=new ArrayList();
		 toPdf=(ArrayList)session.getAttribute("slist");
		 Document document = new Document();
		 File pdffile=new File("c:/pdf_genrated.pdf");
		 try{
		 //rf.setS_requestDate(rs.getString("request_date"));
			//rf.setS_plant(rs.getString("plant_code"));
			//rf.setS_requestedBy(rs.getString("requested_by"));
			//rf.setS_serviceCatagory(rs.getString("service_catagory"));
			//rf.setS_serviceDescription(rs.getString("service_description"));
			//rf.setS_serviceGroup(rs.getString("service_group"));
			//rf.setS_status(rs.getString("app_satus"));
			//rf.setS_approveDate(rs.getString("approve_date"));
       	 
       
		 PdfWriter.getInstance(document,
                new FileOutputStream(pdffile));
        document.open();

        //
        // Creates a table with four column. The first rows
        // will have cell 1 to cell 4.
        //
        PdfPTable table = new PdfPTable(8);
        PdfPCell cell1 = new PdfPCell(new Phrase("REQUEST DATE"));
        PdfPCell cell2 = new PdfPCell(new Phrase("PLANT CODE"));
        PdfPCell cell3 = new PdfPCell(new Phrase("REQUESTED BY"));
        PdfPCell cell4 = new PdfPCell(new Phrase("SERVICE CATAGORY"));
        PdfPCell cell5 = new PdfPCell(new Phrase("SERVICE DESCRIPTION"));
        PdfPCell cell6 = new PdfPCell(new Phrase("SERVICE GROUP"));
        PdfPCell cell7 = new PdfPCell(new Phrase("APPROVAL STATUS"));
        PdfPCell cell8 = new PdfPCell(new Phrase("APPROVAL DATE"));
        
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setBorderWidth(1f);
        cell1.setBorderColor(BaseColor.WHITE);
        
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell2.setBorderWidth(1f);
        cell2.setBorderColor(BaseColor.WHITE);
        
        cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell3.setBorderWidth(1f);
        cell3.setBorderColor(BaseColor.WHITE);

        cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell4.setBorderWidth(1f);
        cell4.setBorderColor(BaseColor.WHITE);
        
        cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell5.setBorderWidth(1f);
        cell5.setBorderColor(BaseColor.WHITE);

        cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell6.setBorderWidth(1f);
        cell6.setBorderColor(BaseColor.WHITE);
        
        cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell7.setBorderWidth(1f);
        cell7.setBorderColor(BaseColor.WHITE);
        
        cell8.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell8.setBorderWidth(1f);
        cell8.setBorderColor(BaseColor.WHITE);


        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
        table.addCell(cell8);
        table.completeRow();
     
        
        Iterator itr=toPdf.iterator();
        
        while(itr.hasNext()){
			  ReportRequestForm rr=(ReportRequestForm)itr.next();
			   table.addCell(new PdfPCell(new Phrase(rr.getS_requestDate())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_plant())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_requestedBy())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_serviceCatagory())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_serviceDescription())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_serviceGroup())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_status())));
		         table.addCell(new PdfPCell(new Phrase(rr.getS_approveDate())));
		         table.completeRow();
        }
        //
        // Creates another row that only have to columns.
        // The cell 5 and cell 6 width will span two columns
        // in width.
        //
       
        table.completeRow();

        //
        // Adds table to the document
        //
        document.add(table);
        
        
        
	
        }catch (Exception e) {
        e.printStackTrace();
    } finally {
        document.close();
    }
    response.setHeader( "Pragma", "public" );  

    response.setContentType( "application/pdf" );
     response.setHeader("Content-disposition", "attachment;filename="+pdffile+"" );
    // ServletOutputStream stream = null;

    // BufferedInputStream buf =null;
     //stream = response.getOutputStream();
     int length=0; 
     //FileInputStream input = new FileInputStream(word_rep);
     try{
     ServletOutputStream sos = response.getOutputStream();   
     byte[] bbuf = new byte[4096];   

     DataInputStream in = new DataInputStream(new FileInputStream(pdffile)); 
     
     while ((in != null) && ((length = in.read(bbuf)) != -1))   
     {   
         sos.write(bbuf,0,length);   
     } 
     
     
     
     in.close();   
     sos.flush();   
     response.flushBuffer();   
     sos.close(); 
     
}
     catch(Exception e){
   	  e.printStackTrace();
     }
		
		
		
		
		
		return null;
	}
	public ActionForward genrateCsvServiceRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toCsv=new ArrayList();
		 toCsv=(ArrayList)session.getAttribute("slist");
		 
		 File csvfile=new File("c:/csv_output.csv");
			try
			{
			    FileWriter writer = new FileWriter(csvfile);
		 
			    writer.append("REQUEST DATE");
			    writer.append(',');
			    writer.append("PLANT CODE");
			    writer.append(',');
			    writer.append("SERVICE CATAGORY");
			    writer.append(',');
			    writer.append("SERVICE DESCRIPTION");
			    writer.append(',');
			    writer.append("SERVICE GROUP");
			    writer.append(',');
			    writer.append("APPROVAL STATUS");
			    writer.append(',');
			    writer.append("APPROVAL DATE");
			    writer.append(',');
			    
			    writer.append('\n');
			    
			    Iterator itr=toCsv.iterator();
			    
			    while(itr.hasNext()){
					  ReportRequestForm rr=(ReportRequestForm)itr.next();
					  writer.append(rr.getS_requestDate());
					    writer.append(',');
					    writer.append(rr.getS_plant());
					    writer.append(',');
					    writer.append(rr.getS_requestedBy());
					    writer.append(',');
					    writer.append(rr.getS_serviceCatagory());
					    writer.append(',');
					    writer.append(rr.getS_serviceDescription());
					    writer.append(',');
					    writer.append(rr.getS_serviceGroup());
					    writer.append(',');
					    writer.append(rr.getS_status());
					    writer.append(',');
					    writer.append(rr.getS_approveDate());
					    writer.append(',');

					    
					    writer.append('\n');
					  
			    }
		 
			    //generate whatever data you want
		 
			    writer.flush();
			    writer.close();
			}
			catch(IOException e)
			{
			     e.printStackTrace();
			} 
			response.setHeader( "Pragma", "public" );  

		     response.setContentType( "application/vnd.ms-excel" );
		      response.setHeader("Content-disposition", "attachment;filename="+csvfile+"" );
		     // ServletOutputStream stream = null;

		     // BufferedInputStream buf =null;
		      //stream = response.getOutputStream();
		      int length=0; 
		      //FileInputStream input = new FileInputStream(word_rep);
		      try{
		      ServletOutputStream sos = response.getOutputStream();   
		      byte[] bbuf = new byte[4096];   

		      DataInputStream in = new DataInputStream(new FileInputStream(csvfile)); 
		      
		      while ((in != null) && ((length = in.read(bbuf)) != -1))   
		      {   
		          sos.write(bbuf,0,length);   
		      } 
		      
		      
		      
		      in.close();   
		      sos.flush();   
		      response.flushBuffer();   
		      sos.close(); 
		      }
		      catch(Exception e){
		    	  e.printStackTrace();
		      }
		
		return null;
	}
	public ActionForward genrateExcelCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		   HttpSession session=request.getSession();
		   List toExcel=new ArrayList();
		   toExcel=(ArrayList)session.getAttribute("clist");
		   HSSFWorkbook l_workBook_out = new HSSFWorkbook(); 
			  HSSFSheet sheet = l_workBook_out.createSheet("Report");   
			  
			  //--------column heading--------------------------//
			  //rf.setC_country(rs.getString("country_name"));
				// rf.setC_customerGroup(rs.getString("customer_group"));
				// String reqDate=rs.getString("REQUEST_DATE");
				//	String a[]=reqDate.split(" ");
				//	reqDate=a[0];
				//	String b[]=reqDate.split("-");
				//	reqDate=b[2]+"/"+b[1]+"/"+b[0];
				// rf.setC_requestDate(reqDate);
				// rf.setC_status(rs.getString("approve_status"));
				// rf.setC_name(rs.getString("name"));
				// rf.setC_requestedBy(rs.getString("created_by"));
				// String appDate=rs.getString("approve_date");
				// if(appDate!=null){
				//	String a1[]=appDate.split(" ");
				//	appDate=a1[0];
				//	String b1[]=appDate.split("-");
				//	appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
				// rf.setC_approveDate(appDate);
			  
			  int rowNum=0;
			HSSFRow headerRow = sheet.createRow((short) rowNum); 
			  headerRow.createCell((short) 0).setCellValue(new HSSFRichTextString("COUNTRY NAME")); 
			  headerRow.createCell((short) 1).setCellValue(new HSSFRichTextString("CUSTOMER GROUP")); 
			  headerRow.createCell((short) 2).setCellValue(new HSSFRichTextString("REQUEST DATE")); 
			  headerRow.createCell((short) 3).setCellValue(new HSSFRichTextString("APPROVE STATUS")); 
			  headerRow.createCell((short) 4).setCellValue(new HSSFRichTextString("NAME")); 
			  headerRow.createCell((short) 5).setCellValue(new HSSFRichTextString("CREATED BY")); 
			  headerRow.createCell((short) 6).setCellValue(new HSSFRichTextString("APPROVE DATE")); 
			  
			  Iterator itr=toExcel.iterator();
			  
			  while(itr.hasNext()){
				  ReportRequestForm rr=(ReportRequestForm)itr.next();
				  
				  HSSFRow row1 = sheet.createRow((short) ++rowNum); 

				  HSSFCell nameCell = row1.createCell((short) 0); 
				  nameCell.setCellValue(new HSSFRichTextString(rr.getC_country())); 

				  HSSFCell addressCell = row1.createCell((short) 1); 
				  addressCell.setCellValue(new HSSFRichTextString(rr.getC_customerGroup())); 

				  HSSFCell cityCell = row1.createCell((short) 2); 
				  cityCell.setCellValue(new HSSFRichTextString(rr.getC_requestDate())); 
				  
				  HSSFCell appdate = row1.createCell((short) 3); 
				  appdate.setCellValue(new HSSFRichTextString(rr.getC_status())); 
				  
				  HSSFCell plant = row1.createCell((short) 4); 
				  plant.setCellValue(new HSSFRichTextString(rr.getC_name())); 
				  
				  HSSFCell materialGroup = row1.createCell((short) 5); 
				  materialGroup.setCellValue(new HSSFRichTextString(rr.getC_requestedBy())); 
				  
				  HSSFCell app_date = row1.createCell((short) 5); 
				  app_date.setCellValue(new HSSFRichTextString(rr.getC_approveDate()));


				  
			  }
			  //------------genrate outpuut----------------------//
			  
			  
			   File l_str_file_out = new File("C:/results.xls"); //Give the location suitable to your requirement   
			    FileOutputStream fileOut;   
			   try {   
			     fileOut = new FileOutputStream(l_str_file_out);   
			     l_workBook_out.write(fileOut);   
			     fileOut.close();   
			    } 
			   	catch (Exception e) {   
			    e.printStackTrace();
			    }
			   	
			   	//---------------------Out put excel file-----------------//
			   	
			    response.setHeader( "Pragma", "public" );  

	              response.setContentType( "application/vnd.ms-excel" );
	               response.setHeader("Content-disposition", "attachment;filename="+l_str_file_out+"" );
	              // ServletOutputStream stream = null;

	              // BufferedInputStream buf =null;
	               //stream = response.getOutputStream();
	               int length=0; 
	               //FileInputStream input = new FileInputStream(word_rep);
	               try{
	               ServletOutputStream sos = response.getOutputStream();   
	               byte[] bbuf = new byte[4096];   

	               DataInputStream in = new DataInputStream(new FileInputStream(l_str_file_out)); 
	               
	               while ((in != null) && ((length = in.read(bbuf)) != -1))   
	               {   
	                   sos.write(bbuf,0,length);   
	               } 
	               
	               
	               
	               in.close();   
	               sos.flush();   
	               response.flushBuffer();   
	               sos.close(); 
	               }
	               catch(Exception e){
	               	e.printStackTrace();
	               }

		
		return null;
	}
	public ActionForward genratePdfCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toPdf=new ArrayList();
		 toPdf=(ArrayList)session.getAttribute("clist");
		 Document document = new Document();
		 File pdffile=new File("c:/pdf_genrated.pdf");
		 try{
			
       
		 PdfWriter.getInstance(document,
                new FileOutputStream(pdffile));
        document.open();

        //
        // Creates a table with four column. The first rows
        // will have cell 1 to cell 4.
        //
        PdfPTable table = new PdfPTable(8);
        PdfPCell cell1 = new PdfPCell(new Phrase("COUNTRY NAME"));
        PdfPCell cell2 = new PdfPCell(new Phrase("CUSTOMER GROUP"));
        PdfPCell cell3 = new PdfPCell(new Phrase("REQUEST DATE"));
        PdfPCell cell4 = new PdfPCell(new Phrase("APPROVE STATUS"));
        PdfPCell cell5 = new PdfPCell(new Phrase("NAME"));
        PdfPCell cell6 = new PdfPCell(new Phrase("CREATED BY"));
        PdfPCell cell7 = new PdfPCell(new Phrase("APPROVE DATE"));
        
        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell1.setBorderWidth(1f);
        cell1.setBorderColor(BaseColor.WHITE);
        
        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell2.setBorderWidth(1f);
        cell2.setBorderColor(BaseColor.WHITE);
        
        cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell3.setBorderWidth(1f);
        cell3.setBorderColor(BaseColor.WHITE);

        cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell4.setBorderWidth(1f);
        cell4.setBorderColor(BaseColor.WHITE);
        
        cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell5.setBorderWidth(1f);
        cell5.setBorderColor(BaseColor.WHITE);

        cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell6.setBorderWidth(1f);
        cell6.setBorderColor(BaseColor.WHITE);
        
        cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell7.setBorderWidth(1f);
        cell7.setBorderColor(BaseColor.WHITE);
        
       


        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        table.addCell(cell5);
        table.addCell(cell6);
        table.addCell(cell7);
       
        table.completeRow();
     
        
        Iterator itr=toPdf.iterator();
        
        while(itr.hasNext()){
			  ReportRequestForm rr=(ReportRequestForm)itr.next();
			   table.addCell(new PdfPCell(new Phrase(rr.getC_country())));
		         table.addCell(new PdfPCell(new Phrase(rr.getC_customerGroup())));
		         table.addCell(new PdfPCell(new Phrase(rr.getC_requestDate())));
		         table.addCell(new PdfPCell(new Phrase(rr.getC_status())));
		         table.addCell(new PdfPCell(new Phrase(rr.getC_name())));
		         table.addCell(new PdfPCell(new Phrase(rr.getC_requestedBy())));
		         table.addCell(new PdfPCell(new Phrase(rr.getC_approveDate())));
		        
		         table.completeRow();
        }
        //
        // Creates another row that only have to columns.
        // The cell 5 and cell 6 width will span two columns
        // in width.
        //
       
        table.completeRow();

        //
        // Adds table to the document
        //
        document.add(table);
        
        
        
	
        }catch (Exception e) {
        e.printStackTrace();
    } finally {
        document.close();
    }
    response.setHeader( "Pragma", "public" );  

    response.setContentType( "application/pdf" );
     response.setHeader("Content-disposition", "attachment;filename="+pdffile+"" );
    // ServletOutputStream stream = null;

    // BufferedInputStream buf =null;
     //stream = response.getOutputStream();
     int length=0; 
     //FileInputStream input = new FileInputStream(word_rep);
     try{
     ServletOutputStream sos = response.getOutputStream();   
     byte[] bbuf = new byte[4096];   

     DataInputStream in = new DataInputStream(new FileInputStream(pdffile)); 
     
     while ((in != null) && ((length = in.read(bbuf)) != -1))   
     {   
         sos.write(bbuf,0,length);   
     } 
     
     
     
     in.close();   
     sos.flush();   
     response.flushBuffer();   
     sos.close(); 
     
}
     catch(Exception e){
   	  e.printStackTrace();
     }
		
		
		
		
		return null;
	}
	public ActionForward genrateCsvCustomerRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toCsv=new ArrayList();
		 toCsv=(ArrayList)session.getAttribute("clist");
		
		 File csvfile=new File("c:/csv_output.csv");
			try
			{
			    FileWriter writer = new FileWriter(csvfile);
		 
			    writer.append("COUNTRY NAME");
			    writer.append(',');
			    writer.append("CUSTOMER GROUP");
			    writer.append(',');
			    writer.append("REQUEST DATE");
			    writer.append(',');
			    writer.append("APPROVE STATUS");
			    writer.append(',');
			    writer.append("NAME");
			    writer.append(',');
			    writer.append("CREATED BY");
			    writer.append(',');
			    writer.append("APPROVE DATE");
			    writer.append(',');
			    
			    writer.append('\n');
			    
			    Iterator itr=toCsv.iterator();
			    
			    while(itr.hasNext()){
					  ReportRequestForm rr=(ReportRequestForm)itr.next();
					  writer.append(rr.getC_country());
					    writer.append(',');
					    writer.append(rr.getC_customerGroup());
					    writer.append(',');
					    writer.append(rr.getC_requestDate());
					    writer.append(',');
					    writer.append(rr.getC_status());
					    writer.append(',');
					    writer.append(rr.getC_name());
					    writer.append(',');
					    writer.append(rr.getC_requestedBy());
					    writer.append(',');
					    writer.append(rr.getC_approveDate());
					    writer.append(',');

					    
					    writer.append('\n');
					  
			    }
		 
			    //generate whatever data you want
		 
			    writer.flush();
			    writer.close();
			}
			catch(IOException e)
			{
			     e.printStackTrace();
			} 
			response.setHeader( "Pragma", "public" );  

		     response.setContentType( "application/vnd.ms-excel" );
		      response.setHeader("Content-disposition", "attachment;filename="+csvfile+"" );
		     // ServletOutputStream stream = null;

		     // BufferedInputStream buf =null;
		      //stream = response.getOutputStream();
		      int length=0; 
		      //FileInputStream input = new FileInputStream(word_rep);
		      try{
		      ServletOutputStream sos = response.getOutputStream();   
		      byte[] bbuf = new byte[4096];   

		      DataInputStream in = new DataInputStream(new FileInputStream(csvfile)); 
		      
		      while ((in != null) && ((length = in.read(bbuf)) != -1))   
		      {   
		          sos.write(bbuf,0,length);   
		      } 
		      
		      
		      
		      in.close();   
		      sos.flush();   
		      response.flushBuffer();   
		      sos.close(); 
		      }
		      catch(Exception e){
		    	  e.printStackTrace();
		      }
		
		
		return null;
	}
	public ActionForward genrateExcelVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		   HttpSession session=request.getSession();
		   List toExcel=new ArrayList();
		   toExcel=(ArrayList)session.getAttribute("vlist");
		   //String reqDate=rs.getString("REQUEST_DATE");
			//String a[]=reqDate.split(" ");
			//reqDate=a[0];
			//String b[]=reqDate.split("-");
			//reqDate=b[2]+"/"+b[1]+"/"+b[0];
		 //rf.setV_requestDate(reqDate);
		// rf.setV_name(rs.getString("name"));
		// rf.setV_location(rs.getString("location_name"));
		// rf.setV_country(rs.getString("country_name"));
		// rf.setV_vendorType(rs.getString("type"));
		// rf.setV_status(rs.getString("approve_status"));
		// String appDate=rs.getString("approve_date");
		// if(appDate!=null){
		//	String a1[]=appDate.split(" ");
		//	appDate=a1[0];
		//	String b1[]=appDate.split("-");
		//	appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
		// rf.setV_approveDate(appDate);
		   HSSFWorkbook l_workBook_out = new HSSFWorkbook(); 
			  HSSFSheet sheet = l_workBook_out.createSheet("Report");   
			  int rowNum=0;
				HSSFRow headerRow = sheet.createRow((short) rowNum); 
				  headerRow.createCell((short) 0).setCellValue(new HSSFRichTextString("REQUEST DATE")); 
				  headerRow.createCell((short) 1).setCellValue(new HSSFRichTextString("NAME")); 
				  headerRow.createCell((short) 2).setCellValue(new HSSFRichTextString("LOCATION NAME")); 
				  headerRow.createCell((short) 3).setCellValue(new HSSFRichTextString("COUNTRY NAME")); 
				  headerRow.createCell((short) 4).setCellValue(new HSSFRichTextString("TYPE")); 
				  headerRow.createCell((short) 5).setCellValue(new HSSFRichTextString("APPROVE STATUS")); 
				  headerRow.createCell((short) 6).setCellValue(new HSSFRichTextString("APPROVE DATE")); 
				  
				  Iterator itr=toExcel.iterator();
				  
				  while(itr.hasNext()){
					  ReportRequestForm rr=(ReportRequestForm)itr.next();
					  
					  HSSFRow row1 = sheet.createRow((short) ++rowNum); 

					  HSSFCell nameCell = row1.createCell((short) 0); 
					  nameCell.setCellValue(new HSSFRichTextString(rr.getV_requestDate())); 

					  HSSFCell addressCell = row1.createCell((short) 1); 
					  addressCell.setCellValue(new HSSFRichTextString(rr.getV_name())); 

					  HSSFCell cityCell = row1.createCell((short) 2); 
					  cityCell.setCellValue(new HSSFRichTextString(rr.getV_location())); 
					  
					  HSSFCell appdate = row1.createCell((short) 3); 
					  appdate.setCellValue(new HSSFRichTextString(rr.getV_country())); 
					  
					  HSSFCell plant = row1.createCell((short) 4); 
					  plant.setCellValue(new HSSFRichTextString(rr.getV_vendorType())); 
					  
					  HSSFCell materialGroup = row1.createCell((short) 5); 
					  materialGroup.setCellValue(new HSSFRichTextString(rr.getV_status())); 
					  
					  HSSFCell app_date = row1.createCell((short) 6); 
					  app_date.setCellValue(new HSSFRichTextString(rr.getV_approveDate()));


					  
				  }
				  //------------genrate outpuut----------------------//
				  
				  
				   File l_str_file_out = new File("C:/results.xls"); //Give the location suitable to your requirement   
				    FileOutputStream fileOut;   
				   try {   
				     fileOut = new FileOutputStream(l_str_file_out);   
				     l_workBook_out.write(fileOut);   
				     fileOut.close();   
				    } 
				   	catch (Exception e) {   
				    e.printStackTrace();
				    }
				   	
				   	//---------------------Out put excel file-----------------//
				   	
				    response.setHeader( "Pragma", "public" );  

		              response.setContentType( "application/vnd.ms-excel" );
		               response.setHeader("Content-disposition", "attachment;filename="+l_str_file_out+"" );
		              // ServletOutputStream stream = null;

		              // BufferedInputStream buf =null;
		               //stream = response.getOutputStream();
		               int length=0; 
		               //FileInputStream input = new FileInputStream(word_rep);
		               try{
		               ServletOutputStream sos = response.getOutputStream();   
		               byte[] bbuf = new byte[4096];   

		               DataInputStream in = new DataInputStream(new FileInputStream(l_str_file_out)); 
		               
		               while ((in != null) && ((length = in.read(bbuf)) != -1))   
		               {   
		                   sos.write(bbuf,0,length);   
		               } 
		               
		               
		               
		               in.close();   
		               sos.flush();   
		               response.flushBuffer();   
		               sos.close(); 
		               }
		               catch(Exception e){
		               	e.printStackTrace();
		               }

		
		
		return null;
	}
	public ActionForward genratePdfVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		 List toPdf=new ArrayList();
		 toPdf=(ArrayList)session.getAttribute("vlist");
		 Document document = new Document();
		 File pdffile=new File("c:/pdf_genrated.pdf");
		//String reqDate=rs.getString("REQUEST_DATE");
			//String a[]=reqDate.split(" ");
			//reqDate=a[0];
			//String b[]=reqDate.split("-");
			//reqDate=b[2]+"/"+b[1]+"/"+b[0];
		 //rf.setV_requestDate(reqDate);
		// rf.setV_name(rs.getString("name"));
		// rf.setV_location(rs.getString("location_name"));
		// rf.setV_country(rs.getString("country_name"));
		// rf.setV_vendorType(rs.getString("type"));
		// rf.setV_status(rs.getString("approve_status"));
		// String appDate=rs.getString("approve_date");
		// if(appDate!=null){
		//	String a1[]=appDate.split(" ");
		//	appDate=a1[0];
		//	String b1[]=appDate.split("-");
		//	appDate=b1[2]+"/"+b1[1]+"/"+b1[0];
		// rf.setV_approveDate(appDate);
		 try{
				
		       
			 PdfWriter.getInstance(document,
	                new FileOutputStream(pdffile));
	        document.open();

	        //
	        // Creates a table with four column. The first rows
	        // will have cell 1 to cell 4.
	        //
	        PdfPTable table = new PdfPTable(7);
	        PdfPCell cell1 = new PdfPCell(new Phrase("REQUEST DATE"));
	        PdfPCell cell2 = new PdfPCell(new Phrase("NAME"));
	        PdfPCell cell3 = new PdfPCell(new Phrase("LOCATION NAME"));
	        PdfPCell cell4 = new PdfPCell(new Phrase("COUNTRY NAME"));
	        PdfPCell cell5 = new PdfPCell(new Phrase("TYPE"));
	        PdfPCell cell6 = new PdfPCell(new Phrase("APPROVE STATUS"));
	        PdfPCell cell7 = new PdfPCell(new Phrase("APPROVE DATE"));
	        
	        cell1.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell1.setBorderWidth(1f);
	        cell1.setBorderColor(BaseColor.WHITE);
	        
	        cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell2.setBorderWidth(1f);
	        cell2.setBorderColor(BaseColor.WHITE);
	        
	        cell3.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell3.setBorderWidth(1f);
	        cell3.setBorderColor(BaseColor.WHITE);

	        cell4.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell4.setBorderWidth(1f);
	        cell4.setBorderColor(BaseColor.WHITE);
	        
	        cell5.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell5.setBorderWidth(1f);
	        cell5.setBorderColor(BaseColor.WHITE);

	        cell6.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell6.setBorderWidth(1f);
	        cell6.setBorderColor(BaseColor.WHITE);
	        
	        cell7.setBackgroundColor(BaseColor.LIGHT_GRAY);
	        cell7.setBorderWidth(1f);
	        cell7.setBorderColor(BaseColor.WHITE);
	        
	       


	        table.addCell(cell1);
	        table.addCell(cell2);
	        table.addCell(cell3);
	        table.addCell(cell4);
	        table.addCell(cell5);
	        table.addCell(cell6);
	        table.addCell(cell7);
	       
	        table.completeRow();
	     
	        
	        Iterator itr=toPdf.iterator();
	        
	        while(itr.hasNext()){
				  ReportRequestForm rr=(ReportRequestForm)itr.next();
				   table.addCell(new PdfPCell(new Phrase(rr.getV_requestDate())));
			         table.addCell(new PdfPCell(new Phrase(rr.getV_name())));
			         table.addCell(new PdfPCell(new Phrase(rr.getV_location())));
			         table.addCell(new PdfPCell(new Phrase(rr.getV_country())));
			         table.addCell(new PdfPCell(new Phrase(rr.getV_vendorType())));
			         table.addCell(new PdfPCell(new Phrase(rr.getV_status())));
			         table.addCell(new PdfPCell(new Phrase(rr.getV_approveDate())));
			        
			         table.completeRow();
	        }
	        //
	        // Creates another row that only have to columns.
	        // The cell 5 and cell 6 width will span two columns
	        // in width.
	        //
	       
	        table.completeRow();

	        //
	        // Adds table to the document
	        //
	        document.add(table);
	        
	        
	        
		
	        }catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        document.close();
	    }
	    response.setHeader( "Pragma", "public" );  

	    response.setContentType( "application/pdf" );
	     response.setHeader("Content-disposition", "attachment;filename="+pdffile+"" );
	    // ServletOutputStream stream = null;

	    // BufferedInputStream buf =null;
	     //stream = response.getOutputStream();
	     int length=0; 
	     //FileInputStream input = new FileInputStream(word_rep);
	     try{
	     ServletOutputStream sos = response.getOutputStream();   
	     byte[] bbuf = new byte[4096];   

	     DataInputStream in = new DataInputStream(new FileInputStream(pdffile)); 
	     
	     while ((in != null) && ((length = in.read(bbuf)) != -1))   
	     {   
	         sos.write(bbuf,0,length);   
	     } 
	     
	     
	     
	     in.close();   
	     sos.flush();   
	     response.flushBuffer();   
	     sos.close(); 
	     
	}
	     catch(Exception e){
	   	  e.printStackTrace();
	     }
			
		
		
		return null;
	}
	public ActionForward genrateCsvVendorRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		  HttpSession session=request.getSession();
		  List toCsv=new ArrayList();
		  toCsv=(ArrayList)session.getAttribute("vlist");
		  
		  
		  File csvfile=new File("c:/csv_output.csv");
			try
			{
			    FileWriter writer = new FileWriter(csvfile);
		 
			    writer.append("REQUEST DATE");
			    writer.append(',');
			    writer.append("NAME");
			    writer.append(',');
			    writer.append("LOCATION NAME");
			    writer.append(',');
			    writer.append("COUNTRY NAME");
			    writer.append(',');
			    writer.append("TYPE");
			    writer.append(',');
			    writer.append("APPROVE STATUS");
			    writer.append(',');
			    writer.append("APPROVE DATE");
			    writer.append(',');
			    
			    writer.append('\n');
			    
			    Iterator itr=toCsv.iterator();
			    
			    while(itr.hasNext()){
					  ReportRequestForm rr=(ReportRequestForm)itr.next();
					  writer.append(rr.getV_requestDate());
					    writer.append(',');
					    writer.append(rr.getV_name());
					    writer.append(',');
					    writer.append(rr.getV_location());
					    writer.append(',');
					    writer.append(rr.getV_country());
					    writer.append(',');
					    writer.append(rr.getV_vendorType());
					    writer.append(',');
					    writer.append(rr.getV_status());
					    writer.append(',');
					    writer.append(rr.getV_approveDate());
					    writer.append(',');

					    
					    writer.append('\n');
					  
			    }
		 
			    //generate whatever data you want
		 
			    writer.flush();
			    writer.close();
			}
			catch(IOException e)
			{
			     e.printStackTrace();
			} 
			response.setHeader( "Pragma", "public" );  

		     response.setContentType( "application/vnd.ms-excel" );
		      response.setHeader("Content-disposition", "attachment;filename="+csvfile+"" );
		     // ServletOutputStream stream = null;

		     // BufferedInputStream buf =null;
		      //stream = response.getOutputStream();
		      int length=0; 
		      //FileInputStream input = new FileInputStream(word_rep);
		      try{
		      ServletOutputStream sos = response.getOutputStream();   
		      byte[] bbuf = new byte[4096];   

		      DataInputStream in = new DataInputStream(new FileInputStream(csvfile)); 
		      
		      while ((in != null) && ((length = in.read(bbuf)) != -1))   
		      {   
		          sos.write(bbuf,0,length);   
		      } 
		      
		      
		      
		      in.close();   
		      sos.flush();   
		      response.flushBuffer();   
		      sos.close(); 
		      }
		      catch(Exception e){
		    	  e.printStackTrace();
		      }
		
		
		return null;
	}
}

