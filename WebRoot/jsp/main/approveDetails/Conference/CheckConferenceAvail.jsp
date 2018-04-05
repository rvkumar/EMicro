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
	
	if(document.forms[0].fromTime.value!=""){
		if(document.forms[0].toTime.value==""){
			alert("Please Select To Time");
			document.forms[0].toTime.focus();
			return false;
		}
	}
	
	var url="approvals.do?method=searchConfRoom";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function roomsList(){

	window.showModalDialog("conference.do?method=roomsList",window.location, "dialogWidth=750px;dialogHeight=620px; center:yes");

}

function bookRoom(){
	var url="conference.do?method=bookRoom";
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
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}



function clearSearch(){
	
	document.forms[0].locationId.value="";
	document.forms[0].floor.value="";
	document.forms[0].roomName.value="";
	document.forms[0].fromDate.value="";
	document.forms[0].fromTime.value="";
	document.forms[0].toTime.value="";
	
}
</script>
</head>
<body >

	<html:form action="approvals" enctype="multipart/form-data">
	<table class="bordered">
	<tr>
	<th colspan="6"><center>Conference Room Search</center></th>
	</tr>
	<tr>
<th>Location<font color="red">*</font></th>
<td><html:select  property="locationId" onchange="getFloor(this.value)">
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
</td>
<th>Floor<font color="red">*</font></th>

<td>
<div id="subcategoryID" align="left">
<html:select  property="floor" onchange="getRoomList(this.value)">
		<html:option value="">--Select--</html:option>
		
	</html:select>
</div>
</td>
<th>Conf.Room<font color="red">*</font></th>
<td>
<div id="roomID" align="left">
<html:select  property="roomName">
		<html:option value="">--Select--</html:option>
		
	</html:select>
</div>
</td>

	</tr>
	<tr>
	<th>Required Date<font color="red">*</font></th><td> <html:text property="fromDate" styleId="popupDatepicker"/></td>
	<th>From Time</th><td><html:text property="fromTime" styleId="timeFrom"/></td>
	<th>To Time</th><td><html:text property="toTime" styleId="timeTo"/> </td>
	</tr>
	<tr>
	<td colspan="6"><center> <html:button property="method" value="Search" onclick="searchConfRoom()" styleClass="rounded"/> &nbsp;&nbsp;
	<input type="reset" value="Clear" class="rounded" onclick="clearSearch()"/></center>
	 </td>
	</tr>
	</table>
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
</logic:notEmpty>	
<logic:notEmpty name="NoRecords">
<table class="bordered">
 <tr>
 <th>Req.No</th><th>Name</th><th>Location</th><th>Floor</th><th>Conf.Room</th><th>From Date</th><th>To Date</th><th>Status</th>
 </tr>
 <tr><td colspan="8"><center><b><font color="green">No Records Found</font></b></center></td></tr>
 </table>
</logic:notEmpty>

<logic:notEmpty name="ResetSearch">
	<script type="text/javascript">
	setSelectedFloor('<bean:write name="approvalsForm" property="floor" />');
	setSelectedRoom('<bean:write name="approvalsForm" property="floor" />','<bean:write name="approvalsForm" property="roomName" />');
	</script>
	
</logic:notEmpty>
</html:form>
</body>
</html>
