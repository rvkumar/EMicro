<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	
	<script type="text/javascript" src="js/sorttable.js"></script>


	<script type="text/javascript" src="js/jquery.min.js"></script>
	<title>eMicro :: Login Users List</title>
	
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



function getdata()
{

if(document.forms[0].frmDate.value=="")
{
	  alert("Please Enter From Date");
	   document.forms[0].frmDate.focus();
	  return false;

}
if(document.forms[0].toDate.value=="")
{
	  alert("Please Enter To Date");
	   document.forms[0].toDate.focus();
	  return false;

}

var URL="loginUser.do?method=searchloginUsersList";
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function cleardata(){
	document.forms[0].frmDate.value="";
	document.forms[0].toDate.value="";
	
	var URL="loginUser.do?method=loginUsersList";
	document.forms[0].action=URL;
		document.forms[0].submit();
}

function getlogin()
{

var url="loginUser.do?method=getLogincount";
			document.forms[0].action=url;
			document.forms[0].submit();

}


function loginUsersList()
{

var url="loginUser.do?method=loginUsersList";
			document.forms[0].action=url;
			document.forms[0].submit();

}
</script>
	
	<style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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
</head>
<body >
<html:form action="loginUser" onsubmit="getdata(); return false;">


			
			
			<table class=" bordered" width="100%">
				<tr>
					<th colspan="7"><center><big>Login Users List</big></center></th>
				</tr>
				
				</table>
				
				<table class="bordered">
				<tr>
				<td>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<b>From Date:<font color="red">*</font>&nbsp;
	
								<html:text property="frmDate"  styleId="popupDatepicker" styleClass="rounded" readonly="true" style="width: 127px; "/>
								&nbsp;&nbsp;
				
			   To Date:<font color="red">*</font>&nbsp;
				
								<html:text property="toDate"  styleId="popupDatepicker2" styleClass="rounded" readonly="true" style="width: 127px; "/>
								&nbsp;&nbsp;
								<br/><br/>
					Location:&nbsp;
					<html:select property="locationId" styleClass="rounded" >
			    	<html:option value="">--Select--</html:option>
			      	<html:options property="locationIdList" labelProperty="locationLabelList"></html:options>  
				</html:select>
				
					&nbsp;&nbsp;
				Department:&nbsp;
							
					<html:select property="department" styleClass="rounded" >
			    	<html:option value="">--Select--</html:option>
			      	<html:options property="deptList" ></html:options>  
				</html:select>
									&nbsp;&nbsp;
								Employee Number/Name:&nbsp;
								<html:text property="pernr"   styleClass="rounded"/>
								
								<a href="#"><img src="images/search.png" align="absmiddle" onclick="getdata()" style="height: 10; width: 10; "/></a>
								<a href="#"><img src="images/clearsearch.jpg" align="absmiddle" onclick="cleardata()" style="height: 10; width: 10; "/></a>
								
								
				</td>
				
				</tr>
				</table>
				
				
				
				<div> &nbsp;</div>	
				 <logic:notEmpty name="loginbutton">
<html:button property="method" value="Get Login Details" styleClass="rounded" onclick="getlogin()"/></center>
</logic:notEmpty>
				 <logic:notEmpty name="backbutton">
<html:button property="method" value="Back" styleClass="rounded" onclick="loginUsersList()"/></center>
</logic:notEmpty>

 <logic:notEmpty name="userscountList">
 
 <div style="width: 60%;" >

  <table class="sortable bordered"  >
 <tr>
 <th>Location</th><th>Department</th><th style="width: 96px; ">Not Logged In</th><th>Logged In</th>
  </tr>

					<logic:iterate id="users" name="userscountList">
						<tr>
							
							<td><bean:write name="users"  property="location"/></td>
						    <td><bean:write name="users"  property="department"/></td>
							<td><bean:write name="users"  property="notlogincount"/></td>
							<td><bean:write name="users"  property="logincount"/></td>
							
						</tr>
					
					</logic:iterate>
 
 
 
 </logic:notEmpty>
	
				
				
				
				<logic:notEmpty name="usersList1">
				
				<div> &nbsp;</div>
				<table class="sortable bordered" width="100%">
				
				
				
					<tr>
					<th width="2%">Sl.No</th><th width="2%">Plant</th><th width="3%">Emp Number<th>Employee Name </th><th >Department</th><th width="15%">LogIn&nbsp;Time</th><th width="15%">IP Address</th>
					</tr>
					<%
					int i=1;
					%>
					<logic:iterate id="users" name="usersList1">
						<tr>
							<td><%=i %></td>
							<td><bean:write name="users"  property="location"/></td>
							<td><bean:write name="users"  property="employeeID"/></td>
							<td><bean:write name="users"  property="empName"/></td>
							<td><bean:write name="users"  property="dept"/></td>
							<td><bean:write name="users"  property="logintime"/></td>
							<td><bean:write name="users"  property="ipNo"/></td>
							
						</tr>
						<%
						i++;
						%>
					</logic:iterate>
			</table>
		</logic:notEmpty>
				
				 <logic:notEmpty name="usersList">
				
				<div> &nbsp;</div>
				<table class="sortable bordered" width="100%">
				
				
				
					<tr>
					<th width="2%">Sl.No</th><th width="2%">Plant</th><th width="3%">Emp Number<th>Employee Name </th><th >Department</th><th width="15%">LogIn&nbsp;Time</th><th width="15%">LogOut&nbsp;Time</th><th width="15%">IP Address</th>
					</tr>
					<%
					int i=1;
					%>
					<logic:iterate id="users" name="usersList">
						<tr>
							<td><%=i %></td>
							<td><bean:write name="users"  property="location"/></td>
							<td><bean:write name="users"  property="employeeID"/></td>
							<td><bean:write name="users"  property="empName"/></td>
							<td><bean:write name="users"  property="dept"/></td>
							<td><bean:write name="users"  property="logintime"/></td>
							<td><bean:write name="users"  property="logoutime"/></td>
							<td><bean:write name="users"  property="ipNo"/></td>
							
						</tr>
						<%
						i++;
						%>
					</logic:iterate>
			</table>
		</logic:notEmpty>
		<br/>
		<logic:notEmpty name="noRecords">
		<table class="sortable bordered" width="100%">
				
					<tr>
					<th>Sl.No</th><th>Plant</th><th>Emp Number<th>Employee Name </th><th>Department</th><th width="80px;">LogIn&nbsp;Time</th><th width="80px;">LogOut&nbsp;Time</th><th>IP Address</th>
					</tr>
					<tr>
					<td colspan="8">
					<font style="color: red;"><center>No Search Records Found...</center></font>
					</td>
					</tr>
		
		</logic:notEmpty>
</html:form>
</body>
</html>	