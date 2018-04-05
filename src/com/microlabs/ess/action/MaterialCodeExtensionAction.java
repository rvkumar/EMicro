package com.microlabs.ess.action;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.MaterialCodeExtensionForm;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;

public class MaterialCodeExtensionAction extends DispatchAction{
	EssDao ad=EssDao.dBConnection();
	
	
	public ActionForward viewCodeExtension(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{

		 LinkedList extDetails=new LinkedList();
		 
     String requstNo=request.getParameter("requstNo");
		 MaterialCodeExtensionForm masterForm=new MaterialCodeExtensionForm();
		 String location="";
		 try{
		String matType="";	 
			int requstNo1=Integer.parseInt(request.getParameter("requstNo"));
			String reqtype=request.getParameter("planttype");
			String matDetails="select loc.LOCATION_CODE from code_extenstion_request as mat,Location as loc  "
			+ "where mat.REQUEST_NO='"+requstNo+"' and (mat.EXTENDED_TO_PLANT_1=loc.LOCATION_CODE or mat.PLANT_2=loc.LOCATION_CODE) ";
			ResultSet rsDetails=ad.selectQuery(matDetails);
			while(rsDetails.next())
			{
				location=rsDetails.getString("LOCATION_CODE");
				matType="Code Extention";
			}
		
		   if(reqtype.equalsIgnoreCase("Plant"))
			{
			String getRecord="select REQUEST_NO,REQUEST_DATE,TYPE,PLANT_1,MATERIAL_CODE_1,EXTENDED_TO_PLANT_1,STORAGE_LOCATION_ID_1," +
			"EXTENDED_STORAGE_LOCATION_1 from code_extenstion_request as c where REQUEST_NO='"+requstNo+"' ";
			
			ResultSet rs=ad.selectQuery(getRecord);
			while(rs.next())
			{
				masterForm.setRequestNo(rs.getInt("REQUEST_NO"));
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				masterForm.setRequestDate(reqDate);
				masterForm.setPlantType(rs.getString("TYPE"));
			     masterForm.setMaterialCode1(rs.getString("MATERIAL_CODE_1"));
					masterForm.setPlant1(rs.getString("PLANT_1"));
					masterForm.setStorageLocationId1(rs.getString("STORAGE_LOCATION_ID_1"));
					masterForm.setExtendToPlant1(rs.getString("EXTENDED_TO_PLANT_1"));
					masterForm.setExtendToStorageLocation1(rs.getString("EXTENDED_STORAGE_LOCATION_1"));
				
			}
			request.setAttribute("plantMandatory", "plantMandatory");
			}
		   
		   else{
			   
			  String getRecord=" select c.REQUEST_NO,c.REQUEST_DATE,c.TYPE,c.MATERIAL_CODE_2,c.PLANT_2,c.FROM_STORAGE_LOCATION,c.TO_STORAGE_LOCATION " +
			  		" from code_extenstion_request as c where REQUEST_NO='"+requstNo+"' ";
				
				ResultSet rs=ad.selectQuery(getRecord);
				if(rs.next()){
					
				masterForm.setRequestNo(rs.getInt("REQUEST_NO"));
				String reqDate=rs.getString("REQUEST_DATE");
				String a[]=reqDate.split(" ");
				reqDate=a[0];
				String b[]=reqDate.split("-");
				reqDate=b[2]+"/"+b[1]+"/"+b[0];
				masterForm.setRequestDate(reqDate);
				masterForm.setPlantType(rs.getString("TYPE"));
				masterForm.setPlant2(rs.getString("PLANT_2"));
				masterForm.setMaterialCode2(rs.getString("MATERIAL_CODE_2"));
			    masterForm.setFromStorageLocation(rs.getString("FROM_STORAGE_LOCATION"));
				masterForm.setToStorageLocation(rs.getString("TO_STORAGE_LOCATION"));
		   }
				request.setAttribute("storageMandatory", "storageMandatory");
				}
				
			   extDetails.add(masterForm);
				request.setAttribute("extdetails", extDetails);
				
				int checkStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='Code Extention' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date," +
					"all_r.Comments from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+requstNo+"' and " +
					"mast.Location='"+location+"' AND all_r.type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver " +
					"and (mast.Role='User' or mast.Role='Accounts' or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority," +
					"mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
						}
						String comments="";
						comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") )
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					if(approveStatus.equalsIgnoreCase(""))
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
				
				request.setAttribute("approverDetails", listApprers);
				
		 }catch(Exception e){
			 e.printStackTrace();
		 }
	
		return mapping.findForward("viewCode");
	}
	
	public ActionForward SubmitAllCodeExt(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		Calendar currentDate = Calendar.getInstance();
		  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
		  String dateNow = formatter.format(currentDate.getTime());
	 
		
			String reqNo[]=masterForm.getGetReqno();
			String loctID="";
			  String  type="";
			  String  plant1="";
			  String  plant2="";
			
			for(int j=0;j<reqNo.length;j++)
			{
				try{
					String requestNo=reqNo[j];
					
					  String accountGroup="select TYPE FROM code_extenstion_request where REQUEST_NO='"+requestNo+"'";	
				  
					  ResultSet accountGroupId1=ad.selectQuery(accountGroup);
				  
				   while(accountGroupId1.next()){
					   type=accountGroupId1.getString("TYPE");
		    	 		
		    	 	}
				   
					  String accountGroup2="select EXTENDED_TO_PLANT_1 from code_extenstion_request where REQUEST_NO='"+requestNo+"'";	
					  
					  ResultSet accountGroupId2=ad.selectQuery(accountGroup2);
				  
				   while(accountGroupId2.next()){
					   plant1=accountGroupId2.getString("EXTENDED_TO_PLANT_1");
		    	 		
		    	 	}
				   
	           String accountGroup3="select PLANT_2 from code_extenstion_request where REQUEST_NO='"+requestNo+"'";	
					  
					  ResultSet accountGroupId3=ad.selectQuery(accountGroup3);
				  
				   while(accountGroupId3.next()){
					   plant2=accountGroupId3.getString("PLANT_2");
		    	 		
		    	 	}
				   
				   
				 if(type.equalsIgnoreCase("Plant"))
				 {
					 loctID=plant1;
				
				 }else{
					 loctID=plant2;
					 
				 }
				 
				int checkApprover=0;
				  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='Code Extention' and " +
			  		" Location='"+loctID+"'";
				  ResultSet rsCheck=ad.selectQuery(checkApprovers);
				  while(rsCheck.next())
				  {
					  checkApprover=1;
					  
				  }
				  if(checkApprover==1)
					{

						try{
							int i=0;
							String matType="Code Extention";
								Date dNow = new Date( );
								SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
								dateNow = ft.format(dNow);
							 	String pApprover="";
							 	String parllelAppr1="";
							 	String parllelAppr2="";
					    		String matGroup="";
					    		String location="";
							 
							checkApprover=0;
							 	String pendingApprs="";
							 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Code Extention' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
					    		ResultSet rsAppr=ad.selectQuery(getApprovers);
					    		while(rsAppr.next())
					    		{
					    			checkApprover=1;
					    			if(rsAppr.getInt("Priority")==1){
					    			pApprover=rsAppr.getString("Approver_Id");
					    			parllelAppr1=rsAppr.getString("Parllel_Approver_1");
					    			parllelAppr2=rsAppr.getString("Parllel_Approver_2");
					    			
					    			}
					    			pendingApprs=pendingApprs+rsAppr.getString("EMP_FULLNAME")+" , ";
					    		}
					    		if(!(pendingApprs.equalsIgnoreCase(""))){
					    			int size=pendingApprs.length();
					    			pendingApprs=pendingApprs.substring(0, (size-2));
					    		}
					    		if(!(pApprover.equalsIgnoreCase(""))){
					    		
					    		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
								saveRecReq = saveRecReq + "'"+requestNo+"','Code Extention','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getUserName()+"','','"+matType+"')";
								int ij=ad.SqlExecuteUpdate(saveRecReq);
								if(!(parllelAppr1.equalsIgnoreCase(""))){
									
									
								 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
								 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Code Extention','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getUserName()+"','','"+matType+"')";
								 ad.SqlExecuteUpdate(sendRecParllelAppr1);
								}
								if(!(parllelAppr2.equalsIgnoreCase(""))){
								
									 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
									 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Code Extention','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getUserName()+"','','"+matType+"')";
									 ad.SqlExecuteUpdate(sendRecParllelAppr2);
								}
								
								if(ij>0){
									masterForm.setMassage2("Request No"+requestNo+". Submitted for approval successully.");
									String updateStatus="update code_extenstion_request set APPROVE_TYPE='In Process',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
									System.out.println(updateStatus);
									ad.SqlExecuteUpdate(updateStatus);
									
								}else{
									masterForm.setMassage1("Error while submiting approval...");
								}
					    		}else{
					    			
					    			masterForm.setMassage1("No Approvers are assigned.Please Contact to Admin");
					    		}
							
						
						}catch (Exception e) {
							e.printStackTrace();
						}
						
					
					}
				  
				}catch (Exception e) {
						e.printStackTrace();
					}
				
			}						
			
		
		return mapping.findForward("displayList");	
	}
	
	
	public ActionForward getReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeExtensionForm materialForm=(MaterialCodeExtensionForm)form;
			
			
			int requestNo=Integer.parseInt(request.getParameter("requstNo"));
			
			String sapCrationDate="";
			
			String materialType=request.getParameter("MaterialType");
			String path="";
			if(materialType.equalsIgnoreCase("Plant"))
			{
			 path="CodeExtention_Plant.jrxml";
			 
			
				String getSapCreationDate="select mat.SAP_CREATION_DATE from code_extenstion_request as code," +
						"material_code_request as mat where code.MATERIAL_CODE_1=mat.SAP_CODE_NO and code.REQUEST_NO='"+requestNo+"'";
				ResultSet rsCrationDate=ad.selectQuery(getSapCreationDate);
				try{
					while(rsCrationDate.next())
					{
						sapCrationDate=rsCrationDate.getString(1);
						String a[]=sapCrationDate.split(" ");
						sapCrationDate=a[0];
						String b[]=sapCrationDate.split("-");
						sapCrationDate=b[2]+"/"+b[1]+"/"+b[0];
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				 path="CodeExtention_Storage.jrxml";
				 String getSapCreationDate="select mat.SAP_CREATION_DATE from code_extenstion_request as code," +
					"material_code_request as mat where code.MATERIAL_CODE_2=mat.SAP_CODE_NO and code.REQUEST_NO='"+requestNo+"'";
			ResultSet rsCrationDate=ad.selectQuery(getSapCreationDate);
			try{
				while(rsCrationDate.next())
				{
					sapCrationDate=rsCrationDate.getString(1);
					String a[]=sapCrationDate.split(" ");
					sapCrationDate=a[0];
					String b[]=sapCrationDate.split("-");
					sapCrationDate=b[2]+"/"+b[1]+"/"+b[0];
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			}
			 
		        String output="raw_m1.pdf";
			 	
			    HttpSession session=request.getSession();
			    
				String pat3=getServlet().getServletContext().getRealPath("/jasperReports/"+path);
				String pat_out=getServlet().getServletContext().getRealPath("/jasperReports/"+output);
				System.out.println("path of the JRXML File is *********************"+path);
				System.out.println("pat3 of the JRXML File is *********************"+pat3);
				try {
				String path2="codeExtention.jasper";
				//String path2="MarksCard.jasper";
				String path1=getServlet().getServletContext().getRealPath("/jasperReports/"+path2);
				System.out.println("Path of the JRXML File is *********************"+path1);
				File reportFile=new File(path1);
				Map parameters = new HashMap();
				
				InputStream input = new FileInputStream(new File(pat3));
				
				JasperDesign design = JRXmlLoader.load(input);
				Map param_map = new HashMap();
				Map param_map2 = new HashMap();
				System.out.println(request.getParameter("requstNo"));
				
				param_map.put("REQUEST_NO", requestNo);
				param_map.put("SAPCreation_Date", sapCrationDate);
				param_map.put("PHARMACOP_GRADE", "Rajesh");
				
				
				JasperReport jReport = JasperCompileManager.compileReport(design);
				
				
				
				
				Connection connection=ConnectionFactory.getConnection();
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(jReport, param_map, connection);
				
				
				 List<JasperPrint> jlist=new ArrayList<JasperPrint>();
				 jlist.add(jasperPrint);
				
				//Connection con=ConnectionFactory.getConnection();
				
				
				
				session.setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
				
				 if(jasperPrint!=null)
				  { 
					 //JRHtmlExporterParameter.BETWEEN_PAGES_HTML;
					 FileOutputStream os = new FileOutputStream(pat_out);
					 //JasperExportManager.exportReportToPdfFile(jasperPrint, pat_out);
					 JRPdfExporter exporter = new JRPdfExporter();
					 exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jlist);
					 exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, os);
					 //JasperExportManager.exportReportToHtml(jasperPrint,"yy.html");
					   //JasperExportManager.exportReportToPdfFile(jasperPrint,path4 + "/"+"db_stats.pdf"); 
					   // int t=pdfasbytes.length;
					   // System.out.println("Length===="+t);
					 exporter.exportReport();
					 
					 OutputStream servletOutputStream = null;
					 ByteArrayOutputStream htmlStream =new ByteArrayOutputStream();
					 byte[] report=htmlStream.toByteArray();
					 
					 
					 response.reset();
					 //response.setContentType("application/html");
						response.setContentType("application/pdf");
					// response.setHeader("Content-disposition","inline=" + "jasperReportName" + ".html");
					// servletOutputStream = response.getOutputStream();
					 //servletOutputStream.write(report);
					// response.setContentLength(report.length);
					 
					 		int length=0;
							StringBuffer buf=new StringBuffer();
							ServletOutputStream outstream=response.getOutputStream();
							response.setHeader("Content-disposition", "attachment; filename="+pat_out+"");
							//response.setContentLength(pdfasbytes.length);
							FileInputStream input1 = new FileInputStream(pat_out);
				                ServletOutputStream sos = response.getOutputStream();   
				                byte[] bbuf = new byte[4096];   

				              DataInputStream in = new DataInputStream(input1); 
				                
				               while ((in != null) && ((length = in.read(bbuf)) != -1))   
				               {   
				                    sos.write(bbuf,0,length);   
				               }   
				                
				                
				              in.close();   
				                sos.flush();   
						
							//response.setContentType("application/pdf");
							//outstream.write(pdfasbytes);
							outstream.close();
				  } 
				
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			    
			    
			
			
		return mapping.findForward("");	
		}
	
	public ActionForward saveAndSubmitMaterial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		String plantType=masterForm.getPlantType();
		try{
			String actionType=masterForm.getActionType();
			masterForm.setActionType(actionType);
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			Calendar currentDate = Calendar.getInstance();
			  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			  String dateNow = formatter.format(currentDate.getTime());
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			  String loctID="";
			  String  type=masterForm.getPlantType();
			  String matShortName="";
			
			  String getShortName="select MATERIAL_SHORT_NAME from Material_Code_Search where (SAP_CODE_NO='"+masterForm.getMaterialCode1()+"' or "
			  + "SAP_CODE_NO='"+masterForm.getMaterialCode2()+"')";
			  try{
				  ResultSet rsShortName=ad.selectQuery(getShortName);
				  while(rsShortName.next()){
					  matShortName=rsShortName.getString("MATERIAL_SHORT_NAME");
				  }
			  }catch(Exception e){
				  e.printStackTrace();
			  }
			  
			 if(type.equalsIgnoreCase("Plant"))
			 {
				 loctID=masterForm.getExtendToPlant1();
			
			 }else{
				 loctID=masterForm.getPlant2();
				 
			 }
			 	
			  int checkApprover=0;
			  String checkApprovers="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2,Priority from Material_Approvers where   Material_Type='Code Extention' and " +
		  		" Location='"+loctID+"'";
			  ResultSet rsCheck=ad.selectQuery(checkApprovers);
			  while(rsCheck.next())
			  {
				  checkApprover=1;
				  
			  }
			  
			  
			if(actionType.equalsIgnoreCase("Save"))
			{
				
				masterForm.setApproveType("Saved");
				if(plantType.equalsIgnoreCase("Plant"))
				{
					
					int materialCodeCount=0;
					
					String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where m.SAP_CODE_NO='"+masterForm.getMaterialCode1()+"' and " +
					"  l.LOCATION_CODE='"+masterForm.getPlant1()+"' and l.LOCATION_CODE=m.LOCATION_ID";
					ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
					while(rsMaterilaCodeCount.next())
					{
						materialCodeCount=rsMaterilaCodeCount.getInt(1);
					}
					if(materialCodeCount>0)
					{
					int requestNo=masterForm.getRequestNo();
					int count=0;
					String getRequestnoCount="select count(*) from code_extenstion_request where REQUEST_NO='"+requestNo+"'";
					ResultSet rsCount=ad.selectQuery(getRequestnoCount);
					while(rsCount.next())
					{
						count=rsCount.getInt(1);
					}
					if(count==0)
					{ 
						
					String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_1,MATERIAL_CODE_1,EXTENDED_TO_PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
							" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant1()+"','"+masterForm.getMaterialCode1()+"','"+masterForm.getExtendToPlant1()+"','"+masterForm.getStorageLocationId1()+"'," +
									"'"+masterForm.getExtendToStorageLocation1()+"','"+reqDate+"','"+user.getEmployeeNo()+"','No','','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode1()+"','"+matShortName+"')";
					int i=0;
					i=ad.SqlExecuteUpdate(saveData);
						
					if(i>0){
						masterForm.setMassage2("Code extenstion request Submitted with request number='"+masterForm.getRequestNo()+"'.");
						if(checkApprover==0)
						{
						return displayExtensionForm(mapping, form, request, response);
						}
						
					}
					else
						masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
					
					
					}else{
						int requestno=0;
						
						String getMaxRecord="select max(REQUEST_NO) from code_extenstion_request";
						ResultSet rsMaxRecord=ad.selectQuery(getMaxRecord);
						while(rsMaxRecord.next())
						{
							requestno=rsMaxRecord.getInt(1);
						}
						requestno=requestno+1;
						masterForm.setRequestNo(requestno);	
						String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_1,MATERIAL_CODE_1,EXTENDED_TO_PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
						" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant1()+"','"+masterForm.getMaterialCode1()+"','"+masterForm.getExtendToPlant1()+"','"+masterForm.getStorageLocationId1()+"'," +
								"'"+masterForm.getExtendToStorageLocation1()+"','"+reqDate+"','"+user.getEmployeeNo()+"','No','','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode1()+"','"+matShortName+"')";
				int i=0;
				i=ad.SqlExecuteUpdate(saveData);
				
				if(i>0){
					masterForm.setMassage2("Code extenstion request Submitted with new request number='"+masterForm.getRequestNo()+"'.");
					
					if(checkApprover==0)
					{
					return displayExtensionForm(mapping, form, request, response);
					}	
					
				}
				else
				{
					masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
					actionType=masterForm.getActionType();
					masterForm.setActionType(actionType);
					request.setAttribute("saveButton", "saveButton");
				}
					}
					
					
					}else{
						masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
						actionType=masterForm.getActionType();
						masterForm.setActionType(actionType);
						request.setAttribute("saveButton", "saveButton");
				
						
					}
					
		}else{
			//Selected Type is storage location
			
			int materialCodeCount=0;
			
			String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where m.SAP_CODE_NO='"+masterForm.getMaterialCode2()+"' and " +
			"  l.LOCATION_CODE='"+masterForm.getPlant2()+"' and l.LOCATION_CODE=m.LOCATION_ID";
			ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
			while(rsMaterilaCodeCount.next())
			{
				materialCodeCount=rsMaterilaCodeCount.getInt(1);
			}
			if(materialCodeCount>0)
			{
			int requestNo=masterForm.getRequestNo();
			int count=0;
			String getRequestnoCount="select count(*) from code_extenstion_request where REQUEST_NO='"+requestNo+"'";
			ResultSet rsCount=ad.selectQuery(getRequestnoCount);
			while(rsCount.next())
			{
				count=rsCount.getInt(1);
			}
			if(count==0)
			{
			String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_2,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,TO_STORAGE_LOCATION,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
					" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant2()+"','"+masterForm.getMaterialCode2()+"','"+masterForm.getFromStorageLocation()+"','"+masterForm.getToStorageLocation()+"'," +
							"'"+reqDate+"','"+user.getEmployeeNo()+"','No','Administrator','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode2()+"','"+matShortName+"')";
			int i=0;
			i=ad.SqlExecuteUpdate(saveData);
				
			if(i>0)
			{
				masterForm.setMassage2("Code extenstion request saved with request number='"+masterForm.getRequestNo()+"'.");
				if(checkApprover==0)
				{
				return displayExtensionForm(mapping, form, request, response);
				}
			}
				else{
				masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
				actionType=masterForm.getActionType();
				masterForm.setActionType(actionType);
				request.setAttribute("saveButton", "saveButton");
			}
			
			}else{
				int requestno=0;
				
				String getMaxRecord="select max(REQUEST_NO) from code_extenstion_request";
				ResultSet rsMaxRecord=ad.selectQuery(getMaxRecord);
				while(rsMaxRecord.next())
				{
					requestno=rsMaxRecord.getInt(1);
				}
				requestno=requestno+1;
				masterForm.setRequestNo(requestno);	
				String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_2,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,TO_STORAGE_LOCATION,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
				" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant2()+"','"+masterForm.getMaterialCode2()+"','"+masterForm.getFromStorageLocation()+"','"+masterForm.getToStorageLocation()+"'," +
						"'"+reqDate+"','"+user.getEmployeeNo()+"','No','Administrator','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode2()+"','"+matShortName+"')";
		int i=0;
		i=ad.SqlExecuteUpdate(saveData);
		
		if(i>0){
			masterForm.setMassage2("Code extenstion request saved with request number='"+masterForm.getRequestNo()+"'.");
			if(checkApprover==0)
			{
			return displayExtensionForm(mapping, form, request, response);
			}
			
		}
		else
			masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
		actionType=masterForm.getActionType();
		masterForm.setActionType(actionType);
		request.setAttribute("saveButton", "saveButton");
			}
			
			
			}else{
				masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
				actionType=masterForm.getActionType();
				masterForm.setActionType(actionType);
				request.setAttribute("saveButton", "saveButton");
			}
		}				
			}else{
				
				
				String recordStatus="";
				String getRecordStatus="select APPROVE_TYPE from code_extenstion_request where REQUEST_NO='"+masterForm.getRequestNo()+"' ";
				ResultSet rsRecord=ad.selectQuery(getRecordStatus);
				while(rsRecord.next())
				{
					recordStatus=rsRecord.getString("APPROVE_TYPE");
				}
				if(recordStatus.equalsIgnoreCase("Rejected"))
				{
					
					String deleteRecords="delete from All_Request where Req_Id='"+masterForm.getRequestNo()+"' and Req_Type='Code Extention'";
					int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
					System.out.println("deleteStatus="+deleteStatus);
				}
				//update code extension..
				
			if(plantType.equalsIgnoreCase("Plant"))	{
				masterForm.setPlant2("");
				masterForm.setMaterialCode2("");
				masterForm.setFromStorageLocation("");
				masterForm.setToStorageLocation("");
			}else{
				masterForm.setPlant1("");
				masterForm.setMaterialCode1("");
				masterForm.setExtendToPlant1("");
				masterForm.setStorageLocationId1("");
				masterForm.setExtendToStorageLocation1("");
			}
				
			int materialCodeCount=0;
		
				if(plantType.equalsIgnoreCase("Plant"))	{
				
						String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where m.SAP_CODE_NO='"+masterForm.getMaterialCode1()+"' and " +
					"  l.LOCATION_CODE='"+masterForm.getPlant1()+"' and l.LOCID=m.LOCATION_ID ";
						ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
						while(rsMaterilaCodeCount.next())
						{
							materialCodeCount=rsMaterilaCodeCount.getInt(1);
						}
						if(materialCodeCount>0)
						{	
						String updateRecord="update code_extenstion_request set TYPE='"+masterForm.getPlantType()+"',PLANT_1='"+masterForm.getPlant1()+"',MATERIAL_CODE_1='"+masterForm.getMaterialCode1()+"',EXTENDED_TO_PLANT_1='"+masterForm.getExtendToPlant1()+"',STORAGE_LOCATION_ID_1='"+masterForm.getStorageLocationId1()+"',EXTENDED_STORAGE_LOCATION_1='"+masterForm.getExtendToStorageLocation1()+"'," +
								"PLANT_2='"+masterForm.getPlant2()+"',MATERIAL_CODE_2='"+masterForm.getMaterialCode2()+"',FROM_STORAGE_LOCATION='"+masterForm.getFromStorageLocation()+"',TO_STORAGE_LOCATION='"+masterForm.getToStorageLocation()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getUserName()+"',MATERIAL_SHORT_NAME='"+matShortName+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						int i=0;
						i=ad.SqlExecuteUpdate(updateRecord);
						if(i>0){
							masterForm.setMassage2("Code extenstion request updated with  request number='"+masterForm.getRequestNo()+"'.");
							if(checkApprover==0)
							{
							return displayExtensionForm(mapping, form, request, response);
							}	
							
						}
						else{
							masterForm.setMassage1("Error...When updating Material code extenstion.Please check....");
							actionType=masterForm.getActionType();
							masterForm.setActionType(actionType);
							request.setAttribute("saveButton", "saveButton");
						}
						}
						else{
							masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
							actionType=masterForm.getActionType();
							masterForm.setActionType(actionType);
							request.setAttribute("saveButton", "saveButton");
						}
					}
			else{
				String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where SAP_CODE_NO='"+masterForm.getMaterialCode2()+"' " +
				" and l.LOCATION_CODE='"+masterForm.getPlant2()+"' and l.LOCID=m.LOCATION_ID ";
				
				ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
				while(rsMaterilaCodeCount.next())
				{
					materialCodeCount=rsMaterilaCodeCount.getInt(1);
				}
				if(materialCodeCount>0)
				{	
				String updateRecord="update code_extenstion_request set TYPE='"+masterForm.getPlantType()+"',PLANT_1='"+masterForm.getPlant1()+"',MATERIAL_CODE_1='"+masterForm.getMaterialCode1()+"',EXTENDED_TO_PLANT_1='"+masterForm.getExtendToPlant1()+"',STORAGE_LOCATION_ID_1='"+masterForm.getStorageLocationId1()+"',EXTENDED_STORAGE_LOCATION_1='"+masterForm.getExtendToStorageLocation1()+"'," +
						"PLANT_2='"+masterForm.getPlant2()+"',MATERIAL_CODE_2='"+masterForm.getMaterialCode2()+"',FROM_STORAGE_LOCATION='"+masterForm.getFromStorageLocation()+"',TO_STORAGE_LOCATION='"+masterForm.getToStorageLocation()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getUserName()+"',MATERIAL_SHORT_NAME='"+matShortName+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				int i=0;
				i=ad.SqlExecuteUpdate(updateRecord);
				if(i>0)
				{
					masterForm.setMassage2("Code extenstion request updated with  request number='"+masterForm.getRequestNo()+"'.");
					
					if(checkApprover==0)
					{
					return displayExtensionForm(mapping, form, request, response);
					}	
				}
				else
				{
					masterForm.setMassage1("Error...When updating Material code extenstion.Please check....");
					actionType=masterForm.getActionType();
					masterForm.setActionType(actionType);
					request.setAttribute("saveButton", "saveButton");
				}
				}
				else{
					masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
					actionType=masterForm.getActionType();
					masterForm.setActionType(actionType);
					request.setAttribute("saveButton", "saveButton");
				}
			}
			
		}
			
			if(checkApprover==1)
			{
				try{

					int i=0;
					
					
					String matType="Code Extention";
					
						Date dNow = new Date( );
						SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
						dateNow = ft.format(dNow);
					 	String pApprover="";
					 	String parllelAppr1="";
					 	String parllelAppr2="";
			    		String matGroup="";
			    		String location="";
					 
					checkApprover=0;
					 	String pendingApprs="";
					 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Code Extention' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
			    		ResultSet rsAppr=ad.selectQuery(getApprovers);
			    		while(rsAppr.next())
			    		{
			    			checkApprover=1;
			    			if(rsAppr.getInt("Priority")==1){
			    			pApprover=rsAppr.getString("Approver_Id");
			    			parllelAppr1=rsAppr.getString("Parllel_Approver_1");
			    			parllelAppr2=rsAppr.getString("Parllel_Approver_2");
			    			
			    			}
			    			pendingApprs=pendingApprs+rsAppr.getString("EMP_FULLNAME")+" , ";
			    		}
			    		if(!(pendingApprs.equalsIgnoreCase(""))){
			    			int size=pendingApprs.length();
			    			pendingApprs=pendingApprs.substring(0, (size-2));
			    		}
			    		if(!(pApprover.equalsIgnoreCase(""))){
			    		
			    		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						saveRecReq = saveRecReq + "'"+masterForm.getRequestNo()+"','Code Extention','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getUserName()+"','','"+matType+"')";
						int ij=ad.SqlExecuteUpdate(saveRecReq);
						if(!(parllelAppr1.equalsIgnoreCase(""))){
							
							
						 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
						 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+masterForm.getRequestNo()+"','Code Extention','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getUserName()+"','','"+matType+"')";
						 ad.SqlExecuteUpdate(sendRecParllelAppr1);
						}
						if(!(parllelAppr2.equalsIgnoreCase(""))){
						
							 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
							 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+masterForm.getRequestNo()+"','Code Extention','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getUserName()+"','','"+matType+"')";
							 ad.SqlExecuteUpdate(sendRecParllelAppr2);
						}
						
						if(ij>0){
							masterForm.setMessage("Request No"+masterForm.getRequestNo()+". Submitted for approval successully.");
							String updateStatus="update code_extenstion_request set APPROVE_TYPE='In Process',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
							System.out.println(updateStatus);
							ad.SqlExecuteUpdate(updateStatus);
							EMailer email = new EMailer();
							String approvermail="";
						 email.sendMailToApprover(request, approvermail,Integer.toString(masterForm.getRequestNo()), "Code Extension");
						}else{
							masterForm.setMessage("Error while submiting approval...");
						}
			    		}else{
			    			
			    			masterForm.setMessage("No Approvers are assigned.Please Contact to Admin");
			    		}
					
				
				}catch (Exception e) {
					e.printStackTrace();
				}
				return displayExtensionForm(mapping, form, request, response);
			}
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAMe,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList1=new ArrayList();
			ArrayList locationLabelList1=new ArrayList();
			
			while(rsLoc.next()) {
				locationList1.add(rsLoc.getString("location_code"));
				locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
			}
			masterForm.setLocationIdList1(locationList1);
			masterForm.setLocationLabelList1(locationLabelList1);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			
			
			String materialCode1=masterForm.getMaterialCode1();
			
			if(materialCode1!=null){
				//get mat tyep
				
			if(!materialCode1.equalsIgnoreCase("")){
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
					"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			}
			}
			
			
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(plantType.equalsIgnoreCase("Plant"))
		{
			request.setAttribute("plantMandatory", "plantMandatory");
			
		}
		else{
			request.setAttribute("storageMandatory", "storageMandatory");
			
		}
		String actionType=masterForm.getActionType();
		if(actionType.equalsIgnoreCase("Update"))
			request.setAttribute("saveButton", "saveButton");
		masterForm.setActionType(actionType);
	return mapping.findForward("displayNewForm");
	}
	
	
	
	public ActionForward sendMailToApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			int i=0;
			
			String requestNo=request.getParameter("ReqestNo");
			String matType="Code Extention";
			String loctID="";
				Date dNow = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
				String dateNow = ft.format(dNow);
			 	String pApprover="";
			 	String parllelAppr1="";
			 	String parllelAppr2="";
	    		String matGroup="";
	    		String location="";
			 	String getMatGroup="select c.TYPE,loc.LOCATION_CODE from code_extenstion_request as c,location as loc where REQUEST_NO='"+requestNo+"' and (c.PLANT_1 =loc.LOCATION_CODE or c.PLANT_2=loc.LOCATION_CODE)";
			 	ResultSet rsMatGrup=ad.selectQuery(getMatGroup);
			 	while(rsMatGrup.next()){
			 		String  type=rsMatGrup.getString("TYPE");
			 		if(type.equalsIgnoreCase("Plant"))
			 		{
			 			loctID=rsMatGrup.getString("LOCATION_CODE");	
			 		}else{
			 			loctID=rsMatGrup.getString("LOCATION_CODE");
			 		}
			 	}
			 	int checkApprover=0;
			 	String pendingApprs="";
			 	String getApprovers="select app.Approver_Id,emp.EMP_FULLNAME,app.Parllel_Approver_1,app.Parllel_Approver_2,app.Priority from Material_Approvers as app,emp_official_info AS emp where app.Location='"+loctID+"' and app.Material_Type='Code Extention' and app.Material_Group='' AND app.Approver_Id=emp.PERNR   order by app.Priority ";
	    		ResultSet rsAppr=ad.selectQuery(getApprovers);
	    		while(rsAppr.next())
	    		{
	    			
	    			checkApprover=1;
	    			if(rsAppr.getInt("Priority")==1){
	    			pApprover=rsAppr.getString("Approver_Id");
	    			parllelAppr1=rsAppr.getString("Parllel_Approver_1");
	    			parllelAppr2=rsAppr.getString("Parllel_Approver_2");
	    			
	    			}
	    			pendingApprs=pendingApprs+rsAppr.getString("EMP_FULLNAME")+",";
	    		}
	    		if(!(pendingApprs.equalsIgnoreCase(""))){
	    			int size=pendingApprs.length();
	    			pendingApprs=pendingApprs.substring(0, (size-1));
	    		}
	    		if(!(pApprover.equalsIgnoreCase(""))){
	    		
	    		 String	saveRecReq="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				saveRecReq = saveRecReq + "'"+requestNo+"','Code Extention','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+pApprover+"','No','"+user.getUserName()+"','','"+matType+"')";
				int ij=ad.SqlExecuteUpdate(saveRecReq);
				if(!(parllelAppr1.equalsIgnoreCase(""))){
					
					String a[]=parllelAppr1.split(",");
					parllelAppr1=a[0];
				 String	sendRecParllelAppr1="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
				 sendRecParllelAppr1 = sendRecParllelAppr1 + "'"+requestNo+"','Code Extention','"+user.getUserName()+"','"+dateNow+"','Pending','No','"+parllelAppr1+"','No','"+user.getUserName()+"','','"+matType+"')";
				 ad.SqlExecuteUpdate(sendRecParllelAppr1);
				}
				if(!(parllelAppr2.equalsIgnoreCase(""))){
					String a[]=parllelAppr2.split(",");
					parllelAppr2=a[0];
					 String	sendRecParllelAppr2="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type) values (";
					 sendRecParllelAppr2 = sendRecParllelAppr2 + "'"+requestNo+"','Code Extention','"+user.getUserName()+"','"+dNow+"','Pending','No','"+parllelAppr2+"','No','"+user.getUserName()+"','','"+matType+"')";
					 ad.SqlExecuteUpdate(sendRecParllelAppr2);
				}
				
				if(ij>0){
					masterForm.setMessage("Request No"+requestNo+". Submitted for approval successully.");
					String updateStatus="update code_extenstion_request set APPROVE_TYPE='In Process',last_approver='No',pending_approver='"+pendingApprs+"' where REQUEST_NO='"+requestNo+"'";
					ad.SqlExecuteUpdate(updateStatus);
					
				}else{
					masterForm.setMessage("Error while submiting approval...");
				}
	    		}else{
	    			
	    			masterForm.setMessage("No Approvers are assigned.Please Contact to Admin");
	    		}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		displayCodeExtenstionList(mapping, form, request, response);
		return mapping.findForward("displayList");
	}
	
	
	public ActionForward saveApproveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String approveType="Pending";
	try{
		
		int requestNo=masterForm.getRequestNo();
		
		String getRecord="select * from code_extenstion_request where REQUEST_NO='"+requestNo+"'";
		ResultSet rs=ad.selectQuery(getRecord);
		while(rs.next())
		{
			masterForm.setRequestNo(rs.getInt("REQUEST_NO"));
			String reqDate=rs.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			masterForm.setRequestDate(reqDate);
			masterForm.setPlantType(rs.getString("TYPE"));
			String plantType=rs.getString("TYPE");
			if(plantType.equalsIgnoreCase("Plant"))
			{
				masterForm.setPlant1(rs.getString("PLANT_1"));
				masterForm.setMaterialCode1(rs.getString("MATERIAL_CODE_1"));
				masterForm.setStorageLocationId1(rs.getString("STORAGE_LOCATION_ID_1"));
				masterForm.setExtendToPlant1(rs.getString("EXTENDED_TO_PLANT_1"));
				masterForm.setExtendToStorageLocation1(rs.getString("EXTENDED_STORAGE_LOCATION_1"));
				request.setAttribute("plantMandatory", "plantMandatory");
			}
			else{
				
				String plant2=rs.getString("PLANT_2");
				plant2=plant2.replaceAll("\\W","");
				masterForm.setPlant2(plant2);
				masterForm.setMaterialCode2(rs.getString("MATERIAL_CODE_2"));
				masterForm.setFromStorageLocation(rs.getString("FROM_STORAGE_LOCATION"));
				masterForm.setToStorageLocation(rs.getString("TO_STORAGE_LOCATION"));
				request.setAttribute("storageMandatory", "storageMandatory");
			}
			approveType=rs.getString("APPROVE_TYPE");
		}		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAMe,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
		ArrayList locationList1=new ArrayList();
		ArrayList locationLabelList1=new ArrayList();
		
		while(rsLoc.next()) {
			locationList1.add(rsLoc.getString("location_code"));
			locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
		}
		masterForm.setLocationIdList1(locationList1);
		masterForm.setLocationLabelList1(locationLabelList1);
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		
		
		String materialCode1=masterForm.getMaterialCode1();
		
		if(materialCode1!=null){
			//get mat tyep
			
		if(!materialCode1.equalsIgnoreCase("")){
		String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
				"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		}
		}
		
		
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		int i=0;
		String currentdate=EMicroUtils.getCurrentSysDate();
		  String a[]=currentdate.split("/");
		  for(int j=0;j<a.length;j++)
		  {
			  System.out.println("a="+a[j]);
		  }
		  currentdate=a[2]+"-"+a[1]+"-"+a[0];
		
		String updateMaterial="update code_extenstion_request set APPROVE_TYPE='"+masterForm.getApproveType()+"',approve_date='"+currentdate+"',last_approver='"+user.getFullName()+"',pending_approver='No'  where REQUEST_NO='"+masterForm.getRequestNo()+"'";
		i=ad.SqlExecuteUpdate(updateMaterial);
		if(i>0)
		{
			approveType=masterForm.getApproveType();
			masterForm.setMassage2("Code creation request updated with request number='"+masterForm.getRequestNo()+"'");
		}else{
			masterForm.setMassage1("Error...When updating code creation reequest.Please Check");
		} 
	}catch (Exception e) {
		e.printStackTrace();
	}
		String actionType="Update";
		masterForm.setActionType(actionType);
		request.setAttribute("saveButton", "saveButton");
		String forwardType="displayNewForm";
		
		
		
		
		return mapping.findForward("serviceMasterSAP");
	}
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		try{
		LinkedList listOfMaterials=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int totalRecords=masterForm.getTotalRecords();//21
		int startRecord=masterForm.getStartRecord();//11
		int endRecord=masterForm.getEndRecord();	
		
		
		if(totalRecords>10){
			  startRecord=1;
			  endRecord=10;
			  masterForm.setTotalRecords(totalRecords);
			  masterForm.setStartRecord(startRecord);
			  masterForm.setEndRecord(10);
			  }
			  else{
				  startRecord=1;
				  masterForm.setTotalRecords(totalRecords);
				  masterForm.setStartRecord(startRecord);
				  masterForm.setEndRecord(totalRecords);  
			  }

		  String status=masterForm.getApproveType();
		  if(status==null)
		  {
			  String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
				"MATERIAL_CODE_2,MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
				"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code where  code.CREATED_BY='"+user.getEmployeeNo()+"' and " +
				"code.APPROVE_TYPE='masterForm.getApproveType()' )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' ";
				ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
				while(rsCodeList.next())
				{
					MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
					masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
					masterForm1.setPlantType(rsCodeList.getString("TYPE"));
					String plantType=rsCodeList.getString("TYPE");
					
					masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
					if(plantType.equalsIgnoreCase("Plant"))
					{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
						
						masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
					}else{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
						masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
					}
					
					masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
					
					listOfMaterials.add(masterForm1);
					
				}
				request.setAttribute("listOfMaterials", listOfMaterials);	
			 
		  }
		  else{
		  
		 if(status.equalsIgnoreCase(""))
			{
			 String getCodeExtensionList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID) AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1,MATERIAL_CODE_2," +
				"mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION,  TO_STORAGE_LOCATION," +
				"code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where code.CREATED_BY='"+user.getEmployeeNo()+"'  and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
				"and (mat.SAP_CODE_NO=code.MATERIAL_CODE_1) or (mat.SAP_CODE_NO=code.MATERIAL_CODE_2))  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
				while(rsCodeList.next())
				{
					MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
					masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
					masterForm1.setPlantType(rsCodeList.getString("TYPE"));
					
					String plantType=rsCodeList.getString("TYPE");
					masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
					if(plantType.equalsIgnoreCase("Plant"))
					{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
						
						masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
					}else{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
						masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
					}
					
					masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
					
					listOfMaterials.add(masterForm1);
					
				}
				request.setAttribute("listOfMaterials", listOfMaterials);	
			 
			}else{
				String getCodeExtensionList="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY ID) AS RowNum,REQUEST_NO,TYPE,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION,TO_STORAGE_LOCATION,APPROVE_TYPE " +
				"from code_extenstion_request where CREATED_BY='"+user.getEmployeeNo()+"' and APPROVE_TYPE='"+masterForm.getApproveType()+"') as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
				while(rsCodeList.next())
				{
					MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
					masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
					masterForm1.setPlantType(rsCodeList.getString("TYPE"));
					String plantType=rsCodeList.getString("TYPE");
					if(plantType.equalsIgnoreCase("Plant"))
					{
						masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
					}else{
						masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
					}
					
					masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
					
					listOfMaterials.add(masterForm1);
					
				}
			
				request.setAttribute("listOfMaterials", listOfMaterials);
			}
		 if(totalRecords>10)
			{
				request.setAttribute("nextButton", "nextButton");
			}
		
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			
			request.setAttribute("displayRecordNo", "displayRecordNo");
			request.setAttribute("materialCodeList","materialCodeList");
			
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayList");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		try{
		LinkedList listOfMaterials=new LinkedList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int totalRecords=masterForm.getTotalRecords();//21
		int startRecord=masterForm.getStartRecord();//11
		int endRecord=masterForm.getEndRecord();	
		
		
		 startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 masterForm.setTotalRecords(totalRecords);
		 masterForm.setStartRecord(startRecord);
		 masterForm.setEndRecord(totalRecords);
		  String status=masterForm.getApproveType();
			if(status.equalsIgnoreCase(""))
			{
				String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
				"MATERIAL_CODE_2,mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
				"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where  code.CREATED_BY='"+user.getEmployeeNo()+"' and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
				" and mat.SAP_CODE_NO=code.existing_sapNo )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
				while(rsCodeList.next())
				{
					MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
					masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
					masterForm1.setPlantType(rsCodeList.getString("TYPE"));
					
					String plantType=rsCodeList.getString("TYPE");
					masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
					if(plantType.equalsIgnoreCase("Plant"))
					{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
						
						masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
					}else{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
						masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
					}
					masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
					
					listOfMaterials.add(masterForm1);
				}
				request.setAttribute("listOfMaterials", listOfMaterials);	
			}else{
				String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
						"MATERIAL_CODE_2,mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
						"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where  code.CREATED_BY='"+user.getEmployeeNo()+"' and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
						" and mat.SAP_CODE_NO=code.existing_sapNo )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
						while(rsCodeList.next())
						{
							MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
							masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
							masterForm1.setPlantType(rsCodeList.getString("TYPE"));
							
							String plantType=rsCodeList.getString("TYPE");
							masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
							if(plantType.equalsIgnoreCase("Plant"))
							{
								masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
								
								masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
								masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
								masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
							}else{
								masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
								masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
								masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
								masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
							}
							masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
							
							listOfMaterials.add(masterForm1);
							
						}
				request.setAttribute("listOfMaterials", listOfMaterials);
			}
		 request.setAttribute("disableNextButton", "disableNextButton");
			request.setAttribute("previousButton", "previousButton");
			if(listOfMaterials.size()<10)
			{
				
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
			request.setAttribute("displayRecordNo", "displayRecordNo");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("displayList");
	}
	
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		try{
			LinkedList listOfMaterials=new LinkedList();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			System.out.println("Start Record="+masterForm.getStartRecord());
			System.out.println("End record="+masterForm.getEndRecord());
			System.out.println("Total Record="+masterForm.getTotalRecords());	
			
			int totalRecords=masterForm.getTotalRecords();//21
			int endRecord=masterForm.getStartRecord()-1;//20
			int startRecord=masterForm.getStartRecord()-10;//11
			
			if(startRecord==1)
			{
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				endRecord=10;
			}
			masterForm.setTotalRecords(totalRecords);
			masterForm.setStartRecord(1);
			masterForm.setEndRecord(10);
			  String status=masterForm.getApproveType();
				if(status.equalsIgnoreCase(""))
				{
					
					String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
					"MATERIAL_CODE_2,mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
					"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where  code.CREATED_BY='"+user.getEmployeeNo()+"' and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
					" and mat.SAP_CODE_NO=code.existing_sapNo )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
					ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
					while(rsCodeList.next())
					{
						MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
						masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
						masterForm1.setPlantType(rsCodeList.getString("TYPE"));
						
						String plantType=rsCodeList.getString("TYPE");
						masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
						if(plantType.equalsIgnoreCase("Plant"))
						{
							masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
							
							masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
							masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
							masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
						}else{
							masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
							masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
							masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
							masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
						}
						masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
						
						listOfMaterials.add(masterForm1);
						
					}
					request.setAttribute("listOfMaterials", listOfMaterials);	
					
					
				}else{
					String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
							"MATERIAL_CODE_2,mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
							"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where  code.CREATED_BY='"+user.getEmployeeNo()+"' and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
							" and mat.SAP_CODE_NO=code.existing_sapNo )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
							ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
							while(rsCodeList.next())
							{
								MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
								masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
								masterForm1.setPlantType(rsCodeList.getString("TYPE"));
								
								String plantType=rsCodeList.getString("TYPE");
								masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
								if(plantType.equalsIgnoreCase("Plant"))
								{
									masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
									
									masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
									masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
									masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
								}else{
									masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
									masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
									masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
									masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
								}
								masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
								
								listOfMaterials.add(masterForm1);
								
							}
					request.setAttribute("listOfMaterials", listOfMaterials);
					
					
				}
		masterForm.setTotalRecords(totalRecords);
		masterForm.setStartRecord(startRecord);
		masterForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				if(startRecord!=1)
				request.setAttribute("previousButton", "previousButton");
				request.setAttribute("displayRecordNo", "displayRecordNo");
				if(listOfMaterials.size()<10)
				{
					masterForm.setStartRecord(1);
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward("displayList");
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		try{
			int totalRecords=masterForm.getTotalRecords();//21
			int startRecord=masterForm.getStartRecord();//11
			int endRecord=masterForm.getEndRecord();
			LinkedList listOfMaterials=new LinkedList();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
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
			String status=masterForm.getApproveType();
			if(status.equalsIgnoreCase(""))
			{
				
				String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
				"MATERIAL_CODE_2,mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
				"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where  code.CREATED_BY='"+user.getEmployeeNo()+"' and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
				" and mat.SAP_CODE_NO=code.existing_sapNo )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
				while(rsCodeList.next())
				{
					MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
					masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
					masterForm1.setPlantType(rsCodeList.getString("TYPE"));
					
					String plantType=rsCodeList.getString("TYPE");
					masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
					if(plantType.equalsIgnoreCase("Plant"))
					{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
						
						masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
					}else{
						masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
						masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
						masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
						masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
					}
					masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
					
					listOfMaterials.add(masterForm1);
					
				}
				request.setAttribute("listOfMaterials", listOfMaterials);	
				
				
			}else{
				String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
						"MATERIAL_CODE_2,mat.MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
						"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code,Material_Code_Search as mat where  code.CREATED_BY='"+user.getEmployeeNo()+"' and code.APPROVE_TYPE='"+masterForm.getApproveType()+"' " +
						" and mat.SAP_CODE_NO=code.existing_sapNo )  as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
						ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
						while(rsCodeList.next())
						{
							MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
							masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
							masterForm1.setPlantType(rsCodeList.getString("TYPE"));
							
							String plantType=rsCodeList.getString("TYPE");
							masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
							if(plantType.equalsIgnoreCase("Plant"))
							{
								masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
								
								masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
								masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
								masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
							}else{
								masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
								masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
								masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
								masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
							}
							masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
							
							listOfMaterials.add(masterForm1);
							
						}
				request.setAttribute("listOfMaterials", listOfMaterials);
				
				
			}
			
			}
			System.out.println("list length="+listOfMaterials.size());
			
			 if(listOfMaterials.size()!=0)
				{
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(endRecord);
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("previousButton", "previousButton");
				}
				else
				{
					int start=startRecord;
					int end=startRecord;
					
					masterForm.setTotalRecords(totalRecords);
					masterForm.setStartRecord(start);
					masterForm.setEndRecord(end);
					
				}
			 if(listOfMaterials.size()<10)
			 {
				 masterForm.setTotalRecords(totalRecords);
				 masterForm.setStartRecord(startRecord);
				 masterForm.setEndRecord(startRecord+listOfMaterials.size()-1);
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
		return mapping.findForward("displayList");
	}
	
	
	
	public ActionForward getSearchRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		int  totalRecords=0;
		  int  startRecord=0;
		  int  endRecord=0;
		  HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		
			int recordsTotal=0;
			String status=masterForm.getApproveType();
			
			String getVendorCount="Select count(*) from code_extenstion_request where CREATED_BY='"+user.getEmployeeNo()+"' and APPROVE_TYPE='"+status+"'";
			ResultSet rsVendorCount=ad.selectQuery(getVendorCount);
			while(rsVendorCount.next())
			{
				recordsTotal=rsVendorCount.getInt(1);
			}
			System.out.println("Toatal Records="+recordsTotal);	    	 
			 if(recordsTotal>10)
			 {
				 masterForm.setTotalRecords(recordsTotal);
			 startRecord=1;
			 endRecord=10;
			 masterForm.setStartRecord(1);
			 masterForm.setEndRecord(10);
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  masterForm.setTotalRecords(totalRecords);
				  masterForm.setStartRecord(1);
				  masterForm.setEndRecord(totalRecords); 
			 }
			 
			
			LinkedList listOfMaterials=new LinkedList();
			
			String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
					"MATERIAL_CODE_2,MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
					"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code where  code.CREATED_BY='"+user.getEmployeeNo()+"' and " +
					"code.APPROVE_TYPE='"+masterForm.getApproveType()+"' )  as sub Where  sub.RowNum between 1  and 10";
					ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
					while(rsCodeList.next())
					{
						MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
						masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
						masterForm1.setPlantType(rsCodeList.getString("TYPE"));
						String plantType=rsCodeList.getString("TYPE");
						
						masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
						if(plantType.equalsIgnoreCase("Plant"))
						{
							masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
							
							masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
							masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
							masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
						}else{
							masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
							masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
							masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
							masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
						}
						
						masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
						
						listOfMaterials.add(masterForm1);
						
					}
			if(listOfMaterials.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				masterForm.setMessage("No Records Found");
				
			}
			String module="ESS"; 
			request.setAttribute("MenuIcon", module);
			request.setAttribute("listOfMaterials", listOfMaterials);
			}catch (Exception e) {
				e.printStackTrace();
			}

	return mapping.findForward("displayList");	
	}
	
	public ActionForward deleteExtenstionRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		try{
			String reqNo[]=masterForm.getRequiredRequestNumber();
			
			for(int j=0;j<reqNo.length;j++)
			{
				String deleteRecord="update  code_extenstion_request  set APPROVE_TYPE='deleted' where REQUEST_NO='"+reqNo[j]+"'";
				
			   int i=0;
			   i=ad.SqlExecuteUpdate(deleteRecord);
			   if(i>=1)
			   {
				   masterForm.setMassage1("Selected Record Successfully Deleted");
				  
			   }else{
				   masterForm.setMassage2("Error.... When Deleting  Record.");
			   }
			   displayCodeExtenstionList(mapping, form, request, response);
			}
			}catch (Exception e) {
				masterForm.setMassage2("Error.... When Deleting  Record.Please Select Atleast One Record");
				displayCodeExtenstionList(mapping, form, request, response);
			}
		
		
	return mapping.findForward("displayList");	
	}
	
	public ActionForward editCodeExtension(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String approveType="Pending";
	try{
		
		int requestNo=Integer.parseInt(request.getParameter("requstNo"));
		
		String getRecord="select * from code_extenstion_request where REQUEST_NO='"+requestNo+"'";
		ResultSet rs=ad.selectQuery(getRecord);
		if(rs.next())
		{
			masterForm.setRequestNo(rs.getInt("REQUEST_NO"));
			String reqDate=rs.getString("REQUEST_DATE");
			String a[]=reqDate.split(" ");
			reqDate=a[0];
			String b[]=reqDate.split("-");
			reqDate=b[2]+"/"+b[1]+"/"+b[0];
			masterForm.setRequestDate(reqDate);
			masterForm.setPlantType(rs.getString("TYPE"));
			String plantType=rs.getString("TYPE");
			if(plantType.equalsIgnoreCase("Plant"))
			{
				masterForm.setPlant1(rs.getString("PLANT_1"));
				masterForm.setMaterialCode1(rs.getString("MATERIAL_CODE_1"));
				masterForm.setStorageLocationId1(rs.getString("STORAGE_LOCATION_ID_1"));
				masterForm.setExtendToPlant1(rs.getString("EXTENDED_TO_PLANT_1"));
				masterForm.setExtendToStorageLocation1(rs.getString("EXTENDED_STORAGE_LOCATION_1"));
				request.setAttribute("plantMandatory", "plantMandatory");
				
				LinkedList storageID=new LinkedList();
				LinkedList storageName=new LinkedList();
				String materialCode1=masterForm.getMaterialCode1();
				if(materialCode1!=null){
					//get mat tyep
				if(!materialCode1.equalsIgnoreCase("")){

					ArrayList locationList=new ArrayList();
					ArrayList locationLabelList=new ArrayList();
					String getAvalLoc="select * from Material_Code_Search where SAP_CODE_NO='"+materialCode1+"'";
					ResultSet rsLocat=ad.selectQuery(getAvalLoc);
					String avaLoc="";
					while(rsLocat.next()){
						avaLoc=avaLoc+"'"+rsLocat.getString("LOCATION_ID")+"'"+",";
					}
					if(!(avaLoc.equalsIgnoreCase(""))){
					avaLoc=avaLoc.substring(0, (avaLoc.length()-1));
			        String getLoc="select LOCID,LOCNAMe,location_code from location where location_code in ("+avaLoc+")";
					ResultSet rs11 = ad.selectQuery(getLoc);
					 locationList=new ArrayList();
					 locationLabelList=new ArrayList();
					
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
					}
					}
					masterForm.setLocationIdList(locationList);
					masterForm.setLocationLabelList(locationLabelList);
					
				String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
						"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				}
				}
				
				masterForm.setStorageID(storageID);
				masterForm.setStorageIDName(storageName);
				
				String availableLoc=user.getAvailableLocations();
				if(availableLoc.equalsIgnoreCase(""))
					availableLoc="0";
				ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
				ArrayList locationList1=new ArrayList();
				ArrayList locationLabelList1=new ArrayList();
				
				while(rsLoc.next()) {
					locationList1.add(rsLoc.getString("location_code"));
					locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
				}
				masterForm.setLocationIdList1(locationList1);
				masterForm.setLocationLabelList1(locationLabelList1);
				
			}
			else{
				
				String plant2=rs.getString("PLANT_2");
				plant2=plant2.replaceAll("\\W","");
				masterForm.setPlant2(plant2);
				masterForm.setMaterialCode2(rs.getString("MATERIAL_CODE_2"));
				masterForm.setFromStorageLocation(rs.getString("FROM_STORAGE_LOCATION"));
				masterForm.setToStorageLocation(rs.getString("TO_STORAGE_LOCATION"));
				request.setAttribute("storageMandatory", "storageMandatory");
				
				
				LinkedList storageID=new LinkedList();
				LinkedList storageName=new LinkedList();
				String materialCode2=masterForm.getMaterialCode2();
				if(materialCode2!=null){
					
					
					//get mat tyep
				if(!materialCode2.equalsIgnoreCase("")){
					
					ArrayList locationList=new ArrayList();
					ArrayList locationLabelList=new ArrayList();
					String getAvalLoc="select * from Material_Code_Search where SAP_CODE_NO='"+materialCode2+"'";
					ResultSet rsLocat=ad.selectQuery(getAvalLoc);
					String avaLoc="";
					while(rsLocat.next()){
						avaLoc=avaLoc+"'"+rsLocat.getString("LOCATION_ID")+"'"+",";
					}
					if(!(avaLoc.equalsIgnoreCase(""))){
					avaLoc=avaLoc.substring(0, (avaLoc.length()-1));
			        String getLoc="select LOCID,LOCNAMe,location_code from location where location_code in ("+avaLoc+")";
					ResultSet rs11 = ad.selectQuery(getLoc);
					 locationList=new ArrayList();
					 locationLabelList=new ArrayList();
					
					while(rs11.next()) {
						locationList.add(rs11.getString("location_code"));
						locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
					}
					}
					masterForm.setLocationIdList1(locationList);
					masterForm.setLocationLabelList1(locationLabelList);	
					
				String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct( m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
						"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode2+"' and m.id=s.MATERIAL_TYPE)";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				}
				}
				
				masterForm.setStorageID(storageID);
				masterForm.setStorageIDName(storageName);
			}
			approveType=rs.getString("APPROVE_TYPE");
		}		
		
		
		
		
	
	}catch (Exception e) {
		e.printStackTrace();
	}
		String actionType="Update";
		masterForm.setActionType(actionType);
		request.setAttribute("saveButton", "saveButton");
		String forwardType="displayNewForm";
		return mapping.findForward(forwardType);
	}
	public ActionForward displayCodeExtenstionList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			int  totalRecords=0;
			  int  startRecord=0;
			  int  endRecord=0;
		
			  String getTotalRecords="select count(*) from code_extenstion_request where CREATED_BY='"+user.getEmployeeNo()+"' and "
			  		+ "APPROVE_TYPE='In Process'";

				  
				  ResultSet rsTotalRecods=ad.selectQuery(getTotalRecords);
				  while(rsTotalRecods.next())
				  {
					  totalRecords=rsTotalRecods.getInt(1);
				  }
				  if(totalRecords>=10)
				  {
					  masterForm.setTotalRecords(totalRecords);
				  startRecord=1;
				  endRecord=10;
				  masterForm.setStartRecord(1);
				  masterForm.setEndRecord(10);
				  request.setAttribute("displayRecordNo", "displayRecordNo");
				  request.setAttribute("nextButton", "nextButton");
				  }else
				  {
					  startRecord=1;
					  endRecord=totalRecords;
					  masterForm.setTotalRecords(totalRecords);
					  masterForm.setStartRecord(1);
					  masterForm.setEndRecord(totalRecords); 
				  }	
			
			
		LinkedList listOfMaterials=new LinkedList();
		masterForm.setApproveType("In Process");
	
		String getCodeExtensionList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY code.ID)  AS  RowNum,code.REQUEST_NO,code.TYPE,MATERIAL_CODE_1," +
		"MATERIAL_CODE_2,MATERIAL_SHORT_NAME,PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,PLANT_2,FROM_STORAGE_LOCATION," +
		"TO_STORAGE_LOCATION,code.APPROVE_TYPE from code_extenstion_request as code where  code.CREATED_BY='"+user.getEmployeeNo()+"' and " +
		"code.APPROVE_TYPE='In Process' )  as sub Where  sub.RowNum between 1  and 10";
		ResultSet rsCodeList=ad.selectQuery(getCodeExtensionList);
		while(rsCodeList.next())
		{
			MaterialCodeExtensionForm masterForm1=new MaterialCodeExtensionForm();
			masterForm1.setRequestNo(rsCodeList.getInt("REQUEST_NO"));
			masterForm1.setPlantType(rsCodeList.getString("TYPE"));
			String plantType=rsCodeList.getString("TYPE");
			
			masterForm1.setDescription(rsCodeList.getString("MATERIAL_SHORT_NAME"));
			if(plantType.equalsIgnoreCase("Plant"))
			{
				masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_1"));
				
				masterForm1.setPlant1(rsCodeList.getString("PLANT_1"));
				masterForm1.setStorageLocationId1(rsCodeList.getString("STORAGE_LOCATION_ID_1"));
				masterForm1.setExtendToStorageLocation1(rsCodeList.getString("EXTENDED_STORAGE_LOCATION_1"));
			}else{
				masterForm1.setMaterialCode1(rsCodeList.getString("MATERIAL_CODE_2"));
				masterForm1.setPlant1(rsCodeList.getString("PLANT_2"));
				masterForm1.setStorageLocationId1(rsCodeList.getString("FROM_STORAGE_LOCATION"));
				masterForm1.setExtendToStorageLocation1(rsCodeList.getString("TO_STORAGE_LOCATION"));
			}
			
			masterForm1.setApproveType(rsCodeList.getString("APPROVE_TYPE"));
			
			listOfMaterials.add(masterForm1);
			
		}
		
		if(listOfMaterials.size()==0)
		{
			request.setAttribute("noRecords", "noRecords");
			masterForm.setMessage("No Records Found");
			
		}
		
		request.setAttribute("listOfMaterials", listOfMaterials);
		}catch (Exception e) {
			e.printStackTrace();
		}
		masterForm.setApproveType("Pending");
		masterForm.setMassage1("");
		masterForm.setMassage2("");
		String module=request.getParameter("id"); 
		request.setAttribute("MenuIcon", module);
		request.setAttribute("disablePreviousButton","disablePreviousButton");
		return mapping.findForward("displayList");
	}
	
	
	public ActionForward saveData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		
		String plantType=masterForm.getPlantType();
		try{
			String actionType=masterForm.getActionType();
			masterForm.setActionType(actionType);
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			if(user==null){
				request.setAttribute("message","Session Expried! Try to Login again!");
				return mapping.findForward("displayiFrameSession");
			}
			Calendar currentDate = Calendar.getInstance();
			  SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
			  String dateNow = formatter.format(currentDate.getTime());
			  String reqDate=masterForm.getRequestDate();
			  String a[]=reqDate.split("/");
			  reqDate=a[2]+"-"+a[1]+"-"+a[0];
			  String matShortName="";
				
			  String getShortName="select MATERIAL_SHORT_NAME from Material_Code_Search where (SAP_CODE_NO='"+masterForm.getMaterialCode1()+"' or "
			  + "SAP_CODE_NO='"+masterForm.getMaterialCode2()+"')";
			  try{
				  ResultSet rsShortName=ad.selectQuery(getShortName);
				  while(rsShortName.next()){
					  matShortName=rsShortName.getString("MATERIAL_SHORT_NAME");
				  }
			  }catch(Exception e){
				  e.printStackTrace();
			  }
			if(actionType.equalsIgnoreCase("Save"))
			{
				
				masterForm.setApproveType("Saved");
				if(plantType.equalsIgnoreCase("Plant"))
				{
					
					int materialCodeCount=0;
					
					String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where m.SAP_CODE_NO='"+masterForm.getMaterialCode1()+"' and " +
							"  l.LOCATION_CODE='"+masterForm.getPlant1()+"' and l.LOCATION_CODE=m.LOCATION_ID";
					ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
					while(rsMaterilaCodeCount.next())
					{
						materialCodeCount=rsMaterilaCodeCount.getInt(1);
					}
					if(materialCodeCount>0)
					{
					int requestNo=masterForm.getRequestNo();
					int count=0;
					String getRequestnoCount="select count(*) from code_extenstion_request where REQUEST_NO='"+requestNo+"'";
					ResultSet rsCount=ad.selectQuery(getRequestnoCount);
					while(rsCount.next())
					{
						count=rsCount.getInt(1);
					}
					if(count==0)
					{
					String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_1,MATERIAL_CODE_1,EXTENDED_TO_PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
							" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant1()+"','"+masterForm.getMaterialCode1()+"','"+masterForm.getExtendToPlant1()+"','"+masterForm.getStorageLocationId1()+"'," +
									"'"+masterForm.getExtendToStorageLocation1()+"','"+reqDate+"','"+user.getEmployeeNo()+"','No','','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode1()+"','"+matShortName+"')"; 
					int i=0;
					i=ad.SqlExecuteUpdate(saveData);
					
					if(i>0)
					{
						masterForm.setMassage2("Code extenstion request saved with request number='"+masterForm.getRequestNo()+"'.");
						return displayExtensionForm(mapping, form, request, response);	
					}
					else
						masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
					
					
					}else{
						int requestno=0;
						
						String getMaxRecord="select max(REQUEST_NO) from code_extenstion_request";
						ResultSet rsMaxRecord=ad.selectQuery(getMaxRecord);
						while(rsMaxRecord.next())
						{
							requestno=rsMaxRecord.getInt(1);
						}
						requestno=requestno+1;
						masterForm.setRequestNo(requestno);	
						String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_1,MATERIAL_CODE_1,EXTENDED_TO_PLANT_1,STORAGE_LOCATION_ID_1,EXTENDED_STORAGE_LOCATION_1,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
						" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant1()+"','"+masterForm.getMaterialCode1()+"','"+masterForm.getExtendToPlant1()+"','"+masterForm.getStorageLocationId1()+"'," +
								"'"+masterForm.getExtendToStorageLocation1()+"','"+reqDate+"','"+user.getEmployeeNo()+"','No','','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode1()+"','"+matShortName+"')";
				int i=0;
				i=ad.SqlExecuteUpdate(saveData);
				
				if(i>0)
				{
					masterForm.setMassage2("Code extenstion request saved with new request number='"+masterForm.getRequestNo()+"'.");
					return displayExtensionForm(mapping, form, request, response);	
				}
				else
				{
					masterForm.setMassage1("Error...When saving Material code extenstion products.....");
					actionType=masterForm.getActionType();
					masterForm.setActionType(actionType);
					request.setAttribute("saveButton", "saveButton");
				}
					}
					
					
					}else{
						masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
						actionType=masterForm.getActionType();
						masterForm.setActionType(actionType);
						request.setAttribute("saveButton", "saveButton");
				
						
					}
					
		}else{
			//Selected Type is storage location
			
			int materialCodeCount=0;
			
			String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where SAP_CODE_NO='"+masterForm.getMaterialCode2()+"' " +
					" and l.LOCATION_CODE='"+masterForm.getPlant2()+"' and l.LOCATION_CODE=m.LOCATION_ID ";
			ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
			while(rsMaterilaCodeCount.next())
			{
				materialCodeCount=rsMaterilaCodeCount.getInt(1);
			}
			if(materialCodeCount>0)
			{
			int requestNo=masterForm.getRequestNo();
			int count=0;
			String getRequestnoCount="select count(*) from code_extenstion_request where REQUEST_NO='"+requestNo+"'";
			ResultSet rsCount=ad.selectQuery(getRequestnoCount);
			while(rsCount.next())
			{
				count=rsCount.getInt(1);
			}
			if(count==0)
			{
			String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_2,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,TO_STORAGE_LOCATION,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
					" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant2()+"','"+masterForm.getMaterialCode2()+"','"+masterForm.getFromStorageLocation()+"','"+masterForm.getToStorageLocation()+"'," +
							"'"+reqDate+"','"+user.getEmployeeNo()+"','No','Administrator','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode2()+"','"+matShortName+"')";
			int i=0;
			i=ad.SqlExecuteUpdate(saveData);
				
			if(i>0)
			{
				masterForm.setMassage2("Code extenstion request saved with request number='"+masterForm.getRequestNo()+"'.");
				
				return displayExtensionForm(mapping, form, request, response);	
			}
			else{
				masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
				actionType=masterForm.getActionType();
				masterForm.setActionType(actionType);
				request.setAttribute("saveButton", "saveButton");
			}
			
			}else{
				int requestno=0;
				
				String getMaxRecord="select max(REQUEST_NO) from code_extenstion_request";
				ResultSet rsMaxRecord=ad.selectQuery(getMaxRecord);
				while(rsMaxRecord.next())
				{
					requestno=rsMaxRecord.getInt(1);
				}
				requestno=requestno+1;
				masterForm.setRequestNo(requestno);	
				String saveData="insert into code_extenstion_request(REQUEST_NO,REQUEST_DATE,TYPE,PLANT_2,MATERIAL_CODE_2,FROM_STORAGE_LOCATION,TO_STORAGE_LOCATION,CREATED_DATE,CREATED_BY,last_approver,pending_approver,APPROVE_TYPE,existing_sapNo,MATERIAL_SHORT_NAME)" +
				" values('"+masterForm.getRequestNo()+"','"+reqDate+"','"+masterForm.getPlantType()+"','"+masterForm.getPlant2()+"','"+masterForm.getMaterialCode2()+"','"+masterForm.getFromStorageLocation()+"','"+masterForm.getToStorageLocation()+"'," +
						"'"+reqDate+"','"+user.getEmployeeNo()+"','No','Administrator','"+masterForm.getApproveType()+"','"+masterForm.getMaterialCode2()+"','"+matShortName+"')";
		int i=0;
		i=ad.SqlExecuteUpdate(saveData);
		
		if(i>0)
		{
			masterForm.setMassage2("Code extenstion request saved with new request number='"+masterForm.getRequestNo()+"'.");
			return displayExtensionForm(mapping, form, request, response);	
		}	
			
		else
			masterForm.setMassage1("Error...When saving Material code extenstion products.Please check....");
		actionType=masterForm.getActionType();
		masterForm.setActionType(actionType);
		request.setAttribute("saveButton", "saveButton");
			}
			
			
			}else{
				masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
				actionType=masterForm.getActionType();
				masterForm.setActionType(actionType);
				request.setAttribute("saveButton", "saveButton");
			}
		}				
			}else{
				//update code extension..
				
				String recordStatus="";
				String getRecordStatus="select APPROVE_TYPE from code_extenstion_request where REQUEST_NO='"+masterForm.getRequestNo()+"' ";
				ResultSet rsRecord=ad.selectQuery(getRecordStatus);
				while(rsRecord.next())
				{
					recordStatus=rsRecord.getString("APPROVE_TYPE");
				}
				if(recordStatus.equalsIgnoreCase("Rejected"))
				{
					
					String deleteRecords="delete from All_Request where Req_Id='"+masterForm.getRequestNo()+"' and Req_Type='Code Extention'";
					int deleteStatus=ad.SqlExecuteUpdate(deleteRecords);
					System.out.println("deleteStatus="+deleteStatus);
				}
				
			if(plantType.equalsIgnoreCase("Plant"))	{
				masterForm.setPlant2("");
				masterForm.setMaterialCode2("");
				masterForm.setFromStorageLocation("");
				masterForm.setToStorageLocation("");
			}else{
				masterForm.setPlant1("");
				masterForm.setMaterialCode1("");
				masterForm.setExtendToPlant1("");
				masterForm.setStorageLocationId1("");
				masterForm.setExtendToStorageLocation1("");
			}
				
			int materialCodeCount=0;
			
				if(plantType.equalsIgnoreCase("Plant"))	{
					String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where m.SAP_CODE_NO='"+masterForm.getMaterialCode1()+"' and " +
					"  l.LOCATION_CODE='"+masterForm.getPlant1()+"' and l.LOCID=m.LOCATION_ID ";
						ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
						while(rsMaterilaCodeCount.next())
						{
							materialCodeCount=rsMaterilaCodeCount.getInt(1);
						}
						if(materialCodeCount>0)
						{	
						String updateRecord="update code_extenstion_request set TYPE='"+masterForm.getPlantType()+"',PLANT_1='"+masterForm.getPlant1()+"',MATERIAL_CODE_1='"+masterForm.getMaterialCode1()+"',EXTENDED_TO_PLANT_1='"+masterForm.getExtendToPlant1()+"',STORAGE_LOCATION_ID_1='"+masterForm.getStorageLocationId1()+"',EXTENDED_STORAGE_LOCATION_1='"+masterForm.getExtendToStorageLocation1()+"'," +
								"PLANT_2='"+masterForm.getPlant2()+"',MATERIAL_CODE_2='"+masterForm.getMaterialCode2()+"',FROM_STORAGE_LOCATION='"+masterForm.getFromStorageLocation()+"',TO_STORAGE_LOCATION='"+masterForm.getToStorageLocation()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',MATERIAL_SHORT_NAME='"+matShortName+"'  where REQUEST_NO='"+masterForm.getRequestNo()+"'";
						int i=0;
						i=ad.SqlExecuteUpdate(updateRecord);
						if(i>0){
							masterForm.setMassage2("Code extenstion request updated with  request number='"+masterForm.getRequestNo()+"'.");
							return displayExtensionForm(mapping, form, request, response);	
							
						}
						else{
							masterForm.setMassage1("Error...When updating Material code extenstion.Please check....");
							actionType=masterForm.getActionType();
							masterForm.setActionType(actionType);
							request.setAttribute("saveButton", "saveButton");
						}
						}
						else{
							masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
							actionType=masterForm.getActionType();
							masterForm.setActionType(actionType);
							request.setAttribute("saveButton", "saveButton");
						}
					}
			else{

				String getMaterialCodeCount="select count(*) from Material_Code_Search as m,Location as l where SAP_CODE_NO='"+masterForm.getMaterialCode2()+"' " +
				" and l.LOCATION_CODE='"+masterForm.getPlant2()+"' and l.LOCID=m.LOCATION_ID ";
				ResultSet rsMaterilaCodeCount=ad.selectQuery(getMaterialCodeCount);
				while(rsMaterilaCodeCount.next())
				{
					materialCodeCount=rsMaterilaCodeCount.getInt(1);
				}
				if(materialCodeCount>0)
				{	
				String updateRecord="update code_extenstion_request set TYPE='"+masterForm.getPlantType()+"',PLANT_1='"+masterForm.getPlant1()+"',MATERIAL_CODE_1='"+masterForm.getMaterialCode1()+"',EXTENDED_TO_PLANT_1='"+masterForm.getExtendToPlant1()+"',STORAGE_LOCATION_ID_1='"+masterForm.getStorageLocationId1()+"',EXTENDED_STORAGE_LOCATION_1='"+masterForm.getExtendToStorageLocation1()+"'," +
						"PLANT_2='"+masterForm.getPlant2()+"',MATERIAL_CODE_2='"+masterForm.getMaterialCode2()+"',FROM_STORAGE_LOCATION='"+masterForm.getFromStorageLocation()+"',TO_STORAGE_LOCATION='"+masterForm.getToStorageLocation()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',MATERIAL_SHORT_NAME='"+matShortName+"' where REQUEST_NO='"+masterForm.getRequestNo()+"'";
				int i=0;
				i=ad.SqlExecuteUpdate(updateRecord);
				if(i>0){
					masterForm.setMassage2("Code extenstion request updated with  request number='"+masterForm.getRequestNo()+"'.");
					
					return displayExtensionForm(mapping, form, request, response);	
				}
				else
				{
					masterForm.setMassage1("Error...When updating Material code extenstion.Please check....");
					actionType=masterForm.getActionType();
					masterForm.setActionType(actionType);
					request.setAttribute("saveButton", "saveButton");
				}
				}
				else{
					masterForm.setMassage1("Material code does not exist in selected plant.Please Check...");
					actionType=masterForm.getActionType();
					masterForm.setActionType(actionType);
					request.setAttribute("saveButton", "saveButton");
				}
			}
			
		}
			
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAMe,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList1=new ArrayList();
			ArrayList locationLabelList1=new ArrayList();
			
			while(rsLoc.next()) {
				locationList1.add(rsLoc.getString("location_code"));
				locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
			}
			masterForm.setLocationIdList1(locationList1);
			masterForm.setLocationLabelList1(locationLabelList1);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			
	String materialCode1=masterForm.getMaterialCode1();
			
			if(materialCode1!=null){
				//get mat tyep
				
			if(!materialCode1.equalsIgnoreCase("")){
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
					"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			}
			}
			
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(plantType.equalsIgnoreCase("Plant"))
		{
			request.setAttribute("plantMandatory", "plantMandatory");
			
		}
		else{
			request.setAttribute("storageMandatory", "storageMandatory");
			
		}
		String actionType=masterForm.getActionType();
		if(actionType.equalsIgnoreCase("Update"))
			request.setAttribute("saveButton", "saveButton");
		masterForm.setActionType(actionType);
	return mapping.findForward("displayNewForm");
	}
	public ActionForward getPlantDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
		masterForm.setStorageLocationType("Plant");
		request.setAttribute("plantMandatory", "plantMandatory");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		String availableLoc=user.getAvailableLocations();
		if(availableLoc.equalsIgnoreCase(""))
			availableLoc="0";
		ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
		ArrayList locationList1=new ArrayList();
		ArrayList locationLabelList1=new ArrayList();
		
		while(rsLoc.next()) {
			locationList1.add(rsLoc.getString("location_code"));
			locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
		}
		masterForm.setLocationIdList1(locationList1);
		masterForm.setLocationLabelList1(locationLabelList1);
		
		LinkedList storageID=new LinkedList();
		LinkedList storageName=new LinkedList();
		String materialCode1=masterForm.getMaterialCode1();
		if(materialCode1!=null){
			//get mat tyep
			
		if(!materialCode1.equalsIgnoreCase("")){
			
		String getAvalLoc="select * from Material_Code_Search where SAP_CODE_NO='"+materialCode1+"'";
		ResultSet rsLocat=ad.selectQuery(getAvalLoc);
		String avaLoc="";
		while(rsLocat.next()){
			avaLoc=avaLoc+"'"+rsLocat.getString("LOCATION_ID")+"'"+",";
		}
		if(!(avaLoc.equalsIgnoreCase(""))){
		avaLoc=avaLoc.substring(0, (avaLoc.length()-1));
        String getLoc="select LOCID,LOCNAMe,location_code from location where location_code in ("+avaLoc+")";
		ResultSet rs11 = ad.selectQuery(getLoc);
		 locationList=new ArrayList();
		 locationLabelList=new ArrayList();
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
		}
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
			
		String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
				"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
		ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
		while(rsStrogeLocation.next()){
			storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
			storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
		}
		}
		}
		masterForm.setStorageID(storageID);
		masterForm.setStorageIDName(storageName);
		
		String actionType=masterForm.getActionType();
		if(actionType.equalsIgnoreCase("Save"))
			request.setAttribute("saveButton", "saveButton");
		else
			request.setAttribute("updateButton", "updateButton");
		masterForm.setActionType(actionType);
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("plantMandatory", "plantMandatory");
		
		return mapping.findForward("displayNewForm");
	}
	
	public ActionForward getStorageLocationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		masterForm.setPlantType("Storage Location");
		try{
			ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAMe"));
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ArrayList locationList1=new ArrayList();
			ArrayList locationLabelList1=new ArrayList();
			masterForm.setLocationIdList1(locationList1);
			masterForm.setLocationLabelList1(locationLabelList1);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			
			String materialCode1=masterForm.getMaterialCode2();
			
			if(materialCode1!=null){
				//get mat tyep
				
			if(!materialCode1.equalsIgnoreCase("")){
			
				
				String getAvalLoc="select * from Material_Code_Search where SAP_CODE_NO='"+materialCode1+"'";
				ResultSet rsLocat=ad.selectQuery(getAvalLoc);
				String avaLoc="";
				while(rsLocat.next()){
					avaLoc=avaLoc+"'"+rsLocat.getString("LOCATION_ID")+"'"+",";
				}
				if(!(avaLoc.equalsIgnoreCase(""))){
				avaLoc=avaLoc.substring(0, (avaLoc.length()-1));
				
				ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where location_code in("+avaLoc+")");
				 locationList1=new ArrayList();
				 locationLabelList1=new ArrayList();
				
				while(rsLoc.next()) {
					locationList1.add(rsLoc.getString("location_code"));
					locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
				}
				}
				masterForm.setLocationIdList1(locationList1);
				masterForm.setLocationLabelList1(locationLabelList1);
				
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
					"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			}
			}
			
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
			String actionType=masterForm.getActionType();
			if(actionType.equalsIgnoreCase("Save"))
				request.setAttribute("saveButton", "saveButton");
			else
				request.setAttribute("updateButton", "updateButton");
			masterForm.setActionType(actionType);
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			request.setAttribute("storageMandatory", "storageMandatory");
			
			
		return mapping.findForward("displayNewForm");
	}
	
	public ActionForward displayExtensionForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		MaterialCodeExtensionForm masterForm=(MaterialCodeExtensionForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		try{
			
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
			
			String availableLoc=user.getAvailableLocations();
			if(availableLoc.equalsIgnoreCase(""))
				availableLoc="0";
			ResultSet rsLoc = ad.selectQuery("select LOCID,LOCNAME,location_code from location where LOCID in("+availableLoc+")");
			ArrayList locationList1=new ArrayList();
			ArrayList locationLabelList1=new ArrayList();
			
			while(rsLoc.next()) {
				locationList1.add(rsLoc.getString("location_code"));
				locationLabelList1.add(rsLoc.getString("location_code")+"-"+rsLoc.getString("LOCNAMe"));
			}
			masterForm.setLocationIdList1(locationList1);
			masterForm.setLocationLabelList1(locationLabelList1);
			
			LinkedList storageID=new LinkedList();
			LinkedList storageName=new LinkedList();
			
			String materialCode1=masterForm.getMaterialCode1();
			
			if(materialCode1!=null){
				//get mat tyep
				
			if(!materialCode1.equalsIgnoreCase("")){
			String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE=(select Distinct(m.MATERIAL_GROUP_ID) from Material_Code_Search as s," +
					"MATERIAL_TYPE as m where s.SAP_CODE_NO='"+materialCode1+"' and m.id=s.MATERIAL_TYPE)";
			ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
			while(rsStrogeLocation.next()){
				storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
				storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
			}
			}
			}
			
			masterForm.setStorageID(storageID);
			masterForm.setStorageIDName(storageName);
		
			int requestno=0;
			
			String getMaxRecord="select max(REQUEST_NO) from code_extenstion_request";
			ResultSet rsMaxRecord=ad.selectQuery(getMaxRecord);
			while(rsMaxRecord.next())
			{
				requestno=rsMaxRecord.getInt(1);
			}
			requestno=requestno+1;
			masterForm.setRequestNo(requestno);
			masterForm.setRequestDate(EMicroUtils.getCurrentSysDate());
			masterForm.setActionType("Save");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//request.setAttribute("plantNotMandatory", "plantNotMandatory");
		//request.setAttribute("storageNotMandatory", "storageNotMandatory");
		request.setAttribute("saveButton", "saveButton");
		
		return mapping.findForward("displayNewForm");
	}

}
