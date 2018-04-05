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



</script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">



		
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left"><center> Comp-Off Form</center></th>
			</tr>
		<tr>
			<td width="15%">Worked Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="startDate"/>
							</td>
		<%-- 	<td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="endDate"/>
							</td>	 --%>			
						<td width="15%">Comp Off Type <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
							<bean:write name="leaveForm" property="reasonType"/>
							</td>	
			</tr>
					<tr>
				<th colspan="4"> Detailed Reason<font color="red" size="2"> * </font>  
						</th>
						</tr>
			
			<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="100" rows="10" style="height: 119px;color:black; width: 874px" disabled="true" ></html:textarea>
						
							</td>
						</tr>
			
		</table>
	
		
		<table  class="bordered"  width="90%" align="left" id="compTable" >
<tr>
            <th width="50px" >SL.no</th>
            <th width="125px" >Employee No.</th>
            <th width="125px" >Name</th>
            <th width="85px" >Department</th>
            <th width="125px" >Designation</th>
            <th width="125px" >No. Of Hours</th>
            <th width="125px" >Applicable</th>
           
     
            
            
            

        </tr>
       
        <logic:iterate id="a" name="comp">
        
       <tr>
       
       <td><bean:write name="a" property="slmindur"/></td>       
       <td><bean:write name="a" property="employeeNumber"/> </td>       
       <td><bean:write name="a" property="employeeName"/></td>       
       <td><bean:write name="a" property="department"/></td>
       <td><bean:write name="a" property="designation"/></td>
       <td><bean:write name="a" property="nofhrs"/></td>
       <td><bean:write name="a" property="shift"/></td> 
       </tr>
       </logic:iterate>
            </table>
	<DIV>&nbsp;</DIV>
	
	
	
	<table>
	<tr><td colspan="6" style="border:0px; text-align: center;">
			
			
			
			<input type="button" class="rounded" value="Close" onclick="history.back(-1)"  /></td>
			
			</tr></table>
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
	

	
		
	
		</html:form>
</body>		

</html>