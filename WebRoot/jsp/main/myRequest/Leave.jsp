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
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />


<title>eMicro :: Leave_Record_Display</title>
<script language="javascript">
function onBack(){
}
</script>

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
<body >
		<div align="center">
			<logic:present name="leaveForm" property="message">
				<font color="red"><bean:write name="leaveForm" property="message" /></font>
			</logic:present>
		</div>
		<html:form action="leave" enctype="multipart/form-data">
			<logic:iterate id="leaveForm" name="leaveDetails">
				<html:hidden property="id"/>

					<div style="width="80%">
						<table class="bordered" width="80%">
							<tr>
								<th colspan="4" style="text-align: center;"><big>Leave Record Display</big></th> 
  							</tr>
							<tr>
  								<td><b>Type</b></td>
								<td><bean:write name="leaveForm" property="leaveType"/></td>
  								<td><b>Applied Date</b></td>
								<td><bean:write name="leaveForm" property="submitDate"/></td>
							</tr>
							
							<tr>
								<td><b>From Date</b></td>
								<td><bean:write name="leaveForm" property="startDate"/></td>
								<td><b>Duration</b></td>
								<td>
									<html:select name="leaveForm" property="startDurationType" styleClass="content" onchange="clearEndDate()" disabled="true">
										<html:option value="">--Select--</html:option>
										<html:option value="FD">Full Day</html:option>
										<html:option value="FH">First Half</html:option>
										<html:option value="SH">Second Half</html:option>
									</html:select>
								</td>
							</tr>

						<tr>
							<td><b>To Date</b></td>
							<td><bean:write name="leaveForm" property="endDate"/></td>
							<td><b>Duration</b></td>
							<td>
								<html:select name="leaveForm" property="endDurationType" styleClass="content" onchange="calculateDays()" disabled="true">
									<html:option value="">--Select--</html:option>
									<html:option value="FD">Full Day</html:option>
									<html:option value="FH">First Half</html:option>
									<html:option value="SH">Second Half</html:option>
								</html:select>
							</td>
						</tr>
						
						<tr>
							<td><b>No of Days</b></td>
							<td>
								<div id="noOfDaysDiv"><bean:write name="leaveForm" property="noOfDays"/></div>
							</td>
							<td><b>Reason Type</b></td>
							<td>
								<html:select name="leaveForm" property="reasonType" styleClass="content" disabled="true">
									<html:option value="">--Select--</html:option>
							<html:options name="leaveForm" property="leaveReason" labelProperty="leaveDetReason"/>
								</html:select>
							</td>
						</tr>	

						<tr>
							<th colspan="4">Detailed Reason</th>
						</tr>

						<tr>	
							<td colspan="4"><bean:write name="leaveForm" property="reason"/></td>
						</tr>
					</logic:iterate>
				
					<logic:notEmpty name="documentDetails">
						<tr>
							<th colspan="4">Uploaded Documents</th>
						</tr>
					<logic:notEmpty name="documentDetails">
				    	<tr>
							<td align="left" class="lft style1" colspan="4">
								<bean:define id="file" name="leaveForm" property="documentName" />
								    <%
										String s = file.toString();
										String v[] = s.split(",");
										int l = v.length;
										for (int i = 0; i < l; i++) 
										{
									%>
									<a href="/EMicro Files/ESS/Leave/<%=v[i]%>" target="_blank"><%=v[i]%></a>
									<%
									}
									%>	
							</td>
						</tr>	
						</logic:notEmpty>
					</logic:notEmpty>
			
					
					<tr>
						<td colspan="4">
							<html:button property="method" value="Close" onclick="history.back(-1)" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
					</tr>
				</table>
				
				
</br>
<logic:notEmpty name="leave">
    <table class="bordered content"><tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="parallelapprovers">
	<tr>
	<td>${abc.approver }&nbsp;</td>
	<td>${abc.appDesig }&nbsp;</td>
	<td>${abc.status }&nbsp;</td>
	<td>${abc.approvedDate }&nbsp;</td>
	<td>${abc.comments }&nbsp;</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
			</div>
		</html:form>
</body>
</html>