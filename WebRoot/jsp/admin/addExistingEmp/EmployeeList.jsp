<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



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
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<script type="text/javascript">

function addEmployee()
{

	var url="addEmp.do?method=display";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function searchEmployee1()
{
var url="addEmp.do?method=serchEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();

}


function onSave(){

	if(document.forms[0].location.value=="")
  {
    alert("Please Select Location");
    document.forms[0].location.focus();
    return false;
  }
  if(document.forms[0].department.value=="")
  {
    alert("Please Select Department");
    document.forms[0].department.focus();
    return false;
  }
   if(document.forms[0].designation.value=="")
  {
    alert("Please Select Designation");
    document.forms[0].designation.focus();
    return false;
  }
  if(document.forms[0].employeeNo.value=="")
  {
    alert("Please Enter Employee No");
    document.forms[0].employeeNo.focus();
    return false;
  }
   if(document.forms[0].firstName.value=="")
  {
    alert("Please Enter First Name");
    document.forms[0].firstName.focus();
    return false;
  }
   if(document.forms[0].contactNo.value=="")
  {
    alert("Please Enter Contact No");
    document.forms[0].contactNo.focus();
    return false;
  }
     if(document.forms[0].emailID.value=="")
  {
    alert("Please Enter Email ID");
    document.forms[0].emailID.focus();
    return false;
  }
    if(document.forms[0].loginName.value=="")
  {
    alert("Please Select Login Name");
    document.forms[0].loginName.focus();
    return false;
  }
    if(document.forms[0].password.value=="")
  {
    alert("Please Select Password");
    document.forms[0].password.focus();
    return false;
  }
    if(document.forms[0].activation.value=="")
  {
    alert("Please Select Activation");
    document.forms[0].activation.focus();
    return false;
  }
    if(document.forms[0].empStatus.value=="")
  {
    alert("Please Select Employee Type");
    document.forms[0].empStatus.focus();
    return false;
  }
   if(document.forms[0].employeeGroup.value=="")
  {
    alert("Please Select Employee Groups");
    document.forms[0].employeeGroup.focus();
    return false;
  }
    
	var url="addEmp.do?method=saveEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();
}

function clearEmployeeSearch(){
	var url="addEmp.do?method=displayEmployee";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function firstRecord()
{

var url="addEmp.do?method=firstRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}   
function previousRecord()
{

var url="addEmp.do?method=previousRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}
    function nextRecord()
    {

var url="addEmp.do?method=nextRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}  
 function lastRecord()
 {

var url="addEmp.do?method=lastRecord";
	document.forms[0].action=url;
	document.forms[0].submit();
}


function resetprocess()
{

var url="addEmp.do?method=resetProcess";
	document.forms[0].action=url;
	document.forms[0].submit();

}
</script>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
</head>

<body>

	
<html:form action="addEmp.do" enctype="multipart/form-data" onsubmit="searchEmployee1(); return false;">
<table>
<tr>
<td>
<html:button property="method" value="Add" style="width:100px;" styleClass="rounded" onclick="addEmployee()"></html:button>&nbsp;
<img src="images/clearsearch.jpg" align="absmiddle"  onclick="clearEmployeeSearch()" />
<html:text property="searchEmployee"></html:text><img src="images/search.png"  onclick="searchEmployee1()" align="absmiddle"/>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<html:button property="method" value="Reset Back End Process" style="width:100px;" styleClass="rounded" onclick="resetprocess()"></html:button>
<logic:notEmpty name="displayRecordNo">
	 
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	<logic:notEmpty name="disablePreviousButton">

	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>

	</logic:notEmpty>
	
	<bean:write property="startRecord"  name="addExistingEMPForm"/>-
	
	<bean:write property="endRecord"  name="addExistingEMPForm"/>

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
  <logic:notEmpty name="employeeList">
	<table   class="sortable" style="aligh:left;">
	<tr align="left">
	<th>Emp&nbsp;No</th><th>Plant</th><th >Employee&nbsp;Name</th><th >Login&nbsp;Name</th><th >Designation</th><th >Department</th><th >Email&nbsp;ID</th><th >Contact No</th><th>Edit</th> </tr>
	<logic:iterate id="empList" name="employeeList">
	<tr>
	<td><bean:write name="empList" property="employeeNo"/></td>
	<td><bean:write name="empList" property="location"/></td>
	<td><bean:write name="empList" property="firstName"/></td>
	<td><bean:write name="empList" property="loginName"/></td>
	
	<td><bean:write name="empList" property="designation"/></td>
	<td><bean:write name="empList" property="department"/></td>
	<td><bean:write name="empList" property="emailID"/></td>
	<td><bean:write name="empList" property="contactNo"/></td>
	<td><a href="addEmp.do?method=editEmployeeDetails&EmpNo=<bean:write name="empList" property="employeeNo"/>"><img src="images/edit1.jpg"/></a></td>
	</tr>
	</logic:iterate>
	
	</table> 		
	</logic:notEmpty>
	
</div>
		
		<logic:notEmpty name="noRecords">
		<table  border="1" class="bordered" style="aligh:left;">
	<tr align="left">
	<th>Emp&nbsp;No</th><th>Plant</th><th >Employee&nbsp;Name</th><th >Login&nbsp;Name</th><th >Designation</th><th >Department</th><th >Email&nbsp;ID</th><th >Contact No</th> </tr>
		
		<tr>
		<td colspan="8" align="center">
						<logic:present name="addExistingEMPForm" property="message">
						<font color="red">
							<bean:write name="addExistingEMPForm" property="message" />
						</font>
					</logic:present>
</td>			</tr>
		</table>
		
		</logic:notEmpty>
 
</html:form>
</body>

</html>