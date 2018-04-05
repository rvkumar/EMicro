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


function process()
{


document.forms[0].action="hrApprove.do?method=shift";
document.forms[0].submit();


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

		 <div style="width: 50%" >
<table class="bordered" >
		
		<tr>
		<th>  Type</th>
		<td><html:select  property="type" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:option value="Master Level">Shift Master</html:option>
			<html:option value="Day Wise">Day Wise</html:option>
			</html:select>
			&nbsp;
		<html:button property="method" value="Go" onclick="process()" styleClass="rounded"/>&nbsp;</td>	
		
		</tr>
</table>
		
<br/>
<center>
<div>

</div>
</center>
<br/>

<logic:notEmpty name="list1">

                
				  <% int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="22"><center>Attendance Summary List</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                    <th>Department</th>
                    <th>Designation</th>                    
                    <th>Work.</th>
                    <th>WO</th>
                    <th>PH</th>
                    <th>SS</th>
                    <th>CL</th>
                    <th>SL</th>
                    <th>EL</th>
                    <th>Lv.Avld</th>
                    <th>PP</th>
                    <th>OD</th>
                    <th>PM</th>
                    <th>LP</th>
                    <th>CO</th>
                    <th>ML</th>
                    <th>Tot.W</th>
                    <th>Pay grp Id</th>
                    <th>Pay Grp Name</th>
                  </tr>
                
                   
                  <logic:iterate id="abc1" name="list1">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i%></td>
                     <td> <bean:write name="abc1" property="employeeno"/></td>     
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="working"/></td>
                    <td> <bean:write name="abc1" property="weeklyOFf"/></td>
                 
                    
                    <td> <bean:write name="abc1" property="paidHoliday"/></td>
                    <td> <bean:write name="abc1" property="secstaurday"/></td>
                    <td> <bean:write name="abc1" property="cl"/></td>
                 	<td> <bean:write name="abc1" property="sl"/></td>
                    <td> <bean:write name="abc1" property="el"/></td>
                    <td> <bean:write name="abc1" property="leave_Availed"/></td>
                    
                    <td> <bean:write name="abc1" property="pp"/></td>
                    <td> <bean:write name="abc1" property="od"/></td>
                 
    				<td> <bean:write name="abc1" property="pm"/></td>
                    
                    
                 
                    
                    <td> <bean:write name="abc1" property="lp"/></td>
                    <td> <bean:write name="abc1" property="co"/></td>
                    <td> <bean:write name="abc1" property="ml"/></td>
                    <td> <bean:write name="abc1" property="total_worked"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                    <td> <bean:write name="abc1" property="payGrpTo"/></td>
                    
                 
                  
                  </tr>
				 </logic:iterate>
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="20"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				  </logic:notEmpty>
				  
				  
				  
				  <logic:notEmpty name="list2">
			
				<% int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="20"><center>Daily Wise Break List</center></th></tr>
                  <tr>
                  
                    
                    <th >#</th>
                     <th >Date</th>
                    <th>Employee No </th>
                    <th>Employee Name </th>
                     <th>In </th>
                    <th>Out</th>
                      <th>Status </th>
                     <th>From Date </th>
                    <th>To Date</th>
                    <th>Department</th>
                      <th>Designation</th>         
                        <th>Remarks</th>
                    <th>PaygroupID</th>
                      <th>Paygroup</th>
                      
                      </tr>
                        <logic:iterate id="abc1" name="list2">
                   <%i++; %>
             
                <tr>               
                     <td ><%=i %></td>
					<td> <bean:write name="abc1" property="date"/></td>     
					<td> <bean:write name="abc1" property="employeeno"/></td>     
					<td> <bean:write name="abc1" property="employeeName"/></td>
					<td> <bean:write name="abc1" property="startTime"/></td>     
					<td> <bean:write name="abc1" property="endTime"/></td>
					<td> <bean:write name="abc1" property="status"/></td>     
					<td> <bean:write name="abc1" property="startDate"/></td>
					<td> <bean:write name="abc1" property="endDate"/></td>
					<td> <bean:write name="abc1" property="department"/></td>
					<td> <bean:write name="abc1" property="designation"/></td>
					<td> <bean:write name="abc1" property="remarks"/></td>
					<td> <bean:write name="abc1" property="payGrpFrom"/></td>
					<td> <bean:write name="abc1" property="paygrp"/></td>
                   
                     
                     </tr>
                     </logic:iterate>
                      
                      </table>
</logic:notEmpty>

</div>
</html:form>
</body>
</html>
