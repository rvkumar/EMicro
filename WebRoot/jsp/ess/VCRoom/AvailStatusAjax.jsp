<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
</head>
<body>
<%-- <%
out.println("<b><big>"+request.getAttribute("status")+"</b></big>");
%> --%>
<logic:notEmpty name="resarvedList">
 <table class="bordered">
 <tr>
 <th colspan="8"><center>VC Reserved Rooms</center></th>
 </tr>
 <tr>
 <th>Req.No</th><th>Name</th><th>Location</th><th>Floor</th><th>VC Room</th><th>From Date</th><th>To Date</th><th>Status</th>
 </tr>
 
 <logic:iterate id="c" name="resarvedList">
 <tr>
 <td>${c.reqNo }</td>
 <td>${c.empName }</td>
 <td>${c.location }</td>
  <td>${c.floor }</td>
  <td>${c.roomName }</td>
  <td>${c.fromDate }</td>
  <td>${c.toDate }</td>
  <td>${c.approvalStatus }</td>
 </tr>
 </logic:iterate>
</table>
</logic:notEmpty>
<logic:notEmpty name="NoRecords">
<table class="bordered">
 <tr>
 <th>Req.No</th><th>Name</th><th>Location</th><th>Floor</th><th>VC Room</th><th>From Date</th><th>To Date</th><th>Status</th>
 </tr>
 <tr><td colspan="8"><center><b><font color="green">VC Room Available</font></b></center></td></tr>
 </table>
</logic:notEmpty>


									</body>
									</html>		