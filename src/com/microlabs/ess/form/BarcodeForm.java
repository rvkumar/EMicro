package com.microlabs.ess.form;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class BarcodeForm extends ActionForm{
	
	
	private String message;
	
	private String mBLNR;
	private String mJAHR;
	private String lOEKZ;
	private String bLDAT;
	private String bUDAT;
	private String wERKS;
	private String mTART;
	private String eKGRP;
	private String xBLNR;
	private String eBELN;
	private String lIFNR;
	private String xSTBW;
	private String bWART;
	private String bSART;
	private String dDMBTR;
	private String bBKTXT;
	private String sENT_DATE;
	private String sSENT_BY;
	private String dCKET_NO;
	private String cNAME;
	private String pD_DT;
	private String name1;
	private String oORT01;
	private String accepted_date;
	private String accepted_by;

	private int totalRecords1;
	private int startRecord1;
	private int endRecord1;
	
	private String type;
	private String datefrom ;
	private String dateto;
	
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	
	private ArrayList purchaselList=new ArrayList();
	
	private String location; 
	
	private String pur;
	private String empName;
	private String accdate;
	private String employeeno;
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatefrom() {
		return datefrom;
	}

	public void setDatefrom(String datefrom) {
		this.datefrom = datefrom;
	}

	public String getDateto() {
		return dateto;
	}

	public void setDateto(String dateto) {
		this.dateto = dateto;
	}

	public int getTotalRecords1() {
		return totalRecords1;
	}

	public void setTotalRecords1(int totalRecords1) {
		this.totalRecords1 = totalRecords1;
	}

	public int getStartRecord1() {
		return startRecord1;
	}

	public void setStartRecord1(int startRecord1) {
		this.startRecord1 = startRecord1;
	}

	public int getEndRecord1() {
		return endRecord1;
	}

	public void setEndRecord1(int endRecord1) {
		this.endRecord1 = endRecord1;
	}

	public String getmBLNR() {
		return mBLNR;
	}

	public void setmBLNR(String mBLNR) {
		this.mBLNR = mBLNR;
	}

	public String getmJAHR() {
		return mJAHR;
	}

	public void setmJAHR(String mJAHR) {
		this.mJAHR = mJAHR;
	}

	public String getlOEKZ() {
		return lOEKZ;
	}

	public void setlOEKZ(String lOEKZ) {
		this.lOEKZ = lOEKZ;
	}

	public String getbLDAT() {
		return bLDAT;
	}

	public void setbLDAT(String bLDAT) {
		this.bLDAT = bLDAT;
	}

	public String getbUDAT() {
		return bUDAT;
	}

	public void setbUDAT(String bUDAT) {
		this.bUDAT = bUDAT;
	}

	public String getwERKS() {
		return wERKS;
	}

	public void setwERKS(String wERKS) {
		this.wERKS = wERKS;
	}

	public String getmTART() {
		return mTART;
	}

	public void setmTART(String mTART) {
		this.mTART = mTART;
	}

	public String geteKGRP() {
		return eKGRP;
	}

	public void seteKGRP(String eKGRP) {
		this.eKGRP = eKGRP;
	}

	public String getxBLNR() {
		return xBLNR;
	}

	public void setxBLNR(String xBLNR) {
		this.xBLNR = xBLNR;
	}

	public String geteBELN() {
		return eBELN;
	}

	public void seteBELN(String eBELN) {
		this.eBELN = eBELN;
	}

	public String getlIFNR() {
		return lIFNR;
	}

	public void setlIFNR(String lIFNR) {
		this.lIFNR = lIFNR;
	}

	public String getxSTBW() {
		return xSTBW;
	}

	public void setxSTBW(String xSTBW) {
		this.xSTBW = xSTBW;
	}

	public String getbWART() {
		return bWART;
	}

	public void setbWART(String bWART) {
		this.bWART = bWART;
	}

	public String getbSART() {
		return bSART;
	}

	public void setbSART(String bSART) {
		this.bSART = bSART;
	}

	public String getdDMBTR() {
		return dDMBTR;
	}

	public void setdDMBTR(String dDMBTR) {
		this.dDMBTR = dDMBTR;
	}

	public String getbBKTXT() {
		return bBKTXT;
	}

	public void setbBKTXT(String bBKTXT) {
		this.bBKTXT = bBKTXT;
	}

	public String getsENT_DATE() {
		return sENT_DATE;
	}

	public void setsENT_DATE(String sENT_DATE) {
		this.sENT_DATE = sENT_DATE;
	}

	public String getsSENT_BY() {
		return sSENT_BY;
	}

	public void setsSENT_BY(String sSENT_BY) {
		this.sSENT_BY = sSENT_BY;
	}

	public String getdCKET_NO() {
		return dCKET_NO;
	}

	public void setdCKET_NO(String dCKET_NO) {
		this.dCKET_NO = dCKET_NO;
	}

	public String getcNAME() {
		return cNAME;
	}

	public void setcNAME(String cNAME) {
		this.cNAME = cNAME;
	}

	public String getpD_DT() {
		return pD_DT;
	}

	public void setpD_DT(String pD_DT) {
		this.pD_DT = pD_DT;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getoORT01() {
		return oORT01;
	}

	public void setoORT01(String oORT01) {
		this.oORT01 = oORT01;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccepted_date() {
		return accepted_date;
	}

	public void setAccepted_date(String accepted_date) {
		this.accepted_date = accepted_date;
	}

	public String getAccepted_by() {
		return accepted_by;
	}

	public void setAccepted_by(String accepted_by) {
		this.accepted_by = accepted_by;
	}

	public ArrayList getLocationIdList() {
		return locationIdList;
	}

	public void setLocationIdList(ArrayList locationIdList) {
		this.locationIdList = locationIdList;
	}

	public ArrayList getLocationLabelList() {
		return locationLabelList;
	}

	public void setLocationLabelList(ArrayList locationLabelList) {
		this.locationLabelList = locationLabelList;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public ArrayList getPurchaselList() {
		return purchaselList;
	}

	public void setPurchaselList(ArrayList purchaselList) {
		this.purchaselList = purchaselList;
	}

	public String getPur() {
		return pur;
	}

	public void setPur(String pur) {
		this.pur = pur;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getAccdate() {
		return accdate;
	}

	public void setAccdate(String accdate) {
		this.accdate = accdate;
	}

	public String getEmployeeno() {
		return employeeno;
	}

	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}
	
	
	
	
}
