<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c" %>
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<script type="text/javascript" src="calender/js/calendar.js"></script>
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
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
	document.forms[0].action="incomeTax.do?method=deleteDocInDraft&cValues="+checkvalues;
document.forms[0].submit();
}
}
}

function submitForApproval(){
document.forms[0].action="incomeTax.do?method=submitForApproval";
	document.forms[0].submit();
    
}

function uploadDocument(){
document.forms[0].action="incomeTax.do?method=modifyuploadDocInDrafts";
	document.forms[0].submit();

}
function deleteRow(row)
{
    var i=row.parentNode.parentNode.rowIndex;
   // alert("Delte Record="+i);
    var x=document.getElementById('investmentTable');
   var len = x.rows.length;
    if(len==2)
    {
    alert("Cannot delete ..Atleast one record is required");
    }
   if(len!=2){
    document.getElementById('investmentTable').deleteRow(i);
    }
    var totalRows=len-1;
  //  alert("totalRows="+totalRows);
    if(totalRows!=i)
    {
    
     
    }
     document.getElementById('levelNo').value=totalRows;
    }
    
function insRow()
{
    console.log( 'hi');
    var x=document.getElementById('investmentTable');
    var new_row = x.rows[1].cloneNode(true);
    var len = x.rows.length;
    new_row.cells[0].innerHTML = len;
    
      var inp1 = new_row.cells[1].getElementsByTagName('select')[0];
    inp1.id = 'investmentcode'+len;
     inp1.name = 'investmentcode';
      inp1.value = 'select';
    var inp2 = new_row.cells[2].getElementsByTagName('select')[0];
    inp2.id = 'InvestmentDesc'+len;
    inp2.name = 'InvestmentDesc';
    inp2.value = 'select';
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
      var inp6 = new_row.cells[6].getElementsByTagName('select')[0];
    inp6.id  = 'status'+len;
    inp6.name = 'status';
    inp6.value = 'select';
     var inp7 = new_row.cells[7].getElementsByTagName('input')[0];
    inp7.id = 'remarks'+len;
    inp7.name = 'remarks';
    inp7.value = '';
    
    x.appendChild( new_row );
    
    document.getElementById('levelNo').value=len;
}






function totalAmount1(){
	 var x=document.getElementById('investmentTable');
	    var new_row = x.rows[1].cloneNode(true);
	    var len = x.rows.length;
	var total=0;
	var table = document.getElementById("investmentTable");

	for(var i = 1; i <= len; i++){
		
		var new_row = table.rows[i].cloneNode(true);
		  var inp4 = new_row.cells[4].getElementsByTagName('input')[0];
		  inp4=inp4.value;
	
	var	no=parseFloat(inp4);
	total=total+no;

		document.forms[0].totalAmount.value=total;
	}

}

function setInvDesc(invCode,invID){
	var no=invID.substr(invID.length - 1);
       var InvestmentDesc="InvestmentDesc"+no;
	document.getElementById(InvestmentDesc).value=invCode;
}

function submitDraftToApproval(){
	

	if(document.forms[0].fiscalYear.value==""){
		alert("Please Select Fiscal Year");
		document.forms[0].fiscalYear.focus();
		return false;
	}
	  var x=document.getElementById('investmentTable');
	    var new_row = x.rows[1].cloneNode(true);
	    var len = x.rows.length;
	var levels =  len;
if(levels=="")
levels=1;
 var pattern = /^\d+(\d+)?$/
     if(!pattern.test(levels))
     {
      alert("No Of Rows Should be Integer. ");
	  document.getElementById('levelNo').focus();
	     return false; 
     }
	if(levels==0)
	{
	 alert("Please enter No. of rows ");
	  document.getElementById('levelNo').focus();
	     return false; 
	
	}
	var table = document.getElementById("investmentTable");
	
    if(levels!=0)
  {
  for(var i = 1; i < levels; i++)
  {
	  var new_row = table.rows[1].cloneNode(true);
	  var inp4 = new_row.cells[1].getElementsByTagName('select')[0];
	    var inp4Id=inp4.id;
	    var sel = document.getElementById(inp4Id);
	    var selecVal = sel.options[sel.selectedIndex].value;
	    if(selecVal == "select"){
			 alert("Please Select Investment Code");
		  return false;	  
			    }
	  var new_row = table.rows[i].cloneNode(true);
    var inp4 = new_row.cells[1].getElementsByTagName('select')[0];
    var inp4Id=inp4.id;
    var sel = document.getElementById(inp4Id);
    var selecVal = sel.options[sel.selectedIndex].value;
    if(selecVal != "select"){
    if(selecVal == "select"){
		 alert("Please Select Investment Code");
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
    var inp4 = new_row.cells[6].getElementsByTagName('select')[0];
    var inp4Id=inp4.id;
    var sel = document.getElementById(inp4Id);
    var selecVal = sel.options[sel.selectedIndex].value;
    if(selecVal == "select"){
		 alert("Please Select Status");
	  return false;	  
		    }
   	}
	
  
  
  }
  }

  }


if(document.forms[0].totalAmount.value==""){
	alert("Please Select Total Amount Button");
	document.forms[0].totalAmount.focus();
	return false;
}
document.forms[0].action="incomeTax.do?method=submitDraftToApproval";
	document.forms[0].submit();
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
</script>
</head>
<body >
<html:form action="incomeTax" enctype="multipart/form-data" method="post">

<div align="center">
				<logic:present name="incomeTaxForm" property="message2">
					<font color="red" size="3"><b><bean:write name="incomeTaxForm" property="message2" /></b></font>
				</logic:present>
				<logic:present name="incomeTaxForm" property="message">
					<font color="Green" size="3"><b><bean:write name="incomeTaxForm" property="message" /></b></font>
				</logic:present>
			</div>
<table class="bordered">
<tr>
<th>Employee No</th><td> <bean:write property="employeeNo" name="incomeTaxForm"/>
<html:hidden property="requestNumber"/>
 </td>
<th>Fiscal Year</th><td><html:select property="fiscalYear" onchange="displayFiscalYear()">
<html:option value="">---Select---</html:option>
<html:options name="incomeTaxForm"  property="yearList"></html:options>
</html:select>
<html:text property="fiscalYearDesc"  style="border:0;width:150px;" readonly="true" />
</td>
</tr>
<tr>
<th>Employee Name</th><td colspan="4"><bean:write property="employeeName" name="incomeTaxForm"/></td>
</tr>
</table>
<br/>
<!--<b>Enter No of Rows</b>&nbsp;<input type="text" name="levelNo" id="levelNo" style="width: 10%;"/>&nbsp;
<input type="button" value=" Add " onclick="insRow()" class="rounded" style="width:50px; "/>
--><table>


       
    
        <table  class="bordered"  width="20%" align="left" id="investmentTable" >
<tr>
            <th width="5px">No</th>
            <th width="50px">Investment<br/>Code</th>
            <th width="125px">Investment<br/>Description</th>
            <th width="125px">Policy / Receipt No</th>
            <th width="125px">Amount</th>
            <th width="125px">Date</th>
            <th width="125px">Status</th>
            <th width="125px">Remarks</th>
             <th width="125px">Delete</th>
            <th width="125px">Add</th>
        </tr>
        <% int i=1; %>
      <c:forEach var="abc" items="${investmetDetails}">
       <tr>
       <td><%=i %></td>
       <td><select id="investmentcode<%=i %>" name="investmentcode" onchange="setInvDesc(this.value,this.id)">
  <option value="select"  >select</option>
  <logic:iterate id="test" name="invCodelist">
   <option value="${test.invCode }" <logic:equal name="abc" property="invCode" value="${test.invCode }">selected="selected"</logic:equal>>${test.invCode }</option>
  </logic:iterate>
  
</select></td>
     
       <td width="50px">
       <select id="InvestmentDesc<%=i %>" name="InvestmentDesc" disabled="disabled">
        <option value="select">select</option>
         <logic:iterate id="test" name="invCodelist">
         <option value="${test.invCode }" <logic:equal name="abc" property="invDes" value="${test.invCode }">selected="selected"</logic:equal>>${test.invDes }</option>
           </logic:iterate>
 
       
       </td>
       <td width="10px"><input type="text" id="receiptNo<%=i %>" name="receiptNo" value="${abc.reciptNum}"/></td>
       <td width="20px"><input type="text" id="amount<%=i %>"name="amount" style="width: 100px;" value="${abc.amt}"/></td>
       <td><input type="text" id="date<%=i %>" onfocus="popupCalender(this.id)" name="date" style="width: 80px;" value="${abc.invDate}"/></td>
      
      <td>
      
      <select id="status<%=i %>" name="status">
       <option value="select">select</option>
       <option value="A" <logic:equal name="abc" property="invStatus" value="A">selected="selected"</logic:equal>>Approved</option>
        <option value="D" <logic:equal name="abc" property="invStatus" value="D">selected="selected"</logic:equal>>Declared</option>
        </select>
      
      </td>
      <td><input type="text" id="remarks<%=i %>" name="remarks" style="width: 70px;" value="${abc.invRemarks}"/></td> 
      <td > <img src="images/delete.png" id="delPOIbutton"  onclick="deleteRow(this)" title="Remove Row"/>  </td>
        <td> <img src="images/add-items.gif" id="addmorePOIbutton" onclick="insRow()" title="Add Row"/>  </td>
       </tr>
       <%
       i++;
       %>
       </c:forEach>
       </table>
      
        <tr>
        <td colspan="4" align="right">
         <div>&nbsp;</div>
        <b>Total Amount</b>
        </td>
        <td ><html:text property="totalAmount" styleId="totalAmount"></html:text> </td>
        <td colspan="5"><input  type="button" value="Total Amount" onclick="totalAmount1()" class="rounded"/>
        
        <div>&nbsp;</div>
        
       
        </td>
        </tr>
        <table class="bordered"  width="20%">
         <tr>
               <th   align="center" colspan="8">
               <div>&nbsp;</div>
               Document Path : 
                  	<html:file property="documentFile" />
                   <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadDocument();" style="align:right;"/>
                 
               </th>
		</tr>
		<logic:notEmpty name="documentDetails">
		<tr>
						<th colspan="10">Uploaded Documents </th>
						</tr>
						

						<logic:iterate id="abc" name="documentDetails">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/ESS/Income Tax/Apply Investment/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
							<td align="center" colspan="6">
								<input type="button"  class="content" value="Delete" onclick="deleteDocumentsSelected()"/>
							</td>
							
						</logic:notEmpty>
						
       </table>   
        
        
   
 
        
       
       
     
     
   
<html:hidden property="requestNumber"/>
<div>&nbsp;</div>
        <table>
      
<tr>
<td colspan="10" align="center">
<html:button property="method" value="Cancel" styleClass="rounded"></html:button>
<html:button property="method" value="Submit For Approval" styleClass="rounded" onclick="submitDraftToApproval()"></html:button>
</td>
</table>
<script language="javascript">
displayFiscalYear();
</script>
</html:form>
</body>
</html>