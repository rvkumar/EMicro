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


<div>


</div>
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
<td> Leave:</td>
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

<br/>
<br/>

<%-- <logic:notEmpty name="emplist">
<table class="bordered">
<tr><th colspan="14"><center>
Micro Labs Limited,<bean:write name="hrApprovalForm" property="locationId"/><br/>
Daily Performance Report for the date: <bean:write name="hrApprovalForm" property="fromDate"/></center></b></big>
</th></tr>
<tr><th>Emp Code</th><th>Shift</th><th>Dept</th><th>Sub_Dept</th><th>Rep Grp</th><th>Emp Name</th><th>Designation</th><th>Qualification</th><th>Arrival</th><th>Late Hrs</th><th>Dep Time</th><th>Early Hrs</th><th>Work Hrs</th><th>OT Hrs</th><th>Pres/Abs</th>
</tr>
<logic:notEmpty name="deptlist">

<logic:iterate id="b" name="deptlist">
<tr>
<td colspan="13">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>
<b>Subdepartment</b> :   <bean:write name="b" property="deptTo" /><br/>
<b>Rep Grp   </b>   :   <bean:write name="b" property="desgTo" />
</td>

</tr>


<logic:iterate id="a" name="emplist">

<logic:equal value="${a.department}" name="b" property="department">
<logic:equal value="${a.subdepartment}" name="b" property="subdepartment">
<logic:equal value="${a.repgrp}" name="b" property="repgrp">

<tr>
<td><bean:write name="a" property="employeeno" /></td>
<td><bean:write name="a" property="shift" /></td>
<td><bean:write name="a" property="deptTo" /></td>
<td><bean:write name="a" property="subdeptTO" /></td>
<td><bean:write name="a" property="rep_et" /></td>
<td><bean:write name="a" property="employeeName" /></td>
<td><bean:write name="a" property="designation" /></td>
<td><bean:write name="a" property="qualification" /></td>
<td><bean:write name="a" property="startTime" /></td>
<td><bean:write name="a" property="late" /></td>
<td><bean:write name="a" property="endTime" /></td>
<td><bean:write name="a" property="early" /></td>
<td><bean:write name="a" property="total" /></td>
<td><bean:write name="a" property="ot" /></td>
<td><bean:write name="a" property="status" /></td>


</tr>
</logic:equal>
</logic:equal></logic:equal>

</logic:iterate>

</logic:iterate>

</logic:notEmpty >

<logic:notEmpty name="onlydeptlist">

</tr>


<logic:iterate id="a" name="emplist">




<tr>
<td><bean:write name="a" property="employeeno" /></td>
<td><bean:write name="a" property="shift" /></td>
<td><bean:write name="a" property="deptTo" /></td>
<td><bean:write name="a" property="subdeptTO" /></td>
<td><bean:write name="a" property="rep_et" /></td>
<td><bean:write name="a" property="employeeName" /></td>
<td><bean:write name="a" property="designation" /></td>
<td><bean:write name="a" property="qualification" /></td>
<td><bean:write name="a" property="startTime" /></td>
<td><bean:write name="a" property="late" /></td>
<td><bean:write name="a" property="endTime" /></td>
<td><bean:write name="a" property="early" /></td>
<td><bean:write name="a" property="total" /></td>
<td><bean:write name="a" property="ot" /></td>
<td><bean:write name="a" property="status" /></td>


</tr>


</logic:iterate>



</logic:notEmpty>

</table>
</logic:notEmpty> --%>

<logic:notEmpty name="abslist">
<table class="bordered">
<tr><th colspan="33"><center>
Micro Labs Limited,<bean:write name="hrApprovalForm" property="locationId"/><br/>
Continuous Absent Report for the date: <bean:write name="hrApprovalForm" property="fromDate"/> to <bean:write name="hrApprovalForm" property="toDate"/></center></b></big>
</th></tr><tr>
<th>Pernr</th><th>Name</th>
           <logic:iterate id="d" name="datelist">
           <th>${d.day}           </th>
					</logic:iterate>
					</tr><logic:notEmpty name="deptlist">
<logic:iterate id="b" name="deptlist">
<%-- <tr>
<td colspan="12">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>

</td>

</tr>
 --%>

<logic:iterate id="abc1" name="abslist">

<logic:equal value="${abc1.department}" name="b" property="department">
<!-- <tr>


</tr> -->

<tr>
<td colspan="12">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>

</td>
<td><bean:write name="abc1" property="employeeno"/></td>
<td><bean:write name="abc1" property="employeeName"/></td>
<logic:iterate id="d" name="datelist">         

<td> <bean:write name="abc1" property="day${d.day}"/></td>

</logic:iterate>
</tr>
</logic:equal>


</logic:iterate>

</logic:iterate>
</logic:notEmpty>
</table>

</logic:notEmpty>
</html:form>
</body>
</html>
