<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript" src="js/sorttable.js"></script>

<script language="javascript">


 function setOutTime(){
 var todayDate=new Date();
var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!

    var yyyy = today.getFullYear();
    if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} var today = dd+'/'+mm+'/'+yyyy;
var hours=todayDate.getHours();
var minutes=todayDate.getMinutes();
var seconds=todayDate.getSeconds();
var format ="AM";
if(hours>11)
{format="PM";
}
if (hours   > 12) { hours = hours - 12; }
if (hours   == 0) { hours = 12; }  
if (minutes < 10){
    minutes = "0" + minutes;
}
 if(document.forms[0].outTime.value==""){
 document.forms[0].outTime.value=(today+" "+hours+":"+minutes+":"+seconds+" "+format);
 }
 }
  function setInTime(){
  
  var todayDate=new Date();
var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!

    var yyyy = today.getFullYear();
    if(dd<10){dd='0'+dd} if(mm<10){mm='0'+mm} var today = dd+'/'+mm+'/'+yyyy;
var hours=todayDate.getHours();
var minutes=todayDate.getMinutes();
var seconds=todayDate.getSeconds();
var format ="AM";
if(hours>11)
{format="PM";
}
if (hours   > 12) { hours = hours - 12; }
if (hours   == 0) { hours = 12; }  
if (minutes < 10){
    minutes = "0" + minutes;
}
 document.forms[0].inTime.value=(today+" "+hours+":"+minutes+":"+seconds+" "+format);
 }
 function updateDetails(){
 

 
 if(document.forms[0].outTime.value==""){
 alert("Please Enter Out Time")
 document.forms[0].outTime.focus();
 return false;
 }
 if(document.forms[0].inTime.value!="")
 {
	  if(document.forms[0].outTime.value==""){
	 alert("Please Enter Out Time")
	 document.forms[0].outTime.focus();
	 return false;
	 } 
 }
 document.forms[0].action="security.do?method=updateDetails&";
document.forms[0].submit();
 }



 window.onunload = refreshParent;
 function refreshParent() {
 var x=window.parent.property;
 
 
     window.close();
     
   
      window.opener.location.reload(); 
 }
                 
           
        
    </script>

    
     

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
body {
 overflow: -moz-scrollbars-vertical;
 overflow-x: hidden;
} 
</style>
<body>
<html:form action="security" enctype="multipart/form-data" method="post">


				<logic:present name="securityForm" property="message1">
					<font color="red" size="3"><b><bean:write name="securityForm" property="message1" /></b></font>
				</logic:present>
				<logic:present name="securityForm" property="message">
					<font color="Green" size="3"><b><bean:write name="securityForm" property="message" /></b></font>
				</logic:present>
		
	<logic:notEmpty name="On Duty">		
	<div style="width: 50%;" align="center">
    <table class="bordered" align="center" >
    <tr>
    <th colspan="4"><center>On Duty Details</center></th>
    </tr>
    <tr> <td colspan="1" rowspan="2">
    <img src="/EMicro Files/images/EmpPhotos/<bean:write name="securityForm" property="empPhoto"/>" alt=""  width="85px" height="85px" />
   </td> </tr>
    <tr>
    <td style="font-size: 30px;" colspan="3"><center><bean:write name="securityForm" property="userName"/></center></td>
    </tr>
     <tr>
    <th >Department</th><td ><bean:write name="securityForm" property="department"/></td>
  
    <th >Designation</th><td ><bean:write name="securityForm" property="designation"/></td>
    </tr>
    
    <tr>
    <th >On Duty Type</th><td ><bean:write name="securityForm" property="ondutyType"/></td>
   
    <th >Visiting Plant</th><td ><bean:write name="securityForm" property="location"/></td>
    </tr>
    <tr>
    <th>From Date</th><td ><bean:write name="securityForm" property="fromDate"/></td>

    <th>To Date</th><td ><bean:write name="securityForm" property="toDate"/></td>
    </tr>
    <tr>
    <th>From Time</th><td ><bean:write name="securityForm" property="fromTime"/></td>
    
    <th>To Time</th><td ><bean:write name="securityForm" property="toTime"/></td>
    </tr>
    <tr>
    <th>Reason</th><td colspan="3"><bean:write name="securityForm" property="reason"/></td>
    </tr>
    <tr>
    <th>Out Time</th><td colspan="3"><html:text property="outTime" style="width:200px;" readonly="true"/>&nbsp;<a href="#"><img src="images/alarmclock.png" height="25" width="25" align="absmiddle" onclick="setOutTime()" title="Click To Enter Time"/></a></td>
    </tr>
     <tr>
    <th>In Time</th><td colspan="3"><html:text property="inTime" style="width:200px;" readonly="true"/>&nbsp;<a href="#"><img src="images/alarmclock.png" height="25" width="25" align="absmiddle" onclick="setInTime()" title="Click To Enter Time"/></a></td>
    </tr>
    <tr>
    <td colspan="4"><center><html:button property="method" value="Save" style="width:100px;" onclick="updateDetails()" styleClass="rounded"/></center></td>
    </tr>
    </table>	
    </div>
    </logic:notEmpty>
      <logic:notEmpty name="Permission">		
 <div style="width: 50%;" align="center">
    <table class="bordered">
    <tr>
    <th colspan="4"><center>Permission Details</center></th>
    </tr>
       <tr> <td colspan="1" rowspan="2">
    <img src="/EMicro Files/images/EmpPhotos/<bean:write name="securityForm" property="empPhoto"/>" alt=""  width="85px" height="85px" />
   </td> </tr>
    <tr>
    <td style="font-size: 30px;" colspan="3"><center><bean:write name="securityForm" property="userName"/></center></td>
    </tr>
       <tr>
    <th >Department</th><td ><bean:write name="securityForm" property="department"/></td>
    <th >Designation</th><td ><bean:write name="securityForm" property="designation"/></td>
    </tr>
    <tr>
    <th>Date</th><td colspan="3"><bean:write name="securityForm" property="fromDate"/></td>
    </tr>
   <tr>
    <th>From Time</th><td ><bean:write name="securityForm" property="fromTime"/></td>
   
    <th>To Time</th><td ><bean:write name="securityForm" property="toTime"/></td>
    </tr>
    <tr>
    <th>Reason</th><td colspan="3"><bean:write name="securityForm" property="reason"/></td>
    </tr>
    <tr>
    <th>Out Time</th><td colspan="3"><html:text property="outTime" style="width:200px;" readonly="true"/>&nbsp;<a href="#"><img src="images/alarmclock.png" height="25" width="25" align="absmiddle" onclick="setOutTime()" title="Click To Enter Time"/></a></td>
    </tr>
     <tr>
    <th>In Time</th><td colspan="3"><html:text property="inTime" style="width:200px;" readonly="true"/>&nbsp;<a href="#"><img src="images/alarmclock.png" height="25" width="25" align="absmiddle" onclick="setInTime()" title="Click To Enter Time"/></a></td>
    </tr>
    <tr>
    <td colspan="4"><center><html:button property="method" value="Save" style="width:100px;" onclick="updateDetails()" styleClass="rounded"/> </center></td>
    </tr>
    </table>	
    </div>
    </logic:notEmpty>
    <html:hidden property="requestNo"/>
    <html:hidden property="reqType"/>
    <html:hidden property="requsterID"/>
     <html:hidden property="selectedFilter"/>
	 <html:hidden property="securityDate"/>
</html:form>
