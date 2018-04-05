<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>eMicro :: Pay Slip</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="description" content="Expand, contract, animate forms with jQuery wihtout leaving the page" />
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
        <meta name="keywords" content="expand, form, css3, jquery, animate, width, height, adapt, unobtrusive javascript"/>
		<link rel="shortcut icon" href="../favicon.ico" type="image/x-icon"/>
		
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />

<!-- Theme css -->
		
		
<script type="text/javascript">
    function onSubmit(){
				
	    
	    if(document.forms[0].dat.value=="Select")
	    {
	      alert("Month/Year field Should not be Empty");
	      document.forms[0].dat.focus();
	      return false;
	    }
			
			var url1="payslip.do?method=submit";
			
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
    
    
</head>

<body onload="display()">

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
    	<b>PAY SLIP (Month/Year) :	</b>
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
    <br/>

	<logic:notEmpty name="detailed_payslip">
	    <div style="width: 100%; text-align: right;">
	    	
		    <a href="javascript:window.print();"><img src="images/print.gif" align="absmiddle" title="Print" ></a>

			<!--a href="javascript:viewvis()"><img src="images/pdf_logo.png" align="absmiddle" top" height="30" width="30" title="Download to PDF"></a-->

			<br/>
			
		    <table class="bordered" width="100%">
				<tr>
					<th colspan="8" style="text-align: center;"><big>PAY SLIP</big></th>
				</tr>

				<tr>
					<td rowspan="2"><img src="images/MLLogo.png" height="80" width="110">
					<td rowspan="2" colspan="5" style="text-align: center;"><big><bean:write property="comaddr" name="payslipForm"/></big></td>
					<td><small>Month</small></td>
					<td><bean:write property="paymnt" name="payslipForm"/></td>
				</tr>
				
				<tr>
					<td><small>Location</small></td>
					<td>-</td>					
				</tr>
				
				<tr>
					<td><small>Emp.No.</small></td>
					<td><bean:write property="empcod" name="payslipForm"/></td>
					<td><small>Name</small></td>
					<td><bean:write property="empname" name="payslipForm"/></td>
					<td><small>Dept.</small></td>
					<td><bean:write property="dept" name="payslipForm"/></td>
					<td><small>Title</small></td>
					<td><bean:write property="desg" name="payslipForm"/></td>
				</tr>

				<tr>
					<td><small>Address</small></td>
					<td colspan="3"><bean:write property="empaddr" name="payslipForm"/></td>
					<td colspan="2"><b>Reporting Manager</b></td>
					<td colspan="2"><bean:write property="repmng" name="payslipForm"/></td>
				</tr>

				<tr>
					<td><small>Date Of Join</small></td>
					<td><bean:write property="doj" name="payslipForm"/></td>
					<td><small>PF No.</small></td>
					<td><bean:write property="pfno" name="payslipForm"/></td>
					<td><small>ESI No.</small></td>
					<td><bean:write property="esino" name="payslipForm"/></td>
					<td><small>PAN No.</small></td>
					<td><bean:write property="panno" name="payslipForm"/></td>
				</tr>

				<tr>					
					<td colspan="2"><small>Bank Name</small></td>
					<td colspan="3"><bean:write property="bnkname" name="payslipForm"/></td>
					<td><small>Account No.</small></td>
					<td colspan="2"><bean:write property="baccno" name="payslipForm"/></td>
				</tr>

				<tr>
					<td><small>Working Days</small></td>
					<td style="text-align: right;"><bean:write property="worgdays" name="payslipForm"/></td>
					<td><small>Days Worked</small></td>
					<td style="text-align: right;"><bean:write property="daywrkd" name="payslipForm"/></td>
					<td><small>L O P</small></td>
					<td style="text-align: right;"><bean:write property="lop" name="payslipForm"/></td>
					<td><small>Days Paid</small></td>
					<td style="text-align: right;"><bean:write property="daypayd" name="payslipForm"/></td>
				</tr>

				<tr>
					<th colspan="8">Leave Balance</th>
				</tr>
				<tr>
					<td><small>Casual<small></td>
					<td style="text-align: right;"><bean:write property="cl" name="payslipForm"/></td>
					<td><small>Sick</small></td>
					<td style="text-align: right;"><bean:write property="sl" name="payslipForm"/></td>
					<td><small>Earned</small></td>
					<td style="text-align: right;"><bean:write property="el" name="payslipForm"/></td>
					<td><small>Maternity</small></td>
					<td style="text-align: right;">-</td>
				</tr>

				<tr>
					<th colspan="2">Earnings</th>
					<th>Entitled Amt.</th>
					<th>Payable Amt.</th>
					<th colspan="2">Deductions</th>
					<th>Entitiled Amt.</th>
					<th>Payable Amt.</th>
				</tr>

				
							<logic:notEmpty name="earnings">
							<tr>
								<logic:iterate id="recordse" name="array1">
										<td colspan="2"><bean:write property="eh" name="recordse"/></td>
										<td style="text-align: right;"><bean:write property="ea" name="recordse"/></td>
										<td style="text-align: right;"><bean:write property="ec" name="recordse"/></td>
								</logic:iterate>
							</tr>
							</logic:notEmpty>
				
							<logic:notEmpty name="deduction">
							<tr>
								<logic:iterate id="records2" name="array2">
										<td colspan="2"><bean:write property="dh" name="records2"/></td>
										<td style="text-align: right;"><bean:write property="da" name="records2"/></td>
										<td style="text-align: right;"><bean:write property="dc" name="records2"/></td>
								</logic:iterate>
							</tr>
							</logic:notEmpty>
				
				<tr>
					<td colspan="2"><small>Total Earnings</small></td>
					<td style="text-align: right;"><bean:write property="arrears_totEarn" name="payslipForm"/></td>
					<td style="text-align: right;"><bean:write property="current_totEarn" name="payslipForm"/></td>
					<td colspan="2"><small>Total Deductions</small></td>
					<td style="text-align: right;"><bean:write property="arrears_totDeduc" name="payslipForm"/></td>
					<td style="text-align: right;"><bean:write property="current_totDeduc" name="payslipForm"/></td>
				</tr>
				<tr>
					<td colspan="2"><small>Loan Bal.</small></td>
					<td colspan="2" style="text-align: right;">00.00</td>
					<td colspan="2"><small>Net Payable</small></td> 
					<td colspan="2" style="text-align: right;"><bean:write property="netpay" name="payslipForm"/></td>					
				</tr>

				<tr>
					<td><small>General Note</small></td>
					<td colspan="7">xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx</td>
				</tr>

				<tr>
					<th colspan="8" style="text-align: center;">Projected Annual Computation</th>
				</tr>

				<tr>
					<td colspan="2"><small>Annual Income</small></td>
					<td colspan="2" style="text-align: right;">00.00</td>
					<td colspan="2"><small>Net Taxable Income</small></td>
					<td><small>Balance Tax</small></td>
					<td style="text-align: right;">00.00</td>
				</tr>
				
				<tr>
					<td colspan="2"><small>Total Tax Payable</small></td>
					<td colspan="2" style="text-align: right;">00.00</td>
					<td colspan="2"><small>Tax Deducted</small></td>
					<td colspan="4" style="text-align: right;">00.00</td>
				</tr>

			
			</table>
	    </div>
	</logic:notEmpty>
	</html:form>

</body>
</html>