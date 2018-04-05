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
<logic:notEmpty name="hrApprovalForm" property="message2">

<script language="javascript">
statusMessage('<bean:write name="hrApprovalForm" property="message2" />');
</script>
</logic:notEmpty>
<logic:notEmpty name="hrApprovalForm" property="message2">

<script language="javascript">
statusMessage('<bean:write name="hrApprovalForm" property="message2" />');
</script>
</logic:notEmpty>
</div>
<br/>
<a href="javascript:window.print()"><img src="jsp/hr/print.png" title="Print this Page"></a>
<br/><br/>

<br/>
<table  border="1">
<tr><th colspan="18"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower Performance Review for the Month of <bean:write property="month" name="hrApprovalForm"></bean:write>&nbsp;&nbsp;<bean:write property="year" name="hrApprovalForm"></bean:write>
</center></th></tr>
<tr><th rowspan="3">Department</th><th colspan="3">Approved Manpower</th><th colspan="3">Existing Manpower</th><th colspan="2">Additions of the Month</th><th colspan="4">Attritions of the Month</th><th colspan="4">Cumulative Attritions from April to <bean:write property="frommonth" name="hrApprovalForm"></bean:write>&nbsp;&nbsp;<bean:write property="year" name="hrApprovalForm"></bean:write></th><th rowspan="3">Total Manpower Available</th></tr>
<tr><th rowspan="2">ST</th><th rowspan="2">RW</th><th rowspan="2">CN</th><th rowspan="2">ST</th><th rowspan="2">RW</th><th rowspan="2">CN</th><th rowspan="2">ST</th><th rowspan="2">RW</th><th colspan="2">ST</th><th colspan="2">RW</th><th colspan="2">ST</th><th colspan="2">RW</th></tr>
<tr><th>No.</th><th>%</th><th>No.</th><th>%</th><th>No.</th><th>%</th><th>No.</th><th>%</th></tr>
<logic:iterate id="a" name="l1">
<tr>
<td>${a.department}</td>
<td>${a.staffapprstrength}</td>
<td>${a.techstaffapprstrength}</td>
<td>${a.contractapprstrength}</td>
<td>${a.staffavailstrength}</td>
<td>${a.techstaffavailstrength}</td>
<td>${a.contractavailstrength}</td>
<td>${a.staffadditioncount}</td>
<td>${a.techstaffadditioncount}</td>
<td>${a.staffattritioncount}</td>
<td>${a.staffattritioncountpercent}</td>
<td>${a.techstaffattritioncount}</td>
<td>${a.techstaffattritioncountpercent}</td>
<td>${a.cumstaffattritioncount}</td>
<td>${a.cumstaffattritioncountpercent}</td>
<td>${a.cumtechstaffattritioncount}</td>
<td>${a.cumtechstaffattritioncountpercent}</td>
<td>${a.total}</td>
</tr>
</logic:iterate>

<tr>
<td  style="background-color:#483D8B ;color: white"><center><b>Total</b></center>	</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffapprstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffapprstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncontractapprstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffavailstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffavailstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncontractavailstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffadditioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffadditioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumtechstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumtechstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntotpresent}</td>
</tr>
</table>
<table border="1">
<tr>
<td colspan="20" rowspan="1"><font color="red">NOTE:</font>1. Hyperlink the  Detailed Manpower Chart. Abbreviation: ST-Staff,RW- Regular WorkMen,CN-Contract<br/>
  2. Give Reason and Corrective Actions if the Cumulative Attrition exceeds 10%<br/>
						 3.10 Techincal satff to be replaced with CN.<br/>
						 4. Additional 3 CN Manpower given to Stores for completing Barcode activity for 2 months. </td>

</tr>
</table>


</html:form>
</body>
</html>
