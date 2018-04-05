<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link href="style1/inner_tbl.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/microlabs1.css" />
<link rel="stylesheet" type="text/css" href="css/displaytablestyle.css" />
<link rel="stylesheet" type="text/css" href="css/styles.css" />
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
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
 <script type="text/javascript">
function getTechnicians(subCategory){
	var category=document.forms[0].category.value;
	var location=document.forms[0].locNo.value;
	var xmlhttp;
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
	    document.getElementById("techniciansID").innerHTML=xmlhttp.responseText;
	    }
	  }
	xmlhttp.open("POST","itIsssues.do?method=getTechnicians&category="+category+"&subCategory="+subCategory+"&locNo="+location,true);
	xmlhttp.send();
} 


function reqtype()
{
var type=document.getElementById("request").value;
var cat="";
if(type=="1")
{
 cat="'PSAP','SSAP'";
  var URL="itIsssues.do?method=sapuseridmanagement&cate="+cat;
		  document.forms[0].action=URL;
	 	  document.forms[0].submit();
}
if(type=="2")
{
 cat="'AUTH'";
  var URL="itIsssues.do?method=sapuseridmanagement&cate="+cat;
		  document.forms[0].action=URL;
	 	  document.forms[0].submit();
}

if(type=="3")
{
cat="'ROLES'";
  var URL="itIsssues.do?method=sapuseridmanagement&cate="+cat;
		  document.forms[0].action=URL;
	 	  document.forms[0].submit();
}

if(type=="4")
{
cat="'DC'";
  var URL="itIsssues.do?method=sapuseridmanagement&cate="+cat;
		  document.forms[0].action=URL;
	 	  document.forms[0].submit();
}



}
 
function getSubcat(category)
{
 
var xmlhttp;
var dt;
dt=category;
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
xmlhttp.open("POST","itIsssues.do?method=getsubcategory&linkName="+dt,true);
xmlhttp.send();
}
function uploadDocument()
{
	if(document.forms[0].category.value=="")
	{
	alert("Please Select Category");
	document.forms[0].category.focus();
     return false;
	}
	
	if(document.forms[0].subCategory.value=="")
	{
	alert("Please Select Sub-Category");
	document.forms[0].subCategory.focus();
     return false;
	}
	
	if(document.forms[0].techinician.value=="")
	{
	alert("Please Select Techician");
	document.forms[0].techinician.focus();
     return false;
	}
	
	if(document.forms[0].documentFile.value==""){
		alert("Please Choose Atleast One File");
		document.forms[0].documentFile.focus();
		return false;
	}
	var fileName=document.forms[0].documentFile.value;
	var ext = fileName.split('.').pop();
   
    if(ext=="jpg"||ext=="png"||ext=="pdf"||ext=="txt"||ext=="xlsx"||ext=="xls"||ext=="doc"||ext=="docx"){
    
    }else{
	alert("Please Upload only jpg,png,pdf,txt,xls,xlsx,doc,docx extension files");
    	
    	return false;
    }
	var subCategory="";
	var technician="";
	 subCategory=document.forms[0].subCategory.value;
	 technician=document.forms[0].techinician.value;
	document.forms[0].action="itIsssues.do?method=uploadDocuments&subCategory="+subCategory+"&technician="+technician;
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
alert('please select atleast one value to delete');
}
else
{
var agree = confirm('Are You Sure To Delete Selected file');
if(agree)
{
	var subCategory="";
	var technician="";
	 subCategory=document.forms[0].subCategory.value;
	 technician=document.forms[0].techinician.value;
	document.forms[0].action="itIsssues.do?method=deleteDocuments&cValues="+checkvalues+"&subCategory="+subCategory+"&technician="+technician;
document.forms[0].submit();
}
}
}
function back(){
	document.forms[0].action="itIsssues.do?method=displayAllIssues";
	document.forms[0].submit();
}

function setSubCatTech(subCategory,Technician){
	 alert("Documents has been updated");
	 document.forms[0].subCategory.value=subCategory;
	 document.forms[0].techinician.value=Technician;
}
function submitData(){
	if(document.forms[0].issuePriority.value=="")
	{
	alert("Please Select Priority");
	document.forms[0].issuePriority.focus();
     return false;
	}

if(document.forms[0].category.value=="")
{
alert("Please Select Category");
document.forms[0].category.focus();
 return false;
}

if(document.forms[0].subCategory.value=="")
{
alert("Please Select Sub-Category");
document.forms[0].subCategory.focus();
 return false;
}

if(document.forms[0].techinician.value=="")
{
alert("Please Select Techician");
document.forms[0].techinician.focus();
 return false;
}

if(document.forms[0].subject.value=="")
{
alert("Please Enter Subject");
document.forms[0].subject.focus();
 return false;
}
var st = document.forms[0].subject.value;
var Re = new RegExp("\\'","g");
st = st.replace(Re,"`");
document.forms[0].subject.value=st;  
var st = document.forms[0].reason.value;
var Re = new RegExp("\\'","g");
st = st.replace(Re,"`");
document.forms[0].reason.value=st; 
	var subCategory=document.forms[0].subCategory.value;
	 var technician=document.forms[0].techinician.value;
	 
	 var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	 
	document.forms[0].action="itIsssues.do?method=submitReq&subCategory="+subCategory+"&technician="+technician;
	document.forms[0].submit();
}

 </script>
 
 
 <style>

.no
{
pointer-events: none; 

}

.design

{

	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);

} 


</style>

	<style>
#modalContainer {
	background-color:rgba(0, 0, 0, 0.3);
	position:absolute;
	width:100%;
	height:100%;
	top:0px;
	left:0px;
	z-index:10000;
	background-image:url(tp.png); /* required by MSIE to prevent actions on lower z-index elements */
}

#alertBox {
	position:relative;
	width:300px;
	min-height:100px;
	margin-top:220px;
	border:1px solid #666;
	background-color:#fff;
	background-repeat:no-repeat;
	background-position:20px 30px;
	font-size: 15px;
}

#modalContainer > #alertBox {
	position:fixed;
}

#alertBox h1 {
	margin:0;
	font:bold 0.9em verdana,arial;
	background-color:rgb(125,170,201);
	color:#FFF;
	border-bottom:1px solid #000;
	padding:2px 0 2px 5px;
}

#alertBox p {
	font:0.7em verdana,arial;
	height:50px;
	padding-left:5px;
	margin-left:55px;
}

#alertBox #closeBtn {
	display:block;
	position:relative;
	margin:5px auto;
	padding:7px;
	border:0 none;
	width:70px;
	font:0.7em verdana,arial;
	text-transform:uppercase;
	text-align:center;
	color:#FFF;
	background-color:rgb(125,170,201);
	border-radius: 3px;
	text-decoration:none;
}

/* unrelated styles */

#mContainer {
	position:relative;
	width:600px;
	margin:auto;
	padding:5px;
	border-top:2px solid #000;
	border-bottom:2px solid #000;
	font:0.7em verdana,arial;
}



</style>
<style>
.fa-spinner {
    color:blue;
}

  .fa-check-square {
    color:green;
}

.fa-square{
color:grey;
}
                   </style> 

	<script>
	var ALERT_TITLE = "Information";
var ALERT_BUTTON_TEXT = "OK";

if(document.getElementById) {
	window.alert = function(txt,value) {
		createCustomAlert(txt,value);
	}
}

function createCustomAlert(txt,value) {
	d = document;

	if(d.getElementById("modalContainer")) return;

	mObj = d.getElementsByTagName("body")[0].appendChild(d.createElement("div"));
	mObj.id = "modalContainer";
	mObj.style.height = d.documentElement.scrollHeight + "px";
	
	alertObj = mObj.appendChild(d.createElement("div"));
	alertObj.id = "alertBox";
	if(d.all && !window.opera) alertObj.style.top = document.documentElement.scrollTop + "px";
	alertObj.style.left = (d.documentElement.scrollWidth - alertObj.offsetWidth)/2 + "px";
	alertObj.style.visiblity="visible";

	h1 = alertObj.appendChild(d.createElement("h1"));
	h1.appendChild(d.createTextNode(ALERT_TITLE));

	msg = alertObj.appendChild(d.createElement("p"));
	//msg.appendChild(d.createTextNode(txt));
	msg.innerHTML = txt;

	btn = alertObj.appendChild(d.createElement("a"));
	btn.id = "closeBtn";
	btn.appendChild(d.createTextNode(ALERT_BUTTON_TEXT));
	btn.href = "#";
	btn.focus();
	btn.onclick = function() { removeCustomAlert(value);return false; }

	alertObj.style.display = "block";
	
}

function removeCustomAlert(value) {
  
  

	document.getElementsByTagName("body")[0].removeChild(document.getElementById("modalContainer"));
	
	
 }
 
 	function statusMessage(message){
				alert(message);
			
				
		
				}
 </script>

  </head>
  
  <body>
   <html:form action="/itIsssues.do" enctype="multipart/form-data">
      <div id="masterdiv" class="">
   <div align="center">
		<logic:notEmpty name="issuesForm" property="message">
	<script language="javascript">
					statusMessage('<bean:write name="issuesForm" property="message" />','');
					</script>
	</logic:notEmpty>
	</div>
	<div align="center">
		<logic:present name="issuesForm" property="message2">
		<script language="javascript">
					statusMessage('<bean:write name="issuesForm" property="message2" />','');
					</script>
	</logic:present>
 </div>
  <div style="width: 50%" >
 		<table class="bordered" >
		<tr>
		<th colspan="5">SAP REQUEST</th>
		</tr>
		<tr>
		<td>Type&nbsp;<font color="red">*</font></td>
		<td>
		
			<html:select  property="requestType" name="issuesForm" styleId="request">
			<html:option value="">--Select--</html:option>
			<%-- <html:option value="SAP User Id Management">SAP User Id Management</html:option>
			<html:option value="SAP Authorization Management">SAP Authorization Management</html:option>
				<html:option value="SAP Roles Management">SAP Roles Management</html:option>
					<html:option value="SAP Document-Cancellation">SAP Document-Cancellation</html:option> --%>
				<%-- <html:option value="user_wise">User Wise</html:option> --%>
			 <html:options name="issuesForm"  property="requestidlist" labelProperty="requestlist"/> 
		</html:select>
	
	<%-- 	<html:button property="method" value="GO" onclick="showlist()" styleClass="rounded" />&nbsp; --%>
		&nbsp;
		<html:button property="method" value="Go" onclick="reqtype()" styleClass="rounded"/>&nbsp;
		</td>
		
		</tr>
		
		
		</table>
		</div>
</div>

</html:form>

  </body>
</html>
