package com.microlabs.admin.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class HelpDeskReportForm extends ActionForm{
	
	private String category;
	private String department;
	private String designation;
	private String locationId;
	private String keyword;
	private String status;
	private String fromDate;
	private String toDate;
	private String comments;
	
	//Issues Count
	private int newStatusCount;
	private int sendToITcount;
	private int userActionCount;
	private int pendForSparescount;
	private int solutionProvidedcount;
	private int inprocesscount;

	private int completedCount;
	
	
	
	private int opencount;
	private int closedcount;
	
	private int total;
	private int next;
	private int prev;

	private int startRecord;
	private int endRecord;
	
	private String reqPriority;
	private String itEngStatus;
	private String remarks;
	
	private String reqname;
	
	//Ad User Creation
	
	
		private String lastApprover;
		private String pendingApprover;
		private String approver;
		private String requestType;
		private int requestNo;
		private String requestNumber;
		private String requestDate;
		private String requestStatus;
		private String requestername;
		private String requesterdepartment;
		private String requesterdesignation;
		private String location;
		private int priority;
		private String usage;
		private String employeeno;
		


		private String mobno;
		private String extno;
		private String ipPhoneno;
		
		private String assetvalue;
		private ArrayList assetvalueIdList=new ArrayList();
		private ArrayList assetvalueLabelList=new ArrayList();
		
		private String hostname;
		private String IPNumber;
		private String adloginname;
		private String reqdfolderacces;
		private String message;
		private String message1;
		
		//Ad deletion
		
		private String deletefrom;
		private String reqdfolderdeletion;	
		
		
		
		//Ad transfer
		private ArrayList locationIdList=new ArrayList();
		private ArrayList locationLabelList=new ArrayList();
		private ArrayList departmentIdList=new ArrayList();
		private ArrayList departmentList= new ArrayList();
		private ArrayList techidList=new ArrayList();
		private ArrayList technameList= new ArrayList();
		
		private String fromlocationId;
		private String tolocationId;
		
		private String  fromdepartment;
		private String  todepartment;
		
		//Lotus Id Creation
		private String reportingManager;
		private String isHeHod;
		private String suggestedEmailId;
		private String isthisCommonId;
		private String enablewebAccess;
		private String reason;
		private int totalmailIdDept;
		private int totalmailIdloc;
		private String outsidecom;
		private String outsidecomyes;
		private String outsidecomno;
		private String firstname;
		private String lastname;
		private String middname;
		
		//Lotus Id Change
		private String fromemailId;
		private String empno;
		private String reasonforchange;
		private String transferoldmail;
		private String deleteoldmails;
		
		//lotus deletion
		private String emailidDelete;	
		
		//Internet Access
		private String internetgrp;
		
		//external Drives 
		private String cdrom;
		private String usb;
		private String floppy;
		private String accesstype;
		private String devicetype;
		
		//FTP ACCESS
		private String requiredfoldername;
		private String durationofaccess;
		private String frmdurationofaccess;
		private String todurationofaccess;
		private String username;
		private String password;
		private String server;
		private String foldername;
		
		//Dash Board
		private String requestBy;
		private String empName;
		private int pending;
		private int closed;
		private int reftype;
		private String issrequestype;
		private String assetcategory;
		private String reqbydate;
		private String suggestmodelname;
		private String gxpyes;
		private String typeofprinter;
		private String users;
		private String printvolpermonth;
		private String suggestmodelno;
		private String apprxvalue;
		private String lanyes;
		private String anysplreq;
		private String purpose;
		private String initials;
		private String newlyJoinedYes;
		private String joiningDate;
		private String totalIDs;
		private String mailIDPath;
		private String deletedDBYes;
		private String forward_MailID;
		private String empType;
		private String subcategory;
		private String transNo;
		private String totalRecords;
		private String chooseType;
		private String empEmailID; 
		private String appdate;
		private String day;
		private String hr;
		private String techname;
		private String approvedDate;
		private String compDate;
		
		private String selType;		
		private int action;
		
		
		private int escalatedLevel;
		
		private String newlyJoinedNo;
		private String reftypes;
		
		
		private ArrayList sapList=new ArrayList();
		private ArrayList sapLabelList= new ArrayList();
		
		
		private String fromDate1;
		private String toDate1;
		

		private ArrayList desgList=new ArrayList();
		private ArrayList desgLabelList=new ArrayList();
		

		private ArrayList deptList=new ArrayList();
		private ArrayList deptLabelList=new ArrayList();
		
	    public ArrayList getSapList() {
			return sapList;
		}
		public void setSapList(ArrayList sapList) {
			this.sapList = sapList;
		}
		
		
		

	
	public ArrayList getSapLabelList() {
			return sapLabelList;
		}
		public void setSapLabelList(ArrayList sapLabelList) {
			this.sapLabelList = sapLabelList;
		}
	public String getNewlyJoinedNo() {
			return newlyJoinedNo;
		}
		public void setNewlyJoinedNo(String newlyJoinedNo) {
			this.newlyJoinedNo = newlyJoinedNo;
		}
	public int getEscalatedLevel() {
			return escalatedLevel;
		}
		public void setEscalatedLevel(int escalatedLevel) {
			this.escalatedLevel = escalatedLevel;
		}
	public int getAction() {
			return action;
		}
		public void setAction(int action) {
			this.action = action;
		}
	public String getCompDate() {
			return compDate;
		}
		public void setCompDate(String compDate) {
			this.compDate = compDate;
		}
	public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
	public ArrayList getTechidList() {
			return techidList;
		}
		public void setTechidList(ArrayList techidList) {
			this.techidList = techidList;
		}
		public ArrayList getTechnameList() {
			return technameList;
		}
		public void setTechnameList(ArrayList technameList) {
			this.technameList = technameList;
		}
	public int getInprocesscount() {
			return inprocesscount;
		}
		public void setInprocesscount(int inprocesscount) {
			this.inprocesscount = inprocesscount;
		}
	public int getSendToITcount() {
			return sendToITcount;
		}
		public void setSendToITcount(int sendToITcount) {
			this.sendToITcount = sendToITcount;
		}
		public int getUserActionCount() {
			return userActionCount;
		}
		public void setUserActionCount(int userActionCount) {
			this.userActionCount = userActionCount;
		}
		public int getPendForSparescount() {
			return pendForSparescount;
		}
		public void setPendForSparescount(int pendForSparescount) {
			this.pendForSparescount = pendForSparescount;
		}
		public int getSolutionProvidedcount() {
			return solutionProvidedcount;
		}
		public void setSolutionProvidedcount(int solutionProvidedcount) {
			this.solutionProvidedcount = solutionProvidedcount;
		}
	public ArrayList getDepartmentIdList() {
			return departmentIdList;
		}
		public void setDepartmentIdList(ArrayList departmentIdList) {
			this.departmentIdList = departmentIdList;
		}
	public int getNewStatusCount() {
			return newStatusCount;
		}
		public void setNewStatusCount(int newStatusCount) {
			this.newStatusCount = newStatusCount;
		}
		public int getCompletedCount() {
			return completedCount;
		}
		public void setCompletedCount(int completedCount) {
			this.completedCount = completedCount;
		}
	public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
	public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	public String getKeyword() {
			return keyword;
		}
		public void setKeyword(String keyword) {
			this.keyword = keyword;
		}
	public String getLocationId() {
			return locationId;
		}
		public void setLocationId(String locationId) {
			this.locationId = locationId;
		}
	public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getDepartment() {
			return department;
		}
		public void setDepartment(String department) {
			this.department = department;
		}
	public String getReqPriority() {
			return reqPriority;
		}
		public void setReqPriority(String reqPriority) {
			this.reqPriority = reqPriority;
		}
		public String getItEngStatus() {
			return itEngStatus;
		}
		public void setItEngStatus(String itEngStatus) {
			this.itEngStatus = itEngStatus;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
	public String getLastApprover() {
			return lastApprover;
		}
		public void setLastApprover(String lastApprover) {
			this.lastApprover = lastApprover;
		}
		public String getPendingApprover() {
			return pendingApprover;
		}
		public void setPendingApprover(String pendingApprover) {
			this.pendingApprover = pendingApprover;
		}
		public String getApprover() {
			return approver;
		}
		public void setApprover(String approver) {
			this.approver = approver;
		}
		public String getRequestType() {
			return requestType;
		}
		public void setRequestType(String requestType) {
			this.requestType = requestType;
		}
		public int getRequestNo() {
			return requestNo;
		}
		public void setRequestNo(int requestNo) {
			this.requestNo = requestNo;
		}
		public String getRequestNumber() {
			return requestNumber;
		}
		public void setRequestNumber(String requestNumber) {
			this.requestNumber = requestNumber;
		}
		public String getRequestDate() {
			return requestDate;
		}
		public void setRequestDate(String requestDate) {
			this.requestDate = requestDate;
		}
		public String getRequestStatus() {
			return requestStatus;
		}
		public void setRequestStatus(String requestStatus) {
			this.requestStatus = requestStatus;
		}
		public String getRequestername() {
			return requestername;
		}
		public void setRequestername(String requestername) {
			this.requestername = requestername;
		}
		public String getRequesterdepartment() {
			return requesterdepartment;
		}
		public void setRequesterdepartment(String requesterdepartment) {
			this.requesterdepartment = requesterdepartment;
		}
		public String getRequesterdesignation() {
			return requesterdesignation;
		}
		public void setRequesterdesignation(String requesterdesignation) {
			this.requesterdesignation = requesterdesignation;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		public int getPriority() {
			return priority;
		}
		public void setPriority(int priority) {
			this.priority = priority;
		}
		public String getUsage() {
			return usage;
		}
		public void setUsage(String usage) {
			this.usage = usage;
		}
		public String getEmployeeno() {
			return employeeno;
		}
		public void setEmployeeno(String employeeno) {
			this.employeeno = employeeno;
		}
		public String getMobno() {
			return mobno;
		}
		public void setMobno(String mobno) {
			this.mobno = mobno;
		}
		public String getExtno() {
			return extno;
		}
		public void setExtno(String extno) {
			this.extno = extno;
		}
		public String getIpPhoneno() {
			return ipPhoneno;
		}
		public void setIpPhoneno(String ipPhoneno) {
			this.ipPhoneno = ipPhoneno;
		}
		public String getAssetvalue() {
			return assetvalue;
		}
		public void setAssetvalue(String assetvalue) {
			this.assetvalue = assetvalue;
		}
		public ArrayList getAssetvalueIdList() {
			return assetvalueIdList;
		}
		public void setAssetvalueIdList(ArrayList assetvalueIdList) {
			this.assetvalueIdList = assetvalueIdList;
		}
		public ArrayList getAssetvalueLabelList() {
			return assetvalueLabelList;
		}
		public void setAssetvalueLabelList(ArrayList assetvalueLabelList) {
			this.assetvalueLabelList = assetvalueLabelList;
		}
		public String getHostname() {
			return hostname;
		}
		public void setHostname(String hostname) {
			this.hostname = hostname;
		}
		public String getIPNumber() {
			return IPNumber;
		}
		public void setIPNumber(String iPNumber) {
			IPNumber = iPNumber;
		}
		public String getAdloginname() {
			return adloginname;
		}
		public void setAdloginname(String adloginname) {
			this.adloginname = adloginname;
		}
		public String getReqdfolderacces() {
			return reqdfolderacces;
		}
		public void setReqdfolderacces(String reqdfolderacces) {
			this.reqdfolderacces = reqdfolderacces;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getMessage1() {
			return message1;
		}
		public void setMessage1(String message1) {
			this.message1 = message1;
		}
		public String getDeletefrom() {
			return deletefrom;
		}
		public void setDeletefrom(String deletefrom) {
			this.deletefrom = deletefrom;
		}
		public String getReqdfolderdeletion() {
			return reqdfolderdeletion;
		}
		public void setReqdfolderdeletion(String reqdfolderdeletion) {
			this.reqdfolderdeletion = reqdfolderdeletion;
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
		public ArrayList getDepartmentList() {
			return departmentList;
		}
		public void setDepartmentList(ArrayList departmentList) {
			this.departmentList = departmentList;
		}
		public String getFromlocationId() {
			return fromlocationId;
		}
		public void setFromlocationId(String fromlocationId) {
			this.fromlocationId = fromlocationId;
		}
		public String getTolocationId() {
			return tolocationId;
		}
		public void setTolocationId(String tolocationId) {
			this.tolocationId = tolocationId;
		}
		public String getFromdepartment() {
			return fromdepartment;
		}
		public void setFromdepartment(String fromdepartment) {
			this.fromdepartment = fromdepartment;
		}
		public String getTodepartment() {
			return todepartment;
		}
		public void setTodepartment(String todepartment) {
			this.todepartment = todepartment;
		}
		public String getReportingManager() {
			return reportingManager;
		}
		public void setReportingManager(String reportingManager) {
			this.reportingManager = reportingManager;
		}
		public String getIsHeHod() {
			return isHeHod;
		}
		public void setIsHeHod(String isHeHod) {
			this.isHeHod = isHeHod;
		}
		public String getSuggestedEmailId() {
			return suggestedEmailId;
		}
		public void setSuggestedEmailId(String suggestedEmailId) {
			this.suggestedEmailId = suggestedEmailId;
		}
		public String getIsthisCommonId() {
			return isthisCommonId;
		}
		public void setIsthisCommonId(String isthisCommonId) {
			this.isthisCommonId = isthisCommonId;
		}
		public String getEnablewebAccess() {
			return enablewebAccess;
		}
		public void setEnablewebAccess(String enablewebAccess) {
			this.enablewebAccess = enablewebAccess;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public int getTotalmailIdDept() {
			return totalmailIdDept;
		}
		public void setTotalmailIdDept(int totalmailIdDept) {
			this.totalmailIdDept = totalmailIdDept;
		}
		public int getTotalmailIdloc() {
			return totalmailIdloc;
		}
		public void setTotalmailIdloc(int totalmailIdloc) {
			this.totalmailIdloc = totalmailIdloc;
		}
		public String getOutsidecom() {
			return outsidecom;
		}
		public void setOutsidecom(String outsidecom) {
			this.outsidecom = outsidecom;
		}
		public String getOutsidecomyes() {
			return outsidecomyes;
		}
		public void setOutsidecomyes(String outsidecomyes) {
			this.outsidecomyes = outsidecomyes;
		}
		public String getOutsidecomno() {
			return outsidecomno;
		}
		public void setOutsidecomno(String outsidecomno) {
			this.outsidecomno = outsidecomno;
		}
		public String getFirstname() {
			return firstname;
		}
		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}
		public String getLastname() {
			return lastname;
		}
		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		public String getMiddname() {
			return middname;
		}
		public void setMiddname(String middname) {
			this.middname = middname;
		}
		public String getFromemailId() {
			return fromemailId;
		}
		public void setFromemailId(String fromemailId) {
			this.fromemailId = fromemailId;
		}
		public String getEmpno() {
			return empno;
		}
		public void setEmpno(String empno) {
			this.empno = empno;
		}
		public String getReasonforchange() {
			return reasonforchange;
		}
		public void setReasonforchange(String reasonforchange) {
			this.reasonforchange = reasonforchange;
		}
		public String getTransferoldmail() {
			return transferoldmail;
		}
		public void setTransferoldmail(String transferoldmail) {
			this.transferoldmail = transferoldmail;
		}
		public String getDeleteoldmails() {
			return deleteoldmails;
		}
		public void setDeleteoldmails(String deleteoldmails) {
			this.deleteoldmails = deleteoldmails;
		}
		public String getEmailidDelete() {
			return emailidDelete;
		}
		public void setEmailidDelete(String emailidDelete) {
			this.emailidDelete = emailidDelete;
		}
		public String getInternetgrp() {
			return internetgrp;
		}
		public void setInternetgrp(String internetgrp) {
			this.internetgrp = internetgrp;
		}
		public String getCdrom() {
			return cdrom;
		}
		public void setCdrom(String cdrom) {
			this.cdrom = cdrom;
		}
		public String getUsb() {
			return usb;
		}
		public void setUsb(String usb) {
			this.usb = usb;
		}
		public String getFloppy() {
			return floppy;
		}
		public void setFloppy(String floppy) {
			this.floppy = floppy;
		}
		public String getAccesstype() {
			return accesstype;
		}
		public void setAccesstype(String accesstype) {
			this.accesstype = accesstype;
		}
		public String getDevicetype() {
			return devicetype;
		}
		public void setDevicetype(String devicetype) {
			this.devicetype = devicetype;
		}
		public String getRequiredfoldername() {
			return requiredfoldername;
		}
		public void setRequiredfoldername(String requiredfoldername) {
			this.requiredfoldername = requiredfoldername;
		}
		public String getDurationofaccess() {
			return durationofaccess;
		}
		public void setDurationofaccess(String durationofaccess) {
			this.durationofaccess = durationofaccess;
		}
		public String getFrmdurationofaccess() {
			return frmdurationofaccess;
		}
		public void setFrmdurationofaccess(String frmdurationofaccess) {
			this.frmdurationofaccess = frmdurationofaccess;
		}
		public String getTodurationofaccess() {
			return todurationofaccess;
		}
		public void setTodurationofaccess(String todurationofaccess) {
			this.todurationofaccess = todurationofaccess;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getServer() {
			return server;
		}
		public void setServer(String server) {
			this.server = server;
		}
		public String getFoldername() {
			return foldername;
		}
		public void setFoldername(String foldername) {
			this.foldername = foldername;
		}
	public String getReqname() {
		return reqname;
	}
	public void setReqname(String reqname) {
		this.reqname = reqname;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getStartRecord() {
		return startRecord;
	}
	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	public int getEndRecord() {
		return endRecord;
	}
	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}
	
	public int getOpencount() {
		return opencount;
	}
	public void setOpencount(int opencount) {
		this.opencount = opencount;
	}
	public int getClosedcount() {
		return closedcount;
	}
	public void setClosedcount(int closedcount) {
		this.closedcount = closedcount;
	}
	public String getRequestBy() {
		return requestBy;
	}
	public void setRequestBy(String requestBy) {
		this.requestBy = requestBy;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	public int getClosed() {
		return closed;
	}
	public void setClosed(int closed) {
		this.closed = closed;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public int getReftype() {
		return reftype;
	}
	public void setReftype(int reftype) {
		this.reftype = reftype;
	}
	public String getIssrequestype() {
		return issrequestype;
	}
	public void setIssrequestype(String issrequestype) {
		this.issrequestype = issrequestype;
	}
	public String getAssetcategory() {
		return assetcategory;
	}
	public void setAssetcategory(String assetcategory) {
		this.assetcategory = assetcategory;
	}
	public String getReqbydate() {
		return reqbydate;
	}
	public void setReqbydate(String reqbydate) {
		this.reqbydate = reqbydate;
	}
	public String getSuggestmodelname() {
		return suggestmodelname;
	}
	public void setSuggestmodelname(String suggestmodelname) {
		this.suggestmodelname = suggestmodelname;
	}
	public String getGxpyes() {
		return gxpyes;
	}
	public void setGxpyes(String gxpyes) {
		this.gxpyes = gxpyes;
	}
	public String getTypeofprinter() {
		return typeofprinter;
	}
	public void setTypeofprinter(String typeofprinter) {
		this.typeofprinter = typeofprinter;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
	}
	public String getPrintvolpermonth() {
		return printvolpermonth;
	}
	public void setPrintvolpermonth(String printvolpermonth) {
		this.printvolpermonth = printvolpermonth;
	}
	public String getSuggestmodelno() {
		return suggestmodelno;
	}
	public void setSuggestmodelno(String suggestmodelno) {
		this.suggestmodelno = suggestmodelno;
	}
	public String getApprxvalue() {
		return apprxvalue;
	}
	public void setApprxvalue(String apprxvalue) {
		this.apprxvalue = apprxvalue;
	}
	public String getLanyes() {
		return lanyes;
	}
	public void setLanyes(String lanyes) {
		this.lanyes = lanyes;
	}
	public String getAnysplreq() {
		return anysplreq;
	}
	public void setAnysplreq(String anysplreq) {
		this.anysplreq = anysplreq;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public String getNewlyJoinedYes() {
		return newlyJoinedYes;
	}
	public void setNewlyJoinedYes(String newlyJoinedYes) {
		this.newlyJoinedYes = newlyJoinedYes;
	}
	public String getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}
	public String getTotalIDs() {
		return totalIDs;
	}
	public void setTotalIDs(String totalIDs) {
		this.totalIDs = totalIDs;
	}
	public String getMailIDPath() {
		return mailIDPath;
	}
	public void setMailIDPath(String mailIDPath) {
		this.mailIDPath = mailIDPath;
	}
	public String getDeletedDBYes() {
		return deletedDBYes;
	}
	public void setDeletedDBYes(String deletedDBYes) {
		this.deletedDBYes = deletedDBYes;
	}
	public String getForward_MailID() {
		return forward_MailID;
	}
	public void setForward_MailID(String forward_MailID) {
		this.forward_MailID = forward_MailID;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	public String getChooseType() {
		return chooseType;
	}
	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}
	public String getEmpEmailID() {
		return empEmailID;
	}
	public void setEmpEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
	}
	public String getAppdate() {
		return appdate;
	}
	public void setAppdate(String appdate) {
		this.appdate = appdate;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getHr() {
		return hr;
	}
	public void setHr(String hr) {
		this.hr = hr;
	}
	public String getTechname() {
		return techname;
	}
	public void setTechname(String techname) {
		this.techname = techname;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getSelType() {
		return selType;
	}
	public void setSelType(String selType) {
		this.selType = selType;
	}
	public String getReftypes() {
		return reftypes;
	}
	public void setReftypes(String reftypes) {
		this.reftypes = reftypes;
	}
	public String getFromDate1() {
		return fromDate1;
	}
	public void setFromDate1(String fromDate1) {
		this.fromDate1 = fromDate1;
	}
	public String getToDate1() {
		return toDate1;
	}
	public void setToDate1(String toDate1) {
		this.toDate1 = toDate1;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public ArrayList getDesgList() {
		return desgList;
	}
	public void setDesgList(ArrayList desgList) {
		this.desgList = desgList;
	}
	public ArrayList getDesgLabelList() {
		return desgLabelList;
	}
	public void setDesgLabelList(ArrayList desgLabelList) {
		this.desgLabelList = desgLabelList;
	}
	public ArrayList getDeptList() {
		return deptList;
	}
	public void setDeptList(ArrayList deptList) {
		this.deptList = deptList;
	}
	public ArrayList getDeptLabelList() {
		return deptLabelList;
	}
	public void setDeptLabelList(ArrayList deptLabelList) {
		this.deptLabelList = deptLabelList;
	}

	
	
}
