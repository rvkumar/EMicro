<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
function search(param1,param2)
{
	alert("hi");
	document.forms[0].action="vc.do?method=revertCompletedrequest&pernr="+param1+"&reqno="+param2;
	document.forms[0].submit();
}

</script>



</head>
<body >

	<html:form action="vc" enctype="multipart/form-data">
	<div>&nbsp;</div>
	 &nbsp;&nbsp;&nbsp;
	<div>&nbsp;</div>
	
	<html:hidden property="reqNo"  name="vcForm" />
	
				<div style="height:300px;overflow:auto">
				<logic:notEmpty name="emplist">
			
				
			<div align="left" class="bordered">
			<table class="sortable" >
			<thead>
			
				<tr>
				<th colspan="11"><center>User Details</center></th>
				</tr>
				
				
			<tr>
			<th style="text-align:left;"><b>
			#
		 </b></th>
			<th style="text-align:left;"><b>Division</b></th>
			<th style="text-align:left;"><b>Emp.No</b></th>
			<th style="text-align:left;"><b>Full Name</b></th>
			<th style="text-align:left;"><b>Role</b></th>
			<th style="text-align:left;"><b>HQ</b></th>
			<th style="text-align:left;"><b>State</b></th>
			<th style="text-align:left;"><b>File Name</b></th>
			<th style="text-align:left;"><b>Remarks</b></th>
			<th style="text-align:left;"><b>Submitted Date</b></th>
										
					</tr>
					</thead>
					<%int i=0; %>
				<logic:iterate id="mytable1" name="emplist">
				<tr>
				<%i++; %>
					<td>
					<%=i%>
					</td>				
					
					<td>
					
				<bean:write name="mytable1" property="divisionid"/>
					</td>
										<td>
				<bean:write name="mytable1" property="empId"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="empName"/>
				</td>
				<td>
				<bean:write name="mytable1" property="desg"/>
				</td>
				<td>
				<bean:write name="mytable1" property="headquater"/>
				</td>
				<td>
				<bean:write name="mytable1" property="state"/>
				</td>
				
				
				<td>
				<a href="${mytable1.path}"  target="_blank">${mytable1.filename}</a>						
				
				
				</td>
				<td>
				<bean:write name="mytable1" property="searchText"/>
				</td>
				<td>
				<bean:write name="mytable1" property="submitDate"/>
				 </td>
				
				
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		</div>
	
	</html:form>
	
</body>
	
</html>	