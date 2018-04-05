<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
      

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
    
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});


function insRow()
{
   
    var x=document.getElementById('compTable');
    var slrow = x.rows.length-1;
    var new_row = x.rows[1].cloneNode(true);
    var len = x.rows.length;
    
 
      var inp1 = new_row.cells[0].getElementsByTagName('input')[0];
    inp1.id = 'slno'+len;
     inp1.name = 'slno';
      inp1.value = parseInt(slrow)+1;
    var inp2 = new_row.cells[1].getElementsByTagName('input')[0];
    inp2.id = 'empno'+len;
    inp2.name = 'empno';
    inp2.value = '';
    
    var imge = new_row.cells[1].getElementsByTagName('img')[0];
    imge.id = 'empno'+len;
   
   
    
    var inp3 = new_row.cells[2].getElementsByTagName('input')[0];
    inp3.id = 'empname'+len;
    inp3.name = 'empname';
    inp3.value = '';
      var inp4 = new_row.cells[3].getElementsByTagName('input')[0];
    inp4.id = 'dept'+len;
    inp4.name = 'dept';
    inp4.value = '';
      var inp5 = new_row.cells[4].getElementsByTagName('input')[0];
    inp5.id = 'desg'+len;
    inp5.name = 'desg';   
     inp5.value = '';
      var inp6 = new_row.cells[5].getElementsByTagName('select')[0];
    inp6.id  = 'nofhrs'+len;
    inp6.name = 'nofhrs';
    inp6.value = '';
     var inp7 = new_row.cells[6].getElementsByTagName('select')[0];
    inp7.id = 'appl'+len;
    inp7.name = 'appl';
    inp7.value = '';
    
    x.appendChild( new_row );
    
    document.getElementById('levelNo').value=len;
}

function deleteRow()
{
	try {
        var table = document.getElementById('compTable');
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


function applyComoff()
{
	if(document.forms[0].startDate.value==""){
alert("Please Select Start Date");
document.forms[0].startDate.focus();
return false;
}
 if(document.forms[0].endDate.value==""){
alert("Please Select End Date");
document.forms[0].endDate.focus();
return false;
}

var startDate=document.forms[0].startDate.value;

var endDate=document.forms[0].endDate.value;

if(startDate!=""&&endDate!=""){
   
     var str1 = document.forms[0].startDate.value;
var str2 = document.forms[0].endDate.value;
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

   if(document.forms[0].reason.value=="")
	    {
	      alert("Please Enter Detailed reason.");
	      document.forms[0].reason.focus();
	      return false;
	    }
	      if(document.forms[0].reason.value!=""){
   
var st = document.forms[0].reason.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].reason.value=st;  
   } 
      if(document.forms[0].reason.value.length>=300){
   
alert("Reason should  be less than 300 characters");
return false;
   }


	var table = document.getElementById('compTable');
	  var rowCount = table.rows.length;
	

  for(var i = 1; i < rowCount; i++)
  {
	  var new_row = table.rows[1].cloneNode(true);
	  var emp = new_row.cells[1].getElementsByTagName('input')[0];
	    var empID=emp.id;	  
	    var empvalue=emp.value;
	    if(empvalue == ""){
			 alert("Please Enter Employee No.");
			document.getElementById(empID).focus();
		  return false;	  
			    }
			    var emplvalue = empvalue;
        var pattern = /^\d+(\d+)?$/
        if (!pattern.test(emplvalue)) {
             alert("Employee No.  Should be Integer ");
              document.getElementById(empID).focus();
            return false;
        }
	  var new_row = table.rows[i].cloneNode(true);
    var emp = new_row.cells[1].getElementsByTagName('input')[0];
    var empID=emp.id;
   var empvalue=emp.value;
   if(empvalue == ""){
			 alert("Please Enter Employee No.");
		document.getElementById(empID).focus();
		  return false;	  
			    }
   	if(empvalue!=""){
    var name = new_row.cells[2].getElementsByTagName('input')[0];
    if(name.value == ""){
		 alert("Please Enter Employee Name");
		 document.getElementById(name.id).focus();
	  return false;	  
		    }
    var dept = new_row.cells[3].getElementsByTagName('input')[0];
    if(dept.value == ""){
	 alert("Please Enter Department");
	 document.getElementById(dept.id).focus();
  return false;	  
	    }
  
    var desg = new_row.cells[4].getElementsByTagName('input')[0];
    if(desg.value == ""){
	 alert("Please Enter Designation");
	  document.getElementById(desg.id).focus();
  return false;	  
	    }
    
    var hrs = new_row.cells[5].getElementsByTagName('select')[0];
    var hrsId=hrs.id;
    var sel = document.getElementById(hrsId);
    var selecVal = sel.options[sel.selectedIndex].value;

    if(selecVal == ""){
		 alert("Please Select No of Hours");
		 document.getElementById(hrsId).focus();
	  return false;	  
		    }
		    
		    var shft = new_row.cells[6].getElementsByTagName('select')[0];
    var shftId=shft.id;
    var sel = document.getElementById(shftId);
    var selecVal = sel.options[sel.selectedIndex].value;
    if(selecVal == ""){
		 alert("Please Select Applicable");
		  document.getElementById(shftId).focus();
	  return false;	  
		    }
   	}
	
  
  
  }
  
  var url="leave.do?method=submitnewcomp";
					document.forms[0].action=url;
					document.forms[0].submit();
  
  }

  



function searchEmployeeId(filed)
	{
	  
		var reqFiled=filed;
	
		//var x=window.open("leave.do?method=displayListUsers&reqFiled="+filed,"SearchSID","width=1100,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
	var x=window.showModalDialog("leave.do?method=displayListUsers&reqFiled="+filed ,window.location, "dialogWidth=850px;dialogHeight=620px; center:yes");
	}
	
	function getCurrentRecord(){

var reqId = document.getElementById("reqId").value;
	var reqType = document.getElementById("reqType").value;
	var totalRecords=document.getElementById("totalReco").value;
	var scnt=document.getElementById("scnt").value;
	var ecnt=document.getElementById("ecnt").value;
		var filterby=document.getElementById("filterby").value;
	
	var url="approvals.do?method=curentRecord&reqId="+reqId+"&reqType="+reqType+"&totalRecord="+totalRecords+"&scnt="+scnt+"&ecnt="+ecnt+"&filterby="+filterby;
	
	document.forms[0].action=url;
	document.forms[0].submit();


}

function changeStatus(elem){

  
	var elemValue = elem.value;
	
	if(elemValue=="Reject"||elemValue=="Cancel")
	{
		if(document.forms[0].remark.value==""){
		alert("Please Add Some Comments");
		 document.forms[0].remark.focus();
		 return false;
		}
	}
	var reqId = document.getElementById("reqId").value;;
	var reqType = document.getElementById("reqType").value;
	var url="approvals.do?method=statusChangeOT&reqId="+reqId+"&reqType="+reqType+"&status="+elemValue;

	document.forms[0].action=url;
	document.forms[0].submit();
}
</script>
</head>


<body>
<html:form action="/approvals.do" enctype="multipart/form-data">
<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="approvalsForm" property="message">
				<font color="green" size="3"><b><bean:write name="approvalsForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="approvalsForm" property="message2">
				<font color="red" size="3"><b><bean:write name="approvalsForm" property="message2" /></b></font>
				
			</logic:present>
		</div>


		
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left"><center>OT Approval Form</center></th>
			</tr>
			<tr><th colspan="4">Requester Details</th></tr>
						<tr><td>Employee Number</td><td><bean:write name="approvalsForm" property="employeeno" /></td><td>Employee Name</td><td><bean:write name="approvalsForm" property="employeeName" /></td></tr>
						<tr><td>Department</td><td><bean:write name="approvalsForm" property="department" /></td><td>Designation</td><td><bean:write name="approvalsForm" property="designation" /></td></tr>
						<tr><td>Date of Joining</td><td colspan=""><bean:write name="approvalsForm" property="dateofBirth" /></td><td>Location</td><td colspan=""><bean:write name="approvalsForm" property="locationId" /></td></tr>
	<tr><th colspan="4">Other Details</th></tr>
		<tr>
			<td width="15%">Worked Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%" colspan="3">
						<b><bean:write name="approvalsForm" property="startDate"/></b>
							</td>
			<%-- <td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							<bean:write name="approvalsForm" property="endDate"/>
							</td>				 --%>
							
			</tr>
					<tr>
				<th colspan="4"> Detailed Reason<font color="red" size="2"> * </font>  
						</th>
						</tr>
			
			<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="100" rows="10" style="height: 55px;color:black; width: 874px" disabled="true" ></html:textarea>
						
							</td>
						</tr>
			
		</table>
	
		
		<table  class="bordered"  width="90%" align="left" id="compTable" >
<tr>
            <th width="50px" >SL.no</th>
            <th width="125px" >Employee No.</th>
            <th width="125px" >Name</th>
            <th width="85px" >Department</th>
            <th width="125px" >Designation</th>
            <th width="125px" >No. Of Hours</th>
          
     
            
            
            

        </tr>
       
        <logic:iterate id="a" name="OT">
        
       <tr>
       
       <td><bean:write name="a" property="slmindur"/></td>       
       <td><bean:write name="a" property="employeeNumber"/> </td>       
       <td><bean:write name="a" property="employeeName"/></td>       
       <td><bean:write name="a" property="department"/></td>
       <td><bean:write name="a" property="designation"/></td>
       <td><bean:write name="a" property="nofhrs"/></td>

       </tr>
       </logic:iterate>
       <tr><th colspan="7">Comments </th>
			</tr>
			<tr>
			<td colspan="7"><html:text property="remark" styleClass="text_field" style="width:100%" styleId="remarkContent"></html:text></td></tr>
       </table>
	<DIV>&nbsp;</DIV>
	
	
	
	<table>
	<tr><td colspan="6" style="border:0px; text-align: center;">
			
			&nbsp;&nbsp;
		   <logic:notEmpty name="approveButton">
			<input type="button" class="rounded" value="Approve" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="rejectButton">
			<input type="button" class="rounded" value="Reject" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="completedButton">
			&nbsp;&nbsp;
			</logic:notEmpty>
			<logic:notEmpty name="Cancel">
			<input type="button" class="rounded" value="Cancel" onclick="changeStatus(this)" />&nbsp;&nbsp;
			</logic:notEmpty>
			
			<input type="button" class="rounded" value="Close" onclick="getCurrentRecord()"  /></td>
			
			</tr></table>
				 <logic:notEmpty name="appList">
	<div>&nbsp;</div>
    <table class="bordered">
    <tr>
    <th colspan="5"><center>Approval Status</center></th>
    </tr>
    <tr><th>Approver Name</th><th>Designation</th><th>Status</th><th>Date</th><th>Comments</th></tr> 
   	<logic:iterate id="abc" name="appList">
	<tr>
	<td>${abc.approver }</td>
	<td>${abc.designation }</td>
	<td>${abc.approveStatus }</td>
	<td>${abc.approveDate }</td>
	<td>${abc.comments }</td>
	
	</tr>
	</logic:iterate>
	</table>
	</logic:notEmpty>
	

	<input style="visibility:hidden;" id="scnt" value="<bean:write property="startAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="ecnt" value="<bean:write property="endAppCount"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="reqId" value="<bean:write name="approvalsForm" property="requestNo"/>"/>
					<input style="visibility:hidden;" id="reqType" value="<bean:write name="approvalsForm" property="requestType"/>"/>
					<input style="visibility:hidden;" id="sValue" value="<bean:write property="searchText"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="totalReco" value="<bean:write property="totalRecords"  name="approvalsForm"/>"/>
					<input style="visibility:hidden;" id="filterby" value="<bean:write property="selectedFilter"  name="approvalsForm"/>"/>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	<html:hidden property="selectedFilter"/>
	<html:hidden property="reqRequstType"/>
	<html:hidden property="requestNo"/>
		
	
		</html:form>
</body>		

</html>