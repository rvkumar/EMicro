
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>


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
<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">

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

	function searchusers(groupid,groupname,groupcount)
		{
		var url="modifyUserGroup.do?method=search_assign_user"+"&groupid="+groupid+"&groupname="+groupname+"&groupcount="+groupcount;
					document.forms[0].action=url;
					document.forms[0].submit();
		}
  
  function display()
    {
    
    if(document.forms[0].message.value!="")
    {
    alert(document.forms[0].message.value);
    }
    
    document.forms[0].message.value=="";
    return false;
    
    }
    
    function firstUserRecord(groupid,groupname,groupcount)
	{

		
		var url="modifyUserGroup.do?method=firstUserRecord"+"&groupid="+groupid+"&groupname="+groupname+"&groupcount="+groupcount;
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function previousUserRecord(groupid,groupname,groupcount)
	{

		var url="modifyUserGroup.do?method=previousUserRecord"+"&groupid="+groupid+"&groupname="+groupname+"&groupcount="+groupcount;
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function nextUserRecord(groupid,groupname,groupcount)
	{

		var url="modifyUserGroup.do?method=nextUserRecord"+"&groupid="+groupid+"&groupname="+groupname+"&groupcount="+groupcount;
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function lastUserRecord(groupid,groupname,groupcount)
	{

		var url="modifyUserGroup.do?method=lastUserRecord"+"&groupid="+groupid+"&groupname="+groupname+"&groupcount="+groupcount;
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function Close(){

	
		var url="modifyUserGroup.do?method=close_assign_user";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function Assign(){

	var rows=document.getElementsByName("selected_unselected_user");
	
	var checkvalues='';
	var uncheckvalues='';
	for (var i =0; i < rows.length; i++)
				{
				//alert('rows is ******************'+rows[i].value);
				if (rows[i].checked)
				{
				checkvalues+=rows[i].value+',';
				}else{
				uncheckvalues+=rows[i].value+',';
				}
				}
		var url="modifyUserGroup.do?method=assign_user_to_group&cValues="+checkvalues+"&unValues="+uncheckvalues;
		document.forms[0].action=url;
		document.forms[0].submit();
	
	}
	
	function clearUsers()
	{
		
		var url="modifyUserGroup.do?method=clearUsers";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	
	function checkAll()
	{
		for(i=0; i < document.forms[0].selected_unselected_user.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selected_unselected_user[i].checked = true ;
			else
				document.forms[0].selected_unselected_user[i].checked = false ;
		}
	}
	
	

</script>

<style type="text/css">
#slideshow {position:relative; margin:0 auto;}
#slideshow img {position:absolute; display:none}
#slideshow img.active {display:block}
</style>


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
</style>


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
</head>

<body onload="display()">
		<!--------WRAPER STARTS -------------------->


		<html:form action="modifyUserGroup.do" method="post" onsubmit="searchusers('${modifyUserGroupForm.groupid}','${modifyUserGroupForm.groupname}','${modifyUserGroupForm.groupcount}')">
		<html:hidden property="message"/>
		<html:hidden property="searchRequired"/>
		<div style="width: 90%">
			<table class="bordered">
			<tr>
 				<th colspan="7"><center><big>Assign Users into Group</big></center></th>
			</tr>
			<tr>
			<td colspan="1" width="13%">
			<b>Group Name:</b>	
			</td>
			<td colspan="1">
			
<%--			<html:text property="groupname" styleClass="rounded" style="width:100px;color:#071907;" disabled="true"/>--%>
			<bean:write property="groupname" name="modifyUserGroupForm"/>
			
			<html:hidden property="groupid"/>
			<html:hidden property="groupname"/>
			<html:hidden property="groupcount"/>
			</td>
			<td colspan="1">
			<b>No. of users in this group:</b>
			</td>
			<td colspan="1">
			<bean:write property="groupcount" name="modifyUserGroupForm"/>
			</td>
			<td colspan="1">
			Group
			</td>
			<td colspan="2">
			<html:select property="group"  styleClass="rounded">
					<html:option value="">--Select--</html:option>
         			<html:options property="groupIdList" labelProperty="groupLabelList"></html:options>    
      			</html:select>
			</td>
			</tr>
			
			<tr>
			<td colspan="1">Plant</td>
		 	<td colspan="1">
		  		<html:select property="locationId" styleClass="rounded">
			    	<html:option value="">--Select--</html:option>
			      	<html:options property="locationIdList" labelProperty="locationLabelList"></html:options>  
				</html:select>
			</td>
	  
	   		<td colspan="1">Dept</td>
		 	<td colspan="1">
				<html:select property="department"  styleClass="rounded">
					<html:option value="">--Select--</html:option>
         			<html:options property="departmentList"></html:options>  
      			</html:select>
      		</td>
      		<td colspan="3" width="25%">
   			<a href="#"><img src="images/clearsearch.jpg" align="absmiddle" title="Clear..."  onclick="clearUsers()"/></a>
    			<html:text property="firstName" styleId="prdsname" title="Name/Employee Code/Email/Desg/Ext No/IP Phone" maxlength="30" size="15" styleClass="rounded"/>
	      		<a href="#"><img src="images/search.png" align="absmiddle" title="Search..."  onclick="searchusers('${modifyUserGroupForm.groupid}','${modifyUserGroupForm.groupname}','${modifyUserGroupForm.groupcount}')"/></a>
	      </td>
		</tr>
			
			
			
	</table>
<br/>
	
		<tr>
			
			<logic:notEmpty name="displayRecordNo">
	
	  	<td colspan="7">
	  	<img src="images/First10.jpg" onclick="firstUserRecord('${modifyUserGroupForm.groupid}','${modifyUserGroupForm.groupname}','${modifyUserGroupForm.groupcount}')" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousUserRecord('${modifyUserGroupForm.groupid}','${modifyUserGroupForm.groupname}','${modifyUserGroupForm.groupcount}')" align="absmiddle"/>
	
	</logic:notEmpty>
	
	
	
	<bean:write property="startRecord"  name="modifyUserGroupForm"/>
	
	
	<bean:write property="endRecord"  name="modifyUserGroupForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextUserRecord('${modifyUserGroupForm.groupid}','${modifyUserGroupForm.groupname}','${modifyUserGroupForm.groupcount}')" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastUserRecord('${modifyUserGroupForm.groupid}','${modifyUserGroupForm.groupname}','${modifyUserGroupForm.groupcount}')" align="absmiddle"/>
	
	
	</td>
	
	
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>&nbsp;&nbsp;
	</logic:notEmpty>
	
	
	<html:button property="method" value="Close" onclick="Close()" styleClass="rounded" />
			
			</tr>
	
		<logic:notEmpty name="contactlist" >
		
		&nbsp;&nbsp;<html:button property="method" value="Assign" onclick="Assign()" styleClass="rounded" />
		
			<div align="left" class="bordered">
			<table class="sortable">
				<tr>
					<th><input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll()"/>&nbsp;&nbsp;Select All</th><th >Employee Code</th><th >Full Name</th><th>Designation</th><th >Department</th><th >Location</th><th >Group</th>
				</tr>

				<logic:iterate id="empList" name="contactlist">
				<bean:define id="gid" property="egroupname" name="empList"/>
				<bean:define id="grnamec" property="groupname" name="modifyUserGroupForm"/>
				<html:hidden property="empcode"/>
				
					<tr>
						
<%--						<td><html:checkbox property="reqempcode" value="${empList.empcode}" styleId="empcode"/></td>--%>
						<td><input class="checkbox" type="checkbox" name="selected_unselected_user" value="<bean:write name="empList" property="empcode"/>"
						<%
						System.out.println(gid);
						
						
						if((gid.equals(grnamec))){
						
						String name="Akshay";
						out.println("checked='checked'");
						
						
						 }%>/>
						 
						 </td>
						<td><bean:write name="empList" property="empcode"/></td>												
						<td><bean:write name="empList" property="firstName"/></td>
						<td><bean:write name="empList" property="designation"/></td>
						<td><bean:write name="empList" property="department"/></td>
						<td><bean:write name="empList" property="locationId"/></td>
						<td><bean:write name="empList" property="egroupname"/></td>
						
					</tr>
				</logic:iterate>
			</table> 
			</div>
		</logic:notEmpty>
	
		<logic:notEmpty name="noRecords">
			<table class="bordered sortable">
				<tr>
					<!-- th>ID</th-->
					<th>Employee Name</th><th >Designation</th><th >Department</th><th>Plant</th><th >Email</th><th >Contact No</th>
				</tr>
				<tr><td colspan="6">	
				<logic:present name="modifyUserGroupForm" property="message">
					<div align="center">
						<font color="red">
							<bean:write name="modifyUserGroupForm" property="message" />
						</font>
					</div>
				</logic:present>
				</td></tr>
			</table>
		</logic:notEmpty></div>

			
			
			
		
	

</html:form>
                

</body>
</html>
