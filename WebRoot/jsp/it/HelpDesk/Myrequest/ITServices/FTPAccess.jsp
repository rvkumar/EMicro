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
	
	
	    if(document.forms[0].accesstype.value=="")
	    {
	      alert("Please Select Access Type");
	      document.forms[0].accesstype.focus();
	      return false;
	    }

    
	   
	     if(document.forms[0].durationofaccess.value=="")
	    {
	      alert("Please Select  Duration Of Access");
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
			
			
var url="itHelpdesk.do?method=submitrequest";
		document.forms[0].action=url;
	document.forms[0].submit(); 
}


 
 </script>

  </head>
  
  <body><br />
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


 



</br>
<br/>

<div style="width: 100%">
<table class="bordered " >
<tr><th colspan="4"><center><big>FTP Access Request Form</big></center></th></tr>
<tr><th colspan="2"><big><center>Priority&nbsp;<font color="red" >*</font><center></big></th><td colspan="2" >
<html:select property="reqPriority" styleClass="content" styleId="filterId" >

	<html:option value="">--Select Priority--</html:option>
	<html:option value="High">High</html:option>
    <html:option value="Low">Low</html:option>
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
<html:select property="accesstype" styleClass="content" styleId="filterId"  name="itHelpdeskForm">

	<html:option value="">--Select--</html:option>
	<html:option value="Read-Only">Read-Only</html:option>
	<html:option value="Read & Execute">Read & Execute</html:option>
	<html:option value="Modify">Modify</html:option>
	<html:option value="Full">Full</html:option>
	
	</html:select></td>

</tr>
<tr>
<td><b>Required Duration Of Access:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:select property="durationofaccess" styleClass="content" styleId="filterId"  name="itHelpdeskForm">

	<html:option value="">--Select--</html:option>
	<html:option value="1">1</html:option>
	<html:option value="2">2</html:option>
	<html:option value="3">3</html:option>
	<html:option value="4">4</html:option>
	<html:option value="5">5</html:option>
	</html:select>
	
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


<html:hidden property="requestType"/>
<html:hidden property="employeeno"/>
<html:hidden  property="requestNumber"/>

</html:form>
  </body>
</html>
