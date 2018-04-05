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
<jsp:directive.page import="com.microlabs.main.form.MailInboxForm"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Sent Mail </title>
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>

<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>
	<!-- import the language module -->
	<script type="text/javascript" src="calender/js/calendar-en.js"></script>
	<!-- other languages might be available in the lang directory; please check your distribution archive. -->
	<!-- ALL demos need these css -->
	<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>

	<!-- Theme css -->
	<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<style type="text/css">
.subTD
{
	cursor:pointer;
    max-width: 150px;
    max-height: 50px;
    overflow: hidden;
}

.search_bg{width:100&; height:30px; background-color:#f1f1f1;}

.openMail{
display:inline-block;
border-radius: 5px;
position: fixed;
width: 99%; 
top: 5;
left: 9;
z-index: 10;
box-shadow: 1px 1px 3px 2px #63b8ff;
-moz-box-shadow: 1px 1px 3px 2px #63b8ff;
  -webkit-box-shadow: 1px 1px 3px 2px #63b8ff;
}

.textStyle{
    display: inline-block;
    color: rgb(255, 255, 255);
    background-color: rgb(180, 182, 174);
    font-weight: bold;
    font-size: 12px;
    text-align: center;
    padding: 3px 10px 4px;
    text-decoration: none;
    margin-left: 5px;
    margin-top: 0px;
    margin-bottom: 5px;
    border: 1px solid rgb(170, 170, 170);
    border-radius: 5px 5px 5px 5px;
    white-space: nowrap;
}

.hideopenMail{
display:none;
}

.readTR{
font: normal 12px "Arial", Verdana, Arial, Helvetica, sans-serif;
}

.bdyTD{
opacity:0.2;
filter:Alpha(opacity=20); /* IE8 and earlier */
}
body{font-family:arial;}
a{color:black;text-decoration:none;font:bold}
span:hover{color:#3fa6f3;text-transform: uppercase;}
body{
font-family: 'trebuchet MS', 'Lucida sans', Arial;
}
</style>
<script type="text/javascript">
function displayMe(elem){
var displayId = elem.id;
	var fname="";
	var serachValue="";
	var tab = document.getElementById("heading").innerHTML;
   	if(tab.indexOf("Search") != -1){
   		serachValue = document.getElementById("sValue").value;
   	}
	//var childElem = document.getElementById("isAttach"+displayId).getAttribute("src");
	var childElem = document.getElementById("att"+displayId).innerHTML;
	if(childElem != "")
	{
		fname="display";
	}
	var url="mail.do?method=displaySelectedMail&folder=SentItem&mailId="+displayId+"&fName="+fname+"&search="+serachValue;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function closeOpenMail(){
   	var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   	var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   	var url="mail.do?method=displaySentMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=";
   	var tab = document.getElementById("heading").innerHTML;
   	if(tab.indexOf("Search") != -1){
   		var srcTxt = document.getElementById("sValue").value;
   		var url="mail.do?method=searchInMail&folder=SentItem&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount;
   	}
	document.forms[0].action=url;
	document.forms[0].submit();
}
function downloadIt(elem){
	var mailId = '<bean:write name="mailInboxForm" property="mailId"/>';
  	var fname = elem;//document.getElementById("attName").innerHTML;
	var url="mail.do?method=displaySelectedMail&folder=SentItem&mailId="+mailId+"&fName="+fname;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function deleteMsg(elem){
	var elemId = elem.id;
	var mId = elemId.replace("del","");
	var url="deleteMail.do?method=removeMsgFromList&folder=SentItem&mailId="+mId;
	document.forms[0].action=url;
	document.forms[0].submit();
	
}

function sentNavigation(naviType){
	if(naviType=="onload"){
		document.getElementById("successful").style.display="";
		setTimeout(function(){document.getElementById("successful").style.display="none";},5000);
	}
	else if(naviType == "next"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displaySentMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=SentItem&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "prev"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displaySentMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=SentItem&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "atLast"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displaySentMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=SentItem&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "veryFirst"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displaySentMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=SentItem&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "actions"){
		var idDis = document.getElementById("Actions").style.display;
		if(idDis == "none"){
			document.getElementById("Actions").style.display = "";
		}
		else{
			document.getElementById("Actions").style.display = "none";
		}
	}
	
}

function searchInMail(){
	if(type == "clear"){
		var url="mail.do?method=displaySentMail&sCount=0&eCount=0";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else{
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
	   	var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
		var srcTxt = document.getElementById("searchText").value;
		var url="mail.do?method=searchInMail&folder=SentItem&searchText="+srcTxt+"&sCount=0&eCount=0";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
}

function rrF(elem, type){
	var displayId = elem.id;
	var serachValue="";
	var url="mail.do?method=displayComposeMail&sId="+type+"&mailId="+displayId+"&folder=SentItem";
	document.forms[0].action=url;
	document.forms[0].submit();
}

</script>

  </head>
  <body onload="sentNavigation('onload')">
  	<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-left:15px">
   		<tr>
    		<td width="95%" align="center" valign="top">
			<div class="middel-blocks-iframe">
	    			<html:form action="mail.do" enctype="multipart/form-data">
      					<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
        					<tr>
		          				<td align="left"><logic:notEmpty name="mailInboxForm">
      							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="heading widgetTitle"><td id="heading"><bean:write property="heading" name="mailInboxForm"/></td><td style="text-align:center; width:300px;"><span id="successful"><bean:write name="mailInboxForm" property="mailMessage"/></span></td></tr>
           						</table></logic:notEmpty>
		          				</td>
		          			</tr>
          					<logic:notEmpty name="openMail">
          					<tr><td>
          						<div id="openMailDiv" class="openMail">
        					<table id="mytable1" style="width:100%; height:100%; border-radius:5%;">
          						<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">To </th><td id="toId" style="width:60%; border:0px;"><bean:write name="mailInboxForm" property="fromInbox"/>  to  <bean:write name="mailInboxForm" property="toAddress"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;" onclick="closeOpenMail()"><img id="closeMail" src="images/close.png" width="16" height="16"/></td></tr>
          								<tr><th class="specalt" width="10%" scope="row" style="border-radius:5px;">Subject</th><td id="subId" style="width:60%; border:0px;"><bean:write name="mailInboxForm" property="subject"/></td><td style="background:#ffffff; width:30%; border:0px; text-align:right;" id="dtId"><bean:write name="mailInboxForm" property="dateTime"/></td></tr>
          						<tr style="background-color:#f1f1f1;"><td colspan="3" style="border:0px; border-radius:5px; text-align:right;"><a id="Actions" style="display:none;"><span style="padding-left: 10px;" id="<bean:write name="mailInboxForm" property="mailId"/>" onclick="rrF(this,'reply')">Reply</span><span style="padding-left: 10px" id="<bean:write name="mailInboxForm" property="mailId"/>" onclick="rrF(this,'RAll')">ReplyAll</span><span style="padding-left: 10px;padding-right: 10px;" id="<bean:write name="mailInboxForm" property="mailId"/>" onclick="rrF(this,'FW')">Forward</span></a><a style="color:rgb(141, 139, 139);" id="actiondrop" onclick="sentNavigation('actions')">Actions </a></td></tr>
          						<tr><td colspan="3" style="border:0px; height:100%">
          								<table width="100%">
          									<logic:notEmpty name="bdyContent">
          									<tr>
          								<td colspan="3" style="height:200px; border:0px;"><div id="bdyId" style="width:100%; height:100%; overflow-y: auto;padding-left: 1.5%;">
          								<% String bc = (String)request.getAttribute("bdyContent");
          								bc = bc.replaceAll("(\\\\r\\\\n|\\\n|\\\r)", "<br/>");
          								out.println(bc);
          								%>
          								</div></td>
          									</tr>
          									</logic:notEmpty>
          									<logic:iterate name="attachDetails" id="abc">
											<tr style="background-color: #f1f1f1;">
											
												<td id="att" colspan="2" style="width:50%; border:0px;"><span id="attName"><bean:write name="abc" property="fileList1"/></span> - <bean:write name="abc" property="fileSize"/></td><td style="background:#ffffff; width:10%; border:0px; cursor: hand; color: #3fa6f3;" id="attDownload" onclick="downloadIt('<bean:write name="abc" property="fileList1"/>')">Download</td>
											
											</tr>
											</logic:iterate>
										</table>
										</td></tr>
          							</table>
          						</div>
          					</td></tr>
          					</logic:notEmpty>
                			<tr>
		                		<td align="center" valign="top">
									<table align="center" width="100%">
									<logic:notEmpty name="displayRecordNo">
				 						<tr>
				 							<td style="width:35%;"></td>
				 							<logic:notEmpty name="veryFirst">
											<td style="width:2%;"><img src="images/First10.jpg"id="veryFirstItem" onclick="sentNavigation('veryFirst')"/></td>
											</logic:notEmpty>
											<logic:notEmpty name="disablePreviousButton">
											<td style="width:2%;"><img src="images/disableLeft.jpg" /></td>
											</logic:notEmpty>
											<logic:notEmpty name="previousButton">
											<td style="width:2%;"><img src="images/previous1.jpg" id="privSetItem" onclick="sentNavigation('prev')"/></td>
											</logic:notEmpty>

											<td style="width:8%;text-align:center;"><bean:write property="startMailCount"  name="mailInboxForm"/>-<bean:write property="endMailCount"  name="mailInboxForm"/></td>

											<logic:notEmpty name="nextButton">
											<td id="nextSet" style="width:2%;"><img src="images/Next1.jpg" id="nextSetItem" onclick="sentNavigation('next')"/></td>
											</logic:notEmpty>
											<logic:notEmpty name="disableNextButton">
											<td style="width:2%;"><img src="images/disableRight.jpg"/></td>
											</logic:notEmpty>
											<logic:notEmpty name="atLast">
											<td style="width:2%;"><img src="images/Last10.jpg" id="atLastItem" onclick="sentNavigation('atLast')"/></td>
											</logic:notEmpty>
											<td style="align:right;text-align:center;">
												<img src="images/clear.jpg" style="vertical-align:middle;" onclick="searchInMail('clear');" width="25" height="25" />
												<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in SentItem" onmousedown="this.value='';"/>
												<img src="images/search-bg.jpg" style="vertical-align:middle;" onclick="searchInMail('search')" width="40" height="50" />
											</td>
										</tr>
									</logic:notEmpty>
									</table>
									</td></tr>
									<tr><td>
									<div align="left" class="bordered">
									<table  border="1"  style="aligh:right;width:100%;font-size:12px;" class="sortable">
										<tr>
										<th style="text-align:left;"><b></b></th>
										<th style="text-align:left;"><b></b></th>
										<th style="text-align:left;"><b>To</b></th>
										<th style="text-align:left;" width="400px"><b>Subject</b></th>
										<th style="text-align:left;"><b></b></th>
										<th style="text-align:left;"><b></b></th>
										<th style="text-align:left;"><b>Date</b></th>
										</tr>
										<logic:notEmpty name="noRecords">
										<tr>
	        								<td colspan="7" style="text-align:center;"><bean:write name="mailInboxForm" property="message"/></td>
	            						</tr>
	            						</logic:notEmpty>
										<logic:iterate name="SentDetails" id="abc">
										<tr class="<bean:write name="abc" property="read"/>">
											<td id="chk<bean:write name="abc" property="mailId"/>"><html:checkbox property="read" name="abc" value="${abc.mailId}" styleId="" style="width :20px;"/></td>
											<td id="flg<bean:write name="abc" property="mailId"/>" ></td>
											<td id="to<bean:write name="abc" property="mailId"/>" style="width:20%;"><bean:write name="abc" property="toAddress"/></td>
											<td onclick="displayMe(this)" class="subTD" style="color:#127BCA;width:50%;" id="<bean:write name="abc" property="mailId"/>"><bean:write name="abc" property="subject"/>&nbsp;-&nbsp;<span class="bdyTD" id="bdy<bean:write name="abc" property="mailId"/>"><bean:write name="abc" property="description"/></span></td>
											<td id="att<bean:write name="abc" property="mailId"/>"><logic:equal value="attach" property="attach" name="abc"><img id="isAttach" title="Attachments" src="images/att_icon.gif"/></logic:equal></td>
											<td id="del<bean:write name="abc" property="mailId"/>" style="cursor:pointer;" onclick="deleteMsg(this)"><img id="del<bean:write name="abc" property="mailId"/>" src="images/delete.png"/></td>
											<td id="dt<bean:write name="abc" property="mailId"/>"><bean:write name="abc" property="dateTime"/></td>
										</tr>
										</logic:iterate>
									</table>
									</div>
								</td>
							</tr>
    				</table>
    				<input style="visibility:hidden;" value="<bean:write property="searchText"  name="mailInboxForm"/>" id="sValue"/>
				</html:form>
			</div>
    		</td>
   		</tr>
   	</table>
  </body>
</html>
