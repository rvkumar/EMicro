
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
			document.forms[0].action="modifyUserGroup.do?method=addGroup";
			document.forms[0].submit();
		}
		else
		alert("Assigne atleast one module to the group..");
		
		return false;
}


function checkAll()
	{
		for(i=0; i < document.forms[0].selectedLinksArr.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selectedLinksArr[i].checked = true ;
			else
				document.forms[0].selectedLinksArr[i].checked = false ;
		}
	}
	
	function reFresh(str){
	
	if(str=='group' && document.forms[0].moduleName!=undefined)
	document.forms[0].moduleName.value="";
			document.forms[0].action="modifyUserGroup.do?method=reFresh&str="+str;
			document.forms[0].submit();
	}
	
	
	function reFreshSubModule(str){
	if(str=='group' && document.forms[0].moduleName!=undefined)
	document.forms[0].moduleName.value="";
			document.forms[0].action="modifyUserGroup.do?method=reFreshSubmodule";
			document.forms[0].submit();
	
	}
	
	function getDetails()
 {
 	var reqType=document.forms[0].reqType.value;
 	var keyword=document.forms[0].keyword.value;
 	var aref = document.getElementById("searchId").href;
 	var lochref="contacts.do?method=getSearchDetails";
 	lochref = lochref + "&reqType="+reqType+"&keyword="+keyword;
 	document.getElementById("searchId").href = lochref;
 	window.frames["contentPage"].location.replace(document.getElementById("searchId").href);
 	  	
 }
	
	
	function modifyGroup(){
	var checkBox=document.forms[0].selectedLinksArr;
	var checked=false;

	if(checkBox.checked==true)
				checked=true;

		for(var i=0;i<checkBox.length;i++)
			{
				if(checkBox[i].checked==true)
				checked=true;
				
			}
				 
		if(checked==true){
			document.forms[0].action="modifyUserGroup.do?method=modify";
			document.forms[0].submit();
		}
		else
		alert("Assigne atleast one link to the module..");
		
		return false;
}
	
	
	function addgrp(){
	        document.forms[0].action="userGroup.do?method=display";
			document.forms[0].submit();
	}
	
	function Modgrpryt(){
	        document.forms[0].action="modifyUserRights.do?method=display";
			document.forms[0].submit();
	}
	
	function onSearchRequired()
	{
		var URL="modifyUserGroup.do?method=getRequiredSearch";
		document.forms[0].action=URL;
 		document.forms[0].submit();

	}
	
	function firstGrouplRecord()
	{

		var url="modifyUserGroup.do?method=firstGroupRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function previousGroupRecord()
	{

		var url="modifyUserGroup.do?method=previousGroupRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function nextGroupRecord()
	{

		var url="modifyUserGroup.do?method=nextGroupRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function lastGroupRecord()
	{

		var url="modifyUserGroup.do?method=lastGroupRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

	}
	
	function onSubmitactivedeactive(groupid,status,groupname){
    
      var agree;
			   if(status=="Active")
			   {
			   agree = confirm('Are You Sure want To Deactivate Group');
			   }
			   else
			   {
			   agree = confirm('Are You Sure want To activate Group');
			   }
    		    if(agree)
      		    {
				var url1="modifyUserGroup.do?method=active_inactive_group"+"&groupid="+groupid+"&status="+status+"&groupname="+groupname;
 				document.forms[0].action=url1;
 				document.forms[0].submit();
 			    }
 			    else
 			    {
 			    return false;
 			    }
		
  }
  
  function onSubmitdelete(groupid,status,groupname){
    
      var agree;
			   
			   agree = confirm('Are You Sure want To Delete Group');
			  
    		    if(agree)
      		    {
				var url1="modifyUserGroup.do?method=delete_group"+"&groupid="+groupid+"&status="+status+"&groupname="+groupname;
 				document.forms[0].action=url1;
 				document.forms[0].submit();
 			    }
 			    else
 			    {
 			    return false;
 			    }
		
  }
  
  function onSubmitedit(groupid,status,groupname){
    
      
				var url1="modifyUserGroup.do?method=edit_group"+"&grpcode="+groupid+"&status="+status+"&grpname="+groupname;
 				document.forms[0].action=url1;
 				document.forms[0].submit();
 			    
		
  }
  
  function clearGroup()
	{
		
		var url="modifyUserGroup.do?method=display";
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


       						
				

					
		<html:form action="modifyUserGroup.do" method="post" onsubmit="onSearchRequired(); return false;">
		<html:hidden property="message"/>
		<div style="width: 90%">
			<table class="bordered" width="90%">
			<tr>
 				<th colspan="6"><center><big>User Rights</big></center></th>
			</tr>
			<tr>
			<td colspan="6">
					<html:button property="method" styleClass="rounded" value="Add New Group" onclick="addgrp()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					<html:button property="method" styleClass="rounded" value="Modify Group Rights" onclick="Modgrpryt()"></html:button>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="#"><img src="images/clearsearch.jpg" align="absmiddle" title="Clear..."  onclick="clearGroup()"/></a>
					<html:text property="searchRequired" styleClass="rounded" style="width:200px;" title="Enter Group name to search"/>
					<a href="modifyUserGroup.do?method=getRequiredSearch"><img src="images/search.png"   align="absmiddle" title="Search"/></a>
					
			</td>		
			</tr>
			</table>
			<br/>
			<tr>
			
			<logic:notEmpty name="displayRecordNo">
	
	  	<td>
	  	<img src="images/First10.jpg" onclick="firstGrouplRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousGroupRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	
	
	
	<bean:write property="startRecord"  name="modifyUserGroupForm"/>
	
	
	<bean:write property="endRecord"  name="modifyUserGroupForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextGroupRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	
	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastGroupRecord()" align="absmiddle"/>
	</td>
	
	
	
	
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="searchRequired"/>	
			</tr>
		
<logic:notEmpty name="groupList"> 
	<div class="bordered">
		<table class="sortable">
			<tr>
					<th style="width:100px;">Group Name</th><th style="width:100px;">Status</th><th style="width:50px;">Active/Inactive</th><th style="width:50px;">Delete</th><th style="width:50px;">Edit</th><th style="width:50px;">Assign Users</th>
				</tr>
		<logic:iterate id="grpList" name="groupList">
				<tr>
						<td><bean:write name="grpList" property="groupname"/></td>
						<td><bean:write name="grpList" property="status"/></td>
						
						<td>
						
						<c:choose>
  			
  						<c:when test="${grpList.status=='Active'}">
    						<a href="#"><img src="images/Present.png" onclick="onSubmitactivedeactive('${grpList.groupid}','${grpList.status}','${grpList.groupname}')" title="Active"></img></a>
  						</c:when>
							
  						<c:otherwise>
    						<a href="#"><img src="images/Pending.gif" onclick="onSubmitactivedeactive('${grpList.groupid}','${grpList.status}','${grpList.groupname}')" title="Inactive"></img></a>
  						</c:otherwise>
						
						</c:choose>
										
    		
  			
						</td>
						
						<td>
						
						<c:choose>
  			
  						<c:when test="${grpList.status=='Active'}">
						
							<a href="#"><img src="images/delete.png" onclick="onSubmitdelete('${grpList.groupid}','${grpList.status}','${grpList.groupname}')" title="Delete"></img></a>			
    					</c:when>
    					
    					<c:otherwise>
    					
    					</c:otherwise>
    					</c:choose>
    					
    					</td>
						
						<td>
							
							<c:choose>
  			
  						<c:when test="${grpList.status=='Active'}">
							
							<a href="#"><img src="images/edit.png" onclick="onSubmitedit('${grpList.groupid}','${grpList.status}','${grpList.groupname}')" title="Edit"></img></a>		
    						
  							
  							</c:when>
  							<c:otherwise>
  							
  							
  							</c:otherwise>
  							</c:choose>
						</td>
						
						<td>
						
						<c:choose>
  			
  						<c:when test="${grpList.status=='Active'}">
									
    						<a href="modifyUserGroup.do?method=assign_users&grpcode=${grpList.groupid}&grpname=${grpList.groupname}&total=${modifyUserGroupForm.totalRecords}&start=${modifyUserGroupForm.startRecord}&end=${modifyUserGroupForm.endRecord}&searchRequired=${modifyUserGroupForm.searchRequired}"><img src="images/add-items.gif" title="User Assign"/></a>
  							
  							</c:when>
  							<c:otherwise>
  							
  							
  							</c:otherwise>
  							</c:choose>
										
    		
  			
						</td>
						
                    		</tr>
    </logic:iterate>
   
			
			
		</table>
 </div>
 </logic:notEmpty>
			
			
			
			
		</div>
					
	

</html:form>
                

</body>
</html>
