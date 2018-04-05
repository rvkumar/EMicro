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
	
	if(document.forms[0].assetcategory.value=="")
    {
      alert("Please Select Category");
      document.forms[0].assetcategory.focus();
      return false;
    }
	
	
    
    var st = document.forms[0].suggestmodelname.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].suggestmodelname.value=st; 
    
   
  
    if(document.forms[0].gxpyes.checked==false && document.forms[0].gxpno.checked==false)
	    {
	      alert("Please Select Yes Or NO In GXP Imapct Validation");
	      document.forms[0].gxpyes.focus();
	         return false;
	    }
   

if(document.forms[0].assetcategory.value=="Printer")
    {
    if(document.forms[0].typeofprinter.value=="")
    {
      alert("Please Select Type Of Printer");
      document.forms[0].typeofprinter.focus();
      return false;
    }
    
        var st = document.forms[0].printvolpermonth.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].printvolpermonth.value=st; 
    
        var st = document.forms[0].users.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].users.value=st; 
    
        var st = document.forms[0].suggestmodelno.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].suggestmodelno.value=st; 
    
    
    
    }
    
    
    
			

	
	
	if(document.forms[0].apprxvalue.value=="")
    {
      alert("Please Enter Approximate Value");
      document.forms[0].apprxvalue.focus();
      return false;
    }
    
      if(document.forms[0].lanyes.checked==false && document.forms[0].lanno.checked==false)
	    {
	      alert("Please Select Yes Or NO In LAN Connectivity");
	      document.forms[0].lanyes.focus();
	         return false;
	    }
    
		var st = document.forms[0].anysplreq.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].anysplreq.value=st;
		
		
		
    
if(document.forms[0].purpose.value=="")
    {
      alert("Please Enter Purpose");
      document.forms[0].purpose.focus();
      return false;
    }
   
	
		var st = document.forms[0].purpose.value;
		var Re = new RegExp("\\'","g");
		st = st.replace(Re,"`");
		document.forms[0].purpose.value=st;
		
		
		var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";

var url="itHelpdesk.do?method=submitrequest";
document.forms[0].action=url;
document.forms[0].submit();
}


function display()
{

var x=document.forms[0].assetcategory.value;

if(x=="Desktop" || x=="Laptop")
{

document.getElementById("printer").style.visibility="collapse";
document.getElementById("printer1").style.visibility="collapse";

}
if(x=="Printer")
{
document.getElementById("printer").style.visibility="visible";
document.getElementById("printer1").style.visibility="visible";

}
if(x=="") 
{

document.getElementById("printer").style.visibility="collapse";
document.getElementById("printer1").style.visibility="collapse";

}

}

function gxb(status){
	if(status=="Yes"){
		
		document.forms[0].gxpno.checked=false;
	}
	if(status=="No"){
		
		document.forms[0].gxpyes.checked=false;
	}
	}
	function lan(status){
	if(status=="Yes"){
		
		document.forms[0].lanno.checked=false;
	}
	if(status=="No"){
		 
		document.forms[0].lanyes.checked=false;
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
<tr><th colspan="4"><center><big>New IT Asset Request Form</big></center></th></tr>
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
<tr><td>Category <font color="red">*</font></td>
<td><html:select property="assetcategory" styleClass="content" styleId="filterId" onchange="display()">

	<html:option value="">--Select Category--</html:option>
	<html:option value="Desktop">Desktop</html:option>
    <html:option value="Laptop">Laptop</html:option>
    <html:option value="Printer">Printer</html:option>
    

	</html:select></td>


<td>Required By Date</td>
<td><html:text  name="itHelpdeskForm" property="reqbydate" styleId="popupDatepicker"  style="width:50%;" readonly="true"></html:text></td>
</tr>

<tr>
<td>Suggested Model If Any:</td>
<td colspan="3"><html:text  name="itHelpdeskForm" property="suggestmodelname" style="width:80%;" ></html:text></td>
</tr>



<tr >
<td >GXP Impact Validation Required:<font color="red">*</font></td>
<td colspan="3">
<html:checkbox property="gxpyes" value="Yes" onclick="gxb(this.value)"></html:checkbox>&nbsp;YES&nbsp;
<html:checkbox property="gxpno" value="No" onclick="gxb(this.value)"></html:checkbox>&nbsp;NO&nbsp;

</td>
</tr>




<tr id="printer" style="visibility: collapse;">
<td>Type Of Printer<font color="red">*</font></td>
<td>
<html:select property="typeofprinter" styleClass="content" styleId="filterId" >

	<html:option value="">--Select --</html:option>
	<html:option value="DeskJet">DeskJet</html:option>
    <html:option value="LaserJet">LaserJet</html:option>
    <html:option value="Dot Matrix">Dot Matrix</html:option>
   <html:option value="Multi Function">Multi Function</html:option> 

	</html:select>

</td>

<td>Print Volume Per Month</td>
<td><html:text  name="itHelpdeskForm" property="printvolpermonth" ></html:text></td>

</tr>
<tr id="printer1" style="visibility: collapse;">
<td>No.Of Users</td>
<td><html:text  name="itHelpdeskForm" property="users" ></html:text></td>


<td>Suggested Model No. If Any</td>
<td><html:text  name="itHelpdeskForm" property="suggestmodelno" style="width:65%;"></html:text></td>

</tr>


</div>


<tr>
<td>Approximate Value:<font color="red">*</font></td>
<td><html:text  name="itHelpdeskForm" property="apprxvalue" ></html:text></td>


<td>LAN Connectivity:<font color="red">*</font></td>
<td>
<html:checkbox property="lanyes" value="Yes" onclick="lan(this.value)"></html:checkbox>&nbsp;YES&nbsp;
<html:checkbox property="lanno" value="No" onclick="lan(this.value)"></html:checkbox>&nbsp;NO&nbsp;

</td>

</tr>

<tr>
	<td><b>Any Special Requirements</b>:</td>
<td colspan="3"><html:textarea property="anysplreq" name="itHelpdeskForm" cols="80" rows="3">

</html:textarea>	
	</td>
</tr>

<tr>
	<td><b>Purpose:&nbsp;<font color="red">*</font></b></td>
<td colspan="3"><html:textarea property="purpose" name="itHelpdeskForm" cols="80" rows="5">

</html:textarea>	
	</td>
</tr>
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
