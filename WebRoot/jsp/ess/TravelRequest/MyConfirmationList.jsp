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


function statusMessage(message){
alert(message);
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}




function searchrequest(){

	
	if(document.forms[0].fromdate.value=="")
		{
		alert("Please Select fromdate ");
		 document.forms[0].fromdate.focus();
		  return false;
		}
	
	if(document.forms[0].todate.value=="")
	{
	alert("Please Select todate ");
	 document.forms[0].todate.focus();
	  return false;
	}
	

	document.forms[0].action="travelrequest.do?method=displayConfirmationDetails";
	document.forms[0].submit();
	}
function searchEmployee(fieldName){
	var reqFieldName=fieldName;


		var toadd = document.getElementById(reqFieldName).value;
		if(toadd.indexOf(",") >= -1){
			toadd = toadd.substring((toadd.lastIndexOf(",")+1),toadd.length);
		}
		document.getElementById(reqFieldName).focus();
		if(toadd == ""){
			document.getElementById(reqFieldName).focus();
			document.getElementById("sU").style.display ="none";
			return false;
		}
		var xmlhttp;
	    if (window.XMLHttpRequest){
	        // code for IE7+, Firefox, Chrome, Opera, Safari
	        xmlhttp=new XMLHttpRequest();
	    }
	    else{
	        // code for IE6, IE5
	        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	    }

	    xmlhttp.onreadystatechange=function(){
	        if (xmlhttp.readyState==4 && xmlhttp.status==200){
	        
	        	document.getElementById("sU").style.display ="";
	        	document.getElementById("sU").className="overlay";
	        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
	        	
	       
	        	       			
	        }
	    }
	     xmlhttp.open("POST","leave.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
	    xmlhttp.send();
	}


	function selectUser(input,reqFieldName){
	
	var lastChar = reqFieldName.substr(reqFieldName.length - 1);


	var res = input.split("-");
		document.getElementById(reqFieldName).value=res[1];
	
		disableSearch(reqFieldName);
	}

	function disableSearch(reqFieldName){
	 
			if(document.getElementById("sU") != null){
			document.getElementById("sU").style.display="none";
			
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
.overlay{
  position: relative;
  top: 5px;
  left: 20;
  width: 100%;
  height: 100%;
  z-index: 10;
 
}

</style>
  </head>
  
  <body>
  
  	<div align="center">
				<logic:notEmpty name="travelRequestForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="travelRequestForm" property="message" />');
					</script>
				</logic:notEmpty>
			
			</div>
  <br/>
  	<html:form action="travelrequest" enctype="multipart/form-data" method="post" >
  	
  	<table class="bordered" style="position: relative;left: 2%;width: 80%;"><tr><th colspan="4">Search</th></tr> 
  	<tr><td>From date</td><td><html:text property="fromdate"   size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker" readonly="true"/></td>
  	<td>To date</td><td><html:text property="todate"   size="20"  onkeyup="this.value = this.value.replace(/'/g,'`')" styleId="popupDatepicker2" readonly="true"/></td></tr>
  	<tr><td>Request Status</td><td><html:select name="travelRequestForm" property="reqStatus" styleClass="text_field">
								
									<html:option value="">Pending</html:option>
									<html:option value="Booked">Approved</html:option>
									<html:option value="Rejected">Rejected</html:option>
								
								
									</html:select></td>
									<td>Employee Number</td><td><html:text property="pernr"   size="20"     onkeyup="searchEmployee(this.id)" styleId="employeeNumber1"/> 
	<html:button property="method" styleClass="rounded" value="Search" onclick="searchrequest()" style="align:right;width:100px;"/>
	
	   <div id="sU" style="display:none;">
		<div id="sUTD" style="width:400px;">
		<iframe src="jsp/ess/compoff/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%" ></iframe>
		</div>
	</div>
	</td></tr></table><br/>
  	<table class="bordered" style="position: relative;left: 2%;width: 80%;">
  	<tr><th colspan="14"><center> Travel Confirmation List</center></th></tr>
  	<tr><th>Sl No</th><th>Req No</th><th>Req By</th><th>Req Date</th><th>Req Type</th><th> Type Of travel</th><th>From</th><th>To</th><th>Depart On</th><th>Return on</th><th>Req Status</th><th>Confirmation Status</th><th>Confirmed Date</th><th>View</th></tr>
  	<logic:notEmpty name="travellist">
  	<%int y=0; %>
  	<logic:iterate id="a" name="travellist">
  	<%y++; %>
  	<tr><td><%=y%></td><td>${a.requestNumber }</td><td>${a.employeeName }</td><td>${a.reqDate }</td><td>${a.reqType }</td><td>${a.typeOfTravel }</td><td>${a.fromPlace }</td><td>${a.toPlace }</td><td>${a.departOn }</td><td>${a.returnOn }</td><td>${a.reqStatus }</td>
  	<td>${a.confirmstatus }</td>	<td>${a.confirmdate }</td>
  	<td>
  	<logic:equal value="Approved" name="a" property="reqStatus">
	<a  href="travelrequest.do?method=ViewConfirmationrequest&requstNo=<bean:write name="a" property="requestNumber"/>&requststatus=<bean:write name="a" property="reqStatus"/>" ><img src="images/view.gif" width="28" height="28"/></a>
	</logic:equal>
	</td></tr>
  	
  	</logic:iterate>
  	
  
  	</logic:notEmpty>
  		<logic:empty name="travellist">
  	
  	<tr><td colspan="14"><center>No Records found..</center></td></tr>
  	
  	</logic:empty>
  	</table>
  		
  			</html:form>			
  </body>
</html>
