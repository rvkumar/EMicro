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
           if(document.forms[0].fromemailId.value=="")
	    {
	      alert("Please Enter From Email ID");
	      document.forms[0].fromemailId.focus();
	      return false;
	    }
			 var st = document.forms[0].fromemailId.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].fromemailId.value=st; 

  if(document.forms[0].reasonforchange.value=="")
	    {
	      alert("Please Enter Reason For Changes");
	      document.forms[0].reasonforchange.focus();
	      return false;
	    }
			 var st = document.forms[0].reasonforchange.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].reasonforchange.value=st; 
   
      if(document.forms[0].isHeHod.value=="")
	    {
	      alert("Please Select Is He HOD");
	      document.forms[0].isHeHod.focus();
	      return false;
	    }
	    
	    if(document.forms[0].transferoldmail.value=="")
	    {
	      alert("Please Select Transfer Old Emails");
	      document.forms[0].transferoldmail.focus();
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

 
 </script>

  </head>
  
  <body>
   <html:form action="/itHelpdesk.do" enctype="multipart/form-data">
   
   <div align="center">
		<logic:notEmpty name="itHelpdeskForm" property="message">
		<font color="green" size="3">
			<b><bean:write name="itHelpdeskForm" property="message" /></b>
		</font>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="itHelpdeskForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="itHelpdeskForm" property="message2" /></b>
		</font>
	</logic:present>


 




<div style="width: 100%">
<table class="bordered " >
<logic:iterate id="itHelpdeskForm" name="LotusIDChange">
<tr><th colspan="4"><center><big>Email ID Change Request Form</big></center></th></tr>
<tr><th colspan="2"><big><center>Priority<center></big></th><td colspan="2" ><big>
<bean:write name="itHelpdeskForm" property="reqPriority"/><big>

</td></tr>
<tr><th colspan="4"><big>Requester Details</big></th></tr>
<tr><td><b>Name:</b></td><td> <bean:write name="itHelpdeskForm" property="requestername"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="itHelpdeskForm" property="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="itHelpdeskForm" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="itHelpdeskForm" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="itHelpdeskForm" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="itHelpdeskForm" property="extno"/></td></tr>

<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="itHelpdeskForm" property="ipPhoneno"/></td>


<html:hidden property="hostname" />
<html:hidden property="IPNumber" />


<td><b>IP Address:</b></td><td ><bean:write name="itHelpdeskForm" property="IPNumber"/></td></tr>
<tr><th colspan="4"><big>Other Details</big></tr>
<tr><td><b>First Name:<font color="red" >*</font></b></td><td><bean:write name="itHelpdeskForm" property="firstname"/></td>
<td><b>Last Name:&nbsp;<font color="red" >*</font></b><bean:write name="itHelpdeskForm" property="lastname"/></td>
<td><b>Middle Name:&nbsp;</b><bean:write name="itHelpdeskForm" property="middname"/> </td>
</tr>

<tr>
<td><b>From Email Id:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><bean:write name="itHelpdeskForm" property="fromemailId"/></td>
	
</tr>
<tr>
<td><b>Newly Joined:&nbsp;<font color="red" >*</font></b></td><td>
<bean:write name="itHelpdeskForm" property="newlyJoinedYes"/>
<bean:write name="itHelpdeskForm" property="newlyJoinedNo"/>
</td>
<td>Joining Date<div id="joiningDateID" style="visibility: hidden;"><font color="red" >*</font></div></td><td><bean:write name="itHelpdeskForm" property="joiningDate"/></td>
</tr>
<tr>
<td><b>Suggested Email Id:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><bean:write name="itHelpdeskForm" property="suggestedEmailId"/></td></tr>
<tr>
<td><b>How many ID's are there in the requested department<font color="red" >*</font></td>
<td colspan="3"><bean:write name="itHelpdeskForm" property="totalIDs"/></td>
</tr>
<tr><td><b>Is He HOD:&nbsp;<font color="red" >*</font></b></td><td><bean:write name="itHelpdeskForm" property="isHeHod"/></td>
	<td><b>Transfer Old Emails:&nbsp;<font color="red" >*</font></b></td><td><bean:write name="itHelpdeskForm" property="transferoldmail"/></td>
</tr>
<tr>

<td><b>Allow Outside Communication:&nbsp;<font color="red" >*</font></b></td><td colspan="3">
<bean:write name="itHelpdeskForm" property="outsidecomyes"/>
<bean:write name="itHelpdeskForm" property="outsidecomno"/>
</td>
</tr>
<tr>
<td><b>Is This Common ID:&nbsp;<font color="red" >*</font></b></td><td><bean:write name="itHelpdeskForm" property="isthisCommonId"/>

	

	</td>
<td><b>Enable Web Access:&nbsp;<font color="red" >*</font></b></td><td><bean:write name="itHelpdeskForm" property="enablewebAccess"/>
	</td></tr>
	<tr><td ><b>Reason:</b></td>
<td colspan="3"><bean:write name="itHelpdeskForm" property="reason"/>
	</td>
</tr>
<tr>
<td colspan="4" style="text-align: center;">
	<html:button property="method"  value="Close" onclick="history.back(-1)" styleClass="rounded" style="width: 100px"></html:button>
</td>
</tr>
		</logic:iterate>
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


<html:hidden property="requestType"/>
</html:form>
  </body>
</html>
