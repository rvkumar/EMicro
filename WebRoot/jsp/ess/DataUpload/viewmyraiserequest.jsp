<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
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
		var toD = new Date();
		if(param == "endDate"){
			var sD = document.forms[0].startDate.value;
			var sDate = sD.split("/");
			toD = new Date(parseInt(sDate[2]),parseInt(sDate[1]),parseInt(sDate[0]));
		}
		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		
		});
	}
</script>

<script type="text/javascript">




function submitrequest()
{
	
	if(document.forms[0].documentFile.value=="")
	{
		alert("PLease upload file");
		return false;
	}
	
	if(document.forms[0].searchText.value=="")
	{
		alert("PLease upload file");
		return false;
	}
	
	
	


	document.forms[0].action="vc.do?method=submitMyraiserequest";
	document.forms[0].submit();

}



</script>
</head>
<body >

	<html:form action="vc" enctype="multipart/form-data">
	
	
	<html:hidden property="id" name="vcForm" />
	<html:hidden property="sfilename" name="vcForm" />
	<html:hidden property="spath" name="vcForm" />
	
	<table class="bordered">
	
	<tr>
			<th style="text-align:left;"><b>Req No</b></th>
			<th style="text-align:left;"><b>Assigned BY</b></th>
			<th style="text-align:left;"><b>Description</b></th>
			
			<th style="text-align:left;"><b>File Template</b></th>
			<th style="text-align:left;"><b>Assigned Date</b></th>
										
					</tr>
					<tr>
					
					<td>
				<bean:write name="vcForm" property="reqNo"/>
				<html:hidden name="vcForm" property="reqNo"/>
					</td>
				<td>
				<bean:write name="vcForm" property="empName"/>
					</td>
					<td>
				<bean:write name="vcForm" property="desc"/>
					</td>
										<td>
				<a href="${vcForm.path}"  target="_blank">${vcForm.filename}</a>						
				
				
				</td>
				<td>
				<bean:write name="vcForm" property="toDate"/>
				</td>
				</tr>
	
	
	<tr>
	<td>Comments</td>
	<td colspan="4">
	
	<html:textarea property="searchText" name="vcForm">${vcForm.searchText}
	</html:textarea>
	</td>
	
	</tr>
	<tr >
	<td>Attachment: </td>
					<td colspan="4">
					<logic:notEqual property="status" name="vcForm" value="Approved">
					<html:file property="documentFile" name="vcForm"/> 
					</logic:notEqual>
					<logic:notEmpty property="sfilename" name="vcForm">
					&nbsp;&nbsp;&nbsp;&nbsp;
					<b>Old file:</b><a href="${vcForm.spath}"  target="_blank">${vcForm.sfilename}</a>
					</logic:notEmpty>
					</td>
	</tr>
	
	<tr>
	<td>
	
	
	</td>
	<td colspan="8"><center> 
	<logic:notEqual property="status" name="vcForm" value="Approved">
	<html:button property="method" value="Submit" onclick="submitrequest()" styleClass="rounded"/> &nbsp;&nbsp;
	</logic:notEqual>
	<input type="reset" value="Close" class="rounded" onclick="history.back(-1)"/></center>
	 </td>
	</tr>
	</table>
	<div>&nbsp;</div>
	 &nbsp;&nbsp;&nbsp;
	 <!-- <input type="button" value="VC Rooms List" class="rounded" onclick="roomsList()"/> -->
	<div>&nbsp;</div>
	

	
	
	</html:form>
	
</body>
	
</html>	