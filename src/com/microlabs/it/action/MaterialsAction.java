package com.microlabs.it.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.microlabs.admin.dao.UserDao;
import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.action.LeaveAction;
import com.microlabs.ess.dao.EssDao;
import com.microlabs.ess.form.MaterialCodeRequestForm;
import com.microlabs.ess.form.PackageMaterialMasterForm;
import com.microlabs.ess.form.RawMaterialForm;
import com.microlabs.it.form.MaterialsForm;
import com.microlabs.main.action.MailInboxAction;
import com.microlabs.main.db.MainDao;
import com.microlabs.main.form.ApprovalsForm;
import com.microlabs.main.form.MailInboxForm;
import com.microlabs.mms.materialcoderequest.sapconnector.MaterialCodeRequestToSAP;
import com.microlabs.newsandmedia.dao.NewsandMediaDao;
import com.microlabs.utilities.EMailer;
import com.microlabs.utilities.EMicroUtils;
import com.microlabs.utilities.UserInfo;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;
public class MaterialsAction extends DispatchAction {
	

	private static final String DESTINATION = "SAP_DESTINATION";
	private void connectSAP(String username, String password) {
		try {
		
			Properties connectProperties = new Properties();// TODO change the
			// details
			connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
					"192.168.1.2");
			connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
			connectProperties
					.setProperty(DestinationDataProvider.JCO_CLIENT, "900");
			connectProperties.setProperty(DestinationDataProvider.JCO_USER,
					username);
			connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
					password);
			connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
					"EN");
			File destCfg = new File(DESTINATION + ".jcoDestination");

			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "SAP_DESTINATION config");
			fos.close();

		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",e);
			
		}
	}
	
	
	public String Empname(String a)
	{
	
		NewsandMediaDao ad=new NewsandMediaDao();
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
		
		}
		return b;
		
	}
		
	public ActionForward sapUserCredentials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		MaterialsForm help=(MaterialsForm)form;
		String update="select * from Sap_Credential where user_id='"+user.getEmployeeNo()+"'  ";
		ResultSet rs = ad.selectQuery(update);
		try {
			if(rs.next())
			{
				help.setUsername(rs.getString("Sap_user_id"));
				help.setPassword(rs.getString("Sap_user_pwd"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return mapping.findForward("sapUserCredentials");
	}
	
	public ActionForward sapSaveCredentials(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	{
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		NewsandMediaDao ad=new NewsandMediaDao();
		
		MaterialsForm help=(MaterialsForm)form;
		
		String update="delete from Sap_Credential where user_id='"+user.getEmployeeNo()+"'  ";
		int saveStatus=ad.SqlExecuteUpdate(update);
		
		
		String update1=" insert into Sap_Credential(Sap_user_id,Sap_user_pwd,User_id) values"
					 + "( '"+help.getUsername()+"' ,'"+help.getPassword()+"','"+user.getEmployeeNo()+"'  ) ";
		int saveStatus1=ad.SqlExecuteUpdate(update1);
		
		help.setMessage("Credential saved Successfully");
		
		return mapping.findForward("sapUserCredentials");
	}
	
	
	public ActionForward sapMaterialCode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	
	{
		
		MaterialsForm approvalsForm=(MaterialsForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String selectedReq[]=approvalsForm.getSelectedRequestNo();
		
		String itemCode[]=request.getParameter("sapnos").split(",");
		System.out.println("total="+selectedReq.length);
		String reqType=approvalsForm.getReqRequstType();
		approvalsForm.setReqRequstType(reqType);
		String status=approvalsForm.getSelectedFilter(); 
		approvalsForm.setSelectedFilter(status);
		
		String swipetype="";
		String type="";
		String inTime="";
		String outTime="";
		String c[] = null;
		
		    
			JCoFunction function=null;
			JCoTable a=null;
			String username="";
			String password="";
			
			String a1="select sap_user_id,sap_user_pwd from Sap_Credential where user_id='"+user.getEmployeeNo()+"' ";
			ResultSet rsk = ad.selectQuery(a1);
			try {
				if(rsk.next())
				{
				username=rsk.getString(1);
				password=rsk.getString(2);
				}
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			connectSAP(username,password);
			JCoDestination destination = null;
			try {
				destination = JCoDestinationManager
						.getDestination(DESTINATION);
			} catch (JCoException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}// TODO change to real
													// destination
			if (destination == null) {
				
				try {
					destination = JCoDestinationManager
							.getDestination(DESTINATION);
				} catch (JCoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}// TODO change to
															// real
															// destination
				if (destination == null) {
					throw new RuntimeException(
							"Could not connect to SAP, destination not found.");
				}
			}
			
			
			try {
				function = destination.getRepository().getFunction(
							"ZMATERIAL_CREATE_RFC");
			} catch (JCoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.toString());// TODO change to log
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				approvalsForm.setMessage(errors.toString());
				
			}
			if (function == null) {
			
				approvalsForm.setMessage(" Login Failure Please Check Username and password");
				pendingRecords(mapping, approvalsForm, request, response);
				return mapping.findForward("display");
			}
			
			JCoTable passTable = function.getTableParameterList().getTable("IT_MATMASTER1");
			
			for(int i=0;i<selectedReq.length;i++)
			{
				
			
				
				Date dNow = new Date( );
				SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(dNow);
				
				 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				    Calendar cal = Calendar.getInstance();
				    cal.add(Calendar.DATE, -1); 
				    String yestdate=dateFormat.format(cal.getTime());
				        
		
				String location="";
				String matGroup="";
				String approver="";
				String parllelAppr1=""; 
				String parllelAppr2="";
				String reqDate="";
				String reqesterID="";
				String requestNo="";
				String reqId=selectedReq[i];
				String item="";
				if(itemCode.length>0)
				 item=itemCode[i];
				int priorityCout=0;
				int checkStatus=0;
				String matType="";
				String getApproverDetails="";
				
				
				String leave="  select "
						+ " material_sap_template.sALES_VIEW,"
						+ " material_sap_template.material_type,"
						+ " '' as material_code,"
						+ " material_sap_template.industry_sector,"
						+ " material_code_request.MATERIAL_SHORT_NAME,"
						+ " material_code_request.UNIT_OF_MEAS_ID as base_Unit_of_Measure,"
						+ " material_code_request.MATERIAL_GROUP_ID as material_Group,"
						+ " material_sap_template.external_Material_Group,"
						+ " material_sap_template.class_Type ,"
						+ " material_sap_template.class_number ,"
						+ " material_sap_template.division,"
						+ " material_code_request.DIVISION_ID,"
						+ " material_code_request.BRAND_ID,"
						+ "	material_sap_template.general_item_category_group,"
						+ "	material_sap_template.gross_weight,"
						+ "	material_sap_template.weight_Unit,"
						+ " material_sap_template.net_weight,"
						+ "	material_sap_template.prod_Insp_Memo,"
						+ "	material_sap_template.batch_management_requirement_indicator,"
						+ " material_sap_template.transportation_group,"
						+ "	material_sap_template.order_Unit,"
						+ "	material_sap_template.purchasing_Value_Key,"
						+ " material_sap_template.temperature_conditions_indicator,"
						+ "	material_sap_template.Storage_conditions,"
						+ " material_sap_template.period_indicator_for_shelf,"
						+ " material_sap_template.total_shelf_life,"
						+ " material_sap_template.rounding_rule_for_calculation_of_SLED,"
						+ "	material_code_request.STORAGE_LOCATION_ID as storage_Location,"
						+ " material_code_request.HSN_Code as HSN_Code,"
						+ " material_sap_template.sales_Organization,"
						+ "	material_sap_template.distribution_Channel,"
						+ "	material_sap_template.sales_unit,"
						+ "	location.location_code as delivering_Plant,"
						+ " material_sap_template.cash_discount_indicator,"
						+ "	material_sap_template.material_statistics_group,"
						+ "	material_sap_template.material_Pricing_Group,"
						+ " material_sap_template.account_assignment_group_for_this_material,"
						+ "	material_sap_template.item_category_group_from_material_master,"
						+ "	material_sap_template.material_group_1,"
						+ " material_sap_template.material_group_2,"
						+ "	material_sap_template.material_group_3,"
						+ "	material_sap_template.material_group_4,"
						+ "	material_sap_template.material_group_5,"
						+ "	location.location_code as plant,"
						+ "	material_sap_template.checking_Group_for_Availability_Check,"
						+ "	material_sap_template.loading_group,"
						+ "	location.location_code as profit_Center,"
						+ " material_code_request.PURCHASE_GROUP_ID as purchasing_Group,"
						+ "	material_sap_template.goods_receipt_processing_time_in_days,"
						+ "	material_sap_template.mRP_Type,"
						+ "	material_sap_template.lot_size,"
						+ " material_sap_template.procurement_Type,"
						+ "	material_sap_template.determination_of_batch_entry_in_the_process_order,"
						+ "	STORAGE_LOCATION.STORAGE_LOCATION_ID as issue_Storage_Location,"
						+ " material_sap_template.indicator_Backflush,"
						+ "	STORAGE_LOCATION.STORAGE_LOCATION_ID as default_storage_location_for_external_procurement,"
						+ "	material_sap_template.planned_delivery_time_in_days,"
						+ " material_sap_template.safety_stock,"
						+ "	material_sap_template.period_indicator,"
						+ "	material_sap_template.dependent_requirements,"
						+ "	material_sap_template.lot_Size_for_Product_Costing,"
						+ " material_sap_template.post_to_insp_Stock,"
						+ "	material_code_request.VALUATION_CLASS ,"
						+ "	material_sap_template.price_Unit,"
						+ "	material_sap_template.standard_Price,"
						+ " material_code_request.material_long_name as sales_Text,"
						+ "	material_code_request.material_long_name as purchase_Text,"
						+ "	material_sap_template.departure_country,"
						+ " material_sap_template.tax_category,"
						+ " material_sap_template.tax_classification_material,"
						
						+ " material_sap_template.departure_country1,"
						+ " material_sap_template.tax_category1,"
						+ " material_sap_template.tax_classification_material1,"
						
						+ "	material_code_request.material_long_name as long_Material_Description,"
						+ " material_sap_template.authorization_Group,"
						+ " material_sap_template.material_Authorization"
				+ " from material_sap_template,material_code_request,STORAGE_LOCATION,Location where material_code_request.type =material_sap_template.material_type "
				+ " and material_code_request.REQUEST_NO='"+reqId+"' and STORAGE_LOCATION.STORAGE_LOCATION_ID=material_code_request.STORAGE_LOCATION_ID"
						+ " and Location.locid=material_code_request.LOCATION_ID and  material_code_request.type=STORAGE_LOCATION.Mat_type "; 
				
				ResultSet rs=ad.selectQuery(leave);
				try {
					if(rs.next())
					{
				
					passTable.appendRow();
					passTable.setValue("SALES_VIEW",rs.getString("sALES_VIEW").toUpperCase().toUpperCase());
					passTable.setValue("MTART",rs.getString("material_type").toUpperCase());
					passTable.setValue("MATNR",rs.getString("material_code").toUpperCase());
					passTable.setValue("MBRSH",rs.getString("industry_sector").toUpperCase());
					passTable.setValue("MAKTX",rs.getString("MATERIAL_SHORT_NAME").toUpperCase());
					passTable.setValue("MEINS",rs.getString("base_Unit_of_Measure").toUpperCase());
					passTable.setValue("MATKL",rs.getString("material_Group").toUpperCase());
					passTable.setValue("EXTWG",rs.getString("external_Material_Group").toUpperCase());
					
					passTable.setValue("CLASSTYPE",rs.getString("class_Type").toUpperCase());
					passTable.setValue("CLASSNUM",rs.getString("class_number").toUpperCase());
					
					if(rs.getString("material_type").equalsIgnoreCase("PPC"))
					{
						passTable.setValue("SPART",rs.getString("DIVISION_ID").toUpperCase());
					}
					else
					{
						passTable.setValue("SPART",rs.getString("division").toUpperCase());
					}
					
					
					//passTable.setValue("SPART",rs.getString("division").toUpperCase());
					passTable.setValue("MTPOS_MARA",rs.getString("general_item_category_group").toUpperCase());
					passTable.setValue("BRGEW",rs.getString("gross_weight").toUpperCase());
					passTable.setValue("GEWEI",rs.getString("weight_Unit").toUpperCase());
					passTable.setValue("NTGEW",rs.getString("net_weight").toUpperCase());
					passTable.setValue("FERTH",rs.getString("prod_Insp_Memo").toUpperCase());
					passTable.setValue("XCHPF",rs.getString("batch_management_requirement_indicator").toUpperCase());
					passTable.setValue("TRAGR",rs.getString("transportation_group").toUpperCase());
					passTable.setValue("BRGEW",rs.getString("order_Unit").toUpperCase());
					passTable.setValue("EKWSL",rs.getString("purchasing_Value_Key").toUpperCase());
					passTable.setValue("TEMPB",rs.getString("temperature_conditions_indicator").toUpperCase());
					passTable.setValue("RAUBE",rs.getString("storage_conditions").toUpperCase());
					passTable.setValue("IPRKZ",rs.getString("period_indicator_for_shelf").toUpperCase());
					passTable.setValue("MHDHB",rs.getString("total_shelf_life").toUpperCase());
					passTable.setValue("RDMHD",rs.getString("rounding_rule_for_calculation_of_SLED").toUpperCase());
					passTable.setValue("LGORT",rs.getString("storage_Location").toUpperCase());
					passTable.setValue("VKORG",rs.getString("sales_Organization").toUpperCase());
					passTable.setValue("VTWEG",rs.getString("distribution_Channel").toUpperCase());
					passTable.setValue("VRKME",rs.getString("sales_unit").toUpperCase());
					passTable.setValue("DWERK",rs.getString("delivering_Plant").toUpperCase());
					passTable.setValue("SKTOF",rs.getString("cash_discount_indicator").toUpperCase());
					passTable.setValue("VERSG",rs.getString("material_statistics_group").toUpperCase());
					passTable.setValue("KONDM",rs.getString("material_Pricing_Group").toUpperCase());
					passTable.setValue("KTGRM",rs.getString("account_assignment_group_for_this_material").toUpperCase());
					passTable.setValue("MTPOS",rs.getString("item_category_group_from_material_master").toUpperCase());
					passTable.setValue("MVGR1",rs.getString("material_group_1").toUpperCase());
					
					if(rs.getString("material_type").equalsIgnoreCase("PPC"))
					{
						passTable.setValue("MVGR2",rs.getString("BRAND_ID").toUpperCase());
						
					}
					else
					{
						passTable.setValue("MVGR2",rs.getString("material_group_2").toUpperCase());	
					}
				//	passTable.setValue("MVGR2",rs.getString("material_group_2").toUpperCase());
					
					passTable.setValue("MVGR3",rs.getString("material_group_3").toUpperCase());
					passTable.setValue("MVGR4",rs.getString("material_group_4").toUpperCase());
					passTable.setValue("MVGR5",rs.getString("material_group_5").toUpperCase());
					passTable.setValue("WERKS",rs.getString("plant").toUpperCase());
					passTable.setValue("MTVFP",rs.getString("checking_Group_for_Availability_Check").toUpperCase());
					passTable.setValue("LADGR",rs.getString("loading_group").toUpperCase());
					passTable.setValue("STEUC",rs.getString("HSN_Code"));
					
					if(rs.getString("plant").equalsIgnoreCase("ML03"))
						passTable.setValue("PRCTR","ML01");
					else if(rs.getString("plant").equalsIgnoreCase("ML26"))
						passTable.setValue("PRCTR","ML00");
					else if(rs.getString("plant").equalsIgnoreCase("ML25"))
						passTable.setValue("PRCTR","ML11");
					else if(rs.getString("plant").equalsIgnoreCase("ML28"))
						passTable.setValue("PRCTR","ML00");
					else
					passTable.setValue("PRCTR",rs.getString("profit_Center").toUpperCase());
					passTable.setValue("EKGRP",rs.getString("purchasing_Group").toUpperCase());
					passTable.setValue("WEBAZ",rs.getString("goods_receipt_processing_time_in_days").toUpperCase());
					passTable.setValue("DISMM",rs.getString("mRP_Type").toUpperCase());
					passTable.setValue("DISLS",rs.getString("lot_size").toUpperCase());
					passTable.setValue("BESKZ",rs.getString("procurement_Type").toUpperCase());
					passTable.setValue("KZECH",rs.getString("determination_of_batch_entry_in_the_process_order").toUpperCase());
					passTable.setValue("LGPRO",rs.getString("issue_Storage_Location").toUpperCase());
					passTable.setValue("RGEKZ",rs.getString("indicator_Backflush").toUpperCase());
					passTable.setValue("LGFSB",rs.getString("default_storage_location_for_external_procurement").toUpperCase());
					passTable.setValue("PLIFZ",rs.getString("planned_delivery_time_in_days").toUpperCase());
					passTable.setValue("EISBE",rs.getString("safety_stock").toUpperCase());
					passTable.setValue("PERKZ",rs.getString("period_indicator").toUpperCase());
					passTable.setValue("SBDKZ",rs.getString("dependent_requirements").toUpperCase());
					passTable.setValue("LOSGR",rs.getString("lot_Size_for_Product_Costing").toUpperCase());
					passTable.setValue("INSMK",rs.getString("post_to_insp_Stock").toUpperCase());
					passTable.setValue("BKLAS",rs.getString("valuation_Class").toUpperCase());
					passTable.setValue("PEINH",rs.getString("price_Unit").toUpperCase());
					passTable.setValue("STPRS",rs.getString("standard_Price").toUpperCase());
					passTable.setValue("S_TDLINE",rs.getString("sales_Text").toUpperCase());
					passTable.setValue("P_TDLINE",rs.getString("purchase_Text").toUpperCase());
					passTable.setValue("ALAND",rs.getString("departure_country").toUpperCase());
					passTable.setValue("TATYP",rs.getString("tax_category").toUpperCase());
					passTable.setValue("TAXKM",rs.getString("tax_classification_material").toUpperCase());
					
					passTable.setValue("ALAND1",rs.getString("departure_country1").toUpperCase());
					passTable.setValue("TATYP1",rs.getString("tax_category1").toUpperCase());
					passTable.setValue("TAXKM1",rs.getString("tax_classification_material1").toUpperCase());
					
					passTable.setValue("ZDESC",rs.getString("long_Material_Description").toUpperCase());
					passTable.setValue("BEGRU",rs.getString("authorization_Group").toUpperCase());
					passTable.setValue("QMATA",rs.getString("material_Authorization").toUpperCase());
					passTable.setValue("VPRSV","S");
					passTable.setValue("REQNO",reqId);
					
					
					
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		
			
			}
			
					
				try {
						
						
						function.execute(destination);
						
						
					} catch (JCoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace(); 
					}
					LinkedList leaveList = new LinkedList();
					JCoTable returnTable = function.getTableParameterList().getTable("IT_MESSAGE");//PAYDATA_D_H
				    
					if(returnTable.getNumRows() > 0){
						returnTable.firstRow();
						do{
							MaterialsForm mat=new MaterialsForm();
							mat.setStatus(returnTable.getString("TYPE"));
							mat.setRequestNo(Integer.toString((returnTable.getInt("REQNO"))));
							mat.setSapCodeNo(returnTable.getString(("MATNR")));
							mat.setSapCreationDate(returnTable.getString(("ERDAT")));
							mat.setMessage(returnTable.getString(("MESSAGE")));
							
						
							leaveList.add(mat);
							
						}
						while(returnTable.nextRow());
				
						MaterialsAction abn=new MaterialsAction();
					abn.updateSAPSTATUS(leaveList,user.getEmployeeNo(),request);
					
					
					
					}
					
					
					String ACtualno="";
				    if(selectedReq!=null)
				    {	
				    
				    StringBuffer reqdept = new StringBuffer();
					for (int i = 0; i < selectedReq.length; i++) {
						reqdept.append("'"+selectedReq[i]+"'" + ",");
					}
					
					ACtualno= reqdept.substring(0, reqdept.length() - 1).toString();
				    
				    }
				    
				    //insert into material search
				    
				    String updat="INSERT INTO Material_Code_Search(LOCATION_ID,MATERIAL_TYPE,MATERIAL_GROUP,STORAGE_LOC,SAP_CODE_NO," +
						"MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME,UNIT_OF_MEAS_ID,APPROXIMATE_PRICE,SAP_CREATION_DATE,CREATED_DATE)"
						+ "select LOCATION_CODE,MATERIAL_TYPE.ID,STXT,STORAGE_LOCATION_ID,SAP_CODE_NO,MATERIAL_SHORT_NAME,MATERIAL_LONG_NAME,"
				    		+ "UNIT_OF_MEAS_ID,APPROXIMATE_VALUE, SAP_CREATION_DATE,SAP_CREATION_DATE from material_code_request,location,MATERIAL_TYPE,"
				    		+ "MATERIAL_GROUP where REQUEST_NO in ("+ACtualno+") and MATERIAL_TYPE.MATERIAL_GROUP_ID=material_code_request.Type AND "
				    		+ "location.LOCID=LOCATION_ID AND MATERIAL_GROUP.MATERIAL_GROUP_ID=material_code_request.MATERIAL_GROUP_ID AND SAP_CODE_NO!='' ";		    
				int nm=ad.SqlExecuteUpdate(updat);
				    
				    
				    ArrayList matlist = new ArrayList();
				    String reqno =" select request_no,sap_code_no,SAP_CREATION_DATE,sap_approved_date,message,sap_message "
				    			+ " from material_code_request where request_no in ("+ACtualno+") order by sap_approved_date desc";
				    ResultSet rs1=ad.selectQuery(reqno);
				    try {
						while(rs1.next())
						{
							MaterialsForm mat = new MaterialsForm();
							mat.setRequestNo(rs1.getString("request_no"));
							mat.setSapCodeNo(rs1.getString("sap_code_no"));
							mat.setSapCreationDate(rs1.getString("SAP_CREATION_DATE"));
							mat.setMessage(rs1.getString("message"));
							mat.setMessage2(rs1.getString("sap_message"));
							matlist.add(mat);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				    
				    
				    request.setAttribute("matlist", matlist);
				    /*approvalsForm.setMessage("Code Created Successfully , Notification Sent to Initiator");*/
				    
				    
					try{
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE where MATERIAL_GROUP_ID in ('LC','PPC','OSE')";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
						}
						approvalsForm.setMaterTypeIDList(materTypeIDList);
						approvalsForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
					}catch (Exception e) {
						e.printStackTrace();
					}
					
					
					LinkedList materGroupIDList=new LinkedList();
					LinkedList materialGroupIdValueList=new LinkedList();
					
					String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
					ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
					try {
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("STXT"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					approvalsForm.setMaterGroupIDList(materGroupIDList);
					approvalsForm.setMaterialGroupIdValueList(materialGroupIdValueList);
					
					
					ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
					ArrayList locationList=new ArrayList();
					ArrayList locationLabelList=new ArrayList();
					try { 
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					

					approvalsForm.setLocationIdList(locationList);
					approvalsForm.setLocationLabelList(locationLabelList);
					
					approvalsForm.setMessage("done");
					
					return mapping.findForward("display");
				
	
	}

	private Statement st=null;
	private Connection conn=null;
	
	public int[] updateSAPSTATUS(LinkedList sap,String username,HttpServletRequest request){
		NewsandMediaDao ad=new NewsandMediaDao();
		
		
		
		
		 try {
			 if(conn==null||conn.isClosed())
			 {
				 conn=ConnectionFactory.getConnection();
			 }
			 
			 PreparedStatement st1=conn.prepareStatement(" update All_Request set Req_Status='Completed',Last_Approver=Pending_Approver,"
			 		+ "  Pending_Approver='',Approved_Persons=Pending_Approver, Actual_Approver='"+username+"',Actual_Approved_Date=getdate(), "
			 				+ "approved_date=getdate() where Req_Id=? and Req_Type='Material Master' and req_status='Pending' ");
					 Iterator list1 = sap.iterator();
				while(list1.hasNext()) {
					MaterialsForm mat= (MaterialsForm)list1.next();
					if(mat.getStatus().equalsIgnoreCase("s"))
				    {
				
					st1.setString(1, mat.getRequestNo());
					st1.addBatch();
				    }
					
					
				    }
			 
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(dNow);
				
				PreparedStatement st=conn.prepareStatement("update material_code_request set MODIFIED_DATE=getdate(),"
						+ "  pending_approver='No' ,last_approver ='"+Empname(username)+"' ,MODIFIED_BY='"+username+"',Approve_Type=?,"
								+ " SAP_CREATION_DATE=?,SAP_Approved_Date='"+dateNow+"',SAP_Message=?,SAP_CODE_NO=?,SAP_CODE_EXISTS='0',"
								+ " message = 'Mass Approve' ,sap_status=?  where REQUEST_NO=?") ;
				Iterator attdItr = sap.iterator();
				while(attdItr.hasNext()) {
					MaterialsForm mat= (MaterialsForm)attdItr.next();
				    if(mat.getStatus().equalsIgnoreCase("s"))
				    {
				    st.setString(1, "Completed");
				    }
				    if(!mat.getStatus().equalsIgnoreCase("s"))
				    {
				    st.setString(1, "Pending");
				    }
					st.setString(2, mat.getSapCreationDate());
					st.setString(3, mat.getMessage());
					st.setString(4, mat.getSapCodeNo());
					st.setString(5, mat.getStatus());
					st.setString(6, mat.getRequestNo());
					
					st.addBatch();
				    
					System.out.println(mat.getSapCreationDate()+":"+mat.getMessage()+":"+mat.getSapCodeNo()+":"+ mat.getRequestNo());
					
					
				    }
				if(!sap.isEmpty()){	
					st1.executeBatch();
					 st.executeBatch();
					
					
					
				}
				
				
				///mail Triggering
				
			String mail="select distinct CREATED_BY from  material_code_request where SAP_Approved_Date='"+dateNow+"' and Approve_Type='Completed'";
			ResultSet nn=ad.selectQuery(mail);
				while(nn.next()) {
			
				EMailer email=new EMailer();
				
				email.sendMailToMaterialMASSCodeRequester(request, "",dateNow, "Material Code Request", username, nn.getString("CREATED_BY"), "Approve");
			   
				}
				
			} catch (SQLException e) {
				e.printStackTrace();// TODO Change to log
				StringWriter errors = new StringWriter();
				e.printStackTrace(new PrintWriter(errors));			
				Date d=new Date();
				SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				String dateNow = ft.format(d);
				String error="insert into ERROR_DETAILS values('SAP MATERIALCODE','"+dateNow+"','"+errors.toString()+"')";
				int i= ad.SqlExecuteUpdate(error);
			}
		return null; 
	}
	
	public ActionForward approveMaterialRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	  {
		
		MaterialsForm approvalsForm=(MaterialsForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		String selectedReq[]=approvalsForm.getSelectedRequestNo();
		
		String itemCode[]=request.getParameterValues("itemCode");
		System.out.println("total="+selectedReq.length);
		String reqType=approvalsForm.getReqRequstType();
		approvalsForm.setReqRequstType(reqType);
		String status=approvalsForm.getSelectedFilter();
		approvalsForm.setSelectedFilter(status);
		
		String swipetype="";
		String type="";
		String inTime="";
		String outTime="";
		String c[] = null;
		
		for(int i=0;i<selectedReq.length;i++)
		{
			
		if(itemCode[i]==null)
		{
			approvalsForm.setMessage("Please Enter Item Code");
			return mapping.findForward("display");
		}
		if(itemCode[i].equalsIgnoreCase(""))
		{
			approvalsForm.setMessage("Please Enter Item Code");
			return mapping.findForward("display");
		}
		
		}
			approvalsForm.setComments("");
			for(int i=0;i<selectedReq.length;i++){
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS");
					String dateNow = ft.format(dNow);
				String location="";
				String matGroup="";
				String approver="";
				String parllelAppr1=""; 
				String parllelAppr2="";
				String reqDate="";
				String reqesterID="";
				String requestNo="";
				String reqId=selectedReq[i];
				String item=itemCode[i];
				int priorityCout=0;
				int checkStatus=0;
				String matType="";
				String getApproverDetails="";
				//
				try{

					String updateStatus=" update All_Request set Req_Status='Approved',Comments='Mass Approve',Last_Approver=Pending_Approver, " +
										" Pending_Approver='',Approved_Persons=Pending_Approver, Actual_Approver='"+user.getEmployeeNo()+"',Actual_Approved_Date='"+dateNow+"', "+
							            " approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and req_status='Pending' ";
				    int saveStatus=ad.SqlExecuteUpdate(updateStatus);
				    
				    
		    	
		    		String updatQuery="update material_code_request set MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',"
		    				+ " SAP_CODE_NO='"+item+"',SAP_CODE_EXISTS='1', message = 'Mass Approve', "
		    				+ " Approve_Type='Completed',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";
		            int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
		            
		            
		            EMailer email=new EMailer();
					String approvermail="";
					email.sendMailToMaterialCodeRequester(request, approvermail, reqId, "Material Code Request", user.getEmployeeNo(), reqId, "Approve");
				    
					
					
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		pendingRecords(mapping, approvalsForm, request, response);
		return mapping.findForward("display");
	}
	
	
		
	
	
	public ActionForward curentRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	  {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		
		if(type.equalsIgnoreCase("Pending"))
		{
			request.setAttribute("approvedButtons", "approvedButtons");
			request.setAttribute("displayButton", "displayButton");
		}
		try{
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			approvalsForm.setMaterTypeIDList(materTypeIDList);
			approvalsForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		String reqType=approvalsForm.getMaterialCodeLists();	
	
		int totalRecords=approvalsForm.getTotalRecords();//21
		int startRecord=approvalsForm.getStartRecord();//11
		int endRecord=approvalsForm.getEndRecord();	
		
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(startRecord);
		approvalsForm.setEndRecord(endRecord);
		 
		 
			
		approvalsForm.setReqRequstType(reqType);
		approvalsForm.setSelectedFilter(type);
				String getMaterialRecords="";
				if(type.equalsIgnoreCase("Pending")){
					getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
					"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
					"where all_r.Req_Type='Material Master' and all_r.Pending_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id  and all_r.type='"+reqType+"' " +
					"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						}
					if(type.equalsIgnoreCase("Approved")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp  " +
						"where all_r.Req_Type='Material Master' and all_r.Last_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
							}
					
					if(type.equalsIgnoreCase("Rejected")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.Approved_Persons='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
								}
					if(type.equalsIgnoreCase("All")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.type='"+reqType+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and (all_R.Last_Approver ='"+user.getEmployeeNo()+"' or all_R.Pending_Approver = '"+user.getEmployeeNo()+"') and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id " +
						"and mat.LOCATION_ID=loc.LOCID and matType.MATERIAL_GROUP_ID=mat.Type )as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
							}
	ResultSet rsMat=ad.selectQuery(getMaterialRecords);	 
	try{
		LinkedList materialList=new LinkedList();
		while(rsMat.next()){
		MaterialCodeRequestForm masterForm=new MaterialCodeRequestForm();
		masterForm.setRequestNumber(rsMat.getString("Req_Id"));
		
		masterForm.setRequestDate(rsMat.getString("Req_Date"));
		masterForm.setLocationCode(rsMat.getString("LOCATION_CODE"));
		masterForm.setMaterialDesc(rsMat.getString("type")+"-"+rsMat.getString("M_DESC"));
		masterForm.setMaterailType(rsMat.getString("type"));
		masterForm.setEmployeeName(rsMat.getString("EMP_FULLNAME"));
		
		masterForm.setMaterialName(rsMat.getString("MATERIAL_SHORT_NAME"));
		masterForm.setMaterialGroupName(rsMat.getString("STXT"));
		masterForm.setUom(rsMat.getString("UNIT_OF_MEAS_ID"));
		masterForm.setLastApprover(rsMat.getString("last_approver"));
		masterForm.setPendingApprover(rsMat.getString("pending_approver"));
		masterForm.setItemExist("off");
		String approveStatus=rsMat.getString("Req_Status");
		if(approveStatus.equalsIgnoreCase("Approved")  )
		{
			masterForm.setApproveStatus("Completed");
		}else{
		masterForm.setApproveStatus(rsMat.getString("Req_Status"));
		}
		masterForm.setMaterialGroup(rsMat.getString("MATERIAL_GROUP_ID"));
		if(type.equalsIgnoreCase("Approved") || type.equalsIgnoreCase("All") )
		{
			masterForm.setSapCodeNo(rsMat.getString("SAP_CODE_NO"));
			String sapExistStatus=rsMat.getString("SAP_CODE_EXISTS");
			if( sapExistStatus!=null && sapExistStatus.equalsIgnoreCase("1") )
			masterForm.setItemCodeExist("on");
		
			String sapCreatDate=rsMat.getString("SAP_CREATION_DATE");
			if(sapCreatDate!=null)
			{
			masterForm.setSapCreationDate(rsMat.getString("SAP_CREATION_DATE"));
			}else{
				masterForm.setSapCreationDate("");
			}
			
			
		}
		masterForm.setSelectedFilter(type);
		materialList.add(masterForm);
		
		}
		request.setAttribute("materialList", materialList);
		request.setAttribute("Material List", "Material List");
		if(materialList.size()==0){
			request.setAttribute("no Material Master records", "no Material Master records");
			approvalsForm.setMessage3("No Records Found..");
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
			
	if(type.equalsIgnoreCase("Pending")){
		request.setAttribute("displayButton", "displayButton");
		}
		 
	if(totalRecords>10){
 	   
	       
		 
		 if(startRecord==1)
		 {
			 request.setAttribute("disablePreviousButton", "disablePreviousButton"); 
		 }
		 if(endRecord>10)
		 {
			 request.setAttribute("previousButton", "previousButton"); 
		 }
		 
		 if(endRecord==totalRecords)
		 {
			request.setAttribute("nextButton", "");
			request.setAttribute("disableNextButton", "disableNextButton");
		 }
		 if(endRecord<totalRecords)
		 {
			 request.setAttribute("nextButton", "nextButton");
		 }
	  }
	 approvalsForm.setTotalRecords(totalRecords);
	 approvalsForm.setStartRecord(startRecord);
	 approvalsForm.setEndRecord(endRecord);
	 if(totalRecords>10)
	 request.setAttribute("displayRecordNo", "displayRecordNo");
		
			
	if(type.equalsIgnoreCase("Pending")){
		request.setAttribute("displayButton", "displayButton");
		}
			
				return mapping.findForward("display");
			}
	
	public ActionForward lastRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
	 {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		
		try{
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			approvalsForm.setMaterTypeIDList(materTypeIDList);
			approvalsForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int totalRecords=approvalsForm.getTotalRecords();//21
		int startRecord=approvalsForm.getStartRecord();//11
		int endRecord=approvalsForm.getEndRecord();	
		String reqType=approvalsForm.getMaterialCodeLists();	
	
		startRecord=totalRecords-9;
		 endRecord=totalRecords;
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(totalRecords);
				String getMaterialRecords="";
				if(type.equalsIgnoreCase("Pending")){
					getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
					"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
					"where all_r.Req_Type='Material Master' and all_r.Pending_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id  and all_r.type='"+reqType+"' " +
					"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						}
					if(type.equalsIgnoreCase("Approved")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp  " +
						"where all_r.Req_Type='Material Master' and all_r.Last_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
							}
					
					if(type.equalsIgnoreCase("Rejected")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.Approved_Persons='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
								}
					if(type.equalsIgnoreCase("All")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.type='"+reqType+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and (all_R.Last_Approver ='"+user.getEmployeeNo()+"' or all_R.Pending_Approver = '"+user.getEmployeeNo()+"') and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id " +
						"and mat.LOCATION_ID=loc.LOCID and matType.MATERIAL_GROUP_ID=mat.Type )as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
							}
	ResultSet rsMat=ad.selectQuery(getMaterialRecords);	 
	try{
		LinkedList materialList=new LinkedList();
		while(rsMat.next()){
		MaterialCodeRequestForm masterForm=new MaterialCodeRequestForm();
		masterForm.setRequestNumber(rsMat.getString("Req_Id"));
		
		masterForm.setRequestDate(rsMat.getString("Req_Date"));
		masterForm.setLocationCode(rsMat.getString("LOCATION_CODE"));
		masterForm.setMaterialDesc(rsMat.getString("type")+"-"+rsMat.getString("M_DESC"));
		masterForm.setMaterailType(rsMat.getString("type"));
		masterForm.setEmployeeName(rsMat.getString("EMP_FULLNAME"));
		
		masterForm.setMaterialName(rsMat.getString("MATERIAL_SHORT_NAME"));
		masterForm.setMaterialGroupName(rsMat.getString("STXT"));
		masterForm.setUom(rsMat.getString("UNIT_OF_MEAS_ID"));
		masterForm.setLastApprover(rsMat.getString("last_approver"));
		masterForm.setPendingApprover(rsMat.getString("pending_approver"));
		masterForm.setItemExist("off");
		String approveStatus=rsMat.getString("Req_Status");
		if(approveStatus.equalsIgnoreCase("Approved")  )
		{
			masterForm.setApproveStatus("Completed");
		}else{
		masterForm.setApproveStatus(rsMat.getString("Req_Status"));
		}
		masterForm.setMaterialGroup(rsMat.getString("MATERIAL_GROUP_ID"));
		if(type.equalsIgnoreCase("Approved") || type.equalsIgnoreCase("All") )
		{
			masterForm.setSapCodeNo(rsMat.getString("SAP_CODE_NO"));
			String sapExistStatus=rsMat.getString("SAP_CODE_EXISTS");
			if( sapExistStatus!=null && sapExistStatus.equalsIgnoreCase("1") )
			masterForm.setItemCodeExist("on");
		
			String sapCreatDate=rsMat.getString("SAP_CREATION_DATE");
			if(sapCreatDate!=null)
			{
			masterForm.setSapCreationDate(rsMat.getString("SAP_CREATION_DATE"));
			}else{
				masterForm.setSapCreationDate("");
			}
			
			
		}
		masterForm.setSelectedFilter(type);
		materialList.add(masterForm);
		
		}
		request.setAttribute("materialList", materialList);
		request.setAttribute("Material List", "Material List");
		request.setAttribute("disableNextButton", "disableNextButton");
		request.setAttribute("previousButton", "previousButton");
		if(materialList.size()<10)
		{
			
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
		}
		request.setAttribute("displayRecordNo", "displayRecordNo");
	 request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
		e.printStackTrace();
	}
			
	if(type.equalsIgnoreCase("Pending")){
		request.setAttribute("displayButton", "displayButton");
		}
			
				return mapping.findForward("display");
			}

	public ActionForward previousRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		
		try{
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			approvalsForm.setMaterTypeIDList(materTypeIDList);
			approvalsForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		int totalRecords=approvalsForm.getTotalRecords();//21
		int endRecord=approvalsForm.getStartRecord()-1;//20
		int startRecord=approvalsForm.getStartRecord()-10;//11
		String reqType=approvalsForm.getMaterialCodeLists();	
	
		if(startRecord==1)
		{
			request.setAttribute("disablePreviousButton", "disablePreviousButton");
			endRecord=10;
		}
		
		
		approvalsForm.setTotalRecords(totalRecords);
		approvalsForm.setStartRecord(1);
		approvalsForm.setEndRecord(10);
		  String status=approvalsForm.getReqRequstType();
				String getMaterialRecords="";
				if(type.equalsIgnoreCase("Pending")){
					getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
					"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
					"where all_r.Req_Type='Material Master' and all_r.Pending_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id  and all_r.type='"+reqType+"' " +
					"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						}
					if(type.equalsIgnoreCase("Approved")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp  " +
						"where all_r.Req_Type='Material Master' and all_r.Last_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
							}
					
					if(type.equalsIgnoreCase("Rejected")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.Approved_Persons='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
								}
					if(type.equalsIgnoreCase("All")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.type='"+reqType+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and (all_R.Last_Approver ='"+user.getEmployeeNo()+"' or all_R.Pending_Approver = '"+user.getEmployeeNo()+"') and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id " +
						"and mat.LOCATION_ID=loc.LOCID and matType.MATERIAL_GROUP_ID=mat.Type )as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
							}
	ResultSet rsMat=ad.selectQuery(getMaterialRecords);	 
	try{
		LinkedList materialList=new LinkedList();
		while(rsMat.next()){
		MaterialCodeRequestForm masterForm=new MaterialCodeRequestForm();
		masterForm.setRequestNumber(rsMat.getString("Req_Id"));
		
		masterForm.setRequestDate(rsMat.getString("Req_Date"));
		masterForm.setLocationCode(rsMat.getString("LOCATION_CODE"));
		masterForm.setMaterialDesc(rsMat.getString("type")+"-"+rsMat.getString("M_DESC"));
		masterForm.setMaterailType(rsMat.getString("type"));
		masterForm.setEmployeeName(rsMat.getString("EMP_FULLNAME"));
		
		masterForm.setMaterialName(rsMat.getString("MATERIAL_SHORT_NAME"));
		masterForm.setMaterialGroupName(rsMat.getString("STXT"));
		masterForm.setUom(rsMat.getString("UNIT_OF_MEAS_ID"));
		masterForm.setLastApprover(rsMat.getString("last_approver"));
		masterForm.setPendingApprover(rsMat.getString("pending_approver"));
		masterForm.setItemExist("off");
		String approveStatus=rsMat.getString("Req_Status");
		if(approveStatus.equalsIgnoreCase("Approved")  )
		{
			masterForm.setApproveStatus("Completed");
		}else{
		masterForm.setApproveStatus(rsMat.getString("Req_Status"));
		}
		masterForm.setMaterialGroup(rsMat.getString("MATERIAL_GROUP_ID"));
		if(type.equalsIgnoreCase("Approved") || type.equalsIgnoreCase("All") )
		{
			masterForm.setSapCodeNo(rsMat.getString("SAP_CODE_NO"));
			String sapExistStatus=rsMat.getString("SAP_CODE_EXISTS");
			if( sapExistStatus!=null && sapExistStatus.equalsIgnoreCase("1") )
			masterForm.setItemCodeExist("on");
		
			String sapCreatDate=rsMat.getString("SAP_CREATION_DATE");
			if(sapCreatDate!=null)
			{
			masterForm.setSapCreationDate(rsMat.getString("SAP_CREATION_DATE"));
			}else{
				masterForm.setSapCreationDate("");
			}
			
			
		}
		masterForm.setSelectedFilter(type);
		materialList.add(masterForm);
		
		}
		request.setAttribute("materialList", materialList);
		request.setAttribute("Material List", "Material List");
		if(materialList.size()==0){
			request.setAttribute("no Material Master records", "no Material Master records");
			approvalsForm.setMessage3("No Records Found..");
		}
		
		
		approvalsForm.setTotalRecords(totalRecords);
		  approvalsForm.setStartRecord(startRecord);
		  approvalsForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			if(startRecord!=1)
			request.setAttribute("previousButton", "previousButton");
			request.setAttribute("displayRecordNo", "displayRecordNo");
			if(materialList.size()<10)
			{
				approvalsForm.setStartRecord(1);
				request.setAttribute("previousButton", "");
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			}
	 request.setAttribute("displayRecordNo", "displayRecordNo");
		
	}catch (Exception e) {
		e.printStackTrace();
	}
			
	if(type.equalsIgnoreCase("Pending")){
		request.setAttribute("displayButton", "displayButton");
		}
			
				return mapping.findForward("display");
			}
	
	public ActionForward nextRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		NewsandMediaDao ad=new NewsandMediaDao();
		
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		int userID=user.getId();
		String type=approvalsForm.getSelectedFilter();
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		
		try{
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			approvalsForm.setMaterTypeIDList(materTypeIDList);
			approvalsForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
			int totalRecords=approvalsForm.getTotalRecords();//21
			int startRecord=approvalsForm.getStartRecord();//11
			int endRecord=approvalsForm.getEndRecord();	
			String reqType=approvalsForm.getMaterialCodeLists();
			
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
				String getMaterialRecords="";
				if(type.equalsIgnoreCase("Pending")){
					getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
					"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
					"where all_r.Req_Type='Material Master' and all_r.Pending_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id  and all_r.type='"+reqType+"' " +
					"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						}
					if(type.equalsIgnoreCase("Approved")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp  " +
						"where all_r.Req_Type='Material Master' and all_r.Last_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
							}
					
					if(type.equalsIgnoreCase("Rejected")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.Approved_Persons='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id and all_r.type='"+reqType+"' " +
						"and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='"+type+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type)as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						 
								}
					if(type.equalsIgnoreCase("All")){
						getMaterialRecords="Select * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
						"all_r.Req_Status,mat.MATERIAL_GROUP_ID,mat.SAP_CODE_NO,mat.SAP_CODE_EXISTS,grp.STXT,mat.UNIT_OF_MEAS_ID,CONVERT(varchar(11),mat.SAP_CREATION_DATE,101) as SAP_CREATION_DATE from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
						"where all_r.Req_Type='Material Master' and all_r.type='"+reqType+"' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and (all_R.Last_Approver ='"+user.getEmployeeNo()+"' or all_R.Pending_Approver = '"+user.getEmployeeNo()+"') and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id " +
						"and mat.LOCATION_ID=loc.LOCID and matType.MATERIAL_GROUP_ID=mat.Type )as  sub Where  sub.RowNum between '"+startRecord+"' and '"+endRecord+"' order by Req_Id desc";	
						
							}
	ResultSet rsMat=ad.selectQuery(getMaterialRecords);	 
	try{
		LinkedList materialList=new LinkedList();
		while(rsMat.next()){
		MaterialCodeRequestForm masterForm=new MaterialCodeRequestForm();
		masterForm.setRequestNumber(rsMat.getString("Req_Id"));
		
		masterForm.setRequestDate(rsMat.getString("Req_Date"));
		masterForm.setLocationCode(rsMat.getString("LOCATION_CODE"));
		masterForm.setMaterialDesc(rsMat.getString("type")+"-"+rsMat.getString("M_DESC"));
		masterForm.setMaterailType(rsMat.getString("type"));
		masterForm.setEmployeeName(rsMat.getString("EMP_FULLNAME"));
		
		masterForm.setMaterialName(rsMat.getString("MATERIAL_SHORT_NAME"));
		masterForm.setMaterialGroupName(rsMat.getString("STXT"));
		masterForm.setUom(rsMat.getString("UNIT_OF_MEAS_ID"));
		masterForm.setLastApprover(rsMat.getString("last_approver"));
		masterForm.setPendingApprover(rsMat.getString("pending_approver"));
		masterForm.setItemExist("off");
		String approveStatus=rsMat.getString("Req_Status");
		if(approveStatus.equalsIgnoreCase("Approved")  )
		{
			masterForm.setApproveStatus("Completed");
		}else{
		masterForm.setApproveStatus(rsMat.getString("Req_Status"));
		}
		masterForm.setMaterialGroup(rsMat.getString("MATERIAL_GROUP_ID"));
		if(type.equalsIgnoreCase("Approved") || type.equalsIgnoreCase("All") )
		{
			masterForm.setSapCodeNo(rsMat.getString("SAP_CODE_NO"));
			String sapExistStatus=rsMat.getString("SAP_CODE_EXISTS");
			if( sapExistStatus!=null && sapExistStatus.equalsIgnoreCase("1") )
			masterForm.setItemCodeExist("on");
		
			String sapCreatDate=rsMat.getString("SAP_CREATION_DATE");
			if(sapCreatDate!=null)
			{
			masterForm.setSapCreationDate(rsMat.getString("SAP_CREATION_DATE"));
			}else{
				masterForm.setSapCreationDate("");
			}
			
			
		}
		masterForm.setSelectedFilter(type);
		materialList.add(masterForm);
		
		}
		request.setAttribute("materialList", materialList);
		request.setAttribute("Material List", "Material List");
		if(materialList.size()==0){
			request.setAttribute("no Material Master records", "no Material Master records");
			approvalsForm.setMessage3("No Records Found..");
		}
		if(materialList.size()!=0)
		{
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(startRecord);
			approvalsForm.setEndRecord(endRecord);
			request.setAttribute("nextButton", "nextButton");
			request.setAttribute("previousButton", "previousButton");
		}
		else
		{
			int start=startRecord;
			int end=startRecord;
			
			approvalsForm.setTotalRecords(totalRecords);
			approvalsForm.setStartRecord(start);
			approvalsForm.setEndRecord(end);
			
		}
	 if(materialList.size()<10)
	 {
		 approvalsForm.setTotalRecords(totalRecords);
		 approvalsForm.setStartRecord(startRecord);
		 approvalsForm.setEndRecord(startRecord+materialList.size()-1);
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
			
	if(type.equalsIgnoreCase("Pending")){
		request.setAttribute("displayButton", "displayButton");
		}
			}
				return mapping.findForward("display");
			}
	public ActionForward saveInSAP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user == null)
		{
			approvalsForm.setMessage3("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		
		
		/*String selectedReq[]=(String[])session.getAttribute("selectedReq");
		String itemCode[]=(String[])session.getAttribute("modifyItemCode");
		String itemExist[]=(String[])session.getAttribute("itemExist");*/
		
		String reqReqNo=approvalsForm.getReqReqNo();
		String reqItemNo=approvalsForm.getReqItemNo();
		String reqItemExist=approvalsForm.getReqItemExist();
		
		System.out.println("reqReqNo="+reqReqNo);
		System.out.println("reqItemNo="+reqItemNo);
		System.out.println("reqItemExist="+reqItemExist);
		
		return mapping.findForward("createInSAP");
	}
	
	public ActionForward createInSAP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user == null)
		{
			approvalsForm.setMessage3("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		
		try{
			String selectedReq[]=approvalsForm.getSelectedRequestNo();
			MaterialCodeRequestToSAP materCode=new MaterialCodeRequestToSAP();
			materCode.sendRequest(selectedReq);
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		curentRecord(mapping, approvalsForm, request, response);
		/*try {
			String SAPUSER = "rfcprd";
			  String SAP_PWD = "Test123#";
			  String SAP_CLIENT_ID = "500";
			 String SAP_HOST = "192.168.1.2";
			  String SAP_SYS_NO = "00";	
			  System.out.println("\n\nVersion of the JCO-library:\n" +
                      "---------------------------\n" + JCO.getMiddlewareVersion());
		JCO.Client mConnection=JCO.createClient(SAP_CLIENT_ID, // SAP client

	            SAPUSER, // userid

	            SAP_PWD, // password

	            "EN", // language

	            SAP_HOST, // application server host name

	            "00");
		mConnection.connect();

	       System.out.println(mConnection.getAttributes());
	       mConnection.disconnect();
		
		}catch (Exception e) {
			e.printStackTrace();
		}*/
	/*	String selectedReq[]=approvalsForm.getSelectedRequestNo();
		System.out.println("total="+selectedReq.length);
		
		String itemCode[]=approvalsForm.getItemCode();
		String itemExist[]=approvalsForm.getItemExist();
		LinkedList itemCodeChange=new LinkedList();;
		for(int i=0;i<itemCode.length;i++)
		{
			String sapCode=itemCode[i];
			int j=0;
			if(!(sapCode.equalsIgnoreCase("")))
			{
				itemCodeChange.add(sapCode);
			}
			
		}
		
		LinkedList itemStatusChange=new LinkedList();
		for(int i=0;i<itemExist.length;i++)
		{
			String itemExists=itemExist[i];
			int j=0;
			if(!(itemExists.equalsIgnoreCase("")))
			{
				itemStatusChange.add(itemExist[i]);
			}
			
		}
		String itemExist1[]=(String[])itemStatusChange.toArray(new String[itemStatusChange.size()]);
		
		String modifyItemCode[]=(String[])itemCodeChange.toArray(new String[itemCodeChange.size()]);
		
		String masterType=approvalsForm.getReqRequstType();
		String reqType=approvalsForm.getMaterialCodeLists();
		
		approvalsForm.setReqRequstType(reqType);
		String status=approvalsForm.getSelectedFilter();
		approvalsForm.setSelectedFilter(status);
		
		String reqReqNo="";
		String reqItemNo="";
		String reqItemExist="";
		
			for(int i=0;i<selectedReq.length;i++){
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					System.out.println(selectedReq[i]);
					reqReqNo=reqReqNo+","+selectedReq[i];
					System.out.println("Item Code="+modifyItemCode[i]);
					
					String sapCode=modifyItemCode[i];
					if(sapCode.equalsIgnoreCase(""))
					{
						approvalsForm.setMessage2("Please Enter Item Code No in "+selectedReq[i]);
						
						approvalsForm.setMaterialCodeLists(reqType);
						pendingRecords(mapping, form, request, response);
						return mapping.findForward("display");
					}else{
						reqItemNo=reqItemNo+","+modifyItemCode[i];
					}
					String sapCodeExist=itemExist1[i];
					if(sapCodeExist.equalsIgnoreCase(""))
					{
						approvalsForm.setMessage2("Please Select Item Exist No in "+selectedReq[i]);
						
						approvalsForm.setMaterialCodeLists(reqType);
						pendingRecords(mapping, form, request, response);
						return mapping.findForward("display");
					}else{
						reqItemExist=reqItemExist+","+itemExist1[i];
					}
				
			}
			
			approvalsForm.setReqReqNo(reqReqNo);
			approvalsForm.setReqItemNo(reqItemNo);
			approvalsForm.setReqItemExist(reqItemExist);*/
			
			
		return mapping.findForward("display");
	}
	public ActionForward approveRequest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm approvalsForm = (MaterialsForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		NewsandMediaDao ad=new NewsandMediaDao();
		if(user == null)
		{
			approvalsForm.setMessage3("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		String selectedReq[]=approvalsForm.getSelectedRequestNo();
		System.out.println("total="+selectedReq.length);
		String reqSAPNO=request.getParameter("reqSAPNO");
		String itemCode[]=reqSAPNO.split(",");
		String test=request.getParameter("ItemExist");
		String itemExist[]=test.split(",");
	
		LinkedList itemCodeChange=new LinkedList();
		
		
		for(int i=0;i<itemCode.length;i++)
		{
			String sapCode=itemCode[i];
			int j=0;
			if(!(sapCode.equalsIgnoreCase("")))
			{
				itemCodeChange.add(sapCode);
			}
			
		}
		
		LinkedList itemStatusChange=new LinkedList();
		for(int i=0;i<itemExist.length;i++)
		{
			String itemExists=itemExist[i];
			int j=0;
			if(!(itemExists.equalsIgnoreCase("")))
			{
				itemStatusChange.add(itemExist[i]);
			}
			
		}
		String itemExist1[]=(String[])itemStatusChange.toArray(new String[itemStatusChange.size()]);
		
		
		String modifyItemCode[]=(String[])itemCodeChange.toArray(new String[itemCodeChange.size()]);
		
		int reqNoTotal=selectedReq.length;
		int itemCodeTotal=modifyItemCode.length;
		int itemExistTotal=itemExist1.length;
		
		if(reqNoTotal!=itemCodeTotal || reqNoTotal!=itemExistTotal)
		{
			approvalsForm.setMessage2("Please Ceck Item Code/Item exist");
			pendingRecords(mapping, form, request, response);
			return mapping.findForward("display");
		}
		
		
		String masterType=approvalsForm.getReqRequstType();
		String reqType=approvalsForm.getMaterialCodeLists();
		
		approvalsForm.setReqRequstType(reqType);
		String status=approvalsForm.getSelectedFilter();
		approvalsForm.setSelectedFilter(status);
		
		
			for(int i=0;i<selectedReq.length;i++){
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					System.out.println(selectedReq[i]);
					System.out.println("Item Code="+modifyItemCode[i]);
					String sapCode=modifyItemCode[i];
					if(sapCode.equalsIgnoreCase(""))
					{
						approvalsForm.setMessage2("Please Enter Item Code No in "+selectedReq[i]);
						approvalsForm.setMaterialCodeLists(reqType);
						pendingRecords(mapping, form, request, response);
						return mapping.findForward("display");
					}
					String exist=itemExist1[i];
					try{
					System.out.println(itemExist[i]);
					}catch (Exception e) {
						System.out.println("off");
					}
			}
			for(int i=0;i<selectedReq.length;i++){
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
					System.out.println(selectedReq[i]);
					System.out.println("Item Code="+modifyItemCode[i]);
					String sapCode=modifyItemCode[i];
					if(sapCode.equalsIgnoreCase(""))
					{
						approvalsForm.setMessage2("Please Enter Item Code No in "+selectedReq[i]);
						approvalsForm.setMaterialCodeLists(reqType);
						pendingRecords(mapping, form, request, response);
						return mapping.findForward("display");
					}
					try{
					System.out.println(itemExist[i]);
					}catch (Exception e) {
						System.out.println("off");
					}
					String reqId=selectedReq[i];
					String location="";
					String matGroup="";
					String matType="";
					try{
						String userRole="";
						String matDetails="select loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.Type from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
						ResultSet rsDetails=ad.selectQuery(matDetails);
						while(rsDetails.next())
						{
							location=rsDetails.getString("LOCATION_CODE");
							matGroup=rsDetails.getString("MATERIAL_GROUP_ID");
							matType=rsDetails.getString("Type");
						}
						
						String updateStatus="update All_Request set Req_Status='Approved',Comments='',Last_Approver='"+user.getEmployeeNo()+"'," +
						"Pending_Approver='',Approved_Persons='"+user.getEmployeeNo()+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master'  and Pending_Approver='"+user.getEmployeeNo()+"'";
					    int saveStatus=0;
					    saveStatus=ad.SqlExecuteUpdate(updateStatus);
					    if(saveStatus>0)
					    {
					   	 
						 
					    }
					    String sapCodeExist="";
					    try{
							String check=itemExist[i];
							if(check.equalsIgnoreCase("On"))
							{
								sapCodeExist="1";
							}
							}catch (Exception e) {
								sapCodeExist="0";
							}
		String updateGeneralMaterial="update material_code_request set	SAP_CODE_NO='"+modifyItemCode[i]+"',SAP_CODE_EXISTS='"+itemExist1[i]+"',SAP_CREATION_DATE='"+dateNow+"',SAP_CREATED_BY='"+(user.getEmployeeNo()+"-"+user.getFullName())+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";	
		int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
		System.out.println(updateMateStatus);
					}catch (Exception e) {
						e.printStackTrace();
					}
			}
		approvalsForm.setMaterialCodeLists(reqType);
		approvalsForm.setSelectedFilter("Approved");
		pendingRecords(mapping, form, request, response);
		return mapping.findForward("display");
	}
	
	
	public ActionForward pendingRecords(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm pendingForm=(MaterialsForm)form;
		EssDao ad=new EssDao();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			pendingForm.setMessage("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		String reqType=pendingForm.getMaterialCodeLists();
		String type=pendingForm.getSelectedFilter();
		try{
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE where MATERIAL_GROUP_ID in ('LC','PPC','OSE')";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			pendingForm.setMaterTypeIDList(materTypeIDList);
			pendingForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		String fromDate="";
		String toDate="";
		
		if(!pendingForm.getFromDate().equalsIgnoreCase(""))
		{	
		    fromDate=pendingForm.getFromDate();
		    toDate=pendingForm.getToDate();
		   String ay[]=fromDate.split("/");
		   fromDate=ay[2]+"-"+ay[1]+"-"+ay[0]+" "+"00:00:00.000";
		   
		   String by[]=toDate.split("/");
		   toDate=by[2]+"-"+by[1]+"-"+by[0]+" "+"23:59:00.000";
		}
		
		int totalRecords = getCountForTable(pendingForm.getSelectedFilter(),user.getEmployeeNo(),reqType);
		  int  startRecord=0;
		  int  endRecord=0;
			 if(totalRecords>10)
			 {
				 pendingForm.setTotalRecords(totalRecords);
			 startRecord=1;
			 endRecord=10;
			 pendingForm.setStartRecord(1);
			 pendingForm.setEndRecord(10);
				request.setAttribute("disablePreviousButton", "disablePreviousButton");
			 request.setAttribute("displayRecordNo", "displayRecordNo");
			 request.setAttribute("nextButton", "nextButton");
			 }else
			 {
				  startRecord=1;
				  endRecord=totalRecords;
				  pendingForm.setTotalRecords(totalRecords);
				  pendingForm.setStartRecord(1);
				  pendingForm.setEndRecord(totalRecords); 
			 }
		

		try{
		
			if(type.equalsIgnoreCase("Pending"))
			{
				request.setAttribute("approvedButtons", "approvedButtons");
				request.setAttribute("displayButton", "displayButton");
			}

			LinkedList leaveList=new LinkedList();
			String getMaterialRecords="";
			
				getMaterialRecords=" Select top "+type+" * From (SELECT ROW_NUMBER() OVER(ORDER BY all_R.Requester_Id) AS  RowNum,mat.SAP_Approved_Date,mat.SAP_Message,all_r.Req_Id,all_r.Req_Date,loc.LOCATION_CODE,all_r.type,matType.M_DESC,mat.MATERIAL_SHORT_NAME,emp.EMP_FULLNAME,mat.last_approver,mat.pending_approver," +
				" all_r.Req_Status,mat.MATERIAL_GROUP_ID,grp.STXT,mat.UNIT_OF_MEAS_ID from Location as loc, All_Request as all_r,emp_official_info as emp,material_code_request as mat,MATERIAL_TYPE as matType,MATERIAL_GROUP as grp " +
				" where all_r.Req_Type='Material Master' and all_r.Pending_Approver='"+user.getEmployeeNo()+"' and emp.PERNR=all_r.Requester_Name and mat.REQUEST_NO=all_r.Req_Id  and all_r.type='"+reqType+"' " +
				" and mat.LOCATION_ID=loc.LOCID and all_r.Req_Status='pending' and mat.MATERIAL_GROUP_ID=grp.MATERIAL_GROUP_ID and matType.MATERIAL_GROUP_ID=mat.Type and REQUEST_DATE >='"+fromDate+"' and REQUEST_DATE <='"+toDate+"'  ";
				if(!pendingForm.getMaterialGrup().equalsIgnoreCase(""))
					getMaterialRecords = getMaterialRecords+" and  mat.MATERIAL_GROUP_ID ='"+pendingForm.getMaterialGrup()+"' ";	
					getMaterialRecords=getMaterialRecords+"  and LOCATION_ID='"+pendingForm.getLocationId()+"'  )as  sub   order by Req_Id desc";	
				
				
				
				ResultSet rsMat=ad.selectQuery(getMaterialRecords);	 
				try{
		LinkedList materialList=new LinkedList();
		while(rsMat.next()){
		MaterialCodeRequestForm masterForm=new MaterialCodeRequestForm();
		masterForm.setRequestNumber(rsMat.getString("Req_Id"));
		
		masterForm.setRequestDate(rsMat.getString("Req_Date"));
		masterForm.setLocationCode(rsMat.getString("LOCATION_CODE"));
		masterForm.setMaterialDesc(rsMat.getString("type")+"-"+rsMat.getString("M_DESC"));
		masterForm.setMaterailType(rsMat.getString("type"));
		masterForm.setEmployeeName(rsMat.getString("EMP_FULLNAME"));
		
		masterForm.setMaterialName(rsMat.getString("MATERIAL_SHORT_NAME"));
		masterForm.setMaterialGroupName(rsMat.getString("STXT"));
		masterForm.setUom(rsMat.getString("UNIT_OF_MEAS_ID"));
		masterForm.setLastApprover(rsMat.getString("last_approver"));
		masterForm.setPendingApprover(rsMat.getString("pending_approver"));
		masterForm.setItemExist("off");
		String approveStatus=rsMat.getString("Req_Status");
		if(approveStatus.equalsIgnoreCase("Approved")  )
		{
			masterForm.setApproveStatus("Completed");
		}else{
		masterForm.setApproveStatus(rsMat.getString("Req_Status"));
		}
		masterForm.setMaterialGroup(rsMat.getString("MATERIAL_GROUP_ID"));
		if(type.equalsIgnoreCase("Approved") || type.equalsIgnoreCase("All") )
		{
			masterForm.setSapCodeNo(rsMat.getString("SAP_CODE_NO"));
			String sapExistStatus=rsMat.getString("SAP_CODE_EXISTS");
			if( sapExistStatus!=null && sapExistStatus.equalsIgnoreCase("1") )
			masterForm.setItemCodeExist("on");
		
			String sapCreatDate=rsMat.getString("SAP_CREATION_DATE");
			if(sapCreatDate!=null)
			{
			masterForm.setSapCreationDate(rsMat.getString("SAP_CREATION_DATE"));
			}else{
				masterForm.setSapCreationDate("");
			}
			
			
		}
		masterForm.setSelectedFilter(type);
		
		masterForm.setCreatedOn(rsMat.getString("SAP_Approved_Date"));
		masterForm.setMessage(rsMat.getString("SAP_Message"));
		
		materialList.add(masterForm);
		
		}
	
	if(materialList.size()>0)
	{	
	request.setAttribute("materialList", materialList);
	request.setAttribute("Material List", "Material List");
	}
	if(materialList.size()==0){
		request.setAttribute("no Material Master records", "no Material Master records");
		pendingForm.setMessage3("No Records Found..");
	}
	if(materialList.size()>0){
		if(type.equalsIgnoreCase("Pending")){
			request.setAttribute("displayButton", "displayButton");
			}
		}
}catch (Exception e) {
	e.printStackTrace();
}
		
	 
		
		}catch (Exception e) {
			e.printStackTrace();
		}

		
		ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try { 
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		pendingForm.setLocationIdList(locationList);
		pendingForm.setLocationLabelList(locationLabelList);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		try {
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("STXT"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pendingForm.setMaterGroupIDList(materGroupIDList);
		pendingForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		
		
		return mapping.findForward("display");
	}
	public int getCountForTable(String reqStatus, String fullName, String reqType){
     
		
    	int rowCount = 0;
    	MainDao exeQry = new MainDao();
    	int appCount=0;
    	
    	String checkApp="select * from Material_Approvers where Material_Type='"+reqType+"' and Approver_Id='"+fullName+"' and Role='Creator'";
    	ResultSet rs=exeQry.selectQuery(checkApp);
    	try{
    		while(rs.next())
    		{
    		appCount=rs.getInt(1);
    		}
    	}catch (Exception e) {
			e.printStackTrace();
		}
    	if(appCount>0)
    	{
    	String reqList="select count(*) from All_Request where Pending_approver='"+fullName+"' and Req_Status='Pending' and type='"+reqType+"'";
    	if(reqStatus.equalsIgnoreCase("Approved")){
			reqList="select count(*) from All_Request where Last_Approver='"+fullName+"' and Req_Status='Approved' and type='"+reqType+"'";
		}
		else if(reqStatus.equalsIgnoreCase("Rejected")){
			reqList="select count(*) from All_Request where Req_Status='"+reqStatus+"' and Last_Approver='"+fullName+"'  and type='"+reqType+"' ";
		}
		else if(reqStatus.equalsIgnoreCase("All")){
			reqList="select count(*) from All_Request where (Last_Approver ='"+fullName+"' or Pending_Approver = '"+fullName+"')  and type='"+reqType+"'";
		}
    	try
    	{
    		ResultSet countrs=exeQry.selectQuery(reqList);
    		while(countrs.next()) {
    			rowCount=Integer.parseInt(countrs.getString(1));
    		}
    	}
    	catch(SQLException se){
    		System.out.println("Exception @ getting count");
    		se.printStackTrace();
    	}
	}
    	return rowCount;
    
	}
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		MaterialsForm masterForm=(MaterialsForm)form;
		EssDao ad=new EssDao();
		try{
			LinkedList materTypeIDList=new LinkedList();
			LinkedList materialTypeIdValueList=new LinkedList();
			String getMaterials="select * from MATERIAL_TYPE where MATERIAL_GROUP_ID in ('LC','PPC','OSE')";
			ResultSet rsMaterial=ad.selectQuery(getMaterials);
			while(rsMaterial.next())
			{
				materTypeIDList.add(rsMaterial.getString("MATERIAL_GROUP_ID"));
				materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
			}
			masterForm.setMaterTypeIDList(materTypeIDList);
			masterForm.setMaterialTypeIdValueList(materialTypeIdValueList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		ResultSet rs11 = ad.selectQuery("select LOCID,LOCNAME,location_code from location ");
		ArrayList locationList=new ArrayList();
		ArrayList locationLabelList=new ArrayList();
		try { 
			while(rs11.next()) {
				locationList.add(rs11.getString("LOCID"));
				locationLabelList.add(rs11.getString("location_code")+"-"+rs11.getString("LOCNAME"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		masterForm.setLocationIdList(locationList);
		masterForm.setLocationLabelList(locationLabelList);
		
		LinkedList materGroupIDList=new LinkedList();
		LinkedList materialGroupIdValueList=new LinkedList();
		
		String getMaterialGroup="select * from MATERIAL_GROUP order by STXT";
		ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
		try {
			while(rsMaterialGroup.next())
			{
				materGroupIDList.add(rsMaterialGroup.getString("STXT"));
				materialGroupIdValueList.add(rsMaterialGroup.getString("STXT")+" - "+rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		masterForm.setMaterGroupIDList(materGroupIDList);
		masterForm.setMaterialGroupIdValueList(materialGroupIdValueList);
		
		
		
		
		
		return mapping.findForward("display");
	}
	
	
	public ActionForward StatusChangeMaterialrecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		MaterialsForm statusForm=(MaterialsForm) form;
		EssDao ad=new EssDao();
		LinkedList list = new LinkedList();
		
	
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			statusForm.setAppMessage("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		String reqId = request.getParameter("reqId");
		MainDao exeQry = new MainDao();
		String reqType = statusForm.getReqRequstType();
		statusForm.setRequestType(reqType);
		String reqStatus = request.getParameter("status");
		
		 reqType = request.getParameter("reqType");
		 
		String filterBy=statusForm.getSelectedFilter();
		statusForm.setSelectedFilter(filterBy);
	statusForm.setReqRequstType(reqType);
	int totalRecords=statusForm.getTotalRecords();//21
	int startRecord=statusForm.getStartRecord();//11
	int endRecord=statusForm.getEndRecord();	
	
	
	statusForm.setTotalRecords(totalRecords);
	statusForm.setStartRecord(startRecord);
	statusForm.setEndRecord(endRecord);
		String comments = statusForm.getRemark();
		
		LinkedList reqDetails = new LinkedList();
		reqDetails.add(0, reqId);
		reqDetails.add(1, reqType);
		//User Informtion
		String lcode = user.getPlantId();
		int user_Id = user.getId();
		String uName = user.getUserName();
		String appName = uName;
		int rowCount= 0;
		System.out.println(reqType);
		//updateStatus("Approved", reqType,reqId);
		//get the total Approvers
		rowCount = getCount("Approvers_Details", reqType);
		//check and get next approver
		

		
		try{		
		
			
	
			
			if(reqType.equalsIgnoreCase("Material Master"))
			{
				String rejected_flag="";
				String flag="select rejected_flag from  material_code_request where REQUEST_NO='"+reqId+"'";
				ResultSet rs1 =ad.selectQuery(flag);
				while(rs1.next())
				{
					 rejected_flag=rs1.getString("rejected_flag");
				}
				
					if(rejected_flag!=null)
					{
						if(rejected_flag.equalsIgnoreCase("yes"))
							request.setAttribute("rejectedFLag", "rejectedFLag");
					}
				
				
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.SSS ");
					String dateNow = ft.format(dNow);
				String location="";
				String matGroup="";
				String approver="";
				String parllelAppr1="";
				String parllelAppr2="";
				String reqDate="";
				String reqesterID="";
				String requestNo="";
				String matType="";
				matGroup=request.getParameter("matGroup");
				int priorityCout=0;
				int checkStatus=0;
				String userRole=statusForm.getUserRole();
				String matDetails="select loc.LOCATION_CODE,mat.Type from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
				ResultSet rsDetails=ad.selectQuery(matDetails);
				while(rsDetails.next())
				{
					location=rsDetails.getString("LOCATION_CODE");
					matType=rsDetails.getString("Type");
				}
				String test="";
				String getProrityCout="select max(Priority) from Material_Approvers where  Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='"+matGroup+"'";
				ResultSet rsCout=ad.selectQuery(getProrityCout);
				while(rsCout.next())
				{
					priorityCout=rsCout.getInt(1);
					test=rsCout.getString(1);
				}
				if(test==null){
					getProrityCout="select max(Priority) from Material_Approvers where  Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group=''";
					rsCout=ad.selectQuery(getProrityCout);
					while(rsCout.next())
					{
						priorityCout=rsCout.getInt(1);
					}
				}
				
				checkStatus=0;
				int apprverPriority=0;
				String getApproverDetails="select * from Material_Approvers where  Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and (Approver_Id='"+user.getEmployeeNo()+"' or Parllel_Approver_1='"+user.getEmployeeNo()+"' or Parllel_Approver_2='"+user.getEmployeeNo()+"') ";
				ResultSet rsAppDet=ad.selectQuery(getApproverDetails);
				while(rsAppDet.next())
				{
					checkStatus=1;
					apprverPriority=rsAppDet.getInt("Priority");
					approver=rsAppDet.getString("Approver_Id");
					parllelAppr1=rsAppDet.getString("Parllel_Approver_1");
					parllelAppr2=rsAppDet.getString("Parllel_Approver_2");
				}
				if(checkStatus==0)
				{
					getApproverDetails="select * from Material_Approvers where  Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='' and (Approver_Id='"+user.getEmployeeNo()+"' or Parllel_Approver_1='"+user.getEmployeeNo()+"' or Parllel_Approver_2='"+user.getEmployeeNo()+"') ";
					rsAppDet=ad.selectQuery(getApproverDetails);
					while(rsAppDet.next())
					{
					
						apprverPriority=rsAppDet.getInt("Priority");
						approver=rsAppDet.getString("Approver_Id");
						parllelAppr1=rsAppDet.getString("Parllel_Approver_1");
						parllelAppr2=rsAppDet.getString("Parllel_Approver_2");
					}
				}
				if(reqStatus.equalsIgnoreCase("Approve")){
				if(priorityCout==apprverPriority)
				{
					//last approver creator
					String updateStatus="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+user.getEmployeeNo()+"'," +
					"Pending_Approver='',Approved_Persons='"+user.getEmployeeNo()+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+user.getEmployeeNo()+"'";
				    int saveStatus=0;
				    saveStatus=ad.SqlExecuteUpdate(updateStatus);
				    
				    if(!(parllelAppr1.equalsIgnoreCase("")))
				    {
				    	 String  updateStatusForparllelAppr="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr1+"'," +
							"Pending_Approver='',Approved_Persons='"+parllelAppr1+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr1+"'";
						    ad.SqlExecuteUpdate(updateStatusForparllelAppr); 
				    }
				    if(!(parllelAppr2.equalsIgnoreCase("")))
				    {
				    	 String  updateStatusForparllelAppr="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr2+"'," +
							"Pending_Approver='',Approved_Persons='"+parllelAppr2+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr2+"'";
						    ad.SqlExecuteUpdate(updateStatusForparllelAppr); 
				    }	
				    if(saveStatus>0)
				    {
				    	statusForm.setMessage2("Code created against request no. '"+reqId+"' successfull");
				    	 String sapCreationDate=statusForm.getSapCreationDate();
						  String b[]=sapCreationDate.split("/");
						  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
						  
						  String sapCodeExist="";
						  String sapCodeexistNo="";
						  
						  sapCodeExist=statusForm.getSapCodeExists();
						  sapCodeexistNo=statusForm.getSapCodeExistsNo();
						  if(sapCodeExist!=null){
							  statusForm.setSapCodeExists("true");
						  }
						  if(sapCodeexistNo!=null){
							  statusForm.setSapCodeExists("false");
						  }
						  
						  if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("ZLAB")
									||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
						     {
						  
						  String updateGeneralMaterial="update material_code_request set STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
							"PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedJustification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"'," +
							"REQUESTED_BY='"+statusForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"'," +
									"MODIFIED_BY='"+user.getEmployeeNo()+"',SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";	 
						  int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
						  System.out.println(updateMateStatus);
						     }
						  if(matType.equalsIgnoreCase("ZPSR"))
						  {
							  String updateGeneralMaterial="update material_code_request set " +
								 "STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
								"PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"'," +
								"APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',MATERIAL_USED_IN='"+statusForm.getMaterialUsedIn()+"'," +
								"IS_EQUIPMENT='"+statusForm.getIsEquipment()+"',IS_SPARE='"+statusForm.getIsSpare()+"',IS_NEW_Equipment='"+statusForm.getIsNewEquipment()+"',IS_NEW_Furniture='"+statusForm.getIsItNewFurniture()+"'," +
										"IS_NEW_Facility='"+statusForm.getIsNewEquipment()+"',IS_Spare_required='"+statusForm.getIsSpareNewEquipment()+"',EQUIPMENT_NAME='"+statusForm.getEquipmentName()+"',EQUIPMENT_MAKE='"+statusForm.getEquipmentMake()+"',Component_MAKE='"+statusForm.getComponentMake()+"',OEM_PartNo='"+statusForm.getOemPartNo()+"'," +
								"PR_NUMBER='"+statusForm.getPrNumber()+"',PO_NUMBER='"+statusForm.getPoNumber()+"',UTILIZING_DEPT='"+statusForm.getUtilizingDept()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedJustification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"'," +
								"REQUESTED_BY='"+statusForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"', SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";	 
								
							  int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
							  System.out.println(updateMateStatus);
					            
						  }
						  
						  if(matType.equalsIgnoreCase("ZPPC"))
						  {
							  String updatQuery="update material_code_request set LOCATION_ID='"+statusForm.getLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"'," +
								"MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',DIVISION_ID='"+statusForm.getDivisionId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',is_asset='"+statusForm.getIsAsset()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"'," +
								"VALUATION_CLASS='"+statusForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedSpecification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"',IS_SAS_FORM_AVAILABLE='"+statusForm.getIsSASFormAvailable()+"'," +
								"MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"', SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";
											 
							  int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
					            System.out.println(updatQuery);
					            System.out.println(updateMateStatus);
						  }
						  
						  if(matType.equalsIgnoreCase("HALB"))
						  {
							  String updateSemiFinished="update material_code_request set STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
						 		"UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+statusForm.getStandardBatchSize()+"',BATCH_CODE='"+statusForm.getBatchCode()+"',TARGET_WEIGHT='"+statusForm.getTargetWeight()+"',PROD_INSP_MEMO='"+statusForm.getProdInspMemo()+"'," +
						 		"WEIGHT_UOM='"+statusForm.getWeightUOM()+"',RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',MODIFIED_DATE='"+dateNow+"'," +
						 		"MODIFIED_BY='"+user.getEmployeeNo()+"',PACK_SIZE='"+statusForm.getPackSize()+"',SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+statusForm.getRequestNo()+"'";
							  int updateMateStatus=ad.SqlExecuteUpdate(updateSemiFinished);
						  }
						  if(matType.equalsIgnoreCase("VERP"))
							{
					 String updatQuery="update material_code_request set STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'" +
					",MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',IS_DMF_MATERIAL='"+statusForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+statusForm.getDmfGradeId()+"',COS_GRADE_AND_NO='"+statusForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+statusForm.getAdditionalTest()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"'," +
					"TO_BE_USED_IN_PRODUCTS='"+statusForm.getToBeUsedInProducts()+"'" +",IS_VENDOR_SPECIFIC_MATERIAL='"+statusForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+statusForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+statusForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+statusForm.getTempCondition()+"',STORAGE_CONDITION='"+statusForm.getStorageCondition()+"'," +
					"RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',DUTY_ELEMENT='"+statusForm.getDutyElement()+"',PACKING_MATERIAL_GROUP='"+statusForm.getPackageMaterialGroup()+"',Type_Of_Material='"+statusForm.getTypeOfMaterial()+"',IS_ARTWORK_REVISION='"+statusForm.getIsArtworkRevision()+"' ,EXISTING_SAP_ITEM_CODE='"+statusForm.getExistingSAPItemCode()+"'" +
					" ,APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',ARTWORK_NO='"+statusForm.getArtworkNo()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"'," +
					"PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"'," +
					"SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";
								 
			    		
			    		int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
			            System.out.println(updatQuery);
			            System.out.println(updateMateStatus);
				       }
					  if(matType.equalsIgnoreCase("FERT") ||matType.equalsIgnoreCase("HAWA"))
						{
						  String updateFinishedProduct="update material_code_request set MANUFACTURED_AT='"+statusForm+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'" +
							",MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+statusForm.getStandardBatchSize()+"',BATCH_CODE='"+statusForm.getBatchCode()+"',SALEABLE_OR_SAMPLE='"+statusForm.getSaleableOrSample()+"',DOMESTIC_OR_EXPORTS='"+statusForm.getDomesticOrExports()+"',SALES_PACK_ID='"+statusForm.getSalesPackId()+"',PACK_TYPE_ID='"+statusForm.getPackTypeId()+"',SALES_UNIT_OF_MEAS_ID='"+statusForm.getSalesUnitOfMeaseurement()+"',DIVISION_ID='"+statusForm.getDivisionId()+"',THERAPEUTIC_SEGMENT_ID='"+statusForm.getTherapeuticSegmentID()+"'," +
							"BRAND_ID='"+statusForm.getBrandID()+"',STRENGTH_ID='"+statusForm.getSrengthId()+"',GENERIC_NAME='"+statusForm.getGenericName()+"',PROD_INSP_MEMO='"+statusForm.getProdInspMemo()+"',GROSS_WEIGHT='"+statusForm.getGrossWeight()+"',NET_WEIGHT='"+statusForm.getNetWeight()+"',WEIGHT_UOM='"+statusForm.getWeightUOM()+"',DIMENSION='"+statusForm.getDimension()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"'," +
							" MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";	 
						  int updateMateStatus=ad.SqlExecuteUpdate(updateFinishedProduct);
						}
						if(matType.equalsIgnoreCase("ROH"))
						{
			    		String updatQuery="update material_code_request set  STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"'," +
						"MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',PHARMACOP_NAME='"+statusForm.getPharmacopName()+"',PHARMACOP_GRADE='"+statusForm.getPharmacopGrade()+"',GENERIC_NAME='"+statusForm.getGenericName()+"',SYNONYM='"+statusForm.getSynonym()+"',PHARMACOP_SPECIFICATION='"+statusForm.getPharmacopSpecification()+"'" +
						",IS_DMF_MATERIAL='"+statusForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+statusForm.getDmfGradeId()+"',MATERIAL_GRADE='"+statusForm.getMaterialGrade()+"',COS_GRADE_AND_NO='"+statusForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+statusForm.getAdditionalTest()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"'" +
						",TO_BE_USED_IN_PRODUCTS='"+statusForm.getToBeUsedInProducts()+"',IS_VENDOR_SPECIFIC_MATERIAL='"+statusForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+statusForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+statusForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+statusForm.getTempCondition()+"',STORAGE_CONDITION='"+statusForm.getStorageCondition()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfLifeType()+"',DUTY_ELEMENT='"+statusForm.getDutyElement()+"'" +
						",RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"'" +
						",MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',Approve_Type='Approved',last_approver='"+user.getFullName()+"',pending_approver='No',rejected_flag='' where REQUEST_NO='"+reqId+"'";
			            int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
						}
				    }
				}else{
				    String nextAppr="";
				    String nextPar1="";
				    String nextPar2="";
				    checkStatus=0;
					String getNextApprover="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2 from  Material_Approvers where Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and Priority='"+(apprverPriority+1)+"'";
					ResultSet rsNextApp=ad.selectQuery(getNextApprover);
					while(rsNextApp.next())
					{  
						checkStatus=1;
						nextAppr=rsNextApp.getString("Approver_Id");
						nextPar1=rsNextApp.getString("Parllel_Approver_1");
						nextPar2=rsNextApp.getString("Parllel_Approver_2");
					}
					if(checkStatus==0)
					{
						getNextApprover="select Approver_Id,Parllel_Approver_1,Parllel_Approver_2 from  Material_Approvers where Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='' and Priority='"+(apprverPriority+1)+"'";
						rsNextApp=ad.selectQuery(getNextApprover);
						while(rsNextApp.next())
						{  
							checkStatus=1;
							nextAppr=rsNextApp.getString("Approver_Id");
							nextPar1=rsNextApp.getString("Parllel_Approver_1");
							nextPar2=rsNextApp.getString("Parllel_Approver_2");
						}
					}
				String empNo=user.getEmployeeNo();
					if(empNo.equalsIgnoreCase(approver))
					{
						String updateStatus="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+user.getEmployeeNo()+"'," +
						"Pending_Approver='',Approved_Persons='"+user.getEmployeeNo()+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+user.getEmployeeNo()+"'";
					    int saveStatus=0;
					    saveStatus=ad.SqlExecuteUpdate(updateStatus);
					    
					    if(!(parllelAppr1.equalsIgnoreCase("")))
					    {
					    	 String  updateStatusForparllelAppr="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr1+"'," +
								"Pending_Approver='',Approved_Persons='"+parllelAppr1+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr1+"'";
							    ad.SqlExecuteUpdate(updateStatusForparllelAppr); 
					    }
					    if(!(parllelAppr2.equalsIgnoreCase("")))
					    {
					    	 String  updateStatusForparllelAppr="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr2+"'," +
								"Pending_Approver='',Approved_Persons='"+parllelAppr2+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr2+"'";
							    ad.SqlExecuteUpdate(updateStatusForparllelAppr); 
					    }
					    if(saveStatus>0)
					    {
					    	 statusForm.setMessage2("Request Is Approved Successfully");
					    	 
					    	String recordStatus="select * from All_Request where Req_Id='"+reqId+"' and Approved_Persons='"+user.getEmployeeNo()+"'";
					    	ResultSet rsRecDet=ad.selectQuery(recordStatus);
					    	while(rsRecDet.next())
					    	{
					    		requestNo=rsRecDet.getString("Requester_Name");
					    		reqDate=rsRecDet.getString("Req_Date");
					    		reqesterID=rsRecDet.getString("Requester_Name");
					    	}
					    	System.out.println(userRole);
					    	if(userRole.equalsIgnoreCase("Accounts"))
					    	{
					    	//update valuation class
					    	String updateMaterial="update material_code_request set VALUATION_CLASS='"+statusForm.getValuationClass()+"',last_approver='"+user.getFullName()+"',pending_approver='"+nextAppr+"' where REQUEST_NO='"+reqId+"'";
					    	int check=ad.SqlExecuteUpdate(updateMaterial);
					    	
					    		
					    		
					    	}
					    	if(userRole.equalsIgnoreCase("Creator"))
					    	{
					    		 String sapCreationDate=statusForm.getSapCreationDate();
								  String b[]=sapCreationDate.split("/");
								  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
					    	
					    		String updatQuery="update material_code_request set  STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"'," +
								"MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',PHARMACOP_NAME='"+statusForm.getPharmacopName()+"',PHARMACOP_GRADE='"+statusForm.getPharmacopGrade()+"',GENERIC_NAME='"+statusForm.getGenericName()+"',SYNONYM='"+statusForm.getSynonym()+"',PHARMACOP_SPECIFICATION='"+statusForm.getPharmacopSpecification()+"'" +
								",IS_DMF_MATERIAL='"+statusForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+statusForm.getDmfGradeId()+"',MATERIAL_GRADE='"+statusForm.getMaterialGrade()+"',COS_GRADE_AND_NO='"+statusForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+statusForm.getAdditionalTest()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"'" +
								",TO_BE_USED_IN_PRODUCTS='"+statusForm.getToBeUsedInProducts()+"',IS_VENDOR_SPECIFIC_MATERIAL='"+statusForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+statusForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+statusForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+statusForm.getTempCondition()+"',STORAGE_CONDITION='"+statusForm.getStorageCondition()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfLifeType()+"',DUTY_ELEMENT='"+statusForm.getDutyElement()+"'" +
								",RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"'" +
								",MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',SAP_CODE_NO='"+statusForm.getSapCodeNo()+"',SAP_CODE_EXISTS='"+statusForm.getSapCodeExists()+"',SAP_CREATION_DATE='"+sapCreationDate+"',SAP_CREATED_BY='"+statusForm.getSapCreatedBy()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";
					    	}
					    	
					    	//update last Approver and Pending Approver
					    	//GET pending approvers
					    	if(!(userRole.equalsIgnoreCase("Creator")))
					    	{
					    	checkStatus=0;
					    	String pendingApprovers="";
					    	String nextApprovers="";
					    	getNextApprover="select emp.EMP_FULLNAME, Approver_Id,Parllel_Approver_1,Parllel_Approver_2 from  Material_Approvers as mat,emp_official_info AS emp where " +
							"Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mat.Approver_Id=emp.PERNR and Priority>'"+apprverPriority+"' order by Priority";
					ResultSet rsPendignApp=ad.selectQuery(getNextApprover);
					while(rsPendignApp.next())
					{  
						checkStatus=1;
						pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
					}
					if(checkStatus==0)
					{
						getNextApprover="select emp.EMP_FULLNAME, Approver_Id,Parllel_Approver_1,Parllel_Approver_2 from  Material_Approvers as mat,emp_official_info AS emp where " +
								"Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='' and mat.Approver_Id=emp.PERNR and Priority>'"+apprverPriority+"' order by Priority";
						rsPendignApp=ad.selectQuery(getNextApprover);
						while(rsPendignApp.next())
						{  
							checkStatus=1;
							pendingApprovers=pendingApprovers+rsPendignApp.getString("EMP_FULLNAME")+" , ";
						}
					}
							if(!(pendingApprovers.equalsIgnoreCase(""))){
								pendingApprovers=pendingApprovers.substring(0, (pendingApprovers.length()-2));	
							}
							
							String updateMaterial="update material_code_request set last_approver='"+user.getFullName()+"',pending_approver='"+pendingApprovers+"' where REQUEST_NO='"+reqId+"'";
					    	int check=ad.SqlExecuteUpdate(updateMaterial);
					    	}
					    	// insert record to next approver
					    	String insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
					    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextAppr+"','No','"+reqesterID+"','','"+matType+"')";
					    	ad.SqlExecuteUpdate(insertAllRequest);
					    	if(!(nextPar1.equalsIgnoreCase(""))){
					    		 insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
						    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextPar1+"','No','"+reqesterID+"','','"+matType+"')";
						    	ad.SqlExecuteUpdate(insertAllRequest);
						    
					    	}
					    	if(!(nextPar2.equalsIgnoreCase(""))){
					    		 insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
						    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextPar2+"','No','"+reqesterID+"','','"+matType+"')";
						    	ad.SqlExecuteUpdate(insertAllRequest);
						    
					    	}
					    	
					    }else{
					    	statusForm.setMessage("Error..Request Is Not Approved");
					    }
						
					}if(!(parllelAppr1.equalsIgnoreCase(""))){
						
						if(empNo.equalsIgnoreCase(parllelAppr1)){
							String updateStatus="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+user.getEmployeeNo()+"'," +
							"Pending_Approver='',Approved_Persons='"+user.getEmployeeNo()+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+user.getEmployeeNo()+"'";
						    int saveStatus=0;
						    saveStatus=ad.SqlExecuteUpdate(updateStatus);
						    
						    String updateStatusForApprover="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+approver+"'," +
							"Pending_Approver='',Approved_Persons='"+approver+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+approver+"'";
						    ad.SqlExecuteUpdate(updateStatusForApprover); 
						    
						    if(!(parllelAppr2.equalsIgnoreCase("")))
						    {
						    	  updateStatusForApprover="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr2+"'," +
									"Pending_Approver='',Approved_Persons='"+parllelAppr2+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr2+"'";
								    ad.SqlExecuteUpdate(updateStatusForApprover); 
						    }
						   
						    if(saveStatus>0)
						    {
						    	statusForm.setMessage2("Request Is Approved Successfully");
						    	String recordStatus="select * from All_Request where Req_Id='"+reqId+"' and Approved_Persons='"+user.getEmployeeNo()+"'";
						    	ResultSet rsRecDet=ad.selectQuery(recordStatus);
						    	while(rsRecDet.next())
						    	{
						    		requestNo=rsRecDet.getString("Requester_Name");
						    		reqDate=rsRecDet.getString("Req_Date");
						    		reqesterID=rsRecDet.getString("Requester_Name");
						    	}
						    	
						    	// insert record to next approver
						    	String insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
						    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextAppr+"','No','"+reqesterID+"','','"+matType+"')";
						    	ad.SqlExecuteUpdate(insertAllRequest);
						    	if(!(nextPar1.equalsIgnoreCase(""))){
						    		 insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
							    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextPar1+"','No','"+reqesterID+"','','"+matType+"')";
							    	ad.SqlExecuteUpdate(insertAllRequest);
							    
						    	}
						    	if(!(nextPar2.equalsIgnoreCase(""))){
						    		 insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
							    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextPar2+"','No','"+reqesterID+"','','"+matType+"')";
							    	ad.SqlExecuteUpdate(insertAllRequest);
							    
						    	}
						    	
						    	
						    }else{
						    	statusForm.setMessage("Error..Request Is Not Approved");
						    }
							
						}
						
						
					}
					if(!(parllelAppr2.equalsIgnoreCase(""))){

						
						if(empNo.equalsIgnoreCase(parllelAppr2)){
							String updateStatus="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+user.getEmployeeNo()+"'," +
							"Pending_Approver='',Approved_Persons='"+user.getEmployeeNo()+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+user.getEmployeeNo()+"'";
						    int saveStatus=0;
						    saveStatus=ad.SqlExecuteUpdate(updateStatus);
						    String updateStatusForApprover="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+approver+"'," +
							"Pending_Approver='',Approved_Persons='"+approver+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+approver+"'";
						    ad.SqlExecuteUpdate(updateStatusForApprover); 
						    
						    if(!(parllelAppr1.equalsIgnoreCase("")))
						    {
						    	  updateStatusForApprover="update All_Request set Req_Status='Approved',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr1+"'," +
									"Pending_Approver='',Approved_Persons='"+parllelAppr1+"',approved_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr1+"'";
								    ad.SqlExecuteUpdate(updateStatusForApprover); 
						    }
						    if(saveStatus>0)
						    {
						    	 
						    	String recordStatus="select * from All_Request where Req_Id='"+reqId+"' and Approved_Persons='"+user.getEmployeeNo()+"'";
						    	ResultSet rsRecDet=ad.selectQuery(recordStatus);
						    	while(rsRecDet.next())
						    	{
						    		requestNo=rsRecDet.getString("Requester_Name");
						    		reqDate=rsRecDet.getString("Req_Date");
						    		reqesterID=rsRecDet.getString("Requester_Name");
						    	}
						    	
						    	// insert record to next approver
						    	String insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
						    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextAppr+"','No','"+reqesterID+"','','"+matType+"')";
						    	ad.SqlExecuteUpdate(insertAllRequest);
						    	if(!(nextPar1.equalsIgnoreCase(""))){
						    		 insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
							    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextPar1+"','No','"+reqesterID+"','','"+matType+"')";
							    	ad.SqlExecuteUpdate(insertAllRequest);
							    
						    	}
						    	if(!(nextPar2.equalsIgnoreCase(""))){
						    		 insertAllRequest="insert into All_Request (Req_Id, Req_Type, Requester_Name, Req_Date, Req_Status, Last_Approver, Pending_Approver,Approved_Persons,Requester_Id,Comments,type)" +
							    	" values('"+reqId+"','Material Master','"+requestNo+"','"+reqDate+"','Pending','No','"+nextPar2+"','No','"+reqesterID+"','','"+matType+"')";
							    	ad.SqlExecuteUpdate(insertAllRequest);
							    
						    	}
						    	
						    	
						    }else{
						    	statusForm.setMessage("Error..Request Is Not Approved");
						    }
							
						}
					}
			     
				}
					
				//set reject buttons  	
					
				
					
					
					
				}//material reject
				else{
					String updateStatus="update All_Request set Req_Status='Rejected',Comments='"+statusForm.getComments()+"',Last_Approver='"+user.getEmployeeNo()+"'," +
					"Pending_Approver='',Approved_Persons='"+user.getEmployeeNo()+"',rejected_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+user.getEmployeeNo()+"'";
				    int saveStatus=0;
				    saveStatus=ad.SqlExecuteUpdate(updateStatus);
				    if(!(parllelAppr1.equalsIgnoreCase("")))
				    {
				    	 String  updateStatusForparllelAppr="update All_Request set Req_Status='Rejected',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr1+"'," +
							"Pending_Approver='',Approved_Persons='"+parllelAppr1+"',rejected_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr1+"'";
				    	 saveStatus=ad.SqlExecuteUpdate(updateStatusForparllelAppr); 
				    }
				    if(!(parllelAppr2.equalsIgnoreCase("")))
				    {
				    	 String  updateStatusForparllelAppr="update All_Request set Req_Status='Rejected',Comments='"+statusForm.getComments()+"',Last_Approver='"+parllelAppr2+"'," +
							"Pending_Approver='',Approved_Persons='"+parllelAppr2+"',rejected_date='"+dateNow+"' where Req_Id='"+reqId+"' and Req_Type='Material Master' and Pending_Approver='"+parllelAppr2+"'";
				    	 saveStatus=ad.SqlExecuteUpdate(updateStatusForparllelAppr); 
				    }
					if(saveStatus>0){
						statusForm.setMessage2("Request has been rejected successfully");
						
						String updateMaterial="update material_code_request set Approve_Type='Rejected',MODIFIED_BY='"+user.getEmployeeNo()+"',rejected_flag='',last_approver='"+user.getFullName()+"',pending_approver='No'  where REQUEST_NO='"+reqId+"'";
						ad.SqlExecuteUpdate(updateMaterial);
						
					}
					if(priorityCout==apprverPriority){
						
						
				    	 String sapCreationDate=statusForm.getSapCreationDate();
						  String b[]=sapCreationDate.split("/");
						  sapCreationDate=b[2]+"-"+b[1]+"-"+b[0];
						  
						  String sapCodeExist="";
						  String sapCodeexistNo="";
						  
						  sapCodeExist=statusForm.getSapCodeExists();
						  sapCodeexistNo=statusForm.getSapCodeExistsNo();
						  if(sapCodeExist!=null){
							  statusForm.setSapCodeExists("true");
						  }
						  if(sapCodeexistNo!=null){
							  statusForm.setSapCodeExists("false");
						  }
						  if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("ZLAB")
									||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
						     {
						  
						  String updateGeneralMaterial="update material_code_request set LOCATION_ID='"+statusForm.getLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
							"MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',is_asset='"+statusForm.getIsAsset()+"',UTILIZING_DEPT='"+statusForm.getUtilizingDept()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedJustification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"'," +
							"REQUESTED_BY='"+statusForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"'," +
									"MODIFIED_BY='"+user.getEmployeeNo()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";	 
						  int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
						  System.out.println(updateMateStatus);
						     }
						  if(matType.equalsIgnoreCase("ZPSR"))
						  {
							  String updateGeneralMaterial="update material_code_request set " +
								 "STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
								"PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"'," +
								"APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',MATERIAL_USED_IN='"+statusForm.getMaterialUsedIn()+"'," +
								"IS_EQUIPMENT='"+statusForm.getIsEquipment()+"',IS_SPARE='"+statusForm.getIsSpare()+"',IS_NEW_Equipment='"+statusForm.getIsNewEquipment()+"',IS_NEW_Furniture='"+statusForm.getIsItNewFurniture()+"'," +
										"IS_NEW_Facility='"+statusForm.getIsNewEquipment()+"',IS_Spare_required='"+statusForm.getIsSpareNewEquipment()+"',EQUIPMENT_NAME='"+statusForm.getEquipmentName()+"',EQUIPMENT_MAKE='"+statusForm.getEquipmentMake()+"',Component_MAKE='"+statusForm.getComponentMake()+"',OEM_PartNo='"+statusForm.getOemPartNo()+"'," +
								"PR_NUMBER='"+statusForm.getPrNumber()+"',PO_NUMBER='"+statusForm.getPoNumber()+"',UTILIZING_DEPT='"+statusForm.getUtilizingDept()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedJustification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"'," +
								"REQUESTED_BY='"+statusForm.getRequestedBy()+"',MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";	 
								
							  int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
							  System.out.println(updateMateStatus);
					            
						  }
						  if(matType.equalsIgnoreCase("ZPPC"))
						  {
							  String updatQuery="update material_code_request set LOCATION_ID='"+statusForm.getLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"'," +
								"MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',DIVISION_ID='"+statusForm.getDivisionId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',is_asset='"+statusForm.getIsAsset()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"'," +
								"VALUATION_CLASS='"+statusForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedSpecification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"',IS_SAS_FORM_AVAILABLE='"+statusForm.getIsSASFormAvailable()+"'," +
								"MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";
											 
							  int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
						  }
						  if(matType.equalsIgnoreCase("VERP"))
							{
					 String updatQuery="update material_code_request set LOCATION_ID='"+statusForm.getLocationId()+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'" +
					",MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',IS_DMF_MATERIAL='"+statusForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+statusForm.getDmfGradeId()+"',COS_GRADE_AND_NO='"+statusForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+statusForm.getAdditionalTest()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"'," +
					"TO_BE_USED_IN_PRODUCTS='"+statusForm.getToBeUsedInProducts()+"'" +",IS_VENDOR_SPECIFIC_MATERIAL='"+statusForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+statusForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+statusForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+statusForm.getTempCondition()+"',STORAGE_CONDITION='"+statusForm.getStorageCondition()+"'," +
					"RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',DUTY_ELEMENT='"+statusForm.getDutyElement()+"',PACKING_MATERIAL_GROUP='"+statusForm.getPackageMaterialGroup()+"',Type_Of_Material='"+statusForm.getTypeOfMaterial()+"',IS_ARTWORK_REVISION='"+statusForm.getIsArtworkRevision()+"' ,EXISTING_SAP_ITEM_CODE='"+statusForm.getExistingSAPItemCode()+"'" +
					" ,APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',ARTWORK_NO='"+statusForm.getArtworkNo()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"'," +
					"PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"'," +	",MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";

					 int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);	 
			    		
			    	
				       }
						  
						  if(matType.equalsIgnoreCase("FERT") ||matType.equalsIgnoreCase("HAWA"))
							{
							  String updateFinishedProduct="update material_code_request set LOCATION_ID='"+statusForm.getLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',MANUFACTURED_AT='"+statusForm+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'" +
								",MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+statusForm.getStandardBatchSize()+"',BATCH_CODE='"+statusForm.getBatchCode()+"',SALEABLE_OR_SAMPLE='"+statusForm.getSaleableOrSample()+"',DOMESTIC_OR_EXPORTS='"+statusForm.getDomesticOrExports()+"',SALES_PACK_ID='"+statusForm.getSalesPackId()+"',PACK_TYPE_ID='"+statusForm.getPackTypeId()+"',SALES_UNIT_OF_MEAS_ID='"+statusForm.getSalesUnitOfMeaseurement()+"',DIVISION_ID='"+statusForm.getDivisionId()+"',THERAPEUTIC_SEGMENT_ID='"+statusForm.getTherapeuticSegmentID()+"'," +
								"BRAND_ID='"+statusForm.getBrandID()+"',STRENGTH_ID='"+statusForm.getSrengthId()+"',GENERIC_NAME='"+statusForm.getGenericName()+"',PROD_INSP_MEMO='"+statusForm.getProdInspMemo()+"',GROSS_WEIGHT='"+statusForm.getGrossWeight()+"',NET_WEIGHT='"+statusForm.getNetWeight()+"',WEIGHT_UOM='"+statusForm.getWeightUOM()+"',DIMENSION='"+statusForm.getDimension()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"'," +
								" MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";	 
							  int updateMateStatus=ad.SqlExecuteUpdate(updateFinishedProduct);
							}
						  
						  if(matType.equalsIgnoreCase("HALB"))
						  {
							  String updateSemiFinished="update material_code_request set STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
						 		"UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfType()+"',STANDARD_BATCH_SIZE='"+statusForm.getStandardBatchSize()+"',BATCH_CODE='"+statusForm.getBatchCode()+"',TARGET_WEIGHT='"+statusForm.getTargetWeight()+"',PROD_INSP_MEMO='"+statusForm.getProdInspMemo()+"'," +
						 		"WEIGHT_UOM='"+statusForm.getWeightUOM()+"',RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',MODIFIED_DATE='"+dateNow+"'," +
						 		"MODIFIED_BY='"+user.getEmployeeNo()+"',PACK_SIZE='"+statusForm.getPackSize()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";
							  int updateMateStatus=ad.SqlExecuteUpdate(updateSemiFinished);
						  }
							if(matType.equalsIgnoreCase("ROH"))
							{	 
					String updatQuery="update material_code_request set  STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"'," +
					"MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',PHARMACOP_NAME='"+statusForm.getPharmacopName()+"',PHARMACOP_GRADE='"+statusForm.getPharmacopGrade()+"',GENERIC_NAME='"+statusForm.getGenericName()+"',SYNONYM='"+statusForm.getSynonym()+"',PHARMACOP_SPECIFICATION='"+statusForm.getPharmacopSpecification()+"'" +
					",IS_DMF_MATERIAL='"+statusForm.getIsDMFMaterial()+"',DMF_GRADE_ID='"+statusForm.getDmfGradeId()+"',MATERIAL_GRADE='"+statusForm.getMaterialGrade()+"',COS_GRADE_AND_NO='"+statusForm.getCosGradeNo()+"',ADDITIONAL_TEST='"+statusForm.getAdditionalTest()+"',COUNTRY_ID='"+statusForm.getCountryId()+"',CUSTOMER_NAME='"+statusForm.getCustomerName()+"'" +
					",TO_BE_USED_IN_PRODUCTS='"+statusForm.getToBeUsedInProducts()+"',IS_VENDOR_SPECIFIC_MATERIAL='"+statusForm.getIsVendorSpecificMaterial()+"',MFGR_NAME='"+statusForm.getMfgrName()+"',SITE_OF_MANUFACTURE='"+statusForm.getSiteOfManufacture()+"',TEMP_CONDITION='"+statusForm.getTempCondition()+"',STORAGE_CONDITION='"+statusForm.getStorageCondition()+"',SHELF_LIFE='"+statusForm.getShelfLife()+"',SHELF_LIFE_TYPE='"+statusForm.getShelfLifeType()+"',DUTY_ELEMENT='"+statusForm.getDutyElement()+"'" +
					",RETEST_DAYS='"+statusForm.getRetestDays()+"',RETEST_DAYS_TYPE='"+statusForm.getRetestType()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"'" +
					",MODIFIED_DATE='"+dateNow+"',MODIFIED_BY='"+user.getEmployeeNo()+"',last_approver='"+user.getFullName()+"',pending_approver='No' where REQUEST_NO='"+reqId+"'";
					  
		    		
		            int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
							}
					}
					
				}
				//default masters
				
				if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("ZLAB")
						||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
			     {
									
				
						
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LANDX"));
						}
						statusForm.setCounID(countryID);
						statusForm.setCountryName(countryName);
						String materialType="";
						String valuationType="";
						String getvaluationType="select MATERIAL_TYPE_ID,VALUATION_CLASS from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rsValuationType=ad.selectQuery(getvaluationType);
						while(rsValuationType.next()){
							materialType=rsValuationType.getString("MATERIAL_TYPE_ID");
							valuationType=rsValuationType.getString("VALUATION_CLASS");
						}
						
						
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='"+matType+"'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						statusForm.setStorageID(storageID);
						statusForm.setStorageIDName(storageName);
						
						ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						statusForm.setLocationIdList(locationList);
						statusForm.setLocationLabelList(locationLabelList);
						
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						statusForm.setMaterTypeIDList(materTypeIDList);
						statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						statusForm.setMaterGroupIDList(materGroupIDList);
						statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						LinkedList deptID=new LinkedList();
						LinkedList deptName=new LinkedList();
						String getdepartment="select * from department";
						ResultSet rsdepartment=ad.selectQuery(getdepartment);
						while(rsdepartment.next()){
							deptID.add(rsdepartment.getInt("DPTID"));
							deptName.add(rsdepartment.getString("DPTSTXT"));
						}
						
						statusForm.setDeptId(deptID);
						statusForm.setDeptName(deptName);
						String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
						statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='"+matType+"'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						statusForm.setValuationClassID(valuationClassID);
						statusForm.setValuationClassName(valuationClassName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						statusForm.setPuchaseGroupIdList(puchaseGroupIdList);
						statusForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getFinishedProduct);
						while(rs.next())
						{

							statusForm.setRequestNo(reqId);
							statusForm.setRequestNumber(reqId);
							statusForm.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
							
							reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							statusForm.setRequestDate(reqDate);
							statusForm.setLocationId(rs.getString("LOCATION_ID"));
							statusForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							statusForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
							statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							statusForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
							statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
					/*		pendAppForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
							String isEquipment=rs.getString("IS_EQUIPMENT");
							if(isEquipment.equalsIgnoreCase("1"))
							{
								pendAppForm.setIsEquipment("True");
							}
							if(isEquipment.equalsIgnoreCase("0"))
								pendAppForm.setIsEquipment("False");
							
							String isSpare=rs.getString("IS_SPARE");
							if(isSpare.equalsIgnoreCase("1"))
							{
								pendAppForm.setIsSpare("True");
							}
							if(isSpare.equalsIgnoreCase("0"))
								pendAppForm.setIsSpare("False");
							
							String isNew=rs.getString("IS_NEW");
							if(isNew.equalsIgnoreCase("1"))
							{
								pendAppForm.setIsNew("True");
							}
							if(isNew.equalsIgnoreCase("0"))
								pendAppForm.setIsNew("False");
							
							
							pendAppForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
							pendAppForm.setPrNumber(rs.getString("PR_NUMBER"));
							pendAppForm.setPoNumber(rs.getString("PO_NUMBER"));*/
							statusForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
							String isAsset=rs.getString("is_asset");
							if(isAsset.equalsIgnoreCase("1"))
							{
								statusForm.setIsAsset("True");
							}
							if(isAsset.equalsIgnoreCase("0"))
								statusForm.setIsAsset("False");
							statusForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
							statusForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
							statusForm.setPurposeID(rs.getString("PURPOSE_ID"));
							statusForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							
							
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null){
								statusForm.setSapCodeNo(sapCodeno);
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							statusForm.setSapCodeExists("True");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							statusForm.setSapCodeExists("False");
						String sapCreationDate=rs.getString("SAP_CREATION_DATE");
						String sapDate[]=sapCreationDate.split(" ");
						sapCreationDate=sapDate[0];
						String sapCode[]=sapCreationDate.split("-");
						sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
						statusForm.setSapCreationDate(sapCreationDate);
						statusForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						statusForm.setRequestedBy(rs.getString("REQUESTED_BY"));
							}

						
						}
						
					
					
					
    				
    				
    				
    				
    				
    				//set ApproverDetails 
    				checkStatus=0;int appStatus=0;
    				LinkedList listApprers=new LinkedList();
    				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
    				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
    				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
    				while(rsApprDetails.next())
    				{
    					checkStatus=1;
    					ApprovalsForm apprvers=new ApprovalsForm();
    					apprvers.setPriority(rsApprDetails.getString("Priority"));
    					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
    					String empCode=rsApprDetails.getString("Approver_Id");
    					String approveStatus="";
    					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
    					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
    					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
    					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
    					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
    					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
    					while(rsAppInfo.next())
    					{
    						
    						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
    						approveStatus=rsAppInfo.getString("Req_Status");
    						if(approveStatus.equalsIgnoreCase(""))
    						{
    							apprvers.setApproveStatus("In Process");
    						}
    						if(approveStatus.equalsIgnoreCase("Approved"))
    						{
    						apprvers.setDate(rsAppInfo.getString("approved_date"));
    						}else{
    							apprvers.setDate(rsAppInfo.getString("rejected_date")); 	appStatus=1;
    						}
    						comments=rsAppInfo.getString("Comments");
    						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
    						{
    							apprvers.setComments("");
    						}else{
    							apprvers.setComments(rsAppInfo.getString("Comments"));
    						}
    						
    						
    					}
    					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
    					{
    						apprvers.setApproveStatus("In Process");
    					}
    					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
    					listApprers.add(apprvers);
    				}
    				if(checkStatus==0)
    				{
    					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
    					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
    					rsApprDetails=ad.selectQuery(getApprDetails);
    					while(rsApprDetails.next())
    					{
    						checkStatus=1;
    						ApprovalsForm apprvers=new ApprovalsForm();
    						apprvers.setPriority(rsApprDetails.getString("Priority"));
    						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
    						String empCode=rsApprDetails.getString("Approver_Id");
    						String approveStatus="";
    						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
    						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
    						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
    						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
    						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
    						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
    						while(rsAppInfo.next())
    						{
    							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
    							approveStatus=rsAppInfo.getString("Req_Status");
    							
    							if(approveStatus.equalsIgnoreCase("Approved"))
    							{
    							String approveDate=	rsAppInfo.getString("approved_date");
    							
    							String a[]=approveDate.split(" ");
    							approveDate=a[0];
    							String b[]=approveDate.split("-");
    							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
    							
    							apprvers.setDate(approveDate);
    							}else{
    								String rejectDate=	rsAppInfo.getString("rejected_date");
    								
    								String a[]=rejectDate.split(" ");
    								rejectDate=a[0];
    								String b[]=rejectDate.split("-");
    								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
    								apprvers.setDate(rejectDate);
    							}
    							comments=rsAppInfo.getString("Comments");
    							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
    							{
    								apprvers.setComments("");
    							}else{
    								apprvers.setComments(rsAppInfo.getString("Comments"));
    							}
    						}
    						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
    						{
    							apprvers.setApproveStatus("In Process");
    						}
    						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
    						listApprers.add(apprvers);
    				}
    				}
    				request.setAttribute("approverDetails", listApprers);
    				
					
			     
					
			     }
				
				if(matType.equalsIgnoreCase("ZPPC"))
				{
							
	
						
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LANDX"));
						}
						statusForm.setCounID(countryID);
						statusForm.setCountryName(countryName);
					
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+"-"+rs11.getString("LOCNAME"));
							
						}
						statusForm.setLocationIdList(locationList);
						statusForm.setLocationLabelList(locationLabelList);
						
						LinkedList divisonID=new LinkedList();
						LinkedList divisonName=new LinkedList();
						String getDivison="select * from DIVISION";
						ResultSet rsDivison=ad.selectQuery(getDivison);
						while(rsDivison.next())
						{
							divisonID.add(rsDivison.getString("DIV_CODE"));
							divisonName.add(rsDivison.getString("DIV_DESC"));
						}
						statusForm.setDivisonID(divisonID);
						statusForm.setDivisonName(divisonName);
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
						}
						statusForm.setMaterTypeIDList(materTypeIDList);
						statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+"-"+rsMaterialGroup.getString("STXT"));
						}
						statusForm.setMaterGroupIDList(materGroupIDList);
						statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
						}
						statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
						statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='ZPPC'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						statusForm.setStorageID(storageID);
						statusForm.setStorageIDName(storageName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						statusForm.setPuchaseGroupIdList(puchaseGroupIdList);
						statusForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='ZPPC'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
						}
						statusForm.setValuationClassID(valuationClassID);
						statusForm.setValuationClassName(valuationClassName);
						
			// get material records
				
						String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getFinishedProduct);
						while(rs.next())
						{
							statusForm.setRequestNo(reqId);
							statusForm.setRequestNumber(reqId);
							 reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							statusForm.setRequestDate(reqDate);
							statusForm.setLocationId(rs.getString("LOCATION_ID"));
							statusForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							statusForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
							statusForm.setDivisionId(rs.getString("DIVISION_ID"));
							String isAsset=rs.getString("is_asset");
							if(isAsset.equalsIgnoreCase("1"))
							{
								statusForm.setIsAsset("True");
							}
							if(isAsset.equalsIgnoreCase("0"))
								statusForm.setIsAsset("False");
							
							statusForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
							statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
							statusForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
							statusForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
							
							String purposeId=rs.getString("PURPOSE_ID");
							purposeId=purposeId.replace(" ","");
							statusForm.setPurposeID(purposeId);
							String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
							
							if(isSAS.equalsIgnoreCase("1"))
							{
								statusForm.setIsSASFormAvailable("True");
							}
							if(isSAS.equalsIgnoreCase("0"))
								statusForm.setIsSASFormAvailable("False");
							

						}
                    
					
					
				}
				
				
				if(matType.equalsIgnoreCase("ZPSR"))
				{
					

						
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LANDX"));
						}
						statusForm.setCounID(countryID);
						statusForm.setCountryName(countryName);
						
						LinkedList deptID=new LinkedList();
						LinkedList deptName=new LinkedList();
						String getdepartment="select * from department";
						ResultSet rsdepartment=ad.selectQuery(getdepartment);
						while(rsdepartment.next()){
							deptID.add(rsdepartment.getInt("DPTID"));
							deptName.add(rsdepartment.getString("DPTSTXT"));
						}
						
						statusForm.setDeptId(deptID);
						statusForm.setDeptName(deptName);
						
						String materialType="";
						String valuationType="";
						String getvaluationType="select MATERIAL_TYPE_ID,VALUATION_CLASS from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rsValuationType=ad.selectQuery(getvaluationType);
						while(rsValuationType.next()){
							materialType=rsValuationType.getString("MATERIAL_TYPE_ID");
							valuationType=rsValuationType.getString("VALUATION_CLASS");
						}
						ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+"-"+rs11.getString("LOCNAME"));
						}
						statusForm.setLocationIdList(locationList);
						statusForm.setLocationLabelList(locationLabelList);
						
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
						}
						statusForm.setMaterTypeIDList(materTypeIDList);
						statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+"-"+rsMaterialGroup.getString("STXT"));
						}
						statusForm.setMaterGroupIDList(materGroupIDList);
						statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
						statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='ZPSR'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						statusForm.setStorageID(storageID);
						statusForm.setStorageIDName(storageName);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS ";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
						}
						statusForm.setValuationClassID(valuationClassID);
						statusForm.setValuationClassName(valuationClassName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						statusForm.setPuchaseGroupIdList(puchaseGroupIdList);
						statusForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getFinishedProduct);
						while(rs.next())
						{

							statusForm.setRequestNo(reqId);
							statusForm.setRequestNumber(reqId);
							reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							statusForm.setRequestDate(reqDate);
							statusForm.setLocationId(rs.getString("LOCATION_ID"));
							statusForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							statusForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
							statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							statusForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
							statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
							statusForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
							String isEquipment=rs.getString("IS_EQUIPMENT");
							if(isEquipment.equalsIgnoreCase("1"))
							{
								statusForm.setIsEquipment("True");
								
							}
							statusForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
							statusForm.setEquipmentMake(rs.getString("EQUIPMENT_MAKE"));
							if(isEquipment.equalsIgnoreCase("0")){
								statusForm.setIsEquipment("False");
							
							}
							String isSpare=rs.getString("IS_SPARE");
							if(isSpare.equalsIgnoreCase("1"))
							{
								statusForm.setIsSpare("True");
							
							}
							statusForm.setComponentMake(rs.getString("Component_MAKE"));
							statusForm.setOemPartNo(rs.getString("OEM_PartNo"));
							if(isSpare.equalsIgnoreCase("0")){
								statusForm.setIsSpare("False");
							
								
							}
							
							statusForm.setMoc(rs.getString("moc")); 
							statusForm.setRating(rs.getString("rating"));
							statusForm.setRange(rs.getString("range"));
							statusForm.setIsNewEquipment(rs.getString("IS_NEW_Equipment"));
							statusForm.setIsItNewFurniture(rs.getString("IS_NEW_Furniture"));
							statusForm.setIsItFacility(rs.getString("IS_NEW_Facility"));
							statusForm.setIsSpareNewEquipment(rs.getString("IS_Spare_required"));
							
							
							
							statusForm.setPrNumber(rs.getString("PR_NUMBER"));
							statusForm.setPoNumber(rs.getString("PO_NUMBER"));
							statusForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
							statusForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
							statusForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
							statusForm.setPurposeID(rs.getString("PURPOSE_ID"));
							statusForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							
							
							String sapCodeno=rs.getString("SAP_CODE_NO");
								if(sapCodeno!=null){
									statusForm.setSapCodeNo(sapCodeno);
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								statusForm.setSapCodeExists("True");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								statusForm.setSapCodeExists("False");
							String sapCreationDate=rs.getString("SAP_CREATION_DATE");
							String sapDate[]=sapCreationDate.split(" ");
							sapCreationDate=sapDate[0];
							String sapCode[]=sapCreationDate.split("-");
							sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
							statusForm.setSapCreationDate(sapCreationDate);
							statusForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							statusForm.setRequestedBy(rs.getString("REQUESTED_BY"));
								}
								
							

						
						}
						//end of default values ZPSR
					
				
					
				}
				
				if(matType.equalsIgnoreCase("HALB")){

				
											
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
						}
						statusForm.setCounID(countryID);
						statusForm.setCountryName(countryName);
						
						LinkedList weightUOMID=new LinkedList();
						LinkedList weightUOMName=new LinkedList();
						String getweightUOM="select * from WEIGHT_UOM";
						ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
						while(rsweightUOM.next())
						{
							weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
							weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
						}
						statusForm.setWeightUOMID(weightUOMID);
						statusForm.setWeightUOMName(weightUOMName);
						
						LinkedList packSizeID=new LinkedList();
						LinkedList packSizeName=new LinkedList();
						String getPackSize="select * from PACK_SIZE";
						ResultSet rsPackSize=ad.selectQuery(getPackSize);
						while(rsPackSize.next())
						{
							packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
							packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
						}
						statusForm.setPackSizeID(packSizeID);
						statusForm.setPackSizeName(packSizeName);
					
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						
						
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						statusForm.setMaterTypeIDList(materTypeIDList);
						statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						
						
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						statusForm.setLocationIdList(locationList);
						statusForm.setLocationLabelList(locationLabelList);
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='HALB'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						statusForm.setStorageID(storageID);
						statusForm.setStorageIDName(storageName);
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						statusForm.setMaterGroupIDList(materGroupIDList);
						statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
						statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						statusForm.setValuationClassID(valuationClassID);
						statusForm.setValuationClassName(valuationClassName);
						String getSemiFinished="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getSemiFinished);
						while(rs.next())
						{

							statusForm.setRequestNo(reqId);
							reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							statusForm.setRequestDate(reqDate);
							
							statusForm.setLocationId(rs.getString("LOCATION_ID"));
							statusForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							
							statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							statusForm.setPackSize(rs.getString("PACK_SIZE"));
							statusForm.setCountryId(rs.getString("COUNTRY_ID"));
							statusForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
							statusForm.setShelfLife(rs.getString("SHELF_LIFE"));
							statusForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
							statusForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
							statusForm.setBatchCode(rs.getString("BATCH_CODE"));
							statusForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
							statusForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
					 		
							statusForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
							statusForm.setRetestDays(rs.getString("RETEST_DAYS"));
							statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
							
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null)
							{
								statusForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									statusForm.setSapCodeExists("True");
								}else{
									statusForm.setSapCodeExistsNo("True");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									statusForm.setSapCodeExists("False");
								String createDate=rs.getString("SAP_CREATION_DATE");
								String a1[]=createDate.split(" ");
								createDate=a1[0];
								String b1[]=createDate.split("-");
								createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
								statusForm.setSapCreationDate(createDate);
								statusForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							}
						
						}
						
					
					
				
					
					
				}
				
				if(matType.equalsIgnoreCase("VERP"))
				{
					
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
						}
						statusForm.setCounID(countryID);
						statusForm.setCountryName(countryName);
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						statusForm.setLocationIdList(locationList);
						statusForm.setLocationLabelList(locationLabelList);
						
						
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='VERP'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						statusForm.setStorageID(storageID);
						statusForm.setStorageIDName(storageName);
						
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						statusForm.setMaterTypeIDList(materTypeIDList);
						statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						statusForm.setMaterGroupIDList(materGroupIDList);
						statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
						statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						statusForm.setPuchaseGroupIdList(puchaseGroupIdList);
						statusForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						LinkedList dmfGradeIDList=new LinkedList();
						LinkedList dmfGradeIDValueList=new LinkedList();
						
						String getDMFValues="select * from DMF_GRADE";
						ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
						while(rsDMFValues.next())
						{
							dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
							dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
						}
						statusForm.setDmfGradeIDList(dmfGradeIDList);
						statusForm.setDmfGradeIDValueList(dmfGradeIDValueList);
						
						LinkedList packageGroupID=new LinkedList();
						LinkedList packageGroupIDValue=new LinkedList();
						
						String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
						ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
						
						while(rsPackageGroup.next())
						{
							packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
							packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
						}
						statusForm.setPackageGroupID(packageGroupID);
						statusForm.setPackageGroupIDValue(packageGroupIDValue);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='VERP'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						statusForm.setValuationClassID(valuationClassID);
						statusForm.setValuationClassName(valuationClassName);
						
						LinkedList tempIDList=new LinkedList();
						LinkedList temValueList=new LinkedList();
						String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
						ResultSet rsTemp=ad.selectQuery(getTemp);
						while(rsTemp.next())
						{
							tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
							temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
						}
						statusForm.setTempIDList(tempIDList);
						statusForm.setTemValueList(temValueList);
						
						LinkedList storageIDList=new LinkedList();
						LinkedList storageLocList=new LinkedList();
						String getStorageLoc="select * from STORAGE_CONDITION";
						ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
						while(rsStorageLoc.next())
						{
							storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
							storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
						}
						statusForm.setStorageIDList(storageIDList);
						statusForm.setStorageLocList(storageLocList);
					// get material records
						
						String getMaterial="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getMaterial);
						while(rs.next())
						{
							statusForm.setRequestNo(reqId);
							statusForm.setRequestNumber(reqId);
							reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							statusForm.setRequestDate(reqDate);
							statusForm.setLocationId(rs.getString("LOCATION_ID"));
							statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
						
							statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
							if(isDMFMaterial.equalsIgnoreCase("1"))
							{
								statusForm.setIsDMFMaterial("True");
								request.setAttribute("dmfMandatory", "dmfMandatory");
								statusForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
								statusForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							}
							if(isDMFMaterial.equalsIgnoreCase("0")){
								statusForm.setIsDMFMaterial("False");
								statusForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
								statusForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
								request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
							}
							statusForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
							
							statusForm.setCountryId(rs.getString("COUNTRY_ID"));
							statusForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
							statusForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
							String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
							if(isVendorStatus.equalsIgnoreCase("1"))
							{
								statusForm.setIsVendorSpecificMaterial("True");
								request.setAttribute("vedorMandatory", "vedorMandatory");
								statusForm.setMfgrName(rs.getString("MFGR_NAME"));
								statusForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							}
							if(isVendorStatus.equalsIgnoreCase("0"))
							{
								statusForm.setIsVendorSpecificMaterial("False");
								request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
							}
							statusForm.setMfgrName(rs.getString("MFGR_NAME"));
							statusForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
							statusForm.setTempCondition(rs.getString("TEMP_CONDITION"));
							statusForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
							statusForm.setRetestDays(rs.getString("RETEST_DAYS"));
							statusForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
							statusForm.setDutyElement(rs.getString("DUTY_ELEMENT"));
							
							statusForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP"));
							statusForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
							String typeOfMaterial=rs.getString("Type_Of_Material");
							
							if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
							{
								request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
								statusForm.setArtworkNo(rs.getString("ARTWORK_NO"));
								String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
								if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
								{
									statusForm.setIsArtworkRevision("True");
								}
								if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
									statusForm.setIsArtworkRevision("False");
								
							}
							else
								request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
							
							statusForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
							statusForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						
						
							ArrayList fileList = new ArrayList();
							String uploadedFiles=rs.getString("Attachements");
							statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
							statusForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
							if(uploadedFiles.equalsIgnoreCase(""))
							{
							}else{
							String v[] = uploadedFiles.split(",");
							int l = v.length;
							for (int i = 0; i < l; i++) 
							{
								PackageMaterialMasterForm pendAppForm2=new PackageMaterialMasterForm();
							int x=v[i].lastIndexOf("/");
							uploadedFiles=v[i].substring(x+1);		
							pendAppForm2.setFileList(uploadedFiles);
							pendAppForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+uploadedFiles+"");
							fileList.add(pendAppForm2);
							}
							request.setAttribute("listName", fileList);
							}
						
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null)
							{
								statusForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									statusForm.setSapCodeExists("True");
								}else{
									statusForm.setSapCodeExistsNo("True");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									statusForm.setSapCodeExists("False");
								String createDate=rs.getString("SAP_CREATION_DATE");
								String a1[]=createDate.split(" ");
								createDate=a1[0];
								String b1[]=createDate.split("-");
								createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
								statusForm.setSapCreationDate(createDate);
								statusForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							}
							
						
						}
							
						
						
					
				
				}
				
				
				if(matType.equalsIgnoreCase("FERT") || matType.equalsIgnoreCase("HAWA"))
				{
					
					LinkedList finDetails=new LinkedList();
				
						
						LinkedList packTypeID=new LinkedList();
						LinkedList packTypeName=new LinkedList();
						
						String getPackType="select * from PACK_TYPE";
						ResultSet rsPackType=ad.selectQuery(getPackType);
						while(rsPackType.next())
						{
							packTypeID.add(rsPackType.getString("P_TYPE_CODE"));
							packTypeName.add(rsPackType.getString("P_TYPE_CODE")+" - "+rsPackType.getString("P_TYPE_DESC"));
						}
						statusForm.setPackTypeID(packTypeID);
						statusForm.setPackTypeName(packTypeName);
					
						LinkedList packSizeID=new LinkedList();
						LinkedList packSizeName=new LinkedList();
						String getPackSize="select * from PACK_SIZE";
						ResultSet rsPackSize=ad.selectQuery(getPackSize);
						while(rsPackSize.next())
						{
							packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
							packSizeName.add(rsPackSize.getString("PACK_SIZE_CODE") );
						}
						statusForm.setPackSizeID(packSizeID);
						statusForm.setPackSizeName(packSizeName);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='"+matType+"'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						statusForm.setValuationClassID(valuationClassID);
						statusForm.setValuationClassName(valuationClassName);
						
						LinkedList salesUOMID=new LinkedList();
						LinkedList salesUOMName=new LinkedList();
						String getSalesUOM="select * from SALES_UOM order by S_UOM_DESC";
						ResultSet rsSalesUOM=ad.selectQuery(getSalesUOM);
						while(rsSalesUOM.next())
						{
							salesUOMID.add(rsSalesUOM.getString("S_UOM_CODE"));
							packSizeName.add(rsSalesUOM.getString("S_UOM_CODE")+" - "+rsSalesUOM.getString("S_UOM_DESC"));
						}
						statusForm.setSalesUOMID(salesUOMID);
						statusForm.setSalesUOMName(salesUOMName);

						LinkedList divisonID=new LinkedList();
						LinkedList divisonName=new LinkedList();
						String getDivison="select * from DIVISION";
						ResultSet rsDivison=ad.selectQuery(getDivison);
						while(rsDivison.next())
						{
							divisonID.add(rsDivison.getString("DIV_CODE"));
							divisonName.add(rsDivison.getString("DIV_DESC"));
						}
						statusForm.setDivisonID(divisonID);
						statusForm.setDivisonName(divisonName);
						
						LinkedList therapeuticID=new LinkedList();
						LinkedList therapeuticName=new LinkedList();
						String getrstherapeutic="select * from THERAPEUTIC_SEGMENT";
						ResultSet rstherapeutic=ad.selectQuery(getrstherapeutic);
						while(rstherapeutic.next())
						{
							therapeuticID.add(rstherapeutic.getString("THER_SEG_CODE"));
							therapeuticName.add(rstherapeutic.getString("THER_SEG_DESC"));
						}
						statusForm.setTherapeuticID(therapeuticID);
						statusForm.setTherapeuticName(therapeuticName);
						
						LinkedList brandIDList=new LinkedList();
						LinkedList brandNameList=new LinkedList();
						String getBrand="select * from BRAND order by BRAND_DESC";
						ResultSet rsBrand=ad.selectQuery(getBrand);
						while(rsBrand.next())
						{
							brandIDList.add(rsBrand.getString("BRAND_CODE"));
							brandNameList.add(rsBrand.getString("BRAND_DESC"));
						}
						statusForm.setBrandIDList(brandIDList);
						statusForm.setBrandNameList(brandNameList);
						
						LinkedList strengthIDList=new LinkedList();
						LinkedList strengthNameList=new LinkedList();
						String getStrength="select * from STRENGTH";
						ResultSet rsStrength=ad.selectQuery(getStrength);
						while(rsStrength.next())
						{
							strengthIDList.add(rsStrength.getString("STRENGTH_CODE"));
							strengthNameList.add(rsStrength.getString("STRENGTH_DESC"));
						}
						statusForm.setStrengthIDList(strengthIDList);
						statusForm.setStrengthNameList(strengthNameList);
						
						LinkedList genericIDList=new LinkedList();
						LinkedList genericNameList=new LinkedList();
						String getGeneric="select * from GENERIC_NAME";
						ResultSet rsGeneric=ad.selectQuery(getGeneric);
						while(rsGeneric.next())
						{
							genericIDList.add(rsGeneric.getString("GEN_NAME_CODE"));
							genericNameList.add(rsGeneric.getString("GEN_NAME_DESC"));
						}
						statusForm.setGenericIDList(genericIDList);
						statusForm.setGenericNameList(genericNameList);
						
						LinkedList weightUOMID=new LinkedList();
						LinkedList weightUOMName=new LinkedList();
						String getweightUOM="select * from WEIGHT_UOM";
						ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
						while(rsweightUOM.next())
						{
							weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
							weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
						}
						statusForm.setWeightUOMID(weightUOMID);
						statusForm.setWeightUOMName(weightUOMName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						statusForm.setPuchaseGroupIdList(puchaseGroupIdList);
						statusForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						
						
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String materialTypeId=request.getParameter("materialType");

						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						statusForm.setMaterTypeIDList(materTypeIDList);
						statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='"+matType+"'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						statusForm.setStorageID(storageID);
						statusForm.setStorageIDName(storageName);
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
						}
						statusForm.setCounID(countryID);
						statusForm.setCountryName(countryName);
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						statusForm.setLocationIdList(locationList);
						statusForm.setLocationLabelList(locationLabelList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						statusForm.setMaterGroupIDList(materGroupIDList);
						statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
						statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
			// get material records
				
				String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
				ResultSet rs=ad.selectQuery(getFinishedProduct);
				while(rs.next())
				{
					statusForm.setRequestNo(reqId);
					 reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					statusForm.setRequestDate(reqDate);
					statusForm.setLocationId(rs.getString("LOCATION_ID"));
					statusForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
					
					String manufacturedAt=rs.getString("MANUFACTURED_AT");
					
					if(manufacturedAt.equalsIgnoreCase("Third Party"))
					{
						request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
						request.setAttribute("manufactureMandatory", "manufactureMandatory");
					}
					else{
						request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
						request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
					}
					
					statusForm.setManufacturedAt(manufacturedAt);
					statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
					statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
					statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
					statusForm.setCountryId(rs.getString("COUNTRY_ID"));
					statusForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					statusForm.setShelfLife(rs.getString("SHELF_LIFE"));
					statusForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					statusForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					statusForm.setBatchCode(rs.getString("BATCH_CODE"));
					statusForm.setSaleableOrSample(rs.getString("SALEABLE_OR_SAMPLE"));
					statusForm.setDomesticOrExports(rs.getString("DOMESTIC_OR_EXPORTS"));
					String salesPackId=rs.getString("SALES_PACK_ID");
					statusForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
					statusForm.setPackTypeId(rs.getString("PACK_TYPE_ID"));
					statusForm.setSalesUnitOfMeaseurement(rs.getString("SALES_UNIT_OF_MEAS_ID"));
					statusForm.setDivisionId(rs.getString("DIVISION_ID"));
					statusForm.setTherapeuticSegmentID(rs.getString("THERAPEUTIC_SEGMENT_ID"));
					statusForm.setBrandID(rs.getString("BRAND_ID"));
					statusForm.setSrengthId(rs.getString("STRENGTH_ID"));
					statusForm.setGenericName(rs.getString("GENERIC_NAME"));
					statusForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
					statusForm.setTaxClassification(rs.getString("Tax_Classification"));
					statusForm.setMaterialPricing(rs.getString("Material_Pricing"));
					statusForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
					statusForm.setNetWeight(rs.getString("NET_WEIGHT"));
					statusForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
					statusForm.setDimension(rs.getString("DIMENSION"));
					statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
					statusForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						statusForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							statusForm.setSapCodeExists("True");
						}else{
							statusForm.setSapCodeExistsNo("True");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							statusForm.setSapCodeExists("False");
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						statusForm.setSapCreationDate(createDate);
						statusForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
					
					
					
				}
					
				}
				
				if(matType.equalsIgnoreCase("ROH"))
				{
					
					String getCountryDetails="select * from Country";
					LinkedList countryID=new LinkedList();
					LinkedList countryName=new LinkedList();
					ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
					while(rsCountryDetails.next()){
						countryID.add(rsCountryDetails.getString("LAND1"));
						countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
					}
					statusForm.setCounID(countryID);
					statusForm.setCountryName(countryName);
					
					ResultSet rs11 = ad.selectQuery("select * from location");
					ArrayList locationList=new ArrayList();
					ArrayList locationLabelList=new ArrayList();
					
					while(rs11.next()) {
						locationList.add(rs11.getString("LOCID"));
						locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
					}
					statusForm.setLocationIdList(locationList);
					statusForm.setLocationLabelList(locationLabelList);
					
					
					LinkedList storageID=new LinkedList();
					LinkedList storageName=new LinkedList();
					String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='ROH'";
					ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
					while(rsStrogeLocation.next()){
						storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
						storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
					}
					statusForm.setStorageID(storageID);
					statusForm.setStorageIDName(storageName);
					
					LinkedList materTypeIDList=new LinkedList();
					LinkedList materialTypeIdValueList=new LinkedList();
					String getMaterials="select * from MATERIAL_TYPE";
					ResultSet rsMaterial=ad.selectQuery(getMaterials);
					while(rsMaterial.next())
					{
						materTypeIDList.add(rsMaterial.getString("id"));
						materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
					}
					statusForm.setMaterTypeIDList(materTypeIDList);
					statusForm.setMaterialTypeIdValueList(materialTypeIdValueList);
					
					LinkedList materGroupIDList=new LinkedList();
					LinkedList materialGroupIdValueList=new LinkedList();
					
					String getMaterialGroup="select * from MATERIAL_GROUP";
					ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
					while(rsMaterialGroup.next())
					{
						materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
						materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
					}
					statusForm.setMaterGroupIDList(materGroupIDList);
					statusForm.setMaterialGroupIdValueList(materialGroupIdValueList);
					
					LinkedList unitOfMeasIdList=new LinkedList();
					LinkedList unitOfMeasIdValues=new LinkedList();
					
					String getunitMesurement="select * from UNIT_MESUREMENT";
					ResultSet rsUnit=ad.selectQuery(getunitMesurement);
					while(rsUnit.next())
					{
						unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
						unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
					}
					statusForm.setUnitOfMeasIdList(unitOfMeasIdList);
					statusForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
					
					LinkedList puchaseGroupIdList=new LinkedList();
					LinkedList puchaseGroupIdValues=new LinkedList();
					
					String getPurchaseGroup="select * from PURCHASE_GROUP";
					ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
					while(rsPurchaseGroup.next())
					{
						puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
						puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
					}
					statusForm.setPuchaseGroupIdList(puchaseGroupIdList);
					statusForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
					
					LinkedList dmfGradeIDList=new LinkedList();
					LinkedList dmfGradeIDValueList=new LinkedList();
					
					String getDMFValues="select * from DMF_GRADE";
					ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
					while(rsDMFValues.next())
					{
						dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
						dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
					}
					statusForm.setDmfGradeIDList(dmfGradeIDList);
					statusForm.setDmfGradeIDValueList(dmfGradeIDValueList);
					
					
					LinkedList valuationClassID=new LinkedList();
					LinkedList valuationClassName=new LinkedList();
					
					String getValuation="select * from VALUATION_CLASS";
					ResultSet rsValuation=ad.selectQuery(getValuation);
					while(rsValuation.next())
					{
						valuationClassID.add(rsValuation.getString("VALUATION_ID"));
						valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
					}
					statusForm.setValuationClassID(valuationClassID);
					statusForm.setValuationClassName(valuationClassName);
					
					LinkedList tempIDList=new LinkedList();
					LinkedList temValueList=new LinkedList();
					String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
					ResultSet rsTemp=ad.selectQuery(getTemp);
					while(rsTemp.next())
					{
						tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
						temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
					}
					statusForm.setTempIDList(tempIDList);
					statusForm.setTemValueList(temValueList);
					
					LinkedList storageIDList=new LinkedList();
					LinkedList storageLocList=new LinkedList();
					String getStorageLoc="select * from STORAGE_CONDITION";
					ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
					while(rsStorageLoc.next())
					{
						storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
						storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
					}
					statusForm.setStorageIDList(storageIDList);
					statusForm.setStorageLocList(storageLocList);
					
					String getMaterial="select * from material_code_request where REQUEST_NO='"+reqId+"'";
					ResultSet rs=ad.selectQuery(getMaterial);
					while(rs.next())
					{
						statusForm.setRequestNumber(reqId);
						reqDate=rs.getString("REQUEST_DATE");
						String a[]=reqDate.split(" ");
						reqDate=a[0];
						String b[]=reqDate.split("-");
						reqDate=b[2]+"/"+b[1]+"/"+b[0];
						statusForm.setRequestDate(reqDate);
						statusForm.setLocationId(rs.getString("LOCATION_ID"));
						statusForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
						statusForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
						statusForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
						statusForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
						statusForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
						statusForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
					String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
					statusForm.setPharmacopGrade(pharmacopGrade);
						
					statusForm.setGenericName(rs.getString("GENERIC_NAME"));
					statusForm.setSynonym(rs.getString("SYNONYM"));
					statusForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
						String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
						if(isDMfMaterial.equalsIgnoreCase("1"))
						{
							statusForm.setIsDMFMaterial("True");
							statusForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
							statusForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
							statusForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							
						}
						statusForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
						if(isDMfMaterial.equalsIgnoreCase("0"))
						{
							statusForm.setIsDMFMaterial("False");
							statusForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
							statusForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
							statusForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
							
							
						}
						
						statusForm.setCountryId(rs.getString("COUNTRY_ID"));
						statusForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
						statusForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
						
						String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
						if(isVendorStatus.equalsIgnoreCase("1"))
						{
							statusForm.setIsVendorSpecificMaterial("True");
							statusForm.setMfgrName(rs.getString("MFGR_NAME"));
							statusForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
						}
						if(isVendorStatus.equalsIgnoreCase("0"))
						{
							statusForm.setIsVendorSpecificMaterial("False");
						}
						
						statusForm.setTempCondition(rs.getString("TEMP_CONDITION"));
						statusForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
						statusForm.setShelfLife(rs.getString("SHELF_LIFE"));
						String dutyElement=rs.getString("DUTY_ELEMENT");
						if(dutyElement.equalsIgnoreCase("1"))
						{
							statusForm.setDutyElement("True");
						}
						if(dutyElement.equalsIgnoreCase("0"))
							statusForm.setDutyElement("False");
						statusForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
						statusForm.setRetestDays(rs.getString("RETEST_DAYS"));
						statusForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
						statusForm.setValuationClass(rs.getString("VALUATION_CLASS"));
						statusForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
						statusForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
						statusForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
						statusForm.setRequestedBy(rs.getString("REQUESTED_BY"));
						
						ArrayList fileList = new ArrayList();
						String uploadedFiles=rs.getString("Attachements");
						if(uploadedFiles.equalsIgnoreCase(""))
						{
							
						}else{
						String v[] = uploadedFiles.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) 
						{
							RawMaterialForm statusForm2=new RawMaterialForm();
							//String url=v[i];
							//statusForm2.setUrl(url);
						System.out.println(v[i]);
							statusForm2.setUploadFilePath(v[i]);
						int x=v[i].lastIndexOf("/");
						uploadedFiles=v[i].substring(x+1);		
						statusForm2.setFileList(uploadedFiles);
						fileList.add(statusForm2);
						}
						request.setAttribute("listName", fileList);
						}
						String sapCodeno=rs.getString("SAP_CODE_NO");
						if(sapCodeno!=null)
						{
							statusForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
							String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
							if(sapCodeExist.equalsIgnoreCase("1"))
							{
								statusForm.setSapCodeExists("Yes");
							}
							if(sapCodeExist.equalsIgnoreCase("0"))
								statusForm.setSapCodeExists("No");
							String createDate=rs.getString("SAP_CREATION_DATE");
							String a1[]=createDate.split(" ");
							createDate=a1[0];
							String b1[]=createDate.split("-");
							createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
							statusForm.setSapCreationDate(createDate);
							statusForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						}
				}
				
				}//end of ROH VALUES
				
				
				
				
				statusForm.setUserRole(userRole);
				
				checkStatus=0;int appStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					
					
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date")); 	appStatus=1;
						}
						comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
					}
					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
				if(checkStatus==0)
				{
					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date")); 	appStatus=1;
							}
						comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
				}
				}
				request.setAttribute("approverDetails", listApprers);
				
				if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("ZLAB")
						||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
			     {	
			
				return mapping.findForward("GeneralMaterial_Creator");
				  }
				
				if(matType.equalsIgnoreCase("ZPPC") )
				{
					
    				   					
    				return mapping.findForward("ZPPC_Creator");
    				
				}
				if(matType.equalsIgnoreCase("ZPSR"))
				{
					
						
					return mapping.findForward("ZPSR_Creator");
					
				}
				if(matType.equalsIgnoreCase("HALB"))
				{
				
				return mapping.findForward("HALB_Creator");
				
				}
				if(matType.equalsIgnoreCase("FERT") || matType.equalsIgnoreCase("HAWA"))
				{
					
					
					return mapping.findForward("finishedProduct_Creator");
				}
									
				if(matType.equalsIgnoreCase("ROH"))
				{
				
					return mapping.findForward("ROH_Creator");
					
				}
				
				if(matType.equalsIgnoreCase("VERP"))
				 {
				
				
			
				       return mapping.findForward("VERP_Creator");
				  
			   }
				
			}
			
						
					
					
					
							
			
		
		}
		catch(SQLException se){
			System.out.println("Exception @ getting Approve");
			se.printStackTrace();
		}
		catch(Exception se){
			System.out.println("Exception @ getting Approve");
			se.printStackTrace();
		}
		request.setAttribute("header", "Pending Request");
		request.setAttribute("listDetails", list);
		request.setAttribute("openRequest", "");
		String result=(String)session.getAttribute("result");
		System.out.println("result="+result);
		if(result==null||result==" ")
		session.setAttribute("result"," ");
		
		
		if(request.getParameter("id")!=null){
		
		request.setAttribute("MenuIcon", request.getParameter("id"));
		}
		
		statusForm.setRequestType(reqType);
		statusForm.setRequestNo(reqId);
		
		
		return mapping.findForward("leaveDetails");
	}
	
	

	public String getDepartmentName(String userId){
		MainDao exeQry = new MainDao();
		String deptName="";
		try{
			ResultSet rs1 = exeQry.selectQuery("select dept_name from emp_master where emp_id='"+userId+"'");
			while (rs1.next()) {
				deptName = rs1.getString("dept_name");
			}
		}catch (SQLException e) {System.out.println("exception @ getting department");
			e.printStackTrace();
		}
		return deptName;
	}

	private String sendMailToApprover(HttpServletRequest request,
			int nextPriority, LinkedList reqDetails, String requesterName) {
		MainDao exeQry = new MainDao();
		String reqId = reqDetails.get(0).toString();
		String reqType = reqDetails.get(1).toString();
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		String reqStatus = request.getParameter("status");
		int user_Id = user.getId();
		String approvermail="";
		String approvername="";
		EMailer email = new EMailer();
		try{
			if(nextPriority != 0){
				if(reqType.contains("Leave") || reqType.contains("On Duty") || reqType.contains("Permission")){
					String getApproverID="select RPTMGR from emp_official_info where PERNR='"+requesterName+"'";
					ResultSet approverRS=exeQry.selectQuery(getApproverID);
					while(approverRS.next()){
						approvername = approverRS.getString("RPTMGR");
					}
					approvername = email.getApproverName(approvername);//check approver delegated or not
					String getNextApproverID="select emp_email from emp_master where emp_id like '%"+approvername+"%'";
					approverRS=exeQry.selectQuery(getNextApproverID);
					while(approverRS.next()){
						approvermail = approverRS.getString(1);
					}
				}
				else{
					String getNextApproverID="select * from Approvers_Details where Type='"+reqType+"' and Priority="+nextPriority;
					ResultSet approverRS=exeQry.selectQuery(getNextApproverID);
					/*while(rs.next()){
						approvername = rs.getString("Approver_id");
						approvermail = rs.getString("emailID");
					}*/
					while(approverRS.next()){
						approvername = approverRS.getString("Approver_id");
						approvername = email.getApproverName(approvername);//check approver delegated or not
						if(approvername.equalsIgnoreCase(approverRS.getString("Approver_id"))){
							approvermail = approverRS.getString("emailID");
						}
						else{
							getNextApproverID="select emp_email from emp_master where emp_id like '%"+approvername+"%'";
							approverRS=exeQry.selectQuery(getNextApproverID);
							while(approverRS.next()){
								approvermail = approverRS.getString(1);
							}
						}
						
					}
				}
			}
			else{
				String getNextApproverID="select * from emp_master where emp_id='"+requesterName+"'";
				ResultSet rs=exeQry.selectQuery(getNextApproverID);
				while(rs.next()){
					approvermail = rs.getString("emp_email");
				}
				if(reqStatus.equalsIgnoreCase("Approve") && reqType.equalsIgnoreCase("Material Code Request")){

					String getCodeCreaterID="select * from Approvers_Details where Type like '%Code Creat%'";
					ResultSet codeRS=exeQry.selectQuery(getCodeCreaterID);
					while(codeRS.next()){
						approvername = codeRS.getString("Approver_id");
						approvername = email.getApproverName(approvername);
						if(approvername.equalsIgnoreCase(codeRS.getString("Approver_id"))){
							approvermail = codeRS.getString("emailID");
						}
						else{
							getCodeCreaterID="select emp_email from emp_master emp_id like '%"+approvername+"%'";
							codeRS=exeQry.selectQuery(getNextApproverID);
							while(codeRS.next()){
								approvermail = codeRS.getString(1);
							}
						}
					}
					LinkedList paramList =  new LinkedList();
					String updateSql= "update All_Request set Pending_approver='"+approvername+"', Req_Status='Pending' where Req_Id='"+reqId+"' and Req_Type='"+reqType+"'";
					int upd=exeQry.SqlExecuteUpdate(updateSql, paramList);
				}
				
			}
			String tName = "Recruitment_Request";
			String getRequestDeatils="select * from "+tName+" inner join All_Request on "+tName+".Req_Id = All_Request.Req_Id where All_Request.Req_Id='"+reqId+"'";
			tName = email.getTableNameForRequest(reqType);
			
			
			if(reqType.contains("Vendor Master") || reqType.contains("Customer Master") || reqType.equalsIgnoreCase("Material Code Request")){
				getRequestDeatils="select * from "+tName+" inner join All_Request on "+tName+".REQUEST_NO = All_Request.Req_Id where All_Request.Req_Id='"+reqId+"'";
			}
			else if(reqType.equalsIgnoreCase("Recruitment Request") || reqType.equalsIgnoreCase("Add Man Power") || reqType.equalsIgnoreCase("Leave") || reqType.equalsIgnoreCase("Feedback")){
				getRequestDeatils="select * from "+tName+" inner join All_Request on "+tName+".Req_Id = All_Request.Req_Id where All_Request.Req_Id='"+reqId+"'";
			}
			else if(reqType.equalsIgnoreCase("On Duty") || reqType.contains("Service Master") || reqType.equalsIgnoreCase("Permission")){
				getRequestDeatils="select * from "+tName+" inner join All_Request on "+tName+".request_no = All_Request.Req_Id where All_Request.Req_Id='"+reqId+"'";
			}
			//String getRequestDeatils="select * from "+tName+" inner join All_Request on "+tName+".Req_Id = All_Request.Req_Id where  All_Request.Req_Id='"+reqId+"'";
			//String getRequestDeatils="select * from "+tName+" where Req_Id='"+reqId+"'";
			
			ResultSet rs1=exeQry.selectQuery(getRequestDeatils);
			
			while(rs1.next()){
				MailInboxForm mailForm = new MailInboxForm();
				MailInboxAction mailAction = new MailInboxAction();
				mailForm.setToAddress(approvermail);
				mailForm.setccAddress("");
				mailForm.setbccAddress("");
				mailForm.setSubject(reqType);
				if(nextPriority == 0){
					if(reqStatus.equalsIgnoreCase("Approve")){
						mailForm.setSubject("Your Request Approved");
						if(reqType.equalsIgnoreCase("Material Code Request")){
							mailForm.setSubject("New Request for Code Creation");
						}
						if(reqType.equalsIgnoreCase("Leave")){
							float noDays=0;
							int leaveType=0;
							String lvqry = "select no_of_days,leave_type from leave_details where Req_Id='"+reqId+"'";
							ResultSet lvqryRS=exeQry.selectQuery(lvqry);
							while(lvqryRS.next()){
								noDays = lvqryRS.getFloat("no_of_days");
								leaveType = lvqryRS.getInt("leave_type");
							}
							LeaveAction lA = new LeaveAction();
							int upd = lA.updateAvail(requesterName, ""+leaveType,""+noDays);
							
						}
					}
					else if(reqStatus.equalsIgnoreCase("Reject")){
						mailForm.setSubject("Your Request Rejected");
					}
					else{
						mailForm.setSubject("SAP Code Created for You Request!");
					}
				}
				String desc = "Requested By : "+rs1.getString("Requester_Name")+"</br>";
				desc = desc + "Requested Date : "+rs1.getString("Req_Date")+"</br>";
				desc = desc + "Department : "+getDepartmentName(rs1.getString("Requester_Id"))+"</br>";
				if(reqType.equalsIgnoreCase("Leave")){
					LeaveAction lAction = new LeaveAction();
					desc = desc + "Leave Type : "+lAction.getLeaveType(rs1.getString("leave_type"))+" for "+lAction.getHolidayType(rs1.getString("start_duration"))+"</br>";
					desc = desc + "From : "+EMicroUtils.display1(rs1.getDate("start_date"))+"</br>";
					desc = desc + "No.Of Days : "+rs1.getString("no_of_days")+"</br>";
				}
				else if(reqType.equalsIgnoreCase("On Duty")){
					desc = desc + "OnDuty Type : "+rs1.getString("onDuty_Type")+" for "+rs1.getString("duration")+"</br>";
					desc = desc + "From : "+EMicroUtils.display1(rs1.getDate("start_date"))+"</br>";
					desc = desc + "No.Of Days : "+rs1.getString("no_of_days")+"</br>";
				}
				else if(reqType.contains("Vendor Master")){
					desc = desc + "Vendar Name : "+rs1.getString("TITLE")+". "+rs1.getString("NAME")+"</br>";
					desc = desc + "PAN NO : "+rs1.getString("PAN_No")+"</br>";
					desc = desc + "EMAIL ID : "+rs1.getString("EMAIL_ID")+"</br>";
					desc = desc + "Vendor Type : "+rs1.getString("Type_Of_Vendor")+"</br>";
				}
				else if(reqType.contains("Customer Master")){
					desc = desc + "Customer Name : MR/MRS/MS. "+rs1.getString("NAME")+"</br>";
					desc = desc + "PAN NO : "+rs1.getString("PAN_No")+"</br>";
					desc = desc + "EMAIL ID : "+rs1.getString("EMAIL_ID")+"</br>";
					desc = desc + "Customer Type : "+rs1.getString("Customer_Type")+"</br>";
				}
				else if(reqType.equalsIgnoreCase("Feedback")){
					desc = desc + "Subject : "+rs1.getString("subject")+"</br>";
					desc = desc + "Comments : "+rs1.getString("comments")+"</br>";
				}
				else if(reqType.equalsIgnoreCase("Material Code Request")){
					desc = desc + "Material Type : "+rs1.getString("Type")+"</br>";
					desc = desc + "Material Short Name : "+rs1.getString("MATERIAL_SHORT_NAME")+"</br>";
					desc = desc + "Material Generic Name : "+rs1.getString("GENERIC_NAME")+"</br>";
					desc = desc + "Pharma Name : "+rs1.getString("PHARMACOP_NAME")+"</br>";
				}
				else if(reqType.contains("Service Master")){
					desc = desc + "Machine Name : "+rs1.getString("machine_name")+"</br>";
					desc = desc + "Service Category : "+rs1.getString("service_catagory")+"</br>";
					desc = desc + "Service Description : "+rs1.getString("service_description")+"</br>";
					desc = desc + "Purpose : "+rs1.getString("purpose")+"</br>";
					
				}
				else if(reqType.equalsIgnoreCase("Permission")){
					desc = desc + "Permission Date : "+rs1.getString("date")+"</br>";
					desc = desc + "Permission Time : "+rs1.getString("startTime")+"</br>";
					desc = desc + "Reason : "+rs1.getString("reason")+"</br>";
				}
				else{
					if(reqType.equalsIgnoreCase("Recruitment Request")){
						desc = desc + "Required Employee : "+rs1.getString("TotalEmp")+"</br>";
						desc = desc + "Required Qualification : "+rs1.getString("Qualification")+"</br>";
						desc = desc + "Salary Offered : "+rs1.getString("SalaryOffered")+"</br>";
					}
					else if(reqType.equalsIgnoreCase("Add Man Power")){
						desc = desc + "Required Man Power : "+rs1.getString("Req_Man_Power")+"</br>";
					}
				}
				desc = desc + "Comments : "+rs1.getString("Comments")+"</br>";
				mailForm.setDescription(desc);
			//	mailAction.mailSendToRecipient(request, mailForm, "request");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
/*		catch(IOException e){System.out.println("Exception @ getting count");e.printStackTrace();}
		catch (ServletException se) { System.out.println("MessagingException @ get Inbox mail"); se.printStackTrace();}
		catch(SQLException sqe){System.out.println("Exception @ getting count");sqe.printStackTrace();}
		catch (MessagingException e) { System.out.println("MessagingException @ get mail"); e.printStackTrace();}*/
		
		return approvername;
	}


	private int getCount(String tName, String reqType) {
		MainDao exeQry = new MainDao();
		int count=0;
		String sql="select count(*) from "+tName+" where Type='"+reqType+"'";
		try{
			ResultSet countrs=exeQry.selectQuery(sql);
			while(countrs.next()) {
					count=Integer.parseInt(countrs.getString(1));
			}
		}
		catch(SQLException se){
			System.out.println("Exception @ getting count");
			se.printStackTrace();
		}
		return count;
	}


	public ActionForward ViewMaterialrecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		
		
		MaterialsForm pendAppForm=(MaterialsForm) form;
		String reqId = request.getParameter("reqId");
		String type=request.getParameter("type");
		
		if(type==null)
		{
			type="0";
		}
		if(type.equalsIgnoreCase("0"))
		{
			request.setAttribute("save", "");
		}
		else
		{
			request.setAttribute("save", "save");
		}
		
		String reqType = pendAppForm.getReqRequstType();
		pendAppForm.setRequestType(reqType);
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user == null)
		{
			pendAppForm.setAppMessage("Session Expried! Try to Login again!");
			return mapping.findForward("approvePage");
		}
		String scount = Integer.toString(pendAppForm.getStartRecord());
		String filterBy=pendAppForm.getSelectedFilter();
	    pendAppForm.setSelectedFilter(filterBy);
		if(reqId==null && reqType==null){
			reqId=pendAppForm.getRequestNo();
			reqType=pendAppForm.getRequestType();
		}
		int startCount = 0;
		int endCount = 0;
		if(scount == null){
			request.setAttribute("noRecords","noRecords");
			pendAppForm.setAppMessage("Request Approved Successfully!");
		}
		String uName = user.getUserName();
		UserDao adMPM=new UserDao();
		EssDao ad=new EssDao();
		try{
								
												
							
				String matType="";				
				String location="";
				String matGroup="";
				String matDetails="select loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.type from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
				ResultSet rsDetails=ad.selectQuery(matDetails);
				if(rsDetails.next())
				{
					location=rsDetails.getString("LOCATION_CODE");
					matGroup=rsDetails.getString("MATERIAL_GROUP_ID");
					matType=rsDetails.getString("type");
				}
								String userRole="";
				int checkStatus=0;
				String getUserRole="select Priority,Role from  Material_Approvers where Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' AND (Approver_Id='"+user.getEmployeeNo()+"' OR Parllel_Approver_1='"+user.getEmployeeNo()+"' OR Parllel_Approver_2='"+user.getEmployeeNo()+"') ";
				ResultSet rsUserRole=ad.selectQuery(getUserRole);
				while(rsUserRole.next())
				{
					checkStatus=1;
					userRole=rsUserRole.getString("Role");
				}
				if(checkStatus==0)
				{
					getUserRole="select Priority,Role from  Material_Approvers where Location='"+location+"' AND  Material_Type='"+matType+"' AND Material_Group='' AND (Approver_Id='"+user.getEmployeeNo()+"' OR Parllel_Approver_1='"+user.getEmployeeNo()+"' OR Parllel_Approver_2='"+user.getEmployeeNo()+"') ";
					rsUserRole=ad.selectQuery(getUserRole);
					while(rsUserRole.next())
					{
						userRole=rsUserRole.getString("Role");
					}
				}
				pendAppForm.setUserRole(userRole);
				
				Date dNow = new Date( );
				 SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
					String dateNow = ft.format(dNow);
					pendAppForm.setSapCreationDate(dateNow);
				pendAppForm.setSapCreatedBy(user.getEmployeeNo()+"-"+user.getFullName());
			
				String rejected_flag="";
			String flag="select rejected_flag from  material_code_request where REQUEST_NO='"+reqId+"'";
			ResultSet rs1 =ad.selectQuery(flag);
			while(rs1.next())
			{
				 rejected_flag=rs1.getString("rejected_flag");
			}
			
				if(rejected_flag!=null)
				{
					if(rejected_flag.equalsIgnoreCase("yes"))
						request.setAttribute("rejectedFLag", "rejectedFLag");
				}
				
			
				if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("LC")
						||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
			     {
					
				
						
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LANDX"));
						}
						pendAppForm.setCounID(countryID);
						pendAppForm.setCountryName(countryName);
						String materialType="";
						String valuationType="";
						String getvaluationType="select MATERIAL_TYPE_ID,VALUATION_CLASS from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rsValuationType=ad.selectQuery(getvaluationType);
						while(rsValuationType.next()){
							materialType=rsValuationType.getString("MATERIAL_TYPE_ID");
							valuationType=rsValuationType.getString("VALUATION_CLASS");
						}
						
						
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='"+matType+"'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						pendAppForm.setStorageID(storageID);
						pendAppForm.setStorageIDName(storageName);
						
						ResultSet rs11 = ad.selectQuery("select LOCID," +
						"LOCNAME,location_code from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						pendAppForm.setLocationIdList(locationList);
						pendAppForm.setLocationLabelList(locationLabelList);
						
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						pendAppForm.setMaterTypeIDList(materTypeIDList);
						pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						pendAppForm.setMaterGroupIDList(materGroupIDList);
						pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						LinkedList deptID=new LinkedList();
						LinkedList deptName=new LinkedList();
						String getdepartment="select * from department";
						ResultSet rsdepartment=ad.selectQuery(getdepartment);
						while(rsdepartment.next()){
							deptID.add(rsdepartment.getInt("DPTID"));
							deptName.add(rsdepartment.getString("DPTSTXT"));
						}
						
						pendAppForm.setDeptId(deptID);
						pendAppForm.setDeptName(deptName);
						String getunitMesurement="select * from UNIT_MESUREMENT order by UNIT_OF_MEAS_ID";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
						pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='"+matType+"'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						pendAppForm.setValuationClassID(valuationClassID);
						pendAppForm.setValuationClassName(valuationClassName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						pendAppForm.setPuchaseGroupIdList(puchaseGroupIdList);
						pendAppForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getFinishedProduct);
						while(rs.next())
						{

							pendAppForm.setRequestNo(reqId);
							pendAppForm.setRequestNumber(reqId);
							pendAppForm.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
							
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							pendAppForm.setRequestDate(reqDate);
							pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
							pendAppForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
							pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
							pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
							pendAppForm.setHsnCode(rs.getString("HSN_Code"));
					/*		masterForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
							String isEquipment=rs.getString("IS_EQUIPMENT");
							if(isEquipment.equalsIgnoreCase("1"))
							{
								masterForm.setIsEquipment("True");
							}
							if(isEquipment.equalsIgnoreCase("0"))
								masterForm.setIsEquipment("False");
							
							String isSpare=rs.getString("IS_SPARE");
							if(isSpare.equalsIgnoreCase("1"))
							{
								masterForm.setIsSpare("True");
							}
							if(isSpare.equalsIgnoreCase("0"))
								masterForm.setIsSpare("False");
							
							String isNew=rs.getString("IS_NEW");
							if(isNew.equalsIgnoreCase("1"))
							{
								masterForm.setIsNew("True");
							}
							if(isNew.equalsIgnoreCase("0"))
								masterForm.setIsNew("False");
							
							
							masterForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
							masterForm.setPrNumber(rs.getString("PR_NUMBER"));
							masterForm.setPoNumber(rs.getString("PO_NUMBER"));*/
							pendAppForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
							String isAsset=rs.getString("is_asset");
							if(isAsset.equalsIgnoreCase("1"))
							{
								pendAppForm.setIsAsset("True");
							}
							if(isAsset.equalsIgnoreCase("0"))
								pendAppForm.setIsAsset("False");
							pendAppForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
							pendAppForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
							pendAppForm.setPurposeID(rs.getString("PURPOSE_ID"));
							
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null){
						pendAppForm.setSapCodeNo(sapCodeno);		
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							pendAppForm.setSapCodeExists("True");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							pendAppForm.setSapCodeExistsNo("True");
						String sapCreationDate=rs.getString("SAP_CREATION_DATE");
						String sapDate[]=sapCreationDate.split(" ");
						sapCreationDate=sapDate[0];
						String sapCode[]=sapCreationDate.split("-");
						sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
						pendAppForm.setSapCreationDate(sapCreationDate);
						pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						pendAppForm.setRequestedBy(rs.getString("REQUESTED_BY"));
							}
							
					 		

						
						}
						
					
					
					if(filterBy.equalsIgnoreCase("Pending")){
    					request.setAttribute("approveButton", "approveButton");
    					request.setAttribute("rejectButton", "rejectButton");
    				}
    				
    				
    				
    				
    				
    				//set ApproverDetails 
    				checkStatus=0;int appStatus=0;
    				LinkedList listApprers=new LinkedList();
    				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
    				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
    				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
    				while(rsApprDetails.next())
    				{
    					checkStatus=1;
    					ApprovalsForm apprvers=new ApprovalsForm();
    					apprvers.setPriority(rsApprDetails.getString("Priority"));
    					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
    					String empCode=rsApprDetails.getString("Approver_Id");
    					String approveStatus="";
    					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
    					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
    					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
    					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
    					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
    					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
    					while(rsAppInfo.next())
    					{
    						
    						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
    						approveStatus=rsAppInfo.getString("Req_Status");
    						if(approveStatus.equalsIgnoreCase(""))
    						{
    							apprvers.setApproveStatus("In Process");
    						}
    						if(approveStatus.equalsIgnoreCase("Approved"))
    						{
    						apprvers.setDate(rsAppInfo.getString("approved_date"));
    						}else{
    							apprvers.setDate(rsAppInfo.getString("rejected_date"));
    							appStatus=1;
    						}
    						String comments=rsAppInfo.getString("Comments");
    						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
    						{
    							apprvers.setComments("");
    						}else{
    							apprvers.setComments(rsAppInfo.getString("Comments"));
    						}
    						
    						
    					}
    					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
    					{
    						apprvers.setApproveStatus("In Process");
    					}
    					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
    					listApprers.add(apprvers);
    				}
    				if(checkStatus==0)
    				{
    					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
    					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
    					rsApprDetails=ad.selectQuery(getApprDetails);
    					while(rsApprDetails.next())
    					{
    						checkStatus=1;
    						ApprovalsForm apprvers=new ApprovalsForm();
    						apprvers.setPriority(rsApprDetails.getString("Priority"));
    						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
    						String empCode=rsApprDetails.getString("Approver_Id");
    						String approveStatus="";
    						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
    						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
    						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
    						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
    						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
    						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
    						while(rsAppInfo.next())
    						{
    							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
    							approveStatus=rsAppInfo.getString("Req_Status");
    							
    							if(approveStatus.equalsIgnoreCase("Approved"))
    							{
    							String approveDate=	rsAppInfo.getString("approved_date");
    							
    							String a[]=approveDate.split(" ");
    							approveDate=a[0];
    							String b[]=approveDate.split("-");
    							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
    							
    							apprvers.setDate(approveDate);
    							}else{
    								String rejectDate=	rsAppInfo.getString("rejected_date");
    								appStatus=1;
    								String a[]=rejectDate.split(" ");
    								rejectDate=a[0];
    								String b[]=rejectDate.split("-");
    								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
    								apprvers.setDate(rejectDate);
    							}
    							String comments=rsAppInfo.getString("Comments");
    							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
    							{
    								apprvers.setComments("");
    							}else{
    								apprvers.setComments(rsAppInfo.getString("Comments"));
    							}
    						}
    						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
    						{
    							apprvers.setApproveStatus("In Process");
    						}
    						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
    						listApprers.add(apprvers);
    				}
    				}
    				request.setAttribute("approverDetails", listApprers);
    				
    				
    				
    				
    				return mapping.findForward("GeneralMaterial_Creator");
					
			     }
				
				if(matType.equalsIgnoreCase("PPC"))
				{
				
	
						
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LANDX"));
						}
						pendAppForm.setCounID(countryID);
						pendAppForm.setCountryName(countryName);
					
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+"-"+rs11.getString("LOCNAME"));
							
						}
						pendAppForm.setLocationIdList(locationList);
						pendAppForm.setLocationLabelList(locationLabelList);
						
						LinkedList divisonID=new LinkedList();
						LinkedList divisonName=new LinkedList();
						String getDivison="select * from DIVISION";
						ResultSet rsDivison=ad.selectQuery(getDivison);
						while(rsDivison.next())
						{
							divisonID.add(rsDivison.getString("DIV_CODE"));
							divisonName.add(rsDivison.getString("DIV_DESC"));
						}
						pendAppForm.setDivisonID(divisonID);
						pendAppForm.setDivisonName(divisonName);
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
						}
						pendAppForm.setMaterTypeIDList(materTypeIDList);
						pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+"-"+rsMaterialGroup.getString("STXT"));
						}
						pendAppForm.setMaterGroupIDList(materGroupIDList);
						pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+"-"+rsUnit.getString("LTXT"));
						}
						pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
						pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='PPC'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						pendAppForm.setStorageID(storageID);
						pendAppForm.setStorageIDName(storageName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						pendAppForm.setPuchaseGroupIdList(puchaseGroupIdList);
						pendAppForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						
						String getValuation="select * from VALUATION_CLASS ";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
						}
						pendAppForm.setValuationClassID(valuationClassID);
						pendAppForm.setValuationClassName(valuationClassName);
						
			// get material records
				
						String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getFinishedProduct);
						while(rs.next())
						{
							pendAppForm.setRequestNo(reqId);
							pendAppForm.setRequestNumber(reqId);
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							pendAppForm.setRequestDate(reqDate);
							pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
							pendAppForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
							pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
							pendAppForm.setDivisionId(rs.getString("DIVISION_ID"));
							String isAsset=rs.getString("is_asset");
							if(isAsset.equalsIgnoreCase("1"))
							{
								pendAppForm.setIsAsset("1");
							}
							if(isAsset.equalsIgnoreCase("0"))
								pendAppForm.setIsAsset("0");
							
							pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
							pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
							pendAppForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
							pendAppForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
							pendAppForm.setHsnCode(rs.getString("HSN_Code"));
							
							String purposeId=rs.getString("PURPOSE_ID");
							purposeId=purposeId.replace(" ","");
							pendAppForm.setPurposeID(purposeId);
							String isSAS=rs.getString("IS_SAS_FORM_AVAILABLE");
							
							if(isSAS.equalsIgnoreCase("1"))
							{
								pendAppForm.setIsSASFormAvailable("1");
							}
							if(isSAS.equalsIgnoreCase("0")){
								pendAppForm.setIsSASFormAvailable("0");}
							
							String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null){
						pendAppForm.setSapCodeNo(sapCodeno);		
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							pendAppForm.setSapCodeExists("True");
						}
						if(sapCodeExist.equalsIgnoreCase("0"))
							pendAppForm.setSapCodeExistsNo("True");
						String sapCreationDate=rs.getString("SAP_CREATION_DATE");
						
						String sapDate[]=sapCreationDate.split(" ");
						sapCreationDate=sapDate[0];
						String sapCode[]=sapCreationDate.split("-");
						sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
						pendAppForm.setSapCreationDate(sapCreationDate);
						pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
						pendAppForm.setRequestedBy(rs.getString("REQUESTED_BY"));
							}
							
						}
						
						

					
					
					if(filterBy.equalsIgnoreCase("Pending")){
    					request.setAttribute("approveButton", "approveButton");
    					request.setAttribute("rejectButton", "rejectButton");
    				}
    				
    				//set ApproverDetails 
    				checkStatus=0;int appStatus=0;
    				LinkedList listApprers=new LinkedList();
    				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
    				"where mast.Location='"+location+"' AND  mast.Material_Type='ZPPC' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
    				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
    				while(rsApprDetails.next())
    				{
    					checkStatus=1;
    					ApprovalsForm apprvers=new ApprovalsForm();
    					apprvers.setPriority(rsApprDetails.getString("Priority"));
    					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
    					String empCode=rsApprDetails.getString("Approver_Id");
    					String approveStatus="";
    					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
    					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
    					"mast.Material_Type='ZPPC' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
    					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
    					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
    					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
    					while(rsAppInfo.next())
    					{
    						
    						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
    						approveStatus=rsAppInfo.getString("Req_Status");
    						if(approveStatus.equalsIgnoreCase(""))
    						{
    							apprvers.setApproveStatus("In Process");
    						}
    						if(approveStatus.equalsIgnoreCase("Approved"))
    						{
    						apprvers.setDate(rsAppInfo.getString("approved_date"));
    						}else{
    							apprvers.setDate(rsAppInfo.getString("rejected_date"));
    							appStatus=1;
    						}
    						String comments=rsAppInfo.getString("Comments");
    						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
    						{
    							apprvers.setComments("");
    						}else{
    							apprvers.setComments(rsAppInfo.getString("Comments"));
    						}
    						
    						
    					}
    					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
    					{
    						apprvers.setApproveStatus("In Process");
    					}
    					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
    					listApprers.add(apprvers);
    				}
    				if(checkStatus==0)
    				{
    					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
    					"where mast.Location='"+location+"' AND  mast.Material_Type='ZPPC' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
    					rsApprDetails=ad.selectQuery(getApprDetails);
    					while(rsApprDetails.next())
    					{
    						checkStatus=1;
    						ApprovalsForm apprvers=new ApprovalsForm();
    						apprvers.setPriority(rsApprDetails.getString("Priority"));
    						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
    						String empCode=rsApprDetails.getString("Approver_Id");
    						String approveStatus="";
    						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
    						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
    						"mast.Material_Type='ZPPC' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
    						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
    						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
    						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
    						while(rsAppInfo.next())
    						{
    							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
    							approveStatus=rsAppInfo.getString("Req_Status");
    							
    							if(approveStatus.equalsIgnoreCase("Approved"))
    							{
    							String approveDate=	rsAppInfo.getString("approved_date");
    							
    							String a[]=approveDate.split(" ");
    							approveDate=a[0];
    							String b[]=approveDate.split("-");
    							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
    							
    							apprvers.setDate(approveDate);
    							}else{
    								String rejectDate=	rsAppInfo.getString("rejected_date");
    								appStatus=1;
    								String a[]=rejectDate.split(" ");
    								rejectDate=a[0];
    								String b[]=rejectDate.split("-");
    								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
    								apprvers.setDate(rejectDate);
    							}
    							String comments=rsAppInfo.getString("Comments");
    							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
    							{
    								apprvers.setComments("");
    							}else{
    								apprvers.setComments(rsAppInfo.getString("Comments"));
    							}
    						}
    						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
    						{
    							apprvers.setApproveStatus("In Process");
    						}
    						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
    						listApprers.add(apprvers);
    				}
    				}
    				request.setAttribute("approverDetails", listApprers);
    			
    			
    				return mapping.findForward("ZPPC_Creator");
					
				}
				
				if(matType.equalsIgnoreCase("OSE"))
				{

					
							
							String getCountryDetails="select * from Country";
							LinkedList countryID=new LinkedList();
							LinkedList countryName=new LinkedList();
							ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
							while(rsCountryDetails.next()){
								countryID.add(rsCountryDetails.getString("LAND1"));
								countryName.add(rsCountryDetails.getString("LANDX"));
							}
							pendAppForm.setCounID(countryID);
							pendAppForm.setCountryName(countryName);
							
							LinkedList deptID=new LinkedList();
							LinkedList deptName=new LinkedList();
							String getdepartment="select * from department";
							ResultSet rsdepartment=ad.selectQuery(getdepartment);
							while(rsdepartment.next()){
								deptID.add(rsdepartment.getInt("DPTID"));
								deptName.add(rsdepartment.getString("DPTSTXT"));
							}
							
							pendAppForm.setDeptId(deptID);
							pendAppForm.setDeptName(deptName);
							
							String materialType="";
							String valuationType="";
							String getvaluationType="select MATERIAL_TYPE_ID,VALUATION_CLASS from material_code_request where REQUEST_NO='"+reqId+"'";
							ResultSet rsValuationType=ad.selectQuery(getvaluationType);
							while(rsValuationType.next()){
								materialType=rsValuationType.getString("MATERIAL_TYPE_ID");
								valuationType=rsValuationType.getString("VALUATION_CLASS");
							}
							ResultSet rs11 = ad.selectQuery("select LOCID," +
							"LOCNAME,location_code from location");
							ArrayList locationList=new ArrayList();
							ArrayList locationLabelList=new ArrayList();
							
							while(rs11.next()) {
								locationList.add(rs11.getString("LOCID"));
								locationLabelList.add(rs11.getString("LOCATION_CODE")+"-"+rs11.getString("LOCNAME"));
							}
							pendAppForm.setLocationIdList(locationList);
							pendAppForm.setLocationLabelList(locationLabelList);
							
							LinkedList materTypeIDList=new LinkedList();
							LinkedList materialTypeIdValueList=new LinkedList();
							String getMaterials="select * from MATERIAL_TYPE";
							ResultSet rsMaterial=ad.selectQuery(getMaterials);
							while(rsMaterial.next())
							{
								materTypeIDList.add(rsMaterial.getString("id"));
								materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+"-"+rsMaterial.getString("M_DESC"));
							}
							pendAppForm.setMaterTypeIDList(materTypeIDList);
							pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
							
							LinkedList materGroupIDList=new LinkedList();
							LinkedList materialGroupIdValueList=new LinkedList();
							String getMaterialGroup="select * from MATERIAL_GROUP";
							ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
							while(rsMaterialGroup.next())
							{
								materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
								materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+"-"+rsMaterialGroup.getString("STXT"));
							}
							pendAppForm.setMaterGroupIDList(materGroupIDList);
							pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
							
							
							LinkedList unitOfMeasIdList=new LinkedList();
							LinkedList unitOfMeasIdValues=new LinkedList();
							
							String getunitMesurement="select * from UNIT_MESUREMENT";
							ResultSet rsUnit=ad.selectQuery(getunitMesurement);
							while(rsUnit.next())
							{
								unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
								unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
							}
							pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
							pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
							LinkedList storageID=new LinkedList();
							LinkedList storageName=new LinkedList();
							String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='OSE'";
							ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
							while(rsStrogeLocation.next()){
								storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
								storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+"-"+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
							}
							pendAppForm.setStorageID(storageID);
							pendAppForm.setStorageIDName(storageName);
							
							LinkedList valuationClassID=new LinkedList();
							LinkedList valuationClassName=new LinkedList();
							String getValuation="select * from VALUATION_CLASS where MAT_TYPE='OSE'";
							ResultSet rsValuation=ad.selectQuery(getValuation);
							while(rsValuation.next())
							{
								valuationClassID.add(rsValuation.getString("VALUATION_ID"));
								valuationClassName.add(rsValuation.getString("VALUATION_ID")+"-"+rsValuation.getString("VALUATION_DESC"));
							}
							pendAppForm.setValuationClassID(valuationClassID);
							pendAppForm.setValuationClassName(valuationClassName);
							
							LinkedList puchaseGroupIdList=new LinkedList();
							LinkedList puchaseGroupIdValues=new LinkedList();
							
							String getPurchaseGroup="select * from PURCHASE_GROUP";
							ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
							while(rsPurchaseGroup.next())
							{
								puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
								puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+"-"+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
							}
							pendAppForm.setPuchaseGroupIdList(puchaseGroupIdList);
							pendAppForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
							
							String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
							ResultSet rs=ad.selectQuery(getFinishedProduct);
							while(rs.next())
							{

								pendAppForm.setRequestNo(reqId);
								pendAppForm.setRequestNumber(reqId);
								pendAppForm.setReqMaterialGroup(rs.getString("MATERIAL_GROUP_ID"));
								String reqDate=rs.getString("REQUEST_DATE");
								String a[]=reqDate.split(" ");
								reqDate=a[0];
								String b[]=reqDate.split("-");
								reqDate=b[2]+"/"+b[1]+"/"+b[0];
								pendAppForm.setRequestDate(reqDate);
								pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
								pendAppForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
								pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
								pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
								pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
								pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
								pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
								pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
								pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
								pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
								pendAppForm.setMaterialUsedIn(rs.getString("MATERIAL_USED_IN"));
								String isEquipment=rs.getString("IS_EQUIPMENT");
								pendAppForm.setHsnCode(rs.getString("HSN_Code"));
								if(isEquipment.equalsIgnoreCase("1"))
								{
									pendAppForm.setIsEquipment("True");
									
								}
								pendAppForm.setEquipmentName(rs.getString("EQUIPMENT_NAME"));
								pendAppForm.setEquipmentMake(rs.getString("EQUIPMENT_MAKE"));
								if(isEquipment.equalsIgnoreCase("0")){
									pendAppForm.setIsEquipment("False");
								
								}
								String isSpare=rs.getString("IS_SPARE");
								if(isSpare.equalsIgnoreCase("1"))
								{
									pendAppForm.setIsSpare("True");
								
								}
								pendAppForm.setComponentMake(rs.getString("Component_MAKE"));
								pendAppForm.setOemPartNo(rs.getString("OEM_PartNo"));
								if(isSpare.equalsIgnoreCase("0")){
									pendAppForm.setIsSpare("False");
								
									
								}
								pendAppForm.setMoc(rs.getString("moc")); 
								pendAppForm.setRating(rs.getString("rating"));
								pendAppForm.setRange(rs.getString("range"));
								
								pendAppForm.setIsNewEquipment(rs.getString("IS_NEW_Equipment"));
								pendAppForm.setIsItNewFurniture(rs.getString("IS_NEW_Furniture"));
								pendAppForm.setIsItFacility(rs.getString("IS_NEW_Facility"));
								pendAppForm.setIsSpareNewEquipment(rs.getString("IS_Spare_required"));
								
								
								
								pendAppForm.setPrNumber(rs.getString("PR_NUMBER"));
								pendAppForm.setPoNumber(rs.getString("PO_NUMBER"));
								pendAppForm.setUtilizingDept(rs.getString("UTILIZING_DEPT"));
								pendAppForm.setDetailedJustification(rs.getString("DETAILED_JUSTIFICATION"));
								pendAppForm.setDetailedSpecification(rs.getString("DETAILED_SPECIFICATION"));
								pendAppForm.setDimension(rs.getString("DIMENSION"));
								pendAppForm.setPurposeID(rs.getString("PURPOSE_ID"));
								pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								
								
								String sapCodeno=rs.getString("SAP_CODE_NO");
									if(sapCodeno!=null){
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								pendAppForm.setSapCodeNo(sapCodeno);
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									pendAppForm.setSapCodeExists("True");
								}
								if(sapCodeExist.equalsIgnoreCase("0"))
									pendAppForm.setSapCodeExistsNo("True");
								String sapCreationDate=rs.getString("SAP_CREATION_DATE");
								String sapDate[]=sapCreationDate.split(" ");
								sapCreationDate=sapDate[0];
								String sapCode[]=sapCreationDate.split("-");
								sapCreationDate=sapCode[2]+"/"+sapCode[1]+"/"+sapCode[0];
								pendAppForm.setSapCreationDate(sapCreationDate);
								pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
								pendAppForm.setRequestedBy(rs.getString("REQUESTED_BY"));
									}
									
								

							
							}
					
					if(filterBy.equalsIgnoreCase("Pending")){
						request.setAttribute("approveButton", "approveButton");
						request.setAttribute("rejectButton", "rejectButton");
					}
					//set ApproverDetails 
					checkStatus=0;int appStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase(""))
							{
								apprvers.setApproveStatus("In Process");
							}
							if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
								appStatus=1;
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
							
							
						}
						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
					}
					if(checkStatus==0)
					{
						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String approveStatus="";
							String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								approveStatus=rsAppInfo.getString("Req_Status");
								
								if(approveStatus.equalsIgnoreCase("Approved")||approveStatus.equalsIgnoreCase("Created"))
								{
								String approveDate=	rsAppInfo.getString("approved_date");
								
								String a[]=approveDate.split(" ");
								approveDate=a[0];
								String b[]=approveDate.split("-");
								approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								
								apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									appStatus=1;
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							if(approveStatus.equalsIgnoreCase("") && appStatus==0)
							{
								apprvers.setApproveStatus("In Process");
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							listApprers.add(apprvers);
					}
					}
					request.setAttribute("approverDetails", listApprers);
				
					return mapping.findForward("ZPSR_Creator");
				}
				if(matType.equalsIgnoreCase("PM"))
				{

					
				
				String getCountryDetails="select * from Country";
				LinkedList countryID=new LinkedList();
				LinkedList countryName=new LinkedList();
				ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
				while(rsCountryDetails.next()){
					countryID.add(rsCountryDetails.getString("LAND1"));
					countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
				}
				pendAppForm.setCounID(countryID);
				pendAppForm.setCountryName(countryName);
				
				ResultSet rs11 = ad.selectQuery("select * from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
				}
				pendAppForm.setLocationIdList(locationList);
				pendAppForm.setLocationLabelList(locationLabelList);
				
				
				LinkedList storageID=new LinkedList();
				LinkedList storageName=new LinkedList();
				String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='VERP'";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				pendAppForm.setStorageID(storageID);
				pendAppForm.setStorageIDName(storageName);
				
				LinkedList materTypeIDList=new LinkedList();
				LinkedList materialTypeIdValueList=new LinkedList();
				String getMaterials="select * from MATERIAL_TYPE";
				ResultSet rsMaterial=ad.selectQuery(getMaterials);
				while(rsMaterial.next())
				{
					materTypeIDList.add(rsMaterial.getString("id"));
					materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
				}
				pendAppForm.setMaterTypeIDList(materTypeIDList);
				pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
				
				LinkedList materGroupIDList=new LinkedList();
				LinkedList materialGroupIdValueList=new LinkedList();
				
				String getMaterialGroup="select * from MATERIAL_GROUP";
				ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
				while(rsMaterialGroup.next())
				{
					materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
					materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
				}
				pendAppForm.setMaterGroupIDList(materGroupIDList);
				pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
				
				LinkedList unitOfMeasIdList=new LinkedList();
				LinkedList unitOfMeasIdValues=new LinkedList();
				
				String getunitMesurement="select * from UNIT_MESUREMENT";
				ResultSet rsUnit=ad.selectQuery(getunitMesurement);
				while(rsUnit.next())
				{
					unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
					unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
				}
				pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
				pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
				
				LinkedList puchaseGroupIdList=new LinkedList();
				LinkedList puchaseGroupIdValues=new LinkedList();
				
				String getPurchaseGroup="select * from PURCHASE_GROUP";
				ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
				while(rsPurchaseGroup.next())
				{
					puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
					puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
				}
				pendAppForm.setPuchaseGroupIdList(puchaseGroupIdList);
				pendAppForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
				
				LinkedList dmfGradeIDList=new LinkedList();
				LinkedList dmfGradeIDValueList=new LinkedList();
				
				String getDMFValues="select * from DMF_GRADE";
				ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
				while(rsDMFValues.next())
				{
					dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
					dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
				}
				pendAppForm.setDmfGradeIDList(dmfGradeIDList);
				pendAppForm.setDmfGradeIDValueList(dmfGradeIDValueList);
				
				LinkedList packageGroupID=new LinkedList();
				LinkedList packageGroupIDValue=new LinkedList();
				
				String getPackageGroup="select * from PACKAGE_MATERIAL_GROUP";
				ResultSet rsPackageGroup=ad.selectQuery(getPackageGroup);
				
				while(rsPackageGroup.next())
				{
					packageGroupID.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID"));
					packageGroupIDValue.add(rsPackageGroup.getString("PACKING_MATERIAL_GROUP_ID")+" - "+rsPackageGroup.getString("PACKING_MATERIAL_GROUP_NAME"));
				}
				pendAppForm.setPackageGroupID(packageGroupID);
				pendAppForm.setPackageGroupIDValue(packageGroupIDValue);
				
				LinkedList valuationClassID=new LinkedList();
				LinkedList valuationClassName=new LinkedList();
				
				String getValuation="select * from VALUATION_CLASS where MAT_TYPE='VERP'";
				ResultSet rsValuation=ad.selectQuery(getValuation);
				while(rsValuation.next())
				{
					valuationClassID.add(rsValuation.getString("VALUATION_ID"));
					valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
				}
				pendAppForm.setValuationClassID(valuationClassID);
				pendAppForm.setValuationClassName(valuationClassName);
				
				LinkedList tempIDList=new LinkedList();
				LinkedList temValueList=new LinkedList();
				String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
				ResultSet rsTemp=ad.selectQuery(getTemp);
				while(rsTemp.next())
				{
					tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
					temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
				}
				pendAppForm.setTempIDList(tempIDList);
				pendAppForm.setTemValueList(temValueList);
				
				LinkedList storageIDList=new LinkedList();
				LinkedList storageLocList=new LinkedList();
				String getStorageLoc="select * from STORAGE_CONDITION";
				ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
				while(rsStorageLoc.next())
				{
					storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
					storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
				}
				pendAppForm.setStorageIDList(storageIDList);
				pendAppForm.setStorageLocList(storageLocList);
			// get material records
				
				String getMaterial="select * from material_code_request where REQUEST_NO='"+reqId+"'";
				ResultSet rs=ad.selectQuery(getMaterial);
				while(rs.next())
				{
					pendAppForm.setRequestNo(reqId);
					pendAppForm.setRequestNumber(reqId);
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					pendAppForm.setRequestDate(reqDate);
					pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
					pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
				
					pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
					String isDMFMaterial=rs.getString("IS_DMF_MATERIAL");
					if(isDMFMaterial.equalsIgnoreCase("1"))
					{
						pendAppForm.setIsDMFMaterial("True");
						request.setAttribute("dmfMandatory", "dmfMandatory");
						pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
						pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
					}
					if(isDMFMaterial.equalsIgnoreCase("0")){
						pendAppForm.setIsDMFMaterial("False");
						pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
						pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
						request.setAttribute("dmfNotMandatory", "dmfNotMandatory");
					}
					pendAppForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
					
					pendAppForm.setCountryId(rs.getString("COUNTRY_ID"));
					pendAppForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					pendAppForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
					String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
					if(isVendorStatus.equalsIgnoreCase("1"))
					{
						pendAppForm.setIsVendorSpecificMaterial("True");
						request.setAttribute("vedorMandatory", "vedorMandatory");
						pendAppForm.setMfgrName(rs.getString("MFGR_NAME"));
						pendAppForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
					}
					if(isVendorStatus.equalsIgnoreCase("0"))
					{
						pendAppForm.setIsVendorSpecificMaterial("False");
						request.setAttribute("vedorNotMandatory", "vedorNotMandatory");
					}
					pendAppForm.setMfgrName(rs.getString("MFGR_NAME"));
					pendAppForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
					pendAppForm.setTempCondition(rs.getString("TEMP_CONDITION"));
					pendAppForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
					pendAppForm.setRetestDays(rs.getString("RETEST_DAYS"));
					pendAppForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
	               pendAppForm.setDutyElement(rs.getString("DUTY_ELEMENT"));
					
					pendAppForm.setPackageMaterialGroup(rs.getString("PACKING_MATERIAL_GROUP"));
					pendAppForm.setTypeOfMaterial(rs.getString("Type_Of_Material"));
					String typeOfMaterial=rs.getString("Type_Of_Material");
					
					if(typeOfMaterial.equalsIgnoreCase("Printed Material"))
					{
						request.setAttribute("materialTypeMandatory", "materialTypeMandatory");
						pendAppForm.setArtworkNo(rs.getString("ARTWORK_NO"));
						String isARTWORKRevisionStatus=rs.getString("IS_ARTWORK_REVISION");
						if(isARTWORKRevisionStatus.equalsIgnoreCase("1"))
						{
							pendAppForm.setIsArtworkRevision("True");
						}
						if(isARTWORKRevisionStatus.equalsIgnoreCase("0"))
							pendAppForm.setIsArtworkRevision("False");
						
					}
					else
						request.setAttribute("materialTypeNotMandatory", "materialTypeNotMandatory");
					
					pendAppForm.setExistingSAPItemCode(rs.getString("EXISTING_SAP_ITEM_CODE"));
					pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
				
				
					ArrayList fileList = new ArrayList();
					String uploadedFiles=rs.getString("Attachements");
					pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
					pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
					pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
					if(uploadedFiles.equalsIgnoreCase(""))
					{
					}else{
					String v[] = uploadedFiles.split(",");
					int l = v.length;
					for (int i = 0; i < l; i++) 
					{
						PackageMaterialMasterForm pendAppForm2=new PackageMaterialMasterForm();
					int x=v[i].lastIndexOf("/");
					uploadedFiles=v[i].substring(x+1);		
					pendAppForm2.setFileList(uploadedFiles);
					pendAppForm2.setFilepath("jsp/EMicro Files/ESS/sapMasterRequest/PackageMaterial Files/UploadFiles/"+uploadedFiles+"");
					fileList.add(pendAppForm2);
					}
					
					
					request.setAttribute("listName", fileList);
					}
					
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						pendAppForm.setSapCodeNo(sapCodeno);
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							pendAppForm.setSapCodeExists("True");
						}else{
							pendAppForm.setSapCodeExistsNo("True");
						}
						
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						pendAppForm.setSapCreationDate(createDate);
						pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
				
					
					
				
				}
					
				if(filterBy.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
				}
				
				
				
				
				
				//set ApproverDetails 
				checkStatus=0;int appStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
							appStatus=1;
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
						
						
					}
					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
				if(checkStatus==0)
				{
					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							apprvers.setDate(approveDate);
							}else{
								String rejectDate=	rsAppInfo.getString("rejected_date");
								appStatus=1;
								String a[]=rejectDate.split(" ");
								rejectDate=a[0];
								String b[]=rejectDate.split("-");
								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(rejectDate);
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
				}
				}
				request.setAttribute("approverDetails", listApprers);
				
			
			
			
				return mapping.findForward("VERP_Creator");
					
				}
				
				if(matType.equalsIgnoreCase("HALB")){
					
						//creator semifinished
						
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
						}
						pendAppForm.setCounID(countryID);
						pendAppForm.setCountryName(countryName);
						
						LinkedList weightUOMID=new LinkedList();
						LinkedList weightUOMName=new LinkedList();
						String getweightUOM="select * from WEIGHT_UOM";
						ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
						while(rsweightUOM.next())
						{
							weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
							weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
						}
						pendAppForm.setWeightUOMID(weightUOMID);
						pendAppForm.setWeightUOMName(weightUOMName);
						
						LinkedList packSizeID=new LinkedList();
						LinkedList packSizeName=new LinkedList();
						String getPackSize="select * from PACK_SIZE";
						ResultSet rsPackSize=ad.selectQuery(getPackSize);
						while(rsPackSize.next())
						{
							packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
							packSizeName.add(rsPackSize.getString("PACK_SIZE_DESC"));
						}
						pendAppForm.setPackSizeID(packSizeID);
						pendAppForm.setPackSizeName(packSizeName);
					
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						
						
						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						pendAppForm.setMaterTypeIDList(materTypeIDList);
						pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						
						
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						pendAppForm.setLocationIdList(locationList);
						pendAppForm.setLocationLabelList(locationLabelList);
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='HALB'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						pendAppForm.setStorageID(storageID);
						pendAppForm.setStorageIDName(storageName);
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						pendAppForm.setMaterGroupIDList(materGroupIDList);
						pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
						pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='HALB'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						pendAppForm.setValuationClassID(valuationClassID);
						pendAppForm.setValuationClassName(valuationClassName);
						String getSemiFinished="select * from material_code_request where REQUEST_NO='"+reqId+"'";
						ResultSet rs=ad.selectQuery(getSemiFinished);
						while(rs.next())
						{

							pendAppForm.setRequestNo(reqId);
							pendAppForm.setRequestNumber(reqId);
							String reqDate=rs.getString("REQUEST_DATE");
							String a[]=reqDate.split(" ");
							reqDate=a[0];
							String b[]=reqDate.split("-");
							reqDate=b[2]+"/"+b[1]+"/"+b[0];
							pendAppForm.setRequestDate(reqDate);
							
							pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
							pendAppForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
							pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
							pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
							pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
							pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
							
							pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
					 		pendAppForm.setPackSize(rs.getString("PACK_SIZE"));
					 		pendAppForm.setCountryId(rs.getString("COUNTRY_ID"));
					 		pendAppForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					 		pendAppForm.setShelfLife(rs.getString("SHELF_LIFE"));
					 		pendAppForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					 		pendAppForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					 		pendAppForm.setBatchCode(rs.getString("BATCH_CODE"));
					 		pendAppForm.setTargetWeight(rs.getString("TARGET_WEIGHT"));
					 		pendAppForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
					 		
					 		pendAppForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
					 		pendAppForm.setRetestDays(rs.getString("RETEST_DAYS"));
					 		pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
					 		
					 		String sapCodeno=rs.getString("SAP_CODE_NO");
							if(sapCodeno!=null)
							{
								pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
								String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
								if(sapCodeExist.equalsIgnoreCase("1"))
								{
									pendAppForm.setSapCodeExists("True");
								}else{
									pendAppForm.setSapCodeExistsNo("True");
								}
							
								String createDate=rs.getString("SAP_CREATION_DATE");
								String a1[]=createDate.split(" ");
								createDate=a1[0];
								String b1[]=createDate.split("-");
								createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
								pendAppForm.setSapCreationDate(createDate);
								pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
							}
						
						}
						if(filterBy.equalsIgnoreCase("Pending")){
							request.setAttribute("approveButton", "approveButton");
							request.setAttribute("rejectButton", "rejectButton");
						}
						
					
					
					//set ApproverDetails 
					checkStatus=0;int appStatus=0;
					LinkedList listApprers=new LinkedList();
					String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
					ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							if(approveStatus.equalsIgnoreCase(""))
							{
								apprvers.setApproveStatus("In Process");
							}
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
							apprvers.setDate(rsAppInfo.getString("approved_date"));
							}else{
								apprvers.setDate(rsAppInfo.getString("rejected_date"));
								appStatus=1;
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
							
							
						}
						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
					}
					if(checkStatus==0)
					{
						getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
						"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
						rsApprDetails=ad.selectQuery(getApprDetails);
						while(rsApprDetails.next())
						{
							checkStatus=1;
							ApprovalsForm apprvers=new ApprovalsForm();
							apprvers.setPriority(rsApprDetails.getString("Priority"));
							apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
							String empCode=rsApprDetails.getString("Approver_Id");
							String approveStatus="";
							String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
							"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
							"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
							"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
							"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
							ResultSet rsAppInfo=ad.selectQuery(recordStatus);
							while(rsAppInfo.next())
							{
								apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
								approveStatus=rsAppInfo.getString("Req_Status");
								
								if(approveStatus.equalsIgnoreCase("Approved"))
								{
								String approveDate=	rsAppInfo.getString("approved_date");
								
								String a[]=approveDate.split(" ");
								approveDate=a[0];
								String b[]=approveDate.split("-");
								approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								
								apprvers.setDate(approveDate);
								}else{
									String rejectDate=	rsAppInfo.getString("rejected_date");
									appStatus=1;
									String a[]=rejectDate.split(" ");
									rejectDate=a[0];
									String b[]=rejectDate.split("-");
									rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
									apprvers.setDate(rejectDate);
								}
								String comments=rsAppInfo.getString("Comments");
								if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
								{
									apprvers.setComments("");
								}else{
									apprvers.setComments(rsAppInfo.getString("Comments"));
								}
							}
							if(approveStatus.equalsIgnoreCase("") && appStatus==0)
							{
								apprvers.setApproveStatus("In Process");
							}
							apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
							listApprers.add(apprvers);
					}
					}
					request.setAttribute("approverDetails", listApprers);
				
					return mapping.findForward("HALB_Creator");
				}
				if(matType.equalsIgnoreCase("FG") || matType.equalsIgnoreCase("HAWA") ){

					LinkedList finDetails=new LinkedList();
					
				
						
						LinkedList packTypeID=new LinkedList();
						LinkedList packTypeName=new LinkedList();
						
						String getPackType="select * from PACK_TYPE";
						ResultSet rsPackType=ad.selectQuery(getPackType);
						while(rsPackType.next())
						{
							packTypeID.add(rsPackType.getString("P_TYPE_CODE"));
							packTypeName.add(rsPackType.getString("P_TYPE_CODE")+" - "+rsPackType.getString("P_TYPE_DESC"));
						}
						pendAppForm.setPackTypeID(packTypeID);
						pendAppForm.setPackTypeName(packTypeName);
					
						LinkedList packSizeID=new LinkedList();
						LinkedList packSizeName=new LinkedList();
						String getPackSize="select * from PACK_SIZE";
						ResultSet rsPackSize=ad.selectQuery(getPackSize);
						while(rsPackSize.next())
						{
							packSizeID.add(rsPackSize.getString("PACK_SIZE_CODE"));
							packSizeName.add(rsPackSize.getString("PACK_SIZE_CODE") );
						}
						pendAppForm.setPackSizeID(packSizeID);
						pendAppForm.setPackSizeName(packSizeName);
						
						LinkedList valuationClassID=new LinkedList();
						LinkedList valuationClassName=new LinkedList();
						String getValuation="select * from VALUATION_CLASS where MAT_TYPE='"+matType+"'";
						ResultSet rsValuation=ad.selectQuery(getValuation);
						while(rsValuation.next())
						{
							valuationClassID.add(rsValuation.getString("VALUATION_ID"));
							valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
						}
						pendAppForm.setValuationClassID(valuationClassID);
						pendAppForm.setValuationClassName(valuationClassName);
						
						LinkedList salesUOMID=new LinkedList();
						LinkedList salesUOMName=new LinkedList();
						String getSalesUOM="select * from SALES_UOM order by S_UOM_DESC";
						ResultSet rsSalesUOM=ad.selectQuery(getSalesUOM);
						while(rsSalesUOM.next())
						{
							salesUOMID.add(rsSalesUOM.getString("S_UOM_CODE"));
							packSizeName.add(rsSalesUOM.getString("S_UOM_CODE")+" - "+rsSalesUOM.getString("S_UOM_DESC"));
						}
						pendAppForm.setSalesUOMID(salesUOMID);
						pendAppForm.setSalesUOMName(salesUOMName);

						LinkedList divisonID=new LinkedList();
						LinkedList divisonName=new LinkedList();
						String getDivison="select * from DIVISION";
						ResultSet rsDivison=ad.selectQuery(getDivison);
						while(rsDivison.next())
						{
							divisonID.add(rsDivison.getString("DIV_CODE"));
							divisonName.add(rsDivison.getString("DIV_DESC"));
						}
						pendAppForm.setDivisonID(divisonID);
						pendAppForm.setDivisonName(divisonName);
						
						LinkedList therapeuticID=new LinkedList();
						LinkedList therapeuticName=new LinkedList();
						String getrstherapeutic="select * from THERAPEUTIC_SEGMENT";
						ResultSet rstherapeutic=ad.selectQuery(getrstherapeutic);
						while(rstherapeutic.next())
						{
							therapeuticID.add(rstherapeutic.getString("THER_SEG_CODE"));
							therapeuticName.add(rstherapeutic.getString("THER_SEG_DESC"));
						}
						pendAppForm.setTherapeuticID(therapeuticID);
						pendAppForm.setTherapeuticName(therapeuticName);
						
						LinkedList brandIDList=new LinkedList();
						LinkedList brandNameList=new LinkedList();
						String getBrand="select * from BRAND order by BRAND_DESC";
						ResultSet rsBrand=ad.selectQuery(getBrand);
						while(rsBrand.next())
						{
							brandIDList.add(rsBrand.getString("BRAND_CODE"));
							brandNameList.add(rsBrand.getString("BRAND_DESC"));
						}
						pendAppForm.setBrandIDList(brandIDList);
						pendAppForm.setBrandNameList(brandNameList);
						
						LinkedList strengthIDList=new LinkedList();
						LinkedList strengthNameList=new LinkedList();
						String getStrength="select * from STRENGTH";
						ResultSet rsStrength=ad.selectQuery(getStrength);
						while(rsStrength.next())
						{
							strengthIDList.add(rsStrength.getString("STRENGTH_CODE"));
							strengthNameList.add(rsStrength.getString("STRENGTH_DESC"));
						}
						pendAppForm.setStrengthIDList(strengthIDList);
						pendAppForm.setStrengthNameList(strengthNameList);
						
						LinkedList genericIDList=new LinkedList();
						LinkedList genericNameList=new LinkedList();
						String getGeneric="select * from GENERIC_NAME";
						ResultSet rsGeneric=ad.selectQuery(getGeneric);
						while(rsGeneric.next())
						{
							genericIDList.add(rsGeneric.getString("GEN_NAME_CODE"));
							genericNameList.add(rsGeneric.getString("GEN_NAME_DESC"));
						}
						pendAppForm.setGenericIDList(genericIDList);
						pendAppForm.setGenericNameList(genericNameList);
						
						LinkedList weightUOMID=new LinkedList();
						LinkedList weightUOMName=new LinkedList();
						String getweightUOM="select * from WEIGHT_UOM";
						ResultSet rsweightUOM=ad.selectQuery(getweightUOM);
						while(rsweightUOM.next())
						{
							weightUOMID.add(rsweightUOM.getString("W_UOM_CODE"));
							weightUOMName.add(rsweightUOM.getString("W_UOM_CODE")+" - "+rsweightUOM.getString("W_UOM_DESC"));
						}
						pendAppForm.setWeightUOMID(weightUOMID);
						pendAppForm.setWeightUOMName(weightUOMName);
						
						LinkedList puchaseGroupIdList=new LinkedList();
						LinkedList puchaseGroupIdValues=new LinkedList();
						
						String getPurchaseGroup="select * from PURCHASE_GROUP";
						ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
						while(rsPurchaseGroup.next())
						{
							puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
							puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
						}
						pendAppForm.setPuchaseGroupIdList(puchaseGroupIdList);
						pendAppForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
						
						
						
						LinkedList materTypeIDList=new LinkedList();
						LinkedList materialTypeIdValueList=new LinkedList();
						String materialTypeId=request.getParameter("materialType");

						String getMaterials="select * from MATERIAL_TYPE";
						ResultSet rsMaterial=ad.selectQuery(getMaterials);
						while(rsMaterial.next())
						{
							
							materTypeIDList.add(rsMaterial.getString("id"));
							materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
						}
						pendAppForm.setMaterTypeIDList(materTypeIDList);
						pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
						
						LinkedList storageID=new LinkedList();
						LinkedList storageName=new LinkedList();
						String getStorageDetails="select * from STORAGE_LOCATION where  MAT_TYPE='"+matType+"'";
						ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
						while(rsStrogeLocation.next()){
							storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
							storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
						}
						pendAppForm.setStorageID(storageID);
						pendAppForm.setStorageIDName(storageName);
						String getCountryDetails="select * from Country";
						LinkedList countryID=new LinkedList();
						LinkedList countryName=new LinkedList();
						ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
						while(rsCountryDetails.next()){
							countryID.add(rsCountryDetails.getString("LAND1"));
							countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
						}
						pendAppForm.setCounID(countryID);
						pendAppForm.setCountryName(countryName);
						
						ResultSet rs11 = ad.selectQuery("select * from location");
						ArrayList locationList=new ArrayList();
						ArrayList locationLabelList=new ArrayList();
						
						while(rs11.next()) {
							locationList.add(rs11.getString("LOCID"));
							locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
						}
						pendAppForm.setLocationIdList(locationList);
						pendAppForm.setLocationLabelList(locationLabelList);
						
						LinkedList materGroupIDList=new LinkedList();
						LinkedList materialGroupIdValueList=new LinkedList();
						
						String getMaterialGroup="select * from MATERIAL_GROUP";
						ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
						while(rsMaterialGroup.next())
						{
							materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
							materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
						}
						pendAppForm.setMaterGroupIDList(materGroupIDList);
						pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
						
						
						
						LinkedList unitOfMeasIdList=new LinkedList();
						LinkedList unitOfMeasIdValues=new LinkedList();
						
						String getunitMesurement="select * from UNIT_MESUREMENT";
						ResultSet rsUnit=ad.selectQuery(getunitMesurement);
						while(rsUnit.next())
						{
							unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
							unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
						}
						pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
						pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
						
			// get material records
				
				String getFinishedProduct="select * from material_code_request where REQUEST_NO='"+reqId+"'";
				ResultSet rs=ad.selectQuery(getFinishedProduct);
				if(rs.next())
				{
					pendAppForm.setRequestNo(reqId);
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					pendAppForm.setRequestDate(reqDate);
					pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
					pendAppForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
					
					String manufacturedAt=rs.getString("MANUFACTURED_AT");
					
					if(manufacturedAt.equalsIgnoreCase("Third Party"))
					{
						request.setAttribute("standardBathcNotMandatory", "standardBathcNotMandatory");
						request.setAttribute("manufactureMandatory", "manufactureMandatory");
					}
					else{
						request.setAttribute("standardBathcMandatory", "standardBathcMandatory");
						request.setAttribute("manufactureNotMandatory", "manufactureNotMandatory");
					}
					
					pendAppForm.setManufacturedAt(manufacturedAt);
					pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
					pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME"));
					pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
					pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
					pendAppForm.setCountryId(rs.getString("COUNTRY_ID"));
					pendAppForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					pendAppForm.setShelfLife(rs.getString("SHELF_LIFE"));
					pendAppForm.setShelfType(rs.getString("SHELF_LIFE_TYPE"));
					pendAppForm.setStandardBatchSize(rs.getString("STANDARD_BATCH_SIZE"));
					pendAppForm.setBatchCode(rs.getString("BATCH_CODE"));
					pendAppForm.setSaleableOrSample(rs.getString("SALEABLE_OR_SAMPLE"));
					pendAppForm.setDomesticOrExports(rs.getString("DOMESTIC_OR_EXPORTS"));
					String salesPackId=rs.getString("SALES_PACK_ID");
					pendAppForm.setSalesPackId(rs.getString("SALES_PACK_ID"));
					pendAppForm.setPackTypeId(rs.getString("PACK_TYPE_ID"));
					pendAppForm.setSalesUnitOfMeaseurement(rs.getString("SALES_UNIT_OF_MEAS_ID"));
					pendAppForm.setDivisionId(rs.getString("DIVISION_ID"));
					pendAppForm.setTherapeuticSegmentID(rs.getString("THERAPEUTIC_SEGMENT_ID"));
					pendAppForm.setBrandID(rs.getString("BRAND_ID"));
					pendAppForm.setSrengthId(rs.getString("STRENGTH_ID"));
					pendAppForm.setGenericName(rs.getString("GENERIC_NAME"));
					pendAppForm.setProdInspMemo(rs.getString("PROD_INSP_MEMO"));
					pendAppForm.setTaxClassification(rs.getString("Tax_Classification"));
					pendAppForm.setMaterialPricing(rs.getString("Material_Pricing"));
					pendAppForm.setGrossWeight(rs.getString("GROSS_WEIGHT"));
					pendAppForm.setNetWeight(rs.getString("NET_WEIGHT"));
					pendAppForm.setWeightUOM(rs.getString("WEIGHT_UOM"));
					pendAppForm.setDimension(rs.getString("DIMENSION"));
					pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
					pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
				
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							pendAppForm.setSapCodeExists("True");
						}else{
							pendAppForm.setSapCodeExistsNo("True");
						}
					
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						pendAppForm.setSapCreationDate(createDate);
						pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
					
					
				}
					
				if(filterBy.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
				}
				
				
				
				
				
				//set ApproverDetails 
				checkStatus=0;int appStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='"+matType+"' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
							appStatus=1;
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
						
						
					}
					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
				if(checkStatus==0)
				{
					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='"+matType+"' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='"+matType+"' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							apprvers.setDate(approveDate);
							}else{
								String rejectDate=	rsAppInfo.getString("rejected_date");
								appStatus=1;
								String a[]=rejectDate.split(" ");
								rejectDate=a[0];
								String b[]=rejectDate.split("-");
								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(rejectDate);
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
				}
				}
				request.setAttribute("approverDetails", listApprers);
				
								
				return mapping.findForward("finishedProduct_Creator");

								}
				
				if(matType.equalsIgnoreCase("RM"))
				{
				
						
				String getCountryDetails="select * from Country";
				LinkedList countryID=new LinkedList();
				LinkedList countryName=new LinkedList();
				ResultSet rsCountryDetails=ad.selectQuery(getCountryDetails);
				while(rsCountryDetails.next()){
					countryID.add(rsCountryDetails.getString("LAND1"));
					countryName.add(rsCountryDetails.getString("LAND1")+" - "+rsCountryDetails.getString("LANDX"));
				}
				pendAppForm.setCounID(countryID);
				pendAppForm.setCountryName(countryName);
				
				ResultSet rs11 = ad.selectQuery("select * from location");
				ArrayList locationList=new ArrayList();
				ArrayList locationLabelList=new ArrayList();
				
				while(rs11.next()) {
					locationList.add(rs11.getString("LOCID"));
					locationLabelList.add(rs11.getString("LOCATION_CODE")+" - "+rs11.getString("LOCNAME"));
				}
				pendAppForm.setLocationIdList(locationList);
				pendAppForm.setLocationLabelList(locationLabelList);
				
				
				LinkedList storageID=new LinkedList();
				LinkedList storageName=new LinkedList();
				String getStorageDetails="select * from STORAGE_LOCATION where MAT_TYPE='ROH'";
				ResultSet rsStrogeLocation=ad.selectQuery(getStorageDetails);
				while(rsStrogeLocation.next()){
					storageID.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID"));
					storageName.add(rsStrogeLocation.getString("STORAGE_LOCATION_ID")+" - "+rsStrogeLocation.getString("STORAGE_LOCATION_NAME"));
				}
				pendAppForm.setStorageID(storageID);
				pendAppForm.setStorageIDName(storageName);
				
				
				
				LinkedList materTypeIDList=new LinkedList();
				LinkedList materialTypeIdValueList=new LinkedList();
				String getMaterials="select * from MATERIAL_TYPE";
				ResultSet rsMaterial=ad.selectQuery(getMaterials);
				while(rsMaterial.next())
				{
					materTypeIDList.add(rsMaterial.getString("id"));
					materialTypeIdValueList.add(rsMaterial.getString("MATERIAL_GROUP_ID")+" - "+rsMaterial.getString("M_DESC"));
				}
				pendAppForm.setMaterTypeIDList(materTypeIDList);
				pendAppForm.setMaterialTypeIdValueList(materialTypeIdValueList);
				
				LinkedList materGroupIDList=new LinkedList();
				LinkedList materialGroupIdValueList=new LinkedList();
				
				String getMaterialGroup="select * from MATERIAL_GROUP";
				ResultSet rsMaterialGroup=ad.selectQuery(getMaterialGroup);
				while(rsMaterialGroup.next())
				{
					materGroupIDList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID"));
					materialGroupIdValueList.add(rsMaterialGroup.getString("MATERIAL_GROUP_ID")+" - "+rsMaterialGroup.getString("STXT"));
				}
				pendAppForm.setMaterGroupIDList(materGroupIDList);
				pendAppForm.setMaterialGroupIdValueList(materialGroupIdValueList);
				
				LinkedList unitOfMeasIdList=new LinkedList();
				LinkedList unitOfMeasIdValues=new LinkedList();
				
				String getunitMesurement="select * from UNIT_MESUREMENT";
				ResultSet rsUnit=ad.selectQuery(getunitMesurement);
				while(rsUnit.next())
				{
					unitOfMeasIdList.add(rsUnit.getString("UNIT_OF_MEAS_ID"));
					unitOfMeasIdValues.add(rsUnit.getString("UNIT_OF_MEAS_ID")+" - "+rsUnit.getString("LTXT"));
				}
				pendAppForm.setUnitOfMeasIdList(unitOfMeasIdList);
				pendAppForm.setUnitOfMeasIdValues(unitOfMeasIdValues);
				
				LinkedList puchaseGroupIdList=new LinkedList();
				LinkedList puchaseGroupIdValues=new LinkedList();
				
				String getPurchaseGroup="select * from PURCHASE_GROUP";
				ResultSet rsPurchaseGroup=ad.selectQuery(getPurchaseGroup);
				while(rsPurchaseGroup.next())
				{
					puchaseGroupIdList.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID"));
					puchaseGroupIdValues.add(rsPurchaseGroup.getString("PURCHASE_GROUP_ID")+" - "+rsPurchaseGroup.getString("PURCHASE_GROUP_DESC"));
				}
				pendAppForm.setPuchaseGroupIdList(puchaseGroupIdList);
				pendAppForm.setPuchaseGroupIdValues(puchaseGroupIdValues);
				
				LinkedList dmfGradeIDList=new LinkedList();
				LinkedList dmfGradeIDValueList=new LinkedList();
				
				String getDMFValues="select * from DMF_GRADE";
				ResultSet rsDMFValues=ad.selectQuery(getDMFValues);
				while(rsDMFValues.next())
				{
					dmfGradeIDList.add(rsDMFValues.getString("DMF_GRADE_ID"));
					dmfGradeIDValueList.add(rsDMFValues.getString("DMF_GRADE_ID")+" - "+rsDMFValues.getString("DMF_GRADE_DESC"));
				}
				pendAppForm.setDmfGradeIDList(dmfGradeIDList);
				pendAppForm.setDmfGradeIDValueList(dmfGradeIDValueList);
				
				
				LinkedList valuationClassID=new LinkedList();
				LinkedList valuationClassName=new LinkedList();
				
				String getValuation="select * from VALUATION_CLASS where MAT_TYPE='ROH'";
				ResultSet rsValuation=ad.selectQuery(getValuation);
				while(rsValuation.next())
				{
					valuationClassID.add(rsValuation.getString("VALUATION_ID"));
					valuationClassName.add(rsValuation.getString("VALUATION_ID")+" - "+rsValuation.getString("VALUATION_DESC"));
				}
				pendAppForm.setValuationClassID(valuationClassID);
				pendAppForm.setValuationClassName(valuationClassName);
				
				LinkedList tempIDList=new LinkedList();
				LinkedList temValueList=new LinkedList();
				String getTemp="select * from TEMP_CONDITION order by TEMP_CON_DESC";
				ResultSet rsTemp=ad.selectQuery(getTemp);
				while(rsTemp.next())
				{
					tempIDList.add(rsTemp.getString("TEMP_CON_ID"));
					temValueList.add(rsTemp.getString("TEMP_CON_ID")+" - "+rsTemp.getString("TEMP_CON_DESC"));
				}
				pendAppForm.setTempIDList(tempIDList);
				pendAppForm.setTemValueList(temValueList);
				
				LinkedList storageIDList=new LinkedList();
				LinkedList storageLocList=new LinkedList();
				String getStorageLoc="select * from STORAGE_CONDITION";
				ResultSet rsStorageLoc=ad.selectQuery(getStorageLoc);
				while(rsStorageLoc.next())
				{
					storageIDList.add(rsStorageLoc.getString("STO_COND_CODE"));
					storageLocList.add(rsStorageLoc.getString("STO_COND_CODE")+" - "+rsStorageLoc.getString("LTXT"));
				}
				pendAppForm.setStorageIDList(storageIDList);
				pendAppForm.setStorageLocList(storageLocList);
			// get material records
				
				String getMaterial="select * from material_code_request where REQUEST_NO='"+reqId+"'";
				ResultSet rs=ad.selectQuery(getMaterial);
				while(rs.next())
				{
					pendAppForm.setRequestNumber(reqId);
					String reqDate=rs.getString("REQUEST_DATE");
					String a[]=reqDate.split(" ");
					reqDate=a[0];
					String b[]=reqDate.split("-");
					reqDate=b[2]+"/"+b[1]+"/"+b[0];
					pendAppForm.setRequestDate(reqDate);
					pendAppForm.setLocationId(rs.getString("LOCATION_ID"));
					pendAppForm.setStorageLocationId(rs.getString("STORAGE_LOCATION_ID"));
					pendAppForm.setMaterialTypeId(rs.getString("MATERIAL_TYPE_ID"));
					pendAppForm.setMaterialShortName(rs.getString("MATERIAL_SHORT_NAME")); 
					pendAppForm.setMaterialLongName(rs.getString("MATERIAL_LONG_NAME"));
					pendAppForm.setMaterialGroupId(rs.getString("MATERIAL_GROUP_ID"));
					pendAppForm.setPharmacopName(rs.getString("PHARMACOP_NAME"));
				String pharmacopGrade=rs.getString("PHARMACOP_GRADE");
				pendAppForm.setPharmacopGrade(pharmacopGrade);
					
				pendAppForm.setGenericName(rs.getString("GENERIC_NAME"));
				pendAppForm.setSynonym(rs.getString("SYNONYM"));
				pendAppForm.setPharmacopSpecification(rs.getString("PHARMACOP_SPECIFICATION"));
					String isDMfMaterial=rs.getString("IS_DMF_MATERIAL");
					if(isDMfMaterial.equalsIgnoreCase("1"))
					{
						pendAppForm.setIsDMFMaterial("True");
						pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
						pendAppForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
						pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
						
					}
					pendAppForm.setAdditionalTest(rs.getString("ADDITIONAL_TEST"));
					if(isDMfMaterial.equalsIgnoreCase("0"))
					{
						pendAppForm.setIsDMFMaterial("False");
						pendAppForm.setDmfGradeId(rs.getString("DMF_GRADE_ID"));
						pendAppForm.setMaterialGrade(rs.getString("MATERIAL_GRADE"));
						pendAppForm.setCosGradeNo(rs.getString("COS_GRADE_AND_NO"));
						
					}
					
					pendAppForm.setCountryId(rs.getString("COUNTRY_ID"));
					pendAppForm.setCustomerName(rs.getString("CUSTOMER_NAME"));
					pendAppForm.setToBeUsedInProducts(rs.getString("TO_BE_USED_IN_PRODUCTS"));
					
					String isVendorStatus=rs.getString("IS_VENDOR_SPECIFIC_MATERIAL");
					if(isVendorStatus.equalsIgnoreCase("1"))
					{
						pendAppForm.setIsVendorSpecificMaterial("True");
						pendAppForm.setMfgrName(rs.getString("MFGR_NAME"));
						pendAppForm.setSiteOfManufacture(rs.getString("SITE_OF_MANUFACTURE"));
					}
					if(isVendorStatus.equalsIgnoreCase("0"))
					{
						pendAppForm.setIsVendorSpecificMaterial("False");
					}
					
					pendAppForm.setTempCondition(rs.getString("TEMP_CONDITION"));
					pendAppForm.setStorageCondition(rs.getString("STORAGE_CONDITION"));
					pendAppForm.setShelfLife(rs.getString("SHELF_LIFE"));
					String dutyElement=rs.getString("DUTY_ELEMENT");
					if(dutyElement.equalsIgnoreCase("1"))
					{
						pendAppForm.setDutyElement("True");
					}
					if(dutyElement.equalsIgnoreCase("0"))
						pendAppForm.setDutyElement("False");
					pendAppForm.setShelfLifeType(rs.getString("SHELF_LIFE_TYPE"));
					pendAppForm.setRetestDays(rs.getString("RETEST_DAYS"));
					pendAppForm.setRetestType(rs.getString("RETEST_DAYS_TYPE"));
					pendAppForm.setValuationClass(rs.getString("VALUATION_CLASS"));
					pendAppForm.setApproximateValue(rs.getString("APPROXIMATE_VALUE"));
					pendAppForm.setUnitOfMeasId(rs.getString("UNIT_OF_MEAS_ID"));
					pendAppForm.setPuchaseGroupId(rs.getString("PURCHASE_GROUP_ID"));
					pendAppForm.setRequestedBy(rs.getString("REQUESTED_BY"));
					
					ArrayList fileList = new ArrayList();
					String uploadedFiles=rs.getString("Attachements");
					if(uploadedFiles.equalsIgnoreCase(""))
					{
						
					}else{
					String v[] = uploadedFiles.split(",");
					int l = v.length;
					for (int i = 0; i < l; i++) 
					{
						RawMaterialForm pendAppForm2=new RawMaterialForm();
						//String url=v[i];
						//pendAppForm2.setUrl(url);
					System.out.println(v[i]);
						pendAppForm2.setUploadFilePath(v[i]);
					int x=v[i].lastIndexOf("/");
					uploadedFiles=v[i].substring(x+1);		
					pendAppForm2.setFileList(uploadedFiles);
					fileList.add(pendAppForm2);
					}
					request.setAttribute("listName", fileList);
					}
					
					String sapCodeno=rs.getString("SAP_CODE_NO");
					if(sapCodeno!=null)
					{
						pendAppForm.setSapCodeNo(rs.getString("SAP_CODE_NO"));
						String sapCodeExist=rs.getString("SAP_CODE_EXISTS");
						if(sapCodeExist.equalsIgnoreCase("1"))
						{
							pendAppForm.setSapCodeExists("True");
						}else{
							pendAppForm.setSapCodeExistsNo("True");
						}
						
						String createDate=rs.getString("SAP_CREATION_DATE");
						String a1[]=createDate.split(" ");
						createDate=a1[0];
						String b1[]=createDate.split("-");
						createDate=b1[2]+"/"+b1[1]+"/"+b1[0];
						pendAppForm.setSapCreationDate(createDate);
						pendAppForm.setSapCreatedBy(rs.getString("SAP_CREATED_BY"));
					}
				
					
				 

				}
					
				if(filterBy.equalsIgnoreCase("Pending")){
					request.setAttribute("approveButton", "approveButton");
					request.setAttribute("rejectButton", "rejectButton");
				}
				
				
				//set ApproverDetails 
				checkStatus=0;int appStatus=0;
				LinkedList listApprers=new LinkedList();
				String getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
				"where mast.Location='"+location+"' AND  mast.Material_Type='ROH' AND Material_Group='"+matGroup+"' and mast.Approver_Id=emp.PERNR order by Priority";
				ResultSet rsApprDetails=ad.selectQuery(getApprDetails);
				while(rsApprDetails.next())
				{
					checkStatus=1;
					ApprovalsForm apprvers=new ApprovalsForm();
					apprvers.setPriority(rsApprDetails.getString("Priority"));
					apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
					String empCode=rsApprDetails.getString("Approver_Id");
					String approveStatus="";
					String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
					"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
					"mast.Material_Type='ROH' AND Material_Group='"+matGroup+"' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
					"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
					"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
					ResultSet rsAppInfo=ad.selectQuery(recordStatus);
					while(rsAppInfo.next())
					{
						
						apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
						approveStatus=rsAppInfo.getString("Req_Status");
						if(approveStatus.equalsIgnoreCase(""))
						{
							apprvers.setApproveStatus("In Process");
						}
						if(approveStatus.equalsIgnoreCase("Approved"))
						{
						apprvers.setDate(rsAppInfo.getString("approved_date"));
						}else{
							apprvers.setDate(rsAppInfo.getString("rejected_date"));
							appStatus=1;
						}
						String comments=rsAppInfo.getString("Comments");
						if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
						{
							apprvers.setComments("");
						}else{
							apprvers.setComments(rsAppInfo.getString("Comments"));
						}
						
						
					}
					if(approveStatus.equalsIgnoreCase("") && appStatus==0)
					{
						apprvers.setApproveStatus("In Process");
					}
					apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
					listApprers.add(apprvers);
				}
				if(checkStatus==0)
				{
					getApprDetails="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME from Material_Approvers as mast,emp_official_info as emp " +
					"where mast.Location='"+location+"' AND  mast.Material_Type='ROH' AND Material_Group='' and mast.Approver_Id=emp.PERNR order by Priority";
					rsApprDetails=ad.selectQuery(getApprDetails);
					while(rsApprDetails.next())
					{
						checkStatus=1;
						ApprovalsForm apprvers=new ApprovalsForm();
						apprvers.setPriority(rsApprDetails.getString("Priority"));
						apprvers.setEmployeeCode(rsApprDetails.getString("Approver_Id"));
						String empCode=rsApprDetails.getString("Approver_Id");
						String approveStatus="";
						String recordStatus="select mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status,all_r.approved_date,all_r.rejected_date,all_r.Comments " +
						"from Material_Approvers as mast,emp_official_info as emp,All_Request as all_r where all_r.Req_Id='"+reqId+"' and mast.Location='"+location+"' AND  " +
						"mast.Material_Type='ROH' AND Material_Group='' and all_r.Last_Approver='"+empCode+"' and mast.Approver_Id=all_r.Last_Approver and (mast.Role='User' or mast.Role='Accounts' " +
						"or mast.Role='Creator') and mast.Approver_Id=emp.PERNR  group by mast.Priority,mast.Priority,mast.Approver_Id,emp.EMP_FULLNAME,all_r.Req_Status," +
						"all_r.approved_date,all_r.rejected_date,all_r.Comments  HAVING count(*) > 0";
						ResultSet rsAppInfo=ad.selectQuery(recordStatus);
						while(rsAppInfo.next())
						{
							apprvers.setApproveStatus(rsAppInfo.getString("Req_Status"));
							approveStatus=rsAppInfo.getString("Req_Status");
							
							if(approveStatus.equalsIgnoreCase("Approved"))
							{
							String approveDate=	rsAppInfo.getString("approved_date");
							
							String a[]=approveDate.split(" ");
							approveDate=a[0];
							String b[]=approveDate.split("-");
							approveDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
							
							apprvers.setDate(approveDate);
							}else{
								String rejectDate=	rsAppInfo.getString("rejected_date");
								
								String a[]=rejectDate.split(" ");
								rejectDate=a[0];
								String b[]=rejectDate.split("-");
								rejectDate=b[2]+"/"+b[1]+"/"+b[0]+" "+a[1];
								apprvers.setDate(rejectDate);
							}
							String comments=rsAppInfo.getString("Comments");
							if(comments.equalsIgnoreCase("null") || comments.equalsIgnoreCase(""))
							{
								apprvers.setComments("");
							}else{
								apprvers.setComments(rsAppInfo.getString("Comments"));
							}
						}
						if(approveStatus.equalsIgnoreCase("") && appStatus==0)
						{
							apprvers.setApproveStatus("In Process");
						}
						apprvers.setEmployeeName(rsApprDetails.getString("EMP_FULLNAME"));
						listApprers.add(apprvers);
				}
				}
				request.setAttribute("approverDetails", listApprers);
				
		
				return mapping.findForward("ROH_Creator");
			}
			
		}
		catch (SQLException e) {
			System.out.println("exception @ upload request");
			e.printStackTrace();
		}
		catch (NullPointerException ne){
			System.out.println("exception @ upload request");
			ne.printStackTrace();
		}
		
		request.setAttribute("openRequest", "open");
		String openTab = request.getParameter("searchTxt");
		if(!openTab.equalsIgnoreCase("")){
			pendAppForm.setHeading("Search Results");
			pendAppForm.setSearchText(openTab);
		}
		
		
		return mapping.findForward("displayRequest");
	}
	
	
	public ActionForward updateMaterialrecord(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		
		
		MaterialsForm statusForm=(MaterialsForm) form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		if(user==null){
			request.setAttribute("message","Session Expried! Try to Login again!");
			return mapping.findForward("displayiFrameSession");
		}
		EssDao ad=new EssDao();
		String reqId = request.getParameter("reqId");
		
		statusForm.setRequestNo("reqId");
		MainDao exeQry = new MainDao();
		String reqType = statusForm.getReqRequstType();
		statusForm.setRequestType(reqType);
		String reqStatus = request.getParameter("status");
		String filterBy=statusForm.getSelectedFilter();
		statusForm.setSelectedFilter(filterBy);
		statusForm.setReqRequstType(reqType);
		int totalRecords=statusForm.getTotalRecords();//21
		int startRecord=statusForm.getStartRecord();//11
		int endRecord=statusForm.getEndRecord();	
		String matType=request.getParameter("matType");
		
		String location="";
		String matGroup="";
		String matDetails="select loc.LOCATION_CODE,mat.MATERIAL_GROUP_ID,mat.type from material_code_request as mat,Location as loc  where mat.REQUEST_NO='"+reqId+"' and mat.LOCATION_ID=loc.LOCID";
		ResultSet rsDetails=ad.selectQuery(matDetails);
		try {
			if(rsDetails.next())
			{
				location=rsDetails.getString("LOCATION_CODE");
				matGroup=rsDetails.getString("MATERIAL_GROUP_ID");
				matType=rsDetails.getString("type");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		statusForm.setTotalRecords(totalRecords);
		statusForm.setStartRecord(startRecord);
		statusForm.setEndRecord(endRecord);
		String comments = statusForm.getRemark();
		
		LinkedList reqDetails = new LinkedList();
		reqDetails.add(0, reqId);
		reqDetails.add(1, reqType);
		//User Informtion
		String lcode = user.getPlantId();
		int user_Id = user.getId();
		String uName = user.getUserName();
		String appName = uName;
		int rowCount= 0;
		System.out.println(reqType);
		
		Date dNow = new Date( );
		 SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-DD");
			String dateNow = ft.format(dNow);
		rowCount = getCount("Approvers_Details", reqType);
			
		
		if(matType.equalsIgnoreCase("ZCIV")||matType.equalsIgnoreCase("ZCON")||matType.equalsIgnoreCase("ZITC")||matType.equalsIgnoreCase("LC")
				||matType.equalsIgnoreCase("ZPFL")||matType.equalsIgnoreCase("ZSCR"))
	     {
         String his="insert into material_code_request_history select *,'Mass Code Edit',getdate() from material_code_request where REQUEST_NO='"+reqId+"'";
 		int updathis=ad.SqlExecuteUpdate(his);
			
			String updateGeneralMaterial="update material_code_request set STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"', MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"' , " +
					" PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',HSN_Code='"+statusForm.getHsnCode()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedJustification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"'," +
					" REQUESTED_BY='"+statusForm.getRequestedBy()+"',MODIFIED_DATE=convert(date,getdate()),MODIFIED_BY='"+user.getEmployeeNo()+"' where REQUEST_NO='"+reqId+"'";	 
			int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
	  
	     }
	  if(matType.equalsIgnoreCase("OSE"))
	  {
	         String his="insert into material_code_request_history select *,'Mass Code Edit',getdate() from material_code_request where REQUEST_NO='"+reqId+"'";
	         int updathis=ad.SqlExecuteUpdate(his);
	         
		  String updateGeneralMaterial="update material_code_request set " +
			 " STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"',MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"'," +
			 " PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"', MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"' ," +
			 " APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"',HSN_Code='"+statusForm.getHsnCode()+"',VALUATION_CLASS='"+statusForm.getValuationClass()+"',MATERIAL_USED_IN='"+statusForm.getMaterialUsedIn()+"'," +
			 " IS_EQUIPMENT='"+statusForm.getIsEquipment()+"',IS_SPARE='"+statusForm.getIsSpare()+"',IS_NEW_Equipment='"+statusForm.getIsNewEquipment()+"',IS_NEW_Furniture='"+statusForm.getIsItNewFurniture()+"'," +
			 " IS_NEW_Facility='"+statusForm.getIsNewEquipment()+"',IS_Spare_required='"+statusForm.getIsSpareNewEquipment()+"',EQUIPMENT_NAME='"+statusForm.getEquipmentName()+"',"+
			 " EQUIPMENT_MAKE='"+statusForm.getEquipmentMake()+"',Component_MAKE='"+statusForm.getComponentMake()+"',OEM_PartNo='"+statusForm.getOemPartNo()+"'," +
			 " PR_NUMBER='"+statusForm.getPrNumber()+"',PO_NUMBER='"+statusForm.getPoNumber()+"',UTILIZING_DEPT='"+statusForm.getUtilizingDept()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',"
			 + " DETAILED_SPECIFICATION='"+statusForm.getDetailedJustification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"',DIMENSION='"+statusForm.getDimension()+"',PACK_SIZE='"+statusForm.getPackSize()+"',equip_Intended='"+statusForm.getEquipIntendedFor()+"'," +
			" MODIFIED_DATE=convert(date,getdate()),MODIFIED_BY='"+user.getEmployeeNo()+"'   where REQUEST_NO='"+reqId+"'";	 
			
		  int updateMateStatus=ad.SqlExecuteUpdate(updateGeneralMaterial);
		  System.out.println(updateMateStatus);
		   
			
	  }
	  
	  if(matType.equalsIgnoreCase("PPC"))
	  {
	         String his="insert into material_code_request_history select *,'Mass Code Edit',getdate() from material_code_request where REQUEST_NO='"+reqId+"'";
	         int updathis=ad.SqlExecuteUpdate(his);
	         
		  String updatQuery="update material_code_request set LOCATION_ID='"+statusForm.getLocationId()+"',MATERIAL_TYPE_ID='"+statusForm.getMaterialTypeId()+"',STORAGE_LOCATION_ID='"+statusForm.getStorageLocationId()+"',MATERIAL_SHORT_NAME='"+statusForm.getMaterialShortName()+"'," +
			" MATERIAL_LONG_NAME='"+statusForm.getMaterialLongName()+"',MATERIAL_GROUP_ID='"+statusForm.getMaterialGroupId()+"',HSN_Code='"+statusForm.getHsnCode()+"',DIVISION_ID='"+statusForm.getDivisionId()+"',UNIT_OF_MEAS_ID='"+statusForm.getUnitOfMeasId()+"',PURCHASE_GROUP_ID='"+statusForm.getPuchaseGroupId()+"',is_asset='"+statusForm.getIsAsset()+"',APPROXIMATE_VALUE='"+statusForm.getApproximateValue()+"'," +
			"VALUATION_CLASS='"+statusForm.getValuationClass()+"',DETAILED_JUSTIFICATION='"+statusForm.getDetailedJustification()+"',DETAILED_SPECIFICATION='"+statusForm.getDetailedSpecification()+"',PURPOSE_ID='"+statusForm.getPurposeID()+"',IS_SAS_FORM_AVAILABLE='"+statusForm.getIsSASFormAvailable()+"'," +
			"MODIFIED_DATE=convert(date,getdate()),MODIFIED_BY='"+user.getEmployeeNo()+"',rejected_flag='' where REQUEST_NO='"+reqId+"'";
						 
		  int updateMateStatus=ad.SqlExecuteUpdate(updatQuery);
			
	  }
	  
	  ViewMaterialrecord(mapping, statusForm, request, response);
	  
	  String returnType="";
	  	  if(matType.equalsIgnoreCase("LC"))
	  		returnType="GeneralMaterial_Creator";

		  if(matType.equalsIgnoreCase("PPC"))
			  returnType="ZPPC_Creator";


		  if(matType.equalsIgnoreCase("OSE"))
			  returnType="ZPSR_Creator";
		
		  return mapping.findForward(returnType);
		
	
	}	
	
}
