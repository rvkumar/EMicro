<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
 <style>
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
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript">

 function serarchLocation(){
	 
	 var url="conference.do?method=searchRoomsList";
		document.forms[0].action=url;
		document.forms[0].submit();
 }
 function clearSearch(){
	 
	 var url="conference.do?method=roomsList";
		document.forms[0].action=url;
		document.forms[0].submit();
 }

</script>
 
 </head>
 <body>
 <html:form action="conference" enctype="multipart/form-data">
  <div style="width: 70%;">
 <table class="bordered" >
 <tr>
 <th>Location&nbsp;</th><td><html:select  property="locationId" >
		<html:option value="">--Select--</html:option>
		<html:options  property="locationIdList" labelProperty="locationLabelList"/>
	</html:select>&nbsp;&nbsp;&nbsp;<a href="#"><img src="images/search.png" align="absmiddle" onclick="serarchLocation()"/></a>&nbsp;
	<a href="#"><img src="images/clearsearch.jpg" align="absmiddle" onclick="clearSearch()"/></a>
</td>
 </tr>
 </table>
 </div>
 <div>&nbsp;</div>
 <div style="width: 70%;">
 
 <table class="bordered" >
 <tr><th colspan="8"><center>Available Conference Rooms</center> </th></tr>
 <tr>
 <th>Location</th><th>Floor</th><th>Conf.Name</th>
 </tr> 
 <logic:notEmpty name="confList">
 <logic:iterate id="c" name="confList">
 <tr>
 <td>${c.locationId }</td><td>${c.floor }</td><td>${c.roomName }</td>
  </tr> 
 
 </tr>
 </logic:iterate>
 </logic:notEmpty>
 <logic:notEmpty name="conferenceForm" property="message2">
 <tr>
 <td colspan="3"><font color="red"><center><bean:write name="conferenceForm" property="message2"/></center></font> </td>
 </tr>
 </logic:notEmpty>
 </table>
 </div>
 </html:form>
 </body>
 </html>