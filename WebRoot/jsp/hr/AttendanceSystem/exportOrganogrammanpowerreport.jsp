

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

<body>


<html:form action="hrApprove" enctype="multipart/form-data">



<br/>
<table class="bordered" border="1">
<tr><th colspan="12"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower Organogram details </b></big>
</center></th></tr>
<tr><th>Sl.No</th><th>Sub-Department/Section</th><th>Approved Strength</th><th>Available Strength</th><th>SR.No</th><th>Emp No</th><th>Emp Name</th><th>Designation</th><th>D.O.J</th><th>Month & Year</th><th>Qualification</th><th>Specialization</th></tr>
<%int i=0; %>
<%int k=0; %>
<logic:iterate id="b" name="deptlist">
<%int j=0; %>
<tr>
<td colspan="11">
<b>Department </b>   :   <bean:write name="b" property="deptTo" /><br/>
</td>
</tr>
<logic:iterate id="a" name="orglist">
<logic:equal value="${a.department}" name="b" property="department">
<%j++; %>
<%i++; %>

<tr>
<td><%=i %></td>
<td><bean:write name="a" property="subdepartment" /></td>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td><%=j %></td>
<td><bean:write name="a" property="employeeno" /></td>
<td><bean:write name="a" property="employeeName" /></td>
<td><bean:write name="a" property="designation" /></td>
<td><bean:write name="a" property="doj" /></td>
<td><bean:write name="a" property="month" /></td>
<td><bean:write name="a" property="qualification" /></td>
<td><bean:write name="a" property="specialization" /></td>
</tr>


</logic:equal>
</logic:iterate>

<logic:iterate id="c" name="def">
<logic:equal value="${c.department}" name="b" property="department">
<%k=k+j; %>
<tr>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">Total</td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="c" property="staffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><%=j %></td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
<td style="background-color: #A9A9A9 ;color: black">&nbsp;</td>
</tr>
</logic:equal>


</logic:iterate>
</logic:iterate>
<tr>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">Grand Total</td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="staffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><%=k %></td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
<td style="background-color:#483D8B ;color: white">&nbsp;</td>
</tr>


</table>



</html:form>
</body>
</html>
