
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
     <head><link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<!--
/////////////////////////////////////////////////
-->
     <script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
    <title>My JSP 'empOIForm.jsp' starting page</title>
   
   <script type="text/javascript">
   
   function addEmployee()
{

	var url="hrNewEmp.do?method=hrDisplayEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();
}
   
   
   function searchEmployee1()
{
var url="hrNewEmp.do?method=serchEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();

}
function clearEmployee(){
var url="hrNewEmp.do?method=displayEmpOfficalInfo";
	document.forms[0].action=url;
	document.forms[0].submit();
}   

function firstRecord()
{

var url="hrNewEmp.do?method=firstRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}   
function previousRecord()
{

var url="hrNewEmp.do?method=previousRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
    function nextRecord()
    {

var url="hrNewEmp.do?method=nextRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}  
 function lastRecord()
 {

var url="hrNewEmp.do?method=lastRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
   
   </script>
    <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
  </head>
  
  <body>
  	
<html:form action="hrNewEmp.do" enctype="multipart/form-data" onsubmit="searchEmployee1(); return false;">
  <table class="bordered">
				<tr>
					
						<th colspan="2">EMPLOYEE INFORMATION</th>
	</tr>
<tr>
<td>
<%-- <html:button property="method" value="Add" styleClass="rounded" style="width:100px;" onclick="addEmployee()"></html:button> --%>
<img src="images/clearsearch.jpg" align="absmiddle"  onclick="clearEmployee()" />
<html:text property="searchEmployee"></html:text><img src="images/search.png" align="absmiddle"  onclick="searchEmployee1()"/>
<logic:notEmpty name="displayRecordNo">
	 
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	<logic:notEmpty name="disablePreviousButton">

	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>

	</logic:notEmpty>
	
	<bean:write property="startRecord"  name="empOfficInfoForm"/>-
	
	<bean:write property="endRecord"  name="empOfficInfoForm"/>

	<logic:notEmpty name="nextButton">

	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>

	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	<img src="images/disableRight.jpg" align="absmiddle"/>

	
	</logic:notEmpty>
	
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	
	</td>
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>

	

	
	</logic:notEmpty>
	
 </tr>
	 </table>

<br/>
<div class="bordered">
<logic:notEmpty  name="employeeList">
<table  class="sortable" style="aligh:left;">
<tr align="left">
	<th>Emp&nbsp;No</th><th>Plant</th><th >Name</th><th >Designation</th><th >Department</th><th >Email&nbsp;ID</th><th >Ext&nbsp;No</th><th >IP&nbsp;No</th><th>View</th> </tr>
	
	<logic:iterate id="empList" name="employeeList">
	<tr>
	<td><bean:write name="empList" property="employeeNumber"/></td>
	<td><bean:write name="empList" property="location"/></td>
	<td><bean:write name="empList" property="firstName"/></td>
	
	
	<td><bean:write name="empList" property="designation"/></td>
	<td><bean:write name="empList" property="department"/></td>
	<td>&nbsp;<bean:write name="empList" property="emailid"/></td>
	<td>&nbsp;<bean:write name="empList" property="contactNo"/></td>
		<td>&nbsp;<bean:write name="empList" property="ipPhoneNo"/></td>

	<td><a href="hrNewEmp.do?method=editEmployeeDetails&EmpNo=<bean:write name="empList" property="employeeNumber"/>"><img src="images/view.gif" style="width: 30%;height: 30%"/></a></td>
	</tr>
	</logic:iterate>


</table>
</logic:notEmpty>





</div>


</td>
</tr>
</table>
</table>
</div>
</td>
</tr>
</table>
</html:form>

  
  
  
  
  
  
  
  
  </body>
</html>
