<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
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
 <script type="text/javascript">
 
 
 function showform(){
 
var url="itHelpdesk.do?method=newrequestform";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function closeData()
{

var url="itHelpdesk.do?method=myrequest";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function saveData()
{

if(document.forms[0].isHeHod.value=="")
	    {
	      alert("Please Select Is He HOD");
	      document.forms[0].isHeHod.focus();
	      return false;
	    }
	    if(document.forms[0].suggestedEmailId.value=="")
	    {
	      alert("Please Enter Suggested Email ID");
	      document.forms[0].suggestedEmailId.focus();
	      return false;
	    }
			 var st = document.forms[0].suggestedEmailId.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].suggestedEmailId.value=st; 
	
	if(document.forms[0].isthisCommonId.value=="")
	    {
	      alert("Please Select Is This Common ID");
	      document.forms[0].isthisCommonId.focus();
	      return false;
	    }
	    
	    if(document.forms[0].enablewebAccess.value=="")
	    {
	      alert("Please Select Enable WebAccess");
	      document.forms[0].enablewebAccess.focus();
	      return false;
	    }
	    
	    if(document.forms[0].enablewebAccess.value=="Yes")
	    {
           if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Reason");
	      document.forms[0].reason.focus();
	      return false;
	    }
			 var st = document.forms[0].reason.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].reason.value=st; 

         }

var url="itHelpdesk.do?method=submitrequest";
		document.forms[0].action=url;
		document.forms[0].submit();
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

function changeStatus(elem){
var elemValue=elem.value;
if(elemValue=="Reject")
	{
	if(document.forms[0].comments.value==""){
	  alert("Please Add Some Comments");
	       document.forms[0].comments.focus();
	         return false;
	  }
	
	}
if(document.forms[0].comments.value!=""){
	var st = document.forms[0].comments.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].comments.value=st;
}
  
	var elemValue = elem.value;
	
	
	var reqId = document.forms[0].requestNo.value;
	
	var reqType = document.forms[0].requestType.value;

	var url="approvals.do?method=statusChangeForITRequest&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;
	
	
	document.forms[0].action=url;
	document.forms[0].submit();
} 
 
 </script>

  </head>
  
  <body>
 <html:form action="/approvals.do" enctype="multipart/form-data">   
   <div align="center">
		<logic:notEmpty name="approvalsForm" property="message">
		<font color="green" size="3">
			<b><bean:write name="approvalsForm" property="message" /></b>
		</font>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="approvalsForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="approvalsForm" property="message2" /></b>
		</font>
	</logic:present>


<div style="width: 100%">
<table class="bordered " >
<tr><th colspan="4"><center><big>New IT Spares Request Form</big></center></th></tr>
<tr><th colspan="2"><big><center>Priority<center></big></th><td colspan="2" ><big>
<bean:write name="approvalsForm" property="reqPriority"/><big>

</td></tr>
<tr><th colspan="4"><big>Requester Details</big></th></tr>
<tr><td><b>Name:</b></td><td> <bean:write name="approvalsForm" property="requestername"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="approvalsForm" property="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="approvalsForm" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="approvalsForm" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="approvalsForm" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="approvalsForm" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="approvalsForm" property="ipPhoneno"/></td>


<html:hidden property="hostname" />
<html:hidden property="IPNumber" />


<td><b>IP Address:</b></td><td ><bean:write name="approvalsForm" property="IPNumber"/></td></tr>
<tr><th colspan="4"><big>Other Details</big></th></tr>
<tr><td>Request date</td><td colspan="3"><bean:write name="approvalsForm" property="requestDate"/></td></tr>
<tr><td>Category <font color="red">*</font></td>
<td><bean:write name="approvalsForm" property="assetcategory"/></td>


<td>Required By Date</td>
<td><bean:write name="approvalsForm" property="reqbydate"/></td>
</tr>

<tr>
<td>Suggested Model If Any:</td>
<td colspan="3"><bean:write name="approvalsForm" property="suggestmodelname"/></td>
</tr>










<tr>
<td>Approximate Value:<font color="red">*</font></td>
<td colspan="3"><bean:write name="approvalsForm" property="apprxvalue"/></td>



</tr>



<tr>
	<td><b>Purpose:&nbsp;<font color="red">*</font></b></td>
<td colspan="3"><bean:write name="approvalsForm" property="purpose"/>
	</td>
</tr>
	<tr>
		<td>
		Comments</td>
		<td colspan="3">
<html:textarea property="comments" style="width:100%;"></html:textarea>		
		
		</td>
		</tr>
<tr><td colspan="6" style="border:0px; text-align: center;">
<br>
</br>
   <logic:notEmpty name="approveButton">
	<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
	</logic:notEmpty>
	<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<!--<input type="button" class="rounded" value="Close" onclick="goBack()"  />
			
			--><input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  />
			</td>
			
			</tr>
		
</table>

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
</div>

<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
<html:hidden property="requestType"/>
<html:hidden property="employeeno"/>
<html:hidden  property="requestNo"/>

<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="userRole"/>
</html:form>
  </body>
</html>
