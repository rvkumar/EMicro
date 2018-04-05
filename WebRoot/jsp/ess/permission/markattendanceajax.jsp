<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
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


<logic:notEmpty name="attDataList" >
 <div class="bordered" id="personalInformation"  align="center" width="80%" >
	   
	    	
<div align="center">
<table class="sortable" width="80%" align="center"  >
<tr><th colspan="9"><center>Attendance Details</center></th></tr>
<tr><th><input type="checkbox" name="checkProp" id="r4" onclick="checkAll()"/>  #</th>	<th width="15%"><center>Date</center></th><th width="15%"><center>Day</center></th><th width="10%"><center>In Time</center></th><th width="10%"><center>Out Time</center></th><th width="10%"><center>In Status</center></th><th width="10%"><center>Out Status</center></th><th width="10%"><center>Shift</center></th><th><center>Swipe Type</center></th></tr>
<%int i=1; %>
<logic:iterate id="abc" name="attDataList">

  <input type="hidden" id="reqdate<%=i %>" value="${abc.payGroup }" name="reqdate"></input>

  <input type="hidden" id="iNTIME<%=i %>" value="${abc.iNTIME }" name="iNTIME${abc.payGroup }"></input>
  <input type="hidden" id="oUTTIME<%=i %>" value="${abc.oUTTIME }" name="oUTTIME${abc.payGroup }"></input>
  <input type="hidden" id="iNSTATUS<%=i %>" value="${abc.iNSTATUS}" name="iNSTATUS"></input>
  <input type="hidden" id="oUTSTATUS<%=i %>" value="${abc.oUTSTATUS }" name="oUTSTATUS"></input>
  
<c:choose>
<c:when test="${abc.day=='Sun'}">
<tr style="background-color: #7CB1C9">
	<td><input type="checkbox"   id=a<%=i %>  name="selectedRequestNo" value="${abc.payGroup}" /> </td>
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="shift"/></td>

<td>
<logic:equal value="AA" name="abc" property="iNSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="In" id="ci<%=i %>"> In &nbsp;
</logic:equal>
<logic:equal value="AA" name="abc" property="oUTSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="Out" id="co<%=i %>"> Out
</input></logic:equal>
     </td>
  

</tr>
<% i++; %>
</c:when>

<c:when test="${abc.day=='Sat'}">
<logic:equal value="WO" name="abc" property="iNTIME">
<tr style="background-color: #7CB1C9">
	<td><input type="checkbox"   id=a<%=i %>  name="selectedRequestNo" value="${abc.payGroup}" /> </td>
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td><logic:equal value="AA" name="abc" property="iNSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="In" id="ci<%=i %>"> In &nbsp;
</logic:equal>
<logic:equal value="AA" name="abc" property="oUTSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="Out" id="co<%=i %>"> Out
</input></logic:equal>  </td>
  

</tr>
  <% i++; %>
</logic:equal>
<logic:notEqual value="WO" name="abc" property="iNTIME">
<tr >
	<td><input type="checkbox"   id=a<%=i %>  name="selectedRequestNo" value="${abc.payGroup}" /> </td>
<td><bean:write name="abc" property="date"/></td>
<td ><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td><logic:equal value="AA" name="abc" property="iNSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="In" id="ci<%=i %>"> In &nbsp;
</logic:equal>
<logic:equal value="AA" name="abc" property="oUTSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="Out" id="co<%=i %>"> Out
</input></logic:equal>   </td>
  
 
</tr>
 <% i++; %>
</logic:notEqual>
</c:when>
<c:otherwise>
<logic:empty name="abc" property="message" >
<tr >
	<td><input type="checkbox"   id=a<%=i %>  name="selectedRequestNo" value="${abc.payGroup}" /> </td>
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td><logic:equal value="AA" name="abc" property="iNSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="In" id="ci<%=i %>"> In &nbsp;
</logic:equal>
<logic:equal value="AA" name="abc" property="oUTSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="Out" id="co<%=i %>"> Out
</input></logic:equal>   </td>
</tr>
<% i++; %>
</logic:empty>
<logic:notEmpty name="abc" property="message">
<tr style="background-color: #E1A15D;">
	<td><input type="checkbox"   id=a<%=i %>  name="selectedRequestNo" value="${abc.payGroup}" /> </td>
<td><bean:write name="abc" property="date"/></td>
<td><bean:write name="abc" property="day"/></td>
<td><bean:write name="abc" property="iNTIME"/></td>
<td><bean:write name="abc" property="oUTTIME"/></td>
<td><bean:write name="abc" property="iNSTATUS"/></td>
<td><bean:write name="abc" property="oUTSTATUS"/></td>
<td><bean:write name="abc" property="shift"/></td>
<td>
<logic:equal value="AA" name="abc" property="iNSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="In" id="ci<%=i %>"> In &nbsp;
</logic:equal>
<logic:equal value="AA" name="abc" property="oUTSTATUS">
<input type="checkbox" name="swipecheck${abc.payGroup}" class="minimal-red" value="Out" id="co<%=i %>"> Out
</input></logic:equal>  </td>
</tr>
<% i++; %>
</logic:notEmpty>
</c:otherwise>
</c:choose>

</logic:iterate>
<tr></tr>

</table></div></div>



</logic:notEmpty>
<br/>

</body>		

</html>