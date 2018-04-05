
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>


<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Compose Mail </title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery-1.4.2.min.js"></script>
<script src="js/jquery-1.9.1.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript" src="js/mail.js"></script>
<style type="text/css">
.CB{font-weight:bold;cursor:pointer;}

.addCB{
	text-decoration:underline;
}
.iconcur{
cursor:pointer;
}
input.hide
{
position:absolute;
-moz-opacity:0;
filter:alpha(opacity: 0);
opacity: 0;
width:25px;
z-index: 2;
cursor: pointer;

}
.hover{
color:#000000;
cursor:pointer;
background-color:#ccc;
}
</style>
<script type="text/javascript">
window.onload = function() {
    for(var i = 0, l = document.getElementsByTagName("input").length; i < l; i++) {
        if(document.getElementsByTagName("input").item(i).type == "text") {
            document.getElementsByTagName("input").item(i).setAttribute("autocomplete", "off");
        }
    }
};

function sendMail()
{
	if(validateAddress()){
		if(document.forms[0].toSubject.value == "")
		{
			var pSubject = prompt("Do u want enter subject:","Subject");
			document.forms[0].toSubject.value = pSubject;
			sendAtLast();
		}
		else{
	        sendAtLast();   
		}	
	}
	else{
		return false;
	}
}
		
function sendAtLast(){
            var url="sendMail.do?method=displaySendMail";
			document.forms[0].action=url;
			var fckEditor = FCKeditorAPI.GetInstance("EditorDefault");
			var content = fckEditor.GetHTML();
			document.forms[0].bdyContent.value = content;
			document.forms[0].submit();
	}	

function validateAddress(){
	var returnmsg = true;
	var inputText = document.forms[0].toAddress.value;
	if(inputText.indexOf(",") != -1){
		inputText = inputText.substring(0,inputText.lastIndexOf(","));
	}
	inputText= inputText.split(",");
    for (var k = 0; k < inputText.length; k++) {
    alert(inputText[k]);
		if(inputText[k] == ""){
		alert("Please enter To address!");
		document.forms[0].toAddress.focus();
			returnmsg = false;
		}
		else if(inputText[k].indexOf("@microlabs") == -1){
			alert("You can send mail to this corporate e-mail address!");
			document.forms[0].toAddress.focus();
			returnmsg = false;
	}
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
		if(inputText[k].match(mailformat))  
	{    
			returnmsg = true;
	}  
	else  
	{  
		alert("You have entered an invalid email address!");  
		document.forms[0].toAddress.focus(); 
			returnmsg = false;  
		}
	}
	return returnmsg;
}

var myVar=setInterval(function(){saveAsDraft()},180000);

function saveAsDraft()
{
	var fckEditor = FCKeditorAPI.GetInstance("EditorDefault");
	var content = fckEditor.GetHTML();
	document.forms[0].bdyContent.value = content;
	var data;
	data = new FormData();
	data.append('toAdd',document.forms[0].toAddress.value);
	data.append('ccAdd',document.forms[0].ccAddress.value);
	data.append('bccAdd',document.forms[0].bccAddress.value);
	data.append('sub',document.forms[0].toSubject.value);
    data.append('content', content);
    var xmlhttp;
    if (window.XMLHttpRequest){
        // code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
                  }
                  else {
        // code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }

    xmlhttp.onreadystatechange=function(){
        if (xmlhttp.readyState==4 && xmlhttp.status==200){
        	document.getElementById("succussMsg").style.display ="";
             document.getElementById("succussMsg").innerHTML="Message Saved";
             setInterval(function(){disableMessage()},6000);
        }
    }

    xmlhttp.open("POST","saveMail.do?method=displaySaveMail",true);
    xmlhttp.send(data);
}

function disableMessage(){
	document.getElementById("succussMsg").style.display ="none";
}


function cancelCompose(){
	var folderName = document.getElementById("folderName").value;
	parent.removeSelect();
	var url="mail.do?method=displayMailHome&openType=cancelNew";
	if(folderName == "Inbox"){
		url="mail.do?method=displayInboxMail&sCount=0&eCount=0";
	}
	else if(folderName == "Draft"){
		url="mail.do?method=displayDraftMail&sCount=0&eCount=0";
	}
	else if(folderName == "SentItem"){
		url="mail.do?method=displaySentMail&sCount=0&eCount=0";
	}
	else if(folderName == "Trash"){
		url="mail.do?method=displayDeletedMail&sCount=0&eCount=0";
	}
	document.forms[0].action=url;
	document.forms[0].submit();
}

function bcHover(type, elem){
	if(type == "add"){
	document.getElementById(elem.id).clasName="CB addCB";
	}
	else {
		document.getElementById(elem.id).clasName="CB";
	}
}

function clickAny(elem){
	if(elem.id == "addcc"){
		document.getElementById("addcc").style.display = (document.getElementById("addcc").style.display == "") ? "none" : "";
  		document.getElementById("ccTR").style.display = (document.getElementById("ccTR").style.display == "") ? "none" : "";
  		increaseHeight(); 
	}
	else if(elem.id == "addbc"){
		document.getElementById("addbc").style.display = (document.getElementById("addbc").style.display == "") ? "none" : "";
  		document.getElementById("bccTR").style.display = (document.getElementById("bccTR").style.display == "") ? "none" : "";
  		increaseHeight();
	}
	else if(elem.id == "removecc"){
		document.getElementById("ccTR").style.display="none";
  		document.getElementById("addcc").style.display="";
  		decreseHeight();
	}
	else if(elem.id == "removebcc"){
		document.getElementById("bccTR").style.display="none";
  		document.getElementById("addbc").style.display="";
		decreseHeight();
	}
}

function increaseHeight(){
	document.getElementById("NewTable").style.height = (parseInt(document.getElementById("NewTable").style.height) + 30) + "px";
	resizeAttachIframe("add");
}
function decreseHeight(){
	document.getElementById("NewTable").style.height = (parseInt(document.getElementById("NewTable").style.height) - 30) + "px";
	resizeAttachIframe("delete");
}
function resizeAttachIframe(elem) {
	if((document.body.scrollHeight)<378){
  		parent.document.getElementById("mailFR").style.height = '378px';
  	}
	if(elem == "add"){
  		parent.document.getElementById("mainFR").style.height = (parseInt(parent.document.getElementById("mainFR").style.height) + 30) + 'px';
 	}
 	else{
  		parent.document.getElementById("mainFR").style.height = (parseInt(parent.document.getElementById("mainFR").style.height) - 30) + 'px';
 	}
}

function searchUsers(){
	var toadd = document.getElementById("toAddress").value;
	if(toadd.indexOf(",") >= -1){
		toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
	}
	document.getElementById("toAddress").focus();
	if(toadd == ""){
		document.getElementById("toAddress").focus();
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
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        }
    }

    xmlhttp.open("POST","mail.do?method=searchGivenUser&sId=New&searchText="+toadd,true);
    xmlhttp.send();
}
function selectUser(input){
	var toinput=document.getElementById("toAddress");
	var toadd = toinput.value
	if(toadd.indexOf(",") >= -1){
	toadd = toadd.substring(0,(toadd.lastIndexOf(",")+1));
	}
	else{
		toadd = "";
	}
	document.getElementById("tempAdd").value = toadd + input.id+",";
	document.getElementById("toAddress").value=document.getElementById("tempAdd").value;
	disableSearch();
}
function disableSearch(){
	if(document.getElementById("sU") != null){
	document.getElementById("sU").style.display="none";
	}
}


function onDeleteFile(attName){
var ifH = document.getElementById("fr1").height;
ifH = parseInt(ifH) - 30;
document.getElementById("fr1").height = ifH;
resizeAttachIframe("delete");
	document.forms[0].action="mail.do?method=deleteUploadedFiles&cValues="+attName;
	document.forms[0].submit();
}

</script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>

</head>

<body>


<%
UserInfo user=(UserInfo)session.getAttribute("user");
%>
<div class="middel-blocks">

	<html:form action="mail.do" enctype="multipart/form-data" styleId="newForm">
		<table id="NewTable" style="width:600px; height:375px;" onclick="disableSearch()">
			
		    <tr>
			<th width="100" class="specalt" scope="row">From </th>
				<td>&nbsp;&nbsp;<bean:write property="fromInbox" name="mailInboxForm" /></td>
				<th style="display:none;"><html:text property="fromInbox" styleClass="text_field" style="width:600px;" maxlength="80" styleId="fromInbox" ><bean:write property="fromInbox" name="mailInboxForm" /></html:text></th>
				<th id="succussMsg" style="color:red;text-align:right;"></th>
			</tr>
			<tr></tr>
			<tr>
				<th style="width: 100px;" class="specalt" scope="row">To <img src="images/star.gif" width="8" height="8" /></th>
				<td style="width:400px;">&nbsp;&nbsp;<html:text property="toAddress" styleClass="rounded" style="width:95%;" maxlength="180" styleId="toAddress" onkeyup="searchUsers()"><bean:write property="toAddress" name="mailInboxForm" /></html:text></td>
				<th style="width: 50px;" id="addcc" class="CB" onclick="clickAny(this)" onmouseover="bcHover('add',this)" onmouseout="bcHover('remove',this)">Cc&nbsp;</th>
				<th style="width:50px;" id="addbc" class="CB" onclick="clickAny(this)" onmouseover="bcHover('add',this)" onmouseout="bcHover('remove',this)">&nbsp;Bcc</th>
			</tr>
			<tr></tr>
			<tr id="sU" style="display:none;">
				<th width="100" class="specalt" scope="row">&nbsp;&nbsp;</th>
				<td id="sUTD" style="width:400px;">&nbsp;&nbsp;<iframe src="jsp/main/searchUsers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe></td>
				<th></th>
				<th></th>
			</tr>
			<tr></tr>
			<tr id="ccTR" style="display:none;">
			<th width="100" class="specalt" scope="row">Cc</th>
				<td style="width:400px;">&nbsp;&nbsp;<html:text property="ccAddress" styleClass="rounded" style="width:95%;" maxlength="80" styleId="ccAddress"><bean:write property="ccAddress" name="mailInboxForm" /></html:text></td>
				<th id="removecc" class="iconcur" onclick="clickAny(this)"><img src="images/delete.png" width="10" height="10" /></th>
				<th></th>
			</tr>
			<tr></tr>
			<tr id="bccTR" style="display:none;">
			<th width="100" class="specalt" scope="row">Bcc</th>
				<td style="width:400px;">&nbsp;&nbsp;<html:text property="bccAddress" styleClass="rounded" style="width:95%;" maxlength="180" styleId="bccAddress"></html:text></td>
				<th id="removebcc" class="iconcur" onclick="clickAny(this)"><img src="images/delete.png" width="10" height="10" /></th>
				<th></th>
			</tr>
			<tr></tr>
			<tr>
			<th width="100" class="specalt" scope="row">Subject </th>
			<td style="width:400px;">&nbsp;&nbsp;<html:text property="subject" styleClass="rounded" style="width:95%;" maxlength="180" styleId="toSubject"><bean:write property="subject" name="mailInboxForm" /></html:text></td>
			</tr>
			<tr></tr>
			<tr id="attNames"><th width="100"></th><td colspan="3" id="attvalueTR"><iframe src="jsp/main/fileUpload.jsp"  id="fr1" scrolling="no" frameborder="0" width="100%" height="30"></iframe></td></tr>
			<logic:notEmpty name="listName">
			<logic:iterate name="listName" id="abc">
			<tr>
				<th width="100" class="specalt" scope="row">&nbsp;&nbsp;</th>
				<td colspan="3">&nbsp;&nbsp;<bean:write name="abc" property="fileList1"/> - <bean:write name="abc" property="fileSize"/>
				<span><img src="images/delete.png" width="10" height="10" onclick="onDeleteFile('<bean:write name="abc" property="fileList1"/>')"/></span></td>
			</tr>
			</logic:iterate>
			</logic:notEmpty>
			<tr>
				<th width="100" class="specalt" scope="row"></th>
				<td align="right" width="400">&nbsp;&nbsp;
				 <html:text property="description" styleClass="text_field" style="display:none;" styleId="bdyContent"></html:text>
					<FCK:editor instanceName="EditorDefault" toolbarSet="Basic" width="820">
							   <jsp:attribute name="value">
							   <div id="bdyId" ><% String bc = (String)request.getAttribute("bdyContent"); out.println(bc);%></div>          
				               </jsp:attribute>
					</FCK:editor>
				</td>
				<th></th>
				<th></th>
			</tr>
			<tr></tr>
			<tr>
			<th width="100" class="newth" scope="row"></th>
		    	<td align="center"><html:button styleClass="rounded" property="method" styleId="sendMailId" onclick="sendMail()" value="Send"></html:button>&nbsp;&nbsp;&nbsp;<html:button styleClass="rounded" property="method" styleId="saveMail" onclick="saveAsDraft()">Save As Draft</html:button>&nbsp;&nbsp;&nbsp;<html:button styleClass="rounded" styleId="cancelMail" property="method" onclick="cancelCompose()">Cancel</html:button></td>
		    	<th></th>
		    </tr>
		    
		</table>
		<input style="display:none;" id="tempAdd" value=""/>
		<input style="visibility:hidden;" id="folderName" value="<bean:write property="searchText"  name="mailInboxForm"/>"/>
		<div style="display:none">
			<table>
			<tr id="ccDiv">
			</tr>
			</table>
		</div>
	</html:form>
</div>
</body>
</html>
