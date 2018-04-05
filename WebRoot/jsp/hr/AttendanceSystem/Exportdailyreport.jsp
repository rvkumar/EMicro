

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
	
	
	<div><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Daily Attendance Detail Report for the Date :  <bean:write name="hrApprovalForm" property="date"/></b></big></center>
	
	
	</div>
	<br/>
		

<div style="width: 40%">
<table  border="1" >
<tr>
<th colspan="2">Summary Of Attendance</th>
</tr>
<tr>
<td>Present:</td>
<td><bean:write name="hrApprovalForm" property="presize" /></td>
</tr>
<tr>
<td>Absent:</td>
<td><bean:write name="hrApprovalForm" property="abssize" /></td>
</tr>
<tr>
<td>On Duty:</td>

<td><bean:write name="hrApprovalForm" property="odsize" /></td>

</tr>
<tr>
<td>Leave:</td>
<td><bean:write name="hrApprovalForm" property="leavesize" /></td>

</tr>
<tr>
<td>Loss Of Pay:</td>
<td><bean:write name="hrApprovalForm" property="lopsize" /></td>
</tr>

<tr>
<td>Total:</td>
<td>${hrApprovalForm.presize+hrApprovalForm.abssize+hrApprovalForm.odsize+hrApprovalForm.leavesize+hrApprovalForm.lopsize}</td>
</tr>
</table>
</div>

<br/>

<div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <%int i=0;%>
				    <table  border="1">
                 
                     <tr>   <th colspan="9"><center>Employee On Leave </center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Division</th>                    
                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Leave Type</th>
                    <th>No Of Days</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="leave">
                  <logic:iterate id="abc1" name="leave">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="fromDate"/></td>
                    <td> <bean:write name="abc1" property="toDate"/></td>
                    <td> <bean:write name="abc1" property="leavetype"/></td>
                    <td> <bean:write name="abc1" property="noOfDays"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available </center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 <br/>
			
				 
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table  border="1">
                 
                     <tr>   <th colspan="9"><center>Employee On Onduty</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Division</th>                    
                    <th>From Date</th>
                    <th>To Date</th>
                  
                    <th>From Time</th>
                     <th>To Time</th>
                    
                  </tr>
                
                   <logic:notEmpty name="onduty">
                  <logic:iterate id="abc1" name="onduty">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i %></td>
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="fromDate"/></td>
                    <td> <bean:write name="abc1" property="toDate"/></td>
                 
                    <td> <bean:write name="abc1" property="startTime"/></td>
                   <td> <bean:write name="abc1" property="endTime"/></td>
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available </center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 
				  <br/>
				  
				  	 
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table border="1" >
                 
                     <tr>   <th colspan="9"><center>Employee On Loss of Pay</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Division</th>                    
                    <th>From Date</th>
                    <th>To Date</th>
                    <th>Leave Type</th>
                    <th>No Of Days</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="lop">
                  <logic:iterate id="abc1" name="lop">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="fromDate"/></td>
                    <td> <bean:write name="abc1" property="toDate"/></td>
                    <td> <bean:write name="abc1" property="leavetype"/></td>
                    <td> <bean:write name="abc1" property="noOfDays"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available </center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 
				 	 <br>
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table class="bordered" border="1" >
                 
                     <tr>   <th colspan="6"><center>Absent Employee<br/>International Bussiness</center></th></tr>
                  <tr>
                  
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Designation</th>                    
                    <th>Division</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="absenceint">
                  <logic:iterate id="abc1" name="absenceint">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="6"><center>Currently details are not available </center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 <br>
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <% i=0;%>
				    <table  border="1">
                 
                     <tr>   <th colspan="6"><center>Absent Employee<br/>Domestic Services</center></th></tr>
                  <tr>
                  
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                      <th>Designation</th>                    
                    <th>Division</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="absence">
                  <logic:iterate id="abc1" name="absence">
                   <%i++; %>
             
                <tr>               
                    <td ><%=i %></td>
                    <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="6"><center>Currently details are not available </center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
</html:form>
</body>
</html>
