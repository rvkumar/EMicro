<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      
  <script type="text/javascript">
  function statusMessage(message){
alert(message);
}
  
  </script>
</head>


<body>
<html:form action="leave" enctype="multipart/form-data">

<logic:notEmpty name="complist">
<table class="bordered">
<tr>
<th colspan="7">Comp Off Available List  <span style="color: red">(Note: Comp off balance will be deducted as per Priority shown in the list below)</span></th>
</tr>
<tr><th>Priority</th><th>Worked Date</th><th>No. of days</th><th>Comp Off Balance</th><th>LapsBy Date</th></tr>
<%int y=0; %>
<logic:iterate id="a" name="complist">
<%y++; %>
<tr><td><%=y %></td><td>${a.startDate }</td><td>${a.noOfDays }</td><td>${a.compBalance }</td><td>${a.endDate }</td></tr>
</logic:iterate>

</table></logic:notEmpty>

		</html:form>
</body>		

</html>