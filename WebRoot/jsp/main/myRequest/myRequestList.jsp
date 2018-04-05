<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>
<jsp:directive.page import="org.apache.struts.action.ActionForm" />
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: My Request Display </title>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/sorttable.js"></script>

<script type="text/javascript">


function searchRequiredRecord()
{

	
   if(document.forms[0].reqStatus.value!="" && document.forms[0].reqRequstType.value!=""){
	document.forms[0].action="myRequest.do?method=displayMyRequestList";
	document.forms[0].submit();
	}

}
function nextRecord()
{
     
	document.forms[0].action="myRequest.do?method=next";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="myRequest.do?method=prev";
	document.forms[0].submit();

}
function lastRecord()
{
    
	document.forms[0].action="myRequest.do?method=last";
	document.forms[0].submit();

}
function firstRecord()
{
     
	document.forms[0].action="myRequest.do?method=first";
	document.forms[0].submit();

}

function searchInMyRequest(type){
	var filter = document.getElementById("filterId").value;
	var requestId = document.getElementById("requestSelectId").value;
	if(type == "clear"){
		var url="myRequest.do?method=displayMyRequestList";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else{
		var sCount = document.getElementById("scnt").value;
   		var eCount = document.getElementById("ecnt").value;
		var srcTxt = document.getElementById("searchText").value;
		document.getElementById("sValue").value = srcTxt;
		var url="myRequest.do?method=searchInMyRequest&filter="+filter+"&searchTxt="+srcTxt+"&sCount=0&eCount=0";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
}


function exportReport()
{
    
	document.forms[0].action="myRequest.do?method=exportdisplayMyRequestList";
	document.forms[0].submit();

}


</script>
<%--<!-- import the language module -->--%>
<%--<script type="text/javascript" src="calender/js/calendar-en.js"></script>--%>
<%--<!-- other languages might be available in the lang directory; please check your distribution archive. -->--%>
<%--<!-- ALL demos need these css -->--%>
<%--<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>--%>
<%----%>
<%--<!-- Theme css -->--%>
<%--<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>--%>
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
</head>
<body style="text-transform:uppercase">
	<div id="wraper">   
    	<div style="padding-left:15px; width: 100%;" class="content_middle"><!--------CONTENT MIDDLE STARTS -------------------->
					
				<html:form action="/myRequest.do" enctype="multipart/form-data">
				
		 		<table class="bordered">
					<tr>
						<th colspan="4"><big>My Request</big></th>
					</tr>          			
      				<tr>
						<td>Request Type <font color="red">*</font></td>
						<td><html:select property="reqRequstType" styleClass="content" styleId="requestSelectId" onchange="searchRequiredRecord()">
								<html:option value="">--Select--</html:option>
							    <html:option value="Material Master">Material Master</html:option>
								<html:option value="Customer Master">Customer Master</html:option>
								<html:option value="Vendor Master">Vendor Master</html:option>
								<html:option value="Service Master">Service Master</html:option>
								<html:option value="Code Extention">Code Extention</html:option>
								<html:option value="Leave">Leave</html:option>	
								<html:option value="On Duty">On Duty</html:option>
								<html:option value="Permission">Permission</html:option>
								 <html:option value="IT Request">IT Request</html:option>
								 <html:option value="Conference">Conference Room</html:option>
								 <html:option value="VC Room">VC Room</html:option>
								 <html:option value="SAP Issues">SAP Issues</html:option>
							</html:select>
						</td>
									
						<td>Filter by <font color="red">*</font></td>
						<td><html:select property="reqStatus" styleClass="content" styleId="filterId" onchange="searchRequiredRecord()">
							<html:option value="">--Select--</html:option>
							<html:option value="Pending">Pending</html:option>
							<html:option value="Created">Created</html:option>
							<html:option value="Submited">Submited</html:option>
							<html:option value="In Process">In Process</html:option>
							<html:option value="Approved">Approved</html:option>
							<html:option value="Rejected">Rejected</html:option>
							<html:option value="Completed">Completed</html:option>
							<html:option value="Cancelled">Cancelled</html:option>
							<html:option value="All">All</html:option>
							</html:select>
						</td>
					</tr>
         			</table>
				<div>&nbsp;</div>
				<div>&nbsp;</div>
				
				<logic:notEmpty name="Leave List">
				<logic:empty name="noRecords">
			
				<html:button property="method" value="Export To Excel" onclick="exportReport()" styleClass="rounded" />
				</logic:empty>
				</logic:notEmpty>
				
				<logic:notEmpty name="ondutyList">
				<logic:empty name="noRecords">
			
				<html:button property="method" value="Export To Excel" onclick="exportReport()" styleClass="rounded" />
				</logic:empty>
				</logic:notEmpty>
				
									<div>&nbsp;</div>
				
         			<div align="center">
         			<table>
						<tr><td align="center">
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="myRequestForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="myRequestForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								<!--<td style="align:right;text-align:center;">
									<img src="images/clear.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('clear');" width="25" height="25" />
									<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in MyRequest" onmousedown="this.value='';"/>
									<img src="images/search-bg.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('search')" width="40" height="50" />
								</td>
							-->
								</logic:notEmpty>
								</td>
							</tr>
						</table>
						</div>
								<logic:notEmpty name="sapit">
          		   
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Request Type</th>
							<th>SAP Request Type</th>
							<th>Priority</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
							<th> Approval Status</th>

							<th>View</th>
						</tr>
						
						
					<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.itReqNo }</td>
							<td><bean:write name="abc" property="itReqDate" /></td>
							
							<td> <bean:write name="abc" property="requestType" /></td>
								<td> <bean:write name="abc" property="requestName" /></td>
			            <td width="10%;">    
			          <logic:equal value="High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="abc" property="reqPriority" />
			             </td>
			                  <td><bean:write name="abc" property="lastApprover" /></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="reqStatus"/></td>
      						
      						<td id="${abc.itReqNo}">
      						
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.itReqNo}&type=${abc.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</table>
					</logic:notEmpty>
				<logic:notEmpty name="vcList">
				    <table class="sortable bordered">
				     <tr>
				     <th>Req.No</th><th>Requested On</th><th>Location</th><th>Floor</th><th>VC Room</th><th>From Date</th><th>To Date</th>
				     <th>Last Approver</th><th>Pending Approver</th><th>Approval Status</th><th>View</th>
				     </tr>
					<logic:iterate id="c" name="myRequestList">
					<tr>
						<td>${c.reqNo }</td>
						<td>${c.submitDate }</td>
						<td>${c.location }</td>
						<td>${c.floor }</td>
						<td>${c.roomName }</td>
						<td>${c.fromDate }</td>
						<td>${c.toDate }</td>
						<td>${c.lastApprover }</td>
						<td>${c.pendingApprover }</td>
						<td>${c.approvalStatus }</td>
						<td>
						<a href="myRequest.do?method=editMyRequest&requstNo=${c.reqNo}&type=${c.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
					</tr>
					</logic:iterate>
					</table>
				</logic:notEmpty>
				
				<logic:notEmpty name="confereceList">
				    <table class="sortable bordered" >
				     <tr>
				     <th>Req.No</th><th>Requested On</th><th>Location</th><th>Floor</th><th>Conf.Room</th><th>From Date</th><th>To Date</th>
				     <th>Last Approver</th><th>Pending Approver</th><th>Approval Status</th><th>View</th>
				     </tr>
					<logic:iterate id="c" name="confereceList">
					<tr>
						<td>${c.reqNo }</td>
						<td>${c.submitDate }</td>
						<td>${c.location }</td>
						<td>${c.floor }</td>
						<td>${c.roomName }</td>
						<td>${c.fromDate }</td>
						<td>${c.toDate }</td>
						<td>${c.lastApprover }</td>
						<td>${c.pendingApprover }</td>
						<td>${c.approvalStatus }</td>
						<td>
						<a href="myRequest.do?method=editMyRequest&requstNo=${c.reqNo}&type=${c.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
					</tr>
					</logic:iterate>
					</table>
				</logic:notEmpty>		
				
				<logic:notEmpty name="it">
          		   
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Request Type</th>
							<th>Priority</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
							<th> Approval Status</th>
							<th> IT Engineer Status</th>
							<th>View</th>
						</tr>
						
						
					<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.itReqNo }</td>
							<td><bean:write name="abc" property="itReqDate" /></td>
							
							<td> <bean:write name="abc" property="requestType" /></td>
			            <td width="10%;">    
			          <logic:equal value="High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="abc" property="reqPriority" />
			             </td>
			                  <td><bean:write name="abc" property="lastApprover" /></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="reqStatus"/></td>
      						<td><bean:write name="abc" property="itEngStatus"/></td>
      						<td id="${abc.itReqNo}">
      						
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.itReqNo}&type=${abc.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					
					</logic:notEmpty>		
					
					
					
						<logic:notEmpty name="ondutyList">
			<table class="sortable bordered">
			
				<tr>
							<th style="text-align:left;"><b>Req No.</b></th>
							<th style="text-align:left;"><b>Type</b></th>
							<th style="text-align:left;"><b>Plant</b></th>
							<th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th>			
							<th style="text-align:left;"><b>To Date</b></th>
							<th class="specalt" align="center"><b>To Time</b></th>
							<th class="specalt" align="center"><b>Approver</b></th>
							<th style="text-align:left;"><b>Status</b></th>
							<th>Edit</th>
						</tr>
				<logic:iterate id="mytable1" name="myRequestList">
									<tr >
										<td>
				<bean:write name="mytable1" property="requestNumber"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="onDutyType"/>
				</td>
				<td>
				<bean:write name="mytable1" property="locationName"/>
				</td>
				<td>
				<bean:write name="mytable1" property="startDate"/>
				</td>
					<td>
				<bean:write name="mytable1" property="startTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="endDate"/>
				</td>
					<td>
				<bean:write name="mytable1" property="endTime"/>
				</td>
				<td>
				<bean:write name="mytable1" property="pendingApprover"/>
				</td>
				<td>
				<bean:write name="mytable1" property="status"/>
				</td>
				<td>
				<a href="myRequest.do?method=editMyRequest&requestNo=${mytable1.requestNumber}&type=OnDuty">
				<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
				</td>
									</tr>
				</logic:iterate>
				</table>
		</logic:notEmpty>
		
						
          			<logic:notEmpty name="permission">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Request Type</th>
							<th>From Time</th>
						   <th>To Time</th>
						      <th>Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td>Permission</td>
							<td> <bean:write name="abc" property="startTime" /></td>
			            
			                  <td><bean:write name="abc" property="endTime" /></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=${abc.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
          			<logic:notEmpty name="customer  list">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Material Type</th>
							
						   <th> Name</th>
						    <th> City</th>
						    <th>Last Approver</th>
						      <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="requestType"/></td>
			             <td><bean:write name="abc" property="vendorName"/></td>
			                 <td><bean:write name="abc" property="locationName"/></td>
			                 <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=${abc.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
						
						
          			<logic:notEmpty name="vendor  list">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Vendor Name</th>
							<th>City</th>
							<th>Vendor Type</th>
						  
						    <th>Last Approver</th>
						      <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="vendorName"/></td>
			            <td><bean:write name="abc" property="locationName"/></td>
			            <td><bean:write name="abc" property="vendorType"/></td>
			                 <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=${abc.requestType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
						
          			<logic:notEmpty name="Code Extention">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Request Type</th>
							<th>Requested On</th>
								<th>TYPE</th>
								<th>Material</th>
								<th>Description</th>
								
									<th>From Storage Location</th>
										<th>To Storage Location</th>
							<th>Last Approver</th>
							<th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="plantType" /></td>
							<td><bean:write name="abc" property="sapNo" /></td>
							<td><bean:write name="abc" property="description" /></td>
							
							
							<td><bean:write name="abc" property="fromStorage" /></td>
							<td><bean:write name="abc" property="toStorage" /></td>
							<td><bean:write name="abc" property="lastApprover"/></td>
							<td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      							<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=${abc.requestType}&planttype=${abc.plantType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
					
          			<logic:notEmpty name="Service List">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Request Type</th>
							<th>Requested On</th>
							<th>Service Description</th>		
							<th>Last Approver</th>
							<th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="serviceDescription" /></td>
							<td><bean:write name="abc" property="lastApprover"/></td>
      						<td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=Service Master">
      						<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>

          			<logic:notEmpty name="myRequestList">

          			<logic:notEmpty name="Leave List">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Request Type</th>
							<th>Requested On</th>
							<th>From Date</th>
							<th>Duration</th>
							<th>To Date</th>
							<th>Duration</th>		
							<th>Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="startDate" /></td>
							<td><bean:write name="abc" property="startDateDuration" /></td>
							<td><bean:write name="abc" property="endDate" /></td>
							<td><bean:write name="abc" property="endDateDuration" /></td>
							<td><bean:write name="abc" property="approver"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      							<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=Leave">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
					
				
					
          			<logic:notEmpty name="Material code list">
          		   
					<table class="sortable bordered">
						<tr>
							<th>Req. No</th>
							<th>Material Type</th>
							<th>Material Name</th>
							<th>Requested On</th>
							<th>Location Code</th>
						    <th>Last Approver</th>
						      <th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
						</tr>
						
						
						<logic:iterate name="myRequestList" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.requestNumber }</td>
							<td><bean:write name="abc" property="requestType"/></td>
							<td><bean:write name="abc" property="matshortname"/></td>
							<td><bean:write name="abc" property="createDate" /></td>
							<td><bean:write name="abc" property="locationName" /></td>
							
						   <td><bean:write name="abc" property="lastApprover"/></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="approvalStatus"/></td>
      						<td id="${abc.requestNumber}">
      						
      						<a href="myRequest.do?method=editMyRequest&requstNo=${abc.requestNumber}&type=${abc.reqMaterialType}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>					
					</table>
					
					
					
					</logic:notEmpty>		
					
					<logic:notEmpty name="noRecords">
					<table class="bordered">
					<tr>
					<logic:equal value="Material Master" property="reqRequstType" name="myRequestForm">
					<th>Req. No</th><th>Material Type</th><th>Requested On</th><th>Location Code</th><th>Last Approver</th><th>Pending Approver</th>
					<th>Status</th><th>View</th>
					</logic:equal>
					<logic:equal value="SAP Issues" property="reqRequstType" name="myRequestForm">
					<tr>
							<th>Req. No</th>
							<th>Requested On</th>
							<th>Request Type</th>
							<th>Priority</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
							<th> Approval Status</th>

							<th>View</th>
						</tr>
					</logic:equal>
					<logic:equal value="Service Master" property="reqRequstType" name="myRequestForm">
					<th>Req. No</th>
							<th>Request Type</th>
							<th>Requested On</th>
							<th>Service Description</th>		
							<th>Last Approver</th>
							<th>Pending Approver</th>
							<th>Status</th>
							<th>View</th>
					</logic:equal>
					<logic:equal value="Code Extention" property="reqRequstType" name="myRequestForm">
					<th>Req. No</th><th>Request Type</th><th>Requested On</th><th>TYPE</th><th>Material</th><th>Description</th><th>From Storage Location</th>
					<th>To Storage Location</th><th>Last Approver</th><th>Pending Approver</th><th>Status</th><th>View</th>
					</logic:equal>
					<logic:equal value="Leave" property="reqRequstType" name="myRequestForm">
					<th>Req. No</th><th>Request Type</th><th>Requested On</th><th>From Date</th><th>Duration</th><th>To Date</th><th>Duration</th>		
							<th>Approver</th><th>Status</th><th>View</th>
					</logic:equal>
					<logic:equal value="On Duty" property="reqRequstType" name="myRequestForm">
					<th ><b>Req No.</b></th><th ><b>Type</b></th><th style="text-align:left;"><b>Plant</b></th><th class="specalt" align="center"><b>From Date</b></th>
							<th class="specalt" align="center"><b>From Time</b></th><th style="text-align:left;"><b>To Date</b></th><th class="specalt" align="center"><b>To Time</b></th>
							<th class="specalt" align="center"><b>Approver</b></th><th style="text-align:left;"><b>Status</b></th><th>Edit</th>
					</logic:equal>
					<logic:equal value="Permission" property="reqRequstType" name="myRequestForm">
					<th>Req. No</th><th>Requested On</th><th>Request Type</th><th>From Time</th> <th>To Time</th><th>Approver</th><th>Status</th><th>View</th>
					</logic:equal>
					<logic:equal value="IT Request" property="reqRequstType" name="myRequestForm">
							<th>Req. No</th><th>Requested On</th><th>Request Type</th><th>Priority</th><th>Last Approver</th><th>Pending Approver</th><th> Approval Status</th>
							<th> IT Engineer Status</th><th>View</th>
					</logic:equal>
					<logic:equal value="Conference" property="reqRequstType" name="myRequestForm">
					 <th>Req.No</th><th>Requested On</th><th>Location</th><th>Floor</th><th>Conf.Room</th><th>From Date</th><th>To Date</th>
				     <th>Last Approver</th><th>Pending Approver</th><th>Approval Status</th><th>View</th>
					</logic:equal>
					<logic:equal value="VC Room" property="reqRequstType" name="myRequestForm">
					 <th>Req.No</th><th>Requested On</th><th>Location</th><th>Floor</th><th>VC Room</th><th>From Date</th><th>To Date</th>
				     <th>Last Approver</th><th>Pending Approver</th><th>Approval Status</th><th>View</th>
					</logic:equal>
					<tr>
					<td colspan="10">
					<div align="center">
						<font color="red" size="3"><b>Searched details could not be found.</b></font>
					</div>
					</td></tr></table>
					</logic:notEmpty>

          		 <html:hidden name="myRequestForm" property="total"/>
 				<html:hidden name="myRequestForm" property="next"/>
 				<html:hidden name="myRequestForm" property="prev"/>
 				<input style="visibility:hidden;" id="scnt" value="<bean:write property="startRecord"  name="myRequestForm"/>"/>
				<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endRecord"  name="myRequestForm"/>"/>
				<input style="visibility:hidden;" id="reqId" value="<bean:write name="myRequestForm" property="requestNumber"/>"/>
				<input style="visibility:hidden;" id="reqType" value="<bean:write name="myRequestForm" property="requestType"/>"/>
				<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="myRequestForm"/>"/>
          		</html:form>
         </div>
    </div>
</body>
</html>
