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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
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
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<title>Home Page</title>
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
<script language="javascript">
function changeStatus(elem){

  
	var elemValue = elem.value;
	
	if(elemValue=="Reject")
	{
		if(document.forms[0].remark.value==""){
		alert("Please Add Some Comments");
		 document.forms[0].remark.focus();
		 return false;
		}
	}
	var reqId = document.getElementById("reqId").value;;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;

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


</script>

</head>
<body >

				
				<html:form action="/approvals.do" enctype="multipart/form-data">
				
				<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
<logic:notEmpty name="details">
<div style="width="80%">
		<logic:iterate name="details" id="detailsView">
			<table class="bordered content" width="80%">
				<tr>
					<th colspan="6" style="text-align: center;"><big>Leave </big></th> 
						</tr>
						<tr><th colspan="4">Requester Details</th></tr>
						<tr><td>Employee Number</td><td><bean:write name="detailsView" property="employeeNumber" /></td><td>Employee Name</td><td><bean:write name="detailsView" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="detailsView" property="department" /></td><td>Designation</td><td><bean:write name="detailsView" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan="3"><bean:write name="detailsView" property="doj" /></td></tr>
				<tr><th colspan="6">Leave Details</th></tr>		
			<tr>
						<td width="15%">Leave Type <font color="red" size="4">*</font></td>
				<td width="64%" align="left" >
					${detailsView.leaveType}
				</td>
				<td>Request date</td>
				<td>		${approvalsForm.requestDate}</td>
			</tr>
			<tr>
							<td width="15%">From Date <font color="red" size="4">*</font></td>
							<td align="left" width="34%">
								${detailsView.startDate}
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="34%">
							${detailsView.startDurationType}
							</td>
						</tr>
						<tr>
							<td width="15%">To Date <font color="red" size="4">*</font></td>
							<td align="left" width="34%">
								${detailsView.endDate}
							</td>
							<td width="15%">Duration <font color="red" size="4">*</font></td>
							<td width="34%">
								${detailsView.endDurationType}
							</td>
						</tr>
						
						<tr>
							<td width="15%">No of Days <font color="red" size="4">*</font></td>
							<td align="left"  width="30%">
							${detailsView.noOfDays}
							</td>
							
						
							<td >Reason Type <font color="red" size="4">*</font></td>
							<td width="34%">
								
								<html:select name="detailsView" property="reasonType" styleClass="content" disabled="true" >
									<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="leaveReason" labelProperty="leaveDetReason"/>
								</html:select>
							</td>
						</tr>	
							
						<tr><th colspan="6"><big>Detailed Reason <font color="red" size="3">*</font></big></th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
								${detailsView.reason}
						
							</td>
						</tr>
					<logic:notEmpty name="fileList">
					<tr><th colspan="6"><big>Documents <font color="red" size="3">*</font></big></th>
					<logic:iterate id="list" name="fileList">
					<tr><td colspan="5">
					<a href="/EMicro Files/ESS/Leave/${list.fileName}" target="_blank">${list.fileName}</a>
					</td></tr>
					</logic:iterate>
						  
				      </logic:notEmpty>
				
	
		</logic:iterate>
		</div>
</logic:notEmpty>	
	
					



			<tr><th colspan="6">Comments </th>
			</tr>
			<tr>
			<td colspan="6">
			<logic:notEmpty name="commentField">
			<html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text>
			</logic:notEmpty>
			<logic:empty name="commentField" >
			<bean:write name="approvalsForm" property="remark"/>
			
			</logic:empty>
			
			
			</td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr>
	</table>
	
	<br/>	
	<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" id="bal">
		<table class="bordered" width="90%">
			<tr>
				<th colspan="6" align="left">Leave Balances </b></big></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th><th>Previous Year Availed (<bean:write name="approvalsForm" property="mode"/>)</th>
			</tr>
		
			<logic:iterate id="bal" name="LeaveBalenceList">
						<tr>
						<th><bean:write name="bal" property="leaveType"/></th>
						<td><bean:write name="bal" property="openingBalence"/></td>
						<td><bean:write name="bal" property="avalableBalence"/></td>
						<td><bean:write name="bal" property="closingBalence"/></td>
						<td><bean:write name="bal" property="awaitingBalence"/></td>
						<td><bean:write name="bal" property="cl"/></td>
						</tr>	
			</logic:iterate>	
		</table>
		</div>
		</logic:notEmpty>
		
		<br/>
	
		<logic:notEmpty name="le">
    <table class="bordered">
    <tr><th colspan="5"><center>Last Leave Details</center></th></tr>
    <tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No. Of Days</th><th>Approved Date</th></tr> 
   	<logic:iterate id="abc" name="le">
	<tr>
	<td>${abc.leaveType }</td>
	<td>${abc.startDate }</td>
	<td>${abc.endDate }</td>
	<td>${abc.noOfDays }</td>
	<td>${abc.approvedDate }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	
	<logic:notEmpty name="nole">
    <table class="bordered">
    <tr><th colspan="5"><center>Last Leave Details</center></th></tr>
    <tr><th>Leave Type</th><th>From Date</th><th>To Date</th><th>No. Of Days</th><th>Approved Date</th></tr> 
   	<tr><td colspan="5"> <center><font color="red">No Leaves Applied.</font></center></td></tr>
	</table>
	</logic:notEmpty>
	
	
	
	
	<br/>
	
	
	<logic:notEmpty name="leave">
    <table class="bordered"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="parallelapprovers">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.appDesig }</td>
	<td>${abc.status }</td>
	<td>${abc.approvedDate }</td>
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
            </html:form>
</body>
</html>