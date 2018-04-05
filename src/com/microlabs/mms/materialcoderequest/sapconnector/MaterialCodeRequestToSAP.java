package com.microlabs.mms.materialcoderequest.sapconnector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import com.microlabs.db.ConnectionFactory;
import com.microlabs.mms.materialcoderequest.dao.MaterialCodeRequestDAO;
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class MaterialCodeRequestToSAP {
	private static final String DESTINATION = "SAP_DESTINATION";
	static String propertyFile="mapping.properties";
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

	public void sendRequest(String selectedReq[]) throws SQLException {

		MaterialCodeRequestDAO mcrd = new MaterialCodeRequestDAO();
		Properties prop = new Properties();

		try {
			// load a properties file
			InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream(propertyFile);
			//prop.load(new FileInputStream("mapping.properties"));
			
			prop.load(in);
			in.close();	

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		int reqNoTotal=selectedReq.length;
		String selectdReqNos="";
		for(int i=0;i<selectedReq.length;i++){
		 selectdReqNos=selectdReqNos+""+selectedReq[i]+",";
		}
		selectdReqNos=selectdReqNos.substring(0, (selectdReqNos.length()-1));
		
		
		
		String sql = "SELECT * FROM material_code_request where REQUEST_NO in("+selectdReqNos+") and  Message_Type is null";// correct the logic
															// for loading he
															// data
		ResultSet rs = mcrd.selectQuery(sql);
		if (rs != null) {
			try {
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
							"ZBAPI_MATERIAL_SAVEDATA");
				if (function == null) {
					throw new RuntimeException(" ZBAPI_MATERIAL_SAVEDATA not found in SAP.");
				}

				JCoTable passTable = function.getTableParameterList().getTable(
						"MATERIAL");
				List<String> executionList = new ArrayList<String>();
				while (rs.next()) {
					passTable.appendRow();
					executionList.add(rs.getString("SAP_CODE_NO"));
					for (Entry<Object, Object> entry : prop.entrySet()) {
						Object val = rs.getObject(entry.getKey().toString());
						if (val != null) {
							System.out.println(entry.getValue().toString()+" = "+val);
							passTable
									.setValue(entry.getValue().toString(), val);
						}
					}
				}
				function.execute(destination);
				JCoTable returnTable = function.getTableParameterList().getTable("RESULT");
				Map<Integer,Character> returnMap = new HashMap<Integer, Character>();
				if(returnTable.getNumRows() > 0){
					returnTable.firstRow();
					do{
						System.out.println("REQ_NO="+returnTable.getInt("REQ_NO"));
						System.out.println("Type="+returnTable.getChar("TYPE"));
						returnMap.put(returnTable.getInt("REQ_NO"),returnTable.getChar("TYPE"));
					}while(returnTable.nextRow());
				}
				mcrd.updateMaterialRequestWithSAPDetails(returnMap);
			} catch (AbapException e) {
				System.out.println(e.toString());// TODO change to log
				return;
			} catch (JCoException e) {
				System.out.println(e.toString());// TODO change to log
				e.printStackTrace();
				return;
			}catch(Throwable e){
				System.out.println(e.toString());// TODO change to log
				e.printStackTrace();
			} finally {
				rs.close();
			}
		}

	}
}
