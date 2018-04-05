

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
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
   <script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
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
</script>

<!-- captcha -->
<link rel="stylesheet" href="css/captcha/jquery.realperson.css"/>
<style>
label { display: inline-block; width: 20%; }
.realperson-challenge { display: inline-block }
</style>

<script src="js/captcha/jquery.plugin.js"></script>
<script src="js/captcha/jquery.realperson.js"></script>
<script>
$(function() {
	$('#defaultReal').realperson({dot: 'o', dots: 
    [['   *   ', '  ***  ', '  ***  ', ' **  * ', ' ***** ', '**    *', '**    *'], 
    ['****** ', '**    *', '**    *', '****** ', '**    *', '**    *', '****** '], 
    [' ***** ', '**    *', '**     ', '**     ', '**     ', '**    *', ' ***** '], 
    ['****** ', '**    *', '**    *', '**    *', '**    *', '**    *', '****** '], 
    ['*******', '**     ', '**     ', '****   ', '**     ', '**     ', '*******'], 
    ['*******', '**     ', '**     ', '****   ', '**     ', '**     ', '**     '], 
    [' ***** ', '**    *', '**     ', '**     ', '**  ***', '**    *', ' ***** '], 
    ['**    *', '**    *', '**    *', '*******', '**    *', '**    *', '**    *'], 
    ['*******', '  **   ', '  **   ', '  **   ', '  **   ', '  **   ', '*******'], 
    ['     **', '     **', '     **', '     **', '     **', '*    **', ' ***** '], 
    ['**    *', '**  ** ', '****   ', '**     ', '****   ', '**  ** ', '**    *'], 
    ['**     ', '**     ', '**     ', '**     ', '**     ', '**     ', '*******'], 
    ['*     *', '**   **', '*** * *', '** *  *', '**    *', '**    *', '**    *'], 
    ['*     *', '**    *', '***   *', '** *  *', '**  * *', '**   **', '**    *'], 
    [' ***** ', '**    *', '**    *', '**    *', '**    *', '**    *', ' ***** '], 
    ['****** ', '**    *', '**    *', '****** ', '**     ', '**     ', '**     '], 
    [' ***** ', '**    *', '**    *', '**    *', '**  * *', '**   * ', ' **** *'], 
    ['****** ', '**    *', '**    *', '****** ', '**  *  ', '**   * ', '**    *'], 
    [' ***** ', '**    *', '**     ', ' ***** ', '     **', '*    **', ' ***** '], 
    ['*******', '  **   ', '  **   ', '  **   ', '  **   ', '  **   ', '  **   '], 
    ['**    *', '**    *', '**    *', '**    *', '**    *', '**    *', ' ***** '], 
    ['**    *', '**    *', ' **  * ', ' **  * ', '  ***  ', '  ***  ', '   *   '], 
    ['**    *', '**    *', '**    *', '** *  *', '*** * *', '**   **', '*     *'], 
    ['**    *', ' **  * ', '  ***  ', '   *   ', '  ***  ', ' **  * ', '**    *'], 
    ['**    *', ' **  * ', '  ***  ', '  **   ', '  **   ', '  **   ', '  **   '], 
    ['*******', '    ** ', '   **  ', '  **   ', ' **    ', '**     ', '*******'], 
    ['  ***  ', ' *   * ', '*   * *', '*  *  *', '* *   *', ' *   * ', '  ***  '], 
    ['   *   ', '  **   ', ' * *   ', '   *   ', '   *   ', '   *   ', '*******'], 
    [' ***** ', '*     *', '      *', '     * ', '   **  ', ' **    ', '*******'], 
    [' ***** ', '*     *', '      *', '    ** ', '      *', '*     *', ' ***** '], 
    ['    *  ', '   **  ', '  * *  ', ' *  *  ', '*******', '    *  ', '    *  '], 
    ['*******', '*      ', '****** ', '      *', '      *', '*     *', ' ***** '], 
    ['  **** ', ' *     ', '*      ', '****** ', '*     *', '*     *', ' ***** '], 
    ['*******', '     * ', '    *  ', '   *   ', '  *    ', ' *     ', '*      '], 
    [' ***** ', '*     *', '*     *', ' ***** ', '*     *', '*     *', ' ***** '], 
    [' ***** ', '*     *', '*     *', ' ******', '      *', '     * ', ' ****  ']],chars: $.realperson.alphanumeric});
});
</script>

<!-- capthca -->
<title>Home Page</title>

<script language="javascript">
	function changeStatus(elem){

  
	var elemValue = elem.value;
	
	if(elemValue=="Reject"||elemValue=="Cancel")
	{
		if(document.forms[0].comments.value==""){
		alert("Please Add Some Comments");
		 document.forms[0].comments.focus();
		 return false;
		}
	}
	var reqId = document.getElementById("reqId").value;;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=statusChangeHRQuery&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;

	document.forms[0].action=url;
	document.forms[0].submit();
}
	
	function statusMessage(message){

alert(message);
}

function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


}
</script>
</head>

<body >
<html:form action="approvals"  enctype="multipart/form-data" method="post">
<div align="center">
				<logic:notEmpty name="approvalsForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="approvalsForm" property="message" />');
					</script>
				</logic:notEmpty>
				
			</div>


	
		<table class="bordered">
		<tr>
<th colspan="4"><center>HR Query Form</center></th>
</tr>
<tr><th colspan="4">Requester Details</th></tr>
						<tr><td>Employee Number</td><td><bean:write name="approvalsForm" property="employeeno" /></td><td>Employee Name</td><td><bean:write name="approvalsForm" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="approvalsForm" property="department" /></td><td>Designation</td><td><bean:write name="approvalsForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><bean:write name="approvalsForm" property="dateofBirth" /></td><td>Location</td><td colspan=""><bean:write name="approvalsForm" property="locationId" /></td></tr>
	
<tr><th colspan="4"><big>Other Details</big></th></tr>

<tr><td>Subject<font color="red">*</font></td><td colspan="3"><bean:write name="approvalsForm" property="subject" /></td></tr>
<tr><td>Description<font color="red">*</font></td><td colspan="3"><bean:write name="approvalsForm" property="reason" /></td></tr>
 <tr><th colspan="7">Comments </th>
			</tr>
			<tr>
			<td colspan="7"><html:textarea property="comments" style="width:100%;"></html:textarea>	</td></tr>
			
   	</table>
   	<br/>
   	<table>
	<tr><td colspan="6" style="border:0px; text-align: center;">
			
			&nbsp;&nbsp;
		   <logic:notEmpty name="approveButton">
			<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
			</td></tr></table>
			
				 <logic:notEmpty name="appList">
	<div>&nbsp;</div>
    <table class="bordered">
    <tr>
    <th colspan="5"><center>Approval Status</center></th>
    </tr>
    <tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.designation }</td>
	<td>${abc.approveStatus }</td>
	<td>${abc.approveDate }</td>
	<td>${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
			
			
			<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="requestNo"/>
		</html:form>
</body>
</html>