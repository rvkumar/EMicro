<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%--<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>--%>
<%--<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>--%>
<jsp:directive.page import="org.apache.struts.action.ActionForm" />
<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="java.util.ArrayList"/>
<jsp:directive.page import="java.util.Iterator"/>
<jsp:directive.page import="java.util.LinkedHashMap"/>
<jsp:directive.page import="java.util.Set"/>
<jsp:directive.page import="java.util.Map"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: My Request Display </title>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">	
function nextRecord()
{
     
	document.forms[0].action="itHelpdesk.do?method=next";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="itHelpdesk.do?method=prev";
	document.forms[0].submit();

}
function lastRecord()
{
    
	document.forms[0].action="itHelpdesk.do?method=last";
	document.forms[0].submit();

}
function firstRecord()
{
     
	document.forms[0].action="itHelpdesk.do?method=displayMyRequestList";
	document.forms[0].submit();

}

function deletereq(reqno,type)
{
var r=confirm("Are You Sure You Want To Delete The Request");
if (r==true)
  {
 
 
  var URL="itHelpdesk.do?method=deleteItRequest&requstNo="+reqno+"&type="+type+"&reqType="+requestType;
  
		document.forms[0].action=URL;
 		document.forms[0].submit();
  }
else
  {     
   
  } 
}

function showform(){
  if(document.forms[0].issrequestype.value=="")
 {
 
 alert("Please Select RequestType");
 document.forms[0].issrequestype.focus();
      return false;
 }
 
 if(document.getElementById("subfilterID").value=="")
 {
 
 alert("Please Select filter");
 document.forms[0].subfilter.focus();
      return false;
 }

    var URL="itHelpdesk.do?method=displayMyRequestList";
		document.forms[0].action=URL;
 		document.forms[0].submit();
 }
function getSubfilter(filter, status)
{

 var xmlhttp;
var dt;
dt=filter;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    
    document.getElementById("subfiltered").innerHTML=xmlhttp.responseText;
    document.getElementById("subfilterID").value=status;
   document.forms[0].subfilter.value =status; 
    
    }
  }
xmlhttp.open("POST","itHelpdesk.do?method=getsubfilter&linkName="+dt,true);
xmlhttp.send();
}

function setSubfilter(subtittle){
document.forms[0].subfilter.value =subtittle; 
	 
	  
}


function chn(){
 document.getElementById("hiddn").style.visibility="hidden";
	  
}


</script>
  </head>
  
  <body>
     <html:form action="/itHelpdesk.do" enctype="multipart/form-data">
 
<table class="bordered" style="width: 640px; ">
<tr>
<th><b>Request Type</b> <font color="red">*</font></th>
						<td colspan="3" font = "20" style="width: 250px; " ><big>
						<html:select property="issrequestype"  styleId="filterId" onchange="getSubfilter(this.value)" onclick="chn()" >
							<html:option value="">--Select--</html:option>
								<html:option value="Helpdesk Request">New Requirements </html:option>
							<html:option value="It issues">IT  Issues</html:option>						
							<html:option value="Sap issues">SAP Issues</html:option>
							</html:select></big>
						</td>
<th><b>Filter By</b> <font color="red">*</font></th>
						<td colspan="3" font = "20" style="width: 30px; ">
<div id="subfiltered" align="left">
						 <select name="chooseType" id="subfilterID" >
<option value="">--Select---</option>
							
</select>
</div>	


					
</td>





<td><img src="images/search.png" width="26" height="24" onclick="showform()" /></td>

</tr>
</table>  

  <div>&nbsp;</div>
  
  
  
  <div align="center" id="hiddn" >
         			<table>
						<tr align="center"><td >
							<logic:notEmpty name="displayRecordNo">

	  	<td>
	  <a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="itHelpdeskForm"/>-
	
	<bean:write property="endRecord"  name="itHelpdeskForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
	</td>

	</table>
	</logic:notEmpty>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
    <html:hidden property="subfilter"/>
    <html:hidden property="chooseType"/>	

	
	
<br/>
								</td>
							</tr>
						</table>
						</div>
  
  <logic:notEmpty name="it">
  <table class="bordered">
					<tr>
						<th colspan="11"><big><center>My Request</center></big></th>
					</tr>
					</table>
	 <table class="bordered sortable">				

                           
						
						   <tr>
							<th width="3%">Req. No</th>
							<th>Requested On</th>
							<th>Request Type</th>
							
							<th>Priority</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
							<th>Approval Status</th>
							<th width="9%">IT Engineer Status</th>
<!-- 							<th>Edit</th> -->
						
							<th>View</th>
						</tr>
						
						<logic:iterate name="it" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.itReqNo }</td>
							<td><bean:write name="abc" property="itReqDate" /></td>
							
							<td> <bean:write name="abc" property="requestType" /></td>
							
							
			            <td width="10%;">    
			           <logic:equal value="Very High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="abc" property="reqPriority" />
			             </td>
			            
                            <td><bean:write name="abc" property="lastApprover" /></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="reqStatus"/></td>
      						<td><bean:write name="abc" property="itEngStatus"/></td>
<!--       						<td> -->
<!--       						<logic:equal value="In Process" name="abc" property="reqStatus">  -->
<!--       						<a href="itHelpdesk.do?method=editITRequest&requstNo=${abc.itReqNo}&type=${abc.requestType}"><img src="images/edit1.jpg"/></a> -->
<!--       						</logic:equal>&nbsp; -->
<!--       					    </td> -->
      					   
      						
      						<td id="${abc.itReqNo}">
      						
      						<a href="itHelpdesk.do?method=viewrequest&requstNo=${abc.itReqNo}&type=${abc.requestType}&status=${abc.itEngStatus}&issrequestype=Helpdesk Request">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>	
					
<logic:notEmpty name="sapit">
  <table class="bordered">
					<tr>
						<th colspan="11"><big><center>My Request</center></big></th>
					</tr>
					</table>
	 <table class="bordered sortable">				

                           
						
						   <tr>
							<th width="3%">Req. No</th>
							<th>Requested On</th>
							<th>Request Type</th>
								<th>Request Name</th>
							<th>Priority</th>
						    <th>Last Approver</th>
						    <th>Pending Approver</th>
						    <th>Approver Status</th>
<!-- 							<th>Edit</th> -->
						
							<th>View</th>
						</tr>
						
						<logic:iterate name="sapit" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.itReqNo }</td>
							<td><bean:write name="abc" property="itReqDate" /></td>
							
							<td> <bean:write name="abc" property="requestType" /></td>
							<td> <bean:write name="abc" property="requestName" /></td>
			            <td width="10%;">    
			         <logic:equal value="Very High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="abc" property="reqPriority" />
			             </td>
			            
                            <td><bean:write name="abc" property="lastApprover" /></td>
						    <td><bean:write name="abc" property="pendingApprover"/></td>
      						<td><bean:write name="abc" property="reqStatus"/></td>
   
<!--       						<td> -->
<!--       						<logic:equal value="In Process" name="abc" property="reqStatus">  -->
<!--       						<a href="itHelpdesk.do?method=editITRequest&requstNo=${abc.itReqNo}&type=${abc.requestType}"><img src="images/edit1.jpg"/></a> -->
<!--       						</logic:equal>&nbsp; -->
<!--       					    </td> -->
      					
      						
      						<td id="${abc.itReqNo}">
      						
      						<a href="itHelpdesk.do?method=viewrequest&requstNo=${abc.itReqNo}&type=${abc.requestType}&issrequestype=Sap issues">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>	
<logic:notEmpty name="ithelp">
  <table class="bordered">
					<tr>
						<th colspan="11"><big><center>My Request</center></big></th>
					</tr>
					</table>
	 <table class="bordered sortable">				

                           
						
						   <tr>
							<th width="3%">Req. No</th>
							<th>Req.Date</th>
							<th>Location</th>
							<th>Employee name</th>
						    <th>Category</th>
						    <th>Sub Category</th>
						    <th>Description</th>
<!-- 							<th>Edit</th> -->
							<th>Priority</th>
							<th>Status</th>
							<th>Assign To </th>
							
							<th>View</th>
						</tr>
						
<logic:iterate name="ithelp" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.requestNo }</td>
							<td><bean:write name="abc" property="reqDate" /></td>
							
							<td> <bean:write name="abc" property="location" /></td>
							
							<td> <bean:write name="abc" property="employeename" /></td>
							
							<td> <bean:write name="abc" property="category" /></td>
							
							<td> <bean:write name="abc" property="subcategory" /></td>
							
							<td> <bean:write name="abc" property="subject" /></td>
							
			            <td width="10%;">    
			         <logic:equal value="Very High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: red;"></canvas>
			           </logic:equal>
			             <logic:equal value="High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0px solid #000000;background-color: #990033;"></canvas>
			           </logic:equal>
			             <logic:equal value="Low" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: green;"></canvas>
			           </logic:equal>
			             <logic:equal value="Medium" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: orange;"></canvas>
			           </logic:equal>
			             <logic:equal value="Normal" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: brown;"></canvas>
			           </logic:equal>
			            <bean:write name="abc" property="reqPriority" />
			             </td>
			                
                            <td><bean:write name="abc" property="requestStatus" /></td>
						    <td><bean:write name="abc" property="assignTo"/></td>

      					  
      						
      						<td id="${abc.requestNo}">
      						
      						<%-- <a href="itHelpdesk.do?method=viewrequest&requstNo=${abc.requestNo}&type=${abc.category}&issrequestype=It issues">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						 --%>
      						 <logic:notEqual name="abc" property="requestStatus" value="ON_Behalf">
      						<a href="itHelpdesk.do?method=viewrequest&requstNo=${abc.requestNo}&type=${abc.category}&issrequestype=It issues">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</logic:notEqual>
      						<logic:equal name="abc" property="requestStatus" value="ON_Behalf">
      						<a href="itIsssues.do?method=pickuponbehalfSelectedIssue&requstNo=${abc.requestNo}&issrequestype=Helpdesk Request">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</logic:equal>
      						 </td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>	

					<logic:notEmpty name="noit">
					
					<tr><td colspan="12"><font color="red"><center> No Records Found....</font></td></center></tr>
					</logic:notEmpty>
						 
					</table>
					
                <html:hidden name="itHelpdeskForm" property="total"/>
 				<html:hidden name="itHelpdeskForm" property="next"/>
 				<html:hidden name="itHelpdeskForm" property="prev"/>
 				</div>
	
	<script type="">
getSubfilter('<bean:write name="itHelpdeskForm" property="issrequestype"/>','<bean:write name="itHelpdeskForm" property="subfilter"/>');
</script>	
</html:form>	
	
  </body>
</html>
