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

 <script type="text/javascript">
 
 
 function showform(){
 
var url="itHelpdesk.do?method=newrequestform";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function closeData()
{

var url="itHelpdesk.do?method=displaynewrequestform";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function closeEdit()
{

var url="itHelpdesk.do?method=myrequest";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function ModifyData()
{

var url="itHelpdesk.do?method=modifyItrequest";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function saveData()
{

if(document.forms[0].reqPriority.value=="")
    {
      alert("Please Select Priority");
      document.forms[0].reqPriority.focus();
      return false;
    }
    
    if(document.forms[0].requiredfoldername.value=="")
	    {
	      alert("Please Enter Required Folder Name/Path");
	      document.forms[0].requiredfoldername.focus();
	      return false;
	    }
    var st = document.forms[0].requiredfoldername.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].requiredfoldername.value=st; 
	
	
	var chks = document.forms[0].accesstypes;
if(chks[0].checked==false&&chks[1].checked==false&&chks[2].checked==false&&chks[3].checked==false&&chks[4].checked==false)
{
alert("Please Select  Atleast One AccessType");
return false;
}

	     if(document.forms[0].durationofaccess.value=="")
	    {
	      alert("Please Enter  Duration Of Access");
	      document.forms[0].durationofaccess.focus();
	      return false;
	    }
	    var durationofaccess = document.forms[0].durationofaccess.value;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(durationofaccess)) {
             alert("Duration Of Access  Should be Integer ");
                document.forms[0].durationofaccess.focus();
            return false;
        }
	    
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
			
			
			var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
			
var url="itHelpdesk.do?method=submitrequest";
		document.forms[0].action=url;
	document.forms[0].submit(); 
}

function isNumber(evt)
{
evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;

}
 
 </script>
<style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
  </head>
  
  <body><br />
   <html:form action="/itHelpdesk.do" enctype="multipart/form-data">
   <div id="masterdiv" class="">
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
<tr><th colspan="4"><center><big>FTP  Access Request Form</big></center></th></tr>
<tr><th colspan="2"><big><center>Priority&nbsp;<font color="red" >*</font><center></big></th><td colspan="2" >
<html:select property="reqPriority" styleClass="content" styleId="filterId" >

	<html:option value="">--Select Priority--</html:option>
	<html:option value="Very High">Very High</html:option>
    <html:option value="High">High</html:option>
    <html:option value="Medium">Medium</html:option>
  

	</html:select>
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
<tr>
<td>Email ID</td>
<td colspan="3"><bean:write name="itHelpdeskForm"  property="empEmailID"/></td>
</tr>
<tr><th colspan="4"><big>Other Details</big></th></tr>
<tr>

<td><b>Required Folder Access/Path:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:text  name="itHelpdeskForm" property="requiredfoldername" style="width:85%;" ></html:text></td>
</tr>

<tr>
<td><b>Access Type:&nbsp;<font color="red" >*</font></b></td><td colspan="3">
<html:multibox property="accesstypes" value="Dowload File"> </html:multibox>&nbsp;<b>Allow Dowload File&nbsp;</b><br/>
<html:multibox property="accesstypes" value="Read/View"> </html:multibox>&nbsp;<b>Allow Read/View Only&nbsp;<br/>
<html:multibox property="accesstypes" value="Printing"> </html:multibox>&nbsp;<b>Allow Printing&nbsp;</b><br/>
<html:multibox property="accesstypes" value="Print Screen"> </html:multibox>&nbsp;<b>Allow Print Screen&nbsp;</b><br/>
<html:multibox property="accesstypes" value="Full"> </html:multibox>&nbsp;<b>Allow Full Access(Download,Print)&nbsp;</b>
</tr>
<tr>
<td><b>Required Duration Of Access:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:text  name="itHelpdeskForm" property="durationofaccess" style="width: 71px;" maxlength="3" onkeypress="return isNumber(event)" ></html:text>
	
	Days
</td>
</tr>


<tr>
	<td><b>Reason:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:textarea property="reason" name="itHelpdeskForm" cols="60" rows="5">

</html:textarea>	
	</td>
</tr>


<tr>
<td colspan="4" style="text-align: center;">
	<logic:notEmpty name="save">
	<html:button property="method"  value="Submit" onclick="saveData()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
	<html:button property="method"  value="Close" onclick="closeData()" styleClass="rounded" style="width: 100px"></html:button>
</logic:notEmpty>

<logic:notEmpty name="modify">
	<html:button property="method"  value="Modify" onclick="ModifyData()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
	<html:button property="method"  value="Close" onclick="closeEdit()" styleClass="rounded" style="width: 100px"></html:button>
</logic:notEmpty></td>
</tr>
		
</table>
</div>



<br/>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="5">Approvers Details</th></tr>
	<tr><th>Priority</th><th>Employee No</th><th>Approver Name</th><th>Department</th><th>Designation</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.employeeCode }</td><td>${abc.employeeName }</td><td>${abc.department }</td><td>${abc.designation }</td></tr>
	</logic:iterate>
</table>
	</logic:notEmpty>

<html:hidden property="requestType"/>
<html:hidden property="employeeno"/>
<html:hidden  property="requestNumber"/>
</div></div>
</html:form>
  </body>
</html>
