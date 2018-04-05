<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<link rel="stylesheet" type="text/css" href="css/styles.css" />

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">




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




function sendId(empno,name,dept,desg,doj)
{
var reqField=document.forms[0].reqFiled.value;
var lastChar = reqField.substr(reqField.length - 1);



  	if(true){
  
	opener.document.getElementById(reqField).value = empno;
	opener.document.getElementById("empname"+lastChar).value = name;
	opener.document.getElementById("dept"+lastChar).value = dept;
	opener.document.getElementById("desg"+lastChar).value = desg;
	opener.document.getElementById("doj"+lastChar).value = doj;
	}
	
		
	
	window.close();
}

	function searchContacts()
		{
		var url="leave.do?method=searchContacts";
					document.forms[0].action=url;
					document.forms[0].submit();
		}




//-->
</script>
</head>

<body>
		<!--------WRAPER STARTS -------------------->

       
            
        
					
					<html:form action="leave.do" method="post" onsubmit="searchContacts(); return false;">
			<div style="width:50%;">		
	<table width="50%" class="bordered">
	
		<tr>
			<th>Plant</th>
		 	<td>
		  		<html:select property="locationId" styleClass="rounded">
			    	<html:option value="">--Select--</html:option>
			      	<html:options property="locationIdList" labelProperty="locationLabelList"></html:options>  
				</html:select>
			</td>
	  
	   		<th>Department</th>
		 	<td>
				<html:select property="department"  styleClass="rounded">
					<html:option value="">--Select--</html:option>
         			<html:options property="departmentList"  ></html:options>  
      			</html:select>
  			</td>
		
	    	<th>Name</th>
   			<td >
    			<html:text property="empName" styleId="prdsname" maxlength="30" size="30" styleClass="rounded" style="width: 124px; "/>
    		
    		<a href="#"><img src="images/search.png" align="absmiddle" title="Search..."  onclick="searchContacts()"/></a>
    		<html:hidden property="reqFiled"/>
    		</td>
		</tr>
	</table>				
					
	</div>
	
	<logic:notEmpty name="userDetails" >
			<div align="left" class="bordered" style="width: 716px; ">
			<table class="sortable" >
				<tr>
				
				<th width="10%">Emp Code</th>	<th width="10%">Name</th><th width="5%">&nbsp;</th>
				</tr>

				<logic:iterate id="empList" name="userDetails">
					<tr>
					<td><bean:write name="empList" property="employeeNumber"/></td>
						<td><bean:write name="empList" property="firstName"/></td>
					
						<td class="lft">
												<a href="javascript:sendId('<bean:write name="empList" property="employeeNumber"/>','<bean:write name="empList" property="firstName"/>','<bean:write name="empList" property="department"/>','<bean:write name="empList" property="designation"/>','<bean:write name="empList" property="dateofJoining"/>')">
													Select</a>
											</td>
					</tr>
				</logic:iterate>
			</table> 
			</div>
		</logic:notEmpty>				
		
								</table>


							</html:form>
            
            

<!-------------- FOOTER STARTS ------------------------->
	
</body>
</html>
