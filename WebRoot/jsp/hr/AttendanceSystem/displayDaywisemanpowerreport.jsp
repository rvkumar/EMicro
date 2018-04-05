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

	
	var startDate=document.forms[0].fromDate.value;
	var endDate=document.forms[0].toDate.value;
	var loc=document.forms[0].locationId.value;
	var dept=document.forms[0].subdeptArray.value;
	var repgrp=document.forms[0].repgrpArray.value;




	if(loc=="")
	   {
	     alert("Please Select Plant");
	     document.forms[0].startDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }

	if(startDate=="")
	   {
	     alert("Please Select From Date");
	     document.forms[0].startDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }

	   if(endDate=="")
	   {
	     alert("Please Select To Date");
	     document.forms[0].endDate.focus();
	     document.forms[0].endDurationType.value="";
	     return false;
	   }
	    if(startDate!=""&&endDate!=""){
	   
	     var str1 = startDate;
	var str2 = endDate;
	var dt1  = parseInt(str1.substring(0,2),10); 
	var mon1 = parseInt(str1.substring(3,5),10); 
	var yr1  = parseInt(str1.substring(6,10),10); 
	var dt2  = parseInt(str2.substring(0,2),10); 
	var mon2 = parseInt(str2.substring(3,5),10); 
	var yr2  = parseInt(str2.substring(6,10),10); 
	var date1 = new Date(yr1, mon1-1, dt1); 
	var date2 = new Date(yr2, mon2-1, dt2); 


	if(date2 < date1) 
	{ 
	    alert("Please Select Valid Date Range");
	    document.forms[0].endDate.value="";
	     document.forms[0].endDate.focus();
	     return false;
	}

	}
	    
		if(dept=="")
		   {
		     alert("Please Select Department");
		     document.forms[0].dept.focus();
		     document.forms[0].endDurationType.value="";
		     return false;
		   }

		   if(repgrp=="")
		   {
		     alert("Please Select Reporting Group");
		     document.forms[0].repgrp.focus();
		     document.forms[0].endDurationType.value="";
		     return false;
		   }
	 
	document.forms[0].action="hrApprove.do?method=manpowerreportSearch";
	document.forms[0].submit();

	 var a = new Array;
	  a[0] = 1;
	  a[1] = 4;
	var x=window.showModalDialog("hrApprove.do?method=manpowerreportexe&loc="+loc+"&from="+startDate+"&to="+endDate,a, "dialogwidth=800;dialogheight=600; center:yes;toolbar=no");
	
	/* ShowProgressAnimation();
	document.getElementById('mainContent').style.display = 'none'; */


}

function processexcel()
{


document.forms[0].action="hrApprove.do?method=exportmusterReportSearch";
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

<a href="javascript:window.print()"><img src="jsp/admin/print.png" title="Print this Page"></a>
<br/>
<logic:notEmpty name="man">
<div>
<table border="1">
<tr> <td><center>
<img  src="images/logo.png" width='50' height='60'></center>
</td><th colspan="41"><font color="blue"><big><strong>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/>&nbsp;<bean:write name="hrApprovalForm" property="lock_id"/><br/>Manpower details from : <bean:write property="fromDate" name="hrApprovalForm"></bean:write><%-- &nbsp;To&nbsp;<bean:write property="toDate" name="hrApprovalForm"> --%><%-- </bean:write> --%>&nbsp;Shift&nbsp;<bean:write property="shift" name="hrApprovalForm"></bean:write></strong></big></font>
</th></tr>
<tr>
<th rowspan="2"><center>Sl.No</center></th ><%-- <th rowspan="2"><center>Location</center></th> --%><th rowspan="2"><center>Department</center></th><th colspan="5"><center>Staff</center></th><th colspan="5"><center>Technical Staff</center></th><th colspan="5"><center>Staff Apprentice</center></th><th colspan="4"><center>Total STAFF,TECHNICAL STAFF,STAFF APPRENTICE</center></th><th rowspan="2" ><center>Total Contract On Roll</center></th >
<logic:iterate id="cat" name="categorylist">
<%-- <th ><center><bean:write property="repgrp" name="cat"></bean:write></center></th> --%>
<th colspan="2"><center><bean:write property="repgrp" name="cat"></bean:write></center></th>
<%-- <th><center><bean:write property="repgrp" name="cat"></bean:write></center></th> --%>
</logic:iterate>



<th rowspan="2"><center>Total Contract Present</center></th ><th rowspan="2"><center>Total Contract Absent</center></th ><th rowspan="2"><center>Total Contract Salary</center></th><th colspan="4"><center>House Keeping</center></th><th rowspan="2"><center>Total  Present</center></th ><th rowspan="2"><center>Total  Absent</center></th ><th rowspan="2"><center>Total  Salary</center></th><%-- <th colspan="4"><center>STAFF,TECHNICAL STAFF,STAFF APPRENTICE</center></th> --%>
</tr>
<tr>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>On Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<%-- <th><center>On Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th> --%>
<logic:iterate id="cat" name="categorylist">
<th rowspan="1"><center>Present</center></th >
<th rowspan="1"><center>Absent</center></th >
</logic:iterate>
<th><center>On Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<%-- <th><center>On Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th> --%>
<%-- <th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
 --%></tr>
<% int i=0;%>

<logic:iterate id="a" name="man">


<logic:equal value="Sub" name="a" property="locationId">

<tr>

<td style="background-color: #A9A9A9 ;color: black" colspan="2"><center>TOTAL-<bean:write name="a" property="department" /></center></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffsalary" /></td>


<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffsalary" /></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffsalary" /></td>
<td style="background-color: #A9A9A9 ;color: black">${a.staffavailstrength+a.techstaffavailstrength+a.apprenstaffavailstrength} </td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threepresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threeabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threesalary" /></td>


<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="contractavailstrength" /></td>

<logic:notEmpty name="skilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="skilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="skilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="skilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="unskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="unskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="semiskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="semiskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="securitypresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="securityabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="projectspresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="projectsabsent" /></td>
</logic:notEmpty>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totcontractpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totcontractabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totcontractSalary" /></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="houseavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="housepresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="houseabsent" /></td>
<%-- <td style="background-color: #A9A9A9 ;color: black">${a.houseapprstrength-a.housepresent}</td> --%>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="housesalary" /></td>


<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totsalary" /></td>

<%-- <td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threeapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threepresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threeabsent" /></td>
 --%><%-- <td style="background-color: #A9A9A9 ;color: black">${a.threeapprstrength-a.threepresent}</td> --%>
<%-- <td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threesalary" /></td> --%>

</tr>
</logic:equal>

<logic:notEqual value="Sub" name="a" property="locationId">
<%i++; %>
<tr>
<td><%=i %></td>
<%-- <td><bean:write name="a" property="locationId" /></td> --%>
<td><bean:write name="a" property="department" /></td>

<td><bean:write name="a" property="staffapprstrength" /></td>
<td><bean:write name="a" property="staffavailstrength" /></td>
<td><bean:write name="a" property="staffpresent" /></td>
<td><bean:write name="a" property="staffabsent" /></td>
<td><bean:write name="a" property="staffsalary" /></td>


<td><bean:write name="a" property="techstaffapprstrength" /></td>
<td><bean:write name="a" property="techstaffavailstrength" /></td>
<td><bean:write name="a" property="techstaffpresent" /></td>
<td><bean:write name="a" property="techstaffabsent" /></td>
<td><bean:write name="a" property="techstaffsalary" /></td>

<td><bean:write name="a" property="apprenstaffapprstrength" /></td>
<td><bean:write name="a" property="apprenstaffavailstrength" /></td>
<td><bean:write name="a" property="apprenstaffpresent" /></td>
<td><bean:write name="a" property="apprenstaffabsent" /></td>
<td><bean:write name="a" property="apprenstaffsalary" /></td>

<td>${a.staffavailstrength+a.apprenstaffavailstrength+a.techstaffavailstrength}</td>
<td><bean:write name="a" property="threepresent" /></td>
<td><bean:write name="a" property="threeabsent" /></td>
<td><bean:write name="a" property="threesalary" /></td>


<td><bean:write name="a" property="contractavailstrength" /></td>

<logic:notEmpty name="skilled">
<td><bean:write name="a" property="skilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="skilled">
<td><bean:write name="a" property="skilledabsent" /></td>
</logic:notEmpty>

<logic:notEmpty name="unskilled">
<td><bean:write name="a" property="unskilledpresent" /></td>
</logic:notEmpty>

<logic:notEmpty name="unskilled">
<td><bean:write name="a" property="unskilledabsent" /></td>
</logic:notEmpty>

<logic:notEmpty name="semiskilled">
<td><bean:write name="a" property="semiskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td><bean:write name="a" property="semiskilledabsent" /></td>
</logic:notEmpty>

<logic:notEmpty name="g4s">
<td><bean:write name="a" property="securitypresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td><bean:write name="a" property="securityabsent" /></td>
</logic:notEmpty>

<logic:notEmpty name="project">
<td><bean:write name="a" property="projectspresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td><bean:write name="a" property="projectsabsent" /></td>
</logic:notEmpty>



<td><bean:write name="a" property="totcontractpresent" /></td>
<td><bean:write name="a" property="totcontractabsent" /></td>
<td><bean:write name="a" property="totcontractSalary" /></td>

<td><bean:write name="a" property="houseavailstrength" /></td>
<td><bean:write name="a" property="housepresent" /></td>
<td><bean:write name="a" property="houseabsent" /></td>
<%-- <td>${a.houseapprstrength-a.housepresent}</td> --%>
<td><bean:write name="a" property="housesalary" /></td>


<td><bean:write name="a" property="totpresent" /></td>
<td><bean:write name="a" property="totabsent" /></td>
<td><bean:write name="a" property="totsalary" /></td>

<%-- <td><bean:write name="a" property="threeapprstrength" /></td>
<td><bean:write name="a" property="threepresent" /></td>
<td><bean:write name="a" property="threeabsent" /></td>
<td>${a.threeapprstrength-a.housepresent}</td>
<td><bean:write name="a" property="threesalary" /></td>
 --%>
</tr>
</logic:notEqual>

</logic:iterate>
<tr ><td colspan="2" style="background-color:#483D8B ;color: white"><strong><b><center>Grand Total:</center></b></strong></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffabsent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffsalary" /></td>


<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffabsent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffsalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffabsent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffsalary" /></td>

<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffavailstrength+hrApprovalForm.grntechstaffavailstrength+hrApprovalForm.grnapprenstaffavailstrength}</td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreepresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreeabsent" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreesalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grncontractavailstrength" /></td>

<logic:notEmpty name="skilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="skilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnunskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnunskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsemiskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsemiskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsecuritypresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsecurityabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnprojectspresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnprojectsabsent" /></td>
</logic:notEmpty>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotcontractpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotcontractabsent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotcontractSalary" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhouseavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhousepresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhouseabsent" /></td>
<%-- <td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnhouseapprstrength-hrApprovalForm.grnhousepresent}</td> --%>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhousesalary" /></td>


<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotabsent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotsalary" /></td>

<%-- <td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreeapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreepresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreeabsent" /></td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnthreeapprstrength-hrApprovalForm.grnthreepresent}</td> 
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreesalary" /></td>
 --%>
</tr>


</table>



</div>
</logic:notEmpty>

<logic:notEmpty name="nolist">

<div>
<table class="bordered">
<tr><th colspan="52"><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower details from : <bean:write property="fromDate" name="hrApprovalForm"></bean:write>&nbsp;To&nbsp;<bean:write property="toDate" name="hrApprovalForm"></bean:write>&nbsp;&nbsp;&nbsp;Shift :-&nbsp;<bean:write property="shift" name="hrApprovalForm"></bean:write>
</th></tr>
<tr>
<th rowspan="2"><center>Sl.No</center></th ><th rowspan="2"><center>Location</center></th><th rowspan="2"><center>Department</center></th><th colspan="5"><center>Staff</center></th><th colspan="5"><center>Technical Staff</center></th><th colspan="5"><center>Staff Apprentice</center></th><th rowspan="2"><center>Contract On Roll</center></th >
<th rowspan="1" colspan="2"><center>CKP - SKILLED</center></th  >
<th rowspan="1" colspan="2"><center>CKP - UNSKILLED</center></th  >
<th rowspan="1" colspan="2"><center>CKP - SEMISKILLED</center></th  >
<th rowspan="1" colspan="2"><center>SECURITY</center></th  >
<th rowspan="1" colspan="2"><center>Projects</center></th  >
<th rowspan="2"><center>Total Contract Present</center></th ><th rowspan="2"><center>Total Contract Absent</center></th ><th rowspan="2"><center>Total Contract Salary</center></th><th colspan="5"><center>House Keeping</center></th><th rowspan="2"><center>Total  Present</center></th ><th rowspan="2"><center>Total  Salary</center></th><th colspan="4"><center>STAFF,TECHNICAL STAFF,STAFF APPRENTICE</center></th>
</tr>
<tr>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
</tr>
<tr>
<td colspan="41"><center>Currently details are not available to display</center></td>
</tr>
</table></div>
</logic:notEmpty>


<logic:notEmpty name="depman">
<table class="bordered">
<tr><th colspan="41"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/>:<bean:write name="hrApprovalForm" property="paygrp"/><br/>Manpower details on : <bean:write property="fromDate" name="hrApprovalForm"></bean:write>
</th></tr>
<tr><th rowspan="2"><center>Sl.No<center></th><th rowspan="2"><center>Department<center></th><th colspan="3"><center>Factory Staff<center></th><th colspan="3"><center>OP.STAFF/WORKERS<center></th><th colspan="3"><center>Total<center></th></tr>
<tr><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th></tr>
<%int i=0;%>
<logic:iterate id="a" name="depman">
<%i++; %>
<logic:equal value="" name="a" property="department">
<tr><td colspan="2" style="background-color:#483D8B ;color: white"><center>Total</center></td><td style="background-color:#483D8B ;color: white">${a.staffavailstrength }</td>
<td style="background-color:#483D8B ;color: white">${a.staffpresent }</td>
<td style="background-color:#483D8B ;color: white">${a.staffabsent}</td>
<td style="background-color:#483D8B ;color: white">${a.staffpresent }</td>
<td style="background-color:#483D8B ;color: white">${a.techstaffavailstrength }</td><td style="background-color:#483D8B ;color: white">${a.techstaffpresent }</td><td style="background-color:#483D8B ;color: white">${a.techstaffavailstrength - a.techstaffpresent}</td><td style="background-color:#483D8B ;color: white">${a.totalRecords }</td><td style="background-color:#483D8B ;color: white">${a.totpresent }</td><td style="background-color:#483D8B ;color: white">${a.totalRecords -a.totpresent}</td>
</tr>
</logic:equal>
<logic:notEqual value="" name="a" property="department">
<tr><td><%=i %></td><td>${a.department }</td><td>${a.staffavailstrength }</td><td>${a.staffpresent }</td><td>${a.staffabsent}</td>
<td>${a.techstaffavailstrength }</td><td>${a.techstaffpresent }</td><td>${a.techstaffabsent }</td><td>${a.totalRecords }</td><td>${a.totpresent }</td><td>${a.totalRecords  - a.totpresent }</td>
</tr>
</logic:notEqual>

</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="nodepman">
<table class="bordered">
<tr><th colspan="31"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower details on : <bean:write property="fromDate" name="hrApprovalForm"></bean:write>
</th></tr>
<tr><th rowspan="2"><center>Sl.No<center></th><th rowspan="2"><center>Department<center></th><th colspan="3"><center>Factory Staff<center></th><th colspan="3"><center>OP.STAFF/WORKERS<center></th><th colspan="3"><center>Total<center></th></tr>
<tr><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th></tr>

<tr>
<td colspan="31"><center>Currently details are not available to display</center></td>
</tr>
</table>
</logic:notEmpty>



</html:form>
</body>
</html>
