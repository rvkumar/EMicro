<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>


<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/styles.css" />
<title>Microlab</title>
<script type="text/javascript">

function getSubcat(linkname)
{
var xmlhttp;
var dt;
dt=linkname;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","itIsssues.do?method=getsubcategory&linkName="+dt,true);
xmlhttp.send();
}



function search(){
	var form = document.getElementById('searchSidFrm');
	form.action="itIsssues.do?method=searchSid";
	form.submit();
}




function deptSelected(){
	var form = document.getElementById('searchSidFrm');
	form.action="itIsssues.do?method=searchSid";
	form.submit();
}


function searchUsers()
{
	document.forms[0].action="itIsssues.do?method=searchUser";
	document.forms[0].submit();
}




function sendId(uName)
{
 var reqField="approver1";
  	if(reqField=="approver1"){
	opener.document.forms[0].forwardEmpId.value = uName;
	}
	
		
	window.open('itIsssues.do?method=searchUser','_parent','');
	window.close();
}

function searchTech()
{
	var category=document.forms[0].category.value;
	var subCategory=document.forms[0].subCategory.value;
	var  locNo=document.forms[0].locationId.value;
	
var url="itIsssues.do?method=getTechn&category="+category+"&subCategory="+subCategory+"&locNo="+locNo;
			document.forms[0].action=url;
			document.forms[0].submit();
}
</script>
</head>

<body>
<html:form action="itIsssues.do" method="post" onsubmit="searchContacts(); return false;">
			<div style="width:50%;">		
	<table width="50%" class="bordered">
		<tr>
		<th>Plant</th><td><html:select property="locationId" styleClass="text_field">
	 				<html:option value="">--Select--</html:option>
	  				<html:options property="locationIdList" labelProperty="locationLabelList" ></html:options>   
	 			</html:select></td>
			<th>Category</th>
		 	<td>
 			<html:select property="category" styleClass="content" styleId="filterId" onchange="getSubcat(this.value)">
				<html:option value="">---Select---</html:option>
				<html:option value="Hardware">Hardware</html:option>
				<html:option value="Internet">Internet</html:option>
				<html:option value="Network">Network</html:option>
				<html:option value="Operating System">Operating System</html:option>
				<html:option value="Printers">Printers</html:option>
				<html:option value="Software">Software</html:option>
				<html:option value="SAP">SAP</html:option>
				<html:option value="IT SERVICES">IT SERVICES</html:option>
				<html:option value="Telephone">Telephone</html:option>
		    </html:select>
			</td>
	  
	   		<th>SubCategory</th>
		 	<td>
		<div id="subcategoryID" align="left">
			<select name="essType">
			<option value="">--Select--</option>
			</select>	
	   	</div>
  			</td>
		
	    	<td>
    		<a href="#"><img src="images/search.png" align="absmiddle" title="Search..."  onclick="searchTech()"/></a>
    		<html:hidden property="reqFiled"/>
    		</td>
		</tr>
	</table>				
					
	</div>
	<div>&nbsp;</div>
	<logic:notEmpty name="techList" >
			<table class="sortable bordered" style="width: 50%;">
				<tr>
				
				<th >Employee Code</th>	<th >Name</th><th>&nbsp;</th>
				</tr>

				<logic:iterate id="empList" name="techList">
					<tr>
					<td><bean:write name="empList" property="technicianID"/></td>
					<td><bean:write name="empList" property="technicianName"/></td>
					
<td class="lft"><a href="javascript:sendId('<bean:write name="empList" property="technicianID"/>')">Select</a></td>
					</tr>
				</logic:iterate>
			</table> 
		</logic:notEmpty>				
		
		<logic:notEmpty name="noRecords">
		<table class="sortable bordered" style="width: 50%;">
			<tr>
			  <th >Employee Code</th>	<th >Name</th>
			</tr>
		    <tr><td colspan="2"><center> <font color="red">No Technicians Assigned...</font></center> </td></tr>
		</logic:notEmpty>
		
							</html:form>
            
            

<!-------------- FOOTER STARTS ------------------------->
	
</body>
</html>
