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

function onClose()
{

window.history.back();

}

</script>

</head>
<body >

				
				<html:form action="/onDuty.do" enctype="multipart/form-data">
				
				<div align="center">
				<logic:present name="onDutyForm" property="message">
					<font color="red" size="3"><b><bean:write name="onDutyForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="onDutyForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="onDutyForm" property="message2" /></b></font>
				</logic:present>
			</div>
		
		<table class="bordered content" width="90%">
			 <tr><th  colspan="4" align="center">
					 On Duty Form </th>
  						<tr>
  							<td width="15%">On Duty Type <font color="red" size="3">*</font></td>
							<td width="64%" align="left">
							${onDutyForm.onDutyType }
							</td>
							<td width="15%">Plant </td>
						
							<td align="left" width="34%">
								${onDutyForm.locationId }
							</td>
						</tr>
						<tr>
							<td width="15%">From Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
							${onDutyForm.startDate }
							
							</td>
							<td width="15%">Out Time </td>
							
							<td width="34%">
								${onDutyForm.startTime }
								
							</td>
						</tr>
						<tr>
							<td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							${onDutyForm.endDate }
							</td>
							
							<td width="15%">In Time <font color="red" size="3">*</font></td>
							
							<td width="34%">
							${onDutyForm.endTime }
							
							</td>
							</tr>
							<tr><td>Req Date</td><td colspan="3">${onDutyForm.submitDate }</td></tr>
						<tr>
				<th colspan="4"> Reason<font color="red" size="2">*</font> : 
						</th>
						</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="10" value="${onDutyForm.reason }"></html:textarea>
						
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
			<td colspan="6">${onDutyForm.remark}</td></tr>
			<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="onClose()"  /></td>
			
			</tr>
	</table>
	
	 <logic:notEmpty name="appList">
		 <div align="left" class="bordered ">
			<table width="100%"   class="sortable">
			<tr>
				<th style="text-align:left;"><b>Type</b></th>
				<th style="text-align:left;"><b>Emp Name</b></th>	
				<th style="text-align:left;"><b>Designation</b></th>
				<th style="text-align:left;"><b>Status</b></th>
				<th style="text-align:left;"><b>Date</b></th>
					<th style="text-align:left;"><b>Comments</b></th>
			</tr>
			<logic:iterate id="abc" name="appList">
			<tr>
			
			<td>${abc.appType}</td>
			<td>${abc.approverName}</td>
			<td>${abc.appDesig}</td>
			<td>&nbsp;${abc.status }</td>
			<td>&nbsp;${abc.approvedDate }</td>
			<td>&nbsp;${abc.comments }</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
		
		<logic:notEmpty name="onduty">
	
	
	
    <table class="bordered"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="onduty">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.appDesig }</td>
	<td>&nbsp;${abc.status }</td>
	<td>&nbsp;${abc.approvedDate }</td>
	<td>&nbsp;${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
				
            </html:form>
</body>
</html>

	</body>
</html>
					
			