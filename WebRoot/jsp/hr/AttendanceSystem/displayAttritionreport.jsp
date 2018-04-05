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

		 <div style="width: 90%" >
		 
		
<div>
</div>
</center>
</br>
</br>
 <logic:equal name="hrApprovalForm" property="summbrkup"  value="Summary">
                <logic:notEmpty name="list1">
				  <% int i=0;%>
				    <table border="1" >
                 
                     <tr>   <th colspan="22"><center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Addition and Attrition report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/></center></th></tr>
                  <tr>
                  
                    
                    <th style="width: 1%">#</th>
             
                    <th style="width: 30%">Department</th>
                    <th style="width: 15%">Available</th>                    
                    <th style="width: 15%">Addition</th>
                    <th style="width: 15%">Attrition</th>                   
                      <th style="width: 15%">Total</th>
                  </tr>
                  
                
                   
                  <logic:iterate id="abc1" name="list1">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>                  
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="availablecount"/></td>
                    <td> <bean:write name="abc1" property="additioncount"/></td>
                    <td> <bean:write name="abc1" property="attritioncount"/></td>
                    <td> ${abc1.availablecount+abc1.additioncount-abc1.attritioncount}</td>
                 
              
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
				     
				      <table border="1" ><tr><th> <b><big> <center>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Addition and Attrition report for the month - <bean:write name="hrApprovalForm" property="month"/> <bean:write name="hrApprovalForm" property="year"/></center></big></b></div>
				     </th></tr>
				    <table border="1" >
				    <tr><th colspan="8"><big><strong>Additions</strong></big></th></tr>
				  
				   <th colspan="8">STAFF</th>
				    <tr>	<!-- <th>Sl.No</th> -->	<th>Pernr</th><th>Emp Name</th><th>Department</th><th>Designation</th><th>DOJ</th><th>Previous Company</th><th colspan="2">Previous Experience(yrs)</th></tr>
				            <logic:iterate id="abc1" name="addi">
                 
             <logic:equal name="abc1" property="repgrp" value="Staff">
                 
				          
                <tr>               
                     <%-- <td ><%=i%></td>  --%> 
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                  
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
                    <td colspan="2"> &nbsp;</td>
             
                     
                     </tr> 
				        </logic:equal>
				        </logic:iterate>
						
						 <th colspan="8"> TECHNICAL STAFF</th>
				    <tr>	<!-- <th>Sl.No</th> -->	<th>Pernr</th><th>Emp Name</th><th>Department</th><th>Designation</th><th>DOJ</th><th>Previous Company</th><th colspan="2">Previous Experience(yrs)</th></tr>
				            <logic:iterate id="abc1" name="addi">
                 
             <logic:equal name="abc1" property="repgrp" value="Technical Staff">
                  
				          
                <tr>               
                     <%-- <td ><%=i%></td>   --%>
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                   
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
                    <td colspan="2"> &nbsp;</td>
             
                     
                     </tr> 
				        </logic:equal>
				        </logic:iterate>
				 
				  
				  	 <th colspan="8">Contractors</th>
				    <tr>	<!-- <th>Sl.No</th> -->	<th>Pernr</th><th>Emp Name</th><th>Reporting Group</th><th>Department</th><th>Designation</th><th>DOJ</th><th>Previous Company</th><th>Previous Experience(yrs)</th></tr>
				            <logic:iterate id="abc1" name="addi">
                   <%i++; %>
             <logic:notEqual name="abc1" property="repgrp" value="Staff">
			  <logic:notEqual name="abc1" property="repgrp" value="Technical Staff">
                        
                <tr>               
                    <%--  <td ><%=i%></td>   --%>
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="repgrp"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
                    <td> &nbsp;</td>
             
                     
                     </tr> 
				        </logic:notEqual>
						 </logic:notEqual>
				        </logic:iterate>
						
				
				
				  
				   
				  
				
				    
				   
				 <%--    <logic:equal name="hrApprovalForm" property="repgrp" value="Staff">
				        <tr>	<th>Sl.No</th>	<th>Pernr</th><th>Emp Name</th><th>Reporting Group</th><th>Department</th><th>Designation</th><th>DOJ</th><th>Previous Company</th><th>Previous Experience(yrs)</th></tr>
				          
				            <logic:iterate id="abc1" name="addi">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>  
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="repgrp"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
                    <td> &nbsp;</td>
             
                     
                     </tr>
				        
				        </logic:iterate>
				        </logic:equal> --%>
				        	<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
				        </table>
				    </logic:notEmpty>
				    
				       <logic:notEmpty name="attri">
				     <% int j=0;%>
					 <br/><br/>
				    <table border="1">
				    <tr><th colspan="8"><big><strong>Attritions</strong></big></th></tr>
					
						<th colspan="8">Staff</th>
				        <tr>		<th>Pernr</th><th>Emp Name</th><th>Department</th><th>Designation</th><th>Date of Leaving</th><th colspan="2">Reason for Leaving/Remarks</th></tr>
				            <logic:iterate id="abc1" name="attri">
                   <%j++; %>
				      <logic:equal name="abc1" property="repgrp" value="Staff">
			 
           
                <tr>               
                     <%-- <td ><%=j%></td> --%>  
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td colspan="2"> &nbsp;</td>
               
             
                     
                     </tr>
				         </logic:equal>
						   </logic:iterate>
						    <th colspan="8">Technical Staff</th>
				        <tr>		<th>Pernr</th><th>Emp Name</th><th>Department</th><th>Designation</th><th>Date of Leaving</th><th colspan="2"> Reason for Leaving/Remarks</th></tr>
				            <logic:iterate id="abc1" name="attri">
                   <%j++; %>
				      <logic:equal name="abc1" property="repgrp" value="Technical Staff">
			 
           
                <tr>               
                     <%-- <td ><%=j%></td>   --%>
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td colspan="2"> &nbsp;</td>
               
             
                     
                     </tr>
				         </logic:equal>
						   </logic:iterate>
						   
						 <th colspan="9">Contractors</th>
				        <tr>	<!-- <th>Sl.No</th> -->	<th>Pernr</th><th>Emp Name</th><th>Reporting Group</th><th>Department</th><th>Designation</th><th>Date of Leaving</th><th> Reason for Leaving/Remarks</th></tr>
				            <logic:iterate id="abc1" name="attri">
                   <%j++; %>
				      <logic:notEqual name="abc1" property="repgrp" value="Staff">
			  <logic:notEqual name="abc1" property="repgrp" value="Technical Staff">
           
                <tr>               
                  <%--    <td ><%=j%></td>   --%>
                     <td> <bean:write name="abc1" property="employeeno"/></td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                     <td> <bean:write name="abc1" property="repgrp"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> &nbsp;</td>
               
             
                     
                     </tr>
				         </logic:notEqual>
						 </logic:notEqual>
				        </logic:iterate>
						
						
					
						  
				        	<%if(j==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
				        </table>
				    </logic:notEmpty>
				    
				  </logic:equal>
				  
				  

</div>

<table border="0">
<tr>
<td align="right">
Printed By:   <bean:write name="hrApprovalForm" property="employeeNumber"/><br/>
Printed On : <%=new java.util.Date()%></td></tr></table>
</html:form>
</body>
</html>
