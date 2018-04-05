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
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy',onSelect: res});
	
});


</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry(
	{
	timeSteps: [1, 5, 0],
	});
	
});


$(function () {


	$('#timeTo').timeEntry(
	{
	timeSteps: [1, 5, 0],
	});
	
});

$('.timeRange').timeEntry({beforeShow: customRange}); 

 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null), 

        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
       
}
/* $('.timeRange').timeEntry({beforeSetTime: firstHalfHourOnly}); 
 
function firstHalfHourOnly(oldTime, newTime, minTime, maxTime) { 
    var increment = (newTime - (oldTime || newTime)) > 0; 
    if (newTime.getMinutes() > 30) { 
        newTime.setMinutes(increment ? 0 : 30); 
        newTime.setHours(newTime.getHours() + (increment ? 1 : 0)); 
    } 
    return newTime; 
} */

</script>

	
	<script type="text/javascript">
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}
	
	function applyPermission(param)
{
               var type=param;
             
           if(document.forms[0].type.value=="Early" || document.forms[0].type.value=="Personal")    
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

  var shiftst=document.getElementById("shifStarttime").value;
  var shiften=document.getElementById("shifEndtime").value;
    
  var ft = document.forms[0].startTime.value;
  var hours = Number(ft.match(/^(\d+)/)[1]);
  var minutes = Number(ft.match(/:(\d+)/)[1]);
  var AMPM = ft.substring(5, 7); 
  if(AMPM == "PM" && hours<12) hours = hours+12;
  if(AMPM == "AM" && hours==12) hours = hours-12;
  var sHours = hours.toString();
  var sMinutes = minutes.toString();
  if(hours<10) sHours = "0" + sHours;
  if(minutes<10) sMinutes = "0" + sMinutes;
  var givst=sHours + ":" + sMinutes;

  var et = document.forms[0].endTime.value;
  var hours = Number(et.match(/^(\d+)/)[1]);
  var minutes = Number(et.match(/:(\d+)/)[1]);
  var AMPM = et.substring(5, 7); 
  if(AMPM == "PM" && hours<12) hours = hours+12;
  if(AMPM == "AM" && hours==12) hours = hours-12;
  var sHours = hours.toString();
  var sMinutes = minutes.toString();
  if(hours<10) sHours = "0" + sHours;
  if(minutes<10) sMinutes = "0" + sMinutes;
  var givend=sHours + ":" + sMinutes;

  var dt = new Date();
  var s =  givst.split(':');
  var userdt1 = new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(),
                     parseInt(s[0]), parseInt(s[1]), 00);

  var e =  givend.split(':');
  var userdt2 = new Date(dt.getFullYear(), dt.getMonth(),
                     dt.getDate(),parseInt(e[0]), parseInt(e[1]),00);
                     
                     
  var g =  shiftst.split(':');
  var shiftdt1 = new Date(dt.getFullYear(), dt.getMonth(), dt.getDate(),
                     parseInt(g[0]), parseInt(g[1]), 00);

  var p =  shiften.split(':');
  var shiftdt2 = new Date(dt.getFullYear(), dt.getMonth(),
                     dt.getDate(),parseInt(p[0]), parseInt(p[1]), 00);

           var difference = userdt2 - userdt1;  

 /*  if(difference<900000)
  {
    	alert("Minimum duration for applying permission is 15 Minutes");
  	return false;
  } */

  if((userdt1 >= shiftdt1 && userdt1 <= shiftdt2))
  	{
 
  	}
  else
  	{
  	alert("Start  time should be equal to or greater than Shift start time");
  	return false;
  	}

  if((userdt2 <= shiftdt2 && userdt2 >= shiftdt1))
  {
  
  }
  else
  {
  alert("End  time should be equal to or lesser than Shift end time");
  return false;
  }
  
  
  if(userdt1.getTime() === shiftdt1.getTime())
	  {
	
	 	document.forms[0].swipetype.value="In";	

	  }
  
   if(userdt2.getTime() === shiftdt2.getTime())
  {
  
 	document.forms[0].swipetype.value="Out";  	
 
  }
  
 
   
if(type=='Applied')
	{
	
		if(document.forms[0].date.value=="")
	    {
	      alert("Permission Date should not be left blank");
	      document.forms[0].date.focus();
	      return false;
	    }
	    if(document.forms[0].date.value!="")
	    {   
		var year=new Date().getFullYear();
		var endYear=document.forms[0].date.value;
		var endYear=endYear.split("/");
		endYear=endYear[2];
			if(year<endYear){
			alert("Please Select Only Current Year");
			document.forms[0].date.value="";
			return false;
			}
	    }
	    	if(document.forms[0].startTime.value=="")
	    {
	      alert("Time should not be left blank");
	      document.forms[0].startTime.focus();
	      return false;
	    }
		if(document.forms[0].endTime.value=="")
	    {
	      alert("End Time should not be left blank");
	      document.forms[0].endTime.focus();
	      return false;
	    } 
	    	if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Valid reason");
	      document.forms[0].reason.focus();
	      return false;
	    }
	    
	    
	}
	
	}
	else
	
	{
	
	 var startTime=document.forms[0].startTime.value;
// var endTime=document.forms[0].endTime.value;
 var startPeriod=startTime.slice(-2);
// var endPeriod=endTime.slice(-2);
 
/*  if(startPeriod=='PM' && endPeriod=='AM')
 {
 
 alert("Please enter valid time cycle");
 
 return false;
 
 }  */ 
    
/*  if(startPeriod=='AM' && endPeriod=='AM' || startPeriod=='PM' && endPeriod=='PM')
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
 

 
 } */

 
   
if(type=='Applied')
	{
	
		if(document.forms[0].date.value=="")
	    {
	      alert("Permission Date should not be left blank");
	      document.forms[0].date.focus();
	      return false;
	    }
	    if(document.forms[0].date.value!="")
	    {   
		var year=new Date().getFullYear();
		var endYear=document.forms[0].date.value;
		var endYear=endYear.split("/");
		endYear=endYear[2];
			if(year<endYear){
			alert("Please Select Only Current Year");
			document.forms[0].date.value="";
			return false;
			}
	    }
	    	if(document.forms[0].startTime.value=="")
	    {
	      alert("Time should not be left blank");
	      document.forms[0].startTime.focus();
	      return false;
	    }
	    /* 	if(document.forms[0].endTime.value=="")
	    {
	      alert("End Time should not be left blank");
	      document.forms[0].endTime.focus();
	      return false;
	    } */
	    	if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Valid reason");
	      document.forms[0].reason.focus();
	      return false;
	    }
	    
	    
	}
	
	}
	
	if(document.forms[0].type.value=="Forgot Swipe")
	{
	if(document.forms[0].swipetype.value=="In")
	{
	if(document.getElementById("iNTIME").value!="")
	{
	if(document.getElementById("iNTIME").value!="00:00")
	{
		alert("Intime already available..Request cannot be raised");
		return false;
		
	}
	}
	}
if(document.forms[0].swipetype.value=="Out")
	{
	
	if(document.getElementById("oUTTIME").value!="")
	{
	if(document.getElementById("oUTTIME").value!="00:00")
	{
	alert("oUTTIME already available..Request cannot be raised");
		return false;
	}
	}
	}
	}
	
		var savebtn = document.getElementById("masterdiv");
	  savebtn.className = "no";
	
	  document.forms[0].action="permission.do?method=savePermission&param="+param;
		document.forms[0].submit(); 



}

function closeLeave()
{
	document.forms[0].action="permission.do?method=displayPermissions";
	document.forms[0].submit();
}
	
	function chngeview()
{

if(document.forms[0].type.value=="Early" ||document.forms[0].type.value=="Personal" )
{

document.getElementById("totime").style.display="";


}
else
{

document.getElementById("totime").style.display="none";

}

}


function res()
{

var datepick=document.forms[0].popupDatepicker.value;

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
    document.getElementById("exactshiftime").value=document.getElementById("shiftime").value;
    }
  }

xmlhttp.open("POST","permission.do?method=displayAttendancejax&datepick="+datepick,true);
xmlhttp.send();
}

</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
  </head>
  
  <body onload="chngeview()">
<% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
 
<html:form action="permission">
<html:hidden property="type" name="permissionForm"  />
<%-- <html:hidden property="swipetype" name="permissionForm"  /> --%>
	<div id="masterdiv" class="">
			<div align="center">
				<logic:present name="permissionForm" property="message2">
					<font color="red" size="3"><b><bean:write name="permissionForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="permissionForm" property="message">
					<font color="Green" size="3"><b><bean:write name="permissionForm" property="message" /></b></font>
				</logic:present>
			</div>
			
			<div id="resid"></div>
			
	<table class="bordered content" width="80%">
		 <tr><th  colspan="8" align="center">
					<logic:notEmpty name="personal"> Permission Form  <small>( Note: Max of ${permissionForm.endRecord } requests can be raised for a month )</small></logic:notEmpty>	
					<logic:notEmpty name="forget">Forgot Swipe Form   <small>( Note: Max of ${permissionForm.endRecord } requests can be raised for a month )</small></logic:notEmpty>  </th>
  						</tr>
						<tr>
						<td width="10%">Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" colspan="3" >
							<html:text property="date" styleId="popupDatepicker"></html:text>
								
							</td>
							
							<%-- <logic:notEmpty name="forget">
							<td><b>Swipe Type<font color="red">*</font></b>&nbsp;&nbsp;&nbsp;
							<html:select property="swipetype" name="permissionForm"  >
						<option value="In">In</option>
						<option value="Out">Out</option>	
								</html:select></td> --%>
								
								<td >
							
							<logic:notEmpty name="forget">
							<div >
							</logic:notEmpty>
							
							<logic:empty name="forget">
							<div style="display: none">
							</logic:empty>
							
							<b>Swipe Type<font color="red">*</font></b>&nbsp;&nbsp;&nbsp;
							<html:select property="swipetype" name="permissionForm"  >
						<option value="In">In</option>
						<option value="Out">Out</option>	
								</html:select>
							</div>	
								
								</td>
								
								
						
							<logic:notEmpty name="personal">
							
							<td><b>Shift <font color="red">*</font></b>&nbsp;&nbsp;&nbsp;
								<input type="text" id="exactshiftime" value="" style="border: 0; width: 431px" readonly="readonly"></input></td>
							</logic:notEmpty>
					
							</tr>
							<tr>
			<td >				
<p> Time: <font color="red" size="3">*</font></td>
	


<td colspan="3">



<html:text property="startTime" styleId="timeFrom"  size="15"/> </td>

<td id="totime" style="display: none;" colspan="2">To Time<font color="red" size="3">*</font>
<html:text property="endTime" styleId="timeTo"  size="15"/>
</td>
<logic:notEmpty name="forget">
							
							<td><b>Shift <font color="red">*</font></b>&nbsp;&nbsp;&nbsp;
								<input type="text" id="exactshiftime" value="" style="border: 0; width: 431px" readonly="readonly"></input></td>
							</logic:notEmpty>
<%-- <td><p>  In Time: <!-- <font color="red" size="3">*</font> --></td><td><html:text property="endTime"  styleId="timeTo"   size="15"/></p>
		</td> --%>
							</tr>
							</table>
									<table class="bordered content" width="80%">
			 <tr><th  colspan="4" align="center">
					 Enter Your Reason Here<font color="red" size="2">*</font>  </th>
  						<tr>
				</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="100" rows="3" onkeypress="return isNumber(event)"  onkeyup="this.value = this.value.replace(/'/g,'`')" ></html:textarea>
						
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
						<html:button property="method"  styleClass="rounded" value="Submit" onclick="applyPermission('Applied');" style="align:right;width:100px;"/> &nbsp;
						
					
						</td>
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
			
		<td>${abc.appType}</td>
			<td>${abc.approverID}</td>
			<td>${abc.approverName}</td>
			<td>${abc.appDesig}</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
	
	
	
	
	
	</table>







</div>
</html:form>
  </body>
</html>
