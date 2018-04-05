package com.microlabs.newsandmedia.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.newsandmedia.form.ArchivesForm;

public class ArchivesAction extends DispatchAction {
	
	
	
	public ActionForward getSubSubLinksData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		NewsandMediaDao ad = new NewsandMediaDao();
		LinkedList subsubLinksData=new LinkedList();
		try{
			
			String linkName=request.getParameter("LinkName");
			archivesForm.setMainLinkName(linkName);
			
			int year=Integer.parseInt(request.getParameter("requiredYear"));
			archivesForm.setYear(year);
			String subLinkName=request.getParameter("SubLinkName");
			archivesForm.setLinkName(subLinkName);
			String subSubLinkName=request.getParameter("SubSubLinkName");
			archivesForm.setSubLinkName(subSubLinkName);
			
			String displaySubSubLinksData="select * from archieves where link_name='"+subSubLinkName+"' and module='"+linkName+"' " +
					" and sub_link='"+subLinkName+"' and year='"+year+"' and status='yes' ";
			ResultSet rsdisplaySubSubLinksData=ad.selectQuery(displaySubSubLinksData);
			while(rsdisplaySubSubLinksData.next())
			{
				archivesForm.setContentDescription(rsdisplaySubSubLinksData.getString("content_description"));
				request.setAttribute("contentDescription", rsdisplaySubSubLinksData.getString("content_description"));
				archivesForm.setFilePath(rsdisplaySubSubLinksData.getString("file_name"));
				archivesForm.setVideoPath(rsdisplaySubSubLinksData.getString("video_name"));
			}
			
		}catch (Exception e) {
			// TODO: handle exception
		}
		request.setAttribute("subsublinkdata", "subsublinkdata");
		return mapping.findForward("displayMainLinks");
	}
	
	
	public ActionForward getSubSubLinkYears(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		NewsandMediaDao ad = new NewsandMediaDao();
		LinkedList subsubLinksYearList=new LinkedList();
		try{
			
			String linkName=request.getParameter("LinkName");
			archivesForm.setMainLinkName(linkName);
			
			
			String subLink=request.getParameter("subLink");
			archivesForm.setLinkName(subLink);
			
			String subsubLink=request.getParameter("subsubLink");
			archivesForm.setSubLinkName(subsubLink);
			
			String geSubSubLinkYears="select year from archieves where link_name='"+subsubLink+"' and module='"+linkName+"' and sub_link='"+subLink+"' and status='yes'   group by year";
			ResultSet rsGeSubLinkYears=ad.selectQuery(geSubSubLinkYears);
			
			while(rsGeSubLinkYears.next()){
				ArchivesForm archivesForm3=new ArchivesForm();
				archivesForm3.setYear(rsGeSubLinkYears.getInt("year"));			
				subsubLinksYearList.add(archivesForm3);
			}	
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		request.setAttribute("subsubLinksYearList", subsubLinksYearList);
		
		
		
		
		return mapping.findForward("displayMainLinks");
	}
	
	public ActionForward subLinkContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {		
		ArchivesForm archivesForm=(ArchivesForm)form;
		NewsandMediaDao ad = new NewsandMediaDao();
		LinkedList subsubLinkList=new LinkedList();
		try{
		
			String linkName=request.getParameter("LinkName");
			archivesForm.setMainLinkName(linkName);
			
			int year=Integer.parseInt(request.getParameter("requiredYear"));
			archivesForm.setYear(year);
			String subLinkName=request.getParameter("SubLinkName");
			archivesForm.setLinkName(subLinkName);
			
			String displaySubLinksData="select * from archieves where link_name='"+subLinkName+"' and module='"+linkName+"' and year='"+year+"' and status='yes' ";
			ResultSet rsdisplayLinksData=ad.selectQuery(displaySubLinksData);
			while(rsdisplayLinksData.next())
			{
				archivesForm.setContentDescription(rsdisplayLinksData.getString("content_description"));
				request.setAttribute("contentDescription", rsdisplayLinksData.getString("content_description"));
				archivesForm.setFilePath(rsdisplayLinksData.getString("file_name"));
				archivesForm.setVideoPath(rsdisplayLinksData.getString("video_name"));
			}
			
			String subSubLinksList="select link_name from archieves where module='"+linkName+"' and status='yes' and sub_link='"+subLinkName+"' and year='"+year+"'  group by link_name ";
			System.out.println("subSubLinksList="+subSubLinksList);
			ResultSet rsSubSubLinksList=ad.selectQuery(subSubLinksList);
			
			while(rsSubSubLinksList.next()){
				ArchivesForm archivesForm3=new ArchivesForm();
				archivesForm3.setSubLinkName(rsSubSubLinksList.getString("link_name"));
				subsubLinkList.add(archivesForm3);
			
			}			
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("subLinksData1", "subLinksData1");
		request.setAttribute("subsubLinksList", subsubLinkList);
		return mapping.findForward("displayMainLinks");
	}
	
	
	public ActionForward getsubLinksYears(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		NewsandMediaDao ad = new NewsandMediaDao();
		LinkedList subLinksYearList=new LinkedList();
		try{
			
			String linkName=request.getParameter("LinkName");
			archivesForm.setLinkName(linkName);
			
			
			String subLink=request.getParameter("subLink");
			archivesForm.setSubLinkName(subLink);
			
			String geSubLinkYears="select year from archieves where link_name='"+subLink+"' and module='"+linkName+"' and status='yes'   group by year";
			ResultSet rsGeSubLinkYears=ad.selectQuery(geSubLinkYears);
			
			while(rsGeSubLinkYears.next()){
				ArchivesForm archivesForm1=new ArchivesForm();
				archivesForm1.setYear(rsGeSubLinkYears.getInt("year"));			
				subLinksYearList.add(archivesForm1);
			}	
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		request.setAttribute("subLinksYearList", subLinksYearList);
		return mapping.findForward("displayMainLinks");
	}
	
	
	
	
	public ActionForward linksContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		NewsandMediaDao ad = new NewsandMediaDao();
		LinkedList subLinks=new LinkedList();
		try{
			String linkName=request.getParameter("LinkName");
			archivesForm.setLinkName(linkName);
			
			int year=Integer.parseInt(request.getParameter("requiredYear"));
			archivesForm.setYear(year);
			String displayLinksData="select * from archieves where link_name='"+linkName+"' and year='"+year+"' and status='yes' ";
			ResultSet rsdisplayLinksData=ad.selectQuery(displayLinksData);
			while(rsdisplayLinksData.next())
			{
				archivesForm.setContentDescription(rsdisplayLinksData.getString("content_description"));
				request.setAttribute("contentDescription", rsdisplayLinksData.getString("content_description"));
				archivesForm.setFilePath(rsdisplayLinksData.getString("file_name"));
				/*String filePath=getServlet().getServletContext().getServletContextName();
				String videoPath=getServlet().getServletContext().getRealPath("cms")+"/"+rsdisplayLinksData.getString("video_name");*/
				archivesForm.setVideoPath(rsdisplayLinksData.getString("video_name"));
			}
			String subLinksList="select link_name from archieves where module='"+linkName+"' and year='"+year+"' and status='yes' and sub_link is null  group by link_name ";
			System.out.println("subLinks="+subLinksList);
			ResultSet rsSubLinksList=ad.selectQuery(subLinksList);
			
			while(rsSubLinksList.next()){
			
				ArchivesForm archivesForm2=new ArchivesForm();
				archivesForm2.setSubLinkName(rsSubLinksList.getString("link_name"));
				subLinks.add(archivesForm2);
				/*archivesForm.setContentDescription(rsLinksContent.getString("content_description"));
				request.setAttribute("contentDescription", rsLinksContent.getString("content_description"));
				archivesForm.setFilePath(rsLinksContent.getString("file_name"));
				archivesForm.setVideoPath(rsLinksContent.getString("video_name"));*/
							
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("linksContent", "linksContent");
		request.setAttribute("subLinksList", subLinks);
		return mapping.findForward("displayMainLinks");
	}
	
	
	public ActionForward subLinksContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		LinkedList SubLinksYear=new LinkedList();
		try{
			NewsandMediaDao ad = new NewsandMediaDao();
			String linkName=request.getParameter("LinkName");
			archivesForm.setLinkName(linkName);
			
			int year=Integer.parseInt(request.getParameter("requiredYear"));
			archivesForm.setYear(year);
			
			String sublinkName=request.getParameter("subLink");
			archivesForm.setSubLinkName(sublinkName);
			
			String subLinkContent="select * from archieves where year='"+year+"' and status='yes' and link_name='"+sublinkName+"'" +
				" and module='"+linkName+"' ";
			ResultSet rsSubLinkContent=ad.selectQuery(subLinkContent);
			while(rsSubLinkContent.next())
			{
				archivesForm.setContentDescription(rsSubLinkContent.getString("content_description"));
				request.setAttribute("contentDescription", rsSubLinkContent.getString("content_description"));
				archivesForm.setFilePath(rsSubLinkContent.getString("file_name"));
				archivesForm.setVideoPath(rsSubLinkContent.getString("video_name"));
			}
			
			
			
			String geSubLinkyears="select year from archieves where  link_name='"+sublinkName+"'" +
				" and module='"+linkName+"'   group by year";
			ResultSet rsSubLinkyears=ad.selectQuery(geSubLinkyears);
			
			while(rsSubLinkyears.next()){
				ArchivesForm archivesForm1=new ArchivesForm();
				archivesForm1.setYear(rsSubLinkyears.getInt("year"));			
				SubLinksYear.add(archivesForm1);
			}	
			
			
		}catch (Exception e) {
		e.printStackTrace();
		}
		request.setAttribute("SubLinksYear", SubLinksYear);
		request.setAttribute("SubLinkContent","SubLinkContent");	
		return mapping.findForward("displayMainLinks");
		
	}
	
	public ActionForward getSubSubLinksContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		LinkedList listOfSubSubLinks=new LinkedList();
		try{
		int year=Integer.parseInt(request.getParameter("requiredYear"));
		archivesForm.setYear(year);
		String mainLink=request.getParameter("MainLink");
		String subLink=request.getParameter("SubLink");
		String subsubLink=request.getParameter("SubSubLink");
		
		
		archivesForm.setLinkName(subsubLink);
		NewsandMediaDao ad = new NewsandMediaDao();

		
		String getSubSubLinksContent="select * from archieves where year='"+year+"' and status='yes' and link_name='"+subsubLink+"'" +
				" and module='"+mainLink+"' and sub_link='"+subLink+"' ";
	    System.out.println("getSubLinksContent="+getSubSubLinksContent);
		ResultSet rsSubSubLinksContent=ad.selectQuery(getSubSubLinksContent);
		
		while(rsSubSubLinksContent.next()){
			archivesForm.setContentDescription(rsSubSubLinksContent.getString("content_description"));
			request.setAttribute("contentDescription", rsSubSubLinksContent.getString("content_description"));
			archivesForm.setFilePath(rsSubSubLinksContent.getString("file_name"));
			archivesForm.setVideoPath(rsSubSubLinksContent.getString("video_name"));						
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		request.setAttribute("SubSubLinkContent","SubSubLinkContent");		
		return mapping.findForward("displayMainLinks");
	}
	
	
	public ActionForward getSubLinksContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		
		String linkName=request.getParameter("LinkName");
		archivesForm.setLinkName(linkName);
		
		int year=Integer.parseInt(request.getParameter("requiredYear"));
		archivesForm.setYear(year);
		
		LinkedList listOfSubLinks=new LinkedList();
		NewsandMediaDao ad = new NewsandMediaDao();
		try{
			int  requiredYear=Integer.parseInt(request.getParameter("requiredYear"));
			
			String linkname=request.getParameter("LinkName");
			
			String getSublinks="select * from archieves where year='"+requiredYear+"' and sub_link is null and module='"+linkname+"' and status='yes'";
			ResultSet rsSublinks=ad.selectQuery(getSublinks);
			while(rsSublinks.next()){
				ArchivesForm archSubLink=new ArchivesForm();
				archSubLink.setSubLinkName(rsSublinks.getString("link_name"));
				
				listOfSubLinks.add(archSubLink);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("SubLinksContent", "SubLinksContent");		
		request.setAttribute("listOfSubLinks", listOfSubLinks);		
		return mapping.findForward("displayMainLinks");
	}
	
	public ActionForward getLinkContent(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ArchivesForm archivesForm=(ArchivesForm)form;
		LinkedList listOfSubLinks=new LinkedList();
		
		
		
		try{
			
			String linkName=request.getParameter("requiredLink");
			archivesForm.setLinkName(linkName);
			NewsandMediaDao ad = new NewsandMediaDao();
			String geyears="select year from archieves where link_name='"+linkName+"' and status='yes'   group by year";
			ResultSet rsGetYear=ad.selectQuery(geyears);
			
			while(rsGetYear.next()){
				ArchivesForm archivesForm1=new ArchivesForm();
				archivesForm1.setYear(rsGetYear.getInt("year"));			
				listOfSubLinks.add(archivesForm1);
			}	
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		
		request.setAttribute("listOfSubLinks", listOfSubLinks);
		
		return mapping.findForward("displayMainLinks");
	}
	
	public ActionForward getMainLinks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("getMainLinks()----");
		ArchivesForm archivesForm=(ArchivesForm)form;
		LinkedList listOfMainLinks=new LinkedList();
		try{
		int year=Integer.parseInt(request.getParameter("requiredYear"));
		archivesForm.setYear(year);
		NewsandMediaDao ad = new NewsandMediaDao();

		
		String getMainLinks="select * from archieves where year='"+year+"' and module='Main' and status='yes'  and sub_link is NULL  ";
		ResultSet rsGetLinks=ad.selectQuery(getMainLinks);
		
		while(rsGetLinks.next()){
			ArchivesForm archivesForm1=new ArchivesForm();
			archivesForm1.setId(rsGetLinks.getInt("id"));
			archivesForm1.setLinkName(rsGetLinks.getString("link_name"));
			archivesForm1.setYear(rsGetLinks.getInt("year"));
			listOfMainLinks.add(archivesForm1);
		}			
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		request.setAttribute("listOfMainLinks", listOfMainLinks);		
		return mapping.findForward("displayMainLinks");
	}
		
	
	public ActionForward displayArchieves(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("displayArchieves");
		
		try{
			System.out.println("displayAwardsForm");
			
			String subLinkName=request.getParameter("subLink"); 	
			String module=request.getParameter("module");
			HttpSession session=request.getSession();
			String linkName=request.getParameter("linkName");
			
			
			LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
			
			
			NewsandMediaDao ad = new NewsandMediaDao();
			
			String sql="select * from links where link_name='"+linkName+"' and module='"+module+"' and" +
					" sub_linkname='"+subLinkName+"'";
			System.out.println("*****SQL QUERY OUTPUT IN DISPLAYCONTENT========"+sql);
			ResultSet rs=ad.selectQuery(sql);
			String iconName1="";
			try{
				
				while (rs.next()) {
					iconName1=rs.getString("icon_name");					
				}			
				
				System.out.println("****ICON NAME****===="+iconName1);
				
				
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			session.setAttribute("SUBLINKS", hm);
			
			String getMainLinks="select link_name from archieves where module='Main' and status='yes' group by  link_name";
			ResultSet rsMainLinks=ad.selectQuery(getMainLinks);
			LinkedList listOFMenu=new LinkedList();
			while(rsMainLinks.next())
			{
				ArchivesForm archivesForm=new ArchivesForm();
				archivesForm.setLinkName(rsMainLinks.getString("link_name"));
				listOFMenu.add(archivesForm);
			}
			
			request.setAttribute("listOFMenu", listOFMenu);
			
			}catch (Exception e) {
				e.printStackTrace();
			}

		return mapping.findForward("displayArchieves");
	}


}
