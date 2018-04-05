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


function process()
{

var loc=document.forms[0].locationId.value;
var month=document.forms[0].month.value;
var year=document.forms[0].year.value;
var summbrkup=document.forms[0].summbrkup.value;


document.forms[0].action="hrApprove.do?method=monthlyReportSearch";
document.forms[0].submit();
/* loading(); */
var x=window.showModalDialog("hrApprove.do?method=monthlyReportexe&loc="+loc+"&month="+month+"&year="+year+"&summbrkup="+summbrkup,null, "dialogWidth=800px;dialogHeight=600px; center:yes");

}




function statusMessage(message){
$('#overlay').remove();
alert(message);
}


 function loading() {
        // add the overlay with loading image to the page
        var over = '<div id="overlay">' +
            '<img id="loading" src="images/loadinggif.gif">' +
            '</div>';
        $(over).appendTo('body');

       
    };
    
    
    function processexcel()
{


document.forms[0].action="hrApprove.do?method=exportmonthlyreportsearch";
document.forms[0].submit();


}   


function back()
{

document.forms[0].action="hrApprove.do?method=attendanceReport";
document.forms[0].submit();

}
</script>

<style>

  #overlay {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.6;
    filter: alpha(opacity=80);
}
#loading {
    width: 250px;
    height: 257px;
    position: absolute;
    top: 20%;
    left: 50%;
    margin: -18px 0 0 -15px;
}

  </style>

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
		 
		
<div>



</div>
</center>
</br>
</br>

<logic:equal name="hrApprovalForm" property="summbrkup"  value="Final Settlement">
<table>
 <tr>   <th colspan="22"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave report for the year-${hrApprovalForm.year}</center></th></tr>
                  </table>
                  
                  
                <br/>
<table  class="bordered">

  <logic:iterate id="leaveForm" name="offlist">
<tr ><td ><b>Employee No.</b></td><td><bean:write name="leaveForm" property="employeeNumber"></bean:write></td>
<td ><b>Name</b></td><td><bean:write name="leaveForm" property="employeeName"></bean:write></td>

</tr>

<tr>
<td ><b>Designation</b></td><td><bean:write name="leaveForm" property="designation"></bean:write></td>
<td ><b>Department</b></td><td><bean:write name="leaveForm" property="department"></bean:write></td>

</tr>

<tr>
<td ><b>Date Of Joining</b></td><td><bean:write name="leaveForm" property="doj"></bean:write></td>
<td ><b>Year</b></td><td><bean:write name="hrApprovalForm" property="year"></bean:write></td>
</tr>
</logic:iterate>
</table></br>  

<logic:notEmpty name="LeaveBalenceList">
		 <div style="width: 90%" >
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left">Leave Balances (In Days)-<bean:write name="hrApprovalForm" property="year"/></th>
			</tr>
		<tr>
			<th>Leave Type</th><th>Opening Balance</th><th>Availed</th><th>Closing Balance</th><th>Awaiting for Approval</th>
			</tr>
			<logic:iterate id="bal" name="LeaveBalenceList">
						<tr>
						<th><bean:write name="bal" property="leaveType"/></th>
						<td><bean:write name="bal" property="openingBalence"/></td>
						<td><bean:write name="bal" property="avalableBalence"/></td>
						<td><bean:write name="bal" property="closingBalence"/></td>
						<td><bean:write name="bal" property="awaitingBalence"/></td>
						</tr>	
			</logic:iterate>			
		</table>
		</div>
		</logic:notEmpty>
		
		
		</br>
		
		<table class="bordered">

<tr align="center">
<th rowspan="2"><center>Leave Type</center></th>
<th colspan="2"><center>Leave Applied</center></th>
<th rowspan="2"><center>No.of <br>Days</center></th>
<th rowspan="2"><center> Applied On</center></th>
<th rowspan="2"><center> Approved On</center></th>
<th rowspan="2"><center>Reason</center></th>


</tr>
<tr >
<th ><center>From</center></th>
<th ><center>To</center></th>


</tr>
<logic:iterate id="a" name="list">
<tr><td align="left"><bean:write name="a" property="leaveType"/></td><td><center><bean:write name="a" property="startDate"/></center></td><td><center><bean:write name="a" property="endDate"/></center></td><td><center><bean:write name="a" property="noOfDays"/></center></td><td><center><bean:write name="a" property="submitDate"/></center></td><td><center><bean:write name="a" property="approvedDate"/></center></td><td><bean:write name="a" property="reason"/></td></tr>
</logic:iterate>
</table>


<logic:notEmpty name="norecords">
<table class="bordered">


<tr><td colspan="7"><font color="red"><center>No Records</center> </font></td></tr>
</table>

</logic:notEmpty>

		

</logic:equal>

 <logic:equal name="hrApprovalForm" property="summbrkup"  value="Summary">
                <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="22"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave Balance report for the year-${hrApprovalForm.year}</center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
             
        
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      
                  </tr>
                  <tr><th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  </tr>
                                          <logic:iterate id="b" name="deptlist">
<tr>
<td colspan="12">
<b>Department </b>   :   <bean:write name="b" property="deptFrom" /><br/>
<b>Subdepartment</b> :   <bean:write name="b" property="deptTo" /><br/>
<b>Reporting Group   </b>   :   <bean:write name="b" property="desgTo" />

</td>

</tr>
                   
                  <logic:iterate id="abc1" name="list1">
                
           <logic:equal value="${abc1.department}" name="b" property="department">
<logic:equal value="${abc1.subdepartment}" name="b" property="subdepartment">
<logic:equal value="${abc1.repgrp}" name="b" property="repgrp">
   <%i++; %>
                <tr>               
                     <td ><%=i%></td>                  
                   
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 
              
                  </tr>
                 </logic:equal></logic:equal></logic:equal>
				 </logic:iterate>
				 	
				 </logic:iterate>
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				    
				    
				    
				      <logic:notEmpty name="onlydept">
				  <% int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="22"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave Balance report for the year-${hrApprovalForm.year}</center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
             
                    <th rowspan="2">Department</th>
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      
                  </tr>
                  <tr><th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  </tr>
                
                   
                  <logic:iterate id="abc1" name="onlydept">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>                  
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				    </logic:equal>
				    
				    
				     <logic:equal name="hrApprovalForm" property="summbrkup"  value="Detailed">
				          <logic:notEmpty name="addi">
				  <% int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="22"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Leave Balance report for the year-${hrApprovalForm.year} </center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
                  <th rowspan="2">Employee Code</th>
                  <th rowspan="2">Employee name</th>
                    <th rowspan="2">Department</th>
                     <th rowspan="2">Designation</th>
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      
                  </tr>
                  <tr><th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  <th>Opening Balance</th>
                  <th>Availed Balance</th>
                  <th>Closing Balance</th>
                  </tr>
                
                   
                  <logic:iterate id="abc1" name="addi">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>       
                         <td> <bean:write name="abc1" property="employeeno"/></td>    
                         <td> <bean:write name="abc1" property="employeeName"/></td>           
                    <td> <bean:write name="abc1" property="department"/></td>
                        <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
				  </logic:equal>
				  
				  

</div>
</html:form>
</body>
</html>
