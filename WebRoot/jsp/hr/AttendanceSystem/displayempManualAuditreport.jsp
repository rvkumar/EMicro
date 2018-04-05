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


function statusMessage(message){
alert(message);
}



function process()
{


document.forms[0].action="hrApprove.do?method=musterReportSearch";
document.forms[0].submit();

ShowProgressAnimation();
document.getElementById('mainContent').style.display = 'none';

}

function processexcel()
{


document.forms[0].action="hrApprove.do?method=exportmusterReportSearch";
document.forms[0].submit();


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

<table class="bordered" border="1" >
<tr><th><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manual Entry Audit Report  <bean:write name="hrApprovalForm" property="month"/>-<bean:write name="hrApprovalForm" property="year"/></b></big></center>
	</th></tr></table>
<%int i=0; %>
 <div style="width: 90%" >
 <table class="bordered" >
 <tr>
 <th>No</th>
 <th>Empl No.</th>
 <th>Empl Name</th>
 <th>Location</th>
 <th>Swipe Date</th>
 <th>Swipe Type</th>
 <th>Prev Time</th>
 <th>Actual Time</th>
 <th>Reason Type</th>
 <th>Remarks</th>
 <th>User Name</th>
 <th>User Ip</th>
 <th>User Designation</th>
 <th>User Department</th>
 <th>Updated date </th>
 </tr>
 <logic:notEmpty name="llist">
                  <logic:iterate id="abc1" name="llist">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>     
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="locationId"/></td>
                    <td> <bean:write name="abc1" property="startDate"/></td>
                    <td> <bean:write name="abc1" property="swipe_Type"/></td>
                    <td> <bean:write name="abc1" property="prev_time"/></td>
                    <td> <bean:write name="abc1" property="time"/></td>
                    <td> <bean:write name="abc1" property="reason_Type"/></td>
                    <td> <bean:write name="abc1" property="remarks"/></td>
                    <td> <bean:write name="abc1" property="employeeNumber"/></td>
                    <td> <bean:write name="abc1" property="id"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="date"/></td>
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				 
				 </table>
</div>

</html:form>
</body>
</html>
