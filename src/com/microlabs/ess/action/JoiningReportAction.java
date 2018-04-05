package com.microlabs.ess.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Properties;

import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.JoiningReportForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;


public class JoiningReportAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
	

	
 	private String[] empDet( String empno) {

		
		String emp=" select emp.eMP_FULLNAME,emp.lOCID,dep.DPTSTXT,desg.DSGSTXT,convert(nvarchar(10),emp.doj,103) as doj,datediff(year,dob,getdate()) as age from Emp_official_info emp "
				+ " , Department dep ,Designation desg  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"' "
						+ " and desg.DSGID= emp.dSGID  ";
		ResultSet ae=ad.selectQuery(emp);

		String[] a = new String[10];
	
		try {
			while(ae.next())
			{
			
			

			    a[0]=ae.getString("eMP_FULLNAME");
			    a[1]=ae.getString("lOCID");
			    a[2]=ae.getString("DPTSTXT");
			    a[3]=ae.getString("DSGSTXT");
			    a[4]=ae.getString("doj");
			    a[5]=ae.getString("age");
      


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
		}
	

	public void copyFile(File sourceFile, File destinationFile) {
        try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                FileOutputStream fileOutputStream = new FileOutputStream(
                                destinationFile);

                int bufferSize;
                byte[] bufffer = new byte[512];
                while ((bufferSize = fileInputStream.read(bufffer)) > 0) {
                        fileOutputStream.write(bufffer, 0, bufferSize);
                }
                fileInputStream.close();
                
                
                fileOutputStream.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
}

	
	
	public ActionForward upLoadEducationAttachments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) 
	{ 
        int fileExist=0;
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		
		
		try {
			ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+joiningForm.getEdcountry()+"' order by BEZEI ");
			ArrayList stateList=new ArrayList();
			ArrayList stateLabelList=new ArrayList();
			
			while(rs4.next()) {
				stateList.add(rs4.getString("BLAND"));
				stateLabelList.add(rs4.getString("BEZEI"));
			}
			joiningForm.setStateList(stateList);
			joiningForm.setStateLabelList(stateLabelList);
			
			
           FormFile myProduct = joiningForm.getEmpEdu();
           String contentType =myProduct.getContentType();
           String fileName   =  myProduct.getFileName();
           int filesize=myProduct.getFileSize();
           String qualification=request.getParameter("Qual");
		String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
		
		if(ext.equalsIgnoreCase("doc")||ext.equalsIgnoreCase("docx")||ext.equalsIgnoreCase("pdf")||ext.equalsIgnoreCase("jpg")||ext.equalsIgnoreCase("xlsx")||ext.equalsIgnoreCase("xls") && (filesize<1048576))
		{
			String checkFile="select count(*) from join_emp_education_documents where file_name='"+fileName+"' and education='"+qualification+"'";
			ResultSet rsCheckFile=ad.selectQuery(checkFile);
		try{
			while(rsCheckFile.next())
			{
				fileExist=rsCheckFile.getInt(1);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}
      if(fileExist==0)
      {
             System.out.println(fileName);
			 byte[] fileData  =   myProduct.getFileData();
			 String filePath = getServlet().getServletContext().getRealPath("ESS/JoinEducationDocuments");
			 
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
		 	 filePath=uploadFilePath+"/EMicro Files/ESS/JoinEducationDocuments";
			 System.out.println(filePath);
			 File destinationDir = new File(filePath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			File fileToCreate = new File(filePath, fileName);
			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
			fileOutStream.write(myProduct.getFileData());
			fileOutStream.flush();
			fileOutStream.close();
			
			
			
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
		
			File oldfile =new File(uploadFilePath+"/EMicro Files/ESS/JoinEducationDocuments/"+fileName);
			
		
			
			//upload files in another path
			
			try{
				String filePath1 = "E:/EMicro Files/ESS/JoinEducationDocuments";
				
				byte[] fileData1 = myProduct.getFileData();
				
				
				File destinationDir1 = new File(filePath1);
				if(!destinationDir1.exists())
				{
					destinationDir1.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate1 = new File(filePath1, fileName);
					if (!fileToCreate1.exists()) {
						FileOutputStream fileOutStream1 = new FileOutputStream(fileToCreate1);
						fileOutStream1.write(fileData1);
						fileOutStream1.flush();
						fileOutStream1.close();
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		    qualification=request.getParameter("Qual");
			String checkEmpExist="select count(*) from join_emp_education_documents where sl_no='"+joiningForm.getId()+"' and education='"+qualification+"'";
			int empNoAvailable=0;
			ResultSet rsCheckEmpExist=ad.selectQuery(checkEmpExist);
			while(rsCheckEmpExist.next())
			{
				empNoAvailable=rsCheckEmpExist.getInt(1);
			}
			String sql="";
        
					
			if(empNoAvailable==0){
			sql="insert into join_emp_education_documents(emp_no,education,file_name,sl_no) values('"+userId.getEmployeeNo()+"','"+qualification+"','"+fileName+"','"+joiningForm.getId()+"')";
			
			}else{
			 sql="update join_emp_education_documents set file_name='"+fileName+"' where sl_no='"+joiningForm.getId()+"' and education='"+qualification+"'";	
			}
			int a = ad.SqlExecuteUpdate(sql);
		
			if (a > 0) {
				joiningForm.setMessage1("");
				joiningForm.setMessage("Documents Uploaded Successfully");
			}else{
				joiningForm.setMessage1("");
				joiningForm.setMessage("Error  ... Please check ");
			}
			ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL order by Education_Level ");
			ArrayList eduIDList=new ArrayList();
			ArrayList eduValueList=new ArrayList();
			
			while(rsEdu.next()) {
				eduIDList.add(rsEdu.getString("Id"));
				eduValueList.add(rsEdu.getString("Education_Level"));
			}
			joiningForm.setEduIDList(eduIDList);
			joiningForm.setEduValueList(eduValueList);
			
			ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION order by Qualification");
			ArrayList qulificationID=new ArrayList();
			ArrayList qulificationValueList=new ArrayList();
			
			while(rsQulif.next()) {
				qulificationID.add(rsQulif.getString("Id"));
				qulificationValueList.add(rsQulif.getString("Qualification"));
			}
			joiningForm.setQulificationID(qulificationID);
			joiningForm.setQulificationValueList(qulificationValueList);
			
			ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY order by Ind_Desc");
			ArrayList industyID=new ArrayList();
			ArrayList industyValueList=new ArrayList();
			
			while(rsIndustry.next()) {
				industyID.add(rsIndustry.getString("Id"));
				industyValueList.add(rsIndustry.getString("Ind_Desc"));
			}
			joiningForm.setIndustyID(industyID);
			joiningForm.setIndustyValueList(industyValueList);
			try{
				ArrayList list = new ArrayList();
				String sql3="select *  from join_emp_education_details_approve " +
						" where Sl_No='"+joiningForm.getId()+"' ";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					JoiningReportForm joiningForm1 = new JoiningReportForm();
					joiningForm1.setId(rs11.getString("id"));
					joiningForm1.setEducationalLevel(rs11.getString("education_level"));
					joiningForm1.setQualification(rs11.getString("qualification"));
					joiningForm1.setSpecialization(rs11.getString("specialization"));
					joiningForm1.setUniversityName(rs11.getString("univarsity_name"));
					joiningForm1.setUniverysityLocation(rs11.getString("university_location"));
					joiningForm1.setEdstate(rs11.getString("e_state"));
					joiningForm1.setEdcountry(rs11.getString("e_country"));
					joiningForm1.setDurationofCourse(rs11.getString("duration_of_course"));
					joiningForm1.setTimes(rs11.getString("time"));
					joiningForm1.setYearofpassing(rs11.getString("year_of_passing"));
					joiningForm1.setFullTimePartTime(rs11.getString("fulltime_parttime"));
					joiningForm1.setPercentage(rs11.getString("percentage"));
					 fileName="";
					String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
					ResultSet rs12 = ad.selectQuery(doc);
					while(rs12.next()){
					fileName=rs12.getString("file_name");
					joiningForm1.setEmpEduDoc(rs12.getString("file_name"));
				
					request.setAttribute("edudoc", "edudoc");
					}
					if(fileName.equalsIgnoreCase(""))
					{
						joiningForm1.setEmpEduDoc("");
					}
					    list.add(joiningForm1);
				}
				request.setAttribute("listName", list);
			}catch (Exception e) {
			e.printStackTrace();
			}
			
  	}
      
		}else{
			joiningForm.setMessage("");
	  		joiningForm.setMessage1("Upload only doc,docx,pdf,jpg,xls,xlsx extension files with size less than 1Mb");

		}
		
		
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
         
	    String eduStatus=joiningForm.getEducationStatus();
	    if(eduStatus.equalsIgnoreCase("Save"))
	    {
		request.setAttribute("employeeEducationDoc", "employeeEducationDoc");
		request.setAttribute("addEducation", "addEducation");
	    }else{
	    	request.setAttribute("modifyEducation", "modifyEducation");
	    }
		String parameter="educationDetails";
		request.setAttribute(parameter, parameter);
		
		
		
		return mapping.findForward("display1");	
	}
	
	public ActionForward upLoadPhoto(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
	    	 
			JoiningReportForm joiningForm=(JoiningReportForm)form;
           int fileExist=0;
           FormFile myProduct = joiningForm.getEmpPhoto();
           String contentType =myProduct.getContentType();
           String fileName   =  myProduct.getFileName();
			String checkFile="select count(*) from Employee_Photos where file_name='"+fileName+"'";
			ResultSet rsCheckFile=ad.selectQuery(checkFile);
		try{
			while(rsCheckFile.next())
			{
				fileExist=rsCheckFile.getInt(1);
			}
		}catch (Exception e) {
		e.printStackTrace();
		}
      if(true)
      {
             System.out.println(fileName);
			 byte[] fileData  =   myProduct.getFileData();
			 String filePath = getServlet().getServletContext().getRealPath("images/EmpPhotos");
			 
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
		 	 filePath=uploadFilePath+"/EMicro Files/images/EmpPhotos";
			 System.out.println(filePath);
				String ext = fileName.substring(fileName.lastIndexOf('.') + 1);
				
				if(ext.equalsIgnoreCase("jpg")) 
				{
			 
			 File destinationDir = new File(filePath);
			if(!destinationDir.exists())
			{
				destinationDir.mkdirs();
			}
			File fileToCreate = new File(filePath, fileName);
			FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
			fileOutStream.write(myProduct.getFileData());
			fileOutStream.flush();
			fileOutStream.close();
			
			
			
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
			String empNo=userId.getEmployeeNo();
			String changeFile=empNo+"."+ext;
			empNo=empNo+"."+ext;
			empNo=empNo.trim();
			File oldfile =new File(uploadFilePath+"/EMicro Files/images/EmpPhotos/"+fileName);
			
			
			File newfile =new File(uploadFilePath+"/EMicro Files/images/EmpPhotos/"+empNo); 
			newfile.delete();
			if(oldfile.renameTo(newfile)){
				System.out.println("Rename succesful");
			}else{
				System.out.println("Rename failed");
				changeFile=fileName;
			}
			
			
			//upload files in another path
			
			try{
				String filePath1 = "E:/EMicro Files/images/EmpPhotos";
				
				byte[] fileData1 = myProduct.getFileData();
				
				
				File destinationDir1 = new File(filePath1);
				if(!destinationDir1.exists())
				{
					destinationDir1.mkdirs();
				}
				if (!fileName.equals("")) {
					File fileToCreate1 = new File(filePath1, empNo);
					if (!fileToCreate1.exists()) {
						FileOutputStream fileOutStream1 = new FileOutputStream(fileToCreate1);
						fileOutStream1.write(fileData1);
						fileOutStream1.flush();
						fileOutStream1.close();
					}
				}
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			String checkEmpExist="select count(*) from Employee_Photos where employeeNo='"+userId.getEmployeeNo()+"'";
			int empNoAvailable=0;
			ResultSet rsCheckEmpExist=ad.selectQuery(checkEmpExist);
			while(rsCheckEmpExist.next())
			{
				empNoAvailable=rsCheckEmpExist.getInt(1);
			}
			String sql="";
			String insertIntoUsers="update users set Emp_Photo='"+changeFile+"' where employeenumber='"+userId.getEmployeeNo()+"'";
			if(empNoAvailable==0){
			sql="insert into Employee_Photos(employeeNo,file_name,file_path) values('"+userId.getEmployeeNo()+"','"+changeFile+"','"+filePath+"')";
			
			}else{
			 sql="update Employee_Photos set file_name='"+changeFile+"' where employeeNo='"+userId.getEmployeeNo()+"'";	
			}
			int a = ad.SqlExecuteUpdate(sql);
			ad.SqlExecuteUpdate(insertIntoUsers);
			if (a > 0) {
				joiningForm.setMessage("Photo Uploaded Successfully");
				joiningForm.setMessage1("");
			}else{
				joiningForm.setMessage("Error While Phot ... Please check Entered Values");
			}

			try{
			ArrayList list = new ArrayList();
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//get current date time with Date()
	        // Date date=new Date().getTime();
			//System.out.println(dateFormat.format(date));
			String sql3="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next())
			{
				joiningForm.setPhotoImage(empNo+"?time="+new Date().getTime());
				System.out.println(""+empNo+"?time="+new Date().getTime());
			}
			}catch (Exception e) {
			e.printStackTrace();
			}
				}else{
					joiningForm.setMessage("");
					joiningForm.setMessage1("Please Upload images Only with .jpg extentsion");
				}
			
  	}else
  	{
  		
  		joiningForm.setMessage1("");
  		joiningForm.setMessage("Image already exist.Please rename the file..");
  	}
      
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
         
	
		request.setAttribute("employeePhoto", "employeePhoto");
		String parameter="personalDetails";
		request.setAttribute(parameter, parameter);
		
		return mapping.findForward("display1");	
	}
	
	
	
	public ActionForward editLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String expID=request.getParameter("expID");
		String reqID=request.getParameter("langID");
		joiningForm.setReqLangID(reqID);
		joiningForm.setReqExpID(expID);
		try{
		ResultSet rsLang = ad.selectQuery("select * from LANGUAGE order by Language");
		ArrayList langID=new ArrayList();
		ArrayList langValueList=new ArrayList();
		try{
		while(rsLang.next()) {
			langID.add(rsLang.getString("Id"));
			langValueList.add(rsLang.getString("Language"));
		}
		joiningForm.setLanguageID(langID);
		joiningForm.setLanguageValueList(langValueList);
		}catch (Exception e) {
		e.printStackTrace();
		}
		
	String sql3="select * from join_emp_language_details_approve where  sl_no='"+joiningForm.getId()+"' and id='"+reqID+"'";
	ResultSet rs11 = ad.selectQuery(sql3);
	while (rs11.next()) {
		 joiningForm.setNewid(rs11.getString("id"));
		joiningForm.setLanguage(rs11.getString("language"));
		joiningForm.setMotherTongue(rs11.getString("mother_tongue"));	
	    joiningForm.setLangSpeaking(rs11.getString("spoken"));
	    joiningForm.setLangRead(rs11.getString("reading"));
		joiningForm.setLangWrite(rs11.getString("writing"));
		joiningForm.setLangstartDate((EMicroUtils.display(rs11.getDate("l_start_date"))));
		joiningForm.setLangendDate((EMicroUtils.display(rs11.getDate("l_end_date"))));
		    
	}
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("modifyLanguage", "modifyLanguage");
		String parameter="languageDetails";
		request.setAttribute(parameter, parameter);
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		return mapping.findForward("display1");	
	}
	
	public static /*ArrayList*/ boolean getNS(String hostName) 
    {  
        // Perform a DNS lookup for NS records in the domain  
        Hashtable env = new Hashtable();  

        boolean flag ;
        env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");  
        DirContext ictx;



        try
        {
        	ictx = new InitialDirContext(env);
        Attributes attrs = ictx.getAttributes(hostName, new String[] { "NS" });  
        Attribute attr = attrs.get("NS");

        ArrayList res = new ArrayList();  
        NamingEnumeration en = attr.getAll();  

        while (en.hasMore())  
        {  
            String x = (String) en.next();  
            if (x.endsWith(".")) x = x.substring(0, x.length() - 1);  
            res.add(x);  
        } 



        if(res.size()>0)
        {
        flag=true;	
       	        }
        else
        {
        flag=false;	

        }

        }



        catch (Exception e) {
			// TODO Auto-generated catch block
        	 flag=false;	
			e.printStackTrace();
		}

        return flag;



    }  

	
	public ActionForward editExprience(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String expID=request.getParameter("expID");
		joiningForm.setReqExpID(expID);
		try{
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX"));
		}
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		
		ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY order by Ind_Desc");
		ArrayList industyID=new ArrayList();
		ArrayList industyValueList=new ArrayList();
		
		while(rsIndustry.next()) {
			industyID.add(rsIndustry.getString("Id"));
			industyValueList.add(rsIndustry.getString("Ind_Desc"));
		}
		joiningForm.setIndustyID(industyID);
		joiningForm.setIndustyValueList(industyValueList);
		
		
		String sql3="select * from join_emp_experience_details_approve where sl_no='"+joiningForm.getId()+"' and id='"+expID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {					
			 joiningForm.setNewid(rs11.getString("id"));
			joiningForm.setNameOfEmployer(rs11.getString("name_of_employer"));
			joiningForm.setIndustry(rs11.getString("industry"));
			 joiningForm.setExCity(rs11.getString("ex_city"));
			joiningForm.setExcountry(rs11.getString("ex_country"));
			 joiningForm.setPositionHeld(rs11.getString("position_held"));
			joiningForm.setJobRole(rs11.getString("job_role"));			
			DateFormat dateFormat = new SimpleDateFormat("MM/yyyy");			
			joiningForm.setStartDate(dateFormat.format(rs11.getDate("start_date")));
			joiningForm.setEndDate(dateFormat.format(rs11.getDate("end_date")));
			System.out.println(rs11.getString("MiciroExp"));
			joiningForm.setMicroExp(rs11.getString("MiciroExp"));
			joiningForm.setMicroNo(rs11.getString("MicroNo"));  
		}
	}catch (Exception e) {
		e.printStackTrace();
	}
	request.setAttribute("modifyExperience", "modifyExperience");
	String parameter="experienceDetails";
	request.setAttribute(parameter, parameter);
	
	joiningForm.setMessage("");
	joiningForm.setMessage1("");
	
	return mapping.findForward("display1");	
	
	}
	
	public ActionForward editEducation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String eduID=request.getParameter("eduID");
		joiningForm.setReqEduID(eduID);
		try{
			
			
			ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL order by Education_Level ");
			ArrayList eduIDList=new ArrayList();
			ArrayList eduValueList=new ArrayList();
			
			while(rsEdu.next()) {
				eduIDList.add(rsEdu.getString("Id"));
				eduValueList.add(rsEdu.getString("Education_Level"));
			}
			joiningForm.setEduIDList(eduIDList);
			joiningForm.setEduValueList(eduValueList);
			
			ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION order by Qualification");
			ArrayList qulificationID=new ArrayList();
			ArrayList qulificationValueList=new ArrayList();
			
			while(rsQulif.next()) {
				qulificationID.add(rsQulif.getString("Id"));
				qulificationValueList.add(rsQulif.getString("Qualification"));
			}
			joiningForm.setQulificationID(qulificationID);
			joiningForm.setQulificationValueList(qulificationValueList);
			
			ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY order by Ind_Desc");
			ArrayList industyID=new ArrayList();
			ArrayList industyValueList=new ArrayList();
			
			while(rsIndustry.next()) {
				industyID.add(rsIndustry.getString("Id"));
				industyValueList.add(rsIndustry.getString("Ind_Desc"));
			}
			joiningForm.setIndustyID(industyID);
			joiningForm.setIndustyValueList(industyValueList);
			
			
			
			String sql3="select * from join_emp_education_details_Approve where id='"+eduID+"' and sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				
				 joiningForm.setNewid(rs11.getString("id"));
				joiningForm.setEducationalLevel(rs11.getString("education_level"));
				joiningForm.setQualification(rs11.getString("qualification"));
				joiningForm.setSpecialization(rs11.getString("specialization"));
				joiningForm.setUniversityName(rs11.getString("univarsity_name"));
				joiningForm.setUniverysityLocation(rs11.getString("university_location"));
				joiningForm.setEdstate(rs11.getString("e_state"));
				joiningForm.setEdcountry(rs11.getString("e_country"));
				joiningForm.setDurationofCourse(rs11.getString("duration_of_course"));
				joiningForm.setTimes(rs11.getString("time"));
                joiningForm.setYearofpassing(rs11.getString("year_of_passing"));				
			/*	joiningForm.setFromDate((EMicroUtils.display(rs11.getDate("from_date"))));
				joiningForm.setToDate((EMicroUtils.display(rs11.getDate("to_date"))));*/
				joiningForm.setFullTimePartTime(rs11.getString("fulltime_parttime"));
				joiningForm.setPercentage(rs11.getString("percentage"));
				String fileName="";
				String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
				ResultSet rs12 = ad.selectQuery(doc);
				while(rs12.next()){
					fileName=rs12.getString("file_name");
				joiningForm.setEmpEduDoc(rs12.getString("file_name"));
			
				request.setAttribute("edudoc", "edudoc");
				}
				if(fileName.equalsIgnoreCase(""))
				{
					joiningForm.setEmpEduDoc("");
				}
				String getstate="select BLAND,BEZEI from State where LAND1='"+joiningForm.getEdcountry()+"' order by BEZEI ";
				ResultSet rs4 = ad.selectQuery(getstate);
				ArrayList stateList=new ArrayList();
				ArrayList stateLabelList=new ArrayList();
				
				try {
					while(rs4.next()) {
						stateList.add(rs4.getString("BLAND"));
						stateLabelList.add(rs4.getString("BEZEI"));
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
				joiningForm.setStateList(stateList);
				joiningForm.setStateLabelList(stateLabelList);
				
				
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		joiningForm.setEducationStatus("modify");
		request.setAttribute("modifyEducation", "modifyEducation");
		String parameter="educationDetails";
		request.setAttribute(parameter, parameter);
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		return mapping.findForward("display1");	
	}
	
	
	public ActionForward editFamily(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String reqID=request.getParameter("familyID");
		joiningForm.setReqFamilyID(reqID);
		
		String sql3="select * from join_emp_family_details_approve where sl_no='"+joiningForm.getId()+"' and id='"+reqID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		try{
			while(rs11.next()){
				 joiningForm.setNewid(rs11.getString("id"));
		     joiningForm.setFamilyDependentTypeID(rs11.getString("family_dependent_type_id"));
		     joiningForm.setFtitle(rs11.getString("f_title"));
			 joiningForm.setFfirstName(rs11.getString("f_first_name"));
		     joiningForm.setFmiddleName(rs11.getString("f_middle_name"));
			 joiningForm.setFlastName(rs11.getString("f_last_name"));
			 joiningForm.setFinitials(rs11.getString("f_initials"));
			 joiningForm.setFgender(rs11.getString("f_gender"));
			 joiningForm.setFdateofBirth((EMicroUtils.display(rs11.getDate("f_date_of_birth"))));
		 	 joiningForm.setFbirthplace(rs11.getString("f_birth_place"));
			 joiningForm.setFtelephoneNumber(rs11.getString("f_telephone_no"));
			 joiningForm.setFmobileNumber(rs11.getString("f_mobile_no"));
			 joiningForm.setFemailAddress(rs11.getString("f_email"));
			 joiningForm.setFbloodGroup(rs11.getString("f_blood_group"));
			 joiningForm.setFdependent(rs11.getString("dependent"));
			 joiningForm.setFemployeeofCompany(rs11.getString("employee_of_company"));
			 joiningForm.setFemployeeNumber(rs11.getString("employee_no_family"));
			 joiningForm.setFnominee(rs11.getString("fnominee"));
			}
			
			ResultSet rs9 = ad.selectQuery("select * from RELATIONSHIP  ");
			ArrayList relationIDList=new ArrayList();
			ArrayList relationValueList=new ArrayList();
	
			while(rs9.next()) {
				relationIDList.add(rs9.getString("Id"));
				relationValueList.add(rs9.getString("RELATIONSHIP"));
			}
			joiningForm.setRelationIDList(relationIDList);
			joiningForm.setRelationValueList(relationValueList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("modifyFamily", "modifyFamily");
		String parameter="familyDetails";
		request.setAttribute(parameter, parameter);
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		return mapping.findForward("display1");
	}
	
	public ActionForward editAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String reqID=request.getParameter("addressID");
		joiningForm.setReqAddressID(reqID);
		
		String sql3="select * from join_emp_address_approve where sl_no='"+joiningForm.getId()+"' and id='"+reqID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		try{
		while (rs11.next()) {
			
			 joiningForm.setNewid(rs11.getString("id"));
			    joiningForm.setAddressType(rs11.getString("address_type"));
			    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
				joiningForm.setHouseNumber(rs11.getString("house_no"));
				joiningForm.setAddressLine1(rs11.getString("address_line1"));
				joiningForm.setAddressLine2(rs11.getString("address_line2"));
				joiningForm.setAddressLine3(rs11.getString("address_line3"));
				joiningForm.setPostalCode(rs11.getString("postal_code"));
				joiningForm.setCity(rs11.getString("a_city"));
				joiningForm.setState(rs11.getString("a_state"));
				joiningForm.setCountry(rs11.getString("a_country"));
				joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
				joiningForm.setCompanyHousing(rs11.getString("company_housing"));
				joiningForm.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
				joiningForm.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
			    
		}
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX"));
		}
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		
		ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where  LAND1='"+joiningForm.getCountry()+"' ");
		ArrayList stateList=new ArrayList();
		ArrayList stateLabelList=new ArrayList();
		
		while(rs4.next()) {
			stateList.add(rs4.getString("BLAND"));
			stateLabelList.add(rs4.getString("BEZEI"));
		}
		joiningForm.setStateList(stateList);
		joiningForm.setStateLabelList(stateLabelList);
		
		
		
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		String parameter="addressDetails";
		request.setAttribute(parameter, parameter);
		joiningForm.setAddressStatus("Modify");
		
		request.setAttribute("modifyAddress", "modifyAddress");
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		return mapping.findForward("display1");
	}
	
	
	
	public ActionForward getEduStaes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		try{
			ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+joiningForm.getEdcountry()+"' order by BEZEI ");
			ArrayList stateList=new ArrayList();
			ArrayList stateLabelList=new ArrayList();
			
			while(rs4.next()) {
				stateList.add(rs4.getString("BLAND"));
				stateLabelList.add(rs4.getString("BEZEI"));
			}
			joiningForm.setStateList(stateList);
			joiningForm.setStateLabelList(stateLabelList);
		
			
			ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			
			ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL order by Education_Level ");
			ArrayList eduIDList=new ArrayList();
			ArrayList eduValueList=new ArrayList();
			
			while(rsEdu.next()) {
				eduIDList.add(rsEdu.getString("Id"));
				eduValueList.add(rsEdu.getString("Education_Level"));
			}
			joiningForm.setEduIDList(eduIDList);
			joiningForm.setEduValueList(eduValueList);
			
			ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION order by Qualification");
			ArrayList qulificationID=new ArrayList();
			ArrayList qulificationValueList=new ArrayList();
			
			while(rsQulif.next()) {
				qulificationID.add(rsQulif.getString("Id"));
				qulificationValueList.add(rsQulif.getString("Qualification"));
			}
			joiningForm.setQulificationID(qulificationID);
			joiningForm.setQulificationValueList(qulificationValueList);
			
			String parameter=request.getParameter("Param");
			String reqid=request.getParameter("id");
			request.setAttribute(parameter, parameter);
			
			HttpSession session=request.getSession();
			UserInfo userId=(UserInfo)session.getAttribute("user");
			
			String educationStatus=joiningForm.getEducationStatus();
			if(educationStatus.equalsIgnoreCase("save"))
			{
				ArrayList list = new ArrayList();
				String sql3="select * from join_emp_education_details_approve where sl_no='"+reqid+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					joiningForm = new JoiningReportForm();
					 joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setEducationalLevel(rs11.getString("education_level"));
					joiningForm.setQualification(rs11.getString("qualification"));
					joiningForm.setSpecialization(rs11.getString("specialization"));
					joiningForm.setUniversityName(rs11.getString("univarsity_name"));
					joiningForm.setUniverysityLocation(rs11.getString("university_location"));
					joiningForm.setEdstate(rs11.getString("e_state"));
					joiningForm.setEdcountry(rs11.getString("e_country"));
					joiningForm.setDurationofCourse(rs11.getString("duration_of_course"));
					joiningForm.setTimes(rs11.getString("time"));
					joiningForm.setFromDate((EMicroUtils.display(rs11.getDate("from_date"))));
					joiningForm.setToDate((EMicroUtils.display(rs11.getDate("to_date"))));
					joiningForm.setFullTimePartTime(rs11.getString("fulltime_parttime"));
					joiningForm.setPercentage(rs11.getString("percentage"));
					String fileName="";
					String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
					ResultSet rs12 = ad.selectQuery(doc);
					while(rs12.next()){
						fileName=rs12.getString("file_name");
					joiningForm.setEmpEduDoc(rs12.getString("file_name"));
				
					request.setAttribute("edudoc", "edudoc");
					}
					if(fileName.equalsIgnoreCase(""))
					{
						joiningForm.setEmpEduDoc("");
					}
					    list.add(joiningForm);
				}
				request.setAttribute("listName", list);
				joiningForm.setEducationStatus("save");
			request.setAttribute("addEducation", "addEducation");
			}else{
				joiningForm.setEducationStatus("modify");
				request.setAttribute("modifyEducation", "addEducation");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		return mapping.findForward("display1");
	}
	public ActionForward displayState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		try{
			ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+joiningForm.getCountry()+"' order by BEZEI ");
			ArrayList stateList=new ArrayList();
			ArrayList stateLabelList=new ArrayList();
			
			while(rs4.next()) {
				stateList.add(rs4.getString("BLAND"));
				stateLabelList.add(rs4.getString("BEZEI"));
			}
			joiningForm.setStateList(stateList);
			joiningForm.setStateLabelList(stateLabelList);
		
			
			ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			String parameter=request.getParameter("Param");
			String reqid=joiningForm.getId();
			request.setAttribute(parameter, parameter);
			
			String addressStatus=joiningForm.getAddressStatus();
			if(addressStatus.equalsIgnoreCase("Save")){
				request.setAttribute("addressAdd", "addressAdd");
				int countEmpAddress=0;
				HttpSession session=request.getSession();	
				UserInfo userId=(UserInfo)session.getAttribute("user");
				String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where sl_no='"+reqid+"' ";
				ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				if(countEmpAddress>0)
				{
		ArrayList list = new ArrayList();
		String sql3="select * from join_emp_address_approve where sl_no='"+reqid+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningReportForm();
			 joiningForm.setNewid(rs11.getString("id"));
			    joiningForm.setAddressType(rs11.getString("address_type"));
			    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
				joiningForm.setHouseNumber(rs11.getString("house_no"));
				joiningForm.setAddressLine1(rs11.getString("address_line1"));
				joiningForm.setAddressLine2(rs11.getString("address_line2"));
				joiningForm.setAddressLine3(rs11.getString("address_line3"));
				joiningForm.setPostalCode(rs11.getString("postal_code"));
				joiningForm.setCity(rs11.getString("a_city"));
				joiningForm.setState(rs11.getString("a_state"));
				joiningForm.setCountry(rs11.getString("a_country"));
				joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
				joiningForm.setCompanyHousing(rs11.getString("company_housing"));
				joiningForm.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
				joiningForm.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
			    list.add(joiningForm);
		}
		request.setAttribute("listName", list);
				joiningForm.setAddressStatus("Save");
				}
			
			
			}else{
				request.setAttribute("modifyAddress", "modifyAddress");
				joiningForm.setAddressStatus("Modify");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		return mapping.findForward("display1");
	}
	
	public ActionForward deletAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
			String type=request.getParameter("param2");
			System.out.println("tye="+type);
			String parameter="addressDetails";
			request.setAttribute(parameter, parameter);
			String reqAddressID=joiningForm.getReqAddressID();
			 
			 HttpSession session=request.getSession();	
				UserInfo userId=(UserInfo)session.getAttribute("user");
				
				String deleteAddress="delete from join_emp_address_Approve where sl_no='"+joiningForm.getId()+"' and id='"+reqAddressID+"'";
	    		int a=0;
	    		 a=ad.SqlExecuteUpdate(deleteAddress);
	    	 if(a>0)
				{
	    		 joiningForm.setMessage("Employee Address Details Deleted Successfully....");
					request.setAttribute("addressAdd", "addressAdd");
					joiningForm.setAddressStatus("Save");
					clearAddress(mapping, form, request, response);
				}
				else
				{
					joiningForm.setMessage1("Employee Address Details Are Not Deleted.Please Check....");
					request.setAttribute("modifyAddress", "modifyAddress");
				}
				
				try
				{
					int countEmpAddress=0;
					String checkjoin_emp_address="select count(user_id) from join_emp_address_Approve where sl_no='"+joiningForm.getId()+"' ";
					ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
					while(rs1.next()){
						countEmpAddress=rs1.getInt(1);			
					}
					if(countEmpAddress>0)
					{
				ArrayList list = new ArrayList();
				String sql3="select * from join_emp_address_Approve where sl_no='"+joiningForm.getId()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					joiningForm = new JoiningReportForm();
					 joiningForm.setNewid(rs11.getString("id"));
					    joiningForm.setAddressType(rs11.getString("address_type"));
					    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
						joiningForm.setHouseNumber(rs11.getString("house_no"));
						joiningForm.setAddressLine1(rs11.getString("address_line1"));
						joiningForm.setAddressLine2(rs11.getString("address_line2"));
						joiningForm.setAddressLine3(rs11.getString("address_line3"));
						joiningForm.setPostalCode(rs11.getString("postal_code"));
						joiningForm.setCity(rs11.getString("a_city"));
						joiningForm.setState(rs11.getString("a_state"));
						joiningForm.setCountry(rs11.getString("a_country"));
						joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
						joiningForm.setCompanyHousing(rs11.getString("company_housing"));
						joiningForm.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
						joiningForm.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
					    list.add(joiningForm);
				}
				request.setAttribute("listName", list);
				
				}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				clearAddress(mapping, form, request, response);
				return mapping.findForward("display1");
				}
	
	
	public ActionForward modifyAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
			String type=request.getParameter("param2");
			System.out.println("tye="+type);
			String parameter="addressDetails";
			request.setAttribute(parameter, parameter);
			String reqAddressID=joiningForm.getReqAddressID();
			 

			 HttpSession session=request.getSession();	
				UserInfo userId=(UserInfo)session.getAttribute("user");
			/* String insertAddress = "insert into " +
				"join_emp_address(employee_no,address_type,care_of_contact_name, house_no, address_line1," +
				" address_line2, address_line3, postal_code, a_city, a_state, " +
				"a_country, own_accomodation,company_housing,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"+ addressType+ "','"
			+ careofcontactname+ "','"+ houseNumber+ "','"+ addressLine1+ "','"+ addressLine2+ "','"
			+ addressLine3+ "','"+ postalCode+ "','"+ city+ "','"+ state+ "','"+ country+ "','"
			+ ownAccomodation+ "','"+ comapnayHousing+ "','"+ userId.getUserName()+ "','SAVE')";*/
			try{
			String addressType=joiningForm.getAddressType();
			 String careofcontactname=joiningForm.getCareofcontactname();
			 String houseNumber=joiningForm.getHouseNumber();
			 String addressLine1=joiningForm.getAddressLine1();
			 String addressLine2=joiningForm.getAddressLine2();
			 String addressLine3=joiningForm.getAddressLine3();
			 String postalCode=joiningForm.getPostalCode();
			 String city=joiningForm.getCity();
			 String state=joiningForm.getState();
			 String country=joiningForm.getCountry();
			 String ownAccomodation=joiningForm.getOwnAccomodation();
			 String comapnayHousing=joiningForm.getCompanyHousing();
	
			 
			 
				
			String updateAddress="update join_emp_address_Approve set address_type='"+addressType+"',care_of_contact_name='"+careofcontactname+"', house_no='"+houseNumber+"', address_line1='"+addressLine1+"'," +
				" address_line2='"+addressLine2+"', address_line3='"+addressLine3+"', postal_code='"+postalCode+"', a_city='"+city+"', a_state='"+state+"', " +
				"a_country='"+country+"', own_accomodation='"+ownAccomodation+"',company_housing='"+comapnayHousing+"' where  sl_no='"+joiningForm.getId()+"' and  id='"+reqAddressID+"' ";
			int a=0;
			a=ad.SqlExecuteUpdate(updateAddress);
			if(a>0)
			{
				joiningForm.setMessage1("");
				joiningForm.setMessage("Employee Address Details updated Successfully");
				request.setAttribute("addressAdd", "addressAdd");
				clearAddress(mapping, form, request, response);
			}
			else
			{
				joiningForm.setMessage("");
				joiningForm.setMessage1("Employee Address Details are not updated.Please Check....");
				request.setAttribute("modifyAddress", "modifyAddress");
			}
				
			
			
			    /*if(type.equalsIgnoreCase("delete")){
			    	for(int i=0;i<addressCheckLength;i++)
					{
			    		String deleteAddress="delete from join_emp_address where user_id='"+userId.getUserName()+"' and id='"+addressCheck[i]+"'";
			    		int a=0;
			    		 a=ad.SqlExecuteUpdate(deleteAddress);
			    	 if(a>0)
						{
							session.setAttribute("status","Employee Address Details Deleted Successfully....");
						}
						else
						{
							session.setAttribute("status","Employee Address Details Are Not Deleted.Please Check....");
						}
						
					}
			    	
			    }
			}*/
				}
				catch (Exception e) {
				e.printStackTrace();
				}
			
			
			try
			{
			ArrayList list = new ArrayList();
			int countEmpAddress=0;
			String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where sl_no='"+joiningForm.getId()+"' ";
			ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
			while(rs1.next()){
				countEmpAddress=rs1.getInt(1);			
			}
			if(countEmpAddress>0)
			{
			String sql3="select * from join_emp_address_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
				    joiningForm.setAddressType(rs11.getString("address_type"));
				    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
					joiningForm.setHouseNumber(rs11.getString("house_no"));
					joiningForm.setAddressLine1(rs11.getString("address_line1"));
					joiningForm.setAddressLine2(rs11.getString("address_line2"));
					joiningForm.setAddressLine3(rs11.getString("address_line3"));
					joiningForm.setPostalCode(rs11.getString("postal_code"));
					joiningForm.setCity(rs11.getString("a_city"));
					joiningForm.setState(rs11.getString("a_state"));
					joiningForm.setCountry(rs11.getString("a_country"));
					joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
					joiningForm.setCompanyHousing(rs11.getString("company_housing"));
					joiningForm.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
					joiningForm.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			clearAddress(mapping, form, request, response);
			return mapping.findForward("display1");
			}

	public ActionForward deleteFamily(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("modifyFamily()-----");
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="familyDetails";
			String type=request.getParameter("param2");
			request.setAttribute(parameter, parameter);
			
			String reqID=joiningForm.getReqFamilyID();
			
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
			
			String deleteAddress="delete from join_emp_family_details_Approve where sl_no='"+joiningForm.getId()+"' and id='"+reqID+"'";
    		int a=0;
    		 a=ad.SqlExecuteUpdate(deleteAddress);
    	 if(a>0)
			{
    		 joiningForm.setMessage("Family Details Deleted Successfully....");
				request.setAttribute("addFamily", "addFamily");
				clearFamilyDetails(mapping, form, request, response);
			}
			else
			{
				joiningForm.setMessage1("Family Details Are Not Deleted.Please Check....");
				request.setAttribute("modifyFamily", "modifyFamily");
			}
    	 
    	 try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_family_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				 joiningForm.setNewid(rs11.getString("id"));
			     joiningForm.setFamilyDependentTypeID(rs11.getString("family_dependent_type_id"));
			     joiningForm.setFtitle(rs11.getString("f_title"));
				 joiningForm.setFfirstName(rs11.getString("f_first_name"));
			     joiningForm.setFmiddleName(rs11.getString("f_middle_name"));
				 joiningForm.setFlastName(rs11.getString("f_last_name"));
				 joiningForm.setFinitials(rs11.getString("f_initials"));
				 joiningForm.setFgender(rs11.getString("f_gender"));
				 joiningForm.setFdateofBirth((EMicroUtils.display(rs11.getDate("f_date_of_birth"))));
			 	 joiningForm.setFbirthplace(rs11.getString("f_birth_place"));
				 joiningForm.setFtelephoneNumber(rs11.getString("f_telephone_no"));
				 joiningForm.setFmobileNumber(rs11.getString("f_mobile_no"));
				 joiningForm.setFemailAddress(rs11.getString("f_email"));
				 joiningForm.setFbloodGroup(rs11.getString("f_blood_group"));
				 joiningForm.setFdependent(rs11.getString("dependent"));
				 joiningForm.setFemployeeofCompany(rs11.getString("employee_of_company"));
				 joiningForm.setFemployeeNumber(rs11.getString("employee_no_family"));
				 joiningForm.setFnominee(rs11.getString("fnominee"));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			ResultSet rs9 = ad.selectQuery("select * from RELATIONSHIP  ");
			ArrayList relationIDList=new ArrayList();
			ArrayList relationValueList=new ArrayList();
			while(rs9.next()) {
				relationIDList.add(rs9.getString("Id"));
				relationValueList.add(rs9.getString("RELATIONSHIP"));
			}
			joiningForm.setRelationIDList(relationIDList);
			joiningForm.setRelationValueList(relationValueList);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			return mapping.findForward("display1");
	}
	
	public ActionForward modifyFamily(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		System.out.println("modifyFamily()-----");
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="familyDetails";
			String type=request.getParameter("param2");
			request.setAttribute(parameter, parameter);
			
			String reqID=joiningForm.getReqFamilyID();
			
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
			
			
			
			String familyDependentTypeID=joiningForm.getFamilyDependentTypeID();
			 String ftitle=joiningForm.getFtitle();
			 String ffirstName=joiningForm.getFfirstName();
			 String fmiddleName=joiningForm.getFmiddleName();
			 String flastName=joiningForm.getFlastName();
			 String finitials=joiningForm.getFinitials();
			 String fgender=joiningForm.getFgender();
			 String dob=joiningForm.getFdateofBirth();
			 String fdateofBirth="";
			 if(!dob.equalsIgnoreCase(""))
			  fdateofBirth=EMicroUtils.dateConvert(joiningForm.getFdateofBirth());
			 String fbirthplace=joiningForm.getFbirthplace();
			 String ftelephoneNumber=joiningForm.getFtelephoneNumber();
			 String fmobileNumber=joiningForm.getFmobileNumber();
			 String femailAddress=joiningForm.getFemailAddress();
			 String fbloodGroup=joiningForm.getFbloodGroup();
			 String fdependent=joiningForm.getFdependent();
			 String femployeeofCompany=joiningForm.getFemployeeofCompany();
			 String femployeeNumber=joiningForm.getFemployeeNumber();
			 String fnominee=joiningForm.getFnominee();
			 
			 if(joiningForm.getFemailAddress()==null)
			 {
				 joiningForm.setFemailAddress("");
			 }
			 
			 if(!joiningForm.getFemailAddress().equalsIgnoreCase(""))
			 {
			   if(joiningForm.getFemailAddress().contains("@"))
			   {
				String to[]=joiningForm.getFemailAddress().split("@");
				String todomain=to[1];
				
				if(JoiningReportAction.getNS(todomain)==false)
				{
					request.setAttribute("modifyFamily", "modifyFamily");
					joiningForm.setMessage1("Invalid Email Id");
					return mapping.findForward("display1");
				}
			   }
			   else
			   {
				   request.setAttribute("modifyFamily", "modifyFamily");
				   joiningForm.setMessage1("Invalid Email Id");
					return mapping.findForward("display1");
			   }
			 }
			 
			 
		/*	 if(fnominee.equalsIgnoreCase("Yes"))
				{
				String countn="select * from join_emp_family_details_Approve where sl_no="+joiningForm.getId()+" and fnominee='Yes'";
				ResultSet cf=ad.selectQuery(countn);
				try {
					if(cf.next())
					{
						joiningForm.setMessage("");
						joiningForm.setMessage1("Nominee can be only one person");
						 request.setAttribute("modifyFamily", "modifyFamily");
						return mapping.findForward("display1");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}*/
			 
				int a=0;
			/*	if(familyDependentTypeID.equalsIgnoreCase("1")||familyDependentTypeID.equalsIgnoreCase("2")){
					//query
					ArrayList list = new ArrayList();
			    String sql4="select COUNT(*) from join_emp_family_details_Approve where sl_no="+joiningForm.getId()+" and family_dependent_type_id="+familyDependentTypeID+"";
			    ResultSet rs12 = ad.selectQuery(sql4); 
			    try {
					while (rs12.next()) {
					
						  a=rs12.getInt(1);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    request.setAttribute("listName", list);
				}
			 */
				
			String updateFamily="update join_emp_family_details_Approve set family_dependent_type_id='"+familyDependentTypeID+"',f_title='"+ftitle+"', f_first_name='"+ffirstName+"', f_middle_name='"+fmiddleName+"'," +
			" f_last_name='"+flastName+"', f_initials='"+finitials+"', f_gender='"+fgender+"', f_date_of_birth='"+fdateofBirth+"', f_birth_place='"+fbirthplace+"', " +
			"f_telephone_no='"+ftelephoneNumber+"', f_mobile_no='"+fmobileNumber+"',f_email='"+femailAddress+"',f_blood_group='"+fbloodGroup+"',dependent='"+fdependent+"',employee_of_company='"+femployeeofCompany+"',employee_no_family='"+femployeeNumber+"',fnominee='"+fnominee+"' where sl_no='"+joiningForm.getId()+"' and id='"+reqID+"'";
			System.out.println("updateFamily="+updateFamily);
			 a=0;
			a=ad.SqlExecuteUpdate(updateFamily);
			
			 if(a>0)
				{
				 joiningForm.setMessage1("");
				 joiningForm.setMessage("Employee Family Details Updated Successfully....");
					request.setAttribute("addFamily", "addFamily");
					clearFamilyDetails(mapping, form, request, response);
				}
				else
				{
					joiningForm.setMessage("");
					joiningForm.setMessage1("Employee Family Details Are Not Updated.Please Check....");
					request.setAttribute("modifyFamily", "modifyFamily");
				}
				
				
			
			userId.getId();
			ResultSet rs9 = ad.selectQuery("select * from RELATIONSHIP  ");
			ArrayList relationIDList=new ArrayList();
			ArrayList relationValueList=new ArrayList();
			try{
			while(rs9.next()) {
				relationIDList.add(rs9.getString("Id"));
				relationValueList.add(rs9.getString("RELATIONSHIP"));
			}
			joiningForm.setRelationIDList(relationIDList);
			joiningForm.setRelationValueList(relationValueList);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
			/*
			   if(type.equalsIgnoreCase("delete")){
			    	for(int i=0;i<familyCheckLength;i++)
					{
			    		String deleteFamily="delete from join_emp_family_details where user_id='"+userId.getUserName()+"' and id='"+familyCheck[i]+"'";
			    		System.out.println("deleteFamily="+deleteFamily);
			    		int a=0;
			    	a=ad.SqlExecuteUpdate(deleteFamily);
			    	 
			    	 if(a>0)
						{
							session.setAttribute("status","Employee Family Details Deleted Successfully....");
						}
						else
						{
							session.setAttribute("status","Employee Family Details Are Not Deleted.Please Check....");
						}
					}
			    	
			    }
			}*/
				
			
			
			try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_family_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
			     joiningForm.setFamilyDependentTypeID(rs11.getString("family_dependent_type_id"));
			     joiningForm.setFtitle(rs11.getString("f_title"));
				 joiningForm.setFfirstName(rs11.getString("f_first_name"));
			     joiningForm.setFmiddleName(rs11.getString("f_middle_name"));
				 joiningForm.setFlastName(rs11.getString("f_last_name"));
				 joiningForm.setFinitials(rs11.getString("f_initials"));
				 joiningForm.setFgender(rs11.getString("f_gender"));
				 joiningForm.setFdateofBirth((EMicroUtils.display(rs11.getDate("f_date_of_birth"))));
			 	 joiningForm.setFbirthplace(rs11.getString("f_birth_place"));
				 joiningForm.setFtelephoneNumber(rs11.getString("f_telephone_no"));
				 joiningForm.setFmobileNumber(rs11.getString("f_mobile_no"));
				 joiningForm.setFemailAddress(rs11.getString("f_email"));
				 joiningForm.setFbloodGroup(rs11.getString("f_blood_group"));
				 joiningForm.setFdependent(rs11.getString("dependent"));
				 joiningForm.setFemployeeofCompany(rs11.getString("employee_of_company"));
				 joiningForm.setFemployeeNumber(rs11.getString("employee_no_family"));
				 joiningForm.setFnominee(rs11.getString("fnominee"));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return mapping.findForward("display1");
			}
	
	public ActionForward deleteEducation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="educationDetails";
			
			
			String reqEduID=request.getParameter("reqEduID");
			request.setAttribute(parameter, parameter);
			HttpSession session=request.getSession();	
			
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			
			String deleteAddress="delete from join_emp_education_details_Approve where sl_no='"+joiningForm.getId()+"' and id='"+reqEduID+"'";
    		System.out.println("delete education="+deleteAddress);
    		int a=0;
    	 a=ad.SqlExecuteUpdate(deleteAddress);
    	 if(a>0)
			{
    		 joiningForm.setMessage("Employee Education Details Deleted Successfully....");
    		 
    		 String deletedocs="delete from join_emp_education_documents where sl_no='"+joiningForm.getId()+"' and education='"+joiningForm.getQualification()+"'";
    	 	 a=ad.SqlExecuteUpdate(deletedocs);
    		 request.setAttribute("addEducation", "addEducation");
				clearEducationDetails(mapping, form, request, response);
			}
			else
			{
				joiningForm.setMessage1("Employee Education Details Are Not Deleted.Please Check....");
				request.setAttribute("addEducation", "addEducation");
				clearEducationDetails(mapping, form, request, response);
			}
			
			try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_education_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				 joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setEducationalLevel(rs11.getString("education_level"));
					joiningForm.setQualification(rs11.getString("qualification"));
					joiningForm.setSpecialization(rs11.getString("specialization"));
					joiningForm.setUniversityName(rs11.getString("univarsity_name"));
					joiningForm.setUniverysityLocation(rs11.getString("university_location"));
					joiningForm.setEdstate(rs11.getString("e_state"));
					joiningForm.setEdcountry(rs11.getString("e_country"));
					joiningForm.setDurationofCourse(rs11.getString("duration_of_course"));
					joiningForm.setTimes(rs11.getString("time"));
					joiningForm.setFromDate((EMicroUtils.display(rs11.getDate("from_date"))));
					joiningForm.setToDate((EMicroUtils.display(rs11.getDate("to_date"))));
					joiningForm.setFullTimePartTime(rs11.getString("fulltime_parttime"));
					joiningForm.setPercentage(rs11.getString("percentage"));
					String fileName="";
					String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
					ResultSet rs12 = ad.selectQuery(doc);
					while(rs12.next()){
						fileName=rs12.getString("file_name");
					joiningForm.setEmpEduDoc(rs12.getString("file_name"));
				
					request.setAttribute("edudoc", "edudoc");
					}
					if(fileName.equalsIgnoreCase(""))
					{
						joiningForm.setEmpEduDoc("");
					}
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			joiningForm.setEducationStatus("Save");
			return mapping.findForward("display1");
			}
	
	public ActionForward modifyEducation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
			String parameter="educationDetails";
			
			
			String reqEduID=request.getParameter("reqEduID");
			request.setAttribute(parameter, parameter);
			
			HttpSession session=request.getSession();	
			String[] educationCheck=joiningForm.getSelectEducation();
			
			ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State where LAND1='"+joiningForm.getEdcountry()+"' order by BEZEI ");
			ArrayList stateList=new ArrayList();
			ArrayList stateLabelList=new ArrayList();
			
			try {
				while(rs4.next()) {
					stateList.add(rs4.getString("BLAND"));
					stateLabelList.add(rs4.getString("BEZEI"));
				}
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			joiningForm.setStateList(stateList);
			joiningForm.setStateLabelList(stateLabelList);
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			 ResultSet rs9 = ad.selectQuery("select * from Country ");
				ArrayList countryList=new ArrayList();
				ArrayList countryLabelList=new ArrayList();
				try{
				while(rs9.next()) {
					countryList.add(rs9.getString("LAND1"));
					countryLabelList.add(rs9.getString("LANDX"));
				}
				joiningForm.setCountryList(countryList);
				joiningForm.setCountryLabelList(countryLabelList);
				
				ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL  ");
				ArrayList eduIDList=new ArrayList();
				ArrayList eduValueList=new ArrayList();
				
				while(rsEdu.next()) {
					eduIDList.add(rsEdu.getString("Id"));
					eduValueList.add(rsEdu.getString("Education_Level"));
				}
				joiningForm.setEduIDList(eduIDList);
				joiningForm.setEduValueList(eduValueList);
				
				ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION ");
				ArrayList qulificationID=new ArrayList();
				ArrayList qulificationValueList=new ArrayList();
				
				while(rsQulif.next()) {
					qulificationID.add(rsQulif.getString("Id"));
					qulificationValueList.add(rsQulif.getString("Qualification"));
				}
				joiningForm.setQulificationID(qulificationID);
				joiningForm.setQulificationValueList(qulificationValueList);
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				 String educationalLevel=joiningForm.getEducationalLevel();
				 String qualification=joiningForm.getQualification();
				 String specialization=joiningForm.getSpecialization();
				 String universityName=joiningForm.getUniversityName();
				 String univerysityLocation=joiningForm.getUniverysityLocation();
				 String edstate=joiningForm.getEdstate();
				 String edcountry=joiningForm.getEdcountry();
				 String durationofCourse=joiningForm.getDurationofCourse();
				 String time=joiningForm.getTimes();
				/* String fromdate=joiningForm.getFromDate();
				 String from="";
				 if(!fromdate.equalsIgnoreCase(""))
				 from=EMicroUtils.dateConvert(joiningForm.getFromDate());
				 String todate=joiningForm.getToDate();
				 String to="";
				 if(!todate.equalsIgnoreCase(""))
				  to=EMicroUtils.dateConvert(joiningForm.getToDate());*/
				 String yearofpassing=joiningForm.getYearofpassing();
				 String fullTimePartTime=joiningForm.getFullTimePartTime();
				 String percentage=joiningForm.getPercentage();
				
				/*String insertEducationDetails = "insert into join_emp_education_details" +
				"(employee_no,education_level, qualification, univarsity_name, " +
				"university_location, e_state, e_country, duration_of_course,from_date, " +
				"to_date, fulltime_parttime, percentage,user_id,Data_Status)values('"+ userId.getEmployeeNo()+ "','"
			+ educationalLevel+ "','"+ qualification+ "','"+ universityName
			+ "','"+ univerysityLocation+ "','"+ edstate+ "','"+ edcountry+ "','"+ durationofCourse
			+ "','"+ from+ "','"+ to+ "','"+ fullTimePartTime+ "','"+ percentage
			+ "','"+ userId.getUserName()+ "','SAVE')";*/
				 
				
			
				 String updateEducation="update join_emp_education_details_Approve set education_level='"+educationalLevel+"',qualification='"+qualification+"',specialization='"+specialization+"', univarsity_name='"+universityName+"'," +
	" university_location='"+univerysityLocation+"', e_state='"+edstate+"', e_country='"+edcountry+"', duration_of_course='"+durationofCourse+"', " +
	"from_date='', to_date='',fulltime_parttime='"+fullTimePartTime+"',percentage='"+percentage+"',year_of_passing='"+yearofpassing+"' where sl_no='"+joiningForm.getId()+"' and id='"+reqEduID+"'";
	System.out.println("updateAddress="+updateEducation);
					int a=0;
					a=ad.SqlExecuteUpdate(updateEducation);
					if(a>0)
					{
						joiningForm.setMessage("Employee Education Details Updated Successfully....");
						request.setAttribute("addEducation", "addEducation");
						clearEducationDetails(mapping, form, request, response);
					}
					else
					{
						joiningForm.setMessage1("Employee Education Details Are Not Deleted.Please Check....");
						request.setAttribute("addEducation", "addEducation");
						clearEducationDetails(mapping, form, request, response);
					}
					
			    
			   /* if(type.equalsIgnoreCase("delete"))
			    {
			    	for(int i=0;i<educationCheckLength;i++)
					{
			    		String deleteAddress="delete from join_emp_education_details where user_id='"+userId.getUserName()+"' and id='"+educationCheck[i]+"'";
			    		System.out.println("delete education="+deleteAddress);
			    		int a=0;
			    	 a=ad.SqlExecuteUpdate(deleteAddress);
			    	 if(a>0)
						{
							session.setAttribute("status","Employee Education Details Deleted Successfully....");
						}
						else
						{
							session.setAttribute("status","Employee Education Details Are Not Deleted.Please Check....");
						}
						
					}
			    	
			    }*/
			
						
			try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_education_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setEducationalLevel(rs11.getString("education_level"));
					joiningForm.setQualification(rs11.getString("qualification"));
					joiningForm.setSpecialization(rs11.getString("specialization"));
					joiningForm.setUniversityName(rs11.getString("univarsity_name"));
					joiningForm.setUniverysityLocation(rs11.getString("university_location"));
					joiningForm.setEdstate(rs11.getString("e_state"));
					joiningForm.setEdcountry(rs11.getString("e_country"));
					joiningForm.setDurationofCourse(rs11.getString("duration_of_course"));
					joiningForm.setTimes(rs11.getString("time"));
					joiningForm.setFromDate((EMicroUtils.display(rs11.getDate("from_date"))));
					joiningForm.setToDate((EMicroUtils.display(rs11.getDate("to_date"))));
					joiningForm.setFullTimePartTime(rs11.getString("fulltime_parttime"));
					joiningForm.setPercentage(rs11.getString("percentage"));
					String fileName="";
					String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
					ResultSet rs12 = ad.selectQuery(doc);
					while(rs12.next()){
						fileName=rs12.getString("file_name");
					joiningForm.setEmpEduDoc(rs12.getString("file_name"));
				
					request.setAttribute("edudoc", "edudoc");
					}
					if(fileName.equalsIgnoreCase(""))
					{
						joiningForm.setEmpEduDoc("");
					}
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return mapping.findForward("display1");
			}
	
	public ActionForward deleteExperience(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="experienceDetails";
			
			request.setAttribute(parameter, parameter);
			
			String reqExpID=request.getParameter("reqExpID");
			joiningForm.setReqExpID(reqExpID);
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			
			String  nameOfEmployer=joiningForm.getNameOfEmployer();
			 String  industry=joiningForm.getIndustry();
			 String  exCity=joiningForm.getExCity();
			 String  excountry=joiningForm.getExcountry();
			 String  positionHeld=joiningForm.getPositionHeld();
			 String  jobRole=joiningForm.getJobRole();
			 String  startDate=joiningForm.getStartDate();
			 String  endDate=joiningForm.getEndDate();
			 
			/* String insertExperience = "insert into join_emp_experience_details" +
				"(employee_no,name_of_employer, industry, ex_city, ex_country, position_held, job_role," +
				" start_date, end_date,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"
			+ nameOfEmployer+ "','"+ industry+ "','"+ exCity+ "','"+ excountry+ "','"+ positionHeld
			+ "','"+ jobRole+ "','"+ startDate+ "','"+ endDate+ "','"+ userId.getUserName()+ "','SAVE')";*/
			 
			 String deleteExperience="delete from join_emp_experience_details_Approve where sl_no='"+joiningForm.getId()+"' and id='"+reqExpID+"'";
	    		System.out.println("deleteExperience="+deleteExperience);
	    		int a=0;
	    	a=ad.SqlExecuteUpdate(deleteExperience);
				if(a>0)
				{
					joiningForm.setMessage("Employee Experience Details Updated Successfully....");
					request.setAttribute("addExperience", "addExperience");
					clearExperienceDetails(mapping, form, request, response);
				}
				else
				{
					joiningForm.setMessage1("Employee Experience Details Are Not Updated.Please Check....");
					request.setAttribute("addExperience", "addExperience");
					clearExperienceDetails(mapping, form, request, response);
				}
			 
			
	
			try{
				
				
			ResultSet rs9 = ad.selectQuery("select * from Country  ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			
			ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY");
			ArrayList industyID=new ArrayList();
			ArrayList industyValueList=new ArrayList();
			
			while(rsIndustry.next()) {
				industyID.add(rsIndustry.getString("Id"));
				industyValueList.add(rsIndustry.getString("Ind_Desc"));
			}
			joiningForm.setIndustyID(industyID);
			joiningForm.setIndustyValueList(industyValueList);
			}catch (Exception e) {
				e.printStackTrace();
			}

			
				/*try{
			
			if(experienceCheckLength>0)
			{
			    if(type.equalsIgnoreCase("modify")){
			for(int i=0;i<experienceCheckLength;i++)
			{
				String updateExperience="update join_emp_experience_details set name_of_employer='"+nameOfEmployer[i]+"',industry='"+industry[i]+"', ex_city='"+exCity[i]+"', ex_country='"+excountry[i]+"'," +
				" position_held='"+positionHeld[i]+"', job_role='"+jobRole[i]+"', start_date='"+startDate[i]+"', end_date='"+endDate[i]+"' where user_id='"+userId.getUserName()+"' and id='"+experienceCheck[i]+"'";
				System.out.println("updateAddress="+updateExperience);
				int a=0;
				a=ad.SqlExecuteUpdate(updateExperience);
				if(a>0)
				{
					session.setAttribute("status","Employee Experience Details Updated Successfully....");
				}
				else
				{
					session.setAttribute("status","Employee Experience Details Are Not Updated.Please Check....");
				}
				
			}
			    }
			    if(type.equalsIgnoreCase("delete")){
			    	for(int i=0;i<experienceCheckLength;i++)
					{
			    		String deleteExperience="delete from join_emp_experience_details where user_id='"+userId.getUserName()+"' and id='"+experienceCheck[i]+"'";
			    		System.out.println("deleteExperience="+deleteExperience);
			    		int a=0;
			    	a=ad.SqlExecuteUpdate(deleteExperience);
			    	 if(a>0)
						{
							session.setAttribute("status","Employee Experience Details Deleted Successfully....");
						}
						else
						{
							session.setAttribute("status","Employee Experience Details Are Not Deleted.Please Check....");
						}
						
						
					}
			    }
			}
				}
				catch (Exception e) {
					e.printStackTrace();
				}*/
			
			try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_experience_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				 joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setNameOfEmployer(rs11.getString("name_of_employer"));
					joiningForm.setIndustry(rs11.getString("industry"));
					 joiningForm.setExCity(rs11.getString("ex_city"));
					joiningForm.setExcountry(rs11.getString("ex_country"));
					 joiningForm.setPositionHeld(rs11.getString("position_held"));
					joiningForm.setJobRole(rs11.getString("job_role"));
					joiningForm.setStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
					joiningForm.setEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return mapping.findForward("display1");
			}
	
	public ActionForward modifyExperience(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="experienceDetails";
			
			request.setAttribute(parameter, parameter);
			
			String reqExpID=request.getParameter("reqExpID");
			joiningForm.setReqExpID(reqExpID);
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			
			String  nameOfEmployer=joiningForm.getNameOfEmployer();
			 String  industry=joiningForm.getIndustry();
			 String  exCity=joiningForm.getExCity();
			 String  excountry=joiningForm.getExcountry();
			 String  positionHeld=joiningForm.getPositionHeld();
			 String  jobRole=joiningForm.getJobRole();
				String  startDate=joiningForm.getStartDate();	
				String c[]=startDate.split("/");		
				  startDate=c[1]+"-"+c[0]+"-"+"01";
				 String  endDate=joiningForm.getEndDate();
				 String b[]=endDate.split("/");	
				 endDate=b[1]+"-"+b[0]+"-"+"01";
			 
			/* String insertExperience = "insert into join_emp_experience_details" +
				"(employee_no,name_of_employer, industry, ex_city, ex_country, position_held, job_role," +
				" start_date, end_date,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"
			+ nameOfEmployer+ "','"+ industry+ "','"+ exCity+ "','"+ excountry+ "','"+ positionHeld
			+ "','"+ jobRole+ "','"+ startDate+ "','"+ endDate+ "','"+ userId.getUserName()+ "','SAVE')";*/
			 String updateExperience="update join_emp_experience_details_approve set name_of_employer='"+nameOfEmployer+"',industry='"+industry+"', ex_city='"+exCity+"', ex_country='"+excountry+"'," +
				" position_held='"+positionHeld+"', job_role='"+jobRole+"', start_date='"+startDate+"', end_date='"+endDate+"',MiciroExp='"+joiningForm.getMicroExp()+"',MicroNo='"+joiningForm.getMicroNo()+"' where sl_no='"+joiningForm.getId()+"' and id='"+reqExpID+"'";
				System.out.println("updateAddress="+updateExperience);
				int a=0;
				a=ad.SqlExecuteUpdate(updateExperience);
				if(a>0)
				{
					joiningForm.setMessage("Employee Experience Details Updated Successfully....");
					request.setAttribute("addExperience", "addExperience");
					clearExperienceDetails(mapping, form, request, response);
				}
				else
				{
					joiningForm.setMessage1("Employee Experience Details Are Not Updated.Please Check....");
					request.setAttribute("addExperience", "addExperience");
					clearExperienceDetails(mapping, form, request, response);
				}
			 
			
	
			try{
				
				
			ResultSet rs9 = ad.selectQuery("select * from Country  ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			
			ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY");
			ArrayList industyID=new ArrayList();
			ArrayList industyValueList=new ArrayList();
			
			while(rs9.next()) {
				industyID.add(rs9.getString("Id"));
				industyValueList.add(rs9.getString("Ind_Desc"));
			}
			joiningForm.setIndustyID(industyID);
			joiningForm.setIndustyValueList(industyValueList);
			}catch (Exception e) {
				e.printStackTrace();
			}

			
				/*try{
			
			if(experienceCheckLength>0)
			{
			    if(type.equalsIgnoreCase("modify")){
			for(int i=0;i<experienceCheckLength;i++)
			{
				String updateExperience="update join_emp_experience_details set name_of_employer='"+nameOfEmployer[i]+"',industry='"+industry[i]+"', ex_city='"+exCity[i]+"', ex_country='"+excountry[i]+"'," +
				" position_held='"+positionHeld[i]+"', job_role='"+jobRole[i]+"', start_date='"+startDate[i]+"', end_date='"+endDate[i]+"' where user_id='"+userId.getUserName()+"' and id='"+experienceCheck[i]+"'";
				System.out.println("updateAddress="+updateExperience);
				int a=0;
				a=ad.SqlExecuteUpdate(updateExperience);
				if(a>0)
				{
					session.setAttribute("status","Employee Experience Details Updated Successfully....");
				}
				else
				{
					session.setAttribute("status","Employee Experience Details Are Not Updated.Please Check....");
				}
				
			}
			    }
			    if(type.equalsIgnoreCase("delete")){
			    	for(int i=0;i<experienceCheckLength;i++)
					{
			    		String deleteExperience="delete from join_emp_experience_details where user_id='"+userId.getUserName()+"' and id='"+experienceCheck[i]+"'";
			    		System.out.println("deleteExperience="+deleteExperience);
			    		int a=0;
			    	a=ad.SqlExecuteUpdate(deleteExperience);
			    	 if(a>0)
						{
							session.setAttribute("status","Employee Experience Details Deleted Successfully....");
						}
						else
						{
							session.setAttribute("status","Employee Experience Details Are Not Deleted.Please Check....");
						}
						
						
					}
			    }
			}
				}
				catch (Exception e) {
					e.printStackTrace();
				}*/
			
			try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_experience_details_approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setNameOfEmployer(rs11.getString("name_of_employer"));
					joiningForm.setIndustry(rs11.getString("industry"));
					 joiningForm.setExCity(rs11.getString("ex_city"));
					joiningForm.setExcountry(rs11.getString("ex_country"));
					 joiningForm.setPositionHeld(rs11.getString("position_held"));
					joiningForm.setJobRole(rs11.getString("job_role"));
					joiningForm.setStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
					joiningForm.setEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return mapping.findForward("display1");
			}
	public ActionForward deleteLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="languageDetails";
			request.setAttribute(parameter, parameter);
			
			String reqLangID=joiningForm.getReqLangID();
			
			HttpSession session=request.getSession();	
			String[] languageCheck=joiningForm.getSelectLanguage();	
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			String deleteLanguage="delete from join_emp_language_details_Approve where sl_no='"+joiningForm.getId()+"' and id='"+reqLangID+"'";
	    	 
    		int a=0;
    		a=ad.SqlExecuteUpdate(deleteLanguage);
    	 
    	 if(a>0)
			{
    		 joiningForm.setMessage("Employee Langauage Details Deleted Successfully....");
				request.setAttribute("addLanguage", "addLanguage");
				clearLanguageDetails(mapping, form, request, response);
			}
			else
			{
				joiningForm.setMessage1("Employee Langauage Details Are Not Deleted.Please Check....");
				request.setAttribute("addLanguage", "addLanguage");
				clearLanguageDetails(mapping, form, request, response);
			}
			
            
			
			int languageCheckLength=0;
			ResultSet rsLang = ad.selectQuery("select * from LANGUAGE ");
			ArrayList langID=new ArrayList();
			ArrayList langValueList=new ArrayList();
		try{
			while(rsLang.next()) {
				langID.add(rsLang.getString("Id"));
				langValueList.add(rsLang.getString("Language"));
			}
			joiningForm.setLanguageID(langID);
			joiningForm.setLanguageValueList(langValueList);
		}catch (Exception e) {
			e.printStackTrace();
		}
	

			
			
			
			try
			{
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_language_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				 joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setLanguage(rs11.getString("language"));
					joiningForm.setMotherTongue(rs11.getString("mother_tongue"));	
				    joiningForm.setLangSpeaking(rs11.getString("spoken"));
				    joiningForm.setLangRead(rs11.getString("reading"));
					joiningForm.setLangWrite(rs11.getString("writing"));
					joiningForm.setLangstartDate((EMicroUtils.display(rs11.getDate("l_start_date"))));
					joiningForm.setLangendDate((EMicroUtils.display(rs11.getDate("l_end_date"))));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return mapping.findForward("display1");
			}
	public ActionForward modifyLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="languageDetails";
			request.setAttribute(parameter, parameter);
			
			String reqLangID=joiningForm.getReqLangID();
			
			
			HttpSession session=request.getSession();	
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			
			 String  language=joiningForm.getLanguage();
			 String  motherTongue=joiningForm.getMotherTongue();	
		     String  langSpeaking=joiningForm.getLangSpeaking();
		     String  langRead=joiningForm.getLangRead();
			 String  langWrite=joiningForm.getLangWrite();
			 String  langstartDate=joiningForm.getLangstartDate();
			 String  langendDate=joiningForm.getLangendDate();
			 int a=0;
				ArrayList list = new ArrayList();
			
			 String updateLanguage="update join_emp_language_details_Approve set language='"+language+"',mother_tongue='"+motherTongue+"', spoken='"+langSpeaking+"', reading='"+langSpeaking+"'," +
				" writing='"+langWrite+"' where sl_no='"+joiningForm.getId()+"' and id='"+reqLangID+"'";
				System.out.println("updateLanguage="+updateLanguage);
		
				a=ad.SqlExecuteUpdate(updateLanguage);
				 if(a>0)
					{
					 joiningForm.setMessage("Employee Langauage Details Updated Successfully....");
					 clearLanguageDetails(mapping, form, request, response);
					 request.setAttribute("addLanguage", "addLanguage");
					}
					else
					{
						joiningForm.setMessage1("Employee Langauage Details Are Not Updated.Please Check....");
						 request.setAttribute("addLanguage", "addLanguage");
					}
				
			
			int languageCheckLength=0;
			ResultSet rsLang = ad.selectQuery("select * from LANGUAGE ");
			ArrayList langID=new ArrayList();
			ArrayList langValueList=new ArrayList();
		try{
			while(rsLang.next()) {
				langID.add(rsLang.getString("Id"));
				langValueList.add(rsLang.getString("Language"));
			}
			joiningForm.setLanguageID(langID);
			joiningForm.setLanguageValueList(langValueList);
		}catch (Exception e) {
			e.printStackTrace();
		}
			try
			{
			 list = new ArrayList();
			String sql3="select * from join_emp_language_details_Approve where sl_no='"+joiningForm.getId()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setLanguage(rs11.getString("language"));
					joiningForm.setMotherTongue(rs11.getString("mother_tongue"));	
				    joiningForm.setLangSpeaking(rs11.getString("spoken"));
				    joiningForm.setLangRead(rs11.getString("reading"));
					joiningForm.setLangWrite(rs11.getString("writing"));
					joiningForm.setLangstartDate((EMicroUtils.display(rs11.getDate("l_start_date"))));
					joiningForm.setLangendDate((EMicroUtils.display(rs11.getDate("l_end_date"))));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	
			return mapping.findForward("display1");
			}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		
		
		
		String sql="select * from links where module='"+id+"' and sub_linkname is null";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			
			 while(rs.next()){
				 hm.put(rs.getString("link_path")+"?method="+rs.getString("method")+"&sId="+rs.getString("link_name")+"&id="+rs.getString("module"), rs.getString("link_name"));
				 
			}
			 
			 session.setAttribute("SUBLINKS", hm);
			
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward reportgenerate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm1 = (JoiningReportForm) form;// TODO Auto-generated method stub
	       HttpSession session=request.getSession();	
		
		UserInfo userId=(UserInfo)session.getAttribute("user");
		userId.getId();
		
		joiningForm1.setMessage("");
		joiningForm1.setMessage1("");
		
		String req=request.getParameter("id");
		
		   
	   //insert log
		String up="insert into JoinReport_Print_log (emp_id,Created_date,Created_By) values ('"+userId.getEmployeeNo()+"',getdate(),'"+userId.getEmployeeNo()+"')";
		int y=ad.SqlExecuteUpdate(up);
		
	   	
	   	DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal11 = Calendar.getInstance();							
		String datecurren11t=dateFormat1.format(cal11.getTime());
	  
		DateFormat newdt = new SimpleDateFormat("dd/MM/yyyy");						
		String displaydate=newdt.format(cal11.getTime());

	
		
		String x[]=null;
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String exportdate=ft.format(dNow);
		
		
		String forward = null;
		ArrayList workFlow1  = new  ArrayList();
		
		
		 Document document = new Document();
			InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		Properties props = null;
		try {
			props = PropertiesLoaderUtils.loadAllProperties("db.properties");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		PropertyPlaceholderConfigurer props2 = new PropertyPlaceholderConfigurer();
		props2.setProperties(props);
		try {
			props.load(in);
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	 	 
	 	  BaseFont base = null;
			try {
			
					try {
						base = BaseFont.createFont("\\Windows\\fonts\\wingding_0.ttf", BaseFont.IDENTITY_H, false);
					} catch (DocumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 	 
			Font font = new Font(base, 16f, Font.BOLD);
		  char checked='\u00FE';
		  char unchecked='\u00A8';
	 	
	 	String uploadFilePath=props.getProperty("file.uploadFilePath");
	 	String filePath=uploadFilePath+"/Joining Report/";
	 	
		File destinationDir = new File(filePath);
		if(!destinationDir.exists())
		{
			destinationDir.mkdirs();
			
			
			
		}
		String empfullnm="";
		PdfWriter writer;
			try {
				try {
			
					//empName
					
					String namesql="select * from join_emp_personal_info_approve  where join_emp_personal_info_approve.id='"+req+"'";
									ResultSet rsname = ad.selectQuery(namesql);
									try {
										if(rsname.next())
										{
											empfullnm=rsname.getString("EMP_FULLNAME").replace(" ", "_");
										}
									} catch (SQLException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
					
					
					writer = PdfWriter.getInstance(document, new FileOutputStream(filePath.replace("/", "\\")+""+empfullnm+"_Joining_Report.pdf"));
				
	        Rectangle rect = new Rectangle(30, 30, 550, 800);
	        writer.setBoxSize("art", rect);
	        
	        
	        Font largeBold = new Font(Font.FontFamily.COURIER, 10,
	                Font.BOLD);
	       
	       Font columnheader = new Font(Font.FontFamily.UNDEFINED, 10,
	                Font.BOLD);
	       
	       Font smallfont = new Font(Font.FontFamily.COURIER,10);
	       smallfont.setColor(BaseColor.BLUE);
	       Font mediumfont = new Font(Font.FontFamily.UNDEFINED,8,Font.BOLD);
	       
	       HeaderFooterPageEventJOINReportView event = new HeaderFooterPageEventJOINReportView(userId.getEmployeeNo(), userId.getFullName());
	        writer.setPageEvent(event);
	    
	       
	        document.open();         
	     
	        
	        
	    	   PdfPTable table1 = new PdfPTable(4); // 3 columns.
	           table1.setWidthPercentage(100); //Width 100%
	           table1.setSpacingBefore(10f); //Space before table
	           table1.setSpacingAfter(10f); //Space after table
	           //Set Column widths
	           float[] columnWidths = {6f,1f,3f,3f};;
			  table1.setWidths(columnWidths);
			  
			 /* PdfContentByte canvas = writer.getDirectContent();
		        Rectangle rect1 = new Rectangle(36, 36, 559, 806);
		        rect1.setBorder(Rectangle.BOX);
		        rect1.setBorderWidth(0.7f);
		        canvas.rectangle(rect1);*/
		      
			  	PdfPCell p4a = new PdfPCell(new Paragraph("To,",columnheader));	 
			  	p4a.setColspan(2);
			  	p4a.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				table1.addCell(p4a);
			
				PdfPCell p2a = new PdfPCell(new Paragraph("Date: ",columnheader));	
				p2a.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				p2a.setHorizontalAlignment(Element.ALIGN_RIGHT);
				p2a.setVerticalAlignment(Element.ALIGN_RIGHT);
				table1.addCell(p2a);
				PdfPCell p3a = new PdfPCell(new Paragraph(displaydate,smallfont));	
				p3a.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				
				table1.addCell(p3a);
				
				PdfPCell p4b = new PdfPCell(new Paragraph("The Sr.VP - Human Resources,",columnheader));			  
				p4b.setColspan(2); 
				p4b.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				table1.addCell(p4b);				
				PdfPCell p4e = new PdfPCell(new Paragraph("Emp.Code: ",columnheader));
				p4e.setHorizontalAlignment(Element.ALIGN_RIGHT);
				p4e.setVerticalAlignment(Element.ALIGN_RIGHT);
				p4e.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				table1.addCell(p4e);
				PdfPCell p4e1 = new PdfPCell(new Paragraph("",smallfont));		
				p4e1.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
				table1.addCell(p4e1);
		
				PdfPCell p4c = new PdfPCell(new Paragraph("Micro Labs Limited, Bangalore.",columnheader));		
				p4c.setBorder(com.itextpdf.text.Rectangle.NO_BORDER);
			  	p4c.setColspan(5);
				table1.addCell(p4c);
				
				table1.setHorizontalAlignment(Element.ALIGN_LEFT);
				
				document.add(table1);		
				
				  Paragraph paragraph1 = new Paragraph(" Dear Sir,");	
	     					document.add(paragraph1);
	          
	     					
				  Paragraph p1 = new Paragraph("Sub: Joining Report");	
				              p1.setAlignment(Element.ALIGN_CENTER);
					     					document.add(p1);
					     					 document.add( Chunk.NEWLINE );
				   
				  Paragraph p2 = new Paragraph(" With reference to your offer letter dated                       , I hereby join the duty with the organization and provide following details:",mediumfont);	
				document.add(p2);
				
				
				//officicl deatisl
				ArrayList pers=new ArrayList();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//get current date time with Date()
		         Date date=new Date();
				System.out.println(dateFormat.format(date));
				String age="";
	
				String sql31="select datediff(year,right(dob,4)+'-'+right(left(dob,5),2)+'-'+left(dob,2),getdate()) as age,Bank.BNAME,pay_method,ISOCD,LANDX,Religion,NATIONALITY.nationality,* from join_emp_personal_info_approve left outer join country on join_emp_personal_info_approve.country_of_birth=country.LAND1 left outer join RELIGION on join_emp_personal_info_approve.religous_denomination=RELIGION.Id"
						+ "  left outer join NATIONALITY on join_emp_personal_info_approve.nationality=NATIONALITY.Id left outer join Currency on join_emp_personal_info_approve.WAERS=Currency.WAERS	  left outer join PAYMODE on join_emp_personal_info_approve.PAYMENT_METHOD=PAYMODE.pay_id	    left outer join Bank on join_emp_personal_info_approve.BANKID=Bank.BANKID left outer join department on department.dptid=join_emp_personal_info_approve.department left outer join Designation on Designation.dsgid=join_emp_personal_info_approve.Designation"
						+ "  where join_emp_personal_info_approve.id='"+req+"'";
								ResultSet rs12 = ad.selectQuery(sql31);
				try {
					while (rs12.next()) {
					
						joiningForm1.setTitle(rs12.getString("title"));
						age=rs12.getString("age");
						joiningForm1.setDateofBirth(rs12.getString("DOB"));
						joiningForm1.setFirstName(rs12.getString("EMP_FULLNAME"));	
						
						if(rs12.getString("SEX").equalsIgnoreCase("M"))
						joiningForm1.setGender("Male");
						else
							joiningForm1.setGender("Female");
						
						joiningForm1.setPanno(rs12.getString("panno"));
						joiningForm1.setUanno(rs12.getString("uanno"));
						joiningForm1.setAdharno(rs12.getString("adharno"));
						joiningForm1.setFirstName(rs12.getString("EMP_FULLNAME")+" "+ rs12.getString("middle_name")+" "+rs12.getString("last_name"));	
						joiningForm1.setMiddleName(rs12.getString("middle_name"));
						joiningForm1.setLastName(rs12.getString("last_name"));
						joiningForm1.setDateofjoin(rs12.getString("DOJ"));
						joiningForm1.setDepartment(rs12.getString("dptstxt"));
						joiningForm1.setDesignation(rs12.getString("dsgstxt"));
						joiningForm1.setLocation(rs12.getString("Location"));
						
						joiningForm1.setNickName(rs12.getString("nick_name"));
					    joiningForm1.setTitle(rs12.getString("title"));
					    
					    if(rs12.getString("marital_status").equalsIgnoreCase("0"))
						joiningForm1.setMaritalStatus("Single");
					    if(rs12.getString("marital_status").equalsIgnoreCase("1"))
							joiningForm1.setMaritalStatus("Married");
					    if(rs12.getString("marital_status").equalsIgnoreCase("2"))
							joiningForm1.setMaritalStatus("Widow");
					    if(rs12.getString("marital_status").equalsIgnoreCase("3"))
							joiningForm1.setMaritalStatus("Divorce");
					    if(rs12.getString("marital_status").equalsIgnoreCase("5"))
							joiningForm1.setMaritalStatus("Separated");

						
						joiningForm1.setBirthplace(rs12.getString("birth_place"));
						joiningForm1.setCountryofBirth(rs12.getString("LANDX"));
						joiningForm1.setCaste(rs12.getString("caste"));
						joiningForm1.setReligiousDenomination(rs12.getString("Religion"));
						joiningForm1.setNationality(rs12.getString("nationality"));
						joiningForm1.setTelephoneNumber(rs12.getString("telephone_no"));
						joiningForm1.setMobileNumber(rs12.getString("mobile_no"));
						joiningForm1.setFaxNumber(rs12.getString("fax_no"));
						joiningForm1.setEmailAddress(rs12.getString("email_address"));
						joiningForm1.setBloodGroup(rs12.getString("blood_group"));
						joiningForm1.setPermanentAccountNumber(rs12.getString("permanent_acno"));
						joiningForm1.setPassportNumber(rs12.getString("passport_no"));
						joiningForm1.setPlaceofIssueofPassport(rs12.getString("place_of_issue_of_passport"));
						joiningForm1.setDateofissueofPassport(rs12.getString("date_of_issue_of_passp"));
						joiningForm1.setDateofexpiryofPassport(rs12.getString("date_of_expiry_of_passport"));
						joiningForm1.setPersonalIdentificationMarks(rs12.getString("personal_identification_mark"));
						joiningForm1.setPhysicallyChallenged(rs12.getString("physiaclly_challenged"));
						joiningForm1.setPhysicallyChallengeddetails(rs12.getString("physically_challenged_details"));
						joiningForm1.setEmergencyContactPersonName(rs12.getString("emergency_contact_person_name"));
						joiningForm1.setEmergencyContactPersonName1(rs12.getString("emergency_contact_person_name1"));
						joiningForm1.setEmergencyContactAddressLine1(rs12.getString("emegency_contact_addressline1"));
						joiningForm1.setEmergencyContactAddressLine2(rs12.getString("emegency_contact_addressline2"));
						joiningForm1.setEmergCntAdd11(rs12.getString("emegency_contact_addressline3"));
						joiningForm1.setEmergCntAdd111(rs12.getString("emegency_contact_addressline4"));
						joiningForm1.setEmergCntAdd22(rs12.getString("emergCntAdd22"));
						joiningForm1.setEmergCntAdd222(rs12.getString("emergCntAdd222"));			
							joiningForm1.setEmergencyMobileNumber(rs12.getString("emegency_mobile_number"));
						joiningForm1.setEmergencyTelephoneNumber(rs12.getString("emegency_telephone_number"));
						joiningForm1.setEmergencyMobileNumber1(rs12.getString("emegency_mobile_number1"));
						joiningForm1.setEmergencyTelephoneNumber1(rs12.getString("emegency_telephone_number1"));
						joiningForm1.setNoOfChildrens(rs12.getString("number_of_childrens"));
						joiningForm1.setWebsite(rs12.getString("website"));
						/*String saveStatus=rs12.getString("Data_Status");
						if(saveStatus.equalsIgnoreCase("Approved")){
							request.setAttribute("blockEmployeeName", "blockEmployeeName");
							String bloodGroup=rs12.getString("blood_group");
							if(!(bloodGroup.equalsIgnoreCase("")))
							{
								request.setAttribute("blockBloodGroup", "blockBloodGroup");
							}
						}*/
						joiningForm1.setEmergCity1(rs12.getString("emerg_city1"));
						joiningForm1.setEmergCity2(rs12.getString("emerg_city2"));
						joiningForm1.setEmergState1(rs12.getString("emerg_state1"));
						joiningForm1.setEmergState2(rs12.getString("emerg_state2"));
						
						
						joiningForm1.setSalaryCurrency(rs12.getString("isocd"));
						joiningForm1.setPaymentMethod(rs12.getString("pay_method"));	
						joiningForm1.setBranchName(rs12.getString("BRANCH"));
						joiningForm1.setBankName(rs12.getString("BNAME"));
						joiningForm1.setAccountType(rs12.getString("BACCTYP"));
						joiningForm1.setAccountNumber(rs12.getString("BACCNO"));			
						joiningForm1.setIfsCCode(rs12.getString("IFSC_CODE"));
						joiningForm1.setMicrCode(rs12.getString("MICR_CODE"));
						
						pers.add(joiningForm1);
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				PdfPTable tableoff = new PdfPTable(4); // 3 columns.
				tableoff.setWidthPercentage(100); //Width 100%
				tableoff.setSpacingBefore(10f); //Space before table
				tableoff.setSpacingAfter(10f); //Space after table
		           //Set Column widths
		         float[]  columnWidth1s = {3f,3f,3f,3f};;
				  table1.setWidths(columnWidth1s);
			
				  
				  PdfPCell o1 = new PdfPCell(new Paragraph("Official Details",columnheader));			  
				  o1.setColspan(5);
				  tableoff.addCell(o1);
				
					PdfPCell o4 = new PdfPCell(new Paragraph("Full Name",mediumfont));	 
					  tableoff.addCell(o4);
						PdfPCell o5 = new PdfPCell(new Paragraph(joiningForm1.getTitle()+" "+joiningForm1.getFirstName(),smallfont));	 
						o5.setColspan(3);
						tableoff.addCell(o5);
			     					
						
						//empooff
						
								
					PdfPCell o6 = new PdfPCell(new Paragraph("Date Of joining",mediumfont));	 
					  tableoff.addCell(o6);
						PdfPCell o7 = new PdfPCell(new Paragraph(joiningForm1.getDateofjoin(),smallfont));	 
						tableoff.addCell(o7);
						PdfPCell o8 = new PdfPCell(new Paragraph("Designation",mediumfont));	 
						  tableoff.addCell(o8);
							PdfPCell o9 = new PdfPCell(new Paragraph(joiningForm1.getDesignation(),smallfont));	 
							tableoff.addCell(o9);
							
							PdfPCell o10 = new PdfPCell(new Paragraph("Division / Dept",mediumfont));	 
							  tableoff.addCell(o10);
								PdfPCell o11 = new PdfPCell(new Paragraph(joiningForm1.getDepartment(),smallfont));	 
								tableoff.addCell(o11);
								PdfPCell o12 = new PdfPCell(new Paragraph("Working Location",mediumfont));	 
							  tableoff.addCell(o12);
								PdfPCell o13 = new PdfPCell(new Paragraph(joiningForm1.getLocation(),smallfont));	 
								tableoff.addCell(o13);
			     					
							PdfPCell o14 = new PdfPCell(new Paragraph("PAN No.",mediumfont));	 
							  tableoff.addCell(o14);
								PdfPCell o15 = new PdfPCell(new Paragraph(joiningForm1.getPanno(),smallfont));	 
								tableoff.addCell(o15);
								PdfPCell o16 = new PdfPCell(new Paragraph("Aadhar No",mediumfont));	 
							  tableoff.addCell(o16);
								PdfPCell o17 = new PdfPCell(new Paragraph(joiningForm1.getAdharno(),smallfont));	 
								tableoff.addCell(o17);		
								
								
								PdfPCell o18 = new PdfPCell(new Paragraph("PF UAN No.",mediumfont));	 
								  tableoff.addCell(o18);
								  PdfPCell o151 = new PdfPCell(new Paragraph(joiningForm1.getUanno(),smallfont));	 
									tableoff.addCell(o151);
								  PdfPCell io1 = new PdfPCell(new Paragraph("Bank A/c No.",mediumfont));			  
								  tableoff.addCell(io1);
								  PdfPCell o153 = new PdfPCell(new Paragraph(joiningForm1.getAccountNumber(),smallfont));	 
									tableoff.addCell(o153);
								
									  
									  PdfPCell yo1 = new PdfPCell(new Paragraph("Name of Bank",mediumfont));			  
									  tableoff.addCell(yo1);
									  PdfPCell o315 = new PdfPCell(new Paragraph(joiningForm1.getBankName(),smallfont));	 
										tableoff.addCell(o315);
									  PdfPCell ko1 = new PdfPCell(new Paragraph("Branch Address",mediumfont));			  
									  tableoff.addCell(ko1);
									  PdfPCell o1d5 = new PdfPCell(new Paragraph(joiningForm1.getBranchName(),smallfont));	 
										tableoff.addCell(o1d5);
									  
																
									
						document.add(tableoff);	
						
						
						
						PdfPTable tablepers = new PdfPTable(4); // 3 columns.
						tablepers.setWidthPercentage(100); //Width 100%
						tablepers.setSpacingBefore(10f); //Space before table
						tablepers.setSpacingAfter(10f); //Space after table
				           //Set Column widths
				         float[]  columnWidth2s = {3f,3f,3f,3f};;
				         tablepers.setWidths(columnWidth2s);
					
				         
				         //address details 
				         
				        
				         
				         String houseno="";
				         String add1="";
				         String add2="";
				         String add3="";
				         String city="";
				         String state="";
				         String pin="";
				         String coun="";
				         
				         String houseno1="";
				         String add11="";
				         String add21="";
				         String add31="";
				         String city1="";
				         String state1="";
				         String pin1="";
				         String coun1="";
				 		
										
								
					String sql33="select BEZEI,LANDX,* from join_emp_address_approve"
							+ " left outer join country on join_emp_address_approve.a_country=country.LAND1  "
							+ "left outer join State on join_emp_address_approve.a_state=state.BLAND  where sl_no='"+req+"'   and a_country=state.land1 and address_type in ('001','003')";
					ResultSet rs141 = ad.selectQuery(sql33);
					try {
						while (rs141.next()) {
							JoiningReportForm	joiningForm = new JoiningReportForm();
							    joiningForm.setNewid(rs141.getString("id"));
							    if(rs141.getString("address_type").equalsIgnoreCase("001"))
							    {
							    	houseno=rs141.getString("house_no");
							         add1=rs141.getString("address_line1");
							          add2=rs141.getString("address_line2");
							          add3=rs141.getString("address_line3");
							          city=rs141.getString("a_city");
							          state=rs141.getString("BEZEI");
							          pin=rs141.getString("postal_code");
							          coun=rs141.getString("landx");
							    }
							    if(rs141.getString("address_type").equalsIgnoreCase("003"))
							    {
							    	houseno1=rs141.getString("house_no");
							         add11=rs141.getString("address_line1");
							          add21=rs141.getString("address_line2");
							          add31=rs141.getString("address_line3");
							          city1=rs141.getString("a_city");
							          state1=rs141.getString("BEZEI");
							          pin1=rs141.getString("postal_code");
							          coun1=rs141.getString("landx");
							    }
							    
							  
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					if(add11.equalsIgnoreCase(""))
						add11=" ";
					if(add21.equalsIgnoreCase(""))
						add21=" ";
					if(add31.equalsIgnoreCase(""))
						add31=" ";
					if(add1.equalsIgnoreCase(""))
						add1=" ";
					if(add2.equalsIgnoreCase(""))
						add2=" ";
					if(add3.equalsIgnoreCase(""))
						add3=" ";
					
						  PdfPCell pe1 = new PdfPCell(new Paragraph("Personal Details",columnheader));			  
						  pe1.setColspan(5);
						  tablepers.addCell(pe1);
						  
						  //hous no addrws1
						  PdfPCell pe2 = new PdfPCell(new Paragraph("Present Address",mediumfont));	 
						  tablepers.addCell(pe2);
						  PdfPCell peo4 = new PdfPCell(new Paragraph(houseno1+","+add11,smallfont));	 
						  peo4.setColspan(3);
						  tablepers.addCell(peo4);
						  
						  //adres 2
						  PdfPCell pek2 = new PdfPCell(new Paragraph());	 
						  tablepers.addCell(pek2);						
						  PdfPCell pej1 = new PdfPCell(new Paragraph(add21,smallfont));			  
						  pej1.setColspan(4);
						  tablepers.addCell(pej1);
						  
						  //adress 3
						  PdfPCell pem2 = new PdfPCell(new Paragraph());	 
						  tablepers.addCell(pem2);
						  PdfPCell pep1 = new PdfPCell(new Paragraph(add31,smallfont));			  
						  pep1.setColspan(4);
						  tablepers.addCell(pep1);
						  
						  PdfPCell pe3 = new PdfPCell(new Paragraph("City",mediumfont));	 
						  tablepers.addCell(pe3);
						  PdfPCell pe4 = new PdfPCell(new Paragraph(city1,smallfont));	 
						  tablepers.addCell(pe4);
						  PdfPCell pe5 = new PdfPCell(new Paragraph("State",mediumfont));			  
						  tablepers.addCell(pe5);
						  PdfPCell pe6 = new PdfPCell(new Paragraph(state1,smallfont));	 
						  tablepers.addCell(pe6);
						  
						  
						  PdfPCell pe7 = new PdfPCell(new Paragraph("Pin",mediumfont));	 
						  tablepers.addCell(pe7);
						  PdfPCell pe8 = new PdfPCell(new Paragraph(pin1,smallfont));	 
						  tablepers.addCell(pe8);
						  PdfPCell pe9 = new PdfPCell(new Paragraph("Country",mediumfont));			  
						  tablepers.addCell(pe9);
						  PdfPCell pe10 = new PdfPCell(new Paragraph(coun1,smallfont));	 
						  tablepers.addCell(pe10);
						  
						  //hous no addrws1
						  PdfPCell pe11 = new PdfPCell(new Paragraph("Permanent Address",mediumfont));	  
						  tablepers.addCell(pe11);
						  PdfPCell po4 = new PdfPCell(new Paragraph(houseno+","+add1,smallfont));	 
						  po4.setColspan(3);
						  tablepers.addCell(po4);
						  
						  //adres 2
						  PdfPCell ek2 = new PdfPCell(new Paragraph());	 
						  tablepers.addCell(ek2);
						  PdfPCell ej1 = new PdfPCell(new Paragraph(add2,smallfont));			  
						  ej1.setColspan(4);
						  tablepers.addCell(ej1);
						  
						  //adress 3
						  PdfPCell pm2 = new PdfPCell(new Paragraph());	 
						  tablepers.addCell(pm2);
						  PdfPCell ep1 = new PdfPCell(new Paragraph(add3,smallfont));			  
						  ep1.setColspan(4);
						  tablepers.addCell(ep1);
						  
						  PdfPCell pe12 = new PdfPCell(new Paragraph("City",mediumfont));	 
						  tablepers.addCell(pe12);
						  PdfPCell pe13 = new PdfPCell(new Paragraph(city,smallfont));	 
						  tablepers.addCell(pe13);
						  PdfPCell pe14 = new PdfPCell(new Paragraph("State",mediumfont));			  
						  tablepers.addCell(pe14);
						  PdfPCell pe15 = new PdfPCell(new Paragraph(state,smallfont));	 
						  tablepers.addCell(pe15);
						  
						  
						  PdfPCell pe16 = new PdfPCell(new Paragraph("Pin",mediumfont));	 
						  tablepers.addCell(pe16);
						  PdfPCell pe17 = new PdfPCell(new Paragraph(pin,smallfont));	 
						  tablepers.addCell(pe17);
						  PdfPCell pe18 = new PdfPCell(new Paragraph("Country",mediumfont));			  
						  tablepers.addCell(pe18);
						  PdfPCell pe19 = new PdfPCell(new Paragraph(coun,smallfont));	 
						  tablepers.addCell(pe19);
						  
						  PdfPCell pe20 = new PdfPCell(new Paragraph("Phone(R)",mediumfont));	 
						  tablepers.addCell(pe20);
						  PdfPCell pe21 = new PdfPCell(new Paragraph(joiningForm1.getTelephoneNumber(),smallfont));	 
						  tablepers.addCell(pe21);
						  PdfPCell pe22 = new PdfPCell(new Paragraph("Phone(M)",mediumfont));			  
						  tablepers.addCell(pe22);
						  PdfPCell pe23 = new PdfPCell(new Paragraph(joiningForm1.getMobileNumber(),smallfont));	 
						  tablepers.addCell(pe23);
						  
						  
						

						  PdfPCell pe24 = new PdfPCell(new Paragraph("E-mail",mediumfont));	 
						  tablepers.addCell(pe24);
						  PdfPCell pe25 = new PdfPCell(new Paragraph(joiningForm1.getEmailAddress(),smallfont));	 
						  tablepers.addCell(pe25);
						  PdfPCell pe30 = new PdfPCell(new Paragraph("Gender",mediumfont));			  
						  tablepers.addCell(pe30);
						  PdfPCell pe31 = new PdfPCell(new Paragraph(joiningForm1.getGender(),smallfont));	 
						  tablepers.addCell(pe31);
						  
						  PdfPCell pe28 = new PdfPCell(new Paragraph("Date of Birth",mediumfont));	 
						  tablepers.addCell(pe28);
						  PdfPCell pe29 = new PdfPCell(new Paragraph(joiningForm1.getDateofBirth(),smallfont));	 
						  tablepers.addCell(pe29);			
						  PdfPCell pe26 = new PdfPCell(new Paragraph("Age",mediumfont));			  
						  tablepers.addCell(pe26);
						  PdfPCell pe27 = new PdfPCell(new Paragraph(age,smallfont));	 
						  tablepers.addCell(pe27);
						  
						  
						  PdfPCell pe32 = new PdfPCell(new Paragraph("Nationality",mediumfont));	 
						  tablepers.addCell(pe32);
						  PdfPCell pe33 = new PdfPCell(new Paragraph(joiningForm1.getNationality(),smallfont));	 
						  tablepers.addCell(pe33);
						  PdfPCell pe34 = new PdfPCell(new Paragraph("Religion",mediumfont));			  
						  tablepers.addCell(pe34);
						  PdfPCell pe35 = new PdfPCell(new Paragraph(joiningForm1.getReligiousDenomination(),smallfont));	 
						  tablepers.addCell(pe35);
						  
						  PdfPCell pe36 = new PdfPCell(new Paragraph("Marital Status",mediumfont));	 
						  tablepers.addCell(pe36);
						  PdfPCell pe37 = new PdfPCell(new Paragraph(joiningForm1.getMaritalStatus(),smallfont));	 
						  tablepers.addCell(pe37);
						  PdfPCell pe38 = new PdfPCell(new Paragraph("Blood Group",mediumfont));			  
						  tablepers.addCell(pe38);
						  PdfPCell pe39 = new PdfPCell(new Paragraph(joiningForm1.getBloodGroup(),smallfont));	 
						  tablepers.addCell(pe39);
						
			     			document.add(tablepers);
			     			
			     			
			     		/*	///exper
		                      ArrayList exper=new ArrayList();
					    			
								
							
		                  	String sqlexper="select LANDX,Ind_Desc,* from join_emp_experience_details_Approve "
		    						+ "left outer join INDUSTRY on join_emp_experience_details_Approve.industry=industry.Id "
		    						+ " left outer join Country on join_emp_experience_details_Approve.ex_country=Country.land1  where sl_no='"+req+"'";
		    				ResultSet rsexp = ad.selectQuery(sqlexper);
									
									try {
										while (rsexp.next()) {
											
											JoiningReportForm joiningForm = new JoiningReportForm();
											 joiningForm.setNewid(rsexp.getString("id"));
											 joiningForm.setNameOfEmployer(rsexp.getString("name_of_employer"));
												joiningForm.setIndustry(rsexp.getString("Ind_Desc"));
												joiningForm.setExCity(rsexp.getString("ex_city"));
												joiningForm.setExcountry(rsexp.getString("LANDX"));
												joiningForm.setPositionHeld(rsexp.getString("position_held"));
												joiningForm.setJobRole(rsexp.getString("job_role"));
												joiningForm.setStartDate((EMicroUtils.display(rsexp.getDate("start_date"))));
												joiningForm.setEndDate((EMicroUtils.display(rsexp.getDate("end_date"))));
												joiningForm.setMicroExp(rsexp.getString("Miciroexp"));
												joiningForm.setMicroNo(rsexp.getString("Microno"));
												exper.add(joiningForm);
										}
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
					     			
					     			
									PdfPTable tableexp= new PdfPTable(6); // 3 columns.
									tableexp.setWidthPercentage(100); //Width 100%
									tableexp.setSpacingBefore(10f); //Space before table
									tableexp.setSpacingAfter(10f); //Space after table
							           //Set Column widths
							         float[]  columnWidthexp = {3f,3f,3f,3f,3f,3f};;
							         tableexp.setWidths(columnWidthexp);
					     			
					     			  PdfPCell experi = new PdfPCell(new Paragraph("Experience Details",columnheader));			  
					     			 experi.setColspan(7);
					     			tableexp.addCell(experi);
					     			
					  			    PdfPCell ex1 = new PdfPCell(new Paragraph("Company Name",mediumfont));
					  			  ex1.setRowspan(2);
					     		 	PdfPCell ex2 = new PdfPCell(new Paragraph("Industry",mediumfont));
					   			  ex2.setRowspan(2);
					   				PdfPCell ex3 = new PdfPCell(new Paragraph("Location",mediumfont));
					   			  ex3.setRowspan(2);
					   			    PdfPCell ex6 = new PdfPCell(new Paragraph("Period",mediumfont));
					   			 ex6.setHorizontalAlignment(Element.ALIGN_MIDDLE);
					   			ex6.setVerticalAlignment(Element.ALIGN_MIDDLE);
					   			  ex6.setColspan(2);							   				
					   				PdfPCell ex5 = new PdfPCell(new Paragraph("Position Held",mediumfont));
					   			  ex5.setRowspan(2);
					   			PdfPCell ex7 = new PdfPCell(new Paragraph("From",mediumfont));
					   			PdfPCell ex8 = new PdfPCell(new Paragraph("To",mediumfont));
					   				
					   				
					   				tableexp.addCell(ex1);
					   				tableexp.addCell(ex2);
					   				tableexp.addCell(ex3);   				
					   				tableexp.addCell(ex6);
					   				tableexp.addCell(ex5);
					   			
					   				tableexp.addCell(ex7);
					   				tableexp.addCell(ex8);
					   			   
					   			   
					   			   
					   			   
								    Iterator expe=exper.iterator();
							   		for(int k=0;k<exper.size();k++)
							   		{
							   			JoiningReportForm n=(JoiningReportForm)expe.next();
							   				
							   					PdfPCell fa1 = new PdfPCell(new Paragraph(n.getNameOfEmployer() ,smallfont));
								   				PdfPCell f2 = new PdfPCell(new Paragraph(n.getIndustry(),smallfont));
								   				PdfPCell p3 = new PdfPCell(new Paragraph(n.getExcountry(),smallfont));						   			
								   				PdfPCell p5 = new PdfPCell(new Paragraph(n.getStartDate(),smallfont));
								   				PdfPCell p6 = new PdfPCell(new Paragraph(n.getEndDate(),smallfont));
								   				PdfPCell p7 = new PdfPCell(new Paragraph(n.getPositionHeld(),smallfont));
								   			
							   					
								   				tableexp.addCell(fa1);
								   				tableexp.addCell(f2);
								   				tableexp.addCell(p3);						   	
								   				tableexp.addCell(p5);
								   				tableexp.addCell(p6);
								   				tableexp.addCell(p7);
							   		}
					     			
					     			document.add(tableexp);
			     			
			     			
			     			
			     			///education
                      ArrayList edu=new ArrayList();
			    			
							
					
							String sqledu="select EDUCATIONAL_LEVEL.Education_Level as edlevl,QUALIFICATION.qualification AS QUAL,BEZEI,LANDX,* from"
									+ " join_emp_education_details_approve left outer join EDUCATIONAL_LEVEL on "
									+ "join_emp_education_details_approve.education_level=EDUCATIONAL_LEVEL.Id  left outer join QUALIFICATION on "
									+ "join_emp_education_details_approve.qualification=QUALIFICATION.id left outer join country on "
									+ "join_emp_education_details_approve.e_country=country.LAND1  left outer join State on join_emp_education_details_approve.e_state=state.bland "
									+ " where sl_no='"+req+"' and join_emp_education_details_approve.e_country=state.land1";
							ResultSet rsedu = ad.selectQuery(sqledu);
							
							try {
								while (rsedu.next()) {
									
									JoiningReportForm joiningForm = new JoiningReportForm();
									 joiningForm.setNewid(rsedu.getString("id"));
									 joiningForm.setEducationalLevel(rsedu.getString("edlevl"));
										joiningForm.setQualification(rsedu.getString("QUAL"));
										joiningForm.setSpecialization(rsedu.getString("specialization"));
										joiningForm.setUniversityName(rsedu.getString("univarsity_name"));
										joiningForm.setUniverysityLocation(rsedu.getString("university_location"));
										joiningForm.setEdstate(rsedu.getString("BEZEI"));
										joiningForm.setEdcountry(rsedu.getString("LANDX"));
										joiningForm.setDurationofCourse(rsedu.getString("duration_of_course"));
										joiningForm.setTimes(rsedu.getString("time"));
										joiningForm.setYearofpassing(rsedu.getString("year_of_passing"));
										joiningForm.setFullTimePartTime(rsedu.getString("fulltime_parttime"));
										joiningForm.setPercentage(rsedu.getString("percentage"));
									   
									 edu.add(joiningForm);
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
			     			
			     			
							PdfPTable tableedu = new PdfPTable(5); // 3 columns.
							tableedu.setWidthPercentage(100); //Width 100%
							tableedu.setSpacingBefore(10f); //Space before table
							tableedu.setSpacingAfter(10f); //Space after table
					           //Set Column widths
					         float[]  columnWidthedu = {3f,3f,3f,3f,3f};;
					         tableedu.setWidths(columnWidthedu);
			     			
			     			  PdfPCell eduedu1 = new PdfPCell(new Paragraph("Education Details",columnheader));			  
			     			 eduedu1.setColspan(6);
			     			tableedu.addCell(eduedu1);
			     			
			  			    PdfPCell edu1 = new PdfPCell(new Paragraph("Course/Degree",mediumfont));	
			     		 	PdfPCell edu2 = new PdfPCell(new Paragraph("Specialization",mediumfont));
			   				PdfPCell edu3 = new PdfPCell(new Paragraph("University/Board",mediumfont));
			   				PdfPCell edu4 = new PdfPCell(new Paragraph("Year of Passing",mediumfont));			   			
			   				PdfPCell edu5 = new PdfPCell(new Paragraph("Class/%age",mediumfont));
			   				
			   				
			   				tableedu.addCell(edu1);
			   				tableedu.addCell(edu2);
			   				tableedu.addCell(edu3);			   				
			   				tableedu.addCell(edu4);
			   				tableedu.addCell(edu5);
			   			   
			   			   
			   			   
			   			   
						    Iterator educ=edu.iterator();
					   		for(int k=0;k<edu.size();k++)
					   		{
					   			JoiningReportForm n=(JoiningReportForm)educ.next();
					   				
					   					PdfPCell fa1 = new PdfPCell(new Paragraph(n.getQualification() ,smallfont));
						   				PdfPCell f2 = new PdfPCell(new Paragraph(n.getSpecialization(),smallfont));
						   				PdfPCell p3 = new PdfPCell(new Paragraph(n.getUniversityName(),smallfont));						   			
						   				PdfPCell p5 = new PdfPCell(new Paragraph(n.getYearofpassing(),smallfont));
						   				PdfPCell p6 = new PdfPCell(new Paragraph(n.getPercentage(),smallfont));
					   					
						   				tableedu.addCell(fa1);
						   				tableedu.addCell(f2);
						   				tableedu.addCell(p3);						   	
						   				tableedu.addCell(p5);
						   				tableedu.addCell(p6);
					   		}
			     			
			     			document.add(tableedu);*/
			     			
			     			
//family
			     			
			     			ArrayList fam=new ArrayList();
			    			
							
							String sql3="select  Relationship, * from join_emp_family_details_approve  left outer join RELATIONSHIP on join_emp_family_details_approve.family_dependent_type_id=RELATIONSHIP.Id where sl_no='"+req+"'";
							ResultSet rs11 = ad.selectQuery(sql3);
							try {
								while (rs11.next()) {
									
									JoiningReportForm joiningForm = new JoiningReportForm();
									 joiningForm.setNewid(rs11.getString("id"));
									     joiningForm.setFamilyDependentTypeID(rs11.getString("Relationship"));
									     joiningForm.setFtitle(rs11.getString("f_title"));
										 joiningForm.setFfirstName(rs11.getString("f_first_name"));
									     joiningForm.setFmiddleName(rs11.getString("f_middle_name"));
										 joiningForm.setFlastName(rs11.getString("f_last_name"));
										 joiningForm.setFinitials(rs11.getString("f_initials"));
										 joiningForm.setFgender(rs11.getString("f_gender"));
										  
										 joiningForm.setFdateofBirth((EMicroUtils.display(rs11.getDate("f_date_of_birth"))));
									 	 joiningForm.setFbirthplace(rs11.getString("f_birth_place"));
										 joiningForm.setFtelephoneNumber(rs11.getString("f_telephone_no"));
										 joiningForm.setFmobileNumber(rs11.getString("f_mobile_no"));
										 joiningForm.setFemailAddress(rs11.getString("f_email"));
										 joiningForm.setFbloodGroup(rs11.getString("f_blood_group"));
										 joiningForm.setFdependent(rs11.getString("dependent"));
										 joiningForm.setFemployeeofCompany(rs11.getString("employee_of_company"));
										 joiningForm.setFemployeeNumber(rs11.getString("employee_no_family"));
										 joiningForm.setFnominee(rs11.getString("fnominee"));
								        fam.add(joiningForm);
								}
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
			     			
			     			
							PdfPTable tablefami = new PdfPTable(5); // 3 columns.
							tablefami.setWidthPercentage(100); //Width 100%
							tablefami.setSpacingBefore(10f); //Space before table
							tablefami.setSpacingAfter(10f); //Space after table
					           //Set Column widths
					         float[]  columnWidth3s = {3f,3f,3f,3f,3f};;
					         tablefami.setWidths(columnWidth3s);
			     			
			     			  PdfPCell f1 = new PdfPCell(new Paragraph("Family Details",columnheader));			  
			     			 f1.setColspan(6);
			     			tablefami.addCell(f1);
			     			
			     		 	PdfPCell p11 = new PdfPCell(new Paragraph("Relation",mediumfont));
			   				PdfPCell p21 = new PdfPCell(new Paragraph("Name",mediumfont));
			   				PdfPCell p31 = new PdfPCell(new Paragraph("Date of Birth",mediumfont));			   			
			   				PdfPCell p51 = new PdfPCell(new Paragraph("Dependent on You",mediumfont));
			   				PdfPCell p61 = new PdfPCell(new Paragraph("Nominee",mediumfont));
			   				
			   				tablefami.addCell(p11);
			   				tablefami.addCell(p21);
			   				tablefami.addCell(p31);			   				
			   				tablefami.addCell(p51);
			   				tablefami.addCell(p61);
			   			   
			   			   
			   			   
			   			   
						    Iterator frc=fam.iterator();
					   		for(int k=0;k<fam.size();k++)
					   		{
					   			JoiningReportForm n=(JoiningReportForm)frc.next();
					   				
					   					PdfPCell fa1 = new PdfPCell(new Paragraph(n.getFamilyDependentTypeID() ,smallfont));
						   				PdfPCell f2 = new PdfPCell(new Paragraph(n.getFfirstName()+" "+n.getFmiddleName()+" "+n.getFlastName(),smallfont));
						   				PdfPCell p3 = new PdfPCell(new Paragraph(n.getFdateofBirth(),smallfont));						   			
						   				PdfPCell p5 = new PdfPCell(new Paragraph(n.getFdependent(),smallfont));
						   				PdfPCell p6 = new PdfPCell(new Paragraph(n.getFnominee(),smallfont));
					   					
						   				tablefami.addCell(fa1);
						   				tablefami.addCell(f2);
						   				tablefami.addCell(p3);						   	
						   				tablefami.addCell(p5);
						   				tablefami.addCell(p6);
					   		}
			     			
			     			document.add(tablefami);
			     			
			     			/*///language
		                      ArrayList lang=new ArrayList();
					    			
								
							
		                  	String sqllang="  select LANGUAGE.language,* from join_emp_language_details_Approve  "
		        					+ " left outer join LANGUAGE on join_emp_language_details_Approve.language=LANGUAGE.Id where sl_no='"+req+"'";
		        			ResultSet rslang = ad.selectQuery(sqllang);
									
									try {
										while (rslang.next()) {
											
											JoiningReportForm joiningForm = new JoiningReportForm();
											 joiningForm.setNewid(rslang.getString("id"));
											 joiningForm.setLanguage(rslang.getString("language"));
												joiningForm.setMotherTongue(rslang.getString("mother_tongue"));	
												
											    joiningForm.setLangSpeaking(rslang.getString("spoken"));
											    joiningForm.setLangRead(rslang.getString("reading"));
												joiningForm.setLangWrite(rslang.getString("writing"));
												
												
												if(joiningForm.getLangRead().equalsIgnoreCase("1"))
													joiningForm.setLangRead("N/A");
												if(joiningForm.getLangRead().equalsIgnoreCase("2"))
													joiningForm.setLangRead("Low");
												if(joiningForm.getLangRead().equalsIgnoreCase("3"))
													joiningForm.setLangRead("Medium");
												if(joiningForm.getLangRead().equalsIgnoreCase("4"))
													joiningForm.setLangRead("High");
												
												
												if(joiningForm.getLangSpeaking().equalsIgnoreCase("1"))
													joiningForm.setLangSpeaking("N/A");
												if(joiningForm.getLangSpeaking().equalsIgnoreCase("2"))
													joiningForm.setLangSpeaking("Low");
												if(joiningForm.getLangSpeaking().equalsIgnoreCase("3"))
													joiningForm.setLangSpeaking("Medium");
												if(joiningForm.getLangSpeaking().equalsIgnoreCase("4"))
													joiningForm.setLangSpeaking("High");
												
												if(joiningForm.getLangWrite().equalsIgnoreCase("1"))
													joiningForm.setLangWrite("N/A");
												if(joiningForm.getLangWrite().equalsIgnoreCase("2"))
													joiningForm.setLangWrite("Low");
												if(joiningForm.getLangWrite().equalsIgnoreCase("3"))
													joiningForm.setLangWrite("Medium");
												if(joiningForm.getLangWrite().equalsIgnoreCase("4"))
													joiningForm.setLangWrite("High");
											
											   
												lang.add(joiningForm);
										}
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
					     			
					     			
									PdfPTable tablelang = new PdfPTable(5); // 3 columns.
									tablelang.setWidthPercentage(100); //Width 100%
									tablelang.setSpacingBefore(10f); //Space before table
									tablelang.setSpacingAfter(10f); //Space after table
							           //Set Column widths
							         float[]  columnWidthla = {3f,3f,3f,3f,3f};;
							         tablelang.setWidths(columnWidthla);
					     			
					     			  PdfPCell langu = new PdfPCell(new Paragraph("Languages Details",columnheader));			  
					     			 langu.setColspan(6);
					     			tablelang.addCell(langu);
					     			
					  			    PdfPCell la1 = new PdfPCell(new Paragraph("Language",mediumfont));	
					     		 	PdfPCell la2 = new PdfPCell(new Paragraph("Mother Tongue",mediumfont));
					   				PdfPCell la3 = new PdfPCell(new Paragraph("Read",mediumfont));
					   				PdfPCell la4 = new PdfPCell(new Paragraph("Write",mediumfont));			   			
					   				PdfPCell la5 = new PdfPCell(new Paragraph("Speak",mediumfont));
					   				
					   				
					   				tablelang.addCell(la1);
					   				tablelang.addCell(la2);
					   				tablelang.addCell(la3);			   				
					   				tablelang.addCell(la4);
					   				tablelang.addCell(la5);
					   			   
					   			   
					   			   
					   			   
								    Iterator langw=lang.iterator();
							   		for(int k=0;k<lang.size();k++)
							   		{
							   			JoiningReportForm n=(JoiningReportForm)langw.next();
							   				
							   					PdfPCell fa1 = new PdfPCell(new Paragraph(n.getLanguage() ,smallfont));
								   				PdfPCell f2 = new PdfPCell(new Paragraph(n.getMotherTongue(),smallfont));
								   				PdfPCell p3 = new PdfPCell(new Paragraph(n.getLangRead(),smallfont));						   			
								   				PdfPCell p5 = new PdfPCell(new Paragraph(n.getLangWrite(),smallfont));
								   				PdfPCell p6 = new PdfPCell(new Paragraph(n.getLangSpeaking(),smallfont));
							   					
								   				tablelang.addCell(fa1);
								   				tablelang.addCell(f2);
								   				tablelang.addCell(p3);						   	
								   				tablelang.addCell(p5);
								   				tablelang.addCell(p6);
							   		}
					     			
					     			document.add(tablelang);
			     			*/
					     			
					     			
					     			
					     			
			     					
			  document.close();
		      writer.close();
		     
		 

			
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			joiningForm1.setFileFullPath("/Joining Report/"+empfullnm+"_Joining_Report.pdf");
		joiningForm1.setReqStatus("pdf");
		

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//get current date time with Date()
         Date date=new Date();
		System.out.println(dateFormat.format(date));
	/*	String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
		ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
		while (rsEmpPhoto.next())
		{
			joiningForm1.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
		request.setAttribute("employeePhoto", "employeePhoto");	
		}*/
	/*	String frst="select EMP_FULLNAME,SEX,DOB from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
		
		ResultSet rs = ad.selectQuery(frst);
		while(rs.next())
		{
			JoiningReportForm	joiningForm = new JoiningReportForm();
			String dob=rs.getString("DOB");
			if(dob!=null ||!(dob.equalsIgnoreCase("")))
			joiningForm.setDateofBirth((EMicroUtils.display(rs.getDate("DOB"))));
			joiningForm.setFirstName(rs.getString("EMP_FULLNAME"));
			String gender= rs.getString("SEX");
			if(gender.equalsIgnoreCase("M"))
			{
				joiningForm.setGender("Male");
				
			}
			else{
				joiningForm.setGender("Female");
			}
		}*/
		int totalRecords = 0;
		  int  startRecord=0;
		  int  endRecord=0;
		  
		  String g3t="select count(*) as c from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"'";
		  ResultSet f=ad.selectQuery(g3t);
		  try {
			if(f.next())
			  {
				  totalRecords=f.getInt("c");
			  }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			 if(totalRecords>10)
			 {
				 joiningForm1.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 joiningForm1.setStartRecord(1);
			 joiningForm1.setEndRecord(10);
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  joiningForm1.setTotalRecords(totalRecords);
				  joiningForm1.setStartRecord(1);
				  joiningForm1.setEndRecord(totalRecords); 
			 }
			 
			 if(totalRecords>0)
			 {
			 
		ArrayList t=new ArrayList();
		String u="select * From (SELECT ROW_NUMBER() OVER(ORDER BY id desc) AS  RowNum,id,Emp_fullname,DOB,email_address,mobile_no from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"') as  sub Where  sub.RowNum between 1 and 10 order by id desc";
		ResultSet yp=ad.selectQuery(u);
		try {
			while(yp.next())
			{
				JoiningReportForm j=new JoiningReportForm();
				j.setNewid(yp.getString("id"));
				j.setFirstName(yp.getString("Emp_fullname"));
				j.setDateofBirth(yp.getString("DOB"));
				j.setEmailAddress(yp.getString("email_address"));
				j.setMobileNumber(yp.getString("mobile_no"));
				t.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(t.size()>0)			
		request.setAttribute("joinlist", t);
			 }
			 joiningForm1.setSearchEmployee("");
	
		
		
		
		return mapping.findForward("displayjoinlist");
	}
	
	public ActionForward displayTables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm1 = (JoiningReportForm) form;// TODO Auto-generated method stub
		HttpSession session=request.getSession();	
		
		
		
		String parameter=request.getParameter("param");
		String reqid=request.getParameter("id");
		UserInfo userId=(UserInfo)session.getAttribute("user");
		userId.getId();
		
		joiningForm1.setMessage("");
		joiningForm1.setMessage1("");
		joiningForm1.setReqStatus("");
		
		try
		{
			if(parameter.equalsIgnoreCase("personalDetails"))
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//get current date time with Date()
		         Date date=new Date();
				System.out.println(dateFormat.format(date));
			/*	String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
				ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
				while (rsEmpPhoto.next())
				{
					joiningForm1.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
				request.setAttribute("employeePhoto", "employeePhoto");	
				}*/
			/*	String frst="select EMP_FULLNAME,SEX,DOB from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
				
				ResultSet rs = ad.selectQuery(frst);
				while(rs.next())
				{
					JoiningReportForm	joiningForm = new JoiningReportForm();
					String dob=rs.getString("DOB");
					if(dob!=null ||!(dob.equalsIgnoreCase("")))
					joiningForm.setDateofBirth((EMicroUtils.display(rs.getDate("DOB"))));
					joiningForm.setFirstName(rs.getString("EMP_FULLNAME"));
					String gender= rs.getString("SEX");
					if(gender.equalsIgnoreCase("M"))
					{
						joiningForm.setGender("Male");
						
					}
					else{
						joiningForm.setGender("Female");
					}
				}*/
				String sql3="select * from join_emp_personal_info_approve where id='"+reqid+"'";
				System.out.println("*********SQL join_emp_personal_info joing table"+sql3);
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					JoiningReportForm	joiningForm = new JoiningReportForm();
					joiningForm.setTitle(rs12.getString("title"));
					
					joiningForm.setDateofBirth(rs12.getString("DOB"));
					joiningForm.setFirstName(rs12.getString("EMP_FULLNAME"));					
					joiningForm.setGender(rs12.getString("SEX"));
					
					joiningForm.setPanno(rs12.getString("panno"));
					joiningForm.setUanno(rs12.getString("uanno"));
					joiningForm.setAdharno(rs12.getString("adharno"));
				
					
					joiningForm.setNickName(rs12.getString("nick_name"));
				    joiningForm.setTitle(rs12.getString("title"));
					joiningForm.setMaritalStatus(rs12.getString("marital_status"));
					
					joiningForm.setBirthplace(rs12.getString("birth_place"));
					joiningForm.setCountryofBirth(rs12.getString("country_of_birth"));
					joiningForm.setCaste(rs12.getString("caste"));
					joiningForm.setReligiousDenomination(rs12.getString("religous_denomination"));
					joiningForm.setNationality(rs12.getString("nationality"));
					joiningForm.setTelephoneNumber(rs12.getString("telephone_no"));
					joiningForm.setMobileNumber(rs12.getString("mobile_no"));
					joiningForm.setFaxNumber(rs12.getString("fax_no"));
					joiningForm.setEmailAddress(rs12.getString("email_address"));
					joiningForm.setBloodGroup(rs12.getString("blood_group"));
					joiningForm.setPermanentAccountNumber(rs12.getString("permanent_acno"));
					joiningForm.setPassportNumber(rs12.getString("passport_no"));
					joiningForm.setPlaceofIssueofPassport(rs12.getString("place_of_issue_of_passport"));
					joiningForm.setDateofissueofPassport(rs12.getString("date_of_issue_of_passp"));
					joiningForm.setDateofexpiryofPassport(rs12.getString("date_of_expiry_of_passport"));
					joiningForm.setPersonalIdentificationMarks(rs12.getString("personal_identification_mark"));
					joiningForm.setPhysicallyChallenged(rs12.getString("physiaclly_challenged"));
					joiningForm.setPhysicallyChallengeddetails(rs12.getString("physically_challenged_details"));
					joiningForm.setEmergencyContactPersonName(rs12.getString("emergency_contact_person_name"));
					joiningForm.setEmergencyContactAddressLine1(rs12.getString("emegency_contact_addressline1"));
					joiningForm.setEmergencyContactAddressLine2(rs12.getString("emegency_contact_addressline2"));
					joiningForm.setEmergCntAdd11(rs12.getString("emegency_contact_addressline3"));
					joiningForm.setEmergCntAdd111(rs12.getString("emegency_contact_addressline4"));
					joiningForm.setEmergCntAdd22(rs12.getString("emergCntAdd22"));
					joiningForm.setEmergCntAdd222(rs12.getString("emergCntAdd222"));			
						joiningForm.setEmergencyMobileNumber(rs12.getString("emegency_mobile_number"));
					joiningForm.setEmergencyTelephoneNumber(rs12.getString("emegency_telephone_number"));
					joiningForm.setNoOfChildrens(rs12.getString("number_of_childrens"));
					joiningForm.setWebsite(rs12.getString("website"));
					/*String saveStatus=rs12.getString("Data_Status");
					if(saveStatus.equalsIgnoreCase("Approved")){
						request.setAttribute("blockEmployeeName", "blockEmployeeName");
						String bloodGroup=rs12.getString("blood_group");
						if(!(bloodGroup.equalsIgnoreCase("")))
						{
							request.setAttribute("blockBloodGroup", "blockBloodGroup");
						}
					}*/
					joiningForm.setEmergCity1(rs12.getString("emerg_city1"));
					joiningForm.setEmergCity2(rs12.getString("emerg_city2"));
					joiningForm.setEmergState1(rs12.getString("emerg_state1"));
					joiningForm.setEmergState2(rs12.getString("emerg_state2"));
					
					joiningForm.setSalaryCurrency(rs12.getString("WAERS"));
					joiningForm.setPaymentMethod(rs12.getString("PAYMENT_METHOD"));	
					joiningForm.setBranchName(rs12.getString("BRANCH"));
					joiningForm.setBankName(rs12.getString("BANKID"));
					joiningForm.setAccountType(rs12.getString("BACCTYP"));
					joiningForm.setAccountNumber(rs12.getString("BACCNO"));			
					joiningForm.setIfsCCode(rs12.getString("IFSC_CODE"));
					joiningForm.setMicrCode(rs12.getString("MICR_CODE"));
					
					
				}
				
				
			}
			else if(parameter.equalsIgnoreCase("addressDetails"))
			{
			request.setAttribute("addressAdd", "addressAdd");
			joiningForm1.setAddressStatus("Save");
				int countEmpAddress=0;
				String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where Sl_No='"+reqid+"' ";
				ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				if(countEmpAddress>0)
				{
		ArrayList list = new ArrayList();
		String sql3="select * from join_emp_address_approve where Sl_No='"+reqid+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			JoiningReportForm	joiningForm = new JoiningReportForm();
			joiningForm.setNewid(rs11.getString("id"));
			    joiningForm.setAddressType(rs11.getString("address_type"));
			    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
				joiningForm.setHouseNumber(rs11.getString("house_no"));
				joiningForm.setAddressLine1(rs11.getString("address_line1"));
				joiningForm.setAddressLine2(rs11.getString("address_line2"));
				joiningForm.setAddressLine3(rs11.getString("address_line3"));
				joiningForm.setPostalCode(rs11.getString("postal_code"));
				joiningForm.setCity(rs11.getString("a_city"));
				joiningForm.setState(rs11.getString("a_state"));
				joiningForm.setCountry(rs11.getString("a_country"));
				joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
				joiningForm.setCompanyHousing(rs11.getString("company_housing"));
				joiningForm.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
				joiningForm.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
			    list.add(joiningForm);
		}
		request.setAttribute("listName", list);
				}
		
		
			}
			else if(parameter.equalsIgnoreCase("familyDetails"))
			{
				request.setAttribute("addFamily", "addFamily");
				
				ResultSet rs9 = ad.selectQuery("select * from RELATIONSHIP  ");
				ArrayList relationIDList=new ArrayList();
				ArrayList relationValueList=new ArrayList();
				
				while(rs9.next()) {
					relationIDList.add(rs9.getString("Id"));
					relationValueList.add(rs9.getString("RELATIONSHIP"));
				}
				joiningForm1.setRelationIDList(relationIDList);
				joiningForm1.setRelationValueList(relationValueList);
				
				
				
				
				
				ArrayList list = new ArrayList();
				String sql3="select * from join_emp_family_details_approve where Sl_No='"+reqid+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					
					JoiningReportForm joiningForm = new JoiningReportForm();
					joiningForm.setNewid(rs11.getString("id"));
					     joiningForm.setFamilyDependentTypeID(rs11.getString("family_dependent_type_id"));
					     joiningForm.setFtitle(rs11.getString("f_title"));
						 joiningForm.setFfirstName(rs11.getString("f_first_name"));
					     joiningForm.setFmiddleName(rs11.getString("f_middle_name"));
						 joiningForm.setFlastName(rs11.getString("f_last_name"));
						 joiningForm.setFinitials(rs11.getString("f_initials"));
						 joiningForm.setFgender(rs11.getString("f_gender"));
						  
						 joiningForm.setFdateofBirth((EMicroUtils.display(rs11.getDate("f_date_of_birth"))));
					 	 joiningForm.setFbirthplace(rs11.getString("f_birth_place"));
						 joiningForm.setFtelephoneNumber(rs11.getString("f_telephone_no"));
						 joiningForm.setFmobileNumber(rs11.getString("f_mobile_no"));
						 joiningForm.setFemailAddress(rs11.getString("f_email"));
						 joiningForm.setFbloodGroup(rs11.getString("f_blood_group"));
						 joiningForm.setFdependent(rs11.getString("dependent"));
						 joiningForm.setFemployeeofCompany(rs11.getString("employee_of_company"));
						 joiningForm.setFemployeeNumber(rs11.getString("employee_no_family"));
						 joiningForm.setFnominee(rs11.getString("fnominee"));
					    list.add(joiningForm);
				}
				clearFamilyDetails(mapping, form, request, response);
				request.setAttribute("listName", list);
			}
			else if(parameter.equalsIgnoreCase("educationDetails"))
			{
			   clearEducationDetails(mapping, form, request, response);
				request.setAttribute("addEducation", "addEducation");
				joiningForm1.setEducationStatus("Save");
				ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
				ArrayList countryList=new ArrayList();
				ArrayList countryLabelList=new ArrayList();
				
				while(rs9.next()) {
					countryList.add(rs9.getString("LAND1"));
					countryLabelList.add(rs9.getString("LANDX"));
				}
				joiningForm1.setCountryList(countryList);
				joiningForm1.setCountryLabelList(countryLabelList);
				ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL order by Education_Level ");
				ArrayList eduIDList=new ArrayList();
				ArrayList eduValueList=new ArrayList();
				
				while(rsEdu.next()) {
					eduIDList.add(rsEdu.getString("Id"));
					eduValueList.add(rsEdu.getString("Education_Level"));
				}
				joiningForm1.setEduIDList(eduIDList);
				joiningForm1.setEduValueList(eduValueList);
				
				ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION order by Qualification");
				ArrayList qulificationID=new ArrayList();
				ArrayList qulificationValueList=new ArrayList();
				
				while(rsQulif.next()) {
					qulificationID.add(rsQulif.getString("Id"));
					qulificationValueList.add(rsQulif.getString("Qualification"));
				}
				joiningForm1.setQulificationID(qulificationID);
				joiningForm1.setQulificationValueList(qulificationValueList);
				
				ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY order by Ind_Desc");
				ArrayList industyID=new ArrayList();
				ArrayList industyValueList=new ArrayList();
				
				while(rsIndustry.next()) {
					industyID.add(rsIndustry.getString("Id"));
					industyValueList.add(rsIndustry.getString("Ind_Desc"));
				}
				joiningForm1.setIndustyID(industyID);
				joiningForm1.setIndustyValueList(industyValueList);
				
				
				ArrayList list = new ArrayList();
				String sql3="select *  from join_emp_education_details_approve " +
						" where Sl_No='"+reqid+"' ";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					JoiningReportForm joiningForm = new JoiningReportForm();
					joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setEducationalLevel(rs11.getString("education_level"));
					joiningForm.setQualification(rs11.getString("qualification"));
					joiningForm.setSpecialization(rs11.getString("specialization"));
					joiningForm.setUniversityName(rs11.getString("univarsity_name"));
					joiningForm.setUniverysityLocation(rs11.getString("university_location"));
					joiningForm.setEdstate(rs11.getString("e_state"));
					joiningForm.setEdcountry(rs11.getString("e_country"));
					joiningForm.setDurationofCourse(rs11.getString("duration_of_course"));
					joiningForm.setTimes(rs11.getString("time"));
					joiningForm.setYearofpassing(rs11.getString("year_of_passing"));
					joiningForm.setFullTimePartTime(rs11.getString("fulltime_parttime"));
					joiningForm.setPercentage(rs11.getString("percentage"));
					String fileName="";
					String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
					ResultSet rs12 = ad.selectQuery(doc);
					while(rs12.next()){
					fileName=rs12.getString("file_name");
					joiningForm.setEmpEduDoc(rs12.getString("file_name"));
				
					request.setAttribute("edudoc", "edudoc");
					}
					if(fileName.equalsIgnoreCase(""))
					{
						joiningForm.setEmpEduDoc("");
					}
					    list.add(joiningForm);
				}
				request.setAttribute("listName", list);
			}
			else if(parameter.equalsIgnoreCase("experienceDetails"))
			{
				joiningForm1.setMicroExp("");
				request.setAttribute("addExperience", "addExperience");
				ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
				ArrayList countryList=new ArrayList();
				ArrayList countryLabelList=new ArrayList();
				
				while(rs9.next()) {
					countryList.add(rs9.getString("LAND1"));
					countryLabelList.add(rs9.getString("LANDX"));
				}
				joiningForm1.setCountryList(countryList);
				joiningForm1.setCountryLabelList(countryLabelList);
				
				ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY order by Ind_Desc");
				ArrayList industyID=new ArrayList();
				ArrayList industyValueList=new ArrayList();
				
				while(rsIndustry.next()) {
					industyID.add(rsIndustry.getString("Id"));
					industyValueList.add(rsIndustry.getString("Ind_Desc"));
				}
				joiningForm1.setIndustyID(industyID);
				joiningForm1.setIndustyValueList(industyValueList);
				
				ArrayList list = new ArrayList();
				String sql3="select * from join_emp_experience_details_approve where Sl_No='"+reqid+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {					
					
					JoiningReportForm	joiningForm = new JoiningReportForm();
					joiningForm.setNewid(rs11.getString("id"));
					joiningForm.setNameOfEmployer(rs11.getString("name_of_employer"));
					joiningForm.setIndustry(rs11.getString("industry"));
					joiningForm.setExCity(rs11.getString("ex_city"));
					joiningForm.setExcountry(rs11.getString("ex_country"));
					joiningForm.setPositionHeld(rs11.getString("position_held"));
					joiningForm.setJobRole(rs11.getString("job_role"));
					joiningForm.setStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
					joiningForm.setEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
					    list.add(joiningForm);
				}
				request.setAttribute("listName", list);
			}
			
			else if(parameter.equalsIgnoreCase("languageDetails"))
			{
				request.setAttribute("addLanguage", "addLanguage");
				ResultSet rsLang = ad.selectQuery("select * from LANGUAGE order by Language");
				ArrayList langID=new ArrayList();
				ArrayList langValueList=new ArrayList();
				try{
				while(rsLang.next()) {
					langID.add(rsLang.getString("Id"));
					langValueList.add(rsLang.getString("Language"));
				}
				joiningForm1.setLanguageID(langID);
				joiningForm1.setLanguageValueList(langValueList);
				}catch (Exception e) {
				e.printStackTrace();
				}
				
				ArrayList list = new ArrayList();
			String sql3="select * from join_emp_language_details_approve where Sl_No='"+reqid+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				JoiningReportForm joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
				joiningForm.setLanguage(rs11.getString("language"));
				joiningForm.setMotherTongue(rs11.getString("mother_tongue"));	
			    joiningForm.setLangSpeaking(rs11.getString("spoken"));
			    joiningForm.setLangRead(rs11.getString("reading"));
				joiningForm.setLangWrite(rs11.getString("writing"));
				joiningForm.setLangstartDate((EMicroUtils.display(rs11.getDate("l_start_date"))));
				joiningForm.setLangendDate((EMicroUtils.display(rs11.getDate("l_end_date"))));
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			clearLanguageDetails(mapping, form, request, response);
				
			}
			else
			{
				System.out.println("ELSE");
			}
			
			
			
		
		
		request.setAttribute(parameter, parameter);
		
		
		
	
	
		
	
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("display1");
	}
	
	
	public ActionForward serchEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		joiningForm.setReqStatus("");
		
		//delete temp data
		
		String abc="Select * from join_emp_personal_info_Approve where data_status!='UPDATED' and user_id='"+userId.getEmployeeNo()+"'";
		ResultSet b=ad.selectQuery(abc);
		try {
			while(b.next())
			{
				
				//address
				String add="delete join_emp_address_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh=ad.SqlExecuteUpdate(add);
				//famile
				String add1="delete join_emp_family_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh1=ad.SqlExecuteUpdate(add1);
				//education
				String add2="delete join_emp_education_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh2=ad.SqlExecuteUpdate(add2);
				//education doc
				String add2doc="delete join_emp_education_documents where Sl_No='"+b.getString("id")+"' ";
				int gh2doc=ad.SqlExecuteUpdate(add2doc);
				//exper
				String add3="delete join_emp_experience_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh3=ad.SqlExecuteUpdate(add3);
				//lan
				String add4="delete join_emp_language_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh4=ad.SqlExecuteUpdate(add4);
				
				
				//pernr
				String per4="delete join_emp_personal_info_Approve where id='"+b.getString("id")+"' ";
				int ere=ad.SqlExecuteUpdate(per4);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String keyword=joiningForm.getSearchEmployee();
		
		ArrayList t=new ArrayList();
		String u="select top 50 * from join_emp_personal_info_Approve where Emp_fullname  like'%"+keyword+"%' or id like'%"+keyword+"%' order by id desc";
		ResultSet yp=ad.selectQuery(u);
		try {
			while(yp.next())
			{
				JoiningReportForm j=new JoiningReportForm();
				j.setNewid(yp.getString("id"));
				j.setFirstName(yp.getString("Emp_fullname"));
				j.setDateofBirth(yp.getString("DOB"));
				j.setEmailAddress(yp.getString("email_address"));
				j.setMobileNumber(yp.getString("mobile_no"));
				t.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(t.size()>0)
		request.setAttribute("joinlist", t);
		return mapping.findForward("displayjoinlist");
		
	
	}
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		joiningForm.setReqStatus("");
		int totalRecords=joiningForm.getTotalRecords();//21
		int startRecord=joiningForm.getStartRecord();//11
		int endRecord=joiningForm.getEndRecord();	
		
		
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
			
			ArrayList t=new ArrayList();
			String u="select * From (SELECT ROW_NUMBER() OVER(ORDER BY id desc) AS  RowNum,id,Emp_fullname,DOB,"
					+ "email_address,mobile_no from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"') as  sub Where "
							+ " sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by id desc";
			ResultSet yp=ad.selectQuery(u);
			try {
				while(yp.next())
				{
					JoiningReportForm j=new JoiningReportForm();
					j.setNewid(yp.getString("id"));
					j.setFirstName(yp.getString("Emp_fullname"));
					j.setDateofBirth(yp.getString("DOB"));
					j.setEmailAddress(yp.getString("email_address"));
					j.setMobileNumber(yp.getString("mobile_no"));
					t.add(j);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(t.size()>0)			
			request.setAttribute("joinlist", t);
			
			if(t.size()!=0)
			{
				joiningForm.setTotalRecords(totalRecords);
				joiningForm.setStartRecord(startRecord);
				joiningForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				joiningForm.setTotalRecords(totalRecords);
				joiningForm.setStartRecord(start);
				joiningForm.setEndRecord(end);
				
			}
		 if(t.size()<10)
		 {
			 joiningForm.setTotalRecords(totalRecords);
			 joiningForm.setStartRecord(startRecord);
			 joiningForm.setEndRecord(startRecord+t.size()-1);
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton"); 
			 
		 }
		 if (endRecord == totalRecords) {
				request.setAttribute("nextButton", "");
				request.setAttribute("disableNextButton",
						"disableNextButton");
			}
			request.setAttribute("displayRecordNo",
					"displayRecordNo");
			
		}
		
		joiningForm.setSearchEmployee("");
		return mapping.findForward("displayjoinlist");
	}
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		joiningForm.setReqStatus("");
		int totalRecords=joiningForm.getTotalRecords();//21
		int endRecord=joiningForm.getStartRecord()-1;//20
		int startRecord=joiningForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		joiningForm.setTotalRecords(totalRecords);
		joiningForm.setStartRecord(startRecord);
		joiningForm.setEndRecord(endRecord);
		
		
		
			
			ArrayList t=new ArrayList();
			String u="select * From (SELECT ROW_NUMBER() OVER(ORDER BY id desc) AS  RowNum,id,Emp_fullname,DOB,"
					+ "email_address,mobile_no from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"') as  sub Where "
							+ " sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by id desc";
			ResultSet yp=ad.selectQuery(u);
			try {
				while(yp.next())
				{
					JoiningReportForm j=new JoiningReportForm();
					j.setNewid(yp.getString("id"));
					j.setFirstName(yp.getString("Emp_fullname"));
					j.setDateofBirth(yp.getString("DOB"));
					j.setEmailAddress(yp.getString("email_address"));
					j.setMobileNumber(yp.getString("mobile_no"));
					t.add(j);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(t.size()>0)			
			request.setAttribute("joinlist", t);
			
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(t.size()<10)
			{
				joiningForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
		
			
			request.setAttribute("displayRecordNo",
					"displayRecordNo");
		joiningForm.setSearchEmployee("");
		return mapping.findForward("displayjoinlist");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		joiningForm.setReqStatus("");
		int totalRecords=joiningForm.getTotalRecords();//21
		int startRecord=joiningForm.getStartRecord();//11
		int endRecord=joiningForm.getEndRecord();	
		
		 startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 joiningForm.setTotalRecords(totalRecords);
		 joiningForm.setStartRecord(startRecord);
		 joiningForm.setEndRecord(totalRecords);
		
		
		
			
			ArrayList t=new ArrayList();
			String u="select * From (SELECT ROW_NUMBER() OVER(ORDER BY id desc) AS  RowNum,id,Emp_fullname,DOB,"
					+ "email_address,mobile_no from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"') as  sub Where "
							+ " sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by id desc";
			ResultSet yp=ad.selectQuery(u);
			try {
				while(yp.next())
				{
					JoiningReportForm j=new JoiningReportForm();
					j.setNewid(yp.getString("id"));
					j.setFirstName(yp.getString("Emp_fullname"));
					j.setDateofBirth(yp.getString("DOB"));
					j.setEmailAddress(yp.getString("email_address"));
					j.setMobileNumber(yp.getString("mobile_no"));
					t.add(j);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			if(t.size()>0)			
			request.setAttribute("joinlist", t);
			
			request.setAttribute("disableNextButton",
					"disableNextButton");
			request.setAttribute("previousButton", "previousButton");
			if (t.size() < 10) {

				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("disablePreviousButton",
						"disablePreviousButton");
			}
			request.setAttribute("displayRecordNo", "displayRecordNo");
	 		
		joiningForm.setSearchEmployee("");
		return mapping.findForward("displayjoinlist");
	}
	
	public ActionForward displayJOINList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
		joiningForm.setReqStatus("");
		
		//delete temp data
		
		String abc="Select * from join_emp_personal_info_Approve where data_status!='UPDATED'  and user_id='"+userId.getEmployeeNo()+"'";
		ResultSet b=ad.selectQuery(abc);
		try {
			while(b.next())
			{
				
				//address
				String add="delete join_emp_address_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh=ad.SqlExecuteUpdate(add);
				//famile
				String add1="delete join_emp_family_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh1=ad.SqlExecuteUpdate(add1);
				//education
				String add2="delete join_emp_education_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh2=ad.SqlExecuteUpdate(add2);
				//education doc
				String add2doc="delete join_emp_education_documents where Sl_No='"+b.getString("id")+"' ";
				int gh2doc=ad.SqlExecuteUpdate(add2doc);
				//exper
				String add3="delete join_emp_experience_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh3=ad.SqlExecuteUpdate(add3);
				//lan
				String add4="delete join_emp_language_details_Approve where Sl_No='"+b.getString("id")+"' ";
				int gh4=ad.SqlExecuteUpdate(add4);
				
				
				//pernr
				String per4="delete join_emp_personal_info_Approve where id='"+b.getString("id")+"' ";
				int ere=ad.SqlExecuteUpdate(per4);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int totalRecords = 0;
		  int  startRecord=0;
		  int  endRecord=0;
		  
		  String g3t="select count(*) as c from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"'";
		  ResultSet f=ad.selectQuery(g3t);
		  try {
			if(f.next())
			  {
				  totalRecords=f.getInt("c");
			  }
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
			 if(totalRecords>10)
			 {
				 joiningForm.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 joiningForm.setStartRecord(1);
			 joiningForm.setEndRecord(10);
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  joiningForm.setTotalRecords(totalRecords);
				  joiningForm.setStartRecord(1);
				  joiningForm.setEndRecord(totalRecords); 
			 }
			 
			 if(totalRecords>0)
			 {
			 
		ArrayList t=new ArrayList();
		String u="select * From (SELECT ROW_NUMBER() OVER(ORDER BY id desc) AS  RowNum,id,Emp_fullname,DOB,email_address,mobile_no from join_emp_personal_info_Approve where user_id='"+userId.getEmployeeNo()+"') as  sub Where  sub.RowNum between 1 and 10 order by id desc";
		ResultSet yp=ad.selectQuery(u);
		try {
			while(yp.next())
			{
				JoiningReportForm j=new JoiningReportForm();
				j.setNewid(yp.getString("id"));
				j.setFirstName(yp.getString("Emp_fullname"));
				j.setDateofBirth(yp.getString("DOB"));
				j.setEmailAddress(yp.getString("email_address"));
				j.setMobileNumber(yp.getString("mobile_no"));
				t.add(j);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(t.size()>0)			
		request.setAttribute("joinlist", t);
			 }
		joiningForm.setSearchEmployee("");
		return mapping.findForward("displayjoinlist");
		
	}
	
	public ActionForward addnewUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm=(JoiningReportForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		joiningForm.setReqStatus("");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//get current date time with Date()
         Date date=new Date();
		System.out.println(dateFormat.format(date));
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Date="+dateFormat.format(cal.getTime()));
		String currentDate=dateFormat.format(cal.getTime());
		synchronized (this) {

			
			joiningForm.setMobileNumber("");
			joiningForm.setEmailAddress("");
			 joiningForm.setTitle("");
			 
			 joiningForm.setDateofBirth("");
				joiningForm.setFirstName("");					
				joiningForm.setGender("");
				
				joiningForm.setPanno("");
				joiningForm.setUanno("");
				joiningForm.setAdharno("");
		
		
			joiningForm.setNickName("");
		
			joiningForm.setMaritalStatus("");
			joiningForm.setBirthplace("");
			joiningForm.setCountryofBirth("");
			joiningForm.setCaste("");
			joiningForm.setReligiousDenomination("");
			joiningForm.setNationality("");
			joiningForm.setTelephoneNumber("");
			joiningForm.setMobileNumber("");
			joiningForm.setFaxNumber("");
			joiningForm.setEmailAddress("");
			
			joiningForm.setBloodGroup("");
			joiningForm.setPermanentAccountNumber("");
			joiningForm.setPassportNumber("");
			
			joiningForm.setPlaceofIssueofPassport("");
			
	
				joiningForm.setDateofexpiryofPassport("");
				joiningForm.setDateofissueofPassport("");
			
			joiningForm.setPersonalIdentificationMarks("");
			joiningForm.setPhysicallyChallenged("");
			joiningForm.setPhysicallyChallengeddetails("");
			joiningForm.setEmergencyContactPersonName("");
			joiningForm.setEmergencyContactAddressLine1("");
			joiningForm.setEmergencyContactAddressLine2("");
			joiningForm.setEmergencyMobileNumber("");
			joiningForm.setEmergencyTelephoneNumber("");
			
			joiningForm.setEmergencyContactPersonName1("");
			joiningForm.setEmergencyContactAddressLine3("");
			joiningForm.setEmergencyContactAddressLine4("");
			joiningForm.setEmergCntAdd11("");
			joiningForm.setEmergCntAdd111("");
			joiningForm.setEmergCntAdd22("");
			joiningForm.setEmergCntAdd222("");
			
			
			joiningForm.setEmergencyMobileNumber1("");
			joiningForm.setEmergencyTelephoneNumber1("");
			
			joiningForm.setNoOfChildrens("");
			joiningForm.setWebsite("");
			joiningForm.setEmergCity1("");
			joiningForm.setEmergCity2("");
			joiningForm.setEmergState1("");
			joiningForm.setEmergState2("");
			
			joiningForm.setSalaryCurrency("");
			joiningForm.setPaymentMethod("");	
			joiningForm.setBranchName("");
			joiningForm.setBankName("");
			joiningForm.setAccountType("");
			joiningForm.setAccountNumber("");			
			joiningForm.setIfsCCode("");
			joiningForm.setMicrCode("");
			
			
			String insertPersonalInfo="INSERT INTO join_emp_personal_info_Approve(user_id,employee_no,title,first_name,nick_name,gender," +
					"marital_status,birth_place,country_of_birth,caste,religous_denomination,nationality,telephone_no,mobile_no,fax_no,email_address," +
					"website,blood_group,permanent_acno,passport_no,place_of_issue_of_passport,date_of_issue_of_passp,date_of_expiry_of_passport,personal_identification_mark," +
					"physiaclly_challenged,physically_challenged_details,emergency_contact_person_name,emegency_contact_addressline1,emegency_contact_addressline2," +
					"emegency_telephone_number,emegency_mobile_number,number_of_childrens,creation_date,Data_Status,emergency_contact_person_name1,emegency_contact_addressline3,emegency_contact_addressline4," +
					"emegency_telephone_number1,emegency_mobile_number1,emerg_city1,emerg_city2,emerg_state1,emerg_state2,emergCntAdd22,emergCntAdd222,EMP_FULLNAME,dob,sex,panno,uanno,adharno,PAYMENT_METHOD,WAERS,BACCTYP,BACCNO,BANKID,BRANCH,IFSC_CODE,MICR_CODE) " +
					"values('"+userId.getUserName()+"','"+userId.getUserName()+"','"+joiningForm.getTitle()+"','"+joiningForm.getFirstName()+"'," +
					"'"+joiningForm.getNickName()+"','"+joiningForm.getGender()+"','"+joiningForm.getMaritalStatus()+"'," +
					"'"+joiningForm.getBirthplace()+"','"+joiningForm.getCountryofBirth()+"','"+joiningForm.getCaste()+"'," +
					"'"+joiningForm.getReligiousDenomination()+"','"+joiningForm.getNationality()+"','"+joiningForm.getTelephoneNumber()+"','"+joiningForm.getMobileNumber()+"','"+joiningForm.getFaxNumber()+"','"+joiningForm.getEmailAddress()+"','"+joiningForm.getWebsite()+"'," +
					"'"+joiningForm.getBloodGroup()+"','"+joiningForm.getPermanentAccountNumber()+"','"+joiningForm.getPassportNumber()+"','"+joiningForm.getPlaceofIssueofPassport()+"'," +
					"'"+joiningForm.getDateofissueofPassport()+"','"+joiningForm.getDateofexpiryofPassport()+"','"+joiningForm.getPersonalIdentificationMarks()+"'," +
					"'"+joiningForm.getPhysicallyChallenged()+"','"+joiningForm.getPhysicallyChallengeddetails()+"','"+joiningForm.getEmergencyContactPersonName()+"'," +
					"'"+joiningForm.getEmergencyContactAddressLine1()+"','"+joiningForm.getEmergencyContactAddressLine2()+"','"+joiningForm.getEmergencyTelephoneNumber()+"'," +
					"'"+joiningForm.getEmergencyMobileNumber()+"','"+joiningForm.getNoOfChildrens()+"','"+currentDate+"','SAVE','"+joiningForm.getEmergencyContactPersonName1()+"'," +
					"'"+joiningForm.getEmergCntAdd11()+"','"+joiningForm.getEmergCntAdd111()+"','"+joiningForm.getEmergencyTelephoneNumber1()+"'," +
					"'"+joiningForm.getEmergencyMobileNumber1()+"','"+joiningForm.getEmergCity1()+"','"+joiningForm.getEmergCity2()+"','"+joiningForm.getEmergState1()+"','"+joiningForm.getEmergState2()+"','"+joiningForm.getEmergCntAdd22()+"','"+joiningForm.getEmergCntAdd222()+"','"+joiningForm.getFirstName()+"',"
							+ "'"+joiningForm.getDateofBirth()+"','"+joiningForm.getGender()+"','"+joiningForm.getPanno()+"','"+joiningForm.getUanno()+"','"+joiningForm.getAdharno()+"',"
									+ "'"+joiningForm.getPaymentMethod()+"','"+joiningForm.getSalaryCurrency()+"','"+joiningForm.getAccountType()+"','"+joiningForm.getAccountNumber()+"',"
											+ "'"+joiningForm.getBankName()+"','"+joiningForm.getBranchName()+"','"+joiningForm.getIfsCCode()+"','"+joiningForm.getMicrCode()+"')";
					int i=ad.SqlExecuteUpdate(insertPersonalInfo);
					
		String get4EmpInfo="select id as reqno from join_emp_personal_info_approve where creation_date='"+currentDate+"'";
		ResultSet rs512=ad.selectQuery(get4EmpInfo);
		try {
			if(rs512.next())
			{
				  joiningForm.setId(rs512.getString("reqno"));
			
				
			}
		//currency
		ResultSet rscur = ad.selectQuery("select * from Currency order by WAERS ");
		ArrayList currencyList=new ArrayList();
		ArrayList currencyLabelList=new ArrayList();
		
		while(rscur.next()) {
			currencyList.add(rscur.getString("WAERS"));
			currencyLabelList.add(rscur.getString("ISOCD"));
		}
		joiningForm.setCurrencyList(currencyList);
		joiningForm.setCurrencyLabelList(currencyLabelList);
		
		//paymode
		ResultSet rspay = ad.selectQuery("select * from PAYMODE order by 1 ");
		ArrayList paymodeList=new ArrayList();
		ArrayList paymodeLabelList=new ArrayList();
		
		while(rspay.next()) {
			paymodeList.add(rspay.getString("pay_id"));
			paymodeLabelList.add(rspay.getString("pay_method"));
		}
		joiningForm.setPaymodeList(paymodeList);
		joiningForm.setPaymodeLabelList(paymodeLabelList);
		
		//bnakname
				ResultSet rsbank= ad.selectQuery("select * from Bank order by BNAME ");
				ArrayList bankIDList=new ArrayList();
				ArrayList bankLabelList=new ArrayList();
				
				while(rsbank.next()) {
					bankIDList.add(rsbank.getString("BANKID"));
					bankLabelList.add(rsbank.getString("BNAME"));
				}
				joiningForm.setBankIDList(bankIDList);
				joiningForm.setBankLabelList(bankLabelList);
		
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX"));
		}
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		
		ResultSet rs10 = ad.selectQuery("select * from NATIONALITY order by Nationality");
		ArrayList nationalityList=new ArrayList();
		ArrayList nationalityLabelList=new ArrayList();
		
		while(rs10.next()) {
			nationalityList.add(rs10.getString("id"));
			nationalityLabelList.add(rs10.getString("Nationality"));
		}
		joiningForm.setNationalityList(nationalityList);
		joiningForm.setNationalityLabelList(nationalityLabelList);
		
		
		ResultSet rs11 = ad.selectQuery("select * from RELIGION");
		ArrayList religionList=new ArrayList();
		ArrayList religionLabelList=new ArrayList();
		
		while(rs11.next()) {
			religionList.add(rs11.getString("id"));
			religionLabelList.add(rs11.getString("Religion"));
		}
		joiningForm.setReligionList(religionList);
		joiningForm.setReligionLabelList(religionLabelList);
		
		
		String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
		ArrayList desgnIDList=new ArrayList();
		ArrayList desgnTXTList=new ArrayList();
		ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
		while(rsDesignationDetails.next()){
			desgnIDList.add(rsDesignationDetails.getString("DSGID"));
			desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
			
		}
		
		joiningForm.setDesgnIDList(desgnIDList);
		joiningForm.setDesgnTXTList(desgnTXTList);
		

		String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
		ArrayList departIDList=new ArrayList();
		ArrayList departTXTList= new ArrayList();
		ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
		while(rsdepartmentDetails.next()){
			departIDList.add(rsdepartmentDetails.getString("DPTID"));
			departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
			
		}
		
		joiningForm.setDepartIDList(departIDList);
		joiningForm.setDepartTXTList(departTXTList);
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		String parameter="personalDetails";		
		request.setAttribute(parameter, parameter);
		}
		
		return mapping.findForward("display1");
	}
	
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		userId.getId();
		
		String id=request.getParameter("id");
		
	    joiningForm.setId(id);
		
		joiningForm.setReqStatus("");
		try{
			joiningForm.setMessage("");
			joiningForm.setMessage1("");
			
		String getEmpInfo="select * from join_emp_personal_info_approve left outer join department on department.dptid=join_emp_personal_info_approve.department left outer join Designation on Designation.dsgid=join_emp_personal_info_approve.Designation where id='"+id+"'";
		ResultSet rs12=ad.selectQuery(getEmpInfo);
		while(rs12.next())
		{
			
			joiningForm.setMobileNumber(rs12.getString("mobile_no"));
			joiningForm.setEmailAddress(rs12.getString("email_address"));
			 joiningForm.setTitle(rs12.getString("title"));
			 
			 joiningForm.setDateofBirth(rs12.getString("DOB"));
				joiningForm.setFirstName(rs12.getString("EMP_FULLNAME"));	
				joiningForm.setMiddleName(rs12.getString("middle_name"));
				joiningForm.setLastName(rs12.getString("last_name"));
				joiningForm.setDateofjoin(rs12.getString("DOJ"));
				joiningForm.setDepartment(rs12.getString("department"));
				joiningForm.setDesignation(rs12.getString("designation"));
				joiningForm.setLocation(rs12.getString("Location"));
				joiningForm.setGender(rs12.getString("SEX"));
				
				joiningForm.setPanno(rs12.getString("panno"));
				joiningForm.setUanno(rs12.getString("uanno"));
				joiningForm.setAdharno(rs12.getString("adharno"));
		
		
			joiningForm.setNickName(rs12.getString("nick_name"));
		
			joiningForm.setMaritalStatus(rs12.getString("marital_status"));
			joiningForm.setBirthplace(rs12.getString("birth_place"));
			joiningForm.setCountryofBirth(rs12.getString("country_of_birth"));
			joiningForm.setCaste(rs12.getString("caste"));
			joiningForm.setReligiousDenomination(rs12.getString("religous_denomination"));
			joiningForm.setNationality(rs12.getString("nationality"));
			joiningForm.setTelephoneNumber(rs12.getString("telephone_no"));
			joiningForm.setMobileNumber(rs12.getString("mobile_no"));
			joiningForm.setFaxNumber(rs12.getString("fax_no"));
			joiningForm.setEmailAddress(rs12.getString("email_address"));
			
			joiningForm.setBloodGroup(rs12.getString("blood_group"));
			joiningForm.setPermanentAccountNumber(rs12.getString("permanent_acno"));
			joiningForm.setPassportNumber(rs12.getString("passport_no"));
			
			joiningForm.setPlaceofIssueofPassport(rs12.getString("place_of_issue_of_passport"));
			String issuePassportDate=rs12.getString("date_of_issue_of_passp");
	
			if(!(issuePassportDate.equalsIgnoreCase(""))) {
				
			
					joiningForm.setDateofissueofPassport((EMicroUtils.display(rs12.getDate("date_of_issue_of_passp"))));
				
			}else{
				joiningForm.setDateofissueofPassport("");
			}
			String expiryDate=rs12.getString("date_of_expiry_of_passport");
			if(!(expiryDate.equalsIgnoreCase(""))){
			
				joiningForm.setDateofexpiryofPassport((EMicroUtils.display(rs12.getDate("date_of_expiry_of_passport"))));
				
			}else{
				joiningForm.setDateofexpiryofPassport("");
			}
			joiningForm.setPersonalIdentificationMarks(rs12.getString("personal_identification_mark"));
			joiningForm.setPhysicallyChallenged(rs12.getString("physiaclly_challenged"));
			joiningForm.setPhysicallyChallengeddetails(rs12.getString("physically_challenged_details"));
			joiningForm.setEmergencyContactPersonName(rs12.getString("emergency_contact_person_name"));
			joiningForm.setEmergencyContactAddressLine1(rs12.getString("emegency_contact_addressline1"));
			joiningForm.setEmergencyContactAddressLine2(rs12.getString("emegency_contact_addressline2"));
			joiningForm.setEmergencyMobileNumber(rs12.getString("emegency_mobile_number"));
			joiningForm.setEmergencyTelephoneNumber(rs12.getString("emegency_telephone_number"));
			
			joiningForm.setEmergencyContactPersonName1(rs12.getString("emergency_contact_person_name1"));
			joiningForm.setEmergencyContactAddressLine3(rs12.getString("emegency_contact_addressline3"));
			joiningForm.setEmergencyContactAddressLine4(rs12.getString("emegency_contact_addressline4"));
			joiningForm.setEmergCntAdd11(rs12.getString("emegency_contact_addressline3"));
			joiningForm.setEmergCntAdd111(rs12.getString("emegency_contact_addressline4"));
			joiningForm.setEmergCntAdd22(rs12.getString("emergCntAdd22"));
			joiningForm.setEmergCntAdd222(rs12.getString("emergCntAdd222"));
			
			
			joiningForm.setEmergencyMobileNumber1(rs12.getString("emegency_mobile_number1"));
			joiningForm.setEmergencyTelephoneNumber1(rs12.getString("emegency_telephone_number1"));
			
			joiningForm.setNoOfChildrens(rs12.getString("number_of_childrens"));
			joiningForm.setWebsite(rs12.getString("website"));
			joiningForm.setEmergCity1(rs12.getString("emerg_city1"));
			joiningForm.setEmergCity2(rs12.getString("emerg_city2"));
			joiningForm.setEmergState1(rs12.getString("emerg_state1"));
			joiningForm.setEmergState2(rs12.getString("emerg_state2"));
			
			joiningForm.setSalaryCurrency(rs12.getString("WAERS"));
			joiningForm.setPaymentMethod(rs12.getString("PAYMENT_METHOD"));	
			joiningForm.setBranchName(rs12.getString("BRANCH"));
			joiningForm.setBankName(rs12.getString("BANKID"));
			joiningForm.setAccountType(rs12.getString("BACCTYP"));
			joiningForm.setAccountNumber(rs12.getString("BACCNO"));			
			joiningForm.setIfsCCode(rs12.getString("IFSC_CODE"));
			joiningForm.setMicrCode(rs12.getString("MICR_CODE"));
			
		}
		//currency
		ResultSet rscur = ad.selectQuery("select * from Currency order by WAERS ");
		ArrayList currencyList=new ArrayList();
		ArrayList currencyLabelList=new ArrayList();
		
		while(rscur.next()) {
			currencyList.add(rscur.getString("WAERS"));
			currencyLabelList.add(rscur.getString("ISOCD"));
		}
		joiningForm.setCurrencyList(currencyList);
		joiningForm.setCurrencyLabelList(currencyLabelList);
		
		//paymode
		ResultSet rspay = ad.selectQuery("select * from PAYMODE order by 1 ");
		ArrayList paymodeList=new ArrayList();
		ArrayList paymodeLabelList=new ArrayList();
		
		while(rspay.next()) {
			paymodeList.add(rspay.getString("pay_id"));
			paymodeLabelList.add(rspay.getString("pay_method"));
		}
		joiningForm.setPaymodeList(paymodeList);
		joiningForm.setPaymodeLabelList(paymodeLabelList);
		
		//bnakname
				ResultSet rsbank= ad.selectQuery("select * from Bank order by BNAME ");
				ArrayList bankIDList=new ArrayList();
				ArrayList bankLabelList=new ArrayList();
				
				while(rsbank.next()) {
					bankIDList.add(rsbank.getString("BANKID"));
					bankLabelList.add(rsbank.getString("BNAME"));
				}
				joiningForm.setBankIDList(bankIDList);
				joiningForm.setBankLabelList(bankLabelList);
		
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX"));
		}
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		
		ResultSet rs10 = ad.selectQuery("select * from NATIONALITY order by Nationality");
		ArrayList nationalityList=new ArrayList();
		ArrayList nationalityLabelList=new ArrayList();
		
		while(rs10.next()) {
			nationalityList.add(rs10.getString("id"));
			nationalityLabelList.add(rs10.getString("Nationality"));
		}
		joiningForm.setNationalityList(nationalityList);
		joiningForm.setNationalityLabelList(nationalityLabelList);
		
		
		ResultSet rs11 = ad.selectQuery("select * from RELIGION");
		ArrayList religionList=new ArrayList();
		ArrayList religionLabelList=new ArrayList();
		
		while(rs11.next()) {
			religionList.add(rs11.getString("id"));
			religionLabelList.add(rs11.getString("Religion"));
		}
		joiningForm.setReligionList(religionList);
		joiningForm.setReligionLabelList(religionLabelList);
		
		String getDesignationDetails="select DSGID,DSGSTXT from Designation order by DSGSTXT";
		ArrayList desgnIDList=new ArrayList();
		ArrayList desgnTXTList=new ArrayList();
		ResultSet rsDesignationDetails=ad.selectQuery(getDesignationDetails);
		while(rsDesignationDetails.next()){
			desgnIDList.add(rsDesignationDetails.getString("DSGID"));
			desgnTXTList.add(rsDesignationDetails.getString("DSGSTXT"));
			
		}
		
		joiningForm.setDesgnIDList(desgnIDList);
		joiningForm.setDesgnTXTList(desgnTXTList);
		

		String getdepartmentDetails="select DPTID,DPTSTXT from Department order by DPTSTXT";
		ArrayList departIDList=new ArrayList();
		ArrayList departTXTList= new ArrayList();
		ResultSet rsdepartmentDetails=ad.selectQuery(getdepartmentDetails);
		while(rsdepartmentDetails.next()){
			departIDList.add(rsdepartmentDetails.getString("DPTID"));
			departTXTList.add(rsdepartmentDetails.getString("DPTSTXT"));
			
		}
		
		joiningForm.setDepartIDList(departIDList);
		joiningForm.setDepartTXTList(departTXTList);
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		String parameter="personalDetails";		
		request.setAttribute(parameter, parameter);
		
	return mapping.findForward("display1");
	}
	

	/*public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		String linkName=request.getParameter("sId"); 	
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();		
					
		
		
		String sql="select * from links where link_name='"+linkName+"' and module='"+module+"'";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
							
			 String sql1="select * from links where module='"+module+"' and sub_linkname is null";
				
				ResultSet rs1=ad.selectQuery(sql1);
					
					
					LinkedHashMap hm=new LinkedHashMap();	
					
					ArrayList a1=new ArrayList();
					 while(rs1.next()){
						 
						 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), (rs1.getString("link_name")+','+rs1.getString("icon_name")));
						 
						 String linkName1=rs1.getString("link_name");
						 
						 if(linkName1.equalsIgnoreCase(linkName)){
						 
						 String sql2="select * from links where module='"+module+"' and sub_linkname='"+rs1.getString("link_name")+"' and sub_linkname is not null";
						 
						 ResultSet rs2=ad.selectQuery(sql2);
						 
						 while (rs2.next()) {
						 	 a1.add(rs2.getString("link_name")+","+rs2.getString("link_path")+"?method="+rs2.getString("method")+"&subLink="+rs2.getString("sub_linkname")+"&module="+rs2.getString("module")+"&linkName="+rs2.getString("link_name"));
						 }
						 
						 hm.put("Arr",a1);
						 }
						 
					}
					
		
					 session.setAttribute("SUBLINKS", hm);
				
					 String sqlempinfo="select * from users u,join_emp_personal_info e where " +
						"u.username=e.user_id";
					 System.out.println("SQL OUTPUT OF select * from users u,emp_personal_inf*********========="+sqlempinfo);
						ResultSet rs3=ad.selectQuery(sqlempinfo);
						while(rs3.next())
						{
					
							 String sqluser="select * From users where usr_type='per'";
							 ResultSet rs5=ad.selectQuery(sqluser);
							 String userType=joiningForm.getUserType();
							 System.out.println("userType" +userType);
							 if(userType=="per")
							 {								 								 
								 System.out.println("perrrrrr");
								 String sql4="SELECT link_name FROM links where module='"+module+"' and  sub_linkname is null and link_name<>'Joining Formalities'";
								 ResultSet rs4=ad.selectQuery(sql4);
								 request.setAttribute("submitButton1", "submitButton1");
							 }
							 while(rs3.next())
							 {
							   joiningForm.setUserType(rs3.getString("usr_type"));
								joiningForm.setUserName(rs3.getString("username"));
								joiningForm.setPassword(rs3.getString("password"));
								System.out.println((rs3.getString("usr_type")));
								System.out.println((rs3.getString("username")));
								System.out.println((rs3.getString("password")));
							 }
							
						}
					 
						
						UserInfo userId=(UserInfo)session.getAttribute("user");
						userId.getId();
						String getEmpInfo="select * from join_emp_personal_info where user_id='"+userId.getUserName()+"'";
						ResultSet rs12=ad.selectQuery(getEmpInfo);
						while(rs12.next())
						{
							joiningForm.setFirstName(rs12.getString("first_name"));
							joiningForm.setMiddleName(rs12.getString("middle_name"));
							joiningForm.setLastName(rs12.getString("last_name"));
							joiningForm.setMobileNumber(rs12.getString("mobile_no"));
							joiningForm.setEmailAddress(rs12.getString("email_address"));
							joiningForm.setTitle(rs12.getString("title"));
							joiningForm.setFirstName(rs12.getString("first_name"));
							joiningForm.setMiddleName(rs12.getString("middle_name"));
							joiningForm.setLastName(rs12.getString("last_name"));
							joiningForm.setInitials(rs12.getString("initials"));
							joiningForm.setNickName(rs12.getString("nick_name"));
							joiningForm.setGender(rs12.getString("gender"));
							joiningForm.setMaritalStatus(rs12.getString("marital_status"));
							joiningForm.setDateofBirth((EMicroUtils.display(rs12.getDate("date_of_birth"))));
							joiningForm.setBirthplace(rs12.getString("birth_place"));
							joiningForm.setCountryofBirth(rs12.getString("country_of_birth"));
							joiningForm.setCaste(rs12.getString("caste"));
							joiningForm.setReligiousDenomination(rs12.getString("religous_denomination"));
							joiningForm.setNationality(rs12.getString("nationality"));
							joiningForm.setTelephoneNumber(rs12.getString("telephone_no"));
							joiningForm.setMobileNumber(rs12.getString("mobile_no"));
							joiningForm.setFaxNumber(rs12.getString("fax_no"));
							joiningForm.setEmailAddress(rs12.getString("email_address"));
							
							joiningForm.setBloodGroup(rs12.getString("blood_group"));
							joiningForm.setPermanentAccountNumber(rs12.getString("permanent_acno"));
							joiningForm.setPassportNumber(rs12.getString("passport_no"));
							
							joiningForm.setPlaceofIssueofPassport(rs12.getString("place_of_issue_of_passport"));
							String issuePassportDate=rs12.getString("date_of_issue_of_passp");
					
							if(!(issuePassportDate.equalsIgnoreCase(""))) {
								
							
									joiningForm.setDateofissueofPassport((EMicroUtils.display(rs12.getDate("date_of_issue_of_passp"))));
								
							}else{
								joiningForm.setDateofissueofPassport("");
							}
							String expiryDate=rs12.getString("date_of_expiry_of_passport");
							if(!(expiryDate.equalsIgnoreCase(""))){
							
								joiningForm.setDateofexpiryofPassport((EMicroUtils.display(rs12.getDate("date_of_expiry_of_passport"))));
								
							}else{
								joiningForm.setDateofexpiryofPassport("");
							}
							joiningForm.setPersonalIdentificationMarks(rs12.getString("personal_identification_mark"));
							joiningForm.setPhysicallyChallenged(rs12.getString("physiaclly_challenged"));
							joiningForm.setPhysicallyChallengeddetails(rs12.getString("physically_challenged_details"));
							joiningForm.setEmergencyContactPersonName(rs12.getString("emergency_contact_person_name"));
							joiningForm.setEmergencyContactAddressLine1(rs12.getString("emegency_contact_addressline1"));
							joiningForm.setEmergencyContactAddressLine2(rs12.getString("emegency_contact_addressline2"));
							joiningForm.setEmergencyMobileNumber(rs12.getString("emegency_mobile_number"));
							joiningForm.setEmergencyTelephoneNumber(rs12.getString("emegency_telephone_number"));
							joiningForm.setNoOfChildrens(rs12.getString("number_of_childrens"));
							joiningForm.setWebsite(rs12.getString("website"));
							
							
						}
						String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
						ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
						while (rsEmpPhoto.next())
						{
							joiningForm.setPhotoImage(rsEmpPhoto.getString("file_name"));
						request.setAttribute("employeePhoto", "employeePhoto");	
						}
						
					 
		
	
		ArrayList list =new ArrayList();

		request.setAttribute("listName", list);
		
		
		request.setAttribute("personalDetails", "personalDetails");
		
		
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX"));
		}
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		
		int countPersonalInfo=0;
		int countEmpAddress=0;
		int countEmpEdu=0;
		int countFamilyDetails=0;
		int countExp=0;
		int countEmpLang=0;
		String checkPersonalInfo="select count(user_id) from join_emp_personal_info where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs11=ad.selectQuery(checkPersonalInfo);
		while(rs11.next()){
			countPersonalInfo=rs11.getInt(1);			
		}
		String checkjoin_emp_address="select count(user_id) from join_emp_address where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs13=ad.selectQuery(checkjoin_emp_address);
		while(rs13.next()){
			countEmpAddress=rs13.getInt(1);			
		}
		
		String checkFamilyDetails="select count(user_id) from join_emp_family_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs2=ad.selectQuery(checkFamilyDetails);
		while(rs2.next()){
			countFamilyDetails=rs2.getInt(1);			
		}
		String checkEduDetails="select count(user_id) from join_emp_education_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs31=ad.selectQuery(checkEduDetails);
		while(rs31.next()){
			countEmpEdu=rs31.getInt(1);			
		}
		String checkExpDetails="select count(user_id) from join_emp_education_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs4=ad.selectQuery(checkExpDetails);
		while(rs4.next()){
			countExp=rs4.getInt(1);			
		}
		String checkEmpLang="select count(user_id) from join_emp_language_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT'";
		ResultSet rs5=ad.selectQuery(checkEmpLang);
		while(rs5.next()){
			countEmpLang=rs5.getInt(1);			
		}
		
		System.out.println("countPersonalInfo="+countPersonalInfo);
		System.out.println("countEmpAddress="+countEmpAddress);
		System.out.println("countFamilyDetails="+countFamilyDetails);
		System.out.println("countEmpEdu="+countEmpEdu);
		
		System.out.println("countExp="+countExp);
		
		
		System.out.println("countEmpLang="+countEmpLang);
		
		if(countPersonalInfo>0&&countEmpAddress>0&&countFamilyDetails>0&&countEmpEdu>0&&countExp>0&&countEmpLang>0){
			
			UserInfo user1=(UserInfo)session.getAttribute("user");
			
			String userType=user1.getUserType();
			System.out.println("user type*****="+userType);

			
			if(userType.equalsIgnoreCase("temp"))
			{		 
				
				String getEmpInformation="select * from join_emp_personal_info where user_id='"+userId.getUserName()+"'";
				ResultSet rs22=ad.selectQuery(getEmpInformation);
				ArrayList list1 = new ArrayList();
				while(rs22.next())
				{
					
					
					joiningForm=(JoiningReportForm) form;
					joiningForm.setFirstName(rs22.getString("first_name"));
					joiningForm.setMiddleName(rs22.getString("middle_name"));
					joiningForm.setLastName(rs22.getString("last_name"));
					joiningForm.setMobileNumber(rs22.getString("mobile_no"));
					joiningForm.setEmailAddress(rs22.getString("email_address"));
					joiningForm.setTitle(rs22.getString("title"));
					joiningForm.setFirstName(rs22.getString("first_name"));
					joiningForm.setMiddleName(rs22.getString("middle_name"));
					joiningForm.setLastName(rs22.getString("last_name"));
					joiningForm.setInitials(rs22.getString("initials"));
					joiningForm.setNickName(rs22.getString("nick_name"));
					joiningForm.setGender(rs22.getString("gender"));
					joiningForm.setMaritalStatus(rs22.getString("marital_status"));
					joiningForm.setDateofBirth((EMicroUtils.display(rs22.getDate("date_of_birth"))));
					joiningForm.setBirthplace(rs22.getString("birth_place"));
					joiningForm.setCountryofBirth(rs22.getString("country_of_birth"));
					joiningForm.setCaste(rs22.getString("caste"));
					joiningForm.setReligiousDenomination(rs22.getString("religous_denomination"));
					joiningForm.setNationality(rs22.getString("nationality"));
					joiningForm.setTelephoneNumber(rs22.getString("telephone_no"));
					joiningForm.setMobileNumber(rs22.getString("mobile_no"));
					joiningForm.setFaxNumber(rs22.getString("fax_no"));
					joiningForm.setEmailAddress(rs22.getString("email_address"));
					
					joiningForm.setBloodGroup(rs22.getString("blood_group"));
					joiningForm.setPermanentAccountNumber(rs22.getString("permanent_acno"));
					joiningForm.setPassportNumber(rs22.getString("passport_no"));
					
					joiningForm.setPlaceofIssueofPassport(rs22.getString("place_of_issue_of_passport"));
					joiningForm.setDateofissueofPassport((EMicroUtils.display(rs22.getDate("date_of_issue_of_passp"))));
					joiningForm.setDateofexpiryofPassport((EMicroUtils.display(rs22.getDate("date_of_expiry_of_passport"))));
					joiningForm.setPersonalIdentificationMarks(rs22.getString("personal_identification_mark"));
					joiningForm.setPhysicallyChallenged(rs22.getString("physiaclly_challenged"));
					joiningForm.setPhysicallyChallengeddetails(rs22.getString("physically_challenged_details"));
					joiningForm.setEmergencyContactPersonName(rs22.getString("emergency_contact_person_name"));
					joiningForm.setEmergencyContactAddressLine1(rs22.getString("emegency_contact_addressline1"));
					joiningForm.setEmergencyContactAddressLine2(rs22.getString("emegency_contact_addressline2"));
					joiningForm.setEmergencyMobileNumber(rs22.getString("emegency_mobile_number"));
					joiningForm.setEmergencyTelephoneNumber(rs22.getString("emegency_telephone_number"));
					joiningForm.setNoOfChildrens(rs22.getString("number_of_childrens"));
					joiningForm.setWebsite(rs22.getString("website"));
					
					
					 list1.add(joiningForm);
				}
				request.setAttribute("empPersonal", list1);
				return mapping.findForward("displaytemp");		 
			
			}
			else
			{
				return mapping.findForward("display1");
		    }
			
			
			
		}
		}catch(SQLException se){
			se.printStackTrace();
		}

		return mapping.findForward("display1");
	}*/
	
	
	public ActionForward selectTempAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		
		HttpSession session=request.getSession();		
					
		System.out.println("*******CONTROL COMES HERE selectAddress*********");
		
		try{
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			String parameter=request.getParameter("param");		
			request.setAttribute(parameter, parameter);
		
			System.out.println("userId"+userId.getId());		
			 
			String selectAddress = "select * from join_emp_address_approve where user_id='"+userId.getUserName()+"'";
			
			System.out.println("*********************selectAddress SQL===================="+selectAddress);		
			
			
			 ResultSet rs3=ad.selectQuery(selectAddress);
			
			 while(rs3.next())
			 {
		 
			   joiningForm.setCareofcontactname(rs3.getString("care_of_contact_name"));
				joiningForm.setHouseNumber(rs3.getString("house_no"));
				joiningForm.setAddressLine1(rs3.getString("address_line1"));
				joiningForm.setAddressLine2(rs3.getString("address_line2"));
				joiningForm.setAddressLine3(rs3.getString("address_line3"));
				joiningForm.setPostalCode(rs3.getString("postal_code"));
				joiningForm.setCity(rs3.getString("a_city"));
				joiningForm.setState(rs3.getString("a_state"));
				joiningForm.setCountry(rs3.getString("a_country"));
				joiningForm.setOwnAccomodation(rs3.getString("own_accomodation"));
				joiningForm.setCompanyHousing(rs3.getString("company_housing"));
				joiningForm.setAddStartDate((EMicroUtils.display(rs3.getDate("start_date"))));
				joiningForm.setAddEndDate((EMicroUtils.display(rs3.getDate("end_date"))));
			 }
			 
			 ArrayList list = new ArrayList();
				String sql3="select * from join_emp_address_approve where user_id='"+userId.getUserName()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					joiningForm = new JoiningReportForm();
					 joiningForm.setNewid(rs11.getString("id"));
					    joiningForm.setAddressType(rs11.getString("address_type"));
					    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
						joiningForm.setHouseNumber(rs11.getString("house_no"));
						joiningForm.setAddressLine1(rs11.getString("address_line1"));
						joiningForm.setAddressLine2(rs11.getString("address_line2"));
						joiningForm.setAddressLine3(rs11.getString("address_line3"));
						joiningForm.setPostalCode(rs11.getString("postal_code"));
						joiningForm.setCity(rs11.getString("a_city"));
						joiningForm.setState(rs11.getString("a_state"));
						joiningForm.setCountry(rs11.getString("a_country"));
						joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
						joiningForm.setCompanyHousing(rs11.getString("company_housing"));
						joiningForm.setAddStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
						joiningForm.setAddEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
					    list.add(joiningForm);
				}
				request.setAttribute("listName", list);
					 
		}catch(SQLException se){
			se.printStackTrace();
		}
		

		return mapping.findForward("display1");
	}
	public ActionForward savePersonalInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		
		HttpSession session1=request.getSession();
		
		HttpSession session=request.getSession();	
		int countPersonalInfo=0;
		int countEmpAddress=0;
		int countEmpEdu=0;
		int countFamilyDetails=0;
		int countExp=0;
		int countEmpLang=0;
		int chkstatus=0;
		/*String parameter=request.getParameter("param");		
		request.setAttribute(parameter, parameter);*/
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String module=request.getParameter("id"); 
		if(userId==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		userId.getId();
	    try
	    {
	
		System.out.println("userId"+userId.getId());
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//get current date time with Date()
         Date date=new Date();
		System.out.println(dateFormat.format(date));

		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Date="+dateFormat.format(cal.getTime()));
		String currentDate=dateFormat.format(cal.getTime());

		
		ResultSet rs9 = ad.selectQuery("select * from Country order by LANDX ");
		ArrayList countryList=new ArrayList();
		ArrayList countryLabelList=new ArrayList();
		
		while(rs9.next()) {
			countryList.add(rs9.getString("LAND1"));
			countryLabelList.add(rs9.getString("LANDX"));
		}
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		
		
		
		String dateOfIssuePassport=joiningForm.getDateofissueofPassport();
		if(!dateOfIssuePassport.equalsIgnoreCase("")){
		dateOfIssuePassport=EMicroUtils.dateConvert(joiningForm.getDateofissueofPassport());
		}else{
			dateOfIssuePassport="";
		}
		joiningForm.setDateofissueofPassport(dateOfIssuePassport);
		String dateOfExpiryPassport=joiningForm.getDateofexpiryofPassport();
		if(!dateOfExpiryPassport.equalsIgnoreCase("")){
		 dateOfExpiryPassport=EMicroUtils.dateConvert(joiningForm.getDateofexpiryofPassport());
		}else{
			dateOfExpiryPassport="";
		}
		joiningForm.setDateofexpiryofPassport(dateOfExpiryPassport);
		
		
		///check duplicates
		//PAN no
		
		String pan="select * from join_emp_personal_info_Approve where panno='"+joiningForm.getPanno()+"' and id!='"+module+"' and panno!=''";
		ResultSet rspan=ad.selectQuery(pan);
		if(rspan.next())
		{
			joiningForm.setMessage1("This PAN No. Already Exists Kindly Check");
			joiningForm.setMessage("");
			request.setAttribute("personalDetails", "personalDetails");
			return mapping.findForward("display1");
		}
		
		//mobileno
		String mob="select * from join_emp_personal_info_Approve where mobile_no='"+joiningForm.getMobileNumber()+"' and id!='"+module+"' and mobile_no!=''";
		ResultSet rsmob=ad.selectQuery(mob);
		if(rsmob.next())
		{
			joiningForm.setMessage1("This Mob No. Already Exists Kindly Check");
			joiningForm.setMessage("");
			request.setAttribute("personalDetails", "personalDetails");
			return mapping.findForward("display1");
		}
		
//eamil
		String email="select * from join_emp_personal_info_Approve where email_address='"+joiningForm.getEmailAddress()+"' and id!='"+module+"' and email_address!='' ";
		ResultSet rsemail=ad.selectQuery(email);
		if(rsemail.next())
		{
			joiningForm.setMessage1("This Email Address Already Exists Kindly Check");
			joiningForm.setMessage("");
			request.setAttribute("personalDetails", "personalDetails");
			return mapping.findForward("display1");
		}
				
				
		//aadhar
		String aadhar="select * from join_emp_personal_info_Approve where adharno='"+joiningForm.getAdharno()+"' and id!='"+module+"'  and adharno!='' ";
		ResultSet rsadar=ad.selectQuery(aadhar);
		if(rsadar.next())
		{
			joiningForm.setMessage1("This Aadhar No. Already Exists Kindly Check");
			joiningForm.setMessage("");
			request.setAttribute("personalDetails", "personalDetails");
			return mapping.findForward("display1");
		}
		
		if(true)
		{
		
		String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where sl_no='"+module+"' ";
		ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
		while(rs1.next()){
			countEmpAddress=rs1.getInt(1);			
		}
		
		String checkFamilyDetails="select count(user_id) from join_emp_family_details_approve where sl_no='"+module+"'";
		ResultSet rs2=ad.selectQuery(checkFamilyDetails);
		while(rs2.next()){
			countFamilyDetails=rs2.getInt(1);			
		}
		String checkEduDetails="select count(user_id) from join_emp_education_details_approve where sl_no='"+module+"'  ";
		ResultSet rs3=ad.selectQuery(checkEduDetails);
		while(rs3.next()){
			countEmpEdu=rs3.getInt(1);			
		}
		String checkExpDetails="select count(user_id) from join_emp_experience_details_approve where sl_no='"+module+"'  ";
		ResultSet rs4=ad.selectQuery(checkExpDetails);
		while(rs4.next()){
			countExp=rs4.getInt(1);			
		}
		String checkEmpLang="select count(user_id) from join_emp_language_details_approve where sl_no='"+module+"'  ";
		ResultSet rs5=ad.selectQuery(checkEmpLang);
		while(rs5.next()){
			countEmpLang=rs5.getInt(1);			
		}
		}
		//chck addres wheter mailing and permanent address are entered
		if(countEmpAddress>0)
		{
			int mail=0;
			int perm=0;
			String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where sl_no='"+module+"' and address_type='003' ";
			ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
			while(rs1.next()){
				mail=rs1.getInt(1);			
			}
			
			String checkjoinperm="select count(user_id) from join_emp_address_approve where sl_no='"+module+"' and address_type='001' ";
			ResultSet rsperm=ad.selectQuery(checkjoinperm);
			while(rsperm.next()){
				perm=rsperm.getInt(1);			
			}
			
			
			if(mail==0)
			{

				joiningForm.setMessage("Please Enter mailing Address Details");
				joiningForm.setMessage1("");
				
				request.setAttribute("personalDetails", "");
				String parameter="addressDetails";	
				request.setAttribute(parameter, parameter);
				request.setAttribute("addressAdd", "addressAdd");
				joiningForm.setAddressStatus("Save");
				
				return mapping.findForward("display1");
			
			}
			if(perm==0)
			{

				joiningForm.setMessage("Please Enter Permanent Address Details");
				joiningForm.setMessage1("");
				
				request.setAttribute("personalDetails", "");
				String parameter="addressDetails";	
				request.setAttribute(parameter, parameter);
				request.setAttribute("addressAdd", "addressAdd");
				joiningForm.setAddressStatus("Save");
				
				return mapping.findForward("display1");
			
			}
		}
		
		if(countEmpAddress>0&&countFamilyDetails>0&&countEmpEdu>0&&countExp>0&&countEmpLang>0)
		{
		
    String checkData="select count(*) from join_emp_personal_info_Approve where id='"+module+"'"  ;
    int k=0;
    int i=0;
    ResultSet rsCheck=ad.selectQuery(checkData);
    while(rsCheck.next()){
    	k=rsCheck.getInt(1);
    }
	if(k==0)
	{
		 if(joiningForm.getEmailAddress().contains("@"))
		   {
		String to[]=joiningForm.getEmailAddress().split("@");
		String todomain=to[1];
		
		if(JoiningReportAction.getNS(todomain)==false)
		{
			joiningForm.setMessage1("Invalid Email Id");
			joiningForm.setMessage("");
			request.setAttribute("personalDetails", "personalDetails");
			return mapping.findForward("display1");
		}
		   }
		 else
		 {
			 joiningForm.setMessage1("Invalid Email Id");
			 joiningForm.setMessage("");
			 request.setAttribute("personalDetails", "personalDetails");
				return mapping.findForward("display1");
		 }
		
		String insertPersonalInfo="INSERT INTO join_emp_personal_info_Approve(user_id,employee_no,title,first_name,nick_name,gender," +
		"marital_status,birth_place,country_of_birth,caste,religous_denomination,nationality,telephone_no,mobile_no,fax_no,email_address," +
		"website,blood_group,permanent_acno,passport_no,place_of_issue_of_passport,date_of_issue_of_passp,date_of_expiry_of_passport,personal_identification_mark," +
		"physiaclly_challenged,physically_challenged_details,emergency_contact_person_name,emegency_contact_addressline1,emegency_contact_addressline2," +
		"emegency_telephone_number,emegency_mobile_number,number_of_childrens,creation_date,Data_Status,emergency_contact_person_name1,emegency_contact_addressline3,emegency_contact_addressline4," +
		"emegency_telephone_number1,emegency_mobile_number1,emerg_city1,emerg_city2,emerg_state1,emerg_state2,emergCntAdd22,emergCntAdd222,EMP_FULLNAME,dob,sex,panno,uanno,adharno,PAYMENT_METHOD,WAERS,BACCTYP,BACCNO,BANKID,BRANCH,IFSC_CODE,MICR_CODE,middle_name,last_name,DOJ,Department,Designation,Location) " +
		"values('"+userId.getUserName()+"','"+userId.getUserName()+"','"+joiningForm.getTitle()+"','"+joiningForm.getFirstName()+"'," +
		"'"+joiningForm.getNickName()+"','"+joiningForm.getGender()+"','"+joiningForm.getMaritalStatus()+"'," +
		"'"+joiningForm.getBirthplace()+"','"+joiningForm.getCountryofBirth()+"','"+joiningForm.getCaste()+"'," +
		"'"+joiningForm.getReligiousDenomination()+"','"+joiningForm.getNationality()+"','"+joiningForm.getTelephoneNumber()+"','"+joiningForm.getMobileNumber()+"','"+joiningForm.getFaxNumber()+"','"+joiningForm.getEmailAddress()+"','"+joiningForm.getWebsite()+"'," +
		"'"+joiningForm.getBloodGroup()+"','"+joiningForm.getPermanentAccountNumber()+"','"+joiningForm.getPassportNumber()+"','"+joiningForm.getPlaceofIssueofPassport()+"'," +
		"'"+joiningForm.getDateofissueofPassport()+"','"+joiningForm.getDateofexpiryofPassport()+"','"+joiningForm.getPersonalIdentificationMarks()+"'," +
		"'"+joiningForm.getPhysicallyChallenged()+"','"+joiningForm.getPhysicallyChallengeddetails()+"','"+joiningForm.getEmergencyContactPersonName()+"'," +
		"'"+joiningForm.getEmergencyContactAddressLine1()+"','"+joiningForm.getEmergencyContactAddressLine2()+"','"+joiningForm.getEmergencyTelephoneNumber()+"'," +
		"'"+joiningForm.getEmergencyMobileNumber()+"','"+joiningForm.getNoOfChildrens()+"','"+currentDate+"','SAVE','"+joiningForm.getEmergencyContactPersonName1()+"'," +
		"'"+joiningForm.getEmergCntAdd11()+"','"+joiningForm.getEmergCntAdd111()+"','"+joiningForm.getEmergencyTelephoneNumber1()+"'," +
		"'"+joiningForm.getEmergencyMobileNumber1()+"','"+joiningForm.getEmergCity1()+"','"+joiningForm.getEmergCity2()+"','"+joiningForm.getEmergState1()+"','"+joiningForm.getEmergState2()+"','"+joiningForm.getEmergCntAdd22()+"','"+joiningForm.getEmergCntAdd222()+"','"+joiningForm.getFirstName()+"',"
				+ "'"+joiningForm.getDateofBirth()+"','"+joiningForm.getGender()+"','"+joiningForm.getPanno()+"','"+joiningForm.getUanno()+"','"+joiningForm.getAdharno()+"',"
						+ "'"+joiningForm.getPaymentMethod()+"','"+joiningForm.getSalaryCurrency()+"','"+joiningForm.getAccountType()+"','"+joiningForm.getAccountNumber()+"',"
								+ "'"+joiningForm.getBankName()+"','"+joiningForm.getBranchName()+"','"+joiningForm.getIfsCCode()+"','"+joiningForm.getMicrCode()+"','"+joiningForm.getMiddleName()+"','"+joiningForm.getLastName()+"','"+joiningForm.getDateofjoin()+"','"+joiningForm.getDepartment()+"','"+joiningForm.getDesignation()+"','"+joiningForm.getLocation()+"')";
		i=ad.SqlExecuteUpdate(insertPersonalInfo);
	}else{
		
		 if(joiningForm.getEmailAddress().contains("@"))
		   {
		String to[]=joiningForm.getEmailAddress().split("@");
		String todomain=to[1];
		
		if(JoiningReportAction.getNS(todomain)==false)
		{
			joiningForm.setMessage1("Invalid Email Id");
			joiningForm.setMessage("");
			 request.setAttribute("personalDetails", "personalDetails");
			return mapping.findForward("display1");
		}
		   }
		 else
		 {
			 joiningForm.setMessage1("Invalid Email Id");
			 joiningForm.setMessage("");
			 request.setAttribute("personalDetails", "personalDetails");
				return mapping.findForward("display1");
		 }
		
	String updatepersonalinfo="update join_emp_personal_info_Approve set title='"+joiningForm.getTitle()+"'," +
			"nick_name='"+joiningForm.getNickName()+"',gender='"+joiningForm.getGender()+"',marital_status='"+joiningForm.getMaritalStatus()+"'," +
			"birth_place='"+joiningForm.getBirthplace()+"',country_of_birth='"+joiningForm.getCountryofBirth()+"'," +
			"caste='"+joiningForm.getCaste()+"',religous_denomination='"+joiningForm.getReligiousDenomination()+"',nationality='"+joiningForm.getNationality()+"'," +
			"telephone_no='"+joiningForm.getTelephoneNumber()+"',fax_no='"+joiningForm.getFaxNumber()+"',website='"+joiningForm.getWebsite()+"'," +
			"blood_group='"+joiningForm.getBloodGroup()+"',permanent_acno='"+joiningForm.getPermanentAccountNumber()+"'," +
			"passport_no='"+joiningForm.getPassportNumber()+"',place_of_issue_of_passport='"+joiningForm.getPlaceofIssueofPassport()+"'," +
		"date_of_issue_of_passp='"+joiningForm.getDateofissueofPassport()+"',date_of_expiry_of_passport='"+joiningForm.getDateofexpiryofPassport()+"'," +
		"personal_identification_mark='"+joiningForm.getPersonalIdentificationMarks()+"',physiaclly_challenged='"+joiningForm.getPhysicallyChallenged()+"'," +
		"physically_challenged_details='"+joiningForm.getPhysicallyChallengeddetails()+"',emergency_contact_person_name='"+joiningForm.getEmergencyContactPersonName()+"'," +
		"emegency_contact_addressline1='"+joiningForm.getEmergencyContactAddressLine1()+"',emegency_contact_addressline2='"+joiningForm.getEmergencyContactAddressLine2()+"'," +
		"emegency_telephone_number='"+joiningForm.getEmergencyTelephoneNumber()+"',emegency_mobile_number='"+joiningForm.getEmergencyMobileNumber()+"',number_of_childrens='"+joiningForm.getNoOfChildrens()+"'," +
		"changed_on='"+currentDate+"',Last_changed_by='"+userId.getId()+"',email_address='"+joiningForm.getEmailAddress()+"',Data_Status='UPDATED',emergency_contact_person_name1='"+joiningForm.getEmergencyContactPersonName1()+"'," +
		"emegency_contact_addressline3='"+joiningForm.getEmergCntAdd11()+"',emegency_contact_addressline4='"+joiningForm.getEmergCntAdd111()+"'," +
		"emegency_telephone_number1='"+joiningForm.getEmergencyTelephoneNumber1()+"',emegency_mobile_number1='"+joiningForm.getEmergencyMobileNumber1()+"'," +
			"first_name='"+joiningForm.getFirstName()+"',mobile_no='"+joiningForm.getMobileNumber()+"',emerg_city1='"+joiningForm.getEmergCity1()+"',emerg_city2='"+joiningForm.getEmergCity2()+"',"
		+ "emerg_state1='"+joiningForm.getEmergState1()+"',emerg_state2='"+joiningForm.getEmergState2()+"',emergCntAdd22='"+joiningForm.getEmergCntAdd22()+"',emergCntAdd222='"+joiningForm.getEmergCntAdd222()+"'"
				+ ",EMP_FULLNAME='"+joiningForm.getFirstName()+"',dob='"+joiningForm.getDateofBirth()+"',sex='"+joiningForm.getGender()+"',panno='"+joiningForm.getPanno()+"',uanno='"+joiningForm.getUanno()+"',adharno='"+joiningForm.getAdharno()+"',"
						+ "PAYMENT_METHOD='"+joiningForm.getPaymentMethod()+"',WAERS='"+joiningForm.getSalaryCurrency()+"',BACCTYP='"+joiningForm.getAccountType()+"',BACCNO='"+joiningForm.getAccountNumber()+"',"
								+ "BANKID='"+joiningForm.getBankName()+"',BRANCH='"+joiningForm.getBranchName()+"',IFSC_CODE='"+joiningForm.getIfsCCode()+"',MICR_CODE='"+joiningForm.getMicrCode()+"',"
										+ "middle_name='"+joiningForm.getMiddleName()+"',last_name='"+joiningForm.getLastName()+"',DOJ='"+joiningForm.getDateofjoin()+"',Department='"+joiningForm.getDepartment()+"',Designation='"+joiningForm.getDesignation()+"',Location='"+joiningForm.getLocation()+"' where id='"+module+"'";

	System.out.println("updatepersonalinfo Query="+updatepersonalinfo);	
	i=ad.SqlExecuteUpdate(updatepersonalinfo);
	}
		if (i > 0) {
			joiningForm.setMessage("Personal Information Details Saved Successfully");
			joiningForm.setMessage1("");
			
		}
		else
		{
			joiningForm.setMessage1("Error While Adding  Personal Information .. Please check Entered Values");
			joiningForm.setMessage("");
			
		}
		}
		else
		{

			
			if(countEmpLang==0)
			{
				joiningForm.setMessage("Please Enter  Language Details");
				joiningForm.setMessage1("");
				
				String parameter="languageDetails";	
				request.setAttribute(parameter, parameter);
				request.setAttribute("addLanguage", "addLanguage");
				request.setAttribute("personalDetails", "");
				 ResultSet rsLang = ad.selectQuery("select * from LANGUAGE ");
					ArrayList langID=new ArrayList();
					ArrayList langValueList=new ArrayList();
				
					while(rsLang.next()) {
						langID.add(rsLang.getString("Id"));
						langValueList.add(rsLang.getString("Language"));
					}
					joiningForm.setLanguageID(langID);
					joiningForm.setLanguageValueList(langValueList);
				return mapping.findForward("display1");
			}
			if(countExp==0)
			{
				joiningForm.setMessage("Please Enter  Experience Details ");
				joiningForm.setMessage1("");
				request.setAttribute("personalDetails", "");
				String parameter="experienceDetails";	
				request.setAttribute(parameter, parameter);
				request.setAttribute("addExperience", "addExperience");
				  rs9 = ad.selectQuery("select * from Country  ");
					 countryList=new ArrayList();
					 countryLabelList=new ArrayList();
					
					while(rs9.next()) {
						countryList.add(rs9.getString("LAND1"));
						countryLabelList.add(rs9.getString("LANDX"));
					}
					joiningForm.setCountryList(countryList);
					joiningForm.setCountryLabelList(countryLabelList);
					
					ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY");
					ArrayList industyID=new ArrayList();
					ArrayList industyValueList=new ArrayList();
					
					while(rsIndustry.next()) {
						industyID.add(rsIndustry.getString("Id"));
						industyValueList.add(rsIndustry.getString("Ind_Desc"));
					}
					joiningForm.setIndustyID(industyID);
					joiningForm.setIndustyValueList(industyValueList);
				return mapping.findForward("display1");
			}
			if(countEmpEdu==0)
			{
				joiningForm.setMessage("Please Enter  Education Details");
				joiningForm.setMessage1("");
				request.setAttribute("personalDetails", "");
				String parameter="educationDetails";	
				request.setAttribute(parameter, parameter);
				request.setAttribute("addEducation", "addEducation");
			
				
				 rs9 = ad.selectQuery("select * from Country  ");
				 countryList=new ArrayList();
				 countryLabelList=new ArrayList();
				
				while(rs9.next()) {
					countryList.add(rs9.getString("LAND1"));
					countryLabelList.add(rs9.getString("LANDX"));
				}
				joiningForm.setCountryList(countryList);
				joiningForm.setCountryLabelList(countryLabelList);
				joiningForm.setEducationStatus("Save");
				
				ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL  ");
				ArrayList eduIDList=new ArrayList();
				ArrayList eduValueList=new ArrayList();
				
				while(rsEdu.next()) {
					eduIDList.add(rsEdu.getString("Id"));
					eduValueList.add(rsEdu.getString("Education_Level"));
				}
				joiningForm.setEduIDList(eduIDList);
				joiningForm.setEduValueList(eduValueList);
				
				ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION ");
				ArrayList qulificationID=new ArrayList();
				ArrayList qulificationValueList=new ArrayList();
				
				while(rsQulif.next()) {
					qulificationID.add(rsQulif.getString("Id"));
					qulificationValueList.add(rsQulif.getString("Qualification"));
				}
				joiningForm.setQulificationID(qulificationID);
				joiningForm.setQulificationValueList(qulificationValueList);
				
				ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY ");
				ArrayList industyID=new ArrayList();
				ArrayList industyValueList=new ArrayList();
				
				while(rsIndustry.next()) {
					industyID.add(rsIndustry.getString("Id"));
					industyValueList.add(rsIndustry.getString("Ind_Desc"));
				}
				joiningForm.setIndustyID(industyID);
				joiningForm.setIndustyValueList(industyValueList);
				joiningForm.setEducationStatus("Save");
				return mapping.findForward("display1");
			}
			if(countFamilyDetails==0)
			{
				joiningForm.setMessage("Please Enter  Family Details");
				joiningForm.setMessage1("");
				request.setAttribute("personalDetails", "");
				String parameter="familyDetails";	
				request.setAttribute(parameter, parameter);
				 rs9 = ad.selectQuery("select * from RELATIONSHIP order by RELATIONSHIP ");
				ArrayList relationIDList=new ArrayList();
				ArrayList relationValueList=new ArrayList();
				
				while(rs9.next()) {
					relationIDList.add(rs9.getString("Id"));
					relationValueList.add(rs9.getString("RELATIONSHIP"));
				}
				joiningForm.setRelationIDList(relationIDList);
				joiningForm.setRelationValueList(relationValueList);
				
				request.setAttribute("addFamily", "addFamily");
				return mapping.findForward("display1");
			}
			if(countEmpAddress==0)
			{
				joiningForm.setMessage("Please Enter  Address Details");
				joiningForm.setMessage1("");
				request.setAttribute("personalDetails", "");
				
				String parameter="addressDetails";	
				request.setAttribute(parameter, parameter);
				request.setAttribute("addressAdd", "addressAdd");
				joiningForm.setAddressStatus("Save");
				
				return mapping.findForward("display1");
			}
		
			
			
		
		}
		
		try{
		
			String getEmpInfo="select * from join_emp_personal_info_approve left outer join department on department.dptid=join_emp_personal_info_approve.department  "
					+ "left outer join Designation on Designation.dsgid=join_emp_personal_info_approve.Designation where id='"+module+"'";
			ResultSet rs12=ad.selectQuery(getEmpInfo);
		while(rs12.next())
		{
			joiningForm.setDateofBirth(rs12.getString("DOB"));
			joiningForm.setFirstName(rs12.getString("EMP_FULLNAME"));	
			joiningForm.setMiddleName(rs12.getString("middle_name"));
			joiningForm.setLastName(rs12.getString("last_name"));
			joiningForm.setDateofjoin(rs12.getString("DOJ"));
			joiningForm.setDepartment(rs12.getString("department"));
			joiningForm.setDesignation(rs12.getString("designation"));
			joiningForm.setLocation(rs12.getString("Location"));
			joiningForm.setFirstName(rs12.getString("EMP_FULLNAME"));					
			joiningForm.setGender(rs12.getString("SEX"));		
			joiningForm.setPanno(rs12.getString("panno"));
			joiningForm.setUanno(rs12.getString("uanno"));
			joiningForm.setAdharno(rs12.getString("adharno"));
			
			joiningForm.setMiddleName(rs12.getString("middle_name"));
			joiningForm.setLastName(rs12.getString("last_name"));
			joiningForm.setMobileNumber(rs12.getString("mobile_no"));
			joiningForm.setEmailAddress(rs12.getString("email_address"));
			joiningForm.setTitle(rs12.getString("title"));
	/*		joiningForm.setFirstName(rs12.getString("first_name"));
			joiningForm.setMiddleName(rs12.getString("middle_name"));
			joiningForm.setLastName(rs12.getString("last_name"));*/
			joiningForm.setInitials(rs12.getString("initials"));
			joiningForm.setNickName(rs12.getString("nick_name"));
			joiningForm.setGender(rs12.getString("gender"));
			joiningForm.setMaritalStatus(rs12.getString("marital_status"));
			//joiningForm.setDateofBirth((EMicroUtils.display(rs12.getDate("date_of_birth"))));
			joiningForm.setBirthplace(rs12.getString("birth_place"));
			joiningForm.setCountryofBirth(rs12.getString("country_of_birth"));
			joiningForm.setCaste(rs12.getString("caste"));
			joiningForm.setReligiousDenomination(rs12.getString("religous_denomination"));
			joiningForm.setNationality(rs12.getString("nationality"));
			joiningForm.setTelephoneNumber(rs12.getString("telephone_no"));
			joiningForm.setMobileNumber(rs12.getString("mobile_no"));
			joiningForm.setFaxNumber(rs12.getString("fax_no"));
			joiningForm.setEmailAddress(rs12.getString("email_address"));
			
			joiningForm.setBloodGroup(rs12.getString("blood_group"));
			joiningForm.setPermanentAccountNumber(rs12.getString("permanent_acno"));
			joiningForm.setPassportNumber(rs12.getString("passport_no"));
			
			joiningForm.setPlaceofIssueofPassport(rs12.getString("place_of_issue_of_passport"));
			String issuePassportDate=rs12.getString("date_of_issue_of_passp");
	
			if(!(issuePassportDate.equalsIgnoreCase(""))) {
				
			
					joiningForm.setDateofissueofPassport((EMicroUtils.display(rs12.getDate("date_of_issue_of_passp"))));
				
			}else{
				joiningForm.setDateofissueofPassport("");
			}
			String expiryDate=rs12.getString("date_of_expiry_of_passport");
			if(!(expiryDate.equalsIgnoreCase(""))){
			
				joiningForm.setDateofexpiryofPassport((EMicroUtils.display(rs12.getDate("date_of_expiry_of_passport"))));
				
			}else{
				joiningForm.setDateofexpiryofPassport("");
			}
			joiningForm.setPersonalIdentificationMarks(rs12.getString("personal_identification_mark"));
			joiningForm.setPhysicallyChallenged(rs12.getString("physiaclly_challenged"));
			joiningForm.setPhysicallyChallengeddetails(rs12.getString("physically_challenged_details"));
			joiningForm.setEmergencyContactPersonName(rs12.getString("emergency_contact_person_name"));
			joiningForm.setEmergencyContactAddressLine1(rs12.getString("emegency_contact_addressline1"));
			joiningForm.setEmergencyContactAddressLine2(rs12.getString("emegency_contact_addressline2"));
			joiningForm.setEmergencyMobileNumber(rs12.getString("emegency_mobile_number"));
			joiningForm.setEmergencyTelephoneNumber(rs12.getString("emegency_telephone_number"));
			
			joiningForm.setEmergencyContactPersonName1(rs12.getString("emergency_contact_person_name1"));
			joiningForm.setEmergencyContactAddressLine3(rs12.getString("emegency_contact_addressline3"));
			joiningForm.setEmergencyContactAddressLine4(rs12.getString("emegency_contact_addressline4"));
			joiningForm.setEmergencyMobileNumber1(rs12.getString("emegency_mobile_number1"));
			joiningForm.setEmergencyTelephoneNumber1(rs12.getString("emegency_telephone_number1"));
			
			joiningForm.setSalaryCurrency(rs12.getString("WAERS"));
			joiningForm.setPaymentMethod(rs12.getString("PAYMENT_METHOD"));	
			joiningForm.setBranchName(rs12.getString("BRANCH"));
			joiningForm.setBankName(rs12.getString("BANKID"));
			joiningForm.setAccountType(rs12.getString("BACCTYP"));
			joiningForm.setAccountNumber(rs12.getString("BACCNO"));			
			joiningForm.setIfsCCode(rs12.getString("IFSC_CODE"));
			joiningForm.setMicrCode(rs12.getString("MICR_CODE"));
			
			joiningForm.setNoOfChildrens(rs12.getString("number_of_childrens"));
			joiningForm.setWebsite(rs12.getString("website"));
			
			
		}
		request.setAttribute("personalDetails", "personalDetails");
		
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
	
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		/*request.setAttribute(parameter, parameter);*/
		
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		
			return mapping.findForward("display1");
		}
	public ActionForward saveAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
				
		
		
		HttpSession session=request.getSession();	
		String parameter=request.getParameter("param");	
		
		request.setAttribute(parameter, parameter);
		
		 String addressType=joiningForm.getAddressType();
		 String careofcontactname=joiningForm.getCareofcontactname();
		 String houseNumber=joiningForm.getHouseNumber();
		 String addressLine1=joiningForm.getAddressLine1();
		 String addressLine2=joiningForm.getAddressLine2();
		 String addressLine3=joiningForm.getAddressLine3();
		 String postalCode=joiningForm.getPostalCode();
		 String city=joiningForm.getCity();
		 String state=joiningForm.getState();
		 String country=joiningForm.getCountry();
		 String ownAccomodation=joiningForm.getOwnAccomodation();
		 String comapnayHousing=joiningForm.getCompanyHousing();
		 String startDate=joiningForm.getAddStartDate();
		 String endDate=joiningForm.getAddEndDate();
		 
					
	
		try{
			UserInfo userId=(UserInfo)session.getAttribute("user");
			String module=request.getParameter("id"); 
			if(userId==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			userId.getId();
		
		
			System.out.println("userId"+userId.getId());
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			int a=0;
			String sql4="select COUNT(*) from join_emp_address_approve where sl_no="+module+" and address_type="+addressType+"";
		    ResultSet rs12 = ad.selectQuery(sql4); 
		    while (rs12.next()) {
			
				  a=rs12.getInt(1);
			} 
			if(a==0)
			{
			 String insertAddress = "insert into " +
				"join_emp_address_approve(employee_no,address_type,care_of_contact_name, house_no, address_line1," +
				" address_line2, address_line3, postal_code, a_city, a_state, " +
				"a_country, own_accomodation,company_housing,user_id,Data_Status,sl_no) values('"+ userId.getEmployeeNo()+ "','"+ addressType+ "','"
			+ careofcontactname+ "','"+ houseNumber+ "','"+ addressLine1+ "','"+ addressLine2+ "','"
			+ addressLine3+ "','"+ postalCode+ "','"+ city+ "','"+ state+ "','"+ country+ "','"
			+ ownAccomodation+ "','"+ comapnayHousing+ "','"+ userId.getUserName()+ "','SAVE','"+module+"')";
		
		System.out.println("SQL OUTPUT*********======="+insertAddress);
		int numAddInfo = statement1.executeUpdate(insertAddress);
			
			if (numAddInfo > 0) {
				joiningForm.setMessage1("");
				joiningForm.setMessage(" Address Details Submitted Successfully");
				request.setAttribute("addressAdd", "addressAdd");
				clearAddress(mapping, form, request, response);
				
			}
				else
				{
					joiningForm.setMessage("");
					joiningForm.setMessage1("Error While Adding  Address Details .. Please check Entered Values");
					request.setAttribute("addressAdd", "addressAdd");
					
				}
			
			}
			else
			{
				joiningForm.setMessage("");
				joiningForm.setMessage1("You have already added the record..please check");
				request.setAttribute("addressAdd", "addressAdd");
			}
			
			ArrayList list = new ArrayList();
			String sql3="select * from join_emp_address_approve where sl_no='"+module+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningReportForm();
				joiningForm.setNewid(rs11.getString("id"));
				    joiningForm.setAddressType(rs11.getString("address_type"));
				    joiningForm.setCareofcontactname(rs11.getString("care_of_contact_name"));
					joiningForm.setHouseNumber(rs11.getString("house_no"));
					joiningForm.setAddressLine1(rs11.getString("address_line1"));
					joiningForm.setAddressLine2(rs11.getString("address_line2"));
					joiningForm.setAddressLine3(rs11.getString("address_line3"));
					joiningForm.setPostalCode(rs11.getString("postal_code"));
					joiningForm.setCity(rs11.getString("a_city"));
					joiningForm.setState(rs11.getString("a_state"));
					joiningForm.setCountry(rs11.getString("a_country"));
					joiningForm.setOwnAccomodation(rs11.getString("own_accomodation"));
					joiningForm.setCompanyHousing(rs11.getString("company_housing"));
					
				    list.add(joiningForm);
			}
			request.setAttribute("listName", list);
			
			ResultSet rs4 = ad.selectQuery("select BLAND,BEZEI from State ");
			ArrayList stateList=new ArrayList();
			ArrayList stateLabelList=new ArrayList();
			
			while(rs4.next()) {
				stateList.add(rs4.getString("BLAND"));
				stateLabelList.add(rs4.getString("BEZEI"));
			}
			joiningForm.setStateList(stateList);
			joiningForm.setStateLabelList(stateLabelList);
		
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("display1");
	}
	public ActionForward saveFamilyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		HttpSession session1=request.getSession();
		HttpSession session=request.getSession();	
		String parameter=request.getParameter("param");		
		request.setAttribute(parameter, parameter);
		 
	     String familyDependentTypeID=joiningForm.getFamilyDependentTypeID();
		 String ftitle=joiningForm.getFtitle();
		 String ffirstName=joiningForm.getFfirstName();
		 String fmiddleName=joiningForm.getFmiddleName();
		 String flastName=joiningForm.getFlastName();
		 String finitials=joiningForm.getFinitials();
		 String fgender=joiningForm.getFgender();
		 String dob=joiningForm.getFdateofBirth();
		 String fdateofBirth="";
		 if(!dob.equalsIgnoreCase(""))
		  fdateofBirth=EMicroUtils.dateConvert(joiningForm.getFdateofBirth());
		 String fbirthplace=joiningForm.getFbirthplace();
		 String ftelephoneNumber=joiningForm.getFtelephoneNumber();
		 String fmobileNumber=joiningForm.getFmobileNumber();
		 String femailAddress=joiningForm.getFemailAddress();
		 String fbloodGroup=joiningForm.getFbloodGroup();
		 String fdependent=joiningForm.getFdependent();
		 String femployeeofCompany=joiningForm.getFemployeeofCompany();
		 String femployeeNumber=joiningForm.getFemployeeNumber();
		 String fnominee=joiningForm.getFnominee();
					
	
		try{
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			String module=request.getParameter("id"); 
			if(userId==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			
			System.out.println("userId"+userId.getId());
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			///check nomineee
			
			/*if(fnominee.equalsIgnoreCase("Yes"))
			{
			String countn="select * from join_emp_family_details_Approve where sl_no="+module+" and fnominee='Yes'";
			ResultSet cf=ad.selectQuery(countn);
			if(cf.next())
			{
				joiningForm.setMessage("");
				joiningForm.setMessage1("Nominee can be only one person");
				request.setAttribute("addFamily", "addFamily");
				return mapping.findForward("display1");
			}
			}*/
			if(true)
			{
			
			
			int a=0;
			if(familyDependentTypeID.equalsIgnoreCase("1")||familyDependentTypeID.equalsIgnoreCase("2")){
				//query
				ArrayList list = new ArrayList();
		    String sql4="select COUNT(*) from join_emp_family_details_Approve where sl_no="+module+" and family_dependent_type_id="+familyDependentTypeID+"";
		    ResultSet rs12 = ad.selectQuery(sql4); 
		    while (rs12.next()) {
			
				  a=rs12.getInt(1);
			}
		    request.setAttribute("listName", list);
			}
			if(a==0){
				
				 if(joiningForm.getFemailAddress()==null)
				 {
					 joiningForm.setFemailAddress("");
				 }
				 if(!joiningForm.getFemailAddress().equalsIgnoreCase(""))
				 {
				   if(joiningForm.getFemailAddress().contains("@"))
				   {
					String to[]=joiningForm.getFemailAddress().split("@");
					String todomain=to[1];
					
					if(JoiningReportAction.getNS(todomain)==false)
					{
						request.setAttribute("addFamily", "addFamily");
						joiningForm.setMessage1("Invalid Email Id");
						return mapping.findForward("display1");
					}
				   }
				   else
				   {
						request.setAttribute("addFamily", "addFamily");
					   joiningForm.setMessage1("Invalid Email Id");
						return mapping.findForward("display1");
				   }
				 }
			String insertFamilyDetails = "insert into join_emp_family_details_Approve" +
			"(employee_no,family_dependent_type_id, f_title, f_first_name, f_middle_name, f_last_name," +
			" f_gender, f_date_of_birth, f_birth_place, f_telephone_no, f_mobile_no, f_email," +
			" f_blood_group, dependent, employee_of_company, employee_no_family, " +
			"f_initials,user_id,Data_Status,fnominee,sl_no) values('"+ userId.getEmployeeNo()+ "','"
		+ familyDependentTypeID+ "','"+ ftitle+ "','"+ ffirstName+ "','"+ fmiddleName+ "','"
		+ flastName+ "','"+ fgender+ "','"+ fdateofBirth+ "','"+ fbirthplace+ "','"+ ftelephoneNumber
		+ "','"+ fmobileNumber+ "','"+ femailAddress+ "','"+ fbloodGroup+ "','"+ fdependent
		+ "','"+ femployeeofCompany+ "','"+ femployeeNumber+ "','"+ finitials+ "','"+ userId.getUserName()+ "','SAVE','"+fnominee+"','"+module+"')";
		

	int numFamilyInfo = statement1.executeUpdate(insertFamilyDetails);

	
	
	if (numFamilyInfo > 0) {
		joiningForm.setMessage1("");
		joiningForm.setMessage("Family Details Submitted Successfully");
		request.setAttribute("addFamily", "addFamily");
		clearFamilyDetails(mapping, form, request, response);
	}
	else
	{
		joiningForm.setMessage("");
		joiningForm.setMessage1("Error While Adding Family Details .. Please check Entered Values");
		request.setAttribute("addFamily", "addFamily");
		
	}
			}else{
				joiningForm.setMessage("");
				joiningForm.setMessage1("You have already added the record..please check");
				request.setAttribute("addFamily", "addFamily");
			}
			}
		ArrayList list = new ArrayList();
		String sql3="select * from join_emp_family_details_Approve where sl_no='"+module+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningReportForm();
			joiningForm.setNewid(rs11.getString("id"));
		     joiningForm.setFamilyDependentTypeID(rs11.getString("family_dependent_type_id"));
		     joiningForm.setFtitle(rs11.getString("f_title"));
			 joiningForm.setFfirstName(rs11.getString("f_first_name"));
		     joiningForm.setFmiddleName(rs11.getString("f_middle_name"));
			 joiningForm.setFlastName(rs11.getString("f_last_name"));
			 joiningForm.setFinitials(rs11.getString("f_initials"));
			 joiningForm.setFgender(rs11.getString("f_gender"));
			 joiningForm.setFdateofBirth((EMicroUtils.display(rs11.getDate("f_date_of_birth"))));
		 	 joiningForm.setFbirthplace(rs11.getString("f_birth_place"));
			 joiningForm.setFtelephoneNumber(rs11.getString("f_telephone_no"));
			 joiningForm.setFmobileNumber(rs11.getString("f_mobile_no"));
			 joiningForm.setFemailAddress(rs11.getString("f_email"));
			 joiningForm.setFbloodGroup(rs11.getString("f_blood_group"));
			 joiningForm.setFdependent(rs11.getString("dependent"));
			 joiningForm.setFemployeeofCompany(rs11.getString("employee_of_company"));
			 joiningForm.setFemployeeNumber(rs11.getString("employee_no_family"));
			 joiningForm.setFnominee(rs11.getString("fnominee"));
			    list.add(joiningForm);
		}
		request.setAttribute("listName", list);
		
		ResultSet rs9 = ad.selectQuery("select * from RELATIONSHIP order by RELATIONSHIP ");
		ArrayList relationIDList=new ArrayList();
		ArrayList relationValueList=new ArrayList();
		
		while(rs9.next()) {
			relationIDList.add(rs9.getString("Id"));
			relationValueList.add(rs9.getString("RELATIONSHIP"));
		}
		joiningForm.setRelationIDList(relationIDList);
		joiningForm.setRelationValueList(relationValueList);
									 
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("display1");
	}
	public ActionForward saveEducationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		HttpSession session1=request.getSession();
		HttpSession session=request.getSession();	
		String parameter=request.getParameter("param");		
		request.setAttribute(parameter, parameter);
		 String educationalLevel=joiningForm.getEducationalLevel();
		 String qualification=joiningForm.getQualification();
		 String specialization=joiningForm.getSpecialization();
		 String universityName=joiningForm.getUniversityName();
		 String univerysityLocation=joiningForm.getUniverysityLocation();
		 String edstate=joiningForm.getEdstate();
		 String edcountry=joiningForm.getEdcountry();
		 String durationofCourse=joiningForm.getDurationofCourse();
		 String time=joiningForm.getTimes();
		 
	/*	 String fromdate=joiningForm.getFromDate();
		 String from="";
		 if(!fromdate.equalsIgnoreCase(""))
		 from=EMicroUtils.dateConvert(joiningForm.getFromDate());
		 String todate=joiningForm.getToDate();
		 String to="";
		 if(!todate.equalsIgnoreCase(""))
		  to=EMicroUtils.dateConvert(joiningForm.getToDate());*/
		 String yearofpassing=joiningForm.getYearofpassing();
		 String fullTimePartTime=joiningForm.getFullTimePartTime();
		 String percentage=joiningForm.getPercentage();
		 try
		 {
			 
		 ResultSet rs9 = ad.selectQuery("select * from Country ");
			ArrayList countryList=new ArrayList();
			ArrayList countryLabelList=new ArrayList();
			
			while(rs9.next()) {
				countryList.add(rs9.getString("LAND1"));
				countryLabelList.add(rs9.getString("LANDX"));
			}
			joiningForm.setCountryList(countryList);
			joiningForm.setCountryLabelList(countryLabelList);
			
			ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL  ");
			ArrayList eduIDList=new ArrayList();
			ArrayList eduValueList=new ArrayList();
			
			while(rsEdu.next()) {
				eduIDList.add(rsEdu.getString("Id"));
				eduValueList.add(rsEdu.getString("Education_Level"));
			}
			joiningForm.setEduIDList(eduIDList);
			joiningForm.setEduValueList(eduValueList);
			
			ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION ");
			ArrayList qulificationID=new ArrayList();
			ArrayList qulificationValueList=new ArrayList();
			
			while(rsQulif.next()) {
				qulificationID.add(rsQulif.getString("Id"));
				qulificationValueList.add(rsQulif.getString("Qualification"));
			}
			joiningForm.setQulificationID(qulificationID);
			joiningForm.setQulificationValueList(qulificationValueList);
			
			ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY ");
			ArrayList industyID=new ArrayList();
			ArrayList industyValueList=new ArrayList();
			
			while(rsIndustry.next()) {
				industyID.add(rsIndustry.getString("Id"));
				industyValueList.add(rsIndustry.getString("Ind_Desc"));
			}
			joiningForm.setIndustyID(industyID);
			joiningForm.setIndustyValueList(industyValueList);
		
			 
			 UserInfo userId=(UserInfo)session.getAttribute("user");
			 String module=request.getParameter("id"); 
				if(userId==null){
					request.setAttribute("message","Session Expried! Try to Login again!");
					return mapping.findForward("displayiFrameSession");
				}
			userId.getId();
		
		
			System.out.println("userId"+userId.getId());
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			 int count=0;
			 String getqual=joiningForm.getQualification();
			 String data ="select count(*) From join_emp_education_details_Approve where qualification='"+getqual+"' and sl_no='"+module+"'";
			 ResultSet rs22 = ad.selectQuery(data);
				while (rs22.next()) 
				{
					 count=rs22.getInt(1);
				}
			
				if(count==0){
			String insertEducationDetails = "insert into join_emp_education_details_Approve" +
			"(employee_no,education_level, qualification,specialization, univarsity_name, " +
			"university_location, e_state, e_country, duration_of_course,from_date, " +
			"to_date, fulltime_parttime, percentage,user_id,Data_Status,year_of_passing,sl_no)values('"+ userId.getEmployeeNo()+ "','"
		+ educationalLevel+ "','"+ qualification+ "','"+specialization+"','"+ universityName
		+ "','"+ univerysityLocation+ "','"+ edstate+ "','"+ edcountry+ "','"+ durationofCourse
		+ "','','','"+ fullTimePartTime+ "','"+ percentage
		+ "','"+ userId.getUserName()+ "','SAVE','"+yearofpassing+"','"+module+"')";

	int numEducationInfo = statement1.executeUpdate(insertEducationDetails);
		
	System.out.println("insertEducationDetails"+insertEducationDetails);
	
		if (numEducationInfo > 0) {
			joiningForm.setMessage1("");
			joiningForm.setMessage("Education Details Submitted Successfully");
			request.setAttribute("addEducation", "addEducation");
			clearEducationDetails(mapping, form, request, response);
			
		}
		else
		{
			joiningForm.setMessage("");
			joiningForm.setMessage1("Error While Adding  Education Details .. Please check Entered Values");
			request.setAttribute("addEducation", "addEducation");
			
		}
		clearEducationDetails(mapping,form,request,response);			
		ArrayList list = new ArrayList();
		String sql3="select * from join_emp_education_details_Approve where sl_no='"+module+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningReportForm();
			joiningForm.setNewid(rs11.getString("id"));
				joiningForm.setEducationalLevel(rs11.getString("education_level"));
				joiningForm.setQualification(rs11.getString("qualification"));
				joiningForm.setSpecialization(rs11.getString("specialization"));
				joiningForm.setUniversityName(rs11.getString("univarsity_name"));
				joiningForm.setUniverysityLocation(rs11.getString("university_location"));
				joiningForm.setEdstate(rs11.getString("e_state"));
				joiningForm.setEdcountry(rs11.getString("e_country"));
				joiningForm.setDurationofCourse(rs11.getString("duration_of_course"));
				joiningForm.setTimes(rs11.getString("time"));
				joiningForm.setFromDate((EMicroUtils.display(rs11.getDate("from_date"))));
				joiningForm.setToDate((EMicroUtils.display(rs11.getDate("to_date"))));
				joiningForm.setFullTimePartTime(rs11.getString("fulltime_parttime"));
				joiningForm.setPercentage(rs11.getString("percentage"));
				String fileName="";
				String doc="select * from join_emp_education_documents where sl_no='"+rs11.getString("sl_no")+"' and education='"+rs11.getString("qualification")+"'";
				ResultSet rs12 = ad.selectQuery(doc);
				while(rs12.next()){
					fileName=rs12.getString("file_name");
				joiningForm.setEmpEduDoc(rs12.getString("file_name"));
			
				request.setAttribute("edudoc", "edudoc");
				}
				if(fileName.equalsIgnoreCase(""))
				{
					joiningForm.setEmpEduDoc("");
				}
				
			    list.add(joiningForm);
		}
		request.setAttribute("listName", list);
		
										 
				}
				else
				{
					joiningForm.setMessage("");
					joiningForm.setMessage1("This qualification already Exists ..Please choose another");
					request.setAttribute("addEducation", "addEducation");
				}
		 
		 }catch(SQLException se){
				se.printStackTrace();
			}
			return mapping.findForward("display1");
		}
	public ActionForward saveExpirienceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		HttpSession session1=request.getSession();
		HttpSession session=request.getSession();	
		String parameter=request.getParameter("param");		
		request.setAttribute(parameter, parameter);
		 String  nameOfEmployer=joiningForm.getNameOfEmployer();
		 String  industry=joiningForm.getIndustry();
		 String  exCity=joiningForm.getExCity();
		 String  excountry=joiningForm.getExcountry();
		 String  positionHeld=joiningForm.getPositionHeld();
		 String  jobRole=joiningForm.getJobRole();		
		String  startDate=joiningForm.getStartDate();	
		String a[]=startDate.split("/");		
		  startDate=a[1]+"-"+a[0]+"-"+"01";
		 String  endDate=joiningForm.getEndDate();
		 String b[]=endDate.split("/");	
		 endDate=b[1]+"-"+b[0]+"-"+"01";
		 try
		 {
			 
			 ResultSet rs9 = ad.selectQuery("select * from Country  ");
				ArrayList countryList=new ArrayList();
				ArrayList countryLabelList=new ArrayList();
				
				while(rs9.next()) {
					countryList.add(rs9.getString("LAND1"));
					countryLabelList.add(rs9.getString("LANDX"));
				}
				joiningForm.setCountryList(countryList);
				joiningForm.setCountryLabelList(countryLabelList);
				
				ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY");
				ArrayList industyID=new ArrayList();
				ArrayList industyValueList=new ArrayList();
				
				while(rsIndustry.next()) {
					industyID.add(rsIndustry.getString("Id"));
					industyValueList.add(rsIndustry.getString("Ind_Desc"));
				}
				joiningForm.setIndustyID(industyID);
				joiningForm.setIndustyValueList(industyValueList);
			 
			 UserInfo userId=(UserInfo)session.getAttribute("user");
			 String module=request.getParameter("id"); 
				if(userId==null){
					request.setAttribute("message","Session Expried! Try to Login again!");
					return mapping.findForward("displayiFrameSession");
				}
			userId.getId();
		
		
			System.out.println("userId"+userId.getId());
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			
			String microExp=joiningForm.getMicroExp();
			
			String insertExperience = "insert into join_emp_experience_details_Approve" +
			"(employee_no,name_of_employer, industry, ex_city, ex_country, position_held, job_role," +
			" start_date, end_date,user_id,Data_Status,MiciroExp,MicroNo,sl_no) values('"+ userId.getEmployeeNo()+ "','"
		+ nameOfEmployer+ "','"+ industry+ "','"+ exCity+ "','"+ excountry+ "','"+ positionHeld
		+ "','"+ jobRole+ "','"+ startDate+ "','"+ endDate+ "','"+ userId.getUserName()+ "','SAVE','"+microExp+"','"+joiningForm.getMicroNo()+"','"+module+"')";
		
			int numExperienceInfo = statement1.executeUpdate(insertExperience);
			
	
	System.out.println("insertExperience"+insertExperience);
		if (numExperienceInfo > 0) {
			joiningForm.setMessage1("");
			joiningForm.setMessage("Expirience  Details Submitted Successfully");
			request.setAttribute("addExperience", "addExperience");
			clearExperienceDetails(mapping, form, request, response);
		}
		else
		{
			joiningForm.setMessage("");
			joiningForm.setMessage1("Error While Adding Expirience Details .. Please check Entered Values");
			request.setAttribute("addExperience", "addExperience");
			
		}
		clearExperienceDetails(mapping,form,request,response);			
		ArrayList list = new ArrayList();
		String sql3="select * from join_emp_experience_details_Approve where sl_no='"+module+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningReportForm();
			joiningForm.setNewid(rs11.getString("id"));
				joiningForm.setNameOfEmployer(rs11.getString("name_of_employer"));
				joiningForm.setIndustry(rs11.getString("industry"));
				 joiningForm.setExCity(rs11.getString("ex_city"));
				joiningForm.setExcountry(rs11.getString("ex_country"));
				 joiningForm.setPositionHeld(rs11.getString("position_held"));
				joiningForm.setJobRole(rs11.getString("job_role"));
				joiningForm.setStartDate((EMicroUtils.display(rs11.getDate("start_date"))));
				joiningForm.setEndDate((EMicroUtils.display(rs11.getDate("end_date"))));
			    list.add(joiningForm);
		}
		request.setAttribute("listName", list);
	
									 
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("display1");
	}
	public ActionForward saveLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;// TODO Auto-generated method stub
		
		HttpSession session1=request.getSession();
		HttpSession session=request.getSession();	
		String parameter=request.getParameter("param");		
		request.setAttribute(parameter, parameter);
		 String  language=joiningForm.getLanguage();
		 String  motherTongue=joiningForm.getMotherTongue();	
	     String  langSpeaking=joiningForm.getLangSpeaking();
	     String  langRead=joiningForm.getLangRead();
		 String  langWrite=joiningForm.getLangWrite();
		 String  langstartDate=joiningForm.getLangstartDate();
		 String  langendDate=joiningForm.getLangendDate();
		 try
		 {
			 ResultSet rsLang = ad.selectQuery("select * from LANGUAGE ");
				ArrayList langID=new ArrayList();
				ArrayList langValueList=new ArrayList();
			
				while(rsLang.next()) {
					langID.add(rsLang.getString("Id"));
					langValueList.add(rsLang.getString("Language"));
				}
				joiningForm.setLanguageID(langID);
				joiningForm.setLanguageValueList(langValueList);
			 UserInfo userId=(UserInfo)session.getAttribute("user");
			 String module=request.getParameter("id"); 
				if(userId==null){
					request.setAttribute("message","Session Expried! Try to Login again!");
					return mapping.findForward("displayiFrameSession");
				}
			userId.getId();
	
			System.out.println("userId"+userId.getId());
			Connection connection=ConnectionFactory.getConnection();
			Statement statement1=connection.createStatement();
			
			int a=0;
			
				//query
				ArrayList list = new ArrayList();
		    String sql5="select COUNT(*) from join_emp_language_details_Approve where sl_no='"+module+"' and language='"+ language+ "'";
		    ResultSet rs13 = ad.selectQuery(sql5);
		    while (rs13.next()) {
				
				  a=rs13.getInt(1);
			
		    request.setAttribute("listName", list);
			}
			if(a==0){
		   
			String insertLanguage = "insert into join_emp_language_details_Approve" +
			"(employee_no,language, mother_tongue,spoken,reading,writing,user_id,Data_Status,sl_no)" +
			" values('"+ userId.getEmployeeNo()+ "','"
		+ language+ "','"+ motherTongue+ "','"+ langSpeaking+ "','"+ langRead+ "','"+ langWrite+ "','"+ userId.getUserName()+ "','SAVE','"+module+"')";
	
	int numLangInfo = statement1.executeUpdate(insertLanguage);
	System.out.println("insertLanguage"+insertLanguage);
	//insertPersonalInfo+="\n"+insertAddress+insertFamilyDetails+insertEducationDetails+insertExperience+insertLanguage;

		if (numLangInfo > 0) {
			joiningForm.setMessage1("");
			joiningForm.setMessage("Language Details Submitted Successfully");
			request.setAttribute("addLanguage", "addLanguage");
			clearLanguageDetails(mapping, form, request, response);
		}
		else
		{
			joiningForm.setMessage("");
			joiningForm.setMessage1("Error While Adding Language Details .. Please check Entered Values");
			request.setAttribute("addLanguage", "addLanguage");
		}}
		else
		{
			joiningForm.setMessage("");
			joiningForm.setMessage1("You have already added the record..please check");
			request.setAttribute("addLanguage", "addLanguage");
		}
		
		clearLanguageDetails(mapping,form,request,response);			
		 list = new ArrayList();
		String sql3="select * from join_emp_language_details_Approve where sl_no='"+module+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
	
		while (rs11.next()) {
			joiningForm = new JoiningReportForm();
			joiningForm.setNewid(rs11.getString("id"));
				joiningForm.setLanguage(rs11.getString("language"));
				joiningForm.setMotherTongue(rs11.getString("mother_tongue"));	
			    joiningForm.setLangSpeaking(rs11.getString("spoken"));
			    joiningForm.setLangRead(rs11.getString("reading"));
				joiningForm.setLangWrite(rs11.getString("writing"));
				joiningForm.setLangstartDate((EMicroUtils.display(rs11.getDate("l_start_date"))));
				joiningForm.setLangendDate((EMicroUtils.display(rs11.getDate("l_end_date"))));
			    list.add(joiningForm);
		}
		request.setAttribute("listName", list);
										 
			}catch(SQLException se){
				se.printStackTrace();
			}
			return mapping.findForward("display1");
		}
			
		
	public ActionForward clearAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;
		
			
			joiningForm.setCareofcontactname("");
			joiningForm.setHouseNumber("");
			joiningForm.setAddressLine1("");
			joiningForm.setAddressLine2("");
			joiningForm.setAddressLine3("");
			joiningForm.setPostalCode("");
			joiningForm.setCity("");
			joiningForm.setState("");
			joiningForm.setCountry("");
			joiningForm.setOwnAccomodation("");
			joiningForm.setCompanyHousing("");
			joiningForm.setAddStartDate("");
			joiningForm.setAddEndDate("");
			return null;
	}
	public ActionForward clearFamilyDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;
		 joiningForm.setFtitle("");
		 joiningForm.setFfirstName("");
		 joiningForm.setFmiddleName("");
		 joiningForm.setFlastName("");
		 joiningForm.setFinitials("");
		 joiningForm.setFgender("");
		 joiningForm.setFdateofBirth("");
		 joiningForm.setFbirthplace("");
		 joiningForm.setFtelephoneNumber("");
		 joiningForm.setFmobileNumber("");
		 joiningForm.setFemailAddress("");
		 joiningForm.setFbloodGroup("");
		 joiningForm.setFdependent("");
		 joiningForm.setFemployeeofCompany("");
		 joiningForm.setFemployeeNumber("");
		 
			return null;
	}
	public ActionForward clearEducationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningReportForm joiningForm = (JoiningReportForm) form;
		joiningForm.setEducationalLevel("");
		joiningForm.setQualification("");
		joiningForm.setSpecialization("");
		joiningForm.setUniversityName("");
		joiningForm.setUniverysityLocation("");
		joiningForm.setEdstate("");
		joiningForm.setEdcountry("");
		joiningForm.setDurationofCourse("");
		joiningForm.setTimes("");
		joiningForm.setFromDate("");
		joiningForm.setToDate("");
		joiningForm.setFullTimePartTime("");
		joiningForm.setPercentage("");
		joiningForm.setYearofpassing("");
		 
		 return null;
	}
	public ActionForward clearExperienceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
			 JoiningReportForm joiningForm = (JoiningReportForm) form;
			 joiningForm.setNameOfEmployer("");
			 joiningForm.setIndustry("");
			 joiningForm.setExCity("");
			 joiningForm.setExcountry("");
			 joiningForm.setPositionHeld("");
			 joiningForm.setJobRole("");
			 joiningForm.setStartDate("");
			 joiningForm.setEndDate("");
		 return null;
	}
				
		 public ActionForward clearLanguageDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 JoiningReportForm joiningForm = (JoiningReportForm) form;
		 
		 joiningForm.setLanguage("");
		 joiningForm.setMotherTongue("");
		 joiningForm.setLangstartDate("");
		 joiningForm.setLangSpeaking("");
		 joiningForm.setLangRead("");
		 joiningForm.setLangWrite("");
		 joiningForm.setLangendDate("");
		 
		 return null;
	}
		 public ActionForward submit(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response) {
				JoiningReportForm joiningForm = (JoiningReportForm) form;
				try{
				HttpSession session=request.getSession();
				
				UserInfo user=(UserInfo)session.getAttribute("user");
				
										
				int countPersonalInfo=0;
				int countEmpAddress=0;
				int countEmpEdu=0;
				int countFamilyDetails=0;
				int countExp=0;
				int countEmpLang=0;
				int chkstatus=0;
				
				String userType=user.getUserType();
				
				String chk="select COUNT(*) from All_Request where Requester_Name='"+user.getEmployeeNo()+"' and Req_Type='Join Personal Information' and Req_Status='Pending' ";
				ResultSet rs11=ad.selectQuery(chk);
				while(rs11.next()){
					chkstatus=rs11.getInt(1);			
				}
				if(chkstatus>0)
				{
					
				   display1(mapping, form, request, response);
				   
				   joiningForm.setMessage1("Personal information already submitted");
					request.setAttribute("personalDetails", "personalDetails");
					return mapping.findForward("display1");
				}
				
				savePersonalInfo(mapping, joiningForm, request, response);		
				
				
				///check duplicates
				//PAN no
				
				String pan="select * from join_emp_personal_info_Approve where panno='"+joiningForm.getPanno()+"' and employee_no!='"+user.getUserName()+"'";
				ResultSet rspan=ad.selectQuery(pan);
				if(rspan.next())
				{
					joiningForm.setMessage1("This PAN No. Already Exists Kindly Check");
					joiningForm.setMessage("");
					request.setAttribute("personalDetails", "personalDetails");
					return mapping.findForward("display1");
				}
				
				//mobileno
				String mob="select * from join_emp_personal_info_Approve where mobile_no='"+joiningForm.getMobileNumber()+"' and employee_no!='"+user.getUserName()+"'";
				ResultSet rsmob=ad.selectQuery(mob);
				if(rsmob.next())
				{
					joiningForm.setMessage1("This Mob No. Already Exists Kindly Check");
					joiningForm.setMessage("");
					request.setAttribute("personalDetails", "personalDetails");
					return mapping.findForward("display1");
				}
				
		        //eamil
				String email="select * from join_emp_personal_info_Approve where email_address='"+joiningForm.getEmailAddress()+"' and employee_no!='"+user.getUserName()+"'";
				ResultSet rsemail=ad.selectQuery(email);
				if(rsemail.next())
				{
					joiningForm.setMessage1("This Email Address Already Exists Kindly Check");
					joiningForm.setMessage("");
					request.setAttribute("personalDetails", "personalDetails");
					return mapping.findForward("display1");
				}
						
						
				//aadhar
				String aadhar="select * from join_emp_personal_info_Approve where adharno='"+joiningForm.getAdharno()+"' and employee_no!='"+user.getUserName()+"'";
				ResultSet rsadar=ad.selectQuery(aadhar);
				if(rsadar.next())
				{
					joiningForm.setMessage1("This Aadhar No. Already Exists Kindly Check");
					joiningForm.setMessage("");
					request.setAttribute("personalDetails", "personalDetails");
					return mapping.findForward("display1");
				}
				
				
				if(true)
				{
				String checkPersonalInfo="select count(user_id) from join_emp_personal_info_approve where user_id='"+user.getUserName()+"' ";
				ResultSet rs=ad.selectQuery(checkPersonalInfo);
				while(rs.next()){
					countPersonalInfo=rs.getInt(1);			
				}
				String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where user_id='"+user.getUserName()+"' ";
				ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				
				String checkFamilyDetails="select count(user_id) from join_emp_family_details_approve where user_id='"+user.getUserName()+"'";
				ResultSet rs2=ad.selectQuery(checkFamilyDetails);
				while(rs2.next()){
					countFamilyDetails=rs2.getInt(1);			
				}
				String checkEduDetails="select count(user_id) from join_emp_education_details_approve where user_id='"+user.getUserName()+"'  ";
				ResultSet rs3=ad.selectQuery(checkEduDetails);
				while(rs3.next()){
					countEmpEdu=rs3.getInt(1);			
				}
				String checkExpDetails="select count(user_id) from join_emp_experience_details_approve where user_id='"+user.getUserName()+"'  ";
				ResultSet rs4=ad.selectQuery(checkExpDetails);
				while(rs4.next()){
					countExp=rs4.getInt(1);			
				}
				String checkEmpLang="select count(user_id) from join_emp_language_details_approve where user_id='"+user.getUserName()+"'  ";
				ResultSet rs5=ad.selectQuery(checkEmpLang);
				while(rs5.next()){
					countEmpLang=rs5.getInt(1);			
				}
				}
				
				//chck addres wheter mailing and permanent address are entered
				if(countEmpAddress>0)
				{
					int mail=0;
					int perm=0;
					String checkjoin_emp_address="select count(user_id) from join_emp_address_approve where user_id='"+user.getUserName()+"' and address_type='003' ";
					ResultSet rs1=ad.selectQuery(checkjoin_emp_address);
					while(rs1.next()){
						mail=rs1.getInt(1);			
					}
					
					String checkjoinperm="select count(user_id) from join_emp_address_approve where user_id='"+user.getUserName()+"' and address_type='001' ";
					ResultSet rsperm=ad.selectQuery(checkjoinperm);
					while(rsperm.next()){
						perm=rsperm.getInt(1);			
					}
					
					
					if(mail==0)
					{

						joiningForm.setMessage("Please Enter mailing Address Details");
						joiningForm.setMessage1("");
						
						request.setAttribute("personalDetails", "");
						String parameter="addressDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addressAdd", "addressAdd");
						joiningForm.setAddressStatus("Save");
						
						return mapping.findForward("display1");
					
					}
					if(perm==0)
					{

						joiningForm.setMessage("Please Enter Permanent Address Details");
						joiningForm.setMessage1("");
						
						request.setAttribute("personalDetails", "");
						String parameter="addressDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addressAdd", "addressAdd");
						joiningForm.setAddressStatus("Save");
						
						return mapping.findForward("display1");
					
					}
				}
				
				
				if(countPersonalInfo>0&&countEmpAddress>0&&countFamilyDetails>0&&countEmpEdu>0&&countExp>0&&countEmpLang>0)
				{
					
					String updatepersonalinfo="update join_emp_personal_info_approve set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					
					ad.SqlExecuteUpdate(updatepersonalinfo);
					String updateaddressinfo="update join_emp_address_approve set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					
					ad.SqlExecuteUpdate(updateaddressinfo);
					
					String updatefamilyinfo="update join_emp_family_details_approve set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updatefamilyinfo);
					
					String updateeducationinfo="update join_emp_education_details_approve set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updateeducationinfo);

					
					String updateexperienceinfo="update join_emp_experience_details_approve set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updateexperienceinfo);
					
					String updatelangauageinfo="update join_emp_language_details_approve set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updatelangauageinfo);
					
					session.setAttribute("status","Employee's  Details Submitted Successfully");
					
			
					//All request
					
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
				String dateNow = ft.format(dNow);
				
				String pApprover=user.getEmployeeNo();
			int Id=0;
			
			 /*	String getApprovers="select approver from JOIN_PERSONAL_INFO_APPROVERS where location='"+ user.getPlantId()+ "' and priority=1";
		  		ResultSet rsAppr=ad.selectQuery(getApprovers);
		  		while(rsAppr.next())
		  		{
		  				  				 				  		
		  			pApprover=rsAppr.getString("approver");
		  			}*/
		  		
		  		String reqid="select COUNT(*) from All_Request where Requester_Name='"+ user.getEmployeeNo()+ "' and Req_Type='Join Personal Information' ";
		  		ResultSet rs=ad.selectQuery(reqid);
		  		while(rs.next())
		  		{
		  			Id=rs.getInt(1);
		  		}
		  		
		  		if(Id>0)
		  		{
		  			Id=1;
		  			int i=0;
			  		
			  		String deleteallrequest="delete  from All_Request where Requester_Name='"+user.getEmployeeNo()+"' and Req_Type='Join Personal Information' ";
					i=ad.SqlExecuteUpdate(deleteallrequest);
		  		}
		  		else
		  		{
		  			Id=1;
		  		}
		  		int reqNo=0;
		  	String maxreqno="select MAX(Req_Id) From All_Request where  Req_Type='Join Personal Information' ";
		  							
		  	ResultSet rs1=ad.selectQuery(maxreqno);
	  		while(rs1.next())
	  		{
	  			reqNo=rs1.getInt(1);
	  			reqNo=reqNo+1;
	  		}
					String data="insert into All_Request (Req_Id,Req_Type ,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id,type)" +
						"values('"+reqNo+ "','Join Personal Information','"+ user.getEmployeeNo()+ "','"+ dateNow+ "','Pending','No','"+ pApprover+ "'," +
						"'No','"+  user.getEmployeeNo()+ "','Join Personal Information')";
					 ad.SqlExecuteUpdate(data);
					
					 
					System.out.println("user type*****="+userType);

					
							 
						
						
						String getEmpInformation="select * from join_emp_personal_info_approve where user_id='"+user.getUserName()+"'";
						ResultSet rs22=ad.selectQuery(getEmpInformation);
						while(rs22.next())
						{
							
							
							joiningForm = new JoiningReportForm();
							joiningForm.setFirstName(rs22.getString("first_name"));
							joiningForm.setMiddleName(rs22.getString("middle_name"));
							joiningForm.setLastName(rs22.getString("last_name"));
							joiningForm.setMobileNumber(rs22.getString("mobile_no"));
							joiningForm.setEmailAddress(rs22.getString("email_address"));
							joiningForm.setTitle(rs22.getString("title"));
							joiningForm.setFirstName(rs22.getString("first_name"));
							joiningForm.setMiddleName(rs22.getString("middle_name"));
							joiningForm.setLastName(rs22.getString("last_name"));
							joiningForm.setInitials(rs22.getString("initials"));
							joiningForm.setNickName(rs22.getString("nick_name"));
							joiningForm.setGender(rs22.getString("gender"));
							joiningForm.setMaritalStatus(rs22.getString("marital_status"));
							joiningForm.setDateofBirth((EMicroUtils.display(rs22.getDate("date_of_birth"))));
							joiningForm.setBirthplace(rs22.getString("birth_place"));
							joiningForm.setCountryofBirth(rs22.getString("country_of_birth"));
							joiningForm.setCaste(rs22.getString("caste"));
							joiningForm.setReligiousDenomination(rs22.getString("religous_denomination"));
							joiningForm.setNationality(rs22.getString("nationality"));
							joiningForm.setTelephoneNumber(rs22.getString("telephone_no"));
							joiningForm.setMobileNumber(rs22.getString("mobile_no"));
							joiningForm.setFaxNumber(rs22.getString("fax_no"));
							joiningForm.setEmailAddress(rs22.getString("email_address"));
							
							joiningForm.setBloodGroup(rs22.getString("blood_group"));
							joiningForm.setPermanentAccountNumber(rs22.getString("permanent_acno"));
							joiningForm.setPassportNumber(rs22.getString("passport_no"));
							
							joiningForm.setPlaceofIssueofPassport(rs22.getString("place_of_issue_of_passport"));
							joiningForm.setDateofissueofPassport((EMicroUtils.display(rs22.getDate("date_of_issue_of_passp"))));
							joiningForm.setDateofexpiryofPassport((EMicroUtils.display(rs22.getDate("date_of_expiry_of_passport"))));
							joiningForm.setPersonalIdentificationMarks(rs22.getString("personal_identification_mark"));
							joiningForm.setPhysicallyChallenged(rs22.getString("physiaclly_challenged"));
							joiningForm.setPhysicallyChallengeddetails(rs22.getString("physically_challenged_details"));
							joiningForm.setEmergencyContactPersonName(rs22.getString("emergency_contact_person_name"));
							joiningForm.setEmergencyContactAddressLine1(rs22.getString("emegency_contact_addressline1"));
							joiningForm.setEmergencyContactAddressLine2(rs22.getString("emegency_contact_addressline2"));
							joiningForm.setEmergencyMobileNumber(rs22.getString("emegency_mobile_number"));
							joiningForm.setEmergencyTelephoneNumber(rs22.getString("emegency_telephone_number"));
							joiningForm.setNoOfChildrens(rs22.getString("number_of_childrens"));
							joiningForm.setWebsite(rs22.getString("website"));
							
							
						}
						request.setAttribute("personalDetails", "personalDetails");
						
						joiningForm.setMessage("Personal Information Details Submitted Successfully");
						joiningForm.setMessage1("");
					
						
					
					
					
			
					
				}
				else
				{
					
					if(countEmpLang==0)
					{
						joiningForm.setMessage("Please Enter  Language Details");
						joiningForm.setMessage1("");
						
						String parameter="languageDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addLanguage", "addLanguage");
						request.setAttribute("personalDetails", "");
						 ResultSet rsLang = ad.selectQuery("select * from LANGUAGE ");
							ArrayList langID=new ArrayList();
							ArrayList langValueList=new ArrayList();
						
							while(rsLang.next()) {
								langID.add(rsLang.getString("Id"));
								langValueList.add(rsLang.getString("Language"));
							}
							joiningForm.setLanguageID(langID);
							joiningForm.setLanguageValueList(langValueList);
						return mapping.findForward("display1");
					}
					if(countExp==0)
					{
						joiningForm.setMessage("Please Enter  Experience Details ");
						joiningForm.setMessage1("");
						request.setAttribute("personalDetails", "");
						String parameter="experienceDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addExperience", "addExperience");
						 ResultSet rs9 = ad.selectQuery("select * from Country  ");
							ArrayList countryList=new ArrayList();
							ArrayList countryLabelList=new ArrayList();
							
							while(rs9.next()) {
								countryList.add(rs9.getString("LAND1"));
								countryLabelList.add(rs9.getString("LANDX"));
							}
							joiningForm.setCountryList(countryList);
							joiningForm.setCountryLabelList(countryLabelList);
							
							ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY");
							ArrayList industyID=new ArrayList();
							ArrayList industyValueList=new ArrayList();
							
							while(rsIndustry.next()) {
								industyID.add(rsIndustry.getString("Id"));
								industyValueList.add(rsIndustry.getString("Ind_Desc"));
							}
							joiningForm.setIndustyID(industyID);
							joiningForm.setIndustyValueList(industyValueList);
						return mapping.findForward("display1");
					}
					if(countEmpEdu==0)
					{
						joiningForm.setMessage("Please Enter  Education Details");
						joiningForm.setMessage1("");
						request.setAttribute("personalDetails", "");
						String parameter="educationDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addEducation", "addEducation");
					
						
						ResultSet rs9 = ad.selectQuery("select * from Country  ");
						ArrayList countryList=new ArrayList();
						ArrayList countryLabelList=new ArrayList();
						
						while(rs9.next()) {
							countryList.add(rs9.getString("LAND1"));
							countryLabelList.add(rs9.getString("LANDX"));
						}
						joiningForm.setCountryList(countryList);
						joiningForm.setCountryLabelList(countryLabelList);
						joiningForm.setEducationStatus("Save");
						
						ResultSet rsEdu = ad.selectQuery("select * from EDUCATIONAL_LEVEL  ");
						ArrayList eduIDList=new ArrayList();
						ArrayList eduValueList=new ArrayList();
						
						while(rsEdu.next()) {
							eduIDList.add(rsEdu.getString("Id"));
							eduValueList.add(rsEdu.getString("Education_Level"));
						}
						joiningForm.setEduIDList(eduIDList);
						joiningForm.setEduValueList(eduValueList);
						
						ResultSet rsQulif = ad.selectQuery("select * from QUALIFICATION ");
						ArrayList qulificationID=new ArrayList();
						ArrayList qulificationValueList=new ArrayList();
						
						while(rsQulif.next()) {
							qulificationID.add(rsQulif.getString("Id"));
							qulificationValueList.add(rsQulif.getString("Qualification"));
						}
						joiningForm.setQulificationID(qulificationID);
						joiningForm.setQulificationValueList(qulificationValueList);
						
						ResultSet rsIndustry = ad.selectQuery("select * from INDUSTRY ");
						ArrayList industyID=new ArrayList();
						ArrayList industyValueList=new ArrayList();
						
						while(rsIndustry.next()) {
							industyID.add(rsIndustry.getString("Id"));
							industyValueList.add(rsIndustry.getString("Ind_Desc"));
						}
						joiningForm.setIndustyID(industyID);
						joiningForm.setIndustyValueList(industyValueList);
						joiningForm.setEducationStatus("Save");
						return mapping.findForward("display1");
					}
					if(countFamilyDetails==0)
					{
						joiningForm.setMessage("Please Enter  Family Details");
						joiningForm.setMessage1("");
						request.setAttribute("personalDetails", "");
						String parameter="familyDetails";	
						request.setAttribute(parameter, parameter);
						ResultSet rs9 = ad.selectQuery("select * from RELATIONSHIP order by RELATIONSHIP ");
						ArrayList relationIDList=new ArrayList();
						ArrayList relationValueList=new ArrayList();
						
						while(rs9.next()) {
							relationIDList.add(rs9.getString("Id"));
							relationValueList.add(rs9.getString("RELATIONSHIP"));
						}
						joiningForm.setRelationIDList(relationIDList);
						joiningForm.setRelationValueList(relationValueList);
						
						request.setAttribute("addFamily", "addFamily");
						return mapping.findForward("display1");
					}
					if(countEmpAddress==0)
					{
						joiningForm.setMessage("Please Enter  Address Details");
						joiningForm.setMessage1("");
						request.setAttribute("personalDetails", "");
						
						String parameter="addressDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addressAdd", "addressAdd");
						joiningForm.setAddressStatus("Save");
						
						return mapping.findForward("display1");
					}
					if(countPersonalInfo==0)
					{
						joiningForm.setMessage("Please Enter  Personal  Details");
						joiningForm.setMessage1("");
						String parameter="personalDetails";	
						request.setAttribute(parameter, parameter);
						//joiningForm.setper("Save");
						
						ResultSet rs9 = ad.selectQuery("select * from Country  ");
						ArrayList countryList=new ArrayList();
						ArrayList countryLabelList=new ArrayList();
						
						while(rs9.next()) {
							countryList.add(rs9.getString("LAND1"));
							countryLabelList.add(rs9.getString("LANDX"));
						}
						joiningForm.setCountryList(countryList);
						joiningForm.setCountryLabelList(countryLabelList);
						
						return mapping.findForward("display1");
					}
					
					
				}
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				//display1(mapping, form, request, response);
				return mapping.findForward("display1");
			}
	
}
