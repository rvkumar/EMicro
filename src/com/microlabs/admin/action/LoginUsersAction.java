package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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

import com.microlabs.admin.dao.UserDao;
import com.microlabs.admin.form.LoginUsersForm;
import com.microlabs.utilities.UserInfo;

public class LoginUsersAction extends DispatchAction {
	UserDao ad=new UserDao();
	
	
	public ActionForward getLogincount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		LinkedList userscntList=new LinkedList();
		LoginUsersForm usersForm1=(LoginUsersForm)form;
		HttpSession sesion=request.getSession();
		
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
		while(rs12.next()){

			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		usersForm1.setDeptList(deparmentList);
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
					
				while(rs11.next()) {
					locationList.add(rs11.getString("location_code"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				usersForm1.setLocationIdList(locationList);
				usersForm1.setLocationLabelList(locationLabelList);
		
		   
		
String sql="select dept.DPTSTXT,emp.LOCID ,(select count(*) from users as u1,emp_official_info as emp1,DEPARTMENT as dept1 where "
		+ " dept1.DPTSTXT =dept.DPTSTXT and emp.LOCID=emp1.LOCID and u1.employeenumber=emp1.PERNR and dept1.DPTID=emp1.DPTID and "
		+ "u1.loginCount=0 ) as notlogincnt,(select count(*) from users as u2,emp_official_info as emp2,DEPARTMENT as dept2 where "
		+ " dept2.DPTSTXT =dept.DPTSTXT and emp.LOCID=emp2.LOCID and u2.employeenumber=emp2.PERNR and dept2.DPTID=emp2.DPTID and "
		+ "u2.loginCount=1 ) as logincnt from users as u,emp_official_info as emp,DEPARTMENT as dept where u.employeenumber=emp.PERNR "
		+ "and dept.DPTID=emp.DPTID   group by  dept.DPTSTXT,emp.LOCID order by emp.LOCID,dept.DPTSTXT";	
		ResultSet rs =ad.selectQuery(sql);
 while(rs.next()){
	LoginUsersForm usersForm=new LoginUsersForm();
	usersForm.setDepartment(rs.getString("DPTSTXT"));
	usersForm.setLocation(rs.getString("LOCID"));
	usersForm.setNotlogincount(rs.getString("notlogincnt"));
	usersForm.setLogincount(rs.getString("logincnt"));
	userscntList.add(usersForm);
}
if(userscntList.size()>0)
	request.setAttribute("userscountList", userscntList);
else
	request.setAttribute("noRecords", "noRecords");
request.setAttribute("backbutton", "backbutton");



		
		return mapping.findForward("loginUsersList");
	}
	
	public ActionForward loginUsersList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUsersForm usersForm=(LoginUsersForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		LinkedList usersList=new LinkedList();
		try{
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
					
				while(rs11.next()) {
					locationList.add(rs11.getString("location_code"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				usersForm.setLocationIdList(locationList);
				usersForm.setLocationLabelList(locationLabelList);
		
		usersForm.setDeptList(deparmentList);
		
		 Date date = new Date();
		    SimpleDateFormat sdf;
		    sdf = new SimpleDateFormat("dd/MMM/yyyy ");
		  
		    System.out.println(sdf.format(date));
		    String getLoginUsersList="select u.Ip_Address,u.Login_Time,emp.LOCID,u.employeenumber,emp.EMP_FULLNAME,dept.DPTSTXT from users as u,emp_official_info as emp,"
		    		+ "DEPARTMENT as dept where  u.employeenumber=emp.PERNR and emp.DPTID=dept.DPTID and  convert(nvarchar(11),u.lastlogindate,103)='"+sdf.format(date)+"' order by Login_Time desc";
		    		ResultSet rs =ad.selectQuery(getLoginUsersList);
		
			while(rs.next()){
				LoginUsersForm usersForm1=new LoginUsersForm();	
				usersForm1.setLocation(rs.getString("LOCID"));
				usersForm1.setEmployeeID(rs.getString("employeenumber"));
				usersForm1.setEmpName(rs.getString("EMP_FULLNAME"));
				usersForm1.setDept(rs.getString("DPTSTXT"));
				String ip=rs.getString("Ip_Address");
				usersForm1.setIpNo(ip);
				
				
				usersForm1.setLogintime(rs.getString("Login_Time"));
				
				
		usersList.add(usersForm1);
			}
		if(usersList.size()>0)
			request.setAttribute("usersList1", usersList);
		else
			request.setAttribute("noRecords", "noRecords");
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("table", "table");
		request.setAttribute("loginbutton", "loginbutton");	
		return mapping.findForward("loginUsersList");
	}
	
	public ActionForward searchloginUsersList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		LoginUsersForm usersForm=(LoginUsersForm)form;
		HttpSession sesion=request.getSession();
		LinkedList usersList=new LinkedList();
		
		
		ArrayList deparmentList=new ArrayList();
		ResultSet rs12 =ad.selectQuery("select DPTSTXT from department order by DPTSTXT");
		while(rs12.next()){
			
			deparmentList.add(rs12.getString("DPTSTXT"));
		}
		
		ResultSet rs11 = ad.selectQuery("select LOCID," +
				"LOCNAME,location_code from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
					
				while(rs11.next()) {
					locationList.add(rs11.getString("location_code"));
					locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
				}
				usersForm.setLocationIdList(locationList);
				usersForm.setLocationLabelList(locationLabelList);
		
		usersForm.setDeptList(deparmentList);
		
		Date dNow = new Date( );
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		String dateNow = ft.format(dNow);
		
		String frmdate=usersForm.getFrmDate();
		String todate=usersForm.getToDate();
		String dept=usersForm.getDepartment();
		String loc=usersForm.getLocationId();
		String empno=usersForm.getPernr();
		
		if(dept==null)
		{
			
			dept="";
		}
		if(loc==null)
		{
			
			loc="";
		}
		if(empno==null)
		{
			
			empno="";
		}
	
				
		if(frmdate!=null&&todate!=null)
		{
		String a[]=frmdate.split("/");
		frmdate=a[2]+"-"+a[1]+"-"+a[0];
		String b[]=todate.split("/");
		todate=b[2]+"-"+b[1]+"-"+b[0];	
			
		}
		if(frmdate==null&&todate==null)
		{
			frmdate=dateNow;
			todate=dateNow;
		}
		
	
		
		   String getLoginUsersList="select u.Ip_Address,u.Login_Time,u.Logout_Time,emp.LOCID,u.employeenumber,emp.EMP_FULLNAME,dept.DPTSTXT from Login_History as u,"
				+ "emp_official_info as emp,DEPARTMENT as dept where  u.employeenumber=emp.PERNR and emp.DPTID=dept.DPTID and (convert(date,u.Login_Time)"
				+ "between('"+frmdate+"') and ('"+todate+"'))";
		   
		   if(!dept.equalsIgnoreCase(""))
		   {
			   getLoginUsersList=getLoginUsersList+"and dept.DPTSTXT='"+dept+"' ";
		   }
		   if(!loc.equalsIgnoreCase(""))
		   {
			   getLoginUsersList=getLoginUsersList+"and emp.LOCID='"+loc+"'";
		   }
		   
		   if(!empno.equalsIgnoreCase(""))
		   {
			   getLoginUsersList=getLoginUsersList+"and (emp.EMP_FULLNAME like '%"+empno+"%' or emp.PERNR like '%"+empno+"%')";
		   }
		   
		    getLoginUsersList=getLoginUsersList+" order by Login_Time desc ";
		   
		ResultSet rs =ad.selectQuery(getLoginUsersList);
		try{
			while(rs.next()){
				LoginUsersForm usersForm1=new LoginUsersForm();	
				usersForm1.setLocation(rs.getString("LOCID"));
				usersForm1.setEmployeeID(rs.getString("employeenumber"));
				usersForm1.setEmpName(rs.getString("EMP_FULLNAME"));
				usersForm1.setDept(rs.getString("DPTSTXT"));
				String ip=rs.getString("Ip_Address");
				usersForm1.setIpNo(ip);
				usersForm1.setLogintime(rs.getString("Login_Time"));
				usersForm1.setLogoutime(rs.getString("Logout_Time"));
				usersList.add(usersForm1);
			}
		if(usersList.size()>0)
			request.setAttribute("usersList", usersList);
		else
			request.setAttribute("noRecords", "noRecords");
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("loginbutton", "loginbutton");
		return mapping.findForward("loginUsersList");
	}

}
