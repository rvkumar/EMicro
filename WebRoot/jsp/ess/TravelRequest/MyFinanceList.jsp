<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'domestic.jsp' starting page</title>
    
	
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->


<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      <link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
      <link rel="stylesheet" type="text/css" href="css/styles.css" />

  <style type="text/css">
@import "jquery.timeentry.css";
</style>
      <script type="text/javascript" src="timeEntry.js"></script>
<script type="text/javascript" src="jquery.timeentry.js"></script>

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



$(function() {
	$('#popupDatepicker3').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker3').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>
<script type="text/javascript">$(function () {


	$('#timeFrom').timeEntry();
});


$(function () {


	$('#timeTo').timeEntry();
});

$('.timeRange').timeEntry({beforeShow: customRange}); 
 
function customRange(input) { 
    return {minTime: (text.styleId == 'timeTo' ? 
        $('#timeFrom').timeEntry('getTime') : null),  
        maxTime: (text.styleId  == 'timeFrom' ? 
        $('#timeTo').timeEntry('getTime') : null)}; 
}


/* function statusMessage(message){
alert(message);
if(message!=null)
{
document.forms[0].action="travelrequest.do?method=searchSubmitFinanceDetails";
	document.forms[0].submit();
	}

} */

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function nextRecord()
{
     
	document.forms[0].action="travelrequest.do?method=nextdisplayMyrequest";
	document.forms[0].submit();

}
function previousRecord()
{
    
	document.forms[0].action="travelrequest.do?method=prevdisplayMyrequest";
	document.forms[0].submit();

}


function cancelrecord(reqid)
{
	document.forms[0].action="travelrequest.do?method=cancelRecord&requstNo="+reqid;
	document.forms[0].submit();

}


function search()
{
	if(document.forms[0].fromdate.value=="")
	{
	alert("Please Select From date");
	return false;
	
	}
	
	if(document.forms[0].todate.value=="")
	{
	alert("Please Select To date");
	return false;
	
	}
	if(document.forms[0].reqStatus.value=="Booked")
	{
	if(document.forms[0].travelagentname.value=="")
	{
	alert("Please Select Travel Agent Name");
	return false;
	}
	}
	
	
	
	document.forms[0].action="travelrequest.do?method=searchSubmitFinanceDetails";
	document.forms[0].submit();

}

function sumamount1(com,id)
{
if(document.getElementById(id).checked==true)
{
if(com=="No")
{
alert("User has not yet given the Travel Confirmation");
document.getElementById(id).checked=false;
return false;
}
}


var k=0;
$('#sumtotal').val(0);

	 var t =document.getElementsByName("selectedRequestNo").length;
		for(var i=0; i <t; i++)
		{	
			if(document.getElementsByName("selectedRequestNo")[i].checked== true )
			{
			
			if(document.getElementsByName("billtype")[i].value=="Credit Note")
			{
			k=k-parseInt(document.getElementsByName("billamount")[i].value);
			}
			else
			{
			k=k+parseInt(document.getElementsByName("billamount")[i].value);
			}
			 
			  
				
			 
			}
			
		}
		$('#sumtotal').val(k); 

}


function sumamount()
{


var k=0;
$('#sumtotal').val(0);

	 var t =document.getElementsByName("selectedRequestNo").length;
		for(var i=0; i <t; i++)
		{	
			if(document.getElementsByName("selectedRequestNo")[i].checked== true )
			{
			
			if(document.getElementsByName("billtype")[i].value=="Credit Note")
			{
			k=k-parseInt(document.getElementsByName("billamount")[i].value);
			}
			else
			{
			k=k+parseInt(document.getElementsByName("billamount")[i].value);
			}
			 
			  
				
			 
			}
			
		}
		$('#sumtotal').val(k); 
		


}

function checkAll()
	{
	
	 var t =document.getElementsByName("selectedRequestNo").length;
		for(var i=0; i<t; i++){
			if(document.forms[0].checkProp.checked==true)
			
			{
			
			   document.getElementsByName("selectedRequestNo")[i].checked = true ;
			}
			else
			{
				document.getElementsByName("selectedRequestNo")[i].checked = false ;
			}	
		}
		
		
		sumamount();
		
		
	}
	
	function statusMessage(message){
alert(message);
}
	
	
function submittofinance()
{

	
	
	if(document.forms[0].total.value=="0" || document.forms[0].total.value=="")
	{
	alert("Please Select Bills");	
	return false;
	}
	
	
	var requestNumber=document.forms[0].requestNumber.value;
	document.forms[0].action="travelrequest.do?method=submitofFinance&requestNumber='"+requestNumber+"'";
	document.forms[0].submit();

}

function viewbill(id)
{

document.forms[0].action="travelrequest.do?method=viewbillupload&reqNo="+id;
	document.forms[0].submit();
}


function unselect()
{

var t =document.getElementsByName("selectedRequestNo").length;
		for(var i=0; i<t; i++){
		document.getElementsByName("selectedRequestNo")[i].checked = false ;
		}

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
  
  <body onload="unselect()">
  <logic:equal value="pdf" name="travelRequestForm" property="reqStatus" >
				<div style="display: none;" ><a href="${travelRequestForm.fileFullPath}"  target="_blank" id="pdf"></a></div>
					<script language="javascript">
	
						document.getElementById("pdf").click();
					</script>
			</logic:equal>
  	<div align="center">
				<logic:notEmpty name="travelRequestForm" property="message">
					
					 <script language="javascript">
				 	/* statusMessage('<bean:write name="travelRequestForm" property="message" />');  */
					</script>
				</logic:notEmpty>
			
			</div>
  <br/>
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  		
  		<div>
  		
  		
  		<table class="bordered" style="position: relative;left: 2%;width: 80%;"><tr><th colspan="4">Search</th></tr> 
  	<tr><td>From date</td><td><html:text property="fromdate"   size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker" readonly="true"/></td>
  	<td>To date</td><td><html:text property="todate"   size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker2" readonly="true"/></td></tr>
  	<tr>
  	<td> Traveller name
  	</td>
  	<td>
  	
	<html:select  property="travelagentname" name="travelRequestForm" >
			<html:option value="">--Select--</html:option>
			<html:options name="travelRequestForm"  property="travIdList" labelProperty="travLabelList"/>
			</html:select>
  	</td>
  	
  	<td>Request Status</td><td colspan="1"><html:select name="travelRequestForm" property="reqStatus" styleClass="text_field">
								
									<html:option value="Booked">Booked</html:option>
									<html:option value="Submitted to Finance">Submitted to Finance</html:option>
									</html:select>
									&nbsp;&nbsp;&nbsp;&nbsp;
									<html:button property="method" styleClass="rounded"  value="go" onclick="search();" style="align:right;width:100px;"/>
									</td>
									</tr>
									<tr>
									
	</tr></table><br/>
  		
         			<table  style="width: 80%;">
						<tr><td align="center">
							<logic:notEmpty name="displayRecordNo">
	 						
								<logic:notEmpty name="disablePreviousButton">
									&nbsp;<img src="images/disableLeft.jpg" align="absmiddle"/>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="previousButton">
									&nbsp;<a href="travelrequest.do?method=prevdisplayMyrequest"><img src="images/previous1.jpg"  align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
									&nbsp;<bean:write property="startRecord"  name="travelRequestForm"/>&nbsp;-&nbsp;<bean:write property="endRecord"  name="travelRequestForm"/>&nbsp;
								<logic:notEmpty name="nextButton">
									&nbsp;<a href="travelrequest.do?method=nextdisplayMyrequest"><img src="images/Next1.jpg" align="absmiddle"/></a>&nbsp;
								</logic:notEmpty>
								<logic:notEmpty name="disableNextButton">
									&nbsp;<img src="images/disableRight.jpg" align="absmiddle"/>&nbsp;
								</logic:notEmpty>
								</logic:notEmpty>
								</td>
							</tr>
						</table>
						</div>
  	  <br/>
  	  
  	 
				
  	  <logic:notEmpty name="finlist">
  	  
   	  <div style="margin-left: 20px">
  	  <html:button property="method" styleClass="rounded"  value="Submit to Finance" onclick="submittofinance();" style="align:right;width:100px;"/>
  	  </div>
  	  <br/>
  	  
  	  
  	  <table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="16">Booking List</th>
						</tr>
						<tr>
						<th><!-- <input type="checkbox" name="checkProp" id="r4" onclick="checkAll()"/> -->Select	</th>
						<th>Bill Type</th>
						<th>Travel Req No</th>
						<th>Travel Agent Name</th> 
						<th>Bill No</th>
						<th>Bill Date</th>
						<th>Bill Amount</th>	
						<th>Travel Confirmed</th>
						
							
						</tr>
						<logic:notEmpty name="finlist">
						<logic:iterate id="abc" name="finlist">
						
						
						<tr>
						<td>
						<html:checkbox property="selectedRequestNo" name="travelRequestForm" value="${abc.id}" styleId="${abc.id}" onclick="sumamount1('${abc.review_Rating}',this.id)"/>
						</td>
						<td>${abc.billtype}<input type="hidden" name="billtype" value="${abc.billtype}"/></td>
						<td>${abc.requestNumber}<input type="hidden" name="requestNumber" id="requestNumber1"  value="${abc.requestNumber}"/></td>
						<td>${abc.travelagentname} </td>
						<td>${abc.billno}</td>
						<td>${abc.billdate}</td>
						<td>${abc.billamount}<input type="hidden" name="billamount" value="${abc.billamount}"/> </td>
						<td>${abc.review_Rating}</td>
						</tr>
						
						
						</logic:iterate>
						
						</logic:notEmpty>
						<tr>
						<th colspan="8" >
						<div style="float: right">
						Total
						<input type="text"  name="total"  readonly="readonly" value="" id="sumtotal" />
						</div>
						</th>
						</tr>
						
					
						</table>
						
						<br/>
						
		 </logic:notEmpty>	
		 
		 <logic:notEmpty name="finsublist">	
		 
		 
  	  <table class="bordered" style="position: relative;left: 2%;width: 80%;">
						<tr>
						<th colspan="10">Finance List</th>
						</tr>
						<tr>
						<th>Req No</th>
						<th>Travel Agent Name</th>
						<th>Amount</th> 
						<th>Submitted Date</th>
						</tr>
						<logic:notEmpty name="finsublist">
						<logic:iterate id="abc" name="finsublist">
						<tr>
						<td ><font color="red"><a href="javascript:viewbill('${abc.requestNumber}')" >${abc.requestNumber}</a></font></td>
						<td>${abc.travelagentname} </td>
						<td>${abc.billdate}</td>
						<td>${abc.billamount} </td>
						</tr>
						
						
						</logic:iterate>
						
						</logic:notEmpty>
					
						</table>
		 
		 </logic:notEmpty>		
  
  			</html:form>			
  </body>
</html>
