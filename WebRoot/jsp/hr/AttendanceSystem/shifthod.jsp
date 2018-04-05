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


<!--<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link href="style/content.css" rel="stylesheet" type="text/css" />
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
 Theme css 
<link href="calender/themes/aqua.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="css/jqueryslidemenu.css" />



--><script type="text/javascript" src="js/sorttable.js"></script>
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/validate.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
      <link rel="stylesheet" type="text/css" href="css/jquery.dataTables.min.css" />
    <script src="js/sumo/jquery.sumoselect.js"></script>
    <link href="js/sumo/sumoselect.css" rel="stylesheet" />


<script type="text/javascript">
	
</script>


<title>Home Page</title>
<link type="text/css" href="css/jquery.datepick.css" rel="stylesheet"/>
<script type="text/javascript" src="js/jquery.datepick.js"></script>
<script language="javascript">

$(document).ready(function() {
    $('#example').DataTable( {
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
} );




$(function() {

	$('#popupDatepicker').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker').datepick({onSelect: showDate});
	
	
});

$(function() {
	$('#popupDatepicker2').datepick({dateFormat: 'dd/mm/yyyy'});
	$('#inlineDatepicker2').datepick({onSelect: showDate});
});


function applyLeave()
{
	document.forms[0].action="leave.do?method=applyLeave";
	document.forms[0].submit();
}


function parseDate(str) {
    var mdy = str.split('/');
    return new Date(mdy[2], mdy[1]-1, mdy[0]);
}


function daydiff(first, second) {

//daydiff

var totaldays=(second-first)/(1000*60*60*24);

if(totaldays<0)
{
alert("start date should be less than end date");
    return "";
}
else{

    return ((second-first)/(1000*60*60*24)+1)
    }
}


function uploadDocument()
{


  if(document.forms[0].documentFile.value=="")
	    {
	      alert("Please Select File.");
	      document.forms[0].documentFile.focus();
	      return false;
	    }
	document.forms[0].action="leave.do?method=uploadDocuments";
	document.forms[0].submit();
}


function deleteDocumentsSelected()
{
	document.forms[0].action="leave.do?method=deleteDocuments";
	document.forms[0].submit();
}


function displayTabs(param)
{
	document.forms[0].action="leave.do?method=displayTabs&param="+param;
	document.forms[0].submit();
}
function applyLeave(param)
{

if(param=='Applied')
{
       if(document.forms[0].leaveType.value=="")
	    {
	      alert("Leave Type should not be left blank");
	      document.forms[0].leaveType.focus();
	      return false;
	    }
	   else if(document.forms[0].startDurationType.value=="")
	    {
	      alert("Holiday Type should not be left blank");
	      document.forms[0].startDurationType.focus();
	      return false;
	    }
	     else if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	     else if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
	     else if(document.forms[0].noOfDays.value=="")
	    {
	      alert("Please Select No Of Days.It should not be left blank");
	      document.forms[0].noOfDays.focus();
	      return false;
	    }
	    
	    
 document.forms[0].action="leave.do?method=submit&param="+param;
document.forms[0].submit();
}
else if(param=='Draft')
{
if(document.forms[0].startDate.value=="")
	    {
	      alert("Start Date should not be left blank");
	      document.forms[0].startDate.focus();
	      return false;
	    }
	    else  if(document.forms[0].endDate.value=="")
	    {
	      alert("End Date should not be left blank");
	      document.forms[0].endDate.focus();
	      return false;
	    }
else if((document.forms[0].leaveType.value!="")||(document.forms[0].startDurationType.value!="")||(document.forms[0].startDate.value!="")||(document.forms[0].endDate.value!=""))
{

	document.forms[0].action="leave.do?method=submit&param="+param;
	document.forms[0].submit();
}

}
}
function closeLeave()
{
	document.forms[0].action="leave.do?method=display&sId=ApplyLeave&id=ESS";
	document.forms[0].submit();
}
function displayRequests()
{
	document.forms[0].action="leave.do?method=displayRequests";
	document.forms[0].submit();
}
function submitRequest(param)
{
document.forms[0].action="leave.do?method=submitRequests&param="+param;
document.forms[0].submit();
}

function cancelRequest()
{
document.forms[0].action="leave.do?method=cancelRequest";
document.forms[0].submit();
}
function deleteDraft()
{
document.forms[0].action="leave.do?method=deleteDraft";
document.forms[0].submit();
}
function calculateEndDate()
{

document.forms[0].noOfDays.value=daydiff(parseDate(document.forms[0].startDate.value), parseDate(document.forms[0].endDate.value));

var endDate=document.forms[0].noOfDays.value;
if(endDate=="")
{
document.forms[0].endDate.value="";
}
}
		
function nextRecord()
{
var url="leave.do?method=nextMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function previousRecord()
{

var url="leave.do?method=previousMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function firstRecord()
{

var url="leave.do?method=firstMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}

function lastRecord()
{

var url="leave.do?method=lastMyRequestRecord";
			document.forms[0].action=url;
			document.forms[0].submit();

}
function cancelLeave(reqno)
{


var url="leave.do?method=cancelRecord&reqno="+reqno;
			document.forms[0].action=url;
			document.forms[0].submit();

}

function dynamicleavbal()
{
var year=document.forms[0].year.value;

var url="leave.do?method=displayRequests&year="+year;
			document.forms[0].action=url;
			document.forms[0].submit();
}
   
 function updateData(emp)
    {
    
		var x=window.showModalDialog("hrApprove.do?method=Updateemployeeshift&emp="+emp,null, "dialogWidth=500px;dialogHeight=220px; center:yes");

 /* alert("1");
    	var per=document.forms[0].employeeNo.value;
    	 alert(per);
    	var shift=document.forms[0].shift1.value;
  alert(shift);
    	var e=document.forms[0].popupDatepicker.value;

    	 */
    
	
		var url="hrApprove.do?method=Modifyshiftdetails&currentDate="+e+"&pernr="+per+"&shift="+shift;
		
		
					document.forms[0].action=url;
					document.forms[0].submit();
		
		
		
		}
   
   
</script>
</head>

<body >
<html:form action="hrApprove" enctype="multipart/form-data">
<div class="middel-blocks">
     		<div align="center">
			
				<logic:present name="calenderForm" property="message2">
					<font color="Green" size="3"><b><bean:write name="calenderForm" property="message2" /></b></font>
				</logic:present>
			</div>
<tr><td width="50%" height="300" valign="top" style="border: 1px solid #4297d7;">
					<div>
	<table class="bordered" width="90%">
		      	<tr>
					<th colspan="4" style="text-align: center;"><big>Employee Shift Assignment</big></th>
										</tr>
 <logic:notEmpty name="noRecords">
 	<div align="left" class="bordered">
 <table class="bordered">
			
			<tr>			<th style="text-align:left;"><b>Employee_no</b></th>
							<th style="text-align:left;"><b>Employee_Name</b></th>
							<th style="text-align:left;"><b>Designation</b></th>	
							<th style="text-align:left;"><b>Department</b></th>
							
							<th style="text-align:left;"><b>Update</b></th></tr>
						</table>
						</div>
	<div align="center">
	
		<logic:present name="sform" property="message">
			<font color="red">
				<bean:write name="sform" property="message" />
			</font>
		</logic:present>
		<br />
		<br />.
		</logic:notEmpty>
	</div>		
		

		<table  align="center" style="width:150px;">
	 		<logic:notEmpty name="displayRecordNo">
	 		<tr><td>
	  			<img src="images/First10.jpg" onclick="firstRecord()"/>
	  			</td>
	  			<logic:notEmpty name="disablePreviousButton">
	  			<td>
	  			<img src="images/disableLeft.jpg" />
	  			</td>
	  			</logic:notEmpty>
	  			<logic:notEmpty name="previousButton">
	  			<td>
	  			<img src="images/previous1.jpg" onclick="previousRecord()"/>
	  			</td>
	  			</logic:notEmpty>
	  			<td>
	  			<bean:write property="startRecord"  name="sform"/>-
	  			</td>
	  			<td><bean:write property="endRecord"  name="sform"/>
	  			</td>
	  			<logic:notEmpty name="nextButton">
	  			<td>
	  			<img src="images/Next1.jpg" onclick="nextRecord()"/>
	  			</td>
	  			</logic:notEmpty>
	  			<logic:notEmpty name="disableNextButton">
	  			<td>
	  			<img src="images/disableRight.jpg" />
	  			</td>
	  			</logic:notEmpty>
	  			<td>
	  			<img src="images/Last10.jpg" onclick="lastRecord()"/>
	  			</td>
	  			<html:hidden property="totalRecords"/>
	  			<html:hidden property="startRecord"/>
	  			<html:hidden property="endRecord"/>
	  			<html:hidden property="next"/>
	  			<html:hidden property="prev"/>
	  		</tr>
	  		</logic:notEmpty>
	  	</table>		
				
		<logic:notEmpty name="shiftEmpDetails">
		<div align="left" class="bordered">
			<table id="example" class="sortable">
			
			<tr>
							<th style="text-align:left;"><b>Employee_No</b></th>
							<th style="text-align:left;"><b>Employee_Name</b></th>
							<th style="text-align:left;"><b>Designation</b></th>	
							<th style="text-align:left;"><b>Department</b></th>
							
							<th style="text-align:left;"><b>Update</b></th>
								<th style="text-align:left;"><b>View</b></th>
				            </tr>
						<%
							int count = 1;
										
						%>
				
			
				<logic:iterate id="mytable1" name="shiftEmpDetails">
				
				
				<tr class="tableOddTR">
				<td>
                     <bean:write name="mytable1" property="employeeNo"/>
                     <html:hidden property="employeeNo" name="mytable1" value="${mytable1.employeeNo}"/>
					</td>
					<td>
						<bean:write name="mytable1" property="employeeName"/>
						 <html:hidden property="employeeName" name="mytable1" />
					</td>
					<td>
						<bean:write name="mytable1" property="designation"/>
					</td>
					<td>
					<bean:write name="mytable1" property="department"/>
					</td>
					<%-- <td>
						<html:text property="curentDate" name="mytable1" styleId="popupDatepicker" style="width: 98px; "/></td>
						
					<td>
							<html:select property="shift" styleId="shift1">
							<html:options name="mytable1"  property="shiftList" labelProperty="shiftLabelList"/>
								 <html:hidden property="shift" name="mytable1" value="${mytable1.shift}"/>
							</html:select>
					</td> --%>
					
					<td><html:button property="method" value="Update" onclick="updateData('${mytable1.employeeNo}')" styleClass="rounded"/>&nbsp;</td>	
				<td>
					
					<a  href="hrApprove.do?method=shiftviewSearch&employeeNo=${mytable1.employeeNo}"><img   src="images/view.gif" width="28" height="28"/></a>
			
					</td>
				</logic:iterate>
				</logic:notEmpty>
		</table>
		</div>
		
	</html:form>
</body>
</html>