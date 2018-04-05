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

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>

<script type="text/javascript">


function saveThoughts()
{
if(document.forms[0].textthoughts.value=="")
	    {
	    alert("Please enter Description");
	    document.forms[0].textthoughts.focus;
	    return false;
	    }
	     var st = document.forms[0].textthoughts.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].textthoughts.value=st;
	    

var url="thought.do?method=savethought";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function BackThoughts()
{
var url="thought.do?method=displaythought";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}
function ResetThoughts()
{
var url="thought.do?method=resetthought";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function modifyThoughts()
{
if(document.forms[0].textthoughts.value.trim()=="")
	    {
	    alert("Please enter Description");
	    document.forms[0].textthoughts.focus;
	    return false;
	    }
	     var st = document.forms[0].textthoughts.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].textthoughts.value=st;
	    if(document.forms[0].displayDate.value=="")
	    {
	    alert("Please Select Display date");
	    document.forms[0].displayDate.focus;
	    return false;
	    }


var url="thought.do?method=modifyThoughts";
	document.forms[0].action=url;
	document.forms[0].submit();
	
}



</script>
<link href="js/tooltip/tooltip.css" rel="stylesheet" type="text/css" />
    <script src="js/tooltip/tooltip.js" type="text/javascript"></script>
    <style type="text/css">
        h4 { font-size: 16px; font-family: "Trebuchet MS", Verdana; line-height:18px;} 
    </style>

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
	
     <div style="width: 90%">	
		<table class="bordered" width="90%">
			<tr>
				<th colspan="4"><center><big>Thoughts Form</big></center></th>
			</tr>
</table >
       <table align="center" class="bordered content">
       <th style="width:50px;" colspan="2">Description <font color="red" size="3">*</font></th>
      <tr><td><html:textarea property="textthoughts" style="width:100%;" cols="75" rows="5" ></html:textarea>
      <br /></td></tr>
      <logic:notEmpty name="ModifyButton">
      <tr>
         <th style="width:50px;" colspan="2">Status <font color="red" size="3">*</font></th>
      <tr><td><html:select  property="status">
      		<html:option value="">-Select-</html:option>
            <html:option value="On">On</html:option>
            <html:option value="Off">Off</html:option>
      </html:select>
      <html:text property="displayDate" styleId="popupDatepicker"/></td>
      
      </tr>
      </logic:notEmpty>
      	<tr>
	 		<td><logic:notEmpty name="saveButton">
	   <html:button property="method"  value="Save" onclick="saveThoughts()" styleClass="rounded tooltip" style="width: 80px;"  onmouseover="tooltip.pop(this, 'Send')"/>&nbsp;&nbsp;
       <html:button property="method"  value="Back" onclick="BackThoughts()" styleClass="rounded" style="width: 80px;"/>&nbsp;&nbsp;
       <html:button property="method"  value="Reset" onclick="ResetThoughts()" styleClass="rounded" style="width: 80px;"/>&nbsp;&nbsp;
        </logic:notEmpty >
        
       <logic:notEmpty name="ModifyButton">
        <html:button property="method"  value="Modify" onclick="modifyThoughts()" styleClass="rounded" style="width: 80px;"/>&nbsp;&nbsp;
         <html:button property="method"  value="Back" onclick="BackThoughts()" styleClass="rounded" style="width: 80px;"/>&nbsp;&nbsp;
        </logic:notEmpty >
           </td>
      </table>
      
      <html:hidden property="priority"/>
      <html:hidden property="reqPriority"/>
      
  </html:form>
   
   
  </body>
</html>
