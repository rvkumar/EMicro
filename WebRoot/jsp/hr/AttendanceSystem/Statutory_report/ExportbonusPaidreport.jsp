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
	 <table><tr><td colspan="5">	&nbsp;</td>

	<td colspan="6">
	<center><big><b>Form C </b></big><br/>[See rule 4(c)]<br/>BONUS PAID TO EMLOYEES FOR THE ACCOUNTING YEAR ENDING ON THE </td>
	
	</tr></table> 
	
	<div>Name of the establishment</center></div>
		<div>No. of Working days in the year</center></div>
	<br/>
	
	<table border="1">
	<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td colspan="5"><center>Deductions</center></td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>
	<tr><td  ><b>Sl No.</td><td ><b>Name of the employee</td><td ><b>Father's name</td><td ><b>Whether he has completed 15 years of age at the beginning of the accounting year</td><td ><b>Designation</td><td ><b><center>No. of days worked in the year</center></td>
	<td ><b>Total salary or wage in respect of the accounting year</b></td><td ><b>Amount of bonus payable under section 10 or section 11 as the case may be</b></td>
	<td ><b>Puja bonus other customary during the accounting year</b></td><td ><b>Interim bonus or bonus bonus paid advance</b></td>	<td ><b>1[Amount of Income-tax deducted</b></td><td ><b>Deduction on account of financial loss, if any, caused by misconduct of the employee</b></td>
	
	<td ><b>2[Total sum deducted under Columns 9,10.10A and 11]</b></td><td ><b>Net amount payable (Column 8 minus Column 12)</b></td>	<td ><b>Amount actually Paid</b></td><td ><b>Date on which paid</b></td><td ><b>Signature/Thumb impression of the employee</b></td>
	
	
	
	</tr>

<tr><td><b>1</td><td><b>2</td><td><b>3</td><td><b>4</td><td><b>5</td><td><b>6</td><td><b>7</td><td><b>8</td> <td><b>9</td><td><b>10</td>  <td><b>10A</td>  

<td><b>11</td><td><b>12</td><td><b>13</td><td><b>14</td><td><b>15</td><td><b>16</td>

</tr>
<%int o=0; %>
<logic:iterate id="abc1" name="adult">
<%o++;%>
<tr><td><%=o %></td><td>${abc1.employeeName}</td><td>${abc1.fileName}</td><td>&nbsp;</td><td>${abc1.designation}</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>

</tr>
</logic:iterate>
	
	</table>
	
		<br/>
		<table border="1"><tr><td>1.</td><td colspan="6">Inserted by the Payment of Bonus (Amendment) Rules 1979, w.e.f. 8-9-1979, vide G.S.R. No-1147 dt. 23rd August 1979.</td></tr>
		<tr><td>2.</td><td colspan="6">Substituted by Ibid</td></tr>
		</table>
		


</html:form>
</body>
</html>
