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
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">




function search(){
	var form = document.getElementById('searchSidFrm');
	form.action="user.do?method=searchSid";
	form.submit();
}




function deptSelected(){
	var form = document.getElementById('searchSidFrm');
	form.action="user.do?method=searchSid";
	form.submit();
}


function searchUsers()
{
	document.forms[0].action="addUser.do?method=searchUser";
	document.forms[0].submit();
}

function sendId(uName)
{
	opener.document.forms[0].reportingManger.value = uName;
	
	window.open('addUser.do?method=searchUser','_parent','');
	window.close();
}


	function searchContacts()
		{
		var url="hrNewEmp.do?method=searchContacts";
					document.forms[0].action=url;
					document.forms[0].submit();
		}
		
	function updateData()
    {

    	var i=document.forms[0].locationId.value;
    	
    	var e=document.forms[0].curentDate.value;
    		
        var emplist=document.forms[0].emplist.value;
    	
    	
    	
	
		var url="hrApprove.do?method=shiftManualAssignUpdate&currentDate="+e+"&plant="+i+"&emplist="+emplist;
					document.forms[0].action=url;
					document.forms[0].submit();
			/* 		window.close(); 
		            window.opener.statusMessage("Submitted Succesfully"); */
		

		
		}

function closeWindow(){
	window.close();
}




//-->
</script>

<!-- <script>
    window.onunload = refreshParent;
    function refreshParent() {
    var x=window.parent.property;
    
    
        window.close();
        
      
         window.opener.location.reload(); 
    } 
</script> -->
</head>

<body>
		<!--------WRAPER STARTS -------------------->

       
            
     		<div align="left">
				<logic:present name="hrApprovalForm" property="message">
					<font color="red" size="3"><b><bean:write name="hrApprovalForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="hrApprovalForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="hrApprovalForm" property="message2" /></b></font>
				</logic:present>
			</div>
					
				<html:form action="/hrApprove.do">
				<html:hidden property="emplist"  name="hrApprovalForm" />
			<div style="width:50%;" align="center">		
	<table width="50%" class="bordered" align="center">
	

					
					
		<tr>
			<th>Plant</th>
		 	<td colspan="2">
		  		<bean:write name="hrApprovalForm" property="locationId"/>
			</td>
			<html:hidden property="locationId"/>
			</tr><th>Selected Date</th>
				<td colspan="2">
		  		<bean:write name="hrApprovalForm" property="curentDate"/>
		  	
			</td>
				<html:hidden property="curentDate"/>
			
			<tr>
			<td>
			Shift
			</td>
			<td>
			<html:select property="shift">
			
			<html:options name="hrApprovalForm"  property="shiftList" labelProperty="shiftLabelList"/>
			</html:select>
			</td>
			
			</tr>
			
			<tr>
			
	   		<td colspan="4" style="text-align: center;">
					<html:button property="method"  value="Update" onclick="updateData()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
					<html:button property="method"  value="Close" onclick="closeWindow()" styleClass="rounded" style="width: 100px"></html:button>
				</td>
			</tr>
			
			</table> 
			</div>
				
		


							</html:form>
            
            

<!-------------- FOOTER STARTS ------------------------->
	
</body>
</html>
