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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
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









function back()
{

document.forms[0].action="vc.do?method=raiserequestlist";
document.forms[0].submit();

}


function deleteDocumentsSelected()
{


var rows=document.getElementsByName("documentCheck");
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
alert('please select atleast one value to Submit');
return false;
}
else
{
	document.forms[0].action="vc.do?method=displayList";
	document.forms[0].submit();

}
}


function checkAll()
{

var t =document.getElementsByName("documentCheck").length;

		for(var i=0; i <t; i++)
		{
		
			if(document.forms[0].checkProp.checked==true)
			{
			
			   document.getElementsByName("documentCheck")[i].checked = true ;
			}
			else
				document.getElementsByName("documentCheck")[i].checked = false ;
		}


}

function process()
{

	
	document.forms[0].action="vc.do?method=serachraiserequestlist";
	document.forms[0].submit();


}


</script>
<style >
.no
{
pointer-events: none; 
}

</style>

<script >

function displayfreq()
{
	if(document.forms[0].frequency.value=="Daily"||document.forms[0].frequency.value=="No")
	{
		document.getElementById("fre1").style.display ="none";
		document.getElementById("fre2").style.display ="none";
		
	}
	else
	{	
	document.getElementById("fre1").style.display ="";
	document.getElementById("fre2").style.display ="";
	
	}
}	

function submitdata()
{	
	
	
	if(document.forms[0].frequency.value=="Daily"||document.forms[0].frequency.value=="No")
		{
	document.forms[0].subdate.value=document.forms[0].fromDate.value
		}
	if(document.forms[0].documentFile.value=="")
	{
		alert("Please upload file");
		return false;
	}
	
	if(document.forms[0].category.value=="")
	{
		alert("Please Select Category");
		return false;
		
	}
	
	if(document.forms[0].toDate.value=="")
	{
		alert("Please Select Last Date");
		return false;
		
	}
	
	if(document.forms[0].desc.value=="")
	{
		alert("Please Enter Description");
		return false;
		
	}
	
	if(document.forms[0].subdate.value=="")
	{
		alert("Please Frequency Submission Date");
		return false;
		
	}	
	
	
	var rows=document.getElementsByName("documentCheck");
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
	alert('please select atleast one value to Submit');
	return false;
	}
	
	
	var start=$("#popupDatepicker").val();
	var end=$("#popupDatepicker2").val();
	var subend=$("#popupDatepicker3").val();

    var str1=start;
	var str2=end;
	var str3=subend;
		   
		var dt1  = parseInt(str1.substring(0,2),10); 
		var mon1 = parseInt(str1.substring(3,5),10); 
		var yr1  = parseInt(str1.substring(6,10),10); 
		var dt2  = parseInt(str2.substring(0,2),10); 
		var mon2 = parseInt(str2.substring(3,5),10); 
		var yr2  = parseInt(str2.substring(6,10),10); 
		var dt3  = parseInt(str3.substring(0,2),10); 
		var mon3 = parseInt(str3.substring(3,5),10); 
		var yr3  = parseInt(str3.substring(6,10),10); 
		
		
		var date1 = new Date(yr1, mon1-1, dt1); 
		var date2 = new Date(yr2, mon2-1, dt2);
		var date3 = new Date(yr3, mon3-1, dt3);
		
		
		
		if(date1 > date3)
		{
		alert("Please Select Valid Ferquency Submission Date");
		return false;
		
		}
		
		if(date1 > date2)
		{
		alert("Please Select Valid Request End Date");
		return false;
		
		}

	
	
	if(document.forms[0].frequency.value=="No")
	{	
	document.forms[0].action="vc.do?method=submitraiserequest";
	document.forms[0].submit();
	}
	else
	{
	document.forms[0].action="vc.do?method=submitMultiraiserequest";
	document.forms[0].submit();		
	}	
}

function searchdetails()
{
	if(document.forms[0].stateArray.value=="")
	{
		alert("Please Select State");
		return false;
	}
	document.forms[0].action="vc.do?method=raiserequestlist";
	document.forms[0].submit();	
	
	}




function datedisplay(id)
{

$('#'+id).datepick({dateFormat: 'dd/mm/yyyy',minDate:new Date()});
$('#inlineDatepicker').datepick({onSelect: showDate});

	
}


function closew()
{
	document.forms[0].action="vc.do?method=displayraiserequest";
	document.forms[0].submit();	
	
	
	}
	
	
function displayfield()
{
	displayfreq();

	
	if(document.forms[0].group_type.value=="Field Staff")
		{
		
		document.getElementById("locationId").value ="ML00";
		document.getElementById("tab1").style.display="none";
			document.getElementById("tab2").style.display="";
		document.getElementById("tab3").style.display="";
		
		
		}
	else
		{
		
		
		document.getElementById("locationId").value ="ML00";
		document.getElementById("tab1").style.display="";
		document.getElementById("tab2").style.display="none";
		document.getElementById("tab3").style.display="none";
	
		
		}
	
	
	}

</script>


</head>

<body onload="displayfield()">


<html:form action="vc" enctype="multipart/form-data">

<div align="center">
<logic:notEmpty name="vcForm" property="message">

<script language="javascript">
statusMessage('<bean:write name="vcForm" property="message" />');
</script>
</logic:notEmpty>
<logic:notEmpty name="vcForm" property="message2">

<script language="javascript">
statusMessage('<bean:write name="vcForm" property="message" />');
</script>
</logic:notEmpty>
</div>

<div style="width: 90%" >
	<table class="bordered" >
		<tr><th colspan="5">Data Collection</th></tr>
		<tr>
		<th>Group</th>
		<td >
		
		<html:select  property="group_type" name="vcForm" onchange="console.log($(this).children(':selected').length);displayfield()" styleClass="testselect1" styleId="group_type">
			<html:option value="Field Staff">Field Staff</html:option>
			<html:option value="Corporate staff">Corporate staff</html:option>
			</html:select>
				
		
		
		</td>
		
		<td>Location: <font color="red">*</font></td>
			<td>
				<html:select  property="locationId" name="vcForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1" styleId="locationId">
					<html:options name="vcForm"  property="locationIdList" labelProperty="locationLabelList"/>
				</html:select>
			</td>
		</tr>
		
		<tr id="tab1">
			
			
			<td >Designation: </td>
			<td >
				<html:select  styleId="desginationArray" property="desginationArray"  name="vcForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" style="desginationArray">
					<html:options name="vcForm"  property="desginationList" labelProperty="desginationLabelList"/>
				</html:select>&nbsp;
				
			</td>
			
			
			<td >Department: </td>
			<td >
				<html:select  styleId="deptArray" property="deptArray"  name="vcForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" style="deptArray">
					<html:options name="vcForm"  property="deptList" labelProperty="deptLabelList"/>
				</html:select>&nbsp;
			</td>
			
			</tr>
			<tr id="tab2">
			<td>Division: <font color="red">*</font></td>
			<td>
				<html:select  property="divisionid" name="vcForm" onchange="console.log($(this).children(':selected').length)" styleClass="testselect1"  styleId="divisionid">
					<html:options name="vcForm"  property="divList" labelProperty="divLabelList"/>
				</html:select>
			</td>
		
			<td>State: </td>
			<td>
				<html:select  styleId="stateArray" property="stateArray"  name="vcForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll">
					<html:options name="vcForm"  property="stateList" labelProperty="stateLabelList"/>
				</html:select>&nbsp;
				<sub><img src="images/search.png" onclick="searchdetails()" align="absmiddle" /></sub>
			</td>
			</tr>
			<tr id="tab3">
			<td>HQ: </td>
			<td>
				<html:select  property="hqArray" name="vcForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" styleId="hqArray">
					<html:options name="vcForm"  property="hqList" labelProperty="hqLabelList"/>
				</html:select>
			</td>
		
		<td>Role: </td>
			<td>
				<html:select  property="desgArray" name="vcForm" multiple="multiple" onchange="console.log($(this).children(':selected').length)" styleClass="testSelAll" styleId="desgArray">
					<html:options name="vcForm"  property="desgList" labelProperty="desgLabelList"/>
				</html:select>
			</td>
		
		</tr>
		<tr>
		<td colspan="4">
		<center>
		<html:button property="method" value="Search" onclick="process()" styleClass="rounded"/>&nbsp;
		<html:button property="method" value="Close" onclick="closew()" styleClass="rounded"/>&nbsp;
		</center>
		</td>
		</tr>
	</table>
<br/>
<center>

	<table class="bordered" >
					<tr>
						<td>Request No: <font color="red">*</font> </td>
						<td><b>System Generated</b></td>	
						<td>Request Date: <font color="red">*</font></td>
						<td><b>${vcForm.reqDate}</b></td>
					</tr>
					<tr>
					<td>Category: <font color="red">*</font></td>
					<td>
					<html:select property="category" styleId="category">
					<html:option value="">--Select---</html:option>
					<html:option value="Sec. Sales">Sec. Sales</html:option>
					<html:option value="CMD Format">CMD Format</html:option>
					<html:option value="others">Others</html:option>
					</html:select>
					</td>
					
					
					<td>Title: <font color="red">*</font></td>
					<td>
					<html:text property="title" name="vcForm" styleId="title"></html:text>
					</td>
					
					</tr>
					
					<tr>
					
					<td>Description: <font color="red">*</font></td>
					<td colspan="3">
					<html:textarea property="desc" cols="30" styleId="desc"></html:textarea>
					</td>
					</tr>
					
					<tr>
					<td title="Start date">Start date: <font color="red" size="3">*</font></td>
					<td>
					<html:text property="fromDate" styleId="popupDatepicker" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"></html:text>
					 </td>
					 
					 
					 <td title="Start date" style="display: none" id="fre1">Frequency Submission date: <font color="red" size="3">*</font></td>
					<td style="display: none"  id="fre2">
					<html:text property="subdate" styleId="popupDatepicker3" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"></html:text>
					 </td>
					
					
					 </tr>
					 <tr>
					<td title="Last date to for submission">Request End date: <font color="red" size="3">*</font></td>
					<td>
					<html:text property="toDate" styleId="popupDatepicker2" onclick="datedisplay(this.id)" onmouseover="datedisplay(this.id)"></html:text>
					 </td>
					  
					 <td>Frequency: <font color="red">*</font></td>
					<td>
					<html:select property="frequency" onchange="displayfreq()">
					<html:option value="No">Once</html:option>
					<html:option value="Daily">Daily</html:option>
					<html:option value="Weekly">Weekly</html:option>
					<html:option value="Fortnite">Fortnite</html:option>
					<html:option value="Monthly">Monthly</html:option>
					<html:option value="Quaterly">Quaterly</html:option>
					<html:option value="Halfyearly">Halfyearly</html:option>
					<html:option value="Yearly">Yearly</html:option>
					</html:select>
					</td>
					</tr>
					<tr>
					
					<td>Attachment: <font color="red">*</font></td>
					<td colspan="3">
					<html:file property="documentFile" name="vcForm"/> 
					</td>
					</tr>
</table>	

</br>


<div style="float: center">

<logic:notEmpty name="emplist">
<html:button property="method" value="Submit" onclick="submitdata()" styleClass="rounded"/>&nbsp;
</logic:notEmpty>

</div>

<%-- <html:button property="method" value="Close" onclick="back()" styleClass="rounded"/>  &nbsp; --%> 


</center>

<br/>

<div style="height:300px;overflow:auto">
				<logic:notEmpty name="emplist">
				
			<div align="left" class="bordered">
			<table class="sortable" >
			<thead>
			
				<tr>
				<th colspan="11"><center>User Details</center></th>
				</tr>
				
				
			<tr>
			<th style="text-align:left;"><b>
			<input type="checkbox" name="checkProp" id="r4" onclick="checkAll()"/>
			Select All </b></th>
			<th style="text-align:left;"><b>Division</b></th>
			<th style="text-align:left;"><b>Emp.No</b></th>
			<th style="text-align:left;"><b>Full Name</b></th>
			<th style="text-align:left;"><b>Role</b></th>
			<th style="text-align:left;"><b>HQ</b></th>
			<th style="text-align:left;"><b>State</b></th>
										
					</tr>
					</thead>
				<logic:iterate id="mytable1" name="emplist">
				<tr>
					<td>
					
			<input type="checkbox"     name="documentCheck" value="${mytable1.empId}" id="${mytable1.empId}" style="width :10px;" />		
			
					
					</td>				
					
					<td>
					
				<bean:write name="mytable1" property="divisionid"/>
					</td>
										<td>
				<bean:write name="mytable1" property="empId"/>
				
				</td>
				<td>
				<bean:write name="mytable1" property="empName"/>
				</td>
				<td>
				<bean:write name="mytable1" property="desg"/>
				</td>
				<td>
				<bean:write name="mytable1" property="headquater"/>
				</td>
				<td>
				<bean:write name="mytable1" property="state"/>
				</td>
									</tr>
				</logic:iterate>
				</table></div>
		</logic:notEmpty>
		</div>



<br/>








</div>
</html:form>
</body>
</html>
