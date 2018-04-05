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
		
	
	<div><center><big><b>Form No. VI</b></big><br/>(See sub-rule(1) of Rule 7)</center>
	
	
	</div>
	<div>
	To be marked as follows;	<br/>		
H' for holidays allowed		<br/>	
W/D' for work on double wages		<br/>	
W/H' for work with subsituted holiday		<br/>	
N/E' if not eligible for wages			
</div>	

<div><center><big><b>Register of National & Festival Holidays for the Year <bean:write name="hrApprovalForm" property="year"/></big></center></div>
	<br/>
		
		<table border="1">
		<tr><td rowspan="3"><b>S. No</b></td><td rowspan="3"><b>Name</b></td><td rowspan="3"><b>Emp No</b></td><td colspan="11"><b><center>Days, dates and months of all the year on which National and Festival Hoilidays are allowed<br/> under Section 3 of the Tamil Nadu Establishments ( National and Festival Holidays) Act,<br/> 1958 (Tamil Nadu Act XXXIII of 1958)</center></b></td></tr>
		<tr>
		<logic:iterate id="g" name="holist">
		<td><b>${g.holidayType } : ${g.date }</b></td>
		</logic:iterate>
	<td rowspan="2"><b>Remarks</b></td>
		</tr>
		<tr>
		<%int j=0; %>
		<logic:iterate id="g" name="holist">
		<%j++; %>
		<td><b><center><%=j %></center></b></td>
</logic:iterate>
		
		</tr>
		<logic:notEmpty name="list" >
		
		<%int h=1; %>
		<logic:iterate id="l" name="list">
		<tr><td><%=h %></td><td>${l.employeeno }</td><td>${l.employeeName }</td><td>${l.holdate1 }</td>
		<td>${l.holdate2 }</td><td>${l.holdate3 }</td><td>${l.holdate4 }</td><td>${l.holdate5 }</td>
		<td>${l.holdate6 }</td><td>${l.holdate7 }</td><td>${l.holdate8 }</td><td>${l.holdate9 }</td><td>${l.holdate10 }</td></tr>
		<%h++; %>
		</logic:iterate>
		</logic:notEmpty>
			</table>
		
		<br/>
		
	
		
		
		


</html:form>
</body>
</html>
