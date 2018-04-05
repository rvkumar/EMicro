

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
	
	
	<div><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manual Entry Audit Report <bean:write name="hrApprovalForm" property="month"/>-<bean:write name="hrApprovalForm" property="year"/></b></big></center>
	
	
	</div>
	<br/>
		

<div style="width: 40%">

<table border="1">
 <tr>
 <th>No</th>
 <th>Empl No.</th>
 <th>Empl Name</th>
 <th>Location</th>
 <th>Swipe Date</th>
 <th>Swipe Type</th>
 <th>Prev Time</th>
 <th>Actual Time</th>
 <th>Reason Type</th>
 <th>Remarks</th>
 <th>User Name</th>
 <th>User Ip</th>
 <th>User Designation</th>
 <th>User Department</th>
 <th>Updated date </th>
 </tr>
 <%int i=0; %>
 <logic:notEmpty name="llist">
                  <logic:iterate id="abc1" name="llist">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>     
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="locationId"/></td>
                    <td> <bean:write name="abc1" property="startDate"/></td>
                    <td> <bean:write name="abc1" property="swipe_Type"/></td>
                    <td> <bean:write name="abc1" property="prev_time"/></td>
                    <td> <bean:write name="abc1" property="time"/></td>
                    <td> <bean:write name="abc1" property="reason_Type"/></td>
                    <td> <bean:write name="abc1" property="remarks"/></td>
                    <td> <bean:write name="abc1" property="employeeNumber"/></td>
                    <td> <bean:write name="abc1" property="id"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="date"/></td>
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				 
				 </table>
				 </div>
</html:form>
</body>
</html>
