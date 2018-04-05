<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

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
    
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function cancelperm(reqno)
{


var url="leave.do?method=selfcancelCompRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}

</script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">
<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="leaveForm" property="message">
				<font color="green" size="3"><b><bean:write name="leaveForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="leaveForm" property="message2">
				<font color="red" size="3"><b><bean:write name="leaveForm" property="message2" /></b></font>
				
			</logic:present>
		</div>


		
		<table class="bordered" width="90%">
			<tr>
				<th colspan="9" align="left"><center>My Approved OT Request</center></th>
	
			</tr>
		<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th >&nbsp;&nbsp;&nbsp;&nbsp;Date</th><th>Reason</th><th>Pending Approver</th><th>Last Approver</th><th>Status</th><th>View</th>
	
</tr>
<logic:notEmpty name="OT">
<logic:iterate id="c" name="OT">
<tr>

<td>${c.requestNumber}</td>
<td><bean:write name="c" property="submitDate"/></td>
<td ><bean:write name="c" property="startDate"/></td>

<td ><bean:write name="c" property="reason"/></td>
<td><bean:write name="c" property="papprover"/></td>
<td><bean:write name="c" property="lapprover"/></td>
<td><bean:write name="c" property="status"/></td>
<td>
<a  href="leave.do?method=selectOTCancelRequest&requstNo=<bean:write name="c" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a>
</td>

</tr>
		</logic:iterate>	
		</logic:notEmpty>
		<logic:notEmpty name="noOT">
		<tr>
<td colspan="8">
<font color="red" size="3"><center>No Records </center></font>
</td>
</tr>
		
		</logic:notEmpty>
	
	</table>
		
	
		</html:form>
</body>		

</html>