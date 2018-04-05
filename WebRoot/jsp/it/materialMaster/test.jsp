<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript">
function send(){
var url="materials.do?method=saveInSAP";
		document.forms[0].action=url;
		document.forms[0].submit();

}
 </script>
 </head>
 <body>
<html:form action="/materials.do" enctype="multipart/form-data">

<table style="borderd sortable">
<tr>
<td>Test Name</td><td>
<html:text property="testName"></html:text>
</td>
</tr>
<tr>
<td colspan="2">
<html:button property="method" value="Send" onclick="send();"></html:button>
</td>
</td>
</table>

<html:hidden property="reqReqNo"/>
<html:hidden property="reqItemNo"/>
<html:hidden property="reqItemExist"/>



</html:form>
</html>