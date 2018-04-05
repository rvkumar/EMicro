<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<html xmlns="http://www.w3.org/1999/xhtml">
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
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

.no
{pointer-events: none; 
}
</style>
<script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>

<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script language="javascript">





$(function () {
	$('#timeFrom').timeEntry();
	});


	$(function () {


		$('#timeTo').timeEntry();
	});
	

	$(function() {
		$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
		$('#inlineDatepicker').datepick({onSelect: showDate});
	});	

	
	function deleteTraveller(bid)
	{

		document.forms[0].action="travelrequest.do?method=billerListDelete&bid="+bid;
		document.forms[0].submit();

	}
	


function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

var formOK = false;

function validatePDF(objFileControl){
 var file = objFileControl.value;
 var len = file.length;
 var ext = file.slice(len - 4, len);
 if(ext.toUpperCase() == ".PDF"){
   formOK = true;
 }
 else{
   formOK = false;
   alert("Only PDF file format attachment is allowed.");
   objFileControl.value="";
 }
}

function uploadDocument1()
{	

	
	
	if(document.forms[0].billtype.value=="")
	{
	alert("Please Select Bill Type");
	return false;
	}
	
	if(document.forms[0].travelagentname.value=="")
	{
	alert("Please Enter Travel Agent Name");
	return false;
	}
	
	if(document.forms[0].billno.value=="")
	{
	alert("Please Enter Bill No");
	return false;
	}
	

	if(document.forms[0].billdate.value=="")
	{
	alert("Please Select Bill Date");
	return false;
	}
	
	if(document.forms[0].billamount.value=="")
	{
	alert("Please Select Bill Amount");
	return false;
	}
	
	if(document.forms[0].documentFile.value=="")
	{
	alert("Please Attach the Document");
	return false;
	}

	
	
	
	document.forms[0].action="travelrequest.do?method=billerListUpload";
	document.forms[0].submit();

}






function statusMessage(message){
alert(message);
}








</script>
<body onload="">
<html:form action="travelrequest" enctype="multipart/form-data" method="post" >

<html:hidden property="id" name="travelRequestForm" />
<html:hidden property="travelmode" name="travelRequestForm" styleId="travelmode" />
<html:hidden property="requestNumber" name="travelRequestForm" />


<html:hidden property="bookedby" name="travelRequestForm" />
<input type="text" name="test" value="0" id="test" style="display: none"/>





<div align="center" id="messageID" style="visibility: true;">
				<logic:notEmpty name="travelRequestForm" property="message">
				<script language="javascript">
					statusMessage('<bean:write name="travelRequestForm" property="message" />');
				</script>
			</logic:notEmpty>
			</div>


<% int i =0; %>
				<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Booking List</th>
						</tr>
						<tr>
						<th>Bill Type</th>
						<th>Travel Agent Name</th> 
						<th>Bill No</th>
						<th>Bill Date</th>
						<th>Bill Amount</th>		
						<th>Attachment</th>
						<th>Action</th>
						</tr>
						<logic:notEmpty name="emplist">
						
						<logic:iterate id="abc" name="emplist">
						<logic:notEqual value="Total" name="abc" property="billtype">
						
						
						
						<%i++; %>
						<script>
						document.getElementById("test").value=<%=i%>
						</script>
						
						<tr>
						<td>${abc.billtype}</td>
						<td>${abc.travelagentname} </td>
						<td>${abc.billno}</td>
						<td>${abc.billdate}</td></td>
						<td>${abc.billamount}</td>
						<td><a href="${abc.fileFullPath}" target="_blank">${abc.fileName}</a></td>
						<td>
						<html:hidden property="bookedby" name="travelRequestForm" />
						<logic:empty name="travelRequestForm" property="bookedby">
						<img src='images/delete.png' onclick="deleteTraveller(${abc.id})" title='Remove Row'/>
						</logic:empty>
						</td>
						</tr>
						
						</logic:notEqual>
						
						<logic:equal value="Total" name="abc" property="billtype">
						<tr>
						<th colspan="4">
						<div style="float: right">
						Total
						</div>
						</th>
						<th colspan="3">${abc.billamount}</th>
						</tr>
						
						</logic:equal>
						
						
						
						</logic:iterate>
						
						</logic:notEmpty>
						
					
						</table>
						
						<br/>
						
						<logic:empty name="travelRequestForm" property="bookedby">
						<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						 <tr><th colspan="6">Booking Details</th></tr>
						 <tr><td>Bill Type</td>
						 <td colspan="2">
						 <select name="billtype">
						 <option value="">--Select--</option>
						 <option value="Invoice">Invoice</option>
						 <option value="Debit Note">Debit Note</option>
						 <option value="Credit Note">Credit Note</option>
						 </select>
						 </td><td colspan="2">Agent Name<font color="red" size="3">*</font></td>
						<td>
						
						
			<html:select  property="travelagentname" name="travelRequestForm" >
			<html:option value="">--Select--</html:option>
			<html:options name="travelRequestForm"  property="travIdList" labelProperty="travLabelList"/>
			</html:select>
		
						</td></tr>
							<tr>
						<td>Bill No.<font color="red" size="3">*</font></td><td>
						<input  type="text" name="billno" onkeyup="this.value = this.value.replace(/'/g,'`')"/>
						</td>
						<td>Bill Date<font color="red" size="3">*</font></td><td>
						 <html:text property="billdate"   name="travelRequestForm"  size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker" readonly="true" value=""/> 
						</td>
						<td>Bill Amount<font color="red" size="3">*</font></td><td>
						<input  type="text" name="billamount" onkeyup="this.value = this.value.replace(/'/g,'`')" onkeypress="return isNumber(event)"/>
						</td></tr>
					
						<tr>
						<td>Attachment <font color="red" size="3">*</font></td>
						<td colspan="3">
						<html:file property="documentFile" name="travelRequestForm" onchange="validatePDF(this)"/> 
						</td>
							<td colspan="2">
							
							
							  <html:button property="method" styleClass="rounded" value="Add" onclick="uploadDocument1()" style="align:right;width:100px;"/> 
							
					    </td>
							</tr>	
							
					</table>
					</logic:empty>
						
				<br/>		
				
								
						
						

			
</html:form>
</body>
</html>