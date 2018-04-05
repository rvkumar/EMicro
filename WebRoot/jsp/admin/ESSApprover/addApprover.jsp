<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">
function onSave(){

var URL="essApprover.do?method=addApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}


function onModify(){

var URL="essApprover.do?method=modifyEmpApprovers";
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
var reqFieldName=fieldName

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
        if(reqFieldName=="parllelAppr11"){
        	document.getElementById("parllelAppr11sU").style.display ="";
        	document.getElementById("parllelAppr11sUTD").innerHTML=xmlhttp.responseText;
        	}
        if(reqFieldName=="parllelAppr12"){
        	document.getElementById("parllelAppr12sU").style.display ="";
        	document.getElementById("parllelAppr12sUTD").innerHTML=xmlhttp.responseText;
        	}
         if(reqFieldName=="approver2"){
        	document.getElementById("approver2sU").style.display ="";
        	document.getElementById("approver2sUTD").innerHTML=xmlhttp.responseText;
        	}
        if(reqFieldName=="parllelAppr21"){
        	document.getElementById("parllelAppr21sU").style.display ="";
        	document.getElementById("parllelAppr21sUTD").innerHTML=xmlhttp.responseText;
        	}
        if(reqFieldName=="parllelAppr22"){
        	document.getElementById("parllelAppr22sU").style.display ="";
        	document.getElementById("parllelAppr22sUTD").innerHTML=xmlhttp.responseText;
        	}
          if(reqFieldName=="approver3"){
        	document.getElementById("approver3sU").style.display ="";
        	document.getElementById("approver3sUTD").innerHTML=xmlhttp.responseText;
        	}
        if(reqFieldName=="parllelAppr31"){
        	document.getElementById("parllelAppr31sU").style.display ="";
        	document.getElementById("parllelAppr31sUTD").innerHTML=xmlhttp.responseText;
        	}
        if(reqFieldName=="parllelAppr32"){
        	document.getElementById("parllelAppr32sU").style.display ="";
        	document.getElementById("parllelAppr32sUTD").innerHTML=xmlhttp.responseText;
        	}				
        	
         
        			
        }
    }
     xmlhttp.open("POST","essApprover.do?method=searchForApproversleave&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
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
	if(reqFieldName=="parllelAppr11"){
		if(document.getElementById("parllelAppr11sU") != null){
		document.getElementById("parllelAppr11sU").style.display="none";
		}
	}
	if(reqFieldName=="parllelAppr12"){
		if(document.getElementById("parllelAppr12sU") != null){
		document.getElementById("parllelAppr12sU").style.display="none";
		}
	}
	if(reqFieldName=="approver2"){
		if(document.getElementById("approver2sU") != null){
		document.getElementById("approver2sU").style.display="none";
		}
	}
	if(reqFieldName=="parllelAppr21"){
		if(document.getElementById("parllelAppr21sU") != null){
		document.getElementById("parllelAppr21sU").style.display="none";
		}
	}
	if(reqFieldName=="parllelAppr22"){
		if(document.getElementById("parllelAppr22sU") != null){
		document.getElementById("parllelAppr22sU").style.display="none";
		}
	}
	if(reqFieldName=="approver3"){
		if(document.getElementById("approver3sU") != null){
		document.getElementById("approver3sU").style.display="none";
		}
	}
	if(reqFieldName=="parllelAppr31"){
		if(document.getElementById("parllelAppr31sU") != null){
		document.getElementById("parllelAppr31sU").style.display="none";
		}
	}
	if(reqFieldName=="parllelAppr32"){
		if(document.getElementById("parllelAppr32sU") != null){
		document.getElementById("parllelAppr32sU").style.display="none";
		}
	}
	
		
}
function goBack(){
var URL="essApprover.do?method=employyList";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}


function changetype1()
{


if(document.getElementById("seq").checked==true)
{
document.getElementById("par").checked=false;
}


}

function changetype2()
{



if(document.getElementById("par").checked==true)
{

document.getElementById("seq").checked=false;
}

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

.no
{
pointer-events:"none"; 
}

</style>

</head>

<body>
<html:form action="essApprover.do">
	<div align="center">
			<logic:present name="essApproverForm" property="message">
			<font color="red">
				<bean:write name="essApproverForm" property="message" />
			</font>
		</logic:present>
		<logic:present name="essApproverForm" property="message2">
			<font color="green">
				<bean:write name="essApproverForm" property="message2" />
			</font>
		</logic:present>
		</div>

<table class="bordered">
<tr><th colspan="2"><center>Add/Modify Approvers</center></th></tr>
<tr class="no"><td>Type <font color="red"><b>*</b></font></td>
	<td>
	<html:select property="essType" styleClass="rounded">
	    <html:option value="Leave">Leave</html:option>
	  <%--   <html:option value="OnDuty">OnDuty</html:option>
	    <html:option value="Permission">Permission</html:option> --%>
	</html:select></td>
</tr>
<tr><td>Employee Number <font color="red"><b>*</b></font></td>
	<td><html:text property="employeeNo" styleClass="rounded" styleId="employeeNo" onkeyup="searchEmployee('employeeNo')">
	<bean:write property="employeeNo" name="essApproverForm" /></html:text>
	<br/>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
</tr>

<tr><td colspan="2"><html:checkbox property="sequentialType" styleId="seq" onclick="changetype1()"></html:checkbox>&nbsp;&nbsp;Sequential&nbsp;&nbsp;&nbsp;
	<html:checkbox property="parallelType" styleId="par" onclick="changetype2()"></html:checkbox>&nbsp;&nbsp;Parallel</td>
</tr>
</table>
<br/>
<br/>
<br/>

<table class="sortable bordered" > 
<tr>
<th>Priority</th><th>Approver</th><th>Parallel Approver 1</th><th>Parallel Approver 2</th>
</tr>
<tr>
	<td><html:text property="priority1" value="1" readonly="true"></html:text></td>
	
	<td><html:text property="approver1" styleId="approver1" onkeyup="searchEmployee('approver1')">
	<bean:write property="approver1" name="essApproverForm" /></html:text>
	
	<div id="approver1sU" style="display:none;">
		<div id="approver1sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
	<td><html:text property="parllelAppr11" styleId="parllelAppr11" onkeyup="searchEmployee('parllelAppr11')">
	<bean:write property="parllelAppr11" name="essApproverForm" /></html:text>
	
	<div id="parllelAppr11sU" style="display:none;">
		<div id="parllelAppr11sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
	<td><html:text property="parllelAppr12" styleId="parllelAppr12" onkeyup="searchEmployee('parllelAppr12')">
	<bean:write property="parllelAppr12" name="essApproverForm" /></html:text>
	
	<div id="parllelAppr12sU" style="display:none;">
		<div id="parllelAppr12sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('1')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority2" value="2" readonly="true"></html:text></td>

	<td><html:text property="approver2"  styleId="approver2" onkeyup="searchEmployee('approver2')">
	<bean:write property="approver2" name="essApproverForm" /></html:text>
	
	<div id="approver2sU" style="display:none;">
		<div id="approver2sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
	<td><html:text property="parllelAppr21" styleId="parllelAppr21" onkeyup="searchEmployee('parllelAppr21')">
	<bean:write property="parllelAppr21" name="essApproverForm" /></html:text>
	
	<div id="parllelAppr21sU" style="display:none;">
		<div id="parllelAppr21sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
	<td><html:text property="parllelAppr22" styleId="parllelAppr22" onkeyup="searchEmployee('parllelAppr22')">
	<bean:write property="parllelAppr22" name="essApproverForm" /></html:text>
	
	<div id="parllelAppr22sU" style="display:none;">
		<div id="parllelAppr22sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>

	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('2')"/></a>
	</td>
</tr>
<tr>
	<td><html:text property="priority3" value="3" readonly="true"></html:text></td>
	
	<td><html:text property="approver3"  styleId="approver3" onkeyup="searchEmployee('approver3')">
	<bean:write property="approver3" name="essApproverForm" /></html:text>
	
	<div id="approver3sU" style="display:none;">
		<div id="approver3sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
	<td><html:text property="parllelAppr31" styleId="parllelAppr31" onkeyup="searchEmployee('parllelAppr31')">
	<bean:write property="parllelAppr31" name="essApproverForm" /></html:text>
	
	<div id="parllelAppr31sU" style="display:none;">
		<div id="parllelAppr31sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	</td>
	<td><html:text property="parllelAppr32" styleId="parllelAppr32" onkeyup="searchEmployee('parllelAppr32')">
	</html:text>
	
	<div id="parllelAppr32sU" style="display:none;">
		<div id="parllelAppr32sUTD" style="width:400px;">
		<iframe src="jsp/admin/ESSApprover/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('3')"/></a>
	</td>
</tr>
<!--<tr>
	<td><html:text property="priority4" value="4" readonly="true"></html:text></td>
	
	<td><html:text property="approver4"  ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('approver4')"/></a>
	</td>
	<td><html:text property="parllelAppr41" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr41')"/></a>
	</td>
	<td><html:text property="parllelAppr42" ></html:text>
	<a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId('parllelAppr42')"/></a>
	&nbsp;<a href="#" title="Clear Fields"><img  src="images/delete.png"  align="absmiddle" onclick="clearApprover1('4')"/></a>
	</td>
</tr>

--><tr>
<td colspan="5" align="center">
<logic:notEmpty name="saveButton">
<div align="center">
<html:button property="method" value="Save" onclick="onSave()" styleClass="rounded" style="width:100px;"/>
<html:reset value="Clear" styleClass="rounded" style="width:100px;"/>
<html:button property="method" value="Close" onclick="goBack()" styleClass="rounded" style="width:100px;"/>
</div>
</logic:notEmpty>
<logic:notEmpty name="modifyButton" >
<div align="center">
<html:button property="method" value="Modify" onclick="onModify()" styleClass="rounded" style="width:100px;"/>
<html:button property="method" value="Close" onclick="goBack()" styleClass="rounded" style="width:100px;"/>

</logic:notEmpty>
</td>
</tr>
</table>
</html:form>	
</body>
</html>