
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Home Page</title>

<script language="javascript">

	function openPage(param)
		{
		var x = window.open("approvalLevels.do?method=displayEmpDetails&param="+param,"SearchSID","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
		}
		
function modifyDetails(){
	var url="authentication.do?method=modifyAuthenticationDetails";
	document.forms[0].action=url;
	document.forms[0].submit();
}
function deleteDetails(){

var agree = confirm('Are you sure.you want to delete record');
if(agree)
{
var url="authentication.do?method=deleteAuthenticationDetails";
document.forms[0].action=url;
document.forms[0].submit();
}
else
{

}
}
		
function addRow(tableID)
	{
	var table=document.getElementById(tableID);
	var rowCount=table.rows.length;
	var row=table.insertRow(rowCount);
	var colCount=5;
	for(var i=0;i<colCount;i++)
	{
	var newcell=row.insertCell(i);
	newcell.innerHTML=table.rows[0].cells[i].innerHTML;
	switch(newcell.childNodes[0].type)
	{
	case"text":newcell.childNodes[0].value="Employee ID";
	break;
	case"checkbox":newcell.childNodes[0].checked=false;
	break;
	
	}
	}
	}
	
function deleteRow(tableID)
{
try{
var table=document.getElementById(tableID);

var rowCount=table.rows.length;

if(rowCount==1)
{

var table=document.getElementById('dataTable1');

var rowCount=table.rows.length;

}
for(var i=0;i<rowCount;i++){
var row=table.rows[i];
var chkbox=row.cells[0].childNodes[0];
if(null!=chkbox&&true==chkbox.checked){
if(rowCount<=1){
alert("Cannot delete all the rows.");
break;}
table.deleteRow(i);rowCount--;i--;
}
}
}
catch(e)
{
alert(e);
}
}
function ClearEMPID (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('myEl').style.cssText = 'color: black';
                input.value = "";
            }
        }
        function SetEMPID (input) {
            if (input.value == "") {
            
            document.getElementById('myEl').style.cssText = 'color: grey';
                input.value = input.defaultValue;
            }
        }
function ClearDesignation (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('design').style.cssText = 'color: black';
                input.value = "";
            }
        }
function SetDesignation (input) {
    if (input.value == "") {
    
    document.getElementById('design').style.cssText = 'color: grey';
        input.value = input.defaultValue;
    }
} 
function ClearEmail (input) {

            if (input.value == input.defaultValue) {
            document.getElementById('email').style.cssText = 'color: black';
                input.value = "";
            }
        }
function SetEmail (input) {
    if (input.value == "") {
    
    document.getElementById('email').style.cssText = 'color: grey';
        input.value = input.defaultValue;
    }
}   
function goBack (input) {
   var url="authentication.do?method=displaynewForm";
document.forms[0].action=url;
document.forms[0].submit();
}     
</script>
</head>

<body >
<html:form action="authentication" enctype="multipart/form-data">
<div align="center">
						<logic:present name="authenticationForm" property="message">
						<font color="red">
							<bean:write name="authenticationForm" property="message" />
						</font>
					</logic:present>
					</div>

	<table align="center" width="60%" id="mytable">
	<tr><td colspan="3"><header id="header"><h2> Approvers Details</h2> </header></td></tr>
	<tr>
	<th width="100" class="specalt" scope="row">Request Type</th>
	<td><html:select  property="requestType" onchange="getInfo()" tabindex="1" >
					<html:option value="">--SELECT--</html:option>
					<html:option value="Update Personnel info">Update Personnel info</html:option>
					<html:option value="Leave" >Leave</html:option>
					<html:option value="On Duty" >On Duty</html:option>
					<html:option value="Apply VPF" >Apply VPF</html:option>
					<html:option value="Apply Investment" >Apply Investment</html:option>
					<html:option value="Claim HRA/TR Lp" >Claim HRA/TR Lp</html:option>
					<html:option value="LTA" >LTA</html:option>
					<html:option value="Income From Previous Employment" >Income From Previous Employment</html:option>
					<html:option value="Apply for Domestic Travel" >Apply for Domestic Travel</html:option>
					<html:option value="Apply for International Travel" >Apply for International Travel</html:option>
				</html:select>
					
		</td>
	
		<html:hidden property="approverID"/>
	
		</tr>
		
		<tr>
	<logic:notEmpty name="approverList">
	<TABLE id="dataTable1" width="250px"  align="center">
<logic:iterate id="abc" name="approverList">

	<TR>
			
			<TD><html:text property="approverID" value="${abc.apprID }" ></html:text></TD>
			<TD><html:text property="designation" value="${abc.empDesignation }" ></html:text></TD>
			<TD><html:text property="emailID" value="${abc.empEmailID }" ></html:text></TD>
			
		</TR>


</logic:iterate>

</TABLE>	
	</logic:notEmpty>
		
		
	</table>
	<table align="center">
	<tr>
		<td align="right" >
		<html:button property="method" value="Back"  styleClass="sudmit_btn" style="width:75px; height:30px;" onclick="goBack()"></html:button>
		</td>
		</tr>
		</table>
	
	
</html:form>


</body>
</html>
