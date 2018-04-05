package com.microlabs.it.action;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.it.Dao.ITDao;
import com.microlabs.it.form.ServiceLevelAgreementForm;




public class ServiceLevelAgreementAction extends DispatchAction{
	
	
	public ActionForward modifyServiceLevel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceLevelAgreementForm serAgrmForm=(ServiceLevelAgreementForm)form;
		System.out.println("modifyServiceLevel()----");
		try{
		String slaName=request.getParameter("slaName");
		ITDao itdao=new ITDao();
		String getServiceQuery="select * from serviceagremlevel where serviceAgrement='"+slaName+"'";
		ResultSet rs=itdao.selectQuery(getServiceQuery);
		while(rs.next()){
			serAgrmForm.setSlaName(rs.getString("serviceAgrement"));
			serAgrmForm.setDescription(rs.getString("description"));
			serAgrmForm.setCriteria(rs.getString("criteria"));
			serAgrmForm.setCriteriaType(rs.getString("criteriaType"));
			serAgrmForm.setRespondedDays(rs.getString("respondedDays"));
			serAgrmForm.setRespondedHours(rs.getString("respondedHours"));
			serAgrmForm.setRespondedMins(rs.getString("respondedMins"));
			serAgrmForm.setResolvedDays(rs.getString("resolvedDays"));
			serAgrmForm.setResolvedHours(rs.getString("resolvedHours"));
			serAgrmForm.setResolvedMins(rs.getString("resolvedMins"));
			serAgrmForm.setEnableLevel1Esc1(rs.getString("enableLevel1Escl"));
			serAgrmForm.setEscalateTo1(rs.getString("escalateTo1"));
			serAgrmForm.setEscalateTime1(rs.getString("escalateTime1"));
			serAgrmForm.setEscDay1(rs.getString("escDay1"));
			serAgrmForm.setEscTime1(rs.getString("escTime1"));
			serAgrmForm.setEscMin1(rs.getString("escMin1"));
			
			serAgrmForm.setEnableLevel1Esc2(rs.getString("enableLevel1Esc2"));
			serAgrmForm.setEscalateTo2(rs.getString("escalateTo2"));
			serAgrmForm.setEscalateTime2(rs.getString("escalateTime2"));
			serAgrmForm.setEscDay2(rs.getString("escDay2"));
			serAgrmForm.setEscTime2(rs.getString("escTime2"));
			serAgrmForm.setEscMin2(rs.getString("escMin2"));
			
			serAgrmForm.setEnableLevel1Esc3(rs.getString("enableLevel1Esc3"));
			serAgrmForm.setEscalateTo3(rs.getString("escalateTo3"));
			serAgrmForm.setEscalateTime3(rs.getString("escalateTime3"));
			serAgrmForm.setEscDay3(rs.getString("escDay3"));
			serAgrmForm.setEscTime3(rs.getString("escTime3"));
			serAgrmForm.setEscMin3(rs.getString("escMin3"));
			
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return mapping.findForward("newServiceLevelAgremForm");
	
	}
	public ActionForward displayServiceLevels(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
		ServiceLevelAgreementForm serAgrmForm=(ServiceLevelAgreementForm)form;
		System.out.println("displayServiceLevels()----");
		ITDao itdao=new ITDao();
		ArrayList serviceList=new ArrayList();
		
		String getServiceQuery="select * from serviceagremlevel";
		
		ResultSet rs=itdao.selectQuery(getServiceQuery);
		while(rs.next()){
			ServiceLevelAgreementForm serAgr=new ServiceLevelAgreementForm();
			serAgr.setSlaName(rs.getString("serviceAgrement"));
			String resolutionTime=rs.getString("resolvedDays")+"Days "+rs.getString("resolvedHours")+"Hrs "+rs.getString("resolvedMins")+"Mins";
			System.out.println("resolutionTime="+resolutionTime);
			
			serAgr.setResolvedHours(resolutionTime);
			String responseTime=rs.getString("respondedDays")+"Days "+rs.getString("respondedDays")+"Hrs "+rs.getString("respondedMins")+"Mins";
			System.out.println("Response Time="+responseTime);
			serAgr.setRespondedHours(responseTime);
			serviceList.add(serAgr);
			
			
		}
		HttpSession session=request.getSession();
		session.setAttribute("serviceList", serviceList);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return mapping.findForward("allServiceLevels");
	}
	
	public ActionForward newServiceLevelAgrementForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceLevelAgreementForm serAgrmForm=(ServiceLevelAgreementForm)form;
		System.out.println("newServiceLevelAgrementForm()----");
		serAgrmForm.setSlaName("");
		serAgrmForm.setDescription("");
		serAgrmForm.setCriteria("");
		serAgrmForm.setCriteriaType("");
		serAgrmForm.setRespondedDays("");
		serAgrmForm.setRespondedHours("");
		serAgrmForm.setRespondedMins("");
		serAgrmForm.setResolvedDays("");
		serAgrmForm.setResolvedHours("");
		serAgrmForm.setResolvedMins("");
		serAgrmForm.setEnableLevel1Esc1("");
		serAgrmForm.setEscalateTo1("");
		serAgrmForm.setEscalateTime1("");
		serAgrmForm.setEscDay1("");
		serAgrmForm.setEscTime1("");
		serAgrmForm.setEscMin1("");
		
		serAgrmForm.setEnableLevel1Esc2("");
		serAgrmForm.setEscalateTo2("");
		serAgrmForm.setEscalateTime2("");
		serAgrmForm.setEscDay2("");
		serAgrmForm.setEscTime2("");
		serAgrmForm.setEscMin2("");
		
		serAgrmForm.setEnableLevel1Esc3("");
		serAgrmForm.setEscalateTo3("");
		serAgrmForm.setEscalateTime3("");
		serAgrmForm.setEscDay3("");
		serAgrmForm.setEscTime3("");
		serAgrmForm.setEscMin3("");
		return mapping.findForward("newServiceLevelAgremForm");
	}
	
	public ActionForward saveServiceLevelAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ServiceLevelAgreementForm serAgrmForm=(ServiceLevelAgreementForm)form;
		System.out.println("saveServiceLevelAgreement()----");
		ITDao itdao=new ITDao();
	String saveServiceLevelQuery="INSERT INTO `serviceagremlevel` (`serviceAgrement`,`description`," +
			"`criteria`,`criteriaType`,`enableLevel1Escl`,`escalateTo1`,`escalateTime1`,`escDay1`,`escTime1`," +
			"`escMin1`,`enableLevel1Esc2`,`escalateTo2`,`escDay2`,`escalateTime2`,`escTime2`,`escMin2`," +
			"`enableLevel1Esc3`,`escalateTo3`,`escDay3`,`escalateTime3`,`escTime3`,`escMin3`,`respondedDays`,`respondedHours`,`respondedMins`,`resolvedDays`,`resolvedHours`,`resolvedMins`)" +
			" VALUES ('"+serAgrmForm.getSlaName()+"','"+serAgrmForm.getDescription()+"','"+serAgrmForm.getCriteria()+"','"+serAgrmForm.getCriteriaType()+"'," +
			"'"+serAgrmForm.getEnableLevel1Esc1()+"','"+serAgrmForm.getEscalateTo1()+"','"+serAgrmForm.getEscalateTime1()+"','"+serAgrmForm.getEscDay1()+"','"+serAgrmForm.getEscTime1()+"'," +
			"'"+serAgrmForm.getEscMin1()+"','"+serAgrmForm.getEnableLevel1Esc2()+"','"+serAgrmForm.getEscalateTo2()+"','"+serAgrmForm.getEscDay2()+"'," +
			"'"+serAgrmForm.getEscalateTime2()+"','"+serAgrmForm.getEscTime2()+"','"+serAgrmForm.getEscMin2()+"'" +
			",'"+serAgrmForm.getEnableLevel1Esc3()+"','"+serAgrmForm.getEscalateTo3()+"','"+serAgrmForm.getEscDay3()+"','"+serAgrmForm.getEscalateTime3()+"','"+serAgrmForm.getEscTime3()+"','"+serAgrmForm.getEscMin1()+"'" +
					",'"+serAgrmForm.getRespondedDays()+"','"+serAgrmForm.getRespondedHours()+"','"+serAgrmForm.getRespondedMins()+"','"+serAgrmForm.getResolvedDays()+"','"+serAgrmForm.getResolvedHours()+"','"+serAgrmForm.getResolvedMins()+"')";
    System.out.println("saveServiceLevelQuery="+saveServiceLevelQuery);    
	int i=0;
        i=itdao.SqlExecuteUpdate(saveServiceLevelQuery);
        System.out.println("i="+i);
        serAgrmForm.setEnableLevel1Esc1("");
        serAgrmForm.setEnableLevel1Esc2("");
        serAgrmForm.setEnableLevel1Esc3("");

		return mapping.findForward("newServiceLevelAgremForm");
	}
	
	
	
	
}
