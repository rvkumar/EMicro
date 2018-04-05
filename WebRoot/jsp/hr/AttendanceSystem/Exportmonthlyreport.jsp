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
	<div><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Monthly Attendance Detail Report for the Month of  <bean:write name="hrApprovalForm" property="month"/>-<bean:write name="hrApprovalForm" property="year"/></b></big></center>
		</div>
	<br/>
		

  <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
                     <tr>   <th colspan="16"><center>Attendance Summary List</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Emp No </th>
                    <th>Emp Name </th>
                    <th>Pay Grp Name</th>
                    <th>Department</th>
                    <th>Designation</th>                    
                    <th>Wrk Days</th>
                   
                    <th>CL</th>
                    <th>SL</th>
                    <th>EL</th>
                    <th>LP</th>
                    <th>CO</th>
                    <th>ML</th>
                    <th>Lv.Avld</th> 
                              <th>AA</th>
                    <th>Tot Wrk Days</th>
                    <th>OD</th>
                   
                    
                  </tr>
                  
                
                   
                  <logic:iterate id="abc1" name="list1">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i%></td>
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="payGrpTo"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="working"/></td>
                    <td> <bean:write name="abc1" property="cl"/></td>
                 	<td> <bean:write name="abc1" property="sl"/></td>
                    <td> <bean:write name="abc1" property="el"/></td>
                    <td> <bean:write name="abc1" property="lp"/></td>
                    <td> <bean:write name="abc1" property="co"/></td>
                    <td> <bean:write name="abc1" property="ml"/></td>
                    <td> <bean:write name="abc1" property="leave_Availed"/></td>
                 <td> <bean:write name="abc1" property="aa"/></td>
                    <td> <bean:write name="abc1" property="total_worked"/></td>
             		<td> <bean:write name="abc1" property="od"/></td>      

                  </tr>
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty>
				  
				  
				  
			<logic:notEmpty name="list2">
				<% int i=0;%>
				    <table class="bordered" border="1">
                 
                      <tr>   <th colspan="13"><center>Daily Wise Break List</center></th></tr>
                  <tr>
  
                    <th >#</th>
                     <th >Date</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                     <th>In </th>
                    <th>Out</th>
                      <th>Status </th>
                     <th>From Date </th>
                    <th>To Date</th>
                    <th>Department</th>
                      <th>Designation</th>         
                        <th>Remarks</th>
               
                      <th>Paygroup</th>
                      
                      </tr>
                      
                        <logic:iterate id="abc1" name="list2">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i %></td>
					<td> <bean:write name="abc1" property="date"/></td>     
					<td> <bean:write name="abc1" property="employeeno"/></td>     
					<td> <bean:write name="abc1" property="employeeName"/></td>
					<td> <bean:write name="abc1" property="startTime"/></td>     
					<td> <bean:write name="abc1" property="endTime"/></td>
					<td> <bean:write name="abc1" property="status"/></td>     
					<td> <bean:write name="abc1" property="startDate"/></td>
					<td> <bean:write name="abc1" property="endDate"/></td>
					<td> <bean:write name="abc1" property="department"/></td>
					<td> <bean:write name="abc1" property="designation"/></td>
					<td> <bean:write name="abc1" property="remarks"/></td>
					
					<td> <bean:write name="abc1" property="paygrp"/></td>
                   
                     
                     </tr>
                     </logic:iterate>
                     
                     
                     <%if(i==0) 
					{
					%>
					<tr>  <td colspan="20"><center>Currently details are not available to display</center></td> </tr>
					<%} %> 
                      
                      </table>

</logic:notEmpty>
</div>
</html:form>
</body>
</html>
