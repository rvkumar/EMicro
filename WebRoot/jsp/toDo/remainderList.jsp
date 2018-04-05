<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>To Do List</title>
		<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
	
	<script type="text/javascript">
	
	function deleteTask(){
	var id=document.forms[0].sno.value;
	var url="todoTask.do?method=deleteTask&id="+id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	function modifyTask(){
		
   var subject = document.forms[0].subject.value;
	var description= document.forms[0].description.value;
	var fromdate= document.forms[0].from_date.value;
	var fromtime= document.forms[0].from_time.value;
	var todate= document.forms[0].to_date.value;
	var totime= document.forms[0].to_time.value;
	if(subject=="")
	{
	alert("Please enter Subject ")
    document.forms[0].subject.focus();
	return false;
	}
	
		if(fromdate=="")
	{
	alert("Please enter From Date ")
	document.forms[0].from_date.focus();
	return false;
	}
	
	if(todate=="")
	{
	alert("Please enter To Date ")
    document.forms[0].to_date.focus();
	return false;
	}
	
			if(fromdate!=todate)
	    {
var a=fromdate.split("/");
var b=todate.split("/");
var dt1  = parseInt(a[0]); 
var mon1 = parseInt(a[1]); 
var yr1  = parseInt(a[2]); 
var dt2  = parseInt(b[0]); 
var mon2 = parseInt(b[1]); 
var yr2  = parseInt(b[2]); 
var date1 = new Date(yr1, mon1, dt1); 
var date2 = new Date(yr2, mon2, dt2); 
if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
   document.forms[0].to_date.value="";
     document.forms[0].to_date.focus();
     return false;
}
}
	     	
             if(fromdate==todate)
	    { 
	          
 var startTime=fromtime;
 var endTime=totime;
 var startPeriod=startTime.slice(-2);
 var endPeriod=endTime.slice(-2);
 
 if(startPeriod=='PM' && endPeriod=='AM')
 {
 
 alert("Please enter valid time cycle");
 
 return false;
 
 }  
    
 if(startPeriod=='AM' && endPeriod=='AM' || startPeriod=='PM' && endPeriod=='PM')
 {
 
    var startHR=startTime.substring(0,2);
 var endHR=endTime.substring(0,2);
   var startMin=startTime.substring(3,5);
 var endMin=endTime.substring(3,5);
 

 
 startHR=parseInt(startHR); 
 endHR=parseInt(endHR);
  startMin=parseInt(startMin); 
 endMin=parseInt(endMin);

   if ( startPeriod=='PM' && endPeriod=='PM' )
 {
 
 if(startHR!="12")
 {
  startHR=startHR+12;
 }
 if(endHR!="12")
 {
  endHR=endHR+12;
 }
 }
 
 

 

 if(startHR>endHR)
 {
  alert("To time should be greater than From time");

 return false;
 
 }
  if(startHR==endHR && endMin<startMin )
 {
  alert("To time Minutes should be greater than From time Minutes");

 return false;
 }
 }
 }   	
	
	var id=document.forms[0].sno.value;
	
	var url="todoTask.do?method=modifyTask&id="+id;
	document.forms[0].action=url;
	document.forms[0].submit();
	}
	
	 function closeTask()
 {
 
		 window.close();
	        window.opener.location.reload();
 }
 
	</script>
	
	<%--     Calender   --%>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>


<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.datepick.js"></script>

<script type="text/javascript">
$(function() {
	$('#taskdate').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});
$(function() {
	$('#taskdate2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
});
function showDate(date) {
	alert('The date chosen is ' + date);
}
</script>

<%--     Calender   --%> 
	<script type="text/javascript">
 
    function refreshParent() {
    	window.close();
        window.opener.location.reload();
    }
</script>
	</head>
	<body>
<html:form action="/todoTask.do">
<div  >
						<logic:present name="todoForm" property="statusMessage">
						<font color="red">
							  <b>  <center><bean:write name="todoForm" property="statusMessage" /></center></b>
						</font>
					</logic:present>
					
					</div>
			<logic:notEmpty name="listOfRecords">
			
			<table class="bordered" style="width:80%;" align="center">
		
			<tr >
			<th>S.No<th>Subject</th><th>Description</th><th>From Date</th><th>To Date</th><th>Edit</th>
			</tr>
			<%
			int i=1;
			%>
			<logic:iterate id="listID" name="listOfRecords">
			
			<tr>
			<td>
			<%=i %></a>
			</td>&nbsp;
			<td ><bean:write property="subject" name="listID" /></td>&nbsp;
			<td><bean:write property="description" name="listID"/>&nbsp;</td>&nbsp;
			<td><bean:write property="from_date" name="listID"/></td>&nbsp;
		    <td><bean:write property="to_date" name="listID"/></td>&nbsp;
		    <td><a href="todoTask.do?method=getReminderDetails&sno=<bean:write property="sno" name="listID"/>&reqdate=<bean:write property="requiredRemiderDate" name="todoForm"/>"><img src="images/edit.png" />
		    
		    </a>
		    		
            </td>
            
		    <%i++; %>
			</logic:iterate>
		    </table>

			</logic:notEmpty>
			<br>
			<center style="padding-right:  35mm;"><table class="bordered" style="width:50%;" align="center">
			
			<logic:notEmpty name="reminderDetails"  >
			<logic:iterate name="reminderDetails" id="list"></logic:iterate>
			<tr ><th colspan="2"><center>Reminder Details</center></th></tr>
			<tr>
			<td>Subject<font color="red" >*</font></td><td><html:text property="subject" name="list" /></td></tr>
			<tr>
			<td>Description</td><td><html:textarea property="description" name="list"  rows="3" cols="45" /></td></tr>
			<tr>
			<td>From Date<font color="red" >*</font></td><td><html:text property="from_date" name="list"  styleId="taskdate" readonly="true"/></td></tr>
			<tr>
			<td>From Time</td><td><html:text property="from_time" name="list"  /></td></tr>
			<tr>
			<td>To Date<font color="red" >*</font></td><td><html:text property="to_date" name="list"  styleId="taskdate2" readonly="true"/></td></tr>
			<tr>
			<td>To Time</td><td><html:text property="to_time"  name="list"  /></td></tr>
			<html:hidden property="sno" name="todoForm"/>
			</td></tr>
			<tr>
			
			<td colspan="2"><center><html:button property="method"  value="Delete" onclick="deleteTask()" styleClass="rounded" style="width: 100px"/>
		<html:button property="method"  value="Modify" onclick="modifyTask()" styleClass="rounded" style="width: 100px" />
		<html:button property="method"  value="Close" onclick="closeTask()" styleClass="rounded" style="width: 100px"/>	</center></td>
		
			</tr>
		
			</logic:notEmpty>
			<logic:notEmpty name="details">
			<tr >
			<th>S.No<th>Subject</th><th>Description</th><th>From Date</th><th>To Date</th><th>Edit</th>
			</tr>
			</logic:notEmpty>
		
			</table>
			</center>
			<html:hidden property="requiredRemiderDate" name="todoForm"/>
			</html:form>
			</body>
			</html>