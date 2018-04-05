

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

		 <div style="width: 90%" >
		 
		
<div>



</div>
</center>
</br>
</br>
<logic:equal name="hrApprovalForm" property="summbrkup"  value="Final Settlement">
<table border="1">
 <tr>   <th colspan="22"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave report for the year-${hrApprovalForm.year}</center></th></tr>
                  </table>
                  
                  
                <br/>
<table  class="bordered" border="1">

  <logic:iterate id="leaveForm" name="offlist">
<tr ><td ><b>Employee No.</b></td><td><bean:write name="leaveForm" property="employeeNumber"></bean:write></td>
<td ><b>Name</b></td><td><bean:write name="leaveForm" property="employeeName"></bean:write></td>

</tr>

<tr>
<td ><b>Designation</b></td><td><bean:write name="leaveForm" property="designation"></bean:write></td>
<td ><b>Department</b></td><td><bean:write name="leaveForm" property="department"></bean:write></td>

</tr>

<tr>
<td ><b>Date Of Joining</b></td><td><bean:write name="leaveForm" property="doj"></bean:write></td>
<td ><b>Year</b></td><td><bean:write name="hrApprovalForm" property="year"></bean:write></td>
</tr>
</logic:iterate>
</table></br>  

<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" >
		<table class="bordered" width="90%" border="1">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<bean:write name="hrApprovalForm" property="year"/></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
			</tr>
			<logic:iterate id="bal" name="LeaveBalenceList">
						<tr>
						<th><bean:write name="bal" property="leaveType"/></th>
						<td><bean:write name="bal" property="openingBalence"/></td>
						<td><bean:write name="bal" property="avalableBalence"/></td>
						<td><bean:write name="bal" property="closingBalence"/></td>
						<td><bean:write name="bal" property="awaitingBalence"/></td>
						</tr>	
			</logic:iterate>			
		</table>
		</div>
		</logic:notEmpty>
		
		
		</br>
		
		<table class="bordered" border="1">

<tr align="center">
<th rowspan="2"><center>Leave Type</center></th>
<th colspan="2"><center>Leave Applied</center></th>
<th rowspan="2"><center>No.of <br>Days</center></th>
<th rowspan="2"><center> Applied On</center></th>
<th rowspan="2"><center> Approved On</center></th>
<th rowspan="2"><center>Reason</center></th>


</tr>
<tr >
<th ><center>From</center></th>
<th ><center>To</center></th>


</tr>
<logic:iterate id="a" name="list">
<tr><td align="left"><bean:write name="a" property="leaveType"/></td><td><center><bean:write name="a" property="startDate"/></center></td><td><center><bean:write name="a" property="endDate"/></center></td><td><center><bean:write name="a" property="noOfDays"/></center></td><td><center><bean:write name="a" property="submitDate"/></center></td><td><center><bean:write name="a" property="approvedDate"/></center></td><td><bean:write name="a" property="reason"/></td></tr>
</logic:iterate>
</table>


<logic:notEmpty name="norecords">
<table class="bordered">


<tr><td colspan="7"><font color="red"><center>No Records</center> </font></td></tr>
</table>

</logic:notEmpty>

		

</logic:equal>
<logic:equal name="hrApprovalForm" property="summbrkup"  value="Summary">
      <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
                     <tr>   <th colspan="12"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave Balance report for the year-${hrApprovalForm.year}</center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
             
        
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      
                  </tr>
                  <tr><th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  </tr>
                                          <logic:iterate id="b" name="deptlist">
<tr>
<td colspan="12">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>
<b>Subdepartment</b> :   <bean:write name="b" property="deptTo" /><br/>
<b>Reporting Group   </b>   :   <bean:write name="b" property="desgTo" />

</td>

</tr>
                   
                  <logic:iterate id="abc1" name="list1">
                
           <logic:equal value="${abc1.department}" name="b" property="department">
<logic:equal value="${abc1.subdepartment}" name="b" property="subdepartment">
<logic:equal value="${abc1.repgrp}" name="b" property="repgrp">
   <%i++; %>
                <tr>               
                     <td ><%=i%></td>                  
                   
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 
              
                  </tr>
                 </logic:equal></logic:equal></logic:equal>
				 </logic:iterate>
				 	
				 </logic:iterate>
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				    
				    
				    	      <logic:notEmpty name="onlydept">
				  <% int i=0;%>
				    <table class="bordered" border="1" >
                 
                     <tr>   <th colspan="12"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave Balance report for the year-${hrApprovalForm.year}</center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
             
                    <th rowspan="2">Department</th>
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      
                  </tr>
                  <tr><th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  </tr>
                
                   
                  <logic:iterate id="abc1" name="onlydept">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>                  
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				    
				    </logic:equal>
				    
				    
				     <logic:equal name="hrApprovalForm" property="summbrkup"  value="Detailed">
				          <logic:notEmpty name="addi">
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
                     <tr>   <th colspan="14"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave Balance report for the year-${hrApprovalForm.year} </center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
                  <th rowspan="2">Employee Code</th>
                  <th rowspan="2">Employee name</th>
                    <th rowspan="2">Department</th>
                     <th rowspan="2">Designation</th>
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      
                  </tr>
                  <tr><th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  </tr>
                
                   
                  <logic:iterate id="abc1" name="addi">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>       
                         <td> <bean:write name="abc1" property="employeeno"/></td>    
                         <td> <bean:write name="abc1" property="employeeName"/></td>           
                    <td> <bean:write name="abc1" property="department"/></td>
                        <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				  </logic:equal>
				  
				  

</div>
</html:form>
</body>
</html>
