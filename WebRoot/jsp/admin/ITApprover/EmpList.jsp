<jsp:directive.page import="com.microlabs.utilities.UserInfo"/>
<jsp:directive.page import="com.microlabs.login.dao.LoginDao"/>
<jsp:directive.page import="java.sql.ResultSet"/>
<jsp:directive.page import="java.sql.SQLException"/>
<jsp:directive.page import="com.microlabs.utilities.IdValuePair"/>


<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="css2/micro_style.css" type="text/css" rel="stylesheet" />
<link href="style1/style.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
	<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
	<link rel="stylesheet" type="text/css" href="style3/css/style.css" />
<title>Microlab</title>
<script type="text/javascript">
function lastMaterialRecord()
{
var url="itApprover.do?method=lastRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function firstMaterialRecord()
{
var url="itApprover.do?method=employyList";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function nextMaterialRecord()
{
var url="itApprover.do?method=nextRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}

function previousMaterialRecord()
{
var url="itApprover.do?method=previousRecord";
			document.forms[0].action=url;
			document.forms[0].submit();
}
function addITApprover(){
var URL="itApprover.do?method=newApprovers";
		document.forms[0].action=URL;
 		document.forms[0].submit();
}

function viewEmpDetails(employeeNo,essType)
{
var URL="itApprover.do?method=editEmpDetails&EmpNo="+employeeNo+"&EssType="+essType;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}

function searchEmp(){
	if(document.forms[0].locationId.value==""){
		alert("Please Select Location");
		document.forms[0].locationId.focus();
		return false;
	}
	
	if(document.forms[0].subCategory.value==""){
		alert("Please Select Sub Category");
		document.forms[0].subCategory.focus();
		return false;
	}
	
	var subcategory=document.forms[0].subCategory.value;
	
var URL="itApprover.do?method=searchEmpRecord&subcategory="+subcategory;
		document.forms[0].action=URL;
 		document.forms[0].submit();

}
function editappr(req,loc)
{

var x=req;
var y=loc;
var URL="itApprover.do?method=editApprovers&reqtype="+x+"&loc="+y;
document.forms[0].action=URL;
 document.forms[0].submit();
}

function deleteappr(req,loc)
{

var x=req;
var y=loc;
var URL="itApprover.do?method=deleteApprovers&reqtype="+x+"&loc="+y;
document.forms[0].action=URL;
 document.forms[0].submit();
}
function reloadForm(){
	var URL="itApprover.do?method=displayItApprover";
			document.forms[0].action=URL;
	 		document.forms[0].submit();

	}
	
function getSubcat(linkname)
{
var xmlhttp;
var dt;
dt=linkname;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
    }
  }
xmlhttp.open("POST","itApprover.do?method=getsubcategory&linkName="+dt,true);
xmlhttp.send();
}

function displaySubCatogery(reqSubCat,requestType){
	 var xmlhttp;
	 var dt;
	 dt=requestType;
	 if (window.XMLHttpRequest)
	   {// code for IE7+, Firefox, Chrome, Opera, Safari
	   xmlhttp=new XMLHttpRequest();
	   }
	 else
	   {// code for IE6, IE5
	   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	   }
	 xmlhttp.onreadystatechange=function()
	   {
	   if (xmlhttp.readyState==4 && xmlhttp.status==200)
	     {
	     document.getElementById("subcategoryID").innerHTML=xmlhttp.responseText;
	     document.forms[0].subCategory.value=reqSubCat;
	     }
	   }
	 xmlhttp.open("POST","itApprover.do?method=getsubcategory&linkName="+dt,true);
	 xmlhttp.send();
}

function clearSearch(){
	var URL="itApprover.do?method=displayItApprover";
	document.forms[0].action=URL;
		document.forms[0].submit();
}
</script>
</head>

<body>
<html:form action="itApprover.do">
	<div align="center">
			<logic:present name="itApproverForm" property="message">
			<font color="red" size="2">
				<bean:write name="itApproverForm" property="message" />
			</font>
		</logic:present>
		<logic:present name="itApproverForm" property="message2">
			<font color="green" size="2">
				<bean:write name="itApproverForm" property="message2" />
			</font>
		</logic:present>
		</div>
<html:button property="method" value=" New " styleClass="rounded" onclick="addITApprover()" ></html:button>

	
	<div>&nbsp;</div>
	<logic:notEmpty name="getSubCategory">
	
<script type="text/javascript">

displaySubCatogery('<bean:write name="itApproverForm" property="essType"/>','<bean:write name="itApproverForm" property="requestType"/>');

</script>


</logic:notEmpty>

<table class="bordered">
<tr>
<th>Location <font color="red">*</font></th> 
			<td align="left">
				<html:select name="itApproverForm" property="locationId">
					<html:option value="">--Select--</html:option>
					<html:options name="itApproverForm" property="locationIdList" labelProperty="locationLabelList"/>
				</html:select>
			</td>
<th>Category1 <font color="red">*</font></th>	<td>
<html:select property="requestType" styleClass="content" styleId="filterId" onchange="getSubcat(this.value)">
	<html:option value="">--Select--</html:option>
	<html:option value="IT SERVICES">IT SERVICES</html:option>
		<html:option value="Hardware">Hardware</html:option>
		<html:option value="Internet">Internet</html:option>
		<html:option value="Network">Network</html:option>
		<html:option value="Operating System">Operating System</html:option>
		<html:option value="Printers">Printers</html:option>
		<html:option value="Software">Software</html:option>
		<html:option value="SAP">SAP</html:option>
		<html:option value="Telephone">Telephone</html:option>
	</html:select>
			
</td>
<th><b>Sub Category <font color="red">*</font></b></th>
<td>
		<div id="subcategoryID" align="left">
						<html:select property="essType" styleClass="content" styleId="filterId">
						
	 						<html:option value="">--Select--</html:option>
	   </html:select>
	   </div>
</td>
<td><a href="#"><img src="images/search.png" onclick="searchEmp()" align="absmiddle"/></a>

<a href="#"><img src="images/clearsearch.jpg" onclick="clearSearch()" align="absmiddle"/></a>
</td>
</tr>
</table>	

<center>
<div> &nbsp;</div>
<logic:notEmpty name="displayRecordNo">
	<table align="center">
	  	<td>
	  	
	<logic:notEmpty name="disablePreviousButton">
	<img src="images/disableLeft.jpg" align="absmiddle"/>
	</logic:notEmpty>
	<logic:notEmpty name="previousButton">
	<img src="images/previous1.jpg" onclick="previousMaterialRecord()" align="absmiddle"/>
	</logic:notEmpty>
	<bean:write property="startRecord"  name="itApproverForm"/>-
	<bean:write property="endRecord"  name="itApproverForm"/>
	<logic:notEmpty name="nextButton">
	<img src="images/Next1.jpg" onclick="nextMaterialRecord()" align="absmiddle"/>
	</logic:notEmpty>
	<logic:notEmpty name="disableNextButton">
	<img src="images/disableRight.jpg" align="absmiddle" />

	
	</logic:notEmpty>
	
	
	</td>
	
	<html:hidden property="totalRecords" name="itApproverForm"/>
	<html:hidden property="startRecord" name="itApproverForm"/>
	<html:hidden property="endRecord" name="itApproverForm"/>
	</logic:notEmpty>
	 </tr>
	 </table></center>
	<div>&nbsp;</div>
	<logic:notEmpty  name="Approvers">
<table align="center" class="bordered">
<tr><th>Location</th><th>Category</th><th>Sub Category</th><th>Total Approvers</th><th>Edit</th><th>Delete</th>
</tr>
<logic:iterate id="a" name="Approvers" >

<tr>
<td><bean:write name="a" property="location"/></td>
<td><bean:write name="a" property="category"/></td>
<td><bean:write name="a" property="requestType"/></td>


<td><bean:write name="a" property="totalRecords"/></td>
	<td><a href="#">
<img src="images/view.gif" height="28" width="28" title="View Record" onclick="editappr('${a.requestType}','${a.location}')"/></a>
     </td>
<td><a href="#">
<img src="images/delete.png"  title="Delete Record" onclick="deleteappr('${a.requestType}','${a.location}')"/></a>
     </td>
</tr>
</logic:iterate>
	
	</logic:notEmpty>
	
	<logic:notEmpty name="noApprovers">
	<table align="center" class="bordered">
<tr><th>Location</th><th>Category</th><th>Sub Category</th><th>Total Approvers</th><th>Edit</th>
</tr>
<tr>
<td colspan="5"><center> <font color="red" size="3">No approvers assigned</font></center></td>
</tr>
	</logic:notEmpty>

<html:hidden property="requestType"/>
</html:form>
</body>
</html>