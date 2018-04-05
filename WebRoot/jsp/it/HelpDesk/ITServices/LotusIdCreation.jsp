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
 <link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>


  <style type="text/css">
@import "jquery.timeentry.css";
</style>
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
	<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#joiningDateId').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});


function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
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
    
    if(document.forms[0].anysplreq.value!="")
 {
    
    if(document.forms[0].firstname.value=="")
    {
      alert("Please Enter FirstName");
      document.forms[0].firstname.focus();
      return false;
    }
    
    var st = document.forms[0].firstname.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].firstname.value=st; 
    
    if(document.forms[0].lastname.value=="")
    {
      alert("Please Enter LastName");
      document.forms[0].lastname.focus();
      return false;
    }
    
    var st = document.forms[0].lastname.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].lastname.value=st; 
			
			
			var st = document.forms[0].middname.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].middname.value=st; 
			
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
			
			
				if(document.forms[0].totalIDs.value!="")
			
				{
	
		
		
		var a=document.forms[0].totalIDs.value;
			 var pattern = /^\d+(\d+)?$/
        if (!pattern.test(a)) {
             alert("How many ID's are there in the requested department  Should be Integer ");
                document.forms[0].totalIDs.focus();
            return false;
        }

			} 	
				
		 if(document.forms[0].outsidecomyes.checked==false && document.forms[0].outsidecomno.checked==false)
	    {
	      alert("Please Select Yes Or NO In Allow Outside Communication");
	      document.forms[0].outsidecomyes.focus();
	         return false;
	    }
				
		

       if(document.forms[0].isHeHod.value=="")
	    {
	      alert("Please Select Is He HOD");
	      document.forms[0].isHeHod.focus();
	      return false;
	    }
	    
	
	
	    
	    if(document.forms[0].enablewebAccess.value=="")
	    {
	      alert("Please Select Enable WebAccess");
	      document.forms[0].enablewebAccess.focus();
	      return false;
	    }
	    
	    if(document.forms[0].isthisCommonId.value=="")
	    {
	      alert("Please Select Is This Common ID");
	      document.forms[0].isthisCommonId.focus();
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
function changeJoiningDate(status){
	if(status=="Yes"){
		document.getElementById("joiningDateID").style="visibility: show";
		document.forms[0].newlyJoinedNo.checked=false;
	}
	if(status=="No"){
		document.getElementById("joiningDateID").style="visibility: hidden";
		document.forms[0].newlyJoinedYes.checked=false;
	}
	
}

function changeoutside(status){
	if(status=="Yes"){
		
		document.forms[0].outsidecomno.checked=false;
	}
	if(status=="No"){
		
		document.forms[0].outsidecomyes.checked=false;
	}
	
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
  
  <body>
   <html:form action="/itHelpdesk.do" enctype="multipart/form-data">
   <div id="masterdiv" class="">
   <html:hidden property="anysplreq" />
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
<tr><th colspan="8"><center><big>Email ID Create Request Form</big></cfenter></th></tr>
<tr><th colspan="2"><big><center>Priority&nbsp;<font color="red" >*</font><center></big></th><td colspan="2" >
<html:select property="reqPriority" styleClass="content" styleId="filterId" >

	<html:option value="">--Select Priority--</html:option>
	<html:option value="Very High">Very High</html:option>
    <html:option value="High">High</html:option>
    <html:option value="Medium">Medium</html:option>


	</html:select>
</td></tr>
<logic:notEmpty name="othr">
<tr><th colspan="8"><big>Requested For Details</big></th></tr>
</logic:notEmpty>
<logic:empty name="othr">
<tr><th colspan="8"><big>Requester Details</big></th></tr>
</logic:empty>
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
<html:hidden property="empEmailID" />

<td><b>IP Address:</b></td><td ><bean:write name="itHelpdeskForm" property="IPNumber"/></td></tr>
<tr>
<td>Email ID</td>
<td colspan="1"><bean:write name="itHelpdeskForm"  property="empEmailID"/></td>
<td>Date of Joining</td>
<td><bean:write name="itHelpdeskForm"  property="joiningDate"/></td>
</tr>
<tr><th colspan="8"><big>Other Details</big></th></tr>
<logic:notEmpty name="othr">
<tr><td><b>First Name:<font color="red" >*</font></b></td><td><html:text  name="itHelpdeskForm" property="firstname" style="width:90%;"></html:text></td>
<td><b>Last Name:&nbsp;<font color="red" >*</font></td><td></b><html:text  name="itHelpdeskForm" property="lastname" style="width: 173px;"></html:text></td></tr>
<tr>
<td><b>Middle Name:&nbsp;</td><td colspan="3"></b><html:text  name="itHelpdeskForm" property="middname" style="width: 316px;"></html:text></td>
</tr>
</logic:notEmpty>
<tr>
<td><b>Suggested Email Id:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><html:text  name="itHelpdeskForm" property="suggestedEmailId" style="width:50%;"></html:text></td>
</tr>
<tr>
<td><b>How many ID's are there in the requested department</td><td colspan="3"><html:text property="totalIDs"/></td>

</tr>
<tr>
<td>Designation</td>
<td>
<html:select  property="designation" name="itHelpdeskForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="itHelpdeskForm"  property="desgList" labelProperty="desgLabelList"/>
</html:select>
</td>
<td>
Department
</td>
<td>
<html:select  property="department" name="itHelpdeskForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="itHelpdeskForm"  property="deptList" labelProperty="deptLabelList"/>
</html:select>
</td>
</tr>.
<tr>
<td><b>Allow Outside Communication:&nbsp;<font color="red" >*</font></b></td><td>
<html:checkbox property="outsidecomyes" value="Yes" styleId="a" onclick="changeoutside(this.value)"></html:checkbox>&nbsp;YES&nbsp;
<html:checkbox property="outsidecomno" value="No" styleId="a" onclick="changeoutside(this.value)"></html:checkbox>&nbsp;NO&nbsp;

</td>
<td><b>Is He HOD:&nbsp;<font color="red" >*</font></b></td><td><html:select property="isHeHod" styleClass="content" styleId="filterId" >

	<html:option value="">--Select--</html:option>
    <html:option value="Yes">Yes</html:option>
    <html:option value="No">No</html:option>

	</html:select></td>
</tr>


<tr>
<td><b>Enable Web Access:&nbsp;<font color="red" >*</font></b></td><td><html:select property="enablewebAccess" styleClass="content" styleId="filterId" >

	<html:option value="">--Select--</html:option>
    <html:option value="Yes">Yes</html:option>
    <html:option value="No">No</html:option>

	</html:select></td>
<td><b>Is This Common ID:&nbsp;<font color="red" >*</font></b></td><td><html:select property="isthisCommonId" styleClass="content" styleId="filterId" >

	<html:option value="">--Select--</html:option>
    <html:option value="Yes">Yes</html:option>
    <html:option value="No">No</html:option>

	</html:select></td>
</tr>

	<td><b>Reason:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><html:textarea property="reason" name="itHelpdeskForm" cols="80" rows="4">

</html:textarea>	
	</td>
</tr>

<%--<tr><td><b>Total Email-ID in Department:</b></td><td colspan="1"><html:text  name="itHelpdeskForm" property="totalmailIdDept"></html:text></td>--%>
<%--<td><b>Total Email-ID in Location:</b></td><td colspan="1"><html:text  name="itHelpdeskForm" property="totalmailIdloc"></html:text></td></tr>--%>

<tr>
<td colspan="4" style="text-align: center;">
	<logic:notEmpty name="save">
	<html:button property="method"  value="Submit" onclick="saveData()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
	<html:button property="method"  value="Close" onclick="closeData()" styleClass="rounded" style="width: 100px"></html:button>
</logic:notEmpty>

<logic:notEmpty name="modify">
	<html:button property="method"  value="Modify" onclick="ModifyData()" styleClass="rounded" style="width: 100px"></html:button>&nbsp;&nbsp;
	<html:button property="method"  value="Close" onclick="closeEdit()" styleClass="rounded" style="width: 100px"></html:button>
</logic:notEmpty>
</td>
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
