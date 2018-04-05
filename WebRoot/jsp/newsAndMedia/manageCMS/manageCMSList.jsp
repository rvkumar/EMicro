<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

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


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Content Management View </title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />

<!--
/////////////////////////////////////////////////
-->
	<script type="text/javascript" src="js/sorttable.js"></script>
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
    <link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript">
function searchContacts()
{
var url="contacts.do?method=searchContacts";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function nextRecord()
{
/* var url="contacts.do?method=nextRecord"; */
var url="manageCMS.do?method=nextRecord";

			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousRecord()
{

/* var url="contacts.do?method=previousRecord"; */
var url="manageCMS.do?method=previousRecord";

			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstRecord()
{

var url="contacts.do?method=firstRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{

var url="contacts.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function displayCMS(reqID){
	var url="manageCMS.do?method=editCMS&reqID="+reqID;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function displayType(){
	var archiveType=document.forms[0].archiveType.value;
	if(archiveType=="Unarchive"){
		var url="manageCMS.do?method=displayUnarchive";
		document.forms[0].action=url;
		document.forms[0].submit();
	}else{
		var url="manageCMS.do?method=displayArchive";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
}

function newCMS(){
	var url="manageCMS.do?method=newCMS";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function deleteCMS(reqID){
	
	var agree = confirm('Are You Sure To Delete Selected Image');
	if(agree)
	{
	
	var url="manageCMS.do?method=deleteCMS&reqID="+reqID;
	document.forms[0].action=url;
	document.forms[0].submit();
	
	}
}

</script>
</head>

<body>
	<html:form action="manageCMS.do" enctype="multipart/form-data">
	<logic:notEmpty name="menuList" >

	<table class="sortable bordered" width="100%">
		<tr>
		<th colspan="3" style="text-align: center;"><big>Manage Business Section</big></th>
		</tr>
	
<%-- 	<tr>
	<th width="5%">Sl.No</th><th width="90%">Menu Name</th><th width="5%">Edit</th> </tr>
	<%
	
	int i=1;
	%>
	<logic:iterate id="menu" name="menuList">
	<tr>
	<td><%=i %></td>
	<td><bean:write name="menu" property="linkName"/></td>
	<td><a href="manageCMS.do?method=displayLinksContent1&LinkName=<bean:write name="menu" property="linkName"/>"><img src="images/edit.png"/></a></td>
	
	</tr>
	<%
	i++;
	%>
	</logic:iterate> --%>
	
	</table> 

	</logic:notEmpty>
	
	
	<table class="bordered" align="center">
	<tr>
		<th colspan="3" style="text-align: center;"><big>Manage Business Section</big></th>
		</tr>
	<tr>
	<th>Choose Type</th>
	<td>
		<html:select property="archiveType" onchange="displayType()">
		  <html:option value="Unarchive">  Unarchive   </html:option>
		  <html:option value="Archive">  Archive   </html:option>
		</html:select> 
	 </td>
	</tr>
	</table>
	<div>&nbsp;</div>
   <html:button property="method" value="New" onclick="newCMS()" styleClass="rounded"/>
	<div>&nbsp;</div>
	<div align="center">  
					
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="manageCMSForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="manageCMSForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								
						
								</logic:notEmpty>
						</div>
	<html:hidden property="totalRecords" name="manageCMSForm"/>
	<html:hidden property="startRecord" name="manageCMSForm"/>
	<html:hidden property="endRecord" name="manageCMSForm"/>
	<table class="bordered sortable">
	 <tr>
	   <th>Menu</th><th>Link</th><th>Sub Link</th><th>Date</th><th>&nbsp;</th><th>&nbsp;</th>
	 </tr>
			<logic:notEmpty name="data">
				<logic:iterate id="a" name="data">
					<tr>
						<td>${a.linkName }</td>
						<td>${a.subLinkName }</td>
						<td>${a.subSubLinkName }</td>
						<td>${a.year }</td>
						<td><a href="#"><img src="images/edit.png" title="Edit" onclick="displayCMS('<bean:write name="a" property="id" />')"/></a></td>
						<td><a href="#"><img src="images/delete.png" title="Delete" onclick="deleteCMS('<bean:write name="a" property="id" />')"/></a></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
			
			<logic:notEmpty name="NoRecords">
			<tr>
						<td colspan="4"><center><font size="2" color="red">No Records Found</font></center></td>
			</tr>
			</logic:notEmpty>

		</table>
	
	
	
	</html:form>
</body>
</html>