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
	 <table><tr><td colspan="5">	<b>Form11(See Rule 112) <br/>  Prescribed under Karnataka Factories Rules 1969</b></td>

	<td colspan="1">
	<center><big><b>Register Of Adult Workers</b></big><br/>
	
	
	
	</td>
	<td>&nbsp;&nbsp;</td>
	
	<td>For the year:&nbsp;<big><b>${hrApprovalForm.year}</b></big></td></tr></table> 
	
	
	
	<br/>
	<div>(Instructions: Relays and date of effect     &nbsp;&nbsp;   &nbsp;&nbsp;    may be entered beforehand in the muster roll of Adults)</div>
	<table border="1">
	<tr><td rowspan="2" ><b>Sl No.</td><td rowspan="2"><b>Name and Residential Address(Mailing Address) of the Worker</td><td rowspan="2"><b>Father's name</td><td rowspan="2"><b>Nature of work & Dept.Symbol</td><td rowspan="2"><b>Relay Assigned &date of effect</td><td colspan="2"><b><center>Certificate of Adolescent</center></td>
	<td rowspan="2"><b>Date of first employement in the factory</b></td><td rowspan="2"><b>Remarks</b></td></tr>
<tr><td ><b><center>No. Of certificate and date</center></td><td ><b><center>Token No. giving reference to certificate</center></td></tr>
<tr><td><b>1</td><td><b>2</td><td><b>3</td><td><b>4</td><td><b>5</td><td><b>6</td><td><b>7</td><td><b>8</td>      

<td><b>9</td>

</tr>
<%int o=0; %>
<logic:iterate id="abc1" name="adult">
<%o++;%>
<tr><td><%=o %></td><td>${abc1.employeeName}<br/>${abc1.approverStatus}</td><td>${abc1.fileName}</td><td>${abc1.designation} ; ${abc1.department}</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>

</tr>
</logic:iterate>
	
	</table>
	
		
		
		


</html:form>
</body>
</html>
