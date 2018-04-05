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
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Hashtable;

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

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.JoiningFormalityForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;



public class JoiningFormalityAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		
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
			String checkFile="select count(*) from emp_education_documents where file_name='"+fileName+"' and education='"+qualification+"'";
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
			 String filePath = getServlet().getServletContext().getRealPath("ESS/EducationDocuments");
			 
			 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
		 	 Properties props = new Properties();
		 	props.load(in);
			in.close();
		 	 String uploadFilePath=props.getProperty("file.uploadFilePath");
		 	 filePath=uploadFilePath+"/EMicro Files/ESS/EducationDocuments";
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
		
			File oldfile =new File(uploadFilePath+"/EMicro Files/ESS/EducationDocuments/"+fileName);
			
	
	
			
			
			//upload files in another path
			
			try{
				String filePath1 = "E:/EMicro Files/ESS/EducationDocuments";
				
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
			String checkEmpExist="select count(*) from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+qualification+"'";
			int empNoAvailable=0;
			ResultSet rsCheckEmpExist=ad.selectQuery(checkEmpExist);
			while(rsCheckEmpExist.next())
			{
				empNoAvailable=rsCheckEmpExist.getInt(1);
			}
			String sql="";
        
			
			
			if(empNoAvailable==0){
			sql="insert into emp_education_documents(emp_no,education,file_name) values('"+userId.getEmployeeNo()+"','"+qualification+"','"+fileName+"')";
			
			}else{
			 sql="update emp_education_documents set file_name='"+fileName+"' where emp_no='"+userId.getEmployeeNo()+"' and education='"+qualification+"'";	
			}
			int a = ad.SqlExecuteUpdate(sql);
		
			if (a > 0) {
				joiningForm.setMessage1("");
				joiningForm.setMessage("Documents Uploaded Successfully");
			}else{
				joiningForm.setMessage1("");
				joiningForm.setMessage("Error  ... Please check ");
			}

			try{
			ArrayList list = new ArrayList();
			String sql3="select * from emp_education_documents where  emp_no='"+userId.getEmployeeNo()+"' ";
			ResultSet rs12 = ad.selectQuery(sql3);
			while (rs12.next())
			{
				joiningForm.setEmpEduDoc(fileName);
				
			}
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
	    	 
			JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			    int filesize=myProduct.getFileSize();
				if(ext.equalsIgnoreCase("jpg") && (filesize<1048576)) 
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
					joiningForm.setMessage1("Please Upload images Only with .jpg extension files with size less than 1Mb");
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
		
	String sql3="select * from emp_language_details where user_id='"+userId.getUserName()+"' and id='"+reqID+"'";
	ResultSet rs11 = ad.selectQuery(sql3);
	while (rs11.next()) {
		 joiningForm.setId(rs11.getString("id"));
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
		
		return mapping.findForward("display1");	
	}
	
	public ActionForward editExprience(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
		
		
		String sql3="select * from emp_experience_details where user_id='"+userId.getUserName()+"' and id='"+expID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {					
			 joiningForm.setId(rs11.getString("id"));
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
	
	return mapping.findForward("display1");	
	
	}
	
	public ActionForward editEducation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			
			
			
			String sql3="select * from emp_education_details where id='"+eduID+"' and user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				
				joiningForm.setId(rs11.getString("id"));
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
				String doc="select * from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+rs11.getString("qualification")+"'";
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
		
		return mapping.findForward("display1");	
	}
	
	
	public ActionForward editFamily(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String reqID=request.getParameter("familyID");
		joiningForm.setReqFamilyID(reqID);
		
		String sql3="select * from emp_family_details where user_id='"+userId.getUserName()+"' and id='"+reqID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		try{
			while(rs11.next()){
			 joiningForm.setId(rs11.getString("id"));
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
		
		return mapping.findForward("display1");
	}
	
	public ActionForward editAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		String reqID=request.getParameter("addressID");
		joiningForm.setReqAddressID(reqID);
		
		String sql3="select * from emp_address where user_id='"+userId.getUserName()+"' and id='"+reqID+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		try{
		while (rs11.next()) {
			
			    joiningForm.setId(rs11.getString("id"));
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
		
		return mapping.findForward("display1");
	}
	
	
	
	public ActionForward getEduStaes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			request.setAttribute(parameter, parameter);
			
			HttpSession session=request.getSession();
			UserInfo userId=(UserInfo)session.getAttribute("user");
			
			String educationStatus=joiningForm.getEducationStatus();
			if(educationStatus.equalsIgnoreCase("save"))
			{
				ArrayList list = new ArrayList();
				String sql3="select * from emp_education_details where user_id='"+userId.getUserName()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					joiningForm = new JoiningFormalityForm();
					 joiningForm.setId(rs11.getString("id"));
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
					String doc="select * from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+rs11.getString("qualification")+"'";
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
		return mapping.findForward("display1");
	}
	public ActionForward displayState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			request.setAttribute(parameter, parameter);
			
			String addressStatus=joiningForm.getAddressStatus();
			if(addressStatus.equalsIgnoreCase("Save")){
				request.setAttribute("addressAdd", "addressAdd");
				int countEmpAddress=0;
				HttpSession session=request.getSession();	
				UserInfo userId=(UserInfo)session.getAttribute("user");
				String checkemp_address="select count(user_id) from emp_address where user_id='"+userId.getUserName()+"' ";
				ResultSet rs1=ad.selectQuery(checkemp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				if(countEmpAddress>0)
				{
		ArrayList list = new ArrayList();
		String sql3="select * from emp_address where user_id='"+userId.getUserName()+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningFormalityForm();
			    joiningForm.setId(rs11.getString("id"));
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
		
		return mapping.findForward("display1");
	}
	
	public ActionForward deletAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
			String type=request.getParameter("param2");
			System.out.println("tye="+type);
			String parameter="addressDetails";
			request.setAttribute(parameter, parameter);
			String reqAddressID=joiningForm.getReqAddressID();
			 
			 HttpSession session=request.getSession();	
				UserInfo userId=(UserInfo)session.getAttribute("user");
				
				String deleteAddress="delete from emp_address where user_id='"+userId.getUserName()+"' and id='"+reqAddressID+"'";
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
					String checkemp_address="select count(user_id) from emp_address where user_id='"+userId.getUserName()+"' ";
					ResultSet rs1=ad.selectQuery(checkemp_address);
					while(rs1.next()){
						countEmpAddress=rs1.getInt(1);			
					}
					if(countEmpAddress>0)
					{
				ArrayList list = new ArrayList();
				String sql3="select * from emp_address where user_id='"+userId.getUserName()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					joiningForm = new JoiningFormalityForm();
					    joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		
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
				"emp_address(employee_no,address_type,care_of_contact_name, house_no, address_line1," +
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
	
			String updateAddress="update emp_address set address_type='"+addressType+"',care_of_contact_name='"+careofcontactname+"', house_no='"+houseNumber+"', address_line1='"+addressLine1+"'," +
				" address_line2='"+addressLine2+"', address_line3='"+addressLine3+"', postal_code='"+postalCode+"', a_city='"+city+"', a_state='"+state+"', " +
				"a_country='"+country+"', own_accomodation='"+ownAccomodation+"',company_housing='"+comapnayHousing+"' where user_id='"+userId.getUserName()+"' and id='"+reqAddressID+"' ";
			int a=0;
			a=ad.SqlExecuteUpdate(updateAddress);
			if(a>0)
			{
				joiningForm.setMessage("Employee Address Details updated Successfully");
				request.setAttribute("addressAdd", "addressAdd");
				clearAddress(mapping, form, request, response);
			}
			else
			{
				joiningForm.setMessage1("Employee Address Details are not updated.Please Check....");
				request.setAttribute("modifyAddress", "modifyAddress");
			}
			
			
			
			
			    /*if(type.equalsIgnoreCase("delete")){
			    	for(int i=0;i<addressCheckLength;i++)
					{
			    		String deleteAddress="delete from emp_address where user_id='"+userId.getUserName()+"' and id='"+addressCheck[i]+"'";
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
			String checkemp_address="select count(user_id) from emp_address where user_id='"+userId.getUserName()+"' ";
			ResultSet rs1=ad.selectQuery(checkemp_address);
			while(rs1.next()){
				countEmpAddress=rs1.getInt(1);			
			}
			if(countEmpAddress>0)
			{
			String sql3="select * from emp_address where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				    joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="familyDetails";
			String type=request.getParameter("param2");
			request.setAttribute(parameter, parameter);
			
			String reqID=joiningForm.getReqFamilyID();
			
			HttpSession session=request.getSession();	
			UserInfo userId=(UserInfo)session.getAttribute("user");
			
			String deleteAddress="delete from emp_family_details where user_id='"+userId.getUserName()+"' and id='"+reqID+"'";
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
			String sql3="select * from emp_family_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
				
				if(JoiningFormalityAction.getNS(todomain)==false)
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
			String updateFamily="update emp_family_details set family_dependent_type_id='"+familyDependentTypeID+"',f_title='"+ftitle+"', f_first_name='"+ffirstName+"', f_middle_name='"+fmiddleName+"'," +
			" f_last_name='"+flastName+"', f_initials='"+finitials+"', f_gender='"+fgender+"', f_date_of_birth='"+fdateofBirth+"', f_birth_place='"+fbirthplace+"', " +
			"f_telephone_no='"+ftelephoneNumber+"', f_mobile_no='"+fmobileNumber+"',f_email='"+femailAddress+"',f_blood_group='"+fbloodGroup+"',dependent='"+fdependent+"',employee_of_company='"+femployeeofCompany+"',employee_no_family='"+femployeeNumber+"' where user_id='"+userId.getUserName()+"' and id='"+reqID+"'";
			System.out.println("updateFamily="+updateFamily);
			int a=0;
			a=ad.SqlExecuteUpdate(updateFamily);
			
			 if(a>0)
				{
				 joiningForm.setMessage("Employee Family Details Updated Successfully....");
					request.setAttribute("addFamily", "addFamily");
					clearFamilyDetails(mapping, form, request, response);
				}
				else
				{
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
			    		String deleteFamily="delete from emp_family_details where user_id='"+userId.getUserName()+"' and id='"+familyCheck[i]+"'";
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
			String sql3="select * from emp_family_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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

	public ActionForward deleteEducation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="educationDetails";
			
			
			String reqEduID=request.getParameter("reqEduID");
			request.setAttribute(parameter, parameter);
			HttpSession session=request.getSession();	
			
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			
			String deleteAddress="delete from emp_education_details where user_id='"+userId.getUserName()+"' and id='"+reqEduID+"'";
    		System.out.println("delete education="+deleteAddress);
    		int a=0;
    	 a=ad.SqlExecuteUpdate(deleteAddress);
    	 if(a>0)
			{
    		 joiningForm.setMessage("Employee Education Details Deleted Successfully....");
    		 
    		 String deletedocs="delete from emp_education_documents where emp_no='"+userId.getUserName()+"' and education='"+joiningForm.getQualification()+"'";
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
			String sql3="select * from emp_education_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
					String doc="select * from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+rs11.getString("qualification")+"'";
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		
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
				
				/*String insertEducationDetails = "insert into emp_education_details" +
				"(employee_no,education_level, qualification, univarsity_name, " +
				"university_location, e_state, e_country, duration_of_course,from_date, " +
				"to_date, fulltime_parttime, percentage,user_id,Data_Status)values('"+ userId.getEmployeeNo()+ "','"
			+ educationalLevel+ "','"+ qualification+ "','"+ universityName
			+ "','"+ univerysityLocation+ "','"+ edstate+ "','"+ edcountry+ "','"+ durationofCourse
			+ "','"+ from+ "','"+ to+ "','"+ fullTimePartTime+ "','"+ percentage
			+ "','"+ userId.getUserName()+ "','SAVE')";*/
			
				 String updateEducation="update emp_education_details set education_level='"+educationalLevel+"',qualification='"+qualification+"',specialization='"+specialization+"', univarsity_name='"+universityName+"'," +
	" university_location='"+univerysityLocation+"', e_state='"+edstate+"', e_country='"+edcountry+"', duration_of_course='"+durationofCourse+"', " +
	"from_date='', to_date='',fulltime_parttime='"+fullTimePartTime+"',percentage='"+percentage+"',year_of_passing='"+yearofpassing+"' where user_id='"+userId.getUserName()+"' and id='"+reqEduID+"'";
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
			    		String deleteAddress="delete from emp_education_details where user_id='"+userId.getUserName()+"' and id='"+educationCheck[i]+"'";
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
			String sql3="select * from emp_education_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
					String doc="select * from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+rs11.getString("qualification")+"'";
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			 
			/* String insertExperience = "insert into emp_experience_details" +
				"(employee_no,name_of_employer, industry, ex_city, ex_country, position_held, job_role," +
				" start_date, end_date,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"
			+ nameOfEmployer+ "','"+ industry+ "','"+ exCity+ "','"+ excountry+ "','"+ positionHeld
			+ "','"+ jobRole+ "','"+ startDate+ "','"+ endDate+ "','"+ userId.getUserName()+ "','SAVE')";*/
			 
			 String deleteExperience="delete from emp_experience_details where user_id='"+userId.getUserName()+"' and id='"+reqExpID+"'";
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
				String updateExperience="update emp_experience_details set name_of_employer='"+nameOfEmployer[i]+"',industry='"+industry[i]+"', ex_city='"+exCity[i]+"', ex_country='"+excountry[i]+"'," +
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
			    		String deleteExperience="delete from emp_experience_details where user_id='"+userId.getUserName()+"' and id='"+experienceCheck[i]+"'";
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
			String sql3="select * from emp_experience_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
					 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			 
			/* String insertExperience = "insert into emp_experience_details" +
				"(employee_no,name_of_employer, industry, ex_city, ex_country, position_held, job_role," +
				" start_date, end_date,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"
			+ nameOfEmployer+ "','"+ industry+ "','"+ exCity+ "','"+ excountry+ "','"+ positionHeld
			+ "','"+ jobRole+ "','"+ startDate+ "','"+ endDate+ "','"+ userId.getUserName()+ "','SAVE')";*/
			 String updateExperience="update emp_experience_details set name_of_employer='"+nameOfEmployer+"',industry='"+industry+"', ex_city='"+exCity+"', ex_country='"+excountry+"'," +
				" position_held='"+positionHeld+"', job_role='"+jobRole+"', start_date='"+startDate+"', end_date='"+endDate+"',MiciroExp='"+joiningForm.getMicroExp()+"',MicroNo='"+joiningForm.getMicroNo()+"' where user_id='"+userId.getUserName()+"' and id='"+reqExpID+"'";
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
				String updateExperience="update emp_experience_details set name_of_employer='"+nameOfEmployer[i]+"',industry='"+industry[i]+"', ex_city='"+exCity[i]+"', ex_country='"+excountry[i]+"'," +
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
			    		String deleteExperience="delete from emp_experience_details where user_id='"+userId.getUserName()+"' and id='"+experienceCheck[i]+"'";
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
			String sql3="select * from emp_experience_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
					 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
		joiningForm.setMessage("");
		joiningForm.setMessage1("");
		
			String parameter="languageDetails";
			request.setAttribute(parameter, parameter);
			
			String reqLangID=joiningForm.getReqLangID();
			
			HttpSession session=request.getSession();	
			String[] languageCheck=joiningForm.getSelectLanguage();	
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			String deleteLanguage="delete from emp_language_details where user_id='"+userId.getUserName()+"' and id='"+reqLangID+"'";
	    	 
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
			String sql3="select * from emp_language_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm=(JoiningFormalityForm)form;
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
			 String updateLanguage="update emp_language_details set language='"+language+"',mother_tongue='"+motherTongue+"', spoken='"+langSpeaking+"', reading='"+langSpeaking+"'," +
				" writing='"+langWrite+"' where user_id='"+userId.getUserName()+"' and id='"+reqLangID+"'";
				System.out.println("updateLanguage="+updateLanguage);
				int a=0;
				a=ad.SqlExecuteUpdate(updateLanguage);
				 if(a>0)
					{
					 joiningForm.setMessage("Employee Language Details Updated Successfully....");
					 clearLanguageDetails(mapping, form, request, response);
					 request.setAttribute("addLanguage", "addLanguage");
					}
					else
					{
						joiningForm.setMessage1("Employee Language Details Are Not Updated.Please Check....");
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
			ArrayList list = new ArrayList();
			String sql3="select * from emp_language_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
		
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
	
	
	public ActionForward displayTables(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningFormalityForm joiningForm1 = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		HttpSession session=request.getSession();	
		
		String parameter=request.getParameter("param");
		UserInfo userId=(UserInfo)session.getAttribute("user");
		userId.getId();
		
		joiningForm1.setMessage("");
		joiningForm1.setMessage1("");
		
		try
		{
			if(parameter.equalsIgnoreCase("personalDetails"))
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//get current date time with Date()
		         Date date=new Date();
				System.out.println(dateFormat.format(date));
				String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
				ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
				while (rsEmpPhoto.next())
				{
					joiningForm1.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
				request.setAttribute("employeePhoto", "employeePhoto");	
				}
				String frst="select EMP_FULLNAME,SEX,DOB from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
				
				ResultSet rs = ad.selectQuery(frst);
				while(rs.next())
				{
					JoiningFormalityForm	joiningForm = new JoiningFormalityForm();
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
				}
				String sql3="select * from emp_personal_info where user_id='"+userId.getUserName()+"'";
				System.out.println("*********SQL emp_personal_info joing table"+sql3);
				ResultSet rs12 = ad.selectQuery(sql3);
				while (rs12.next()) {
					JoiningFormalityForm	joiningForm = new JoiningFormalityForm();
					joiningForm.setTitle(rs12.getString("title"));
					
					
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
					/*	String saveStatus=rs12.getString("Data_Status");
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
					
					
				}
				
				
			}
			else if(parameter.equalsIgnoreCase("addressDetails"))
			{
			request.setAttribute("addressAdd", "addressAdd");
			joiningForm1.setAddressStatus("Save");
				int countEmpAddress=0;
				String checkemp_address="select count(user_id) from emp_address where user_id='"+userId.getUserName()+"' ";
				ResultSet rs1=ad.selectQuery(checkemp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				if(countEmpAddress>0)
				{
		ArrayList list = new ArrayList();
		String sql3="select * from emp_address where user_id='"+userId.getUserName()+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			JoiningFormalityForm	joiningForm = new JoiningFormalityForm();
			    joiningForm.setId(rs11.getString("id"));
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
				String sql3="select * from emp_family_details where user_id='"+userId.getUserName()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					
					JoiningFormalityForm joiningForm = new JoiningFormalityForm();
					     joiningForm.setId(rs11.getString("id"));
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
				String sql3="select *  from emp_education_details " +
						" where user_id='"+userId.getEmployeeNo()+"' ";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					JoiningFormalityForm joiningForm = new JoiningFormalityForm();
					joiningForm.setId(rs11.getString("id"));
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
					String doc="select * from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+rs11.getString("qualification")+"'";
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
				String sql3="select * from emp_experience_details where user_id='"+userId.getUserName()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {					
					
					JoiningFormalityForm	joiningForm = new JoiningFormalityForm();
					joiningForm.setId(rs11.getString("id"));
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
			String sql3="select * from emp_language_details where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				JoiningFormalityForm joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
		
		
		
	
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		int countPersonalInfo=0;
		int countEmpAddress=0;
		int countEmpEdu=0;
		int countFamilyDetails=0;
		int countExp=0;
		int countEmpLang=0;
		String checkPersonalInfo="select count(user_id) from emp_personal_info where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs11=ad.selectQuery(checkPersonalInfo);
		while(rs11.next()){
			countPersonalInfo=rs11.getInt(1);			
		}
		String checkemp_address="select count(user_id) from emp_address where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs1=ad.selectQuery(checkemp_address);
		while(rs1.next()){
			countEmpAddress=rs1.getInt(1);			
		}
		
		String checkFamilyDetails="select count(user_id) from emp_family_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs2=ad.selectQuery(checkFamilyDetails);
		while(rs2.next()){
			countFamilyDetails=rs2.getInt(1);			
		}
		String checkEduDetails="select count(user_id) from emp_education_details where user_id='"+user.getUserName()+"'  and Data_Status='SUBMIT'";
		ResultSet rs3=ad.selectQuery(checkEduDetails);
		while(rs3.next()){
			countEmpEdu=rs3.getInt(1);			
		}
		String checkExpDetails="select count(user_id) from emp_education_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs4=ad.selectQuery(checkExpDetails);
		while(rs4.next()){
			countExp=rs4.getInt(1);			
		}
		String checkEmpLang="select count(user_id) from emp_language_details where user_id='"+user.getUserName()+"'  and Data_Status='SUBMIT'";
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
		
		if(countPersonalInfo>0&&countEmpAddress>0&&countFamilyDetails>0&&countEmpEdu>0&&countExp>0&&countEmpLang>0)
		{
			
			UserInfo user1=(UserInfo)session.getAttribute("user");
			
			String userType=user1.getUserType();
			System.out.println("user type*****="+userType);

			
			if(userType.equalsIgnoreCase("temp"))
			{		 System.out.println("temporary");
		
				return mapping.findForward("displaytemp");
			}
			else
				
			{	
		
		return mapping.findForward("display1");
			}
		}
		else
		{
			return mapping.findForward("display1");
		}
		}catch(SQLException se){
			se.printStackTrace();
		}
		return mapping.findForward("display1");
	}
	
	
	
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
		HttpSession session=request.getSession();	
		UserInfo userId=(UserInfo)session.getAttribute("user");
		userId.getId();
		
		
		try{
			joiningForm.setMessage("");
			joiningForm.setMessage1("");
			String frst="select EMP_FULLNAME,SEX,DOB from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
			
			ResultSet rs = ad.selectQuery(frst);
			while(rs.next())
			{
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
			}
		String getEmpInfo="select * from emp_personal_info where user_id='"+userId.getUserName()+"'";
		ResultSet rs12=ad.selectQuery(getEmpInfo);
		while(rs12.next())
		{
			
			joiningForm.setMobileNumber(rs12.getString("mobile_no"));
			joiningForm.setEmailAddress(rs12.getString("email_address"));
			 joiningForm.setTitle(rs12.getString("title"));
		
		
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
				
			
					joiningForm.setDateofissueofPassport(rs12.getString("date_of_issue_of_passp"));
				
			}else{
				joiningForm.setDateofissueofPassport("");
			}
			String expiryDate=rs12.getString("date_of_expiry_of_passport");
			if(!(expiryDate.equalsIgnoreCase(""))){
			
				joiningForm.setDateofexpiryofPassport(rs12.getString("date_of_expiry_of_passport"));
				
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
		
		String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
		ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//get current date time with Date()
         Date date=new Date();
		System.out.println(dateFormat.format(date));
	
		while (rsEmpPhoto.next())
		{
			joiningForm.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
		request.setAttribute("employeePhoto", "employeePhoto");	
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		String parameter="personalDetails";		
		request.setAttribute(parameter, parameter);
		
	return mapping.findForward("display1");
	}
	/*public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
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
				
					 String sqlempinfo="select * from users u,emp_personal_info e where " +
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
						String getEmpInfo="select * from emp_personal_info where user_id='"+userId.getUserName()+"'";
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
		String checkPersonalInfo="select count(user_id) from emp_personal_info where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs11=ad.selectQuery(checkPersonalInfo);
		while(rs11.next()){
			countPersonalInfo=rs11.getInt(1);			
		}
		String checkemp_address="select count(user_id) from emp_address where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs13=ad.selectQuery(checkemp_address);
		while(rs13.next()){
			countEmpAddress=rs13.getInt(1);			
		}
		
		String checkFamilyDetails="select count(user_id) from emp_family_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs2=ad.selectQuery(checkFamilyDetails);
		while(rs2.next()){
			countFamilyDetails=rs2.getInt(1);			
		}
		String checkEduDetails="select count(user_id) from emp_education_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs31=ad.selectQuery(checkEduDetails);
		while(rs31.next()){
			countEmpEdu=rs31.getInt(1);			
		}
		String checkExpDetails="select count(user_id) from emp_education_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT' ";
		ResultSet rs4=ad.selectQuery(checkExpDetails);
		while(rs4.next()){
			countExp=rs4.getInt(1);			
		}
		String checkEmpLang="select count(user_id) from emp_language_details where user_id='"+user.getUserName()+"' and Data_Status='SUBMIT'";
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
				
				String getEmpInformation="select * from emp_personal_info where user_id='"+userId.getUserName()+"'";
				ResultSet rs22=ad.selectQuery(getEmpInformation);
				ArrayList list1 = new ArrayList();
				while(rs22.next())
				{
					
					
					joiningForm=(JoiningFormalityForm) form;
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
		
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
			 
			String selectAddress = "select * from emp_address where user_id='"+userId.getUserName()+"'";
			
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
				String sql3="select * from emp_address where user_id='"+userId.getUserName()+"'";
				ResultSet rs11 = ad.selectQuery(sql3);
				while (rs11.next()) {
					joiningForm = new JoiningFormalityForm();
					    joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
		
		HttpSession session1=request.getSession();
		
		HttpSession session=request.getSession();	
		String parameter=request.getParameter("param");		
		request.setAttribute(parameter, parameter);
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
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		//get current date time with Date()
         Date date=new Date();
		System.out.println(dateFormat.format(date));

		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		System.out.println("Current Date="+dateFormat.format(cal.getTime()));
		String currentDate=dateFormat.format(cal.getTime());
		String frst="select EMP_FULLNAME,SEX,DOB from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
		
		ResultSet rs = ad.selectQuery(frst);
		while(rs.next())
		{
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
		
		
    String checkData="select count(*) from emp_personal_info where user_id='"+userId.getUserName()+"'"  ;
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
		
		if(JoiningFormalityAction.getNS(todomain)==false)
		{
			joiningForm.setMessage1("Invalid Email Id");
			return mapping.findForward("display1");
		}
		   }
		 else
		 {
			 joiningForm.setMessage1("Invalid Email Id");
				return mapping.findForward("display1");
		 }
		
		String insertPersonalInfo="INSERT INTO emp_personal_info(user_id,employee_no,title,first_name,nick_name,gender," +
		"marital_status,birth_place,country_of_birth,caste,religous_denomination,nationality,telephone_no,mobile_no,fax_no,email_address," +
		"website,blood_group,permanent_acno,passport_no,place_of_issue_of_passport,date_of_issue_of_passp,date_of_expiry_of_passport,personal_identification_mark," +
		"physiaclly_challenged,physically_challenged_details,emergency_contact_person_name,emegency_contact_addressline1,emegency_contact_addressline2," +
		"emegency_telephone_number,emegency_mobile_number,number_of_childrens,creation_date,Data_Status,emergency_contact_person_name1,emegency_contact_addressline3,emegency_contact_addressline4," +
		"emegency_telephone_number1,emegency_mobile_number1,emerg_city1,emerg_city2,emerg_state1,emerg_state2,emergCntAdd22,emergCntAdd222) " +
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
		"'"+joiningForm.getEmergencyMobileNumber1()+"','"+joiningForm.getEmergCity1()+"','"+joiningForm.getEmergCity2()+"','"+joiningForm.getEmergState1()+"','"+joiningForm.getEmergState2()+"','"+joiningForm.getEmergCntAdd22()+"','"+joiningForm.getEmergCntAdd222()+"')";
		i=ad.SqlExecuteUpdate(insertPersonalInfo);
	}else{
		
		
		 if(joiningForm.getEmailAddress().contains("@"))
		   {
		String to[]=joiningForm.getEmailAddress().split("@");
		String todomain=to[1];
		
		if(JoiningFormalityAction.getNS(todomain)==false)
		{
			joiningForm.setMessage1("Invalid Email Id");
			return mapping.findForward("display1");
		}
		   }
		 else
		 {
			 joiningForm.setMessage1("Invalid Email Id");
				return mapping.findForward("display1");
		 }
	String updatepersonalinfo="update emp_personal_info set title='"+joiningForm.getTitle()+"'," +
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
		"changed_on='"+currentDate+"',Last_changed_by='"+userId.getId()+"',email_address='"+joiningForm.getEmailAddress()+"',Data_Status='SAVE',emergency_contact_person_name1='"+joiningForm.getEmergencyContactPersonName1()+"'," +
		"emegency_contact_addressline3='"+joiningForm.getEmergCntAdd11()+"',emegency_contact_addressline4='"+joiningForm.getEmergCntAdd111()+"'," +
		"emegency_telephone_number1='"+joiningForm.getEmergencyTelephoneNumber1()+"',emegency_mobile_number1='"+joiningForm.getEmergencyMobileNumber1()+"'," +
			"first_name='"+joiningForm.getFirstName()+"',mobile_no='"+joiningForm.getMobileNumber()+"',emerg_city1='"+joiningForm.getEmergCity1()+"',emerg_city2='"+joiningForm.getEmergCity2()+"',"
		+ "emerg_state1='"+joiningForm.getEmergState1()+"',emerg_state2='"+joiningForm.getEmergState2()+"',emergCntAdd22='"+joiningForm.getEmergCntAdd22()+"',emergCntAdd222='"+joiningForm.getEmergCntAdd222()+"' where user_id='"+userId.getUserName()+"'";
	
	
	System.out.println("updatepersonalinfo Query="+updatepersonalinfo);	
	i=ad.SqlExecuteUpdate(updatepersonalinfo);
	}
		if (i > 0) {
			joiningForm.setMessage("Personal Information Details Saved Successfully");
			
			
		}
		else
		{
			joiningForm.setMessage1("Error While Adding  Personal Information .. Please check Entered Values");
			
			
		}
		
		try{
		
		String getEmpInfo="select * from emp_personal_info where user_id='"+userId.getUserName()+"'";
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
			
			joiningForm.setNoOfChildrens(rs12.getString("number_of_childrens"));
			joiningForm.setWebsite(rs12.getString("website"));
			
			
		}
		
		
		joiningForm.setCountryList(countryList);
		joiningForm.setCountryLabelList(countryLabelList);
		String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
		ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
		while (rsEmpPhoto.next())
		{
			joiningForm.setPhotoImage(rsEmpPhoto.getString("file_name"));
		request.setAttribute("employeePhoto", "employeePhoto");	
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
			
		request.setAttribute(parameter, parameter);
		
	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
		
			return mapping.findForward("display1");
		}
	public ActionForward saveAddress(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
				
		
		
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
			
			 
			 String insertAddress = "insert into " +
				"emp_address(employee_no,address_type,care_of_contact_name, house_no, address_line1," +
				" address_line2, address_line3, postal_code, a_city, a_state, " +
				"a_country, own_accomodation,company_housing,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"+ addressType+ "','"
			+ careofcontactname+ "','"+ houseNumber+ "','"+ addressLine1+ "','"+ addressLine2+ "','"
			+ addressLine3+ "','"+ postalCode+ "','"+ city+ "','"+ state+ "','"+ country+ "','"
			+ ownAccomodation+ "','"+ comapnayHousing+ "','"+ userId.getUserName()+ "','SAVE')";
		
		System.out.println("SQL OUTPUT*********======="+insertAddress);
		int numAddInfo = statement1.executeUpdate(insertAddress);
			
			if (numAddInfo > 0) {
				
				joiningForm.setMessage(" Address Details Submitted Successfully");
				request.setAttribute("addressAdd", "addressAdd");
				clearAddress(mapping, form, request, response);
				
			}
				else
				{
					joiningForm.setMessage1("Error While Adding  Address Details .. Please check Entered Values");
					request.setAttribute("addressAdd", "addressAdd");
					
				}
			
			ArrayList list = new ArrayList();
			String sql3="select * from emp_address where user_id='"+userId.getUserName()+"'";
			ResultSet rs11 = ad.selectQuery(sql3);
			while (rs11.next()) {
				joiningForm = new JoiningFormalityForm();
				    joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
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
			
			int a=0;
			if(familyDependentTypeID.equalsIgnoreCase("1")||familyDependentTypeID.equalsIgnoreCase("2")){
				//query
				ArrayList list = new ArrayList();
		    String sql4="select COUNT(*) from emp_family_details where employee_no="+userId.getEmployeeNo()+ "and family_dependent_type_id="+familyDependentTypeID+"";
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
					
					if(JoiningFormalityAction.getNS(todomain)==false)
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
			String insertFamilyDetails = "insert into emp_family_details" +
			"(employee_no,family_dependent_type_id, f_title, f_first_name, f_middle_name, f_last_name," +
			" f_gender, f_date_of_birth, f_birth_place, f_telephone_no, f_mobile_no, f_email," +
			" f_blood_group, dependent, employee_of_company, employee_no_family, " +
			"f_initials,user_id,Data_Status) values('"+ userId.getEmployeeNo()+ "','"
		+ familyDependentTypeID+ "','"+ ftitle+ "','"+ ffirstName+ "','"+ fmiddleName+ "','"
		+ flastName+ "','"+ fgender+ "','"+ fdateofBirth+ "','"+ fbirthplace+ "','"+ ftelephoneNumber
		+ "','"+ fmobileNumber+ "','"+ femailAddress+ "','"+ fbloodGroup+ "','"+ fdependent
		+ "','"+ femployeeofCompany+ "','"+ femployeeNumber+ "','"+ finitials+ "','"+ userId.getUserName()+ "','SAVE')";
		

	int numFamilyInfo = statement1.executeUpdate(insertFamilyDetails);

	
	
	if (numFamilyInfo > 0) {
		joiningForm.setMessage("Family Details Submitted Successfully");
		request.setAttribute("addFamily", "addFamily");
		clearFamilyDetails(mapping, form, request, response);
	}
	else
	{
		joiningForm.setMessage1("Error While Adding Family Details .. Please check Entered Values");
		request.setAttribute("addFamily", "addFamily");
		
	}
			}else{
				joiningForm.setMessage1("You have already added the record..please check");
				request.setAttribute("addFamily", "addFamily");
			}
		ArrayList list = new ArrayList();
		String sql3="select * from emp_family_details where user_id='"+userId.getUserName()+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningFormalityForm();
			 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
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
			 String data ="select count(*) From emp_education_details where qualification='"+getqual+"' and employee_no='"+userId.getEmployeeNo()+"'";
			 ResultSet rs22 = ad.selectQuery(data);
				while (rs22.next()) 
				{
					 count=rs22.getInt(1);
				}
			
				if(count==0){
			String insertEducationDetails = "insert into emp_education_details" +
			"(employee_no,education_level, qualification,specialization, univarsity_name, " +
			"university_location, e_state, e_country, duration_of_course,from_date, " +
			"to_date, fulltime_parttime, percentage,user_id,Data_Status,year_of_passing)values('"+ userId.getEmployeeNo()+ "','"
		+ educationalLevel+ "','"+ qualification+ "','"+specialization+"','"+ universityName
		+ "','"+ univerysityLocation+ "','"+ edstate+ "','"+ edcountry+ "','"+ durationofCourse
		+ "','','','"+ fullTimePartTime+ "','"+ percentage
		+ "','"+ userId.getUserName()+ "','SAVE','"+yearofpassing+"')";

	int numEducationInfo = statement1.executeUpdate(insertEducationDetails);
		
	System.out.println("insertEducationDetails"+insertEducationDetails);
	
		if (numEducationInfo > 0) {
			joiningForm.setMessage("Education Details Submitted Successfully");
			request.setAttribute("addEducation", "addEducation");
			clearEducationDetails(mapping, form, request, response);
			
		}
		else
		{
			joiningForm.setMessage1("Error While Adding  Education Details .. Please check Entered Values");
			request.setAttribute("addEducation", "addEducation");
			
		}
		clearEducationDetails(mapping,form,request,response);			
		ArrayList list = new ArrayList();
		String sql3="select * from emp_education_details where user_id='"+userId.getUserName()+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningFormalityForm();
			 joiningForm.setId(rs11.getString("id"));
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
				String doc="select * from emp_education_documents where emp_no='"+userId.getEmployeeNo()+"' and education='"+rs11.getString("qualification")+"'";
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
					joiningForm.setMessage("This qualification already Exists ..Please choose another");
					request.setAttribute("addEducation", "addEducation");
				}
		 
		 }catch(SQLException se){
				se.printStackTrace();
			}
			return mapping.findForward("display1");
		}
	public ActionForward saveExpirienceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
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
			
			String insertExperience = "insert into emp_experience_details" +
			"(employee_no,name_of_employer, industry, ex_city, ex_country, position_held, job_role," +
			" start_date, end_date,user_id,Data_Status,MiciroExp,MicroNo) values('"+ userId.getEmployeeNo()+ "','"
		+ nameOfEmployer+ "','"+ industry+ "','"+ exCity+ "','"+ excountry+ "','"+ positionHeld
		+ "','"+ jobRole+ "','"+ startDate+ "','"+ endDate+ "','"+ userId.getUserName()+ "','SAVE','"+microExp+"','"+joiningForm.getMicroNo()+"')";
		
			int numExperienceInfo = statement1.executeUpdate(insertExperience);
			
	
	System.out.println("insertExperience"+insertExperience);
		if (numExperienceInfo > 0) {
			joiningForm.setMessage(" Experience  Details Submitted Successfully");
			request.setAttribute("addExperience", "addExperience");
			clearExperienceDetails(mapping, form, request, response);
		}
		else
		{
			joiningForm.setMessage1("Error While Adding Experience Details .. Please check Entered Values");
			request.setAttribute("addExperience", "addExperience");
			
		}
		clearExperienceDetails(mapping,form,request,response);			
		ArrayList list = new ArrayList();
		String sql3="select * from emp_experience_details where user_id='"+userId.getUserName()+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
		while (rs11.next()) {
			joiningForm = new JoiningFormalityForm();
				 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;// TODO Auto-generated method stub
		
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
			if(language.equalsIgnoreCase("1")||language.equalsIgnoreCase("2")||language.equalsIgnoreCase("3")||language.equalsIgnoreCase("4")||language.equalsIgnoreCase("5")||
			language.equalsIgnoreCase("6")||language.equalsIgnoreCase("7")||language.equalsIgnoreCase("8")||language.equalsIgnoreCase("9")||language.equalsIgnoreCase("10")||
			language.equalsIgnoreCase("11")||language.equalsIgnoreCase("12")||language.equalsIgnoreCase("13")||language.equalsIgnoreCase("14")||language.equalsIgnoreCase("15")
			||language.equalsIgnoreCase("16")||language.equalsIgnoreCase("17")||language.equalsIgnoreCase("18")||language.equalsIgnoreCase("19")||language.equalsIgnoreCase("20")||
			language.equalsIgnoreCase("21")||language.equalsIgnoreCase("22")||language.equalsIgnoreCase("23")||language.equalsIgnoreCase("24")||language.equalsIgnoreCase("24")||language.equalsIgnoreCase("25")
			||language.equalsIgnoreCase("26")||language.equalsIgnoreCase("27")||language.equalsIgnoreCase("28")||language.equalsIgnoreCase("29")||language.equalsIgnoreCase("30"))
			
			{
				//query
				ArrayList list = new ArrayList();
		    String sql5="select COUNT(*) from emp_language_details where employee_no="+userId.getEmployeeNo()+ "and language="+ language+ "";
		    ResultSet rs13 = ad.selectQuery(sql5);
		    while (rs13.next()) {
				
				  a=rs13.getInt(1);
			}
		    request.setAttribute("listName", list);
			}
			if(a==0){
		   
			String insertLanguage = "insert into emp_language_details" +
			"(employee_no,language, mother_tongue,spoken,reading,writing,user_id,Data_Status)" +
			" values('"+ userId.getEmployeeNo()+ "','"
		+ language+ "','"+ motherTongue+ "','"+ langSpeaking+ "','"+ langRead+ "','"+ langWrite+ "','"+ userId.getUserName()+ "','SAVE')";
	
	int numLangInfo = statement1.executeUpdate(insertLanguage);
	System.out.println("insertLanguage"+insertLanguage);
	//insertPersonalInfo+="\n"+insertAddress+insertFamilyDetails+insertEducationDetails+insertExperience+insertLanguage;

		if (numLangInfo > 0) {
			
			joiningForm.setMessage("Language Details Submitted Successfully");
			request.setAttribute("addLanguage", "addLanguage");
			clearLanguageDetails(mapping, form, request, response);
		}
		else
		{
			joiningForm.setMessage1("Error While Adding Language Details .. Please check Entered Values");
			request.setAttribute("addLanguage", "addLanguage");
		}}
		else
		{
			joiningForm.setMessage1("You have already added the record..please check");
			request.setAttribute("addLanguage", "addLanguage");
		}
		
		clearLanguageDetails(mapping,form,request,response);			
		ArrayList list = new ArrayList();
		String sql3="select * from emp_language_details where user_id='"+userId.getUserName()+"'";
		ResultSet rs11 = ad.selectQuery(sql3);
	
		while (rs11.next()) {
			joiningForm = new JoiningFormalityForm();
			 joiningForm.setId(rs11.getString("id"));
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
		
			
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
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
		JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
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
			 JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
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
		 JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
		 
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
				JoiningFormalityForm joiningForm = (JoiningFormalityForm) form;
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
				
				String chk="select COUNT(*) from All_Request where Requester_Name='"+user.getEmployeeNo()+"' and Req_Type='Personal Information' and Req_Status='Pending' ";
				ResultSet rs11=ad.selectQuery(chk);
				while(rs11.next()){
					chkstatus=rs11.getInt(1);			
				}
				if(chkstatus>0)
				{
					
				   display1(mapping, form, request, response);
				   
				   joiningForm.setMessage("Personal information already submitted");
					request.setAttribute("personalDetails", "personalDetails");
					return mapping.findForward("display1");
				}
				
				
				
				if(userType.equalsIgnoreCase("temp"))
				{
				String checkPersonalInfo="select count(user_id) from emp_personal_info where user_id='"+user.getUserName()+"' and Data_Status='SAVE'";
				ResultSet rs=ad.selectQuery(checkPersonalInfo);
				while(rs.next()){
					countPersonalInfo=rs.getInt(1);			
				}
				String checkemp_address="select count(user_id) from emp_address where user_id='"+user.getUserName()+"' and Data_Status='SAVE' ";
				ResultSet rs1=ad.selectQuery(checkemp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				
				String checkFamilyDetails="select count(user_id) from emp_family_details where user_id='"+user.getUserName()+"' and Data_Status='SAVE'";
				ResultSet rs2=ad.selectQuery(checkFamilyDetails);
				while(rs2.next()){
					countFamilyDetails=rs2.getInt(1);			
				}
				String checkEduDetails="select count(user_id) from emp_education_details where user_id='"+user.getUserName()+"' and Data_Status='SAVE' ";
				ResultSet rs3=ad.selectQuery(checkEduDetails);
				while(rs3.next()){
					countEmpEdu=rs3.getInt(1);			
				}
				String checkExpDetails="select count(user_id) from emp_experience_details where user_id='"+user.getUserName()+"' and Data_Status='SAVE' ";
				ResultSet rs4=ad.selectQuery(checkExpDetails);
				while(rs4.next()){
					countExp=rs4.getInt(1);			
				}
				String checkEmpLang="select count(user_id) from emp_language_details where user_id='"+user.getUserName()+"' and Data_Status='SAVE' ";
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
				}
				else
				{
				String checkPersonalInfo="select count(user_id) from emp_personal_info where user_id='"+user.getUserName()+"' ";
				ResultSet rs=ad.selectQuery(checkPersonalInfo);
				while(rs.next()){
					countPersonalInfo=rs.getInt(1);			
				}
				String checkemp_address="select count(user_id) from emp_address where user_id='"+user.getUserName()+"' ";
				ResultSet rs1=ad.selectQuery(checkemp_address);
				while(rs1.next()){
					countEmpAddress=rs1.getInt(1);			
				}
				
				String checkFamilyDetails="select count(user_id) from emp_family_details where user_id='"+user.getUserName()+"'";
				ResultSet rs2=ad.selectQuery(checkFamilyDetails);
				while(rs2.next()){
					countFamilyDetails=rs2.getInt(1);			
				}
				String checkEduDetails="select count(user_id) from emp_education_details where user_id='"+user.getUserName()+"'  ";
				ResultSet rs3=ad.selectQuery(checkEduDetails);
				while(rs3.next()){
					countEmpEdu=rs3.getInt(1);			
				}
				String checkExpDetails="select count(user_id) from emp_experience_details where user_id='"+user.getUserName()+"'  ";
				ResultSet rs4=ad.selectQuery(checkExpDetails);
				while(rs4.next()){
					countExp=rs4.getInt(1);			
				}
				String checkEmpLang="select count(user_id) from emp_language_details where user_id='"+user.getUserName()+"'  ";
				ResultSet rs5=ad.selectQuery(checkEmpLang);
				while(rs5.next()){
					countEmpLang=rs5.getInt(1);			
				}
				}
				if(countPersonalInfo>0&&countEmpAddress>0&&countFamilyDetails>0&&countEmpEdu>0&&countExp>0&&countEmpLang>0)
				{
					
					String updatepersonalinfo="update emp_personal_info set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					
					ad.SqlExecuteUpdate(updatepersonalinfo);
					String updateaddressinfo="update emp_address set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					
					ad.SqlExecuteUpdate(updateaddressinfo);
					
					String updatefamilyinfo="update emp_family_details set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updatefamilyinfo);
					
					String updateeducationinfo="update emp_education_details set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updateeducationinfo);

					
					String updateexperienceinfo="update emp_experience_details set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updateexperienceinfo);
					
					String updatelangauageinfo="update emp_language_details set Data_Status='pending' where user_id='"+user.getUserName()+"' ";
					ad.SqlExecuteUpdate(updatelangauageinfo);
					
					session.setAttribute("status","Employee's  Details Submitted Successfully");
					
					//All request
					
					Date dNow = new Date( );
					 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
				String dateNow = ft.format(dNow);
				
				String pApprover="";
			int Id=0;
			
			 	String getApprovers="select APPMGR from emp_official_info where PERNR='"+ user.getEmployeeNo()+ "'";
		  		ResultSet rsAppr=ad.selectQuery(getApprovers);
		  		while(rsAppr.next())
		  		{
		  				  				 				  		
		  			pApprover=rsAppr.getString("APPMGR");
		  			}
		  		
		  		String reqid="select COUNT(*) from All_Request where Requester_Name='"+ user.getEmployeeNo()+ "' and Req_Type='Personal Information' ";
		  		ResultSet rs=ad.selectQuery(reqid);
		  		while(rs.next())
		  		{
		  			Id=rs.getInt(1);
		  		}
		  		
		  		if(Id>0)
		  		{
		  			Id=1;
		  			int i=0;
			  		
			  		String deleteallrequest="delete  from All_Request where Requester_Name='"+user.getEmployeeNo()+"' and Req_Type='Personal Information' ";
					i=ad.SqlExecuteUpdate(deleteallrequest);
		  		}
		  		else
		  		{
		  			Id=1;
		  		}
		  		int reqNo=0;
		  	String maxreqno="select MAX(Req_Id) From All_Request where  Req_Type='Personal Information' ";
		  							
		  	ResultSet rs1=ad.selectQuery(maxreqno);
	  		while(rs1.next())
	  		{
	  			reqNo=rs1.getInt(1);
	  			reqNo=reqNo+1;
	  		}
					String data="insert into All_Request (Req_Id,Req_Type ,Requester_Name,Req_Date,Req_Status,Last_Approver,Pending_Approver,Approved_Persons,Requester_Id,type)" +
						"values('"+reqNo+ "','Personal Information','"+ user.getEmployeeNo()+ "','"+ dateNow+ "','Pending','No','"+ pApprover+ "'," +
						"'No','"+  user.getEmployeeNo()+ "','Personal Information')";
					 ad.SqlExecuteUpdate(data);
					
					 
					System.out.println("user type*****="+userType);

					
					if(userType.equalsIgnoreCase("temp"))
					{		 
						
						
						String getEmpInformation="select * from emp_personal_info where user_id='"+user.getUserName()+"'";
						ResultSet rs22=ad.selectQuery(getEmpInformation);
						while(rs22.next())
						{
							
							
							joiningForm = new JoiningFormalityForm();
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
						return mapping.findForward("displaytemp");		 
					
					}
					else
					{
						request.setAttribute("personalDetails", "personalDetails");
						return mapping.findForward("display1");
				    }
					
				}
				else
				{
					
					if(countEmpLang==0)
					{
						joiningForm.setMessage("Please Enter  Language Details");
						
						String parameter="languageDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addLanguage", "addLanguage");
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
						
						
						String parameter="addressDetails";	
						request.setAttribute(parameter, parameter);
						request.setAttribute("addressAdd", "addressAdd");
						joiningForm.setAddressStatus("Save");
						
						return mapping.findForward("display1");
					}
					if(countPersonalInfo==0)
					{
						joiningForm.setMessage("Please Enter  Personal  Details");
						String parameter="addressDetails";	
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
