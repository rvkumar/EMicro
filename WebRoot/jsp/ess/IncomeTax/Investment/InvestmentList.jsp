<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript" src="js/sorttable.js"></script>

<script language="javascript">
function newInvestment(){
document.forms[0].action="incomeTax.do?method=display";
document.forms[0].submit();

}
function editRecord(requestNo){
document.forms[0].action="incomeTax.do?method=editDetails&RequestNo="+requestNo;
document.forms[0].submit();
}
</script>
<style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>
</head>
<body>
<html:form action="incomeTax" enctype="multipart/form-data" method="post">
<html:button property="method" value=" New " onclick="newInvestment()" styleClass="rounded" />
<logic:notEmpty name="listOfInvestmet">
<br/>
<table class="bordered sortable">
<tr><th>Req No</th><th>Fiscal Year</th><th>Total Amount</th><th>Submit Date</th><th>Status</th><th>Edit</th></tr>
<logic:iterate id="abc" name="listOfInvestmet">
<tr>
<td><bean:write name="abc" property="requestNumber"/></td>
<td><bean:write name="abc" property="fiscalYear"/></td>
<td><bean:write name="abc" property="totalAmount"/></td>
<td><bean:write name="abc" property="submitDate"/></td>
<td><bean:write name="abc" property="approvalStatus"/></td>
<td><logic:equal value="Drafts" property="approvalStatus" name="abc"><a href="#"><img src="images/edit1.jpg" onclick="editRecord('${abc.requestNumber }')"/></a></logic:equal>&nbsp;</td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="noRecords">
<table class="bordered sortable">
<tr><th>Req No</th><th>Fiscal Year</th><th>Total Amount</th><th>Submit Date</th><th>Status</th><th>Edit</th></tr>
<tr>
<td colspan="6">Searched details could not be found.</td>
</tr>

</table>
</logic:notEmpty>
</html:form>
</body>
</html>