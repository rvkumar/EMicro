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
</style>
<script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>



<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script language="javascript">
function deleteDocumentsSelected()
{
var rows=document.getElementsByName("documentCheck");

var checkvalues='';
var uncheckvalues='';
for(var i=0;i<rows.length;i++)
{
if (rows[i].checked)
{
checkvalues+=rows[i].value+',';
}else{
uncheckvalues+=rows[i].value+',';
}
}

if(checkvalues=='')
{
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{
	document.forms[0].action="lta.do?method=deleteDocuments&cValues="+checkvalues;
document.forms[0].submit();
}
}
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}


function uploadDocument1()
{
	document.forms[0].action="travelrequest.do?method=travellerListUpload";
	document.forms[0].submit();

}
function deleteTraveller(id)
{

document.forms[0].action="travelrequest.do?method=travellerListDelete&id="+id;
	document.forms[0].submit();

}




function statusMessage(message){
alert(message);
}

$(function() {
	$('#passportexpirydate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

var a =5;

</script>
<body onload="">
<html:form action="travelrequest" enctype="multipart/form-data" method="post" >

<html:hidden property="id" name="travelRequestForm" />
<html:hidden property="travelmode" name="travelRequestForm" />


<div align="center" id="messageID" style="visibility: true;">
				<logic:present name="extIncomeForm" property="message2">
			
				<script language="javascript">
					statusMessage('<bean:write name="extIncomeForm" property="message2" />');
					</script>
				</logic:present>
				<logic:present name="extIncomeForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="extIncomeForm" property="message" />');
					</script>
					
				</logic:present>
			</div>

<!-- //air -->


<input type="text" name="test" value="0" id="test"/>

<% int i =0; %>

						


<logic:equal value="Air" name="travelRequestForm" property="travelmode">
						<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<th colspan="6"><center>Traveller Details</center></th>
						<tr><td>Traveller Type: </td> 
						<td>
						<select name="guest_Type">
  						<option value="employee">Employee</option>
  						<option value="guest">Guest</option>
  						<option value="doctor">Doctor</option>
						</select></td>
						<td>Employee No.:</td><td><input type="text" name="guest_pernr" onkeypress="return isNumber(event)"/></td>
						<td>Title: </td><td><select name="guest_Title">
  						<option value="Mr">Mr</option>
  						<option value="Mrs">Mrs</option>
						<option value="Dr">Dr</option>
  						
						</select></td>
						</tr>
						<tr>
						<td>Name: </td><td><input type="text"  name="guestName" /></td>
						
						<td>Gender: </td> <td>
						<select name="gender">
  						<option value="male">Male</option>
  						<option value="female">Female</option>
						<option value="other">Other</option>
						</select></td>
						<td>e-Mail: </td><td><input type="text" name="email_Guest" /></td>
						</tr>
						<tr>
						<td>Contact No: </td><td><input type="text" name="guestContactNo"/></td>
						<td>Company Name: </td><td><input type="text" name="guest_Company"/></td>
						
						<td>Passport No.</td><td><input type="text" name="passportno"/></td>
						</tr>
						<tr>
						<td>Passport Expiry Date: </td><td><input type="text" name="passportexpirydate" id="passportexpirydate"/></td>
						<td>Visa Number & Date: </td><td><input type="text" name="guest_Visano" /></td>
						<td>DOB / Age: </td><td><input type="text" name="guest_DOB"/></td>
						</tr>
						<tr>
						<td>Meal Preference: </td><td><input type="text" name="guest_Meal"/></td>
						<td>Attachment: </td><td>
						<html:file property="documentFile" name="travelRequestForm"/> 
			                     
						</td>
						
						<td >
						<html:button property="method" styleClass="rounded"  value="Add" onclick="uploadDocument1();" style="align:right;width:100px;"/>
						</td>
						 </tr>
						</table>
						
						<br/>
						<div style="width: 800px;overflow-y:scroll">
						<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<tr>
						<th>Traveller Type</th> <th>Employee No</th>
						<th>Title</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact No</th> <th>Company Name</th>
						<th>Passport No</th> <th>Passport Expiry Date</th>
						<th>Visa Number & Date</th> <th>Date Of Birth / Age</th>
						<th>Meal Preference</th><th>Attachment</th>
						<th>Action</th>
						</tr>
						<logic:notEmpty name="emplist">
						
						<logic:iterate id="abc" name="emplist">
						<%i++; %>
						<script>
						document.getElementById("test").value=<%=i%>
						</script>
						
						<tr>
						<td>${abc.guest_Type}</td>
						<td>${abc.guest_pernr}</td>
						<td>${abc.guest_Title}</td>
						<td>${abc.guestName}</td>
						<td>${abc.gender}</td>
						<td>${abc.email_Guest}</td>
						<td>${abc.guestContactNo}</td>
						<td>${abc.guest_Company}</td>
						<td>${abc.passportno}</td>
						<td>${abc.passportexpirydate}</td>
						<td>${abc.guest_Visano}</td>
						<td>${abc.guest_DOB}</td>
						<td>${abc.guest_Meal}</td>
						<td><a href="${abc.fileFullPath}" target="_blank">${abc.fileName}</a></td>
						<td>
						<img src='images/delete.png' onclick="deleteTraveller(${abc.id})" title='Remove Row'/>
						</td>
						</tr>
						
						
						
						</logic:iterate>
						
						</logic:notEmpty>
						
					
						</table>
						</div>

						</logic:equal>
		<!-- // Road -->				
						
						<logic:equal value="Road" name="travelRequestForm" property="travelmode">
						<table class="bordered" style="position: relative;left: 2%;width: 60%;">
						<tr>
						<th colspan="16"><center>Traveller Details</center></th>
						</tr>
						<tr><td>Traveller Type</td> 
						<td>
						<select name="guest_Type">
  						<option value="employee">Employee</option>
  						<option value="guest">Guest</option>
  						<option value="doctor">Doctor</option>
						</select></td>
						<td>Employee No: </td><td><input type="text" name="guest_pernr" onkeypress="return isNumber(event)"/></td>
						<td>Title: </td><td><select name="guest_Title">
  						<option value="Mr">Mr</option>
  						<option value="Mrs">Mrs</option>
						<option value="Dr">Dr</option>
  						
						</select></td>
						</tr>
						<tr>
						<td>Name: </td><td><input type="text" name="guestName"/></td> 
						
						<td>Gender: </td> <td>
						<select name="gender">
  						<option value="male">Male</option>
  						<option value="female">Female</option>
						<option value="other">Other</option>
						</select></td>
						<td>e-Mail:</td><td><input type="text" name="email_Guest" /></td>
						</tr>
						<tr>
						<td>Contact No: </td><td><input type="text" name="guestContactNo"/></td>
						<td>Company Name: </td><td><input type="text" name="guest_Company"/></td>
						<td>DOB / Age: </td><td><input type="text" name="guest_DOB"/></td>
						</tr>
						<tr>
						<td>Meal Preference: </td><td><input type="text" name="guest_Meal"/></td>
						<td>Attachment: </td><td>
						
						<html:file property="documentFile" name="travelRequestForm"/> 
			                     
						</td>
						
						<td colspan="8">
						<html:button property="method" styleClass="rounded"  value="Add" onclick="uploadDocument1();" style="align:right;width:100px;"/>
						</td>
						 </tr>
						</table>
						
						</br>
						
						
						
							<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<tr>
						<th>Traveller Type</th> <th>Employee No</th>
						<th>Title</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact No</th> <th>Company Name</th>
						 <th>Date Of Birth / Age</th>
						 <th>Attachment</th>
						<th>Action</th>
						</tr>
						<logic:notEmpty name="emplist">
						
						<logic:iterate id="abc" name="emplist">
						
						<%i++; %>
						<script>
						document.getElementById("test").value=<%=i%>
						</script>
						<tr>
						<td>${abc.guest_Type}</td>
						<td>${abc.guest_pernr}</td>
						<td>${abc.guest_Title}</td>
						<td>${abc.guestName}</td></td>
						<td>${abc.gender}</td>
						<td>${abc.email_Guest}</td>
						<td>${abc.guestContactNo}</td>
						<td>${abc.guest_Company}</td>
						<td>${abc.guest_DOB}</td>
						<td><a href="${abc.fileFullPath}"  target="_blank">${abc.fileName}</a></td>
						<td>
						<img src='images/delete.png'   onclick="deleteTraveller(${abc.id})" title='Remove Row'/>
						</td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
						</table>
						
						</logic:equal>
						
						
						<!-- //Rail -->	
		
						<logic:equal value="Rail" name="travelRequestForm" property="travelmode">
						
						<table class="bordered" style="position: relative;left: 2%;width: 60%;">
						<tr>
						<th colspan="16"><center>Traveller Details</center></th>
						</tr>
						<tr><td>Traveller Type</td> 
						<td>
						<select name="guest_Type">
  						<option value="employee">Employee</option>
  						<option value="guest">Guest</option>
  						<option value="doctor">Doctor</option>
						</select></td>
						<td>Employee No: </td><td>
						<input type="text" name="guest_pernr" onkeypress="return isNumber(event)"/>
						
						</td>
						<td>Title</td><td><select name="guest_Title">
  						<option value="Mr">Mr</option>
  						<option value="Mrs">Mrs</option>
						<option value="Dr">Dr</option>
  						
						</select></td>
						</tr>
						<tr>
						<td>Name: </td><td><input type="text" name="guestName"/></td> 
						
						<td>Gender: </td> <td>
						<select name="gender">
  						<option value="male">Male</option>
  						<option value="female">Female</option>
						<option value="other">Other</option>
						</select></td>
						<td>e-Mail: </td><td><input type="text" name="email_Guest" /></td>
						</tr>
						<tr>
						<td>Contact No:</td><td><input type="text" name="guestContactNo"/></td>
						<td>Company Name: </td><td><input type="text" name="guest_Company"/></td>
						<td>DOB / Age: / Age</td><td><input type="text" name="guest_DOB"/></td>
						</tr>
						<tr>
						<td>Meal Preference: </td><td><input type="text" name="guest_Meal"/></td>
						<td>Attachment: </td><td>
						<html:file property="documentFile" name="travelRequestForm"/> 
			                     
						</td>
						
						<td colspan="8">
						<html:button property="method" styleClass="rounded"  value="Add" onclick="uploadDocument1();" style="align:right;width:100px;"/>
						</td>
						 </tr>
						</table>
						
						
						</br>
						
							<table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Traveller List</th>
						</tr>
						<tr>
						<th>Traveller Type</th> <th>Employee No</th>
						<th>Title</th> <th>Name</th>
						<th>Gender</th> <th>Email</th>
						<th>Contact No</th> <th>Company Name</th>
						 <th>Date Of Birth / Age</th>
						 <th>Attachment</th>
						<th>Action</th>
						</tr>
						<logic:notEmpty name="emplist">
					
						<logic:iterate id="abc" name="emplist">
							<%i++; %>
						<script>
						document.getElementById("test").value=<%=i%>
						</script>
						<tr>
						<td>${abc.guest_Type}</td>
						<td>${abc.guest_pernr}</td>
						<td>${abc.guest_Title}</td>
						<td>${abc.guestName}</td>
						<td>${abc.gender}</td>
						<td>${abc.email_Guest}</td>
						<td>${abc.guestContactNo}</td>
						<td>${abc.guest_Company}</td>
						<td>${abc.guest_DOB}</td>
						<td><a href="${abc.fileFullPath}"  target="_blank">${abc.fileName}</a></td>
						<td>
						<img src='images/delete.png'   onclick="deleteTraveller(${abc.id})" title='Remove Row'/>
						</td>
						</tr>
						</logic:iterate>
						</logic:notEmpty>
						</table>
						
						</logic:equal>	
								
						
						

			
</html:form>
</body>
</html>