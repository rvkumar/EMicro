<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

 
  </script>
 </head>
 <body>
  <html:form action="/helpDeskReport.do" enctype="multipart/form-data" >
  <logic:notEmpty name="helpDeskList">
	
<div>&nbsp;</div>
	  <table class="sortable bordered" border="1" >
	  <tr><th colspan="9"><center>IT Help Desk Report</center> </th></tr>
	<tr >
<th style="background-color: #F79F81;">Req&nbsp;No</th><th style="width:100px;background-color: #F79F81;">Req Date</th><th style="width:100px;background-color: #F79F81;">Completed Date</th><th style="background-color: #F79F81;">Emp No</th><th style="background-color: #F79F81;">Req.By</th>
	<th style="background-color: #F79F81;">Request&nbsp;Type</th> <th style="background-color: #F79F81;"> Technician</th><th style="background-color: #F79F81;">Last Approver</th> <th style="background-color: #F79F81;">Pending Approver</th><th style="width:100px;background-color: #F79F81;">Status</th><th style="background-color: #F79F81;"> IT Engineer<br/> Status</th><th style="background-color: #F79F81;">Approval Date</th><th style="background-color: #F79F81;">Days</th><th style="background-color: #F79F81;">Hours</th></tr>
</tr>
	  <logic:iterate id="h" name="helpDeskList">
	 <tr>
	 			<td style="background-color: #F6CECE;"><bean:write name="h" property="requestNo"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="requestDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="compDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="empno"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="requestername"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="reqname"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="approver"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="lastApprover"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="pendingApprover"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="requestStatus"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="itEngStatus"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="approvedDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="day"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="h" property="hr"/></td>
	 
	 </tr> 
	  </logic:iterate>
	</logic:notEmpty>
  <logic:notEmpty name="listofIssues">
	<table class="bordered sortable" border="1">
	 <tr><th colspan="13" ><center>IT Issues Report</center> </th></tr>
	<tr><th style="background-color: #F79F81;">Req.No</th><th style="background-color: #F79F81;">Req.Date</th><th style="width:100px;background-color: #F79F81;">Completed Date</th><th style="background-color: #F79F81;">Location</th><th style="background-color: #F79F81;">Employee Name</th><th style="background-color: #F79F81;">Technician</th><th style="background-color: #F79F81;">Category</th><th style="background-color: #F79F81;">Sub Category</th><th style="background-color: #F79F81;">Descripton</th>
	<th style="background-color: #F79F81;">Priority</th><th style="background-color: #F79F81;">Status</th><th style="background-color: #F79F81;">Approval Date</th><th style="background-color: #F79F81;">Days</th><th style="background-color: #F79F81;">Hours</th></tr>
		<logic:iterate id="issue" name="listofIssues">
			<tr>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="requestNo"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="reqDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="compDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="location"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="employeename"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="technicianName"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="category"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="subcategory"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="subject"/></td>
				 <td width="10%;" style="background-color: #F6CECE;">
   <logic:equal value="Very High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="issue" property="reqPriority">
			            <canvas   width="10" height="15" style="border:0px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>

<bean:write name="issue" property="reqPriority"/></td>
<td style="background-color: #F6CECE;"><bean:write name="issue" property="requestStatus"/></td>
<td style="background-color: #F6CECE;"><bean:write name="issue" property="approvedDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="day"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="hr"/></td>
			</tr>
		</logic:iterate>
	</logic:notEmpty>


</table>

<logic:notEmpty name="dashboardissue">
	<table class="bordered sortable"  border="1">
	 <tr><th colspan="9" ><center>Issues Report</center> </th></tr>
	 <tr><th style="background-color: #F79F81;">Req.No</th><th style="background-color: #F79F81;">Req.Date</th><th style="width:100px;background-color: #F79F81;">Completed Date</th><th style="background-color: #F79F81;">Location</th><th style="background-color: #F79F81;">Employee Name</th><th style="background-color: #F79F81;">Technician</th><th style="background-color: #F79F81;">Category</th><th style="background-color: #F79F81;">Sub Category</th><th style="background-color: #F79F81;">Descripton</th>
	<th style="background-color: #F79F81;">Priority</th><th style="background-color: #F79F81;">Status</th><th style="background-color: #F79F81;">Approval Date</th><th style="background-color: #F79F81;">Days</th><th style="background-color: #F79F81;">Hours</th></tr>
		<logic:iterate id="issue" name="dashboardissue">
			<tr>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="requestNo"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="reqDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="compDate"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="location"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="employeename"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="technicianName"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="category"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="subcategory"/></td>
				<td style="background-color: #F6CECE;"><bean:write name="issue" property="subject"/></td>
				 <td width="10%;" style="background-color: #F6CECE;">
                         <logic:equal value="Very High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="issue" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="issue" property="reqPriority">
			            <canvas   width="10" height="15" style="border:0px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>

<bean:write name="issue" property="reqPriority"/></td>
<td style="background-color: #F6CECE;"><bean:write name="issue" property="requestStatus"/></td>
<td style="background-color: #F6CECE;"><bean:write name="issue" property="approvedDate"/></td>
<td style="background-color: #F6CECE;"><bean:write name="issue" property="day"/></td>
<td style="background-color: #F6CECE;"><bean:write name="issue" property="hr"/></td>
</tr>
		</logic:iterate>
	</logic:notEmpty>
</table>
</html:form></body></html>