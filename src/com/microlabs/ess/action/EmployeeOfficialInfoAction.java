package com.microlabs.ess.action;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.EmployeeOfficialInfoForm;
import com.microlabs.hr.dao.HRDao;
import com.microlabs.hr.form.HRForm;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;


public class EmployeeOfficialInfoAction extends DispatchAction {
	EssDao ad=new EssDao();
	
	
	public ActionForward updateAsset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int reqId=empForm.getId();
		String receivedDt=empForm.getRecivedDt();
		String a[]=receivedDt.split("/");
		receivedDt=a[2]+"-"+a[1]+"-"+a[0];
		empForm.setMessage("");
		empForm.setMessage1("");
		try{
			String updateAssets="update Assets set assetType='"+empForm.getAssetType()+"',assetDesc='"+empForm.getAssetDesc()+"',"
			+ "serialNo='"+empForm.getSerialNo()+"',Insurence_renuewal='"+empForm.getInsurRenewalDt()+"',received_dt='"+receivedDt+"' "
			+ "where id='"+reqId+"'";
				int i=0;
		i=ad.SqlExecuteUpdate(updateAssets);
		if(i>0){
			empForm.setMessage("Asset details has been updated.");
			List assetList=new LinkedList();
			String getAssetList="select * from Assets where PERNR='"+user.getEmployeeNo()+"'";
			ResultSet rsList=ad.selectQuery(getAssetList);
			while(rsList.next()){
				EmployeeOfficialInfoForm e=new EmployeeOfficialInfoForm();
				e.setId(rsList.getInt("id"));
				e.setAssetType(rsList.getString("assetType"));
				e.setAssetDesc(rsList.getString("assetDesc"));
				e.setSerialNo(rsList.getString("serialNo"));
				e.setInsurRenewalDt(rsList.getString("Insurence_renuewal"));
				e.setRecivedDt(EMicroUtils.display(rsList.getDate("received_dt")));
				assetList.add(e);
			}
			request.setAttribute("assetList", assetList);
			empForm.setAssetType("");
			empForm.setAssetDesc("");
			empForm.setSerialNo("");
			empForm.setInsurRenewalDt("");
			empForm.setRecivedDt("");
		}else{
			empForm.setMessage("Error..While Saving Asset details .");
		}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayAssets");
	
	}
	public ActionForward editAsset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		empForm.setMessage("");
		empForm.setMessage1("");
		String reqID=request.getParameter("reqId");
		empForm.setId(Integer.parseInt(reqID));
		String getDetails="select * from Assets where PERNR='"+user.getEmployeeNo()+"' and id='"+reqID+"'";
		ResultSet rs=ad.selectQuery(getDetails);
		try{
		while(rs.next()){
			empForm.setAssetType(rs.getString("assetType"));
			empForm.setAssetDesc(rs.getString("assetDesc"));
			empForm.setSerialNo(rs.getString("serialNo"));
			empForm.setInsurRenewalDt(rs.getString("Insurence_renuewal"));
			empForm.setRecivedDt(EMicroUtils.display(rs.getDate("received_dt")));
		}
		request.setAttribute("update", "update");
		request.setAttribute("save", "save");
		}catch(Exception e){
         e.printStackTrace();			
		}
		
		return mapping.findForward("displayAssets");
	}
	
	public ActionForward deleteAsset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		empForm.setMessage("");
		empForm.setMessage1("");
		String checkedValues=request.getParameter("cValues");
		checkedValues = checkedValues.substring(0, checkedValues.length());
		String[] documentCheck=checkedValues.split(",");
	    try{
	    	int j=0;
			for(int i=0;i<documentCheck.length;i++)
			{
			String updateRentDt="delete from  Assets where  PERNR='"+user.getEmployeeNo()+"' and Id='"+documentCheck[i]+"' ";
		   j=ad.SqlExecuteUpdate(updateRentDt);
			}
		    if(j>0){
		    	empForm.setMessage("Asset details has been deleted");
		    	
		    }else{
		    	empForm.setMessage1("Error..While deleting asset details .");
		    }
	    	List assetList=new LinkedList();
	    	String getAssetList="select * from Assets where PERNR='"+user.getEmployeeNo()+"'";
			ResultSet rsList=ad.selectQuery(getAssetList);
			while(rsList.next()){
				EmployeeOfficialInfoForm e=new EmployeeOfficialInfoForm();
				e.setId(rsList.getInt("id"));
				e.setAssetType(rsList.getString("assetType"));
				e.setAssetDesc(rsList.getString("assetDesc"));
				e.setSerialNo(rsList.getString("serialNo"));
				e.setInsurRenewalDt(rsList.getString("Insurence_renuewal"));
				e.setRecivedDt(EMicroUtils.display(rsList.getDate("received_dt")));
				if(rsList.getString("Created_by")!=null)
				e.setCreatedDate(rsList.getString("Created_by"));
				else
					e.setCreatedDate("");	
				assetList.add(e);
			}
			if(assetList.size()>0)
			request.setAttribute("assetList", assetList);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		return mapping.findForward("displayAssets");
	}
	public ActionForward saveAssets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String receivedDt=empForm.getRecivedDt();
		String a[]=receivedDt.split("/");
		receivedDt=a[2]+"-"+a[1]+"-"+a[0];
		empForm.setMessage("");
		empForm.setMessage1("");
		try{
			String saveAssets="INSERT INTO Assets(assetType,assetDesc,serialNo,Insurence_renuewal,received_dt,PERNR)"
		+ " values('"+empForm.getAssetType()+"','"+empForm.getAssetDesc()+"','"+empForm.getSerialNo()+"','"+empForm.getInsurRenewalDt()+"','"+receivedDt+"','"+user.getEmployeeNo()+"')";
		int i=0;
		i=ad.SqlExecuteUpdate(saveAssets);
		if(i>0){
			empForm.setMessage("Asset details has been saved");
			List assetList=new LinkedList();
			String getAssetList="select * from Assets where PERNR='"+user.getEmployeeNo()+"'";
			ResultSet rsList=ad.selectQuery(getAssetList);
			while(rsList.next()){
				EmployeeOfficialInfoForm e=new EmployeeOfficialInfoForm();
				e.setId(rsList.getInt("id"));
				e.setAssetType(rsList.getString("assetType"));
				e.setAssetDesc(rsList.getString("assetDesc"));
				e.setSerialNo(rsList.getString("serialNo"));
				e.setInsurRenewalDt(rsList.getString("Insurence_renuewal"));
				e.setRecivedDt(EMicroUtils.display(rsList.getDate("received_dt")));
				if(rsList.getString("Created_by")!=null)
				e.setCreatedDate(rsList.getString("Created_by"));
				else
					e.setCreatedDate("");
				assetList.add(e);
			}
			request.setAttribute("assetList", assetList);
		}else{
			empForm.setMessage("Error..While Saving Asset details .");
		}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return mapping.findForward("displayAssets");
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
	public ActionForward viewHRAsset(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		EmployeeOfficialInfoForm hr=(EmployeeOfficialInfoForm)form;	
		HRDao ad=new HRDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String uniqeid=request.getParameter("reqId");
		
		
		
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
				if(v.getString("Prev_user")!=null)
				{
				if(!v.getString("Prev_user").equalsIgnoreCase(""))
				{
					String[] b=null;
					
					b=empDet(v.getString("Prev_user"));
					hr.setPrev_user_name(b[0]);
					hr.setPrev_user_dep(b[2]);
					hr.setPrev_user_des(b[3]);
				}
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
		return mapping.findForward("viewHRassetform");
		
	}
	public ActionForward displayAssets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		empForm.setMessage("");
		empForm.setMessage1("");
		empForm.setAssetType("");
		empForm.setAssetDesc("");
		empForm.setSerialNo("");
		empForm.setInsurRenewalDt("");
		empForm.setRecivedDt("");
	    try{
	    	List assetList=new LinkedList();
	    	String getAssetList="select * from Assets where PERNR='"+user.getEmployeeNo()+"' and created_by is null ";
			ResultSet rsList=ad.selectQuery(getAssetList);
			while(rsList.next()){
				EmployeeOfficialInfoForm e=new EmployeeOfficialInfoForm();
				e.setId(rsList.getInt("id"));
				e.setAssetType(rsList.getString("assetType"));
				e.setAssetDesc(rsList.getString("assetDesc"));
				e.setSerialNo(rsList.getString("serialNo"));
				e.setInsurRenewalDt(rsList.getString("Insurence_renuewal"));
				e.setRecivedDt(EMicroUtils.display(rsList.getDate("received_dt")));
				if(rsList.getString("Created_by")!=null)
				e.setCreatedDate(rsList.getString("Created_by"));
				else
					e.setCreatedDate("");
				assetList.add(e);
			}
			if(assetList.size()>0)
				request.setAttribute("assetList", assetList);
	    }catch(Exception e){
	    	e.printStackTrace();
	    	
	    }
	    
		ArrayList g=new ArrayList();
		String lis="select CONVERT(varchar(10),Created_date,103)+' '+ CONVERT(varchar(5),Created_date,108) as Created_date1,* from Assets where"
				+ " created_by='"+user.getEmployeeNo()+"' and PERNR='"+user.getEmployeeNo()+"' and delete_flag=0";
		ResultSet ff=ad.selectQuery(lis);
		try {
			while(ff.next())
			{
				HRForm hr=new HRForm();
				hr.setId(ff.getString("id"));
				hr.setAssetType(ff.getString("assetType"));
				hr.setAssetDesc(ff.getString("assetDesc"));				
				hr.setCreated_Date(ff.getString("Created_date1"));
				g.add(hr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("hrassets", g);
		
		
		
		 InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties");
	 	 Properties props = new Properties();
	 	 try {
			props.load(in);
			 in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	 	 String itamsdb=props.getProperty("ITAMS");
	 	 
	 	 
	 	 
		 	ArrayList itasset=new ArrayList();
			String lis1="select s1.storTXT as hddsize,s2.storTXT as ramsize,* from "+itamsdb+".dbo.NewITAssetDetails_Desktop left outer join "+itamsdb+".dbo.Storage_size s1 on s1.storID="+itamsdb+".dbo.NewITAssetDetails_Desktop.size_type"
					+ " left outer join "+itamsdb+".dbo.Storage_size s2 on s2.storID="+itamsdb+".dbo.NewITAssetDetails_Desktop.ram_size where empno='"+user.getEmployeeNo()+"' and NewITAssetDetails_Desktop.AssetState=1";
			ResultSet ff1=ad.selectQuery(lis1);
			try {
				while(ff1.next())
				{
					EmployeeOfficialInfoForm hr=new EmployeeOfficialInfoForm();
					
					hr.setAssetType(ff1.getString("asset_type"));
					hr.setAsset_No(ff1.getString("asset_no"));
					hr.setCategory(ff1.getString("category"));
					hr.setHost_Name(ff1.getString("host_name"));
					hr.setMake(ff1.getString("make"));
					hr.setModel(ff1.getString("model"));
					hr.setManufacturer(ff1.getString("manufacturer"));
					hr.setProcessor(ff1.getString("processor"));
					if(ff1.getString("ram")!=null)
					hr.setrAM(ff1.getString("ram")+" "+ff1.getString("ramsize"));
					if(ff1.getString("hdd")!=null)
					hr.sethDD(ff1.getString("hdd")+" "+ff1.getString("hddsize"));
				
					itasset.add(hr);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			request.setAttribute("ITassetList", itasset); 
		
		
		
		return mapping.findForward("displayAssets");
	}
	public ActionForward updatBankDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList details = new LinkedList();
		try{
			String updateInfo="update emp_official_info set BRANCH='"+empForm.getBranchName()+"',IFSC_CODE='"+empForm.getIfsCCode()+"',MICR_CODE='"+empForm.getMicrCode()+"' where PERNR='"+user.getEmployeeNo()+"'";
		int i=0;
		i=ad.SqlExecuteUpdate(updateInfo);
		if(i==1)
		{
			empForm.setMessage("Data is updated successfully");
		}else{
			empForm.setMessage1("Error....Data is not updated.");
		}
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		try{		
			String empCountry="";
			String empState="";
			String sql1="SELECT emp.EMP_FULLNAME,emp.PERNR,comp.BUTXT,emp.WERKS,p.Short_desc,c.STAFFCAT,c.CATLTEXT,emp.RPTMGR,emp.ROOM,emp.APPMGR,emp.FLOOR,emp.BUILDING," +
					"emp.DOJ,emp.DOC,emp.DOL,coun.LANDX,emp.LAND1,emp.state,st.BEZEI,loc.LOCATION_CODE,loc.LOCNAME,g.GRDTXT,desg.DSGSTXT,dept.DPTSTXT," +
					"emp.EMAIL_ID,emp.TEL_NO,emp.TEL_EXTENS,emp.IP_PHONE,emp.ESI,emp.ESINO,emp.PF,emp.PFNO,emp.PT,emp.IT,emp.PANNO,emp.BONUS,emp.LEAVES,emp.WAERS," +
					"pay.pay_method,emp.BACCTYP,emp.BACCNO,emp.IFSC_CODE,emp.MICR_CODE,emp.BOARD_LINE FROM emp_official_info as emp,Paygroup_Master as p," +
					"Category  as c,Country as coun, State as st,Department as dept, Designation as desg,Grade as g,PAYMODE as pay,Location as loc ," +
					"Company as comp  where emp.PERNR='"+user.getEmployeeNo()+"' and emp.STAFFCAT=c.STAFFCAT and emp.LAND1=coun.LAND1 and emp.STATE=st.ID " +
					"and dept.DPTID=emp.DPTID and desg.DSGID =emp.DSGID and g.GRDID=emp.GRDID and pay.pay_id=emp.payment_method  and  emp.BUKRS=comp.BUKRS " +
					"and p.PAYGROUP=emp.pay_group and emp.LOCID=loc.LOCATION_CODE ";
	 			ResultSet rs1=ad.selectQuery(sql1);
				while(rs1.next()){
					empForm.setFirstName(rs1.getString("EMP_FULLNAME"));
				empForm.setFirstName(user.getFirstName());
				empForm.setMiddleName(user.getMiddleName());
				empForm.setLastName(user.getLastName());
				empForm.setEmployeeNumber(rs1.getString("PERNR"));
				empForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
				empForm.setCompanyName(rs1.getString("BUTXT"));
				empForm.setPlant(rs1.getString("WERKS"));
				empForm.setPayGroup(rs1.getString("Short_desc"));
				empForm.setEmployeeCategory(rs1.getString("CATLTEXT"));
				String reportingMngr="";
				empForm.setReportingManger("");
				empForm.setRoom(rs1.getString("ROOM"));
				empForm.setIpPhone(rs1.getString("IP_PHONE"));
				String approverMgr="";
				String getApprMgrName="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where ess.employeeNumber='"+rs1.getString("PERNR")+"' " +
				"and emp.PERNR=ess.ApproverId  and ess.essType='Leave' and ess.Priority=1";
				ResultSet rsAppr=ad.selectQuery(getApprMgrName);
				while(rsAppr.next())
				{
					approverMgr=rsAppr.getString("EMP_FULLNAME");
				}
				empForm.setApprovalManger(approverMgr);
				empForm.setFloor(rs1.getString("FLOOR"));
				empForm.setBuilding(rs1.getString("BUILDING"));
				//empForm.setAddressTypeID(rs1.getString("ADRTYP"));
				empForm.setDateofJoining(EMicroUtils.display1(rs1.getDate("DOJ")));
				empForm.setDateofLeaving(EMicroUtils.display1(rs1.getDate("DOL")));
				empForm.setDateofConformation(EMicroUtils.display1(rs1.getDate("DOC")));
				empForm.setCounID(rs1.getString("LANDX"));
				empCountry=rs1.getString("LANDX");
				
				empState=rs1.getString("state");
				String getEmpState=" select * from State where LAND1='"+rs1.getString("LAND1")+"' AND BLAND='"+empState+"'";
				ResultSet rsEmpstate=ad.selectQuery(getEmpState);
				while(rsEmpstate.next()){
					
					empForm.setState(rsEmpstate.getString("BEZEI"));
				}
				empForm.setLocation(rs1.getString("LOCATION_CODE")+"-"+rs1.getString("LOCNAME"));
				empForm.setGradeID(rs1.getString("GRDTXT"));
				empForm.setDesignation(rs1.getString("DSGSTXT"));
				empForm.setDepartment(rs1.getString("DPTSTXT"));
				empForm.setEmailid(rs1.getString("EMAIL_ID"));
				empForm.setTelNo(rs1.getString("TEL_NO"));
				empForm.setExtnNo(rs1.getString("TEL_EXTENS"));
				String eligibleESI=rs1.getString("ESI");
				if(eligibleESI.equalsIgnoreCase("Y"));
				{
				eligibleESI="Yes";
				}
				if(eligibleESI.equalsIgnoreCase("N"));
				{
				eligibleESI="No";
				}
				empForm.setEligibleforESIDeduction(eligibleESI);
				empForm.setEsiNumber(rs1.getString("ESINO"));
				String eligiblePF=rs1.getString("PF");
				if(eligiblePF.equalsIgnoreCase("Y"));
				{
					eligiblePF="Yes";
				}
				if(eligiblePF.equalsIgnoreCase("N"));
				{
					eligiblePF="No";
				}
				empForm.setEligibleforPFDeduction(eligiblePF);
				empForm.setPfNumber(rs1.getString("PFNO"));
				String eligiblePT=rs1.getString("PT");
				if(eligiblePT.equalsIgnoreCase("Y"));
				{
					eligiblePT="Yes";
				}
				if(eligiblePT.equalsIgnoreCase("N"));
				{
					eligiblePT="No";
				}
				empForm.setEligibleforPTDeduction(eligiblePT);
				String eligibleIT=rs1.getString("IT");
				if(eligibleIT.equalsIgnoreCase("Y"));
				{
					eligibleIT="Yes";
				}
				if(eligibleIT.equalsIgnoreCase("N"));
				{
					eligibleIT="No";
				}
				empForm.setEligibleforITDeduction(eligibleIT);
				empForm.setPanNo(rs1.getString("PANNO"));
				String eligibleBonus=rs1.getString("BONUS");
				if(eligibleBonus.equalsIgnoreCase("Y"));
				{
					eligibleBonus="Yes";
				}
				if(eligibleBonus.equalsIgnoreCase("N"));
				{
					eligibleBonus="No";
				}
				empForm.setBonus(eligibleBonus);
				String eligibleLeaves=rs1.getString("LEAVES");
				if(eligibleLeaves.equalsIgnoreCase("Y"));
				{
					eligibleLeaves="Yes";
				}
				if(eligibleLeaves.equalsIgnoreCase("N"));
				{
					eligibleLeaves="No";
				}
				empForm.setLeaves(eligibleLeaves);
				empForm.setSalaryCurrency(rs1.getString("WAERS"));
				empForm.setPaymentMethod(rs1.getString("pay_method"));
				
				String accType=rs1.getString("BACCTYP");
				if(accType.equals("S"));
				{
					accType="Savings Account";
				}
				if(accType.equals("C"));
				{
					accType="Current Account";
				}
				empForm.setAccountType("");
				empForm.setAccountNumber(rs1.getString("BACCNO"));
				
				empForm.setIfsCCode(rs1.getString("IFSC_CODE"));
				empForm.setMicrCode(rs1.getString("MICR_CODE"));
				empForm.setBoardLine(rs1.getString("BOARD_LINE"));
				
				
				
			//	empForm.setDepartment(rs.getString("DPTID"));
			//	empForm.setDepartIDList("DPTID");}
				String getBankDetails="select BRANCH,IFSC_CODE,MICR_CODE from emp_official_info where PERNR='"+user.getEmployeeNo()+"' ";
				ResultSet rs=ad.selectQuery(getBankDetails);
				while(rs.next()){
					empForm.setBranchName(rs.getString("BRANCH"));
					empForm.setIfsCCode(rs.getString("IFSC_CODE"));
					empForm.setMicrCode(rs.getString("MICR_CODE"));
				}
				
				UserInfo userId=(UserInfo)session.getAttribute("user");
				userId.getId();
				int empPhotoCount=0;
				String checkImage="select count(*) from Employee_Photos where employeeNo='"+userId.getEmployeeNo()+"'";
				ResultSet rsCheck=ad.selectQuery(checkImage);
				while(rsCheck.next()){
					empPhotoCount=rsCheck.getInt(1);
				}
				if(empPhotoCount>0){
				String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
				ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
				while (rsEmpPhoto.next())
				{
					empForm.setPhotoImage(rsEmpPhoto.getString("file_name"));
				request.setAttribute("employeePhoto", "employeePhoto");	
				}
				}else{
					String gender="";
					String getGender="select SEX from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
					ResultSet rsGender=ad.selectQuery(getGender);
					while(rsGender.next()){
						gender=rsGender.getString("SEX");
					}
					if(gender.equalsIgnoreCase("M"))
					{
						empForm.setPhotoImage("male.png");
						request.setAttribute("employeePhoto", "employeePhoto");	
					}else{
						empForm.setPhotoImage("female.png");
						request.setAttribute("employeePhoto", "employeePhoto");	
					}
				}
			}
				String branchName="";
				String bankName="";
				String ifsCode="";
				String micrCode="";
				String getBankDetails1="select bank.BRANCH,bank.BNAME,e.IFSC_CODE,e.MICR_CODE from emp_official_info e,Bank as bank "
						+ "where PERNR='"+user.getEmployeeNo()+"' and  e.BANKID=bank.BANKID  ";
				ResultSet rsBankDetails1=ad.selectQuery(getBankDetails1);
				while(rsBankDetails1.next()){
					branchName=rsBankDetails1.getString("BRANCH");
					bankName=rsBankDetails1.getString("BNAME");
					ifsCode=rsBankDetails1.getString("IFSC_CODE");
					micrCode=rsBankDetails1.getString("MICR_CODE");
				}
				empForm.setBranchName(branchName);
				empForm.setBankName(bankName);
				empForm.setIfsCCode(ifsCode);
				empForm.setMicrCode(micrCode);
				String getLocation="select loc.LAND1 from emp_official_info emp,Location loc where emp.PERNR='"+user.getEmployeeNo()+"' and emp.LOCID=loc.LOCATION_CODE ";
				ResultSet rsLoc=ad.selectQuery(getLocation);
				while(rsLoc.next()){
					empForm.setPlace(rsLoc.getString("LAND1"));
				}
				UserInfo userId=(UserInfo)session.getAttribute("user");
				int empPhotoCount=0;
				String getBankDetails="select BRANCH,IFSC_CODE,MICR_CODE from emp_official_info where PERNR='"+user.getEmployeeNo()+"' ";
				ResultSet rs=ad.selectQuery(getBankDetails);
				while(rs.next()){
					empForm.setBranchName(rs.getString("BRANCH"));
					empForm.setIfsCCode(rs.getString("IFSC_CODE"));
					empForm.setMicrCode(rs.getString("MICR_CODE"));
				}
				String checkImage="select count(*) from Employee_Photos where employeeNo='"+userId.getEmployeeNo()+"'";
				ResultSet rsCheck=ad.selectQuery(checkImage);
				while(rsCheck.next()){
					empPhotoCount=rsCheck.getInt(1);
				}
				if(empPhotoCount>0){
				String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
				ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
				while (rsEmpPhoto.next())
				{
					empForm.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
				request.setAttribute("employeePhoto", "employeePhoto");	
				}
				}else{
					String gender="";
					String getGender="select SEX from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
					ResultSet rsGender=ad.selectQuery(getGender);
					while(rsGender.next()){
						gender=rsGender.getString("SEX");
					}
					if(gender.equalsIgnoreCase("M"))
					{
						empForm.setPhotoImage("male.png");
						request.setAttribute("employeePhoto", "employeePhoto");	
					}else{
						empForm.setPhotoImage("female.png");
						request.setAttribute("employeePhoto", "employeePhoto");	
					}
				}
				details.add(empForm);
			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("details", details);
		return mapping.findForward("displayOfficial");
	}
	
	
	
	public ActionForward updateOfficialInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		EmployeeOfficialInfoForm empForm = (EmployeeOfficialInfoForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		LinkedList details = new LinkedList();
		try{
			String updateInfo="update emp_official_info set TEL_NO='"+empForm.getReqTelNo()+"',TEL_EXTENS='"+empForm.getReqExtNo()+"'," +
		"IP_PHONE='"+empForm.getReqIPPhone()+"',BUILDING='"+empForm.getReqBuilding()+"',FLOOR='"+empForm.getReqFloor()+"',ROOM='"+empForm.getReqBlockNo()+"',BOARD_LINE='"+empForm.getReqboardLine()+"' where PERNR='"+user.getEmployeeNo()+"'";
		int i=0;
		i=ad.SqlExecuteUpdate(updateInfo);
		if(i==1)
		{
			empForm.setMessage("Data is updated successfully");
		}else{
			empForm.setMessage1("Error....Data is not updated.");
		}
		}catch (Exception e) {
			e.printStackTrace(); 
		}
		try{		
		String empCountry="";
		String empState="";
		String sql1="SELECT emp.EMP_FULLNAME,emp.PERNR,comp.BUTXT,emp.WERKS,p.Short_desc,c.STAFFCAT,c.CATLTEXT,emp.RPTMGR,emp.ROOM,emp.APPMGR,emp.FLOOR,emp.BUILDING," +
				"emp.DOJ,emp.DOC,emp.DOL,coun.LANDX,emp.LAND1,emp.state,st.BEZEI,loc.LOCATION_CODE,loc.LOCNAME,g.GRDTXT,desg.DSGSTXT,dept.DPTSTXT," +
				"emp.EMAIL_ID,emp.TEL_NO,emp.TEL_EXTENS,emp.IP_PHONE,emp.ESI,emp.ESINO,emp.PF,emp.PFNO,emp.PT,emp.IT,emp.PANNO,emp.BONUS,emp.LEAVES,emp.WAERS," +
				"pay.pay_method,emp.BACCTYP,emp.BACCNO,emp.IFSC_CODE,emp.MICR_CODE,emp.BOARD_LINE,emp.UANNO FROM emp_official_info as emp,Paygroup_Master as p," +
				"Category  as c,Country as coun, State as st,Department as dept, Designation as desg,Grade as g,PAYMODE as pay,Location as loc ," +
				"Company as comp  where emp.PERNR='"+user.getEmployeeNo()+"' and emp.STAFFCAT=c.STAFFCAT and emp.LAND1=coun.LAND1 and emp.STATE=st.ID " +
				"and dept.DPTID=emp.DPTID and desg.DSGID =emp.DSGID and g.GRDID=emp.GRDID and pay.pay_id=emp.payment_method  and  emp.BUKRS=comp.BUKRS " +
				"and p.PAYGROUP=emp.pay_group and emp.LOCID=loc.LOCATION_CODE ";
 			ResultSet rs1=ad.selectQuery(sql1);
			while(rs1.next()){
				empForm.setFirstName(rs1.getString("EMP_FULLNAME"));
			empForm.setFirstName(user.getFirstName());
			empForm.setMiddleName(user.getMiddleName());
			empForm.setLastName(user.getLastName());
			empForm.setEmployeeNumber(rs1.getString("PERNR"));
			empForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
			empForm.setCompanyName(rs1.getString("BUTXT"));
			empForm.setPlant(rs1.getString("WERKS"));
			empForm.setPayGroup(rs1.getString("Short_desc"));
			empForm.setEmployeeCategory(rs1.getString("CATLTEXT"));
			String reportingMngr="";
			empForm.setReportingManger("");
			empForm.setRoom(rs1.getString("ROOM"));
			empForm.setIpPhone(rs1.getString("IP_PHONE"));
			String approverMgr="";
			String getApprMgrName="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where ess.employeeNumber='"+rs1.getString("PERNR")+"' " +
			"and emp.PERNR=ess.ApproverId  and ess.essType='Leave' and ess.Priority=1";
			ResultSet rsAppr=ad.selectQuery(getApprMgrName);
			while(rsAppr.next())
			{
				approverMgr=rsAppr.getString("EMP_FULLNAME");
			}
			empForm.setApprovalManger(approverMgr);
			empForm.setFloor(rs1.getString("FLOOR"));
			empForm.setBuilding(rs1.getString("BUILDING"));
			//empForm.setAddressTypeID(rs1.getString("ADRTYP"));
			empForm.setDateofJoining(EMicroUtils.display1(rs1.getDate("DOJ")));
			empForm.setDateofLeaving(EMicroUtils.display1(rs1.getDate("DOL")));
			empForm.setDateofConformation(EMicroUtils.display1(rs1.getDate("DOC")));
			empForm.setCounID(rs1.getString("LANDX"));
			empCountry=rs1.getString("LANDX");
			
			empState=rs1.getString("state");
			String getEmpState=" select * from State where LAND1='"+rs1.getString("LAND1")+"' AND BLAND='"+empState+"'";
			ResultSet rsEmpstate=ad.selectQuery(getEmpState);
			while(rsEmpstate.next()){
				
				empForm.setState(rsEmpstate.getString("BEZEI"));
			}
			empForm.setLocation(rs1.getString("LOCATION_CODE")+"-"+rs1.getString("LOCNAME"));
			empForm.setGradeID(rs1.getString("GRDTXT"));
			empForm.setDesignation(rs1.getString("DSGSTXT"));
			empForm.setDepartment(rs1.getString("DPTSTXT"));
			empForm.setEmailid(rs1.getString("EMAIL_ID"));
			empForm.setTelNo(rs1.getString("TEL_NO"));
			empForm.setExtnNo(rs1.getString("TEL_EXTENS"));
			String eligibleESI=rs1.getString("ESI");
			if(eligibleESI.equalsIgnoreCase("Y"));
			{
			eligibleESI="Yes";
			}
			if(eligibleESI.equalsIgnoreCase("N"));
			{
			eligibleESI="No";
			}
			empForm.setEligibleforESIDeduction(eligibleESI);
			empForm.setEsiNumber(rs1.getString("ESINO"));
			String eligiblePF=rs1.getString("PF");
			if(eligiblePF.equalsIgnoreCase("Y"));
			{
				eligiblePF="Yes";
			}
			if(eligiblePF.equalsIgnoreCase("N"));
			{
				eligiblePF="No";
			}
			empForm.setEligibleforPFDeduction(eligiblePF);
			empForm.setPfNumber(rs1.getString("PFNO"));
			String eligiblePT=rs1.getString("PT");
			if(eligiblePT.equalsIgnoreCase("Y"));
			{
				eligiblePT="Yes";
			}
			if(eligiblePT.equalsIgnoreCase("N"));
			{
				eligiblePT="No";
			}
			empForm.setEligibleforPTDeduction(eligiblePT);
			String eligibleIT=rs1.getString("IT");
			if(eligibleIT.equalsIgnoreCase("Y"));
			{
				eligibleIT="Yes";
			}
			if(eligibleIT.equalsIgnoreCase("N"));
			{
				eligibleIT="No";
			}
			empForm.setEligibleforITDeduction(eligibleIT);
			empForm.setPanNo(rs1.getString("PANNO"));
			String eligibleBonus=rs1.getString("BONUS");
			if(eligibleBonus.equalsIgnoreCase("Y"));
			{
				eligibleBonus="Yes";
			}
			if(eligibleBonus.equalsIgnoreCase("N"));
			{
				eligibleBonus="No";
			}
			empForm.setBonus(eligibleBonus);
			String eligibleLeaves=rs1.getString("LEAVES");
			if(eligibleLeaves.equalsIgnoreCase("Y"));
			{
				eligibleLeaves="Yes";
			}
			if(eligibleLeaves.equalsIgnoreCase("N"));
			{
				eligibleLeaves="No";
			}
			empForm.setLeaves(eligibleLeaves);
			empForm.setSalaryCurrency(rs1.getString("WAERS"));
			empForm.setPaymentMethod(rs1.getString("pay_method"));
			
			String accType=rs1.getString("BACCTYP");
			if(accType.equals("S"));
			{
				accType="Savings Account";
			}
			if(accType.equals("C"));
			{
				accType="Current Account";
			}
			empForm.setAccountType("");
			empForm.setAccountNumber(rs1.getString("BACCNO"));
			
			empForm.setIfsCCode(rs1.getString("IFSC_CODE"));
			empForm.setMicrCode(rs1.getString("MICR_CODE"));
			empForm.setBoardLine(rs1.getString("BOARD_LINE"));
			
			
			
		//	empForm.setDepartment(rs.getString("DPTID"));
		//	empForm.setDepartIDList("DPTID");}
			String getBankDetails="select BRANCH,IFSC_CODE,MICR_CODE from emp_official_info where PERNR='"+user.getEmployeeNo()+"' ";
			ResultSet rs=ad.selectQuery(getBankDetails);
			while(rs.next()){
				empForm.setBranchName(rs.getString("BRANCH"));
				empForm.setIfsCCode(rs.getString("IFSC_CODE"));
				empForm.setMicrCode(rs.getString("MICR_CODE"));
			}
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			int empPhotoCount=0;
			String checkImage="select count(*) from Employee_Photos where employeeNo='"+userId.getEmployeeNo()+"'";
			ResultSet rsCheck=ad.selectQuery(checkImage);
			while(rsCheck.next()){
				empPhotoCount=rsCheck.getInt(1);
			}
			if(empPhotoCount>0){
			String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
			ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
			while (rsEmpPhoto.next())
			{
				empForm.setPhotoImage(rsEmpPhoto.getString("file_name"));
			request.setAttribute("employeePhoto", "employeePhoto");	
			}
			}else{
				String gender="";
				String getGender="select SEX from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
				ResultSet rsGender=ad.selectQuery(getGender);
				while(rsGender.next()){
					gender=rsGender.getString("SEX");
				}
				if(gender.equalsIgnoreCase("M"))
				{
					empForm.setPhotoImage("male.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}else{
					empForm.setPhotoImage("female.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}
			}
			empForm.setUanno("");
			String uanNo=rs1.getString("UANNO");
			if(uanNo!=null){
				if(!uanNo.equals(""))
					empForm.setUanno(rs1.getString("UANNO"));
			}
		}
			String branchName="";
			String bankName="";
			String ifsCode="";
			String micrCode="";
			String getBankDetails1="select bank.BRANCH,bank.BNAME,e.IFSC_CODE,e.MICR_CODE from emp_official_info e,Bank as bank "
					+ "where PERNR='"+user.getEmployeeNo()+"' and  e.BANKID=bank.BANKID  ";
			ResultSet rsBankDetails1=ad.selectQuery(getBankDetails1);
			while(rsBankDetails1.next()){
				branchName=rsBankDetails1.getString("BRANCH");
				bankName=rsBankDetails1.getString("BNAME");
				ifsCode=rsBankDetails1.getString("IFSC_CODE");
				micrCode=rsBankDetails1.getString("MICR_CODE");
			}
			empForm.setBranchName(branchName);
			empForm.setBankName(bankName);
			empForm.setIfsCCode(ifsCode);
			empForm.setMicrCode(micrCode);
			String getLocation="select loc.LAND1 from emp_official_info emp,Location loc where emp.PERNR='"+user.getEmployeeNo()+"' and emp.LOCID=loc.LOCATION_CODE ";
			ResultSet rsLoc=ad.selectQuery(getLocation);
			while(rsLoc.next()){
				empForm.setPlace(rsLoc.getString("LAND1"));
			}
			UserInfo userId=(UserInfo)session.getAttribute("user");
			int empPhotoCount=0;
			String getBankDetails="select BRANCH,IFSC_CODE,MICR_CODE from emp_official_info where PERNR='"+user.getEmployeeNo()+"' ";
			ResultSet rs=ad.selectQuery(getBankDetails);
			while(rs.next()){
				empForm.setBranchName(rs.getString("BRANCH"));
				empForm.setIfsCCode(rs.getString("IFSC_CODE"));
				empForm.setMicrCode(rs.getString("MICR_CODE"));
			}
			String checkImage="select count(*) from Employee_Photos where employeeNo='"+userId.getEmployeeNo()+"'";
			ResultSet rsCheck=ad.selectQuery(checkImage);
			while(rsCheck.next()){
				empPhotoCount=rsCheck.getInt(1);
			}
			if(empPhotoCount>0){
			String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
			ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
			while (rsEmpPhoto.next())
			{
				empForm.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
			request.setAttribute("employeePhoto", "employeePhoto");	
			}
			}else{
				String gender="";
				String getGender="select SEX from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
				ResultSet rsGender=ad.selectQuery(getGender);
				while(rsGender.next()){
					gender=rsGender.getString("SEX");
				}
				if(gender.equalsIgnoreCase("M"))
				{
					empForm.setPhotoImage("male.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}else{
					empForm.setPhotoImage("female.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}
			}
			details.add(empForm);
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("details", details);
		return mapping.findForward("displayOfficial");
	}
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	// TODO Auto-generated method stub
		
		EmployeeOfficialInfoForm empOfficialForm = (EmployeeOfficialInfoForm) form;
		String id=request.getParameter("id"); 		
		HttpSession session=request.getSession();
		String sql="select * from links where module='"+id+"' and sub_linkname is null";
		
		ResultSet rs=ad.selectQuery(sql);
		
		try{
			
			LinkedHashMap<String, String> hm=new LinkedHashMap<String, String>();	
			
			
			 while(rs.next()){
				 hm.put(rs.getString("link_path")+"?method="+rs.getString("method")+"&sId="+rs.getString("link_name")+"&id="+rs.getString("module"), rs.getString("link_name"));
				 
			}
			 
			 session.setAttribute("SUBLINKS", hm);
			
			
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		
		ArrayList list =new ArrayList();
		
		request.setAttribute("listName", list);
		

		return mapping.findForward("display");
	}
	public ActionForward display1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
	
		EmployeeOfficialInfoForm empOfficialForm = (EmployeeOfficialInfoForm) form;
	
		String linkName=request.getParameter("sId"); 	
		String module=request.getParameter("id"); 		
		HttpSession session=request.getSession();		
					
		
		String sql="select * from links where link_name='"+linkName+"' and module='"+module+"'";
		
		ResultSet rs=ad.selectQuery(sql);
	
		try{
							
			 String sql1="select * from links where module='"+module+"' and sub_linkname is null";
				
				ResultSet rs1=ad.selectQuery(sql1);
					
					
					LinkedHashMap hm=new LinkedHashMap();	
					
					ArrayList a1=new ArrayList();
					 while(rs1.next()){
						
						 
						 hm.put(rs1.getString("link_path")+"?method="+rs1.getString("method")+"&sId="+rs1.getString("link_name")+"&id="+rs1.getString("module"), rs1.getString("link_name"));
						 
						 String linkName1=rs1.getString("link_name");
						 
						 if(linkName1.equalsIgnoreCase(linkName)){
						 
						 String sql2="select * from links where module='"+module+"' and sub_linkname='"+rs1.getString("link_name")+"' and sub_linkname is not null";
						 
						 ResultSet rs2=ad.selectQuery(sql2);
						 
						 while (rs2.next()) {
						 	 a1.add(rs2.getString("link_name")+","+rs2.getString("link_path")+"?method="+rs2.getString("method")+"&subLink="+rs2.getString("sub_linkname")+"&module="+rs2.getString("module")+"&linkName="+rs2.getString("link_name"));
						 }
						 
						 hm.put("Arr",a1);
						 }
						 
					}
					 session.setAttribute("SUBLINKS", hm);
		}catch(SQLException se){
			se.printStackTrace();
		}
	
		ArrayList list =new ArrayList();

		request.setAttribute("listName", list);
		
		 
		 
		return mapping.findForward("display1");
	}
	
	public ActionForward displayOfficialInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		EmployeeOfficialInfoForm empLForm =(EmployeeOfficialInfoForm)form;
		String linkName=request.getParameter("sId"); 	
		String module=request.getParameter("id"); 
		HttpSession session=request.getSession();
		LinkedList details = new LinkedList();
		try{		
			UserInfo user=(UserInfo)session.getAttribute("user");
			
			empLForm.setMessage("");
			empLForm.setMessage1("");
		String empCountry="";
		String empState="";
		EmployeeOfficialInfoForm empOffForm = new EmployeeOfficialInfoForm();
			String sql1="SELECT emp.EMP_FULLNAME,emp.PERNR,comp.BUTXT,emp.WERKS,p.Short_desc,c.STAFFCAT,c.CATLTEXT,emp.RPTMGR,emp.ROOM,emp.APPMGR,emp.FLOOR,emp.BUILDING," +
					"emp.DOJ,emp.DOC,emp.DOL,coun.LANDX,emp.LAND1,emp.state,st.BEZEI,loc.LOCATION_CODE,loc.LOCNAME,g.GRDTXT,desg.DSGSTXT,dept.DPTSTXT," +
					"emp.EMAIL_ID,emp.TEL_NO,emp.TEL_EXTENS,emp.IP_PHONE,emp.ESI,emp.ESINO,emp.PF,emp.PFNO,emp.PT,emp.IT,emp.PANNO,emp.BONUS,emp.LEAVES,emp.WAERS," +
					"pay.pay_method,emp.BACCTYP,emp.BACCNO,emp.IFSC_CODE,emp.MICR_CODE,emp.BOARD_LINE,emp.UANNO FROM emp_official_info as emp,Paygroup_Master as p," +
					"Category  as c,Country as coun, State as st,Department as dept, Designation as desg,Grade as g,PAYMODE as pay,Location as loc ," +
					"Company as comp  where emp.PERNR='"+user.getEmployeeNo()+"' and emp.STAFFCAT=c.STAFFCAT and emp.LAND1=coun.LAND1 and emp.STATE=st.ID " +
					"and dept.DPTID=emp.DPTID and desg.DSGID =emp.DSGID and g.GRDID=emp.GRDID and pay.pay_id=emp.payment_method  and  emp.BUKRS=comp.BUKRS " +
					"and p.PAYGROUP=emp.pay_group and emp.LOCID=loc.LOCATION_CODE ";
 					ResultSet rs1=ad.selectQuery(sql1);
			while(rs1.next()){
				empOffForm.setFirstName(rs1.getString("EMP_FULLNAME"));
				empOffForm.setFirstName(user.getFirstName());
				empOffForm.setMiddleName(user.getMiddleName());
				empOffForm.setLastName(user.getLastName());
				empOffForm.setEmployeeNumber(rs1.getString("PERNR"));
				empOffForm.setEmployeeName(rs1.getString("EMP_FULLNAME"));
				empOffForm.setCompanyName(rs1.getString("BUTXT"));
				empOffForm.setPlant(rs1.getString("WERKS"));
				empOffForm.setPayGroup(rs1.getString("Short_desc"));
				empOffForm.setEmployeeCategory(rs1.getString("CATLTEXT"));
				String reportingMngr="";
				empOffForm.setReportingManger("");
				empOffForm.setRoom(rs1.getString("ROOM"));
				empOffForm.setIpPhone(rs1.getString("IP_PHONE"));
				String approverMgr="";
				String getApprMgrName="select emp.EMP_FULLNAME from ESS_Approvers as ess,emp_official_info as emp where ess.employeeNumber='"+rs1.getString("PERNR")+"' " +
				"and emp.PERNR=ess.ApproverId  and ess.essType='Leave' and ess.Priority=1";
		ResultSet rsAppr=ad.selectQuery(getApprMgrName);
		while(rsAppr.next())
		{
			approverMgr=rsAppr.getString("EMP_FULLNAME");
		}
				empOffForm.setApprovalManger(approverMgr);
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
				
				String accType=rs1.getString("BACCTYP");
				if(accType.equals("S"));
				{
					accType="Savings Account";
				}
				if(accType.equals("C"));
				{
					accType="Current Account";
				}
				empOffForm.setAccountType("");
				empOffForm.setAccountNumber(rs1.getString("BACCNO"));
				
				empLForm.setIfsCCode(rs1.getString("IFSC_CODE"));
				empLForm.setMicrCode(rs1.getString("MICR_CODE"));
				empOffForm.setBoardLine(rs1.getString("BOARD_LINE"));
				
				String uanNo=rs1.getString("UANNO");
				if(uanNo!=null){
					if(!uanNo.equals(""))
						empOffForm.setUanno(rs1.getString("UANNO"));
				}
				
			//	empForm.setDepartment(rs.getString("DPTID"));
			//	empForm.setDepartIDList("DPTID");
				
				
			}
			String branchName="";
			String bankName="";
			String ifsCode="";
			String micrCode="";
			String getBankDetails1="select bank.BRANCH,bank.BNAME,e.IFSC_CODE,e.MICR_CODE from emp_official_info e,Bank as bank "
					+ "where PERNR='"+user.getEmployeeNo()+"' and  e.BANKID=bank.BANKID  ";
			ResultSet rsBankDetails1=ad.selectQuery(getBankDetails1);
			while(rsBankDetails1.next()){
				branchName=rsBankDetails1.getString("BRANCH");
				bankName=rsBankDetails1.getString("BNAME");
				ifsCode=rsBankDetails1.getString("IFSC_CODE");
				micrCode=rsBankDetails1.getString("MICR_CODE");
			}
			empOffForm.setBranchName(branchName);
			empOffForm.setBankName(bankName);
			empOffForm.setIfsCCode(ifsCode);
			empOffForm.setMicrCode(micrCode);
			
			
			String getLocation="select loc.LAND1 from emp_official_info emp,Location loc where emp.PERNR='"+user.getEmployeeNo()+"' and emp.LOCID=loc.LOCATION_CODE ";
			ResultSet rsLoc=ad.selectQuery(getLocation);
			while(rsLoc.next()){
				empLForm.setPlace(rsLoc.getString("LAND1"));
			}
			
			String getBankDetails="select BRANCH,IFSC_CODE,MICR_CODE from emp_official_info where PERNR='"+user.getEmployeeNo()+"' ";
			ResultSet rs=ad.selectQuery(getBankDetails);
			while(rs.next()){
				empLForm.setBranchName(rs.getString("BRANCH"));
				empLForm.setIfsCCode(rs.getString("IFSC_CODE"));
				empLForm.setMicrCode(rs.getString("MICR_CODE"));
			}
			
			
			UserInfo userId=(UserInfo)session.getAttribute("user");
			userId.getId();
			int empPhotoCount=0;
			String checkImage="select count(*) from Employee_Photos where employeeNo='"+userId.getEmployeeNo()+"'";
			ResultSet rsCheck=ad.selectQuery(checkImage);
			while(rsCheck.next()){
				empPhotoCount=rsCheck.getInt(1);
			}
			if(empPhotoCount>0){
			String getEmpPhoto="select * from Employee_Photos where  employeeNo='"+userId.getEmployeeNo()+"' ";
			ResultSet rsEmpPhoto = ad.selectQuery(getEmpPhoto);
			while (rsEmpPhoto.next())
			{
				empLForm.setPhotoImage(rsEmpPhoto.getString("file_name")+"?time="+new Date().getTime());
			request.setAttribute("employeePhoto", "employeePhoto");	
			}
			}else{
				String gender="";
				String getGender="select SEX from emp_official_info where PERNR='"+userId.getEmployeeNo()+"'";
				ResultSet rsGender=ad.selectQuery(getGender);
				while(rsGender.next()){
					gender=rsGender.getString("SEX");
				}
				if(gender.equalsIgnoreCase("M"))
				{
					empLForm.setPhotoImage("male.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}else{
					empLForm.setPhotoImage("female.png");
					request.setAttribute("employeePhoto", "employeePhoto");	
				}
			}
			details.add(empOffForm);
		}
		catch(SQLException se)
		{
			System.out.println("Exception @ get official info");
			se.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		request.setAttribute("details", details);
		return mapping.findForward("displayOfficial");
	}
}
