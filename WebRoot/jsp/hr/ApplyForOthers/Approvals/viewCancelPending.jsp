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
   <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
   <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
      <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <script type="text/javascript" src="js/sorttable.js"></script>
 <script type="text/javascript">
 
 function showSelectedFilter(){
 if(document.getElementById("requestSelectId").value=="")
 {
 
 alert("Please Select RequestType");
 document.forms[0].reqRequstType.focus();
      return false;
 }
 
 if(document.getElementById("filterId").value=="")
 {
 
 alert("Please Select filter");
 document.forms[0].selectedFilter.focus();
      return false;
 }
 
 
 var url="hrApprove.do?method=pendingcancelRecords";
	document.forms[0].action=url;
	document.forms[0].submit();
}
 
 function closerecord(){



	var url="hrApprove.do?method=displayAllPending";
	document.forms[0].action=url;
	document.forms[0].submit();

	
	
	
}

function nextRecord(){
var url="hrApprove.do?method=nextRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function previousRecord(){
var url="hrApprove.do?method=previousRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}

function lastRecord(){
var url="hrApprove.do?method=lastRecord";
		document.forms[0].action=url;
		document.forms[0].submit();

}
function firstRecord(){
var url="hrApprove.do?method=pendingRecords";
		document.forms[0].action=url;
		document.forms[0].submit();
}

function approve(){


var rows=document.getElementsByName("selectedRequestNo");
var checkvalues='';
var uncheckvalues='';
for(var i=0;i<rows.length;i++)
{
if (rows[i].checked)
{
checkvalues+=rows[i].value+',';
}else{
uncheckvalues+=rows[i].value+',';
}
}
if(checkvalues=='')
{
alert('please select atleast one record');
}
else
{

var url="hrApprove.do?method=approveRequest";
	document.forms[0].action=url;
	document.forms[0].submit();
		}
}

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
        if(reqFieldName=="employeeNo"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }
     xmlhttp.open("POST","hrApprove.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}

function selectUser(input,reqFieldName){
var res = input.split("-");
	document.getElementById(reqFieldName).value=res[1];
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="employeeNo"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
 
 </script>
  </head>
  
    <body>
  <html:form action="/hrApprove.do" enctype="multipart/form-data" onsubmit="showSelectedFilter();return false;">
  <div align="center">
				<logic:present name="hrApprovalForm" property="message">
					<font color="Green" size="3"><b><bean:write name="hrApprovalForm" property="message" /></b></font>
				</logic:present>
			
			</div>
			<table class="bordered ">
			<tr>
<th><b>Request Type : <font color="red">*</font></b>
</th><td>	<html:select property="reqRequstType" styleClass="Content"  styleId="requestSelectId"  >
	<html:option value="">Select</html:option>

		<html:option value="Cancel Leave">Leave</html:option>	
		<html:option value="Cancel On Duty">On Duty</html:option>
		<html:option value="Cancel Permission">Permission</html:option>
		<html:option value="Cancel Comp-Off">Comp-Off</html:option>
		<html:option value="Cancel OT">OT</html:option>

	</html:select>
	</td>
	<th><b>Filter by</b> <font color="red">*</font></th>
						<td>
						<html:select property="selectedFilter" styleClass="content" styleId="filterId"  >
						
								<html:option value="">Select</html:option>
							<html:option value="CPending">Pending</html:option>
							<html:option value="Cancelled">Cancelled</html:option>
				         	<html:option value="Rejected">Rejected</html:option>
							</html:select>
						</td>
						<td><img src="images/search.png" width="26" height="24" onclick="showSelectedFilter()" /></td>
						</tr>
						
  </table>
  <br/>
</br>

<center>

<logic:notEmpty name="displayRecordNo">

	  	<td>
	  	<img src="images/First10.jpg" onclick="firstRecord()" align="absmiddle"/>
	
	
	
	<logic:notEmpty name="disablePreviousButton">
	
	
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	
	
	</logic:notEmpty>
	  	
	
	<logic:notEmpty name="previousButton">
	
	
	<img src="images/previous1.jpg" onclick="previousRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	

	<bean:write property="startRecord"  name="hrApprovalForm"/>-
	
	<bean:write property="endRecord"  name="hrApprovalForm"/>
	
	<logic:notEmpty name="nextButton">
	
	<img src="images/Next1.jpg" onclick="nextRecord()" align="absmiddle"/>
	
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">

	
	<img src="images/disableRight.jpg" align="absmiddle" />
	
	
	</logic:notEmpty>
		
		<img src="images/Last10.jpg" onclick="lastRecord()" align="absmiddle"/>
	</td>

	</table>
	</logic:notEmpty>
	</center>
	<html:hidden property="totalRecords"/>
	<html:hidden property="startRecord"/>
	<html:hidden property="endRecord"/>
	
<br/>
  <logic:notEmpty name="OTlist">	

<logic:equal value="Pending" property="selectedFilter" name="hrApprovalForm">
 &nbsp;
</logic:equal>
<br/>
</br>

		<table class="bordered sortable">
			<tr>
		<th>Req&nbsp;No</th><th style="width:200px;">Employee Name</th><th>Requested On</th>
					<th style="width:100px;">Worked Date</th>
				<th style="width:100px;">Status</th><th style="width:100px;">View</th>
				</tr>
					
<logic:iterate id="leave" name="OTlist">

<tr>


<td><bean:write name="leave" property="requestNumber"/></td>

<td><bean:write name="leave" property="employeeName"/></td>
<td><bean:write name="leave" property="submitDate"/></td>
<td><bean:write name="leave" property="startDate"/></td>



<td><bean:write name="leave" property="status"/></td>
<td><a  href="hrApprove.do?method=selectOTRequest&requstNo=<bean:write name="leave" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a></td>

</tr>

</logic:iterate>
</table>
</logic:notEmpty>
  <logic:notEmpty name="complist">	

<logic:equal value="Pending" property="selectedFilter" name="hrApprovalForm">
 &nbsp;
</logic:equal>
<br/>
</br>

		<table class="bordered sortable">
			<tr>
		<th>Req&nbsp;No</th><th style="width:200px;">Employee Name</th><th>Requested On</th>
					<th style="width:100px;">Worked Date</th>
				<th style="width:100px;">Status</th><th style="width:100px;">View</th>
				</tr>
					
<logic:iterate id="leave" name="complist">

<tr>


<td><bean:write name="leave" property="requestNumber"/></td>

<td><bean:write name="leave" property="employeeName"/></td>
<td><bean:write name="leave" property="submitDate"/></td>
<td><bean:write name="leave" property="startDate"/></td>



<td><bean:write name="leave" property="status"/></td>
<td><a  href="hrApprove.do?method=selectCompoffRequest&requstNo=<bean:write name="leave" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a></td>

</tr>

</logic:iterate>
</table>
</logic:notEmpty>
  <logic:notEmpty name="leaveList">	

<logic:equal value="Pending" property="selectedFilter" name="hrApprovalForm">
 &nbsp;
</logic:equal>
<br/>
</br>

		<table class="bordered sortable">
			<tr>
		<th>Req&nbsp;No</th><th style="width:100px;">Leave Type</th><th style="width:200px;">Employee Name</th><th>Requested On</th>
					<th style="width:100px;">Start Date</th><th style="width:100px;">Duration</th><th style="width:100px;">End Date</th><th style="width:100px;">Duration</th>
					<th style="width:100px;">No Of Days</th><th style="width:100px;">Status</th><th style="width:100px;">View</th>
				</tr>
					
<logic:iterate id="leave" name="leaveList">

<tr>


<td><bean:write name="leave" property="requestNumber"/></td>
<td><bean:write name="leave" property="leaveType"/></td>
<td><bean:write name="leave" property="employeeName"/></td>
<td><bean:write name="leave" property="submitDate"/></td>
<td><bean:write name="leave" property="startDate"/></td>
<td><bean:write name="leave" property="startDurationType"/></td>
<td><bean:write name="leave" property="endDate"/></td>
<td><bean:write name="leave" property="endDurationType"/></td>
<td><bean:write name="leave" property="noOfDays"/></td>
<td><bean:write name="leave" property="approveStatus"/></td>
<td><a  href="hrApprove.do?method=selectRequest&requstNo=<bean:write name="leave" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a></td>

</tr>

</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="ondutyList">

<logic:equal value="Pending" property="selectedFilter" name="hrApprovalForm">
<html:button  property="method" value="Approve" styleClass="rounded" onclick="approve()" style="width:100px;" ></html:button> &nbsp;
</logic:equal>
<br/>
</br>
<table class="sortable bordered">
<tr > <th colspan="13"><center>Onduty List</center></th></tr>
<tr >
<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Requested On</th><th style="width:100px;">Status</th><th style="width:100px;">View</th>
</tr>
<logic:iterate id="onDuty" name="ondutyList">
<tr>


<td><bean:write name="onDuty" property="requestNumber"/></td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="onDutyType"/></td>

<td><bean:write name="onDuty" property="locationId"/></td>
<td><bean:write name="onDuty" property="startDate"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endDate"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="submitDate"/></td>
<td><bean:write name="onDuty" property="approver"/></td>
<td><a  href="hrApprove.do?method=selectondutyRequest&requstNo=<bean:write name="onDuty" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a></td>
</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="Permissionlist">
<logic:equal value="Pending" property="selectedFilter" name="hrApprovalForm">
<html:button  property="method" value="Approve" styleClass="rounded" onclick="approve()" style="width:100px;" ></html:button> &nbsp;
</logic:equal>
<br/>
</br>
<table class="sortable bordered">
<tr>
<th>Req&nbsp;No</th><th style="width:100px;"> Type</th><th style="width:200px;">Employee Full Name</th>
	<th style="width:100px;">Permission Date</th><th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th><th>Requested On</th>
	<th style="width:100px;">Status</th><th>View</th>
</tr>
<logic:iterate id="onDuty" name="Permissionlist" >
<tr>


<td><bean:write name="onDuty" property="requestNumber"/></td>
<td>Permission</td>
<td><bean:write name="onDuty" property="employeeName"/></td>
<td><bean:write name="onDuty" property="date"/></td>
<td><bean:write name="onDuty" property="startTime"/></td>
<td><bean:write name="onDuty" property="endTime"/></td>
<td><bean:write name="onDuty" property="reqdate"/></td>
<td><bean:write name="onDuty" property="approver"/></td>
<td><a  href="hrApprove.do?method=selectpermissionRequest&requstNo=<bean:write name="onDuty" property="requestNumber"/>" ><img src="images/view.gif" width="28" height="28"/></a></td>

</tr>
</logic:iterate>
</table>
</logic:notEmpty>

<logic:notEmpty name="no Leave records">
<br/>
</br>
<table class="sortable bordered">
			<tr>
				<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;">Leave Type</th><th style="width:200px;">Employee Name</th><th>Requested On</th>
					<th style="width:100px;">Start Date</th><th style="width:100px;">Duration</th><th style="width:100px;">End Date</th><th style="width:100px;">Duration</th>
					<th style="width:100px;">No Of Days</th><th style="width:100px;">Status</th>
				</tr>
				<tr > <td colspan="13"><center><font color="red">Search details not found..</font></center></td></tr>

</table>
</logic:notEmpty>
<logic:notEmpty name="noOT">
<table class="sortable bordered">
		<tr>
		<th>Req&nbsp;No</th><th style="width:200px;">Employee Name</th><th>Requested On</th>
					<th style="width:100px;">Worked Date</th>
				<th style="width:100px;">Status</th><th style="width:100px;">View</th>
				</tr>
		<tr>
<td colspan="8">
<font color="red" size="3"><center>No Records </center></font>
</td>
</tr>
		</table>
		</logic:notEmpty>
	<logic:notEmpty name="nocomp">
<table class="sortable bordered">
		<tr>
		<th>Req&nbsp;No</th><th style="width:200px;">Employee Name</th><th>Requested On</th>
					<th style="width:100px;">Worked Date</th>
				<th style="width:100px;">Status</th><th style="width:100px;">View</th>
				</tr>
		<tr>
<td colspan="8">
<font color="red" size="3"><center>No Records </center></font>
</td>
</tr>
		</table>
		</logic:notEmpty>
<logic:notEmpty name="no OnDuty records">
<br/>
</br>
<table class="sortable bordered">
<tr >
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:200px;">Requested By</th><th style="width:100px;">OnDuty&nbsp;Type&nbsp;</th><th style="width:100px;">Plant</th>
	<th style="width:100px;">Start Date</th><th style="width:100px;">From&nbsp;Time</th><th style="width:100px;">End Date</th><th style="width:100px;">To Time</th>
	<th style="width:100px;">Requested On</th><th style="width:100px;">Status</th>
</tr>
				<tr > <td colspan="13"><center><font color="red">Search details not found..</font></center></td></tr>
</table>
</logic:notEmpty>

<logic:notEmpty name="no Permission records">
<br/>
</br>
<table class="sortable bordered">
<tr > <th colspan="13"><center>Permission List</center></th></tr>
<tr>
<th>Select</th>	<th>Req&nbsp;No</th><th style="width:100px;"> Type</th><th style="width:200px;">Employee Full Name</th>
	<th style="width:100px;">Permission Date</th><th style="width:100px;">Start Time</th><th style="width:100px;">End Time</th><th>Requested On</th>
	<th style="width:100px;">Status</th>
</tr>
				<tr > <td colspan="13"><center><font color="red">Search details not found..</font></center></td></tr>
</table>
</logic:notEmpty>

  <html:hidden property="reqRequstType"/>
  
  </html:form>
  </body>
</html>
