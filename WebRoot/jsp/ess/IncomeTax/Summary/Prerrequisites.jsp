<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >

<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<title>Prerrequisites Details</title>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">
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
<div align="center" id="messageID" style="visibility: true;">
		<logic:present name="summaryForm" property="message">
			<font color="red" size="3"><b><bean:write name="summaryForm" property="message" /></b></font>
			<script type="text/javascript">
			setInterval(hideMessage,6000);
			</script>
		</logic:present>
		<logic:present name="summaryForm" property="message1">
			<font color="Green" size="3"><b><bean:write name="summaryForm" property="message1" /></b></font>
			<script type="text/javascript">
			setInterval(hideMessage,6000);
			</script>
		</logic:present>
	</div>
<html:form action="/summary.do" enctype="multipart/form-data" >

<logic:notEmpty name="perqList">
<table class="bordered" style="width: 50%;">
<tr><th>Perquisite</th><th>Type</th><th>S.No<th>FULLAMT</th><th>PERQON</th><th>Perquisite Amount</th></tr>
<logic:iterate id="abc" name="perqList">
<tr>
<td>${abc.pERQ_NAME }</td>
<td>${abc.pERQID }</td>
<td>${abc.perqSNO }</td>
<td>${abc.fULLAMT }</td>
<td>${abc.pERQON }</td>
<td>${abc.pERQAMT }</td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

</html:form></body></html>