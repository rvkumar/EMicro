
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/><link rel="stylesheet" type="text/css" href="css/styles.css" />
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<title>Home Page</title>

<script language="javascript">


function onSubmit(){
var url="it.do?method=saveITRequstDetails";
	document.forms[0].action=url;
	document.forms[0].submit();
}
		
</script>
</head>

<body >



				<div align="center">
					<logic:present name="itForm" property="message">
						<font color="red">
							<bean:write name="itForm" property="message" />
						</font>
					</logic:present>
				</div>
				
				
<html:form action="/it.do" enctype="multipart/form-data">
<% 
		String status=(String)session.getAttribute("status");		
		if(status==""||status==null)
		{
		
		}
		else{
		
		%>
		<b><font color="red"><%=status %></font></b>
		<%
		session.setAttribute("status", " ");
		}
		 %>

					<table class=forumline align=center width='70%' border="1"> 
	<tr>
		<td align=center colspan=5 bgcolor="#51B0F8"><font color="white">
		<center>IT Request Form</center>	</font>	
		</td>
	</tr>
	<tr>
 
	<td>
	Category 	
	<html:select property="category" name="itForm" style="margin-left:100">
	<html:option value="">Select Category</html:option>
	  <html:option value="Desktop">Desktop</html:option> 
      <html:option value="Laptop">Laptop</html:option> 
      <html:option value="Internet">Internet</html:option> 
      <html:option value="Network">Network</html:option> 
       <html:option value="Printers">Printers</html:option> 
      <html:option value="SAP">SAP</html:option>      
      <html:option value="Legacy Application">Legacy Application</html:option>
      <html:option value="Software">Software</html:option>  
      <html:option value="VOIP Phone">VOIP Phone</html:option>      
      <html:option value="User Administration">User Administration</html:option>	
	</html:select>
	</td>
	<td>
	Status
	<html:select property="status" name="itForm" style="margin-left:100">
			<html:option value="">Select Status</html:option>
			<html:option value="Closed">Closed</html:option> 
		    <html:option value="On Hold">On Hold</html:option> 
		    <html:option value="Open">Open</html:option> 
		    <html:option value="4">Resolved</html:option>
	</html:select>	
	</td>
	</tr>
	<tr>
	<td>
	Mode  
	<html:select property="mode" name="itForm" style="margin-left:122">
			<html:option value="">Select Mode   </html:option>
			<html:option value="Portal">Portal</html:option> 
		    <html:option value="Phone">Phone</html:option> 
		    <html:option value="Email">Email</html:option> 
		  
	</html:select>	
	</td>
	<td>
	Priority
	<html:select property="priority" name="itForm" style="margin-left:100">
			<html:option value="">Select Mode</html:option>
			<html:option value="Low">Low</html:option> 
		    <html:option value="Medium">Medium</html:option> 
		    <html:option value="Normal">Normal</html:option> 
		  
	</html:select>	
	</td>
	</tr>
	<tr>				
	
	<tr>
		<td align=center colspan=5 bgcolor="#51B0F8"><font color="white">
		Requester Details:	</font>	
		</td>
		</tr>
		
	<tr>
	<td>
	Name	
	<html:text property="name" style="margin-left:100"></html:text>
	</td>
	<td>
	Assigned To<html:text property="assignedTo" style="margin-left:100"></html:text>
	</td>
	</tr>
	<tr><td>
	Asset Details<html:text property="assetDetails" style="margin-left:50"/>
	</td>
	<td>
	Contact Number<html:text property="contactNo" style="margin-left:80"></html:text>
	</td></tr>
	<tr>
	<td>
	Department<html:text property="department" style="margin-left:60"/>
	</td>
	<td>
	Job Title<html:text property="jobTitle" style="margin-left:130"/>
	</td></tr>
	<tr>
	<td>
	Created By<html:text property="createdBy" style="margin-left:70"/>
	</td>
	<td>
	Plant<html:text property="plant" style="margin-left:150"></html:text>
	</td>
	</tr>
	<tr>
	<td>
	Location Details<html:text property="locationDetails" style="margin-left:45"></html:text>
	</td>
	<td>
	Intercom Number<html:text property="intercomNo" style="margin-left:70"></html:text>
	</td>
	</tr>
	
	<tr><td>
	System IP Address<html:text property="IPAdress" style="margin-left:30"></html:text>
	</td>
	<td>
	Sub Category<html:text property="subCategory" style="margin-left:100"></html:text>
	</td>	
	</tr>
	<tr><td>
	Requester Id<html:text property="requesterId" style="margin-left:70"></html:text>
	</td></tr>
	<tr>
	<td>
	Due By
	<html:text property="dueBy" style="margin-left:100"></html:text>
	</td>
	</tr>
	<tr>
	<td colspan="2">
	Subject<html:text property="subject"  style="width:600;margin-left:5;"></html:text>
	</td></tr>
	
	<tr>
	<td colspan="2">
	<b>Description</b>
	<FCK:editor instanceName="EditorDefault">
								<jsp:attribute name="value">
				             
				               </jsp:attribute>
				            
							</FCK:editor>
	
	</td>
	</tr>
	<logic:notEmpty name="submitButton">
                                                       
                                                       <table align="center">
                                                               <tr>
                                                               <td colspan="4">
                                                               <div align="center">
                                                               <html:submit value="submit" styleClass="button" property="method" onclick="onSubmit()"/>
                                                               </div>
                                                               </td>
                                                               </tr>
                                                               </table>
                                                       
                                               </logic:notEmpty>
	
	</table>        
            </html:form>



</body>
</html>
