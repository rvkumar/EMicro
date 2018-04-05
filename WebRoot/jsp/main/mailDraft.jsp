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
<title>Draft </title>
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
    max-width: 150px;
    max-height: 50px;
    overflow: hidden;
}
.openMail{
display:inline-block;
background-color:#f1f1f1;
border-radius: 5px;
position: absolute;
width: 99%; 
top: 5;
left: 10;
z-index: 10;
box-shadow: 0px 5px 5px 0 #d1e8f4, 0px -5px 5px 0 #d1e8f4;
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
body{
font-family: 'trebuchet MS', 'Lucida sans', Arial;
}
</style>
<script type="text/javascript">
function displayMe(elem){
var displayId = elem.id;
	var tab = document.getElementById("heading").innerHTML;
	var serachValue="";
   	if(tab.indexOf("Search") != -1){
   		serachValue = document.getElementById("sValue").value;
   	}
	//var childElem = document.getElementById("isAttach"+displayId).getAttribute("src");
	var childElem = document.getElementById("att"+displayId).innerHTML;
	var url="mail.do?method=displayComposeMail&sId=Open&mailId="+displayId+"&search="+serachValue;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function closeOpenMail(){
   	var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   	var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   	var url="mail.do?method=displayInboxMail&sCount="+sCount+"&eCount="+eCount;
   	var tab = document.getElementById("heading").innerHTML;
   	if(tab.indexOf("Search") != -1){
   		var srcTxt = document.getElementById("sValue").value;
   		var url="mail.do?method=searchInMail&folder=Draft&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount;
   	}
	document.forms[0].action=url;
	document.forms[0].submit();
}

function deleteMsg(elem){

	var elemId = elem.id;
	var mId = elemId.replace("del","");

	var url="deleteMail.do?method=removeMsgFromList&folder=Draft&mailId="+mId;
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
   		var url="mail.do?method=displayDraftMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=Draft&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=next";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "prev"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displayDraftMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=Draft&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=priv";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "atLast"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displayDraftMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=draft&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=alast";
	   	}
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else if(naviType == "veryFirst"){
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
   		var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
   		var url="mail.do?method=displayDraftMail&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
   		var tab = document.getElementById("heading").innerHTML;
	   	if(tab.indexOf("Search") != -1){
	   		var srcTxt = document.getElementById("sValue").value;
	   		var url="mail.do?method=searchInMail&folder=Draft&searchText="+srcTxt+"&sCount="+sCount+"&eCount="+eCount+"&fnpl=vfirst";
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
		var url="mail.do?method=displayDraftMail&sCount=0&eCount=0";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
	else{
		var sCount = '<bean:write property="startMailCount"  name="mailInboxForm"/>';
	   	var eCount = '<bean:write property="endMailCount"  name="mailInboxForm"/>';
		var srcTxt = document.getElementById("searchText").value;
		var url="mail.do?method=searchInMail&folder=Draft&searchText="+srcTxt+"&sCount=0&eCount=0";
		document.forms[0].action=url;
		document.forms[0].submit();
	}
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
          							<table id="mytable1" style="width:100%; heigth:100%; border-radius:5%">
          								<tr><th  class="specalt" width="5%" scope="row">To </th><td id="toId" style="width:80%; border:0px;"><bean:write name="mailInboxForm" property="toAddress"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;" onclick="closeOpenMail()"><img id="closeMail" src="images/Close.png" width="16" height="16"/></td></tr>
          								<tr><th class="specalt" width="5%" scope="row"></th><td id="subId" style="width:80%; border:0px;"><bean:write name="mailInboxForm" property="subject"/></td><td style="background:#ffffff; width:3%; border:0px; text-align:right;"></td></tr>
          								<tr id="openMailAtt"><th width="5%" class="specalt" scope="row" id="attachId"></th><td style="width:80%; border:0px;" id="attDownload"><bean:write name="mailInboxForm" property="attach"/></td><td style="background:#ffffff; width:10%; border:0px; text-align:right;" id="dtId"><bean:write name="mailInboxForm" property="dateTime"/></td></tr>
          								<tr class="underline"><td></td><td></td><td></td></tr>
          								<tr>
          									<td colspan="3" style="height:260px; border:0px;"><div id="bdyId" style="background:#ffffff; width:100%; height:100%; overflow-y: auto;"><% String bc = (String)request.getAttribute("bdyContent"); out.println(bc);%><!--<bean:write name="mailInboxForm" property="description"/>--></div></td>
          								</tr>
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
												<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in Trash" onmousedown="this.value='';"/>
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
										<logic:iterate name="DraftDetails" id="abc">
										<tr>
											<td id="chk<bean:write name="abc" property="mailId"/>"><html:checkbox property="read" name="abc" value="${abc.mailId}" styleId="" style="width :20px;"/></td>
											<td id="flg<bean:write name="abc" property="mailId"/>"></td>
											<td id="to<bean:write name="abc" property="mailId"/>" style="<bean:write name="abc" property="read"/>"><bean:write name="abc" property="toAddress"/></td>
											<td onclick="displayMe(this)" class="subTD" style="<bean:write name="abc" property="read"/>" id="<bean:write name="abc" property="mailId"/>"><bean:write name="abc" property="subject"/>&nbsp;-&nbsp;<span class="bdyTD" id="bdy<bean:write name="abc" property="mailId"/>"><bean:write name="abc" property="description"/></span></td>
											<td id="att<bean:write name="abc" property="mailId"/>"><logic:equal value="attach" property="attach" name="abc"><img id="isAttach" title="Attachments" src="images/att_icon.gif"/></logic:equal></td>
											<td id="del<bean:write name="abc" property="mailId"/>" onclick="deleteMsg(this)"><img id="del<bean:write name="abc" property="mailId"/>" src="images/delete.png"/></td>
											<td id="dt<bean:write name="abc" property="mailId"/>" style="<bean:write name="abc" property="read"/>"><bean:write name="abc" property="dateTime"/></td>
										</tr>
										
										</logic:iterate>
									</table>
									</div>
                				</td>
		        		</tr>
    				</table>
				</html:form>
			</div>
    		</td>
   		</tr>
   	</table>
  </body>
  
</html>
