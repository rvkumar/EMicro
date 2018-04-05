<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
   td{font-family: Arial; font-size: 12;}
 </style>
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/sorttable.js"></script>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<script type="text/javascript">
	function popupCalender(param)
	{
		var toD = new Date();

		var cal = new Zapatec.Calendar.setup({
		inputField : param, // id of the input field
		singleClick : true, // require two clicks to submit
		ifFormat : "%d/%m/%Y", // format of the input field
		showsTime : false, // show time as well as date
		button : "button2", // trigger button
		});
	}
	function addAccommodation(){
		
		
		
		
	if(document.forms[0].fromDate.value==""){
	alert("Please Enter From Date");
	document.forms[0].fromDate.focus();
	return false;
	}
	if(document.forms[0].toDate.value==""){
	alert("Please Enter To Date");
	document.forms[0].toDate.focus();
	return false;
	}
	if(document.forms[0].fromDate.value!="" && document.forms[0].toDate.value!=""){
		var str1 = document.forms[0].fromDate.value;
		var str2 = document.forms[0].toDate.value;
		var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 
		var date1 = new Date(yr1, mon1, dt1); 
		var date2 = new Date(yr2, mon2, dt2); 

		if(date2 < date1) 
		{ 
		    alert("Please Select Valid Date Range");
		    document.forms[0].endDate.value="";
		     document.forms[0].endDate.focus();
		     return false;
		}
		   }
	
	
	var amount=parseInt(document.forms[0].amount.value);
	
	if(amount<=0){
	alert("Please Enter Some Amount");
	document.forms[0].amount.focus();
	return false;
	}
	if(document.forms[0].accommodationType.value==""){
	alert("Please Enter Accommodation Type");
	document.forms[0].accommodationType.focus();
	return false;
	}
	
	if(amount>=100000)
		{
	if(document.forms[0].panNo.value==""){
	alert("Please Enter Pan No");
	document.forms[0].panNo.focus();
	return false;
	}
	}
	if(document.forms[0].landName.value==""){
	alert("Please Enter Land Name");
	document.forms[0].landName.focus();
	return false;
	}
	if(document.forms[0].city.value==""){
	alert("Please Enter city");
	document.forms[0].city.focus();
	return false;
	}
	if(document.forms[0].address1.value==""){
	alert("Please Enter Address 1");
	document.forms[0].address1.focus();
	return false;
	}
	
	
	document.getElementById("address").style.display="";
	document.getElementById("subbutton").style.display="";
	
	var fromdate=document.forms[0].fromDate.value;
	var todate=document.forms[0].toDate.value;
	var amont=document.forms[0].amount.value;
	var acctype=document.forms[0].accommodationType.value;
	var pan=document.forms[0].panNo.value;
	var landname=document.forms[0].landName.value;
	var cityy=document.forms[0].city.value;
	var add1=document.forms[0].address1.value;
	var add2=document.forms[0].address2.value;
	var add3=document.forms[0].address3.value;
	var met=document.forms[0].metrocity.checked;

	
	var table = document.getElementById('address');
	var row = table.insertRow(1);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
    var cell8 = row.insertCell(7);
    var cell9 = row.insertCell(8);
    var cell10 = row.insertCell(9);
    var cell11 = row.insertCell(10);
    var cell12 = row.insertCell(11);
	
	
	cell1.innerHTML=fromdate;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=fromdate;
    element1.name="fd";
    cell1.appendChild(element1);
    
    
    
    	cell2.innerHTML=todate;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=todate;
    element1.name="td";
    cell2.appendChild(element1);
    
    
    	cell3.innerHTML=amont;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=amont;
    element1.name="am";
    cell3.appendChild(element1);
    
    
    
    	cell4.innerHTML=acctype;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=acctype;
    element1.name="acc";
    cell4.appendChild(element1);
    
    
    
	cell5.innerHTML=pan;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=pan;
    element1.name="pn";
    cell5.appendChild(element1);
    
    
    
	cell6.innerHTML=landname;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=landname;
    element1.name="la";
    cell6.appendChild(element1);
    
    
    
	cell7.innerHTML=cityy;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=cityy;
    element1.name="ci";
    cell7.appendChild(element1);
    
    
	cell8.innerHTML=add1;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=add1;
    element1.name="a1";
    cell8.appendChild(element1);
    
	cell9.innerHTML=add2;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=add2;
    element1.name="a2";
    cell9.appendChild(element1);
    
    
	cell10.innerHTML=add3;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=add3;
    element1.name="a3";
    cell10.appendChild(element1);
    
	
	var ty="";
	if(met==true)
		{
		ty="Yes";
		}
		else
			{
			ty="No";
			}
	cell11.innerHTML=ty;
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=ty;
    element1.name="met";
    cell11.appendChild(element1);
    
    
    cell12.innerHTML="<img src='images/delete.png'   onclick='deleteRow(this,1)' title='Remove Row'/>";
	
	}
	function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";
	}
	function save(){
	document.forms[0].action="claimTRLP.do?method=save";
	document.forms[0].submit();
	}
	function SubmitForApproval(){
	 var fiscalYear=document.forms[0].fiscalYear.value;
	 if(fiscalYear==""){
		 alert("Please Select Fiscal Year");
		 document.forms[0].fiscalYear.focus();
		 return false;
	 }
	document.forms[0].action="claimTRLP.do?method=submitForApproval";
	document.forms[0].submit();
	}
	function editAccomDetails(reqId)
	{
		document.forms[0].action="claimTRLP.do?method=editAccomDetaisl&reqID="+reqId;
		document.forms[0].submit();
	}
	function modifyAccomDetails()
	{
		document.forms[0].action="claimTRLP.do?method=modifyAccomDetails";
		document.forms[0].submit();
	}
	function deleteRent()
	{
		document.forms[0].action="claimTRLP.do?method=deleteRent";
		document.forms[0].submit();
	}
	function deleteRent()
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
	document.forms[0].action="claimTRLP.do?method=deleteRent&cValues="+checkvalues;
document.forms[0].submit();
}
}
}
	
function displayFiscalYear(){
	var fiscalYear=document.forms[0].fiscalYear.value;
	if(fiscalYear=="")
		document.forms[0].fiscalyearDesc.value="";
	if(fiscalYear!=""){
		var nextYear=parseInt(fiscalYear);
		nextYear=nextYear+1;
		document.forms[0].fiscalyearDesc.value="April-"+fiscalYear+" to March "+nextYear;
		
		var doj=document.forms[0].doj.value;
		var t1=(doj).split('/');
		var t2=("01/04/"+fiscalYear+"").split('/');
		
		var d1 = Date.parse(t1[2]+"-"+t1[1]+"-"+t1[0]);
		var d2 = Date.parse(t2[2]+"-"+t2[1]+"-"+t2[0]);
		

		
		if (d1 > d2) {
		
			
			document.forms[0].fromDate.value=doj;
			}
		else
			{
			document.forms[0].fromDate.value="01/04/"+fiscalYear+"";
			}
	
		document.forms[0].toDate.value="31/03/"+nextYear+"";
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

function deleteRow(element,value)
{
	 var table="";
	
	
		 table = document.getElementById('address');
		
	table.deleteRow(element.parentNode.parentNode.rowIndex);
	
	
    }
    
function statusMessage(message){
	alert(message);
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

<script language="javascript">

</script>
</head>
<body>
		<div align="center" id="messageID" style="visibility: true;">
			
				<logic:present name="claimTRLPForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="claimTRLPForm" property="message" />');
					</script>
					
				</logic:present>
			</div>
			
<html:form action="claimTRLP" enctype="multipart/form-data" method="post">
		
<table class="bordered">
<tr>
<th colspan="8"><center><b><big> HRA Exemption</big></b></center></th>
</tr>
	  <tr><th colspan="8"><big>Requester Details</big></th></tr>
						<tr><td>Employee Number</td><td><bean:write name="claimTRLPForm" property="employeeNo" /></td><td>Employee Name</td><td><bean:write name="claimTRLPForm" property="employeeName" /></td>
						<td>Department</td><td><bean:write name="claimTRLPForm" property="department" /></td><td>Designation</td><td><bean:write name="claimTRLPForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><html:hidden property="doj" name="claimTRLPForm"/><bean:write name="claimTRLPForm" property="doj" /></td><td>Location</td><td colspan=""><bean:write name="claimTRLPForm" property="location" /></td>

<td>Staff Category</td><td><bean:write name="claimTRLPForm" property="staffCategory" /></td><th>Fiscal Year <font color="red" size="2" >*</font></th>
<td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="claimTRLPForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalyearDesc"  style="border:0;width:160px;" readonly="true" />
</td></tr>
</table>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6"><big>Approvers Details</big></th></tr>
	<tr><th><center>Priority</center></th><th><center>Approver Name</center></th><th><center>Department</center></th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.approverName }</td><td>${abc.apprDept }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
<table class="bordered">
<tr>
<th colspan="4"><big>Rent Details</big></th>
</tr>
<tr><td>Effective From <font color="red">*</font></td>
<td><html:text property="fromDate" styleId="fromDate" onfocus="popupCalender(fromDate)" readonly="true" styleClass="rounded"></html:text>
<img src="images/date_icon.gif" align="absmiddle" /></td>
<td>To Date <font color="red">*</font></td>
<td><html:text property="toDate" styleId="toDate" onfocus="popupCalender(toDate)" readonly="true" styleClass="rounded"></html:text>
<img src="images/date_icon.gif" align="absmiddle" />
</td></tr>
<tr><td>Total Amount (per annum)<font color="red">*</font></td><td><html:text property="amount" styleClass="rounded" onkeypress="return isNumber(event)" onkeyup="this.value = this.value.replace(/'/g,'`')" style="text-align:right;"></html:text>
</td><td>Metro city</td><td><html:checkbox property="metrocity" name="claimTRLPForm"></html:checkbox></td>
</tr>
<tr><th colspan="4">Accommodation Details<font color="red"> (If Total Amount is above 1 lak then Land Lords Pan No. is mandatory)</font></th></tr>
<tr>
<td>Accommodation Type <font color="red">*</font></td><td>

<html:select property="accommodationType" >
<html:option value="">---Select---</html:option>

<html:option value="Furnished">Furnished</html:option>
<html:option value="Unfurnished">Unfurnished</html:option>
</html:select>

</td>
<td>Land Lords Pan No. <font color="red">*</font></td><td><html:text property="panNo" styleClass="rounded" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:text></td>
</tr>
<tr>
	<td>Land Lords Name <font color="red">*</font></td><td><html:text property="landName" styleClass="rounded" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:text></td>
	<td>Land Lords City <font color="red">*</font></td><td><html:text property="city" styleClass="rounded" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:text>
	</td>
</tr>
<tr>
	<td>Land Lords Address 1 <font color="red">*</font></td><td><html:textarea property="address1" styleClass="rounded" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:textarea></td>
	<td>Land Lords Address 2</td><td><html:textarea property="address2" styleClass="rounded" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:textarea>
	</td>
</tr>
<tr>
	<td>Land Lords Address 3</td><td colspan="4"><html:textarea property="address3" styleClass="rounded" onkeyup="this.value = this.value.replace(/'/g,'`')"></html:textarea></td>
</tr>
<tr>
<td colspan="4">
<logic:empty name="addDetails">
<center><html:button property="method" value="Add Details" styleClass="rounded" onclick="addAccommodation()"></html:button></center>
</logic:empty>
<logic:notEmpty name="modify">
<center><html:button property="method" value="Modify Details" styleClass="rounded" onclick="modifyAccomDetails()"></html:button></center>

</logic:notEmpty>
</td>
</tr>
</table>

<html:hidden property="requestNo"/>
<br/>

  

<table class="bordered sortable" id="address" style="display: none;">
<tr><th>From Date</th><th>To Date</th><th>Payable Amount</th><th>Accm.Type</th><th>Land Lords Pan No</th><th>Land Lords Name</th><th>Land Lords City</th><th>Land Lords Addr1</th><th>Land Lords Addr2</th><th>Land Lords Addr3</th><th>Metro</th><th>Delete</th></tr>

</table>

<br/>
<iframe src="claimTRLP.do?method=showUploadFields" name="contentPage1" width="105%" 
        			frameborder="1" scrolling="yes" id="the_iframe">
        </iframe>
	 <br/> <br/>	
<table id="subbutton" style="display: none;">
<tr>
<td colspan="7"><center><html:button property="method" value="Submit  For Approval" onclick="SubmitForApproval()" styleClass="rounded" />
<%-- <html:button property="method" value="Save As Draft" onclick="save()" styleClass="rounded"></html:button> --%>

</center></td>
</tr>
</table>


<div>&nbsp;</div>


	
	<div>&nbsp;</div>
	

        
       

<html:hidden property="requiredID"/>

</html:form>
</body>
</html>