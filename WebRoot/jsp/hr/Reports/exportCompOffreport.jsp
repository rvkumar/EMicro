

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

				
	<html:form action="hrreport" enctype="multipart/form-data">
	
		




</div>
</center>

                <logic:notEmpty name="clist">
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
                     <tr>   <th colspan="8"><center>MICRO LABS LIMITED<br/>Comp Off report between <bean:write name="hrReportForm" property="hrFromDate"/> - <bean:write name="hrReportForm" property="hrToDate"/></center></th></tr>
                  <tr>
                  
                    
                    <th style="width: 1%">#</th>
             
                    <th style="width: 30%">Emp name</th>
                    <th style="width: 15%">Department</th>                    
                    <th style="width: 15%">Emp no.</th>
                    <th style="width: 15%">Comp Off worked Date</th>    
        
                     <th style="width: 15%">No of Hrs</th>   
                     <th style="width: 15%">Comp Off balance</th>             
                      <th style="width: 15%">Comp Off Availed Date</th>
                  </tr>
                  
                
                   
                  <logic:iterate id="abc1" name="clist">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>                  
                    <td> <bean:write name="abc1" property="empName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="empno"/></td>
                    <td> <bean:write name="abc1" property="startDate"/></td>
                    
                  <td> <bean:write name="abc1" property="nofhrs"/></td>
                    <td> <bean:write name="abc1" property="noOfDays"/></td>
                    <td> <bean:write name="abc1" property="startDurationType"/></td>
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="6"><center></center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty>
				  
				  
				

</div>
</html:form>
</body>
</html>
