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
</script>


<title>Home Page</title>

<script language="javascript">

function process()
{
var k = document.forms[0].fromDate.value;

if(k=="")
{
alert("Please Select date");
return false;
}


document.forms[0].action="hrApprove.do?method=dailyreportsearch";
document.forms[0].submit();
var x=window.showModalDialog("hrApprove.do?method=dailyreportexe",null, "dialogWidth=800px;dialogHeight=600px; center:yes");

}

function processexcel()
{
var k = document.forms[0].fromDate.value;

if(k=="")
{
alert("Please Select date ");
return false;
}


document.forms[0].action="hrApprove.do?method=exportdailyreportsearch";
document.forms[0].submit();


}

function clearAllFields(){
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";
}




function statusMessage(message){
alert(message);
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
	<br/>			
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
<tr><th><center><big><b>MICRO LABS LIMITED,<bean:write name="hrApprovalForm" property="locationId"/><br/>Daywise AttendanceSummary Report for the Month :  <bean:write name="hrApprovalForm" property="month"/></b></big></center>	
	
	</th></tr></table>



</br>

<div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <%int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="9"><center>Summary Of Attendance</center></th></tr>
                 
                   <logic:notEmpty name="shift">
                  <logic:iterate id="abc1" name="shift">
                   <%i++; %>
                   <tr>           
                    <th colspan="9" >&nbsp;&nbsp;&nbsp;&nbsp;Date :-<bean:write name="abc1" property="startDate"/></th>
                    </tr>
                                    
                   <tr> 
                    <th>Type</th>
                    <th>ApprovedStrength</th>
                    <th>Onroll</th>
                    <th>Present</th>
                     <th>Absent</th>
                     <th>Onduty</th>
                     <th>Leave</th>
                     <th>Percentage</th>
                     </tr>
                <tr>
                    <td>Staff</td>     
                    <td> <bean:write name="abc1" property="staffapprstrength"/></td>
                    <td> <bean:write name="abc1" property="staffavailstrength"/></td>
					<td> <bean:write name="abc1" property="staffpresent"/></td> 
					<td> <bean:write name="abc1" property="staffabsent"/></td>
					<td> <bean:write name="abc1" property="staffondauty"/></td>     
					<td> <bean:write name="abc1" property="staffleave"/></td>
					<td> <bean:write name="abc1" property="perstaff"/></td>  
					</tr>
					 <tr>  
					  <td>Tech_Staff</td>             
                    <td> <bean:write name="abc1" property="techstaffapprstrength"/></td>
                    <td> <bean:write name="abc1" property="techstaffavailstrength"/></td>
                    <td> <bean:write name="abc1" property="techstaffpresent"/></td>
                    <td> <bean:write name="abc1" property="techstaffabsent"/></td>
                    <td> <bean:write name="abc1" property="techstaffondauty"/></td>
                    <td> <bean:write name="abc1" property="techstaffleave"/></td>
                    <td> <bean:write name="abc1" property="pertechstaff"/></td>
                    </tr>
                                    
                   <tr> 
                    <td>Contractor</td>    
                    <td> <bean:write name="abc1" property="contractapprstrength"/></td>
                     <td> <bean:write name="abc1" property="contractavailstrength"/></td>
                      <td> <bean:write name="abc1" property="contractpresent"/></td>
                       <td> <bean:write name="abc1" property="contractabsent"/></td>
                        <td> <bean:write name="abc1" property="conondauty"/></td>
                         <td> <bean:write name="abc1" property="conleave"/></td>
                          <td> <bean:write name="abc1" property="perconstaff"/></td>
                    </tr>   
                    <tr>
                      <td>Total</td>    
                    <td> <bean:write name="abc1" property="totalapprovedstrength"/></td>
                     <td> <bean:write name="abc1" property="totalavailstrength"/></td>
                      <td> <bean:write name="abc1" property="presize"/></td>
                       <td> <bean:write name="abc1" property="totalabsent"/></td>
                        <td> <bean:write name="abc1" property="totalonduty"/></td>
                         <td> <bean:write name="abc1" property="totalleave"/></td>
                          <td> <bean:write name="abc1" property="totalper"/></td>
                                    
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="9"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>
				 <br/>
			
				 
				 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  				 </table>
				 </div>
				 </div>


</center>

</div>
</html:form>
</body>
</html>
