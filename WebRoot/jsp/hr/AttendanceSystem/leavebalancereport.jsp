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
<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
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


<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">

$(function() {
$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
$('#inlineDatepicker').datepick({onSelect: showDate});


});

$(function() {
$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
alert('The date chosen is ' + date);
}


function statusMessage(message){
alert(message);
}



function process()
{

	
	var loc=document.forms[0].locationId.value;
	var dep=document.forms[0].deptArray.value;
	var subdep=document.forms[0].subdeptArray.value;
	var repgrp=document.forms[0].repgrpArray.value;
	var summbrkup=document.forms[0].summbrkup.value;	
	var wrk=document.forms[0].workLocId.value;
	var year=document.forms[0].year.value;
	var pernr=document.forms[0].employeeno.value;



	if(loc=="")
	   {
	     alert("Please Select Plant");
	     document.forms[0].loc.focus();

	     return false;
	   }


	if(summbrkup!="Final Settlement")
    {
		if(dep=="")
		   {
		     alert("Please Select Department");
		     document.forms[0].dep.focus();
	
		     return false;
		   }

		
		if(subdep!="")
		{


		if(repgrp=="")
		{
		alert("Please Select Reporting Group");
		return false;
		}

		}
		 
		if(repgrp!="")
		{


		if(subdep=="")
		{
		alert("Please Select SubDepartment");
		return false;
		}
		}
 }
	
	else
		{

		if(pernr=="")
		{
		alert("Please Select Employeenumber");
		return false;
		}
		
		}
	document.forms[0].action="hrApprove.do?method=leavebalreportSearch";
	document.forms[0].submit();

	 var a = new Array;
	  a[0] = 1;
	  a[1] = 4;
	var x=window.showModalDialog("hrApprove.do?method=leavebalreportEXE&loc="+loc+"&summbrkup="+summbrkup+"&wrk="+wrk+"&year="+year,a, "dialogwidth=800;dialogheight=600; center:yes;toolbar=no");
	
	/* ShowProgressAnimation();
	document.getElementById('mainContent').style.display = 'none'; */


}

function processexcel()
{


	
	var loc=document.forms[0].locationId.value;
	var dep=document.forms[0].deptArray.value;
	var subdep=document.forms[0].subdeptArray.value;
	var repgrp=document.forms[0].repgrpArray.value;
	var summbrkup=document.forms[0].summbrkup.value;
	var wrk=document.forms[0].workLocId.value;
	var year=document.forms[0].year.value;
	var pernr=document.forms[0].employeeno.value;

	if(loc=="")
	   {
	     alert("Please Select Plant");
	     document.forms[0].loc.focus();
	 
	     return false;
	   }

	if(summbrkup!="Final Settlement")
	    {
	if(dep=="")
	   {
	     alert("Please Select Department");
	     document.forms[0].dep.focus();

	     return false;
	   }
	
	
	if(subdep!="")
	{


	if(repgrp=="")
	{
	alert("Please Select Reporting Group");
	return false;
	}

	}
	 
	if(repgrp!="")
	{


	if(subdep=="")
	{
	alert("Please Select SubDepartment");
	return false;
	}
	}
 
	    }
	
	else
		{

		if(pernr=="")
		{
		alert("Please Select Employeenumber");
		return false;
		}
		
		}
	document.forms[0].action="hrApprove.do?method=exportLeavebalreport&loc="+loc+"&summbrkup="+summbrkup+"&wrk="+wrk+"&year="+year;
	document.forms[0].submit();



} 


function back()
{

document.forms[0].action="hrApprove.do?method=attendanceReport";
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
	        if(reqFieldName=="employeeno"){
	        	document.getElementById("sU").style.display ="";
	        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
	        	}
	       
	        	       			
	        }
	    }
	    xmlhttp.open("POST","hrApprove.do?method=searchForManualApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
	    xmlhttp.send();
	}


	function selectUser(input,reqFieldName){


	var res = input.split("-");
		document.getElementById(reqFieldName).value=res[1];

		disableSearch(reqFieldName);
	}

	function disableSearch(reqFieldName){
	  if(reqFieldName=="employeeno"){
			if(document.getElementById("sU") != null){
			document.getElementById("sU").style.display="none";
			}
		}
		}

</script>
</head>

<body>


<html:form action="hrApprove" enctype="multipart/form-data">

<div align="center">
<logic:notEmpty name="hrApprovalForm" property="message">

<script language="javascript">
statusMessage('<bean:write name="hrApprovalForm" property="message" />');
</script>
</logic:notEmpty>
<logic:notEmpty name="hrApprovalForm" property="message2">

<script language="javascript">
statusMessage('<bean:write name="hrApprovalForm" property="message" />');
</script>
</logic:notEmpty>
</div>

 <div style="width: 90%" >

 
<table class="bordered" >

<tr>
<th colspan="5">Leave Balance Details</th>
</tr>


<tr>
<td>Location<font color="red">*</font></td>
<td colspan="2">

<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1" >

<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
</html:select>

</td>

<td>Department<font color="red">*</font></td>
<td colspan="2">
<html:select  property="deptArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="deptList" labelProperty="deptLabelList"/>
</html:select>


</td>



</tr>
<tr>

<td>Sub Department</td>
<td colspan="2">
<html:select  property="subdeptArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="subdeptList" labelProperty="subdeptLabelList"/>
</html:select>


</td>

	
<td>Reporting Group</td>
<td colspan="2">
<html:select  property="repgrpArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
</html:select>


</td>

</tr>

<tr>
		<td>Work Location</td>
		<td colspan="2">
		

		
		<html:select  property="workLocId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:option value="">--Select--</html:option>
			<html:options name="hrApprovalForm"  property="workList" labelProperty="workLabelList"/>
			
		</html:select>
		
		</td>
		<td>Year</td>
		<td colspan="2">
<html:select  property="year" name="hrApprovalForm" styleClass="testselect1">
<html:options name="hrApprovalForm"  property="yearList"/>
</html:select>
</td>
		</tr>
		
		<tr>
	

<td >Employee No <font color="red" style="visibility:hidden;" id="emp">*</font></td>

	<td colspan="4"><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno"  style="width: 84px; " >
	<bean:write property="employeeno" name="hrApprovalForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div>
				
		&nbsp;&nbsp;<%-- <html:button property="method" value="Continue" onclick="onSave()" styleClass="rounded" style="width: 81px;"/> --%>
					</td>
</tr>
		
		<tr>
		<th colspan="5">View
		</th>
		</tr>
		<tr>
		<td colspan="6">  <input type="radio" name="summbrkup" id="acb1" value="Summary"  checked="checked"<logic:equal name="hrApprovalForm" property="summbrkup" value="Summary">checked</logic:equal>>&nbsp;Summary
		
		&nbsp;  <input type="radio" name="summbrkup" id="acb1" value="Detailed"  <logic:equal name="hrApprovalForm" property="summbrkup" value="Detailed">checked</logic:equal>>&nbsp;Detailed
		&nbsp;  <input type="radio" name="summbrkup" id="acb1" value="Final Settlement"  <logic:equal name="hrApprovalForm" property="summbrkup" value="Final Settlement">checked</logic:equal>>&nbsp;Leave Report</td>
		</tr>
</table>
<br/>
<center>

<div>

<html:button property="method" value="Execute" onclick="process()" styleClass="rounded"/>&nbsp;<html:button property="method" value="Export To Excel" onclick="processexcel()" styleClass="rounded"/>
<html:button property="method" value="Close" onclick="back()" styleClass="rounded"/>  &nbsp; 



</div>
</center>
</div>
<br/>

</html:form>
</body>
</html>
