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
	 <table><tr><td colspan="5">	<b>Name and Address of the Factory:<br/> Micro Labs Ltd, <br/>  92 Sipcot Indl Complex,
Hosur - 635126</b></td>

	<td colspan="25">
	<center><big><b>FORM NO.25</b></big><br/>(Prescribed under Rule 103 of the Tamil Nadu factories Rule. 1950)
	
	
	
	</td></tr></table> 
	
	<div><center><big><b>MUSTER ROLL FOR THE MONTH OF <bean:write name="hrApprovalForm" property="month"/>-<bean:write name="hrApprovalForm" property="year"/></b></big></div>
	
	<br/>
	
	<table border="1">
	<tr><td rowspan="2" ><b>Sl No.</td><td rowspan="2"><b>Name of worker</td><td rowspan="2"><b>Father's name</td><td rowspan="2"><b>DesignationNature of work</td><td rowspan="2"><b>D O B</td><td rowspan="2"><b>Place 
of employment</td><td rowspan="2"><b>Group No</td><td rowspan="2"><b>Relay No.</td><td colspan="${hrApprovalForm.day-4}"><b><center>For the Period Ending</center></td></tr>
<tr><td colspan="${hrApprovalForm.day-4}"><b><center>Dates</center></td></tr>
<tr><td><b>1</td><td><b>2</td><td><b>3</td><td><b>4</td><td><b>5</td><td><b>6</td><td><b>7</td><td><b>8</td><logic:iterate id="c" name="datelist">         

<td><b>${c.day}</td>

</logic:iterate></tr>
<%int o=0; %>
<logic:iterate id="abc1" name="list3">
<%o++;%>
<tr><td><%=o %></td><td>${abc1.employeeName}</td><td>${abc1.fileName}</td><td>${abc1.designation}</td><td>${abc1.date}</td><td>${abc1.department}</td><td>${abc1.employeeno}</td><td>${abc1.employeeno}</td>
 <logic:iterate id="d" name="datelist">         
					
<td> <bean:write name="abc1" property="day${d.day}"/></td>

</logic:iterate>
</tr>
</logic:iterate>
	
	</table>
	
		
		
		


</html:form>
</body>
</html>
