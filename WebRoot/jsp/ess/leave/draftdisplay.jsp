<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript">
	function popupCalender(param)
	{
		var toD = new Date();
		if(param == "endDate"){
			var sD = document.forms[0].startDate.value;
			var sDate = sD.split("/");
			toD = new Date(parseInt(sDate[2]),parseInt(sDate[1]),parseInt(sDate[0]));
		}
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		});
	}
</script>


<title>Home Page</title>

<script language="javascript">

function clearAllFields(){
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";
}

function calculateDays()
{

var startDate=document.forms[0].startDate.value;
var startDateDuration=document.forms[0].startDurationType.value;
var endDate=document.forms[0].endDate.value;
var endDurationType=document.forms[0].endDurationType.value;
var leaveType=document.forms[0].leaveType.value;
 var casualBal=document.forms[0].casuallvcloseBal.value;
 var sickBal=document.forms[0].sicklvcloseBal.value;
 var casualMaxDays=document.forms[0].clMaxDays.value;
 var sickMaxDays=document.forms[0].slMaxDays.value;
 var year=document.forms[0].year.value;
 
 var clmindur=document.forms[0].clmindur.value;
var slmindur=document.forms[0].slmindur.value;
var lossmindur=document.forms[0].lossmindur.value;
var plmindur=document.forms[0].plmindur.value;
 if(leaveType=="")
   {
     alert("Leave Type should not be left blank");
     document.forms[0].leaveType.focus();
     document.forms[0].endDurationType.value="";
     
     return false;
   }
var lvType="";
if(leaveType==1)
    lvType="Casual ";
if(leaveType==2)
    lvType="Sick ";
if(leaveType==3)
    lvType="Privilege";
if(leaveType==5)
    lvType="MATERNITY";    
    
if(startDate=="")
   {
     alert("Please Select Start Date");
     document.forms[0].startDate.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
 if(startDateDuration=="")
   {
     alert("Please Select Start Date Duration");
     document.forms[0].startDateDuration.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
   if(endDate=="")
   {
     alert("Please Select End Date");
     document.forms[0].endDate.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
    if(startDate!=""&&endDate!=""){
   
     var str1 = document.forms[0].startDate.value;
var str2 = document.forms[0].endDate.value;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 

if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}
if(yr1!=year || yr2!=year)
{
  alert("From date Year and To date year should equal to calendar Year");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}

   }
   if(endDurationType=="")
   {
     alert("Please Select End Date Duration");
     document.forms[0].endDurationType.focus();
     return false;
   }
   
   if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
    if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
   
   if((startDateDuration=="FH"&&endDurationType=="FD"))
   {
   
   alert("Please Select Valid Duration");
   document.forms[0].startDurationType.focus();
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].startDurationType.value="";
      
     return false;
   }
   
   if((startDateDuration=="FH"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
   document.forms[0].startDurationType.focus();
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
  
   if(startDate==endDate && startDateDuration!=endDurationType)
   {
   alert("Please Select Valid Duration ");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
        document.forms[0].endDurationType.value="";
     return false;
   }
   if(document.forms[0].leaveType.value=="3")
   {
   
   var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
   
   if((startDateDuration=="SH" && endDurationType=="FD") ||(startDateDuration=="FD" && endDurationType=="SH")||(startDateDuration=="FD" && endDurationType=="FH"))
   {
   totalLeaveDays=totalLeaveDays-1;
   }
    var d=document.forms[0].preleavmin.value;
    if(sickBal!=0.0 && casualBal!=0.0)
	{
   if(totalLeaveDays<d){
    alert("Minimum "+d+" days should be availed for Previlage Leave");
    document.forms[0].totalLeaveDays.value="";
    document.forms[0].endDate.value="";
         return false;
    }
   
   }
   }
   if((startDateDuration=="FH"&&endDurationType=="FH")||(startDateDuration=="SH"&&endDurationType=="SH"))
   {
    if(startDate!=endDate){
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
        document.forms[0].endDurationType.value="";
     return false;
    }
   
   } 
    if(leaveType!="" && startDate!="" && startDateDuration!="" && endDate!="" && endDurationType!="" && leaveType!="5")
 {
var xmlhttp;



if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("noOfDaysDiv").innerHTML=xmlhttp.responseText;
    
    var totalDays=document.forms[0].totalLeaveDays.value;
    
    if(leaveType==1)
    {
	   if(casualMaxDays<totalDays){
	    var mess= "Maximum "+casualMaxDays+" days allow to apply Casual leave";
	    alert(mess);
	    document.forms[0].endDate.value="";
	   document.forms[0].totalLeaveDays.value="";
	    }
    }
     if(leaveType==2)
    {
	   if(parseFloat(sickMaxDays)<parseFloat(totalDays)){
	    var mess= "Maximum "+sickMaxDays+" days allow to apply Sick leave";
	    alert(mess);
	    document.forms[0].endDate.value="";
	   document.forms[0].totalLeaveDays.value="";
	    }
    }
    if(totalDays==0.0){
   alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }
     if(totalDays==-1.0){
    alert("You have applied Onduty");
    document.forms[0].endDate.value="";
    document.forms[0].totalLeaveDays.value="";
    }
    if(totalDays==-2.0){
    alert("You have applied Permission");
    document.forms[0].endDate.value="";
    document.forms[0].totalLeaveDays.value="";
    }
    if(totalDays==-3.0){
            var leavtype=document.forms[0].leaveType.value;
           
        if(leavtype==1)
        {
        var advdays=document.forms[0].casleavadv.value;
      
        var mess= " Minimum "+advdays+" days before is required to apply casual leave";
        alert(mess);
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
        }
        
         if(leavtype==3)
        {
        var advdays=document.forms[0].preleavadv.value;
        var mess= "Minimum "+advdays+" days before is required to apply privilege leave";
        alert(mess);
        document.forms[0].endDate.value="";
       document.forms[0].totalLeaveDays.value="";
        }
        
    }
    if(totalDays==-4.0){
            alert("Already you have applied  Leave On Selection Date.");
        document.forms[0].endDate.value="";
        document.forms[0].totalLeaveDays.value="";
      }
      if(totalDays==-6.0){
    var mess= "Selection date is holiday.Please Check the Dates";
        alert(mess);
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
    if(totalDays==-7.0){
     if(leaveType=="1")
    	var mess= "Between Two Casual Leaves "+clmindur+" days is required. \n Please Check last Applied Casual Leave.";
    if(leaveType=="2")
    	var mess= "Between Two Sick Leaves "+slmindur+" days is required. \n Please Check last Applied Sick Leave. ";
    if(leaveType=="3")
    	var mess= "Between Two Privilege Leaves "+plmindur+" days is required. \n Please Check last Applied Privilege Leave. ";
    if(leaveType=="4")
    	var mess= "Between Two Loss Of Pay Leaves "+lossmindur+" days is required. \n Please Check last Applied Loss Of Pay Leave. ";			
        alert(mess);
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";			
        
    }
    
     if(totalDays==-8.0){
    var mess= " You have exceeded Max 3 days allowed in First 6 months..You cannot apply this leave type until July";
        alert(mess); 
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
     if(totalDays==-9.0){
    var mess= "You have exceeded Max 10 Times allowed to apply this leave type for the Calendar Year";
        alert(mess); 
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
     if(totalDays==-10.0){
    var mess= "Sick Leave Cannot be Applied in Advance";
        alert(mess); 
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
       if(totalDays==-11.0){
    var mess= "Another Leave type cannot be applied in Combination";
        alert(mess); 
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
    
      if(totalDays==-12.0){
    var mess= "Minimum "+d+" days should be availed for Previlage Leave";
        alert(mess); 
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
    
     if(totalDays==-13.0){
 	    var mess= "Request Can be applied  only within 6 days after goin on Leave..Contact HR";
 	        alert(mess); 
 	        document.forms[0].endDate.value="";
 	      document.forms[0].totalLeaveDays.value="";
 	    }
 	    
 	       if(parseFloat(totalDays)==-14.0){
    	    var mess= "You are not authorised to apply leave for Selected Dates ..Contact HR";
    	        alert(mess); 
    	        document.forms[0].endDate.value="";
    	      document.forms[0].totalLeaveDays.value="";
    	    }
      /*
    if(totalDays==-5.0){
        alert("You have applied Casual Leave.\nPlease Choose Another Leave Type");
        document.forms[0].endDate.value="";
        document.forms[0].totalLeaveDays.value="";
      }
    */
    }
  }

xmlhttp.open("POST","leave.do?method=calculateDays&year="+year+"&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType,true);
xmlhttp.send();

}
if(leaveType=="5" )
{
	var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
	
	 if(totalLeaveDays<60){
     alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }else if(totalLeaveDays>90){
    alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }
    else{
	document.forms[0].totalLeaveDays.value=totalLeaveDays;
	}
}
}











function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}


function daydiff(first, second) {

//daydiff

var totaldays=(second-first)/(1000*60*60*24);

if(totaldays<0)
{

    return "";
}
else{

    return ((second-first)/(1000*60*60*24)+1)
    }
}


function uploadDocument()
{

var noOfDays=document.forms[0].totalLeaveDays.value;
 if(noOfDays=="0.0")
 {
 		alert("No Of Days Should not be Zero");
	      document.forms[0].documentFile.focus();
	      return false;
 }

  if(document.forms[0].documentFile.value=="")
	    {
	      alert("Please Select File.");
	      document.forms[0].documentFile.focus();
	      return false;
	    }
	document.forms[0].action="leave.do?method=uploadDocuments";
	document.forms[0].submit();
}


function deleteDocumentsSelected()
{
	document.forms[0].action="leave.do?method=deleteDocuments";
	document.forms[0].submit();
}


function displayTabs(param)
{
	document.forms[0].action="leave.do?method=displayTabs&param="+param;
	document.forms[0].submit();
}
function applyLeave(param)
{
var totalLeaveDays=document.forms[0].totalLeaveDays.value;
	var year=document.forms[0].year.value;
if(param=='Applied')
{
	var startDate=document.forms[0].startDate.value;
	var startDateDuration=document.forms[0].startDurationType.value;
	var endDate=document.forms[0].endDate.value;
	var endDurationType=document.forms[0].endDurationType.value;
	var leaveType=document.forms[0].leaveType.value;

	if(leaveType=="")
	{
	    alert("Leave Type should not be left blank");
	    document.forms[0].leaveType.focus();
	    return false;
	}
	else if(startDate=="")
	{
	  alert("Please Select Start Date");
	  document.forms[0].startDate.focus();
	  return false;
	}
 	else if(startDateDuration=="")
   {
     alert("Please Select Start Date Duration");
     document.forms[0].startDateDuration.focus();
     return false;
   }
   else if(endDate=="")
   {
     alert("Please Select End Date");
     document.forms[0].endDate.focus();
     return false;
   }
   else if(endDurationType=="")
   {
     alert("Please Select End Date Duration");
     document.forms[0].endDurationType.focus();
     return false;
   }
    else if(totalLeaveDays=="")
   {
     alert("No of days should not be blank");
     document.forms[0].totalLeaveDays.focus();
     return false;
   }
    else if(totalLeaveDays==0.0)
   {
     alert("No of days Can't be Zero.Please Check..");
     document.forms[0].totalLeaveDays.focus();
     return false;
   }
   
   else if(document.forms[0].reasonType.value=="")
   {
    alert("Please Select Reason Type");
     document.forms[0].reasonType.focus();
     return false;
   } 
   else if(document.forms[0].reason.value=="")
   {
    alert("Please Enter Detailed Reason");
     document.forms[0].reason.focus();
     return false;
   }else if(document.forms[0].reason.value!=""){
var st = document.forms[0].reason.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].reason.value=st;
   
   }    
       if(document.forms[0].reason.value.length>=300){
   
alert("Reason should  be less than 300 characters");
return false;
   } 
   
   
 	document.forms[0].action="leave.do?method=submitDraft&year="+year+"&param="+param+"&noOfDays="+totalLeaveDays;
	document.forms[0].submit();
}
else if(param=='Draft')
{
if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	    else  if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
else if((document.forms[0].leaveType.value!="")||(document.forms[0].startDurationType.value!="")||(document.forms[0].startDate.value!="")||(document.forms[0].endDate.value!=""))
{

	document.forms[0].action="leave.do?method=submit&param="+param+"&noOfDays="+totalLeaveDays;
	document.forms[0].submit();
}

}
}
function closeLeave()
{
	document.forms[0].action="leave.do?method=displayDrafts";
	document.forms[0].submit();
}
function displayRequests()
{
	document.forms[0].action="leave.do?method=displayRequests";
	document.forms[0].submit();
}
function submitRequest(param)
{
document.forms[0].action="leave.do?method=submitRequests&param="+param;
document.forms[0].submit();
}

function cancelRequest()
{
document.forms[0].action="leave.do?method=cancelRequest";
document.forms[0].submit();
}
function deleteDraft()
{
document.forms[0].action="leave.do?method=deleteDraft";
document.forms[0].submit();
}
function calculateEndDate()
{

document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));

var endDate=document.forms[0].noOfDays.value;
if(endDate=="")
{
document.forms[0].endDate.value="";
}
}


function clearEndDate()
{
document.forms[0].endDate.value="";
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";
}

function clearNoOfDays()
{
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";

}	
function checkTotalDays()
{
var totalDays=document.forms[0].totalLeaveDays.value;
if(totalDays==0.0){
alert("Invalid Selection.Please Check...");
}

}	
function generate(){

document.forms[0].action="payslip.do?method=generateIntoPDF";
	document.forms[0].submit();

}


function checkLeaveCal(){
var startDate=document.forms[0].startDate.value;
var startDateDuration=document.forms[0].startDurationType.value;
var endDate=document.forms[0].endDate.value;
var endDurationType=document.forms[0].endDurationType.value;
var leaveType=document.forms[0].leaveType.value;
 var casualBal=document.forms[0].casuallvcloseBal.value;
 var sickBal=document.forms[0].sicklvcloseBal.value;
 var casualMaxDays=document.forms[0].clMaxDays.value;
 var sickMaxDays=document.forms[0].slMaxDays.value;
 var year=document.forms[0].year.value;
 
 var applyafter=document.forms[0].applyAfterDate.value;
 
 var clmindur=document.forms[0].clmindur.value;
var slmindur=document.forms[0].slmindur.value;
var lossmindur=document.forms[0].lossmindur.value;
var plmindur=document.forms[0].plmindur.value;
var lvType="";
if(leaveType==1)
    lvType="Casual ";
if(leaveType==2)
    lvType="Sick ";
if(leaveType==3)
    lvType="Privilege";
if(leaveType==5)
    lvType="MATERNITY"; 
 
if(startDate!=""){
var newyear=new Date().getFullYear();
var stDate=document.forms[0].startDate.value;
var stYear=stDate.split("/");
stYear=stYear[2];


var sdate=stDate.split("/");
sdate=sdate[2]+"-"+sdate[1]+"-"+sdate[0];


	if(stYear>newyear){
	alert("Next year Leaves cannot be applied in Advance");
	document.forms[0].startDate.value="";
	return false;
	}
	
	
	if(sdate<applyafter){
		alert("Start date should be greater than or equal to "+applyafter);
		document.forms[0].startDate.value="";
		return false;
		}
}
if(endDate!=""){
var newyear=new Date().getFullYear();
var endDat=document.forms[0].endDate.value;
var endYear=endDat.split("/");
endYear=endYear[2];


	if(endYear>newyear){
	alert("Next year Leaves cannot be applied in Advance");
	document.forms[0].endDate.value="";
	return false;
	
}
}
 if(startDate!=""&&endDate!=""){
    var str1 = document.forms[0].startDate.value;
var str2 = document.forms[0].endDate.value;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 

if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}

if(yr1!=year || yr2!=year)
{
  alert("From date Year and To date year should equal to calendar Year");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}
   
   }
   
   
   
   

if(leaveType!="" && startDate!="" && startDateDuration!="" && endDate!="" && endDurationType!=""){
 if((startDateDuration=="FH"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
   document.forms[0].startDurationType.focus();
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }

if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
    if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
   
   if((startDateDuration=="FH"&&endDurationType=="FD"))
   {
   
   alert("Please Select Valid Duration");
   	document.forms[0].startDurationType.focus();
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
      
     return false;
   }
  
   if(startDate==endDate && startDateDuration!=endDurationType)
   {
   alert("Please Select Valid Duration ");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
      document.forms[0].endDurationType.value="";
     return false;
   }
   
 
   if(startDate==endDate && startDateDuration!=endDurationType)
   {
   alert("Invalid Selection Dates");
     document.forms[0].endDurationType.focus();
       document.forms[0].endDurationType.value="";
      document.forms[0].totalLeaveDays.value="";
     return false;
   }
   if((startDateDuration=="FH"&&endDurationType=="FH")||(startDateDuration=="SH"&&endDurationType=="SH"))
   {
    if(startDate!=endDate){
   alert("Invalid Selection Duration");
     document.forms[0].endDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].endDurationType.value="";
     return false;
    }
   
   }
    if(document.forms[0].leaveType.value=="3")
   {
   
   var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
     var d=document.forms[0].preleavmin.value;
   if((startDateDuration=="SH" && endDurationType=="FD") ||(startDateDuration=="FD" && endDurationType=="SH")||(startDateDuration=="FD" && endDurationType=="FH"))
   {
   totalLeaveDays=totalLeaveDays-1;
   }
   if(sickBal!=0.0 && casualBal!=0.0)
{
   if(totalLeaveDays<d){
 alert("Minimum "+d+" days should be availed for Previlage Leave");
       document.forms[0].endDate.value="";
         return false;
    }
    }
   
   }  
 if(leaveType!="" && startDate!="" && startDateDuration!="" && endDate!="" && endDurationType!="" && leaveType!="5")
 {
var xmlhttp;



if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("noOfDaysDiv").innerHTML=xmlhttp.responseText;
    
    var totalDays=document.forms[0].totalLeaveDays.value;
    if(leaveType==1)
    {
   if(casualMaxDays<totalDays){
    var mess= "Maximum "+casualMaxDays+" days allow to apply Casual leave";
    alert(mess);
    document.forms[0].endDate.value="";
   document.forms[0].totalLeaveDays.value="";
    }
    }
     if(leaveType==2)
    {
	    if(parseFloat(sickMaxDays)<parseFloat(totalDays)){
	    var mess= "Maximum "+sickMaxDays+" days allow to apply Sick leave";
	    alert(mess);
	    document.forms[0].endDate.value="";
	   document.forms[0].totalLeaveDays.value="";
	    }
    }
    if(totalDays==0.0){
   alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }
     if(totalDays==-1.0){
    alert("You have applied Onduty");
    document.forms[0].endDate.value="";
    document.forms[0].totalLeaveDays.value="";
    }
    if(totalDays==-2.0){
    alert("You have applied Permission");
    document.forms[0].endDate.value="";
    document.forms[0].totalLeaveDays.value="";
    }
    if(totalDays==-3.0){
            var leavtype=document.forms[0].leaveType.value;
           
        if(leavtype==1)
        {
        var advdays=document.forms[0].casleavadv.value;
      
        var mess= " Minimum "+advdays+" days before is required to apply casual leave";
        alert(mess);
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
        }
        
         if(leavtype==3)
        {
        var advdays=document.forms[0].preleavadv.value;
        var mess= "Minimum "+advdays+" days before is required to apply privilege leave";
        alert(mess);
        document.forms[0].endDate.value="";
       document.forms[0].totalLeaveDays.value="";
        }
        
    }
    if(totalDays==-4.0){
        alert("Already you have applied Leave On Selection Date.");
        document.forms[0].endDate.value="";
        document.forms[0].totalLeaveDays.value="";
      }
      if(totalDays==-6.0){
    var mess= "Selection date is holiday.Please Check the Dates";
        alert(mess);
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";
    }
     if(totalDays==-7.0){
     if(leaveType=="1")
    	var mess= "Between Two Casual Leaves "+clmindur+" days is required. \n Please Check last Applied Casual Leave.";
    if(leaveType=="2")
    	var mess= "Between Two Sick Leaves "+slmindur+" days is required. \n Please Check last Applied Sick Leave. ";
    if(leaveType=="3")
    	var mess= "Between Two Privilege Leaves "+plmindur+" days is required. \n Please Check last Applied Privilege Leave. ";
    if(leaveType=="4")
    	var mess= "Between Two Loss Of Pay Leaves "+lossmindur+" days is required. \n Please Check last Applied Loss Of Pay Leave. ";			
        alert(mess);
        document.forms[0].endDate.value="";
      document.forms[0].totalLeaveDays.value="";			
    }
      /*
    if(totalDays==-5.0){
        alert("You have applied Casual Leave.\nPlease Choose Another Leave Type");
        document.forms[0].endDate.value="";
        document.forms[0].totalLeaveDays.value="";
      }
      */
    }
  }

xmlhttp.open("POST","leave.do?method=calculateDays&year="+year+"&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType,true);
xmlhttp.send();

}
if(leaveType=="5" )
{
	var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
	
	 if(totalLeaveDays<60){
     alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }else if(totalLeaveDays>90){
   alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }
    else{
	document.forms[0].totalLeaveDays.value=totalLeaveDays;
	}
}

}

}

function statusMessage(message){
alert(message);
}

function uploadDocumentModify()
{

	if(document.forms[0].documentFile.value==""){
	 alert("Please Choose One File..");
	 document.forms[0].documentFile.focus();
	 return false;
	}
	document.forms[0].action="leave.do?method=uploadDocumentsModify";
	document.forms[0].submit();
}
function deleteDocumentsSelectedModify()
{
var rows=document.getElementsByName("checkedfiles");
var checkvalues='';
var uncheckvalues='';
for(var i=0;i<rows.length;i++)
{
if (rows[i].checked)
{
checkvalues+=rows[i].value+',';
}else{
uncheckvalues+=rows[i].value+',';
}
}
if(checkvalues=='')
{
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{
	document.forms[0].action="leave.do?method=deleteDocumentsModify&cValues="+checkvalues+"&unValues="+uncheckvalues;
document.forms[0].submit();
}
}
}


function dynamicleavbal()
{
var year=document.forms[0].year.value;
var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){

        	document.getElementById("bal").innerHTML=xmlhttp.responseText;
        	       
        	       			
        }
    }

     xmlhttp.open("POST","leave.do?method=displayLeaveBal&year="+year,true);
    xmlhttp.send();

}

function compffbal()
{



var startDate=document.forms[0].startDate.value;

var leaveType=document.forms[0].leaveType.value;

if(leaveType!="6")
{
return false;
}

var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){

        	document.getElementById("bal").innerHTML=xmlhttp.responseText;
        	       
        	       			
        }
    }

     xmlhttp.open("POST","leave.do?method=displaycompbalance&LeaveType="+leaveType+"&StartDate="+startDate,true);
    xmlhttp.send();

}
</script>
</head>

<body >


				<div align="center">
				<logic:notEmpty name="leaveForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="leaveForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="leaveForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="leaveForm" property="message" />');
					</script>
				</logic:notEmpty>
			</div>
		<html:form action="leave" enctype="multipart/form-data">	
<html:hidden name="leaveForm" property="sicklvcloseBal"/>	 
	<html:hidden name="leaveForm" property="casuallvcloseBal"/>	 	
				
	<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" id="bal">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<big><b><bean:write name="leaveForm" property="year"/></b></big></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
			</tr>
		
			<logic:iterate id="bal" name="LeaveBalenceList">
						<tr>
						<th><bean:write name="bal" property="leaveType"/></th>
						<td><bean:write name="bal" property="openingBalence"/></td>
						<td><bean:write name="bal" property="avalableBalence"/></td>
						<td><bean:write name="bal" property="closingBalence"/></td>
						<td><bean:write name="bal" property="awaitingBalence"/></td>
						</tr>	
			</logic:iterate>	
			
		</table>
		</div>
		</logic:notEmpty>			
				
			

	
				
				<logic:notEmpty name="submitDetails">
				
					
							<html:hidden property="id"/>
							
						
							
						
					
					<br/>
				
			<table class="bordered content" width="90%" >
				<tr>
					<th style="text-align: center;" colspan="6"><big>Leave Apply Form</big></th>
				</tr>
  							<tr>
  					<td width="15%">Calendar Year <font color="red" size="4">*</font></td>
  							<td width="1%"> : </td>
							<td width="64%" align="left" colspan="" style="width: 293px; ">
								<html:select name="leaveForm" property="year" styleClass="content" onchange="dynamicleavbal(),checkLeaveCal()">
							<html:options name="leaveForm"  property="yearList"/>
								</html:select>
								<html:hidden property="requestNumber"/>
								
							</td>
  							<td width="15%">Leave Type <font color="red" size="4">*</font></td>
  							<td width="1%"> : </td>
							<td width="64%" align="left" colspan="2">
								<html:select name="leaveForm" property="leaveType" styleClass="content" onchange="checkLeaveCal(),res()">
									<html:option value="">--Select--</html:option>
										<html:options name="leaveForm" property="leaveTypeID" labelProperty="leaveTypeName"/>
								</html:select>
								<html:hidden property="requestNumber"/>
								
							</td>
						</tr>
						<tr>
							<td width="15%">From  Date <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td align="left" width="34%">
								<html:text property="startDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true" onchange="checkLeaveCal(),compffbal()"/>
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
								<html:select name="leaveForm" property="startDurationType" styleClass="content" onchange="checkLeaveCal()">
									<html:option value="">--Select--</html:option>
									<html:option value="FD">Full Day</html:option>
									<html:option value="FH">First Half</html:option>
									<html:option value="SH">Second Half</html:option>
								</html:select>
							</td>
						</tr>
						<tr>
							<td width="15%">To Date <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td align="left" width="34%">
								<html:text property="endDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field" 
										readonly="true" onchange="checkLeaveCal()"/>
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
								<html:select name="leaveForm" property="endDurationType" styleClass="content" onchange="calculateDays()">
									<html:option value="">--Select--</html:option>
									<html:option value="FD">Full Day</html:option>
									<html:option value="FH">First Half</html:option>
									<html:option value="SH">Second Half</html:option>
								</html:select>
							</td>
						</tr>
						
						<tr>
							<td width="15%">No of Days <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							
							<td align="left"  width="30%">
							<div id="noOfDaysDiv">
								<html:text property="totalLeaveDays" readonly="true"  styleClass="text_field" style="border:0;"/>
							</div>
							</td>
							
						
							<td >Reason Type <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
							<div id="resid" align="left">
			<html:select name="leaveForm" property="reasonType" styleClass="content" >
									<html:option value="">--Select--</html:option>
									<html:options name="leaveForm" property="leaveReason" labelProperty="leaveDetReason"/>
								</html:select>
			</div>
							</td>
						</tr>	
							
						<tr><th colspan="6"><big>Detailed Reason <font color="red" size="3">*</font></big> Max(300 char)</th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="5"></html:textarea>
						
							</td>
						</tr>
					

				<logic:notEmpty name="documentDetails">
						<tr >
							<th colspan="6">Uploaded Documents 
							</th>
						</tr>
						
						<logic:iterate name="documentDetails" id="abc" >
									<bean:define id="file" name="abc"
										property="documentName" />
								    <%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
									%>
									<tr>
									<td><a href="/EMicro Files/ESS/Leave/<%=v[i]%>" target="_blank"><%=v[i]%></a></td>
									
									<td colspan="5"><input type="checkbox" name="checkedfiles"
												value="<%=v[i]%>" /></td>
									</tr>
									<%
									}
									%>		
									</logic:iterate>
						<TR>
							<td align="center" colspan="4">
								<input type="button" value="Delete" onclick="deleteDocumentsSelectedModify()"/>
							</td>
						</TR>
	
				</logic:notEmpty>
			      <tr>
		                   <th colspan="6"><big>Attachments</big></th>
		                   </tr>
		                   <tr>
		                   <td colspan="6"> 
                     <html:file property="documentFile" />
                    
                     <html:button property="method" styleClass="rounded" value="Upload" onclick="uploadDocumentModify();" style="align:right;"/>
                    </td>
                </tr>
						<tr>
						<td colspan="6" align="center">
						<html:button property="method" styleClass="rounded" value="Submit" onclick="applyLeave('Applied');"/>
						
						
						
						<html:button property="method" styleClass="rounded" value="Close" onclick="closeLeave()"/>
						</td>
						</tr>
					</table>
					
					</logic:notEmpty>
					
					
					
					
			</table>
			
			<br/>
					<logic:notEmpty name="appList">
					 <table  width="100%"  class="bordered">
						<tr>
						<th colspan="4"><big>Approver Details</big></th></tr>
						<tr>
							<th style="text-align:left;"><b>Type</b></th>
							<th style="text-align:left;"><b>EmpNo</b></th>
							<th style="text-align:left;"><b>Emp Name</b></th>	
							<th style="text-align:left;"><b>Designation</b></th>
						</tr>
						<logic:iterate id="abc" name="appList">
						<tr>
						
						<td>${abc.appType}</td>
						<td>${abc.approverID}</td>
						<td>${abc.approverName}</td>
						<td>${abc.appDesig}</td>
						</tr>
						</logic:iterate>
						</table>
					</logic:notEmpty>
					
					<br/>
	<html:hidden name="leaveForm" property="casleavadv"/>
	<html:hidden name="leaveForm" property="preleavadv"/>	 
	<html:hidden name="leaveForm" property="preleavmin"/>	 
	<html:hidden property="clMaxDays" name="leaveForm"  />
	<html:hidden property="slMaxDays" name="leaveForm"  />
			    <html:hidden name="leaveForm" property="applyAfterDate"/>
	<html:hidden property="clmindur" name="leaveForm"  />
	<html:hidden property="slmindur" name="leaveForm"  />
	<html:hidden property="lossmindur" name="leaveForm"  />
	<html:hidden property="plmindur" name="leaveForm"  />
            </html:form>
            <div style="clear:both"></div>
            



</body>
</html>
