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

function changeStatus(id,type,status){

var name=document.forms[0].employeeName.value;
var ids=document.forms[0].id.value;
		
		
		var r = confirm("Are you sure you want to "+status+" : "+name+" request with Request No. "+ids+"");
if (r == true) {

   document.forms[0].action="hrApprove.do?method=cancelondutyRequest&ReqNo="+id+"&ReqType="+type+"&status="+status;
document.forms[0].submit();
    
} else {
    return false;
}
	
		
	}
	
	</script>


</script>

</head>
<body >

				
				<html:form action="/approvals.do" enctype="multipart/form-data">
			<html:hidden property="id" name="hrApprovalForm"/>
	<html:hidden property="employeeName" name="hrApprovalForm"/>
				<div align="center">
				<logic:present name="hrApprovalForm" property="message">
					<font color="red" size="3"><b><bean:write name="hrApprovalForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="hrApprovalForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="hrApprovalForm" property="message2" /></b></font>
				</logic:present>
			</div>
			
										<logic:notEmpty name="attDataList" >
 <div class="bordered" id="personalInformation"  align="center" width="80%" >
	    
	    	<center> PH: Paid Holiday &nbsp;&nbsp; WO: Weekly Off &nbsp;&nbsp;SL : Sick Leave &nbsp;&nbsp;&nbsp; EL : Earned Leave &nbsp;&nbsp;&nbsp;  CL : Casual Leave &nbsp;&nbsp;&nbsp;ML: Maternity Leave &nbsp;&nbsp;&nbsp;<br/>LWP: Leave Without Pay &nbsp;&nbsp;&nbsp;OD: OnDuty
	    	</small></center>
	    	
<div align="center">
<table class="sortable" width="80%" align="center"  >
<tr><th colspan="7"><center>Attendance Details</center></th></tr>
<tr><th width="15%"><center>Date</center></th><th width="15%"><center>Day</center></th><th width="15%"><center>In Time</center></th><th width="15%"><center>Out Time</center></th><th width="15%"><center>In Status</center></th><th width="15%"><center>Out Status</center></th><th><center>Note</center></th></tr>
<logic:iterate id="abc" name="attDataList">

<c:choose>
<c:when test="${abc.day=='Sun'}">
<tr style="background-color: #7CB1C9">
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td>&nbsp;</td>
  
  
</tr>
</c:when>

<c:when test="${abc.day=='Sat'}">
<logic:equal value="WO" name="abc" property="iNTIME">
<tr style="background-color: #7CB1C9">
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td>&nbsp;</td>
  
  
</tr>
</logic:equal>
<logic:notEqual value="WO" name="abc" property="iNTIME">
<tr >
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="remarks"/></td>
  
  
</tr>
</logic:notEqual>
</c:when>
<c:otherwise>
<logic:empty name="abc" property="message" >
<tr >
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="remarks"/></td>

</tr>
</logic:empty>
<logic:notEmpty name="abc" property="message">
<tr style="background-color: #E1A15D;">
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="remarks"/></td>
</tr>
</logic:notEmpty>
</c:otherwise>
</c:choose>

</logic:iterate>
<tr></tr>

</table></div></div>



</logic:notEmpty>
<br/>
		
		<table class="bordered content" width="90%">
			 <tr><th  colspan="4" align="center">
					 <center>On Duty Form</center> </th></tr>
					 					<tr><th colspan="4">Requester Details</th></tr>
						<tr><td>Employee Number</td><td><bean:write name="hrApprovalForm" property="employeeNumber" /></td><td>Employee Name</td><td><bean:write name="hrApprovalForm" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="hrApprovalForm" property="department" /></td><td>Designation</td><td><bean:write name="hrApprovalForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan="3"><bean:write name="hrApprovalForm" property="date" /></td></tr>
  					
  					<tr><th colspan="4">On Duty Details</th></tr>
  						<tr>
  							<td width="15%">On Duty Type <font color="red" size="3">*</font></td>
							<td width="64%" align="left" >
							${hrApprovalForm.onDutyType }
							</td>
							<td width="15%">Plant <font color="red" size="3">*</font></td>
							<td width="64%" align="left" >
							${hrApprovalForm.locationId }
							</td>
						</tr>
						<tr>
							<td width="15%">From Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
							${hrApprovalForm.startDate }
							
							</td>
							<td width="15%">From Time <font color="red" size="3">*</font></td>
							
							<td width="34%">
								${hrApprovalForm.startTime }
								
							</td>
						</tr>
						<tr>
							<td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							${hrApprovalForm.endDate }
							</td>
							
							<td width="15%">To Time <font color="red" size="3">*</font></td>
							
							<td width="34%">
							${hrApprovalForm.endTime }
							
							</td>
							</tr>
								
						<tr>
				<th colspan="4"> Detailed Purpose<font color="red" size="2">*</font> : 
						</th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="6" value="${hrApprovalForm.reason }"></html:textarea>
						
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
						
					   
		          	<tr><th colspan="6">User Remarks </th>
			</tr>
						<tr><td colspan="4">	<html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent" disabled="true" name="hrApprovalForm"></html:text>
						</td></tr>
						
						<tr><th colspan="6">Comments </th>
			</tr>
						<tr><td colspan="4">	<html:textarea property="comments" styleClass="text_field" style="width:100%" styleId="remarkContent" name="hrApprovalForm"></html:textarea>
						</td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
<tr>
					
						<td colspan="4" ><center>
				 <logic:notEqual value="2" property="status" name="hrApprovalForm"> <logic:notEmpty name="cancelbutton"><html:button property="method" value="Cancel Onduty" styleClass="rounded" style="width: 100px" onclick="changeStatus('${hrApprovalForm.id}','On Duty',this.value)"/>		
				 <html:button property="method" value="Reject" styleClass="rounded" style="width: 100px" onclick="changeStatus('${hrApprovalForm.id}','On Duty',this.value)"/>
				 </logic:notEmpty></logic:notEqual>			
	             <logic:notEmpty name="cancelbutton"> <html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="window.history.go(-1)"/></logic:notEmpty>
	                  <logic:empty name="cancelbutton"> <html:button property="method" value="Close" styleClass="rounded" style="width: 100px" onclick="window.history.go(-2)"/></logic:empty>	</center></td>
				</tr>
			
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
					
			