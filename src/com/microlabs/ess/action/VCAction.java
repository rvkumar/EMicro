package com.microlabs.ess.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.DishaDao;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.dao.VCMail;
import com.microlabs.ess.form.VCForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class VCAction extends DispatchAction {
	
	
	public ActionForward bulkverifyCompletedrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		String reqno=request.getParameter("reqno");
		String pernr=request.getParameter("pernr");
		String comments=request.getParameter("comments");
		
		
		 String[] mlocl=request.getParameterValues("selectedRequestNo");
		 String ACtualreq="";
			StringBuffer reqdept = new StringBuffer();
			
		  if(mlocl!=null)
			{
		  for(int i=0;i<mlocl.length;i++)
			{
			  reqdept.append("'"+mlocl[i]+"'" + ",");
			}
			}
		  if(reqdept.length()>1)
			{	
			ACtualreq = reqdept.substring(0, reqdept.length() - 1)
					.toString();
			}
			else
			{
				ACtualreq="''";
			} 

		String up = "Update data_Collection_list set admin_comments="+comments+" , Status='Approved' "
				+ " where req_no='"+reqno+"' and pernr in ("+ACtualreq+") ";
		int i = ad.SqlExecuteUpdate(up);
		 
		viewSubmittedrequest(mapping, form, request, response);
		
		return mapping.findForward("viewCompletedrequest");
	}
	
	public ActionForward verifyCompletedrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		String reqno=request.getParameter("reqno");
		String pernr=request.getParameter("pernr");
		String comments=request.getParameter("comments");

		String up = "Update data_Collection_list set admin_comments='"+comments+"' , Status='Approved' "
				+ " where req_no='"+reqno+"' and pernr='"+pernr+"' ";
		int i = ad.SqlExecuteUpdate(up);
		 
		viewSubmittedrequest(mapping, form, request, response);
		
		return mapping.findForward("viewSubmittedrequest");
	}
	
	
	public ActionForward revertCompletedrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		String reqno=request.getParameter("reqno");
		String pernr=request.getParameter("pernr");
		String comments=request.getParameter("comments");
		String filePath="";
		
		
		String sql="select * from data_Collection_list where req_no='"+reqno+"' and pernr='"+pernr+"' and  "
				+ " status ='Submitted' ";
		ResultSet rs = ad.selectQuery(sql);
		try {
			while(rs.next())
			{
			if(!rs.getString("file_name").equalsIgnoreCase(""))
			{
				
				InputStream in = ConnectionFactory.class
						.getClassLoader().getResourceAsStream(
								"db.properties");
				Properties props = new Properties();
				try {
					props.load(in);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String uploadFilePath = props
						.getProperty("file.uploadFilePath");
				System.out
						.println("required filepath="
								+ uploadFilePath
								+ rs.getString("Submitted_filepath"));
				filePath = uploadFilePath
						+ (String) rs.getString("Submitted_filepath").subSequence(0, rs.getString("Submitted_filepath").lastIndexOf("/"));

				File fileToCreate = new File(filePath,  rs.getString("pernr")+"_"+rs.getString("submitted_filename"));
				boolean test = fileToCreate.delete();
				System.out.println(test);
				
				
				String up = "Update data_Collection_list set Submitted_filename='' ,Submitted_filepath='',remarks='' ,Status='Pending',admin_comments='"+comments+"'  "
						+ " where req_no='"+reqno+"' and pernr='"+pernr+"' ";
				int i = ad.SqlExecuteUpdate(up);
			}
			}	 
			}
			catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 
		viewCompletedrequest(mapping, form, request, response);
		
		return mapping.findForward("viewCompletedrequest");
	}
	
	public ActionForward viewSubmittedrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		DishaDao ad1 = new DishaDao();
		VCForm help = (VCForm) form;
		String reqno=request.getParameter("reqno");
		String group_type=request.getParameter("group_type");
		
		String sql="select * from data_Collection_list where req_no='"+reqno+"' and"
				+ " status ='Submitted' ";
		ResultSet rs = ad.selectQuery(sql);
		String ACtualreq="";
		StringBuffer reqdept = new StringBuffer();
		try {
			while(rs.next())
			{
				reqdept.append("'"+rs.getString("pernr")+"'" + ",");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(reqdept.length()>1)
		{	
		ACtualreq = reqdept.substring(0, reqdept.length() - 1)
				.toString();
		}
		else
		{
			ACtualreq="''";
		}
		  ArrayList emplist = new ArrayList();
		  ResultSet rsa=null;
		  if(help.getGroup_type().equalsIgnoreCase("Field Staff"))
		    {
			  
		   
		  
		  sql="select employee_Master.employee_code,employee_Master.employee_name,division_code,s_owner_mst.description, "
		    		+ " s_state_mst.state_code,s_state_mst.state_name,employee_Master.hq_code,w_hq_mst_main.hq_name,"
		    		+ " Emp_Email_id.Email,designation_master.description as desdesc from w_user_master,employee_Master "
		    		+ " left outer join s_owner_mst on s_owner_mst.sa_id=employee_Master.division_code  "
		    		+ " left outer join s_state_mst on s_state_mst.state_code=employee_Master.state_code "
		    		+ " left outer join w_hq_mst_main on w_hq_mst_main.hq_code=employee_Master.hq_code "
		    		+ " left outer join Emp_Email_id on Emp_Email_id.Emp_Code=employee_Master.employee_code "
		    		+ " left outer join designation_master on designation_master.code =employee_Master.designatioN_code "
		    		+ " where employee_Master.employee_code=w_user_master.user_id and w_user_master.status='A' and "
		    		+ " isnumeric(employee_Master.employee_code)=1 and w_user_master.user_id in ("+ACtualreq+")"; 
		  rsa= ad1.selectQuery(sql);
		    }
		  else
		  {
			  sql=" select '' as description,pernr as employee_code, emp_fullname as employee_name ,"
		     		+ "des.DSGSTXT as desdesc ,'' as hq_name,'' as state_name , EMAIL_ID as email  "
		     		+ " from emp_official_info as emp ,DESIGNATION as des where pernr is not null"
		     		+ " and emp.dsgid=des.DSGID  and emp.pernr in  ("+ACtualreq+")";
			  rsa= ad.selectQuery(sql);
		  }
		  try {
			while(rsa.next())
			  {
				  	VCForm emp = new VCForm();
					emp.setDivisionid(rsa.getString("description"));
					emp.setEmpId(rsa.getString("employee_code"));
					emp.setEmpName(rsa.getString("employee_name"));
					emp.setDesg(rsa.getString("desdesc"));
					emp.setHeadquater(rsa.getString("hq_name"));
					emp.setState(rsa.getString("state_name"));
					emp.setEmpEmailID(rsa.getString("email"));
					
					String s1="select convert(nvarchar(10),submitted_date,103) as sub1 ,* from data_Collection_list where pernr='"+emp.getEmpId()+"' "
							+ " and req_no='"+reqno+"' ";
					ResultSet rs1 = ad.selectQuery(s1);
					if(rs1.next())
					{
					emp.setFilename(rs1.getString("submitted_filename") );
					emp.setPath(rs1.getString("Submitted_filepath"));
					emp.setSubmitDate(rs1.getString("sub1"));
					}
					
					help.setReqNo(reqno);
					emplist.add(emp);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  request.setAttribute("emplist",emplist);
		
		
		return mapping.findForward("viewSubmittedrequest");
	}
	
	
	public ActionForward viewCompletedrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		DishaDao ad1 = new DishaDao();
		VCForm help = (VCForm) form;
		String reqno=request.getParameter("reqno");
		String group_type=request.getParameter("group_type");
		
		String sql="select * from data_Collection_list where req_no='"+reqno+"' and"
				+ " status ='Approved' ";
		ResultSet rs = ad.selectQuery(sql);
		String ACtualreq="";
		StringBuffer reqdept = new StringBuffer();
		try {
			while(rs.next())
			{
				reqdept.append("'"+rs.getString("pernr")+"'" + ",");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(reqdept.length()>1)
		{	
		ACtualreq = reqdept.substring(0, reqdept.length() - 1)
				.toString();
		}
		else
		{
			ACtualreq="''";
		}
		  ArrayList emplist = new ArrayList();
		  ResultSet rsa =null;
		  if(help.getGroup_type().equalsIgnoreCase("Field Staff"))
			    {
		  sql="select employee_Master.employee_code,employee_Master.employee_name,division_code,s_owner_mst.description, "
		    		+ " s_state_mst.state_code,s_state_mst.state_name,employee_Master.hq_code,w_hq_mst_main.hq_name,"
		    		+ " Emp_Email_id.Email,designation_master.description as desdesc from w_user_master,employee_Master "
		    		+ " left outer join s_owner_mst on s_owner_mst.sa_id=employee_Master.division_code  "
		    		+ " left outer join s_state_mst on s_state_mst.state_code=employee_Master.state_code "
		    		+ " left outer join w_hq_mst_main on w_hq_mst_main.hq_code=employee_Master.hq_code "
		    		+ " left outer join Emp_Email_id on Emp_Email_id.Emp_Code=employee_Master.employee_code "
		    		+ " left outer join designation_master on designation_master.code =employee_Master.designatioN_code "
		    		+ " where employee_Master.employee_code=w_user_master.user_id and w_user_master.status='A' and "
		    		+ " isnumeric(employee_Master.employee_code)=1 and w_user_master.user_id in ("+ACtualreq+")"; 
		   rsa = ad1.selectQuery(sql);
			    }
		  else
		  {
			  sql=" select '' as description,pernr as employee_code, emp_fullname as employee_name ,"
			     		+ "des.DSGSTXT as desdesc ,'' as hq_name,'' as state_name , EMAIL_ID as email  "
			     		+ " from emp_official_info as emp ,DESIGNATION as des where pernr is not null"
			     		+ " and emp.dsgid=des.DSGID  and emp.pernr in  ("+ACtualreq+")";  
			  rsa = ad.selectQuery(sql);
		  }
		  try {
			while(rsa.next())
			  {
				  	VCForm emp = new VCForm();
					emp.setDivisionid(rsa.getString("description"));
					emp.setEmpId(rsa.getString("employee_code"));
					emp.setEmpName(rsa.getString("employee_name"));
					emp.setDesg(rsa.getString("desdesc"));
					emp.setHeadquater(rsa.getString("hq_name"));
					emp.setState(rsa.getString("state_name"));
					emp.setEmpEmailID(rsa.getString("email"));
					
					String s1="select convert(nvarchar(10),submitted_date,103) as sub1, * from data_Collection_list where pernr='"+emp.getEmpId()+"' "
							+ " and req_no='"+reqno+"' ";
					ResultSet rs1 = ad.selectQuery(s1);
					if(rs1.next())
					{
					emp.setFilename(rs1.getString("submitted_filename") );
					emp.setPath(rs1.getString("Submitted_filepath"));
					emp.setSubmitDate(rs1.getString("sub1") );
					emp.setSearchText(rs1.getString("admin_comments"));
					}
					
					help.setReqNo(reqno);
					emplist.add(emp);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  request.setAttribute("emplist",emplist);
		
		
		return mapping.findForward("viewCompletedrequest");
	}

	
	
	public ActionForward viewPendingrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		DishaDao ad1 = new DishaDao();
		VCForm help = (VCForm) form;
		String reqno=request.getParameter("reqno");
		
		String group_type=request.getParameter("group_type");
		
		String sql="select * from data_Collection_list where req_no='"+reqno+"' and"
				+ " status ='Pending' ";
		ResultSet rs = ad.selectQuery(sql);
		String ACtualreq="";
		StringBuffer reqdept = new StringBuffer();
		try {
			while(rs.next())
			{
				reqdept.append("'"+rs.getString("pernr")+"'" + ",");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(reqdept.length()>1)
		{	
		ACtualreq = reqdept.substring(0, reqdept.length() - 1)
				.toString();
		}
		else
		{
			ACtualreq="''";
		}
		  ArrayList emplist = new ArrayList();
		  ResultSet rsa = null ;
		  if(help.getGroup_type().equalsIgnoreCase("Field Staff"))
		    {
		  sql="select employee_Master.employee_code,employee_Master.employee_name,division_code,s_owner_mst.description, "
		    		+ " s_state_mst.state_code,s_state_mst.state_name,employee_Master.hq_code,w_hq_mst_main.hq_name,"
		    		+ " Emp_Email_id.Email,designation_master.description as desdesc from w_user_master,employee_Master "
		    		+ " left outer join s_owner_mst on s_owner_mst.sa_id=employee_Master.division_code  "
		    		+ " left outer join s_state_mst on s_state_mst.state_code=employee_Master.state_code "
		    		+ " left outer join w_hq_mst_main on w_hq_mst_main.hq_code=employee_Master.hq_code "
		    		+ " left outer join Emp_Email_id on Emp_Email_id.Emp_Code=employee_Master.employee_code "
		    		+ " left outer join designation_master on designation_master.code =employee_Master.designatioN_code "
		    		+ " where employee_Master.employee_code=w_user_master.user_id and w_user_master.status='A' and "
		    		+ " isnumeric(employee_Master.employee_code)=1 and w_user_master.user_id in ("+ACtualreq+")"; 
		  rsa = ad1.selectQuery(sql);
		    }
		  else
		  {
			  sql=" select '' as description,pernr as employee_code, emp_fullname as employee_name ,"
		     		+ "des.DSGSTXT as desdesc ,'' as hq_name,'' as state_name , EMAIL_ID as email  "
		     		+ " from emp_official_info as emp ,DESIGNATION as des where pernr is not null"
		     		+ " and emp.dsgid=des.DSGID  and emp.pernr in  ("+ACtualreq+")";
			  rsa = ad.selectQuery(sql);
		  }
		  try {
			while(rsa.next())
			  {
				  	VCForm emp = new VCForm();
					emp.setDivisionid(rsa.getString("description"));
					emp.setEmpId(rsa.getString("employee_code"));
					emp.setEmpName(rsa.getString("employee_name"));
					emp.setDesg(rsa.getString("desdesc"));
					emp.setHeadquater(rsa.getString("hq_name"));
					emp.setState(rsa.getString("state_name"));
					emp.setEmpEmailID(rsa.getString("email"));
					emplist.add(emp);
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  request.setAttribute("emplist",emplist);
		
		
		return mapping.findForward("viewPendingrequest");
	}
	public String createRar(String path,String reqno)
	{                
        try
        {
                String zipFile = (String) path.subSequence(0, path.lastIndexOf("/"))+"/"+reqno+".zip";
                String sourceDirectory = path;
               
                byte[] buffer = new byte[1024];
                 FileOutputStream fout = new FileOutputStream(zipFile);
                 ZipOutputStream zout = new ZipOutputStream(fout);
                 File dir = new File(sourceDirectory);
                 if(!dir.isDirectory())
                 {
                        System.out.println(sourceDirectory + " is not a directory");
                 }
                 else
                 {
                        File[] files = dir.listFiles();
                        for(int i=0; i < files.length ; i++)
                        {
                                System.out.println("Adding " + files[i].getName());
                                FileInputStream fin = new FileInputStream(files[i]);
                                zout.putNextEntry(new ZipEntry(files[i].getName()));
                                int length;
                                while((length = fin.read(buffer)) > 0)
                                {
                                   zout.write(buffer, 0, length);
                                }
                 
                                 zout.closeEntry();
                                 fin.close();
                        }
                 }
                  zout.close();
                 
                  System.out.println("Zip file has been created!");
       
        }
        catch(IOException ioe)
        {
                System.out.println("IOException :" + ioe);
        }
		String a = null;
		return a;
       
    }
	
	
	public ActionForward submitMultiraiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		String fromdate="";
		String todate="";
		
		
		if(help.getFromDate().contains("/"))
		{
			String returnd[]=help.getFromDate().split("/");
			fromdate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
			help.setFromDate(fromdate);
			
		}
		
		if(help.getToDate().contains("/"))
		{
			String returnd[]=help.getToDate().split("/");
			todate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
			help.setToDate(todate);
			
		}
		
		String sql="";
		if(help.getFrequency().equals("Daily"))
		{
			sql="select dateadd(day,-1+1 , thedate) as startdate ,thedate as enddate from "
					+ " ( SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate )t "
					+ " where DATEDIFF(day,'"+help.getFromDate()+"',thedate)%1=0 and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>0";
		}
		
		if(help.getFrequency().equals("Weekly"))
		{
			sql="select dateadd(day,-7 , thedate) as startdate ,thedate as enddate from "
					+ " (SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate )t "
					+ " where DATEDIFF(day,'"+help.getFromDate()+"',thedate)%7=0 and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>0";
		}
		
		if(help.getFrequency().equals("Fortnite"))
		{
			sql="select dateadd(day,-14 , thedate) as startdate ,thedate as enddate from "
					+ "(SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate )t "
					+ " where DATEDIFF(day,'"+help.getFromDate()+"',thedate)%14=0 and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>0";
		}
		
		if(help.getFrequency().equals("Monthly"))
		{
			sql=  " select  thedate as startdate ,dateadd(day,-1, dateadd(month,1,thedate)) as enddate from  ( "
					+ "SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate  "
					+ " )t where  day(thedate)=  day('"+help.getFromDate()+"') and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>=0 ";
					
		}
		
		if(help.getFrequency().equals("Quaterly"))
		{
			sql="select thedate as startdate ,dateadd(day,-1, dateadd(month,3,thedate)) as enddate from  ("
					+ " SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate  )t "
					+ " where day(thedate)=  day('"+help.getFromDate()+"') and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>=0 and"
					+ " DATEDIFF(month,thedate,'"+help.getFromDate()+"')%3=0 ";
		}
		
		if(help.getFrequency().equals("Halfyearly"))
		{
			sql="select thedate as startdate ,dateadd(day,-1, dateadd(month,6,thedate)) as enddate  from ( "
					+ " SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate)t  "
					+ " where day(thedate)=  day('"+help.getFromDate()+"') and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>=0 and"
					+ " DATEDIFF(month,thedate,'"+help.getFromDate()+"')%6=0 ";
		}
		
		if(help.getFrequency().equals("Yearly"))
		{
			sql="select thedate as startdate ,dateadd(day,-1, dateadd(month,12,thedate)) as enddate  from ( "
					+ " SELECT * FROM dbo.ExplodeDates('"+help.getFromDate()+"','"+help.getToDate()+"') as thedate  )t "
					+ " where day(thedate)=  day('"+help.getFromDate()+"') and DATEDIFF(day,'"+help.getFromDate()+"',thedate)>=0 and"
					+ " DATEDIFF(month,thedate,'"+help.getFromDate()+"')%12=0 ";
		}
		
		ResultSet ads=ad.selectQuery(sql);
		try {
			while(ads.next())
			{
				help.setFromDate1(ads.getString("startdate"));
				help.setToDate1(ads.getString("enddate"));
				submitraiserequest(mapping, form, request, response);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(!help.getFrequency().equalsIgnoreCase("no"))
		{
        	help.setFromDate("");
        	help.setToDate("");
		    displayraiserequest(mapping, form, request, response);
		}
		
		
		return mapping.findForward("myraiserequest");
	}
	
	
	public ActionForward submitMyraiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		String  todate="";
		
		FormFile documentFile = help.getDocumentFile();
		String documentName = documentFile.getFileName();
		
		
		
		
		String filePath = "";
		
		if(help.getSfilename()!=null)
		{	
		if(!help.getSfilename().equalsIgnoreCase(""))
		{
			
			InputStream in = ConnectionFactory.class
					.getClassLoader().getResourceAsStream(
							"db.properties");
			Properties props = new Properties();
			try {
				props.load(in);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String uploadFilePath = props
					.getProperty("file.uploadFilePath");
			System.out
					.println("required filepath="
							+ uploadFilePath
							+ help.getSpath());
			filePath = uploadFilePath
					+ (String)help.getSpath().subSequence(0, help.getSpath().lastIndexOf("/"));
			File fileToCreate = new File(filePath,  user.getEmployeeNo()+"_"+help.getSfilename());
			boolean test = fileToCreate.delete();
			System.out.println(test);
			
			
		}
		}
		
		
		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();
		if(!documentName.equalsIgnoreCase(""))
		{	
			
			if (filesize < 5000000) 
			{
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Data Collection/UploadFiles");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"/files");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"/files";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"/files";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				
				
				
				
				
						String contentType = documentFile.getContentType();
				
						
						
						String path = "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"/files"+"/"+documentFile.getFileName();
						String path1 = "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"/files"+"/"+user.getEmployeeNo()+"_"+documentFile.getFileName();
						String path2=uploadFilePath+path;
						String path3=uploadFilePath+path1;
						
						File oldfile =new File(path2);
						File newfile =new File(path3); 
						newfile.delete();
						if(oldfile.renameTo(newfile)){
							System.out.println("Rename succesful");
						}else{
							System.out.println("Rename failed");
						
						}
						
						
						
						String update="update data_collection_list set submitted_date=getdate(),Submitted_filename='"+documentFile.getFileName()+"' ,"
								+ " Submitted_filepath='"+path1+"' ,remarks='"+help.getSearchText()+"' ,Status='Submitted' "
								+ " where id='"+help.getId()+"' ";
					
							int a = ad.SqlExecuteUpdate(update);
							if (a > 0) {
								help.setMessage("Submitted Successfully");
							}
				
				}
				
			
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			
			
			 
			
			

		} else {
			help.setMessage("Upload extension files with size less than 1Mb");
		}
	}
		
		return mapping.findForward("myraiserequest");
	}
	
	
	public ActionForward viewmyraiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		
		VCForm help = (VCForm) form;
		String id=request.getParameter("reqno");
		
		String sql="select data_collection.req_no,data_collection.created_by,data_collection.description,data_collection.file_name,data_collection.created_date,data_collection.path,"
		 		+ " data_collection_list.status,data_collection_list.Submitted_filename,"
		 		+ " data_collection_list.remarks ,data_collection_list.Submitted_filepath from  data_collection,data_collection_list "
			 		+ " where data_collection_list.req_no= data_collection.req_no"
			 		+ " and  data_collection_list.id='"+id+"' ";
			 ResultSet rs = ad.selectQuery(sql);
			 try {
				if(rs.next())
				 {	 help.setId(id);
					 help.setReqNo(rs.getString("req_no"));
					 help.setEmpName(Empname(rs.getString("created_by")));
					 help.setDesc(rs.getString("description"));
					 help.setFilename(rs.getString("file_name"));
					 help.setToDate(rs.getString("created_date"));
					 help.setPath(rs.getString("path"));
					 help.setSearchText(rs.getString("remarks"));
					 help.setSfilename(rs.getString("Submitted_filename"));
					 help.setSpath(rs.getString("Submitted_filepath"));
					 help.setStatus(rs.getString("status"));
					 
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		
		return mapping.findForward("viewmyraiserequest");
	}
	
	
	public ActionForward myraiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		
		VCForm help = (VCForm) form;
		
		
		String fromdate="";
		String todate="";
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        Date date = new Date();
        String today = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -180);
        Date todate1 = cal.getTime();    
        String befordate = dateFormat.format(todate1);
        
        if(help.getFromDate()==null)
        {
        	help.setFromDate("");
        }
        if(help.getToDate()==null)
        {
        	help.setToDate("");
        }
	
		if(help.getFromDate().equalsIgnoreCase(""))
		{
			help.setFromDate(befordate);
		}
		if(help.getToDate().equalsIgnoreCase(""))
		{
			help.setToDate(today);
		}
		
		
		
		if(help.getReqstatus()==null)
		{
			help.setReqstatus("Pending");
		}
		
		  String from[]=help.getFromDate().split("/");
		  fromdate=from[2]+"-"+from[1]+"-"+from[0];
		  
		  
		    
		  String to[]=help.getToDate().split("/");
		  todate=to[2]+"-"+to[1]+"-"+to[0];
		  
		 ArrayList emplist = new ArrayList(); 	
		 String sql="select convert(nvarchar(10),data_collection.Start_date,103) as start_date1,"
		 		+ " convert(nvarchar(10),data_collection.last_date,103) as last_date1,"
		 		+ " convert(nvarchar(10),assigned_date,103) as assigned_date1,  "
		 		+ " data_collection.description,data_collection_list.status, data_collection_list.* from data_collection_list , data_collection "
		 		+ " where data_collection_list.req_no= data_collection.req_no and "
		 		+ " ((data_collection.start_date>= '"+fromdate+"' and "
		 		+ " data_collection.start_date<= '"+todate+"') or "
		 		+ " ( data_collection.f_submit_date>= '"+fromdate+"' and "
		 		+ " data_collection.f_submit_date<= '"+todate+"')) "
		 		+ " and pernr='"+user.getEmployeeNo()+"' ";
		 
		 
		 
		 
		 
		 if(help.getReqstatus()!=null)
		 { 
		 if(!help.getReqstatus().equalsIgnoreCase("all"))
		 		sql=sql+ " and data_collection_list.status='"+help.getReqstatus()+"' ";
		 }
		 sql =sql+" order by req_no desc";
		 ResultSet rs = ad.selectQuery(sql);
		 
		 try {
			while(rs.next())
			 {
				
			 VCForm emp = new  VCForm();
			 emp.setId(rs.getString("id"));
			 emp.setReqNo(rs.getString("req_no"));
			 emp.setEmpName(Empname(rs.getString("assigned_by")));
			 emp.setDesc(rs.getString("description"));
			 emp.setFilename(rs.getString("file_name"));
			 emp.setFromDate(rs.getString("Start_date1") );
			 emp.setToDate(rs.getString("last_date1"));
			 emp.setStatus(rs.getString("status"));
			 emplist.add(emp);
			
				 
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 request.setAttribute("emplist", emplist);
		return mapping.findForward("myraiserequest");
	}
	
	
	
	
	public ActionForward submitraiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		

		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		String fromdate="";
		String  todate="";
		FormFile documentFile = help.getDocumentFile();
		String documentName = documentFile.getFileName();
		int reqNo=0;
		
		synchronized (this) {
		
			String getMaxNo = "select isnull(max(req_no),0) from data_collection";
			ResultSet rs = ad.selectQuery(getMaxNo);
			try {
				if (rs.next()) {
					reqNo = rs.getInt(1) + 1;
				}
				help.setReqNo(Integer.toString(reqNo));
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		String filePath = "";
		
		
		
		if(help.getFromDate().contains("/"))
		{
			String returnd[]=help.getFromDate().split("/");
			fromdate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
			help.setFromDate(fromdate);
			
		}
		
		if(help.getToDate().contains("/"))
		{
			String returnd[]=help.getToDate().split("/");
			todate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
			help.setToDate(todate);
			
		}
		
		if(help.getSubdate().contains("/"))
		{
			String returnd[]=help.getSubdate().split("/");
			todate=returnd[2]+"-"+returnd[1]+"-"+returnd[0];
			help.setSubdate(todate);
			
		}
		
		
		String ext = documentName.substring(documentName.lastIndexOf('.') + 1);
		int filesize = documentFile.getFileSize();
		if(!documentName.equalsIgnoreCase(""))
		{	
		
		/*if ((ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx")
				|| ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("jpg")
				|| ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls") || ext
					.equalsIgnoreCase("txt")) && (filesize < 1048576)) */
			
			if (filesize < 5000000) 
			{
			try {
				byte[] size = documentFile.getFileData();
				if (!documentName.equalsIgnoreCase("")) {
					int length = documentName.length();
					int dot = documentName.lastIndexOf(".");
					String fileName = documentFile.getFileName();
					String extension = documentName.substring(dot, length);
					filePath = getServlet().getServletContext().getRealPath(
							"jsp/EMicro Files/ESS/Data Collection/UploadFiles");
					InputStream in = ConnectionFactory.class.getClassLoader()
							.getResourceAsStream("db.properties");
					Properties props = new Properties();
					props.load(in);
					in.close();
					String uploadFilePath = props
							.getProperty("file.uploadFilePath");
					System.out.println("required filepath=" + uploadFilePath
							+ "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"");
					filePath = uploadFilePath
							+ "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"";
					File destinationDir = new File(filePath);
					if (!destinationDir.exists()) {
						destinationDir.mkdirs();
					}
					if (!fileName.equals("")) {
						File fileToCreate = new File(filePath, fileName);
						if (!fileToCreate.exists()) {
							FileOutputStream fileOutStream = new FileOutputStream(
									fileToCreate);
							fileOutStream.write(documentFile.getFileData());
							fileOutStream.flush();
							fileOutStream.close();
						}
					}
					try {
						String filePath1 = "E:/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+"";
						byte[] fileData1 = documentFile.getFileData();
						InputStream is = new ByteArrayInputStream(fileData1);
						// boolean status=saveDataInJRS(is,fileName);
						// System.out.println("File status="+status);

						File destinationDir1 = new File(filePath1);
						if (!destinationDir1.exists()) {
							destinationDir1.mkdirs();
						}
						if (!fileName.equals("")) {
							File fileToCreate1 = new File(filePath1, fileName);
							if (!fileToCreate1.exists()) {
								FileOutputStream fileOutStream1 = new FileOutputStream(
										fileToCreate1);
								fileOutStream1
										.write(documentFile.getFileData());
								fileOutStream1.flush();
								fileOutStream1.close();
							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException fe) {
				fe.printStackTrace();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
			
			
			if(help.getFromDate1()==null)
			{
				help.setFromDate1(help.getFromDate());
			}
			
			if(help.getToDate1()==null)
			{
				help.setToDate1(help.getToDate());
			}
			
			
			

			String contentType = documentFile.getContentType();
			String fileName = documentFile.getFileName();
			try {
				String path = "/EMicro Files/ESS/Data Collection/UploadFiles/"+help.getReqNo()+""+"/"+documentFile.getFileName();	
				
				String sql = " insert into data_collection(req_no,category,description,last_date,file_name,path,remarks,created_by,start_date,"
						+ " frequency,act_start_date,act_last_date,title,f_submit_date,group_type)"
							+ "values('"
							+ reqNo
							+ "','"+help.getCategory()+""
							+ "','"+help.getDesc()+""
							+ "','"+help.getToDate1()+""
							+ "','"+documentFile.getFileName()+""
							+ "','"+path+""
							+ "','"+""+""
							+ "','"+user.getEmployeeNo()+"','"+help.getFromDate1()+"','"+help.getFrequency()+"'"
							+ ",'"+help.getFromDate()+"','"+help.getToDate()+"','"+help.getTitle()+"'"
							+ ", dateadd(day,DATEDIFF(day,'"+help.getFromDate()+"','"+help.getSubdate()+"'),'"+help.getFromDate1()+"') ,'"+help.getGroup_type()+"' )";
					int a = ad.SqlExecuteUpdate(sql);
					if (a > 0) {
						help.setMessage("Request No "+reqNo+" Created Successfully");
						help.setMessage2("");
					
	  String[] mlocl=request.getParameterValues("documentCheck");
		
	  if(mlocl!=null)
		{
	  for(int i=0;i<mlocl.length;i++)
		{
		  String sql1="insert into data_collection_list"
					+ "(req_no,pernr,file_name,path,assigned_by)"
			+ "values('"
			+ reqNo
			+ "','"+mlocl[i]+""
			+ "','"+documentFile.getFileName()+""
			+ "','"+path+""
			+ "','"+user.getEmployeeNo()+"' )";
			int a1 = ad.SqlExecuteUpdate(sql1);
			
			DishaDao ad1 = new DishaDao();
			String approvermail="";
			String sql1a="select email from Emp_Email_id "
					+ " where emp_code='"+mlocl[i]+"' ";
			ResultSet rs = ad1.selectQuery(sql1a);
			if(rs.next())
			{
				approvermail=rs.getString(1);
			}
			
			if(help.getFrequency().equalsIgnoreCase("no"))
			{	
			VCMail mail = new VCMail();
			mail.sendMailToEndUser(request, reqNo,	user.getEmployeeNo(), mlocl[i], "Pending");
			}
			
		}
		}
		}
		} 
		catch (Exception e) {
				e.printStackTrace();

			}

		} else {
			help.setMessage("Upload extension files with size less than 1Mb");
		}
	}
		

		if(help.getFrequency().equalsIgnoreCase("no"))
		{
        	help.setFromDate("");
        	help.setToDate("");
		   displayraiserequest(mapping, form, request, response);
		}
		return mapping.findForward("displayraiserequest");
	}
	
	
	
	
	public ActionForward searchMyrequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EssDao ad = new EssDao();
		VCForm conForm = (VCForm) form;
		
		
		
		return mapping.findForward("myraiserequest");
	}
	
	public ActionForward displayraiserequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		
		VCForm help = (VCForm) form;
		
		
			String fromdate="";
			String todate="";
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

        	Date date = new Date();
        	String today = dateFormat.format(date);

	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -7);
	        Date todate1 = cal.getTime();    
	        String befordate = dateFormat.format(todate1);
	        
	        if(help.getFromDate()==null)
	        {
	        	help.setFromDate("");
	        }
	        if(help.getToDate()==null)
	        {
	        	help.setToDate("");
	        }
		
			if(help.getFromDate().equalsIgnoreCase(""))
			{
				help.setFromDate(befordate);
			}
			if(help.getToDate().equalsIgnoreCase(""))
			{
				help.setToDate(today);
			}
			
			  String from[]=help.getFromDate().split("/");
			  fromdate=from[2]+"-"+from[1]+"-"+from[0];
			  
			  
			    
			  String to[]=help.getToDate().split("/");
			  todate=to[2]+"-"+to[1]+"-"+to[0];
			  
			 ArrayList emplist = new ArrayList(); 
			 String sql="select group_type,convert(nvarchar(10),Start_date,103) as start_date1,convert(nvarchar(10),f_submit_date,103) as last_date1,* from  data_collection,"
			 		+ " (select sum((case when status='Pending' then 1 else 0 end)) as Pending,"
			 		+ "  sum((case when status='Submitted' then 1 else 0 end)) as Submitted,"
			 		+ "  sum((case when status='Approved' then 1 else 0 end)) as Approved, "
			 		+ " req_no"
			 		+ "  from data_collection_list group by req_no)t"
			 		+ " where data_collection.req_no=t.req_no and  "
			 		+ " ((start_date>= '"+fromdate+"' and "
			 		+ " start_date<= '"+todate+"') or "
			 		+ " (f_submit_date>= '"+fromdate+"' and "
			 		+ " f_submit_date<= '"+todate+"')) "
			 		+ " and created_by='"+user.getEmployeeNo()+"' ";
			 
			 
			 if(help.getReqstatus()!=null)
			 { 
			 if(!help.getReqstatus().equalsIgnoreCase("all"))
			 		sql=sql+ " and data_collection.status='"+help.getReqstatus()+"' ";
			 }
			 sql=sql+"order by data_collection.req_no desc";
			 ResultSet rs = ad.selectQuery(sql);
			 try {
				while(rs.next())
				 {
				 VCForm emp = new  VCForm();
				 emp.setReqNo(rs.getString("req_no"));
				 
				 emp.setEmpName(Empname(rs.getString("Created_by")));
				 emp.setDesc(rs.getString("description"));
				 emp.setFilename(rs.getString("file_name"));
				 emp.setFromDate(rs.getString("Start_date1") );
				 emp.setToDate(rs.getString("last_date1"));
				 if(!rs.getString("pending").equalsIgnoreCase("0")||!rs.getString("submitted").equalsIgnoreCase("0"))
				 {
					 emp.setStatus("Pending");	 
				 }
				 else
				 {
					 emp.setStatus("Completed");
				 }
				 emp.setPendingApprover(rs.getString("pending") );
				 emp.setReqstatus(rs.getString("Submitted"));
				 emp.setCompleted(rs.getString("Approved"));
				 emp.setPath(rs.getString("path") );
				 emp.setFilename(rs.getString("file_name") );
				 emp.setGroup_type(rs.getString("group_type"));
				 emplist.add(emp);
					 
				 }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 request.setAttribute("emplist", emplist);
			 
			 return mapping.findForward("displayraiserequest");
	}
	
	public ActionForward getfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		EssDao ad = new EssDao();
		VCForm help = (VCForm) form;
		
		
		String path="C:/Tomcat 8.0/webapps"+request.getParameter("path");
		String reqno=request.getParameter("reqno");
		String rarpath=(String) path.subSequence(0, path.lastIndexOf("/"))+"/Files";
		String filepath=(String) path.subSequence(0, path.lastIndexOf("/"))+"/";
		createRar(rarpath,reqno);
		
		
		response.setContentType("text/html");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String filename = reqno+".zip"; 
		  response.setContentType("APPLICATION/OCTET-STREAM"); 
		  response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\""); 

		  java.io.FileInputStream fileInputStream = null;
		try {
			fileInputStream = new java.io.FileInputStream(filepath + filename);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  int i; 
		  try {
			while ((i=fileInputStream.read()) != -1) {
			    out.write(i); 
			  }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  try {
			fileInputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  out.close(); 
	
		
		
		return mapping.findForward("");
		
	}
	
	public ActionForward raiserequestlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		DishaDao ad1 = new DishaDao();
		EssDao ad = new EssDao();
		VCForm conForm = (VCForm) form;
		
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
		String dateNow = ft.format(dNow);
		
		conForm.setFromDate("");
		conForm.setToDate("");
		
		conForm.setReqDate(dateNow);
		
		
		ResultSet rs11 = ad.selectQuery("select * from location  ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
	
			try {
				while (rs11.next()) {
					locationList.add(rs11.getString("LOCATION_CODE"));
					locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
							+ rs11.getString("LOCNAME"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			
		//division
			
			
			ResultSet rs11a = ad.selectQuery("select  * from DIVISION_Head where DIV_head ='"+user.getEmployeeNo()+"'  ");
			ArrayList divList = new ArrayList();
			ArrayList divLabelList = new ArrayList();
		
				try {
					while (rs11a.next()) {
						divList.add(rs11a.getString("DIV_CODE"));
						divLabelList.add(rs11a.getString("DIV_CODE") + " - "
								+ rs11a.getString("DIV_DESC"));
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				conForm.setDivList(divList);
				conForm.setDivLabelList(divLabelList);
				
				
		
		//state
			
				ResultSet rs11ab = ad1.selectQuery(" select * from s_state_mst");
				ArrayList stateList = new ArrayList();
				ArrayList stateLabelList = new ArrayList();
			
					try {
						while (rs11ab.next()) {
							stateList.add(rs11ab.getString("state_code"));
							stateLabelList.add(rs11ab.getString("state_code") + " - "
									+ rs11ab.getString("State_name"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					conForm.setStateList(stateList);
					conForm.setStateLabelList(stateLabelList);
					
				
					String ACtualstate="";
				    if(conForm.getStateArray()!=null)
				    {	
				    String[] state = conForm.getStateArray();
				    
				    StringBuffer reqdept = new StringBuffer();
					for (int i = 0; i < state.length; i++) {
						reqdept.append("'"+state[i]+"'" + ",");
					}
					ACtualstate= reqdept.substring(0, reqdept.length() - 1).toString();
				    }
				    else
				    {
				    	ACtualstate="''";
				    }
			
		//hq			
					
					ResultSet rs11hq = ad1.selectQuery("select * from w_hq_mst_main"
							+ " where state_code in ("+ACtualstate+")");
					ArrayList hqList = new ArrayList();
					ArrayList hqLabelList = new ArrayList();
				
						try {
							while (rs11hq.next()) {
								hqList.add(rs11hq.getString("hq_code"));
								hqLabelList.add(rs11hq.getString("hq_code") + " - "
										+ rs11hq.getString("hq_name"));
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						conForm.setHqList(hqList);
						conForm.setHqLabelList(hqLabelList);
						
		//desg
						
									
						ResultSet rs11hqa = ad1.selectQuery("select * from designation_master");
						ArrayList desgList = new ArrayList();
						ArrayList desgLabelList = new ArrayList();
					
							try {
								while (rs11hqa.next()) {
									desgList.add(rs11hqa.getString("code"));
									desgLabelList.add(rs11hqa.getString("code")+ " - "
											+ rs11hqa.getString("description"));
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							conForm.setDesgList(desgList);
							conForm.setDesgLabelList(desgLabelList);		
		//dept
							
							ArrayList deptList=new ArrayList();
							ArrayList deptLabelList=new ArrayList();
							ResultSet rs15 = ad.selectQuery("select dptid," +
									"DPTLTXT from DEPARTMENT order by DPTLTXT "); 
							try {
										while(rs15.next()) {
											deptList.add(rs15.getString("dptid"));
											deptLabelList.add(rs15.getString("DPTLTXT"));
										}
										rs15.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
							conForm.setDeptList(deptList);
							conForm.setDeptLabelList(deptLabelList);
							
			
		//desg
							
							ArrayList desginationList=new ArrayList();
							ArrayList desginationLabelList=new ArrayList();
							ResultSet rs15a = ad.selectQuery("select DSGID,DSGSTXT " +
									" from designation order by DSGSTXT "); 
							try {
										while(rs15a.next()) {
											desginationList.add(rs15a.getString("DSGID"));
											desginationLabelList.add(rs15a.getString("DSGSTXT"));
										}
										rs15a.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
							conForm.setDesginationList(desginationList);
							conForm.setDesginationLabelList(desginationLabelList);
							
							
							
							
		
		return mapping.findForward("raiserequestlist");
		
	}
	
	
	public ActionForward serachraiserequestlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
	    
		
		DishaDao ad1 = new DishaDao();
		EssDao ad = new EssDao();
		
		VCForm help = (VCForm) form;
		raiserequestlist(mapping, form, request, response);
		String ACtualstate="";
	    if(help.getStateArray()!=null)
	    {	
	    String[] state = help.getStateArray();
	    
	    StringBuffer reqdept = new StringBuffer();
		for (int i = 0; i < state.length; i++) {
			reqdept.append("'"+state[i]+"'" + ",");
		}
		ACtualstate= reqdept.substring(0, reqdept.length() - 1).toString();
	    }
	    
	    

		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("dd-MM-yyyy");
		String dateNow = ft.format(dNow);
		
		
		help.setReqDate(dateNow);
		
	    
	    String Hqstate="";
	    if(help.getHqArray()!=null)
	    {	
	    String[] hq = help.getHqArray();
	    
	    StringBuffer reqHq = new StringBuffer();
		for (int i = 0; i < hq.length; i++) {
			reqHq.append("'"+hq[i]+"'" + ",");
		}
		Hqstate= reqHq.substring(0, reqHq.length() - 1).toString();
	    }
	    
	    
	    String Hqdesg="";
	    if(help.getDesgArray()!=null)
	    {	
	    String[] hq = help.getDesgArray();
	    
	    StringBuffer reqHq = new StringBuffer();
		for (int i = 0; i < hq.length; i++) {
			reqHq.append("'"+hq[i]+"'" + ",");
		}
		Hqdesg= reqHq.substring(0, reqHq.length() - 1).toString();
	    }
	    
	    
	    String Hqdept="";
	    if(help.getDeptArray()!=null)
	    {	
	    String[] hq = help.getDeptArray();
	    
	    StringBuffer reqHq = new StringBuffer();
		for (int i = 0; i < hq.length; i++) {
			reqHq.append("'"+hq[i]+"'" + ",");
		}
		Hqdept= reqHq.substring(0, reqHq.length() - 1).toString();
	    }
	    
	    
	    
	    
	    String corpdesg="";
	    if(help.getDesginationArray()!=null)
	    {	
	    String[] hq = help.getDesginationArray();
	    
	    StringBuffer reqHq = new StringBuffer();
		for (int i = 0; i < hq.length; i++) {
			reqHq.append("'"+hq[i]+"'" + ",");
		}
		corpdesg= reqHq.substring(0, reqHq.length() - 1).toString();
	    }
	    
	    
	    
	    String sql="";
	    ArrayList emplist = new ArrayList();
	    ResultSet rs ;
	    if(help.getGroup_type().equalsIgnoreCase("Field Staff"))
	    {	
	     sql="select employee_Master.employee_code,employee_Master.employee_name,division_code,s_owner_mst.description, "
	    		+ " s_state_mst.state_code,s_state_mst.state_name,employee_Master.hq_code,w_hq_mst_main.hq_name,"
	    		+ " Emp_Email_id.Email,designation_master.description as desdesc from w_user_master,employee_Master "
	    		+ " left outer join s_owner_mst on s_owner_mst.sa_id=employee_Master.division_code  "
	    		+ " left outer join s_state_mst on s_state_mst.state_code=employee_Master.state_code "
	    		+ " left outer join w_hq_mst_main on w_hq_mst_main.hq_code=employee_Master.hq_code "
	    		+ " left outer join Emp_Email_id on Emp_Email_id.Emp_Code=employee_Master.employee_code "
	    		+ " left outer join designation_master on designation_master.code =employee_Master.designatioN_code "
	    		+ " where employee_Master.employee_code=w_user_master.user_id and w_user_master.status='A' and isnumeric(employee_Master.employee_code)=1 "
	    		+ " and s_owner_mst.sa_id='"+help.getDivisionid()+"' ";
	    		if(!Hqstate.equalsIgnoreCase(""))
	    		sql=sql+ " and w_hq_mst_main.hq_code in ("+Hqstate+" ) ";
	    		if(!ACtualstate.equalsIgnoreCase(""))		
	    		sql=sql+  "and s_state_mst.state_code in ("+ACtualstate+" ) ";
	    		if(!Hqdesg.equalsIgnoreCase(""))		
				    sql=sql+  "and employee_Master.designation_code in ("+Hqdesg+") ";
	    		System.out.println(sql);
	    		rs = ad1.selectQuery(sql);
	    }
	    else
	    {	
		     sql="select '' as description,pernr as employee_code, emp_fullname as employee_name ,"
		     		+ "des.DSGSTXT as desdesc ,'' as hq_name,'' as state_name , EMAIL_ID as email  "
		     		+ " from emp_official_info as emp ,DESIGNATION as des where pernr is not null"
		     		+ " and emp.dsgid=des.DSGID  and emp.locid='"+help.getLocationId()+"' and emp.active=1 ";
		     if(!Hqdept.equalsIgnoreCase(""))		
				    sql=sql+  "and emp.DPTID in ("+Hqdept+") ";
		     
		     if(!corpdesg.equalsIgnoreCase(""))		
				    sql=sql+  "and emp.dsgid in ("+corpdesg+") ";
		     
		     System.out.println(sql);
			 rs = ad.selectQuery(sql);
	    }
	    
	     
	    try {
			while(rs.next())
			{
			VCForm emp = new VCForm();
			emp.setDivisionid(rs.getString("description"));
			emp.setEmpId(rs.getString("employee_code"));
			emp.setEmpName(rs.getString("employee_name"));
			emp.setDesg(rs.getString("desdesc"));
			emp.setHeadquater(rs.getString("hq_name"));
			emp.setState(rs.getString("state_name"));
			emp.setEmpEmailID(rs.getString("email"));
			emplist.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    request.setAttribute("emplist", emplist);
	    	
	    
		
		return mapping.findForward("raiserequestlist");
		
	}

	public ActionForward searchConfRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		String locId = conForm.getLocationId();
		String floor = conForm.getFloor();
		String room = conForm.getRoomName();
		request.setAttribute("ResetSearch", "ResetSearch");
		ResultSet rs11 = ad.selectQuery("select  * from VCRoom_List,Location where Location.locid=VCRoom_List.locid");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "	+ rs11.getString("LOCNAME")+ " ; "	+rs11.getString("room_name")+ " ; "	+rs11.getString("Floor"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			String reqFromDate = conForm.getFromDate();
			String a1[] = reqFromDate.split("/");
			reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
			String fromtime = conForm.getFromTime();
			String pm = "PM";
			if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
				fromtime = fromtime.replaceAll("PM", "");
				String a[] = fromtime.split(":");
				if (Integer.parseInt(a[0]) != 12) {
					int hr = Integer.parseInt(a[0]) + 12;
					reqFromDate = reqFromDate + " " + hr + ":" + a[1];
				} else {
					fromtime = fromtime.replaceAll("PM", "");
					reqFromDate = reqFromDate + " " + fromtime;
				}
			} else {
				fromtime = fromtime.replaceAll("AM", "");
				reqFromDate = reqFromDate + " " + fromtime;
			}

			String reqToDate = conForm.getFromDate();
			String b1[] = reqToDate.split("/");
			reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
			String totime = conForm.getToTime();
			if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
				totime = totime.replaceAll("PM", "");
				String a[] = totime.split(":");
				if (Integer.parseInt(a[0]) != 12) {
					int hr = Integer.parseInt(a[0]) + 12;
					reqToDate = reqToDate + " " + hr + ":" + a[1];
				} else {
					totime = totime.replaceAll("PM", "");
					reqToDate = reqToDate + " " + totime;
				}
			} else {
				totime = totime.replaceAll("AM", "");
				reqToDate = reqToDate + " " + totime;
			}

			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String dateNow = ft.format(dNow);
			List resarvedList = new LinkedList();
		/*	String checkAvilbilty = "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
					+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,"
					+ "emp_official_info emp,Location loc where ((conf.Req_From_Date between '"
					+ reqFromDate
					+ "' and '"
					+ reqToDate
					+ "') or (conf.Req_To_Date between '"
					+ reqFromDate
					+ "' and '"
					+ reqToDate
					+ "')) and emp.PERNR=conf.Requster_Id "
					+ "and conf.Loc_Id=loc.LOCID and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";

			if (conForm.getFromTime().equals("")) {
				String fromDate = conForm.getFromDate();
				String b2[] = fromDate.split("/");
				fromDate = b2[2] + "-" + b2[1] + "-" + b2[0];
				checkAvilbilty = "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
						+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,"
						+ "emp_official_info emp,Location loc where (conf.From_Date='"
						+ fromDate
						+ "' or conf.To_Date='"
						+ fromDate
						+ "' ) and emp.PERNR=conf.Requster_Id "
						+ "and conf.Loc_Id=loc.LOCID and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";

			}*/

			
			String checkAvilbilty = "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
					+ "conf.To_Date,conf.To_Time,conf.approval_status,(select top 1 LOCATION_CODE+'-'+LOCNAME+'-'+room_name+'-'+Floor from VCRoom_List, Location where Location.locid=VCRoom_List.locid  and  VCRoom_List.locid = vc_from) as vcf , "
					+ " (select top 1 LOCATION_CODE+'-'+LOCNAME+'-'+room_name+'-'+Floor from VCRoom_List,Location where Location.locid=VCRoom_List.locid and  VCRoom_List.locid = vc_to  )vct from VCRoom_Details conf,emp_official_info emp,Location loc "
					+ "where (( Req_From_Date between '"
					+ reqFromDate
					+ "'  and '"
					+ reqToDate
					+ "') or ( Req_To_Date  between '"
					+ reqFromDate
					+ "'  and '"
					+ reqToDate
					+ "')or  ( '"+reqFromDate+"' between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   )) "
					+ " and (conf.Loc_Id='"+locId+"'  or conf.vc_from='"+locId+"'  or conf.vc_to='"+locId+"'  ) "
					+ " and emp.PERNR=conf.Requster_Id and "
					+ "conf.Loc_Id=loc.LOCID and approval_status not in ('Rejected','Self Cancelled') "
					+ "union"
					+ " select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
					+ "conf.To_Date,conf.To_Time,conf.approval_status ,(select top 1 LOCATION_CODE+'-'+LOCNAME+'-'+room_name+'-'+Floor from VCRoom_List, Location where Location.locid=VCRoom_List.locid  and  VCRoom_List.locid = vc_from) as vcf , (select top 1 LOCATION_CODE+'-'+LOCNAME+'-'+room_name+'-'+Floor from VCRoom_List,Location where Location.locid=VCRoom_List.locid and  VCRoom_List.locid = vc_to  )vct "
					+ " from VCRoom_Details conf,emp_official_info emp,Location loc where"
					+ " (( Req_From_Date between"
					+ " '"+reqFromDate+"'  and '"+reqToDate+"') or ( Req_To_Date  between '"+reqFromDate+"'  and '"+reqToDate+"')or  ( '"+reqFromDate+"'"
					+ " between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   ))  "
					+ "and (conf.Loc_Id='16'  or conf.vc_from='16' or conf.vc_to='16'  ) "
					+ " and emp.PERNR=conf.Requster_Id  and emp.LOCID=loc.location_code  and approval_status not in ('Rejected','Self Cancelled')";
			
			ResultSet rs = ad.selectQuery(checkAvilbilty);
			try {
				while (rs.next()) {
					VCForm c = new VCForm();
					c.setReqNo(rs.getString("Reqest_No"));
					c.setLocation(rs.getString("LOCATION_CODE"));
					c.setEmpName(rs.getString("EMP_FULLNAME"));
					c.setFloor(rs.getString("Floor"));
					c.setRoomName(rs.getString("Conf_Room"));
					c.setFromDate(rs.getString("From_Date") + " "
							+ rs.getString("From_Time"));
					c.setToDate(rs.getString("To_Date") + " "
							+ rs.getString("To_Time"));
					c.setApprovalStatus(rs.getString("approval_status"));
					c.setVcFrom(rs.getString("vcf"));
					c.setVcTo(rs.getString("vct"));
					resarvedList.add(c);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resarvedList.size() > 0) {
				request.setAttribute("resarvedList", resarvedList);
			} else {
				request.setAttribute("NoRecords", "NoRecords");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward("confBookedList");
	}

	public ActionForward checkAvailablety1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		String locId = request.getParameter("locID");
		String floor = request.getParameter("floor");
		String room = request.getParameter("room");
		String reqFromDate = request.getParameter("fromDate");
		String vcFrom=request.getParameter("vcFrom");
		String vcTo=request.getParameter("vcTo");
		
		String a1[] = reqFromDate.split("/");
		reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
		String fromtime = request.getParameter("formTime");
		String pm = "PM";
		if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			fromtime = fromtime.replaceAll("PM", "");
			String a[] = fromtime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqFromDate = reqFromDate + " " + hr + ":" + a[1];
			} else {
				fromtime = fromtime.replaceAll("PM", "");
				reqFromDate = reqFromDate + " " + fromtime;
			}
		} else {
			fromtime = fromtime.replaceAll("AM", "");
			reqFromDate = reqFromDate + " " + fromtime;
		}

		String reqToDate = request.getParameter("fromDate");
		String b1[] = reqToDate.split("/");
		reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
		String totime = request.getParameter("toTime");
		if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			totime = totime.replaceAll("PM", "");
			String a[] = totime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqToDate = reqToDate + " " + hr + ":" + a[1];
			} else {
				totime = totime.replaceAll("PM", "");
				reqToDate = reqToDate + " " + totime;
			}
		} else {
			totime = totime.replaceAll("AM", "");
			reqToDate = reqToDate + " " + totime;
		}
		String fromDt = EMicroUtils.dateConvert(request
				.getParameter("fromDate"));
		String toDt = EMicroUtils.dateConvert(request.getParameter("toDate"));
		int count = 0;

		List resarvedList = new LinkedList();
		/*
		 * select
		 * conf.Reqest_No,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf
		 * .Conf_Room
		 * ,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf
		 * .approval_status from VCRoom_Details conf,emp_official_info
		 * emp,Location loc where (('2014-08-25 10:03:00.000' between
		 * Req_From_Date and Req_To_Date) or ('2014-08-25 11:03:00.000' between
		 * Req_From_Date and Req_To_Date)) and emp.PERNR=conf.Requster_Id and
		 * conf.Loc_Id=loc.LOCID
		 */

		
		String checkAvilbilty = "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
				+ "conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,emp_official_info emp,Location loc "
				+ "where (( Req_From_Date between '"
				+ reqFromDate
				+ "'  and '"
				+ reqToDate
				+ "') or ( Req_To_Date  between '"
				+ reqFromDate
				+ "'  and '"
				+ reqToDate
				+ "')or  ( '"+reqFromDate+"' between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   )) "
				+ " and (conf.Loc_Id='"+locId+"' or conf.Loc_Id='"+vcFrom+"' or conf.Loc_Id='"+vcTo+"' or conf.vc_from='"+locId+"' or conf.vc_from='"+vcFrom+"' or conf.vc_from='"+vcTo+"' or conf.vc_to='"+locId+"' or conf.vc_to='"+vcFrom+"' or conf.vc_to='"+vcTo+"' ) "
				+ " and emp.PERNR=conf.Requster_Id and "
				+ "conf.Loc_Id=loc.LOCID and approval_status not in ('Rejected','Self Cancelled')";
		ResultSet rs = ad.selectQuery(checkAvilbilty);
		try {
			while (rs.next()) {
				VCForm c = new VCForm();
				c.setReqNo(rs.getString("Reqest_No"));
				c.setLocation(rs.getString("LOCATION_CODE"));
				c.setEmpName(rs.getString("EMP_FULLNAME"));
				c.setFloor(rs.getString("Floor"));
				c.setRoomName(rs.getString("Conf_Room"));
				c.setFromDate(rs.getString("From_Date") + " "
						+ rs.getString("From_Time"));
				c.setToDate(rs.getString("To_Date") + " "
						+ rs.getString("To_Time"));
				c.setApprovalStatus(rs.getString("approval_status"));
				resarvedList.add(c);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (resarvedList.size() > 0) {
			request.setAttribute("resarvedList", resarvedList);
		} else {
			request.setAttribute("NoRecords", "NoRecords");
		}

		return mapping.findForward("availabletyStatus");
	}

	public ActionForward checkAvailablety(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		String locId = request.getParameter("locID");
		String floor = request.getParameter("floor");
		String room = request.getParameter("room");
		String reqFromDate = request.getParameter("fromDate");

		String a1[] = reqFromDate.split("/");
		reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
		String fromtime = request.getParameter("formTime");
		String pm = "PM";
		if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			fromtime = fromtime.replaceAll("PM", "");
			String a[] = fromtime.split(":");
			int hr = Integer.parseInt(a[0]) + 12;
			reqFromDate = reqFromDate + " " + hr + ":" + a[1];
		} else {
			fromtime = fromtime.replaceAll("AM", "");
			reqFromDate = reqFromDate + " " + fromtime;
		}

		String reqToDate = request.getParameter("fromDate");
		String b1[] = reqToDate.split("/");
		reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
		String totime = request.getParameter("toTime");
		if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			totime = totime.replaceAll("PM", "");
			String a[] = totime.split(":");
			int hr = Integer.parseInt(a[0]) + 12;
			reqToDate = reqToDate + " " + hr + ":" + a[1];
		} else {
			totime = totime.replaceAll("AM", "");
			reqToDate = reqToDate + " " + totime;
		}
		String fromDt = EMicroUtils.dateConvert(request
				.getParameter("fromDate"));
		String toDt = EMicroUtils.dateConvert(request.getParameter("toDate"));
		int count = 0;
		String checkAvilbilty = "select count(*) from VCRoom_Details where '"
				+ reqFromDate + "' between Req_From_Date and Req_To_Date";
		ResultSet rsChekFromDt = ad.selectQuery(checkAvilbilty);
		try {
			while (rsChekFromDt.next()) {
				count = rsChekFromDt.getInt(1);
			}
			checkAvilbilty = "select count(*) from VCRoom_Details where '"
					+ reqToDate + "' between Req_From_Date and Req_To_Date";
			ResultSet rsChekToDt = ad.selectQuery(checkAvilbilty);
			while (rsChekToDt.next()) {
				count = count + rsChekToDt.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count > 0) {
			conForm.setStatus("Booked");
			request.setAttribute("status", "Reserved");
		} else {
			conForm.setStatus("Available");
			request.setAttribute("status", "Available");
		}

		return mapping.findForward("availabletyStatus");
	}

	public ActionForward submitDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		userDetails(mapping, form, request, response);
		ResultSet rs11 = ad.selectQuery("select  * from VCRoom_List,Location where Location.locid=VCRoom_List.locid");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID")+","+rs11.getString("room_name")+ ","	+rs11.getString("Floor"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "	+ rs11.getString("LOCNAME")+ "|"	+rs11.getString("room_name")+ "|"	+rs11.getString("Floor"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateNow = ft.format(dNow);

		String locId = request.getParameter("locID");
		String floor = request.getParameter("floor");
		String room = request.getParameter("room");
		String reqFromDate = request.getParameter("fromDate");
		String vcFrom=request.getParameter("vcFrom");
		String vcTo=request.getParameter("vcTo");

		String a1[] = reqFromDate.split("/");
		reqFromDate = a1[2] + "-" + a1[1] + "-" + a1[0];
		String fromtime = request.getParameter("formTime");
		String pm = "PM";
		if (fromtime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			fromtime = fromtime.replaceAll("PM", "");
			String a[] = fromtime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqFromDate = reqFromDate + " " + hr + ":" + a[1];
			} else {
				fromtime = fromtime.replaceAll("PM", "");
				reqFromDate = reqFromDate + " " + fromtime;
			}
		} else {
			fromtime = fromtime.replaceAll("AM", "");
			reqFromDate = reqFromDate + " " + fromtime;
		}

		String reqToDate = request.getParameter("fromDate");
		String b1[] = reqToDate.split("/");
		reqToDate = b1[2] + "-" + b1[1] + "-" + b1[0];
		String totime = request.getParameter("toTime");
		if (totime.toLowerCase().indexOf(pm.toLowerCase()) != -1) {
			totime = totime.replaceAll("PM", "");
			String a[] = totime.split(":");
			if (Integer.parseInt(a[0]) != 12) {
				int hr = Integer.parseInt(a[0]) + 12;
				reqToDate = reqToDate + " " + hr + ":" + a[1];
			} else {
				totime = totime.replaceAll("PM", "");
				reqToDate = reqToDate + " " + totime;
			}
		} else {
			totime = totime.replaceAll("AM", "");
			reqToDate = reqToDate + " " + totime;
		}
		String fromDt = EMicroUtils.dateConvert(request
				.getParameter("fromDate"));
		String toDt = EMicroUtils.dateConvert(request.getParameter("toDate"));
		
		
		///3 days vcanot book
		int days=0;
		String abcr="select datediff(day,'"+reqToDate+"',getdate()) as days";
		ResultSet bb1=ad.selectQuery(abcr);
		try {
			if(bb1.next())
			{
				days=bb1.getInt("days");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		if(days>=3)
		{
			conForm.setMessage2(" VC Room cannot be  booked after 3 days");
			return mapping.findForward("bookRoom");
			
		}
 
		
		
		//chekc 15 minu minimum booked durtatrion
		
		int minutes=0;
		String abc="select datediff(minute,'"+reqFromDate+"','"+reqToDate+"')  as minutes";
		ResultSet bb=ad.selectQuery(abc);
		try {
			if(bb.next())
			{
				minutes=bb.getInt("minutes");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		if(!(minutes>=15))
		{
			conForm.setMessage2(" VC Room Should be  Reserved for minimum of 15 Minutes");
			return mapping.findForward("bookRoom");
			
		}
		
		
		
		
		
		int reqNo = 0;

		int count = 0;
		String checkAvilbilty = "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
				+ "conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,emp_official_info emp,Location loc "
				+ "where (( Req_From_Date between '"
				+ reqFromDate
				+ "'  and '"
				+ reqToDate
				+ "') or ( Req_To_Date  between '"
				+ reqFromDate
				+ "'  and '"
				+ reqToDate
				+ "')or  ( '"+reqFromDate+"' between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   )) "
				+ " and (conf.Loc_Id='"+locId+"' or conf.Loc_Id='"+vcFrom+"' or conf.Loc_Id='"+vcTo+"' or conf.vc_from='"+locId+"' or conf.vc_from='"+vcFrom+"' or conf.vc_from='"+vcTo+"' or conf.vc_to='"+locId+"' or conf.vc_to='"+vcFrom+"' or conf.vc_to='"+vcTo+"' ) "
				+ " and emp.PERNR=conf.Requster_Id and "
				+ "conf.Loc_Id=loc.LOCID and approval_status not in ('Rejected','Self Cancelled') "
				+ "union"
				+ " select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
				+ "conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,emp_official_info emp,Location loc where"
				+ " (( Req_From_Date between"
				+ " '"+reqFromDate+"'  and '"+reqToDate+"') or ( Req_To_Date  between '"+reqFromDate+"'  and '"+reqToDate+"')or  ( '"+reqFromDate+"'"
				+ " between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   ))  "
				+ "and (conf.Loc_Id='16' or conf.Loc_Id='16' or conf.Loc_Id='16' or conf.vc_from='16' or conf.vc_from='16' or conf.vc_from='16' or conf.vc_to='16' or conf.vc_to='16' or conf.vc_to='16' ) "
				+ " and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID and approval_status not in ('Rejected','Self Cancelled')";
		
		
		
		ResultSet rsChekFromDt = ad.selectQuery(checkAvilbilty);
		try {
			while (rsChekFromDt.next()) {
				count = rsChekFromDt.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (count == 0) {
			synchronized (this) {

				String getMaxOfReqNo = "select max(Reqest_No) from VCRoom_Details";
				ResultSet rsMax = ad.selectQuery(getMaxOfReqNo);
				try {
					while (rsMax.next()) {
						reqNo = rsMax.getInt(1) + 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String approverID = "";
				String par_approverID = "";
				String pendingApprs = "";
				String getApprovers = "select appr.Approver_Id,emp.EMP_FULLNAME,appr.Priority,appr.Parallel_Approver_Id from VCRoom_Approvers appr,emp_official_info emp "
						+ " where appr.LocID='"
						+ locId
						+ "' and appr.Floor='"
						+ floor
						+ "' and appr.Room='"
						+ room
						+ "' "
						+ "and emp.PERNR=appr.Approver_Id order by Priority";
				ResultSet rsAppr = ad.selectQuery(getApprovers);
				try {
					while (rsAppr.next()) {
						if (rsAppr.getInt("Priority") == 1)
						{
							approverID = rsAppr.getString("Approver_Id");
							par_approverID=rsAppr.getString("Parallel_Approver_Id");
						}
						pendingApprs = pendingApprs
								+ rsAppr.getString("EMP_FULLNAME") + " , ";
					}
					if (!(pendingApprs.equalsIgnoreCase(""))) {
						int size = pendingApprs.length();
						pendingApprs = pendingApprs.substring(0, (size - 2));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				String data = conForm.getEmployeeno();
				if (data.equalsIgnoreCase("")) {
					data = user.getEmployeeNo();
				}

				String saveData = "insert into VCRoom_Details(Reqest_No,Requster_Id,Loc_Id,Floor,Conf_Room,From_Date,From_Time,To_Date,To_Time,vc_from,vc_to,Purpose,Req_From_Date,"
						+ "Req_To_Date,Email_ID,Ext_No,IP_Phone,IPAddress,Submit_Date,Last_approver,Pending_approver,approved_date,approval_status,foruse) "
						+ "values('"
						+ reqNo
						+ "','"
						+ user.getEmployeeNo()
						+ "','"
						+ locId
						+ "','"
						+ floor
						+ "','"
						+ room
						+ "','"
						+ fromDt
						+ "','"
						+ conForm.getFromTime()
						+ "',"
						+ "'"
						+ toDt
						+ "','"
						+ conForm.getToTime()
						+ "','"
						+ vcFrom
						+ "','"
						+ vcTo
						+ "','"
						+ conForm.getPurpose()
						+ "','"
						+ reqFromDate
						+ "','"
						+ reqToDate
						+ "','"
						+ user.getMail_id()
						+ "',"
						+ "'"
						+ conForm.getExtno()
						+ "','"
						+ conForm.getIpPhoneno()
						+ "','"
						+ conForm.getIPNumber()
						+ "','"
						+ dateNow
						+ "','','"
						+ pendingApprs + "','','Pending','" + data + "')";
				int i = ad.SqlExecuteUpdate(saveData);
				if (i > 0) {
					// send Request to Approvers
					ft = new SimpleDateFormat("dd/MM/yyyy");
					dateNow = ft.format(dNow);
					String saveAllReq="";
					if(!par_approverID.equalsIgnoreCase(""))
					{
					 saveAllReq = "insert into All_Request(Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,"
							+ "Approved_Persons,Requester_Id,Comments,type) values ('"
							+ reqNo
							+ "','VC Booking','"
							+ user.getEmployeeNo()
							+ "','"
							+ dateNow
							+ "','Pending','',"
							+ "'"
							+ approverID
							+ "','','"
							+ user.getEmployeeNo()
							+ "','','VC Booking'),('"
							+ reqNo
							+ "','VC Booking','"
							+ user.getEmployeeNo()
							+ "','"
							+ dateNow
							+ "','Pending','',"
							+ "'"
							+ par_approverID
							+ "','','"
							+ user.getEmployeeNo()
							+ "','','VC Booking')";
					}
					else
					{
						 saveAllReq = "insert into All_Request(Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,"
									+ "Approved_Persons,Requester_Id,Comments,type) values ('"
									+ reqNo
									+ "','VC Booking','"
									+ user.getEmployeeNo()
									+ "','"
									+ dateNow
									+ "','Pending','',"
									+ "'"
									+ approverID
									+ "','','"
									+ user.getEmployeeNo()
									+ "','','VC Booking')";
					}
					
					int j = ad.SqlExecuteUpdate(saveAllReq);
					conForm.setMessage("Request has been sent to approver.");
					VCMail mail = new VCMail();
					mail.sendMailToApprover(request, reqNo,	user.getEmployeeNo(), approverID, "Pending");
					
					if(!par_approverID.equalsIgnoreCase(""))
					mail.sendMailToApprover(request, reqNo,	user.getEmployeeNo(), par_approverID, "Pending");
				}
			}
		} else {
			conForm.setMessage2("Selected Date VC Room Already Reserved.");
			String getBookedList = "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
				+ "conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,emp_official_info emp,Location loc "
				+ "where (( Req_From_Date between '"
				+ reqFromDate
				+ "'  and '"
				+ reqToDate
				+ "') or ( Req_To_Date  between '"
				+ reqFromDate
				+ "'  and '"
				+ reqToDate
				+ "')or  ( '"+reqFromDate+"' between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   )) "
				+ " and (conf.Loc_Id='"+locId+"' or conf.Loc_Id='"+vcFrom+"' or conf.Loc_Id='"+vcTo+"' or conf.vc_from='"+locId+"' or conf.vc_from='"+vcFrom+"' or conf.vc_from='"+vcTo+"' or conf.vc_to='"+locId+"' or conf.vc_to='"+vcFrom+"' or conf.vc_to='"+vcTo+"' ) "
				+ " and emp.PERNR=conf.Requster_Id and "
				+ "conf.Loc_Id=loc.LOCID and approval_status not in ('Rejected','Self Cancelled') "
				+ "union"
				+ " select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,conf.Conf_Room,conf.From_Date,conf.From_Time,"
				+ "conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,emp_official_info emp,Location loc where"
				+ " (( Req_From_Date between"
				+ " '"+reqFromDate+"'  and '"+reqToDate+"') or ( Req_To_Date  between '"+reqFromDate+"'  and '"+reqToDate+"')or  ( '"+reqFromDate+"'"
				+ " between Req_From_Date  and Req_To_Date) or ( '"+reqToDate+"'  between Req_From_Date  and Req_To_Date   ))  "
				+ "and (conf.Loc_Id='16' or conf.Loc_Id='16' or conf.Loc_Id='16' or conf.vc_from='16' or conf.vc_from='16' or conf.vc_from='16' or conf.vc_to='16' or conf.vc_to='16' or conf.vc_to='16' ) "
				+ " and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID and approval_status not in ('Rejected','Self Cancelled')";
			List resarvedList = new LinkedList();
			/*
			 * String checkAvilbilty=
			 * "select conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
			 * +
			 * "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status from VCRoom_Details conf,"
			 * +
			 * "emp_official_info emp,Location loc where conf.From_Date>='"+dateNow
			 * +"' and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID";
			 * ResultSet rs=ad.selectQuery(checkAvilbilty);
			 */
			ResultSet rs = ad.selectQuery(getBookedList);
			try {
				while (rs.next()) {
					VCForm c = new VCForm();
					c.setReqNo(rs.getString("Reqest_No"));
					c.setLocation(rs.getString("LOCATION_CODE"));
					c.setEmpName(rs.getString("EMP_FULLNAME"));
					c.setFloor(rs.getString("Floor"));
					c.setRoomName(rs.getString("Conf_Room"));
					c.setFromDate(rs.getString("From_Date") + " "
							+ rs.getString("From_Time"));
					c.setToDate(rs.getString("To_Date") + " "
							+ rs.getString("To_Time"));
					c.setApprovalStatus(rs.getString("approval_status"));
					resarvedList.add(c);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resarvedList.size() > 0) {
				request.setAttribute("resarvedList", resarvedList);
			}
		}
		conForm.setLocationId("16,Corporate VC Room,Third Floor");
		conForm.setFromDate("");
		conForm.setFromTime("");
		conForm.setToDate("");
		conForm.setToTime("");
	
		conForm.setPurpose("");

		return mapping.findForward("bookRoom");
	}

	public ActionForward bookRoom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		userDetails(mapping, form, request, response);
		ResultSet rs11 = ad.selectQuery("select  * from VCRoom_List,Location where Location.locid=VCRoom_List.locid");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID")+","+rs11.getString("room_name")+ ","	+rs11.getString("Floor"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "	+ rs11.getString("LOCNAME")+ "|"	+rs11.getString("room_name")+ "|"	+rs11.getString("Floor"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		conForm.setLocationId("16,Corporate VC Room,Third Floor");
		conForm.setFloor("");
		conForm.setRoomName("");
		return mapping.findForward("bookRoom");
	}

	public ActionForward userDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm help = (VCForm) form;
		EssDao ad = new EssDao();
		String ipaddress = "";
		String username = "";
		ipaddress = request.getHeader("X-FORWARDED-FOR"); // proxy
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		if (ipaddress == null) {
			ipaddress = request.getRemoteAddr();
		}
		try {
			String data = "select emp.PERNR,emp.EMP_FULLNAME,emp.EMAIL_ID,emp.IP_PHONE,emp.TEL_EXTENS,dep.DPTSTXT,des.DSGSTXT,loc.LOCATION_CODE,loc.LOCID "
					+ "from emp_official_info as emp,DEPARTMENT as dep,DESIGNATION as des ,Location as loc where PERNR='"
					+ user.getEmployeeNo()
					+ "' and "
					+ "dep.DPTID=emp.DPTID and des.DSGID=emp.DSGID and loc.LOCATION_CODE=emp.LOCID";
			ResultSet rs = ad.selectQuery(data);

			while (rs.next()) {
				help.setRequestername(rs.getString("EMP_FULLNAME"));
				help.setRequesterdepartment(rs.getString("DPTSTXT"));
				help.setRequesterdesignation(rs.getString("DSGSTXT"));
				help.setExtno(rs.getString("TEL_EXTENS"));
				help.setIpPhoneno(rs.getString("IP_PHONE"));
				help.setLocation(rs.getString("LOCATION_CODE"));
				help.setLocNo(rs.getString("LOCID"));
				help.setHostname(username);
				help.setIPNumber(ipaddress);
				help.setEmpno(rs.getString("PERNR"));
				help.setEmpEmailID(rs.getString("EMAIL_ID"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapping.findForward("newincidentform");
	}
	
	public ActionForward displayVCReportList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();

		ResultSet rs11 = ad.selectQuery("select  * from VCRoom_List,Location where Location.locid=VCRoom_List.locid");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "	+ rs11.getString("LOCNAME")+ " | "	+rs11.getString("room_name")+ " | "	+rs11.getString("Floor"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conForm.setLocationIdList(locationList);
		conForm.setLocationLabelList(locationLabelList);
		
	ArrayList yearList=new ArrayList();
		
		ResultSet rs17 = ad.selectQuery("select  distinct year(From_Date) as year from VCRoom_Details order by 1 desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("year"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				conForm.setYearList(yearList);
		

		return mapping.findForward("VCReport");
	}
	
	
	public ActionForward generateVCReportList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();

		ResultSet rs11 = ad.selectQuery("select  * from VCRoom_List,Location where Location.locid=VCRoom_List.locid");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "	+ rs11.getString("LOCNAME")+ " | "	+rs11.getString("room_name")+ " | "	+rs11.getString("Floor"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conForm.setLocationIdList(locationList);
		conForm.setLocationLabelList(locationLabelList);
		
	ArrayList yearList=new ArrayList();
		
		ResultSet rs17 = ad.selectQuery("select  distinct year(From_Date) as year from VCRoom_Details order by 1 desc"); 
				try {
					while(rs17.next()) {
						yearList.add(rs17.getString("year"));
					}
					rs17.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				conForm.setYearList(yearList);
				
				
				
				
				///
				ArrayList report=new ArrayList();
				String aa="";
				
				if(conForm.getReqstatus().equalsIgnoreCase("All"))
				 aa="select datediff(minute,convert(nvarchar,(from_date))+' '+convert(nvarchar,(from_time)),convert(nvarchar,(to_date))+' '+convert(nvarchar,(to_time)))/60 as hrs,datediff(minute,convert(nvarchar,(from_date))+' '+convert(nvarchar,(from_time)),convert(nvarchar,(to_date))+' '+convert(nvarchar,(to_time)))%60 as min,datename(month,from_date) as dismonth,* from VCRoom_Details,emp_official_info,DEPARTMENT where loc_id='"+conForm.getLocationId()+"' and month(From_Date)='"+conForm.getMonth()+"' and year(From_Date)="+conForm.getYear()+" and emp_official_info.PERNR=VCRoom_Details.Requster_Id and emp_official_info.DPTID=DEPARTMENT.DPTID  order by reqest_no desc ";
				else
			 aa="select datediff(minute,convert(nvarchar,(from_date))+' '+convert(nvarchar,(from_time)),convert(nvarchar,(to_date))+' '+convert(nvarchar,(to_time)))/60 as hrs,datediff(minute,convert(nvarchar,(from_date))+' '+convert(nvarchar,(from_time)),convert(nvarchar,(to_date))+' '+convert(nvarchar,(to_time)))%60 as min,datename(month,from_date) as dismonth,* from VCRoom_Details,emp_official_info,DEPARTMENT where loc_id='"+conForm.getLocationId()+"' and month(From_Date)='"+conForm.getMonth()+"' and year(From_Date)="+conForm.getYear()+" and approval_status='"+conForm.getReqstatus()+"' and emp_official_info.PERNR=VCRoom_Details.Requster_Id and emp_official_info.DPTID=DEPARTMENT.DPTID  order by reqest_no desc";
				
				ResultSet ab=ad.selectQuery(aa);
				try {
					while(ab.next())
					{
						VCForm vc = new VCForm();
						vc.setLocation(EmpLoc(ab.getString("Loc_id"))+" " +ab.getString("conf_room")+" "+ab.getString("floor"));
						vc.setReqNo(ab.getString("reqest_no"));
						vc.setRequestername(ab.getString("Emp_fullname"));
						vc.setDept(ab.getString("dptstxt"));
						vc.setFromDate(ab.getString("From_date"));
						vc.setToDate(ab.getString("To_date"));
						vc.setFromTime(ab.getString("From_time"));
						vc.setToTime(ab.getString("To_time"));
						String hr="";
						String min="";
						
						if(ab.getString("hrs").length()==1)
					    hr="0"+ab.getString("hrs");
						else
						hr=ab.getString("hrs");
						
						if(ab.getString("min").length()==1)
							min="0"+ab.getString("min");
							else
								min=ab.getString("min");
						
						vc.setDuration(hr+":"+min);
						vc.setReqstatus(ab.getString("approval_status"));

						conForm.setDisplaymonth(ab.getString("dismonth"));
						report.add(vc);
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(report.size()>0)
					request.setAttribute("report", report);
				else	
					request.setAttribute("noreport", "noreport");
					
				
		

		return mapping.findForward("VCReport");
	}
	
	public String Empname(String a)
	{
		EssDao ad = new EssDao();
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
		
		String emp = "select EMP_FULLNAME from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("emp_fullname"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}
	
	public String EmpLoc(String a)
	{
	
		EssDao ad = new EssDao();
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			
		String emp = "select LOCID from emp_official_info where pernr = '"+a+"'";
		ResultSet rs = ad.selectQuery(emp);
		try {
			if(rs.next())
			{
				b=rs.getString("LOCID"); 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		return b;
		
	}

	public ActionForward displayList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();

		ResultSet rs11 = ad.selectQuery("select  * from VCRoom_List,Location where Location.locid=VCRoom_List.locid");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "	+ rs11.getString("LOCNAME")+ " | "	+rs11.getString("room_name")+ " | "	+rs11.getString("Floor"));
			}

			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
			String dateNow = ft.format(dNow);
			List resarvedList = new LinkedList();
			String checkAvilbilty = "select dep.DPTSTXT,conf.Reqest_No,conf.Submit_Date,emp.EMP_FULLNAME,loc.LOCATION_CODE,conf.Floor,"
					+ "conf.Conf_Room,conf.From_Date,conf.From_Time,conf.To_Date,conf.To_Time,conf.approval_status,(select top 1 LOCATION_CODE+'-'+LOCNAME+'-'+room_name+'-'+Floor from VCRoom_List, Location where Location.locid=VCRoom_List.locid  and  VCRoom_List.locid = vc_from) as vcf , (select top 1 LOCATION_CODE+'-'+LOCNAME+'-'+room_name+'-'+Floor from VCRoom_List,Location where Location.locid=VCRoom_List.locid and  VCRoom_List.locid = vc_to  )vct from VCRoom_Details conf,"
					+ "emp_official_info emp,Location loc,department dep where conf.From_Date>='"
					+ dateNow
					+ "' and emp.PERNR=conf.Requster_Id and conf.Loc_Id=loc.LOCID and emp.dptid=dep.dptid order by conf.Reqest_No desc ";
			ResultSet rs = ad.selectQuery(checkAvilbilty);
			try {
				while (rs.next()) {
					VCForm c = new VCForm();
					c.setReqNo(rs.getString("Reqest_No"));
					c.setLocation(rs.getString("LOCATION_CODE"));
					c.setEmpName(rs.getString("EMP_FULLNAME"));
					c.setFloor(rs.getString("Floor"));
					c.setRoomName(rs.getString("Conf_Room"));
					c.setFromDate(rs.getString("From_Date") + " "
							+ rs.getString("From_Time"));
					c.setToDate(rs.getString("To_Date") + " "
							+ rs.getString("To_Time"));
					c.setApprovalStatus(rs.getString("approval_status"));
					c.setDept(rs.getString("DPTSTXT"));
					c.setVcFrom(rs.getString("vcf"));
					c.setVcTo(rs.getString("vct"));
					resarvedList.add(c);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (resarvedList.size() > 0) {
				request.setAttribute("resarvedList", resarvedList);
			} else {
				request.setAttribute("NoRecords", "NoRecords");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		conForm.setLocationId("");
		conForm.setFloor("");
		conForm.setRoomName("");

		return mapping.findForward("confBookedList");
	}

	public ActionForward searchRoomsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		String locID = conForm.getLocationId();
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			String getConfList = "select loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Room_Name  from VCRoom_List conf,Location loc where "
					+ "conf.LocId=loc.LOCID and Status='yes' and conf.LocId='"
					+ locID + "'";
			ResultSet rsConfList = ad.selectQuery(getConfList);
			List confList = new LinkedList();
			while (rsConfList.next()) {
				VCForm rooms = new VCForm();
				rooms.setLocationId(rsConfList.getString("LOCATION_CODE") + "-"
						+ rsConfList.getString("LOCNAME"));
				rooms.setFloor(rsConfList.getString("Floor"));
				rooms.setRoomName(rsConfList.getString("Room_Name"));

				confList.add(rooms);
			}
			request.setAttribute("confList", confList);
			if (confList.size() == 0)
				conForm.setMessage2("No VC Room Available");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward("roomsList");
	}

	public ActionForward roomsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCForm conForm = (VCForm) form;
		EssDao ad = new EssDao();
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
			conForm.setLocationIdList(locationList);
			conForm.setLocationLabelList(locationLabelList);
			conForm.setLocationId(user.getPlantId());
			String getConfList = "select loc.LOCATION_CODE,loc.LOCNAME,conf.Floor,conf.Room_Name  from VCRoom_List conf,Location loc where "
					+ "conf.LocId=loc.LOCID and Status='yes'";
			ResultSet rsConfList = ad.selectQuery(getConfList);
			List confList = new LinkedList();
			while (rsConfList.next()) {
				VCForm rooms = new VCForm();
				rooms.setLocationId(rsConfList.getString("LOCATION_CODE") + "-"
						+ rsConfList.getString("LOCNAME"));
				rooms.setFloor(rsConfList.getString("Floor"));
				rooms.setRoomName(rsConfList.getString("Room_Name"));

				confList.add(rooms);
			}
			request.setAttribute("confList", confList);
			if (confList.size() == 0)
				conForm.setMessage2("No Records Found");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapping.findForward("roomsList");
	}

}
