

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
<title>Contacts </title>

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
var url="contacts.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousRecord()
{

var url="contacts.do?method=previousRecord";
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
		function viewMaterial(empNo)
		{
		
	 
		
			var url="contacts.do?method=viewEmployee2&empNo="+empNo;
			document.forms[0].action=url;
			document.forms[0].submit();
		
		}
	

</script>
</head>
<body>
<html:form action="contacts" enctype="multipart/form-data">

<table class="bordered Content">
<tr><th>
SEARCH DETAILS </th></tr></table>
<br/> 
<div align="center">
						<logic:present name="contactsForm" property="message1">
						<font color="red">
							<bean:write name="contactsForm" property="message1" />
						</font>
					</logic:present>
 
	<logic:notEmpty name="empList" >
	<div align="left" class="bordered">
	<table border="0" width="100%"  class="sortable">
	

       
	 <tr>
	                        <th style="text-align:left;"><b>Employee Photo</b></th>
							<th style="text-align:left;"><b>Employee Name</b></th>
							<th style="text-align:left;"><b>Designation</b></th>	
							<th style="text-align:left;"><b>Department</b></th>
							<th style="text-align:left;"><b>Plant</b></th>
							<th style="text-align:left;"><b>Email&nbsp;ID</b></th>
							<th style="text-align:left;"><b>Board Line</b></th>
							<th style="text-align:left;"><b>Ext.No</b></th>
							<th style="text-align:left;"><b>IP Phone</b></th>
							<th style="text-align:left;"><b>View</b></th>
	 </tr>
	
            
                         
					
						<%
							int count = 1;
										
						%>
	<logic:iterate id="empList" name="empList">
	
<tr>
	<td colspan="1" align="center" valign="middle" style="text-align: center;">
	<img src="/EMicro Files/images/EmpPhotos/<bean:write name="empList" property="empPhoto"/>"
	width="40px" height="40px" border="1" align="middle"  /></td>
	<td><bean:write name="empList" property="firstName"/></td>
	<td><bean:write name="empList" property="designation"/></td>
	<td><bean:write name="empList" property="department"/></td>
	<td><bean:write name="empList" property="locationId"/></td>
	<td><bean:write name="empList" property="emailID"/></td>
	<td><bean:write name="empList" property="boardNo"/></td>
	<td><bean:write name="empList" property="contactNo"/></td>
	<td><bean:write name="empList" property="ipPhone"/></td>
		<td >
      		<a href="#">
      	<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewMaterial('${empList.empid}')"/></a>
      			</td>
	</tr>

								
	
	</logic:iterate>
	
	</table> 
	</div>
	</logic:notEmpty>
	

	
	<logic:notEmpty name="noRecords">

<div class="bordered">

    <table border="0" width="100%" >
	<tr>
	                        <th style="text-align:left;"><b>ID</b></th>
							<th style="text-align:left;"><b>Employee Name</b></th>
							<th style="text-align:left;"><b>Designation</b></th>	
							<th style="text-align:left;"><b>Department</b></th>
							<th style="text-align:left;"><b>Plant</b></th>
							<th style="text-align:left;"><b>Email&nbsp;ID</b></th>
							<th style="text-align:left;"><b>Board Line</b></th>
							<th style="text-align:left;"><b>Contact No</b></th>
	 </tr>	</table>
					
   <div align="center">
						<logic:present name="contactsForm" property="message">
						<font color="red">
							<bean:write name="contactsForm" property="message" />
						</font>
					    </logic:present>
           
	</div>
	</div>
						
				
</logic:notEmpty>

<logic:notEmpty name="conList">
  <table border="0" width="50%" id="newTableView">
		               <tr>
		
							<th style="text-align:left;"><b>Input Content </b></th>
							<th style="text-align:left;"><b>Path</b></th>
							
						</tr>
						
					
						<tr><td colspan="5" class="underline"></td></tr>
						<%
							int count = 1;
										
						%>
										

<logic:iterate id="abc" name="conList">
<%if(count == 1) {%>
									<tr class="tableOddTR">
										<td>
<bean:write name="abc" property="keyword"/></td>
<td><bean:write name="abc" property="path"/>
</td>
									</tr>
<% count++;
							} else {
								int oddoreven=0;
								oddoreven  = count%2;
								if(oddoreven == 0)
								{
									%>
									<tr class="tableEvenTR">
										<td>
<bean:write name="abc" property="keyword"/></td>
<td><bean:write name="abc" property="path"/>
</td>
										
									</tr>
											
									<% }else{%>
									<tr class="tableOddTR">
										<td>
<bean:write name="abc" property="keyword"/></td>
<td><bean:write name="abc" property="path"/>
</td>
										
									</tr>
									<% }count++;}%>

									
								


</logic:iterate>

</table>

</logic:notEmpty>
<logic:notEmpty name="noRecords1">


<table border="0" width="50%" align="center" id="newTableView">
	<tr>
	<th style="text-align:left;"><b>Input Content </b></th>
							<th style="text-align:left;"><b>Path</b></th>
	 </tr>	</table>
					
					<div align="center">
						<logic:present name="contactsForm" property="message">
						<font color="red">
							<bean:write name="contactsForm" property="message" />
						</font>
					</logic:present>
           
	</div>
						
				
</logic:notEmpty>
		<html:hidden name="contactsForm" property="keyword"/>
	</html:form>
</body>
</html>