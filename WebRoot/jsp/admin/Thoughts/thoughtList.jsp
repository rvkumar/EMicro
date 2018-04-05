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
<jsp:directive.page import="java.text.SimpleDateFormat"/>

<jsp:directive.page import="java.util.*"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Material Code Request </title>

<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />


<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link href="css/displaytablestyle.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<script type="text/javascript" src="js/sorttable.js"></script>

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">


function shownewThoughts()
{
var url="thought.do?method=displayNEWthoughts";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}


function deleteRecord(reqPriority)
{

var agree = confirm('Are You Sure You want To Delete');
if(agree)
{
var url="thought.do?method=Deletethoughts&reqpriority="+reqPriority;
	document.forms[0].action=url;
	document.forms[0].submit();
      
}


}

function previousRecord()
{
var url="thought.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function nextRecord()
{
var url="thought.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

</script>
  </head>
  
  <body>
  <html:form action="/thought.do"  enctype="multipart/form-data">
  
  
  
      <div align="center">
		<logic:notEmpty name="ThoughtForm" property="message">
			<font color="Green" size="3"><b><bean:write name="ThoughtForm" property="message" /></b></font>
		</logic:notEmpty>
		<logic:notEmpty name="ThoughtForm" property="message2">
			<font color="red" size="3"><b><bean:write name="ThoughtForm" property="message2" /></b></font>
		</logic:notEmpty>
	</div>
	<%
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date date = new Date();
	String currenDate=dateFormat.format(date);
	%>

<html:button property="method"  value="New" onclick="shownewThoughts()" styleClass="rounded" style="width: 80px;"/>&nbsp;&nbsp;
      <div>&nbsp;</div>
      	

<div> &nbsp;</div>
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
									&nbsp;<bean:write property="startRecord"  name="ThoughtForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="ThoughtForm"/>&nbsp;
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
	<html:hidden property="totalRecords" name="ThoughtForm"/>
	<html:hidden property="startRecord" name="ThoughtForm"/>
	<html:hidden property="endRecord" name="ThoughtForm"/>
 								
   <div class="bordered" >
		<table class="sortable" width="70%">
			<tr align="center" > 
				<th style="width:50%;">Description</th><th style="width:50px;">Priority</th><th style="width:50px;">Display Date</th><th style="width:50px;">Edit</th><th style="width:50px;">Delete</th>
				</tr>
		 <logic:iterate id="ThoughtForm" name="thoughtdetails">		
	<tr>

   <td>
    <logic:equal value="<%= currenDate%>" property="displayDate" name="ThoughtForm">
  <b> <bean:write name="ThoughtForm" property="description"/></b>
   </logic:equal>
    <logic:notEqual value="<%= currenDate%>" property="displayDate" name="ThoughtForm">
   <bean:write name="ThoughtForm" property="description"/>
   </logic:notEqual>
   </td>
   <td><bean:write name="ThoughtForm" property="priority"/></td>
    <td>
    <logic:equal value="<%= currenDate%>" property="displayDate" name="ThoughtForm">
   <b> <bean:write name="ThoughtForm" property="displayDate"/></b>
    </logic:equal>
    <logic:notEqual value="<%= currenDate%>" property="displayDate" name="ThoughtForm">
    <bean:write name="ThoughtForm" property="displayDate"/>
    </logic:notEqual>
    </td>
  <td><a href="thought.do?method=Editthoughts&priority=${ThoughtForm.priority}"><img src="images/edit1.jpg"/></a></td>
  <td><a  onclick="javascript:deleteRecord('${ThoughtForm.priority}')"><img src="images/delete.png"  /></a></td> 
   </tr>
   
   	</logic:iterate>
  
</table>



</div>

<logic:notEmpty name="ThoughtForm" property="message3">
			<font color="red" ><b><center><bean:write name="ThoughtForm" property="message3" /></center></b></font>
		</logic:notEmpty>
  

  </html:form>
  
  </body>
</html>
