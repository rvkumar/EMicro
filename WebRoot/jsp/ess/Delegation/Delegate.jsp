
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.main.form.ApprovalsForm"/>
<jsp:directive.page import="com.microlabs.ess.form.RawMaterialForm"/>



<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />


<script type="text/javascript">
window.onload = function() {
    for(var i = 0, l = document.getElementsByTagName("input").length; i < l; i++) {
        if(document.getElementsByTagName("input").item(i).type == "text") {
            document.getElementsByTagName("input").item(i).setAttribute("autocomplete", "off");
        }
    }
};

	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2", // trigger button
	disableFunc: function(date) {
          var now= new Date();
        return (date.getDate() < now.getDate());
    }
	});
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

    xmlhttp.open("POST","delegate.do?method=searchGivenUser&sId=New&searchText="+toadd,true);
    xmlhttp.send();
}
function selectUser(input){
	var toid = document.getElementById("selectInput").value;
	document.getElementById("delegateEmp").value= input.innerHTML;
	disableSearch();
}
function disableSearch(){
	if(document.getElementById("sU") != null){
	document.getElementById("sU").style.display="none";
	}
	return true;
}
function delegateAction(){
	document.forms[0].action="delegate.do?method=saveDelegation";
	document.forms[0].submit();
	
}
</script>

<style type="text/css">
.openMail{
display:inline-block;
border-radius: 5px;
position: fixed;
width: 99%; 
z-index: 10;
height:300px;
box-shadow: 1px 1px 3px 2px #63b8ff;
-moz-box-shadow: 1px 1px 3px 2px #63b8ff;
  -webkit-box-shadow: 1px 1px 3px 2px #63b8ff;
}
</style>
</head>

<body>
	
				
	<html:form action="/delegate.do" enctype="multipart/form-data">
		<div align="center">
			<logic:present name="delegateForm" property="message">
				<font color="red"><bean:write name="delegateForm" property="message" /></font>
			</logic:present>
		</div>
		<table border="0" cellpadding=2 cellspacing=2 width="100%"  style="border: 0px" align="center">
			<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
			<div>
				<div class="widgetTitle"> Delegation </div><br/>
				<table border="0" cellpadding="8" class="content" style="solid #4297d7;">
			
				<tr>
					<td width="15%">Start Date <font color="red" size="4">*</font></td>
					<td width="1%"> : </td>
					<td align="left" width="34%"><html:text property="fromDate" styleId="startDate" style="width:50%" onfocus="popupCalender('startDate')" styleClass="rounded" readonly="true"/></td>
					<td width="15%">End Date <font color="red" size="4">*</font></td>
					<td width="1%"> : </td>
					<td width="34%"><html:text property="toDate" styleId="endDate" onfocus="popupCalender('endDate')" style="width:50%" styleClass="rounded" readonly="true"/></td>
				</tr>
				<tr style="height:10px;"></tr>
				<tr>
					<td width="15%">Delegate to <font color="red" size="4">*</font></td>
					<td width="1%"> : </td>
					<td align="left" width="34%"><html:text property="employeeNumber" style="width:50%" onkeyup="searchUsers(this)" styleId="delegateEmp" onmousedown="this.value=''" styleClass="rounded"/></td>
					<td width="15%"><html:button property="method" value="Delegate" styleClass="rounded" style="width:75px; height:30px;" onclick="delegateAction()"></html:button></td>
					<td width="1%"></td>
					<td width="34%"></td>
				</tr>
				<tr id="sU" style="display:none;">
					<td width="15%"></td>
					<td width="1%"> : </td>
					<td id="sUTD" align="left" width="34%"></td>
					<td width="15%"></td>
					<td width="1%"></td>
					<td width="34%"></td>
				</tr>
				<tr style="height:20px;"></tr>
				<tr><td colspan="6">
				<div align="left" class="bordered">
				<table border="1"  style="aligh:right;width:100%;font-size:12px;" class="sortable">
					<tr>
					<th>From</th>
					<th>To</th>
					<th>Delegate to</th>
					</tr>
					<logic:empty name="DelegationList">
					<tr><td colspan="4" style="text-align:center;"><bean:write name="delegateForm" property="message" /></td></tr>
					</logic:empty>
					<logic:notEmpty name="DelegationList">
					<logic:iterate id="abc" name="DelegationList">
					<tr>
						<td>${abc.fromDate}</td>
						<td>${abc.toDate}</td>
						<td>${abc.employeeNumber}</td>
					</tr>
					</logic:iterate>
					</logic:notEmpty>
				</table>
				</div>
				</td></tr>
				</table>
			</div>
			</td></tr>
			<tr>
				<td style="display:none;">
					<input id="appIdValue" value=""/>
					<input id="selectInput" value=""/>
				</td>
			</tr>
		</table>
		
	</html:form>

</body>
</html>