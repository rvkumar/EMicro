package com.microlabs.hr.form;

import org.apache.struts.action.ActionForm;

import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class HRApprovalForm extends ActionForm{
	
	private String requestNo;
	private String requestDate;
	private String requestType;
	private String reqMaterialGroup;
	private String requestedBy;
	private String header;
	private String reqRequstType;
	private String selectedFilter;
	private String employeeNo;
	private String subdeptTO;
	private String[] selectedRequestNo;
	private String searchText;
	
	private String empStatus;
	
	public String getSubdeptTO() {
		return subdeptTO;
	}
	public void setSubdeptTO(String subdeptTO) {
		this.subdeptTO = subdeptTO;
	}
	private String message3;
	private String message2;
	private String message;
	
	private int totalRecords;
	private int startRecord;
	private int endRecord;
	
	
	private String leaveType;
	
	private int requestNumber;
	private String contentDescription;
	private String startDate;
	private String startDurationType;
	private String endDate;
	private String submitDate;
	private String endDurationType;
	private String noOfDays;

	private String documentName;
	private String fileName;
	private String id;
	private FormFile documentFile;
	public String[] documentCheck;
	private String generatedBy;
	private String status;
	private String prev_time;
	
	
	public String[] leaveCheck;
	public String[] leavelist;
	
	
	
	public String[] getMonths() {
		return months;
	}
	public void setMonths(String[] months) {
		this.months = months;
	}
	public String[] months;
	private String reason;
	private String fileNames;
	private String holidayType;
	
	private	LinkedList leaveTypeID=new LinkedList();
	private LinkedList leaveTypeName=new LinkedList();
	
	
	
	
	private	LinkedList leaveReason=new LinkedList();
	private LinkedList leaveDetReason=new LinkedList();
	
	
	private String reasonType;
	
	
	private String remark;
	
	
	//onduty
	
private String onDutyType;
	
	private String duration;
	
	
	private String approver;
	private String employeeNumber;
	private String designation;
	private String approverNumber;
	
	private String employeeName;
	private String department;
	private String date;
	private String locationId;
	private String startTime;
	private String endTime;
	
	
	private String comments;
	
	
	
	//Attendance
	
	private String frompernr;
	private String topernr;
	private String plant;
	private String paygrp;
	private String staffcat;
	private String fromDate;
	private String toDate;
	
	private int grnapprenstaffabsent;
	private int grntechstaffabsent;
	private int grnstaffabsent;	
	
	private int totalapprovedstrength;
	private int totalavailstrength;
	private int totalprsent;
	private int totalabsent;
	
	
	private int contractpresent;
	private int contractabsent;
	
	private int staffondauty;
	private int staffleave;
	
	private int techstaffondauty;
	private int techstaffleave;
	
	private int conondauty;
	
	private float perstaff;
	private float pertechstaff;
	private float perconstaff;
	private float totalper;
	
	
	private int conleave;
	
	private int totalleave;
	private int totalonduty;
	
	
	
	public int getConleave() {
		return conleave;
	}
	public void setConleave(int conleave) {
		this.conleave = conleave;
	}
	public int getTotalleave() {
		return totalleave;
	}
	public void setTotalleave(int totalleave) {
		this.totalleave = totalleave;
	}
	public int getTotalonduty() {
		return totalonduty;
	}
	public void setTotalonduty(int totalonduty) {
		this.totalonduty = totalonduty;
	}
	public int getTotalapprovedstrength() {
		return totalapprovedstrength;
	}
	public void setTotalapprovedstrength(int totalapprovedstrength) {
		this.totalapprovedstrength = totalapprovedstrength;
	}
	public int getTotalavailstrength() {
		return totalavailstrength;
	}
	public void setTotalavailstrength(int totalavailstrength) {
		this.totalavailstrength = totalavailstrength;
	}
	public int getTotalprsent() {
		return totalprsent;
	}
	public void setTotalprsent(int totalprsent) {
		this.totalprsent = totalprsent;
	}
	public int getTotalabsent() {
		return totalabsent;
	}
	public void setTotalabsent(int totalabsent) {
		this.totalabsent = totalabsent;
	}
	public int getContractpresent() {
		return contractpresent;
	}
	public void setContractpresent(int contractpresent) {
		this.contractpresent = contractpresent;
	}
	public int getContractabsent() {
		return contractabsent;
	}
	public void setContractabsent(int contractabsent) {
		this.contractabsent = contractabsent;
	}
	public int getStaffondauty() {
		return staffondauty;
	}
	public void setStaffondauty(int staffondauty) {
		this.staffondauty = staffondauty;
	}
	public int getStaffleave() {
		return staffleave;
	}
	public void setStaffleave(int staffleave) {
		this.staffleave = staffleave;
	}
	public int getTechstaffondauty() {
		return techstaffondauty;
	}
	public void setTechstaffondauty(int techstaffondauty) {
		this.techstaffondauty = techstaffondauty;
	}
	public int getTechstaffleave() {
		return techstaffleave;
	}
	public void setTechstaffleave(int techstaffleave) {
		this.techstaffleave = techstaffleave;
	}
	public int getConondauty() {
		return conondauty;
	}
	public void setConondauty(int conondauty) {
		this.conondauty = conondauty;
	}
	public float getPerstaff() {
		return perstaff;
	}
	public void setPerstaff(float perstaff) {
		this.perstaff = perstaff;
	}
	public float getPertechstaff() {
		return pertechstaff;
	}
	public void setPertechstaff(float pertechstaff) {
		this.pertechstaff = pertechstaff;
	}
	public float getPerconstaff() {
		return perconstaff;
	}
	public void setPerconstaff(float perconstaff) {
		this.perconstaff = perconstaff;
	}
	public float getTotalper() {
		return totalper;
	}
	public void setTotalper(float totalper) {
		this.totalper = totalper;
	}
	public String getEmpStatus() {
		return empStatus;
	}
	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}
	public int getGrnapprenstaffabsent() {
		return grnapprenstaffabsent;
	}
	public void setGrnapprenstaffabsent(int grnapprenstaffabsent) {
		this.grnapprenstaffabsent = grnapprenstaffabsent;
	}
	public int getGrntechstaffabsent() {
		return grntechstaffabsent;
	}
	public void setGrntechstaffabsent(int grntechstaffabsent) {
		this.grntechstaffabsent = grntechstaffabsent;
	}
	public int getGrnstaffabsent() {
		return grnstaffabsent;
	}
	public void setGrnstaffabsent(int grnstaffabsent) {
		this.grnstaffabsent = grnstaffabsent;
	}
	public int getStaffabsent() {
		return staffabsent;
	}
	public void setStaffabsent(int staffabsent) {
		this.staffabsent = staffabsent;
	}
	public int getTechstaffabsent() {
		return techstaffabsent;
	}
	public void setTechstaffabsent(int techstaffabsent) {
		this.techstaffabsent = techstaffabsent;
	}
	public int getApprenstaffabsent() {
		return apprenstaffabsent;
	}
	public void setApprenstaffabsent(int apprenstaffabsent) {
		this.apprenstaffabsent = apprenstaffabsent;
	}
	private int staffabsent;
	private int techstaffabsent;
	private int apprenstaffabsent;
	
	public String getMidDate() {
		return midDate;
	}
	public void setMidDate(String midDate) {
		this.midDate = midDate;
	}
	private String midDate;
	private ArrayList locationIdList=new ArrayList();
	private ArrayList locationLabelList=new ArrayList();
	private String leavetype;
	
	private String summbrkup;
	private String attntype;
	private String attnstatus;
	
	
	private String frommonth;
	private String tomonth;
	private String calyear;
	
	
	private String employeeno;
	
	
	private String swipetype;
	private String swipereason;
	private String swipetime;
	private String swiperemarks;
	private String latecount;
	
	

	private String swipe_Type;
	private String reason_Type;
	private String time;
	private String Remarks;
	private String count;
	
	
	private ArrayList payGroupList=new ArrayList();
	private ArrayList payGroupLabelList=new ArrayList();
	
	private ArrayList categoryList=new ArrayList();
	private ArrayList categoryLabelList=new ArrayList();
	
	private String plantFrom;
	private String plantTo;
	private String payGrpFrom;
	private String payGrpTo;
	private String catFrom;
	private String catTo;
	
	

	private ArrayList workList=new ArrayList();
	private ArrayList workLabelList=new ArrayList();
	
	
	private String workFrom;
	private String workTo;
	private String shift;
	
	private String rule_Code;
	private String swipe_Count;
	private String doj;
	private String activeStatus;
	
	
	private ArrayList shiftList=new ArrayList();
	private ArrayList shiftLabelList=new ArrayList();
	
	private String frequency;
	private String day;
	private String month;
	
	private String deptFrom;
	private String deptTo;
	
	private int monthFrom;
	private int monthTo;
	private int calYear;
	private String CurentDate;
	
	
	

	private ArrayList deptList=new ArrayList();
	private ArrayList deptLabelList=new ArrayList();
	private String emplist;
	
	
	
	private ArrayList desgList=new ArrayList();
	private ArrayList desgLabelList=new ArrayList();
	
	private String desgFrom;
	private String desgTo;
	
	private ArrayList yearList=new ArrayList();
	private String year;
	
	private float working;
	private int weeklyOFf;
	private int paidHoliday;
	private int secstaurday;
	private float pp;
	private float aa;
	private float od;
	private float pm;
	private float cl;
	private float sl;
	private float el;
	private float lp;
	private float co;
	private float ml;
	private float ph;
	private float wo;
	private float sh;
	private float ss;
	
	private float leave_Availed;
	private float total_worked;
	private float week_off_earned;
	private float paid_days;
	private float worK_hrs;
	
	
	private String day1;
	private String day2;
	private String day3;
	private String day4;
	private String day5;
	private String day6;
	private String day7;
	private String day8;
	private String day9;
	private String day10;
	private String day11;
	private String day12;
	private String day13;
	private String day14;
	private String day15;
	private String day16;
	private String day17;
	private String day18;
	private String day19;
	private String day20;
	private String day21;
	private String day22;
	private String day23;
	private String day24;
	private String day25;
	private String day26;
	private String day27;
	private String day28;
	private String day29;
	private String day30;
	private String day31;
	
	private String type;
	
	private String[] deptArray;
	private String[] desgArray;
	
	private String[] locArray;
	private String[] payArray;
	private String[] catArray;
	
	
	private int leavesize;
	private int odsize;
	private int lopsize;
	private int abssize;
	private int clsize;
	private int slsize;
	private int elsize;
	
	
	private String approverStatus;
	private String approveDate;
	
	 private String reqdate;
	   
	 private int presize;
		private int pmsize;   
		
		
		private String workLocId;
	   
	
		private int progress;
		
		public String process_name;
		public String process_location;
		public String process_parameter;
		public String process_status;
		public String start_time;
		public String end_time;
		public String lock_id;
		public String emp_count;
		public String fetch_st;
		public String fetch_et;
		public String leave_st;
		public String leave_et;
		public String punch_st;
		public String punch_et;
		public String manual_st;
		public String manual_et;
		public String week_st;
		public String week_et;
		public String rep_st;
		public String rep_et; 
		
		private	LinkedList statId=new LinkedList();
		private LinkedList statName=new LinkedList();
		
		public String form_type; 
		
		
		private String explocid;
		private String expcat;
		
		private String perstatus;
		private String addstatus;
		private String famstatus;
		private String edustatus;
		private String expstatus;
		private String lanstatus;
		
		private String holdate1;
		private String holdate2;
		private String holdate3;
		private String holdate4;
		private String holdate5;
		private String holdate6;
		private String holdate7;
		private String holdate8;
		private String holdate9;
		private String holdate10;
		
		
		
		
		public String getHoldate1() {
			return holdate1;
		}
		public void setHoldate1(String holdate1) {
			this.holdate1 = holdate1;
		}
		public String getHoldate2() {
			return holdate2;
		}
		public void setHoldate2(String holdate2) {
			this.holdate2 = holdate2;
		}
		public String getHoldate3() {
			return holdate3;
		}
		public void setHoldate3(String holdate3) {
			this.holdate3 = holdate3;
		}
		public String getHoldate4() {
			return holdate4;
		}
		public void setHoldate4(String holdate4) {
			this.holdate4 = holdate4;
		}
		public String getHoldate5() {
			return holdate5;
		}
		public void setHoldate5(String holdate5) {
			this.holdate5 = holdate5;
		}
		public String getHoldate6() {
			return holdate6;
		}
		public void setHoldate6(String holdate6) {
			this.holdate6 = holdate6;
		}
		public String getHoldate7() {
			return holdate7;
		}
		public void setHoldate7(String holdate7) {
			this.holdate7 = holdate7;
		}
		public String getHoldate8() {
			return holdate8;
		}
		public void setHoldate8(String holdate8) {
			this.holdate8 = holdate8;
		}
		public String getHoldate9() {
			return holdate9;
		}
		public void setHoldate9(String holdate9) {
			this.holdate9 = holdate9;
		}
		public String getHoldate10() {
			return holdate10;
		}
		public void setHoldate10(String holdate10) {
			this.holdate10 = holdate10;
		}
		
		
		public String getPerstatus() {
			return perstatus;
		}
		public void setPerstatus(String perstatus) {
			this.perstatus = perstatus;
		}
		public String getAddstatus() {
			return addstatus;
		}
		public void setAddstatus(String addstatus) {
			this.addstatus = addstatus;
		}
		public String getFamstatus() {
			return famstatus;
		}
		public void setFamstatus(String famstatus) {
			this.famstatus = famstatus;
		}
		public String getEdustatus() {
			return edustatus;
		}
		public void setEdustatus(String edustatus) {
			this.edustatus = edustatus;
		}
		public String getExpstatus() {
			return expstatus;
		}
		public void setExpstatus(String expstatus) {
			this.expstatus = expstatus;
		}
		public String getLanstatus() {
			return lanstatus;
		}
		public void setLanstatus(String lanstatus) {
			this.lanstatus = lanstatus;
		}
		public String getExplocid() {
			return explocid;
		}
		public void setExplocid(String explocid) {
			this.explocid = explocid;
		}
		public String getExpcat() {
			return expcat;
		}
		public void setExpcat(String expcat) {
			this.expcat = expcat;
		}
		public String getForm_type() {
			return form_type;
		}
		public void setForm_type(String form_type) {
			this.form_type = form_type;
		}
		public LinkedList getStatId() {
			return statId;
		}
		public void setStatId(LinkedList statId) {
			this.statId = statId;
		}
		public LinkedList getStatName() {
			return statName;
		}
		public void setStatName(LinkedList statName) {
			this.statName = statName;
		}
		public String getProcess_name() {
			return process_name;
		}
		public void setProcess_name(String process_name) {
			this.process_name = process_name;
		}
		public String getProcess_location() {
			return process_location;
		}
		public void setProcess_location(String process_location) {
			this.process_location = process_location;
		}
		public String getProcess_parameter() {
			return process_parameter;
		}
		public void setProcess_parameter(String process_parameter) {
			this.process_parameter = process_parameter;
		}
		public String getProcess_status() {
			return process_status;
		}
		public void setProcess_status(String process_status) {
			this.process_status = process_status;
		}
		public String getStart_time() {
			return start_time;
		}
		public void setStart_time(String start_time) {
			this.start_time = start_time;
		}
		public String getEnd_time() {
			return end_time;
		}
		public void setEnd_time(String end_time) {
			this.end_time = end_time;
		}
		public String getLock_id() {
			return lock_id;
		}
		public void setLock_id(String lock_id) {
			this.lock_id = lock_id;
		}
		public String getEmp_count() {
			return emp_count;
		}
		public void setEmp_count(String emp_count) {
			this.emp_count = emp_count;
		}
		public String getFetch_st() {
			return fetch_st;
		}
		public void setFetch_st(String fetch_st) {
			this.fetch_st = fetch_st;
		}
		public String getFetch_et() {
			return fetch_et;
		}
		public void setFetch_et(String fetch_et) {
			this.fetch_et = fetch_et;
		}
		public String getLeave_st() {
			return leave_st;
		}
		public void setLeave_st(String leave_st) {
			this.leave_st = leave_st;
		}
		public String getLeave_et() {
			return leave_et;
		}
		public void setLeave_et(String leave_et) {
			this.leave_et = leave_et;
		}
		public String getPunch_st() {
			return punch_st;
		}
		public void setPunch_st(String punch_st) {
			this.punch_st = punch_st;
		}
		public String getPunch_et() {
			return punch_et;
		}
		public void setPunch_et(String punch_et) {
			this.punch_et = punch_et;
		}
		public String getManual_st() {
			return manual_st;
		}
		public void setManual_st(String manual_st) {
			this.manual_st = manual_st;
		}
		public String getManual_et() {
			return manual_et;
		}
		public void setManual_et(String manual_et) {
			this.manual_et = manual_et;
		}
		public String getWeek_st() {
			return week_st;
		}
		public void setWeek_st(String week_st) {
			this.week_st = week_st;
		}
		public String getWeek_et() {
			return week_et;
		}
		public void setWeek_et(String week_et) {
			this.week_et = week_et;
		}
		public String getRep_st() {
			return rep_st;
		}
		public void setRep_st(String rep_st) {
			this.rep_st = rep_st;
		}
		public String getRep_et() {
			return rep_et;
		}
		public void setRep_et(String rep_et) {
			this.rep_et = rep_et;
		}
		public int getProgress() {
			return progress;
		}
		public void setProgress(int progress) {
			this.progress = progress;
		}
		public float getSh() {
			return sh;
		}
		public void setSh(float sh) {
			this.sh = sh;
		}
		private String[] subdeptArray;
		private ArrayList subdeptList=new ArrayList();
		private ArrayList subdeptLabelList=new ArrayList();
		private String subdepartment;
		
		private String[] repgrpArray;
		private ArrayList repgrpList=new ArrayList();
		private ArrayList repgrpLabelList=new ArrayList();
		private String repgrp;
		
		private String[] congrpArray;
		private ArrayList congrpList=new ArrayList();
		private ArrayList congrpLabelList=new ArrayList();
		private String congrp;
		
		private String[] shiftArray;
		
		private int staffapprstrength;
		private int staffavailstrength;
		private int staffpresent;
		private float staffsalary;
		
		private int techstaffapprstrength;
		private int techstaffavailstrength;
		private int techstaffpresent;
		private float techstaffsalary;
		
		
		private int apprenstaffapprstrength;
		private int apprenstaffavailstrength;
		private int apprenstaffpresent;
		private float apprenstaffsalary;
		
		private int contractapprstrength;
		private int contractavailstrength;

		private int skilledpresent;
		
		
		private int unskilledpresent;
		
		private int semiskilledpresent;
		private int skilledabsent;
		private int unskilledabsent;
		private int semiskilledabsent;
		private int securitypresent;
		private int securityabsent;
		private int projectspresent;
		private int projectsabsent;
		
		
		public int getSecurityabsent() {
			return securityabsent;
		}
		public void setSecurityabsent(int securityabsent) {
			this.securityabsent = securityabsent;
		}
		public int getProjectsabsent() {
			return projectsabsent;
		}
		public void setProjectsabsent(int projectsabsent) {
			this.projectsabsent = projectsabsent;
		}
		public int getSkilledabsent() {
			return skilledabsent;
		}
		public void setSkilledabsent(int skilledabsent) {
			this.skilledabsent = skilledabsent;
		}
		public int getUnskilledabsent() {
			return unskilledabsent;
		}
		public void setUnskilledabsent(int unskilledabsent) {
			this.unskilledabsent = unskilledabsent;
		}
		public int getSemiskilledabsent() {
			return semiskilledabsent;
		}
		public void setSemiskilledabsent(int semiskilledabsent) {
			this.semiskilledabsent = semiskilledabsent;
		}
		private int totcontractpresent;
		private int totcontractabsent;
		
		private int totcontractSalary;
		
		
		private int houseapprstrength;	
		private int houseavailstrength;
		
		private int housepresent;
		private int houseabsent;
		private float housesalary;
		
		private int totpresent;
		private int totabsent;
		private float totsalary;
		
		
		private int threeapprstrength;	
		private int threepresent;
		private int threeabsent;
		private float threesalary;
		
		///grand
		
		private int grnstaffapprstrength;
		private int grnstaffavailstrength;
		private int grnstaffpresent;
		private float grnstaffsalary;
		
		private int grntechstaffapprstrength;
		private int grntechstaffavailstrength;
		private int grntechstaffpresent;
		private float grntechstaffsalary;
		
		
		private int grnapprenstaffapprstrength;
		private int grnapprenstaffavailstrength;
		private int grnapprenstaffpresent;
		private float grnapprenstaffsalary;
		
		private int grncontractapprstrength;
		private int grncontractavailstrength;


		private int grnskilledpresent;
		private int grnunskilledpresent;
		private int grnsemiskilledpresent;
		private int grnsecuritypresent;
		private int grnprojectspresent;
		private int grnskilledabsent;
		private int grnunskilledabsent;
		private int grnsemiskilledabsent;
		private int grnsecurityabsent;
		private int grnprojectsabsent;
		
		
		
		public int getThreeabsent() {
			return threeabsent;
		}
		public int getHouseavailstrength() {
			return houseavailstrength;
		}
		public void setHouseavailstrength(int houseavailstrength) {
			this.houseavailstrength = houseavailstrength;
		}
		public void setThreeabsent(int threeabsent) {
			this.threeabsent = threeabsent;
		}
		public int getTotabsent() {
			return totabsent;
		}
		public void setTotabsent(int totabsent) {
			this.totabsent = totabsent;
		}
		public int getHouseabsent() {
			return houseabsent;
		}
		public void setHouseabsent(int houseabsent) {
			this.houseabsent = houseabsent;
		}
		public int getTotcontractabsent() {
			return totcontractabsent;
		}
		public void setTotcontractabsent(int totcontractabsent) {
			this.totcontractabsent = totcontractabsent;
		}
		public int getGrnskilledabsent() {
			return grnskilledabsent;
		}
		public void setGrnskilledabsent(int grnskilledabsent) {
			this.grnskilledabsent = grnskilledabsent;
		}
		public int getGrnunskilledabsent() {
			return grnunskilledabsent;
		}
		public void setGrnunskilledabsent(int grnunskilledabsent) {
			this.grnunskilledabsent = grnunskilledabsent;
		}
		public int getGrnsemiskilledabsent() {
			return grnsemiskilledabsent;
		}
		public void setGrnsemiskilledabsent(int grnsemiskilledabsent) {
			this.grnsemiskilledabsent = grnsemiskilledabsent;
		}
		public int getGrnsecurityabsent() {
			return grnsecurityabsent;
		}
		public void setGrnsecurityabsent(int grnsecurityabsent) {
			this.grnsecurityabsent = grnsecurityabsent;
		}
		public int getGrnprojectsabsent() {
			return grnprojectsabsent;
		}
		public void setGrnprojectsabsent(int grnprojectsabsent) {
			this.grnprojectsabsent = grnprojectsabsent;
		}
		private int grntotcontractpresent;
		private int grntotcontractabsent;
		private int grntotcontractSalary;
		
		
		private int grnhouseapprstrength;	
		private int grnhouseavailstrength;	
		public int getGrnhouseavailstrength() {
			return grnhouseavailstrength;
		}
		public void setGrnhouseavailstrength(int grnhouseavailstrength) {
			this.grnhouseavailstrength = grnhouseavailstrength;
		}
		private int grnhousepresent;
		private int grnhouseabsent;
		private float grnhousesalary;
		
		private int grntotpresent;
		private int grntotabsent;
		private float grntotsalary;
		
		
		private int grnthreeapprstrength;	
		private int grnthreepresent;
		private int grnthreeabsent;
		private float grnthreesalary;
		
	
		private String early;	
		private String late;
		private String total;
		private String ot;
		
		private int availablecount;
		private int additioncount;
		private int attritioncount;
		
		
		private int staffadditioncount;
		private int staffattritioncount;
		private float staffattritioncountpercent;
		
		private int techstaffadditioncount;
		private int techstaffattritioncount;
		private float techstaffattritioncountpercent;
		
		private int cumstaffattritioncount;
		private float cumstaffattritioncountpercent;
		private int cumtechstaffattritioncount;
		private float cumtechstaffattritioncountpercent;
		
		
		private int grnstaffadditioncount;
		private int grnstaffattritioncount;
		private float grnstaffattritioncountpercent;
		
		private int grntechstaffadditioncount;
		private int grntechstaffattritioncount;
		private float grntechstaffattritioncountpercent;
		
		private int grncumstaffattritioncount;
		private float grncumstaffattritioncountpercent;
		private int grncumtechstaffattritioncount;
		private float grncumtechstaffattritioncountpercent;
		
		
		
		private float cl_openingBalence;
		private float cl_avalableBalence;
		private float cl_closingBalence;
		
		private float sl_openingBalence;
		private float sl_avalableBalence;
		private float sl_closingBalence;
		
		private float el_openingBalence;
		private float el_avalableBalence;
		private float el_closingBalence;
		
		private String qualification;
		private String specialization;
		
		
		private String fullpunch;
		
		private String punchloc;
		
		private String punchdate;
		
		private float consal;
		
		private String emptype;
		
		
		
		
		
		
	
		public int getGrntotcontractabsent() {
			return grntotcontractabsent;
		}
		public void setGrntotcontractabsent(int grntotcontractabsent) {
			this.grntotcontractabsent = grntotcontractabsent;
		}
		public int getGrnhouseabsent() {
			return grnhouseabsent;
		}
		public void setGrnhouseabsent(int grnhouseabsent) {
			this.grnhouseabsent = grnhouseabsent;
		}
		public int getGrntotabsent() {
			return grntotabsent;
		}
		public void setGrntotabsent(int grntotabsent) {
			this.grntotabsent = grntotabsent;
		}
		public int getGrnthreeabsent() {
			return grnthreeabsent;
		}
		public void setGrnthreeabsent(int grnthreeabsent) {
			this.grnthreeabsent = grnthreeabsent;
		}
		public String getEmptype() {
			return emptype;
		}
		public void setEmptype(String emptype) {
			this.emptype = emptype;
		}
		public float getSs() {
			return ss;
		}
		public void setSs(float ss) {
			this.ss = ss;
		}
		public float getConsal() {
			return consal;
		}
		public void setConsal(float consal) {
			this.consal = consal;
		}
		public String getPunchdate() {
			return punchdate;
		}
		public void setPunchdate(String punchdate) {
			this.punchdate = punchdate;
		}
		public String getPunchloc() {
			return punchloc;
		}
		public void setPunchloc(String punchloc) {
			this.punchloc = punchloc;
		}
		public String getFullpunch() {
			return fullpunch;
		}
		public void setFullpunch(String fullpunch) {
			this.fullpunch = fullpunch;
		}
		public String[] getCongrpArray() {
			return congrpArray;
		}
		public void setCongrpArray(String[] congrpArray) {
			this.congrpArray = congrpArray;
		}
		public ArrayList getCongrpList() {
			return congrpList;
		}
		public void setCongrpList(ArrayList congrpList) {
			this.congrpList = congrpList;
		}
		public ArrayList getCongrpLabelList() {
			return congrpLabelList;
		}
		public void setCongrpLabelList(ArrayList congrpLabelList) {
			this.congrpLabelList = congrpLabelList;
		}
		public String getCongrp() {
			return congrp;
		}
		public void setCongrp(String congrp) {
			this.congrp = congrp;
		}
		public String getOt() {
			return ot;
		}
		public void setOt(String ot) {
			this.ot = ot;
		}
		public int getClsize() {
			return clsize;
		}
		public void setClsize(int clsize) {
			this.clsize = clsize;
		}
		public int getSlsize() {
			return slsize;
		}
		public void setSlsize(int slsize) {
			this.slsize = slsize;
		}
		public int getElsize() {
			return elsize;
		}
		public void setElsize(int elsize) {
			this.elsize = elsize;
		}
		public String getSpecialization() {
			return specialization;
		}
		public void setSpecialization(String specialization) {
			this.specialization = specialization;
		}
		public String getQualification() {
			return qualification;
		}
		public void setQualification(String qualification) {
			this.qualification = qualification;
		}
		public float getCl_openingBalence() {
			return cl_openingBalence;
		}
		public void setCl_openingBalence(float cl_openingBalence) {
			this.cl_openingBalence = cl_openingBalence;
		}
		public float getCl_avalableBalence() {
			return cl_avalableBalence;
		}
		public void setCl_avalableBalence(float cl_avalableBalence) {
			this.cl_avalableBalence = cl_avalableBalence;
		}
		public float getCl_closingBalence() {
			return cl_closingBalence;
		}
		public void setCl_closingBalence(float cl_closingBalence) {
			this.cl_closingBalence = cl_closingBalence;
		}
		public float getSl_openingBalence() {
			return sl_openingBalence;
		}
		public void setSl_openingBalence(float sl_openingBalence) {
			this.sl_openingBalence = sl_openingBalence;
		}
		public float getSl_avalableBalence() {
			return sl_avalableBalence;
		}
		public void setSl_avalableBalence(float sl_avalableBalence) {
			this.sl_avalableBalence = sl_avalableBalence;
		}
		public float getSl_closingBalence() {
			return sl_closingBalence;
		}
		public void setSl_closingBalence(float sl_closingBalence) {
			this.sl_closingBalence = sl_closingBalence;
		}
		public float getEl_openingBalence() {
			return el_openingBalence;
		}
		public void setEl_openingBalence(float el_openingBalence) {
			this.el_openingBalence = el_openingBalence;
		}
		public float getEl_avalableBalence() {
			return el_avalableBalence;
		}
		public void setEl_avalableBalence(float el_avalableBalence) {
			this.el_avalableBalence = el_avalableBalence;
		}
		public float getEl_closingBalence() {
			return el_closingBalence;
		}
		public void setEl_closingBalence(float el_closingBalence) {
			this.el_closingBalence = el_closingBalence;
		}
		public int getStaffadditioncount() {
			return staffadditioncount;
		}
		public void setStaffadditioncount(int staffadditioncount) {
			this.staffadditioncount = staffadditioncount;
		}
		public int getStaffattritioncount() {
			return staffattritioncount;
		}
		public void setStaffattritioncount(int staffattritioncount) {
			this.staffattritioncount = staffattritioncount;
		}
		public float getStaffattritioncountpercent() {
			return staffattritioncountpercent;
		}
		public void setStaffattritioncountpercent(float staffattritioncountpercent) {
			this.staffattritioncountpercent = staffattritioncountpercent;
		}
		public int getTechstaffadditioncount() {
			return techstaffadditioncount;
		}
		public void setTechstaffadditioncount(int techstaffadditioncount) {
			this.techstaffadditioncount = techstaffadditioncount;
		}
		public int getTechstaffattritioncount() {
			return techstaffattritioncount;
		}
		public void setTechstaffattritioncount(int techstaffattritioncount) {
			this.techstaffattritioncount = techstaffattritioncount;
		}
		public float getTechstaffattritioncountpercent() {
			return techstaffattritioncountpercent;
		}
		public void setTechstaffattritioncountpercent(
				float techstaffattritioncountpercent) {
			this.techstaffattritioncountpercent = techstaffattritioncountpercent;
		}
		public int getCumstaffattritioncount() {
			return cumstaffattritioncount;
		}
		public void setCumstaffattritioncount(int cumstaffattritioncount) {
			this.cumstaffattritioncount = cumstaffattritioncount;
		}
		public float getCumstaffattritioncountpercent() {
			return cumstaffattritioncountpercent;
		}
		public void setCumstaffattritioncountpercent(float cumstaffattritioncountpercent) {
			this.cumstaffattritioncountpercent = cumstaffattritioncountpercent;
		}
		public int getCumtechstaffattritioncount() {
			return cumtechstaffattritioncount;
		}
		public void setCumtechstaffattritioncount(int cumtechstaffattritioncount) {
			this.cumtechstaffattritioncount = cumtechstaffattritioncount;
		}
		public float getCumtechstaffattritioncountpercent() {
			return cumtechstaffattritioncountpercent;
		}
		public void setCumtechstaffattritioncountpercent(
				float cumtechstaffattritioncountpercent) {
			this.cumtechstaffattritioncountpercent = cumtechstaffattritioncountpercent;
		}
		public int getGrnstaffadditioncount() {
			return grnstaffadditioncount;
		}
		public void setGrnstaffadditioncount(int grnstaffadditioncount) {
			this.grnstaffadditioncount = grnstaffadditioncount;
		}
		public int getGrnstaffattritioncount() {
			return grnstaffattritioncount;
		}
		public void setGrnstaffattritioncount(int grnstaffattritioncount) {
			this.grnstaffattritioncount = grnstaffattritioncount;
		}
		public float getGrnstaffattritioncountpercent() {
			return grnstaffattritioncountpercent;
		}
		public void setGrnstaffattritioncountpercent(float grnstaffattritioncountpercent) {
			this.grnstaffattritioncountpercent = grnstaffattritioncountpercent;
		}
		public int getGrntechstaffadditioncount() {
			return grntechstaffadditioncount;
		}
		public void setGrntechstaffadditioncount(int grntechstaffadditioncount) {
			this.grntechstaffadditioncount = grntechstaffadditioncount;
		}
		public int getGrntechstaffattritioncount() {
			return grntechstaffattritioncount;
		}
		public void setGrntechstaffattritioncount(int grntechstaffattritioncount) {
			this.grntechstaffattritioncount = grntechstaffattritioncount;
		}
		public float getGrntechstaffattritioncountpercent() {
			return grntechstaffattritioncountpercent;
		}
		public void setGrntechstaffattritioncountpercent(
				float grntechstaffattritioncountpercent) {
			this.grntechstaffattritioncountpercent = grntechstaffattritioncountpercent;
		}
		public int getGrncumstaffattritioncount() {
			return grncumstaffattritioncount;
		}
		public void setGrncumstaffattritioncount(int grncumstaffattritioncount) {
			this.grncumstaffattritioncount = grncumstaffattritioncount;
		}
		public float getGrncumstaffattritioncountpercent() {
			return grncumstaffattritioncountpercent;
		}
		public void setGrncumstaffattritioncountpercent(
				float grncumstaffattritioncountpercent) {
			this.grncumstaffattritioncountpercent = grncumstaffattritioncountpercent;
		}
		public int getGrncumtechstaffattritioncount() {
			return grncumtechstaffattritioncount;
		}
		public void setGrncumtechstaffattritioncount(int grncumtechstaffattritioncount) {
			this.grncumtechstaffattritioncount = grncumtechstaffattritioncount;
		}
		public float getGrncumtechstaffattritioncountpercent() {
			return grncumtechstaffattritioncountpercent;
		}
		public void setGrncumtechstaffattritioncountpercent(
				float grncumtechstaffattritioncountpercent) {
			this.grncumtechstaffattritioncountpercent = grncumtechstaffattritioncountpercent;
		}
		public int getContractavailstrength() {
			return contractavailstrength;
		}
		public void setContractavailstrength(int contractavailstrength) {
			this.contractavailstrength = contractavailstrength;
		}
		public int getGrncontractavailstrength() {
			return grncontractavailstrength;
		}
		public void setGrncontractavailstrength(int grncontractavailstrength) {
			this.grncontractavailstrength = grncontractavailstrength;
		}
		public float getAa() {
			return aa;
		}
		public void setAa(float aa) {
			this.aa = aa;
		}
		public int getAvailablecount() {
			return availablecount;
		}
		public void setAvailablecount(int availablecount) {
			this.availablecount = availablecount;
		}
		public int getAdditioncount() {
			return additioncount;
		}
		public void setAdditioncount(int additioncount) {
			this.additioncount = additioncount;
		}
		public int getAttritioncount() {
			return attritioncount;
		}
		public void setAttritioncount(int attritioncount) {
			this.attritioncount = attritioncount;
		}
		public float getWo() {
			return wo;
		}
		public void setWo(float wo) {
			this.wo = wo;
		}
		public float getPh() {
			return ph;
		}
		public void setPh(float ph) {
			this.ph = ph;
		}
		public float getWeek_off_earned() {
			return week_off_earned;
		}
		public void setWeek_off_earned(float week_off_earned) {
			this.week_off_earned = week_off_earned;
		}
		public float getPaid_days() {
			return paid_days;
		}
		public void setPaid_days(float paid_days) {
			this.paid_days = paid_days;
		}
		public float getWorK_hrs() {
			return worK_hrs;
		}
		public void setWorK_hrs(float worK_hrs) {
			this.worK_hrs = worK_hrs;
		}
		public String getEarly() {
			return early;
		}
		public void setEarly(String early) {
			this.early = early;
		}
		public String getLate() {
			return late;
		}
		public void setLate(String late) {
			this.late = late;
		}
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public int getStaffapprstrength() {
			return staffapprstrength;
		}
		public void setStaffapprstrength(int staffapprstrength) {
			this.staffapprstrength = staffapprstrength;
		}
		public int getStaffavailstrength() {
			return staffavailstrength;
		}
		public void setStaffavailstrength(int staffavailstrength) {
			this.staffavailstrength = staffavailstrength;
		}
		public int getStaffpresent() {
			return staffpresent;
		}
		public void setStaffpresent(int staffpresent) {
			this.staffpresent = staffpresent;
		}
		public float getStaffsalary() {
			return staffsalary;
		}
		public void setStaffsalary(float staffsalary) {
			this.staffsalary = staffsalary;
		}
		public int getTechstaffapprstrength() {
			return techstaffapprstrength;
		}
		public void setTechstaffapprstrength(int techstaffapprstrength) {
			this.techstaffapprstrength = techstaffapprstrength;
		}
		public int getTechstaffavailstrength() {
			return techstaffavailstrength;
		}
		public void setTechstaffavailstrength(int techstaffavailstrength) {
			this.techstaffavailstrength = techstaffavailstrength;
		}
		public int getTechstaffpresent() {
			return techstaffpresent;
		}
		public void setTechstaffpresent(int techstaffpresent) {
			this.techstaffpresent = techstaffpresent;
		}
		public float getTechstaffsalary() {
			return techstaffsalary;
		}
		public void setTechstaffsalary(float techstaffsalary) {
			this.techstaffsalary = techstaffsalary;
		}
		public int getApprenstaffapprstrength() {
			return apprenstaffapprstrength;
		}
		public void setApprenstaffapprstrength(int apprenstaffapprstrength) {
			this.apprenstaffapprstrength = apprenstaffapprstrength;
		}
		public int getApprenstaffavailstrength() {
			return apprenstaffavailstrength;
		}
		public void setApprenstaffavailstrength(int apprenstaffavailstrength) {
			this.apprenstaffavailstrength = apprenstaffavailstrength;
		}
		public int getApprenstaffpresent() {
			return apprenstaffpresent;
		}
		public void setApprenstaffpresent(int apprenstaffpresent) {
			this.apprenstaffpresent = apprenstaffpresent;
		}
		public float getApprenstaffsalary() {
			return apprenstaffsalary;
		}
		public void setApprenstaffsalary(float apprenstaffsalary) {
			this.apprenstaffsalary = apprenstaffsalary;
		}
		public int getContractapprstrength() {
			return contractapprstrength;
		}
		public void setContractapprstrength(int contractapprstrength) {
			this.contractapprstrength = contractapprstrength;
		}
		public int getSkilledpresent() {
			return skilledpresent;
		}
		public void setSkilledpresent(int skilledpresent) {
			this.skilledpresent = skilledpresent;
		}
		public int getUnskilledpresent() {
			return unskilledpresent;
		}
		public void setUnskilledpresent(int unskilledpresent) {
			this.unskilledpresent = unskilledpresent;
		}
		public int getSemiskilledpresent() {
			return semiskilledpresent;
		}
		public void setSemiskilledpresent(int semiskilledpresent) {
			this.semiskilledpresent = semiskilledpresent;
		}
		public int getSecuritypresent() {
			return securitypresent;
		}
		public void setSecuritypresent(int securitypresent) {
			this.securitypresent = securitypresent;
		}
		public int getProjectspresent() {
			return projectspresent;
		}
		public void setProjectspresent(int projectspresent) {
			this.projectspresent = projectspresent;
		}
		public int getTotcontractpresent() {
			return totcontractpresent;
		}
		public void setTotcontractpresent(int totcontractpresent) {
			this.totcontractpresent = totcontractpresent;
		}
		public int getTotcontractSalary() {
			return totcontractSalary;
		}
		public void setTotcontractSalary(int totcontractSalary) {
			this.totcontractSalary = totcontractSalary;
		}
		public int getHouseapprstrength() {
			return houseapprstrength;
		}
		public void setHouseapprstrength(int houseapprstrength) {
			this.houseapprstrength = houseapprstrength;
		}
		public int getHousepresent() {
			return housepresent;
		}
		public void setHousepresent(int housepresent) {
			this.housepresent = housepresent;
		}
		public float getHousesalary() {
			return housesalary;
		}
		public void setHousesalary(float housesalary) {
			this.housesalary = housesalary;
		}
		public int getThreeapprstrength() {
			return threeapprstrength;
		}
		public void setThreeapprstrength(int threeapprstrength) {
			this.threeapprstrength = threeapprstrength;
		}
		public int getThreepresent() {
			return threepresent;
		}
		public void setThreepresent(int threepresent) {
			this.threepresent = threepresent;
		}
		public float getThreesalary() {
			return threesalary;
		}
		public void setThreesalary(float threesalary) {
			this.threesalary = threesalary;
		}
		public int getGrnstaffapprstrength() {
			return grnstaffapprstrength;
		}
		public void setGrnstaffapprstrength(int grnstaffapprstrength) {
			this.grnstaffapprstrength = grnstaffapprstrength;
		}
		public int getGrnstaffavailstrength() {
			return grnstaffavailstrength;
		}
		public void setGrnstaffavailstrength(int grnstaffavailstrength) {
			this.grnstaffavailstrength = grnstaffavailstrength;
		}
		public int getGrnstaffpresent() {
			return grnstaffpresent;
		}
		public void setGrnstaffpresent(int grnstaffpresent) {
			this.grnstaffpresent = grnstaffpresent;
		}
		public float getGrnstaffsalary() {
			return grnstaffsalary;
		}
		public void setGrnstaffsalary(float grnstaffsalary) {
			this.grnstaffsalary = grnstaffsalary;
		}
		public int getGrntechstaffapprstrength() {
			return grntechstaffapprstrength;
		}
		public void setGrntechstaffapprstrength(int grntechstaffapprstrength) {
			this.grntechstaffapprstrength = grntechstaffapprstrength;
		}
		public int getGrntechstaffavailstrength() {
			return grntechstaffavailstrength;
		}
		public void setGrntechstaffavailstrength(int grntechstaffavailstrength) {
			this.grntechstaffavailstrength = grntechstaffavailstrength;
		}
		public int getGrntechstaffpresent() {
			return grntechstaffpresent;
		}
		public void setGrntechstaffpresent(int grntechstaffpresent) {
			this.grntechstaffpresent = grntechstaffpresent;
		}
		public float getGrntechstaffsalary() {
			return grntechstaffsalary;
		}
		public void setGrntechstaffsalary(float grntechstaffsalary) {
			this.grntechstaffsalary = grntechstaffsalary;
		}
		public int getGrnapprenstaffapprstrength() {
			return grnapprenstaffapprstrength;
		}
		public void setGrnapprenstaffapprstrength(int grnapprenstaffapprstrength) {
			this.grnapprenstaffapprstrength = grnapprenstaffapprstrength;
		}
		public int getGrnapprenstaffavailstrength() {
			return grnapprenstaffavailstrength;
		}
		public void setGrnapprenstaffavailstrength(int grnapprenstaffavailstrength) {
			this.grnapprenstaffavailstrength = grnapprenstaffavailstrength;
		}
		public int getGrnapprenstaffpresent() {
			return grnapprenstaffpresent;
		}
		public void setGrnapprenstaffpresent(int grnapprenstaffpresent) {
			this.grnapprenstaffpresent = grnapprenstaffpresent;
		}
		public float getGrnapprenstaffsalary() {
			return grnapprenstaffsalary;
		}
		public void setGrnapprenstaffsalary(float grnapprenstaffsalary) {
			this.grnapprenstaffsalary = grnapprenstaffsalary;
		}
		public int getGrncontractapprstrength() {
			return grncontractapprstrength;
		}
		public void setGrncontractapprstrength(int grncontractapprstrength) {
			this.grncontractapprstrength = grncontractapprstrength;
		}
		public int getGrnskilledpresent() {
			return grnskilledpresent;
		}
		public void setGrnskilledpresent(int grnskilledpresent) {
			this.grnskilledpresent = grnskilledpresent;
		}
		public int getGrnunskilledpresent() {
			return grnunskilledpresent;
		}
		public void setGrnunskilledpresent(int grnunskilledpresent) {
			this.grnunskilledpresent = grnunskilledpresent;
		}
		public int getGrnsemiskilledpresent() {
			return grnsemiskilledpresent;
		}
		public void setGrnsemiskilledpresent(int grnsemiskilledpresent) {
			this.grnsemiskilledpresent = grnsemiskilledpresent;
		}
		public int getGrnsecuritypresent() {
			return grnsecuritypresent;
		}
		public void setGrnsecuritypresent(int grnsecuritypresent) {
			this.grnsecuritypresent = grnsecuritypresent;
		}
		public int getGrnprojectspresent() {
			return grnprojectspresent;
		}
		public void setGrnprojectspresent(int grnprojectspresent) {
			this.grnprojectspresent = grnprojectspresent;
		}
		public int getGrntotcontractpresent() {
			return grntotcontractpresent;
		}
		public void setGrntotcontractpresent(int grntotcontractpresent) {
			this.grntotcontractpresent = grntotcontractpresent;
		}
		public int getGrntotcontractSalary() {
			return grntotcontractSalary;
		}
		public void setGrntotcontractSalary(int grntotcontractSalary) {
			this.grntotcontractSalary = grntotcontractSalary;
		}
		public int getGrnhouseapprstrength() {
			return grnhouseapprstrength;
		}
		public void setGrnhouseapprstrength(int grnhouseapprstrength) {
			this.grnhouseapprstrength = grnhouseapprstrength;
		}
		public int getGrnhousepresent() {
			return grnhousepresent;
		}
		public void setGrnhousepresent(int grnhousepresent) {
			this.grnhousepresent = grnhousepresent;
		}
		public float getGrnhousesalary() {
			return grnhousesalary;
		}
		public void setGrnhousesalary(float grnhousesalary) {
			this.grnhousesalary = grnhousesalary;
		}
		public int getGrntotpresent() {
			return grntotpresent;
		}
		public void setGrntotpresent(int grntotpresent) {
			this.grntotpresent = grntotpresent;
		}
		public float getGrntotsalary() {
			return grntotsalary;
		}
		public void setGrntotsalary(float grntotsalary) {
			this.grntotsalary = grntotsalary;
		}
		public int getGrnthreeapprstrength() {
			return grnthreeapprstrength;
		}
		public void setGrnthreeapprstrength(int grnthreeapprstrength) {
			this.grnthreeapprstrength = grnthreeapprstrength;
		}
		public int getGrnthreepresent() {
			return grnthreepresent;
		}
		public void setGrnthreepresent(int grnthreepresent) {
			this.grnthreepresent = grnthreepresent;
		}
		public float getGrnthreesalary() {
			return grnthreesalary;
		}
		public void setGrnthreesalary(float grnthreesalary) {
			this.grnthreesalary = grnthreesalary;
		}
		public int getTotpresent() {
			return totpresent;
		}
		public void setTotpresent(int totpresent) {
			this.totpresent = totpresent;
		}
		public float getTotsalary() {
			return totsalary;
		}
		public void setTotsalary(float totsalary) {
			this.totsalary = totsalary;
		}
	public String[] getShiftArray() {
			return shiftArray;
		}
		public void setShiftArray(String[] shiftArray) {
			this.shiftArray = shiftArray;
		}
	public String[] getSubdeptArray() {
			return subdeptArray;
		}
		public void setSubdeptArray(String[] subdeptArray) {
			this.subdeptArray = subdeptArray;
		}
		public ArrayList getSubdeptList() {
			return subdeptList;
		}
		public void setSubdeptList(ArrayList subdeptList) {
			this.subdeptList = subdeptList;
		}
		public ArrayList getSubdeptLabelList() {
			return subdeptLabelList;
		}
		public void setSubdeptLabelList(ArrayList subdeptLabelList) {
			this.subdeptLabelList = subdeptLabelList;
		}
		public String getSubdepartment() {
			return subdepartment;
		}
		public void setSubdepartment(String subdepartment) {
			this.subdepartment = subdepartment;
		}
		public String[] getRepgrpArray() {
			return repgrpArray;
		}
		public void setRepgrpArray(String[] repgrpArray) {
			this.repgrpArray = repgrpArray;
		}
		public ArrayList getRepgrpList() {
			return repgrpList;
		}
		public void setRepgrpList(ArrayList repgrpList) {
			this.repgrpList = repgrpList;
		}
		public ArrayList getRepgrpLabelList() {
			return repgrpLabelList;
		}
		public void setRepgrpLabelList(ArrayList repgrpLabelList) {
			this.repgrpLabelList = repgrpLabelList;
		}
		public String getRepgrp() {
			return repgrp;
		}
		public void setRepgrp(String repgrp) {
			this.repgrp = repgrp;
		}
	public String getWorkLocId() {
			return workLocId;
		}
		public void setWorkLocId(String workLocId) {
			this.workLocId = workLocId;
		}
	public int getPresize() {
			return presize;
		}
		public void setPresize(int presize) {
			this.presize = presize;
		}
		public int getPmsize() {
			return pmsize;
		}
		public void setPmsize(int pmsize) {
			this.pmsize = pmsize;
		}
	public String getReqdate() {
		return reqdate;
	}
	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}
	public String getApproverStatus() {
		return approverStatus;
	}
	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}
	public String getApproveDate() {
		return approveDate;
	}
	public void setApproveDate(String approveDate) {
		this.approveDate = approveDate;
	}
	public int getLeavesize() {
		return leavesize;
	}
	public void setLeavesize(int leavesize) {
		this.leavesize = leavesize;
	}

	public int getOdsize() {
		return odsize;
	}
	public void setOdsize(int odsize) {
		this.odsize = odsize;
	}
	public int getLopsize() {
		return lopsize;
	}
	public void setLopsize(int lopsize) {
		this.lopsize = lopsize;
	}
	public int getAbssize() {
		return abssize;
	}
	public void setAbssize(int abssize) {
		this.abssize = abssize;
	}
	public String[] getDeptArray() {
		return deptArray;
	}
	public void setDeptArray(String[] deptArray) {
		this.deptArray = deptArray;
	}
	public String[] getDesgArray() {
		return desgArray;
	}
	public void setDesgArray(String[] desgArray) {
		this.desgArray = desgArray;
	}
	public String[] getLocArray() {
		return locArray;
	}
	public void setLocArray(String[] locArray) {
		this.locArray = locArray;
	}
	public String[] getPayArray() {
		return payArray;
	}
	public void setPayArray(String[] payArray) {
		this.payArray = payArray;
	}
	public String[] getCatArray() {
		return catArray;
	}
	public void setCatArray(String[] catArray) {
		this.catArray = catArray;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDay1() {
		return day1;
	}
	public void setDay1(String day1) {
		this.day1 = day1;
	}
	public String getDay2() {
		return day2;
	}
	public void setDay2(String day2) {
		this.day2 = day2;
	}
	public String getDay3() {
		return day3;
	}
	public void setDay3(String day3) {
		this.day3 = day3;
	}
	public String getDay4() {
		return day4;
	}
	public void setDay4(String day4) {
		this.day4 = day4;
	}
	public String getDay5() {
		return day5;
	}
	public void setDay5(String day5) {
		this.day5 = day5;
	}
	public String getDay6() {
		return day6;
	}
	public void setDay6(String day6) {
		this.day6 = day6;
	}
	public String getDay7() {
		return day7;
	}
	public void setDay7(String day7) {
		this.day7 = day7;
	}
	public String getDay8() {
		return day8;
	}
	public void setDay8(String day8) {
		this.day8 = day8;
	}
	public String getDay9() {
		return day9;
	}
	public void setDay9(String day9) {
		this.day9 = day9;
	}
	public String getDay10() {
		return day10;
	}
	public void setDay10(String day10) {
		this.day10 = day10;
	}
	public String getDay11() {
		return day11;
	}
	public void setDay11(String day11) {
		this.day11 = day11;
	}
	public String getDay12() {
		return day12;
	}
	public void setDay12(String day12) {
		this.day12 = day12;
	}
	public String getDay13() {
		return day13;
	}
	public void setDay13(String day13) {
		this.day13 = day13;
	}
	public String getDay14() {
		return day14;
	}
	public void setDay14(String day14) {
		this.day14 = day14;
	}
	public String getDay15() {
		return day15;
	}
	public void setDay15(String day15) {
		this.day15 = day15;
	}
	public String getDay16() {
		return day16;
	}
	public void setDay16(String day16) {
		this.day16 = day16;
	}
	public String getDay17() {
		return day17;
	}
	public void setDay17(String day17) {
		this.day17 = day17;
	}
	public String getDay18() {
		return day18;
	}
	public void setDay18(String day18) {
		this.day18 = day18;
	}
	public String getDay19() {
		return day19;
	}
	public void setDay19(String day19) {
		this.day19 = day19;
	}
	public String getDay20() {
		return day20;
	}
	public void setDay20(String day20) {
		this.day20 = day20;
	}
	public String getDay21() {
		return day21;
	}
	public void setDay21(String day21) {
		this.day21 = day21;
	}
	public String getDay22() {
		return day22;
	}
	public void setDay22(String day22) {
		this.day22 = day22;
	}
	public String getDay23() {
		return day23;
	}
	public void setDay23(String day23) {
		this.day23 = day23;
	}
	public String getDay24() {
		return day24;
	}
	public void setDay24(String day24) {
		this.day24 = day24;
	}
	public String getDay25() {
		return day25;
	}
	public void setDay25(String day25) {
		this.day25 = day25;
	}
	public String getDay26() {
		return day26;
	}
	public void setDay26(String day26) {
		this.day26 = day26;
	}
	public String getDay27() {
		return day27;
	}
	public void setDay27(String day27) {
		this.day27 = day27;
	}
	public String getDay28() {
		return day28;
	}
	public void setDay28(String day28) {
		this.day28 = day28;
	}
	public String getDay29() {
		return day29;
	}
	public void setDay29(String day29) {
		this.day29 = day29;
	}
	public String getDay30() {
		return day30;
	}
	public void setDay30(String day30) {
		this.day30 = day30;
	}
	public String getDay31() {
		return day31;
	}
	public void setDay31(String day31) {
		this.day31 = day31;
	}
	public float getLeave_Availed() {
		return leave_Availed;
	}
	public void setLeave_Availed(float leave_Availed) {
		this.leave_Availed = leave_Availed;
	}
	public float getTotal_worked() {
		return total_worked;
	}
	public void setTotal_worked(float total_worked) {
		this.total_worked = total_worked;
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
	public String getDesgFrom() {
		return desgFrom;
	}
	public void setDesgFrom(String desgFrom) {
		this.desgFrom = desgFrom;
	}
	public String getDesgTo() {
		return desgTo;
	}
	public void setDesgTo(String desgTo) {
		this.desgTo = desgTo;
	}
	public ArrayList getYearList() {
		return yearList;
	}
	public void setYearList(ArrayList yearList) {
		this.yearList = yearList;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public float getWorking() {
		return working;
	}
	public void setWorking(float working) {
		this.working = working;
	}
	public int getWeeklyOFf() {
		return weeklyOFf;
	}
	public void setWeeklyOFf(int weeklyOFf) {
		this.weeklyOFf = weeklyOFf;
	}
	public int getPaidHoliday() {
		return paidHoliday;
	}
	public void setPaidHoliday(int paidHoliday) {
		this.paidHoliday = paidHoliday;
	}
	public int getSecstaurday() {
		return secstaurday;
	}
	public void setSecstaurday(int secstaurday) {
		this.secstaurday = secstaurday;
	}
	public float getPp() {
		return pp;
	}
	public void setPp(float pp) {
		this.pp = pp;
	}
	public float getOd() {
		return od;
	}
	public void setOd(float od) {
		this.od = od;
	}
	public float getPm() {
		return pm;
	}
	public void setPm(float pm) {
		this.pm = pm;
	}
	public float getCl() {
		return cl;
	}
	public void setCl(float cl) {
		this.cl = cl;
	}
	public float getSl() {
		return sl;
	}
	public void setSl(float sl) {
		this.sl = sl;
	}
	public float getEl() {
		return el;
	}
	public void setEl(float el) {
		this.el = el;
	}
	public float getLp() {
		return lp;
	}
	public void setLp(float lp) {
		this.lp = lp;
	}
	public float getCo() {
		return co;
	}
	public void setCo(float co) {
		this.co = co;
	}
	public float getMl() {
		return ml;
	}
	public void setMl(float ml) {
		this.ml = ml;
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
	public String getEmplist() {
		return emplist;
	}
	public void setEmplist(String emplist) {
		this.emplist = emplist;
	}
	public ArrayList getPayGroupList() {
		return payGroupList;
	}
	public void setPayGroupList(ArrayList payGroupList) {
		this.payGroupList = payGroupList;
	}
	public ArrayList getPayGroupLabelList() {
		return payGroupLabelList;
	}
	public void setPayGroupLabelList(ArrayList payGroupLabelList) {
		this.payGroupLabelList = payGroupLabelList;
	}
	public ArrayList getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(ArrayList categoryList) {
		this.categoryList = categoryList;
	}
	public ArrayList getCategoryLabelList() {
		return categoryLabelList;
	}
	public void setCategoryLabelList(ArrayList categoryLabelList) {
		this.categoryLabelList = categoryLabelList;
	}
	public String getPlantFrom() {
		return plantFrom;
	}
	public void setPlantFrom(String plantFrom) {
		this.plantFrom = plantFrom;
	}
	public String getPlantTo() {
		return plantTo;
	}
	public void setPlantTo(String plantTo) {
		this.plantTo = plantTo;
	}
	public String getPayGrpFrom() {
		return payGrpFrom;
	}
	public void setPayGrpFrom(String payGrpFrom) {
		this.payGrpFrom = payGrpFrom;
	}
	public String getPayGrpTo() {
		return payGrpTo;
	}
	public void setPayGrpTo(String payGrpTo) {
		this.payGrpTo = payGrpTo;
	}
	public String getCatFrom() {
		return catFrom;
	}
	public void setCatFrom(String catFrom) {
		this.catFrom = catFrom;
	}
	public String getCatTo() {
		return catTo;
	}
	public void setCatTo(String catTo) {
		this.catTo = catTo;
	}
	public ArrayList getWorkList() {
		return workList;
	}
	public void setWorkList(ArrayList workList) {
		this.workList = workList;
	}
	public ArrayList getWorkLabelList() {
		return workLabelList;
	}
	public void setWorkLabelList(ArrayList workLabelList) {
		this.workLabelList = workLabelList;
	}
	public String getWorkFrom() {
		return workFrom;
	}
	public void setWorkFrom(String workFrom) {
		this.workFrom = workFrom;
	}
	public String getWorkTo() {
		return workTo;
	}
	public void setWorkTo(String workTo) {
		this.workTo = workTo;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getRule_Code() {
		return rule_Code;
	}
	public void setRule_Code(String rule_Code) {
		this.rule_Code = rule_Code;
	}
	public String getSwipe_Count() {
		return swipe_Count;
	}
	public void setSwipe_Count(String swipe_Count) {
		this.swipe_Count = swipe_Count;
	}
	public String getDoj() {
		return doj;
	}
	public void setDoj(String doj) {
		this.doj = doj;
	}
	public String getActiveStatus() {
		return activeStatus;
	}
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	public ArrayList getShiftList() {
		return shiftList;
	}
	public void setShiftList(ArrayList shiftList) {
		this.shiftList = shiftList;
	}
	public ArrayList getShiftLabelList() {
		return shiftLabelList;
	}
	public void setShiftLabelList(ArrayList shiftLabelList) {
		this.shiftLabelList = shiftLabelList;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDeptFrom() {
		return deptFrom;
	}
	public void setDeptFrom(String deptFrom) {
		this.deptFrom = deptFrom;
	}
	public String getDeptTo() {
		return deptTo;
	}
	public void setDeptTo(String deptTo) {
		this.deptTo = deptTo;
	}
	public int getMonthFrom() {
		return monthFrom;
	}
	public void setMonthFrom(int monthFrom) {
		this.monthFrom = monthFrom;
	}
	public int getMonthTo() {
		return monthTo;
	}
	public void setMonthTo(int monthTo) {
		this.monthTo = monthTo;
	}
	public int getCalYear() {
		return calYear;
	}
	public void setCalYear(int calYear) {
		this.calYear = calYear;
	}
	public String getCurentDate() {
		return CurentDate;
	}
	public void setCurentDate(String curentDate) {
		CurentDate = curentDate;
	}
	public String getSwipe_Type() {
		return swipe_Type;
	}
	public void setSwipe_Type(String swipe_Type) {
		this.swipe_Type = swipe_Type;
	}
	public String getReason_Type() {
		return reason_Type;
	}
	public void setReason_Type(String reason_Type) {
		this.reason_Type = reason_Type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSwipetype() {
		return swipetype;
	}
	public void setSwipetype(String swipetype) {
		this.swipetype = swipetype;
	}
	public String getSwipereason() {
		return swipereason;
	}
	public void setSwipereason(String swipereason) {
		this.swipereason = swipereason;
	}
	public String getSwipetime() {
		return swipetime;
	}
	public void setSwipetime(String swipetime) {
		this.swipetime = swipetime;
	}
	public String getSwiperemarks() {
		return swiperemarks;
	}
	public void setSwiperemarks(String swiperemarks) {
		this.swiperemarks = swiperemarks;
	}
	public String getLatecount() {
		return latecount;
	}
	public void setLatecount(String latecount) {
		this.latecount = latecount;
	}
	public String getEmployeeno() {
		return employeeno;
	}
	public void setEmployeeno(String employeeno) {
		this.employeeno = employeeno;
	}
	public String getFrommonth() {
		return frommonth;
	}
	public void setFrommonth(String frommonth) {
		this.frommonth = frommonth;
	}
	public String getTomonth() {
		return tomonth;
	}
	public void setTomonth(String tomonth) {
		this.tomonth = tomonth;
	}
	public String getCalyear() {
		return calyear;
	}
	public void setCalyear(String calyear) {
		this.calyear = calyear;
	}
	public String getSummbrkup() {
		return summbrkup;
	}
	public void setSummbrkup(String summbrkup) {
		this.summbrkup = summbrkup;
	}
	public String getAttntype() {
		return attntype;
	}
	public void setAttntype(String attntype) {
		this.attntype = attntype;
	}
	public String getAttnstatus() {
		return attnstatus;
	}
	public void setAttnstatus(String attnstatus) {
		this.attnstatus = attnstatus;
	}
	public String getLeavetype() {
		return leavetype;
	}
	public void setLeavetype(String leavetype) {
		this.leavetype = leavetype;
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
	public String getFrompernr() {
		return frompernr;
	}
	public void setFrompernr(String frompernr) {
		this.frompernr = frompernr;
	}
	public String getTopernr() {
		return topernr;
	}
	public void setTopernr(String topernr) {
		this.topernr = topernr;
	}
	public String getPlant() {
		return plant;
	}
	public void setPlant(String plant) {
		this.plant = plant;
	}
	public String getPaygrp() {
		return paygrp;
	}
	public void setPaygrp(String paygrp) {
		this.paygrp = paygrp;
	}
	public String getStaffcat() {
		return staffcat;
	}
	public void setStaffcat(String staffcat) {
		this.staffcat = staffcat;
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
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOnDutyType() {
		return onDutyType;
	}
	public void setOnDutyType(String onDutyType) {
		this.onDutyType = onDutyType;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getApproverNumber() {
		return approverNumber;
	}
	public void setApproverNumber(String approverNumber) {
		this.approverNumber = approverNumber;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReasonType() {
		return reasonType;
	}
	public void setReasonType(String reasonType) {
		this.reasonType = reasonType;
	}
	public LinkedList getLeaveTypeID() {
		return leaveTypeID;
	}
	public void setLeaveTypeID(LinkedList leaveTypeID) {
		this.leaveTypeID = leaveTypeID;
	}
	public LinkedList getLeaveTypeName() {
		return leaveTypeName;
	}
	public void setLeaveTypeName(LinkedList leaveTypeName) {
		this.leaveTypeName = leaveTypeName;
	}
	public LinkedList getLeaveReason() {
		return leaveReason;
	}
	public void setLeaveReason(LinkedList leaveReason) {
		this.leaveReason = leaveReason;
	}
	public LinkedList getLeaveDetReason() {
		return leaveDetReason;
	}
	public void setLeaveDetReason(LinkedList leaveDetReason) {
		this.leaveDetReason = leaveDetReason;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public int getRequestNumber() {
		return requestNumber;
	}
	public void setRequestNumber(int requestNumber) {
		this.requestNumber = requestNumber;
	}
	public String getContentDescription() {
		return contentDescription;
	}
	public void setContentDescription(String contentDescription) {
		this.contentDescription = contentDescription;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getStartDurationType() {
		return startDurationType;
	}
	public void setStartDurationType(String startDurationType) {
		this.startDurationType = startDurationType;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate;
	}
	public String getEndDurationType() {
		return endDurationType;
	}
	public void setEndDurationType(String endDurationType) {
		this.endDurationType = endDurationType;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getDocumentName() {
		return documentName;
	}
	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public String getGeneratedBy() {
		return generatedBy;
	}
	public void setGeneratedBy(String generatedBy) {
		this.generatedBy = generatedBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getLeaveCheck() {
		return leaveCheck;
	}
	public void setLeaveCheck(String[] leaveCheck) {
		this.leaveCheck = leaveCheck;
	}
	public String[] getLeavelist() {
		return leavelist;
	}
	public void setLeavelist(String[] leavelist) {
		this.leavelist = leavelist;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFileNames() {
		return fileNames;
	}
	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
	public String getHolidayType() {
		return holidayType;
	}
	public void setHolidayType(String holidayType) {
		this.holidayType = holidayType;
	}
	public String getEmployeeNo() {
		return employeeNo;
	}
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
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
	public String getMessage3() {
		return message3;
	}
	public void setMessage3(String message3) {
		this.message3 = message3;
	}
	public String getReqRequstType() {
		return reqRequstType;
	}
	public void setReqRequstType(String reqRequstType) {
		this.reqRequstType = reqRequstType;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getReqMaterialGroup() {
		return reqMaterialGroup;
	}
	public void setReqMaterialGroup(String reqMaterialGroup) {
		this.reqMaterialGroup = reqMaterialGroup;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String[] getSelectedRequestNo() {
		return selectedRequestNo;
	}
	public void setSelectedRequestNo(String[] selectedRequestNo) {
		this.selectedRequestNo = selectedRequestNo;
	}
	public String getMessage2() {
		return message2;
	}
	public void setMessage2(String message2) {
		this.message2 = message2;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSelectedFilter() {
		return selectedFilter;
	}
	public void setSelectedFilter(String selectedFilter) {
		this.selectedFilter = selectedFilter;
	}
	public String getPrev_time() {
		return prev_time;
	}
	public void setPrev_time(String prev_time) {
		this.prev_time = prev_time;
	}


}
