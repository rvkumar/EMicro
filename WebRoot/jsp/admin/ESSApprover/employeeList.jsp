<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">
function lastMaterialRecord()
{
var url="essApprover.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function firstMaterialRecord()
{
var url="essApprover.do?method=employyList";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function nextMaterialRecord()
{
var url="essApprover.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousMaterialRecord()
{
var url="essApprover.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function addESSApprover(){
var URL="essApprover.do?method=newApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function viewEmpDetails(employeeNo,essType)
{
var URL="essApprover.do?method=editEmpDetails&EmpNo="+employeeNo+"&EssType="+essType;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function searchEmp(){
var URL="essApprover.do?method=searchEmpRecord";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function statusMessage(message){
alert(message);
}
</script>
</head>

<body>
<html:form action="essApprover.do" onsubmit="searchEmp(); return false;">
<div align="center">
			<logic:present name="essApproverForm" property="message">
			
			<script language="javascript">
					statusMessage('<bean:write name="essApproverForm" property="message" />');
					</script>
		</logic:present>
		<logic:present name="essApproverForm" property="message2">
				<script language="javascript">
					statusMessage('<bean:write name="essApproverForm" property="message2" />');
					</script>
		</logic:present>
		</div>
<html:button property="method" value=" New " styleClass="rounded" onclick="addESSApprover()"></html:button>
&nbsp;&nbsp;<html:text property="searchKeyword"  styleClass="rounded" style="width:200px;" title="emp.no,name,department,type"></html:text>
&nbsp;&nbsp;<a href="#"><img src="images/search.png" onclick="searchEmp()" align="absmiddle"/></a>
<br/><br/>
<logic:notEmpty name="displayRecordNo">
	<center><table align="center">
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstMaterialRecord()" align="absmiddle"/>
	<logic:notEmpty name="disablePreviousButton">
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	</logic:notEmpty>
	<logic:notEmpty name="previousButton">
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>
	</logic:notEmpty>
	<bean:write property="startRecord"  name="essApproverForm"/>-
	<bean:write property="endRecord"  name="essApproverForm"/>
	<logic:notEmpty name="nextButton">
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<img src="images/disableRight.jpg" align="absmiddle" />

	
	</logic:notEmpty>
	
		<img src="images/Last10.jpg" onclick="lastMaterialRecord()" align="absmiddle"/>
	</td>
	
	<html:hidden property="totalRecords" name="essApproverForm"/>
	<html:hidden property="startRecord" name="essApproverForm"/>
	<html:hidden property="endRecord" name="essApproverForm"/>
	</logic:notEmpty>
	 </tr>
	 </table></center>
	<br/>
	<logic:notEmpty name="empList">
	
	<table class="sortable bordered">
	<tr>
	<th style="width:50px;">Emp.No</th><th >Employee Name</th><th>Department</th><th >Type</th>
	<th>View</th>
	</tr>
	<logic:iterate id="emp" name="empList">
	<tr>
						<td><bean:write name="emp" property="employeeNo"/></td>
						<td><bean:write name="emp" property="empname"/></td>
						<td><bean:write name="emp" property="department"/></td>
						<td><bean:write name="emp" property="essType"/></td>
						<td><a href="#">
      							<img src="images/view.gif" height="28" width="28" title="View Record" onclick="viewEmpDetails('${emp.employeeNo}','${emp.essType}')"/></a>
      				</td>
	</tr>					
	</logic:iterate>
	</logic:notEmpty>


</html:form>
</body>
</html>