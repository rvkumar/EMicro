<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <script language="javascript">
function changeStatus(elem){

  
	var elemValue = elem.value;
	
	if(elemValue=="Reject"||elemValue=="Cancel")
	{
		if(document.forms[0].remark.value==""){
		alert("Please Add Some Comments");
		 document.forms[0].remark.focus();
		 return false;
		}
	}
	var reqId = document.getElementById("reqId").value;;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=statusChangeVC&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;

	document.forms[0].action=url;
	document.forms[0].submit();
}

function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


}

function checkAvailability(){
	window.showModalDialog("approvals.do?method=checkVCAvail",window.location, "dialogWidth=950px;dialogHeight=500px; center:yes");

	
}
</script>
</head>
  <body>
  			<html:form action="/approvals.do" enctype="multipart/form-data">
  				
				<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
  	<table class="bordered content" width="80%">
	<tr><th colspan="4"><center><big>VC Room Booking Form</big></center></th></tr>
	<logic:notEmpty name="confList">
	<logic:iterate id="c" name="confList">
	
	<tr>
	<th colspan="4">Requester Details</th>
	</tr>
	<tr><td><b>Name:</b></td><td> <bean:write name="c" property="empName"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="c" property="empId"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="c" property="dept"/></td>
<td><b>Designation:</b></td><td ><bean:write name="c" property="desg"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="c" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="c" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="c" property="ipPhoneno"/></td>
<td><b>IP Address:</b></td><td ><bean:write name="c" property="IPNumber"/></td></tr>

	<tr>
	<th colspan="4">VC Room Details</th>
	</tr>
	<tr><td>For</td>
	<td colspan="3">
	<bean:write property="usage" name="c" /></td>
	</tr>
	<tr>
	<td>Location</td>
	<td><bean:write name="c" property="location"/>
	</td>
	<td>Floor</td>
	<td><bean:write name="c" property="floor"/></td>
	</tr>
	<tr>
	<td>VC Room Name</td>
	<td colspan="3"><bean:write name="c" property="roomName"/>
</td>
</tr>
	<tr>
	<td>From Date</td><td>
	<bean:write name="c" property="fromDate"/>
	</td>
	
	<td>From Time</td><td>
	<bean:write name="c" property="fromTime"/>
	</td>
	</tr>
	<tr>
	<td>To Date</td>
	<td><bean:write name="c" property="toDate"/></td>
	<td>To Time</td>
	<td><bean:write name="c" property="toTime"/> </td>
	</tr>
	<tr>
	<td>VC From</td>
	<td><bean:write name="c" property="vcFrom"/></td>
	<td>VC To</td>
	<td><bean:write name="c" property="vcTo"/> </td>
	</tr>
	<tr><td> Status</td>
	<td ><b><bean:write name="c" property="status"/></b> </td>
	<td> Date</td>
	<td ><b><bean:write name="c" property="approvedDate"/></b> </td>
	</tr>
	<tr>
	<th colspan="4">Purpose</th>
	</tr>
	<tr>
	<td colspan="4"><bean:write name="c" property="purpose"/> </td>
	</tr>
		 </logic:iterate>
	</logic:notEmpty>
		 
		 	<tr><th colspan="6">Comments </th>
			</tr>
			<tr>
			<td colspan="6"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
			
			<input type="button" class="rounded" value="Check Availability" onclick="checkAvailability()" />&nbsp;&nbsp;
		   <logic:notEmpty name="approveButton">
			<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="completedButton">
			<input type="button" class="rounded" value="Completed" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="Cancel">
			<input type="button" class="rounded" value="Cancel" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr>
	</table>
	 <logic:notEmpty name="appList">
	<div>&nbsp;</div>
    <table class="bordered">
    <tr>
    <th colspan="5"><center>Approval Status</center></th>
    </tr>
    <tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.designation }</td>
	<td>${abc.approveStatus }</td>
		<td>${abc.approveDate }</td>
	<td>${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	
						<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="requestNo"/>
            </html:form>
            
</body>
</html>

	</body>
</html>
					
		 