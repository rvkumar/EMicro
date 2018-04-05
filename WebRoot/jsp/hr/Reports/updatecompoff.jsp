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



function updatecomoff(){
 var reqno=document.forms[0].requestNumber.value;
 var re=document.getElementById("nofhrs1").value;
 var app=document.getElementById("appl1").value;
 
 if(re==""){
 alert("Please Select No Of Hours Worked");
 document.getElementById("nofhrs1").focus();
 return false;
 
 }
 if(app==""){
 alert("Please Select After/Before Shift Worked");
 document.getElementById("appl1").focus();
 return false;
 }
 /* var re=document.getElementById("reqno").value; */
 

	var url="leave.do?method=updatecompoff&reqno="+reqno+"&hrs="+re+"&appl="+app;
					document.forms[0].action=url;
					document.forms[0].submit();
}
</script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">

<logic:present 	 name="leaveForm" property="message2">
				<center><font color="green" size="3"><b><bean:write name="leaveForm" property="message2" /></b></font></center>
				
			</logic:present>
<logic:present name="leaveForm" property="message">
				<center><font color="red" size="3"><b><bean:write name="leaveForm" property="message" /></b></font></center>
				
			</logic:present>
		
		
		<table  class="bordered"  width="90%" align="left" id="compTable" >
		<tr>
				<th colspan="8" align="left"><center> Comp-Off Update Form</center></th>
			</tr>
<tr>
            <th width="50px" >Req.no</th>
            <th width="125px" >Employee No.</th>
            <th width="125px" >Name</th>
            <th width="85px" >Department</th>
            <th width="125px" >Designation</th>
            <th width="125px" >No. Of Hours</th>
            <th width="125px" >Applicable</th>
           
     
            
            
            

        </tr>
       
        <logic:iterate id="a" name="comp">
        
       <tr>
       
  <%--      <td><bean:write name="a" property="slmindur"/></td>    --%>    
   <html:hidden property="requestNumber" value="${a.requestNumber}" />  
    <td><bean:write name="a" property="requestNumber"/></td> 
       <td><bean:write name="a" property="employeeNumber"/> </td>       
       <td><bean:write name="a" property="employeeName"/></td>       
       <td><bean:write name="a" property="department"/></td>
       <td><bean:write name="a" property="designation"/></td>
       
   
       <td><bean:write name="a" property="nofhrs"/></td>
       <td><bean:write name="a" property="shift"/></td> 
       </tr>
       </logic:iterate>
            </table>
		<table class="bordered" width="90%">
		<!-- 	<tr>
				<th colspan="8" align="left"><center> Comp-Off Update Form</center></th>
			</tr> -->
			<tr>
				<th colspan="8"> Detailed Reason<font color="red" size="2"> * </font>  
						</th>
						</tr>
			
			<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="100" rows="10" style="height: 119px;color:black; width: 874px" disabled="true" ></html:textarea>
						
							</td>
						</tr>
		<tr>
			<th width="15%">Worked Date </th> 
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="startDate"/>
							</td>
		<%-- 	<td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="endDate"/>
							</td>	 --%>			
						<th width="15%">Comp Off Type </th> 
							
							<td align="left" width="34%" colspan="4">
							<bean:write name="leaveForm" property="reasonType"/>
							</td>	
			</tr>
		
			<tr>
			<th>Shift Code</th>
			<td><bean:write name="leaveForm" property="shift"/></td>
			<th>Totalworking Hour</th>
			<td colspan="4"><bean:write name="leaveForm" property="toPlace"/></td>
			
			</tr><tr>
			<th>In Time</th>
			<td><bean:write name="leaveForm" property="comFromtime"/></td>
			<th>Out Time</th>
			<td><bean:write name="leaveForm" property="comTotime"/></td>
			<th>Status</th>
			<td colspan="2"><bean:write name="leaveForm" property="secstatus"/></td>
			</tr>
			<%-- <tr><th>Status</th>
			<td><bean:write name="leaveForm" property="secstatus"/></td></tr> --%>
				
			
		</table>
	
		
            <table  class="bordered"  width="10px" align="right" id="compTable" >
	<tr>
          
         
            <th width="15px" colspan="2" >No. Of Hours</th>
            <th width="15px"   colspan="2">Applicable</th>
           
     
            
            
            

        </tr>
       
        <logic:iterate id="a" name="comp">
        
       <tr>
       <%-- <td>${a.requestNumber}</td> --%>
   <%--    <html:hidden property="requestNumber" value="${a.requestNumber}" /> 
       <td><bean:write name="a" property="slmindur"/></td> 
      <td><bean:write  name="a" property="requestNumber"/></td>      
       <td><bean:write name="a" property="employeeNumber"/> </td>       
       <td><bean:write name="a" property="employeeName"/></td>       
       <td><bean:write name="a" property="department"/></td>
       <td><bean:write name="a" property="designation"/></td --%>
       
    <%--    <td><html:text property="designation" styleId="approver1" onkeyup="searchEmployee('approver1')">
	<bean:write property="approver1" name="essApproverForm" /></html:text> --%>
	<%--    <td><html:text property="nofhrs" styleId="approver1">
	<bean:write property="nofhrs" name="a" /></html:text></td>
       <td><bean:write name="a" property="nofhrs"/></td>
       <td><bean:write name="a" property="shift"/></td>  --%>
         <td colspan="2"><select name="nofhrs" id="nofhrs1">
       <option value="">-Select-</option>
      <option value="4">4 Hours</option>
      <option value="8">8 Hours</option>       
       </select></td>
       <td id="app" colspan="2"><select name="appl" id="appl1">
       <option value="">-Select-</option>
      <option value="BS">Before Shift Start Time</option>
      <option value="AS">After Shift End Time</option>       
       </select></td>
       </tr>
       </logic:iterate>
            </table>
	<DIV>&nbsp;</DIV>
	
	
	
	<table>
	<tr><td style="border:0px; text-align: center;">
			
			<input type="button" class="rounded" value="Close" onclick="history.back(-1)"  />
			
			
		<html:button property="method" styleClass="rounded" value="Update" onclick="updatecomoff();" style="align:right;width:100px;"/> &nbsp;</td>
			
			</tr></table>
				 <logic:notEmpty name="appList">
	<div>&nbsp;</div>
   
	</logic:notEmpty>
	

	
		
	
		</html:form>
</body>		

</html>