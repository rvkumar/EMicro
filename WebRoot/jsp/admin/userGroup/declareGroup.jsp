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
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>

<script type="text/javascript">

function addGroup(){

var checkBox=document.forms[0].selectedMainModulesArr;
var checked=false;

if(document.forms[0].groupName.value=='')
{
alert('please enter a group name');
document.forms[0].groupName.focus();
return false;
}

if(checkBox.checked==true)
	checked=true;
		for(var i=0;i<checkBox.length;i++)
			{
				if(checkBox[i].checked==true)
				checked=true;
				//alert(checkBox[i].value);
			}
				 
		if(checked==true){
			document.forms[0].action="userGroup.do?method=addGroup";
			document.forms[0].submit();
		}
		else
		alert("Assign atleast one module to the group..");
		
		return false;
}

function checkAll()
	{
		for(i=0; i < document.forms[0].selectedMainModulesArr.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selectedMainModulesArr[i].checked = true ;
			else
				document.forms[0].selectedMainModulesArr[i].checked = false ;
		}
	}
	
	function checkAllLocations()  
	{
	for(i=0; i < document.forms[0].selectedLocationsArr.length; i++){
			if(document.forms[0].checkLoc.checked==true)
				document.forms[0].selectedLocationsArr[i].checked = true ;
			else
				document.forms[0].selectedLocationsArr[i].checked = false ;
		}
	}
	


function display(){
	if(document.forms[0].message.value!="")  
		{
			alert(document.forms[0].message.value);
		}
	if(document.forms[0].statusMessage.value!="")  
		{
			alert(document.forms[0].statusMessage.value);
		}
		
		document.forms[0].message.value="";
		document.forms[0].statusMessage.value="";
		return false;
}


//-->
</script>
</head>

<body onload="display()">

		<%--<div align="center">
					<logic:present name="userGroupForm" property="message">
						<font color="red">
							<script type="text/javascript">
				
					displaymessage('<bean:write name="userGroupForm" property="message" />');
					</script>
						</font>
					</logic:present>
				</div>
				
				<div align="center">
					<logic:present name="userGroupForm" property="statusMessage">
						<font color="red">
							<script type="text/javascript">
				
					displaystatus('<bean:write name="userGroupForm" property="statusMessage" />');
					</script>
						</font>
					</logic:present>
				</div>
					
					--%><html:form action="userGroup.do">
					<html:hidden property="message"/>
					<html:hidden property="statusMessage"/>
				<div style="width: 50%">
		 		<table class="bordered" width="50%">
						<tr><th colspan="3"><center><big>User Groups</big></center></th></tr>
						<tr>
							<td>Group Name :</td>
							<td colspan="2"><html:text property="groupName" size="30%"/></td>
						</tr>
						<tr><th colspan="3"><center><big>Select Modules</big></center></th></tr>
						<tr>
							<td colspan="3"><center><big>
								<html:select name="userGroupForm" property="moduleName" disabled="true">
									<html:option value="main">Main</html:option>
								</html:select>
							</big></center></td>
						</tr>
						
						
						<logic:notEmpty name="userGroupForm" property="links">
						
						<tr>
						<th colspan="3"><center><b><font><input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll()"/>&nbsp;&nbsp;Select Links</font></b>
						
						</center></th>
						</tr>
						
						
						<input type="hidden" name="selectedMainModulesArr" value="Main"/>
						
						<logic:iterate id="vvv" name="userGroupForm" property="links">
							<tr>
								<td colspan="3"><input class="checkbox" type="checkbox"  name="selectedMainModulesArr"
								 value="<bean:write name="vvv" property="value"/>"/>&nbsp;&nbsp;<bean:write name="vvv" property="value"/>
								
								</td>
							</tr>
						</logic:iterate>
						</logic:notEmpty>  
						<tr><th colspan="3"><center><big>Select Locations</big></center></th></tr>
						<tr>
						<th colspan="3"><center><b><font><input class="checkbox" type="checkbox" name="checkLoc" onclick="checkAllLocations()"/>&nbsp;&nbsp;Select Links</font></b>
						
						</center></th>
						</tr>
						<logic:notEmpty name="userGroupForm" property="locations">
						<logic:iterate id="locID" name="userGroupForm" property="locations">
							<tr>
								<td colspan="3"><input class="checkbox" type="checkbox"  name="selectedLocationsArr"
								 value="<bean:write name="locID" property="id"/>"/>&nbsp;&nbsp;<bean:write name="locID" property="value"/>
								
								</td>
							</tr>
						</logic:iterate>
						
						</logic:notEmpty>
						
					<logic:notEmpty name="addGroupButton">
						<tr><td colspan="3"><div align=center>
						
						<html:button property="method" value="Add Group" onclick="addGroup()" styleClass="rounded"></html:button>
						
						<html:button property="method" value="Close" onclick="location.href='modifyUserGroup.do?method=display'" styleClass="rounded" />
						</div></td></tr>
						</logic:notEmpty>
					
					</table>
					</div>
							</html:form>
						

</body>
</html>
