package com.microlabs.it.form;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class HelpDeskForm extends ActionForm
{
	
	private int total;
	private int next;
	private int prev;

	private int totalRecords;

	private int startRecord;
	private int endRecord;
	private String reqPriority;
	private String itEngStatus;
	private String remarks;
	private String requestName;
	public String getRequestName() {
		return requestName;
	}
	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}
	private String category;
	private String subject;
	private String searchText;
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
	private String empEmailID;
	private String initials;

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
	private String comments;
	private String selectedIssueStatus;
	//Ad deletion
	
	private String deletefrom;
	private String reqdfolderdeletion;	
	
	
	
	//Ad transfer
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private ArrayList departmentList= new ArrayList();
	
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
	private String newlyJoinedYes;
	private String newlyJoinedNo;
	private String joiningDate;
	private String totalIDs;
	private String mailIDRetained;
	//lotus deletion
	private String emailidDelete;	
	private String deletedDBYes;
	private String deletedDBNo;
	private String forward_MailID;
	private String mailIDPath;
	
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
	private String accesstypes[];
	
	
//Transport request
	private String fromclient;
	private String targetclient;
	private String fromsys;
	private String tosys;
	private String[] module;
	private String naturofchnge;
	private String description;
	private String chngreqNo;
	private String chngreqDate;
	private String reqDescription;
	
	
	//issues
	private String issrequestype;
	private String issAssignedTechn;
	
	private ArrayList subcatList=new ArrayList();
	
	//new Asset
	private String assetcategory;
	private String suggestmodelname;
	private String reqbydate;
	private String purpose;
	private String gxpyes;
	private String gxpno;
	private String typeofprinter;
	private String printvolpermonth;
	private String users;
	private String suggestmodelno;
	private String apprxvalue;
	private String lanyes;
	private String lanno;
	private String anysplreq;
	private String empType;
	private String subcategory;
	private String transNo;
	private String locationId;
	private String chooseType;
	private String subfilter;
	private ArrayList subfilterList=new ArrayList();
	
	//subfilterList
	
	private int escalatedLevel;
	
	//qms
	private String user;
	
	private String multilocation[];
	private String multilocationid;
	private String view;
	
	
	

	private ArrayList spareLabelList=new ArrayList();
	

	private ArrayList deptList=new ArrayList();
	private ArrayList deptLabelList=new ArrayList();
	
	
	
	private ArrayList desgList=new ArrayList();
	private ArrayList desgLabelList=new ArrayList();
	
	
	private String department;
	private String designation;
	
	
	
	
	
	
	

	public ArrayList getSpareLabelList() {
		return spareLabelList;
	}
	public void setSpareLabelList(ArrayList spareLabelList) {
		this.spareLabelList = spareLabelList;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String[] getMultilocation() {
		return multilocation;
	}
	public void setMultilocation(String[] multilocation) {
		this.multilocation = multilocation;
	}
	public String getMultilocationid() {
		return multilocationid;
	}
	public void setMultilocationid(String multilocationid) {
		this.multilocationid = multilocationid;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public int getEscalatedLevel() {
		return escalatedLevel;
	}
	public void setEscalatedLevel(int escalatedLevel) {
		this.escalatedLevel = escalatedLevel;
	}
	public String getFromclient() {
		return fromclient;
	}
	public void setFromclient(String fromclient) {
		this.fromclient = fromclient;
	}
	public String getTargetclient() {
		return targetclient;
	}
	public void setTargetclient(String targetclient) {
		this.targetclient = targetclient;
	}
	public String getFromsys() {
		return fromsys;
	}
	public void setFromsys(String fromsys) {
		this.fromsys = fromsys;
	}
	public String getTosys() {
		return tosys;
	}
	public void setTosys(String tosys) {
		this.tosys = tosys;
	}
	public String[] getModule() {
		return module;
	}
	public void setModule(String[] module) {
		this.module = module;
	}
	public String getNaturofchnge() {
		return naturofchnge;
	}
	public void setNaturofchnge(String naturofchnge) {
		this.naturofchnge = naturofchnge;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getChngreqNo() {
		return chngreqNo;
	}
	public void setChngreqNo(String chngreqNo) {
		this.chngreqNo = chngreqNo;
	}
	public String getChngreqDate() {
		return chngreqDate;
	}
	public void setChngreqDate(String chngreqDate) {
		this.chngreqDate = chngreqDate;
	}
	public String getReqDescription() {
		return reqDescription;
	}
	public void setReqDescription(String reqDescription) {
		this.reqDescription = reqDescription;
	}
	public String[] getAccesstypes() {
		return accesstypes;
	}
	public void setAccesstypes(String[] accesstypes) {
		this.accesstypes = accesstypes;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getSuggestmodelname() {
		return suggestmodelname;
	}
	public void setSuggestmodelname(String suggestmodelname) {
		this.suggestmodelname = suggestmodelname;
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
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getGxpyes() {
		return gxpyes;
	}
	public void setGxpyes(String gxpyes) {
		this.gxpyes = gxpyes;
	}
	public String getGxpno() {
		return gxpno;
	}
	public void setGxpno(String gxpno) {
		this.gxpno = gxpno;
	}
	public String getTypeofprinter() {
		return typeofprinter;
	}
	public void setTypeofprinter(String typeofprinter) {
		this.typeofprinter = typeofprinter;
	}
	public String getPrintvolpermonth() {
		return printvolpermonth;
	}
	public void setPrintvolpermonth(String printvolpermonth) {
		this.printvolpermonth = printvolpermonth;
	}
	public String getUsers() {
		return users;
	}
	public void setUsers(String users) {
		this.users = users;
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
	public String getLanno() {
		return lanno;
	}
	public void setLanno(String lanno) {
		this.lanno = lanno;
	}
	public String getAnysplreq() {
		return anysplreq;
	}
	public void setAnysplreq(String anysplreq) {
		this.anysplreq = anysplreq;
	}
	public String getInitials() {
		return initials;
	}
	public void setInitials(String initials) {
		this.initials = initials;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	public ArrayList getSubcatList() {
		return subcatList;
	}
	public void setSubcatList(ArrayList subcatList) {
		this.subcatList = subcatList;
	}
	public String getIssrequestype() {
		return issrequestype;
	}
	public void setIssrequestype(String issrequestype) {
		this.issrequestype = issrequestype;
	}
	public String getIssAssignedTechn() {
		return issAssignedTechn;
	}
	public void setIssAssignedTechn(String issAssignedTechn) {
		this.issAssignedTechn = issAssignedTechn;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
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
	public String getDeletedDBNo() {
		return deletedDBNo;
	}
	public void setDeletedDBNo(String deletedDBNo) {
		this.deletedDBNo = deletedDBNo;
	}
	public String getForward_MailID() {
		return forward_MailID;
	}
	public void setForward_MailID(String forward_MailID) {
		this.forward_MailID = forward_MailID;
	}
	public String getMailIDRetained() {
		return mailIDRetained;
	}
	public void setMailIDRetained(String mailIDRetained) {
		this.mailIDRetained = mailIDRetained;
	}
	public String getNewlyJoinedYes() {
		return newlyJoinedYes;
	}
	public void setNewlyJoinedYes(String newlyJoinedYes) {
		this.newlyJoinedYes = newlyJoinedYes;
	}
	public String getNewlyJoinedNo() {
		return newlyJoinedNo;
	}
	public void setNewlyJoinedNo(String newlyJoinedNo) {
		this.newlyJoinedNo = newlyJoinedNo;
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
	public String getEmpEmailID() {
		return empEmailID;
	}
	public void setEmpEmailID(String empEmailID) {
		this.empEmailID = empEmailID;
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
	public String getEmailidDelete() {
		return emailidDelete;
	}
	public void setEmailidDelete(String emailidDelete) {
		this.emailidDelete = emailidDelete;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReqPriority() {
		return reqPriority;
	}
	public void setReqPriority(String reqPriority) {
		this.reqPriority = reqPriority;
	}

	public String getRequestStatus() {
		return requestStatus;
	}
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}
	public int getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(int requestNo) {
		this.requestNo = requestNo;
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
	public String getRequiredfoldername() {
		return requiredfoldername;
	}
	public void setRequiredfoldername(String requiredfoldername) {
		this.requiredfoldername = requiredfoldername;
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
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
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
	public void setIPNumber(String number) {
		IPNumber = number;
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
	public String getFromemailId() {
		return fromemailId;
	}
	public String getItEngStatus() {
		return itEngStatus;
	}
	public void setItEngStatus(String itEngStatus) {
		this.itEngStatus = itEngStatus;
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
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getDevicetype() {
		return devicetype;
	}
	public void setDevicetype(String devicetype) {
		this.devicetype = devicetype;
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
	public String getOutsidecom() {
		return outsidecom;
	}
	public void setOutsidecom(String outsidecom) {
		this.outsidecom = outsidecom;
	}
	public String getDeleteoldmails() {
		return deleteoldmails;
	}
	public void setDeleteoldmails(String deleteoldmails) {
		this.deleteoldmails = deleteoldmails;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSelectedIssueStatus() {
		return selectedIssueStatus;
	}
	public void setSelectedIssueStatus(String selectedIssueStatus) {
		this.selectedIssueStatus = selectedIssueStatus;
	}
	public String getTransNo() {
		return transNo;
	}
	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getChooseType() {
		return chooseType;
	}
	public void setChooseType(String chooseType) {
		this.chooseType = chooseType;
	}
	public String getSubfilter() {
		return subfilter;
	}
	public void setSubfilter(String subfilter) {
		this.subfilter = subfilter;
	}
	public ArrayList getSubfilterList() {
		return subfilterList;
	}
	public void setSubfilterList(ArrayList subfilterList) {
		this.subfilterList = subfilterList;
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
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	
	

}
