
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
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

</head>

<body >
<html:form action="paysliprequest" onsubmit="return processrequest()" enctype="multipart/form-data" method="post">

<logic:notEmpty name="Usrreq">
<table border="1" >
<tr><th colspan="9"><center>HR Query Report</center></th></tr>
<tr>
<th>Req&nbsp;No</th><th>Request Date</th><th style="width:100px;">Requested&nbsp;By</th><th>Subject</th><th>Category</th><th>Description</th><th>Status</th>
<th>Updated by</th><th>Comments</th><th>Updated time</th>
</tr>
<logic:iterate id="c" name="Usrreq">
<tr>

<td>${c.req_id}</td>
<td><bean:write name="c" property="req_date"/></td>
<td><bean:write name="c" property="empname"/></td>
<td><bean:write name="c" property="subject"/></td>
<td><bean:write name="c" property="category"/></td>
<td><bean:write name="c" property="description"/></td>
<td><bean:write name="c" property="status"/></td>
<td><bean:write name="c" property="hrName"/></td>
<td><bean:write name="c" property="comments"/></td>
<td><bean:write name="c" property="apprvddate"/></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>


		</html:form>

	</body>
</html>
					
			