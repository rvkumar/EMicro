<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>

<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<%--	<link rel="stylesheet" type="text/css" href="styles.css" />--%>

<%--  <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />--%>
<!--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>-->

<%--<script type='text/javascript' src="calender/js/zapatec.js"></script>--%>
<!-- Custom includes -->
<!-- import the calendar script -->
<%--<script type="text/javascript" src="calender/js/calendar.js"></script>--%>
    <base href="<%=basePath%>">
    
    <title>eMicro :: Home_Image_Upload</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
<script type="text/javascript">

function onUpload(){


var splChars = "'";
if(document.forms[0].productFileName.value=="")
{
  alert("Please Choose Gif File");
  document.forms[0].productFileName.focus();
  return false;
}
else if(document.forms[0].productFileName.value!="")
{
  var productFileName=document.forms[0].productFileName.value;
    for (var i = 0; i < productFileName.length; i++) {
	    if (splChars.indexOf(productFileName.charAt(i)) != -1){
	    alert ("Please Remove Single Code(') in  Gif File  !"); 
	          document.forms[0].productFileName.focus();
	 return false;
	}
	}
}	   
		
			var url="products.do?method=upLoadProduct";
			document.forms[0].action=url;
			document.forms[0].submit();		 
}


function deleteMainLinkFiles(){
	
	var fileChecked=0;
var fileLength=document.forms[0].checkedfiles.length;
var fileLength1=document.forms[0].checkedfiles.checked;
if(fileLength1==true && fileLength==undefined)
{
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="products.do?method=deleteProductgif";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}
else
{
for(i=0;i<fileLength;i++)
{
if(document.forms[0].checkedfiles[i].checked==true)
{
fileChecked=fileChecked+1;
}
}
if(fileChecked==0)
{
alert('Select Atleast One Record To Delete');
return false;
}
else
{
}
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="products.do?method=deleteProductgif";
		document.forms[0].action=url;
		document.forms[0].submit();
}
else
{
return false;
}
}	
}

function statusMessage(message)
{

alert(message);

}

</script>

</head>
  
<body>
	<html:form action="products.do" enctype="multipart/form-data">
	<logic:notEmpty name="newProductForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="newProductForm" property="message" />');
					</script>
				</logic:notEmpty>
	<div style="width: 90%">
	<html:hidden  property="fileCount"/>
	<table class="bordered" width="90%">
		<tr>
			<th colspan="2" style="text-align: center;"><big>Home Page Animated File</big></th>
		</tr>
		
		<tr>
			<td align="center">Animation (GIF Only)</td>
			<td><html:file name="newProductForm" property="productFileName" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;
				<html:button value="Upload" onclick="onUpload()" property="method" styleClass="rounded" style="width: 100px" />
			</td>
		</tr>

		<logic:notEmpty name="listName">
			<tr>
				<th colspan="2"><big>Uploaded File</big></th>
			</tr>

			<logic:iterate name="listName" id="listid">
            	<tr>
					<td><input type="checkbox" name="checkedfiles" value="<bean:write name="listid" property="gifFile"/>" /></td>
					<td><bean:write name="listid" property="gifFile" /></td>					
				</tr>
			</logic:iterate>

			<tr>
				<td colspan="2" align="center">
					<html:button value="Delete" onclick="deleteMainLinkFiles()" property="method" styleClass="rounded" style="width: 100px" />
				</td>
			</tr>
		</logic:notEmpty>							
	</table>
	</div>
	</html:form>

</body>
</html>
