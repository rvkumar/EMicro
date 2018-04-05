

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
                <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table class="bordered" border="1" >
                 <tr><th colspan="21">
                    <div><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Contractual report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/></center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Pay Grp Name</th>
                    <th>Department</th>
                    <th>Designation</th>                    
                    <th>Work.</th>
                    <th>Week Off</th>
                    <th>CL</th>
                    <th>SL</th>
                    <th>EL</th>
                    <th>LP</th>
                    <th>CO</th>
                    <th>ML</th>
                    <th>Lv.Avld</th> 
                    <th>Tot.W</th>
                    <th>OD</th>
                     <th>NH</th>
                    <th>Weekly off Earned </th>
                     <th>Paid Days </th>
                      <th>Tot Work Hrs </th>
                  </tr>
                  
                
                   
                  <logic:iterate id="abc1" name="list1">
                   <%i++; %>
             
             <logic:equal value="TOTAL" name="abc1" property="employeeno">
               <tr>               
                     
                     <td colspan="6" style="background-color:#483D8B ;color: white"><center><b> <bean:write name="abc1" property="employeeno"/></b></center></td>     
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="working"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="wo"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="cl"/></td>
                 	<td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="sl"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="el"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="lp"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="co"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="ml"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="leave_Availed"/></td>
                
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="total_worked"/></td>
             		<td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="od"/></td>      
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="ph"/></td> 
                    
                     <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="week_off_earned"/></td>
             		<td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="paid_days"/></td>      
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="worK_hrs"/></td> 
                  </tr>
             
             </logic:equal>
              <logic:notEqual value="TOTAL" name="abc1" property="employeeno">
                <tr>               
                     <td ><%=i%></td>
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="payGrpTo"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="working"/></td>
                    <td> <bean:write name="abc1" property="wo"/></td>
                    <td> <bean:write name="abc1" property="cl"/></td>
                 	<td> <bean:write name="abc1" property="sl"/></td>
                    <td> <bean:write name="abc1" property="el"/></td>
                    <td> <bean:write name="abc1" property="lp"/></td>
                    <td> <bean:write name="abc1" property="co"/></td>
                    <td> <bean:write name="abc1" property="ml"/></td>
                    <td> <bean:write name="abc1" property="leave_Availed"/></td>
                
                    <td> <bean:write name="abc1" property="total_worked"/></td>
             		<td> <bean:write name="abc1" property="od"/></td>      
                    <td> <bean:write name="abc1" property="ph"/></td> 
                    
                     <td> <bean:write name="abc1" property="week_off_earned"/></td>
             		<td> <bean:write name="abc1" property="paid_days"/></td>      
                    <td> <bean:write name="abc1" property="worK_hrs"/></td> 
                  </tr>
                  </logic:notEqual>
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty>
				  
				  
				  

</div>
</html:form>
</body>
</html>
