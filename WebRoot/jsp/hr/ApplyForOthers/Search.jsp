<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
       <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>
  <script type="text/javascript">
  function applyReqrequest(){
  var reqType=document.forms[0].requiredType.value;
  
  if(document.forms[0].requiredType.value==""){
  alert("Please Select Type");
  document.forms[0].requiredType.focus();
  return false;
  }
  if(document.forms[0].empNumber.value==""){
  alert("Please Enter Employee Number ");
  document.forms[0].empNumber.focus();
  return false;
  }
  if(reqType=='Leave'){
  var url="hrLeave.do?method=newLeaveRequest";
			document.forms[0].action=url;
			document.forms[0].submit();
  
  }
  if(reqType=='OnDuty'){
   var url="hrLeave.do?method=applyOnduty";
			document.forms[0].action=url;
			document.forms[0].submit();
  }
  if(reqType=='Permission'){
   var url="hrLeave.do?method=permission";
			document.forms[0].action=url;
			document.forms[0].submit();
  }
  if(reqType=='Comp-off'){
   var url="hrLeave.do?method=displaynewcomp";
			document.forms[0].action=url;
			document.forms[0].submit();
  }
  if(reqType=='OT'){
   var url="hrLeave.do?method=displaynewOT";
			document.forms[0].action=url;
			document.forms[0].submit();
  }
}
  
  </script>    
</head>

<body>
<html:form action="hrLeave">
<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="hrLeaveForm" property="message">
				<font color="green" size="3"><b><bean:write name="hrLeaveForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="hrLeaveForm" property="empStatus">
				<font color="red" size="3"><b><bean:write name="hrLeaveForm" property="empStatus" /></b></font>
				
			</logic:present>
			
			<logic:present name="hrLeaveForm" property="message2">
				<font color="red" size="3"><b><bean:write name="hrLeaveForm" property="message2" /></b></font>
				
			</logic:present>
		</div>
<table class="bordered">
<tr>
<th>Type<font color="red" size="3">*</font></th><td><html:select property="requiredType">
<html:option value="">----Select---</html:option>
<html:option value="Leave">Leave</html:option>
<html:option value="OnDuty">On Duty</html:option>
<html:option value="Permission">Permission</html:option>
<html:option value="Comp-off">Comp-off</html:option>
<html:option value="OT">Overtime</html:option>
</html:select></td>
<th>Employee Number<font color="red" size="3">*</font></th><td><html:text property="empNumber"/></td>
<td><html:button property="method" value="Submit" onclick="applyReqrequest()" styleClass="rounded"></html:button>
</tr>
</table>
</html:form>
</body>
</html>