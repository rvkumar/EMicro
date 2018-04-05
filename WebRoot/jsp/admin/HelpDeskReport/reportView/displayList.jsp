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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: My Request Display </title>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/sorttable.js"></script>
<script type="text/javascript">	
function nextRecord()
{
     
	document.forms[0].action="helpDeskReport.do?method=next";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="helpDeskReport.do?method=prev";
	document.forms[0].submit();

}
function lastRecord()
{
    
	document.forms[0].action="helpDeskReport.do?method=last";
	document.forms[0].submit();

}
function firstRecord()
{
     
	document.forms[0].action="helpDeskReport.do?method=first";
	document.forms[0].submit();

}

function backdata()
{

document.forms[0].action="helpDeskReport.do?method=displayreport";
document.forms[0].submit();
}

</script>
  </head>
  
  <body>
     <html:form action="/helpDeskReport.do" enctype="multipart/form-data">
  
  <div>
  						 	<html:button property="method"  value="Back" onclick="backdata()" styleClass="rounded" style="width: 100px"></html:button>
  </div>
  <div align="center">
         			<table>
						<tr><td align="center">
							<logic:notEmpty name="displayRecordNo">
	 							<logic:notEmpty name="veryFirst">
	 								&nbsp;<a href="#"><img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/></a>&nbsp;
	 							</logic:notEmpty>
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<a href="#"><img src="images/disableLeft.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="#"><img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="helpdeskReportForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="helpdeskReportForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="#"><img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<a href="#"><img src="images/disableRight.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="atLast">
									&nbsp;<a href="#"><img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/></a>
								</logic:notEmpty>
								<!--<td style="align:right;text-align:center;">
									<img src="images/clear.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('clear');" width="25" height="25" />
									<input type="text" id="searchText" style="padding-top: 3px; width: 200px;" class="rounded" value="Search in MyRequest" onmousedown="this.value='';"/>
									<img src="images/search-bg.jpg" style="vertical-align:middle;" onclick="searchInMyRequest('search')" width="40" height="50" />
								</td>-->
							         
								</logic:notEmpty>
								</td>
							</tr>
						</table>
						</div>
  
  
  <table class="bordered">
					<tr>
						<th colspan="11"><big><center>
						<logic:equal value="In Process" property="reqname" name="helpdeskReportForm">In Process Requests</logic:equal>
						<logic:equal value="Open" property="reqname" name="helpdeskReportForm">Open Requests</logic:equal>
						<logic:equal value="Closed" property="reqname" name="helpdeskReportForm">Closed Requests</logic:equal>
						</center></big></th>
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
						
							<th>View</th>
						</tr>
						
						<logic:notEmpty name="it">
						<logic:iterate name="it" id="abc">
						<tr class="tableOddTR">
							
							<td >${abc.itReqNo }</td>
							<td><bean:write name="abc" property="itReqDate" /></td>
							
							<td> <bean:write name="abc" property="requestType" /></td>
							
			            <td width="10%;">    
			          <logic:equal value="High" name="abc" property="reqPriority">
			            <canvas  width="10" height="15" style="border:0.5px solid #000000;background-color: red;"></canvas>
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
      					
      					
      						
      						<td id="${abc.itReqNo}">
      						
      						<a href="helpDeskReport.do?method=viewrequest&requstNo=${abc.itReqNo}&type=${abc.requestType}&status=${abc.itEngStatus}">
      							<img src="images/view.gif" height="28" width="28" title="View Record"/></a>
      						</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>	
					<logic:notEmpty name="noit">
					
					<tr><td colspan="12"><font color="red"><center> No Records....</font></td></center></tr>
					</logic:notEmpty>
					
						 
					</table>
					
                <html:hidden name="helpdeskReportForm" property="total"/>
 				<html:hidden name="helpdeskReportForm" property="next"/>
 				<html:hidden name="helpdeskReportForm" property="prev"/>
 				
				</html:form>	
  </body>
</html>
