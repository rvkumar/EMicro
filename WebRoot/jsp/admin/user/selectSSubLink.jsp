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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css/micro_style.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>

<script type="text/javascript">


</script>

</head>
<body>
	<div style="width: 50%">
		 <table class="bordered" width="50%">
	
	<logic:empty name="subLinks"><tr><th colspan="3"><center><font>No Sub link!</font></center></th></tr></logic:empty>
	<logic:notEmpty name="subLinks">
	<tr><th colspan="3"><font><center>Select Sub Links</center></font></th></tr>				
	<bean:define id="gid" property="selectedSubSubModules" name="userRightsForm"/>	
	<logic:iterate id="vvv" name="userRightsForm" property="subSubLinks">
	<tr>
		<td colspan="3"><input class="checkbox" type="checkbox" name="selectedSubSubModulesArr" value="<bean:write name="vvv" property="id"/>" 
			  <%  
			  
			  if(((ArrayList<String>) gid).contains(""+((IdValuePair)vvv).getId())){
			   out.println("checked='checked'");
			  }
			   %>/>&nbsp;<bean:write name="vvv" property="value"/>
		</td>
	</tr>
	</logic:iterate>
	<tr><td colspan="3">
	<html:button property="method" styleClass="rounded"  value="Modify Group" onclick="modifySubGroup();"></html:button>&nbsp;<html:button property="method" styleClass="rounded"  value="Cancel" onclick="closeSubDiv();"></html:button>
	</td></tr>
	</logic:notEmpty>		
	</table>
	</div>
</body>
</html>
