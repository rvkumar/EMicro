

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
				    <table class="bordered" border="1">
                 
               <tr>   <th colspan="12"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br>Department Wise Man Days Report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/></center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
              
                    <th>Sub Department</th>
                    <th>No.Of.emp</th>                    
                    <th>Paiddays.</th>
                    <th>P/OD</th>
                    <th>A</th>
                    <th>WO</th>
                    <th>HL</th>
                    <th>CL</th>
                    <th>SL</th>
                    <th>EL</th>               
                    <th>CO</th>
                    
                  </tr>
                  
                
                            <logic:iterate id="b" name="deptlist">
<tr>
<td colspan="12">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>

<b>Reporting group   </b>   :   <bean:write name="b" property="desgTo" />

</td>

</tr>
                   
                  <logic:iterate id="abc1" name="list1">
                  
             
     
              <logic:notEqual value="tot" name="abc1" property="status">
              <logic:equal value="${abc1.department}" name="b" property="department">
<logic:equal value="${abc1.subdepartment}" name="b" property="subdepartment">
<logic:equal value="${abc1.repgrp}" name="b" property="repgrp">
 <%i++; %>
                <tr>               
                     <td ><%=i%></td>
                    <td ><b><bean:write name="abc1" property="deptTo"/></b></td>     
                    <td > <bean:write name="abc1" property="employeeno"/></td>
                    <td > <bean:write name="abc1" property="paid_days"/></td> 
                    <td > <bean:write name="abc1" property="od"/></td>   
                    <td > <bean:write name="abc1" property="lp"/></td>
                    <td > <bean:write name="abc1" property="wo"/></td>
                    <td > <bean:write name="abc1" property="ph"/></td> 
                    <td > <bean:write name="abc1" property="cl"/></td>
                 	<td > <bean:write name="abc1" property="sl"/></td>
                    <td > <bean:write name="abc1" property="el"/></td>                   
                    <td > <bean:write name="abc1" property="co"/></td>
                  </tr>
                  </logic:equal></logic:equal></logic:equal>
                  </logic:notEqual>
				 </logic:iterate>
				 	</logic:iterate>
				 	
				 	
				 	 <logic:iterate id="abc1" name="list1">
				 	        <logic:equal value="tot" name="abc1" property="status">
               <tr>               
                     
                     <td  colspan="2" style="background-color:#483D8B ;color: white"><center><b> Grand Total</b></center></td>     
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="employeeno"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="paid_days"/></td> 
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="od"/></td>   
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="lp"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="wo"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="ph"/></td> 
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="cl"/></td>
                 	<td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="sl"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="el"/></td>                   
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="co"/></td>
                   
                  </tr>
             
             </logic:equal>
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
                 
                     <tr>   <th colspan="14"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br>Employee Wise Man Days report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/>
                     
                      <br/>Contractor-<bean:write name="hrApprovalForm" property="contentDescription"/></center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
              
                    <th>EmpNo</th>
                    <th>Name</th>                    
                    <th>Paiddays.</th>
                    <th>P/OD</th>
                    <th>A</th>
                    <th>WO</th>
                    <th>HL</th>
                    <th>CL</th>
                    <th>SL</th>
                    <th>EL</th>               
                    <th>CO</th>
                      <th>OT hrs</th>
                           <th>Salary</th>
                  </tr>
                  
                <logic:iterate id="b" name="deptlist">
<tr>
<td colspan="14">
<b>Department </b>   :   <bean:write name="b" property="deptTo" /><br/>
<b>Reporting group   </b>   :   <bean:write name="b" property="desgTo" />
</td>

</tr>
                   
                  <logic:iterate id="abc1" name="list2">
                 
             <logic:notEqual value="tot" name="abc1" property="status">
      <logic:equal value="${abc1.subdepartment}" name="b" property="subdepartment">
        <logic:equal value="${abc1.repgrp}" name="b" property="repgrp">
                <%i++; %>
                <tr>               
                     <td ><%=i%></td>
                    <td ><b><bean:write name="abc1" property="employeeno"/></b></td>     
                    <td > <bean:write name="abc1" property="employeeName"/></td>
                    <td > <bean:write name="abc1" property="paid_days"/></td> 
                    <td > <bean:write name="abc1" property="od"/></td>   
                    <td > <bean:write name="abc1" property="lp"/></td>
                    <td > <bean:write name="abc1" property="wo"/></td>
                    <td > <bean:write name="abc1" property="ph"/></td> 
                    <td > <bean:write name="abc1" property="cl"/></td>
                 	<td > <bean:write name="abc1" property="sl"/></td>
                    <td > <bean:write name="abc1" property="el"/></td>                   
                    <td > <bean:write name="abc1" property="co"/></td>
                     <td > <bean:write name="abc1" property="ot"/></td>
                      <td > <bean:write name="abc1" property="consal"/></td>
                  </tr>
                  </logic:equal>
              </logic:equal>
              </logic:notEqual>
				 </logic:iterate>
				 	
				 </logic:iterate>
				 
				 <logic:iterate id="abc1" name="list2">
				 	        <logic:equal value="tot" name="abc1" property="status">
               <tr>               
                     
                     <td  colspan="2" style="background-color:#483D8B ;color: white"><center><b> Grand Total</b></center></td>     
                    <td style="background-color:#483D8B ;color: white"> &nbsp;</td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="paid_days"/></td> 
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="od"/></td>   
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="lp"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="wo"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="ph"/></td> 
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="cl"/></td>
                 	<td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="sl"/></td>
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="el"/></td>                   
                    <td style="background-color:#483D8B ;color: white"> <bean:write name="abc1" property="co"/></td>
              <td style="background-color:#483D8B ;color: white">&nbsp;</td>
                          <td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="consal"/></td>
                  </tr>
             
             </logic:equal>
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
