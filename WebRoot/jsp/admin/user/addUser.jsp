
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
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

<link href="style/inner_tbl.css" rel="stylesheet" type="text/css" />
<title>Microlab</title>

<style type="text/css">
a:link {
	text-decoration: none;
}
a:visited {
	text-decoration: none;
}
a:hover {
	text-decoration: none;
}
a:active {
	text-decoration: none;
}
.style2 {	color: #df1e1c; font: bold 11px "Arial", Verdana, Arial, Helvetica, sans-serif;	font-size: 12px;
}
</style>

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

function onSubmit(){
	if(document.forms[0].linkName.value=="")
	    {
	      alert("Link Name should not be left blank");
	      document.forms[0].linkName.focus();
	      return false;
	    }
	    else if(document.forms[0].subLinkName.value=="")
	    {
	      alert("SubLink Name should not be left blank");
	      document.forms[0].subLinkName.focus();
	      return false;
	    }
	    else if(document.forms[0].linkPath.value=="")
	    {
	      alert("Link Path should not be left blank");
	      document.forms[0].linkPath.focus();
	      return false;
	    }
	    else if(document.forms[0].methodName.value=="")
	    {
	      alert("Method Name should not be left blank");
	      document.forms[0].methodName.focus();
	      return false;
	    }
	     else if(document.forms[0].priority.value=="")
	    {
	      alert("Priority should not be left blank");
	      document.forms[0].priority.focus();
	      return false;
	    }
	   
	    var x = document.forms[0].priority.value;
           if(isNaN(x)||x.indexOf(" ")!=-1)
           {
              alert("enter numeric value for Priority")
              return false; 
           }
		var url="links.do?method=submit";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function displaySublinks(){
		var url="links.do?method=displaySublinks";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function onModify(){
		var url="links.do?method=select";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function onDelete(){
		var url="links.do?method=delete";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	function onUpload(){	   
			var url="links.do?method=uploadFiles";
			document.forms[0].action=url;
			document.forms[0].submit();	
	}
	
function onUploadVideo(){
	
	var url="links.do?method=uploadVideos";
	document.forms[0].action=url;
	document.forms[0].submit();
	}
function onDeleteFile()
{
var fileChecked=0;
var fileLength=document.forms[0].select.length;
var fileLength1=document.forms[0].select.checked;
if(fileLength1==true && fileLength==undefined)
{
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="links.do?method=deleteFileList";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}
else
{
for(i=0;i<fileLength;i++)
{
if(document.forms[0].select[i].checked==true)
{
fileChecked=fileChecked+1;
}
}
if(fileChecked==0)
{
alert('Select Atleast One Record To Delete');
return false;
}
else
{
}
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="links.do?method=deleteFileList";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}	
}
function onDeleteVideo()
{
var videoChecked=0;
var videoLength=document.forms[0].select1.length;
var videoLength1=document.forms[0].select1.checked;

if(videoLength1==true && videoLength==undefined)
{
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="links.do?method=deleteVideoList";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}
else
{
for(i=0;i<videoLength;i++)
{
if(document.forms[0].select1[i].checked==true)
{
videoChecked=videoChecked+1;
}
}
if(videoChecked==0)
{
alert('Select Atleast One Record To Delete');
return false;
}
else
{
}
var agree = confirm('Are You Sure To Delete Selected Requests');
if(agree)
{
var url="links.do?method=deleteVideoList";
	document.forms[0].action=url;
	document.forms[0].submit();
}
else
{
return false;
}
}	
}

function searchEmployeeId()
{
	var x = window.open("addUser.do?method=searchSid","SearchSID","width=500,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
}

 function modifyUser()
    {
		document.forms[0].action="addUser.do?method=modifyUser";
		document.forms[0].submit();
	}



//-->
</script>
</head>

<body >
		<!--------WRAPER STARTS -------------------->
<div id="wraper">

                
                <div style="padding-left: 10px;width: 70%;"><!--------CONTENT MIDDLE STARTS -------------------->
                	
                    <div>
					
					
						
									<div align="center">
					<logic:present name="userForm" property="message">
						<font color="red">
							<bean:write name="userForm" property="message" />
						</font>
					</logic:present>
				</div>
	
					<html:form action="addUser.do">
					
		<table border="0" align="center" id="mytable1" >
						<tr>
						  <th colspan="2">Add Users</th>
						</tr>
						
						
					    
					     <tr>
                  <td colspan="2" scope="row"><div align="left" class="style2">*Star Marks are Mandatory fields*</div>
                    </th></td>
                </tr>
					    
					    
					    <html:hidden property="employeeType"/>
					    
					    
						<tr>
						
						 <th width="274" class="specalt" scope="row">Employee Number<img src="images/star.gif" width="8" height="8" /></th>
						
							 
							<td><html:text property="employeeNumber" readonly="true"/>&nbsp;<html:button property="method" styleClass="rudraButtonCSS" value="Search" onclick="searchEmployeeId();"></html:button></td>
						</tr>
						
						
						<tr>
							<th width="274" class="specalt" scope="row">User Name<img src="images/star.gif" width="8" height="8" /></th>
							
							<td><html:text property="userName" readonly="true"/></td>
						</tr>
						
						
						<tr>
						<th width="274" class="specalt" scope="row">Password<img src="images/star.gif" width="8" height="8" /></th>
							<td><html:password property="password" readonly="true"/></td>
						</tr>
						
						<tr>
								<th width="274" class="specalt" scope="row">Full Name<img src="images/star.gif" width="8" height="8" /></th>
							<td><html:text property="fullName"/></td>
						</tr>
						
						
						<tr>
								<th width="274" class="specalt" scope="row">Country Name<img src="images/star.gif" width="8" height="8" /></th>
							<td>
								<html:text property="countryName"/>
							</td>
						</tr>
						
						
						<tr>
								<th width="274" class="specalt" scope="row">State<img src="images/star.gif" width="8" height="8" /></th>
							<td>
							<html:text property="state" readonly="true"/>
							</td>
							
						</tr>
						
						<tr>
								<th width="274" class="specalt" scope="row">Plant<img src="images/star.gif" width="8" height="8" /></th>
							<td>
							<html:text property="plant" readonly="true"/>
						</td>
						</tr>
						
						<tr>
								<th width="274" class="specalt" scope="row">Department<img src="images/star.gif" width="8" height="8" /></th>
							<td>
							<html:text property="department" readonly="true"/>
						</td>
						</tr>
						
						<tr>
								<th width="274" class="specalt" scope="row">Designation<img src="images/star.gif" width="8" height="8" /></th>
							
							<td>
							<html:text property="designation" readonly="true"/>
							</td>
						</tr>
						
						
						<tr>
								<th width="274" class="specalt" scope="row">User Group<img src="images/star.gif" width="8" height="8" /></th>
							<td>
								<html:select name="userForm" property="groupID" onchange="reFresh();" >
									<html:option value="">--Select Group--</html:option>
									<html:options property="groupIdList" labelProperty="groupValueList" name="userForm"/>
								</html:select>
							</td>
						</tr>
						
						
						<tr>
						
						  <td colspan="2" align="center" class="lft"><div align="center">
                   			<html:button property="method" styleId="btn_personal" value="Modify User" onclick="modifyUser();">
							</html:button>
							
							<html:button property="method" value="Delete User" styleId="btn_personal"  onclick="deleteUser();">
							</html:button>
                  </div></td>
						
						</tr>
					</table>


							</html:form>
						</div>
                
            
            </div> <!--------CONTENT ENDS -------------------->
            
            
</div><!--------WRAPER ENDS -------------------->
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td bgcolor="#FFFFFF">&nbsp;</td>
    
  </tr>
</table>



                

</body>
</html>
