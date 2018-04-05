
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
               
           if(document.forms[0].type.value=="Early")    
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
	   document.forms[0].action="hrLeave.do?method=savePermission&param="+param;
		document.forms[0].submit();




}

function closeLeave()
{
	document.forms[0].action="hrLeave.do?method=search";
	document.forms[0].submit();
}
	
	function chngeview()
{

if(document.forms[0].type.value=="Early")
{

document.getElementById("totime").style.display="";


}
else
{

document.getElementById("totime").style.display="none";

}

}
</script>

  </head>
  
  <body>

 
<html:form action="hrLeave">

			<div align="center">
				<logic:present name="hrLeaveForm" property="message2">
					<font color="red" size="3"><b><bean:write name="hrLeaveForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="hrLeaveForm" property="message">
					<font color="Green" size="3"><b><bean:write name="hrLeaveForm" property="message" /></b></font>
				</logic:present>
			</div>
			
			<table class="bordered content" width="80%">
		 <tr><th  colspan="8" align="center">
					 Permission Form </th>
  						</tr>
  						<tr><td>Employee Number</td><td colspan="3"><bean:write name="hrLeaveForm" property="empName"/></td></tr>
						<tr>
						<td width="10%">Permission Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" >
							<html:text property="date" styleId="popupDatepicker"></html:text>
								
							</td>
							
							<td>
							Type
							</td>
							
							<td colspan="2">
							<html:select property="type" name="hrLeaveForm" onchange="chngeview()">							
							<option value="Late">Late Coming</option>
							<option value="Early">Early Going</option>
							<option value="Missing">Missing</option>
						     <option value="Forgot Swipe">Forgot Swipe</option>
							</html:select>
							</td>
							</tr>
							<tr>
			<td >				
<p> Time: <font color="red" size="3">*</font></td><td>
<html:text property="startTime" styleId="timeFrom"  size="15"/> </td><td id="totime" style="display: none;">To Time<font color="red" size="3">*</font>
<html:text property="endTime" styleId="timeTo"  size="15"/></p>
</td><td><b>Swipe Type<font color="red">*</font></b></td>
<td><html:select property="swipetype" name="hrLeaveForm"  >
						<option value="In">In</option>
						<option value="Out">Out</option>	
								</html:select>       </td>
								<html:hidden property="empNumber"/>
					 
<%-- <td><p>  In Time: <!-- <font color="red" size="3">*</font> --></td><td><html:text property="endTime"  styleId="timeTo"   size="15"/></p>
		</td> --%>
							</tr>
							</table>
			
	
				<br/>
					<table class="bordered content" width="80%">
			 <tr><th  colspan="4" align="center">
					 Enter Your Reason Here<font color="red" size="2">*</font>  </th>
  						<tr>
				</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="109" rows="9"></html:textarea>
						
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
						<html:button property="method"  styleClass="rounded" value="Submit" onclick="applyPermission('Applied');" style="align:right;width:100px;"/> &nbsp;
						
						<html:reset styleClass="rounded"    value="Reset" style="align:right;width:100px;"/>&nbsp;
					
						<html:button property="method"  styleClass="rounded" value="Close" onclick="closeLeave()" style="align:right;width:100px;"/>
						</td>
								</td>
				</tr></table>
					
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
	
	
	
	
	
	</table>








</html:form>
  </body>
</html>
