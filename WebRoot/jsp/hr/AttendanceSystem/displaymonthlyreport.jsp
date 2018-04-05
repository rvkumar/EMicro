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


function process()
{

var loc=document.forms[0].locationId.value;
var month=document.forms[0].month.value;
var year=document.forms[0].year.value;
var summbrkup=document.forms[0].summbrkup.value;


document.forms[0].action="hrApprove.do?method=monthlyReportSearch";
document.forms[0].submit();
/* loading(); */
var x=window.showModalDialog("hrApprove.do?method=monthlyReportexe&loc="+loc+"&month="+month+"&year="+year+"&summbrkup="+summbrkup,null, "dialogWidth=800px;dialogHeight=600px; center:yes");

}




function statusMessage(message){
$('#overlay').remove();
alert(message);
}


 function loading() {
        // add the overlay with loading image to the page
        var over = '<div id="overlay">' +
            '<img id="loading" src="images/loadinggif.gif">' +
            '</div>';
        $(over).appendTo('body');

       
    };
    
    
    function processexcel()
{


document.forms[0].action="hrApprove.do?method=exportmonthlyreportsearch";
document.forms[0].submit();


}   


function back()
{

document.forms[0].action="hrApprove.do?method=attendanceReport";
document.forms[0].submit();

}
</script>

<style>

  #overlay {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.6;
    filter: alpha(opacity=80);
}
#loading {
    width: 250px;
    height: 257px;
    position: absolute;
    top: 20%;
    left: 50%;
    margin: -18px 0 0 -15px;
}

  </style>

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

		 <div style="width: 90%" >
	<a href="javascript:window.print()"><img src="jsp/admin/print.png" title="Print this Page"></a>	 
	<table  border="1" >
<tr><td ><center>
<img  src="images/logo.png" width='50' height='60'></center>
</td><th><center><font color="blue"><big><strong>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Monthly Attendance Detail Report for the Month of  <bean:write name="hrApprovalForm" property="month"/>-<bean:write name="hrApprovalForm" property="year"/></big>
	</font></center></th></tr></table>
</center>
</br>
</br>
                <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table border="1" >
                 
                     <tr>   <th colspan="22"><center>Attendance Summary List</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th style="width:120px;">Employee No </th>
                    <th style="width:150px;">Employee Name </th>
                    <th>Pay Grp Name</th>
                    <th>Department</th>
                    <th>Designation</th>                    
                    <th>Work.</th>
                   
                    <th>CL</th>
                    <th>SL</th>
                    <th>EL</th>
                    <th>LP</th>
                    <th>CO</th>
                    <th>ML</th>
                    <th>Lv.Avld</th> 
                    <th>AA</th>
                    <th>Tot.W</th>
                    <th>OD</th>
                   
                    
                  </tr>
                  
                   
                  <logic:iterate id="abc1" name="list1">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i%></td>
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="payGrpTo"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="working"/></td>
                    <td> <bean:write name="abc1" property="cl"/></td>
                 	<td> <bean:write name="abc1" property="sl"/></td>
                    <td> <bean:write name="abc1" property="el"/></td>
                    <td> <bean:write name="abc1" property="lp"/></td>
                    <td> <bean:write name="abc1" property="co"/></td>
                    <td> <bean:write name="abc1" property="ml"/></td>
                    <td> <bean:write name="abc1" property="leave_Availed"/></td>
                   <td> <bean:write name="abc1" property="aa"/></td>
                    <td> <bean:write name="abc1" property="total_worked"/></td>
             		<td> <bean:write name="abc1" property="od"/></td>      

                  </tr>
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty>
				  
				  
				  
			<logic:notEmpty name="list2">
				<% int i=0;%>
				    <table border="1" >
                 
                     <tr>   <th colspan="20"><center>Employee Wise Daily Break List</center></th></tr>
                  <tr>
  
                    <th >#</th>
                     <!-- <th >Date</th> -->
                    <th style="width:120px;">Employee No </th>
                    <th >Date</th>
                    <th style="width:150px;">Employee Name </th>
                     <th>In </th>
                    <th>Out</th>
                      <th>Status </th>
                     <th>From Date </th>
                    <th>To Date</th>
                    <th>Department</th>
                      <th>Designation</th>         
                        <th>Remarks</th>
                    <th>PaygroupID</th>
                      <th>Paygroup</th>
                      
                      </tr>
                      
                        <logic:iterate id="abc1" name="list2">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i %></td>
					<%-- <td> <bean:write name="abc1" property="date"/></td> --%>     
					<td> <bean:write name="abc1" property="employeeno"/></td>   
					<td> <bean:write name="abc1" property="date"/></td> 
					<td> <bean:write name="abc1" property="employeeName"/></td>
					<td> <bean:write name="abc1" property="startTime"/></td>     
					<td> <bean:write name="abc1" property="endTime"/></td>
					<td> <bean:write name="abc1" property="status"/></td>     
					<td> <bean:write name="abc1" property="startDate"/></td>
					<td> <bean:write name="abc1" property="endDate"/></td>
					<td> <bean:write name="abc1" property="department"/></td>
					<td> <bean:write name="abc1" property="designation"/></td>
					<td> <bean:write name="abc1" property="remarks"/></td>
					<td> <bean:write name="abc1" property="payGrpFrom"/></td>
					<td> <bean:write name="abc1" property="paygrp"/></td>
                   
                     
                     </tr>
                     </logic:iterate>
                     
                     
                     <%if(i==0) 
					{
					%>
					<tr>  <td colspan="20"><center>Currently details are not available to display</center></td> </tr>
					<%} %> 
                      
                      </table>

</logic:notEmpty>
</div>
</html:form>
</body>
</html>
