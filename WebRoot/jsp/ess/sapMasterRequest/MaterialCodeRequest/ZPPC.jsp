<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>

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
<title>eMicro :: Semi Finished Creation</title>

	<style>
		input:focus { 
		    outline:none;
		    border-color:#2ECCFA;
		    box-shadow:0 0 10px #2ECCFA;
		}
		select:focus { 
		    outline:none;
		    border-color:#2ECCFA;
		    box-shadow:0 0 10px #2ECCFA;
		}
	</style>

	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script type="text/javascript">

function changeStatus(elem){
  
	var elemValue = elem.value;
	if(elem=="Reject")
	{
	if(document.forms[0].comments.value!=""){
	  alert("Please Add Some Comments");
	  }
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup=document.forms[0].reqMaterialGroup.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back(-2);
  }
  
 function getCurrentRecord(){

 
	
	
	var url="materialCode.do?method=curentRecord";
	
	document.forms[0].action=url;
	document.forms[0].submit();


}
</script>
</head>
<body style="text-transform:uppercase;">
<html:form action="/materialCode.do" enctype="multipart/form-data">
<logic:iterate id="approvalsForm" name="prodetails">

<table class="bordered">
<tr>
<th colspan="4" align="center"><big><center>ZPPC-Promotional Material</center> </big></th>
</tr>
				<tr>
					<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>
   				<tr>	  
	 				<td>Request No <font color="red">*</font></td>
					<td align="left">
					<html:hidden property="requestNumber"/>
						<bean:write name="approvalsForm" property="requestNumber"/>
						
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td align="left">
					<bean:write name="approvalsForm" property="requestDate"/>
					</td>
				</tr>
				<tr>
				<td>Location <font color="red">*</font></td>
				<td>
					<html:hidden property="locationId"/>
			<bean:write name="approvalsForm" property="locationId"/>
							</td>

<%--				<td>Material Type <font color="red">*</font></td>--%>
<%--				<td><html:select  property="materialTypeId" disabled="true">--%>
<%--					<html:option value="">Select</html:option>--%>
<%--					<html:options name="promotionalForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				</td>--%>
			
				<td>Storage&nbsp;Location <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="storageLocationId"/>
				
				</td>
			</tr>
			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3">
				<bean:write name="approvalsForm" property="materialShortName"/>
				</td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3" >
				<bean:write name="approvalsForm" property="materialLongName"/>
				</td>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="materialGroupId"/>
				
			
				</td>
				<td>Purchase&nbsp;Group <font color="red">*</font></td>
				<td>
								<bean:write name="approvalsForm" property="puchaseGroupId"/>
				</td>
			</tr>
	<tr>
				<td>Brand <font color="red">*</font></td>
				<td colspan="3">
							<bean:write name="approvalsForm" property="brandID"/>
				
				</td>
				
			</tr>
			<tr>
				<td>U O M <font color="red">*</font></td>
				<td >
							<bean:write name="approvalsForm" property="unitOfMeasId"/>
				
				</td>
				<td>Division <font color="red">*</font></td>
				<td>											
					<bean:write name="approvalsForm" property="divisionId"/>

				</td>
			</tr>

			<tr>
	  			<th colspan="8"><big>Other Details</big></th>
   			</tr>
		   <tr>
		   <td>
		   Is Asset <font color="red">*</font>
		   </td>
		   <td>
		  					<bean:write name="approvalsForm" property="isAsset"/>
		  
					</td>
				<td>Purpose <font color="red">*</font></td>
				<td>		  				
					<bean:write name="approvalsForm" property="purposeID"/>

					</td>
					<tr>
				<td>
				Is&nbsp;SAS&nbsp;Form&nbsp;Available <font color="red">*</font>
				</td>
				<td colspan="3">
					<bean:write name="approvalsForm" property="isSASFormAvailable"/>

				</td>
			</tr>

			<tr>
				<td>Approximate Value <font color="red">*</font></td>
				<td>
									<bean:write name="approvalsForm" property="approximateValue"/>
				</td>
				<td>Valuation&nbsp;Class <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="valuationClass"/>

				</td>
			</tr>

				
			<tr>
				<td>Justification <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="detailedJustification"/>
				
				</td>
				<td>Specification <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="detailedSpecification"/>

				</td>
			</tr>
			<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
				
	 		
		<tr>			
	<th colspan="6">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<img src="images/star.gif" width="8" height="8" /></td>
				<td>
				<bean:write name="approvalsForm" property="sapCodeExists"/>
				</td>
		
			<td >SAP Code No<img src="images/star.gif" width="8" height="8" /></td>
				<td colspan="4"><bean:write name="approvalsForm" property="sapCodeNo"/>	</td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<bean:write name="approvalsForm" property="sapCreationDate"/>
				
			</td>
			
				<td >Code Created By<img src="images/star.gif" width="8" height="8" /></td><td colspan="4">
				<bean:write name="approvalsForm" property="sapCreatedBy"/>
			
				</td>
			</tr>
						
			
					<tr>
						<td colspan="6">
							<html:button property="method" value="Close" onclick="getCurrentRecord()" styleClass="rounded" style="width: 100px" ></html:button>
						</td>
					</tr>
				</table> 
		</div>
		</div>
		</logic:iterate>

<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td></tr>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;		
			<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
	<html:hidden property="locationId" />
	<html:hidden property="approveStatus"/>
	<html:hidden property="fromDate"/>
	<html:hidden property="toDate"/>
	<html:hidden property="materialCodeLists"/>
</html:form>

</div>
</body>
</html>			