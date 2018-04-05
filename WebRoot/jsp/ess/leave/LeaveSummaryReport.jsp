<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

  <head>
   <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
    <base href="<%=basePath%>">
    
    <title>My JSP 'LeaveReport.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/sorttable.js"></script>
	<link rel="stylesheet" type="text/css" href="css/styles.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
    <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />

	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
function showform()
{


var url="leave.do?method=displayOthersLeaveReport";
document.forms[0].action=url;
document.forms[0].submit();
}

function process()
{


var url="leave.do?method=displaySummaryReport";
document.forms[0].action=url;
document.forms[0].submit();
}

</script>
  </head>
 <body>
   
<html:form action="leave" enctype="multipart/form-data">


<link rel="stylesheet" type="text/css" href="Table.css">
</head>




<table class="bordered">
<tr>
<th><b>Year</b></th>
<td>
<html:select property="empyear" styleClass="content" styleId="filterId" >
<html:options name="leaveForm"  property="yearList" />
</html:select>&nbsp;<html:button property="method" value="Execute" onclick="process()" styleClass="rounded"/>
</td>
</tr></table>



<br>
			          <logic:notEmpty name="addi">
				  <% int i=0;%>
				    <table class="bordered" style="width: 10%">
                 
                     <tr>   <th colspan="22"><center>Leave Balance report for the year-${leaveForm.empyear} </center></th></tr>
                  <tr>
                  
                    
                    <th rowspan="2">#</th>
                  <th rowspan="2">Code</th>
                  <th rowspan="2">Name</th>
                    <th rowspan="2">Dept</th>
                     <th rowspan="2">Desg</th>
                    <th colspan="3">Casual Leave</th>                    
                    <th colspan="3">Sick Leave</th>   
                    <th colspan="3">Earned Leave</th>             
                      <th colspan="3">Total</th>
                  </tr>
                  <tr><th>Op Bal</th>
                  <th>Availed </th>
                  <th>Cl Bal</th>
                  <th>Op Bal</th>
                  <th>Availed </th>
                  <th>Cl Bal</th>
                  <th>Op Bal</th>
                  <th>Availed </th>
                  <th>Cl Balance</th>
                       <th>Op Bal</th>
                  <th>Availed </th>
                  <th>Cl Bal</th>
                  </tr>
                
                   
                  <logic:iterate id="abc1" name="addi">
                   <%i++; %>
           
                <tr>               
                     <td ><%=i%></td>       
                         <td> <bean:write name="abc1" property="employeeno"/></td>    
                         <td> <bean:write name="abc1" property="employeeName"/></td>           
                    <td> <bean:write name="abc1" property="department"/></td>
                        <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="cl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="cl_closingBalence"/></td>
                     <td> <bean:write name="abc1" property="sl_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="sl_closingBalence"/></td>
                       <td> <bean:write name="abc1" property="el_openingBalence"/></td>
                    <td> <bean:write name="abc1" property="el_avalableBalence"/></td>
                    <td> <bean:write name="abc1" property="el_closingBalence"/></td>
                 <td> ${ abc1.el_openingBalence + abc1.cl_openingBalence+abc1.sl_openingBalence}</td>
                    <td> ${ abc1.el_avalableBalence + abc1.cl_avalableBalence+abc1.sl_avalableBalence}</td>
                    <td> ${ abc1.el_closingBalence + abc1.cl_closingBalence+abc1.sl_closingBalence}</td>
              
                  </tr>
                 
				 </logic:iterate>
				 	
				 
				
				<%if(i==0) 
				{
				%>
				<tr><td colspan="23"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
			
				    </logic:notEmpty >
			




</html:form>
  </body>
</html>
