
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
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
      <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />

    <script type="text/javascript">
        $(document).ready(function () {
            window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
            window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
            window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
            window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });

        });
        
         $(document).ready(function () {
           $('.testselect1').SumoSelect();

        });
    </script>


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
function showlist(){

document.forms[0].action="modifyUserRights.do?method=getAuditreport";
	document.forms[0].submit();
	
}


</script>
</head>

<body >
<html:form action="modifyUserRights.do">
			<table class="bordered" >
		<tr>
		<th colspan="5">Rights Audit Report</th>
		</tr>
		<tr>
		<td>HR Modules&nbsp;<font color="red">*</font></td>
		<td>
		
			<html:select  property="moduleId" name="userRightsForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:option value="">--Select--</html:option>
			<html:options name="userRightsForm"  property="moduleIdList" labelProperty="moduleLabelList"/>
		</html:select>
	
		<html:button property="method" value="Execute" onclick="showlist()" styleClass="rounded" />&nbsp;
		
		</td>
		</tr>
		</table>
		
		<br/>
		<logic:notEmpty name="gija">
		<table class="bordered">
		<tr><th>Pernr</th><th>Emp Name</th><th>Location</th><th>Department</th><th>Designation</th>	<th style="width: 90%">Attendance Location</th><th>Created By</th><th>Created Date</th>
		<th>Modified By</th><th>Modified Date</th></tr>
	
		<logic:iterate id="a" name="gija">
		<tr><td>${a.pernr }</td>
		<td>${a.empname }</td>
		<td>${a.loc }</td>
		<td>${a.dpt }</td>
		<td>${a.desg }</td>
		<td >${a.atten_loc }</td>
		<td>${a.created_by }</td>
		<td>${a.created_date }</td>
		<td>${a.modified_by }</td>
		<td>${a.modified_date }</td>
		</tr>
	
	
		</logic:iterate>
		</table>
		</logic:notEmpty>
	
							</html:form>
				

</body>
</html>
