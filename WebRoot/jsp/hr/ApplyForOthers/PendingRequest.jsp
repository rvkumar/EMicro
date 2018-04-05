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
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">
function showSelectedFilter(){
if(document.forms[0].reqRequstType.value!="" ){

	
	var url="hrLeave.do?method=pendingRecords&sCount=0&eCount=0&searchTxt=";
	document.forms[0].action=url;
	document.forms[0].submit();

	
	
	}
}
function nextRecord(){
var url="hrLeave.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="hrLeave.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="hrLeave.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){
var url="hrLeave.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
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
<table class="bordered" width="90%">
<tr>
	<th colspan="4"><big>Others Pending Request</big></th>
</tr> 
<tr>
<td><b>Request Type : <font color="red">*</font></b>
</td><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId" onchange="showSelectedFilter()">
	<html:option value="">Select</html:option>
		<html:option value="Leave">Leave</html:option>	
		<html:option value="On Duty">On Duty</html:option>
		<html:option value="Permission">Permission</html:option>
	</html:select>
	</td>
	<html:hidden property="selectedFilter" value="Pending"/>
</tr>						
</table>
<br/>			
<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="hrLeaveForm"/>-
	
	<bean:write property="endRecord"  name="hrLeaveForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>

	</table>
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
<br/>
<logic:notEmpty name="Permissionlist">
<table class="sortable bordered">
<tr>
	<th>Req&nbsp;No</th><th style="width:100px;"> Type</th><th style="width:200px;">Employee  Name</th><th>Req Date</th>
	<th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th>
	
</tr>
<logic:iterate id="onDuty" name="Permissionlist">
<tr>
<td><bean:write name="onDuty" property="requestNumber"/></td>
<td>Permission</td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="createDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>

</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="ondutyList">
<table class="sortable bordered">
<tr >
	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th>
</tr>
<logic:iterate id="onDuty" name="ondutyList">
<tr>


<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="onDutyType"/></td>

<td><bean:write name="onDuty" property="locationId"/></td>
<td><bean:write name="onDuty" property="startDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endDate"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="submitDate"/></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="leaveList">	



		<table class="bordered sortable">
			<tr>
					<th>Req&nbsp;No</th><th style="width:100px;">Leave Type</th><th style="width:200px;">Employee Name</th><th>Req Date</th>
					<th style="width:100px;">Start Date</th><th style="width:100px;">Duration</th><th style="width:100px;">End Date</th><th style="width:100px;">Duration</th>
					<th style="width:100px;">No Of Days</th>
				</tr>
					
<logic:iterate id="leave" name="leaveList">

<tr>


<td><bean:write name="leave" property="requestNumber"/></td>
<td><bean:write name="leave" property="leaveType"/></td>
<td><bean:write name="leave" property="employeeName"/></td>
<td><bean:write name="leave" property="submitDate"/></td>
<td><bean:write name="leave" property="startDate"/></td>
<td><bean:write name="leave" property="startDurationType"/></td>
<td><bean:write name="leave" property="endDate"/></td>
<td><bean:write name="leave" property="endDurationType"/></td>
<td><bean:write name="leave" property="noOfDays"/></td>

</tr>

</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="no Leave records">

		<table class="bordered sortable">
			<tr>
				<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;">Leave Type</th><th style="width:200px;">Employee Name</th><th>Req Date</th>
					<th style="width:100px;">Start Date</th><th style="width:100px;">Duration</th><th style="width:100px;">End Date</th><th style="width:100px;">Duration</th>
					<th style="width:100px;">No Of Days</th><th style="width:100px;">Status</th><th style="width:50px;">View</th>
				</tr>
				
<tr>
<td colspan="12">				
<div align="center">
<font color="red" size="3">Searched details could not be found.</font>
</div></td></tr>
</table>
</logic:notEmpty>
<logic:notEmpty name="no Material Master records">
<logic:equal value="Permission" property="reqRequstType" name="hrLeaveForm">
<table class="sortable bordered">
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;"> Type</th><th style="width:200px;">Employee Full Name</th><th>Req Date</th>
	<th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th>
	<th style="width:100px;">Status</th><th style="width:50px;">View</th><logic:equal value="Approved" name="hrLeaveForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal>
</tr>
</logic:equal>
<logic:equal value="On Duty" property="reqRequstType" name="hrLeaveForm">
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Req Date</th><th style="width:100px;">Status</th><th style="width:50px;">View</th><logic:equal value="Approved" name="hrLeaveForm"  property="selectedFilter"><th style="width:100px;">Cancel</th></logic:equal>
</tr>
</logic:equal>
<tr>
<td colspan="13">
<div align="center">
<font color="red" size="3">Searched details could not be found.</font>
</div>
</td>
</tr>
</table>
</table></logic:notEmpty>
</html:form>
  </body>
</html>