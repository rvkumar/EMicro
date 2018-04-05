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
import com.microlabs.it.form.ActiveDirectoryForm;

public class ActiveDirectoryAction extends DispatchAction {
	
	public ActionForward newactiveDirectoryForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("newactiveDirectoryForm()----");
		ActiveDirectoryForm adForm1=(ActiveDirectoryForm)form;
		adForm1.setEmployeeNo("");
		adForm1.setFirstName("");
        adForm1.setLastName("");
        adForm1.setDesignation("");
        adForm1.setDepartment("");
        adForm1.setLocation("");
        adForm1.setContactDetails("");
        adForm1.setAssetDetails("");
        adForm1.setHostName("");
        adForm1.setIPNumber("");
        adForm1.setAdLoginName("");
        adForm1.setRequiredFolderAccess("");
        
		return mapping.findForward("newactiveDirectoryForm");
	}

	public ActionForward displaySavedADDraftForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try{
			String reqADId=request.getParameter("reqADId");
			ActiveDirectoryForm adForm=(ActiveDirectoryForm)form;
			ITDao itdao=new ITDao();
		String getReqQuery="select * from ad_user_req where Req_AD_Id='"+reqADId+"' ";
		System.out.println("getReqQuery="+getReqQuery);
		ResultSet rs=itdao.selectQuery(getReqQuery);	
		while (rs.next()) {
		
			adForm.setEmployeeNo(rs.getString("Employee_Number"));			
			adForm.setReqADId(rs.getString("Req_AD_Id"));
			adForm.setFirstName(rs.getString("First_Name"));
			adForm.setLastName(rs.getString("Last_Name"));
			adForm.setDesignation(rs.getString("Designation"));
			adForm.setDepartment(rs.getString("Department"));
			adForm.setLocation(rs.getString("Location"));
			adForm.setContactDetails(rs.getString("Contact_Details"));
			adForm.setAssetDetails(rs.getString("Asset_Details"));
			adForm.setHostName(rs.getString("Host_Name"));
			adForm.setIPNumber(rs.getString("IP_Number"));
			adForm.setReqADId(rs.getString("Req_AD_Id"));
			adForm.setAdLoginName(rs.getString("AD_Login_Name"));
			adForm.setRequiredFolderAccess(rs.getString("Required_Folder_Access"));
		}
		}
		catch (Exception e) {
					
				}		
		
		return mapping.findForward("displayupdateADForm");

	}
	
	public ActionForward updateADRequestAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("updateADRequestAction()----");
		ActiveDirectoryForm adForm=(ActiveDirectoryForm)form;
		ITDao itdao=new ITDao();
		
		String savedType=request.getParameter("savedType");
		System.out.println("savedType="+savedType);
		String updateADReqID="update ad_user_req set `Employee_Number`='"+adForm.getEmployeeNo()+"',`First_Name`='"+adForm.getFirstName()+"',`Last_Name`='"+adForm.getLastName()+"',`Designation`='"+adForm.getDesignation()+"'," + 
		"`Department`='"+adForm.getDepartment()+"',`Location`='"+adForm.getLocation()+"',`Contact_Details`='"+adForm.getContactDetails()+"',`Asset_Details`='"+adForm.getAssetDetails()+"',`Host_Name`='"+adForm.getHostName()+"',`IP_Number`='"+adForm.getIPNumber()+"',`AD_Login_Name`='"+adForm.getAdLoginName()+"',`Required_Folder_Access`='"+adForm.getRequiredFolderAccess()+"',`Storage_Type`='Inbox'" +
				" where`Req_AD_Id`='"+adForm.getReqADId()+"'";
		System.out.println("updateADReqID="+updateADReqID);
		int i=0;
		i=itdao.SqlExecuteUpdate(updateADReqID);
		HttpSession session=request.getSession();
		if(i>0){
			session.setAttribute("status", "Request Updated Successfully");
		System.out.println("Request Added Successfully");
		}
		else
		{
			session.setAttribute("status", "Request Not Updated.Please Check...");
			System.out.println("values are not Submited");	
		}
		return mapping.findForward("displayupdateADForm");

	}
	
	public ActionForward displayRequriedADForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("displayRequriedADForm()----");
		ActiveDirectoryForm adForm=(ActiveDirectoryForm)form;
		
		String req=adForm.getChangeReqForm();
		System.out.println("selceted="+req);
		
		if(req=="Select Drafts"||req.equalsIgnoreCase("Select Drafts")){
			System.out.println("i am in if");
			
			return mapping.findForward("newactiveDirectoryForm");
		}
		
		ArrayList adReqList=new ArrayList();
		try{
			ITDao itdao=new ITDao();
		String getReqQuery="select * from ad_user_req where Storage_Type='"+adForm.getChangeReqForm()+"'";
		ResultSet rs=itdao.selectQuery(getReqQuery);	
		while (rs.next()) {
			ActiveDirectoryForm adForm2=new ActiveDirectoryForm();
			adForm2.setReqADId(rs.getString("Req_AD_Id"));			
			adForm2.setEmployeeNo(rs.getString("Employee_Number"));
			adForm2.setAdLoginName(rs.getString("AD_Login_Name"));
			adForm2.setRequiredFolderAccess(rs.getString("Required_Folder_Access"));
			adForm2.setChangeReqForm(rs.getString("Storage_Type"));
			adReqList.add(adForm2);
		}
		}
		catch (Exception e) {
					
				}
		HttpSession session=request.getSession();
		session.setAttribute("adReqList", adReqList);
		
		
		return mapping.findForward("reqADForm");
	}
	
	public ActionForward saveADRequestAction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		System.out.println("saveADRequestAction()----");
		ActiveDirectoryForm adForm=(ActiveDirectoryForm)form;
		ITDao itdao=new ITDao();
		
		String savedType=request.getParameter("savedType");
		System.out.println("savedType="+savedType);
		
		String saveADvalQuery="INSERT INTO `ad_user_req` (`Employee_Number`,`First_Name`,`Last_Name`,`Designation`," + 
		"`Department`,`Location`,`Contact_Details`,`Asset_Details`,`Host_Name`,`IP_Number`,`AD_Login_Name`,`Required_Folder_Access`,`Storage_Type`,`Req_AD_Id`) " +
		"VALUES  ('"+adForm.getEmployeeNo()+"','"+adForm.getFirstName()+"','"+adForm.getLastName()+"','"+adForm.getDesignation()+"','"+adForm.getDepartment()+"','"+adForm.getLocation()+"','"+adForm.getContactDetails()+"'," +
		"'"+adForm.getAssetDetails()+"','"+adForm.getHostName()+"','"+adForm.getIPNumber()+"','"+adForm.getAdLoginName()+"','"+adForm.getRequiredFolderAccess()+"','"+savedType+"','"+adForm.getReqADId()+"')";
		System.out.println("saveADvalQuery="+saveADvalQuery);
		int i=0;
		i=itdao.SqlExecuteUpdate(saveADvalQuery);
		HttpSession session=request.getSession();
		if(i>0){
			session.setAttribute("status", "Request Submited Successfully");
		System.out.println("Request Added Successfully");
		}
		else
		{
			session.setAttribute("status", "Request Not Submited.Please Check...");
			System.out.println("values are not Submited");	
		}

		
		return mapping.findForward("newactiveDirectoryForm");
	}

}
