
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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry();
});


$(function () {


	$('#timeTo').timeEntry();
});

$('.timeRange').timeEntry({beforeShow: customRange}); 
 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null),  
        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
}

</script>


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




function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}


function daydiff(first, second) {

//daydiff

var totaldays=(second-first)/(1000*60*60*24);

if(totaldays<0)
{
alert("start date should be less than end date");
  document.forms[0].endDate.value="";
	document.forms[0].noOfDays.value=0;      
	      
    return 0;
}
else{

    return ((second-first)/(1000*60*60*24)+1)
    }
}


function uploadDocument()
{
	document.forms[0].action="hrLeave.do?method=uploadOnDutyDocuments";
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
	document.forms[0].action="hrLeave.do?method=deleteIOnDutyDocuments&cValues="+checkvalues;
document.forms[0].submit();
}
}
}



function displayTabs(param)
{
	document.forms[0].action="leave.do?method=displayTabs&param="+param;
	document.forms[0].submit();
}
function applyOnduty(param)
{
	var type=param;
	if(type=='Applied')
	{

		if(document.forms[0].onDutyType.value=="")
	    {
	      alert("Onduty Type should not be left blank");
	      document.forms[0].onDutyType.focus();
	      return false;
	    }
		 if(document.forms[0].onDutyType.value=="Visit Plants")
         {
	          if(document.forms[0].locationId.value=="")
	         {
	         
		      alert("Plant should not be left blank");
		      document.forms[0].locationId.focus();
		      return false;
	         }
        } 
	     if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	if(document.forms[0].startDate.value!="")
	    {    
var year=new Date().getFullYear();
var stDate=document.forms[0].startDate.value;
var stYear=stDate.split("/");
stYear=stYear[2];
if(year<stYear){
alert("Please Select Only Current Year");
document.forms[0].startDate.value="";
return false;
}
}
	   if(document.forms[0].startTime.value=="") 
	    {
	      alert("From Time should not be left blank");
	      document.forms[0].startTime.focus();
	      return false;
	    }
	     if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	    if(document.forms[0].endDate.value!="")
	    {   
		var year=new Date().getFullYear();
		var endYear=document.forms[0].endDate.value;
		var endYear=endYear.split("/");
		endYear=endYear[2];
			if(year<endYear){
			alert("Please Select Only Current Year");
			document.forms[0].endDate.value="";
			return false;
			}
	    }
	    if(document.forms[0].endTime.value=="")
	    {
	      alert("End Time should not be left blank");
	      document.forms[0].endTime.focus();
	      return false;
	    }
	    
	     if(document.forms[0].startDate.value!=document.forms[0].endDate.value)
	    {
	   var str1 = document.forms[0].startDate.value;
var str2 = document.forms[0].endDate.value;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1-1, dt1); 
var date2 = new Date(yr2, mon2-1, dt2); 

if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}
	    
	    
	     }
	    
	   if(document.forms[0].startDate.value==document.forms[0].endDate.value)
	    { 
	          
 var startTime=document.forms[0].startTime.value;
 var endTime=document.forms[0].endTime.value;
 var startPeriod=startTime.slice(-2);
 var endPeriod=endTime.slice(-2);
 
 if(startPeriod=='PM' && endPeriod=='AM')
 {
 
 alert("Please enter valid time cycle");
 
 return false;
 
 }  
    
 if(startPeriod=='AM' && endPeriod=='AM' || startPeriod=='PM' && endPeriod=='PM')
 {
    var startHR=startTime.substring(0,2);
 var endHR=endTime.substring(0,2);
   var startMin=startTime.substring(3,5);
 var endMin=endTime.substring(3,5);
 

 
 startHR=parseInt(startHR); 
 endHR=parseInt(endHR);
  startMin=parseInt(startMin); 
 endMin=parseInt(endMin);

   if ( startPeriod=='PM' && endPeriod=='PM' )
 {
 
 if(startHR!="12")
 {
  startHR=startHR+12;
 }
 if(endHR!="12")
 {
  endHR=endHR+12;
 }
 }
 
 

 

 if(startHR>endHR)
 {
  alert("To time should be greater than From time");

 return false;
 
 }
  if(startHR==endHR && endMin<startMin )
 {
  alert("To time Minutes should be greater than From time Minutes");

 return false;
 
 }
 

 
 }
	    
	    
	    
	    }
	    
	        if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Valid reason.");
	      document.forms[0].reason.focus();
	      return false;
	    }
	    var reason=document.forms[0].reason.value;
         
         var splChars = "'";
for (var i = 0; i < reason.length; i++) {
    if (splChars.indexOf(reason.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Reason!"); 
     document.forms[0].reason.focus();
 return false;
}
}



	    
	    
		document.forms[0].action="hrLeave.do?method=saveOnduty&param="+param;
		document.forms[0].submit();
	}
	
}
function closeLeave()
{
	document.forms[0].action="hrLeave.do?method=search";
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

if(document.forms[0].startDate.value==""){
alert("Please Select Start Date");
document.forms[0].startDate.focus();
return false;
}else if(document.forms[0].duration.value==""){
alert("Please Select Duration");
document.forms[0].duration.focus();
return false;
}else if(document.forms[0].endDate.value==""){
alert("Please Select End Date");
document.forms[0].endDate.focus();
return false;
}
var stDate=document.forms[0].startDate.value;
var endDate=document.forms[0].endDate.value;
var durtaion=document.forms[0].duration.value;

if(stDate!=endDate && (durtaion=="FH" || durtaion=="SH"))
{

alert("Invalid Selection Duration");
document.forms[0].duration.focus();
document.forms[0].duration.value="";
document.forms[0].noOfDays.value="";
return false;

}
if(stDate==endDate && (durtaion=="FH" || durtaion=="SH"))
{
document.forms[0].noOfDays.value="0.5";
}else{
document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));

var endDate=document.forms[0].noOfDays.value;

}
if(endDate=="")
{
document.forms[0].endDate.value="";
}

}



function checkTotalDays(){

var stDate=document.forms[0].startDate.value;
var endDate=document.forms[0].endDate.value;
var Stdurtaion=document.forms[0].duration.value;
var endDuration=document.forms[0].endDurationType.value;

if(document.forms[0].onDutyType.value=="")
{
alert("Please Select On Duty Type");
document.forms[0].onDutyType.focus();
return false;
}

if(stDate!="" && endDate!="" && Stdurtaion!="" && endDuration!=""){
if(stDate!=endDate && (Stdurtaion=="FH" || endDuration=="SH"))
{
alert("Invalid Selection Duration");
document.forms[0].endDurationType.value="";
document.forms[0].endDurationType.focus();
document.forms[0].noOfDays.value="";
return false;
}

if(stDate==endDate && ((Stdurtaion=="FH" && endDuration=="FD") || ((Stdurtaion=="SH" && endDuration=="FD") )|| (Stdurtaion=="FD" && endDuration=="FH") || (Stdurtaion=="FD" && endDuration=="SH") ))
{
alert("Invalid Selection Duration");

document.forms[0].endDurationType.value="";
document.forms[0].endDurationType.focus();
document.forms[0].noOfDays.value="";
return false;
}

if(document.forms[0].onDutyType.value=="Visit Plants")
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
    var totalDays=document.forms[0].noOfDays.value;
    if(totalDays==0.0){
    alert("Please Check Start Date/End Date");
    document.forms[0].noOfDays.value="";
     document.forms[0].endDurationType.value="";
    }
    }
  }
var location=document.forms[0].locationId.value;
xmlhttp.open("POST","onDuty.do?method=calculateDays&StartDate="+stDate+"&StartDur="+Stdurtaion+"&EndDate="+endDate+"&EndDur="+endDuration+"&location="+location,true);
xmlhttp.send();
}
else{


if(stDate==endDate && (Stdurtaion=="FD" || endDuration=="FD"))
{
	document.forms[0].noOfDays.value="1";
}
if(stDate==endDate && ( (Stdurtaion=="FH" && endDuration=="FH") || (Stdurtaion=="SH" && endDuration=="SH")  ))
{
	document.forms[0].noOfDays.value="0.5";
}else{
	var totalDays=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	
	if(stDate!=endDate && ((Stdurtaion=="FD" && endDuration=="FH")|| (Stdurtaion=="SH" && endDuration=="FD"))) 
	{
	var totalDays=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	document.forms[0].noOfDays.value=totalDays-0.5;
	}else{
	document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	}
	var endDate=document.forms[0].noOfDays.value;
}

if(stDate!=endDate )
{

	if(Stdurtaion=="SH" && endDuration=="FH")
	{
	var totalDays=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	
	document.forms[0].noOfDays.value=totalDays-1;
	
	}
	if(Stdurtaion=="FD" && endDuration=="FD")
	{
	document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	}
}

}
}
}

function calDays(){
alert("hi");
var stDate=document.forms[0].startDate.value;
var endDate=document.forms[0].endDate.value;
var Stdurtaion=document.forms[0].duration.value;
var endDuration=document.forms[0].endDurationType.value;
var onDutyType=document.forms[0].onDutyType.value;

if(stDate!="" && endDate!="" && Stdurtaion!="" && endDuration!="" &&  onDutyType!="")
{
if(stDate!=endDate && (Stdurtaion=="FH" || endDuration=="SH"))
{
alert("Invalid Selection Duration");
document.forms[0].duration.value="";
document.forms[0].duration.focus();
document.forms[0].noOfDays.value="";
return false;
}

if(stDate==endDate && ((Stdurtaion=="FH" && endDuration=="FD") || ((Stdurtaion=="SH" && endDuration=="FD") )|| (Stdurtaion=="FD" && endDuration=="FH") || (Stdurtaion=="FD" && endDuration=="SH") ))
{
alert("Invalid Selection Duration");

document.forms[0].duration.value="";
document.forms[0].duration.focus();
document.forms[0].noOfDays.value="";
return false;
}

if(document.forms[0].onDutyType.value=="Visit Plants")
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
    var totalDays=document.forms[0].noOfDays.value;
    if(totalDays==0.0){
    alert("Please Check Start Date/End Date");
    document.forms[0].noOfDays.value="";
     document.forms[0].endDurationType.value="";
    }
    }
  }

var location=document.forms[0].locationId.value;
xmlhttp.open("POST","onDuty.do?method=calculateDays&StartDate="+stDate+"&StartDur="+Stdurtaion+"&EndDate="+endDate+"&EndDur="+endDuration+"&location="+location,true);
xmlhttp.send();
}
else{


if(stDate==endDate && (Stdurtaion=="FD" || endDuration=="FD"))
{
	document.forms[0].noOfDays.value="1";
}
if(stDate==endDate && ( (Stdurtaion=="FH" && endDuration=="FH") || (Stdurtaion=="SH" && endDuration=="SH")  ))
{
	document.forms[0].noOfDays.value="0.5";
}else{
	var totalDays=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	
	if(stDate!=endDate && ((Stdurtaion=="FD" && endDuration=="FH")|| (Stdurtaion=="SH" && endDuration=="FD"))) 
	{
	var totalDays=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	document.forms[0].noOfDays.value=totalDays-0.5;
	}else{
	document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	}
	var endDate=document.forms[0].noOfDays.value;
}

if(stDate!=endDate)
{

	if(Stdurtaion=="SH" && endDuration=="FH")
	{
	var totalDays=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	
	document.forms[0].noOfDays.value=totalDays-1;
	
	}
	if(Stdurtaion=="FD" && endDuration=="FD")
	{
	document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));
	}
}

}
}
}

function checkType()
{
if(document.forms[0].onDutyType.value=="Visit Plants")
{
 document.getElementById("plant").style.visibility="visible";
}
if(document.forms[0].onDutyType.value!="Visit Plants")
{
 document.getElementById("plant").style.visibility="hidden";
}
}
function test1()
{

document.getElementById("messageID").style.visiblity="hidden";
}

function attendance()
{

var startDate=document.forms[0].startDate.value;

var endDate=document.forms[0].endDate.value;



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
xmlhttp.open("POST","hrLeave.do?method=displayattendance&StartDate="+startDate+"&EndDate="+endDate+"&empNo="+empNo,true);
xmlhttp.send();

}


</script>
</head>

<body >
	<html:form action="hrLeave" enctype="multipart/form-data">
		<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="hrLeaveForm" property="message">
				<font color="green" size="3"><b><bean:write name="hrLeaveForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="hrLeaveForm" property="message2">
				<font color="red" size="3"><b><bean:write name="hrLeaveForm" property="message2" /></b></font>
				
			</logic:present>
		</div>
		
	<br/>
	
	 <div  id="attproces" >
	    
	    	</div>
			<br/>
		<table class="bordered content" width="90%">
			 <tr><th  colspan="4" align="center">
					 On Duty Form </th>
					 <tr>
					 	<html:hidden property="empNumber"/>
					 <td>Employee Number</td><td colspan="3"><bean:write name="hrLeaveForm" property="empName"/></td>
					 </tr>
  						<tr>
  							<td width="15%">On Duty Type <font color="red" size="3">*</font></td>
							<td width="64%" align="left" >
							<html:select name="hrLeaveForm" property="onDutyType" styleClass="text_field" onchange="checkType(),attendance()">
						
									<html:option value="">--Select--</html:option>
							<html:options name="hrLeaveForm" property="ondutyvalue" labelProperty="ondutyreason"/>
								
									</html:select>
							</td>
							<td>Plant<div style="visibility: hidden" id="plant"><font color="red" size="3">*</font></div></td>  
			<td >
	 			<html:select property="locationId" styleClass="text_field" >
	 				<html:option value="">--Select--</html:option>
	  				<html:options property="locationIdList" labelProperty="locationLabelList" ></html:options>   
	 			</html:select>
			</td>
						</tr>
						<tr>
							<td width="15%">Start Date <font color="red" size="3" >*</font></td> 
							
							<td align="left" width="34%">
									<html:text property="startDate" styleId="startDate" onfocus="popupCalender('startDate')" styleClass="text_field"
										readonly="true" />
							</td>
							
								<td>				
					<p> Out&nbsp;Time<font color="red" size="3">*</font> </td><td><html:text property="startTime" styleId="timeFrom"  size="15"/></p></td>
						</tr>
						<tr>
							<td width="15%">End Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
								<html:text property="endDate" styleId="endDate" onfocus="popupCalender('endDate')" styleClass="text_field" 
										readonly="true" onchange="attendance()"/>
							</td>
							
							
							<td><p>In&nbsp;Time<font color="red" size="3">*</font> </td>
							<td><html:text property="endTime"  styleId="timeTo"   size="15"/></p>
							</tr>
							
						<tr>
				<th colspan="4"> Reason<font color="red" size="2">*</font> : 
						</th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="10"></html:textarea>
						
							</td>
						</tr>

						<logic:notEmpty name="documentDetails">
						<th colspan="6">Uploaded Documents </th>
						</tr>
						

						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/ESS/On Duty/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
							<td align="center" colspan="6">
								<input type="button"  class="content" value="Delete" onclick="deleteDocumentsSelected()"/>
							</td>
							
						</logic:notEmpty>
						
					    <tr >
		                  <th  colspan="4" align="center">Document Path : 
		                     	<html:file property="documentFile" />
			                     <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadDocument();" style="align:right;width:100px;"/>
			                   
		                  </th>
		                </tr>
		          	<tr>
							<td colspan="4" align="center">
								<html:button property="method" styleClass="rounded" value="Apply" onclick="applyOnduty('Applied');" style="align:right;width:100px;"/> &nbsp;
							
								<!--<html:button property="method" styleClass="rounded" value="Save As Draft" onclick="applyOnduty('Draft');" style="align:right;width:100px;"/>&nbsp;
							
								--><html:reset styleClass="rounded"  value="Clear" style="align:right;width:100px;"/>&nbsp;
							
					       	<html:button property="method" styleClass="rounded" value="Close" onclick="closeLeave()" style="align:right;width:100px;"/>
					       	    
							</td>
				</tr></table>
				<br/>
		
		
		 <logic:notEmpty name="appList">
		 <div align="left" class="bordered ">
			<table width="100%"   class="sortable">
			<tr>
				<th style="text-align:left;"><b>Type</b></th>
				<th style="text-align:left;"><b>EmpNo</b></th>
				<th style="text-align:left;"><b>Emp Name</b></th>	
				<th style="text-align:left;"><b>Designation</b></th>
			</tr>
			<logic:iterate id="abc" name="appList">
			<tr>
			
			<td>Approver Manager</td>
			<td>${abc.approverID}</td>
			<td>${abc.approverName}</td>
			<td>${abc.appDesig}</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
		
		<html:hidden property="requestNumber"/>
	</html:form>

	</body>
</html>
					
			