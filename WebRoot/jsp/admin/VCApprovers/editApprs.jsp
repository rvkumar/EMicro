<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">
function onSave(){//locationId,floor,roomName
	if(document.forms[0].locationId.value==""){
		alert("Location Should not blank");
		document.forms[0].locationId.focus();
		return false;
	}
	if(document.forms[0].floor.value==""){
		alert("Floor Should not blank");
		document.forms[0].floor.focus();
		return false;
	}
	if(document.forms[0].roomName.value==""){
		alert("Conference Room Should not blank");
		document.forms[0].roomName.focus();
		return false;
	}
var floor=document.forms[0].floor.value;
var roomName=document.forms[0].roomName.value;

var URL="vcAppr.do?method=addApprovers&floor="+floor+"&roomName="+roomName;
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function onModify(){
	if(document.forms[0].category.value==""){
		alert("Category Should not blank");
		document.forms[0].category.focus();
		return false;
	}
	if(document.forms[0].subCategory.value==""){
		alert("Sub Category Should not blank");
		document.forms[0].subCategory.focus();
		return false;
	}
	if(document.forms[0].locationId.value==""){
		alert("Location Should not blank");
		document.forms[0].locationId.focus();
		return false;
	}
var URL="vcAppr.do?method=modifyApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();


}

function clearApprover1(priority)
{
	if(priority==1)
	{
		document.forms[0].approver1.value="";
		document.forms[0].parllelAppr11.value="";
		document.forms[0].parllelAppr12.value="";
 }else if(priority==2)
 {
		document.forms[0].approver2.value="";
		document.forms[0].parllelAppr21.value="";
		document.forms[0].parllelAppr22.value="";
 }else if(priority==3)
 {
		document.forms[0].approver3.value="";
		document.forms[0].parllelAppr31.value="";
		document.forms[0].parllelAppr32.value="";
 }else if(priority==4)
 {
		document.forms[0].approver4.value="";
		document.forms[0].parllelAppr41.value="";
		document.forms[0].parllelAppr42.value="";
 }
}
function addESSApprover(){
var URL="essApprover.do?method=addApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}
function searchEmployee(fieldName){

var reqFieldName=fieldName;
var location=document.forms[0].locationId.value;

if(location=="")
{
alert("Please Select Location");
document.forms[0].locationId.focus();
      return false;
}


	var toadd = document.getElementById(reqFieldName).value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById(reqFieldName).focus();
	if(toadd == ""){
		document.getElementById(reqFieldName).focus();
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
        if(reqFieldName=="employeeNo"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
        if(reqFieldName=="approver1"){
        	document.getElementById("approver1sU").style.display ="";
        	document.getElementById("approver1sUTD").innerHTML=xmlhttp.responseText;
        	}
       
     
         if(reqFieldName=="approver2"){
        	document.getElementById("approver2sU").style.display ="";
        	document.getElementById("approver2sUTD").innerHTML=xmlhttp.responseText;
        	}
      
     
          if(reqFieldName=="approver3"){
        	document.getElementById("approver3sU").style.display ="";
        	document.getElementById("approver3sUTD").innerHTML=xmlhttp.responseText;
        	}
        	  if(reqFieldName=="approver4"){
        	document.getElementById("approver4sU").style.display ="";
        	document.getElementById("approver4sUTD").innerHTML=xmlhttp.responseText;
        	}
       
     
         if(reqFieldName=="approver5"){
        	document.getElementById("approver5sU").style.display ="";
        	document.getElementById("approver5sUTD").innerHTML=xmlhttp.responseText;
        	}
      
     
          if(reqFieldName=="approver6"){
        	document.getElementById("approver6sU").style.display ="";
        	document.getElementById("approver6sUTD").innerHTML=xmlhttp.responseText;
        	}
       
         
        			
        }
    }
     xmlhttp.open("POST","vcAppr.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName+"&loc="+location,true);
    xmlhttp.send();
}

function selectUser(input,reqFieldName){

var res = input.split("-");
	document.getElementById(reqFieldName).value=res[1];
	disableSearch(reqFieldName);
}
function disableSearch(reqFieldName){
  if(reqFieldName=="employeeNo"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	if(reqFieldName=="approver1"){
		if(document.getElementById("approver1sU") != null){
		document.getElementById("approver1sU").style.display="none";
		}
	}


	if(reqFieldName=="approver2"){
		if(document.getElementById("approver2sU") != null){
		document.getElementById("approver2sU").style.display="none";
		}
	}
	
	
	if(reqFieldName=="approver3"){
		if(document.getElementById("approver3sU") != null){
		document.getElementById("approver3sU").style.display="none";
		}
	}
	if(reqFieldName=="approver4"){
		if(document.getElementById("approver4sU") != null){
		document.getElementById("approver4sU").style.display="none";
		}
	}


	if(reqFieldName=="approver5"){
		if(document.getElementById("approver5sU") != null){
		document.getElementById("approver5sU").style.display="none";
		}
	}
	
	
	if(reqFieldName=="approver6"){
		if(document.getElementById("approver6sU") != null){
		document.getElementById("approver6sU").style.display="none";
		}
	}


	
		
}

function getFloor(linkname)
{
var xmlhttp;
var dt;
dt=linkname;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getFloorList&locID="+dt,true);
xmlhttp.send();
}
function getRoomList()
{
	
	var floor=document.forms[0].floor.value;
	var locId=document.forms[0].locationId.value;
	
	
var xmlhttp;

if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("roomID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","vcAppr.do?method=getRoomsList&locID="+locId+"&floor="+floor,true);
xmlhttp.send();
}

function goBack()
{
	document.forms[0].action="vcAppr.do?method=approversList";
 		document.forms[0].submit();
}


function onModify()
{
	document.forms[0].action="vcAppr.do?method=modifyApprs";
 		document.forms[0].submit();
}

function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}


</script>
<style>
input:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
select:focus { 
    outline:none;
    border-color:#2ECCFA;
    box-shadow:0 0 10px #2ECCFA;
}
</style>
</head>
<body>

<div align="center">
<div align="center" id="messageID" style="visibility: true;">
			<logic:present name="vcApprForm" property="message" >
			<font color="green">
				<bean:write name="vcApprForm" property="message" />
			</font>
		<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
		</logic:present>
		<logic:present name="vcApprForm" property="message2">
			<font color="red">
				<bean:write name="vcApprForm" property="message2" />
			</font>
			<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
		</logic:present>
		</div>
		</div>
<html:form action="vcAppr.do">
<div style="width: 90%;" >
<table class="bordered">
<tr>
<th>Location<font color="red">*</font></th>
<td><bean:write name="vcApprForm" property="locationname" />
<html:hidden property="locationId" />
<html:hidden property="locationname" />
</td>
<th>Floor<font color="red">*</font></th>

<td><bean:write name="vcApprForm" property="floor" />
<html:hidden property="floor" />

</td>
<th>Conf.Room<font color="red">*</font></th>
<td><bean:write name="vcApprForm" property="roomName" />
<html:hidden property="roomName" />

</td>

	</tr>
</table>
</div>
<div>&nbsp;</div>
<div style="width: 90%;" >
<table class="sortable bordered"  width="60%"> 
<tr>
<th>Priority</th><th>Approver</th>
</tr>
<tr>
	<td><html:text property="priority1" value="1" readonly="true"></html:text></td>
	
	<td><html:text property="approver1" styleId="approver1" onkeyup="searchEmployee('approver1')">
	<bean:write property="approver1" name="vcApprForm" /></html:text>
	
	<div id="approver1sU" style="display:none;">
		<div id="approver1sUTD" style="width:400px;">
		<iframe src="jsp/admin/ITApprover/searchITApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>

</tr>
<tr>
	<td><html:text property="priority2" value="2" readonly="true"></html:text></td>

	<td><html:text property="approver2"  styleId="approver2" onkeyup="searchEmployee('approver2')">
	<bean:write property="approver2" name="vcApprForm" /></html:text>
	
	<div id="approver2sU" style="display:none;">
		<div id="approver2sUTD" style="width:400px;">
		<iframe src="jsp/admin/ITApprover/searchITApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	
</tr>
<tr>
	<td><html:text property="priority3" value="3" readonly="true"></html:text></td>
	
	<td><html:text property="approver3"  styleId="approver3" onkeyup="searchEmployee('approver3')">
	<bean:write property="approver3" name="vcApprForm" /></html:text>
	
	<div id="approver3sU" style="display:none;">
		<div id="approver3sUTD" style="width:400px;">
		<iframe src="jsp/admin/ITApprover/searchITApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
</tr>
<tr>
<td colspan="5" align="center">
<div align="center">
<html:button property="method" value="Modify" onclick="onModify()" styleClass="rounded" style="width:100px;"/>
<html:button property="method" value="Close" onclick="goBack()" styleClass="rounded" style="width:100px;"/>
</div>

</td>
</tr>
</table>
</div>
</html:form>
</body>
</html>