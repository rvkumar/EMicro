
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
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Home Page</title>
<script src="js/common.js"></script>

<script language="javascript">
window.onload = function() {
    for(var i = 0, l = document.getElementsByTagName("input").length; i < l; i++) {
        if(document.getElementsByTagName("input").item(i).type == "text") {
            document.getElementsByTagName("input").item(i).setAttribute("autocomplete", "off");
        }
    }
};
//while editing or adding approvers for request
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
        	document.getElementById("sU"+elm).style.display ="";
        	document.getElementById("sUTD"+elm).innerHTML=xmlhttp.responseText;
        }
    }

    xmlhttp.open("POST","authentication.do?method=searchGivenUser&sId=New&searchText="+toadd,true);
    xmlhttp.send();
}
	
	
//for set approver id in hidden value	
function SetApproverID (input) {
if (input.value == "") {
		input.style.cssText = 'color: grey;width:200px';
        input.value = input.defaultValue;
    }
    document.getElementById("appIdValue").value= input.value;
}
//for add or add more approver 			
function showLevels(elem){
	document.getElementById("definelevels").innerHTML = "";
	var levels = "";
	if(elem == "Add"){
		levels = document.getElementById("levelNo").value;
	}
	else{
		var pSubject = prompt("How Many Approvers To Add?","1");
		levels = pSubject;
		document.getElementById("levelNo").value = levels;
		document.getElementById("ascTR").style.display = "none";
		document.getElementById("editPage").innerHTML = "";
	}
	levels = levels.trim();
	levels = levels.replace(' ', '');
	if(levels == "" || levels == "0"){
		alert("Please Enter Approvals Level!");
		document.forms[0].employeeNumber.focus();
		return false;
	}
	var appTable = document.getElementById("definelevels");
	for(var i = 1; i <= levels; i++){
		var trElem = document.createElement("tr");
		var tdElem = document.createElement("td");
		tdElem.setAttribute("style", "width:25%");
		tdElem.setAttribute("id", i);
		
		//This is for Approver ID Field	
		var element = document.createElement("input");
		
		//Create Labels
		var label = document.createElement("Label");
		label.innerHTML = "Approver "+i+"<br/>";     
		
		//Assign different attributes to the element.
		element.setAttribute("type", "text");
		element.setAttribute("autocomplete", "off");
		element.setAttribute("value", "Employee ID");
		element.setAttribute("name", "approverID");
		element.setAttribute("style", "width:200px");
		element.setAttribute("class", "rounded");
		element.setAttribute("onkeyup","searchUsers(this,"+i+")");
		
		element.setAttribute("onfocus", "this.value=''");
		element.setAttribute("onblur", "SetApproverID (this)");
		 
		element.setAttribute("id", "aprID"+i);
		label.setAttribute("style", "font-weight:normal");
		     
		tdElem.appendChild(label);
		tdElem.appendChild(element);
		
		//Create span tag for name & email, designation
		//var ifr1 = document.getElementById("if1").innerHTML;
		var tdIF = document.createElement("td");
		tdIF.setAttribute("id", "iftd"+i);
		//tdIF.innerHTML = ifr1;
		
		
		trElem.appendChild(tdElem);
		trElem.appendChild(tdIF);
		appTable.appendChild(trElem);
		
		var trem = document.createElement("tr");
		trem.setAttribute("style","height:10px");
		var tdem = document.createElement("td");
		trem.appendChild(tdem);
		appTable.appendChild(trem);
		
		//for search users
		var sutr = document.createElement("tr");
		sutr.setAttribute("id", "sU"+i);
		var sutd = document.createElement("td");
		sutd.setAttribute("colspan", "2");
		sutd.setAttribute("style", "width:75%");
		sutd.setAttribute("align", "center");
		sutd.setAttribute("id", "sUTD"+i);
		sutr.appendChild(sutd);
		appTable.appendChild(sutr);
	}
	//for save button
	var trElem1 = document.createElement("tr");
	var tdElem1 = document.createElement("td");
	tdElem1.innerHTML = document.getElementById("saveandcancel").innerHTML;
	tdElem1.setAttribute("colspan", "2");
	tdElem1.setAttribute("align", "center");
	trElem1.appendChild(tdElem1);
	appTable.appendChild(trElem1);
}

//for save the approvers

function saveDetails(elem){
	var levels=""
	if(elem == "edit"){
		levels = document.getElementById("appCount").value;
		document.getElementById("levelNo").value = levels;
	}
	else{
	levels = document.getElementById("levelNo").value;
	}
	levels = levels.trim();
	levels = levels.replace(' ', '');
	if(document.forms[0].employeeNumber.value=="")
   	{
    	alert("Please Enter Required Level Number ");
     	document.forms[0].requestType.focus();
     	return false;
   	}
   	else {
   		if(elem == "save"){
	   		for(var i = 1; i <= levels; i++){
	   			if(document.getElementById("aprID"+i).value == document.getElementById("aprID"+i).defaultValue){
	   				alert("Please Enter Valid Approver"+i+" Number!");
	     			document.getElementById("aprID"+i).focus();
	     			return false;
	   			}
	   		}
   		}
   	}

	var url="authentication.do?method=saveAuthenticationDetails&type="+elem;
			document.forms[0].action=url;
			document.forms[0].submit();	

}

//for view the approvers for request//

function viewDetails(input){
var type = input;
var reqType = "";
	if(type == "view"){
		type = "View";
		if(document.forms[0].requestType.value=="-Select-")
	    {
	      alert("Please Select Request Type ");
	      document.forms[0].requestType.focus();
	      return false;
	    }
	}
	else if(type == "add new"){
		type = "Add New Request";
	}
	else{
		type=input.value;
		reqType = input.id;
	}
	var url="authentication.do?method=viewDetails&Type="+type+"&reqType="+reqType;
			document.forms[0].action=url;
			document.forms[0].submit();

}

function cancelAll(){
var url="authentication.do?method=displaynewForm&sId=Authentication&id=Admin";
			document.forms[0].action=url;
			document.forms[0].submit();
}


function showMoreAction(){
var idDis = document.getElementById("Actions").style.display;
var ad = document.getElementById("actiondrop");
var acticon = document.getElementById("acticon");
if(idDis == "none"){
	ad.setAttribute("style","font-weight:normal");
	acticon.setAttribute("src","images/lefarrow.png");
	document.getElementById("Actions").style.display = "";
}
else{
	ad.setAttribute("style","font-weight:bold;");
	acticon.setAttribute("src","images/rhtarrow.png");
	document.getElementById("Actions").style.display = "none";
}
}

function deleteThis(elem,type){
	var reqType = type;
var url="authentication.do?method=deleteDetails&empid="+elem.id+"&reqType="+reqType;
			document.forms[0].action=url;
			document.forms[0].submit();
}

function addNewRequest(){
	var reqName = document.getElementById("reqName").value;
	if(reqName == "")
	{
		alert("Please Enter Request Name!");
	    document.getElementById("requestName").focus();
	    return false;
	}
	var url="authentication.do?method=addNewRequest&reqName="+reqName;
	document.forms[0].action=url;
	document.forms[0].submit();
}

function setApproverDetails(empid){
var reqType = document.getElementById("reqType").value;
var toid = document.getElementById("selectInput").value;
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
        	document.getElementById("iftd"+toid).innerHTML=xmlhttp.responseText;
        }
    }

    xmlhttp.open("POST","authentication.do?method=getDetails&empid="+empid+"&reqType="+reqType,true);
    xmlhttp.send();
}

function selectUser(input){
	var toid = document.getElementById("selectInput").value;
	document.getElementById("aprID"+toid).value= input.innerHTML;
	if(disableSearch(toid)){
		setApproverDetails(document.getElementById("aprID"+toid).value);
	}
	
}
function disableSearch(elem){
	if(document.getElementById("sU"+elem) != null){
	document.getElementById("sU"+elem).style.display="none";
	}
	return true;
}

</script>
<style type="text/css">
body{
font-family: 'trebuchet MS', 'Lucida sans', Arial;
}
</style>
</head>

<body >
<html:form action="authentication" enctype="multipart/form-data">
	<div align="center">
		<logic:present name="authenticationForm" property="message">
		<font color="red">
			<bean:write name="authenticationForm" property="message" />
		</font>
		</logic:present>
	</div>
	<table align="center" width="75%">
	<tr>
      	<td colspan="3">
      		<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr style="border-collapse:collapse;" class="heading widgetTitle"><td id="heading">Authentication for Request <bean:write property="requestType" name="authenticationForm"/></td></tr>
		    </table>
		</td>
	</tr>
	<tr style="height:10px;"><td></td></tr>
	<logic:empty name="addNewRequest">
	<tr>
		<th width="200px" scope="row">Request Type<img src="images/star.gif" width="8" height="8" /></th>
		<td><html:select  property="requestType" onchange="viewDetails('view');" tabindex="1" styleId="reqtypeid" style="border-radius:5px;">
						<html:options property="requestNames" labelProperty="requestNames"/>
					</html:select>
		</td>
		<td><html:button styleClass="rounded" property="method" styleId="addnew" onclick="viewDetails('add new');" style="width:150px;" value="Add New Request"></html:button></td>
	</tr>
	</logic:empty>
	<tr style="height:10px;"><td></td></tr>
	<logic:notEmpty name="levelPart">
	<tr>
		<td colspan="3">
			<table id="leveldetails" width="100%"  align="left">
				<tr>
					<th width="200px" class="specalt" scope="row">Get Approval from <img src="images/star.gif" width="8" height="8" /></th>
					<td align="left"><html:text property="employeeNumber" styleClass="rounded" onmousedown="this.value='';" style="width:240px;"  styleId="levelNo"/> <b>Levels</b>&nbsp;<span>&nbsp;&nbsp;<html:button styleClass="rounded" property="method" styleId="sendMail" onclick="showLevels('Add');" style="width:100px;" value="Go"></html:button></span></td>
				</tr>
			</table>
			<br/>
	</td></tr>
	</logic:notEmpty>
	<tr style="height:10px;"><td></td></tr>
	<logic:notEmpty name="addNewRequest">
	<tr>
		<td colspan="3">
			<table width="100%"  align="left">
				<tr>
					<th width="200px" class="specalt" scope="row">Request Name <img src="images/star.gif" width="8" height="8" /></th>
					<td align="left"><html:text property="employeeNumber" styleClass="rounded" onmousedown="this.value='';" style="width:240px;"  styleId="reqName"/>&nbsp;<span>&nbsp;&nbsp;<html:button styleClass="rounded" property="method" styleId="sendMail" onclick="addNewRequest();" style="width:100px;" value="Add"></html:button></span></td>
				</tr>
			</table>
			<br/>
	</td></tr>
	</logic:notEmpty>
	<tr style="height:10px;"><td></td></tr>
	<tr>
		<td colspan="3">
			<table id="definelevels" width="100%" align="center">
			
			</table>
		</td>
				</tr>
	
	<tr><td align="center" valign="top" colspan="3">
     	<table align="center" style="height:30px;width:100%;">
		<logic:notEmpty name="displayRecordNo">
	<tr>
 				<td style="width:35%;"></td>
 				<logic:notEmpty name="veryFirst">
				<td style="width:2%;"><img src="images/First10.jpg" id="veryFirstItem" onclick="sentNavigation('veryFirst','authentication')"/></td>
				</logic:notEmpty>
				<logic:notEmpty name="disablePreviousButton">
				<td style="width:2%;"><img src="images/disableLeft.jpg" /></td>
				</logic:notEmpty>
				<logic:notEmpty name="previousButton">
				<td style="width:2%;"><img src="images/previous1.jpg" id="privSetItem" onclick="sentNavigation('prev','authentication')"/></td>
				</logic:notEmpty>

				<td id="secnt" style="width:8%;text-align:center;"><bean:write property="startRequestCount"  name="authenticationForm"/>&nbsp;-&nbsp;<bean:write property="endRequestCount"  name="authenticationForm"/></td>
				<logic:notEmpty name="nextButton">
				<td id="nextSet" style="width:2%;"><img src="images/Next1.jpg" id="nextSetItem" onclick="sentNavigation('next','authentication')"/></td>
				</logic:notEmpty>
				<logic:notEmpty name="disableNextButton">
				<td style="width:2%;"><img src="images/disableRight.jpg"/></td>
				</logic:notEmpty>
				<logic:notEmpty name="atLast">
				<td style="width:2%;"><img src="images/Last10.jpg" id="atLastItem" onclick="sentNavigation('atLast','authentication')"/></td>
				</logic:notEmpty>
				<td style="align:right;text-align:center;width:35%;">
					
				</td>
				</tr>
		</logic:notEmpty>
		</table>
	</td></tr>
	
				<tr>
		<td colspan="3">
		<logic:notEmpty name="requestList">
			<div align="left" class="bordered">
				<table border="1"  style="aligh:right;width:100%;font-size:12px;" class="sortable">
				<tr>
					<th width="5%">Sl.No</th>
					<th width="50%">Request Name</th>
					<th width="20%"></th>
					<th></th>
				</tr>
				
				<% int count = 1;%>
				<logic:iterate id="abc" name="requestList">
				<tr>
					<td width="5%"><span>${abc.apprID }</span></td>
					<td width="50%"><span>${abc.requestType }</span><html:text style="display:none;" styleId="reqType" property="requestType" value="${abc.requestType }"></html:text></td>
					<td width="20%"><html:button styleClass="rounded" property="method" styleId="${abc.requestType }" onclick="viewDetails(this)" style="width:100px;" value="${abc.addOrView}"></html:button></td>
					<td width="5%"><img src="images/delete.png" id="" onclick="deleteThis(this,'${abc.requestType }')"/></td>
				</tr>
				</logic:iterate>
				
			</table>
			<input type="hidden" id="appCount" value="<%=request.getAttribute("appcount").toString() %>"/>
			</div>
		</logic:notEmpty>
		</td>
				</tr>
	<logic:notEmpty name="viewApproverList">
	<tr id="ascvTR"><td colspan="3">
		<div align="left" class="bordered">
			<table border="1"  style="aligh:right;width:100%;font-size:12px;" class="sortable">
			<tr>
				<th>Priority</th>
				<th>User Name</th>
				<th>EMail</th>
				<th>Designation</th>
				<th></th>
				</tr>
			<logic:iterate id="abc" name="viewApproverList">
			<% if(Integer.parseInt(request.getAttribute("appcount").toString()) != 0){ %>
			<tr>
				<td width="15%">Approver <span>${abc.approverPriority }</span></td>
				<td style="width:20%">${abc.apprID }</td>
				<td>${abc.empEmailID }</td>
				<td>${abc.empDesignation}</td>
				<td><img src="images/delete.png" id="${abc.apprID }" onclick="deleteThis(this,'${abc.requestType }')"/></td>
			</tr>
			<%} %>
				</logic:iterate>
			</table>	
		</div>
		<input type="hidden" id="appCount" value="<%=request.getAttribute("appcount").toString() %>"/>
	</td></tr>
	<tr><td><html:button property="method" value="Back" styleClass="rounded" style="width:75px; height:30px;" onclick="cancelAll()"></html:button></td></tr>	
			</logic:notEmpty>
			<logic:notEmpty name="editApproverList">
	<tr id="ascTR"><td colspan="3">
		<table style="width:100%;text-align:center;" id="editPage">
				<tr><td><input type="hidden" id="appCount" value="<%=request.getAttribute("appcount").toString() %>"/></td></tr>
				<logic:iterate id="abc" name="editApproverList">
				<% if(Integer.parseInt(request.getAttribute("appcount").toString()) != 0){ %>
			<tr>
				<td width="15%">Approver <span>${abc.approverPriority }</span></td>
				<td style="width:20%"><html:text property="approverID" value="${abc.apprID }" styleId="aprID${abc.approverPriority }" onblur="SetApproverID (this)" styleClass="rounded" style="width:100%" onkeyup="searchUsers(this,'${abc.approverPriority }')" onmousedown="this.value='';"></html:text></td>
				<td id="iftd${abc.approverPriority }"></td>
				<td><img src="images/delete.png" id="${abc.apprID }" onclick="deleteThis(this,'${abc.requestType }')"/></td>
				</tr>
			<tr id="sU${abc.approverPriority }" style="display:none;">
			<td></td>
			<td id="sUTD${abc.approverPriority }"><iframe src="jsp/main/searchUsers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe></td>
			<td></td>
			<td></td>
			</tr>
			<tr style="height:10px;"><td colspan="4"></td></tr>
				<%} %>
				</logic:iterate>
			<tr></tr>
			<tr><td colspan="4" align="center"><html:button property="method" value="Add More" styleClass="rounded" style="width:75px; height:30px;" onclick="showLevels('Add More')"></html:button>&nbsp;<html:button property="method" value="Save" styleClass="rounded" style="width:75px; height:30px;" onclick="saveDetails('edit')"></html:button>&nbsp;<html:button property="method" value="Cancel" styleClass="rounded" style="width:75px; height:30px;" onclick="cancelAll()"></html:button></td></tr>
			</table>	
	</td></tr>	
			</logic:notEmpty>
	<tr>
	<td id="texttd" style="display:none;"><label style="font-weight:normal" id="applabel">Approver 1</label>
		<input type="text" value="Employee ID" onmousedown="this.value=''" onblur="setAppId(this)"/></td>
	<td id="if1" style="display:none;"><iframe src="jsp/admin/authentication/approverDetails.jsp"  id="fr1" scrolling="no" width="70%" frameborder="0" height="35"></iframe></td>
	<td style="display:none;">
		<input id="appIdValue" value=""/>
		<input id="selectInput" value=""/>
		<html:hidden property="employeeNumber" styleClass="text_field" style="width:240px;"  styleId="levelNo"/>
		<html:text styleId="reqType" property="requestType"><bean:write property="requestType" name="authenticationForm"/></html:text>
	</td>
	<td id="saveandcancel" style="display:none;"><html:button property="method" value="Save" styleClass="rounded" style="width:75px; height:30px;" onclick="saveDetails('save')"></html:button>&nbsp;<html:button property="method" value="Cancel" styleClass="rounded" style="width:75px; height:30px;" onclick="cancelAll()"></html:button></td>
	</tr>
		
	</table>
	<input style="visibility:hidden;" id="scnt" value="<bean:write property="startRequestCount"  name="authenticationForm"/>"/>
	<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endRequestCount"  name="authenticationForm"/>"/>
</html:form>


</body>
</html>
