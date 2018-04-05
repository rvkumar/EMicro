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
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<title>Microlab</title>
<script type="text/javascript">

<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}


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

function sendId(eNo,uName,pwd,fName,cName,state,plant,department,desig,eType,gId)
{
	
	opener.document.forms[0].employeeNumber.value = eNo;
	opener.document.forms[0].userName.value = uName;
	opener.document.forms[0].password.value = pwd;
	opener.document.forms[0].fullName.value = fName;
	opener.document.forms[0].countryName.value = cName;
	opener.document.forms[0].state.value = state;
	opener.document.forms[0].plant.value = plant;
	opener.document.forms[0].department.value = department;
	opener.document.forms[0].designation.value = desig;
	opener.document.forms[0].employeeType.value = eType;
	
	
	opener.document.forms[0].groupID.value = gId;
	
	window.open('addUser.do?method=searchUser','_parent','');
	window.close();
}
	




//-->
</script>
</head>

<body>
		<!--------WRAPER STARTS -------------------->
                    
                    
               
                
             
					
					<div align="center">
					<logic:present name="userForm" property="message">
						<font color="red">
							<bean:write name="userForm" property="message" />
						</font>
					</logic:present>
				</div>

					
				<html:form action="/addUser">
					
		<table border="1" align="center" id="mytable" >
						<tr><td colspan=2 bgcolor="#51B0F8"><div align=center>
						<font color="white">Search Employee Id</font></div></td></tr>
						
							<tr>
								<td>Employee Type</td>
								
									<td>
										<html:select name="userForm" property="employeeType" onchange="deptSelected();">
											<html:option value="">--Select--</html:option>
									 		<html:option value="temp">Temporary</html:option>
									 		<html:option value="per">Permanent</html:option>
									 		<html:option value="admin">Admin</html:option>
										</html:select>
									</td>
							</tr>
							
							
							
							<tr>
							<td>Country Name<img src="images/smallindex.jpg"/></td>
							
							       <td>
										<html:select name="userForm" property="countryName">
											<html:option value="">--Select--</html:option>
									 		<html:option value="india">India</html:option>
									 		<html:option value="per">Indonesia</html:option>
									 		<html:option value="admin">Malesiya</html:option>
										</html:select>
									</td>
						</tr>
						
						
						<tr>
							<td>State<img src="images/smallindex.jpg"/></td>
							
							<td>
							<html:select name="userForm" property="state">
									<html:option value="">--Select--</html:option>
									<html:option value="Karana">Karanataka</html:option>
									<html:option value="ap">Andhra Pradesh</html:option>
									<html:option value="punjab">Punjab</html:option>
							</html:select>
							</td>
							
						</tr>
						
						<tr>
							<td>Plant<img src="images/smallindex.jpg"/></td>
							
							<td>
							<html:select name="userForm" property="plant">
								<html:option value="">--Select--</html:option>
								<html:option value="hosur">Hosur Plant</html:option>
							</html:select>
						</td>
						</tr>
						
						<tr>
						
						<td>Department<img src="images/smallindex.jpg"/></td>
						<td>
							<html:select name="userForm" property="department" >
								<html:option value="">--Select--</html:option>
								<html:option value="IT">IT</html:option>
							</html:select>
						</td>
						
						</tr>
						
						<tr>
							<td>Designation<img src="images/smallindex.jpg"/></td>
							
							<td>
							<html:select name="userForm" property="designation" >
									<html:option value="">--Select--</html:option>
									<html:option value="Manager">Manager</html:option>
									<html:option value="ITHead">IT Head</html:option>
							</html:select>
							</td>
						</tr>
						
						
					<tr>
							<td colspan="2">
							<div align="center">
							   <html:button property="method" styleClass="rudraButtonCSS" value="Search" onclick="searchUsers()"/>
							   </div>
							</td>
						</tr>		
							
							
					</table>
					<br/>
					<br/>
					
					
					<logic:notEmpty name="userDetails">
								<table class=forumline align=center width='60%' border="1">
									<tr>
										<td align=center colspan=6 bgcolor="#51B0F8">
											<font color="white">User Details</font>
										</td>
										
									</tr>
									
									<tr height='20'>
									
										<td>
											<b>Sl. No</b>
										</td>
										
										<td>
											<b>User Name</b>
										</td>
										
										<td>
											<b>Country</b>
										</td>
										
										<td>
											<b>State</b>
										</td>
										
										<td>
											<b>Plant</b>
										</td>
										
										<td>
											<b>Department</b>
										</td>
										
									</tr>
									<%
									int count = 1;
									%>
									<logic:iterate name="userDetails" id="abc">
										<tr>
										
											<td class="lft">
												<a href="#" 
												onclick="sendId('${abc.userId}','${abc.userName}','${abc.password}'
												,'${abc.fullName}','${abc.countryName}','${abc.state}'
												,'${abc.plant}','${abc.department}','${abc.designation}','${abc.employeeType}'
												,'${abc.groupID}')">
													<%=count++%></a>
											</td>
											
											<td>
												<bean:write name="abc" property="userName"/>
											</td>
											
											<td>
												<bean:write name="abc" property="countryName"/>
											</td>
											
											<td>
												<bean:write name="abc" property="state"/>
											</td>
											
											<td>
												<bean:write name="abc" property="plant"/>
											</td>
											
											<td>
												<bean:write name="abc" property="department"/>
											</td>
											
											</tr>
									</logic:iterate>
									
									</logic:notEmpty>
								</table>

							</html:form>
				</body>
</html>
