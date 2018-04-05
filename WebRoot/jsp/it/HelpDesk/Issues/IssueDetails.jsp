<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script type="text/javascript" src="js/sorttable.js"></script>
 <link rel="stylesheet" type="text/css" href="style3/css/microlabs.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/TableCSS.css" />
 <link rel="stylesheet" type="text/css" href="style3/css/style.css" />
 <jsp:directive.page import="com.microlabs.it.form.IssuesForm" />
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
	.underline{width:100%; background-color:#a7c6dc; height:1px; margin-top:6px; margin-bottom:6px;}
	.tableCurves {
    border-collapse: separate;
    border-spacing: 0;
    border: 1px solid DarkGreen;
    border-radius: 5px;
    background: WhiteSmoke ;
    padding: 5px;
	</style>
 <script type="text/javascript">
 function resolveIssue(status){
  var empno=document.getElementById("empno").value;
  var fwdempno=document.forms[0].forwardEmpId.value;
 
if(empno==fwdempno)
{
alert("Ticket cannot be forwarded to Requester");
document.forms[0].forwardEmpId.value="";
		 document.forms[0].comments.focus();
		 return false;
}
 
 
	 if(document.forms[0].comments.value==""){
		 alert("Please Enter Some Solution");
		 document.forms[0].comments.focus();
		 return false;
	 }
	 
	 	var st = document.forms[0].comments.value;
	var Re = new RegExp("\\'","g");
	st = st.replace(Re,"`");
	document.forms[0].comments.value=st;  
	 
	 
	 if(document.forms[0].selectedIssueStatus.value==""){
		 alert("Please Select Status");
		 document.forms[0].selectedIssueStatus.focus();
		 return false;
	 }
	 
	 
	  if(document.forms[0].selectedIssueStatus.value=="Forwarded"){
	  
	  if(document.forms[0].forwardEmpId.value=="")
	  {
		 alert("Please Select Forward To");
		 document.forms[0].forwardEmpId.focus();
		 return false;
		 }
		 
		   if(document.forms[0].forwardEmpId.value!="")
	    {
	     var forwardEmpId = document.forms[0].forwardEmpId.value;
      var pattern = /^\d+(\d+)?$/
      if (!pattern.test(forwardEmpId)) {
           alert("Employee number should be number. ");
              document.forms[0].forwardEmpId.focus();
          return false;
      }
	    }

		 
	 }
	 
	  var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	 
	 document.forms[0].action="itIsssues.do?method=resolveIssue";
	document.forms[0].submit();
 }
 
 function showResolution(){
	 document.getElementById("showResolutionID").style.visibility="visible";
	
 }
 function searchEmployeeId(filed)
{
	var reqFiled=filed;
	var x=window.open("itIsssues.do?method=displayListUsers&reqFiled="+filed,"SearchSID","width=800,height=500,status=no,toolbar=no,scrollbars=yes,menubar=no,sizeable=0");
}
 function uploadSolutionDoc()
 {
 	if(document.forms[0].documentFile.value==""){
 		alert("Please Choose Atleast One File");
 		document.forms[0].documentFile.focus();
 		return false;
 	}
 	document.forms[0].action="itIsssues.do?method=uploadSolutionDoc";
 	document.forms[0].submit();
 }
 function deleteSolutionDoc()
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
 	document.forms[0].action="itIsssues.do?method=deleteSolutionDoc&cValues="+checkvalues;
 document.forms[0].submit();
 }
 }
 }
 function forwardIssue(){
	 
	 if(document.forms[0].forwardEmpId.value=="")
    {
	 alert("Please Enter Employee Number");
	 document.forms[0].forwardEmpId.focus();
	 return false;
    }
	  if(document.forms[0].forwardEmpId.value!="")
	    {
	     var forwardEmpId = document.forms[0].forwardEmpId.value;
      var pattern = /^\d+(\d+)?$/
      if (!pattern.test(forwardEmpId)) {
           alert("Employee number should be number. ");
              document.forms[0].forwardEmpId.focus();
          return false;
      }
	    }
	    
	     var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	    
	 document.forms[0].action="itIsssues.do?method=forwardIssue";
	 document.forms[0].submit(); 
 }
 
 function ReplyIssue(){
	 if(document.forms[0].comments.value==""){
		 alert("Please Enter Some Comments");
		 document.forms[0].comments.focus();
		 return false;
	 }
	 if(document.forms[0].comments.value!=""){
		 var st = document.forms[0].comments.value;
			var Re = new RegExp("\\'","g");
			st = st.replace(Re,"`");
			document.forms[0].comments.value=st; 
	 }
	 
	 if(document.forms[0].selectedIssueStatus.value==""){
		 alert("Please Select Status");
		 document.forms[0].selectedIssueStatus.focus();
		 return false;
	 }
	 
	 
	  var savebtn = document.getElementById("masterdiv");
	   savebtn.className = "no";
	   
	 document.forms[0].action="itIsssues.do?method=replyToTechnican";
	 document.forms[0].submit();  
 }
 function hideMessage(){
		
		document.getElementById("messageID").style.visibility="hidden";	
	}
 function showHistory(){
	 var status=document.getElementById("HistoryID").style.visibility;
	 var imgvalue=document.getElementById("arrowID").src;
	 
	 if(status=="hidden"){ 
		document.getElementById("HistoryID").style.visibility="visible";
		document.getElementById("arrowID").src="images/left_menu/down_arrow.png";
		
	 }
	 else{
		 document.getElementById("HistoryID").style.visibility="hidden";
		 document.getElementById("arrowID").src="images/left_menu/up_arrow.png";
	 }
 }
 
function back(){
	document.forms[0].action="itIsssues.do?method=currentRecord";
	 document.forms[0].submit();
} 

 function ApproveIssue(){
 
 alert("1");
 var com= document.getElementById("oncomments").value;
 alert(com);
	 if(com==""){
		 alert("Please Enter Some Comments");
		 document.forms[0].oncomments.focus();
		 return false;
		
	 }

	   document.forms[0].action="itIsssues.do?method=appsubmitReq&com="+com;
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
        if(reqFieldName=="forwardEmpId"){
        	document.getElementById("sU").style.display ="";
        	document.getElementById("sUTD").innerHTML=xmlhttp.responseText;
        	}
       
        	       			
        }
    }

     xmlhttp.open("POST","itHelpdesk.do?method=searchForApprovers&sId=New&searchText="+toadd+"&reqFieldName="+reqFieldName,true);
    xmlhttp.send();
}


function selectUser(input,reqFieldName){



	document.getElementById(reqFieldName).value=input;
	disableSearch(reqFieldName);
}

function disableSearch(reqFieldName){
  if(reqFieldName=="forwardEmpId"){
		if(document.getElementById("sU") != null){
		document.getElementById("sU").style.display="none";
		}
	}
	}
 </script>
 
 
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
 
 
  <style>

.no
{pointer-events: none; 
}
.design

{
	outline-style: dotted;
    outline-color: rgba(19,137,95,0.7);
} 


</style>
   </head>
  <body>
<html:form action="/itIsssues.do" enctype="multipart/form-data">
<div id="masterdiv" class="">
<div align="center" id="messageID" style="visibility: true;">
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
	</div>
	
<logic:notEmpty name="requseterDetails">
<logic:iterate id="det" name="requseterDetails">
<table class="bordered">
<tr><th colspan="4"><big>Requester Details</big></th></tr>


<tr><td><b>Name:</b></td><td> <bean:write name="det" property="employeename"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="det" property="employeeno"/><input type="hidden" value="${det.employeeno}" id="empno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="det" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="det" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="det" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="det" property="extno"/></td></tr>
<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="det" property="ipPhoneno"/></td>
<td><b>IP Address:</b></td><td ><bean:write name="det" property="IPNumber" /></td></tr>
<logic:notEmpty name="showReqReply12">
<tr><td ><font color="red" ><b>On_Behalf raised by</b></font></td><td><bean:write name="det" property="onbename"/></td><td><bean:write name="det" property="onbedepartment"/></td><td><bean:write name="det" property="onbedesignation"/></td></tr>
</logic:notEmpty>
<tr><th colspan="4"><big>Classification </big></th></tr>
<tr>
<td>Request No</td>
<td><bean:write name="det" property="requestNo"/></td>
<td>Request Date</td>
<td><bean:write name="det" property="reqDate"/></td>

</tr>
<tr>
<td >Status Of Message&nbsp;<font color="red" >*</font></td><td  >
<bean:write name="issuesForm" property="requestStatus"/>
</td>
<td >Priority&nbsp;<font color="red" >*</font></td><td  >
<bean:write name="det" property="issuePriority"/>
</td>



</tr>
<tr>
<td >Mode&nbsp;<font color="red" >*</font></td><td >
<bean:write name="det" property="mode"/>

</td>
<td><b>Category</b> <font color="red">*</font></td>
<td><bean:write name="det" property="category"/></td>

</tr>
<tr><td><b>Sub-Category</b> <font color="red">*</font></td>
<td colspan="3"><bean:write name="det" property="subcategory"/></td>	</tr>
<tr><th colspan="4"><big>Problem Details </big></th></tr>
<tr>
<td><b>Subject:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><bean:write name="det" property="subject"/></td></tr>
<tr>
	<td><b>Description:&nbsp;</b></td>
<td colspan="3"><bean:write name="det" property="reason"/></td>
</tr>
<tr>
	<td><b>On-Behalf comments:&nbsp;</b></td>
<td colspan="3"><bean:write name="det" property="oncomments"/></td>
</tr>
<logic:notEmpty name="requesterDetails">
 <tr>
	<th colspan="4">Attachments </th>
	</tr>
	<logic:iterate id="abc" name="requesterDetails">
	<tr>
		<td colspan="4">
			<a href="/EMicro Files/IT/Help Desk/Issues/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
		</td>
	</tr>
	</logic:iterate>
</logic:notEmpty>
</table>
</logic:iterate>
</logic:notEmpty>

<logic:notEmpty name="showReqReply1">
<table class="bordered">
<tr><th>Comments<font color="red" >*</font></th>
<td><html:textarea property="oncomments" styleId="oncomments" cols="80" rows="5" style="height: 88px; width: 657px;"/></td>
</tr>
<tr>
<td colspan="2"><center>
 <html:button property="method" value="Accept" styleClass="rounded" onclick="ApproveIssue()"/>
&nbsp;<html:button property="method" value="Reject" styleClass="rounded"  onclick="back()"/> </center>
 </td>
</tr>
</table>
</logic:notEmpty>


<!-- Requester Reply Options -->
<logic:notEmpty name="showReqReply">
<table class="bordered">
<tr><th>Comments<font color="red" >*</font></th>
<td><html:textarea property="comments" cols="80" rows="5" style="height: 88px; width: 657px;"/></td>
</tr>
<tr>
<td>Status Of Message &nbsp;<font color="red" >*</font></td>
<td><html:select property="selectedIssueStatus">
<html:option value="">-----Select-----</html:option>
<html:option value="Sent To IT">Sent To IT</html:option>
<html:option value="Completed">Completed</html:option>
</html:select>
</td>
</tr>
<tr>
            <th  colspan="4" align="center">Document Path : 
               	<html:file property="documentFile" />
                <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadSolutionDoc();" style="align:right;width:100px;"/>
              
            </th>
</tr>
<logic:notEmpty name="issueDocs">

          <tr>
						<th colspan="6">Uploaded Documents </th>
						</tr>
						<logic:iterate id="abc" name="issueDocs">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/IT/Help Desk/Issues/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
						<tr>
							<td align="center" colspan="6">
							<html:button property="method" value="Delete" onclick="deleteSolutionDoc()" styleClass="rounded"/>
							</td>
							</tr>

						</logic:notEmpty>

<tr>
<td colspan="2"><center>
 <html:button property="method" value="Submit" styleClass="rounded" onclick="ReplyIssue()"/>
&nbsp;<html:button property="method" value="Back" styleClass="rounded"  onclick="back()"/> </center>
 </td>
</tr>
</table>
</logic:notEmpty>


<!-- Technician Reply Options -->	
<logic:notEmpty name="showTechnReply">
<div>&nbsp;</div>
<table class="bordered">
<tr><th>Solution<font color="red" >*</font></th>
<td><html:textarea property="comments" cols="80" rows="5" style="height: 88px; width: 657px;"/></td>
</tr>
<tr>
<td>Status Of Message &nbsp;<font color="red" >*</font></td>
<td><html:select property="selectedIssueStatus">
<html:option value="">-----Select-----</html:option>
<html:option value="On Hold">On Hold</html:option>
<html:option value="User Action">User Action</html:option>
<html:option value="Completed">Completed For User</html:option>
<html:option value="Forwarded">Forwarded</html:option>
</html:select></td>
</tr>
<tr>
            <th  colspan="4" align="center">Document Path : 
               	<html:file property="documentFile" />
                <html:button property="method" styleClass="rounded"  value="Upload" onclick="uploadSolutionDoc();" style="align:right;width:100px;"/>
              
            </th>
</tr>
<logic:notEmpty name="issueDocs">

          <tr>
						<th colspan="6">Uploaded Documents </th>
						</tr>
						<logic:iterate id="abc" name="issueDocs">
						<tr>
							<td>
								<html:checkbox property="documentCheck" name="abc"
									value="${abc.id}" styleId="${abc.id}" onclick="addInput(this.value)" style="width :10px;"/>
							</td>
							<td colspan="3">
								<a href="/EMicro Files/IT/Help Desk/Issues/UploadFiles/${abc.fileName}"  target="_blank">  <bean:write name="abc" property="fileName"/></a>
							</td>
						</tr>
						</logic:iterate>
						<tr>
							<td align="center" colspan="6">
							<html:button property="method" value="Delete" onclick="deleteSolutionDoc()" styleClass="rounded"/>
							</td>
							</tr>

						</logic:notEmpty>
						
<tr>
<th>Forward To</th><td>

<html:text property="forwardEmpId" styleClass="rounded" onkeyup="searchEmployee('forwardEmpId')" styleId="forwardEmpId" >	<bean:write property="forwardEmpId" name="issuesForm" /></html:text>
&nbsp;<a href="#"><img src="images/search.png" align="absmiddle" onclick="searchEmployeeId('approver1')"/></a> 
&nbsp;<%-- <html:button property="method" value="Forward Issue" styleClass="rounded" onclick="forwardIssue()" styleId="fwd"/> --%>
<div id="sU" style="display:none;">
		<div id="sUTD" style="width:80px;">
		<iframe src="jsp/it/HelpDesk/searchemployee.jsp"  id="srcUId" scrolling="no" frameborder="0" height="30" width="60%"></iframe>
		</div>
	</div>
</td>
</tr>
<tr>
<td colspan="2"><center>
<logic:notEmpty name="submittbuton">
 <html:button property="method" value="Submit" styleClass="rounded" onclick="resolveIssue(this.value)"/>
 </logic:notEmpty>
&nbsp;<html:button property="method" value="Back" styleClass="rounded"  onclick="back()"/> </center>
 </td>
</tr>
</table>
</logic:notEmpty>

<!-- Others -->


<div>&nbsp;</div>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Technician Details</th></tr>
	<tr><th>Employee No</th><th>Employee Name</th><th>Department</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.technicianID }</td><td>${abc.employeename }</td><td>${abc.department }</td><td>${abc.requestStatus }</td><td>${abc.approvedDate }</td><td>${abc.comments }</td>
	</tr>

	</logic:iterate>
</table>
	</logic:notEmpty>
<div>&nbsp;</div>	
<logic:notEmpty name="convList">
<div  onclick="showHistory()" style="background: DarkKhaki;" onmouseover="this.style.background='CadetBlue ';" onmouseout="this.style.background='DarkKhaki';">
<b><big>&nbsp;History&nbsp;<img src="images/left_menu/up_arrow.png" alt="" id="arrowID" align="absmiddle"/> </big></b></div>
<div>&nbsp;</div>
<div style="visibility: hidden;" id="HistoryID">
<table class="tableCurves">
<tr>
<td style="background: LightGray;"><img src="images/conversation.png" alt="" height="20px" width="25px"/>
<b><big>Communication:</big></b></td>
</tr>
<logic:iterate id="a" name="convList">
<tr>
<td>
<b>Employee Name&nbsp;:</b>&nbsp;${a.employeename } &nbsp;&nbsp;&nbsp;&nbsp;<b>Date:</b>${a.reqDate }
<table  style="background-color: NavajoWhite ;width: 180px;height:30px; " align="right" class="tableCurves">
<tr>
<td><b>&nbsp;Status:</b>&nbsp;${a.requestStatus }&nbsp;</td>
</tr>
</table>
<br/>
<b>Description :</b>
${a.comments }<br/>
<%
IssuesForm  form1=(IssuesForm)a;
String fileNames=form1.getFileName();
if(!fileNames.equals("")){
%>
<b>Documents:</b><br/>
<% 	

	String s = fileNames.toString();
	s = s.substring(0, s.length());
	String v[] = s.split(",");
	int l = v.length;
	for (int i = 0; i < l; i++) {

		String u=v[i];
%>
<a href="/EMicro Files/IT/Help Desk/Issues/UploadFiles/<%=u%>" target="_blank"><%=u%></a>

<br />
<%
}
}
%>
<div class="underline"></div>
</td>
</tr>
</logic:iterate>
</table>
</div>
</logic:notEmpty>


<html:hidden property="transNo" name="issuesForm"/>	
<html:hidden property="empType" name="issuesForm"/>	
<html:hidden property="requestNo" name="issuesForm" />	

<html:hidden property="totalRecords" />
<html:hidden property="startRecord" />
<html:hidden property="endRecord" />

<html:hidden property="empType" />
<html:hidden property="locationId" />
<html:hidden property="chooseType" />

<html:hidden property="maincategory" />
<html:hidden property="mainrequestStatus" />

<html:hidden property="fromDate" />
<html:hidden property="toDate" />
<html:hidden property="department" />

</div>
</html:form>
</body></html>