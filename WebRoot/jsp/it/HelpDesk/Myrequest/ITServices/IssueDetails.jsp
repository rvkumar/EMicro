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
 <jsp:directive.page import="com.microlabs.it.form.HelpDeskForm" />
 <jsp:directive.page import="com.microlabs.admin.form.HelpDeskReportForm" />
 
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

 </script>
   </head>
  <body>
<td></td><html:form action="/itHelpdesk.do" enctype="multipart/form-data">
<div align="center" id="messageID" style="visibility: true;">
 <div align="center">
 
 		<logic:notEmpty name="itHelpdeskForm" property="message"> 
		<font color="green" size="3">
			<b><bean:write name="itHelpdeskForm" property="message" /></b>
		</font>
		<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
	</logic:notEmpty> 
	</div>
	<div align="center">
		<logic:present name="itHelpdeskForm" property="message2">
		<font color="red" size="3">
			<b><bean:write name="itHelpdeskForm" property="message2" /></b>
		</font>
		<script type="text/javascript">
					setInterval(hideMessage,6000);
					</script>
	</logic:present>
	</div>
	</div>
	
<logic:notEmpty name="requseterDetails">
<logic:iterate id="det" name="requseterDetails">
<table class="bordered">
<tr><th colspan="4"><big>Requester Details</big></th></tr>


<tr><td><b>Name:</b></td><td> <bean:write name="det" property="employeename"/></td>

<td><b>Employee No:</b></td><td ><bean:write name="det" property="employeeno"/></td></tr>
<tr>
<td><b>Department:</b></td><td ><bean:write name="det" property="requesterdepartment"/></td>
<td><b>Designation:</b></td><td ><bean:write name="det" property="requesterdesignation"/></td></tr>
<tr>
<td><b>Location:</b></td><td ><bean:write name="det" property="location"/></td>
<td><b>Ext No:</b></td><td ><bean:write name="det" property="extno"/></td></tr>
<tr>
<td><b>IP Phone No:</b></td><td ><bean:write name="det" property="ipPhoneno"/></td>
<td><b>IP Address:</b></td><td ><bean:write name="det" property="IPNumber"/></td></tr>
<tr><th colspan="4"><big>Classification </big></th></tr>
<tr>
<td>Request No</td>
<td><bean:write name="det" property="requestNo"/></td>
<td >Status Of Message&nbsp;<font color="red" >*</font></td><td  >
<bean:write name="itHelpdeskForm" property="requestStatus"/>
</td>
</tr>
<tr>
<td >Priority&nbsp;<font color="red" >*</font></td><td  >
<bean:write name="det" property="issuePriority"/>
</td>
<td >Mode&nbsp;<font color="red" >*</font></td><td >
<bean:write name="det" property="mode"/>

</td>


</tr>
<tr>
<td><b>Category</b> <font color="red">*</font></td>
<td><bean:write name="det" property="category"/></td>
<td><b>Sub-Category</b> <font color="red">*</font></td>
<td><bean:write name="det" property="subcategory"/></td>	
</tr>
<tr><th colspan="4"><big>Problem Details </big></th></tr>
<tr>
<td><b>Subject:&nbsp;<font color="red" >*</font></b></td>
<td colspan="3"><bean:write name="det" property="subject"/></td></tr>
<tr>
	<td><b>Reason:&nbsp;</b></td>
<td colspan="3"><bean:write name="det" property="reason"/></td>
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

<!-- Requester Reply Options -->



<!-- Technician Reply Options -->	



<!-- Others --><center>
<html:button property="method" value="Close" styleClass="rounded"  onclick="history.back(-1)"/></center>
<logic:notEmpty name="showBackButton">
<div> &nbsp;</div>
<center>
&nbsp; </center>
</logic:notEmpty>
<div>&nbsp;</div>
<logic:notEmpty name="approverDetails">
	<table class="bordered">
	<tr><th colspan="6">Technician Details</th></tr>
	<tr><th>Employee No</th><th>Employee Name</th><th>Status</th><th>Date</th><th>Comments</th></tr>
	<logic:iterate id="abc" name="approverDetails">
	<tr>
	<td>${abc.technicianID }</td><td>${abc.employeename }</td><td>${abc.requestStatus }</td><td>${abc.approvedDate }</td><td>${abc.comments }</td>
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


<html:hidden property="transNo" name="itHelpdeskForm"/>	
<html:hidden property="empType" name="itHelpdeskForm"/>	
<html:hidden property="requestNo" name="itHelpdeskForm" />	

<html:hidden property="totalRecords" />
<html:hidden property="startRecord" />
<html:hidden property="endRecord" />

<html:hidden property="empType" />
<html:hidden property="locationId" />
<html:hidden property="chooseType" />

</html:form>
</body></html>