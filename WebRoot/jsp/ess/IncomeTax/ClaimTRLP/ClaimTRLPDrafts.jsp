<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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

<script language="javascript">
function applyClaimTRLP(){
document.forms[0].action="claimTRLP.do?method=newClaimTRLP";
document.forms[0].submit();
}
</script>
</head>
<body>
<html:form action="incomeTax" enctype="multipart/form-data" method="post">

<html:button property="method" value="New" onclick="applyClaimTRLP()" styleClass="rounded"/>

<logic:notEmpty name="listOfClaimTRLP">
<table class="bordered sortable">
<tr><th>Employee Name</th><th>Location</th><th>Staff Category</th><th>Fiscal Year</th><th>Status</th><th>&nbsp;</th>
<th><center><a href="#"><img src="images/deleteIcon.png" onclick="deleteRent()" height="20" title="Delete"/></a></center></th></tr>
<logic:iterate id="abc" name="listOfClaimTRLP">
<tr>
<td>${abc.employeeName }</td><td>${abc.location }</td><td>${abc.staffCategory }</td><td>${abc.fiscalYear }</td>
<td>${abc.recordStatus }</td><td><a href="#"><img src="images/edit1.jpg" title="edit" onclick="editClaimTRLP('<bean:write name="abc" property="requestNo"/>')"/></td>
<td><center><html:checkbox property="selectedReqNo" name="abc"
		value="${abc.requestNo}" styleId="${abc.requestNo}"  style="width :10px;"/></center></td>
</tr>
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="noRecords">
<table class="bordered sortable">
<tr><th>Employee Name</th><th>Location</th><th>Staff Category</th><th>Fiscal Year</th><th>Status</th><th>&nbsp;</th>
</tr>
<tr><td colspan="6"><center><font color="red" size="3">Searched details could not be found.</center></td></tr>
</table>
</logic:notEmpty>
</html:form>
</body>
</html>