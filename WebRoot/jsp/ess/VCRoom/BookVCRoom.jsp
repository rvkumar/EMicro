<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><br />
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

	window.showModalDialog("vc.do?method=roomsList",window.location, "dialogWidth=750px;dialogHeight=620px; center:yes");

}

function bookRoom(){


	if(document.forms[0].usage.value=="Others")
	{
	if(document.forms[0].employeeno.value==""){
		alert("Please Select Employee No.");
		document.forms[0].employeeno.focus();
		return false;
	
	}
	
	if(document.forms[0].employeeno.value==document.forms[0].empno.value){
		alert("Please Select other Employee Nos.");
		document.forms[0].employeeno.value="";
		document.forms[0].employeeno.focus();
		return false;
	
	}
	
	
	}
	
	if(document.forms[0].employeeno.value==document.forms[0].empno.value){		
		document.forms[0].employeeno.value="";		
		return false;
	
	}
	
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
	
	if(document.forms[0].vcFrom.value==""){
		alert("Please Select VC From");
		document.forms[0].vcFrom.focus();
		return false;
	}
	if(document.forms[0].vcTo.value==""){
		alert("Please Select VC To");
		document.forms[0].vcTo.focus();
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
	
	
	
	 if(document.forms[0].fromDate.value==document.forms[0].toDate.value)
	    { 
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
	    }
	    
	    else
		 {
		 

	     var str1 = document.forms[0].fromDate.value;
 		var str2 = document.forms[0].toDate.value;
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
	 


	 

	
	
	var locId=document.forms[0].locationId.value;
	var b=locId.split(",");	
	var fromDate=document.forms[0].fromDate.value;
	var toDate=document.forms[0].toDate.value;
	var formTime=document.forms[0].fromTime.value;
	var toTime=document.forms[0].toTime.value;
	var vcFrom=document.forms[0].vcFrom.value;
	var c=vcFrom.split(",");	
	var vcTo=document.forms[0].vcTo.value; 
	var d=vcTo.split(",");	
	
	
	
	
	var url="vc.do?method=submitDetails&locID="+b[0]+"&floor="+b[2]+"&room="+b[1]+"&fromDate="+fromDate+"&toDate="+toDate+"&formTime="+formTime+"&toTime="+toTime+"&vcFrom="+c[0]+"&vcTo="+d[0];
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
xmlhttp.open("POST","vcAppr.do?method=getFloorList&locID="+dt,true);
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

function checkAvailable()
{
	
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
	
	
	 if(document.forms[0].startDate.value==document.forms[0].endDate.value)
	    { 
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
	    }
	
	var locId=document.forms[0].locationId.value;	
	var fromDate=document.forms[0].fromDate.value;
	var toDate=document.forms[0].toDate.value;
	var formTime=document.forms[0].fromTime.value;
	var toTime=document.forms[0].toTime.value;
	var vcFrom=document.forms[0].vcFrom.value;
	var vcTo=document.forms[0].vcTo.value; 
	
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
xmlhttp.open("POST","vc.do?method=checkAvailablety1&locID="+locId+"&fromDate="+fromDate+"&toDate="+toDate+"&formTime="+formTime+"&toTime="+toTime+"&vcFrom="+vcFrom+"&vcTo="+vcTo,true);
xmlhttp.send();
}
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}
function bookRoomList(){
	var url="vc.do?method=displayList";
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

function searchEmployee(fieldName){

var reqFieldName=fieldName

	var toadd = document.getElementById(reqFieldName).value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById(reqFieldName).focus();
	if(toadd == ""){
		document.getElementById(reqFieldName).focus();
		document.getElementById("sU").style.display ="none";
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
        if(reqFieldName=="employeeno"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}


function selectUser(input,reqFieldName){


	document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeno"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
	
	 function  colr()
 {
 
   if(document.forms[0].usage.value=="Others")
 {
 
  document.getElementById("emp").style.visibility="visible";
 }
 else
 
  {
 document.forms[0].employeeno.value="";
  document.getElementById("emp").style.visibility="hidden";
 }
 
 }
</script>
</head>
<body >

	<html:form action="vc" enctype="multipart/form-data">
	<html:hidden property="empno" name="vcForm" />
	<table class="bordered">
	<tr><th colspan="4"><center><big>VC Room Booking Form</big></center></th></tr>
	<tr>
	<th colspan="4">Requester Details</th>
	</tr>
	<tr><td><b>Name:</b></td><td> <bean:write name="vcForm" property="requestername"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="vcForm" property="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="vcForm" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="vcForm" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="vcForm" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="vcForm" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="vcForm" property="ipPhoneno"/></td>


<html:hidden property="hostname" />
<html:hidden property="IPNumber" />


<td><b>IP Address:</b></td><td ><bean:write name="vcForm" property="IPNumber"/></td></tr>

	<tr>
	<th colspan="4">VC Room Details</th>
	</tr>
	<tr><td>For</td>
	<td>
	<html:select property="usage" styleClass="content" styleId="filterId" onchange="colr()" >
							<html:option value="Yourself">Yourself</html:option>
							<html:option value="Others">Others</html:option>
							
							</html:select></td>
	
<td>Employee No <font color="red" style="visibility:hidden;" id="emp">*</font></td>
	<td><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno">
	<bean:write property="employeeno" name="vcForm" /></html:text>
	<div id="sU" style="display:none;">
	<div id="sUTD" style="width:400px;">
	<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
	</div>
    </div></td></tr>
	<tr>
	<td>Location &nbsp;<font color="red">*</font></td>
	<td colspan="3"><html:select  property="locationId" >
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>
	</td>
	<%-- <td>Floor &nbsp;<font color="red">*</font></td>
	<td><div id="subcategoryID" align="left">
<html:select  property="floor" onchange="getRoomList(this.value)">
		<html:option value="">--Select--</html:option>
		
	</html:select>
</div></td> --%>

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
	<tr><td>VC From &nbsp;<font color="red">*</font></td>
	
	<td><html:select  property="vcFrom">
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select></td>
	
	<td>VC To &nbsp;<font color="red">*</font></td>
	<td><html:select  property="vcTo">
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select></td>
	</tr>
	<tr>
	<th colspan="4">Purpose&nbsp;<font color="red">*</font></th>
	</tr>
	<tr>
	<td colspan="4"><html:textarea property="purpose" rows="3" cols="100"/> </td>
	</tr>
	<tr>
	<td colspan="6"><center>
	<%-- <html:button property="method" value="Check Availability" onclick="checkAvailable()" styleClass="rounded"/>&nbsp;&nbsp; --%>
	<html:button property="method" value="Book Room" onclick="bookRoom()" styleClass="rounded"/>&nbsp;&nbsp;
	<html:button property="method" value="Close" onclick="bookRoomList()" styleClass="rounded"/></center>
	 </td>
	</tr>
	</table>
	
	<div>&nbsp;</div>
<div id="availabilityID">
	
	</div>
	<div align="center" id="messageID" style="visibility: true;">
				<logic:present name="vcForm" property="message">
					<font color="Green" size="3"><b><bean:write name="vcForm" property="message" /></b></font>
					<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
				</logic:present>
				<logic:present name="vcForm" property="message2">
					<font color="red" size="3"><b><bean:write name="vcForm" property="message2" /></b></font>
					<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
				</logic:present>
			</div>
	<div>&nbsp;</div>
	<logic:notEmpty name="resarvedList">
	
 <table class="bordered">
 <tr>
 <th colspan="8"><center>VC Reserved Rooms</center></th>
 </tr>
 <tr>
 <th>Req.No</th><th>Name</th><th>Location</th><th>Floor</th><th>VC Room</th><th>From Date</th><th>To Date</th><th>Status</th>
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
	setSelectedFloor('<bean:write name="vcForm" property="floor" />');
	setSelectedRoom('<bean:write name="vcForm" property="floor" />','<bean:write name="vcForm" property="roomName" />');
	</script>

</logic:notEmpty>
			
	</html:form>
	
</body>
	
</html>	