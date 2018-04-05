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
     
    var inp2 = new_row.cells[1].getElementsByClassName('abc')[0];
    
    inp2.id = 'employeeNumber'+len;
    inp2.name = 'employeeNumber';
    inp2.value = '';
    
    var imge = new_row.cells[1].getElementsByTagName('img')[0];
    imge.id = 'employeeNumber'+len;
   
   
    
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
     var inp6 = new_row.cells[5].getElementsByTagName('input')[0];
    inp6.id = 'doj'+len;
    inp6.name = 'doj';   
     inp6.value = '';
      var inp7 = new_row.cells[6].getElementsByTagName('select')[0];
    inp7.id  = 'nofhrs'+len;
    inp7.name = 'nofhrs';
    inp7.value = '';
   /*   var inp8 = new_row.cells[7].getElementsByTagName('select')[0];
    inp8.id = 'appl'+len;
    inp8.name = 'appl';
    inp8.value = ''; */
    
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

function savdraft()
{

	if(document.forms[0].startDate.value==""){
alert("Please Select Start Date");
document.forms[0].startDate.focus();
return false;
}
 /* if(document.forms[0].endDate.value==""){
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
   } */

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
   
   
    var url="leave.do?method=savecompDraft";
	document.forms[0].action=url;
	document.forms[0].submit();

}


function applyComoff(param)
{
	if(document.forms[0].startDate.value==""){
alert("Please Select Start Date");
document.forms[0].startDate.focus();
return false;
}
/*  if(document.forms[0].endDate.value==""){
alert("Please Select End Date");
document.forms[0].endDate.focus();
return false;
} */
/* 
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
var date1 = new Date(yr1, mon1-1, dt1); 
var date2 = new Date(yr2, mon2-1, dt2); 


if(date2 < date1) 
{ 
    alert("Please Select Valid Date Range");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}

if(date2 > date1) 
{ 
    alert("To date should be equal to From Date");
    document.forms[0].endDate.value="";
     document.forms[0].endDate.focus();
     return false;
}


   }
 */
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
	  var emp = new_row.cells[1].getElementsByClassName('abc')[0];
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
    
    var hrs = new_row.cells[6].getElementsByTagName('select')[0];
    var hrsId=hrs.id;
    var sel = document.getElementById(hrsId);
    var selecVal = sel.options[sel.selectedIndex].value;

    if(selecVal == ""){
		 alert("Please Select No of Hours");
		 document.getElementById(hrsId).focus();
	  return false;	  
		    }
		    
		
   	}
	
  
  
  }
  
  var url="hrLeave.do?method=submitnewOT&param="+param;
					document.forms[0].action=url;
					document.forms[0].submit();
  
  }

  



function searchEmployeeId(filed)
	{
	  
		var reqFiled=filed;
	
		//var x=window.open("leave.do?method=displayListUsers&reqFiled="+filed,"SearchSID","width=1100,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
	var x=window.showModalDialog("leave.do?method=displayListUsers&reqFiled="+filed ,window.location, "dialogWidth=850px;dialogHeight=620px; center:yes");
	}
	
	function closeLeave()
	{
	document.forms[0].action="hrLeave.do?method=search";
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
	document.getElementById("empname"+lastChar).value=res[0];
	document.getElementById("dept"+lastChar).value=res[2];	
	
	if(res[4].contains('/'))
	{	
	document.getElementById("desg"+lastChar).value=res[3];
	document.getElementById("doj"+lastChar).value=res[4];
	}
	else
	{
	document.getElementById("desg"+lastChar).value=res[3]+"-"+res[4];
	document.getElementById("doj"+lastChar).value=res[5];
	}
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
 
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		
	}
	}


</script>
</head>


<body>
<html:form action="hrLeave" enctype="multipart/form-data">
<div align="center" id="messageID" style="visibility: visible;">
			<logic:present name="hrLeaveForm" property="message">
				<font color="green" size="3"><b><bean:write name="hrLeaveForm" property="message" /></b></font>
				
			</logic:present>
			<logic:present name="hrLeaveForm" property="message2">
				<font color="red" size="3"><b><bean:write name="hrLeaveForm" property="message2" /></b></font>
				
			</logic:present>
		</div>
<html:hidden property="empNumber"/>

		
		<table class="bordered" width="90%">
			<tr>
				<th colspan="5" align="left"><center>Apply OT</center></th>
			</tr>
		<tr><td>Employee Number</td><td colspan="3"><bean:write name="hrLeaveForm" property="empName"/></td></tr>
		<tr>
			<td width="15%">Worked Date <font color="red" size="3">*</font></td> 
							
							<td align="left" width="34%">
								<html:text property="startDate" styleId="popupDatepicker" styleClass="text_field" readonly="true" />
							</td>
			<%-- <td width="15%">To Date <font color="red" size="3">*</font></td>
							
							<td align="left" width="34%">
							<html:text property="endDate" styleId="popupDatepicker2" styleClass="text_field" readonly="true" />
							</td> --%>				
							
			</tr>
					<tr>
				<th colspan="4"> Detailed Reason<font color="red" size="2"> * </font> Max(300 char) : 
						</th>
						</tr>
			
			<tr>	
						
							<td colspan="6" class="lft style1">
							<html:textarea property="reason" cols="110" rows="10" style="height: 119px; "></html:textarea>
						
							</td>
						</tr>
			
		</table><br/>
		<table>
		<tr>
		<td></td>
		</tr>
		
		</table><br/>
		
		<table  class="bordered"  width="150%" align="left" id="compTable" >
<tr>
            <th width="50px" >SL.no</th>
            <th width="200px" >Employee No.</th>
            <th width="100px" >Name</th>
            <th width="85px" >Department</th>
            <th width="125px" >Designation</th>
            <th width="85px" >DOJ</th>
            <th width="125px" >No. Of Hours</th>
            <!-- <th width="125px" >Applicable</th> -->
            <td width="20px;"><a href="#" ><img src="images/add-items.gif" id="addmorePOIbutton" onclick="insRow()" title="Add Row" /></a></td>
<td><a href="#" ><img src="images/delete.png"   onclick="deleteRow()" title="Remove Row"/></a></td>
       
     
            
            
            

        </tr>
       
        
        
       <tr>
       
       <td><input type="text" id="slno1"  name="slno" style="width: 25px;" value="1" disabled="disabled"/></td>
       
       <td width="200px"><html:text property="employeeNumber"  onkeyup="searchEmployee(this.id)" styleId="employeeNumber1" style="width: 43px; " styleClass="abc" value=""> 
      <bean:write property="employeeNumber" name="hrLeaveForm" /></html:text><a href="#"><img  src="images/search.png" title="search"  align="absmiddle" onclick="searchEmployeeId(this.id)" id="employeeNumber1" style="height: 20px; width: 21px; "/></a>    
       <div id="sU" style="display:none;">
		<div id="sUTD" style="width:400px;">
		<iframe src="jsp/ess/compoff/searchApprovers.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="100%"></iframe>
		</div>
	</div>     </td>
       <td><input type="text" id="empname1"        name="empname"        style="width: 100px;" disabled="disabled"/></td>
       
       <td><input type="text" id="dept1"    name="dept"    style="width: 90px;" disabled="disabled"/></td>
       <td><input type="text" id="desg1"     name="desg"     style="width: 119px;" disabled="disabled"/></td>
       <td><input type="text" id="doj1"     name="doj"     style="width: 65px;" disabled="disabled"/></td>
       <td><select name="nofhrs" id="nofhrs1">
       <option value="">-Select-</option>
       <option value="1">1 Hour</option>
      <option value="2">2 Hours</option>   
      <option value="3">3 Hours</option>
      <option value="4">4 Hours</option>      
      <option value="5">5 Hours</option>
      <option value="6">6 Hours</option>   
      <option value="7">7 Hours</option>
      <option value="8">8 Hours</option>      
      <option value="9">9 Hours</option>
      <option value="10">10 Hours</option>  
       </select></td>
     <!--   <td><select name="appl" id="appl1">
       <option value="">-Select-</option>
      <option value="BS">Before Shift Start Time</option>
      <option value="AS">After Shift End Time</option>       
       </select></td>  -->
       <td colspan="2"><INPUT type="checkbox" name="chk" value=""/></td>
       
  
        
       
       </tr>
       </table>
	<DIV>&nbsp;</DIV>
	<table>
	
	<center>
	<html:button property="method" styleClass="rounded" value="Submit" onclick="applyComoff(this.value);" style="align:right;width:100px;"/> &nbsp;
	<%-- <html:button property="method" styleClass="rounded" value="Save As Draft" onclick="applyComoff(this.value);" style="align:right;width:100px;"/> &nbsp; --%>
	<html:button property="method" styleClass="rounded" value="Close" onclick="closeLeave()" style="align:right;width:100px;"/>
	</center>
	
	</table>
	<DIV>&nbsp;</DIV>
		<logic:notEmpty name="appList">
					
						<table  width="100%"  class="bordered">
						<tr>
						<th colspan="4"><big>Approver Details</big></th></tr>
						<tr>
						
							<th style="text-align:left;"><b>Employee Code</b></th>
							<th style="text-align:left;"><b>Name</b></th>	
							<th style="text-align:left;"><b>Designation</b></th>
						</tr>
						<logic:iterate id="abc" name="appList">
						<tr>
						
					
						<td>${abc.approverID}</td>
						<td>${abc.approverName}</td>
						<td>${abc.appDesig}</td>
						</tr>
						</logic:iterate>
						</table>
					
					</logic:notEmpty><br/>
	<table class="bordered">
	<tr><th colspan="4">Plant Head Details</th></tr>
	<tr>
	<th>Employee No</th><th>Name</th><th>Department</th><th>Designation</th>	
	</tr>
	<tr><td>${hrLeaveForm.employeeNumber}</td><td>${hrLeaveForm.employeeName}</td><td>${hrLeaveForm.department}</td><td>${hrLeaveForm.designation}</td></tr>
	
	</table>
	
		
	
		</html:form>
</body>		

</html>