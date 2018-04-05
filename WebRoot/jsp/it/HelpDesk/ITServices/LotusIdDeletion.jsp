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
 <link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
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
	
		<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">
$(function() {
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>

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
    
    
	
	if(document.forms[0].emailidDelete.value=="")
	    {
	      alert("Please Enter Email ID To Be Deleted");
	      document.forms[0].emailidDelete.focus();
	      return false;
	    }
			 var st = document.forms[0].emailidDelete.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].emailidDelete.value=st; 
			
			
			
		var st = document.forms[0].forward_MailID.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].forward_MailID.value=st;
			

       if(document.forms[0].isHeHod.value=="")
	    {
	      alert("Please Select Is He HOD");
	      document.forms[0].isHeHod.focus();
	      return false;
	    }
	    
	
	 
	    if(document.forms[0].isthisCommonId.value=="")
	    {
	      alert("Please Select Is This Common ID");
	      document.forms[0].isthisCommonId.focus();
	      return false;
	    }
	    
	    
	     if(document.forms[0].deletefrom.value=="")
	    {
	      alert("Please Select Delete From");
	      document.forms[0].deletefrom.focus();
	      return false;
	    }
	    
	       // split the date into days, months, years array
var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1; //January is 0!
var yyyy = today.getFullYear();

if(dd<10) {
    dd='0'+dd
} 

if(mm<10) {
    mm='0'+mm
} 

today = dd+'/'+mm+'/'+yyyy;

var x = today.split('/');
var y = document.forms[0].deletefrom.value.split('/');


// create date objects using year, month, day
var a = new Date(x[2],x[1],x[0]);
var b = new Date(y[2],y[1],y[0]);

// calculate difference between dayes
var c = ( b - a );

// convert from milliseconds to days
// multiply milliseconds * seconds * minutes * hours
var d = c / (1000 * 60 * 60 * 24);

if(d>=30)
{
 alert("Delete From Date Should Be Less Than Or Equal To 30 Days From The Current Date");
	      document.forms[0].deletefrom.focus();
	         return false;

}
	    
	
	
	 if(document.forms[0].deletedDBYes.checked==false && document.forms[0].deletedDBNo.checked==false)
	    {
	      alert("Please Select Yes Or NO In Required Deleted Mail Database");
	      document.forms[0].deletedDBYes.focus();
	         return false;
	    }
	    
	    if(document.forms[0].deletedDBYes.checked==true)
	    {
	    
	     if(document.forms[0].mailIDPath.value=="")
	    {
	      alert("Please Enter Path Where To be Copied");
	      document.forms[0].mailIDPath.focus();
	      return false;
	    }
	    
	    }
		
		   var st = document.forms[0].mailIDPath.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].mailIDPath.value=st;
	    
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

function deletedDB(status){
	if(status=="Yes"){
		document.getElementById("pathID").style="visibility: show";
		document.forms[0].deletedDBNo.checked=false;
	}
	if(status=="No"){
		document.getElementById("pathID").style="visibility: hidden";
		document.forms[0].deletedDBYes.checked=false;
	}
	
}

function days_between() {


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
<tr><th colspan="4"><center><big>Email ID Delete Request Form</big></cfenter></th></tr>
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
<td><b>Email Id To Be Deleted:&nbsp;<font color="red" >*</font></b></td><td colspan="3"><html:text  name="itHelpdeskForm" property="emailidDelete" style="width:50%;"></html:text></td>
</tr>
<tr><td><b>Emails To Be Forwarded To ID:</b></td><td colspan="3"><html:text property="forward_MailID" style="width:400px;" maxlength="20"/></td></tr>
<tr>

<td><b>Is He HOD:&nbsp;<font color="red" >*</font></b></td><td><html:select property="isHeHod" styleClass="content" styleId="filterId" >

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


<tr><td><b>Delete From :&nbsp;</b><font color="red" >*</font></td><td colspan="4"><html:text  name="itHelpdeskForm" property="deletefrom" styleId="popupDatepicker"  style="width:20%;" readonly="true"></html:text></td></tr>

<tr><td><b>Required Deleted Mail Database:&nbsp;<font color="red" >*</font></b></td><td>
<html:checkbox property="deletedDBYes" value="Yes" onclick="deletedDB(this.value)"></html:checkbox>&nbsp;YES&nbsp;
<html:checkbox property="deletedDBNo" value="No" onclick="deletedDB(this.value)"></html:checkbox>&nbsp;NO&nbsp;
</td>
<td><b>Path Where To Be Copied <div id="pathID" style="visibility: hidden;"><font color="red" >*</font></div></td><td><html:text property="mailIDPath" style="width: 376px;"/></td>
</tr>

<tr>
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
