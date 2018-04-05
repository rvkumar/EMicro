package com.microlabs.hr.action;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.attendence.db.ConnectionFactory1;
import com.attendence.form.AttendenceForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.hr.dao.SAPAttendenceDAO;
import com.microlabs.hr.form.SAPAttendenceForm;
import com.microlabs.utilities.UserInfo;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPAttendenceAction extends DispatchAction {
	EssDao ad = EssDao.dBConnection();
	private static final String DESTINATION = "SAP_DESTINATION";

	private void connectSAP() {
		try {

			Properties connectProperties = new Properties();// TODO change the
			// details
			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
					"192.168.1.33");
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
					"00");
			connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
					"450");
			connectProperties.setProperty(DestinationDataProvider.JCO_USER,
					"rfcdev");
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
					"Init123#");
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
					"EN");
			File destCfg = new File(DESTINATION + ".jcoDestination");

			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "SAP_DESTINATION config");
			fos.close();

		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",
					e);
		}
	}
	
	
public String getActualEmpNo(String pernr) throws Exception {
		
	String emp="";
		
		
		String per="Select case when Swipe_No is null then pernr else Swipe_No end as actemp from emp_official_info where PERNR='"+pernr+"'";
		ResultSet a=ad.selectQuery(per);
			if(a.next())	
			{
			emp=a.getString("actemp");
				}
		
			try {
				a.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		return emp;
	}
	
	public String EmpLoc(String a)
	{
		
		String b = "";
		if(a==null)
		{
		  return b;	
		}
		
		if(!a.equalsIgnoreCase(""))
		{	
			
			EssDao ad = new EssDao();
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
	
	public String getTableName(String location)
	{
		String tableName="";
		if(location.equalsIgnoreCase("CBWH"))
			tableName="CBWHCAL";
		if(location.equalsIgnoreCase("ML00"))
				tableName="CmpCal2013";
		if(location.equalsIgnoreCase("ML01"))
			tableName="ML01CAL";
		if(location.equalsIgnoreCase("ML02"))
			tableName="ML02CAL";
		if(location.equalsIgnoreCase("ML03"))
			tableName="ML03CAL";
		if(location.equalsIgnoreCase("ML04"))
			tableName="ML04CAL";
		if(location.equalsIgnoreCase("ML05"))
			tableName="ML05CAL";
		if(location.equalsIgnoreCase("ML06"))
			tableName="ML06CAL";
		if(location.equalsIgnoreCase("ML07"))
			tableName="ML07CAL";
		if(location.equalsIgnoreCase("ML08"))
			tableName="ML08CAL";
		if(location.equalsIgnoreCase("ML09"))
			tableName="ML09CAL";
		if(location.equalsIgnoreCase("ML10"))
			tableName="ML10CAL";
		if(location.equalsIgnoreCase("ML11"))
			tableName="ML11CAL";
		if(location.equalsIgnoreCase("ML12"))
			tableName="ML12CAL";
		if(location.equalsIgnoreCase("ML13"))
			tableName="ML13CAL";
		if(location.equalsIgnoreCase("ML14"))
			tableName="ML14CAL";
		if(location.equalsIgnoreCase("ML15"))
			tableName="ML15CAL";
		if(location.equalsIgnoreCase("ML16"))
			tableName="ML16CAL";
		if(location.equalsIgnoreCase("ML17"))
			tableName="ML17CAL";
		if(location.equalsIgnoreCase("ML18"))
       		tableName="ML18CAL";
		if(location.equalsIgnoreCase("ML19"))
			tableName="ML19CAL";
		if(location.equalsIgnoreCase("ML20"))
			tableName="ML20CAL";
		if(location.equalsIgnoreCase("ML21"))
			tableName="ML21CAL";
		if(location.equalsIgnoreCase("ML22"))
       		tableName="ML22CAL";
		if(location.equalsIgnoreCase("ML23"))
			tableName="ML23CAL";
		if(location.equalsIgnoreCase("ML24"))
			tableName="ML24CAL";
		if(location.equalsIgnoreCase("ML25"))
			tableName="ML25CAL";
		if(location.equalsIgnoreCase("ML26"))
       		tableName="ML26CAL";
		if(location.equalsIgnoreCase("ML51"))
			tableName="ML51CAL";
		if(location.equalsIgnoreCase("ML52"))
			tableName="ML52CAL";
		if(location.equalsIgnoreCase("ML53"))
			tableName="ML53CAL";
		if(location.equalsIgnoreCase("ML54"))
			tableName="ML54CAL";
		if(location.equalsIgnoreCase("ML55"))
			tableName="ML55CAL";
		if(location.equalsIgnoreCase("ML56"))
			tableName="ML56CAL";
		if(location.equalsIgnoreCase("ML57"))
			tableName="ML57CAL";
		if(location.equalsIgnoreCase("ML58"))
			tableName="ML58CAL";
		if(location.equalsIgnoreCase("ML59"))
			tableName="ML59CAL";
		if(location.equalsIgnoreCase("ML60"))
			tableName="ML60CAL";
		if(location.equalsIgnoreCase("ML61"))
			tableName="ML61CAL";
		if(location.equalsIgnoreCase("ML62"))
			tableName="ML62CAL";
		if(location.equalsIgnoreCase("ML63"))
			tableName="ML63CAL";
		if(location.equalsIgnoreCase("ML64"))
			tableName="ML64CAL";
		if(location.equalsIgnoreCase("ML65"))
			tableName="ML65CAL";
		if(location.equalsIgnoreCase("ML66"))
			tableName="ML66CAL";
		if(location.equalsIgnoreCase("ML67"))
			tableName="ML67CAL";
		if(location.equalsIgnoreCase("ML68"))
			tableName="ML68CAL";
		if(location.equalsIgnoreCase("ML90"))
			tableName="ML90CAL";
		if(location.equalsIgnoreCase("ML91"))
			tableName="ML91CAL";
		if(location.equalsIgnoreCase("ML92"))
			tableName="ML92CAL";
	
		return tableName;
	}
	public String Empname(String a)
	{
		
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
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		
		return b;
		
	}
	public ActionForward displayAttendeceDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		

		SAPAttendenceForm attdForm = (SAPAttendenceForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");

		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}

		//session.setMaxInactiveInterval(1*60);
		SAPAttendenceDAO dao = new SAPAttendenceDAO();
		String empNo = attdForm.getEmployeeNo();
		String month = attdForm.getMonth();

		String monName = month.substring(0, 3);
		String calendarmon=monName;
		String mon=month.substring(0, 3);
		String year = month.substring(month.length() - 4, month.length());

		/*
		 * String reqyear=month.substring(month.length()-2, month.length());
		 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
		 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
		 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
		 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
		 * a4.submit(mapping, attendenceForm, request, response); return
		 * mapping.findForward("display"); }
		 */


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datenow = new Date();
		String time = sdf.format(datenow);
		
		String today[]=time.split("-");
		
		int datetoday=Integer.parseInt(today[2]);
		String monthtoday=today[1];
		
		
		if (monName.equals("Jan"))
			monName = "01";
		if (monName.equals("Feb"))
			monName = "02";
		if (monName.equals("Mar"))
			monName = "03";
		if (monName.equals("Apr"))
			monName = "04";
		if (monName.equals("May"))
			monName = "05";
		if (monName.equals("Jun"))
			monName = "06";
		if (monName.equals("Jul"))
			monName = "07";
		if (monName.equals("Aug"))
			monName = "08";
		if (monName.equals("Sep"))
			monName = "09";
		if (monName.equals("Oct"))
			monName = "10";
		if (monName.equals("Nov"))
			monName = "11";
		if (monName.equals("Dec"))
			monName = "12";

		String date ="";	

	

	    Calendar cal = Calendar.getInstance();
	    ArrayList att = new ArrayList();

	    cal.set(Integer.parseInt(year), Integer.parseInt(monName)-1, 1);
	    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	   
	    int i=1;
	/*    String query="EXEC daily_report_status_Att '"+days+"','"+monName+"','"+year+"','"+empNo+"' ";
		int k=ad.SqlExecuteUpdate(query);*/

	    String textemp=request.getParameter("textempno");
	    String ename="";
	    
	    if(textemp==null)
	    {
	    	textemp="";
	    }
	   
	    if(!textemp.equalsIgnoreCase(""))
	    {
	    	empNo=textemp;
	    	ename=Empname(empNo);
	    }
	    String punhrow="";
	    String tot;
	    
	    
		/*try {
			tot = "SELECT dbo.[getPuchtIme]('"+monName+"','"+getActualEmpNo(empNo)+"','"+year+"','"+EmpLoc(empNo)+"')AS allpunch";
		    ResultSet b=ad.selectQuery(tot);
			
	    try {
			if(b.next())
			{
				punhrow=b.getString("allpunch");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}*/
	    try {
			if(EmpLoc(empNo).equalsIgnoreCase("ML00")){
				
				tot = "SELECT dbo.[getPuchtImeETime]('"+monName+"','"+getActualEmpNo(empNo)+"','"+year+"','"+EmpLoc(empNo)+"')AS allpunch";
			    ResultSet b=ad.selectQuery(tot);
				
				
		    try {
				if(b.next())
				{
					punhrow=b.getString("allpunch");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
				
			}else{
			tot = "SELECT dbo.[getPuchtIme]('"+monName+"','"+getActualEmpNo(empNo)+"','"+year+"','"+EmpLoc(empNo)+"')AS allpunch";
		    ResultSet b=ad.selectQuery(tot);
			
			
	    try {
			if(b.next())
			{
				punhrow=b.getString("allpunch");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		} 
			}catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    
	    
		attdForm.setRemarks(ename);
		
		
		String s9 = "  select right(Report_Daily_wise.date,2) as dayno,Permission_details.swipe_type,Permission_details.type,pernr,Report_Daily_wise.date,convert(varchar(11),Report_Daily_wise.date,106) as date1,"
    			+ "left(DATENAME(WEEKDAY,Report_Daily_wise.date),3) as day,left(In_time,5) as intime, left(out_time,5) as outtime,REPLACE(left(status,2),'NL','LP') as "
    			+ "instatus,REPLACE(right(status,2),'NL','LP') as outstatus, shift from Report_Daily_wise left outer join Permission_details on "
    			+ "Permission_details.user_id=pernr and  Permission_details.date=Report_Daily_wise.Date and Permission_details.Approver_Status=1 where Pernr='"+empNo+"'"
    			+ " and month(Report_Daily_wise.date)='"+monName+"' and year(Report_Daily_wise.date)='"+year+"' order by date";
	/*String s9 = "    select right(Report_Daily_wise.date,2) as dayno,EMP_MANUAL_SWIPE.in_out as swipe_type,EMP_MANUAL_SWIPE.Lost_Entry_Reason_Type as type,Report_Daily_wise.pernr,"
			+ "	Report_Daily_wise.date,convert(varchar(11),Report_Daily_wise.date,106) as date1,left(DATENAME(WEEKDAY,Report_Daily_wise.date),3) as day,"
			+ "	left(In_time,5) as intime, left(out_time,5) as outtime,REPLACE(left(status,2),'NL','LP') as instatus,REPLACE(right(status,2),'NL','LP')	"
			+ " as outstatus, shift from Report_Daily_wise left outer join EMP_MANUAL_SWIPE on EMP_MANUAL_SWIPE.pernr=Report_Daily_wise.pernr and 	"
			+ " EMP_MANUAL_SWIPE.Start_date=Report_Daily_wise.Date and   EMP_MANUAL_SWIPE.Status_Flag=1 where Report_Daily_wise.Pernr='"+empNo+"' and	"
			+ "  month(Report_Daily_wise.date)='"+monName+"' and year(Report_Daily_wise.date)='"+year+"'  order by date";		    	
    	*/    	String duplicatedate="";
	    	ResultSet rs9 = ad.selectQuery(s9);
	    	try {
				while(rs9.next())
				{
					
					SAPAttendenceForm help = new SAPAttendenceForm();
					
					

if(punhrow!=null)
{
String frst=punhrow.substring(punhrow.indexOf("@"+rs9.getString("dayno")+"$")+4,punhrow.length());
if(punhrow.contains(("@"+rs9.getString("dayno")+"$")))
help.setAllpunch(frst.substring(0, frst.indexOf("@")-1));
}

			
					help.setDate(rs9.getString("date1"));
					help.setDay(rs9.getString("day"));
					date=rs9.getString("date");
					
					if(!(rs9.getString("intime").equalsIgnoreCase("00:00") && rs9.getString("instatus").equalsIgnoreCase("AA")))
		    			help.setiNTIME(rs9.getString("intime"));


			    	if(!(rs9.getString("outtime").equalsIgnoreCase("00:00") && rs9.getString("outstatus").equalsIgnoreCase("AA")))
					help.setoUTTIME(rs9.getString("outtime"));

			    	if(monName.equalsIgnoreCase(monthtoday))
			    	{
	                if(datetoday>=i)
	                {
	                	help.setiNSTATUS(rs9.getString("instatus"));
	                	if(datetoday==i)
	                	{
	                		if(rs9.getString("outstatus").equalsIgnoreCase("AA"))
	                		{
	                			help.setoUTSTATUS("");
	                		}
	                		else
	                		{
	                			help.setoUTSTATUS(rs9.getString("outstatus"));
	                		}
	                	}
	                	else
	                	{
	                		help.setoUTSTATUS(rs9.getString("outstatus"));
	                	}
						
						
						switch(rs9.getString("shift")){
						case "0001": help.setShift("Gen(0001)");
						  break;
case "0002": help.setShift("Gen(0002)");
						  break;
case "0003": help.setShift("FS(0003)");
						  break;
case "0004": help.setShift("SS(0004)");
						  break;
case "0005": help.setShift("TS(0005)");
						  break;
case "0006": help.setShift("SS1(0006)");
						  break;
case "0007": help.setShift("FS(0007)");
						  break;
case "0008": help.setShift("SS0008)");
						  break;
case "0009": help.setShift("TS(0009)");
						  break;
case "0010": help.setShift("NS(0010)");
						  break;
case "0011": help.setShift("SS-SM(0011)");
						  break;
case "0012": help.setShift("EnggB(0012)");
						  break;
case "0013": help.setShift("EnggC(0013)");
						  break;
case "0014": help.setShift("SecA(0014)");
						  break;
case "0015": help.setShift("SecB(0015)");
						  break;
case "0016": help.setShift("SecC(0016)");
						  break;
case "0017": help.setShift("Gen(0017)");
						  break;
case "0018": help.setShift("Night(0018)");
						  break;
case "0019": help.setShift("Gen(0019)");
						  break;
case "0020": help.setShift("Gen2(0020)");
						  break;
case "0021": help.setShift("2ND(0021)");
						  break;
case "0022": help.setShift("2ND1(0022)");
						  break;
case "0023": help.setShift("EnggGen(0023)");
						  break;
case "0024": help.setShift("EnggGen1(0024)");
						  break;
case "0025": help.setShift("1ST(0025)");
						  break;
case "0026": help.setShift("Engg2ND(0026)");
						  break;
case "0027": help.setShift("3RD(0027)");
						  break;
case "0028": help.setShift("QC1ST(0028)");
						  break;
case "0030": help.setShift("Gen4(0030)");
						  break;
case "0031": help.setShift("1ST(0031)");
						  break;
case "0032": help.setShift("Gen(0032)");
						  break;
case "0033": help.setShift("2ND(0033)");
						  break;
case "0034": help.setShift("Night(0034)");
						  break;
case "0035": help.setShift("GOASecA(0035)");
						  break;
case "0036": help.setShift("GOASecB(0036)");
						  break;
case "0037": help.setShift("GOASecC(0037)");
						  break;
case "0038": help.setShift("Gen3(0038)");
						  break;
case "0029": help.setShift("QC2ND(0029)");
						  break;
case "0039": help.setShift("Gen1(0039)");
						  break;
case "0040": help.setShift("Gen2(0040)");
						  break;
case "0041": help.setShift("FS(0041)");
						  break;
case "0042": help.setShift("SS(0042)");
						  break;
case "0043": help.setShift("TS(0043)");
						  break;
case "0054": help.setShift("Gen(0054)");
						  break;
case "0055": help.setShift("1ST(0055)");
						  break;
case "0056": help.setShift("2ND(0056)");
						  break;
case "0057": help.setShift("Night(0057)");
						  break;
case "0058": help.setShift("Gen5(0058)");
						  break;
case "0059": help.setShift("Gen6(0059)");
						  break;
case "0060": help.setShift("Gen(0060)");
						  break;
case "0061": help.setShift("FS(0061)");
						  break;
case "0062": help.setShift("SS(0062)");
						  break;
case "0063": help.setShift("TS(0063)");
						  break;
case "0064": help.setShift("Night(0064)");
						  break;
case "0065": help.setShift("Gen(0065)");
						  break;
case "0066": help.setShift("SSQC(0066)");
						  break;
case "0067": help.setShift("FS(0067)");
						  break;
case "0069": help.setShift("Gen(0069)");
						  break;
case "0070": help.setShift("Gen2(0070)");
						  break;
case "0071": help.setShift("Gen3(0071)");
						  break;
case "0072": help.setShift("QC2ND1(0072)");
						  break;
case "0074": help.setShift("EnggA(0074)");
						  break;
case "0075": help.setShift("EnggD(0075)");
						  break;
case "0044": help.setShift("FS1(0044)");
						  break;
case "0045": help.setShift("FS2(0045)");
						  break;
case "0046": help.setShift("Gen1(0046)");
						  break;
case "0047": help.setShift("Gen2(0047)");
						  break;
case "0048": help.setShift("SS(0048)");
						  break;
case "0049": help.setShift("3RD(0049)");
						  break;
case "0050": help.setShift("Gen(0050)");
						  break;
case "0051": help.setShift("FS(0051)");
						  break;
case "0052": help.setShift("SS(0052)");
						  break;
case "0053": help.setShift("TS(0053)");
						  break;
case "0068": help.setShift("Gen(0068)");
						  break;
case "0073": help.setShift("QcNig(0073)");
						  break;
						  
case "0081": help.setShift("GS(0081)");
break;
case "0082": help.setShift("FS(0082)");
break;
case "0083": help.setShift("SS(0083)");
break;
case "0084": help.setShift("Nig(0084)");
break;
case "0087": help.setShift("Gen(0087)");
break;
case "0088": help.setShift("RD(0088)");
break;
case "0089": help.setShift("Sec(0089)");
break;
}  }
	                else
			    	{
			    		
	                	help.setiNSTATUS("");
						help.setoUTSTATUS("");	
						
						switch(rs9.getString("shift")){
						case "0001": help.setShift("Gen(0001)");
						  break;
case "0002": help.setShift("Gen(0002)");
						  break;
case "0003": help.setShift("FS(0003)");
						  break;
case "0004": help.setShift("SS(0004)");
						  break;
case "0005": help.setShift("TS(0005)");
						  break;
case "0006": help.setShift("SS1(0006)");
						  break;
case "0007": help.setShift("FS(0007)");
						  break;
case "0008": help.setShift("SS0008)");
						  break;
case "0009": help.setShift("TS(0009)");
						  break;
case "0010": help.setShift("NS(0010)");
						  break;
case "0011": help.setShift("SS-SM(0011)");
						  break;
case "0012": help.setShift("EnggB(0012)");
						  break;
case "0013": help.setShift("EnggC(0013)");
						  break;
case "0014": help.setShift("SecA(0014)");
						  break;
case "0015": help.setShift("SecB(0015)");
						  break;
case "0016": help.setShift("SecC(0016)");
						  break;
case "0017": help.setShift("Gen(0017)");
						  break;
case "0018": help.setShift("Night(0018)");
						  break;
case "0019": help.setShift("Gen(0019)");
						  break;
case "0020": help.setShift("Gen2(0020)");
						  break;
case "0021": help.setShift("2ND(0021)");
						  break;
case "0022": help.setShift("2ND1(0022)");
						  break;
case "0023": help.setShift("EnggGen(0023)");
						  break;
case "0024": help.setShift("EnggGen1(0024)");
						  break;
case "0025": help.setShift("1ST(0025)");
						  break;
case "0026": help.setShift("Engg2ND(0026)");
						  break;
case "0027": help.setShift("3RD(0027)");
						  break;
case "0028": help.setShift("QC1ST(0028)");
						  break;
case "0030": help.setShift("Gen4(0030)");
						  break;
case "0031": help.setShift("1ST(0031)");
						  break;
case "0032": help.setShift("Gen(0032)");
						  break;
case "0033": help.setShift("2ND(0033)");
						  break;
case "0034": help.setShift("Night(0034)");
						  break;
case "0035": help.setShift("GOASecA(0035)");
						  break;
case "0036": help.setShift("GOASecB(0036)");
						  break;
case "0037": help.setShift("GOASecC(0037)");
						  break;
case "0038": help.setShift("Gen3(0038)");
						  break;
case "0029": help.setShift("QC2ND(0029)");
						  break;
case "0039": help.setShift("Gen1(0039)");
						  break;
case "0040": help.setShift("Gen2(0040)");
						  break;
case "0041": help.setShift("FS(0041)");
						  break;
case "0042": help.setShift("SS(0042)");
						  break;
case "0043": help.setShift("TS(0043)");
						  break;
case "0054": help.setShift("Gen(0054)");
						  break;
case "0055": help.setShift("1ST(0055)");
						  break;
case "0056": help.setShift("2ND(0056)");
						  break;
case "0057": help.setShift("Night(0057)");
						  break;
case "0058": help.setShift("Gen5(0058)");
						  break;
case "0059": help.setShift("Gen6(0059)");
						  break;
case "0060": help.setShift("Gen(0060)");
						  break;
case "0061": help.setShift("FS(0061)");
						  break;
case "0062": help.setShift("SS(0062)");
						  break;
case "0063": help.setShift("TS(0063)");
						  break;
case "0064": help.setShift("Night(0064)");
						  break;
case "0065": help.setShift("Gen(0065)");
						  break;
case "0066": help.setShift("SSQC(0066)");
						  break;
case "0067": help.setShift("FS(0067)");
						  break;
case "0069": help.setShift("Gen(0069)");
						  break;
case "0070": help.setShift("Gen2(0070)");
						  break;
case "0071": help.setShift("Gen3(0071)");
						  break;
case "0072": help.setShift("QC2ND1(0072)");
						  break;
case "0074": help.setShift("EnggA(0074)");
						  break;
case "0075": help.setShift("EnggD(0075)");
						  break;
case "0044": help.setShift("FS1(0044)");
						  break;
case "0045": help.setShift("FS2(0045)");
						  break;
case "0046": help.setShift("Gen1(0046)");
						  break;
case "0047": help.setShift("Gen2(0047)");
						  break;
case "0048": help.setShift("SS(0048)");
						  break;
case "0049": help.setShift("3RD(0049)");
						  break;
case "0050": help.setShift("Gen(0050)");
						  break;
case "0051": help.setShift("FS(0051)");
						  break;
case "0052": help.setShift("SS(0052)");
						  break;
case "0053": help.setShift("TS(0053)");
						  break;
case "0068": help.setShift("Gen(0068)");
						  break;
case "0073": help.setShift("QcNig(0073)");
						  break;
case "0081": help.setShift("GS(0081)");
break;
case "0082": help.setShift("FS(0082)");
break;
case "0083": help.setShift("SS(0083)");
break;
case "0084": help.setShift("Nig(0084)");
break;
case "0087": help.setShift("Gen(0087)");
break;
case "0088": help.setShift("RD(0088)");
break;
case "0089": help.setShift("Sec(0089)");
break;
}
		                  
			    	}
	                
		    	}
			    	else
			    	{
			    		
			    		help.setiNSTATUS(rs9.getString("instatus"));
						help.setoUTSTATUS(rs9.getString("outstatus"));
						
						switch(rs9.getString("shift")){
						case "0001": help.setShift("Gen(0001)");
						  break;
case "0002": help.setShift("Gen(0002)");
						  break;
case "0003": help.setShift("FS(0003)");
						  break;
case "0004": help.setShift("SS(0004)");
						  break;
case "0005": help.setShift("TS(0005)");
						  break;
case "0006": help.setShift("SS1(0006)");
						  break;
case "0007": help.setShift("FS(0007)");
						  break;
case "0008": help.setShift("SS0008)");
						  break;
case "0009": help.setShift("TS(0009)");
						  break;
case "0010": help.setShift("NS(0010)");
						  break;
case "0011": help.setShift("SS-SM(0011)");
						  break;
case "0012": help.setShift("EnggB(0012)");
						  break;
case "0013": help.setShift("EnggC(0013)");
						  break;
case "0014": help.setShift("SecA(0014)");
						  break;
case "0015": help.setShift("SecB(0015)");
						  break;
case "0016": help.setShift("SecC(0016)");
						  break;
case "0017": help.setShift("Gen(0017)");
						  break;
case "0018": help.setShift("Night(0018)");
						  break;
case "0019": help.setShift("Gen(0019)");
						  break;
case "0020": help.setShift("Gen2(0020)");
						  break;
case "0021": help.setShift("2ND(0021)");
						  break;
case "0022": help.setShift("2ND1(0022)");
						  break;
case "0023": help.setShift("EnggGen(0023)");
						  break;
case "0024": help.setShift("EnggGen1(0024)");
						  break;
case "0025": help.setShift("1ST(0025)");
						  break;
case "0026": help.setShift("Engg2ND(0026)");
						  break;
case "0027": help.setShift("3RD(0027)");
						  break;
case "0028": help.setShift("QC1ST(0028)");
						  break;
case "0030": help.setShift("Gen4(0030)");
						  break;
case "0031": help.setShift("1ST(0031)");
						  break;
case "0032": help.setShift("Gen(0032)");
						  break;
case "0033": help.setShift("2ND(0033)");
						  break;
case "0034": help.setShift("Night(0034)");
						  break;
case "0035": help.setShift("GOASecA(0035)");
						  break;
case "0036": help.setShift("GOASecB(0036)");
						  break;
case "0037": help.setShift("GOASecC(0037)");
						  break;
case "0038": help.setShift("Gen3(0038)");
						  break;
case "0029": help.setShift("QC2ND(0029)");
						  break;
case "0039": help.setShift("Gen1(0039)");
						  break;
case "0040": help.setShift("Gen2(0040)");
						  break;
case "0041": help.setShift("FS(0041)");
						  break;
case "0042": help.setShift("SS(0042)");
						  break;
case "0043": help.setShift("TS(0043)");
						  break;
case "0054": help.setShift("Gen(0054)");
						  break;
case "0055": help.setShift("1ST(0055)");
						  break;
case "0056": help.setShift("2ND(0056)");
						  break;
case "0057": help.setShift("Night(0057)");
						  break;
case "0058": help.setShift("Gen5(0058)");
						  break;
case "0059": help.setShift("Gen6(0059)");
						  break;
case "0060": help.setShift("Gen(0060)");
						  break;
case "0061": help.setShift("FS(0061)");
						  break;
case "0062": help.setShift("SS(0062)");
						  break;
case "0063": help.setShift("TS(0063)");
						  break;
case "0064": help.setShift("Night(0064)");
						  break;
case "0065": help.setShift("Gen(0065)");
						  break;
case "0066": help.setShift("SSQC(0066)");
						  break;
case "0067": help.setShift("FS(0067)");
						  break;
case "0069": help.setShift("Gen(0069)");
						  break;
case "0070": help.setShift("Gen2(0070)");
						  break;
case "0071": help.setShift("Gen3(0071)");
						  break;
case "0072": help.setShift("QC2ND1(0072)");
						  break;
case "0074": help.setShift("EnggA(0074)");
						  break;
case "0075": help.setShift("EnggD(0075)");
						  break;
case "0044": help.setShift("FS1(0044)");
						  break;
case "0045": help.setShift("FS2(0045)");
						  break;
case "0046": help.setShift("Gen1(0046)");
						  break;
case "0047": help.setShift("Gen2(0047)");
						  break;
case "0048": help.setShift("SS(0048)");
						  break;
case "0049": help.setShift("3RD(0049)");
						  break;
case "0050": help.setShift("Gen(0050)");
						  break;
case "0051": help.setShift("FS(0051)");
						  break;
case "0052": help.setShift("SS(0052)");
						  break;
case "0053": help.setShift("TS(0053)");
						  break;
case "0068": help.setShift("Gen(0068)");
						  break;
case "0073": help.setShift("QcNig(0073)");
						  break;
case "0087": help.setShift("Gen(0087)");
break;
case "0088": help.setShift("RD(0088)");
break;
case "0089": help.setShift("Sec(0089)");
break;
}
	}
				   

			    	String data = "select Approvel_Status,reason from leave_details where  user_id='"
							+ empNo
							+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
							+  date 
							+ "' between start_date and  end_date and YEAR(start_date)='"
							+ year + "'";
					ResultSet rs10 = ad.selectQuery(data);
					while (rs10.next()) {
						if (rs10.getString("Approvel_Status").equalsIgnoreCase(
								"Approved"))
							help.setRemarks(rs10.getString("reason"));
						else
							help.setRemarks("Leave to be Approved");
					}

					String data1 = "select Approver_Status,reason from OnDuty_details where  user_id='"
							+ empNo
							+ "' and Approver_Status in('Approved','In Process') and '"
							+ date
							+ "' between start_date and  end_date  and YEAR(start_date)='"
							+ year + "'";
					ResultSet rs101 = ad.selectQuery(data1);
					while (rs101.next()) {
						if (rs101.getString("Approver_Status")
								.equalsIgnoreCase("Approved"))
							help.setRemarks(rs101.getString("reason"));
						else
							help.setRemarks("Onduty to be Approved");
					} 


					



					String data11 = "select * from holidays where date = '"+date+"' and location = (select locid from Location where LOCATION_CODE in (select LOCID from emp_official_info where PERNR="+empNo+")) ";
					ResultSet rs1011 = ad.selectQuery(data11);
					try {
						while (rs1011.next()) {

								help.setRemarks(rs1011.getString("Holiday_Name"));
								help.setMessage("HOL");
								

						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				i++;
				
				if(rs9.getString("swipe_type")!=null)
				{
					if(!rs9.getString("swipe_type").equalsIgnoreCase(""))
					{
						if(!rs9.getString("type").equalsIgnoreCase("Forgot Swipe"))
						{
						if(rs9.getString("swipe_type").equalsIgnoreCase("In"))
						{
							help.setiNSTATUSstar("*");
						}
						else
						{
							help.setoUTSTATUSstar("*");
						}
						}
						else
						{
							if(rs9.getString("swipe_type").equalsIgnoreCase("In"))
							{
								help.setiNTIMEhash("#");
							}
							else
							{
								help.setoUTTIMEhash("#");
							}
						}
					}
				}
				if(help.getiNTIME()==null)
				{
					help.setiNTIME("");
				}
				if(help.getoUTTIME()==null)
				{
					help.setoUTTIME("");
				}
				if(help.getiNSTATUS()==null)
				{
					help.setiNSTATUS("");
				}
				if(help.getoUTSTATUS()==null)
				{
					help.setoUTSTATUS("");
				}
				
				if(help.getiNTIMEhash()==null)
				{
					help.setiNTIMEhash("");
				}
				if(help.getoUTTIMEhash()==null)
				{
					help.setoUTTIMEhash("");
				}
				if(help.getiNSTATUSstar()==null)
				{
					help.setiNSTATUSstar("");
				}
				if(help.getoUTSTATUSstar()==null)
				{
					help.setoUTSTATUSstar("");
				}
				if(!duplicatedate.equalsIgnoreCase(rs9.getString("date1")))
					att.add(help);
		duplicatedate=rs9.getString("date1");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		/*	//working calendar
            String work="select "+calendarmon+" from "+getTableName(EmpLoc(empNo))+" where DAY='"+i+"'";
        	ResultSet rswork = ad.selectQuery(work);
			try {
				while (rswork.next()) {

				  if(!rswork.getString(1).equalsIgnoreCase("W"))
				  {
					  if(rswork.getString(1).equalsIgnoreCase("WS"))
					  {
						  help.setiNSTATUS("SS");
							
							help.setoUTSTATUS("SS"); 
					  }
					  else
					  {
						  help.setiNSTATUS(rswork.getString(1));
							
							help.setoUTSTATUS(rswork.getString(1)); 
					  }
						
				  }
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/




	    	
	    












	    if (att.size() > 0) {
			request.setAttribute("attDataList", att);
		}


					//shft

		display(mapping, attdForm, request, response);

		return mapping.findForward("display");

	}

	public ActionForward displayAttendeceDetails1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SAPAttendenceForm attdForm = (SAPAttendenceForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");

		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		//session.setMaxInactiveInterval(1*60);
		SAPAttendenceDAO dao = new SAPAttendenceDAO();
		String empNo = attdForm.getEmployeeNo();
		String month = attdForm.getMonth();

		String monName = month.substring(0, 3);
		String year = month.substring(month.length() - 4, month.length());

		/*
		 * String reqyear=month.substring(month.length()-2, month.length());
		 * String reqmonth=monName+reqyear; AttendenceForm attendenceForm = new
		 * AttendenceForm(); attendenceForm.setEmpcode(empNo);
		 * attendenceForm.setDat(reqmonth); AttendenceAction a4=new
		 * AttendenceAction(); if(monName.equalsIgnoreCase("Nov")) {
		 * a4.submit(mapping, attendenceForm, request, response); return
		 * mapping.findForward("display"); }
		 */

		if (monName.equals("Jan"))
			monName = "01";
		if (monName.equals("Feb"))
			monName = "02";
		if (monName.equals("Mar"))
			monName = "03";
		if (monName.equals("Apr"))
			monName = "04";
		if (monName.equals("May"))
			monName = "05";
		if (monName.equals("Jun"))
			monName = "06";
		if (monName.equals("Jul"))
			monName = "07";
		if (monName.equals("Aug"))
			monName = "08";
		if (monName.equals("Sep"))
			monName = "09";
		if (monName.equals("Oct"))
			monName = "10";
		if (monName.equals("Nov"))
			monName = "11";
		if (monName.equals("Dec"))
			monName = "12";

		month = year + monName + "01";

		JCoFunction function = null;
		try {

			LinkedList payGroupID = new LinkedList();
			LinkedList payGroupShort_Name = new LinkedList();
			String getPayGrupDetails = "select * from Paygroup_Master order by Paygroup";
			ResultSet rs = ad.selectQuery(getPayGrupDetails);
			try {
				while (rs.next()) {
					payGroupID.add(rs.getString("Paygroup"));
					payGroupShort_Name.add(rs.getString("Short_desc"));
				}
				attdForm.setPayGroupID(payGroupID);
				attdForm.setPayGroupShort_Name(payGroupShort_Name);

			} catch (Exception e) {
				e.printStackTrace();
			}

			if (true)

			{
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
				/*
				 * JCoFunction function =
				 * destination.getRepository().getFunction( "ZBAPI_MMAS");
				 */
				function = destination.getRepository().getFunction(
						"ZBAPI_HR_ATTENDANCE");
				if (function == null) {
					throw new RuntimeException(
							" ZBAPI_HR_ATTENDANCE not found in SAP.");// ZBAPI_HR_PAYSLIP
				}
				function.getImportParameterList().setValue("PAYGROUP",
						attdForm.getPayGroup());
				function.getImportParameterList().setValue("PERNR", empNo);
				function.getImportParameterList().setValue("MONTH", month);

				function.execute(destination);
				LinkedList attdList = new LinkedList();
				JCoTable returnTable = function.getTableParameterList()
						.getTable("ATTNDATA");// PAYDATA_D_H
				Map<Integer, Character> returnMap = new HashMap<Integer, Character>();
				if (returnTable.getNumRows() > 0) {
					returnTable.firstRow();
					do {
						String reqDate = convertDate(returnTable
								.getDate("BEGDA"));
						String inTime = returnTable.getTime("INTIME")
								.toString();
						String a1[] = inTime.split(" ");
						if (a1.length == 6)
							inTime = a1[3];
						String outTime = returnTable.getTime("OUTTIME")
								.toString();
						String b1[] = outTime.split(" ");
						if (b1.length == 6)
							outTime = b1[3];

						SAPAttendenceForm attData = new SAPAttendenceForm();
						attData.setDate(reqDate);
						attData.setiNTIME(returnTable.getString("INTIME"));
						attData.setiNSTATUS(returnTable.getString("INSTATUS"));
						attData.setoUTTIME(returnTable.getString("OUTTIME"));
						attData.setoUTSTATUS(returnTable.getString("OUTSTATUS"));
						attData.setShift(returnTable.getString("SHTCODE"));
						attdList.add(attData);

					} while (returnTable.nextRow());

					String deleteRecords = "delete from SAP_Attendence where PERNR='"
							+ empNo + "'  ";
					ad.SqlExecuteUpdate(deleteRecords);

					dao.updateAttendenceDetails(attdList, empNo);

				}

				if (returnTable.getNumRows() == 0) {

					JCoTable returnStatus = function.getTableParameterList()
							.getTable("RETURN");
					if (returnStatus.getNumRows() > 0) {
						char c = returnStatus.getChar("TYPE");
						if (c == 'E') {
							attdForm.setMessage(returnStatus
									.getString("MESSAGE"));
						}
					}

				}

				LinkedList attDataList = new LinkedList();
				String getAttendence = "select convert(varchar(11),s.[BEGDA],106) as date,BEGDA as rdate,DATENAME(WEEKDAY,S.[BEGDA]) as day,convert(varchar(5),s.[INTIME],"
						+ "106) as INTIME,s.INSTATUS,convert(varchar(5),s.[OUTTIME],106) as OUTTIME,s.OUTSTATUS,(SELECT Holiday_Name FROM holidays WHERE Location='"
						+ user.getPlantId()
						+ "' "
						+ "AND convert(varchar(11),s.[BEGDA],103)=convert(varchar(11),Date,103) ) as holiday,Shift from SAP_Attendence as s where s.PERNR='"
						+ empNo
						+ "' and"
						+ " MONTH(s.[BEGDA])='"
						+ monName
						+ "' order by date";
				ResultSet rsAttend = ad.selectQuery(getAttendence);

				while (rsAttend.next()) {
					SAPAttendenceForm attData = new SAPAttendenceForm();
					Date today = new Date();
					String date = today.toString();
					String a[] = date.split(" ");
					date = a[2] + " " + a[1] + " " + a[5];
					attData.setDate(rsAttend.getString("date"));
					attData.setDay(rsAttend.getString("day").substring(0, 3));
					
		
					if(!(rsAttend.getString("INTIME").equalsIgnoreCase("00:00")))
					attData.setiNTIME(rsAttend.getString("INTIME"));
					else
					{
						attData.setiNTIME("");
					}
					if((!(rsAttend.getString("INSTATUS").equalsIgnoreCase("TI")	|| rsAttend.getString("INSTATUS").equalsIgnoreCase("TO"))))
					attData.setiNSTATUS(rsAttend.getString("INSTATUS"));
					else
					attData.setiNSTATUS("");
					if((!(rsAttend.getString("OUTSTATUS").equalsIgnoreCase("TI")|| rsAttend.getString("OUTSTATUS").equalsIgnoreCase("TO"))))
					attData.setoUTSTATUS(rsAttend.getString("OUTSTATUS"));
					else
					attData.setoUTSTATUS("");
					
					if(!(rsAttend.getString("OUTTIME").equalsIgnoreCase("00:00")))
					attData.setoUTTIME(rsAttend.getString("OUTTIME"));
					else
					{
						attData.setoUTTIME("");
					}
					
					String remark = rsAttend.getString("holiday");
					if (remark != null) {
						attData.setRemarks(remark);
						attData.setMessage("HOL");
					}
					
					//shft
					String sht="select * from shift_master where shft_code='"+rsAttend.getString("Shift")+"'";
					ResultSet as=ad.selectQuery(sht);
					if(as.next())
					{
						attData.setShift(as.getString("SHFT_STXT"));
					}
					
					

					String data = "select * from leave_details where  user_id='"
							+ empNo
							+ "' and Approvel_Status in('Approved','Pending') and record_status!='Draft'  and '"
							+ rsAttend.getString("rdate")
							+ "' between start_date and  end_date and YEAR(start_date)='"
							+ year + "'";
					ResultSet rs10 = ad.selectQuery(data);
					while (rs10.next()) {
						if (rs10.getString("Approvel_Status").equalsIgnoreCase(
								"Approved"))
							attData.setRemarks(rs10.getString("reason"));
						else
							attData.setRemarks("Leave to be Approved");
					}

					String data1 = "select * from OnDuty_details where  user_id='"
							+ empNo
							+ "' and Approver_Status in('Approved','In Process') and '"
							+ rsAttend.getString("rdate")
							+ "' between start_date and  end_date  and YEAR(start_date)='"
							+ year + "'";
					ResultSet rs101 = ad.selectQuery(data1);
					while (rs101.next()) {
						if (rs101.getString("Approver_Status")
								.equalsIgnoreCase("Approved"))
							attData.setRemarks(rs101.getString("reason"));
						else
							attData.setRemarks("Onduty to be Approved");
					}

					/*
					 * String in=rsAttend.getString("INSTATUS"); String
					 * out=rsAttend.getString("OUTSTATUS");
					 * 
					 * if(rsAttend.getString("day").equalsIgnoreCase("Saturday"))
					 * {
					 * if(in.equalsIgnoreCase("WO")&&(out.equalsIgnoreCase(""WO
					 * "))) { in="SS"; out="SS"; } }
					 * if(!(rsAttend.getString("INSTATUS"
					 * ).equalsIgnoreCase("PP")
					 * ||rsAttend.getString("INSTATUS").equalsIgnoreCase
					 * ("AA")||rsAttend
					 * .getString("INSTATUS").equalsIgnoreCase("TI"
					 * )||rsAttend.getString
					 * ("INSTATUS").equalsIgnoreCase("TO")))
					 * attData.setiNSTATUS(in); else attData.setiNSTATUS("");
					 * 
					 * if(!(rsAttend.getString("OUTSTATUS").equalsIgnoreCase("PP"
					 * )
					 * ||rsAttend.getString("OUTSTATUS").equalsIgnoreCase("AA")||
					 * rsAttend
					 * .getString("OUTSTATUS").equalsIgnoreCase("TI")||rsAttend
					 * .getString("OUTSTATUS").equalsIgnoreCase("TO")))
					 * attData.setoUTSTATUS(out); else attData.setoUTSTATUS("");
					 */
					attDataList.add(attData);

				}
				if (attDataList.size() > 0) {
					request.setAttribute("attDataList", attDataList);
				}

			} else {

				Connection con = ConnectionFactory1.getConnection();
				AttendenceForm attendenceForm = new AttendenceForm();
				int i = 0;
				try {
					// Statement st=con.createStatement();
					// Statement st1=con.createStatement();
					Statement st2 = con.createStatement();

					String sql2 = "Select a.empcode,a.name,a.designatn,b.[desc],CONVERT(varchar(11), a.joindate, 106) as doj from Empmst as a,deptdesc as b where a.dept=b.dept and a.empcode='"
							+ empNo + "'";

					ResultSet rs2 = st2.executeQuery(sql2);

					if (rs2.next()) {

						attendenceForm.setEmpcode1(rs2.getString("empcode"));
						attendenceForm.setName(rs2.getString("name"));
						attendenceForm.setDesignation(rs2
								.getString("designatn"));
						attendenceForm.setDepartment(rs2.getString("desc"));
						attendenceForm.setDoj(rs2.getString("doj"));
						attendenceForm.setDat1(attendenceForm.getDat());
						i = 1;
					}
					if (i > 0) {
						ArrayList vblist = displaydb(empNo,
								request, attendenceForm, monName);
						request.setAttribute("vb", vblist);
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (AbapException e) {
			System.out.println(e.toString());// TODO change to log

		} catch (JCoException e) {
			System.out.println(e.toString());// TODO change to log
			System.out.println(e.getMessageType());

			// e.printStackTrace();
			// System.out.println(function.getException("");

		} catch (Throwable e) {
			System.out.println(e.toString());// TODO change to log
			e.printStackTrace();
		} finally {
			// rs.close();
		}
		display(mapping, attdForm, request, response);

		return mapping.findForward("display");
	}

	public ArrayList displaydb(String empcode, HttpServletRequest request,
			AttendenceForm attendenceForm, String mon) {
		Connection con = ConnectionFactory1.getConnection();
		int i = 0;
		ArrayList arrayl = new ArrayList();
		try {
			// Statement st=con.createStatement();
			Statement st1 = con.createStatement();
			String tablename = "";
			if (mon.equals("11"))
				tablename = "Nov14Trn";
			if (mon.equals("10"))
				tablename = "Oct14Trn";

			String sq1 = "Select convert(varchar(11),a.[date],106) as date,DATENAME(WEEKDAY,a.[date]) as day,a.arrtim,SUBSTRINg(a.presabs,1,2) as Morning,a.deptim,SUBSTRINg(a.presabs,3,2) as Evening from "
					+ tablename
					+ " as a where a.empcode='"
					+ empcode
					+ "' and SUBSTRING(convert(varchar(11),a.[date],106),4,3)+''+SUBSTRING(convert(varchar(11),a.[date],106),10,2)+'Trn'='"
					+ tablename + "'";
			ResultSet rs1 = st1.executeQuery(sq1);

			AttendenceForm attendenceForm1 = null;
			String arrtim;
			String arrtim1[];
			String arrtim2;
			String deptim;
			String deptim1[];
			String deptim2;
			if (rs1.next()) {
				attendenceForm1 = new AttendenceForm();
				attendenceForm1.setDay(rs1.getString("date"));
				attendenceForm1.setGetday(rs1.getString("day"));
				arrtim = rs1.getString("arrtim");
				arrtim1 = arrtim.split("\\.");
				arrtim2 = arrtim1[1];
				if (arrtim2.length() < 2) {
					arrtim = arrtim1[0] + "." + arrtim1[1] + "0";
				}
				attendenceForm1.setArr(arrtim);
				attendenceForm1.setArrdesc(rs1.getString("Morning"));
				String mornType = rs1.getString("Morning");
				if (arrtim.equals("0.00")) {
					if (mornType.equals("P ") || mornType.equals("A "))
						attendenceForm1.setArrdesc("");
				}
				deptim = rs1.getString("deptim");
				deptim1 = deptim.split("\\.");
				deptim2 = deptim1[1];
				if (deptim2.length() < 2) {
					deptim = deptim1[0] + "." + deptim1[1] + "0";
				}

				attendenceForm1.setDep(deptim);
				attendenceForm1.setDepdesc(rs1.getString("Evening"));
				String evenType = rs1.getString("Evening");
				if (deptim.equals("0.00")) {
					if (evenType.equals("P ") || evenType.equals("A "))
						attendenceForm1.setDepdesc("");
				}
				SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat format2 = new SimpleDateFormat("dd MMM yyyy");
				Date date1 = new Date();
				Calendar now = Calendar.getInstance(); // This gets the current
														// date and time.
				now.get(Calendar.YEAR);
				Date date = format1.parse((date1.getDate() + "/"
						+ (now.get(Calendar.MONTH) + 1) + "/" + (now
						.get(Calendar.YEAR))));
				if (format2.format(date).equalsIgnoreCase(
						attendenceForm1.getDay())) {
					String depTime = attendenceForm1.getDep();
					if (depTime.equalsIgnoreCase("0.00")) {
						attendenceForm1.setDep("");
						attendenceForm1.setDepdesc("");
					}
				}

				arrayl.add(attendenceForm1);

				while (rs1.next()) {

					attendenceForm1 = new AttendenceForm();

					attendenceForm1.setDay(rs1.getString("date"));
					attendenceForm1.setGetday(rs1.getString("day"));
					arrtim = rs1.getString("arrtim");
					arrtim1 = arrtim.split("\\.");
					arrtim2 = arrtim1[1];
					if (arrtim2.length() < 2) {
						arrtim = arrtim1[0] + "." + arrtim1[1] + "0";
					}
					attendenceForm1.setArr(arrtim);
					attendenceForm1.setArrdesc(rs1.getString("Morning"));
					mornType = rs1.getString("Morning");
					if (arrtim.equals("0.00")) {
						if (mornType.equals("P ") || mornType.equals("A "))
							attendenceForm1.setArrdesc("A ");
					}
					deptim = rs1.getString("deptim");
					deptim1 = deptim.split("\\.");
					deptim2 = deptim1[1];
					if (deptim2.length() < 2) {
						deptim = deptim1[0] + "." + deptim1[1] + "0";

					}
					attendenceForm1.setDep(deptim);

					attendenceForm1.setDepdesc(rs1.getString("Evening"));
					evenType = rs1.getString("Evening");
					if (deptim.equals("0.00")) {
						if (evenType.equals("P ") || evenType.equals("A "))
							attendenceForm1.setDepdesc("A ");
					}
					now.get(Calendar.YEAR);
					date = format1.parse((date1.getDate() + "/"
							+ (now.get(Calendar.MONTH) + 1) + "/" + (now
							.get(Calendar.YEAR))));

					if (format2.format(date).equalsIgnoreCase(
							attendenceForm1.getDay())) {
						String depTime = attendenceForm1.getDep();
						if (depTime.equalsIgnoreCase("0.00")) {
							attendenceForm1.setDep("");
							attendenceForm1.setDepdesc("");
						}
					}
					arrayl.add(attendenceForm1);

				}
				attendenceForm.setMesage("");

			}

			else {

				attendenceForm.setMesage("Attendence Details not found");
			}
			request.setAttribute("arrayl", arrayl);
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return arrayl;

	}

	public String convertDate(Date date1) {

		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		String formatedDate = "";
		try {
			Date date = formatter.parse(date1.toString());

			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			formatedDate = cal.get(Calendar.YEAR) + "-"
					+ (cal.get(Calendar.MONTH) + 1) + "-"
					+ cal.get(Calendar.DATE);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return formatedDate;
	}

	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPAttendenceForm attdForm = (SAPAttendenceForm) form;
		HttpSession session = request.getSession();
		UserInfo user = (UserInfo) session.getAttribute("user");
       
		if(attdForm.getEmpno()==null || attdForm.getEmpno().equalsIgnoreCase(""))
		{	
			
		attdForm.setEmployeeNo(user.getEmployeeNo());
		}
		else
		{
			attdForm.setEmployeeNo(attdForm.getEmpno());
		}

		String empno = attdForm.getEmployeeNo();
		String getPayGroup = "Select PAY_GROUP from emp_official_info where PERNR='"
				+ empno + "'";
		ResultSet rsPayGroup = ad.selectQuery(getPayGroup);
		try {
			while (rsPayGroup.next()) {
				attdForm.setPayGroup(rsPayGroup.getString("PAY_GROUP"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		ArrayList ar_id = new ArrayList();
		ArrayList ar_name = new ArrayList();
		
		String cmpcode="";
		int id=user.getGroupId();
		String cmp[]=new String[0]; 
		String locations="";
		
		String aa="select * from user_group where id='"+id+"'";
		ResultSet cc=ad.selectQuery(aa);
		try {
			if(cc.next())
			{
				cmpcode=cc.getString("Attendance_Loc");
				if(cmpcode!=null)
				{
				
				
				if(cmpcode.contains(","))
				{
				 cmp=cmpcode.split(",");
				
				for(int b=0;b<cmp.length;b++)
				{
					locations=locations+"'"+cmp[b]+"',";
				}
				
				locations=locations.substring(0, locations.length()-1);
				}
				else
				{
					locations="'"+cmpcode+"'";
				}
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		if(!locations.equalsIgnoreCase(""))
			request.setAttribute("empselection", "empselection");


		int i = 0;
		try {

			 String[] monthNames = {"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
				Calendar c = Calendar.getInstance();		
				c.add(Calendar.MONTH, 0);
				int reqmonth = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
				int reqyear  = c.get(Calendar.YEAR);
				String mont1=new SimpleDateFormat("MMM").format(c.getTime())+reqyear;
				ar_id.add(mont1);
				ar_name.add(monthNames[reqmonth] + " " + reqyear);
				
				c = Calendar.getInstance();
				c.add(Calendar.MONTH, -1);
				 reqmonth = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
				 reqyear  = c.get(Calendar.YEAR);
				 mont1=new SimpleDateFormat("MMM").format(c.getTime())+reqyear;
				ar_id.add(mont1);
				ar_name.add(monthNames[reqmonth] + " " + reqyear);
				
				c = Calendar.getInstance();
				c.add(Calendar.MONTH, -2);
				 reqmonth = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
				 reqyear  = c.get(Calendar.YEAR);
				 mont1=new SimpleDateFormat("MMM").format(c.getTime())+reqyear;
				ar_id.add(mont1);
				ar_name.add(monthNames[reqmonth] + " " + reqyear);
				
				c = Calendar.getInstance();
				c.add(Calendar.MONTH, -3);
				 reqmonth = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
				 reqyear  = c.get(Calendar.YEAR);
				 mont1=new SimpleDateFormat("MMM").format(c.getTime())+reqyear;
				ar_id.add(mont1);
				ar_name.add(monthNames[reqmonth] + " " + reqyear);
				
				
				c = Calendar.getInstance();
				c.add(Calendar.MONTH, -4);
				 reqmonth = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
				 reqyear  = c.get(Calendar.YEAR);
				 mont1=new SimpleDateFormat("MMM").format(c.getTime())+reqyear;
				ar_id.add(mont1);
				ar_name.add(monthNames[reqmonth] + " " + reqyear);
				
				c = Calendar.getInstance();
				c.add(Calendar.MONTH, -5);
				 reqmonth = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
				 reqyear  = c.get(Calendar.YEAR);
				 mont1=new SimpleDateFormat("MMM").format(c.getTime())+reqyear;
				ar_id.add(mont1);
				ar_name.add(monthNames[reqmonth] + " " + reqyear);
			
				
				attdForm.setAr_id(ar_id);
				attdForm.setAr_name(ar_name);
			attdForm.setAr_id(ar_id);
			attdForm.setAr_name(ar_name);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//--- added for hod 
		ArrayList empIdList=new ArrayList();
		ArrayList empLabelList=new ArrayList();
		ArrayList yearList=new ArrayList();
		String isapprover = "select employeeNumber , employeeNumber+'-'+emp_official_info.EMP_FULLNAME as emplabel from ESS_Approvers , emp_official_info"
							 +" where ESS_Approvers.employeeNumber = emp_official_info.PERNR and (ESS_Approvers.ApproverId = '"+user.getEmployeeNo()+"' or Parallel_Approver1 = '"+user.getEmployeeNo()+"' or Parallel_Approver2 = '"+user.getEmployeeNo()+"') and Active='1'" ;
		ResultSet rs2 = ad.selectQuery(isapprover);
		
		try {
			while (rs2.next())

			{
				empIdList.add(rs2.getString("employeeNumber"));
				empLabelList.add(rs2.getString("emplabel"));
				
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
		}
		
		attdForm.setEmplIdList(empIdList);
		attdForm.setEmpLabelList(empLabelList);
		if (empIdList.size() > 0) {
			request.setAttribute("Approver", "Approver");
		}
		///////

		return mapping.findForward("display");
	}
	
	

	public String tableName(String monYear, String year) {
		String mon = monYear.substring(0, 3);
		String yr = year;
		return mon + yr;
	}

}
