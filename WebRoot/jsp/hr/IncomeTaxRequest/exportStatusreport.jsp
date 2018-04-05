

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

				
	<html:form action="incomeTaxReq" enctype="multipart/form-data">
			

              
				  <% int i=0;%>
				    <table class="bordered" border="1">
                 
               <tr>   <th colspan="11"><center>MICRO LABS LIMITED<br>Income Tax Status  Report for the year - <bean:write name="incomeTaxReqForm" property="fiscalYear"/> </center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
              
                    <th>Emp No</th>
                    <th>Emp Name</th>                    
                    <th>Department</th>
                    <th>Designation</th>
                    <th>Investment</th>
                    <th>Claim  HRA</th>
                    <th>Previous Income</th>
                    <th>External Income</th>
                    <th>Annual LTA</th>
                    <th>Annual Medical</th>               
                  
                    
                  </tr>
                  <logic:notEmpty name="status">
                  <%int y=0; %>
                  <logic:iterate id="h" name="status">
                  <%y++; %>
                  <tr>
                  <td><%=y %></td>
                  <td>${h.employeeNo }</td>
                     <td>${h.employeeName }</td>
                        <td>${h.department }</td>
                           <td>${h.designation }</td>
                   <td>${h.invCode }</td>
                   <td>${h.invDes }</td>
                    <td>${h.invLimit }</td>
                   <td>${h.invRemarks }</td>
                    <td>${h.invSection }</td>
                   <td>${h.invStatus }</td>
                  </tr>
                  </logic:iterate>
                  </logic:notEmpty>
                  
                </table>
                           

</html:form>
</body>
</html>
