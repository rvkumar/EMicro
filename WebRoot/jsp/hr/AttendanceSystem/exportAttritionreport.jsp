

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
<logic:equal name="hrApprovalForm" property="summbrkup"  value="Summary">
                <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
                     <tr>   <th colspan="6"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Addition and Attrition report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/></center></th></tr>
                  <tr>
                  
                    
                    <th style="width: 1%">#</th>
             
                    <th style="width: 30%">Department</th>
                    <th style="width: 15%">Available</th>                    
                    <th style="width: 15%">Addition</th>
                    <th style="width: 15%">Attrition</th>                   
                      <th style="width: 15%">Total</th>
                  </tr>
                  
                
                   
                  <logic:iterate id="abc1" name="list1">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>                  
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="availablecount"/></td>
                    <td> <bean:write name="abc1" property="additioncount"/></td>
                    <td> <bean:write name="abc1" property="attritioncount"/></td>
                    <td> ${abc1.availablecount+abc1.additioncount-abc1.attritioncount}</td>
                 
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="6"><center></center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty>
				  </logic:equal>
				  
				  <logic:equal name="hrApprovalForm" property="summbrkup"  value="Detailed">
				    <logic:notEmpty name="addi">
				     <% int i=0;%>
				     
				    <table class="bordered" ><tr><th colspan="7"> <b><big> <center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Addition and Attrition report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/></center></big></b></div>
				     </th></tr>
				    <table class="bordered" border="1">
				    
				    <tr><th colspan="8"><big><strong>Additions</strong></big></th></tr>
				        <tr>	<th>Sl.No</th>	<th>Pernr</th><th>Emp Name</th><th>Department</th><th>Designation</th><th>DOJ</th><th>Previous Company</th><th>Previous Experience(yrs)</th></tr>
				          
				            <logic:iterate id="abc1" name="addi">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>  
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
                    <td> &nbsp;</td>
             
                     
                     </tr>
				        
				        </logic:iterate>
				        	<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center></center></td></tr>
				<%} %> 
				        </table>
				    </logic:notEmpty>
				    
				       <logic:notEmpty name="attri">
				     <% int j=0;%>
				    <table class="bordered" border="1">
				    <tr><th colspan="7"><big><strong>Attritions</strong></big></th></tr>
				        <tr>	<th>Sl.No</th>	<th>Pernr</th><th>Emp Name</th><th>Department</th><th>Designation</th><th>Date of Leaving</th><th> Reason for Leaving/Remarks</th></tr>
				            <logic:iterate id="abc1" name="attri">
                   <%j++; %>
           
                <tr>               
                     <td ><%=j%></td>  
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
               
             
                     
                     </tr>
				        
				        </logic:iterate>
				        	<%if(j==0) 
				{
				%>
				<tr><td colspan="23"><center></center></td></tr>
				<%} %> 
				        </table>
				    </logic:notEmpty>
				    
				  </logic:equal>
				  
				  

</div>
</html:form>
</body>
</html>
