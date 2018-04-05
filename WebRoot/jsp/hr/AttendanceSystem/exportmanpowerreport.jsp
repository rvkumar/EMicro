

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


<logic:notEmpty name="man">
<table border="0" cellpadding=2 cellspacing=2 width="100%"  style="border: 0px">
<tr><td colspan="15"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower details from : <bean:write property="fromDate" name="hrApprovalForm"></bean:write>&nbsp;To&nbsp;<bean:write property="toDate" name="hrApprovalForm"></bean:write>
</td></tr></center>
</table>
<br></br>


<div>
<table class="bordered" border="1">
<tr>
<th rowspan="2"><center>Sl.No</center></th ><th rowspan="2"><center>Location</center></th><th rowspan="2"><center>Department</center></th><th colspan="5"><center>Staff</center></th><th colspan="5"><center>Technical Staff</center></th><th colspan="5"><center>Staff Apprentice</center></th><th rowspan="2"><center>Contract Approved Strength</center></th >
<logic:iterate id="cat" name="categorylist">
<th><center><bean:write property="repgrp" name="cat"></bean:write></center></th>
</logic:iterate>



<th rowspan="2"><center>Total Contract Present</center></th ><th rowspan="2"><center>Total Contract Salary</center></th><th colspan="3"><center>House Keeping</center></th><th rowspan="2"><center>Total  Present</center></th ><th rowspan="2"><center>Total  Salary</center></th><th colspan="3"><center>STAFF,TECHNICAL STAFF,STAFF APPRENTICE</center></th>
</tr>
<tr>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<logic:iterate id="cat" name="categorylist">
<th rowspan="1"><center>Present</center></th >
</logic:iterate>
<th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Salary</center></th>
</tr>
<% int i=0;%>

<logic:iterate id="a" name="man">


<logic:equal value="Sub" name="a" property="locationId">

<tr>

<td style="background-color: #A9A9A9 ;color: black" colspan="3"><center><bean:write name="a" property="department" /></center></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="staffsalary" /></td>


<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="techstaffsalary" /></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffavailstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="apprenstaffsalary" /></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="contractapprstrength" /></td>

<logic:notEmpty name="skilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="skilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="skilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="skilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="unskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="unskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="semiskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="semiskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="securitypresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="securityabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="projectspresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="projectsabsent" /></td>
</logic:notEmpty>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totcontractpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totcontractabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totcontractSalary" /></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="houseapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="housepresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="houseabsent" /></td>
<%-- <td style="background-color: #A9A9A9 ;color: black">${a.houseapprstrength-a.housepresent}</td> --%>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="housesalary" /></td>


<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totpresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totabsent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="totsalary" /></td>

<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threeapprstrength" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threepresent" /></td>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threeabsent" /></td>
<%-- <td style="background-color: #A9A9A9 ;color: black">${a.threeapprstrength-a.threepresent}</td> --%>
<td style="background-color: #A9A9A9 ;color: black"><bean:write name="a" property="threesalary" /></td>

</tr>
</logic:equal>

<logic:notEqual value="Sub" name="a" property="locationId">
<%i++; %>
<tr>
<td><%=i %></td>
<td><bean:write name="a" property="locationId" /></td>
<td><bean:write name="a" property="department" /></td>

<td><bean:write name="a" property="staffapprstrength" /></td>
<td><bean:write name="a" property="staffavailstrength" /></td>
<td><bean:write name="a" property="staffpresent" /></td>
<td><bean:write name="a" property="staffabsent" /></td>
<td><bean:write name="a" property="staffsalary" /></td>


<td><bean:write name="a" property="techstaffapprstrength" /></td>
<td><bean:write name="a" property="techstaffavailstrength" /></td>
<td><bean:write name="a" property="techstaffpresent" /></td>
<td><bean:write name="a" property="techstaffabsent" /></td>
<td><bean:write name="a" property="techstaffsalary" /></td>

<td><bean:write name="a" property="apprenstaffapprstrength" /></td>
<td><bean:write name="a" property="apprenstaffavailstrength" /></td>
<td><bean:write name="a" property="apprenstaffpresent" /></td>
<td><bean:write name="a" property="apprenstaffabsent" /></td>
<td><bean:write name="a" property="apprenstaffsalary" /></td>

<td><bean:write name="a" property="contractapprstrength" /></td>

<logic:notEmpty name="skilled">
<td><bean:write name="a" property="skilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="skilled">
<td><bean:write name="a" property="skilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td><bean:write name="a" property="unskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td><bean:write name="a" property="unskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td><bean:write name="a" property="semiskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td><bean:write name="a" property="semiskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td><bean:write name="a" property="securitypresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td><bean:write name="a" property="securityabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td><bean:write name="a" property="projectspresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td><bean:write name="a" property="projectsabsent" /></td>
</logic:notEmpty>

<td><bean:write name="a" property="totcontractpresent" /></td>
<td><bean:write name="a" property="totcontractabsent" /></td>
<td><bean:write name="a" property="totcontractSalary" /></td>

<td><bean:write name="a" property="houseapprstrength" /></td>
<td><bean:write name="a" property="housepresent" /></td>
<td><bean:write name="a" property="houseabsent" /></td>
<%-- <td>${a.houseapprstrength-a.housepresent}</td> --%>
<td><bean:write name="a" property="housesalary" /></td>


<td><bean:write name="a" property="totpresent" /></td>
<td><bean:write name="a" property="totabsent" /></td>
<td><bean:write name="a" property="totsalary" /></td>

<td><bean:write name="a" property="threeapprstrength" /></td>
<td><bean:write name="a" property="threepresent" /></td>
<td><bean:write name="a" property="threeabsent" /></td>
<%-- <td>${a.threeapprstrength-a.threepresent}</td> --%>
<td><bean:write name="a" property="threesalary" /></td>

</tr>
</logic:notEqual>

</logic:iterate>
<tr ><td colspan="3" style="background-color:#483D8B ;color: white"><strong><b><center>Grand Total:</center></b></strong></td>



<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffabsent" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnstaffsalary" /></td>


<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffabsent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffsalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffavailstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffabsent" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntechstaffsalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnapprenstaffsalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grncontractapprstrength" /></td>

<logic:notEmpty name="skilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="skilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnunskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="unskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnunskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsemiskilledpresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="semiskilled">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsemiskilledabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsecuritypresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="g4s">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnsecurityabsent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnprojectspresent" /></td>
</logic:notEmpty>
<logic:notEmpty name="project">
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnprojectsabsent" /></td>
</logic:notEmpty>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotcontractpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotcontractabsent" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotcontractSalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhouseapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhousepresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhouseabsent" /></td>

<%-- <td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnhouseapprstrength-hrApprovalForm.grnhousepresent}</td> --%>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnhousesalary" /></td>


<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotpresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotabsent" /></td>


<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grntotsalary" /></td>

<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreeapprstrength" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreepresent" /></td>
<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreeabsent" /></td>

<%-- <td style="background-color:#483D8B ;color: white">${hrApprovalForm.grnthreeapprstrength-hrApprovalForm.grnthreepresent}</td> --%>


<td style="background-color:#483D8B ;color: white"><bean:write name="hrApprovalForm" property="grnthreesalary" /></td>

</tr>



</table>



</div>
</logic:notEmpty>

<logic:notEmpty name="nolist">

<div>
<table class="bordered">
<tr>
<th rowspan="2"><center>Sl.No</center></th ><th rowspan="2"><center>Location</center></th><th rowspan="2"><center>Department</center></th><th colspan="5"><center>Staff</center></th><th colspan="5"><center>Technical Staff</center></th><th colspan="5"><center>Staff Apprentice</center></th><th rowspan="2"><center>Contract Approved Strength</center></th >
<th rowspan="1" colspan="2"><center>CKP - SKILLED</center></th  >
<th rowspan="1" colspan="2"><center>CKP - UNSKILLED</center></th  >
<th rowspan="1" colspan="2"><center>CKP - SEMISKILLED</center></th  >
<th rowspan="1" colspan="2"><center>SECURITY</center></th  >
<th rowspan="1" colspan="2"><center>Projects</center></th  >
<th rowspan="2" ><center>Total Contract Present</center></th ><th rowspan="2" ><center>Total Contract Absent</center></th ><th rowspan="2"><center>Total Contract Salary</center></th><th colspan="4"><center>House Keeping</center></th><th rowspan="2"><center>Total  Present</center></th ><th rowspan="2"><center>Total Absent</center></th ><th rowspan="2"><center>Total  Salary</center></th><th colspan="4"><center>STAFF,TECHNICAL STAFF,STAFF APPRENTICE</center></th>
</tr>
<tr>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Absent</center></th  >
<%-- <th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Present</center></th  >
<th rowspan="1"><center>Present</center></th  > --%>
<th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
<th><center>Approved Strength</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>Salary</center></th>
</tr>
<tr>
<td colspan="31"><center>Currently details are not available to display</center></td>
</tr>
</table></div>
</logic:notEmpty>

<logic:notEmpty name="depman">
<table class="bordered" border="1">
<tr><th colspan="8"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/>:<bean:write name="hrApprovalForm" property="paygrp"/><br/>Manpower details on : <bean:write property="fromDate" name="hrApprovalForm"></bean:write>
</th></tr>
<tr><th rowspan="2"><center>Sl.No<center></th><th rowspan="2"><center>Department<center></th><th colspan="3"><center>Factory Staff<center></th><th colspan="3"><center>OP.STAFF/WORKERS<center></th><th colspan="3"><center>Total<center></th></tr>
<tr><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th></tr>
<%int i=0;%>
<logic:iterate id="a" name="depman">
<%i++; %>
<logic:equal value="" name="a" property="department">
<tr><td colspan="2" style="background-color:#483D8B ;color: white"><center>Total</center></td><td style="background-color:#483D8B ;color: white">${a.staffavailstrength }</td>
<td style="background-color:#483D8B ;color: white">${a.staffpresent }</td>
<td style="background-color:#483D8B ;color: white">${a.staffavailstrength - a.staffpresent}</td>
<td style="background-color:#483D8B ;color: white">${a.techstaffavailstrength }</td><td style="background-color:#483D8B ;color: white">${a.techstaffpresent }</td><td style="background-color:#483D8B ;color: white">${a.techstaffavailstrength - a.techstaffpresent}</td><td style="background-color:#483D8B ;color: white">${a.totalRecords }</td><td style="background-color:#483D8B ;color: white">${a.totpresent }</td><td style="background-color:#483D8B ;color: white">${a.totalRecords -a.totpresent}</td>
</tr>
</logic:equal>
<logic:notEqual value="" name="a" property="department">
<tr><td><%=i %></td><td>${a.department }</td><td>${a.staffavailstrength }</td><td>${a.staffpresent }</td><td>${a.staffavailstrength - a.staffpresent}</td>
<td>${a.techstaffavailstrength }</td><td>${a.techstaffpresent }</td><td>${a.techstaffavailstrength - a.techstaffpresent }</td><td>${a.totalRecords }</td><td>${a.totpresent }</td><td>${a.totalRecords  - a.totpresent }</td>
</tr>
</logic:notEqual>

</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="nodepman">
<table class="bordered" border="1">
<tr><th colspan="8"><center><big><b>MICRO LABS LIMITED ,<bean:write name="hrApprovalForm" property="locationId"/><br/>Manpower details on : <bean:write property="fromDate" name="hrApprovalForm"></bean:write>
</th></tr>
<tr><th rowspan="2"><center>Sl.No<center></th><th rowspan="2"><center>Department<center></th><th colspan="3"><center>Factory Staff<center></th><th colspan="3"><center>OP.STAFF/WORKERS<center></th><th colspan="3"><center>Total<center></th></tr>
<tr><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th><th><center>ON Roll</center></th><th><center>Present</center></th><th><center>Absent</center></th></tr>

<tr>
<td colspan="31"><center>Currently details are not available to display</center></td>
</tr>
</table>
</logic:notEmpty>

</html:form>
</body>
</html>
