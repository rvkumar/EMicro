package com.microlabs.ess.form;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.springframework.web.multipart.MultipartFile;

public class TravelRequestForm extends ActionForm {

	private String traveldeskId;
	private int requestNumber;
	private String plandate;
	private String planorigin;
	private String plandestination;
	private int mtpId;
	private int multiCityId;
	private String status;
	private String travel_desk_type1;
	private String traveldeskname1;
	private String mtravelmode1;
	
	private String mtraveltype1;
	
	
	private String traveldeskname;

	private String planremarks;
	
	private int id;
	private String typeOfTravel;
	private String travelFor;
	private String travelRequestFor;
	private String guestName;
	private String guestContactNo;
	private String guestAge;
	private String modeOfTravel;
	private String fromPlace;
	private String toPlace;
	private String departOn;
	private String departOn1;
	private String departTime;
	private String departRemarks;


	private String returnOn;
	private String returnTime;
	private String returnRemarks;
	private String via;
	private String purposeOfVisit;
	private String remarks;
	
	private String managerName;
	private String employeeName;
	private String place;
	private int contactNo;
	private String gender;
	private int age;

	private String returnfrom;
	private String returnTo;
	private String travellingPersonName;
	private FormFile documentFile;
	public String[] documentCheck;
	private String message;
	private String message2;
	private String fileName;
	private String fileFullPath;
	private String filepath;
	
	private List<MultipartFile> files;
	private File[] fileUpload=new File[10];
	private String[] fileUploadFileName=new String[10];
	private String[] fileUploadContentType=new String[10];
	
	private String reqType;
	
	private String reqDate;
	
	private String reqStatus;
	
	
	
	private String passportno;
	private String passportplace;
	private String passportissuedate;
	private String passportexpirydate;
	
	
	private String employeeno;
	private String locationId;
	
	private String department;
	private String designation;
	private String dateofBirth;
	
	private String travelagentname;
	private String billno;
	private String billdate;
	private String billamount;
	
	private String bookingstatus;
	
	private String fromdate;
	private String todate;
	
	
	private String bookeddate;
	private String bookedby;
	
	
	private String pernr;
	
	private int total;
	private int next;
	private int prev;

	private int startRecord;
	private int endRecord;
	
	private String confirmdate;
	private String confirmby;
	
	
	private String confirmstatus;
	
	private String multiemployeeno;
	
	
	private String userpassportno;
	private String userpassportplace;
	private String userpassportissuedate;
	private String userpassportexpirydate;
	private String usermobno;
	private String usermailId;
	private String userGender;
	private String userAge;
	private String userPersonalmailId;
	
	private String guestmailId;
	private String usertype;
	private String travelmode;
	private String travelmodex;
	private String traveltype;
	private String traveltype11;
	private String email_Guest;
	private String guest_Title;
	private String guest_Visano;
	private String guest_DOB;
	private String guest_Meal;
	private String guest_Type;
	
	private String guest_Company;
	private String travel_Adult;
	private String travel_Child;
	private String travel_Infant;
	private String purposetext;
	private String travel_Days;
	private String airline_Pref;
	private String airline_Just;
	private String spon_div;
	private String bud_code;
	private String est_trip_cose;
	private String hotel_Res;
	private String onduty_Req;
	private String rent_Car;
	private String pickup_Details;
	private String drop_Details;
	private String trip_Advance;
	private String trip_Amt;
	private String trip_Currency;
	private String trip_From_Date;
	private String trip_From_Time;
	private String trip_To_Date;
	private String trip_To_time;
	private String trip_Priority;
	private String guest_pernr;
	
	
	////sql
	private String purposetype;
	private String service_class;
	
	private String report_Type;
	private String on_duty;
	private String searchText;
	private String hotel_Name;
	private String hotel_City;
	
	
	
	public String getTravel_desk_type1() {
		return travel_desk_type1;
	}

	public void setTravel_desk_type1(String travel_desk_type1) {
		this.travel_desk_type1 = travel_desk_type1;
	}

	public String getTraveldeskname1() {
		return traveldeskname1;
	}

	public void setTraveldeskname1(String traveldeskname1) {
		this.traveldeskname1 = traveldeskname1;
	}

	public String getTraveldeskId() {
		return traveldeskId;
	}

	public void setTraveldeskId(String traveldeskId) {
		this.traveldeskId = traveldeskId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getMtpId() {
		return mtpId;
	}

	public void setMtpId(int mtpId) {
		this.mtpId = mtpId;
	}

	public int getMultiCityId() {
		return multiCityId;
	}

	public void setMultiCityId(int multiCityId) {
		this.multiCityId = multiCityId;
	}

	public String getMtravelmode1() {
		return mtravelmode1;
	}

	public void setMtravelmode1(String mtravelmode1) {
		this.mtravelmode1 = mtravelmode1;
	}

	public String getMtraveltype1() {
		return mtraveltype1;
	}

	public void setMtraveltype1(String mtraveltype1) {
		this.mtraveltype1 = mtraveltype1;
	}

	public String getPlandate() {
		return plandate;
	}

	public void setPlandate(String plandate) {
		this.plandate = plandate;
	}

	public String getPlanorigin() {
		return planorigin;
	}

	public void setPlanorigin(String planorigin) {
		this.planorigin = planorigin;
	}

	public String getPlandestination() {
		return plandestination;
	}

	public void setPlandestination(String plandestination) {
		this.plandestination = plandestination;
	}

	public String getPlanremarks() {
		return planremarks;
	}

	public void setPlanremarks(String planremarks) {
		this.planremarks = planremarks;
	}

	public String getTravelmodex() {
		return travelmodex;
	}

	public void setTravelmodex(String travelmodex) {
		this.travelmodex = travelmodex;
	}

	public String getTraveltype11() {
		return traveltype11;
	}

	public void setTraveltype11(String traveltype11) {
		this.traveltype11 = traveltype11;
	}

	
	
	public String getTraveldeskname() {
		return traveldeskname;
	}

	public void setTraveldeskname(String traveldeskname) {
		this.traveldeskname = traveldeskname;
	}



	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	
	private ArrayList traveldeskList=new ArrayList();
	private ArrayList traveldeskLabelList=new ArrayList();
	
	private ArrayList travelLabelList= new ArrayList();
	
	private ArrayList travelist= new ArrayList();
	
	
	public ArrayList getTravelLabelList() {
		return travelLabelList;
	}

	public void setTravelLabelList(ArrayList travelLabelList) {
		this.travelLabelList = travelLabelList;
	}

	public ArrayList getTravelist() {
		return travelist;
	}

	public void setTravelist(ArrayList travelist) {
		this.travelist = travelist;
	}

	private String locid;
	private String billtype;
	
	private String travel_Days1;
	
	

	private ArrayList travIdList=new ArrayList();
	private ArrayList travLabelList=new ArrayList();
	
	
	
	private ArrayList divIdList=new ArrayList();
	private ArrayList divLabelList=new ArrayList();
	
	
	private ArrayList currIdList=new ArrayList();
	private ArrayList currLabelList=new ArrayList();
	
	
	
	private ArrayList travel_desk_repList=new ArrayList();
	private ArrayList travel_desk_repLabelList=new ArrayList();
	
	
	
	private ArrayList traveltypeList=new ArrayList();
	private ArrayList traveltypeLabelList=new ArrayList();
	
	
	private ArrayList deptList=new ArrayList();
	private ArrayList deptLabelList=new ArrayList();
	
	
	
	
	private String selectedRequestNo;
	
	private int oldrequestNumber;
	private int oldid;
	private String review_Trip;
	private String review_Rating;
	
	private String comments;
	
	private String confirm_Comments;
	private String travel_desk_type;
	
	private String accom_type;
	private String accom_name;
	
	
	private String p_name;
	private String p_dept;
	private String p_cont;
	private String p_email;
	private String utravelmode;
	
	private String origin;
	private String departure;
	private String visano;
	private String accom_req;
	
	
	
	public String getTrip_Amt() {
		return trip_Amt;
	}

	public void setTrip_Amt(String trip_Amt) {
		this.trip_Amt = trip_Amt;
	}

	public String getTrip_Currency() {
		return trip_Currency;
	}

	public void setTrip_Currency(String trip_Currency) {
		this.trip_Currency = trip_Currency;
	}

	public String getTrip_From_Date() {
		return trip_From_Date;
	}

	public void setTrip_From_Date(String trip_From_Date) {
		this.trip_From_Date = trip_From_Date;
	}

	public String getTrip_From_Time() {
		return trip_From_Time;
	}

	public void setTrip_From_Time(String trip_From_Time) {
		this.trip_From_Time = trip_From_Time;
	}

	public String getTrip_To_Date() {
		return trip_To_Date;
	}

	public void setTrip_To_Date(String trip_To_Date) {
		this.trip_To_Date = trip_To_Date;
	}

	public String getTrip_To_time() {
		return trip_To_time;
	}

	public void setTrip_To_time(String trip_To_time) {
		this.trip_To_time = trip_To_time;
	}

	public String getUserPersonalmailId() {
		return userPersonalmailId;
	}

	public void setUserPersonalmailId(String userPersonalmailId) {
		this.userPersonalmailId = userPersonalmailId;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public String getGuestmailId() {
		return guestmailId;
	}

	public void setGuestmailId(String guestmailId) {
		this.guestmailId = guestmailId;
	}

	public String getUserAge() {
		return userAge;
	}

	public void setUserAge(String userAge) {
		this.userAge = userAge;
	}

	public String getUserpassportno() {
		return userpassportno;
	}

	public void setUserpassportno(String userpassportno) {
		this.userpassportno = userpassportno;
	}

	public String getUserpassportplace() {
		return userpassportplace;
	}

	public void setUserpassportplace(String userpassportplace) {
		this.userpassportplace = userpassportplace;
	}

	public String getUserpassportissuedate() {
		return userpassportissuedate;
	}

	public void setUserpassportissuedate(String userpassportissuedate) {
		this.userpassportissuedate = userpassportissuedate;
	}

	public String getUserpassportexpirydate() {
		return userpassportexpirydate;
	}

	public void setUserpassportexpirydate(String userpassportexpirydate) {
		this.userpassportexpirydate = userpassportexpirydate;
	}

	public String getUsermobno() {
		return usermobno;
	}

	public void setUsermobno(String usermobno) {
		this.usermobno = usermobno;
	}

	public String getUsermailId() {
		return usermailId;
	}

	public void setUsermailId(String usermailId) {
		this.usermailId = usermailId;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getMultiemployeeno() {
		return multiemployeeno;
	}

	public void setMultiemployeeno(String multiemployeeno) {
		this.multiemployeeno = multiemployeeno;
	}

	public String getConfirmstatus() {
		return confirmstatus;
	}

	public void setConfirmstatus(String confirmstatus) {
		this.confirmstatus = confirmstatus;
	}

	public String getConfirmdate() {
		return confirmdate;
	}

	public void setConfirmdate(String confirmdate) {
		this.confirmdate = confirmdate;
	}

	public String getConfirmby() {
		return confirmby;
	}

	public void setConfirmby(String confirmby) {
		this.confirmby = confirmby;
	}
	

	public String getDepartOn1() {
		return departOn1;
	}

	public void setDepartOn1(String departOn1) {
		this.departOn1 = departOn1;
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

	public String getBookedby() {
		return bookedby;
	}

	public void setBookedby(String bookedby) {
		this.bookedby = bookedby;
	}

	public String getPernr() {
		return pernr;
	}

	public void setPernr(String pernr) {
		this.pernr = pernr;
	}

	public String getBookeddate() {
		return bookeddate;
	}

	public void setBookeddate(String bookeddate) {
		this.bookeddate = bookeddate;
	}

	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getTravelagentname() {
		return travelagentname;
	}

	public void setTravelagentname(String travelagentname) {
		this.travelagentname = travelagentname;
	}

	public String getBillno() {
		return billno;
	}

	public void setBillno(String billno) {
		this.billno = billno;
	}

	public String getBilldate() {
		return billdate;
	}

	public void setBilldate(String billdate) {
		this.billdate = billdate;
	}

	public String getBillamount() {
		return billamount;
	}

	public void setBillamount(String billamount) {
		this.billamount = billamount;
	}

	public String getBookingstatus() {
		return bookingstatus;
	}

	public void setBookingstatus(String bookingstatus) {
		this.bookingstatus = bookingstatus;
	}

	public String getEmployeeno() {
		return employeeno;
	}

	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}

	public String getLocationId() {
		return locationId;
	}

	public void setLocationId(String locationId) {
		this.locationId = locationId;
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

	public String getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public String getPassportno() {
		return passportno;
	}

	public void setPassportno(String passportno) {
		this.passportno = passportno;
	}

	public String getPassportplace() {
		return passportplace;
	}

	public void setPassportplace(String passportplace) {
		this.passportplace = passportplace;
	}

	public String getPassportissuedate() {
		return passportissuedate;
	}

	public void setPassportissuedate(String passportissuedate) {
		this.passportissuedate = passportissuedate;
	}

	public String getPassportexpirydate() {
		return passportexpirydate;
	}

	public void setPassportexpirydate(String passportexpirydate) {
		this.passportexpirydate = passportexpirydate;
	}

	public String getReqStatus() {
		return reqStatus;
	}

	public void setReqStatus(String reqStatus) {
		this.reqStatus = reqStatus;
	}

	public String getReqDate() {
		return reqDate;
	}

	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getDepartRemarks() {
		return departRemarks;
	}

	public void setDepartRemarks(String departRemarks) {
		this.departRemarks = departRemarks;
	}

	public String getReturnRemarks() {
		return returnRemarks;
	}

	public void setReturnRemarks(String returnRemarks) {
		this.returnRemarks = returnRemarks;
	}

	public File[] getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File[] fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String[] getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String[] fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String[] getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String[] fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileFullPath() {
		return fileFullPath;
	}

	public void setFileFullPath(String fileFullPath) {
		this.fileFullPath = fileFullPath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage2() {
		return message2;
	}

	public void setMessage2(String message2) {
		this.message2 = message2;
	}

	public int getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public FormFile getDocumentFile() {
		return documentFile;
	}

	public void setDocumentFile(FormFile documentFile) {
		this.documentFile = documentFile;
	}

	public String[] getDocumentCheck() {
		return documentCheck;
	}

	public void setDocumentCheck(String[] documentCheck) {
		this.documentCheck = documentCheck;
	}

	public String getTravellingPersonName() {
		return travellingPersonName;
	}

	public void setTravellingPersonName(String travellingPersonName) {
		this.travellingPersonName = travellingPersonName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getContactNo() {
		return contactNo;
	}

	public void setContactNo(int contactNo) {
		this.contactNo = contactNo;
	}


	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}



	public String getReturnfrom() {
		return returnfrom;
	}

	public void setReturnfrom(String returnfrom) {
		this.returnfrom = returnfrom;
	}

	public String getReturnTo() {
		return returnTo;
	}

	public void setReturnTo(String returnTo) {
		this.returnTo = returnTo;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public String getTravelRequestFor() {
		return travelRequestFor;
	}

	public void setTravelRequestFor(String travelRequestFor) {
		this.travelRequestFor = travelRequestFor;
	}

	public String getTypeOfTravel() {
		return typeOfTravel;
	}

	public void setTypeOfTravel(String typeOfTravel) {
		this.typeOfTravel = typeOfTravel;
	}

	public String getTravelFor() {
		return travelFor;
	}

	public void setTravelFor(String travelFor) {
		this.travelFor = travelFor;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestContactNo() {
		return guestContactNo;
	}

	public void setGuestContactNo(String guestContactNo) {
		this.guestContactNo = guestContactNo;
	}

	public String getGuestAge() {
		return guestAge;
	}

	public void setGuestAge(String guestAge) {
		this.guestAge = guestAge;
	}

	public String getModeOfTravel() {
		return modeOfTravel;
	}

	public void setModeOfTravel(String modeOfTravel) {
		this.modeOfTravel = modeOfTravel;
	}

	public String getFromPlace() {
		return fromPlace;
	}

	public void setFromPlace(String fromPlace) {
		this.fromPlace = fromPlace;
	}

	public String getToPlace() {
		return toPlace;
	}

	public void setToPlace(String toPlace) {
		this.toPlace = toPlace;
	}

	public String getDepartOn() {
		return departOn;
	}

	public void setDepartOn(String departOn) {
		this.departOn = departOn;
	}

	public String getReturnOn() {
		return returnOn;
	}

	public void setReturnOn(String returnOn) {
		this.returnOn = returnOn;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getPurposeOfVisit() {
		return purposeOfVisit;
	}

	public void setPurposeOfVisit(String purposeOfVisit) {
		this.purposeOfVisit = purposeOfVisit;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getTravelmode() {
		return travelmode;
	}

	public void setTravelmode(String travelmode) {
		this.travelmode = travelmode;
	}

	public String getTraveltype() {
		return traveltype;
	}

	public void setTraveltype(String traveltype) {
		this.traveltype = traveltype;
	}

	public String getEmail_Guest() {
		return email_Guest;
	}

	public void setEmail_Guest(String email_Guest) {
		this.email_Guest = email_Guest;
	}

	public String getGuest_Title() {
		return guest_Title;
	}

	public void setGuest_Title(String guest_Title) {
		this.guest_Title = guest_Title;
	}

	public String getGuest_Visano() {
		return guest_Visano;
	}

	public void setGuest_Visano(String guest_Visano) {
		this.guest_Visano = guest_Visano;
	}

	public String getGuest_DOB() {
		return guest_DOB;
	}

	public void setGuest_DOB(String guest_DOB) {
		this.guest_DOB = guest_DOB;
	}

	public String getGuest_Meal() {
		return guest_Meal;
	}

	public void setGuest_Meal(String guest_Meal) {
		this.guest_Meal = guest_Meal;
	}

	public String getGuest_Type() {
		return guest_Type;
	}

	public void setGuest_Type(String guest_Type) {
		this.guest_Type = guest_Type;
	}

	public String getGuest_Company() {
		return guest_Company;
	}

	public void setGuest_Company(String guest_Company) {
		this.guest_Company = guest_Company;
	}

	public String getTravel_Infant() {
		return travel_Infant;
	}

	public void setTravel_Infant(String travel_Infant) {
		this.travel_Infant = travel_Infant;
	}

	public String getTravel_Child() {
		return travel_Child;
	}

	public void setTravel_Child(String travel_Child) {
		this.travel_Child = travel_Child;
	}

	public String getTravel_Adult() {
		return travel_Adult;
	}

	public void setTravel_Adult(String travel_Adult) {
		this.travel_Adult = travel_Adult;
	}

	public String getPurposetext() {
		return purposetext;
	}

	public void setPurposetext(String purposetext) {
		this.purposetext = purposetext;
	}

	public String getTravel_Days() {
		return travel_Days;
	}

	public void setTravel_Days(String travel_Days) {
		this.travel_Days = travel_Days;
	}

	public String getAirline_Pref() {
		return airline_Pref;
	}

	public void setAirline_Pref(String airline_Pref) {
		this.airline_Pref = airline_Pref;
	}

	public String getAirline_Just() {
		return airline_Just;
	}

	public void setAirline_Just(String airline_Just) {
		this.airline_Just = airline_Just;
	}

	public String getSpon_div() {
		return spon_div;
	}

	public void setSpon_div(String spon_div) {
		this.spon_div = spon_div;
	}

	public String getBud_code() {
		return bud_code;
	}

	public void setBud_code(String bud_code) {
		this.bud_code = bud_code;
	}

	public String getEst_trip_cose() {
		return est_trip_cose;
	}

	public void setEst_trip_cose(String est_trip_cose) {
		this.est_trip_cose = est_trip_cose;
	}

	public String getHotel_Res() {
		return hotel_Res;
	}

	public void setHotel_Res(String hotel_Res) {
		this.hotel_Res = hotel_Res;
	}

	public String getOnduty_Req() {
		return onduty_Req;
	}

	public void setOnduty_Req(String onduty_Req) {
		this.onduty_Req = onduty_Req;
	}

	public String getRent_Car() {
		return rent_Car;
	}

	public void setRent_Car(String rent_Car) {
		this.rent_Car = rent_Car;
	}

	public String getPickup_Details() {
		return pickup_Details;
	}

	public void setPickup_Details(String pickup_Details) {
		this.pickup_Details = pickup_Details;
	}

	public String getDrop_Details() {
		return drop_Details;
	}

	public void setDrop_Details(String drop_Details) {
		this.drop_Details = drop_Details;
	}

	public String getTrip_Advance() {
		return trip_Advance;
	}

	public void setTrip_Advance(String trip_Advance) {
		this.trip_Advance = trip_Advance;
	}

	public String getTrip_Priority() {
		return trip_Priority;
	}

	public void setTrip_Priority(String trip_Priority) {
		this.trip_Priority = trip_Priority;
	}

	public String getGuest_pernr() {
		return guest_pernr;
	}

	public void setGuest_pernr(String guest_pernr) {
		this.guest_pernr = guest_pernr;
	}

	public String getPurposetype() {
		return purposetype;
	}

	public void setPurposetype(String purposetype) {
		this.purposetype = purposetype;
	}

	public String getService_class() {
		return service_class;
	}

	public void setService_class(String service_class) {
		this.service_class = service_class;
	}

	public String getReport_Type() {
		return report_Type;
	}

	public void setReport_Type(String report_Type) {
		this.report_Type = report_Type;
	}

	public String getOn_duty() {
		return on_duty;
	}

	public void setOn_duty(String on_duty) {
		this.on_duty = on_duty;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getHotel_Name() {
		return hotel_Name;
	}

	public void setHotel_Name(String hotel_Name) {
		this.hotel_Name = hotel_Name;
	}

	public String getHotel_City() {
		return hotel_City;
	}

	public void setHotel_City(String hotel_City) {
		this.hotel_City = hotel_City;
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

	public String getLocid() {
		return locid;
	}

	public void setLocid(String locid) {
		this.locid = locid;
	}

	public String getBilltype() {
		return billtype;
	}

	public void setBilltype(String billtype) {
		this.billtype = billtype;
	}

	public String getTravel_Days1() {
		return travel_Days1;
	}

	public void setTravel_Days1(String travel_Days1) {
		this.travel_Days1 = travel_Days1;
	}

	public ArrayList getTravIdList() {
		return travIdList;
	}

	public void setTravIdList(ArrayList travIdList) {
		this.travIdList = travIdList;
	}

	public ArrayList getTravLabelList() {
		return travLabelList;
	}

	public void setTravLabelList(ArrayList travLabelList) {
		this.travLabelList = travLabelList;
	}

	public ArrayList getDivLabelList() {
		return divLabelList;
	}

	public void setDivLabelList(ArrayList divLabelList) {
		this.divLabelList = divLabelList;
	}

	public ArrayList getDivIdList() {
		return divIdList;
	}

	public void setDivIdList(ArrayList divIdList) {
		this.divIdList = divIdList;
	}

	public String getSelectedRequestNo() {
		return selectedRequestNo;
	}

	public void setSelectedRequestNo(String selectedRequestNo) {
		this.selectedRequestNo = selectedRequestNo;
	}

	public int getOldrequestNumber() {
		return oldrequestNumber;
	}

	public void setOldrequestNumber(int oldrequestNumber) {
		this.oldrequestNumber = oldrequestNumber;
	}

	public int getOldid() {
		return oldid;
	}

	public void setOldid(int oldid) {
		this.oldid = oldid;
	}

	public String getReview_Trip() {
		return review_Trip;
	}

	public void setReview_Trip(String review_Trip) {
		this.review_Trip = review_Trip;
	}

	public String getReview_Rating() {
		return review_Rating;
	}

	public void setReview_Rating(String review_Rating) {
		this.review_Rating = review_Rating;
	}

	public ArrayList getCurrIdList() {
		return currIdList;
	}

	public void setCurrIdList(ArrayList currIdList) {
		this.currIdList = currIdList;
	}

	public ArrayList getCurrLabelList() {
		return currLabelList;
	}

	public void setCurrLabelList(ArrayList currLabelList) {
		this.currLabelList = currLabelList;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getConfirm_Comments() {
		return confirm_Comments;
	}

	public void setConfirm_Comments(String confirm_Comments) {
		this.confirm_Comments = confirm_Comments;
	}

	public String getTravel_desk_type() {
		return travel_desk_type;
	}

	public void setTravel_desk_type(String travel_desk_type) {
		this.travel_desk_type = travel_desk_type;
	}

	public ArrayList getTravel_desk_repList() {
		return travel_desk_repList;
	}

	public void setTravel_desk_repList(ArrayList travel_desk_repList) {
		this.travel_desk_repList = travel_desk_repList;
	}

	public ArrayList getTravel_desk_repLabelList() {
		return travel_desk_repLabelList;
	}

	public void setTravel_desk_repLabelList(ArrayList travel_desk_repLabelList) {
		this.travel_desk_repLabelList = travel_desk_repLabelList;
	}

	public ArrayList getTraveltypeList() {
		return traveltypeList;
	}

	public void setTraveltypeList(ArrayList traveltypeList) {
		this.traveltypeList = traveltypeList;
	}

	public ArrayList getTraveltypeLabelList() {
		return traveltypeLabelList;
	}

	public void setTraveltypeLabelList(ArrayList traveltypeLabelList) {
		this.traveltypeLabelList = traveltypeLabelList;
	}

	public String getAccom_type() {
		return accom_type;
	}

	public void setAccom_type(String accom_type) {
		this.accom_type = accom_type;
	}

	public String getAccom_name() {
		return accom_name;
	}

	public void setAccom_name(String accom_name) {
		this.accom_name = accom_name;
	}

	public String getP_name() {
		return p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public String getP_dept() {
		return p_dept;
	}

	public void setP_dept(String p_dept) {
		this.p_dept = p_dept;
	}

	public String getP_cont() {
		return p_cont;
	}

	public void setP_cont(String p_cont) {
		this.p_cont = p_cont;
	}

	public String getP_email() {
		return p_email;
	}

	public void setP_email(String p_email) {
		this.p_email = p_email;
	}

	public String getUtravelmode() {
		return utravelmode;
	}

	public void setUtravelmode(String utravelmode) {
		this.utravelmode = utravelmode;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getVisano() {
		return visano;
	}

	public void setVisano(String visano) {
		this.visano = visano;
	}

	public String getAccom_req() {
		return accom_req;
	}

	public void setAccom_req(String accom_req) {
		this.accom_req = accom_req;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
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

	public ArrayList getTraveldeskList() {
		return traveldeskList;
	}

	public void setTraveldeskList(ArrayList traveldeskList) {
		this.traveldeskList = traveldeskList;
	}

	public ArrayList getTraveldeskLabelList() {
		return traveldeskLabelList;
	}

	public void setTraveldeskLabelList(ArrayList traveldeskLabelList) {
		this.traveldeskLabelList = traveldeskLabelList;
	}
	
	

}
