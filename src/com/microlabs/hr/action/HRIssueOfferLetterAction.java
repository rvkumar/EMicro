package com.microlabs.hr.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HRIssueOfferLetterForm;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.UserInfo;


public class HRIssueOfferLetterAction extends DispatchAction{
	
	
	public ActionForward displayCurrentOpenings(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			
			HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;
			
			
			HRDao ad=new HRDao();
			HttpSession session=request.getSession();
			
			LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
				
			String sql="select * from Recruitmen_Request ";
			
			HRIssueOfferLetterForm hRIssueOfferLetterForm1=null;
			
			ResultSet rs=ad.selectQuery(sql);
			
			try{
				ArrayList a1=new ArrayList();
				
				while (rs.next()) {
					hRIssueOfferLetterForm1=new HRIssueOfferLetterForm();
					
					//hRIssueOfferLetterForm1.setDesignation(rs.getString(""));
					hRIssueOfferLetterForm1.setJobTitle(rs.getString("job_title"));
					hRIssueOfferLetterForm1.setPrimaryLocation(rs.getString("primarylocation"));
					hRIssueOfferLetterForm1.setDepartment(rs.getString("department"));
					hRIssueOfferLetterForm1.setRecruitmentId(rs.getString("id"));
					a1.add(hRIssueOfferLetterForm1);
				}
				
				request.setAttribute("RecruitmentDetails", a1);
				
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			session.setAttribute("SUBLINKS", hm);
			
			
			return mapping.findForward("displayCurrentOpenings");
	}

	
	
	
	
	public ActionForward submitOfferLetterDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		try {
			String checkUsernameQuery="select count(*) from users where username='"+hrForm.getUserloginname()+"' and password='"+hrForm.getPassword()+"'";
			int count = 0;
			ResultSet rs1 = ad.selectQuery(checkUsernameQuery);
			while (rs1.next()) {
				count = rs1.getInt(1);
			}
			System.out.println("count=" + count);
			if (count == 0) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//get current date time with Date()
				Date date = new Date();
				System.out.println(dateFormat.format(date));

				//get current date time with Calendar()
				Calendar cal = Calendar.getInstance();
				System.out.println("Current Date="+dateFormat.format(cal.getTime()));
				String currentDate=dateFormat.format(cal.getTime());
				UserInfo user=(UserInfo)session.getAttribute("user");
				String fileList="";
				ResultSet rs4 = ad.selectQuery("SELECT * FROM temp_hr_IssueOfferLetterdoc where user_id='"+user.getId()+"'");
				
				
				while (rs4.next()) {
				fileList+="/jsp/hr/Issue Offer Letter/documents/" + rs4.getString("file_name")+",";
				}
				
				System.out.println("filelist path="+fileList);
				
		String saveOfferLetterDetails="insert into IssueOfferLetter_Details(Recruitmen_ID,JobTitle,Department,PrimaryLocation,Userloginname," +
				"Password,FirstName,MiddleName,LastName,Address1,Address2,Address3,City,State,Plant,Country,EmployeeType,Pincode,Gender,DateOfBirth," +
				"MaritalStatus,CellNo,TelephoneNo,EmailID,SalaryOffered,Uploaded_Documents)" +
		      " values('"+hrForm.getRecuritmentID()+"','"+hrForm.getJobTitle()+"','"+hrForm.getDepartment()+"','"+hrForm.getPrimaryLocation()+"','"+hrForm.getUserloginname()+"'," +
				"'"+hrForm.getPassword()+"','"+hrForm.getFirstName()+"','"+hrForm.getMiddleName()+"','"+hrForm.getLastName()+"','"+hrForm.getAddress1()+"','"+hrForm.getAddress2()+"','"+hrForm.getAddress3()+"','"+hrForm.getCity()+"','"+hrForm.getState()+"'," +
						"'"+hrForm.getPlant()+"','"+hrForm.getCountry()+"','"+hrForm.getEmpType()+"','"+hrForm.getPincode()+"','"+hrForm.getGender()+"','"+hrForm.getDateOfBirth()+"'," +
				"'"+hrForm.getMaritalStatus()+"','"+hrForm.getCellNo()+"','"+hrForm.getTelephoneNo()+"','"+hrForm.getEmailID()+"','"+hrForm.getSalaryOffered()+"','"+fileList+"')";
				
				
				System.out.println("saveOfferLetterDetails=" + saveOfferLetterDetails);

				int i = 0;
				i = ad.SqlExecuteUpdate(saveOfferLetterDetails);
				System.out.println("i=" + i);
				if(i>0){
					
					
					String insertEmpQuery="insert into users(jobTitle,username,password,firstname,middlename,lastname," +
							"usr_type,mail_id,department_id,country_id,state_id,mobile_no,entering_date,gender," +
							"activated,employeenumber,include_links,exclude_links,incsublinks,count,userstatus,status) values" +
							"('"+hrForm.getJobTitle()+"','"+hrForm.getUserloginname()+"','"+hrForm.getPassword()+"','"+hrForm.getFirstName()+"'," +
							"'"+hrForm.getMiddleName()+"','"+hrForm.getLastName()+"','temp'," +
							"'"+hrForm.getEmailID()+"','"+hrForm.getDepartment()+"','"+hrForm.getCountry()+"','"+hrForm.getState()+"'," +
							"'"+hrForm.getCellNo()+"','"+currentDate+"','"+hrForm.getGender()+"','On','"+hrForm.getUserloginname()+"','4','','18','0','"+hrForm.getEmpType()+"',1)";
					System.out.println("inertEmpQuery="+insertEmpQuery);
					
					int j=0;
					j=ad.SqlExecuteUpdate(insertEmpQuery);
					System.out.println("j="+j);
					
					if(j>0){
						
						String getUserID="select * from users where username='"+hrForm.getUserloginname()+"' and password='"+hrForm.getPassword()+"'";
					    System.out.println("getUserID="+getUserID); 
						ResultSet rs=ad.selectQuery(getUserID);
						int id=0;
						while(rs.next())
						{
							id=Integer.parseInt(rs.getString("id"));
						}
						
						System.out.println("id from users table="+id);
						String insertEmp_PersonalInfo="insert into emp_personal_info(user_id,employee_no,first_name,middle_name,last_name,mobile_no,email_address,user_name)" +
								" values('"+id+"','"+hrForm.getUserloginname()+"','"+hrForm.getFirstName()+"','"+hrForm.getMiddleName()+"','"+hrForm.getLastName()+"','"+hrForm.getCellNo()+"','"+hrForm.getEmailID()+"','"+hrForm.getUserloginname()+"')";
						ad.SqlExecuteUpdate(insertEmp_PersonalInfo);           
						
						session.setAttribute("status","New Employee Inserted Successfully User Name='"+hrForm.getUserloginname()+"' and Password='"+hrForm.getPassword()+"'");
					
						String deleteUploadedFiles="delete from temp_hr_IssueOfferLetterdoc where user_id='"+user.getId()+"'";
						ad.SqlExecuteUpdate(deleteUploadedFiles);
					}
					if(j==0){
						session.setAttribute("status","Values Are Not Inserted.Please Check.... ");
					}
					
					
					
		}
					
				
				
				if (i == 0) {
					session.setAttribute("status",
							"Values Are Not Inserted.Please Check.... ");
				}

			}

			else {
				session.setAttribute("status",
						"Please Change Username and Password");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		displayOfferLetter(mapping, form, request, response);
		return mapping.findForward("displayOfferLetter");
		}
	
	public ActionForward getUserName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		try{
			HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;
			HRDao ad=new HRDao();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//get current date time with Date()
			Date date = new Date();
			System.out.println(dateFormat.format(date));

			//get current date time with Calendar()
			String username="";
			Calendar cal = Calendar.getInstance();
			System.out.println("Current Date="+dateFormat.format(cal.getTime()));
			String currentDate=dateFormat.format(cal.getTime());
			String getTotalEmp="select max(employeenumber) from users ";
			System.out.println("getTotalEmp="+getTotalEmp);
			int max=0;
			ResultSet rs1=ad.selectQuery(getTotalEmp);
			while(rs1.next()){
				max=Integer.parseInt(rs1.getString(1));
			}
			
			System.out.println("max of emp="+max);
			max=max+1;
			hrForm.setEmpno(max);
			username=Integer.toString(max);
			
			String pwd=currentDate;
			System.out.println("pwd="+pwd);
			System.out.println("pwd="+pwd.length());
			pwd=pwd.replaceAll("[-]","");
			System.out.println("pwd="+pwd);
			pwd=max+"@"+pwd;
			System.out.println("auto pwd="+pwd);
			System.out.println("auto username="+username);
			hrForm.setUserloginname(username);
			hrForm.setPassword(pwd);
			
	
		}
		catch (Exception e) {
		e.printStackTrace();
		}
	
	displayOfferLetter(mapping, form, request, response);
	return mapping.findForward("displayOfferLetter");
	}
	
	
	public ActionForward getRecruitmentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;
		HRDao ad = new HRDao();
		String recruitmentID=hrForm.getRecuritmentID();
		try{
		String getDetails="select * from Recruitmen_Request where Recruitment_Id='"+recruitmentID+"'";
		ResultSet rs=ad.selectQuery(getDetails);
		while(rs.next()){
			
			hrForm.setRecruitmentId(rs.getString("id"));
			hrForm.setJobTitle(rs.getString("Job_Title"));
			hrForm.setDepartment(rs.getString("Department"));
			hrForm.setPrimaryLocation(rs.getString("PrimaryLocation"));
			
		}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		displayOfferLetter(mapping, form, request, response);	
		return mapping.findForward("displayOfferLetter");
		
	}
	
	
	public ActionForward displayOfferLetter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;

		String subLinkName=request.getParameter("subLink"); 	
		String module=request.getParameter("module");
		HttpSession session=request.getSession();
		String linkName=request.getParameter("linkName");
		
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		
		NewsandMediaDao ad=new NewsandMediaDao();
		
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
		
		
		try {
			ArrayList idList = new ArrayList();
			String getIDs = "select Recruitment_Id from Man_Power_Matrix order by Recruitment_Id";
			ResultSet rs1 = ad.selectQuery(getIDs);
			while (rs1.next()) {
				idList.add(rs1.getInt(1));
			}
			hrForm.setGroupIds(idList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayOfferLetter");
	}

	
	
	public ActionForward readOpeningDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;
		HRDao ad = new HRDao();
		
		String id=request.getParameter("id");
		
		
		String sql="select * from Recruitmen_Request where id='"+id+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		
		
		try{
			
			while (rs.next()) {
			
			hrForm.setDepartment(rs.getString("Department"));
			hrForm.setShiftType(rs.getString("ShiftType"));
			hrForm.setQualifications(rs.getString("Qualification"));
			hrForm.setExperience(rs.getString("Experience"));
			hrForm.setJobDescription(rs.getString("JobDescription"));
			hrForm.setIndustryType(rs.getString("IndustryType"));
			hrForm.setSalaryOffered(rs.getString("SaleryOffered"));
			hrForm.setJobTitle(rs.getString("Job_Title"));
			
			hrForm.setPrimaryLocation(rs.getString("PrimaryLocation"));
			
			}
			
			request.setAttribute("RecruitmentReadOnly", "RecruitmentReadOnly");
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		
		return mapping.findForward("displayCurrentOpenings");
	}
		
		
	
	public ActionForward uploadDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;

		HRDao ad = new HRDao();
		FormFile documentFile=hrForm.getDocumentFile();
		String documentName=documentFile.getFileName();
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		 byte[] size=documentFile.getFileData();
		 if(!documentName.equalsIgnoreCase("")){
		 int length=documentName.length();
	     int dot=documentName.lastIndexOf(".");
	     String extension=documentName.substring(dot,length);
 	     String filepath = getServlet().getServletContext().getRealPath("/jsp/hr/Issue Offer Letter/documents/"+documentFile.getFileName());
		 File imageFile=new File(filepath);
		 FileOutputStream outputStream=new FileOutputStream(imageFile);
		 outputStream.write(size);
		 outputStream.flush();
		 outputStream.close();
		 request.setAttribute("submitDetails", "submitDetails");
		 }
		 }catch(FileNotFoundException fe){
			fe.printStackTrace();
		}catch(IOException ie){
			ie.printStackTrace();
		}
		try{
			String sql9="select count(*) from temp_hr_IssueOfferLetterdoc  where user_id='"+user.getId()+"' and file_name='"+documentFile.getFileName()+"'";
			ResultSet rs15 = ad.selectQuery(sql9);
			int fileCount=0;
			while (rs15.next())
			{
				fileCount=Integer.parseInt(rs15.getString(1));
			}
			if(fileCount>0)
			{
				session.setAttribute("status", "Document aleardy uploaded..please choose another file");
			}
			else
			{
				String sql="insert into temp_hr_IssueOfferLetterdoc(user_id,file_name)" +
				"values('"+user.getId()+"','"+documentFile.getFileName()+"')";
				int a=ad.SqlExecuteUpdate(sql);
				if(a>0)
					{
					session.setAttribute("status", "Documents Uploaded Successfully");
			
					}
			}
		String sql1="select * from temp_hr_IssueOfferLetterdoc where user_id='"+user.getId()+"'";
		ResultSet rs=ad.selectQuery(sql1);
			
			ArrayList list = new ArrayList();
			String sql3="select file_name from temp_hr_IssueOfferLetterdoc where user_Id='"+user.getId()+"'";
			ResultSet rs5 = ad.selectQuery(sql3);
			while (rs5.next())
			{
				HRIssueOfferLetterForm hrForm1  = new HRIssueOfferLetterForm();
				hrForm1.setFileList(rs5.getString("file_name"));
			list.add(hrForm1);
			}
			request.setAttribute("listName", list);

		}catch(Exception e){
			e.printStackTrace();
		}
		displayOfferLetter(mapping, form, request, response);
		return mapping.findForward("displayOfferLetter");
	}
	
	public ActionForward deleteDocuments(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRIssueOfferLetterForm hrForm=(HRIssueOfferLetterForm)form;
		String[] documentCheck=hrForm.getSelect();

		HttpSession session=request.getSession();
		HRDao ad = new HRDao();
		UserInfo user=(UserInfo)session.getAttribute("user");
		 int documentLength=0;
		 try 
		 {
			 documentLength=documentCheck.length;
		 }catch(Exception e){
				//e.printStackTrace();
		 }
			int document=0;
			String documentId="";
			String documentName="";
			String sql="";
			System.out.println("rejectLength Is ********************"+documentLength);
			String[] documentId1=null;
			try{
				if(documentLength>0)
				{
					for(int i=0;i<documentLength;i++)
					{
						document++;
						documentId=documentCheck[i];
	        				sql="delete from temp_hr_IssueOfferLetterdoc " +
        			 		" where file_name='"+documentId+"'";
	        				System.out.println("Getting a sql is *************"+sql);
	        				int c=ad.SqlExecuteUpdate(sql);
	        				if(c>0){
	        					session.setAttribute("status", "Document Details Deleted sucessfully");
	        					
	        				}
	        				 request.setAttribute("submitDetails", "submitDetails");
					}
				}
				ArrayList list = new ArrayList();
				String sql3="select file_name from temp_hr_IssueOfferLetterdoc where user_Id='"+user.getId()+"'";
				ResultSet rs5 = ad.selectQuery(sql3);
				while (rs5.next())
				{
					HRIssueOfferLetterForm hrForm1  = new HRIssueOfferLetterForm();
					hrForm1.setFileList(rs5.getString("file_name"));
				list.add(hrForm1);
				}
				request.setAttribute("listName", list);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			displayOfferLetter(mapping, form, request, response);
		return mapping.findForward("displayOfferLetter");
	}
	

}
