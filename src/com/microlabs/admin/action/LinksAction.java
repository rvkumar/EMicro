package com.microlabs.admin.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.admin.dao.LinksDao;
import com.microlabs.admin.form.LinksForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.newsandmedia.form.FckEditorForm;


public class LinksAction extends DispatchAction{
	
	public ActionForward searchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;
		try{
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			
			String id=request.getParameter("id");
			Date d = new Date();
			
			if(contentYear==null){
				
				
				contentYear=String.valueOf(1900 + d.getYear());
				
				
			}
			if(contentYear!=null){
				if(contentYear.equalsIgnoreCase("")){
					contentYear=String.valueOf(1900 + d.getYear());
				}
			}
			
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
	
			ResultSet rs=ad.selectQuery(sql);
			
			
			
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);
	 int  totalRecords=0;
	  int  startRecord=0;
	  int  endRecord=0;		
	  String searchType=linksForm.getSerchType();
	  String getTotalRecords="select count(*) from cms_sublinks where main_linkname like '%"+searchType+"%' or sub_linkname like '%"+searchType+"%' or content_year  like '%"+searchType+"%' or link_name  like '%"+searchType+"%'";

ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
while(rsTotalRecods.next())
{
totalRecords=rsTotalRecods.getInt(1);
}

if(totalRecords>10)
{
linksForm.setTotalRecords(totalRecords);
startRecord=1;
endRecord=10;
linksForm.setStartRecord(1);
linksForm.setEndRecord(10);
request.setAttribute("displayRecordNo", "displayRecordNo");
request.setAttribute("nextButton", "nextButton");
}else
{
startRecord=1;
endRecord=totalRecords;
linksForm.setTotalRecords(totalRecords);
linksForm.setStartRecord(1);
linksForm.setEndRecord(totalRecords); 
}

/*	  String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
"From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
*/


String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
"  From  cms_sublinks as c where main_linkname like '%"+searchType+"%' or sub_linkname like '%"+searchType+"%' or content_year  like '%"+searchType+"%' or link_name  like '%"+searchType+"%') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";

// String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";

ResultSet rs1=ad.selectQuery(sql2);

ArrayList a1=new ArrayList();
LinksForm linksForm1=null;


while(rs1.next()) {
	linksForm1=new LinksForm();
	
	linksForm1.setRowno(rs1.getInt("RowNum"));
	linksForm1.setCmsLinkId(rs1.getString("id"));
	linksForm1.setLinkName(rs1.getString("main_linkname"));
	linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
	linksForm1.setSubLinkTitle(rs1.getString("link_name"));
	linksForm1.setContentYear(rs1.getString("content_year"));
	linksForm1.setIconName(rs1.getString("icon_name"));
	a1.add(linksForm1);
}


request.setAttribute("CmsLinkDetails", a1);

ResultSet rs3 = ad.selectQuery("select * from links where status is null");

while(rs3.next()) {
	linkIdList.add(rs3.getString("link_name"));
	linkValueList.add(rs3.getString("link_name"));
}
linksForm.setLinkIdList(linkIdList);
linksForm.setLinkValueList(linkValueList);


String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
ResultSet rs2 = ad.selectQuery(sql1);

ArrayList list=new ArrayList();

LinksForm linksForm2=null;
int count=0;
while (rs2.next()){
	
		linksForm2 = new LinksForm();
		linksForm2.setImageId(rs2.getString("id")+","+count);
		linksForm2.setImageName(rs2.getString("file_name"));
		linksForm2.setImageTitle(rs2.getString("file_description"));
		list.add(linksForm2);
		count++;
	
}
request.setAttribute("listName", list);

	
	ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
			"and status=1 and sub_linkname=link_name");
	ArrayList subLinkIdList = new ArrayList();
	ArrayList subLinkValueList = new ArrayList();
	FckEditorForm fckEditorForm1 = null;
	while(rs4.next()) {
		subLinkIdList.add(rs4.getString("link_name"));
		subLinkValueList.add(rs4.getString("link_name"));
	}
	linksForm.setSubLinkIdList(subLinkIdList);
	linksForm.setSubLinkValueList(subLinkValueList);
	linksForm.setLinkName(mainLinkName);
	request.setAttribute("displaySublinkField", "displaySublinkField");
	request.setAttribute("submitButton", "submitButton");

	

}catch(SQLException se){
	se.printStackTrace();
}
request.setAttribute("addbutton", "addbutton");
return mapping.findForward("displayCmsLinks");
}
	
	public ActionForward lastTenRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;
		try{
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			
			String id=request.getParameter("id");
			Date d = new Date();
			
			if(contentYear==null){
				
				
				contentYear=String.valueOf(1900 + d.getYear());
				
				
			}
			if(contentYear!=null){
				if(contentYear.equalsIgnoreCase("")){
					contentYear=String.valueOf(1900 + d.getYear());
				}
			}
			
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
	
			ResultSet rs=ad.selectQuery(sql);
			
			
			
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);	
					int totalRecords=linksForm.getTotalRecords();//21
					int startRecord=linksForm.getStartRecord();//11
					int endRecord=linksForm.getEndRecord();	
					
					
					 startRecord=totalRecords-9;
					 endRecord=totalRecords;
					 linksForm.setTotalRecords(totalRecords);
					 linksForm.setStartRecord(startRecord);
					 linksForm.setEndRecord(totalRecords);
					String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
					"  From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";

				 // String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
					String searchType=linksForm.getSerchType();
					if(!searchType.equalsIgnoreCase(""))
					{
				sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
				"  From  cms_sublinks as c where main_linkname like '%"+searchType+"%' or sub_linkname like '%"+searchType+"%' or content_year  like '%"+searchType+"%' or link_name  like '%"+searchType+"%') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
					}				
						
						
						ArrayList listOfMaterialCode=new ArrayList();
						LinksForm linksForm1=null;
						
						ResultSet rs1=ad.selectQuery(sql2);
						while(rs1.next()) {
							linksForm1=new LinksForm();
							
							linksForm1.setRowno(rs1.getInt("RowNum"));
							linksForm1.setCmsLinkId(rs1.getString("id"));
							linksForm1.setLinkName(rs1.getString("main_linkname"));
							linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
							linksForm1.setSubLinkTitle(rs1.getString("link_name"));
							linksForm1.setContentYear(rs1.getString("content_year"));
							linksForm1.setIconName(rs1.getString("icon_name"));
							listOfMaterialCode.add(linksForm1);
						}
						
						
						request.setAttribute("CmsLinkDetails", listOfMaterialCode);
						 request.setAttribute("disableNextButton", "disableNextButton");
							request.setAttribute("previousButton", "previousButton");
							if(listOfMaterialCode.size()<10)
							{
								
								request.setAttribute("previousButton", "");
								request.setAttribute("disablePreviousButton", "disablePreviousButton");
							}
							request.setAttribute("displayRecordNo", "displayRecordNo");
						
						
						
							// set links and sub links
							 
							 ResultSet rs3 = ad.selectQuery("select * from links where status is null");
								
								while(rs3.next()) {
									linkIdList.add(rs3.getString("link_name"));
									linkValueList.add(rs3.getString("link_name"));
								}
								linksForm.setLinkIdList(linkIdList);
								linksForm.setLinkValueList(linkValueList);
								
								
								String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
								" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
								ResultSet rs2 = ad.selectQuery(sql1);
								
								ArrayList list=new ArrayList();
								
								LinksForm linksForm2=null;
								int count=0;
								while (rs2.next()){
									
										linksForm2 = new LinksForm();
										linksForm2.setImageId(rs2.getString("id")+","+count);
										linksForm2.setImageName(rs2.getString("file_name"));
										linksForm2.setImageTitle(rs2.getString("file_description"));
										list.add(linksForm2);
										count++;
									
								}
								request.setAttribute("listName", list);
								
									
									ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
											"and status=1 and sub_linkname=link_name");
									ArrayList subLinkIdList = new ArrayList();
									ArrayList subLinkValueList = new ArrayList();
									FckEditorForm fckEditorForm1 = null;
									while(rs4.next()) {
										subLinkIdList.add(rs4.getString("link_name"));
										subLinkValueList.add(rs4.getString("link_name"));
									}
									linksForm.setSubLinkIdList(subLinkIdList);
									linksForm.setSubLinkValueList(subLinkValueList);
									linksForm.setLinkName(mainLinkName);
									request.setAttribute("displaySublinkField", "displaySublinkField");
									request.setAttribute("submitButton", "submitButton");
									 			
								
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("addbutton", "addbutton");
		return mapping.findForward("displayCmsLinks");
	}
	
	public ActionForward firstTenRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;
		try{
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			
			String id=request.getParameter("id");
			Date d = new Date();
			
			if(contentYear==null){
				
				
				contentYear=String.valueOf(1900 + d.getYear());
				
				
			}
			if(contentYear!=null){
				if(contentYear.equalsIgnoreCase("")){
					contentYear=String.valueOf(1900 + d.getYear());
				}
			}
			
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
	
			ResultSet rs=ad.selectQuery(sql);
			
			
			
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);	
					int totalRecords=linksForm.getTotalRecords();//21
					int startRecord=linksForm.getStartRecord();//11
					int endRecord=linksForm.getEndRecord();	
					
					
					if(totalRecords>10){
						  startRecord=1;
						  endRecord=10;
						  linksForm.setTotalRecords(totalRecords);
						  linksForm.setStartRecord(startRecord);
						  linksForm.setEndRecord(10);
						  }
						  else{
							  startRecord=1;
							  linksForm.setTotalRecords(totalRecords);
							  linksForm.setStartRecord(startRecord);
							  linksForm.setEndRecord(totalRecords);  
						  }

					String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
					"  From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
					String searchType=linksForm.getSerchType();
					if(!searchType.equalsIgnoreCase(""))
					{
					sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
					"  From  cms_sublinks as c where main_linkname like '%"+searchType+"%' or sub_linkname like '%"+searchType+"%' or content_year  like '%"+searchType+"%' or link_name  like '%"+searchType+"%') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
					}
				 // String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
						
						
						
						ArrayList listOfMaterialCode=new ArrayList();
						LinksForm linksForm1=null;
						
						ResultSet rs1=ad.selectQuery(sql2);
						while(rs1.next()) {
							linksForm1=new LinksForm();
							
							linksForm1.setRowno(rs1.getInt("RowNum"));
							linksForm1.setCmsLinkId(rs1.getString("id"));
							linksForm1.setLinkName(rs1.getString("main_linkname"));
							linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
							linksForm1.setSubLinkTitle(rs1.getString("link_name"));
							linksForm1.setContentYear(rs1.getString("content_year"));
							linksForm1.setIconName(rs1.getString("icon_name"));
							listOfMaterialCode.add(linksForm1);
						}
						
						
						request.setAttribute("CmsLinkDetails", listOfMaterialCode);
						 if(totalRecords>10)
							{
								request.setAttribute("nextButton", "nextButton");
							}
						
							request.setAttribute("disablePreviousButton", "disablePreviousButton");
							
							request.setAttribute("displayRecordNo", "displayRecordNo");
							request.setAttribute("materialCodeList","materialCodeList");
							// set links and sub links
							 
							 ResultSet rs3 = ad.selectQuery("select * from links where status is null");
								
								while(rs3.next()) {
									linkIdList.add(rs3.getString("link_name"));
									linkValueList.add(rs3.getString("link_name"));
								}
								linksForm.setLinkIdList(linkIdList);
								linksForm.setLinkValueList(linkValueList);
								
								
								String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
								" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
								ResultSet rs2 = ad.selectQuery(sql1);
								
								ArrayList list=new ArrayList();
								
								LinksForm linksForm2=null;
								int count=0;
								while (rs2.next()){
									
										linksForm2 = new LinksForm();
										linksForm2.setImageId(rs2.getString("id")+","+count);
										linksForm2.setImageName(rs2.getString("file_name"));
										linksForm2.setImageTitle(rs2.getString("file_description"));
										list.add(linksForm2);
										count++;
									
								}
								request.setAttribute("listName", list);
								
									
									ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
											"and status=1 and sub_linkname=link_name");
									ArrayList subLinkIdList = new ArrayList();
									ArrayList subLinkValueList = new ArrayList();
									FckEditorForm fckEditorForm1 = null;
									while(rs4.next()) {
										subLinkIdList.add(rs4.getString("link_name"));
										subLinkValueList.add(rs4.getString("link_name"));
									}
									linksForm.setSubLinkIdList(subLinkIdList);
									linksForm.setSubLinkValueList(subLinkValueList);
									linksForm.setLinkName(mainLinkName);
									request.setAttribute("displaySublinkField", "displaySublinkField");
									request.setAttribute("submitButton", "submitButton");
									 			
								
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("addbutton", "addbutton");
		return mapping.findForward("displayCmsLinks");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;
		try{
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			
			String id=request.getParameter("id");
			Date d = new Date();
			
			if(contentYear==null){
				
				
				contentYear=String.valueOf(1900 + d.getYear());
				
				
			}
			if(contentYear!=null){
				if(contentYear.equalsIgnoreCase("")){
					contentYear=String.valueOf(1900 + d.getYear());
				}
			}
			
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
	
			ResultSet rs=ad.selectQuery(sql);
			
			
			
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);	
			
			
			System.out.println("Start Record="+linksForm.getStartRecord());
			System.out.println("End record="+linksForm.getEndRecord());
			System.out.println("Total Record="+linksForm.getTotalRecords());	
			int totalRecords=linksForm.getTotalRecords();//21
			int endRecord=linksForm.getStartRecord()-1;//20
			int startRecord=linksForm.getStartRecord()-10;//11
			
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}	
			linksForm.setTotalRecords(totalRecords);
			linksForm.setStartRecord(1);
			linksForm.setEndRecord(10);
			
			String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
			"  From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";

			String searchType=linksForm.getSerchType();
			if(!searchType.equalsIgnoreCase(""))
			{
									sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
									"  From  cms_sublinks as c where main_linkname like '%"+searchType+"%' or sub_linkname like '%"+searchType+"%' or content_year  like '%"+searchType+"%' or link_name  like '%"+searchType+"%') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
			}
		 // String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
				
				
				
				ArrayList listOfMaterialCode=new ArrayList();
				LinksForm linksForm1=null;
				
				ResultSet rs1=ad.selectQuery(sql2);
				while(rs1.next()) {
					linksForm1=new LinksForm();
					
					linksForm1.setRowno(rs1.getInt("RowNum"));
					linksForm1.setCmsLinkId(rs1.getString("id"));
					linksForm1.setLinkName(rs1.getString("main_linkname"));
					linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
					linksForm1.setSubLinkTitle(rs1.getString("link_name"));
					linksForm1.setContentYear(rs1.getString("content_year"));
					linksForm1.setIconName(rs1.getString("icon_name"));
					listOfMaterialCode.add(linksForm1);
				}
				
				
				request.setAttribute("CmsLinkDetails", listOfMaterialCode);	
				
				endRecord=endRecord;
				linksForm.setTotalRecords(totalRecords);
				linksForm.setStartRecord(startRecord);
				linksForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(listOfMaterialCode.size()<10)
				{
					linksForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}		
		
				
				// set links and sub links
				 
				 ResultSet rs3 = ad.selectQuery("select * from links where status is null");
					
					while(rs3.next()) {
						linkIdList.add(rs3.getString("link_name"));
						linkValueList.add(rs3.getString("link_name"));
					}
					linksForm.setLinkIdList(linkIdList);
					linksForm.setLinkValueList(linkValueList);
					
					
					String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
					ResultSet rs2 = ad.selectQuery(sql1);
					
					ArrayList list=new ArrayList();
					
					LinksForm linksForm2=null;
					int count=0;
					while (rs2.next()){
						
							linksForm2 = new LinksForm();
							linksForm2.setImageId(rs2.getString("id")+","+count);
							linksForm2.setImageName(rs2.getString("file_name"));
							linksForm2.setImageTitle(rs2.getString("file_description"));
							list.add(linksForm2);
							count++;
						
					}
					request.setAttribute("listName", list);
					
						
						ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
								"and status=1 and sub_linkname=link_name");
						ArrayList subLinkIdList = new ArrayList();
						ArrayList subLinkValueList = new ArrayList();
						FckEditorForm fckEditorForm1 = null;
						while(rs4.next()) {
							subLinkIdList.add(rs4.getString("link_name"));
							subLinkValueList.add(rs4.getString("link_name"));
						}
						linksForm.setSubLinkIdList(subLinkIdList);
						linksForm.setSubLinkValueList(subLinkValueList);
						linksForm.setLinkName(mainLinkName);
						request.setAttribute("displaySublinkField", "displaySublinkField");
						request.setAttribute("submitButton", "submitButton");
						 			
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("displayRecordNo", "displayRecordNo");
		request.setAttribute("addbutton", "addbutton");
		return mapping.findForward("displayCmsLinks");
	}
	
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;
		try{
			System.out.println("Start Record="+linksForm.getStartRecord());
			System.out.println("End record="+linksForm.getEndRecord());
			System.out.println("Total Record="+linksForm.getTotalRecords());
			int totalRecords=linksForm.getTotalRecords();//21
			int startRecord=linksForm.getStartRecord();//11
			int endRecord=linksForm.getEndRecord();//20
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			
			String id=request.getParameter("id");
			Date d = new Date();
			
			if(contentYear==null){
				
				
				contentYear=String.valueOf(1900 + d.getYear());
				
				
			}
			if(contentYear!=null){
				if(contentYear.equalsIgnoreCase("")){
					contentYear=String.valueOf(1900 + d.getYear());
				}
			}
			
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
	
			ResultSet rs=ad.selectQuery(sql);
			
			
			
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);
			
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
					
					  String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
						"  From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
String searchType=linksForm.getSerchType();
if(!searchType.equalsIgnoreCase(""))
{
						sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
						"  From  cms_sublinks as c where main_linkname like '%"+searchType+"%' or sub_linkname like '%"+searchType+"%' or content_year  like '%"+searchType+"%' or link_name  like '%"+searchType+"%') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
}
					 // String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
							
							
							
							ArrayList listOfMaterialCode=new ArrayList();
							LinksForm linksForm1=null;
							
							ResultSet rs1=ad.selectQuery(sql2);
							while(rs1.next()) {
								linksForm1=new LinksForm();
								
								linksForm1.setRowno(rs1.getInt("RowNum"));
								linksForm1.setCmsLinkId(rs1.getString("id"));
								linksForm1.setLinkName(rs1.getString("main_linkname"));
								linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
								linksForm1.setSubLinkTitle(rs1.getString("link_name"));
								linksForm1.setContentYear(rs1.getString("content_year"));
								linksForm1.setIconName(rs1.getString("icon_name"));
								listOfMaterialCode.add(linksForm1);
							}
							
							
							request.setAttribute("CmsLinkDetails", listOfMaterialCode);	
							if(listOfMaterialCode.size()!=0)
							{
								linksForm.setTotalRecords(totalRecords);
								linksForm.setStartRecord(startRecord);
								linksForm.setEndRecord(endRecord);
								request.setAttribute("nextButton", "nextButton");
								request.setAttribute("previousButton", "previousButton");
							}
							else
							{
								int start=startRecord;
								int end=startRecord;
								
								linksForm.setTotalRecords(totalRecords);
								linksForm.setStartRecord(start);
								linksForm.setEndRecord(end);
								
							}
						 if(listOfMaterialCode.size()<10)
						 {
							 linksForm.setTotalRecords(totalRecords);
							 linksForm.setStartRecord(startRecord);
							 linksForm.setEndRecord(startRecord+listOfMaterialCode.size()-1);
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
						  
							
							request.setAttribute("previousButton", "previousButton");
				}
			 
		// set links and sub links
			 
			 ResultSet rs3 = ad.selectQuery("select * from links where status is null");
				
				while(rs3.next()) {
					linkIdList.add(rs3.getString("link_name"));
					linkValueList.add(rs3.getString("link_name"));
				}
				linksForm.setLinkIdList(linkIdList);
				linksForm.setLinkValueList(linkValueList);
				
				
				String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
				" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
				ResultSet rs2 = ad.selectQuery(sql1);
				
				ArrayList list=new ArrayList();
				
				LinksForm linksForm2=null;
				int count=0;
				while (rs2.next()){
					
						linksForm2 = new LinksForm();
						linksForm2.setImageId(rs2.getString("id")+","+count);
						linksForm2.setImageName(rs2.getString("file_name"));
						linksForm2.setImageTitle(rs2.getString("file_description"));
						list.add(linksForm2);
						count++;
					
				}
				request.setAttribute("listName", list);
				
					
					ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
							"and status=1 and sub_linkname=link_name");
					ArrayList subLinkIdList = new ArrayList();
					ArrayList subLinkValueList = new ArrayList();
					FckEditorForm fckEditorForm1 = null;
					while(rs4.next()) {
						subLinkIdList.add(rs4.getString("link_name"));
						subLinkValueList.add(rs4.getString("link_name"));
					}
					linksForm.setSubLinkIdList(subLinkIdList);
					linksForm.setSubLinkValueList(subLinkValueList);
					linksForm.setLinkName(mainLinkName);
					request.setAttribute("displaySublinkField", "displaySublinkField");
					request.setAttribute("submitButton", "submitButton");
					 
					request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("addbutton", "addbutton");
		return mapping.findForward("displayCmsLinks");
	}
	
	public ActionForward deleteModifyLinkImageFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
		
		String[] deleteArray=linksForm.getSelect1();
		String[] imageTitle1=linksForm.getImageTitle1();
		
		
		String contentYear=linksForm.getContentYear();
		
		String param=request.getParameter("param");
		
		try{
			LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			//String deleteArray[] = linksForm.getSelect();
			String selected = "";
			String id="";
			int a =0;
			
			Date d=new Date();
			   ArrayList years = new ArrayList();
		  	   int pyear = 1900 + d.getYear();
			
			for(int i=pyear;i>=1950;i--){
				years.add(i);
			}
			
			linksForm.setYears(years);
			
			if (deleteArray != null) {
				for(int i=0; i<deleteArray.length; i++) {
					
					id=deleteArray[i];
					System.out.println("Getting a Title is *************"+id);
					
					selected += "'" + deleteArray[i] + "'";
					System.out.println("Getting a Title is *************"+imageTitle1[i]);
					
					if(param.equalsIgnoreCase("Modify")){
						 String deletesql = "update cms_linksfilelist set file_description='"+imageTitle1[Integer.parseInt(id.split(",")[1])]+"' where id ='"+id.split(",")[0]+"'";
						 a = adlinks.SqlExecuteUpdate(deletesql);
					}
					if(param.equalsIgnoreCase("Delete")){
						 String deletesql = "delete from cms_linksfilelist where id ='"+id.split(",")[0]+"'";
						 a = adlinks.SqlExecuteUpdate(deletesql);
					}
					
				}
				
			}
			
			if(a>0){
				linksForm.setMessage("Files "+param+"ied Successfully");
			}else{
				linksForm.setMessage("Error While "+param+"ing Files ...");
			}
			ArrayList list = new ArrayList();
			
			
			ResultSet rs5 = adlinks.selectQuery("select id,file_name,file_description from cms_linksfilelist where main_linkname='"+linksForm.getLinkName()+"' and " +
	 		"sub_linkname='"+linksForm.getSubLinkName()+"' and link_name='"+linksForm.getSubLinkTitle()+"'");
			int count1=0;
			LinksForm linksForm1=null;
			while (rs5.next()) {
				
				linksForm1 = new LinksForm();
				linksForm1.setImageId(rs5.getString("id")+","+count1);
				linksForm1.setImageName(rs5.getString("file_name").substring(rs5.getString("file_name").lastIndexOf("/")+1, rs5.getString("file_name").length()));
				linksForm1.setImageTitle(rs5.getString("file_description"));
				list.add(linksForm1);
				count1++;
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			
			
			ResultSet rs6 = adlinks.selectQuery("select * from cms_sublinks where main_linkname='"+linksForm.getLinkName()+"' and " +
			 		"sub_linkname='"+linksForm.getSubLinkName()+"' and link_name='"+linksForm.getSubLinkTitle()+"'");
					
					
					while (rs6.next()) {
						String content_description="";
						content_description=rs6.getString("content_description");
						content_description=content_description.replaceAll("'", "''");
						linksForm.setContentDescriptionAdmin(content_description);
					}
					
			
			ResultSet rs = adlinks.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			
			linksForm.setLinkName(linksForm.getLinkName());
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			linksForm.setSubLinkName(linksForm.getSubLinkName());
			linksForm.setSubLinkTitle(linksForm.getSubLinkTitle());
			
			
			String sql1="select * from cms_linksfilelist where main_linkname='"+linksForm.getLinkName()+"' and " +
			"sub_linkname='"+linksForm.getSubLinkName()+"' and link_name='"+linksForm.getSubLinkTitle()+"'";
			
			ResultSet rs1=adlinks.selectQuery(sql1);
			
			
			ArrayList a1=new ArrayList();
			LinksForm linksForm2=null;
			int count=0;
			while (rs1.next()) {
				linksForm2=new LinksForm();
				linksForm2.setImageId(rs1.getString("id")+","+count);
				linksForm2.setImageTitle(rs1.getString("file_description"));
				linksForm2.setImageName(rs1.getString("file_name").substring(rs1.getString("file_name").lastIndexOf("/")+1, rs1.getString("file_name").length()));
				a1.add(linksForm2);
				count++;
			}
			
			linksForm.setContentYear(contentYear);
			
			request.setAttribute("ImageDetails", a1);
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception caught =" + e.getMessage());
			}
		
		
	
		
		
		return mapping.findForward("displayCmsLinksModify");
	}
	
	
	
	
	
	public ActionForward deleteLinkImageFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
		
		String[] deleteArray=linksForm.getSelect1();
		String[] imageTitle1=linksForm.getImageTitle1();
		
		String param=request.getParameter("param");
		
		try{
			LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			//String deleteArray[] = linksForm.getSelect();
			String selected = "";
			String id="";
			int a =0;
			
			
			Date d=new Date();
			   ArrayList years = new ArrayList();
		  	   int pyear = 1900 + d.getYear();
			
			for(int i=pyear;i>=1950;i--){
				years.add(i);
			}
			
			linksForm.setYears(years);
			
			
			if (deleteArray != null) {
				for(int i=0; i<deleteArray.length; i++) {
					
					id=deleteArray[i];
					System.out.println("Getting a Title is *************"+id);
					
					selected += "'" + deleteArray[i] + "'";
					System.out.println("Getting a Title is *************"+imageTitle1[i]);
					
					if(param.equalsIgnoreCase("Modify")){
						String deletesql = "update temp_filelist set file_description='"+imageTitle1[Integer.parseInt(id.split(",")[1])]+"' where id ='"+id.split(",")[0]+"'";
						 a = adlinks.SqlExecuteUpdate(deletesql);
					}
					if(param.equalsIgnoreCase("Delete")){
						String deletesql = "delete from temp_filelist where id ='"+id.split(",")[0]+"'";
						 a = adlinks.SqlExecuteUpdate(deletesql);
					}
					request.setAttribute("editFCKEditor", "editFCKEditor");
					
				}
				
			}
			
			if (a > 0) {
				
				linksForm.setMessage("Files "+param+"ied Successfully");
			} else {
				linksForm.setMessage("Error While "+param+"ing Files ...");
			}
			ArrayList list = new ArrayList();
			
			
			ResultSet rs5 = adlinks.selectQuery("select file_name from temp_filelist where main_linkname='"+linksForm.getLinkName()+"' and " +
	 		"sub_linkname='"+linksForm.getSubLinkName()+"' and link_name='"+linksForm.getSubLinkTitle()+"'");
			
			LinksForm linksForm1=null;
			while (rs5.next()) {

				linksForm1 = new LinksForm();
				linksForm1.setFileList(rs5.getString("file_name"));
				list.add(linksForm1);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			
			ResultSet rs = adlinks.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			
			linksForm.setLinkName(linksForm.getLinkName());
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			linksForm.setSubLinkName(linksForm.getSubLinkName());
			linksForm.setSubLinkTitle(linksForm.getSubLinkTitle());
			linksForm.setContentYear(linksForm.getContentYear());
			
			String sql1="select * from temp_filelist where main_linkname='"+linksForm.getLinkName()+"' and " +
			"sub_linkname='"+linksForm.getSubLinkName()+"' and link_name='"+linksForm.getSubLinkTitle()+"'";
			
			ResultSet rs1=adlinks.selectQuery(sql1);
			
			
			ArrayList a1=new ArrayList();
			LinksForm linksForm2=null;
			int count=0;
			while (rs1.next()) {
				linksForm2=new LinksForm();
				linksForm2.setImageId(rs1.getString("id")+","+count);
				linksForm2.setImageTitle(rs1.getString("file_description"));
				linksForm2.setImageName(rs1.getString("file_name").substring(rs1.getString("file_name").lastIndexOf("/")+1, rs1.getString("file_name").length()));
				a1.add(linksForm2);
				count++;
			}
			
			
			request.setAttribute("ImageDetails", a1);
			request.setAttribute("editDetails", "editDetails");
			Enumeration<String> params = request
			.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			content_description=content_description.replaceAll("'", "''");
			linksForm.setContentDescriptionAdmin(content_description);
		
		} catch (Exception e) {
			System.out.println("Exception caught =" + e.getMessage());
		}
		
		
	
		
		
		return mapping.findForward("displayCmsLinks");
	}
		
		
		public ActionForward display(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			String linkName = linksForm.getLinkName();
			String subLinkName = linksForm.getSubLinkName();
			String linkPath = linksForm.getLinkPath();
			String methodName = linksForm.getMethodName();
			String priority = linksForm.getPriority();
			
			try {
				LinksDao ad = new LinksDao();
				ResultSet rs = ad.selectQuery("select * from links where status is null");
				ArrayList linkIdList = new ArrayList();
				ArrayList linkValueList = new ArrayList();
				
				while(rs.next()) {
					linkIdList.add(rs.getString("link_name"));
					linkValueList.add(rs.getString("link_name"));
				}
				
				linksForm.setLinkName(linkName);
				linksForm.setLinkIdList(linkIdList);
				linksForm.setLinkValueList(linkValueList);
				linksForm.setSubLinkName(subLinkName);
				linksForm.setLinkPath(linkPath);
				linksForm.setMethodName(methodName);
				linksForm.setPriority(priority);
				linksForm.setContentDescription("");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception caught =" + e.getMessage());
			}
			
			
			String id = request.getParameter("id");
			if(id!=null){
			
			HRDao ad = new HRDao();
			String sql = "select * from links where module='" + id + "' and status=1 ";
			ResultSet rs = ad.selectQuery(sql);
			try {
				LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
				
				while(rs.next()) {
					hm.put(rs.getString("link_path")+"?method="+rs.getString("method") + "&sId="+rs.getString("id"), (rs.getString("link_name")+','+rs.getString("icon_name")));
				}
				session.setAttribute("SUBLINKS", hm);
			} catch (SQLException se) {
				se.printStackTrace();
			}
			
			}
			
			
			return mapping.findForward("display");
		}
		
		
		public ActionForward displayLinksPage(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			
			String linkName = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String linkPath="newsAndMedia.do";
			String method="display1";
			String priority=linksForm.getPriority();
			String id=linksForm.getLinkId();
			
			
			String id1=request.getParameter("id");
			
			if(id1==null){
				request.setAttribute("editDetails", "editDetails");
			}
			
			
			 Date d = new Date();
			   ArrayList years = new ArrayList();
		  	   int pyear = 1900 + d.getYear();
			
			for(int i=pyear;i>=1950;i--){
				years.add(i);
			}
			
			
			linksForm.setYears(years);	
			
			
			
			Enumeration<String> params = request.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			try{
			
			LinksDao ad = new LinksDao();
			ResultSet rs = ad.selectQuery("select * from links where status is null");
			
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			String sql2="select * from cms_sublinks where main_linkname='"+linkName+"' " +
					"and sub_linkname='"+sub_link_name+"'";
			
			ResultSet rs1=ad.selectQuery(sql2);
			
			ArrayList a1=new ArrayList();
			LinksForm linksForm1=null;
			while(rs1.next()) {
				linksForm1=new LinksForm();
				
				//linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setCmsLinkId(rs1.getString("link_name"));
				linksForm1.setLinkName(rs1.getString("main_linkname"));
				linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
				linksForm1.setSubLinkTitle(rs1.getString("link_name"));
				a1.add(linksForm1);
			}
			
			
			request.setAttribute("CmsLinkDetails", a1);
			}catch(SQLException se){
				se.printStackTrace();
			}
			linksForm.setLinkName(linkName);
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			
			
			return mapping.findForward("displayCmsLinks");
		}
		
		
		
		
		
		public ActionForward displayCmsSublinks(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			LinksForm linksForm = (LinksForm) form;// TODO
			String linkName = linksForm.getLinkName();
			try {
				LinksDao ad = new LinksDao();
				
				String sql="select * from links where module='"+linkName+"' " +
				"and status=1 and sub_linkname is null";
				
				ResultSet rs = ad.selectQuery("select * from links where module='"+linkName+"' " +
						"and status=1 and sub_linkname=link_name");
				ArrayList subLinkIdList = new ArrayList();
				ArrayList subLinkValueList = new ArrayList();
				FckEditorForm fckEditorForm1 = null;
				while(rs.next()) {
					subLinkIdList.add(rs.getString("link_name"));
					subLinkValueList.add(rs.getString("link_name"));
				}
				linksForm.setSubLinkIdList(subLinkIdList);
				linksForm.setSubLinkValueList(subLinkValueList);
				linksForm.setLinkName(linkName);
				request.setAttribute("displaySublinkField", "displaySublinkField");
				request.setAttribute("submitButton", "submitButton");
			} catch (Exception e) {
				e.printStackTrace();
			}
			display(mapping, form, request, response);
			return mapping.findForward("displayCmsLinks");
		}
		
		
		
		public ActionForward displayCmsSublinksImages(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			
			String id=request.getParameter("id");
			Date d = new Date();
			
			if(contentYear==null){
				
				
				contentYear=String.valueOf(1900 + d.getYear());
				
				
			}
			if(contentYear!=null){
				if(contentYear.equalsIgnoreCase("")){
					contentYear=String.valueOf(1900 + d.getYear());
				}
			}
			
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			try{
			ResultSet rs=ad.selectQuery(sql);
			
			
			
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);
					
	int  totalRecords=0;
	  int  startRecord=0;
	  int  endRecord=0;
	  
	  String getTotalRecords="select count(*) from cms_sublinks where main_linkname='"+mainLinkName+"' " +
					"and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
	  
	  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
	  while(rsTotalRecods.next())
	  {
		  totalRecords=rsTotalRecods.getInt(1);
	  }
	  
	  if(totalRecords>10)
	  {
		  linksForm.setTotalRecords(totalRecords);
	  startRecord=1;
	  endRecord=10;
	  linksForm.setStartRecord(1);
	  linksForm.setEndRecord(10);
	  request.setAttribute("displayRecordNo", "displayRecordNo");
	  request.setAttribute("nextButton", "nextButton");
	  }else
	  {
		  startRecord=1;
		  endRecord=totalRecords;
		  linksForm.setTotalRecords(totalRecords);
		  linksForm.setStartRecord(1);
		  linksForm.setEndRecord(totalRecords); 
	  }
	  
/*	  String getList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
		"From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";
*/
	
	  
	  String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name" +
		"  From  cms_sublinks as c where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'  ";

	 // String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
			
			ResultSet rs1=ad.selectQuery(sql2);
			
			ArrayList a1=new ArrayList();
			LinksForm linksForm1=null;
			
			
			while(rs1.next()) {
				linksForm1=new LinksForm();
				
				linksForm1.setRowno(rs1.getInt("RowNum"));
				linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setLinkName(rs1.getString("main_linkname"));
				linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
				linksForm1.setSubLinkTitle(rs1.getString("link_name"));
				linksForm1.setContentYear(rs1.getString("content_year"));
				linksForm1.setIconName(rs1.getString("icon_name"));
				a1.add(linksForm1);
			}
			
			
			request.setAttribute("CmsLinkDetails", a1);
			
			ResultSet rs3 = ad.selectQuery("select * from links where status is null");
			
			while(rs3.next()) {
				linkIdList.add(rs3.getString("link_name"));
				linkValueList.add(rs3.getString("link_name"));
			}
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			
			
			String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
			" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
			ResultSet rs2 = ad.selectQuery(sql1);
			
			ArrayList list=new ArrayList();
			
			LinksForm linksForm2=null;
			int count=0;
			while (rs2.next()){
				
					linksForm2 = new LinksForm();
					linksForm2.setImageId(rs2.getString("id")+","+count);
					linksForm2.setImageName(rs2.getString("file_name"));
					linksForm2.setImageTitle(rs2.getString("file_description"));
					list.add(linksForm2);
					count++;
				
			}
			request.setAttribute("listName", list);
			
				
				ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
						"and status=1 and sub_linkname=link_name");
				ArrayList subLinkIdList = new ArrayList();
				ArrayList subLinkValueList = new ArrayList();
				FckEditorForm fckEditorForm1 = null;
				while(rs4.next()) {
					subLinkIdList.add(rs4.getString("link_name"));
					subLinkValueList.add(rs4.getString("link_name"));
				}
				linksForm.setSubLinkIdList(subLinkIdList);
				linksForm.setSubLinkValueList(subLinkValueList);
				linksForm.setLinkName(mainLinkName);
				request.setAttribute("displaySublinkField", "displaySublinkField");
				request.setAttribute("submitButton", "submitButton");
			
				
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			request.setAttribute("addbutton", "addbutton");
			return mapping.findForward("displayCmsLinks");
		}
		
		
		public ActionForward selectYearCmsContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			String mainLinkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			String linkName=linksForm.getSubLinkTitle();
			
			
			String contentYear=linksForm.getContentYear();
			if(contentYear!=null)
			{
				request.setAttribute("editFCKEditor", "editFCKEditor");
			}
			
			String id=request.getParameter("id");
			String sid=request.getParameter("sid");
			Date d = new Date();
			
			if(contentYear==null && sid==null){
				contentYear=String.valueOf(1900 + d.getYear());
			}
			
			Enumeration<String> params = request
			.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			content_description=content_description.replaceAll("'", "''");
			linksForm.setContentDescriptionAdmin(content_description);
			String sql="select * from cms_sublinks where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' " +
					"and content_year='"+contentYear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			try{
			ResultSet rs=ad.selectQuery(sql);
			
			String sub_link_name="";
			
			
			if(rs.next()) {
				linksForm.setLinkName(rs.getString("main_linkname"));
				linksForm.setSubLinkName(rs.getString("sub_linkname"));
				sub_link_name=rs.getString("sub_linkname");
				linkName=rs.getString("link_name");
				linksForm.setSubLinkTitle(rs.getString("link_name"));
				
				mainLinkName=rs.getString("main_linkname");
				
				subLinkName=rs.getString("sub_linkname");
				
				if(rs.getString("archived_status").equalsIgnoreCase("0")){
					linksForm.setCmsLinkArchive("off");
				}
				if(rs.getString("archived_status").equalsIgnoreCase("1")){
					linksForm.setCmsLinkArchive("on");
				}
				
				linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				
				}else{
					
					
					/*linksForm.setMessage("Content Details does not Exists in this year.. you can add cont" +
							"ent to this year ");*/
					linksForm.setContentDescriptionAdmin("");
					 
					   ArrayList years = new ArrayList();
				  	   int pyear = 1900 + d.getYear();
					
					for(int i=pyear;i>=1950;i--){
						years.add(i);
					}
					
					linksForm.setYears(years);
					
					
					
					 int  totalRecords=0;
	  int  startRecord=0;
	  int  endRecord=0;		
	  String searchType=linksForm.getSerchType();
	  String getTotalRecords="select count(*) from cms_sublinks as c where  c.main_linkname='"+mainLinkName+"' and c.sub_linkname='"+subLinkName+"' and c.content_year='"+contentYear+"'";

ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
while(rsTotalRecods.next())
{
totalRecords=rsTotalRecods.getInt(1);
} 
					 
					 String sql2="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum, c.id,c.main_linkname,c.sub_linkname,c.link_name,c.content_year,c.icon_name,c.content_description" +
					" from cms_sublinks as c where c.main_linkname='"+mainLinkName+"' and c.sub_linkname='"+subLinkName+"' and c.content_year='"+contentYear+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+totalRecords+"'  ";
				
					 
					
					//String sql2="select * from cms_sublinks where main_linkname='"+mainLinkName+"' and sub_linkname='"+subLinkName+"' and content_year='"+contentYear+"'";
			
			ResultSet rs1=ad.selectQuery(sql2);
			
			ArrayList a1=new ArrayList();
			LinksForm linksForm1=null;
			
			
			while(rs1.next()) {
				linksForm1=new LinksForm();
				
				linksForm1.setRowno(rs1.getInt("RowNum"));
				linksForm1.setCmsLinkId(rs1.getString("id"));
				linksForm1.setLinkName(rs1.getString("main_linkname"));
				linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
				linksForm1.setSubLinkTitle(rs1.getString("link_name"));
				linksForm1.setContentYear(rs1.getString("content_year"));
				linksForm1.setIconName(rs1.getString("icon_name"));
				
				linksForm1.setContentDescriptionAdmin(rs1.getString("content_description"));
				a1.add(linksForm1);
			}
			
			
			request.setAttribute("CmsLinkDetails", a1);
			
			ResultSet rs3 = ad.selectQuery("select * from links where status is null");
			
			while(rs3.next()) {
				linkIdList.add(rs3.getString("link_name"));
				linkValueList.add(rs3.getString("link_name"));
			}
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			
			
			String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
			" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
			ResultSet rs2 = ad.selectQuery(sql1);
			
			ArrayList list=new ArrayList();
			
			LinksForm linksForm2=null;
			int count=0;
			while (rs2.next()){
				
					linksForm2 = new LinksForm();
					linksForm2.setImageId(rs2.getString("id")+","+count);
					linksForm2.setImageName(rs2.getString("file_name").substring(rs2.getString("file_name").lastIndexOf("/")+1, rs2.getString("file_name").length()));
					
					linksForm2.setImageTitle(rs2.getString("file_description"));
					list.add(linksForm2);
					count++;
				
			}
			request.setAttribute("listName", list);
			
				
				ResultSet rs4 = ad.selectQuery("select * from links where module='"+mainLinkName+"' " +
						"and status=1 and sub_linkname=link_name");
				ArrayList subLinkIdList = new ArrayList();
				ArrayList subLinkValueList = new ArrayList();
				FckEditorForm fckEditorForm1 = null;
				while(rs4.next()) {
					subLinkIdList.add(rs4.getString("link_name"));
					subLinkValueList.add(rs4.getString("link_name"));
				}
				linksForm.setSubLinkIdList(subLinkIdList);
				linksForm.setSubLinkValueList(subLinkValueList);
				linksForm.setLinkName(mainLinkName);
				request.setAttribute("displaySublinkField", "displaySublinkField");
				request.setAttribute("submitButton", "submitButton");
			
				request.setAttribute("editDetails", "editDetails");
				if(totalRecords==0)
				{
					linksForm.setContentDescriptionAdmin(" ");
				}
				
			return mapping.findForward("displayCmsLinks");
					
			}
			
			 
			   ArrayList years = new ArrayList();
		  	   int pyear = 1900 + d.getYear();
			
			for(int i=pyear;i>=1950;i--){
				years.add(i);
			}
			
			linksForm.setYears(years);
			
			String sql2="select * from cms_sublinks where main_linkname='"+linkName+"' " +
			"and sub_linkname='"+sub_link_name+"'";
	
	ResultSet rs1=ad.selectQuery(sql2);
	
	ArrayList a1=new ArrayList();
	LinksForm linksForm1=null;
	
	
	
	while(rs1.next()) {
		linksForm1=new LinksForm();
		
		
		linksForm1.setCmsLinkId(rs1.getString("id"));
		linksForm1.setLinkName(rs1.getString("main_linkname"));
		linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
		linksForm1.setSubLinkTitle(rs1.getString("link_name"));
		a1.add(linksForm1);
	}
	
	
	request.setAttribute("CmsLinkDetails", a1);
			
			ResultSet rs3 = ad.selectQuery("select * from links where status is null");
			
			
			while(rs3.next()) {
				linkIdList.add(rs3.getString("link_name"));
				linkValueList.add(rs3.getString("link_name"));
			}
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			
			
			ArrayList list=new ArrayList();
			
			
			String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and content_year='"+contentYear+"'";
			ResultSet rs2 = ad.selectQuery(sql1);
			
			
			LinksForm linksForm2=null;
			int count=0;
			while (rs2.next()){
				
					linksForm2 = new LinksForm();
					linksForm2.setImageId(rs2.getString("id")+","+count);
					
					linksForm2.setImageName(rs2.getString("file_name").substring(rs2.getString("file_name").lastIndexOf("/")+1, rs2.getString("file_name").length()));

					linksForm2.setImageTitle(rs2.getString("file_description"));
					list.add(linksForm2);
					count++;
				
			}
			request.setAttribute("listName", list);
			
			
			
			
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			request.setAttribute("displayfckeditor", "displayfckeditor");
			return mapping.findForward("displayCmsLinksModify");
		}
		
		
		public ActionForward selectCmsContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			
			
			String id=request.getParameter("id");
			
			 Date d = new Date();
			   ArrayList years = new ArrayList();
		  	   int pyear = 1900 + d.getYear();
			
			
			String sql="select * from cms_sublinks where id='"+id+"' and" +
					" content_year='"+pyear+"'";
			
			LinksDao ad=new LinksDao();
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			try{
			ResultSet rs=ad.selectQuery(sql);
			
			
			String linkName="";
			String sub_link_name="";
			String mainLinkName="";
			String subLinkName="";
			while (rs.next()) {
				linksForm.setLinkName(rs.getString("main_linkname"));
				linksForm.setSubLinkName(rs.getString("sub_linkname"));
				sub_link_name=rs.getString("sub_linkname");
				linkName=rs.getString("link_name");
				linksForm.setSubLinkTitle(rs.getString("link_name"));
				
				mainLinkName=rs.getString("main_linkname");
				
				subLinkName=rs.getString("sub_linkname");
				
				
				if(rs.getString("archived_status").equalsIgnoreCase("0")){
					linksForm.setCmsLinkArchive("off");
				}
				if(rs.getString("archived_status").equalsIgnoreCase("1")){
					linksForm.setCmsLinkArchive("on");
				}
				
				linksForm.setContentDescriptionAdmin(rs.getString("content_description"));
				
				}
			
			
			for(int i=pyear;i>=1950;i--){
				years.add(i);
			}
			
			linksForm.setYears(years);
			
			String sql2="select * from cms_sublinks where main_linkname='"+linkName+"' " +
			"and sub_linkname='"+sub_link_name+"' ";
	
	ResultSet rs1=ad.selectQuery(sql2);
	
	ArrayList a1=new ArrayList();
	LinksForm linksForm1=null;
	
	
	while(rs1.next()) {
		linksForm1=new LinksForm();
		
		linksForm1.setCmsLinkId(rs1.getString("id"));
		linksForm1.setLinkName(rs1.getString("main_linkname"));
		linksForm1.setSubLinkName(rs1.getString("sub_linkname"));
		linksForm1.setSubLinkTitle(rs1.getString("link_name"));
		a1.add(linksForm1);
	}
			
			request.setAttribute("CmsLinkDetails", a1);
			
			ResultSet rs3 = ad.selectQuery("select * from links where status is null");
			
			
			while(rs3.next()) {
				linkIdList.add(rs3.getString("link_name"));
				linkValueList.add(rs3.getString("link_name"));
			}
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			
			
			ArrayList list=new ArrayList();
			
			String sql1="select * from cms_linksfilelist where main_linkname='"+mainLinkName+"'" +
					" and sub_linkname='"+subLinkName+"' and link_name='"+linkName+"' and  content_year='"+pyear+"'";
			ResultSet rs2 = ad.selectQuery(sql1);
			
			
			LinksForm linksForm2=null;
			int count=0;
			while (rs2.next()){
					
					linksForm2 = new LinksForm();
					linksForm2.setImageId(rs2.getString("id")+","+count);
					linksForm2.setImageName(rs2.getString("file_name").substring(rs2.getString("file_name").lastIndexOf("/")+1, rs2.getString("file_name").length()));
					
					linksForm2.setImageTitle(rs2.getString("file_description"));
					list.add(linksForm2);
					count++;
			}
			request.setAttribute("listName", list);
			
			linksForm.setContentYear(String.valueOf(pyear));
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			return mapping.findForward("displayCmsLinksModify");
		}
		
		
		
		public ActionForward uploadGalleryImage(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			
			LinksDao ad=new LinksDao();
			
			String linkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			
			String imageTitle=linksForm.getImageTitle();
			
			String subLinkTitle=linksForm.getSubLinkTitle();
			String contentYear=linksForm.getContentYear();
			
			
			FormFile imageGalleryFile=linksForm.getImageGalleryFile();
			
			String contentType = imageGalleryFile.getContentType();
			String fileName = imageGalleryFile.getFileName();
			try {
				byte[] fileData = imageGalleryFile.getFileData();
				
				
				
				String filePath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/cms/About Company")+"/"+subLinkName+"" +
						"/links/"+subLinkTitle+"/images/"+contentYear;
				
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
			 	 filePath=uploadFilePath+"/EMicro Files/cms/About Company/"+subLinkName+"/links/"+subLinkTitle+"/images/"+contentYear;
				
				File imageGalleryDir = new File(filePath);
				
				
				if(!imageGalleryDir.exists())
				{
					imageGalleryDir.mkdirs();
				}
			
			if (!fileName.equals("")) {
				File fileToCreate = new File(filePath, fileName);
				if (!fileToCreate.exists()) {
					FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
					fileOutStream.write(imageGalleryFile.getFileData());
					fileOutStream.flush();
					fileOutStream.close();
				}
				
			}
			
			
			
			
			
			 Date d = new Date();
			   ArrayList years = new ArrayList();
		  	   int pyear = 1900 + d.getYear();
			
			for(int i=pyear;i>=1950;i--){
				years.add(i);
			}
			
			
			linksForm.setYears(years);	
			
			ResultSet rs = ad.selectQuery("select * from links where status is null");
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			linksForm.setLinkName(linkName);
			linksForm.setLinkIdList(linkIdList);
			linksForm.setLinkValueList(linkValueList);
			
			linksForm.setContentYear(contentYear);
			 filePath=uploadFilePath+"/EMicro Files/cms/About Company/"+subLinkName+"/links/"+subLinkTitle+"/images/"+contentYear+"/";
			String sql="insert into temp_filelist(main_linkname,sub_linkname,link_name,file_name,file_description)" +
					"values('"+linkName+"','"+subLinkName+"','"+subLinkTitle+"','"+(filePath+fileName)+"','"+imageTitle+"')";
			
			int a= ad.SqlExecuteUpdate(sql);
			
			
			if(a>0){
				linksForm.setImageTitle("");
				linksForm.setMessage("Image Inserted  Successfully");
				request.setAttribute("editFCKEditor", "editFCKEditor");
			}
			
			String sql1="select * from temp_filelist where main_linkname='"+linkName+"' and " +
					"sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"'";
			
			ResultSet rs1=ad.selectQuery(sql1);
			
			
			ArrayList a1=new ArrayList();
			LinksForm linksForm1=null;
			int count=0;
			while (rs1.next()) {
				linksForm1=new LinksForm();
				linksForm1.setImageId(rs1.getString("id")+","+count);
				linksForm1.setImageTitle(rs1.getString("file_description"));
				linksForm1.setImageName(rs1.getString("file_name").substring(rs1.getString("file_name").lastIndexOf("/")+1, rs1.getString("file_name").length()));
				a1.add(linksForm1);
				count++;
			}
			
			
			request.setAttribute("ImageDetails", a1);
			request.setAttribute("editDetails", "editDetails");
			
			Enumeration<String> params = request
			.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			
			linksForm.setContentDescriptionAdmin(content_description);
			
			} catch (FileNotFoundException e) {
				linksForm.setMessage("Error While Uploading Image File");
				e.printStackTrace();
			} catch (IOException e) {
				linksForm.setMessage("Error While Uploading Image File");
				e.printStackTrace();
			}catch (SecurityException se){
				linksForm.setMessage("Error While Uploading Image File");
				se.printStackTrace();
			}catch (SQLException se){
				se.printStackTrace();
			}
			
			
			return mapping.findForward("displayCmsLinks");	
		}
		
		
		public ActionForward submit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			String linkName = linksForm.getLinkName();
			String subLinkName = linksForm.getSubLinkName();
			
			String linkPath = "newsAndMedia.do";
			String methodName = "display1";
			
			
			String priority = linksForm.getPriority();
			HttpSession session = request.getSession();
			Enumeration<String> params = request
			.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
	
	
	
	
			try {
				LinksDao ad = new LinksDao();
				
				FormFile myFile = linksForm.getIconNames();
			    String contentType = myFile.getContentType();
				String iconName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
				   if(ext.equalsIgnoreCase("jpeg")||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("jpg"))||(ext.equalsIgnoreCase("gif")))
				   {
				String iconPath = getServlet().getServletContext().getRealPath("/jsp/EMicro Files/cms")+"/"+linkName+"/"+subLinkName+"/UploadIcons";
				File destinationDir = new File(iconPath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!iconName.equals("")) {
					File fileToCreate = new File(iconPath, iconName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				
				
				
				 
				
				
				
				
				
				request.setAttribute("iconName", iconName);
				iconPath = iconPath.replace("\\", "\\\\");
			  }
				String iconList="/jsp/EMicro Files/cms"+"/"+linkName+"/"+subLinkName+"/UploadIcons"+"/"+iconName;
				
				String fileList="";
				ResultSet rs4 = ad.selectQuery("SELECT * FROM filelist where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ subLinkName
								+ "'");
				while (rs4.next()) {
					fileList+="/jsp/EMicro Files/cms"+"/"+linkName+"/"+subLinkName+"/UploadFiles"+"/"+ rs4.getString("file_name")+",";
				}
				String videoList="";
				ResultSet rs5 = ad.selectQuery("SELECT * FROM video_list where link_name='"
								+ linkName
								+ "' and sub_link_name='"
								+ subLinkName							
								+ "'");
				while (rs5.next()) {
					videoList+="/jsp/EMicro Files/cms"+"/"+linkName+"/"+subLinkName+"/UploadVideos"+"/"+rs5.getString("video_name")+",";
				}
				if(!fileList.equalsIgnoreCase("")){
					fileList=fileList.substring(0, fileList.lastIndexOf(","));
				}
				if(!videoList.equalsIgnoreCase("")){
					videoList=videoList.substring(0, videoList.lastIndexOf(","));
				}
				
				
				String sql = "insert into links(module,link_name," +
						"link_path,method,priority,status,delete_status,content_description,file_name,video_name,icon_name,sub_linkname) values('"
					+ linkName
					+ "','"
					+ subLinkName
					+ "','"
					+ linkPath
					+ "','"
					+ methodName+"',"+priority+",1,1,'"+content_description+"','"+fileList+"','"+videoList+"','"+iconList+"','"+subLinkName+"')";
						
					
		int a=ad.SqlExecuteUpdate(sql);
		 if (a>0) {
			 linksForm.setMessage("Admin Fck Editor Details Submitted  Successfully");
			 String deletesql = "delete from filelist where link_name='"
					+ linkName + "' and sub_link_name='" + subLinkName+ "'";
			String deletesql1 = "delete from video_list where link_name='"
					+ linkName + "' and sub_link_name='" + subLinkName+ "'";
			
			ad.SqlExecuteUpdate(deletesql);
			ad.SqlExecuteUpdate(deletesql1);
			
			linksForm.setLinkName("");
			linksForm.setSubLinkName("");
			linksForm.setUnderSubLinks("");
			linksForm.setLinkPath("");
			linksForm.setMethodName("");
			linksForm.setPriority("");
		 } 
		 else 
		 {
			 linksForm.setMessage("Error While  Adding Admin Fck Editor Details.. Please check Entered Values");
		 }			
			} catch (Exception e) {
				e.printStackTrace();
			}
			display(mapping, form, request, response);
			return mapping.findForward("display");
		}
		
		
		public ActionForward displaySublinks(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			String linkName = linksForm.getLinkName();
			try {
				LinksDao ad = new LinksDao();
				ResultSet rs = ad.selectQuery("select * from links where module='"
								+linkName+"'  and delete_status=1 " );
				ArrayList a1 = new ArrayList();
				LinksForm linksForm1 = null;
				while (rs.next()) {
					linksForm1 = new LinksForm();
					linksForm1.setLinkName(linkName);
					linksForm1.setSubLinkName(rs.getString("link_name"));
					linksForm1.setSubLinkId(rs.getString("link_name"));
					linksForm1.setMethodName(rs.getString("method"));
					linksForm1.setPriority(rs.getString("priority"));
					a1.add(linksForm1);
				}
				linksForm.setLinkName(linkName);
				request.setAttribute("listDetails", a1);
				request.setAttribute("displaySublinkField", "displaySublinkField");
				request.setAttribute("submitButton", "submitButton");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			HttpSession session=request.getSession();
			
			LinkedHashMap<String, String> hm= (LinkedHashMap<String, String>)session.getAttribute("SUBLINKS");
			
			
			session.setAttribute("SUBLINKS", hm);
			
			
			display(mapping, form, request, response);
			return mapping.findForward("display");
		}
		
		
		public ActionForward displayLinksContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
	    {
			System.out.println("displayLinksContent()----");
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			
			
			String linksId = request.getParameter("sId");
			String linkName = linksForm.getLinkName();
			System.out.println("Link Name="+linkName);
			
			
			linksForm.setLinkName(linkName);
			LinksDao adlinks = new LinksDao();
			HttpSession session=request.getSession();
			try 
			{
				LinksDao ad = new LinksDao();
				if(linkName.equalsIgnoreCase("Login Help")){
				
					
					String sql="select * from links where link_name='"+linkName + "' and " +
					"status is null  ";
			System.out.println("sql="+sql);
			ResultSet rs = ad.selectQuery(sql);
			String linkId=null;
			while (rs.next()) {
				linkId=rs.getString("id");
				linksForm.setSubLinkName(rs.getString("link_name"));
				linksForm.setSubLinkId(linksId);
				linksForm.setLinkId(rs.getString("id"));
				linksForm.setLinkPath(rs.getString("link_path"));
				linksForm.setMethodName(rs.getString("method"));
				linksForm.setPriority(rs.getString("priority"));
				linksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
			}
			session.setAttribute("linkId",linkId);
			displayMainLinks(mapping, form, request, response);
			return mapping.findForward("displayMainLinks");	
				}
				else{
				
				
				
				String sql="select * from links where link_name='"+linkName + "' and " +
						"status is null and module='Main' ";
				System.out.println("sql="+sql);
				ResultSet rs = ad.selectQuery(sql);
				String linkId=null;
				while (rs.next()) {
					linkId=rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					linksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
				}
				session.setAttribute("linkId",linkId);
				ArrayList list = new ArrayList();
				
				
				String sql3="select * from links where link_name='"+linkName + "' and " +
						"status is null and module='Main' and file_name not like ''";
				ResultSet rs5 = adlinks.selectQuery(sql3);
				while (rs5.next()){
					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				
				
				ArrayList list1 = new ArrayList();
				String sql4="select * from links where link_name='"+linkName + "' and " +
						"status is null and module='Main' and video_name not like ''";
				ResultSet rs6 = adlinks.selectQuery(sql4);
				while (rs6.next()){
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs6.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
				request.setAttribute("displaySublinkField", "displaySublinkField");
				
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
			displaySublinks(mapping, form, request, response);
			display(mapping, form, request, response);
			
			
			
			
			return mapping.findForward("displayMainLinks");
		}
		
		public ActionForward displayMainLinks(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
	    {
			LinksDao ad = new LinksDao();
			System.out.println("displayMainLinks()----");
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			HttpSession session = request.getSession();
			String linkName = linksForm.getLinkName();
			String subLinkName = linksForm.getSubLinkName();
			String linkPath = linksForm.getLinkPath();
			String methodName = linksForm.getMethodName();
			String priority = linksForm.getPriority();
			try {
			
				ResultSet rs = ad.selectQuery("select * from links where status is null");
				ArrayList linkIdList = new ArrayList();
				ArrayList linkValueList = new ArrayList();
				
				while(rs.next()) {
					linkIdList.add(rs.getString("link_name"));
					linkValueList.add(rs.getString("link_name"));
				}
				
				linksForm.setLinkName(linkName);
				linksForm.setLinkIdList(linkIdList);
				linksForm.setLinkValueList(linkValueList);
				linksForm.setSubLinkName(subLinkName);
				linksForm.setLinkPath(linkPath);
				linksForm.setMethodName(methodName);
				linksForm.setPriority(priority);
				linksForm.setContentDescription("");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Exception caught =" + e.getMessage());
			}
			
			
			String id = request.getParameter("id");
			
			
			if(id!=null){
			
			
			String sql = "select * from links where module='" + id + "' and status=1 ";
			ResultSet rs = ad.selectQuery(sql);
			try {
				LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
				
				while(rs.next()) {
					hm.put(rs.getString("link_path")+"?method="+rs.getString("method") + "&sId="+rs.getString("id"),(rs.getString("link_name")+','+rs.getString("icon_name")));
				}
				session.setAttribute("SUBLINKS", hm);
			} catch (SQLException se) {
				se.printStackTrace();
			}
			
			}
			
			
			LinkedHashMap<String, String> hm= (LinkedHashMap<String, String>)session.getAttribute("SUBLINKS");
			
			
			session.setAttribute("SUBLINKS", hm);
			
			String sql="select * from links where module is null and  status is null ";
	System.out.println("sql="+sql);
	ResultSet rs = ad.selectQuery(sql);
	try{
	
	while (rs.next()) {
		linkName=rs.getString("link_name");
	}
		
	}catch (Exception e) {
		e.printStackTrace();
		}
			if(linkName.equalsIgnoreCase("Login Help")){
				
			}else{
			request.setAttribute("displaySublinkField", "displaySublinkField");
			}
			return mapping.findForward("displayMainLinks");
		}
		
		
	public ActionForward selectContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
	{
		    
			LinksForm linksForm = (LinksForm) form;// TODO Auto-generated method stub
			System.out.println("********SELECT METHOD========");
			String linksId = request.getParameter("sId");
			String linkName = request.getParameter("lId");
			linksForm.setLinkName(linkName);
			LinksDao adlinks = new LinksDao();
			HttpSession session=request.getSession();
			try 
			{
				LinksDao ad = new LinksDao();
				String sql="select * from links where link_name='"+linksId + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
				ResultSet rs = ad.selectQuery(sql);
				String linkId=null;
				while (rs.next()) {
					linkId=rs.getString("id");
					linksForm.setSubLinkName(rs.getString("link_name"));
					linksForm.setSubLinkId(linksId);
					linksForm.setLinkId(rs.getString("id"));
					linksForm.setLinkPath(rs.getString("link_path"));
					linksForm.setMethodName(rs.getString("method"));
					linksForm.setPriority(rs.getString("priority"));
					linksForm.setContentDescriptionAdmin(rs.getString("content_description"));	
					linksForm.setIconList(rs.getString("icon_name"));	
				}
				session.setAttribute("linkId",linkId);
				ArrayList list = new ArrayList();
				String sql3="select * from links where link_name='"+linksId + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
				ResultSet rs5 = adlinks.selectQuery(sql3);
				while (rs5.next()){
					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql4="select * from links where link_name='"+linksId + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
				ResultSet rs6 = adlinks.selectQuery(sql4);
				while (rs6.next())
                                {
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs6.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			displaySublinks(mapping, form, request, response);
			display(mapping, form, request, response);
			return mapping.findForward("display1");
		}
		
		
		
		public ActionForward uploadFiles(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			LinksForm linksForm = (LinksForm) form;
			try {
				System.out.println("UPLOAD METHOD");
				String link_id = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				LinksDao adlinks = new LinksDao();
				String linkId = linksForm.getLinkName();
				String subLinkName = request.getParameter("subLinkName");
				
				String sql4="select * from links where module='"
					+link_id+"'  and delete_status=1 ";
				ResultSet rs6 = adlinks
						.selectQuery(sql4);
		         String sublinks="";
		         ArrayList listnew=new ArrayList();
				while (rs6.next())
				{
					sublinks=rs6.getString("link_name");
					listnew.add(sublinks);
				}
				if(listnew.contains(subLinkName))
				{
					linksForm.setMessage("Sub Link Name already taken.choose another name");
				}
				else
				{
				FormFile myFile = linksForm.getFileNames();
			    String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
				 if( linksForm.getFileNames().getFileSize()== 0){
					 linksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
					 display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs5 = adlinks.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql7="select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs16 = adlinks
								.selectQuery(sql7);
						while (rs16.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs16.getString("video_name"));
							list1.add(linksForm);
						}
						
						request.setAttribute("listName1", list1);	
				     return mapping.findForward("display");
				    }
				   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
				   {
				String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + link_id+"/"+sub_link_name+"/UploadFiles";
				File destinationDir = new File(filePath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				request.setAttribute("fileName", fileName);
				filePath = filePath.replace("\\", "\\\\");
				String sql9="select count(*) from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"' and file_name='"+fileName+"'";
				ResultSet rs15 = adlinks.selectQuery(sql9);
				int fileCount=0;
				while (rs15.next())
				{
					fileCount=Integer.parseInt(rs15.getString(1));
				}
				if(fileCount>0)
				{
					linksForm.setMessage("File aleardy uploaded..please choose another file");
				}
				else
				{
				String insertsql = "insert into filelist(link_name,sub_link_name,file_path,file_name) values('"
						+link_id
						+"','"
						+sub_link_name
						+"','"
						+filePath
						+"','"+fileName+"')";
				int a = adlinks.SqlExecuteUpdate(insertsql);
				if (a > 0)
				{
					linksForm.setMessage("Documents Uploaded Successfully");
					ArrayList list1 = new ArrayList();
					ResultSet rs7 = adlinks.selectQuery("select video_name from video_list where link_name='"+link_id+"' and " +
			 		"sub_link_name='"+sub_link_name+"'");
					while (rs7.next()) {
						linksForm = new LinksForm();
						linksForm.setVideoFilesList(rs7.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);
				} else {
					linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
				}
				}
				ArrayList list = new ArrayList();
				ResultSet rs5 = adlinks.selectQuery("select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'");
				while (rs5.next()) {
					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				   }
					else
					{
						linksForm.setMessage("Only TXT,DOC,DOCX,PDF File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs5 = adlinks.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql8="select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs18 = adlinks
								.selectQuery(sql8);
						while (rs18.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs18.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
				 	   return mapping.findForward("display");
					}
					ArrayList list1 = new ArrayList();
				   String sql8="select video_name from video_list where link_name='"+link_id+"' and " +
			 		"sub_link_name='"+sub_link_name+"'";	
							ResultSet rs18 = adlinks
									.selectQuery(sql8);
							while (rs18.next())
			                                {
								linksForm = new LinksForm();
								linksForm.setVideoFilesList(rs18.getString("video_name"));
								list1.add(linksForm);
							}
							request.setAttribute("listName1", list1);	
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display");
		}
	
		
		public ActionForward uploadVideos(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			LinksForm linksForm = (LinksForm) form;
			try {
				System.out.println("UPLOAD METHOD");
				String link_id = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				LinksDao adlinks = new LinksDao();
				String linkId = linksForm.getLinkName();
				String subLinkName = request.getParameter("subLinkName");
				String sql4="select * from links where module='"
					+link_id+"'  and delete_status=1 ";
				ResultSet rs6 = adlinks
						.selectQuery(sql4);
                 String sublinks="";
                 ArrayList listnew=new ArrayList();
 				while (rs6.next())
 				{
 					sublinks=rs6.getString("link_name");
 					listnew.add(sublinks);
 				}
				if(listnew.contains(subLinkName))
				{
					linksForm.setMessage("Sub Link Name already taken.choose another name");
				}
				else
				{
				FormFile myFile = linksForm.getVideoFileNames();
				String contentType = myFile.getContentType();
				String videoName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
				 if( linksForm.getVideoFileNames().getFileSize()== 0){
					 linksForm.setMessage("Please choose a Video file(MP4,OGV)to Upload");
					 display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs5 = adlinks.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql5="select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs11 = adlinks
								.selectQuery(sql5);
						while (rs11.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs11.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
				     return mapping.findForward("display");
				    }
				   if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
				   {	
				String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + link_id+"/"+sub_link_name+"/UploadVideos";
				File destinationDir = new File(videoPath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!videoName.equals("")) {
					File fileToCreate = new File(videoPath, videoName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				request.setAttribute("videoName", videoName);
				videoPath = videoPath.replace("\\", "\\\\");
				String sql9="select count(*) from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"' and video_name='"+videoName+"'";
				ResultSet rs5 = adlinks.selectQuery(sql9);
				int videoCount=0;
				while (rs5.next())
				{
					videoCount=Integer.parseInt(rs5.getString(1));
				}
				if(videoCount>0)
				{
					linksForm.setMessage("Video aleardy uploaded..please choose another video");
				}
				else
				{
				String insertsql = "insert into video_list(link_name, sub_link_name, video_path, " +
						"video_name)"
						+" values('"
						+link_id
						+"','"
						+sub_link_name
						+ "','"
						+videoPath
						+ "','"+videoName+"')";
				int a = adlinks.SqlExecuteUpdate(insertsql);
				if (a > 0) 
				{
					linksForm.setMessage("Video Files Uploaded Successfully");	
					ArrayList list = new ArrayList();
					String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs15 = adlinks.selectQuery(sql3);
					while (rs15.next()) 
	                               {
						linksForm = new LinksForm();
						linksForm.setFileList(rs15.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
				} 
				else 
				{
					linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
				}
				}
				ArrayList list1 = new ArrayList();
				
				ResultSet rs7 = adlinks.selectQuery("select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'");
				while (rs7.next()) {
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs7.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
				   }
					else
					{
						linksForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs5 = adlinks.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql8="select video_name from video_list where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'";
						ResultSet rs9 = adlinks
								.selectQuery(sql8);
						while (rs9.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs9.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
				     return mapping.findForward("display");
					}
				   ArrayList list = new ArrayList();
					String sql3="select file_name from filelist where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
					ResultSet rs5 = adlinks.selectQuery(sql3);
					while (rs5.next()) 
	                               {
						linksForm = new LinksForm();
						linksForm.setFileList(rs5.getString("file_name"));
						list.add(linksForm);
					}
					request.setAttribute("listName", list);
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display");
		}
		

		
		
		
		public ActionForward deleteFileList(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			LinksForm linksForm = (LinksForm) form;// TODO
			try {
				LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String linkId = linksForm.getLinkName();
			String subLinkName = request.getParameter("subLinkName");
				String deleteArray[] = linksForm.getSelect();
				String selected = "";
				if (deleteArray != null) {
					if (deleteArray.length == 1) {
						selected = "'" + deleteArray[0] + "'";
					} else {
						for (int i = 0; i < deleteArray.length; i++) {
							selected += "'" + deleteArray[i] + "'";
							if (i < deleteArray.length - 1) {
								selected += ",";
							}
						}
					}
				}
				String deletesql = "delete from  filelist where file_name IN(" + selected+ ")";
				int a = adlinks.SqlExecuteUpdate(deletesql);
				if (a > 0) {
					linksForm.setMessage("Files Deleted Successfully");
				} else {
					linksForm.setMessage("Error While Deleting Files ...");
				}
				ArrayList list = new ArrayList();
				ResultSet rs5 = adlinks.selectQuery("select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'");
				while (rs5.next()) {

					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				String sql4="select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'";
				ResultSet rs6 = adlinks
						.selectQuery(sql4);
				while (rs6.next())
                                {
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs6.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
			} catch (Exception e) {
				System.out.println("Exception caught =" + e.getMessage());
			}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display");
		}
		
		
		
		public ActionForward deleteVideoList(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			LinksForm linksForm = (LinksForm) form;// TODO
			try {
				LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String linkId = linksForm.getLinkName();
			String subLinkName = request.getParameter("subLinkName");
				String deleteArray[] = linksForm.getSelect1();
				String selected = "";
				if (deleteArray != null) {
					if (deleteArray.length == 1) {
						selected = "'" + deleteArray[0] + "'";
					} else {
						for (int i = 0; i < deleteArray.length; i++) {
							selected += "'" + deleteArray[i] + "'";
							if (i < deleteArray.length - 1) {
								selected += ",";
							}
						}
					}
				}
				String deletesql = "delete from  video_list where video_name IN(" + selected+ ")";
				int a = adlinks.SqlExecuteUpdate(deletesql);
				if (a > 0) {
					linksForm.setMessage("Video Files Deleted Successfully");
				} else {
					linksForm.setMessage("Error While Deleting Video Files ...");
				}
               ArrayList list = new ArrayList();
				ResultSet rs5 = adlinks.selectQuery("select file_name from filelist where link_name='"+link_id+"' and " +
		 		"sub_link_name='"+sub_link_name+"'");
				while (rs5.next()) {

					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				ArrayList list1 = new ArrayList();
				ResultSet rs6 = adlinks.selectQuery("select video_name from video_list where link_name='"+link_id+"' and " +
	 		"sub_link_name='"+sub_link_name+"'");
				while (rs6.next()) {
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs6.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
			} catch (Exception e) {
				System.out.println("Exception caught =" + e.getMessage());
			}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display");
		}
		
		
		public ActionForward uploadCmsLinkFilesModify(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
				{
			LinksForm linksForm = (LinksForm) form;;
			try {
				LinksDao ad = new LinksDao();
				System.out.println("UPLOAD FILE MODIFY  METHOD");
				String linkName = linksForm.getLinkName();
				String subLinkName = linksForm.getSubLinkName();
				
				String contentYear=linksForm.getContentYear();
				
				String subLinkTitle = linksForm.getSubLinkTitle();
				
				String fileDescription=linksForm.getImageTitle();
				
				linksForm.setLinkName(linkName);
				linksForm.setSubLinkName(subLinkName);
				
				
				ArrayList linkIdList = new ArrayList();
				ArrayList linkValueList = new ArrayList();
				 Date d = new Date();
				   ArrayList years = new ArrayList();
			  	   int pyear = 1900 + d.getYear();
				
				for(int i=pyear;i>=1950;i--){
					years.add(i);
				}
				
				linksForm.setYears(years);
				
				
				ResultSet rs = ad.selectQuery("select * from links where status is null");
				
				
 				while(rs.next()) {
					linkIdList.add(rs.getString("link_name"));
					linkValueList.add(rs.getString("link_name"));
				}
				
				linksForm.setLinkIdList(linkIdList);
				
				linksForm.setLinkValueList(linkValueList);
				
				
				
			 String sql6="select * from cms_sublinks where sub_linkname='"+subLinkName + "' and " +
				" main_linkname='"+linkName+"' and link_name='"+subLinkTitle+"'";
			 ResultSet rs6=ad.selectQuery(sql6);
			 String linkPath=null;
			 String method=null;
			 String priority=null;
			 String contentDescription=null;
			 while (rs6.next()) 
			 {
				 contentDescription=rs6.getString("content_description");
			 }
			 
			 linksForm.setContentDescriptionAdmin(contentDescription);	 
				FormFile myFile = linksForm.getImageGalleryFile();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
				 if( linksForm.getImageGalleryFile().getFileSize()== 0){
					 linksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
				    	display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						
						
						 String sql3="select * from cms_linksfilelist where sub_linkname='"+subLinkName + "' and " +
							" main_linkname='"+linkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
						
						
						ResultSet rs5 = ad.selectQuery(sql3);
						
						LinksForm linksForm1=null;
						while (rs5.next()) 
						{
							linksForm1 = new LinksForm();
							linksForm1.setImageId(rs5.getString("id"));
							linksForm1.setImageName(rs5.getString("file_name"));
							linksForm1.setImageTitle(rs5.getString("file_description"));
							
							list.add(linksForm1);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();


				     //return mapping.findForward("displayCmsLinksModify");
				    }
				   if(ext.equalsIgnoreCase("jpg")||(ext.equalsIgnoreCase("jpeg"))||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("pdf")))
				   {
				//String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadFiles";
				String filePath = getServlet().getServletContext().getRealPath("jsp/EMicro Files/cms") + "/" + linkName+"/"+subLinkName+"/links/" +
						""+subLinkTitle+"/images/"+contentYear+"";

				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	 filePath=uploadFilePath+"/EMicro Files/cms/" + linkName+"/"+subLinkName+"/links/" +
						""+subLinkTitle+"/images/"+contentYear;
			 	 
			 	 
				File destinationDir = new File(filePath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				
			
				
				
				request.setAttribute("fileName", fileName);
				filePath = filePath.replace("\\", "\\\\");
				
				
				/*String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";*/
				
				
				String sqlselect="select * from cms_linksfilelist where sub_linkname='"+subLinkName + "' and " +
				" main_linkname='"+linkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
				
				ResultSet rs15 = ad.selectQuery(sqlselect);
				String FileList="";
				while (rs15.next())
				{
					FileList=rs15.getString("file_name")+",";
				}
				//String	fileNameList="cms" + "/" + linkName+"/"+subLinkName+"/UploadFiles" + "/"+fileName;
				
				String fileNameList = "jsp/EMicro Files/cms"+"/"+linkName+"/"+subLinkName+"/links/"+subLinkTitle+"/images/"+contentYear+"/"+fileName;
				
				fileNameList=uploadFilePath+"/EMicro Files/cms/" + linkName+"/"+subLinkName+"/links/" +
				""+subLinkTitle+"/images/"+contentYear+"/"+fileName;
				
			ArrayList list1 = new ArrayList();
				
				
				boolean b;
			    b = FileList.contains(fileNameList);
			    if(b)
			    {
			    	linksForm.setMessage("This file  is  already uploaded..please choose another file");	
			    }
			    else
			    {
			    	String fileNames=null;
			    	if(FileList.equalsIgnoreCase(""))
			    	{
			    		fileNames=fileNameList;
			    	}
			    	else
			    	{
			    		fileNames=FileList+','+fileNameList;
			    	}
					
					String inserSQl="insert into cms_linksfilelist(main_linkname,sub_linkname," +
							"link_name,file_name,file_description,content_year)values('"+linkName+"','"+subLinkName+"'," +
									"'"+subLinkTitle+"','"+fileNameList+"','"+fileDescription+"','"+contentYear+"')";
					
					
					int a = ad.SqlExecuteUpdate(inserSQl);
					
					String sql3="select * from cms_linksfilelist where sub_linkname='"+subLinkName + "' and " +
					" main_linkname='"+linkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
				
				
				ResultSet rs5 = ad.selectQuery(sql3);
				
				ArrayList list2=new ArrayList();
				int count=0;
				LinksForm linksForm1=null;
				while(rs5.next()) 
				{
					linksForm1 = new LinksForm();
					linksForm1.setImageId(rs5.getString("id")+","+count);
					
					String imagename=rs5.getString("file_name");
					int x1=imagename.lastIndexOf("/");
					imagename=imagename.substring(x1+1);
					linksForm1.setImageName(imagename);
					linksForm1.setImageTitle(rs5.getString("file_description"));
					
					list2.add(linksForm1);
					count++;
				}
				request.setAttribute("listName", list2);
					
					
					if (a > 0)
					{
						linksForm.setImageTitle("");
						linksForm.setMessage("Documents Uploaded Successfully");
					} else
					{
						linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					}	
			    }
				ArrayList list = new ArrayList();
				
				
				//request.setAttribute("listName", list);
				   }
					else
					{
						linksForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						
						ArrayList list1 = new ArrayList();
						
						String sql20="select * from cms_sublinks where sub_linkname='"+subLinkName + "' and " +
						" main_linkname='"+linkName+"' and link_name='"+subLinkTitle+"'";
						
						
					 ResultSet rs10=ad.selectQuery(sql20);
					 while (rs10.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs10.getString("content_description"));
					 }
					   request.setAttribute("listName1", list1);	
				 	   return mapping.findForward("displayCmsLinksModify");
					}
				   
					
			}catch (Exception e) {
				e.printStackTrace();
			}
			//display(mapping, form, request, response);
			//displaySublinks(mapping, form, request, response);
			return mapping.findForward("displayCmsLinksModify");	
		}
		
		
		
		
		public ActionForward uploadFilesModify(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
				{
			LinksForm linksForm = (LinksForm) form;;
			try {
				LinksDao ad = new LinksDao();
				System.out.println("UPLOAD FILE MODIFY  METHOD");
				String linkName = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				linksForm.setLinkName(linkName);
				linksForm.setSubLinkName(sub_link_name);
			 String sql6="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
			 ResultSet rs6=ad.selectQuery(sql6);
			 String linkPath=null;
			 String method=null;
			 String priority=null;
			 String contentDescription=null;
			 while (rs6.next()) 
			 {
				 linksForm.setLinkPath(rs6.getString("link_path"));           
				 linksForm.setMethodName(rs6.getString("method"));
				 linksForm.setPriority(rs6.getString("priority")); 
				 contentDescription=rs6.getString("content_description");
			}
			 
			 linksForm.setContentDescriptionAdmin(contentDescription);	 
				FormFile myFile = linksForm.getFileNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
				 if( linksForm.getFileNames().getFileSize()== 0){
					 linksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
				    	display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
						{
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'  and video_name not like ''";
						ResultSet rs12 = ad
								.selectQuery(sql4);
						while (rs12.next()) {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs12.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
						 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
							"status=1 and delete_status=1 and module='"+linkName+"'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
				     return mapping.findForward("display1");
				    }
				   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
				   {
				String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadFiles";
				File destinationDir = new File(filePath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				request.setAttribute("fileName", fileName);
				filePath = filePath.replace("\\", "\\\\");
				String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
				ResultSet rs15 = ad.selectQuery(sqlselect);
				String FileList="";
				while (rs15.next())
				{
					FileList=rs15.getString("file_name");
				}
				String	fileNameList="cms" + "/" + linkName+"/"+sub_link_name+"/UploadFiles" + "/"+fileName;
				boolean b;
			    b = FileList.contains(fileNameList);
			    if(b)
			    {
			    	linksForm.setMessage("This file  is  already uploaded..please choose another file");	
			    }
			    else
			    {
			    	String fileNames=null;
			    	if(FileList.equalsIgnoreCase(""))
			    	{
			    		fileNames=fileNameList;
			    	}
			    	else
			    	{
			    		fileNames=FileList+','+fileNameList;
			    	}
					String updatesql="update links set file_name='"+fileNames+"' where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'" ;
					int a = ad.SqlExecuteUpdate(updatesql);
					if (a > 0)
					{
						linksForm.setMessage("Documents Uploaded Successfully");
					} else
					{
						linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					}	
			    }
				ArrayList list = new ArrayList();
				String sql3="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
				ResultSet rs5 = ad.selectQuery(sql3);
				while (rs5.next()) 
				{
					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				   }
					else
					{
						linksForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
						{
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
						ResultSet rs13 = ad
								.selectQuery(sql4);
						while (rs13.next()) 
						{
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs13.getString("video_name"));
							list1.add(linksForm);
						}
						String sql20="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 ResultSet rs10=ad.selectQuery(sql20);
					 while (rs10.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs10.getString("content_description"));
					}
						request.setAttribute("listName1", list1);	
				 	   return mapping.findForward("display1");
					}
				   ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like '' ";
					ResultSet rs14 = ad
							.selectQuery(sql4);
					while (rs14.next()) {
						linksForm = new LinksForm();
						linksForm.setVideoFilesList(rs14.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);
			}catch (Exception e) {
				e.printStackTrace();
			}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display1");	
		}
		
		
		
		
		
		
		public ActionForward uploadLinksFilesModify(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception
				{
			LinksForm linksForm = (LinksForm) form;;
			try {
				LinksDao ad = new LinksDao();
				System.out.println("UPLOAD FILE MODIFY  METHOD");
				String linkName = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				linksForm.setLinkName(linkName);
				linksForm.setSubLinkName(sub_link_name);
			 String sql6="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
			 ResultSet rs6=ad.selectQuery(sql6);
			 String linkPath=null;
			 String method=null;
			 String priority=null;
			 String contentDescription=null;
			 while (rs6.next()) 
			 {
				 linksForm.setLinkPath(rs6.getString("link_path"));           
				 linksForm.setMethodName(rs6.getString("method"));
				 linksForm.setPriority(rs6.getString("priority")); 
				 contentDescription=rs6.getString("content_description");
			}
			 linksForm.setContentDescriptionAdmin(contentDescription);	 
				FormFile myFile = linksForm.getFileNames();
				String contentType = myFile.getContentType();
				String fileName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
				 if( linksForm.getFileNames().getFileSize()== 0){
					 linksForm.setMessage("Please choose a TXT,DOC,DOCX,PDF file to Upload");
				    	display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+linkName + "' and " +
				        "status is null and module='Main' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
						{
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+linkName + "' and " +
					   "status is null and module='Main'  and video_name not like ''";
						ResultSet rs12 = ad
								.selectQuery(sql4);
						while (rs12.next()) {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs12.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
						 String sql9="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
				     return mapping.findForward("display1");
				    }
				   if(ext.equalsIgnoreCase("txt")||(ext.equalsIgnoreCase("doc"))||(ext.equalsIgnoreCase("docx"))||(ext.equalsIgnoreCase("pdf")))
				   {
				String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+"UploadFiles";
				File destinationDir = new File(filePath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, fileName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
						fileOutStream.write(myFile.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				request.setAttribute("fileName", fileName);
				filePath = filePath.replace("\\", "\\\\");
				String sqlselect="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and file_name not like ''";
				ResultSet rs15 = ad.selectQuery(sqlselect);
				String FileList="";
				while (rs15.next())
				{
					FileList=rs15.getString("file_name");
				}
				String	fileNameList="cms" + "/" + linkName+"/"+"UploadFiles" + "/"+fileName;
				boolean b;
			    b = FileList.contains(fileNameList);
			    if(b)
			    {
			    	linksForm.setMessage("This file  is  already uploaded..please choose another file");	
			    }
			    else
			    {
			    	String fileNames=null;
			    	if(FileList.equalsIgnoreCase(""))
			    	{
			    		fileNames=fileNameList;
			    	}
			    	else
			    	{
			    		fileNames=FileList+','+fileNameList;
			    	}
					String updatesql="update links set file_name='"+fileNames+"' where link_name='"+linkName + "' and " +
				    "status is null and module='Main'" ;
					int a = ad.SqlExecuteUpdate(updatesql);
					if (a > 0)
					{
						linksForm.setMessage("Documents Uploaded Successfully");
					} else
					{
						linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
					}	
			    }
				ArrayList list = new ArrayList();
				String sql3="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and file_name not like ''";
				ResultSet rs5 = ad.selectQuery(sql3);
				while (rs5.next()) 
				{
					linksForm = new LinksForm();
					linksForm.setFileList(rs5.getString("file_name"));
					list.add(linksForm);
				}
				request.setAttribute("listName", list);
				   }
					else
					{
						linksForm.setMessage("Only File(.TXT,.PDF,.DOC,.DOCX) File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+linkName + "' and " +
						"status is null and module='Main' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
						{
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+linkName + "' and " +
						"status is null and module='Main' and video_name not like ''";
						ResultSet rs13 = ad.selectQuery(sql4);
						
						while (rs13.next()) 
						{
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs13.getString("video_name"));
							list1.add(linksForm);
						}
						String sql20="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
					 ResultSet rs10=ad.selectQuery(sql20);
					 while (rs10.next()) 
					 {
						 linksForm.setContentDescriptionAdmin(rs10.getString("content_description"));
					}
						request.setAttribute("listName1", list1);	
				 	   return mapping.findForward("display1");
					}
				   ArrayList list1 = new ArrayList();
					String sql4="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like '' ";
					ResultSet rs14 = ad
							.selectQuery(sql4);
					while (rs14.next()) {
						linksForm = new LinksForm();
						linksForm.setVideoFilesList(rs14.getString("video_name"));
						list1.add(linksForm);
					}
					request.setAttribute("listName1", list1);
			}catch (Exception e) {
				e.printStackTrace();
			}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("displayMainLinks");	
		}
		
		
		public ActionForward uploadLinksVideosModify(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			LinksForm linksForm = (LinksForm) form;;
			try {
				LinksDao ad = new LinksDao();
				System.out.println("UPLOAD Video MODIFY  METHOD");
				String linkName = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				linksForm.setLinkName(linkName);
				linksForm.setSubLinkName(sub_link_name);
			 String sql6="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
			 ResultSet rs6=ad.selectQuery(sql6);
			 String linkPath=null;
			 String method=null;
			 String priority=null;
			 String contentDescription=null;
			 while (rs6.next()) 
			 {
				 linksForm.setLinkPath(rs6.getString("link_path"));           
				 linksForm.setMethodName(rs6.getString("method"));
				 linksForm.setPriority(rs6.getString("priority")); 
				 contentDescription=rs6.getString("content_description");
			}
			 linksForm.setContentDescriptionAdmin(contentDescription);	 
				FormFile myFile = linksForm.getVideoFileNames();
				String contentType = myFile.getContentType();
				String videoName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
				 if( linksForm.getVideoFileNames().getFileSize()== 0){
					 linksForm.setMessage("Please choose a Video file(MP4,OGV)to Upload");
					 display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+linkName + "' and " +
						"status is null and module='Main' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like ''";
						ResultSet rs16 = ad
								.selectQuery(sql4);
						while (rs16.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs16.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
						 String sql9="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
				     return mapping.findForward("display1");
				    }
				if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
				   {
				    	
				String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+"UploadVideos";
				File destinationDir = new File(videoPath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!videoName.equals("")) {
					File fileToCreate = new File(videoPath, videoName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());

						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				request.setAttribute("videoName", videoName);
              videoPath = videoPath.replace("\\", "\\\\");
				String sqlselect="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like ''";
				ResultSet rs17 = ad.selectQuery(sqlselect);
				String videoList="";
				while (rs17.next())
				{
					videoList=rs17.getString("video_name");
				}
				
				String	videoNameList="cms" + "/" + linkName+"/"+""+"UploadVideos" + "/"+videoName;
				boolean b;
			    b = videoList.contains(videoNameList);
			    if(b)
			    {
			    	linksForm.setMessage("This video  is  already uploaded..please choose another file");			
			    }
			    else
			    {
			    	String videoNames=null;
			    	if(videoList.equalsIgnoreCase(""))
			    	{
			    		videoNames=videoNameList;
			    	}
			    	else
			    	{
			    		 videoNames=videoList+','+videoNameList;
			    	}
					String updatesql="update links set video_name='"+videoNames+"' where link_name='"+linkName + "' and " +
				"status is null and module='Main'" ;
					int a = ad.SqlExecuteUpdate(updatesql);
					if (a > 0)
					{
						linksForm.setMessage("Video Files Uploaded Successfully");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like ''";
						ResultSet rs16 = ad
								.selectQuery(sql4);
						while (rs16.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs16.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
						 String sql9="select * from links where link_name='"+linkName + "' and " +
						 "status is null and module='Main' ";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
					} 
					else {
						linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and file_name not like '' ";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like ''";
						ResultSet rs26 = ad
								.selectQuery(sql4);
						while (rs26.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs26.getString("video_name"));
							list1.add(linksForm);
						}
						 String sql9="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
						request.setAttribute("listName1", list1);	
					}
			    }
				ArrayList list1 = new ArrayList();
				ResultSet rs36 = ad.selectQuery("select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like ''");
				while (rs36.next()) {
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs36.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
				   }
					else
					{
						linksForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main' and video_name not like ''";
						ResultSet rs46 = ad
								.selectQuery(sql4);
						while (rs46.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs46.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);
						 String sql9="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
						 ResultSet rs9=ad.selectQuery(sql9);
						while (rs9.next()) 
						{
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
						ad.closeResultset();
						ad.closeStatement();
				     return mapping.findForward("display1");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("displayMainLinks");
		}
		
		
		
		public ActionForward uploadVideosModify(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			LinksForm linksForm = (LinksForm) form;;
			try {
				LinksDao ad = new LinksDao();
				System.out.println("UPLOAD Video MODIFY  METHOD");
				String linkName = linksForm.getLinkName();
				String sub_link_name = linksForm.getSubLinkName();
				linksForm.setLinkName(linkName);
				linksForm.setSubLinkName(sub_link_name);
			 String sql6="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
			 ResultSet rs6=ad.selectQuery(sql6);
			 String linkPath=null;
			 String method=null;
			 String priority=null;
			 String contentDescription=null;
			 while (rs6.next()) 
			 {
				 linksForm.setLinkPath(rs6.getString("link_path"));           
				 linksForm.setMethodName(rs6.getString("method"));
				 linksForm.setPriority(rs6.getString("priority")); 
				 contentDescription=rs6.getString("content_description");
			}
			 linksForm.setContentDescriptionAdmin(contentDescription);	 
				FormFile myFile = linksForm.getVideoFileNames();
				String contentType = myFile.getContentType();
				String videoName = myFile.getFileName();
				byte[] fileData = myFile.getFileData();
				String ext = videoName.substring(videoName.lastIndexOf('.') + 1);
				 if( linksForm.getVideoFileNames().getFileSize()== 0){
					 linksForm.setMessage("Please choose a Video file(MP4,OGV)to Upload");
					 display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like ''";
						ResultSet rs16 = ad
								.selectQuery(sql4);
						while (rs16.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs16.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
						 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
							"status=1 and delete_status=1 and module='"+linkName+"'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
						 	 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						 }
				     return mapping.findForward("display1");
				    }
				if(ext.equalsIgnoreCase("mp4")||(ext.equalsIgnoreCase("ogv")))
				   {
				    	
				String videoPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadVideos";
				File destinationDir = new File(videoPath);
				if(!destinationDir.exists())
				{
					destinationDir.mkdirs();
				}
				if (!videoName.equals("")) {
					File fileToCreate = new File(videoPath, videoName);
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(
								fileToCreate);
						fileOutStream.write(myFile.getFileData());

						fileOutStream.flush();
						fileOutStream.close();
					}
				}
				request.setAttribute("videoName", videoName);
              videoPath = videoPath.replace("\\", "\\\\");
				String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
				ResultSet rs17 = ad.selectQuery(sqlselect);
				String videoList="";
				while (rs17.next())
				{
					videoList=rs17.getString("video_name");
				}
				
				String	videoNameList="cms" + "/" + linkName+"/"+sub_link_name+"/"+"/UploadVideos" + "/"+videoName;
				boolean b;
			    b = videoList.contains(videoNameList);
			    if(b)
			    {
			    	linksForm.setMessage("This video  is  already uploaded..please choose another file");			
			    }
			    else
			    {
			    	String videoNames=null;
			    	if(videoList.equalsIgnoreCase(""))
			    	{
			    		videoNames=videoNameList;
			    	}
			    	else
			    	{
			    		 videoNames=videoList+','+videoNameList;
			    	}
					String updatesql="update links set video_name='"+videoNames+"' where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+linkName+"'" ;
					int a = ad.SqlExecuteUpdate(updatesql);
					if (a > 0)
					{
						linksForm.setMessage("Video Files Uploaded Successfully");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like ''";
						ResultSet rs16 = ad
								.selectQuery(sql4);
						while (rs16.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs16.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);	
						 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
							"status=1 and delete_status=1 and module='"+linkName+"'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
					} 
					else {
						linksForm.setMessage("Error While Uploading Files ... Please check Entered Values");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'and file_name not like '' ";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''";
						ResultSet rs26 = ad
								.selectQuery(sql4);
						while (rs26.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs26.getString("video_name"));
							list1.add(linksForm);
						}
						 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
							"status=1 and delete_status=1 and module='"+linkName+"'";
						 ResultSet rs9=ad.selectQuery(sql9);
						 while (rs9.next()) 
						 {
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
						request.setAttribute("listName1", list1);	
					}
			    }
				ArrayList list1 = new ArrayList();
				ResultSet rs36 = ad.selectQuery("select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"' and video_name not like ''");
				while (rs36.next()) {
					linksForm = new LinksForm();
					linksForm.setVideoFilesList(rs36.getString("video_name"));
					list1.add(linksForm);
				}
				request.setAttribute("listName1", list1);
				   }
					else
					{
						linksForm.setMessage("Only Video(MP4,OGV) File Format is Allowed");
						display(mapping, form, request, response);
						displaySublinks(mapping, form, request, response);
						ArrayList list = new ArrayList();
						String sql3="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"' and file_name not like ''";
						ResultSet rs5 = ad.selectQuery(sql3);
						while (rs5.next()) 
		                               {
							linksForm = new LinksForm();
							linksForm.setFileList(rs5.getString("file_name"));
							list.add(linksForm);
						}
						request.setAttribute("listName", list);
						ArrayList list1 = new ArrayList();
						String sql4="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'and video_name not like ''";
						ResultSet rs46 = ad
								.selectQuery(sql4);
						while (rs46.next())
		                                {
							linksForm = new LinksForm();
							linksForm.setVideoFilesList(rs46.getString("video_name"));
							list1.add(linksForm);
						}
						request.setAttribute("listName1", list1);
						 String sql9="select * from links where link_name='"+sub_link_name + "' and " +
							"status=1 and delete_status=1 and module='"+linkName+"'";
						 ResultSet rs9=ad.selectQuery(sql9);
						while (rs9.next()) 
						{
							 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
						}
						ad.closeResultset();
						ad.closeStatement();
						ad.connClose();
				     return mapping.findForward("display1");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display1");
		}
		
		
		public ActionForward deleteImagesCmsLinkList(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;
			try
			{
			System.out.println("DELETE FILE MODIFY METHOD");
			LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String sub_link_title = linksForm.getSubLinkTitle();
			
			
			String param=request.getParameter("param");
			
			
			ArrayList linkIdList = new ArrayList();
			ArrayList linkValueList = new ArrayList();
			
			
			ResultSet rs = adlinks.selectQuery("select * from links where status is null");
			
			
			while(rs.next()) {
				linkIdList.add(rs.getString("link_name"));
				linkValueList.add(rs.getString("link_name"));
			}
			
			linksForm.setLinkIdList(linkIdList);
			
			linksForm.setLinkValueList(linkValueList);
			
			
			String uncheckValues=request.getParameter("unValues");
			String checkValues=request.getParameter("cValues");
			linksForm.setSubLinkName(sub_link_name);
			String sql9="select * from cms_sublinks where main_linkname='"+link_id + "' and " +
			"sub_linkname='"+sub_link_name +"' and link_name='"+sub_link_title +"' ";
		 ResultSet rs9=adlinks.selectQuery(sql9);
		 while (rs9.next()) 
		 {
			 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
		 }
		 
		 
		 if(param.equalsIgnoreCase("Modify")){
			 
			 
			 String[] descriptionValues=request.getParameterValues("checkeddescription");
			 
			 
			 String descrition="";
			 for(int i=0;i<descriptionValues.length;i++){
				 descrition+=descriptionValues[i]+",";
			 }
			 	
				String updatesql="update cms_sublinks set file_description='"+descrition+"' where main_linkname='"+link_id + "' and " +
				"sub_linkname='"+sub_link_name +"' and link_name='"+sub_link_title +"' " ;
				
				adlinks.SqlExecuteUpdate(updatesql);
		 }else{
			 
			 
			 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
			 checkValues = checkValues.substring(0, checkValues.length());
				String v[] = uncheckValues.split(",");
				String upFileName=null;
				String upFileName2="";
				for(int i=0;i<v.length;i++)
				{
				upFileName="cms"+"/" + link_id+"/"+sub_link_name+"/links" + "/"+sub_link_title+"/images/"+v[i];
			    
			    upFileName2+=upFileName+",";
				}
				String up2="cms"+"/" + link_id+"/"+sub_link_name+"/links" + "/"+sub_link_title+"/images/";
				upFileName2 = upFileName2.substring(0, upFileName2.length()-1);
				String updatesql=null;
				if(upFileName2.equalsIgnoreCase(up2))
				{
					updatesql="update cms_sublinks set file_name='' where main_linkname='"+link_id + "' and " +
			"sub_linkname='"+sub_link_name +"' and link_name='"+sub_link_title +"' " ;
				}
				else
				{
					 updatesql="update cms_sublinks set file_name='"+upFileName2+"' where main_linkname='"+link_id + "' and " +
			"sub_linkname='"+sub_link_name +"' and link_name='"+sub_link_title +"' " ;
				}
		int a = adlinks.SqlExecuteUpdate(updatesql);
		if (a > 0) {
			linksForm.setMessage("Documents deleted Successfully");
		ArrayList list = new ArrayList();
		String sql3="select * from cms_sublinks where  main_linkname='"+link_id + "' and " +
			"sub_linkname='"+sub_link_name +"' and link_name='"+sub_link_title +"' and file_name not like ''";
		ResultSet rs5 = adlinks.selectQuery(sql3);
		
		LinksForm linksForm2=null;
		while (rs5.next()) 
		{
			linksForm2 = new LinksForm();
			linksForm2.setFileList(rs5.getString("file_name"));
			linksForm2.setFileDescription(rs5.getString("file_description"));
			list.add(linksForm2);
		}
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		
		
		String sql19="select * from cms_sublinks where  main_linkname='"+link_id + "' and " +
			"sub_linkname='"+sub_link_name +"' and link_name='"+sub_link_title +"'";
	 ResultSet rs19=adlinks.selectQuery(sql19);
	 while (rs19.next()) 
	 {
	 	 linksForm.setContentDescriptionAdmin(rs19.getString("content_description"));	 
	 }
		
		return mapping.findForward("displayCmsLinksModify");
		} 
		else {
			linksForm.setMessage("Error While deleteing Files ... Please check Entered Values");
		
			return mapping.findForward("displayCmsLinksModify");
		}
		
		 }
		}catch (Exception e) {
			e.printStackTrace();
		}
			
			
		  
			return mapping.findForward("displayCmsLinksModify");
		}
		
		
		
		public ActionForward deleteFileListModify(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) 
		{
			LinksForm linksForm = (LinksForm) form;
			try
			{
			System.out.println("DELETE FILE MODIFY METHOD");
			LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String uncheckValues=request.getParameter("unValues");
			String checkValues=request.getParameter("cValues");
			linksForm.setSubLinkName(sub_link_name);
			String sql9="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+link_id+"'";
		 ResultSet rs9=adlinks.selectQuery(sql9);
		 while (rs9.next()) 
		 {
			 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
		 }
			 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
			 checkValues = checkValues.substring(0, checkValues.length());
				String v[] = uncheckValues.split(",");
				String upFileName=null;
				String upFileName2="";
				for(int i=0;i<v.length;i++)
				{
				upFileName="cms"+"/" + link_id+"/"+sub_link_name+"/UploadFiles" + "/"+v[i];
			    
			    upFileName2+=upFileName+",";
				}
				String up2="cms" + "/" + link_id+"/"+sub_link_name+"/UploadFiles" + "/";
				upFileName2 = upFileName2.substring(0, upFileName2.length()-1);
				String updatesql=null;
				if(upFileName2.equalsIgnoreCase(up2))
				{
					updatesql="update links set file_name='' where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+link_id+"'" ;
				}
				else
				{
					 updatesql="update links set file_name='"+upFileName2+"' where link_name='"+sub_link_name + "' and " +
					"status=1 and delete_status=1 and module='"+link_id+"'" ;
				}
		int a = adlinks.SqlExecuteUpdate(updatesql);
		if (a > 0) {
			linksForm.setMessage("Documents deleted Successfully");
		ArrayList list = new ArrayList();
		String sql3="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+link_id+"'and file_name not like ''";
		ResultSet rs5 = adlinks.selectQuery(sql3);
		while(rs5.next()) 
		{
			linksForm = new LinksForm();
			linksForm.setFileList(rs5.getString("file_name"));
			list.add(linksForm);
		}
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		String sql4="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+link_id+"' and video_name not like ''";
		ResultSet rs13 = adlinks.selectQuery(sql4);
		
		while (rs13.next()) 
		{
			linksForm = new LinksForm();
			linksForm.setVideoFilesList(rs13.getString("video_name"));
			list1.add(linksForm);
		}
		
		request.setAttribute("listName1", list1);
		String sql19="select * from links where link_name='"+sub_link_name + "' and " +
		"status=1 and delete_status=1 and module='"+link_id+"'";
		 ResultSet rs19=adlinks.selectQuery(sql19);
		 while (rs19.next()) 
		 {
			 linksForm.setContentDescriptionAdmin(rs19.getString("content_description"));	 
		 }
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
		} 
		else {
			linksForm.setMessage("Error While deleteing Files ... Please check Entered Values");
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
			
			
		    display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display1");
		}
		
		
		public ActionForward submitLinksContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
				{
			System.out.println("updateLinksContent()-----");
			LinksForm linksForm = (LinksForm) form;
			
			String linkName=linksForm.getLinkName();
			String subLinkName=linksForm.getSubLinkName();
			
			FormFile iconNames=linksForm.getIconNames();
			
			String subLinkTitle=linksForm.getSubLinkTitle();
			String contentYear=linksForm.getContentYear();
			
			Enumeration<String> params = request
			.getParameterNames();
			String parameter;
			String content_description = "";
			while (params.hasMoreElements()) {
				parameter = params.nextElement();
				if (parameter.equalsIgnoreCase("EditorDefault")) {
					content_description += request.getParameter(parameter);
				}
			}
			
			content_description=content_description.replaceAll("'", "''");
			
			LinksDao ad = new LinksDao();
			
			String sql="select * from temp_filelist where main_linkname='"+linkName+"' and" +
					" sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"'";
			
			try{
				
				ResultSet rs=ad.selectQuery(sql);
				
				String fileName="";
				String fileDescription="";
				
				while(rs.next()) {
					fileName+=rs.getString("file_name")+",";
					fileDescription+=rs.getString("file_description")+",";
					
					
					
					String sql12="insert into cms_linksfilelist(main_linkname,sub_linkname," +
							"link_name,file_name,file_description,content_year)values('"+linkName+"','"+subLinkName+"'," +
							"'"+subLinkTitle+"','"+rs.getString("file_name")+"'," +
							"'"+rs.getString("file_description")+"','"+contentYear+"')";
					ad.SqlExecuteUpdate(sql12);
					
				}
				
				
				//String path="cms/"+ linkName+"/"+subLinkName+"/links/"+subLinkTitle+"/"+iconNames.getFileName();
				String path=iconNames.getFileName();
				
				String sql1="insert into cms_sublinks(main_linkname,sub_linkname,link_name," +
						"link_path,method,file_name,icon_name,content_description,file_description,content_year,archived_status)" +
						"values('"+linkName+"','"+subLinkName+"','"+subLinkTitle+"','newsAndMedia.do','displayLinks'," +
						"'"+fileName+"'," +
						"'"+path+"','"+content_description+"','"+fileDescription+"','"+contentYear+"','0')";
				
			 	int a=ad.SqlExecuteUpdate(sql1);
			 	
			 	
			 	String sql2="delete from temp_filelist where main_linkname='"+linkName+"' and" +
			 			" sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"'";
			 	
			 	ad.SqlExecuteUpdate(sql2);
			 	
			 	String contentType = iconNames.getContentType();
					
					byte[] fileData = iconNames.getFileData();
					
					
					
					
					
					
					//String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+subLinkName+"/links/"+subLinkTitle;
					
					
			
					
					String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+subLinkName+"" +
					"/links/"+subLinkTitle+"/images/"+contentYear+"/icon";
					
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	  System.out.println("required filepath="+uploadFilePath+"/EMicro Files/ESS/On Duty/UploadFiles");
				 	 filePath=uploadFilePath+"/EMicro Files/cms/"+linkName+"/"+subLinkName+"/links/"+subLinkTitle+"/images/"+contentYear+"/icon";
			
			File imageGalleryDir = new File(filePath);
			
			
			if(!imageGalleryDir.exists())
			{
				imageGalleryDir.mkdirs();
			}
				if (!fileName.equals("")) {
					File fileToCreate = new File(filePath, iconNames.getFileName());
					if (!fileToCreate.exists()) {
						FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
						fileOutStream.write(iconNames.getFileData());
						fileOutStream.flush();
						fileOutStream.close();
					}
					
				}
				
				filePath=filePath+"/"+iconNames.getFileName();
				filePath = filePath.replace("\\", "\\\\");
				
				String sql31="update cms_sublinks set icon_name='"+filePath+"' where main_linkname='"+linkName+"' and" +
			 			" sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
				
				ad.SqlExecuteUpdate(sql31);
				
			 	
				ResultSet rs1 = ad.selectQuery("select * from links where status is null");
				ArrayList linkIdList = new ArrayList();
				ArrayList linkValueList = new ArrayList();
				
				while(rs1.next()) {
					linkIdList.add(rs1.getString("link_name"));
					linkValueList.add(rs1.getString("link_name"));
				}
				
				linksForm.setLinkName(linkName);
				linksForm.setLinkIdList(linkIdList);
				linksForm.setLinkValueList(linkValueList);
			 	
			 	
			 	if(a>0){
			 		
			 		displayCmsSublinksImages(mapping, form, request, response);
			 		linksForm.setMessage("Links Content Submitted Successfully");
			 	}
			 	
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			return mapping.findForward("displayCmsLinks");	
			}
		
		
		
		public ActionForward updateCmsLinksContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
				{
			
			System.out.println("updateLinksContent()-----");
			LinksForm linksForm = (LinksForm) form;
			HttpSession session=request.getSession();
			LinksDao adlinks = new LinksDao();
			String linkName = linksForm.getLinkName();
				try
				{
				System.out.println("***UPDATE THE CONTENT***");
				FormFile iconNames=linksForm.getIconNames();
				
				String sub_link_name = linksForm.getSubLinkName();
				String mainLinkName = linksForm.getLinkName();
				
				String subLinkTitle =linksForm.getSubLinkTitle();
				
				String priority=linksForm.getPriority();
				String id=linksForm.getLinkId();
				
				String sqlselect="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
				Enumeration<String> params = request
				.getParameterNames();
				String parameter;
				String content_description = "";
				
				while (params.hasMoreElements()) {
					parameter = params.nextElement();
					if (parameter.equalsIgnoreCase("EditorDefault")) {
						content_description += request.getParameter(parameter);
					}
				}
				
				ResultSet rs17 = adlinks.selectQuery(sqlselect);
				String fileList=null;
				String videoList=null;
				while (rs17.next()) 
				{   
					fileList=rs17.getString("file_name");
					videoList=rs17.getString("video_name");
				}
			
		String sql17="update cms_sublinks set content_description='"+content_description+"' where main_linkname='"+linkName+"' and " +
				"sub_linkname='"+sub_link_name+"' and link_name='"+subLinkTitle+"'";
		
		adlinks.SqlExecuteUpdate(sql17);
		String path=iconNames.getFileName();
		
		
		String contentType = iconNames.getContentType();
		String fileName1 = iconNames.getFileName();
			
			byte[] fileData = iconNames.getFileData();
			
			//String filePath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+subLinkName+"/links/"+subLinkTitle;
			
			String filePath = getServlet().getServletContext().getRealPath("/");
		if (!path.equals("")) {
			File fileToCreate = new File(filePath, iconNames.getFileName());
			if (!fileToCreate.exists()) {
				FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
				fileOutStream.write(iconNames.getFileData());
				fileOutStream.flush();
				fileOutStream.close();
			}
			
		}
			
			linksForm.setMessage("Your details have been updated successfully");
		
			}catch (Exception e) {
				e.printStackTrace();
			}
		
			
			return mapping.findForward("displayCmsLinksModify");	
		  }
		
		
		
		
		public ActionForward updateLinksContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
				{
			System.out.println("updateLinksContent()-----");
			LinksForm linksForm = (LinksForm) form;
			HttpSession session=request.getSession();
			LinksDao adlinks = new LinksDao();
			String linkName = linksForm.getLinkName();
				try
				{
				System.out.println("***UPDATE THE CONTENT***");
				
				String sub_link_name = linksForm.getSubLinkName();
				String linkPath=linksForm.getLinkPath();
				String method=linksForm.getMethodName();
				String priority=linksForm.getPriority();
				String id=linksForm.getLinkId();
				
				String sqlselect="select * from links where link_name='"+linkName + "' and " +
				"status is null and module='Main'";
				Enumeration<String> params = request
				.getParameterNames();
		String parameter;
		String content_description = "";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
				ResultSet rs17 = adlinks.selectQuery(sqlselect);
				String fileList=null;
				String videoList=null;
				while (rs17.next()) 
				{   
					fileList=rs17.getString("file_name");
					videoList=rs17.getString("video_name");
				}
		
				
				if(linkName.equalsIgnoreCase("Login Help"));
				{
					
					String sql18="update links set content_description='"+content_description+"' where link_name='"+linkName+"' and " +
					"status is null ";
			
					int j=0;
					j=adlinks.SqlExecuteUpdate(sql18);
	
			
			linksForm.setContentDescriptionAdmin(content_description);
			
			
			}
			
			
		
				
				
				
				
			
		String sql17="update links set content_description='"+content_description+"' where link_name='"+linkName+"' and " +
				"status is null and module='Main'";
		
		adlinks.SqlExecuteUpdate(sql17);
				
			linksForm.setMessage("Your details have been updated successfully");
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+linkName+"' and " +
			"status is null and module='Main'";
			ResultSet rs5 = adlinks.selectQuery(sql3);
			while (rs5.next()) {

				linksForm = new LinksForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			String sql4="select * from links where link_name='"+linkName+"' and " +
			"status is null and module='Main'";
			ResultSet rs15 = adlinks
					.selectQuery(sql4);
			while (rs15.next()) {
				linksForm = new LinksForm();
				linksForm.setVideoFilesList(rs15.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);	
		
				}catch (Exception e) {
					e.printStackTrace();
				}
				
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			
			return mapping.findForward("displayMainLinks");	
		  }
		
		public ActionForward deleteVideoListModify(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) {
			LinksForm linksForm = (LinksForm) form;
			try
			{
			System.out.println("DELETE VIDEO MODIFY METHOD");
			LinksDao adlinks = new LinksDao();
			String link_id = linksForm.getLinkName();
			String sub_link_name = linksForm.getSubLinkName();
			String uncheckValues=request.getParameter("unValues");
			String checkValues=request.getParameter("cValues");
			linksForm.setSubLinkName(sub_link_name);
			String sql9="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+link_id+"'";
		 ResultSet rs9=adlinks.selectQuery(sql9);
		 while (rs9.next()) 
		 {
			 linksForm.setContentDescriptionAdmin(rs9.getString("content_description"));	 
		}
			 uncheckValues = uncheckValues.substring(0, uncheckValues.length());
				String v[] = uncheckValues.split(",");
			 String upVideoName=null;
				String upVideoName2="";
				for(int i=0;i<v.length;i++)
				{
					upVideoName="cms" + "/" + link_id+"/"+sub_link_name+"/UploadVideos" + "/"+v[i];
			    			    upVideoName2+=upVideoName+",";
				}
				 String up2="cms" + "/" + link_id+"/"+sub_link_name+"/UploadVideos" +"/";
			        upVideoName2 = upVideoName2.substring(0, upVideoName2.length()-1);
					String updatesql=null;
					if(upVideoName2.equalsIgnoreCase(up2))
					{
						 updatesql="update links set video_name='' where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+link_id+"'";
					}
					else
					{
						 updatesql="update links set video_name='"+upVideoName2+"' where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+link_id+"'";
					}
		int a = adlinks.SqlExecuteUpdate(updatesql);
		if (a > 0) {
			linksForm.setMessage("videos deleted Successfully");
		ArrayList list = new ArrayList();
		String sql3="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+link_id+"' and file_name not like ''";
		ResultSet rs5 = adlinks.selectQuery(sql3);
		while(rs5.next()) 
		{
			linksForm = new LinksForm();
			linksForm.setFileList(rs5.getString("file_name"));
			list.add(linksForm);
		}
		
		request.setAttribute("listName", list);
		ArrayList list1 = new ArrayList();
		String sql4="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+link_id+"'and video_name not like ''";
		ResultSet rs13 = adlinks
				.selectQuery(sql4);
		while (rs13.next()) 
		{
			linksForm = new LinksForm();
			linksForm.setVideoFilesList(rs13.getString("video_name"));
			list1.add(linksForm);
		}
		request.setAttribute("listName1", list1);
		String sql19="select * from links where link_name='"+sub_link_name + "' and " +
		"status=1 and delete_status=1 and module='"+link_id+"'";
		 ResultSet rs19=adlinks.selectQuery(sql19);
		 while (rs19.next()) 
		 {
			 linksForm.setContentDescriptionAdmin(rs19.getString("content_description"));	 
		 }
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
		} 
		else {
			linksForm.setMessage("Error While deleteing Files ... Please check Entered Values");
		display(mapping, form, request, response);
		displaySublinks(mapping, form, request, response);
		return mapping.findForward("display1");
		}
			}catch (Exception e) {
				e.printStackTrace();
			}
		    display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display1");
		}
		
		
		
		public ActionForward updateCmsLinksContent1(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
				{
			
			System.out.println("***UPDATE THE CONTENT***");
			LinksForm linksForm = (LinksForm) form;
			HttpSession session=request.getSession();
			LinksDao adlinks = new LinksDao();
				try
				{
					String mainLinkName = linksForm.getLinkName();
					String subLinkName = linksForm.getSubLinkName();
					
					
					Enumeration<String> params = request
					.getParameterNames();
					String parameter;
					String content_description = "";
					while (params.hasMoreElements()) {
						parameter = params.nextElement();
						if (parameter.equalsIgnoreCase("EditorDefault")) {
							content_description += request.getParameter(parameter);
						}
					}
					linksForm.setContentDescriptionAdmin(content_description);
					String subLinkTitle = linksForm.getSubLinkTitle();
					String contentYear="0";
				contentYear = linksForm.getContentYear();
					
					
					
					
					String id=linksForm.getLinkId();
					FormFile myFile = linksForm.getIconNames();
				    String contentType = myFile.getContentType();
					String iconName = myFile.getFileName();
					byte[] fileData = myFile.getFileData();
					String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
					if(ext.equalsIgnoreCase("jpeg")||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("jpg"))||(ext.equalsIgnoreCase("gif")))
					{
						   
					
					
					String iconPath = getServlet().getServletContext().getRealPath("cms") + "/" + mainLinkName+"/"+subLinkName+"/links/" +
					""+subLinkTitle+"/images/"+contentYear+"/icon";
					
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
				 	 iconPath=uploadFilePath+"/EMicro Files/cms/" + mainLinkName+"/"+subLinkName+"/links/" +
					""+subLinkTitle+"/images/"+contentYear+"/icon";
					
					File destinationDir = new File(iconPath);
					if(!destinationDir.exists())
					{
						destinationDir.mkdirs();
					}
					if(!iconName.equals("")) {
						File fileToCreate = new File(iconPath, iconName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					
					request.setAttribute("iconName", iconName);
					
				  }
					   
			    String iconList="";
				if(iconName.equalsIgnoreCase(""))
				{}
				else
				{
					InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
				 	 Properties props = new Properties();
				 	props.load(in);
					in.close();
				 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
					  iconList = uploadFilePath+"/EMicro Files/cms/" + mainLinkName+"/"+subLinkName+"/links/" +
						""+subLinkTitle+"/images/"+contentYear+"/icon";
				}
				
				
				System.out.println("linksForm.getCmsLinkArchive()"+linksForm.getCmsLinkArchive());
				
				String archiveStatus="";
				String arch=linksForm.getCmsLinkArchive();
				if("on".equalsIgnoreCase(arch)){
					archiveStatus="1";
				}
				if("off".equalsIgnoreCase(arch)){
					archiveStatus="0";
				}
				if(arch==null){
					archiveStatus="0";
				}
				InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
			 	 Properties props = new Properties();
			 	props.load(in);
				in.close();
			 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
			 	String iconPath=uploadFilePath+"/EMicro Files/cms/" + mainLinkName+"/"+subLinkName+"/links/" +
					""+subLinkTitle+"/images/"+contentYear+"/icon";
				String fileName2=iconPath+"/"+ iconName;
				
				fileName2 = fileName2.replace("\\", "\\\\");
			
				String sql2="update cms_sublinks set icon_name='"+fileName2+"',content_description='"+content_description+"'," +
						"archived_status='"+archiveStatus+"'" +
						" where main_linkname='"+mainLinkName+"' and " +
						"sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
				if(iconName.equalsIgnoreCase(""))
				{
					sql2="update cms_sublinks set content_description='"+content_description+"'," +
					"archived_status='"+archiveStatus+"'" +
					" where main_linkname='"+mainLinkName+"' and " +
					"sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
				}
				int a=adlinks.SqlExecuteUpdate(sql2);
				
				
				String sql3="update cms_linksfilelist set " +
				"archived_status='"+archiveStatus+"'" +
				" where main_linkname='"+mainLinkName+"' and " +
				"sub_linkname='"+subLinkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";

				adlinks.SqlExecuteUpdate(sql3);
				
				
				ArrayList linkIdList=new ArrayList();
				ArrayList linkValueList=new ArrayList();
				ResultSet rs3 = adlinks.selectQuery("select * from links where status is null");
				
				
				while(rs3.next()) {
					linkIdList.add(rs3.getString("link_name"));
					linkValueList.add(rs3.getString("link_name"));
				}
				linksForm.setLinkIdList(linkIdList);
				linksForm.setLinkValueList(linkValueList);
				
				linksForm.setLinkName(mainLinkName);
				
				
				Date d = new Date();
				ArrayList years = new ArrayList();
			  	int pyear = 1900 + d.getYear();
				
				for(int i=pyear;i>=1950;i--){
					years.add(i);
				}
				
				
				linksForm.setYears(years);	
				linksForm.setContentYear(contentYear);
				
				if(a>0){
					
					linksForm.setMessage("Links Details Modified successfully");
				}
				
				String sql33="select * from cms_linksfilelist where sub_linkname='"+subLinkName + "' and " +
				" main_linkname='"+mainLinkName+"' and link_name='"+subLinkTitle+"' and content_year='"+contentYear+"'";
			
			
			ResultSet rs5 = adlinks.selectQuery(sql33);
			
			ArrayList list2=new ArrayList();
			int count=0;
			LinksForm linksForm1=null;
			while(rs5.next()) 
			{
				linksForm1 = new LinksForm();
				linksForm1.setImageId(rs5.getString("id")+","+count);
				
				String imagename=rs5.getString("file_name");
				int x1=imagename.lastIndexOf("/");
				imagename=imagename.substring(x1+1);
				linksForm1.setImageName(imagename);
				linksForm1.setImageTitle(rs5.getString("file_description"));
				
				list2.add(linksForm1);
				count++;
			}
			request.setAttribute("listName", list2);
				
				}catch (Exception e) {
					e.printStackTrace();
				}
			
				return mapping.findForward("displayCmsLinksModify");	
		       }
		
		
		
		
		public ActionForward updateInnerLinksContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
				{
			
			System.out.println("***UPDATE THE CONTENT***");
			LinksForm linksForm = (LinksForm) form;
			HttpSession session=request.getSession();
			LinksDao adlinks = new LinksDao();
				try
				{
					String linkName = linksForm.getLinkName();
					String sub_link_name = linksForm.getSubLinkName();
					String linkPath="newsAndMedia.do";
					String method="display1";
					String priority=linksForm.getPriority();
					String id=linksForm.getLinkId();
					FormFile myFile = linksForm.getIconNames();
				    String contentType = myFile.getContentType();
					String iconName = myFile.getFileName();
					byte[] fileData = myFile.getFileData();
					String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
					if(ext.equalsIgnoreCase("jpeg")||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("jpg"))||(ext.equalsIgnoreCase("gif")))
					{
						   
					String iconPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadIcons";
					
					
					File destinationDir = new File(iconPath);
					if(!destinationDir.exists())
					{
						destinationDir.mkdirs();
					}
					if(!iconName.equals("")) {
						File fileToCreate = new File(iconPath, iconName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					
					request.setAttribute("iconName", iconName);
					iconPath = iconPath.replace("\\", "\\\\");
				  }
					   
			    String iconList="";
				if(iconName.equalsIgnoreCase(""))
				{
					 String sql="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 System.out.println("******SQL OUT PUT WHEN ICON NOT UPLOADED==="+sql);
				ResultSet rs = adlinks.selectQuery(sql);
				while (rs.next()) 
				{
					  iconList=rs.getString("icon_name");	
				}
				}
				else
				{
					  iconList="cms" + "/" + linkName+"/"+sub_link_name+"/UploadIcons" + "/"+ iconName;
				}
				
				String updatesql2="update links set link_name='"+sub_link_name+"' where module='"+linkName+"' and " +
 		"id='"+id+"' and status=1";
				adlinks.SqlExecuteUpdate(updatesql2);
				
				String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
				Enumeration<String> params = request
				.getParameterNames();
		String parameter;
		String content_description = "";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
				ResultSet rs17 = adlinks.selectQuery(sqlselect);
				
				String fileList=null;
				String videoList=null;
				while (rs17.next()) 
				{   
					fileList=rs17.getString("file_name");
					videoList=rs17.getString("video_name");
				}
				String updatesql="update links set link_path='"+linkPath+"',method='"+method+"',priority='"+priority+"',icon_name='"+iconList+"',video_name='"+videoList+"',file_name='"+fileList+"',content_description='"+content_description+"' where  link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
		int a = adlinks.SqlExecuteUpdate(updatesql);
		if (a > 0) {
			linksForm.setMessage("Your details have been updated successfully");
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
			ResultSet rs5 = adlinks.selectQuery(sql3);
			while (rs5.next()) {

				linksForm = new LinksForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			String sql4="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
			ResultSet rs15 = adlinks
					.selectQuery(sql4);
			while (rs15.next()) {
				linksForm = new LinksForm();
				linksForm.setVideoFilesList(rs15.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);	
			 String sql="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
		ResultSet rs = adlinks.selectQuery(sql);
		while (rs.next()) 
		{
			linksForm.setIconList(rs.getString("icon_name"));	
		}
		} else {
			linksForm.setMessage("Error While updateing your details  ... Please check Entered Values");
		}
				}catch (Exception e) {
					e.printStackTrace();
				}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display1");	
		       }
		
		public ActionForward updateContent(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
				{
			
			System.out.println("***UPDATE THE CONTENT***");
			LinksForm linksForm = (LinksForm) form;
			HttpSession session=request.getSession();
			LinksDao adlinks = new LinksDao();
				try
				{
					String linkName = linksForm.getLinkName();
					String sub_link_name = linksForm.getSubLinkName();
					String linkPath="newsAndMedia.do";
					String method="display1";
					String priority=linksForm.getPriority();
					String id=linksForm.getLinkId();
					FormFile myFile = linksForm.getIconNames();
				    String contentType = myFile.getContentType();
					String iconName = myFile.getFileName();
					byte[] fileData = myFile.getFileData();
					String ext = iconName.substring(iconName.lastIndexOf('.') + 1);
					if(ext.equalsIgnoreCase("jpeg")||(ext.equalsIgnoreCase("png"))||(ext.equalsIgnoreCase("jpg"))||(ext.equalsIgnoreCase("gif")))
					{
						   
					String iconPath = getServlet().getServletContext().getRealPath("cms") + "/" + linkName+"/"+sub_link_name+"/UploadIcons";
					File destinationDir = new File(iconPath);
					if(!destinationDir.exists())
					{
						destinationDir.mkdirs();
					}
					if(!iconName.equals("")) {
						File fileToCreate = new File(iconPath, iconName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(myFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					
					request.setAttribute("iconName", iconName);
					iconPath = iconPath.replace("\\", "\\\\");
				  }
					   
			    String iconList="";
				if(iconName.equalsIgnoreCase(""))
				{
					 String sql="select * from links where link_name='"+sub_link_name + "' and " +
						"status=1 and delete_status=1 and module='"+linkName+"'";
					 System.out.println("******SQL OUT PUT WHEN ICON NOT UPLOADED==="+sql);
				ResultSet rs = adlinks.selectQuery(sql);
				while (rs.next()) 
				{
					  iconList=rs.getString("icon_name");	
				}
				}
				else
				{
					  iconList="cms" + "/" + linkName+"/"+sub_link_name+"/UploadIcons" + "/"+ iconName;
				}
				
				String updatesql2="update links set link_name='"+sub_link_name+"' where module='"+linkName+"' and " +
 		"id='"+id+"' and status=1";
				adlinks.SqlExecuteUpdate(updatesql2);
				
				String sqlselect="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
				Enumeration<String> params = request
				.getParameterNames();
		String parameter;
		String content_description = "";
		while (params.hasMoreElements()) {
			parameter = params.nextElement();
			if (parameter.equalsIgnoreCase("EditorDefault")) {
				content_description += request.getParameter(parameter);
			}
		}
				ResultSet rs17 = adlinks.selectQuery(sqlselect);
				
				String fileList=null;
				String videoList=null;
				while (rs17.next()) 
				{   
					fileList=rs17.getString("file_name");
					videoList=rs17.getString("video_name");
				}
				String updatesql="update links set link_path='"+linkPath+"',method='"+method+"',priority='"+priority+"',icon_name='"+iconList+"',video_name='"+videoList+"',file_name='"+fileList+"',content_description='"+content_description+"' where  link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
		int a = adlinks.SqlExecuteUpdate(updatesql);
		if (a > 0) {
			linksForm.setMessage("Your details have been updated successfully");
			ArrayList list = new ArrayList();
			String sql3="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
			ResultSet rs5 = adlinks.selectQuery(sql3);
			while (rs5.next()) {

				linksForm = new LinksForm();
				linksForm.setFileList(rs5.getString("file_name"));
				list.add(linksForm);
			}
			request.setAttribute("listName", list);
			ArrayList list1 = new ArrayList();
			String sql4="select * from links where link_name='"+sub_link_name + "' and " +
			"status=1 and delete_status=1 and module='"+linkName+"'";
			ResultSet rs15 = adlinks
					.selectQuery(sql4);
			while (rs15.next()) {
				linksForm = new LinksForm();
				linksForm.setVideoFilesList(rs15.getString("video_name"));
				list1.add(linksForm);
			}
			request.setAttribute("listName1", list1);	
			 String sql="select * from links where link_name='"+sub_link_name + "' and " +
				"status=1 and delete_status=1 and module='"+linkName+"'";
		ResultSet rs = adlinks.selectQuery(sql);
		while (rs.next()) 
		{
			linksForm.setIconList(rs.getString("icon_name"));	
		}
		} else {
			linksForm.setMessage("Error While updateing your details  ... Please check Entered Values");
		}
				}catch (Exception e) {
					e.printStackTrace();
				}
			display(mapping, form, request, response);
			displaySublinks(mapping, form, request, response);
			return mapping.findForward("display1");	
		       }
	}