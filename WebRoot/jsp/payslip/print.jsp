<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>

<html>
    <head>
        <title>Employee Attendance Details Print</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Expand, contract, animate forms with jQuery wihtout leaving the page" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <meta name="keywords" content="expand, form, css3, jquery, animate, width, height, adapt, unobtrusive javascript"/>
		
		
		

	
	
    
</head>

<body>
    
    <div id="wraper" align="center">
    <html:form action="/payslip.do" enctype="multipart/form-data">
    
    <% 
		                 String status=(String)session.getAttribute("status");		
		             if(status==""||status==null)
		                 {
		
		                    }
		                 else{
		
		                 %>
		                <b><center><font color="red" size="4" ><%=status %></font></center></b>
		                   <%
		                   session.setAttribute("status"," ");
	                       	}
                         %>
    
     <html:hidden property="empcode"/>
    
    
   
    <div id="personalInformation">
    <table border="0" width="100%">
    <tr><td width="100%" align="right"><a href="payslip.do?method=print">Print</a></td></tr>
    </table>
    <table width="100%" border="1" id="mytable1">
<tr class="tablerowdark1">
<td colspan="7" align="center" bgcolor="#51B0F8"><b><font color="white"><b>Payslip</b></font></b></td>
</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">
Company Address
</th>
<td colspan="2" width="50%">
<bean:write property="comaddr" name="payslipForm"/>
</td>

<th colspan="1" width="10%" align="left" style="font-weight: bold" class="specalt" scope="row">
Month
</th>

<td colspan="3" width="40%" align="left">
<bean:write property="paymnt" name="payslipForm"/>
</td>

</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Employee Number</th>
<td colspan="2" align="left" width="32%"><bean:write property="empcod" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Bank Name</th>
<td colspan="3" width="32%"><bean:write property="bnkname" name="payslipForm"/></td>


</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Employee Name</th>
<td colspan="2" align="left" width="32%"><bean:write property="empname" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Bank Acc No.</th>
<td colspan="3" width="32%"><bean:write property="baccno" name="payslipForm"/></td>

</tr>

<tr>

<th colspan="1" align="left" width="18%" rowspan="2" style="font-weight: bold" class="specalt" scope="row">Address</th>
<td colspan="2" align="left" width="32%" rowspan="2"><bean:write property="empaddr" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">PF No.</th>
<td colspan="3" width="32%"><bean:write property="pfno" name="payslipForm"/></td>

</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">ESI No.</th>
<td colspan="3" width="32%"><bean:write property="esino" name="payslipForm"/></td>

</tr>

<%--<tr>

<td colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">ESI No.</td>
<td colspan="3" width="32%">fgh456ghg</td>

</tr>

--%><tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Designation</th>
<td colspan="2" align="left" width="32%"><bean:write property="desg" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">PAN No.</th>
<td colspan="3" width="32%"><bean:write property="panno" name="payslipForm"/></td>

</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Department</th>
<td colspan="2" align="left" width="32%"><bean:write property="dept" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Days Paid</th>
<td colspan="3" width="32%"><bean:write property="daypayd" name="payslipForm"/></td>

</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">DOJ</th>
<td colspan="2" align="left" width="32%"><bean:write property="doj" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Days</th>
<td colspan="1" width="10.6%">Wkg Days- <bean:write property="worgdays" name="payslipForm"/></td>
<td colspan="1" width="10.6%">Days Wkd- <bean:write property="daywrkd" name="payslipForm"/></td>
<td colspan="1" width="10.6%">LOP - <bean:write property="lop" name="payslipForm"/></td>

</tr>

<tr>

<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Reporting Manager</th>
<td colspan="2" align="left" width="32%"><bean:write property="repmng" name="payslipForm"/></td>
<th colspan="1" align="left" width="18%" style="font-weight: bold" class="specalt" scope="row">Leave Balance</th>
<td colspan="1" width="10.6%">EL - <bean:write property="el" name="payslipForm"/></td>
<td colspan="1" width="10.6%">SL - <bean:write property="sl" name="payslipForm"/></td>
<td colspan="1" width="10.6%">CL - <bean:write property="cl" name="payslipForm"/></td>

</tr>

<tr>

<th colspan="1" align="left" width="18%">Earnings</th>
<th colspan="1" align="left" width="16%">Arrears</th>
<th colspan="1" align="left" width="16%">Current</th>
<th colspan="1" width="18.8%" align="left">Deductions</th>
<th colspan="1" width="10.6%" align="left">Arrears</th>
<th colspan="1" width="10.6%" align="left">Current</th>
<th colspan="1" width="10.6%" align="left">Net</th>

</tr>

<%--    For Loop Starts from here    --%>


<tr>
<td colspan="3">
<table width="100%">

<logic:notEmpty name="earnings">
<logic:iterate id="recordse" name="array1">
<tr>
<td colspan="1" align="left" width="18%">
<bean:write property="eh" name="recordse"/>
</td>
<td colspan="1" align="left" width="16%">
<bean:write property="ea" name="recordse"/>
</td>
<td colspan="1" align="left" width="16%">
<bean:write property="ec" name="recordse"/>
</td>

</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</td>

<td colspan="3">
<table width="100%">
<logic:notEmpty name="deduction">
<logic:iterate id="records2" name="array2">
<tr>
<td colspan="1" align="left" width="18.8%">
<bean:write property="dh" name="records2"/>
</td>
<td colspan="1.5" align="left" width="10.6%">
<bean:write property="da" name="records2"/>
</td>
<td colspan="1.5" align="left" width="10.6%">
<bean:write property="dc" name="records2"/>
</td>

</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</td>
<td rowspan="10" colspan="1"><bean:write property="netpay" name="payslipForm"/></td>
</tr>




<%--    For Loop Ends here    --%>

<tr>

<th colspan="1" align="left" width="18%">Total Earnings</th>
<td colspan="1" align="left" width="16%"><bean:write property="arrears_totEarn" name="payslipForm"/></td>
<td colspan="1" align="left" width="16%"><bean:write property="current_totEarn" name="payslipForm"/></td>
<th colspan="1" align="left" width="18.8%">Total Deductions</th>
<td colspan="1.5" align="left" width="10.6%"><bean:write property="arrears_totDeduc" name="payslipForm"/></td>
<td colspan="1.5" align="left" width="10.6%"><bean:write property="current_totDeduc" name="payslipForm"/></td>

</tr>





</table>

    </div>

	</html:form>
	</div>
    </body>
</html>