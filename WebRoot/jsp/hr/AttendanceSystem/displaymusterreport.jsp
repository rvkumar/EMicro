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


document.forms[0].action="hrApprove.do?method=musterReportSearch";
document.forms[0].submit();

ShowProgressAnimation();
document.getElementById('mainContent').style.display = 'none';

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

<a href="javascript:window.print()"><img src="jsp/hr/print.png" title="Print this Page"></a>
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

<table class="bordered" >
<tr><th><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Muster Monthly Attendance Detail Report for the Month of  <bean:write name="hrApprovalForm" property="month"/>-<bean:write name="hrApprovalForm" property="year"/></b></big></center>
	</th></tr></table>

 <div style="width: 90%" >
 
 <logic:equal name="hrApprovalForm" property="attntype"  value="Calendar Month">

<div>


<logic:notEmpty name="list1">
<table class="bordered" >





 <% int i=0;%>
<logic:iterate id="abc1" name="list1">


<logic:equal value="0" property="odsize" name="abc1">

<tr><td colspan="50">
<bean:write name="abc1" property="month"/>
</td></tr>
					<tr>
					<th>Date</th>
					<logic:iterate id="c" name="datelist">         

<th>${c.day}</th>

</logic:iterate></tr>	
					</logic:equal>
					
		<logic:notEqual value="0" property="odsize" name="abc1">			
<tr>
<%i++;%>


<td>	<logic:equal value="1" property="odsize" name="abc1">
REM</logic:equal>
<logic:equal value="2" property="odsize" name="abc1">
In</logic:equal>
<logic:equal value="3" property="odsize" name="abc1">
Out</logic:equal>
<logic:equal value="4" property="odsize" name="abc1">
Late</logic:equal>
<logic:equal value="5" property="odsize" name="abc1">
Early</logic:equal>
<logic:equal value="6" property="odsize" name="abc1">
Wk Hrs</logic:equal>
<logic:equal value="7" property="odsize" name="abc1">
Ot Hrs</logic:equal>
<logic:equal value="8" property="odsize" name="abc1">
Shift Code</logic:equal>
</td>



                  <logic:iterate id="d" name="datelist">         
					
					
<td> <bean:write name="abc1" property="day${d.day}"/></td>

</logic:iterate>
                   
                    </tr></logic:notEqual>
                  
					                    
					
					</logic:iterate>
					  <%if(i==0) 
					{
					%>
					<tr><td colspan="20"><center>Currently details are not available to display</center></td></tr>
					<%} %> 
					</table> 
					</logic:notEmpty>



<logic:notEmpty name="list3">
<table class="bordered" >


<tr>
<th>Pernr</th><th>Name</th><th>Month</th><th>Year</th>
                    <logic:iterate id="d" name="datelist">         
					
<th>${d.day}</th>

</logic:iterate>
					<th> Present</th>
					<th> CL</th>
					<th> SL</th>
					<th> EL</th>					
					<th> WO</th>
					<th>SS</th>
					<th> PH</th>
					<th> OD</th>
					<th> SH</th>
					<th> LP</th>
					<th>Paid Days</th> 
				    <th>Total</th> 
</tr>

 <% int i=0;%>
 
<logic:iterate id="abc1" name="list3">

<tr>
<%i++;%>
<td><bean:write name="abc1" property="employeeno"/></td>
<td><bean:write name="abc1" property="employeeName"/></td>
<td><bean:write name="abc1" property="month"/></td>
<td><bean:write name="abc1" property="year"/></td>
 <logic:iterate id="d" name="datelist">         
					
<td> <bean:write name="abc1" property="day${d.day}"/></td>

</logic:iterate>
					
					<td> <bean:write name="abc1" property="pp"/></td>
					<td> <bean:write name="abc1" property="cl"/></td>
					<td> <bean:write name="abc1" property="sl"/></td>
					<td> <bean:write name="abc1" property="el"/></td>					
					<td> <bean:write name="abc1" property="wo"/></td>
						<td> <bean:write name="abc1" property="ss"/></td>
					<td> <bean:write name="abc1" property="ph"/></td>
					<td> <bean:write name="abc1" property="od"/></td>
					<td> <bean:write name="abc1" property="sh"/></td>
					<td> <bean:write name="abc1" property="lp"/></td>
				<td> ${abc1.pp+abc1.cl+abc1.sl+abc1.el+abc1.wo+abc1.ss+abc1.ph+abc1.od+abc1.sh}</td> 
					<td> ${abc1.pp+abc1.cl+abc1.sl+abc1.el+abc1.wo+abc1.ss+abc1.ph+abc1.od+abc1.sh+abc1.lp}</td> 
                    </tr>
                    <%if(i==0) 
					{
					%>
					<tr><td colspan="20"><center>Currently details are not available to display</center></td></tr>
					<%} %> 
					                    
					
					</logic:iterate>
					
					
				<tr>
			 <bean:define id="val" value="${hrApprovalForm.day}"></bean:define>
					<td colspan="<bean:write name="val" />" style="background-color:#483D8B ;color: white"><center><b>Total</b></center>			
					</td>
					
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="pp"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="cl"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="sl"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="el"/></td>					
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="wo"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="ss"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="ph"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="od"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="sh"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="lp"/></td>
				<td style="background-color:#483D8B ;color: white"> ${hrApprovalForm.pp+hrApprovalForm.cl+hrApprovalForm.sl+hrApprovalForm.el+hrApprovalForm.wo+hrApprovalForm.ss+hrApprovalForm.ph+hrApprovalForm.od+hrApprovalForm.sh}</td> 
					<td style="background-color:#483D8B ;color: white"> ${hrApprovalForm.pp+hrApprovalForm.cl+hrApprovalForm.sl+hrApprovalForm.el+hrApprovalForm.wo+hrApprovalForm.ss+hrApprovalForm.ph+hrApprovalForm.od+hrApprovalForm.sh+hrApprovalForm.lp}</td> 
			         </tr>
					
				    
					</table> 
					</logic:notEmpty>

</div>
</logic:equal>

 <logic:equal name="hrApprovalForm" property="attntype"  value="Payable Month">

<div>


<logic:notEmpty name="list1">
<table class="bordered" >





 <% int i=0;%>
<logic:iterate id="abc1" name="list1">


<logic:equal value="0" property="odsize" name="abc1">

<tr><td colspan="50">
<bean:write name="abc1" property="month"/>
</td></tr>
					<tr>
					<th>Date</th>
					<logic:iterate id="c" name="datelist">         

<th>${c.day}</th>

</logic:iterate></tr>	
					</logic:equal>
					
		<logic:notEqual value="0" property="odsize" name="abc1">			
<tr>
<%i++;%>


<td>	<logic:equal value="1" property="odsize" name="abc1">
REM</logic:equal>
<logic:equal value="2" property="odsize" name="abc1">
In</logic:equal>
<logic:equal value="3" property="odsize" name="abc1">
Out</logic:equal>
<logic:equal value="4" property="odsize" name="abc1">
Late</logic:equal>
<logic:equal value="5" property="odsize" name="abc1">
Early</logic:equal>
<logic:equal value="6" property="odsize" name="abc1">
Wk Hrs</logic:equal>
<logic:equal value="7" property="odsize" name="abc1">
Ot Hrs</logic:equal>
<logic:equal value="8" property="odsize" name="abc1">
Shift Code</logic:equal>
</td>



                  <logic:iterate id="d" name="datelist">         
					
					
<td> <bean:write name="abc1" property="day${d.day}"/></td>

</logic:iterate>
                   
                    </tr></logic:notEqual>
                  
					                    
					
					</logic:iterate>
					  <%if(i==0) 
					{
					%>
					<tr><td colspan="20"><center>Currently details are not available to display</center></td></tr>
					<%} %> 
					</table> 
					</logic:notEmpty>
					
					
					
					<logic:notEmpty name="list3">
<table class="bordered" >


<tr>
<th>Pernr</th><th>Name</th><th>Month</th><th>Year</th>
                   <logic:iterate id="d" name="datelist">         
					
<th>${d.day}</th>

</logic:iterate>
					
					<th> Present</th>
					<th> CL</th>
					<th> SL</th>
					<th> EL</th>					
					<th> WO</th>
				    <th> SS</th>
					<th> PH</th>
					<th> OD</th>
					<th> SH</th>
					<th> LP</th>
					<th>Paid Days</th> 
				    <th>Total</th> 
</tr>
 <% int i=0;%>
 
<logic:iterate id="abc1" name="list3">

<tr>
<%i++;%>
<td><bean:write name="abc1" property="employeeno"/></td>
<td><bean:write name="abc1" property="employeeName"/></td>
<td><bean:write name="abc1" property="month"/></td>
<td><bean:write name="abc1" property="year"/></td>
<logic:iterate id="d" name="datelist">         
					
<td> <bean:write name="abc1" property="day${d.day}"/></td>

</logic:iterate>
					
					<td> <bean:write name="abc1" property="pp"/></td>
					<td> <bean:write name="abc1" property="cl"/></td>
					<td> <bean:write name="abc1" property="sl"/></td>
					<td> <bean:write name="abc1" property="el"/></td>					
					<td> <bean:write name="abc1" property="wo"/></td>
							<td> <bean:write name="abc1" property="ss"/></td>
					<td> <bean:write name="abc1" property="ph"/></td>
					<td> <bean:write name="abc1" property="od"/></td>
					<td> <bean:write name="abc1" property="sh"/></td>
					<td> <bean:write name="abc1" property="lp"/></td>
					<td> ${abc1.pp+abc1.cl+abc1.sl+abc1.el+abc1.wo+abc1.ss+abc1.ph+abc1.od+abc1.sh}</td> 
					<td> ${abc1.pp+abc1.cl+abc1.sl+abc1.el+abc1.wo+abc1.ss+abc1.ph+abc1.od+abc1.sh+abc1.lp}</td> 
                    </tr>
                    <%if(i==0) 
					{
					%>
					<tr><td colspan="20"><center>Currently details are not available to display</center></td></tr>
					<%} %> 
		
					</logic:iterate>
					
				
					
					
					<tr>
				
							 <bean:define id="val" value="${hrApprovalForm.day}"></bean:define>
					<td colspan="<bean:write name="val" />" style="background-color:#483D8B ;color: white"><center><b>Total</b></center>		
					</td>
					
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="pp"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="cl"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="sl"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="el"/></td>					
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="wo"/></td>
							<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="ss"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="ph"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="od"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="sh"/></td>
					<td style="background-color:#483D8B ;color: white"> <bean:write name="hrApprovalForm" property="lp"/></td>
				<td style="background-color:#483D8B ;color: white"> ${hrApprovalForm.pp+hrApprovalForm.cl+hrApprovalForm.sl+hrApprovalForm.el+hrApprovalForm.wo+hrApprovalForm.ss+hrApprovalForm.ph+hrApprovalForm.od+hrApprovalForm.sh}</td> 
					<td style="background-color:#483D8B ;color: white"> ${hrApprovalForm.pp+hrApprovalForm.cl+hrApprovalForm.sl+hrApprovalForm.el+hrApprovalForm.wo+hrApprovalForm.ss+hrApprovalForm.ph+hrApprovalForm.od+hrApprovalForm.sh+hrApprovalForm.lp}</td> 
			        </tr>
					 
					</table> 
					</logic:notEmpty>
					


</div>
</logic:equal>

</html:form>
</body>
</html>
