<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" >

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
function addRow(){
var table = document.getElementById("myTable");
var new_row = table.rows[1].cloneNode(true);
      
      var len=table.rows.length;
    
      var inp1 = new_row.cells[0].getElementsByTagName('input')[0];
      inp1.id = 'employer'+len;
      inp1.name = 'employer';
      inp1.value = '';
      //saleryID1,saleryDescription,saleryType,startDate,endDate,totalAmount,remarks
        var inp2 = new_row.cells[1].getElementsByTagName('input')[0];
      inp2.id = 'saleryID'+len;
      inp2.value = '';
      
      var inp3 = new_row.cells[2].getElementsByTagName('input')[0];
      inp3.id = 'saleryDescription'+len;
      inp3.name = 'saleryDescription';
      inp3.value = '';
      
      var inp4 = new_row.cells[3].getElementsByTagName('input')[0];
      inp4.id = 'saleryType'+len;
      inp4.value = '';
      
      var inp5 = new_row.cells[4].getElementsByTagName('input')[0];
      inp5.id = 'startDate'+len;
     
      inp5.value = '';
      
      var inp6 = new_row.cells[5].getElementsByTagName('input')[0];
      inp6.id = 'endDate'+len;
      inp6.value = '';
      
      var inp6 = new_row.cells[6].getElementsByTagName('input')[0];
      inp6.id = 'totalAmount'+len;
      inp6.value = '';
      
      var inp7 = new_row.cells[7].getElementsByTagName('input')[0];
      inp7.id = 'remarks'+len;
      inp7.value = '';
       table.appendChild( new_row );
       
}
function deleteRow()
{
  
	try {
        var table = document.getElementById("myTable");
        var rowCount = table.rows.length;

        for(var i=0; i<rowCount; i++) {
            var row = table.rows[i];
           
            var chkbox = row.cells[8].childNodes[0];
            if(null != chkbox && true == chkbox.checked) {
                if(rowCount <= 2) {
                    alert("Cannot delete all the rows.");
                    break;
                }
                table.deleteRow(i);
                rowCount--;
                i--;
            }


        }
        }catch(e) {
            alert(e);
        }
 
     
    }
function saveDetails(){
if(document.forms[0].fiscalYear.value==""){
	alert("Please Select Fiscal Year");
	document.forms[0].fiscalYear.focus();
	return false;
}
if(document.forms[0].employer.value==""){
	alert("Pleaes Enter Employer");
	document.forms[0].employer.focus();
	return false;
}
/* if(document.forms[0].saleryID.value==""){
	alert("Pleaes Enter Salery Id");
	document.forms[0].saleryID.focus();
	return false;
} */

if(document.forms[0].saleryType.value==""){
	alert("Pleaes Enter salery Type");
	document.forms[0].saleryType.focus();
	return false;
}
if(document.forms[0].startDate.value==""){
	alert("Please Select Start Date");
	document.forms[0].startDate.focus();
	return false;
}
if(document.forms[0].endDate.value==""){
	alert("Please Select End Date");
	document.forms[0].endDate.focus();
	return false;
}
if(document.forms[0].startDate.value!="" && document.forms[0].endDate.value!=""){
	var str1 = document.forms[0].startDate.value;
	var str2 = document.forms[0].endDate.value;
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
if(document.forms[0].totalAmount.value==""){
	alert("Please Enter Total Amount");
	document.forms[0].totalAmount.focus();
	return false;
}


//check same employer diff salry id validation

var reqlen=document.getElementsByName("emplo").length;

for(var h=0;h<reqlen;h++)
	{
	if(document.getElementsByName("emplo")[h].value==document.forms[0].employer.value)
		{
		
		alert("Employer already Added");
		return false;
		}
	}



document.getElementById("salary").style.display="";
document.getElementById("subbutton").style.display="";
var saltype="";
var emplo=document.forms[0].employer.value;
	/* var salid=document.forms[0].saleryID.value; */
var salx=document.forms[0].saleryType.value;	

	if(salx=="I")
	{
	 saltype="Income";
	}
	
	else
	{
	 saltype="Deduction";
	}
	
	
	
	var start=document.forms[0].startDate.value;
	var end=document.forms[0].endDate.value;
	var amount=document.forms[0].totalAmount.value;
	var remarks=document.forms[0].remarks.value;

	
	var table = document.getElementById('salary');
	var row = table.insertRow(1);
 var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    var cell5 = row.insertCell(4);
    var cell6 = row.insertCell(5);
    var cell7 = row.insertCell(6);
  /*    var cell8 = row.insertCell(7); */
    
    
    cell1.innerHTML=emplo;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=emplo;
    element1.name="emplo";
    cell1.appendChild(element1);
    
    
   /*  cell2.innerHTML=salid;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=salid;
    element1.name="sal";
    cell2.appendChild(element1); */
    
    
    
    cell2.innerHTML=saltype;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=saltype;
    element1.name="salty";
    cell2.appendChild(element1);
    
    
    
    cell3.innerHTML=start;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=start;
    element1.name="st";
    cell3.appendChild(element1);
    
    
    cell4.innerHTML=end;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=end;
    element1.name="en";
    cell4.appendChild(element1);
    
    
    cell5.innerHTML=amount;
    
    var element1= document.createElement("input");
    element1.type="hidden";
    element1.value=amount;
    element1.name="am";
    cell5.appendChild(element1);
    
    
    cell6.innerHTML=remarks;
    
    var element2= document.createElement("input");
    element2.type="hidden";
    element2.value=remarks;
    element2.name="rem";
    cell6.appendChild(element2);

    cell7.innerHTML="<img src='images/delete.png'   onclick='deleteRow(this,1)' title='Remove Row'/>";
	
}
function editPrevIncome(id){
	document.forms[0].action="previousIncome.do?method=editPrevIncome&ID="+id;
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
	document.forms[0].action="previousIncome.do?method=deletePrevIncome&cValues="+checkvalues;
	document.forms[0].submit();
}
}
}
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}
function submitForApproval(){

 var fiscalYear=document.forms[0].fiscalYear.value;
	 if(fiscalYear==""){
		 alert("Please Select Fiscal Year");
		 document.forms[0].fiscalYear.focus();
		 return false;
	 }
	document.forms[0].action="previousIncome.do?method=submitForApproval";
	document.forms[0].submit();
}
function cancelRequest(){
	var agree = confirm('Are You Sure To Cancel Request');
	if(agree)
	{
		document.forms[0].action="previousIncome.do?method=cancelRequest";
		document.forms[0].submit();
	}
	
}
function displayFiscalYear(){
	var fiscalYear=document.forms[0].fiscalYear.value;
	if(fiscalYear=="")
		document.forms[0].fiscalYearDesc.value="";
	if(fiscalYear!=""){
		var nextYear=parseInt(fiscalYear);
		nextYear=nextYear+1;
		document.forms[0].fiscalYearDesc.value="April-"+fiscalYear+" to March "+nextYear;
	}
}
function changeMousePointer(){
	
	document.forms[0].fiscalYear.focus();
}
function setSaleryDesc(invCode){

	var act="";
	 var newdata=invCode.substr(invCode.length - 1);
     if(newdata=="I")
    	 act="Income";
     else
    	 act="Deduction";
	
	document.forms[0].saleryDescription.value=invCode;
	document.forms[0].saleryType.value=act;
}

function updateDetails(){
	document.forms[0].action="previousIncome.do?method=updateDetails";
	document.forms[0].submit();
}


function deleteRow(element,value)
{
	 var table="";
	
	
		 table = document.getElementById('salary');
		
	table.deleteRow(element.parentNode.parentNode.rowIndex);
	
	
    }
    
    
    function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
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
</head>
<body onload="self.scrollTo(0,0)">
		<div align="center" id="messageID" style="visibility: true;">
				
				<logic:present name="previousIncomeForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="previousIncomeForm" property="message" />');
					</script>
					
				</logic:present>
			</div>
<html:form action="/previousIncome.do" enctype="multipart/form-data" onsubmit="">
<table class="bordered"  >

 <tr><th colspan="8"><big>Requester Details</big></th></tr>
						<tr><td>Employee Number</td><td><bean:write name="previousIncomeForm" property="employeeNo" /></td><td>Employee Name</td><td><bean:write name="previousIncomeForm" property="employeeName" /></td>
						<td>Department</td><td><bean:write name="previousIncomeForm" property="department" /></td><td>Designation</td><td><bean:write name="previousIncomeForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><bean:write name="previousIncomeForm" property="doj" /></td><td>Location</td><td colspan=""><bean:write name="previousIncomeForm" property="location" /></td>

<td>Staff Category</td><td><bean:write name="previousIncomeForm" property="staffCategory" /></td><th>Fiscal Year <font color="red" size="2" >*</font></th>
<td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="previousIncomeForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalYearDesc"  style="border:0;width:160px;" readonly="true" />
</td></tr>
</table>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="3"><big>Approvers Details</big></th></tr>
	<tr><th><center>Priority</center></th><th><center>Approver Name</center></th><th><center>Department</center></th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.approverName }</td><td>${abc.apprDept }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
<br/>

<table class="bordered">
<tr>
<th colspan="8"><center><b><big>Previous Income</big></b></center></th>
</tr>
<tr><td>Employer<font color="red" size="2" >*</font></td><td><html:text property="employer" onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
<%-- <td>Salary ID<font color="red" size="2" >*</font></td>
<td><html:select property="saleryID" onchange="setSaleryDesc(this.value)">
<html:option value="">Select</html:option>
		<html:options name="previousIncomeForm"  property="salIdList" labelProperty="salIdList"/>
</html:select>
</td> --%>
<%-- <td>Salary Description</td>
<td>
<html:select property="saleryDescription" disabled="true">
<html:option value="">Select</html:option>
<html:options name="previousIncomeForm"  property="salIdList" labelProperty="salLabelList"/>
</html:select>

</td> --%>
<td>Salary Type<font color="red" size="2" >*</font></td><td>

<select name="saleryType">
	<option value="">Select</option>
	<option value="I">Income</option>
	<option value="D">Deduction</option>
	</select>

</td>


<td>Start Date <font color="red" size="2" >*</font></td><td><html:text property="startDate" onfocus="popupCalender(startDate)" readonly="true"/> <img src="images/date_icon.gif" align="absmiddle" /></td>

<td>End Date <font color="red" size="2" >*</font></td><td><html:text property="endDate" onfocus="popupCalender(endDate)" readonly="true"/> <img src="images/date_icon.gif" align="absmiddle" /></td>
</tr>
<tr><td>Total Amount <font color="red" size="2" >*</font></td><td><html:text property="totalAmount"  onkeyup="this.value = this.value.replace(/'/g,'`')" onkeypress="return isNumber(event)"  style="text-align:right;"/></td>
<td>Remarks</td><td colspan="6"><html:text property="remarks" style="width:500px;text-transform:uppercase" maxlength="50"  onkeyup="this.value = this.value.replace(/'/g,'`')" /></td>
</tr>
<tr><td colspan="8"><center>
<logic:empty name="addDetails">
<html:button property="method" value="Add Details" onclick="saveDetails()" styleClass="rounded"/>&nbsp;
<%-- <html:reset value="Clear" styleClass="rounded"></html:reset> --%>
</logic:empty>
<logic:notEmpty name="updateDetails">
<html:button property="method" value="Update Details" onclick="updateDetails()" styleClass="rounded"/>&nbsp;
</logic:notEmpty>


</center></td></tr>

</table>
<br/>

	
	<table class="bordered" id="salary" style="display: none;">
	<tr><th>Employer</th><th>Salary Type</th><th>Start Date</th><th>End Date</th><th>Total Amount</th><th>Remarks</th><th>Delete</th>
	</tr>
	
	
		
	
	
	</table>

</br>
<iframe src="previousIncome.do?method=showUploadFields" name="contentPage1" width="105%" 
        			frameborder="1" scrolling="yes" id="the_iframe">
        </iframe>
        
        <br/>
	<table id="subbutton" style="display: none;"><tr>
		<td colspan="8"><center><html:button property="method" value="Submit For Approval " onclick="submitForApproval()" styleClass="rounded"/>&nbsp;
		<%-- <html:button property="method" value="Cancel Request" styleClass="rounded" onclick="cancelRequest()"/></center> --%>
		</td>
	</tr></table>
	
	<div>&nbsp;</div>


	
		
	

<!-- <table class="bordered" style="width: 80%;position: fixed;left: 2%;" id="myTable">
<tr>
<th>Employer</th><th>Salary ID</th><th>Salary Description</th><th>Salary Type</th><th>Start Date</th><th>End Date</th><th>Total Amount</th>
<th>Remarks</th>
<td width="200px;"><a href="#" ><img src="images/add-items.gif" id="addmorePOIbutton" onclick="addRow()" title="Add Row"/></a></td>
<td><a href="#" ><img src="images/delete.png"   onclick="deleteRow()" title="Remove Row"/></a></td>
</tr>
<tr>
	<td> <input type="text" name="employer" id="employer1" /> </td>
	<td><input type="text" name="saleryID" id="saleryID1" /></td> 
	<td><input type="text" name="saleryDescription" id="saleryDescription1" /></td>
	<td><input type="text" name="saleryType" id="saleryType1" /></td>
	<td><input type="text" name="startDate" id="startDate1" onfocus="popupCalender(this.id)"/></td>
	<td> <input type="text" name="endDate" id="endDate1" onfocus="popupCalender(this.id)"/></td>
	<td><input type="text" name="totalAmount" id="totalAmount1" /></td>
	<td><input type="text" name="remarks" id="remarks1" /></td>
	<td><INPUT type="checkbox" name="chk"/></td>
</tr>
</table> -->

<html:hidden property="requestNo" />
<html:hidden property="id" />


</html:form>
</body>
</html>