

<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
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
<big><b><center>
Micro Labs Limited,<bean:write name="hrApprovalForm" property="locationId"/><br/>
Daily Arrival Report for the date :<bean:write name="hrApprovalForm" property="fromDate"/></center></b></big>


</div>
&nbsp;&nbsp;
<table class="bordered" border="1">
<tr><th>Sl.no</th><th>Emp Code</th><th>Shift</th><th>Dept</th><th>Sub_Dept</th><th>Rep grp</th><th>Emp Name</th><th>Designation</th><th>Arrival</th><th>Late Hrs</th><th>Status</th>
</tr>
 <%int i=0;%>
<logic:notEmpty name="deptlist">
<logic:iterate id="b" name="deptlist">
<tr>
<td colspan="10">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>
<b>Subdepartment</b> :   <bean:write name="b" property="deptTo" /><br/>
<b>Rep grp   </b>   :   <bean:write name="b" property="desgTo" />
</td>

</tr>



<logic:iterate id="a" name="emplist">
 
<logic:equal value="${a.department}" name="b" property="department">
<logic:equal value="${a.subdepartment}" name="b" property="subdepartment">
<logic:equal value="${a.repgrp}" name="b" property="repgrp">
<%i++; %>
<tr>
<td><%=i %></td>
<td><bean:write name="a" property="employeeno" /></td>
<td><bean:write name="a" property="shift" /></td>
<td><bean:write name="a" property="deptTo" /></td>
<td><bean:write name="a" property="subdeptTO" /></td>
<td><bean:write name="a" property="rep_et" /></td>
<td><bean:write name="a" property="employeeName" /></td>
<td><bean:write name="a" property="designation" /></td>
<td><bean:write name="a" property="startTime" /></td>
<td><bean:write name="a" property="late" /></td>
<td><bean:write name="a" property="status" /></td>


</tr>
</logic:equal>
</logic:equal></logic:equal>

</logic:iterate>

</logic:iterate>
</logic:notEmpty>

<logic:notEmpty  name="onlydeptlist">





<logic:iterate id="a" name="emplist">
 


<%i++; %>
<tr>
<td><%=i %></td>
<td><bean:write name="a" property="employeeno" /></td>
<td><bean:write name="a" property="shift" /></td>
<td><bean:write name="a" property="deptTo" /></td>
<td><bean:write name="a" property="subdeptTO" /></td>
<td><bean:write name="a" property="rep_et" /></td>
<td><bean:write name="a" property="employeeName" /></td>
<td><bean:write name="a" property="designation" /></td>
<td><bean:write name="a" property="startTime" /></td>
<td><bean:write name="a" property="late" /></td>
<td><bean:write name="a" property="status" /></td>


</tr>



</logic:iterate>


</logic:notEmpty>

</table>


</html:form>
</body>
</html>
