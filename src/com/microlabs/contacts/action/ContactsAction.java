package com.microlabs.contacts.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.contacts.form.ContactsForm;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.form.EmployeeOfficialInfoForm;
import com.microlabs.login.dao.LoginDao;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
public class ContactsAction extends DispatchAction{
	
	public ActionForward curentRecord2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ContactsForm contacForm=(ContactsForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String keyword=contacForm.getKeyword();
		boolean check=false;
		try{
	
			int test=Integer.parseInt(keyword);
			
			}catch (NumberFormatException e) {
				check=true;
			}
			
			try{
			ArrayList empList=new ArrayList();
			String getEmpdetails="select u.PERNR,u.EMP_FULLNAME,u.EMAIL_ID,u.TEL_EXTENS,dept.DPTSTXT,desg.DSGSTXT,loc.location_code ,u.IP_PHONE,u.SEX,us.Emp_Photo,u.BOARD_LINE from " +
			"emp_official_info as u,Location as loc,department as dept,designation as desg,users as us where us.employeenumber=u.PERNR and us.employeenumber!='"+user.getEmployeeNo()+"'  and u.active='1' and  (u.EMP_FULLNAME like '%"+keyword+"%'  " +
			"or u.EMAIL_ID  like'%"+keyword+"%' ";
			
			if(check==false){
				getEmpdetails=getEmpdetails+"or u.PERNR ="+keyword+" or u.TEL_EXTENS ="+keyword+" or u.IP_PHONE ="+keyword+"";
			}
			
			getEmpdetails=getEmpdetails+")  and u.LOCID=loc.LOCATION_CODE and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID";
			
			
			
			ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{
				ContactsForm form2=new ContactsForm();
				form2.setEmpid(rs.getString("PERNR"));
				form2.setFirstName(rs.getString("EMP_FULLNAME"));
				form2.setIpPhone(rs.getString("IP_PHONE"));
				form2.setEmailID(rs.getString("EMAIL_ID"));
				form2.setDepartment(rs.getString("DPTSTXT"));
				form2.setDesignation(rs.getString("DSGSTXT"));
				form2.setContactNo(rs.getString("TEL_EXTENS"));
				form2.setLocationId(rs.getString("location_code"));
				String empSex=rs.getString("SEX");
			    String empImg=rs.getString("Emp_Photo");
			    form2.setBoardNo(rs.getString("BOARD_LINE"));
			   
			    form2.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
			    
			    if(empImg.equalsIgnoreCase(""))
			    {
			    	if(empSex.equalsIgnoreCase("M"))
			    	{
			    		form2.setEmpPhoto("male.png");
			    	}
			    	if(empSex.equalsIgnoreCase("F"))
			    	{
			    		form2.setEmpPhoto("female.png");
			    	}
			    	
			    }
			  empList.add(form2);
			}
			request.setAttribute("empList", empList);
			if(empList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				contacForm.setMessage("No Employee Found");
			}
		
		
		}catch (Exception e) {
		e.printStackTrace();
		}
     

		
		
		
		return mapping.findForward("empSearch");
	}
	
	public ActionForward curentRecordcontacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		ContactsForm contacForm=(ContactsForm)form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		
		ArrayList contactList=new ArrayList();
	try{
		int totalRecords=contacForm.getTotalRecords();//21
		int startRecord=contacForm.getStartRecord();//11
		int endRecord=contacForm.getEndRecord();	
		if(totalRecords==0 && startRecord==0 && endRecord==0)
		{
			startRecord=1;
			endRecord=10;
		}
		LinkedList listOfMaterialCode=new LinkedList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
			
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		contacForm.setLocationIdList(locationList);
		contacForm.setLocationLabelList(locationLabelList);
		
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		contacForm.setDepartmentList(deparmentList);
		
	
		String locationId=contacForm.getLocationId();
		contacForm.setLocationId(locationId);
		String a[]=locationId.split("-");
		locationId=a[0];
		String department=contacForm.getDepartment();
		contacForm.setDepartment(department);
		String firstName=contacForm.getFirstName();
		contacForm.setFirstName(firstName);
		
		String getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY  u.PERNR) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
		"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT,u.SEX,us.Emp_Photo,u.BOARD_LINE  from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as us where us.employeenumber=u.PERNR and us.employeenumber!='"+user.getEmployeeNo()+"' and u.LOCID=loc.location_code " +
		"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.active='1' ";
			if(!locationId.equalsIgnoreCase(""))
			{
				getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
			}
			if(!department.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%'";
			}
			if(!firstName.equalsIgnoreCase(""))
			{

				getContactList=getContactList+" and  (u.EMP_FULLNAME like '%"+firstName+"%' or u.EMAIL_ID  like '%"+firstName+"%' or u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' )";

			}
			
getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
contacForm.setSearchQuery(getContactList);
ResultSet rs=ad.selectQuery(getContactList);
ContactsForm emp=null;
while(rs.next())
{
	
	emp=new ContactsForm();
	emp.setEmpid(rs.getString("PERNR"));
	emp.setFirstName(rs.getString("EMP_FULLNAME"));
	emp.setDesignation(rs.getString("DSGSTXT"));
	emp.setDepartment(rs.getString("DPTSTXT"));
	emp.setLocationId(rs.getString("location_code"));

	
	emp.setBoardNo(rs.getString("BOARD_LINE"));
	
	  emp.setEmailID(rs.getString("EMAIL_ID"));
	emp.setContactNo(rs.getString("TEL_EXTENS"));
	emp.setIpPhone(rs.getString("IP_PHONE"));
	
	String empSex=rs.getString("SEX");
    String empImg=rs.getString("Emp_Photo");
   
    	emp.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
    
    if(empImg.equalsIgnoreCase(""))
    {
    	if(empSex.equalsIgnoreCase("M"))
    	{
    		emp.setEmpPhoto("male.png");
    	}
    	if(empSex.equalsIgnoreCase("F"))
    	{
    		emp.setEmpPhoto("female.png");
    	}
    	
    }

    
	contactList.add(emp);
}			
request.setAttribute("contactlist", contactList);
if(totalRecords>10)
{
	request.setAttribute("nextButton", "nextButton");
	request.setAttribute("displayRecordNo", "displayRecordNo");
}

request.setAttribute("disablePreviousButton", "disablePreviousButton");


	
if(contactList.size()==0)
{
	request.setAttribute("noRecords", "noRecords");
	contacForm.setMessage("No Records");
}

}catch (Exception e) {
e.printStackTrace();
}
		return mapping.findForward("searchContactsForm");
	}
	
	public ActionForward viewEmployee2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		 
		ContactsForm contacForm=(ContactsForm)form;
		 String keyword=contacForm.getKeyword();
		 contacForm.setKeyword(keyword);
		EmployeeOfficialInfoForm empLForm =new EmployeeOfficialInfoForm();
		LoginDao ad=new LoginDao();
		ArrayList contactList=new ArrayList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo=request.getParameter("empNo");
		LinkedList details = new LinkedList();
	
		
		try
		{
			
		
		String empCountry="";
		String empState="";
			
			String sql1="SELECT emp.EMP_FULLNAME,emp.PERNR,comp.BUTXT,emp.WERKS,p.Short_desc,c.CATLTEXT,emp.RPTMGR,emp.ROOM,emp.APPMGR,emp.FLOOR,emp.BUILDING," +
					"emp.DOJ,emp.DOC,emp.DOL,coun.LANDX,emp.LAND1,emp.state,st.BEZEI,loc.LOCATION_CODE,loc.LOCNAME,g.GRDTXT,desg.DSGSTXT,dept.DPTSTXT," +
					"emp.EMAIL_ID,emp.TEL_NO,emp.TEL_EXTENS,emp.IP_PHONE,emp.ESI,emp.ESINO,emp.PF,emp.PFNO,emp.PT,emp.IT,emp.PANNO,emp.BONUS,emp.LEAVES,emp.WAERS," +
					"pay.pay_method,emp.BACCTYP,emp.BACCNO,emp.IFSC_CODE,emp.MICR_CODE FROM emp_official_info as emp,Paygroup_Master as p," +
					"Category  as c,Country as coun, State as st,Department as dept, Designation as desg,Grade as g,PAYMODE as pay,Location as loc ," +
					"Company as comp  where emp.PERNR='"+empNo+"' and emp.STAFFCAT=c.STAFFCAT and emp.LAND1=coun.LAND1 and emp.STATE=st.ID " +
					"and dept.DPTID=emp.DPTID and desg.DSGID =emp.DSGID and g.GRDID=emp.GRDID and pay.pay_id=emp.payment_method  and  emp.BUKRS=comp.BUKRS " +
					"and p.Paygroup=emp.pay_group and emp.LOCID=loc.LOCATION_CODE ";
			
			
 					ResultSet rs1=ad.selectQuery(sql1);
 					
			while(rs1.next()){
				EmployeeOfficialInfoForm empOffForm = new EmployeeOfficialInfoForm();
			
				empOffForm.setFirstName(rs1.getString("EMP_FULLNAME"));
			
				
				empOffForm.setEmployeeNumber(rs1.getString("PERNR"));
				empOffForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
				empOffForm.setCompanyName(rs1.getString("BUTXT"));
				empOffForm.setPlant(rs1.getString("WERKS"));
				empOffForm.setPayGroup(rs1.getString("Short_desc"));
				empOffForm.setEmployeeCategory(rs1.getString("CATLTEXT"));
				//String reportingMgr=rs1.getString("RPTMGR");
				empOffForm.setReportingManger("");
				/*String getReportMgrName="select EMP_FULLNAME from emp_official_info where PERNR='"+reportingMgr+"'";
				ResultSet rsReprtingmagr=ad.selectQuery(getReportMgrName);
				while(rsReprtingmagr.next())
				{
					empOffForm.setReportingManger(rsReprtingmagr.getString("EMP_FULLNAME"));
				}*/
				empOffForm.setRoom(rs1.getString("ROOM"));
				empOffForm.setIpPhone(rs1.getString("IP_PHONE"));
				String apprMgr=rs1.getString("APPMGR");
				empOffForm.setApprovalManger("");
				String getApprMgrName="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where ess.employeeNumber='"+rs1.getString("PERNR")+"' " +
				"and emp.PERNR=ess.ApproverId  and ess.essType='Leave' and ess.Priority=1";
				ResultSet rsAppr=ad.selectQuery(getApprMgrName);
				while(rsAppr.next())
				{
					empOffForm.setApprovalManger(rsAppr.getString("EMP_FULLNAME"));
				}
				empOffForm.setFloor(rs1.getString("FLOOR"));
				empOffForm.setBuilding(rs1.getString("BUILDING"));
				//empOffForm.setAddressTypeID(rs1.getString("ADRTYP"));
				empOffForm.setDateofJoining(EMicroUtils.display1(rs1.getDate("DOJ")));
				empOffForm.setDateofLeaving(EMicroUtils.display1(rs1.getDate("DOL")));
				empOffForm.setDateofConformation(EMicroUtils.display1(rs1.getDate("DOC")));
				empOffForm.setCounID(rs1.getString("LANDX"));
				empCountry=rs1.getString("LANDX");
				
				empState=rs1.getString("state");
				String getEmpState=" select * from State where LAND1='"+rs1.getString("LAND1")+"' AND BLAND='"+empState+"'";
				ResultSet rsEmpstate=ad.selectQuery(getEmpState);
				while(rsEmpstate.next()){
					
					empOffForm.setState(rsEmpstate.getString("BEZEI"));
				}
				empOffForm.setLocation(rs1.getString("LOCATION_CODE")+"-"+rs1.getString("LOCNAME"));
				empOffForm.setGradeID(rs1.getString("GRDTXT"));
				empOffForm.setDesignation(rs1.getString("DSGSTXT"));
				empOffForm.setDepartment(rs1.getString("DPTSTXT"));
				empOffForm.setEmailid(rs1.getString("EMAIL_ID"));
				empOffForm.setTelNo(rs1.getString("TEL_NO"));
				empOffForm.setExtnNo(rs1.getString("TEL_EXTENS"));
				String eligibleESI=rs1.getString("ESI");
				if(eligibleESI.equalsIgnoreCase("Y"));
				{
				eligibleESI="Yes";
				}
				if(eligibleESI.equalsIgnoreCase("N"));
				{
				eligibleESI="No";
				}
				empOffForm.setEligibleforESIDeduction(eligibleESI);
				empOffForm.setEsiNumber(rs1.getString("ESINO"));
				String eligiblePF=rs1.getString("PF");
				if(eligiblePF.equalsIgnoreCase("Y"));
				{
					eligiblePF="Yes";
				}
				if(eligiblePF.equalsIgnoreCase("N"));
				{
					eligiblePF="No";
				}
				empOffForm.setEligibleforPFDeduction(eligiblePF);
				empOffForm.setPfNumber(rs1.getString("PFNO"));
				String eligiblePT=rs1.getString("PT");
				if(eligiblePT.equalsIgnoreCase("Y"));
				{
					eligiblePT="Yes";
				}
				if(eligiblePT.equalsIgnoreCase("N"));
				{
					eligiblePT="No";
				}
				empOffForm.setEligibleforPTDeduction(eligiblePT);
				String eligibleIT=rs1.getString("IT");
				if(eligibleIT.equalsIgnoreCase("Y"));
				{
					eligibleIT="Yes";
				}
				if(eligibleIT.equalsIgnoreCase("N"));
				{
					eligibleIT="No";
				}
				empOffForm.setEligibleforITDeduction(eligibleIT);
				empOffForm.setPanNo(rs1.getString("PANNO"));
				String eligibleBonus=rs1.getString("BONUS");
				if(eligibleBonus.equalsIgnoreCase("Y"));
				{
					eligibleBonus="Yes";
				}
				if(eligibleBonus.equalsIgnoreCase("N"));
				{
					eligibleBonus="No";
				}
				empOffForm.setBonus(eligibleBonus);
				String eligibleLeaves=rs1.getString("LEAVES");
				if(eligibleLeaves.equalsIgnoreCase("Y"));
				{
					eligibleLeaves="Yes";
				}
				if(eligibleLeaves.equalsIgnoreCase("N"));
				{
					eligibleLeaves="No";
				}
				empOffForm.setLeaves(eligibleLeaves);
				empOffForm.setSalaryCurrency(rs1.getString("WAERS"));
				empOffForm.setPaymentMethod(rs1.getString("pay_method"));
				//empOffForm.setBankName(rs1.getString("BNAME"));
				String accType=rs1.getString("BACCTYP");
				if(accType.equalsIgnoreCase("S"));
				{
					accType="Savings Account";
				}
				if(accType.equalsIgnoreCase("C"));
				{
					accType="Current Account";
				}
				empOffForm.setAccountType(accType);
				empOffForm.setAccountNumber(rs1.getString("BACCNO"));
				//empOffForm.setBranchName(rs1.getString("BRANCH"));
				empOffForm.setIfsCCode(rs1.getString("IFSC_CODE"));
				empOffForm.setMicrCode(rs1.getString("MICR_CODE"));
				details.add(empOffForm);
				
				
			//	empForm.setDepartment(rs.getString("DPTID"));
			//	empForm.setDepartIDList("DPTID");
				
				
			}
			String getLocation="select loc.LAND1 from emp_official_info emp,Location loc where emp.PERNR='"+empNo+"' and emp.LOCID=loc.LOCATION_CODE ";
			ResultSet rsLoc=ad.selectQuery(getLocation);
			while(rsLoc.next()){
				contacForm.setPlace(rsLoc.getString("LAND1"));
			}
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			int empPhotoCount=0;
			String checkImage="select count(*) from Employee_Photos where employeeNo='"+empNo+"'";
			ResultSet rsCheck=ad.selectQuery(checkImage);
			while(rsCheck.next()){
				empPhotoCount=rsCheck.getInt(1);
			}
			EmployeeOfficialInfoForm empLForm1 =new EmployeeOfficialInfoForm();
			ArrayList empPhotoList=new ArrayList();
			if(empPhotoCount>0){
				
			String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+empNo+"' ";
			ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
			while (rsEmpPhoto.next())
			{
				empLForm1.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
			request.setAttribute("employeePhoto", "employeePhoto");	
			}
			}else{
				String gender="";
				String getGender="select SEX from emp_official_info where PERNR='"+empNo+"'";
				ResultSet rsGender=ad.selectQuery(getGender);
				while(rsGender.next()){
					gender=rsGender.getString("SEX");
				}
				if(gender.equalsIgnoreCase("M"))
				{
					empLForm1.setPhotoImage("male.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}else{
					empLForm1.setPhotoImage("female.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}
			}
			empPhotoList.add(empLForm1);
		 request.setAttribute("empPhotoList", empPhotoList);
		}
		catch(SQLException se)
		{
			System.out.println("Exception @ get official info");
			se.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("details", details);
		
				
		return mapping.findForward("empoffInfo2");
	
		
	}

	public ActionForward viewEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{ 
		ContactsForm contacForm=(ContactsForm)form;
		 String keyword=contacForm.getKeyword();
		 contacForm.setKeyword(keyword);
		EmployeeOfficialInfoForm empLForm =new EmployeeOfficialInfoForm();
		LoginDao ad=new LoginDao();
		ArrayList contactList=new ArrayList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String empNo=request.getParameter("empNo");
		LinkedList details = new LinkedList();
	
		
		try
		{
			
		
		String empCountry="";
		String empState="";
			
			String sql1="SELECT emp.EMP_FULLNAME,emp.PERNR,comp.BUTXT,emp.WERKS,p.Short_desc,c.CATLTEXT,emp.RPTMGR,emp.ROOM,emp.APPMGR,emp.FLOOR,emp.BUILDING," +
					"emp.DOJ,emp.DOC,emp.DOL,coun.LANDX,emp.LAND1,emp.state,st.BEZEI,loc.LOCATION_CODE,loc.LOCNAME,g.GRDTXT,desg.DSGSTXT,dept.DPTSTXT," +
					"emp.EMAIL_ID,emp.TEL_NO,emp.TEL_EXTENS,emp.IP_PHONE,emp.ESI,emp.ESINO,emp.PF,emp.PFNO,emp.PT,emp.IT,emp.PANNO,emp.BONUS,emp.LEAVES,emp.WAERS," +
					"pay.pay_method,emp.BACCTYP,emp.BACCNO,emp.IFSC_CODE,emp.MICR_CODE FROM emp_official_info as emp,Paygroup_Master as p," +
					"Category  as c,Country as coun, State as st,Department as dept, Designation as desg,Grade as g,PAYMODE as pay,Location as loc ," +
					"Company as comp  where emp.PERNR='"+empNo+"' and emp.STAFFCAT=c.STAFFCAT and emp.LAND1=coun.LAND1 and emp.STATE=st.ID " +
					"and dept.DPTID=emp.DPTID and desg.DSGID =emp.DSGID and g.GRDID=emp.GRDID and pay.pay_id=emp.payment_method  and  emp.BUKRS=comp.BUKRS " +
					"and p.Paygroup=emp.pay_group and emp.LOCID=loc.LOCATION_CODE ";
			
			
 					ResultSet rs1=ad.selectQuery(sql1);
 					
			while(rs1.next()){
				EmployeeOfficialInfoForm empOffForm = new EmployeeOfficialInfoForm();
			
				empOffForm.setFirstName(rs1.getString("EMP_FULLNAME"));
				empOffForm.setEmployeeNumber(rs1.getString("PERNR"));
				empOffForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
				empOffForm.setCompanyName(rs1.getString("BUTXT"));
				empOffForm.setPlant(rs1.getString("WERKS"));
				empOffForm.setPayGroup(rs1.getString("Short_desc"));
				empOffForm.setEmployeeCategory(rs1.getString("CATLTEXT"));
				String reportingMgr=rs1.getString("RPTMGR");
				empOffForm.setReportingManger("");
				/*String getReportMgrName="select EMP_FULLNAME from emp_official_info where PERNR='"+reportingMgr+"'";
				ResultSet rsReprtingmagr=ad.selectQuery(getReportMgrName);
				while(rsReprtingmagr.next())
				{
					empOffForm.setReportingManger(rsReprtingmagr.getString("EMP_FULLNAME"));
				}*/
				empOffForm.setRoom(rs1.getString("ROOM"));
				empOffForm.setIpPhone(rs1.getString("IP_PHONE"));
				String apprMgr=rs1.getString("APPMGR");
				empOffForm.setApprovalManger("");
				String getApprMgrName="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where ess.employeeNumber='"+rs1.getString("PERNR")+"' " +
						"and emp.PERNR=ess.ApproverId  and ess.essType='Leave' and ess.Priority=1";
				ResultSet rsAppr=ad.selectQuery(getApprMgrName);
				while(rsAppr.next())
				{
					empOffForm.setApprovalManger(rsAppr.getString("EMP_FULLNAME"));
				}
			
				empOffForm.setFloor(rs1.getString("FLOOR"));
				empOffForm.setBuilding(rs1.getString("BUILDING"));
				//empOffForm.setAddressTypeID(rs1.getString("ADRTYP"));
				empOffForm.setDateofJoining(EMicroUtils.display1(rs1.getDate("DOJ")));
				empOffForm.setDateofLeaving(EMicroUtils.display1(rs1.getDate("DOL")));
				empOffForm.setDateofConformation(EMicroUtils.display1(rs1.getDate("DOC")));
				empOffForm.setCounID(rs1.getString("LANDX"));
				empCountry=rs1.getString("LANDX");
				
				empState=rs1.getString("state");
				String getEmpState=" select * from State where LAND1='"+rs1.getString("LAND1")+"' AND BLAND='"+empState+"'";
				ResultSet rsEmpstate=ad.selectQuery(getEmpState);
				while(rsEmpstate.next()){
					
					empOffForm.setState(rsEmpstate.getString("BEZEI"));
				}
				empOffForm.setLocation(rs1.getString("LOCATION_CODE")+"-"+rs1.getString("LOCNAME"));
				empOffForm.setGradeID(rs1.getString("GRDTXT"));
				empOffForm.setDesignation(rs1.getString("DSGSTXT"));
				empOffForm.setDepartment(rs1.getString("DPTSTXT"));
				empOffForm.setEmailid(rs1.getString("EMAIL_ID"));
				empOffForm.setTelNo(rs1.getString("TEL_NO"));
				empOffForm.setExtnNo(rs1.getString("TEL_EXTENS"));
				String eligibleESI=rs1.getString("ESI");
				if(eligibleESI.equalsIgnoreCase("Y"));
				{
				eligibleESI="Yes";
				}
				if(eligibleESI.equalsIgnoreCase("N"));
				{
				eligibleESI="No";
				}
				empOffForm.setEligibleforESIDeduction(eligibleESI);
				empOffForm.setEsiNumber(rs1.getString("ESINO"));
				String eligiblePF=rs1.getString("PF");
				if(eligiblePF.equalsIgnoreCase("Y"));
				{
					eligiblePF="Yes";
				}
				if(eligiblePF.equalsIgnoreCase("N"));
				{
					eligiblePF="No";
				}
				empOffForm.setEligibleforPFDeduction(eligiblePF);
				empOffForm.setPfNumber(rs1.getString("PFNO"));
				String eligiblePT=rs1.getString("PT");
				if(eligiblePT.equalsIgnoreCase("Y"));
				{
					eligiblePT="Yes";
				}
				if(eligiblePT.equalsIgnoreCase("N"));
				{
					eligiblePT="No";
				}
				empOffForm.setEligibleforPTDeduction(eligiblePT);
				String eligibleIT=rs1.getString("IT");
				if(eligibleIT.equalsIgnoreCase("Y"));
				{
					eligibleIT="Yes";
				}
				if(eligibleIT.equalsIgnoreCase("N"));
				{
					eligibleIT="No";
				}
				empOffForm.setEligibleforITDeduction(eligibleIT);
				empOffForm.setPanNo(rs1.getString("PANNO"));
				String eligibleBonus=rs1.getString("BONUS");
				if(eligibleBonus.equalsIgnoreCase("Y"));
				{
					eligibleBonus="Yes";
				}
				if(eligibleBonus.equalsIgnoreCase("N"));
				{
					eligibleBonus="No";
				}
				empOffForm.setBonus(eligibleBonus);
				String eligibleLeaves=rs1.getString("LEAVES");
				if(eligibleLeaves.equalsIgnoreCase("Y"));
				{
					eligibleLeaves="Yes";
				}
				if(eligibleLeaves.equalsIgnoreCase("N"));
				{
					eligibleLeaves="No";
				}
				empOffForm.setLeaves(eligibleLeaves);
				empOffForm.setSalaryCurrency(rs1.getString("WAERS"));
				empOffForm.setPaymentMethod(rs1.getString("pay_method"));
				//empOffForm.setBankName(rs1.getString("BNAME"));
				String accType=rs1.getString("BACCTYP");
				if(accType.equalsIgnoreCase("S"));
				{
					accType="Savings Account";
				}
				if(accType.equalsIgnoreCase("C"));
				{
					accType="Current Account";
				}
				empOffForm.setAccountType(accType);
				empOffForm.setAccountNumber(rs1.getString("BACCNO"));
				//empOffForm.setBranchName(rs1.getString("BRANCH"));
				empOffForm.setIfsCCode(rs1.getString("IFSC_CODE"));
				empOffForm.setMicrCode(rs1.getString("MICR_CODE"));
				details.add(empOffForm);
				
				
			//	empForm.setDepartment(rs.getString("DPTID"));
			//	empForm.setDepartIDList("DPTID");
				
				
			}
			String getLocation="select loc.LAND1 from emp_official_info emp,Location loc where emp.PERNR='"+empNo+"' and emp.LOCID=loc.LOCATION_CODE ";
			ResultSet rsLoc=ad.selectQuery(getLocation);
			while(rsLoc.next()){
				contacForm.setPlace(rsLoc.getString("LAND1"));
			}
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			int empPhotoCount=0;
			String checkImage="select count(*) from Employee_Photos where employeeNo='"+empNo+"'";
			ResultSet rsCheck=ad.selectQuery(checkImage);
			while(rsCheck.next()){
				empPhotoCount=rsCheck.getInt(1);
			}
			EmployeeOfficialInfoForm empLForm1 =new EmployeeOfficialInfoForm();
			ArrayList empPhotoList=new ArrayList();
			if(empPhotoCount>0){
				
			String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+empNo+"' ";
			ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
			while (rsEmpPhoto.next())
			{
				empLForm1.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
			request.setAttribute("employeePhoto", "employeePhoto");	
			}
			}else{
				String gender="";
				String getGender="select SEX from emp_official_info where PERNR='"+empNo+"'";
				ResultSet rsGender=ad.selectQuery(getGender);
				while(rsGender.next()){
					gender=rsGender.getString("SEX");
				}
				if(gender.equalsIgnoreCase("M"))
				{
					empLForm1.setPhotoImage("male.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}else{
					empLForm1.setPhotoImage("female.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}
			}
			empPhotoList.add(empLForm1);
		 request.setAttribute("empPhotoList", empPhotoList);
		}
		catch(SQLException se)
		{
			System.out.println("Exception @ get official info");
			se.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("details", details);
		
				
		return mapping.findForward("empoffInfo");
	}
	

	public ActionForward getSearchDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		   String keyword=contacForm.getKeyword();
			if(keyword!=null)
		   keyword=keyword.trim();
			HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
		     contacForm.setKeyword(keyword);
           String reqType = contacForm.getReqType();
     if(keyword==null || reqType==null)
     {
      	 contacForm.setMessage1("Please Enter Some Keyword");
    	/*if(keyword==null){
       	 contacForm.setMessage1("Please Enter Person To Search");
       	}
    	if(reqType!=null &&reqType.equalsIgnoreCase("Content")&& keyword==null ){
          	 contacForm.setMessage1("Please Enter Content To Search");
          	}*/
     }else{
           if( reqType.equalsIgnoreCase("Content")){
        	   
        	   if(keyword.equalsIgnoreCase("")){
        		   request.setAttribute("noRecords1", "noRecords1");
					contacForm.setMessage("Please Enter Content To Search..");
   				
   			}else{
                   
                   ArrayList conList = new ArrayList();
                   String getCondetails="select link_name,link_path,method,module ,sub_linkname from links where  link_name like '%"+keyword+"%'";
                   
                   ResultSet rd =ad.selectQuery(getCondetails);
                   
                   try {
					while(rd.next()){
						ContactsForm form3 = new ContactsForm();
						String path="";
					String module=rd.getString("module");
					String linkname=rd.getString("link_name");
					String sub_linkname=rd.getString("sub_linkname");
					if(module.equalsIgnoreCase("Main"))
					{
						path=linkname+" "+"Menu";
					}else{
						   if(linkname.equalsIgnoreCase(sub_linkname))
						   {
							   path=module+"-->"+linkname;
						   }else{
							   path=module+"-->"+sub_linkname+"-->"+linkname;
						   }
					}
						form3.setPath(path);
						form3.setKeyword(keyword);
						conList.add(form3);
						
					   }
					request.setAttribute("conList", conList);
					if(conList.size()==0)
					{
						request.setAttribute("noRecords1", "noRecords1");
						contacForm.setMessage("No Content Found");
					}
				} catch (Exception e) {
				
					e.printStackTrace();
					request.setAttribute("noRecords1", "noRecords1");
					contacForm.setMessage("Please Enter content");
				}
           }
           }

		
		System.out.println("keyword="+keyword);
		System.out.println("test"+contacForm.getTest());
		try{
		if(reqType.equalsIgnoreCase("Person"))
		{ 
			
			if(keyword.equalsIgnoreCase("")){
				request.setAttribute("noRecords", "noRecords");
				contacForm.setMessage("Please Enter Person Name To Search..");
				
			}else{
			boolean check=false;
			
			try{
			int test=Integer.parseInt(keyword);
			
			}catch (NumberFormatException e) {
			check=true;
			}
			
			ArrayList empList=new ArrayList();
			String getEmpdetails="select u.PERNR,u.EMP_FULLNAME,u.EMAIL_ID,u.TEL_EXTENS,u.BOARD_LINE,dept.DPTSTXT,desg.DSGSTXT,loc.location_code ,u.IP_PHONE,u.SEX,us.Emp_Photo from " +
			"emp_official_info as u,Location as loc,department as dept,designation as desg,users as us where us.employeenumber=u.PERNR and us.employeenumber!='"+user.getEmployeeNo()+"'  and u.active='1' and   (u.EMP_FULLNAME like '%"+keyword+"%'  " +
			"or u.EMAIL_ID  like'%"+keyword+"%' ";
			
			if(check==false){
				getEmpdetails=getEmpdetails+"or u.PERNR ="+keyword+" or u.TEL_EXTENS ="+keyword+" or u.IP_PHONE ="+keyword+"";
			}
			
			getEmpdetails=getEmpdetails+")  and u.LOCID=loc.LOCATION_CODE  and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID";
			
			
			
			ResultSet rs=ad.selectQuery(getEmpdetails);
			while(rs.next())
			{
				ContactsForm form2=new ContactsForm();
				form2.setEmpid(rs.getString("PERNR"));
				form2.setFirstName(rs.getString("EMP_FULLNAME"));
				form2.setIpPhone(rs.getString("IP_PHONE"));
				form2.setEmailID(rs.getString("EMAIL_ID"));
				form2.setDepartment(rs.getString("DPTSTXT"));
				form2.setDesignation(rs.getString("DSGSTXT"));
				form2.setContactNo(rs.getString("TEL_EXTENS"));
				form2.setBoardNo(rs.getString("BOARD_LINE"));
				form2.setLocationId(rs.getString("location_code"));
				String empSex=rs.getString("SEX");
			    String empImg=rs.getString("Emp_Photo");
			   
			    form2.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
			    
			    if(empImg.equalsIgnoreCase("")||empImg==null)
			    {
			    	if(empSex.equalsIgnoreCase("M"))
			    	{
			    		form2.setEmpPhoto("male.png");
			    	}
			    	if(empSex.equalsIgnoreCase("F"))
			    	{
			    		form2.setEmpPhoto("female.png");
			    	}
			    	
			    }
			  empList.add(form2);
			}
			request.setAttribute("empList", empList);
			if(empList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				contacForm.setMessage("No Employee Found");
			}
		}
		}
		}catch (Exception e) {
		e.printStackTrace();
		}
     }
     
    
		return mapping.findForward("empSearch");
	}
	
	public ActionForward firstRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
				ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		ArrayList contactList=new ArrayList();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			contacForm.setLocationIdList(locationList);
			contacForm.setLocationLabelList(locationLabelList);
			
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			contacForm.setDepartmentList(deparmentList);
			
		
			String locationId=contacForm.getLocationId();
			String a[]=locationId.split("-");
			locationId=a[0];
			String department=contacForm.getDepartment();
			String firstName=contacForm.getFirstName();
			firstName=firstName.trim();
			
			String getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg,users as us where us.employeenumber=u.PERNR and   " +
			"u.LOCID=loc.LOCATION_CODE  and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR!='"+user.getEmployeeNo()+"' and u.active='1'";

if(!locationId.equalsIgnoreCase(""))
{
getCount=getCount+" and u.LOCID= '"+locationId+"'";
}
if(!department.equalsIgnoreCase(""))
{
getCount=getCount+" and dept.DPTSTXT ='"+department+"'";
}
if(!firstName.equalsIgnoreCase(""))
{
getCount=getCount+" and  (u.EMP_FULLNAME like '%"+firstName+"%' or u.EMAIL_ID  like '%"+firstName+"%' or u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' )";

}


int  totalRecords=0;

	ResultSet rsCount=ad.selectQuery(getCount);
	while(rsCount.next())
	{
		totalRecords=rsCount.getInt(1);	
	}
			
			int startRecord=1;//11
			int endRecord=10;	
			if(totalRecords>10){
				  startRecord=1;
				  endRecord=10;
				  contacForm.setTotalRecords(totalRecords);
				  contacForm.setStartRecord(startRecord);
				  contacForm.setEndRecord(10);
				  }
				  else{
					  startRecord=1;
					  contacForm.setTotalRecords(totalRecords);
					  contacForm.setStartRecord(startRecord);
					  contacForm.setEndRecord(totalRecords);  
				  }

			String getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.PERNR) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.TEL_NO,u.IP_PHONE,desg.DSGSTXT,u.SEX,us.Emp_Photo,u.BOARD_LINE from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as us where us.employeenumber=u.PERNR and u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR!='"+user.getEmployeeNo()+"' and u.active='1' ";
				if(!locationId.equalsIgnoreCase(""))
				{
					getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
				}
				if(!department.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and dept.DPTSTXT ='"+department+"'";
				}
				if(!firstName.equalsIgnoreCase(""))
				{
					getContactList=getContactList+"and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' ) ";
				}
				
	getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"' order by PERNR";
	contacForm.setSearchQuery(getContactList);
	ResultSet rs=ad.selectQuery(getContactList);
	ContactsForm emp=null;
	while(rs.next())
	{
		
		emp=new ContactsForm();
		emp.setEmpid(rs.getString("PERNR"));
		emp.setFirstName(rs.getString("EMP_FULLNAME"));
		emp.setDesignation(rs.getString("DSGSTXT"));
		emp.setDepartment(rs.getString("DPTSTXT"));
		emp.setLocationId(rs.getString("location_code"));
		emp.setEmailID(rs.getString("EMAIL_ID"));
		emp.setContactNo(rs.getString("TEL_EXTENS"));
		emp.setBoardNo(rs.getString("TEL_NO"));
		emp.setIpPhone(rs.getString("IP_PHONE"));
		emp.setBoardNo(rs.getString("BOARD_LINE"));
		String empSex=rs.getString("SEX");
	    String empImg=rs.getString("Emp_Photo");
	   
	    	emp.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
	    
	    if(empImg.equalsIgnoreCase(""))
	    {
	    	if(empSex.equalsIgnoreCase("M"))
	    	{
	    		emp.setEmpPhoto("male.png");
	    	}
	    	if(empSex.equalsIgnoreCase("F"))
	    	{
	    		emp.setEmpPhoto("female.png");
	    	}
	    	
	    }

	    
		contactList.add(emp);
	}			
	request.setAttribute("contactlist", contactList);
			
		
				
			 if(totalRecords>10)
				{
					request.setAttribute("nextButton", "nextButton");
					request.setAttribute("displayRecordNo", "displayRecordNo");
				}
			
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
				
				
					
				if(contactList.size()==0)
				{
					request.setAttribute("noRecords", "noRecords");
					contacForm.setMessage("No Records");
				}
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("searchContactsForm");
	}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		try{
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
				
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			contacForm.setLocationIdList(locationList);
			contacForm.setLocationLabelList(locationLabelList);
			
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			contacForm.setDepartmentList(deparmentList);
		
			String locationId=contacForm.getLocationId();
			String a[]=locationId.split("-");
			locationId=a[0];
			String department=contacForm.getDepartment();
			String firstName=contacForm.getFirstName();
			firstName=firstName.trim();
			String lastName=contacForm.getLastName();
			String emailID=contacForm.getEmailID();
			ArrayList contactList=new ArrayList();
			int totalRecords=contacForm.getTotalRecords();//21
			int startRecord=contacForm.getStartRecord();//11
			int endRecord=contacForm.getEndRecord();	
			
			
			 startRecord=totalRecords-9;
			 endRecord=totalRecords;
			 contacForm.setTotalRecords(totalRecords);
			 contacForm.setStartRecord(startRecord);
			 contacForm.setEndRecord(totalRecords);
			 String getContactList="  Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.PERNR) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.TEL_NO,u.IP_PHONE,desg.DSGSTXT,u.SEX,us.Emp_Photo,u.BOARD_LINE from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as us where us.employeenumber=u.PERNR and u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID  and u.PERNR!='"+user.getEmployeeNo()+"' and u.active='1' ";
					if(!locationId.equalsIgnoreCase(""))
					{
						getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
					}
					if(!department.equalsIgnoreCase(""))
					{
						getContactList=getContactList+" and dept.DPTSTXT ='"+department+"'";
					}
					if(!firstName.equalsIgnoreCase(""))
					{
						getContactList=getContactList+"and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' ) ";
					}
					
		getContactList=getContactList+") as sub Where  sub.RowNum between  '"+startRecord+"' and '"+endRecord+"'";
		contacForm.setSearchQuery(getContactList);
		ResultSet rs=ad.selectQuery(getContactList);
		ContactsForm emp=null;
		while(rs.next())
		{
			emp=new ContactsForm();
			emp.setEmpid(rs.getString("PERNR"));
			emp.setFirstName(rs.getString("EMP_FULLNAME"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocationId(rs.getString("location_code"));
			emp.setEmailID(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
			emp.setBoardNo(rs.getString("TEL_NO"));
			emp.setIpPhone(rs.getString("IP_PHONE"));
			String empSex=rs.getString("SEX");
		    String empImg=rs.getString("Emp_Photo");
		    emp.setBoardNo(rs.getString("BOARD_LINE"));
		   
		    	emp.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
		    
		    if(empImg.equalsIgnoreCase(""))
		    {
		    	if(empSex.equalsIgnoreCase("M"))
		    	{
		    		emp.setEmpPhoto("male.png");
		    	}
		    	if(empSex.equalsIgnoreCase("F"))
		    	{
		    		emp.setEmpPhoto("female.png");
		    	}
		    	
		    }
			contactList.add(emp);
		}			
		request.setAttribute("contactlist", contactList);
				
			
			 request.setAttribute("disableNextButton", "disableNextButton");
				request.setAttribute("previousButton", "previousButton");
				if(contactList.size()<10)
				{
					
					request.setAttribute("previousButton", "");
					request.setAttribute("disablePreviousButton", "disablePreviousButton");
				}
				request.setAttribute("displayRecordNo", "displayRecordNo");
				
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("searchContactsForm");
	}
	
	
	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		
		ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
	try{
		LinkedList listOfMaterialCode=new LinkedList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
			
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		contacForm.setLocationIdList(locationList);
		contacForm.setLocationLabelList(locationLabelList);
		
	
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		contacForm.setDepartmentList(deparmentList);
		String locationId=contacForm.getLocationId();
		String a[]=locationId.split("-");
		locationId=a[0];
		String department=contacForm.getDepartment();
		String firstName=contacForm.getFirstName();
		firstName=firstName.trim();
		String lastName=contacForm.getLastName();
		String emailID=contacForm.getEmailID();
		ArrayList contactList=new ArrayList();
		
		int totalRecords=contacForm.getTotalRecords();//21
		int endRecord=contacForm.getStartRecord()-1;//20
		int startRecord=contacForm.getStartRecord()-10;//11
		
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		contacForm.setTotalRecords(totalRecords);
		contacForm.setStartRecord(1);
		contacForm.setEndRecord(10);
		String getContactList="  Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.PERNR) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.TEL_NO,u.IP_PHONE,desg.DSGSTXT,u.SEX,us.Emp_Photo,u.BOARD_LINE from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as us where us.employeenumber=u.PERNR and u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID  and u.PERNR!='"+user.getEmployeeNo()+"' and u.active='1' ";
			if(!locationId.equalsIgnoreCase(""))
			{
				getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
			}
			if(!department.equalsIgnoreCase(""))
			{
				getContactList=getContactList+" and dept.DPTSTXT ='"+department+"'";
			}
			if(!firstName.equalsIgnoreCase(""))
			{
				getContactList=getContactList+"and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' ) ";
			}
			
			
			getContactList=getContactList+") as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
			contacForm.setSearchQuery(getContactList);
				ResultSet rs=ad.selectQuery(getContactList);
				ContactsForm emp=null;
				while(rs.next())
				{
					emp=new ContactsForm();
					emp.setEmpid(rs.getString("PERNR"));
					emp.setFirstName(rs.getString("EMP_FULLNAME"));
					emp.setDesignation(rs.getString("DSGSTXT"));
					emp.setDepartment(rs.getString("DPTSTXT"));
					emp.setLocationId(rs.getString("location_code"));
					emp.setEmailID(rs.getString("EMAIL_ID"));
					emp.setContactNo(rs.getString("TEL_EXTENS"));
					emp.setBoardNo(rs.getString("TEL_NO"));
					emp.setIpPhone(rs.getString("IP_PHONE"));
					String empSex=rs.getString("SEX");
				    String empImg=rs.getString("Emp_Photo");
					emp.setBoardNo(rs.getString("BOARD_LINE"));
				   
				    	emp.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
				    
				    if(empImg.equalsIgnoreCase(""))
				    {
				    	if(empSex.equalsIgnoreCase("M"))
				    	{
				    		emp.setEmpPhoto("male.png");
				    	}
				    	if(empSex.equalsIgnoreCase("F"))
				    	{
				    		emp.setEmpPhoto("female.png");
				    	}
				    	
				    }
					contactList.add(emp);
				}			
				request.setAttribute("contactlist", contactList);		
request.setAttribute("contactlist", contactList);
		
	
	contacForm.setTotalRecords(totalRecords);
	contacForm.setStartRecord(startRecord);
	contacForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(contactList.size()<10)
			{
				contacForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return mapping.findForward("searchContactsForm");
	}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		try{
		LinkedList listOfMaterialCode=new LinkedList();
		ResultSet rs11 = ad.selectQuery("select LOCID," +
		"LOCNAME,location_code from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		 HttpSession session=request.getSession();
			UserInfo user=(UserInfo)session.getAttribute("user");
			
		while(rs11.next()) {
			locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
		}
		contacForm.setLocationIdList(locationList);
		contacForm.setLocationLabelList(locationLabelList);
		
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		contacForm.setDepartmentList(deparmentList);
		
	
		String locationId=contacForm.getLocationId();
		String a[]=locationId.split("-");
		locationId=a[0];
		String department=contacForm.getDepartment();
		String firstName=contacForm.getFirstName();
		firstName=firstName.trim();
		String lastName=contacForm.getLastName();
		String emailID=contacForm.getEmailID();
		ArrayList contactList=new ArrayList();
		ContactsForm emp=null;

		int totalRecords=contacForm.getTotalRecords();//21
		int startRecord=contacForm.getStartRecord();//11
		int endRecord=contacForm.getEndRecord();
	
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
			
			String getContactList="  Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.PERNR) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
			"u.EMAIL_ID,u.TEL_EXTENS,u.TEL_NO,u.IP_PHONE,desg.DSGSTXT,u.SEX,us.Emp_Photo,u.BOARD_LINE from emp_official_info as u,Location as loc,department as  dept,designation as desg,users as us where us.employeenumber=u.PERNR and u.LOCID=loc.location_code " +
			"and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.PERNR!='"+user.getEmployeeNo()+"' and u.active='1' ";
				if(!locationId.equalsIgnoreCase(""))
				{
					getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
				}
				if(!department.equalsIgnoreCase(""))
				{
					getContactList=getContactList+" and dept.DPTSTXT ='"+department+"'";
				}
				if(!firstName.equalsIgnoreCase(""))
				{
					getContactList=getContactList+"and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' ) ";
				}
				getContactList=getContactList+") as sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"'";
				contacForm.setSearchQuery(getContactList);
					ResultSet rs=ad.selectQuery(getContactList);
					while(rs.next())
					{
						emp=new ContactsForm();
						emp.setEmpid(rs.getString("PERNR"));
						emp.setFirstName(rs.getString("EMP_FULLNAME"));
						emp.setDesignation(rs.getString("DSGSTXT"));
						emp.setDepartment(rs.getString("DPTSTXT"));
						emp.setLocationId(rs.getString("location_code"));
						emp.setEmailID(rs.getString("EMAIL_ID"));
						emp.setContactNo(rs.getString("TEL_EXTENS"));
						emp.setBoardNo(rs.getString("TEL_NO"));
						emp.setIpPhone(rs.getString("IP_PHONE"));
						String empSex=rs.getString("SEX");
					    String empImg=rs.getString("Emp_Photo");
						emp.setBoardNo(rs.getString("BOARD_LINE"));
					   
					    	emp.setEmpPhoto(rs.getString("Emp_Photo")+"?time="+new Date().getTime());
					    
					    if(empImg.equalsIgnoreCase(""))
					    {
					    	if(empSex.equalsIgnoreCase("M"))
					    	{
					    		emp.setEmpPhoto("male.png");
					    	}
					    	if(empSex.equalsIgnoreCase("F"))
					    	{
					    		emp.setEmpPhoto("female.png");
					    	}
					    	
					    }
						contactList.add(emp);
					}			
					request.setAttribute("contactlist", contactList);		
request.setAttribute("contactlist", contactList);
		
		}
		System.out.println("list length="+contactList.size());
		
		 if(contactList.size()!=0)
			{
			 contacForm.setTotalRecords(totalRecords);
			 contacForm.setStartRecord(startRecord);
			 contacForm.setEndRecord(endRecord);
				request.setAttribute("nextButton", "nextButton");
				request.setAttribute("previousButton", "previousButton");
			}
			else
			{
				int start=startRecord;
				int end=startRecord;
				
				contacForm.setTotalRecords(totalRecords);
				contacForm.setStartRecord(start);
				contacForm.setEndRecord(end);
				
			}
		 if(contactList.size()<10)
		 {
			 contacForm.setTotalRecords(totalRecords);
			 contacForm.setStartRecord(startRecord);
			 contacForm.setEndRecord(startRecord+contactList.size()-1);
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
		
		return mapping.findForward("searchContactsForm");
	}
	
	public ActionForward displayContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		try{
			Connection conn=ConnectionFactory.getConnection();
			
			CallableStatement cstmt=conn.prepareCall("");
						
			LinkedList listOfMaterialCode=new LinkedList();
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			
			 HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location order by LOCATION_CODE"); 
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
			contacForm.setLocationIdList(locationList);
			contacForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			contacForm.setDepartmentList(deparmentList);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("searchContactsForm");
	}
	
	public ActionForward searchContacts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ContactsForm contacForm=(ContactsForm)form;
		LoginDao ad=new LoginDao();
		try{
		
			LinkedList listOfMaterialCode=new LinkedList();
			ResultSet rs11 = ad.selectQuery("select LOCID," +
			"LOCNAME,location_code from location order by LOCATION_CODE");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			 HttpSession session=request.getSession();
				UserInfo user=(UserInfo)session.getAttribute("user");
				
			while(rs11.next()) {
				locationList.add(rs11.getString("location_code"));
				locationLabelList.add(rs11.getString("location_code"));
			}
			contacForm.setLocationIdList(locationList);
			contacForm.setLocationLabelList(locationLabelList);
			ArrayList deparmentList=new ArrayList();
			ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
			while(rs12.next()){
				
				deparmentList.add(rs12.getString("DPTSTXT"));
			}
			
			contacForm.setDepartmentList(deparmentList);
		
			String locationId=contacForm.getLocationId();
			String department=contacForm.getDepartment();
			String firstName=contacForm.getFirstName();
			String lastName=contacForm.getLastName();
	
			ArrayList contactList=new ArrayList();
			ContactsForm emp=null;
			
			String getCount="select count(*) from emp_official_info as u,Location as loc,department as dept,designation as desg where  " +
					"u.LOCID=loc.LOCATION_CODE  and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID and u.active='1' ";
if(!locationId.equalsIgnoreCase(""))
{
	getCount=getCount+" and u.LOCID= '"+locationId+"'";
}
if(!department.equalsIgnoreCase(""))
{
	getCount=getCount+" and dept.DPTSTXT like'%"+department+"%'";
}
if(!firstName.equalsIgnoreCase(""))
{
	
	getCount=getCount+" and  (u.EMP_FULLNAME like '%"+firstName+"%' or u.EMAIL_ID  like '%"+firstName+"%' or u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' )";
	
}

int  totalRecords=0;
int  startRecord=0;
int  endRecord=0;
			ResultSet rsCount=ad.selectQuery(getCount);
			while(rsCount.next())
			{
				totalRecords=rsCount.getInt(1);	
			}
			
			 if(totalRecords>=10)
			  {
				 contacForm.setTotalRecords(totalRecords);
			  startRecord=1;
			  endRecord=10;
			  contacForm.setStartRecord(1);
			  contacForm.setEndRecord(10);
			  request.setAttribute("displayRecordNo", "displayRecordNo");
			  request.setAttribute("nextButton", "nextButton");
			  }else
			  {
				  startRecord=1;
				  endRecord=totalRecords;
				  contacForm.setTotalRecords(totalRecords);
				  contacForm.setStartRecord(1);
				  contacForm.setEndRecord(totalRecords); 
			  }
			
	String getContactList=" Select * From (SELECT ROW_NUMBER() OVER(ORDER BY u.ID) AS  RowNum,u.id,u.PERNR,u.EMP_FULLNAME,dept.DPTSTXT,loc.location_code," +
	"u.EMAIL_ID,u.TEL_EXTENS,u.IP_PHONE,desg.DSGSTXT from emp_official_info as u,Location as loc,department as  dept,designation as desg where u.LOCID=loc.LOCATION_CODE and u.active='1'  ";
		if(!locationId.equalsIgnoreCase(""))
		{
			getContactList=getContactList+"and u.LOCID= '"+locationId+"'";
		}
		if(!department.equalsIgnoreCase(""))
		{
			getContactList=getContactList+" and dept.DPTSTXT like'%"+department+"%' and dept.DPTID=u.DPTID and desg.DSGID=u.DSGID";
		}
		if(!firstName.equalsIgnoreCase(""))
		{
			getContactList=getContactList+"and  (u.EMP_FULLNAME like '%"+firstName+"%'  or u.EMAIL_ID  like '%"+firstName+"%'  or  u.TEL_EXTENS like '%"+firstName+"%' or u.IP_PHONE like '%"+firstName+"%' ) ";
		}
		
		
		getContactList=getContactList+") as sub Where  sub.RowNum between 1 and 10";
		contacForm.setSearchQuery(getContactList);
			ResultSet rs=ad.selectQuery(getContactList);
			while(rs.next())
			{
				emp=new ContactsForm();
				emp.setEmpid(rs.getString("PERNR"));
				emp.setFirstName(rs.getString("EMP_FULLNAME"));
				emp.setDesignation(rs.getString("DSGSTXT"));
				emp.setDepartment(rs.getString("DPTSTXT"));
				emp.setLocationId(rs.getString("location_code"));
				emp.setEmailID(rs.getString("EMAIL_ID"));
				emp.setContactNo(rs.getString("TEL_EXTENS"));
				emp.setIpPhone(rs.getString("IP_PHONE"));
				contactList.add(emp);
			}			
			request.setAttribute("contactlist", contactList);
			
			if(contactList.size()==0)
			{
				request.setAttribute("noRecords", "noRecords");
				
				contacForm.setMessage("No Contacts");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("searchContactsForm");
	}
	
	

}
