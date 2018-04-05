package com.microlabs.ess.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
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

import com.microlabs.db.ConnectionFactory;
import com.microlabs.ess.dao.EssDao;
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
	EssDao ad=new EssDao();
	private static final String DESTINATION = "SAP_DESTINATION";
	static String propertyFile="sapmasterdetails.properties";
	private void connectSAP() {
		try {
		InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream(propertyFile);
		
		Properties connectProperties = new Properties();// TODO change the
		connectProperties.load(in);
		in.close();												// details
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, 
				"192.168.1.33");
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
		connectProperties
				.setProperty(DestinationDataProvider.JCO_CLIENT, "150");
		connectProperties.setProperty(DestinationDataProvider.JCO_USER,
				"rfcdev");
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
				"Init123#");
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "EN");
		File destCfg = new File(DESTINATION + ".jcoDestination");
		
			FileOutputStream fos = new FileOutputStream(destCfg, false);
			connectProperties.store(fos, "SAP_DESTINATION config");
			fos.close();
	
		} catch (Exception e) {
			throw new RuntimeException("Unable to create the destination file",e);
		}
	}
	
	public ActionForward displayAttendeceDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPAttendenceForm attdForm =(SAPAttendenceForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		attdForm.setEmployeeNo(user.getEmployeeNo());
		try {
			// load a properties file
					
			
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
			/*JCoFunction function = destination.getRepository().getFunction(
					"ZBAPI_MMAS");*/
			JCoFunction function = destination.getRepository().getFunction(
						"ZBAPI_HR_ATTENDANCE");
			if (function == null) {
				throw new RuntimeException(" ZBAPI_HR_ATTENDANCE not found in SAP.");
			}

			JCoTable passTable = function.getTableParameterList().getTable(
					"MATERIAL");
			passTable.appendRow();
			passTable.setValue("PERNR", user.getEmployeeNo());
			passTable.setValue("PAYGROUP", attdForm.getPayGroup());
			passTable.setValue("MONTH", attdForm.getMonth());
			
			function.execute(destination);
			
			JCoTable returnTable = function.getTableParameterList().getTable("ATTNDATA");
			Map<Integer,Character> returnMap = new HashMap<Integer, Character>();
			if(returnTable.getNumRows() > 0){
				returnTable.firstRow();
				do{
					System.out.println("BEGDA="+returnTable.getInt("BEGDA"));
					System.out.println("INTIME="+returnTable.getChar("INTIME"));
					System.out.println("INSTATUS="+returnTable.getChar("INSTATUS"));
					System.out.println("OUTTIME="+returnTable.getChar("OUTTIME"));
					System.out.println("MATERIAL_SHORT_NAME="+returnTable.getChar("MATERIAL_SHORT_NAME"));
					//returnMap.put(returnTable.getInt("REQ_NO"),returnTable.getChar("TYPE"));
				}while(returnTable.nextRow());
			}
			//mcrd.updateMaterialRequestWithSAPDetails(returnMap);
		} catch (AbapException e) {
			System.out.println(e.toString());// TODO change to log
			
		} catch (JCoException e) {
			System.out.println(e.toString());// TODO change to log
			e.printStackTrace();
	
		}catch(Throwable e){
			System.out.println(e.toString());// TODO change to log
			e.printStackTrace();
		} finally {
			//rs.close();
		}
		
		return mapping.findForward("display");
	}
	
	public ActionForward display(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SAPAttendenceForm attdForm =(SAPAttendenceForm)form;
		HttpSession session=request.getSession();
		UserInfo user=(UserInfo)session.getAttribute("user");
		attdForm.setEmployeeNo(user.getEmployeeNo());
		LinkedList payGroupID=new LinkedList();
		LinkedList payGroupShort_Name=new LinkedList();
		
		String getPayGrupDetails="select * from Paygroup_Master order by Paygroup";
		ResultSet rs=ad.selectQuery(getPayGrupDetails);
		try{
		while(rs.next()){
			payGroupID.add(rs.getString("Paygroup"));
			payGroupShort_Name.add(rs.getString("Short_desc"));
		}
		attdForm.setPayGroupID(payGroupID);
		attdForm.setPayGroupShort_Name(payGroupShort_Name);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return mapping.findForward("display");
	}

	
}
