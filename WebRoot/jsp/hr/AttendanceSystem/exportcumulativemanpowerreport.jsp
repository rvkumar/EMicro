

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
<table border="0" cellpadding=2 cellspacing=2 width="100%"  style="border: 0px">
<tr><td colspan="15"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower Cumulative details for the Month : <bean:write property="month" name="hrApprovalForm"></bean:write>&nbsp;&nbsp;<bean:write property="year" name="hrApprovalForm"></bean:write>
</td></tr></center>
</table>
<br></br>

<table class="bordered" border="1">
<tr><th rowspan="3">Department</th><th colspan="3">Approved Manpower</th><th colspan="3">Existing Manpower</th><th colspan="2">Additions of the Month</th><th colspan="4">Attritions of the Month</th><th colspan="4">Cumulative Attritions from April to <bean:write property="frommonth" name="hrApprovalForm"></bean:write>&nbsp;&nbsp;<bean:write property="year" name="hrApprovalForm"></bean:write></th><th rowspan="3">Total Manpower Available</th></tr>
<tr><th rowspan="2">ST</th><th rowspan="2">RW</th><th rowspan="2">CN</th><th rowspan="2">ST</th><th rowspan="2">RW</th><th rowspan="2">CN</th><th rowspan="2">ST</th><th rowspan="2">RW</th><th colspan="2">ST</th><th colspan="2">RW</th><th colspan="2">ST</th><th colspan="2">RW</th></tr>
<tr><th>No.</th><th>%</th><th>No.</th><th>%</th><th>No.</th><th>%</th><th>No.</th><th>%</th></tr>
<logic:iterate id="a" name="l1">
<tr>
<td>${a.department}</td>
<td>${a.staffapprstrength}</td>
<td>${a.techstaffapprstrength}</td>
<td>${a.contractapprstrength}</td>
<td>${a.staffavailstrength}</td>
<td>${a.techstaffavailstrength}</td>
<td>${a.contractavailstrength}</td>
<td>${a.staffadditioncount}</td>
<td>${a.techstaffadditioncount}</td>
<td>${a.staffattritioncount}</td>
<td>${a.staffattritioncountpercent}</td>
<td>${a.techstaffattritioncount}</td>
<td>${a.techstaffattritioncountpercent}</td>
<td>${a.cumstaffattritioncount}</td>
<td>${a.cumstaffattritioncountpercent}</td>
<td>${a.cumtechstaffattritioncount}</td>
<td>${a.cumtechstaffattritioncountpercent}</td>
<td>${a.total}</td>
</tr>
</logic:iterate>

<tr>
<td  style="background-color:#483D8B ;color: white"><center><b>Total</b></center>	</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffapprstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffapprstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncontractapprstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffavailstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffavailstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncontractavailstrength}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffadditioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffadditioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntechstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumtechstaffattritioncount}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grncumtechstaffattritioncountpercent}</td>
<td style="background-color:#483D8B ;color: white">${hrApprovalForm.grntotpresent}</td>
</tr>
</table>
</html:form>
</body>
</html>
