<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<jsp:directive.page import="com.microlabs.login.dao.LoginDao" />
<jsp:directive.page import="java.sql.ResultSet" />
<jsp:directive.page import="java.sql.SQLException" />
<jsp:directive.page import="java.util.ArrayList" />
<jsp:directive.page import="java.util.Iterator" />
<jsp:directive.page import="java.util.LinkedHashMap" />
<jsp:directive.page import="java.util.Set" />
<jsp:directive.page import="java.util.Map" />
<jsp:directive.page import="com.microlabs.utilities.IdValuePair" />
<jsp:directive.page import="com.microlabs.ess.form.JoiningReportForm" />

<link rel="stylesheet" type="text/css" href="css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="css/TableCSS.css" />

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="../jquery.jscrollpane.css" />
<script type="text/javascript" src="../jquery.jscrollpane.min.js"></script>
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css" />

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style1/inner_tbl.css" />

<%--<script type="text/javascript" src="http://www.trsdesign.com/scripts/jquery-1.4.2.min.js"></script>--%>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Microlab</title>

 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>


		<style type="text/css">
.text_field {
	background-color: #f6f6f6;
	width: 150px;
	height: 20px;
	border: #38abff 1px solid
}
</style>

<script>

function addnew()
{
	var URL="essJoin.do?method=addnewUser";
	document.forms[0].action=URL;
		document.forms[0].submit();	
	
}

function searchEmployee1()
{
	if(document.forms[0].searchEmployee.value=="")
		{
		alert("Please enter Search text");
	      document.forms[0].searchEmployee.focus();
	      return false;
		}
var url="essJoin.do?method=serchEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();

}

function clearEmployee(){
	document.forms[0].searchEmployee.value="";
	}
	
function nextRecord(){
	var url="essJoin.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

	}
	function previousRecord(){
	var url="essJoin.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

	}

	function lastRecord(){
	var url="essJoin.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

	}
	function firstRecord(){
	var url="essJoin.do?method=displayJOINList";
			document.forms[0].action=url;
			document.forms[0].submit();
	}
</script>

	</head>

	<body>
<html:form action="/essJoin.do" enctype="multipart/form-data" onsubmit="searchEmployee1();return false">


<logic:equal value="pdf" name="joiningReportForm" property="reqStatus" >
				<div style="display: none;" ><a href="${joiningReportForm.fileFullPath}"  target="_blank" id="pdf"></a></div>
					<script language="javascript">
	
						document.getElementById("pdf").click();
					</script>
			</logic:equal>
		<div align="center" id="messageID">
			<logic:present name="joiningReportForm" property="message">
				<font color="green" size="4"> <bean:write
						name="joiningReportForm" property="message" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
			</logic:present>
			<logic:present name="joiningReportForm" property="message1">
				<font color="red" size="4"> <bean:write
						name="joiningReportForm" property="message1" /> </font>
						<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
			</logic:present>
		</div>

<input type="button" class="rounded" value="Add New" onclick="addnew()" style="width: 120px" />&nbsp;<img src="images/clearsearch.jpg" align="absmiddle"  onclick="clearEmployee()" />&nbsp;
<html:text property="searchEmployee" title="Enter Emp Name or Ref no." styleClass="rounded"></html:text>&nbsp;<img src="images/search.png" align="absmiddle"  onclick="searchEmployee1()"/>
<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="joiningReportForm"/>-
	
	<bean:write property="endRecord"  name="joiningReportForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>

	</table>
	</logic:notEmpty>
		<br />
<br />
			<table align="center" class="bordered">
				<tr><th style="width: 10%">Reference Id</th><th>Name</th><th>DOB</th><th style="width: 15%">Email</th><th>Mob No</th><th>Edit</th>	<th style="width: 8%">Report</th>	
				</tr>
				<logic:notEmpty name="joinlist">
				<logic:iterate id="j" name="joinlist">
				<tr><td>${j.newid}</td><td>${j.firstName}</td><td>${j.dateofBirth}</td><td>${j.emailAddress}</td><td>${j.mobileNumber}</td><td>
			<a href="essJoin.do?method=display1&id=${j.newid}"><img src="images/edit1.jpg"/></a>
				</td>
				<td>
			<a href="essJoin.do?method=reportgenerate&id=${j.newid}"><img src="images/pdf.png" style="height: 30%;width: 30%;align:absmiddle"/></a>
				</td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="joinlist">
				<tr><td colspan="5">No records to display..</td></tr>
				 </logic:empty>
				
			</table>
			<br />
			<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>

		          
		</html:form>
	
	</body>
</html>
