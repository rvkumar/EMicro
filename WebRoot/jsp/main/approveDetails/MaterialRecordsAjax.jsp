<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
    <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
</head>
<body>
<logic:notEmpty name="materialList">
<table class="borderd" width="100%" style="width: 100%;">
<tr>
<th>Material Type</th><th>Material Group</th><th>Short Name</th><th>Long Name</th><th>SAP Code No</th>
</tr>
<logic:iterate id="abc" name="materialList">
<tr>
<td><bean:write name="abc" property="materialType"/></td>
<td><bean:write name="abc" property="materialGrup"/></td>
<td><bean:write name="abc" property="shortName"/></td>
<td><bean:write name="abc" property="longName"/></td>
<td><bean:write name="abc" property="codeNo"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>
<logic:notEmpty name="noMaterialList">
<table class="borderd" width="100%" style="width: 100%;">
<tr>
<th>Material Type</th><th>Material Group</th><th>Short Name</th><th>Long Name</th><th>SAP Code No</th>
</tr>
<tr>
<td colspan="5">
<center><font color="red" size="2"><b>No Records...</b></font></center>
</td></tr></table>
</logic:notEmpty>

</body>
</html>

