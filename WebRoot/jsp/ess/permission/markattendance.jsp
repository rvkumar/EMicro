
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      
      <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy',onSelect: res});
	
});


</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry();
});


$(function () {


	$('#timeTo').timeEntry();
});

$('.timeRange').timeEntry({beforeShow: customRange}); 
 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null),  
        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
}

</script>

	
	<script type="text/javascript">
	function popupCalender(param)
	{
	var cal = new Zapatec.Calendar.setup({
	inputField : param, // id of the input field
	singleClick : true, // require two clicks to submit
	ifFormat : "%d/%m/%Y ", // format of the input field
	showsTime : false, // show time as well as date
	button : "button2" // trigger button
	});
	}
	
	function applyPermission(param)
{


    	var k =0;
var l=document.getElementsByName("selectedRequestNo").length;
for(var i=1; i <=l; i++){
	if(document.getElementById("a"+i).checked==true)
	{
	   
	   k=1;
	}
}
if(k==0)
{

alert("Select Atleast One Checkbox");
return false;

}


//validationb to check one check swipe
for(var i=1; i <=l; i++){
	if(document.getElementById("a"+i).checked==true )
	{

	if(document.getElementById("iNSTATUS"+i).value=="AA" && document.getElementById("oUTSTATUS"+i).value!="AA")
	{
	if(document.getElementById("ci"+i).checked==false)
	  {

	  alert("Select Swipe Type")
	  document.getElementById("ci"+i).focus();
	  return false;
	  
	  }
	}
	
	if(document.getElementById("oUTSTATUS"+i).value=="AA" && document.getElementById("iNSTATUS"+i).value!="AA")
	{
	if(document.getElementById("co"+i).checked==false)
	  {

	  alert("Select Swipe Type")
	  document.getElementById("co"+i).focus();
	  return false;
	  
	  }
	}
	if(document.getElementById("iNSTATUS"+i).value=="AA" && document.getElementById("oUTSTATUS"+i).value=="AA")
	{
	
		if(document.getElementById("ci"+i).checked==false && document.getElementById("co"+i).checked==false)
		  {

		  alert("Select Swipe Type")
		  document.getElementById("ci"+i).focus();
		  return false;
		  
		  }
	}

	
	}
}
              
              
              	if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Valid reason");
	      document.forms[0].reason.focus();
	      return false;
	    }
	
		var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	
	  document.forms[0].action="permission.do?method=saveMarkPermission";
	  document.forms[0].submit();



}

function closeLeave()
{
	document.forms[0].action="permission.do?method=displayPermissions";
	document.forms[0].submit();
}
	
	function chngeview()
{

if(document.forms[0].type.value=="Early" ||document.forms[0].type.value=="Personal" )
{

document.getElementById("totime").style.display="";


}
else
{

document.getElementById("totime").style.display="none";

}

}


function res()
{


 
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
    document.getElementById("resid").innerHTML=xmlhttp.responseText;
    }
  }

xmlhttp.open("POST","permission.do?method=displayMarkjax",true);
xmlhttp.send();
}


	function checkAll()
    	{
    
    	    var t =document.getElementsByName("selectedRequestNo").length;
    	  
    		for(i=1; i <= t; i++){
    			if(document.forms[0].checkProp.checked==true)
    			   document.getElementById("a"+i).checked = true ;
    			else
    				document.getElementById("a"+i).checked = false ;
    		}
    	}

</script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
  </head>
  
  <body onload="res()">
<% 
  				UserInfo user=(UserInfo)session.getAttribute("user");
  			%>
 
<html:form action="permission">
	<div id="masterdiv" class="">
			<div align="center">
				<logic:present name="permissionForm" property="message2">
					<font color="red" size="3"><b><bean:write name="permissionForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="permissionForm" property="message">
					<font color="Green" size="3"><b><bean:write name="permissionForm" property="message" /></b></font>
				</logic:present>
			</div>
			
			
			
	<table class="bordered content" width="80%">
		 <tr><th  colspan="8" align="center">
					Mark Attendance Form ( Note: Max of ${permissionForm.endRecord } requests can be raised for a month )</th>
  						</tr>
</table><div id="resid"></div>
									<table class="bordered content" width="80%">
			 <tr><th  colspan="4" align="center">
					 Enter Your Reason Here<font color="red" size="2">*</font>  </th>
  						<tr>
				</tr>
						<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="100" rows="3" onkeypress="return isNumber(event)"  onkeyup="this.value = this.value.replace(/'/g,'`')" ></html:textarea>
						
							</td>
						</tr>
						<tr>
							<td colspan="4" align="center">
						<html:button property="method"  styleClass="rounded" value="Submit" onclick="applyPermission('Applied');" style="align:right;width:100px;"/> &nbsp;
						
					
						</td>
								</td>
				</tr></table>
				<br/>
		
					
							 <logic:notEmpty name="appList">
		 <div align="left" class="bordered ">
			<table width="100%"   class="sortable">
			<tr>
				<th style="text-align:left;"><b>Type</b></th>
				<th style="text-align:left;"><b>EmpNo</b></th>
				<th style="text-align:left;"><b>Emp Name</b></th>	
				<th style="text-align:left;"><b>Designation</b></th>
			</tr>
			<logic:iterate id="abc" name="appList">
			<tr>
			
		<td>${abc.appType}</td>
			<td>${abc.approverID}</td>
			<td>${abc.approverName}</td>
			<td>${abc.appDesig}</td>
			</tr>
			</logic:iterate>
			</table>
		</div>
		</logic:notEmpty>
	
	
	
	
	
	</table>







</div>
</html:form>
  </body>
</html>
