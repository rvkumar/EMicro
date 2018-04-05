<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<script type="text/javascript">
function onUpload(){
var ifH = parent.document.getElementById("fr1").height;
ifH = parseInt(ifH) + 30;
parent.document.getElementById("fr1").height = ifH; 
parent.resizeAttachIframe("add");
	var url="/EMicro/mail.do?method=uploadFiles";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function onDeleteFile(attName){
var ifH = parent.document.getElementById("fr1").height;
ifH = parseInt(ifH) - 30;
parent.document.getElementById("fr1").height = ifH;
parent.resizeAttachIframe("delete");
	document.forms[0].action="/EMicro/mail.do?method=deleteUploadedFiles&cValues="+attName;
	document.forms[0].submit();
}

</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
<style>
.hide
{
position:absolute;
-moz-opacity:0;
filter:alpha(opacity: 0);
opacity: 0;
width:25px;
z-index: 2;
cursor: pointer;

}</style>

</head>
<body>

	<html:form action="mail.do" enctype="multipart/form-data" styleId="attachForm" >
	<table>
		<tr>
			<td><html:file styleId="attList" property="documentFile" styleClass="hide" onchange="onUpload()"></html:file><img src="/EMicro/images/att_icon.gif" id="attImg" class="iconcur"/><span style="padding-left:5px;color:rgb(248, 67, 9);">Info: You can attach upto 10 MB.</span><span style="text-align:center; padding-left: 10px"><bean:write name="mailInboxForm" property="mailMessage"/></span></td>
		</tr>
		<logic:notEmpty name="listName">
		<logic:iterate name="listName" id="abc">
			<tr> 
				<td><bean:write name="abc" property="fileList1"/> - <bean:write name="abc" property="fileSize"/>
				<span><img src="images/delete.png" width="10" height="10" onclick="onDeleteFile('<bean:write name="abc" property="fileList1"/>')"/></span></td>
			</tr>
		</logic:iterate>
		</logic:notEmpty>
		</table>
	</html:form>
</body>
</html>