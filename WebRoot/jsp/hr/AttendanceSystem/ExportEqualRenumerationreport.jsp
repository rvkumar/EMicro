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
	 <table><tr><td colspan="5">	<b>Name and Address <br/>  of the Establishment<br/>Men :&nbsp;${hrApprovalForm.additioncount}&nbsp; Women :&nbsp;${hrApprovalForm.attritioncount}  &nbsp;Total Workers Employed ;&nbsp;${hrApprovalForm.attritioncount+hrApprovalForm.additioncount}</b></td>

	<td colspan="1">
	<center><big><b>FORM 'D' [Rule 6]<br/>(See Rule3(1))</b></big>
	</center>
	
	
	</td>
	
	</tr></table> 
	
	<div><center>REGISTER	TO BE MAINTAINED BY THE EMPLOYER UNDER RULE 6 OF THE EQUAL REMUNERATION RULE 1976</center></div>
	
	<br/>
	
	<table border="1">
	<tr><td rowspan="2" ><b>Category of workers</td><td rowspan="2"><b>Brief Description of work</td><td rowspan="2"><b>No. Of Men employed</td><td rowspan="2"><b>No. Of Women employed</td><td colspan="7"><b><center>Components of Renumeration</center></td>
	<td rowspan="2"><b>Cash Value of Concessional Supply of essential Commodities</b></td><td rowspan="2"><b>Remarks</b></td></tr>
<tr><td ><b><center>Rate Of Remuneration Paid<br/>Rs.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   P.</center></td><td ><b><center>Basic Wages Of Salary<br/>Rs.   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P.</center></td><td ><b><center>Dearness Allowance<br/>Rs.   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P.</center></td>
<td ><b><center>House Rent Allowance<br/>Rs.   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;P.</center></td><td ><b><center>Other Allowance<br/>Rs.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   P.</center></td><td ><b><center>&nbsp;</center></td><td ><b><center>&nbsp;</center></td></tr>


</tr>
<%int o=0; %>
<logic:iterate id="abc1" name="adult">
<%o++;%>
<tr><td>${abc1.catFrom}</td><td>&nbsp;</td><td>${abc1.additioncount}</td><td>${abc1.attritioncount}</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
</tr>
</logic:iterate>
	
	</table>
	
		
		
		


</html:form>
</body>
</html>
