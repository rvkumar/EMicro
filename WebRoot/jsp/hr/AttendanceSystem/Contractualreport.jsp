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
	var cat=document.forms[0].catArray.value;
	var repgrp=document.forms[0].repgrpArray.value;

	var month=document.forms[0].month.value;
	var year=document.forms[0].year.value;





	if(loc=="")
	   {
	     alert("Please Select Plant");
	     document.forms[0].loc.focus();

	     return false;
	   }

	
	    
		if(cat=="")
		   {
		     alert("Please Select Category");
		     document.forms[0].cat.focus();
	
		     return false;
		   }

		   if(repgrp=="")
		   {
		     alert("Please Select Reporting Group");
		     document.forms[0].repgrp.focus();
	
		     return false;
		   }
	 
	document.forms[0].action="hrApprove.do?method=contractreportSearch";
	document.forms[0].submit();

	 var a = new Array;
	  a[0] = 1;
	  a[1] = 4;
	var x=window.showModalDialog("hrApprove.do?method=contractreportEXE&loc="+loc+"&month="+month+"&year="+year,a, "dialogwidth=800;dialogheight=600; center:yes;toolbar=no");
	
	/* ShowProgressAnimation();
	document.getElementById('mainContent').style.display = 'none'; */


}

function processexcel()
{


	
	var loc=document.forms[0].locationId.value;
	var cat=document.forms[0].catArray.value;
	var repgrp=document.forms[0].repgrpArray.value;

	var month=document.forms[0].month.value;
	var year=document.forms[0].year.value;



	if(loc=="")
	   {
	     alert("Please Select Plant");
	     document.forms[0].loc.focus();
	 
	     return false;
	   }

	
	    
		if(cat=="")
		   {
		     alert("Please Select Category");
		     document.forms[0].cat.focus();
	
		     return false;
		   }

		   if(repgrp=="")
		   {
		     alert("Please Select Reporting Group");
		     document.forms[0].repgrp.focus();

		     return false;
		   }
	 
	document.forms[0].action="hrApprove.do?method=exportcontractreport";
	document.forms[0].submit();



} 


function back()
{

document.forms[0].action="hrApprove.do?method=attendanceReport";
document.forms[0].submit();

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
<th colspan="5">Contractor Details</th>
</tr>


<tr>
<td>Location<font color="red">*</font></td>
<td colspan="3">

<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">

<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
</html:select>

</td>
</tr>



<tr>
<%-- <td>Department</td>
<td colspan="">
<html:select  property="deptArray" name="hrApprovalForm" multiple="true">
<html:option value="">--Select--</html:option>
<html:options name="hrApprovalForm"  property="deptList" labelProperty="deptLabelList"/>
</html:select>


</td> --%>
<td>Staff Category<font color="red">*</font></td>
<td colspan="3">
<html:select  property="catArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="categoryList" labelProperty="categoryLabelList"/>
</html:select>


</td>
</tr>
<tr>
<td>Reporting Group<font color="red">*</font></td>
<td colspan="3">
<html:select  property="repgrpArray" name="hrApprovalForm" multiple="true" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
</html:select>


</td>
<%-- <td>
Shift
</td>
<td colspan="3">
<html:select  property="shiftArray" name="hrApprovalForm" styleId="shift" multiple="true">
			<html:option value="">--Select--</html:option>
			<html:options name="hrApprovalForm"  property="shiftList" labelProperty="shiftLabelList"/>
</html:select>
</td> --%>
</tr>

<tr>
		<td>
		Month
		</td>
		<td colspan="">	
		<html:select  property="month" name="hrApprovalForm" styleClass="testselect1">
			<html:option value="1">January</html:option>
			<html:option value="2">February</html:option>
			<html:option value="3">March</html:option>
			<html:option value="4">April</html:option>
			<html:option value="5">May</html:option>
			<html:option value="6">June</html:option>
			<html:option value="7">July</html:option>
			<html:option value="8">August</html:option>
			<html:option value="9">September</html:option>
			<html:option value="10">October</html:option>
			<html:option value="11">November</html:option>
			<html:option value="12">December</html:option>
		</html:select>
		&nbsp;
		Year
		<html:select  property="year" name="hrApprovalForm" styleClass="testselect1">
			<html:options name="hrApprovalForm"  property="yearList"/>	
		</html:select>
		
		
		</td>
		
		
		
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
