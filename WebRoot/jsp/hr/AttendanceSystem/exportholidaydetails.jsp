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
	

&nbsp;&nbsp;


		 <div style="width: 80%">
		<table class="bordered" width="100%" border="1">
		<tr><th colspan="5"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Holiday List for the year-<bean:write name="hrApprovalForm" property="year"/></th></tr>
		<tr><th colspan="5"></th></tr>
				<tr>
					<th align="center">Sl. No</th>
					<th align="center">Location</th>
					<th align="center">Day</th>
					<th align="center">Date</th>					
					<th align="center">Occasion</th>
				</tr>
		<%int i=0; %>
			<logic:iterate name="listDetails" id="abc">
			<% i++;%>
		<tr>
						<td>
							<%=i %>
						</td>
											
						<td><bean:write name="abc" property="location"/></td>
						<td><bean:write name="abc" property="dayName"/></td>
						<td><bean:write name="abc" property="holidayDate"/></td>
						<td><bean:write name="abc" property="holidayName"/></td>
					</tr>
		
		</logic:iterate>
		
</table>
</div>
<br/>
<center>




  

</div>
</html:form>
</body>
</html>
