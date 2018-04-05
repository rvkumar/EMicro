
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <title>eMicro :: Pay Slip</title>
       <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />

  <style type="text/css">
  
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<body >
<html:form action="/payslip.do" enctype="multipart/form-data">
<table  style="width: 100%;">
	<tr>
		<td ><img src="images/MLLogo.png" height="80" width="110"></td>
		<td><b>Micro Labs Limited</b><br/>#27,Race Course Road, Bangalore-560001 </td>
		<td ><b>Salary Slip - </b></td>
	</tr>
</table>
</html:form>
</body>
</html>