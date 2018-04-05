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


<!--<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
 Theme css 
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />



--><script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />


<script type="text/javascript">
	
</script>


<title>Home Page</title>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script language="javascript">



$(function() {

	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy',minDate:new Date()});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});


$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy',minDate:new Date()});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}


function search(){
	var form = document.getElementById('searchSidFrm');
	form.action="user.do?method=searchSid";
	form.submit();
}




function deptSelected(){
	var form = document.getElementById('searchSidFrm');
	form.action="user.do?method=searchSid";
	form.submit();
}


function searchUsers()
{
	document.forms[0].action="addUser.do?method=searchUser";
	document.forms[0].submit();
}

function sendId(uName)
{
	opener.document.forms[0].reportingManger.value = uName;
	
	window.open('addUser.do?method=searchUser','_parent','');
	window.close();
}


	function searchContacts()
		{
		var url="hrNewEmp.do?method=searchContacts";
					document.forms[0].action=url;
					document.forms[0].submit();
		}
		
	function updateData(emp)
    {

    	
		var url="hrApprove.do?method=Modifyshiftdetails&pernr="+emp;
		
		
					document.forms[0].action=url;
					document.forms[0].submit();
		
		

		
		}

function closeWindow(){
	window.close();
}




//-->
</script>

<!-- <script>
    window.onunload = refreshParent;
    function refreshParent() {
    var x=window.parent.property;
    
    
        window.close();
        
      
         window.opener.location.reload(); 
    } 
</script> -->
</head>

<body>
		<!--------WRAPER STARTS -------------------->

       
            
     		<div align="left">
				<logic:present name="hrApprovalForm" property="message">
					<font color="red" size="3"><b><bean:write name="hrApprovalForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="hrApprovalForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="hrApprovalForm" property="message2" /></b></font>
				</logic:present>
			</div>
					
				<html:form action="/hrApprove.do">
				<html:hidden property="emplist"  name="hrApprovalForm" />
			<div style="width:50%;" align="center">		
	<table width="50%" class="bordered" align="center">
	

					
					
		<tr>
			<th>Plant</th>
		 	<td >
		  		<bean:write name="hrApprovalForm" property="locationId"/>
			</td>
			</tr>
			<tr>
			<th>Employee Number</th>
		 	<td >
		 
		  		<bean:write name="hrApprovalForm" property="emplist"/>
			</td>
			
			
			</tr><th>Select Date</th>
				<%-- <td>
						<html:text property="curentDate"  styleId="popupDatepicker" style="width: 98px; "/></td>
						
					<td>
					<td>
						<html:text property="curentDate"  styleId="popupDatepicker" style="width: 98px; "/></td>
						
					<td> --%>
					
						<td >
		  		<bean:write name="hrApprovalForm" property="curentDate"/>
			</td>
		
				
			
			<tr>
			<th>Shift</th>
			<td>
			<%-- <html:select property="shift">
			
			<html:options name="hrApprovalForm"  property="shiftList" labelProperty="shiftLabelList"/>
			</html:select> --%>
				<bean:write name="hrApprovalForm" property="shift"/>
			</td>
			
			</tr>
			
			<tr>
			
	   		<td colspan="4" style="text-align: center;">
	   							<html:button property="method"  value="Close" onclick="closeWindow()" styleClass="rounded" style="width: 100px"></html:button>
				</td>
			</tr>
			
			</table> 
			</div>
				
		


							</html:form>
            
            

<!-------------- FOOTER STARTS ------------------------->
	
</body>
</html>
