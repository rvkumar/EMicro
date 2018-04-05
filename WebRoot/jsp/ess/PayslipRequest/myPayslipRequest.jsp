

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
	function processrequest()
	{
	
	if(document.forms[0].req_type.value=="Payslip")
	{
	if(document.forms[0].month.value=="")
	{
	alert("Please Select Month");
     document.forms[0].month.focus();
     return false;
	}
	}
	
	if(document.forms[0].year.value=="")
	{
	alert("Please Select Year");
     document.forms[0].year.focus();
      return false;
	}
	
	if(document.forms[0].req_type.value=="Payslip")
	{
	var d = new Date();
	var curdate= d.getDate();
	var curyear= d.getFullYear();
	var curmon=d.getMonth()+1;
	
	var usermonth=document.forms[0].month.value;
    var useryear=document.forms[0].year.value;
	   if(useryear==curyear)
    {
    
    
    if(usermonth>=curmon )
    {
    alert("PaySlip not available for selected month");
  return false;
    }
    
    else
    {
    if(curdate<10 && usermonth==(curmon-1) )
    {
      alert("PaySlip not available for selected month");
  return false;
    }
    
    }    
    
    }
    }

	document.forms[0].action="paysliprequest.do?method=raiserequest";
	document.forms[0].submit();
	}
	
	function statusMessage(message){

alert(message);
}


</script>
</head>

<body >
<html:form action="paysliprequest" onsubmit="return processrequest()" enctype="multipart/form-data" method="post">
<div align="center">
				<logic:notEmpty name="paysliprequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="paysliprequestForm" property="message" />');
					</script>
				</logic:notEmpty>
				
			</div>

<html:hidden property="req_type" />
	<table class="bordered">
	<logic:equal value="Payslip" name="paysliprequestForm" property="req_type">
	<tr><th colspan="6"><center>Payslip Request Form</center></th></tr>
	</logic:equal>
	<logic:equal value="Form16" name="paysliprequestForm" property="req_type">
		<tr><th colspan="6"><center>Form16 Request Form</center></th></tr>
	</logic:equal>
		<tr>
		<logic:equal value="Payslip" name="paysliprequestForm" property="req_type">
<th>Month</th>
<td>

<html:select  property="month" name="paysliprequestForm" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:option value="1">Jan</html:option>
<html:option value="2">Feb</html:option>
<html:option value="3">Mar</html:option>
<html:option value="4">April</html:option>
<html:option value="5">May</html:option>
<html:option value="6">June</html:option>
<html:option value="7">July</html:option>
<html:option value="8">Aug</html:option>
<html:option value="9">Sep</html:option>
<html:option value="10">Oct</html:option>
<html:option value="11">Nov</html:option>
<html:option value="12">Dec</html:option>
</html:select>
</td>
</logic:equal>
<th colspan="">Financial Year</th>
<td>
<html:select  property="year" name="paysliprequestForm" styleClass="testselect1" >
<html:option value="">--Select--</html:option>
<html:options name="paysliprequestForm"  property="yearList"/>
</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</td></tr>

<tr><th>Please enter the letters displayed</th><td colspan="4">
<input type="text" id="defaultReal" name="defaultReal" class="rounded" style="margin-bottom: 10px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="submit" value="Submit" class="rounded">
</td></tr>
</table>
	<br/>
	<logic:notEmpty name="paylist">
		<table class="bordered">
	<tr><th colspan="7"><center>My Request</center></th></tr>
	<tr><th>Sl no</th><th> Req no</th><th> Req Type</th>
	<logic:equal value="Payslip" name="paysliprequestForm" property="req_type"><th> Month</th></logic:equal>
	<th>Year</th><th>Req Date</th><th>Status</th></tr>
	<%int h=0; %>
	<logic:iterate id="a" name="paylist">
	<%h++; %>
	<tr><td><%=h %></td><td>${a.req_id}</td><td>${a.req_type}</td>
			<logic:equal value="Payslip" name="paysliprequestForm" property="req_type"><td>${a.monthname}</td></logic:equal>
			<td>${a.year}</td><td>${a.req_date}</td><td>${a.status}</td></tr>
	
	</logic:iterate>
	</table>	
	</logic:notEmpty>
	
	<logic:empty name="paylist">
	<table class="bordered">
	<tr><th colspan="7"><center>My Request</center></th></tr>
	<tr><th>Sl no</th><th> Req no</th><th> Req Type</th><th> Month</th><th>Year</th><th>Req Date</th><th>Status</th></tr>
	<tr><td colspan="7"><center>No records...</center></td></tr>
	</table>
	</logic:empty>
		</html:form>
</body>
</html>