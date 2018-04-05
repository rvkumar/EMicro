<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>
	
		<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
</head>
<body background="red">
<div  style="left: 20px;">

<logic:notEmpty name="apsentList">
<table  align="center" class="bordered sortable" style="height: auto;width: 50%;">
<tr>
<th align="center" colspan="4"> 
<center>Leaves&nbsp;List</center></th>
</tr>
<tr>
<th>Name</th><th>No Of Days</th><th>Start Date</th><th>End Date</th>
</tr>   

   <logic:iterate id="mylist" name="apsentList">
   <tr>
<td >
         <font  size="3"> <bean:write name="mylist" property="emp_name"></bean:write></font>  
     </td>
     <td>${mylist.totalDays }</td>
     <td>${mylist.from_date }</td>
     <td>${mylist.to_date }</td>
     
     </tr>
        </logic:iterate>
        </table>
</logic:notEmpty>
<div>&nbsp;</div>
<logic:notEmpty name="ondutyList">
<table  align="center" class="bordered sortable" style="height: auto;width: 45%;">
<tr>
<th align="center" colspan="4"><center> OnDuty List</center></th>
</tr>
<tr>
<th>Name</th><th>Start Date</th><th>End Date</th>
</tr>  
   <logic:iterate id="mylist" name="ondutyList">
   <tr>
<td align="left" bordercolor="">
        
         <font  size="3"> <bean:write name="mylist" property="emp_name"></bean:write></font>  
  </td>
   <td>${mylist.from_date }</td>
     <td>${mylist.to_date }</td>
  </tr>
        </logic:iterate>
        </table>
</logic:notEmpty>
</div>
</body>
</html>