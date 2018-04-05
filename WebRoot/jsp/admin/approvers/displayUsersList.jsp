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




function sendId(uName)
{
 var reqField=document.forms[0].reqFiled.value;
  	if(reqField=="approver1"){
	opener.document.forms[0].approver1.value = uName;
	}
	if(reqField=="parllelAppr11"){
	opener.document.forms[0].parllelAppr11.value = uName;
	}
	if(reqField=="parllelAppr12"){
	opener.document.forms[0].parllelAppr12.value = uName;
	}
	if(reqField=="approver2"){
	opener.document.forms[0].approver2.value = uName;
	}
	if(reqField=="parllelAppr21"){
	opener.document.forms[0].parllelAppr21.value = uName;
	}
	if(reqField=="parllelAppr22"){
	opener.document.forms[0].parllelAppr22.value = uName;
	}
	
	if(reqField=="approver3"){
	opener.document.forms[0].approver3.value = uName;
	}
	if(reqField=="parllelAppr31"){
	opener.document.forms[0].parllelAppr31.value = uName;
	}
	if(reqField=="parllelAppr32"){
	opener.document.forms[0].parllelAppr32.value = uName;
	}
	
	if(reqField=="approver4"){
	opener.document.forms[0].approver4.value = uName;
	}
	if(reqField=="parllelAppr41"){
	opener.document.forms[0].parllelAppr41.value = uName;
	}
	if(reqField=="parllelAppr42"){
	opener.document.forms[0].parllelAppr42.value = uName;
	}
	
	if(reqField=="approver5"){
	opener.document.forms[0].approver5.value = uName;
	}
	if(reqField=="parllelAppr51"){
	opener.document.forms[0].parllelAppr51.value = uName;
	}
	if(reqField=="parllelAppr52"){
	opener.document.forms[0].parllelAppr52.value = uName;
	}
	
	if(reqField=="approver6"){
	opener.document.forms[0].approver6.value = uName;
	}
	if(reqField=="parllelAppr61"){
	opener.document.forms[0].parllelAppr61.value = uName;
	}
	if(reqField=="parllelAppr62"){
	opener.document.forms[0].parllelAppr62.value = uName;
	}
		
	window.open('addUser.do?method=searchUser','_parent','');
	window.close();
}

	function searchContacts()
		{
		var url="materialApprover.do?method=searchContacts";
					document.forms[0].action=url;
					document.forms[0].submit();
		}




//-->
</script>
</head>

<body>
		<!--------WRAPER STARTS -------------------->

       
            
        
					
					<html:form action="materialApprover.do" method="post" onsubmit="searchContacts(); return false;">
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
		
	    	<th>Full Name</th>
   			<td >
    			<html:text property="empName" styleId="prdsname" maxlength="30" size="30" styleClass="rounded"/>
    		&nbsp;&nbsp;
    		<a href="#"><img src="images/search.png" align="absmiddle" title="Search..."  onclick="searchContacts()"/></a>
    		<html:hidden property="reqFiled"/>
    		</td>
		</tr>
	</table>				
					
	</div>
	
	<logic:notEmpty name="userDetails" >
			<div align="left" class="bordered">
			<table class="sortable">
				<tr>
				
				<th >Employee Code</th>	<th >Name</th><th >Department</th><th>Plant</th>
				</tr>

				<logic:iterate id="empList" name="userDetails">
					<tr>
					<td><bean:write name="empList" property="employeeNumber"/></td>
						<td><bean:write name="empList" property="firstName"/></td>
						<td><bean:write name="empList" property="department"/></td>
						<td><bean:write name="empList" property="locationId"/></td>
						<td class="lft">
												<a href="javascript:sendId('<bean:write name="empList" property="employeeNumber"/>')">
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
