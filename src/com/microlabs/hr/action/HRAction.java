package com.microlabs.hr.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.lowagie.text.pdf.PdfWriter;    

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.util.StringUtils;

import com.microlabs.admin.form.LinksForm;
import com.microlabs.ess.form.EmployeeOfficialInfoForm;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.EmpOfficalInformationForm;
import com.microlabs.hr.form.HRForm;
import com.microlabs.it.Dao.ITDao;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;


public class HRAction extends DispatchAction{
	
	public String Empname(String a)
	{
		HRDao ad=new HRDao();
		
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
	public ActionForward newHRassetform(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		HRForm.setAssetType(HRForm.getAssetType());
		HRForm.setpERNR(HRForm.getpERNR());
		
		String[] a=null;
		
		a=empDet(HRForm.getpERNR());
		HRForm.setEmpname(a[0]);
		HRForm.setEmpdep(a[2]);
		HRForm.setEmpdesg(a[3]);
		HRForm.setEmploc(a[1]);
		HRForm.setEmpext(a[4]);
		
		request.setAttribute("save", "save");
		return mapping.findForward("newHRassetform");
	}
	
private String[] empDet( String empno) {
	String[] a = new String[10];
	
	
	if(empno==null)
	{
		return a;
	}
	if(empno.equalsIgnoreCase(""))
	{
		return a;
	}
	
	HRDao ad=new HRDao();
		String emp=" select emp.eMP_FULLNAME,emp.lOCID,dep.DPTSTXT,desg.DSGSTXT,emp.tel_extens from Emp_official_info emp "
				+ " , Department dep ,Designation desg  where emp.dPTID = dep.DPTID and emp.pERNR='"+empno+"' "
						+ " and desg.DSGID= emp.dSGID  ";
		ResultSet ae=ad.selectQuery(emp);

	
	
		try {
			while(ae.next())
			{
			
			

			    a[0]=ae.getString("eMP_FULLNAME");
			    a[1]=ae.getString("lOCID");
			    a[2]=ae.getString("DPTSTXT");
			    a[3]=ae.getString("DSGSTXT");
			    a[4]=ae.getString("tel_extens");
			   


			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
		}

public ActionForward updateAsset(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	
	HRForm hr=(HRForm)form;	
	HRDao ad=new HRDao();
	
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	if(user==null){
		request.setAttribute("message","Session Expried! Try to Login again!");
		return mapping.findForward("displayiFrameSession");
	}
	String uniqeid=hr.getId();

	String saveAssets="";
	String insuiss=hr.getInsurance_Issue_date();
	if(insuiss==null)
		insuiss="";
	if(!insuiss.equalsIgnoreCase(""))
	{
	String a[]=insuiss.split("/");
	insuiss=a[2]+"-"+a[1]+"-"+a[0];
	}
	
	String insuexp=hr.getInsurance_Exp_date();
	if(insuexp==null)
		insuexp="";
	if(!insuexp.equalsIgnoreCase(""))
	{
	String a[]=insuexp.split("/");
	insuexp=a[2]+"-"+a[1]+"-"+a[0];
	}
	
	if(!hr.getAssetType().equalsIgnoreCase("Data Card"))
	{
		 saveAssets="INSERT INTO Assets(assetType,assetDesc,PERNR,Model,Make,Vehicle_no,Insurance_no,Insurance_Compny,Insurance_Issue_date,Insurance_Exp_date,Prev_user,Created_by,Created_Date,Modified_by,Modified_date)"
			+ " values('"+hr.getAssetType()+"','"+hr.getAssetDesc()+"','"+hr.getpERNR()+"','"+hr.getModel()+"','"+hr.getMake()+"','"+hr.getVehicle_no()+"',"
					+ "'"+hr.getInsurance_no()+"','"+hr.getInsurance_Compny()+"','"+insuiss+"','"+insuexp+"','"+hr.getPrev_user()+"','"+hr.getCreated_by()+"','"+hr.getCreated_Date()+"','"+user.getEmployeeNo()+"',getdate())";
	}
	else
	{
		 saveAssets="INSERT INTO Assets(assetType,assetDesc,PERNR,Service_provider,Data_card_no,Default_pwd,Service_plan,Created_by,Created_Date,Modified_by,Modified_date)"
			+ " values('"+hr.getAssetType()+"','"+hr.getAssetDesc()+"','"+hr.getpERNR()+"','"+hr.getService_provider()+"','"+hr.getData_card_no()+"','"+hr.getDefault_pwd()+"',"
					+ "'"+hr.getService_plan()+"','"+hr.getCreated_by()+"','"+hr.getCreated_Date()+"','"+user.getEmployeeNo()+"',getdate())";
	}
		int i=0;
			i=ad.SqlExecuteUpdate(saveAssets);
			if(i>0){
				hr.setMessage("Asset details has been Modified");
				String hl="delete Assets where id='"+uniqeid+"' ";
				int k=ad.SqlExecuteUpdate(hl);			
			}
			else
			{
				hr.setMessage2("Error..While Modifying Asset details");
			}
	
		
	displayHRAssetList(mapping, form, request, response);

	return mapping.findForward("displayHRAssetList");
}
public ActionForward editAsset(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	
	HRForm hr=(HRForm)form;	
	HRDao ad=new HRDao();
	
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	if(user==null){
		request.setAttribute("message","Session Expried! Try to Login again!");
		return mapping.findForward("displayiFrameSession");
	}
	String uniqeid=request.getParameter("reqId");
	hr.setId(uniqeid);
	
	
	String set="select * from assets where id='"+uniqeid+"'";
	ResultSet  v=ad.selectQuery(set);
	try {
		while(v.next())
		{
			hr.setAssetType(v.getString("assetType"));
			hr.setAssetDesc(v.getString("assetDesc"));
			hr.setpERNR(v.getString("pernr"));
			String[] a=null;
			
			a=empDet(hr.getpERNR());
			hr.setEmpname(a[0]);
			hr.setEmpdep(a[2]);
			hr.setEmpdesg(a[3]);
			hr.setEmploc(a[1]);
			hr.setEmpext(a[4]);
			
			hr.setModel(v.getString("Model"));
			hr.setMake(v.getString("Make"));
			hr.setVehicle_no(v.getString("Vehicle_no"));
			hr.setInsurance_no(v.getString("Insurance_no"));
			hr.setInsurance_Compny(v.getString("Insurance_Compny"));
			if(v.getString("Insurance_Issue_date")!=null)
			hr.setInsurance_Issue_date(EMicroUtils.display(v.getDate("Insurance_Issue_date")));
			if(v.getString("Insurance_Exp_date")!=null)
			hr.setInsurance_Exp_date(EMicroUtils.display(v.getDate("Insurance_Exp_date")));
			
			hr.setPrev_user(v.getString("Prev_user"));
			if(!v.getString("Prev_user").equalsIgnoreCase(""))
			{
				String[] b=null;
				
				b=empDet(v.getString("Prev_user"));
				hr.setPrev_user_name(b[0]);
				hr.setPrev_user_dep(b[2]);
				hr.setPrev_user_des(b[3]);
			}
			
			hr.setService_provider(v.getString("Service_provider"));
			hr.setData_card_no(v.getString("Data_card_no"));
			hr.setDefault_pwd(v.getString("Default_pwd"));
			hr.setService_plan(v.getString("Service_plan"));
			hr.setCreated_by(v.getString("Created_by"));
			hr.setCreated_Date(v.getString("Created_Date"));
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	request.setAttribute("update", "update");
	return mapping.findForward("newHRassetform");
	
}
public ActionForward addnewHRAsset(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	
	HRForm hr=(HRForm)form;	
	HRDao ad=new HRDao();
	
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	if(user==null){
		request.setAttribute("message","Session Expried! Try to Login again!");
		return mapping.findForward("displayiFrameSession");
	}
	String saveAssets="";
	String insuiss=hr.getInsurance_Issue_date();
	if(insuiss==null)
		insuiss="";
	if(!insuiss.equalsIgnoreCase(""))
	{
	String a[]=insuiss.split("/");
	insuiss=a[2]+"-"+a[1]+"-"+a[0];
	}
	
	String insuexp=hr.getInsurance_Exp_date();
	if(insuexp==null)
		insuexp="";
	if(!insuexp.equalsIgnoreCase(""))
	{
	String a[]=insuexp.split("/");
	insuexp=a[2]+"-"+a[1]+"-"+a[0];
	}
	
	if(!hr.getAssetType().equalsIgnoreCase("Data Card"))
	{
		 saveAssets="INSERT INTO Assets(assetType,assetDesc,PERNR,Model,Make,Vehicle_no,Insurance_no,Insurance_Compny,Insurance_Issue_date,Insurance_Exp_date,Prev_user,Created_by,Created_Date)"
			+ " values('"+hr.getAssetType()+"','"+hr.getAssetDesc()+"','"+hr.getpERNR()+"','"+hr.getModel()+"','"+hr.getMake()+"','"+hr.getVehicle_no()+"',"
					+ "'"+hr.getInsurance_no()+"','"+hr.getInsurance_Compny()+"','"+insuiss+"','"+insuexp+"','"+hr.getPrev_user()+"','"+user.getEmployeeNo()+"',getdate())";
	}
	else
	{
		 saveAssets="INSERT INTO Assets(assetType,assetDesc,PERNR,Service_provider,Data_card_no,Default_pwd,Service_plan,Created_by,Created_Date)"
			+ " values('"+hr.getAssetType()+"','"+hr.getAssetDesc()+"','"+hr.getpERNR()+"','"+hr.getService_provider()+"','"+hr.getData_card_no()+"','"+hr.getDefault_pwd()+"',"
					+ "'"+hr.getService_plan()+"','"+user.getEmployeeNo()+"',getdate())";
	}
		int i=0;
			i=ad.SqlExecuteUpdate(saveAssets);
			if(i>0){
				hr.setMessage("Asset details has been saved");
			}
			else
			{
				hr.setMessage2("Error..While Saving Asset details");
			}
	
			displayHRAssetList(mapping, form, request, response);

			return mapping.findForward("displayHRAssetList");
			
			
}
public ActionForward assetlog(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) {
	
	HRForm HRForm=(HRForm)form;	
	HRDao ad=new HRDao();
	
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	if(user==null){
		request.setAttribute("message","Session Expried! Try to Login again!");
		return mapping.findForward("displayiFrameSession");
	}
	
	Date dNow = new Date( );
	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
	String exportdate=ft.format(dNow);
	response.setHeader("Content-Disposition", "inline; filename="+exportdate+"_AssetLog_Report.xls");
	
	ArrayList g=new ArrayList();
	String lis="select * from Assets where created_by='"+user.getEmployeeNo()+"'";
	ResultSet ff=ad.selectQuery(lis);
	try {
		while(ff.next())
		{
			HRForm hr=new HRForm();
			hr.setId(ff.getString("id"));
			hr.setAssetType(ff.getString("assetType"));
			hr.setAssetDesc(ff.getString("assetDesc"));
			hr.setEmpname(Empname(ff.getString("pernr")));				
			hr.setModel(ff.getString("Model"));
			hr.setMake(ff.getString("Make"));
			hr.setVehicle_no(ff.getString("Vehicle_no"));
			hr.setInsurance_no(ff.getString("Insurance_no"));
			hr.setInsurance_Compny(ff.getString("Insurance_Compny"));
			if(ff.getString("Insurance_Issue_date")!=null)
			hr.setInsurance_Issue_date(EMicroUtils.display(ff.getDate("Insurance_Issue_date")));
			if(ff.getString("Insurance_Exp_date")!=null)
			hr.setInsurance_Exp_date(EMicroUtils.display(ff.getDate("Insurance_Exp_date")));
			
			hr.setPrev_user(Empname(ff.getString("Prev_user")));			
			
			hr.setService_provider(ff.getString("Service_provider"));
			hr.setData_card_no(ff.getString("Data_card_no"));
			hr.setDefault_pwd(ff.getString("Default_pwd"));
			hr.setService_plan(ff.getString("Service_plan"));
			hr.setCreated_by(Empname(ff.getString("Created_by")));
			hr.setCreated_Date(ff.getString("Created_Date"));
			hr.setModified_by(Empname(ff.getString("Modified_by")));
			hr.setModified_date(ff.getString("modified_date"));
			if(ff.getString("Delete_flag").equalsIgnoreCase("0"))
			hr.setDelete_flag("No");
			else
		    hr.setDelete_flag("YEs");	
			
			g.add(hr);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	request.setAttribute("hrassets", g);
	
	return mapping.findForward("displayHRAssetLOG");

}


	public ActionForward displayHRAssetList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		
		ArrayList g=new ArrayList();
		String lis="select CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1,* from Assets where created_by='"+user.getEmployeeNo()+"' and delete_flag=0";
		ResultSet ff=ad.selectQuery(lis);
		try {
			while(ff.next())
			{
				HRForm hr=new HRForm();
				hr.setId(ff.getString("id"));
				hr.setAssetType(ff.getString("assetType"));
				hr.setAssetDesc(ff.getString("assetDesc"));
				hr.setEmpname(Empname(ff.getString("pernr")));
				hr.setCreated_Date(ff.getString("Created_date1"));
				g.add(hr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("hrassets", g);
		
		return mapping.findForward("displayHRAssetList");

	}
	
	public ActionForward deleteAsset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		HRForm.setMessage("");
		HRForm.setMessage2("");
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
	    try{
	    	int j=0;
			for(int i=0;i<documentCheck.length;i++)
			{
			String updateRentDt="update  Assets set delete_flag=1,modified_by='"+user.getEmployeeNo()+"',modified_date=getdate() where  Created_by='"+user.getEmployeeNo()+"' and Id='"+documentCheck[i]+"' ";
		   j=ad.SqlExecuteUpdate(updateRentDt);
			}
		    if(j>0){
		    	HRForm.setMessage("Asset details has been deleted");
		    	
		    }else{
		    	HRForm.setMessage2("Error..While deleting asset details ..");
		    }
		    ArrayList g=new ArrayList();
			String lis="select CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1,* from Assets where created_by='"+user.getEmployeeNo()+"' and delete_flag=0";
			ResultSet ff=ad.selectQuery(lis);
			try {
				while(ff.next())
				{
					HRForm hr=new HRForm();
					hr.setId(ff.getString("id"));
					hr.setAssetType(ff.getString("assetType"));
					hr.setAssetDesc(ff.getString("assetDesc"));
					hr.setEmpname(Empname(ff.getString("pernr")));
					hr.setCreated_Date(ff.getString("Created_date1"));
					g.add(hr);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("hrassets", g);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return mapping.findForward("displayHRAssetList");
	}
	
	public ActionForward assignemployees(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		
		HRForm.setLocationIdList(locationList);
		HRForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		HRForm.setDeptIdList(deptList);
		HRForm.setDeptLabelList(deptLabelList);
		
		
		
		String abc="";
		String def="";
				
		String checked=request.getParameter("check");
		String unchecked=request.getParameter("uncheck");
		
		if(!checked.equalsIgnoreCase(""))
		{
		abc="update emp_official_info set PAYSLIP_ACTIVE=1 where PERNR in ("+checked+")";
		int j=ad.SqlExecuteUpdate(abc);
		}
		
		if(!unchecked.equalsIgnoreCase(""))
		{
		 def="update emp_official_info set PAYSLIP_ACTIVE=0 where PERNR in ("+unchecked+")";
		 int j=ad.SqlExecuteUpdate(def);
		}
		
		/*//chk count
		String  present="";
		String check="";
		String count="select EmpNos from HR_PAYSLIP_RIGHT";
		ResultSet co=ad.selectQuery(count);
		while(co.next())
		{
			present=co.getString("EmpNos");
			check="Y";
		}
		if(!check.equalsIgnoreCase("Y"))
		{
			
				
		String query="insert into HR_PAYSLIP_RIGHT values('"+checked+"')";
		
		int i=ad.SqlExecuteUpdate(query);
		
		}
		else
		{
			if(!checked.equalsIgnoreCase(""))
			{
			checked=present+","+checked;
			
			
			String[] strArr = {checked};
	        //convert string array to list
	        List<String> tmpList = Arrays.asList(strArr);
	        //create a treeset with the list, which eliminates duplicates
	        HashSet<String> unique = new HashSet<String>(tmpList);
	        System.out.println(unique);
	
			}
			else
			{
				
			}
			
			String query="update HR_PAYSLIP_RIGHT set EmpNos='"+checked+"'";
			int i=ad.SqlExecuteUpdate(query);
			
		}*/
		
		
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		getemployeesList(mapping, HRForm, request, response);
		return mapping.findForward("empllist");
	}
	
	public ActionForward displaypayslipemployees(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		
		HRForm.setLocationIdList(locationList);
		HRForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		HRForm.setDeptIdList(deptList);
		HRForm.setDeptLabelList(deptLabelList);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mapping.findForward("empllist");
	}
	
	
	
	public ActionForward getemployeesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		ResultSet rs11 = ad.selectQuery("select * from location ");
		ArrayList locationList = new ArrayList();
		ArrayList locationLabelList = new ArrayList();
		try {
			while (rs11.next()) {
				locationList.add(rs11.getString("LOCATION_CODE"));
				locationLabelList.add(rs11.getString("LOCATION_CODE") + " - "
						+ rs11.getString("LOCNAME"));
			}
		
		HRForm.setLocationIdList(locationList);
		HRForm.setLocationLabelList(locationLabelList);

		ResultSet rs12 = ad.selectQuery("select * from DEPARTMENT ");
		ArrayList deptList = new ArrayList();
		ArrayList deptLabelList = new ArrayList();
		while (rs12.next()) {
			deptList.add(rs12.getString("DPTID"));
			deptLabelList.add(rs12.getString("DPTSTXT"));
		}
		HRForm.setDeptIdList(deptList);
		HRForm.setDeptLabelList(deptLabelList);
		
		
		
		String[] loc = 	HRForm.getLocationId();
		String[] cat = 	HRForm.getCat();
		String[] dept = HRForm.getDepartments();

		StringBuffer reqloc = new StringBuffer();
		for (int i = 0; i < loc.length; i++) {
			reqloc.append(loc[i] + ",");
		}
		String ACtualloc = reqloc.substring(0, reqloc.length() - 1).toString();

		StringBuffer reqcat = new StringBuffer();
		for (int i = 0; i < cat.length; i++) {
			reqcat.append(cat[i] + ",");
		}
		String ACtualcat = reqcat.substring(0, reqcat.length() - 1).toString();

		StringBuffer reqdept = new StringBuffer();
		for (int i = 0; i < dept.length; i++) {
			reqdept.append(dept[i] + ",");
		}
		String ACtualdept = reqdept.substring(0, reqdept.length() - 1)
				.toString();
		
		ArrayList empList=new ArrayList();
		String query="";
		
		 query="Select * from emp_official_info as e,DESIGNATION as desg,DEPARTMENT as dep where LOCID in ('"+ACtualloc+"')  and STAFFCAT in ("+ACtualcat+") and dep.DPTID in ("+ACtualdept+") and e.DPTID=dep.DPTID and desg.DSGID=e.DSGID  and e.ACTIVE='1' ";
		 
		 if(!HRForm.getPernr().equalsIgnoreCase(""))
		 
		 {
			 query=query+" and e.pernr ='"+HRForm.getPernr()+"' ";
		 }
		 
		 query=query+"order by pernr";
		ResultSet rs=ad.selectQuery(query);
		String alreadypresent="";
		while(rs.next())
		
		{
			EmpOfficalInformationForm emp=new EmpOfficalInformationForm();
			
			emp.setLoginName(rs.getString("Emp_FullName"));
			emp.setEmployeeNumber(rs.getString("PERNR"));			
			emp.setFirstName(rs.getString("Emp_FullName"));
			emp.setDesignation(rs.getString("DSGSTXT"));
			emp.setDepartment(rs.getString("DPTSTXT"));
			emp.setLocation(rs.getString("LOCID"));
			emp.setEmailid(rs.getString("EMAIL_ID"));
			emp.setContactNo(rs.getString("TEL_EXTENS"));
			emp.setIpPhoneNo(rs.getString("IP_PHONE"));
			
			if(rs.getString("PAYSLIP_ACTIVE").equalsIgnoreCase("1"))
			{
			emp.setEmployeeName(rs.getString("PERNR"));
			emp.setAppr(rs.getString("PERNR"));
			}
			
			/*String abc="select * from HR_PAYSLIP_RIGHT where  EmpNos like '%"+rs.getString("PERNR")+"%'";
			ResultSet as=ad.selectQuery(abc);
			try {
				while(as.next())
				{
					emp.setCounID("Yes");
					emp.setEmployeeName(rs.getString("PERNR"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
			
			
			empList.add(emp);
		}
		
		
		request.setAttribute("employeeList", empList);
		
		
		if(empList.size()==0)
		{
			request.setAttribute("norecords", "norecords");
		}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
		
		
		return mapping.findForward("empllist");
	}
	
	public ActionForward displaywomenPolicy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("womenpolicylist");

	}
	
	
	public ActionForward displayleavePolicy(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("leavpolicylist");

	}
	
	public ActionForward displayguesthouse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("guesthouse");
	
	}
	public ActionForward displayIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayIssues()----");
		HRForm hrForm=(HRForm)form;
		
		
		return mapping.findForward("displayIssusesForm");
	}

	public ActionForward displayReqIssues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("displayReqIssues()----");
		HRForm hrForm=(HRForm)form;
		String reqStatus=hrForm.getStatus();
		
		
		
		
		return mapping.findForward("displayIssusesForm");
	}
	
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		String sql="select * from links where id in("+user.getIncludeSubLinks()+") order by id";
		ResultSet rs=ad.selectQuery(sql);
		try{
			String sql3="select id from links where module='"+id+"'";
			ResultSet rs2=ad.selectQuery(sql3);
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			String[] a1=userLinks.split(",");
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
			
			//list.removeAll(list1);
			
			String inkLinks="";
			String commaDelimitedString = StringUtils.collectionToCommaDelimitedString(list1);
			 commaDelimitedString=commaDelimitedString;
			
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module ='HR' " +
					"and id in("+commaDelimitedString+") order by id";
			
			ResultSet rs1=ad.selectQuery(sql1);
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<LinksForm> allLinkList=new LinkedList<LinksForm>();
		 	LinkedHashMap<LinksForm,LinkedList<LinksForm>> finalLnkdList= new LinkedHashMap<LinksForm,LinkedList<LinksForm>>();
		 	
			while(rs1.next()){
				String slName = rs1.getString("sub_linkname");
				System.out.println("rs1.getString(sub_linkname) **************"+slName);
				mainMenuSet.add(rs1.getString("sub_linkname"));
				LinksForm l=new LinksForm();
					String url = rs1.getString("url");
					/*if(slName.equalsIgnoreCase("Holiday List"))
					{
						url = rs1.getString("url")+"&locationName=";
					}*/
					l.setLinkPath(url);
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(slName);
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
			 	for(String main:mainMenuSet){
			 		LinkedList<LinksForm> links=new LinkedList<LinksForm>();
			 		LinksForm temp=null;
			 		for(LinksForm aa:allLinkList){
			 				for(LinksForm sl:allLinkList)
			 				{
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		for(LinksForm g:links) System.out.println(g.getLinkName());
	 				finalLnkdList.put(temp, links);
			 	}
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 	for (Map.Entry<LinksForm,LinkedList<LinksForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() + ", Value = " + entry.getValue().size()+"");
		 	    for (LinksForm lin : entry.getValue()) {
				}
		 	}
		 	session.setAttribute("SUBLINKS", finalLnkdList);
			 
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		
		LinkedHashMap hm=(LinkedHashMap)session.getAttribute("SUBLINKS");
		
		String sql1="select * from archieves where link_name='"+id+"' and module='Main' ";
		
		ResultSet rs1=ad.selectQuery(sql1);
		
		try{
			while(rs1.next()) {
				 
				HRForm.setContentDescription(rs1.getString("content_description"));
				HRForm.setFileFullPath(rs1.getString("file_name"));
				HRForm.setVideoFullPath(rs1.getString("video_name"));
			}
		
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		request.setAttribute("MenuIcon", id);
		return mapping.findForward("display");
	}
	
	

	
	public ActionForward display1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRForm HRForm=(HRForm)form;	
		HRDao ad=new HRDao();
		
		
		String linkName=request.getParameter("sId"); 
		
		String module=request.getParameter("id"); 
		String subLinkOf=request.getParameter("subLink");
		if(subLinkOf != null){
			module = subLinkOf;
		}
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			
			//LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			 	
			String sql3="select id from links where module='"+module+"'";
			
			ResultSet rs2=ad.selectQuery(sql3);
			
			String inLinks="";
			while (rs2.next()) {
				inLinks+=rs2.getString("id")+",";
			}
			
			
			String sql11="select * from archieves where link_name='"+linkName+"' and module='"+module+"'";
			
			ResultSet rs11=ad.selectQuery(sql11);
			
			try{
				while(rs11.next()) {
					 
					HRForm.setContentDescription(rs11.getString("content_description"));
					HRForm.setFileFullPath(rs11.getString("file_name"));
					HRForm.setVideoFullPath(rs11.getString("video_name"));
				}
			
			}catch(SQLException se){
				se.printStackTrace();
			}
			
			
			inLinks=inLinks.substring(0, inLinks.lastIndexOf(","));
			String[] array = new HashSet<String>(Arrays.asList(inLinks.split(","))).toArray(new String[0]);
			
			List<String> list = new ArrayList<String>(array.length);  
			for (String s : array) {  
			    list.add(s);  
			}  
			
			
			
			String userLinks=user.getIncludeSubLinks()+","+user.getIncludeSubSubLinks();
			
			String[] a1=userLinks.split(",");
			
			List<String> list1 = new ArrayList<String>(a1.length);  
			for (String s : a1) {  
			    list1.add(s);  
			}  
		    
			
			
			Collection listOne = list;
	        Collection listTwo = list1;
	        
	        listTwo.retainAll(listOne);
			
			//list.removeAll(list1);
			
			String inkLinks="";
			
			
			/*String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname,icon_name " +
					" from links  " +
					" where module='ESS'" +
					" and id in(13,14,18,19,59,60,61,62,63,64,65,66,67,68,69,70,71,86) order by priority";
		*/	
			
			String sql1="select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as url,module,link_name,sub_linkname," +
					"icon_name " +
			" from links  " +
			" where module='HR'" +
			" and id in(48,50,51,72,76,52,44,49,73,83,106) order by priority";
			
			
			ResultSet rs1=ad.selectQuery(sql1);
				
				
			//select id,priority,link_path+'?method='+method+'&sId='+link_name+'&id='+module as 
			// url,module,link_name,sub_linkname  from links   where module ='News And Media' and id 
			//in(38,39,40,45,46) order by id	
			
			//LinkedHashMap hm=new LinkedHashMap();	
				
			
			LinkedHashSet<String> mainMenuSet=new LinkedHashSet<String>();
			LinkedList<HRForm> allLinkList=new LinkedList<HRForm>();
		 	LinkedHashMap<HRForm,LinkedList<HRForm>> finalLnkdList= new LinkedHashMap<HRForm,LinkedList<HRForm>>();
		 	
			while(rs1.next()){
				
				mainMenuSet.add(rs1.getString("sub_linkname"));
				HRForm l=new HRForm();
					l.setLinkPath(rs1.getString("url"));
					l.setPriority(""+rs1.getString("priority"));
					l.setSubLinkName(rs1.getString("sub_linkname"));
					l.setLinkName(rs1.getString("link_name"));
					l.setIconName(rs1.getString("icon_name"));
				allLinkList.add(l);
			}	
		 		
				
			 	for(String main:mainMenuSet){
			 		LinkedList<HRForm> links=new LinkedList<HRForm>();
			 		HRForm temp=null;
			 		System.out.println(" -- "+main+"");
			 		for(HRForm aa:allLinkList){
			 				for(HRForm sl:allLinkList)
			 				{
			 						
			 					if(!sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main))
			 						links.add(sl);
			 					else if(sl.getLinkName().equals(sl.getSubLinkName()) && aa.getLinkName().equals(sl.getLinkName()) && aa.getSubLinkName().equals(main) )
			 						temp=sl;
			 				}
			 		}
			 		for(HRForm g:links) System.out.println(g.getLinkName());
	 				
	 				finalLnkdList.put(temp, links);
			 	}
			 	
			 	HRForm.setLinkName(linkName);
			 	
			 	
		 	
		 	//LinkedHashMap<LinksForm,ArrayList<LinksForm>> map = new LinkedHashMap<LinksForm,ArrayList<LinksForm>>();
		 /*	for (Map.Entry<HRForm,LinkedList<HRForm>> entry : finalLnkdList.entrySet()) {
		 	    System.out.println("Key = " + entry.getKey().getLinkName() +"" +"Link Path is"+ entry.getKey().getLinkPath()+"Get Image "+ entry.getKey().getIconName()+ ", Value = " + entry.getValue().size()+"");
		 	    for (HRForm lin : entry.getValue()) {

				}
		 	}*/
		 	
		 	
		 	String sql2="select * from cms_sublinks where main_linkname='"+module+"' " +
		 			"and sub_linkname='"+linkName+"' and archived_status='0'";
		 	
		 	
		 	ResultSet rs3=ad.selectQuery(sql2);
		 	
		 	HRForm newsMediaForm1=null;
		 	
		 	ArrayList a2=new ArrayList();
		 	while (rs3.next()) {
		 		newsMediaForm1=new HRForm();
		 		
		 		
		 		newsMediaForm1.setLinkId(rs3.getString("id"));
		 		newsMediaForm1.setLinkTitle(rs3.getString("link_name"));
		 		newsMediaForm1.setImageName(rs3.getString("icon_name"));
		 		a2.add(newsMediaForm1);
			}
		 	
		 	
		 	session.setAttribute("SUBLINKS", finalLnkdList);
		 	request.setAttribute("subLinkDetails", a2);
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("MenuIcon", module);
		
		return mapping.findForward("displayCMS1");
	}
	
	
	public ActionForward displayCMS1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HRForm HRForm=(HRForm)form;	
		ITDao ad=new ITDao();
		
		
		String linkName="HR"; 
		
		String module="Main"; 		
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		
		try{
			String contentDescription="";
			String uplodedfiles="";
			String uplodedvideos="";
			
			String sql1="select * from archieves where link_name='"+linkName+"' and module='Main' and status='null' order by month desc";
			//String sql11="select * from links where link_name='"+linkName+"' and module='"+module+"' " +"and sub_linkname=link_name";
			
			ResultSet rs11=ad.selectQuery(sql1);
			
				while(rs11.next()) {
					contentDescription+=rs11.getString("content_description");
					if(!rs11.getString("file_name").equals(""))
					 uplodedfiles+=rs11.getString("file_name")+",";
					if(!rs11.getString("video_name").equals(""))
					 uplodedvideos+=rs11.getString("video_name")+",";
					HRForm.setContentDescription(contentDescription);
					HRForm.setFileFullPath(uplodedfiles);
					HRForm.setVideoFullPath(uplodedvideos);
				}
			
				ArrayList imageList=new ArrayList();
			
				HRForm.setLinkName(linkName);
			
				if(contentDescription.equalsIgnoreCase(""))
				{
					HRForm.setContentDescription("No Content is available");
					
				}
				
				if(uplodedfiles.equalsIgnoreCase(""))
				{
					HRForm.setFileFullPath("");
				}
				if(uplodedvideos.equalsIgnoreCase(""))
				{
					HRForm.setVideoFullPath("");
				}
		 	session.setAttribute("SUBLINKS", session.getAttribute("SUBLINKS"));
				
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		
		request.setAttribute("MenuIcon", module);
		request.setAttribute("linkname", "HR");
		
		return mapping.findForward("displayCMS1");
	}
	
	
	
	
	
	
	
	
	
}
