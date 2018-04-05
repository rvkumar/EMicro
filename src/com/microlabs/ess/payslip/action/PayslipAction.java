/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.microlabs.ess.payslip.action;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.payslip.db.payslipDB;
import com.microlabs.ess.payslip.form.PayslipForm;
import com.microlabs.utilities.UserInfo;
import com.sap.conn.jco.ext.DestinationDataProvider;

/** 
 * MyEclipse Struts
 * Creation date: 04-27-2013
 * 
 * XDoclet definition:
 * @struts.action path="/attendence" name="attendenceForm" input="/form/attendence.jsp" parameter="method" scope="request" validate="true"
 */
public class PayslipAction extends DispatchAction {
	/*
	 * Generated Methods
	 */

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	EssDao ad=new EssDao();
	private static final String DESTINATION = "SAP_DESTINATION";
	private void connectSAP() {
		try {
		
		Properties connectProperties = new Properties();// TODO change the
													// details
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
				"192.168.1.33");
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
		connectProperties
				.setProperty(DestinationDataProvider.JCO_CLIENT, "150");
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,
				"rfcdev");
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
				"Init123#");
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "EN");
		File destCfg = new File(DESTINATION + ".jcoDestination");
		
			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "SAP_DESTINATION config");
			fos.close();
	
		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",e);
		}
	}
	
	public ActionForward getPaySlipDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//yyyyMMdd
		PayslipForm payslipForm = (PayslipForm) form;
		submit(mapping, form, request, response);
		/*payslipDB dao=new payslipDB();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo=user.getEmployeeNo();
		String month=payslipForm.getDat();
		String a[]=month.split("/");
		month=a[2]+a[1]+a[0];
		JCoFunction function=null;
		int payGrpID=0;
		try {
		String getPayGrupID="select PAY_GROUP from emp_official_info where PERNR='"+empNo+"'";
		ResultSet rsPayId=ad.selectQuery(getPayGrupID);
		while(rsPayId.next()){
			payGrpID=rsPayId.getInt(1);
		}
			
					
			
			connectSAP();
			JCoDestination destination = JCoDestinationManager
					.getDestination(DESTINATION);// TODO change to real
													// destination
			if (destination == null) {
				
				destination = JCoDestinationManager
						.getDestination(DESTINATION);// TODO change to
															// real
															// destination
				if (destination == null) {
					throw new RuntimeException(
							"Could not connect to SAP, destination not found.");
				}
			}
			 function = destination.getRepository().getFunction(
					"ZBAPI_MMAS");
			 function = destination.getRepository().getFunction(
						"ZBAPI_HR_PAYSLIP");
			if (function == null) {
				throw new RuntimeException(" ZBAPI_HR_PAYSLIP not found in SAP.");//
			}
			function.getImportParameterList().setValue("PAYGROUP",11 );
			function.getImportParameterList().setValue("PERNR", 85985);
			function.getImportParameterList().setValue("MONTH", month);
			function.execute(destination);
			
			JCoTable returnTable = function.getTableParameterList().getTable("PAYSLIP_H");
			System.out.println("test");
			List listPayH=new LinkedList();
			if(returnTable.getNumRows() > 0){
				returnTable.firstRow();
				do{
					PayslipForm p=new PayslipForm();
					p.setPayGroup(returnTable.getString("PAYGROUP"));
					p.setEmpcod(Integer.toString(returnTable.getInt("PERNR")));
					p.setPaymnt(returnTable.getString("PRMNTH"));
					p.setComaddr(returnTable.getString("BUKRS"));
					p.setLocation(returnTable.getString("WERKS"));
					p.setGRD_ID(returnTable.getInt("STAFFCAT"));
					p.setSTATE_ID(returnTable.getInt("STATE"));
					p.setLOC_ID(returnTable.getInt("LOCID"));
					p.setGRD_ID(returnTable.getInt("GRDID"));
					p.setDSG_ID(returnTable.getInt("DSGID"));
					p.setDPT_ID(returnTable.getInt("DPTID"));
					p.setCUR_KEY(returnTable.getString("WAERS"));
					p.setPAY_MODE(returnTable.getString("PAYMODE"));
					p.setBANK_ID(returnTable.getInt("BANKID"));
					p.setBANK_ACC_TYP(returnTable.getString("BACCTYP"));
					p.setBANK_ACC_NO(returnTable.getString("BACCNO"));
					p.setBANK_BRANCH(returnTable.getString("BRANCH"));
					p.setIFSC_CODE(returnTable.getString("IFSC_CODE"));
					p.setMICR_CODE(returnTable.getString("MICR_CODE"));
					p.setWKG_DAYS(returnTable.getDouble("WKGDAYS"));
					p.setWKD_DAYS(returnTable.getDouble("WKDDAYS"));
					p.setLEV_DAYS(returnTable.getDouble("LEVDAYS"));
					p.setLOP_DAYS(returnTable.getDouble("LOPDAYS"));
					p.setTOT_EARN(returnTable.getDouble("TOTEARN"));
					p.setTOT_DEDN(returnTable.getDouble("TOTDEDN"));
					p.setTOT_AEARN(returnTable.getDouble("TOTARRE"));
					p.setTOT_ADEDN(returnTable.getDouble("TOTARRD"));
					String creationDt=convertDate(returnTable.getDate("CRDAT"));
					p.setCR_DAT(creationDt);
					String CPUTM="";
					CPUTM=returnTable.getTime("CPUTM").toString();
					String a1[]=CPUTM.split(" ");
					if(a1.length==6)
						CPUTM=a1[3];
					p.setCPU_TM(CPUTM);
					p.setENT_USER_NAME(returnTable.getString("UNAME"));
					listPayH.add(p);
					}while(returnTable.nextRow());
				String deleteRecords="delete from PAYSLIP_H where PERNR='"+empNo+"'";
				ad.SqlExecuteUpdate(deleteRecords);
				
				dao.updateAPAYSLIP_HDetails(listPayH,empNo);
			}
			
			
			JCoTable returnTable_PAYSLIP_D = function.getTableParameterList().getTable("PAYSLIP_D");
			List<PayslipForm> listPayD= new LinkedList<PayslipForm>();
			if(returnTable_PAYSLIP_D.getNumRows() > 0){
				while(returnTable_PAYSLIP_D.nextRow()){
					PayslipForm p=new PayslipForm();
					p.setPayGroup(returnTable_PAYSLIP_D.getString("PAYGROUP"));
					p.setEmpcod(Integer.toString(returnTable_PAYSLIP_D.getInt("PERNR")));
					p.setPaymnt(returnTable_PAYSLIP_D.getString("PRMNTH"));
					p.setSAL_ID(returnTable_PAYSLIP_D.getInt("SALID"));
					p.setINVE_CO(returnTable_PAYSLIP_D.getString("INVECO"));
					p.setSAL_AMT(returnTable_PAYSLIP_D.getDouble("SALAMT"));
					p.setARR_AMT(returnTable_PAYSLIP_D.getDouble("ARRAMT"));
					p.setINP_SLIP(returnTable_PAYSLIP_D.getDouble("INPSLIP"));
					String creationDt=convertDate(returnTable_PAYSLIP_D.getDate("CRDAT"));
					p.setCR_DAT(creationDt);
					String CPUTM="";
					CPUTM=returnTable_PAYSLIP_D.getTime("CPUTM").toString();
					String a1[]=CPUTM.split(" ");
					if(a1.length==6)
						CPUTM=a1[3];
					p.setCPU_TM(CPUTM);
					p.setENT_USER_NAME(returnTable_PAYSLIP_D.getString("UNAME"));
					listPayD.add(p);
				}
				String deleteRecords="delete from PAYSLIP_D where PERNR='"+empNo+"'";
				ad.SqlExecuteUpdate(deleteRecords);
				dao.updateAPAYSLIP_D(listPayD,empNo);
				
			}
			if(returnTable.getNumRows() > 0 && returnTable_PAYSLIP_D.getNumRows() > 0){
				submit(mapping, form, request, response);
			}
			if(returnTable.getNumRows() == 0 && returnTable_PAYSLIP_D.getNumRows() == 0){

				
				JCoTable returnStatus = function.getTableParameterList().getTable("RETURN");
				if(returnStatus.getNumRows() > 0){
					char  c=returnStatus.getChar("TYPE");
					if(c=='E'){
						payslipForm.setMessage(returnStatus.getString("MESSAGE"));
					}
				}
				
			
			}
			
			
			
		} catch (AbapException e) {
			System.out.println(e.toString());// TODO change to log
			
		} catch (JCoException e) {
			System.out.println(e.toString());// TODO change to log
			System.out.println(e.getMessageType());
			e.printStackTrace();
			
		}catch(Throwable e){
			System.out.println(e.toString());// TODO change to log
			e.printStackTrace();
		} finally {
			//rs.close();
		}*/
		return mapping.findForward("home");
	}
public String convertDate(Date date1){
		
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		String formatedDate="";
		try {
			Date date = formatter.parse(date1.toString());

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			 formatedDate=cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.DATE);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return formatedDate;
	}
	public ActionForward generateIntoPDF(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PayslipForm payslipForm = (PayslipForm) form;// TODO Auto-generated method stub
		
		payslipDB payslipdb=new payslipDB();
		HttpSession session=request.getSession();
		 List toPdf=new ArrayList();
		 toPdf=(ArrayList)session.getAttribute("clist");
		 Document document = new Document();
		 File pdffile=new File("c:/pdf_genrated.pdf");
			int i=0;
			String joiningDate="";
		 try{
			 String empcode = (String)session.getAttribute("employeenumber");
			 String month = (String)session.getAttribute("paydate");
			 Connection con = ConnectionFactory.getConnection();
			 
			 String company="";
			 String bankName="";
			 String empName="";
			 String bankAccNo="";
			 String address="";
			 String pfNo="";
			 String esiNo="";
			 String designation="";
			 String panNo="";
			 String department="";
			 String daysPaid="";
			 String dateOfJoining="";
			 String wkgDays="";
			 String daysWkd="";
			 String lop="";
			 String reportingMang="";
			 String eL="";
			 String sL="";
			 String cL="";
			 ResultSet rs4=null;
				PayslipForm payslipForm1=null;
			String arrears_totEarn="";
			String current_totEarn="";
			String arrears_totDeduc="";
			String current_totDeduc="";
			String netPay="";
			
				
			 
			 Statement st1=con.createStatement();
				Statement st2a=con.createStatement();
				Statement st2b=con.createStatement();
				Statement st3=con.createStatement();
				Statement st4=con.createStatement();
				Statement st5=con.createStatement();
				Statement st6=con.createStatement();
				Statement st7=con.createStatement();
				
				ArrayList array1=new ArrayList();
				ArrayList array2=new ArrayList();
				
				String sql1="SELECT PAYH.PERNR,PAYH.RPT_MNG,PAYH.BANK_ACC_NO,PAYH.PR_MNTH AS PAYMNT," +
		"EMPO.EMP_FULLNAME as emp_name,DESG.DSGSTXT,DEPT.DPTSTXT,PAYH.PR_MNTH,COM.BUTXT as comaddr," +
		"COM.ORT01,BANK.BNAME,EMPO.DOJ,EMPO.ESINO,EMPO.PFNO,EMPO.PANNO,PAYH.WKG_DAYS,PAYH.WKD_DAYS,PAYH.LEV_DAYS," +
		"PAYH.LOP_DAYS,PAYH.TOT_EARN,PAYH.TOT_DEDN,PAYH.TOT_AEARN,PAYH.TOT_ADEDN,PAYH.NET_EARN FROM PAYSLIP_H as PAYH INNER JOIN " +
		"Company AS COM ON PAYH.COMP_CODE=COM.BUKRS INNER JOIN " +
		"Bank AS BANK ON PAYH.BANK_ID=BANK.BANKID INNER JOIN Designation AS DESG ON PAYH.DSG_ID=DESG.DSGID " +
		"INNER JOIN Department AS DEPT ON PAYH.DPT_ID=DEPT.DPTID INNER JOIN emp_official_info AS EMPO " +
		"ON PAYH.PERNR=EMPO.PERNR AND PAYH.PERNR='"+empcode+"' AND PAYH.PR_MNTH='"+month+"'";
				
				
				ResultSet rs1=st1.executeQuery(sql1);
				
				if (rs1.next()) {
					
					i=1;
					
					String rep_mng=rs1.getString("RPT_MNG");
							
				//WRITE CODE
					
					company=rs1.getString("comaddr");
					month=rs1.getString("PAYMNT");
					empcode=rs1.getString("PERNR");
					
					bankName=rs1.getString("BNAME");
					empName=rs1.getString("emp_name");
					bankAccNo=rs1.getString("BANK_ACC_NO");
					pfNo=rs1.getString("PFNO");
					esiNo=rs1.getString("ESINO");
					designation=rs1.getString("DSGSTXT");
					panNo=rs1.getString("PANNO");
					department=rs1.getString("DPTSTXT");
				
					double noofday = rs1.getDouble("WKG_DAYS");
					double nooflop = rs1.getDouble("LOP_DAYS");
					double paydays=noofday-nooflop;
					
					payslipForm.setDaypayd(Double.toString(paydays));
					daysPaid=Double.toString(paydays);
					payslipForm.setDoj(rs1.getString("DOJ"));
					String a[]=rs1.getString("DOJ").split(" ");
					joiningDate=a[0];
					String b[]=joiningDate.split("-");
					joiningDate=b[2]+"/"+b[1]+"/"+b[0];
					
					payslipForm.setWorgdays(rs1.getString("WKG_DAYS"));
					wkgDays=rs1.getString("WKG_DAYS");
					payslipForm.setDaywrkd(rs1.getString("WKD_DAYS"));
					daysWkd=rs1.getString("WKD_DAYS");
					payslipForm.setLop(rs1.getString("LOP_DAYS"));
					lop=rs1.getString("LOP_DAYS");
					
					 
					/*payslipForm.setEl(rs1.getString("comaddr"));
					payslipForm.setSl(rs1.getString("comaddr"));
					payslipForm.setCl(rs1.getString("comaddr"));*/
					
					payslipForm.setArrears_totEarn(rs1.getString("TOT_AEARN"));
					arrears_totEarn=rs1.getString("TOT_AEARN");
					current_totEarn=rs1.getString("TOT_EARN");
					payslipForm.setCurrent_totEarn(rs1.getString("TOT_EARN"));
					payslipForm.setArrears_totDeduc(rs1.getString("TOT_ADEDN"));
					arrears_totDeduc=rs1.getString("TOT_ADEDN");
					payslipForm.setCurrent_totDeduc(rs1.getString("TOT_DEDN"));
					current_totDeduc=rs1.getString("TOT_DEDN");
					payslipForm.setNetpay(rs1.getString("NET_EARN"));
					netPay=rs1.getString("NET_EARN");
					String sql2a="SELECT ADDR.address_line1,ADDR.address_line2,ADDR.address_line3 from emp_address AS ADDR " +
						"INNER JOIN  PAYSLIP_H AS PAYH ON PAYH.PERNR=ADDR.employee_no AND ADDR.address_type='Permanent' AND PAYH.PERNR='"+empcode+"'";
				
					ResultSet rs2a=st2a.executeQuery(sql2a);
				
					if (rs2a.next()) {
						
						//WRITE CODE
						payslipForm.setEmpaddr(rs2a.getString("address_line1")+" "+ rs2a.getString("address_line2")+" "+rs2a.getString("address_line3"));
						address=rs2a.getString("address_line1")+" "+ rs2a.getString("address_line2")+" "+rs2a.getString("address_line3");
					}
					
					else
					{
						String sql2b="SELECT ADDR.address_line1,ADDR.address_line2,ADDR.address_line3 from emp_address AS ADDR " +
						"INNER JOIN  PAYSLIP_H AS PAYH ON PAYH.PERNR=ADDR.employee_no AND ADDR.address_type='Temperory' AND PAYH.PERNR='"+empcode+"'";
				
					ResultSet rs2b=st2b.executeQuery(sql2b);
				
					if (rs2b.next()) {
						
						//WRITE CODE
						payslipForm.setEmpaddr(rs2b.getString("address_line1")+" "+rs2b.getString("address_line2")+" "+rs2b.getString("address_line3"));
					}
					}
					
					
					String sql3="SELECT EMP.emp_name from emp_master AS EMP " +
					"INNER JOIN PAYSLIP_H AS PAYH ON PAYH.RPT_MNG=EMP.emp_id WHERE PAYH.RPT_MNG='"+rep_mng+"'";
			
					ResultSet rs3=st3.executeQuery(sql3);
					
					if (rs3.next()) {
					
					//WRITE CODE
						payslipForm.setRepmng(rs3.getString("emp_name"));
						reportingMang=rs3.getString("emp_name");
					}
					
					String sql4="SELECT SAL.SAL_SNAME,SAL.SAL_TYPE,PAYD.SAL_AMT,PAYD.ARR_AMT FROM PAYSLIP_D AS PAYD INNER JOIN SALARY_M AS SAL ON PAYD.SAL_ID=SAL.SAL_ID WHERE PAYD.PERNR='"+empcode+"' AND PAYD.PR_MNTH='"+month+"' AND SAL.SAL_TYPE='Earning'";
			
					rs4=st4.executeQuery(sql4);
					
					 
					
					if(rs4.next()) {
						payslipForm1=new PayslipForm();
						payslipForm1.setEh(rs4.getString("SAL_SNAME"));
					
						 
						payslipForm1.setEa(rs4.getString("ARR_AMT"));
						payslipForm1.setEc(rs4.getString("SAL_AMT"));
						array1.add(payslipForm1);
						request.setAttribute("earnings", "earnings");
					}
					
					while (rs4.next()) {
						//WRITE CODE
						payslipForm1=new PayslipForm();
						payslipForm1.setEh(rs4.getString("SAL_SNAME"));
						payslipForm1.setEa(rs4.getString("ARR_AMT"));
						payslipForm1.setEc(rs4.getString("SAL_AMT"));
						 
						array1.add(payslipForm1);
						
					}
					
					
					
					String sql5="SELECT SAL.SAL_SNAME,PAYD.SAL_AMT,PAYD.ARR_AMT FROM PAYSLIP_D AS PAYD INNER JOIN SALARY_M AS SAL ON PAYD.SAL_ID=SAL.SAL_ID WHERE PAYD.PERNR='"+empcode+"' AND PAYD.PR_MNTH='"+month+"' AND SAL.SAL_TYPE='Deduction'";
					
					ResultSet rs5=st5.executeQuery(sql5);
					
					PayslipForm payslipForm2=null;
					if(rs5.next()) {
						payslipForm2=new PayslipForm();
						payslipForm2.setDh(rs5.getString("SAL_SNAME"));
						payslipForm2.setDa(rs5.getString("ARR_AMT"));
						payslipForm2.setDc(rs5.getString("SAL_AMT"));
						array2.add(payslipForm2);
						request.setAttribute("deduction", "deduction");
					}
					while (rs5.next()) {
						//WRITE CODE
						payslipForm2=new PayslipForm();
						payslipForm2.setDh(rs5.getString("SAL_SNAME"));
						payslipForm2.setDa(rs5.getString("ARR_AMT"));
						payslipForm2.setDc(rs5.getString("SAL_AMT"));
						array2.add(payslipForm2);
					
					}
					
					
					
					//con.close();
					payslipForm.setMesage("");
					
				}
				 Image image = Image.getInstance ("C:/Tomcat 6.0/webapps/EMicro/images/logo.png");
			     image.scaleAbsolute(40f, 40f);
			 
			 PdfWriter.getInstance(document, new FileOutputStream(pdffile));
		        document.open();
		        
		        PdfPTable table=new PdfPTable(4);
		        PdfPCell cell7 = new PdfPCell (image);
		       
		        cell7.setHorizontalAlignment (Element.ALIGN_CENTER);
		        Font fontbold = FontFactory.getFont("MS Sans Serif", 12, Font.BOLD);
                PdfPCell cell = new PdfPCell (new Paragraph ("Payslip",fontbold));
              
		      cell.setColspan (3);
		      cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		     
		     
		      cell.setPadding (10.0f);
		     					               
              table.addCell(cell7);
		      table.addCell(cell);						               

		      table.addCell("Company Address");
		      table.addCell(company);
		      table.addCell("Month");
		      table.addCell(month);
		      table.addCell("Employee Number"); table.addCell(empcode); table.addCell("Bank Name"); table.addCell(bankName);
		      table.addCell("Employee Name");table.addCell(empName); table.addCell("Bank Acc No");table.addCell(bankAccNo);
		      table.addCell("Address");table.addCell(address);  table.addCell("PF No");  table.addCell(pfNo);
		      table.addCell("ESI No");
		      table.addCell(esiNo);
		      table.addCell("Designation");
		      table.addCell(designation);
		      table.addCell("PAN No");
		      table.addCell(panNo);
		      table.addCell("Department");table.addCell(department);  table.addCell("Days Paid");  table.addCell(daysPaid);
		    
		      PdfPTable table2=new PdfPTable(6);
		      
		      PdfPCell cell2 = new PdfPCell();

		      cell2.setColspan (6);
		      
		      table2.addCell("DOJ");table2.addCell(joiningDate);  table2.addCell("Days");  table2.addCell("Wkg Days-"+wkgDays); table2.addCell("Days Wkd-"+daysWkd);
		     table2.addCell("LOP-"+lop); 
		      table.addCell("Reporting Manager");table.addCell(reportingMang);  table.addCell("Leave Balance");  table.addCell(pfNo);
		     
		   
		     
		      
		      //earnings
		      PdfPTable table3=new PdfPTable(3);
		      
		      PdfPCell cell3 = new PdfPCell();

		      cell3.setColspan (3);
		      table3.addCell("Earnings");table3.addCell("Arrears");  table3.addCell("Current");
			String getEarnings="SELECT SAL.SAL_SNAME,SAL.SAL_TYPE,PAYD.SAL_AMT,PAYD.ARR_AMT FROM PAYSLIP_D AS PAYD INNER JOIN SALARY_M AS SAL ON " +
					"PAYD.SAL_ID=SAL.SAL_ID WHERE PAYD.PERNR='"+empcode+"' AND PAYD.PR_MNTH='"+month+"' AND SAL.SAL_TYPE='Earning'";
			ResultSet rsEarnign=st6.executeQuery(getEarnings);
	
			while(rsEarnign.next())
			{
				table3.addCell(rsEarnign.getString("SAL_SNAME"));
				 table3.addCell(rsEarnign.getString("SAL_AMT"));
				 table3.addCell(rsEarnign.getString("ARR_AMT"));
			}
			
			
			
		   
		 table3.addCell("Total Earnings");
		 table3.addCell(arrears_totEarn);
		 table3.addCell(current_totEarn);
		 
		 PdfPTable table5=new PdfPTable(3);
	      
	      PdfPCell cell5 = new PdfPCell();

	      cell5.setColspan (3);
		 
	      table5.addCell("Deductions");table5.addCell("Arrears");  table5.addCell("Current");
		
		String getDeductions="SELECT SAL.SAL_SNAME,PAYD.SAL_AMT,PAYD.ARR_AMT FROM PAYSLIP_D AS PAYD INNER JOIN SALARY_M AS SAL ON PAYD.SAL_ID=SAL.SAL_ID " +
				"WHERE PAYD.PERNR='"+empcode+"' AND PAYD.PR_MNTH='"+month+"' AND SAL.SAL_TYPE='Deduction'";
		ResultSet rsDeduction=st7.executeQuery(getDeductions);
		
		while(rsDeduction.next())
		{
			table5.addCell(rsDeduction.getString("SAL_SNAME"));
			table5.addCell(rsDeduction.getString("ARR_AMT"));
			table5.addCell(rsDeduction.getString("SAL_AMT"));
			
		}
		
		   PdfPTable table1=new PdfPTable(2);
		      
		      PdfPCell cell1 = new PdfPCell(table3);
		      PdfPCell cell11 = new PdfPCell(table5);
		      
		      table1.addCell(cell1);
		      table1.addCell(cell11);
		     
		      
		      
			      
		table5.addCell("Total Deductions");
		 table5.addCell(arrears_totDeduc);
		 table5.addCell(current_totDeduc);
		 
		 PdfPTable table4=new PdfPTable(2);
	      
	      PdfPCell cell4 = new PdfPCell();

	      cell4.setColspan (2);
		 
	      PdfPCell cell8 = new PdfPCell (new Paragraph ("Net",fontbold));
	      cell8.setHorizontalAlignment(Element.ALIGN_CENTER);
		   table4.addCell(cell8);
		   table4.addCell(netPay);
		       
		  	//document.add(image);
		      document.add(table);
		      document.add(table2);
		      
		      document.add(table1);
		      
		      document.add(table4);
		        
		    	
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
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PayslipForm payslipForm = (PayslipForm) form;// TODO Auto-generated method stub
		
		payslipDB payslipdb=new payslipDB();
		payslipForm.setMesage("");
		 HttpSession session = request.getSession();
		 String employeenumber = (String)session.getAttribute("employeenumber");
			UserInfo user=(UserInfo)session.getAttribute("user");
			String empNo=user.getEmployeeNo();
		 //payslipForm.setEmpcode(employeenumber);
		 session.setAttribute("paydate", 2013-11-30);
		int i=0;
		i=payslipdb.submitdb(empNo,"2013-11-30",request,payslipForm);
		
		if(i>0)
		{
			request.setAttribute("detailed_payslip", "detailed_payslip");
			//att.displaydb(payslipForm.getEmpcode(),request,payslipForm);
			
		}
		
		else
		{
			payslipForm.setMesage("Employee Records not found");
		}
		return monthyear(mapping, form, request, response);
				
	}
	
	
	public ActionForward print(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PayslipForm payslipForm = (PayslipForm) form;// TODO Auto-generated method stub
		
		payslipDB payslipdb=new payslipDB();
		
		 HttpSession session = request.getSession();
		 String employeenumber = (String)session.getAttribute("employeenumber");
		 String paydate = (String)session.getAttribute("paydate");
		 //payslipForm.setEmpcode(employeenumber);
		
		int i=0;
		i=payslipdb.submitdb(employeenumber,paydate,request,payslipForm);
		
		if(i>0)
		{
			return mapping.findForward("print");
		}
		
		else
		{
			
		}
		return monthyear(mapping, form, request, response);
				
	}
	
	public ActionForward monthyear(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PayslipForm payslipForm = (PayslipForm) form;// TODO Auto-generated method stub
		
		//payslipDB att=new payslipDB();
		
		ArrayList ar_id=new ArrayList();
		ArrayList ar_name=new ArrayList();
	//	att.monthyear(request,payslipForm);
		
		try{
		
		Calendar ca = new GregorianCalendar();
		int iTDay = ca.get(Calendar.DATE);
		int iTYear = ca.get(Calendar.YEAR);
		int iTMonth = ca.get(Calendar.MONTH);
		GregorianCalendar cal = new GregorianCalendar(iTYear, iTMonth, iTDay);
	  int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
	 int month=cal.get(Calendar.MONTH);
	 int year=cal.get(Calendar.YEAR);
	 
	String month1=new SimpleDateFormat("MMMM").format(new Date(2008,iTMonth, 01));
	String tableName1="01"+"/"+(iTMonth+1)+"/"+iTYear;
	ar_id.add(tableName1);
	ar_name.add(month1+" "+iTYear);
	if(month1.equals("January")){
		iTYear=iTYear-1;
		iTMonth=12-iTMonth;
	}else{
		iTMonth=iTMonth-1;
	}
	String month2=new SimpleDateFormat("MMMM").format(new Date(2008,(iTMonth), 01));
	String tableName2="01"+"/"+(iTMonth+1)+"/"+iTYear;;
	ar_id.add(tableName2);
	ar_name.add(month2+" "+iTYear);
	if(month2.equals("January")){
		iTYear=iTYear-1;
		iTMonth=11-iTMonth;	
	}else{
		iTMonth=iTMonth-1;
	}
	String month3=new SimpleDateFormat("MMMM").format(new Date(2008,(iTMonth), 01));
	String tableName3="01"+"/"+(iTMonth+1)+"/"+iTYear;;
	ar_id.add(tableName3);
	ar_name.add(month3+" "+iTYear);
		
	payslipForm.setAr_id(ar_id);
	payslipForm.setAr_name(ar_name);
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
		return mapping.findForward("home");
		
	}
	
}