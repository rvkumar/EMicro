<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>

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
<link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<!--
/////////////////////////////////////////////////
-->
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

function res()
{

var leaveType=document.forms[0].leaveType.value;

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
    document.getElementById("resid").innerHTML=xmlhttp.responseText;
    }
  }

xmlhttp.open("POST","hrLeave.do?method=leavereason&LeaveType="+leaveType,true);
xmlhttp.send();
}

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
var casualMaxDays=document.forms[0].clMaxDays.value;
var sickMaxDays=document.forms[0].slMaxDays.value;
var year=document.forms[0].year.value;

var clmindur=document.forms[0].clmindur.value;
var slmindur=document.forms[0].slmindur.value;
var lossmindur=document.forms[0].lossmindur.value;
var plmindur=document.forms[0].plmindur.value;

var applyafter=document.forms[0].applyAfterDate.value;

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
 
 var casualBal=document.forms[0].casuallvcloseBal.value;
 var sickBal=document.forms[0].sicklvcloseBal.value;
 
if(sickBal!=0.0 && casualBal!=0.0)
{

  /*  if(parseFloat(totalLeaveDays)<d&&totalLeaveDays!=-11.0){
   
     alert("Minimum "+d+" days should be availed for Previlage Leave");
    document.forms[0].totalLeaveDays.value="";
    document.forms[0].endDate.value="";
         return false;
    } */
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
    var totalDays="";
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
    
   totalDays =document.forms[0].totalLeaveDays.value;
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
    
     var d=document.forms[0].preleavmin.value;
var casualBal=document.forms[0].casuallvcloseBal.value;
 var sickBal=document.forms[0].sicklvcloseBal.value;
    
      if(leaveType==3)
    {
	    if(sickBal!=0.0 && casualBal!=0.0)
{

   /* if(parseFloat(totalDays)<d&&totalDays!=-11.0){
     alert("Minimum "+d+" days should be availed for Previlage Leave");
       document.forms[0].endDate.value="";
       document.forms[0].totalLeaveDays.value="";
         return false;
    } */
   
   }
    }
    
    if(totalDays==0.0){
      alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
     document.forms[0].endDate.value="";
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
    if(totalDays==-4.0){
       // alert("You have applied Sick Leave.\nPlease Choose Another Leave Type");
       alert("Already you have applied Leave On Selection Date.");
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
      if(totalDays==-3.0){
            var leavtype=document.forms[0].leaveType.value;
        //casual leave condition   
        if(false)
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
    if(totalDays==-6.0){
    var mess= " Selection date is holiday.Please Check the Dates ";
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
    	    var mess= "Leave cannot be applied";
    	        alert(mess); 
    	        document.forms[0].endDate.value="";
    	      document.forms[0].totalLeaveDays.value="";
    	    }
    
    }
  }
var empNo=document.forms[0].empNumber.value;
xmlhttp.open("POST","hrLeave.do?method=calculateDays&year="+year+"&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType+"&empNo="+empNo,true);
xmlhttp.send();

}
if(leaveType=="5")
{
	var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
	
	 if(totalLeaveDays<60){
     alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }else if(totalLeaveDays>182){
    alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }
    else{
	document.forms[0].totalLeaveDays.value=totalLeaveDays;
	}
}

if(leaveType=="3")
{

if(parseFloat(totalDays)<d&&totalDays!=-11.0){
     alert("Minimum "+d+" days should be availed for Previlage Leave");
       document.forms[0].endDate.value="";
       document.forms[0].totalLeaveDays.value="";
         return false;
    }

}

}



function attendance()
{

var startDate=document.forms[0].startDate.value;
var startDateDuration=document.forms[0].startDurationType.value;
var endDate=document.forms[0].endDate.value;
var endDurationType=document.forms[0].endDurationType.value;
var leaveType=document.forms[0].leaveType.value;

var year=document.forms[0].year.value;



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
    document.getElementById("attproces").innerHTML=xmlhttp.responseText;
    
 
 
      
    
    }
  }
var empNo=document.forms[0].empNumber.value;
xmlhttp.open("POST","hrLeave.do?method=displayattendance&year="+year+"&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType+"&empNo="+empNo,true);
xmlhttp.send();

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
	      
	      return false;
 }
 
 
  if(document.forms[0].documentFile.value=="")
	    {
	      alert("Please Select File.");
	      document.forms[0].documentFile.focus();
	      return false;
	    }
	document.forms[0].action="hrLeave.do?method=uploadDocuments";
	document.forms[0].submit();
}


function deleteDocumentsSelected()
{


var rows=document.getElementsByName("documentCheck");
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

	document.forms[0].action="hrLeave.do?method=deleteDocuments";
	document.forms[0].submit();
}
}
}

function displayTabs(param)
{
	document.forms[0].action="hrLeave.do?method=displayTabs&param="+param;
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
	 if(startDate=="")
	{
	  alert("Please Select Start Date");
	  document.forms[0].startDate.focus();
	  return false;
	}
 	 if(startDateDuration=="")
   {
     alert("Please Select Start Date Duration");
     document.forms[0].startDateDuration.focus();
     return false;
   }
    if(endDate=="")
   {
     alert("Please Select End Date");
     document.forms[0].endDate.focus();
     return false;
   }
    if(endDurationType=="")
   {
     alert("Please Select End Date Duration");
     document.forms[0].endDurationType.focus();
     return false;
   }
     if(totalLeaveDays=="")
   {
     alert("No of days should not be blank");
     document.forms[0].totalLeaveDays.focus();
     return false;
   }
     if(totalLeaveDays<=0.0)
   {
     alert("No of days Can't be Zero or Negative.Please Check..");
     document.forms[0].totalLeaveDays.focus();
     return false;
   }
   
    if(document.forms[0].reasonType.value=="")
   {
    alert("Please Select Reason Type");
     document.forms[0].reasonType.focus();
     return false;
   } 
    if(document.forms[0].reason.value=="")
   {
    alert("Please Enter Detailed Reason");
     document.forms[0].reason.focus();
     return false;
   }
    if(document.forms[0].reason.value!=""){
   
var st = document.forms[0].reason.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].reason.value=st;  
   }    
 
       if(document.forms[0].reason.value.length>=300){
   
alert("Reason should  be less than 300 characters");
return false;
   } 
   
   
 document.forms[0].action="hrLeave.do?method=submit&year="+year+"&param="+param+"&noOfDays="+totalLeaveDays;
document.forms[0].submit();
}
 if(param=='Draft')
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
	     else if(totalLeaveDays==0.0)
	   {
	     alert("No of days Can't be Zero.");
	     document.forms[0].totalLeaveDays.focus();
	     return false;
	   }
else if((document.forms[0].leaveType.value!="")||(document.forms[0].startDurationType.value!="")||(document.forms[0].startDate.value!="")||(document.forms[0].endDate.value!=""))
{

	document.forms[0].action="hrLeave.do?method=submit&year="+year+"&param="+param+"&noOfDays="+totalLeaveDays;
	document.forms[0].submit();
}

}
}
function closeLeave()
{
	document.forms[0].action="hrLeave.do?method=search";
	document.forms[0].submit();
}
function displayRequests()
{
	document.forms[0].action="hrLeave.do?method=displayRequests";
	document.forms[0].submit();
}
function submitRequest(param)
{
document.forms[0].action="hrLeave.do?method=submitRequests&param="+param;
document.forms[0].submit();
}

function cancelRequest()
{
document.forms[0].action="hrLeave.do?method=cancelRequest";
document.forms[0].submit();
}
function deleteDraft()
{
document.forms[0].action="hrLeave.do?method=deleteDraft";
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
var casualMaxDays=document.forms[0].clMaxDays.value;
var sickMaxDays=document.forms[0].slMaxDays.value;
var clmindur=document.forms[0].clmindur.value;
var slmindur=document.forms[0].slmindur.value;
var lossmindur=document.forms[0].lossmindur.value;
var plmindur=document.forms[0].plmindur.value;
var year=document.forms[0].year.value;

var applyafter=document.forms[0].applyAfterDate.value;

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
mon1=mon1-1;
mon2=mon2-1;
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
     
      document.forms[0].totalLeaveDays.value="";
       document.forms[0].startDurationType.value="";
       document.forms[0].startDurationType.focus();
      
     return false;
   }

if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].startDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
    document.forms[0].startDurationType.value="";
     return false;
   }
    if((startDateDuration=="FD"&&endDurationType=="SH"))
   {
   
   alert("Please Select Valid Duration");
     document.forms[0].startDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
          document.forms[0].startDurationType.value="";
      
     return false;
   }
   
   if((startDateDuration=="FH"&&endDurationType=="FD"))
   {
   
   alert("Please Select Valid Duration");
   document.forms[0].startDurationType.value="";
   	document.forms[0].startDurationType.focus();
   
      document.forms[0].totalLeaveDays.value="";
       
      
     return false;
   }
  
   if(startDate==endDate && startDateDuration!=endDurationType)
   {
   alert("Please Select Valid Duration ");
      document.forms[0].totalLeaveDays.value="";
    document.forms[0].startDurationType.value="";
   	document.forms[0].startDurationType.focus();
     return false;
   }
   
 
   if(startDate==endDate && startDateDuration!=endDurationType)
   {
   alert("Invalid Selection Dates");
       document.forms[0].startDurationType.value="";
   	document.forms[0].startDurationType.focus();
      document.forms[0].totalLeaveDays.value="";
     return false;
   }
   if((startDateDuration=="FH"&&endDurationType=="FH")||(startDateDuration=="SH"&&endDurationType=="SH"))
   {
    if(startDate!=endDate){
   alert("Invalid Selection Duration");
      document.forms[0].totalLeaveDays.value="";
        document.forms[0].startDurationType.value="";
   	document.forms[0].startDurationType.focus();
     return false;
    }
   
   }
    if(document.forms[0].leaveType.value=="3")
   {
   var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
      var d=document.forms[0].preleavmin.value;
      var casualBal=document.forms[0].casuallvcloseBal.value;
	 var sickBal=document.forms[0].sicklvcloseBal.value;
 
	if(sickBal!=0.0 && casualBal!=0.0)
	{
	  /*  if(parseFloat(totalLeaveDays)<d)
	   {
	   
	    alert("Minimum "+d+" days should be availed for Previlage Leave");
	       document.forms[0].endDate.value="";
	        document.forms[0].totalLeaveDays.value="";
	         return false;
	   } */
   } 

   if((startDateDuration=="SH" && endDurationType=="FD") ||(startDateDuration=="FD" && endDurationType=="SH")||(startDateDuration=="FD" && endDurationType=="FH"))
   {
   totalLeaveDays=totalLeaveDays-1;
   }
   var d=document.forms[0].preleavmin.value;
var casualBal=document.forms[0].casuallvcloseBal.value;
 var sickBal=document.forms[0].sicklvcloseBal.value;

 
if(sickBal!=0.0 && casualBal!=0.0)
{
  /*  if(parseFloat(totalLeaveDays)<d){
     alert("Minimum "+d+" days should be availed for Previlage Leave");
       document.forms[0].endDate.value="";
        document.forms[0].totalLeaveDays.value="";
         return false;
    } */
   
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
  var totalDays="";
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("noOfDaysDiv").innerHTML=xmlhttp.responseText;
     document.getElementById("attproces").style.display="";
    
     totalDays=document.forms[0].totalLeaveDays.value;
    
    
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
    var d=document.forms[0].preleavmin.value;
var casualBal=document.forms[0].casuallvcloseBal.value;
 var sickBal=document.forms[0].sicklvcloseBal.value;
    
      if(leaveType==3)
    {
	    if(sickBal!=0.0 && casualBal!=0.0)
{
  /*  if(parseFloat(totalDays)<d){
     alert("Minimum "+d+" days should be availed for Previlage Leave");
       document.forms[0].endDate.value="";
       document.forms[0].totalLeaveDays.value="";
         return false;
    } */
   
   }
    }
   
    if(totalDays==0.0){
    alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].endDate.value="";
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
    if(totalDays==-4.0){
        alert("Already you have applied Leave On Selection Date.");
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
    
          if(totalDays==-3.0){
            var leavtype=document.forms[0].leaveType.value;
          //casual leave condition 
        if(false)
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
    var mess= "You have exceeded Max 5 Times allowed to apply this leave type for the Calendar Year";
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
 	    var mess= "Leave cannot be applied";
 	        alert(mess); 
 	        document.forms[0].endDate.value="";
 	      document.forms[0].totalLeaveDays.value="";
 	    }
          
    }
  };
  
  var empNo=document.forms[0].empNumber.value;

xmlhttp.open("POST","hrLeave.do?method=calculateDays&year="+year+"&LeaveType="+leaveType+"&StartDate="+startDate+"&StartDur="+startDateDuration+"&EndDate="+endDate+"&EndDur="+endDurationType+"&empNo="+empNo,true);
xmlhttp.send();

}

if(leaveType=="3")
{



if(parseFloat(totalDays)<d&&totalDays!=-11.0){
     alert("Minimum "+d+" days should be availed for Previlage Leave");
       document.forms[0].endDate.value="";
       document.forms[0].totalLeaveDays.value="";
       return false;
    }

}


if(leaveType=="5" )
{
	var totalLeaveDays=daydiff(parseDate(startDate), parseDate(endDate));
	
	 if(totalLeaveDays<60){
     alert("Insufficient " +lvType+ " Leave Balance.\nPlease Choose Another Leave Type");
    document.forms[0].totalLeaveDays.value="";
    }else if(totalLeaveDays>182){
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
    var emp=document.forms[0].empNumber.value;
     xmlhttp.open("POST","hrLeave.do?method=displaycompbalance&LeaveType="+leaveType+"&StartDate="+startDate+"&empNo="+emp,true);
    xmlhttp.send();

}

function compofflist()
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

        	document.getElementById("compfflist").innerHTML=xmlhttp.responseText;
        	       
        	       			
        }
    }
    var emp=document.forms[0].empNumber.value;
     xmlhttp.open("POST","hrLeave.do?method=displaycompofflist&LeaveType="+leaveType+"&StartDate="+startDate+"&empNo="+emp,true);
    xmlhttp.send();

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

    var emp=document.forms[0].empNumber.value;
     xmlhttp.open("POST","hrLeave.do?method=displayLeaveBal&year="+year+"&empNo="+emp,true);
    xmlhttp.send();

}



</script>
</head>

<body>

				
	<%@taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%><html:form action="hrLeave" enctype="multipart/form-data">
	
			<div align="center">
				<logic:notEmpty name="hrLeaveForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrLeaveForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="hrLeaveForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrLeaveForm" property="message2" />');
					</script>
				</logic:notEmpty>
			</div>
	
		<logic:notEmpty name="submitDetails">
		
		<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" id="bal">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<big><b><bean:write name="hrLeaveForm" property="year"/></b></big></th>
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
		
		
		<br/>
		
			
 <div  id="attproces" >
	    
	    	</div>




		<br/>
		
		<br/>
		<div id="compfflist"></div>
				
			<table class="bordered" width="90%" >
				<tr>
					<th style="text-align: center;" colspan="6">Apply Leave</th>
				</tr>
				<tr>
				<td width="15%">Employee Number <font color="red" size="4">*</font></td>
  							<td width="1%"> : </td>
							<td width="64%" align="left" colspan="4">
							<html:hidden property="empNumber" name="hrLeaveForm"/>
								<bean:write name="hrLeaveForm" property="empName"/>
								
							</td></tr>
  					<tr>
  					<td width="15%">Calendar Year <font color="red" size="4">*</font></td>
  							<td width="1%"> : </td>
							<td width="64%" align="left" colspan="" style="width: 293px; ">
								<html:select name="hrLeaveForm" property="year" styleClass="content" onchange="dynamicleavbal(),checkLeaveCal()">
								<html:options name="hrLeaveForm"  property="yearList"/>
								</html:select>
								<html:hidden property="requestNumber"/>
								
							</td>
  							<td width="15%">Leave Type <font color="red" size="4">*</font></td>
  							<td width="1%"> : </td>
							<td width="64%" align="left">
								<html:select name="hrLeaveForm" property="leaveType" styleClass="content" onchange="checkLeaveCal(),res()">
									<html:option value="">--Select--</html:option>
										<html:options name="hrLeaveForm" property="leaveTypeID" labelProperty="leaveTypeName"/>
								</html:select>
								<html:hidden property="requestNumber"/>
								
							</td>
						</tr>
						<tr>
							<td width="15%">From  Date <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td align="left" width="34%">
								<html:text property="startDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true" onchange="checkLeaveCal(),compffbal(),compofflist()"/>
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
								<html:select name="hrLeaveForm" property="startDurationType" styleClass="content" onchange="checkLeaveCal()">
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
										readonly="true" onchange="checkLeaveCal(),attendance()"/>
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="1%"> : </td>
							<td width="34%">
								<html:select name="hrLeaveForm" property="endDurationType" styleClass="content" onchange="calculateDays(),attendance()">
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
			<html:select name="hrLeaveForm" property="reasonType" styleClass="content" >
									<html:option value="">--Select--</html:option>
									<html:options name="hrLeaveForm" property="leaveReason" labelProperty="leaveDetReason"/>
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
					<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="5">
								<a href="/EMicro Files/ESS/Leave/${abc.fileName }" target="_blank"><bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
					</logic:iterate>
							<td align="center" colspan="6">
								<input type="button"  styleClass="rounded" style="width:100px;" value="Delete" onclick="deleteDocumentsSelected()"/>
							</td>
							
				</logic:notEmpty>
						
					    <tr>
		                   <th colspan="6"><big>Attachments</big></th>
		                   </tr>
		                   <tr>
		                   <td colspan="6"> 
		                     	<html:file property="documentFile" />
			                     <html:button property="method" styleClass="rounded" value="Upload" onclick="uploadDocument();" style="width:100px;"/>
		                    </td>
		                </tr>

						<tr>
							<td colspan="6">
								<html:button property="method" styleClass="rounded" style="width: 100px" value="Apply" onclick="applyLeave('Applied');" />&nbsp;&nbsp;
								<html:button property="method" styleClass="rounded" style="width: 100px" value="Save As Draft" onclick="applyLeave('Draft');"/>&nbsp;&nbsp;
								<html:reset styleClass="rounded" style="width: 100px" value="Clear" />&nbsp;&nbsp;
								<html:button property="method" styleClass="rounded" style="width: 100px" value="Close" onclick="closeLeave()"/>&nbsp;&nbsp;
							</td>
						</tr>
					</table>
					<br/>
					<logic:notEmpty name="appList">
					
						<table  width="100%"  class="bordered">
						<tr>
						<th colspan="4"><big>Approver Details</big></th></tr>
						<tr>
							<th style="text-align:left;"><b>Type</b></th>
							<th style="text-align:left;"><b>Employee Code</b></th>
							<th style="text-align:left;"><b>Name</b></th>	
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

			
				
					
					</logic:notEmpty>
			<br/>		
					
			<logic:notEmpty name="leaveDetails">
			<display:table name="leaveDetails"  requestURI="/hrLeave.do" pagesize="10" 
			 defaultsort="1" defaultorder="descending" id="mytable1" >
     
		    <display:column property="id" title="Req No" sortable="true"   />
		   
			 <display:column property="leaveType" title="Leave Type" sortable="true"   />
            <display:column property="startDate" title="Start Date" sortable="true"/>
            <display:column property="endDate" title="End Date" sortable="true"  />
            <display:column property="noOfDays" title="No Of Days" sortable="true" />
            <display:column property="status" title="Status" sortable="true"   />
            <display:column property="submitDate" title="Submit Date" sortable="true" />
            
            </display:table>
              
					
			</logic:notEmpty>
			<logic:notEmpty name="leaveDraftDetails">
			<display:table name="leaveDraftDetails" requestURI="/hrLeave.do" 
					pagesize="10"  id="mytable1" >
			 <display:column title="select">
						<input type="checkbox" name="listid2"   value='<bean:write name="mytable1" property="id"/>' title="Check To Approve/Reject" />
						</display:column>
			
			 <display:column property="id" href="hrLeave.do?method=selectContent" paramId="id" title="Request No." sortable="true"   />
            <display:column property="startDate" title="Start Date" sortable="true"  />
            <display:column property="endDate" title="End Date" sortable="true"  />
            <display:column property="noOfDays" title="No Of Days" sortable="true"  />
             <display:column property="holidayType" title="Holiday Type" sortable="true"  />
            <display:column property="leaveType" title="Leave Type" sortable="true"   />
            
       </display:table>
       
       <html:button property="method"  value="Cancel Request" onclick="cancelRequest()"/>
            
           
            
		</logic:notEmpty>
			<logic:notEmpty name="leaveEmpDetails">
			<display:table name="leaveEmpDetails" requestURI="/hrLeave.do" pagesize="10"  id="mytable1" >
			
			<display:column property="generatedBy" title="Applied By" sortable="true"   />
				<display:column property="submitDate" title="SubmitDate" sortable="true" />
			 <display:column property="leaveType" title="Leave Type" sortable="true"   />
			 <display:column property="holidayType" title="Holiday Type" sortable="true"  />
            <display:column property="startDate" title="Start Date" sortable="true"  />
            <display:column property="endDate" title="End Date" sortable="true"  />
            <display:column property="noOfDays" title="No Of Days" sortable="true"  />
            <display:column title=" uploaded files" sortable="true">
									<bean:define id="file" name="mytable1"
										property="fileNames" />
								    <%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
									%>
									<a href="/EMicro Files/ESS/Leave<%=v[i]%>" target="_blank"><%=v[i]%></a>
									<%
									}
									%>		
			  </display:column>
            <display:column property="status" title="Status" sortable="true"   />
             <display:column title="select">
						<input type="checkbox" name="listid"   value='<bean:write name="mytable1" property="id"/>' title="Check To Approve/Reject" />
						</display:column>
             </display:table>
           <div align="center">
           <html:button property="method"  value="Approve" onclick="submitRequest('approve')"/>
         	  <html:button property="method"  value="Reject" onclick="submitRequest('reject')"/>
           </div>
		   </logic:notEmpty>
		  
		  <logic:notEmpty name="noRecords">


<table id="mytable1">
<tr>
<th>
</th><th></th>

<th>
Req No</th><th>Leave Type</th><th >Start Date</th><th>End Date</th><th >No Of Days</th><th >Status</th><th >Plant</th><th >Submit Date</th></tr>
</table>

</logic:notEmpty> 
		    <html:hidden name="hrLeaveForm" property="applyAfterDate"/>
		   
   <html:hidden name="hrLeaveForm" property="casleavadv"/>
	<html:hidden name="hrLeaveForm" property="preleavadv"/>	 
	<html:hidden name="hrLeaveForm" property="preleavmin"/>
	
	<html:hidden name="hrLeaveForm" property="sicklvcloseBal"/>	 
	<html:hidden name="hrLeaveForm" property="casuallvcloseBal"/>	 
	<html:hidden property="clMaxDays" name="hrLeaveForm"  />
	<html:hidden property="slMaxDays" name="hrLeaveForm"  />
	
	<html:hidden property="clmindur" name="hrLeaveForm"  />
	<html:hidden property="slmindur" name="hrLeaveForm"  />
	<html:hidden property="lossmindur" name="hrLeaveForm"  />
	<html:hidden property="plmindur" name="hrLeaveForm"  />
	
	
            </html:form>
            <div style="clear:both"></div>



</body>
</html>
