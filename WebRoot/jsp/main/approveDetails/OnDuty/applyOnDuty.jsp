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

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<title>Home Page</title>

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
	var url="approvals.do?method=statusChangeOnDuty&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;

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
		
		<table class="bordered content" width="90%">
			 <tr><th  colspan="4" align="center">
					 <center>On Duty Form</center> </th></tr>
					 					<tr><th colspan="4">Requester Details</th></tr>
						<tr><td>Employee Number</td><td><bean:write name="approvalsForm" property="employeeNumber" /></td><td>Employee Name</td><td><bean:write name="approvalsForm" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="approvalsForm" property="department" /></td><td>Designation</td><td><bean:write name="approvalsForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan="3"><bean:write name="approvalsForm" property="date" /></td></tr>
  					
  					<tr><th colspan="4">On Duty Details</th></tr>
  						<tr>
  							<td width="15%">On Duty Type <font color="red" size="3">*</font></td>
							<td width="64%" align="left" >
							${approvalsForm.onDutyType }
							</td>
							<td width="15%">Plant <font color="red" size="3">*</font></td>
							<td width="64%" align="left" >
							${approvalsForm.locationId }
							</td>
						</tr>
						<tr>
							<td width="15%">From Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
							${approvalsForm.startDate }
							
							</td>
							<td width="15%">From Time <font color="red" size="3">*</font></td>
							
							<td width="34%">
								${approvalsForm.startTime }
								
							</td>
						</tr>
						<tr>
							<td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							${approvalsForm.endDate }
							</td>
							
							<td width="15%">To Time <font color="red" size="3">*</font></td>
							
							<td width="34%">
							${approvalsForm.endTime }
							
							</td>
							</tr>
								<tr><td>Req Date</td><td colspan="3">${approvalsForm.requestDate }</td></tr>
								
						<tr>
				<th colspan="4"> Detailed Purpose<font color="red" size="2">*</font> : 
						</th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="10" value="${approvalsForm.reason }"></html:textarea>
						
							</td>
						</tr>

						<logic:notEmpty name="documentDetails">
						<th colspan="4">Uploaded Documents </th>
						</tr>
						

						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/ESS/On Duty/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
							
							
						</logic:notEmpty>
						
					   
		          	<tr><th colspan="6">Comments </th>
			</tr>
			<tr>
			<td colspan="6"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr>
	</table>
	
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
			<br/>
	<logic:notEmpty name="onduty">
	
	
	
    <table class="bordered"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="onduty">
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

	</body>
</html>
					
			