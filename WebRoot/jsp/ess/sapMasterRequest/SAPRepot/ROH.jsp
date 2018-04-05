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
<body >
	<html:form action="/materialCode.do" enctype="multipart/form-data">
	            <logic:iterate id="approvalsForm" name="rawdetails">
	
<div style="width: 70%; " align="center">	

<table class="bordered" style="float: right;" >

<tr>
<th colspan="4" align="center"><big><center>ROH-Raw Material</center> </big></th>
</tr>
				<tr>
					<th colspan="4"><big>Basic Details Of Material</big></th>
   				</tr>
				<tr>	  
	 				<td>Request No <font color="red">*</font></td>
					<td align="left">
					
						<bean:write name="approvalsForm" property="requestNumber"/>
						
					</td>
					<td>Request Date <font color="red">*</font></td>
					<td align="left">
					<bean:write name="approvalsForm" property="requestDate"/>
					</td>
				</tr>
				<tr>
					<td>Location <font color="red">*</font></td>
					<td align="left">
					
						<bean:write name="approvalsForm" property="locationId"/>
					</td>
					<td>Storage Location <font color="red">*</font></td>
					<td>
					<bean:write name="approvalsForm" property="storageLocationId"/>
					</td>

<%--				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" />--%>
<%--				</th>--%>
<%--				<td align="left">--%>
<%--				<html:select name="approvalsForm" property="materialTypeId" disabled="true" styleClass="text_field" style="width:100px;">--%>
<%--					<html:option value="">--Select--</html:option>--%>
<%--					<html:options name="approvalsForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
                 
				</tr>
				<html:hidden property="locationId"/>
				<tr>
					<td>Short Name <font color="red">*</font></td>
					<td colspan="3">
					<bean:write name="approvalsForm" property="materialShortName"/>
					</td>
				</tr>
				<tr>
					<td>Long Name <font color="red">*</font></td>
					<td colspan="3">
					<bean:write name="approvalsForm" property="materialLongName"/>
					</td>
				</tr>
				<tr>
					<td>Mat.Group <font color="red">*</font></td>
					<td align="left">
				
					<bean:write name="approvalsForm" property="materialGroupId"/>
				
					</td>
					<td>U O M <font color="red">*</font></td>
					<td>
					<bean:write name="approvalsForm" property="unitOfMeasId"/>
					</td>
				</tr>
				<tr>
					<td>Purchasing Group <font color="red">*</font></td>
					<td colspan="3">
					<bean:write name="approvalsForm" property="puchaseGroupId"/>
					</td>
				</tr>
				<tr>
	 				<th colspan="4"><big>Quality Specification</big></th>
   				</tr>
				<tr>
					<td>Pharmacopoeial Name <font color="red">*</font></td>
					<td colspan="3">
					<bean:write name="approvalsForm" property="pharmacopName"/>
					</td>
				</tr>
				<tr>
					<td>Pharmacopoeial Grade <font color="red">*</font></td>
					<td align="left" colspan="3">
					<bean:write name="approvalsForm" property="pharmacopGrade"/>
					</td>
			</tr>
			<tr>
				<td>Generic Name <font color="red">*</font></td>
				<td colspan="3">
				<bean:write name="approvalsForm" property="genericName"/>
				</td>
			</tr>
			<tr>
				<td>Synonym <font color="red">*</font></td>
				<td colspan="3">
				<bean:write name="approvalsForm" property="synonym"/>
				</td>
			</tr>
			<tr>
				<td>Pharmacopoeial&nbsp;Specification<font color="red">*</font></td>
				<td colspan="3">
				<bean:write name="approvalsForm" property="pharmacopSpecification"/>
				</td>
			</tr>
			<tr>
				<td>Duty Element <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="dutyElement"/>
				</td>
				<td>Is DMF Material <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="isDMFMaterial"/>
				</td>
			</tr>
			<tr>
				<td>DMF Grade  
					<div id="im" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td align="left">
				<bean:write name="approvalsForm" property="dmfGradeId"/>
				</td>
				<td>Material Grade
					<div id="im1" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td>
				<bean:write name="approvalsForm" property="materialGrade"/>
				</td>
			</tr>
			<tr>
				<td>COS Grade No
					<div id="im2" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td>
				<bean:write name="approvalsForm" property="cosGradeNo"/>
				</td>
				<td>Additional Test</td>
				<td>
				<bean:write name="approvalsForm" property="additionalTest"/>
				</td>
			</tr>
			<tr>
	 			<th colspan="4"><big>Vendor / Manufacture Information</big></th>
   			</tr>
			<tr>
				<td>Is&nbsp;Material&nbsp;is<br/> Supplier/Manufacture/Site Specific <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="isVendorSpecificMaterial"/>
				</td>
				<td>Manufacture Name
					<div id="isVendor1" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td>
				<bean:write name="approvalsForm" property="mfgrName"/>
				</td>
			</tr>
			<tr>
				<td>Site Of Manufacture
					<div id="isVendor2" style="visibility: hidden"> <font color="red">*</font></div>
				</td>
				<td>
				<bean:write name="approvalsForm" property="siteOfManufacture"/>
				</td>
				<td>Country <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="countryId"/>
				</td>
			</tr>
			<tr>
				<td>Customer Name <font color="red">*</font></td>
				<td colspan="3">
				<bean:write name="approvalsForm" property="customerName"/>
				</td>
			</tr>
			<tr>
				<td>To Be Used In Product (S) <font color="red">*</font></td>
				<td colspan="3">
				<bean:write name="approvalsForm" property="toBeUsedInProducts"/>
				</td>
			</tr>
			<tr>
				<th colspan="4"><big>Other Details</big></th>
	   		</tr>
	   		<tr>
				<td>Temp.Condition</td>
				<td >
				<bean:write name="approvalsForm" property="tempCondition"/>
				</td>
				
				<td>Storage&nbsp;Condition</td>
				<td >
				<bean:write name="approvalsForm" property="storageCondition"/>
				</td>
			</tr>
			<tr>
				<td>Shelf Life <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="shelfLife"/>
				<bean:write name="approvalsForm" property="shelfLifeType"/>
				</td>
				<td>Retest Days <font color="red">*</font></td>
				<td colspan="2">
				<bean:write name="approvalsForm" property="retestDays"/>
					
					<bean:write name="approvalsForm" property="retestType"/>
						
				</td>
			</tr>
			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="valuationClass"/>
				</td>
				<td>Approximate&nbsp;Value <font color="red">*</font></td>
				<td>
				<bean:write name="approvalsForm" property="approximateValue"/>
				</td>
			</tr>
			<tr>
<td >WM Storage Type <font color="red">*</font></td><td colspan="3"> <bean:write name="approvalsForm" property="storage"/></td>
			</tr>
			
			<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
				<logic:notEmpty name="listName">
					<tr>
						<th colspan="4"><big>Uploaded Documents</big></th>
					</tr>
				<logic:iterate name="listName" id="listid">
					<bean:define id="file" name="listid" property="fileList" />
					<%
						String s = file.toString();
						String v[] = s.split(",");
						int l = v.length;
						for (int i = 0; i < l; i++) 
						{
						int x=v[i].lastIndexOf("/");
							String u=v[i].substring(x+1);
							
					%>
					<tr>
						<td colspan="4"><a href="/EMicro Files/ESS/sapMasterRequest/Raw Materials Files/UploadFiles/<%=u%>"   target="_blank"><%=u%></a></td>
						
					</tr>
					<%
					}
					%>		
				</logic:iterate>
					
				</logic:notEmpty>	
		<%--<tr>
		<td>
		Comments</td>
		<td colspan="3">
		<html:textarea property="comments" style="width:100%;"></html:textarea>
		
		</td>
		</tr>		
		--%><tr>			
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
						<center><html:button property="method" value="Close" onclick="window.close()" styleClass="rounded" style="width: 100px" /></center>
						</td>
					</tr>
				</table> 
				
		</div>
		</logic:iterate>
<div>&nbsp;</div>
<logic:notEmpty name="approverDetails">
	<table class="bordered" style="width: 70%;">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Sl.No</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
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
