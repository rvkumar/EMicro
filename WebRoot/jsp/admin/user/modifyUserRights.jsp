
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
window.onload = function() {
    for(var i = 0, l = document.getElementsByTagName("input").length; i < l; i++) {
        if(document.getElementsByTagName("input").item(i).type == "text") {
            document.getElementsByTagName("input").item(i).setAttribute("autocomplete", "off");
        }
    }
};
		
function deleteUser()
{
		var agree=confirm('Are You Sure To Delete Selected User');
       	if(agree)
       	{
		  var URL="addUser.do?method=deleteUser";
		  document.forms[0].action=URL;
	 	  document.forms[0].submit();
		}
		else
		{
		  return false;
		}
}
	
	
function reFresh(str){

if(document.forms[0].moduleName.value=="Select")
	    {
	      alert("Please Select the Module ...");
	      document.forms[0].grouplistid.focus();
	      return false;
	    }

if(str=='group' && document.forms[0].moduleName!=undefined)
        document.forms[0].moduleName.value="";
		document.forms[0].action="modifyUserRights.do?method=userRight&str="+str;
		document.forms[0].submit();
}
	
function modifyUser(){
	document.forms[0].action="addUser.do?method=modifyUser";
	document.forms[0].submit();
}
	
	
function modifyGroup()
{

	var rows=document.getElementsByName("selectedMainModulesArr");

	var checkvalues='';
	var uncheckvalues='';
	for(var i=1;i<rows.length;i++)
	{
		if (rows[i].checked)
		{
			checkvalues+=rows[i].value+',';
		}else{
			uncheckvalues+=rows[i].value+',';
		}
	}
	var rows1=document.getElementsByName("selectedLocationsArr");
	var loccheckvalues='';
	var locuncheckvalues='';
	for(var i=0;i<rows1.length;i++)
	{
		if (rows1[i].checked)
		{
			loccheckvalues+=rows1[i].value+',';
		}else{
			locuncheckvalues+=rows1[i].value+',';
		}
	}
			
	document.forms[0].action="modifyUserRights.do?method=addUserRight&cValues="+checkvalues+"&unValues="+uncheckvalues+"&locCheckvalues="+loccheckvalues+"&locUncheckvalues="+locuncheckvalues;
	document.forms[0].submit();
}
	
function getLinks()
{

	if(document.forms[0].grouplistid.value=="Select")
	    {
	      alert("Please Select the Group ...");
	      document.forms[0].grouplistid.focus();
	      return false;
	    }

	document.forms[0].action="modifyUserRights.do?method=getLinks";
	document.forms[0].submit();
}
	
	
function reFreshSubModule()
{
	document.forms[0].action="modifyUserRights.do?method=userRightSubModule";
	document.forms[0].submit();
}
	
	
function modifySGroup(){
	
	var rows=document.getElementsByName("selectedSubModulesArr");
	
	var checkvalues='';
	var uncheckvalues='';
	for (var i =1; i < rows.length; i++)
				{
				//alert('rows is ******************'+rows[i].value);
				if (rows[i].checked)
				{
				checkvalues+=rows[i].value+',';
				}else{
				uncheckvalues+=rows[i].value+',';
				}
				}
		document.forms[0].action="modifyUserRights.do?method=addUserRight&cValues="+checkvalues+"&unValues="+uncheckvalues;
		document.forms[0].submit();
}
	
	
function modifySubGroup(){
	
	var rows=document.getElementsByName("selectedSubSubModulesArr");
	
	var checkvalues='';
	var uncheckvalues='';
	for (var i = 0; i < rows.length; i++)
	{
		if(document.getElementById("popUpDiv"+rows[i].value) != null){
			document.getElementById("popUpDiv"+rows[i].value).style.display ="none";
		}	
		if (rows[i].checked)
		{
		checkvalues+=rows[i].value+',';
		}else{
		uncheckvalues+=rows[i].value+',';
		}
	}
		document.forms[0].action="modifyUserRights.do?method=addSubUserRight&cValues="+checkvalues+"&unValues="+uncheckvalues;
		document.forms[0].submit();
}



function selectSubLink(elem){
	var uName=document.getElementById("grouplistid").value;
	closeSubDiv();
		
		var xmlhttp;
	    if (window.XMLHttpRequest){
	        // code for IE7+, Firefox, Chrome, Opera, Safari
	        xmlhttp=new XMLHttpRequest();
	    }
	    else{
	        // code for IE6, IE5
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	    }

	    xmlhttp.onreadystatechange=function(){
	        if (xmlhttp.readyState==4 && xmlhttp.status==200){
	        	document.getElementById("selectInput").value = elem.value;
	        	document.getElementById("popUpDiv"+elem.value).style.display ="";
	        	document.getElementById("popUpDiv"+elem.value).innerHTML=xmlhttp.responseText;
	        }
	    }
	
	    xmlhttp.open("POST","modifyUserRights.do?method=userRightSubModule&uName="+uName+"&mId="+elem.value,true);
	    xmlhttp.send();
}
function closeSubDiv(){
	var closediv = document.getElementById("selectInput").value;
	var rows=document.getElementsByName("selectedSubModulesArr");
	for (var i = 0; i < rows.length; i++)
	{
		if(document.getElementById("popUpDiv"+rows[i].value) != null){
			document.getElementById("popUpDiv"+rows[i].value).style.display ="none";
		}
	}
}
function searchUsers(input,elm){
	var toadd = input.value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	if(toadd == ""){
		input.focus();
		document.getElementById("sU").style.display ="none";
		return false;
	}
	var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
        	document.getElementById("selectInput").value = elm;
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        }
    }

    xmlhttp.open("POST","modifyUserRights.do?method=searchGivenUser&sId=New&searchText="+toadd,true);
    xmlhttp.send();
}
function selectUser(input){
	var toid = document.getElementById("selectInput").value;
	document.getElementById("grouplistid").value= input.innerHTML;
	disableSearch();
}
function disableSearch(){
	if(document.getElementById("sU") != null){
	document.getElementById("sU").style.display="none";
	}
	return true;
}

function checkAll_Test(elem){
	if(elem.checked){
		
		var rows=document.getElementsByName("selectedSubModulesArr");
		for (var i =1; i < rows.length; i++)
		{
			if (rows[i].checked)
			{
				
			}else{
				rows[i].setAttribute("checked", "true");
			}
		}
	}
	else{
		var rows=document.getElementsByName("selectedSubModulesArr");
		for (var i =1; i < rows.length; i++)
		{
			if (rows[i].checked)
			{
				rows[i].removeAttribute("checked");
			}else{
				
			}
		}
	}
}

function checkAll()
	{
		for(i=0; i < document.forms[0].selectedSubModulesArr.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selectedSubModulesArr[i].checked = true ;
			else
				document.forms[0].selectedSubModulesArr[i].checked = false ;
		}
	}
	
function checkAll_main()
	{
		for(i=0; i < document.forms[0].selectedMainModulesArr.length; i++){
			if(document.forms[0].checkProp.checked==true)
				document.forms[0].selectedMainModulesArr[i].checked = true ;
			else
				document.forms[0].selectedMainModulesArr[i].checked = false ;
		}
	}
	


//function displaymessage(message){

	//			alert(message);
		//}

function display(){
	if(document.forms[0].message.value!="")  
		{
			alert(document.forms[0].message.value);
		}
		document.forms[0].message.value="";
		return false;
}

</script>
</head>

<body onload="display()">

			<%--<div align="center">
			<logic:notEmpty name="userRightsForm" property="message">
				<font color="red">
				<script type="text/javascript">
				
					displaymessage('<bean:write name="userRightsForm" property="message" />');
					</script>
				</font>
			</logic:notEmpty>
			</div>
					
					
			--%><html:form action="modifyUserRights.do">
			<html:hidden property="message"/>
			<div style="width: 50%">
		 <table class="bordered" width="50%">

					<input type="hidden" name="MenuIcon2" value="<%=request.getAttribute("MenuIcon") %>"/>   
		 
			 
					
					<th colspan="3"><center><big> Group Rights </big></center></th>
				
					<tr>
						<td>Group Name<font color="red" size="3">*</font></td>
<%--						<td style="text-align:left;width:20%;"><html:text property="groupName" style="width:95%" onkeyup="searchUsers(this)" styleId="empRightId" onmousedown="this.value=''" styleClass="rounded"/></td>--%>
							<td><html:select property="grouplistid" styleId="grouplistid">
			
		<html:option value="Select">-- Select --</html:option>
		<html:options property="ar_id" labelProperty="ar_name" name="userRightsForm"/>
		
		</html:select></td>
						<td><html:button property="method" styleClass="rounded" value="Get Links" onclick="getLinks();"></html:button>&nbsp;&nbsp;<html:button property="method" value="Close" onclick="location.href='modifyUserGroup.do?method=display'" styleClass="rounded" /></td>
					</tr>
					
					<tr id="sU" style="display:none;">
						<td style="text-align:center;width:15%;"></td>
						<td id="sUTD" style="text-align:left;width:30%;"></td>
						<td style="width: 25%;text-align: center;"></td>
					</tr>
					<tr>
					<td colspan="3" style="display:none;">
						<input id="appIdValue" value=""/>
						<input id="selectInput" value=""/>
					</td>
				</tr>

						
				<logic:notEmpty name="userRightsForm" property="modules">
					<tr><td>Select Modules</td>
					<bean:define id="abc" name="userRightsForm" property="modules"/>

						<td colspan="2" class="lft style1">
						<html:select name="userRightsForm" property="moduleName" styleClass="rounded" onchange="reFresh('module1');">
						<html:option value="Select">-- Select --</html:option>
						<html:option value="main">Main</html:option>
						<html:options collection="abc" property="value" labelProperty="value" />
						</html:select>
						</td>
					</tr>
					
						
					<logic:notEmpty name="userRightsForm" property="links">
					<bean:define id="gid" property="selectedModules" name="userRightsForm"/>
			
					<%  int count=0;%>
					
						<tr>
						<th colspan="3"><center><input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll_main()"/>&nbsp;&nbsp;Select Links</center></th>
						</tr>
						
						<input type="hidden" name="selectedMainModulesArr" value="Main"/>
						
						<logic:iterate id="vvv" name="userRightsForm" property="links">
							<tr>
							<td colspan="3">&nbsp;<input class="checkbox" type="checkbox" name="selectedMainModulesArr" value="<bean:write name="vvv" property="id"/>"
								 
								  
								  <% if(((ArrayList<String>) gid).contains(""+((IdValuePair)vvv).getId())){
								   count++;
								   out.println("checked='checked'");
								  }%>/>&nbsp;<bean:write name="vvv" property="value"/>
								</td>
<%--								<td><bean:write name="vvv" property="value"/></td>--%>
									<div id="popUpDiv<bean:write name="vvv" property="id"/>" style="display:none;"></div>
							</tr>
							
						
						</logic:iterate>
						
						<logic:notEmpty name="userRightsForm" property="locations">
						<bean:define id="gid" property="selectedLocations" name="userRightsForm"/>
						<%  int count1=0;%>
						<tr>
						<th colspan="3"><center><input class="checkbox" type="checkbox" name="checkLoc" onclick="checkAll_Locations()"/>&nbsp;&nbsp;Select Locations</center></th>
						</tr>
						<logic:iterate id="locID" name="userRightsForm" property="locations">
							<tr>
							<td colspan="3">&nbsp;<input class="checkbox" type="checkbox" name="selectedLocationsArr" value="<bean:write name="locID" property="id"/>"
								 
								  
								  <% if(((ArrayList<String>) gid).contains(""+((IdValuePair)locID).getId())){
								   count++;
								   out.println("checked='checked'");
								  }%>/>&nbsp;<bean:write name="locID" property="value"/>
								</td>
<%--								<td><bean:write name="vvv" property="value"/></td>--%>
									<div id="popUpDiv1<bean:write name="locID" property="id"/>" style="display:none;"></div>
							</tr>
							
						
						</logic:iterate>
						</logic:notEmpty>
						
						<tr><td colspan="3">
						<div align="center">
						<html:button property="method"  value="Modify Group" onclick="modifyGroup();" styleClass="rounded" >
					
						</html:button> 
						</div></td></tr>
						
						</logic:notEmpty>
						
						
						<logic:notEmpty name="userRightsForm" property="subLinks">
						<bean:define id="gid" property="selectedSubLinks" name="userRightsForm"/>
						
						<%  int count1=0;%>
						
						<tr>
						<th colspan="3"><center> <input class="checkbox" type="checkbox" name="checkProp" onclick="checkAll()"/>&nbsp;&nbsp;Select Links</center></th>
						</tr>
						
						<input type="hidden" name="selectedSubModulesArr" value="Main"/>
						<logic:iterate id="vvv" name="userRightsForm" property="subLinks">
							<tr>
							
								<td colspan="3">&nbsp;<input class="checkbox" type="checkbox" onclick="selectSubLink(this)"
								 name="selectedSubModulesArr" 
								 value="<bean:write name="vvv" property="id"/>" 
								  <% 
								  if(((ArrayList<String>) gid).contains(""+((IdValuePair)vvv).getId())){
								   count1++;
								   out.println("checked='checked'");
								  }
								   %>/>&nbsp;<bean:write name="vvv" property="value"/>
								   <div id="popUpDiv<bean:write name="vvv" property="id"/>" style="display:none;"></div>
								</td>   
							</tr>
<%--						<tr><td colspan="3"><div id="popUpDiv<bean:write name="vvv" property="id"/>" style="display:none;"></div></td></tr>--%>
						</logic:iterate>
						
						<tr><td colspan="3">
						<div align="center">
						<html:button property="method"  value="Modify Group" onclick="modifySGroup();" styleClass="rounded">
						
						</html:button> 
						</div></td></tr>
						
						</logic:notEmpty>
						
						
						<logic:notEmpty name="userRightsForm" property="subModules">
						<tr><td colspan="3"><font color="black">
						<center>Select Sub Modules</center></font></td></tr>
						<bean:define id="abc" name="userRightsForm" property="subModules"/>
						<tr>
							<td colspan="3">
								<html:select name="userRightsForm" property="subModuleName" onchange="reFreshSubModule();">
								    <html:option value="">--Select--</html:option>
									<html:options collection="abc" property="value" labelProperty="value" />
								</html:select>
							</td>
						</tr>
						
						
						<logic:notEmpty name="userRightsForm" property="subSubLinks">
						
						<bean:define id="gid" property="selectedSubSubModules" name="userRightsForm"/>
						
						
						<tr><th colspan="3">
						<font><center>Select Sub Links</center></font></th></tr>
						
						<logic:iterate id="vvv" name="userRightsForm" property="subSubLinks">
							<tr>
								<th class="spec">U<bean:write name="vvv" property="value"/></th>
								<td><input class="checkbox" type="checkbox" 
								 name="selectedSubSubModulesArr" 
								 value="<bean:write name="vvv" property="id"/>" 
								  <%  
								  
								  if(((ArrayList<String>) gid).contains(""+((IdValuePair)vvv).getId())){
								   out.println("checked='checked'");
								  }
								   %>/></td>
							</tr>
						</logic:iterate>
						
						
						<tr><td colspan="2">
						<div align="center">
						<html:button property="method"   value="Modify Group" onclick="modifySubGroup();" styleClass="rounded">
						
						</html:button> 
						</div></td></tr>
						
						</logic:notEmpty>
						</logic:notEmpty>
						
					</logic:notEmpty>
						
					


</table>
</div>
							</html:form>
				

</body>
</html>
