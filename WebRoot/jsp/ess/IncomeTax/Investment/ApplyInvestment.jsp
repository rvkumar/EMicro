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
	document.forms[0].action="incomeTax.do?method=deleteDocuments&cValues="+checkvalues;
document.forms[0].submit();
}
}
}

function submitForApproval(param)
{



	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	


	var table = document.getElementById("investmentTable");
	 var len = table.rows.length;
	 var y=1;
	 
	 
   
  for(var i = 2; i < len; i++)
  {
	  var new_row = table.rows[1].cloneNode(true);
	  var inp4 = new_row.cells[1].getElementsByTagName('select')[0];
	    var inp4Id=inp4.id;
	    var sel = document.getElementById(inp4Id);
	    var selecVal = sel.options[sel.selectedIndex].value;
	    if(selecVal == "select"){
			 alert("Please Select Investment Code");
			  document.getElementById(inp4Id).focus();
		  return false;	  
			    }
	  var new_row = table.rows[y].cloneNode(true);
    var inp4 = new_row.cells[1].getElementsByTagName('select')[0];
    var inp4Id=inp4.id;
    var sel = document.getElementById(inp4Id);
    var selecVal = sel.options[sel.selectedIndex].value;
    if(selecVal != "select"){
    if(selecVal == "select"){
		 alert("Please Select Investment Code");
		   document.getElementById(inp4Id).focus();
	  return false;	  
		    }
   	if(selecVal!="select"){
   	
    var inp4 = new_row.cells[3].getElementsByTagName('input')[0];
    if(inp4.value == ""){
  
		 alert("Please Enter Policy / Receipt No");
		   
	  return false;	  
		    }
    var inp4 = new_row.cells[4].getElementsByTagName('input')[0];
    if(inp4.value == ""){
	 alert("Please Enter Amount");
	   
  return false;	  
	    }
  
    var inp4 = new_row.cells[5].getElementsByTagName('input')[0];
    if(inp4.value == ""){
	 alert("Please Select Date");
	 
  return false;	  
	    }
    
  
   	}
	
  
  
  }
  
  y=y+1;
  }

  


/* if(document.getElementById("totalAmount").value==""){
	alert("Please Click ont Total Amount Button");
	document.getElementById("totalAmount").focus();
	return false;
} */
  
document.forms[0].action="incomeTax.do?method=submitForApproval&param="+param;
	document.forms[0].submit();
}

function uploadDocument(){
document.forms[0].action="incomeTax.do?method=uploadDocuments";
	document.forms[0].submit();
}
</script>

<script>
function deleteRow()
{
	try {
        var table = document.getElementById("investmentTable");
        var rowCount = table.rows.length;

        for(var i=0; i<rowCount; i++) {
            var row = table.rows[i];
           
            var chkbox = row.cells[7].childNodes[0];
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
    
    




function insRow()
{
   
    var x=document.getElementById('investmentTable');
    var new_row = x.rows[1].cloneNode(true);
    var len = '';       
    var elements = document.getElementsByName("investmentcode");
    var reqlen = elements.length;      	
    len =parseInt(elements[reqlen-1].id.substr(elements[reqlen-1].id.indexOf(":") + 1))+1;
    
     new_row.cells[0].innerHTML = len;
    
      var inp1 = new_row.cells[1].getElementsByTagName('select')[0];
    inp1.id = 'investmentcode:'+len;
     inp1.name = 'investmentcode';
      inp1.value = '';
  /*   var inp2 = new_row.cells[2].getElementsByTagName('select')[0];
    inp2.id = 'InvestmentDesc'+len;
    inp2.name = 'InvestmentDesc';
    inp2.value = ''; */
     var inp9 = new_row.cells[2].getElementsByTagName('input')[0];
    inp9.id = 'invtype'+len;
    inp9.name = 'invtype';
    inp9.value = '';
    
    var inp3 = new_row.cells[3].getElementsByTagName('input')[0];
    inp3.id = 'receiptNo'+len;
    inp3.name = 'receiptNo';
    inp3.value = '';
      var inp4 = new_row.cells[4].getElementsByTagName('input')[0];
    inp4.id = 'amount'+len;
    inp4.name = 'amount';
    inp4.value = '';
      var inp5 = new_row.cells[5].getElementsByTagName('input')[0];
    inp5.id = 'date'+len;
    inp5.name = 'date';
      inp5.id = 'date'+len;
    
     inp5.value = '';
     
     var inp7 = new_row.cells[6].getElementsByTagName('textarea')[0];
    inp7.id = 'remarks'+len;
    inp7.name = 'remarks';
    inp7.value = '';
    
    x.appendChild( new_row );
    
    document.getElementById('levelNo').value=len;
}
function totalAmount1(){


   var x=document.getElementById('investmentTable');
    var len = x.rows.length;
 
  

var totalI=0;
var y=1;
var u=0;

for(var i = 2; i <= len; i++){

var InvestmentDesc=document.getElementsByName("investmentcode")[u].value;

   
if(InvestmentDesc!="select"){
var no=document.getElementsByName("amount")[u].value;


no=parseFloat(no);


totalI=totalI+no;


y=y+1;
u=u+1;
}
}
if(isNaN(totalI))
{
totalI=0;
}

document.getElementById("totalAmount").value=parseInt(totalI);
}
function goBack(){
document.forms[0].action="incomeTax.do?method=investmentList";
	document.forms[0].submit();
}

function setInvDesc(invCode,invID){
	var no=invID.substr(invID.length - 1);
       var InvestmentDesc="invtype"+no;
       var newdata=invCode.split("?");
    
       invCode=newdata[1];
   
	document.getElementById(InvestmentDesc).value=invCode;
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
function hideMessage(){
	document.getElementById("messageID").style.visibility="hidden";	
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function sectiontot(){



 var x=document.getElementById('investmentTable');
    var len = x.rows.length;
 
  


var u=0;
var fin=0;

var io=document.getElementsByName("secfilter").length;

for(var i = 0; i < io; i++)
{
var arr=document.getElementsByName("secfilter")[i].value;
fin=0;
u=0;



for(var k = 2; k <= len; k++){
	


var loop=document.getElementsByName("invtype")[u].value;
var amo=document.getElementsByName("amount")[u].value;

if(loop=="")
	{
	break;
	}

if(amo=="")
{
break;
}

if(arr==loop)
	{	
	if(amo!="")
	{

	fin=parseFloat(fin)+parseFloat(amo);

	}
	document.getElementById(arr+":table").style.display="";
	
	}


       u=u+1;
    
}

  document.getElementById(arr+":claimreq").value=fin;
  

  if((parseInt(document.getElementById(arr+":claimreq").value)+parseInt(document.getElementById(arr+":claimreqyear").value))<parseInt(document.getElementById(arr+":limit").value))
	  {
	  document.getElementById(arr+":allow").value=document.getElementById(arr+":claimreq").value;
	  }
  else
	  {
	  document.getElementById(arr+":allow").value=document.getElementById(arr+":limit").value;
	  }
  
  
  
  
}

}


function statusMessage(message){
alert(message);
}


function emptytable(){

var io=document.getElementsByName("secfilter").length;

for(var i = 0; i < io; i++)
{
var arr=document.getElementsByName("secfilter")[i].value;
document.getElementById(arr+":table").style.display="none";
}
}


function resizeIFrameToFitContent( iFrame ) {

    iFrame.width  = iFrame.contentWindow.document.body.scrollWidth;
    iFrame.height = iFrame.contentWindow.document.body.scrollHeight;
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
<html:form action="incomeTax" enctype="multipart/form-data" method="post">
<html:hidden property="requestNumber"/>
<div align="center" id="messageID" style="visibility: true;">
			
				<logic:present name="incomeTaxForm" property="message">
						<script language="javascript">
					statusMessage('<bean:write name="incomeTaxForm" property="message" />');
					</script>
					
				</logic:present>
			</div>


   	<table class="bordered">
  

	  <tr><th colspan="8"><big>Requester Details</big></th></tr>
						<tr><td>Employee Number</td><td><bean:write name="incomeTaxForm" property="employeeNo" /></td><td>Employee Name</td><td><bean:write name="incomeTaxForm" property="employeeName" /></td>
						<td>Department</td><td><bean:write name="incomeTaxForm" property="department" /></td><td>Designation</td><td><bean:write name="incomeTaxForm" property="designation" /></td></tr>
					<tr><td>Date of Joining</td><td colspan=""><bean:write name="incomeTaxForm" property="doj" /></td><td>Location</td><td colspan=""><bean:write name="incomeTaxForm" property="location" /></td>
<td>Staff Category</td><td><bean:write name="incomeTaxForm" property="staffCategory" /></td><th>Fiscal Year <font color="red" size="2" >*</font></th>
<td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="incomeTaxForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalYearDesc"  style="border:0;width:160px;" readonly="true" />
</td></tr>
		</table>
		<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="3"><center>Approvers Details</center></th></tr>
	<tr><th><center>Priority</center></th><th><center>Approver Name</center></th><th><center>Department</center></th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.priority }</td><td>${abc.approverName }</td><td>${abc.apprDept }</td>
	</tr>
	</logic:iterate>
</table>
	</logic:notEmpty>

<br/>
<!-- <div>&nbsp;</div> -->
<input type="hidden" name="levelNo" id="levelNo" style="width: 10%;"/>
<table  class="bordered"  width="20%" align="left" id="investmentTable" >
<tr>
            <th width="5px"><center>No</center></th>
            <th width="50px"><center>Investment Code:Description</center></th>
             <th width="50px"><center>Investment Section</center></th>
           <!--  <th width="95px">Investment Description</th> -->
            <th width="85px"><center>Policy / Receipt No</center></th>
            <th width="100px"><center>Amount</center></th>
            <th width="125px"><center>Date</center></th>
           
            <th width="145px"><center>Remarks</center></th>
            <td width="20px;"><a href="#" ><img
						src="images/add-items.gif" id="addmorePOIbutton"
						onclick="insRow()" title="Add Row" /></a></td>
<td><a href="#" ><img src="images/delete.png"   onclick="deleteRow()" title="Remove Row"/></a></td>
        </tr>
       <tr>
				<td>1</td>
				<td><select id="investmentcode:1" name="investmentcode" onchange="setInvDesc(this.value,this.id);totalAmount1();emptytable();sectiontot();">
   
  <option value="select">--Select--</option>
    <logic:iterate id="test" name="invCodelist">
  <option value="${test.invCode }?${test.invSection }">${test.invCode }</option>

  </logic:iterate>
</select></td>
      <%--  <td>
       <select id="InvestmentDesc1" name="InvestmentDesc" disabled="disabled">
        <option value="select">select</option>
        <logic:iterate id="test" name="invCodelist">
 				 <option value="${test.invCode }">${test.invDes }</option>
 				  </logic:iterate>
		</select>
       </td> --%>
        <td width="10px"><input type="text" id="invtype1" name="invtype" style="width: 100px;" onkeyup="this.value = this.value.replace(/'/g,'`')" disabled="true"/></td>
       <td width="10px"><input type="text" id="receiptNo1" name="receiptNo" style="width: 100px;" onkeyup="this.value = this.value.replace(/'/g,'`')"/></td>
       <td width="20px"><input type="text" id="amount1" name="amount" style="width: 100px;text-align:right;" onkeypress="return isNumber(event)" onkeyup="totalAmount1();sectiontot()" /></td>
       <td><input type="text" id="date1" onfocus="popupCalender(this.id)" name="date" style="width: 80px;" onkeyup="this.value = this.value.replace(/'/g,'`')" readonly="true"/></td>
      
      <td><textarea  id="remarks1" name="remarks" onkeyup="this.value = this.value.replace(/'/g,'`')" style="width: 132px; "></textarea></td> 
     <td><INPUT type="checkbox" name="chk"/></td>
        <td width="50px"> </td>
       </tr>
       </table>
       <table class="bordered">
      <tr>
        <th colspan="4" >
           <b>Total  Amount</b>
    <input  type="text" name="totalAmount" id="totalAmount"  readonly="readonly" style="background-color:  #c2c0bf ;color: black;font-weight: bold;text-align:right;" />
      
     <!-- <input  type="button" value="Total Amount" onclick="totalAmount1()" class="rounded"/> -->
        </th>
        </tr>
       </table>
       <br/>
       <div id="sectiontotal" class="no" >
        	<logic:notEmpty name="sectionlist">
        	<table class="bordered"><tr><th colspan="5"><center>Summary</center></th></tr>
        	<tr><th><center>Section</center></th><th><center>Claim in this request</center></th><th><center>Total Claim in this year</center></th><th><center>Limit</center></th><th><center>Allowed</center></th></tr>
        	<logic:iterate id="l" name="sectionlist">
        	
        	<tr style="display: none;" id="${l.invSection}:table">
        	<td><input type="text" id="${l.invSection}" name="secfilter" value="${l.invSection}" style="border: none;border-style: none;outline: none"/></td>
        	<td><input type="text" id="${l.invSection}:claimreq" name="secreq" value="0" style="border: none;border-style: none;outline: none;text-align:right;"/></td>
        	<td><input type="text" id="${l.invSection}:claimreqyear" name="secreqyear" value="${l.invRemarks}" style="border: none;border-style: none;outline: none;text-align:right;"/></td>
        	<td><input type="text" id="${l.invSection}:limit" name="seclimit" value="${l.invLimit}" style="border: none;border-style: none;outline: none;text-align:right;"/></td>
        	<td><input type="text" id="${l.invSection}:allow" name="secallow" value="" style="border: none;border-style: none;outline: none;text-align:right;"/></td>
        	</tr>
        	
        	</logic:iterate>
        	</table>
        	
        	</logic:notEmpty>
        	
        	</div>
        	
    
        	
        	<html:hidden property="fileFullPath" name="incomeTaxForm" />
        	
        	<div>&nbsp;</div>
        	
        <table>
         <iframe src="incomeTax.do?method=showUploadFields" name="contentPage1" width="105%" 
        			frameborder="1" scrolling="yes" id="the_iframe">
        </iframe>
        <br>
</br>    <tr>
<td colspan="10" align="center">
<html:button property="method" value="Submit  For Approval" styleClass="rounded" onclick="submitForApproval('submit')"></html:button>&nbsp;
<%-- <html:button property="method" value="Back" styleClass="rounded" onclick="goBack()"></html:button>&nbsp; --%>
<%-- <html:button property="method" value="Save As Draft" styleClass="rounded" onclick="submitForApproval('draft')"></html:button> --%>


</td></tr>
</table>
<div>&nbsp;</div>


	
	    <br/>
       
<%
for(int i=0;i<1;i++){
%>
<script type="text/javascript">
	insRow();

</script>
<% 
}
%>
</html:form>
</body>
</html>