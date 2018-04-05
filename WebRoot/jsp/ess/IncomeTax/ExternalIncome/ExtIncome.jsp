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
      
   var len = '';       
    var elements = document.getElementsByName("saleryType");
    var reqlen = elements.length;      	
    len =parseInt(elements[reqlen-1].id.substr(elements[reqlen-1].id.indexOf(":") + 1))+1;
 
   
         var inp4 = new_row.cells[0].getElementsByTagName('select')[0];
      inp4.id = 'saleryType:'+len;
      inp4.value = '';
      
      var inp3 = new_row.cells[1].getElementsByTagName('input')[0];
      inp3.id = 'desc'+len;
      inp3.name = 'desc';
      inp3.value = '';
      
     
      
      var inp5 = new_row.cells[2].getElementsByTagName('input')[0];
      inp5.id = 'startDate'+len;
     
      inp5.value = '';
      
      var inp6 = new_row.cells[3].getElementsByTagName('input')[0];
      inp6.id = 'endDate'+len;
      inp6.value = '';
      
      var inp6 = new_row.cells[4].getElementsByTagName('input')[0];
      inp6.id = 'totalAmount'+len;
      inp6.value = '';
      
      var inp7 = new_row.cells[5].getElementsByTagName('input')[0];
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
           
            var chkbox = row.cells[6].childNodes[0];
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
	var table = document.getElementById("myTable");
	
	  var len=table.rows.length;

	  for(var i=1;i<len;i++){  
		  var new_row = table.rows[i].cloneNode(true);
		  
	     
		
	      var inp4 = new_row.cells[0].getElementsByTagName('select')[0];
	      var inp4Id=inp4.id;
	      var sel = document.getElementById(inp4Id);
	      var selecVal = sel.options[sel.selectedIndex].value;
	          if(selecVal == "select"){
	 		 alert("Please Select Salary Type");
	  	  return false;	  
	 		    }
	      if(selecVal!="select"){
	      
	  
	 		    
	 		    
	 		    
	 		    var inp6 = new_row.cells[1].getElementsByTagName('input')[0];
	      if(inp6.value == ""){
	 		 alert("Please Enter Description");
	 		 
	  	  return false;	  
	 		    }
	    
	      var inp5 = new_row.cells[2].getElementsByTagName('input')[0];
	      if(inp5.value == ""){
	 		 alert("Please Select Start Date");
	  	  return false;	  
	 		    }
	      
	      var inp6 = new_row.cells[3].getElementsByTagName('input')[0];
	      if(inp6.value == ""){
	 		 alert("Please Select End Date");
	  	  return false;	  
	 		    }
	      if(inp5.value!="" && inp6.value!=""){
		var str1 = inp5.value;
		var str2 = inp6.value;
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
	      var inp6 = new_row.cells[4].getElementsByTagName('input')[0];
	      if(inp6.value == ""){
	 		 alert("Please Enter Total Amount");
	  	  return false;	  
	 		    }
	 		    
	 		    
	      
	      var inp7 = new_row.cells[5].getElementsByTagName('input')[0];
	      if(inp7.value == ""){
	 		 alert("Please Enter Remarks");
	  	  	return false;	  
	 		    } 
		 }
		 }
	
	  var fiscalYear=document.forms[0].fiscalYear.value;
	  if(fiscalYear=="")
		{
			alert("Please Select Fiscal Year");
			document.forms[0].fiscalYear.focus();
			return false;
		}	
	  
	 /*  var totalIncome=document.forms[0].totalIncome.value;
	  var totalDeduction=document.forms[0].totalIncome.value;
	  
	  if(totalIncome=="0.0" ||totalDeduction=="0.0" ){
		  alert("Please Select Total Button");
			
			return false;
	  } */
	  
	  
	  document.forms[0].action="extIncome.do?method=save";
		document.forms[0].submit();
}
  /*
  function saveDetails(){
		var fiscalYear=document.forms[0].fiscalYear.value;
		
		
	
		if(fiscalYear=="")
		{
			alert("Please Select Fiscal Year");
			document.forms[0].fiscalYear.focus();
			return false;
		}	
	  document.forms[0].action="extIncome.do?method=save";
		document.forms[0].submit();
  }
  */
function hideMessage(){
	
	document.getElementById("messageID").style.visibility="hidden";	
}
function submitForApproval(){
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
function CalIncomeDeduction(){
	
	
   var x=document.getElementById('myTable');
    var len = x.rows.length;
 
 

var totalI=0;
var totalD=0;
var y=1;

for(var i = 2; i <= len; i++){


  var type=document.getElementById("saleryType:"+y).value;
    
if(type!="select"){
var no=document.getElementById('totalAmount'+y).value;


no=parseFloat(no);

if(type=="I")
{
totalI=totalI+no;
}
else
{
totalD=totalD+no;
}
y=y+1;
}
}
if(isNaN(totalI))
{
totalI=0;
}
if(isNaN(totalD))
{
totalD=0;
}

document.forms[0].totalIncome.value=totalI;
document.forms[0].totalDeduction.value=totalD;

}

function setSaleryDesc(invCode,invID){
	var no=invID.substr(invID.length - 1);
       var saleryDescription="saleryDescription"+no;
	document.getElementById(saleryDescription).value=invCode;
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
				
				<logic:present name="extIncomeForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="extIncomeForm" property="message" />');
					</script>
					
				</logic:present>
			</div>
<html:form action="/extIncome.do" enctype="multipart/form-data" onsubmit="">
<table class="bordered">

 <tr><th colspan="4"><big>Requester Details</big></th></tr>
						<tr><td>Employee Number</td><td><bean:write name="extIncomeForm" property="employeeNo" /></td><td>Employee Name</td><td><bean:write name="extIncomeForm" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="extIncomeForm" property="department" /></td><td>Designation</td><td><bean:write name="extIncomeForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><bean:write name="extIncomeForm" property="doj" /></td><td>Location</td><td colspan=""><bean:write name="extIncomeForm" property="location" /></td></tr>

<tr><td>Staff Category</td><td><bean:write name="extIncomeForm" property="staffCategory" /></td><th>Fiscal Year <font color="red" size="2" >*</font></th>
<td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="extIncomeForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalYearDesc"  style="border:0;width:160px;" readonly="true" />
</td></tr>

</table>
<br/>


<table class="bordered" style="left: 2%;width: 80%;" >
<tr><th colspan="7"><center><big>External Income/Deduction</big></center></th></tr>
<tr><td>
 <table class="bordered" style="width: 80%;;left: 2%;" id="myTable">
<tr>
<th><center> Type</center></th><th> <center>Description</center></th><th><center>Start Date</center></th><th><center>End Date</center></th><th><center>Total Amount</center></th>
<th>Remarks</th>
<td width="20px;"><a href="#" ><img src="images/add-items.gif" id="addmorePOIbutton" onclick="addRow()" title="Add Row" /></a></td>
<td><a href="#" ><img src="images/delete.png"   onclick="deleteRow()" title="Remove Row"/></a></td>
</tr>
<tr>
	
	
	
	
	<td>
	<select name="saleryType" id="saleryType:1" onchange="CalIncomeDeduction()">
	<option value="select">Select</option>
	<option value="I">Income</option>
	<option value="D">Deduction</option>
	</select></td>
	<td><input type="text" name="desc" id="desc1"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
	<td><input type="text" name="startDate" id="startDate1" onfocus="popupCalender(this.id)" style="width: 70px;" readonly="true"/></td>
	<td> <input type="text" name="endDate" id="endDate1" onfocus="popupCalender(this.id)" style="width: 70px;" readonly="true"/></td>
	<td><input type="text" name="totalAmount" id="totalAmount1"  onkeyup="CalIncomeDeduction()" onkeypress="return isNumber(event)"  style="text-align:right;"/></td>
	<td><input type="text" name="remarks" id="remarks1"  onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
	<td><INPUT type="checkbox" name="chk"/></td>
</tr>

</table> 
</td></tr>
<tr><th colspan="7">
 <b>Total Income&nbsp;&nbsp;</b><html:text property="totalIncome" readonly="true"  style="background-color:  #c2c0bf ;color: black;font-weight: bold;text-align:right;" /> &nbsp;&nbsp;<b>Total Deduction&nbsp;&nbsp;</b><html:text property="totalDeduction" readonly="true" style="background-color:  #c2c0bf ;color: black;font-weight: bold;text-align:right;"/>&nbsp;&nbsp;
<%--  <html:button property="method" value="Total" styleClass="rounded" onclick="CalIncomeDeduction()"/> --%>
&nbsp;&nbsp; </th>
 </tr>

 </table>

        <br/>
<table id="subbutton" ><tr>
		<td colspan="8"><center><html:button property="method" value="Submit For Approval" onclick="saveDetails()" styleClass="rounded"/>&nbsp;
		<%-- <html:button property="method" value="Cancel Request" styleClass="rounded" onclick="cancelRequest()"/></center> --%>
		</td>
	</tr></table>
<html:hidden property="requestNo" />
<%
for(int i=0;i<1;i++){
%>
<script type="text/javascript">
addRow();

</script>
<% 
}
%>
<br/> 

<div>&nbsp;</div>

<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6"><center>Approvers Details</center></th></tr>
	<tr><th><center>Priority</center></th><th><center>Approver Name</center></th><th><center>Department</center></th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.approverName }</td><td>${abc.apprDept }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>
	
	
	<br/>
<iframe src="extIncome.do?method=showUploadFields" name="contentPage1" width="100%" 
        			frameborder="0" scrolling="no" id="the_iframe" style="overflow-y:auto;height:1000px;"  frameborder="0">
        </iframe>
</html:form>
</body>
</html>