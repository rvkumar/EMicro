package com.microlabs.admin.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;    
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.form.VCApprForm;
import com.microlabs.admin.form.ESSApproverForm;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.utilities.UserInfo;

public class VCApprAction extends DispatchAction{
	
	public ActionForward updateRoomDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
	  String updateFloor="update VCRoom_List set status='"+masterForm.getStatus()+"' where LocId='"+masterForm.getLocationId()+"' and "
	  + "Floor='"+masterForm.getFloor()+"' and Room_Name='"+masterForm.getRoomName()+"'";
	  int i=ad.SqlExecuteUpdate(updateFloor);
	  if(i>0){
		  masterForm.setMessage("Data updated Successfully");
	  }
		
		
		return mapping.findForward("addnewconference");
	}
	
	
	public ActionForward editRoomDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		String location=request.getParameter("loc");
		String floor=request.getParameter("Floor");
		String room=request.getParameter("room");
		
		String getRoomDetails="select loc.LOCID,conf.Floor,conf.Room_Name,conf.Status from VCRoom_List conf,"
		+ "Location loc where conf.LocId=loc.LOCID and loc.LOCATION_CODE='"+location+"' and conf.Floor='"+floor+"' and conf.Room_Name='"+room+"'";
		ResultSet rsList=ad.selectQuery(getRoomDetails);
		try{
			while(rsList.next()){
			masterForm.setLocationId(rsList.getString("LOCID"));
			masterForm.setFloor(rsList.getString("Floor"));
			masterForm.setRoomName(rsList.getString("Room_Name"));
			masterForm.setStatus(rsList.getString("Status"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		masterForm.setAddroom("true");
		request.setAttribute("setRoom", "setRoom");
		request.setAttribute("modifyButton", "modifyButton");
		request.setAttribute("saveButton", "saveButton");
		
		request.setAttribute("setReqFloor", "setReqFloor");
		return mapping.findForward("addnewconference");
	}
	public ActionForward saveFloor(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
	   String saveType=request.getParameter("saveType");
	   if(saveType.equals("Floor")){
		 
		   int check=0;
		  String checkFloor="select count(*) from VCFloor_List where LocId='"+masterForm.getLocationId1()+"' and Floor='"+masterForm.getFloor1()+"'";
		  ResultSet rsCheck=ad.selectQuery(checkFloor);
		  try{
			 while(rsCheck.next()){
				 check=rsCheck.getInt(1);
			 }
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  if(check==0){ 
		  String saveFloor="insert into VCFloor_List(LocId,Floor) values('"+masterForm.getLocationId1()+"','"+masterForm.getFloor1()+"')";
		  int i=ad.SqlExecuteUpdate(saveFloor);
		  if(i>0){
			  masterForm.setMessage("Data Saved Successfully");
			  request.setAttribute("setFloor", "setFloor");
		  }
		  }else{
			  masterForm.setMessage2("Floor name already exist.Data Not Saved Successfully");
			  request.setAttribute("setFloor", "setFloor");
		  }
	   }else{
		   int check=0;
			  String checkFloor="select count(*) from VCRoom_List where LocId='"+masterForm.getLocationId()+"' and "
			  + "Floor='"+masterForm.getFloor()+"' and Room_Name='"+masterForm.getRoomName()+"'";
			  ResultSet rsCheck=ad.selectQuery(checkFloor);
			  try{
				 while(rsCheck.next()){
					 check=rsCheck.getInt(1);
				 }
			  }catch(Exception e){
				  e.printStackTrace();
			  }
			  if(check==0){ 
				  String saveFloor="insert into VCRoom_List(LocId,Floor,Room_Name,status) values('"+masterForm.getLocationId()+"',"
				  		+ "'"+masterForm.getFloor()+"','"+masterForm.getRoomName()+"','"+masterForm.getStatus()+"')";
				  
				  int i=ad.SqlExecuteUpdate(saveFloor);
				  if(i>0){
					  masterForm.setMessage("Data Saved Successfully");
					  request.setAttribute("setRoom", "setRoom");
				  }
				  }else{
					  masterForm.setMessage2("Room name already exist.Data Not Saved Successfully");
					  request.setAttribute("setRoom", "setRoom");
				  }
			  
	   }
		
		return mapping.findForward("addnewconference");
		
	}
	
	public ActionForward addnewconference(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
		String Status=request.getParameter("status");
		
		ResultSet rs11 = ad.selectQuery("select * from location");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
	
		
		
		
		
		return mapping.findForward("addnewconference");
	}
	
	public ActionForward manageConfroom(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		EssDao ad=new EssDao();
		String conf="Select * From VCRoom_List ";
		ResultSet rs=ad.selectQuery(conf);
		ArrayList conflist=new ArrayList();
	try {
		while(rs.next())
		{
			VCApprForm data=new VCApprForm();
			
			   String Locame="";
			     
			     String loca="Select * from Location where LOCID='"+rs.getString("LocId")+"'";
			     ResultSet rs0=ad.selectQuery(loca);
			     
					try {
						if(rs0.next())
						 {
							Locame=rs0.getString("LOCATION_CODE");
		           
						 }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			
			data.setLocationId(Locame);			
			data.setFloor(rs.getString("Floor"));			
			data.setRoomName(rs.getString("Room_Name"));
			data.setStatus(rs.getString("Status"));
			conflist.add(data);
			
		}
		request.setAttribute("conflist", conflist);
		
		
	} catch (SQLException e) {
	
		e.printStackTrace();
	}
		
		
		return mapping.findForward("confroomList");
	}
	
	public ActionForward deleteApprs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
	     String floor=request.getParameter("floor");
	     String room=request.getParameter("roomName");
	     String loc=request.getParameter("loc");
	     
        String LocId="";
	     
	     String loca="Select * from Location where LOCATION_CODE='"+loc+"'";
	     ResultSet rs0=ad.selectQuery(loca);
	     
			try {
				if(rs0.next())
				 {
           LocId=rs0.getString("LOCID");
					 
				 }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	     
	     String delete="delete VCRoom_Approvers where LocID='"+LocId+"' and Floor='"+floor+"' and Room='"+room+"'";
		   	int a=ad.SqlExecuteUpdate(delete);
	     
			if(a>0)
		   	{
				masterForm.setMessage("Data Deleted Successfully");
		   	}
			else
			{
				masterForm.setMessage2("Error..Data Not Deleted");
			}
	     
			approversList(mapping, masterForm, request, response);
			return mapping.findForward("approversList");
		
	}
	
	public ActionForward modifyApprs(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		 EssDao ad=new EssDao();
		 VCApprForm masterForm=(VCApprForm)form;
	     String floor=masterForm.getFloor();
	     String room=masterForm.getRoomName();
		 String approver1=masterForm.getApprover1();
		 String approver2=masterForm.getApprover2();
		 String approver3=masterForm.getApprover3();		  
	     String loc=masterForm.getLocationId();
	    
		
	   	String delete="delete VCRoom_Approvers where LocID='"+loc+"' and Floor='"+floor+"' and Room='"+room+"'";
	   	int a=ad.SqlExecuteUpdate(delete);
		
	   	if(a>0)
	   	{
	   		int i=0;
			 if(!(approver1.equalsIgnoreCase("")))
			  {
				  String saveEssApprovers="insert into VCRoom_Approvers(LocID,Floor,Room,Approver_Id,Priority)" +
				  " values('"+loc+"','"+floor+"','"+room+"','"+approver1+"','1')";	
				  i=ad.SqlExecuteUpdate(saveEssApprovers);
			  }	  
			  if(!(approver2.equalsIgnoreCase("")))
			  {
				  String saveEssApprovers="insert into VCRoom_Approvers(LocID,Floor,Room,Approver_Id,Priority)" +
						  " values('"+loc+"','"+floor+"','"+room+"','"+approver2+"','2')";	
						  i=ad.SqlExecuteUpdate(saveEssApprovers);
			  }
			  if(!(approver3.equalsIgnoreCase("")))
			  {
				  String saveEssApprovers="insert into VCRoom_Approvers(LocID,Floor,Room,Approver_Id,Priority)" +
						  " values('"+loc+"','"+floor+"','"+room+"','"+approver3+"','3')";	
						  i=ad.SqlExecuteUpdate(saveEssApprovers);
			  }	
			  	
				if(i>0)
				{
					masterForm.setMessage("Data Updated Successfully");
					
		     
				}else{
					masterForm.setMessage2("Error..Data Not Saved");
				}
				
	   	}
	   	
		return mapping.findForward("editApprovers");
	}
	
	public ActionForward editApprover(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
	     String floor=request.getParameter("floor");
	     String room=request.getParameter("roomName");
	     String loc=request.getParameter("loc");
	     String LocId="";
	     
	     String loca="Select * from Location where LOCATION_CODE='"+loc+"'";
	     ResultSet rs0=ad.selectQuery(loca);
	     
			try {
				if(rs0.next())
				 {
           LocId=rs0.getString("LOCID");
					 
				 }
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	     
	     masterForm.setFloor(floor);
	     masterForm.setRoomName(room);
         masterForm.setLocationId(LocId);
         masterForm.setLocationname(loc);
	     

	     String getappr="select * from VCRoom_Approvers where LocID='"+LocId+"' and Floor='"+floor+"' and Room='"+room+"' order by Priority ";
	     ResultSet rs=ad.selectQuery(getappr);
	     try {
			while(rs.next())
			 {
				
				int priority=rs.getInt("Priority");
				if(priority==1)
				masterForm.setApprover1(rs.getString("Approver_Id"));
				if(priority==2)
				masterForm.setApprover2(rs.getString("Approver_Id"));
				if(priority==3)
				masterForm.setApprover3(rs.getString("Approver_Id"));
				 
			 }
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		
		return mapping.findForward("editApprovers");
	}
	
	
	public ActionForward addApprovers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		EssDao ad=new EssDao();
		VCApprForm masterForm=(VCApprForm)form;
	     String floor=request.getParameter("floor");
	     String room=request.getParameter("roomName");
		 String approver1=masterForm.getApprover1();
		 String approver2=masterForm.getApprover2();
		 String approver3=masterForm.getApprover3();
		
		 
		 String loc=masterForm.getLocationId();
		 
		 int i=0;
		 int count=0;
		 
		 
		 //chk approvers already present 
		 
			String checkAppr="select count(*) from VCRoom_Approvers  where LocID ='"+loc+"' and Floor='"+floor+"' and Room='"+room+"'";
			ResultSet rsCheckAppr=ad.selectQuery(checkAppr);
			
				try {
					while(rsCheckAppr.next()){
						count=rsCheckAppr.getInt(1);
					}
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				} 
				
				if(count==0)
		 
				{
		 if(!(approver1.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into VCRoom_Approvers(LocID,Floor,Room,Approver_Id,Priority)" +
			  " values('"+loc+"','"+floor+"','"+room+"','"+approver1+"','1')";	
			  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }	  
		  if(!(approver2.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into VCRoom_Approvers(LocID,Floor,Room,Approver_Id,Priority)" +
					  " values('"+loc+"','"+floor+"','"+room+"','"+approver2+"','2')";	
					  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }
		  if(!(approver3.equalsIgnoreCase("")))
		  {
			  String saveEssApprovers="insert into VCRoom_Approvers(LocID,Floor,Room,Approver_Id,Priority)" +
					  " values('"+loc+"','"+floor+"','"+room+"','"+approver3+"','3')";	
					  i=ad.SqlExecuteUpdate(saveEssApprovers);
		  }	
		  
		
		
				
				
			if(i>0)
			{
				masterForm.setMessage("Data Saved Successfully");
				
	            masterForm.setLocationId("");
	            masterForm.setFloor("");
	            masterForm.setRoomName("");
	            masterForm.setApprover1("");
	            masterForm.setApprover2("");
	            masterForm.setApprover3("");
	           
	     
			}else{                   
				masterForm.setMessage("Error..Data Not Saved");
			}
			
			
				}
				else{
					masterForm.setMessage("Approvers already Assigned For This  Selection");
				}
			ResultSet rs11 = ad.selectQuery("select * from location ");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			try {
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
		
	
			
			
			request.setAttribute("saveButton", "saveButton");
			return mapping.findForward("newApprovers");
		
	}
	public ActionForward newApprover(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCApprForm apprForm=(VCApprForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		EssDao ad=new EssDao();
		ResultSet rs11 = ad.selectQuery("select * from location order by LOCATION_CODE");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try {
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
			}
			
			apprForm.setLocationIdList(locationList);
			apprForm.setLocationLabelList(locationLabelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("saveButton", "saveButton");
		return mapping.findForward("newApprovers");
	}
	public ActionForward getRoomsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		VCApprForm help = (VCApprForm) form;
		EssDao ad=new EssDao();
		// TODO Auto-generated method stub
		String locID = request.getParameter("locID");	
		String floorID=request.getParameter("floor");
		ArrayList roomsList = new ArrayList();
		try {
			String getFloor="select Room_Name from VCRoom_List where LocId='"+locID+"' and Floor='"+floorID+"'  and status='yes'";
			ResultSet rsFloor=ad.selectQuery(getFloor);
			while(rsFloor.next()){
				roomsList.add(rsFloor.getString("Room_Name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("roomsList", roomsList);
		return mapping.findForward("roomAjax");
	
	}
	
	public ActionForward getFloorList1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		VCApprForm help = (VCApprForm) form;
		EssDao ad=new EssDao();
		// TODO Auto-generated method stub
		String locID = request.getParameter("locID");		
		ArrayList floorList = new ArrayList();
		try {
			String getFloor="select Distinct(Floor) from VCFloor_List where LocId='"+locID+"' ";
			ResultSet rsFloor=ad.selectQuery(getFloor);
			while(rsFloor.next()){
				floorList.add(rsFloor.getString("Floor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("floorList", floorList);
		//displayMainLinks(mapping, form, request, response);
		return mapping.findForward("floorajax");
	
	}
	
	public ActionForward getFloorList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		VCApprForm help = (VCApprForm) form;
		EssDao ad=new EssDao();
		// TODO Auto-generated method stub
		String locID = request.getParameter("locID");		
		ArrayList floorList = new ArrayList();
		try {
			String getFloor="select Distinct(Floor) from VCRoom_List where LocId='"+locID+"' and status='yes'";
			ResultSet rsFloor=ad.selectQuery(getFloor);
			while(rsFloor.next()){
				floorList.add(rsFloor.getString("Floor"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("floorList", floorList);
		//displayMainLinks(mapping, form, request, response);
		return mapping.findForward("floorajax");
	
	}
	public ActionForward searchForApprovers(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) {
	HttpSession session=request.getSession();
	UserInfo user=(UserInfo)session.getAttribute("user");
	VCApprForm masterForm=(VCApprForm)form;
	EssDao ad=new EssDao();
	String sTxt = request.getParameter("searchText");
	String reqFieldName=request.getParameter("reqFieldName");
	
	String Loc=request.getParameter("loc");
	
	masterForm.setSearchText(sTxt);
	MailInboxAction mAction = new MailInboxAction();
	LinkedList searchList=new LinkedList();
	try{
		String searchQuery="select * from emp_official_info as emp,Location as loc where (EMP_FULLNAME like '%"+sTxt+"%' or  EMAIL_ID like '%"+sTxt+"%' or PERNR like '%"+sTxt+"%') and loc.LOCATION_CODE=emp.LOCID and loc.LOCID="+Loc+"";
		ResultSet rs=ad.selectQuery(searchQuery);
	 int i=1;
		while(rs.next()) {
			
			ESSApproverForm emp=new ESSApproverForm();
			emp.setEmp(rs.getString("EMP_FULLNAME")+"-"+rs.getString("PERNR"));
			emp.setReqFieldName(reqFieldName);
		   searchList.add(emp);
		  if(i==4)
		  {
			  break;
		  }
		i++;
		}
		
	}
	
	catch (SQLException sqle) { System.out.println("SQLException @ searching User Details"); sqle.printStackTrace();}
	
	request.setAttribute("SearchUserDetails", searchList);
	
	return mapping.findForward("searchITApprovers");
	}
	public ActionForward approversList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		VCApprForm masterForm=(VCApprForm)form;
		HttpSession sesion=request.getSession();
		UserInfo user=(UserInfo)sesion.getAttribute("user");
		EssDao ad=new EssDao();
		LinkedList 	appr=new LinkedList();
			String getApprovers="select loc.LOCATION_CODE,conf.Floor,conf.room,count(*) as total from VCRoom_Approvers conf,Location loc "
					+ " where conf.LocID=loc.LOCID group by loc.LOCATION_CODE,conf.Floor,conf.Room";
		ResultSet rs=ad.selectQuery(getApprovers);
		try {
			while(rs.next()){
				VCApprForm masterForm1=new VCApprForm();
				masterForm1.setLocationId(rs.getString("LOCATION_CODE"));
				masterForm1.setFloor(rs.getString("Floor"));
				masterForm1.setRoomName(rs.getString("room"));
				masterForm1.setTotalRecords(rs.getInt("total"));
				appr.add(masterForm1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 request.setAttribute("Approvers", appr);
		 
			ResultSet rs11 = ad.selectQuery("select * from location");
			ArrayList locationList=new ArrayList();
			ArrayList locationLabelList=new ArrayList();
			try {
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCATION_CODE"));
					locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			masterForm.setLocationIdList(locationList);
			masterForm.setLocationLabelList(locationLabelList);
		return mapping.findForward("approversList");
	}
}
