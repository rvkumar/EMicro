<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
</head>
<body>


<select name="subLinkName" id="subLinkNameID" onchange="getSubSublinks(this.value)">
<option value="">--Select---</option>
									<logic:iterate id="xxx" name="subLinkValueListID">
									<option value='<bean:write name="xxx"/>'><bean:write name="xxx"/></option>
									</logic:iterate>
									</select>
								
									</body>
									</html>