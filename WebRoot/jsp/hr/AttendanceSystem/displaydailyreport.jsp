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
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
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


<title>Home Page</title>

<script language="javascript">

function process()
{
var k = document.forms[0].fromDate.value;

if(k=="")
{
alert("Please Select date");
return false;
}


document.forms[0].action="hrApprove.do?method=dailyreportsearch";
document.forms[0].submit();
var x=window.showModalDialog("hrApprove.do?method=dailyreportexe",null, "dialogWidth=800px;dialogHeight=600px; center:yes");

}

function processexcel()
{
var k = document.forms[0].fromDate.value;

if(k=="")
{
alert("Please Select date ");
return false;
}


document.forms[0].action="hrApprove.do?method=exportdailyreportsearch";
document.forms[0].submit();


}

function clearAllFields(){
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";
}




function statusMessage(message){
alert(message);
}

function back()
{

document.forms[0].action="hrApprove.do?method=attendanceReport";
document.forms[0].submit();

}
</script>
</head>

<body>

				
	<html:form action="hrApprove" enctype="multipart/form-data">
	
			<div align="center">
				<logic:notEmpty name="hrApprovalForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="hrApprovalForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
			</div>




<table class="bordered" >
<tr><th><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Daily Attendance Detail Report for the Date :  <bean:write name="hrApprovalForm" property="date"/></b></big></center>	
	
	</th></tr></table>


&nbsp;&nbsp;
<div style="width: 40%">
<table class="bordered" >
<tr>
<th colspan="2">Summary Of Attendance</th>
</tr>
<tr>
<td>Present:</td>
<td><bean:write name="hrApprovalForm" property="presize" /></td>
</tr>
<tr>
<td>Absent:</td>
<td><bean:write name="hrApprovalForm" property="abssize" /></td>
</tr>
<tr>
<td>On Duty:</td>

<td><bean:write name="hrApprovalForm" property="odsize" /></td>

</tr>
<tr>
<td>Leave:</td>
<td><bean:write name="hrApprovalForm" property="leavesize" /></td>

</tr>
<tr>
<td>Loss Of Pay:</td>
<td><bean:write name="hrApprovalForm" property="lopsize" /></td>
</tr>

<tr>
<td>Total:</td>
<td>${hrApprovalForm.presize+hrApprovalForm.abssize+hrApprovalForm.odsize+hrApprovalForm.leavesize+hrApprovalForm.lopsize}</td>
</tr>
</table>
</div>

</br>

<div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <%int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="9"><center>Employee On Leave </center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Division</th>                    
                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Leave Type</th>
                    <th>No Of Days</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="leave">
                  <logic:iterate id="abc1" name="leave">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="fromDate"/></td>
                    <td> <bean:write name="abc1" property="toDate"/></td>
                    <td> <bean:write name="abc1" property="leavetype"/></td>
                    <td> <bean:write name="abc1" property="noOfDays"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 <br/>
			
				 
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="9"><center>Employee On Onduty</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Division</th>                    
                    <th>From Date</th>
                    <th>To Date</th>
                  
                    <th>From Time</th>
                     <th>To Time</th>
                    
                  </tr>
                
                   <logic:notEmpty name="onduty">
                  <logic:iterate id="abc1" name="onduty">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i %></td>
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="fromDate"/></td>
                    <td> <bean:write name="abc1" property="toDate"/></td>
                 
                    <td> <bean:write name="abc1" property="startTime"/></td>
                   <td> <bean:write name="abc1" property="endTime"/></td>
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 
				  <br/>
				  
				  	 
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="9"><center>Employee On Loss of Pay</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Division</th>                    
                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Leave Type</th>
                    <th>No Of Days</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="lop">
                  <logic:iterate id="abc1" name="lop">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="fromDate"/></td>
                    <td> <bean:write name="abc1" property="toDate"/></td>
                    <td> <bean:write name="abc1" property="leavetype"/></td>
                    <td> <bean:write name="abc1" property="noOfDays"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 
				 <br>
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="6"><center>Absent Employee<br/>International Bussiness</center></th></tr>
                  <tr>
                  
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Designation</th>                    
                    <th>Division</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="absenceint">
                  <logic:iterate id="abc1" name="absenceint">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="6"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 
				 <br>
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="6"><center>Absent Employee<br/>Domestic Services</center></th></tr>
                  <tr>
                  
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Designation</th>                    
                    <th>Division</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="absence">
                  <logic:iterate id="abc1" name="absence">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="6"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>


</center>

</div>
</html:form>
</body>
</html>
