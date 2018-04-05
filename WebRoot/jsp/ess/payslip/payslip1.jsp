<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>

<html>
    <head>
        <title>eMicro :: Pay Slip</title>
       <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />

  <style type="text/css">
  
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>

<!-- Theme css -->
		
		
<script type="text/javascript">
    function onSubmit(){
				
	    
	    if(document.forms[0].dat.value=="")
	    {
	      alert("Date field Should not be Empty");
	      document.forms[0].dat.focus();
	      return false;
	    }
			
		//	var url1="payslip.do?method=submit";
			var url1="payslip.do?method=getPaySlipDetails";
			document.forms[0].action=url1;
 			document.forms[0].submit();

	}
	
	function viewvis()
{
//alert('Param is ************'+param);
//window.open("visitors.do?method=viewvisitor&id="+param,"active", 
// 		'width=500, height=400, toolbar=no, location=no, resizable=no,scrollbars=no, directories=no,status=no, titlebar=no');

window.showModalDialog("payslip.do?method=print", "dialogWidth:100%; dialogHeight:100%; center: {on}" );
//window.open("payslip.do?method=print", "dialogWidth:400px; dialogHeight:400px; center:yes" );
/*
var url1="payslip.do?method=generateIntoPDF";
			
			document.forms[0].action=url1;
 			document.forms[0].submit();
 			*/
}
	
	
	function display()
    {
    
    
    
    if(document.forms[0].mesage.value!="")
    {
    alert(document.forms[0].mesage.value);
    document.forms[0].empcode.value="";
    //document.forms[0].dat.value="May13";
    document.forms[0].empcode.focus();
    }
    //document.forms[0].dat.value="May13";
    document.forms[0].mesage.value=="";
    
    
    
    return false;
    
    }
	
</script>
	
<%--<script type="text/javascript">
var months = new Array("Jan", "Feb", "Mar",
"Apr", "May", "Jun", "Jul", "Aug", "Sep",
"Oct", "Nov", "Dec");

function writeMonthOptions() {

   var today = new Date();
   var date = new Date(today);	
   date.setFullYear(date.getFullYear() - 3)
   var dropDown = document.getElementById("dat");
   var i = 0;
   var optionValues;

   while (today.getTime() >= date.getTime()) {

	var myyear = today.getFullYear().toString(10).substring(2, 4);
      optionValues = months[today.getMonth()] + myyear;
      dropDown.options[i++] = new Option(optionValues, optionValues)
      today.setMonth(today.getMonth() - 1);
   }
}

//window.onload = writeMonthOptions;

</script>
    --%>
  <script type="text/javascript">
$(function() {
	$('#month').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#toDate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>  
    
</head>

<body onload="display()">
			<div align="center">
				<logic:present name="payslipForm" property="message">
					<font color="red" size="3"><b><bean:write name="payslipForm" property="message" /></b></font>
				</logic:present>
				<logic:present name="payslipForm" property="message1">
					<font color="Green" size="3"><b><bean:write name="payslipForm" property="message2" /></b></font>
				</logic:present>
			</div>
	<html:form action="/payslip.do" enctype="multipart/form-data">
	  	<% 
			String status=(String)session.getAttribute("status");		
	            if(status==""||status==null)
	                {
	                }
	            else{
	    %>
	    <b><center><font color="red" size="4" ><%=status %></font></center></b>
	    <%
	    	session.setAttribute("status"," ");
	       }
         %>
		<html:hidden property="mesage"/>
		<html:hidden property="empcode"/>

    <div style="text-align: center;">
    	<b>PAY SLIP (Date) : <img src="images/date_icon.gif" align="absmiddle" />	</b>
    		
			<html:select property="dat" >
				<html:option value="Select">--Select--</html:option>
				<html:options property="ar_id" labelProperty="ar_name" name="payslipForm"/>
			</html:select>
			&nbsp;&nbsp;
			<input type="text" style="display: none;"/>
			<html:button property="method" value="Submit" onclick="onSubmit()" styleClass="rounded" style="width: 100px"></html:button>
    </div>
    <br/>
    <hr/>
	<logic:notEmpty name="detailed_payslip">
	<div style="width: 100%; text-align: right;">
	<a href="javascript:window.print();"><img src="images/print.gif" align="absmiddle" title="Print" ></a>
	</div>
	<table  style="width: 100%;">
	<tr>
	<td ><img src="images/MLLogo.png" height="80" width="110"></td>
	<td><b>Micro Labs Limited</b><br/>#27,Race Course Road, Bangalore-560001 </td>
	<td ><b>Salary Slip - <bean:write property="paymnt" name="payslipForm"/></b></td>
	</tr>
	
	</table>
	    <div style="width: 100%; text-align: right;">
	    	
		    

			<!--<a href="javascript:viewvis()"><img src="images/adobe_reader_logo.png" align="absmiddle" top" height="30" width="30" title="Download to PDF"></a>
-->
			<div>&nbsp;</div>
			
		    <table class="bordered"  style="width:100%; ">
				<tr>
					<th >Employee Information</th>
				</tr>
				<tr>
					<td><b>Name</b></td><td><bean:write property="empname" name="payslipForm"/></td>
					<td><b>Date Of Joining</b></td><td><bean:write property="doj" name="payslipForm"/></td>
				</tr>
				<tr>	
					<td><b>Emp.No.</b></td>	<td><bean:write property="empcod" name="payslipForm"/></td>
					<td><b>PAN No.</b></td><td><bean:write property="panno" name="payslipForm"/>&nbsp;</td>
				</tr>	
				<tr>
					<td><b>Department</b></td><td><bean:write property="dept" name="payslipForm"/></td>
					<td><b>Bank Name</b></td><td ><bean:write property="bnkname" name="payslipForm"/></td>
				</tr>
				<tr>	
					<td><b>Title</b></td>
					<td><bean:write property="desg" name="payslipForm"/></td>
				</tr>

				<tr>
					<td><b>Address</b></td>
					<td colspan="3">Race Course Road, Bng<!--<bean:write property="empaddr" name="payslipForm"/>--></td>
					<td colspan="2"><b>Reporting Manager</b></td>
					<td colspan="2"><bean:write property="repmng" name="payslipForm"/>&nbsp;</td>
				</tr>

				<tr>
					<td><b>Date Of Join</b></td>
					<td><bean:write property="doj" name="payslipForm"/></td>
					<td><b>PF No.</b></td>
					<td><bean:write property="pfno" name="payslipForm"/></td>
					<td><b>ESI No.</b>&nbsp;</td>
					<td><bean:write property="esino" name="payslipForm"/>&nbsp;</td>
					<td><b>PAN No.</b></td>
					<td><bean:write property="panno" name="payslipForm"/>&nbsp;</td>
				</tr>

				<tr>					
					<td colspan="1"><b>Bank Name</b></td>
					<td colspan="4"><bean:write property="bnkname" name="payslipForm"/></td>
					<td><b>Account No.</b></td>
					<td colspan="2"><bean:write property="baccno" name="payslipForm"/></td>
				</tr>

				<tr>
					<td><b>Working Days</b></td>
					<td style="text-align: right;"><bean:write property="worgdays" name="payslipForm"/></td>
					<td><b>Payable Days</b></td>
					<td style="text-align: right;"><bean:write property="daywrkd" name="payslipForm"/></td>
					<td><b>Absent</b></td>
					<td style="text-align: right;"><bean:write property="lop" name="payslipForm"/></td>
					<td><b>Leaves Taken</b></td>
					<td style="text-align: right;"><bean:write property="daypayd" name="payslipForm"/></td>
				</tr>

				<tr>
					<th colspan="8">Leave Balance</th>
				</tr>
				<tr>
					<td><b>Casual</b></td>
					<td style="text-align: right;"><bean:write property="cl" name="payslipForm"/>&nbsp;</td>
					<td><b>Sick</b></td>
					<td style="text-align: right;"><bean:write property="sl" name="payslipForm"/>&nbsp;</td>
					<td><b>Earned</b></td>
					<td style="text-align: right;"><bean:write property="el" name="payslipForm"/>&nbsp;</td>
					<td><b>Maternity</b></td>
					<td style="text-align: right;">&nbsp;</td>
				</tr>
				<tr>
				<th colspan="4" align="center"><center>Earnings</center>
				</th>
				<th colspan="4" align="center"><center>Deductions</center>
				</th>
				
				</tr>
				<%
				int earTot=0;
				int deduTot=0;
				%>
				<logic:notEmpty name="deduction">
					<logic:iterate id="records2" name="array2">
					<% deduTot++;%>
					</logic:iterate>
				</logic:notEmpty>
					<tr>
					<td colspan="4">
							<logic:notEmpty name="earnings">
								<table class="bordered" style="position: relative;height: 100%;">
								<tr>
									<th colspan="2">Description</th>
									<th>Arrears</th>
									<th>Current</th>
									</tr>
								<logic:iterate id="recordse" name="array1">
						          <tr>
										<td colspan="2"><bean:write property="eh" name="recordse"/></td>
										<td style="text-align: right;"><bean:write property="ea" name="recordse"/></td>
										<td style="text-align: right;"><bean:write property="ec" name="recordse"/></td>
										</tr>
								<%
								
								earTot++;
								%>		
								</logic:iterate>
								<%
								if(earTot<deduTot){
									int addColumn=deduTot-earTot;
								for(int i=0;i<addColumn;i++)	{
								%>
									<tr>
										<td colspan="2">&nbsp;</td>
										<td style="text-align: right;">&nbsp;</td>
										<td style="text-align: right;">&nbsp;</td>
									</tr>
									<%
									
								}
								}
									%>
							</table>
							</logic:notEmpty>
				        </td>
				        <td colspan="4">
							<logic:notEmpty name="deduction">
							<%deduTot=0; %>
						<table class="bordered" style="position: relative;height: 100%;">
							<tr>
									<th colspan="2">Description</th>
									<th>Arrears</th>
									<th>Current</th>
									</tr>
								<logic:iterate id="records2" name="array2">
									<tr>
										<td colspan="2"><bean:write property="dh" name="records2"/>&nbsp;</td>
										<td style="text-align: right;"><bean:write property="da" name="records2"/>&nbsp;</td>
										<td style="text-align: right;"><bean:write property="dc" name="records2"/>&nbsp;</td>
									</tr>
									<%			
									deduTot++;
								%>
								</logic:iterate>
								
								<%
								if(earTot>deduTot){
									int addColumn=earTot-deduTot;
								for(int i=0;i<addColumn;i++)	{
									
									System.out.println("test");
								%>
									<tr>
										<td colspan="2">&nbsp;</td>
										<td style="text-align: right;">&nbsp;</td>
										<td style="text-align: right;">&nbsp;</td>
									</tr>
									<%
									
								}
								}
									%>
						</table>
							</logic:notEmpty>
				</td>
				<tr>
					<td colspan="2"><b>Total Earnings</b></td>
					<td style="text-align: right;"><bean:write property="arrears_totEarn" name="payslipForm"/></td>
					<td style="text-align: right;"><bean:write property="current_totEarn" name="payslipForm"/></td>
					<td colspan="2"><b>Total Deductions</b></td>
					<td style="text-align: right;"><bean:write property="arrears_totDeduc" name="payslipForm"/></td>
					<td style="text-align: right;"><bean:write property="current_totDeduc" name="payslipForm"/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Loan Bal.</b></td>
					<td colspan="2" style="text-align: right;">00.00</td>
					<td colspan="2"><b>Net Payable</b></td> 
					<td colspan="2" style="text-align: right;"><bean:write property="netpay" name="payslipForm"/></td>					
				</tr>

				<tr>
					<td><b>General Note</td>
					<td colspan="7">xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</td>
				</tr>

				<tr>
					<th colspan="8" style="text-align: center;">Projected Annual Computation</th>
				</tr>

				<tr>
					<td colspan="2"><b>Annual Income</b></td>
					<td colspan="2" style="text-align: right;">00.00</td>
					<td ><b>Net Tax Income</b></td>
						<td style="text-align: right;">00.00</td>
					<td><b>Balance Tax</b></td>
					<td style="text-align: right;">00.00</td>
				</tr>
				
				<tr>
					<td colspan="2"><b>Total Tax Payable</b></td>
					<td colspan="2" style="text-align: right;">00.00</td>
					<td colspan="2"><b>Tax Deducted</b></td>
					<td colspan="4" style="text-align: right;">00.00</td>
				</tr>

			
			</table>
	    </div>
	</logic:notEmpty>
	</html:form>

</body>
</html>