<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="com.microlabs.ess.form.LeaveForm"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<%@ taglib uri="/WEB-INF/tlds/c.tld" prefix="c"%>
<%@ taglib uri="/WEB-INF/tlds/displaytag-11.tld" prefix="display"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <style type="text/css">
   th{font-family: Arial;}
    td{font-family: Arial; font-size: 12;}
    </style>


<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type='text/javascript' src="calender/js/zapatec.js"></script>
<!-- Custom includes -->
<!-- import the calendar script -->
<script type="text/javascript" src="calender/js/calendar.js"></script>

<!-- import the language module -->
<script type="text/javascript" src="calender/js/calendar-en.js"></script>
<!-- other languages might be available in the lang directory; please check your distribution archive. -->
<!-- ALL demos need these css -->
<link href="calender/css/zpcal.css" rel="stylesheet" type="text/css"/>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<!-- Theme css -->
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<!--
/////////////////////////////////////////////////
-->
<script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>

<script src="js/sumo/jquery.sumoselect1.js"></script>
      <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />

    <script type="text/javascript">
        $(document).ready(function () {
            window.asd = $('.SlectBox').SumoSelect({ csvDispCount: 3 });
            window.test = $('.testsel').SumoSelect({okCancelInMulti:true });
            window.testSelAll = $('.testSelAll').SumoSelect({okCancelInMulti:true, selectAll:true });
            window.testSelAll2 = $('.testSelAll2').SumoSelect({selectAll:true });

        });
        
         $(document).ready(function () {
           $('.testselect1').SumoSelect();

        });
    </script>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script type="text/javascript">

$(function() {
    var existingdate=document.getElementById("message3").value;
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy',maxDate:new Date()});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});


function showDate(date) {
	alert('The date chosen is ' + date);
}


function clearAllFields(){
document.forms[0].endDurationType.value="";
document.forms[0].totalLeaveDays.value="";
}



function statusMessage(message){


	window.close();	alert(message);
}

function process(){

var startDate=document.forms[0].fromDate.value;
var endDate=document.forms[0].toDate.value;
var loc=document.forms[0].locationId.value;
var cat=document.forms[0].staffcat.value;




if(loc=="")
   {
     alert("Please Select Plant");
     document.forms[0].startDate.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
   
  
if(loc=="ML00")
{

if(document.forms[0].repgrp.value=="")
{
  alert("Please Select reporting group");
     document.forms[0].repgrp.focus();
     
     return false;
     }
     
     }

if(startDate=="")
   {
     alert("Please Select From Date");
     document.forms[0].startDate.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }

   if(endDate=="")
   {
     alert("Please Select To Date");
     document.forms[0].endDate.focus();
     document.forms[0].endDurationType.value="";
     return false;
   }
    if(startDate!=""&&endDate!=""){
   
     var str1 = startDate;
var str2 = endDate;
var dt1  = parseInt(str1.substring(0,2),10); 
var mon1 = parseInt(str1.substring(3,5),10); 
var yr1  = parseInt(str1.substring(6,10),10); 
var dt2  = parseInt(str2.substring(0,2),10); 
var mon2 = parseInt(str2.substring(3,5),10); 
var yr2  = parseInt(str2.substring(6,10),10); 
var date1 = new Date(yr1, mon1-1, dt1); 
var date2 = new Date(yr2, mon2-1, dt2); 


if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}

var diffDays = parseInt((date2 - date1) / (1000 * 60 * 60 * 24)); 
if(diffDays>=55)
	{
	alert("Date Range Exceeded");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
	}

}



/* if(cat=="")
   {
     alert("Please Select Category");
     document.forms[0].staffcat.focus();
     document.forms[0].endDurationType.value="";
     return false;
   } */
   
   
var k =0;
var l=document.getElementsByName("selectedRequestNo").length;
for(var i=1; i <=l; i++){
	if(document.getElementById("a"+i).checked==true)
	{
	   
	   k=1;
	}
}
if(k==0)
{

alert("Select Atleast One Checkbox");
return false;

}

	document.forms[0].action="hrApprove.do?method=processattendancefile";
	document.forms[0].submit();
//	window.showModalDialog("main.do?method=alertmessage" ,null, "dialogWidth=650px;dialogHeight=520px;dialogLeft:400px;dialogTop:150px; center:yes;status:no;");
    loading();
    setInterval(loading, 5000);
}
</script>


  <script type="text/javascript">

 function loading() {
	 
	  var id=document.forms[0].lock_id.value;
	
        // add the overlay with loading image to the page
       /*  var over = '<div id="overlay">' +
            '<img id="loading" src="jsp/Progressbar.jsp">' +
            '</div>';
        $(over).appendTo('body'); */

        // click on the overlay to remove it
        //$('#overlay').click(function() {
        //    $(this).remove();
        //});

        // hit escape to close the overlay
     /*    $(document).keyup(function(e) {
            if (e.which === 27) {
                $('#overlay').remove();
            }
        }); */
        
        /* window.showModalDialog("hrApprove.do?method=progressbar" ,null, "dialogWidth=650px;dialogHeight=220px;dialogLeft:400px;dialogTop:150px; center:yes;status:no;"); */
        
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
	      
	        	document.getElementById("overlay").style.display ="";
	        	document.getElementById("overlay").innerHTML=xmlhttp.responseText;
	
	        	   
	        	       			
	        }
	    }
	    xmlhttp.open("POST","hrApprove.do?method=progressbar&id="+id,true);
	    xmlhttp.send();
    };

    function searchEmployee(fieldName){

    	var reqFieldName=fieldName

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
    	        if(reqFieldName=="employeeno"){
    	        	document.getElementById("sU").style.display ="";
    	        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
    	        	}
    	       
    	        	       			
    	        }
    	    }
    	    xmlhttp.open("POST","hrApprove.do?method=searchForManualApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    	    xmlhttp.send();
    	}


    	function selectUser(input,reqFieldName){


    	var res = input.split("-");
    		document.getElementById(reqFieldName).value=res[1];

    		disableSearch(reqFieldName);
    	}

    	function disableSearch(reqFieldName){
    	  if(reqFieldName=="employeeno"){
    			if(document.getElementById("sU") != null){
    			document.getElementById("sU").style.display="none";
    			}
    		}
    		}
    	
    	function showlist()
    	{
    		
    		var startDate=document.forms[0].fromDate.value;
    		var endDate=document.forms[0].toDate.value;
    		var loc=document.forms[0].locationId.value;
    		var cat=document.forms[0].staffcat.value;




    		if(loc=="")
    		   {
    		     alert("Please Select Plant");
    		     document.forms[0].startDate.focus();
    		     document.forms[0].endDurationType.value="";
    		     return false;
    		   }
    		
    		if(loc=="ML00")
    		{

    		if(document.forms[0].repgrp.value=="")
    		{
    		  alert("Please Select reporting group");
    		     document.forms[0].repgrp.focus();
    		     
    		     return false;
    		     }
    		     
    		     }

    		if(startDate=="")
    		   {
    		     alert("Please Select From Date");
    		     document.forms[0].startDate.focus();
    		     document.forms[0].endDurationType.value="";
    		     return false;
    		   }

    		   if(endDate=="")
    		   {
    		     alert("Please Select To Date");
    		     document.forms[0].endDate.focus();
    		     document.forms[0].endDurationType.value="";
    		     return false;
    		   }
    		    if(startDate!=""&&endDate!=""){
    		   
    		     var str1 = startDate;
    		var str2 = endDate;
    		var dt1  = parseInt(str1.substring(0,2),10); 
    		var mon1 = parseInt(str1.substring(3,5),10); 
    		var yr1  = parseInt(str1.substring(6,10),10); 
    		var dt2  = parseInt(str2.substring(0,2),10); 
    		var mon2 = parseInt(str2.substring(3,5),10); 
    		var yr2  = parseInt(str2.substring(6,10),10); 
    		var date1 = new Date(yr1, mon1-1, dt1); 
    		var date2 = new Date(yr2, mon2-1, dt2); 


    		if(date2 < date1) 
    		{ 
    		    alert("Please Select Valid Date Range");
    		    document.forms[0].endDate.value="";
    		     document.forms[0].endDate.focus();
    		     return false;
    		}
    		
    		var diffDays = parseInt((date2 - date1) / (1000 * 60 * 60 * 24)); 
    		if(diffDays>=55)
    			{
    			alert("Date Range Exceeded");
    		    document.forms[0].endDate.value="";
    		     document.forms[0].endDate.focus();
    		     return false;
    			}

    		}



    		/* if(cat=="")
    		   {
    		     alert("Please Select Category");
    		     document.forms[0].staffcat.focus();
    		     document.forms[0].endDurationType.value="";
    		     return false;
    		   } */
    		document.forms[0].action="hrApprove.do?method=showprocesslist";
    		document.forms[0].submit();
    	}
    	
    	
    	function checkAll()
    	{
    	    var t =document.getElementsByName("selectedRequestNo").length;
    		for(i=1; i <= t; i++){
    			if(document.forms[0].checkProp.checked==true)
    			   document.getElementById("a"+i).checked = true ;
    			else
    				document.getElementById("a"+i).checked = false ;
    		}
    	}

  </script>
  
  <style>
  #overlay {
    position: absolute;
    left: 0;
    top: 0;
    bottom: 0;
    right: 0;
    background: #000;
    opacity: 0.9;
    filter: alpha(opacity=70);
    
    
}
#loading {
    width: 250px;
    height: 257px;
    position: absolute;
    top: 20%;
    left: 50%;
    margin: -18px 0 0 -15px;
    
    
}

  </style>
</head>

<body>
<html:hidden property="message3" name="hrApprovalForm" styleId="message3"/>
				
	<html:form action="hrApprove" enctype="multipart/form-data">
	

	
			<div align="center">
				<logic:notEmpty name="hrApprovalForm" property="message">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
				<logic:notEmpty name="hrApprovalForm" property="message2">
					
					<script language="javascript">
					statusMessage('<bean:write name="hrApprovalForm" property="message" />');
					</script>
				</logic:notEmpty>
			</div>

		 <div style="width: 90%" >
		<table class="bordered" >
		<tr>
		<th colspan="5">Process Attendance</th>
		</tr>
	<%-- 	<tr>
		<td>Employee no.</td>
<td colspan="3">
		<html:text property="frompernr" name="hrApprovalForm"  style="width: 98px; "/>&nbsp;To&nbsp;
<html:text property="topernr" name="hrApprovalForm"  style="width: 98px; "/></td>
		
		</tr> --%>
	<%-- 	<tr>
		<td>Company Code</td>
		<td>
		
		<html:text property="plant" name="hrApprovalForm"  />
		</td>
		</tr> --%>
		<tr>
		<td>Location&nbsp;<font color="red">*</font></td>
		<td>
		
			<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:option value="">--Select--</html:option>
			<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select>
		</td>
		</tr>
		<tr>
		<td>Date&nbsp;<font color="red">*</font></td>
<td colspan="3">
		<html:text property="fromDate" name="hrApprovalForm" styleId="popupDatepicker" style="width: 98px; "/>&nbsp;To&nbsp;
<html:text property="toDate" name="hrApprovalForm"  styleId="popupDatepicker2" style="width: 98px; "/></td>
		
		</tr>


<tr>



<td>Staff Category</td>
<td>
<html:select  property="staffcat" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="hrApprovalForm"  property="categoryList" labelProperty="categoryLabelList"/>

</html:select>


</td>

</tr>
<tr><td>Reporting Group</td>
<td colspan="3">
<html:select  property="repgrp" name="hrApprovalForm"  onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="hrApprovalForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
</html:select>


</td></tr>

<tr>
		<td>Work Location</td>
		<td>
		

		
		<html:select  property="locArray" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" multiple="true">			
			<html:options name="hrApprovalForm"  property="workList" labelProperty="workLabelList"/>
			
		</html:select>
		
		</td>
		</tr>

	<tr>

<td>Pay group</td>
<td>

<html:select  property="paygrp" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="hrApprovalForm"  property="payGroupList" labelProperty="payGroupLabelList"/>
</html:select>



</td>


</tr>
<tr>
<td>Department</td>
<td colspan="3">
<html:select  property="department" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="hrApprovalForm"  property="deptList" labelProperty="deptLabelList"/>
</html:select>


</td>
</tr>

<tr>
<td>Designation</td>
<td colspan="3">
<html:select  property="designation" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
<html:option value="">--Select--</html:option>
<html:options name="hrApprovalForm"  property="desgList" labelProperty="desgLabelList"/>

</html:select>


</td>
</tr>

<tr>
	

<td>Employee No <font color="red" style="visibility:hidden;" id="emp">*</font></td>

	<td colspan="3"><html:text property="employeeno"  onkeyup="searchEmployee('employeeno')" styleId="employeeno"  style="width: 84px; " >
	<bean:write property="employeeno" name="hrApprovalForm" /></html:text>
	<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="60" width="60%"></iframe>
		</div>
	</div>
				
		&nbsp;&nbsp;<%-- <html:button property="method" value="Continue" onclick="onSave()" styleClass="rounded" style="width: 81px;"/> --%>
					</td>
</tr>
		<tr>
		<th colspan="5">Type
		</th>
		</tr>
		<tr>
		<td colspan="6">  <input type="radio" name="emptype" id="acb1" value="Active"  checked="checked"<logic:equal name="hrApprovalForm" property="emptype" value="Active">checked</logic:equal>>&nbsp;Active
			&nbsp;  <input type="radio" name="emptype" id="acb1" value="Inactive"  <logic:equal name="hrApprovalForm" property="emptype" value="Inactive">checked</logic:equal>>&nbsp;Inactive</td>
		</tr>
		
		
		
</table>
<br/>
<center>

<div>
<html:button property="method" value="Execute" onclick="showlist()" styleClass="rounded"/>&nbsp;
<logic:notEmpty name="processbutton">
<html:button property="method" value="Process" onclick="process()" styleClass="rounded"/>&nbsp;
</logic:notEmpty>
</div>
</center>

</div>
<br/>
	<div id="overlay" style="display:none;">
		<html:hidden property="lock_id" name="hrApprovalForm" />
		<html:hidden property="progress" name="hrApprovalForm" />
		<iframe src="jsp/hr/AttendanceSystem/Progressbar.jsp"  id="srcUId" scrolling="no" frameborder="0"></iframe>
	
	</div>
	<br/>
<logic:notEmpty name="emptyshiftnos">
 <div style="width: 90%" >
<table class="bordered"><tr>
<th colspan="5"> <center>Shift Assignement Pending List</center></th>
</tr>
<tr>
<th>Number</th><th>Name</th><th>Department</th><th>Designation</th>
<th>DOJ</th>
</tr>
<logic:iterate id="a" name="emptyshiftnos">
<tr>

<td>${a.employeeNumber}</td>
<td>${a.employeeName}</td><td>${a.department}</td><td>${a.designation}</td>
<td>${a.doj}</td>

</tr>
</logic:iterate>

</table>
</div>


</logic:notEmpty>


<logic:notEmpty name="emplist">
		 <div style="width: 90%" >
<table class="bordered">
<tr><th>Select&nbsp;<input type="checkbox" name="checkProp" id="r4" onclick="checkAll()"/></th><th>Pernr</th><th>Name</th><th>Department</th><th>Designation</th><th>Date of Joining</th></tr>
 <% int i = 0; %>
<logic:iterate id="abc" name="emplist">
       <%i++; %>
<tr>

     <td>  <input type="checkbox"   id=a<%=i%>  name="selectedRequestNo" value="${abc.employeeNumber}" /></td>
<td>${abc.employeeNumber}</td><td>${abc.employeeName}</td><td>${abc.department}</td><td>${abc.designation}</td><td>${abc.dateofJoining}</td></tr>
</logic:iterate>
</table>
</div>

</logic:notEmpty>



</html:form>
</body>
</html>
