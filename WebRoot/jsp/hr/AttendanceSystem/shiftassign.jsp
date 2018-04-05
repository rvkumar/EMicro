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
<link rel="stylesheet" type="text/css" href="css/microlabs1.css" />

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
<script src="js/sumo1.js"></script>
<!-- <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script> -->
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
	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});

function showDate(date) {
	alert('The date chosen is ' + date);
}

function checkAll()
    	{
    	    var t =document.getElementsByName("selectedRequestNo").length;
    		for(var i=1; i <= t; i++){
    			if(document.forms[0].checkProp.checked==true)
    			   document.getElementById("a"+i).checked = true ;
    			else
    				document.getElementById("a"+i).checked = false ;
    		}
    	}
</script>


<title>Home Page</title>

<script language="javascript">
function massshiftassign()
    {
		    
  
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
		 var sh=document.getElementById("shift").value;
		 var swi=document.getElementById("swipe").value;

		
		if(sh=="" && swi=="")
		{
			alert("Select Atleast One DropDown list ");
			return false;
			
		}
		
		
		
		
		document.forms[0].action="hrApprove.do?method=massUpdateShiftAssign";
		document.forms[0].submit();
		    
    }


function update()
{
	
	document.forms[0].action="hrApprove.do?method=updateShiftAssign";
	document.forms[0].submit();
}




function statusMessage(message){
alert(message);
}

function search(){



var endDate=document.forms[0].toDate.value;

var startDate=document.forms[0].fromDate.value;




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

}




	document.forms[0].action="hrApprove.do?method=shiftassignSearch";
	document.forms[0].submit();
}

function onUpload(){

if(document.forms[0].documentFile.value=="")
	    {
	      alert("Please Select File ");
	      document.forms[0].documentFile.focus();
	      return false;
	    }

document.forms[0].action="hrApprove.do?method=uploadFileAction";
document.forms[0].submit();
} 
function ondwnload(){


	
document.forms[0].action="hrApprove.do?method=downloadExceltemplate";
document.forms[0].submit();
} 

</script>
</head>

<body>

				
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
		<th colspan="5">Shift Assignment</th>
		</tr>
			
	
		<tr>
		<td>Location<font color="red">*</font></td>
		<td>
		
			<html:select  property="locationId" name="hrApprovalForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			
			<html:options name="hrApprovalForm"  property="locationIdList" labelProperty="locationLabelList"/>
		</html:select> 
		</td>
		</tr>
		
		<tr>
		<td>Pay group</td>
		<td>
		
		<html:select  property="payArray" name="hrApprovalForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">
			
			<html:options name="hrApprovalForm"  property="payGroupList" labelProperty="payGroupLabelList"/>
		</html:select>
		
			
		</td>
		</tr>
		
		<tr>
		<td>Staff Category</td>
		<td>
		

		
		<html:select  property="catArray" name="hrApprovalForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">
			
			<html:options name="hrApprovalForm"  property="categoryList" labelProperty="categoryLabelList"/>
			
		</html:select>
		</td>
		</tr>
		
		<tr>
<td>Department</td>
<td colspan="3">
<html:select  property="deptArray" name="hrApprovalForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">
			
			<html:options name="hrApprovalForm"  property="deptList" labelProperty="deptLabelList"/>
		</html:select>



</td>
</tr>
<tr><td>Reporting Group</td>
<td colspan="3">
<html:select  property="repgrpArray" name="hrApprovalForm" multiple="true"  onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">

<html:options name="hrApprovalForm"  property="repgrpList" labelProperty="repgrpLabelList"/>
</html:select>


</td></tr>
		<tr>
		<td>Work Location</td>
		<td>
		

		
		<html:select  property="locArray" name="hrApprovalForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">
			
			<html:options name="hrApprovalForm"  property="workList" labelProperty="workLabelList"/>
			
		</html:select>
		
		</td>
		</tr>
	
	<tr>
		<td>Date Of Joining</td>
<td colspan="3">
		<html:text property="fromDate" name="hrApprovalForm" styleId="popupDatepicker" style="width: 98px; "/>&nbsp;To&nbsp;
<html:text property="toDate" name="hrApprovalForm"  styleId="popupDatepicker2" style="width: 98px; "/></td>
		
		</tr>
		
		<tr>
		<td>Employee No.</td>
<td colspan="3">
		<html:text property="frompernr" name="hrApprovalForm"  style="width: 98px; "/></td>
		
		</tr>
</table>
</br>

<table class="bordered" >
<tr><th>Status </th></tr>
<tr>
<td>

<input type="radio" name="status"    value="Pending For Shift Assignment" checked="checked"<logic:equal name="hrApprovalForm" property="status" value="Pending For Shift Assignment">checked</logic:equal>>
Pending For Shift Assignment &nbsp;&nbsp;
<input type="radio" name="status"   value="all"  <logic:equal name="hrApprovalForm" property="status" value="all">checked</logic:equal>>
All

</td>
</tr>

</table>

</br>
 <table  class="bordered">
 <tr>
<th colspan="2">Download Excel Template</th>
</tr>
<tr>
<td>Month</td>
<td>

<html:select  property="month" name="hrApprovalForm" styleClass="testselect1">
<html:option value="1">Jan</html:option>
<html:option value="2">Feb</html:option>
<html:option value="3">Mar</html:option>
<html:option value="4">April</html:option>
<html:option value="5">May</html:option>
<html:option value="6">June</html:option>
<html:option value="7">July</html:option>
<html:option value="8">Aug</html:option>
<html:option value="9">Sep</html:option>
<html:option value="10">Oct</html:option>
<html:option value="11">Nov</html:option>
<html:option value="12">Dec</html:option>
</html:select>

<html:select  property="year" name="hrApprovalForm" styleClass="testselect1">
<html:options name="hrApprovalForm"  property="yearList"/>
</html:select>&nbsp;&nbsp;&nbsp;
<html:button value="Download" onclick="ondwnload()" property="method" styleClass="rounded" style="width: 100px" />
</td>


</tr>
<tr>
<th colspan="2">Upload Excel File</th>
</tr>
<tr>	
				<td colspan="4">
					<html:file  property="documentFile" styleClass="rounded" style="width: 220px"/>&nbsp;&nbsp;&nbsp;
					<html:button value="Upload" onclick="onUpload()" property="method" styleClass="rounded" style="width: 100px" />
				</td>
				
			</tr></table> 
<!-- 
<table class="bordered" >
<tr><th>Employee Status</th></tr>
<tr>
<td>
<input type="radio" name="activeStatus" id="a3" value="all" <logic:equal name="hrApprovalForm" property="status" value="all">checked</logic:equal>>
All &nbsp;&nbsp;
<input type="radio" name="activeStatus" id="a4" value="active" <logic:equal name="hrApprovalForm" property="status" value="active">checked</logic:equal>>
Active &nbsp;&nbsp;
<input type="radio" name="activeStatus"  id="a5"   value="left" <logic:equal name="hrApprovalForm" property="status" value="left">checked</logic:equal>>
Left
</td>
</tr> 

</table>-->



<br/>
<center>
<div>
<html:button property="method" value="Execute" onclick="search()" styleClass="rounded"/>&nbsp;
<logic:notEmpty name="list">
<%-- <html:button property="method" value="Update" onclick="update()" styleClass="rounded"/> --%>
</logic:notEmpty>
</div>
</center>
<br/>

<logic:notEmpty name="list">
<table class="bordered">
<tr>
<th colspan="5">Mass Update</th>
</tr>
<tr>
<td>
Shift
</td>
<td>
<html:select  property="shift" name="hrApprovalForm" styleId="shift" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:option value="">--Select--</html:option>
			<html:options name="hrApprovalForm"  property="shiftList" labelProperty="shiftLabelList"/>
</html:select>
</td>
<td>
Swipe
</td>
<td>
<html:select  property="swipe_Count" name="hrApprovalForm"  styleId="swipe" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
			<html:option value="">--Select--</html:option>
			<html:option value="0">0</html:option>
			<html:option value="1">1</html:option>
			<html:option value="2">2</html:option>
			
</html:select>
</td>
<td>
<html:button property="method" value="Update" onclick="massshiftassign()" styleClass="rounded"/>
</td>

</tr>
</table>
</logic:notEmpty>

</br>
</br>

 <div class="row form-group">
                  <div class="col-lg-12 col-md-12 form-group">
                
				  <%int i=0;%>
				    <table class="bordered" >
                 
                     <tr>   <th colspan="12"><center>Search List</center></th></tr>
                  <tr>
                  
                    <th >Select&nbsp;<input type="checkbox" name="checkProp" id="r4" onclick="checkAll()"/></th>
                    <th>Emp Code</th>
                    <th>Employee Name</th>
                    <th>Plant</th>                    
                    <th>Shift</th>
                  
                    <th>Swipe</th>
                    <th>Designation</th>
                    <th>Department</th>
                    <th>DOJ</th>
                    <th>Pay Group</th>
                    
                    
                  </tr>
                
                   <logic:notEmpty name="list">
                  <logic:iterate id="abc1" name="list">
                   <%i++; %>
                <tr>               
                    <td> <input type="checkbox"   id=a<%=i%>  name="selectedRequestNo" value="${abc1.employeeno}" /> </td>  
                    <td> <bean:write name="abc1" property="employeeno"/><input type="hidden" name="employeeno" value="${abc1.employeeno}" /> </td>
                    <td> <bean:write name="abc1" property="employeeName"/></td>
                    <td> <bean:write name="abc1" property="plant"/></td>
                    <td>
                    <select id="investmentcode1" name="shiflist" disabled="disabled" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
                      <option value="select">select</option>
					  <c:forEach var="item" items="${hrApprovalForm.shiftList}">
    				  <option value="${item}" <logic:equal name="abc1" property="shift" value="${item}">selected="selected" </logic:equal> ><c:out value="${item}" /></option>
					  </c:forEach>
                    </select>
                    
                    </td>
                    
                    
                    
    			
                    <td> 
                    
                    <select id="investmentcode1" name="swipe_Count" disabled="disabled" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1">
                    <option value="select">select</option>
                    <option value="0" <logic:equal name="abc1" property="swipe_Count" value="0">selected="selected" </logic:equal> >0</option>
                    <option value="1" <logic:equal name="abc1" property="swipe_Count" value="1">selected="selected" </logic:equal> >1</option>
                    <option value="2" <logic:equal name="abc1" property="swipe_Count" value="2">selected="selected" </logic:equal> >2</option>
                    </select>
                    
                    
                    </td>
                    <td> <bean:write name="abc1" property="designation"/></td>
                    <td> <bean:write name="abc1" property="department"/></td>
                    
                    <td> <bean:write name="abc1" property="doj"/></td>
                    <td> <bean:write name="abc1" property="paygrp"/></td>
                  </tr>
				 </logic:iterate>
				 
				 </logic:notEmpty>
				<%if(i==0) 
				{
				%>
				<tr><td colspan="12"><center>Currently details are not available to display</center></td></tr>
				<%} %> 
		
				 </table>
				 </div>
				 </div>

</div>
</html:form>
</body>
</html>
