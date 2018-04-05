<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style>
 th{font-family: Arial; font-size: 14;}
td{font-family: Arial; font-size: 10;}
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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>eMicro :: Raw Material </title>

	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<script type="text/javascript">

function changeStatus(elem){

var elemValue = elem.value;
if(elemValue=="Reject")
	{

if(document.forms[0].comments.value==""){
	  alert("Please Add Some Comments");
	       document.forms[0].comments.focus();
	         return false;
	  }
	
	}
  
	var elemValue = elem.value;
	if(elemValue=='Created')
	{
	elemValue='Approve';
	
	/* if(document.forms[0].locationId.value=="")
	    {
	      alert("Please Select Location");
	      document.forms[0].locationId.focus();
	      return false;
	    } */
<%--	     if(document.forms[0].materialTypeId.value=="")--%>
<%--	    {--%>
<%--	      alert("Please Select Material Type");--%>
<%--	      document.forms[0].materialTypeId.focus();--%>
<%--	      return false;--%>
<%--	    }--%>
	    if(document.forms[0].storageLocationId.value=="")
	    {
	      alert("Please Select Storage Location");
	      document.forms[0].storageLocationId.focus();
	      return false;
	    }
	      if(document.forms[0].materialShortName.value=="")
	    {
	      alert("Please Enter Material Short Name");
	      document.forms[0].materialShortName.focus();
	      return false;
	    }
	     var st = document.forms[0].materialShortName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialShortName.value=st;
	
	
	
	
	      if(document.forms[0].materialLongName.value=="")
	    {
	      alert("Please Enter Material Long Name");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	    if(document.forms[0].materialLongName.value!="")
	    {
	     var a=document.forms[0].materialLongName.value;
	     a=a.length;
	     if(a>80){
	      alert("Material Long Name Should be less than 80 characters");
	      document.forms[0].materialLongName.focus();
	      return false;
	    }
	    
	       var st = document.forms[0].materialLongName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].materialLongName.value=st;
	
}
	      if(document.forms[0].materialGroupId.value=="")
	    {
	      alert("Please Select Material Group");
	      document.forms[0].materialGroupId.focus();
	      return false;
	    }
	      if(document.forms[0].unitOfMeasId.value=="")
	    {
	      alert("Please Select U.O.M");
	      document.forms[0].unitOfMeasId.focus();
	      return false;
	    }
	    
	    if(document.forms[0].packSize.value=="")
	    {
	      alert("Please Select Pack Size");
	      document.forms[0].packSize.focus();
	      return false;
	    }
	   
	      if(document.forms[0].customerName.value=="")
	    {
	      alert("Please Enter Customer Name");
	      document.forms[0].customerName.focus();
	      return false;
	    }
	 var st = document.forms[0].customerName.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].customerName.value=st;
	
	
	
	    /*if(document.forms[0].prodInspMemo.value=="")
	    {
	      alert("Please Enter Prod Insp Memo");
	      document.forms[0].prodInspMemo.focus();
	      return false;
	    }
	      var prodInspMemo=document.forms[0].prodInspMemo.value;
	    for (var i = 0; i < prodInspMemo.length; i++) {
    if (splChars.indexOf(prodInspMemo.charAt(i)) != -1){
    alert ("Please Remove Single Code(') in  Prod Insp Memo!"); 
    	      document.forms[0].prodInspMemo.focus();
 return false;
}
}
*/
	      if(document.forms[0].shelfLife.value=="")
	    {
	      alert("Please Enter Shelf Life");
	      document.forms[0].shelfLife.focus();
	      return false;
	    }
	     var shelfLife = document.forms[0].shelfLife.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(shelfLife)) {
             alert("Shelf Life  Should be Integer ");
              document.forms[0].shelfLife.focus();
            return false;
        }
        
        
          if(document.forms[0].shelfType.value=="")
	    {
	      alert("Please Select Shelf Type");
	      document.forms[0].shelfType.focus();
	      return false;
	    }
	    if(document.forms[0].retestDays.value=="")
	    {
	      alert("Please Enter Retest Days");
	      document.forms[0].retestDays.focus();
	      return false;
	    }
	     var retestDays = document.forms[0].retestDays.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(retestDays)) {
             alert("Retest Days  Should be Integer ");
              document.forms[0].retestDays.focus();
            return false;
        }
         if(document.forms[0].retestType.value=="")
	    {
	      alert("Please Enter Retest Days Type");
	      document.forms[0].retestType.focus();
	      return false;
	    }
        
	      if(document.forms[0].standardBatchSize.value=="")
	    {
	      alert("Please Enter Standard Batch Size");
	      document.forms[0].standardBatchSize.focus();
	      return false;
	    }
	    
	    
	      var standardBatchSize = document.forms[0].standardBatchSize.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(standardBatchSize)) {
             alert("Standard Batch Size Value Should be Integer or Float!");
              document.forms[0].standardBatchSize.focus();
            return false;
        }
	      if(document.forms[0].batchCode.value=="")
	    {
	      alert("Please Enter Batch Code");
	      document.forms[0].batchCode.focus();
	      return false;
	    }
	    var st = document.forms[0].batchCode.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].batchCode.value=st;
	
	      if(document.forms[0].targetWeight.value=="")
	    {
	      alert("Please Enter Target Weight");
	      document.forms[0].targetWeight.focus();
	      return false;
	    }
	    var targetWeight = document.forms[0].targetWeight.value;
        var pattern = /^\d+(\.\d+)?$/
        if (!pattern.test(targetWeight)) {
             alert("Target Weight Value Should be Integer or Float!");
              document.forms[0].targetWeight.focus();
            return false;
        }
	      
	    
	     
	     if(document.forms[0].weightUOM.value=="")
	    {
	      alert("Please Enter Weight UOM");
	      document.forms[0].weightUOM.focus();
	      return false;
	    }
	     
        
	     if(document.forms[0].valuationClass.value=="")
	    {
	      alert("Please Select Valuation Class");
	      document.forms[0].valuationClass.focus();
	      return false;
	    }
		   if(document.forms[0].sapCodeExists.checked==false && document.forms[0].sapCodeExistsNo.checked==false )
	    {
	      alert("Please Select  SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    } 
	    
	       if(document.forms[0].sapCodeExists.checked==true && document.forms[0].sapCodeExistsNo.checked==true )
	    {
	      alert("Please Choose Only One Option In SAP Code Exists");
	      document.forms[0].sapCodeExists.focus();
	      return false;
	    }
	    
	     	   if(document.forms[0].sapCodeNo.value=="")
	    {
	      alert("Please Enter SAP Code No.");
	      document.forms[0].sapCodeNo.focus();
	      return false;
	    } 
	  var st = document.forms[0].sapCodeNo.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].sapCodeNo.value=st; 
	
	
	}
	var reqId = document.forms[0].requestNumber.value;
	var reqType = "ROH";
	var matGroup=document.forms[0].materialGroupId.value;
	var location=document.forms[0].locationId.value;
	var url="approvals.do?method=statusChangeForRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue+"&matGroup="+matGroup+"&location="+matGroup;
	document.forms[0].action=url;
	document.forms[0].submit();
}
function goBack()
  {
  window.history.back()
  }	  
   function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;

	document.forms[0].action=url;
	document.forms[0].submit();


} 

</script>
</head>
<body style="text-transform: uppercase;">
	<html:form action="/approvals.do" enctype="multipart/form-data">
	<div align="center">
				<logic:present name="approvalsForm" property="message">
					<font color="red" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="approvalsForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				</logic:present>
			</div>
	
	
	

	
		<table class="bordered" width="90%">
			<tr>
			<th colspan="8"><center><big>Semifinished Form</big></center></th>
		</tr>
			<tr>
	 			<th colspan="4"><big>Basic Details Of Material</big></th>
			</tr>

			<tr>
				<td>Request No. <font color="red">*</font></td>
				<td><html:text property="requestNo" readonly="true" />
				
		
					<html:hidden property="typeDetails"/>
				</td>
				<td>Request Date <font color="red">*</font></td>
				<td><html:text property="requestDate" styleId="requestDate" onfocus="popupCalender('requestDate')" readonly="true" /></td>
			</tr>

			<tr>
				<td>Location <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="locationId" disabled="true">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="locationIdList" labelProperty="locationLabelList"/>
					</html:select>
				</td>

<%--				<th width="274" class="specalt" scope="row">Material Type <img src="images/star.gif" width="8" height="8" /></th>--%>
<%--				<td align="left">--%>
<%--				<html:select  name="approvalsForm"  property="materialTypeId" styleClass="text_field" style="width:100px; ">--%>
<%--					<html:option value="">--Select--</html:option>--%>
<%--				<html:options name="approvalsForm" property="materTypeIDList" --%>
<%--									labelProperty="materialTypeIdValueList"/>--%>
<%--					</html:select>--%>
<%--				<br /></td>--%>
				 <html:hidden property="materialTypeId"/>
				<td>Storage Location <font color="red">*</font></td>
				<td><html:select name="approvalsForm" property="storageLocationId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="storageID" labelProperty="storageIDName"/>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Short Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialShortName" maxlength="40" size="45" title="Maximum of 40 characters" style="width:400px;text-transform:uppercase"></html:text></td>
			</tr>
			
			<tr>
				<td>Long Name <font color="red">*</font></td>
				<td colspan="3"><html:text property="materialLongName" maxlength="80" size="110" title="Maximum of 80 characters" style="text-transform:uppercase"></html:text></td>
			</tr>
			
			<tr>
				<td>Material Group <font color="red">*</font></td>
				<td colspan="3"><html:select name="approvalsForm" property="materialGroupId" disabled="TRUE"> 
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="materGroupIDList" labelProperty="materialGroupIdValueList"/>
					</html:select>
				&nbsp;&nbsp;U O M <font color="red">*</font>
				<html:select name="approvalsForm" property="unitOfMeasId">
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="unitOfMeasIdList" labelProperty="unitOfMeasIdValues"/>
					</html:select>
				&nbsp;&nbsp;Pack Size<font color="red">*</font>
				<html:select property="packSize">	
							<html:option value="">--Select--</html:option>
							<html:options name="approvalsForm" property="packSizeID" labelProperty="packSizeName"/>
						</html:select>
				
		
			</tr>

			<tr>
				<th colspan="4"><big>Quality Requirement</big></th>
   			</tr>

			<tr>
				<td>Country <font color="red">*</font></td>
				<td><html:select property="countryId">
						<html:option value="">--Select--</html:option>
						<html:options property="counID" labelProperty="countryName" />
					</html:select>
				</td>
				<td>Customer Name <font color="red">*</font></td>
				<td><html:text property="customerName" maxlength="40" size="40" title="Maximum of 40 characters" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				
				<html:hidden property="prodInspMemo" ></html:hidden>
				
				<td>Shelf Life <font color="red">*</font></td>
				<td ><html:text property="shelfLife" style="width:40px;"></html:text>
					<html:select property="shelfType">
						<html:option value="months">Months</html:option>
						<html:option value="days">Days</html:option>
						
					</html:select>
				</td>
				<td>Retest Days <font color="red">*</font></td>
				<td colspan="3"><html:text property="retestDays" style="text-transform:uppercase;width:40px;" ></html:text>
				<html:select property="retestType">
						<html:option value="days">Days</html:option>
						<html:option value="months">Months</html:option>
					</html:select>
				</td>
			</tr>

			<tr>
				<td>Std. Batch Size <font color="red">*</font></td>
				<td><html:text property="standardBatchSize" style="text-transform:uppercase"></html:text></td>
				<td>Batch Code <font color="red">*</font></td>
				<td><html:text property="batchCode" maxlength="10" style="text-transform:uppercase"></html:text></td>
			</tr>

			<tr>
				<td>Target Weight <font color="red">*</font></td>
				<td><html:text property="targetWeight" style="text-transform:uppercase"></html:text></td>
			
			<!--<tr>
			<th width="274" class="specalt" scope="row">Gross Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="grossWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr>
			<th width="274" class="specalt" scope="row">Net Weight<img src="images/star.gif" width="8" height="8" /></th>
			<td><html:text property="netWeight" styleClass="text_field" ></html:text>
			</td>
			</tr>
			<tr-->

				<td>Weight UOM <font color="red">*</font></td>
				<td><html:select property="weightUOM">	
						<html:option value="">--Select--</html:option>
						<html:options name="approvalsForm" property="weightUOMID" labelProperty="weightUOMName"/>
					</html:select></td>
			</tr>

			<tr>
				
			</tr>

			<tr>
				<th colspan="4"><big>Other Details</big></th>
   			</tr>

			<tr>
				<td>Valuation Class <font color="red">*</font></td>
				<td><html:select property="valuationClass">
						<html:options name="approvalsForm" property="valuationClassID" labelProperty="valuationClassName"/>
					</html:select>
				</td>
			</tr>
			
		
			<tr>
	<th colspan="4">Material Code Details:</th>
	   </tr>
	   
	 		  <tr>
				<td >SAP Code Exists<font color="red">*</font></td>
				<td>
				
						<html:checkbox property="sapCodeExists"/>
						<span class="text">Yes</span>
						&nbsp;&nbsp;&nbsp;
				<html:checkbox property="sapCodeExistsNo"/>
						<span class="text">No</span>
				</td>
			
			<td >SAP Code No<font color="red">*</font></td>
				<td>	<html:text property="sapCodeNo" styleClass="text_field" style="width:200px;" maxlength="18"></html:text></td>
			
			</tr>
			
			<tr>	
			<td >Code Creation Date<img src="images/star.gif" width="8" height="8" />
	</td>
			<td align="left">
				<html:text property="sapCreationDate" styleId="sapCreationDate"  readonly="true" styleClass="text_field"/></td>
		
				<td >Code Created By<font color="red">*</font></td>
				<td><html:text property="sapCreatedBy" styleClass="text_field"  maxlength="12" readonly="true"></html:text>
				</td>
			</tr>
			<logic:notEmpty name="rejectedFLag">
			<tr>
			<td>Status</td>
			<td colspan="4"><b><big>For Reapproval</big></b></td>
			</tr>
			</logic:notEmpty>
			<tr>
		<td>
		Comments</td>
		<td colspan="3">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
		
		</td>
		</tr>		
		<tr><td colspan="6" style="border:0px; text-align: center;">
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Created" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr>	
		</table>
		
		<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="userRole"/>
<html:hidden property="requestNumber"/>
	<html:hidden property="locationId"/>
	<html:hidden property="materialTypeId"/>
	<html:hidden property="reqMaterialGroup"/>

	<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.approveStatus }</td><td>${abc.date }</td><td>${abc.comments }</td>
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	
	<br/>
	&nbsp;
	<br/>
	&nbsp;
	
	
</html:form>
</body>
</html>
