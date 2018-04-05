package com.microlabs.ess.action;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.ApprovalLevelsForm;
import com.microlabs.utilities.UserInfo;

public class ApprovalLevelsAction extends DispatchAction{

	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ApprovalLevelsForm approvalLevelsForm=(ApprovalLevelsForm)form;
		
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward displayEmpDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ApprovalLevelsForm approvalLevelsForm=(ApprovalLevelsForm)form;
		
		
		String param=request.getParameter("param");
		
		
		approvalLevelsForm.setApproverNumber(param);
		
		
		return mapping.findForward("displayEmplist");
	}
	
	
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ApprovalLevelsForm approvalLevelsForm=(ApprovalLevelsForm)form;
		
		
		String approver1=approvalLevelsForm.getApprover1();
		String approver2=approvalLevelsForm.getApprover2();
		String approver3=approvalLevelsForm.getApprover3();
		String approver4=approvalLevelsForm.getApprover4();
		String approver5=approvalLevelsForm.getApprover5();
		System.out.println("chechk===="+approver2);
		
		String approverNumber1=approvalLevelsForm.getApproverNumber1();
		String designationNumber1=approvalLevelsForm.getDesignationNumber1();
		String approverNumber2=approvalLevelsForm.getApproverNumber2();
		String designationNumber2=approvalLevelsForm.getDesignationNumber2();
		String approverNumber3=approvalLevelsForm.getApproverNumber3();
		String designationNumber3=approvalLevelsForm.getDesignationNumber3();
		String approverNumber4=approvalLevelsForm.getApproverNumber4();
		String designationNumber4=approvalLevelsForm.getDesignationNumber4();
		String approverNumber5=approvalLevelsForm.getApproverNumber5();
		String designationNumber5=approvalLevelsForm.getDesignationNumber5();
		
		
		String countryName=approvalLevelsForm.getCountryName();
		String state=approvalLevelsForm.getState();
		String plant=approvalLevelsForm.getPlant();
		String department=approvalLevelsForm.getDepartment();
		String type=approvalLevelsForm.getType();
		HttpSession session=request.getSession();
		
		UserInfo user=(UserInfo)session.getAttribute("user");
		int priority=0;
		EssDao ad=new EssDao();
		int a=0;
		if(!approver1.equalsIgnoreCase("")){
			String designation=null;
			String app_name=null;
			String query="select DSGSTXT from designation where DSGID='"+designationNumber1+"'";
		    ResultSet get_des=ad.selectQuery(query);
		    try{
		    if(get_des.next())
		    	designation=get_des.getString("DSGSTXT");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    String query_name="select fullname from users where id='"+approverNumber1+"'";
		    ResultSet get_name=ad.selectQuery(query_name);
		    try{
		    if(get_name.next())
		    	app_name=get_name.getString("fullname");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		   // String get_priority=null;
		    String priority_sql="select max(priority) from approvers_details where type='"+type+"'";
		    ResultSet prio_rs=ad.selectQuery(priority_sql);
		    String sql=null;
		    try{
		    if(prio_rs.next()){
		     
		    int p=prio_rs.getInt(1);
		    priority=p+1;
		sql="insert into approvers_details(approver_id,approver_designation," +
				"employee_name,priority,type,plant) values('"+approverNumber1+"','"+designation+"'," +
				"'"+app_name+"',"+
				"'"+priority+"'" +
				",'"+type+"','"+plant+"')";
		    }
		    else{
		    	priority=1;
		    	sql="insert into approvers_details(approver_id,approver_designation," +
				"employee_name,priority,type,plant) values('"+approverNumber1+"','"+designation+"'," +
				"'"+app_name+"',"+
				"'"+priority+"'" +
				",'"+type+"','"+plant+"')";
		    }
		System.out.println(sql);
		a= ad.SqlExecuteUpdate(sql);
	
		}
		   
		    catch(Exception e){
		    	
		    	e.printStackTrace();
		    }
		}
		if(!approver2.equalsIgnoreCase("")){
			String designation=null;
			String app_name=null;
			String query="select DSGSTXT from designation where DSGID='"+designationNumber2+"'";
		    ResultSet get_des=ad.selectQuery(query);
		    try{
		    if(get_des.next())
		    	designation=get_des.getString("DSGSTXT");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    String query_name="select fullname from users where id='"+approverNumber2+"'";
		    ResultSet get_name=ad.selectQuery(query_name);
		    try{
		    if(get_name.next())
		    	app_name=get_name.getString("fullname");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    priority++;
		String sql="insert into approvers_details(approver_id,approver_designation," +
				"employee_name,priority,type,plant) values('"+approverNumber2+"','"+designation+"'," +
				"'"+app_name+"',"+
				"'"+priority+"'" +
				",'"+type+"','"+plant+"')";
		System.out.println(sql);
		a= ad.SqlExecuteUpdate(sql);
			}
		if(!approver3.equalsIgnoreCase("")){
			String designation=null;
			String app_name=null;
			String query="select DSGSTXT from designation where DSGID='"+designationNumber3+"'";
		    ResultSet get_des=ad.selectQuery(query);
		    try{
		    if(get_des.next())
		    	designation=get_des.getString("DSGSTXT");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    String query_name="select fullname from users where id='"+approverNumber3+"'";
		    ResultSet get_name=ad.selectQuery(query_name);
		    try{
		    if(get_name.next())
		    	app_name=get_name.getString("fullname");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    priority++;
		String sql="insert into approvers_details(approver_id,approver_designation," +
				"employee_name,priority,type,plant) values('"+approverNumber3+"','"+designation+"'," +
				"'"+app_name+"',"+
				"'"+priority+"'" +
				",'"+type+"','"+plant+"')";
		System.out.println(sql);
		a= ad.SqlExecuteUpdate(sql);
			}
		if(!approver4.equalsIgnoreCase("")){
			String designation=null;
			String app_name=null;
			String query="select DSGSTXT from designation where DSGID='"+designationNumber4+"'";
		    ResultSet get_des=ad.selectQuery(query);
		    try{
		    if(get_des.next())
		    	designation=get_des.getString("DSGSTXT");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    String query_name="select fullname from users where id='"+approverNumber4+"'";
		    ResultSet get_name=ad.selectQuery(query_name);
		    try{
		    if(get_name.next())
		    	app_name=get_name.getString("fullname");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    priority++;
		String sql="insert into approvers_details(approver_id,approver_designation," +
				"employee_name,priority,type,plant) values('"+approverNumber4+"','"+designation+"'," +
				"'"+app_name+"',"+
				"'"+priority+"'" +
				",'"+type+"','"+plant+"')";
		System.out.println(sql);
		a= ad.SqlExecuteUpdate(sql);
			}
		if(!approver5.equalsIgnoreCase("")){
			String designation=null;
			String app_name=null;
			String query="select DSGSTXT from designation where DSGID='"+designationNumber5+"'";
		    ResultSet get_des=ad.selectQuery(query);
		    try{
		    if(get_des.next())
		    	designation=get_des.getString("DSGSTXT");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    String query_name="select fullname from users where id='"+approverNumber5+"'";
		    ResultSet get_name=ad.selectQuery(query_name);
		    try{
		    if(get_name.next())
		    	app_name=get_name.getString("fullname");
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    priority++;
		String sql="insert into approvers_details(approver_id,approver_designation," +
				"employee_name,priority,type,plant) values('"+approverNumber5+"','"+designation+"'," +
				"'"+app_name+"',"+
				"'"+priority+"'" +
				",'"+type+"','"+plant+"')";
		System.out.println(sql);
		a= ad.SqlExecuteUpdate(sql);
			}
		ArrayList appList=new ArrayList();
		
		if(a>0){
			approvalLevelsForm.setMessage("Approvals Levels Submitted Successfully");
			
			approvalLevelsForm.setCountryName("");
			approvalLevelsForm.setState("");
			approvalLevelsForm.setPlant("");
			approvalLevelsForm.setDepartment("");
			approvalLevelsForm.setApprover1("");
			approvalLevelsForm.setApprover2("");
			approvalLevelsForm.setApprover3("");
			approvalLevelsForm.setApprover4("");
			approvalLevelsForm.setApprover5("");
			approvalLevelsForm.setDesignation("");
			
			
			
		}else{
			approvalLevelsForm.setMessage("Error While Submitting Approvals Levels");
		}
		String sql="select approver_id,approver_designation,type,plant from approvers_details where type='"+type+"'";
		ResultSet rs=ad.selectQuery(sql);
		
		try{
		while(rs.next()){
			ApprovalLevelsForm af=new ApprovalLevelsForm();
			af.setApproverNumber(rs.getString("approver_id"));
			af.setDesignation(rs.getString("approver_designation"));
			af.setType(rs.getString("type"));
			af.setPlant(rs.getString("plant"));
			appList.add(af);
			
		}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("applist", appList);
		
		return mapping.findForward("display");
	}
	
	
	
	
	
}
