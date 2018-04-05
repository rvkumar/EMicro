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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>



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
function roomsList(){

	window.showModalDialog("conference.do?method=roomsList",window.location, "dialogWidth=750px;dialogHeight=620px; center:yes");

}

function bookRoom(){
	if(document.forms[0].locationId.value==""){
		alert("Please Select Location");
		document.forms[0].locationId.focus();
		return false;
	}
	if(document.forms[0].floor.value==""){
		alert("Please Select Floor");
		document.forms[0].floor.focus();
		return false;
	}
	if(document.forms[0].roomName.value==""){
		alert("Please Select Conference Room");
		document.forms[0].roomName.focus();
		return false;
	}
	if(document.forms[0].fromDate.value==""){
		alert("Please Select From Date");
		document.forms[0].fromDate.focus();
		return false;
	}
	if(document.forms[0].fromTime.value==""){
		alert("Please Select From Time");
		document.forms[0].fromTime.focus();
		return false;
	}
	if(document.forms[0].toDate.value==""){
		alert("Please Select To Date");
		document.forms[0].toDate.focus();
		return false;
	}
	if(document.forms[0].toTime.value==""){
		alert("Please Select To Time");
		document.forms[0].toTime.focus();
		return false;
	} 
	if(document.forms[0].purpose.value==""){
		alert("Please Enter Purpose");
		document.forms[0].purpose.focus();
		return false;
	} 
	
	var st = document.forms[0].purpose.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].purpose.value=st;  
	
	
	var url="conference.do?method=submitDetails";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}


</script>

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
function displyBookedList(){

document.getElementById("bookedRoomID").style.visibility="visible";
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
xmlhttp.open("POST","conferenceAppr.do?method=getFloorList&locID="+dt,true);
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
xmlhttp.open("POST","conferenceAppr.do?method=getRoomsList&locID="+locId+"&floor="+floor,true);
xmlhttp.send();
}

function checkAvailable()
{
	
	if(document.forms[0].locationId.value==""){
		alert("Please Select Location");
		document.forms[0].locationId.focus();
		return false;
	}
	if(document.forms[0].floor.value==""){
		alert("Please Select Floor");
		document.forms[0].floor.focus();
		return false;
	}
	if(document.forms[0].roomName.value==""){
		alert("Please Select Conference Room");
		document.forms[0].roomName.focus();
		return false;
	}
	if(document.forms[0].fromDate.value==""){
		alert("Please Select From Date");
		document.forms[0].fromDate.focus();
		return false;
	}
	if(document.forms[0].fromTime.value==""){
		alert("Please Select From Time");
		document.forms[0].fromTime.focus();
		return false;
	}
	if(document.forms[0].toDate.value==""){
		alert("Please Select To Date");
		document.forms[0].toDate.focus();
		return false;
	}
	if(document.forms[0].toTime.value==""){
		alert("Please Select To Time");
		document.forms[0].toTime.focus();
		return false;
	}
	var st = document.forms[0].purpose.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].purpose.value=st;
	
	var locId=document.forms[0].locationId.value;
	var floor=document.forms[0].floor.value;
	var room=document.forms[0].roomName.value;
	var fromDate=document.forms[0].fromDate.value;
	var toDate=document.forms[0].toDate.value;
	var formTime=document.forms[0].fromTime.value;
	var toTime=document.forms[0].toTime.value;
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
    document.getElementById("availabilityID").innerHTML=xmlhttp.responseText;
    
    }
  }
xmlhttp.open("POST","conference.do?method=checkAvailablety1&locID="+locId+"&floor="+floor+"&room="+room+"&fromDate="+fromDate+"&toDate="+toDate+"&formTime="+formTime+"&toTime="+toTime,true);
xmlhttp.send();
}
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}
function bookRoomList(){
	var url="conference.do?method=displayList";
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
	xmlhttp.open("POST","conferenceAppr.do?method=getFloorList&locID="+locationId,true);
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
xmlhttp.open("POST","conferenceAppr.do?method=getRoomsList&locID="+locId+"&floor="+floor,true);
xmlhttp.send();
	
	
}
</script>
</head>
<body >

	<html:form action="conference" enctype="multipart/form-data">
	<table class="bordered">
	<tr><th colspan="4"><center><big>Conference Room Booking Form</big></center></th></tr>
	<tr>
	<th colspan="4">Requester Details</th>
	</tr>
	<tr><td><b>Name:</b></td><td> <bean:write name="conferenceForm" property="requestername"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="conferenceForm" property="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="conferenceForm" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="conferenceForm" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="conferenceForm" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="conferenceForm" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="conferenceForm" property="ipPhoneno"/></td>


<html:hidden property="hostname" />
<html:hidden property="IPNumber" />


<td><b>IP Address:</b></td><td ><bean:write name="conferenceForm" property="IPNumber"/></td></tr>

	<tr>
	<th colspan="4">Conference Room Details</th>
	</tr>
	<tr>
	<td>Location &nbsp;<font color="red">*</font></td>
	<td><html:select  property="locationId" onchange="getFloor(this.value)">
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
	</td>
	<td>Floor &nbsp;<font color="red">*</font></td>
	<td><div id="subcategoryID" align="left">
<html:select  property="floor" onchange="getRoomList(this.value)">
		<html:option value="">--Select--</html:option>
		
	</html:select>
</div></td>
	</tr>
	<tr>
	<td>Conf.Room Name &nbsp;<font color="red">*</font></td>
	<td colspan="3"><div id="roomID" align="left">
<html:select  property="roomName">
		<html:option value="">--Select--</html:option>
	</html:select>
</div>
</td>
</tr>
	<tr>
	<td>From Date &nbsp;<font color="red" >*</font></td><td>
	<html:text property="fromDate" styleId="startDate" onfocus="popupCalender('startDate')" />
	</td>
	
	<td>From Time &nbsp;<font color="red">*</font></td><td> 
	<html:text property="fromTime" styleId="timeFrom" />
	</td>
	</tr>
	<tr>
	<td>To Date &nbsp;<font color="red">*</font></td>
	<td><html:text property="toDate" styleId="toDate" onfocus="popupCalender('toDate')" /></td>
	<td>To Time &nbsp;<font color="red" >*</font></td>
	<td><html:text property="toTime" styleId="timeTo" /> </td>
	</tr>
	<tr><td></td>
	<td></td>
	</tr>
	<tr>
	<th colspan="4">Purpose&nbsp;<font color="red">*</font></th>
	</tr>
	<tr>
	<td colspan="4"><html:textarea property="purpose" rows="3" cols="100"/> </td>
	</tr>
	<tr>
	<td colspan="6"><center>
	<html:button property="method" value="Check Availability" onclick="checkAvailable()" styleClass="rounded"/>&nbsp;&nbsp;
	<html:button property="method" value="Book Room" onclick="bookRoom()" styleClass="rounded"/>&nbsp;&nbsp;
	<html:button property="method" value="Back" onclick="bookRoomList()" styleClass="rounded"/></center>
	 </td>
	</tr>
	</table>
	
	<div>&nbsp;</div>
<div id="availabilityID">
	
	</div>
	<div align="center" id="messageID" style="visibility: true;">
				<logic:present name="conferenceForm" property="message">
					<font color="Green" size="3"><b><bean:write name="conferenceForm" property="message" /></b></font>
					<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
				</logic:present>
				<logic:present name="conferenceForm" property="message2">
					<font color="red" size="3"><b><bean:write name="conferenceForm" property="message2" /></b></font>
					<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
				</logic:present>
			</div>
	<div>&nbsp;</div>
	<logic:notEmpty name="resarvedList">
	
 <table class="bordered">
 <tr>
 <th colspan="8"><center>Conference Reserved Rooms</center></th>
 </tr>
 <tr>
 <th>Req.No</th><th>Name</th><th>Location</th><th>Floor</th><th>Conf.Room</th><th>From Date</th><th>To Date</th><th>Status</th>
 </tr>
 
 <logic:iterate id="c" name="resarvedList">
 <tr>
 <td>${c.reqNo }</td>
 <td>${c.empName }</td>
 <td>${c.location }</td>
  <td>${c.floor }</td>
  <td>${c.roomName }</td>
  <td>${c.fromDate }</td>
  <td>${c.toDate }</td>
  <td>${c.approvalStatus }</td>
 </tr>
 </logic:iterate>
</table>
<script type="text/javascript">
	setSelectedFloor('<bean:write name="conferenceForm" property="floor" />');
	setSelectedRoom('<bean:write name="conferenceForm" property="floor" />','<bean:write name="conferenceForm" property="roomName" />');
	</script>

</logic:notEmpty>
			
	</html:form>
	
</body>
	
</html>	