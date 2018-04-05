<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
 <style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
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

<script type="text/javascript">

function searchConfRoom(){
	
	
	if(document.forms[0].locationId.value==""){
		alert("Please Select Location");
		document.forms[0].locationId.focus();
		return false;
	}
	
	
	if(document.forms[0].fromDate.value==""){
		alert("Please Select From Date");
		document.forms[0].fromDate.focus();
		return false;
	}
	
	if(document.forms[0].fromTime.value!=""){
		if(document.forms[0].toTime.value==""){
			alert("Please Select To Time");
			document.forms[0].toTime.focus();
			return false;
		}
	}
	
	
	var startTime=document.forms[0].fromTime.value;
 var endTime=document.forms[0].toTime.value;
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
  alert("To time should be greater than From Time");

 return false;
 
 }
  if(startHR==endHR && endMin<=startMin )
 {
  alert("To time Minutes should be greater than  From Time Minutes");

 return false;
 
 }
 

 
 }
	
	
	var url="vc.do?method=searchConfRoom";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function roomsList(){

	window.showModalDialog("vc.do?method=roomsList",window.location, "dialogWidth=750px;dialogHeight=620px; center:yes");

}

function bookRoom(){
	var url="vc.do?method=bookRoom";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function setSelectedFloor(floor){
	var locationId=document.forms[0].locationId.value;
	
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
	    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
	    
	    document.forms[0].floor.value=floor;
	    }
	  }
	xmlhttp.open("POST","vcAppr.do?method=getFloorList&locID="+locationId,true);
	xmlhttp.send();
	
}

function getFloor(linkname)
{
var xmlhttp;
var dt;
dt=linkname;
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
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getFloorList&locID="+dt,true);
xmlhttp.send();
}

function setSelectedRoom(floor,room){
	
	var locId=document.forms[0].locationId.value;
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
    document.getElementById("roomID").innerHTML=xmlhttp.responseText;
    
    document.forms[0].roomName.value=room;
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getRoomsList&locID="+locId+"&floor="+floor,true);
xmlhttp.send();
	
	
}

function getRoomList()
{
	
	var floor=document.forms[0].floor.value;
	var locId=document.forms[0].locationId.value;
	
	
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
    document.getElementById("roomID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getRoomsList&locID="+locId+"&floor="+floor,true);
xmlhttp.send();
}
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}



function clearSearch(){
	var url="vc.do?method=displayList";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function generatereport(){
	var url="vc.do?method=generateVCReportList";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
</script>
</head>
<body >

	<html:form action="vc" enctype="multipart/form-data">
	<table class="bordered">
	<tr>
	<th colspan="8"><center>VC Report</center></th>
	</tr>
	<tr>
<th>Location<font color="red">*</font></th>
<td><html:select  property="locationId" >
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
</td>


	<th>Month</th>
<td>

<html:select  property="month" name="vcForm" styleClass="testselect1">
<html:option value="1">Jan</html:option>
<html:option value="2">Feb</html:option>
<html:option value="3">Mar</html:option>
<html:option value="4">April</html:option>
<html:option value="5">May</html:option>
<html:option value="6">June</html:option>
<html:option value="7">July</html:option>
<html:option value="8">Aug</html:option>
<html:option value="9">Sep</html:option>
<html:option value="10">Oct</html:option>
<html:option value="11">Nov</html:option>
<html:option value="12">Dec</html:option>
</html:select>
</td>

	</tr>
	<tr>
	<th>Year</th><td>
<html:select  property="year" name="vcForm" styleClass="testselect1">
<html:options name="vcForm"  property="yearList"/>
</html:select>
</td>
	<th>Status</th><td><html:select  property="reqstatus" name="vcForm" styleClass="testselect1">
	
<html:option value="All">All</html:option>
<html:option value="Pending">Pending</html:option>
<html:option value="Reserved">Reserved</html:option>
<html:option value="Completed">Completed</html:option>
<html:option value="Self Cancelled">Self Cancelled</html:option>
<html:option value="Rejected">Rejected</html:option>
<html:option value="Cancelled">Cancelled</html:option>

</html:select></td>
	</tr>
	<tr>
	<td colspan="8"><center> <html:button property="method" value="Generate" onclick="generatereport()" styleClass="rounded"/> &nbsp;&nbsp;
	
	 </td>
	</tr>
	</table>


	<div>&nbsp;</div>

<logic:notEmpty name="report">
<table class="bordered"><tr><th colspan="10"><center>VC room booking details as on ${vcForm.displaymonth} - ${vcForm.year}</center></th></tr>
<tr><th>Location</th><th>Req no</th><th>Requester Name</th><th>Department</th><th>From Date</th><th>To Date</th><th>From Time</th><th>To Time</th><th>Duration(HH:MM)</th><th>Status</th></tr>
<logic:iterate id="a" name="report">
<tr>
<td>${a.location }</td>
<td>${a.reqNo }</td>
<td>${a.requestername }</td>
<td>${a.dept }</td>
<td>${a.fromDate }</td>
<td>${a.toDate }</td>
<td>${a.fromTime }</td>
<td>${a.toTime }</td>
<td>${a.duration }</td>
<td>${a.reqstatus }</td>


</tr>

</logic:iterate>

</table>


</logic:notEmpty>
	
	
	<logic:notEmpty name="noreport">
<table class="bordered"><tr><th colspan="10"><center>VC room booking details as on ${vcForm.displaymonth} - ${vcForm.year}</center></th></tr>
<tr><th>Location</th><th>Req no</th><th>Requester Name</th><th>Department</th><th>From Date</th><th>To Date</th><th>From Time</th><th>To Time</th><th>Status</th></tr>
<tr><td colspan="10"><center>No Records Found......</center></td></tr>

</table>


</logic:notEmpty>

	
	
	</html:form>
	
</body>
	
</html>	